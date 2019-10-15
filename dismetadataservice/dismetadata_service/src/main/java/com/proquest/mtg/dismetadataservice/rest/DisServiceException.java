package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@SuppressWarnings("serial")
public class DisServiceException extends WebApplicationException {

	public DisServiceException(Response.Status status) {
		super(createResponse(status, null));
	}
	
	public DisServiceException(Response.Status status, String message) {
		super(createResponse(status, message));
	}
	
	private static Response createResponse(Response.Status statusCode, String message) {
		System.out.println("IN RESPONSE");
		if(message==null)
		{
			System.out.println("NULL!");
			message="Internal Server Error";
			statusCode= Status.INTERNAL_SERVER_ERROR;		
		}
		return Response.status(statusCode).entity(message).type(MediaType.TEXT_PLAIN).build();
	}
}
