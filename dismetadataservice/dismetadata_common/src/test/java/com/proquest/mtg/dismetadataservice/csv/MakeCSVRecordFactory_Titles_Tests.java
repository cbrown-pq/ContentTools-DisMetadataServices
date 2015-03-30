package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_Titles_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	Title title;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus);
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		title = new Title();
	}

	@Test
	public void makeTitleWithEmpty() throws Exception {
		metadata.setTitle(title);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeTitleWithEmptyMasterTitle() throws Exception {
		title.setElectronicTitle("ElectronicTitle");
		title.setEnglishOverwriteTitle("OverwriteTitle");
		title.setForeignTitle("ForeignTitle");
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setTitle(title);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,"
				+ "\"ForeignTitle.\"" + "," + "\"ElectronicTitle.\"" + ","
				+ "\"OverwriteTitle\"" + ",,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeTitleWithEmptyElectronicTitle() throws Exception {
		title.setMasterTitle("MasterTitle");
		title.setEnglishOverwriteTitle("OverwriteTitle");
		title.setForeignTitle("ForeignTitle");
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setTitle(title);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,"
				+ "\"ForeignTitle.\"" + ",," + "\"OverwriteTitle\""
				+ ",,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeTitleWithEmptyOverwriteTitle() throws Exception {
		title.setMasterTitle("MasterTitle");
		title.setElectronicTitle("ElectronicTitle");
		title.setForeignTitle("ForeignTitle");
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setTitle(title);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,"
				+ "\"ElectronicTitle.\""
				+ ",,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeTitleWithEmptyForeignTitle() throws Exception {
		title.setMasterTitle("MasterTitle");
		title.setEnglishOverwriteTitle("OverwriteTitle");
		title.setElectronicTitle("ElectronicTitle");
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setTitle(title);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,"
				+ "\"MasterTitle.\",\"ElectronicTitle.\",\"OverwriteTitle\""
				+ ",,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withAllTitles() throws Exception {
		title.setMasterTitle("Bridging the gap to peace:  From a new way of thinking into action");
		title.setElectronicTitle("Utilization of an articulation index procedure in the evaluation of hearing-aid efficiency");
		title.setEnglishOverwriteTitle("Adsorption kinetics at the air-water interface.  \\lbrack Dutch text\\rbrack");
		title.setForeignTitle("NAD(+)-glycohydrolase in runderschildklier:  Afzonderen, eigenschappen en bereiden van monoklonale antistoffen.  (Dutch text)");
		metadata.setTitle(title);
		String expectedCSVData = header
				+ "\r\n"
				+ ",,,,,,,,,,,,,,,,,"
				+ "\"NAD(+)-glycohydrolase in runderschildklier:  Afzonderen, eigenschappen en bereiden van monoklonale antistoffen.  (Dutch text).\""
				+ ","
				+ "\"Utilization of an articulation index procedure in the evaluation of hearing-aid efficiency.\""
				+ ","
				+ "\"Adsorption kinetics at the air-water interface.  \\lbrack Dutch text\\rbrack\""
				+ ",,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	
	@Test
	public void makeTitleWithQuotesInTitle() throws Exception {
		String masterTilte = " Master title with \"quotes\"";
		title.setMasterTitle(masterTilte);
		
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setTitle(title);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,"
				+ "\"Master title with \"\"quotes\"\".\",,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}
	
	@Test
	public void makeTitleWithSGMLInTitle() throws Exception {
		String masterTilte = "Master title with &laquo; and &Lambda; and &ldquo;";
		title.setMasterTitle(masterTilte);
		
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setTitle(title);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,"
				+ "\"Master title with \"\" and Lambda and \"\".\",,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}
