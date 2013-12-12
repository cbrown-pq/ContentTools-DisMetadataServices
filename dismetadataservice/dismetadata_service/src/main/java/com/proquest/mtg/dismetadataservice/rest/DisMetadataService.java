package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/dismetadata/")
public class DisMetadataService {

	@GET
	@Path("/{pubNumber}/{type}")
    public Response getDisMetaData(@PathParam("pubNumber") String pubNumber,@PathParam("type") String type) {

        String output = pubNumber + "-" + type;

        return Response.status(200).entity(output).build();

    }

}