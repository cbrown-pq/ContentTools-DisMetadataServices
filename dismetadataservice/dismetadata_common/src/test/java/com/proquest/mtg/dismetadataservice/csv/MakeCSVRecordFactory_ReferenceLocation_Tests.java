package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;

public class MakeCSVRecordFactory_ReferenceLocation_Tests {
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
	public void makeWithEmptReferenceLocation() throws Exception {
		String refLocation = null;
		metadata.setReferenceLocation(refLocation);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyReferenceLocation() throws Exception {
		metadata.setReferenceLocation("DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN");
		String expectedCSVData = header
				+ "\r\n,,,,,,,\"DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN\",,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
}
