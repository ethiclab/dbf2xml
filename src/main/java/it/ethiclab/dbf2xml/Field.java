package it.ethiclab.dbf2xml;

import javax.xml.bind.annotation.XmlAttribute;

public class Field {

    private String name;
    private String value;

    public Field() {
        // required default constructor.
    }

    public Field(String name, String value) {
        this.setName(name);
        this.setValue(value);
    }

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    @XmlAttribute
    public void setValue(String value) {
        this.value = value;
    }
}
