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

            for (int i = 0; i < numberOfRecords; i++) {
                Row row = new Row();
                Object[] rowObjects = reader.nextRecord();
                for (int j = 0; j < numberOfFields; j++) {
                    DBFField field = reader.getField(j);
if (rowObjects != null) {
                    Object obj = rowObjects[j];
                    String s = cleanTextContent(obj == null ? null : obj.toString());
                    Field f = new Field(field.getName(), s);
                    row.getFields().add(f);
} else {
    System.err.println("Error reading row " + i);
}
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

    /**
     * @credits: https://howtodoinjava.com/regex/java-clean-ascii-text-non-printable-chars/
     */
    private static String cleanTextContent(String text)
    {
        if (text == null) {
            return null;
        }

        text = text.replaceAll("\r\n", "\n");
        text = text.replaceAll("\r", "\n");

        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F\n\t]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}&&[^\n\t]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        return text.trim();
    }
}
