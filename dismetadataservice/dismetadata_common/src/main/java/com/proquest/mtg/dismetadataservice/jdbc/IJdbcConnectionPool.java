package com.proquest.mtg.dismetadataservice.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface IJdbcConnectionPool {
	
	//public static final String kExodusConnectionPool = "ExodusJdbcConnectionPool";
	//public static final String kEbsConnectionPool = "EbsJdbcConnectionPool"; 
	//public static final String kFopExodusConnectionPool = "FopExodusJdbcConnectionPool";
	
	Connection getConnection() throws SQLException;
	
	void destroy() throws Exception;

}
