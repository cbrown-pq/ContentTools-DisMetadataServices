package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;

public class MarcRecordFactory_BaseURL_Tests extends
		MarcRecordFactoryBase_Tests {

	DisPubMetaData disPubMetaData;
	String tag = MarcTags.kUrl;
	String expectedMarcFieldData;

	@Test
	public void withNull() {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setExternalURL(null);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmpty() {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setExternalURL("");
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withURL() {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setExternalURL("http://mystuff.com/host?id:");
		metaData.setPubNumber("441720");
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "u"
				+ "http://mystuff.com/host?id:" + "441720";
		MarcRecord marc = factory.makeFrom(metaData);
		assertThat(marc.getFieldCount(), is(3 + kDataIndependentFieldCount));
		assertThat(marc.getFirstFieldMatchingTag(MarcTags.kUrl).getData(),
				is(expectedMarcFieldData));

	}

}
