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

import app.component.pms.feign.PmsViewpointRoleFeign;
import cn.mftcc.common.MessageEnum;

@Controller
@RequestMapping("/pmsViewpointRole")
public class PmsViewpointRoleController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PmsViewpointRoleFeign pmsViewpointRoleFeign;
	
	{
		ActionContext.initialize(request,
				response);
	}
	

	@RequestMapping(value = "/insertByRoleNo")
	public Map<String, Object> insertByRoleNo(Model model, String roleNo,String viewpointMenuNo) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		pmsViewpointRoleFeign.insertByRoleNo(roleNo,viewpointMenuNo);
		dataMap.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}

}
