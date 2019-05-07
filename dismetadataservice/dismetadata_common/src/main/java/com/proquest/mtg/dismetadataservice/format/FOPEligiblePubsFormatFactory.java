package com.proquest.mtg.dismetadataservice.format;

/*import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.IFOPEligiblePubsProvider;
import com.proquest.mtg.dismetadataservice.fopeligiblepubs.xml.FopEligiblePubsList;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class FOPEligiblePubsFormatFactory {
	private final IFOPEligiblePubsProvider fopEligiblePubsProvider;
	private XmlSchemaValidator<FopEligiblePubsList> xmlSchemaValidator;
	public static String kFopEligiblePubsXSDFileName = "FOPEligiblePubs.xsd";

	@Inject
	public FOPEligiblePubsFormatFactory (
			IFOPEligiblePubsProvider fopEligiblePubsProvider) throws IOException, SAXException {
		this.fopEligiblePubsProvider = fopEligiblePubsProvider;
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream in = classLoader
				.getResourceAsStream(kFopEligiblePubsXSDFileName);
		if (null == in) {
			throw new IOException("Failed to getSystemResourceAsStream = "
					+ kFopEligiblePubsXSDFileName);
		}
		xmlSchemaValidator = new XmlSchemaValidator<FopEligiblePubsList>(in);
	}

	public IFOPEligiblePubsProvider getFOPEligiblePubsProvider() {
		return fopEligiblePubsProvider;
	}

	public FOPEligiblePubsDataFormat get() throws IOException, SAXException {
		return new FOPEligiblePubsDataFormat(getFOPEligiblePubsProvider(),xmlSchemaValidator);
	}
	
	public IFOPEligiblePubsDataFormat update() throws IOException {
		return new FOPEligiblePubsDataFormat(getFOPEligiblePubsProvider());
	}
}*/
