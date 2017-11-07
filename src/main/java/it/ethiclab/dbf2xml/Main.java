package it.ethiclab.dbf2xml;

public class Main {
	private Main() {
    	throw new IllegalAccessError("Main is not instantiable.");
  	}

	public static void main(String[] args) throws Exception {
		ArgumentReader argumentReader = new ArgumentReader(args);
		String entityName = argumentReader.readArg("entityName");
		String sourceType = argumentReader.readArg("sourceType");
		String sourceLocation = argumentReader.readArg("sourceLocation");
		String destinationType = argumentReader.readArg("destinationType");
		String destinationLocation = argumentReader.readArg("destinationLocation");
		DataReader reader = (DataReader) Main.class.getClassLoader()
			.loadClass(sourceType).newInstance();
		DataWriter writer = (DataWriter) Main.class.getClassLoader()
			.loadClass(destinationType).newInstance();
		Data data = reader.read(entityName, sourceLocation);
		writer.write(entityName, destinationLocation, data);
	}
}
