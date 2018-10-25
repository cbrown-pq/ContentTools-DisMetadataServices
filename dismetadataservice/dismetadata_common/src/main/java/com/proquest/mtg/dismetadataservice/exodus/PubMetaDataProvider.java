package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

public class PubMetaDataProvider implements IPubMetaDataProvider {
	private IJdbcConnectionPool connectionPool;
	private String pqOpenUrlBase;
	
	@Inject
	public PubMetaDataProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) 
			IJdbcConnectionPool connectionPool,
			@Named(DisMetadataProperties.PQ_OPEN_URL_BASE) 
			String pqOpenUrlBase) throws SQLException {
		this.connectionPool = connectionPool;
		this.pqOpenUrlBase = pqOpenUrlBase.trim();
	}

	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}
		
	public String getPqOpenUrlBase() {
		return pqOpenUrlBase;
	}

	@Override
	public DisPubMetaData getPubMetaDataFor(String pubId, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception {
		DisPubMetaData result = null;
		Connection connection = null;
		PubMetaDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new PubMetaDataQuery(connection, getPqOpenUrlBase());
			result = query.getFor(pubId, excludeRestriction, excludeAbstract, excludeAltAbstract);
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
