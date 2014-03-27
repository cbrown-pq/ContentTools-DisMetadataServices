package com.proquest.mtg.dismetadataservice.helper;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School;

public class MakeSchoolMetadataHelper {
	public static Schools MakeFakeSchoolMetadata() {
		School fakeSchool1 = new School();
		fakeSchool1.setCode("1234");
		fakeSchool1.setName("Fake University 1");
		fakeSchool1.setState("MI");
		fakeSchool1.setCountry("US");
		
		School fakeSchool2 = new School();
		fakeSchool2.setCode("5678");
		fakeSchool2.setName("Fake University 2");
		fakeSchool2.setState("MI");
		fakeSchool2.setCountry("US");
		
		Schools schools = new Schools();
		schools.getSchool().addAll(Lists.newArrayList(fakeSchool1, fakeSchool2));
		return schools;
	}
}
