package it.ethiclab.dbf2xml;

import java.io.*;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class DbfToDataTest {

    @Test
    public void testFileDoesNotExist() {
        assertThatThrownBy(() -> new DbfToData().acquire("src/test/resources/doesnotexist.dbf", null))
        .isInstanceOf(FileNotFoundException.class)
        .hasMessage("src/test/resources/doesnotexist.dbf (No such file or directory)");
    }

    @Test
    public void testGoodFileWithAutomaticMemo() throws IOException {
        assertThat(new DbfToData().acquire("src/test/resources/dbase_8b.dbf", null))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithAutomaticMemoUppercase() throws IOException {
        assertThat(new DbfToData().acquire("src/test/resources/dbase_8b1.DBF", null))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithSpecifiedMemo() throws IOException {
        assertThat(new DbfToData().acquire("src/test/resources/dbase_8b.dbf", "src/test/resources/dbase_8b.dbt"))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithDifferentExtension() throws IOException {
        assertThat(new DbfToData().acquire("src/test/resources/dbase_8b.dbf2", null))
        .isNotNull();
    }

    @Test
    public void testGoodFileWithoutAutomaticMemo() throws IOException {
        assertThat(new DbfToData().acquire("src/test/resources/dbase_8b0.dbf", null))
        .isNotNull();
    }
}
