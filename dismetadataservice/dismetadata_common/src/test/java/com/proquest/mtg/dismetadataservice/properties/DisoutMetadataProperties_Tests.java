package com.proquest.mtg.dismetadataservice.properties;

import java.util.Properties;

public class DisoutMetadataProperties_Tests {

	static final String kjdbcUrl = "FakeDBURL";
	static final String kExodusDbUrl = "FakeURL";
	static final String kExodusUserName = "FakeUserName";
	static final String kExodusUserPassword = "FakeUserPassword";
	static final String kExodusPoolSize = "5";
	static final String kExodusDbClassType = "FakeDbClassType";
	
	public static Properties makePropertyMapForTesting() {
		Properties props = new Properties();
		props.setProperty(DisMetadataProperties.EXODUS_DB_URL, kjdbcUrl);
		props.setProperty(DisMetadataProperties.EXODUS_USER_NAME, kExodusUserName);
		props.setProperty(DisMetadataProperties.EXODUS_PASSWORD, kExodusUserPassword);
		props.setProperty(DisMetadataProperties.EXODUS_POOL_SIZE, kExodusPoolSize);
		props.setProperty(DisMetadataProperties.EXODUS_DB_CLASSTYPE, kExodusDbClassType);
		
		return props;
	}
}
