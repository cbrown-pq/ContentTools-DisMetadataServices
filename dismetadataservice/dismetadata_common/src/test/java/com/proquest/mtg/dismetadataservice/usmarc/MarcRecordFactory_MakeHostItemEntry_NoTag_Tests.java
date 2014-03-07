package com.proquest.mtg.dismetadataservice.usmarc;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class MarcRecordFactory_MakeHostItemEntry_NoTag_Tests extends
		UsMarcRecordFactoryBase_Test_Helper {

	String tag = MarcTags.kHostItemEntry;
	String expectedMarcFieldData1;
	DisPubMetaData metaData;

	String batchType, batchTypeDescription, batchVolumeIssue, daiSectionCode;
	Batch batch;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		batch = new Batch();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNullBatch() {
		metaData.setBatch(batch);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNullBatchTypeCode() {
		batch.setDBTypeCode(null);
		metaData.setBatch(batch);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyBatchTypeCode() {
		batch.setDBTypeCode("");
		metaData.setBatch(batch);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNullBatchTypeDescription() {
		batch.setDBTypeDesc(null);
		metaData.setBatch(batch);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyBatchTypeDescription() {
		batch.setDBTypeDesc("");
		metaData.setBatch(batch);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNullBatchVolumeIssue() {
		batch.setVolumeIssue(null);
		metaData.setBatch(batch);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyBatchVolumeIssue() {
		batch.setVolumeIssue("");
		metaData.setBatch(batch);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNullDAISectionCode() {
		batch.setDAISectionCode(null);
		metaData.setBatch(batch);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyDAISectionCode() {
		batch.setDAISectionCode("");
		metaData.setBatch(batch);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

}
