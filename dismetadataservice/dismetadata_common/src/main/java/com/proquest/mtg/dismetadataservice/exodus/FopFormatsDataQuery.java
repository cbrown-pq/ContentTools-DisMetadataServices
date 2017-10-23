package com.proquest.mtg.dismetadataservice.exodus;

	import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import oracle.sql.ArrayDescriptor;
import oracle.sql.ARRAY;

import org.apache.commons.lang.ArrayUtils;

import com.google.common.collect.Lists;


	public class FopFormatsDataQuery {
		
		private Connection connection;
		private CallableStatement exodusStatement;

		
		private static final String kUpdateFopAvailableFormats =
				"{call dis_update_available_formats(?,?,?,?)}";
		
		public FopFormatsDataQuery(Connection inConnection) throws Exception {
			this.connection = inConnection;
		}
		
		
		public String updateFOPFormats(String pubid, String format) throws Exception {
			ArrayDescriptor oracleVarchar2Collection = ArrayDescriptor.createDescriptor("DIS.T_DVF_LIST",connection);
			Object[] format_array = new Object[1];
			format_array[0] = format;
			ARRAY formatAry = new ARRAY (oracleVarchar2Collection, connection, format_array);
			this.exodusStatement = connection.prepareCall(kUpdateFopAvailableFormats);
			int ret = 2;
			this.exodusStatement.registerOutParameter(3, Types.INTEGER);
			this.exodusStatement.registerOutParameter(4, Types.VARCHAR);
			this.exodusStatement.setString(1, pubid );
			this.exodusStatement.setObject(2, formatAry );
			this.exodusStatement.executeUpdate();
			ret = this.exodusStatement.getInt(3);
			String retStatement = this.exodusStatement.getString(4);
			
			if(ret == 0){
				return "Update successful";
			}else{
				return "Error!" + retStatement;
			}
		}
		
		public void close() throws SQLException {
			closeStatement(exodusStatement);
		}
		
		private void closeStatement(PreparedStatement statement) throws SQLException {
			if (null != statement) {
				statement.close();
			}
		}
		
	}
