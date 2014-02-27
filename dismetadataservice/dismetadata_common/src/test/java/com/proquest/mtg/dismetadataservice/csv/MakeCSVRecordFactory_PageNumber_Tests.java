package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;

public class MakeCSVRecordFactory_PageNumber_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
	}

	@Test
	public void makeWithEmptyPageNumber() throws Exception {
		String pageNumber = null;
		metadata.setPubPageNum(pageNumber);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyPubPageNumber() throws Exception {
		metadata.setPubPageNum("152");
		String expectedCSVData = header
				+ "\r\n,,,,\"152\",,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
}
