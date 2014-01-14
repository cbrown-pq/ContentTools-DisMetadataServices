package com.proquest.mtg.dismetadataservice.format;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;

public class USMarcFormat implements IMetaDataFormats {
	
	private final IMarcProvider marcDataProvider;
	
	@Inject
	public USMarcFormat(IMarcProvider marcDataProvider) {
		this.marcDataProvider = marcDataProvider;
	}

	public IMarcProvider getMarcDataProvider() {
		return marcDataProvider;
	}

	@Override
	public String makeFor(String pubNum) throws Exception {
		return getMarcDataProvider().getMarcResultFor(pubNum).toMarcString();
	}

}
