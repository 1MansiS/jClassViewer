package com.veracode.research.outputgenerator.format;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class XMLGenerator implements OutputFormatGenerator {
    @Override
    public String generateOutput(Map<String, Object> outputData) {
        return "xml";
    }

    public String hello() {
        return "Hello World";
    }

    public String generateXML(Object objData) {
        //String xmlString = "" ;

        XStream xstream = new XStream(new StaxDriver());
        final Writer writer = new StringWriter();
        xstream.marshal(objData, new CompactWriter(writer));

        return writer.toString();
    }


    public void generateXMLTemp(Object outputData) {
        Object output = outputData;

        //System.out.println(output.getClass().getCanonicalName());

        Method[] methods = output.getClass().getMethods();

        for (Method method : methods) {
            if(method.getName().startsWith("get")) {
                try {
                    System.out.println("Method Name : " + method.getName() + " value : " + method.invoke(outputData) + " Return Type : " + method.getReturnType() );
                    if(method.getReturnType().isArray()) {
                        System.out.println(Array.get(method.invoke(outputData), 0));

                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }



            //try {
                //System.out.println("Method : " + method.getName());
            /*} catch (IllegalAccessException e) {
                e.printStackTrace();
            }*/
        }
    }
}