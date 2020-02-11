package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.Author;

public class USMarcRecordFactory_MakeMultipleAuthors_Tests extends
		USMarcRecordFactoryBase_Test_Helper {
	String tag = MarcTags.kMulitpleAuthor;

	DisPubMetaData metaData;
	Author author;
	List<Author> authors;
	String expectedMarcFieldData1;
	String expectedMarcFieldData2;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		author = new Author();
		authors = new ArrayList<Author>();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNullVariantTitle() {
		author.setAltAuthorFullName(null);
		authors.add(author);
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyVariantTitle() {
		author.setAltAuthorFullName("");
		authors.add(author);
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNoAuthors() {
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withAdditionalAuthorAndNoSequenceNumber() {
		String authorName = "John King";
		author.setAuthorFullName(authorName);
		authors.add(author);
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withAdditionalAuthorAndInValidSequenceNumber() {
		String authorName = "John King";
		author.setAuthorFullName(authorName);
		author.setSequenceNumber(1);
		authors.add(author);
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withAdditionalAuthorAndValidSequenceNumber() {
		String authorName = "John King";
		author.setAuthorFullName(authorName);
		author.setSequenceNumber(2);
		authors.add(author);
		metaData.setAuthors(authors);
		expectedMarcFieldData1 = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ authorName + MarcCharSet.kSubFieldIndicator + "ejoint author";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData1));
	}

	@Test
	public void withAdditionalAuthorsAndValidSequenceNumber() {
		String authorName1 = "John King";
		String authorName2 = "Ben Jerry ";
		author.setAuthorFullName(authorName1);
		author.setSequenceNumber(2);
		authors.add(author);
		Author author2 = new Author();
		author2.setAuthorFullName(authorName2);
		author2.setSequenceNumber(2);
		authors.add(author2);
		metaData.setAuthors(authors);
		expectedMarcFieldData1 = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ authorName1 + MarcCharSet.kSubFieldIndicator
				+ "ejoint author";
		expectedMarcFieldData2 = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ authorName2 + MarcCharSet.kSubFieldIndicator
				+ "ejoint author";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(2));
		assertThat(fieldsMatchingTag.get(0).getData(),
				is(expectedMarcFieldData1));
		assertThat(fieldsMatchingTag.get(1).getData(),
				is(expectedMarcFieldData2));
	}

}
