package com.proquest.mtg.dismetadataservice.rest.helper;

import java.io.IOException;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.proquest.mtg.dismetadataservice.exodus.IMStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.MStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.format.MetaDataFormatFactory;
import com.proquest.mtg.dismetadataservice.rest.LocMetaDataServiceProvider;
import com.proquest.mtg.dismetadataservice.rest.LocReportServiceProvider;
import com.proquest.mtg.dismetadataservice.rest.PdfDownloadServiceProvider;
import com.proquest.mtg.dismetadataservice.rest.SchoolMetadataServiceProvider;
import com.proquest.mtg.dismetadataservice.rest.SubjectsMetadataServiceProvider;

public class MockServiceTestHelper extends ServiceTestHelperBase {
	
	public MockServiceTestHelper(final MetaDataFormatFactory metaDataFormatFactory,
			final SchoolMetadataServiceProvider schoolMetadata, 
			final SubjectsMetadataServiceProvider subjectsMetadata, 
			final LocMetaDataServiceProvider locMetadata,
			final LocReportServiceProvider locReport,
			final PdfDownloadServiceProvider pdfDownload,
			final MStarPubMetaDataProvider mstarMetadata) 
			throws IllegalArgumentException, IOException {
		
		Injector injector = Guice.createInjector( new AbstractModule() {
									 @Override
									 protected void configure() {
										 bind(MetaDataFormatFactory.class).toInstance(metaDataFormatFactory);
										 bind(SchoolMetadataServiceProvider.class).toInstance(schoolMetadata);
										 bind(SubjectsMetadataServiceProvider.class).toInstance(subjectsMetadata);
										 bind(LocMetaDataServiceProvider.class).toInstance(locMetadata);
										 bind(LocReportServiceProvider.class).toInstance(locReport);
										 bind(PdfDownloadServiceProvider.class).toInstance(pdfDownload);
										 bind(IMStarPubMetaDataProvider.class).toInstance(mstarMetadata);
									 }
		 	 					});
		
		createHTTPServer(injector);
		
	}
	
}
