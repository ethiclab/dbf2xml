package it.ethiclab.dbf2xml;

import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class DataToXmlWriter {
    public String write(Data data) throws Exception {
        final JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
    		  final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    		  jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        final StringWriter sw = new StringWriter();
    		  jaxbMarshaller.marshal(data, sw);
        return sw.toString();
    }
}
