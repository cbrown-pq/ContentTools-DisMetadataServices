package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/usmarc")
public class DisMetadataService {

	@GET
	@Path("/{param}")
    public Response getMsg(@PathParam("param") String pubNumber) {

        String output = pubNumber;

        return Response.status(200).entity(output).build();

    }

}