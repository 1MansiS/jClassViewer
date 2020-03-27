package com.research.veracode.archivereader;

import org.apache.commons.io.IOUtils;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
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

    @param is is InputStream if entire jar file.
    @param matchingPattern is regexp of files we want to match against. For e.g.
    Returns a map of all matchingPattern filename from the input stream of a zip file.
    For e.g pass matchingFileName as "namespace" or a file extn.
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
}

