package com.proquest.mtg.dismetadataservice.format;

import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools;

public interface ISchoolMetaDataFormats {
	
	public Schools makeForSchoolCode(String schoolCode) throws Exception;
	public Schools makeForAllSchoolCode() throws Exception;

}
