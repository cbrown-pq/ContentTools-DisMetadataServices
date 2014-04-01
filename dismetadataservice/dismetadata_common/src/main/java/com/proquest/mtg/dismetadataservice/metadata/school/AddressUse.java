package com.proquest.mtg.dismetadataservice.metadata.school;

import java.util.List;

public class AddressUse {
	
	private String type;
	private String eBSAccount;
	private String deliveryDate;
	private String dateCreated;
	private String dateModified;
	private List<SchoolContact> schoolContacts;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String geteBSAccount() {
		return eBSAccount;
	}
	public void seteBSAccount(String eBSAccount) {
		this.eBSAccount = eBSAccount;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public List<SchoolContact> getSchoolContacts() {
		return schoolContacts;
	}
	public void setSchoolContacts(List<SchoolContact> schoolContacts) {
		this.schoolContacts = schoolContacts;
	}
	
}
