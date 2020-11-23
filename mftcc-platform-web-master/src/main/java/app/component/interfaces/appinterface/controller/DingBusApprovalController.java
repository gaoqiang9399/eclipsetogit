package app.component.interfaces.appinterface.controller;

import java.text.NumberFormat;
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
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.examinterface.ExamInterfaceFeign;
import app.component.interfaces.appinterface.feign.DingBusApprovalFeign;
import app.component.nmd.entity.ParmDic;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.entity.SysUser;
import app.component.sysextendinterface.SysExtendInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/dingBusApproval")
public class DingBusApprovalController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DingBusApprovalFeign dingBusApprovalFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CalcInterfaceFeign calcInterfaceFeign;
	@Autowired
	private SysExtendInterfaceFeign sysExtendInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private ExamInterfaceFeign examInterfaceFeign;

	/**
	 * 融资原申请金额 格式化
	 */
	/**
	 * 融资审批金额 格式化
	 */

	// 获得审批列表，调用供应链接口
	/***
	 * 获得审批列表，调用供应链接口
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSysTaskListAjax")
	@ResponseBody
	public Map<String, Object> getSysTaskListAjax(String pasMaxNo, String pasMinNo, String userNo, String pasSts)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<SysTaskInfo> sysTaskList = dingBusApprovalFeign.getAllForWechat(pasMaxNo, pasMinNo, userNo, pasSts);
			Gson gson = new Gson();
			String sysTaskJson = gson.toJson(sysTaskList);
			dataMap.put("errorCode", "00000");
			dataMap.put("data", sysTaskJson);
			dataMap.put("errorMsg", "成功");
		} catch (Exception e) {
			// logger.error("移动端插入融资信息出错", e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 进入审批视角（审批页面）
	 * {@link app.component.app.action.MfBusApplyWkfAction#getViewPoint()}
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Mahao
	 * @date 2016-5-26 上午10:26:55
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String appId, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(appId, null);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		model.addAttribute("scNo", scNo);
		model.addAttribute("nodeNo", nodeNo);
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String mfBusApplyCusMngName = mfBusApply.getCusMngName();
		model.addAttribute("mfBusApplyCusMngName", mfBusApplyCusMngName);
		mfBusApply.setReplyFincRate(mfBusApply.getFincRate());
		mfBusApply = appInterfaceFeign.processDataForApply(mfBusApply);
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfBusApply.getCusNo());
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

		Double appAmt1 = mfBusApply.getAppAmt();
		/*
		 * if(BizPubParm.TERM_TYPE_MONTH.equals(termType)){//1月2天 termTypeStr =
		 * "个月"; }else if(BizPubParm.TERM_TYPE_DAY.equals(termType)){
		 * termTypeStr = "天"; }
		 */
		String termShow = mfBusApply.getTermShow();
		String cmpdRateType = mfBusApply.getCmpFltRateShow();
		Double appFincRate = mfBusApply.getFincRate();
		model.addAttribute("appFincRate", appFincRate);
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setAppId(appId);
		List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
		list = appInterfaceFeign.getApplyHisListByAppId(mfBusApplyHis);

		if (list != null && list.size() > 0) {
			mfBusApplyHis = list.get(0);
			PropertyUtils.copyProperties(mfBusApply, mfBusApplyHis);
		}
		// 格式化申请时间金额
		mfBusApply.setAppTimeShow(DateUtil.getShowDateTime(mfBusApply.getAppTimeShow()));
		mfBusApply.setAppAmt1(appAmt1);
		String rawAppAmt = MathExtend.moneyStr(appAmt1);
		model.addAttribute("rawAppAmt", rawAppAmt);
		NumberFormat nf = NumberFormat.getInstance();
		// 不使用千分位，即展示为11672283.234，而不是11,672,283.234
		nf.setGroupingUsed(false);
		String approveAppAmt = nf.format(mfBusApply.getAppAmt());
		model.addAttribute("approveAppAmt", approveAppAmt);
		mfBusApply.setTermShow(termShow);
		mfBusApply.setCmpFltRateShow(cmpdRateType);
		// 获得该申请相关的费用标准信息
		List<MfBusAppFee> mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(appId, null, null);
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);

		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.apply_approval, appId, null, User.getRegNo(request));
		model.addAttribute("formId", formId);
		FormData formapply0003 = formService.getFormData(formId);
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
		model.addAttribute("ajaxData", ajaxData);
		// 获得客户的授信额度信息
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCusNo(mfBusApply.getCusNo());
		mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
		Double tmpBal = 0.00;
		if (mfCusCreditApply != null) {
			tmpBal = mfCusCreditApply.getCreditSum();
		}

		// 获取核心企业授信可用额度
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
		getObjValue(formapply0003, mfCusCreditApply);
		getObjValue(formapply0003, mfBusApply);

		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);

		this.changeFormProperty(formapply0003, "opinionType", "optionArray", opinionTypeList);
		// 添加贷后检查模型数据
		List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
		this.changeFormProperty(formapply0003, "templateId", "optionArray", examList);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
		model.addAttribute("termUnit", termUnit);
		this.changeFormProperty(formapply0003, "term", "unit", termUnit);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
		model.addAttribute("rateUnit", rateUnit);
		this.changeFormProperty(formapply0003, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formapply0003, "overRate", "unit", rateUnit);
		// 处理还款方式
		List<OptionsList> repayTypeList = getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
		this.changeFormProperty(formapply0003, "repayType", "optionArray", repayTypeList);

		// 处理担保方式
		String vouTypeStr = "";
		Map<String, ParmDic> vouTypeMap = new CodeUtils().getMapObjByKeyName("VOU_TYPE");
		String[] vouTypeArray = mfBusApply.getVouType().split("\\|");
		if (null != vouTypeArray && vouTypeArray.length > 0) {
			for (int i = 0; i < vouTypeArray.length; i++) {
				String vouItem = vouTypeArray[i];
				if (StringUtil.isNotBlank(vouItem)) {
					String vouItemStr = vouTypeMap.get(vouItem).getOptName();
					vouTypeStr = vouTypeStr + vouItemStr + "|";
				}
			}
		}
		model.addAttribute("vouTypeStr", vouTypeStr);
		if (vouTypeStr.length() > 0) {
			vouTypeStr = vouTypeStr.substring(0, vouTypeStr.length() - 1);
		}

		// 处理贷款投向
		String fincUseStr = mfBusApply.getFincUse();
		Map<String, ParmDic> fincUseMap = new CodeUtils().getMapObjByKeyName("TRADE");
		fincUseStr = fincUseMap.get(fincUseStr).getOptName();
		model.addAttribute("fincUseStr", fincUseStr);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(appId, null);
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		dataMap.put("opinionTypeList", opinionTypeList);// 审批意见类型
		dataMap.put("repayTypeList", repayTypeList);// 还款方式
		Gson gson = new Gson();
		String dataJson = gson.toJson(dataMap);

		model.addAttribute("dataJson", dataJson);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingBusApproval";
	}

	/**
	 * 
	 * 方法描述： 审批提交（审批意见保存）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Mahao
	 * @date 2017-8-8 15:53:17
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String ajaxDataList, String taskId, String appId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		FormData formapply0003 = formService.getFormData((String) map.get("formId"));
		getFormValue(formapply0003, map);
		MfBusApply mfBusApply = new MfBusApply();
		setObjValue(formapply0003, mfBusApply);
		dataMap = getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		dataMap.put("opinionType", opinionType);
		dataMap.put("approvalOpinion", approvalOpinion);
		JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
		List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(), new JsonConfig());
		dataMap.put("mfBusAppFeeList", mfBusAppFeeList);
		Result res;
		try {
			mfBusApply = appInterfaceFeign.disProcessDataForApply(mfBusApply);
			res = appInterfaceFeign.doCommit(taskId, appId, opinionType, approvalOpinion, transition,
					User.getRegNo(request), nextUser, mfBusApply, mfBusAppFeeList, dataMap);
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
	 * 
	 * @param repayType
	 * @return
	 * @throws Exception
	 *             List<OptionsList>
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

	/**
	 * 通过审批列表获得进件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax1")
	@ResponseBody
	public Map<String, Object> findByPageAjax1(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String pasSts) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		;
		try {
			// sysTaskInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			// sysTaskInfo.setCriteriaList(sysTaskInfo, ajaxData);//我的筛选
			// this.getRoleConditions(sysTaskInfo,"1000000001");//记录级权限控制方法
			String opNo = User.getRegNo(request);
			sysTaskInfo.setPasSts(pasSts);
			sysTaskInfo.setUserNo(opNo);
			sysTaskInfo.setPasMaxNo("1");
			sysTaskInfo.setPasMinNo("110");
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = dingBusApprovalFeign.findByPage1(ipage, sysTaskInfo);
			List<SysTaskInfo> sysTaskList = (List<SysTaskInfo>) ipage.getResult();
			Map<String, Object> appMap = new HashMap<String, Object>();
			Map<String, Object> relustMap = new HashMap<String, Object>();
			if (null != sysTaskList && sysTaskList.size() > 0) {
				List<MfBusApply> applist = appInterfaceFeign.getTaskAppList(opNo);
				for (int i = 0; i < applist.size(); i++) {
					MfBusApply apply = applist.get(i);
					String appid = apply.getAppId();
					if (!StringUtil.isBlank(applist.get(i).getAppTime())) {
						String formatDate = DateUtil.getShowDateTime(apply.getAppTime());
						apply.setAppTime(formatDate);
					}
					appMap.put(appid, applist.get(i));
				}
				for (int j = 0; j < sysTaskList.size(); j++) {
					SysTaskInfo sysTask = sysTaskList.get(j);
					String pasUrl = sysTask.getPasUrl();
					pasUrl = pasUrl.substring(pasUrl.indexOf("?"));
					sysTask.setPasUrl(pasUrl);
					String appid = sysTask.getBizPkNo();
					relustMap.put(appid, appMap.get(appid));
				}
			}
			dataMap.put("data", sysTaskList);
			dataMap.put("appMap", relustMap);// key为业务编号，value为app实体
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getSysTaskApproveList")
	public String getSysTaskApproveList(Model model) {
		return "/component/interfaces/appinterface/DingSysTaskApprove_List";
	}


}
