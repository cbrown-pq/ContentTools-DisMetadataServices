package com.proquest.mtg.dismetadataservice.marc;

public class LanguageCodeToPartialLanguageName {

	public static String getLanguageFor(String languageCode) {
		if (languageCode.contentEquals("EN")) {
			return "eng";
		}
		else if (languageCode.contentEquals("DU")) {
			return "dut";
		}
		else if (languageCode.contentEquals("FR")) {
			return "fre";
		}
		else if (languageCode.contentEquals("SP")) {
			return "spa";
		}
		else if (languageCode.contentEquals("FI")) {
			return "fin";
		}
		else if (languageCode.contentEquals("SW")) {
			return "swe";
		}
		else if (languageCode.contentEquals("NO")) {
			return "nor";
		}
		else {
			return "|||";
		}
	}
}
