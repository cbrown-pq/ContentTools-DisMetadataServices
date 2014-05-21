package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject;

public class SubjectsMetaDataProvider implements ISubjectsMetaDataProvider {
	private IJdbcConnectionPool connectionPool;

	@Inject
	public SubjectsMetaDataProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) 
			IJdbcConnectionPool connectionPool) throws SQLException {
		this.connectionPool = connectionPool;
	}
	
	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}

	@Override
	public List<Subject> getAllSubjects() throws Exception {
		List<Subject> result =  null;
		Connection connection = null;
		SubjectsMetaDataQuery  query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new SubjectsMetaDataQuery(connection);
			result = query.getAllSubjects();
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
