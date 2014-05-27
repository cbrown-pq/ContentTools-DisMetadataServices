package com.proquest.mtg.dismetadataservice.exodus;

import java.util.ArrayList;
import java.util.List;

import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject.PQOnlineDescriptions;

public class SplitPQOnlineSubjectsDescriptors {
	
	private static final String kDelimiter = ";";
	
	public static PQOnlineDescriptions split(String value) {
	
		List<String> descriptions = new ArrayList<String>();
		if(null != value && !value.isEmpty())
		{
			int index = value.indexOf(";");
			if (index > -1) {
				String[] onlineDescriptionString = value.split(kDelimiter);
				for (int i = 0; i < onlineDescriptionString.length; i++) {
					descriptions.add(onlineDescriptionString[i]);
				}
			} else {
				descriptions.add(value);
			}
		}
		PQOnlineDescriptions pqOnlineDescriptions = new PQOnlineDescriptions();
		pqOnlineDescriptions.setPQOnlineDescription(descriptions);
		return pqOnlineDescriptions;
	}

}
