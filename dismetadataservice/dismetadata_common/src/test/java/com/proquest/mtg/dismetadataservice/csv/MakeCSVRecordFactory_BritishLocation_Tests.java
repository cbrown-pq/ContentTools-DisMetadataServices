package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;

public class MakeCSVRecordFactory_BritishLocation_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
	}

	@Test
	public void makeWithEmptyBritishLocation() throws Exception {
		metadata = new DisPubMetaData();
		String blNumber = null;
		metadata.setBLNumber(blNumber);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyBritishLocation() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setBLNumber("BLNumber");
		String expectedCSVData = header
				+ "\r\n,,,,,,,,\"BLNumber\",,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
}
