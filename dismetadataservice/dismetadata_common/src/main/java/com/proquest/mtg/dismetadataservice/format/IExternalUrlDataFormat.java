package com.proquest.mtg.dismetadataservice.format;

import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;

public interface IExternalUrlDataFormat {
	
	DissertationList makeFor() throws Exception;

}
