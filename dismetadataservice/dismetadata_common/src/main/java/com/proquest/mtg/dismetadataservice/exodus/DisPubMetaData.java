package com.proquest.mtg.dismetadataservice.exodus;

import java.math.BigInteger;
import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.metadata.Author;

public class DisPubMetaData {
	private BigInteger dateOfExtraction;
	private String pubNumber;
	private String itemId;
	private String isbn;
	private String pubPageNum;
	private String pageCount;
	private String publisher;
	private String blNumber;
	private String referenceLocation;
	private String externalURL;
	private List<Author> authors;
	private List<DissLanguage> dissLanguages;
	private List<CmteMember> cmteMembers;
	private List<Subject> subjects;
	private List<SuppFile> suppFiles;
	private List<String> departments;
	private List<Keyword> keywords;
	private List<SalesRestriction> salesRestrictions;
	private List<FormatRestriction> formatRestrictions;
	private String dissAbstract;
	private Batch batch;
	private List<AlternateTitle> alternateTitle;
	private Advisors advisors;
	private Title title;
	private School school;
	private PdfStatus pdfStatus;

	public BigInteger getDateOfExtraction() {
		return dateOfExtraction;
	}
	
	public void setDateOfExtraction(BigInteger dateOfExtraction) {
		this.dateOfExtraction = dateOfExtraction;
	}

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

	public void setAuthors(Iterable<Author> authors) {
		this.authors = Lists.newArrayList(authors);
	}
	
	public List<DissLanguage> getDissLanguages() {
		return dissLanguages;
	}

	public void setDissLanguages(Iterable<DissLanguage> value) {
		this.dissLanguages = Lists.newArrayList(value);
	}

	public String getAbstract() {
		return dissAbstract;
	}
	
	public void setAbstract(String value) {
		this.dissAbstract = value;
	}
	
	public void setCmteMembers(Iterable<CmteMember> value) {
		if (null == value) {
			this.cmteMembers = Lists.newArrayList();
		} else {
			this.cmteMembers = Lists.newArrayList(value);
		}
	}
	
	public List<CmteMember> getCmteMembers() {
		return cmteMembers;
	}
	
	
	public void setSubjects(Iterable<Subject> value) {
		if (null == value) {
			this.subjects = Lists.newArrayList();
		} else {
			this.subjects = Lists.newArrayList(value);
		}
	}
	
	public List<Subject> getSubjects() {
		return subjects;
	}
	
	public void setSuppFiles(Iterable<SuppFile> value) {
		if (null == value) {
			this.suppFiles = Lists.newArrayList();
		} else {
			this.suppFiles = Lists.newArrayList(value);
		}
	}
	
	public List<SuppFile> getSuppFiles() {
		return suppFiles;
	}
	
	public void setDepartments(Iterable<String> value) {
		if (null == value) {
			this.departments = Lists.newArrayList();
		} else {
			this.departments = Lists.newArrayList(value);
		}
	}
	
	public List<String> getDepartments() {
		return departments;
	}
	
	public void setKeywords(Iterable<Keyword> value) {
		if (null == value) {
			this.keywords = Lists.newArrayList();
		} else {
			this.keywords = Lists.newArrayList(value);
		}
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}
	
	public void setSalesRestrictions(Iterable<SalesRestriction> value) {
		if (null == value) {
			this.salesRestrictions = Lists.newArrayList();
		} else {
			this.salesRestrictions = Lists.newArrayList(value);
		}
	}
	
	public List<SalesRestriction> getSalesRestrictions() {
		return salesRestrictions;
	}
	
	public List<FormatRestriction> getFormatRestrictions() {
		return formatRestrictions;
	}
	
	public void setFormatRestrictions(Iterable<FormatRestriction> value) {
		if (null == value) {
			this.formatRestrictions = Lists.newArrayList();
		} else {
			this.formatRestrictions = Lists.newArrayList(value);
		}
	}
		
	public void setBatch(Batch value) {
		this.batch = value;
	}
	
	public Batch getBatch() {
		return batch;
	}
	            
	public void setAlternateTitles(Iterable<AlternateTitle> value) {
		if (null == value) {
			this.alternateTitle = Lists.newArrayList();
		} else {
			this.alternateTitle = Lists.newArrayList(value);
		}
	}

	public List<AlternateTitle> getAlternateTitles() {
		return alternateTitle;
	}


	public void setAdvisors(Advisors value) {
		this.advisors = value;
	}
	
	public Advisors getAdvisors() {
		return advisors;
	}
	
	public void setTitle(Title value) {
		this.title = value;
	}

	public Title getTitle() {
		return title;
	}

	public void setSchool(School value) {
		this.school = value;
	}
	
	public School getSchool() {
		return school;
	}
	
	public PdfStatus getPdfStatus() {
		return pdfStatus;
	}
	
	public void setPdfStatus(PdfStatus value) {
		this.pdfStatus = value;
	}
	
	public void setPublisher(String value) {
		this.publisher = value;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	@Override
	public String toString() {
		return "DisPubMetaData [pubNumber=" + pubNumber + ", itemId=" + itemId
				+ ", isbn=" + isbn + ", pubPageNum=" + pubPageNum
				+ ", pageCount=" + pageCount + ", blNumber=" + blNumber
				+ ", referenceLocation=" + referenceLocation + ", externalURL="
				+ externalURL + ", authors=" + authors + ", language="
				+ dissLanguages + "]";
	}

	public static class DissLanguage {
		private final String languageDescription;
		private final String languageCode;
		
		public DissLanguage(String languageDescription, String languageCode) {
			this.languageDescription = languageDescription;
			this.languageCode = languageCode;
		}
		
		public String getLanguageCode() {
			return languageCode;
		}
		
		public String getLanguageDescription() {
			return languageDescription;
		}

		@Override
		public String toString() {
			return "Language [languageDescription=" + languageDescription
					+ ", languageCode=" + languageCode + "]";
		}
		
	}
	
	public static class CmteMember {
        protected String firstName;        
        protected String middleName;        
        protected String lastName;
        protected String suffix;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String value) {
            this.firstName = value;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String value) {
            this.middleName = value;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String value) {
            this.lastName = value;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String value) {
            this.suffix = value;
        }
    }
	
	public static class Subject {
        private String subjectGroupDesc;
        private String subjectCode;
        private String subjectDesc;
        private Integer sequenceNumber;

        public String getSubjectGroupDesc() {
            return subjectGroupDesc;
        }

        public void setSubjectGroupDesc(String value) {
            this.subjectGroupDesc = value;
        }

        public String getSubjectCode() {
            return subjectCode;
        }

        public void setSubjectCode(String value) {
            this.subjectCode = value;
        }

        public String getSubjectDesc() {
            return subjectDesc;
        }

        public void setSubjectDesc(String value) {
            this.subjectDesc = value;
        }

        public Integer getSequenceNumber() {
            return sequenceNumber;
        }

        public void setSequenceNumber(Integer value) {
            this.sequenceNumber = value;
        }
    }
	
	public static class SuppFile {
        private String suppFileCategory;
        private String suppFilename;
        private String suppFileDesc;

        public String getSuppFileCategory() {
            return suppFileCategory;
        }

        public void setSuppFileCategory(String value) {
            this.suppFileCategory = value;
        }

        public String getSuppFilename() {
            return suppFilename;
        }

        public void setSuppFilename(String value) {
            this.suppFilename = value;
        }

        public String getSuppFileDesc() {
            return suppFileDesc;
        }

        public void setSuppFileDesc(String value) {
            this.suppFileDesc = value;
        }
    }
	
    public static class Keyword {
        private String value;
        private String source;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String value) {
            this.source = value;
        }

    }
    
    public static class SalesRestriction {
    	private String code;
        private String description;
        private String restrictionStartDate;
        private String restrictionEndDate;

        public String getDescription() {
            return description;
        }

        public void setDescription(String value) {
            this.description = value;
        }

		public String getCode() {
			return code;
		}

		public void setCode(String value) {
			this.code = value;
		}

		public String getRestrictionStartDate() {
			return restrictionStartDate;
		}

		public void setRestrictionStartDate(String value) {
			this.restrictionStartDate = value;
		}

		public String getRestrictionEndDate() {
			return restrictionEndDate;
		}

		public void setRestrictionEndDate(String value) {
			this.restrictionEndDate = value;
		}
    }
    
    public static class FormatRestriction {
    	private String code;

		public String getCode() {
			return code;
		}

		public void setCode(String value) {
			this.code = value;
		}
    }
    
    public static class Batch {
        private String dbTypeCode;
        private String dbTypeDesc;
        private String volumeIssue;
        private String daiSectionCode;

        public String getDBTypeCode() {
            return dbTypeCode;
        }

        public void setDBTypeCode(String value) {
            this.dbTypeCode = value;
        }

        public String getDBTypeDesc() {
            return dbTypeDesc;
        }

        public void setDBTypeDesc(String value) {
            this.dbTypeDesc = value;
        }

        public String getVolumeIssue() {
            return volumeIssue;
        }

        public void setVolumeIssue(String value) {
            this.volumeIssue = value;
        }

        public String getDAISectionCode() {
            return daiSectionCode;
        }

        public void setDAISectionCode(String value) {
            this.daiSectionCode = value;
        }
    }
    
    public static class AlternateTitle {
        protected String altTitle;
        protected String language;

        public String getAltTitle() {
            return altTitle;
        }

        public void setAltTitle(String value) {
            this.altTitle = value;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String value) {
            this.language = value;
        }
    }
    
    public static class Advisors {
        private String advisorsExodusStr;
        private List<Advisor> advisors;
        
        public String getAdvisorsExodusStr() {
            return advisorsExodusStr;
        }
        public void setAdvisorsExodusStr(String value) {
            this.advisorsExodusStr = value;
        }
        public List<Advisor> getAdvisor() {
            return advisors;
        }
        public void setAdvisor(Iterable<Advisor> value) {
        	if (null == value) {
        		this.advisors = Lists.newArrayList();
        	} else {
        		this.advisors = Lists.newArrayList(value);
        	}
        }
    }
    
    public static class Advisor {
        private String advisorFullName;
        private String altAdvisorFullName;
        
        public String getAdvisorFullName() {
            return advisorFullName;
        }
        public void setAdvisorFullName(String value) {
            this.advisorFullName = value;
        }
        public String getAltAdvisorFullName() {
            return altAdvisorFullName;
        }
        public void setAltAdvisorFullName(String value) {
            this.altAdvisorFullName = value;
        }
    }
    
    public static class Title {
        private String electronicTitle;
        private String masterTitle;
        private String englishOverwriteTitle;
        private String foreignTitle;
        
		public String getElectronicTitle() {
			return electronicTitle;
		}
		public void setElectronicTitle(String value) {
			this.electronicTitle = value;
		}
		public String getMasterTitle() {
			return masterTitle;
		}
		public void setMasterTitle(String value) {
			this.masterTitle = value;
		}
		public String getEnglishOverwriteTitle() {
			return englishOverwriteTitle;
		}
		public void setEnglishOverwriteTitle(String value) {
			this.englishOverwriteTitle = value;
		}
		public String getForeignTitle() {
			return foreignTitle;
		}
		public void setForeignTitle(String value) {
			this.foreignTitle = value;
		}
    }
    
    public static class School {
        private String schoolCode;
        private String schoolName;
        private String schoolCountry;
        private String schoolState;

        public String getSchoolCode() {
            return schoolCode;
        }

        public void setSchoolCode(String value) {
            this.schoolCode = value;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String value) {
            this.schoolName = value;
        }

        public String getSchoolCountry() {
            return schoolCountry;
        }

        public void setSchoolCountry(String value) {
            this.schoolCountry = value;
        }

        public String getSchoolState() {
            return schoolState;
        }

        public void setSchoolState(String value) {
            this.schoolState = value;
        }
    }
    
    public static class PdfStatus {
        private boolean pdfAvailableStatus;
        private String pdfAvailableDate;
        
		public String getPdfAvailableDate() {
			return pdfAvailableDate;
		}

		public void setPdfAvailableDate(String value) {
			this.pdfAvailableDate = value;
		}

		public boolean isPdfAvailable() {
			return pdfAvailableStatus;
		}

		public void setPdfAvailableStatus(boolean value) {
			this.pdfAvailableStatus = value;
		}
    }
}
