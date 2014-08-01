package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LOCSubmissionUpdateQuery {
	private Connection connection;
	private PreparedStatement updateClaimSubmissionStmt;
	private PreparedStatement updateDeliverySubmissionStmt;
	public static final String kEmptyValue = "";
	
	private static final String kUpdateLOCClaimSubmissionDate = 
			"UPDATE " +
					"DIS_ITEMS ditm " +
			"SET " +
					"DITM_LC_CLAIM_SENT_DATE = to_date(SYSDATE, 'DD-MON-YY') " + 
			"WHERE " +
					"DITM_PUB_NUMBER = ? ";

	private static final String kUpdateLOCDeliveryDate = 
			"UPDATE " +
					"DIS_ITEMS ditm " +
			"SET " +
					"DITM_LC_DATE_OF_DELIVERY = to_date(SYSDATE, 'DD-MON-YY') " + 
			"WHERE " +
					"DITM_PUB_NUMBER = ? ";
					
	public LOCSubmissionUpdateQuery(Connection connection)
			throws SQLException {
		this.updateClaimSubmissionStmt = connection.prepareStatement(
				kUpdateLOCClaimSubmissionDate);
		this.updateDeliverySubmissionStmt = connection.prepareStatement(
				kUpdateLOCDeliveryDate);
		
		this.connection = connection;
	}

	
	public void updateClaimSubmissionFor(String pubNumber)
			throws Exception {
		try {
			getClaimSubmissionUpdateStmt().setString(1, pubNumber);
			if (getClaimSubmissionUpdateStmt().executeUpdate() == 0) {
				throw new Exception("Failed to update Claim submission date for pub number : " 
						+ pubNumber + ". Reason : Exodus update return to result.");
			}
		} catch (SQLException e) {
			throw new Exception("Failed to update Claim submission date for pub number : " 
					+ pubNumber + ". Reason : " + e.getMessage());
		}
	}
	
	public void updateDeliverySubmissionFor(String pubNumber)
			throws Exception {
		try {
			getDeliverySubmissionUpdateStmt().setString(1, pubNumber);
			if (getDeliverySubmissionUpdateStmt().executeUpdate() == 0) {
				throw new Exception("Failed to update delivery submission date for pub number : " 
						+ pubNumber + ". Reason : Exodus update return to result.");
			}
		} catch (SQLException e) {
			throw new Exception("Failed to update delivery submission date for pub number : " 
					+ pubNumber + ". Reason : " + e.getMessage());
		}
	}
	
	public void close() throws SQLException {
		closeStatement(getClaimSubmissionUpdateStmt());
		closeStatement(getDeliverySubmissionUpdateStmt());
	}
	
	private  PreparedStatement getClaimSubmissionUpdateStmt() throws SQLException {
		return updateClaimSubmissionStmt;
	}
	
	private  PreparedStatement getDeliverySubmissionUpdateStmt() throws SQLException {
		return updateDeliverySubmissionStmt;
	}

	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
	}

	public Connection getConnection() {
		return connection;
	}	
}
