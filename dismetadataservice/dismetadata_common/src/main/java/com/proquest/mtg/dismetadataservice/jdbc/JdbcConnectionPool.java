package com.proquest.mtg.dismetadataservice.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.ucp.UniversalConnectionPoolAdapter;
import oracle.ucp.admin.UniversalConnectionPoolManager;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;


public class JdbcConnectionPool implements IJdbcConnectionPool {

	private static volatile int poolCount = 0;
	private final PoolDataSource poolDataSource;

	public JdbcConnectionPool(JdbcConfig config) throws Exception {

		poolDataSource = PoolDataSourceFactory.getPoolDataSource();
		poolDataSource.setURL(config.getDbUrl());
		poolDataSource.setUser(config.getUserName());
		poolDataSource.setPassword(config.getPassword());
		poolDataSource.setConnectionFactoryClassName(config.getDbClassType());
		poolDataSource.setMaxConnectionReuseTime(5 * 60);
		poolDataSource.setMaxConnectionReuseCount(100);
		poolDataSource.setAbandonedConnectionTimeout(10 * 60);
		poolDataSource.setInactiveConnectionTimeout(10 * 60);
		
		poolDataSource.setConnectionPoolName("JdbcConnectionPool - " + (++poolCount));
		
		UniversalConnectionPoolManager poolManager = 
				UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
		
		poolManager.createConnectionPool((UniversalConnectionPoolAdapter)poolDataSource);
	}

	public Connection getConnection() throws SQLException {
		return poolDataSource.getConnection();
	}

	@Override
	public void destroy() throws Exception {
		UniversalConnectionPoolManager poolManager = 
				UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
		
		poolManager.destroyConnectionPool(poolDataSource.getConnectionPoolName());
	}

}
