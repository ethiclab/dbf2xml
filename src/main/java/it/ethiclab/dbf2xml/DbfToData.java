package it.ethiclab.dbf2xml;

import com.linuxense.javadbf.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class DbfToData {

    private void resolveMemoFile(DBFReader reader, File dbf) {
        String base = dbf.getName().substring(0, dbf.getName().length() - 4);
        String suffix = dbf.getName().substring(dbf.getName().length() - 4);
        char ch = suffix.charAt(suffix.length() - 1);
        suffix = suffix.substring(0, 3) + (Character.isUpperCase(ch) ? 'T' : 't');
        String dbtDefaultName = base + suffix;
        File autodbt = new File(Paths.get(dbf.getAbsoluteFile().getParentFile().getAbsolutePath(), dbtDefaultName).toString());
        if (autodbt.exists()) {
            reader.setMemoFile(autodbt);
        }
    }

    public Data acquire(String dbfname, String dbtname) throws IOException {
        Data data = new Data();
        File dbf = new File(dbfname);
        String enc = "windows-1252";
        DBFReader reader = new DBFReader(new FileInputStream(dbf), Charset.forName(enc), false);
        try {
            if (dbtname != null) {
                reader.setMemoFile(new File(dbtname));
            } else if (dbf.getName().toUpperCase().endsWith(".DBF")) {
                resolveMemoFile(reader, dbf);
            }

            int numberOfFields = reader.getFieldCount();
            int numberOfRecords = reader.getRecordCount();

            for (int i = 0; i < numberOfRecords; i++) {
                Row row = new Row();
                Object[] rowObjects = reader.nextRecord();
                for (int j = 0; j < numberOfFields; j++) {
                    DBFField field = reader.getField(j);
                    Object obj = rowObjects[j];
                    String s = cleanTextContent(obj == null ? null : obj.toString());
                    Field f = new Field(field.getName(), s);
                    row.getFields().add(f);
                }
                data.getRows().add(row);
            }
            return data;
        } finally {
            DBFUtils.close(reader);
        }
    }

    /**
     * @credits: https://howtodoinjava.com/regex/java-clean-ascii-text-non-printable-chars/
     */
    private static String cleanTextContent(String text) {
        if (text == null) {
            return null;
        }
        return text
        .replaceAll("\r\n", "\n")
        .replaceAll("\r", "\n")
        // strips off all non-ASCII characters
        .replaceAll("[^\\x00-\\x7F\n\t]", "")
        // removes non-printable characters from Unicode
        .replaceAll("\\p{C}&&[^\n\t]", "")
        // erases all the ASCII control characters
        .replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "")
        .trim();
    }
}
