package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.metadata.school.School;

public class SchoolMetaDataProvider implements ISchoolMetaDataProvider {

	private IJdbcConnectionPool connectionPool;

	@Inject
	public SchoolMetaDataProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) 
			IJdbcConnectionPool connectionPool) throws SQLException {
		this.connectionPool = connectionPool;
	}
	
	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}
	
	@Override
	public List<String> getAllSchoolCodes() throws Exception {
		List<String> result = null;
		Connection connection = null;
		SchoolMetaDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new SchoolMetaDataQuery(connection);
			result = query.getAllSchoolCodes();
		}
		finally {
			if (null != query) {
				query.close();
			}
			if (null != connection) {
				connection.close();
			}
		}
		return result;
	}
	
	@Override
	public School getSchoolMetaDataFor(String schoolCode) throws Exception {
		School result = null;
		Connection connection = null;
		SchoolMetaDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new SchoolMetaDataQuery(connection);
			result = query.getSchoolMetadataForSchoolCode(schoolCode);
		}
		finally {
			if (null != query) {
				query.close();
			}
			if (null != connection) {
				connection.close();
			}
		}
		return result;
	}

}
