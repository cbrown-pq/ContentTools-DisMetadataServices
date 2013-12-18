package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

import com.proquest.mtg.dismetadataservice.metadata.Author;

public class DisPubMetaData {
	private String pubNumber;
	private String itemId;
	private String isbn;
	private String pubPageNum;
	private String pageCount;
	private String blNumber;
	private String referenceLocation;
	private String externalURL;
	private List<Author> authors;
	private DissLanguage dissLanguage;

	public String getPubNumber() {
		return pubNumber;
	}

	public void setPubNumber(String pubNumber) {
		this.pubNumber = pubNumber;
	}
	
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getISBN() {
		return isbn;
	}

	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

	public String getPubPageNum() {
		return pubPageNum;
	}

	public void setPubPageNum(String pubPageNum) {
		this.pubPageNum = pubPageNum;
	}

	
	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getBLNumber() {
		return blNumber;
	}

	public void setBLNumber(String blNumber) {
		this.blNumber = blNumber;
	}

	public String getReferenceLocation() {
		return referenceLocation;
	}

	public void setReferenceLocation(String referenceLocation) {
		this.referenceLocation = referenceLocation;
	}

	public String getExternalURL() {
		return externalURL;
	}

	public void setExternalURL(String externalURL) {
		this.externalURL = externalURL;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	
	public DissLanguage getDissLanguage() {
		return dissLanguage;
	}

	public void setDissLanguage(DissLanguage value) {
		this.dissLanguage = value;
	}

	@Override
	public String toString() {
		return "DisPubMetaData [pubNumber=" + pubNumber + ", itemId=" + itemId
				+ ", isbn=" + isbn + ", pubPageNum=" + pubPageNum
				+ ", pageCount=" + pageCount + ", blNumber=" + blNumber
				+ ", referenceLocation=" + referenceLocation + ", externalURL="
				+ externalURL + ", authors=" + authors + ", language="
				+ dissLanguage + "]";
	}

	public static class DissLanguage {
		private String languageDescription;
		private String languageCode;
		
		public DissLanguage(String languageDescription, String languageCode) {
			this.languageDescription = languageDescription;
			this.languageCode = languageCode;
		}
		
		public String getLanguageCode() {
			return languageCode;
		}
		public void setLanguageCode(String value) {
			this.languageCode = value;
		}
		
		public String getLanguageDescription() {
			return languageDescription;
		}
		public void setLanguageDescription(String value) {
			this.languageDescription = value;
		}

		@Override
		public String toString() {
			return "Language [languageDescription=" + languageDescription
					+ ", languageCode=" + languageCode + "]";
		}
		
	}
}
