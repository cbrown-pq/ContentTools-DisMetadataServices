package com.proquest.mtg.dismetadataservice.format;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.ICSVProvider;

public class CSVFormat implements IMetaDataFormats {
	private final ICSVProvider csvDataProvider;

	@Inject
	public CSVFormat(ICSVProvider csvDataProvider) {
		this.csvDataProvider = csvDataProvider;
	}

	public ICSVProvider getCsvDataProvider() {
		return csvDataProvider;
	}

	@Override
	public String makeFor(String pubNum) throws Exception {
		return getCsvDataProvider().getCSVResultFor(pubNum);
	}

}
