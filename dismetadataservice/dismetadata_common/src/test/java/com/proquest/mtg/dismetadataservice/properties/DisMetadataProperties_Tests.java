package com.proquest.mtg.dismetadataservice.properties;

import java.util.Properties;


import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
//import com.proquest.mtg.dismetadataservice.helper.MyAsserts;
//import com.proquest.mtg.dismetadataservice.jdbc.JdbcConfig;

public class DisMetadataProperties_Tests {

	static final String kExodusDbUrl = "FakeURL";
	static final String kExodusUserName = "FakeUserName";
	static final String kExodusUserPassword = "FakeUserPassword";
	static final String kExodusPoolSize = "5";
	static final String kExodusDbClassType = "FakeDbClassType";
	static final String kPQOpenUrlBase = "www.proquest.com/base/url:fake";
	static final String kSchoolBatchSize = "5";
	static final String kServiceUrlBase = "www.service.proquest.com/base/url:fake";
	static final String kPqserviceTimeout = "30";
	static final String kUserAgent = "fakeAgent";
	static final String kFopExodusUserName = "FakeFopUserName";
	static final String kFopExodusUserPassword = "FakeFopUserPassword";
	static final String kEcmsMr3HeaderKey = "fakeAgent";
	static final String kEcmsMr3HeaderValue = "fakeAgent";
	static final String kMr3ServiceUrlBase = "www.proquest.com/base/url:fake";
	static final String kecmsServiceUrlBase = "www.proquest.com/base/url:fake";
	static final String kMr3FopServiceUrlBase = "www.proquest.com/base/url:fake";
	static final String kVmsDbUrlBase = "www.proquest.com/base/url:fake";
	static final String kVmsDbUserName = "FakeVmsUserName";
	static final String kVmsDbUserPassword = "FakeVmsUserPassword";
	static final String kFopDbUrlBase = "www.proquest.com/base/url:fake";
	static final String kFopDbUserName = "FakeFopUserName";
	static final String kFopDbUserPassword = "FakeFopPassword";
	
	public static Properties makePropertyMapForTesting() {
		Properties props = new Properties();
		props.setProperty(DisMetadataProperties.EXODUS_DB_URL, kExodusDbUrl);
		props.setProperty(DisMetadataProperties.EXODUS_USER_NAME, kExodusUserName);
		props.setProperty(DisMetadataProperties.EXODUS_PASSWORD, kExodusUserPassword);
		props.setProperty(DisMetadataProperties.EXODUS_POOL_SIZE, kExodusPoolSize);
		props.setProperty(DisMetadataProperties.EXODUS_DB_CLASSTYPE, kExodusDbClassType);
		props.setProperty(DisMetadataProperties.PQ_OPEN_URL_BASE, kPQOpenUrlBase);
		props.setProperty(DisMetadataProperties.SCHOOL_BATCH_SIZE, kSchoolBatchSize);
		props.setProperty(DisMetadataProperties.PQ_SERVICE_URL_BASE, kServiceUrlBase);
		props.setProperty(DisMetadataProperties.PQ_SERVICE_TIMEOUT_MS, kPqserviceTimeout);
		props.setProperty(DisMetadataProperties.PQ_SERVICE_USER_AGENT, kUserAgent);
		props.setProperty(DisMetadataProperties.FOP_EXODUS_USER_NAME, kFopExodusUserName);
		props.setProperty(DisMetadataProperties.FOP_EXODUS_PASSWORD, kFopExodusUserPassword);
		props.setProperty(DisMetadataProperties.ECMS_MR3_HEADER_KEY,kEcmsMr3HeaderKey);
		props.setProperty(DisMetadataProperties.ECMS_MR3_HEADER_VALUE, kEcmsMr3HeaderValue);
		props.setProperty(DisMetadataProperties.MR3_SERVICE_URL_BASE, kMr3ServiceUrlBase);
		props.setProperty(DisMetadataProperties.ECMS_SERVICE_URL_BASE, kecmsServiceUrlBase);
		props.setProperty(DisMetadataProperties.MR3_SERVICE_FOP_URL_BASE, kMr3FopServiceUrlBase);
		props.setProperty(DisMetadataProperties.VMS_DB_URL, kVmsDbUrlBase);
		props.setProperty(DisMetadataProperties.VMS_DB_USER_NAME, kVmsDbUserName);
		props.setProperty(DisMetadataProperties.VMS_DB_PASSWORD, kVmsDbUserPassword);
		props.setProperty(DisMetadataProperties.FOP_DB_URL, kFopDbUrlBase);
		props.setProperty(DisMetadataProperties.FOP_DB_USER_NAME, kFopDbUserName);
		props.setProperty(DisMetadataProperties.FOP_DB_PASSWORD, kFopDbUserPassword);
		return props;
	}
	
	DisMetadataProperties target;
	
	@Before
	public void setUp() throws Exception {
		target = new DisMetadataProperties(
				DisMetadataProperties_Tests.makePropertyMapForTesting());
	}
	
	/*@Test
	public void hasCorrect_ExodusJdbcConfig() throws Exception{
		JdbcConfig expectedConnectionConfig = new JdbcConfig(
				kExodusDbUrl, kExodusUserName, kExodusUserPassword, 
				kExodusDbClassType, Integer.parseInt(kExodusPoolSize));
		
		//MyAsserts.assertEqual(target.getExodusJdbcConfig(), expectedConnectionConfig);
	}
	
	@Test
	public void hasCorrect_FopExodusJdbcConfig() throws Exception{
		JdbcConfig expectedFopConnectionConfig = new JdbcConfig(
				kExodusDbUrl, kFopExodusUserName, kFopExodusUserPassword, 
				kExodusDbClassType, Integer.parseInt(kExodusPoolSize));
		
		//MyAsserts.assertEqual(target.getFopExodusConfig(), expectedFopConnectionConfig);
	}*/
	
	@Test
	public void hasCorrect_PqOpenUrlBase() throws Exception{
		assertThat(target.getPqOpenUrlBase(), is(kPQOpenUrlBase));
		
	}
	
	@Test
	public void hasCorrect_SchoolBatchSize() throws Exception{
		assertThat(target.getSchoolBatchSize(), is(Integer.parseInt(kSchoolBatchSize)));
	}
	
	@Test
	public void hasCorrect_ServiceUrlBase() throws Exception {
		assertThat(target.getPQServiceURL(), is(kServiceUrlBase));
	}
	
	@Test
	public void hasCorrect_PqServiceTimeoutMS() throws Exception {
		assertThat(target.getPqServiceTimeoutMS(), is(Integer.parseInt(kPqserviceTimeout)));
	}
	
	@Test
	public void hasCorrect_PqServiceUserAgent() throws Exception {
		assertThat(target.getPqServiceUserAgent(), is(kUserAgent));
	}
	
	@Test
	public void hasCorrect_Mr3ServiceUrlBase() throws Exception{
		assertThat(target.getPqOpenUrlBase(), is(kMr3ServiceUrlBase));
	}
	
	
	@Test
	public void hasCorrect_ecmsServiceUrlBase() throws Exception{
		assertThat(target.getPqOpenUrlBase(), is(kecmsServiceUrlBase));
	}
	
	@Test
	public void hasCorrect_EcmsMr3HeaderKey() throws Exception {
		assertThat(target.getPqServiceUserAgent(), is(kEcmsMr3HeaderKey));
	}
	
	@Test
	public void hasCorrect_EcmsMr3HeaderValue() throws Exception {
		assertThat(target.getPqServiceUserAgent(), is(kEcmsMr3HeaderValue));
	}
	
	
	@Test
	public void hasCorrect_EcmsMr3FopHeaderValue() throws Exception {
		assertThat(target.getMr3FopServiceURL(), is(kMr3FopServiceUrlBase));
	}
	
	@Test
	public void hasCorrect_VmsDbUrl() throws Exception {
		assertThat(target.getVmDbUrl(), is(kVmsDbUrlBase));
	}
	
	@Test
	public void hasCorrect_VmsDbUserName() throws Exception {
		assertThat(target.getVmDbUserName(), is(kVmsDbUserName));
	}
	
	@Test
	public void hasCorrect_VmsDbPassword() throws Exception {
		assertThat(target.getVmDbPassword(), is(kVmsDbUserPassword));
	}
	
	
}