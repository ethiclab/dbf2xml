package it.ethiclab.dbf2xml;

import java.io.*;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class DbfToDataTest {

    @Test
    public void testFileDoesNotExist() {
        assertThatThrownBy(() -> new DbfToDataReader().read(null, "src/test/resources/doesnotexist.dbf"))
        .isInstanceOf(ApplicationRuntimeException.class)
        .hasCause(new FileNotFoundException("src/test/resources/doesnotexist.dbf (No such file or directory)"));
    }

    @Test
    public void testGoodFileWithAutomaticMemo() throws Exception {
        assertThat(new DbfToDataReader().read(null, "src/test/resources/dbase_8b.dbf"))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithAutomaticMemoUppercase() throws Exception {
        assertThat(new DbfToDataReader().read(null, "src/test/resources/dbase_8b1.DBF"))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithSpecifiedMemo() throws Exception {
        assertThat(new DbfToDataReader().read(null, "src/test/resources/dbase_8b.dbf"))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithDifferentExtension() throws Exception {
        assertThat(new DbfToDataReader().read(null, "src/test/resources/dbase_8b.dbf2"))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithoutAutomaticMemo() throws Exception {
        assertThat(new DbfToDataReader().read(null, "src/test/resources/dbase_8b0.dbf"))
        .isNotNull();
    }
}
