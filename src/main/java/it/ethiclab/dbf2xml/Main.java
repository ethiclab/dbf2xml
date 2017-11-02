package it.ethiclab.dbf2xml;

public class Main {
	private Main() {
    	throw new IllegalAccessError("Main is not instantiable.");
  	}

	public static void main(String[] args) throws Exception {
		ArgumentReader argumentReader = new ArgumentReader(args);
		String entityName = argumentReader.readArg();
		String destinationType = argumentReader.readArg();
		String destinationLocation = argumentReader.readArg();
		String dbf = argumentReader.readArg();
		String dbt = argumentReader.readArg();
		Data data = new DbfToData().acquire(dbf, dbt);
		DataWriter writer = (DataWriter) Main.class.getClassLoader()
			.loadClass(destinationType).newInstance();
		writer.write(entityName, destinationLocation, data);
	}
}
