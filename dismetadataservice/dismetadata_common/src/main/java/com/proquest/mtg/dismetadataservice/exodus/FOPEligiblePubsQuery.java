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
			"nvl(di.ditm_source,'null')" + kPubSource + " " +
			"from dis_items di, " +
			"dis_item_available_formats diaf, " +
			"(select ditm_pub_number, max(nvl(diaf_date_modified,diaf_date_created)) dt " +
				"from dis_items di,dis_item_available_formats diaf " +
				"where di.ditm_id = diaf.ditm_id " +
				"and diaf.dvf_code in('RFP','RFN','MP','MN','MFC','MFL') group by ditm_pub_number) film_fiche_availability_date, " +
			"(select ditm_pub_number,max(nvl(diaf_date_modified,diaf_date_created)) dt " + 
				"from dis_items di,dis_item_available_formats diaf " + 
				"where di.ditm_id = diaf.ditm_id " + 
				"and diaf.dvf_code in('PDF') group by ditm_pub_number) pdf_availability_date " +
			"WHERE " +
				"ditm_status = 'Z' " +
				"AND di.ditm_id = diaf.ditm_id " +
				"AND nvl(ditm_film_fiche_in_progress,'N') != 'Y' " +
				"AND diaf.dvf_code in('RFP','RFN','MP','MN','MFC','MFL') " +
				"AND di.ditm_pub_number = film_fiche_availability_date.ditm_pub_number " +
				"AND di.ditm_pub_number = pdf_availability_date.ditm_pub_number " +
				"AND pdf_availability_date.dt > film_fiche_availability_date.dt and ROWNUM < 10";
			
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