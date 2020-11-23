package app.component.cus.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.pmsinterface.PmsInterfaceFeign;

/**
 * Title: BankIdentifyAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 28 17:31:46 CST 2016
 **/
@Controller
@RequestMapping("/mfCusInfoDetail")
public class MfCusInfoDetailController extends BaseFormBean {
	@Autowired
	private PmsInterfaceFeign pmsInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 获得当前操作员的功能权限，对角色时，所有开启的功能权限合并。
	 * 
	 * @return
	 * @throws Exception
	 * @author Jiasl
	 */
	@RequestMapping(value = "/getUserPmsBizsAjax")
	@ResponseBody
	public Map<String, Object> getUserPmsBizsAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String opNo = User.getRegNo(request);
		try {
			String pmBizNo = pmsInterfaceFeign.getUserPmsBizs(opNo);
			dataMap.put("pmBizNo", pmBizNo);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			// logger.error("查询操作员权限出错",e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询操作员权限出错");
		}
		return dataMap;
	}

}
