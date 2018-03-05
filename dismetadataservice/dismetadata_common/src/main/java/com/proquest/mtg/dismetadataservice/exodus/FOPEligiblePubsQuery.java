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
				"AND ditm_pub_number in	('10010904','10015132','10009782','10009814','10009785','10015138','10015153','10015144','10009794','10009813','10015130','10015139','10015149','10009795','10009809','10009781','10015129','10006347','10015136','10015131','10009797','10015134','10015111','10008859','10015146','10015108','10009808','10015643','10009799','10009788','10015137','10015135','10013841','10015145','10009810','10009786','10008860','10008962','10015140','10009787','10009816','10015094','10009806','10015154','10008885','10009801','10009811','10009792','10009779','10015117','10009803','10015133','10009802','10008975','10009798','10015148','10263663','10264480','10261382','10265477','10253761','10253780','10253062','10253595','10253234','10251965','10251528','10252569','10256778','10255766','10252716','10260995','10245192','10259036','10257501','10257505','10251437','10193837','10258894','10169963','10259413','10258651','10259423','10257633','10194716','10257131','10258166','10169961','10259214','10259218','10259219','10161141','10258743','10255422','10190042','10244690','10257498','10258522','10256475','10259294','10259310','10244727','10259320','10258809','10259322','10170832','10592737','10282322','111111')";
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