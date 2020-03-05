# Basic Project Layout
- Created a dedicated module OutputGenerator, which takes an arraylist of classfile objects with all loaded data per class file interrogated, and generates requested file format. Can add additional constrcutor when more formats are added
        - No Jackson or any output related libraries to be included in any other parts of this project
- Created a dedicated module for extracting required information from a Class file. This module's entry point would take yaml config file and Input Stream object of a single class file for interrogation. It will return a populated ClassFile Object.
        - No Apache BCEL related librraies to be included in any other parts of the project
- Engine class, will create an array list with each *needed* file, as key and corresponding ClassFile object with populated data. For non-class files, value could be null or whatever.

# Assumptions:
- POJO field names mapping to output ? Using Gson's [Field Naming Strategy](https://www.javadoc.io/doc/com.google.code.gson/gson/2.6/com/google/gson/FieldNamingPolicy.html#UPPER_CAMEL_CASE) for field mapping. So, for any future POJO field additions choose accordingly.
- Not generating a ToC of input jar file at the moment. Just looking for .class files.
