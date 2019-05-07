package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import java.io.File;

//import org.json.JSONObject;
//import org.apache.commons.io.FileUtils;
import java.util.regex.Matcher;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.format.MetaDataFormatFactory;

import java.io.InputStream;
//import java.util.Properties;
import java.util.regex.Pattern;

import com.google.inject.name.Named;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/metadata/")
public class DisMetadataServiceProvider {
	private final MetaDataFormatFactory metaDataformatFactory;	
	private final String ecmsMr3HeaderKey;
	private final String ecmsMr3HeaderValue;	
	private final String mr3ServiceUrlBase;
	private final String ecmsServiceUrlBase;

	@Inject
	public DisMetadataServiceProvider(MetaDataFormatFactory metaDataformatFactory,			
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_KEY) String ecmsMr3HeaderKey,
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_VALUE) String ecmsMr3HeaderValue,
			@Named(DisMetadataProperties.MR3_SERVICE_URL_BASE) String mr3ServiceUrlBase,
			@Named(DisMetadataProperties.ECMS_SERVICE_URL_BASE) String ecmsServiceUrlBase) {
		this.metaDataformatFactory = metaDataformatFactory;
		this.ecmsMr3HeaderKey = ecmsMr3HeaderKey;
		this.ecmsMr3HeaderValue = ecmsMr3HeaderValue;
		this.ecmsServiceUrlBase = ecmsServiceUrlBase;
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
	
	public String getECMSServiceUrlBase() {
		return ecmsServiceUrlBase;
	}

	public MetaDataFormatFactory getMetaDataFormatFactory() {
		return metaDataformatFactory;
	}
	
	//static final String outDirPath = "C:\\Temp\\VMS";

	@GET
	@Path("/{pubNumber}/{formatType}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getDisMetaData(@PathParam("pubNumber") String pubNumber,
			@PathParam("formatType") String formatType,
			@DefaultValue("0") @QueryParam("er") int excludeRestriction,
			@DefaultValue("0") @QueryParam("ea") int excludeAbstract,
			@DefaultValue("0") @QueryParam("eaa") int excludeAltAbstract) throws WebApplicationException {
		String result = null;
        //String jsonStr = null;
        ClientResponse response = null;
		//Properties props = new Properties();
		try {
            String URL = getECMSServiceUrlBase();
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue();
			Client c = Client.create();
			WebResource resource = c.resource(URL+"/"+pubNumber);
			System.out.println("Web url :" +resource);
			response = resource.header("Content-Type", "application/json")
					    .header("Accept",  "application/json")
                    	.header(HEADERKEY, HEADERVALUE)
                    	.get(ClientResponse.class);
			InputStream is = response.getEntityInputStream();
			String line;
			StringBuilder text = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			while((line = reader.readLine()) != null) {
			    text.append(line).append("  ");
			}
			String pakId = text.toString();
			String ecmsData = "";
			Pattern pattern = Pattern.compile(".*value\":\"(.*)\"}}]");
			Matcher matcher = pattern.matcher(pakId);
			if (matcher.find())
			{
				//System.out.println("FOUND :" +matcher.group(1));
				ecmsData = matcher.group(1);
			}
			 is.close();
			 
	            URL = "http://mr3.pre.int.proquest.com/mr3/fops/titles";
				HEADERKEY = getECMSMr3HeaderKey();
				HEADERVALUE = getECMSMr3HeaderValue();
				c = Client.create();
				resource = c.resource(URL+"/"+pubNumber);
				System.out.println("Web url :" +resource);
				response = resource.header("Content-Type", "application/json")
						    .header("Accept",  "application/json")
	                    	.header(HEADERKEY, HEADERVALUE)
	                    	.get(ClientResponse.class);
				InputStream mr3is = response.getEntityInputStream();
				StringBuilder mr3text = new StringBuilder();
				BufferedReader mr3reader = new BufferedReader(new InputStreamReader(mr3is));
				while((line = mr3reader.readLine()) != null) {
				    mr3text.append(line).append("  ");
				}
				String mr3Data = mr3text.toString();
				System.out.println("mr3 out" +mr3Data);
				
			result = getMetaDataFormatFactory().getFor(formatType).makeFor(ecmsData, mr3Data, excludeRestriction, excludeAbstract, excludeAltAbstract);
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.NOT_FOUND,e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return Response.status(Response.Status.OK).entity(result).build();
	}

}