//package com.proquest.mtg.dismetadataservice.locreports;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.inject.Named;
//
//import com.google.common.collect.Lists;
//import com.proquest.mtg.dismetadataservice.exodus.LocReportMetaDataQuery;
//import com.proquest.mtg.dismetadataservice.exodus.LocReportPubMetaData;
//import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
//
//public class CopyrightPubReportProvider {
//	private IJdbcConnectionPool connectionPool;
//
//	@Inject
//	public CopyrightPubReportProvider(
//			@Named(IJdbcConnectionPool.kExodusConnectionPool) 
//			IJdbcConnectionPool connectionPool) throws SQLException {
//		this.connectionPool = connectionPool;
//	}
//	
//	public IJdbcConnectionPool getConnectionPool() {
//		return connectionPool;
//	}
//	
//	
//	public List<LocReportPubMetaData> getCopyrightSubmittedPubs(String formatType) throws Exception {
//		List<LocReportPubMetaData> result = Lists.newArrayList();
//		Connection connection = null;
//		LocReportMetaDataQuery query = null;
//		try {
//			connection = getConnectionPool().getConnection();
//			query = new LocReportMetaDataQuery(connection);
//			result = query.getLOCReportPubsForCopyright(formatType);
//		}
//		finally {
//			if (null != query) {
//				query.close();
//			}
//			if (null != connection) {
//				connection.close();
//			}
//		}
//		return result;
//	}
//
//	public List<LocReportPubMetaData> getNonCopyrightPubs(String formatType) throws Exception {
//		List<LocReportPubMetaData> nonCopyrightPubs;
//		Connection connection = null;
//		LocReportMetaDataQuery query = null;
//		try {
//			connection = getConnectionPool().getConnection();
//			query = new LocReportMetaDataQuery(connection);
//			nonCopyrightPubs = query.getLOCReportPubsForNonCopyright(formatType);
//		}
//		finally {
//			if (null != query) {
//				query.close();
//			}
//			if (null != connection) {
//				connection.close();
//			}
//		}
//		
//		
//		return nonCopyrightPubs;
//	}
//
//	public void updateFilmPullDate(List<String> pubs) throws Exception {
//		Connection connection = null;
//		LocReportMetaDataQuery query = null;
//		try {
//			connection = getConnectionPool().getConnection();
//			query = new LocReportMetaDataQuery(connection);
//			query.updateLOCFilmPullDateFor(pubs);
//		}
//		finally {
//			if (null != query) {
//				query.close();
//			}
//			if (null != connection) {
//				connection.close();
//			}
//		}
//	}
//	
//}
