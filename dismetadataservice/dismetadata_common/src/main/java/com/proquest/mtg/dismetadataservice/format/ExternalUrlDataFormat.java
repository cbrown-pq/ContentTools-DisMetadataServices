package com.proquest.mtg.dismetadataservice.format;


import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.proquest.mtg.dismetadataservice.error.Errors;
import com.proquest.mtg.dismetadataservice.exodus.IExternalUrlDataProvider;
import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList.Dissertation;
import com.proquest.mtg.dismetadataservice.utils.DisoutMetadaException;
import com.proquest.mtg.dismetadataservice.utils.XmlSchemaValidator;
public class ExternalUrlDataFormat implements IExternalUrlDataFormat {

	private final IExternalUrlDataProvider externalUrlDataProvider;
	private final XmlSchemaValidator<DissertationList> schemaValidator;
	private final Errors errorMsgXml;

	public ExternalUrlDataFormat(IExternalUrlDataProvider externalUrlDataProvider,
			XmlSchemaValidator<DissertationList> schemaValidator) {
		this.externalUrlDataProvider = externalUrlDataProvider;
		this.schemaValidator = schemaValidator;
		this.errorMsgXml = new Errors();
	}



	public XmlSchemaValidator<DissertationList> getSchemaValidator() {
		return schemaValidator;
	}

	public IExternalUrlDataProvider getExternalUrlDataProvider() {
		return externalUrlDataProvider;
	}

	@Override
	public DissertationList makeFor() throws Exception {
		DissertationList dissertationList = getExternalUrlDataProvider().geDataFor();
		if (null == dissertationList) {
			String msg = "No data found ";
			addError(msg);
			throw new DisoutMetadaException(
					convertObjectToString(this.errorMsgXml));
		} else {
			List<String> errors = getSchemaValidator().validateMngXml(dissertationList);
			for (String err : errors) {
				addError(err);
			}			
			if (errors.size() > 0) {
				String str = convertObjectToString(this.errorMsgXml);			
				throw new DisoutMetadaException(str);
			}
		}
		return dissertationList;
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
		
		String str = result.toString().replace("&amp;","&").replace("&lt;","<")
				.replace("&gt;",">").replace("&apos;","'")
				.replace("&quot;","\"");
		
		return str;
		
	}



}
