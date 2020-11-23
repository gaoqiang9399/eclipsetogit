package app.component.frontview.controller;

import java.util.HashMap;
import java.util.List;
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

import app.component.frontview.entity.MfAppSeting;
import app.component.frontview.feign.MfAppSetingFeign;
import app.component.frontview.feign.MfAppUpdateSettingFeign;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfFrontKindAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 23 17:08:22 CST 2017
 **/
@Controller
@RequestMapping("/mfAppUpdateSetting")
public class MfAppUpdateSettingController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfAppUpdateSettingFeign mfAppUpdateSettingFeign;
	@Autowired
	private MfAppSetingFeign mfAppSetingFeign;

	@RequestMapping(value = "/createJsonFileAjax")
	@ResponseBody
	public Map<String, Object> createJsonFileAjax(String appVersion) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfAppUpdateSettingFeign.createJsonFile(appVersion);
		} catch (Exception e) {
//			logger.error("createJsonFileAjax方法出错，执行action层失败，抛出异常，", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * app更新设置页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppUpdateConfigPage")
	public String getAppUpdateConfigPage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		JSONObject json = new JSONObject();
		MfAppSeting mfAppSeting=new MfAppSeting();
		List<MfAppSeting> mfAppSetingList = mfAppSetingFeign.findByPage(mfAppSeting);
		JSONArray array = new JSONArray();
		array = JSONArray.fromObject(mfAppSetingList);
		json.put("mfAppSetingList", array);
		ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/frontview/AppUpdateConfig";
	}

	/**
	 * 我的信息页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHistoryVersion")
	@ResponseBody
	public Map<String, Object> getHistoryVersion(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfAppUpdateSettingFeign.getHistoryVersion();
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
}
