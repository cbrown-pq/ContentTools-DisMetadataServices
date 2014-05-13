package com.proquest.mtg.dismetadataservice.metadata;

import java.util.List;

import com.google.common.collect.Lists;

public class Author {
	private String authorFullName;
	private String firstName;
	private String lastName;
	private String middleName;
	private String altAuthorFullName;
	private List<Degree> degree;
	private int sequenceNumber;
	private String authorCitizenship;

	public String getAuthorFullName() {
		return authorFullName;
	}

	public void setAuthorFullName(String value) {
		this.authorFullName = value;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String value) {
		this.firstName = value;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String value) {
		this.lastName = value;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String value) {
		this.middleName = value;
	}

	public String getAltAuthorFullName() {
		return altAuthorFullName;
	}

	public void setAltAuthorFullName(String value) {
		this.altAuthorFullName = value;
	}

	public void setSequenceNumber(int value) {
		this.sequenceNumber = value;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public List<Degree> getDegrees() {
		return degree;
	}

	public void setDegrees(List<Degree> degree) {
		if (null == this.degree) {
			this.degree = Lists.newArrayList(degree);
		} else {
			this.degree.addAll(degree);
		}
	}

	@Override
	public String toString() {
		return "Author [authorFullName=" + authorFullName + ", firstName="
				+ firstName + ", lastName=" + lastName + ", middleName="
				+ middleName + ", altAuthorFullName=" + altAuthorFullName
				+ ", degree=" + degree + ", sequenceNumber=" + sequenceNumber
				+ "]";
	}

	public static class Degree {
		protected String degreeCode;
		protected String degreeDescription;
		protected String degreeYear;
		protected Integer sequenceNumber;

		public String getDegreeCode() {
			return degreeCode;
		}

		public void setDegreeCode(String value) {
			this.degreeCode = value;
		}

		public String getDegreeDescription() {
			return degreeDescription;
		}

		public void setDegreeDescription(String value) {
			this.degreeDescription = value;
		}

		public String getDegreeYear() {
			return degreeYear;
		}

		public void setDegreeYear(String value) {
			this.degreeYear = value;
		}

		public Integer getSequenceNumber() {
			return sequenceNumber;
		}

		public void setSequenceNumber(Integer value) {
			this.sequenceNumber = value;
		}

		@Override
		public String toString() {
			return "Degree [degreeCode=" + degreeCode + ", degreeDescription="
					+ degreeDescription + ", degreeYear=" + degreeYear
					+ ", sequenceNumber=" + sequenceNumber + "]";
		}
	}

	public String getAuthorCitizenship() {
		return authorCitizenship;
	}

	public void setAuthorCitizenship(String authorCitizenship) {
		this.authorCitizenship = authorCitizenship;
	}
}
