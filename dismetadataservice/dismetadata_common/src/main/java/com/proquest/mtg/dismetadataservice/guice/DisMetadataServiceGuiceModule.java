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
//import com.proquest.mtg.dismetadataservice.exodus.ExternalUrlDataProvider;
//import com.proquest.mtg.dismetadataservice.exodus.FOPEligiblePubsProvider;
//import com.proquest.mtg.dismetadataservice.exodus.FopFormatsDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.ICSVProvider;
//import com.proquest.mtg.dismetadataservice.exodus.IExternalUrlDataProvider;
//import com.proquest.mtg.dismetadataservice.exodus.IFOPEligiblePubsProvider;
import com.proquest.mtg.dismetadataservice.exodus.IMStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;
import com.proquest.mtg.dismetadataservice.exodus.IPubMetaDataProvider;
//import com.proquest.mtg.dismetadataservice.exodus.ISchoolMetaDataProvider;
//import com.proquest.mtg.dismetadataservice.exodus.ISubjectsMetaDataProvider;
//import com.proquest.mtg.dismetadataservice.exodus.MStarPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.PubMetaDataProvider;
//import com.proquest.mtg.dismetadataservice.exodus.SchoolMetaDataProvider;
//import com.proquest.mtg.dismetadataservice.exodus.SubjectsMetaDataProvider;
import com.proquest.mtg.dismetadataservice.fop.FopMetaDataProvider;
import com.proquest.mtg.dismetadataservice.fop.IFopMetaDataProvider;
import com.proquest.mtg.dismetadataservice.format.CSVFormat;
import com.proquest.mtg.dismetadataservice.format.FakeFormat;
import com.proquest.mtg.dismetadataservice.format.IFopFormatsDataProvider;
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
import com.proquest.mtg.dismetadataservice.vms.IVmsMetaDataProvider;
import com.proquest.mtg.dismetadataservice.vms.VmsMetaDataProvider;
import com.proquest.mtg.services.gossamer.docfrosting.DocumentfrostingObjectIdResource;
import com.proquest.mtg.services.gossamer.docfrosting.DocumentfrostingObjectIdResource_ClientStub;
import com.proquest.mtg.services.gossamer.relatedids.RelatedidsGoidIdtypeIdResource;
import com.proquest.mtg.services.gossamer.relatedids.RelatedidsGoidIdtypeIdResource_ClientStub;
import com.proquest.mtg.utils.writer.IWriter;
import com.proquest.mtg.utils.writer.StringWriter;

@SuppressWarnings("deprecation")
public class DisMetadataServiceGuiceModule extends AbstractModule {
	
	private final AppConfigReader appConfigReader;
	//private IJdbcConnectionPool exodusConnectionPool;
	//private IJdbcConnectionPool exodusFopConnectionPool;
	
	//@SuppressWarnings("deprecation")
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
	
	/*public void shutdown() {
		shutdownExodusConnectionPool();
		shutdownFopExodusConnectionPool();
	}

	private void shutdownFopExodusConnectionPool() {
		if (null != exodusFopConnectionPool) {
			try {
				exodusFopConnectionPool.destroy();
			} catch (Exception e) {
				logger.error("Failed to destory Exodus JDBC Connection Pool, because: " + e.getMessage(), e);
			}
		}
		
	}

	private void shutdownExodusConnectionPool() {
		if (null != exodusConnectionPool) {
			try {
				exodusConnectionPool.destroy();
			} catch (Exception e) {
				logger.error("Failed to destory Exodus JDBC Connection Pool, because: " + e.getMessage(), e);
			}
		}
	}*/
	
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
	
	@Provides @Named(DisMetadataProperties.MR3_SERVICE_URL_BASE)
	protected String mr3ServiceUserAgent(DisMetadataProperties props) {
		return props.getMr3ServiceURL();
	}
	
	@Provides @Named(DisMetadataProperties.ECMS_SERVICE_URL_BASE)
	protected String ecmsServiceUserAgent(DisMetadataProperties props) {
		return props.getECMSServiceURL();
	}
	
	@Provides @Named(DisMetadataProperties.ECMS_MR3_HEADER_KEY) 
	protected String ecmsMr3Header(DisMetadataProperties props) { 
		return props.getECMSMr3HeaderKey();
	}
	@Provides @Named(DisMetadataProperties.ECMS_MR3_HEADER_VALUE) 
	protected String ecmsMr3User(DisMetadataProperties props) { 
		return props.getECMSMr3HeaderValue(); }
	
	@Provides @Named(DisMetadataProperties.MR3_SERVICE_FOP_URL_BASE) 
	protected String mr3FopUrl(DisMetadataProperties props) { 
		return props.getMr3FopServiceURL(); }
	
	@Provides @Named(DisMetadataProperties.VMS_DB_URL) 
	protected String vmDbUrl(DisMetadataProperties props) { 
		return props.getVmDbUrl(); }
	 
	@Provides @Named(DisMetadataProperties.VMS_DB_USER_NAME) 
	protected String vmDbUserName(DisMetadataProperties props) { 
		return props.getVmDbUserName(); }
	 
	@Provides @Named(DisMetadataProperties.VMS_DB_PASSWORD) 
	protected String vmDbUserPassword(DisMetadataProperties props) { 
		return props.getVmDbPassword(); }
	 
	@Provides @Named(DisMetadataProperties.FOP_DB_URL) 
	protected String fopDbUrl(DisMetadataProperties props) { 
		return props.getFopDbUrl(); }
	 
	@Provides @Named(DisMetadataProperties.FOP_DB_USER_NAME) 
	protected String fopDbUserName(DisMetadataProperties props) { 
		return props.getFopDbUserName(); }
	 
	@Provides @Named(DisMetadataProperties.FOP_DB_PASSWORD) 
	protected String fopDbUserPassword(DisMetadataProperties props) { 
		return props.getFopDbPassword(); }
	
	@Provides @Named(DisMetadataProperties.FOP_DB_CLASS_NAME) 
	protected String fopDbClass(DisMetadataProperties props) { 
		return props.getFopDbClass(); }
	
	
	@Provides @Named(DisMetadataProperties.OPTIMUS_URL_BASE) 
	protected String optimusUrlBase(DisMetadataProperties props) { 
		return props.getOptimusUrlBase(); }

	@Provides @Named(DisMetadataProperties.OPTIMUS_KEY) 
	protected String optimusKey(DisMetadataProperties props) { 
		return props.getOptimusKey(); }

	@Provides @Named(DisMetadataProperties.OPTIMUS_SECRET_KEY) 
	protected String optimusSecretKey(DisMetadataProperties props) { 
		return props.getOptimusSecretKey(); }

	//@SuppressWarnings("deprecation")
	@Provides @Singleton
	protected ClientConnectionManager getHttpClientConnectionManager() {
		int workerThreadCount = 2;
		httpClientConnectionManager = 
				new ThreadSafeClientConnManager(SchemeRegistryFactory.createDefault());
		httpClientConnectionManager.setDefaultMaxPerRoute(workerThreadCount);
		return httpClientConnectionManager;
	}
	
	//@Provides @Singleton @Named(IJdbcConnectionPool.kExodusConnectionPool)
	//protected IJdbcConnectionPool exodusConnectionPool(DisMetadataProperties props) {
	//	IJdbcConnectionPool result = null;
	//	try {
	//		return new JdbcConnectionPool(props.getExodusJdbcConfig());
	//	} catch (Exception e) {
	//		logger.error("Failed to initialize Exodus JDBC Connection Pool, because: " + e.getMessage(), e);
	//	}
	//	return result;
	//}
	
	//@Provides @Singleton @Named(IJdbcConnectionPool.kFopExodusConnectionPool)
	//protected IJdbcConnectionPool fopExodusConnectionPool(DisMetadataProperties props) {
	//	IJdbcConnectionPool result = null;
	//	try {
	//		return new JdbcConnectionPool(props.getFopExodusConfig());
	//	} catch (Exception e) {
	//		logger.error("Failed to initialize Exodus JDBC Connection Pool, because: " + e.getMessage(), e);
	//	}
	//	return result;
	//}
	
	//@SuppressWarnings("deprecation")
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
		//bind(ISchoolMetaDataProvider.class).to(SchoolMetaDataProvider.class);
		//bind(ISubjectsMetaDataProvider.class).to(SubjectsMetaDataProvider.class);
		bind(IMediaDownloader.class).to(MediaDownloader.class);
		bind(IWriter.class).to(StringWriter.class);
		//bind(IMStarPubMetaDataProvider.class).to(MStarPubMetaDataProvider.class);
		//bind(IExternalUrlDataProvider.class).to(ExternalUrlDataProvider.class);
		//bind(IFOPEligiblePubsProvider.class).to(FOPEligiblePubsProvider.class);
	//	bind(IFopFormatsDataProvider.class).to(FopFormatsDataProvider.class);
		bind(IVmsMetaDataProvider.class).to(VmsMetaDataProvider.class);
	}

}
