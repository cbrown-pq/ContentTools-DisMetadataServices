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
			"from dis_items di,dis_work_orders dwo " +
			"WHERE " +
				"ditm_status = 'Z' " +
				"AND ditm_film_fiche_in_progress = 'N' " +
				"AND ditm_source in('E','F','R','D','P','M') " +
				"AND di.diw_id = dwo.diw_id " +
				"AND not exists (select 1 from dis_item_available_formats where ditm_id = di.ditm_id and dvf_code in ('MFC','MFL')) " +
				"AND dwo.diw_id > 1138627 "	+ 
			//FOR PREPROD	"AND dwo.diw_id > 1000000 "	+ 
				"AND rownum <= 950";

	
	//production query
	//SELECT ditm_id,di.ditm_pub_number PubNumber,to_date('01/05/2018 15:00:00','MM-DD-YYYY hh24:mi:ss'), ditm_date_created,ditm_date_modified,nvl(di.ditm_source,'null') PubSource 
	//from dis_items di 
	//WHERE ditm_status = 'Z' AND 
	//ditm_film_fiche_in_progress = 'N' AND 
	//not exists (select 1 from dis_item_available_formats where ditm_id = di.ditm_id and dvf_code in ('MFC','MFL')) and
	//ditm_source in('E','F','R','D','P','M') and ditm_pub_number in('10745921','10746165',	'10746542',	'10747492',	'10747795','10640913','10641761',	'10642151','10746648','10746936');
			
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