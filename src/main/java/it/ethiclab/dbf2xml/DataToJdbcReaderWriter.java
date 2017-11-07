package it.ethiclab.dbf2xml;

import java.sql.*;
import java.util.concurrent.atomic.*;
import java.io.*;

public class DataToJdbcReaderWriter implements DataWriter, DataReader {

    private void createDatabase(Connection cn) throws SQLException {
        cn.getMetaData();
    }

    private String buildCreateTableSqlStatement(String entityName, Data data) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append('"');
        sb.append(entityName);
        sb.append('"');
        sb.append(' ');
        sb.append('(');
        sb.append('\n');
        sb.append('\t');
        sb.append('"');
        sb.append("ID");
        sb.append('"');
        sb.append(' ');
        sb.append("INTEGER PRIMARY KEY");
        sb.append('\n');
        data.getRows().get(0).getFields().stream().forEachOrdered(row -> {
            sb.append('\t');
            sb.append(',');
            sb.append('"');
            sb.append(row.getName());
            sb.append('"');
            sb.append(' ');
            sb.append("TEXT");
            sb.append('\n');
        });
        sb.append(')');
        sb.append(';');
        return sb.toString();
    }

    private String buildDropTableSqlStatement(String entityName) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append('"');
        sb.append(entityName);
        sb.append('"');
        sb.append(';');
        return sb.toString();
    }

    private PreparedStatement buildInsertStatement(Connection cn, String entityName, int id, Row row) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append('"');
        sb.append(entityName);
        sb.append('"');
        sb.append(' ');
        sb.append('(');
        sb.append('\n');
        sb.append('\t');
        sb.append('"');
        sb.append("ID");
        sb.append('"');
        sb.append('\n');
        row.getFields().stream().forEachOrdered(f -> {
            sb.append('\t');
            sb.append(',');
            sb.append('"');
            sb.append(f.getName());
            sb.append('"');
            sb.append('\n');
        });
        sb.append(')');
        sb.append(' ');
        sb.append("VALUES");
        sb.append(' ');
        sb.append('(');
        sb.append('\n');
        sb.append('\t');
        sb.append("?");
        sb.append('\n');
        row.getFields().stream().forEachOrdered(f -> {
            sb.append('\t');
            sb.append(',');
            sb.append("?");
            sb.append('\n');
        });
        sb.append(')');
        sb.append(';');
        String sqlInsert = sb.toString();
        PreparedStatement insert = cn.prepareStatement(sqlInsert);
        insert.setInt(1, id);
        AtomicInteger a = new AtomicInteger(2);
        row.getFields().stream().forEachOrdered(field -> {
            try {
                insert.setString(a.getAndIncrement(), (String) field.getValue());
            } catch (SQLException e) {
                throw new ApplicationRuntimeException(e);
            }
        });
        return insert;
    }

    private void close(Statement closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (SQLException e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    private void close(ResultSet closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (SQLException e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    private void execute(Connection cn, String sql, boolean ignoreExceptions) {
        Statement stmt = null;
        try {
            stmt = cn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            if (!ignoreExceptions) {
                throw new ApplicationRuntimeException(e);
            }
        } finally {
            close(stmt);
        }
    }

    private void executeQuery(Data data, Connection cn, String sql, boolean ignoreExceptions) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = cn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Row row = new Row();
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    Field f = new Field(rsmd.getColumnName(i + 1), rs.getString(i + 1));
                    row.getFields().add(f);
                }
                data.getRows().add(row);
            }
        } catch (SQLException e) {
            if (!ignoreExceptions) {
                throw new ApplicationRuntimeException(e);
            }
        } finally {
            close(rs);
            close(stmt);
        }
    }

    private void insertRow(Connection cn, String entityName, int id, Row row) {
        PreparedStatement insert = null;
        try {
            insert = buildInsertStatement(cn, entityName, id, row);
            int ret = insert.executeUpdate();
            if (ret != 1) {
                throw new ApplicationRuntimeException("error inserting data");
            }
        } catch (SQLException e) {
            throw new ApplicationRuntimeException(e);
        } finally {
            close(insert);
        }
    }

    @Override
    public void write(String entityName, String destinationSpecs, Data data) {
        try {
            Connection cn = DriverManager.getConnection(destinationSpecs);
            try {
                createDatabase(cn);
                String sqlDrop = buildDropTableSqlStatement(entityName);
                execute(cn, sqlDrop, true);
                if (!data.getRows().isEmpty()) {
                    String sqlCreate = buildCreateTableSqlStatement(entityName, data);
                    execute(cn, sqlCreate, false);
                    AtomicInteger a = new AtomicInteger();
                    data.getRows().forEach(row -> insertRow(cn, entityName, a.incrementAndGet(), row));
                }
                String afterSql = System.getProperty("sql.postExecution");
                if (afterSql != null) {
                    execute(cn, afterSql, false);
                }
            } finally {
                cn.close();
            }
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    @Override
    public Data read(String query, String sourceSpecs) {
        Data data = new Data();
        try {
            Connection cn = DriverManager.getConnection(sourceSpecs);
            try {
                executeQuery(data, cn, query, false);
                return data;
            } finally {
                cn.close();
            }
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
    }
}
