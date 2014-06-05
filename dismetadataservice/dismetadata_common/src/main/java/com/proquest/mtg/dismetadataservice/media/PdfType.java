package com.proquest.mtg.dismetadataservice.media;

import com.google.common.collect.ImmutableSet;

public enum PdfType {
	SEARCHABLE("SPDF"), 
	NATIVE("NPDF"),
	IMAGE_300DPI("300PDF"), 
	IMAGE_600DPI("600PDF"); 
	
    private String value;

    PdfType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static PdfType fromString(String value) {
        if (null == value) {
            throw new IllegalArgumentException();
        }
        for (PdfType v : values()) {
            if (value.equalsIgnoreCase(v.getValue())) {
            	return v;
            }
        }
        throw new IllegalArgumentException();
    }
    
        
    public static final ImmutableSet<PdfType> kPriorityOrderForDistribution = ImmutableSet.of(
			PdfType.NATIVE, PdfType.IMAGE_300DPI, PdfType.IMAGE_600DPI, PdfType.SEARCHABLE);
    
}
