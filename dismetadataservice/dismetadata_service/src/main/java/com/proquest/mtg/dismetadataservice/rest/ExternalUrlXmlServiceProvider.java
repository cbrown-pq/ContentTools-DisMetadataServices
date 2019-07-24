package com.proquest.mtg.dismetadataservice.rest;

/*import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
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
	@Path("/getList")
	@Produces(MediaType.APPLICATION_XML)
	public DissertationList getExternalUrlDataFor(@DefaultValue("99991231") @QueryParam("lastRunDate") String lastRunDate) throws WebApplicationException {
		DissertationList result;
		try {
			result = getExternalUrlMetaDataFormatFactory().get().getExtUrlData(lastRunDate);
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
	
	@GET
	@Path("/updateExtUrlStatus/{pubId}/{Status}")
	public String updateExternalUrlStatus(@PathParam("pubId") String pubId, @PathParam("Status") String Status ) throws WebApplicationException {
		String result;
		try {
			result = getExternalUrlMetaDataFormatFactory().update().updateExtUrlStatus(pubId, Status);
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
}*/
