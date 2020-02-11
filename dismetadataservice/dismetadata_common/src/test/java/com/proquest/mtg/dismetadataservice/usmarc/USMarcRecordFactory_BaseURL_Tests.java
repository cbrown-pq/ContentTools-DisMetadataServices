package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class USMarcRecordFactory_BaseURL_Tests extends
		USMarcRecordFactoryBase_Test_Helper {

	DisPubMetaData disPubMetaData;
	String tag = MarcTags.kUrl;
	String expectedMarcFieldData;

	@Test
	public void withNull() {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPqOpenURL(null);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmpty() {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPqOpenURL("");
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withURL() {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPqOpenURL("http://mystuff.com/host?id:441720");
		metaData.setPubNumber("441720");
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "u"
				+ "http://mystuff.com/host?id:" + "441720";
		MarcRecord marc = factory.makeFrom(metaData);
		assertThat(marc.getFieldCount(), is(3 + kDataIndependentFieldCount));
		assertThat(marc.getFirstFieldMatchingTag(MarcTags.kUrl).getData(),
				is(expectedMarcFieldData));

	}
}
