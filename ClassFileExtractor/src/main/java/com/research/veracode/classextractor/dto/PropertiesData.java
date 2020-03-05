package com.research.veracode.classextractor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
This POJO holds properties details of both class level properties and method arguments.
Apache BCEL reference of getFields, is used interchangeably as properties.
 */
public class PropertiesData implements Serializable {

    private String PropertyName = null;
    private List<String> propertyAccessModifiers = new ArrayList<String>();
    private List<AnnotationData> annotations = new ArrayList<AnnotationData>();

    public String getPropertyName() {
        return PropertyName;
    }

    public List<AnnotationData> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationData> annotations) {
        this.annotations = annotations;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public List<String> getPropertyAccessModifiers() {
        return propertyAccessModifiers;
    }

    public void setPropertyAccessModifiers(List<String> propertyAccessModifiers) {
        this.propertyAccessModifiers = propertyAccessModifiers;
    }


    @Override
    public String toString() {
        return "Property name : " + getPropertyName();

    }
}
