package com.proquest.mtg.dismetadataservice.format;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.IExternalUrlDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.IMStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class ExternalUrlMetaDataFormatFactory {
	public static String kDissertationXSDFileName = "ExodusExternalUrl.xsd";
	private final IExternalUrlDataProvider externalUrlDataProvider;

	@Inject
	public ExternalUrlMetaDataFormatFactory(
			IExternalUrlDataProvider externalUrlDataProvider) {
		this.externalUrlDataProvider = externalUrlDataProvider;

	}

	public IExternalUrlDataProvider getExternalUrlDataProvider() {
		return externalUrlDataProvider;
	}

	public ExternalUrlDataFormat create() throws IOException, SAXException {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream in = classLoader
				.getResourceAsStream(kDissertationXSDFileName);
		if (null == in) {
			throw new IOException("Failed to getSystemResourceAsStream = "
					+ kDissertationXSDFileName);
		}

		return new ExternalUrlDataFormat(getExternalUrlDataProvider(),
				new XmlSchemaValidator<DissertationList>(in));
	}

}
