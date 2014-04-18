package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchoolUpdateQueryForDashboard {
	private Connection connection;
	private PreparedStatement dashBoardAdminUpdateStmt;
	private PreparedStatement disSchoolUpdateStmt;
	private static String kExodusModifiedUserName = "OneAdmin";
	private static String kDashBoardAdminName  = "DASHBOARD ADMIN";

	private static final String kUpdateSchoolPersonForDashBoardAdmin = "UPDATE " +
					"DIS_SCHOOL_PEOPLE dsp " +
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
			"DIS_SCHOOLS dish " +
		"SET "  +
			"dish.DISH_DASH_LOAD_STATUS = 'Y', "+
			"dish.DISH_DASH_LOAD_STATUS_DATE = to_date(?,'dd-Mon-yy') " +
		"WHERE " +
			"dish.DISH_CODE = ?";
	
	public SchoolUpdateQueryForDashboard(Connection connection) throws SQLException {
		this.dashBoardAdminUpdateStmt = connection.prepareStatement(kUpdateSchoolPersonForDashBoardAdmin);
		this.disSchoolUpdateStmt = connection.prepareStatement(kUpdateSchoolLoadStatus);
		this.connection = connection;
	}
	
	public void updateSchoolDashboardAdminFor(String schoolCode) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		String currentDate = dateFormat.format(new Date());
		try {
			getConnection().setAutoCommit(false);
			executeSchoolPersonUpdate(schoolCode, currentDate);
			executeSchoolUpdate(schoolCode, currentDate);
			getConnection().commit();
	    } catch (SQLException e ) {
	        if (getConnection() != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                getConnection().rollback();
	                throw new Exception("Failed to update Exodus for school code : " + schoolCode 
	    	        		+ ". Exception : " + e.getMessage());
	            } catch(SQLException excep) {
	            	throw new Exception("Failed to update Exodus for school code : " + schoolCode 
	    	        		+ ". Exception : " + e.getMessage());
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
}
