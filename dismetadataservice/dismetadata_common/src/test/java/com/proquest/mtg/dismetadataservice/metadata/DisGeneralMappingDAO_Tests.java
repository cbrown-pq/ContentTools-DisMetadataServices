package com.proquest.mtg.dismetadataservice.metadata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;

public class DisGeneralMappingDAO_Tests {
	
	static String kMarcMapping = "MARC_245_IND";
	static int kMappingCount = 34;
	
	DisGeneralMappingDAO target;
	
	@Before
	public void setUp() throws Exception {		
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		target = new DisGeneralMappingDAO(connectionPool.getConnection());
	}
	
	@Test
	public void hasCorrect_MarcMappingCount() throws SQLException {
		List<DisGeneralMapping> disGeneralMapping = target.getAllMappingFor(kMarcMapping);
		assertThat(disGeneralMapping.size(), is(kMappingCount));
	}
	

}
