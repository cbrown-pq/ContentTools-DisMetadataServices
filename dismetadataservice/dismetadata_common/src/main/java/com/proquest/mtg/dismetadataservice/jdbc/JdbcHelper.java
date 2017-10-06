package com.proquest.mtg.dismetadataservice.jdbc;

public class JdbcHelper {
	
	public static final String kConnectionFactoryClass_OracleThin = "oracle.jdbc.pool.OracleDataSource";
	
	public static JdbcConfig makeConfigForExodusDevelopment() {
		String url = "jdbc:oracle:thin:@sharedb202.aa1.pqe:1521:exdvl";
		String user = "dos_prod";
		String password = "dos_prod";
		String dbClassType = kConnectionFactoryClass_OracleThin;
		int maxPoolSize = 5;
		return new JdbcConfig(url, user, password, dbClassType, maxPoolSize);
	}
	
	public static JdbcConfig makeConfigForExodusPreProd() {
		String url = "jdbc:oracle:thin:@sharedb202.aa1.pqe:1521:expep";
		String user = "dos_prod";
		String password = "dos_prod";
		String dbClassType = kConnectionFactoryClass_OracleThin;
		int maxPoolSize = 5;
		return new JdbcConfig(url, user, password, dbClassType, maxPoolSize);
	}
	
	public static JdbcConfig makeConfigForExodusProd() {
		String url = "jdbc:oracle:thin:@shrdb102.dc4.pqe:1521:exprd";
		String user = "dos_prod";
		String password = "dos_prod";
		String dbClassType = kConnectionFactoryClass_OracleThin;
		int maxPoolSize = 5;
		return new JdbcConfig(url, user, password, dbClassType, maxPoolSize);
	}	

	
	public static JdbcConnectionPool makePoolForExodusDevelopment() throws Exception {		
		return new JdbcConnectionPool(makeConfigForExodusDevelopment());
	}
	
	public static JdbcConnectionPool makePoolForExodusPreProd() throws Exception {		
		return new JdbcConnectionPool(makeConfigForExodusPreProd());
	}
	
	public static JdbcConnectionPool makePoolForExodusProd() throws Exception {		
		return new JdbcConnectionPool(makeConfigForExodusProd());
	}
	
	public static JdbcConnectionPool makePoolForExodusUnitTest() throws Exception {		
		//return new JdbcConnectionPool(makeConfigForExodusPreProd());
		return new JdbcConnectionPool(makeConfigForExodusDevelopment());
	}	

}
