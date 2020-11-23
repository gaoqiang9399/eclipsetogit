package app.component.interfaces.appinterface.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.BaseFormBean;

import app.component.interfaces.appinterface.feign.AppSysUserFeign;

/**
 * 用户登录信息管理的Action类
 * 
 * @author zhang_dlei
 * @date 2017-06-15 下午5:58:30
 */
@Controller
@RequestMapping("/appSysUser")
public class AppSysUserController extends BaseFormBean {
	@Autowired
	private AppSysUserFeign appSysUserFeign;
	// 异步参数

	// 操作员编号
	// 手机验证码
	// 联系人手机号码

	/**
	 * 获取发送手机验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPhoneVerifyAjax")
	@ResponseBody
	public Map<String, Object> getPhoneVerifyAjax(String mobile) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo层发短信服务
			dataMap = appSysUserFeign.sendMessage(mobile);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "验证码发送异常");
			// logger.error("验证码发送异常",e);
		}
		return dataMap;
	}

	/**
	 * 操作员登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginAjax")
	@ResponseBody
	public Map<String, Object> loginAjax(String mobile, String verifyNum) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo层登录服务
			dataMap = appSysUserFeign.login(mobile, verifyNum);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "登录异常");
//			logger.error("登录异常", e);
		}
		return dataMap;
	}

	/**
	 * 获取个人中心信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonalCenterInfoAjax")
	@ResponseBody
	public Map<String, Object> getPersonalCenterInfoAjax(String opNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			// 调用Bo层服务
			dataMap = appSysUserFeign.getPersonalCenterInfo(opNo);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取个人中心信息异常");
//			logger.error("获取个人中心信息异常", e);
		}
		return dataMap;
	}

}
