package app.component.interfaces.mobileinterface.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeanUtil {

	public static  Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");
	/**
	 * 把实体类转化为Map的方法
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBeanToMap(Object obj) {  
	    if (obj == null) {  
	        return null;  
	    }  
	    Map<String, Object> map = new HashMap<String, Object>();  
	    try {  
	        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	        for (PropertyDescriptor property : propertyDescriptors) {  
	            String key = property.getName();  
	            // 过滤class属性  
	            if (!"class".equals(key)) {
	                // 得到property对应的getter方法  
	                Method getter = property.getReadMethod();  
	                if(null != getter){
	                	Object value = getter.invoke(obj,new Object[0]);
	                	map.put(key, value);  
	                }
	            }  
	  
	        }  
	    } catch (Exception e) {  
	    	throw new NullPointerException("请求参数为空");
	    }  
	    return map;  
	}  
	/**
	 * 驼峰格式转数据库字段
	 * @param  property
	 * @return
	 */
	public static  String propertyToColumn(String property) {
		 if (property == null || property.isEmpty()){
			 return "";
		 }
		Matcher matcher = HUMP_PATTERN.matcher(property);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
