package com.proquest.mtg.dismetadataservice.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface IJdbcConnectionPool {
	
	Connection getConnection() throws SQLException;
	
	void destroy() throws Exception;

}
