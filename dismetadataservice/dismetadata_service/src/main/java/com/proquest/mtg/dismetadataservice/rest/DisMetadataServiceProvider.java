package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dismetadata/")

public class DisMetadataServiceProvider {

	@GET
	@Path("/{pubNumber}/{type}")
    public Response getDisMetaData(@PathParam("pubNumber") String pubNumber,@PathParam("type") String type) {

        String output = pubNumber + "-" + type;
        return Response.status(200).entity(output).build();

    }
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_XML)
	public String returnTitle() {
		return "{\"name\":\"George Koch\", \"age\":58}";
		
		//return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RelatedIds><RelatedId scinfo=\"PROFESSIONAL|ACADEMIC\">305129883</RelatedId></RelatedIds>";
	}
	

}