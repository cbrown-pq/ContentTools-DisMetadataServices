package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
//import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
//import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.metadata.SGMLEntitySubstitution;
import com.proquest.mtg.dismetadataservice.usmarc.USMarcRecordFactory;

public class USMarcRecordFactory_Author_Tests {
	
static int kDataIndependentFieldCount = 3;
	
	DisGenMappingProvider disGenMappingProvider;
	Author author;
	List<Author> authors;
	Degree degree;
	List<Degree> degrees;
	
	DissLanguage language;
	List<DissLanguage> languages;
	
	USMarcRecordFactory factory;
	
	@Before
	public void setUp() throws Exception {
		//IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider();
		factory = new USMarcRecordFactory(disGenMappingProvider);
	}
	
	@Test
	public void withNullAuthorName_ShouldNotGenerateTag() {
		author = new Author();
		authors = Lists.newArrayList(author);
		String tag = MarcTags.kAuthor;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAuthors(authors);
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
	@Test
	public void authorWithoutSGMLChar() {
		author = new Author();
		author.setAuthorFullName("John C Mark");
		authors = Lists.newArrayList(author);
		String tag = MarcTags.kAuthor;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAuthors(authors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "1 " + MarcCharSet.kSubFieldIndicator + "a" + "John C Mark" + ".";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void authorWithSGMLChar() {
		author = new Author();
		String authorFullName = "&prime;John C &AElig; Mark@?";
		authorFullName = SGMLEntitySubstitution.applyAllTo(authorFullName);
		author.setAuthorFullName(authorFullName);
		authors = Lists.newArrayList(author);
		String tag = MarcTags.kAuthor;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAuthors(authors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "1 " + MarcCharSet.kSubFieldIndicator + "a" + authorFullName + ".";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

}
