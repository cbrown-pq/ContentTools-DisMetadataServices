package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.util.List;

import javax.inject.Named;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.loc.LOCRecordFactory;
import com.proquest.mtg.dismetadataservice.pqloc.Claims;
import com.proquest.mtg.dismetadataservice.pqloc.Claim;
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
	
	public CreateNewClaimInput getEligibleLOCData() throws Exception {
		
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
			claimList.add(getDataFor(pubId));
		}
		CreateNewClaimInput createNewClaimInput = new CreateNewClaimInput();
		createNewClaimInput.setClaims(claims);
		return createNewClaimInput;
	}
	
	public CreateNewClaimInput getLOCDataFor(String pubId) throws Exception {
		Claims claims = new Claims();
		List<Claim> claimList = claims.getClaim();
		claimList.add(getDataFor(pubId));
		CreateNewClaimInput createNewClaimInput = new CreateNewClaimInput();
		createNewClaimInput.setClaims(claims);
		return createNewClaimInput;
	}
	
	private Claim getDataFor(String pubId) throws Exception {
		DisPubMetaData disPubMetaData = getPubMetaDataProvider().getPubMetaDataFor(pubId);
		return getLOCRecordFactory().getLOCRecordFor(disPubMetaData);
	}
	
}
