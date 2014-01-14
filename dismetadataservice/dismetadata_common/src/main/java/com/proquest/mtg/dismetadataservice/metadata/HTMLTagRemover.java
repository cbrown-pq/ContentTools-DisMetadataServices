package com.proquest.mtg.dismetadataservice.metadata;

import java.util.regex.Pattern;

public class HTMLTagRemover {
	public static final int kDefaultMaxTagLength = 100;
	private int maxTagLength;
	
	public HTMLTagRemover() {
		this.maxTagLength = kDefaultMaxTagLength;
	}

	public String applyTo(String x) {
		if (null == x) {
			return null; 
		}
		
		Pattern tagPattern = Pattern.compile("<[^<]{1,"+ getMaxagLength() +"}?>");
		return tagPattern.matcher(
				SGMLAngluarBracesEntitySubstitution.applyAllTo(x)).replaceAll("");
	}

	public int getMaxagLength() {
		return maxTagLength;
	}
	
	public void setMaxTagLength(int value) {
		maxTagLength = value;
	}

}
