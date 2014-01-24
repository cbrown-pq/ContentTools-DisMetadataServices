package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MetaDataCustomExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
		Response response;
		
		if(e instanceof IllegalArgumentException) {
			 response = createResponse(Response.Status.NO_CONTENT, e.getMessage());
		} else {
			response = createResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return response;
	}

	private Response createResponse(Status statusCode, String message) {
		return Response.status(statusCode).entity(message).build();
	}

}
