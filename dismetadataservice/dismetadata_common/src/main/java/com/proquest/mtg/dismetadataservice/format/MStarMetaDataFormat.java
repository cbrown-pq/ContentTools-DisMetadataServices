package com.proquest.mtg.dismetadataservice.format;


import java.io.StringWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import com.proquest.mtg.dismetadataservice.error.Errors;
import com.proquest.mtg.dismetadataservice.exodus.IMStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation;
import com.proquest.mtg.dismetadataservice.utils.DisoutMetadaException;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;
public class MStarMetaDataFormat implements IMStarMetaDataFormat {

	private final IMStarPubMetaDataProvider metaDataProvider;
	private final XmlSchemaValidator<Dissertation> schemaValidator;
	private final Errors errorMsgXml;

	public MStarMetaDataFormat(IMStarPubMetaDataProvider metaDataProvider,
			XmlSchemaValidator<Dissertation> schemaValidator) {
		this.metaDataProvider = metaDataProvider;
		this.schemaValidator = schemaValidator;
		this.errorMsgXml = new Errors();

	}

	public XmlSchemaValidator<Dissertation> getSchemaValidator() {
		return schemaValidator;
	}

	public IMStarPubMetaDataProvider getMetaDataProvider() {
		return metaDataProvider;
	}

	@Override
	public String makeForDissertation(String pubNum) throws Exception {

		Dissertation pubMetaData = getMetaDataProvider().getPubMetaDataFor(
				pubNum);
		if (null == pubMetaData) {
			String msg = "No data found for this Pub Number: " + pubNum;
			addError(msg);
			throw new DisoutMetadaException(
					convertObjectToString(this.errorMsgXml));
		} else {
			List<String> errors = getSchemaValidator().validateMngXml(
					pubMetaData);
			for (String err : errors) {
				addError(err);
			}			
			if (errors.size() > 0) {
				String str = convertObjectToString(this.errorMsgXml);			
				throw new DisoutMetadaException(str);
			}
		}
		return convertObjectToString(pubMetaData);
	}

	private void addError(String errorMessage) {
		Errors.Error err = new Errors.Error();
		err.setErrorDescription(errorMessage);
		this.errorMsgXml.getError().add(err);
	}
	
	private String convertObjectToString(Object object) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);		
		StringWriter result = new StringWriter();
		marshaller.marshal(object, result);
		/*
		String str = result.toString().replace("&amp;","&").replace("&lt;","<")
				.replace("&gt;",">").replace("&apos;","'")
				.replace("&quot;","\"");		
			
		return str;
		*/
		return handleHtmlTag(result.toString());
	}
	
	private String handleHtmlTag(String str){
		Pattern tagPattern = Pattern.compile("&lt;([^<]{1,50}?)&gt;");
		Matcher m2 = tagPattern.matcher(str);
		while (m2.find()){
			String rep = "<"+m2.group(1)+">";	
			str = str.replaceAll(m2.group(0),rep);			
			m2 = tagPattern.matcher(str);
		}		
		return str;
	}

}
