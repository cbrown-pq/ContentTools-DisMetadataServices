package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_AccessNoteRestriction_Tests extends
		Marc21RdaRecordFactory_Test_Helper {
	String tag = MarcTags.kAccessRestrictionNote;
	String expectedMarcFieldData;
	SalesRestriction salesRestriction;

	@Test
	public void withNull() throws Exception {
		DisPubMetaData metaData = new DisPubMetaData();
		salesRestriction = new SalesRestriction();
		salesRestriction.setCode(null);
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(0));
	}

	@Test
	public void withEmpty() throws Exception {
		DisPubMetaData metaData = new DisPubMetaData();
		salesRestriction = new SalesRestriction();
		salesRestriction.setCode("");
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(0));
	}

	@Test
	public void withSalesRestriction5_DoNotSell_ThirdPartyVendor()
			throws Exception {
		String salesRestrictioNote = "This item must not be sold to any third party vendors";
		DisPubMetaData metaData = new DisPubMetaData();
		salesRestriction = new SalesRestriction();
		metaData.setPubNumber(Marc21RdaRecordFactory_Test_Helper.kPubForPdfAvailable);
		salesRestriction.setCode("5");
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ salesRestrictioNote + ".";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}

	@Test
	public void withSalesRestriction5_DoNotSell_ThirdPartySearchIndex()
			throws Exception {
		String salesRestrictioNote = "This item must not be added to any third party search indexes";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(Marc21RdaRecordFactory_Test_Helper.kPubForPdfAvailable);
		salesRestriction = new SalesRestriction();
		salesRestriction.setCode("8");
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ salesRestrictioNote + ".";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}

	@Test
	public void withSalesRestriction_AllOtherRestrictions() throws Exception {
		String salesRestrictioNote = "This item is not available from ProQuest Dissertations & Theses";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(Marc21RdaRecordFactory_Test_Helper.kPubForPdfAvailable);
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setCode("2");
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ salesRestrictioNote + ".";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}

	@Test
	public void withMultipleSalesRestrictions() throws Exception {
		String pubId = "test pub";
		String expectedMarcFieldData1 = "  "
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "This item is not available from ProQuest Dissertations & Theses.";
		String expectedMarcFieldData2 = "  "
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "This item must not be added to any third party search indexes.";
		String expectedMarcFieldData3 = "  " + MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "This item must not be sold to any third party vendors.";

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);
		SalesRestriction salesRestriction1 = new SalesRestriction();
		salesRestriction1.setCode("1");
		SalesRestriction salesRestriction2 = new SalesRestriction();
		salesRestriction2.setCode("8");
		SalesRestriction salesRestriction3 = new SalesRestriction();
		salesRestriction3.setCode("5");
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction1,
				salesRestriction2, salesRestriction3));
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> salesRestrictionFields = marc.getFieldsMatchingTag(tag);
		assertThat(salesRestrictionFields.size(), is(3));
		assertThat(salesRestrictionFields.get(0).getData(),
				is(expectedMarcFieldData1));
		assertThat(salesRestrictionFields.get(1).getData(),
				is(expectedMarcFieldData2));
		assertThat(salesRestrictionFields.get(2).getData(),
				is(expectedMarcFieldData3));
	}

}
