package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class LocReportMetaDataQuery {

	private Connection connection;

	private static final String kColumnPubId = "PubNumber";
	private static final String kColumnAuthorFullName = "AuthorFullName";
	private static final String kFormatTypeFilm35MM = "\'RFP\',\'RFN\'";
	private static final String kFormatTypeFiche105MM = "\'MP\',\'MN\'"; 
	
	public static final int kMaxNumberOfValuesForUpdate = 5;

	private static final String kSelectCopyrightReportPubs = "SELECT "
			+ "DISTINCT ditm.ditm_pub_number " + kColumnPubId + ", "
			+ "dath.dath_fullname " + kColumnAuthorFullName + " " + "FROM "
			+ "dis_work_orders dwo, dis_items ditm, dis.dis_authors dath, dis_item_available_formats diaf "
			+ "WHERE " 
			+ "ditm.ditm_id = diaf.ditm_id AND "
			+ "dwo.dvwo_code = 'V' AND "
			+ "ditm.diw_id = dwo.diw_id AND "
			+ "ditm.ditm_copyright_year IS NOT NULL AND "
			+ "ditm.DITM_LC_COPYRIGHT_SENT_DATE IS NOT NULL AND "
			+ "ditm.ditm_lc_claim_sent_date IS NOT NULL AND "
			+ "ditm.ditm_lc_film_pull_date is NULL AND "
			+ "ditm.ditm_id = dath.ditm_id AND "
			+ "dath.dath_sequence_number = 1 ";

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
			+ " di.ditm_lc_claim_sent_date is null and"
			+ " di.ditm_lc_film_pull_date is null and"
			+ " da.dvc_code = dvc.dvc_code(+) and" 
			+ " da.dvc_code like \'US\'";
	
	private static final String kLOCFilmPullDateUpdate = "UPDATE " +
			"DIS.DIS_ITEMS ditm " +
		"SET "  +
			"ditm.DITM_LC_FILM_PULL_DATE = to_date(?,'dd-Mon-yy') " +
		"WHERE " +
			"ditm.DITM_PUB_NUMBER IN (%s)";

	private PreparedStatement locReportForCopyrightStatement;
	private PreparedStatement locReportForNonCopyrightsStatement;

	public LocReportMetaDataQuery(Connection connection) throws SQLException {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public List<LocReportPubMetaData> getLOCReportPubsForCopyright(String formatType)
			throws Exception {
		List<LocReportPubMetaData> result = Lists.newArrayList();
		ResultSet cursor = null;
		String sql = "";
		try {
			if(formatType.equalsIgnoreCase("35mm")) {
				sql = kSelectCopyrightReportPubs + " and diaf.dvf_code in(" + kFormatTypeFilm35MM + ")";
			}
			else if(formatType.equalsIgnoreCase("105mm")) {
				sql = kSelectCopyrightReportPubs + " and diaf.dvf_code in(" + kFormatTypeFiche105MM + ")";
			
			}
			else{ 
				sql = kSelectCopyrightReportPubs + " and diaf.dvf_code in(" + kFormatTypeFilm35MM  + "," + kFormatTypeFiche105MM  +  ")";
			}
			sql = sql + " order by ditm_pub_number";
			this.locReportForCopyrightStatement = getConnection().prepareStatement(sql);
			cursor = getLocReportForCopyrightStatement().executeQuery();
			while (cursor.next()) {
				String pubNumber = trimmed(cursor.getString(kColumnPubId));
				String authorFullName = trimmed(cursor
						.getString(kColumnAuthorFullName));
				LocReportPubMetaData reportPubs = new LocReportPubMetaData(
						pubNumber, authorFullName);
				result.add(reportPubs);
			}
		} catch (SQLException e) {
			if (getConnection() != null) {
				e.printStackTrace();
				throw new Exception(
						"Failed to getLOCReportPubsForNonCopyright. Exception : "
								+ e.getMessage());
			} 
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	public List<LocReportPubMetaData> getLOCReportPubsForNonCopyright(String formatType)
			throws Exception {
		List<LocReportPubMetaData> locReportPubMetaDataList = Lists.newArrayList();
		ResultSet cursor = null;
		String sql = "";
		try {
			
			if(formatType.equalsIgnoreCase("35mm")) {
				sql = kSelectLocNoncopyrightPubs + " and diaf.dvf_code in(" + kFormatTypeFilm35MM + ")";
			}
			else if(formatType.equalsIgnoreCase("105mm")) {
				sql = kSelectLocNoncopyrightPubs + " and diaf.dvf_code in(" + kFormatTypeFiche105MM + ")";
			
			}
			else{ 
				sql = kSelectLocNoncopyrightPubs + " and diaf.dvf_code in(" + kFormatTypeFilm35MM  + "," + kFormatTypeFiche105MM  +  ")";
			}
			sql = sql + " order by ditm_pub_number";
			this.locReportForNonCopyrightsStatement = getConnection().prepareStatement(sql);
			cursor = getLocReportForNonCopyrightStatement().executeQuery();
			while (cursor.next()) {
				LocReportPubMetaData locReportPubMetaData = new LocReportPubMetaData(
						cursor.getString(kColumnPubId),
						cursor.getString(kColumnAuthorFullName));
				locReportPubMetaDataList.add(locReportPubMetaData);
			}
		} catch (SQLException e) {
			if (getConnection() != null) {
				e.printStackTrace();
				throw new Exception(
						"Failed to getLOCReportPubsForNonCopyright. Exception : "
								+ e.getMessage());
			} 
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return locReportPubMetaDataList;
	}
	
	public void updateLOCFilmPullDateFor(List<String> pubs)
			throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		String currentDate = dateFormat.format(new Date());
		PreparedStatement locFilmPullDateStatement = null;
		try {
			getConnection().setAutoCommit(false);
			while (pubs.size() > 0) {
				List<String> pubsSubSet = null;
				if (pubs.size() > kMaxNumberOfValuesForUpdate) {
					pubsSubSet = Lists.newArrayList(pubs.subList(0, kMaxNumberOfValuesForUpdate));
					pubs.subList(0, kMaxNumberOfValuesForUpdate).clear();
				} else {
					pubsSubSet = Lists.newArrayList(pubs);
					pubs.clear();
				}
				String sql = String.format(kLOCFilmPullDateUpdate, preparePlaceHolders(pubsSubSet));
				locFilmPullDateStatement = getConnection().prepareStatement(sql);
				locFilmPullDateStatement.setString(1, currentDate);
				locFilmPullDateStatement.executeUpdate();
			}
			getConnection().commit();
		} catch (SQLException e) {
			if (getConnection() != null) {
				System.err.print("Transaction is being rolled back");
				e.printStackTrace();
				getConnection().rollback();
				throw new Exception(
						"Failed to update LOC Film Pull Date. Exception : "
								+ e.getMessage());
			}
		} finally {
			getConnection().setAutoCommit(true);
		}
	}
	
	public static String preparePlaceHolders(List<String> pubs) {
	    StringBuilder builder = new StringBuilder();
	    int length = pubs.size();
	    for (int i = 0; i < length;) {
	        builder.append("'" + pubs.get(i) + "'");
	        if (++i < length) {
	            builder.append(",");
	        }
	    }
	    return builder.toString();
	}

	public static void setValues(PreparedStatement preparedStatement, List<String> values) throws SQLException {
	    for (int i = 0; i < values.size(); i++) {
	        preparedStatement.setObject(i + 1, values.get(i));
	    }
	}

	public PreparedStatement getLocReportForCopyrightStatement() {
		return locReportForCopyrightStatement;
	}
	
	
	public PreparedStatement getLocReportForNonCopyrightStatement() {
		return locReportForNonCopyrightsStatement;
	}

	public void close() throws SQLException {
		closeStatement(locReportForCopyrightStatement);
		closeStatement(locReportForNonCopyrightsStatement);
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
