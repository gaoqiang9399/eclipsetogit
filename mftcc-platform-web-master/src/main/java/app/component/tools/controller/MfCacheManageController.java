package app.component.tools.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import cn.mftcc.util.PropertiesUtil;
/**
 * 缓存管理入口。
 * @author Tangxj
 */

@Controller
@RequestMapping(value = "/mfCacheManage")
public class MfCacheManageController extends BaseFormBean {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	public MfCacheManageController(){
	}
	
	@ResponseBody
	@RequestMapping(value = "/resetPropertiesAjax")
	public Map<String, Object> resetPropertiesAjax() throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		dataMap.put("flag", "error");
		dataMap.put("msg", "此功能已失效，请重启服务！");
//		boolean result = PropertiesUtil.resetProperties();
//		if (result) {
//			dataMap.put("flag", "success");
//		} else {
//			dataMap.put("flag", "error");
//			dataMap.put("msg", "重置失败！");
//		}
		return dataMap;
	}
	
}
