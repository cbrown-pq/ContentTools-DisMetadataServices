package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.marc21rda.Marc21RdaRecordFactory;

public class Marc21RdaRecordFactory_Test_Helper {

	
	static int kDataIndependentFieldCount = 4;
	IJdbcConnectionPool connectionPool;
	DisGenMappingProvider disGenMappingProvider;
	
	Marc21RdaRecordFactory factory;
	
	@Before
	public void setUp() throws Exception {
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider(connectionPool);
		factory = new Marc21RdaRecordFactory(disGenMappingProvider);
	}
	
	public void verifyMarcRecordHasCorrectCount(DisPubMetaData metaData, String tag, String expectedMarcFieldData, int dataSpecificFieldCount) {
		MarcRecord marc = factory.makeFrom(metaData);
		assertThat(marc.getFieldCount(), is(dataSpecificFieldCount + kDataIndependentFieldCount));
	}
	
	public void verifyMarcRecordHasCorrectField(DisPubMetaData metaData, String tag, String expectedMarcFieldData, int dataSpecificFieldCount) {
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedMarcFieldData));
	}
	
	public void verifyMarcRecordHasEmptyField(DisPubMetaData metaData, String tag) {
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}


}
