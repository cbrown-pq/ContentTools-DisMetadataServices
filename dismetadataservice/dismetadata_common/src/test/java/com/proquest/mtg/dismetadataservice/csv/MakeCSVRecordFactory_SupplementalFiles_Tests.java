package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;

public class MakeCSVRecordFactory_SupplementalFiles_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	SuppFile suppFile;
	List<SuppFile> suppFiles;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}

		suppFile = new SuppFile();
		suppFiles = new ArrayList<SuppFile>();
	}

	@Test
	public void makeWithEmptySupplemenatalFiles() throws Exception {
		metadata = new DisPubMetaData();
		metadata.setSuppFiles(suppFiles);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptySupFileName() throws Exception {
		String suppFileDesc = "SuppFileDesc";
		String suppFileCategory = "SuppFileCategory";
		suppFile.setSuppFileDesc(suppFileDesc);
		suppFile.setSuppFileCategory(suppFileCategory);
		suppFiles.add(suppFile);
		metadata.setSuppFiles(suppFiles);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"Y\"" + ",,\"" + suppFileDesc + "\"" + ",\""
				+ suppFileCategory + "\"" + ",,,,,,,,,,,\"N\",,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptySupFileDesc() throws Exception {
		String suppFileName = "SuppFileName";
		String suppFileCategory = "SuppFileCategory";
		suppFile.setSuppFilename(suppFileName);
		suppFile.setSuppFileCategory(suppFileCategory);
		suppFiles.add(suppFile);
		metadata.setSuppFiles(suppFiles);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"Y\"" + ",\"" + suppFileName + "\"" + ",,\""
				+ suppFileCategory + "\"" + ",,,,,,,,,,,\"N\",,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptySupFileCategory() throws Exception {
		String suppFileName = "SuppFileName";
		String suppFileDesc = "SuppFileDesc";
		suppFile.setSuppFileDesc(suppFileDesc);
		suppFile.setSuppFilename(suppFileName);
		suppFiles.add(suppFile);
		metadata.setSuppFiles(suppFiles);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"Y\"" + ",\"" + suppFileName + "\"" + ",\"" + suppFileDesc
				+ "\"" + "," + ",,,,,,,,,,,\"N\",,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withSupplementalFiles() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		String suppFileName = "Yodel2ArchitecturalAnalysis.pdf";
		String suppFileDesc = "Architecture Analysis";
		String suppFileCategory = "pdf";
		suppFile.setSuppFilename(suppFileName);
		suppFile.setSuppFileDesc(suppFileDesc);
		suppFile.setSuppFileCategory(suppFileCategory);
		List<SuppFile> suppFiles = new ArrayList<SuppFile>();
		suppFiles.add(suppFile);
		metadata.setSuppFiles(suppFiles);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"Y\"" + ",\"" + suppFileName + "\"" + ",\"" + suppFileDesc
				+ "\"" + ",\"" + suppFileCategory + "\""
				+ ",,,,,,,,,,,\"N\",,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
}
