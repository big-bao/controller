package org.opendaylight.controller.md.sal.dom.xsql.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JDBCStatement implements PreparedStatement {
    private JDBCResultSet rs = null;
    private transient JDBCConnection connection = null;
    private static Map<Integer, JDBCResultSet> queries = new ConcurrentHashMap<Integer, JDBCResultSet>();
    private String sql = null;

    public JDBCStatement(JDBCConnection con,String _sql) {
        this.connection = con;
        this.sql = _sql;
    }

    public JDBCStatement(JDBCConnection con) {
        this.connection = con;
    }

    public void setSQL(String _sql){
        this.sql = _sql;
    }

    public JDBCStatement() {

    }

    public PreparedStatement getProxy() {
        return this;
        /*
        return (PreparedStatement) Proxy.newProxyInstance(this.getClass()
                .getClassLoader(), new Class[] { PreparedStatement.class },
                new JDBCProxy(this));
                */
    }

    public static JDBCResultSet getQuery(int id) {
        return queries.get(id);
    }

    public static JDBCResultSet removeQuery(int id) {
        return queries.remove(id);
    }

    @Override
    public java.sql.ResultSet executeQuery(String _sql) throws SQLException {
        rs = new JDBCResultSet(_sql);
        queries.put(rs.getID(), rs);
        synchronized (rs) {
            this.connection.send(new JDBCCommand(rs,
                    JDBCCommand.TYPE_EXECUTE_QUERY));
            try {
                rs.wait();
            } catch (Exception err) {
            }
            if (rs.getError() != null) {
                throw ((SQLException) rs.getError());
            }
        }
        return rs.getProxy();
    }

    @Override
    public boolean execute(String _sql) throws SQLException {
        return true;
    }

    public void addRecord(ArrayList hierarchy) {
        rs.addRecord(hierarchy);
    }

    public int size() {
        return rs.size();
    }

    public void setFinished(boolean b) {
        this.rs.setFinished(b);
    }

    public JDBCResultSet getRS() {
        return this.rs;
    }

    public ResultSet getResultSet() {
        return this.rs;
    }

    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void cancel() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearBatch() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearWarnings() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys)
            throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames)
            throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int[] executeBatch() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys)
            throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes)
            throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames)
            throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Connection getConnection() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getFetchDirection() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getFetchSize() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public java.sql.ResultSet getGeneratedKeys() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxRows() throws SQLException {
        return 200;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isClosed() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isPoolable() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void closeOnCompletion() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return this.executeQuery(this.sql);
    }

    @Override
    public int executeUpdate() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearParameters() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean execute() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addBatch() throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNString(int parameterIndex, String value)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value,
            long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType,
            int scaleOrLength) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader,
            long length) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }

}
