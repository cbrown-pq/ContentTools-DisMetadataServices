package com.proquest.mtg.dismetadataservice.fop;

import java.util.List;

import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject;

public interface IFopMetaDataProvider {

	void updateFFInProgress(String pubNumber, String inProgressFlag)
			throws Exception;
}
