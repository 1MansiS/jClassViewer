package com.research.veracode.classextractor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
This POJO stores details of annotations applied to classes/methods/properties. Structure referenced from https://docs.oracle.com/javase/tutorial/java/annotations/basics.html
 */
public class AnnotationData implements Serializable {

    /*
    name/type/class of annotation
     */
    private String name = "" ;

    /*
       An annotation can contain:
       - Multiple elements
       - Each element can be named or unnamed
       - each elements value can be a single string or a list of strings
       - Unnamed element's key is assumed to be "value"

       AnnotationEntry class, provides values as string, and not list. Thus a HashMap of String, String should work.
        */
    List<HashMap<String, String>> elements = new ArrayList<HashMap<String, String>>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HashMap<String, String>> getElements() {
        return elements;
    }

    public void setElements(List<HashMap<String, String>> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "Annotation Name : " + getName();

    }


}
