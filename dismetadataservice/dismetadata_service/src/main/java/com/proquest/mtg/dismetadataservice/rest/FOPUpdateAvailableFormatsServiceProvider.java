package com.proquest.mtg.dismetadataservice.rest;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.proquest.mtg.dismetadataservice.format.FOPUpdateAvailablePubsFormatFactory;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

@Path("/fopformats/")
public class FOPUpdateAvailableFormatsServiceProvider {
	private FOPUpdateAvailablePubsFormatFactory fopUpdateAvailablePubsFormatFactory;
	private final String ecmsMr3HeaderKey;
	private final String ecmsMr3HeaderValue;	
	private final String mr3ServiceUrlBase;
	
	@Inject
		public FOPUpdateAvailableFormatsServiceProvider(FOPUpdateAvailablePubsFormatFactory fopUpdateAvailablePubsFormatFactor, 
				@Named(DisMetadataProperties.ECMS_MR3_HEADER_KEY) String ecmsMr3HeaderKey,
				@Named(DisMetadataProperties.ECMS_MR3_HEADER_VALUE) String ecmsMr3HeaderValue,
				@Named(DisMetadataProperties.MR3_SERVICE_URL_BASE) String mr3ServiceUrlBase) {
			this.fopUpdateAvailablePubsFormatFactory = fopUpdateAvailablePubsFormatFactory;
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
	public FOPUpdateAvailablePubsFormatFactory getFopUpdateAvailablePubsFormatFactory() {
		return fopUpdateAvailablePubsFormatFactory;
	}
		

@SuppressWarnings("unused")
@GET
@Path("/updateAvailableFormats/{pubID}/{format}")
public String updateInprogressStatusFor(@PathParam("pubID") String pubNumber, @PathParam("format") String format) throws WebApplicationException {
	String[] formats = format.split(",");
	String result = null;
	Properties props = new Properties();
	try {
		System.out.println("\nProcessing Pub-Id: " + pubNumber);
		for (String filmType : formats) {
			//result = getFopUpdateAvailablePubsFormatFactory().updateFOPFormatsQuery(pubNumber,format);
            String URL = getMr3ServiceUrlBase();
            String body = null;
            if(format.equalsIgnoreCase("MFL")) {
            	body = "[{\"Code\":\""+format+"\",\"Format\":\"Masterfilm\",\"Created\":true}]"; 
            	}
            else {
            	body = "[{\"Code\":\""+format+"\",\"Format\":\"Masterfiche\",\"Created\":true}]"; 
            	}
            System.out.println(body);
			String HEADERKEY = getECMSMr3HeaderKey();
			String HEADERVALUE = getECMSMr3HeaderValue();
			
			URL url = null;
			String newUrl =URL+"/"+pubNumber+"/formats";
			StringBuilder postData = new StringBuilder();
			postData.append(body);
			url = new URL(newUrl);
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.addRequestProperty(HEADERKEY, HEADERVALUE);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.getOutputStream().write(postDataBytes);
			
			int responseCode = conn.getResponseCode();
			if(responseCode == 204) {
				result = null;
				//result = "Update successful";
				}else {
					throw new Exception("Error!" + responseCode + conn.getResponseMessage());
					}
	            }
		} catch (Exception e) {
				e.printStackTrace();
				throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
				}
	return result;
	}

}