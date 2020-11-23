package app.util;

import java.util.HashMap;
import java.util.Map;

import app.base.Criteria;
import app.base.CustomSort;
import net.sf.json.JSONObject;

/**
 * JSONStr 处理类
 * @author JIZH
 *
 */
public class JsonStrHandling {
	
	public static String handlingNull(String jsonStr) {
		if(jsonStr.indexOf("{")!=-1&&jsonStr.indexOf("}")!=-1) {
			jsonStr=jsonStr.replaceAll("=null", "=\"\"");
		}
		return jsonStr;
	}
	/**
	 * json 字符串转对象
	 * @param jsonStr
	 * @return
	 */
	public static <T> T handlingStrToBean(Object objStr, Class<T> entityClass) {
		if (objStr != null && !"".equals(objStr.toString())) {
			objStr = JSONObject.fromObject(objStr);
			JSONObject jsonObj = JSONObject.fromObject(handlingNull(objStr.toString()));
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("criteriaLists", Criteria.class);
			classMap.put("customSorts", CustomSort.class);
			objStr = JSONObject.toBean(jsonObj, entityClass, classMap);
		}
		return (T) objStr;
	}

}
