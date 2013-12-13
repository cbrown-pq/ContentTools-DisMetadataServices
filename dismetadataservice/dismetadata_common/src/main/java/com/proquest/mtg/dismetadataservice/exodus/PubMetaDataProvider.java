package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;

public class PubMetaDataProvider implements IPubMetaDataProvider {
	private IJdbcConnectionPool connectionPool;
	
	
	@Inject
	public PubMetaDataProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) 
			IJdbcConnectionPool connectionPool) throws SQLException {
		this.connectionPool = connectionPool;
	}

	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}

	@Override
	public DisPubMetaData getPubMetaDataFor(String pubId) throws Exception {
		DisPubMetaData result = null;
		Connection connection = null;
		PubMetaDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new PubMetaDataQuery(connection);
			result = query.getFor(pubId);
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
