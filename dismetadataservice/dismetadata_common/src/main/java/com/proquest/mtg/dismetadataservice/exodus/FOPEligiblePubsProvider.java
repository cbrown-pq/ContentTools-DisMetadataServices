package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Named;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.fopeligiblepubs.xml.FopEligiblePubsList;


public class FOPEligiblePubsProvider implements IFOPEligiblePubsProvider {

	private IJdbcConnectionPool connectionPool;

	@Inject
	public FOPEligiblePubsProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) IJdbcConnectionPool connectionPool)
			throws SQLException {
		this.connectionPool = connectionPool;
	}

	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}

	@Override
	public FopEligiblePubsList geDataFor() throws Exception {
		FopEligiblePubsList result = null;
		Connection connection = null;
		FOPEligiblePubsQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new FOPEligiblePubsQuery(connection);
			result = query.getAllFOPEligiblePubs();
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
	public String updateInprogressStatus(String pubid, String status) throws Exception {
		String result = "No result";
		Connection connection = null;
		FOPEligiblePubsQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new FOPEligiblePubsQuery(connection);
			result = query.updateFFInProgressStatusStatus(pubid, status);
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