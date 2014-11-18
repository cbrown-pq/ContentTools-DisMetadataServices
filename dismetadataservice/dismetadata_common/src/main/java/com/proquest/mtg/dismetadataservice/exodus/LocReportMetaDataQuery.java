package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;

public class LocReportMetaDataQuery {
	
	private Connection connection;
	
	private static final String kColumnPubId = "PubNumber";
	private static final String kColumnAuthorFullName = "AuthorFullName";
	
	private static final String kSelectCopyrightReportPubs = 
			"SELECT " +
				"DISTINCT ditm.ditm_pub_number " + kColumnPubId + ", " +
				"dath.dath_fullname " +   kColumnAuthorFullName + " " + 
 			"FROM " +
				"dis_work_orders dwo, dis_items ditm, dis.dis_authors dath " +
			"WHERE " +
				"dwo.dvwo_code = 'V' AND " +
				"ditm.diw_id = dwo.diw_id AND " +
				"ditm.ditm_copyright_year IS NOT NULL AND " +
				"ditm.DITM_LC_COPYRIGHT_SENT_DATE IS NOT NULL AND " +
				"ditm.ditm_date_of_delivery IS NOT NULL	AND " +
				"ditm.ditm_lc_claim_sent_date IS NOT NULL AND " +
				"ditm.DITM_LC_FILM_PULL_DATE is NULL AND " +
				"ditm.ditm_id = dath.ditm_id AND " +
				"dath.dath_sequence_number = 1 " +
			"ORDER BY ditm.ditm_pub_number ";

	private PreparedStatement locReportForCopyrightStatement;

	public LocReportMetaDataQuery(Connection connection) throws SQLException {
		this.connection = connection;
		this.locReportForCopyrightStatement = connection.prepareStatement(kSelectCopyrightReportPubs);
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	
	public List<LocReportPubMetaData> getLOCReportPubsForCopyright() throws SQLException {
		List<LocReportPubMetaData> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			cursor = getLocReportForCopyrightStatement().executeQuery();
			while (cursor.next()) {
				String pubNumber = trimmed(cursor.getString(kColumnPubId));
				String authorFullName = trimmed(cursor.getString(kColumnAuthorFullName));
				LocReportPubMetaData reportPubs = new LocReportPubMetaData(pubNumber, authorFullName);
				result.add(reportPubs);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	public PreparedStatement getLocReportForCopyrightStatement() {
		return locReportForCopyrightStatement;
	}
	
	public void close() throws SQLException {
		closeStatement(locReportForCopyrightStatement);
	}
	
	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
	}
	
	public static String trimmed(String x) {
		if (null != x) {
			x = x.trim();
		}
		return x;
	}

	
}
