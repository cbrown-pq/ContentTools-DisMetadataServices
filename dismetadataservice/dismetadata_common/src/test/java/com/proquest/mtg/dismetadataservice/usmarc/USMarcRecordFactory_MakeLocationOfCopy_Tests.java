package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class USMarcRecordFactory_MakeLocationOfCopy_Tests extends
		USMarcRecordFactoryBase_Test_Helper {
	String tag = MarcTags.kLocationOfCopy;

	String expectedMarcFieldData;
	DisPubMetaData metaData;
	String LocationOfCopy;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNullLocationOfCopy() {
		metaData.setReferenceLocation(null);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyLocationOfCopy() {
		metaData.setReferenceLocation("");
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withLocationOfCopy() {
		String locationOfCopy = "Location";
		metaData.setReferenceLocation(locationOfCopy);
		expectedMarcFieldData = "2 " + MarcCharSet.kSubFieldIndicator + "a"
				+ locationOfCopy + ".";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData));
	}

}
