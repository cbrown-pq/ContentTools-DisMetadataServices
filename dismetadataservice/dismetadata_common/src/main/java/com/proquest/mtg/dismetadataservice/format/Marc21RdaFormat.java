package com.proquest.mtg.dismetadataservice.format;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;

public class Marc21RdaFormat implements IMetaDataFormats {
	
	private final IMarcProvider marc21RdaDataProvider;
	
	@Inject
	public Marc21RdaFormat(IMarcProvider marc21RdaDataProvider) {
		this.marc21RdaDataProvider = marc21RdaDataProvider;
	}

	public IMarcProvider getMarc21RdaDataProvider() {
		return marc21RdaDataProvider;
	}

	@Override
	public String makeFor(String pubNum, String mr3Data, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception {
		return getMarc21RdaDataProvider().getMarc21RDAResultFor(pubNum, mr3Data, excludeRestriction, excludeAbstract, excludeAltAbstract).toMarcString();
	}

}
