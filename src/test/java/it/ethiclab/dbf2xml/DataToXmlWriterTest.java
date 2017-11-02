package it.ethiclab.dbf2xml;

import java.io.*;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class DataToXmlWriterTest {

    @Test
    public void testException() throws Exception {
        assertThatThrownBy(() -> new DataToXmlWriter().write(null, null, null))
            .isInstanceOf(ApplicationRuntimeException.class);
    }

    @Test
    public void testFileDoesNotExist() throws Exception {
        File f = new File("target/data.xml");
        f.delete();
        assertThat(f).doesNotExist();
        new DataToXmlWriter().write("DATA", f.getAbsolutePath(), new Data());
        assertThat(f).exists();
        assertThat(Utils.readFile(f.getAbsolutePath())).isEqualTo(Utils.readFile("src/test/resources/data.xml"));
    }
}
