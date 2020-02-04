package com.proquest.mtg.dismetadataservice.rest;

import java.io.BufferedReader;
// import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
// import javax.ws.rs.PUT;
import javax.ws.rs.Path;
// import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.name.Named;
import com.google.inject.Inject;
// import com.google.inject.servlet.RequestParameters;
// import com.proquest.mtg.dismetadataservice.format.SchoolMetaDataFormatFactory;
// import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools;
import com.proquest.optimus.security.SharedKeyAuthorization;
import com.proquest.optimus.security.service.SharedKeyAuthorizationService;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.proquest.mtg.dismetadataservice.rest.DisServiceException;


@Path("/optimusdata/")
public class OptimusServiceProvider {
 
	private String optimusUrl;
	private String optimusKey;
	private String optimusSecretKey;

	@Inject
	public void OptimusServiceProviderProperties(
		@Named(DisMetadataProperties.OPTIMUS_URL_BASE) String optimusUrl,
		@Named(DisMetadataProperties.OPTIMUS_KEY) String optimusKey,
		@Named(DisMetadataProperties.OPTIMUS_SECRET_KEY) String optimusSecretKey) {
			this.optimusUrl = optimusUrl; 
			this.optimusKey = optimusKey; 
			this.optimusSecretKey = optimusSecretKey; 
	}
	
	@GET
	@Path("/getReferenceCountsTotals")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRefCountsTotalsFromOptimus(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate, @QueryParam("pubNumbers") List<String> pubNumbers) throws WebApplicationException {
		String response = null;
		String optimusRefSumUrl = String.join("/", this.optimusUrl, "reference-counts-summary");
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
							this.optimusKey,
							this.optimusSecretKey,
			                "POST",
							optimusRefSumUrl,
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection(optimusRefSumUrl,"application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);
			String url = String.format("{`dateSearchStart`: `%1$s`, `dateSearchEnd`: `%2$s`, `pubNumbers`: %3$s}", startDate, endDate, pubNumbers).replace('`', '"');
			response = processResponse(urlConnection, url);
		} catch(IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return response;
	}

	@GET
	@Path("/getReferenceRejectsTotals")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRefRejectsTotalsFromOptimus(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate, @QueryParam("pubNumbers") List<String> pubNumbers) throws WebApplicationException {
		String response = null;
		String optimusRejSumUrl = String.join("/", this.optimusUrl, "reference-rejects-counts-summary");
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
							this.optimusKey,
							this.optimusSecretKey,
			                "POST",
			                optimusRejSumUrl,
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection(optimusRejSumUrl,"application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);
			String url = String.format("{`dateSearchStart`: `%1$s`, `dateSearchEnd`: `%2$s`, `pubNumbers`: %3$s}", startDate, endDate, pubNumbers).replace('`', '"');
			response = processResponse(urlConnection, url);
		} catch(IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return response;
	}

	@GET
	@Path("/getReferenceCounts")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRefCountsInfoFromOptimus(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate, @QueryParam("pubNumbers") List<String> pubNumbers) throws WebApplicationException {
		String response = null;
		String optimusRefCntUrl = String.join("/", this.optimusUrl, "reference-counts");
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
							this.optimusKey,
							this.optimusSecretKey,
			                "POST",
			                optimusRefCntUrl,
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection(optimusRefCntUrl,"application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);
			String url = String.format("{`dateSearchStart`: `%1$s`, `dateSearchEnd`: `%2$s`, `pubNumbers`: %3$s}", startDate, endDate, pubNumbers).replace('`', '"');
			response = processResponse(urlConnection, url);
		} catch(IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return response;
	}
	
	@GET
	@Path("/getReferenceRejects")
	@Produces(MediaType.TEXT_PLAIN)
	public String getReferenceRejects(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate, @QueryParam("pubNumbers") List<String> pubNumbers) throws WebApplicationException {
		String response = null;
		String optimusRefRejUrl = String.join("/", this.optimusUrl, "reference-rejects");
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
							this.optimusKey,
							this.optimusSecretKey,
			                "POST",
			                optimusRefRejUrl,
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection(optimusRefRejUrl, "application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);
			String url = String.format("{`dateSearchStart`: `%1$s`, `dateSearchEnd`: `%2$s`, `pubNumbers`: %3$s}", startDate, endDate, pubNumbers).replace('`', '"');
			response = processResponse(urlConnection, url);
		} catch(IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT); /*As per standard it shouldn't contain a message */
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return response;
	}
	
	
/*	@GET
	@Path("/getDissertations/{startDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDissertations(@PathParam("startDate") String startDate) throws WebApplicationException {
		String response = null;
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
			                "ME6K5WY83ESRX62S",
			                "mtdRctMeKfjbNx5vTWmdnByhnZ88cykMXreQ793TpBQ8P56tBnsA5dA38nw3KUxw",
			                "POST",
			                "http://10.241.16.112:8080/optimus-pipeline-service/dissertations",
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection("http://10.241.16.112:8080/optimus-pipeline-service/dissertations	","application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);

			response = processResponse(urlConnection, "{\"dateCreatedStart\":\"20160413\",\"dateCreatedEnd\":\"20160418\",\"pubNumbers\":[\"10610665\",\"10195668\"]}");
			
			System.out.println("response:" + response);
		} catch(IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT); As per standard it shouldn't contain a message 
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return response;
	}*/
	
	public static URLConnection buildUrlConnection(String path, String contentType, String accept, String computedSignature, SharedKeyAuthorization sharedKeyAuthorization)
            throws Exception {

				URL url = new URL(path);
				URLConnection urlConnection = url.openConnection();
				((HttpURLConnection) urlConnection).setRequestMethod(sharedKeyAuthorization.getVerb());
				
				urlConnection.setRequestProperty("User-Agent", "junit");
				urlConnection.setRequestProperty("Content-Type", contentType);
				urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
				urlConnection.setRequestProperty("Accept", accept);
				urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("SharedKey ");
				stringBuilder.append("PublicAccessAPIKey=\"").append(sharedKeyAuthorization.getPublicAccessAPIKey()).append("\",");
				stringBuilder.append("Signature=\"");
				stringBuilder.append(computedSignature);
				stringBuilder.append("\",");
				stringBuilder.append("SignatureAlgorithm=\"HMAC-SHA512\",");
				stringBuilder.append("Timestamp=\"");
				stringBuilder.append(sharedKeyAuthorization.getEpochTimestamp());
				stringBuilder.append("\",");
				stringBuilder.append("Nonce=\"");
				stringBuilder.append(sharedKeyAuthorization.getNonce());
				stringBuilder.append("\",");
				stringBuilder.append("Version=\"1.0\"");
				System.out.println("STRING::" + stringBuilder.toString());
				urlConnection.setRequestProperty("Authorization", stringBuilder.toString());
				
				return urlConnection;
}

	 		public static String processResponse(URLConnection urlConnection, String requestBody) {

			String response;
			
			try {
			        OutputStream outputStream = null;
			        OutputStreamWriter outputStreamWriter = null;
			        BufferedReader bufferedReader;
			        String inputLine;
			        StringBuffer stringBuffer = new StringBuffer();
			        try {
			            urlConnection.setDoInput(true);
			            if (requestBody != null) {
			               urlConnection.setDoOutput(true);
			               outputStream = urlConnection.getOutputStream();
			               outputStreamWriter = new OutputStreamWriter(outputStream);
			               outputStreamWriter.write(requestBody);
			               outputStreamWriter.flush();
			            }
			            if (urlConnection instanceof HttpsURLConnection) {
			               bufferedReader = new BufferedReader(new InputStreamReader(((HttpsURLConnection) urlConnection).getInputStream()));
			               } else {
			               bufferedReader = new BufferedReader(new InputStreamReader(((HttpURLConnection) urlConnection).getInputStream()));
			            }
			            } catch (IOException ioException) {
			
			            if (urlConnection instanceof HttpsURLConnection) {
			               bufferedReader = new BufferedReader(new InputStreamReader(((HttpsURLConnection) urlConnection).getErrorStream()));
			            } else {
			               bufferedReader = new BufferedReader(new InputStreamReader(((HttpURLConnection) urlConnection).getErrorStream()));
			            }
			           }
			           while ((inputLine = bufferedReader.readLine()) != null) {
			                             stringBuffer.append(inputLine);
			             }
			           bufferedReader.close();
			
			           response = stringBuffer.toString();
			           System.out.println(response);
			           if (outputStream != null && outputStreamWriter != null) {
			              outputStreamWriter.close();
			              outputStream.close();
			           }
			} catch(Throwable throwable) {
			             throw new RuntimeException(throwable);
			}
			return response;
}

}
