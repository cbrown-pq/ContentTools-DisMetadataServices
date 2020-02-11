package com.proquest.mtg.dismetadataservice.marc21rda;

import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_CatalogingSource_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	DisPubMetaData metaData;
	String tag = MarcTags.kCatalogingSource;
	String expectedMarcFieldData;

	@Test
	public void withCatalogingSource() {
		metaData = new DisPubMetaData();
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "MiAaPQD" + MarcCharSet.kSubFieldIndicator + "b" + "eng"
				+ MarcCharSet.kSubFieldIndicator + "c" + "MiAaPQD"
				+ MarcCharSet.kSubFieldIndicator + "e" + "rda";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}
}
