package com.proquest.mtg.dismetadataservice.exodus;

import static com.proquest.mtg.dismetadataservice.metadata.SchoolMetadataMatcher.schoolEqualTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.metadata.school.School;

public class SchoolMetaDataProvider_Tests {
	static SchoolMetaDataProvider target;

	@BeforeClass
	public static void setUp() throws Exception {		
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		target = new SchoolMetaDataProvider(connectionPool);
	}

	
	@Test
	public void missingSchoolCode_ReturnsNull() throws Exception {
		School metaDataResult;
		metaDataResult = target.getSchoolMetaDataFor(MakeExodusSchoolMetadataForTesting.fakeSchool);
		assertThat( metaDataResult, nullValue());
	}
	
	@Test
	public void schoolAddresses_test() throws Exception {
		School metaDataResult;
		metaDataResult = target.getSchoolMetaDataFor(MakeExodusSchoolMetadataForTesting.school1);
		assertThat(metaDataResult, schoolEqualTo(MakeExodusSchoolMetadataForTesting.makeExpectedMetaData1()));
	}
	
	/*@Test
	public void schoolPersonType_test() throws Exception {
		School metaDataResult;
		metaDataResult = target.getSchoolMetaDataFor(MakeExodusSchoolMetadataForTesting.school2);
		assertThat(metaDataResult, schoolEqualTo(MakeExodusSchoolMetadataForTesting.makeExpectedMetaData2()));
	}
	
	@Test
	public void schoolNameType_test() throws Exception {
		DisSchoolMetaData metaDataResult;
		metaDataResult = target.getSchoolMetaDataFor(MakeExodusSchoolMetadataForTesting.school2);
		assertThat(metaDataResult, schoolEqualTo(MakeExodusSchoolMetadataForTesting.makeExpectedMetaData3()));
	}*/
	
}
