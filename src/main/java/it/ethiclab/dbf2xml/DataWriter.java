package it.ethiclab.dbf2xml;

@FunctionalInterface
public interface DataWriter {
    public void write(String entityName, String destinationSpecs, Data source);
}
