package com.proquest.mtg.dismetadataservice.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.proquest.mtg.dismetadataservice.loc.LOCReportPubJson;
import com.proquest.mtg.dismetadataservice.loc.LOCReportPubListCR;
import com.proquest.mtg.dismetadataservice.loc.LOCReportPubListNonCR;
//import com.proquest.mtg.dismetadataservice.locreports.CopyrightPubReportProvider;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@Path("/locreports/")
public class LocReportServiceProvider {
	
//	private CopyrightPubReportProvider copyrightPubReportProvider;
	private final String ecmsMr3HeaderKey;
	private final String ecmsMr3HeaderValue;	
	private final String mr3ServiceUrlBase;
	public static final String kPubSeparator = ",";
	
	@Inject
	public LocReportServiceProvider(/*CopyrightPubReportProvider copyrightPubReportProvider,*/ 
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_KEY) String ecmsMr3HeaderKey,
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_VALUE) String ecmsMr3HeaderValue,
			@Named(DisMetadataProperties.MR3_SERVICE_URL_BASE) String mr3ServiceUrlBase) {
//		this.copyrightPubReportProvider = copyrightPubReportProvider;
		this.ecmsMr3HeaderKey = ecmsMr3HeaderKey;
		this.ecmsMr3HeaderValue = ecmsMr3HeaderValue;
		this.mr3ServiceUrlBase = mr3ServiceUrlBase;
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
	
	@GET
	@Path("/copyrightpubs/{formatType}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEligibleCopyrightPubs(@PathParam("formatType") String formatType) throws WebApplicationException {
		String mr3FormatType = formatType.equals("105mm") ? "MFC" : "MFL";
		ClientResponse response = null;
		List<LOCReportPubJson> result = Lists.newArrayList();
		try {
            String URL = getMr3ServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue(); 
			Client c = Client.create();
			WebResource resource = c.resource(URL).path("loc").path("getCopyrightedFilmPubs")
					.queryParam("filmType", mr3FormatType);
			response = resource.header("Content-Type", "application/json")
                    	.header(HEADERKEY, HEADERVALUE)
                    	.get(ClientResponse.class);
			 String xmlStr = response.getEntity(String.class);
			 
			 LOCReportPubListCR pubList;
			 // handle when MR3 returns "No matching dissertations found:"
			 if (response.getStatus() == 404 && StringUtils.isNotBlank(xmlStr) && !xmlStr.startsWith("<")) { //TODO probably need better error handling here
				 pubList = new LOCReportPubListCR();
				 return Response.status(200).entity(pubList.makeJsonList()).build();
			} else {
				JAXBContext jaxbContext = JAXBContext.newInstance(LOCReportPubListCR.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(xmlStr);
				pubList = (LOCReportPubListCR) unmarshaller.unmarshal(reader);
			}
			 result = pubList.makeJsonList();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(response.getStatus()).entity(result).build();
	}
	
	
	@GET
	@Path("/noncopyrightpubs/{formatType}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLOCDataForAllNonCopyrightPubs(@PathParam("formatType") String formatType) throws WebApplicationException {
		String mr3FormatType = formatType.equals("105mm") ? "MFC" : "MFL";
		ClientResponse response = null;
		List<LOCReportPubJson> result = Lists.newArrayList();
		try {
            String URL = getMr3ServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue(); 
			Client c = Client.create();
			WebResource resource = c.resource(URL).path("loc").path("getNonCopyrightedFilmPubs")
					.queryParam("filmType", mr3FormatType);
			response = resource.header("Content-Type", "application/json")
                    	.header(HEADERKEY, HEADERVALUE)
                    	.get(ClientResponse.class);
			 String xmlStr = response.getEntity(String.class);
			 
			 JAXBContext jaxbContext = JAXBContext.newInstance(LOCReportPubListNonCR.class);
			 Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			 StringReader reader = new StringReader(xmlStr);
			 LOCReportPubListNonCR pubList = (LOCReportPubListNonCR) unmarshaller.unmarshal(reader);
			 result = pubList.makeJsonList();
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(response.getStatus()).entity(result).build();
	}
	
	@POST
	@Path("ackDownload")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response ackDownload(@Context HttpServletRequest request, String data) throws WebApplicationException {
		ClientResponse response = null;
		try {
			JSONArray dataArray = new JSONArray(data);
			
            String URL = getMr3ServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue(); 
			Client c = Client.create();
			WebResource resource = c.resource(URL).path("loc").path("locFilmSent");
			response = resource.header("Content-Type", "application/json")
                    	.header(HEADERKEY, HEADERVALUE)
                    	.post(ClientResponse.class, jsonToArrayList(dataArray));
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(response.getStatus()).build();
	}
	
	private ArrayList<String> jsonToArrayList (JSONArray json) throws JSONException {
		ArrayList<String> listdata = new ArrayList<String>();     
		JSONArray jArray = (JSONArray)json; 
		if (jArray != null) { 
		   for (int i=0;i<jArray.length();i++){ 
		    listdata.add(jArray.getString(i));
		   } 
		}
		
		return listdata;
	}
	
}
