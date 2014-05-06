package com.proquest.mtg.dismetadataservice.format;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.SchoolMetaDataProvider;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class SchoolMetaDataFormatFactory {
	public static String kSchoolMetaDataXSDFileName = "ExodusSchoolMetaData.xsd";
	private final SchoolMetaDataProvider schoolMetadaProvider;
	
	@Inject
	public SchoolMetaDataFormatFactory(SchoolMetaDataProvider schoolMetadaProvider) {
		this.schoolMetadaProvider = schoolMetadaProvider;
		
	}

	public SchoolMetaDataProvider getSchoolMetadaProvider() {
		return schoolMetadaProvider;
	}
	
	
	public SchoolMetaDataFormat create() throws IOException, SAXException {
		
		 ClassLoader classLoader = Thread.currentThread()
                 .getContextClassLoader();
		 InputStream in = classLoader.getResourceAsStream(kSchoolMetaDataXSDFileName);
		 if (null == in) {
			 throw new IOException("Failed to getSystemResourceAsStream = "
                         + kSchoolMetaDataXSDFileName);
		 }
 
		return new SchoolMetaDataFormat(getSchoolMetadaProvider(),
				new XmlSchemaValidator<Schools>(in));
	}

	public void updateDsahboardLoadStatus(String schoolCode) throws Exception {
		getSchoolMetadaProvider().updateDashboardLoadStatus(schoolCode);
		
	}
	

}
