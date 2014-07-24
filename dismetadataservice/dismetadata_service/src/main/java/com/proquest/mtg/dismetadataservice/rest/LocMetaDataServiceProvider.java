package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.loc.LOCFormat;
import com.proquest.mtg.dismetadataservice.pqloc.CreateNewClaimInput;


@Path("/loc/")
public class LocMetaDataServiceProvider {
	
	private final LOCFormat locFormat;

	public LOCFormat getLocFormat() {
		return locFormat;
	}
	
	@Inject
	public LocMetaDataServiceProvider(LOCFormat locFormat) {
		this.locFormat = locFormat;
	}
	
	@GET
	@Path("/metadataForEligiblePubs/")
	@Produces(MediaType.APPLICATION_XML)
	public CreateNewClaimInput getLOCDataForAllEligiblePubs() throws WebApplicationException {
		CreateNewClaimInput result = null;
		try {
			result = getLocFormat().makeForEligiblePubs();
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
	
	@GET
	@Path("/metadataFor/{pubNumber}")
	@Produces(MediaType.APPLICATION_XML)
	public CreateNewClaimInput getLOCDataFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
		CreateNewClaimInput result = null;
		try {
			result = getLocFormat().makeFor(pubNumber);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}

	@PUT
	@Path("/ackClaimSubmissionFor/{pubNumber}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ackLOCClaimSubmissionFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
		try {
			getLocFormat().updateLOCClaimSubmissionFor(pubNumber);
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity("SUCCESS").build();		
	}
	
	@PUT
	@Path("/ackDeliverySubmissionFor/{pubNumber}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ackLOCDeliverySubmissionFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
		try {
			getLocFormat().updateLOCDeliverySubmissionFor(pubNumber);
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity("SUCCESS").build();		
	}

}
