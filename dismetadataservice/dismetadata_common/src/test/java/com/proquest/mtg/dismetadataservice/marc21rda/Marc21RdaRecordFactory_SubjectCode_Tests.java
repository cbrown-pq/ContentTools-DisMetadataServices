package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.guice.DisMetadataServiceGuiceModule;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
//import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class Marc21RdaRecordFactory_SubjectCode_Tests {
	String tag;
	Marc21RdaRecordFactory factory ;
	
	@SuppressWarnings("unused")
	@Before
	public void setUp() throws Exception {
		tag = MarcTags.kSubjectCode;
		//IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider();
		Injector injector = Guice.createInjector(
				new DisMetadataServiceGuiceModule("dismetadata.local.properties"));
		factory = new Marc21RdaRecordFactory(disGenMappingProvider);
	}
	
	/*@Test
	public void nullSubject_no_Tag() throws Exception {
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setSubjects(null);
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
	@Test
	public void emptySubjectList_no_Tag() throws Exception {
		DisPubMetaData metaData = new DisPubMetaData();
		List<Subject> subjects = Lists.newArrayList();
		metaData.setSubjects(subjects);
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
	@Test
	public void withOneSubject() throws Exception {
		
		String subjectCode = "0010";
		Subject subject = new Subject();
		subject.setSubjectCode(subjectCode);
		List<Subject> subjects = Lists.newArrayList(subject);
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setSubjects(subjects);
		
		String expectedSubject = "  " + MarcCharSet.kSubFieldIndicator + "a" + subjectCode;
		MarcRecord marc = factory.makeFrom(metaData);
		
		List<MarcField> subjectTermFields = marc.getFieldsMatchingTag(tag); 
		assertThat(subjectTermFields.size(), is(1));
		assertThat(subjectTermFields.get(0).getData(), is(expectedSubject));
	}
	
	@Test
	public void withMultipleSubjects() throws Exception {
		
		String subjectCode1 = "1234";
		String subjectCode2 = "2345";
		String subjectCode3 = "3456";
		Subject subject1 = new Subject();
		Subject subject2 = new Subject();
		Subject subject3 = new Subject();
		
		subject1.setSubjectCode(subjectCode1);
		subject2.setSubjectCode(subjectCode2);
		subject3.setSubjectCode(subjectCode3);
		
		List<Subject> subjects = Lists.newArrayList(subject1, subject2, subject3);
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setSubjects(subjects);
		
		String expectedSubject1 = "  " + MarcCharSet.kSubFieldIndicator + "a" + subjectCode1;
		String expectedSubject2 = "  " + MarcCharSet.kSubFieldIndicator + "a" + subjectCode2;
		String expectedSubject3 = "  " + MarcCharSet.kSubFieldIndicator + "a" + subjectCode3;
		
		MarcRecord marc = factory.makeFrom(metaData);
		
		List<MarcField> subjectTermFields = marc.getFieldsMatchingTag(tag); 
		assertThat(subjectTermFields.size(), is(3));
		assertThat(subjectTermFields.get(0).getData(), is(expectedSubject1));
		assertThat(subjectTermFields.get(1).getData(), is(expectedSubject2));
		assertThat(subjectTermFields.get(2).getData(), is(expectedSubject3));
	}*/
}
