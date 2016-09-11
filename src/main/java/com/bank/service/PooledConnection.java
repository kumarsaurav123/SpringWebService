package bank.service;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

class PoolConnection implements Connection {
	
	/*----------------------------------+
	| Variables                         |
	+----------------------------------*/
	
	protected Connection conn;
	//
	private Throwable _throwable;
	
	private ConnectionPool connectionPool;
	
	public PoolConnection(Connection conn, ConnectionPool connectionPool) {
		this.conn = conn;
		this.connectionPool = connectionPool;
	}
	
	@Override
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void clearWarnings() throws SQLException {
		conn.clearWarnings();
	}
	
	@Override
	public synchronized void close() throws SQLException {
		
		synchronized (connectionPool.connections) {
			System.out.println("adding connection back to pool");
			System.out.println("Size= " + connectionPool.connections.size());

			connectionPool.connections.add(this);
		}
		
	}
	
	@Override
	public void commit() throws SQLException {
		conn.commit();
	}
	
	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*----------------------------------+
	| Constructors                      |
	+----------------------------------*/
	
	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Statement createStatement() throws SQLException {
		_throwable = new Throwable();
		return conn.createStatement();
	}
	
	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return conn.createStatement(resultSetType, resultSetConcurrency);
	}
	
	
	
	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}
	
	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Expires this connection and closes the underlying connection to the
	 * database. Once expired, a connection can no longer be used.
	 **/
	public void expire() throws SQLException {
		conn.close();
		conn = null;
	}
	
	/*----------------------------------+
	| Wrapping methods for Connection   |
	+----------------------------------*/
	
	public Throwable get_throwable() {
		return _throwable;
	}
	
	@Override
	public boolean getAutoCommit() throws SQLException {
		return conn.getAutoCommit();
	}
	
	@Override
	public String getCatalog() throws SQLException {
		return conn.getCatalog();
	}
	
	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}
	
	
	@Override
	public int getHoldability() throws SQLException {
		return conn.getHoldability();
	}
	
	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return conn.getMetaData();
	}
	
	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Returns the time stamp of the last time this connection was closed.
	 **/
	
	
	@Override
	public int getTransactionIsolation() throws SQLException {
		return conn.getTransactionIsolation();
	}
	
	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return conn.getTypeMap();
	}
	
	@Override
	public SQLWarning getWarnings() throws SQLException {
		return conn.getWarnings();
	}
	
	/**
	 * Checks if the connection currently is used by someone.
	 **/
	
	
	@Override
	public boolean isClosed() throws SQLException {
		return conn.isClosed();
	}
	
	@Override
	public boolean isReadOnly() throws SQLException {
		return conn.isReadOnly();
	}
	
	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String nativeSQL(String sql) throws SQLException {
		return conn.nativeSQL(sql);
	}
	
	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return conn.prepareCall(sql);
	}
	
	/*
	 * The following methods are new methods from java.sql.Connection that were
	 * added in JDK1.4. These additions are incompatible with older JDK
	 * versions.
	 */
	
	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}
	
	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}
	
	
	
	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		_throwable = new Throwable();
		return conn.prepareStatement(sql);
	}
	
	@Override
	public PreparedStatement prepareStatement(String sql, int autoGenerateKeys) throws SQLException {
		return conn.prepareStatement(sql, autoGenerateKeys);
	}
	
	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}
	
	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}
	
	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return conn.prepareStatement(sql, columnIndexes);
	}
	
	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return conn.prepareStatement(sql, columnNames);
	}
	
	public void printStackTrace() {
		_throwable.printStackTrace(System.err);
	}
	
	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		conn.releaseSavepoint(savepoint);
	}
	
	@Override
	public void rollback() throws SQLException {
		conn.rollback();
	}
	
	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		conn.rollback(savepoint);
	}
	
	public void set_throwable(Throwable _throwable) {
		this._throwable = _throwable;
	}
	
	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		conn.setAutoCommit(autoCommit);
	}
	
	@Override
	public void setCatalog(String catalog) throws SQLException {
		conn.setCatalog(catalog);
	}
	
	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	
	
	@Override
	public void setHoldability(int holdability) throws SQLException {
		conn.setHoldability(holdability);
	}
	
	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
			throws SQLException
			{
		conn.setReadOnly(readOnly);
			}
	
	@Override
	public Savepoint setSavepoint()
			throws SQLException
			{
		return conn.setSavepoint();
			}
	
	@Override
	public Savepoint setSavepoint(String name)
			throws SQLException
			{
		return conn.setSavepoint(name);
			}
	
	@Override
	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setTransactionIsolation(int level)
			throws SQLException
			{
		conn.setTransactionIsolation(level);
			}
	
	@Override
	public void setTypeMap(Map<String,Class<?>> map)
			throws SQLException
			{
		conn.setTypeMap(map);
			}
	
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}