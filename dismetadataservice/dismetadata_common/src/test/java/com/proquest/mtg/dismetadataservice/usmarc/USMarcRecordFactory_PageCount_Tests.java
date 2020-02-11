package com.proquest.mtg.dismetadataservice.usmarc;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class USMarcRecordFactory_PageCount_Tests extends
		USMarcRecordFactoryBase_Test_Helper {

	String tag = MarcTags.kPageCount;
	String expectedMarcFieldData;
	DisPubMetaData metaData;

	@Before
	public void setup() {
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNull() {
		metaData.setPageCount(null);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmpty() {
		metaData.setPageCount("");
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withPageCount() {
		String pageCount = "978";
		metaData.setPageCount(pageCount);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ pageCount+" p.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}

}
