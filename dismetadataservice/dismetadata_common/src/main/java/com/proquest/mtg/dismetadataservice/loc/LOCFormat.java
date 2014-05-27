package com.proquest.mtg.dismetadataservice.loc;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.LOCMetaDataProvider;
import com.proquest.mtg.dismetadataservice.pqloc.Claims;

public class LOCFormat {
	
	private final LOCMetaDataProvider locDataProvider;
	
	@Inject
	public LOCFormat(LOCMetaDataProvider locDataProvider) {
		this.locDataProvider = locDataProvider;
	}

	public LOCMetaDataProvider getLocDataProvider() {
		return locDataProvider;
	}
	
	public Claims makeFor(String pubNum) throws Exception {
		return getLocDataProvider().getLOCDataFor(pubNum);
	}
	
	public Claims makeForEligiblePubs(String lastRunDate) throws Exception {
		return getLocDataProvider().getEligibleLOCData(lastRunDate);
	}

}
