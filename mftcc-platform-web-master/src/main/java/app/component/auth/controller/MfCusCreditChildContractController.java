package  app.component.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.auth.entity.MfCusCreditAdjustApply;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditApproveInfo;
import app.component.auth.entity.MfCusCreditChildContract;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditAdjustApplyFeign;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditApproveInfoFeign;
import app.component.auth.feign.MfCusCreditChildContractFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 类名： MfCusCreditChildContractController
 * 描述：资金机构子合同表
 * @author 沈浩兵
 * @date 2018年5月11日 上午9:24:51
 * 
 *
 */
@Controller
@RequestMapping("/mfCusCreditChildContract")
public class MfCusCreditChildContractController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCreditChildContractFeign mfCusCreditChildContractFeign;
	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired 
	private MfCusCreditApplyFeign mfCusCreditApplyFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private WorkflowDwrFeign  workflowDwrFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private WkfBusInterfaceFeign wkfBusInterfaceFeign;
	@Autowired 
	private MfCusCreditApproveInfoFeign mfCusCreditApproveInfoFeign;
	@Autowired
	private MfCusCreditAdjustApplyFeign mfCusCreditAdjustApplyFeign;
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		String query = "";
		String kindNo = "";
		String creditType = BizPubParm.CREDIT_TYPE_ADD;//授信类型1新增2调整追加
		String creditAppId = request.getParameter("creditAppId");
		String wkfAppId = request.getParameter("wkfAppId");
		//
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		/*mfCusCreditApply.setCreditAppId(creditAppId);
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);*/
		mfCusCreditApply.setWkfAppId(wkfAppId);
		mfCusCreditApply = mfCusCreditApplyFeign.getByWkfId(mfCusCreditApply);
		if (mfCusCreditApply==null) {
			MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
			mfCusCreditAdjustApply.setWkfAppId(wkfAppId);
			mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
			if (mfCusCreditAdjustApply!=null) {
				creditType = BizPubParm.CREDIT_TYPE_ADJUST;
				kindNo = "creditAdjust"+mfCusCreditAdjustApply.getCreditModel();
			}
		}else{
			kindNo = "credit"+mfCusCreditApply.getCreditModel();
			if ("2".equals(mfCusCreditApply.getProjectType())) {//非风险
				kindNo = "credit"+mfCusCreditApply.getCreditModel()+"-"+mfCusCreditApply.getProjectType();
			}
		}
		//获得授信合同信息
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(creditAppId);
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		mfCusCreditContract.setAuthBalShow(mfCusCreditContract.getAuthBal());
		mfCusCreditContract.setCreditSum(mfCusCreditContract.getAuthBal());
		mfCusCreditContract.setAddAmtShow(mfCusCreditContract.getAddAmt());

		mfCusCreditChildContract.setAgenciesUid(mfCusCreditContract.getAgenciesUid());
		mfCusCreditChildContract.setCreditAppId(creditAppId);
		mfCusCreditChildContract = mfCusCreditChildContractFeign.getById(mfCusCreditChildContract);
		if(BizPubParm.CREDIT_TYPE_ADD.equals(creditType)){
			mfCusCreditChildContract.setAuthBal(mfCusCreditContract.getAuthBal());
		}
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		//获得授信申请表单
		String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.child_pact_sign, null, null, User.getRegNo(request));
		FormData formcreditChildPactAdd = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		
		getObjValue(formcreditChildPactAdd, mfCusCreditContract);
		getObjValue(formcreditChildPactAdd, mfCusCreditChildContract);
		
		model.addAttribute("formcreditChildPactAdd", formcreditChildPactAdd);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("nodeNo", WKF_NODE.child_pact_sign.getNodeNo());
		model.addAttribute("creditType", creditType);
		model.addAttribute("query", query);
		return "/component/auth/MfCusCreditChildContract_Insert";
	}
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String creditAppId,String wkfAppId,String creditType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		try {
			dataMap=getMapByJson(ajaxData);
			//String formId = prdctInterfaceFeign.getFormId("credit", WKF_NODE.child_pact, null, null, User.getRegNo(request));
			FormData formcreditChildPactAdd = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcreditChildPactAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditChildPactAdd)){
				setObjValue(formcreditChildPactAdd, mfCusCreditChildContract);
				double authBal =mfCusCreditChildContract.getAuthBal();
				//authBal=MathExtend.subtract(authBal, mfCusCreditChildContract.getCreditSum());
				/*if (authBal<0) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("timeOne", "资金机构的合作额度");
					paramMap.put("timeTwo", "授信余额");
					dataMap.put("msg", MessageEnum.NOT_FORM_TIME.getMessage(paramMap));
					dataMap.put("flag", "overAmt");
					return dataMap;
				}*/
				mfCusCreditChildContract.setCreditType(creditType);
				mfCusCreditChildContract = mfCusCreditChildContractFeign.insert(mfCusCreditChildContract);
				/*MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
				mfCusCreditContract.setCreditAppId(creditAppId);
				mfCusCreditContract.setAuthBal(authBal);
				mfCusCreditContractFeign.update(mfCusCreditContract);*/
				//业务进入下一个流程
				TaskImpl task= wkfInterfaceFeign.getTaskWithUser(wkfAppId, null, User.getRegNo(request));
				String transition=workflowDwrFeign.findNextTransition(task.getId());
				Result result= wkfInterfaceFeign.doCommit(task.getId(),AppConstant.OPINION_TYPE_ARREE, "", transition, User.getRegNo(request), "");
				if(result!=null && result.isSuccess()){
					if(!result.isEndSts()){
						task= wkfInterfaceFeign.getTaskWithUser(wkfAppId, null, User.getRegNo(request));
						//更新业务阶段
						MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
						mfCusCreditApply.setCreditAppId(creditAppId);
						mfCusCreditApply.setBusStage(task.getDescription());
						mfCusCreditApply.setNodeNo(task.getActivityName());
						mfCusCreditApply.setLstModTime(DateUtil.getDateTime());
						mfCusCreditApplyFeign.update(mfCusCreditApply);
						result.setMsg(MessageEnum.SUCCEED_SUBMIT_TONEXT.getMessage(task.getDescription()));
						dataMap.put("flag", "success");
					}else{
						result.setMsg("流程已完成!");
						dataMap.put("flag", "finish");
					}
				}else{
					result.setMsg(MessageEnum.SUCCEED_OPERATION.getMessage());
					dataMap.put("flag", "error");
				}
				dataMap.put("msg",result.getMsg());
				/*if(authBal<=0){
				}else{
					dataMap.put("flag", "success");
					dataMap.put("msg",MessageEnum.SUCCEED_OPERATION.getMessage("子合同签约"));
				}*/
				
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/delete")
	public void delete(@RequestBody MfCusCreditChildContract mfCusCreditChildContract) throws Exception{
		mfCusCreditChildContractFeign.delete(mfCusCreditChildContract);
	}

	
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String creditAppId,String wkfAppId,String creditType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		try {
			MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
			dataMap=getMapByJson(ajaxData);
			//String formId = prdctInterfaceFeign.getFormId("credit", WKF_NODE.child_pact, null, null, User.getRegNo(request));
			FormData formpassSignBase = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formpassSignBase, getMapByJson(ajaxData));
			if(this.validateFormData(formpassSignBase)){
				setObjValue(formpassSignBase, mfCusCreditChildContract);
				setObjValue(formpassSignBase, mfCusCreditContract);
				
				double childCreditSum = mfCusCreditChildContract.getCreditSum();
				double childAddBal = mfCusCreditChildContract.getAddAmt();
				//获得立项项目关联的资金机构占用立项项目额度的总和
				MfCusCreditChildContract cusCreditChildContract = new MfCusCreditChildContract();
				cusCreditChildContract.setCreditAppId(creditAppId);
				List<MfCusCreditChildContract> cusCreditChildContractList = mfCusCreditChildContractFeign.getMfCusCreditChildContractList(cusCreditChildContract);
				if (cusCreditChildContractList!=null&&cusCreditChildContractList.size()>0) {
					for (int i = 0; i < cusCreditChildContractList.size(); i++) {
						cusCreditChildContract = cusCreditChildContractList.get(i);
						if (!cusCreditChildContract.getChildPactId().equals(mfCusCreditChildContract.getChildPactId())) {
							childCreditSum = MathExtend.add(childCreditSum, cusCreditChildContract.getCreditSum());
							if (cusCreditChildContract.getAddAmt()!=null){
								childAddBal = MathExtend.add(childAddBal, cusCreditChildContract.getAddAmt());
							}
						}
					}
				}
				double projectCreditSum =mfCusCreditContract.getCreditSumShow();//立项项目余额
				double authBal =mfCusCreditContract.getAuthBalShow();//立项项目余额
				if(null == mfCusCreditContract.getAddAmtShow()){
					mfCusCreditContract.setAddAmtShow(0.0);
				}
				double projectAddAmt =mfCusCreditContract.getAddAmtShow();//立项项目追加额度
				double creditSum = mfCusCreditContract.getCreditSum();//资金机构的合作额度
				double addAmt =mfCusCreditChildContract.getAddAmt();//追加额度
				if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {//资金机构项目额度追加调整
					//立项项目授信总额度减去立项相关关联的资金机构合同分配的总额度
					authBal=MathExtend.subtract(projectCreditSum, childCreditSum);
				}else if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
					authBal=MathExtend.subtract(projectCreditSum, childCreditSum);
					projectAddAmt = MathExtend.subtract(projectAddAmt, addAmt);
					if(addAmt<0){
						authBal = 0.00;
					}
				}else {
				}
				mfCusCreditChildContract.setCreditSts(BizPubParm.AUTH_STS_SIGN);
				mfCusCreditChildContract.setCreditType(creditType);
				mfCusCreditChildContractFeign.update(mfCusCreditChildContract);
				
				mfCusCreditContract = new MfCusCreditContract();
				mfCusCreditContract.setCreditAppId(creditAppId);
				mfCusCreditContract.setAuthBal(authBal);
				mfCusCreditContract.setAddAmt(projectAddAmt);
				mfCusCreditContractFeign.update(mfCusCreditContract);
			
				mfCusCreditContract = new MfCusCreditContract();
				mfCusCreditContract.setCreditAppId(creditAppId);
				mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
				Result result = null;
				//立项项目额度没有占用完且为风险类的授信业务，流程回到关联资金机构
				if (authBal>0&&"1".equals(mfCusCreditContract.getProjectType())) {
					//业务进入下一个流程
					result = wkfBusInterfaceFeign.doCommitNextStepByOpinionType(creditAppId, wkfAppId, AppConstant.OPINION_TYPE_RESTART, User.getRegNo(request));
					if (result.isSuccess()) {
						mfCusCreditApplyFeign.updateCreditBusStage(creditAppId, wkfAppId, User.getRegNo(request));
						dataMap.put("flag", "success");
					}else{
						dataMap.put("flag", "error");
					}
				}else if (projectAddAmt != 0 && "1".equals(mfCusCreditContract.getProjectType())){//没有占用完
					//业务进入下一个流程
					result = wkfBusInterfaceFeign.doCommitNextStepByOpinionType(creditAppId, wkfAppId, AppConstant.OPINION_TYPE_RESTART, User.getRegNo(request));
					if (result.isSuccess()) {
						mfCusCreditApplyFeign.updateCreditBusStage(creditAppId, wkfAppId, User.getRegNo(request));
						dataMap.put("flag", "success");
					}else{
						dataMap.put("flag", "error");
					}
				}else {
					//业务进入下一个流程
					TaskImpl task= wkfInterfaceFeign.getTaskWithUser(wkfAppId, null, User.getRegNo(request));
					String transition=workflowDwrFeign.findNextTransition(task.getId());
					result= wkfInterfaceFeign.doCommit(task.getId(),AppConstant.OPINION_TYPE_ARREE, "", transition, User.getRegNo(request), "");
					if(result!=null && result.isSuccess()){
						//更新业务阶段
						MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
						mfCusCreditApply.setCreditAppId(creditAppId);
						if(!result.isEndSts()){
							task= wkfInterfaceFeign.getTaskWithUser(wkfAppId, null, User.getRegNo(request));
							mfCusCreditApply.setBusStage(task.getDescription());
							mfCusCreditApply.setNodeNo(task.getActivityName());
							result.setMsg(MessageEnum.SUCCEED_SUBMIT_TONEXT.getMessage(task.getDescription()));
							dataMap.put("flag", "success");
						}else{
							mfCusCreditApply.setBusStage("已完成");
							mfCusCreditApply.setNodeNo("finish");
							result.setMsg("流程已完成!");
							dataMap.put("flag", "finish");
						}
						mfCusCreditApply.setLstModTime(DateUtil.getDateTime());
						mfCusCreditApplyFeign.update(mfCusCreditApply);
					}else{
						result.setMsg(MessageEnum.SUCCEED_OPERATION.getMessage());
						dataMap.put("flag", "error");
					}
				}
				dataMap.put("msg",result.getMsg());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	
	@RequestMapping(value = "/getById")
	public String getById(Model model,String childPactId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		mfCusCreditChildContract.setChildPactId(childPactId);
		mfCusCreditChildContract = mfCusCreditChildContractFeign.getById(mfCusCreditChildContract);
		
		/*MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCreditAppId(mfCusCreditChildContract.getCreditAppId());
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);*/
		
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(mfCusCreditChildContract.getCreditAppId());
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		//获得授信申请表单
		FormData formcreditChildPactDetail = formService.getFormData("creditChildPactDetail");//原始String.valueOf(dataMap.get("formId"))
		
		getObjValue(formcreditChildPactDetail, mfCusCreditChildContract);
		
		//getObjValue(formcreditChildPactDetail, mfCusCreditApply);
		getObjValue(formcreditChildPactDetail, mfCusCreditContract);
		model.addAttribute("formcreditChildPactDetail", formcreditChildPactDetail);
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("creditAppId", mfCusCreditContract.getCreditAppId());
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditChildContract_Detail";
	}

	
	@RequestMapping(value = "/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditChildContract")MfCusCreditChildContract mfCusCreditChildContract) throws Exception{
		return mfCusCreditChildContractFeign.findByPage(ipage, mfCusCreditChildContract);
	}
	
	@RequestMapping(value = "/getCreditChildContractByCusNo")
	public String getCreditChildContractByCusNo(Model model, String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcreditpact0001 = formService.getFormData("creditpact0001");
		 getFormValue(formcreditpact0001);
		MfCusCreditContract  mfCusCreditContract = new MfCusCreditContract();
		//mfCusCreditContract.setId(id);
		 mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
		 getObjValue(formcreditpact0001, mfCusCreditContract);
		model.addAttribute("formcreditpact0001", formcreditpact0001);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditContract_Detail";
	}
	/**
	 * 
	 * 方法描述： 立项合同
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年4月10日 上午11:59:09
	 */
	@RequestMapping(value = "/pactApplyInput")
	public String pactApplyInput(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		String query = "";
		String creditType = "";
		String creditAppId = request.getParameter("creditAppId");
		String wkfAppId = request.getParameter("wkfAppId");
		String kindNo = null;
		//
		//MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		//fCusCreditApply.setCreditAppId(creditAppId);
		//mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
 		MfCusCreditApply cusCreditApply = new MfCusCreditApply();
		cusCreditApply.setWkfAppId(wkfAppId);
		cusCreditApply = mfCusCreditApplyFeign.getByWkfId(cusCreditApply);
		if (cusCreditApply==null) {
			MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
			mfCusCreditAdjustApply.setWkfAppId(wkfAppId);
			mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
			if (mfCusCreditAdjustApply!=null) {
				creditType = BizPubParm.CREDIT_TYPE_ADJUST;
				kindNo = "creditAdjust"+mfCusCreditAdjustApply.getCreditModel();
			}
		}else{
			creditType = BizPubParm.CREDIT_TYPE_ADD;
			kindNo = "credit"+cusCreditApply.getCreditModel();
			if ("2".equals(cusCreditApply.getProjectType())) {//非风险
				kindNo = "credit"+cusCreditApply.getCreditModel()+"-"+cusCreditApply.getProjectType();
			}
		}
		//获得授信合同信息
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(creditAppId);
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		mfCusCreditContract.setCreditSum(null);
		mfCusCreditContract.setAgenciesName(null);
		mfCusCreditContract.setAgenciesUid(null);
		mfCusCreditContract.setAddAmtShow(mfCusCreditContract.getAddAmt());
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		//获得授信申请表单
		String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.child_pact, null, null, User.getRegNo(request));
		FormData formcreditChildPactAdd = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		
		//getObjValue(formcreditChildPactAdd, mfCusCreditApply);
		//getObjValue(formcreditChildPactAdd, mfCusCreditChildContract);
		getObjValue(formcreditChildPactAdd, mfCusCreditContract);
		model.addAttribute("formcreditChildPactAdd", formcreditChildPactAdd);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("nodeNo", WKF_NODE.child_pact.getNodeNo());
		model.addAttribute("creditType", creditType);
		model.addAttribute("query", query);
		return "/component/auth/MfCusCreditChildContract_applyInput";
	}
	/**
	 * 
	 * 方法描述： 法务上传合同资料
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年4月10日 下午12:06:19
	 */
	@RequestMapping(value = "/pactDataUpload")
	public String pactDataUpload(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		String query = "";
		String creditAppId = request.getParameter("creditAppId");
		String wkfAppId = request.getParameter("wkfAppId");
		MfCusCreditApproveInfo mfCusCreditApproveInfo = null;
		
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCreditAppId(creditAppId);
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		//获得授信审批主表信息
		if(StringUtil.isNotEmpty(mfCusCreditApply.getCreditProcessId())){
			mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
			mfCusCreditApproveInfo.setCreditAppId(creditAppId);
			mfCusCreditApproveInfo = mfCusCreditApproveInfoFeign.getById(mfCusCreditApproveInfo);
			if (mfCusCreditApproveInfo!=null) {
				mfCusCreditApproveInfo.setAuthBal(mfCusCreditApproveInfo.getCreditSum());
			}
		}
		
		//获得法务上传合同表单
		String kindNo = "credit"+mfCusCreditApply.getCreditModel();
		if ("2".equals(mfCusCreditApply.getProjectType())) {//非风险
			kindNo = "credit"+mfCusCreditApply.getCreditModel()+"-"+mfCusCreditApply.getProjectType();
		}
		String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.upload_pact_data, null, null, User.getRegNo(request));
		FormData formcreditChildPactAdd = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		
		getObjValue(formcreditChildPactAdd, mfCusCreditApply);
		if (mfCusCreditApproveInfo!=null) {
			getObjValue(formcreditChildPactAdd, mfCusCreditApproveInfo);
		}
		model.addAttribute("formcreditChildPactAdd", formcreditChildPactAdd);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditApply.getCusNo());
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("nodeNo", WKF_NODE.upload_pact_data.getNodeNo());
		model.addAttribute("query", query);
		return "/component/auth/MfCusCreditChildContract_pactDataUpload";
	}
	/**
	 * 
	 * 方法描述： 合同归档
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年4月10日 下午1:50:14
	 */
	@RequestMapping(value = "/pactDataRegular")
	public String pactDataRegular(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		String query = "";
		String creditAppId = request.getParameter("creditAppId");
		String wkfAppId = request.getParameter("wkfAppId");
		//获得授信合同信息
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(creditAppId);
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		mfCusCreditContract.setCreditSum(mfCusCreditContract.getAuthBal());
		
		mfCusCreditChildContract.setCreditAppId(creditAppId);
		List<MfCusCreditChildContract> mfCusCreditChildContractList = mfCusCreditChildContractFeign.getMfCusCreditChildContractList(mfCusCreditChildContract);
		if (mfCusCreditChildContractList!=null&&mfCusCreditChildContractList.size()>0) {
			mfCusCreditChildContract = mfCusCreditChildContractList.get(0);
			mfCusCreditChildContract.setAuthBal(mfCusCreditContract.getAuthBal());
		}
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		//获得授信申请表单
		String formId = prdctInterfaceFeign.getFormId("credit", WKF_NODE.pact_data_regular, null, null, User.getRegNo(request));
		FormData formcreditChildPactAdd = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		
		getObjValue(formcreditChildPactAdd, mfCusCreditContract);
		getObjValue(formcreditChildPactAdd, mfCusCreditChildContract);
		model.addAttribute("formcreditChildPactAdd", formcreditChildPactAdd);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("nodeNo", null);
		model.addAttribute("query", query);
		return "/component/auth/MfCusCreditChildContract_pactDataRegular";
	}
	
	@RequestMapping(value = "/submitPactDataRegularAjax")
	@ResponseBody
	public Map<String, Object> submitPactDataRegularAjax(String ajaxData,String creditAppId,String wkfAppId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		try {
			dataMap=getMapByJson(ajaxData);
			String formId = prdctInterfaceFeign.getFormId("credit", WKF_NODE.pact_data_regular, null, null, User.getRegNo(request));
			FormData formcreditChildPactAdd = formService.getFormData(formId);
			getFormValue(formcreditChildPactAdd, getMapByJson(ajaxData));
			setObjValue(formcreditChildPactAdd, mfCusCreditChildContract);
			double authBal =mfCusCreditChildContract.getAuthBal();
			Result result = null;
			if (authBal>0) {
				//业务进入下一个流程
				result = wkfBusInterfaceFeign.doCommitNextStepByOpinionType(creditAppId, wkfAppId, AppConstant.OPINION_TYPE_RESTART, User.getRegNo(request));
				if (result.isSuccess()) {
					dataMap.put("flag", "success");
				}else{
					dataMap.put("flag", "error");
				}
			}else {
				//业务进入下一个流程
				TaskImpl task= wkfInterfaceFeign.getTask(wkfAppId, null);
				String transition=workflowDwrFeign.findNextTransition(task.getId());
				result= wkfInterfaceFeign.doCommit(task.getId(),AppConstant.OPINION_TYPE_ARREE, "", transition, User.getRegNo(request), "");
				if(result!=null && result.isSuccess()){
					if(!result.isEndSts()){
						task= wkfInterfaceFeign.getTask(wkfAppId, null);
						result.setMsg(MessageEnum.SUCCEED_SUBMIT_TONEXT.getMessage(task.getDescription()));
						dataMap.put("flag", "success");
					}else{
						result.setMsg("流程已完成!");
						dataMap.put("flag", "finish");
					}
				}else{
					result.setMsg(MessageEnum.SUCCEED_OPERATION.getMessage());
					dataMap.put("flag", "error");
				}
			}
			dataMap.put("msg",result.getMsg());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getChildPactListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getChildPactListHtmlAjax(String ajaxData,String creditAppId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		try {
			String htmlStr = "";
			mfCusCreditChildContract.setCreditAppId(creditAppId);
			List<MfCusCreditChildContract> mfCusCreditChildContractList = mfCusCreditChildContractFeign.getMfCusCreditChildContractList(mfCusCreditChildContract);
			JsonTableUtil jtu = new JsonTableUtil();
			htmlStr = jtu.getJsonStr("tablecreditChildPactList", "tableTag", mfCusCreditChildContractList, null, true);
			dataMap.put("flag", "success");
			dataMap.put("msg",htmlStr);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getChildPactListAjax")
	@ResponseBody
	public Map<String, Object> getChildPactListAjax(String creditAppId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		try {
			mfCusCreditChildContract.setCreditAppId(creditAppId);
			List<MfCusCreditChildContract> mfCusCreditChildContractList = mfCusCreditChildContractFeign.getMfCusCreditChildContractList(mfCusCreditChildContract);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			if (mfCusCreditChildContractList!=null&&mfCusCreditChildContractList.size()>0) {
				for (int i = 0; i < mfCusCreditChildContractList.size(); i++) {
					jsonObject = new JSONObject();
					jsonObject.put("id", mfCusCreditChildContractList.get(i).getAgenciesUid());
					jsonObject.put("name", mfCusCreditChildContractList.get(i).getAgenciesName());
					jsonArray.add(jsonObject);
				}
			}
			dataMap.put("items", jsonArray.toArray());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 验证资金机构的调整的项目额度不能小于融资业务中已占用的项目额度
	 * @param creditAppId
	 * @param cusNoFund
	 * @param adjustAmt
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月3日 上午11:38:47
	 */
	@RequestMapping(value = "/checkCusNoFundAdjustAmtAjax")
	@ResponseBody
	public Map<String, Object> checkCusNoFundAdjustAmtAjax(String creditAppId,String cusNoFund,double adjustAmt) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = mfCusCreditChildContractFeign.checkCusNoFundAdjustAmt(creditAppId, cusNoFund, adjustAmt);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getCusNoFundProjectPactInfo")
	@ResponseBody
	public Map<String, Object> getCusNoFundProjectPactInfo(String creditAppId,String cusNoFund) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
			mfCusCreditChildContract.setCreditAppId(creditAppId);
			mfCusCreditChildContract.setAgenciesUid(cusNoFund);
			mfCusCreditChildContract = mfCusCreditChildContractFeign.getById(mfCusCreditChildContract);
			if (mfCusCreditChildContract!=null) {
				dataMap.put("mfCusCreditChildContract", mfCusCreditChildContract);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 打开追加资金机构合同
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年5月30日 下午5:46:54
	 */
	@RequestMapping(value = "/addChildPact")
	public String addChildPact(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		String query = "";
		String creditType = BizPubParm.CREDIT_TYPE_ADD;//授信类型1新增2调整追加
		String creditAppId = request.getParameter("creditAppId");
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCreditAppId(creditAppId);
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		if (mfCusCreditApply==null) {
			MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
			mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
			mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
			if (mfCusCreditAdjustApply!=null) {
				creditType = BizPubParm.CREDIT_TYPE_ADJUST;
			}
		}
		//获得授信合同信息
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(creditAppId);
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		mfCusCreditContract.setAuthBalShow(mfCusCreditContract.getAuthBal());
		mfCusCreditContract.setCreditSum(mfCusCreditContract.getAuthBal());
		//获得授信申请表单
		FormData formcreditChildPactAdd = formService.getFormData("addCreditChildPact");//原始String.valueOf(dataMap.get("formId"))
		mfCusCreditContract.setPactNo(null);
		getObjValue(formcreditChildPactAdd, mfCusCreditContract);
		model.addAttribute("formcreditChildPactAdd", formcreditChildPactAdd);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("nodeNo", WKF_NODE.child_pact.getNodeNo());
		model.addAttribute("creditType", creditType);
		model.addAttribute("query", query);
		return "/component/auth/MfCusCreditChildContract_AddChildPact";
	}
	/**
	 * 
	 * 方法描述： 追加合作资金机构保存
	 * @param ajaxData
	 * @param creditAppId
	 * @param wkfAppId
	 * @param creditType
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月30日 下午5:52:39
	 */
	@RequestMapping(value = "/saveAddChildPactInfoAjax")
	@ResponseBody
	public Map<String, Object> saveAddChildPactInfoAjax(String ajaxData,String creditAppId,String creditType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		try {
			dataMap=getMapByJson(ajaxData);
			FormData formcreditChildPactAdd = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcreditChildPactAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditChildPactAdd)){
				setObjValue(formcreditChildPactAdd, mfCusCreditChildContract);
				mfCusCreditChildContract.setCreditType(creditType);
				mfCusCreditChildContractFeign.insertPactInfo(mfCusCreditChildContract);
				mfCusCreditChildContract = new MfCusCreditChildContract();
				mfCusCreditChildContract.setCreditAppId(creditAppId);
				List<MfCusCreditChildContract> mfCusCreditChildContractList = mfCusCreditChildContractFeign.getMfCusCreditChildContractList(mfCusCreditChildContract);
				String htmlStr = "";
				JsonTableUtil jtu = new JsonTableUtil();
				htmlStr = jtu.getJsonStr("tablecreditChildPactList", "tableTag", mfCusCreditChildContractList, null, true);
				dataMap.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	/**
	
	 *@描述 项目减少额度校验额度是否超过项目合作资金机构合作额度
	
	 *@参数  creditAppId 授信申请号
	
	 *@返回值
	
	 *@创建人  shenhaobing
	
	 *@创建时间  2018/8/15
	
	 *@修改人和其它信息
	
	 */
	@RequestMapping(value = "/checkChildPactAuthBalAjax")
	@ResponseBody
	public Map<String, Object> checkChildPactAuthBalAjax(String ajaxData,String creditAppId,Double projectAddAmt) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditChildContract mfCusCreditChildContract = new MfCusCreditChildContract();
		try {
			Double authBal = 0.00;
			//获得立项项目关联的资金机构占用立项项目额度的总和
			MfCusCreditChildContract cusCreditChildContract = new MfCusCreditChildContract();
			cusCreditChildContract.setCreditAppId(creditAppId);
			List<MfCusCreditChildContract> cusCreditChildContractList = mfCusCreditChildContractFeign.getMfCusCreditChildContractList(cusCreditChildContract);
			if (cusCreditChildContractList!=null&&cusCreditChildContractList.size()>0) {
				for (int i = 0; i < cusCreditChildContractList.size(); i++) {
					cusCreditChildContract = cusCreditChildContractList.get(i);
					if (!cusCreditChildContract.getChildPactId().equals(mfCusCreditChildContract.getChildPactId())) {
						authBal = MathExtend.add(authBal, cusCreditChildContract.getAuthBal());
					}
				}
				//0：相等；1：大于；-1：小于
				//项目减少额度超过项目合作资金机构合作额度
				projectAddAmt = 0-projectAddAmt;
				if (MathExtend.comparison(projectAddAmt+"",authBal+"")==1){
					dataMap.put("flag", "error");
				}else{
					dataMap.put("flag", "success");
				}
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
}
