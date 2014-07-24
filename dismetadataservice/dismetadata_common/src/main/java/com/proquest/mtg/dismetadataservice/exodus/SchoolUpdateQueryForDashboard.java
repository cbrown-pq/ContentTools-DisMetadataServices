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
	private PreparedStatement dashBoardAdminDeleteStmt;
	private PreparedStatement dashBoardAdminDeleteElectronicAddressStmt;
	private PreparedStatement dashBoardAdminDeleteElectronicAddressUsesStmt;
	private PreparedStatement dashBoardAdminDeleteAddressesStmt;
	private PreparedStatement dashBoardAdminDeletePhoneNumbersStmt;
	private PreparedStatement dashBoardAdminDeletePhoneNumberUsesStmt;
	private PreparedStatement disSchoolUpdateStmt;
	private PreparedStatement disSchoolLoadStatusStmt;
	private static String kDashBoardAdminName = "DASHBOARD ADMIN";
	private static final String kColumnSchoolLoadStatus = "SchoolLoadStatus";
	private static final String kColumnSchoolLoadStatusDate = "SchoolLoadStatusDate";
	public static final String kEmptyValue = "";
	
	private static final String kDeleteElectronicAddressUsesForDashBoardAdmin = "DELETE FROM DIS_ELEC_ADDRESS_USES DEAU " + 
			"WHERE DEA_ID in(SELECT DEA.DEA_ID FROM DIS_SCHOOLS DISH, DIS_SCHOOL_PEOPLE DSP, DIS_VALID_TITLE_CATEGORIES DVTC,DIS_ELECTRONIC_ADDRESSES DEA " + 
			"WHERE DSP.DISH_ID =  DISH.DISH_ID " + 
			"AND DSP.DVTC_ID = DVTC.DVTC_ID " +
			"AND DSP.DSP_ID = DEA.DSP_ID " + 
			"AND DVTC.DVTC_NAME = '" + kDashBoardAdminName + "' " +
			"AND DISH.DISH_CODE = ?)";	

	private static final String kDeleteElectronicAddressForDashBoardAdmin = "DELETE FROM DIS_ELECTRONIC_ADDRESSES DEA " + 
			"WHERE DSP_ID in(SELECT DSP_ID FROM DIS_SCHOOLS DISH, DIS_SCHOOL_PEOPLE DSP, DIS_VALID_TITLE_CATEGORIES DVTC " + 
			"WHERE DSP.DISH_ID =  DISH.DISH_ID " + 
			"AND DSP.DVTC_ID = DVTC.DVTC_ID " +
			"AND DVTC.DVTC_NAME = '" + kDashBoardAdminName + "' " +
			"AND DISH.DISH_CODE = ?)";	
	
	private static final String kDeleteAddressesForDashBoardAdmin = "DELETE FROM DIS_ADDRESSES DA " + 
			"WHERE DSP_ID in(SELECT DSP_ID FROM DIS_SCHOOLS DISH, DIS_SCHOOL_PEOPLE DSP, DIS_VALID_TITLE_CATEGORIES DVTC " + 
			"WHERE DSP.DISH_ID =  DISH.DISH_ID " + 
			"AND DSP.DVTC_ID = DVTC.DVTC_ID " +
			"AND DVTC.DVTC_NAME = '" + kDashBoardAdminName + "' " +
			"AND DISH.DISH_CODE = ?)";	
	
	
	private static final String kDeletePhoneNumbersForDashBoardAdmin = "DELETE FROM DIS_PHONE_NUMBERS DPN " + 
			"WHERE DSP_ID in(SELECT DSP_ID FROM DIS_SCHOOLS DISH, DIS_SCHOOL_PEOPLE DSP, DIS_VALID_TITLE_CATEGORIES DVTC " + 
			"WHERE DSP.DISH_ID =  DISH.DISH_ID " + 
			"AND DSP.DVTC_ID = DVTC.DVTC_ID " +
			"AND DVTC.DVTC_NAME = '" + kDashBoardAdminName + "' " +
			"AND DISH.DISH_CODE = ?)";	
	
	
	private static final String kDeletePhoneNumberUsesForDashBoardAdmin = "DELETE FROM DIS_PHONE_USES DPU " + 
			"WHERE DPN_ID in(SELECT DPN_ID FROM DIS_SCHOOLS DISH, DIS_SCHOOL_PEOPLE DSP, DIS_VALID_TITLE_CATEGORIES DVTC,DIS_PHONE_NUMBERS DPN " + 
			"WHERE DSP.DISH_ID =  DISH.DISH_ID " + 
			"AND DSP.DVTC_ID = DVTC.DVTC_ID " +
			"AND DSP.DSP_ID = DPN.DSP_ID " + 
			"AND DVTC.DVTC_NAME = '" + kDashBoardAdminName + "' " +
			"AND DISH.DISH_CODE = ?)";
	
	private static final String kDeleteSchoolPersonForDashBoardAdmin = "DELETE FROM DIS_SCHOOL_PEOPLE DSP " + 
																		"WHERE DSP_ID in(SELECT DSP_ID FROM DIS_SCHOOLS DISH, DIS_VALID_TITLE_CATEGORIES DVTC " + 
																		"WHERE DSP.DISH_ID =  DISH.DISH_ID " + 
																		"AND DSP.DVTC_ID = DVTC.DVTC_ID " +
																		"AND DVTC.DVTC_NAME = '" + kDashBoardAdminName + "' " +
																		"AND DISH.DISH_CODE = ?)";	
	
	private static final String kUpdateSchoolLoadStatus = "UPDATE " +
			"DIS.DIS_SCHOOLS dish " +
		"SET "  +
			"dish.DISH_DASH_LOAD_STATUS = 'Y', "+
			"dish.DISH_DASH_LOAD_STATUS_DATE = to_date(?,'dd-Mon-yy') " +
		"WHERE " +
			"dish.DISH_CODE = ?";
	
	private static final String kSchoolLoadStatus =  "SELECT " 			
			+ "DISH.DISH_DASH_LOAD_STATUS " + kColumnSchoolLoadStatus + ", "
			+ "TO_CHAR(DISH.DISH_DASH_LOAD_STATUS_DATE,'YYYY-MM-DD') " + kColumnSchoolLoadStatusDate + " "
			+ "FROM "  +				
				"DIS_SCHOOLS DISH " +
			"WHERE DISH_CODE = ? ";
					
	
	public SchoolUpdateQueryForDashboard(Connection connection)
			throws SQLException {
		this.dashBoardAdminDeleteStmt = connection
				.prepareStatement(kDeleteSchoolPersonForDashBoardAdmin);
		this.dashBoardAdminDeleteElectronicAddressStmt = connection
				.prepareStatement(kDeleteElectronicAddressForDashBoardAdmin);
		this.disSchoolUpdateStmt = connection
				.prepareStatement(kUpdateSchoolLoadStatus);
		this.disSchoolLoadStatusStmt = connection
				.prepareStatement(kSchoolLoadStatus);
		this.dashBoardAdminDeleteElectronicAddressUsesStmt = connection.prepareStatement(kDeleteElectronicAddressUsesForDashBoardAdmin);
		this.dashBoardAdminDeleteAddressesStmt = connection.prepareStatement(kDeleteAddressesForDashBoardAdmin);
		this.dashBoardAdminDeletePhoneNumbersStmt = connection.prepareStatement(kDeletePhoneNumbersForDashBoardAdmin);
		this.dashBoardAdminDeletePhoneNumberUsesStmt = connection.prepareStatement(kDeletePhoneNumberUsesForDashBoardAdmin);
		this.connection = connection;
	}

	public void updateSchoolDashboardAdminFor(String schoolCode)
			throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		String currentDate = dateFormat.format(new Date());
		try {
			getConnection().setAutoCommit(false);
			executeElectronicAddressUsesDelete(schoolCode);
			executeElectronicAddressDelete(schoolCode);
			executeAddressDelete(schoolCode);
			executePhoneNumberUses(schoolCode);
			executePhoneNumbersDelete(schoolCode);
			executeSchoolPersonDelete(schoolCode);
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

	private void executeSchoolPersonDelete(String schoolCode)
			throws SQLException {
		dashBoardAdminDeleteStmt.setString(1, schoolCode);
		dashBoardAdminDeleteStmt.executeUpdate();
	}
	
	private void executeElectronicAddressDelete(String schoolCode)
			throws SQLException {
		dashBoardAdminDeleteElectronicAddressStmt.setString(1, schoolCode);
		dashBoardAdminDeleteElectronicAddressStmt.executeUpdate();
	}
	
	private void executeElectronicAddressUsesDelete(String schoolCode)
			throws SQLException {
		dashBoardAdminDeleteElectronicAddressUsesStmt.setString(1, schoolCode);
		dashBoardAdminDeleteElectronicAddressUsesStmt.executeUpdate();
	}
	
	private void executePhoneNumbersDelete(String schoolCode)
			throws SQLException {
		dashBoardAdminDeletePhoneNumbersStmt.setString(1, schoolCode);
		dashBoardAdminDeletePhoneNumbersStmt.executeUpdate();
	}
	
	private void executePhoneNumberUses(String schoolCode)
			throws SQLException {
		dashBoardAdminDeletePhoneNumberUsesStmt.setString(1, schoolCode);
		dashBoardAdminDeletePhoneNumberUsesStmt.executeUpdate();
	}
		
	private void executeAddressDelete(String schoolCode)
			throws SQLException {
		dashBoardAdminDeleteAddressesStmt.setString(1, schoolCode);
		dashBoardAdminDeleteAddressesStmt.executeUpdate();
	}

	public Connection getConnection() {
		return connection;
	}

	public void close() throws SQLException {
		closeStatement(dashBoardAdminDeleteStmt);
		closeStatement(dashBoardAdminDeleteElectronicAddressStmt);
		closeStatement(dashBoardAdminDeleteElectronicAddressUsesStmt);
		closeStatement(dashBoardAdminDeleteAddressesStmt);
		closeStatement(dashBoardAdminDeletePhoneNumbersStmt);
		closeStatement(dashBoardAdminDeletePhoneNumberUsesStmt);
		closeStatement(disSchoolUpdateStmt);
		closeStatement(disSchoolLoadStatusStmt);
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
