package com.research.veracode.classextractor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassFile implements Serializable {


    // fully-qualified class name. i.e. packagename.class
    private String ClassName = null ;


    // All access modifiers on the class. i.e. public final MyClass {}, should give a list of strings with "public" and "final" in it.
    private List<String> classAccessModifiers = new ArrayList<String>();

    // Class which current class extends. Java doesn't support multiple inheritance... So no need of a list
    String inherits = null;

    // All interfaces which are implemented by current class.
    List<String> implementsList = new ArrayList<String>();

    // Major version current class is compiled with Ref: https://en.wikipedia.org/wiki/Java_class_file#General_layout
    int majorNumber = 0;
    int minorNumber = 0 ;

    List<AnnotationData> annotations = new ArrayList<AnnotationData>();

    private List<PropertiesData> propertiesList = new ArrayList<PropertiesData>();


    private List<MethodData> methodList = new ArrayList<MethodData>();


    public int getMajorNumber() {
        return majorNumber;
    }

    public void setMajorNumber(int majorNumber) {
        this.majorNumber = majorNumber;
    }

    public int getMinorNumber() {
        return minorNumber;
    }

    public void setMinorNumber(int minorNumber) {
        this.minorNumber = minorNumber;
    }

    public List<String> getClassAccessModifiers() {
        return classAccessModifiers;
    }

    public void setClassAccessModifiers(List<String> classAccessModifiers) {
        this.classAccessModifiers = classAccessModifiers;
    }

    public String getInherits() {
        return inherits;
    }

    public void setInherits(String inherits) {
        this.inherits = inherits;
    }


    public List<String> getImplementsList() {
        return implementsList;
    }

    public void setImplementsList(List<String> implementsList) {
        this.implementsList = implementsList;
    }

    public List<AnnotationData> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationData> annotations) {
        this.annotations = annotations;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public List<PropertiesData> getPropertiesList() {
        return propertiesList;
    }

    public void setPropertiesList(List<PropertiesData> propertiesList) {
        this.propertiesList = propertiesList;
    }

    public List<MethodData> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<MethodData> methodList) {
        this.methodList = methodList;
    }


    @Override
    public String toString() {
        return "Class Name : " + getClassName() ;
    }

}
