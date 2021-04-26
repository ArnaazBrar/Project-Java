package com.mgg;

public class DatabaseInfo {
	
	/**
	 * Connection parameters that are necessary for CSE's configuration
	 */
	
	public static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String USERNAME = "abrar";
	public static final String PASSWORD = "jPyjvqj4";
	public static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;

	
	public static String getParameters() {
		return PARAMETERS;
	}
	public static String getUsername() {
		return USERNAME;
	}
	public static String getPassword() {
		return PASSWORD;
	}
	public static String getUrl() {
		return URL;
	}

}
