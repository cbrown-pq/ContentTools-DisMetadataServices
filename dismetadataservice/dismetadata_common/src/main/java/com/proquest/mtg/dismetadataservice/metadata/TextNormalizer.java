package com.proquest.mtg.dismetadataservice.metadata;

public class TextNormalizer {
	private static final String kCdataStartTag = "<![CDATA[";
	private static final String kCdataEndTag = "]]>";
	private static final String kParaGraphTag = "<p>";

	public String applyTo(String x) {
		if (null != x) {
			return
				replaceParagraphIndicatorsWithTag(
				SGMLAngluarBracesEntitySubstitution.applyAllTo(
				removeAdeptTags(
				removeCdataTags(x.trim()))));
		}
		else {
			return null;
		}
	}
	
	public String removeAdeptTags(String x) {
		return x.replace("<?Pub Inc>", "").trim();
	}
	
	public String removeCdataTags(String x) {
		if (x.startsWith(kCdataStartTag)) {
			x = x.substring(kCdataStartTag.length());
			if (x.endsWith(kCdataEndTag)) {
				x = x.substring(0, x.length() - kCdataEndTag.length());
			}
		}
		return x.trim();
	}
	
	public String replaceParagraphIndicatorsWithTag(String x) {
		return x.replaceAll("\\^|\n", kParaGraphTag);
	}

}
