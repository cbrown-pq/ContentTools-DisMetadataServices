package com.proquest.mtg.dismetadataservice.marc21rda;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.PdfAvailableDateStatus;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.SuppFile;

public class Marc21RdaRecordFactory_MediaType_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	String tag = MarcTags.kMediaType;
	String expectedMarcFieldData;
	DisPubMetaData metaData;
	PdfAvailableDateStatus pdfStatus;
	List<SuppFile> suppFileList;

	@Before
	public void setup() {
		metaData = new DisPubMetaData();
		suppFileList = new ArrayList<SuppFile>();
	}
	
	@Test
	public void withSuppFilesNotSet() {
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "acomputer"
				+ MarcCharSet.kSubFieldIndicator + "bc"
				+ MarcCharSet.kSubFieldIndicator + "2rdamedia";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}

	@Test
	public void withNoSuppFiles() {
		suppFileList = new ArrayList<SuppFile>();
		metaData.setSuppFiles(suppFileList);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "acomputer"
				+ MarcCharSet.kSubFieldIndicator + "bc"
				+ MarcCharSet.kSubFieldIndicator + "2rdamedia";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}

	@Test
	public void withSuppFiles_NullValues() {
		suppFileList = new ArrayList<SuppFile>();
		SuppFile file1 = new SuppFile();
		file1.setSuppFileCategory(null);
		file1.setSuppFileDesc(null);
		file1.setSuppFilename(null);
		SuppFile file2 = new SuppFile();
		file2.setSuppFileCategory(null);
		file2.setSuppFileDesc(null);
		file2.setSuppFilename(null);
		suppFileList.add(file1);
		suppFileList.add(file2);
		metaData.setSuppFiles(suppFileList);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "acomputer"
				+ MarcCharSet.kSubFieldIndicator + "bc"
				+ MarcCharSet.kSubFieldIndicator + "2rdamedia";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}
	
	@Test
	public void withSuppFiles_EmptyValues() {
		suppFileList = new ArrayList<SuppFile>();
		SuppFile file1 = new SuppFile();
		file1.setSuppFileCategory("");
		file1.setSuppFileDesc("");
		file1.setSuppFilename("");
		SuppFile file2 = new SuppFile();
		file2.setSuppFileCategory("");
		file2.setSuppFileDesc("");
		file2.setSuppFilename("");
		suppFileList.add(file1);
		suppFileList.add(file2);
		metaData.setSuppFiles(suppFileList);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "acomputer"
				+ MarcCharSet.kSubFieldIndicator + "bc"
				+ MarcCharSet.kSubFieldIndicator + "2rdamedia";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}
	
	@Test
	public void withSuppFiles() {
		suppFileList = new ArrayList<SuppFile>();
		SuppFile file1 = new SuppFile();
		file1.setSuppFileCategory("category1");
		file1.setSuppFileDesc("description1");
		file1.setSuppFilename("filename1");
		SuppFile file2 = new SuppFile();
		file2.setSuppFileCategory("category2");
		file2.setSuppFileDesc("description2");
		file2.setSuppFilename("filename2");
		suppFileList.add(file1);
		suppFileList.add(file2);
		metaData.setSuppFiles(suppFileList);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "acomputer"
				+ MarcCharSet.kSubFieldIndicator + "bc"
				+ MarcCharSet.kSubFieldIndicator + "2rdamedia";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}
	

}
