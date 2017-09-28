package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.fopeligiblepubs.xml.FopEligiblePubsList;
import com.proquest.mtg.dismetadataservice.format.FOPEligiblePubsFormatFactory;


@Path("/foppubs/")
public class FOPEligiblePubsServiceProvider {
	
	private final FOPEligiblePubsFormatFactory fopEligiblePubsFormatFactory;

	
	@Inject
	public FOPEligiblePubsServiceProvider(FOPEligiblePubsFormatFactory fopEligiblePubsFormatFactory) {
		this.fopEligiblePubsFormatFactory = fopEligiblePubsFormatFactory;
	}
	
	public FOPEligiblePubsFormatFactory getFOPEligiblePubsFormatFactory() {
		return fopEligiblePubsFormatFactory;
	}

	@GET
	@Path("/getEligiblePubsList")
	@Produces(MediaType.APPLICATION_XML)
	public FopEligiblePubsList getFOPEligiblePubsDataFor() throws WebApplicationException {
		FopEligiblePubsList result;
		try {
			result = getFOPEligiblePubsFormatFactory().get().getFOPEligiblePubs();
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
	
	@GET
	@Path("/updateInProgressStatus/{pubID}/{Status}")
	public String updateInprogressStatusFor(@PathParam("pubID") String pubId, @PathParam("Status") String Status) throws WebApplicationException {
		String result;
		try {
			result = getFOPEligiblePubsFormatFactory().update().updateFFInprogressStatus(pubId, Status);
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
}
