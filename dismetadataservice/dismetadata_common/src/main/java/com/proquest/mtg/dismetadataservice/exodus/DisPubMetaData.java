package com.proquest.mtg.dismetadataservice.exodus;

public class DisPubMetaData {
	private String pubNumber;
	private String itemId;
	private String isbn;
	private String pubPageNum;
	private String pageCount;	
	
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
}
