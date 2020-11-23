package app.component.pact.fiveclass.controller;


import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.ViewUtil;
import app.component.pact.fiveclass.entity.FiveClassAndPact;
import app.component.pact.fiveclass.entity.MfFiveclassSummaryApply;
import app.component.pact.fiveclass.feign.MfFiveclassSummaryApplyFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WkfInterfaceFeign;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 10 12:26:58 CST 2017
 **/
@Controller
@RequestMapping("/mfFiveclassSummaryApply")
public class MfFiveclassSummaryApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfFiveclassSummaryApplyFeign mfFiveclassSummaryApplyFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private WorkflowDwrFeign workflowDwrFeign;
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String fiveclassContext) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfiveclassapplyBase = formService.getFormData("fiveclassApplyBase");
		try {
			getFormValue(formfiveclassapplyBase, getMapByJson(ajaxData));
			if (this.validateFormData(formfiveclassapplyBase)) {
				MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
				setObjValue(formfiveclassapplyBase, mfFiveclassSummaryApply);
				mfFiveclassSummaryApply.setFiveclassContext(fiveclassContext);
				mfFiveclassSummaryApply=mfFiveclassSummaryApplyFeign.insert(mfFiveclassSummaryApply);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfFiveclassSummaryApply.getApproveNodeName());
				paramMap.put("opNo", mfFiveclassSummaryApply.getApprovePartName());
				if (!"".equals(mfFiveclassSummaryApply.getApproveNodeName()) && mfFiveclassSummaryApply.getApproveNodeName() != null) {
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("分类"));
				}
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}	
		return dataMap;
	}
	@RequestMapping(value = "/insertAjaxForStage")
	@ResponseBody
	public Map<String, Object> insertAjaxForStage(String ajaxData,String fiveclassContext) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfiveclassapplyBase = formService.getFormData("fiveclassApplyStageBase");
		try {
			getFormValue(formfiveclassapplyBase, getMapByJson(ajaxData));
			if (this.validateFormData(formfiveclassapplyBase)) {
				MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
				setObjValue(formfiveclassapplyBase, mfFiveclassSummaryApply);
				mfFiveclassSummaryApply.setFiveclassContext(fiveclassContext);
				mfFiveclassSummaryApply=mfFiveclassSummaryApplyFeign.insertForStage(mfFiveclassSummaryApply);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfFiveclassSummaryApply.getApproveNodeName());
				paramMap.put("opNo", mfFiveclassSummaryApply.getApprovePartName());
				if (!"".equals(mfFiveclassSummaryApply.getApproveNodeName()) && mfFiveclassSummaryApply.getApproveNodeName() != null) {
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("分类"));
				}
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String applyId, String activityType) throws Exception {

		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclassApplyViewPoint = formService.getFormData("fiveclassApplyViewPoint");
		getFormValue(formfiveclassApplyViewPoint);
		MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
		mfFiveclassSummaryApply.setApplyId(applyId);
		mfFiveclassSummaryApply = mfFiveclassSummaryApplyFeign.getById(mfFiveclassSummaryApply);
		getObjValue(formfiveclassApplyViewPoint, mfFiveclassSummaryApply);
		String fiveclassContent  = mfFiveclassSummaryApply.getFiveclassContext();
		JSONArray jsonArray = JSONArray.fromObject(fiveclassContent);
		List<FiveClassAndPact> fiveClassAndPacts = new ArrayList<FiveClassAndPact>();
		for (int i = 0; i < jsonArray.size(); i++) {
			FiveClassAndPact fiveClassAndPact = JsonStrHandling.handlingStrToBean(jsonArray.get(i), FiveClassAndPact.class) ;
			fiveClassAndPacts.add(fiveClassAndPact);
		}
		Ipage ipage = this.getIpage();
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablefiveandpactbatchapprove", "thirdTableTag", fiveClassAndPacts, ipage, true);
		JSONObject json = new JSONObject();
		json.put("tableHtml",tableHtml);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_EXAM;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("applyId", mfFiveclassSummaryApply.getApplyId());
	
		dataMap.put("scNo", scNo);
		ViewUtil.setViewPointParm(request, dataMap);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfFiveclassSummaryApply.getApplyId(), null);
		List<OptionsList> opinionTypeList = new ArrayList<OptionsList>();
		// 处理审批意见类型
		List<OptionsList> opinionTypeList1 = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		for (int i = 0; i < opinionTypeList1.size(); i++) {
			OptionsList optionsList = opinionTypeList1.get(i);
			if("1".equals(optionsList.getOptionValue())){
				opinionTypeList.add(optionsList);
			}
		}
		this.changeFormProperty(formfiveclassApplyViewPoint, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formfiveclassApplyViewPoint", formfiveclassApplyViewPoint);
		model.addAttribute("applyId", applyId);
		model.addAttribute("scNo", scNo);
		model.addAttribute("query", "");
		
		return "component/pact/fiveclass/MfFiveclassSummaryApply_ViewPoint";
	}
	@RequestMapping(value = "/getViewPointForStage")
	public String getViewPointForStage(Model model, String applyId, String activityType) throws Exception {

		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclassApplyViewPoint = formService.getFormData("fiveclassApplyViewPointForStage");
		getFormValue(formfiveclassApplyViewPoint);
		MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
		mfFiveclassSummaryApply.setApplyId(applyId);
		mfFiveclassSummaryApply = mfFiveclassSummaryApplyFeign.getById(mfFiveclassSummaryApply);
		getObjValue(formfiveclassApplyViewPoint, mfFiveclassSummaryApply);
		String fiveclassContent  = mfFiveclassSummaryApply.getFiveclassContext();
		JSONArray jsonArray = JSONArray.fromObject(fiveclassContent);
		List<FiveClassAndPact> fiveClassAndPacts = new ArrayList<FiveClassAndPact>();
		for (int i = 0; i < jsonArray.size(); i++) {
			FiveClassAndPact fiveClassAndPact = JsonStrHandling.handlingStrToBean(jsonArray.get(i), FiveClassAndPact.class) ;
			fiveClassAndPacts.add(fiveClassAndPact);
		}
		Ipage ipage = this.getIpage();
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablefiveandpactbatchapprove", "thirdTableTag", fiveClassAndPacts, ipage, true);
		JSONObject json = new JSONObject();
		json.put("tableHtml",tableHtml);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_EXAM;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("applyId", mfFiveclassSummaryApply.getApplyId());

		dataMap.put("scNo", scNo);
		ViewUtil.setViewPointParm(request, dataMap);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfFiveclassSummaryApply.getApplyId(), null);
		List<OptionsList> opinionTypeList = new ArrayList<OptionsList>();
		// 处理审批意见类型
		List<OptionsList> opinionTypeList1 = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		for (int i = 0; i < opinionTypeList1.size(); i++) {
			OptionsList optionsList = opinionTypeList1.get(i);
			if("1".equals(optionsList.getOptionValue())){
				opinionTypeList.add(optionsList);
			}
		}
		this.changeFormProperty(formfiveclassApplyViewPoint, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formfiveclassApplyViewPoint", formfiveclassApplyViewPoint);
		model.addAttribute("applyId", applyId);
		model.addAttribute("scNo", scNo);
		model.addAttribute("query", "");

		return "component/pact/fiveclass/MfFiveclassSummaryApply_ViewPointForStage";
	}

	/**
	 * 审批流程提交
	 *
	 * @param taskId
	 * @param activityType
	 * @param fiveclassId
	 * @param opinionType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/submitUpdate")
	@ResponseBody
	public Map<String, Object> submitUpdate(Model model, String ajaxData, String taskId, String activityType,
											String applyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclassApplyViewPoint = formService.getFormData("fiveclassApplyViewPoint");
		getFormValue(formfiveclassApplyViewPoint, getMapByJson(ajaxData));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
		setObjValue(formfiveclassApplyViewPoint, mfFiveclassSummaryApply);
		if (taskId != null) {
			if (taskId.indexOf(",") != -1) {
				taskId = taskId.substring(0, taskId.indexOf(","));
			}
		}
		dataMap = (HashMap<String, Object>) getMapByJson(ajaxData);
		dataMap.put("activityType", activityType);
		dataMap.put("orgNo", User.getOrgNo(request));
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		String transition = workflowDwrFeign.findNextTransition(taskId);
		transition = request.getParameter("transition");
		//	String transition = list.get(0).getName();
		dataMap.put("taskId", taskId);
		dataMap.put("appNo", applyId);
		dataMap.put("opinionType", opinionType);
		dataMap.put("approvalOpinion", approvalOpinion);
		dataMap.put("transition", transition);
		dataMap.put("opNo", User.getRegNo(this.request));
		dataMap.put("nextUser","");
		dataMap.put("mfFiveclassSummaryApply", mfFiveclassSummaryApply);
		Result res = mfFiveclassSummaryApplyFeign.doCommit(dataMap);

		if (!res.isSuccess()) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		} else {
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		}
		return dataMap;
	}
	/**
	 * 审批流程提交
	 *
	 * @param taskId
	 * @param activityType
	 * @param fiveclassId
	 * @param opinionType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/submitUpdateForStage")
	@ResponseBody
	public Map<String, Object> submitUpdateForStage(Model model, String ajaxData, String taskId, String activityType, String applyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclassApplyViewPoint = formService.getFormData("fiveclassApplyViewPointForStage");
		getFormValue(formfiveclassApplyViewPoint, getMapByJson(ajaxData));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
		setObjValue(formfiveclassApplyViewPoint, mfFiveclassSummaryApply);
		if (taskId != null) {
			if (taskId.indexOf(",") != -1) {
				taskId = taskId.substring(0, taskId.indexOf(","));
			}
		}
		dataMap = (HashMap<String, Object>) getMapByJson(ajaxData);
		dataMap.put("activityType", activityType);
		dataMap.put("orgNo", User.getOrgNo(request));
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		String transition = workflowDwrFeign.findNextTransition(taskId);
		transition = request.getParameter("transition");
		//	String transition = list.get(0).getName();
		dataMap.put("taskId", taskId);
		dataMap.put("appNo", applyId);
		dataMap.put("opinionType", opinionType);
		dataMap.put("approvalOpinion", approvalOpinion);
		dataMap.put("transition", transition);
		dataMap.put("opNo", User.getRegNo(this.request));
		dataMap.put("nextUser","");
		dataMap.put("mfFiveclassSummaryApply", mfFiveclassSummaryApply);
		Result res = mfFiveclassSummaryApplyFeign.doCommitForStage(dataMap);

		if (!res.isSuccess()) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		} else {
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		}
		return dataMap;
	}
	@RequestMapping(value = "/getListPage")
	public String getListHisPage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/pact/fiveclass/MfFiveclassSummaryApply_List";
	}
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
		try {
			mfFiveclassSummaryApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfFiveclassSummaryApply.setCriteriaList(mfFiveclassSummaryApply, ajaxData);// 我的筛选
			// this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfFiveclassSummaryApply", mfFiveclassSummaryApply));
			ipage = mfFiveclassSummaryApplyFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 查询(客户经理调整页面)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String applyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclassapplyDetail = formService.getFormData("fiveclassapplyDetail");
		getFormValue(formfiveclassapplyDetail);
		MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
		mfFiveclassSummaryApply.setApplyId(applyId);
		mfFiveclassSummaryApply = mfFiveclassSummaryApplyFeign.getById(mfFiveclassSummaryApply);
		getObjValue(formfiveclassapplyDetail, mfFiveclassSummaryApply);
		String fiveclassContent  = mfFiveclassSummaryApply.getFiveclassContext();
		JSONArray jsonArray = JSONArray.fromObject(fiveclassContent);
		List<FiveClassAndPact> fiveClassAndPacts = new ArrayList<FiveClassAndPact>();
		for (int i = 0; i < jsonArray.size(); i++) {
			FiveClassAndPact fiveClassAndPact = JsonStrHandling.handlingStrToBean(jsonArray.get(i), FiveClassAndPact.class) ;
			fiveClassAndPacts.add(fiveClassAndPact);
		}
		Ipage ipage = this.getIpage();
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablefiveandpactbatch", "thirdTableTag", fiveClassAndPacts, ipage, true);
		JSONObject json = new JSONObject();
		json.put("tableHtml",tableHtml);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("formfiveclassapplyDetail", formfiveclassapplyDetail);
		model.addAttribute("applyIdnew", applyId);
		model.addAttribute("query", "");
		return "component/pact/fiveclass/MfFiveclassSummaryApply_Detail";
	}
	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		boolean validateFlag = this.validateFormData(formfiveclass0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		boolean validateFlag = this.validateFormData(formfiveclass0002);
	}

}
