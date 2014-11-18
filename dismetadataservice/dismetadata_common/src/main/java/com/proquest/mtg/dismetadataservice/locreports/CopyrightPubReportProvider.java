package com.proquest.mtg.dismetadataservice.locreports;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.LocReportMetaDataQuery;
import com.proquest.mtg.dismetadataservice.exodus.LocReportPubMetaData;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;

public class CopyrightPubReportProvider {
	private IJdbcConnectionPool connectionPool;

	@Inject
	public CopyrightPubReportProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) 
			IJdbcConnectionPool connectionPool) throws SQLException {
		this.connectionPool = connectionPool;
	}
	
	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}
	
	
	public List<LocReportPubMetaData> getCopyrightSubmittedPubs() throws Exception {
		List<LocReportPubMetaData> result = Lists.newArrayList();
		Connection connection = null;
		LocReportMetaDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new LocReportMetaDataQuery(connection);
			result = query.getLOCReportPubsForCopyright();
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
