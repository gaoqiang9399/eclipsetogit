/**
 * Copyright (C) DXHM 版权所有
 * 文件名： RiskParmEcode.java
 * 包名： app.component.common
 * 说明：
 * @author Javelin
 * @date 2017-7-4 上午9:35:09
 * @version V1.0
 */ 
package app.component.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： RiskParmEcode
 * 描述：风险拦截相关枚举
 * @author Javelin
 * @date 2017-7-4 上午9:35:09
 */
public class RiskParmEnum {

	/**
	 * 类名： RISK_PREVENT_LEVEL
	 * 描述：风险拦截级别 （对应数据字典 RISK_PREVENT_LEVEL）
	 * @author Javelin
	 * @date 2017-7-4 上午9:40:37
	 */
	public static enum RISK_PREVENT_LEVEL{
		/***无风险***/
		WFX("WFX","0","无风险"),
		/***低风险***/
		DFX("DFX","1","低风险"),
		/***高风险***/
		GFX("GFX","2","高风险"),
		/***业务拒绝***/
		YWJJ("YWJJ","99","业务拒绝"),
		/***业务满足***/
		Y("Y","Y","是"),
		/***业务不满足***/
		N("N","N","否");
		
		private String name;
		private String code;
		private String desc;
		
		private RISK_PREVENT_LEVEL(String code, String desc){
			this(desc,code,desc);
		}
		
		private RISK_PREVENT_LEVEL(String name, String code, String desc){
			this.name=name;
			this.code = code;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeCode(String name) {
			return fromValue(name).code;
		}
		
		public static final String getTypeDesc(String code) {
			return fromValue(code).desc;
		}
		public static final String getTypeName(String code) {
			return fromValue(code).name;
		}
		
		public static final RISK_PREVENT_LEVEL fromValue(String inputStr) {
			for (RISK_PREVENT_LEVEL c : RISK_PREVENT_LEVEL.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (RISK_PREVENT_LEVEL c : RISK_PREVENT_LEVEL.values()){
				resMap.put(c.code, c.desc);
			}
			return resMap;
		}
	}
	
	/**
	 * 类名： NODE_RISK_RULES_NO
	 * 描述：节点检查项规则编号
	 * @author Javelin
	 * @date 2017-7-4 下午4:13:10
	 */
	public static enum NODE_RISK_RULES_NO{
		/***保理业务风险拦截（测试）***/
		blfxlj("blfxlj","RISK_RULES1","保理业务风险拦截");
		
		private String name;
		private String code;
		private String desc;
		
		private NODE_RISK_RULES_NO(String code, String desc){
			this(desc,code,desc);
		}
		
		private NODE_RISK_RULES_NO(String name, String code, String desc){
			this.name=name;
			this.code = code;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeCode(String name) {
			return fromValue(name).code;
		}
		
		public static final String getTypeDesc(String code) {
			return fromValue(code).desc;
		}
		public static final String getTypeName(String code) {
			return fromValue(code).name;
		}
		
		public static final NODE_RISK_RULES_NO fromValue(String inputStr) {
			for (NODE_RISK_RULES_NO c : NODE_RISK_RULES_NO.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (NODE_RISK_RULES_NO c : NODE_RISK_RULES_NO.values()){
				resMap.put(c.code, c.desc);
			}
			return resMap;
		}
	}
}
