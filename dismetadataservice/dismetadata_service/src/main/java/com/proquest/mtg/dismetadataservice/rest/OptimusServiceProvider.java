package com.proquest.mtg.dismetadataservice.rest;

import java.io.BufferedReader;
import java.io.Console;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestParameters;
//import com.proquest.mtg.dismetadataservice.format.SchoolMetaDataFormatFactory;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools;
import com.proquest.optimus.security.SharedKeyAuthorization;
import com.proquest.optimus.security.service.SharedKeyAuthorizationService;

@Path("/optimusdata/")
public class OptimusServiceProvider {

	// Added by Roeland
	/*
	Authorization:SharedKey 
	PublicAccessAPIKey="RLRFC83WE2NVK7LA",
	Signature="gv9KcBlIXIBPgWLVQBpZPwqZPHLOSGL8kZtqZBz/1LCaINGdI+CYTBbQIRivoJNspDA5KEpFPAQdABgEwh7h6w==",
	SignatureAlgorithm="HMAC-SHA512",
	Timestamp="1572355506",
	Nonce="UD5SfiXLeXga4J4PHpO7s8LPrjUdi3Wg",
	Version="1.0"
	Content-Type:application/json
	Accept:application/json
	*/
	@GET
	@Path("/getReferenceCountsTotalsPre")
	@Produces(MediaType.TEXT_PLAIN)
	// public String getRefCountsTotalsFromOptimus(@QueryParam("vendor") String vendor, @QueryParam("startDate") String startDate, @QueryParam("pubNumbers") List<String> pubNumbers) throws WebApplicationException {
	public String getRefCountsTotalsFromOptimusPre() throws WebApplicationException {
		String response = null;
		//                  "https://optimus-pipeline-service.prod.proquest.com/optimus-pipeline-service/vendors/INNODATA/reference-counts-summary"
		//                      https://optimus-pipeline-service.pre.int.proquest.com/optimus-pipeline-service/vendors/INNODATA/reference-counts",
		String optimusPreUrl = "https://optimus-pipeline-service.pre.int.proquest.com/optimus-pipeline-service/vendors/INNODATA/reference-counts-summary";
		// String optimusPreUrl = String.format("https://optimus-pipeline-service.prod.int.proquest.com/optimus-pipeline-service/vendors/%1$s/reference-counts-summary", vendor);
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
			        	    "RLRFC83WE2NVK7LA",
				            "gv9KcBlIXIBPgWLVQBpZPwqZPHLOSGL8kZtqZBz/1LCaINGdI+CYTBbQIRivoJNspDA5KEpFPAQdABgEwh7h6w==",
			                "POST",
			                optimusPreUrl,
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection(optimusPreUrl,"application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);
			// String url = String.format("{`dateSearchStart`: `%1$s`, `pubNumbers`: [`22588181`,`22624858`]}", startDate).replace('`', '"');
			String url = "{\"dateSearchStart\": \"20191024\", \"pubNumbers\": [\"22588181\",\"22624858\"]}";
			
			// String url = String.format("{`dateSearchStart`: `%1$s`, `pubNumbers`: %2$s}", startDate, pubNumbers).replace('`', '"');
			// String url = "{\"dateSearchStart\": \"" + startDate + "\", \"pubNumbers\": " + pubNumbers + "}";
			response = processResponse(urlConnection, url);
		} catch(IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return response;
	}

	@GET
	@Path("/getReferenceCountsTotals")
	@Produces(MediaType.TEXT_PLAIN)
	// public String getRefCountsTotalsFromOptimus(@QueryParam("vendor") String vendor, @QueryParam("startDate") String startDate, @QueryParam("pubNumbers") List<String> pubNumbers) throws WebApplicationException {
	public String getRefCountsTotalsFromOptimus() throws WebApplicationException {
		String response = null;
		//                  "https://optimus-pipeline-service.prod.proquest.com/optimus-pipeline-service/vendors/INNODATA/reference-counts-summary"
		String optimusUrl = "https://optimus-pipeline-service.prod.proquest.com/optimus-pipeline-service/vendors/INNODATA/reference-counts-summary";
		// String optimusUrl = String.format("https://optimus-pipeline-service.prod.int.proquest.com/optimus-pipeline-service/vendors/%1$s/reference-counts-summary", vendor);
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
			        	    "EL4B9BCEYCD24VVH",
				            "hBBYbQVNnrE43rfZnQwywxuRnXj6KHR77pqdKamGsD7Km24apP8FaVaQA6ssrw8R",
			                "POST",
			                optimusUrl,
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection(optimusUrl,"application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);
			// String url = String.format("{`dateSearchStart`: `%1$s`, `pubNumbers`: [`22588181`,`22624858`]}", startDate).replace('`', '"');
			String url = "{\"dateSearchStart\": \"20191024\", \"pubNumbers\": [\"22588181\",\"22624858\"]}";
			
			// String url = String.format("{`dateSearchStart`: `%1$s`, `pubNumbers`: %2$s}", startDate, pubNumbers).replace('`', '"');
			// String url = "{\"dateSearchStart\": \"" + startDate + "\", \"pubNumbers\": " + pubNumbers + "}";
			response = processResponse(urlConnection, url);
		} catch(IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return response;
	}

	/*
	@GET
	@Path("/getReferenceRejectsTotals")
	@Produces(MediaType.TEXT_PLAIN)
	public String getReferenceRejectsTotalsFromOptimus(@QueryParam("vendor") String vendor, @QueryParam("startDate") String startDate, @QueryParam("pubNumbers") List<String> pubNumbers) throws WebApplicationException {
		String response = null;
		String optimusUrl = String.format("https://optimus-pipeline-service.prod.int.proquest.com/optimus-pipeline-service/vendors/%1$s/reference-rejects-counts-summary", vendor);
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
			        	    "EL4B9BCEYCD24VVH",
				            "hBBYbQVNnrE43rfZnQwywxuRnXj6KHR77pqdKamGsD7Km24apP8FaVaQA6ssrw8R",
			                "POST",
			                optimusUrl,
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection(optimusUrl,"application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);
			String url = String.format("{`dateSearchStart`: `%1$s`, `pubNumbers`: %2$s}", startDate, pubNumbers).replace('`', '"');
			// String url = "{\"dateSearchStart\": \"" + startDate + "\", \"pubNumbers\": " + pubNumbers + "}";
			response = processResponse(urlConnection, url);
		} catch(IllegalArgumentException e) {
			throw new DisServiceException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return response;
	}
	*/
	@GET
	@Path("/getReferenceCounts")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRefCountsInfoFromOptimus(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate, @QueryParam("pubNumbers") List<String> pubNumbers) throws WebApplicationException {
		String response = null;
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
			        	    "EL4B9BCEYCD24VVH",
				            "hBBYbQVNnrE43rfZnQwywxuRnXj6KHR77pqdKamGsD7Km24apP8FaVaQA6ssrw8R",
			                "POST",
			                "https://optimus-pipeline-service.prod.int.proquest.com/optimus-pipeline-service/vendors/INNODATA/reference-counts",
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection("https://optimus-pipeline-service.prod.int.proquest.com/optimus-pipeline-service/vendors/INNODATA/reference-counts","application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);
			String url = "{\"dateCreatedStart\"" + ":" + "\"" + startDate + "\"," + "\"dateCreatedEnd\"" + ":" + "\"" + endDate + "\"," +  "\"pubNumbers\": "+ pubNumbers + "}";
			//response = processResponse(urlConnection, "{\"dateCreatedStart\":\"20160413\"}");
			//response =  processResponse(urlConnection, "{\"dateCreatedStart\":\"20160413\",\"pubNumbers\":[\"1014308071\",\"1014308431\"]}");
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
		try {
			Map<String, String[]> parameterMap = new HashMap<String,String[]>();
			SharedKeyAuthorizationService sharedKeyAuthorizationService =	
			        new SharedKeyAuthorizationService(
			        	    "EL4B9BCEYCD24VVH",
				            "hBBYbQVNnrE43rfZnQwywxuRnXj6KHR77pqdKamGsD7Km24apP8FaVaQA6ssrw8R",
			                "POST",
			                "https://optimus-pipeline-service.prod.int.proquest.com/optimus-pipeline-service/vendors/INNODATA/reference-rejects",
			                parameterMap);
			 
			SharedKeyAuthorization sharedKeyAuthorization = sharedKeyAuthorizationService.build();
			
			System.out.println("signature:"+sharedKeyAuthorization.getSignature());
			
			URLConnection urlConnection = buildUrlConnection("https://optimus-pipeline-service.prod.int.proquest.com/optimus-pipeline-service/vendors/INNODATA/reference-rejects","application/json", "application/json", sharedKeyAuthorization.getSignature(), sharedKeyAuthorization);
			String url = "{\"dateCreatedStart\"" + ":" + "\"" + startDate + "\"," + "\"dateCreatedEnd\"" + ":" + "\"" + endDate + "\"," + "\"pubNumbers\": "+ pubNumbers + "}";
			//response = processResponse(urlConnection, "{\"dateCreatedStart\":\"20160413\",\"dateCreatedEnd\":\"20160418\",\"pubNumbers\":[\"1014308071\",\"1014308431\"]}");
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
