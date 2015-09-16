package com.proquest.mtg.dismetadataservice.format;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

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
					convertObjecttoString(this.errorMsgXml));
		} else {
			List<String> errors = getSchemaValidator().validateMngXml(
					pubMetaData);
			for (String err : errors) {
				addError(err);
			}			
			if (errors.size() > 0) {
				throw new DisoutMetadaException(
						convertObjecttoString(this.errorMsgXml));
			}
		}
		return convertObjecttoString(pubMetaData);
	}

	private void addError(String errorMessage) {
		Errors.Error err = new Errors.Error();
		err.setErrorDescription(errorMessage);
		this.errorMsgXml.getError().add(err);
	}

	private String convertObjecttoString(Object object) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter result = new StringWriter();
		marshaller.marshal(object, result);
		return result.toString();
	}

}
