package com.proquest.mtg.dismetadataservice.marc;

import com.google.common.base.CharMatcher;

public class MarcCharSet {
	public static final char kRecordSeparator = (char)0x0a;
	public static final char kRecordTerminator = (char)0x1d;
	public static final char kFieldTerminator = (char)0x1e;
	public static final char kSubFieldIndicator = (char)0x1f;
	
	public static final char[] kAllChars = {
			kRecordSeparator,
			kRecordTerminator,
			kFieldTerminator,		
			kSubFieldIndicator, 
			' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+',  ',', '-', '.', '/',
			'0', '1', '2', '3', '4', '5', '6',  '7', '8', '9', ':', ';',  '<', '=', '>', '?', 
			'@', 'A', 'B', 'C', 'D', 'E', 'F',  'G', 'H', 'I', 'J', 'K',  'L', 'M', 'N', 'O', 
			'P', 'Q', 'R', 'S', 'T', 'U', 'V',  'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', 
			'`', 'a', 'b', 'c', 'd', 'e', 'f',  'g', 'h', 'i', 'j', 'k',  'l', 'm', 'n', 'o', 
			'p', 'q', 'r', 's', 't', 'u', 'v',  'w', 'x', 'y', 'z', '{',  '|', '}', '~' 
	};
	
	public static final CharMatcher kCharMatcher = CharMatcher.anyOf(new String(kAllChars));
	
	public static boolean isLegal(CharSequence sequence) {
		return kCharMatcher.matchesAllOf(sequence);
	}

}
