package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchoolUpdateQueryForDashboard {
	private Connection connection;
	private PreparedStatement dashBoardAdminUpdateStmt;
	private PreparedStatement disSchoolUpdateStmt;
	private PreparedStatement disSchoolLoadStatusStmt;
	private static String kExodusModifiedUserName = "OneAdmin";
	private static String kDashBoardAdminName = "DASHBOARD ADMIN";
	private static final String kColumnSchoolEndDate = "SchoolEndDate";
	private static final String kColumnSchoolStatus = "SchoolStatus";
	private static final String kColumnSchoolUserModified = "SchoolUserModified";
	private static final String kColumnSchoolDateModified = "SchoolDateModified";
	private static final String kColumnSchoolLoadStatus = "SchoolLoadStatus";
	private static final String kColumnSchoolLoadStatusDate = "SchoolLoadStatusDate";
	public static final String kEmptyValue = "";

	private static final String kUpdateSchoolPersonForDashBoardAdmin = "UPDATE " +
					"DIS.DIS_SCHOOL_PEOPLE dsp " +
				"SET "  +
					"dsp.DSP_END_DATE = to_date(?,'dd-Mon-yy'), " +
					"dsp.DSP_STATUS = 'N', " +	
					"dsp.DSP_USER_MODIFIED = '" + kExodusModifiedUserName + "', "+
					"dsp.DSP_DATE_MODIFIED = to_date(?,'dd-Mon-yy') " +
				"WHERE EXISTS (SELECT 1 FROM " +
					"DIS_SCHOOLS dish, DIS_VALID_TITLE_CATEGORIES dvtc " +
					"WHERE " +
						"dish.DISH_ID = dsp.DISH_ID and " + 
						"dsp.DVTC_ID = dvtc.DVTC_ID and " + 
						"dvtc.DVTC_NAME = '" + kDashBoardAdminName + "' and " +  
						"dish.DISH_CODE = ?)";
	
	private static final String kUpdateSchoolLoadStatus = "UPDATE " +
			"DIS.DIS_SCHOOLS dish " +
		"SET "  +
			"dish.DISH_DASH_LOAD_STATUS = 'Y', "+
			"dish.DISH_DASH_LOAD_STATUS_DATE = to_date(?,'dd-Mon-yy') " +
		"WHERE " +
			"dish.DISH_CODE = ?";
	
	private static final String kSchoolLoadStatus =  "SELECT " 
			+ "DSP.DSP_END_DATE " + kColumnSchoolEndDate + ", "
			+ "DSP.DSP_STATUS " + kColumnSchoolStatus + ", "
			+ "DSP.DSP_USER_MODIFIED " + kColumnSchoolUserModified + ", "
			+ "DSP.DSP_DATE_MODIFIED " + kColumnSchoolDateModified + ", "
			+ "DISH.DISH_DASH_LOAD_STATUS " + kColumnSchoolLoadStatus + ", "
			+ "DISH.DISH_DASH_LOAD_STATUS_DATE " + kColumnSchoolLoadStatusDate + " "
			+ "FROM "  +
				"DIS_SCHOOL_PEOPLE DSP, " +
				"DIS_SCHOOLS DISH, " +
				"DIS_VALID_TITLE_CATEGORIES DVTC " +
			"WHERE DSP.DISH_ID = DISH.DISH_ID " +
						"AND DSP.DVTC_ID = DVTC.DVTC_ID " +
						"AND dvtc.DVTC_NAME = '" + kDashBoardAdminName + "' " +
						"AND DISH_CODE = ? ";
					
	
	public SchoolUpdateQueryForDashboard(Connection connection)
			throws SQLException {
		this.dashBoardAdminUpdateStmt = connection
				.prepareStatement(kUpdateSchoolPersonForDashBoardAdmin);
		this.disSchoolUpdateStmt = connection
				.prepareStatement(kUpdateSchoolLoadStatus);
		this.disSchoolLoadStatusStmt = connection
				.prepareStatement(kSchoolLoadStatus);
		this.connection = connection;
	}

	public void updateSchoolDashboardAdminFor(String schoolCode)
			throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		String currentDate = dateFormat.format(new Date());
		try {
			getConnection().setAutoCommit(false);
			executeSchoolPersonUpdate(schoolCode, currentDate);
			executeSchoolUpdate(schoolCode, currentDate);
			getConnection().commit();
		} catch (SQLException e) {
			if (getConnection() != null) {
				try {
					System.err.print("Transaction is being rolled back");
					getConnection().rollback();
					throw new Exception(
							"Failed to update Exodus for school code : "
									+ schoolCode + ". Exception : "
									+ e.getMessage());
				} catch (SQLException excep) {
					throw new Exception(
							"Failed to update Exodus for school code : "
									+ schoolCode + ". Exception : "
									+ e.getMessage());
				}
			}
		} finally {
			getConnection().setAutoCommit(true);
		}
	}

	private void executeSchoolUpdate(String schoolCode, String currentDate)
			throws SQLException {
		disSchoolUpdateStmt.setString(1, currentDate);
		disSchoolUpdateStmt.setString(2, schoolCode);
		disSchoolUpdateStmt.executeUpdate();
	}

	private void executeSchoolPersonUpdate(String schoolCode, String currentDate)
			throws SQLException {
		dashBoardAdminUpdateStmt.setString(1, currentDate);
		dashBoardAdminUpdateStmt.setString(2, currentDate);
		dashBoardAdminUpdateStmt.setString(3, schoolCode);
		dashBoardAdminUpdateStmt.executeUpdate();
	}

	public Connection getConnection() {
		return connection;
	}

	public void close() throws SQLException {
		closeStatement(dashBoardAdminUpdateStmt);
	}

	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
	}

	public List<String> getSchoolDashboardDataFor(String schoolCode)
			throws SQLException {
		List<String> dashboardData = new ArrayList<String>();
		if (null != schoolCode) {
			ResultSet cursor = null;
			try {
				disSchoolLoadStatusStmt.setString(1, schoolCode);
				cursor = disSchoolLoadStatusStmt.executeQuery();
				if (cursor.next()) {
					dashboardData.add(required(cursor
							.getString(kColumnSchoolEndDate)));
					dashboardData.add(required(cursor
							.getString(kColumnSchoolStatus)));
					dashboardData.add(required(cursor
							.getString(kColumnSchoolUserModified)));
					dashboardData.add(required(cursor
							.getString(kColumnSchoolDateModified)));
					dashboardData.add(required(cursor
							.getString(kColumnSchoolLoadStatus)));
					dashboardData.add(required(cursor
							.getString(kColumnSchoolLoadStatusDate)));
				}
			} finally {
				if (null != cursor) {
					cursor.close();
				}
			}
		}
		return dashboardData;

	}

	public static String trimmed(String x) {
		if (null != x) {
			x = x.trim();
		}
		return x;
	}

	public static String required(String x) {
		x = trimmed(x);
		if (null == x || x.isEmpty()) {
			x = kEmptyValue;
		}
		return x;
	}

}
