package app.component.interfaces.appinterface.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.component.interfaces.appinterface.feign.DingInterfaceFeign;
import app.component.sys.entity.SysUser;

@Controller
@RequestMapping("/dingInterface")
public class DingInterfaceController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DingInterfaceFeign dingInterfaceFeign;

	/**
	 * 功能：获得js
	 * 
	 * @param htmlUrl
	 */
	@RequestMapping(value = "/getDingJSConfigAjax")
	@ResponseBody
	public Map<String, Object> getDingJSConfigAjax(String htmlUrl) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String url = URLDecoder.decode(htmlUrl, "utf-8");
			Map<String, String> resultMap = dingInterfaceFeign.getDingJSConfig(url);
			dataMap.put("data", resultMap);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取Dingding js-sdk相关的配置失败！");
//			logger.error("获取微信js-sdk需要的相关的配置失败", e);
		}
		return dataMap;
	}

	/**
	 * 功能：获得操作员列表
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserInfoAjax")
	@ResponseBody
	public Map<String, Object> getUserInfoAjax(String code) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysUser user = new SysUser();
		Gson gson = new Gson();
		try {
			user = dingInterfaceFeign.getUserInfo(code);
			String userJson = gson.toJson(user);
			dataMap.put("data", userJson);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获得用户通讯录信息失败");
//			logger.error("获得用户通讯录信息失败", e);
		}
		return dataMap;
	}

	@RequestMapping(value = "/toDingLogin")
	public String toDingLogin(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/interfaces/appinterface/DingInterfaceAction_toDingLogin";
	}

}
