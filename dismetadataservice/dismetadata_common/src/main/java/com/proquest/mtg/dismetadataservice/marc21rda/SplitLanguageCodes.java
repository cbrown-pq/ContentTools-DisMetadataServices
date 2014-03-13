package com.proquest.mtg.dismetadataservice.marc21rda;

import java.util.List;

import com.google.common.collect.Lists;

public class SplitLanguageCodes {

	private static final String kDelimiter = ";";
	
	public static List<String> split(String delimitedLanguageCodes) {
		List<String> result = Lists.newArrayList();
		if (null != delimitedLanguageCodes) {
			delimitedLanguageCodes = delimitedLanguageCodes.trim();
			int firstColonIndex = delimitedLanguageCodes.indexOf(':');
			if (firstColonIndex >= 0) {
				delimitedLanguageCodes = delimitedLanguageCodes.substring(firstColonIndex+1);
			}
			for (String item : Lists.newArrayList(delimitedLanguageCodes.split(kDelimiter))) {
				item = item.trim();
				if (!item.isEmpty()) {
					result.add(item);
				}
			}
		}
		return result;
	}

}
