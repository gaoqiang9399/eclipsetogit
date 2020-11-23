package app.component.interfaces.appinterface.controller;

import java.text.NumberFormat;
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
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;

import app.base.User;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppChild;
import app.component.pact.entity.MfBusFincAppHis;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 钉钉放款审批
 * 
 * @author MaHao
 * @date 2017-8-18 上午9:53:29
 */
@Controller
@RequestMapping("/dingFincApproval")
public class DingFincApprovalController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusFincAppBo
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	// @Autowired
	// private MfBusFincWkfFeign mfBusFincWkfFeign;
	@Autowired
	private CalcInterfaceFeign calcInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;

	/**
	 * 进入放款审批列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-22 下午5:16:58
	 */
	@RequestMapping(value = "/getDingFincApprovalList")
	public String getDingFincApprovalList(Model model) throws Exception {
		return "/component/interfaces/appinterface/DingFincApproval_List";
	}

	/**
	 * 钉钉放款审批详情页面
	 * {@link app.component.pact.action.MfBusFincAppAction#getViewPoint}
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-18 上午9:53:06
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String fincId, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		Map<String, String> map = new HashMap<String, String>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = pactInterfaceFeign.getByFincIdOrwkfId(mfBusFincApp);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		String cusNo = mfBusFincApp.getCusNo();
		model.addAttribute("cusNo", cusNo);
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

		String busModel = mfBusFincApp.getBusModel();
		MfBusFincAppHis mfBusFincAppHis = new MfBusFincAppHis();
		mfBusFincAppHis.setFincId(fincId);
		if (BizPubParm.BUS_MODEL_1.equals(busModel) || BizPubParm.BUS_MODEL_2.equals(busModel)) {// 动产质押业务
			map.put("cusNoWarehouse", mfBusFincApp.getCusNoWarehouse());// 仓储机构
		} else if (BizPubParm.BUS_MODEL_4.equals(busModel)) {// 保兑仓
			map.put("cusNoCore", mfBusFincApp.getCusNoCore());// 核心企业
		} else if (BizPubParm.BUS_MODEL_5.equals(busModel) || BizPubParm.BUS_MODEL_6.equals(busModel)) {// 保理
			map.put("cusNoCore", mfBusFincApp.getCusNoCore());
		}else {
		}

		map.put("cusNo", mfBusFincApp.getCusNo());
		map.put("busModel", mfBusFincApp.getBusModel());
		map.put("pactId", mfBusFincApp.getPactId());
		map.put("cusBaseType", mfCusCustomer.getCusBaseType());
		List<MfBusFincAppHis> list = pactInterfaceFeign.getFincListByFincId(mfBusFincAppHis);
		if (list != null && list.size() > 0) {
			mfBusFincAppHis = list.get(0);
			PropertyUtils.copyProperties(mfBusFincApp, mfBusFincAppHis);
		}
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(mfBusFincApp.getAppId());
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		// 合同金额 开始日期 批复金额格式化
		String formatPactAmt = MathExtend.moneyStr(mfBusPact.getPactAmt());
		String formatUsableFincAmt = MathExtend.moneyStr(mfBusPact.getUsableFincAmt());
		NumberFormat nf = NumberFormat.getInstance();
		// 不使用千分位，即展示为11672283.234，而不是11,672,283.234
		nf.setGroupingUsed(false);
		// 格式化可用金额，申请支用金额
		String formatPutoutAmt = nf.format(mfBusFincApp.getPutoutAmt());
		String approvePactAmt = nf.format(mfBusPact.getPactAmt());
		String approveUsableFincAmt = nf.format(mfBusPact.getUsableFincAmt());
		String beginDateShow = DateUtil.getShowDateTime(mfBusPact.getBeginDate());
		model.addAttribute("formatPactAmt", formatPactAmt);
		model.addAttribute("formatUsableFincAmt", formatUsableFincAmt);
		model.addAttribute("formatPutoutAmt", formatPutoutAmt);
		model.addAttribute("approvePactAmt", approvePactAmt);
		model.addAttribute("approveUsableFincAmt", approveUsableFincAmt);
		model.addAttribute("beginDateShow", beginDateShow);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位,月利率百分号
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		model.addAttribute("rateUnit", rateUnit);
		// 通过借据号获取子借据号
		MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
		mfBusFincAppChild.setFincId(mfBusFincApp.getFincId());// 借据id
		mfBusFincAppChild.setPutoutCount(mfBusFincApp.getPutoutCount());// 放款次数
		mfBusFincAppChild = pactInterfaceFeign.getMfBusFincAppChildByInfo(mfBusFincAppChild);
		model.addAttribute("mfBusFincAppChild", mfBusFincAppChild);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfBusFincAppChild.getFincChildId(), null);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		model.addAttribute("scNo", scNo);
		model.addAttribute("nodeNo", nodeNo);

		// if ("5".equals(busModel)) {
		// formfincapp0005 =
		// appInterfaceFeign.getAuditForm(mfBusPact.getAppId(),
		// mfBusFincAppChild.getFincChildId(), WKF_NODE.putout_approval,
		// "fincapp0005bl");
		// } else {
		// formfincapp0005 =
		// appInterfaceFeign.getAuditForm(mfBusPact.getAppId(),
		// mfBusFincAppChild.getFincChildId(), WKF_NODE.putout_approval,
		// "fincapp0005");
		// }
		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.putout_approval,
				mfBusPact.getAppId(), mfBusFincAppChild.getFincChildId(), User.getRegNo(request));
		FormData formfincapp0005 = formService.getFormData(formId);

		mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);

		MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
		if (StringUtil.isNotEmpty(mfBusFincApp.getBankAccId())) {
			mfCusBankAccManage.setId(mfBusFincApp.getBankAccId());
			mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
			if (mfCusBankAccManage != null) {
				mfBusPact.setBankAccId(mfBusFincApp.getBankAccId());
				mfBusPact.setIncomBank(mfCusBankAccManage.getBank());
				mfBusPact.setIncomAccountName(mfCusBankAccManage.getAccountName());
			}
		}
		if (StringUtil.isNotEmpty(mfBusFincApp.getCollectAccId())) {
			mfCusBankAccManage = new MfCusBankAccManage();
			mfCusBankAccManage.setId(mfBusFincApp.getCollectAccId());
			mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
			if (mfCusBankAccManage != null) {
				mfBusPact.setCollectAccId(mfBusFincApp.getCollectAccId());
				mfBusPact.setCollectBank(mfCusBankAccManage.getBank());
				mfBusPact.setCollectAccName(mfCusBankAccManage.getAccountName());
			}
		}
		if (StringUtil.isNotEmpty(mfBusFincApp.getRepayAccId())) {
			mfCusBankAccManage = new MfCusBankAccManage();
			mfCusBankAccManage.setId(mfBusFincApp.getRepayAccId());
			mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
			if (mfCusBankAccManage != null) {
				mfBusPact.setRepayAccId(mfBusFincApp.getRepayAccId());
				mfBusPact.setRepayBank(mfCusBankAccManage.getBank());
				mfBusPact.setRepayAccName(mfCusBankAccManage.getAccountName());
			}
		}
		mfBusPact.setPayMethod(mfBusFincApp.getPayMethod());
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

		getObjValue(formfincapp0005, mfBusFincAppChild);
		getObjValue(formfincapp0005, mfBusPact);

		// 获得该申请相关的费用标准信息
		List<MfBusAppFee> mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(mfBusFincApp.getAppId(), null, null);
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);

		map.put("appId", mfBusFincApp.getAppId());
		map.put("fincId", fincId);
		map.put("vouType", mfBusPact.getVouType());
		map.put("pactSts", mfBusPact.getPactSts());
		map.put("scNo", BizPubParm.SCENCE_TYPE_DOC_FINC_APPROVAL);
		// ViewUtil.setViewPointParm(request, map);
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formfincapp0005, "opinionType", "optionArray", opinionTypeList);
		request.setAttribute("opinionTypeList", new Gson().toJson(opinionTypeList));
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(mfBusFincAppChild.getFincChildId(), null);
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);

		// 客户的账户列表
		mfCusBankAccManage = new MfCusBankAccManage();
		mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
		mfCusBankAccManage.setUseFlag("1");
		List<MfCusBankAccManage> mfCusBankAccManageList = cusInterfaceFeign
				.getMfCusBankAccListByCusNo(mfCusBankAccManage);
		JSONArray bankArray = JSONArray.fromObject(mfCusBankAccManageList);
		for (int i = 0; i < bankArray.size(); i++) {
			bankArray.getJSONObject(i).put("name",
					bankArray.getJSONObject(i).getString("accountNo").replaceAll("([\\d]{4})(?=\\d)", "$1 "));
		}
		JSONObject json = new JSONObject();
		json.put("bankArray", bankArray);
		// 还款方式字典项
		JSONArray payMethodArray = new CodeUtils().getJSONArrayByKeyName("PAY_METHOD");
		json.put("payMethodArray", payMethodArray);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("formfincapp0005", formfincapp0005);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingFincApproval";
	}

	/**
	 * 方法描述：验证账号信息与合同中的是否一致
	 * {@link app.component.pact.action.MfBusFincAppAction#validateAccIsModifyAjax}
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-22 上午11:35:12
	 */
	@RequestMapping(value = "/validateAccIsModifyAjax")
	@ResponseBody
	public Map<String, Object> validateAccIsModifyAjax(String ajaxData,String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setAppId(appId);
			mfBusPact = pactInterfaceFeign.getById(mfBusPact);
			String msg = "";
			MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
			if (StringUtil.isNotEmpty(mfBusPact.getBankAccId()) && map.get("bankAccId") != null) {
				if (!mfBusPact.getBankAccId().equals(map.get("bankAccId"))) {
					mfCusBankAccManage.setId(map.get("bankAccId").toString());
					mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
					msg = msg + mfCusBankAccManage.getAccountNo() + ",";
				}
			}
			if (StringUtil.isNotEmpty(mfBusPact.getRepayAccId()) && map.get("repayAccId") != null) {
				if (!mfBusPact.getRepayAccId().equals(map.get("repayAccId"))) {
					mfCusBankAccManage = new MfCusBankAccManage();
					mfCusBankAccManage.setId(map.get("repayAccId").toString());
					mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
					msg = msg + mfCusBankAccManage.getAccountNo() + ",";
				}

			}
			if (StringUtil.isNotEmpty(mfBusPact.getCollectAccId()) && map.get("collectAccId") != null) {
				if (!mfBusPact.getCollectAccId().equals(map.get("collectAccId"))) {
					mfCusBankAccManage = new MfCusBankAccManage();
					mfCusBankAccManage.setId(map.get("collectAccId").toString());
					mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
					msg = msg + mfCusBankAccManage.getAccountNo() + ",";
				}
			}
			if (msg.length() == 0) {
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", msg.substring(0, msg.length() - 1) + "与合同中的账号不一致");
			}
		} catch (Exception e) {
//			logger.error("钉钉放款审批提交前校验银行卡和合同信息中的是否一致出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "验证账号信息与合同中的是否一致出错");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 放款审批提交 {@link app.component.pact.action.MfBusFincAppAction#updateProcess}
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-22 上午11:59:02
	 */
	 
	@RequestMapping(value = "/updateProcess")
	@ResponseBody
	public Map<String, Object> updateProcess(String ajaxData, String ajaxDataList, String fincId, String taskId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		MfBusFincApp mfBusFincApptmp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = pactInterfaceFeign.getByFincIdOrwkfId(mfBusFincApp);
		Map map = getMapByJson(ajaxData);
		//formfincapp0005 = formService.getFormData("fincapp0005bl");
		FormData formfincapp0005 = formService.getFormData((String) map.get("formId"));
//		if("5".equals(mfBusFincApp.getBusModel())){
//		}else{
		//	formfincapp0005 = formService.getFormData("fincapp0005");
//		}
		getFormValue(formfincapp0005, map);
		setObjValue(formfincapp0005, mfBusFincApptmp);
		mfBusFincApptmp.setWkfRepayId(mfBusFincApp.getWkfRepayId());
		mfBusFincApptmp.setRateType(mfBusFincApp.getRateType());
		JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
		List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>)JSONArray.toList(jsonArray, new MfBusAppFee(), new JsonConfig());
		try {
			dataMap=getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			mfBusFincApptmp = pactInterfaceFeign.disProcessDataForFinc(mfBusFincApptmp);
			
			Result result = pactInterfaceFeign.updateProcess(taskId, fincId, opinionType, approvalOpinion, transition, User.getRegNo(request), nextUser, mfBusFincApptmp,mfBusAppFeeList,dataMap);
			if(result.isSuccess()){
				dataMap.put("flag", "success");
				if(result.isEndSts()){
					dataMap.put("msg", result.getMsg());
				}else{
					dataMap.put("msg", result.getMsg());
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 放款审批提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-8-5 下午5:52:09
	 */
	@RequestMapping(value = "/processSubmitAjax")
	@ResponseBody
	public Map<String, Object> processSubmitAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// FIXME 和updateProcess区别？ 2017-07-01
		return dataMap;
	}

}
