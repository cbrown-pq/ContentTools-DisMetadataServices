package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;

public class MakeCSVRecordFactory_Degree_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	List<Author> authors;
	List<Degree> degrees;
	Author author;
	Degree degree;
	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		authors = new ArrayList<Author>();
		degrees = new ArrayList<Degree>();
		author = new Author();
		degree = new Degree();
	}

	@Test
	public void makeWithEmptyDegrees() throws Exception {
		degrees.add(degree);
		author.setDegrees(degrees);
		authors.add(author);
		metadata.setAuthors(authors);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,\"NONE\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withEmptyDegreeCode() throws Exception {
		degree.setDegreeDescription("DegreeDesc");
		degree.setDegreeYear("DegreeYear");
		degrees.add(degree);
		authors.add(author);
		metadata.setAuthors(authors);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,\"NONE\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withEmptyDegreeDesc() throws Exception {
		degree.setDegreeCode("DegreeCode");
		degree.setDegreeYear("DegreeYear");
		degrees.add(degree);
		authors.add(author);
		metadata.setAuthors(authors);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,\"NONE\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withEmptyDegreeYear() throws Exception {
		Author author = new Author();
		Degree degree = new Degree();
		degree.setDegreeDescription("DegreeDesc");
		degree.setDegreeCode("DegreeCode");
		degrees.add(degree);
		authors.add(author);
		metadata.setAuthors(authors);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,\"NONE\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withDegrees() throws Exception {
		degree.setDegreeCode("D.N.Sc.");
		degree.setDegreeDescription("Doctor of Nursing Science");
		degree.setDegreeYear("1987");
		degrees.add(degree);
		author.setDegrees(degrees);
		authors.add(author);
		metadata.setAuthors(authors);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,"
				+ "\"D.N.Sc.\"" + ",\"Doctor of Nursing Science\""
				+ ",\"1987\"" + ",,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,\"NONE\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}
