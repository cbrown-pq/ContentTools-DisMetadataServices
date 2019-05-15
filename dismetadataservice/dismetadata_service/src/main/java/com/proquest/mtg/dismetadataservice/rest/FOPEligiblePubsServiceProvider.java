package com.proquest.mtg.dismetadataservice.rest;

//CBDELETE2
import java.util.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.inject.Inject;
import com.google.inject.name.Named;
//import com.proquest.mtg.dismetadataservice.format.FOPEligiblePubsFormatFactory;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONObject;


@Path("/foppubs/")
public class FOPEligiblePubsServiceProvider {
	
//	private final FOPEligiblePubsFormatFactory fopEligiblePubsFormatFactory;	
	private final String ecmsMr3HeaderKey;
	private final String ecmsMr3HeaderValue;	
	private final String mr3ServiceUrlBase;
	
	@Inject
	public FOPEligiblePubsServiceProvider(//FOPEligiblePubsFormatFactory fopEligiblePubsFormatFactory, 
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_KEY) String ecmsMr3HeaderKey,
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_VALUE) String ecmsMr3HeaderValue,
			@Named(DisMetadataProperties.MR3_SERVICE_URL_BASE) String mr3ServiceUrlBase) {
		//this.fopEligiblePubsFormatFactory = fopEligiblePubsFormatFactory;
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
	/*public FOPEligiblePubsFormatFactory getFOPEligiblePubsFormatFactory() {
		return fopEligiblePubsFormatFactory;
	}*/

	@SuppressWarnings("unused")
	@GET
	@Path("/getEligiblePubsList/{date}/")
	@Produces(MediaType.APPLICATION_XML)
	public Response getFOPEligiblePubsDataFor(@PathParam("date") String date) throws WebApplicationException {
		String jsonStr = null;
		ClientResponse response = null;
		Properties props = new Properties();
		try {
            String URL = getMr3ServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue(); 
			Client c = Client.create();
			WebResource resource = c.resource(URL+"?date="+date);
			response = resource.header("Content-Type", "application/xml")
                    	.header(HEADERKEY, HEADERVALUE)
                    	.get(ClientResponse.class);
			 jsonStr = response.getEntity(String.class);
			 JSONObject json = new JSONObject(Response.status(response.getStatus()).entity(jsonStr).build());
			 System.out.println("json result :" +json.toString(4));
			//result = getFOPEligiblePubsFormatFactory().get().getFOPEligiblePubs();
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(response.getStatus()).entity(jsonStr).build();
	}
	
/*	@GET
	@Path("/updateInProgressStatus/{pubID}/{Status}")
	public String updateInprogressStatusFor(@PathParam("pubID") String pubId, @PathParam("Status") String Status) throws WebApplicationException {
		String result;
		try {
			result = getFOPEligiblePubsFormatFactory().update().updateFFInprogressStatus(pubId, Status);
		} catch (IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}*/

}