package app.component.pms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.BaseFormBean;

import app.component.pms.feign.PmsDataRangRoleFeign;
import cn.mftcc.common.MessageEnum;

@Controller
@RequestMapping("/pmsDataRangRole")
public class PmsDataRangRoleController extends BaseFormBean{
	//数据权限
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
		private PmsDataRangRoleFeign pmsDataRangRoleFeign;
		
	@RequestMapping(value = "/insertByRoleNo")
		public Map<String, Object> insertByRoleNo(Model model, String roleNo, String funNo) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			pmsDataRangRoleFeign.insertByRoleNo(roleNo,funNo);
			dataMap.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
			return dataMap;
		}
		
	
}
