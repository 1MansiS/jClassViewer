package com.veracode.research.outputgenerator;

import com.veracode.research.outputgenerator.format.*;

import java.util.Map;

public class OutputGenerator {

    private String outputFormat = "" ;

    public OutputGenerator(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String generateOutput(Object outputData) {
        OutputFormatGenerator outputFormatGenerator = null;

        switch (this.outputFormat) {
            case "TEXT" :
            case "txt" :
            case "TXT" :
            case "text" : outputFormatGenerator = new TextGenerator(); break;

            case "XML" :
            case "xml" :    outputFormatGenerator = new XMLGenerator(); break ;

            case "JSON" :
            case "json" : outputFormatGenerator = new JSONGenerator(); break ;

            case "GRAPHDB" :
            case "graphdb" : outputFormatGenerator = new GraphDBGenerator(); break ;

            default : outputFormatGenerator = new TextGenerator(); break ;
        }

        return outputFormatGenerator.generateOutput(outputData) ;
    }
}
