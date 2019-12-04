/*package com.proquest.mtg.dismetadataservice.properties;

import java.util.ArrayList;
import java.util.Properties;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
 
public class OptimusServiceProviderProperties {

	public final static String OPTIMUS_URL_BASE = "optimus.api.url";
	
	public final static ArrayList<String> kRequiredProps = Lists
			.newArrayList(
					OPTIMUS_URL_BASE);
	
	private final String optimusUrlBase;
	
	@Inject
	public OptimusServiceProviderProperties(IAppConfigReader appConfigReader) throws Exception {
		this(appConfigReader.getAllProperties());
	}

	public OptimusServiceProviderProperties(Properties props) {
		validate(props);
		this.optimusUrlBase = props.getProperty(OPTIMUS_URL_BASE);
	}

	public String getoptimusUrlBase() {
		return optimusUrlBase;
	}
	
	private void validate(Properties props) throws IllegalArgumentException {
		for (String propKeyName : kRequiredProps) {
			if (!props.containsKey(propKeyName)) {
				throw new IllegalArgumentException(
						"Invalid OptimusServiceProviderProperties:  missing required key ="
								+ propKeyName);
			}
		}
	}
	
}*/
