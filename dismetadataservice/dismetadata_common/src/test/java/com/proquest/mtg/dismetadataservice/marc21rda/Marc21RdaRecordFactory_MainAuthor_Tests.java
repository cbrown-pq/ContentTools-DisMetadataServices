package com.proquest.mtg.dismetadataservice.marc21rda;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.metadata.SGMLEntitySubstitution;

public class Marc21RdaRecordFactory_MainAuthor_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	DisGenMappingProvider disGenMappingProvider;
	Author author;
	List<Author> authors;
	Degree degree;
	List<Degree> degrees;

	DissLanguage language;
	List<DissLanguage> languages;
	String tag;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		tag = MarcTags.kAuthor;
		author = new Author();
	}

	@Test
	public void withNullAuthorName_ShouldNotGenerateTag() {
		author.setAuthorFullName(null);
		authors = Lists.newArrayList(author);
		String tag = MarcTags.kAuthor;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyAuthorName_ShouldNotGenerateTag() {
		author.setAuthorFullName("");
		authors = Lists.newArrayList(author);
		String tag = MarcTags.kAuthor;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void authorWithoutSGMLChar() {
		author.setAuthorFullName("John C Mark");
		authors = Lists.newArrayList(author);
		String tag = MarcTags.kAuthor;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAuthors(authors);
		String expectedData = "1 " + MarcCharSet.kSubFieldIndicator + "a"
				+ "John C Mark" + "," 
				+  MarcCharSet.kSubFieldIndicator 
				+ "e"
				+ "author"
				+ ".";
		verifyMarcRecordHasCorrectCount(metaData, tag, expectedData, 1);
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void authorWithSGMLChar() {
		String authorFullName = "&prime;John C &AElig; Mark@?";
		authorFullName = SGMLEntitySubstitution.applyAllTo(authorFullName);
		author.setAuthorFullName(authorFullName);
		authors = Lists.newArrayList(author);
		String tag = MarcTags.kAuthor;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAuthors(authors);
		String expectedData = "1 " + MarcCharSet.kSubFieldIndicator + "a"
				+ authorFullName + "," 
				+  MarcCharSet.kSubFieldIndicator 
				+ "e"
				+ "author"
				+ ".";
		verifyMarcRecordHasCorrectCount(metaData, tag, expectedData, 1);
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

}
