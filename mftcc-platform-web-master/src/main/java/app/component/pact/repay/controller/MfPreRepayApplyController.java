package app.component.pact.repay.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.feign.MfRepayPlanFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCustomer;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.repay.entity.MfPreRepayApply;
import app.component.pact.repay.entity.MfPrepaymentBean;
import app.component.pact.repay.feign.MfPreRepayApplyFeign;
import app.component.pact.repay.feign.MfRepaymentFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfPreRepayApplyAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 14 10:46:31 CST 2017
 **/
@Controller
@RequestMapping("/mfPreRepayApply")
public class MfPreRepayApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfPreRepayApplyBo
	@Autowired
	private MfPreRepayApplyFeign mfPreRepayApplyFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfRepaymentFeign mfRepaymentFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfRepayPlanFeign mfRepayPlanFeign;
	
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		// 提前还款申请状态
		CodeUtils codeUtils = new CodeUtils();
		JSONArray appStsJsonArray = codeUtils.getJSONArrayByKeyName("PRE_REPAY_APP_STS");
		model.addAttribute("appStsJsonArray", appStsJsonArray);
		model.addAttribute("query", "");
		return "component/pact/repay/MfPreRepayApply_List";
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
		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		try {
			mfPreRepayApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfPreRepayApply.setCriteriaList(mfPreRepayApply, ajaxData);// 我的筛选
			mfPreRepayApply.setCustomSorts(ajaxData);// 自定义排序
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfPreRepayApply", mfPreRepayApply));
			// 自定义查询Bo方法
			
			ipage = mfPreRepayApplyFeign.findByPage(ipage);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formprerepayapplyadd = formService.getFormData("prerepayapplyadd");

		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		mfPreRepayApply.setPenaltyAmt(0.00);
		mfPreRepayApply.setOpName(User.getRegName(request));
		mfPreRepayApply.setBrName(User.getOrgName(request));
		// 提前还款申请审批流程
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setFlowApprovalNo("pre_repay_approval");
		mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
		mfPreRepayApply.setFlowFlag(mfKindFlow.getUseFlag());
		mfPreRepayApply.setFlowId(mfKindFlow.getFlowId());
		getObjValue(formprerepayapplyadd, mfPreRepayApply);
		// 提前还款利息设置参数

		model.addAttribute("formprerepayapplyadd", formprerepayapplyadd);
		model.addAttribute("query", "");
		return "component/pact/repay/MfPreRepayApply_Insert";
	}

	/**
	 * 
	 * 方法描述： 获取可提前还款的借据列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param pageSize 
	 * @param pageNo 
	 * @date 2017-8-15 上午9:24:53
	 */
	@RequestMapping(value = "/getPreRepayFincListAjax")
	@ResponseBody
	public Map<String, Object> getPreRepayFincListAjax(String ajaxData, Integer pageSize, Integer pageNo) throws Exception {
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
			ipage = mfBusFincAppFeign.getPreRepayFincList(ipage);
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
	 * @param startFlowFlag 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String startFlowFlag) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formprerepayapplyadd = formService.getFormData("prerepayapplyadd");
			getFormValue(formprerepayapplyadd, getMapByJson(ajaxData));
			if (this.validateFormData(formprerepayapplyadd)) {
				MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
				mfPreRepayApply.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(formprerepayapplyadd, mfPreRepayApply);
				dataMap = mfPreRepayApplyFeign.insert(mfPreRepayApply, startFlowFlag);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * @param startFlowFlag 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String startFlowFlag) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formprerepayapplydetail = formService.getFormData("prerepayapplydetail");
			getFormValue(formprerepayapplydetail, getMapByJson(ajaxData));
			if (this.validateFormData(formprerepayapplydetail)) {
				MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
				mfPreRepayApply.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(formprerepayapplydetail, mfPreRepayApply);
				mfPreRepayApplyFeign.update(mfPreRepayApply, startFlowFlag);
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
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param preRepayAppId 
	 * @param preRepayAppSts 
	 * @date 2017-8-18 下午6:41:48
	 */
	@RequestMapping(value = "/updateAppStsAjax")
	@ResponseBody
	public Map<String, Object> updateAppStsAjax( String preRepayAppId, String preRepayAppSts) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
			mfPreRepayApply.setPreRepayAppId(preRepayAppId);
			mfPreRepayApply.setPreRepayAppSts(preRepayAppSts);
			mfPreRepayApplyFeign.updateAppStsAjax(mfPreRepayApply);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String preRepayAppId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formprerepayapplydetail = formService.getFormData("prerepayapplydetail");
		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		mfPreRepayApply.setPreRepayAppId(preRepayAppId);
		mfPreRepayApply = mfPreRepayApplyFeign.getById(mfPreRepayApply);
		getObjValue(formprerepayapplydetail, mfPreRepayApply, formData);
		if (mfPreRepayApply != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
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
	public Map<String, Object> deleteAjax(String preRepayAppId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		mfPreRepayApply.setPreRepayAppId(preRepayAppId);
		try {
			mfPreRepayApplyFeign.delete(mfPreRepayApply);
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
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String preRepayAppId, String mfBusEditorRepayplanFlag) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		mfPreRepayApply.setPreRepayAppId(preRepayAppId);
		mfPreRepayApply = mfPreRepayApplyFeign.getById(mfPreRepayApply);
		mfPreRepayApply.setAppAmtFormat(MathExtend.moneyStr(mfPreRepayApply.getAppAmt()));
		MfPrepaymentBean mfPrepaymentBean=null;
		if ("3".equals(mfPreRepayApply.getPreRepayAppSts()) ) {//提前还款否决,取申请提前还款申请填写的
			FormData formprerepayapplydetail = formService.getFormData("prerepayapplyrefusedetail");
			getFormValue(formprerepayapplydetail);
			mfPreRepayApply = new MfPreRepayApply();
			mfPreRepayApply.setPreRepayAppId(preRepayAppId);
			mfPreRepayApply = mfPreRepayApplyFeign.getById(mfPreRepayApply);
			//实际利息
			Double shiShouLiXi = mfPreRepayApply.getIntstAmt();
			if(shiShouLiXi==null){
				shiShouLiXi=0.00;
			}
			//减免金额
			Double deductAmt = mfPreRepayApply.getDeductAmt();
			if(deductAmt==null){
				deductAmt=0.00;
			}
			//利息
			mfPreRepayApply.setIntstAmt(shiShouLiXi+deductAmt);
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincId(mfPreRepayApply.getFincId());
			mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

			MfBusAppKind mfBusAppKind = new MfBusAppKind();
			mfBusAppKind.setAppId(mfBusFincApp.getAppId());
			mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
			String tiQianHuanBen = "0.00";
			if ("1".equals(mfBusAppKind.getPreRepayType()) || "3".equals(mfBusAppKind.getPreRepayType())) {// 提前结清
				int returnPlanPoint = Integer.valueOf(StringUtil.KillBlankAndTrim(mfBusAppKind.getReturnPlanPoint(), "2"));
				tiQianHuanBen = MathExtend.moneyStr(String.valueOf(mfBusFincApp.getLoanBal()), returnPlanPoint);
			}
			getObjValue(formprerepayapplydetail, mfPreRepayApply);
			getObjValue(formprerepayapplydetail, mfBusFincApp);
			this.changeFormProperty(formprerepayapplydetail, "tiQianHuanBen", "initValue", tiQianHuanBen);
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
			String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
			this.changeFormProperty(formprerepayapplydetail, "fincRate", "unit", rateUnit);
			// 剩余本金
			String dangQiBenJin = "0.00";
			if(null!=mfBusFincApp.getLoanBal()){
				dangQiBenJin = String.valueOf(mfBusFincApp.getLoanBal());
			}
			this.changeFormProperty(formprerepayapplydetail, "dangQiBenJin", "initValue", dangQiBenJin);
			model.addAttribute("formprerepayapplydetail", formprerepayapplydetail);
			model.addAttribute("mfBusAppKind", mfBusAppKind);
			model.addAttribute("mfPreRepayApply", mfPreRepayApply);
			model.addAttribute("query", "");
			return "component/pact/repay/MfPreRepayApply_Detail";
		}
		if (!"2".equals(mfPreRepayApply.getPreRepayAppSts()) ) {
			FormData formprerepayapplydetail = formService.getFormData("prerepayapplydetail");
			getFormValue(formprerepayapplydetail);
/*			mfPreRepayApply = new MfPreRepayApply();
			mfPreRepayApply.setPreRepayAppId(preRepayAppId);
			mfPreRepayApply = mfPreRepayApplyFeign.getById(mfPreRepayApply);*/
			//实际利息
			Double shiShouLiXi = mfPreRepayApply.getIntstAmt();
			if(shiShouLiXi==null){
				shiShouLiXi=0.00;
			}
			//减免金额
			Double deductAmt = mfPreRepayApply.getDeductAmt();
			if(deductAmt==null){
				deductAmt=0.00;
			}
			//利息
			mfPreRepayApply.setIntstAmt(shiShouLiXi+deductAmt);
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincId(mfPreRepayApply.getFincId());
			mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

			MfBusAppKind mfBusAppKind = new MfBusAppKind();
			mfBusAppKind.setAppId(mfBusFincApp.getAppId());
			mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
			String tiQianHuanBen = "0.00";
			if ("1".equals(mfBusAppKind.getPreRepayType()) || "3".equals(mfBusAppKind.getPreRepayType())) {// 提前结清
				int returnPlanPoint = Integer.valueOf(StringUtil.KillBlankAndTrim(mfBusAppKind.getReturnPlanPoint(), "2"));
				tiQianHuanBen = MathExtend.moneyStr(String.valueOf(mfBusFincApp.getLoanBal()), returnPlanPoint);
			}
			getObjValue(formprerepayapplydetail, mfPreRepayApply);
			getObjValue(formprerepayapplydetail, mfBusFincApp);
			this.changeFormProperty(formprerepayapplydetail, "tiQianHuanBen", "initValue", tiQianHuanBen);
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
			String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
			this.changeFormProperty(formprerepayapplydetail, "fincRate", "unit", rateUnit);
			// 当期本金
			dataMap = mfRepaymentFeign.doPrepaymentJsp(mfPreRepayApply.getFincId());
			mfPrepaymentBean = new MfPrepaymentBean();
			Gson gson = new Gson();
			mfPrepaymentBean = gson.fromJson(gson.toJson(dataMap.get("mfPrepaymentBean")),MfPrepaymentBean.class);;
			String dangQiBenJin = String.valueOf(mfPrepaymentBean.getDangQiBenJin());
			this.changeFormProperty(formprerepayapplydetail, "dangQiBenJin", "initValue",
					dangQiBenJin);
			model.addAttribute("formprerepayapplydetail", formprerepayapplydetail);
			model.addAttribute("mfBusAppKind", mfBusAppKind);
			model.addAttribute("mfPreRepayApply", mfPreRepayApply);
			model.addAttribute("query", "");
			return "component/pact/repay/MfPreRepayApply_Detail";
		}
		dataMap = mfRepaymentFeign.doPrepaymentJsp(mfPreRepayApply.getFincId());
		Gson gson = new Gson();
		// 客户信息
		MfCusCustomer mfCusCustomer = gson.fromJson(gson.toJson(dataMap.get("mfCusCustomer")), MfCusCustomer.class);
		// 借据信息
		MfBusFincApp mfBusFincApp = gson.fromJson(gson.toJson(dataMap.get("mfBusFincApp")), MfBusFincApp.class);
		// 还款信息
		mfPrepaymentBean = gson.fromJson(gson.toJson(dataMap.get("mfPrepaymentBean")), MfPrepaymentBean.class);
		// 经过提前还款申请后的提前还款页面的默认值是申请时的数据
		Double shiShouZongJi = 0.00;
		// 计算实收总计=提前还本+实收利息+违约金-本次冲抵
		shiShouZongJi = MathExtend.add(shiShouZongJi, mfPreRepayApply.getAppAmt());
		shiShouZongJi = MathExtend.add(shiShouZongJi, mfPreRepayApply.getIntstAmt());
		shiShouZongJi = MathExtend.add(shiShouZongJi, mfPreRepayApply.getPenaltyAmt());
		shiShouZongJi = MathExtend.subtract(shiShouZongJi, mfPrepaymentBean.getBenCiChongDi());

		mfPrepaymentBean.setShiShouZongJi(shiShouZongJi);
		// 实收利息
		mfPrepaymentBean.setShiShouLiXi(mfPreRepayApply.getIntstAmt());
		mfPrepaymentBean.setShiShouLiXiFormat(MathExtend.moneyStr(mfPreRepayApply.getIntstAmt()));
		//提前还本金额
		mfPrepaymentBean.setTiQianHuanBen(mfPreRepayApply.getAppAmt());
		mfPrepaymentBean.setTiQianHuanBenFormat(MathExtend.moneyStr(mfPreRepayApply.getAppAmt()));
		// 优惠金额
		mfPrepaymentBean.setYouHuiEr(mfPreRepayApply.getDeductAmt());
		mfPrepaymentBean.setSystemDateLong(DateUtil.getShowDateTime(mfPreRepayApply.getPlanRepayDate()));
		mfPrepaymentBean.setTiQianHuanKuanWeiYueJin(mfPreRepayApply.getPenaltyAmt());
		mfPrepaymentBean.setTiQianHuanKuanWeiYueJinFormat(MathExtend.moneyStr(mfPreRepayApply.getPenaltyAmt()));
		mfPrepaymentBean.setShiShouZongJi(shiShouZongJi);

		// 应收信息
		//此处注释掉 用这个工具转的话会造成还款计划的期号带有小数点
		//List<MfRepayPlan> mfRepayPlanList =gson.fromJson(gson.toJson(dataMap.get("mfRepayPlanList")), List.class);
		List<MfRepayPlan> mfRepayPlanList = (List<MfRepayPlan>) dataMap.get("mfRepayPlanList");
		dataMap.put("preRepayApplyFlag", "1");// 经过提前还款申请后进入提前还款页面
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfPrepaymentBean", mfPrepaymentBean);
		model.addAttribute("mfRepayPlanList", mfRepayPlanList);
		model.addAttribute("mfBusEditorRepayplanFlag", mfBusEditorRepayplanFlag);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "component/pact/repay/MfRepayment_MfPrepaymentJsp";
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
		FormData formprerepayapplydetail = formService.getFormData("prerepayapplydetail");
		getFormValue(formprerepayapplydetail);
		boolean validateFlag = this.validateFormData(formprerepayapplydetail);
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
		FormData formprerepayapplydetail = formService.getFormData("prerepayapplydetail");
		getFormValue(formprerepayapplydetail);
		boolean validateFlag = this.validateFormData(formprerepayapplydetail);
	}

	/**
	 *
	 * 功能描述:提前还款申请  选择借据号后 执行的方法
	 * @param: [ajaxData, fincId]
	 * @return: java.util.Map<java.lang.String,java.lang.Object>
	 * @auther: wd
	 * @date: 2018/12/19 11:19
	 * 
	 */
	@RequestMapping(value = "/getFincInfoAjax")
	@ResponseBody
	public Map<String, Object> getFincInfoAjax(String ajaxData, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<MfBusFincApp> list = new ArrayList<MfBusFincApp>();
		MfBusFincApp mfBusFincApp = null; 
		try {
			// 提前还款页面数据取值
			dataMap = mfRepaymentFeign.doPrepaymentJsp(fincId);
			//hash转换为JSON 再转为List 最后放入实体类;
			JSONArray json = JSONArray.fromObject(dataMap.get("mfBusFincApp"));
			list = (ArrayList<MfBusFincApp>) JSONArray.toCollection(json, MfBusFincApp.class);// tolist过时方法 用toCollection代替
			if(list != null) {
				mfBusFincApp = list.get(0);
			}
			
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
	 * 方法描述：跳转至审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param preRepayAppId 
	 * @param activityType 
	 * @date 2017-8-15 下午8:02:51
	 */
	@RequestMapping(value = "/getPreRepayApproval")
	public String getPreRepayApproval(Model model,String preRepayAppId,String taskId, String hideOpinionType, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(preRepayAppId, taskId);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

		FormData formprerepayapplyadd = formService.getFormData("prerepayapplyapproval");
		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		mfPreRepayApply.setPreRepayAppId(preRepayAppId);
		mfPreRepayApply = mfPreRepayApplyFeign.getById(mfPreRepayApply);

		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(mfPreRepayApply.getFincId());
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		getObjValue(formprerepayapplyadd, mfPreRepayApply);
		getObjValue(formprerepayapplyadd, mfBusFincApp);
		this.changeFormProperty(formprerepayapplyadd, "appTime", "initValue", mfPreRepayApply.getAppTime());
		//实际利息
		Double shiShouLiXi = mfPreRepayApply.getIntstAmt();
		if(shiShouLiXi==null){
			shiShouLiXi=0.00;
		}
		//减免金额
		Double deductAmt = mfPreRepayApply.getDeductAmt();
		if(deductAmt==null){
			deductAmt=0.00;
		}

		//应收利息
		String yingShouLiXi = String.valueOf(shiShouLiXi+deductAmt);
		this.changeFormProperty(formprerepayapplyadd, "yingShouLiXi", "initValue", yingShouLiXi);

		//处理利率单位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
		this.changeFormProperty(formprerepayapplyadd, "fincRate", "unit", rateUnit);
		dataMap = mfRepaymentFeign.doPrepaymentJsp(mfPreRepayApply.getFincId());
		Gson gson = new Gson();
		String mfPrepaymentS = gson.toJson( dataMap.get("mfPrepaymentBean"));
		MfPrepaymentBean mfPrepaymentBean = gson.fromJson(mfPrepaymentS, MfPrepaymentBean.class);
		getObjValue(formprerepayapplyadd, mfPrepaymentBean);

		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", taskAppro.getActivityName());// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formprerepayapplyadd, "opinionType", "optionArray", opinionTypeList);

		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		model.addAttribute("befNodesjsonArray", befNodesjsonArray);

		model.addAttribute("scNo", scNo);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("formprerepayapplyadd", formprerepayapplyadd);
		model.addAttribute("mfPrepaymentBean", mfPrepaymentBean);
		model.addAttribute("mfPreRepayApply", mfPreRepayApply);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("query", "");
		return "component/pact/repay/MfPreRepayApply_Approval";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param taskId 
	 * @param preRepayAppId 
	 * @param transition 
	 * @param nextUser 
	 * @date 2017-8-15 下午8:02:51
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String preRepayAppId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formprerepayapplyadd = formService.getFormData(formId);
		getFormValue(formprerepayapplyadd, map);
		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		setObjValue(formprerepayapplyadd, mfPreRepayApply);
		Result res;
		try {
			dataMap = getMapByJson(ajaxData);
			dataMap.put("orgNo", User.getOrgNo(request));
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			res = mfPreRepayApplyFeign.doCommit(taskId, preRepayAppId, opinionType, approvalOpinion, transition,
					User.getRegNo(this.getHttpRequest()), nextUser, mfPreRepayApply, dataMap);
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
	 * 
	 * 方法描述： 检查是否需要提前还款申请
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param fincId 
	 * @date 2017-8-18 下午3:39:00
	 */
	@RequestMapping(value = "/checkPreRepayApplyAjax")
	@ResponseBody
	public Map<String, Object> checkPreRepayApplyAjax(String ajaxData, String fincId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 贷后参数设置
		Map<String, ParmDic> dicMap = new CodeUtils().getMapObjByKeyName("PRE_REPAY_APPLY_FLAG");
		dataMap.put("preRepayApplyFlag", dicMap.get("1").getRemark());
		// 如果需要进行提前还款申请
		if ("1".equals(dicMap.get("1").getRemark())) {
			// 判断是否已经进行了提前还款申请
			MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
			mfPreRepayApply.setFincId(fincId);
			mfPreRepayApply = mfPreRepayApplyFeign.checkPreRepayApply(mfPreRepayApply);
			if (mfPreRepayApply == null || "0".equals(mfPreRepayApply.getPreRepayAppSts())
					|| "1".equals(mfPreRepayApply.getPreRepayAppSts())) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FIRST_OPERATION.getMessage("提前还款申请"));
			} else {
				dataMap.put("flag", "success");
				dataMap.put("preRepayAppId", mfPreRepayApply.getPreRepayAppId());

			}
		} else {
			dataMap.put("flag", "success");
			dataMap.put("preRepayAppId", "");
		}

		return dataMap;
	}

	/**
	 * 提前还款准入
	 * @param fincId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ifCanPreRepayAjax")
	@ResponseBody
	public Map<String, Object> ifCanPreRepayAjax(String ajaxData, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 贷后参数设置
		try {
			// 判断是否已经进行了提前还款申请
			MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
			mfPreRepayApply.setFincId(fincId);
			String result = mfPreRepayApplyFeign.ifCanPreRepay(mfPreRepayApply);
			if ("0".equals(result)) {
				dataMap.put("msg", "不符合提前还款条件");
			}
			dataMap.put("flag", "success");
			dataMap.put("result", result);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FIRST_OPERATION.getMessage("提前还款准入"));
		}

		return dataMap;
	}


	@RequestMapping(value = "/getRepayTrialType")
	@ResponseBody
	public Map<String, Object> getRepayTrialType(String ajaxData, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 判断是否有符合状态的数据 没有则还款试算按钮置灰
			MfRepayPlan mfRepayPlan = new MfRepayPlan();
			mfRepayPlan.setFincId(fincId);
			mfRepayPlan.setOutFlag("('0','2')");
			int num = mfRepayPlanFeign.getMfRepayPlanCount(mfRepayPlan);
			if(num == 0){//没有符合的数据
				dataMap.put("flag", "hide");
			}else{
				dataMap.put("flag", "success");
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
		}

		return dataMap;
	}
}
