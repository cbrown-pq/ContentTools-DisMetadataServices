package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Named;
import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

public class MStarPubMetaDataProvider implements IMStarPubMetaDataProvider {

	private IJdbcConnectionPool connectionPool;
	private String pqOpenUrlBase;

	@Inject
	public MStarPubMetaDataProvider(
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

	public Dissertation getPubMetaDataFor(String pubId) throws SQLException {
		Dissertation result = null;
		Connection connection = null;
		PubMetaDataQueryForMrngXml query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new PubMetaDataQueryForMrngXml(connection,
					getPqOpenUrlBase());
			result = query.getFor(pubId);
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
	public Dissertation getFakePubMetaDataFor(String pubId) throws SQLException {
		Dissertation result = null;
		return result;
	}
}