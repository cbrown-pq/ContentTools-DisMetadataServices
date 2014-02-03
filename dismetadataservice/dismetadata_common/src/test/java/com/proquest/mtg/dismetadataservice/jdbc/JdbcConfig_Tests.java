package com.proquest.mtg.dismetadataservice.jdbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class JdbcConfig_Tests {
	
	String dbUrl;
	String userName;
	String password;
	String dbClassType;
	int poolSize;

	JdbcConfig target;

	@Before
	public void setUp() throws Exception {

		dbUrl = "FakeDbUrl";
		userName = "FakeUserName";
		password = "FakePassword";
		poolSize = 1;
		dbClassType = "FakeDBClassType";
		target = new JdbcConfig(dbUrl, userName, password, dbClassType, poolSize);
	}

	@Test
	public void hasCorrect_DbUrl() {
		assertThat(target.getDbUrl(), is(dbUrl));
	}

	@Test
	public void hasCorrect_UserName() {
		assertThat(target.getUserName(), is(userName));
	}

	@Test
	public void hasCorrect_Password() {
		assertThat(target.getPassword(), is(password));
	}

	@Test
	public void hasCorrect_PoolSize() {
		assertThat(target.getMaxPoolSize(), is(poolSize));
	}

	@Test
	public void hasCorrect_DbClassType() {
		assertThat(target.getDbClassType(), is(dbClassType));
	}
	
	@Test
	public void canSet_DbUrl() {
		String newDbUrl = "my url";
		target.setDbUrl(newDbUrl);
		assertThat(target.getDbUrl(), is(newDbUrl));
	}

	@Test
	public void canSet_UserName() {
		String newUserName = "my user";
		target.setUserName(newUserName);
		assertThat(target.getUserName(), is(newUserName));
	}

	@Test
	public void canSet_Password() {
		String newPassword = "my password";
		target.setPassword(newPassword);
		assertThat(target.getPassword(), is(newPassword));
	}

	@Test
	public void canSet_PoolSize() {
		int newMaxPoolSize = 3;
		target.setMaxPoolSize(newMaxPoolSize);
		assertThat(target.getMaxPoolSize(), is(newMaxPoolSize));
	}

	@Test
	public void canSet_DbClassType() {
		String newDbClassType = "my dbClassType";
		target.setDbClassType(newDbClassType);
		assertThat(target.getDbClassType(), is(newDbClassType));
	}

}
