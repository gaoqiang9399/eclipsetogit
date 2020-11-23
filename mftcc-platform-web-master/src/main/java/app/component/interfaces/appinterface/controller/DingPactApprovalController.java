package app.component.interfaces.appinterface.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.pact.entity.MfBusPactHis;
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
import app.component.appinterface.AppInterfaceFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.examinterface.ExamInterfaceFeign;
import app.component.interfaces.appinterface.entity.DingBusApproval;
import app.component.interfaces.appinterface.feign.DingBusApprovalFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincAppChild;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sys.entity.SysTaskInfo;
import app.component.sysextendinterface.SysExtendInterfaceFeign;
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
@RequestMapping("/dingPactApproval")
public class DingPactApprovalController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DingBusApprovalFeign dingBusApprovalFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
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
	 * 
	 * 方法描述：
	 * 进入审批视角（审批页面）{@link app.component.pact.action.MfBusPactWkfAction#getViewPoint()}
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-5-26 上午10:26:55
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String pactId, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(pactId, null);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		model.addAttribute("scNo", scNo);
		model.addAttribute("nodeNo", nodeNo);
		Map<String, String> map = new HashMap<String, String>();

		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		MfBusPactHis mfBusPactHis = new MfBusPactHis();
		mfBusPactHis.setPactId(pactId);
		String cusNo = mfBusPact.getCusNo();
		model.addAttribute("cusNo", cusNo);
		// 获取客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		// 处理原始的数据（利率转换）
		mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);

		Double pactAmtShow = mfBusPact.getPactAmt();// 原始的合同金额
		Double fincRateShow = mfBusPact.getFincRate();// 原始的合同利率
		String termShow = mfBusPact.getTermShow();// 原始的合同期限
		String cmpdRateType = mfBusPact.getCmpFltRateShow();

		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.contract_approval,
				mfBusPact.getAppId(), null, User.getRegNo(request));
		FormData formpact0005 = formService.getFormData(formId);

		List<MfBusPactHis> list = new ArrayList<MfBusPactHis>();
		list = pactInterfaceFeign.getListByPactId(mfBusPactHis);
		if (list != null && list.size() > 0) {
			mfBusPactHis = list.get(0);
			PropertyUtils.copyProperties(mfBusPact, mfBusPactHis);
		}
		// 处理审批历史的数据（利率转换）
		mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);
		mfBusPact.setPactAmtShow(pactAmtShow);
		mfBusPact.setFincRateShow(fincRateShow);
		mfBusPact.setTermShow(termShow);
		mfBusPact.setCmpFltRateShow(cmpdRateType);
		// 合同金额 开始日期 批复金额格式化
		String rawPactAmt = MathExtend.moneyStr(pactAmtShow);
		NumberFormat nf = NumberFormat.getInstance();
		// 不使用千分位，即展示为11672283.234，而不是11,672,283.234
		nf.setGroupingUsed(false);
		String approvePactAmt = nf.format(mfBusPact.getPactAmt());
		String beginDateShow = DateUtil.getShowDateTime(mfBusPact.getBeginDate());
		model.addAttribute("rawPactAmt", rawPactAmt);
		model.addAttribute("approvePactAmt", approvePactAmt);
		model.addAttribute("beginDateShow", beginDateShow);
		getObjValue(formpact0005, mfBusPact);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();
		this.changeFormProperty(formpact0005, "term", "unit", termUnit);
		model.addAttribute("termUnit", termUnit);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位,月利率百分号
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formpact0005, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formpact0005, "fincRateShow", "unit", rateUnit);
		this.changeFormProperty(formpact0005, "overRate", "unit", rateUnit);
		model.addAttribute("rateUnit", rateUnit);
		// 处理担保方式
		String vouTypeStr = "";
		Map<String, ParmDic> vouTypeMap = new CodeUtils().getMapObjByKeyName("VOU_TYPE");
		String[] vouTypeArray = mfBusPact.getVouType().split("\\|");
		if (null != vouTypeArray && vouTypeArray.length > 0) {
			for (int i = 0; i < vouTypeArray.length; i++) {
				String vouItem = vouTypeArray[i];
				if (StringUtil.isNotBlank(vouItem)) {
					String vouItemStr = vouTypeMap.get(vouItem).getOptName();
					vouTypeStr = vouTypeStr + vouItemStr + "|";
				}
			}
		}
		if (vouTypeStr.length() > 0) {
			vouTypeStr = vouTypeStr.substring(0, vouTypeStr.length() - 1);
		}
		model.addAttribute("vouTypeStr", vouTypeStr);
		// 处理贷款投向
		String fincUseStr = mfBusPact.getFincUse();
		Map<String, ParmDic> fincUseMap = new CodeUtils().getMapObjByKeyName("TRADE");
		fincUseStr = fincUseMap.get(fincUseStr).getOptName();
		model.addAttribute("fincUseStr", fincUseStr);
		// 处理还款方式
		String repayTypeStr = mfBusPact.getRepayType();
		Map<String, ParmDic> repayTypeMap = new CodeUtils().getMapObjByKeyName("REPAY_TYPE");
		repayTypeStr = repayTypeMap.get(repayTypeStr).getOptName();
		model.addAttribute("repayTypeStr", repayTypeStr);
		map.put("cusNo", cusNo);
		map.put("busModel", mfBusPact.getBusModel());

		JSONArray mapArray = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
		JSONObject json = new JSONObject();
		for (int i = 0; i < mapArray.size(); i++) {
			mapArray.getJSONObject(i).put("id", mapArray.getJSONObject(i).getString("optCode"));
			mapArray.getJSONObject(i).put("name", mapArray.getJSONObject(i).getString("optName"));
		}
		json.put("mapArray", mapArray);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		// 还款方式
		if (StringUtil.isNotBlank(mfBusPact.getRepayType())) {
			List<JSONObject> repayTypeList = new ArrayList<JSONObject>();
			JSONArray repayTypeArray = new CodeUtils().getJSONArrayByKeyName("REPAY_TYPE");
			JSONObject repayJson = new JSONObject();
			for (int i = 0; i < repayTypeArray.size(); i++) {
				if (mfBusPact.getRepayType().contains(repayTypeArray.getJSONObject(i).getString("optCode"))) {
					repayJson.put("id", repayTypeArray.getJSONObject(i).getString("optCode"));
					repayJson.put("name", repayTypeArray.getJSONObject(i).getString("optName"));
					repayTypeList.add(repayJson);
				}
			}
			dataMap.put("repayTypeList", repayTypeList);// 还款方式
		}
		// 获得该申请相关的费用标准信息
		List<MfBusAppFee> mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(mfBusPact.getAppId(), null, null);
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);
		scNo = BizPubParm.SCENCE_TYPE_DOC_PACT_APPROVAL;
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formpact0005, "opinionType", "optionArray", opinionTypeList);

		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(pactId, null);
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		dataMap.put("opinionTypeList", opinionTypeList);// 审批意见类型
		Gson gson = new Gson();
		String dataJson = gson.toJson(dataMap);
		model.addAttribute("dataJson", dataJson);
		model.addAttribute("formpact0005", formpact0005);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingPactApproval";
	}

	/**
	 * 
	 * 方法描述：
	 * 审批提交（审批意见保存）{@link app.component.pact.action.MfBusPactWkfAction#submitUpdateAjax()}
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-5-26 上午10:53:17
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String ajaxDataList, String pactId, String taskId,
			String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusPact mfBusPact = new MfBusPact();
		MfBusPact mfBusPacttmp = new MfBusPact();
		mfBusPacttmp.setPactId(pactId);
		mfBusPacttmp = pactInterfaceFeign.getById(mfBusPacttmp);
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formpact0005 = formService.getFormData(formId);

		getFormValue(formpact0005, map);
		setObjValue(formpact0005, mfBusPact);
		pactInterfaceFeign.disProcessDataForPact(mfBusPact);
		JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
		List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(),
				new JsonConfig());
		Result res;
		try {
			dataMap = getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			dataMap.put("termType", mfBusPacttmp.getTermType());
			res = pactInterfaceFeign.doCommit(taskId, pactId, opinionType, approvalOpinion, transition,
					User.getRegNo(request), nextUser, mfBusPact, mfBusAppFeeList, dataMap);
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
	 * 通过审批列表获得合同信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPagePactAjax")
	@ResponseBody
	public Map<String, Object> findByPagePactAjax(String pasSts, String pasMaxNo, String pasMinNo, int pageSize,
			int pageNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DingBusApproval dingBusApproval = new DingBusApproval();
		;
		try {
			// sysTaskInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			// sysTaskInfo.setCriteriaList(sysTaskInfo, ajaxData);//我的筛选
			// this.getRoleConditions(sysTaskInfo,"1000000001");//记录级权限控制方法
			String opNo = User.getRegNo(request);
			dingBusApproval.setPasSts(pasSts);
			dingBusApproval.setUserNo(opNo);
			dingBusApproval.setPasMaxNo(pasMaxNo);// "1"
			dingBusApproval.setPasMinNo(pasMinNo);// "109"
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = dingBusApprovalFeign.findByPagePact(ipage, dingBusApproval);
			List<DingBusApproval> dingBusApprovalList = (List<DingBusApproval>) ipage.getResult();
			dataMap.put("data", dingBusApprovalList);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 通过审批列表获得合同信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loanFindByPageAjax")
	@ResponseBody
	public Map<String, Object> loanFindByPageAjax(String pasSts, String pasMaxNo, String pasMinNo, int pageSize,
			int pageNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DingBusApproval dingBusApproval = new DingBusApproval();
		;
		try {
			// sysTaskInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			// sysTaskInfo.setCriteriaList(sysTaskInfo, ajaxData);//我的筛选
			// this.getRoleConditions(sysTaskInfo,"1000000001");//记录级权限控制方法
			String opNo = User.getRegNo(request);
			dingBusApproval.setPasSts(pasSts);
			dingBusApproval.setUserNo(opNo);
			dingBusApproval.setPasMaxNo(pasMaxNo);// "1"
			dingBusApproval.setPasMinNo(pasMinNo);// "109"
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = dingBusApprovalFeign.findByPagePact(ipage, dingBusApproval);
			List<DingBusApproval> dingBusApprovalList = (List<DingBusApproval>) ipage.getResult();
			dataMap.put("data", dingBusApprovalList);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 通过审批列表获得进件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
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
			sysTaskInfo.setPasMinNo("113");
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = dingBusApprovalFeign.findByPage1(ipage, sysTaskInfo);
			List<SysTaskInfo> sysTaskList = (List<SysTaskInfo>) ipage.getResult();
			Map<String, Object> appMap = new HashMap<String, Object>();
			Map<String, Object> relustMap = new HashMap<String, Object>();
			if (null != sysTaskList && sysTaskList.size() > 0) {
				List<MfBusFincAppChild> busFincList = pactInterfaceFeign.getTaskList(opNo);
				for (int i = 0; i < busFincList.size(); i++) {
					MfBusFincAppChild finApp = busFincList.get(i);
					String fincChildId = finApp.getFincChildId();
					/*
					 * if(!StringUtil.isBlank(busFincList.get(i).getAppTime())){
					 * String formatDate = DateUtil.getStr(apply.getAppTime());
					 * apply.setAppTime(formatDate); }
					 */
					appMap.put(fincChildId, busFincList.get(i));
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

	/**
	 * 合同审批页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getDingPactApproveList")
	public String getDingPactApproveList(Model model) {
		return "/component/interfaces/appinterface/DingPactApprove_List";
	}

}
