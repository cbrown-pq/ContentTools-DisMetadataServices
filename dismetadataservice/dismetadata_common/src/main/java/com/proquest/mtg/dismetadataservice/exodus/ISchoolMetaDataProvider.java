package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

public interface ISchoolMetaDataProvider {
	
	List<String> getAllSchoolCodes() throws Exception;

	DisSchoolMetaData getSchoolMetaDataFor(String schoolCode) throws Exception;

}
