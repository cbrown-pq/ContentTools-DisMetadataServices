package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfAvailableDateStatus;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;
import com.proquest.mtg.dismetadataservice.media.PubMediaInfoProvider;

public class MakeCSVRecordFactory_PDF_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	PdfAvailableDateStatus pdfAvailableDateStatus;
	PubMediaInfoProvider pubMediaInforProvider;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	String pubNumber;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0);
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		pdfAvailableDateStatus = new PdfAvailableDateStatus();
		pubNumber = "Pub1";
	}

	@Test
	public void makeWithEmptyPdfStatus() throws Exception {

		metadata.setPdfStatus(pdfAvailableDateStatus);
		String expectedCSVData = header
				+ "\r\n,,,\"N\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithPDFAvailableAndEmptyPdfAvailableDate() throws Exception {
		metadata.setPdfStatus(pdfAvailableDateStatus);
		metadata.setPubNumber(pubNumber);
		expect(pdfVaultAvailableStatus.isPdfAvailableInVaultFor(pubNumber)).andStubReturn(true);
		replayAll();
		metadata.setPdfStatus(pdfAvailableDateStatus);
		String expectedCSVData = header + "\r\n" + "\"" + pubNumber + "\",,,\"Y\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void makeWithNoPDFAvailableAndEmptyPdfAvailable() throws Exception {
		metadata.setPdfStatus(pdfAvailableDateStatus);
		metadata.setPubNumber(pubNumber);
		expect(pdfVaultAvailableStatus.isPdfAvailableInVaultFor(pubNumber)).andStubReturn(false);
		replayAll();
		String expectedCSVData = header + "\r\n" + "\"" + pubNumber + "\",,,\"N\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithPDFAvailableDateAndPdfStatusNotAvailable() throws Exception {
		pdfAvailableDateStatus.setPdfAvailableDate("PdfAvailableDate");
		metadata.setPdfStatus(pdfAvailableDateStatus);
		metadata.setPubNumber(pubNumber);
		expect(pdfVaultAvailableStatus.isPdfAvailableInVaultFor(pubNumber)).andStubReturn(false);
		replayAll();
		String expectedCSVData = header + "\r\n" + "\"" + pubNumber + "\",,,\"N\",\"PdfAvailableDate\",\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void makeWithPDFAvailableDateAndPdfStatusAvailable() throws Exception {
		pdfAvailableDateStatus.setPdfAvailableDate("PdfAvailableDate");
		metadata.setPdfStatus(pdfAvailableDateStatus);
		metadata.setPubNumber(pubNumber);
		expect(pdfVaultAvailableStatus.isPdfAvailableInVaultFor(pubNumber)).andStubReturn(true);
		replayAll();
		String expectedCSVData = header + "\r\n" + "\"" + pubNumber + "\",,,\"Y\",\"PdfAvailableDate\",\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
}
