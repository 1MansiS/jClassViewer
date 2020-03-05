package com.veracode.research.outputgenerator.format;

import com.google.gson.*;
import com.veracode.research.outputgenerator.format.customformatter.NullRemoverTypeAdapter;


import java.util.Collection;
import java.util.Map;

public class TextGenerator implements OutputFormatGenerator {
    private String retString = "" ;

    @Override
    public String generateOutput(Map<String, Object> classFileMap) {

        Gson gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(Collection.class,new NullRemoverTypeAdapter())
                    .setPrettyPrinting()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();

        for(Map.Entry<String, Object> classEntry : classFileMap.entrySet()) { // Traverse thru all the ClassFile Objects passed to this module for persisting
            traverseJsonObj(gson.fromJson(gson.toJson(classEntry.getValue()), JsonElement.class).getAsJsonObject(),0);
        }

        return this.retString;
    }


    private void traverseJsonObj(JsonElement jObj, int level) {

        for(Map.Entry<String, JsonElement> entry : jObj.getAsJsonObject().entrySet()) {
            if(getType(jObj.getAsJsonObject().get(entry.getKey())).equals("Primitive")) {
                this.retString += repeatTabs(level) + entry.getKey()+" : "+entry.getValue().getAsString() + "\n";
            } else if (getType( jObj.getAsJsonObject().get(entry.getKey())).equals("Array")) {
                this.retString += repeatTabs(level) + entry.getKey()+" : " + "\n";
                entry.getValue().getAsJsonArray().forEach(eachArrayElem -> {
                    if(getType(eachArrayElem).equals("Primitive")) {
                        this.retString += repeatTabs(level+1) + eachArrayElem.getAsString() + "\n" ;
                    } else {
                        traverseJsonObj(eachArrayElem, level+1);
                    }
                });
            } else if(getType( jObj.getAsJsonObject().get(entry.getKey())).equals("Object")) {
                traverseJsonObj(jObj.getAsJsonObject(),level+1);
            }
        }
    }

    private String repeatTabs(int times) {
        String retTabs = "" ;

        for(int i = 0 ; i < times ; i++) {
            retTabs += "\t" ;
        }

        return retTabs;
    }

    private String getType(JsonElement jElem) {
        String type = "" ;

        if(jElem.isJsonObject()) {
            type = "Object";
        } else if(jElem.isJsonArray()) {
            type = "Array" ;
        } else if (jElem.isJsonPrimitive()) {
            type = "Primitive" ;
        } else if (jElem.isJsonNull()) {
            type = "null" ;
        }

        return type;
    }
}