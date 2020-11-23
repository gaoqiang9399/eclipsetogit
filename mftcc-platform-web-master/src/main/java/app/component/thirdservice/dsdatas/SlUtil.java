/**
 * Copyright (C) DXHM 版权所有
 * 文件名： SlUtil.java
 * 包名： app.component.thirdservice.dsdatas
 * 说明：
 * @author wzw
 * @date 2017-11-17 下午3:46:22
 * @version V1.0
 */ 
package app.component.thirdservice.dsdatas;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： SlUtil
 * 描述：索伦第三方
 * @author wzw
 * @date 2017-11-17 下午3:46:22
 *
 *
 */
public class SlUtil {

	/**
	 * 公司帐号
	 */
	public static final String companyAccount = "dhxd_SAURON";
	
	/**
	 * 公司签名
	 */
	public static final String signature = "874afc739b2947c380e2ac8378632704";
	
	/**
	 * 获取数据令牌access_token接口
	 */
	public static String url_access_token = "https://data.hulushuju.com/api/companies/{companyAccount}/access_token?signature={signature}";
	
	/**
	 * 根据姓名手机号身份证获取索伦数据接口
	 */
	public static String url_sauron = "https://ad.hulushuju.com/api/sauron?name={name}&phone={phone}&idCard={idCard}&companyAccount={companyAccount}&accessToken={accessToken}";
	
	/**
	 * 获取令牌成功标识
	 */
	public static final String CODE_TOKEN_SUCCESS = "0";
	/**
	 * 获取索伦数据成功标识
	 */
	public static final String CODE_SL_SUCCESS = "17153";
	
	/**
	 * 索伦错误信息
	 */
	public static final Map<String,String> ErrMap = new HashMap<String,String>(){{
		put("4352","公司帐号不存在");
		put("4353","公司帐号已锁定");
		put("4354","账号不支持认证此产品!");
		put("4355","公司签名错误");
		put("4357","获取机构访问数据令牌异常");
		put("16896","获取数据的令牌不存在或已失效");
		put("17152","获取数据异常");
		put("17153","获取数据成功");
		put("17154","身份证号码错误");
		put("17155","姓名错误");
		put("17156","手机号码错误");
	}};
	
}
