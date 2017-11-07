package it.ethiclab.dbf2xml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.io.*;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import java.sql.*;
import java.util.concurrent.atomic.*;

public class MainTest {
    @Test
    public void testCannotInstantiate() throws Exception {
        Constructor<Main> c = Main.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThatThrownBy(() -> c.newInstance())
        .isInstanceOf(InvocationTargetException.class)
        .hasCause(new IllegalAccessError("Main is not instantiable."));
    }

    @Test
    public void testMainWithXmlWriter() throws Exception {
        File f = new File("target/dbase_8b.xml");
        f.delete();
        assertThat(f).doesNotExist();
        Main.main(new String[]{
            "dbase_8b",
            "it.ethiclab.dbf2xml.DataToXmlWriter",
            "target/dbase_8b.xml",
            "src/test/resources/dbase_8b.dbf"
        });
        assertThat(f).exists();
        assertThat(Utils.readFile(f.getAbsolutePath())).isEqualTo(Utils.readFile("src/test/resources/dbase_8b.xml"));
    }

    @Test
    public void testMainWithJdbcWriter() throws Exception {
        System.setProperty("jdbc.drivers", "org.sqlite.JDBC");
        File f = new File("target/test.db");
        f.delete();
        assertThat(f).doesNotExist();
        String url = "jdbc:sqlite:target/test.db";
        Main.main(new String[]{
            "dbase_8b",
            "it.ethiclab.dbf2xml.DataToJdbcWriter",
            url,
            "src/test/resources/dbase_8b.dbf"
        });
        assertThat(f).exists();
        AtomicInteger a = new AtomicInteger();
        try (Connection cn = DriverManager.getConnection(url);
        Statement stmt = cn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from dbase_8b");
        ) {
            while(rs.next()) {
                a.incrementAndGet();
                switch (a.get()) {
                    case 1:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNotNull();
                    assertThat(rs.getString("LOGICAL")).isNotNull();
                    assertThat(rs.getString("FLOAT")).isNotNull();
                    assertThat(rs.getString("MEMO")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("One");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("1.00");
                    assertThat(rs.getString("DATE")).isEqualTo("Thu Jan 01 00:00:00 CET 1970");
                    assertThat(rs.getString("LOGICAL")).isEqualTo("true");
                    assertThat(rs.getString("FLOAT")).isEqualTo("1.234567890123460000");
                    assertThat(rs.getString("MEMO")).isEqualTo("First memo");
                    break;
                    case 2:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNotNull();
                    assertThat(rs.getString("LOGICAL")).isNotNull();
                    assertThat(rs.getString("FLOAT")).isNotNull();
                    assertThat(rs.getString("MEMO")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("Two");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("2.00");
                    assertThat(rs.getString("DATE")).isEqualTo("Thu Dec 31 00:00:00 CET 1970");
                    assertThat(rs.getString("LOGICAL")).isEqualTo("true");
                    assertThat(rs.getString("FLOAT")).isEqualTo("2.000000000000000000");
                    assertThat(rs.getString("MEMO")).isEqualTo("Second memo");
                    break;
                    case 3:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNotNull();
                    assertThat(rs.getString("LOGICAL")).isNull();
                    assertThat(rs.getString("FLOAT")).isNotNull();
                    assertThat(rs.getString("MEMO")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("Three");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("3.00");
                    assertThat(rs.getString("DATE")).isEqualTo("Tue Jan 01 00:00:00 CET 1980");
                    assertThat(rs.getString("FLOAT")).isEqualTo("3.000000000000000000");
                    assertThat(rs.getString("MEMO")).isEqualTo("Third memo");
                    break;
                    case 4:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNotNull();
                    assertThat(rs.getString("LOGICAL")).isNull();
                    assertThat(rs.getString("FLOAT")).isNotNull();
                    assertThat(rs.getString("MEMO")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("Four");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("4.00");
                    assertThat(rs.getString("DATE")).isEqualTo("Mon Jan 01 00:00:00 CET 1900");
                    assertThat(rs.getString("FLOAT")).isEqualTo("4.000000000000000000");
                    assertThat(rs.getString("MEMO")).isEqualTo("Fourth memo");
                    break;
                    case 5:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNotNull();
                    assertThat(rs.getString("LOGICAL")).isNull();
                    assertThat(rs.getString("FLOAT")).isNotNull();
                    assertThat(rs.getString("MEMO")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("Five");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("5.00");
                    assertThat(rs.getString("DATE")).isEqualTo("Mon Dec 31 00:00:00 CET 1900");
                    assertThat(rs.getString("FLOAT")).isEqualTo("5.000000000000000000");
                    assertThat(rs.getString("MEMO")).isEqualTo("Fifth memo");
                    break;
                    case 6:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNotNull();
                    assertThat(rs.getString("LOGICAL")).isNull();
                    assertThat(rs.getString("FLOAT")).isNotNull();
                    assertThat(rs.getString("MEMO")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("Six");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("6.00");
                    assertThat(rs.getString("DATE")).isEqualTo("Tue Jan 01 00:00:00 CET 1901");
                    assertThat(rs.getString("FLOAT")).isEqualTo("6.000000000000000000");
                    assertThat(rs.getString("MEMO")).isEqualTo("Sixth memo");
                    break;
                    case 7:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNotNull();
                    assertThat(rs.getString("LOGICAL")).isNull();
                    assertThat(rs.getString("FLOAT")).isNotNull();
                    assertThat(rs.getString("MEMO")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("Seven");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("7.00");
                    assertThat(rs.getString("DATE")).isEqualTo("Fri Dec 31 00:00:00 CET 1999");
                    assertThat(rs.getString("FLOAT")).isEqualTo("7.000000000000000000");
                    assertThat(rs.getString("MEMO")).isEqualTo("Seventh memo");
                    break;
                    case 8:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNotNull();
                    assertThat(rs.getString("LOGICAL")).isNull();
                    assertThat(rs.getString("FLOAT")).isNotNull();
                    assertThat(rs.getString("MEMO")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("Eight");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("8.00");
                    assertThat(rs.getString("DATE")).isEqualTo("Wed Dec 31 00:00:00 CET 1919");
                    assertThat(rs.getString("FLOAT")).isEqualTo("8.000000000000000000");
                    assertThat(rs.getString("MEMO")).isEqualTo("Eighth memo");
                    break;
                    case 9:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNull();
                    assertThat(rs.getString("LOGICAL")).isNull();
                    assertThat(rs.getString("FLOAT")).isNull();
                    assertThat(rs.getString("MEMO")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("Nine");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("9.00");
                    assertThat(rs.getString("MEMO")).isEqualTo("Ninth memo");
                    break;
                    case 10:
                    assertThat(rs.getString("ID")).isNotNull();
                    assertThat(rs.getString("CHARACTER")).isNotNull();
                    assertThat(rs.getString("NUMERICAL")).isNotNull();
                    assertThat(rs.getString("DATE")).isNull();
                    assertThat(rs.getString("LOGICAL")).isNull();
                    assertThat(rs.getString("FLOAT")).isNotNull();
                    assertThat(rs.getString("MEMO")).isNull();
                    assertThat(rs.getString("CHARACTER")).isEqualTo("Ten records stored in this database");
                    assertThat(rs.getString("NUMERICAL")).isEqualTo("10.00");
                    assertThat(rs.getString("FLOAT")).isEqualTo("0.100000000000000000");
                }
            }
        }
        assertThat(a.get()).isEqualTo(10);
    }
}
