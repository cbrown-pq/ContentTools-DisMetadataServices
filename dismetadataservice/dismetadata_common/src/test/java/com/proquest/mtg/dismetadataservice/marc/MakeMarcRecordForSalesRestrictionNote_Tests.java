package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;

public class MakeMarcRecordForSalesRestrictionNote_Tests extends MarcRecordFactoryBase_Tests {
	
	@Test
	public void withPubHavingNoMatchingStartDigit() throws Exception {
		String tag = MarcTags.kAccessRestrictionNote;
		String pubId = "TestPub";
		String salesRestrictioNote = "Test Restriction";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		metaData.setBatch(batch);
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setDescription(salesRestrictioNote);
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
	@Test
	public void withNonDACShouldnotGenerate506Tag() throws Exception {
		String tag = MarcTags.kAccessRestrictionNote;
		String pubId = "01TestPub";
		String salesRestrictioNote = "Test Restriction";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);
		Batch batch = new Batch();
		batch.setDBTypeCode("TEST");
		metaData.setBatch(batch);
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setDescription(salesRestrictioNote);
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
	@Test
	public void withNoSalesRestrictionMessage_OutputDefaultMessage() throws Exception {
		String tag = MarcTags.kAccessRestrictionNote;
		String pubId = "01TestPub";
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a" + 
					"This item is not available from University Microfilms International.";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		metaData.setBatch(batch);
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 4);
	}
	
	@Test
	public void withMatching() throws Exception {
		String tag = MarcTags.kAccessRestrictionNote;
		String pubId = "01TestPub";
		String salesRestrictioNote = "Test Restriction";
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a" + salesRestrictioNote;
		
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		metaData.setBatch(batch);
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setDescription(salesRestrictioNote);
		metaData.setSalesRestrictions(Lists.newArrayList(salesRestriction));
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 4);
	}
	
}
