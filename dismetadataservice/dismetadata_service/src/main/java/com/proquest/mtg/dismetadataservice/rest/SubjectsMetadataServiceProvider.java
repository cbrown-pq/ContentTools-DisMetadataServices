package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.format.SubjectsMetaDataFormatFactory;
import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects;

@Path("/subjectsmetadata/")
public class SubjectsMetadataServiceProvider {
	
	private final SubjectsMetaDataFormatFactory subjectMetaDataformatFactory;
	
	@Inject
	public SubjectsMetadataServiceProvider(SubjectsMetaDataFormatFactory subjectMetaDataformatFactory) {
		this.subjectMetaDataformatFactory = subjectMetaDataformatFactory;
	}

	public SubjectsMetaDataFormatFactory getSubjectsMetaDataFormatFactory() {
		return subjectMetaDataformatFactory;
	}

	
	@GET
	@Path("/getAllSubjects/")
	@Produces(MediaType.APPLICATION_XML)
	public Subjects getAllSubjects() throws WebApplicationException {
		Subjects result = new Subjects();
		try {
			result = getSubjectsMetaDataFormatFactory().create().makeForAllSubjects();
		} catch(IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}	

}
