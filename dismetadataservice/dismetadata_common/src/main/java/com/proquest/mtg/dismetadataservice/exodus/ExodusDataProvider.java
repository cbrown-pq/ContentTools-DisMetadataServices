package com.proquest.mtg.dismetadataservice.exodus;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.csv.CSVRecordFactory;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc21rda.Marc21RdaRecordFactory;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.metadata.PlainTextNormalizer;
import com.proquest.mtg.dismetadataservice.usmarc.USMarcRecordFactory;

public class ExodusDataProvider implements IMarcProvider,ICSVProvider {
	
	private final IPubMetaDataProvider pubMetaDataProvider;
	private final DisGenMappingProvider disGenMappingProvider;
	private final PlainTextNormalizer plainTextNormalizer;
	private final PDFVaultAvailableStatusProvider pdfVaultAvailableStatusProvider;

	@Inject
	public ExodusDataProvider(IPubMetaDataProvider pubMetaDataProvider, 
			DisGenMappingProvider disGenMappingProvider,
			PlainTextNormalizer plainTextNormalizer,
			PDFVaultAvailableStatusProvider pdfVaultAvailableStatusProvider) {
		this.pubMetaDataProvider = pubMetaDataProvider;
		this.disGenMappingProvider = disGenMappingProvider;
		this.plainTextNormalizer = plainTextNormalizer;
		this.pdfVaultAvailableStatusProvider = pdfVaultAvailableStatusProvider;
	}


	public IPubMetaDataProvider getPubMetaDataProvider() {
		return pubMetaDataProvider;
	}

	public DisGenMappingProvider getDisGenMappingProvider() {
		return disGenMappingProvider;
	}

	public PlainTextNormalizer getPlainTextNormalizer() {
		return plainTextNormalizer;
	}
	
	public PDFVaultAvailableStatusProvider getPDFVaultAvailableStatusProvider() {
		return pdfVaultAvailableStatusProvider;
	}
	
	@Override
	public MarcRecord getMarcResultFor(String pubNum) throws Exception {
		USMarcRecordFactory marcFactory = new USMarcRecordFactory(getDisGenMappingProvider());
		MarcRecord marcRecord = marcFactory.makeFrom(
				getPubMetaDataProvider().getPubMetaDataFor(pubNum));
		applyPlainTextNormalizerTo(marcRecord);
		return marcRecord;
	}
	
	@Override
	public MarcRecord getMarc21RDAResultFor(String pubNum) throws Exception {
		Marc21RdaRecordFactory marc21RdaFactory = new Marc21RdaRecordFactory(
				getDisGenMappingProvider());
		MarcRecord marcRecord = marc21RdaFactory.makeFrom(
				getPubMetaDataProvider().getPubMetaDataFor(pubNum));
		applyPlainTextNormalizerTo(marcRecord);
		return marcRecord;
	}

	private void applyPlainTextNormalizerTo(MarcRecord marcRecord) {
		marcRecord.apply(getPlainTextNormalizer());
	}


	@Override
	public String getCSVResultFor(String pubNum) throws Exception {
		CSVRecordFactory csvFactory = new CSVRecordFactory(getPDFVaultAvailableStatusProvider());
		String csvRecord = csvFactory.makeFrom(
				getPubMetaDataProvider().getPubMetaDataFor(pubNum));
		return csvRecord;
	}
}
