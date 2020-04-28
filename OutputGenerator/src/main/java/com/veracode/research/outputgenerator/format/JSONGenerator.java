package com.veracode.research.outputgenerator.format;

import com.google.gson.*;
import com.veracode.research.outputgenerator.format.customformatter.NullRemoverTypeAdapter;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;


public class JSONGenerator implements OutputFormatGenerator {

    @Override
    public String generateOutput(Object outputData) {
        String output = "" ;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                //.setFieldNamingStrategy(customPolicy)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .registerTypeHierarchyAdapter(Collection.class,new NullRemoverTypeAdapter())
                .create();


        return gson.toJson(outputData);
    }

    private class MyExclusions implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            System.out.println(fieldAttributes.getName());
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            System.out.println(aClass);
            return false;
        }
    }
}
