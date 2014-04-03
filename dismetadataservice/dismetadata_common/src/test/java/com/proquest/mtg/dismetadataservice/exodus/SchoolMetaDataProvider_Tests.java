package com.proquest.mtg.dismetadataservice.exodus;

import static com.proquest.mtg.dismetadataservice.metadata.SchoolMetadataMatcher.schoolEqualTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.metadata.school.School;

public class SchoolMetaDataProvider_Tests {
	static SchoolMetaDataProvider target;
	static final int kSchoolBatchSize = 5;
	
	@BeforeClass
	public static void setUp() throws Exception {		
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		target = new SchoolMetaDataProvider(connectionPool,kSchoolBatchSize);
	}

	
	@Test
	public void missingSchoolCode_ReturnsNull() throws Exception {
		School school;
		school = target.getSchoolMetaDataFor(MakeExodusSchoolMetadataForTesting.fakeSchool);
		assertThat( school, nullValue());
	}
	
	@Test
	public void schoolAddresses_test() throws Exception {
		School school;
		school = target.getSchoolMetaDataFor(MakeExodusSchoolMetadataForTesting.school1);
		assertThat(school, schoolEqualTo(MakeExodusSchoolMetadataForTesting.makeExpectedMetaData1()));
	}
	
	@Test
	public void schoolPersonType_test() throws Exception {
		School school;
		school = target.getSchoolMetaDataFor(MakeExodusSchoolMetadataForTesting.school2);
		assertThat(school, schoolEqualTo(MakeExodusSchoolMetadataForTesting.makeExpectedMetaData2()));
	}
	
	@Test
	public void hasCorrect_SchoolBatchSize() throws Exception {
		assertThat(target.getSchoolBatchSize(), is(kSchoolBatchSize));
	}
	
/*	@Test
	public void getAllSchoolMetadata_withBatchSize() throws Exception {
		assertThat(target.getAllSchoolMetaData(), notNullValue());
	}*/
	
}
