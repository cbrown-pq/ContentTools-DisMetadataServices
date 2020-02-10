package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class MarcRecordFactory_MakeHostItemEntry_Tests extends
	Marc21RdaRecordFactory_Test_Helper{

	Batch batch;
	DisPubMetaData metaData;
	String tag = MarcTags.kHostItemEntry;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		batch = new Batch();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withVolumeIssueAndDAIDBType_DAICode(){
		String desc = "Disseration Abstracts International";
		batch.setVolumeIssue("67-03");
		batch.setDAISectionCode("A");
		batch.setDBTypeDesc(desc);
		batch.setDBTypeCode("DAI");
		
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "0 " + MarcCharSet.kSubFieldIndicator  + "t" 
				+ desc + MarcCharSet.kSubFieldIndicator  + "g" + "67-03A.";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withVolumeIssueAndDAIDBType_NoDAICode1(){
		String desc = "Disseration Abstracts International";
		batch.setVolumeIssue("73-06C");
		batch.setDAISectionCode(null);
		batch.setDBTypeDesc(desc);
		batch.setDBTypeCode("DAI");
		
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "0 " + MarcCharSet.kSubFieldIndicator  + "t" 
				+ desc + MarcCharSet.kSubFieldIndicator  + "g" + "73-06C.";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withVolumeIssueAndDAIDBType_NoDAICode2(){
		String desc = "Disseration Abstracts International";
		batch.setVolumeIssue("UNC23");
		batch.setDAISectionCode(null);
		batch.setDBTypeDesc(desc);
		batch.setDBTypeCode("DAI");
		
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "0 " + MarcCharSet.kSubFieldIndicator  + "t" 
				+ desc + MarcCharSet.kSubFieldIndicator  + "g" + "UNC23.";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withVolumeIssueAndMAIDBType1(){
		String desc = "Masters Abstracts International";
		batch.setVolumeIssue("S0032");
		batch.setDBTypeDesc(desc);
		batch.setDBTypeCode("MAI");
		
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "0 " + MarcCharSet.kSubFieldIndicator  + "t" 
				+ desc + MarcCharSet.kSubFieldIndicator  + "g" + "S0032.";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withVolumeIssueAndMAIDBType2(){
		String desc = "Masters Abstracts International";
		batch.setVolumeIssue("00-00M");
		batch.setDBTypeDesc(desc);
		batch.setDBTypeCode("MAI");
		
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "0 " + MarcCharSet.kSubFieldIndicator  + "t" 
				+ desc + MarcCharSet.kSubFieldIndicator  + "g" + "00-00.";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withVolumeIssueAndMAIDBType3(){
		String desc = "Masters Abstracts International";
		batch.setVolumeIssue("51-01M(E)");
		batch.setDBTypeDesc(desc);
		batch.setDBTypeCode("MAI");
		
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "0 " + MarcCharSet.kSubFieldIndicator  + "t" 
				+ desc + MarcCharSet.kSubFieldIndicator  + "g" + "51-01(E).";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	@Test
	public void withVolumeIssueAndOtherDBTypes(){
		String desc = "All Others Dissertations";
		batch.setVolumeIssue("volumeissue");
		batch.setDBTypeDesc(desc);
		batch.setDBTypeCode("XYZ");
		
		metaData.setBatch(batch);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "0 " + MarcCharSet.kSubFieldIndicator  + "t" 
				+ desc + MarcCharSet.kSubFieldIndicator  + "g" + "volumeissue.";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
	
	

	
}
