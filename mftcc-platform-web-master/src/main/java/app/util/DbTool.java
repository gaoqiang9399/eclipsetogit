package app.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class DbTool {
	
	/**
	 * SqlServer Test Connection
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static boolean SqlServerJDBCTest(Connection conn) throws SQLException {
		log("Test SqlServer JDBC connection Start...");
		boolean flag = true;
		//禁止自动提交
		conn.setAutoCommit(false);
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append(" select "+
			 "Amount,Downpayment,CustDeposit,DealerDeposit,OverheadExpenses,Insurance"+
			" from relms.dbo.t_Pack "+
			" where DealerID in(select b.DealerID "+
			 " from relms.dbo.t_Dealer a,relms.dbo.t_Quota b  "+
			" where a.DealerID = b.DealerID and a.Name ='福清市隆兴工程机械厂') and ClearDate = '' ");
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			rs = DbChanger.execQuery(selectSqlSb.toString(), conn);
			rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			double all = 0.0;
			while (rs.next()) {
				double  Amount = Double.parseDouble("".equals(rs.getString("Amount").trim()) ?"0":rs.getString("Amount").trim());
				double  Downpayment = Double.parseDouble("".equals(rs.getString("Downpayment").trim()) ?"0":rs.getString("Downpayment").trim());
				double  CustDeposit = Double.parseDouble("".equals(rs.getString("CustDeposit").trim()) ?"0":rs.getString("CustDeposit").trim());
				double  DealerDeposit = Double.parseDouble("".equals(rs.getString("DealerDeposit").trim()) ?"0":rs.getString("DealerDeposit").trim());
				double  OverheadExpenses = Double.parseDouble("".equals(rs.getString("OverheadExpenses").trim()) ?"0":rs.getString("OverheadExpenses").trim());
				double  Insurance = Double.parseDouble("".equals(rs.getString("Insurance").trim()) ?"0":rs.getString("Insurance").trim());
				all += (Amount - Downpayment- CustDeposit-DealerDeposit);
				/*for (int i = 1; i <= columns; i++) {
					log(rsmd.getColumnName(i));
					log(rs.getString(i));
					System.out.print("\t\t");
					log("i:"+i);
				}*/
			}
			 String i = DecimalFormat.getInstance().format(all);  
			log(i);
			//conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			rs.close();
			conn.close();
		}
		log("Test SqlServer JDBC connection End...");
		return flag;
	}
	/**
	 * Mysql Test Connection
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static boolean MysqlJDBCTest(Connection conn,String tableName) throws SQLException {
		log("Test Mysql JDBC connection Start...");
		boolean flag = true;
		//禁止自动提交
		conn.setAutoCommit(false);
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append("SELECT * from ");
		selectSqlSb.append(tableName);
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			rs = DbChanger.execQuery(selectSqlSb.toString(), conn);
			rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			String sql = "SELECT ";
			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					log(rsmd.getColumnName(i));
					if(i!=1){
						sql+=",";
					}
					sql+=rsmd.getColumnName(i).toUpperCase();
				/*	log(rs.getString(i));
					System.out.print("\t\t");*/
				}
				log(sql+" FROM "+tableName.toUpperCase());
				break;
			}
			//conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			rs.close();
			conn.close();
		}
		log("Test Mysql JDBC connection End...");
		return flag;
	}
	/**
	 * Mysql Test2 Connection
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static boolean MysqlJDBCTest2(Connection conn,String tableName,String insPs,String sqlType) throws SQLException {
		log("Test Mysql JDBC connection Start...");
		boolean flag = true;
		//禁止自动提交
		conn.setAutoCommit(false);
		if(insPs==null||"".equals(insPs)){
			insPs = "insertPs";
		}
		if(sqlType==null||"".equals(sqlType)){
			sqlType = "select";
		}
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append("SELECT C.COLUMN_NAME AS COLUMN_NAME,C.COLUMN_COMMENT AS COLUMN_COMMENT,");
		selectSqlSb.append("  C.COLUMN_TYPE AS COLUMN_TYPE");
		selectSqlSb.append("  FROM information_schema.COLUMNS C");
		selectSqlSb.append(" WHERE TABLE_NAME = '");
		selectSqlSb.append(tableName+"'");
		ResultSet rs = null;
		try {
			rs = DbChanger.execQuery(selectSqlSb.toString(), conn);
			String sql = "";
			StringBuffer selColumnSql = new StringBuffer();
			StringBuffer insertSql = new StringBuffer();
			StringBuffer desStr = new StringBuffer();
			int i=0;
			sqlType = sqlType.toLowerCase();
			if("update".equals(sqlType)){
				sql+="UPDATE "+tableName.toUpperCase()+" SET ";
				while (rs.next()) {
					String colName = rs.getString("COLUMN_NAME").toUpperCase();
					String colComment = rs.getString("COLUMN_COMMENT");	
					String colType = rs.getString("COLUMN_TYPE");
					String type = getColType(colType);
					if(i==0){
						sql+="A."+colName+"="+"B."+colName;
						desStr.append(colComment);
					}else{
						sql+=",A."+colName+"="+"B."+colName;
						desStr.append(","+colComment);
					}
					insertSql.append(insPs+".set"+type+"("+i+",rs.get"+type+"(\""+colName+"\"));//"+colComment+"\n");
					i++;
				}
				log(sql);
			}else if("insert".equals(sqlType)){
				sql+="INSERT "+tableName.toUpperCase();
				String col = " (";
				String value = " VALUES(";
				while (rs.next()) {
					String colName = rs.getString("COLUMN_NAME").toUpperCase();
					String colComment = rs.getString("COLUMN_COMMENT");	
					String colType = rs.getString("COLUMN_TYPE");
					String type = getColType(colType);
					if(i==0){
						col+=colName;
						value+="?";
						desStr.append(colComment);
					}else{
						col+=","+colName;
						value+=",?";
						desStr.append(","+colComment);
					}
					insertSql.append(insPs+".set"+type+"("+(i+1)+",rs.get"+type+"(\""+colName+"\"));//"+colComment+"\n");
					i++;
				}
				log(sql+col+")"+value+")");
			}else{
				sql+="SELECT ";
				while (rs.next()) {
					String colName = rs.getString("COLUMN_NAME").toUpperCase();
					String colComment = rs.getString("COLUMN_COMMENT");	
					String colType = rs.getString("COLUMN_TYPE");
					String type = getColType(colType);
					if(i==0){
						sql+=colName;
						desStr.append(colComment);
					}else{
						sql+=","+colName;
						desStr.append(","+colComment);
					}
					insertSql.append(insPs+".set"+type+"("+i+",rs.get"+type+"(\""+colName+"\"));//"+colComment+"\n");
					selColumnSql.append(type+" "+columnToProperty(colName)+" = rs.get"+type+"(\""+colName+"\");//"+colComment+"\n");
					i++;
				}
				log(sql+" FROM "+tableName.toUpperCase());
			}
			log(desStr.toString());
			log(insertSql.toString());
			log(selColumnSql.toString());
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			rs.close();
			conn.close();
		}
		log("Test Mysql JDBC connection End...");
		return flag;
	}
	
	/**
	 * 获取字段map<字段,解释>
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static Map<String,String> MysqlJDBCGetColMap(Connection conn,String tableName) throws SQLException {
		Map<String,String> colMap = new HashMap<String,String>();
		log("Test Mysql JDBC connection Start...");
		//禁止自动提交
		conn.setAutoCommit(false);
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append("SELECT C.COLUMN_NAME AS COLUMN_NAME,C.COLUMN_COMMENT AS COLUMN_COMMENT,");
		selectSqlSb.append("  C.COLUMN_TYPE AS COLUMN_TYPE");
		selectSqlSb.append("  FROM information_schema.COLUMNS C");
		selectSqlSb.append(" WHERE TABLE_NAME = '");
		selectSqlSb.append(tableName+"'");
		ResultSet rs = null;
		try {
			rs = DbChanger.execQuery(selectSqlSb.toString(), conn);
			while (rs.next()) {
				String colName = rs.getString("COLUMN_NAME").toLowerCase();
				String colComment = rs.getString("COLUMN_COMMENT");	
				String colType = rs.getString("COLUMN_TYPE");
				String type = getColType(colType);
				colMap.put(columnToProperty(colName), colComment);
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			rs.close();
			conn.close();
		}
		log("Test Mysql JDBC connection End...");
		return colMap;
	}
	
	/**
	 * 数据库字段转驼峰格式
	 * @param column
	 * @return
	 */
	public static String columnToProperty(String column) {
        StringBuilder result = new StringBuilder();
        if (column == null || column.isEmpty()) {
            return "";
        } else if (!column.contains("_")) {
            return column.substring(0, 1).toLowerCase() + column.substring(1);
        } else {
            String[] columns = column.split("_");
            for (String columnSplit : columns) {
                if (columnSplit.isEmpty()) {
                    continue;
                }
                if (result.length() == 0) {
                    result.append(columnSplit.toLowerCase());
                } else {
                    result.append(columnSplit.substring(0, 1).toUpperCase()).append(columnSplit.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }
    }
	/**
	 * Mysql Test2 Connection
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static boolean MysqlJDBCALTER(Connection conn,String tableName,String col) throws SQLException {
		log("Test Mysql JDBC connection Start...");
		boolean flag = true;
		//禁止自动提交
		conn.setAutoCommit(false);
		PreparedStatement alterPs = null;
		try {
			String sql = "ALTER TABLE "+tableName;
			for(int i=1,len=240;i<len;i++){
				if(i==len-1){
					sql+=" ADD COLUMN `"+col+(i+1)+"` decimal(16,2) NULL AFTER `"+col+i+"`";
				}else{
					sql+=" ADD COLUMN `"+col+(i+1)+"` decimal(16,2) NULL AFTER `"+col+i+"`,";
				}
			}
			log(sql);
			alterPs = conn.prepareStatement(sql);
			//alterPs.execute();
			//conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			alterPs.close();
			conn.close();
		}
		log("Test Mysql JDBC connection End...");
		return flag;
	}
	/**
	 * 获取类型
	 * @param colType
	 * @return
	 */
	public static String getColType(String colType){
		String type = "";
		if(colType!=null){
			String tmp = colType.split("\\(")[0];
			if(tmp.indexOf("varchar")!=-1){
				type = "String";
			}else if(tmp.indexOf("char")!=-1){
				type = "String";
			}else if(tmp.indexOf("decimal")!=-1){
				tmp = colType.split("\\(")[1].split(",")[1].split("\\)")[0];
				if("0".equals(tmp)){
					type = "Int";
				}else{
					type = "Double";
				}
			}else if(tmp.indexOf("int")!=-1){
				type = "Int";
			}else {
			}
		}
		return type;
	}
	
	/**
	 * Mysql Test Connection
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static boolean GetTableCol(Connection conn,String table) throws SQLException {
		log("Test SqlServer JDBC connection Start...");
		boolean flag = true;
        try {  
        	DatabaseMetaData dbmd = null;  
            dbmd = conn.getMetaData();  
            ResultSet resultSet = dbmd.getTables(null, "%", table, new String[] { "TABLE" });  
            while (resultSet.next()) {  
                String tableName=resultSet.getString("TABLE_NAME");  
                log(tableName);  
                if(tableName.equals(table)){  
                    ResultSet rs = conn.getMetaData().getColumns(null, conn.getMetaData().getUserName(),tableName.toUpperCase(), "%");  
                    while(rs.next()){  
                        String colName = rs.getString("COLUMN_NAME");  
                        String remarks = rs.getString("REMARKS");  
                        if(remarks == null || "".equals(remarks)){
                            remarks = colName;  
                        }  
                        String dbType = rs.getString("TYPE_NAME");  
                        System.out.print(colName.toUpperCase()); 
                        System.out.print("\t"); 
                        System.out.print(remarks); 
                        System.out.print("\t"); 
                        log(dbType); 
                       
                    }  
                }  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
		log("Test SqlServer JDBC connection End...");
		return flag;
	}
	
	/**
	 * 控制台输出
	 * 
	 * @param str
	 */
	public static void log(Object str) {
		System.out.println("\n"+str);
	}

}
