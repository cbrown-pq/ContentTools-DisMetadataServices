package com.proquest.mtg.dismetadataservice.pdf;

import javax.inject.Inject;

import com.proquest.mtg.dismetadataservice.media.IMediaDownloader;
import com.proquest.mtg.dismetadataservice.media.IPubMediaInfoProvider;
import com.proquest.mtg.dismetadataservice.media.MediaDownloadException;
import com.proquest.mtg.dismetadataservice.media.PdfMediaInfo;
import com.proquest.mtg.dismetadataservice.media.PubMediaInfo;
import com.proquest.mtg.dismetadataservice.media.PubMediaInfoProviderFactory;

public class PdfMediaProvider {
	private final PubMediaInfoProviderFactory pubMediaProviderFactory;
	private final IMediaDownloader mediaDownloader;	
	
	@Inject
	public PdfMediaProvider(
			PubMediaInfoProviderFactory pubMediaProviderFactory,
			IMediaDownloader mediaDownloader) {
		this.pubMediaProviderFactory = pubMediaProviderFactory;
		this.mediaDownloader = mediaDownloader;
	}
	
	public PubMediaInfoProviderFactory getPubMediaProviderFactory() {
		return pubMediaProviderFactory;
	}
	
	public IMediaDownloader getMediaDownloader() {
		return mediaDownloader;
	}

	public byte[] downloadFor(String pubNumber,
			PDFDownloadOptions pdfDownloadOptions) throws Exception {
		byte[] content = null;
		System.out.println("PdfMediaProvider download pub:" + pubNumber);
		IPubMediaInfoProvider pubMediaInfoProvider = 
				getPubMediaProviderFactory().create(pdfDownloadOptions.isRestrictionIncluded());
		PubMediaInfo curPubMediaInfo = pubMediaInfoProvider.makeFor(pubNumber.replaceAll("\\n",""));
		System.out.println("curPubMediaInfo:" + curPubMediaInfo.toString());
		PdfMediaInfo curPdfMediaInfo = curPubMediaInfo.getPreferredPdf(
				pdfDownloadOptions.getPdfPriorityOrder());
		if (null == curPdfMediaInfo) {
			throw new MediaDownloadException("PdfMediaInfo is null");
		} else {
			if (pdfDownloadOptions.isCopyrightMsgRequired()) {
				content = getMediaDownloader().download(
						curPdfMediaInfo.getUrlWithPdfCopyrightMsg());
			} else {
				content = getMediaDownloader().download(
						curPdfMediaInfo.getUrlWithoutPdfCopyrightMsg());
			}
		}		
		return content;
	}
}
