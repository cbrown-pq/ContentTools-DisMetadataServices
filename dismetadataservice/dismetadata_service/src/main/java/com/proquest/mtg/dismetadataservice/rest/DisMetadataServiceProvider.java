package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
			@PathParam("formatType") String formatType) throws WebApplicationException {
		String result = null;
		try {
			result = getMetaDataFormatFactory().getFor(formatType).makeFor(pubNumber);
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity(result).build();
	}

}