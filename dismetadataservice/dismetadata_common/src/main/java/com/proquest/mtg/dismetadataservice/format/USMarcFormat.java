package com.proquest.mtg.dismetadataservice.format;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.datasource.IMarcProvider;

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
	public String makeFor(String pubNum, String mr3Data, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception {
		return getMarcDataProvider().getMarcResultFor(pubNum, mr3Data, excludeRestriction, excludeAbstract, excludeAltAbstract).toMarcString();
	}

}
