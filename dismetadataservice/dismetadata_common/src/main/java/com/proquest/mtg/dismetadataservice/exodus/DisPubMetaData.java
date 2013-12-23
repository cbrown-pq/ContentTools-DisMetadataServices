package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.metadata.Author;

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
	private List<DissLanguage> dissLanguages;
	private List<CmteMember> cmteMembers;
	private List<Subject> subjects;
	private List<SuppFile> suppFiles;
	private List<String> departments;
	private List<Keyword> keywords;
	private String dissAbstract;

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

}
