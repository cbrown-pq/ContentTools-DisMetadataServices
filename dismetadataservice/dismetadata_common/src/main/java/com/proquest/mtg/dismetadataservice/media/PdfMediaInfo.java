package com.proquest.mtg.dismetadataservice.media;

import java.net.MalformedURLException;
import java.net.URL;

public class PdfMediaInfo {
	private final PdfType pdfType;
	private final String urlSpec;
	
	public PdfMediaInfo(
			PdfType pdfType,
			String url) {
		this.pdfType = pdfType;
		this.urlSpec = url;
	}
	
	public PdfType getPdfType() {
		return pdfType;
	}
	
	public String getUrlSpec() {
		return urlSpec;
	}
	
	public URL getUrl() throws MalformedURLException {
		return new URL(getUrlSpec());
	}

	@Override
	public String toString() {
		return "PdfInfo [pdfType=" + pdfType + ", urlSpec=" + urlSpec + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pdfType == null) ? 0 : pdfType.hashCode());
		result = prime * result + ((urlSpec == null) ? 0 : urlSpec.hashCode());
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
		PdfMediaInfo other = (PdfMediaInfo) obj;
		if (pdfType != other.pdfType)
			return false;
		if (urlSpec == null) {
			if (other.urlSpec != null)
				return false;
		} else if (!urlSpec.equals(other.urlSpec))
			return false;
		return true;
	}
}
