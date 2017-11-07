package it.ethiclab.dbf2xml;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class ArgumentReaderTest {

    @Test
    public void testNoArgs() {
        ArgumentReader ar = new ArgumentReader(new String[] {});
        assertThatThrownBy(() -> ar.readArg("abc")).hasMessage("abc is mandatory.");
    }

    @Test
    public void testOneArgs() {
        ArgumentReader ar = new ArgumentReader(new String[] {"hello"});
        assertThat(ar.readArg("abc")).isEqualTo("hello");
        assertThatThrownBy(() -> ar.readArg("xyz")).hasMessage("xyz is mandatory.");
    }
}
