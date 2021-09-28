package com.proquest.mtg.dismetadataservice.marc;

import java.util.List;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.AlternateTitle;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.metadata.DisGeneralMapping;

public class MarcRecordFactoryBase {
	public static final String kRecordIdPrefix = "AAI";
	public static final String kMarcMapping = "MARC_245_IND";
	public static final String kDoctoralPrefix = "DAI";
	public static final String kMastersPrefix = "MAI";
	
	protected String makeFieldDataFrom(char dataFieldIndicator1,
			char dataFieldIndicator2, char subFieldIndicator, String fieldData) {
		StringBuilder builder = new StringBuilder();
		builder.append(dataFieldIndicator1);
		builder.append(dataFieldIndicator2);
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator);
		builder.append(fieldData);
		return builder.toString();
	}

	protected String makeFieldDataFrom(char dataFieldIndicator1,
			char dataFieldIndicator2, char subFieldIndicator,
			String fieldData1, String fieldData2) {
		StringBuilder builder = new StringBuilder();
		builder.append(dataFieldIndicator1);
		builder.append(dataFieldIndicator2);
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator);
		builder.append(fieldData1);
		builder.append(fieldData2);
		return builder.toString();
	}

	protected String makeHostItemEntryFieldDataFrom(char dataFieldIndicator1,
			char dataFieldIndicator2, char subFieldIndicator1,
			String fieldData1, char subFieldIndicator2, String fieldData2) {
		StringBuilder builder = new StringBuilder();
		builder.append(dataFieldIndicator1);
		builder.append(dataFieldIndicator2);
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator1);
		builder.append(fieldData1);
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator2);
		builder.append(fieldData2);
		return builder.toString();
	}

	protected String makeFieldDataFrom(char subFieldIndicator, String fieldData) {
		StringBuilder builder = new StringBuilder();
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator);
		builder.append(fieldData);
		return builder.toString();
	}
	
	protected String makeFieldDataFrom(char dataFieldIndicator1,
			char dataFieldIndicator2, String fieldData) {
		StringBuilder builder = new StringBuilder();
		builder.append(dataFieldIndicator1);
		builder.append(dataFieldIndicator2);
		builder.append(fieldData);
		return builder.toString();
	}
	
	
	protected char getSecondFieldIndicator(DisGenMappingProvider disGenMappingProvider,String title,String marcMapping) {
		String degreeValue2 = null;
		List<DisGeneralMapping> marcMappings = disGenMappingProvider
				.getDisMapping().get(marcMapping);
		for (DisGeneralMapping mapping : marcMappings) {
			int degree2Val = Integer.parseInt(mapping.getDegreeValue2());
			if (title.length() >= degree2Val ) {
				if (title.substring(0, degree2Val).contentEquals(
						mapping.getDegreevalue1())) {
					degreeValue2 = mapping.getDegreeValue2();
				}
			}
		}
		char secondIndicator = (degreeValue2 == null) ? '0' : degreeValue2.charAt(0);
		return secondIndicator;
	}
	
	protected String verifyTitle(String title) {

		String outTitle = null;
		if (null != title) {
			outTitle = title.trim();
			if (outTitle.endsWith(".")) {
				outTitle = outTitle.substring(0, outTitle.length() - 1);
			}
		}
		return outTitle;
	}
	
	protected String endWithComma(String x) {
		return x.endsWith(",") ? x : x + ",";
	}
	
	protected String endWithPeriod(String x) {
		return x.endsWith(".") ? x : x + ".";
	}
	
	protected String endWithSemicolon(String x) {
		return x.endsWith(";") ? x : x + "; ";
	}
	
	protected String getTitleToInclude(DisPubMetaData disPubMetaData) {
		String title = null;
		String englishOverwriteTitle = (disPubMetaData.getTitle() == null) ? 
				null : disPubMetaData.getTitle().getEnglishOverwriteTitle();
		if (null != englishOverwriteTitle) {
			String cleanedFTitle = verifyTitle(disPubMetaData.getTitle()
					.getForeignTitle());
			if (null != cleanedFTitle) {
				title = cleanedFTitle;
			} else {
				String masterTitle = verifyTitle(disPubMetaData.getTitle()
						.getMasterTitle());
				title = masterTitle;
			}
		} else {
			String cleanedElectronicTitle = disPubMetaData.getTitle() == null ? 
													null : verifyTitle(disPubMetaData.getTitle().getElectronicTitle());
			if (null != cleanedElectronicTitle) {
				title = cleanedElectronicTitle;
			} else {
				String masterTitle = disPubMetaData.getTitle() == null ? 
										null :	verifyTitle(disPubMetaData.getTitle().getMasterTitle());
				title = masterTitle;
			}
		}
		return title;
	}
	
	protected List<AlternateTitle> getAlternateTitleToInclude(DisPubMetaData disPubMetaData) {
		return disPubMetaData.getAlternateTitles();          
	}

}
