package com.proquest.mtg.dismetadataservice.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class MarcXMLSchemaValidator {
	public static String kMarcXmlSchemaXSDFileName = "MARC21slim.xsd";
	
	
	private Validator validator;

	public MarcXMLSchemaValidator() throws SAXException, IOException {		
		initSchemaValidator(getSchemaFile());
	}
	
	public MarcXMLSchemaValidator(InputStream schemaFile) throws SAXException {		
		initSchemaValidator(schemaFile);
	}
	
	private void initSchemaValidator(InputStream schemaFile) throws SAXException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    Schema schema = schemaFactory.newSchema(new StreamSource(schemaFile));
	    this.validator = schema.newValidator();
	}
	
	private InputStream getSchemaFile() throws IOException {
		ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream(kMarcXmlSchemaXSDFileName);
		if (null == in) {
			throw new IOException("Failed to getSystemResourceAsStream = "
                    + kMarcXmlSchemaXSDFileName);
		}
	 
		return in;
	}
	 
	
    public void validateXml(String xml) throws SAXException, IOException {
    	getValidator().validate(new StreamSource(new StringReader(xml)));
    }
    
	private Validator getValidator() {
		return validator;
	}
}
