package app.component.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户相关枚举类
 * @author XIEZHENGUO
 *
 */
public class CusEnum {
	
	public static enum BUDGET_APP_STS{
		/***申请中***/
		SQZ("0","申请中"),
		SQZ1("1","申请中");
		
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
		
		private BUDGET_APP_STS(String code, String name){
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
				
		public static final BUDGET_APP_STS fromValue(String inputStr) {
			for (BUDGET_APP_STS c : BUDGET_APP_STS.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}
				
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (BUDGET_APP_STS c : BUDGET_APP_STS.values()){
				resMap.put(c.code, c.name);
			}
			return resMap;
		}
		
	}
}
