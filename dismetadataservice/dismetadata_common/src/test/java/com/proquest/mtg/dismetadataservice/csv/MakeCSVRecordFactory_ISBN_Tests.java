package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;

public class MakeCSVRecordFactory_ISBN_Tests {
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
	public void makeWithEmptyISBN() throws Exception {
		String isbn = null;
		metadata.setISBN(isbn);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyISBN() throws Exception {
		metadata.setISBN("978-1-303-03106-9");
		String expectedCSVData = header
				+ "\r\n,,,\"978-1-303-03106-9\",,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}
