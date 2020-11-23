package app.component.interfaces.mobileinterface.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.interfaces.mobileinterface.entity.MfAccessInfo;
import app.component.interfaces.mobileinterface.entity.VmCifCustomerDTO;
import app.component.interfaces.mobileinterface.entity.WebCusLineReg;
import app.component.interfaces.mobileinterface.feign.WebCusLineRegFeign;
import app.component.interfaces.mobileinterface.util.RandomNumUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;

/**
 * Title: WebCusLineRegAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon May 22 15:30:35 CST 2017
 **/
@Controller
@RequestMapping("/webCusLineReg")
public class WebCusLineRegController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入WebCusLineRegBo
	@Autowired
	private WebCusLineRegFeign webCusLineRegFeign;

	private static Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号

//	// 全局变量
//	/**
//	 * 获取发送手机验证码
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/getPhoneVerifyAjax")
//	@ResponseBody
//	public Map<String, Object> getPhoneVerifyAjax(String tel, String random, String msgSender, int ik)
//			throws Exception {
//
//		String referer = request.getHeader("Referer");
//		String UserAgent = request.getHeader("User-Agent");
//		String Cookie = request.getHeader("Cookie");
//		String Accept = request.getHeader("Accept");
//		String ip = getIpAddress(request);
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		WebCusLineReg webCusLineReg = new WebCusLineReg();
//
//		String _ttel = StringUtil.KillNull(tel);// 手机号非空处理
//		if (!isMobile(_ttel)) {// 手机号校验
//			return dataMap;
//		}
//		// 图片验证码校验
//		if (null != random) {
//			if (!request.getSession().getAttribute("random").equals(random)) {
//				dataMap.put("errorCode", "99999");
//				dataMap.put("errorMsg", "请输入正确的图片数字");
//				return dataMap;
//			}
//		}
//
//		/********** IP策略 ************/
//		try {
//			if (CacheUtil.getMapByKey(CACHE_KEY.ipMap).containsKey(StringUtil.KillNull(ip))) {
//				String[] arry = (String[]) CacheUtil.getMapByKey(CACHE_KEY.ipMap).get(ip);
//				long hisTime = Long.parseLong(arry[1]);
//				long currTime = System.currentTimeMillis();
//
//				if ((currTime - hisTime) / 1000 / 60 > 15) {// 超过15分钟，重置运行发送
//					CacheUtil.putMapkeyInCache(ip, new String[] { "1", System.currentTimeMillis() + "" },
//							CACHE_KEY.ipMap);
//				}
//
//				if ((currTime - hisTime) / 1000 / 60 <= 10 && Integer.valueOf(arry[0]) >= 3) {// 10分钟内发3次
//					dataMap.put("errorCode", "11111");
//					dataMap.put("errorMsg", "短时间内尝试过多，请稍后再试。");
//					arry[0] = MathExtend.add(arry[0], "1");// 计数器+1
//					arry[1] = System.currentTimeMillis() + "";// 最后发送时间重置
//					CacheUtil.putMapkeyInCache(ip, arry, CACHE_KEY.ipMap);
//					return dataMap;
//				} else {
//					arry[0] = MathExtend.add(arry[0], "1");// 计数器+1
//					// _ipmap.put(ip, arry);
//					CacheUtil.putMapkeyInCache(ip, arry, CACHE_KEY.ipMap);
//				}
//
//			} else {// 重置发送情况
//				CacheUtil.putMapkeyInCache(ip, new String[] { "1", System.currentTimeMillis() + "" }, CACHE_KEY.ipMap);
//			}
//		} catch (Exception e) {
//			// logger\.error("添加短信短IP时间内发送失败：" + e.getMessage(), e);
//		}
//		/********** IP策略 ************/
//
//		/********** 手机号策略 ************/
//		try {
//			try {
//				if (CacheUtil.getMapByKey(CACHE_KEY.phoneMap).containsKey(StringUtil.KillNull(_ttel))) {
//					String[] arry = (String[]) CacheUtil.getMapByKey(CACHE_KEY.phoneMap).get(_ttel);
//					long hisTime = Long.parseLong(arry[1]);
//					long currTime = System.currentTimeMillis();
//
//					if ((currTime - hisTime) / 1000 / 60 > 15) {// 超过15分钟，重置运行发送
//						// arry[0]=MathExtend.add(arry[0], "1");// 计数器+1
//						// arry[1]=System.currentTimeMillis()+"";
//						// phonemap.put(_ttel, new
//						// String[]{"1",System.currentTimeMillis()+""});
//						CacheUtil.putMapkeyInCache(_ttel, new String[] { "1", System.currentTimeMillis() + "" },
//								CACHE_KEY.phoneMap);
//					}
//
//					if ((currTime - hisTime) / 1000 / 60 <= 10 && Integer.valueOf(arry[0]) >= 3) {// 10分钟内发3次
//						dataMap.put("errorCode", "11111");
//						dataMap.put("errorMsg", "短时间内尝试过多，请稍后再试。");
//						arry[0] = MathExtend.add(arry[0], "1");// 计数器+1
//						arry[1] = System.currentTimeMillis() + "";// 最后发送时间重置
//						// phonemap.put(_ttel, arry);
//						CacheUtil.putMapkeyInCache(_ttel, arry, CACHE_KEY.phoneMap);
//						return dataMap;
//					} else {
//						arry[0] = MathExtend.add(arry[0], "1");// 计数器+1
//						// phonemap.put(_ttel, arry);
//						CacheUtil.putMapkeyInCache(_ttel, arry, CACHE_KEY.phoneMap);
//					}
//
//				} else {// 重置发送情况
//					CacheUtil.putMapkeyInCache(_ttel, new String[] { "1", System.currentTimeMillis() + "" },
//							CACHE_KEY.phoneMap);
//				}
//			} catch (Exception e) {
//				// logger\.error("添加短信手机号短时间内发送失败：" + e.getMessage(), e);
//			}
//
//			/********** 手机号策略 ************/
//
//			if (StringUtil.isNotEmpty(referer)) {// PC
//				if (referer.contains("wechatDesign/pages/share.jsp")
//						|| referer.contains("wechatDesign/pages/Dhshare.jsp")) {
//					webCusLineReg.setCusTel(tel);
//					webCusLineReg.setMsgSender(msgSender);
//					// 调用Bo层发短信服务
//					dataMap = webCusLineRegFeign.sendMessage(webCusLineReg);
//				} else {
//					dataMap.put("errorCode", "11111");
//					dataMap.put("errorMsg", "验证码发送非法");
//					return dataMap;
//				}
//
//			}
//			if (StringUtil.isEmpty(referer)) {// 手机
//				// 判断手机端访问来源
//				if ((UserAgent.contains("Android") || UserAgent.contains("iPhone"))
//						&& (UserAgent.contains("Html5Plus") && UserAgent.contains("Mobile"))
//						&& Accept.contains("application/json") && Accept.contains("text/javascript")
//						&& Accept.contains("q=0.01")) {
//					webCusLineReg.setCusTel(tel);
//					webCusLineReg.setMsgSender(msgSender);
//					// 调用Bo层发短信服务
//					dataMap = webCusLineRegFeign.sendMessage(webCusLineReg);
//				} else {
//					dataMap.put("errorCode", "11111");
//					dataMap.put("errorMsg", "验证码发送非法");
//				}
//			}
//			/******* 监控日志处理，不打印太多日志 start ***********/
//			try {
//
//				if (ik > 10) {// 10次正常短信发送打印1次日志，为了监控如果有可疑情况可以查看客户端请求头部报文
//					// logger\.error("ip:" + ip + "tel:" + tel + " Referer:" +
//					// referer + " User-Agent:" + UserAgent
//					// + " Cookie:" + Cookie + " Accept:" + Accept);
//					ik = 0;
//				}
//				ik++;
//			} catch (Exception e) {
//			}
//			/******* 监控日志处理，不打印太多日志 end ***********/
//		} catch (Exception e) {
//			// logger\.error("验证码发送异常", e);
//			dataMap.put("errorCode", "11111");
//			dataMap.put("errorMsg", "验证码发送异常");
//		}
//		return dataMap;
//	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 * 
	 * 用户真实IP为： 192.168.1.110
	 * 
	 * @param request
	 * @return
	 */
	private static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 手机号验证
	 * 
	 * @author ：shijing 2016年12月5日下午4:34:46
	 * @param str
	 * @return 验证通过返回true
	 */
	private static boolean isMobile(final String str) {
		Matcher m = null;
		boolean b = false;
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
//
//	/**
//	 * 获取发送手机验证码(网信登录注册调用三方发送验证码)
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/getWxPhoneVerifyAjax")
//	@ResponseBody
//	public Map<String, Object> getWxPhoneVerifyAjax(String tel, String random, String msgSender, int ik)
//			throws Exception {
//		String referer = request.getHeader("Referer");
//		String UserAgent = request.getHeader("User-Agent");
//		String Cookie = request.getHeader("Cookie");
//		String Accept = request.getHeader("Accept");
//		String ip = getIpAddress(request);
//		// //logger\.error("ip:"+ip+" Referer:"+referer+"
//		// User-Agent:"+UserAgent+"
//		// Cookie:"+Cookie+" Accept:"+Accept);
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		WebCusLineReg webCusLineReg = new WebCusLineReg();
//
//		String _ttel = StringUtil.KillNull(tel);// 手机号非空处理
//		if (!isMobile(_ttel)) {// 手机号校验
//			return dataMap;
//		}
//		// 图片验证码校验
//		if (null != random) {
//			if (request == null || request.getSession() == null
//					|| request.getSession().getAttribute("random") == null) {
//				dataMap.put("errorCode", "99999");
//				dataMap.put("errorMsg", "请输入正确的图片数字");
//				return dataMap;
//			}
//			if (!request.getSession().getAttribute("random").equals(random)) {
//				dataMap.put("errorCode", "99999");
//				dataMap.put("errorMsg", "请输入正确的图片数字");
//				return dataMap;
//			}
//		}
//
//		/********** IP策略 ************/
//		try {
//			if (CacheUtil.getMapByKey(CACHE_KEY.ipMap).containsKey(StringUtil.KillNull(ip))) {
//				String[] arry = (String[]) CacheUtil.getMapByKey(CACHE_KEY.ipMap).get(ip);
//				long hisTime = Long.parseLong(arry[1]);
//				long currTime = System.currentTimeMillis();
//
//				if ((currTime - hisTime) / 1000 / 60 > 15) {// 超过15分钟，重置运行发送
//					CacheUtil.putMapkeyInCache(ip, new String[] { "1", System.currentTimeMillis() + "" },
//							CACHE_KEY.ipMap);
//				}
//
//				if ((currTime - hisTime) / 1000 / 60 <= 10 && Integer.valueOf(arry[0]) >= 3) {// 10分钟内发3次
//					dataMap.put("errorCode", "11111");
//					dataMap.put("errorMsg", "短时间内尝试过多，请稍后再试。");
//					arry[0] = MathExtend.add(arry[0], "1");// 计数器+1
//					arry[1] = System.currentTimeMillis() + "";// 最后发送时间重置
//					CacheUtil.putMapkeyInCache(ip, arry, CACHE_KEY.ipMap);
//					return dataMap;
//				} else {
//					arry[0] = MathExtend.add(arry[0], "1");// 计数器+1
//					CacheUtil.putMapkeyInCache(ip, arry, CACHE_KEY.ipMap);
//				}
//
//			} else {// 重置发送情况
//				CacheUtil.putMapkeyInCache(ip, new String[] { "1", System.currentTimeMillis() + "" }, CACHE_KEY.ipMap);
//			}
//		} catch (Exception e) {
//			// logger\.error("添加短信短IP时间内发送失败：" + e.getMessage(), e);
//		}
//		/********** IP策略 ************/
//
//		/********** 手机号策略 ************/
//		try {
//			try {
//				if (CacheUtil.getMapByKey(CACHE_KEY.phoneMap).containsKey(StringUtil.KillNull(_ttel))) {
//					String[] arry = (String[]) CacheUtil.getMapByKey(CACHE_KEY.phoneMap).get(_ttel);
//					long hisTime = Long.parseLong(arry[1]);
//					long currTime = System.currentTimeMillis();
//
//					if ((currTime - hisTime) / 1000 / 60 > 15) {// 超过15分钟，重置运行发送
//						CacheUtil.putMapkeyInCache(_ttel, new String[] { "1", System.currentTimeMillis() + "" },
//								CACHE_KEY.phoneMap);
//					}
//
//					if ((currTime - hisTime) / 1000 / 60 <= 10 && Integer.valueOf(arry[0]) >= 3) {// 10分钟内发3次
//						dataMap.put("errorCode", "11111");
//						dataMap.put("errorMsg", "短时间内尝试过多，请稍后再试。");
//						arry[0] = MathExtend.add(arry[0], "1");// 计数器+1
//						arry[1] = System.currentTimeMillis() + "";// 最后发送时间重置
//						CacheUtil.putMapkeyInCache(_ttel, arry, CACHE_KEY.phoneMap);
//						return dataMap;
//					} else {
//						arry[0] = MathExtend.add(arry[0], "1");// 计数器+1
//						CacheUtil.putMapkeyInCache(_ttel, arry, CACHE_KEY.phoneMap);
//					}
//
//				} else {// 重置发送情况
//					// phonemap.put(_ttel, new
//					// String[]{"1",System.currentTimeMillis()+""});
//					CacheUtil.putMapkeyInCache(_ttel, new String[] { "1", System.currentTimeMillis() + "" },
//							CACHE_KEY.phoneMap);
//				}
//			} catch (Exception e) {
//				// logger\.error("添加短信手机号短时间内发送失败：" + e.getMessage(), e);
//			}
//
//			/********** 手机号策略 ************/
//
//			if (StringUtil.isNotEmpty(referer)) {// PC
//				if (referer.contains("TcReportAction_getSharePage.action")) {
//					webCusLineReg.setCusTel(tel);
//					webCusLineReg.setMsgSender(msgSender);
//					// 调用Bo层发短信服务
//					dataMap = webCusLineRegFeign.sendWxMessage(webCusLineReg);
//				} else {
//					dataMap.put("errorCode", "11111");
//					dataMap.put("errorMsg", "验证码发送非法");
//					return dataMap;
//				}
//
//			}
//			if (StringUtil.isEmpty(referer)) {// 手机
//				// 判断手机端访问来源
//				if ((UserAgent.contains("Android") || UserAgent.contains("iPhone"))
//						&& (UserAgent.contains("Html5Plus") && UserAgent.contains("Mobile"))
//						&& Accept.contains("application/json") && Accept.contains("text/javascript")
//						&& Accept.contains("q=0.01")) {
//					webCusLineReg.setCusTel(tel);
//					webCusLineReg.setMsgSender(msgSender);
//					// 调用Bo层发短信服务
//					dataMap = webCusLineRegFeign.sendWxMessage(webCusLineReg);
//				} else {
//					dataMap.put("errorCode", "11111");
//					dataMap.put("errorMsg", "验证码发送非法");
//				}
//			}
//			/******* 监控日志处理，不打印太多日志 start ***********/
//			try {
//				if (ik > 10) {// 10次正常短信发送打印1次日志，为了监控如果有可疑情况可以查看客户端请求头部报文
//					// logger\.error("ip:" + ip + "tel:" + tel + " Referer:" +
//					// referer + " User-Agent:" + UserAgent
//					// + " Cookie:" + Cookie + " Accept:" + Accept);
//					ik = 0;
//				}
//				ik++;
//			} catch (Exception e) {
//			}
//			/******* 监控日志处理，不打印太多日志 end ***********/
//		} catch (Exception e) {
//			// logger\.error("验证码发送异常", e);
//			dataMap.put("errorCode", "11111");
//			dataMap.put("errorMsg", "验证码发送异常");
//		}
//		return dataMap;
//	}

	/**
	 * 获取发送手机验证码(网信登录注册调用三方发送验证码:用于测试)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWxPhoneVerifyForTestAjax")
	@ResponseBody
	public Map<String, Object> getWxPhoneVerifyForTestAjax(String tel, String msgSender) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WebCusLineReg webCusLineReg = new WebCusLineReg();
		try {
			webCusLineReg.setCusTel(tel);
			webCusLineReg.setMsgSender(msgSender);
			// 调用Bo层发短信服务
			dataMap = webCusLineRegFeign.sendWxMessage(webCusLineReg);
		} catch (Exception e) {
			// logger\.error("验证码发送异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "验证码发送异常");
		}
		return dataMap;
	}

	/**
	 * 获取发送手机验证码(网信修改手机号发送验证码)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWxUpdatePhoneVerifyAjax")
	@ResponseBody
	public Map<String, Object> getWxUpdatePhoneVerifyAjax(String tel, String msgSender) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WebCusLineReg webCusLineReg = new WebCusLineReg();
		try {
			webCusLineReg.setCusTel(tel);
			webCusLineReg.setMsgSender(msgSender);
			// 调用Bo层发短信服务
			dataMap = webCusLineRegFeign.sendWxUpdateMessage(webCusLineReg);
		} catch (Exception e) {
			// logger\.error("验证码发送异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "验证码发送异常");
		}
		return dataMap;
	}

	/**
	 * validate验证码方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateVerifyAjax")
	@ResponseBody
	public Map<String, Object> validateVerifyAjax(String tel, String msgSender, String verifyNum) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WebCusLineReg webCusLineReg = new WebCusLineReg();
		try {
			webCusLineReg.setCusTel(tel);
			webCusLineReg.setMsgSender(msgSender);
			// 调用Bo层发短信服务
			dataMap = webCusLineRegFeign.validateVerifyCode(webCusLineReg, verifyNum);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "验证码验证异常");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 登陆注册 用户第一次请求时先注册再登陆 微信openid 和 tel都存在时 执行微信绑定操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginAjax")
	@ResponseBody
	public Map<String, Object> loginAjax(String openid, String tel, String qqOpenid, String wxOpenid, String cusNo,
			String osType, String channelSourceNo, String verifyNum, String type) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WebCusLineReg webCusLineReg = new WebCusLineReg();
		try {
			webCusLineReg.setOpenid(openid);
			webCusLineReg.setCusTel(tel);
			webCusLineReg.setAppQqOpenid(qqOpenid);
			webCusLineReg.setAppWxOpenid(wxOpenid);
			webCusLineReg.setCusNo(cusNo);
			webCusLineReg.setClientType(osType);
			if (StringUtil.isEmpty(channelSourceNo)) {
				// 调用Bo方法获取返回结果
				dataMap = webCusLineRegFeign.doLogin(webCusLineReg, type, verifyNum, request);
			} else {
				// 调用Bo方法获取返回结果
				webCusLineReg.setChannelSourceNo(channelSourceNo);
				dataMap = webCusLineRegFeign.doLoginNew(webCusLineReg, type, verifyNum, request);

			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "登陆异常");
			// logger\.error("登陆异常", e);
		}
		return dataMap;
	}

	/**
	 * 插入用户设备信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertDeviceInfoAjax")
	@ResponseBody
	public Map<String, Object> insertDeviceInfoAjax(MfAccessInfo mfAccessInfo) throws Exception {
		String ip = getIpAddress(request);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfAccessInfo.setIpAddress(ip);
			dataMap = webCusLineRegFeign.insertDeviceInfo(mfAccessInfo);
		} catch (Exception e) {
			// logger\.error("插入用户设备信息失败", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "插入用户设备信息失败");
		}
		return dataMap;
	}

	/**
	 * 退出登录接口
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logoutAjax")
	@ResponseBody
	public Map<String, Object> logoutAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo方法获取返回结果
			dataMap = webCusLineRegFeign.doLogout(cusNo);
		} catch (Exception e) {
			// logger\.error("退出登陆异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "退出登陆异常");
		}
		return dataMap;
	}

	/**
	 * 登陆注册(网信接口) 用户第一次请求时先注册再登陆 微信openid 和 tel都存在时 执行微信绑定操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginForWxAjax")
	@ResponseBody
	public Map<String, Object> loginForWxAjax(String openid, String tel, String qqOpenid, String wxOpenid, String cusNo,
			String osType, String verifyNum, String type) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WebCusLineReg webCusLineReg = new WebCusLineReg();
		try {
			webCusLineReg.setOpenid(openid);
			webCusLineReg.setCusTel(tel);
			webCusLineReg.setAppQqOpenid(qqOpenid);
			webCusLineReg.setAppWxOpenid(wxOpenid);
			webCusLineReg.setCusNo(cusNo);
			webCusLineReg.setClientType(osType);
			// 调用Bo方法获取返回结果
			dataMap = webCusLineRegFeign.doLoginForWx(webCusLineReg, type, verifyNum, request);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "登陆异常");
			// logger\.error("登陆异常", e);
		}
		return dataMap;
	}

	/**
	 * 完善个人客户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/completeInfoAjax")
	@ResponseBody
	public Map<String, Object> completeInfoAjax(String verifyNum, VmCifCustomerDTO cifcus) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			// 调用Bo方法获取返回结果
			if (StringUtil.isEmpty(cifcus.getChannelSourceNo())) {
				dataMap = webCusLineRegFeign.doCompleteCustomerInfo2(cifcus, request, verifyNum);
			} else {
				dataMap = webCusLineRegFeign.doCompleteCustomerInfoNew(cifcus, request, verifyNum);
			}
		} catch (Exception e) {
			// logger\.error("移动端完善客户信息异常", e);
			if (e instanceof DuplicateKeyException) {// 插入表时主键冲突
				dataMap.put("errorMsg", "11111");// 已知异常
				dataMap.put("errorMsg", "新增客户编号冲突");
			} else {
				dataMap.put("errorCode", "11111");
				dataMap.put("errorMsg", "信息完善异常");
			}
		}
		return dataMap;
	}

	@RequestMapping(value = "/updateTelPhoneAjax")
	@ResponseBody
	public Map<String, Object> updateTelPhoneAjax(String tel, String channelSourceNo, String verifyNum, String newTel) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isEmpty(channelSourceNo)) {
				dataMap = webCusLineRegFeign.updateTelPhone(tel, newTel, verifyNum);
			} else {
				dataMap = webCusLineRegFeign.updateTelPhoneNew(channelSourceNo, tel, newTel, verifyNum);
			}

		} catch (Exception e) {
			// logger\.error("修改新的手机号码出错原号码为：" + tel + ",新号码：" + newTel, e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "修改新号码出错：" + e.getMessage());
		}
		return dataMap;
	}

	/**
	 * QQ或微信登录时绑定或注册
	 * 
	 * @return
	 */
	@RequestMapping(value = "/bindTelPhoneAjax")
	@ResponseBody
	public Map<String, Object> bindTelPhoneAjax(String openid, String tel, String osType, String channelSourceNo,
			String verifyNum, String type) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isEmpty(channelSourceNo)) {
				dataMap = webCusLineRegFeign.doBindTelPhone(tel, verifyNum, openid, type, request, osType);
			} else {
				dataMap = webCusLineRegFeign.doBindTelPhoneNew(tel, verifyNum, openid, type, osType, channelSourceNo,
						request);
			}

		} catch (Exception e) {
			// //logger\.error("绑定手机号失败");
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "绑定手机号失败：" + e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/updateTelPhoneForWxAjax")
	@ResponseBody
	public Map<String, Object> updateTelPhoneForWxAjax(String tel, String verifyNum, String newTel) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = webCusLineRegFeign.updateTelPhoneForWx(tel, newTel, verifyNum);
		} catch (Exception e) {
			// logger\.error("修改新的手机号码出错原号码为：" + tel + ",新号码：" + newTel, e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "修改新号码出错：" + e.getMessage());
		}
		return dataMap;
	}

	/**
	 * app分享登陆注册 引导客户下载app 用户第一次请求时先注册再登陆 引导客户下载app 用户有app时直接打开app登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shareLoginAjax")
	@ResponseBody
	public Map<String, Object> shareLoginAjax(String openid, String tel, String qqOpenid, String wxOpenid, String cusNo,
			String channelSourceNo, String verifyNum, String type, String cusName, String recomTel, String random)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WebCusLineReg webCusLineReg = new WebCusLineReg();
		try {
			webCusLineReg.setOpenid(openid);
			webCusLineReg.setCusTel(tel);
			webCusLineReg.setAppQqOpenid(qqOpenid);
			webCusLineReg.setAppWxOpenid(wxOpenid);
			webCusLineReg.setCusNo(cusNo);
			// 调用Bo方法获取返回结果
			if (StringUtil.isEmpty(channelSourceNo)) {
				dataMap = webCusLineRegFeign.doShareLogin(webCusLineReg, type, verifyNum, cusName, recomTel, random,
						request);
			} else {
				dataMap = webCusLineRegFeign.doShareLoginNew(webCusLineReg, channelSourceNo, type, verifyNum, cusName,
						recomTel, random, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "登陆异常");
			// logger\.error("登陆异常", e);
		}
		return dataMap;
	}

	/**
	 * 网信分享 按资金机构uid统计 app分享登陆注册 引导客户下载app 用户第一次请求时先注册再登陆 引导客户下载app
	 * 用户有app时直接打开app登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shareWxLoginAjax")
	@ResponseBody
	public Map<String, Object> shareWxLoginAjax(String openid, String tel, String qqOpenid, String wxOpenid,
			String cusNo, String cusName, String random, String verifyNum, String type, String agenciesUid)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WebCusLineReg webCusLineReg = new WebCusLineReg();
		try {
			webCusLineReg.setOpenid(openid);
			webCusLineReg.setCusTel(tel);
			webCusLineReg.setAppQqOpenid(qqOpenid);
			webCusLineReg.setAppWxOpenid(wxOpenid);
			webCusLineReg.setCusNo(cusNo);
			if (request == null || request.getSession() == null
					|| request.getSession().getAttribute("random") == null) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请输入正确的图片数字");
				return dataMap;
			}
			if (!request.getSession().getAttribute("random").equals(random)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请输入正确的图片数字");
				return dataMap;
			} else {
				request.getSession().removeAttribute("random");
			}
			// 调用Bo方法获取返回结果
			dataMap = webCusLineRegFeign.doWxShareLogin(webCusLineReg, type, verifyNum, cusName, agenciesUid, request);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "登陆异常");
			// logger\.error("登陆异常", e);
		}
		return dataMap;
	}

	/**
	 * 获取注册客户列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusListPage")
	public String getCusListPage(Model model, String dataType) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("dataType", dataType);
		return "/component/interfaces/mobileinterface/WebCusLineReg_List";
	}

	/**
	 * 获取注册客户列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WebCusLineReg webCusLineReg = new WebCusLineReg();
		try {
			webCusLineReg.setCustomQuery(ajaxData);// 自定义查询参数赋值
			webCusLineReg.setCriteriaList(webCusLineReg, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			String dataType = this.getHttpRequest().getParameter("dataType");
			if ("regCount".equals(dataType)) {// 注册人数列表
				webCusLineReg.setRegTime(DateUtil.getDate());
			} else if ("onlineCount".equals(dataType)) {// 在线人数列表
				webCusLineReg.setOnlineFlag("1");
			}else {
			}
			ipage = webCusLineRegFeign.findByPage(ipage, webCusLineReg);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 动态图片验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRandom")
	public String getRandom(Model model, String ajaxData) throws Exception {
		RandomNumUtil rdnu = RandomNumUtil.Instance();
		model.addAttribute("inputStream", rdnu.getImage());// 取得带有随机字符串的图片
		model.addAttribute("random", rdnu.getString());// 把验证码放入HttpSession中
		model.addAttribute("query", "");
		return "random";
	}

}
