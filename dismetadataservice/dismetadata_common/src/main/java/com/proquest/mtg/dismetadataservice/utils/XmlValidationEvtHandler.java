package com.proquest.mtg.dismetadataservice.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class XmlValidationEvtHandler implements ValidationEventHandler {
	List<String> errorList = new ArrayList<String>();
	
	public List<String> getErrorList() {
		return errorList;
	}

	public boolean handleEvent(ValidationEvent event) {
		String msg = event.getMessage();
		String t = msg.substring(msg.indexOf(':')+1);
		errorList.add(t);
		return true;
	}
}