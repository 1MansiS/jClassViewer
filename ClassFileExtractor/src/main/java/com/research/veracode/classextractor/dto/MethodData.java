package com.research.veracode.classextractor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MethodData implements Serializable {

    private String methodName = null ;

    private List<String> methodAccessModifiers = new ArrayList<String>();

    private String returnType = null ;

    private List<String> exceptions = new ArrayList<String>();

    private List<AnnotationData> annotations = new ArrayList<AnnotationData>();


    private List<MethodArguments> methodArguments = new ArrayList<MethodArguments>() ;

    public List<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
    }

    public List<AnnotationData> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationData> annotations) {
        this.annotations = annotations;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getMethodAccessModifiers() {
        return methodAccessModifiers;
    }

    public void setMethodAccessModifiers(List<String> methodAccessModifiers) {
        this.methodAccessModifiers = methodAccessModifiers;
    }

    public List<MethodArguments> getMethodArguments() {
        return methodArguments;
    }

    public void setMethodArguments(List<MethodArguments> methodArguments) {
        this.methodArguments = methodArguments;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }


    @Override
    public String toString() {
        return "Name : " + getMethodName() +
                "Access Modifiers : " ;



    }

}
