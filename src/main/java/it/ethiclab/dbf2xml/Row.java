package it.ethiclab.dbf2xml;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Row {
    private final List<Field> fields = new ArrayList<>();

    @XmlElement(name="field")
    public List<Field> getFields() {
        return fields;
    }
}
