package com.proquest.mtg.dismetadataservice.format;

import com.proquest.mtg.dismetadataservice.exodus.IMStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class MStarMetaDataFormat implements IMStarMetaDataFormat {

	private final IMStarPubMetaDataProvider metaDataProvider;
	private final XmlSchemaValidator<Dissertation> schemaValidator;

	public MStarMetaDataFormat(IMStarPubMetaDataProvider metaDataProvider,
			XmlSchemaValidator<Dissertation> schemaValidator) {
		this.metaDataProvider = metaDataProvider;
		this.schemaValidator = schemaValidator;

	}

	public XmlSchemaValidator<Dissertation> getSchemaValidator() {
		return schemaValidator;
	}

	public IMStarPubMetaDataProvider getMetaDataProvider() {
		return metaDataProvider;
	}

	@Override
	public Dissertation makeForDissertation(String pubNum) throws Exception {

		Dissertation pubMetaData = getMetaDataProvider().getPubMetaDataFor(
				pubNum);
		getSchemaValidator().validateXml(pubMetaData);
		
		return pubMetaData;
	}	

}
