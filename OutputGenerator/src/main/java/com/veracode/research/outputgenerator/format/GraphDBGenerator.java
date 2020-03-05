package com.veracode.research.outputgenerator.format;

import java.util.Map;

public class GraphDBGenerator implements OutputFormatGenerator{
    @Override
    public String generateOutput(Map<String, Object> outputData) {
        return "graphdb";
    }
}
