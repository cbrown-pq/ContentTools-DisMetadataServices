package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_Advisor_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	Advisors advisors;
	Advisor advisor;
	List<Advisor> advisorList;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();
		advisors = new Advisors();
		advisor = new Advisor();
		advisorList = Lists.newArrayList();
	}

	@Test
	public void makeWithEmptyAdvisors() throws Exception {
		metadata.setAdvisors(advisors);
		String csvData = factory.makeFrom(metadata);
		String value = CSVTestHelper.getValueForHeader(csvData, CSVHeaders.kAdvisors);
        assertThat(value, is(nullValue()));
	}

	@Test
	public void makeWithEmptyAdvisor() throws Exception {

		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metadata.setAdvisors(advisors);
		String csvData = factory.makeFrom(metadata);
		String value = CSVTestHelper.getValueForHeader(csvData, CSVHeaders.kAdvisors);
		assertThat(value, is(nullValue()));
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

		String csvData = factory.makeFrom(metadata);
		String value = CSVTestHelper.getValueForHeader(csvData, CSVHeaders.kAdvisors);
		assertThat(value, is("Moriarty, Matthew D.|Kinstlinger, Gary"));
	}
}
