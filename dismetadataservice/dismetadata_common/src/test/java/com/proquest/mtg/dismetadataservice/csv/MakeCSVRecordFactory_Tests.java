package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfAvailableDateStatus;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;
import com.proquest.mtg.dismetadataservice.metadata.SGMLEntitySubstitution;
import com.proquest.mtg.dismetadataservice.metadata.TextNormalizer;

public class MakeCSVRecordFactory_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	String header = "";
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	
	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0);
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
				+ "\r\n,,,\"N\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(empty);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void acceptance1() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPubNumber("3569004");
		metadata.setAdvisors(makeAdvisors());
		metadata.setISBN("978-1-303-03106-9");
		metadata.setReferenceLocation("DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN");
		metadata.setPublisher("Kuopion Yliopiston Painatuskeskus, Kuopio, Finland");
		metadata.setPqOpenURL("http://pq.com/pub:3569004");
		metadata.setExternalURL("http://www.theses.com/idx/registered_users/etd/8f.asp");
		metadata.setSchool(makeSchool());
		metadata.setSubjects(makeSubjects());
		String expectedCSVData = header
				+ "\r\n\"3569004\",,,\"N\",,\"N\",,,,,,\"Massachusetts Institute of Technology\",,\"Language, Literature and Linguistics\",,\"978-1-303-03106-9\",,,,,,,,\"Moriarty, Matthew D.|Kinstlinger, Gary\",,,\"Literature, Medieval\",\"0297\",\"0753\",\"UNITED STATES\",\"Massachusetts\",,,,,,,,,,,,,,,,,,,,,\"Kuopion Yliopiston Painatuskeskus, Kuopio, Finland\",\"http://www.theses.com/idx/registered_users/etd/8f.asp\",,,\"http://pq.com/pub:3569004\",,,,\"DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN\",";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void acceptance2() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPubNumber("3569004");
		metadata.setAdvisors(makeAdvisors());
		metadata.setReferenceLocation("DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN");
		metadata.setSchool(makeSchool());
		metadata.setSubjects(makeSubjects());
		metadata.setTitle(makeTitle());
		String expectedCSVData = header
				+ "\r\n\"3569004\",\"NAD(+)-glycohydrolase in runderschildklier:  Afzonderen, eigenschappen en bereiden van monoklonale antistoffen.  (Dutch text).\",,\"N\",,\"N\",,,,,,\"Massachusetts Institute of Technology\",,\"Language, Literature and Linguistics\",,,,,,,,,,\"Moriarty, Matthew D.|Kinstlinger, Gary\",,,\"Literature, Medieval\",\"0297\",\"0753\",\"UNITED STATES\",\"Massachusetts\",,,,\"Utilization of an articulation index procedure in the evaluation of hearing-aid efficiency.\",\"Adsorption kinetics at the air-water interface.  \\lbrack Dutch text\\rbrack\",,,,,,,,,,,,,,,,,,,,,,,,\"DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN\",";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void acceptance3() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPubNumber("3569004");
		metadata.setAdvisors(makeAdvisors());
		metadata.setReferenceLocation("DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN");
		metadata.setSchool(makeSchool());
		metadata.setTitle(makeTitle());
		metadata.setAbstract(makeAbstract());

		String expectedCSVData = header
				+ "\r\n\"3569004\",\"NAD(+)-glycohydrolase in runderschildklier:  Afzonderen, eigenschappen en bereiden van monoklonale antistoffen.  (Dutch text).\",,\"N\",,\"N\",,,,,,\"Massachusetts Institute of Technology\",,,,,,\"<abstractText>Aim</italic>. The aim was to evaluate the results of endovascular aneurysm repair (EVAR) of abdominal aortic aneurysms (AAA) in terms of intra-AAA pressure and clinical outcomes. <p>   <italic>Methods and results</italic>. 0.014-inch guide-wire-mounted tip-pressure sensors were used for direct intra-aneurysm sac pressure measurement (DISP) through translumbar puncture of the AAA. Mean Pressure Index was calculated (MPI) as the percentage of mean intra-aneurysm pressure relative to the simultaneous mean intra-aortic pressure. The pressure sensor was evaluated in vitro, in an aneurysm model, showing good agreement with model output (2 mm Hg). Measurements within aneurysm thrombus agreed well with the simultaneous ones from the model's lumen (1 mm Hg). Intra-observer variability of DISP in 15 patients undergoing double measurement, by separate punctures of the AAA, showed MPI median variability of 0% (r = .962, p < .0001). Median MPI in patients with shrinking (n = 11), unchanged (n = 10) and expanding (n = 9) aneurysms without endoleaks at least 1 year after EVAR was 19%, 30% and 59%, respectively. Pulse pressure was also higher in expanding compared to the shrinking AAAs (10 vs. 2 mm Hg, p < .0001). Seven of the 10 patients with unchanged AAAs underwent further imaging follow-up after DISP; 2 expanded (MPIs 47%--63%), 4 shrank (MPI 21%--30%) and I continued unchanged (MPI 14%). Type II endoleaks (6 patients, 7 DISP) seemed to be a varied entity with different degrees of AAA pressurization, even in the same patient at different occasions (range MPI 22%--92%). Successful endoleak embolization resulted in pressure reduction. Between 1998 and 2001, 168 consecutive patients with non-ruptured AAAs were treated by EVAR (n = 117) and open repair (OR) due to anatomical restrictions for EVAR (group A, n = 40) or young age with long life-expectancy (n = 11). Thirty-day mortality in EVAR (n = 117), OR group A and B was 2.6%, 15% and 0%, respectively. EVAR patients had higher ASA classifications (p < .0001). Late survival was not different between the groups and late reinterventions, mainly endovascular, were more frequent in EVAR. <p>   <italic>Conclusions</italic>. DISP is a reliable and reproducible technique for measuring intra-AAA pressure. It may become an important tool for EVAR evaluation by detecting EVAR outcomes early. High pressure is associated with AAA expansion, while low pressure with shrinkage. EVAR provides good results for AAA treatment even with the inclusion of high risk patients. The wide application of EVAR may affect the results of OR, since this tends to be performed in older patients with severe comorbidity and challenging anatomy. OR continues to the first-option for low-risk young patients. <p>\",,,,,,\"Moriarty, Matthew D.|Kinstlinger, Gary\",,,,,\"0753\",\"UNITED STATES\",\"Massachusetts\",,,,\"Utilization of an articulation index procedure in the evaluation of hearing-aid efficiency.\",\"Adsorption kinetics at the air-water interface.  \\lbrack Dutch text\\rbrack\",,,,,,,,,,,,,,,,,,,,,,,,\"DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN\",";

		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void acceptance4() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setPubNumber("3569004");
		metadata.setFormatRestrictions(makeFormatRestriction());
		String expectedCSVData = header
				+ "\r\n\"3569004\",,,\"N\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"C|CE\",\"NONE|NONE\",\"NONE|NONE\",\"NONE|NONE\",,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void acceptance5() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setFormatRestrictions(makeFormatRestriction());
		String expectedCSVData = header
				+ "\r\n,,,\"N\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"C|CE\",\"NONE|NONE\",\"NONE|NONE\",\"NONE|NONE\",,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	private List<FormatRestriction> makeFormatRestriction() {
		PdfAvailableDateStatus pdfStatus = new PdfAvailableDateStatus();
		pdfStatus.setPdfAvailableDate("23-APR-2012");
		List<FormatRestriction> formatRestrictions = new ArrayList<FormatRestriction>();
		FormatRestriction formatRestriction1 = new FormatRestriction();
		formatRestriction1.setCode("C");
		FormatRestriction formatRestriction2 = new FormatRestriction();
		formatRestriction2.setCode("CE");
		formatRestrictions.add(formatRestriction1);
		formatRestrictions.add(formatRestriction2);
		return formatRestrictions;
	}

	private String makeAbstract() {
		String abstractText = "<abstractText>Aim</italic>. The aim was to evaluate the results of endovascular aneurysm repair (EVAR) of abdominal aortic aneurysms (AAA) in terms of intra-AAA pressure and clinical outcomes. ^   <italic>Methods and results</italic>. 0.014-inch guide-wire-mounted tip-pressure sensors were used for direct intra-aneurysm sac pressure measurement (DISP) through translumbar puncture of the AAA. Mean Pressure Index was calculated (MPI) as the percentage of mean intra-aneurysm pressure relative to the simultaneous mean intra-aortic pressure. The pressure sensor was evaluated in vitro, in an aneurysm model, showing good agreement with model output (2 mm Hg). Measurements within aneurysm thrombus agreed well with the simultaneous ones from the model's lumen (1 mm Hg). Intra-observer variability of DISP in 15 patients undergoing double measurement, by separate punctures of the AAA, showed MPI median variability of 0% (r = .962, p &lt; .0001). Median MPI in patients with shrinking (n = 11), unchanged (n = 10) and expanding (n = 9) aneurysms without endoleaks at least 1 year after EVAR was 19%, 30% and 59%, respectively. Pulse pressure was also higher in expanding compared to the shrinking AAAs (10 vs. 2 mm Hg, p &lt; .0001). Seven of the 10 patients with unchanged AAAs underwent further imaging follow-up after DISP; 2 expanded (MPIs 47%&ndash;63%), 4 shrank (MPI 21%&ndash;30%) and I continued unchanged (MPI 14%). Type II endoleaks (6 patients, 7 DISP) seemed to be a varied entity with different degrees of AAA pressurization, even in the same patient at different occasions (range MPI 22%&ndash;92%). Successful endoleak embolization resulted in pressure reduction. Between 1998 and 2001, 168 consecutive patients with non-ruptured AAAs were treated by EVAR (n = 117) and open repair (OR) due to anatomical restrictions for EVAR (group A, n = 40) or young age with long life-expectancy (n = 11). Thirty-day mortality in EVAR (n = 117), OR group A and B was 2.6%, 15% and 0%, respectively. EVAR patients had higher ASA classifications (p &lt; .0001). Late survival was not different between the groups and late reinterventions, mainly endovascular, were more frequent in EVAR. ^   <italic>Conclusions</italic>. DISP is a reliable and reproducible technique for measuring intra-AAA pressure. It may become an important tool for EVAR evaluation by detecting EVAR outcomes early. High pressure is associated with AAA expansion, while low pressure with shrinkage. EVAR provides good results for AAA treatment even with the inclusion of high risk patients. The wide application of EVAR may affect the results of OR, since this tends to be performed in older patients with severe comorbidity and challenging anatomy. OR continues to the first-option for low-risk young patients. ^";
		TextNormalizer abstractNormalizer = new TextNormalizer();
		abstractText = abstractNormalizer.applyTo(abstractText);
		abstractText = SGMLEntitySubstitution.applyAllTo(abstractText);
		return abstractText;
	}

	private Title makeTitle() {
		Title title = new Title();
		title.setMasterTitle("Bridging the gap to peace:  From a new way of thinking into action");
		title.setElectronicTitle("Utilization of an articulation index procedure in the evaluation of hearing-aid efficiency");
		title.setEnglishOverwriteTitle("Adsorption kinetics at the air-water interface.  \\lbrack Dutch text\\rbrack");
		title.setForeignTitle("NAD(+)-glycohydrolase in runderschildklier:  Afzonderen, eigenschappen en bereiden van monoklonale antistoffen.  (Dutch text)");
		return title;
	}

	private List<Subject> makeSubjects() {
		List<Subject> subjects = new ArrayList<Subject>();
		Subject subject = new Subject();
		String subjDesc = "Language, Literature and Linguistics";
		String subjCode = "0297";
		String subjGroupDesc = "Literature, Medieval";
		subject.setSubjectDesc(subjDesc);
		subject.setSubjectCode(subjCode);
		subject.setSubjectGroupDesc(subjGroupDesc);
		subjects.add(subject);
		return subjects;
	}

	private Advisors makeAdvisors() {
		Advisors advisors = new Advisors();
		List<Advisor> advisorList = new ArrayList<Advisor>();
		Advisor advisor1 = new Advisor();
		advisor1.setAdvisorFullName("Moriarty, Matthew D.");
		Advisor advisor2 = new Advisor();
		advisor2.setAdvisorFullName("Kinstlinger, Gary");
		advisorList.add(advisor1);
		advisorList.add(advisor2);
		advisors.setAdvisor(advisorList);
		return advisors;
	}

	private School makeSchool() {
		School school = new School();
		school.setSchoolName("Massachusetts Institute of Technology");
		school.setSchoolCode("0753");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("Massachusetts");
		return school;
	}

}
