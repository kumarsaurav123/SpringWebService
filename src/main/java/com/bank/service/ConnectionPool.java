package bank.service;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:database.properties")
public class ConnectionPool {
	@Value("${db.url}")
	private String url;
	@Value("${db.user}")
	private String user;
	@Value("${db.password}")
	private String password;
	@Value("${db.driverClassName}")
	private String driverClassName;
	private boolean draining = false;
	
	protected List<PoolConnection> connections;
	
	public ConnectionPool() {
		super();
		connections = new ArrayList<PoolConnection>();
	}
	
	public ConnectionPool(String url, String user, String password) {
		setDriverClassName();
		this.url = url;
		this.user = user;
		this.password = password;
		
		connections = new ArrayList<PoolConnection>();
	}
	
	
	public void drain() {
		draining = true;
		
		synchronized (connections) {
			for (int i = connections.size() - 1; i >= 0; i--) {
				PoolConnection pc = connections.get(i);
				
				connections.remove(i);
				try {
					pc.expire();
				} catch (SQLException ignore) {
				}
			}
		}
	}
	
	@Override
	protected void finalize() {
		drain();
	}
	
	public Connection getConnection() throws SQLException {
		if (draining) {
			throw new SQLException("ConnectionPool was drained.");
		}
		// return connection from pool
		PoolConnection pc = null;
		synchronized (connections) {
			if (connections.size() == 0) {
				Connection con = DriverManager.getConnection(url, user, password);
				connections.add(new PoolConnection(con, this));
			}
			System.out.println("connection got");
			System.out.println("Size= " + connections.size());
			pc = connections.remove(0);
			
		}
		return pc;
		
	}
	
	public List<PoolConnection> getConnections() {
		return connections;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
	
	public String getPassword() {
		return password;
	}
	
	
	public int getPoolSize() {
		synchronized (connections) {
			return connections.size();
		}
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	@PostConstruct
	public void init() {
		// do whatever you need with properties
	}
	
	
	
	public boolean isDraining() {
		return draining;
	}
	public void setConnections(List<PoolConnection> connections) {
		this.connections = connections;
	}
	
	public void setDraining(boolean draining) {
		this.draining = draining;
	}
	
	public void setDriverClassName() {
		try {
			System.out.println("registering drivers");
			Driver driver = (Driver) Class.forName(driverClassName).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
}