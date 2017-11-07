package it.ethiclab.dbf2xml;

import java.sql.*;
import java.util.concurrent.atomic.*;

public class DataToJdbcWriter implements DataWriter {

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

    private void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
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
}
