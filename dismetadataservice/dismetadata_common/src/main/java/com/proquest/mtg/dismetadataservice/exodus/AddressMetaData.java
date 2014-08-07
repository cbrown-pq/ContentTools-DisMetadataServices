package com.proquest.mtg.dismetadataservice.exodus;


public class AddressMetaData {

	    private String name;
	    private String line1;
	    private String line2;
	    private String line3;
	    private String city;
	    private String fiveDigitZip;
	    private String fourDigitZip;
	    private String postalCode;
	    private String stateProvince;
	    private String country;
	    private String stateCode;
	    private String countryDescription;
	    private String locCountryDescription;
	    
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getLine1() {
			return line1;
		}
		
		public void setLine1(String line1) {
			this.line1 = line1;
		}
		
		public String getLine2() {
			return line2;
		}
		
		public void setLine2(String line2) {
			this.line2 = line2;
		}
		
		public String getLine3() {
			return line3;
		}
		
		public void setLine3(String line3) {
			this.line3 = line3;
		}
		
		public String getCity() {
			return city;
		}
		
		public void setCity(String city) {
			this.city = city;
		}
		
		public String getPostalCode() {
			return postalCode;
		}
		
		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}
		
		public String getFiveDigitZip() {
			return fiveDigitZip;
		}
		
		public void setFiveDigitzip(String fiveDigitzip) {
			this.fiveDigitZip = fiveDigitzip;
		}
		
		public String getFourDigitZip() {
			return fourDigitZip;
		}
		
		public void setFourDigitzip(String fourDigitZip) {
			this.fourDigitZip = fourDigitZip;
		}
		
		public String getStateProvince() {
			return stateProvince;
		}
		
		public void setStateProvince(String stateProvince) {
			this.stateProvince = stateProvince;
		}
		
		public String getCountry() {
			return country;
		}
		
		public void setCountry(String country) {
			this.country = country;
		}

		public void setStateCode(String stateCode) {
			this.stateCode = stateCode;
		}
		
		public String getStateCode() {
			return stateCode;
		}
		
		public void setCountryDescription(String countryDescription) {
			this.countryDescription = countryDescription;
		}
		
		public String getCountryDescription() {
			return countryDescription;
		}

		@Override
		public String toString() {
			return "AddressMetaData [name=" + name + ", line1=" + line1
					+ ", line2=" + line2 + ", line3=" + line3 + ", city="
					+ city + ", fiveDigitZip=" + fiveDigitZip
					+ ", fourDigitZip=" + fourDigitZip + ", postalCode="
					+ postalCode + ", stateProvince=" + stateProvince
					+ ", country=" + country + ", stateCode=" + stateCode
					+ ", countryDescription=" + countryDescription
					+ ", locCountryDescription=" + locCountryDescription + "]";
		}

		public String getLocCountryDescription() {
			return locCountryDescription;
		}

		public void setLocCountryDescription(String locCountryDescription) {
			this.locCountryDescription = locCountryDescription;
		}
		
}
