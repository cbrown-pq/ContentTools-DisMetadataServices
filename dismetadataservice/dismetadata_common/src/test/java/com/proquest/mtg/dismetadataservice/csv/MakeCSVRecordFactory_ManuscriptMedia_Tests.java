package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.ManuscriptMedia;

public class MakeCSVRecordFactory_ManuscriptMedia_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	ManuscriptMedia manuscriptMedia;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		manuscriptMedia = new ManuscriptMedia();
	}

	@Test
	public void makeWithManuscriptMediaCodeNull() throws Exception {
		String manuscriptMediaCode = null;
		manuscriptMedia.setManuscriptMediaCode(manuscriptMediaCode);
		metadata.setManuscriptMedia(manuscriptMedia);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void makeWithManuscriptMediaDescNull() throws Exception {
		String manuscriptMediaDesc = null;
		manuscriptMedia.setManuscriptMediaDesc(manuscriptMediaDesc);
		metadata.setManuscriptMedia(manuscriptMedia);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void makeWithManuscriptMediaCode() throws Exception {
		String manuscriptMediaCode = "ManuscriptMediaCode";
		manuscriptMedia.setManuscriptMediaCode(manuscriptMediaCode);
		metadata.setManuscriptMedia(manuscriptMedia);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,\"ManuscriptMediaCode\",,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void makeWithManuscriptMediaDesc() throws Exception {
		String manuscriptMediaDesc = "ManuscriptMediaDesc";
		manuscriptMedia.setManuscriptMediaCode(manuscriptMediaDesc);
		metadata.setManuscriptMedia(manuscriptMedia);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,\"ManuscriptMediaDesc\",,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}


}
