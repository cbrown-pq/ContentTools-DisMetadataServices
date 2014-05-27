package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfStatus;

public class MakeCSVRecordFactory_PDF_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	PdfStatus pdfStatus;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		pdfStatus = new PdfStatus();
	}

	@Test
	public void makeWithEmptyPdfStatus() throws Exception {

		metadata.setPdfStatus(pdfStatus);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithPDFAvailableAndEmptyPdfAvailableDate() throws Exception {
		pdfStatus.setPdfAvailableStatus(true);
		metadata.setPdfStatus(pdfStatus);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,,," + ",\"Y\"" + ",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void makeWithNoPDFAvailableAndEmptyPdfAvailable() throws Exception {
		pdfStatus.setPdfAvailableStatus(false);
		metadata.setPdfStatus(pdfStatus);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,,," + ",\"N\"" + ",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithPDFAvailableDateAndNoPdfAvailable() throws Exception {
		pdfStatus.setPdfAvailableDate("PdfAvailableDate");
		metadata.setPdfStatus(pdfStatus);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,,," + ",\"N\"" 
				+ ",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void makeWithPDFAvailableDateAndPdfStatusNotAvailable() throws Exception {
		pdfStatus.setPdfAvailableDate("PdfAvailableDate");
		pdfStatus.setPdfAvailableStatus(false);
		metadata.setPdfStatus(pdfStatus);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,,," + ",\"N\"" 
				+ ",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void makeWithPDFAvailableDateAndPdfStatusAvailable() throws Exception {
		pdfStatus.setPdfAvailableDate("PdfAvailableDate");
		pdfStatus.setPdfAvailableStatus(true);
		metadata.setPdfStatus(pdfStatus);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,,," + ",\"Y\"" 
				+ ",\"PdfAvailableDate\",,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}
