package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

import javax.inject.Named;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.loc.LOCRecordFactory;
import com.proquest.mtg.dismetadataservice.pqloc.Claim;
import com.proquest.mtg.dismetadataservice.pqloc.Claims;
import com.proquest.mtg.dismetadataservice.pqloc.CreateNewClaimInput;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

public class LOCMetaDataProvider {
	
	private final IPubMetaDataProvider pubMetaDataProvider;
	private final LOCRecordFactory locRecordFactory;
//	private final IJdbcConnectionPool connectionPool;
	private final String ecmsMr3HeaderKey;
	private final String ecmsMr3HeaderValue;	
	private final String mr3ServiceUrlBase;

	@Inject
	public LOCMetaDataProvider(IPubMetaDataProvider pubMetaDataProvider,
			LOCRecordFactory locRecordFactory,
//			@Named(IJdbcConnectionPool.kExodusConnectionPool) IJdbcConnectionPool connectionPool,
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_KEY) String ecmsMr3HeaderKey,
			@Named(DisMetadataProperties.ECMS_MR3_HEADER_VALUE) String ecmsMr3HeaderValue,
			@Named(DisMetadataProperties.MR3_SERVICE_URL_BASE) String mr3ServiceUrlBase) {
		this.pubMetaDataProvider = pubMetaDataProvider;
		this.locRecordFactory = locRecordFactory;
//		this.connectionPool = connectionPool;
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

	//public IJdbcConnectionPool getConnectionPool() {
	//	return connectionPool;
	//}

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
//	public CreateNewClaimInput getEligibleLOCData(int excludeRestriction) throws Exception {
//		
//		List<String> pubIds;
//		try (Connection connection = getConnectionPool().getConnection();
//				LOCPubSelectionQuery query = new LOCPubSelectionQuery(connection)) {
//			pubIds = query.getAll();
//		}
//		
//		Claims claims = new Claims();
//		List<Claim> claimList = claims.getClaim();
//		
//		for(String pubId : pubIds) {
//			claimList.add(getDataFor(pubId, "TEMPSTUB", excludeRestriction));
//		}
//		CreateNewClaimInput createNewClaimInput = new CreateNewClaimInput();
//		createNewClaimInput.setClaims(claims);
//		return createNewClaimInput;
//	}
	
	public CreateNewClaimInput getLOCDataFor(String ecmsData, String mr3Data, int excludeRestriction) throws Exception {
		Claims claims = new Claims();
		List<Claim> claimList = claims.getClaim();
		claimList.add(getDataFor(ecmsData, mr3Data, excludeRestriction));
		CreateNewClaimInput createNewClaimInput = new CreateNewClaimInput();
		createNewClaimInput.setClaims(claims);
		return createNewClaimInput;
	}
	
	private Claim getDataFor(String ecmsData, String mr3Data, int excludeRestriction) throws Exception {
		DisPubMetaData disPubMetaData = getPubMetaDataProvider().getPubMetaDataFor(ecmsData, mr3Data, excludeRestriction, 0, 0);
		return getLOCRecordFactory().getLOCRecordFor(disPubMetaData);
	}

	
}
