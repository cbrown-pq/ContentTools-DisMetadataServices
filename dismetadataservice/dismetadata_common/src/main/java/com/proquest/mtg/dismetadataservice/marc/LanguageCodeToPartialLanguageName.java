package com.proquest.mtg.dismetadataservice.marc;

public class LanguageCodeToPartialLanguageName {

	public static String getLanguageFor(String languageCode) {

		if (null == languageCode) {
			return "|||";
		}
		if (languageCode.contentEquals("AE")) {
			return "ara;eng";
		} else if (languageCode.contentEquals("AF")) {
			return "afr";
		} else if (languageCode.contentEquals("AH")) {
			return "ara;heb";
		} else if (languageCode.contentEquals("AN")) {
			return "ang";
		} else if (languageCode.contentEquals("AR")) {
			return "ara";
		} else if (languageCode.contentEquals("AS")) {
			return "afr;nso";
		} else if (languageCode.contentEquals("BQ")) {
			return "baq";
		} else if (languageCode.contentEquals("BS")) {
			return "bos";
		} else if (languageCode.contentEquals("CA")) {
			return "cat";
		} else if (languageCode.contentEquals("CE")) {
			return "chi;eng";
		} else if (languageCode.contentEquals("CH")) {
			return "chi";
		} else if (languageCode.contentEquals("CR")) {
			return "hrv";
		} else if (languageCode.contentEquals("CZ")) {
			return "cze";
		} else if (languageCode.contentEquals("DA")) {
			return "dan";
		} else if (languageCode.contentEquals("DE")) {
			return "dut;eng";
		} else if (languageCode.contentEquals("DL")) {
			return "dut;lat";
		} else if (languageCode.contentEquals("DU")) {
			return "dut";
		} else if (languageCode.contentEquals("EF")) {
			return "est;fin";
		} else if (languageCode.contentEquals("EG")) {
			return "gre;eng";
		} else if (languageCode.contentEquals("EH")) {
			return "hun;eng";
		} else if (languageCode.contentEquals("EL")) {
			return "lat;eng";
		} else if (languageCode.contentEquals("EN")) {
			return "eng";
		} else if (languageCode.contentEquals("ES")) {
			return "est";
		} else if (languageCode.contentEquals("EZ")) {
			return "est;rus;eng";
		} else if (languageCode.contentEquals("FE")) {
			return "fre;eng";
		} else if (languageCode.contentEquals("FI")) {
			return "fin";
		} else if (languageCode.contentEquals("FL")) {
			return "dut";
		} else if (languageCode.contentEquals("FN")) {
			return "fin;eng";
		} else if (languageCode.contentEquals("FR")) {
			return "fre";
		} else if (languageCode.contentEquals("FS")) {
			return "fre;spa";
		} else if (languageCode.contentEquals("FT")) {
			return "fre;ita";
		} else if (languageCode.contentEquals("FZ")) {
			return "fre;spa;eng";
		} else if (languageCode.contentEquals("GA")) {
			return "glg";
		} else if (languageCode.contentEquals("GE")) {
			return "ger";
		} else if (languageCode.contentEquals("GN")) {
			return "ger;eng";
		} else if (languageCode.contentEquals("GR")) {
			return "gre";
		} else if (languageCode.contentEquals("GS")) {
			return "ger;spa";
		} else if (languageCode.contentEquals("HE")) {
			return "heb";
		} else if (languageCode.contentEquals("HG")) {
			return "heb;eng";
		} else if (languageCode.contentEquals("HI")) {
			return "haw";
		} else if (languageCode.contentEquals("HN")) {
			return "haw;eng";
		} else if (languageCode.contentEquals("HU")) {
			return "hun";
		} else if (languageCode.contentEquals("HY")) {
			return "heb;yid";
		} else if (languageCode.contentEquals("IC")) {
			return "ice";
		} else if (languageCode.contentEquals("IE")) {
			return "ita;eng";
		} else if (languageCode.contentEquals("IR")) {
			return "gle";
		} else if (languageCode.contentEquals("IS")) {
			return "zul";
		} else if (languageCode.contentEquals("IT")) {
			return "ita";
		} else if (languageCode.contentEquals("JA")) {
			return "jpb";
		} else if (languageCode.contentEquals("JE")) {
			return "jpn;eng";
		} else if (languageCode.contentEquals("JP")) {
			return "jpr";
		} else if (languageCode.contentEquals("KE")) {
			return "kor;eng";
		} else if (languageCode.contentEquals("KO")) {
			return "kor";
		} else if (languageCode.contentEquals("LA")) {
			return "lat";
		} else if (languageCode.contentEquals("LI")) {
			return "lit";
		} else if (languageCode.contentEquals("LV")) {
			return "lav";
		} else if (languageCode.contentEquals("ME")) {
			return "enm";
		} else if (languageCode.contentEquals("NE")) {
			return "nor;eng";
		} else if (languageCode.contentEquals("NG")) {
			return "nav;eng";
		} else if (languageCode.contentEquals("NO")) {
			return "nor";
		} else if (languageCode.contentEquals("PE")) {
			return "por;eng";
		} else if (languageCode.contentEquals("PH")) {
			return "pol;eng";
		} else if (languageCode.contentEquals("PL")) {
			return "pol";
		} else if (languageCode.contentEquals("PN")) {
			return "per;eng";
		} else if (languageCode.contentEquals("PR")) {
			return "por";
		} else if (languageCode.contentEquals("RE")) {
			return "rus;eng";
		} else if (languageCode.contentEquals("RO")) {
			return "rum";
		} else if (languageCode.contentEquals("RU")) {
			return "rus";
		} else if (languageCode.contentEquals("SA")) {
			return "san";
		} else if (languageCode.contentEquals("SB")) {
			return "spa;baq";
		} else if (languageCode.contentEquals("SC")) {
			return "chi";
		} else if (languageCode.contentEquals("SD")) {
			return "nso";
		} else if (languageCode.contentEquals("SE")) {
			return "spa;eng";
		} else if (languageCode.contentEquals("SG")) {
			return "swe;eng";
		} else if (languageCode.contentEquals("SK")) {
			return "slo";
		} else if (languageCode.contentEquals("SL")) {
			return "spa;lat";
		} else if (languageCode.contentEquals("SO")) {
			return "sot";
		} else if (languageCode.contentEquals("SP")) {
			return "spa";
		} else if (languageCode.contentEquals("SR")) {
			return "spa;ara";
		} else if (languageCode.contentEquals("SS")) {
			return "syc;eng";
		} else if (languageCode.contentEquals("SV")) {
			return "slv";
		} else if (languageCode.contentEquals("SW")) {
			return "swe";
		} else if (languageCode.contentEquals("TC")) {
			return "chi";
		} else if (languageCode.contentEquals("TE")) {
			return "tha;eng";
		} else if (languageCode.contentEquals("TO")) {
			return "cai";
		} else if (languageCode.contentEquals("TS")) {
			return "ven";
		} else if (languageCode.contentEquals("TU")) {
			return "tur";
		} else if (languageCode.contentEquals("TW")) {
			return "tsn";
		} else if (languageCode.contentEquals("UE")) {
			return "eng";
		} else if (languageCode.contentEquals("UK")) {
			return "ukr";
		} else if (languageCode.contentEquals("WE")) {
			return "wel";
		} else if (languageCode.contentEquals("YI")) {
			return "yid";
		} else {
			return "|||";
		}
	}
}
