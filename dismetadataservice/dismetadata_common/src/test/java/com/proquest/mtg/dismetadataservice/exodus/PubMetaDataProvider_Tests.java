package com.proquest.mtg.dismetadataservice.exodus;

import static com.proquest.mtg.dismetadataservice.metadata.DissertationMatcher.dissertationEqualTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.SplitAuthorNames;


public class PubMetaDataProvider_Tests {
	static final String noVolumeIssuePub1 = "0564394";
	static final String fakePub1 = "fakePub1";
		
	static final String pubId0 = "C660938"; // Multiple Subjects
	static final String pubId1 = "1529601"; // Null Location of Copy
	static final String pubId2 = "0123559"; // Null Abstract
	static final String pubId3 = "U183503"; // British
	static final String pubId4 = "H000684"; // Chinese
	static final String pubId5 = "NR93884"; // Electronic Title
	static final String pubId6 = "1535737"; // Supplemental Files
	
	//static final List<String> kAllPubIds = Lists.newArrayList(   
	//		pubId0, pubId1, noVolumeIssuePub1, pubId2, pubId3, pubId4, pubId5, pubId6, fakePub1);
		
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
	
	/*public static Keyword makeKeywordFrom(String value, String source) {
		Keyword result = new Keyword();
		result.setValue(value);
		result.setSource(source);
		return result;
	}
	
	public static Keywords makeKeywordsFrom(List<Keyword> items) {
		Keywords result = new Keywords();
		result.getKeyword().addAll(items);
		return result;
	}*/
	
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
	/*
	public static Advisor makeAdvisorFrom(String fullName, String alternateName, String language) {
		Advisor result = new Advisor();
		result.setAdvisorFullName(fullName);
		if (null != alternateName) {
			AltAdvisorFullName altAdvisor = new AltAdvisorFullName();
			altAdvisor.setValue(alternateName);
			altAdvisor.setLanguage(language);
			result.setAltAdvisorFullName(altAdvisor);
		}
		return result;
	}*/
	
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
	
	// Pub C660938
	public static final DisPubMetaData makeExpectedMetaData0() {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId0);
		x.setReferenceLocation(
			"TWENTE UNIVERSITY LIBRARY, CENTRAL LIBRARY, P.O. BOX 217, NL-7500 AE     ENSCHEDE, THE NETHERLANDS");
		x.setISBN("978-90-365-1123-0");
		x.setPubPageNum("997");
		x.setPageCount("268");

//		Batch batch = new Batch();
//		batch.setDBTypeCode("DAI");
//		batch.setDBTypeDesc("Dissertation Abstracts International");
//		batch.setVolumeIssue("59-04");
//		batch.setDAISectionCode("C");
//		x.setBatch(batch);
		
//		School school = new School();
//		school.setSchoolCode("0237");
//		school.setSchoolName("Universiteit Twente (The Netherlands)");
//		school.setSchoolCountry("NETHERLANDS");
//		school.setSchoolState(null);
//		x.setSchool(school);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Applied Sciences", "0796", "Operations Research", 1), 
				makeSubjectForTesting("Applied Sciences", "0546", "Engineering, Industrial", 2),
				makeSubjectForTesting("Social Sciences", "0454", "Business Administration, Management", 3));
		x.setSubjects(subjects);

//		Title title = new Title();
//		title.setValue("Recovery strategies and reverse logistic network design");
//		title.setLanguage("English");
//		x.setTitle(title);
		
		String abstract_ = "In this thesis, we study the set-up of a reverse logistic system for durable consumer products. Decision support models are developed for the determination of recovery strategies, i.e., optimising the degree of disassembly and the assignment of recovery options, as well as determining a reverse logistic network design, i.e., optimising locations and capacities for facilities and optimising good flows between those facilities. The models are developed on the basis of Operations Research techniques, implemented in software and applied in two business cases. ^";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
//		x.setKeywords(makeKeywordsFrom(Lists.newArrayList(
//				makeKeywordFrom("CONSUMER", "For Datrix"),
//				makeKeywordFrom("DESIGN", "For Datrix"),
//				makeKeywordFrom("LOGISTIC", "For Datrix"),
//				makeKeywordFrom("NETWORK", "For Datrix"),
//				makeKeywordFrom("PRODUCT", "For Datrix"),
//				makeKeywordFrom("PRODUCTS", "For Datrix"),
//				makeKeywordFrom("RECOVERY", "For Datrix"),
//				makeKeywordFrom("REVERSE", "For Datrix"),
//				makeKeywordFrom("STRATEGIES", "For Datrix"),
//				makeKeywordFrom("consumer products", "By I and L"),
//				makeKeywordFrom("product recovery", "By I and L")
//				)));
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Krikke, Hans Ronald", null, 1, 
						makeDegreeFrom("Dr.", "Doctorate/Docteur", "1998", 1)));
		x.setAuthors(authors);
		
		return x;
	}
	
	// Pub 1529601
	public static final DisPubMetaData makeExpectedMetaData1() {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId1);
		
		x.setISBN("978-1-267-73400-6");
		x.setPageCount("51");
		
//		School school = new School();
//		school.setSchoolCode("0962");
//		school.setSchoolName("California State University, Los Angeles");
//		school.setSchoolCountry("UNITED STATES");
//		school.setSchoolState("California");
//		x.setSchool(school);
		
//		Batch batch = new Batch();
//		batch.setDBTypeCode("MAI");
//		batch.setDBTypeDesc("Masters Abstracts International");
//		batch.setVolumeIssue("51-03M(E)");
//		x.setBatch(batch);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Language, Literature and Linguistics", "0593", "Literature, English", 1));
		x.setSubjects(subjects);

//		Title title = new Title();
//		title.setValue("Reviving the Jago: A case for resssesing Arthur Morrison's \"A Child of the Jago\"");
//		title.setLanguage("English");
//		x.setTitle(title);
		
		String abstract_ = "<?Pub Inc> Arthur Morrison's &ldquo;A Child of The Jago&rdquo; was initially received with dismay and even anger . Contemporary British critics complained bitterly of  its harsh portrayal of the life of the London poor.  Morrison's purpose was to describe the lives of the inhabitants of the worst slum in the nation. This thesis explores why  Morrison's novel was harshly criticized by focusing on its: tone; episodes of violence; and use of the argot of the poor. Morrison's audience were well-to-do Britons who felt that the author had exaggerated the violence and desperation of life in the slum. This thesis also suggests why  Morrison's A Child of The Jago should be included in the cannon of British literature.^";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
//		Departments departments = new Departments();
//		departments.getDepartment().add("English");
//		x.setDepartments(departments);
		
//		x.setKeywords(makeKeywordsFrom(Lists.newArrayList(
//				makeKeywordFrom("A", "For Datrix"),
//				makeKeywordFrom("ARTHUR", "For Datrix"),
//				makeKeywordFrom("CASE", "For Datrix"),
//				makeKeywordFrom("CHILD", "For Datrix"),
//				makeKeywordFrom("JAGO", "For Datrix"),
//				makeKeywordFrom("MORRISON", "For Datrix"),
//				makeKeywordFrom("RESSSESING", "For Datrix"),
//				makeKeywordFrom("REVIVING", "For Datrix"),
//				makeKeywordFrom("S", "For Datrix")
//				)));
		
		
		List<CmteMember> comittee = Lists.newArrayList(
				makeCmteMemberFrom("Michelle", null, "Hawley", null),
				makeCmteMemberFrom("Ruben", null, "Quintero", null));
		x.setCmteMembers(comittee);
		x.setSuppFiles(null);
		
//		Advisors advisors = new Advisors();
//		advisors.getAdvisor().add(makeAdvisorFrom("Jim Garrett", null, null));
//		x.setAdvisors(advisors);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Munoz-Hodgson, Krishana", null, 1, 
						makeDegreeFrom("M.A.", "Master of Arts", "2012", 1)));
		x.setAuthors(authors);
		
		return x;
	}
	
	// Pub 0123559
	public static final DisPubMetaData makeExpectedMetaData2() {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId2);
		x.setPubPageNum("61");
		x.setPageCount("16");
		
//		Batch batch = new Batch();
//		batch.setDBTypeCode("ADD");
//		batch.setDBTypeDesc("American Doctoral Dissertations");
//		batch.setVolumeIssue("L1923");
//		x.setBatch(batch);
		
//		School school = new School();
//		school.setSchoolCode("0130");
//		school.setSchoolName("University of Minnesota");
//		school.setSchoolCountry("UNITED STATES");
//		school.setSchoolState("Minnesota");
//		x.setSchool(school);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Education", "0525", "Education, Educational Psychology", 1));
		x.setSubjects(subjects);
		
//		Title title = new Title();
//		title.setValue("NON-VERBAL GROUP INTELLIGENCE TESTS FOR PRIMARY PUPILS");
//		title.setLanguage("English");
//		x.setTitle(title);
		
		String  abstract_ = "";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
		
//		x.setKeywords(makeKeywordsFrom(Lists.newArrayList(
//				makeKeywordFrom("GROUP", "For Datrix"),
//				makeKeywordFrom("INTELLIGENCE", "For Datrix"),
//				makeKeywordFrom("NON", "For Datrix"),
//				makeKeywordFrom("PRIMARY", "For Datrix"),
//				makeKeywordFrom("PUPILS", "For Datrix"),
//				makeKeywordFrom("TESTS", "For Datrix"),
//				makeKeywordFrom("VERBAL", "For Datrix")
//				)));
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"JOHNSON, OSCAR JULIUS", null, 1, 
						makeDegreeFrom("Ph.D.", "Doctor of Philosophy", "1921", 1)));
		x.setAuthors(authors);
					
		return x;
	}
	
	// Pub U183503
	public static final DisPubMetaData makeExpectedMetaData3() {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId3);
		x.setPageCount("1");
		x.setExternalURL("http://eprints.soton.ac.uk/15468");
		x.setBLNumber("DXN074035");
		
//		Batch batch = new Batch();
//		batch.setDBTypeCode("DAI");
//		batch.setDBTypeDesc("Dissertation Abstracts International");
//		batch.setVolumeIssue("70-37");
//		batch.setDAISectionCode("C");
//		x.setBatch(batch);
		
//		School school = new School();
//		school.setSchoolCode("5036");
//		school.setSchoolName("University of Southampton (United Kingdom)");
//		school.setSchoolCountry("ENGLAND");
//		school.setSchoolState(null);
//		x.setSchool(school);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Pure Sciences", "0752", "Physics, Optics", 1));
		x.setSubjects(subjects);
		
//		Title title = new Title();
//		title.setValue("Fundamental properties of Bragg gratings and their application to the design of advanced structures.");
//		title.setLanguage("English");
//		x.setTitle(title);
		
		String abstract_ = "<![CDATA[This thesis presents the analysis of the local properties of Bragg gratings and their application to the improvement of standard designs and advanced structures.  The time spent by light inside each grating section is derived in terms of complex-valued quantities, and clear meaning is given to both the real and imaginary parts.  Improved physical understanding of propagation and energy distributions inside periodic structures is obtained.  Local properties also explain in a more intuitive way well understood features of different gratings, which improves intuition of new complex designs.  The analysis of the effect of perturbations is immediate using this approach and has important practical applications.  Independent confirmation of the theory is obtained, and experimental measurement of the imaginary part of the local time delay is given.  Phase errors affect the grating writing techniques, and the related sensitivity is analysed in detail. The robustness of different designs is discussed with respect to such manufacturing errors.  Fine tuning of standard or advanced grating designs by means of suitable error distributions is also proposed, and optimised characteristics either in the reflectivity or in the dispersive response are obtained.  This method is integrated with inverse scattering designs to further boost their performances.  Improved complex designs are also proposed in case losses affect propagation in the grating.  Cladding mode losses are compensated using an iterative layer-peeling algorithm.  The design of the first wide-band dispersion-compensating grating realised with a standard single mode fibre is shown.  Background losses and UV-induced losses in gratings are also compensated using a modified layer-peeling method.  The physical limitations related to grating design in lossy media are explained using the derived understanding of local properties.  New advanced designs are also considered that fully exploit the theoretical potentialities and manufacturing capabilities of Bragg gratings.  The performance of code-division multiple access systems based on superstructured gratings is improved by combining encoding, bandwidth filtering, and dispersion compensation in the same high reflectivity grating.\n]]>";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");		
		x.setDissLanguages(Lists.newArrayList(language));
		
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Ghiringhelli, F.", null, 1, 
						makeDegreeFrom("Ph.D.", "Doctor of Philosophy", "2003", 1)));
		x.setAuthors(authors);
		
		return x;
	}
	
	// Pub H000684
	public static final DisPubMetaData makeExpectedMetaData4() {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId4);
		
//		Batch batch = new Batch();
//		batch.setDBTypeCode("DAI");
//		batch.setDBTypeDesc("Dissertation Abstracts International");
//		batch.setVolumeIssue("71-17");
//		batch.setDAISectionCode("C");
//		x.setBatch(batch);
		
//		School school = new School();
//		school.setSchoolCode("0370");
//		school.setSchoolName("Tsinghua University (People's Republic of China)");
//		school.setSchoolCountry("PEOPLES REP. OF CHINA");
//		school.setSchoolState(null);
//		x.setSchool(school);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Applied Sciences", "0543", "Engineering, Civil", 1));
		x.setSubjects(subjects);
		
//		Title title = new Title();
//		title.setValue("Development of an air conditioner controller using fuzzy control theory");
//		title.setLanguage("Chinese");
//		x.setTitle(title);
		
//		AlternateTitle alternateTitle = new AlternateTitle();
//		alternateTitle.setValue("应用模糊控制原理的空调控制器的开发");
//		alternateTitle.setLanguage("Chinese");
//		x.setAlternateTitles(new AlternateTitles());
//		x.getAlternateTitles().getAlternateTitle().add(alternateTitle);
		
		//String abstract_ = "In this paper, a new control method for room air conditioners (RACs) is presented, and a new RAC controller for the conventional RACs without inverters is developed in this basis. First, the RAC market conditions both domestic and abroad, as well as the development of RAC and RAC controllers are shown. Then some latest result on human thermo comfort are introduced. On this basis, a new control method for RACs which has considered the human thermo comfort principles is presented. This method has employed the fuzzy control theory which is suitable for the complex system such as the room-RAC system. After a detailed analysis on conventional RACs, the new method is applied on this kind of RAC. Then, computer simulations on this control method and two other conventional control methods  which are for comparation are done. Results show that the new method performs better on temperature flucturation, on-off times of compressor and unsensitivity on system parameter changes. Last, an RAC controller which employes the new control method is successfully developed. Test showes it has good electrical performance. It has low cost and high reliability which is ready for batch production.";
		String abstract_ = "In this paper, a new control method for room air conditioners (RACs) is presented, and a new RAC controller for the conventional RACs without inverters is developed in this basis. First, the RAC market conditions both domestic and abroad, as well as the development of RAC and RAC controllers are shown. Then some latest result on human thermo comfort are introduced. On this basis, a new control method for RACs which has considered the human thermo comfort principles is presented. This method has employed the fuzzy control theory which is suitable for the complex system such as the room-RAC system. After a detailed analysis on conventional RACs, the new method is applied on this kind of RAC. Then, computer simulations on this control method and two other conventional control methods  which are for comparation are done. Results show that the new method performs better on temperature flucturation, on-off times of compressor and unsensitivity on system parameter changes. Last, an RAC controller which employes the new control method is successfully developed. Test showes it has good electrical performance. It has low cost and high reliability which is ready for batch production.";
		x.setAbstract(abstract_);

		DissLanguage language = new DissLanguage("Chinese", "CH");
		x.setDissLanguages(Lists.newArrayList(language));
		
//		x.setKeywords(makeKeywordsFrom(Lists.newArrayList(
//				makeKeywordFrom("房间空调器", "By Author"),
//				makeKeywordFrom("模糊控制", "By Author"),
//				makeKeywordFrom("空气调节", "By Author"),
//				makeKeywordFrom("自由词", "By Author"),
//				makeKeywordFrom("计算机仿真", "By Author")
//				)));

//		Advisors advisors = new Advisors();
//		advisors.getAdvisor().add(makeAdvisorFrom("Guan Ping  Feng", "冯冠平", "Chinese"));
//		x.setAdvisors(advisors);
		
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

		return x;
	}
	
	// NR93884
	public static final DisPubMetaData makeExpectedMetaData5() {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId5);
		x.setISBN("978-0-494-93884-3");
		x.setPageCount("186");

//		School school = new School();
//		school.setSchoolCode("0862");
//		school.setSchoolName("Universite du Quebec a Chicoutimi (Canada)");
//		school.setSchoolCountry("CANADA");
//		school.setSchoolState(null);
//		x.setSchool(school);
		
//		Batch batch = new Batch();
//		batch.setDBTypeCode("DAI");
//		batch.setDBTypeDesc("Dissertation Abstracts International");
//		batch.setVolumeIssue("74-09(E)");
//		batch.setDAISectionCode("B");
//		x.setBatch(batch);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Applied Sciences", "0794", "Engineering, Materials Science", 1));
		x.setSubjects(subjects);
		
//		Title title = new Title();
//		title.setValue("Rheological behavior and microstructural evolution of semi-solid<p>hypereutectic aluminum-silicon-magnesium-copper alloys using rheoforming process");
//		title.setLanguage("English");
//		x.setTitle(title);
		
		String abstract_ = "<?Pub Inc> The aim of the current study was to investigate the rheological behavior and microstructural evolution of hypereutectic Al-Si-Cu and Al-Si-Mg-Cu alloys using conventional and modified SEED process (Swirled Enthalpy Equilibration Device). ^   In the first part; the feasibility of semi-solid processing of hypereutectic Al-Si-Cu A390 alloys using a novel rheoforming process was investigated. A combination of the SEED process, isothermal holding using insulation and addition of solid alloy during swirling was introduced as a novel method to improve the processability of semi-solid A390 slurries. The effects of isothermal holding and the addition of solid alloy on the temperature gradient between the centre and the wall and on the formation of &alpha;-Al particles were examined. In addition, phosphorus and strontium were added to the molten metal to refine the primary and eutectic silicon structure to facilitate semi-solid processing. It was found that the combination of the SEED process with two additional processing steps can produce semisolid 390 alloys that can be rheoformed. ^   In the second part, the effects of Mg additions ranging from 6 to 15% on the solidification behaviour of hypereutectic Al-155i-xMg-4Cu alloys was investigated using thermodynamic calculations, thermal analysis and extensive microstructural examination. The Mg level strongly influenced the microstructural evolution of the primary Mg<sub>2</sub>Si phase as well as the solidification behaviour. Thermodynamic predictions using ThermoCalc software reported the occurrence of six reactions, comprising the formation of primary Mg<sub>2 </sub>Si, two pre-eutectic binary reactions, forming either Mg<sub>2</sub>Si + Si or Mg<sub>2</sub>Si + &alpha;-Al phases, the main ternary eutectic reaction forming Mg<sub>2</sub>Si + Si + &alpha;-Al, and two post-eutectic reactions resulting in the precipitation of the Q-Al<sub>5</sub>Mg<sub>8</sub>Cu<sub> 2</sub>Si<sub>6</sub> and &thetas;-Al<sub>2</sub>Cu phases, respectively. Microstructures of the four alloys studied confirmed the presence of these phases, in addition to that of the &pi;-Al<sub>8</sub>Mg<sub>3</sub>FeSi<sub> 6</sub> phase. The presence of the &pi;-phase was also confirmed by thermal analysis. ^   In the third part, the effects of P and Sr on the microstructure of hypereutectic Al-155i14Mg-4Cu alloy were studied. The microstructural examination and phase identification were carried out using optical microscopy and scanning electron microscopy (SEM). The effects of individual and combined additions of P and Sr on the eutectic arrest in Al-155i14Mg-4Cu alloy were examined using thermal analysis. The mean size of primary Mg<sub>2</sub>Si decreases from about 350 &mu;m to less than 60 &mu;m and the morphology changes from coarse dendritic type or equiaxed to polygonal type. In addition, the morphology of the eutectic Mg<sub>2</sub>Si phase changes from coarse Chinese script to fine fiber-like, while that of the eutectic Si phase changes from coarse acicular shape to a fine fibrous form. With Sr addition, the morphology of the &pi;-Fe phase evolved from Chinese script to a fine twin platelet form. Furthermore, the thermal analysis results reveal that the addition of Sr or Sr and P reduces the temperature of eutectic nucleation and growth. ^   Finally, the rheological behaviour and microstructure of semi-solid hypereutectic A390, P-refined A390, Al-15Si-10.5Mg-4Cu and Al-15Si-13.5Mg-4Cu alloys were investigated by using parallel plate viscometry. The flow deformation of these alloys in the semi-solid state was characterized at different deformation rates and at variable solid fractions. The calculated viscosity for variable shear rate was deduced using the analytical method developed by Laxmanan and Flemings. Microstructures of the four alloys, after partial solidification, were examined in order to characterize the flow behaviour during deformation.  (Abstract shortened by UMI.)^";
		x.setAbstract(abstract_);
				
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
//		x.setKeywords(makeKeywordsFrom(Lists.newArrayList(
//				makeKeywordFrom("ALLOYS", "For Datrix"),
//				makeKeywordFrom("ALUMINUM", "For Datrix"),
//				makeKeywordFrom("ALUMINUM ALLOYS", "For Datrix"),
//				makeKeywordFrom("Aluminum alloys", "By I and L"),
//				makeKeywordFrom("BEHAVIOR", "For Datrix"),
//				makeKeywordFrom("COPPER", "For Datrix"),
//				makeKeywordFrom("EVOLUTION", "For Datrix"),
//				makeKeywordFrom("MAGNESIUM", "For Datrix"),
//				makeKeywordFrom("MICROSTRUCTURAL", "For Datrix"),
//				makeKeywordFrom("PROCESS", "For Datrix"),
//				makeKeywordFrom("RHEOFORMING", "For Datrix"),
//				makeKeywordFrom("RHEOLOGICAL", "For Datrix"),
//				makeKeywordFrom("RHEOLOGY", "For Datrix"),
//				makeKeywordFrom("Rheoforming", "By I and L"),
//				makeKeywordFrom("Rheology", "By I and L"),
//				makeKeywordFrom("SEMI", "For Datrix"),
//				makeKeywordFrom("SEMISOLID METALS", "For Datrix"),
//				makeKeywordFrom("SILICON", "For Datrix"),
//				makeKeywordFrom("SOLID\nHYPEREUTECTIC", "For Datrix"),
//				makeKeywordFrom("Semisolid metals", "By I and L"),
//				makeKeywordFrom("USING", "For Datrix")
//				)));
		
//		Advisors advisors = new Advisors();
//		advisors.getAdvisor().add(makeAdvisorFrom("X. Grant Chen", null, null));
//		x.setAdvisors(advisors);
		
		x.setCmteMembers(null);
		x.setSuppFiles(null);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Tebib, Mehand", null, 1, 
						makeDegreeFrom("Ph.D.", "Doctor of Philosophy", "2012", 1)));
		x.setAuthors(authors);
		
		return x;
	}
	
	// 1535737
	public static final DisPubMetaData makeExpectedMetaData6() {
		DisPubMetaData x = new DisPubMetaData();
		x.setPubNumber(pubId6);
		x.setISBN("978-1-303-02410-8");
		x.setPageCount("9");

//		School school = new School();
//		school.setSchoolCode("0033");
//		school.setSchoolName("University of California, San Diego");
//		school.setSchoolCountry("UNITED STATES");
//		school.setSchoolState("California");
//		x.setSchool(school);
		
//		Batch batch = new Batch();
//		batch.setDBTypeCode("MAI");
//		batch.setDBTypeDesc("Masters Abstracts International");
//		batch.setVolumeIssue("51-05M(E)");
//		batch.setDAISectionCode(null);
//		x.setBatch(batch);
		
		List<Subject> subjects = Lists.newArrayList(
				makeSubjectForTesting("Communication and the Arts", "0465", "Theater", 1));
		x.setSubjects(subjects);
		
//		Title title = new Title();
//		title.setValue("Finding the Flow: Management Style in \"In the Red and Brown Water\"");
//		title.setLanguage("English");
//		x.setTitle(title);
		
		String abstract_ = "<?Pub Inc> The prevailing metaphors in <italic>In the Red and Brown Water</italic> are wind and water, and I likened my work on the production to a river. Water can be strong, and also gentle. It can adapt to terrain or transform landscape. During this collaboration I focused on flowing with the production process, as ideas emerged and evolved and decisions were fluid. It has been a huge breakthrough to trust my instincts in a new way to serve the production, and discover that I cannot default to my impulses to impose rigidity and structure.  A production, like a river, is going to keep flowing and a group of artists cannot be controlled. I guided and adapted to the process, but I could not change it, because it was bigger and stronger than me.  ^   During my graduate studies, I have learned that there is no \"right way,\" there is only the way I am working right now, and my approach can be different for each project. As someone who enjoys structure and routine, the open-ended possibilities are challenging to accept. But as an experiential learner, flexibility has been my journey, and through many theatre and dance productions I have developed my personal stage management style to be open to the needs of each project. With the culmination of my formal training, my style is to be more like water: finding the flow and guiding others through the production.^";
		x.setAbstract(abstract_);
		
		DissLanguage language = new DissLanguage("English", "EN");
		x.setDissLanguages(Lists.newArrayList(language));
		
//		x.setKeywords(makeKeywordsFrom(Lists.newArrayList(
//				makeKeywordFrom("BROWN", "For Datrix"),
//				makeKeywordFrom("FINDING", "For Datrix"),
//				makeKeywordFrom("FLOW", "For Datrix"),
//				makeKeywordFrom("MANAGEMENT", "For Datrix"),
//				makeKeywordFrom("RED", "For Datrix"),
//				makeKeywordFrom("STAGE MANAGEMENT", "For Datrix"),
//				makeKeywordFrom("STYLE", "For Datrix"),
//				makeKeywordFrom("Stage management", "By Author"),
//				makeKeywordFrom("WATER", "For Datrix")
//				)));
		
//		Departments departments = new Departments();
//		departments.getDepartment().add("Theatre and Dance");
//		x.setDepartments(departments);
		
		List<SuppFile> supplementalFiles = Lists.newArrayList(
				makeSuppFileFrom("ZingleCallingPages11-13.pdf", "pdf", PubMetaDataQuery.kEmptyValue),
				makeSuppFileFrom("ZingleCallingPages3-5.pdf", "pdf", PubMetaDataQuery.kEmptyValue),
				makeSuppFileFrom("ZingleCallingPages94-96.pdf", "pdf", "In the Red and Brown Water Calling Pages 94-96"));
		x.setSuppFiles(supplementalFiles);
		
		List<CmteMember> comittee = Lists.newArrayList(
				makeCmteMemberFrom("John", "B.", "Doller", null),
				makeCmteMemberFrom("Charles", "E.", "Means", null), 
				makeCmteMemberFrom("Gregory", "D.", "Wallace", null));
		x.setCmteMembers(comittee);
		
//		Advisors advisors = new Advisors();
//		advisors.getAdvisor().add(makeAdvisorFrom("Lisa J. Porter", null, null));
//		x.setAdvisors(advisors);
		
		List<Author> authors = Lists.newArrayList();
		authors.add(
				makeAuthorFrom(
						"Zingle, Laura Maria", null, 1, 
						makeDegreeFrom("M.F.A.", "Master of Fine Arts", "2013", 1)));
		x.setAuthors(authors);
		
		return x;
	}
	
	static PubMetaDataProvider target;

	@BeforeClass
	public static void setUp() throws Exception {		
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		target = new PubMetaDataProvider(connectionPool);
		//metaDataResult = target.getPubMetaDataFor(kAllPubIds);
		//actualMetaData = metaDataResult.getMetaData();
	}
	
	
//	@Test
//	public void missingPubs_HasPub1() throws Exception {
//		assertThat(metaDataResult.getMissingPubs(), hasItem(noVolumeIssuePub1));
//	}
	
//	@Test
//	public void missingPubs_HasPub2() throws Exception {
//		assertThat(metaDataResult.getMissingPubs(), hasItem(fakePub1));
//	}
	
//	@Test
//	public void metaData_HasCorrectCount() throws Exception {
//		assertThat(actualMetaData.size(), is(7));
//	}
	
	@Test
	public void onePub_0() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(pubId0);
		assertThat(metaDataResult, dissertationEqualTo(makeExpectedMetaData0()));
	}
	
	@Test
	public void onePub_1() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(pubId1);
		assertThat(metaDataResult, dissertationEqualTo(makeExpectedMetaData1()));
	}
	
	@Test
	public void onePub_2() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(pubId2);
		assertThat(metaDataResult, dissertationEqualTo(makeExpectedMetaData2()));
	}
	
	@Test
	public void onePub_3() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(pubId3);
		assertThat(metaDataResult, dissertationEqualTo(makeExpectedMetaData3()));		
	}
	
	@Test
	public void onePub_4() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(pubId4);
		assertThat(metaDataResult, dissertationEqualTo(makeExpectedMetaData4()));
	}
	
	@Test
	public void onePub_5() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(pubId5);
		assertThat(metaDataResult, dissertationEqualTo(makeExpectedMetaData5()));
	}
	
	@Test
	public void onePub_6() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(pubId6);
		assertThat(metaDataResult, dissertationEqualTo(makeExpectedMetaData6()));
	}
}
