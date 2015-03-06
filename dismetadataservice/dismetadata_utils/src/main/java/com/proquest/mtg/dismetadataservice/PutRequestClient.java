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
		//c.addFilter(new HTTPBasicAuthFilter(clientName, password));
		//WebResource resource = c.resource("http://localhost:8080/dismetadata_service/disout/schoolmetadata/ackSchoolMetadataLoadFor/0056");
		WebResource resource = c.resource("http://umidos110.dc4.pqe:8001/dismetadata_service/disout/locreports/updatefilmpulldate?publist=3665621,3665637,3665643,3665645,3665647,3665648,3665650,3665658,3665663,3665747,3665748,3665752,3665755,3665756,3665761,3665783,3665784,3665786,3665787,3665792,3665800,3665806,3665809,3665814,3665817,3665819,3665823,3665826,3665827,3665829,3665830,3665835,3665841,3665846,3665850,3665853,3665854,3665863,3665865,3665867,3665868,3665870,3665875,3665882,3665883,3665884,3665885,3665886,3665887,3665888,3665889,3665890,3665898,3665901,3665905,3665909,3665910,3665911,3665912,3665918,3665921,3665925,3665927,3665930,3665935,3665936,3665941,3665943,3665946,3665948,3665949,3665953,3665960,3665966,3665967,3666090,3666118,3666120,3666121,3666126,3666129,3666132,3666134,3666136,3666141,3666145,3666146,3666147,3666155,3666157,3666158,3666163,3666164,3666167,3666168,3666170,3666174,3666179,3666181,3666184,3666185,3666186,3666187,3666198,3666201,3666202,3666203,3666204,3666212,3666214,3666215,3666220,3666221,3666222,3666224,3666227,3666231,3666233,3666235,3666236,3666487,3666570,3666748,3666751,3666757,3673896,3674106,3674120,3674123,3674128,3674133,3674135,3674144,3674149,3674153,3674203,3674210,3674211,3674212,3674214,3674215,3674216,3674222,3674223,3674234,3674236,3674237,3674243,3674268,3674290,3674299,3674310,3674327,3674328,3674329,3674341,3674372,3674473");
        ClientResponse response = resource.put(ClientResponse.class);
	    
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
