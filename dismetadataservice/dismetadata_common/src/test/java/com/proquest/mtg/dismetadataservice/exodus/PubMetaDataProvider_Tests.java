package com.proquest.mtg.dismetadataservice.exodus;

import static com.proquest.mtg.dismetadataservice.metadata.DissertationMatcher.dissertationEqualTo;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;


public class PubMetaDataProvider_Tests {
	
	static PubMetaDataProvider target;

	@BeforeClass
	public static void setUp() throws Exception {		
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		target = new PubMetaDataProvider(connectionPool);
	}
	
	@Test
	public void pubWith_NoVolumeIssue_ReturnsNull() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.noVolumeIssuePub1);
		assertThat( metaDataResult, nullValue());
	}
	
	@Test
	public void pubWith_MissingPubID_ReturnsNull() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.fakePub1);
		assertThat(metaDataResult, nullValue());
	}
	
	
	@Test
	public void multipleSubjects_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId0);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData0()));
	}
	
	@Test
	public void nullLocationCopy_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId1);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData1()));
	}
	
	@Test
	public void nullAbstract_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId2);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData2()));
	}
	
	@Test
	public void britishPub_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId3);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData3()));		
	}
	
	@Test
	public void chinesePub_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId4);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData4()));
	}
	
	@Test
	public void electronicTitle_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId5);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData5()));
	}
	
	@Test
	public void supplementalFile_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId6);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData6()));
	}
	
	@Test
	public void normalPub_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId7);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData7()));
	}
	
	@Test
	public void multipleAuthors_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId8);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData8()));
	}
	
	@Test
	public void candianPub_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId9);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData9()));
	}
	
	@Test
	public void pubMultipleLanguages_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId10);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData10()));
	}
	
	@Test
	public void pubMultipleDepartment_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId11);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData11()));
	}
	
	@Test
	public void pubWithFoerignTitle_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId12);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData12()));
	}
	
	@Test
	public void pubWithMultipleFormatRestriction_test() throws Exception {
		DisPubMetaData metaDataResult;
		metaDataResult = target.getPubMetaDataFor(MakeExodusMetadataForTesting.pubId13);
		assertThat(metaDataResult, dissertationEqualTo(MakeExodusMetadataForTesting.makeExpectedMetaData13()));
	}
	
}
