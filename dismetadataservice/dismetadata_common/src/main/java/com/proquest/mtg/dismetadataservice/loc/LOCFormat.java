package com.proquest.mtg.dismetadataservice.loc;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.LOCMetaDataProvider;
import com.proquest.mtg.dismetadataservice.pqloc.CreateNewClaimInput;

public class LOCFormat {
	
	private final LOCMetaDataProvider locDataProvider;
	
	@Inject
	public LOCFormat(LOCMetaDataProvider locDataProvider) {
		this.locDataProvider = locDataProvider;
	}

	public LOCMetaDataProvider getLocDataProvider() {
		return locDataProvider;
	}
	
	public CreateNewClaimInput makeFor(String pubNum) throws Exception {
		return getLocDataProvider().getLOCDataFor(pubNum);
	}
	
	public CreateNewClaimInput makeForEligiblePubs() throws Exception {
		return getLocDataProvider().getEligibleLOCData();
	}

	public void updateLOCClaimSubmissionFor(String pubNumber) throws Exception {
		getLocDataProvider().updateLOCClaimSubmissionFor(pubNumber);
	}
	
	public void updateLOCDeliverySubmissionFor(String pubNumber) throws Exception {
		getLocDataProvider().updateLOCDeliverySubmissionFor(pubNumber);
	}

}
