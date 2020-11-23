/**
 * Copyright (C) DXHM 版权所有
 * 文件名： CoreEnum.java
 * 包名： app.component.common
 * 说明：
 * @author Javelin
 * @date 2017-9-1 上午10:44:38
 * @version V1.0
 */ 
package app.component.common;

import java.util.HashMap;
import java.util.Map;

import cn.mftcc.util.PropertiesUtil;

/**
 * 类名： CoreEnum
 * 描述：
 * @author Javelin
 * @date 2017-9-1 上午10:44:38
 *
 *
 */
public class CoreEnum {
	
	/**
	 * 方法描述： 是否开启核心第三方
	 * @return
	 * boolean true：开启
	 * @author Javelin
	 * @date 2017-9-7 下午4:50:25
	 */
	public static boolean getThirdIsOpen() {
		boolean isOpen = false;
		String mftf_to_cbs = PropertiesUtil.getWebServiceProperty("mftf_to_cbs");
		if ("true".equals(mftf_to_cbs)) {
			isOpen = true;
		}
		return isOpen;
	}
	
	/**
	 * 类名： CORE_ID_TYPE
	 * 描述：供应链系统证件类型对应核心证件类型
	 * @author Javelin
	 * @date 2017-9-1 上午11:22:22
	 */
	public static enum CORE_ID_TYPE{
		/***身份证***/
		SFZ("1","0","身份证"),
		/***户口簿***/
		HKB("2","1","户口簿"),
		/**护照***/
		HZ("3","2","护照"),
		/***军官证***/
		JUGZ("J","3","军官证"),
		/***士兵证***/
		SBZ("6","4","士兵证"),
		/***港澳居民来往内地通行证***/
		GATXZ("7","5","港澳居民来往内地通行证"),
		/***台湾同胞来往内地通行证***/
		TWTXZ("I","6","台湾同胞来往内地通行证"),
		/***临时身份证/其他证件(对私)***/
		LXSFZ("H","7","临时身份证"),
		/***外国人居留证***/
		WGRJLZ("F","8","外国人居留证"),
		/***警官证***/
		JIGZ("G","9","警官证"),
		/***组织机构代码***/
		ZZJGTM("8","A","组织机构代码"),
		/***社会信用代码***/
		SHXYDM("S","B","社会信用代码"),
		/***营业执照号码***/
		YYZZH("A","C","营业执照号码");
		/** 核心证件类型 */
		private String name;
		/** 供应链证件类型 */
		private String num;
		private String desc;
		
		private CORE_ID_TYPE(String num, String desc){
			this(desc,num,desc);
		}
		
		private CORE_ID_TYPE(String name, String num, String desc){
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
		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		/*** 获取对应核心证件类型*/
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}
		
		public static final CORE_ID_TYPE fromValue(String num) {
			for (CORE_ID_TYPE c : CORE_ID_TYPE.values()) {
				if (c.num.equals(num)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + num);
		}
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (CORE_ID_TYPE c : CORE_ID_TYPE.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
}
