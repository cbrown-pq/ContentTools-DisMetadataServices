package com.proquest.mtg.dismetadataservice.marc21rda;

import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_SystemControlNumber_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	DisPubMetaData disPubMetaData;
	String tag = MarcTags.kSystemControlNumber;
	String expectedMarcFieldData;

	@Test
	public void withNull() {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setISBN(null);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmpty() {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setISBN("");
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withSystemControlNumber() {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(Marc21RdaRecordFactory_Test_Helper.kPubForPdfNotAvailable);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator
				+ "a(MiAaPQD)AAI" + Marc21RdaRecordFactory_Test_Helper.kPubForPdfNotAvailable;
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}
}
