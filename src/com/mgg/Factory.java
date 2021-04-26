package com.mgg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Factory {
	
	// Factory Method to get connection
	public static Connection getConnection() {
		
		String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
		String userName = DatabaseInfo.getUsername();
		String password = DatabaseInfo.getPassword();
		String url = DatabaseInfo.getUrl();
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;
	}
			
			
}