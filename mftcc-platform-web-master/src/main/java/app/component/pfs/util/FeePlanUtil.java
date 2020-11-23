package app.component.pfs.util;


import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mftcc.util.StringUtil;


/**
 *费用计划工具类
 * 
 */
public class FeePlanUtil {
	private static Logger logger = LoggerFactory.getLogger(FeePlanUtil.class);
	/**
	 * 方法描述： 由于费用的可变性，拼接 <strong>动态方法名</strong> 通过反射来取值。
	 * @param object 从中取值的对象实例。
	 * @param methodName 完整的方法名，例如：getMoney1 / getAmonut2
	 * @return
	 * String
	 * @author liuyf
	 * @throws Exception 
	 * @date 2015-5-15 上午11:15:09
	 */
	public static Double getFeeFieldByInvoke(Object object, String methodName) throws Exception {
		Double result = 0.00;
		try {
			Method method = object.getClass().getDeclaredMethod(methodName);
			result = (Double) method.invoke(object);
		} catch (Exception e) {
			logger.error("通过反射获取费用相关的值时出错！Class："+object.getClass()+"，方法："+methodName, e);
			throw e;
		}
		return result;
	}
	/**
	 * 方法描述： 由于费用的可变性，拼接 <strong>动态方法名</strong> 通过反射来设置字段值。
	 * @param object 从中取值的对象实例。
	 * @param methodName 完整的方法名，例如：getMoney1 / getAmonut2
	 * @param setParam 被set的值。
	 * @return
	 * String
	 * @author liuyf
	 * @throws Exception 
	 * @date 2015-5-15 上午11:15:09
	 */
	public static String setFeeFieldByInvoke(Object object, String methodName, Double setParam) throws Exception {
		String result = "";
		try {
			Method method = object.getClass().getDeclaredMethod(methodName, Double.class);
			result = StringUtil.KillNull((String) method.invoke(object, setParam));
		} catch (Exception e) {
			logger.error("通过反射set费用相关的字段值时出错！Class："+object.getClass()+"，方法："+methodName+"，参数："+setParam, e);
			throw e;
		}
		return result;
	}

}
