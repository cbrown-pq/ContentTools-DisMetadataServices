package com.proquest.mtg.dismetadataservice.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proquest.mtg.dismetadataservice.exodus.ExodusDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.ICSVProvider;
import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;
import com.proquest.mtg.dismetadataservice.exodus.IPubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.ISchoolMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.ISubjectsMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.PubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.SchoolMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.SubjectsMetaDataProvider;
import com.proquest.mtg.dismetadataservice.format.CSVFormat;
import com.proquest.mtg.dismetadataservice.format.FakeFormat;
import com.proquest.mtg.dismetadataservice.format.Marc21RdaFormat;
import com.proquest.mtg.dismetadataservice.format.MetaDataFormatFactory;
import com.proquest.mtg.dismetadataservice.format.USMarcFormat;
import com.proquest.mtg.dismetadataservice.helper.WellKnownFormatTypes;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.properties.AppConfigReader;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.proquest.mtg.dismetadataservice.properties.IAppConfigReader;
import com.proquest.mtg.utils.writer.IWriter;
import com.proquest.mtg.utils.writer.StringWriter;

public class DisMetadataServiceGuiceModule extends AbstractModule {
	
	private final AppConfigReader appConfigReader;
	private IJdbcConnectionPool exodusConnectionPool;
	
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
	
	@Provides @Singleton
	protected MetaDataFormatFactory formatTaskFactories(DisMetadataProperties props,
						FakeFormat fakeFormat, USMarcFormat usMarcFormat,CSVFormat csvFromat,Marc21RdaFormat marc21RdaFormat) {
		MetaDataFormatFactory result = new MetaDataFormatFactory();
		if (props.fakeExodusFlag()) {
			result.add(WellKnownFormatTypes.FAKE_MARC_TESTING, fakeFormat);
			result.add(WellKnownFormatTypes.USMARC, usMarcFormat);
			result.add(WellKnownFormatTypes.CSV, csvFromat);
			result.add(WellKnownFormatTypes.MARC21RDA, marc21RdaFormat);
		}
		else {
			result.add(WellKnownFormatTypes.FAKE_MARC_TESTING, fakeFormat);
			result.add(WellKnownFormatTypes.USMARC, usMarcFormat);
			result.add(WellKnownFormatTypes.CSV, csvFromat);
			result.add(WellKnownFormatTypes.MARC21RDA, marc21RdaFormat);
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
		bind(IWriter.class).to(StringWriter.class);
	}

}
