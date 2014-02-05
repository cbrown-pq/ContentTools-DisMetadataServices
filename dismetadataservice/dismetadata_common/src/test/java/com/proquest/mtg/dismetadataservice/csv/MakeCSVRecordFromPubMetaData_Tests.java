package com.proquest.mtg.dismetadataservice.csv;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfStatus;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.TextNormalizer;

import static org.hamcrest.CoreMatchers.is;

public class MakeCSVRecordFromPubMetaData_Tests {
	IJdbcConnectionPool connectionPool;
	String header = "";

	CSVRecordFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();

		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
	}

	@Test(expected = Exception.class)
	public void with_Null_Throws() throws Exception {
		factory.makeFrom(null);
	}

	@Test
	public void with_Emtpy() throws Exception {
		DisPubMetaData empty = new DisPubMetaData();
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(empty);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyPubNumber() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPubNumber("3569004");
		String expectedCSVData = header
				+ "\r\n\"3569004\",,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyVolumeIssue() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setVolumeIssue("74-08(E)");
		metadata.setBatch(batch);
		String expectedCSVData = header
				+ "\r\n,\"74-08(E)\",,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
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
		String expectedCSVData = header
				+ "\r\n,,\"Moriarty, Matthew D.|Kinstlinger, Gary\",,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";

		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyISBN() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setISBN("978-1-303-03106-9");
		String expectedCSVData = header
				+ "\r\n,,,\"978-1-303-03106-9\",,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyPubPageNumber() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPubPageNum("152");
		String expectedCSVData = header
				+ "\r\n,,,,\"152\",,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyPubPageCount() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPageCount("128");
		String expectedCSVData = header
				+ "\r\n,,,,,\"128\",,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyPublisher() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPublisher("Kuopion Yliopiston Painatuskeskus, Kuopio, Finland");
		String expectedCSVData = header
				+ "\r\n,,,,,,\"Kuopion Yliopiston Painatuskeskus, Kuopio, Finland\",,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyReferenceLocation() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setReferenceLocation("DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN");
		String expectedCSVData = header
				+ "\r\n,,,,,,,\"DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN\",,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyBritishLocation() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setBLNumber("546782");
		String expectedCSVData = header
				+ "\r\n,,,,,,,,\"546782\",,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyExternalUrl() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setExternalURL("http://www.theses.com/idx/registered_users/etd/8f.asp");
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,\"http://www.theses.com/idx/registered_users/etd/8f.asp\",,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDissLanguages() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		List<DissLanguage> languages = new ArrayList<DissLanguage>();
		DissLanguage lang1 = new DissLanguage("English", "EN");
		DissLanguage lang2 = new DissLanguage("French", "FR");
		languages.add(lang1);
		languages.add(lang2);
		metadata.setDissLanguages(languages);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,\"English|French\",\"EN|FR\",,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
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
		String expectedCSVData = header + "\r\n,,,,,,,,,,,,\"0753\",\"Massachusetts Institute of Technology\",\"UNITED STATES\",\"Massachusetts\",,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";	
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
				+ "\r\n"
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
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,"
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
		degree.setDegreeDescription("Doctor of Nursing Science");
		degree.setDegreeYear("1987");
		degrees.add(degree);
		author.setDegrees(degrees);
		authors.add(author);
		metadata.setAuthors(authors);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,"
				+ "\"D.N.Sc.\"" + ",\"Doctor of Nursing Science\""
				+ ",\"1987\"" + ",,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyAbstract() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		String abstractText = "<abstractText>Aim</italic>. The aim was to evaluate the results of endovascular aneurysm repair (EVAR) of abdominal aortic aneurysms (AAA) in terms of intra-AAA pressure and clinical outcomes. ^   <italic>Methods and results</italic>. 0.014-inch guide-wire-mounted tip-pressure sensors were used for direct intra-aneurysm sac pressure measurement (DISP) through translumbar puncture of the AAA. Mean Pressure Index was calculated (MPI) as the percentage of mean intra-aneurysm pressure relative to the simultaneous mean intra-aortic pressure. The pressure sensor was evaluated in vitro, in an aneurysm model, showing good agreement with model output (2 mm Hg). Measurements within aneurysm thrombus agreed well with the simultaneous ones from the model's lumen (1 mm Hg). Intra-observer variability of DISP in 15 patients undergoing double measurement, by separate punctures of the AAA, showed MPI median variability of 0% (r = .962, p &lt; .0001). Median MPI in patients with shrinking (n = 11), unchanged (n = 10) and expanding (n = 9) aneurysms without endoleaks at least 1 year after EVAR was 19%, 30% and 59%, respectively. Pulse pressure was also higher in expanding compared to the shrinking AAAs (10 vs. 2 mm Hg, p &lt; .0001). Seven of the 10 patients with unchanged AAAs underwent further imaging follow-up after DISP; 2 expanded (MPIs 47%&ndash;63%), 4 shrank (MPI 21%&ndash;30%) and I continued unchanged (MPI 14%). Type II endoleaks (6 patients, 7 DISP) seemed to be a varied entity with different degrees of AAA pressurization, even in the same patient at different occasions (range MPI 22%&ndash;92%). Successful endoleak embolization resulted in pressure reduction. Between 1998 and 2001, 168 consecutive patients with non-ruptured AAAs were treated by EVAR (n = 117) and open repair (OR) due to anatomical restrictions for EVAR (group A, n = 40) or young age with long life-expectancy (n = 11). Thirty-day mortality in EVAR (n = 117), OR group A and B was 2.6%, 15% and 0%, respectively. EVAR patients had higher ASA classifications (p &lt; .0001). Late survival was not different between the groups and late reinterventions, mainly endovascular, were more frequent in EVAR. ^   <italic>Conclusions</italic>. DISP is a reliable and reproducible technique for measuring intra-AAA pressure. It may become an important tool for EVAR evaluation by detecting EVAR outcomes early. High pressure is associated with AAA expansion, while low pressure with shrinkage. EVAR provides good results for AAA treatment even with the inclusion of high risk patients. The wide application of EVAR may affect the results of OR, since this tends to be performed in older patients with severe comorbidity and challenging anatomy. OR continues to the first-option for low-risk young patients. ^";
		TextNormalizer abstractNormalizer = new TextNormalizer();
		abstractText = abstractNormalizer.applyTo(abstractText);
		metadata.setAbstract(abstractText);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,"
				+ "\"" + abstractText + "\""
				+ ",,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlySubjects() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		List<Subject> subjects = new ArrayList<Subject>();
		Subject subject = new Subject();
		String subjDesc = "Language, Literature and Linguistics";
		String subjCode = "0297";
		String subjGroupDesc = "Literature, Medieval";
		subject.setSubjectDesc(subjDesc);
		subject.setSubjectCode(subjCode);
		subject.setSubjectGroupDesc(subjGroupDesc);
		subjects.add(subject);
		metadata.setSubjects(subjects);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,"
				+ "\"" + subjDesc + "\"" + ",\"" + subjCode + "\"" + ",\""
				+ subjGroupDesc + "\"" + ",\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlySupplementalFiles() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		SuppFile suppFile = new SuppFile();
		String suppFileName = "Yodel2ArchitecturalAnalysis.pdf";
		String suppFileDesc = "Architecture Analysis";
		String suppFileCategory = "pdf";
		suppFile.setSuppFilename(suppFileName);
		suppFile.setSuppFileDesc(suppFileDesc);
		suppFile.setSuppFileCategory(suppFileCategory);
		List<SuppFile> suppFiles = new ArrayList<SuppFile>();
		suppFiles.add(suppFile);
		metadata.setSuppFiles(suppFiles);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"Y\"" + ",\"" + suppFileName + "\"" + ",\"" + suppFileDesc
				+ "\"" + ",\"" + suppFileCategory + "\""
				+ ",,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDepartments() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		List<String> deparments = Lists.newArrayList(
				"Applied Behavioral Science", "Communication Studies");
		metadata.setDepartments(deparments);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,," 
				+ ",\""	+ "Applied Behavioral Science|Communication Studies" + "\""
				+ ",,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyKeywords() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Keyword keyword1 = new Keyword();
		keyword1.setValue("By Author");
		keyword1.setSource("low temperature adsorption drying");
		Keyword keyword2 = new Keyword();
		keyword2.setValue("For Datrix");
		keyword2.setSource("	  免疫");
		List<Keyword> keywords = Lists.newArrayList(keyword1,keyword2);
		metadata.setKeywords(keywords );
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,," 
				+ ",\"" +  "By Author|For Datrix"  + "\""
				+ ",\"" +  "low temperature adsorption drying|	  免疫"  + "\""
				+ ",,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlySalesRestrictions() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		SalesRestriction salesRestriction1 = new SalesRestriction();
		salesRestriction1.setCode("1");
		salesRestriction1.setDescription("Not Available For Sale");
		SalesRestriction salesRestriction2 = new SalesRestriction();
		salesRestriction2.setCode("2");
		salesRestriction2.setDescription("Available");
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(salesRestriction1,salesRestriction2);
		metadata.setSalesRestrictions(salesRestrictions);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,," 
				+ ",\"" + "1|2" + "\""
				+ ",\"" + "Not Available For Sale|Available" + "\""
				+ ",,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyDissertationTypeCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,," 
				+ ",\"" + "DAC"   + "\""
				+ ",,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyDissertationCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDBTypeDesc("Dissertation Abstracts International");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,," 
				+ ",\"" + "Dissertation Abstracts International"   + "\""
				+ ",,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyDissertationDAICode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDAISectionCode("B");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,," 
				+ ",\"" + "B"   + "\""
				+ ",\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	
	@Test
	public void withOnlyPdf() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		PdfStatus pdfStatus = new PdfStatus();
		pdfStatus.setPdfAvailableDate("23-APR-2012");
		pdfStatus.setPdfAvailableStatus(true);
		metadata.setPdfStatus(pdfStatus);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,,," 
				+ ",\"Y\""
				+ ",\"23-APR-2012\""
				+ ",,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlyFormatRestrictionCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		PdfStatus pdfStatus = new PdfStatus();
		pdfStatus.setPdfAvailableDate("23-APR-2012");
		pdfStatus.setPdfAvailableStatus(true);
		List<FormatRestriction> formatRestrictions = new ArrayList<FormatRestriction>();
		FormatRestriction formatRestriction1 = new FormatRestriction();
		formatRestriction1.setCode("C");
		FormatRestriction formatRestriction2 = new FormatRestriction();
		formatRestriction2.setCode("CE");
		formatRestrictions.add(formatRestriction1);
		formatRestrictions.add(formatRestriction2);
		metadata.setFormatRestrictions(formatRestrictions );
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,,," 
				+ ",\"N\""
				+ ",,\"C|CE\",";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	
}
