package com.proquest.mtg.dismetadataservice.format;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.IMStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class MStarMetaDataFormatFactory {
	public static String kDissertationXSDFileName = "MStarXML.xsd";
	private final IMStarPubMetaDataProvider mStarMetadaProvider;

	@Inject
	public MStarMetaDataFormatFactory(
			IMStarPubMetaDataProvider mStarMetadaProvider) {
		this.mStarMetadaProvider = mStarMetadaProvider;

	}

	public IMStarPubMetaDataProvider getMStarMetadaProvider() {
		return mStarMetadaProvider;
	}

	public MStarMetaDataFormat create() throws IOException, SAXException {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream in = classLoader
				.getResourceAsStream(kDissertationXSDFileName);
		if (null == in) {
			throw new IOException("Failed to getSystemResourceAsStream = "
					+ kDissertationXSDFileName);
		}

		return new MStarMetaDataFormat(getMStarMetadaProvider(),
				new XmlSchemaValidator<Dissertation>(in));
	}

}
