package com.proquest.mtg.dismetadataservice.exodus;

import java.text.ParseException;
import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.AlternateTitle;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfStatus;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.SplitAuthorNames;

public class MakeExodusMetadataForTesting {
	
	public static final String pqOpenUrlBase = "http://gateway.proquest.com/openurl?url_ver=Z39.88-2004&rft_val_fmt=info:ofi/fmt:kev:mtx:dissertation&res_dat=xri:pqm&rft_dat=xri:pqdiss:";
	static final String noVolumeIssuePub1 = "0564394";
	static final String fakePub1 = "fakePub1";
		
	static final String pubId0 = "C660938"; // Multiple Subjects
	static final String pubId1 = "1529601"; // Null Location of Copy
	static final String pubId2 = "0123559"; // Null Abstract
	static final String pubId3 = "U183503"; // British
	static final String pubId4 = "H000684"; // Chinese
	static final String pubId5 = "NR93884"; // Electronic Title
	static final String pubId6 = "1535737"; // Supplemental Files
	static final String pubId7 = "1496919"; // Normal pub
	static final String pubId8 = "3587237"; // Multiple Author
	static final String pubId9 = "MR82571"; // Canadian Pub
	static final String pubId10 = "C806973"; // Multiple Languages
	static final String pubId11 = "3533463"; // Multiple Department
	static final String pubId12 = "1533698"; // Foreign Title
	static final String pubId13 = "NQ61050"; // Multiple Sales Restriction 
	
	public static Subject makeSubjectForTesting(String group, String code, String description, int sequenceNumber) {
		Subject result = new Subject();
		result.setSubjectGroupDesc(group);
		if (null != code) { 
			result.setSubjectCode(code);
		}
		result.setSubjectDesc(description);
		result.setSequenceNumber(sequenceNumber);
		return result;
	}
	
	public static Keyword makeKeywordFrom(String value, String source) {
		Keyword result = new Keyword();
		result.setValue(value);
		result.setSource(source);
		return result;
	}
	
	public static SalesRestriction makeSalesDescriptionFrom(String code, String startDate, 
			String endDate, String description) {
		SalesRestriction result = new SalesRestriction();
		result.setCode(code);
		result.setRestrictionStartDate(startDate);
		result.setRestrictionEndDate(endDate);
		result.setDescription(description);
		return result;
	}
	
	public static FormatRestriction makeFormatDescriptionFrom(String code) {
		FormatRestriction result = new FormatRestriction();
		result.setCode(code);
		return result;
	}
		
	public static SuppFile makeSuppFileFrom(String fileName, String category, String description) {
		SuppFile result = new SuppFile();
		result.setSuppFilename(fileName);
		result.setSuppFileCategory(category);
		result.setSuppFileDesc(description);
		return result;
	}
	
	public static CmteMember makeCmteMemberFrom(String firstName, String middleName, String lastName, String suffix) {
		CmteMember result = new CmteMember();
		result.setFirstName(firstName);
		result.setMiddleName(middleName);
		result.setLastName(lastName);
		result.setSuffix(suffix);
		return result;
	}
	
	public static Advisors makeAdvisorsFrom(String advisorExodusStr, Iterable<Advisor> advisor) {
		Advisors result = new Advisors();
		result.setAdvisorsExodusStr(advisorExodusStr);
		result.setAdvisor(advisor);
		return result;
	}
	public static Advisor makeAdvisorFrom(String fullName, String alternateName) {
		Advisor result = new Advisor();
		result.setAdvisorFullName(fullName);
		if (null != alternateName) {
			result.setAltAdvisorFullName(alternateName);
		}
		return result;
	}
	
	public static Author makeAuthorFrom(
			String fullName, 
			String altAuthorName,
			int sequenceNumber,
			Degree... degrees) {
		Author result = new Author();
		SplitAuthorNames splitNames = new SplitAuthorNames(fullName);
		result.setAuthorFullName(splitNames.getFull());
		result.setFirstName(splitNames.getFirst());
		result.setMiddleName(splitNames.getMiddle());
		result.setLastName(splitNames.getLast());
		result.setAltAuthorFullName(altAuthorName);
		result.setSequenceNumber(sequenceNumber);
		result.setDegrees(Lists.newArrayList(degrees));
		return result;
	}
	
	public static Degree makeDegreeFrom(String code, String description, String year, int sequenceNumber) {
		Degree result = new Degree();
		result.setDegreeCode(code);
		result.setDegreeDescription(description);
		result.setDegreeYear(year);
		result.setSequenceNumber(sequenceNumber);
		return result;
	}
	
	public static PdfStatus makePdfStatusFrom(String flag, String value) {
		PdfStatus result = new PdfStatus();
		if (flag == "Y")
			result.setPdfAvailableStatus(true);
		else
			result.setPdfAvailableStatus(false);

		result.setPdfAvailableDate(value);
		return result;
	}
	
	public static String makePqOpenUrlFor(String pubId) {
		return pqOpenUrlBase + pubId.trim();
	}
	
	// Pub C660938
	public static final DisPubMetaData makeExpectedMetaData0() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId0);
		x.setReferenceLocation(
			"TWENTE UNIVERSITY LIBRARY, CENTRAL LIBRARY, P.O. BOX 217, NL-7500 AE     ENSCHEDE, THE NETHERLANDS");
		x.setISBN("978-90-365-1123-0");
		x.setPubPageNum("997");
		x.setPageCount("268");
		x.setAdvisors(null);
		x.setPqOpenURL(makePqOpenUrlFor(pubId0));

		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("59-04C");
		batch.setDAISectionCode(null);
		x.setBatch(batch);
		
		School school = new School();
		school.setSchoolCode("0237");
		school.setSchoolName("Universiteit Twente (The Netherlands)");
		school.setSchoolCountry("NETHERLANDS");
		school.setSchoolState(null);
		x.setSchool(school);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Applied Sciences", "0796", "Operations Research", 1), 
				makeSubjectForTesting("Applied Sciences", "0546", "Engineering, Industrial", 2),
				makeSubjectForTesting("Social Sciences", "0454", "Business Administration, Management", 3));
		x.setSubjects(subjects);

		Title title = new Title();
		title.setElectronicTitle("Recovery strategies and reverse logistic network design");
		title.setMasterTitle("Recovery strategies and reverse logistic network design");
		x.setTitle(title);
		
		String abstract_ = "In this thesis, we study the set-up of a reverse logistic system for durable consumer products. Decision support models are developed for the determination of recovery strategies, i.e., optimising the degree of disassembly and the assignment of recovery options, as well as determining a reverse logistic network design, i.e., optimising locations and capacities for facilities and optimising good flows between those facilities. The models are developed on the basis of Operations Research techniques, implemented in software and applied in two business cases. ^";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
		x.setDepartments(null);
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("CONSUMER", "For Datrix"),
				makeKeywordFrom("DESIGN", "For Datrix"),
				makeKeywordFrom("LOGISTIC", "For Datrix"),
				makeKeywordFrom("NETWORK", "For Datrix"),
				makeKeywordFrom("PRODUCT", "For Datrix"),
				makeKeywordFrom("PRODUCTS", "For Datrix"),
				makeKeywordFrom("RECOVERY", "For Datrix"),
				makeKeywordFrom("REVERSE", "For Datrix"),
				makeKeywordFrom("STRATEGIES", "For Datrix"),
				makeKeywordFrom("consumer products", "By I and L"),
				makeKeywordFrom("product recovery", "By I and L")
				));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("9", "01-JAN-1998", null, "Do not Sell"));
		x.setSalesRestrictions(salesRestrictions);
		
		x.setFormatRestrictions(null);
		
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		x.setAlternateTitles(null);
		x.setAdvisors(null);
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Krikke, Hans Ronald", null, 1, 
						makeDegreeFrom("Dr.", "Doctorate/Docteur", "1998", 1)));
		x.setAuthors(authors);
		
		x.setPdfStatus(makePdfStatusFrom("N","19-MAY-2014"));
		
		return x;
	}
		
	// Pub 1529601
	public static final DisPubMetaData makeExpectedMetaData1() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId1);
		
		x.setISBN("978-1-267-73400-6");
		x.setPageCount("51");
		x.setPqOpenURL(makePqOpenUrlFor(pubId1));
		
		School school = new School();
		school.setSchoolCode("0962");
		school.setSchoolName("California State University, Los Angeles");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("California");
		x.setSchool(school);
		
		Batch batch = new Batch();
		batch.setDBTypeCode("MAI");
		batch.setDBTypeDesc("Masters Abstracts International");
		batch.setVolumeIssue("51-03M(E)");
		x.setBatch(batch);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Language, Literature and Linguistics", "0593", "Literature, English", 1));
		x.setSubjects(subjects);

		Title title = new Title();
		title.setMasterTitle("Reviving the Jago: A case for resssesing Arthur Morrison's \"A Child of the Jago\"");		
		x.setTitle(title);
		
		String abstract_ = "<?Pub Inc> Arthur Morrison's &ldquo;A Child of The Jago&rdquo; was initially received with dismay and even anger . Contemporary British critics complained bitterly of  its harsh portrayal of the life of the London poor.  Morrison's purpose was to describe the lives of the inhabitants of the worst slum in the nation. This thesis explores why  Morrison's novel was harshly criticized by focusing on its: tone; episodes of violence; and use of the argot of the poor. Morrison's audience were well-to-do Britons who felt that the author had exaggerated the violence and desperation of life in the slum. This thesis also suggests why  Morrison's A Child of The Jago should be included in the cannon of British literature.^";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
		String department = "English";
		x.setDepartments(Lists.newArrayList(department));
		
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("A", "For Datrix"),
				makeKeywordFrom("ARTHUR", "For Datrix"),
				makeKeywordFrom("CASE", "For Datrix"),
				makeKeywordFrom("CHILD", "For Datrix"),
				makeKeywordFrom("JAGO", "For Datrix"),
				makeKeywordFrom("MORRISON", "For Datrix"),
				makeKeywordFrom("RESSSESING", "For Datrix"),
				makeKeywordFrom("REVIVING", "For Datrix"),
				makeKeywordFrom("S", "For Datrix")
				));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("1", "15-OCT-2012", null, "Not Available for Sale"),
				makeSalesDescriptionFrom("5", "15-OCT-2012", null, "Do not sell third party vendor"));
		x.setSalesRestrictions(salesRestrictions);
		
		x.setFormatRestrictions(null);
		
		List<CmteMember> comittee = Lists.newArrayList(
				makeCmteMemberFrom("Michelle", null, "Hawley", null),
				makeCmteMemberFrom("Ruben", null, "Quintero", null));
		x.setCmteMembers(comittee);
		x.setSuppFiles(null);
		x.setAlternateTitles(null);
		
		Advisors advisors = makeAdvisorsFrom(
				"Adviser: Jim Garrett",
				Lists.newArrayList(
						makeAdvisorFrom("Jim Garrett", null)));
		x.setAdvisors(advisors);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Munoz-Hodgson, Krishana", null, 1, 
						makeDegreeFrom("M.A.", "Master of Arts", "2012", 1)));
		x.setAuthors(authors);
		
		x.setPdfStatus(makePdfStatusFrom("Y","14-DEC-2012"));
		
		return x;
	}
	
	// Pub 0123559
	public static final DisPubMetaData makeExpectedMetaData2() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId2);
		x.setPubPageNum("61");
		x.setPageCount("16");
		x.setAdvisors(null);
		x.setPqOpenURL(makePqOpenUrlFor(pubId2));
		
		Batch batch = new Batch();
		batch.setDBTypeCode("ADD");
		batch.setDBTypeDesc("American Doctoral Dissertations");
		batch.setVolumeIssue("L1923");
		x.setBatch(batch);
		
		School school = new School();
		school.setSchoolCode("0130");
		school.setSchoolName("University of Minnesota");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("Minnesota");
		x.setSchool(school);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Education", "0525", "Education, Educational Psychology", 1));
		x.setSubjects(subjects);
		
		Title title = new Title();
		title.setMasterTitle("NON-VERBAL GROUP INTELLIGENCE TESTS FOR PRIMARY PUPILS");
		x.setTitle(title);
		
		String  abstract_ = "";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
		x.setDepartments(null);
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("GROUP", "For Datrix"),
				makeKeywordFrom("INTELLIGENCE", "For Datrix"),
				makeKeywordFrom("NON", "For Datrix"),
				makeKeywordFrom("PRIMARY", "For Datrix"),
				makeKeywordFrom("PUPILS", "For Datrix"),
				makeKeywordFrom("TESTS", "For Datrix"),
				makeKeywordFrom("VERBAL", "For Datrix")
				));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("9", "01-JAN-1921", null, "Do not Sell"));
		x.setSalesRestrictions(salesRestrictions);
		
		x.setFormatRestrictions(null);
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		x.setAlternateTitles(null);
		
		x.setAdvisors(null);
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"JOHNSON, OSCAR JULIUS", null, 1, 
						makeDegreeFrom("Ph.D.", "Doctor of Philosophy", "1921", 1)));
		x.setAuthors(authors);

		x.setPdfStatus(makePdfStatusFrom("N","19-MAY-2014"));
		
		return x;
	}
	
	// Pub U183503
	public static final DisPubMetaData makeExpectedMetaData3() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId3);
		x.setPageCount("1");
		x.setExternalURL("http://eprints.soton.ac.uk/15468");
		x.setBLNumber("DXN074035");
		x.setAdvisors(null);
		x.setPqOpenURL(makePqOpenUrlFor(pubId3));
		
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("70-37C");
		batch.setDAISectionCode(null);
		x.setBatch(batch);
		
		School school = new School();
		school.setSchoolCode("5036");
		school.setSchoolName("University of Southampton (United Kingdom)");
		school.setSchoolCountry("ENGLAND");
		school.setSchoolState(null);
		x.setSchool(school);
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("9", "10-JUN-2009", null, "Do not Sell"));
		x.setSalesRestrictions(salesRestrictions);
		x.setFormatRestrictions(null);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Pure Sciences", "0752", "Physics, Optics", 1));
		x.setSubjects(subjects);
		
		Title title = new Title();
		title.setMasterTitle("Fundamental properties of Bragg gratings and their application to the design of advanced structures.");
		x.setTitle(title);
		
		String abstract_ = "<![CDATA[This thesis presents the analysis of the local properties of Bragg gratings and their application to the improvement of standard designs and advanced structures.  The time spent by light inside each grating section is derived in terms of complex-valued quantities, and clear meaning is given to both the real and imaginary parts.  Improved physical understanding of propagation and energy distributions inside periodic structures is obtained.  Local properties also explain in a more intuitive way well understood features of different gratings, which improves intuition of new complex designs.  The analysis of the effect of perturbations is immediate using this approach and has important practical applications.  Independent confirmation of the theory is obtained, and experimental measurement of the imaginary part of the local time delay is given.  Phase errors affect the grating writing techniques, and the related sensitivity is analysed in detail. The robustness of different designs is discussed with respect to such manufacturing errors.  Fine tuning of standard or advanced grating designs by means of suitable error distributions is also proposed, and optimised characteristics either in the reflectivity or in the dispersive response are obtained.  This method is integrated with inverse scattering designs to further boost their performances.  Improved complex designs are also proposed in case losses affect propagation in the grating.  Cladding mode losses are compensated using an iterative layer-peeling algorithm.  The design of the first wide-band dispersion-compensating grating realised with a standard single mode fibre is shown.  Background losses and UV-induced losses in gratings are also compensated using a modified layer-peeling method.  The physical limitations related to grating design in lossy media are explained using the derived understanding of local properties.  New advanced designs are also considered that fully exploit the theoretical potentialities and manufacturing capabilities of Bragg gratings.  The performance of code-division multiple access systems based on superstructured gratings is improved by combining encoding, bandwidth filtering, and dispersion compensation in the same high reflectivity grating.\n]]>";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");		
		x.setDissLanguages(Lists.newArrayList(language));
		
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		x.setAlternateTitles(null);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Ghiringhelli, F.", null, 1, 
						makeDegreeFrom("Ph.D.", "Doctor of Philosophy", "2003", 1)));
		x.setAuthors(authors);
		x.setAdvisors(null);
		x.setDepartments(null);
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("ADVANCED", "For Datrix"),
				makeKeywordFrom("APPLICATION", "For Datrix"),
				makeKeywordFrom("BRAGG", "For Datrix"),
				makeKeywordFrom("DESIGN", "For Datrix"),
				makeKeywordFrom("FUNDAMENTAL", "For Datrix"),
				makeKeywordFrom("GRATINGS", "For Datrix"),
				makeKeywordFrom("PROPERTIES", "For Datrix"),
				makeKeywordFrom("STRUCTURES", "For Datrix")
				));
		
		PdfStatus pdfStatus = new PdfStatus();
		pdfStatus.setPdfAvailableDate("19-MAY-2014");
		pdfStatus.setPdfAvailableStatus(false);
		x.setPdfStatus(makePdfStatusFrom("N","19-MAY-2014"));
		
		return x;
	}
	
	// Pub H000684
	public static final DisPubMetaData makeExpectedMetaData4() {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId4);
		x.setPqOpenURL(makePqOpenUrlFor(pubId4));
		
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("71-17C");
		batch.setDAISectionCode(null);
		x.setBatch(batch);
		
		School school = new School();
		school.setSchoolCode("0370");
		school.setSchoolName("Tsinghua University (People's Republic of China)");
		school.setSchoolCountry("PEOPLES REP. OF CHINA");
		school.setSchoolState(null);
		x.setSchool(school);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Applied Sciences", "0543", "Engineering, Civil", 1));
		x.setSubjects(subjects);
		
		Title title = new Title();
		title.setMasterTitle("Development of an air conditioner controller using fuzzy control theory");
		x.setTitle(title);
		
		AlternateTitle alternateTitle = new AlternateTitle();
		alternateTitle.setAltTitle("应用模糊控制原理的空调控制器的开发\n\t");
		alternateTitle.setLanguage("Chinese");
		x.setAlternateTitles(Lists.newArrayList(alternateTitle));
		
		//String abstract_ = "In this paper, a new control method for room air conditioners (RACs) is presented, and a new RAC controller for the conventional RACs without inverters is developed in this basis. First, the RAC market conditions both domestic and abroad, as well as the development of RAC and RAC controllers are shown. Then some latest result on human thermo comfort are introduced. On this basis, a new control method for RACs which has considered the human thermo comfort principles is presented. This method has employed the fuzzy control theory which is suitable for the complex system such as the room-RAC system. After a detailed analysis on conventional RACs, the new method is applied on this kind of RAC. Then, computer simulations on this control method and two other conventional control methods  which are for comparation are done. Results show that the new method performs better on temperature flucturation, on-off times of compressor and unsensitivity on system parameter changes. Last, an RAC controller which employes the new control method is successfully developed. Test showes it has good electrical performance. It has low cost and high reliability which is ready for batch production.";
		String abstract_ = "In this paper, a new control method for room air conditioners (RACs) is presented, and a new RAC controller for the conventional RACs without inverters is developed in this basis. First, the RAC market conditions both domestic and abroad, as well as the development of RAC and RAC controllers are shown. Then some latest result on human thermo comfort are introduced. On this basis, a new control method for RACs which has considered the human thermo comfort principles is presented. This method has employed the fuzzy control theory which is suitable for the complex system such as the room-RAC system. After a detailed analysis on conventional RACs, the new method is applied on this kind of RAC. Then, computer simulations on this control method and two other conventional control methods  which are for comparation are done. Results show that the new method performs better on temperature flucturation, on-off times of compressor and unsensitivity on system parameter changes. Last, an RAC controller which employes the new control method is successfully developed. Test showes it has good electrical performance. It has low cost and high reliability which is ready for batch production.";
		x.setAbstract(abstract_);

		DissLanguage language = new DissLanguage("Chinese", "CH");
		x.setDissLanguages(Lists.newArrayList(language));
		
		x.setDepartments(null);
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("房间空调器", "By Author"),
				makeKeywordFrom("模糊控制", "By Author"),
				makeKeywordFrom("空气调节", "By Author"),
				makeKeywordFrom("自由词", "By Author"),
				makeKeywordFrom("计算机仿真", "By Author")
				));
		
		x.setSalesRestrictions(null);
		x.setFormatRestrictions(null);

		Advisors advisors = makeAdvisorsFrom(
				"Adviser: Guan Ping  Feng",
				Lists.newArrayList(
						makeAdvisorFrom("Guan Ping  Feng", "冯冠平")));
		x.setAdvisors(advisors);
	
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Zhang, Jian", 
						"张建",  
						1, 
						makeDegreeFrom("M.Eng.", "Master of Engineering", "2001", 1)));
		x.setAuthors(authors);

		x.setPdfStatus(makePdfStatusFrom("N",null));
		
		return x;
	}
	
	// NR93884
	public static final DisPubMetaData makeExpectedMetaData5() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId5);
		x.setISBN("978-0-494-93884-3");
		x.setPageCount("186");
		x.setPqOpenURL(makePqOpenUrlFor(pubId5));

		School school = new School();
		school.setSchoolCode("0862");
		school.setSchoolName("Universite du Quebec a Chicoutimi (Canada)");
		school.setSchoolCountry("CANADA");
		school.setSchoolState(null);
		x.setSchool(school);
		
		Batch batch = new Batch();
		batch.setDBTypeCode("DAI");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("74-09(E)");
		batch.setDAISectionCode("B");
		x.setBatch(batch);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Applied Sciences", "0794", "Engineering, Materials Science", 1));
		x.setSubjects(subjects);
		
		Title title = new Title();
		title.setMasterTitle("Rheological behavior and microstructural evolution of semi-solid\nhypereutectic Al-Si-Mg-Cu alloys using rheoforming process");
		title.setElectronicTitle("Rheological behavior and microstructural evolution of semi-solid\nhypereutectic aluminum-silicon-magnesium-copper alloys using rheoforming process");
		x.setTitle(title);
		
		String abstract_ = "<?Pub Inc> The aim of the current study was to investigate the rheological behavior and microstructural evolution of hypereutectic Al-Si-Cu and Al-Si-Mg-Cu alloys using conventional and modified SEED process (Swirled Enthalpy Equilibration Device). ^   In the first part; the feasibility of semi-solid processing of hypereutectic Al-Si-Cu A390 alloys using a novel rheoforming process was investigated. A combination of the SEED process, isothermal holding using insulation and addition of solid alloy during swirling was introduced as a novel method to improve the processability of semi-solid A390 slurries. The effects of isothermal holding and the addition of solid alloy on the temperature gradient between the centre and the wall and on the formation of &alpha;-Al particles were examined. In addition, phosphorus and strontium were added to the molten metal to refine the primary and eutectic silicon structure to facilitate semi-solid processing. It was found that the combination of the SEED process with two additional processing steps can produce semisolid 390 alloys that can be rheoformed. ^   In the second part, the effects of Mg additions ranging from 6 to 15% on the solidification behaviour of hypereutectic Al-155i-xMg-4Cu alloys was investigated using thermodynamic calculations, thermal analysis and extensive microstructural examination. The Mg level strongly influenced the microstructural evolution of the primary Mg<sub>2</sub>Si phase as well as the solidification behaviour. Thermodynamic predictions using ThermoCalc software reported the occurrence of six reactions, comprising the formation of primary Mg<sub>2 </sub>Si, two pre-eutectic binary reactions, forming either Mg<sub>2</sub>Si + Si or Mg<sub>2</sub>Si + &alpha;-Al phases, the main ternary eutectic reaction forming Mg<sub>2</sub>Si + Si + &alpha;-Al, and two post-eutectic reactions resulting in the precipitation of the Q-Al<sub>5</sub>Mg<sub>8</sub>Cu<sub> 2</sub>Si<sub>6</sub> and &thetas;-Al<sub>2</sub>Cu phases, respectively. Microstructures of the four alloys studied confirmed the presence of these phases, in addition to that of the &pi;-Al<sub>8</sub>Mg<sub>3</sub>FeSi<sub> 6</sub> phase. The presence of the &pi;-phase was also confirmed by thermal analysis. ^   In the third part, the effects of P and Sr on the microstructure of hypereutectic Al-155i14Mg-4Cu alloy were studied. The microstructural examination and phase identification were carried out using optical microscopy and scanning electron microscopy (SEM). The effects of individual and combined additions of P and Sr on the eutectic arrest in Al-155i14Mg-4Cu alloy were examined using thermal analysis. The mean size of primary Mg<sub>2</sub>Si decreases from about 350 &mu;m to less than 60 &mu;m and the morphology changes from coarse dendritic type or equiaxed to polygonal type. In addition, the morphology of the eutectic Mg<sub>2</sub>Si phase changes from coarse Chinese script to fine fiber-like, while that of the eutectic Si phase changes from coarse acicular shape to a fine fibrous form. With Sr addition, the morphology of the &pi;-Fe phase evolved from Chinese script to a fine twin platelet form. Furthermore, the thermal analysis results reveal that the addition of Sr or Sr and P reduces the temperature of eutectic nucleation and growth. ^   Finally, the rheological behaviour and microstructure of semi-solid hypereutectic A390, P-refined A390, Al-15Si-10.5Mg-4Cu and Al-15Si-13.5Mg-4Cu alloys were investigated by using parallel plate viscometry. The flow deformation of these alloys in the semi-solid state was characterized at different deformation rates and at variable solid fractions. The calculated viscosity for variable shear rate was deduced using the analytical method developed by Laxmanan and Flemings. Microstructures of the four alloys, after partial solidification, were examined in order to characterize the flow behaviour during deformation.  (Abstract shortened by UMI.)^";
		x.setAbstract(abstract_);
				
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
		x.setDepartments(null);
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("ALLOYS", "For Datrix"),
				makeKeywordFrom("ALUMINUM", "For Datrix"),
				makeKeywordFrom("ALUMINUM ALLOYS", "For Datrix"),
				makeKeywordFrom("Aluminum alloys", "By I and L"),
				makeKeywordFrom("BEHAVIOR", "For Datrix"),
				makeKeywordFrom("COPPER", "For Datrix"),
				makeKeywordFrom("EVOLUTION", "For Datrix"),
				makeKeywordFrom("MAGNESIUM", "For Datrix"),
				makeKeywordFrom("MICROSTRUCTURAL", "For Datrix"),
				makeKeywordFrom("PROCESS", "For Datrix"),
				makeKeywordFrom("RHEOFORMING", "For Datrix"),
				makeKeywordFrom("RHEOLOGICAL", "For Datrix"),
				makeKeywordFrom("RHEOLOGY", "For Datrix"),
				makeKeywordFrom("Rheoforming", "By I and L"),
				makeKeywordFrom("Rheology", "By I and L"),
				makeKeywordFrom("SEMI", "For Datrix"),
				makeKeywordFrom("SEMISOLID METALS", "For Datrix"),
				makeKeywordFrom("SILICON", "For Datrix"),
				makeKeywordFrom("SOLID\nHYPEREUTECTIC", "For Datrix"),
				makeKeywordFrom("Semisolid metals", "By I and L"),
				makeKeywordFrom("USING", "For Datrix")
				));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("5", "08-JUL-2013", null, "Do not sell third party vendor"),
				makeSalesDescriptionFrom("8", "08-JUL-2013", null, "Do not sell third party Indexing"));
		x.setSalesRestrictions(salesRestrictions);
		x.setFormatRestrictions(null);
		
		Advisors advisors = makeAdvisorsFrom(
				"Adviser: X. Grant Chen",
				Lists.newArrayList(
						makeAdvisorFrom("X. Grant Chen", null)));
		x.setAdvisors(advisors);
		
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		x.setAlternateTitles(null);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Tebib, Mehand", null, 1, 
						makeDegreeFrom("Ph.D.", "Doctor of Philosophy", "2012", 1)));
		x.setAuthors(authors);
		
		x.setPdfStatus(makePdfStatusFrom("Y","20-JUL-2013"));
		
		return x;
	}
	
	// 1535737
	public static final DisPubMetaData makeExpectedMetaData6() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId6);
		x.setISBN("978-1-303-02410-8");
		x.setPageCount("9");
		x.setPqOpenURL(makePqOpenUrlFor(pubId6));

		School school = new School();
		school.setSchoolCode("0033");
		school.setSchoolName("University of California, San Diego");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("California");
		x.setSchool(school);
		
		Batch batch = new Batch();
		batch.setDBTypeCode("MAI");
		batch.setDBTypeDesc("Masters Abstracts International");
		batch.setVolumeIssue("51-05M(E)");
		batch.setDAISectionCode(null);
		x.setBatch(batch);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Communication and the Arts", "0465", "Theater", 1));
		x.setSubjects(subjects);
		
		Title title = new Title();
		title.setMasterTitle("Finding the Flow: Management Style in \"In the Red and Brown Water\"");
		x.setTitle(title);
		
		String abstract_ = "<?Pub Inc> The prevailing metaphors in <italic>In the Red and Brown Water</italic> are wind and water, and I likened my work on the production to a river. Water can be strong, and also gentle. It can adapt to terrain or transform landscape. During this collaboration I focused on flowing with the production process, as ideas emerged and evolved and decisions were fluid. It has been a huge breakthrough to trust my instincts in a new way to serve the production, and discover that I cannot default to my impulses to impose rigidity and structure.  A production, like a river, is going to keep flowing and a group of artists cannot be controlled. I guided and adapted to the process, but I could not change it, because it was bigger and stronger than me.  ^   During my graduate studies, I have learned that there is no \"right way,\" there is only the way I am working right now, and my approach can be different for each project. As someone who enjoys structure and routine, the open-ended possibilities are challenging to accept. But as an experiential learner, flexibility has been my journey, and through many theatre and dance productions I have developed my personal stage management style to be open to the needs of each project. With the culmination of my formal training, my style is to be more like water: finding the flow and guiding others through the production.^";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
		
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("BROWN", "For Datrix"),
				makeKeywordFrom("FINDING", "For Datrix"),
				makeKeywordFrom("FLOW", "For Datrix"),
				makeKeywordFrom("MANAGEMENT", "For Datrix"),
				makeKeywordFrom("RED", "For Datrix"),
				makeKeywordFrom("STAGE MANAGEMENT", "For Datrix"),
				makeKeywordFrom("STYLE", "For Datrix"),
				makeKeywordFrom("Stage management", "By Author"),
				makeKeywordFrom("WATER", "For Datrix")
				));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("5", "12-APR-2013", null, "Do not sell third party vendor"),
				makeSalesDescriptionFrom("8", "12-APR-2013", null, "Do not sell third party Indexing"));
		x.setSalesRestrictions(salesRestrictions);
		x.setFormatRestrictions(null);
		
		String departments = "Theatre and Dance";
		x.setDepartments(Lists.newArrayList(departments));
		
		List<SuppFile> supplementalFiles = Lists.newArrayList(
				makeSuppFileFrom("ZingleCallingPages11-13.pdf", "pdf", PubMetaDataQuery.kEmptyValue),
				makeSuppFileFrom("ZingleCallingPages3-5.pdf", "pdf", PubMetaDataQuery.kEmptyValue),
				makeSuppFileFrom("ZingleCallingPages94-96.pdf", "pdf", "In the Red and Brown Water Calling Pages 94-96"));
		x.setSuppFiles(supplementalFiles);
		x.setAlternateTitles(null);
		
		List<CmteMember> comittee = Lists.newArrayList(
				makeCmteMemberFrom("John", "B.", "Doller", null),
				makeCmteMemberFrom("Charles", "E.", "Means", null), 
				makeCmteMemberFrom("Gregory", "D.", "Wallace", null));
		x.setCmteMembers(comittee);
		
		Advisors advisors = makeAdvisorsFrom(
				"Adviser: Lisa J. Porter",
				Lists.newArrayList(
						makeAdvisorFrom("Lisa J. Porter", null)));
		x.setAdvisors(advisors);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Zingle, Laura Maria", null, 1, 
						makeDegreeFrom("M.F.A.", "Master of Fine Arts", "2013", 1)));
		x.setAuthors(authors);
		x.setPdfStatus(makePdfStatusFrom("Y","08-MAY-2013"));
		return x;
	}
	
	// 1496919
	public static final DisPubMetaData makeExpectedMetaData7() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId7);
		x.setISBN("978-1-124-78544-8");
		x.setPageCount("442");
		x.setPubPageNum("342");
		x.setPqOpenURL(makePqOpenUrlFor(pubId7));

		School school = new School();
		school.setSchoolCode("0792");
		school.setSchoolName("State University of New York at Binghamton");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("New York");
		x.setSchool(school);
			
		Batch batch = new Batch();
		batch.setDBTypeCode("MAI");
		batch.setDBTypeDesc("Masters Abstracts International");
		batch.setVolumeIssue("50-01M");
		batch.setDAISectionCode(null);
		x.setBatch(batch);
			
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Earth Sciences", "0388", "Hydrology", 1),
				makeSubjectForTesting("Earth Sciences", "0372", "Geology", 2),
				makeSubjectForTesting("Earth Sciences", "0407", "Environmental Geology", 3));
		x.setSubjects(subjects);
			
		Title title = new Title();
		title.setMasterTitle("Characterization of the hydrogeology of the bedrock aquifer in rural Vestal, New York");
		x.setTitle(title);
			
		String abstract_ = "<?Pub Inc> The majority of the Town of Vestal obtains their water supply from wells drilled into the Upper Devonian fractured-bedrock aquifer.  Concerns about quantity and quality of groundwater prompted a study of the aquifer to determine the likelihood of future water quantity and quality issues if the aquifer is developed further.  A written survey of residents was conducted, 251 water samples were collected, 136 depth-to-groundwater measurements were taken, 5 slug/bail-down tests were performed, depth to bedrock data were compiled, and 4 seismic-refraction surveys were conducted.  An average hydraulic conductivity of 9.7 x 10-2 m/day was found.  Water chemistry revealed limited interconnectivity between wells; the aquifer is physically and chemically heterogeneous.  Water availability varies with the seasons; summer well usage will likely cause a decrease in storage.  Moderate development of the aquifer should be supported if low well densities are maintained and summer water usage is monitored. ^";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
			
		//x.setDepartments(null);
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("AQUIFER", "For Datrix"),
				makeKeywordFrom("BEDROCK", "For Datrix"),
				makeKeywordFrom("CHARACTERIZATION", "For Datrix"),
				makeKeywordFrom("FRACTURED", "For Datrix"),
				makeKeywordFrom("Fractured", "By Author"),
				makeKeywordFrom("HYDRAULIC CONDUCTIVITY", "For Datrix"),
				makeKeywordFrom("HYDROGEOLOGY", "For Datrix"),
				makeKeywordFrom("Hydraulic conductivity", "By Author"),
				makeKeywordFrom("Hydrogeology", "By Author"),
				makeKeywordFrom("NEW", "For Datrix"),
				makeKeywordFrom("RECHARGE", "For Datrix"),
				makeKeywordFrom("RURAL", "For Datrix"),
				makeKeywordFrom("Recharge", "By Author"),
				makeKeywordFrom("SUSTAINABILITY", "For Datrix"),
				makeKeywordFrom("Sustainability", "By Author"),
				makeKeywordFrom("VESTAL", "For Datrix"),
				makeKeywordFrom("Vestal", "By Author"),
				makeKeywordFrom("YORK", "For Datrix")
				));
			
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("5", "25-JUL-2011", null, "Do not sell third party vendor"));
		x.setSalesRestrictions(salesRestrictions);
		x.setFormatRestrictions(null);
		
		String departments = "Geological Sciences and Environmental Studies";
		x.setDepartments(Lists.newArrayList(departments));
		
		List<SuppFile> supplementalFiles = Lists.newArrayList(
				makeSuppFileFrom("NT1_Appendix.xls", "spreadsheet", "NT1 Data Logger Data"),
				makeSuppFileFrom("NT2_Appendix.xls", "spreadsheet", "NT2 Data Logger Data"),
				makeSuppFileFrom("ST1_Appendix.xls", "spreadsheet", "ST1 Data Logger Data"),
				makeSuppFileFrom("ST2_Appendix.xls", "spreadsheet", "ST2 Data Logger Data"));
		x.setSuppFiles(supplementalFiles);
		x.setAlternateTitles(null);
			
		List<CmteMember> comittee = Lists.newArrayList(
				makeCmteMemberFrom("Jeffrey", "S.", "Barker", null),
				makeCmteMemberFrom("Robert", "V.", "Demicco", null), 
				makeCmteMemberFrom("Joseph", "R.", "Graney", null));
		x.setCmteMembers(comittee);
		
		Advisors advisors = makeAdvisorsFrom(
				"Adviser: Karen M. Salvage",
				Lists.newArrayList(
						makeAdvisorFrom("Karen M. Salvage", null)));
		x.setAdvisors(advisors);
			
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Vayo, Lynette Irene", null, 1, 
						makeDegreeFrom("M.S.", "Master of Science", "2011", 1)));
		x.setAuthors(authors);
		x.setPdfStatus(makePdfStatusFrom("Y","22-MAR-2012"));	
		return x;
	}
	
	// 3587237
	public static final DisPubMetaData makeExpectedMetaData8() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId8);
		x.setISBN("978-1-303-24310-3");
		x.setPageCount("93");
		x.setPubPageNum(null);
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		x.setPqOpenURL(makePqOpenUrlFor(pubId8));
		
		Title title = new Title();
		title.setMasterTitle("A problem-based learning project focused on the Missouri teacher quality standards");
		x.setTitle(title);
				
		Advisors advisors = makeAdvisorsFrom(
				"Adviser: William T. Rebore",
				Lists.newArrayList(
						makeAdvisorFrom("William T. Rebore", null)));
		x.setAdvisors(advisors);
					
		String abstract_ = "<?Pub Inc> This team project was a requirement for the Saint Louis University Executive Doctor of Education program. It was a problem-based learning project focused on the teacher quality standards adopted by the Missouri State Board of Education in June 2011. In the earliest stages prior to adoption of the standards, the researchers hired by the state indicated the problem with evaluating all nine standards and 36 indicators. As there was no system to determine what the most important standards were, the project sought to provide a system of weight to assist with the implementation of the standards. ^   As the background information indicated, the standards were research-based and provided an excellent framework for quality teaching. This project utilized perceptual data from Missouri principals and superintendents. These principals represented the highest and lowest achieving schools in the state based on the criteria for accreditation. A discrepancy analysis was completed to ascertain the importance of each standard. ^   At the conclusion of the project with all survey data analyzed, the solution was to weight the standards based on three things. First, the highest weight standards were based on the highest-achieving schools' responses. Second, the lowest standards were identified by both groups as the lowest. Finally, the five standards between were labeled as being within the purview of local discretion, but between the highest and lowest weighted standards. This system of weight was rooted in the survey responses and ensures local districts find value in the use of the standards as evaluative criteria.^";
		x.setAbstract(abstract_);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Education", "0443", "Education, Evaluation", 1),
				makeSubjectForTesting("Education", "0458", "Education, Policy", 2),
				makeSubjectForTesting("Education", "0449", "Education, Leadership", 3));
		x.setSubjects(subjects);
		
		List<Author> authors = Lists.newArrayList(
				makeAuthorFrom(
						"Cotter, Joshua C.", null, 1, 
						makeDegreeFrom("Ed.D.", "Doctor of Education", "2013", 1)),
				makeAuthorFrom(
						"Long, Jacob E.", null, 2, 
						makeDegreeFrom("Ed.D.", "Doctor of Education", "2013", 1)),		
				makeAuthorFrom(
						"Smith, Scott A.", null, 3, 
						makeDegreeFrom("Ed.D.", "Doctor of Education", "2013", 1)));
		x.setAuthors(authors);
		
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("A", "For Datrix"),
				makeKeywordFrom("BASED", "For Datrix"),
				makeKeywordFrom("FOCUSED", "For Datrix"),
				makeKeywordFrom("LEARNING", "For Datrix"),
				makeKeywordFrom("MISSOURI", "For Datrix"),
				makeKeywordFrom("Missouri", "By I and L"),
				makeKeywordFrom("PROBLEM", "For Datrix"),
				makeKeywordFrom("PROBLEM-BASED LEARNING", "For Datrix"),
				makeKeywordFrom("PROJECT", "For Datrix"),
				makeKeywordFrom("Problem-based learning", "By I and L"),
				makeKeywordFrom("QUALITY", "For Datrix"),
				makeKeywordFrom("STANDARDS", "For Datrix"),
				makeKeywordFrom("TEACHER", "For Datrix"),
				makeKeywordFrom("TEACHER QUALITY STANDARDS", "For Datrix"),
				makeKeywordFrom("Teacher quality standards", "By I and L")
				));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("5", "03-JUL-2013", null, "Do not sell third party vendor"),
				makeSalesDescriptionFrom("8", "03-JUL-2013", null, "Do not sell third party Indexing"));
		x.setSalesRestrictions(salesRestrictions);
		x.setFormatRestrictions(null);
		
		Batch batch = new Batch();
		batch.setDBTypeCode("DAI");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("74-11(E)");
		batch.setDAISectionCode("A");
		x.setBatch(batch);
		
		x.setAlternateTitles(null);
		
		School school = new School();
		school.setSchoolCode("0193");
		school.setSchoolName("Saint Louis University");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("Missouri");
		x.setSchool(school);
		
		List<CmteMember> comittee = Lists.newArrayList(
				makeCmteMemberFrom("Steve", null, "Dooer", null),
				makeCmteMemberFrom("Dennis", null, "Lea", null), 
				makeCmteMemberFrom("JoNell", null, "Wood", null),
				makeCmteMemberFrom("Gary", "K.", "Wright", null));			
		x.setCmteMembers(comittee);
		
		x.setSuppFiles(null);
		x.setDepartments(Lists.newArrayList("Educational Leadership"));
		x.setPdfStatus(makePdfStatusFrom("Y","29-AUG-2013"));
		
		return x;
	}
	
	// MR82571
	public static final DisPubMetaData makeExpectedMetaData9() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId9);
		x.setISBN("978-0-494-82571-6");
		x.setPageCount("104");
		x.setPubPageNum("2616");
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		x.setPqOpenURL(makePqOpenUrlFor(pubId9));
		
		Title title = new Title();
		title.setMasterTitle("&Eacute;tude des frottements internes dans le tellurure de\nbismuth pr&eacute;par&eacute; par extrusion");
		x.setTitle(title);
		
		x.setAdvisors(null);
					
		String abstract_ = "<?Pub Inc> In this work, we realized an experimental set up in the laboratory in order to study the internal friction of rods of bismuth telluride based alloys as a function of temperature, and to determine their Young&rsquo;s modulus by mechanical spectroscopy. Bismuth telluride alloys are semiconductor materials, which we obtain by mechanical alloying and hot extrusion.^   The set up provides the mechanical excitation of the sample and a registers the resultant acoustic signal response. Following the extraction of the vibration natural frequency, it is possible to determine Young&rsquo;s modulus and the internal friction (IF) of the studied rod.^   This impulse excitation technique (IET) is non-destructive and does not require cutting the analyzed samples and thus preserves their mechanical properties undamaged. This allows a better evaluation and control of the mechanical properties of the elaborated samples and ensures their quality before processing for thermoelectric module fabrication.^   The obtained results of the variation of internal frictions as a function of temperature showed a difference in behavior between the N and P type bismuth telluride based alloys. Data treatments permitted to draw behavior laws for both types of thermoelectric alloys. The treatment also permits the determination of an activation energy associated with the exponential law variation. Mechanisms of the elastic energy dissipation have been suggested. ^   The exponential variation of the internal friction is limited by a plateau in the region of low temperatures. This was interpreted as arising from the nanometric structure of our bulk material.^   The influence of the presence of cracks in defective rods on internal frictions measurements has been investigated. In a certain temperature range (100 - 200&deg;C), the IF values increase rapidly to reach very high values (> 10<super>-2</super>). At temperatures corresponding to higher internal frictions, we obtained an increase in the oscillation natural frequency and in Young&rsquo;s modulus.^   A long rod cut in three parts was studied to check its non-homogeneity. At low temperatures, the part corresponding to the end of extrusion presents higher IF. Young&rsquo;s modulus is smaller in the part corresponding to the beginning of the extrusion. This might be due to the fact that this latter part of the rod is more porous and less dense than the other two parts.^";
		x.setAbstract(abstract_);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Applied Sciences", "0794", "Engineering, Materials Science", 1),
				makeSubjectForTesting("Applied Sciences", "0743", "Engineering, Metallurgy", 2),
				makeSubjectForTesting("Pure Sciences", "0986", "Physics, Acoustics", 3));
		x.setSubjects(subjects);
		
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("BISMUTH", "For Datrix"),
				makeKeywordFrom("DANS", "For Datrix"),
				makeKeywordFrom("DE", "For Datrix"),
				makeKeywordFrom("DES", "For Datrix"),
				makeKeywordFrom("ETUDE", "For Datrix"),
				makeKeywordFrom("EXTRUSION", "For Datrix"),
				makeKeywordFrom("FROTTEMENTS", "For Datrix"),
				makeKeywordFrom("INTERNES", "For Datrix"),
				makeKeywordFrom("LE", "For Datrix"),
				makeKeywordFrom("PAR", "For Datrix"),
				makeKeywordFrom("PREPARE", "For Datrix"),
				makeKeywordFrom("TELLURURE", "For Datrix")
				));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("5", "21-NOV-2012", null, "Do not sell third party vendor"),
				makeSalesDescriptionFrom("8", "21-NOV-2012", null, "Do not sell third party Indexing"));
		x.setSalesRestrictions(salesRestrictions);
		x.setFormatRestrictions(null);
		
		Batch batch = new Batch();
		batch.setDBTypeCode("MAI");
		batch.setDBTypeDesc("Masters Abstracts International");
		batch.setVolumeIssue("50-04M");
		batch.setDAISectionCode(null);
		x.setBatch(batch);
		
		x.setAlternateTitles(null);
		
		School school = new School();
		school.setSchoolCode("1105");
		school.setSchoolName("Ecole Polytechnique, Montreal (Canada)");
		school.setSchoolCountry("CANADA");
		school.setSchoolState(null);
		x.setSchool(school);

		List<Author> authors = Lists.newArrayList(
				makeAuthorFrom(
						"Bourbia, Ouaheb", null, 1, 
						makeDegreeFrom("M.Sc.A.", "Master of Applied Sciences/Maitrise es sciences appliquees", "2011", 1)));
		x.setAuthors(authors);
					
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		x.setDepartments(null);
		x.setPdfStatus(makePdfStatusFrom("Y","22-MAR-2012"));
		
		return x;
	}
	
	// C806973
	public static final DisPubMetaData makeExpectedMetaData10() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId10);
		x.setISBN("978-951-45-9858-6");
		x.setPageCount("170");
		x.setPubPageNum("23");
		DissLanguage language = new DissLanguage("Estonian, Russian, and English", "EZ");
		x.setDissLanguages(Lists.newArrayList(language));
		x.setAdvisors(null);
		x.setPublisher("Aivar Kriiska, Side 10A2, Parnu 80017, Estonia");
		x.setPqOpenURL(makePqOpenUrlFor(pubId10));
		
		Title title = new Title();
		title.setMasterTitle("Stone Age settlement and economic processes in the Estonian coastal area and islands");
		x.setTitle(title);
		
					
		String abstract_ = "Since 1994 the author has carried out intensive investigations in Estonian coastal areas and on the islands. He has discovered nearly 50 Stone Age settlement sites, and carried out archaeological excavations on 12 sites. The dissertation comprises 12 articles printed in various publications, and a summary. The articles present the reviews of the results of archaeological fieldwork, and conclusions&mdash;on micro-, meso- and macrolevel&mdash;based on artefact and bone analysis, comparative study and radiocarbon dates. ^   At the present stage of investigations, we can date coastal settlement in Estonia to the beginning of the Litorina Sea, when a diverse hunting economy based on seal hunting, marine and river fishing and game hunting, was completely formed. Most likely this was the basis for the colonisation of the islands, which started presumably from the coastal area of West Estonia. Radiocarbon dates provide evidence for the earliest habitation of Saaremaa in about 5800 cal BC, on Hiiumaa in 5700 cal BC, and in Ruhnu in 5300 cal BC. At first, the islands were probably visited only seasonally. Nevertheless, it is likely that permanent settlement appeared on Saaremaa already in Late Mesolithic times. In the Middle and Late Neolithic periods, a diverse hunting economy developed on the islands, based on marine fishing and hunting of different species of seals (ringed seal, grey seal, harp seal) and porpoise. This may indicate the formation of all-year-round villages. ^   New dates enable us to elaborate more accurately the chronology of the Estonian Stone Age. The summary presents the new chronological system of the Estonian Stone Age: the Kunda Culture ca 9000&ndash;4900 cal BC, the Narva Culture ca 4900&ndash;4150 cal BC, the Typical Combed Ware Culture ca 4150&ndash;3650 cal BC, the Late Combed Ware Culture ca 3650&ndash;1500(?) cal BC, and the Corded Ware Culture ca 3200&ndash;1500(?) cal BC. ^";
		x.setAbstract(abstract_);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Social Sciences", "0324", "Anthropology, Archaeology", 1));
		x.setSubjects(subjects);
		
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("AGE", "For Datrix"),
				makeKeywordFrom("AREA", "For Datrix"),
				makeKeywordFrom("COASTAL", "For Datrix"),
				makeKeywordFrom("Coastal", "From Title"),
				makeKeywordFrom("ECONOMIC", "For Datrix"),
				makeKeywordFrom("ECONOMIC PROCESSES", "For Datrix"),
				makeKeywordFrom("ESTONIAN", "For Datrix"),
				makeKeywordFrom("Economic processes", "From Title"),
				makeKeywordFrom("Estonian", "From Title"),
				makeKeywordFrom("ISLANDS", "For Datrix"),
				makeKeywordFrom("Islands", "From Title"),
				makeKeywordFrom("PROCESSES", "For Datrix"),
				makeKeywordFrom("SETTLEMENT", "For Datrix"),
				makeKeywordFrom("STONE", "For Datrix"),
				makeKeywordFrom("STONE AGE", "For Datrix"),
				makeKeywordFrom("Settlement", "From Title"),
				makeKeywordFrom("Stone Age", "From Title")));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("9", "23-JAN-2002", null, "Do not Sell"));
		x.setSalesRestrictions(salesRestrictions);
		x.setFormatRestrictions(Lists.newArrayList(
				makeFormatDescriptionFrom("M")));
		
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("63-01C");
		batch.setDAISectionCode(null);
		x.setBatch(batch);
		
		x.setAlternateTitles(null);
		
		School school = new School();
		school.setSchoolCode("0592");
		school.setSchoolName("Helsingin Yliopisto (Finland)");
		school.setSchoolCountry("FINLAND");
		school.setSchoolState(null);
		x.setSchool(school);

		List<Author> authors = Lists.newArrayList(
				makeAuthorFrom(
						"Kriiska, Aivar", null, 1, 
						makeDegreeFrom("FT", "Doctor of Philosophy (Finnish)", "2001", 1)));
		x.setAuthors(authors);
					
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		x.setDepartments(null);
		x.setPdfStatus(makePdfStatusFrom("N","19-MAY-2014"));
		
		return x;
	}
	
	// 3533463
	public static final DisPubMetaData makeExpectedMetaData11() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId11);
		x.setISBN("978-1-267-77436-1");
		x.setPageCount("151");
		x.setPubPageNum(null);
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		x.setPqOpenURL(makePqOpenUrlFor(pubId11));
		
		Title title = new Title();
		title.setMasterTitle("Spectral Approaches to Learning Predictive Representations");
		x.setTitle(title);
		
		Advisors advisors = makeAdvisorsFrom(
				"Adviser: Geoffrey J. Gordon",
				Lists.newArrayList(
						makeAdvisorFrom("Geoffrey J. Gordon", null)));
		x.setAdvisors(advisors);
		
		String abstract_ = "A central problem in artificial intelligence is to choose actions to maximize reward in a partially observable, uncertain environment. To do so, we must obtain an accurate environment model, and then plan to maximize reward. However, for complex domains, specifying a model by hand can be a time consuming process. This motivates an alternative approach: learning a model directly from observations. Unfortunately, learning algorithms often recover a model that is too inaccurate to support planning or too large and complex for planning to succeed; or, they require excessive prior domain knowledge or fail to provide guarantees such as statistical consistency. To address this gap, we propose spectral subspace identification algorithms which provably learn compact, accurate, predictive models of partially observable dynamical systems directly from sequences of action-observation pairs. Our research agenda includes several variations of this general approach: spectral methods for classical models like Kalman filters and hidden Markov models, batch algorithms and online algorithms, and kernel-based algorithms for learning models in high- and infinite-dimensional feature spaces. All of these approaches share a common framework: the model's belief space is represented as predictions of observable quantities and spectral algorithms are applied to learn the model parameters. Unlike the popular EM algorithm, spectral learning algorithms are statistically consistent, computationally efficient, and easy to implement using established matrix-algebra techniques. We evaluate our learning algorithms on a series of prediction and planning tasks involving simulated data and real robotic systems. ^";
		x.setAbstract(abstract_);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Applied Sciences", "0984", "Computer Science", 1));
		x.setSubjects(subjects);
		
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("APPROACHES", "For Datrix"),
				makeKeywordFrom("LEARNING", "For Datrix"),
				makeKeywordFrom("MACHINE LEARNING", "For Datrix"),
				makeKeywordFrom("Machine learning", "By I and L"),
				makeKeywordFrom("PREDICTIVE", "For Datrix"),
				makeKeywordFrom("PREDICTIVE STATE REPRESENTATIONS", "For Datrix"),
				makeKeywordFrom("Predictive state representations", "By I and L"),
				makeKeywordFrom("REINFORCEMENT LEARNING", "For Datrix"),
				makeKeywordFrom("REPRESENTATIONS", "For Datrix"),
				makeKeywordFrom("Reinforcement learning", "By I and L"),
				makeKeywordFrom("SPECTRAL", "For Datrix"),
				makeKeywordFrom("SPECTRAL LEARNING", "For Datrix"),
				makeKeywordFrom("SYSTEM IDENTIFICATION", "For Datrix"),
				makeKeywordFrom("Spectral learning", "By I and L"),
				makeKeywordFrom("System identification", "By I and L")));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("5", "06-NOV-2012", null, "Do not sell third party vendor"),
				makeSalesDescriptionFrom("8", "06-NOV-2012", null, "Do not sell third party Indexing"));
		x.setSalesRestrictions(salesRestrictions);
		x.setFormatRestrictions(null);
		
		Batch batch = new Batch();
		batch.setDBTypeCode("DAI");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("74-03(E)");
		batch.setDAISectionCode("B");
		x.setBatch(batch);
		
		x.setAlternateTitles(null);
		
		School school = new School();
		school.setSchoolCode("0041");
		school.setSchoolName("Carnegie Mellon University");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("Pennsylvania");
		x.setSchool(school);

		List<Author> authors = Lists.newArrayList(
				makeAuthorFrom(
						"Boots, Byron", null, 1, 
						makeDegreeFrom("Ph.D.", "Doctor of Philosophy", "2012", 1)));
		x.setAuthors(authors);

		List<CmteMember> comittee = Lists.newArrayList(
				makeCmteMemberFrom("J.", "Andrew", "Bagnell", null),
				makeCmteMemberFrom("Dieter", null, "Fox", null), 
				makeCmteMemberFrom("Arthur", null, "Gretton", null));			
		x.setCmteMembers(comittee);
		
		x.setSuppFiles(null);
		
		x.setDepartments(Lists.newArrayList("Computer Science", "Machine Learning"));
		x.setPdfStatus(makePdfStatusFrom("Y","07-DEC-2012"));
		
		return x;
	}	
	

	// 1533698
	public static final DisPubMetaData makeExpectedMetaData12() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId12);
		x.setISBN("978-1-267-91928-1");
		x.setPageCount("155");
		x.setPubPageNum(null);
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		x.setPqOpenURL(makePqOpenUrlFor(pubId12));
		
		Title title = new Title();
		title.setMasterTitle("Development and Characterization of Carbon Nanotubes (CNTs) and Silicon Carbide (SiC) Reinforced Al-based Nanocomposites");
		title.setForeignTitle("Development and Characterization of Carbon Nanotubes (CNTs) and Silicon Carbide (SiC) Reinforced Aluminum-based Nanocomposites");
		x.setTitle(title);
			
		Advisors advisors = makeAdvisorsFrom(
				"Adviser: Nasser Al-Aqeeli",
				Lists.newArrayList(
						makeAdvisorFrom("Nasser Al-Aqeeli", null)));
		x.setAdvisors(advisors);
		
		String abstract_ = "<?Pub Inc> Composites are engineered materials developed from constituent materials; matrix and reinforcements, to attain synergistic behavior at the micro and macroscopic level which are different from the individual materials. The high specific strength, low weight, excellent chemical resistance and fatigue endurance makes these composites superior than other materials despite anisotropic behaviors. Metal matrix composites (MMCs) have excellent physical and mechanical properties and alumium (Al) alloy composites have gained considerable interest and are used in multiple industries including: aerospace, structural and automotive. The aim of this research work is to develop an advanced Al-based nanocomposites reinforced with Carbon nanotubes (CNTs) and silicon carbide particulates (SiCp) nanophases using mechanical alloying and advanced consolidation procedure (Non-conventional) i.e. Spark Plasma Sintering (SPS) using two types of aluminum alloys (Al-7Si-0.3mg and Al-12Si-0.3Mg). Different concentrations of SiCp and CNTs were added and ball milled for different milling periods under controlled atmosphere to study the effect of milling time and the distribution of the second phases. Characterization techniques were used to investigate the morphology of the as received monolithic and milled powder using Field Emission Scanning Electron Microscope (FESEM), Energy Dispersive Spectroscopy (EDS), X-Ray Mapping, X-Ray Diffraction (XRD) and Particle Size Analyses (PSA). The results revealed that the addition of high concentrations of SiCp and CNTs in both alloys aided in refining the structure of the resulting powder further as the reinforcement particles acted like a grinding agent. Good distribution of reinforcing particles was observed from SEM and no compositional fluctuations were observed from the EDS. Some degree of agglomerations was observed despite the ethyl alcohol sonication effect of the CNTs before ball milling. From the XRD; continuous reduction in crystallite size and increase in internal strains were observed as milling progressed with increase in wt.% reinforcement due to the severe plastic deformation. Al/SiC and Al/CNTs were successfully consolidated by the SPS at sintering temperatures of 400, 450 and 500&deg;C with SiC at 5, 12 and 20wt% and 0.5wt%CNT milled for 20hrs and 3 hrs respectively. It was obtained that sintering temperature of 500&deg;C was the most suitable as the densification achieved for SiC reinforced sample was above 98% and 100% for unreinforced sample. The hardness increased with increasing SiC content from 0, 5 to 12 wt% i.e 68, 82, 85 respectively. At 20%wt of SiC a slight decrease in the hardness was observed i.e. 70 which might be attributed to high wt.% SiC, a similar trend was observed for the other alloy studied. For CNT reinforced samples, the hardness and densification increased significantly and 100% densification was obtained at 500&ordm;C, a hardness value from 68 to 82 was achieved from 0 to 0.5wt%CNT with a similar trend to the other alloy of interest. Conclusively, sintering of both alloys at 500&ordm;C and above is the most suitable, the use of SiCp and CNTs as reinforcements improved the hardness, 12wt% SiC showed better hardness values than 20wt% SiC at all three temperatures and the Al alloy containing higher Si in its alloying elements showed better hardness values using the same reinforcement and sintering parameters. ^";
		x.setAbstract(abstract_);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Applied Sciences", "0548", "Engineering, Mechanical", 1),
				makeSubjectForTesting("Applied Sciences", "0652", "Nanotechnology", 2));
		x.setSubjects(subjects);
		
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("AL", "For Datrix"),
				makeKeywordFrom("BASED", "For Datrix"),
				makeKeywordFrom("CARBIDE", "For Datrix"),
				makeKeywordFrom("CARBON", "For Datrix"),
				makeKeywordFrom("CHARACTERIZATION", "For Datrix"),
				makeKeywordFrom("CNTS", "For Datrix"),
				makeKeywordFrom("DEVELOPMENT", "For Datrix"),
				makeKeywordFrom("NANOCOMPOSITES", "For Datrix"),
				makeKeywordFrom("NANOTUBES", "For Datrix"),
				makeKeywordFrom("REINFORCED", "For Datrix"),
				makeKeywordFrom("SIC", "For Datrix"),
				makeKeywordFrom("SILICON", "For Datrix")));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("5", "04-FEB-2013", null, "Do not sell third party vendor"));
		x.setSalesRestrictions(salesRestrictions);
		x.setFormatRestrictions(null);
		
		Batch batch = new Batch();
		batch.setDBTypeCode("MAI");
		batch.setDBTypeDesc("Masters Abstracts International");
		batch.setVolumeIssue("51-05M(E)");
		batch.setDAISectionCode(null);
		x.setBatch(batch);
		
		x.setAlternateTitles(null);
		
		School school = new School();
		school.setSchoolCode("1088");
		school.setSchoolName("King Fahd University of Petroleum and Minerals (Saudi Arabia)");
		school.setSchoolCountry("SAUDI ARABIA");
		school.setSchoolState(null);
		x.setSchool(school);

		List<Author> authors = Lists.newArrayList(
				makeAuthorFrom(
						"Gujba, Kachalla Abdullahi", null, 1, 
						makeDegreeFrom("M.S.", "Master of Science", "2012", 1)));
		x.setAuthors(authors);
					
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		x.setDepartments(Lists.newArrayList("Mechanical Engineering Department"));
		x.setPdfStatus(makePdfStatusFrom("Y","30-APR-2013"));
		
		return x;
	}
	
	// Pub NQ61050
	public static final DisPubMetaData makeExpectedMetaData13() throws ParseException {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId13);
		
		x.setISBN("978-0-612-61050-7");
		x.setPageCount("296");
		x.setPubPageNum("2702");
		
		School school = new School();
		school.setSchoolCode("2500");
		school.setSchoolName("The University of British Columbia (Canada)");
		school.setSchoolCountry("CANADA");
		school.setSchoolState(null);
		x.setSchool(school);
		x.setPqOpenURL(makePqOpenUrlFor(pubId13));
		
		Batch batch = new Batch();
		batch.setDBTypeCode("DAI");
		batch.setDBTypeDesc("Dissertation Abstracts International");
		batch.setVolumeIssue("62-08");
		batch.setDAISectionCode("A");
		x.setBatch(batch);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Education", "0279", "Education, Language and Literature", 1),
				makeSubjectForTesting("Education", "0710", "Education, Technology of", 2),
				makeSubjectForTesting("Education", "0282", "Education, Bilingual and Multicultural", 3),
				makeSubjectForTesting("Education", "0530", "Education, Teacher Training", 4));
		x.setSubjects(subjects);

		Title title = new Title();
		title.setMasterTitle("Subjects -in -Interaction version 3.0:  An intellectual system for modern language student teachers to appropriate multiliteracies as designers and interpreters of media texts");		
		x.setTitle(title);
		
		String abstract_ = "This dissertation, which draws on the fields of critical theory, sociolinguistic theory, teacher education, and human-computer interaction, examines issues of culture and intercultural understanding, critical multiliteracies, learning in general and, specifically, the role of new media in the creation and interpretation of (learning) cultures. ^   Critical modern language education theorists advocate engaging in ethnographic studies of one's own and the target language culture as a way to shed traditional, static, product-based notions of culture for postmodern, dynamic, process-based interpretations of culture(s). To this end, how can teacher educators prepare student teachers to be reflexive about their own classroom practice? ^   In this approach, sixty secondary-level student teachers made short digital movies on their cultural interpretations of an object of their choice, such as cars. They filmed each other and were filmed as they worked and reflected on their movies and then used an on-line video analysis tool to share, annotate and critique the digital representations of their processes and products in relation to the course content. The participants assumed a variety of research roles, such as research initiators, qualitative researchers, video ethnographers, reflective practitioners and beta-testers of previously unreleased software. ^   Multimedia profiles of eight participants, presented on an accompanying CD-ROM, illustrate learning experiences that occurred throughout the group. They found it <italic>challenging</italic> to reconcile their prior schema and new concepts; <italic>confusing</italic> to develop a teaching approach while their basic assumptions were evolving; <italic>exciting</italic> to use state of the art tools and take on research roles; <italic>rewarding</italic> to participate in forums for productive reflection and discover new capacities; <italic> effective</italic> for making abstract ideas concrete; and <italic>empowering </italic> to appropriate the technical and intellectual skills to carry out similar projects. ^   This study points to a need for a pedagogical shift in preparing modern language student teachers which positions them to claim the classroom as their own. This includes claiming the right to: include culture in a language driven classroom; choose their own media materials; determine their own curriculum within standardized curricular and textbook guidelines; use non-traditional language teaching approaches; and hold high expectations for their students for critical thinking and use of the target language.* ^   *This dissertation includes a CD that is compound (contains both a paper copy and a CD as part of the dissertation). The CD requires the following applications: QuickTime, JPEG.^";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
		x.setDepartments(null);
		
		x.setKeywords(Lists.newArrayList(
				makeKeywordFrom("0", "For Datrix"),
				makeKeywordFrom("3", "For Datrix"),
				makeKeywordFrom("APPROPRIATE", "For Datrix"),
				makeKeywordFrom("DESIGNERS", "For Datrix"),
				makeKeywordFrom("INTELLECTUAL", "For Datrix"),
				makeKeywordFrom("INTELLECTUAL SYSTEM", "For Datrix"),
				makeKeywordFrom("INTERACTION", "For Datrix"),
				makeKeywordFrom("INTERPRETERS", "For Datrix"),
				makeKeywordFrom("Intellectual system", "From Title"),
				makeKeywordFrom("LANGUAGE", "For Datrix"),
				makeKeywordFrom("MEDIA", "For Datrix"),
				makeKeywordFrom("MEDIA TEXTS", "For Datrix"),
				makeKeywordFrom("MODERN", "For Datrix"),
				makeKeywordFrom("MODERN LANGUAGE", "For Datrix"),
				makeKeywordFrom("MULTILITERACIES", "For Datrix"),
				makeKeywordFrom("Media texts", "From Title"),
				makeKeywordFrom("Modern language", "From Title"),
				makeKeywordFrom("Multiliteracies", "From Title"),
				makeKeywordFrom("STUDENT", "For Datrix"),
				makeKeywordFrom("STUDENT TEACHERS", "For Datrix"),
				makeKeywordFrom("SUBJECTS", "For Datrix"),
				makeKeywordFrom("SUBJECTS-IN-INTERACTION", "For Datrix"),
				makeKeywordFrom("SYSTEM", "For Datrix"),
				makeKeywordFrom("Student teachers", "From Title"),
				makeKeywordFrom("Subjects-in-Interaction", "From Title"),
				makeKeywordFrom("TEACHERS", "For Datrix"),
				makeKeywordFrom("TEXTS", "For Datrix"),
				makeKeywordFrom("VERSION", "For Datrix")
				));
		
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				makeSalesDescriptionFrom("5", "21-NOV-2012", null, "Do not sell third party vendor"),
				makeSalesDescriptionFrom("8", "21-NOV-2012", null, "Do not sell third party Indexing"));
		x.setSalesRestrictions(salesRestrictions);
		
		x.setFormatRestrictions(Lists.newArrayList(
				makeFormatDescriptionFrom("CE"),
				makeFormatDescriptionFrom("E"),
				makeFormatDescriptionFrom("FM"),
				makeFormatDescriptionFrom("FME")));
		
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		x.setAlternateTitles(null);
		
		Advisors advisors = makeAdvisorsFrom(
				"Adviser:  Ricki Goldman-Segall",
				Lists.newArrayList(
						makeAdvisorFrom("Ricki Goldman-Segall", null)));
		x.setAdvisors(advisors);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Beers, Maggie", null, 1, 
						makeDegreeFrom("Ph.D.", "Doctor of Philosophy", "2001", 1)));
		x.setAuthors(authors);
		x.setPdfStatus(makePdfStatusFrom("Y","22-MAY-2011"));
		
		return x;
	}
}
