package com.proquest.mtg.dismetadataservice.jdbc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.junit.Before;
import org.junit.Test;

public class JdbcConnectionPool_Tests {
	
	JdbcConnectionPool pool;
	
	@Before
	public void setUp() throws Exception {
		pool = JdbcHelper.makePoolForExodusDevelopment();
	}
	
	@Test
	public void canUseConnection() throws Exception {
		Connection connection = null;
		try{
			connection = pool.getConnection();
			DatabaseMetaData md = connection.getMetaData();
			assertThat(md, notNullValue());	
		}			
		finally {
			if (null != connection) {
				connection.close();
			}
		}
	}
	
	@Test
	public void canDestroyPool() throws Exception {
		final int kTimeoutSeconds = 1;
		
		Connection connection1 = pool.getConnection();
		Connection connection2 = pool.getConnection();
		
		assertThat(connection1.isValid(kTimeoutSeconds), is(true));
		assertThat(connection2.isValid(kTimeoutSeconds), is(true));
		
		pool.destroy();
		
		assertThat(connection1.isValid(kTimeoutSeconds), is(false));
		assertThat(connection2.isValid(kTimeoutSeconds), is(false));
	}
}
