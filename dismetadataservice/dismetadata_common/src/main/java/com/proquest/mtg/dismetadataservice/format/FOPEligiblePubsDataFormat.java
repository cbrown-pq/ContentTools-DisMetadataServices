package com.proquest.mtg.dismetadataservice.format;


import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.proquest.mtg.dismetadataservice.error.Errors;
import com.proquest.mtg.dismetadataservice.exodus.IFOPEligiblePubsProvider;
import com.proquest.mtg.dismetadataservice.fopeligiblepubs.xml.FopEligiblePubsList;
import com.proquest.mtg.dismetadataservice.utils.DisoutMetadaException;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;

public class FOPEligiblePubsDataFormat implements IFOPEligiblePubsDataFormat {

	private final IFOPEligiblePubsProvider fopEligiblePubsProvider;
	private final XmlSchemaValidator<FopEligiblePubsList> schemaValidator;
	private final Errors errorMsgXml;

	public FOPEligiblePubsDataFormat(IFOPEligiblePubsProvider fopEligiblePubsProvider,
			XmlSchemaValidator<FopEligiblePubsList> schemaValidator) {
		this.fopEligiblePubsProvider = fopEligiblePubsProvider;
		this.schemaValidator = schemaValidator;
		this.errorMsgXml = new Errors();
	}
	
	public FOPEligiblePubsDataFormat(IFOPEligiblePubsProvider fopEligiblePubsProvider) {
		this.fopEligiblePubsProvider = fopEligiblePubsProvider;
		this.schemaValidator = null;
		this.errorMsgXml = new Errors();
	}

	public IFOPEligiblePubsProvider getFOPEligiblePubsProvider() {
		return fopEligiblePubsProvider;
	}
	
	public XmlSchemaValidator<FopEligiblePubsList> getSchemaValidator() {
		return schemaValidator;
	}

	@Override
	public FopEligiblePubsList getFOPEligiblePubs() throws Exception {
		FopEligiblePubsList eligiblePubsList = getFOPEligiblePubsProvider().geDataFor();
		
		if (null == eligiblePubsList) {
			String msg = "No data found ";
			addError(msg);
			throw new DisoutMetadaException(
					convertObjectToString(this.errorMsgXml));
		}else {
			List<String> errors = getSchemaValidator().validateMngXml(eligiblePubsList);
			for (String err : errors) {
				addError(err);
			}			
			if (errors.size() > 0) {
				String str = convertObjectToString(this.errorMsgXml);			
				throw new DisoutMetadaException(str);
			}
		}
		return eligiblePubsList;
	}
	
	@Override
	public String updateFFInprogressStatus(String pubid, String status) throws Exception {
		String result = getFOPEligiblePubsProvider().updateInprogressStatus(pubid, status);
		if (result.matches("Error")) {
			String msg = "Film fiche in progress status update operation failed ["+result+"]";
			addError(msg);
			throw new DisoutMetadaException(
					convertObjectToString(this.errorMsgXml));
		}
		return result;
	}
	
	private String convertObjectToString(Object object) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);		
		StringWriter result = new StringWriter();
		marshaller.marshal(object, result);
		
		String str = result.toString().replace("&amp;","&").replace("&lt;","<")
				.replace("&gt;",">").replace("&apos;","'")
				.replace("&quot;","\"");
		
		return str;
		
	}
	
	private void addError(String errorMessage) {
		Errors.Error err = new Errors.Error();
		err.setErrorDescription(errorMessage);
		this.errorMsgXml.getError().add(err);
	}
}
