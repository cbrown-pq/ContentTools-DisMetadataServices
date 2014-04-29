package com.proquest.mtg.dismetadataservice.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XmlSchemaValidator<T> {
	private Validator validator;

	public XmlSchemaValidator(String schemaFilename) throws SAXException, FileNotFoundException {		
		initSchemaValidator(new FileInputStream(schemaFilename));
	}
	
	public XmlSchemaValidator(InputStream schemaFile) throws SAXException {		
		initSchemaValidator(schemaFile);
	}
	
	private void initSchemaValidator(InputStream schemaFile) throws SAXException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    Schema schema = schemaFactory.newSchema(new StreamSource(schemaFile));
	    this.validator = schema.newValidator();
	}
	
    public void validateXml(String xmlFile) throws SAXException, IOException {
    	getValidator().validate(new StreamSource(xmlFile));
    }
    
    public void validateXml(T object) throws SAXException, IOException, JAXBException {
    	JAXBContext jc = JAXBContext.newInstance(object.getClass());
        JAXBSource source = new JAXBSource(jc, object);
    	getValidator().validate(source);
    }

	private Validator getValidator() {
		return validator;
	}

}
