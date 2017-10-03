package it.ethiclab.dbf2xml;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import com.linuxense.javadbf.*;

public class Main {
	public static void main(String args[]) throws IOException {
		DBFReader reader = null;
        try {
            File dbf = new File(args[0]);
            reader = new DBFReader(new FileInputStream(dbf), Charset.forName("windows-1252"), false);
            if (args.length > 1) {
                reader.setMemoFile(new File(args[1]));
            } else if (dbf.getName().toUpperCase().endsWith(".DBF")) {
                String base = dbf.getName().substring(0, dbf.getName().length() - 4);
                String suffix = dbf.getName().substring(dbf.getName().length() - 4);
                char ch = suffix.charAt(suffix.length() - 1);
                suffix = suffix.substring(0, 3) + (Character.isUpperCase(ch) ? 'T' : 't');
                String dbtDefaultName = base + suffix;
                File dbt = new File(Paths.get(dbf.getParentFile().getAbsolutePath(), dbtDefaultName).toString());
                if (dbt.exists()) {
                    reader.setMemoFile(dbt);
                }
            }

            int numberOfFields = reader.getFieldCount();
            int numberOfRecords = reader.getRecordCount();

            for (int i = 0; i < numberOfRecords - 1; i++) {
                System.out.println(Integer.toString(i));
                Object[] rowObjects = reader.nextRecord();
                for (int j = 0; j < numberOfFields; j++) {
                    DBFField field = reader.getField(j);
                    System.out.println(i + " " + field.getName() + " = [" + rowObjects[j] + "]");
                }
            }
		} finally {
			DBFUtils.close(reader);
		}
	}
}
