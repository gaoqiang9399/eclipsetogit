package app.component.pms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;

import app.component.pms.feign.PmsEntranceRoleFeign;
import cn.mftcc.common.MessageEnum;

@Controller
@RequestMapping("/pmsEntranceRole")
public class PmsEntranceRoleController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PmsEntranceRoleFeign pmsEntranceRoleFeign;
	
	{
		ActionContext.initialize(request,
				response);
	}
	
	
	@RequestMapping(value = "/insert")
	public Map<String, Object> insert(Model model) throws Exception{
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		model.addAttribute("query", "");
		model.addAttribute("dataMap",dataMap);
		return dataMap;
	}
	
	@RequestMapping(value = "/insertByRoleNo")
	public Map<String, Object> insertByRoleNo(Model model, String roleNo, String pmsNo) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		pmsEntranceRoleFeign.insertByRoleNo(roleNo,pmsNo);
		dataMap.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	
	@RequestMapping(value = "/getById")
	public Map<String, Object> getById(Model model) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}


}
