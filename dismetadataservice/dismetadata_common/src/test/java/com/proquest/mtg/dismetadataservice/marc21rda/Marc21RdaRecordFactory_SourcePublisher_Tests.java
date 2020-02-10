package com.proquest.mtg.dismetadataservice.marc21rda;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_SourcePublisher_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	String tag = MarcTags.kGeneralNote;
	String expectedMarcFieldData;
	DisPubMetaData metaData;

	@Before
	public void setup() {
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNull() {
		metaData.setPublisher(null);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmpty() {
		metaData.setPublisher("");
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withPublisher() {
		String publisher = "Reproprint";
		metaData.setPublisher(publisher);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "aPublisher info.: " + publisher + ".";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}

}
