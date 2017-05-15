package com.proquest.mtg.dismetadataservice.exodus;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;

public class ExternalUrlDataProvider_Tests {
	
	static ExternalUrlDataProvider target;
	
	@BeforeClass
	public static void setUp() throws Exception {		
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		target = new ExternalUrlDataProvider(connectionPool);
	}

	@Test
	public void getAllSubjectsReturnNotNullValue() throws Exception {
		DissertationList dissertationList;
		dissertationList = target.geDataFor("20170427");
		assertThat( dissertationList, notNullValue());
	}
	
	@Test
	@Ignore
	public void updateExternalUrlReturnsSuccess() throws Exception {
		String expectedUpdateReturnVal = "Update successful"; 
		String actualUpdateReturnVal = target.updateUrlStatus("10062892", "Valid");
		assertEquals( actualUpdateReturnVal, expectedUpdateReturnVal);
	}
	
	@Test
	@Ignore
	public void updateExternalUrlReturnsPubNotFoundError() throws Exception {
		String expectedUpdateReturnVal = "Error! Pub not found"; 
		String actualUpdateReturnVal = target.updateUrlStatus("9999999999", "Valid");
		assertEquals( actualUpdateReturnVal, expectedUpdateReturnVal);
	}
}
