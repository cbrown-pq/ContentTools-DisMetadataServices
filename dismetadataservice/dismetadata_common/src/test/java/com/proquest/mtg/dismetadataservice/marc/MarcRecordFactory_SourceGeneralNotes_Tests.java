package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class MarcRecordFactory_SourceGeneralNotes_Tests {

	DisGenMappingProvider disGenMappingProvider;
	MarcRecordFactory factory;
	Batch batch;
	String tag;
	
	@Before
	public void setUp() throws Exception {
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider(connectionPool);
		factory = new MarcRecordFactory(disGenMappingProvider);
		tag = MarcTags.kGeneralNote;
	}
	
	@Test
	public void withNoBatch_NoTag() {
		batch = null; 
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
	@Test
	public void withNullDbCode_NoTag() {
		batch = new Batch();
		batch.setDBTypeCode(null);
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
	@Test
	public void withDAIDbCode_NullVolIss_NullPageNul() {
		batch = new Batch();
		batch.setDBTypeCode("DAI");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue(null);
		batch.setDAISectionCode(null);

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubPageNum(null);
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "  " + MarcCharSet.kSubFieldIndicator 
			+ "a" + "Source: Dissertation Abstracts International" + ".";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
}
