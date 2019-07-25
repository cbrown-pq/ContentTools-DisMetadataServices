package com.proquest.mtg.dismetadataservice.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.owlike.genson.Genson;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@Path("/loc/")
public class LocMetaDataServiceProvider {
	
//	private final LOCFormat locFormat;
	private final String ecmsMr3HeaderKey;
	private final String ecmsMr3HeaderValue;	
	private final String mr3ServiceUrlBase;

//	public LOCFormat getLocFormat() {
//		return locFormat;
//	}
	
	public String getECMSMr3HeaderKey() {
		return ecmsMr3HeaderKey;
	}

	public String getECMSMr3HeaderValue() {
		return ecmsMr3HeaderValue;
	}

	public String getMr3ServiceUrlBase() {
		return mr3ServiceUrlBase;
	}
	
	@Inject
	public LocMetaDataServiceProvider( 
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_KEY) String ecmsMr3HeaderKey,
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_VALUE) String ecmsMr3HeaderValue,
			@Named(DisMetadataProperties.MR3_SERVICE_URL_BASE) String mr3ServiceUrlBase) {
//		this.locFormat = locFormat;
		this.ecmsMr3HeaderKey = ecmsMr3HeaderKey;
		this.ecmsMr3HeaderValue = ecmsMr3HeaderValue;
		this.mr3ServiceUrlBase = mr3ServiceUrlBase;
	}
	
	//CBDELETE
	/*@GET
	@Path("/metadataForEligiblePubs/")
	@Produces(MediaType.APPLICATION_XML)
	public CreateNewClaimInput getLOCDataForAllEligiblePubs() throws WebApplicationException {
		CreateNewClaimInput result = null;
		try {
			result = getLocFormat().makeForEligiblePubs(0);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.NO_CONTENT);*/ /*As per standard it shouldn't contain a message */
		/*} catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}*/
	
//	@GET
//	@Path("/metadataFor/{pubNumber}")
//	@Produces(MediaType.APPLICATION_XML)
//	public CreateNewClaimInput getLOCDataFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
//		CreateNewClaimInput result = null;
//		try {
//			result = getLocFormat().makeFor(pubNumber,"TEMPSTUB",0);
//		} catch(IllegalArgumentException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
//		}
//		return result;
//	}

	@PUT
	@Path("/ackClaimSubmissionFor/{pubNumber}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ackLOCClaimSubmissionFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
		ClientResponse response = null;
		try {
			String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String dateString = sdf.format(new Date());
			String jsonString = new JSONObject().put("CopyrightDateSubmitted", dateString).toString();
			Map<String, String> map = (new Genson()).deserialize(jsonString, Map.class);
			
            String URL = getMr3ServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue(); 
			Client c = Client.create();
			WebResource resource = c.resource(URL).path("loc").path("cpsubmitted").path(pubNumber);
			response = resource.header("Content-Type", "application/json")
                    	.header(HEADERKEY, HEADERVALUE)
                    	.post(ClientResponse.class, map);
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity("SUCCESS").build();
	}
	
	@GET
	@Path("/ackClaimSubmissionFor/{pubNumber}")
	@Produces(MediaType.TEXT_PLAIN) //TODO remove
	public Response getAckLOCClaimSubmissionFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
		ClientResponse response = null;
		try {
			String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String dateString = sdf.format(new Date());
//			String dateString = "2019-04-21T15:33:00.682Z";
			String jsonString = new JSONObject().put("CopyrightDateSubmitted", dateString).toString();
			Map<String, String> map = (new Genson()).deserialize(jsonString, Map.class);
			
            String URL = getMr3ServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue(); 
			Client c = Client.create();
			WebResource resource = c.resource(URL).path("loc").path("cpsubmitted").path(pubNumber);
			response = resource.header("Content-Type", "application/json")
                    	.header(HEADERKEY, HEADERVALUE)
                    	.post(ClientResponse.class, map);
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity("SUCCESS").build();
	}
	
	@PUT
	@Path("/ackDeliverySubmissionFor/{pubNumber}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ackLOCDeliverySubmissionFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
		ClientResponse response = null;
		try {
			String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String dateString = sdf.format(new Date());
			String jsonString = new JSONObject().put("LOCFirstLiveDate", dateString).toString();
			Map<String, String> map = (new Genson()).deserialize(jsonString, Map.class);
			
	        String URL = getMr3ServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue(); 
			Client c = Client.create();
			WebResource resource = c.resource(URL).path("loc").path("locsent").path(pubNumber);
			response = resource.header("Content-Type", "application/json")
	                	.header(HEADERKEY, HEADERVALUE)
	                	.type("application/json")
	                	.post(ClientResponse.class, map);
			System.out.println("MR3 locsent responded with: " + response.getStatus());
			return Response.status(response.getStatus()).build();
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GET
	@Path("/ackDeliverySubmissionFor/{pubNumber}")
	@Produces(MediaType.TEXT_PLAIN) //TODO  remove
	public Response getAckLOCDeliverySubmissionFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
//		try {
//			getLocFormat().updateLOCDeliverySubmissionFor(pubNumber);
//		} catch (Exception e) {
//			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
//		}
//		return Response.status(Response.Status.OK).entity("SUCCESS").build();		
		ClientResponse response = null;
		try {
			String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String dateString = sdf.format(new Date());
//			String dateString = "2019-04-21T15:33:00.682Z";
			String jsonString = new JSONObject().put("LOCFirstLiveDate", dateString).toString();
			Map<String, String> map = (new Genson()).deserialize(jsonString, Map.class);
			
            String URL = getMr3ServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue(); 
			Client c = Client.create();
			WebResource resource = c.resource(URL).path("loc").path("locsent").path(pubNumber);
			response = resource.header("Content-Type", "application/json")
                    	.header(HEADERKEY, HEADERVALUE)
                    	.type("application/json")
                    	.post(ClientResponse.class, map);
			System.out.println("MR3 locsent responded with: " + response.getStatus());
			return Response.status(response.getStatus()).build();
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
