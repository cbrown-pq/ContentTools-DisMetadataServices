package com.proquest.mtg.dismetadataservice.helper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import com.proquest.mtg.dismetadataservice.jdbc.JdbcConfig;

public class MyAsserts {
	
	public static void assertEqual(JdbcConfig actual, JdbcConfig expected) {
		assertThat(actual, not(nullValue()));
		assertThat(expected, not(nullValue()));
		assertThat(actual.getDbUrl(), is(expected.getDbUrl()));
		assertThat(actual.getUserName(), is(expected.getUserName()));
		assertThat(actual.getPassword(), is(expected.getPassword()));
		assertThat(actual.getMaxPoolSize(), is(expected.getMaxPoolSize()));
		assertThat(actual.getDbClassType(), is(expected.getDbClassType()));
	}

}
