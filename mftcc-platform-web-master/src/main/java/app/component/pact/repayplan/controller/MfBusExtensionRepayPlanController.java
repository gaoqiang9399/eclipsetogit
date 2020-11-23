package app.component.pact.repayplan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.pact.extension.entity.MfBusExtensionApply;
import app.component.pact.extension.feign.MfBusExtensionApplyFeign;
import app.component.wkf.feign.WkfInterfaceFeign;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.entity.MfRepayPlanParameter;
import app.component.calccoreinterface.CalcRepayPlanInterfaceFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.finance.util.CwPublicUtil;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincAppChild;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppChildFeign;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.repayplan.feign.MfBusExtensionRepayPlanFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.pactinterface.PactRepayPlanInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;

/**
 * MfBusExtensionRepayPlanAction 展期还款计划
 * 
 * @author WD
 *
 */
@Controller
@RequestMapping("/mfBusExtensionRepayPlan")
public class MfBusExtensionRepayPlanController extends BaseFormBean {
	private PactInterfaceFeign pactInterfaceFeign;// 注入合同接口
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
//	private MfBusFincAppFeign mfBusFincAppFeign;// 注入借据接口
//	private CalcRepayPlanInterfaceFeign calcRepayPlanInterfaceFeign;// 注入规则接口
	@Autowired
	private MfBusExtensionRepayPlanFeign mfBusExtensionRepayPlanFeign;// 注入还款计划
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	private MfBusFincAppChildFeign mfBusFincAppChildFeign;// 注入借据子表
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private PactRepayPlanInterfaceFeign pactRepayPlanInterfaceFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusExtensionApplyFeign mfBusExtensionApplyFeign;
	/**
	 * 
	 * 方法描述： 还款计划页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @param extensionApplyId 
	 * @date 2017-09-16 上午10:39:07
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/extensionRepayPlanList")
	public String extensionRepayPlanList(Model model, String ajaxData, String extensionApplyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		String nodeNo = WKF_NODE.extension_review_finc.getNodeNo();// 功能节点编号

		List<MfRepayPlan> repayPlanList = new ArrayList<MfRepayPlan>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> mapParm = new HashMap<String, String>();
		Map<String, String> delayDateMap = new HashMap<String, String>();
		MfBusAppKind mfBusAppKind=null;
		MfBusAppKind mfBusAppKindShow=null;
		MfBusPact mfBusPact=null;
		MfBusFincAppChild mfBusFincAppChild=null;
		FormData formrepayplanlist0001=null;
		if (StringUtil.isNotEmpty(extensionApplyId)) {
			// 调用封装的还款计划方法
			dataMap = mfBusExtensionRepayPlanFeign.getExtensionRepayPlanList(extensionApplyId, mapParm);
			repayPlanList = (List<MfRepayPlan>) dataMap.get("repayPlanList");// 还款计划列表
			mfBusFincAppChild = (MfBusFincAppChild)JsonStrHandling.handlingStrToBean( dataMap.get("mfBusFincAppChild"), MfBusFincAppChild.class);
//			mfBusFincAppChild = (MfBusFincAppChild) dataMap.get("mfBusFincAppChild");// 借据实体
			mfBusPact = (MfBusPact)JsonStrHandling.handlingStrToBean( dataMap.get("mfBusPact"), MfBusPact.class);
//			mfBusPact = (MfBusPact) dataMap.get("mfBusPact");// 合同信息
			mfBusPact.setBeginDate(DateUtil.getShowDateTime(mfBusPact.getBeginDate()));// 合同开始日期

			String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.review_finc, null, null, User.getRegNo(request));
			 formrepayplanlist0001 = formService.getFormData(formId);
			 mfBusAppKind = (MfBusAppKind)JsonStrHandling.handlingStrToBean( dataMap.get("mfBusAppKind"), MfBusAppKind.class);
//			mfBusAppKind = (MfBusAppKind) dataMap.get("mfBusAppKind");// 参数实体
			Map<String, Object> dataAppKindShowMap = (Map<String, Object>) dataMap.get("dataAppKindShowMap");
			mfBusAppKindShow = (MfBusAppKind)JsonStrHandling.handlingStrToBean( dataAppKindShowMap.get("mfBusAppKindShow"), MfBusAppKind.class);
//			mfBusAppKindShow = (MfBusAppKind) dataAppKindShowMap.get("mfBusAppKindShow");// 参数展示实体
			delayDateMap = (Map<String, String>) dataMap.get("delayDateMap");// 顺延日期
			Gson gson = new Gson();
			request.setAttribute("repayPlanList", repayPlanList);
			request.setAttribute("delayDateMap", gson.toJson(delayDateMap));
			mfBusPact.setFincRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfBusPact.getFincRate(),
					Integer.parseInt(mfBusAppKind.getYearDays()),
					Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
			mfBusPact.setOverRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfBusPact.getOverRate(),
					Integer.parseInt(mfBusAppKind.getYearDays()),
					Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
		}
		getObjValue(formrepayplanlist0001, mfBusAppKind);
		getObjValue(formrepayplanlist0001, mfBusPact);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formrepayplanlist0001, "fincRate", "unit", rateUnit);
		model.addAttribute("formrepayplanlist0001", formrepayplanlist0001);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("mfBusAppKind", mfBusAppKind);
		model.addAttribute("mfBusAppKindShow", mfBusAppKindShow);
		model.addAttribute("mfBusFincAppChild", mfBusFincAppChild);
		model.addAttribute("fincId",mfBusFincAppChild.getFincId());
        model.addAttribute("appId", mfBusFincAppChild.getAppId());
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("query", "");
		model.addAttribute("projectName",ymlConfig.getSysParams().get("sys.project.name"));
		return "component/pact/repayplan/MfBusExtensionRepayPlan_ExtensionRepayPlanList";
	}

	/**
	 * 保存展期还款计划
	 * 
	 * @param extensionApplyId
	 * @param pageSize
	 * @param pageNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/extensionRepayPlanAjax")
	@ResponseBody
	public Map<String, Object> extensionRepayPlanAjax(String ajaxData, String extensionApplyId, Integer pageSize,
			Integer pageNo,String nodeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String beginDate = request.getParameter("beginDate");// 借据开始日期
		String endDate = request.getParameter("endDate");// 借据到期日期
		String planListData = request.getParameter("planListData");// 还款计划列表数据
		String planListSize = request.getParameter("planListSize");// 还款计划期数
		Map<String, String> formMap = CwPublicUtil.getMapByJson(planListData);
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(extensionApplyId);
		mfBusExtensionApply = mfBusExtensionApplyFeign.getById(mfBusExtensionApply);
		//判断当前节点是否与流程相符
		TaskImpl taskApprove = wkfInterfaceFeign.getTask(mfBusExtensionApply.getWkfAppId(), "");
		if(!nodeNo.equals(String.valueOf(taskApprove.getActivityName()))){//不相等则节点不在此环节
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
			return dataMap;
		}
		try {
			// 保存还款计划并流转到下一审批流程
			dataMap = mfBusExtensionRepayPlanFeign.doExtensionApprovalProcess(extensionApplyId, beginDate, endDate,
					formMap, planListSize,User.getRegNo(request));
			// 获取还款计划
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tablerepayplan0003", "tableTag", (List) dataMap.get("repayPlanList"),
					ipage, true);
			dataMap.put("tableHtml", tableHtml);
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("放款") + " 业务进入贷后阶段");
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("保存还款计划"));
		}
		return dataMap;
	}

	/**
	 * 到期日期重新调用获取还款计划
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExtensionPlanListByEndDateAjax")
	@ResponseBody
	public Map<String, Object> getExtensionPlanListByEndDateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String beginDate = request.getParameter("beginDate");// 借据开始日期
		String endDate = request.getParameter("endDate");// 到期日期
		String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");// 多次放款还款计划是否合并
																						// 1-启用、0-禁用
		String interestCollectType = request.getParameter("interestCollectType");// 利息收息方式：1-上收息
																					// 2-下收息
		String repayDateSet = request.getParameter("repayDateSet");// 还款日设置：1-贷款发放日
																	// 2-月末
																	// 3-固定还款日
		String yearDays = request.getParameter("yearDays");// 计息天数
		String normCalcType = request.getParameter("normCalcType");// 利息计算方式（按月计息：每月30天/按日计息：实际天数）1按月结息2按日结息
		String secondNormCalcType = request.getParameter("secondNormCalcType");// 不足期计息类型
																				// 1-按月计息|2-按实际天数计息
		String returnPlanPoint = request.getParameter("returnPlanPoint");// 保留小数位数
																			// 2-两位|1-一位|0-不保留
		String returnPlanRound = request.getParameter("returnPlanRound");// 还款计划舍入方式
																			// 2-四舍五入
		String instCalcBase = request.getParameter("instCalcBase");// 提前还款利息计算基数：1-按借据余额、2-按提前还款本金
		String preInstCollectType = request.getParameter("preInstCollectType");
		String repayDateDef = request.getParameter("repayDateDef");
		String rulesNo = request.getParameter("rulesNo");
		String appId = request.getParameter("appId");
		String extensionApplyId = request.getParameter("extensionApplyId");
		Map<String, String> mapParm = new HashMap<String, String>();
		MfRepayPlanParameter mfRepayPlanParameter = new MfRepayPlanParameter();
		try {
			if (extensionApplyId != null) {
				mapParm.put("extensionApplyId", extensionApplyId);
				mfRepayPlanParameter.setPutoutDate(DateUtil.getYYYYMMDD(beginDate));// 开始日期
				mfRepayPlanParameter.setEndDate(DateUtil.getYYYYMMDD(endDate));// 结束日期
				mfRepayPlanParameter.setRepayDayType(repayDateSet);// 还款日方式
																	// 1-固定还款日|2-随放款日
				mfRepayPlanParameter.setFixedRepayDayType(preInstCollectType);// 预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
				mfRepayPlanParameter.setFixedRepayDay(repayDateDef);// 固定还款日
				mfRepayPlanParameter.setIntstDays(yearDays);// 计息天数 （360/365）
				mfRepayPlanParameter.setNotAdequacyCalType(secondNormCalcType);// 不足期计息类型
																				// 1-按月计息|2-按实际天数计息
				mfRepayPlanParameter.setDecimalDigits(returnPlanPoint);// 保留小数位数
																		// 2-两位|1-一位|0-不保留
				mfRepayPlanParameter.setInterestReckonMode(normCalcType);// 计息方式
																			// 1按月结息2按日结息
				mfRepayPlanParameter.setIntstBase(instCalcBase);// 利息计算基础
																// (1-贷款金额;2-本金余额)
				mfRepayPlanParameter.setInterestCollectType(interestCollectType);// 利息收息方式：1-上收息
				// 2-下收息
				mfRepayPlanParameter.setMultipleLoanPlanMerge(multipleLoanPlanMerge);// 多次放款还款计划合并：1-启用、0-禁用
				mfRepayPlanParameter.setRounding(returnPlanRound);// 四舍五入2
				mfRepayPlanParameter.setRulesNo(rulesNo);// 规则唯一编号
				mapParm.put("appId", appId);
				dataMap = mfBusExtensionRepayPlanFeign.getExtensionPlanListByEndDate(mfRepayPlanParameter, mapParm);
				if (dataMap != null) {
					dataMap.put("flag", "success");
				} else {
					dataMap.put("flag", "error");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("修改到期日期获取还款计划"));
		}
		return dataMap;
	}

	/**
	 * 展期改变结束日期调用还款计划
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeExtensionPlanByEndDateAjax")
	@ResponseBody
	public Map<String, Object> changeExtensionPlanByEndDateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		String repayPlanListParm = request.getParameter("repayPlanList");// 还款计划List
		Gson gson = new Gson();
		List<MfRepayPlan> list = gson.fromJson(repayPlanListParm, new TypeToken<List<MfRepayPlan>>() {
		}.getType());
		String modTermNum = request.getParameter("modTermNum");// 修改当前期数
		String loanAmt = request.getParameter("loanAmt");// 借据金额
		String fincRate = request.getParameter("fincRate");// 年利率（执行利率）
		String intstModify = request.getParameter("intstModify");// 利息修改状态
																	// 0：未修改利息
																	// 1：已修改利息

		String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");// 多次放款还款计划是否合并
																						// 1-启用、0-禁用
		String interestCollectType = request.getParameter("interestCollectType");// 利息收息方式：1-上收息
																					// 2-下收息
		String repayDateSet = request.getParameter("repayDateSet");// 还款日设置：1-贷款发放日
																	// 2-月末
																	// 3-固定还款日
		String yearDays = request.getParameter("yearDays");// 计息天数
		String normCalcType = request.getParameter("normCalcType");// 利息计算方式（按月计息：每月30天/按日计息：实际天数）1按月结息2按日结息
		String secondNormCalcType = request.getParameter("secondNormCalcType");// 不足期计息类型
																				// 1-按月计息|2-按实际天数计息
		String returnPlanPoint = request.getParameter("returnPlanPoint");// 保留小数位数
																			// 2-两位|1-一位|0-不保留
		String returnPlanRound = request.getParameter("returnPlanRound");// 还款计划舍入方式
																			// 2-四舍五入
		String instCalcBase = request.getParameter("instCalcBase");// 提前还款利息计算基数：1-按借据余额、2-按提前还款本金
		String preInstCollectType = request.getParameter("preInstCollectType");
		String repayDateDef = request.getParameter("repayDateDef");
		String rulesNo = request.getParameter("rulesNo");
		String appId = request.getParameter("appId");

		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> mapParm = new HashMap<String, String>();
		MfRepayPlanParameter mfRepayPlanParameter = new MfRepayPlanParameter();
		mapParm.put("appId", appId);
		mapParm.put("modTermNum", modTermNum);
		mapParm.put("intstModify", intstModify);

		mfRepayPlanParameter.setFincRate(fincRate);
		mfRepayPlanParameter.setLoanAmt(Double.valueOf(loanAmt));
		mfRepayPlanParameter.setRepayDayType(repayDateSet);// 还款日方式
															// 1-固定还款日|2-随放款日
		mfRepayPlanParameter.setFixedRepayDayType(preInstCollectType);// 预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
		mfRepayPlanParameter.setFixedRepayDay(repayDateDef);// 固定还款日
		mfRepayPlanParameter.setIntstDays(yearDays);// 计息天数 （360/365）
		mfRepayPlanParameter.setNotAdequacyCalType(secondNormCalcType);// 不足期计息类型
																		// 1-按月计息|2-按实际天数计息
		mfRepayPlanParameter.setDecimalDigits(returnPlanPoint);// 保留小数位数
																// 2-两位|1-一位|0-不保留
		mfRepayPlanParameter.setInterestReckonMode(normCalcType);// 计息方式
																	// 1按月结息2按日结息
		mfRepayPlanParameter.setIntstBase(instCalcBase);// 利息计算基础
														// (1-贷款金额;2-本金余额)
		mfRepayPlanParameter.setInterestCollectType(interestCollectType);// 利息收息方式：1-上收息
																			// 2-下收息
		mfRepayPlanParameter.setMultipleLoanPlanMerge(multipleLoanPlanMerge);// 多次放款还款计划合并：1-启用、0-禁用
		mfRepayPlanParameter.setRounding(returnPlanRound);// 四舍五入2
		mfRepayPlanParameter.setRulesNo(rulesNo);
		try {
			// dataMap = mfBusExtensionRepayPlanFeign.getChangePlanByEndDate(list,
			// mfRepayPlanParameter, mapParm);
			if (dataMap != null) {
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("修改本期结束日期获取还款计划"));
		}
		return dataMap;
	}

}
