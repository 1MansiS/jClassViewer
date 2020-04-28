package com.veracode.research.outputgenerator.format;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class XMLGenerator implements OutputFormatGenerator {
    @Override
    public String generateOutput(Object outputData) {
        XStream xstream = new XStream(new StaxDriver());

        final Writer writer = new StringWriter();

        xstream.marshal(outputData, new PrettyPrintWriter(writer));

        return writer.toString().replaceAll("com.research.veracode.classextractor.dto.","");
    }
}