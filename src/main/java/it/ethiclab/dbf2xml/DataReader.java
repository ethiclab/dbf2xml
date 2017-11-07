package it.ethiclab.dbf2xml;

@FunctionalInterface
public interface DataReader {
    public Data read(String entityName, String sourceSpecs);
}
