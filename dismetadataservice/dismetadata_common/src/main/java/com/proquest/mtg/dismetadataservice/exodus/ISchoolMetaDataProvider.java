package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School;

public interface ISchoolMetaDataProvider {
	
	List<String> getAllSchoolCodes() throws Exception;

	School getSchoolMetaDataFor(String schoolCode) throws Exception;
	
	List<School> getAllSchoolMetaData() throws Exception;

	List<School> getAllSchoolsMetaDataFor(List<String> schoolCodes) 
			throws Exception;
	
	void updateDashboardLoadStatus(String schoolCodes) throws Exception;

}
