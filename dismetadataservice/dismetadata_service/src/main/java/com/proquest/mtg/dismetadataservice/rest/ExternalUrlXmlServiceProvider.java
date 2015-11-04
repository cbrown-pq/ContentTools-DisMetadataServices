package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
import com.proquest.mtg.dismetadataservice.format.ExternalUrlMetaDataFormatFactory;


@Path("/externalurl/")
public class ExternalUrlXmlServiceProvider {
	
	private final ExternalUrlMetaDataFormatFactory externalUrlMetaDataFormatFactory;

	
	@Inject
	public ExternalUrlXmlServiceProvider(ExternalUrlMetaDataFormatFactory externalUrlMetaDataFormatFactory) {
		this.externalUrlMetaDataFormatFactory = externalUrlMetaDataFormatFactory;
	}
	
	public ExternalUrlMetaDataFormatFactory getExternalUrlMetaDataFormatFactory() {
		return externalUrlMetaDataFormatFactory;
	}

	
	@GET
	@Path("/getList/")
	@Produces(MediaType.APPLICATION_XML)
	public DissertationList getExternalUrlDataFor() throws WebApplicationException {
		DissertationList result;
		try {
			result = getExternalUrlMetaDataFormatFactory().create().makeFor();
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}	
}
