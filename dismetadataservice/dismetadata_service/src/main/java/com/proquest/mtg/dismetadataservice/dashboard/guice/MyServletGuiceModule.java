package com.proquest.mtg.dismetadataservice.dashboard.guice;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
//import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;


public class MyServletGuiceModule extends ServletModule {
	
	@Override
    protected void configureServlets() {
		serve("/rest/*").with(GuiceContainer.class);
		ResourceConfig rc = new PackagesResourceConfig( "com.proquest.mtg.dismetadataservice.rest");
		 for ( Class<?> resource : rc.getClasses() ) {
			 bind( resource );
		 }
	}

}
