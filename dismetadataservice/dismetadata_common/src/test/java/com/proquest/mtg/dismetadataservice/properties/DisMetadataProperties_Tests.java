package com.proquest.mtg.dismetadataservice.properties;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.proquest.mtg.dismetadataservice.helper.MyAsserts;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConfig;

public class DisMetadataProperties_Tests {

	static final String kExodusDbUrl = "FakeURL";
	static final String kExodusUserName = "FakeUserName";
	static final String kExodusUserPassword = "FakeUserPassword";
	static final String kExodusPoolSize = "5";
	static final String kExodusDbClassType = "FakeDbClassType";
	static final String kPQOpenUrlBase = "www.proquest.com/base/url:fake";
	static final String kSchoolBatchSize = "5";
	
	public static Properties makePropertyMapForTesting() {
		Properties props = new Properties();
		props.setProperty(DisMetadataProperties.EXODUS_DB_URL, kExodusDbUrl);
		props.setProperty(DisMetadataProperties.EXODUS_USER_NAME, kExodusUserName);
		props.setProperty(DisMetadataProperties.EXODUS_PASSWORD, kExodusUserPassword);
		props.setProperty(DisMetadataProperties.EXODUS_POOL_SIZE, kExodusPoolSize);
		props.setProperty(DisMetadataProperties.EXODUS_DB_CLASSTYPE, kExodusDbClassType);
		props.setProperty(DisMetadataProperties.PQ_OPEN_URL_BASE, kPQOpenUrlBase);
		props.setProperty(DisMetadataProperties.SCHOOL_BATCH_SIZE, kSchoolBatchSize);
		return props;
	}
	
	DisMetadataProperties target;
	
	@Before
	public void setUp() throws Exception {
		target = new DisMetadataProperties(
				DisMetadataProperties_Tests.makePropertyMapForTesting());
	}
	
	@Test
	public void hasCorrect_ExodusJdbcConfig() throws Exception{
		JdbcConfig expectedConnectionConfig = new JdbcConfig(
				kExodusDbUrl, kExodusUserName, kExodusUserPassword, 
				kExodusDbClassType, Integer.parseInt(kExodusPoolSize));
		
		MyAsserts.assertEqual(target.getExodusJdbcConfig(), expectedConnectionConfig);
	}
	
	@Test
	public void hasCorrect_PqOpenUrlBase() throws Exception{
		assertThat(target.getPqOpenUrlBase(), is(kPQOpenUrlBase));
	}
	
	@Test
	public void hasCorrect_SchoolBatchSize() throws Exception{
		assertThat(target.getSchoolBatchSize(), is(Integer.parseInt(kSchoolBatchSize)));
	}
}
