package it.ethiclab.dbf2xml;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class DataToJdbcReaderWriterTest {

    @Test
    public void testException() throws Exception {
        assertThatThrownBy(() -> new DataToJdbcReaderWriter().write(null, null, null))
            .isInstanceOf(ApplicationRuntimeException.class);
    }
}
