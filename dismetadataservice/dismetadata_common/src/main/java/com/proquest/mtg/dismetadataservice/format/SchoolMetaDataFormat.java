package com.proquest.mtg.dismetadataservice.format;

import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.SchoolMetaDataProvider;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class SchoolMetaDataFormat implements ISchoolMetaDataFormats {
	
	private final SchoolMetaDataProvider schoolMetaDataProvider;
	private final XmlSchemaValidator<Schools> xmlSchemaValidator;

	public SchoolMetaDataFormat(SchoolMetaDataProvider schoolMetaDataProvider,
			XmlSchemaValidator<Schools> xmlSchemaValidator) {
		this.schoolMetaDataProvider = schoolMetaDataProvider;
		this.xmlSchemaValidator = xmlSchemaValidator;
		
	}

	public SchoolMetaDataProvider getSchoolMetaDataProvider() {
		return schoolMetaDataProvider;
	}
	
	public XmlSchemaValidator<Schools> getXmlSchemaValidator() {
		return xmlSchemaValidator;
	}
	
	@Override
	public Schools makeForSchoolCode(String schoolCode) throws Exception {
		Schools.School school;
		Schools result = new Schools();
		school = getSchoolMetaDataProvider().getSchoolMetaDataFor(schoolCode);
		result.getSchool().add(school);
		getXmlSchemaValidator().validateXml(result);
		return result;
	}


	@Override
	public Schools makeForAllSchoolCode() throws Exception {
		List<School> allSchools = Lists.newArrayList();
		Schools result = new Schools();
		allSchools = getSchoolMetaDataProvider().getAllSchoolMetaData();
		result.getSchool().addAll(allSchools);
		getXmlSchemaValidator().validateXml(result);
		return result;
	}

}
