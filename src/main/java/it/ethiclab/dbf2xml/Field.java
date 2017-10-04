package it.ethiclab.dbf2xml;

import javax.xml.bind.annotation.XmlAttribute;

public class Field {

    private String name;
    private Object value;

    public Field() {

    }

    public Field(String name, Object value) {
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
