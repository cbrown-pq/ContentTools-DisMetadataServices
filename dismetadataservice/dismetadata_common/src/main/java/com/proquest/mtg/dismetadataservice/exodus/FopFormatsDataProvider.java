package com.proquest.mtg.dismetadataservice.exodus;

	import java.sql.Connection;
import java.sql.SQLException;

	import javax.inject.Named;

	import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.format.IFopFormatsDataProvider;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;


	public class FopFormatsDataProvider implements IFopFormatsDataProvider {

		private static IJdbcConnectionPool connectionPool;

		@Inject
		public FopFormatsDataProvider(
				@Named(IJdbcConnectionPool.kFopExodusConnectionPool) IJdbcConnectionPool connectionPool)
				throws SQLException {
			FopFormatsDataProvider.setConnectionPool(connectionPool);
		}

		public static IJdbcConnectionPool getConnectionPool() {
			return connectionPool;
		}

		
		@Override
		public String updateFOPFormatsQuery(String pubid, String format) throws Exception {
			String result = "No result";
			Connection connection = null;
			FopFormatsDataQuery query = null;
			try {
				connection = getConnectionPool().getConnection();
				query = new FopFormatsDataQuery(connection);
				result = query.updateFOPFormats(pubid, format);
			} finally {
				if (null != query) {
					query.close();
				}
				if (null != connection) {
					connection.close();
				}
			}
			return result;
		}

		public static void setConnectionPool(IJdbcConnectionPool connectionPool) {
			FopFormatsDataProvider.connectionPool = connectionPool;
		}
		
	}
