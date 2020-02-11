package com.proquest.mtg.dismetadataservice.datasource;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;

import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

public class PubMetaDataProvider implements IPubMetaDataProvider {
	private String pqOpenUrlBase;
	
	@Inject
	public PubMetaDataProvider(
			@Named(DisMetadataProperties.PQ_OPEN_URL_BASE) 
			String pqOpenUrlBase) throws SQLException {
		this.pqOpenUrlBase = pqOpenUrlBase.trim();
	}
		
	public String getPqOpenUrlBase() {
		return pqOpenUrlBase;
	}

	@Override
	public DisPubMetaData getPubMetaDataFor(String ecmsData, String mr3Data, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception {
		DisPubMetaData result = null;
		PubMetaDataQuery query = null;
		String pqBase = getPqOpenUrlBase();
		try {
			query = new PubMetaDataQuery(getPqOpenUrlBase());
			result = query.getFor(ecmsData, mr3Data, pqBase, excludeRestriction, excludeAbstract, excludeAltAbstract);
		}
		finally {
		}
		return result;
	}
}
