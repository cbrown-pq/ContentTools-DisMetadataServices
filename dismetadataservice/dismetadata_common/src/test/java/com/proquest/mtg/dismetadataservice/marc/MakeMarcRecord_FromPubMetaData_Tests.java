package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.usmarc.USMarcRecordFactory;

public class MakeMarcRecord_FromPubMetaData_Tests {

	static int kDataIndependentFieldCount = 4;
	IJdbcConnectionPool connectionPool;
	DisGenMappingProvider disGenMappingProvider;

	USMarcRecordFactory factory;

	@Before
	public void setUp() throws Exception {
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusProd();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider(
				connectionPool);
		factory = new USMarcRecordFactory(disGenMappingProvider);
	}

	@Test(expected = Exception.class)
	public void with_Null_Throws() throws Exception {
		factory.makeFrom(null);
	}

	@Test
	public void with_Emtpy() throws Exception {
		DisPubMetaData empty = new DisPubMetaData();
		MarcRecord marc = factory.makeFrom(empty);
		assertThat(marc.getFieldCount(), is(0 + kDataIndependentFieldCount));
	}

	public void verifyMarcRecordHasSingleField(DisPubMetaData metaData,
			String tag, String expectedMarcFieldData, int dataSpecificFieldCount) {
		MarcRecord marc = factory.makeFrom(metaData);
		assertThat(marc.getFieldCount(), is(dataSpecificFieldCount
				+ kDataIndependentFieldCount));
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(),
				is(expectedMarcFieldData));

	}

	@Test
	public void withOnly_RecordID() throws Exception {
		String tag = MarcTags.kRecId;
		String pubId = "C660938";
		String expectedMarcFieldData = "AAI" + pubId;

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);

		verifyMarcRecordHasSingleField(metaData, tag, expectedMarcFieldData, 2);
	}

	@Test
	public void withOnly_pubIdAndTimeStamp() {
		String tag = MarcTags.kTransactionTimestamp;
		String pubId = "C660938";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);
		MarcRecord marc = factory.makeFrom(metaData);
		assertThat(marc.getFieldCount(), is(2 + kDataIndependentFieldCount));
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), notNullValue());
	}

	@Test
	public void withOnly_ISBN() {
		String tag = MarcTags.kIsbn;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setISBN("test-isbn");

		MarcRecord marc = factory.makeFrom(metaData);
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator
				+ "a" + "testisbn";
		assertThat(marc.getFieldCount(), is(1 + kDataIndependentFieldCount));
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(),
				is(expectedMarcFieldData));
	}

	@Test
	public void withOnly_SystemControlNumber() {
		String tag = MarcTags.kSystemControlNumber;
		String pubId = "C660938";
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setPubNumber(pubId);

		MarcRecord marc = factory.makeFrom(metaData);
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator
				+ "a" + "(MiAaPQ)AAI" + pubId.trim();

		assertThat(marc.getFieldCount(), is(2 + kDataIndependentFieldCount));
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(),
				is(expectedMarcFieldData));
	}

	@Test
	public void withOnly_CatalogingSource() {
		String tag = MarcTags.kCatalogingSource;

		DisPubMetaData metaData = new DisPubMetaData();
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator
				+ "a" + "MiAaPQ" + MarcCharSet.kSubFieldIndicator + "c"
				+ "MiAaPQ";

		assertThat(marc.getFieldCount(), is(kDataIndependentFieldCount));
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(),
				is(expectedMarcFieldData));
	}

	@Test
	public void withOnly_LocationOfCopy() throws Exception {
		String tag = MarcTags.kLocationOfCopy;
		String locationOfCopy = "TWENTE UNIVERSITY LIBRARY, CENTRAL LIBRARY, P.O. BOX 217, NL-7500 AE     ENSCHEDE, THE NETHERLANDS";
		String expectedMarcFieldData = "2 " + MarcCharSet.kSubFieldIndicator
				+ "a" + locationOfCopy + ".";

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setReferenceLocation(locationOfCopy);

		verifyMarcRecordHasSingleField(metaData, tag, expectedMarcFieldData, 1);
	}

	@Test
	public void withOnly_Subjects() throws Exception {

		List<Subject> subjects = Lists.newArrayList(
				createSubject("0454", "Business Administration, Management"),
				createSubject("0546", "Engineering, Industrial"),
				createSubject("0796", "Operations Research"));

		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setSubjects(subjects);

		String expectedSubjectTermFieldData1 = " 4"
				+ MarcCharSet.kSubFieldIndicator + "a"
				+ "Business Administration, Management.";
		String expectedSubjectTermFieldData2 = " 4"
				+ MarcCharSet.kSubFieldIndicator + "a"
				+ "Engineering, Industrial.";
		String expectedSubjectTermFieldData3 = " 4"
				+ MarcCharSet.kSubFieldIndicator + "a" + "Operations Research.";

		String expectedSubjectCodeFieldData1 = "  "
				+ MarcCharSet.kSubFieldIndicator + "a" + "0454";
		String expectedSubjectCodeFieldData2 = "  "
				+ MarcCharSet.kSubFieldIndicator + "a" + "0546";
		String expectedSubjectCodeFieldData3 = "  "
				+ MarcCharSet.kSubFieldIndicator + "a" + "0796";

		MarcRecord marc = factory.makeFrom(metaData);
		assertThat(marc.getFieldCount(), is(6 + kDataIndependentFieldCount));

		List<MarcField> subjectTermFields = marc
				.getFieldsMatchingTag(MarcTags.kSubjectTerm);
		assertThat(subjectTermFields.size(), is(3));
		assertThat(subjectTermFields.get(0).getData(),
				is(expectedSubjectTermFieldData1));
		assertThat(subjectTermFields.get(1).getData(),
				is(expectedSubjectTermFieldData2));
		assertThat(subjectTermFields.get(2).getData(),
				is(expectedSubjectTermFieldData3));

		List<MarcField> subjectCodeFields = marc
				.getFieldsMatchingTag(MarcTags.kSubjectCode);
		assertThat(subjectCodeFields.size(), is(3));
		assertThat(subjectCodeFields.get(0).getData(),
				is(expectedSubjectCodeFieldData1));
		assertThat(subjectCodeFields.get(1).getData(),
				is(expectedSubjectCodeFieldData2));
		assertThat(subjectCodeFields.get(2).getData(),
				is(expectedSubjectCodeFieldData3));
	}

	private Subject createSubject(String code, String desc) {
		Subject subject = new Subject();
		subject.setSubjectCode(code);
		subject.setSubjectDesc(desc);
		return subject;
	}

	// @Test
	// public void acceptance() throws Exception {
	//
	// String pubId = "C660938";
	// String locationOfCopy =
	// "TWENTE UNIVERSITY LIBRARY, CENTRAL LIBRARY, P.O. BOX 217, NL-7500 AE     ENSCHEDE, THE NETHERLANDS";
	// String abstractText = "This is my research";
	// List<SubjectMetaData> subjects = Lists.newArrayList(
	// new SubjectMetaData("0454", "Business Administration, Management"),
	// new SubjectMetaData("0546", "Engineering, Industrial"),
	// new SubjectMetaData("0796", "Operations Research"));
	//
	// PubMetaData metaData = new PubMetaData();
	// metaData.setPubId(pubId);
	// metaData.setLocationOfCopy(locationOfCopy);
	// metaData.setAbstractText(abstractText);
	// metaData.setSubjects(subjects);
	//
	// MarcRecord marc = factory.makeFrom(metaData);
	//
	// assertThat(marc.getFieldCount(), is(9));
	//
	// assertThat(marc.getFieldsMatchingTag(MarcTags.kRecId).size(), is(1));
	// assertThat(marc.getFieldsMatchingTag(MarcTags.kAbstract).size(), is(1));
	// assertThat(marc.getFieldsMatchingTag(MarcTags.kLocationOfCopy).size(),
	// is(1));
	// assertThat(marc.getFieldsMatchingTag(MarcTags.kSubjectTerm).size(),
	// is(3));
	// assertThat(marc.getFieldsMatchingTag(MarcTags.kSubjectCode).size(),
	// is(3));
	// }

}
