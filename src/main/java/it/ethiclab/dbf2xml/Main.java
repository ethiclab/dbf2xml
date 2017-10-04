package it.ethiclab.dbf2xml;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import com.linuxense.javadbf.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Main {
	public static void main(String args[]) throws Exception {
		DBFReader reader = null;
        try {
            String enc = "windows-1252";
            Data data = new Data();
            File dbf = new File(args[0]);
            reader = new DBFReader(new FileInputStream(dbf), Charset.forName(enc), false);
            if (args.length > 1) {
                reader.setMemoFile(new File(args[1]));
            } else if (dbf.getName().toUpperCase().endsWith(".DBF")) {
                String base = dbf.getName().substring(0, dbf.getName().length() - 4);
                String suffix = dbf.getName().substring(dbf.getName().length() - 4);
                char ch = suffix.charAt(suffix.length() - 1);
                suffix = suffix.substring(0, 3) + (Character.isUpperCase(ch) ? 'T' : 't');
                String dbtDefaultName = base + suffix;
                File dbt = new File(Paths.get(dbf.getAbsoluteFile().getParentFile().getAbsolutePath(), dbtDefaultName).toString());
                if (dbt.exists()) {
                    reader.setMemoFile(dbt);
                }
            }

            int numberOfFields = reader.getFieldCount();
            int numberOfRecords = reader.getRecordCount();

            for (int i = 0; i < numberOfRecords - 1; i++) {
                Row row = new Row();
                Object[] rowObjects = reader.nextRecord();
                for (int j = 0; j < numberOfFields; j++) {
                    DBFField field = reader.getField(j);
                    Field f = new Field(field.getName(), rowObjects[j]);
                    row.getFields().add(f);
                }
                data.getRows().add(row);
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
            jaxbMarshaller.marshal(data, System.out );
        } finally {
			DBFUtils.close(reader);
		}
	}
}
