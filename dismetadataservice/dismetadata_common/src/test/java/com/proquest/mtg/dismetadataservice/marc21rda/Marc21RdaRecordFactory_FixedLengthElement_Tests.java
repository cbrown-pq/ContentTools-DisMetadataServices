package com.proquest.mtg.dismetadataservice.marc21rda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;

public class Marc21RdaRecordFactory_FixedLengthElement_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	String fixedElementString = "    miu||||||m   |||||||";

	Author author;
	List<Author> authors;
	Degree degree;
	List<Degree> degrees;

	DissLanguage language;
	List<DissLanguage> languages;

	DisPubMetaData metaData;
	String tag;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		tag = MarcTags.kFiexedLengthDataElements;
		degree = createDegree();
		author = new Author();
		author.setDegrees(Lists.newArrayList(degree));
		authors = Lists.newArrayList(author);
		metaData = new DisPubMetaData();
	}

	private Degree createDegree() {
		Degree degree = new Degree();
		degree.setSequenceNumber(1);
		return degree;
	}

	@Test
	public void withDegreeAndLanguageInfo() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("YYMMdd").format(now);

		degree.setDegreeYear("2012");
		metaData.setAuthors(authors);
		DissLanguage language = new DissLanguage("English", "EN");
		metaData.setDissLanguages(Lists.newArrayList(language));
		String expectedData = curTime + "s" + "2012" + fixedElementString
				+ "eng" + " d";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);

	}
	
	@Test
	public void withDegreeAndMultipleLanguagesInfo() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("YYMMdd").format(now);

		degree.setDegreeYear("2012");
		metaData.setAuthors(authors);
		DissLanguage language = new DissLanguage("Estonian, Russian, and English", "EZ");
		metaData.setDissLanguages(Lists.newArrayList(language));
		String expectedData = curTime + "s" + "2012" + fixedElementString
				+ "est" + " d";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);

	}

	@Test
	public void withNullDegreeYearAndLanguageInfo() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("YYMMdd").format(now);
		String tag = MarcTags.kFiexedLengthDataElements;
		degree.setDegreeYear(null);
		metaData.setAuthors(authors);
		DissLanguage language = new DissLanguage("English", "EN");
		metaData.setDissLanguages(Lists.newArrayList(language));
		String expectedData = curTime + "n" + fixedElementString + "eng" + " d";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 2);
	}

	@Test
	public void withEmptyDegreeYearAndLanguageInfo() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("YYMMdd").format(now);
		String tag = MarcTags.kFiexedLengthDataElements;
		degree.setDegreeYear("");
		metaData.setAuthors(authors);
		DissLanguage language = new DissLanguage("English", "EN");
		metaData.setDissLanguages(Lists.newArrayList(language));
		String expectedData = curTime + "n" + fixedElementString + "eng" + " d";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void withNoDegreeYearAndInvalidLanguageInfo() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("YYMMdd").format(now);
		String tag = MarcTags.kFiexedLengthDataElements;
		degree.setDegreeYear(null);
		metaData.setAuthors(authors);
		DissLanguage language = new DissLanguage("EnglishTest", "ENTEST");
		metaData.setDissLanguages(Lists.newArrayList(language));
		String expectedData = curTime + "n" + fixedElementString + "|||" + " d";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 2);
	}

}
