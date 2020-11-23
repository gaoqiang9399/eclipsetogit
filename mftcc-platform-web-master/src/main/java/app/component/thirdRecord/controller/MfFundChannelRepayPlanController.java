package  app.component.thirdRecord.controller;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.channel.fund.entity.MfFundChannelRepayPlan;
import app.component.common.BizPubParm;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pact.entity.MfRepayPlanTrial;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.feign.MfRepayPlanTrialFeign;
import app.component.pact.util.RementPayPoiExcelUtil;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.recourse.feign.MfBusRecourseApplyFeign;
import app.component.thirdRecord.feign.MfFundChannelRepayHistoryFeign;
import app.component.thirdRecord.feign.MfFundChannelRepayPlanFeign;
import app.component.tools.entity.MfLoanCalculator;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * Title: MfBusRecourseApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 16 19:39:17 CST 2018
 **/
@Controller
@RequestMapping("/mfFundChannelRepayPlan")
public class MfFundChannelRepayPlanController extends BaseFormBean{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfBusAppKindFeign mfBusAppKindFeign;
	@Autowired
	private MfRepayPlanTrialFeign mfRepayPlanTrialFeign;
	@Autowired
	private MfFundChannelRepayPlanFeign mfFundChannelRepayPlanFeign;
	@Autowired
	private MfBusRecourseApplyFeign mfBusRecourseApplyFeign;
	@Autowired
	private MfFundChannelRepayHistoryFeign mfFundChannelRepayHistoryFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String pactId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrepayplantrial0002 = formService.getFormData("fundchannelrepayplan_base");
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
			if (BizPubParm.CALC_INTST_FLAG_2.equals(calcIntstFlag)) {// 首尾都计算，因为借据存储的是首尾相接
				endDate = DateUtil.addDay(endDate, -1);
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
		//根据产品上的利率转变展示利率
	   mfBusAppKind.setAppId(mfBusPact.getAppId());
	   mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
	   Double loanRate = mfLoanCalculator.getLoanRate();
	   mfLoanCalculator.setLoanRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfLoanCalculator.getLoanRate(), Integer.parseInt(mfBusAppKind.getYearDays()),Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
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
		this.changeFormProperty(formrepayplantrial0002, "cusNo", "initValue",mfBusPact.getCusNo()); 
		this.changeFormProperty(formrepayplantrial0002, "cusName", "initValue",mfBusPact.getCusName()); 
		this.changeFormProperty(formrepayplantrial0002, "pactNo", "initValue",mfBusPact.getPactNo()); 
		this.changeFormProperty(formrepayplantrial0002, "appName", "initValue",mfBusPact.getAppName()); 
		this.changeFormProperty(formrepayplantrial0002, "kindName", "initValue",mfBusPact.getKindName()); 
		//处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formrepayplantrial0002, "loanRate", "unit", rateUnit);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("mfLoanCalculator", mfLoanCalculator);
		model.addAttribute("formrepayplantrial0002", formrepayplantrial0002);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", mfBusPact.getCusNo());
		model.addAttribute("pactId", mfBusPact.getPactId());
		model.addAttribute("appId", mfBusPact.getAppId());
		return "/component/thirdRecord/MfFundChannelRepayPlan_Insert";
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData,String formData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			Map<String, Object> pactMap = getMapByJson(formData);
			pactMap.put("currentOpNo", User.getRegNo(request));
			mfFundChannelRepayPlanFeign.insertList(ajaxData,pactMap);
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		
		return dataMap;
	}
	
	@RequestMapping("/insertNewPlanAjax")
	@ResponseBody
	public Map<String,Object> insertNewPlanAjax(String ajaxData,String cusNo,String cusName,String pactId,String putoutAmt) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			Map<String, Object> pactMap = new HashMap<String, Object>();//getMapByJson(formData);
			pactMap.put("cusNo", cusNo);
			pactMap.put("cusName", cusName);
			pactMap.put("pactId", pactId);
			pactMap.put("loanAmt", putoutAmt);
			pactMap.put("currentOpNo", User.getRegNo(request));
			mfFundChannelRepayPlanFeign.insertNewPlanList(ajaxData,pactMap);
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		
		return dataMap;
	}
	
	/**
	 * 下载还款计划表模板
	 * @author 段泽宇
	 * @date2018.4.12
	 */
	@RequestMapping(value = "/exportExcelAjax")
	@ResponseBody
	public Map<String, Object> exportExcelAjax(String ajaxData, String pactId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//找到模板
			String path = mfFundChannelRepayPlanFeign.doExportExcelModel(pactId);
			dataMap.put("flag", "success");
			dataMap.put("exportFlag", "success");
			dataMap.put("path", path);
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("导出"));
			if (path == null) {
				dataMap.put("exportFlag", "error");
				dataMap.put("msg", MessageEnum.NO_FILE.getMessage("模板导出路径"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * 提交
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/submitBussProcessAjax")
	@ResponseBody
	public Map<String, Object> submitBussProcessAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfFundChannelRepayPlanFeign.submitBussProcess(appId,User.getRegNo(request));
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_COMMIT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	//还款计划表上传控制
	@RequestMapping(value = "/uploadAjax")
	@ResponseBody
	public Map<String, Object> uploadAjax(String cusFinUploadFileName,@RequestParam(value="cusFinUpload")  MultipartFile cusFinUpload) throws Exception{
		ActionContext.initialize(request,response);
		String cusNo = request.getParameter("cusNo");
		boolean flag = true;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		cusFinUploadFileName=cusFinUpload.getOriginalFilename();
		//添加文件上传格式验证，过滤可能威胁系统安全非法后缀
		if(cusFinUploadFileName.endsWith(".exe")||cusFinUploadFileName.endsWith(".jsp")||cusFinUploadFileName.endsWith(".bat")
				||cusFinUploadFileName.endsWith(".dll")||cusFinUploadFileName.endsWith(".com")||cusFinUploadFileName.endsWith(".sh")||cusFinUploadFileName.endsWith(".py")){
			dataMap.put("flag", "error");
			dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
			return dataMap;
		}
		//根据属性名获取upload.properties中对应属性值
		String uploadFinPath = PropertiesUtil.getUploadProperty("uploadFinFilePath");
		//上传后的excel文件名字
		String newFileName = cusNo + UUID.randomUUID().toString() + cusFinUploadFileName.substring(cusFinUploadFileName.lastIndexOf("."));
		//上传后excel文件的所在文件夹
		String allPath = uploadFinPath + File.separator + newFileName;
		File f = new File(allPath);
		cusFinUpload.transferTo(f);
		try {
			//解析excel;
			RementPayPoiExcelUtil rementPayPoiExcelUtil = new RementPayPoiExcelUtil();
			List<MfRepayPlan> mfRepayPlans = rementPayPoiExcelUtil.resolveExcel(allPath);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tablerepayplantrial0001", "tableTag", mfRepayPlans, null, true);
			dataMap.put("htmlStr", tableHtml);
			dataMap.put("dataMsg", "文件上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			dataMap.put("flag", "error");	
			dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
		} finally {
			if(flag){
				dataMap.put("flag", "success");
			}
		}
		return dataMap;
	}
	
	/**
	 * 跳转到还款页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/repay")
	public String repay(Model model,String pactId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfFundChannelRepayPlan mfFundChannelRepayPlan = new MfFundChannelRepayPlan();
		mfFundChannelRepayPlan.setPactId(pactId);
		mfFundChannelRepayPlan.setOutFlag("1");
		mfFundChannelRepayPlan = mfFundChannelRepayPlanFeign.getByIdForRepay(mfFundChannelRepayPlan);
		FormData formfundchannelrepayplanRepay = formService.getFormData("fundchannelrepayplan_repay");
		getObjValue(formfundchannelrepayplanRepay, mfFundChannelRepayPlan);
		// 根据合同号获取合同
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		this.changeFormProperty(formfundchannelrepayplanRepay, "pactNo", "initValue",mfBusPact.getPactNo());
		this.changeFormProperty(formfundchannelrepayplanRepay, "realRepayIntst", "initValue",String.valueOf(mfFundChannelRepayPlan.getRepayIntst()));
		this.changeFormProperty(formfundchannelrepayplanRepay, "realRepayPrcp", "initValue",String.valueOf(mfFundChannelRepayPlan.getRepayPrcp()));
		this.changeFormProperty(formfundchannelrepayplanRepay, "realRepayPrcpIntstSum", "initValue",String.valueOf(mfFundChannelRepayPlan.getRepayPrcpIntstSum()));
		model.addAttribute("formfundchannelrepayplanRepay", formfundchannelrepayplanRepay);
		model.addAttribute("query","");
		return "/component/thirdRecord/MfFundChannelRepayPlan_Repay";
	}
}
