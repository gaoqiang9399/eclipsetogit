/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PublicUtil.java
 * 包名： app.component.finance.util
 * 说明：
 * @author Javelin
 * @date 2017-1-3 上午11:37:36
 * @version V1.0
 */ 
package app.component.finance.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import app.component.finance.paramset.entity.CwCycleHst;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 类名： PublicUtil
 * 描述：该公共类 不涉及事务，涉及事务调其他公共类
 * @author Javelin
 * @date 2017-1-3 上午11:37:36
 */
public class CwPublicUtil {

	private static final GsonBuilder gsonBuilder = new GsonBuilder();
	public static final Gson gson = gsonBuilder.create();
	private static Pattern pattern = Pattern.compile("[1-9][0-9][0-9][0-9]");
	
	
	/**
	 * 方法描述： 获取财务帐套标识
	 * @param httpservletrequest
	 * @return
	 * String
	 * @author Javelin
	 * @date 2017-7-19 上午11:06:45
	 */
	public static String getFinBook() {
		String book = null;
//		if (request != null) {
//			book = (String) request.getSession().getAttribute("finBooks");
//		}
		return book == null ? CwCacheUtil.CW_BOOKS : book.trim();
	}
	/**
	 * 方法描述： 将JSON字符串转换成MAP
	 * @param ajaxData
	 * @return
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-1-3 上午11:38:10
	 */
	public static Map<String, String> getMapByJson(String ajaxData){
		Map<String, String> map = new HashMap<String,String>();
		JSONArray ja = JSONArray.fromObject(ajaxData);
		for(Object obj:ja){
			JSONObject jb = JSONObject.fromObject(obj);
			String key = jb.getString("name");
			String value = jb.getString("value");
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * 方法描述： 截取字段到指定长度，超出的部分使用...
	 * @param parmStr
	 * @param lenth
	 * @return
	 * String
	 * @author Javelin
	 * @date 2017-1-4 上午11:24:17
	 */
	public static String cutStrToLenth(String parmStr, int lenth){
		String toStr = "";
		if (parmStr.length() > lenth) {
			toStr = parmStr.substring(0, lenth) + "...";
		}else {
			toStr = parmStr;
		}
		return toStr;
	}
	
	/**
	 * 方法描述： 格式化金额， 为0返回空
	 * @param money
	 * @return
	 * String
	 * @author Javelin
	 * @date 2017-4-1 下午3:43:37
	 */
	public static String moneyStr(String money){
		return MathExtend.comparison(money, "0") != 0 ? MathExtend.moneyStr(money) : "";
	}
	
	/**
	 * 方法描述： 根据会计期间实体 组装周期字符串数据
	 * @param cwCycleHst
	 * @return
	 * String
	 * @author Javelin
	 * @date 2017-1-5 上午10:58:14
	 */
	public static String getCwCycleHstForWeek(CwCycleHst cwCycleHst){
		if (cwCycleHst == null) {
			return "";
		}
		String week = cwCycleHst.getYears();
		if (cwCycleHst.getNums().length() > 1) {
			week = week + cwCycleHst.getNums();
		}else {
			week = week + "0" + cwCycleHst.getNums();
		}
		return week;
	}
	/**
	 * 
	 * 方法描述： 根据字符串判断是否为纯数字
	 * @param str
	 * @return
	 * boolean
	 * @author 刘争帅
	 * @date 2017-1-10 下午4:21:55
	 */
	public static boolean isNumber(String str) {
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 周期增加或减少一定周期数
	 * @param week 原周期
	 * @param addMon 减少为负
	 * @return
	 * @throws ParseException
	 */
	public static String weekAdd(String week,int addMon) throws ParseException{
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM", Locale.US);
		 Date date = simpleDateFormat.parse(week);
		 Calendar calender = Calendar.getInstance();
		 calender.setTime(date);
		 calender.add(Calendar.MONTH, addMon);
		 return simpleDateFormat.format(calender.getTime());
	}
	/**
	 * 日期加减天数
	 * @param date yyyyMMDD
	 * @param addDay int
	 * @return
	 * @throws ParseException
	 */
	public static String dateAdd(String date,int addDay) throws ParseException{
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMDD", Locale.US);
		 Date dt = simpleDateFormat.parse(date);
		 Calendar calender = Calendar.getInstance();
		 calender.setTime(dt);
		 calender.add(Calendar.DAY_OF_YEAR, addDay);
		 return simpleDateFormat.format(calender.getTime());
	}
	
	/**
	 * 方法描述： 根据科目号级别以及格式类型 加长科目号
	 * @param oldAccType	已设置科目规则编号
	 * @param newAccType	新选择
	 * @param oldAccno	科目编号
	 * @param level	科目级别
	 * @return
	 * String
	 * @author Javelin
	 * @date 2017-2-20 下午3:43:37
	 */
	public static String getNewAccno(String oldAccType, String newAccType, String oldAccno, String level) {
		String accNewno = "";
		int oldType = Integer.valueOf(oldAccType);//科目已有级别
		int newType = Integer.valueOf(newAccType);//新选级别
		int lvl = Integer.valueOf(level); //科目级别
		int diff = newType - oldType;
		if (lvl == 1 || diff == 0) {
			return oldAccno;
		}else {
			String zeroStr = "";//添加长度
			for (int i = 0; i < diff; i++) {
				zeroStr += "0";
			}
			StringBuffer strb = new StringBuffer();
			strb.append(oldAccno.subSequence(0, 4));
			for (int i = 1; i < lvl; i++) {
				strb.append(zeroStr).append(oldAccno.subSequence((4 + (i - 1) * (2 + oldType)), (4 + i * (2 + oldType))));
			}
			accNewno = strb.toString();
		}
		return accNewno;
	}
	
	/**
	 * 方法描述： json字符串转map
	 * @param json
	 * @return
	 * Map
	 * @author Javelin
	 * @date 2017-3-18 下午2:55:53
	 */
	public static Map toMap(String json) {
		return gson.fromJson(json, new TypeToken<Map>() {
		}.getType());
	}

	 /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException
     *             如果分析类属性失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InstantiationException
     *             如果实例化 JavaBean 失败
     * @throws InvocationTargetException
     *             如果调用属性的 setter 方法失败
     */
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
    
    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName) && !"startNumAndEndNum".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    
    /**
     * 方法描述： 将一个 JavaBean 对象转化为一个  Map 为null字段不添加
     * @param bean
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * Map
     * @author Javelin
     * @date 2016-8-16 上午10:01:31
     */
    public static Map convertBeanKillNull(Object bean)
    		throws IntrospectionException, IllegalAccessException, InvocationTargetException {
    	Class type = bean.getClass();
    	Map returnMap = new HashMap();
    	BeanInfo beanInfo = Introspector.getBeanInfo(type);
    	
    	PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
    	for (int i = 0; i< propertyDescriptors.length; i++) {
    		PropertyDescriptor descriptor = propertyDescriptors[i];
    		String propertyName = descriptor.getName();
    		if (!"class".equals(propertyName)) {
    			Method readMethod = descriptor.getReadMethod();
    			Object result = readMethod.invoke(bean, new Object[0]);
    			if (result != null) {
    				returnMap.put(propertyName, result);
    			}
    		}
    	}
    	return returnMap;
    }
    /**
     * 将辅助核算字符串生成科目名称后缀 如:_张三_管理员
     */
    public static String getComSuffixByItem(String itemStr){
    	String resultStr="";
    	if(StringUtil.isNotEmpty(itemStr)){
			String[] itemArr=itemStr.split("\\|");
			for(int i=0;i<itemArr.length;i++){
				String[] item=itemArr[i].split("@");
				resultStr+="_"+item[3];
			}
		}
    	return resultStr;
    }
}
