package com.proquest.mtg.dismetadataservice.csv;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_SupplementalFiles_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	SuppFile suppFile;
	List<SuppFile> suppFiles;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();

		suppFile = new SuppFile();
		suppFiles = new ArrayList<SuppFile>();
	}

	@Test
	public void makeWithEmptySupplemenatalFiles() throws Exception {
		metadata = new DisPubMetaData();
		metadata.setSuppFiles(suppFiles);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kHasSupplementalFiles, "N");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileNames, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileDescription, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileCategory, null);
	}

	@Test
	public void makeWithEmptySupFileName() throws Exception {
		String suppFileDesc = "SuppFileDesc";
		String suppFileCategory = "SuppFileCategory";
		suppFile.setSuppFileDesc(suppFileDesc);
		suppFile.setSuppFileCategory(suppFileCategory);
		suppFiles.add(suppFile);
		metadata.setSuppFiles(suppFiles);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kHasSupplementalFiles, "Y");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileNames, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileDescription, suppFileDesc);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileCategory, suppFileCategory);
	}

	@Test
	public void makeWithEmptySupFileDesc() throws Exception {
		String suppFileName = "SuppFileName";
		String suppFileCategory = "SuppFileCategory";
		suppFile.setSuppFilename(suppFileName);
		suppFile.setSuppFileCategory(suppFileCategory);
		suppFiles.add(suppFile);
		metadata.setSuppFiles(suppFiles);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kHasSupplementalFiles, "Y");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileNames, suppFileName);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileDescription, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileCategory, suppFileCategory);
	}

	@Test
	public void makeWithEmptySupFileCategory() throws Exception {
		String suppFileName = "SuppFileName";
		String suppFileDesc = "SuppFileDesc";
		suppFile.setSuppFileDesc(suppFileDesc);
		suppFile.setSuppFilename(suppFileName);
		suppFiles.add(suppFile);
		metadata.setSuppFiles(suppFiles);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kHasSupplementalFiles, "Y");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileNames, suppFileName);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileDescription, suppFileDesc);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileCategory, null);
	}

	@Test
	public void withSupplementalFiles() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		String suppFileName = "Yodel2ArchitecturalAnalysis.pdf";
		String suppFileDesc = "Architecture Analysis";
		String suppFileCategory = "pdf";
		suppFile.setSuppFilename(suppFileName);
		suppFile.setSuppFileDesc(suppFileDesc);
		suppFile.setSuppFileCategory(suppFileCategory);
		List<SuppFile> suppFiles = new ArrayList<SuppFile>();
		suppFiles.add(suppFile);
		metadata.setSuppFiles(suppFiles);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kHasSupplementalFiles, "Y");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileNames, suppFileName);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileDescription, suppFileDesc);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSuppFileCategory, suppFileCategory);
	}
}
