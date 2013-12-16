package com.proquest.mtg.dismetadataservice.metadata;

import java.util.List;

import com.google.common.collect.Lists;

public class SplitAuthorNames {

	private String full;
	private String last;
	private String first;
	private String middle;
	private String suffix;
	
	public SplitAuthorNames(String source) {
		if (null != source) {
			source = source.trim();
			if (!source.isEmpty()) {
				this.full = source;
				List<String> parts = Lists.newArrayList(source.split(","));
				if (parts.size() > 0) {
					last = parts.get(0).trim();
				}
				else {
					last = full;
				}
				if (parts.size() > 1) {
					String secondPart = parts.get(1).trim();
					int firstSpaceIndex = secondPart.indexOf(" ");
					if (firstSpaceIndex > 0) {
						first = secondPart.substring(0, firstSpaceIndex).trim();
						String remainder = secondPart.substring(firstSpaceIndex+1);
						if (!remainder.isEmpty()) {
							middle = remainder.trim();
						}
					}
					else {
						first = secondPart.trim();
					}
				}
				if (parts.size() > 2) {
					suffix = parts.get(2).trim();
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
	
	public String getMiddle() {
		return middle;
	}
	
	public String getSuffix() {
		return suffix;
	}
}
