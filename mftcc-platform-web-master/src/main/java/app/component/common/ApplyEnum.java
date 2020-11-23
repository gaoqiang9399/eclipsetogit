package app.component.common;

import java.util.HashMap;
import java.util.Map;


/**
 * 业务申请枚举类
 * @author XIEZHENGUO
 *
 */
public class ApplyEnum {
	
	/***表单类别***/
	public static enum BUDGET_CATEGORY_TYPE{
		
		/***进件***/
		CATEGORY1("1","进件"),
		
		/***流程节点***/
		CATEGORY2("2","流程节点");
		
		private String code;
		private String name;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		private BUDGET_CATEGORY_TYPE(String code, String name){
			this.name = name;
			this.code = code;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).code;
		}
				
		public static final String getTypeName(String code) {
			return fromValue(code).name;
		}
				
		public static final BUDGET_CATEGORY_TYPE fromValue(String inputStr) {
			for (BUDGET_CATEGORY_TYPE c : BUDGET_CATEGORY_TYPE.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
				
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (BUDGET_CATEGORY_TYPE c : BUDGET_CATEGORY_TYPE.values()){
				resMap.put(c.code, c.name);
			}
			return resMap;
		}
		
	}
	
	
	/***表单类型***/
	public static enum BUDGET_SHOW_TYPE{
		
		/***正常表单***/
		SHOW_TYPE1("1","正常表单"),
		
		/***列表***/
		SHOW_TYPE2("2","列表");
		
		private String code;
		private String name;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		private BUDGET_SHOW_TYPE(String code, String name){
			this.name = name;
			this.code = code;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).code;
		}
				
		public static final String getTypeName(String code) {
			return fromValue(code).name;
		}
				
		public static final BUDGET_SHOW_TYPE fromValue(String inputStr) {
			for (BUDGET_SHOW_TYPE c : BUDGET_SHOW_TYPE.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
				
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (BUDGET_SHOW_TYPE c : BUDGET_SHOW_TYPE.values()){
				resMap.put(c.code, c.name);
			}
			return resMap;
		}
		
	}
	
	/***是否允许编辑***/
	public static enum BUDGET_EDIT_FLAG{
		
		/***允许编辑***/
		EDIT_FLAG1("1","是"),
		
		/***不允许编辑***/
		EDIT_FLAG0("0","否");
		
		private String code;
		private String name;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		private BUDGET_EDIT_FLAG(String code, String name){
			this.name = name;
			this.code = code;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).code;
		}
				
		public static final String getTypeName(String code) {
			return fromValue(code).name;
		}
				
		public static final BUDGET_EDIT_FLAG fromValue(String inputStr) {
			for (BUDGET_EDIT_FLAG c : BUDGET_EDIT_FLAG.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
				
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (BUDGET_EDIT_FLAG c : BUDGET_EDIT_FLAG.values()){
				resMap.put(c.code, c.name);
			}
			return resMap;
		}
		
	}
	
	/***是否启用***/
	public static enum BUDGET_USE_FLAG{
		
		/***启用***/
		USE_FLAG1("1","启用"),
		
		/***禁用***/
		USE_FLAG0("0","禁用");
		
		private String code;
		private String name;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		private BUDGET_USE_FLAG(String code, String name){
			this.name = name;
			this.code = code;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).code;
		}
				
		public static final String getTypeName(String code) {
			return fromValue(code).name;
		}
				
		public static final BUDGET_USE_FLAG fromValue(String inputStr) {
			for (BUDGET_USE_FLAG c : BUDGET_USE_FLAG.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
				
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (BUDGET_USE_FLAG c : BUDGET_USE_FLAG.values()){
				resMap.put(c.code, c.name);
			}
			return resMap;
		}
		
	}
	
	
	/***期限类型***/
	public static enum BUDGET_TERM_TYPE{
		
		/***期限月***/
		TERM_TYPE1("1","期限月"),
		
		/***期限日***/
		TERM_TYPE2("2","期限天");
		
		private String code;
		private String name;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		private BUDGET_TERM_TYPE(String code, String name){
			this.name = name;
			this.code = code;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).code;
		}
				
		public static final String getTypeName(String code) {
			return fromValue(code).name;
		}
				
		public static final BUDGET_TERM_TYPE fromValue(String inputStr) {
			for (BUDGET_TERM_TYPE c : BUDGET_TERM_TYPE.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
				
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (BUDGET_TERM_TYPE c : BUDGET_TERM_TYPE.values()){
				resMap.put(c.code, c.name);
			}
			return resMap;
		}
		
	}
	
	
	/***多表单与单表单***/
	public static enum BUDGET_DEFFLAG_TYPE{
		
		/***单表单***/
		DEFFLAG1("1","单表单"),
		
		/***多表单***/
		DEFFLAG0("0","多表单");
		
		private String code;
		private String name;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		private BUDGET_DEFFLAG_TYPE(String code, String name){
			this.name = name;
			this.code = code;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).code;
		}
				
		public static final String getTypeName(String code) {
			return fromValue(code).name;
		}
				
		public static final BUDGET_DEFFLAG_TYPE fromValue(String inputStr) {
			for (BUDGET_DEFFLAG_TYPE c : BUDGET_DEFFLAG_TYPE.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
				
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (BUDGET_DEFFLAG_TYPE c : BUDGET_DEFFLAG_TYPE.values()){
				resMap.put(c.code, c.name);
			}
			return resMap;
		}
		
	}
}
