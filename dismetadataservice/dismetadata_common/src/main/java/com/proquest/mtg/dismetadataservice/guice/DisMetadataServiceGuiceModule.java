package com.proquest.mtg.dismetadataservice.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proquest.gossamer.ApacheGossamerServiceClient;
import com.proquest.gossamer.GossamerServiceClient;
import com.proquest.mtg.dismetadataservice.exodus.ExodusDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.ExternalUrlDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.FOPEligiblePubsProvider;
import com.proquest.mtg.dismetadataservice.exodus.ICSVProvider;
import com.proquest.mtg.dismetadataservice.exodus.IExternalUrlDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.IFOPEligiblePubsProvider;
import com.proquest.mtg.dismetadataservice.exodus.IMStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;
import com.proquest.mtg.dismetadataservice.exodus.IPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.ISchoolMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.ISubjectsMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.MStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.PubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.SchoolMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.SubjectsMetaDataProvider;
import com.proquest.mtg.dismetadataservice.format.CSVFormat;
import com.proquest.mtg.dismetadataservice.format.FakeFormat;
import com.proquest.mtg.dismetadataservice.format.Marc21RdaFormat;
import com.proquest.mtg.dismetadataservice.format.MarcXmlFormat;
import com.proquest.mtg.dismetadataservice.format.MetaDataFormatFactory;
import com.proquest.mtg.dismetadataservice.format.USMarcFormat;
import com.proquest.mtg.dismetadataservice.helper.WellKnownFormatTypes;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.media.IMediaDownloader;
import com.proquest.mtg.dismetadataservice.media.MediaDownloader;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.properties.AppConfigReader;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.proquest.mtg.dismetadataservice.properties.IAppConfigReader;
import com.proquest.mtg.services.gossamer.docfrosting.DocumentfrostingObjectIdResource;
import com.proquest.mtg.services.gossamer.docfrosting.DocumentfrostingObjectIdResource_ClientStub;
import com.proquest.mtg.services.gossamer.relatedids.RelatedidsGoidIdtypeIdResource;
import com.proquest.mtg.services.gossamer.relatedids.RelatedidsGoidIdtypeIdResource_ClientStub;
import com.proquest.mtg.utils.writer.IWriter;
import com.proquest.mtg.utils.writer.StringWriter;

public class DisMetadataServiceGuiceModule extends AbstractModule {
	
	private final AppConfigReader appConfigReader;
	private IJdbcConnectionPool exodusConnectionPool;
	private ThreadSafeClientConnManager httpClientConnectionManager;
	
	private final Logger logger = LoggerFactory.getLogger(DisMetadataServiceGuiceModule.class);
	
	public DisMetadataServiceGuiceModule(String appConfigFileName) {
		try {
			
			appConfigReader = new AppConfigReader(appConfigFileName);
			
		} catch (RuntimeException e) {
			logger.error("Failed to initialize AppConfigReader. Properties File Name: "  + appConfigFileName, e);
			throw e;
		}
	}
	
	public void shutdown() {
		shutdownExodusConnectionPool();
	}

	private void shutdownExodusConnectionPool() {
		if (null != exodusConnectionPool) {
			try {
				exodusConnectionPool.destroy();
			} catch (Exception e) {
				logger.error("Failed to destory Exodus JDBC Connection Pool, because: " + e.getMessage(), e);
			}
		}
	}
	
	@Provides @Named(DisMetadataProperties.PQ_OPEN_URL_BASE)
	protected String pqOpenUrlBase(DisMetadataProperties props) {
		return props.getPqOpenUrlBase();
	}
	
	@Provides @Named(DisMetadataProperties.SCHOOL_BATCH_SIZE)
	protected int schoolbatchSize(DisMetadataProperties props) {
		return props.getSchoolBatchSize();
	}
	
	@Provides @Named(DisMetadataProperties.PQ_SERVICE_URL_BASE)
	protected String pqUrlBase(DisMetadataProperties props) {
		return props.getPQServiceURL();
	}
	
	@Provides @Named(DisMetadataProperties.PQ_SERVICE_TIMEOUT_MS)
	protected int pqSeviceTimeoutMs(DisMetadataProperties props) {
		return props.getPqServiceTimeoutMS();
	}
	
	@Provides @Named(DisMetadataProperties.PQ_SERVICE_USER_AGENT)
	protected String pqServiceUserAgent(DisMetadataProperties props) {
		return props.getPqServiceUserAgent();
	}
	
	@Provides @Singleton
	protected ClientConnectionManager getHttpClientConnectionManager() {
		int workerThreadCount = 2;
		httpClientConnectionManager = 
				new ThreadSafeClientConnManager(SchemeRegistryFactory.createDefault());
		httpClientConnectionManager.setDefaultMaxPerRoute(workerThreadCount);
		return httpClientConnectionManager;
	}
	
	@Provides @Singleton @Named(IJdbcConnectionPool.kExodusConnectionPool)
	protected IJdbcConnectionPool exodusConnectionPool(DisMetadataProperties props) {
		IJdbcConnectionPool result = null;
		try {
			return new JdbcConnectionPool(props.getExodusJdbcConfig());
		} catch (Exception e) {
			logger.error("Failed to initialize Exodus JDBC Connection Pool, because: " + e.getMessage(), e);
		}
		return result;
	}
	
	@Provides @Singleton GossamerServiceClient getGossamerServiceClient(
			@Named(DisMetadataProperties.PQ_SERVICE_URL_BASE) String pqServicesUrlBase,
			@Named(DisMetadataProperties.PQ_SERVICE_TIMEOUT_MS) int timeoutMs,
			@Named(DisMetadataProperties.PQ_SERVICE_USER_AGENT) String userAgent,
			ClientConnectionManager connectionManager) {
		
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		
		return new ApacheGossamerServiceClient(
				userAgent,
				pqServicesUrlBase,
				timeoutMs, 
				connectionManager, 
				params);
	}
	
	@Provides @Singleton RelatedidsGoidIdtypeIdResource getRelatedidsGoidIdtypeIdResource(
			GossamerServiceClient gossamerServiceClient) {
		return new RelatedidsGoidIdtypeIdResource_ClientStub(gossamerServiceClient);
	}
	
	@Provides @Singleton DocumentfrostingObjectIdResource getDocumentfrostingObjectIdResource(
			GossamerServiceClient gossamerServiceClient) {
		return new DocumentfrostingObjectIdResource_ClientStub(gossamerServiceClient);
	}
	
	@Provides @Singleton
	protected MetaDataFormatFactory formatTaskFactories(DisMetadataProperties props,
						FakeFormat fakeFormat, USMarcFormat usMarcFormat,
						CSVFormat csvFromat, Marc21RdaFormat marc21RdaFormat,
						MarcXmlFormat marcXml) {
		MetaDataFormatFactory result = new MetaDataFormatFactory();
		if (props.fakeExodusFlag()) {
			result.add(WellKnownFormatTypes.FAKE_MARC_TESTING, fakeFormat);
			result.add(WellKnownFormatTypes.USMARC, usMarcFormat);
			result.add(WellKnownFormatTypes.CSV, csvFromat);
			result.add(WellKnownFormatTypes.MARC21RDA, marc21RdaFormat);
			result.add(WellKnownFormatTypes.MARCXML, marcXml);
		}
		else {
			result.add(WellKnownFormatTypes.FAKE_MARC_TESTING, fakeFormat);
			result.add(WellKnownFormatTypes.USMARC, usMarcFormat);
			result.add(WellKnownFormatTypes.CSV, csvFromat);
			result.add(WellKnownFormatTypes.MARC21RDA, marc21RdaFormat);
			result.add(WellKnownFormatTypes.MARCXML, marcXml);
		}
		return result;
	}
	
	@Override
	protected void configure() {
		bind(IAppConfigReader.class).toInstance(appConfigReader);
		bind(DisMetadataProperties.class).asEagerSingleton();
		bind(DisGenMappingProvider.class).asEagerSingleton();
		bind(IMarcProvider.class).to(ExodusDataProvider.class);
		bind(ICSVProvider.class).to(ExodusDataProvider.class);
		bind(IPubMetaDataProvider.class).to(PubMetaDataProvider.class);
		bind(ISchoolMetaDataProvider.class).to(SchoolMetaDataProvider.class);
		bind(ISubjectsMetaDataProvider.class).to(SubjectsMetaDataProvider.class);
		bind(IMediaDownloader.class).to(MediaDownloader.class);
		bind(IWriter.class).to(StringWriter.class);
		bind(IMStarPubMetaDataProvider.class).to(MStarPubMetaDataProvider.class);
		bind(IExternalUrlDataProvider.class).to(ExternalUrlDataProvider.class);
		bind(IFOPEligiblePubsProvider.class).to(FOPEligiblePubsProvider.class);
	}

}
