package com.proquest.mtg.dismetadataservice.utils;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;


public class XMLSchemaValidator_Tests {
	XmlSchemaValidator<?> target;
	File destinationDir;
	
	private static String kValidXSDFileName = "ValidXSD.xsd";
	private static String kInvalidInputXmlFileName = "InvalidXMLFile.xml";
	private static String kValidInputXmlFileName = "ValidXML.xml";
	private static String kMissingInputXmlFileName = "missingXML.xml";
	String resourceLocation;
	
	public static File getRootDir() throws Exception {
		String appRootDirResourceLocatorFile = "SchemaValidator_Test/ResourceLocator.txt";
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		URL url = classLoader
				.getResource(appRootDirResourceLocatorFile);
		if (null == url) {
			fail("Failed to find test resource: "
					+ appRootDirResourceLocatorFile);
		}
		return new File(url.toURI().getPath()).getParentFile();
	}
	
	@Before
	public void setup() throws Exception {
		resourceLocation = getRootDir().getAbsolutePath();
	}
	
	@Test(expected=FileNotFoundException.class)
	public void initializeWithMissingXSDFile_throw_exception() throws Exception {
		String missingXsdFile = FilenameUtils.concat(
				getRootDir().getAbsolutePath(), "WrongXsd.xml");
		target = new XmlSchemaValidator<Object>(missingXsdFile);
	}
	
	@Test(expected=SAXException.class)
	public void withInvalidXml_throw_exception() throws Exception {
		String validXsdFile = FilenameUtils.concat(
				getRootDir().getAbsolutePath(), kValidXSDFileName);
		String inValidXmlFile = FilenameUtils.concat(
				getRootDir().getAbsolutePath(), kInvalidInputXmlFileName);
		target = new XmlSchemaValidator<Object>(validXsdFile);
		target.validateXml(inValidXmlFile);
	}
	
	@Test
	public void validXml_Success() throws Exception {
		String validXsdFile = FilenameUtils.concat(
				getRootDir().getAbsolutePath(), kValidXSDFileName);
		String validXsml = FilenameUtils.concat(
				getRootDir().getAbsolutePath(), kValidInputXmlFileName);
		target = new XmlSchemaValidator<Object>(validXsdFile);
		target.validateXml(validXsml);
	}
	
	@Test(expected=IOException.class)
	public void missingInputXml_thorws_exception() throws Exception {
		String validXsdFile = getRootDir().getAbsolutePath() + "\\" + kValidXSDFileName;
		String missingXsml = getRootDir().getAbsolutePath() + "\\" + kMissingInputXmlFileName;
		target = new XmlSchemaValidator<Object>(validXsdFile);
		target.validateXml(missingXsml);
	}

}
