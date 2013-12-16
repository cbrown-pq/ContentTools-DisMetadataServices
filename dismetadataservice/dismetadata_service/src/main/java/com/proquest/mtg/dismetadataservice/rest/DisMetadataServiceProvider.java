package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;

@Path("/dispubmetadata/")
public class DisMetadataServiceProvider {

	@GET
	@Path("/{pubNumber}/{formatType}")
	@Produces(MediaType.APPLICATION_XML)
	public DisPubMetaData getDisMetaData(
			@PathParam("pubNumber") String pubNumber,
			@PathParam("formatType") String formatType) {

		DisPubMetaData disPubMetadata = new DisPubMetaData();
		disPubMetadata.setPubNumber(pubNumber);
		disPubMetadata.setFormatType(formatType);
		return disPubMetadata;

	}
	
	@GET
	@Path("/{pubNumber}")
    public Response getDisMetaData2(@PathParam("pubNumber") String pubNumber) {

        String output = pubNumber;
        return Response.status(200).entity(output).build();

    }
	
}