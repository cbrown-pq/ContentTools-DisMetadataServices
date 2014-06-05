package com.proquest.mtg.dismetadataservice.marc21rda;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfAvailableDateStatus;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_ContentType_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	String tag = MarcTags.kContentType;
	String expectedMarcFieldData;
	DisPubMetaData metaData;
	PdfAvailableDateStatus pdfStatus;

	@Before
	public void setup() {
		metaData = new DisPubMetaData();
		pdfStatus = new PdfAvailableDateStatus();
	}
	
	@Test
	public void withNull() throws Exception {
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "aunspecified"
				+ MarcCharSet.kSubFieldIndicator + "bzzz"
				+ MarcCharSet.kSubFieldIndicator + "2rdacontent";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}

	@Test
	public void withNoPdfStatus() throws Exception {
		metaData.setPdfStatus(pdfStatus);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "aunspecified"
				+ MarcCharSet.kSubFieldIndicator + "bzzz"
				+ MarcCharSet.kSubFieldIndicator + "2rdacontent";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}


	@Test
	public void withPdfStatusAvailable() throws Exception {
		metaData.setPdfStatus(pdfStatus);
		metaData.setPubNumber(Marc21RdaRecordFactory_Test_Helper.kPubForPdfAvailable);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "atext"
				+ MarcCharSet.kSubFieldIndicator + "btxt"
				+ MarcCharSet.kSubFieldIndicator + "2rdacontent";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}
	
	@Test
	public void withPdfStatusNotAvailable() throws Exception {
		metaData.setPdfStatus(pdfStatus);
		metaData.setPubNumber(Marc21RdaRecordFactory_Test_Helper.kPubForPdfNotAvailable);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "aunspecified"
				+ MarcCharSet.kSubFieldIndicator + "bzzz"
				+ MarcCharSet.kSubFieldIndicator + "2rdacontent";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}

}
