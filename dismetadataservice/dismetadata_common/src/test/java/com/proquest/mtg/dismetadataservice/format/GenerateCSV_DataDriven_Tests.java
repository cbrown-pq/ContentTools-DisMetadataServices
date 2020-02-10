package com.proquest.mtg.dismetadataservice.format;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.proquest.mtg.dismetadataservice.datasource.ECMSDataProvider;
import com.proquest.mtg.dismetadataservice.datasource.ICSVProvider;
import com.proquest.mtg.dismetadataservice.datasource.MakeECMSMetadataForTesting;
import com.proquest.mtg.dismetadataservice.datasource.PubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.guice.DisMetadataServiceGuiceModule;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConnectionPool;
//import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.metadata.HTMLTagRemover;
import com.proquest.mtg.dismetadataservice.metadata.PlainTextNormalizer;

public class GenerateCSV_DataDriven_Tests {
	public static final String kTestDataDirPath = "src//test//resources//Csv_Regression_Test_Data//";
	ICSVProvider csvProvider;
	List<PubAndCSVRecord> expectedCsvRecords;

	public static class PubAndCSVRecord {

		private final String pubId;
		private final String csvRecord;

		public PubAndCSVRecord(String pubId, String csvRecord) {
			this.pubId = pubId;
			this.csvRecord = csvRecord;
		}

		public String getPubId() {
			return pubId;
		}

		public String getCSVRecord() {
			return csvRecord;
		}

		public static PubAndCSVRecord makeFor(ICSVProvider csvProvider,
				String pubId, String mr3Data) throws Exception {
			String csvRecord = csvProvider.getCSVResultFor(pubId,mr3Data,0,0,0);
			return new PubAndCSVRecord(pubId, csvRecord);
		}

	}

	@Before
	public void setUp() throws Exception {
		initCSVProvider();
		initExpectedCSVRecords();
	}

	@Test
	@Ignore
	public void dataDrivenTest() throws Exception {
		// Temporary String
		String foo = "JSON STUB";
		for (PubAndCSVRecord expectedCsv : expectedCsvRecords) {
			PubAndCSVRecord actualCsv = PubAndCSVRecord.makeFor(csvProvider,
					expectedCsv.getPubId(), foo);
			assertEquals(actualCsv.getPubId(), expectedCsv.getPubId());
			assertEquals("Pub ID: " + actualCsv.getPubId(), actualCsv.getCSVRecord(), expectedCsv.getCSVRecord());
		}
	}

	private void initCSVProvider() throws Exception {
		//JdbcConnectionPool connectionPool = JdbcHelper
		//		.makePoolForExodusUnitTest();
		PubMetaDataProvider pubMetaDataProvider = new PubMetaDataProvider(
				MakeECMSMetadataForTesting.pqOpenUrlBase);
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider();
		PlainTextNormalizer plainTextNormalizer = new PlainTextNormalizer(
				new HTMLTagRemover());
		Injector injector = Guice.createInjector(
				new DisMetadataServiceGuiceModule("dismetadata.local.properties"));
		PDFVaultAvailableStatusProvider pdfVaultAvailableStatusProvider = 
				injector.getInstance(PDFVaultAvailableStatusProvider.class);
		csvProvider = new ECMSDataProvider(pubMetaDataProvider,
				disGenMappingProvider, plainTextNormalizer, pdfVaultAvailableStatusProvider);
	}

	private void initExpectedCSVRecords() throws Exception {
		expectedCsvRecords = Lists.newArrayList();
		for (File csvFile : listCsvFilesIn(kTestDataDirPath)) {
			String pubName = FilenameUtils.getBaseName(csvFile.getName());
			String csvRecord = readFile(csvFile);
			expectedCsvRecords.add(new PubAndCSVRecord(pubName, csvRecord));
		}
	}

	private static List<File> listCsvFilesIn(String dirname) {
		File testDataDir = new File(dirname);
		return Lists.newArrayList(testDataDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.toLowerCase().endsWith(".csv");
			}
		}));
	}

	private String readFile(File csvFile) throws Exception {
		String csvString = FileUtils.readFileToString(csvFile);
		return csvString;
	}
}
