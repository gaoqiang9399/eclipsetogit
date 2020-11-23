package app.component.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;

import app.common.BaseController;
/**
 * @ClassName: SysInterceptorAjaxAction
 * @author jzh
 * @date 2016-7-12 下午07:00:00
 */
@Controller
@RequestMapping("/sysInterceptorAjax")
public class SysInterceptorAjaxController extends BaseController {
	private static final long serialVersionUID = 4845731637868007220L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	/**
	 * session 失效
	 * @return
	 */
	@RequestMapping(value = "/sessionFailure")
	@ResponseBody
	public Map<String,Object> sessionFailure(){
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("flag", "error");
		dataMap.put("msg", "登陆超时,请重新登陆！");
		return dataMap;	
	}
	/**
	 * 其他系统情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/otherSysSituation")
	@ResponseBody
	public Map<String,Object> otherSysSituation(){
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(request!=null){
			HttpSession session = request.getSession();
			if(session!=null){
				Map<String,Object> msgMap = new HashMap<String,Object>();
				msgMap = (Map<String, Object>) session.getAttribute("msgMap");
				session.removeAttribute("msgMap");
				String actionMsg = "";//提示信息
				String state = "";//提示状态  1.globalSts 2.noLogin 3.exception
				if(msgMap!=null){
					actionMsg = (String)msgMap.get("actionMsg");
					state = (String)msgMap.get("state");
				}
				dataMap.put("flag", "error");
				dataMap.put("state", state);
				dataMap.put("msg", actionMsg);
				return dataMap;	
			}
		}
		dataMap.put("flag", "error");
		dataMap.put("msg", "系统访问失败！");
		return dataMap;	
	}
	
}
