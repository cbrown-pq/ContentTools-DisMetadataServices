package com.proquest.mtg.dismetadataservice.exodus;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject;

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
		dissertationList = target.geDataFor();
		assertThat( dissertationList, notNullValue());
	}

}
