package com.proquest.mtg.dismetadataservice.exodus;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcRecordFactory;

public class ExodusDataProvider implements IMarcProvider {
	
	private final IPubMetaDataProvider pubMetaDataProvider;

	@Inject
	public ExodusDataProvider(IPubMetaDataProvider pubMetaDataProvider) {
		this.pubMetaDataProvider = pubMetaDataProvider;
	}

	public IPubMetaDataProvider getPubMetaDataProvider() {
		return pubMetaDataProvider;
	}

	@Override
	public String getMarcResultFor(String pubNum) throws Exception {
		MarcRecordFactory marcFactory = new MarcRecordFactory();
		MarcRecord marcRecord = marcFactory.makeFrom(
				getPubMetaDataProvider().getPubMetaDataFor(pubNum));
		return marcRecord.toMarcString();
	}
	

}
