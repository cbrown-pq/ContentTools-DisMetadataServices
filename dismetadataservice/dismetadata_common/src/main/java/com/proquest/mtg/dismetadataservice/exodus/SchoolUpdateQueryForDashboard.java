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
	private static String kExodusModifiedUserName = "OneAdmin";
	private static String kDashBoardAdminName  = "DASHBOARD ADMIN";

	private static final String kUpdateDashBoardAdmin = "UPDATE " +
					"DIS_SCHOOL_PEOPLE dsp " +
				"SET "  +
					"dsp.DSP_END_DATE = to_date(?,'dd-Mon-yy'), " +
					"dsp.DSP_STATUS = 'N', " +
					"dsp.DSP_USER_MODIFIED = '" + kExodusModifiedUserName + "', "+
					"dsp.DSP_DATE_MODIFIED = to_date(?,'dd-Mon-yy') " +
				"WHERE EXISTS (SELECT 1 FROM " +
					"DIS_SCHOOLS ds, DIS_VALID_TITLE_CATEGORIES dvtc " +
					"WHERE " +
						"ds.DISH_ID = dsp.DISH_ID and " + 
						"dsp.DVTC_ID = dvtc.DVTC_ID and " + 
						"dvtc.DVTC_NAME = '" + kDashBoardAdminName + "' and " + 
						"ds.DISH_CODE = ?)";
	
	public SchoolUpdateQueryForDashboard(Connection connection) throws SQLException {
		this.dashBoardAdminUpdateStmt = connection.prepareStatement(kUpdateDashBoardAdmin);
		this.connection = connection;
	}
	
	public void updateSchoolDashboardAdminFor(String schoolCode) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		String currentDate = dateFormat.format(new Date());
		try {
			getConnection().setAutoCommit(false);
			dashBoardAdminUpdateStmt.setString(1, currentDate);
			dashBoardAdminUpdateStmt.setString(2, currentDate);
			dashBoardAdminUpdateStmt.setString(3, schoolCode);
			dashBoardAdminUpdateStmt.executeUpdate();
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
