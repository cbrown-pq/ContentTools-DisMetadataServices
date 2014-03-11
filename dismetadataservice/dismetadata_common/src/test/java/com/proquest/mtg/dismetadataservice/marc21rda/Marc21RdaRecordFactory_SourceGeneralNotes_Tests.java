package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class Marc21RdaRecordFactory_SourceGeneralNotes_Tests {

	DisGenMappingProvider disGenMappingProvider;
	Marc21RdaRecordFactory factory;
	Batch batch;
	String tag;

	@Before
	public void setUp() throws Exception {
		IJdbcConnectionPool connectionPool = JdbcHelper
				.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider(
				connectionPool);
		factory = new Marc21RdaRecordFactory(disGenMappingProvider);
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
	public void withDAIDbCode_NullVolIss_NullPageNum() {
		batch = new Batch();
		batch.setDBTypeCode("DAI");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue(null);
		batch.setDAISectionCode(null);

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubPageNum(null);
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Source: Dissertation Abstracts International" + ".";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withDAIDbCode_VolIss_NullPageNum() {
		batch = new Batch();
		batch.setDBTypeCode("DAI");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("74-07(E)");
		batch.setDAISectionCode(null);

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubPageNum(null);
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Source: Dissertation Abstracts International" 
				+ ", Volume: 74-07(E)" 
				+ ".";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withDAIDbCode_VolIss_WithSectionAndPage() {
		batch = new Batch();
		batch.setDBTypeCode("DAI");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("74-07(E)");
		batch.setDAISectionCode("A");

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubPageNum("1473");
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Source: Dissertation Abstracts International" 
				+ ", Volume: 74-07(E)" 
				+ ", Section: A" 
				+ ", page: 1473"
				+ ".";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withMAIDbCode_VolIss_WithSectionAndPage() {
		batch = new Batch();
		batch.setDBTypeCode("MAI");
		batch.setDBTypeDesc("Masters Abstracts International");
		batch.setVolumeIssue("51-01(E)");
		batch.setDAISectionCode("");

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubPageNum("1473");
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Source: Masters Abstracts International" 
				+ ", Volume: 51-01" 
				+ ", page: 1473"
				+ ".";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withMAIDbCode_VolIss_WithSection_NullPageNum() {
		batch = new Batch();
		batch.setDBTypeCode("MAI");
		batch.setDBTypeDesc("Masters Abstracts International");
		batch.setVolumeIssue("51-01(E)");
		batch.setDAISectionCode("");

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubPageNum("");
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Source: Masters Abstracts International" 
				+ ", Volume: 51-01" 
				+ ".";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

}
