package com.proquest.mtg.dismetadataservice.exodus;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcRecordFactory;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class ExodusDataProvider implements IMarcProvider {
	
	private final IPubMetaDataProvider pubMetaDataProvider;
	private final DisGenMappingProvider disGenMappingProvider;

	@Inject
	public ExodusDataProvider(IPubMetaDataProvider pubMetaDataProvider, DisGenMappingProvider disGenMappingProvider) {
		this.pubMetaDataProvider = pubMetaDataProvider;
		this.disGenMappingProvider = disGenMappingProvider;
	}

	public IPubMetaDataProvider getPubMetaDataProvider() {
		return pubMetaDataProvider;
	}

	public DisGenMappingProvider getDisGenMappingProvider() {
		return disGenMappingProvider;
	}

	@Override
	public MarcRecord getMarcResultFor(String pubNum) throws Exception {
		MarcRecordFactory marcFactory = new MarcRecordFactory(getDisGenMappingProvider());
		MarcRecord marcRecord = marcFactory.makeFrom(
				getPubMetaDataProvider().getPubMetaDataFor(pubNum));
		return marcRecord;
	}
	

}
