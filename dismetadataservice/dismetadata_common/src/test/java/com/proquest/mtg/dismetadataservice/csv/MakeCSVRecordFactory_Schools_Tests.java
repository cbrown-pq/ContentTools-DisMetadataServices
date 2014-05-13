package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;

public class MakeCSVRecordFactory_Schools_Tests {
	CSVRecordFactory factory;
	String header = "";
	School school;
	DisPubMetaData metadata;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		school = new School();
	}

	@Test
	public void makeSchoolWithEmpty() throws Exception {
		metadata.setSchool(school);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeSchoolWithEmptySchoolCode() throws Exception {
		school.setSchoolCountry("SchoolCountry");
		school.setSchoolName("SchoolName");
		school.setSchoolState("SchoolState");
		metadata.setSchool(school);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,\"SchoolName\",\"SchoolCountry\",\"SchoolState\",,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeSchoolWithEmptySchoolName() throws Exception {
		school.setSchoolCode("SchoolCode");
		school.setSchoolCountry("SchoolCountry");
		school.setSchoolState("SchoolState");
		metadata.setSchool(school);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,\"SchoolCode\",,\"SchoolCountry\",\"SchoolState\",,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeSchoolWithEmptySchoolCountry() throws Exception {
		school.setSchoolCode("SchoolCode");
		school.setSchoolName("SchoolName");
		school.setSchoolState("SchoolState");
		metadata.setSchool(school);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,\"SchoolCode\",\"SchoolName\",,\"SchoolState\",,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeSchoolWithEmptySchoolState() throws Exception {
		school.setSchoolCode("SchoolCode");
		school.setSchoolName("SchoolName");
		school.setSchoolCountry("SchoolCountry");
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setSchool(school);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,\"SchoolCode\",\"SchoolName\",\"SchoolCountry\",,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void withOnlySchool() throws Exception {
		school.setSchoolName("Massachusetts Institute of Technology");
		school.setSchoolCode("0753");
		school.setSchoolCountry("UNITED STATES");
		school.setSchoolState("Massachusetts");
		metadata.setSchool(school);
		String expectedCSVData = header + "\r\n,,,,,,,,,,,,,\"0753\",\"Massachusetts Institute of Technology\",\"UNITED STATES\",\"Massachusetts\",,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";	
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}
