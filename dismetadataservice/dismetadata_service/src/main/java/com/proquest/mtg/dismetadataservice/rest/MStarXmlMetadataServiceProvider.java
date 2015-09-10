package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.format.MStarMetaDataFormatFactory;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation;


@Path("/mstar/")
public class MStarXmlMetadataServiceProvider {
	private final MStarMetaDataFormatFactory mStarMetaDataFormatFactory;

	@Inject
	public MStarXmlMetadataServiceProvider(
			MStarMetaDataFormatFactory mStarMetaDataFormatFactory) {
		this.mStarMetaDataFormatFactory = mStarMetaDataFormatFactory;
	}

	public MStarMetaDataFormatFactory getMStarMetaDataFormatFactory() {
		return mStarMetaDataFormatFactory;
	}

	@GET
	@Path("/dissinfo/{pubNum}")
	@Produces(MediaType.APPLICATION_XML)
	public Dissertation getMstarMetaData(@PathParam("pubNum") String pubNum)
			throws WebApplicationException {
		Dissertation result;
		try {
			result = getMStarMetaDataFormatFactory().create()
					.makeForDissertation(pubNum);
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(
					Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
}
