package com.proquest.mtg.dismetadataservice.format;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.IExternalUrlDataProvider;
import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class ExternalUrlMetaDataFormatFactory {
	public static String kDissertationXSDFileName = "ExodusExternalUrl.xsd";
	private XmlSchemaValidator<DissertationList> xmlSchemaValidator;
	private final IExternalUrlDataProvider externalUrlDataProvider;

	@Inject
	public ExternalUrlMetaDataFormatFactory (
			IExternalUrlDataProvider externalUrlDataProvider) throws IOException, SAXException {
		this.externalUrlDataProvider = externalUrlDataProvider;
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream in = classLoader
				.getResourceAsStream(kDissertationXSDFileName);
		if (null == in) {
			throw new IOException("Failed to getSystemResourceAsStream = "
					+ kDissertationXSDFileName);
		}
		xmlSchemaValidator = new XmlSchemaValidator<DissertationList>(in);
	}

	public IExternalUrlDataProvider getExternalUrlDataProvider() {
		return externalUrlDataProvider;
	}

	public ExternalUrlDataFormat get() throws IOException, SAXException {
		return new ExternalUrlDataFormat(getExternalUrlDataProvider(),
				xmlSchemaValidator);
	}
	
	public IExternalUrlDataFormat update() throws IOException, SAXException {
			return new ExternalUrlDataFormat(getExternalUrlDataProvider());
	}

}
