package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Named;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;


public class ExternalUrlDataProvider implements IExternalUrlDataProvider {

	private IJdbcConnectionPool connectionPool;

	@Inject
	public ExternalUrlDataProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) IJdbcConnectionPool connectionPool)
			throws SQLException {
		this.connectionPool = connectionPool;
	}

	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}

	@Override
	public DissertationList geDataFor(String lastRunDate) throws Exception {
		DissertationList result = null;
		Connection connection = null;
		ExternalUrlDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new ExternalUrlDataQuery(connection);
			result = query.getAllExternalUrls(lastRunDate);
		} finally {
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
	public String updateUrlStatus(String pubid, String status) throws Exception {
		String result = "No result";
		Connection connection = null;
		ExternalUrlDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new ExternalUrlDataQuery(connection);
			result = query.updateExternalUrlStatus(pubid, status);
		} finally {
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