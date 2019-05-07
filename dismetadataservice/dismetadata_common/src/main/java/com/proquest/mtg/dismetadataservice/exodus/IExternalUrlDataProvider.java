package com.proquest.mtg.dismetadataservice.exodus;


import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;

public interface IExternalUrlDataProvider {
	DissertationList geDataFor(String lastRunDate) throws Exception;
	String updateUrlStatus(String pubid, String status) throws Exception;
}
