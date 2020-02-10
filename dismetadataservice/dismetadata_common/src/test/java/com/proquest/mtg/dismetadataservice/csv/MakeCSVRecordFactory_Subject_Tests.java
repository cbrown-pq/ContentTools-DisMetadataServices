package com.proquest.mtg.dismetadataservice.csv;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_Subject_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	List<Subject> subjects;
	Subject subject;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();
		subjects = new ArrayList<Subject>();
		subject = new Subject();
	}

	@Test
	public void makeWithEmptySubjects() throws Exception {
		metadata.setSubjects(subjects);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectDesc, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectGroupDesc, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectCode, null);
	}

	@Test
	public void makeWithEmptySubjDesc() throws Exception {
		String subjCode = "SubjCode";
		String subjGroupDesc = "SubjGroupDesc";
		subject.setSubjectCode(subjCode);
		subject.setSubjectGroupDesc(subjGroupDesc);
		subjects.add(subject);
		metadata.setSubjects(subjects);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectDesc, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectGroupDesc, subjGroupDesc);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectCode, subjCode);
	}

	@Test
	public void makeWithEmptySubjCode() throws Exception {
		String subjDesc = "SubjDesc";
		String subjGroupDesc = "SubjGroupDesc";
		subject.setSubjectDesc(subjDesc);
		subject.setSubjectGroupDesc(subjGroupDesc);
		subjects.add(subject);
		metadata.setSubjects(subjects);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectDesc, subjDesc);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectGroupDesc, subjGroupDesc);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectCode, null);
	}

	@Test
	public void makeWithEmptySubjGroupDesc() throws Exception {
		String subjCode = "SubjCode";
		String subjDesc = "SubjDesc";
		subject.setSubjectCode(subjCode);
		subject.setSubjectDesc(subjDesc);
		subjects.add(subject);
		metadata.setSubjects(subjects);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectDesc, subjDesc);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectGroupDesc, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectCode, subjCode);
	}

	@Test
	public void withSubjects() throws Exception {
		String subjDesc = "Language, Literature and Linguistics";
		String subjCode = "0297";
		String subjGroupDesc = "Literature, Medieval";
		subject.setSubjectDesc(subjDesc);
		subject.setSubjectCode(subjCode);
		subject.setSubjectGroupDesc(subjGroupDesc);
		subjects.add(subject);
		metadata.setSubjects(subjects);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectDesc, subjDesc);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectGroupDesc, subjGroupDesc);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSubjectCode, subjCode);
	}

}
