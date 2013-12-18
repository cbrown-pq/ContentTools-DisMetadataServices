package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.proquest.mtg.dismetadataservice.metadata.Author;

@XmlRootElement(name = "DisPubMetaData")
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
	private String formatType;

	@XmlElement
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
	
	@XmlElement
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
	
	public String getFormatType() {
		return formatType;
	}
	
	@XmlAttribute
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
}
