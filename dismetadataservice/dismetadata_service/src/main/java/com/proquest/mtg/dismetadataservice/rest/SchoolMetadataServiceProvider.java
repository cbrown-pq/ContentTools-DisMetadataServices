package com.proquest.mtg.dismetadataservice.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.SchoolMetaDataProvider;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School;

@Path("/schoolmetadata/")
public class SchoolMetadataServiceProvider {
	private final SchoolMetaDataProvider schoolMetadaProvider;

	@Inject
	public SchoolMetadataServiceProvider(SchoolMetaDataProvider schoolMetadaProvider) {
		this.schoolMetadaProvider = schoolMetadaProvider;
	}

	public SchoolMetaDataProvider getSchoolMetadataProvider() {
		return schoolMetadaProvider;
	}
	
	@GET
	@Path("/getAllSchools/")
	@Produces(MediaType.APPLICATION_XML)
	public Schools getAllSchoolMetaData() throws WebApplicationException {
		List<School> schools = Lists.newArrayList();
		Schools result = new Schools();
		try {
			schools = getSchoolMetadataProvider().getAllSchoolMetaData();
			result.getSchool().addAll(schools);
			//result = getMetaDataFormatFactory().getFor(formatType).makeFor(pubNumber);
		} catch(IllegalArgumentException e) {
			throw new MetaDataServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			throw new MetaDataServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		//return Response.status(Response.Status.OK).entity(result).build();
		
		//Schools fakeSchool = MakeSchoolMetadataHelper.MakeFakeSchoolMetadata();
		//return fakeSchool;
		return result;
	}
	
	@GET
	@Path("/getSchoolByCode/{schoolCode}")
	@Produces(MediaType.APPLICATION_XML)
	public Schools getSchoolMetaDataByCode(@PathParam("schoolCode") String schoolCode) throws WebApplicationException {
		Schools.School school;
		Schools result = new Schools();
		try {
			school = getSchoolMetadataProvider().getSchoolMetaDataFor(schoolCode);
			result.getSchool().add(school);
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
			getSchoolMetadataProvider().updateDashboardLoadStatus(schoolCode);
		} catch (Exception e) {
			throw new MetaDataServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity("SUCCESS").build();
	}
}
