package com.research.veracode.classextractor;

import com.research.veracode.classextractor.dto.*;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.BasicType;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class ClassFileExtractor {
    private Map<String, Object> yamlConfig = null ;
    private JavaClass javaClass = null;

    /*
    Configure what data needs to be extracted from class files, by passing in InputStream of Yaml file.
    @param configYamlFile contains configuration of all elements from classfiles/properties/methods which we wish to include in output file.
     */
    public ClassFileExtractor(InputStream configYamlFile) {

        this.yamlConfig = new Yaml().load(configYamlFile);
        //System.out.println(this.yamlConfig);
    }

    /*
    This function returns populated ClassFile object based on yaml configuration file.

    @param classFileInputStream take InputStream of a single class file
    @param className is the class name of InputStream class file.

    @return Populated ClassFile object with all details extracted based on input yaml configuration file.
     */
    public  ClassFile getClassFileData(InputStream classFileInputStream, String className) {
        //ClassFile classFile = new ClassFile() ;
        parse(classFileInputStream, className);
        return populateClassFileObject();

        //classFile.setName(this.javaClass.getClassName());

        //return classFile;
    }

    /*
    Parse inputstream of class file from which information needs to be extracted. This is based on Apache BCEL library
     */
    private void parse(InputStream classfileInputStream, String className) {
        ClassParser classParser = new ClassParser(classfileInputStream, className);
        //System.out.println("parse: Class Name " + className);

        try {
            this.javaClass = classParser.parse();
        } catch (IOException e) {
            //System.out.println("Exception: While trying to parse " + className + " " + e.getMessage());
        }
    }

    private ClassFile populateClassFileObject() {
        ClassFile classFile = new ClassFile();

        // Populating Class File Name
        classFile.setClassName(this.javaClass.getClassName());

        classFile.getClassAccessModifiers().addAll(getAccessModifiers(this.javaClass));

        // Populating All interfaces being implemented by this class.
        if(this.javaClass.getInterfaceNames().length > 0) {
            for(String interfaceNames : this.javaClass.getInterfaceNames()) {
                classFile.getImplementsList().add(interfaceNames);
            }
        }

        // Populating Extending class if any, else BCEL gives java.lang.Object. Java doesn't support multiple inheritance, so no need for a List.
        classFile.setInherits(this.javaClass.getSuperclassName());

        // Populating Major and Minor versions of the class file being compiled in.
        classFile.setMajorNumber(this.javaClass.getMajor());

        classFile.setMinorNumber(this.javaClass.getMinor());


        // Populating Class level Annotations
        if(this.javaClass.getAnnotationEntries().length > 0) {
            for(AnnotationEntry annotation : this.javaClass.getAnnotationEntries()) {
                    classFile.getAnnotations().add(getAnnotationDetails(annotation));
            }
        }

        // Populating Class Properties Details:
        Field[] fields = this.javaClass.getFields();
        if(fields.length > 0) {
            for(Field field : fields) {
                classFile.getPropertiesList().add(getPropertyDetails(field));
            }
        }

        // Populating Method details
        Method[] methods = this.javaClass.getMethods();

        if(methods.length > 0){
            for(Method method : methods) {
                MethodData methodData = new MethodData();

                // Method name
                methodData.setMethodName(method.getName());

                // Access Modifiers on the method
                methodData.getMethodAccessModifiers().addAll(getAccessModifiers(method));

                // Return type
                methodData.setReturnType(method.getReturnType().toString());

                // Exceptions returned by the method
                if(method.getExceptionTable() != null) {
                    for(String exceptioName : method.getExceptionTable().getExceptionNames()) {
                        methodData.getExceptions().add(exceptioName);
                    }
                }

                // Method level annotations
                if(method.getAnnotationEntries().length > 0) {
                    for(AnnotationEntry entry : method.getAnnotationEntries()) {
                        methodData.getAnnotations().add(getAnnotationDetails(entry));
                    }
                }

                // Arguments of this method
                if(method.getLocalVariableTable() != null) {
                    MethodArguments methodArguments = new MethodArguments();

                    for(LocalVariable localVariable : method.getLocalVariableTable().getLocalVariableTable()) {
                        // getPropertyDetails won't work here, since Field and Method both are of type FieldOrMethod class... not subclassing. So have to construct this one manually.
                        methodArguments.setMethodArgumentName(localVariable.getName());
                        methodArguments.setMethodArgumentType(localVariable.getSignature().replaceFirst("L","").replaceAll("/","."));
                        if(method.getParameterAnnotationEntries().length > 0) {
                            for(ParameterAnnotationEntry parameterAnnotationEntry : method.getParameterAnnotationEntries()) {
                                for(AnnotationEntry annotationEntry : parameterAnnotationEntry.getAnnotationEntries()) {
                                    methodArguments.getAnnotations().add(getAnnotationDetails(annotationEntry));
                                }
                            }
                        }

                        methodData.getMethodArguments().add(methodArguments);
                    }
                }
                classFile.getMethodList().add(methodData);
            }
        }

        return classFile;
    }

    private PropertiesData getPropertyDetails(FieldOrMethod fieldOrMethod) {
        PropertiesData propertiesData = new PropertiesData();

        propertiesData.setPropertyName(fieldOrMethod.getName().replaceAll("/",".").replaceAll(";",""));
        propertiesData.setPropertyAccessModifiers(getAccessModifiers(fieldOrMethod));
        for(AnnotationEntry annotationEntry : fieldOrMethod.getAnnotationEntries()) {
            propertiesData.getAnnotations().add(getAnnotationDetails(annotationEntry));
        }

        return propertiesData;
    }


    private AnnotationData getAnnotationDetails(AnnotationEntry annotationEntry) {

        AnnotationData annotationData = new AnnotationData();

        // annotationEntry.getAnnotationType() returns array representation (starts with L), and toString doesn't seem to be doing the job. Thus, the patch below
        annotationData.setName(annotationEntry.getAnnotationType().replaceFirst("L","").replaceAll("/",".").replaceAll(";",""));

        for(ElementValuePair elementValuePair : annotationEntry.getElementValuePairs()) {
            HashMap<String, String> elementMap = new HashMap<String, String>();
            elementMap.put(elementValuePair.getNameString(), elementValuePair.getValue().stringifyValue().replaceAll("/","."));
            annotationData.getElements().add(elementMap);
        }

        return annotationData;
    }

    private List<String> getAccessModifiers(AccessFlags accessFlags) {
        Set<String> accessModifiers = new LinkedHashSet<String>();

        if(accessFlags.isAbstract()) {
            accessModifiers.add("abstract");
        } else if(accessFlags.isPrivate()) {
            accessModifiers.add("private");
        } else if(accessFlags.isAnnotation()){
            accessModifiers.add("annotation");
        } else if(accessFlags.isEnum()) {
            accessModifiers.add("enum");
        } else if(accessFlags.isFinal()) {
            accessModifiers.add("final");
        } else if(accessFlags.isInterface()) {
            accessModifiers.add("interface");
        } else if(accessFlags.isNative()) {
            accessModifiers.add("native");
        } else if(accessFlags.isPublic()) {
            accessModifiers.add("public");
        } else if(accessFlags.isProtected()) {
            accessModifiers.add("protected");
        } else if(accessFlags.isStatic()) {
            accessModifiers.add("static");
        }

        return new ArrayList<>(accessModifiers);
    }
}