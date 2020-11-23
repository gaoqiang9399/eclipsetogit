package app.component.wkf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.api.TaskQuery;

import app.base.User;
import app.component.wkf.AppConstant;
import app.component.wkf.ListUtil;
import app.component.wkf.feign.TaskFeign;
import app.util.toolkit.Ipage;

/**
 * Title: WkfTaskAction.java Description:
 * 
 * @author:renyongxian@dhcc.com.cn
 * @Thu Feb 28 12:59:54 GMT 2013
 **/
@Controller
@RequestMapping(value = "/wkfTask")
public class WkfTaskController extends BaseFormBean {
	private static final long serialVersionUID = 3647309520104256839L;
	// ҳ�洫ֵ
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private TaskFeign taskFeign;
	private List wkfTaskList;
	/**
	 * ��ҳ��ѯ
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0012 = formService.getFormData("wkf0012");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
//		TaskQuery query = WFUtil.getTaskService().createQuery().assignee(User.getRegNo(getHttpRequest()));
//		query.processDefinitionKeys(AppConstant.PAS_WKF_KEY+","+AppConstant.COM_SUP_WKF_KEY+","+AppConstant.COM_WKF_KEY+","+AppConstant.NORMAL_WKF_KEY);
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0012,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0012,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0012,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		return "/component/wkf/WkfTask_List";
	}
	
	//�˻�ҵ��
	@RequestMapping(value = "/findByPageForBack")
	public String findByPageForBack(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0012 = formService.getFormData("wkf0012");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest())).lastResult("rollback");
//		TaskQuery query = WFUtil.getTaskService().createCustomQuery().candidate(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
//		TaskQuery query = WFUtil.getTaskService().createQuery().assignee(User.getRegNo(getHttpRequest()));
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0012,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0012,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0012,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		
		model.addAttribute("formwkf0012", formwkf0012);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForBack";
	}
	@RequestMapping(value = "/findByPageForSecRefund")
	public String findByPageForSecRefund(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0040 = formService.getFormData("wkf0040");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0040,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0040,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0040,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0040", formwkf0040);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForSecRefund";
	}
	@RequestMapping(value = "/findByPageForIqpCont")
	public String findByPageForIqpCont(Model model,String contNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0042 = formService.getFormData("wkf0042");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
		if (null != contNo && !"".equals(contNo))
		{
			query.appId(contNo);
			this.changeFormProperty(formwkf0042,"contNo","initValue",contNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0042,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0042,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0042", formwkf0042);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "list";
	}
	@RequestMapping(value = "/findByPageForTransfer")
	public String findByPageForTransfer(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0039 = formService.getFormData("wkf0039");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0039,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0039,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0039,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0039", formwkf0039);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "list";
	}
	@RequestMapping(value = "/findByPageForCancel")
	public String findByPageForCancel(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0038 = formService.getFormData("wkf0038");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0038,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0038,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0038,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0038", formwkf0038);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForCancel";
	}
	@RequestMapping(value = "/findByPageForSecAmtOffset")
	public String findByPageForSecAmtOffset(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0037 = formService.getFormData("wkf0037");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0037,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0037,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0037,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0037", formwkf0037);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForSecAmtOffset";
	}
	@RequestMapping(value = "/findByPageForActiveBuyBack")
	public String findByPageForActiveBuyBack(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0034 = formService.getFormData("wkf0034");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0034,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0034,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0034,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0034", formwkf0034);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForActiveBuyBack";
	}
	@RequestMapping(value = "/findByPageForPassiveBuyBack")
	public String findByPageForPassiveBuyBack(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0035 = formService.getFormData("wkf0035");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0035,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0035,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0035,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0035", formwkf0035);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForPassiveBuyBack";
	}
	@RequestMapping(value = "/findByPageForRepayAdv")
	public String findByPageForRepayAdv(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0036 = formService.getFormData("wkf0036");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0036,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0036,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0036,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0036", formwkf0036);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForRepayAdv";
	}
//	public String findByPageForEval() throws Exception {
//		ActionContext.initialize(request,response);
//		formwkf0023 = formService.getFormData("wkf0023");
//		Ipage ipage = this.getIpage();
//		TaskQuery query = WFUtil.getTaskService().createCustomQuery().candidate(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
////		TaskQuery query = WFUtil.getTaskService().createQuery().assignee(User.getRegNo(getHttpRequest()));
//		query.processDefinitionKeys(AppConstant.COM_WKF_EVAL_KEY);
//		if (null != appNo && !"".equals(appNo))
//		{
//			query.appId(appNo);
//			this.changeFormProperty(formwkf0023,"appNo","initValue",appNo);
//		}
//		if (null != approved && !"".equals(approved))
//		{
//			query.approved(approved);
//			this.changeFormProperty(formwkf0023,"approved","initValue",approved);
//		}
//		if (null != cifName && !"".equals(cifName))
//		{
//			query.appValue(name+cifName);
//			this.changeFormProperty(formwkf0023,"cifName","initValue",cifName);
//		}
//		
//		ipage.initPageCounts(new Integer[] { (int) query.count() });
//		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
//		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
//		wkfTaskList = ListUtil.changeList(wkfTaskList);
//		ipage.setResult(wkfTaskList);
//		if (null != appMsg)
//			this.addActionMessage(this.getText(appMsg));
//		return "list";
//	}
	@RequestMapping(value = "/findByPageForAuth")
	public String findByPageForAuth(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0024 = formService.getFormData("wkf0024");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
//		TaskQuery query = WFUtil.getTaskService().createQuery().assignee(User.getRegNo(getHttpRequest()));
		query.processDefinitionKeys(AppConstant.COM_WKF_AUTH_KEY);
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0024,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0024,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0024,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0024", formwkf0024);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForPassiveBuyBack";
	}
	@RequestMapping(value = "/findByPageForExtend")
	public String findByPageForExtend(Model model,String appNo,String approved,String cifName,String name,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0025 = formService.getFormData("wkf0025");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
//		TaskQuery query = WFUtil.getTaskService().createQuery().assignee(User.getRegNo(getHttpRequest()));
		query.processDefinitionKeys(AppConstant.COM_WKF_EXTEND_KEY);
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0025,"appNo","initValue",appNo);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0025,"approved","initValue",approved);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0025,"cifName","initValue",cifName);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0025", formwkf0025);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForExtend";
	}
	@RequestMapping(value = "/findByPageForDealer")
	public String findByPageForDealer(Model model,String appNo,String approved,String cifName,String name,String activityName,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0022 = formService.getFormData("wkf0022");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
//		TaskQuery query = WFUtil.getTaskService().createQuery().assignee(User.getRegNo(getHttpRequest()));
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0022,"appNo","initValue",appNo);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0022,"cifName","initValue",cifName);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0022,"approved","initValue",approved);
		}
		if (null != activityName && !"".equals(activityName))
		{
			query.activityNames(activityName);
			this.changeFormProperty(formwkf0022,"activityName","initValue",activityName);
		}
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0022", formwkf0022); 
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForDealer";
	}
	@RequestMapping(value = "/findByPageForExtendDealer")
	public String findByPageForExtendDealer(Model model,String appNo,String approved,String cifName,String name,String activityName,String appMsg) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0022 = formService.getFormData("wkf0022");
		Ipage ipage = this.getIpage();
		TaskQuery query = taskFeign.candidate2(User.getRegNo(getHttpRequest()),User.getOrgNo(getHttpRequest()));
		query.processDefinitionKeys(AppConstant.COM_WKF_EXTEND_KEY);
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0022,"appNo","initValue",appNo);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0022,"cifName","initValue",cifName);
		}
		if (null != approved && !"".equals(approved))
		{
			query.approved(approved);
			this.changeFormProperty(formwkf0022,"approved","initValue",approved);
		}
		if (null != activityName && !"".equals(activityName))
		{
			query.activityNames(activityName);
			this.changeFormProperty(formwkf0022,"activityName","initValue",activityName);
		}
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
		wkfTaskList = query.page(firstResult, ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		wkfTaskList = ListUtil.changeList(wkfTaskList);
		ipage.setResult(wkfTaskList);
		if (null != appMsg) {
            this.addActionMessage(model,appMsg);
        }
		model.addAttribute("formwkf0022", formwkf0022);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/WkfTask_ListForExtendDealer";
	}
}