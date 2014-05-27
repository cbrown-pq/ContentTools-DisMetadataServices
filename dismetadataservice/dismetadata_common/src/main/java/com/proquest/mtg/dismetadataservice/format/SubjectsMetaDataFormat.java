package com.proquest.mtg.dismetadataservice.format;


import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.SubjectsMetaDataProvider;
import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects;
import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class SubjectsMetaDataFormat implements ISubjectsMetaDataFormats {
	
	private final SubjectsMetaDataProvider subjectsMetaDataProvider;
	private final XmlSchemaValidator<Subjects> xmlSchemaValidator;

	public SubjectsMetaDataFormat(SubjectsMetaDataProvider subjectsMetaDataProvider,
			XmlSchemaValidator<Subjects> xmlSchemaValidator) {
		this.subjectsMetaDataProvider = subjectsMetaDataProvider;
		this.xmlSchemaValidator = xmlSchemaValidator;
		
	}

	public SubjectsMetaDataProvider getSubjectsMetaDataProvider() {
		return subjectsMetaDataProvider;
	}
	
	public XmlSchemaValidator<Subjects> getXmlSchemaValidator() {
		return xmlSchemaValidator;
	}
	

	@Override
	public Subjects makeForAllSubjects() throws Exception {
		List<Subject> allSubjects = Lists.newArrayList();
		Subjects result = new Subjects();
		allSubjects = getSubjectsMetaDataProvider().getAllSubjects();
		result.getSubject().addAll(allSubjects);
		getXmlSchemaValidator().validateXml(result);
		return result;
	}

}
