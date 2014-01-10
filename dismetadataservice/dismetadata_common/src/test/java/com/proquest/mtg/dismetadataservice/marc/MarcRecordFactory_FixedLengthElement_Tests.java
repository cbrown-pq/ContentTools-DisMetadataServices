package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class MarcRecordFactory_FixedLengthElement_Tests  {
	
	static int kDataIndependentFieldCount = 3;
	
	DisGenMappingProvider disGenMappingProvider;
	Author author;
	List<Author> authors;
	Degree degree;
	List<Degree> degrees;
	
	DissLanguage language;
	List<DissLanguage> languages;
	
	MarcRecordFactory factory;
	
	@Before
	public void setUp() throws Exception {
		degree = createDegree();
		author = new Author();
		author.setDegrees(Lists.newArrayList(degree));
		authors = Lists.newArrayList(author);
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider(connectionPool);
		factory = new MarcRecordFactory(disGenMappingProvider);
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
		String tag = MarcTags.kFiexedLengthDataElements;
		DisPubMetaData metaData = new DisPubMetaData();
		degree.setDegreeYear("2012");
		metaData.setAuthors(authors);
		DissLanguage language = new DissLanguage("English", "EN");
		metaData.setDissLanguages(Lists.newArrayList(language));
		String expectedData = curTime + "s" + "2012" + "    ||||||||||||||||| ||" + "eng" + " d";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withNoDegreeYearAndLanguageInfo() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("YYMMdd").format(now);
		String tag = MarcTags.kFiexedLengthDataElements;
		DisPubMetaData metaData = new DisPubMetaData();
		degree.setDegreeYear(null);
		metaData.setAuthors(authors);
		DissLanguage language = new DissLanguage("English", "EN");
		metaData.setDissLanguages(Lists.newArrayList(language));
		String expectedData = curTime + "n" + "    ||||||||||||||||| ||" + "eng" + " d";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withNoDegreeYearAndInvalidLanguageInfo() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("YYMMdd").format(now);
		
		String tag = MarcTags.kFiexedLengthDataElements;
		DisPubMetaData metaData = new DisPubMetaData();
		degree.setDegreeYear(null);
		metaData.setAuthors(authors);
		DissLanguage language = new DissLanguage("EnglishTest", "ENTEST");
		metaData.setDissLanguages(Lists.newArrayList(language));
		String expectedData = curTime + "n" + "    ||||||||||||||||| ||" + "|||" + " d";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

}
