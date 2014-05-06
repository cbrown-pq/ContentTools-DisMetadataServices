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
import com.proquest.mtg.dismetadataservice.format.SchoolMetaDataFormatFactory;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools;

@Path("/schoolmetadata/")
public class SchoolMetadataServiceProvider {
	private final SchoolMetaDataFormatFactory schoolMetaDataFormatFactory;

	@Inject
	public SchoolMetadataServiceProvider(SchoolMetaDataFormatFactory schoolMetaDataFormatFactory) {
		this.schoolMetaDataFormatFactory = schoolMetaDataFormatFactory;
	}

	public SchoolMetaDataFormatFactory getSchoolMetaDataFormatFactory() {
		return schoolMetaDataFormatFactory;
	}
	
	@GET
	@Path("/getAllSchools/")
	@Produces(MediaType.APPLICATION_XML)
	public Schools getAllSchoolMetaData() throws WebApplicationException {
		Schools result = new Schools();
		try {
			result = getSchoolMetaDataFormatFactory().create().makeForAllSchoolCode();
		} catch(IllegalArgumentException e) {
			throw new MetaDataServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			throw new MetaDataServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
	
	@GET
	@Path("/getSchoolByCode/{schoolCode}")
	@Produces(MediaType.APPLICATION_XML)
	public Schools getSchoolMetaDataByCode(@PathParam("schoolCode") String schoolCode) throws WebApplicationException {
		Schools result = new Schools();
		try {
			result = getSchoolMetaDataFormatFactory().create().makeForSchoolCode(schoolCode);
		} catch(IllegalArgumentException e) {
			throw new MetaDataServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			throw new MetaDataServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
	
	@PUT
	@Path("/ackSchoolMetadataLoadFor/{schoolCode}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ackSchoolMetaDataLoadByCode(@PathParam("schoolCode") String schoolCode) throws WebApplicationException {
		try {
			getSchoolMetaDataFormatFactory().updateDsahboardLoadStatus(schoolCode);
		} catch (Exception e) {
			throw new MetaDataServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity("SUCCESS").build();
	}
}
