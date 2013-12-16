package com.proquest.mtg.dismetadataservice.metadata;


public class Author {
    private String authorFullName;
    private String firstName;
    private String lastName;
    private String middleName;
    private String altAuthorFullName;
    private String language;
    //private List<Dissertation.Authors.Author.Degree> degree;
    private int sequenceNumber;

    
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String value) {
		this.language = value;
	}

}


