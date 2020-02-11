package com.proquest.mtg.dismetadataservice.csv;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.ManuscriptMedia;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_ManuscriptMedia_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	ManuscriptMedia manuscriptMedia;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	
	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();
		manuscriptMedia = new ManuscriptMedia();
	}

	@Test
	public void makeWithManuscriptMediaCodeNull() throws Exception {
		String manuscriptMediaCode = null;
		manuscriptMedia.setManuscriptMediaCode(manuscriptMediaCode);
		metadata.setManuscriptMedia(manuscriptMedia);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kManuscriptMediaCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kManuscriptMediaDesc, null);
	}
	
	@Test
	public void makeWithManuscriptMediaDescNull() throws Exception {
		String manuscriptMediaDesc = null;
		manuscriptMedia.setManuscriptMediaDesc(manuscriptMediaDesc);
		metadata.setManuscriptMedia(manuscriptMedia);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kManuscriptMediaCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kManuscriptMediaDesc, null);
	}
	
	@Test
	public void makeWithManuscriptMediaCode() throws Exception {
		String manuscriptMediaCode = "ManuscriptMediaCode";
		manuscriptMedia.setManuscriptMediaCode(manuscriptMediaCode);
		metadata.setManuscriptMedia(manuscriptMedia);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kManuscriptMediaCode, manuscriptMediaCode);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kManuscriptMediaDesc, null);
	}
	
	@Test
	public void makeWithManuscriptMediaDesc() throws Exception {
		String manuscriptMediaDesc = "ManuscriptMediaDesc";
		manuscriptMedia.setManuscriptMediaDesc(manuscriptMediaDesc);
		metadata.setManuscriptMedia(manuscriptMedia);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kManuscriptMediaCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kManuscriptMediaDesc, manuscriptMediaDesc);
	}


}
