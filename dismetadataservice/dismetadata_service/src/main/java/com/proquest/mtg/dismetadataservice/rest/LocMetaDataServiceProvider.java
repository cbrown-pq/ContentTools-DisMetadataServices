package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.loc.LOCFormat;
import com.proquest.mtg.dismetadataservice.pqloc.Claims;


@Path("/locmetadata/")
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
	@Path("/eligiblePubs/{lastRunDate}")
	@Produces(MediaType.APPLICATION_XML)
	public Claims getLOCDataForAllEligiblePubs(@PathParam("lastRunDate") String lastRunDate) throws WebApplicationException {
		Claims result = null;
		try {
			result = getLocFormat().makeForEligiblePubs(lastRunDate);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new MetaDataServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			e.printStackTrace();
			throw new MetaDataServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
	
	@GET
	@Path("/{pubNumber}")
	@Produces(MediaType.APPLICATION_XML)
	public Claims getLOCDataFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
		Claims result = null;
		try {
			result = getLocFormat().makeFor(pubNumber);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new MetaDataServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			e.printStackTrace();
			throw new MetaDataServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}


}
