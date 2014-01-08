package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.format.MetaDataFormatFactory;

@Path("/dispubmetadata/")
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
			@PathParam("formatType") String formatType) {
		String result = null;
		try {
			result = getMetaDataFormatFactory().getFor(formatType).makeFor(pubNumber);
				    
		} catch (NullPointerException e) {
			result = "No Data Found";
			return Response.status(300).entity(result).build();
		} catch (Exception e) {
			result = e.toString();
			return Response.status(400).entity(result).build();
		}
		return Response.status(200).entity(result).build();

	}

}