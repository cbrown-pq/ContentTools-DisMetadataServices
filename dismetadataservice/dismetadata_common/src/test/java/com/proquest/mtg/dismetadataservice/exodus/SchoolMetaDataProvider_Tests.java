/*package com.proquest.mtg.dismetadataservice.exodus;

import static com.proquest.mtg.dismetadataservice.metadata.SchoolMetadataMatcher.schoolEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School;


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
	public void schoolVerifyAdressAndPersonCounts() throws Exception {
		School school;
		school = target.getSchoolMetaDataFor(MakeExodusSchoolMetadataForTesting.school2);
		assertThat(school.getAddresses().getAddress().size(),is(106));
		assertThat(school.getSchoolPersons().getSchoolPerson().size(),is(3));
	}
	
	@Test
	public void schoolPersonType_test() throws Exception {
		School school;
		school = target.getSchoolMetaDataFor(MakeExodusSchoolMetadataForTesting.school3);
		assertThat(school, schoolEqualTo(MakeExodusSchoolMetadataForTesting.makeExpectedMetaData3()));
	}
	
	@Test
	public void hasCorrect_SchoolBatchSize() throws Exception {
		assertThat(target.getSchoolBatchSize(), is(kSchoolBatchSize));
	}
	
	@Test
	public void getAllSchoolMetadataFor_withBatchSize() throws Exception {
		List<String> schoolCodes = new ArrayList<String>(Arrays.asList("0126","0502","0127"));
		List<School> schools = target.getAllSchoolsMetaDataFor(schoolCodes);
		assertThat(schools.size(), is(3));
		School school1 = schools.get(0);
		assertThat(school1, schoolEqualTo(MakeExodusSchoolMetadataForTesting.makeExpectedMetaData1()));
		School school2 = schools.get(1);
		assertThat(school2, schoolEqualTo(MakeExodusSchoolMetadataForTesting.makeExpectedMetaData2()));
		School school3 = schools.get(2);
		assertThat(school3, schoolEqualTo(MakeExodusSchoolMetadataForTesting.makeExpectedMetaData3()));
		}
}
*/