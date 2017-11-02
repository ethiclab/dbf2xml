package it.ethiclab.dbf2xml;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class ArgumentReaderTest {

    @Test
    public void testNoArgs() {
        ArgumentReader ar = new ArgumentReader(new String[] {});
        assertThat(ar.readArg()).isNull();
        assertThat(ar.readArg()).isNull();
        assertThat(ar.readArg()).isNull();
    }

    @Test
    public void testOneArgs() {
        ArgumentReader ar = new ArgumentReader(new String[] {"hello"});
        assertThat(ar.readArg()).isEqualTo("hello");
        assertThat(ar.readArg()).isNull();
        assertThat(ar.readArg()).isNull();
    }
}
