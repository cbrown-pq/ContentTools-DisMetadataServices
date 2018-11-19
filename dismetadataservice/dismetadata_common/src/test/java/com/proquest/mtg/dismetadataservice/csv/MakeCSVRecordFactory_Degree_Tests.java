package com.proquest.mtg.dismetadataservice.csv;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;

public class MakeCSVRecordFactory_Degree_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	List<Author> authors;
	List<Degree> degrees;
	Author author;
	Degree degree;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	
	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();
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
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeYear, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorLocCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeDesc, null);
	}

	@Test
	public void withEmptyDegreeCode() throws Exception {
		degree.setDegreeDescription("DegreeDesc");
		degree.setDegreeYear("DegreeYear");
		degrees.add(degree);
		authors.add(author);
		metadata.setAuthors(authors);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeYear, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorLocCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeDesc, null);
	}

	@Test
	public void withEmptyDegreeDesc() throws Exception {
		degree.setDegreeCode("DegreeCode");
		degree.setDegreeYear("DegreeYear");
		degrees.add(degree);
		authors.add(author);
		metadata.setAuthors(authors);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeYear, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorLocCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeDesc, null);
		
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
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeYear, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorLocCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeDesc, null);
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
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeCode, "D.N.Sc.");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeYear, "1987");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kAuthorLocCitizenship, "NONE");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDegreeDesc, "Doctor of Nursing Science");
	}

}
