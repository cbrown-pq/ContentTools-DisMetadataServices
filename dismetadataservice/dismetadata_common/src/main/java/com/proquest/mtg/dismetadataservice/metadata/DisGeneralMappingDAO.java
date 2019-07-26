package com.proquest.mtg.dismetadataservice.metadata;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.*;
//import java.util.function.BiConsumer;
import java.util.regex.Pattern;



import com.google.common.collect.Lists;

public class DisGeneralMappingDAO {
	
	private final static Pattern LTRIM = Pattern.compile("^%+");
	private final static Pattern RTRIM = Pattern.compile("%+$");
	
	//private static final String kDegreeValue_1 = "DGR_VALUE_1";
	//private static final String kDegreeValue_2 = "DGR_VALUE_2";
	
	static Map<String, Integer> createHashMap(){
		
	//HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		Map<String, Integer> hmap = new Hashtable<>();
	
	hmap.put("%L'%",	2);
	hmap.put("%An %",	3);
  hmap.put("%Le %",	3);
  hmap.put("%''A %",	3);
	hmap.put("%\"A %",	3);
	hmap.put("%La %",	3);
	hmap.put("%El %",	3);
	hmap.put("%L' %",	3);
	hmap.put("%Un %",	3);
	hmap.put("%The %",	4);
	hmap.put("%''A %",	4);
	hmap.put("%'An %",	4);
	hmap.put("%\"An %",	4);
	hmap.put("%'Le %",	4);
	hmap.put("%\"Le %",	4);
	hmap.put("%'El %",	4);
	hmap.put("%\"El %",	4);
	hmap.put("%'La %",	4);
	hmap.put("%\"La %",	4);
	hmap.put("%Una %",	4);
	hmap.put("%Les %",	4);
	hmap.put("%Las %",	4);
	hmap.put("%Los %",	4);
	hmap.put("%'Los %",	5);
	hmap.put("%\"Los %",	5);
	hmap.put("%'Las %",	5);
	hmap.put("%\"Las %",	5);
	hmap.put("%'Una %",	5);
	hmap.put("%\"Una %",	5);
	hmap.put("%'Les %",	5);
	hmap.put("%\"Les %",	5);
	hmap.put("%THE %",	4);
	hmap.put("%AN %",	3);
	
	return hmap;
}		
			
	public DisGeneralMappingDAO() throws Exception {
		//this.disMappingStatement = connection.prepareStatement(kSelectDisGenMapping);
	}
	
	//public List<DisGeneralMapping> getAllMappingFor(String degreeSystem) throws SQLException {
	public List<DisGeneralMapping> getAllMappingFor() throws Exception {
		List<DisGeneralMapping> result = null;
		//ResultSet cursor = null;
		try {
			//disMappingStatement.setString(1, degreeSystem);
			//cursor = disMappingStatement.executeQuery();
			Map<String, Integer> hmap = createHashMap();
			result = makeMappingFrom((Map<String, Integer>) hmap);
		}
		finally {
			//if (null != cursor) {
			//	cursor.close();
			//}
			//if(null != connection) {
			//	connection.close();
			//}
		}
		return result;
	}

	private List<DisGeneralMapping> makeMappingFrom(Map hmap) throws Exception {
		List<DisGeneralMapping> result = Lists.newArrayList();
		String k;
		String v;
		for (Object key : hmap.keySet().toArray() ) {
			k = key.toString();
			v = hmap.get(k).toString();
			String dgrValue_1 = k.toString();
			String dgrValue_2 = v.toString();
			DisGeneralMapping disGeneralMapping = new DisGeneralMapping(
					RTRIM.matcher(LTRIM.matcher(dgrValue_1).replaceAll("")).replaceAll(""), 
					dgrValue_2);
			result.add(disGeneralMapping);
		}
		return result;
	}

}
