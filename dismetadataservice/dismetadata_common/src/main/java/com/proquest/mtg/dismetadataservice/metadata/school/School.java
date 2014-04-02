package com.proquest.mtg.dismetadataservice.metadata.school;

import java.util.List;

public class School {
	private String id;
	private String code;
	private String name;
	private String country;
	private String state;
	private String carnegieCode;
	private String dashboardEligibility;
	private List<Address> addresses;
	private List<PersonType> personTypes;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCarnegieCode() {
		return carnegieCode;
	}

	public void setCarnegieCode(String carnegieCode) {
		this.carnegieCode = carnegieCode;
	}

	public String getDashboardEligibility() {
		return dashboardEligibility;
	}

	public void setDashboardEligibility(String dashboardEligibility) {
		this.dashboardEligibility = dashboardEligibility;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<PersonType> getPersonTypes() {
		return personTypes;
	}

	public void setPersonTypes(List<PersonType> personTypes) {
		this.personTypes = personTypes;
	}

}
