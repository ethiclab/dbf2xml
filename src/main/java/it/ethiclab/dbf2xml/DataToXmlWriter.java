package it.ethiclab.dbf2xml;

import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class DataToXmlWriter implements DataWriter {
    @Override
    public void write(String entityName, String destinationSpecs, Data data) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
    		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		jaxbMarshaller.marshal(data, new FileOutputStream(new File(destinationSpecs)));
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
    }
}
