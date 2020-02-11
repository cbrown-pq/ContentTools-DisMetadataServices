package com.proquest.mtg.dismetadataservice.loc;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.datasource.LOCMetaDataProvider;
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
	
	public CreateNewClaimInput makeFor(String ecmsData, String mr3Data, int excludeRestriction) throws Exception {
		return getLocDataProvider().getLOCDataFor(ecmsData, mr3Data, excludeRestriction);
	}
	
//	//CBDELETE
//	public CreateNewClaimInput makeForEligiblePubs(int excludeRestriction) throws Exception {
//		return getLocDataProvider().getEligibleLOCData(excludeRestriction);
//	}

//	public void updateLOCClaimSubmissionFor(String pubNumber) throws Exception {
//		getLocDataProvider().updateLOCClaimSubmissionFor(pubNumber);
//	}
	
//	public void updateLOCDeliverySubmissionFor(String pubNumber) throws Exception {
//		getLocDataProvider().updateLOCDeliverySubmissionFor(pubNumber);
//	}

}
