package com.proquest.mtg.dismetadataservice.properties;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class DisMetadataProperties_Invalid_Tests {

	private Properties props;

	@Before
	public void setUp() throws Exception {
		props = DisMetadataProperties_Tests.makePropertyMapForTesting();

		assertThat("Test Setup", new DisMetadataProperties(props),
				notNullValue());
	}

	/*@Test(expected = IllegalArgumentException.class)
	public void throws_WhenMissing_JdbcURL() throws Exception {
		props.remove(DisMetadataProperties.EXODUS_DB_URL);
		new DisMetadataProperties(props);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throws_WhenMissing_ExodusUserName() throws Exception {
		props.remove(DisMetadataProperties.EXODUS_USER_NAME);
		new DisMetadataProperties(props);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throws_WhenMissing_ExodusPassword() throws Exception {
		props.remove(DisMetadataProperties.EXODUS_PASSWORD);
		new DisMetadataProperties(props);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throws_WhenMissing_ExodusPoolSize() throws Exception {
		props.remove(DisMetadataProperties.EXODUS_POOL_SIZE);
		new DisMetadataProperties(props);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throws_WhenMissing_ExodusDBClassType() throws Exception {
		props.remove(DisMetadataProperties.EXODUS_DB_CLASSTYPE);
		new DisMetadataProperties(props);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throws_WhenMissing_FopExodusUserName() throws Exception {
		props.remove(DisMetadataProperties.FOP_EXODUS_USER_NAME);
		new DisMetadataProperties(props);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throws_WhenMissing_FopExodusPassword() throws Exception {
		props.remove(DisMetadataProperties.FOP_EXODUS_PASSWORD);
		new DisMetadataProperties(props);
	}*/
	
	@Test(expected = IllegalArgumentException.class)
	public void throws_WhenMissing_EcmsMr3HeaderKey() throws Exception {
		props.remove(DisMetadataProperties.ECMS_MR3_HEADER_KEY);
		new DisMetadataProperties(props);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throws_WhenMissing_EcmsMr3HeaderValue() throws Exception {
		props.remove(DisMetadataProperties.ECMS_MR3_HEADER_VALUE);
		new DisMetadataProperties(props);
	}
	
	  @Test(expected = IllegalArgumentException.class)
	  public void throws_WhenMissing_Mr3ServiceUrlBase() throws Exception {
	  props.remove(DisMetadataProperties.MR3_SERVICE_URL_BASE);
	  new DisMetadataProperties(props);
	}

}
