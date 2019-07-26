package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
//import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
//import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class USMarcRecordFactory_EnglishTranslationOfTitle_Tests {

	DisGenMappingProvider disGenMappingProvider;

	Title title;
	USMarcRecordFactory factory;

	@Before
	public void setUp() throws Exception {
		//IJdbcConnectionPool connectionPool = JdbcHelper
		//		.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider();
		title = new Title();
		factory = new USMarcRecordFactory(disGenMappingProvider);
	}

	@Test
	public void withNoEnglishOverwriteTitle_ShouldNotGenerateTag() {
		title.setEnglishOverwriteTitle(null);
		String tag = MarcTags.kEnglishTranslationOfTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(0));
	}

	@Test
	public void withEnglishOverwriteTitle_AndNoElectronicTitle() {
		title.setEnglishOverwriteTitle("English Title");
		String tag = MarcTags.kEnglishTranslationOfTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		String expectedData = "00" + MarcCharSet.kSubFieldIndicator + "a"
				+ "English Title." + MarcCharSet.kSubFieldIndicator + "yeng";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withEnglishOverwriteTitle_AndElectronicTitle() {
		title.setEnglishOverwriteTitle("English Title");
		title.setEnglishOverwriteTitle("Electronic Title");
		String tag = MarcTags.kEnglishTranslationOfTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		String expectedData = "00" + MarcCharSet.kSubFieldIndicator + "a"
				+ "Electronic Title." + MarcCharSet.kSubFieldIndicator + "yeng";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

}
