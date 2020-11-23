package app.component.app.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.GuaranteeApplyFeign;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.feign.MfBusFincAppMainFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 担保业务action<br>
 * 
 * @author WangChao
 */
@Controller
@RequestMapping("/guaranteeApply")
public class GuaranteeApplyController extends BaseFormBean {
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private GuaranteeApplyFeign guaranteeApplyFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private CalcInterfaceFeign calcInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusAppFeeFeign mfBusAppFeeFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusFincAppMainFeign mfBusFincAppMainFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	/**
	 * 收费页面
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-5-24 上午10:44:48
	 */
	@RequestMapping(value = "/fee")
	public String fee(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = WKF_NODE.fee.getScenceTypeDoc();// 要件场景
		// nodeNo = WKF_NODE.fee.getNodeNo();// 功能节点编号
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		mfBusApply = appInterfaceFeign.processDataForApply(mfBusApply);

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(appId);
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

		TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
		String nodeNo = task.getActivityName();
		// 北京暂定两个费用节点的表单一样
		//由于支持审批阶段产品变更，故需要取合同的产品
		//获取合同信息
		String kindNo = mfBusApply.getKindNo();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact = pactInterfaceFeign.getByAppId(appId);
		if (mfBusPact!=null){
			kindNo = mfBusPact.getKindNo();
			mfBusApply.setKindNo(kindNo);
			mfBusApply.setKindName(mfBusPact.getKindName());
		}
		String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.fee, null, null, User.getRegNo(request));
		FormData formappzhlf0004 = formService.getFormData(formId);

		getFormValue(formappzhlf0004);
		getObjValue(formappzhlf0004, mfBusApply);

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusAppKind.getRateType()).getRemark();
		this.changeFormProperty(formappzhlf0004, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formappzhlf0004, "overRate", "unit", rateUnit);
		model.addAttribute("formappzhlf0004", formappzhlf0004);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("appId", appId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("query", "");
		return "/component/app/guaranteeApply_fee";
	}

	/**
	 * 收费提交
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-5-24 上午11:44:56
	 */
	@RequestMapping(value = "/feeSubmitAjax")
	@ResponseBody
	public Map<String, Object> feeSubmitAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);

		Map<String, Object> map = getMapByJson(ajaxData);
		map.put("opNo", User.getRegNo(request));
		map.put("opName", User.getRegName(request));
		map.put("brNo", User.getOrgNo(request));
		map.put("brName", User.getOrgName(request));
		Result result = guaranteeApplyFeign.doFeeSubmit(map);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("appId", map.get("appId"));
		dataMap.put("msg", result.getMsg());
		dataMap.put("flag", "success");

		return dataMap;
	}

	/**
	 * 放款确认、发卡，页面
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-5-24 上午10:44:48
	 */
	@RequestMapping(value = "/loanConfirm")
	public String loanConfirm(Model model, String appId, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = WKF_NODE.loan_confirm.getScenceTypeDoc();// 要件场景
		String nodeNo = WKF_NODE.loan_confirm.getNodeNo();// 功能节点编号

		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);

		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact = pactInterfaceFeign.getByAppId(appId);

		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.loan_confirm, null, null, User.getRegNo(request));
		FormData formData = formService.getFormData(formId);

		if (StringUtil.isNotEmpty(fincId)) {// 借据数据
			MfBusFincApp bfa = new MfBusFincApp();
			bfa.setFincId(fincId);
			bfa = pactInterfaceFeign.getFincAppById(bfa);
			getObjValue(formData, bfa);
		}
		getObjValue(formData, mfBusApply);
		getObjValue(formData, mfBusPact);

		model.addAttribute("formData", formData);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("scNo", scNo);
		model.addAttribute("query", "");
		model.addAttribute("appId", appId);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("mfBusPact", mfBusPact);
		return "/component/app/guaranteeApply_loanConfirm";
	}

	/**
	 * 放款确认、发卡，提交
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-5-24 上午11:44:56
	 */
	@RequestMapping(value = "/loanConfirmSubmitAjax")
	@ResponseBody
	public Map<String, Object> loanConfirmSubmitAjax(String appId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> map = getMapByJson(ajaxData);
		map.put("regNo", User.getRegNo(request));
		Result result = guaranteeApplyFeign.doLoanConfirmSubmit(appId, map);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("appId", appId);
		dataMap.put("msg", result.getMsg());
		dataMap.put("flag", "success");

		return dataMap;
	}

	/**
	 * 发放确认。合并放款申请放款复核功能，提交后进入贷后
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-7-24 上午10:59:06
	 */
	@RequestMapping(value = "/mergeLoan")
	public String mergeLoan(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String nodeNo = WKF_NODE.mergeLoan.getNodeNo();// 功能节点编号

		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);

		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact = pactInterfaceFeign.getByAppId(appId);

		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.mergeLoan, null, null, User.getRegNo(request));
		FormData formData = formService.getFormData(formId);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = calcInterfaceFeign.getFeeInfo(mfBusPact);

		getObjValue(formData, dataMap);
		getObjValue(formData, mfBusApply);
		getObjValue(formData, mfBusPact);
		model.addAttribute("formData", formData);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("query", "");
		return "/component/app/guaranteeApply_mergeLoan";
	}

	/**
	 * 发放确认。合并放款申请放款复核功能，提交后进入贷后，提交
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-5-24 上午11:44:56
	 */
	@RequestMapping(value = "/mergeLoanSubmitAjax")
	@ResponseBody
	public Map<String, Object> mergeLoanSubmitAjax(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> map = getMapByJson(ajaxData);

		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);

		FormData formData = formService.getFormData((String) map.get("formId"));
		getFormValue(formData, map);

		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		setObjValue(formData, mfBusFincApp);
		guaranteeApplyFeign.doMergeLoanSubmit(mfBusFincApp, appId, map);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("appId", appId);
		dataMap.put("msg", MessageEnum.SUCCEED_COMMIT.getMessage());
		dataMap.put("flag", "success");

		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 跳转费用录入页面
	 * @param model
	 * @param appId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年5月8日 上午9:49:52
	 */
	@RequestMapping(value = "/feeCollect")
	public String feeCollect(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = WKF_NODE.fee.getScenceTypeDoc();// 要件场景
		// nodeNo = WKF_NODE.fee.getNodeNo();// 功能节点编号
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		mfBusApply = appInterfaceFeign.processDataForApply(mfBusApply);
		
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(appId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
		
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(appId);
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

		TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
		String nodeNo = WKF_NODE.fee_collect.getNodeNo();
		// 北京暂定两个费用节点的表单一样
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.fee_collect, null, null, User.getRegNo(request));
		FormData formappzhlf0004 = formService.getFormData(formId);
		//获得授信信息
		Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(appId);
		if (!creditData.isEmpty()) {
			getObjValue(formappzhlf0004, creditData);
			mfBusPact.setCreditPactNo((String) creditData.get("creditPactNo"));
		}
		getFormValue(formappzhlf0004);
		//getObjValue(formappzhlf0004, mfBusApply);
		getObjValue(formappzhlf0004, mfBusPact);

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusAppKind.getRateType()).getRemark();
		this.changeFormProperty(formappzhlf0004, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formappzhlf0004, "overRate", "unit", rateUnit);
		model.addAttribute("formappzhlf0004", formappzhlf0004);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("appId", appId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("query", "");
		return "/component/app/guaranteeApply_feeCollect";
	}
	/**
	 * 
	 * 方法描述： 费用录入保存提交
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月8日 上午9:49:35
	 */
	@RequestMapping(value = "/feeCollectSubmitAjax")
	@ResponseBody
	public Map<String, Object> feeCollectSubmitAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData feeCollectBase = formService.getFormData(formId);
		getFormValue(feeCollectBase, getMapByJson(ajaxData));
		MfBusPact mfBusPact = new MfBusPact();
		setObjValue(feeCollectBase, mfBusPact);
		map.put("opNo", User.getRegNo(request));
		map.put("opName", User.getRegName(request));
		map.put("brNo", User.getOrgNo(request));
		map.put("brName", User.getOrgName(request));
		
		map.put("mfBusPact", mfBusPact);
		Result result = guaranteeApplyFeign.doFeeCollectSubmit(map);
		if (result!=null) {
			dataMap.put("appId", map.get("appId"));
			dataMap.put("msg", result.getMsg());
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 跳转到财务确认费用
	 * @param model
	 * @param appId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年5月8日 上午9:49:14
	 */
	@RequestMapping(value = "/feeCollectConfirm")
	public String feeCollectConfirm(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = WKF_NODE.fee.getScenceTypeDoc();// 要件场景
		// nodeNo = WKF_NODE.fee.getNodeNo();// 功能节点编号
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		mfBusApply = appInterfaceFeign.processDataForApply(mfBusApply);

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(appId);
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(appId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		//TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
		String nodeNo = WKF_NODE.fee_collect_confirm.getNodeNo();
		// 北京暂定两个费用节点的表单一样
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.fee_collect_confirm, null, null, User.getRegNo(request));
		FormData formappzhlf0004 = formService.getFormData(formId);
		MfBusAppFee busAppFee = new MfBusAppFee();
		busAppFee.setAppId(appId);
		List<MfBusAppFee> busAppFeeList = mfBusAppFeeFeign.getMfBusAppFeeList(busAppFee);
		if (busAppFeeList!=null&&busAppFeeList.size()>0) {
			String ext3 = "";
			for (int i = 0; i < busAppFeeList.size(); i++) {
				if (StringUtil.isNotEmpty(busAppFeeList.get(i).getFeeMainNo())) {
					busAppFee = busAppFeeList.get(i);
					ext3 = ext3+"|"+busAppFee.getItemName();
				}
			}
			if (StringUtil.isNotEmpty(ext3)) {
				ext3 = ext3.substring(1, ext3.length());
			}
			busAppFee.setExt3(ext3);
		}
		getFormValue(formappzhlf0004);
		//获得授信信息
		Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(appId);
		if (!creditData.isEmpty()) {
			getObjValue(formappzhlf0004, creditData);
			mfBusPact.setCreditPactNo((String) creditData.get("creditPactNo"));
		}
		//getObjValue(formappzhlf0004, mfBusApply);
		getObjValue(formappzhlf0004, busAppFee);
		getObjValue(formappzhlf0004, mfBusPact);
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setPactId(mfBusPact.getPactId());
		Ipage ipage = new Ipage();
		ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
		ipage = mfBusFincAppFeign.findLoanAfterByPage(ipage);
		List<MfBusFincApp> mfBusFincAppList = (List<MfBusFincApp>) ipage.getResult();
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusAppKind.getRateType()).getRemark();
		this.changeFormProperty(formappzhlf0004, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formappzhlf0004, "overRate", "unit", rateUnit);
		model.addAttribute("formappzhlf0004", formappzhlf0004);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("mfBusFincAppList", mfBusFincAppList);
		model.addAttribute("appId", appId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("query", "");
		return "/component/app/guaranteeApply_feeCollectConfirm";
	}
	/**
	 * 
	 * 方法描述： 财务确认提交
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月8日 上午9:49:00
	 */
	@RequestMapping(value = "/feeCollectConfirmSubmitAjax")
	@ResponseBody
	public Map<String, Object> feeCollectConfirmSubmitAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		Map<String, Object> map = getMapByJson(ajaxData);
		FormData formpact0002 = formService.getFormData((String) map.get("formId"));
		getFormValue(formpact0002, getMapByJson(ajaxData));
		if (this.validateFormData(formpact0002)) {
			MfBusPact mfBusPact = new MfBusPact();
			setObjValue(formpact0002, mfBusPact);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusPact);
			map.put("mfBusPact", mfBusPact);
			map.put("opNo", User.getRegNo(request));
			map.put("opName", User.getRegName(request));
			map.put("brNo", User.getOrgNo(request));
			map.put("brName", User.getOrgName(request));
			Result result = guaranteeApplyFeign.doFeeCollectConfirmSubmit(map);
			if (result!=null) {
				if (result.isSuccess()) {
					dataMap.put("flag", "success");
					if (result.isEndSts()) {
						dataMap.put("flag", "finish");
					}
				}else{
					dataMap.put("flag", "error");
				}
				dataMap.put("msg", result.getMsg());
				dataMap.put("appId", map.get("appId"));
			}else{
				dataMap.put("flag", "error");
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", this.getFormulavaliErrorMsg());
		}
		return dataMap;
	}

	/**
	 * 方法描述：费用收取页面（保理应收账款融资使用）
	 * @param model
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/feeForReceFinc")
	public String feeForReceFinc(Model model, String appId,String fincMainId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String nodeNo = WKF_NODE.fee_rece_finc.getNodeNo();// 要件场景
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		mfBusApply = appInterfaceFeign.processDataForApply(mfBusApply);
		//应收账款列表
		MfBusFincAppMain mfBusFincAppMainQuery = new MfBusFincAppMain();
		mfBusFincAppMainQuery.setFincMainId(fincMainId);
		mfBusFincAppMainQuery = mfBusFincAppMainFeign.getById(mfBusFincAppMainQuery);
		//表单编号
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.fee_rece_finc, null, null, User.getRegNo(request));
		FormData formappzhlf0004 = formService.getFormData(formId);
		getFormValue(formappzhlf0004);
		getObjValue(formappzhlf0004, mfBusApply);
		getObjValue(formappzhlf0004, mfBusFincAppMainQuery);
//		MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
//		if (mfBusPact!=null){
//			getObjValue(formappzhlf0004, mfBusPact);
//		}
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincAppMainQuery.getRateType()).getRemark();
		this.changeFormProperty(formappzhlf0004, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formappzhlf0004, "overRate", "unit", rateUnit);
		//融资金额
		if(null!=mfBusFincAppMainQuery.getFincAmt()){
			String putoutAmt = String.valueOf(mfBusFincAppMainQuery.getFincAmt());
			this.changeFormProperty(formappzhlf0004, "putoutAmt", "initValue",putoutAmt);
		}
		//应收账款列表
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(fincMainId);

		List<MfBusReceTransfer> mfBusReceTransferList = pactInterfaceFeign.getReceFincList(mfBusFincAppMain);
		model.addAttribute("formappzhlf0004", formappzhlf0004);
		model.addAttribute("mfBusReceTransferList", mfBusReceTransferList);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("appId", appId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("fincMainId", fincMainId);
		model.addAttribute("query", "");
		return "/component/app/guaranteeApply_feeForReceFinc";
	}

	/**
	 * 方法描述：应收账款融资费用收取提交
	 * @param ajaxData
	 * @param fincMainId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/receFincFeeSubmitAjax")
	@ResponseBody
	public Map<String, Object> receFincFeeSubmitAjax(String ajaxData,String fincMainId) throws Exception {
		ActionContext.initialize(request, response);

		Map<String, Object> map = getMapByJson(ajaxData);
		map.put("opNo", User.getRegNo(request));
		map.put("opName", User.getRegName(request));
		map.put("brNo", User.getOrgNo(request));
		map.put("brName", User.getOrgName(request));
		map.put("fincMainId",fincMainId);
		Result result = guaranteeApplyFeign.doReceFincFeeSubmit(map);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("appId", map.get("appId"));
		dataMap.put("msg", result.getMsg());
		dataMap.put("flag", "success");

		return dataMap;
	}

}
