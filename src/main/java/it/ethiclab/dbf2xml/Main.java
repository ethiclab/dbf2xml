package it.ethiclab.dbf2xml;

public class Main {
	   public static void main(String[] args) throws Exception {
		      System.out.println(new DataToXmlWriter().write(new DbfToDataReader().read(args[0])));
	   }
}
