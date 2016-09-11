package bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSource extends AbstractDataSource {
	private final static Logger LOGGER = Logger.getLogger(AbstractDataSource.class.getName());
	private ConnectionPool connectionPool;
	
	@Autowired
	public DataSource(ConnectionPool connectionPool) {
		super();
		this.connectionPool = connectionPool;
		connectionPool.setDriverClassName();
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return connectionPool.getConnection();
	}
	
	@Override
	public Connection getConnection(String arg0, String arg1) throws SQLException {
		return connectionPool.getConnection();
	}
	
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return LOGGER;
	}
	
	@Autowired
	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
}
