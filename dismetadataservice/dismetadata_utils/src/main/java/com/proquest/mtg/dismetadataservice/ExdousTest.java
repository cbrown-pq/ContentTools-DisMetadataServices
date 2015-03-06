package com.proquest.mtg.dismetadataservice;

import java.sql.Connection;

import com.proquest.mtg.dismetadataservice.exodus.LOCSubmissionUpdateQuery;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConfig;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConnectionPool;

public class ExdousTest {

	public static void main(String args[]) throws Exception {
		JdbcConfig jdbcConfig = new JdbcConfig(
				"jdbc:oracle:thin:@shrdb302.aa1.pqe:1521:expep",
				"dos_pep", "dos_pep", 
				"oracle.jdbc.pool.OracleDataSource", 5);
		IJdbcConnectionPool result = new JdbcConnectionPool(jdbcConfig);
		Connection connection = null;
		LOCSubmissionUpdateQuery query = null;
		String pubNumber = "0000056";
		try {
			connection = result.getConnection();
			query = new LOCSubmissionUpdateQuery(connection);
	    	query.updateClaimSubmissionFor(pubNumber);
	    	query.updateDeliverySubmissionFor(pubNumber);
	    	System.out.println("Done");
		} catch(Exception e) {
			e.printStackTrace();
			
		}
		finally {
			if (null != query) {
				query.close();
			}
			if (null != connection) {
				connection.close();
			}
		}
	}
}
