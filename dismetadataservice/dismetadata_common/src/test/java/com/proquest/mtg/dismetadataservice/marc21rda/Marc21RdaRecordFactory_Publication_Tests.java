package com.proquest.mtg.dismetadataservice.marc21rda;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;

public class Marc21RdaRecordFactory_Publication_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	Author author;
	List<Author> authors;
	Degree degree;
	List<Degree> degrees;

	DisPubMetaData metaData;
	String tag;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		tag = MarcTags.kPublication;
		degree = createDegree();
		author = new Author();
		authors = Lists.newArrayList();
		metaData = new DisPubMetaData();
	}

	private Degree createDegree() {
		Degree degree = new Degree();
		degree.setSequenceNumber(1);
		return degree;
	}

	@Test
	public void withNullDegreeYear_ShouldGenerateTag() {
		degree.setDegreeYear(null);
		author.setDegrees(Lists.newArrayList(degree));
		authors.add(author);
		metaData.setAuthors(authors);
		String expectedData = " 1" + MarcCharSet.kSubFieldIndicator + "a"
				+ "Ann Arbor : " + MarcCharSet.kSubFieldIndicator
				+ "bProQuest Dissertations & Theses.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void withEmptyDegree_ShouldGenerateTag() {
		degree.setDegreeYear("");
		author.setDegrees(Lists.newArrayList(degree));
		authors.add(author);
		metaData.setAuthors(authors);
		String expectedData = " 1" + MarcCharSet.kSubFieldIndicator + "a"
				+ "Ann Arbor : " + MarcCharSet.kSubFieldIndicator
				+ "bProQuest Dissertations & Theses.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void withDegreeAndLanguageInfo() {
		degree.setDegreeYear("2012");
		author.setDegrees(Lists.newArrayList(degree));
		authors.add(author);
		metaData.setAuthors(authors);
		String expectedData = " 1" + MarcCharSet.kSubFieldIndicator + "a"
				+ "Ann Arbor : " + MarcCharSet.kSubFieldIndicator
				+ "bProQuest Dissertations & Theses, "
				+ MarcCharSet.kSubFieldIndicator + "c" + degree.getDegreeYear();
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

}
