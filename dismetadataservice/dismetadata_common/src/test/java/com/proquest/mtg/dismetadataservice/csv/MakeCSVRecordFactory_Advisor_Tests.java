package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;

public class MakeCSVRecordFactory_Advisor_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	Advisors advisors;
	Advisor advisor;
	List<Advisor> advisorList;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		advisors = new Advisors();
		advisor = new Advisor();
		advisorList = Lists.newArrayList();
	}

	@Test
	public void makeWithEmptyAdvisors() throws Exception {
		metadata.setAdvisors(advisors);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptyAdvisor() throws Exception {

		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metadata.setAdvisors(advisors);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyAdvisor() throws Exception {
		Advisor advisor1 = new Advisor();
		advisor1.setAdvisorFullName("Moriarty, Matthew D.");
		Advisor advisor2 = new Advisor();
		advisor2.setAdvisorFullName("Kinstlinger, Gary");
		advisorList.add(advisor1);
		advisorList.add(advisor2);
		advisors.setAdvisor(advisorList);
		metadata.setAdvisors(advisors);
		String expectedCSVData = header
				+ "\r\n,,\"Moriarty, Matthew D.|Kinstlinger, Gary\",,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";

		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
}
