package com.proquest.mtg.dismetadataservice.csv;

import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_Department_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	List<String> deparments;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
	}

	@Test
	public void makeWithEmptyDepartments() throws Exception {
		metadata = new DisPubMetaData();
		deparments = Lists.newArrayList();
		metadata.setDepartments(deparments);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDepartmentName, null);
	}

	@Test
	public void withOnlyDepartments() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		List<String> deparments = Lists.newArrayList(
				"Applied Behavioral Science", "Communication Studies");
		metadata.setDepartments(deparments);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDepartmentName, "Applied Behavioral Science|Communication Studies");
	}

}
