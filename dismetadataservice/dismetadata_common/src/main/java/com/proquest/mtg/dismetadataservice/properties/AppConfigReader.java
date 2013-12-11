package com.proquest.mtg.dismetadataservice.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.Inject;

public class AppConfigReader implements IAppConfigReader {
	
	private final String propertyFileName;

	@Inject
	public AppConfigReader(String propertyFileName) {
		this.propertyFileName = propertyFileName;
	}

	@Override
	public Properties getAllProperties() throws Exception {
		return loadFrom();
	}
	
	private Properties loadFrom() throws Exception {
		
		Properties props = new Properties();
	    ClassLoader classLoader = Thread.currentThread()
	                    .getContextClassLoader();
	    InputStream in = classLoader.getResourceAsStream(getPropertyFileName());
	    if (null == in) {
	            throw new IOException("Failed to getSystemResourceAsStream = "
	                            + getPropertyFileName());
	    }
	    try {
	            props.load(in);
	           
	    } finally {
	            in.close();
	    }
		return props;
	}

	public String getPropertyFileName() {
		return propertyFileName;
	}

}
