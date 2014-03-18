package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;

public class USMarcRecordFactory_MakeDegrees_Tests extends
USMarcRecordFactoryBase_Test_Helper {
	String tag1 = MarcTags.kDegreeName;
	String tag2 = MarcTags.kDegreeDate;
	String expectedMarcFieldData1,expectedMarcFieldData2;
	DisPubMetaData metaData;
	Author author;
	Degree degree;
	List<Degree> degrees;
	List<Author> authors;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		author = new Author();
		degree = new Degree();
		degrees = new ArrayList<Degree>();
		authors = new ArrayList<Author>();
		metaData = new DisPubMetaData();
	}
	
	@Test
	public void withNullDegreeCodeAndYear() {
		degree.setDegreeCode(null);
		degree.setDegreeYear(null);
		degrees.add(degree);
		author.setDegrees(degrees);
		
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag1);
		verifyMarcRecordHasEmptyField(metaData, tag2);
	}
	
	@Test	
	public void withEmptyDegreeCodeAndYear() {
		
		degree.setDegreeCode("");
		degree.setDegreeYear("");
		author = new Author();
		degrees = new ArrayList<Degree>();
		degrees.add(degree);
		author.setDegrees(degrees);
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag1);
		verifyMarcRecordHasEmptyField(metaData, tag2);
	}

	@Test
	public void withEmptyAuthor() {
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag1);
		verifyMarcRecordHasEmptyField(metaData, tag2);
	}
	
	@Test
	public void withDegreeAndInValidSequenceNumber() {
		Degree degree = new Degree();
		degree.setDegreeCode("DegreeCode");
		degree.setDegreeYear("DegreeDate");
		degree.setSequenceNumber(0);
		degrees.add(degree);
		author.setDegrees(degrees);
		authors.add(author);
		metaData.setAuthors(authors);
		verifyMarcRecordHasEmptyField(metaData, tag1);
		verifyMarcRecordHasEmptyField(metaData, tag2);
	}
	
	@Test
	public void withDegreeCodeAndNoDegreeYear() {
		
		degree.setDegreeCode("DegreeCode");
		degree.setDegreeYear("");
		degree.setSequenceNumber(1);
		degrees.add(degree);
		author.setDegrees(degrees);
		authors.add(author);
		metaData.setAuthors(authors);
		expectedMarcFieldData1 = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "DegreeCode";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag1);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData1));
		List<MarcField> fieldsMatchingTag2 = marc.getFieldsMatchingTag(tag2); 
		assertThat(fieldsMatchingTag2.size(), is(0));
	}
	
	@Test
	public void withDegreeYearAndNoDegreeCode() {
		
		degree.setDegreeCode("");
		degree.setDegreeYear("DegreeYear");
		degree.setSequenceNumber(1);
		degrees.add(degree);
		author.setDegrees(degrees);
		authors.add(author);
		metaData.setAuthors(authors);
		expectedMarcFieldData2 = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "DegreeYear";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag1); 
		assertThat(fieldsMatchingTag1.size(), is(0));
		List<MarcField> fieldsMatchingTag2 = marc.getFieldsMatchingTag(tag2);
		assertThat(fieldsMatchingTag2.size(), is(1));
		assertThat(fieldsMatchingTag2.get(0).getData(),
				is(expectedMarcFieldData2));
		
	}
	
	@Test
	public void withDegreeAndValidSequenceNumber() {
		degree.setDegreeCode("DegreeCode");
		degree.setDegreeYear("DegreeDate");
		degree.setSequenceNumber(1);
		degrees.add(degree);
		author.setDegrees(degrees);
		
		authors.add(author);
		metaData.setAuthors(authors);
		expectedMarcFieldData1 = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "DegreeCode";
		expectedMarcFieldData2 = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "DegreeDate";
		
		MarcRecord marc = factory.makeFrom(metaData);
		
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag1);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData1));
		List<MarcField> fieldsMatchingTag2 = marc.getFieldsMatchingTag(tag2);
		assertThat(fieldsMatchingTag2.size(), is(1));
		assertThat(fieldsMatchingTag2.get(0).getData(),
				is(expectedMarcFieldData2));
	}
	

}
