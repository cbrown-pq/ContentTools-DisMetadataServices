package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList.Dissertation;

public interface IExternalUrlDataProvider {
	DissertationList geDataFor(String lastRunDate) throws Exception;
	String updateUrlStatus(String pubid, String status) throws Exception;
}
