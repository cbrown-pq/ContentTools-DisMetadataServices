package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import javax.inject.Named;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.loc.LOCRecordFactory;
import com.proquest.mtg.dismetadataservice.pqloc.Claim;
import com.proquest.mtg.dismetadataservice.pqloc.Claims;
import com.proquest.mtg.dismetadataservice.pqloc.CreateNewClaimInput;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LOCMetaDataProvider {
	
	private final IPubMetaDataProvider pubMetaDataProvider;
	private final LOCRecordFactory locRecordFactory;
	private final IJdbcConnectionPool connectionPool;
	private final String ecmsMr3HeaderKey;
	private final String ecmsMr3HeaderValue;	
	private final String mr3ServiceUrlBase;

	@Inject
	public LOCMetaDataProvider(IPubMetaDataProvider pubMetaDataProvider,
			LOCRecordFactory locRecordFactory,
			@Named(IJdbcConnectionPool.kExodusConnectionPool) IJdbcConnectionPool connectionPool,
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_KEY) String ecmsMr3HeaderKey,
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_VALUE) String ecmsMr3HeaderValue,
			@Named(DisMetadataProperties.MR3_SERVICE_URL_BASE) String mr3ServiceUrlBase) {
		this.pubMetaDataProvider = pubMetaDataProvider;
		this.locRecordFactory = locRecordFactory;
		this.connectionPool = connectionPool;
		this.ecmsMr3HeaderKey = ecmsMr3HeaderKey;
		this.ecmsMr3HeaderValue = ecmsMr3HeaderValue;
		this.mr3ServiceUrlBase = mr3ServiceUrlBase;
	}

	public IPubMetaDataProvider getPubMetaDataProvider() {
		return pubMetaDataProvider;
	}

	public LOCRecordFactory getLOCRecordFactory() {
		return locRecordFactory;
	}

	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
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
	
	//CBDELETE
	/*public CreateNewClaimInput getEligibleLOCData(int excludeRestriction) throws Exception {
		
		List<String> pubIds;
		try (Connection connection = getConnectionPool().getConnection();
				LOCPubSelectionQuery query = new LOCPubSelectionQuery(connection)) {
			pubIds = query.getAll();
		}
		
		Claims claims = new Claims();
		List<Claim> claimList = claims.getClaim();
		
		for(String pubId : pubIds) {
			claimList.add(getDataFor(pubId, excludeRestriction));
		}
		CreateNewClaimInput createNewClaimInput = new CreateNewClaimInput();
		createNewClaimInput.setClaims(claims);
		return createNewClaimInput;
	}*/
	
	public CreateNewClaimInput getLOCDataFor(String pubId, String mr3Data, int excludeRestriction) throws Exception {
		Claims claims = new Claims();
		List<Claim> claimList = claims.getClaim();
		claimList.add(getDataFor(pubId, mr3Data, excludeRestriction));
		CreateNewClaimInput createNewClaimInput = new CreateNewClaimInput();
		createNewClaimInput.setClaims(claims);
		return createNewClaimInput;
	}
	
	private Claim getDataFor(String pubId, String mr3Data, int excludeRestriction) throws Exception {
		DisPubMetaData disPubMetaData = getPubMetaDataProvider().getPubMetaDataFor(pubId, mr3Data, excludeRestriction, 0, 0);
		return getLOCRecordFactory().getLOCRecordFor(disPubMetaData);
	}

	public void updateLOCClaimSubmissionFor(String pubNumber) throws Exception {
		String jsonStr = null;
		ClientResponse response = null;
		
		String URL = getMr3ServiceUrlBase();
		String HEADERKEY = getECMSMr3HeaderKey();
		String HEADERVALUE = getECMSMr3HeaderValue(); 
		Client c = Client.create();
		WebResource resource = c.resource(URL).path("cpsubmitted").path(pubNumber);
		response = resource.header("Content-Type", "application/json")
                	.header(HEADERKEY, HEADERVALUE)
                	.post(ClientResponse.class);
		 jsonStr = response.getEntity(String.class);
		
//		try (Connection connection = getConnectionPool().getConnection();
//				LOCSubmissionUpdateQuery query = new LOCSubmissionUpdateQuery(connection)) {
//			query.updateClaimSubmissionFor(pubNumber);
//		}
	}
	
	public void updateLOCDeliverySubmissionFor(String pubNumber) throws Exception {
		String jsonStr = null;
		ClientResponse response = null;
		
		String URL = getMr3ServiceUrlBase();
		String HEADERKEY = getECMSMr3HeaderKey();
		String HEADERVALUE = getECMSMr3HeaderValue(); 
		Client c = Client.create();
		WebResource resource = c.resource(URL).path("locsent").path(pubNumber);
		response = resource.header("Content-Type", "application/json")
                	.header(HEADERKEY, HEADERVALUE)
                	.post(ClientResponse.class);
		 jsonStr = response.getEntity(String.class);
		
//		try (Connection connection = getConnectionPool().getConnection();
//				LOCSubmissionUpdateQuery query = new LOCSubmissionUpdateQuery(connection)) {
//	    	query.updateDeliverySubmissionFor(pubNumber);
//		}
	}
	
}
