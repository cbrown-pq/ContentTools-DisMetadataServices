package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquest.mtg.dismetadataservice.fopeligiblepubs.xml.FopEligiblePubsList;
import com.proquest.mtg.dismetadataservice.fopeligiblepubs.xml.FopEligiblePubsList.Publication;


public class FOPEligiblePubsQuery {
	
	private Connection connection;
	private PreparedStatement fopEligiblePubsStatement;
	private CallableStatement exodusStatement;

	private static final String kPubId = "PubNumber";
	private static final String kPubSource = "PubSource";
	
	private static final String kSelectFOPEligiblePubs = "SELECT " +
			"di.ditm_pub_number " + kPubId + ", " +
			"nvl(di.ditm_source,'null') " + kPubSource + " " +
			"from dis_items di " +
			"WHERE " +
				"ditm_status = 'Z' " +
				"AND ditm_film_fiche_in_progress = 'N' " +
				"AND ditm_pub_number in	('3746638','3746582','3746352','3746351','3746002'," +
				"'3746000','3745873','3745872','3745871','3745868','3745675','3745624', " +
				"'3745623','3745622','3745604','3745582','3745570','3745568','3745407','3745389')";
				//"AND not exists (select 1 from dis_item_available_formats where ditm_id = di.ditm_id and dvf_code in ('MFC','MFL')) " +
				//"and to_date(ditm_date_MODIFIED,'dd-MON-yy') between '01-JAN-2017' and '31-JAN-2017' and ditm_source != 'H'";
			
	private static final String kUpdateFFInProgressStatus = "UPDATE dis_items set ditm_film_fiche_in_progress = ? where ditm_pub_number = ?"; 
	
	public FOPEligiblePubsQuery(Connection inConnection) throws Exception {
		this.connection = inConnection;
	}
	
	public FopEligiblePubsList getAllFOPEligiblePubs() throws Exception {
		FopEligiblePubsList result = new FopEligiblePubsList();
		this.fopEligiblePubsStatement = connection.prepareStatement(kSelectFOPEligiblePubs);
		ResultSet cursor = null;
		try {
			cursor = fopEligiblePubsStatement.executeQuery();
			while (cursor.next()) {
				Publication publication = new Publication();
				publication.setPubID(cursor.getString(kPubId));
				publication.setPubSource(cursor.getString(kPubSource));
				result.getPublication().add(publication);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	public String updateFFInProgressStatusStatus(String pubid, String status) throws Exception {
		
		System.out.println(">> Updating film-fiche in progress status ["+status+"] for pub number ["+pubid+"]");
		this.exodusStatement = connection.prepareCall(kUpdateFFInProgressStatus);
		this.exodusStatement.setString(1, status);
		this.exodusStatement.setString(2, pubid);
		
		this.exodusStatement.executeUpdate();
		connection.commit();
		
		return "Done";
	}
	
	public void close() throws SQLException {
		closeStatement(fopEligiblePubsStatement);
	}

	private void closeStatement(PreparedStatement statement) throws SQLException {
		if (null != statement) {
			statement.close();
		}
	}
}