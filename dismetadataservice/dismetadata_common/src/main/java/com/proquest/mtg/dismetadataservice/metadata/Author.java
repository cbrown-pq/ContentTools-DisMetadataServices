package com.proquest.mtg.dismetadataservice.metadata;

import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.datasource.AddressMetaData;


public class Author {
	private String authorId;
    private String authorFullName;
    private String firstName;
    private String lastName;
    private String middleName;
    private String altAuthorFullName;
    private List<Degree> degree;
    private List<Claimant> claimants;
    private String claimantAddFlag;
    private List<AddressMetaData> addresses;
    private int sequenceNumber;
    private String authorCitizenship;
    private String orcID;
    private String authorLocCitizenship;

    public String getAuthorId() {
    	return authorId;
    }
    
    public String getOrcID() {
		return orcID;
	}

	public void setOrcID(String orcID) {
		this.orcID = orcID;
	}

	public void setAuthorId(String kcolumnauthorid) {
    	this.authorId = kcolumnauthorid;
    }
    
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
	
	public List<Claimant> getClaimants() {
		return claimants;
	}

	public void setClaimants(List<Claimant> claimants) {
		if (null == this.claimants) {
			this.claimants = Lists.newArrayList(claimants);
		} else {
			this.claimants.addAll(claimants);
		}
	}
	
	public String getClaimantAddFlag() {
		return claimantAddFlag;
	}
	
	public void setClaimantAddFlag(String claimantAddFlag) {
		this.claimantAddFlag = claimantAddFlag;
	}
	
	public void setAddresses(List<AddressMetaData> addresses) {
		this.addresses = addresses;
	}

	public List<AddressMetaData> getAddresses() {
		return addresses;
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
	
	public static class Claimant {
		
		protected String claimantId;
		protected Integer sequenceNumber;
		protected String  claimantFullName;
		protected String  lastName;
		protected String  firstName;
		protected String  middleName;
		protected List<AddressMetaData> addresses;
		
		public void setSequenceNumber(Integer sequenceNumber) {
			this.sequenceNumber = sequenceNumber;
		}
		
		public Integer getSequenceNumber() {
			return sequenceNumber;
		}
		
		public void setClaimantFullName(String claimantFullName) {
			this.claimantFullName = claimantFullName;
		}
		
		public String getClaimantFullName() {
			return claimantFullName;
		}
		
		public void setClaimantId(String claimantId) {
			this.claimantId = claimantId;
		}
		
		public String getClaimantId() {
			return claimantId;
		}
		
		public void setAddresses(List<AddressMetaData> addresses) {
			this.addresses = addresses;
		}

		public List<AddressMetaData> getAddresses() {
			return addresses;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getMiddleName() {
			return middleName;
		}

		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		
	}
	public String getAuthorCitizenship() {
		return authorCitizenship;
	}

	public void setAuthorCitizenship(String authorCitizenship) {
		this.authorCitizenship = authorCitizenship;
	}

	public String getAuthorLocCitizenship() {
		return authorLocCitizenship;
	}

	public void setAuthorLocCitizenship(String authorLocCitizenship) {
		this.authorLocCitizenship = authorLocCitizenship;
	}

}


