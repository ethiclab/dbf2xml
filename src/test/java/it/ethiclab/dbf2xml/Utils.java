package it.ethiclab.dbf2xml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
    public static String readFile(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
