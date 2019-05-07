package com.proquest.mtg.dismetadataservice.dashboard.guice;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.proquest.mtg.dismetadataservice.guice.DisMetadataServiceGuiceModule;

public class MyServletContextListener extends GuiceServletContextListener {
	private static final String kParam_props = "dismetadata.appConfigFileName";
	
	private final Logger logger = LoggerFactory.getLogger(MyServletContextListener.class);
	private DisMetadataServiceGuiceModule disMetadataServiceGuiceModule;
	private Injector injector;
	
	@Override
	protected Injector getInjector() {
		return injector;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			String appConfigFileName = getPropertiesFrom(sce);
			if (null != appConfigFileName) {
				disMetadataServiceGuiceModule = new DisMetadataServiceGuiceModule(appConfigFileName);
				injector = Guice.createInjector(new MyServletGuiceModule(),						
						disMetadataServiceGuiceModule);
			}
			super.contextInitialized(sce);
		} catch (Throwable e) {
			logger.error("Failed to initialize ServletContextListener", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//disMetadataServiceGuiceModule.shutdown();
		super.contextDestroyed(sce);
	}
	
	private String getPropertiesFrom(ServletContextEvent sce) {
		String result = sce.getServletContext().getInitParameter(kParam_props);
		if (null == result) {
			logger.error(
					"Failed to get persistence unit name from web.xml.  " +
							"Make sure context-param exists for: " + kParam_props);
		}
		return result;
	}
}
