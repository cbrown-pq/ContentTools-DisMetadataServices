package com.proquest.mtg.dismetadataservice.datasource;


import java.sql.SQLException;
import com.proquest.mtg.dismetadataservice.ecms.ECMSMetaDataFormatFactory;


public class PubMetaDataQuery {
	public static final String kEmptyValue = "";
	
	private final String pqOpenUrlBase;
	
	public PubMetaDataQuery(String pqOpenUrlBase) throws SQLException {
		this.pqOpenUrlBase = pqOpenUrlBase;
	}
	
	public String getPqOpenUrlBase() {
		return pqOpenUrlBase;
	}
	
	public String makePqOpenUrlFor(String pubId) {
		return getPqOpenUrlBase() + pubId.trim();
	}
	
	public DisPubMetaData getFor(String ecmsData, String mr3Data,  String PqOpenUrlBase, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception {
		DisPubMetaData result = null;
		try {
			ecmsData = ecmsData.replaceAll("\\\\n", "").replace("\\\"", "\"").replaceAll("\\\\t", "").replaceAll("<\\?xml(.+?)\\?>", "").trim();
			// Strip out \n and \t
				result = makeDisPubMetaDataFrom(ecmsData, mr3Data, PqOpenUrlBase, excludeRestriction, excludeAbstract, excludeAltAbstract);
		}
		finally {
		}
		return result;
	}
		
	private DisPubMetaData makeDisPubMetaDataFrom(String ecmsData, String mr3Data, String PqOpenUrlBase, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception {
		DisPubMetaData result = new DisPubMetaData();
		result = ECMSMetaDataFormatFactory.constructECMSMetaData(ecmsData, mr3Data, PqOpenUrlBase, excludeRestriction,excludeAbstract,excludeAltAbstract);
		return result;
	}
	
	public static String trimmed(String x) {
		if (null != x) {
			x = x.trim();
		}
		return x;
	}
	
	public static String required(String x) {
		x = trimmed(x);
		if (null == x || x.isEmpty()) {
			x = kEmptyValue;
		}
		return x;
	}
}
