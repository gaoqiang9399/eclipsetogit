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
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditContractDetail;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditContractDetailFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.prdct.entity.MfKindNodeFee;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* Title: MfCusCreditContractDetailBo.java
* Description:客户授信申请业务操作控制
* @author:LJW
* @Mon Feb 27 10:43:09 CST 2017
**/
@Controller
@RequestMapping("/mfCusCreditContractDetail")
public class MfCusCreditContractDetailController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCreditContractDetailFeign mfCusCreditContractDetailFeign;
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		String query = "";
		String creditAppId = request.getParameter("creditAppId");
		String wkfAppId = request.getParameter("wkfAppId");
		//
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCreditAppId(creditAppId);
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		//获得授信合同信息
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(creditAppId);
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		mfCusCreditContract.setCreditSum(mfCusCreditContract.getAuthBal());
		
		mfCusCreditContractDetail.setCreditAppId(creditAppId);
		/*List<MfCusCreditContractDetail> mfCusCreditContractDetailList = mfCusCreditContractDetailFeign.getMfCusCreditContractDetailList(mfCusCreditContractDetail);
		if (mfCusCreditContractDetailList!=null&&mfCusCreditContractDetailList.size()>0) {
			mfCusCreditContractDetail = mfCusCreditContractDetailList.get(0);
			mfCusCreditContractDetail.setAuthBal(mfCusCreditContract.getAuthBal());
		}*/
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		//获得授信申请表单
		//String formId = prdctInterfaceFeign.getFormId("credit"+mfCusCreditApply.getCreditModel(), WKF_NODE.child_pact_sign, null, null, User.getRegNo(request));
		String formId = "PactDetail0001";
		FormData formPactDetail0001 = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		mfCusCreditContractDetail.setFirstParty(mfCusCreditApply.getCusName());
		mfCusCreditContractDetail.setCreditPactNo(mfCusCreditContract.getPactNo());
		getObjValue(formPactDetail0001, mfCusCreditContractDetail);
		getObjValue(formPactDetail0001, mfCusCreditApply);
		mfCusCreditContract.setPactNo(null);
		getObjValue(formPactDetail0001, mfCusCreditContract);
		
		model.addAttribute("formPactDetail0001", formPactDetail0001);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("creditAppId", creditAppId);
		//model.addAttribute("nodeNo", WKF_NODE.child_pact_sign.getNodeNo());
		model.addAttribute("query", query);
		return "/component/auth/MfCusCreditContractDetail_Insert";
	}
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String creditAppId,String wkfAppId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		try {
			dataMap=getMapByJson(ajaxData);
			FormData formcreditChildPactAdd = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcreditChildPactAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditChildPactAdd)){
				setObjValue(formcreditChildPactAdd, mfCusCreditContractDetail);
				mfCusCreditContractDetail = mfCusCreditContractDetailFeign.insert(mfCusCreditContractDetail);
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

	@RequestMapping(value = "/delete")
	public void delete(@RequestBody MfCusCreditContractDetail mfCusCreditContractDetail) throws Exception{
		mfCusCreditContractDetailFeign.delete(mfCusCreditContractDetail);
	}

	
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String creditAppId,String wkfAppId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		try {
			MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
			dataMap=getMapByJson(ajaxData);
			//String formId = prdctInterfaceFeign.getFormId("credit", WKF_NODE.child_pact, null, null, User.getRegNo(request));
			FormData formpassSignBase = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formpassSignBase, getMapByJson(ajaxData));
			if(this.validateFormData(formpassSignBase)){
				setObjValue(formpassSignBase, mfCusCreditContractDetail);
				setObjValue(formpassSignBase, mfCusCreditContract);
				mfCusCreditContractDetailFeign.update(mfCusCreditContractDetail);
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
	
	
	@RequestMapping(value = "/getById")
	public String getById(Model model,String childPactId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		String id = request.getParameter("id");
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		mfCusCreditContractDetail.setId(id);
		mfCusCreditContractDetail = mfCusCreditContractDetailFeign.getById(mfCusCreditContractDetail);
		
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(mfCusCreditContractDetail.getCreditAppId());
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		//获得授信申请表单
		String formId = "PactDetail0001";
		FormData formcreditPactDetail = formService.getFormData("PactDetail0001");//原始String.valueOf(dataMap.get("formId"))
		
		getObjValue(formcreditPactDetail, mfCusCreditContract);
		getObjValue(formcreditPactDetail, mfCusCreditContractDetail);
		model.addAttribute("formcreditPactDetail", formcreditPactDetail);
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("creditAppId", mfCusCreditContract.getCreditAppId());
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditContractDetail_Detail";
	}

	
	@RequestMapping(value = "/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditContractDetail")MfCusCreditContractDetail mfCusCreditContractDetail) throws Exception{
		return mfCusCreditContractDetailFeign.findByPage(ipage, mfCusCreditContractDetail);
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
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		String query = "";
		String creditAppId = request.getParameter("creditAppId");
		String wkfAppId = request.getParameter("wkfAppId");
		//
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCreditAppId(creditAppId);
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		//获得授信合同信息
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(creditAppId);
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		mfCusCreditContract.setCreditSum(mfCusCreditContract.getAuthBal());
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		//获得授信申请表单
		String formId = prdctInterfaceFeign.getFormId("credit"+mfCusCreditApply.getCreditModel(), WKF_NODE.child_pact, null, null, User.getRegNo(request));
		FormData formcreditChildPactAdd = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		
		//getObjValue(formcreditChildPactAdd, mfCusCreditApply);
		getObjValue(formcreditChildPactAdd, mfCusCreditContract);
		model.addAttribute("formcreditChildPactAdd", formcreditChildPactAdd);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("nodeNo", WKF_NODE.child_pact.getNodeNo());
		model.addAttribute("query", query);
		return "/component/auth/MfCusCreditContractDetail_applyInput";
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
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		String query = "";
		String creditAppId = request.getParameter("creditAppId");
		String wkfAppId = request.getParameter("wkfAppId");
		
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCreditAppId(creditAppId);
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		//获得授信合同信息
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(creditAppId);
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		mfCusCreditContract.setCreditSum(mfCusCreditContract.getAuthBal());
		
		mfCusCreditContractDetail.setCreditAppId(creditAppId);
		List<MfCusCreditContractDetail> mfCusCreditContractDetailList = mfCusCreditContractDetailFeign.getMfCusCreditContractDetailList(mfCusCreditContractDetail);
		if (mfCusCreditContractDetailList!=null&&mfCusCreditContractDetailList.size()>0) {
			mfCusCreditContractDetail = mfCusCreditContractDetailList.get(0);
			mfCusCreditContractDetail.setAuthBal(mfCusCreditContract.getAuthBal());
		}
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		//获得授信申请表单
		String formId = prdctInterfaceFeign.getFormId("credit"+mfCusCreditApply.getCreditModel(), WKF_NODE.upload_pact_data, null, null, User.getRegNo(request));
		FormData formcreditChildPactAdd = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		
		getObjValue(formcreditChildPactAdd, mfCusCreditContract);
		getObjValue(formcreditChildPactAdd, mfCusCreditContractDetail);
		//request.setAttribute("currbrNo", User.getOrgNo(request));
		model.addAttribute("formcreditChildPactAdd", formcreditChildPactAdd);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("nodeNo", WKF_NODE.upload_pact_data.getNodeNo());
		model.addAttribute("query", query);
		return "/component/auth/MfCusCreditContractDetail_pactDataUpload";
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
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		String query = "";
		String creditAppId = request.getParameter("creditAppId");
		String wkfAppId = request.getParameter("wkfAppId");
		//获得授信合同信息
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(creditAppId);
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		mfCusCreditContract.setCreditSumShow(mfCusCreditContract.getCreditSum());
		mfCusCreditContract.setCreditSum(mfCusCreditContract.getAuthBal());
		
		mfCusCreditContractDetail.setCreditAppId(creditAppId);
		List<MfCusCreditContractDetail> mfCusCreditContractDetailList = mfCusCreditContractDetailFeign.getMfCusCreditContractDetailList(mfCusCreditContractDetail);
		if (mfCusCreditContractDetailList!=null&&mfCusCreditContractDetailList.size()>0) {
			mfCusCreditContractDetail = mfCusCreditContractDetailList.get(0);
			mfCusCreditContractDetail.setAuthBal(mfCusCreditContract.getAuthBal());
		}
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		//获得授信申请表单
		String formId = prdctInterfaceFeign.getFormId("credit", WKF_NODE.pact_data_regular, null, null, User.getRegNo(request));
		FormData formcreditChildPactAdd = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		
		getObjValue(formcreditChildPactAdd, mfCusCreditContract);
		getObjValue(formcreditChildPactAdd, mfCusCreditContractDetail);
		model.addAttribute("formcreditChildPactAdd", formcreditChildPactAdd);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditContract.getCusNo());
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("nodeNo", null);
		model.addAttribute("query", query);
		return "/component/auth/MfCusCreditContractDetail_pactDataRegular";
	}
	
	@RequestMapping(value = "/submitPactDataRegularAjax")
	@ResponseBody
	public Map<String, Object> submitPactDataRegularAjax(String ajaxData,String creditAppId,String wkfAppId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		try {
			dataMap=getMapByJson(ajaxData);
			String formId = prdctInterfaceFeign.getFormId("credit", WKF_NODE.pact_data_regular, null, null, User.getRegNo(request));
			FormData formcreditChildPactAdd = formService.getFormData(formId);
			getFormValue(formcreditChildPactAdd, getMapByJson(ajaxData));
			setObjValue(formcreditChildPactAdd, mfCusCreditContractDetail);
			double authBal =mfCusCreditContractDetail.getAuthBal();
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
	
	@RequestMapping(value = "/getPactDetailListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getChildPactListHtmlAjax(String ajaxData,String creditAppId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		try {
			String htmlStr = "";
			mfCusCreditContractDetail.setCreditAppId(creditAppId);
			List<MfCusCreditContractDetail> mfCusCreditContractDetailList = mfCusCreditContractDetailFeign.getMfCusCreditContractDetailList(mfCusCreditContractDetail);
			JsonTableUtil jtu = new JsonTableUtil();
			htmlStr = jtu.getJsonStr("tablePactDetail0001", "tableTag", mfCusCreditContractDetailList, null, true);
			dataMap.put("flag", "success");
			dataMap.put("htmlStr",htmlStr);
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
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		try {
			mfCusCreditContractDetail.setCreditAppId(creditAppId);
			List<MfCusCreditContractDetail> mfCusCreditContractDetailList = mfCusCreditContractDetailFeign.getMfCusCreditContractDetailList(mfCusCreditContractDetail);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			if (mfCusCreditContractDetailList!=null&&mfCusCreditContractDetailList.size()>0) {
				for (int i = 0; i < mfCusCreditContractDetailList.size(); i++) {
					jsonObject = new JSONObject();
					//jsonObject.put("id", mfCusCreditContractDetailList.get(i).getAgenciesUid());
					//jsonObject.put("name", mfCusCreditContractDetailList.get(i).getAgenciesName());
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
	
	@RequestMapping(value = "/checkCusNoFundCreditAmtAjax")
	@ResponseBody
	public Map<String, Object> checkCusNoFundCreditAmtAjax(String childPactId,double appAmt) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		try {
			//dataMap = mfCusCreditContractDetailFeign.checkCusNoFundCreditAmt(childPactId, appAmt);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getChildPactListDataByAppId")
	@ResponseBody
	public Map<String,Object> getChildPactListDataByAppId(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
		List<MfCusCreditContractDetail> mfCusCreditContractDetailList =mfCusCreditContractDetailFeign.findByCreditAppId(appId);
		JSONArray array = new JSONArray();
		if(mfCusCreditContractDetailList!=null&&mfCusCreditContractDetailList.size()>0){
			JSONObject jsonObject = new JSONObject();
			for (int i = 0; i < mfCusCreditContractDetailList.size(); i++) {
				jsonObject.put("id", mfCusCreditContractDetailList.get(i).getPactNo());
				jsonObject.put("name", mfCusCreditContractDetailList.get(i).getPactNo());
				array.add(jsonObject);
			}
			dataMap.put("mfCusCreditContractDetailList", mfCusCreditContractDetailList);
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("items", array.toString());
		return dataMap;
	}

}
