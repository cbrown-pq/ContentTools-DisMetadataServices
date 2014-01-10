package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class MarcRecordFactoryBase_Tests {
	
	static int kDataIndependentFieldCount = 3;
	IJdbcConnectionPool connectionPool;
	DisGenMappingProvider disGenMappingProvider;
	
	MarcRecordFactory factory;
	
	@Before
	public void setUp() throws Exception {
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider(connectionPool);
		factory = new MarcRecordFactory(disGenMappingProvider);
	}
	
	public void verifyMarcRecordHasCorrectField(DisPubMetaData metaData, String tag, String expectedMarcFieldData, int dataSpecificFieldCount) {
		MarcRecord marc = factory.makeFrom(metaData);
		assertThat(marc.getFieldCount(), is(dataSpecificFieldCount + kDataIndependentFieldCount));
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedMarcFieldData));
	}

}
