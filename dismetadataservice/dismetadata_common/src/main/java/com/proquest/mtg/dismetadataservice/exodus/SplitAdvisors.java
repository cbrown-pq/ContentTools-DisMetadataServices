package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

import com.google.common.collect.Lists;

public class SplitAdvisors {
	private static final String kDelimiter = ";";
	
	public static List<String> split(String delimitedAdvisorStr) {
		List<String> result = Lists.newArrayList();
		if (null != delimitedAdvisorStr) {
			delimitedAdvisorStr = delimitedAdvisorStr.trim();
			int firstColonIndex = delimitedAdvisorStr.indexOf(':');
			if (firstColonIndex >= 0) {
				delimitedAdvisorStr = delimitedAdvisorStr.substring(firstColonIndex+1);
			}
			for (String item : Lists.newArrayList(delimitedAdvisorStr.split(kDelimiter))) {
				item = item.trim();
				if (!item.isEmpty()) {
					result.add(item);
				}
			}
		}
		return result;
	}
}
