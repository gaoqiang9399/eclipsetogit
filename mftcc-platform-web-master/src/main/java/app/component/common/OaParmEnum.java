/**
 * Copyright (C) DXHM 版权所有
 * 文件名： OaParmEnum.java
 * 包名： app.component.common
 * 说明：
 * @author Javelin
 * @date 2017-6-10 上午11:16:45
 * @version V1.0
 */ 
package app.component.common;

import java.util.HashMap;
import java.util.Map;


/**
 * 类名： OaParmEnum
 * 描述：
 * @author Javelin
 * @date 2017-6-10 上午11:16:45
 *
 *
 */
public class OaParmEnum {
	
	/**
	 * 
	 * 类名： BUDGET_APP_STS
	 * 描述： 审批状态
	 * @author Javelin
	 * @date 2017-6-10 上午11:17:12
	 */
	public static enum BUDGET_APP_STS{
		/***申请中***/
		SQZ("SQZ","0","申请中"),
		/***审批中***/
		SPZ("SPZ","1","审批中"),
		/***审批通过***/
		SPTG("SPTG","2","审批通过"),
		/***已否决***/
		YFJ("YFJ","3","已否决");
		
		private String name;
		private String num;
		private String desc;
		
		private BUDGET_APP_STS(String num, String desc){
			this(desc,num,desc);
		}
		
		private BUDGET_APP_STS(String name, String num, String desc){
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
		
		public static final BUDGET_APP_STS fromValue(String inputStr) {
			for (BUDGET_APP_STS c : BUDGET_APP_STS.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (BUDGET_APP_STS c : BUDGET_APP_STS.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	/**
	 * 类名： BUDGET_TYPE
	 * 描述：预算类别
	 * @author Javelin
	 * @date 2017-6-13 下午4:53:21
	 */
	public static enum BUDGET_TYPE{
		/***有计划无预算***/
		yjhwys("yjhwys","0","有计划无预算"),
		/***有计划有预算***/
		yjhyys("yjhyys","1","有计划有预算"),
		/***无计划有预算***/
		wjhyys("wjhyys","2","无计划有预算");
		
		private String name;
		private String num;
		private String desc;
		
		private BUDGET_TYPE(String num, String desc){
			this(desc,num,desc);
		}
		
		private BUDGET_TYPE(String name, String num, String desc){
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
		
		public static final BUDGET_TYPE fromValue(String inputStr) {
			for (BUDGET_TYPE c : BUDGET_TYPE.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (BUDGET_TYPE c : BUDGET_TYPE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
}
