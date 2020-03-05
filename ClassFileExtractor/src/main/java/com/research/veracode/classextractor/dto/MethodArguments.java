package com.research.veracode.classextractor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MethodArguments implements Serializable {
    private String methodArgumentName = null ;

    /*
    Class of the argument, for e.g. Argument: HttpServletRequest request, will give us argumentType as javax/servlet/http/HttpServletRequest
    */
    private String methodArgumentType = "";

    private List<AnnotationData> annotations = new ArrayList<AnnotationData>();


    public List<AnnotationData> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationData> annotations) {
        this.annotations = annotations;
    }

    public String getMethodArgumentName() {
        return methodArgumentName;
    }

    public void setMethodArgumentName(String methodArgumentName) {
        this.methodArgumentName = methodArgumentName;
    }

    public String getMethodArgumentType() {
        return methodArgumentType;
    }

    public void setMethodArgumentType(String methodArgumentType) {
        this.methodArgumentType = methodArgumentType;
    }

    @Override
    public String toString() {
        return "Method Argument name : " + getMethodArgumentName();

    }
}
