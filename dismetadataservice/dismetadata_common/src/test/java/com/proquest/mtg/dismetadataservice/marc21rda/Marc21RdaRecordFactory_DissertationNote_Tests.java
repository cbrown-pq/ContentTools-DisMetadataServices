package com.proquest.mtg.dismetadataservice.marc21rda;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;

public class Marc21RdaRecordFactory_DissertationNote_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	String tag = MarcTags.kDissertationNote;
	String expectedMarcFieldData;
	DisPubMetaData metaData;
	School school;
	Degree degree;
	List<Degree> degrees;
	Author author;
	List<Author> authors;

	@Before
	public void setup() {
		metaData = new DisPubMetaData();
		school = new School();
		degree = new Degree();
		author = new Author();
	}

	@Test
	public void withNull() {
		school.setSchoolName(null);
		degree.setDegreeCode(null);
		degree.setDegreeYear(null);
		metaData.setSchool(school);
		degrees = Lists.newArrayList(degree);
		author.setDegrees(degrees);
		authors = Lists.newArrayList(author);
		
		metaData.setAuthors(authors);
		metaData.setSchool(school);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmpty() {
		school.setSchoolName("");
		degree.setDegreeCode("");
		degree.setDegreeYear("");
		metaData.setSchool(school);
		degrees = Lists.newArrayList(degree);
		author.setDegrees(degrees);
		authors = Lists.newArrayList(author);
		metaData.setAuthors(authors);
		metaData.setSchool(school);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withDissNote() {
		school.setSchoolName("HARVARD UNIVERSITY");
		degree.setDegreeCode("PH.D");
		degree.setDegreeYear("1984");
		metaData.setSchool(school);
		degrees = Lists.newArrayList(degree);
		author.setDegrees(degrees);
		authors = Lists.newArrayList(author);
		metaData.setAuthors(authors);
		metaData.setSchool(school);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator 
								     + "bPH.D"
								     + MarcCharSet.kSubFieldIndicator 
								     + "cHARVARD UNIVERSITY"
								     + MarcCharSet.kSubFieldIndicator 
								     + "d1984"
								     + ".";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}

}
