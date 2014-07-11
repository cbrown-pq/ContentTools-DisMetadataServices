package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


@SuppressWarnings("serial")
public class DisServiceException extends WebApplicationException {

	public DisServiceException(Response.Status status) {
		super(createResponse(status, null));
	}
	
	public DisServiceException(Response.Status status, String message) {
		super(createResponse(status, message));
	}
	
	private static Response createResponse(Response.Status statusCode, String message) {
		return Response.status(statusCode).entity(message).build();
	}
}
