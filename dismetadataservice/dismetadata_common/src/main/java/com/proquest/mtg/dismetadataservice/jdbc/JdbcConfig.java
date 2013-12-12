package com.proquest.mtg.dismetadataservice.jdbc;

public class JdbcConfig {
	private String dbUrl;
	private String userName;
	private String password;
	private String dbClassType;
	private int maxPoolSize;

	public JdbcConfig(
			String dbUrl, String userName, String password,
			String dbClassType, int maxPoolSize) {
		this.setDbUrl(dbUrl);
		this.setUserName(userName);
		this.setPassword(password);
		this.setDbClassType(dbClassType);
		this.setMaxPoolSize(maxPoolSize);
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String value) {
		this.userName = value;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int value) {
		this.maxPoolSize = value;
	}

	public String getDbClassType() {
		return dbClassType;
	}

	public void setDbClassType(String value) {
		this.dbClassType = value;
	}

	@Override
	public String toString() {
		return "JdbcConnectionConfig [dbUrl=" + dbUrl + ", userName="
				+ userName + ", password=" + password + ", dbClassType="
				+ dbClassType + ", maxPoolSize=" + maxPoolSize + "]";
	}
}
