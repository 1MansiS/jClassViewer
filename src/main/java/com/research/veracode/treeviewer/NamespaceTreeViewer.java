package com.research.veracode.treeviewer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.research.veracode.archivereader.ReadArchive;

import com.research.veracode.classextractor.ClassFileExtractor;
import com.research.veracode.classextractor.dto.Archive;
import com.research.veracode.classextractor.dto.ClassFile;
import com.veracode.research.outputgenerator.OutputGenerator;


/*
Nice FileUtils: https://www.rgagnon.com/javadetails/java-0665.html
 */
public class NamespaceTreeViewer {

    @Parameter(names = {"--path", "-p"},description = "Location of jar file, if not on classpath. Could be local or remote", required=true, order = 0)
    private static String path;

    @Parameter(names = {"--namespace", "-n"},description = "Namespace whose details is needed", order = 1)
    private static String namespace = "";

    @Parameter(names = {"--output", "-o"},description = "Output format xml/json." , order = 2)
    private static String output = "xml";

    @Parameter(names = {"--file", "-f"},description = "Output file name. Will add extention automatically. (Defaults to output)" , order = 3)
    private static String outputFile = "";

    @Parameter(names = {"--list", "-l"},description = "List class names" , order = 4)
    private static boolean list = false;

    @Parameter(names = {"--help","-h"}, help = true, order = 4)
    private boolean help;

    private static ReadArchive readArchive = new ReadArchive();

    public static void main(String args[]) {
        NamespaceTreeViewer namespaceTreeViewer = new NamespaceTreeViewer();

        JCommander jct = JCommander.newBuilder()
                .addObject(namespaceTreeViewer)
                .build();

        jct.setProgramName(NamespaceTreeViewer.class.getSimpleName());

        jct.parse(args);


        if (namespaceTreeViewer.help) {
            jct.usage();
            return;
        }

        if(outputFile.equals("")) {
            String archiveFileName = path.substring(path.lastIndexOf('/') + 1);
            outputFile = archiveFileName.substring(0,archiveFileName.lastIndexOf(".")) + "." + output;
        }

        InputStream jarInputStream = null ;
        ClassFileExtractor classFileExtractor = new ClassFileExtractor(NamespaceTreeViewer.class.getResourceAsStream("/data.yaml"));

        List<ClassFile> listOfClassFileDetails = new ArrayList<ClassFile>();
        OutputGenerator outputGenerator = new OutputGenerator(output) ;

        List<String> listOfFiles = new ArrayList<String>();
        Map<String, InputStream> inputStreamOfFiles = null;


        /*
        Keeping jmod file support separate from jar files for few reasons:
            1. ZipInputStream API doesn't support jmod file format, only ZipFile supports it. So, restricted by that.
            2. jmod file is not documented publicly, so there are chances it oracle could change it in future. If so, it would be an easier code maintenance.
         */
        if(path.endsWith("jar")) {
            if(list) {
                listOfFiles = readArchive.listArchiveFiles(jarInputStream);
                printListOfFiles(listOfFiles);
                System.exit(0);
            }

            if(path.startsWith("http")) { // remote file
                jarInputStream = readArchive.getInputStreamRemoteFile(path);
            } else { // local file
                jarInputStream = readArchive.getInputStreamLocalFile(path);
            }

            inputStreamOfFiles = readArchive.getNameSpaceFilesInputStream(jarInputStream, namespace+".*.class");

        } else if(path.endsWith("jmod")) {
            if(list) {
                listOfFiles = readArchive.listArchiveFiles(jarInputStream);
                printListOfFiles(listOfFiles);
                System.exit(0);
            }

            if(!path.startsWith("http")) { // remote jmod file
                inputStreamOfFiles = readArchive.getNameSpaceFilesInputStream(path, namespace+".*.class");
            } else { // local jmod file
                System.out.println("Currently only supporting local jmod files");
                jct.usage();
                System.exit(0);
            }
        } else {
            System.out.println("Unknown file type");
            jct.usage();
        }

        for(Map.Entry<String , InputStream> entry : inputStreamOfFiles.entrySet()) {
            listOfClassFileDetails.add((ClassFile)classFileExtractor.getClassFileData(entry.getValue(), entry.getKey()));
        }

        Archive archive = new Archive();
        archive.setArchiveName(path);
        archive.setListOfClassFileDetails(listOfClassFileDetails);

        PrintWriter printWriter = null ;

        try {
            printWriter = new PrintWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            System.out.println("Exception : While creating file " + outputFile);
        }

        printWriter.print(outputGenerator.generateOutput(archive));
        printWriter.close();
    }

    private static void printListOfFiles(List<String> listofFiles) {
        for(String file : listofFiles) {
            System.out.println(file);
        }
    }
}
