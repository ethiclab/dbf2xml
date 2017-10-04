package it.ethiclab.dbf2xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Data {
    private final List<Row> rows = new ArrayList<>();

    @XmlElement(name="row")
    public List<Row> getRows() {
        return rows;
    }
}
