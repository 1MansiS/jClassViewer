package com.research.veracode.classextractor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Archive implements Serializable {

    private String ArchiveName = null;
    private List<ClassFile> ClassFiles = new ArrayList<ClassFile>();

    public List<ClassFile> getListOfClassFileDetails() {
        return ClassFiles;
    }

    public void setListOfClassFileDetails(List<ClassFile> listOfClassFileDetails) {
        this.ClassFiles = listOfClassFileDetails;
    }

    public String getArchiveName() {
        return ArchiveName;
    }

    public void setArchiveName(String archiveName) {
        this.ArchiveName = archiveName;
    }


}
