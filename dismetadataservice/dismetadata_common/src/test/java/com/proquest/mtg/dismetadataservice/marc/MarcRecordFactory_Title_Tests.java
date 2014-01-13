package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class MarcRecordFactory_Title_Tests {
	
	DisGenMappingProvider disGenMappingProvider;
	
	Title title;
	MarcRecordFactory factory;
	
	@Before
	public void setUp() throws Exception {
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider(connectionPool);
		title = new Title();
		factory = new MarcRecordFactory(disGenMappingProvider);
	}
	
	@Test
	public void withNoTitle_ShouldNotGenerateTag() {
		title.setEnglishOverwriteTitle(null);
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
	@Test
	public void withAllTitle_ShouldUseForeignTitle() {
		title.setEnglishOverwriteTitle("English Title");
		title.setMasterTitle("Master Title");
		title.setForeignTitle("Foreign Title");
		title.setElectronicTitle("Electronic Title");
		
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a" 
					+ "Foreign Title.";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void exceptForeignTitle_ShouldUseMasterTitle() {
		title.setEnglishOverwriteTitle("English Title");
		title.setMasterTitle("Master Title");
		title.setElectronicTitle("Electronic Title");
		
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a" 
					+ "Master Title.";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void exceptEnglishTitle_ShouldUseElectronicTitle() {
		title.setMasterTitle("Master Title");
		title.setForeignTitle("Foreign Title");
		title.setElectronicTitle("Electronic Title");
		
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a" 
					+ "Electronic Title.";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void exceptEnglishAndElectronicTitle_ShouldUseMasterTitle() {
		title.setMasterTitle("Master Title");
		title.setForeignTitle("Foreign Title");
		
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a" 
					+ "Master Title.";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	
	@Test
	public void withMatchingSecondFieldIndicatior1() {
		title.setEnglishOverwriteTitle("English Title");
		title.setMasterTitle("Master Title");
		title.setForeignTitle("A Foreign Title");
		title.setElectronicTitle("Electronic Title");
		
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		String expectedData = "12" + MarcCharSet.kSubFieldIndicator + "a" 
					+ "A Foreign Title.";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withMatchingSecondFieldIndicatior2() {
		title.setEnglishOverwriteTitle("English Title");
		title.setMasterTitle("Master Title");
		title.setForeignTitle("\"Le Foreign Title");
		title.setElectronicTitle("Electronic Title");
		
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		String expectedData = "14" + MarcCharSet.kSubFieldIndicator + "a" 
					+ "\"Le Foreign Title.";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	

}
