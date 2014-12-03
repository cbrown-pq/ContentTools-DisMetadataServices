package com.proquest.mtg.dismetadataservice.rest;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	public static final String kPubSeparator = ",";
	
	@Inject
	public LocReportServiceProvider(CopyrightPubReportProvider copyrightPubReportProvider) {
		this.copyrightPubReportProvider = copyrightPubReportProvider;
	}
	
	@GET
	@Path("/copyrightpubs/{formatType}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEligibleCopyrightPubs(@PathParam("formatType") String formatType) throws WebApplicationException {
		List<LocReportPubMetaData> result = Lists.newArrayList();
		try {
			result = getCopyrightPubReportProvider().getCopyrightSubmittedPubs(formatType);
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
	
	
	@GET
	@Path("/noncopyrightpubs/{formatType}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLOCDataForAllNonCopyrightPubs(@PathParam("formatType") String formatType) throws WebApplicationException {
		List<LocReportPubMetaData> result = null;
		try {
			result = getCopyrightPubReportProvider().getNonCopyrightPubs(formatType);
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
	
	@PUT
	@Path("/updatefilmpulldate/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateFilm(@DefaultValue("") @QueryParam("publist") String pubList) throws WebApplicationException {
		List<String> pubs = Lists.newArrayList(pubList.split(kPubSeparator));
		pubs.removeAll(Lists.newArrayList("", null));
		try {
			if (pubs.size() > 0) {
				getCopyrightPubReportProvider().updateFilmPullDate(pubs);
			}
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity("SUCCESS").build();
	}
	

	public CopyrightPubReportProvider getCopyrightPubReportProvider() {
		return copyrightPubReportProvider;
	}
}
