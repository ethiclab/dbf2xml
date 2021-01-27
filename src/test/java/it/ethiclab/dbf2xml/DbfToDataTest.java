package it.ethiclab.dbf2xml;

import java.io.*;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.Locale;

public class DbfToDataTest {

    static final String OS_NAME = System.getProperty("os.name");
    static final String LOCALE = Locale.getDefault().toString();

    @Test
    public void testFileDoesNotExist() {
        String expectedMessage = "No such file or directory";
        if (LOCALE.compareTo("it_IT") == 0) {
            expectedMessage = "Impossibile trovare il file specificato";
        }
        if (OS_NAME.startsWith("Windows")) {
            assertThatThrownBy(() -> new DbfToDataReader().read("src/test/resources/doesnotexist.dbf"))
            .isInstanceOf(RuntimeException.class)
            .hasCause(new FileNotFoundException("src\\test\\resources\\doesnotexist.dbf (" + expectedMessage + ")"));
            return;
        }
        assertThatThrownBy(() -> new DbfToDataReader().read("src/test/resources/doesnotexist.dbf"))
        .isInstanceOf(RuntimeException.class)
        .hasCause(new FileNotFoundException("src/test/resources/doesnotexist.dbf (" + expectedMessage + ")"));
    }

    @Test
    public void testGoodFileWithAutomaticMemo() throws Exception {
        assertThat(new DbfToDataReader().read("src/test/resources/dbase_8b.dbf"))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithAutomaticMemoUppercase() throws Exception {
        assertThat(new DbfToDataReader().read("src/test/resources/dbase_8b1.DBF"))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithSpecifiedMemo() throws Exception {
        assertThat(new DbfToDataReader().read("src/test/resources/dbase_8b.dbf"))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithDifferentExtension() throws Exception {
        assertThat(new DbfToDataReader().read("src/test/resources/dbase_8b.dbf2"))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithoutAutomaticMemo() throws Exception {
        assertThat(new DbfToDataReader().read("src/test/resources/dbase_8b0.dbf"))
        .isNotNull();
    }
}
