package com.proquest.mtg.dismetadataservice.format;

import java.util.List;
import com.proquest.mtg.dismetadataservice.fopeligiblepubs.xml.FopEligiblePubsList;

public interface IFOPEligiblePubsDataFormat {
		
	FopEligiblePubsList getFOPEligiblePubs() throws Exception;
	String updateFFInprogressStatus(String pubid, String status) throws Exception;
}
