package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.format.MetaDataFormatFactory;

@Path("/metadata/")
public class DisMetadataServiceProvider {
	private final MetaDataFormatFactory metaDataformatFactory;

	@Inject
	public DisMetadataServiceProvider(MetaDataFormatFactory metaDataformatFactory) {
		this.metaDataformatFactory = metaDataformatFactory;
	}

	public MetaDataFormatFactory getMetaDataFormatFactory() {
		return metaDataformatFactory;
	}

	@GET
	@Path("/{pubNumber}/{formatType}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getDisMetaData(@PathParam("pubNumber") String pubNumber,
			@PathParam("formatType") String formatType) throws MetaDataServiceException {
		String result = null;
		int statusCode;
		try {
			result = getMetaDataFormatFactory().getFor(formatType).makeFor(pubNumber);
			statusCode = 200;	    
		} catch (Exception ex) {
			throw new MetaDataServiceException(ex);
		}
		return Response.status(statusCode).entity(result).build();

	}

}