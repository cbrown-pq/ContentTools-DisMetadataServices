package com.proquest.mtg.dismetadataservice.metadata;

//import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.google.inject.Inject;
//import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;

public class DisGenMappingProvider {
	public static final String kMarcMapping = "MARC_245_IND";
	
	private final Map<String, List<DisGeneralMapping>> disMapping;
	

	//private final IJdbcConnectionPool connectionPool;
	
	@Inject
	public DisGenMappingProvider() throws Exception {
		//this.connectionPool = connectionPool;
		disMapping = new HashMap<String, List<DisGeneralMapping>>();
		initialize();
	}

	private void initialize() throws Exception {
		DisGeneralMappingDAO disGenMappingDAO = new DisGeneralMappingDAO();
		disMapping.put(kMarcMapping, disGenMappingDAO.getAllMappingFor());
	}

	public Map<String, List<DisGeneralMapping>> getDisMapping() {
		return disMapping;
	}
	
}
