package com.proquest.mtg.dismetadataservice.metadata;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;

public class DisGenMappingProvider {
	public static final String kMarcMapping = "MARC_245_IND";
	
	private final Map<String, List<DisGeneralMapping>> disMapping;
	

	private final IJdbcConnectionPool connectionPool;
	
	@Inject
	public DisGenMappingProvider(@Named(IJdbcConnectionPool.kExodusConnectionPool) 
	IJdbcConnectionPool connectionPool) throws SQLException {
		this.connectionPool = connectionPool;
		disMapping = new HashMap<String, List<DisGeneralMapping>>();
		initialize();
	}

	private void initialize() throws SQLException {
		DisGeneralMappingDAO disGenMappingDAO = new DisGeneralMappingDAO(connectionPool.getConnection());
		disMapping.put(kMarcMapping, disGenMappingDAO.getAllMappingFor(kMarcMapping));
	}

	public Map<String, List<DisGeneralMapping>> getDisMapping() {
		return disMapping;
	}
	
}
