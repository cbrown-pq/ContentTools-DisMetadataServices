package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School;

public class SchoolMetaDataProvider implements ISchoolMetaDataProvider {

	private IJdbcConnectionPool connectionPool;
	private int schoolBatchSize;

	@Inject
	public SchoolMetaDataProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) 
			IJdbcConnectionPool connectionPool,
			@Named(DisMetadataProperties.SCHOOL_BATCH_SIZE) int batchSize) throws SQLException {
		this.connectionPool = connectionPool;
		this.schoolBatchSize = batchSize;
	}
	
	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}
	
	public int getSchoolBatchSize() {
		return schoolBatchSize;
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
	
	@Override
	public List<School> getAllSchoolMetaData() throws Exception {
		List<School> result = new ArrayList<School>();
		List<String> schoolCodes = getAllSchoolCodes();
		result = getAllSchoolsMetaDataFor(schoolCodes);
		return result;
	}
	
	@Override
	public List<School> getAllSchoolsMetaDataFor(List<String> schoolCodes) throws Exception {
		List<School> result = new ArrayList<School>();
		Connection connection = null;
		SchoolMetaDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new SchoolMetaDataQuery(connection);
	    	result = query.getAllSchoolMetadata(schoolCodes, getSchoolBatchSize());
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
	public void updateDsahboardLoadStatus(String schoolCodes) throws Exception {
		Connection connection = null;
		SchoolUpdateQueryForDashboard query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new SchoolUpdateQueryForDashboard(connection);
	    	query.updateSchoolDashboardAdminFor(schoolCodes);
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
