/**
 * Copyright (C) DXHM 版权所有
 * 文件名： CWEnumBean.java
 * 包名： app.component.finance.util
 * 说明：
 * @author 刘争帅
 * @date 2016-12-28 下午2:49:12
 * @version V1.0
 */ 
package app.component.finance.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： CWEnumBean
 * 描述：财务中科目使用的枚举类
 * @author 刘争帅
 * @date 2016-12-28 下午2:49:12
 *
 *
 */
public class CWEnumBean {
	
	/**
	 * 类名： YES_OR_NO
	 * 描述：Y: 是 与 N: 否
	 * @author Javelin
	 * @date 2016-12-30 下午3:35:27
	 */
	public static enum YES_OR_NO{
		/***是***/
		SHI("SHI","Y","是"),
		/***否***/
		FOU("FOU","N","否");
		
		private String name;
		private String num;
		private String desc;
		
		private YES_OR_NO(String num, String desc){
			this(desc,num,desc);
		}
		
		private YES_OR_NO(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final YES_OR_NO fromValue(String inputStr) {
			for (YES_OR_NO c : YES_OR_NO.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("KEMUTYPE枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (YES_OR_NO c : YES_OR_NO.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	/**
	 * 类名： VOUCHER_FLAG
	 * 描述：0：已制单；1：已审核：2：已签字；3：已核查；4：已记账；5：已作废
	 * @author Javelin
	 * @date 2016-12-30 下午3:36:24
	 */
	public static enum VOUCHER_FLAG{
		/***已制单***/
		YZD("YZD","0","已制单"),
		/***已审核***/
		YSH("YSH","1","已审核"),
		/***已签字***/
		YQZ("YQZ","2","已签字"),
		/***已核查***/
		YHC("YHC","3","已核查"),
		/***已记账： 显示已审核，2017年8月17日 把已审核修改成已记账***/
		YJZ("YJZ","4","已记账"),
		/***已作废***/
		YZC("YZC","5","已作废");
		
		private String name;
		private String num;
		private String desc;
		
		private VOUCHER_FLAG(String num, String desc){
			this(desc,num,desc);
		}
		
		private VOUCHER_FLAG(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getNum() {
			return num;
		}
		
		public void setNum(String num) {
			this.num = num;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}
		
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		
		public static final VOUCHER_FLAG fromValue(String inputStr) {
			for (VOUCHER_FLAG c : VOUCHER_FLAG.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (VOUCHER_FLAG c : VOUCHER_FLAG.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	
	/**
	 * 类名： SYSPARAM_CODE
	 * 描述：系统参数中的Pcode码
	 * @author Javelin
	 * @date 2017-1-12 下午3:24:22
	 */
	public static enum SYSPARAM_CODE{
		/**现金流量分析 1：启用，0：禁用*/
		xjllfx("xjllfx","10000","现金流量分析"),
		/**出纳启用期间*/
		cnqyqj("cnqyqj","10001","出纳启用期间"),
		/**凭证金额负数开关 0：允许；1：不允许*/
		pzfskg("pzfskg","10002","凭证负数开关"),
		/**在结账时进行现金流量分析检查  0：检查；1：不检查*/
		jzxjfxjc("jzxjfxjc","10003","结账现金流量分析检查"),
		/**报表是否按其科目方向调整 0：是，1：否*/
		sfkmtz("sfkmtz","10012","报表是否按其科目方向调整"),
		/***按科目余额反向结转 0：是，1：否***/
		yefxjz("yefxjz","10013","按科目余额反向结转"),
		/***资产负债表显示列***/
		zcfzbxs("001","80002","资产负债表显示列"),
		/***利润分配表显示列***/
		lrfpbxs("003","80003","利润分配表显示列"),
		/***现金流量表显示列***/
		xjllbxs("002","80004","现金流量表显示列"),
		/***启用会计期间***/
		qjqyqj("qjqyqj","80011","启用会计期间"),
		/***结账-本年利润科目***/
		bnlrkm("bnlrkm","80013","结账-本年利润科目"),
		/***结账-以前年度损益调整科目***/
		sytzkm("sytzkm","80016","结账-以前年度损益调整科目"),
		/***结账-以前年度损益调整的结转科目***/
		sytzjzkm("sytzjzkm","80017","结账-以前年度损益调整的结转科目"),
		/***结账-年结未分配利润科目***/
		wfplrkm("wfplrkm","80014","结账-年结未分配利润科目"),
		/***科目编码格式类型0：4-2-2; 1：4-3-3; 2：4-4-4***/
		kmbmgs("kmbmgs","80027","科目编码格式类型"),
		/***报表项自增编码***/
		bbxzzbm("bbxzzbm","80035","报表项自增编码"),
		/***一级科目位数 3： 3-2-2；4：4-2-2 ***/
		yjkmws("yjkmws","80036","一级科目位数"),
		/***财务初始化标志 0:未初始化； 1：已初始化***/
		cwcshbz("cwcshbz","90000","财务初始化标志"),
		/***财务余额初始化标识***/
		cwyecshbz("cwyecshbz","90001","财务余额初始化标识"),
		/***财务余额初始化日期***/
		yecshrq("yecshrq","90002","财务余额初始化日期"),
		/***财务科目初始配置标志***/
		kmcshbz("kmcshbz","90003","财务科目初始配置标志"),
		/***设置计提方式***/
		jititype("jititype","10004","设置计提方式"),
		/***设置全部计提方式即提百分比***/
		alljiti("alljiti","10005","设置全部计提方式即提百分比"),
		/***设置正常计提方式即提百分比***/
		zcjiti("zcjiti","10006","设置正常计提方式即提百分比"),
		/***设置关注计提方式即提百分比***/
		gzjiti("gzjiti","10007","设置关注计提方式即提百分比"),
		/***设置次级计提方式即提百分比***/
		cjjiti("cjjiti","10008","设置次级计提方式即提百分比"),
		/***设置可疑计提方式即提百分比***/
		kyjiti("kyjiti","10009","设置可疑计提方式即提百分比"),
		/***设置损失计提方式即提百分比***/
		ssjiti("ssjiti","10010","设置损失计提方式即提百分比"),
		/**价税分离方式*/
		pricetaxtype("pricetaxtype","20001","价税分离方式"),
		/**价税分离方式*/
		pricetaxrate("pricetaxrate","20002","税率"),
		/***设置计提借方科目***/
		jiejtacc("jiejtacc","10034","设置计提借方科目"),
		/***设置计提贷方科目***/
		daijtacc("daijtacc","10035","设置计提贷方科目");
		
		private String name;
		private String num;
		private String desc;
		
		private SYSPARAM_CODE(String num, String desc){
			this(desc,num,desc);
		}
		private SYSPARAM_CODE(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}
		
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		public static final SYSPARAM_CODE fromValue(String inputStr) {
			for (SYSPARAM_CODE c : SYSPARAM_CODE.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (SYSPARAM_CODE c : SYSPARAM_CODE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	/**
	 * 类名： VCH_BES_TYPE
	 * 描述：凭证主表业务类型
	 * @author Javelin
	 * @date 2017-1-13 下午2:28:23
	 */
	public static enum VCH_BES_TYPE{
		/**
		 * 原系统编号：
		 * 01:放款，02：收取咨询费，03：固定利息提前扣除，04：主动还款，05：贷款优惠，06：贷款本金核销，
		 * 07：贷款利息核销，08：核销收回，09：贷款核销，10：贷款收回核销，11：结转，12：长期待摊费用，13：费用审批，
		 * 14：固定资产减值，15：固定资产盘亏，16：固定资产盘盈，17：固定资产盘盈，18：固定资产新增，19：固定资产折旧，20：固定资产终止，
		 * 21：其他应收款挂账，22：其他应收款收款，23：税金管理_上缴，24：税金管理_提取，25:融资引入，26：融资归还，
		 * 27：固定资产折旧—批量，28：长期待摊摊销-批量，29：年终处理-数据提取，30：年终处理-数据分配 31，登记凭证  
		 * 32: 结转以前年度损益调整  33 : 结转待处理资产损益
		 */
		/**手工登记凭证*/
		sgdj("sgdj","01","手工登记凭证"),
		/**结账-结转凭证*/
		jzpz("jzpz","02","结转凭证"),
		/**结账-结转以前年度损益调整*/
		jzsytz("jzsytz","03","结转以前年度损益调整"),
		/**结账-固定资产折旧*/
		jzzczj("jzzczj", "04","结账-固定资产折旧"),
		/**结账-长期待摊摊销*/
		jzcqtx("jzcqtx", "05","结账-长期待摊摊销"),
		/**复核-业务记账*/
		ywfhjz("ywfhjz", "06","复核-业务记账"),
		/**年终处理-数据提取*/
		yeartq("yeartq", "07","年终处理-数据提取"),
		/**年终处理-数据分配*/
		yearfp("yearfp", "08","年终处理-数据分配"),
		/**年终处理*/
		yearcl("yearcl", "09","年终处理");
		
		private String name;
		private String num;
		private String desc;
		
		private VCH_BES_TYPE(String num, String desc){
			this(desc,num,desc);
		}
		private VCH_BES_TYPE(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}
		
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		public static final VCH_BES_TYPE fromValue(String inputStr) {
			for (VCH_BES_TYPE c : VCH_BES_TYPE.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (VCH_BES_TYPE c : VCH_BES_TYPE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	/**
	 * 类名： COM_ITEMS_TYPE
	 * 描述：科目辅助核算类别
	 * @author Javelin
	 * @date 2017-3-7 上午11:38:17
	 */
	public static enum COM_ITEMS_TYPE{
		/***员工***/
		employ("employ","1000001","员工"),
		/***部门***/
		dept("dept","1000002","部门"),
		/***客户***/
		customer("customer","1000003","客户");
		
		private String name;
		private String num;
		private String desc;
		
		private COM_ITEMS_TYPE(String num, String desc){
			this(desc,num,desc);
		}
		
		private COM_ITEMS_TYPE(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getNum() {
			return num;
		}
		
		public void setNum(String num) {
			this.num = num;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}
		
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		
		public static final COM_ITEMS_TYPE fromValue(String inputStr) {
			for (COM_ITEMS_TYPE c : COM_ITEMS_TYPE.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("COM_ITEMS_TYPE枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getReturnMethodMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (COM_ITEMS_TYPE c : COM_ITEMS_TYPE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	
	/**
	 * 
	 * 类名： KEMUTYPE
	 * 描述：科目类型
	 * @author 刘争帅
	 * @date 2016-12-28 下午2:53:20
	 * 
	 *
	 */
	public static enum KEMUTYPE{
		/***资产类***/
		zcl("zcl","1","资产类"),
		/***负债类***/
		fzl("fzl","2","负债类"),
		/***共同类***/
		gtl("gtl","3","共同类"),
		/***所有者权益类***/
		syzqy("syzqy","4","所有者权益类"),
		/***成本类***/
		cbl("cbl","5","成本类"),
		/***损益类***/
		syl("syl","6","损益类"),
		/***表外类***/
		bwl("bwl","7","表外类");
		
		private String name;
		private String num;
		private String desc;
		
		private KEMUTYPE(String num, String desc){
			this(desc,num,desc);
		}
		
		private KEMUTYPE(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final KEMUTYPE fromValue(String inputStr) {
			for (KEMUTYPE c : KEMUTYPE.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("KEMUTYPE枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getReturnMethodMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (KEMUTYPE c : KEMUTYPE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	public static enum JIEDAITYPE{
		/***1是借***/
		jie("jie","1","借"),
		/***2是贷***/
		dai("dai","2","贷");
		
		
		private String name;
		private String num;
		private String desc;
		
		private JIEDAITYPE(String num, String desc){
			this(desc,num,desc);
		}
		
		private JIEDAITYPE(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final JIEDAITYPE fromValue(String inputStr) {
			for (JIEDAITYPE c : JIEDAITYPE.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("JIEDAITYPE枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getReturnMethodMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (JIEDAITYPE c : JIEDAITYPE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	public static enum KEMUZINO{
		/***资产类***/
		zcl("zcl","1","80028"),
		/***负债类***/
		fzl("fzl","2","80029"),
		/***共同类***/
		gtl("gtl","3","80030"),
		/***所有者权益类***/
		syzqy("syzqy","4","80031"),
		/***成本类***/
		cbl("cbl","5","80032"),
		/***损益类***/
		syl("syl","6","80033"),
		/***表外类***/
		bwl("bwl","7","80034");
		
		private String name;
		private String num;
		private String desc;
		
		private KEMUZINO(String num, String desc){
			this(desc,num,desc);
		}
		
		private KEMUZINO(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final KEMUZINO fromValue(String inputStr) {
			for (KEMUZINO c : KEMUZINO.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("KEMUZINO枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getReturnMethodMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (KEMUZINO c : KEMUZINO.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	/**
	 * 
	 * 类名： JY_TYPE
	 * 描述：交易类型
	 * @author lzshuai
	 * @date 2017-3-8 下午2:25:21
	 * 
	 *
	 */
	public static enum JY_TYPE{
	
		/***信贷***/
		XDTYPE("XDType","02","信贷"),
		/***财务***/
		CWTYPE("CWType","01","财务");
		private String name;
		private String num;
		private String desc;
		
		private JY_TYPE(String num, String desc){
			this(desc,num,desc);
		}
		
		private JY_TYPE(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final JY_TYPE fromValue(String inputStr) {
			for (JY_TYPE c : JY_TYPE.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("JY_TYPE枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (JY_TYPE c : JY_TYPE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	public static enum XD_SERVICE_DATA{
		/***财务***/
		//CWTYPE("CWType","01","财务"),
		/***信贷***/
		XDFK("xdfk","CODE20001","放款"),
		XDZDHK("xdzdhk","CODE20002","还款"),//主动还款(正常/提前/逾期)  2017年9月14日 改为还款
		FKSF("fksf","CODE20003","放款时收取(利息/服务费)"),
		DJDSQFY("djdsqfy","CODE20004","费用收取"),//将 单节点收取费用 修改成费用收取 
		YOUHUI("youhui","CODE20005","优惠(表外账)"),
		JIEYU("jieyu","CODE20006","结余"),
		AGAINST("against","CODE20007","冲抵"),
		TUIFEE("tuifee","CODE20008","退费"),
		TUIXI("tuixi","CODE20009","退息"),
		GDLXTQKC("gdlxtqkc","CODE20010","固定利息提前扣除"),
		/*XDSF("xdsf","CODE20003","收费"),*/
		/*XDJM("xdjm","CODE20004","减免"),//先把减免的放开
*/		/*JITI("jiti","CODE20011","计提风险准备金"),*/
		DKBJHX("dkbjhx","CODE20012","贷款本金核销"),
		DKLXHX("dklxhx","CODE20013","贷款利息核销"),
		HXSH("hxsh","CODE20014","核销收回"),
		DKHXBW("dkhxbw","CODE20015","贷款核销(表外账)"),
		DKHXSH("dkhxsh","CODE20016","贷款核销收回(表外账)");
		
		//YEARFP("yearfp","CODE20017","年终处理-数据分配"),
		//YEARTQ("yeartq","CODE20018","年终处理-数据提取");
		/*SQZXF("sqzxf","CODE20013","收取咨询费"),*/
	
		private String name;
		private String num;
		private String desc;
		
		private XD_SERVICE_DATA(String num, String desc){
			this(desc,num,desc);
		}
		
		private XD_SERVICE_DATA(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final XD_SERVICE_DATA fromValue(String inputStr) {
			for (XD_SERVICE_DATA c : XD_SERVICE_DATA.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("XD_SERVICEDATA枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (XD_SERVICE_DATA c : XD_SERVICE_DATA.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	/**
	 * 
	 * 类名： CW_SERVICE_DATA
	 * 描述：财务业务类型的枚举类
	 * @author lzshuai
	 * @date 2017-10-14 下午4:04:40
	 * 
	 *
	 */
	public static enum CW_SERVICE_DATA{
		/***财务***/
	
		YEARFP("yearfp","CODE20017","年终处理-数据分配"),
		YEARTQ("yeartq","CODE20018","年终处理-数据提取");
		/*SQZXF("sqzxf","CODE20013","收取咨询费"),*/
	
		private String name;
		private String num;
		private String desc;
		
		private CW_SERVICE_DATA(String num, String desc){
			this(desc,num,desc);
		}
		
		private CW_SERVICE_DATA(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final CW_SERVICE_DATA fromValue(String inputStr) {
			for (CW_SERVICE_DATA c : CW_SERVICE_DATA.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("CW_SERVICE_DATA枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (CW_SERVICE_DATA c : CW_SERVICE_DATA.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	/**
	 * 
	 * 类名： REVIEW_BUSINESS_DATA
	 * 描述：业务记账规则-
	 * @author lzshuai
	 * @date 2017-4-20 下午2:32:12
	 * 
	 *
	 */
	public static enum REVIEW_BUSINESS_DATA{
		
		/***现金/银行***/
		CASHBANK("1","cashBankAmt","现金/银行"),
		/***本金***/
		PRINCIPALAMT("2","principalAmt","本金"),
		/**利息*/
		INSTAMT("3","intstAmt","利息"),
		/**罚息*/
		OVERINISTAMT("4","overInistAmt","罚息"),
		/**服务费*/
		/*EXPENSES("5","feeService","服务费"),*/
		/**优惠表外类科目**/
		BALANCESHEET("6","preferAmt","优惠"),
		/** 逾期利息*/
		DUEAMT("7","overDueAmt","逾期利息"),
		/** 复利利息*/
		FULIRATEAMT("8","cmpdIntstAmt","复利利息"),
		/**违约金*/
		WEIYUEJIN("9","breachAmt","违约金"),
		/**手续费*/
		/*SHOUXUFEI("10","feePoundage","手续费"),*/
		/**结余*/
		JIEYU("11","balanceAmt","结余"),
		/**冲抵*/
		CHONGDI("12","againstAmt","冲抵"),
		/**计提金额*/
		JTBAL("13","jtBal","计提金额"),
		/**逾期本金*/
		OVERPRINCIPAL("14","overduePrincipalAmt","逾期本金"),
		/**核销金额*/
		HEXIAOJINE("15","cancelAmt","核销金额"),//修改核销
		/**核销金额*/
		TUIKUANJINE("16","refundAmt","退款金额"),
		/**税金*/
		PRICETAXAMT("17","priceTaxAmt","税金"),
		/**利润分配——未分配利润 */
		WFPLR("18","wfplrAmt","利润分配——未分配利润 "),
		/**利润分配—提取法定盈余公积*/
		TQFDYYGJ("19","fdyygjAmt","利润分配—提取法定盈余公积"),
		/**利润分配—提取一般风险准备*/
		TQYBFXZB("20","ybfxzbAmt","利润分配—提取一般风险准备"),
		/**利润分配—提取任意盈余公积*/
		TQRYYYGJ("21","ryyygjAmt","利润分配—提取任意盈余公积"),
		/**利润分配—提取应付股利*/
		TQYSGL("22","yfglAmt","利润分配—提取应付股利");
		
		
		
		private String name;
		private String num;
		private String desc;
		
		private REVIEW_BUSINESS_DATA(String num, String desc){
			this(desc,num,desc);
		}
		
		private REVIEW_BUSINESS_DATA(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getNum() {
			return num;
		}
		
		public void setNum(String num) {
			this.num = num;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}
		
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		
		public static final REVIEW_BUSINESS_DATA fromValue(String inputStr) {
			for (REVIEW_BUSINESS_DATA c : REVIEW_BUSINESS_DATA.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("REVIEW_BUSINESS_DATA枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final List<Map<String, String>> getList() {
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			Map<String, String> resMap = null;
			for (REVIEW_BUSINESS_DATA c : REVIEW_BUSINESS_DATA.values()){
				resMap = new HashMap<String, String>();
				resMap.put("id", c.name);
				resMap.put("key", c.num);//保存key
				resMap.put("value", c.desc);
				list.add(resMap);
				resMap = null;
			}
			return list;
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (REVIEW_BUSINESS_DATA c : REVIEW_BUSINESS_DATA.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	/**
	 * 
	 * 类名： ADAPT_TRANSATION
	 * 描述：适应交易数据
	 * @author lzshuai
	 * @date 2017-4-21 上午10:47:01
	 * 
	 *
	 */
	public static enum ADAPT_TRANSATION{
		/**
		 *全部
		 */
		TALL("tall","0","全部"),
		/*JITIAMT("jitiamt","jiti001","准备金计提"),*/
		/**
		 *年结数据分配
		 */
		YEARFP("yearfp","year001","年结数据分配"),
		/**
		 *年结数据提取
		 */
		YEARTQ("yeartq","year002","年结数据提取"),
		/**
		 *抵押
		 */
		TDIYA("tdiya","2","抵押"),
		/**
		 *质押
		 */
		TZHIYA("tzhiya","3","质押"),
		/**
		 *信用
		 */
		TXINYONG("txinyong","4","信用"),
		/**
		 *转让
		 */
		TZHUANRANG("tzhuanrang","5","转让"),
		/**
		 *车贷
		 */
		TCHEDAI("tchedai","6","车贷"),
		/**
		 *消费贷
		 */
		TXIAOFEIDAI("txiaofeidai","7","消费贷");

		
		private String name;
		private String num;
		private String desc;
		
		private ADAPT_TRANSATION(String num, String desc){
			this(desc,num,desc);
		}
		
		private ADAPT_TRANSATION(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getNum() {
			return num;
		}
		
		public void setNum(String num) {
			this.num = num;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}
		
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		
		public static final ADAPT_TRANSATION fromValue(String inputStr) {
			for (ADAPT_TRANSATION c : ADAPT_TRANSATION.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("ADAPT_TRANSATION枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (ADAPT_TRANSATION c : ADAPT_TRANSATION.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	/**
	 * 类名： ASSETS_METHOD
	 * 描述：固定资产折旧方式
	 * @author Javelin
	 * @date 2017-5-8 上午10:43:33
	 */
	public static enum ASSETS_METHOD{
		/**
		 * 平均年限法
		 */
		pjnxf("pjnxf","0","平均年限法"),
		/**
		 * 不提折旧
		 */
		bjtzj("bjtzj","1","不提折旧");
		
		private String name;
		private String num;
		private String desc;
		
		private ASSETS_METHOD(String num, String desc){
			this(desc,num,desc);
		}
		
		private ASSETS_METHOD(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getNum() {
			return num;
		}
		
		public void setNum(String num) {
			this.num = num;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}
		
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		
		public static final ASSETS_METHOD fromValue(String inputStr) {
			for (ASSETS_METHOD c : ASSETS_METHOD.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("ASSETS_METHOD枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (ASSETS_METHOD c : ASSETS_METHOD.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	
	/**
	 * 
	 * 类名： REPORT_TYPE
	 * 描述：报表类型
	 * @author lzshuai
	 * @date 2017-5-22 上午11:36:14
	 * 
	 *
	 */
	public static enum REPORT_TYPE{
		/**
		 * 资产负债表
		 */
		zcfzb("zcfzb","001","资产负债表"),
		/**
		 * 现金流量表
		 */
		xjllb("xjllb","002","现金流量表"),
		/**
		 * 利润表
		 */
		lrb("lrb","003","利润表");
		
		private String name;
		private String num;
		private String desc;
		
		private REPORT_TYPE(String num, String desc){
			this(desc,num,desc);
		}
		
		private REPORT_TYPE(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getNum() {
			return num;
		}
		
		public void setNum(String num) {
			this.num = num;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}
		
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		
		public static final REPORT_TYPE fromValue(String inputStr) {
			for (REPORT_TYPE c : REPORT_TYPE.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("REPORT_TYPE枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (REPORT_TYPE c : REPORT_TYPE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	public static enum USEFLAG_STA{
		/***启用***/
		YESUSE("YESUSE","1","启用"),
		/***否***/
		NOUSE("NOUSE","0","禁用");
		
		private String name;
		private String num;
		private String desc;
		
		private USEFLAG_STA(String num, String desc){
			this(desc,num,desc);
		}
		
		private USEFLAG_STA(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final USEFLAG_STA fromValue(String inputStr) {
			for (USEFLAG_STA c : USEFLAG_STA.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("KEMUTYPE枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (USEFLAG_STA c : USEFLAG_STA.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	/**
	 * 
	 * 类名： USEFLAG_STA
	 * 描述：计提五级分类
	 * @author lzshuai
	 * @date 2017-8-24 上午11:48:24
	 * 
	 *
	 */
	public static enum JITI_FIVE_CLASS{
		/***全部贷款余额***/
		ALLLOANBAL("allloanbal","0","全部贷款余额"),
		/***正常贷款余额***/
		ZCLOANBAL("zcloanbal","1","正常贷款余额"),
		/***关注贷款余额***/
		GZLOANBAL("gzloanbal","2","关注贷款余额"),
		/***关注贷款余额***/
		CJLOANBAL("cjloanbal","3","次级贷款余额"),
		/***可疑贷款余额***/
		KYLOANBAL("kyloanbal","4","可疑贷款余额"),
		/***损失贷款余额***/
		SSLOANBAL("ssloanbal","5","损失贷款余额");
		
		private String name;
		private String num;
		private String desc;
		
		private JITI_FIVE_CLASS(String num, String desc){
			this(desc,num,desc);
		}
		
		private JITI_FIVE_CLASS(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final JITI_FIVE_CLASS fromValue(String inputStr) {
			for (JITI_FIVE_CLASS c : JITI_FIVE_CLASS.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("JITI_FIVE_CLASS枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (JITI_FIVE_CLASS c : JITI_FIVE_CLASS.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	/** 凭证导出文件类型
	 * 类名： VCH_OUT_FILE_TYPE
	 * 描述：
	 * @author Javelin
	 * @date 2017-12-23 下午2:37:00
	 */
	public static enum VCH_OUT_FILE_TYPE{
		/***EXCEL***/
		EXCEL("EXCEL","0","EXCEL"),
		/***XML***/
		XML("XML","1","XML");
		
		private String name;
		private String num;
		private String desc;
		
		private VCH_OUT_FILE_TYPE(String num, String desc){
			this(desc,num,desc);
		}
		
		private VCH_OUT_FILE_TYPE(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getNum() {
			return num;
		}
		
		public void setNum(String num) {
			this.num = num;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}
		
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		
		public static final VCH_OUT_FILE_TYPE fromValue(String inputStr) {
			for (VCH_OUT_FILE_TYPE c : VCH_OUT_FILE_TYPE.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("JITI_FIVE_CLASS枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (VCH_OUT_FILE_TYPE c : VCH_OUT_FILE_TYPE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
}
