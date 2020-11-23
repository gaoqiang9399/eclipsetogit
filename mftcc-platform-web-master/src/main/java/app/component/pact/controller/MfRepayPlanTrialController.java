package app.component.pact.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.tech.oscache.CodeUtils;
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

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfBusPact;
import app.component.pact.entity.MfRepayPlanTrial;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.feign.MfRepayPlanTrialFeign;
import app.component.pact.repay.entity.MfReceivableBean;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.tools.entity.MfLoanCalculator;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Title: MfRepayPlanTrialAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cncalculateAjax
 * @Tue Dec 12 10:35:23 CST 2017
 **/
@Controller
@RequestMapping("/mfRepayPlanTrial")
public class MfRepayPlanTrialController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfRepayPlanTrialBo
	@Autowired
	private MfRepayPlanTrialFeign mfRepayPlanTrialFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfBusAppKindFeign mfBusAppKindFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/MfRepayPlanTrial_List";
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
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRepayPlanTrial mfRepayPlanTrial = new MfRepayPlanTrial();
		try {
			mfRepayPlanTrial.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfRepayPlanTrial.setCriteriaList(mfRepayPlanTrial, ajaxData);// 我的筛选
			// mfRepayPlanTrial.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfRepayPlanTrial,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfRepayPlanTrialFeign.findByPage(ipage, mfRepayPlanTrial);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrepayplantrial0002 = formService.getFormData("repayplantrial0002");
			getFormValue(formrepayplantrial0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrepayplantrial0002)) {
				MfRepayPlanTrial mfRepayPlanTrial = new MfRepayPlanTrial();
				setObjValue(formrepayplantrial0002, mfRepayPlanTrial);
				mfRepayPlanTrialFeign.insert(mfRepayPlanTrial);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrepayplantrial0002 = formService.getFormData("repayplantrial0002");
		getFormValue(formrepayplantrial0002, getMapByJson(ajaxData));
		MfRepayPlanTrial mfRepayPlanTrialJsp = new MfRepayPlanTrial();
		setObjValue(formrepayplantrial0002, mfRepayPlanTrialJsp);
		MfRepayPlanTrial mfRepayPlanTrial = mfRepayPlanTrialFeign.getById(mfRepayPlanTrialJsp);
		if (mfRepayPlanTrial != null) {
			try {
				mfRepayPlanTrial = (MfRepayPlanTrial) EntityUtil.reflectionSetVal(mfRepayPlanTrial, mfRepayPlanTrialJsp,
						getMapByJson(ajaxData));
				mfRepayPlanTrialFeign.update(mfRepayPlanTrial);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrepayplantrial0002 = formService.getFormData("repayplantrial0002");
			getFormValue(formrepayplantrial0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrepayplantrial0002)) {
				MfRepayPlanTrial mfRepayPlanTrial = new MfRepayPlanTrial();
				setObjValue(formrepayplantrial0002, mfRepayPlanTrial);
				mfRepayPlanTrialFeign.update(mfRepayPlanTrial);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
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
	public Map<String, Object> getByIdAjax(String planId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrepayplantrial0002 = formService.getFormData("repayplantrial0002");
		MfRepayPlanTrial mfRepayPlanTrial = new MfRepayPlanTrial();
		mfRepayPlanTrial.setPlanId(planId);
		mfRepayPlanTrial = mfRepayPlanTrialFeign.getById(mfRepayPlanTrial);
		getObjValue(formrepayplantrial0002, mfRepayPlanTrial, formData);
		if (mfRepayPlanTrial != null) {
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
	public Map<String, Object> deleteAjax(String planId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRepayPlanTrial mfRepayPlanTrial = new MfRepayPlanTrial();
		mfRepayPlanTrial.setPlanId(planId);
		try {
			mfRepayPlanTrialFeign.delete(mfRepayPlanTrial);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
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
	public String input(Model model, String pactId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrepayplantrial0002 = formService.getFormData("repayplantrial0002");
		// 根据合同号获取合同
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		// 合同开始日期
		String beginDate = mfBusPact.getBeginDate();
		String endDate = mfBusPact.getEndDate();
		double pactAmt = mfBusPact.getPactAmt();
		MfLoanCalculator mfLoanCalculator = new MfLoanCalculator();
		mfLoanCalculator.setLoanMon(mfBusPact.getTerm());
		JSONObject json = new JSONObject();
		// 获取还款计划的所需的参数
		Map<String,String> parmMap = new HashMap<String,String>();
		parmMap.put("appId",mfBusPact.getAppId());
		String calcIntstFlag = prdctInterfaceFeign.getCalcIntstFlag(parmMap);
		json.put("calcIntstFlag", calcIntstFlag);
		// 查看是否需要修改还款计划每一期的结束日期
		//获取还款计划结束日期是否需要通过参数控制减一的所需的参数  0 不需要修改  1 需要每一期的结束日期减一
		String  repayPlanShowFlag= new CodeUtils().getSingleValByKey("REPAY_PLAN_SHOW_FLAG");
		if (StringUtil.isNotEmpty(beginDate)) {

		} else {
			// 获取申请中的日期
			beginDate = DateUtil.getDate();
			if ("1".equals(mfBusPact.getTermType())) {
				endDate = DateUtil.addMonth(beginDate, mfLoanCalculator.getLoanMon());
			} else if ("2".equals(mfBusPact.getTermType())) {
				endDate = DateUtil.addDay(beginDate, mfLoanCalculator.getLoanMon());
			}else {
			}

			if(StringUtil.isEmpty(repayPlanShowFlag)){
				repayPlanShowFlag="0";
			}
			if(BizPubParm.REPAYPLANSHOWFLAG_1.equals(repayPlanShowFlag)){
				String endDays=beginDate.substring(6,8);//获取日期天
				endDays=MathExtend.subtract(endDays,"1");
				if (endDays.length()==1){
					endDays="0"+endDays;
				}
				String months=endDate.substring(0,6);//获取每一期的还款计划所在的年月
				if("00".equals(endDays)){//起息日期减去一天后跨月
					months=DateUtil.getLastMonth(months);//获取上个月份
					endDate=months+"01";
					//获取月末
					endDate=DateUtil.getMaxMonthDate(endDate,"yyyyMMdd");
				}else{//起息日期减去一天后 没有跨月
					if("02".equals(months.substring(4,6)) && StringUtil.isMore(endDays,"28")==1){//月份是二月
						//获取月末
						endDate=DateUtil.getMaxMonthDate(endDate,"yyyyMMdd");
					}else{
						endDate=months+endDays;
					}
				}
			}else{
				if (BizPubParm.CALC_INTST_FLAG_2.equals(calcIntstFlag)) {// 首尾都计算，因为借据存储的是首尾相接
					endDate = DateUtil.addDay(endDate, -1);
				}
			}

		}
		mfLoanCalculator.setRepayType(mfBusPact.getRepayType());
		mfLoanCalculator.setTermType(mfBusPact.getTermType());
		mfLoanCalculator.setProvideDate(beginDate);
		mfLoanCalculator.setEndDate(DateUtil.getDiyDate(endDate, "yyyy-MM-dd"));
		mfLoanCalculator.setLoanAmt(pactAmt);
		mfLoanCalculator.setLoanRate(mfBusPact.getFincRate());
		mfLoanCalculator.setRepayTerm(1);
		mfLoanCalculator.setStartDate(DateUtil.getDate());
		mfLoanCalculator.setPactId(pactId);
		// 获取计息类型
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
		mfLoanCalculator.setAppId(mfBusPact.getAppId());
		if (mfBusAppKind != null) {
			mfLoanCalculator.setIntstType(mfBusAppKind.getNormCalcType());
			mfLoanCalculator.setRepayDateState(mfBusAppKind.getRepayDateSet());
			mfLoanCalculator.setRegularRepayDate(mfBusAppKind.getRepayDateDef());
		}
		// 查询是否进行过试算
		MfRepayPlanTrial mfRepayPlanTrial = new MfRepayPlanTrial();
		mfRepayPlanTrial.setPactId(pactId);
		List<MfRepayPlanTrial> mfRepayPlanTrialList = mfRepayPlanTrialFeign.getByPactId(mfRepayPlanTrial);
		// 日期格式化
		for (int i = 0; i < mfRepayPlanTrialList.size(); i++) {
			mfRepayPlanTrial = mfRepayPlanTrialList.get(i);
			mfRepayPlanTrial.setPlanBeginDate(DateUtil.getDiyDate(mfRepayPlanTrial.getPlanBeginDate(), "yyyy-MM-dd"));
			mfRepayPlanTrial.setPlanEndDate(DateUtil.getDiyDate(mfRepayPlanTrial.getPlanEndDate(), "yyyy-MM-dd"));
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablerepayplantrial0001", "tableTag", mfRepayPlanTrialList, null, true);
		json.put("html", tableHtml);
		String totalAmt = "0.00";
		String totalIntstAmt = "0.00";
		// 判断是否可以重新计算
		if (mfRepayPlanTrialList != null && mfRepayPlanTrialList.size() > 0) {
			for (int i = 0; i < mfRepayPlanTrialList.size(); i++) {
				totalAmt = MathExtend.add(totalAmt, String.valueOf(mfRepayPlanTrialList.get(i).getRepayPrcpIntstSum()));
				totalIntstAmt = MathExtend.add(totalIntstAmt,
						String.valueOf(mfRepayPlanTrialList.get(i).getRepayIntst()));
			}
			// 查询合同当前状态
			String pactSts = mfBusPact.getPactSts();
			if (BizPubParm.PACT_STS_UN_SIGN.equals(pactSts) || BizPubParm.PACT_STS_UN_SUBMIT.equals(pactSts)) {
				json.put("calculateFlag", true);
			} else {
				json.put("calculateFlag", false);
			}
		} else {
			json.put("calculateFlag", true);
		}
		json.put("totalAmt", totalAmt);
		json.put("totalIntstAmt", totalIntstAmt);
		String ajaxData = json.toString();
		getObjValue(formrepayplantrial0002, mfLoanCalculator);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("mfLoanCalculator", mfLoanCalculator);
		model.addAttribute("formrepayplantrial0002", formrepayplantrial0002);
		model.addAttribute("query", "");
		return "/component/pact/MfRepayPlanTrial_Insert";
	}

	/**
	 * 
	 * 方法描述： 还款计划试算
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author YaoWenHao
	 * @date 2017-12-12 下午3:47:06
	 */
	@RequestMapping(value = "/calculateAjax")
	@ResponseBody
	public Map<String, Object> calculateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxData);

		try {
			dataMap = mfRepayPlanTrialFeign.calculate(dataMap);
			List<MfRepayPlan> mfRepayPlanList = (List<MfRepayPlan>) dataMap.get("repayPlanList");
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tablerepayplantrial0001", "tableTag", mfRepayPlanList, null, true);
			dataMap.put("htmlStr", tableHtml);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
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
	public String getById(Model model, String planId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrepayplantrial0001 = formService.getFormData("repayplantrial0001");
		getFormValue(formrepayplantrial0001);
		MfRepayPlanTrial mfRepayPlanTrial = new MfRepayPlanTrial();
		mfRepayPlanTrial.setPlanId(planId);
		mfRepayPlanTrial = mfRepayPlanTrialFeign.getById(mfRepayPlanTrial);
		getObjValue(formrepayplantrial0001, mfRepayPlanTrial);
		model.addAttribute("mfRepayPlanTrial", mfRepayPlanTrial);
		model.addAttribute("formrepayplantrial0001", formrepayplantrial0001);
		model.addAttribute("query", "");
		return "/component/pact/MfRepayPlanTrial_Detail";
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
		FormData formrepayplantrial0002 = formService.getFormData("repayplantrial0002");
		getFormValue(formrepayplantrial0002);
		boolean validateFlag = this.validateFormData(formrepayplantrial0002);
	}
	
	/**
	 * 正常还款试算预计归还时间改变
	 */
	@RequestMapping(value = "rapayTrialDateChangeAjax")
	@ResponseBody
	public Map<String,Object> rapayTrialDateChangeAjax(String fincId, String repayDate,String yujiguihuan) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("fincId", fincId);
			parmMap.put("repayDate", repayDate);
			parmMap.put("yujiguihuan", yujiguihuan);
			dataMap = mfRepayPlanTrialFeign.doRapayTrialDateChange(parmMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
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
		FormData formrepayplantrial0002 = formService.getFormData("repayplantrial0002");
		getFormValue(formrepayplantrial0002);
		boolean validateFlag = this.validateFormData(formrepayplantrial0002);
	}

}
