package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Named;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

public class ExternalUrlDataProvider implements IExternalUrlDataProvider {

	private IJdbcConnectionPool connectionPool;
	private String pqOpenUrlBase;

	@Inject
	public ExternalUrlDataProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) IJdbcConnectionPool connectionPool,
			@Named(DisMetadataProperties.PQ_OPEN_URL_BASE) String pqOpenUrlBase)
			throws SQLException {
		this.connectionPool = connectionPool;
		this.pqOpenUrlBase = pqOpenUrlBase.trim();
	}

	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}

	public String getPqOpenUrlBase() {
		return pqOpenUrlBase;
	}

	public String makePqOpenUrlFor(String pubId) {
		return getPqOpenUrlBase() + pubId.trim();
	}

	@Override
	public DissertationList geDataFor() throws Exception {
		DissertationList result = null;
		Connection connection = null;
		ExternalUrlDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new ExternalUrlDataQuery(connection);
			result = query.getAllExternalUrls();
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