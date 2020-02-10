package com.proquest.mtg.dismetadataservice.csv;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_Schools_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	School school;
	DisPubMetaData metadata;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();
		school = new School();
	}

	@Test
	public void makeSchoolWithEmpty() throws Exception {
		metadata.setSchool(school);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolName, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCountry, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolState, null);
	}

	@Test
	public void makeSchoolWithEmptySchoolCode() throws Exception {
		school.setSchoolCountry("SchoolCountry");
		school.setSchoolName("SchoolName");
		school.setSchoolState("SchoolState");
		metadata.setSchool(school);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolName, "SchoolName");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCountry, "SchoolCountry");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolState, "SchoolState");
	}

	@Test
	public void makeSchoolWithEmptySchoolName() throws Exception {
		school.setSchoolCode("SchoolCode");
		school.setSchoolCountry("SchoolCountry");
		school.setSchoolState("SchoolState");
		metadata.setSchool(school);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolName, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCode, "SchoolCode");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCountry, "SchoolCountry");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolState, "SchoolState");
	}

	@Test
	public void makeSchoolWithEmptySchoolCountry() throws Exception {
		school.setSchoolCode("SchoolCode");
		school.setSchoolName("SchoolName");
		school.setSchoolState("SchoolState");
		metadata.setSchool(school);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolName, "SchoolName");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCode, "SchoolCode");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCountry, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolState, "SchoolState");
	}

	@Test
	public void makeSchoolWithEmptySchoolState() throws Exception {
		school.setSchoolCode("SchoolCode");
		school.setSchoolName("SchoolName");
		school.setSchoolCountry("SchoolCountry");
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setSchool(school);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolName, "SchoolName");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCode, "SchoolCode");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCountry, "SchoolCountry");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolState, null);
	}
	
	@Test
	public void withOnlySchool() throws Exception {
		school.setSchoolName("Massachusetts Institute of Technology");
		school.setSchoolCode("0753");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("Massachusetts");
		metadata.setSchool(school);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolName, "Massachusetts Institute of Technology");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCode, "0753");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolCountry, "UNITED STATES");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSchoolState, "Massachusetts");
	}

}
