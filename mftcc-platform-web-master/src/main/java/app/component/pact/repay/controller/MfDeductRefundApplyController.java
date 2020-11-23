package app.component.pact.repay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.repay.entity.MfDeductRefundApply;
import app.component.pact.repay.feign.MfDeductRefundApplyFeign;
import app.component.pact.repay.feign.MfRepaymentFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import net.sf.json.JSONArray;

/**
 * Title: MfDeductRefundApplyAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 22 11:24:01 CST 2017
 **/
@Controller
@RequestMapping("/mfDeductRefundApply")
public class MfDeductRefundApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfDeductRefundApplyBo
	@Autowired
	private MfDeductRefundApplyFeign mfDeductRefundApplyFeign;
	@Autowired
	private MfRepaymentFeign mfRepaymentFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		// 申请状态
		CodeUtils codeUtils = new CodeUtils();
		JSONArray appStsJsonArray = codeUtils.getJSONArrayByKeyName("PRE_REPAY_APP_STS");
		JSONArray appTypeJsonArray = codeUtils.getJSONArrayByKeyName("APP_TYPE");
		model.addAttribute("appStsJsonArray", appStsJsonArray);
		model.addAttribute("appTypeJsonArray", appTypeJsonArray);
		model.addAttribute("query", "");
		return "component/pact/repay/MfDeductRefundApply_List";
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
			String ajaxData,Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfDeductRefundApply mfDeductRefundApply = new MfDeductRefundApply();
		try {
			mfDeductRefundApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfDeductRefundApply.setCriteriaList(mfDeductRefundApply, ajaxData);// 我的筛选
			mfDeductRefundApply.setCustomSorts(ajaxData);// 自定义排序
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfDeductRefundApply", mfDeductRefundApply));
			// 自定义查询Bo方法
			ipage = mfDeductRefundApplyFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取可以减免的借据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param pageSize
	 * @param pageNo
	 * @date 2017-8-26 上午9:16:22
	 */
	@RequestMapping(value = "/getDefundFincListAjax")
	@ResponseBody
	public Map<String, Object> getDefundFincListAjax(String ajaxData, Integer pageSize, Integer pageNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setCustomQuery(ajaxData);
			mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
			ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
			ipage = mfBusFincAppFeign.getDefundFincList(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取可以退费的借据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param pageSize
	 * @param pageNo
	 * @date 2017-8-26 上午9:16:22
	 */
	@RequestMapping(value = "/getRefundFincListAjax")
	@ResponseBody
	public Map<String, Object> getRefundFincListAjax(String ajaxData, Integer pageSize, Integer pageNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setCustomQuery(ajaxData);
			mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
			ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
			ipage = mfBusFincAppFeign.getRefundFincList(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @param flowFlag
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, String> insertAjax(String ajaxData, String flowFlag) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formdeductrefundapplyadd = formService.getFormData(formId);
			getFormValue(formdeductrefundapplyadd, getMapByJson(ajaxData));
			if (this.validateFormData(formdeductrefundapplyadd)) {
				MfDeductRefundApply mfDeductRefundApply = new MfDeductRefundApply();
				mfDeductRefundApply.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(formdeductrefundapplyadd, mfDeductRefundApply);
				dataMap = mfDeductRefundApplyFeign.insert(mfDeductRefundApply, flowFlag);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @param flowFlag
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String flowFlag) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formdeductrefundapplydetail = formService.getFormData(formId);
			getFormValue(formdeductrefundapplydetail, getMapByJson(ajaxData));
			if (this.validateFormData(formdeductrefundapplydetail)) {
				MfDeductRefundApply mfDeductRefundApply = new MfDeductRefundApply();
				mfDeductRefundApply.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(formdeductrefundapplydetail, mfDeductRefundApply);
				mfDeductRefundApplyFeign.update(mfDeductRefundApply, flowFlag);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfDeductRefundApply mfDeductRefundApply = new MfDeductRefundApply();
		mfDeductRefundApply.setId(id);
		try {
			mfDeductRefundApplyFeign.delete(mfDeductRefundApply);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formdeductrefundapplyadd = formService.getFormData("deductapplyadd");
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setFlowApprovalNo("derate_refund_approval");
		mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
		MfDeductRefundApply mfDeductRefundApply = new MfDeductRefundApply();
		mfDeductRefundApply.setFlowFlag(mfKindFlow.getUseFlag());
		mfDeductRefundApply.setFlowId(mfKindFlow.getFlowId());
		getObjValue(formdeductrefundapplyadd, mfDeductRefundApply);
		model.addAttribute("formdeductrefundapplyadd", formdeductrefundapplyadd);
		model.addAttribute("mfKindFlow", mfKindFlow);
		model.addAttribute("mfDeductRefundApply", mfDeductRefundApply);
		model.addAttribute("query", "");
		return "component/pact/repay/MfDeductRefundApply_Insert";
	}

	/**
	 * 
	 * 方法描述： 根据申请的类型，切换不同的表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param appType
	 * @param query
	 * @date 2017-8-22 下午4:23:14
	 */
	@RequestMapping(value = "/getFormHtmlAjax")
	@ResponseBody
	public Map<String, Object> getFormHtmlAjax(String appType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			MfDeductRefundApply mfDeductRefundApply = new MfDeductRefundApply();
			mfDeductRefundApply.setAppType(appType);
			MfKindFlow mfKindFlow = new MfKindFlow();
			mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
			mfKindFlow.setFlowApprovalNo("derate_refund_approval");
			mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
			mfDeductRefundApply.setFlowFlag(mfKindFlow.getUseFlag());
			mfDeductRefundApply.setFlowId(mfKindFlow.getFlowId());
			if ("1".equals(appType)) {// 减免
				FormData formdeductrefundapplyadd = formService.getFormData("deductapplyadd");
				getFormValue(formdeductrefundapplyadd);
				getObjValue(formdeductrefundapplyadd, mfDeductRefundApply);
				String htmlStr = jsonFormUtil.getJsonStr(formdeductrefundapplyadd, "bootstarpTag", "");
				dataMap.put("htmlStr", htmlStr);
			} else {// 退费
				FormData formdeductrefundapplyadd = formService.getFormData("refundapplyadd");
				getFormValue(formdeductrefundapplyadd);
				getObjValue(formdeductrefundapplyadd, mfDeductRefundApply);
				String htmlStr = jsonFormUtil.getJsonStr(formdeductrefundapplyadd, "bootstarpTag", "");
				dataMap.put("htmlStr", htmlStr);
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("切换表单"));
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 根据借据号获取借据的信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param fincId
	 * @date 2017-8-26 上午9:17:15
	 */
	@RequestMapping(value = "/getDeductFincInfoAjax")
	@ResponseBody
	public Map<String, Object> getDeductFincInfoAjax(String ajaxData, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 提前还款页面数据取值
			dataMap = mfRepaymentFeign.doRepaymentJsp(fincId);
			
			MfBusFincApp mfBusFincApp = (MfBusFincApp) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusFincApp"), MfBusFincApp.class);
			// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
			String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
			dataMap.put("rateUnit", rateUnit);
			// 合同金额格式化
			dataMap.put("pactAmt", MathExtend.moneyStr(mfBusFincApp.getPactAmt()));
			// 借据金额格式化
			dataMap.put("putoutAmt", MathExtend.moneyStr(mfBusFincApp.getPutoutAmt()));
			// 剩余金额格式化
			dataMap.put("loanBal", MathExtend.moneyStr(mfBusFincApp.getLoanBal()));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "选择借据");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 根据借据号获取借据的信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param fincId
	 * @date 2017-8-26 上午9:17:15
	 */
	@RequestMapping(value = "/getRefundFincInfoAjax")
	@ResponseBody
	public Map<String, Object> getRefundFincInfoAjax(String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfDeductRefundApplyFeign.getRefundFincInfo(fincId);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "选择借据");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formdeductrefundapplydetail = formService.getFormData("deductapplydetail");
		getFormValue(formdeductrefundapplydetail);
		MfDeductRefundApply mfDeductRefundApply = new MfDeductRefundApply();
		mfDeductRefundApply.setId(id);
		mfDeductRefundApply = mfDeductRefundApplyFeign.getById(mfDeductRefundApply);

		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(mfDeductRefundApply.getFincId());
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusFincApp.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

		getObjValue(formdeductrefundapplydetail, mfDeductRefundApply);
		getObjValue(formdeductrefundapplydetail, mfBusFincApp);
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
		this.changeFormProperty(formdeductrefundapplydetail, "fincRate", "unit", rateUnit);
		model.addAttribute("formdeductrefundapplydetail", formdeductrefundapplydetail);
		model.addAttribute("mfBusAppKind", mfBusAppKind);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfDeductRefundApply", mfDeductRefundApply);
		model.addAttribute("query", "");
		return "component/pact/repay/MfDeductRefundApply_Detail";
	}

	/**
	 * 
	 * 方法描述：跳转至审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param activityType 
	 * @date 2017-8-15 下午8:02:51
	 */
	@RequestMapping(value = "/getApprovalPage")
	public String getApprovalPage(Model model, String id, String activityType, String hideOpinionType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		String formId = "deductapplyapproval";
		MfDeductRefundApply mfDeductRefundApply = new MfDeductRefundApply();
		mfDeductRefundApply.setId(id);
		mfDeductRefundApply = mfDeductRefundApplyFeign.getById(mfDeductRefundApply);
		if ("2".equals(mfDeductRefundApply.getAppType())) {// 退费
			formId = "refundapplyapproval";
		}
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(mfDeductRefundApply.getFincId());
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		FormData formdeductrefundapplyadd = formService.getFormData(formId);
		getObjValue(formdeductrefundapplyadd, mfDeductRefundApply);
		getObjValue(formdeductrefundapplyadd, mfBusFincApp);
		// 处理审批意见类型
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formdeductrefundapplyadd, "opinionType", "optionArray", opinionTypeList);
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
		this.changeFormProperty(formdeductrefundapplyadd, "fincRate", "unit", rateUnit);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(id, null);
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formdeductrefundapplyadd", formdeductrefundapplyadd);
		model.addAttribute("scNo", scNo);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("formId", formId);
		model.addAttribute("query", "");
		return "component/pact/repay/MfDeductRefundApply_Approval";
	}

	/**
	 * 
	 * 方法描述： 申请审批提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param taskId
	 * @param id
	 * @param transition
	 * @param nextUser
	 * @date 2017-8-26 上午9:25:04
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String id, String transition,
			String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formdeductrefundapplyadd = formService.getFormData(formId);
		getFormValue(formdeductrefundapplyadd, map);
		MfDeductRefundApply mfDeductRefundApply = new MfDeductRefundApply();
		setObjValue(formdeductrefundapplyadd, mfDeductRefundApply);
		Result res;
		try {
			dataMap = getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			dataMap.put("orgNo", User.getOrgNo(request));
			res = mfDeductRefundApplyFeign.doCommit(taskId, id, opinionType, approvalOpinion, transition,
					User.getRegNo(this.request), nextUser, mfDeductRefundApply, dataMap);
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
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;
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
		FormData formdeductrefundapplydetail = formService.getFormData("deductrefundapplydetail");
		getFormValue(formdeductrefundapplydetail);
		boolean validateFlag = this.validateFormData(formdeductrefundapplydetail);
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
		FormData formdeductrefundapplydetail = formService.getFormData("deductrefundapplydetail");
		getFormValue(formdeductrefundapplydetail);
		boolean validateFlag = this.validateFormData(formdeductrefundapplydetail);
	}

}
