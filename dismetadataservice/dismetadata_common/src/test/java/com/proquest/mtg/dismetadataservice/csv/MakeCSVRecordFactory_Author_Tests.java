package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;
import com.proquest.mtg.dismetadataservice.metadata.Author;

public class MakeCSVRecordFactory_Author_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	List<Author> authors;
	Author author;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	
	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0);
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		authors = new ArrayList<Author>();
		author = new Author();
	}

	@Test
	public void makeWithEmptyAuthors() throws Exception {
		metadata.setAuthors(authors);
		String expectedCSVData = header
				+ "\r\n,,,\"N\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withEmptyAuthorName() throws Exception {
		String authorName = "";
		author.setAuthorFullName(authorName);

		authors.add(author);
		metadata.setAuthors(authors);
		String expectedCSVData = header
				+ "\r\n,,,\"N\",,\"N\",,,,,,,,,,,,,,,,\"NONE\",,,,,,,,,,,\"NONE\",,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withAuthors() throws Exception {
		Author author1 = new Author();
		author1.setAuthorFullName("Yamamoto, Masahiro");
		Author author2 = new Author();
		author2.setAuthorFullName("Fernandez-Rivera, Salvador");
		List<Author> authors = new ArrayList<Author>();
		authors.add(author1);
		authors.add(author2);
		metadata.setAuthors(authors);
		String expectedCSVData = header + "\r\n,,\"Yamamoto, Masahiro|Fernandez-Rivera, Salvador\",\"N\",,\"N\",,,,,,,,,,,,,,,,\"NONE|NONE\",,,,,,,,,,,\"NONE|NONE\",,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}
