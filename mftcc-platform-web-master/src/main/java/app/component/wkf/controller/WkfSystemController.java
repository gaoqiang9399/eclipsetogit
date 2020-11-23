package app.component.wkf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhcc.workflow.WF;

import app.common.BaseController;
import app.component.wkf.WebUtil;
import app.component.wkf.entity.WkfApprovalUser;
import app.component.wkf.feign.WkfApprovalUserFeign;
import app.util.toolkit.Ipage;
@Controller
@RequestMapping(value = "/wkfSystem")
public class WkfSystemController extends BaseController {
	@Autowired
	private WkfApprovalUserFeign wkfApprovalUserFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/*private PrdBaseBo prdBaseBo;*/
	@RequestMapping(value = "/findUsersInfo")
	public String findUsersInfo() {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String roleNo = request.getParameter("wf_roleno");
		Ipage ipage = this.getIpage();
		
		WkfApprovalUser wkfApprovalUser = new WkfApprovalUser();
		wkfApprovalUser.setStartNumAndEndNum(ipage);
		wkfApprovalUser.setWkfUserName(userId);
		wkfApprovalUser.setWkfBrNo(WebUtil.getBranchId(request));
		wkfApprovalUser.setWkfRoleNo(roleNo);
		List<String> list = wkfApprovalUserFeign.getWkfApprovalUserList(wkfApprovalUser);
		
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return WF.PARAM_USERSINFO_FIND;
	}
	@RequestMapping(value = "/selectUser")
	public String selectUser() {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String brNo = request.getParameter("brNo");
		Ipage ipage = this.getIpage();
		
		WkfApprovalUser wkfApprovalUser = new WkfApprovalUser();
		wkfApprovalUser.setStartNumAndEndNum(ipage);
		wkfApprovalUser.setWkfUserName(userId);
		if(brNo != "all"){
			wkfApprovalUser.setWkfBrNo(WebUtil.getBranchId(request));
		}
		List<String> list = wkfApprovalUserFeign.getWkfApprovalUserList(wkfApprovalUser);
		
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return "/component/wkf/selectUserInfo";
	}
	@RequestMapping(value = "/selectBusiness")
	public String selectBusiness() {
		HttpServletRequest request = getHttpRequest();
		Ipage ipage = this.getIpage();
//		PrdBase prdBase = new PrdBase();
		
//		List<PrdBase> list = (List<PrdBase>)prdBaseBo.findByPage(ipage, prdBase).getResult();
//		ipage.setResult(list);
//		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return "/component/wkf/WFProxy_SelectBusiness";
	}

	
}
