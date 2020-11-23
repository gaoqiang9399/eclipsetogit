package app.component.pss.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 类名： PublicUtil
 * 描述：该公共类 不涉及事务，涉及事务调其他公共类
 * @author HGX
 * @param <T>
 * @date 2017-08-21 下午01:37:36
 */
public class PssPublicUtil<T> {
	
	//商品id字段
	private static String GOODSID = "goodsId";
	private static String GOODSIDM = "getGoodsId";

	/**
	 * 方法描述： 将JSON字符串数组转换成List<MAP>
	 * @param ajaxData
	 * @return
	 * List<Map<String,String>>
	 * @author HGX
	 * @date 2017-08-21 下午01:38:10
	 */
	public static List<Map<String, String>> getMapByJsonArr(String jsonArr){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		Map<String, String> mapKey = new HashMap<String, String>();
		JSONArray jas = JSONArray.fromObject(jsonArr);
		for(int i = 0;i < jas.size();i++){
			map = new HashMap<String, String>();
			JSONArray jaChilds = jas.getJSONArray(i);
			for(int j = 0;j < jaChilds.size();j++){
				if(i == 0){
					mapKey.put(j + "", jaChilds.getString(j));
				}else{
					map.put(mapKey.get(j + ""), jaChilds.getString(j));
				}
			}
			if(i != 0){
				//TODO 校验map信息 20170821
				if(!validMap(map)){
					continue;
				}
				list.add(map);
			}
		}
		
		return list;
	}
	
	/**
	 * 方法描述： 将JSON对象转换成List<T>
	 * @param ajaxData
	 * @return
	 * List<T>
	 * @author HGX
	 * @param <T>
	 * @param String
	 * @throws Exception 
	 * @date 2017-08-23 下午01:38:10
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getMapByJsonObj(T t, String jsonArr) throws Exception{
		List<T> list = new ArrayList<T>();
		JSONArray jas = JSONArray.fromObject(jsonArr);
		for(int i = 0;i < jas.size();i++){
			JSONObject jaChilds = jas.getJSONObject(i);
			t = (T)JSONObject.toBean(jaChilds, t.getClass());
			//TODO 校验class信息 20170823
			/*if(!validMap(t)){
				continue;
			}*/
			list.add(t);
		}
		
		return list;
	}
	
	//校验map信息
	private static boolean validMap(Map<String, String> map){
		boolean reBoo = false;
		if(null != map.get(GOODSID) && !"".equals(map.get(GOODSID))){
			reBoo = true;
		}
		return reBoo;
	}
	
	//校验map信息
	private static <T> boolean validMap(T t) throws Exception{
		boolean reBoo = false;
		Method m = t.getClass().getMethod(GOODSIDM);
		String gdVal = (String) m.invoke(t); // 调用getter方法获取属性值
		if(null != gdVal && !"".equals(gdVal)){
			reBoo = true;
		}
		return reBoo;
	}
	
	//分录信息检验，根据商品id检查
	public static <T>boolean validateSepList(List<T> list) throws Exception{
		boolean reBoo = false;
		Method m = null;
		String gdVal = "";
		for(T tTemp:list){
			m = tTemp.getClass().getMethod(GOODSIDM);
			if(null != tTemp){
				gdVal = (String)m.invoke(tTemp);
				if(null != gdVal && !"".equals(gdVal)){
					reBoo = true;
					break;
				}
			}
		}
		
		return reBoo;
	}
	
	//分录信息过滤（根据是否存在商品ID判断）
	public static <T>List<T> filterSepList(List<T> list) throws Exception{
		List<T> reList = new ArrayList<T>();
		Method m = null;
		String gdVal = "";
		int seq = 1;
		for(T tTemp:list){
			m = tTemp.getClass().getMethod(GOODSIDM);
			if(null != tTemp){
				gdVal = (String)m.invoke(tTemp);
				if(null != gdVal && !"".equals(gdVal)){
					tTemp.getClass().getMethod("setSequence", Integer.class).invoke(tTemp, seq);
					seq++;
					reList.add(tTemp);
				}
			}
		}
		return reList;
	}
	public static <T>List<T> filterSepList(List<T> list,String getMethod) throws Exception{
		List<T> reList = new ArrayList<T>();
		Method m = null;
		String gdVal = "";
		int seq = 1;
		for(T tTemp:list){
			m = tTemp.getClass().getMethod(getMethod);
			if(null != tTemp){
				gdVal = (String)m.invoke(tTemp);
				if(null != gdVal && !"".equals(gdVal)){
					tTemp.getClass().getMethod("setSequence", Integer.class).invoke(tTemp, seq);
					seq++;
					reList.add(tTemp);
				}
			}
		}
		return reList;
	}
	
	//List<Map<String, String>> TO Map<String, String>
	public static Map<String, String> chglistToMap(List<Map<String, String>> list, String... args) throws Exception{
		Map<String, String> maps = new HashMap<String, String>();
		String key = "GOODS_ID";
		String val = "GOODS_NAME";
		int i = 1;
		for(String str:args){
			if(i == 1){
				key = str;
				i++;
			}else{
				val = str;
			}
		}
		for(Map<String, String> map:list){
			maps.put(map.get(key), map.get(val));
		}
		
		return maps;
	}
	/** 
     * set属性的值到Bean 
     *  
     * @param bean 
     * @param valMap 
     */  
	public static void setFieldValue(Object bean, Map<String, String> valMap) {
        Class<?> cls = bean.getClass();  
        // 取出bean里的所有方法  
        Method[] methods = cls.getDeclaredMethods();  
        Field[] fields = cls.getDeclaredFields();  
  
        for (Field field : fields) {  
            try {  
                String fieldSetName = parSetName(field.getName());  
                if (!checkSetMet(methods, fieldSetName)) {  
                    continue;  
                }  
                Method fieldSetMet = cls.getMethod(fieldSetName,  
                        field.getType());  
//              String fieldKeyName = parKeyName(field.getName());  
                String  fieldKeyName = field.getName();  
                String value = valMap.get(fieldKeyName);  
                if (null != value && !"".equals(value)) {
                    String fieldType = field.getType().getSimpleName();  
                    if ("String".equals(fieldType)) {  
                        fieldSetMet.invoke(bean, value);  
                    } else if ("Date".equals(fieldType)) {  
                        Date temp = parseDate(value);  
                        fieldSetMet.invoke(bean, temp);  
                    } else if ("Integer".equals(fieldType)  
                            || "int".equals(fieldType)) {  
                        Integer intval = Integer.parseInt(value);  
                        fieldSetMet.invoke(bean, intval);  
                    } else if ("Long".equalsIgnoreCase(fieldType)) {  
                        Long temp = Long.parseLong(value);  
                        fieldSetMet.invoke(bean, temp);  
                    } else if ("Double".equalsIgnoreCase(fieldType)) {  
                        Double temp = Double.parseDouble(value);  
                        fieldSetMet.invoke(bean, temp);  
                    } else if ("Boolean".equalsIgnoreCase(fieldType)) {  
                        Boolean temp = Boolean.parseBoolean(value);  
                        fieldSetMet.invoke(bean, temp);  
                    } else {  
                        System.out.println("not supper type" + fieldType);  
                    }
                }  
            } catch (Exception e) {  
                continue;  
            }  
        }  
    }
	
	 /** 
     * 拼接在某属性的 set方法 
     *  
     * @param fieldName 
     * @return String 
     */  
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }
        return "set"  
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()  
                + fieldName.substring(startIndex + 1);  
    }
    
    /** 
     * 判断是否存在某属性的 set方法 
     *  
     * @param methods 
     * @param fieldSetMet 
     * @return boolean 
     */  
    private static boolean checkSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {  
            if (fieldSetMet.equals(met.getName())) {  
                return true;  
            }  
        }  
        return false;  
    }
    
    /** 
     * 格式化string为Date 
     *  
     * @param datestr 
     * @return date 
     */  
    private static Date parseDate(String datestr) {
        if (null == datestr || "".equals(datestr)) {  
            return null;  
        }  
        try {  
            String fmtstr = null;  
            if (datestr.indexOf(':') > 0) {  
                fmtstr = "yyyy-MM-dd HH:mm:ss";  
            } else {  
                fmtstr = "yyyy-MM-dd";  
            }  
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr);  
            return sdf.parse(datestr);  
        } catch (Exception e) {  
            return null;  
        }  
    }
    
    /**
     * 单个对象的某个键的值
     * @param object 对象
     * @param key 键
     * @return Object 键在对象中所对应得值 没有查到时返回空字符串
     */
    public static Object getValueByKey(Object obj, String key) {
    	// 得到类对象
    	Class userCla = (Class) obj.getClass();
    	/* 得到类中的所有属性集合 */
    	Field[] fs = userCla.getDeclaredFields();
    	for (int i = 0; i < fs.length; i++) {
    		Field f = fs[i];
    		f.setAccessible(true); // 设置些属性是可以访问的
    		try {
    			if (f.getName().endsWith(key)) {
    				System.out.println("单个对象的某个键的值==反射==" + f.get(obj));
    				return f.get(obj);
    			}
    		} catch (IllegalArgumentException e) {
    			e.printStackTrace();
    		} catch (IllegalAccessException e) {
    			e.printStackTrace();
    		}
    	}
    	// 没有查到时返回空字符串
    	return "";
    }
    
}
