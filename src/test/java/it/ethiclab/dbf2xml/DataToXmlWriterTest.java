package it.ethiclab.dbf2xml;

import java.io.*;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class DataToXmlWriterTest {

    @Test
    public void testException() throws Exception {
        assertThatThrownBy(() -> new DataToXmlWriter().write(null))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testFileDoesNotExist() throws Exception {
        assertThat(new DataToXmlWriter().write(new Data()))
            .isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<data/>\n");
    }
}
