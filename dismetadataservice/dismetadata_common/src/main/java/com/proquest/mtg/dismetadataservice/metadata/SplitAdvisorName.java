package com.proquest.mtg.dismetadataservice.metadata;

import java.util.List;

import com.google.common.collect.Lists;

public class SplitAdvisorName {

	private String full;
	private String last;
	private String first;
	private String suffix;

	// Format: FirstName . . MiddleName LastName, Suffix

	public SplitAdvisorName(String source) {
		if (null != source) {
			source = source.trim();
			if (!source.isEmpty()) {
				this.full = source;
				List<String> parts = Lists.newArrayList(source.split(","));

				String firstPart = parts.get(0).trim();
				int lastSpaceIndex = firstPart.lastIndexOf(" ");
				if (lastSpaceIndex > 0) {
					last = firstPart.substring(lastSpaceIndex + 1).trim();
					first = firstPart.substring(0, lastSpaceIndex + 1).trim();
				} else {
					first = firstPart;
					last = "";
				}

				if (parts.size() > 1) {
					suffix = parts.get(1).trim();
				}
			}
		}
	}

	public String getFull() {
		return full;
	}

	public String getLast() {
		return last;
	}

	public String getFirst() {
		return first;
	}	

	public String getSuffix() {
		return suffix;
	}
}
