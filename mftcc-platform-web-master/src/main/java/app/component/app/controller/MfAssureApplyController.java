/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfAssureApplyAction.java
 * 包名： app.component.app.action
 * 说明：中汇鑫德-惠商通卡-太原业务
 * @author Javelin
 * @date 2017-6-5 上午10:17:11
 * @version V1.0
 */
package app.component.app.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.app.entity.MfBusApplyHis;
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
import com.core.struts.taglib.JsonFormUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplyAuditHis;
import app.component.app.feign.MfAssureApplyFeign;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfBusApplyHisFeign;
import app.component.app.feign.MfLoanApplyFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.examinterface.ExamInterfaceFeign;
import app.component.nmd.censor.entity.MfBusCensorBase;
import app.component.nmd.censor.entity.MfBusCensorBiz;
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sys.entity.SysUser;
import app.component.sysextendinterface.SysExtendInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 类名： MfAssureApplyAction
 * 描述：中汇鑫德-惠商通卡-太原业务
 * @author Javelin
 * @date 2017-6-5 上午10:17:11
 */
@Controller
@RequestMapping("/mfAssureApply")
public class MfAssureApplyController extends BaseFormBean {
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private MfAssureApplyFeign mfAssureApplyFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfLoanApplyFeign mfLoanApplyFeign;
	@Autowired
	private MfBusApplyHisFeign mfBusApplyHisFeign;
	@Autowired
	private CalcInterfaceFeign calcInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private SysExtendInterfaceFeign sysExtendInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private ExamInterfaceFeign examInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/**
	 * 方法描述： 进入 惠商通卡-太原 准入表单
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-6-5 上午11:42:33
	 */

	@RequestMapping(value = "/inputAssureApply")
	public String inputAssureApply(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		//中汇企业客户表单
		FormData formapplyzh00001 = formService.getFormData("assurecus00001");
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppTime(DateUtil.getDateTime());
		mfBusApply.setAppTimeShow(DateUtil.getDate());
		mfBusApply.setAppId(WaterIdUtil.getWaterId("app"));
		String appId = mfBusApply.getAppId();
		mfBusApply.setCusMngName(User.getRegName(request));

		getFormValue(formapplyzh00001);
		getObjValue(formapplyzh00001, mfBusApply);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formapplyzh00001, "bootstarpTag", "");
		dataMap.put("htmlStrCorp", htmlStrCorp);
		JSONObject jb = JSONObject.fromObject(dataMap);
		List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(new MfSysKind());
		String jsonStr = JSONArray.fromObject(mfSysKindList).toString();
		dataMap = jb;
		model.addAttribute("formapplyzh00001", formapplyzh00001);
		model.addAttribute("jsonStr", jsonStr);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "inputAssureApply";
	}

	/**
	 * 方法描述： 惠商通卡-太原 准入
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-6-5 上午11:59:50
	 */

	@RequestMapping(value = "/insertAssureApplyAjax")
	@ResponseBody
	public Map<String, Object> insertAssureApplyAjax(String ajaxData, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			FormData formapplyzh00001 = formService.getFormData("assurecus00001");
			String cusBaseType = map.get("cusType").toString().substring(0, 1);
			getFormValue(formapplyzh00001, map);

			if (this.validateFormData(formapplyzh00001)) {
				//开始处理客户信息
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				setObjValue(formapplyzh00001, mfCusCustomer);
				mfCusCustomer.setCusNo(cusNo);
				//处理业务内容
				MfBusApply mfBusApply = new MfBusApply();
				setObjValue(formapplyzh00001, mfBusApply);

				//个人客户
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();

				if ("2".equals(cusBaseType)) {
					setObjValue(formapplyzh00001, mfCusPersBaseInfo);
				}
				mfAssureApplyFeign.insertAssureApply(mfCusCustomer, mfCusPersBaseInfo, mfBusApply);

				dataMap.put("appId", mfBusApply.getAppId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
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

	/**
	 * 方法描述： 进入尽职调查表单
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-6-6 上午10:30:06
	 */

	@RequestMapping(value = "/inputSurveyForm")
	public String inputSurveyForm(String appId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formapplyzh00001 = formService.getFormData("assureapp00001");
		//获取业务与客户信息
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfBusApply.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		getObjValue(formapplyzh00001, mfCusCustomer);
		getObjValue(formapplyzh00001, mfBusApply);

		model.addAttribute("formapplyzh00001", formapplyzh00001);
		model.addAttribute("query", "");
		return "inputSurveyForm";
	}

	/**
	 * 方法描述： 完成尽职调查结果
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-6-6 上午10:37:45
	 */

	@RequestMapping(value = "/insertSurveyAjax")
	@ResponseBody
	public Map<String, Object> insertSurveyAjax(String ajaxData, String taskId, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusApply mfBusApply = new MfBusApply();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		FormData formapplyzh00001 = formService.getFormData("assureapp00001");
		getFormValue(formapplyzh00001, getMapByJson(ajaxData));
		setObjValue(formapplyzh00001, mfBusApply);
		setObjValue(formapplyzh00001, mfCusCustomer);
		dataMap = getMapByJson(ajaxData);
		Result res;
		try {
			res = mfAssureApplyFeign.insertSurvey(taskId, appId, dataMap, mfBusApply, mfCusCustomer);
			dataMap = new HashMap<String, Object>();
			if (res.isSuccess()) {
				dataMap.put("appId", appId);
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;

	}

	/**
	 * 方法描述： 进入审批页面
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-6-5 下午4:44:34
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String appId, String appStep, String hideOpinionType, String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		List<MfBusCensorBase> mfBusCensorBaseList = new ArrayList<MfBusCensorBase>();
		List<MfBusAppFee> mfBusAppFeeList = new ArrayList<MfBusAppFee>();
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(appId, null);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		mfBusApply.setReplyFincRate(mfBusApply.getFincRate());
		mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfBusApply.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		Double appAmt1 = mfBusApply.getAppAmt();
		String termShow = mfBusApply.getTermShow();
		String cmpdRateType = mfBusApply.getCmpFltRateShow();
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setAppId(appId);
		List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
		list = mfBusApplyHisFeign.getListByAppId(mfBusApplyHis);

		if (list != null && list.size() > 0) {
			mfBusApplyHis = list.get(0);
			PropertyUtils.copyProperties(mfBusApply, mfBusApplyHis);
		}

		mfBusApply.setAppAmt1(appAmt1);
		mfBusApply.setTermShow(termShow);
		mfBusApply.setCmpFltRateShow(cmpdRateType);
		//获得该申请相关的费用标准信息
		mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(appId, null, null);
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.apply_approval, appId, null,User.getRegNo(request));
		FormData formapplyzh00001 = formService.getFormData(formId);

		JSONObject json = new JSONObject();
		// 操作员
		List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
		JSONArray userJsonArray = new JSONArray();
		for (int i = 0; i < userList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", userList.get(i).getOpNo());
			jsonObject.put("name", userList.get(i).getOpName());
			userJsonArray.add(jsonObject);
		}
		json.put("userJsonArray", userJsonArray);
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
		for (int i = 0; i < map.size(); i++) {
			map.getJSONObject(i).put("id", map.getJSONObject(i).getString("optCode"));
			map.getJSONObject(i).put("name", map.getJSONObject(i).getString("optName"));
		}
		json.put("map", map);
		String ajaxData = json.toString();

		// 获得客户的授信额度信息
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCusNo(mfBusApply.getCusNo());
		mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
		Double tmpBal = 0.00;
		if (mfCusCreditApply != null) {
			tmpBal = mfCusCreditApply.getCreditSum();
		}

		//获取核心企业授信可用额度
		mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCusNo(mfBusApply.getCusNoCore());
		mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
		if (mfCusCreditApply == null) {
			mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setAuthBal(0.00);
		} else {
			mfCusCreditApply.setAuthBal(mfCusCreditApply.getCreditSum());
		}
		mfCusCreditApply.setCreditSum(tmpBal);
		//审批中各个节点需要的表单
		getObjValue(formapplyzh00001, mfCusCustomer);
		getObjValue(formapplyzh00001, mfBusApply);

		if ("1".equals(appStep)) {//担保审查表
			MfBusCensorBiz mfBusCensorBiz = new MfBusCensorBiz();
			mfBusCensorBiz.setKindNo(mfBusApply.getKindNo());
			mfBusCensorBiz.setNodeNo(taskAppro.getActivityName());
			mfBusCensorBaseList = nmdInterfaceFeign.getBusCensorBaseList(mfBusCensorBiz);
			if (mfBusCensorBaseList != null && mfBusCensorBaseList.size() > 0) {
				for (MfBusCensorBase censorBase : mfBusCensorBaseList) {
					censorBase.setShowCensorName(censorBase.getCensorName());
					censorBase.setShowItemName(censorBase.getItemName());
					censorBase.setCensorAgree("0");
				}
			}
		} else if ("2".equals(appStep)) {//贷审会
			ArrayList<MfBusApplyAuditHis> mfBusApplyAuditHisList = new ArrayList<MfBusApplyAuditHis>();
			MfBusApplyAuditHis mfBusApplyAuditHis = new MfBusApplyAuditHis();
			// mfBusApplyAuditHis.setReviewType("1");
			mfBusApplyAuditHisList.add(mfBusApplyAuditHis);
		}else {
		}

		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());//流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formapplyzh00001, "opinionType", "optionArray", opinionTypeList);

		//添加贷后检查模型数据
		List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
		this.changeFormProperty(formapplyzh00001, "templateId", "optionArray", examList);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
		this.changeFormProperty(formapplyzh00001, "term", "unit", termUnit);
		//处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
		this.changeFormProperty(formapplyzh00001, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formapplyzh00001, "overRate", "unit", rateUnit);
		//处理还款方式
		List<OptionsList> repayTypeList = getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
		this.changeFormProperty(formapplyzh00001, "repayType", "optionArray", repayTypeList);
		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(appId, null);
		// ServletActionContext.getRequest().setAttribute("befNodesjsonArray",
		// befNodesjsonArray);
		ActionContext.getActionContext().getRequest().setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formapplyzh00001", formapplyzh00001);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "point";
	}

	
	/**
	 * 方法描述： 审批提交
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-6-5 下午5:56:42
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String ajaxDataList, String appStep, String taskId, String appId, String transition, String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		FormData formapplyzh00001 = formService.getFormData((String) map.get("formId"));
		getFormValue(formapplyzh00001, map);
		MfBusApply mfBusApply = new MfBusApply();
		setObjValue(formapplyzh00001, mfBusApply);
		dataMap = getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
		List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(), new JsonConfig());

		dataMap.put("appStep", appStep);
		dataMap.put("ajaxDataList", jsonArray);

		Result res;
		try {
			mfBusApply = mfLoanApplyFeign.disProcessDataForApply(mfBusApply);
			mfBusApply.setCurrentSessionOrgNo(User.getOrgNo(request));
			res = mfAssureApplyFeign.submitUpdate(taskId, appId, opinionType, approvalOpinion, transition, User.getRegNo(this.getHttpRequest()), nextUser, mfBusApply, mfBusAppFeeList, dataMap);
			dataMap = new HashMap<String, Object>();
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;

	}

	/**
	 * 
	 * 方法描述： 获取还款方式选择项列表
	 * @param repayType
	 * @return
	 * @throws Exception
	 * List<OptionsList>
	 * @author zhs
	 * @date 2017-7-19 下午4:08:35
	 */
	private List<OptionsList> getRepayTypeList(String repayType, String repayTypeDef) throws Exception {
		String[] repayTypeArray = repayType.split("\\|");
		Map<String, String> dicMap = new CodeUtils().getMapByKeyName("REPAY_TYPE");
		List<OptionsList> repayTypeList = new ArrayList<OptionsList>();
		if (StringUtil.isNotEmpty(repayTypeDef)) {
			OptionsList op = new OptionsList();
			op.setOptionLabel(dicMap.get(repayTypeDef));
			op.setOptionValue(repayTypeDef);
			repayTypeList.add(op);
		}
		for (int i = 0; i < repayTypeArray.length; i++) {
			if (StringUtil.isNotEmpty(repayTypeDef)) {
				if (repayTypeArray[i].equals(repayTypeDef)) {
					continue;
				}
			}
			OptionsList op = new OptionsList();
			op.setOptionLabel(dicMap.get(repayTypeArray[i]));
			op.setOptionValue(repayTypeArray[i]);
			repayTypeList.add(op);
		}
		return repayTypeList;
	}

}
