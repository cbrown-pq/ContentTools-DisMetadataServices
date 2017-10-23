package com.proquest.mtg.dismetadataservice;

import java.sql.Connection;

import org.apache.commons.io.FileUtils;

import com.proquest.mtg.dismetadataservice.exodus.FopFormatsDataQuery;
import com.proquest.mtg.dismetadataservice.exodus.LOCSubmissionUpdateQuery;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConfig;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConnectionPool;

public class FopUpdateFormatTest {
			
		public static void main(String args[]) throws Exception {
			JdbcConfig jdbcConfig = new JdbcConfig(
					"jdbc:oracle:thin:@sharedb202.aa1.pqe:1521:exdvl",
					"fop_dvl", "fop_dvl", 
					"oracle.jdbc.pool.OracleDataSource", 5);
			JdbcConnectionPool result = new JdbcConnectionPool(jdbcConfig);
			Connection connection = null;
			FopFormatsDataQuery query = null;
			String pubNumber = "0000056";
			String format = "PDF";
			String responseMsg;
			try {
				connection = result.getConnection();
				query = new FopFormatsDataQuery(connection);
				responseMsg = query.updateFOPFormats(pubNumber, format);
		    	System.out.println("Done");
		    	System.out.println("MSG:" + responseMsg);
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
