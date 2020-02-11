package com.proquest.mtg.dismetadataservice.marc21rda;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_Isbn_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	String tag = MarcTags.kIsbn;
	String expectedMarcFieldData;
	DisPubMetaData metaData;

	@Before
	public void setup() {
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNull() {
		metaData.setISBN(null);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmpty() {
		metaData.setISBN("");
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withISBN() {
		String isbn = "978-0-315-00059-9";
		metaData.setISBN(isbn);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ isbn.replaceAll("-", "");
		verifyMarcRecordHasCorrectCount(metaData, tag, expectedMarcFieldData, 1);
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}

}
