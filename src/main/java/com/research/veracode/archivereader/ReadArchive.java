package com.research.veracode.archivereader;

import org.apache.commons.io.IOUtils;

import java.io.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


import java.net.MalformedURLException;
import java.net.URL;



public class ReadArchive {
    /*
    Reads and returns input stream of local file
     */
    public InputStream getInputStreamLocalFile(String path) {

        InputStream is = null;

        try {
            is = new FileInputStream(new File(path));

        } catch (FileNotFoundException e) {
            System.out.println("Exception: Reading input file " + path);
            System.exit(0);
        }

        return is;
    }


    /*
    Reads and returns input stream of remote file.
    */
    public InputStream getInputStreamRemoteFile(String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream() ;

        try {
            BufferedInputStream inputStream = new BufferedInputStream(new URL(path).openStream());
            baos = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) > 0) {
                baos.write(data, 0, byteContent);
            }
        } catch (MalformedURLException mfue) { System.out.println("Exception: Couldn't read from " + path); }
        catch(IOException io) {System.out.println("Exception : while reading from " + path);}

        byte[] bytes = baos.toByteArray() ;
        return new ByteArrayInputStream(bytes) ;
    }

    /*
    Returns list of all files in this archive
     */
    public List<String> listArchiveFiles(InputStream is) {

        List<String> files = new ArrayList<String>();

        ZipInputStream zipInputStream = new ZipInputStream(is);

        ZipEntry entry;

        try {
            while ((entry = zipInputStream.getNextEntry()) != null) {
                files.add(entry.getName());
            }
        } catch(IOException io) {System.out.println("Exception: Traversing thru file content");}

        return files;
    }


    /*
    @param path: Path of local jmod path. Used only for jmod files.
    @return : List of all files in this jmod file.
     */
    public List<String> listArchiveFiles(String path) {
        List<String> files = new ArrayList<>();
        ZipFile zf = null;
        try {
            zf = new ZipFile(path);
        } catch (IOException e) {
            System.out.println("Exception: Reading jmod file " + path);
            System.exit(0);
        }

        Enumeration<? extends ZipEntry> entries = zf.entries();


        while (entries.hasMoreElements()) {
            files.add(entries.nextElement().getName());
        }

        return files;
    }

    /*
    @param is is InputStream of entire jar file. Used only for jar files.
    @param matchingPattern is regexp of files we want to match against. For e.g. pass matchingFileName as "namespace" or a file extn.
    @return: Returns a map of all matchingPattern filename and corresponding InputStream of each matchPattern file names.
     */
    public Map<String, InputStream> getNameSpaceFilesInputStream(InputStream is, String matchingPattern) {
        Pattern p = Pattern.compile(matchingPattern);

        Map<String, InputStream> matchingFileInputStream = new HashMap<String, InputStream>();

        ZipInputStream zipInputStream = new ZipInputStream(is);

        ZipEntry entry;

        Matcher m ;

        try {
            while ((entry = zipInputStream.getNextEntry()) != null) {

                m = p.matcher(entry.getName().replace("/","."));
                ByteArrayOutputStream baos = new ByteArrayOutputStream() ;

                if(m.matches()) {
                    try {
                        IOUtils.copy(zipInputStream, baos) ;
                    } catch (IOException e) {System.out.println("Exception: Reading " + entry.getName());e.printStackTrace(); }
                    matchingFileInputStream.put(entry.getName(),new ByteArrayInputStream(baos.toByteArray()));
                }
            }
        } catch(IOException io) {System.out.println("Exception: Traversing thru file content");}

        return matchingFileInputStream;
    }

    /*
    @param path: Local file path of jmod files. This function us used only for jmod files.
    @param: matchPattern is regexp of files we want to match against. For e.g. pass matchingFileName as "namespace" or a file extn.
    @return: Returns a map of all matchingPattern filename and corresponding InputStream of each matchPattern file names.
     */
    public Map<String, InputStream> getNameSpaceFilesInputStream(String path, String matchingPattern) {
        Pattern p = Pattern.compile(matchingPattern);
        Matcher m ;

        Map<String, InputStream> matchingFileInputStream = new HashMap<String, InputStream>();

        ZipFile zf = null ;
        try {
            zf = new ZipFile(path);
        } catch (IOException e) {
            System.out.println("Exception: Issues reading jmod file " + e.getMessage());
        }

        Enumeration<? extends ZipEntry> entries = zf.entries();

        try {
            while(entries.hasMoreElements()) {

                ZipEntry entry = entries.nextElement();
                m = p.matcher(entry.getName().replace("/","."));

                if(m.matches()) {
                    matchingFileInputStream.put(entry.getName(), zf.getInputStream(entry));
                }
            }

        } catch(IOException e){System.out.println("Exception : Issues getting entries from jmod file " + e.getMessage());}

        return matchingFileInputStream;
    }
}

