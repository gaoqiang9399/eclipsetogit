package  app.component.auth.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.auth.entity.MfCusCreditAdjustApply;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.entity.MfCusCreditContractHis;
import app.component.auth.entity.MfCusPorductCredit;
import app.component.auth.feign.MfCusCreditAdjustApplyFeign;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.auth.feign.MfCusCreditContractHisFeign;
import app.component.auth.feign.MfCusCreditPactWkfFeign;
import app.component.auth.feign.MfCusPorductCreditFeign;
import app.component.auth.feign.MfWorkFlowDyFormFeign;
import app.component.common.ApplyEnum;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusTable;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfCusCreditApproveInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jun 26 09:56:51 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCreditPactWkf")
public class MfCusCreditPactWkfController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;
	@Autowired
	private MfCusCreditContractHisFeign mfCusCreditContractHisFeign;
	@Autowired
	private MfCusCreditApplyFeign mfCusCreditApplyFeign;
	@Autowired
	private MfCusCreditAdjustApplyFeign mfCusCreditAdjustApplyFeign;
	@Autowired
	private MfCusPorductCreditFeign mfCusPorductCreditFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusCreditPactWkfFeign mfCusCreditPactWkfFeign;
	@Autowired
	private MfWorkFlowDyFormFeign mfWorkFlowDyFormFeign;
	@Autowired
	private MfSysKindFeign mfSysKindFeign;
	/**
	 * 方法描述： 授信合同审批页面
	 * @param model
	 * @param cusNo
	 * @param pactId
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2018年6月27日 下午3:29:00
	 */
	@RequestMapping(value = "/getViewPoint")
	@SuppressWarnings({ "unchecked" })
	public String getViewPoint(Model model, String cusNo, String  pactId,String taskId, String hideOpinionType, String isPrimary,String updateCreditAmt) throws Exception{
		FormService formService = new FormService();
		String creditAppId = null;
		String creditType = null;
		String formId = null;
		ActionContext.initialize(request, response);
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setPactId(pactId);
		mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);

		//获得审批节点信息
		String pactIdTmp = pactId;
		if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是合同清稿审批
			pactIdTmp = mfCusCreditContract.getPrimaryPactId();
		}
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(pactIdTmp, taskId);
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		MfCusCreditContractHis mfCusCreditContractHis = new MfCusCreditContractHis();
		mfCusCreditContractHis.setPactId(pactId);
		if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是业务初选审批
			mfCusCreditContractHis.setPrimaryPactId(mfCusCreditContract.getPrimaryPactId());
		}
		List<MfCusCreditContractHis> list = mfCusCreditContractHisFeign.getCreditContractHisList(mfCusCreditContractHis);
		if (list != null && list.size() > 0) {
			mfCusCreditContractHis = list.get(0);
            mfCusCreditContractHis.setId(mfCusCreditContract.getId());
			mfCusCreditContractHis.setCreditType(mfCusCreditContract.getCreditType());
			PropertyUtils.copyProperties(mfCusCreditContract, mfCusCreditContractHis);
		}
		creditAppId = mfCusCreditContract.getCreditAppId();
		creditType = mfCusCreditContract.getCreditType();
		String nodeNo = taskAppro.getActivityName();
		String defFalg = ApplyEnum.BUDGET_DEFFLAG_TYPE.DEFFLAG1.getCode();
		String creditModel = mfCusCreditContract.getCreditModel();
		String kindNo = mfCusCreditContract.getTemplateCreditId();
		WKF_NODE node = WKF_NODE.credit_pact_approval;
		if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是初选审批
			node = WKF_NODE.primary_credit_pact_approval;
		}
		MfKindForm mfKindForm = prdctInterfaceFeign.getInitKindForm(kindNo,nodeNo,defFalg);
		if (mfKindForm==null) {
			formId = prdctInterfaceFeign.getFormId(kindNo, node, null, null, User.getRegNo(request));
		}else{
			formId = mfKindForm.getAddModel();
		}
		FormData formcreditpactApprove = formService.getFormData(formId);
		
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType) || BizPubParm.CREDIT_TYPE_TRANSFER.equals(creditType)) {
			mfCusCreditApply.setCreditAppId(creditAppId);
			mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		}else{
			MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
			mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
			mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
			mfCusCreditApply.setCreditAppId(mfCusCreditAdjustApply.getCreditAppId());
			mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		}
		mfCusCreditContract.setProjectName(mfCusCreditApply.getProjectName());
		mfCusCreditContract.setProjectNo(mfCusCreditApply.getProjectNo());
		mfCusCreditContract.setRiskLevel(mfCusCreditApply.getRiskLevel());
		mfCusCreditContract.setRiskLevelName(mfCusCreditApply.getRiskLevelName());

		getObjValue(formcreditpactApprove, mfCusCreditContract);
		String activityType = taskAppro.getActivityType();
		//处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),opinionTypeMap);
		this.changeFormProperty(formcreditpactApprove, "opinionType", "optionArray", opinionTypeList);
		
		//查询授信产品信息
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setCreditAppId(creditAppId);
		List<MfCusPorductCredit> mfCusPorductCredits = mfCusPorductCreditFeign.getByCreditAppIdDesc(mfCusPorductCredit);
		
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		JSONObject json = new JSONObject();
		JSONArray array = JSONArray.fromObject(mfCusPorductCredits);
		json.put("mfCusPorductCreditList", array);
		//获取所有产品
		List<MfSysKind> mfSysKinds = new ArrayList<MfSysKind>();
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setUseFlag("1");
		mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
		for(MfSysKind kind:mfSysKinds){
			kind.setRemark("");
		}
		array = JSONArray.fromObject(mfSysKinds);
		json.put("mfSysKinds", array);
		request.setAttribute("creditType", creditType);
		request.setAttribute("json", json);
		request.setAttribute("cusType", String.valueOf(mfCusCustomer.getCusType()));
		
		//为了展示调整前的产品额度
		if(BizPubParm.CREDIT_TYPE_ADJUST.equals(mfCusCreditContract.getCreditType())){
			MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
			mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
			mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
			
			model.addAttribute("creditAppIdPro", mfCusCreditAdjustApply.getCreditAppId());
			model.addAttribute("adjAppIdPro", creditAppId);
		}
		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		request.setAttribute("scNo", scNo);
		model.addAttribute("formcreditpactApprove", formcreditpactApprove);
		model.addAttribute("query", "");
		model.addAttribute("entrance", "credit");
		model.addAttribute("nodeNo",nodeNo);
		model.addAttribute("baseType",String.valueOf(mfCusCustomer.getCusType()).substring(0,1));
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("pactId",pactId);
		model.addAttribute("creditAppId",creditAppId);
		model.addAttribute("isPrimary", isPrimary);
		model.addAttribute("primaryPactId", mfCusCreditContract.getPrimaryPactId());
        model.addAttribute("updateCreditAmt", updateCreditAmt);
		model.addAttribute("approvalNodeNo", WKF_NODE.credit_pact_approval.getNodeNo());
		//授信合同审批页面增加电话回访动态表单
		String wkfId = taskAppro.getProcessDefinitionId();
		String activityName = taskAppro.getActivityName();
//		List<MfCusTable> cusTableList = getDyFormHtml(wkfId,activityName ,pactId, this.getIpage());
		List<MfCusTable> cusTableList = new ArrayList<>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("dynTableList", cusTableList);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
		
		
		return "/component/auth/MfCusCreditPactApprove";
	}
	
	

	/**
	 * 方法描述： 授信合同审批提交
	 * @param ajaxData
	 * @param taskId
	 * @param pactId
	 * @param transition
	 * @param nextUser
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2018年6月27日 下午3:53:12
	 */
	@RequestMapping(value = "/submitApproveAjax")
	@ResponseBody
	public Map<String, Object> submitApproveAjax(String ajaxData, String taskId, String pactId, String transition, String nextUser ,String kindNoNews,String  creditAmtNews,String amountLands,String monthTotalRates) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		dataMap=getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalRemark"));
		FormData formcreditPactApprove = formService.getFormData(String.valueOf(dataMap.get("formId")));
		getFormValue(formcreditPactApprove, getMapByJson(ajaxData));
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		setObjValue(formcreditPactApprove, mfCusCreditContract);
		dataMap.put("mfCusCreditContract", mfCusCreditContract);
		dataMap.put("isCeilingLoop", mfCusCreditContract.getIsCeilingLoop());
		dataMap.put("taskId", taskId);
		dataMap.put("pactId", pactId);
		dataMap.put("transition", transition);
		dataMap.put("opNo", User.getRegNo(request));
		dataMap.put("nextUser", nextUser);
		dataMap.put("opinionType", opinionType);
		dataMap.put("approvalOpinion", approvalOpinion);
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("regNo", User.getRegNo(request));
		Result res = mfCusCreditPactWkfFeign.doCommit(dataMap);
		//此时合同修改授信产品信息
		if(!"".equals(kindNoNews)&&!"".equals(creditAmtNews)&&!"".equals(amountLands)&&!"".equals(monthTotalRates)){
			JSONArray	kindNoXins = JSONArray.fromObject(kindNoNews);
			JSONArray	creditAmtXins = JSONArray.fromObject(creditAmtNews);
			JSONArray	amountLandXins = JSONArray.fromObject(amountLands);
			JSONArray	monthTotalRateXins = JSONArray.fromObject(monthTotalRates);
			if ( creditAmtXins!=null && creditAmtXins.size() > 0 && !creditAmtXins.isEmpty() && amountLandXins != null && amountLandXins.size() > 0) {
			    MfCusPorductCredit	porductCredit  =  new  MfCusPorductCredit();
			    porductCredit.setCreditAppId(mfCusCreditContract.getCreditAppId());
				//根据授信申请id删除授信产品表里面的数据
			    mfCusPorductCreditFeign.deleteByCreditId(porductCredit);
				
				MfCusPorductCredit cusPorductCredit = new MfCusPorductCredit();
				 MfSysKind   mfSysKind  =  new   MfSysKind();
				for(int i = 0,count=creditAmtXins.size();i<count;i++){
					if(StringUtil.isNotEmpty(kindNoXins.getString(i))){
						cusPorductCredit.setKindNo(kindNoXins.getString(i));
						 mfSysKind.setKindNo(kindNoXins.getString(i));
					}
				   if(StringUtil.isNotEmpty(creditAmtXins.getString(i))){
					   cusPorductCredit.setCreditAmt(Double.valueOf(creditAmtXins.getString(i)));
					   cusPorductCredit.setCreditBal(Double.valueOf(creditAmtXins.getString(i)));
				   }
				   if(amountLandXins.getString(i) != null  &&  !"null".equals(amountLandXins.getString(i))  && StringUtil.isNotEmpty(amountLandXins.getString(i))){
					   cusPorductCredit.setAmountLand(Integer.parseInt(amountLandXins.getString(i)));
				   }
				   if(monthTotalRateXins.getString(i) != null  &&  !"null".equals(monthTotalRateXins.getString(i))  && StringUtil.isNotEmpty(monthTotalRateXins.getString(i))){
					   cusPorductCredit.setMonthTotalRate(Double.valueOf(monthTotalRateXins.getString(i)));
				   }
				   cusPorductCredit.setCreditAppId(mfCusCreditContract.getCreditAppId());
				   //根据产品号查询出产品的名字
				   MfSysKind    sysKind = mfSysKindFeign.getById(mfSysKind);
				   if(sysKind != null){
					   cusPorductCredit.setKindName(sysKind.getKindName());
				   }
				   //修改授信产品表
				   mfCusPorductCreditFeign.deleteKindInsert(cusPorductCredit);
				}
	       }
		}
		if(res.isSuccess()){
			dataMap.put("flag", "success");
			if(res.isEndSts()){
				Result result  = mfCusCreditPactWkfFeign.doCommitNextStep(mfCusCreditContract);
				if (!result.isSuccess()) {
					dataMap.put("flag", "error");
				}
				dataMap.put("msg", result.getMsg());
			}else{
				dataMap.put("msg", res.getMsg());
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}
	/**
	 * 方法描述： 授信合同审批提交
	 * @param ajaxData
	 * @param taskId
	 * @param pactId
	 * @param transition
	 * @param nextUser
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2018年7月23日 下午4:53:12
	 */
	@RequestMapping(value = "/submitApproveForPrimaryAjax")
	@ResponseBody
	public Map<String, Object> submitApproveForPrimaryAjax(String ajaxData, String taskId, String pactId,String primaryPactId, String transition, String nextUser) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		dataMap=getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalRemark"));
		FormData formcreditPactApprove = formService.getFormData(String.valueOf(dataMap.get("formId")));
		getFormValue(formcreditPactApprove, getMapByJson(ajaxData));
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		setObjValue(formcreditPactApprove, mfCusCreditContract);
		dataMap.put("mfCusCreditContract", mfCusCreditContract);
		dataMap.put("isCeilingLoop", mfCusCreditContract.getIsCeilingLoop());
		dataMap.put("taskId", taskId);
		dataMap.put("pactId", pactId);
		dataMap.put("primaryPactId", primaryPactId);
		dataMap.put("transition", transition);
		dataMap.put("opNo", User.getRegNo(request));
		dataMap.put("nextUser", nextUser);
		dataMap.put("opinionType", opinionType);
		dataMap.put("approvalOpinion", approvalOpinion);
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("regNo", User.getRegNo(request));
		Result res = mfCusCreditPactWkfFeign.doCommitForPrimary(dataMap);
		if(res.isSuccess()){
			dataMap.put("flag", "success");
			if(res.isEndSts()){
				Result result  = mfCusCreditPactWkfFeign.doCommitNextStep(mfCusCreditContract);
				if (!result.isSuccess()) {
					dataMap.put("flag", "error");
				}
				dataMap.put("msg", result.getMsg());
			}else{
				dataMap.put("msg", res.getMsg());
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}
}
