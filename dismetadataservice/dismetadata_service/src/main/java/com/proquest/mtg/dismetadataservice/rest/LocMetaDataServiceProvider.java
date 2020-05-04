package com.proquest.mtg.dismetadataservice.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import com.proquest.mtg.dismetadataservice.loc.LOCFormat;
import com.proquest.mtg.dismetadataservice.pqloc.CreateNewClaimInput;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@Path("/loc/")
public class LocMetaDataServiceProvider {
	
	private final LOCFormat locFormat;
	private final String ecmsMr3HeaderKey;
	private final String ecmsMr3HeaderValue;	
	private final String mr3ServiceUrlBase;
	private final String ecmsServiceUrlBase;

	public LOCFormat getLocFormat() {
		return locFormat;
	}
	
	public String getECMSMr3HeaderKey() {
		return ecmsMr3HeaderKey;
	}

	public String getECMSMr3HeaderValue() {
		return ecmsMr3HeaderValue;
	}

	public String getMr3ServiceUrlBase() {
		return mr3ServiceUrlBase;
	}
	
	public String getECMSServiceUrlBase() {
		return ecmsServiceUrlBase;
	}
	
	@Inject
	public LocMetaDataServiceProvider(LOCFormat locFormat, 
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_KEY) String ecmsMr3HeaderKey,
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_VALUE) String ecmsMr3HeaderValue,
			@Named(DisMetadataProperties.MR3_SERVICE_URL_BASE) String mr3ServiceUrlBase,
			@Named(DisMetadataProperties.ECMS_SERVICE_URL_BASE) String ecmsServiceUrlBase) {
		this.locFormat = locFormat;
		this.ecmsMr3HeaderKey = ecmsMr3HeaderKey;
		this.ecmsMr3HeaderValue = ecmsMr3HeaderValue;
		this.mr3ServiceUrlBase = mr3ServiceUrlBase;
		this.ecmsServiceUrlBase = ecmsServiceUrlBase;
	}
	
//	//CBDELETE
//	@GET
//	@Path("/metadataForEligiblePubs/")
//	@Produces(MediaType.APPLICATION_XML)
//	public CreateNewClaimInput getLOCDataForAllEligiblePubs() throws WebApplicationException {
//		CreateNewClaimInput result = null;
//		try {
//			result = getLocFormat().makeForEligiblePubs(0);
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
	
	@GET
	@Path("/metadataFor/{pubNumber}")
	@Produces(MediaType.APPLICATION_XML)
	public CreateNewClaimInput getLOCDataFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
		CreateNewClaimInput result = null;
		ClientResponse response = null;
		try {
			//------------
			String URL = getECMSServiceUrlBase();
			// System.out.println("ECMS URL :" +URL);
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue();
			Client c = Client.create();
			WebResource resource = c.resource(URL + "/" + pubNumber);
			response = resource.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.header(HEADERKEY, HEADERVALUE)
					.get(ClientResponse.class);
			if (response.getStatus() == 404) {
				System.out.println("404 ERROR IN LOC ECMS/MR3 CALL.  URL: " + resource);
				throw new Exception("404.  Missing ECMS data");
			}

			InputStream is = response.getEntityInputStream();
			String line;
			StringBuilder text = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				text.append(line).append("  ");
				// System.out.println("LINE :" +line);
			}
			String pakId = text.toString();
			// System.out.println("DATA :" +pakId);
			String ecmsData = "";
			int startIndex = pakId.indexOf("<IngestRecord");
			String startToBeReplaced = pakId.substring(0, startIndex);
			ecmsData = pakId.replace(startToBeReplaced, "");
			int endIndex = ecmsData.indexOf("</IngestRecord");
			ecmsData = ecmsData.substring(0, endIndex + 15);
			// System.out.println("NEW PAKID AFTER REMOVE :" + ecmsData);

			if (ecmsData.isEmpty() || ecmsData == null) {
				response.setStatus(404);

			}
			is.close();
			if (response.getStatus() == 500) {
				System.out.println("500 ERROR IN LOC ECMS/MR3 DATA PARSE.  URL: " + resource);
				throw new Exception("500.  Server Error");
			}

			URL = getMr3ServiceUrlBase();
			// System.out.println("MR3 URL :" +URL);
			HEADERKEY = getECMSMr3HeaderKey();
			HEADERVALUE = getECMSMr3HeaderValue();
			c = Client.create();
			resource = c.resource(URL + "/dissertation/titles/" + pubNumber);
			response = resource.header("Content-Type", "application/json").header("Accept", "application/json")
					.header(HEADERKEY, HEADERVALUE).get(ClientResponse.class);
			if (response.getStatus() == 404) {
				System.out.println("404 ERROR IN LOC ECMS/MR3 CALL.  URL: " + resource);
				throw new Exception("404.  Missing MR3 data");
			}
			if (response.getStatus() == 500) {
				System.out.println("500 ERROR IN LOC MR3 PARSE.  URL: " + resource);
				throw new Exception("500.  Server Error");
			}
			if (response.getStatus() == 200) {
				InputStream mr3is = response.getEntityInputStream();
				StringBuilder mr3text = new StringBuilder();
				BufferedReader mr3reader = new BufferedReader(new InputStreamReader(mr3is));
				while ((line = mr3reader.readLine()) != null) {
					mr3text.append(line).append("  ");
				}
				String mr3Data = mr3text.toString();
				// System.out.println("FOUND MR3 DATA :" +mr3Data);
				mr3is.close();

				if ((!mr3Data.isEmpty()) && (!ecmsData.isEmpty())) {
					result = getLocFormat().makeFor(ecmsData,mr3Data,0);
				}
			}
			//------------
			
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}

	@PUT
	@Path("/ackClaimSubmissionFor/{pubNumber}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ackLOCClaimSubmissionFor(@PathParam("pubNumber") String pubNumber) throws WebApplicationException {
		ClientResponse response = null;
		return Response.status(Response.Status.GONE).build();
//		try {
//			String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
//			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//			String dateString = sdf.format(new Date());
//			String jsonString = new JSONObject().put("CopyrightDateSubmitted", dateString).toString();
//			Map<String, String> map = (new Genson()).deserialize(jsonString, Map.class);
//			
//            String URL = getMr3ServiceUrlBase();
//			String HEADERKEY = getECMSMr3HeaderKey();
//			String HEADERVALUE = getECMSMr3HeaderValue(); 
//			Client c = Client.create();
//			WebResource resource = c.resource(URL).path("loc").path("cpsubmitted").path(pubNumber);
//			response = resource.header("Content-Type", "application/json")
//                    	.header(HEADERKEY, HEADERVALUE)
//                    	.post(ClientResponse.class, map);
//			if (response.getStatus() != 204)
//				System.out.println("MR3 cpsubmitted responded with: " + response.getStatus());
//			return Response.status(response.getStatus()).build();
//		} catch (IllegalArgumentException e) {
//			throw new DisServiceException(Response.Status.NO_CONTENT);
//		}catch (Exception e) {
//			e.printStackTrace();
//			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
//		}
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
			String jsonString = new JSONObject().put("CopyrightDateSubmitted", dateString).toString();
			Map<String, String> map = (new Genson()).deserialize(jsonString, Map.class);
			
	        String URL = getMr3ServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue(); 
			Client c = Client.create();
			WebResource resource = c.resource(URL).path("loc").path("cpsubmitted").path(pubNumber);
			response = resource.header("Content-Type", "application/json")
	                	.header(HEADERKEY, HEADERVALUE)
	                	.type("application/json")
	                	.post(ClientResponse.class, map);
			if (response.getStatus() != 204)
				System.out.println("MR3 cpSubmitted responded with: " + response.getStatus());
			return Response.status(response.getStatus()).build();
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
