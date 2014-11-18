package com.proquest.mtg.dismetadataservice.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.LocReportPubMetaData;
import com.proquest.mtg.dismetadataservice.locreports.CopyrightPubReportProvider;


@Path("/locreports/")
public class LocReportServiceProvider {
	
	private CopyrightPubReportProvider copyrightPubReportProvider;
	
	@Inject
	public LocReportServiceProvider(CopyrightPubReportProvider copyrightPubReportProvider) {
		this.copyrightPubReportProvider = copyrightPubReportProvider;
	}
	
	@GET
	@Path("/copyrightpubs/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEligibleCopyrightPubs() throws WebApplicationException {
		List<LocReportPubMetaData> result = Lists.newArrayList();
		try {
			result = getCopyrightPubReportProvider().getCopyrightSubmittedPubs();
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity(result).build();
	}

	public CopyrightPubReportProvider getCopyrightPubReportProvider() {
		return copyrightPubReportProvider;
	}

}
