package com.research.veracode.treeviewer;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.research.veracode.archivereader.ReadArchive;
import com.research.veracode.classextractor.ClassFileExtractor;


import com.thoughtworks.xstream.XStream;
import com.veracode.research.outputgenerator.OutputGenerator;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
Nice FileUtils: https://www.rgagnon.com/javadetails/java-0665.html
 */
public class NamespaceTreeViewer {

    @Parameter(names = {"--path", "-p"},description = "Location of jar file, if not on classpath. Could be local or remote", required=true, order = 0)
    private static String path;

    @Parameter(names = {"--namespace", "-n"},description = "Namespace whose details is needed", order = 1)
    private static String namespace;

    @Parameter(names = {"--output", "-o"},description = "Output format text/xml/json/graphdb. (Default to text output)" , order = 2)
    private static String output = "text";

    @Parameter(names = {"--file", "-f"},description = "Output file name. Will add extention automatically. (Defaults to output)" , order = 3)
    private static String outputFile = "output";

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

        InputStream jarInputStream = null ;
        ClassFileExtractor classFileExtractor = new ClassFileExtractor(NamespaceTreeViewer.class.getResourceAsStream("/data.yaml"));

        // We are storing fully-quantified class file name in ClassFile POJO. So, technically we needed just a List to store all ClassFile populated objects.
        // Decided to use Map, just if we decide to add provision for non-class files, like a TOC functionality. It would lead to a lot of code change.
        Map<String, Object> outputClassFileDetails = new HashMap<String, Object>();
        OutputGenerator outputGenerator = new OutputGenerator(output) ;

        if(!path.startsWith("http")) { // if jar file is local.
            jarInputStream = readArchive.getInputStreamLocalFile(path);
        } else { // if jar file is remote
            jarInputStream = readArchive.getInputStreamRemoteFile(path);
        }

        for(Map.Entry<String, InputStream> entry : readArchive.getNameSpaceFilesInputStream(jarInputStream, namespace+".*.class").entrySet()) {
            outputClassFileDetails.put(entry.getKey(), classFileExtractor.getClassFileData(entry.getValue(), entry.getKey()));
        }

        PrintWriter printWriter = null ;

        try {
            printWriter = new PrintWriter(new FileWriter(outputFile+"."+output));
        } catch (IOException e) {
            System.out.println("Exception : While creating file " + outputFile + "." + output);
        }

        printWriter.print(outputGenerator.generateOutput(outputClassFileDetails));
        printWriter.close();
    }
}
