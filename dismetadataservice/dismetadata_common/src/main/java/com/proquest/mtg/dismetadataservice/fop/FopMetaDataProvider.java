package com.proquest.mtg.dismetadataservice.fop;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proquest.mtg.dismetadataservice.guice.DisMetadataServiceGuiceModule;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

public class FopMetaDataProvider implements IFopMetaDataProvider {
	private String fopDbUrl;
	private String fopDbUserName;
	private String fopDbPassword;
	private final Logger logger = LoggerFactory.getLogger(FopMetaDataProvider.class);
	@Inject
	public FopMetaDataProvider(
			@Named(DisMetadataProperties.FOP_DB_URL) String fopDbUrl,
			@Named(DisMetadataProperties.FOP_DB_USER_NAME) String fopDbUserName,
			@Named(DisMetadataProperties.FOP_DB_PASSWORD) String fopDbPassword)
			throws Exception {
		this.fopDbUrl = fopDbUrl;
		this.fopDbUserName = fopDbUserName;
		this.fopDbPassword = fopDbPassword;
	}

	public String getFopDbUrl() {
		return fopDbUrl;
	}

	public void setFopDbUrl(String fopDbUrl) {
		this.fopDbUrl = fopDbUrl;
	}

	public String getFopDbUserName() {
		return fopDbUserName;
	}

	public void setFopDbUserName(String fopDbUserName) {
		this.fopDbUserName = fopDbUserName;
	}


	public String getFopDbPassword() {
		return fopDbPassword;
	}


	public void setFopDbPassword(String fopDbPassword) {
		this.fopDbPassword = fopDbPassword;
	}


	@Override
	public void updateFFInProgress(String pubNumber,String inProgressFlag) throws Exception {
	//	Class.forName("com.mysql.jdbc.Driver");
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		Connection connection = null;
		FopMetaDataQuery query;
		try {
			connection = DriverManager.getConnection(getFopDbUrl(),
					getFopDbUserName(), getFopDbPassword());
			query = new FopMetaDataQuery(connection);
			query.updateFilmFicheInProgress(pubNumber, inProgressFlag);
			logger.info(pubNumber + " in progress flag was updated to " + inProgressFlag);
		} finally {
			if (null != connection) {
				connection.close();
			}
		}
	}
}
