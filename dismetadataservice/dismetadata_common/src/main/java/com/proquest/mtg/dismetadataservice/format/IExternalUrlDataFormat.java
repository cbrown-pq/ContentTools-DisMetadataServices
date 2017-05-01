package com.proquest.mtg.dismetadataservice.format;

import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;

public interface IExternalUrlDataFormat {
		
	DissertationList getExtUrlData(String lastRunDate) throws Exception;
	String updateExtUrlStatus(String pubid, String status) throws Exception;

}
