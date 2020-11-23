package app.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbChanger {
	private static Map<String, String> ifIpoMap = new HashMap<String, String>();// 是否上市公司转换
	private static Map<String, String> ifGdMap = new HashMap<String, String>();// 是否本行股东转换
	private static Map<String, String> ifGroupMap = new HashMap<String, String>();// 是否集团客户转换
	private static Map<String, String> entranFlagMap = new HashMap<String, String>();// 进出口权标志转换
	private static Map<String, String> projSizeMap = new HashMap<String, String>();// 企业规模转换
	private static Map<String, String> custTypeMap = new HashMap<String, String>();// 客户细分类型转换
	private static Map<String, String> dutyMap = new HashMap<String, String>();// 职务转换
	private static Map<String, String> censTypeMap = new HashMap<String, String>();// 户口性质转换
	private static Map<String, String> ifDisMap = new HashMap<String, String>();// 是否下岗职工转换
	private static Map<String, String> feedNumMap = new HashMap<String, String>();// 供养人口转换
	private static Map<String, String> ifEmplMap = new HashMap<String, String>();// 是否本行职工转换
	private static Map<String, String> occurTypeMap = new HashMap<String, String>();// 发生类型转换
	private static Map<String, String> fairFlagMap = new HashMap<String, String>();// 是否公正转换
	private static Map<String, String> iceFlagMap = new HashMap<String, String>();// 冻结标志转换
	private static Map<String, String> finishFlagMap = new HashMap<String, String>();// 归档标志转换
	private static Map<String, String> contStsMap = new HashMap<String, String>();// 合同状态转换
	private static Map<String, String> prdNoMap = new HashMap<String, String>();// 产品号转换
	private static Map<String, String> corpChaMap = new HashMap<String, String>();// 客户性质
	private static Map<String, String> corpkindMap = new HashMap<String, String>();// 单位性质
	private static Map<String, String> regTypeMap = new HashMap<String, String>();// 营业执照登记注册类型
	private static Map<String, String> timeTypeMap = new HashMap<String, String>();// 期限类型
	private static Map<String, String> icTypeMap = new HashMap<String, String>();// 计息方式
	private static Map<String, String> rateFloatTypeMap = new HashMap<String, String>();// 利率浮动类型
	private static Map<String, String> vouFormMap = new HashMap<String, String>();// 保证形式
	private static Map<String, String> ifArgcSuppMap = new HashMap<String, String>();// 涉农情况
	private static Map<String, String> rptType = new HashMap<String, String>();// 月报，季报，年报
	private static Map<String, String> bankRelType = new HashMap<String, String>();// 与银行关联关系
	private static Map<String, String> bankDuty = new HashMap<String, String>();// 在我行担任职务
	private static Map<String, String> pactSts = new HashMap<String, String>();// 出账状态
	private static Map<String, String> gawnType = new HashMap<String, String>();// 抵押物种类
	private static Map<String, String> pawnType = new HashMap<String, String>();// 质押物种类
	private static Map<String, String> leaseTerm = new HashMap<String, String>();// 付款方式

	private static List<String> errorList = new ArrayList<String>();

	@SuppressWarnings("unused")
	private static void initMap() {
		log("初始化MAP.........");
		prdNoMap.put("01", "1851");// 一般流动资金贷款
		prdNoMap.put("02", "1860");// 对公固定资产非项目贷款
		prdNoMap.put("06", "1861");// 对公固定资产项目贷款
		// prdNoMap.put("09","1851");//其它对公贷款
		prdNoMap.put("11", "1862");// 个人一手房按揭贷款
		// prdNoMap.put("12","1864");//个人商用房贷款
		// prdNoMap.put("13","1870");//银团业务
		prdNoMap.put("21", "1874");// 汽车消费贷款
		// prdNoMap.put("41","1865");//个人经营性贷款
		// prdNoMap.put("49","1256");//商业承兑汇票贴现
		// prdNoMap.put("50","1256");//银行承兑汇票贴现
		// prdNoMap.put("51","1865");//农户贷款
		prdNoMap.put("55", "1867");// 企业委托贷款
		// prdNoMap.put("56","1042");//银行承兑汇票签发
		// prdNoMap.put("58","1872");//对公下岗失业人员小额担保贷款
		prdNoMap.put("60", "1865");// 个人综合消费贷款
		// prdNoMap.put("64","1865");//抵押贷款
		// prdNoMap.put("65","1865");//质押贷款
		// prdNoMap.put("66","1865");//一般保证贷款
		prdNoMap.put("99", "1872");// 个人下岗失业贷款
		prdNoMap.put("86", "86");// 微贷

		occurTypeMap.put("1", "1");// 新增
		occurTypeMap.put("2", "4");// 收回再贷
		occurTypeMap.put("3", "3");// 还旧借新
		occurTypeMap.put("4", "5");// 其他
		occurTypeMap.put("5", "5");// 其他
		// occurTypeMap.put("6","5");//客户直贴
		// occurTypeMap.put("7","5");//转贴
		// occurTypeMap.put("A","9");//转入
		occurTypeMap.put("9", "5");// 其他
		occurTypeMap.put("A", "5");// 其他
		occurTypeMap.put("B", "5");// 其他

		fairFlagMap.put("1", "0");// 否
		fairFlagMap.put("2", "1");// 是

		contStsMap.put("1", "1");// 未生效
		contStsMap.put("2", "4");// 已出帐
		contStsMap.put("3", "4");// 已出帐

		iceFlagMap.put("0", "1");// 正常 不太对
		iceFlagMap.put("1", "0");// 冻结
		iceFlagMap.put("2", "1");// 正常
		iceFlagMap.put("3", "0");// 冻结

		finishFlagMap.put("0", "0");// 未归档
		finishFlagMap.put("1", "1");// 已归档

		ifIpoMap.put("1", "0");// 否
		ifIpoMap.put("2", "1");// 是

		ifGdMap.put("1", "0");// 否
		ifGdMap.put("2", "1");// 是

		ifGroupMap.put("1", "0");// 否
		ifGroupMap.put("2", "1");// 是

		entranFlagMap.put("1", "0");// 否
		entranFlagMap.put("2", "1");// 是

		projSizeMap.put("01", "1");// 大型
		projSizeMap.put("02", "2");// 中型
		projSizeMap.put("03", "3");// 小型
		projSizeMap.put("04", "4");// 微型
		projSizeMap.put("05", "9");// 其他

		custTypeMap.put("01", "A");// 企业法人
		custTypeMap.put("02", "D");// 企业非法人-其他
		custTypeMap.put("03", "E");// 个体工商户
		custTypeMap.put("04", "C");// 事业单位
		custTypeMap.put("99", "D");// 其他

		custTypeMap.put("1", "10");// 经营户
		custTypeMap.put("2", "11");// 自然人
		custTypeMap.put("3", "12");// 小微企业主
		custTypeMap.put("4", "13");// 个体工商户(个人)

		dutyMap.put("1", "1");// 高级领导（行政级别局级及局级以上领导或大公司高级管理人员）
		dutyMap.put("2", "1");
		dutyMap.put("3", "2");// 中级领导（行政级别局级以下领导或大公司中级管理人员）
		dutyMap.put("4", "3");// 一般员工
		dutyMap.put("5", "4");// 其他
		dutyMap.put("9", "9");// 未知

		censTypeMap.put("0", "1");// 常住
		censTypeMap.put("1", "2");// 临时

		ifDisMap.put("1", "1");// 否
		ifDisMap.put("2", "0");// 是

		feedNumMap.put("0", "0");// 0人
		feedNumMap.put("1", "1");// 1人
		feedNumMap.put("2", "2");// 2人
		feedNumMap.put("3", "3");// 3人
		feedNumMap.put("4", "4");// 4人及以上

		ifEmplMap.put("1", "0");// 否
		ifEmplMap.put("0", "1");// 是

		corpChaMap.put("01", "01");// 企业法人
		corpChaMap.put("02", "02");// 企业非法人
		corpChaMap.put("03", "99");// 个体工商户
		corpChaMap.put("04", "04");// 事业单位
		corpChaMap.put("99", "99");// 其他

		corpkindMap.put("1", "200");// 党政机关100，事业单位200（机关事业）
		corpkindMap.put("2", "510");// 国有企业（国营企业）
		corpkindMap.put("3", "800");// 其他（金融企业）
		corpkindMap.put("4", "300");// 军队
		corpkindMap.put("5", "570");// 私营企业
		corpkindMap.put("6", "700");// 个体经营
		corpkindMap.put("7", "800");// 其他（三资企业）
		corpkindMap.put("8", "800");// 其他（邮电通信）
		corpkindMap.put("9", "900");// 未知

		regTypeMap.put("110", "110");// 国有企业
		regTypeMap.put("120", "120");// 集体企业
		regTypeMap.put("130", "130");// 股份合作企业
		regTypeMap.put("140", "140");// 联营企业
		regTypeMap.put("150", "150");// 有限责任公司
		regTypeMap.put("160", "160");// 股份有限公司
		regTypeMap.put("170", "170");// 私营企业
		regTypeMap.put("220", "200");// 三资企业（港、澳、台投资企业）
		regTypeMap.put("330", "300");// 外商投资企业
		regTypeMap.put("410", "400");// 个体经营
		regTypeMap.put("500", "500");// 其它

		timeTypeMap.put("1", "1");// 短期
		timeTypeMap.put("2", "2");// 中期
		timeTypeMap.put("3", "3");// 长期
		timeTypeMap.put("4", null);// 其他

		icTypeMap.put("1", "3");// 按月
		icTypeMap.put("2", "4");// 按季
		icTypeMap.put("3", "9");// 一次性还款

		rateFloatTypeMap.put("01", "1");// 固定利率
		rateFloatTypeMap.put("02", "2");// 按月浮动
		rateFloatTypeMap.put("03", "3");// 按季浮动
		rateFloatTypeMap.put("04", "4");// 按半年浮动
		rateFloatTypeMap.put("05", "5");// 按一年浮动

		vouFormMap.put("1", "1");// 单人担保
		vouFormMap.put("3", "2");// 多人联保
		vouFormMap.put("2", "3");// 多人分保

		ifArgcSuppMap.put("2", "00");// 未涉农
		ifArgcSuppMap.put("1", "23");// 城市企业涉农

		rptType.put("50", "1");// 月报
		rptType.put("40", "2");// 季报
		rptType.put("00", "3");// 年报

		bankRelType.put("银行的内部人", "01");
		bankRelType.put("主要非自然人股东", "09");

		bankDuty.put("非我行职员", "4");
		bankDuty.put("一般职员", "3");
		bankDuty.put("中层领导", "2");
		bankDuty.put("高层领导", "1");

		pactSts.put("1", "1");// 未出帐(未生效)
		pactSts.put("2", "4");// 已出账
		pactSts.put("3", "5");// 日终已处理
		// 抵押类型
		gawnType.put("1", "10003");// 房产
		gawnType.put("2", "10001");// 土地使用权（包含土地附着物）
		gawnType.put("3", "10002");// 在建工程
		gawnType.put("4", "10004");// 交通工具
		gawnType.put("5", "10005");// 机器设备
		gawnType.put("6", "10009");// 其他类
		// 质押类型
		pawnType.put("1", "10017");// 存单
		pawnType.put("2", "10014");// 票据
		pawnType.put("3", "10013");// 保单
		pawnType.put("4", "10012");// 国债
		pawnType.put("5", "10016");// 股权/股票
		pawnType.put("6", "10030");// 其他权利
		
		leaseTerm.put("0", "");
		leaseTerm.put("1", "");

		log("始化MAP成功.........");
	}

	public static String DB_NAME_TO = "czsh";// NEWCMS
	public static String DB_NAME_FROM = "lxd";// lxd07010811
	public Map<String, String> dicMap = new HashMap<String, String>();

	public static Connection connection;

	public DbChanger() {
		dicMap.put("YES_NO_0", "0");
		dicMap.put("YES_NO_1", "1");

	}

	public static ResultSet execQuery(String sqlstr, Connection conn) {
		if (conn == null) {
			log("数据联接对象为空.不能进行更新操作...");
			return null;
		}
		Statement ps = null;
		try {
			ps = conn.createStatement();
			return ps.executeQuery(sqlstr);
		} catch (SQLException ex) {
			log(sqlstr);
			log("数据库执行失败,详细信息为:" + ex.getMessage());
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

	public static boolean execSQL(String sqlstr, Connection conn) {

		if (conn == null) {
			log("数据联接对象为空.不能进行操作...");
			return false;
		}
		Statement ps = null;
		try {
			ps = conn.createStatement();
			ps.execute(sqlstr);
			return true;
		} catch (SQLException ex) {
			log(sqlstr);
			log("数据库执行失败,详细信息为:" + ex.getMessage());
			errorList.add("SQL:" + sqlstr + "\n" + "ERROR:" + ex.getMessage());
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
	 * 控制台输出
	 * 
	 * @param str
	 */
	public static void log(Object str) {
		System.out.println("\n"+str);
	}
	
	
	/**
	 * 获取厂商
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetDdtCusSupplier(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start GetDdtCusSupplier...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append("SELECT * ");
		selectSqlSb.append(" from relms.dbo.t_Producer p,relms.dbo.t_Pack cp,relms.dbo.t_Equipment e");
		selectSqlSb.append(" where cp.EquipID = e.EquipID and e.ProducerID = p.ProducerID");
		if(inCusStr!=null&&inCusStr.length>0){
			selectSqlSb.append(" AND cp.CustName in(");
			for (int i = 0; i < inCusStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inCusStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inCusStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(inConStr!=null&&inConStr.length>0){
			selectSqlSb.append(" AND  cp.PackID in(");
			for (int i = 0; i < inConStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inConStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inConStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
			if(timeNodeStart.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate >= '"+timeNodeStart+"'");
			}else{
				throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
			}
		}
		if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			if(timeNodeEnd.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate < '"+timeNodeEnd+"'");
			}else{
				throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
			}
		}
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End GetDdtCusSupplier...");
		return rs;
	}
	
	/**
	 * 获取经销商
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetDdtPoxy(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start GetDdtPoxy...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append("SELECT * from relms.dbo.t_Dealer d,relms.dbo.t_Pack cp");
		selectSqlSb.append(" where d.DealerID = cp.DealerID");
		if(inCusStr!=null&&inCusStr.length>0){
			selectSqlSb.append(" AND  cp.CustName in(");
			for (int i = 0; i < inCusStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inCusStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inCusStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(inConStr!=null&&inConStr.length>0){
			selectSqlSb.append(" AND  cp.PackID in(");
			for (int i = 0; i < inConStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inConStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inConStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
			if(timeNodeStart.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate >= '"+timeNodeStart+"'");
			}else{
				throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
			}
		}
		if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			if(timeNodeEnd.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate < '"+timeNodeEnd+"'");
			}else{
				throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
			}
		}
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End GetDdtPoxy...");
		return rs;
	}
	/**
	 * 获取承租人
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetOdtCust(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append("select cus.CustID AS CustID,cus.Name AS Name,cus.Address AS Address,");
		selectSqlSb.append(" cus.Telephone AS Telephone,cus.Mobile AS Mobile,");
		selectSqlSb.append(" cus.Bank AS Bank,cus.Account AS Account,cus.Guarantor AS Guarantor,");
		selectSqlSb.append(" cus.GrtAddr AS GrtAddr,cus.GrtContact AS GrtContact,pack.PackID AS PackID,");
		selectSqlSb.append(" pack.Recorder AS Recorder,pack.InputDate AS InputDate");
		selectSqlSb.append(" from relms.dbo.t_Cust cus,relms.dbo.t_Pack pack ");
		selectSqlSb.append(" where cus.Name = pack.CustName");
		if(inCusStr!=null&&inCusStr.length>0){
			selectSqlSb.append(" AND  pack.CustName in(");
			for (int i = 0; i < inCusStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inCusStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inCusStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(inConStr!=null&&inConStr.length>0){
			selectSqlSb.append(" AND  pack.PackID in(");
			for (int i = 0; i < inConStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inConStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inConStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
			if(timeNodeStart.indexOf("-")!=-1){
				selectSqlSb.append(" AND pack.InputDate >= '"+timeNodeStart+"'");
			}else{
				throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
			}
		}
		if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			if(timeNodeEnd.indexOf("-")!=-1){
				selectSqlSb.append(" AND pack.InputDate < '"+timeNodeEnd+"'");
			}else{
				throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
			}
		}
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End...");
		return rs;
	}
	
	/**
	 * 获取租赁合同
	 * 承租人关联合同，合同关联租赁物 ，租赁物关联厂商
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetOdtAppProject(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start GetOdtAppProject...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append(" select p.PackID AS PackID,p.CustName AS CustName,p.EquipID AS EquipID,p.StartDate AS StartDate,");
		selectSqlSb.append(" p.EndDate AS EndDate,p.VisaDate AS VisaDate,p.LeaseMonth AS LeaseMonth,p.Amount AS Amount,");
		selectSqlSb.append(" p.DpDate AS DpDate,p.OeDate AS OeDate,p.CdDate AS CdDate,p.DealerDeposit AS DealerDeposit,");
		selectSqlSb.append(" p.DdDate AS DdDate,p.Insurance AS Insurance,p.InsDate AS InsDate,p.Overdraft AS Overdraft,");
		selectSqlSb.append(" p.Rent AS Rent,p.PackSum AS PackSum,p.IsStart AS IsStart,p.DealerID AS DealerID,");
		selectSqlSb.append(" p.ClearDate AS ClearDate,p.Clearer AS Clearer,p.ClearDesc AS ClearDesc,p.Recorder AS Recorder,");
		selectSqlSb.append(" p.Price AS Price,p.InputDate AS InputDate,p.Checker AS Checker,p.RentType AS RentType,");
		selectSqlSb.append(" p.LeaseTerm AS LeaseTerm,c.CustID AS CustID,c.Address AS Address,");
		selectSqlSb.append(" c.Telephone AS Telephone,c.Mobile AS Mobile,c.Bank AS Bank,c.Account AS Account,");
		selectSqlSb.append(" pro.ProducerID AS ProducerID,pro.Name AS ProducerName,pro.Address AS ProducerAddress,");
		selectSqlSb.append(" pv.InterestRate  AS InterestRate,pv.FirstOutint AS FirstOutint,pv.SecondOutint AS SecondOutint,");
		selectSqlSb.append(" p.IsStart AS IsStart,");
		selectSqlSb.append(" dbod.Name AS DealerName,");
		selectSqlSb.append(" p.Downpayment AS Downpayment,");
		selectSqlSb.append(" p.CustDeposit AS CustDeposit,");
		selectSqlSb.append(" p.OverheadExpenses AS OverheadExpenses,");
		selectSqlSb.append(" ISNULL((select pay.Amount from relms.dbo.t_Payment pay where OrderNum = '1' AND pay.PackID = p.PackID),0) AS DownpaymentOther,");
		selectSqlSb.append(" ISNULL((select pay.Amount from relms.dbo.t_Payment pay where OrderNum = '2' AND pay.PackID = p.PackID),0) AS OverheadExpensesOther,");
		selectSqlSb.append(" ISNULL((select pay.Amount from relms.dbo.t_Payment pay where OrderNum = '3' AND pay.PackID = p.PackID),0) AS CustDepositOther");
		selectSqlSb.append(" from relms.dbo.t_Pack p");
		selectSqlSb.append(" left join relms.dbo.t_Equipment equ on p.EquipID = equ.EquipID ");
		selectSqlSb.append(" left join relms.dbo.t_PackVar pv on p.PackID = pv.PackID and pv.AdjustDate = ");
		selectSqlSb.append(" (select MAX(pv1.AdjustDate) from relms.dbo.t_PackVar pv1 where pv1.PackID = p.PackID)");
		selectSqlSb.append(" left join relms.dbo.t_Cust c On  c.Name = p.CustName ");
		selectSqlSb.append(" left join relms.dbo.t_Producer pro On pro.ProducerID = equ.ProducerID");
		selectSqlSb.append(" left join relms.dbo.t_Dealer dbod on dbod.DealerID = p.DealerID");
		selectSqlSb.append(" WHERE 1=1 ");
		if(inCusStr!=null&&inCusStr.length>0){
			selectSqlSb.append(" AND  p.CustName in(");
			for (int i = 0; i < inCusStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inCusStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inCusStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(inConStr!=null&&inConStr.length>0){
			selectSqlSb.append(" AND p.PackID in(");
			for (int i = 0; i < inConStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inConStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inConStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
			if(timeNodeStart.indexOf("-")!=-1){
				selectSqlSb.append(" AND p.InputDate >= '"+timeNodeStart+"'");
			}else{
				throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
			}
		}
		if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			if(timeNodeEnd.indexOf("-")!=-1){
				selectSqlSb.append(" AND p.InputDate < '"+timeNodeEnd+"'");
			}else{
				throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
			}
		}
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End GetOdtAppProject...");
		return rs;
	}
	
	/**
	 * 获取划款计划
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetOdtAcLnPayPln(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append(" select r.PackID AS PackID,r.Sequence AS Sequence,r.Period AS Period,r.FaithDate AS FaithDate,");
		selectSqlSb.append(" r.Rent AS Rent,r.Corpus AS Corpus,r.Inter AS Inter,r.LastCorpus AS LastCorpus,r.SumOwing AS SumOwing,");
		selectSqlSb.append(" r.IncRent AS IncRent,r.IncInterest AS IncInterest,r.RealDate AS RealDate,r.Payment AS Payment,");
		selectSqlSb.append(" r.LateDays AS LateDays,r.Interest AS Interest,r.Balance AS Balance,r.IsPayed AS IsPayed,");
		selectSqlSb.append(" r.Inputer AS Inputer,r.Checker AS Checker,p.ClearDate AS ClearDate,p.Clearer AS Clearer,");
		selectSqlSb.append("  p.CustName AS  CustName");
		selectSqlSb.append(" from relms.dbo.t_RentDetail r,relms.dbo.t_Pack p ");
		selectSqlSb.append(" where r.PackID = p.PackID ");
		//selectSqlSb.append(" AND p.PackID not in (Select distinct PackID From relms.dbo.t_RentDetail where  Inter<0)");
		if(inCusStr!=null&&inCusStr.length>0){
			selectSqlSb.append(" AND  p.CustName in(");
			for (int i = 0; i < inCusStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inCusStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inCusStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(inConStr!=null&&inConStr.length>0){
			selectSqlSb.append(" AND p.PackID in(");
			for (int i = 0; i < inConStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inConStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inConStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
			if(timeNodeStart.indexOf("-")!=-1){
				selectSqlSb.append(" AND p.InputDate >= '"+timeNodeStart+"'");
			}else{
				throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
			}
		}
		if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			if(timeNodeEnd.indexOf("-")!=-1){
				selectSqlSb.append(" AND p.InputDate < '"+timeNodeEnd+"'");
			}else{
				throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
			}
		}
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		
		log("Select End...");
		return rs;
	}
	
	/**
	 * 获取划款计划  罚息减免
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetPackInterReduce(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append(" select pir.PackID AS PackID,pir.Year AS Year,pir.Month AS Month,");
		selectSqlSb.append("   pir.ReduceDays AS ReduceDays,pir.Amount AS Amount ");
		selectSqlSb.append(" from relms.dbo.t_PackInterReduce pir");
		selectSqlSb.append(" WHERE pir.PackID  in (Select p.PackID from relms.dbo.t_Pack p Where 1=1 ");
		if(inCusStr!=null&&inCusStr.length>0){
			selectSqlSb.append(" AND  p.CustName in(");
			for (int i = 0; i < inCusStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inCusStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inCusStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(inConStr!=null&&inConStr.length>0){
			selectSqlSb.append(" AND p.PackID in(");
			for (int i = 0; i < inConStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inConStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inConStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
			if(timeNodeStart.indexOf("-")!=-1){
				selectSqlSb.append(" AND p.InputDate >= '"+timeNodeStart+"'");
			}else{
				throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
			}
		}
		if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			if(timeNodeEnd.indexOf("-")!=-1){
				selectSqlSb.append(" AND p.InputDate < '"+timeNodeEnd+"'");
			}else{
				throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
			}
		}
		selectSqlSb.append(")");
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		
		log("Select End...");
		return rs;
	}
	
	/**
	 * 获取租赁物
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetOdtLeaseItemInfo(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start GetOdtLeaseItemInfo...");
		StringBuffer selectSqlSb = new StringBuffer();
		
		selectSqlSb.append(" SELECT ");
		selectSqlSb.append(" cp.PackID AS HirepackID,cp.CustName AS CustName,ep.NAME AS EquipName, ");
		selectSqlSb.append(" ep.Model AS Model,ep.ProducerID AS ProducerID,p.NAME AS ProducerName, ");
		selectSqlSb.append(" b.PackID AS PackID,bpe.EngineNum AS EngineNum,");
		selectSqlSb.append(" bpe.GpsUser AS GpsUser,bpe.GpsAddr AS GpsAddr, ");
		selectSqlSb.append(" bpe.GpsPassword AS GpsPassword,bpe.PledgeEndDate AS PledgeEndDate, ");
		selectSqlSb.append(" bpe.PledgeStartDate AS PledgeStartDate,bpe.Pledgee AS Pledgee, ");
		selectSqlSb.append(" bpe.ThawDate AS ThawDate,bpe.BuybackDate AS BuybackDate, ");
		selectSqlSb.append(" bpe.TOSDate AS TOSDate,bpe.Price AS Price,bpe.EquNum AS EquipNum ");
		selectSqlSb.append(" FROM relms.dbo.t_Pack cp ");
		selectSqlSb.append(" LEFT JOIN relms.dbo.t_Equipment ep ON cp.EquipID = ep.EquipID ");
		selectSqlSb.append(" LEFT JOIN relms.dbo.t_Producer p ON ep.ProducerID = p.ProducerID ");
		selectSqlSb.append(" LEFT JOIN relms.dbo.t_BuyPack b ON cp.PackID = b.HirepackID ");
		selectSqlSb.append(" LEFT JOIN relms.dbo.t_BuyPackEqu bpe ON bpe.BuyPackID = b.PackID ");
		selectSqlSb.append(" WHERE 1 = 1 ");
		if(inCusStr!=null&&inCusStr.length>0){
			selectSqlSb.append(" AND  cp.CustName in(");
			for (int i = 0; i < inCusStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inCusStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inCusStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(inConStr!=null&&inConStr.length>0){
			selectSqlSb.append(" AND  cp.PackID in(");
			for (int i = 0; i < inConStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inConStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inConStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
			if(timeNodeStart.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate >= '"+timeNodeStart+"'");
			}else{
				throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
			}
		}
		if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			if(timeNodeEnd.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate < '"+timeNodeEnd+"'");
			}else{
				throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
			}
		}
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End GetOdtLeaseItemInfo...");
		return rs;
	}
	
	/**
	 * 获取保险信息
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetOdtInsureRegister(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append(" select b.HirepackID AS HirepackID,e.PackID AS PackID,e.VisaDate AS VisaDate,");
		selectSqlSb.append(" e.EquipNum AS EquipNum,ep.Name AS EquipName,e.Insurer AS Insurer,e.InsureDate AS InsureDate,");
		selectSqlSb.append(" e.InsureLimit AS InsureLimit,e.Insurance AS Insurance,e.InsPayDate AS InsPayDate,");
		selectSqlSb.append(" e.PolicyHolder AS PolicyHolder,e.FirstBene AS FirstBene,e.SecondBene AS SecondBene,");
		selectSqlSb.append(" e.InsureType AS InsureType,e.InsCost AS InsCost");
		selectSqlSb.append(" from relms.dbo.t_EquipIns e,relms.dbo.t_BuyPack b,");
		selectSqlSb.append(" relms.dbo.t_Equipment ep,relms.dbo.t_BuyPackEqu bpe,");
		selectSqlSb.append(" relms.dbo.t_Pack cp");
		selectSqlSb.append(" where e.BuyPackID = b.PackID and e.EquipNum = bpe.EquNum");
		selectSqlSb.append(" and bpe.BuyPackID = b.PackID and bpe.EquID = ep.EquipID");
		selectSqlSb.append(" and cp.PackID = b.HirepackID");
		if(inCusStr!=null&&inCusStr.length>0){
			selectSqlSb.append(" AND  cp.CustName in(");
			for (int i = 0; i < inCusStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inCusStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inCusStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(inConStr!=null&&inConStr.length>0){
			selectSqlSb.append(" AND  cp.PackID in(");
			for (int i = 0; i < inConStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inConStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inConStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
			if(timeNodeStart.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate >= '"+timeNodeStart+"'");
			}else{
				throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
			}
		}
		if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			if(timeNodeEnd.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate < '"+timeNodeEnd+"'");
			}else{
				throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
			}
		}
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End...");
		return rs;
	}
	
	/**
	 * 获取或有费用
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetOdtAppFee(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append(" select pay.PackID AS PackID,pay.OrderNum AS OrderNum,pay.PayDate AS PayDate,");
		selectSqlSb.append(" pay.Amount AS Amount,pay.Subject AS Subject");
		selectSqlSb.append(" from relms.dbo.t_Payment pay,relms.dbo.t_Pack cp");
		selectSqlSb.append(" where cp.PackID = pay.PackID");
		if(inCusStr!=null&&inCusStr.length>0){
			selectSqlSb.append(" AND  cp.CustName in(");
			for (int i = 0; i < inCusStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inCusStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inCusStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(inConStr!=null&&inConStr.length>0){
			selectSqlSb.append(" AND  cp.PackID in(");
			for (int i = 0; i < inConStr.length; i++) {
				if(i!=0){
					selectSqlSb.append(",'"+inConStr[i]+"'");
				}else{
					selectSqlSb.append("'"+inConStr[i]+"'");
				}
			}
			selectSqlSb.append(")");
		}
		if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
			if(timeNodeStart.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate >= '"+timeNodeStart+"'");
			}else{
				throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
			}
		}
		if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			if(timeNodeEnd.indexOf("-")!=-1){
				selectSqlSb.append(" AND cp.InputDate < '"+timeNodeEnd+"'");
			}else{
				throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
			}
		}
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End...");
		return rs;
	}
	/**
	 * 经销商授信信息
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetOdtAuthCont(Connection conn,String []inCusStr,String []inConStr,String timeNodeStart,String timeNodeEnd) throws SQLException {
		log("Select start...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append(" select q.DealerID AS DealerID,q.Name AS Name,q.Address AS Address,q.Telphone AS Telphone,");
		selectSqlSb.append(" q.Fax AS Fax,q.Compact AS Compact,q.Mobile AS Mobile,q.Email AS Email,q.Bank AS Bank,");
		selectSqlSb.append(" q.Account AS Account,q.ProtoNum AS ProtoNum,q.StartDate AS StartDate,q.EndDate AS EndDate,");
		selectSqlSb.append(" q.Quota AS Quota,q.Used AS Used,q.UsedCycle AS UsedCycle ");
		selectSqlSb.append("  from relms.dbo.v_Quota q ");
		if((inCusStr!=null&&inCusStr.length>0)||(inConStr!=null&&inConStr.length>0)
				||timeNodeStart!=null&&!"".equals(timeNodeStart) ||
				timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
			selectSqlSb.append(" where DealerID in (select cp.DealerID from relms.dbo.t_Pack cp where 1=1 ");
			if(inCusStr!=null&&inCusStr.length>0){
				selectSqlSb.append(" AND  cp.CustName in(");
				for (int i = 0; i < inCusStr.length; i++) {
					if(i!=0){
						selectSqlSb.append(",'"+inCusStr[i]+"'");
					}else{
						selectSqlSb.append("'"+inCusStr[i]+"'");
					}
				}
				selectSqlSb.append(")");
			}
			if(inConStr!=null&&inConStr.length>0){
				selectSqlSb.append(" AND  cp.PackID in(");
				for (int i = 0; i < inConStr.length; i++) {
					if(i!=0){
						selectSqlSb.append(",'"+inConStr[i]+"'");
					}else{
						selectSqlSb.append("'"+inConStr[i]+"'");
					}
				}
				selectSqlSb.append(")");
			}
			selectSqlSb.append(" )");
			if(timeNodeStart!=null&&!"".equals(timeNodeStart)){
				if(timeNodeStart.indexOf("-")!=-1){
					selectSqlSb.append(" AND cp.InputDate >= '"+timeNodeStart+"'");
				}else{
					throw new SQLException("时间节点起始时间不正确 例如：2000-01-01");
				}
			}
			if(timeNodeEnd!=null&&!"".equals(timeNodeEnd)){
				if(timeNodeEnd.indexOf("-")!=-1){
					selectSqlSb.append(" AND cp.InputDate < '"+timeNodeEnd+"'");
				}else{
					throw new SQLException("时间节点结束时间不正确 例如：2000-01-01");
				}
			}
		}
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End...");
		return rs;
	}
	
	/**
	 * 获取融资信息
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetOdtPledge(Connection conn) throws SQLException {
		log("Select start...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append(" SELECT p.PackID AS PackID,p.SignDate AS SignDate,p.Pledgee AS Pledgee,p.RecordNum AS RecordNum,");
		selectSqlSb.append(" p.BeginDate AS BeginDate,p.EndDate AS EndDate,p.ThawDate AS ThawDate,p.Checker AS Checker,");
		selectSqlSb.append(" pg.PledgeMoney AS PledgeMoney");
		selectSqlSb.append("  FROM relms.dbo.t_Pledge p");
		selectSqlSb.append(" LEFT JOIN (SELECT PackID,SUM(PledgeMoney) AS PledgeMoney ");
		selectSqlSb.append(" FROM relms.dbo.t_PledgeGoods GROUP BY PackID) AS pg ON p.PackID = pg.PackID");

		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End...");
		return rs;
	}
	
	/**
	 * 获取融资下的合同信息
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetOdtPledgeCon(Connection conn,String finPackID) throws SQLException {
		log("Select start...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append(" select pdg.PackID finPackID,p.PackID AS conPackID,SUM(pdg.PledgeMoney) AS PledgeMoney");
		selectSqlSb.append(" from relms.dbo.t_PledgeGoods pdg,");
		selectSqlSb.append(" relms.dbo.t_BuyPackEqu bpe,");
		selectSqlSb.append(" relms.dbo.t_Pack p,");
		selectSqlSb.append(" relms.dbo.t_BuyPack bp");
		selectSqlSb.append(" where pdg.EquipNum = bpe.equNum ");
		selectSqlSb.append(" and bp.PackID = bpe.BuyPackID");
		selectSqlSb.append(" and bp.HirepackID = p.PackID");
		selectSqlSb.append(" and pdg.EquipNum is not null");
		selectSqlSb.append(" and pdg.EquipNum != ''");
		selectSqlSb.append(" and pdg.PackID  = '"+finPackID+"'");
		selectSqlSb.append(" group by pdg.PackID,p.PackID");
		log(selectSqlSb.toString());
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End...");
		return rs;
	}
	
	/**
	 * 查询公共方法
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet GetSelectResultSet(Connection conn,String tableName) throws SQLException {
		log("Select start...");
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append("SELECT * from "+tableName);
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
			ResultSetMetaData rsmd = null; 
			rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					log(rsmd.getColumnName(i));
					System.out.print(rs.getString(i));
					System.out.print("\t\t");
				}
			}
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		log("Select End...");
		return rs;
	}
	
	/***
	 * 获取 操作员信息
	 * opNo opName brNo brName
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String,String>> getSysUser(Connection conn) throws SQLException{
		List<Map<String,String>> userList = new ArrayList<Map<String,String>>();
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append("SELECT su.OP_NO AS opNo,su.OP_NAME AS opName,su.BR_NO AS brNo,so.BR_NAME AS brName");
		selectSqlSb.append(" FROM sys_user su,sys_org so");
		selectSqlSb.append(" WHERE su.BR_NO = so.BR_NO");
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
			while (rs.next()) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("opNo", rs.getString("opNo"));
				map.put("opName", rs.getString("opName"));
				map.put("brNo", rs.getString("brNo"));
				map.put("brName", rs.getString("brName"));
				userList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rs.close();
		}
		return userList;
	}
	
	/***
	 * 获取 产品信息
	 * opNo opName brNo brName
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String,String>> getProdDef(Connection conn) throws SQLException{
		List<Map<String,String>> prodList = new ArrayList<Map<String,String>>();
		StringBuffer selectSqlSb = new StringBuffer();
		selectSqlSb.append("SELECT PROD_NO,PROD_NAME,PROD_TYPE,LEASE_TYPE");
		selectSqlSb.append(" FROM PROD_DEF");
		ResultSet rs = null;
		try {
			rs = execQuery(selectSqlSb.toString(), conn);
			while (rs.next()) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("prodNo", rs.getString("PROD_NO"));
				map.put("prodName", rs.getString("PROD_NAME"));
				map.put("prodType", rs.getString("PROD_TYPE"));
				map.put("leaseType", rs.getString("LEASE_TYPE"));
				prodList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rs.close();
		}
		return prodList;
	}
	
	/**
	 * 数据库链接属性
	 * @return
	 */
	public static Map<String,String> dbConn(){
		Map<String,String> dbConnMap = new HashMap<String,String>();
		String DBdriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://localhost:1433;databaseName=relms;IntegratedSecurity=True";
		String userName = "sa";
		String passWord = "sa";
		dbConnMap.put("DBdriver", DBdriver);
		dbConnMap.put("url", url);
		dbConnMap.put("userName", userName);
		dbConnMap.put("passWord", passWord);
		return dbConnMap;
	}

//	public static void main(String[] args) throws SQLException, IOException {
//		log("---------------------开始---------------------");
//		Connection conn = DbHelper.createConn();
//		//initMap();
//		String DBdriver = DbChanger.dbConn().get("DBdriver");
//		String url = DbChanger.dbConn().get("url");
//		String userName = DbChanger.dbConn().get("userName");
//		String passWord = DbChanger.dbConn().get("passWord");
//		try {
//			//Connection newConn  = DbHelper.createConn(DBdriver,url,userName,passWord);
//			//GetTableCol(conn,"cus_acc");//CUS_ACC
//			//SqlServerJDBCTest(newConn);
//			//newConn.close();
//			//MysqlJDBCALTER(conn,"cus_fin_pro_rpt","PF_FIELD");
//			DbTool.MysqlJDBCTest2(conn,"ODT_AC_LN_PAY_PLN","insertAcPs","insert");
//			conn.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			//conn.close();
//		}
//		log("---------------------结束---------------------");
//	}
}
