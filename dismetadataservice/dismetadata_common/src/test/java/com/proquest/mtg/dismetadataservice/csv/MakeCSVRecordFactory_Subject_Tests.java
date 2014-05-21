package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;

public class MakeCSVRecordFactory_Subject_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	List<Subject> subjects;
	Subject subject;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		subjects = new ArrayList<Subject>();
		subject = new Subject();
	}

	@Test
	public void makeWithEmptySubjects() throws Exception {
		metadata.setSubjects(subjects);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptySubjDesc() throws Exception {
		String subjCode = "SubjCode";
		String subjGroupDesc = "SubjGroupDesc";
		subject.setSubjectCode(subjCode);
		subject.setSubjectGroupDesc(subjGroupDesc);
		subjects.add(subject);
		metadata.setSubjects(subjects);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ "\"" + subjCode + "\"" + ",\"" + subjGroupDesc + "\""
				+ ",\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptySubjCode() throws Exception {
		String subjDesc = "SubjDesc";
		String subjGroupDesc = "SubjGroupDesc";
		subject.setSubjectDesc(subjDesc);
		subject.setSubjectGroupDesc(subjGroupDesc);
		subjects.add(subject);
		metadata.setSubjects(subjects);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,"
				+ "\"" + subjDesc + "\"" + ",,\"" + subjGroupDesc + "\""
				+ ",\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptySubjGroupDesc() throws Exception {
		String subjCode = "SubjCode";
		String subjDesc = "SubjDesc";
		subject.setSubjectCode(subjCode);
		subject.setSubjectDesc(subjDesc);
		subjects.add(subject);
		metadata.setSubjects(subjects);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,"
				+ "\"" + subjDesc + "\"" + ",\"" + subjCode + "\""
				+ ",,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withSubjects() throws Exception {
		String subjDesc = "Language, Literature and Linguistics";
		String subjCode = "0297";
		String subjGroupDesc = "Literature, Medieval";
		subject.setSubjectDesc(subjDesc);
		subject.setSubjectCode(subjCode);
		subject.setSubjectGroupDesc(subjGroupDesc);
		subjects.add(subject);
		metadata.setSubjects(subjects);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,"
				+ "\"" + subjDesc + "\"" + ",\"" + subjCode + "\"" + ",\""
				+ subjGroupDesc + "\"" + ",\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}
