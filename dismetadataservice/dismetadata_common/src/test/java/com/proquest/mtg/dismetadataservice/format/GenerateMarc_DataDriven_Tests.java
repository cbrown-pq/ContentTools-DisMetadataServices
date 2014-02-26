package com.proquest.mtg.dismetadataservice.format;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.ExodusDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;
import com.proquest.mtg.dismetadataservice.exodus.MakeExodusMetadataForTesting;
import com.proquest.mtg.dismetadataservice.exodus.PubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcParser;
import com.proquest.mtg.dismetadataservice.marc.MarcParserException;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.metadata.HTMLTagRemover;
import com.proquest.mtg.dismetadataservice.metadata.PlainTextNormalizer;

public class GenerateMarc_DataDriven_Tests {
	public static final String kTestDataDirPath = "src//test//resources//Marc_Regression_Test_Data//";

	public static class PubAndMarcRecord {
				
		public static final char kFieldSeparatorChar = (char)0x1e;
		public static final String kFieldSeparator = Character.toString(kFieldSeparatorChar);
				
		private final String pubId;
		private final MarcRecord marcRecord;
		
		public PubAndMarcRecord(String pubId, MarcRecord marcRecord) {
			this.pubId = pubId;
			this.marcRecord = marcRecord;
			marcRecord.removeFieldsWithTag(MarcTags.kTransactionTimestamp);
			marcRecord.removeFieldsWithTag(MarcTags.kFiexedLengthDataElements); // This has some TimeStamp stuff too.
		}
		
		public PubAndMarcRecord(String pubName, String marcData) throws MarcParserException, UnsupportedEncodingException {
			this(pubName, MarcRecord.makeFrom(marcData));
		}
		
		public String getPubId() {
			return pubId;
		}
		
		public MarcRecord getMarcRecord() {
			return marcRecord;
		}
		
		public static PubAndMarcRecord makeFor(IMarcProvider marcProvider, String pubId) throws Exception {
			MarcRecord marcRecord = marcProvider.getMarcResultFor(pubId);
			return new PubAndMarcRecord(pubId, marcRecord);
		}
	}
	
	List<PubAndMarcRecord> expectedMarcRecords;
	IMarcProvider marcProvider;
	
	@Before
	public void setUp() throws Exception {
		initMarcProvider();
		initExpectedMarcRecords();
	}

	@Test
	public void dataDrivenTest() throws Exception {
		for (PubAndMarcRecord expectedMarc : expectedMarcRecords) {
			PubAndMarcRecord actualMarc = PubAndMarcRecord.makeFor(marcProvider, expectedMarc.getPubId());
			String message = "Pub: " + expectedMarc.getPubId();
			verifyMarc(message, actualMarc, expectedMarc);
		}
	}
	
	private void initMarcProvider() throws Exception {
		JdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		PubMetaDataProvider pubMetaDataProvider = new PubMetaDataProvider(connectionPool, 
				MakeExodusMetadataForTesting.pqOpenUrlBase);
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider(connectionPool);
		PlainTextNormalizer plainTextNormalizer = new PlainTextNormalizer(new HTMLTagRemover());
		marcProvider = new ExodusDataProvider(pubMetaDataProvider, disGenMappingProvider, plainTextNormalizer);
	}
	
	
	
	private void initExpectedMarcRecords() throws IOException,
			MarcParserException {
		expectedMarcRecords = Lists.newArrayList();
		MarcParser parser = new MarcParser();
		for (File marcFile : listMarcFilesIn(kTestDataDirPath))
		{
			String pubName = FilenameUtils.getBaseName(marcFile.getName());
			MarcRecord marcRecord = parser.read(marcFile);
			expectedMarcRecords.add(new PubAndMarcRecord(pubName, marcRecord)); 
			
		}
	}
	
	public static List<File> listMarcFilesIn(String dirname) {
		File testDataDir = new File(dirname);
		return Lists.newArrayList(testDataDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.toLowerCase().endsWith(".mrc");
			}
		}));
	}
	
	private void verifyMarc(
		String message, 
		PubAndMarcRecord actualMarc, 
		PubAndMarcRecord expectedMarc) { 
		verifyMarcRecordTags(message, actualMarc, expectedMarc);
		verifyMarcFields(message, actualMarc, expectedMarc);
	}

	private void verifyMarcRecordTags(
		String message, 
		PubAndMarcRecord expectedMarc, 
		PubAndMarcRecord actualMarc) {
		assertThat(
				message + " Verify Tags",
				actualMarc.getMarcRecord().getAllTags(),
				is(expectedMarc.getMarcRecord().getAllTags()));
	}
	
	private void verifyMarcFields(
		String message, 
		PubAndMarcRecord expectedMarc, 
		PubAndMarcRecord actualMarc) {
		int fieldCount = expectedMarc.getMarcRecord().getFieldCount();
		for (int i=0; i<fieldCount; ++i) {
			MarcField expectedField = expectedMarc.getMarcRecord().getAllFields().get(i);
			MarcField actualField = actualMarc.getMarcRecord().getAllFields().get(i);
			assertThat(
					message + " Tag: " + expectedField.getTag(), 
					actualField.getTag(), 
					is(expectedField.getTag()));
			assertThat(
					message + " Data for Tag: " + expectedField.getTag(), 
					actualField.getData(), 
					is(expectedField.getData()));
		}
	}
}
