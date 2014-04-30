package com.proquest.mtg.dismetadataservice;

import java.net.HttpURLConnection;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class PutRequestClient {

	public static void main(String args[]) {
		String clientName = "schoolmetadata";
		String password = "pw4school";
		Client c = Client.create();
		c.addFilter(new HTTPBasicAuthFilter(clientName, password));
		WebResource resource = c.resource("http://localhost:8080/dismetadata_service/disout/schoolmetadata/ackSchoolMetadataLoadFor/0056");
        ClientResponse response = resource.put(ClientResponse.class, null);
	    
	    int statusCode = response.getStatus();
	    System.out.println("statusCode = " + statusCode);
	    String responseMsg;
	    if(statusCode == HttpURLConnection.HTTP_OK) {
	    	responseMsg = response.getEntity(String.class);
	    } else if(statusCode == HttpURLConnection.HTTP_NO_CONTENT) {
	    	responseMsg = null;
	    } else {
	    	responseMsg = response.getEntity(String.class);
	    }
	    System.out.println("responseMsg = " + responseMsg);
	}
}
