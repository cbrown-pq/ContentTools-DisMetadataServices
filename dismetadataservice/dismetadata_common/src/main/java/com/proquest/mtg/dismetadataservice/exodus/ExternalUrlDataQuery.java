package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList.Dissertation;


public class ExternalUrlDataQuery {
	
	public static final String kPubId = "PubId";
	public static final String kExternalUrl = "ExternalUrl";
	
	private PreparedStatement externalUrlStatement;
	

	private static final String kSelectExternalUrls = "select " + 
			 "ditm_pub_number "	+ kPubId + ", " +
			 "ditm_ext_url "	+ kExternalUrl + " " +		
			 "from dis_items where ditm_ext_url is not null";
	
	public ExternalUrlDataQuery(Connection connection) throws SQLException {
		this.externalUrlStatement = connection.prepareStatement(kSelectExternalUrls);
	}
	
	public DissertationList getAllExternalUrls() throws SQLException {
		DissertationList result = new DissertationList();
		ResultSet cursor = null;
		try {
			cursor = externalUrlStatement.executeQuery();
			while (cursor.next()) {
				Dissertation dissertation = new Dissertation();
				dissertation.setPubID(cursor.getString(kPubId));
				dissertation.setExtURL(cursor.getString(kExternalUrl));
				result.getDissertation().add(dissertation);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	public void close() throws SQLException {
		closeStatement(externalUrlStatement);
	}

	private void closeStatement(PreparedStatement statement) throws SQLException {
		if (null != statement) {
			statement.close();
		}
	}
}
