package com.proquest.mtg.dismetadataservice.properties;

import java.util.ArrayList;
import java.util.Properties;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcConfig;

public class DisMetadataProperties {

	public final static String EXODUS_DB_URL = "exodus.db.url";
	public final static String EXODUS_USER_NAME = "exodus.db.username";
	public final static String EXODUS_PASSWORD = "exodus.db.password";
	public final static String EXODUS_POOL_SIZE = "exodus.db.poolsize";
	public final static String EXODUS_DB_CLASSTYPE = "exodus.db.classtype";
	public final static String PQ_OPEN_URL_BASE = "pq.open.url.base";
	public static final String SCHOOL_BATCH_SIZE = "school.batch.size";
	public final static String PQ_SERVICE_URL_BASE = "pq.service.url.base";
	public final static String PQ_SERVICE_TIMEOUT_MS = "pq.service.timeout.ms";
	public final static String PQ_SERVICE_USER_AGENT = "pq.service.user.agent";
	public final static String FOP_EXODUS_USER_NAME = "fop.exodus.db.username";
	public final static String FOP_EXODUS_PASSWORD = "fop.exodus.db.password";
	public final static String MR3_SERVICE_URL_BASE = "mr3.service.url.base";
	public final static String ECMS_SERVICE_URL_BASE = "ecms.service.url.base";
	public final static String ECMS_MR3_HEADER_KEY = "ecms.mr3.header.key";
	public final static String ECMS_MR3_HEADER_VALUE = "ecms.mr3.header.value";
	public final static String MR3_SERVICE_FOP_URL_BASE = "mr3.service.fop.url.base";

	public final static ArrayList<String> kRequiredProps = Lists
			.newArrayList(
					EXODUS_DB_URL,
					EXODUS_USER_NAME,
					EXODUS_PASSWORD,
					EXODUS_POOL_SIZE,
					EXODUS_DB_CLASSTYPE,
					PQ_OPEN_URL_BASE,
					SCHOOL_BATCH_SIZE,
					PQ_SERVICE_URL_BASE,
					PQ_SERVICE_TIMEOUT_MS,
					PQ_SERVICE_USER_AGENT,
					FOP_EXODUS_USER_NAME,
					FOP_EXODUS_PASSWORD,
					MR3_SERVICE_URL_BASE,
					ECMS_SERVICE_URL_BASE,
					ECMS_MR3_HEADER_KEY,
					ECMS_MR3_HEADER_VALUE,
					MR3_SERVICE_FOP_URL_BASE);
	
	
	private final JdbcConfig exodusConfig;
	private final String pqOpenUrlBase;
	private final int schoolBatchSize;
	private final String pqServiceURL;
	private final int pqServiceTimeoutMS;
	private final String pqServiceUserAgent;
	private final JdbcConfig fopExodusConfig;
	private final String mr3ServiceURL;
	private final String ecmsServiceURL;
	private final String ecmsMr3HeaderKey;
	private final String ecmsMr3HeaderValue;
	private final String mr3FopServiceURL;

	@Inject
	public DisMetadataProperties(IAppConfigReader appConfigReader) throws Exception {
		this(appConfigReader.getAllProperties());
	}

	public DisMetadataProperties(Properties props) {
		validate(props);
		
		this.exodusConfig = new JdbcConfig(
				props.getProperty(EXODUS_DB_URL),
				props.getProperty(EXODUS_USER_NAME),
				props.getProperty(EXODUS_PASSWORD),
				props.getProperty(EXODUS_DB_CLASSTYPE),
				getIntValueFrom(props, EXODUS_POOL_SIZE));
		
		this.fopExodusConfig = new JdbcConfig(
				props.getProperty(EXODUS_DB_URL),
				props.getProperty(FOP_EXODUS_USER_NAME),
				props.getProperty(FOP_EXODUS_PASSWORD),
				props.getProperty(EXODUS_DB_CLASSTYPE),
				getIntValueFrom(props, EXODUS_POOL_SIZE));
		
		this.pqOpenUrlBase = props.getProperty(PQ_OPEN_URL_BASE);
		this.schoolBatchSize = getIntValueFrom(props, SCHOOL_BATCH_SIZE);
		this.pqServiceURL = props.getProperty(PQ_SERVICE_URL_BASE);
		this.pqServiceUserAgent = props.getProperty(PQ_SERVICE_USER_AGENT);
		this.pqServiceTimeoutMS = getIntValueFrom(props, PQ_SERVICE_TIMEOUT_MS);
		this.mr3ServiceURL = props.getProperty(MR3_SERVICE_URL_BASE);
		this.ecmsServiceURL = props.getProperty(ECMS_SERVICE_URL_BASE);
		this.ecmsMr3HeaderKey = props.getProperty(ECMS_MR3_HEADER_KEY);
		this.ecmsMr3HeaderValue = props.getProperty(ECMS_MR3_HEADER_VALUE);
		this.mr3FopServiceURL = props.getProperty(MR3_SERVICE_FOP_URL_BASE);
	}

	public JdbcConfig getExodusJdbcConfig() {
		return exodusConfig;
	}
	
	public JdbcConfig getFopExodusConfig() {
		return fopExodusConfig;
	}

	public String getPqOpenUrlBase() {
		return pqOpenUrlBase;
	}
	
	private void validate(Properties props) throws IllegalArgumentException {
		for (String propKeyName : kRequiredProps) {
			if (!props.containsKey(propKeyName)) {
				throw new IllegalArgumentException(
						"Invalid DismetadataProperties:  missing required key ="
								+ propKeyName);
			}
		}
	}
	
	private int getIntValueFrom(Properties props, String key) {
		return Integer.parseInt(props.getProperty(key));
	}

	public boolean fakeExodusFlag() {
		return true;
	}

	public int getSchoolBatchSize() {
		return schoolBatchSize;
	}
	
	public String getPQServiceURL() {
		return this.pqServiceURL;
	}
	
	public int getPqServiceTimeoutMS() {
		return pqServiceTimeoutMS;
	}

	public String getPqServiceUserAgent() {
		return pqServiceUserAgent;
	}
	
	public String getMr3ServiceURL() {
		return mr3ServiceURL;
	}
	
	public String getECMSServiceURL() {
		return ecmsServiceURL;
	}
	
	public String getECMSMr3HeaderKey() {
		return ecmsMr3HeaderKey;
	}
	
	public String getECMSMr3HeaderValue() {
		return ecmsMr3HeaderValue;
	}

	public String getMr3FopServiceURL() {
		return mr3FopServiceURL;
	}
	
}