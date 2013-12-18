package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.PubMetaDataProvider;

@Path("/dispubmetadata/")
public class DisMetadataServiceProvider {
	private final PubMetaDataProvider pubMetadataProvider;

	@Inject
	public DisMetadataServiceProvider(PubMetaDataProvider pubMetadataProvider) {
		this.pubMetadataProvider = pubMetadataProvider;
	}

	public PubMetaDataProvider getPubMetadataProvider() {
		return pubMetadataProvider;
	}

	@GET
	@Path("/{pubNumber}/{formatType}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getDisMetaData(@PathParam("pubNumber") String pubNumber,
			@PathParam("formatType") String formatType) {
		String result = null;
		DisPubMetaData disPubMetadata;
		try {
			disPubMetadata = pubMetadataProvider
					.getPubMetaDataFor(pubNumber);
			result = "Pub Number: " + disPubMetadata.getPubNumber() + "\n" 	+ 
					 "Item Id: " + disPubMetadata.getItemId() + "\n" + 
					 "ISBN: " + disPubMetadata.getISBN() + "\n" + 
					 "Pub Page Number: "  + disPubMetadata.getPubPageNum() + "\n" + 
					 "Page Count: "	+ disPubMetadata.getPageCount() + "\n" +
					 "Format Type: " + formatType;
		} catch (NullPointerException e) {
			result = "No Data Found";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(result).build();

	}

}