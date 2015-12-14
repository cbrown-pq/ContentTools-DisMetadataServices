package com.proquest.mtg.dismetadataservice.metadata;

public class TextNormalizer {
	private static final String kCdataStartTag = "<![CDATA[";
	private static final String kCdataEndTag = "]]>";
	private static final String kParaGraphTag = "<p>";
	private static final String kCloseParaGraphTag = "</p>";

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
	
	public String addCdataTags(String x) {
		if ((x.startsWith(kCdataStartTag)) && (x.endsWith(kCdataEndTag))) {
			return x;
		} else {
			String result = kCdataStartTag + x + kCdataEndTag;
			return result.trim();
		}
	}
	
		
	public String replaceCRTagwithPara(String text) {
		boolean cdataExist = false;
		if (text == null) {
			return text;
		}

		if (!text.contains("^")) {
			return text;
		} else {
			if ((text.startsWith(kCdataStartTag)) && (text.endsWith(kCdataEndTag))) {
				text = removeCdataTags(text);
				cdataExist = true;		
			}

			text = text.replaceFirst("\\^", kParaGraphTag);
			if (!text.contains("^")) {
				text = text + kCloseParaGraphTag;
				if (cdataExist){
					return kCdataStartTag + text + kCdataEndTag;
				} else {
					return text;
				}				
			}
			do {
				if (text.indexOf("^") == text.lastIndexOf("^")) {
					text = text.replaceFirst("\\^", kCloseParaGraphTag);
					break;
				} else {
					text = text.replaceFirst("\\^", kCloseParaGraphTag
							+ kParaGraphTag);
				}

			} while (text.contains("^"));

			if (cdataExist){
				return kCdataStartTag + text + kCdataEndTag;
			} else {
				return text;
			}
		}
	}
}
