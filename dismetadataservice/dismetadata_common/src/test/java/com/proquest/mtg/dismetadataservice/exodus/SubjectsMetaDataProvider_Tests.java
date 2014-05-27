package com.proquest.mtg.dismetadataservice.exodus;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject;

public class SubjectsMetaDataProvider_Tests {
	
	static SubjectsMetaDataProvider target;
	
	@BeforeClass
	public static void setUp() throws Exception {		
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		target = new SubjectsMetaDataProvider(connectionPool);
	}

	@Test
	public void getAllSubjectsReturnNotNullValue() throws Exception {
		List<Subject> subjects;
		subjects = target.getAllSubjects();
		assertThat( subjects, notNullValue());
	}

}
