package app.component.report.util;

import java.util.HashMap;
import java.util.Map;

import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 类描述：报表查询条件工具类
 * @author 李伟
 * @date 2017-8-23 下午4:09:51
 */
public class MfReportQueryConfigUtil {
	/*andOr  0-and 1-or 
	compareType  运算符标志: 1-大于 2-大于等于 3-等于 4-小于 5-小于等于 6-不等于 7-like 8-in 9-not in
	varcharFlag  字符串标志: 1-字符串 2-整形 
	conditionFlag 条件标志: 1-部门 2-操作员 3-其他  4-客户类型 5-业务品种 6-押品类别 7-信息来源 8-客户姓名9-城市 11-渠道
	rightBracket 右括号标志: 1-有右括号 其他情况 无*/
	public final static String JSON_FLAG_0 = "0";//JSON标志 0-非json
	public final static String JSON_FLAG_1 = "1";//JSON标志 1-json
	public static Map<String,String> andOrMap = new HashMap<String,String>();
	public static Map<String,String> compareTypeMap = new HashMap<String,String>();
	public static Map<String,String> varcharFlagMap = new HashMap<String,String>();
	public static Map<String,String> conditionFlagMap = new HashMap<String,String>();
	static{
		andOrMap.put("0","and");
		andOrMap.put("1","or");
		
		compareTypeMap.put("1",">");
		compareTypeMap.put("2",">=");
		compareTypeMap.put("3","=");
		compareTypeMap.put("4","<");
		compareTypeMap.put("5","<=");
		compareTypeMap.put("6","<>");
		compareTypeMap.put("7","like ");
		compareTypeMap.put("8","in");
		compareTypeMap.put("9","not in");
		compareTypeMap.put("10","=");
	}
	
	/**
	 * @Description:拼接sql 
	 * @param conditonSql
	 * @return
	 * @author: 李伟
	 * @date: 2017-8-23 下午4:24:23
	 */
	@SuppressWarnings("rawtypes")
	public static String pieceTogetherSql(String conditonSql){
		StringBuffer sqlBuffer = new StringBuffer();
		JSONArray ja = JSONArray.fromObject(conditonSql);
		int num = 0;
		for(Object obj:ja){
			JSONObject jb = JSONObject.fromObject(obj);
			String value = jb.getString("value");
			if(StringUtil.isNotEmpty(value)){
				String condition = jb.getString("condition");
				String compareType = jb.getString("compareType");
				String varcharFlag = jb.getString("varcharFlag");
				String andOr = jb.getString("andOr");
			
				sqlBuffer.append(" ");
				if(num!=0){//第一个条件不需要加and
					sqlBuffer.append(andOrMap.get(andOr));
				}
				num++;
				sqlBuffer.append(" ");
				sqlBuffer.append(condition);
				sqlBuffer.append(" ");
				sqlBuffer.append(compareTypeMap.get(compareType));
				sqlBuffer.append(" ");
				String unit = "";
				if("1".equals(varcharFlag)){//字符串类型
					unit = "'";
				}
				if("7".equals(compareType)){//like
					//value = unit+"%"+value+unit+"%";
					value = unit+"%"+value+"%"+unit;
				}else if("8".equals(compareType)||"9".equals(compareType)){//in,not in
					value = StringUtil.arrayToString(value.split(","),"'",",");
					value = "("+value+")";
				}else if("10".equals(compareType)){
					value = "DATE_FORMAT(date_add(CONCAT("+value+",'01'), interval 1 MONTH), '%Y%m%d')";
				}else {
				}
				sqlBuffer.append(value);
				Map resultMap = jb;
				Object rightBracket = resultMap.get("rightBracket");
				if(rightBracket!=null && "1".equals(rightBracket.toString())){//右括号标志()
					sqlBuffer.append(")");
				}
				sqlBuffer.append(" ");
			}
		}
		String result = sqlBuffer.toString();
		if(StringUtil.isEmpty(result)){
			result = " 1=1 ";
		}
		return result;
	}
	
	/***
	 * @Description:拼接查询方式为javaBean方式的公共条件
	 * @return
	 * @author: 李伟
	 * @date: 2017-8-24 上午9:11:27
	 */
	public static String pieceJavaBeanSql(String conditionSql){
		StringBuffer sqlBuffer = new StringBuffer();
		JSONArray ja = JSONArray.fromObject(conditionSql);
		for(Object obj:ja){
			JSONObject jb = JSONObject.fromObject(obj);
			String value = jb.getString("value");
			if(StringUtil.isNotEmpty(value)){
				String compareType = jb.getString("compareType");
				if(!"8".equals(compareType) && !"9".equals(compareType)){
					String condition = jb.getString("condition");
					String varcharFlag = jb.getString("varcharFlag");
					String andOr = jb.getString("andOr");
				
					sqlBuffer.append(" ");
					sqlBuffer.append(andOrMap.get(andOr));
					sqlBuffer.append(" ");
					sqlBuffer.append(condition);
					sqlBuffer.append(" ");
					sqlBuffer.append(compareTypeMap.get(compareType));
					sqlBuffer.append(" ");
					String unit = "";
					if("1".equals(varcharFlag)){//字符串类型
						unit = "'";
					}
					if("7".equals(compareType)){//like 
						//value = unit+"%"+value+unit+"%";
						value = unit+"%"+value+"%"+unit;
					}
					sqlBuffer.append(value);
					sqlBuffer.append(" ");
				}
			}
		}
		return sqlBuffer.toString();
	}
	

}
