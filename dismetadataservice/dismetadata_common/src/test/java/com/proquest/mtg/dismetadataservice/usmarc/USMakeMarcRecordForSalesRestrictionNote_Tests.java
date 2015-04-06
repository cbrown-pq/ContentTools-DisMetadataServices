package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class USMakeMarcRecordForSalesRestrictionNote_Tests extends USMarcRecordFactoryBase_Test_Helper {
	
	@Test
	public void withSalesRestrictionCodeOtherThan5or8_OutputDefaultMessage() throws Exception {
		String tag = MarcTags.kAccessRestrictionNote;
		String pubId = "TestPub";
		String expectedNote = "This item is not available from ProQuest Dissertations & Theses.";
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a" + expectedNote;
		String salesRestrictioNote = "Test Restriction";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		metaData.setBatch(batch);
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setCode("9");
		salesRestriction.setDescription(salesRestrictioNote);
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 4);		
	}
	
	@Test
	public void withSaleRestricNoteShouldGenerate506Tag() throws Exception {
		// Restriction code should be either 5 or 8
		String tag = MarcTags.kAccessRestrictionNote;
		String pubId = "01TestPub";
		String salesRestrictioNote = "Test Restriction";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);
		Batch batch = new Batch();
		batch.setDBTypeCode("TEST");
		metaData.setBatch(batch);
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setCode("5");
		salesRestriction.setDescription(salesRestrictioNote);
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
	}
	
	@Test
	public void withNoSalesRestrictionMessage_OutputNoMessage() throws Exception {
		String tag = MarcTags.kAccessRestrictionNote;
		String pubId = "01TestPub";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
}
