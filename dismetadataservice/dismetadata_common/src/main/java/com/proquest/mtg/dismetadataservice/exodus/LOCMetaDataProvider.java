package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.util.List;

import javax.inject.Named;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.loc.LOCRecordFactory;
import com.proquest.mtg.dismetadataservice.pqloc.Claim;
import com.proquest.mtg.dismetadataservice.pqloc.Claims;
import com.proquest.mtg.dismetadataservice.pqloc.CreateNewClaimInput;

public class LOCMetaDataProvider {
	
	private final IPubMetaDataProvider pubMetaDataProvider;
	private final LOCRecordFactory locRecordFactory;
	private final IJdbcConnectionPool connectionPool;

	@Inject
	public LOCMetaDataProvider(IPubMetaDataProvider pubMetaDataProvider,
			LOCRecordFactory locRecordFactory,
			@Named(IJdbcConnectionPool.kExodusConnectionPool) 
	IJdbcConnectionPool connectionPool) {
		this.pubMetaDataProvider = pubMetaDataProvider;
		this.locRecordFactory = locRecordFactory;
		this.connectionPool = connectionPool;
		
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
	
	//CBDELETE
	/*public CreateNewClaimInput getEligibleLOCData(int excludeRestriction) throws Exception {
		
		List<String> pubIds;
		Connection connection = null;
		LOCPubSelectionQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new LOCPubSelectionQuery(connection);
			pubIds = query.getAll();
		}
		finally {
			if (null != query) {
				query.close();
			}
			if (null != connection) {
				connection.close();
			}
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
		Connection connection = null;
		LOCSubmissionUpdateQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new LOCSubmissionUpdateQuery(connection);
	    	query.updateClaimSubmissionFor(pubNumber);
		}
		finally {
			if (null != query) {
				query.close();
			}
			if (null != connection) {
				connection.close();
			}
		}
	}
	
	public void updateLOCDeliverySubmissionFor(String pubNumber) throws Exception {
		Connection connection = null;
		LOCSubmissionUpdateQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new LOCSubmissionUpdateQuery(connection);
	    	query.updateDeliverySubmissionFor(pubNumber);
		}
		finally {
			if (null != query) {
				query.close();
			}
			if (null != connection) {
				connection.close();
			}
		}
	}
	
}
