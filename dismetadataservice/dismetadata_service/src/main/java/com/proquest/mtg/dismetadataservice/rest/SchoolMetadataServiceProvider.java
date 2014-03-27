package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.proquest.mtg.dismetadataservice.helper.MakeSchoolMetadataHelper;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools;

@Path("/schoolmetadata/")
public class SchoolMetadataServiceProvider {

	@GET
	@Path("/getAllSchools/")
	@Produces(MediaType.APPLICATION_XML)
	public Schools getAllSchoolMetaData() throws WebApplicationException {
		Schools fakeSchool = MakeSchoolMetadataHelper.MakeFakeSchoolMetadata();
		return fakeSchool;
	}
	
	@GET
	@Path("/getSchoolByCode/{schoolCode}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSchoolMetaDataByCode(@PathParam("schoolCode") String schoolCode) throws WebApplicationException {
		String result = "This is schools with School Code :" + schoolCode;
		return Response.status(Response.Status.OK).entity(result).build();
	}
}
