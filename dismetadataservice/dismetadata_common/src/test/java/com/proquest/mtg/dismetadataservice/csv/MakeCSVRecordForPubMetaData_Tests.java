package com.proquest.mtg.dismetadataservice.csv;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;

import static org.hamcrest.CoreMatchers.is;

public class MakeCSVRecordForPubMetaData_Tests {
	IJdbcConnectionPool connectionPool;
	String header = "PUB NUMBER,VOLUME ISSUE,ADVISORS,ISBN,PAGE NUMBER,PAGE COUNT,PUBLISHER,REFERENCE,BL NUMBER,EXTERNAL URL,DISS LANGUAGE DESCRIPTION,DISS LANGUAGE CODE,SCHOOL CODE,SCHOOL NAME,SCHOOL COUNTRY,SCHOOL STATE,TITLE,ENGLISH TITLE,VARIANT TITLE,AUTHORS,DEGREE CODE,DEGREE DESC,DEGREE YEAR,ABSTRACT,SUBJ DESC,SUBJ CODE,SUBJ GROUP DESC,HAS SUPP FILES,SUPPLEMENTAL FILE NAMES,SUPPLEMENTAL FILE DESCRIPTION,SUPPLEMENTAL FILE CATEGORY,DEPARTMENT,KEYWORD,KEYWORD SOURCE,SALES RESTRICTION CODE,SALES RESTRICTION DESC,SALES RESTRICTION STARTDT,SALES RESTRICTION ENDDT,DISS TYPE CODE,DISS CODE,DAI SECTION CODE,PDF AVAILABLE,PDF AVAILABLE DATE,FORMAT RESTRICTION CODE,";

	CSVRecordFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
	}

	@Test(expected = Exception.class)
	public void with_Null_Throws() throws Exception {
		factory.makeFrom(null);
	}

	@Test
	public void with_Emtpy() throws Exception {
		DisPubMetaData empty = new DisPubMetaData();
		String expectedCSVData = header + "\n,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(empty);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyPubNumber() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPubNumber("3569004");
		String expectedCSVData = header + "\n\"3569004\",,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyVolumeIssue() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setVolumeIssue("74-08(E)");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\n,\"74-08(E)\",,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyAdvisors() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Advisors advisors = new Advisors();
		List<Advisor> advisorList = new ArrayList<Advisor>();
		Advisor advisor1 = new Advisor();
		advisor1.setAdvisorFullName("Moriarty, Matthew D.");
		Advisor advisor2 = new Advisor();
		advisor2.setAdvisorFullName("Kinstlinger, Gary");
		advisorList.add(advisor1);
		advisorList.add(advisor2);
		advisors.setAdvisor(advisorList);
		metadata.setAdvisors(advisors);
		String expectedCSVData = header + "\n,,\"Moriarty, Matthew D.|Kinstlinger, Gary\",,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyISBN() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setISBN("978-1-303-03106-9");
		String expectedCSVData = header + "\n,,,\"978-1-303-03106-9\",,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyPubPageNumber() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPubPageNum("152");
		String expectedCSVData = header + "\n,,,,\"152\",,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyPubPageCount() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPageCount("128");
		String expectedCSVData = header + "\n,,,,,\"128\",,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyPublisher() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPublisher("Kuopion Yliopiston Painatuskeskus, Kuopio, Finland");
		String expectedCSVData = header + "\n,,,,,,\"Kuopion Yliopiston Painatuskeskus, Kuopio, Finland\",,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyReferenceLocation() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setReferenceLocation("DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN");
		String expectedCSVData = header + "\n,,,,,,,\"DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN\",,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyBritishLocation() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setBLNumber("546782");
		String expectedCSVData = header + "\n,,,,,,,,\"546782\",,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyExternalUrl() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setExternalURL("http://www.theses.com/idx/registered_users/etd/8f.asp");
		String expectedCSVData = header + "\n,,,,,,,,,\"http://www.theses.com/idx/registered_users/etd/8f.asp\",,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyDissLanguages() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		List<DissLanguage> languages = new ArrayList<DissLanguage>();
		DissLanguage lang1 = new DissLanguage("English","EN");
		DissLanguage lang2 = new DissLanguage("French","FR");
		languages.add(lang1);
		languages.add(lang2);
		metadata.setDissLanguages(languages);
		String expectedCSVData = header + "\n,,,,,,,,,,\"English|French\",\"EN|FR\",,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlySchool() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		School school = new School();
		school.setSchoolName("Massachusetts Institute of Technology");
		school.setSchoolCode("0753");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("Massachusetts");
		metadata.setSchool(school);
		String expectedCSVData = header + "\n,,,,,,,,,,,,\"Massachusetts Institute of Technology\",\"0753\",\"UNITED STATES\",\"Massachusetts\",,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyTitle() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Title title = new Title();
		title.setMasterTitle("Bridging the gap to peace:  From a new way of thinking into action");
		title.setElectronicTitle("Utilization of an articulation index procedure in the evaluation of hearing-aid efficiency");
		title.setEnglishOverwriteTitle("Adsorption kinetics at the air-water interface.  \\lbrack Dutch text\\rbrack");
		title.setForeignTitle("NAD(+)-glycohydrolase in runderschildklier:  Afzonderen, eigenschappen en bereiden van monoklonale antistoffen.  (Dutch text)");
		metadata.setTitle(title);
		String expectedCSVData = header 
				+ "\n"
				+ ",,,,,,,,,,,,,,,,"
				+ "\"NAD(+)-glycohydrolase in runderschildklier:  Afzonderen, eigenschappen en bereiden van monoklonale antistoffen.  (Dutch text).\""
				+ ","
				+ "\"Utilization of an articulation index procedure in the evaluation of hearing-aid efficiency.\""
				+ ","
				+ "\"Adsorption kinetics at the air-water interface.  \\lbrack Dutch text\\rbrack\""
				+ ",,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyAuthors() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Author author1 = new Author();
		author1.setAuthorFullName("Yamamoto, Masahiro");
		Author author2 = new Author();
		author2.setAuthorFullName("Fernandez-Rivera, Salvador");
		List<Author> authors = new ArrayList<Author>();
		authors.add(author1);
		authors.add(author2);
		metadata.setAuthors(authors);
		String expectedCSVData = header 
				+ "\n"
				+ ",,,,,,,,,,,,,,,,,,,"
				+ "\"Yamamoto, Masahiro|Fernandez-Rivera, Salvador\""
				+ ",,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyDegrees() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Author author = new Author();
		List<Author> authors = new ArrayList<Author>();
	    List<Degree> degrees = new ArrayList<Degree>();
	    Degree degree = new Degree();
	    degree.setDegreeCode("D.N.Sc.");
		degree.setDegreeDescription("Doctor of Nursing Science" );
		degree.setDegreeYear("1987");
		degrees.add(degree);
		author.setDegrees(degrees);
		authors.add(author);
		metadata.setAuthors(authors);
		String expectedCSVData = header
				+ "\n"
				+ ",,,,,,,,,,,,,,,,,,,,"
				+ "\"D.N.Sc.\""
				+ ",\"Doctor of Nursing Science\""
				+ ",\"1987\""
				+ ",,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
}
