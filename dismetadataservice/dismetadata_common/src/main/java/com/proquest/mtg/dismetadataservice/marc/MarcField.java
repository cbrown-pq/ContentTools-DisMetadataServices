package com.proquest.mtg.dismetadataservice.marc;

import com.google.common.base.CharMatcher;

public class MarcField implements Comparable<MarcField> {
	public static final int kTagSize = 3;
	public static final String kFieldTerminatorStr = Character.toString(MarcCharSet.kFieldTerminator);
	
	private String tag;
	private String dataWithTerminator;
	
	public MarcField(String tag, String fieldData) {
		setTag(tag);
		setData(fieldData);
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String value) {
		validateTag(value);
		tag = value;
	}
	
	public String getData() {
		return dataWithTerminator.substring(0, dataWithTerminator.length()-1);
	}
	
	public void setData(String value) {
		validateData(value);
		this.dataWithTerminator = 
				value.endsWith(kFieldTerminatorStr) ? 
						value : 
						value + kFieldTerminatorStr;
	}

	public String getDataWithTerminator() {
		return dataWithTerminator;
	}
	
	public boolean hasIllegalChars(CharMatcher charMatcher) {
		return !charMatcher.matchesAllOf(getDataWithTerminator());
	}
	
	@Override
	public int compareTo(MarcField other) {
		return this.getTag().compareTo(other.getTag());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarcField other = (MarcField) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MarcField [tag=" + tag + ", dataWithTerminator="
				+ dataWithTerminator + "]";
	}
	
	private void validateTag(String value) {
		if (null == value || value.length() != kTagSize) {
			throw new IllegalArgumentException(
					"Invalid tag.  Must be exactly " + kTagSize + " characters long.");
		}
	}
	
	private void validateData(String value) {
		if (null == value || tag.length() != kTagSize) {
			throw new IllegalArgumentException("Field Data cannot be null");
		}
	}

}
