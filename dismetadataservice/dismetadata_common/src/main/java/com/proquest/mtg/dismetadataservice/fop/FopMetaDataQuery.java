package com.proquest.mtg.dismetadataservice.fop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FopMetaDataQuery {
	private final Logger logger = LoggerFactory.getLogger(FopMetaDataProvider.class);
	Connection connection;
	String updateQuery = "UPDATE PUB SET FILM_FICHE_IN_PROGRESS = ? where PUB_NUMBER = ?";
	public FopMetaDataQuery(Connection connection) {
		this.connection = connection;
	}

	public void updateFilmFicheInProgress(String pubNumber,String flag) throws Exception {
		try{
		Class.forName("com.mysql.jdbc.Driver");
		PreparedStatement preparedStmt = connection.prepareStatement(updateQuery);  
		preparedStmt.setString(1, flag);
	      preparedStmt.setString(2, pubNumber);
	      System.out.println(connection.toString());  
		 preparedStmt.executeUpdate();
		 connection.close();
        }
	catch (Exception e)
    {
      logger.info("Got an exception! "+ e.getMessage());
    }
	}

}
