package cn.mftcc.function.jdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JdbcUtil {
	private static Log log = LogFactory.getLog(JdbcUtil.class);
	private static DataSource ds = null;

	public static DataSource getDs() {
		return ds;
	}

	public static void setDs(DataSource ds) {
		JdbcUtil.ds = ds;
	}

	private static void initDataSource() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ctitc");
		} catch (NamingException e) {
			log.error("获得数据源失败!请检查tomcat的context.xml配置文件",e);
		}
	}

	public static Connection getConnection() throws SQLException {
		if (ds == null) {
			initDataSource();
		}
		return ds.getConnection();
	}
	
//	 @SuppressWarnings("finally")  
//	     public static Connection getConnection1(){ 
//		 Connection conn= null;
//	         try{  
//	             Class.forName("com.mysql.jdbc.Driver");  
//	             conn=DriverManager.getConnection("jdbc:mysql://192.168.2.115:6665/xedk","xedk","admin");  
//	         }catch(Exception e){  
//	             e.printStackTrace();  
//	             System.out.println("建立数据库发生错误！");  
//	         }finally{  
//	             return conn;  
//	         }  
//	     }  

	
	

	public static final void close(ResultSet rs, Statement ps, Connection con)
			throws SQLException {
		if (rs != null) {
			rs.close();
			rs = null;
		}
		if (ps != null) {
			ps.close();
			ps = null;
		}
		if (con != null) {
			con.close();
			con = null;
		}
	}

	public static <T> T queryForObject(String sql, Class<T> returnType,
			Object... parm) throws Exception {
		Connection con=null;
		try {
			con = getConnection();
			return queryForObject(sql, returnType, con, parm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			close(null, null, con);
		}
	}

//	public static <T> T queryForObject(String sql, Class<T> returnType,
//			Connection con, Object... parm) throws Exception {
//		return innerQueryObject(getSql(sql, parm), con,
//				RowMapperFactory.getRowMapperUsingBeanUtils(returnType),
//				getParmFromSql(sql, parm));
//	}

	public static int queryForInt(String sql, Object... parm) throws Exception {
		Connection con=null;
		try {
			con = getConnection();
			return queryForInt(sql, con, parm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			close(null, null, con);
		}
	}

	public static final void printSql(String sql, Object... args) {
		if (log.isInfoEnabled()) {
			log.debug("SQL:" + sql);
			StringBuilder sb = new StringBuilder("[");
			for (Object object : args) {
				sb.append(object).append(",");
			}
			log.debug("Params:" + sb.substring(0, sb.length() - 1) + "]");
		}
	}

	public final static int innerExecuteUpdate(String sql, Connection con,
			Object... args) throws Exception {
		printSql(sql, args);
		PreparedStatement stat = null;
		int r = 0;
		try {
			stat = con.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				stat.setObject(i + 1, args[i]);
			}
			r = stat.executeUpdate();
		} finally {
			close(null, stat, null);
		}
		return r;
	}

	public final static int[] innerExecuteUpdateBatch(String sql,
			Connection con, Object[][] args) throws Exception {
		PreparedStatement stat = null;
		int[] r = new int[0];
		try {
			stat = con.prepareStatement(sql);
			for (int k = 0; k < args.length; k++) {
				Object[] a = args[k];
				printSql(sql, a);
				for (int i = 0; i < a.length; i++) {
					stat.setObject(i + 1, a[i]);
				}
				stat.addBatch();
			}
			r = stat.executeBatch();
		} finally {
			close(null, stat, null);
		}
		return r;
	}

	public static int[] executeUpdateBatch(String sql, Object[] args)
			throws Exception {
		Connection con = null;
		try {
			con = getConnection();
			return executeUpdateBatch(sql, con, args);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			close(null, null, con);
		}
	}

	public static int[] executeUpdateBatch(String sql, Connection con,
			Object[] args) throws Exception {
		if (args == null || args.length == 0) {
			throw new RuntimeException("批量更新实体个数不能为0");
		}
		List<Object[]> listArgs = new ArrayList<Object[]>();
		for (Object object : args) {
			listArgs.add(getParmFromSql(sql, object));
		}
		return innerExecuteUpdateBatch(getSql(sql, args[0]), con,
				listArgs.toArray(new Object[0][0]));
	}

//	public final static <T> List<T> innerQueryList(String sql, Connection con,
//			RowMapper<T> rm, Object... args) throws Exception {
//		printSql(sql, args);
//		List<T> list = new ArrayList<T>();
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			stat = con.prepareStatement(sql);
//			for (int i = 0; i < args.length; i++) {
//				stat.setObject(i + 1, args[i]);
//			}
//			rs = stat.executeQuery();
//			while (rs.next()) {
//				T o = rm.mapRow(rs);
//				list.add(o);
//			}
//		} finally {
//			close(rs, stat, null);
//		}
//		return list;
//	}

//	public final static <T> T innerQueryObject(String sql, Connection con,
//			RowMapper<T> rm, Object... args) throws Exception {
//		printSql(sql, args);
//		T o = null;
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		try {
//			stat = con.prepareStatement(sql);
//			for (int i = 0; i < args.length; i++) {
//				stat.setObject(i + 1, args[i]);
//			}
//			rs = stat.executeQuery();
//			if (rs.next()) {
//				o = rm.mapRow(rs);
//			}
//			if (rs.next()) {
//				throw new RuntimeException("应该只查询出一条记录，但实际返回了多条");
//			}
//		} finally {
//			close(rs, stat, null);
//		}
//		return o;
//	}

//	public static int queryForInt(String sql, Connection con, Object... parm)
//			throws Exception {
//		return innerQueryObject(getSql(sql, parm), con,
//				RowMapperFactory.getSingleColumRowMapper(Integer.class),
//				getParmFromSql(sql, parm));
//	}

	public static <T> List<T> queryForList(String sql, Class<T> returnType,
			Object... parm) throws Exception {
		Connection con = null;
		try {
			con = getConnection();
			return queryForList(sql, returnType, con, parm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			close(null, null, con);
		}
	}

//	public static <T> List<T> queryForList(String sql, Class<T> returnType,
//			Connection con, Object... parm) throws Exception {
//		return innerQueryList(getSql(sql, parm), con,
//				RowMapperFactory.getRowMapperUsingBeanUtils(returnType),
//				getParmFromSql(sql, parm));
//	}

	public static int executeUpdate(String sql, Object... parm)
			throws Exception {
		Connection con = null;
		try {
			con = getConnection();
			return executeUpdate(sql, con, parm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			close(null, null, con);
		}
	}

	public static int executeUpdate(String sql, Connection con, Object... parm)
			throws Exception {
		return innerExecuteUpdate(getSql(sql, parm), con,
				getParmFromSql(sql, parm));
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert(Object o, Class<T> cls) {
		if (null != o && o.getClass().equals(cls)) {
            return (T) o;
        }
		if (null == o) {
			return null;
		}
		return (T) ConvertUtils.convert(o, cls);
	}

	public static final Pattern sqlR = Pattern.compile("\\#([^\\s]*?)\\#");
	public static final Pattern sqlR1 = Pattern.compile("\\$([^\\s]*?)\\$");

	/**
	 * 方法描述： 把sql语句中的#parm#替换为 ？
	 * 
	 * @param sql
	 * @return String
	 * @author 王少泽
	 * @throws Exception
	 * @date 2013-4-2 下午6:58:46
	 */
	public static final String getSql(String sql, Object... bean)
			throws Exception {
		sql = getReplaceSql(sql, bean);
		Matcher match = sqlR.matcher(sql);
		String s = match.replaceAll("?");
		return s;
	}

	/**
	 * 方法描述： 原位替换sql $$
	 * 
	 * @param sql
	 * @param bean
	 * @return
	 * @throws Exception
	 *             String
	 * @author 王少泽
	 * @date 2013-4-3 下午12:22:10
	 */
	private static final String getReplaceSql(String sql, Object... bean)
			throws Exception {
		Matcher match = sqlR1.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (match.find()) {
			String g = match.group();
			g = g.substring(1, g.length() - 1);
			String tmp = null;
			boolean hasEmptyValue = false;
			boolean findIt = false;
			for (int j = 0, length = bean.length; j < length; j++) {
				try {
					tmp = BeanUtils.getProperty(bean[j], g);
				} catch (Exception e) {
					tmp = null;
				}
				if (null == tmp) {// 为null继续往下寻找
					continue;
				} else if ("".equals(tmp)) {// 为空字符的情况，记住有空字符，然后继续往下找
					hasEmptyValue = true;
				} else {// 有值的时候表示找到
					findIt = true;
					break;
				}
			}
			if (hasEmptyValue && !findIt)// 如果有空字符，且最后没有找到匹配的，将参数重置为空
            {
                tmp = "";
            }
			if (null == tmp) {
                throw new Exception("缺少参数：" + g);
            }
			match.appendReplacement(sb, tmp);
		}
		match.appendTail(sb);
		return sb.toString();
	}

	public static final boolean allowNull = true;

	/**
	 * 方法描述： 根据sql语句和参数bean返回需要提交的参数。如bean：{pid:"10001"} sql:“select * from
	 * cw_stting where pid = #pid#” 提取后是["10001"]
	 * 
	 * @param sql
	 * @param bean
	 * @return
	 * @throws Exception
	 *             Object[]
	 * @author 王少泽
	 * @date 2013-4-2 下午6:59:29
	 */
	private static Object[] getParmFromSql(String sql, Object... bean) throws Exception {
		Matcher match = sqlR.matcher(sql);
		ArrayList<String> _parm = new ArrayList<String>();
		while (match.find()) {
			String g = match.group(1);
			String tmp = null;
			boolean hasEmptyValue = false;
			boolean findIt = false;
			for (int j = 0, length = bean.length; j < length; j++) {
				try {
					tmp = BeanUtils.getProperty(bean[j], g);
				} catch (Exception e) {
					tmp = null;
				}
				if (null == tmp) {// 为null继续往下寻找
					continue;
				} else if ("".equals(tmp)) {// 为空字符的情况，记住有空字符，然后继续往下找
					hasEmptyValue = true;
				} else {// 有值的时候表示找到
					findIt = true;
					break;
				}
			}
			if (hasEmptyValue && !findIt)// 如果有空字符，且最后没有找到匹配的，将参数重置为空
            {
                tmp = "";
            }
			if (!allowNull) {
				if (null == tmp) {
                    throw new Exception("缺少参数：" + g);
                }
			}
			_parm.add(tmp);
		}
		return _parm.toArray();
	}
}
