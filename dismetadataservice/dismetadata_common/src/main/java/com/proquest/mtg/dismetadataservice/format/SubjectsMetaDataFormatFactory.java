/*package com.proquest.mtg.dismetadataservice.format;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.SubjectsMetaDataProvider;
import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class SubjectsMetaDataFormatFactory {

	public static String kSubjectMetaDataXSDFileName = "Subjects.xsd";
	private final SubjectsMetaDataProvider subjectsMetaDataProvider;
	
	@Inject
	public SubjectsMetaDataFormatFactory(SubjectsMetaDataProvider subjectsMetaDataProvider) {
		this.subjectsMetaDataProvider = subjectsMetaDataProvider;
		
	}

	public SubjectsMetaDataProvider getSubjectsMetadaProvider() {
		return subjectsMetaDataProvider;
	}
	
	
	public SubjectsMetaDataFormat create() throws IOException, SAXException {
		
		 ClassLoader classLoader = Thread.currentThread()
                 .getContextClassLoader();
		 InputStream in = classLoader.getResourceAsStream(kSubjectMetaDataXSDFileName);
		 if (null == in) {
			 throw new IOException("Failed to getSystemResourceAsStream = "
                         + kSubjectMetaDataXSDFileName);
		 }
 
		return new SubjectsMetaDataFormat(getSubjectsMetadaProvider(),
				new XmlSchemaValidator<Subjects>(in));
	}

}*/
