package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_Department_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	List<String> deparments;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus);
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
	}

	@Test
	public void makeWithEmptyDepartments() throws Exception {
		metadata = new DisPubMetaData();
		deparments = Lists.newArrayList();
		metadata.setDepartments(deparments);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDepartments() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		List<String> deparments = Lists.newArrayList(
				"Applied Behavioral Science", "Communication Studies");
		metadata.setDepartments(deparments);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,," + ",\""
				+ "Applied Behavioral Science|Communication Studies" + "\""
				+ ",,,,,,,,,,\"N\",,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}
