package app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库基础操作实现类
 */
public class DbHelper {
	// 数据库名
	public final static String database = "lease";
	// 数据库连接方法
	public final static ConnectionType mode = ConnectionType.JDBC_MySQL;
	// 服务器IP
	//final static String server = "localhost";
	public final static String server = "192.168.0.195";
	// 用户名
	public final static String userName = "root";
	// 密码
	public final static String password = "root";
	// 编码格式
	public final static String encode = "UTF-8";

	/**
	 * 创建通用连接
	 * @return 连接对象
	 */
	public static Connection createConn() {
		if (mode == ConnectionType.JDBC_ODBC_BRIDGE) {
			// SQLServer桥连接
			return getConn("sun.jdbc.odbc.JdbcOdbcDriver",
					"jdbc:odbc:driver=sql server;server=" + server
							+ ";database=" + database);
		} else if (mode == ConnectionType.JDBC_MICROSOFT) {
			// SQLServer JDBC连接
			return getConn("com.microsoft.sqlserver.jdbc.SQLServerDriver",
					"jdbc:sqlserver://" + server + ":1433;DataBaseName="
							+ database);
		} else if (mode == ConnectionType.JDBC_MySQL) {
			// MySQL连接
			return getConn("org.gjt.mm.mysql.Driver", "jdbc:mysql://" + server
					+ ":3388/" + database + "?characterEncoding=" + encode);
		} else if (mode == ConnectionType.JDBC_ORACLE) {
			// Oracle连接
			return getConn("oracle.jdbc.driver.OracleDriver",
					"jdbc:oracle:thin:@" + server + ":1521:"+database);
		}else {
		}
		return null;
	}

	/**
	 * 创建专用连接
	 * @param driver :驱动名称
	 * @param url :连接地址
	 * @param userName :用户名
	 * @param password :密码
	 * @return:连接对象
	 */
	public static Connection createConn(String driver, String url,
			String userName, String password) {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException ex) {
			System.out.println("数据库联接失败,详细信息为:" + ex.getMessage());
		} catch (SQLException ex) {
			System.out.println("数据库联接失败,详细信息为:" + ex.getMessage());
		}
		return null;
	}

	/**
	 * 启动事务
	 * @param conn 连接对象
	 */
	public static void beginTransaction(Connection conn) {
		try {
			conn.setAutoCommit(false);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 提交事务
	 * @param conn 连接对象
	 */
	public static void commitTransaction(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 回滚事务
	 * @param conn 连接对象
	 */
	public static void rollbackTransaction(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 执行数据库的增删改方法.
	 * @param sqlstr 增删改Sql语句
	 * @param conn 连接对象
	 * @return 是否成功
	 */
	public static boolean execUpdate(String sqlstr, Connection conn) {
		if (conn == null) {
			System.out.println("数据联接对象为空.不能进行更新操作...");
			return false;
		}
		Statement ps = null;
		try {
			ps = conn.createStatement();
			return (ps.executeUpdate(sqlstr) != -1);
		} catch (SQLException ex) {
			System.out.println("数据库执行更新失败,详细信息为:" + ex.getMessage());
			return false;
		}finally {
			if (ps !=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 执行数据库的增删改方法
	 * @param sqlstr 增删改Sql语句
	 * @param conn 连接对象
	 * @return 影响的行数
	 */
	public static int execUpdateCounts(String sqlstr, Connection conn) {
		if (conn == null) {
			System.out.println("数据联接对象为空.不能进行更新操作...");
			return 0;
		}
		Statement ps = null;
		try {
			ps = conn.createStatement();
			return (ps.executeUpdate(sqlstr));
		} catch (SQLException ex) {
			System.out.println("数据库执行更新失败,详细信息为:" + ex.getMessage());
			return 0;
		}finally {
			if (ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 执行数据库的查询方法. 如进行Insert操作.sql语句为:insert into
	 * testTable(字段1,字段2,字段3)values(?,?,?); 调用的时候需传入代替?号的对象数组.如: new
	 * Object[]{val1,val2,val3}
	 * @param sqlstr 增删改的Sql语句
	 * @param sqlParam  Sql参数
	 * @param conn 连接对象
	 * @return 是否成功
	 */
	public static boolean execUpdate(String sqlstr, Object[] sqlParam,
			Connection conn) {
		if (conn == null) {
			System.out.println("数据联接对象为空.不能进行更新操作...");
			return false;
		}
		PreparedStatement ps =null;
		try {
			ps = conn.prepareStatement(sqlstr);
			for (int i = 0; i < sqlParam.length; i++) {
				ps.setObject(i + 1, sqlParam[i]);
			}
			return (ps.executeUpdate() != -1);
		} catch (SQLException ex) {
			System.out.println("数据库执行更新失败,详细信息为:" + ex.getMessage());
			return false;
		}finally {
			if (ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param sqlstr 查询Sql语句
	 * @param conn 连接对象
	 * @return ResultSet结果集
	 */
	public static ResultSet execQuery(String sqlstr, Connection conn) {
		if (conn == null) {
			System.out.println("数据联接对象为空.不能进行查询操作...");
			return null;
		}
		Statement ps= null;
		try {
			ps = conn.createStatement();
			return ps.executeQuery(sqlstr);
		} catch (SQLException ex) {
			System.out.println("数据库执行查询失败,详细信息为:" + ex.getMessage());
			return null;
		}finally {
			if (ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 执行数据库的查询方法.外面操作完结果集,请记住调用close方法 list:SQL参数. 调用的时候需传入代替?号的对象数组. 如：new
	 * Object[]{val1,val2,val3}
	 * @param sqlstr 查询sql语句
	 * @param sqlParam sql参数
	 * @param conn 连接对象
	 * @return ResultSet结果集
	 */
	public static ResultSet execQuery(String sqlstr, Object[] sqlParam,
			Connection conn) {
		if (conn == null) {
			System.out.println("数据联接对象为空.不能进行查询操作...");
			return null;
		}
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sqlstr);
			for (int i = 0; i < sqlParam.length; i++) {
				ps.setObject(i + 1, sqlParam[i]);
			}
			return ps.executeQuery();
		} catch (SQLException ex) {
			System.out.println("数据库执行查询失败,详细信息为:" + ex.getMessage());
			return null;
		}finally {
			if (ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 使用存贮过程查询
	 * @param sql 存储过程执行语句。如："{call GetRecordAsPage(?,?,?,?)}"
	 * @param sqlParam 存储过程参数
	 * @param conn 连接对象
	 * @return ResultSet结果集
	 */
	public static ResultSet execCall(String sql, Object[] sqlParam,
			Connection conn) {
		if (conn == null) {
			System.out.println("数据联接对象为空.不能进行查询操作...");
			return null;
		}
		PreparedStatement ps = null;
		try {
			ps = conn.prepareCall(sql);
			for (int i = 0; i < sqlParam.length; i++) {
				if (sqlParam[i] == null) {
					ps.setNull(i + 1, 2);
				} else {
					ps.setObject(i + 1, sqlParam[i]);
				}
			}
			return ps.executeQuery();
		} catch (SQLException ex) {
			System.out.println("数据库执行查询失败,详细信息为:" + ex.getMessage());
			return null;
		}finally {
			if (ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建连接
	 * @param driver  连接驱动
	 * @param url  连接字符串
	 * @return 连接对象
	 */
	private static Connection getConn(String driver, String url) {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException ex) {
			System.out.println("数据库联接失败,详细信息为:" + ex.getMessage());
		} catch (SQLException ex) {
			System.out.println("数据库联接失败,详细信息为:" + ex.getMessage());
		}
		return null;

	}
	/**
	 * 关闭数据库链接
	 * @param conn
	 */
	public static void closeConn(Connection conn) {
		try {
			conn.close();
		} catch (Exception ex) {
			System.out.println("链接关闭异常");
		}
	}

	/**
	 * 数据库类型枚举
	 */
	public enum ConnectionType {
		/**
		 *JDBC_ODBC_BRIDGE
		 */
		JDBC_ODBC_BRIDGE,
		/**
		 *JDBC_MICROSOFT
		 */
		JDBC_MICROSOFT,
		/**
		 * JDBC_MySQL
		 */
		JDBC_MySQL,
		/**
		 * JDBC_ORACLE
		 */
		JDBC_ORACLE
	}
	
}