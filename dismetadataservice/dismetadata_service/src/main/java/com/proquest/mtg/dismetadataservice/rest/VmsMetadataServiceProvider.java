package com.proquest.mtg.dismetadataservice.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.vms.VmsMetaDataProvider;

@Path("/vmsmetadata/")
public class VmsMetadataServiceProvider {

	private final VmsMetaDataProvider vmsMetaDataProvider;

	@Inject
	public VmsMetadataServiceProvider(VmsMetaDataProvider vmsMetaDataProvider) {
		this.vmsMetaDataProvider = vmsMetaDataProvider;
	}

	@GET
	@Path("/getPQDeliveryData")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllSubjects(@QueryParam("startDate") String startDate,@QueryParam("endDate") String endDate, @QueryParam("pubNumbers") String pubNumbers) throws WebApplicationException {
		String result = "";
		JSONArray jsonArray = new JSONArray();
		try {
			result = vmsMetaDataProvider.getPQDeliveryData(startDate,endDate,pubNumbers);

		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT); 
		} catch (Exception e) {
			throw new DisServiceException(
					Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}

}
