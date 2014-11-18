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

	private static final String kSelectCopyrightReportPubs = "SELECT "
			+ "DISTINCT ditm.ditm_pub_number " + kColumnPubId + ", "
			+ "dath.dath_fullname " + kColumnAuthorFullName + " " + "FROM "
			+ "dis_work_orders dwo, dis_items ditm, dis.dis_authors dath "
			+ "WHERE " + "dwo.dvwo_code = 'V' AND "
			+ "ditm.diw_id = dwo.diw_id AND "
			+ "ditm.ditm_copyright_year IS NOT NULL AND "
			+ "ditm.DITM_LC_COPYRIGHT_SENT_DATE IS NOT NULL AND "
			+ "ditm.ditm_date_of_delivery IS NOT NULL	AND "
			+ "ditm.ditm_lc_claim_sent_date IS NOT NULL AND "
			+ "ditm.DITM_LC_FILM_PULL_DATE is NULL AND "
			+ "ditm.ditm_id = dath.ditm_id AND "
			+ "dath.dath_sequence_number = 1 "
			+ "ORDER BY ditm.ditm_pub_number ";

	private static final String kSelectLocNoncopyrightPubs = "select distinct ditm_pub_number "
			+ kColumnPubId
			+ ", da.dath_fullname "
			+ kColumnAuthorFullName
			+ " from dis_items di,"
			+ " dis_work_orders dwo, "
			+ " dis_item_available_formats diaf,"
			+ " dis_valid_formats dvf,"
			+ " dis_authors da,"
			+ " dis_valid_countries dvc,"
			+ " dis_volume_issues dvi,"
			+ " dis_database_types ddt"
			+ " where"
			+ " di.ditm_id = diaf.ditm_id and"
			+ " di.ditm_id = da.ditm_id and"
			+ " ditm_pub_number not like ('EP%') and"
			+ " ditm_pub_number not like ('DP%') and"
			+ " ditm_pub_number not like ('EC%') and"
			+ " ditm_pub_number not like ('DC%') and"
			+ " di.diw_id = dwo.diw_id and"
			+ " di.ditm_copyright_year is null and"
			+ " da.dath_sequence_number = 1 and"
			+ " di.dvi_id = dvi.dvi_id and"
			+ " dvi.ddt_code not in('MAI') and"
			+ " dvi.ddt_code = ddt.ddt_code and"
			+ " dwo.dvwo_code = 'V' and"
			+ " exists (select ditm_id from dis_item_available_formats diaf"
			+ "         where diaf.ditm_id = di.ditm_id"
			+ "               and dvf_code in ('MFC','MFL') ) and"
			+ " diaf.dvf_code = dvf.dvf_code and"
			+ " diaf.dvf_code in('MP','MN','RFP','RFN') and"
			// + " di.ditm_lc_nocopyright_sent_date is null and"
			+ " da.dvc_code = dvc.dvc_code(+) and" + " da.dvc_code like \'US\'";

	private PreparedStatement locReportForCopyrightStatement;
	private PreparedStatement locReportForNonCopyrightsStatement;

	public LocReportMetaDataQuery(Connection connection) throws SQLException {
		this.connection = connection;
		this.locReportForCopyrightStatement = connection
				.prepareStatement(kSelectCopyrightReportPubs);
		this.locReportForNonCopyrightsStatement = connection
				.prepareStatement(kSelectLocNoncopyrightPubs);
	}

	public Connection getConnection() {
		return connection;
	}

	public List<LocReportPubMetaData> getLOCReportPubsForCopyright()
			throws SQLException {
		List<LocReportPubMetaData> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			cursor = getLocReportForCopyrightStatement().executeQuery();
			while (cursor.next()) {
				String pubNumber = trimmed(cursor.getString(kColumnPubId));
				String authorFullName = trimmed(cursor
						.getString(kColumnAuthorFullName));
				LocReportPubMetaData reportPubs = new LocReportPubMetaData(
						pubNumber, authorFullName);
				result.add(reportPubs);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	public List<LocReportPubMetaData> getLOCReportPubsForNonCopyright()
			throws SQLException {
		List<LocReportPubMetaData> locReportPubMetaDataList = Lists
				.newArrayList();
		ResultSet cursor = null;
		try {
			cursor = getLocReportForNonCopyrightStatement().executeQuery();
			while (cursor.next()) {
				LocReportPubMetaData locReportPubMetaData = new LocReportPubMetaData(
						cursor.getString(kColumnPubId),
						cursor.getString(kColumnAuthorFullName));
				locReportPubMetaDataList.add(locReportPubMetaData);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return locReportPubMetaDataList;
	}

	public PreparedStatement getLocReportForCopyrightStatement() {
		return locReportForCopyrightStatement;
	}
	
	
	public PreparedStatement getLocReportForNonCopyrightStatement() {
		return locReportForNonCopyrightsStatement;
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
