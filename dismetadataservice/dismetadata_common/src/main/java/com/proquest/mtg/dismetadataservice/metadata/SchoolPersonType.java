package com.proquest.mtg.dismetadataservice.metadata;

import java.util.List;

public class SchoolPersonType {
	private String title;
	private String category;
	private String department;
	private String status;
	private String email;
	private String startDate;
	private String endDate;
	private String nameId;
	private String phoneNumber;
	private NameType nameType;



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getNameId() {
		return nameId;
	}
	
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public NameType getNameType() {
		return nameType;
	}

	public void setNameType(NameType nameType) {
		this.nameType = nameType;
	}
	
}
