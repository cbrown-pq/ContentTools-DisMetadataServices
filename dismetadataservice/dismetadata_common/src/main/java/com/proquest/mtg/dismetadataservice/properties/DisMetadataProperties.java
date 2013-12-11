package com.proquest.mtg.dismetadataservice.properties;

import java.util.ArrayList;
import java.util.Properties;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConfig;

public class DisMetadataProperties {

	public final static String EXODUS_DB_URL = "exodus.db.url";
	public final static String EXODUS_USER_NAME = "exodus.db.username";
	public final static String EXODUS_PASSWORD = "exodus.db.password";
	public final static String EXODUS_POOL_SIZE = "exodus.db.poolsize";
	public final static String EXODUS_DB_CLASSTYPE = "exodus.db.classtype";

	public final static ArrayList<String> kRequiredProps = Lists
			.newArrayList(
					EXODUS_DB_URL, 
					EXODUS_USER_NAME, 
					EXODUS_PASSWORD, 
					EXODUS_POOL_SIZE,
					EXODUS_DB_CLASSTYPE);
	
	
	private final JdbcConfig exodusConfig;

	@Inject
	public DisMetadataProperties(IAppConfigReader appConfigReader) throws Exception {
		this(appConfigReader.getAllProperties());
	}

	public DisMetadataProperties(Properties props) {
		validate(props);
		
		this.exodusConfig = new JdbcConfig(
				props.getProperty(EXODUS_DB_URL),
				props.getProperty(EXODUS_USER_NAME),
				props.getProperty(EXODUS_PASSWORD),
				props.getProperty(EXODUS_DB_CLASSTYPE),
				getIntValueFrom(props, EXODUS_POOL_SIZE));
	}

	public JdbcConfig getExodusJdbcConfig() {
		return exodusConfig;
	}
	
	private void validate(Properties props) throws IllegalArgumentException {
		for (String propKeyName : kRequiredProps) {
			if (!props.containsKey(propKeyName)) {
				throw new IllegalArgumentException(
						"Invalid DismetadataProperties:  missing required key ="
								+ propKeyName);
			}
		}
	}
	
	private int getIntValueFrom(Properties props, String key) {
		return Integer.parseInt(props.getProperty(key));
	}

}
