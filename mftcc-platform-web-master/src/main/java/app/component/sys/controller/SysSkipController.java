package app.component.sys.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.SysDescTempUtil;
import app.base.User;
import app.component.common.BizPubParm;
@Controller
@RequestMapping("/sysSkip")
public class SysSkipController extends BaseFormBean {
	private static final long serialVersionUID = 1L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@RequestMapping(value = "/skipToC")
	public String skipToC() {
		ActionContext.initialize(request,response);
//		SysDescTempUtil.replace("10123", new HashMap<String, Object>(), request);
		return "/component/sys/C";
	}
	
	/**
	 * 业务管理员
	 * @return
	 */
	@RequestMapping(value = "/skipToBizC")
	public String skipToBizC() {
		ActionContext.initialize(request,response);
		SysDescTempUtil.replace("10123", new HashMap<String, Object>(), request);
		return "/component/sys/bizC";
	}
	
	/**
	 * 高管
	 * @return
	 */
	@RequestMapping(value = "/skipToSeniorC")
	public String skipToSeniorC() {
		ActionContext.initialize(request,response);
		SysDescTempUtil.replace("10123", new HashMap<String, Object>(), request);
		return "/component/sys/seniorC";
	}
	
	/**
	 * 风控债权人员
	 * @return
	 */
	@RequestMapping(value = "/skipToRiskDebtC")
	public String skipToRiskDebtC(Model model) {
		ActionContext.initialize(request,response);
		SysDescTempUtil.replace("10123", new HashMap<String, Object>(), request);
		String ifRegion = "";
		String orgNo = User.getOrgNo(getHttpRequest());
		if("101".equals(orgNo.substring(0, 3))){ // 流程变量ifHead判断是不是福州总部
			ifRegion = BizPubParm.YES_NO_N;
		}else{
			ifRegion = BizPubParm.YES_NO_Y;
		}
		model.addAttribute("ifRegion", ifRegion);
		return "/component/sys/riskDebtC";
	}
	
	
	/**
	 * 客户经理
	 * @return
	 */
	@RequestMapping(value = "/skipToManageC")
	public String skipToManageC() {
		ActionContext.initialize(request,response);
		SysDescTempUtil.replace("10123", new HashMap<String, Object>(), request);
		return "/component/sys/manageC";
	}
	
	/**
	 * 资金经理
	 * @return
	 */
	@RequestMapping(value = "/skipToCapitalC")
	public String skipToCapitalC() {
		ActionContext.initialize(request,response);
		SysDescTempUtil.replace("10123", new HashMap<String, Object>(), request);
		return "/component/sys/capitalC";
	}
	
	/**
	 * 财务
	 * @return
	 */
	@RequestMapping(value = "/skipToFinaC")
	public String skipToFinaC() {
		ActionContext.initialize(request,response);
		SysDescTempUtil.replace("10123", new HashMap<String, Object>(), request);
		return "/component/sys/finaC";
	}
	
	/**
	 * OA行政其他功能
	 * @return
	 */
	@RequestMapping(value = "/skipToAdministrativeC")
	public String skipToAdministrativeC() {
		ActionContext.initialize(request,response);
		SysDescTempUtil.replace("10123", new HashMap<String, Object>(), request);
		return "/component/sys/administrativeC";
	}
	
	/**
	 * OA人事其他功能
	 * @return
	 */
	@RequestMapping(value = "/skipToPersonnelC")
	public String skipToPersonnelC() {
		ActionContext.initialize(request,response);
		SysDescTempUtil.replace("10123", new HashMap<String, Object>(), request);
		return "/component/sys/personnelC";
	}
	
}
