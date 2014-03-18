package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class USMarcRecordFactory_MakeVariantTitle_Tests extends
		USMarcRecordFactoryBase_Test_Helper {

	String tag = MarcTags.kVariantTitle;

	String expectedMarcFieldData;
	DisPubMetaData metaData;
	Title title;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		title = new Title();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNullVariantTitle() {
		title.setEnglishOverwriteTitle(null);
		metaData.setTitle(title);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyVariantTitle() {
		title.setEnglishOverwriteTitle("");
		metaData.setTitle(title);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNoTitle() {
		metaData.setTitle(title);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withVariantTitle() {
		String variantTitle = "EnglishOverwriteTitle";
		title.setEnglishOverwriteTitle(variantTitle);
		metaData.setTitle(title);
		expectedMarcFieldData = "00" + MarcCharSet.kSubFieldIndicator + "a"
				+ variantTitle + ".";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData));
	}

}
