package app.component.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.app.entity.*;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditConfig;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditConfigFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.common.BizPubParm;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.component.cus.cusinvoicemation.feign.MfCusInvoiceMationFeign;
import app.component.cus.dishonestinfo.entity.MfCusDishonestInfo;
import app.component.cus.dishonestinfo.feign.MfCusDishonestInfoFeign;
import app.component.cus.entity.*;
import app.component.cus.execnotice.entity.MfCusExecNotice;
import app.component.cus.execnotice.feign.MfCusExecNoticeFeign;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.report.entity.MfCusReportAcount;
import app.component.cus.report.feign.MfCusReportAcountFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfTuningReportFeign;
import app.base.User;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfTuningReport;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfTuningReportFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.modelinterface.ModelInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfTuningReportAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 19:48:40 CST 2017
 **/
@Controller
@RequestMapping("/mfTuningReport")
public class MfTuningReportController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfTuningReportBo
	@Autowired
	private MfTuningReportFeign mfTuningReportFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	//全局变量
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private ModelInterfaceFeign modelInterfaceFeign;
	@Autowired
	private MfCusCreditApplyFeign mfCusCreditApplyFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private MfCusInvoiceMationFeign mfCusInvoiceMationFeign;
	@Autowired
	private MfBusAgenciesFeign mfBusAgenciesFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private MfCusDishonestInfoFeign mfCusDishonestInfoFeign;
	@Autowired
	private MfCusExecNoticeFeign mfCusExecNoticeFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private TaskFeign taskFeign;
	@Autowired
	private MfCusCreditConfigFeign mfCusCreditConfigFeign;
	@Autowired
	private MfCusReportAcountFeign mfCusReportAcountFeign;
	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;

	//异步参数
	//表单变量
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		return "/component/app/MfTuningReport_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfTuningReport mfTuningReport = new MfTuningReport();
		try {
			mfTuningReport.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfTuningReport.setCriteriaList(mfTuningReport, ajaxData);//我的筛选
			//this.getRoleConditions(mfTuningReport,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfTuningReportFeign.findByPage(ipage, mfTuningReport);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formappreport0002 = formService.getFormData("appreport0002");
			getFormValue(formappreport0002, getMapByJson(ajaxData));
			if(this.validateFormData(formappreport0002)){
		MfTuningReport mfTuningReport = new MfTuningReport();
				setObjValue(formappreport0002, mfTuningReport);
				mfTuningReportFeign.insert(mfTuningReport);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				dataMap.put("appId", mfTuningReport.getAppId());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 方法描述： 插入信息并且提交流程
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-6-18 上午10:58:24
	 */
	@RequestMapping(value = "/insertAndCommitAjax")
	@ResponseBody
	public Map<String, Object> insertAndCommitAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map map = getMapByJson(ajaxData);
		try{
		FormData 	formappreport0002 = formService.getFormData(map.get("formId").toString());
			getFormValue(formappreport0002, getMapByJson(ajaxData));
			if(this.validateFormData(formappreport0002)){
		MfTuningReport mfTuningReport = new MfTuningReport();
				setObjValue(formappreport0002, mfTuningReport);
				Result result = mfTuningReportFeign.insertAndCommit(mfTuningReport);
		MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(mfTuningReport.getAppId());
				mfBusApply = mfBusApplyFeign.getById(mfBusApply);
				dataMap.put("appSts", mfBusApply.getAppSts());
				dataMap.put("flag", "success");
				dataMap.put("appId", mfTuningReport.getAppId());
				dataMap.put("msg", result.getMsg());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formappreport0002 = formService.getFormData("appreport0002");
		getFormValue(formappreport0002, getMapByJson(ajaxData));
		MfTuningReport mfTuningReportJsp = new MfTuningReport();
		setObjValue(formappreport0002, mfTuningReportJsp);
		MfTuningReport mfTuningReport = mfTuningReportFeign.getById(mfTuningReportJsp);
		if(mfTuningReport!=null){
			try{
				mfTuningReport = (MfTuningReport)EntityUtil.reflectionSetVal(mfTuningReport, mfTuningReportJsp, getMapByJson(ajaxData));
				mfTuningReportFeign.update(mfTuningReport);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfTuningReport mfTuningReport = new MfTuningReport();
		try{
		FormData 	formappreport0002 = formService.getFormData("appreport0002");
			getFormValue(formappreport0002, getMapByJson(ajaxData));
			if(this.validateFormData(formappreport0002)){
				setObjValue(formappreport0002, mfTuningReport);
				mfTuningReportFeign.update(mfTuningReport);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData,String reportId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formappreport0002 = formService.getFormData("appreport0002");
		MfTuningReport mfTuningReport = new MfTuningReport();
		mfTuningReport.setReportId(reportId);
		mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
		getObjValue(formappreport0002, mfTuningReport,formData);
		if(mfTuningReport!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);	
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdDetailAjax")
	@ResponseBody
	public Map<String, Object> getByIdDetailAjax(String ajaxData,String reportId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formappreport0002 = formService.getFormData("appReportBase_detail");
		MfTuningReport mfTuningReport = new MfTuningReport();
 		mfTuningReport.setAppId(reportId);
 		mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
		getObjValue(formappreport0002, mfTuningReport,formData);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String query="";
		String htmlStr = jsonFormUtil.getJsonStr(formappreport0002, "propertySeeTag", query);
		if(mfTuningReport!=null){
			dataMap.put("flag", "success");
			dataMap.put("reportDetailInfo", htmlStr);
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String reportId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfTuningReport mfTuningReport = new MfTuningReport();
		mfTuningReport.setReportId(reportId);
		try {
			mfTuningReportFeign.delete(mfTuningReport);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String appId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		String nodeNo = WKF_NODE.resp_investigation.getNodeNo();
		String cusNo = mfBusApply.getCusNo();
		MfTuningReport mfTuningReport = new MfTuningReport();
		mfTuningReport.setAppId(mfBusApply.getAppId());
		mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
		if(mfTuningReport == null){
			mfTuningReport.setAppId(mfBusApply.getAppId());
			mfTuningReport.setCusNo(mfBusApply.getCusNo());
			mfTuningReport.setCusName(mfBusApply.getCusName());
		}
		
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.resp_investigation, appId, null, User.getRegNo(request));
		FormData formappreport0002 = formService.getFormData(formId);
		Map<String, String> map = new HashMap<String,String>();
		map.put("tmpBizNo", appId);
		map.put("showType", "resp_investigation_doc_form_show");
		map.put("nodeNo", WKF_NODE.resp_investigation.getNodeNo());
		String templateBizConfigId = modelInterfaceFeign.getfTemplateBizConfigId(map);
		getObjValue(formappreport0002, mfTuningReport);
		model.addAttribute("formappreport0002", formappreport0002);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("templateBizConfigId", templateBizConfigId);
		model.addAttribute("query", "");
		return "/component/app/MfTuningReport_Insert";
	}

	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formappreport0002 = formService.getFormData("appreport0002");
		 getFormValue(formappreport0002);
		MfTuningReport  mfTuningReport = new MfTuningReport();
		 setObjValue(formappreport0002, mfTuningReport);
		 mfTuningReportFeign.insert(mfTuningReport);
		 getObjValue(formappreport0002, mfTuningReport);
		 this.addActionMessage(model, "保存成功");
		 List<MfTuningReport> mfTuningReportList = (List<MfTuningReport>)mfTuningReportFeign.findByPage(this.getIpage(), mfTuningReport).getResult();
		model.addAttribute("formappreport0002", formappreport0002);
		model.addAttribute("mfTuningReportList", mfTuningReportList);
		model.addAttribute("query", "");
		return "/component/app/MfTuningReport_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String reportId) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request,response);
		FormData  formappreport0002 = formService.getFormData("appreport0002");
		 getFormValue(formappreport0002);
		MfTuningReport  mfTuningReport = new MfTuningReport();
		 mfTuningReport.setReportId(reportId);
		 mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
		 getObjValue(formappreport0002, mfTuningReport);
		model.addAttribute("formappreport0002", formappreport0002);
		model.addAttribute("query", "");
		 return "/component/app/MfTuningReport_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData,String reportId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfTuningReport mfTuningReport = new MfTuningReport();
		mfTuningReport.setReportId(reportId);
		mfTuningReportFeign.delete(mfTuningReport);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formappreport0002 = formService.getFormData("appreport0002");
		 getFormValue(formappreport0002);
		 boolean validateFlag = this.validateFormData(formappreport0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formappreport0002 = formService.getFormData("appreport0002");
		 getFormValue(formappreport0002);
		 boolean validateFlag = this.validateFormData(formappreport0002);
	}
	/**
	 * 判断业务是否生成了尽调报告
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByAppIdAjax")
	@ResponseBody
	public Map<String,Object> getByAppIdAjax(Model model, String appId) throws Exception{
		Map<String,Object>  resultMap = new HashMap<String,Object>();
		ActionContext.initialize(request,response);
		if(StringUtil.isNotEmpty(appId)){
			MfTuningReport  mfTuningReport = new MfTuningReport();
			mfTuningReport.setAppId(appId);
			mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
			if(mfTuningReport!=null){
				resultMap.put("uploadSize","1");
			}else{
				resultMap.put("uploadSize","0");
			}
		}else{
			resultMap.put("uploadSize","0");
		}
		return resultMap;
	}

	/**
	 * 获取业务中的尽调报告
	 * @param model
	 * @param appId
	 * @param type 1授信2业务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReportDtail")
	public String getReportDtail(Model model, String appId,String type) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		String formId =null;
		FormData  formappreport0002 =null;
		boolean ifWrite = false;//是否可以修改尽调报告
		String nodeNo = null;
		String cusNo = null;
		String kindNo = null;
		//授信业务
		if("1".equals(type)){
			MfTuningReport  mfTuningReport = new MfTuningReport();
			mfTuningReport.setAppId(appId);
			mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
			nodeNo = WKF_NODE.report.getNodeNo();
			if(mfTuningReport!=null){
				MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
				mfCusCreditApply.setCreditAppId(appId);
				mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
				if(mfCusCreditApply!=null){
					cusNo = mfCusCreditApply.getCusNo();
					if(StringUtil.isNotEmpty(mfCusCreditApply.getCreditApproveId())){
						Task task = wkfInterfaceFeign.getTask(mfCusCreditApply.getCreditApproveId(), null);
						if(task!=null){
							String assignee = taskFeign.getAssignee(task.getId());
							if(StringUtil.isNotEmpty(assignee) && BizPubParm.CREDIT_SUPPLEMENT.equals(task.getActivityName())
									&& User.getRegNo(request).equals(assignee)){
								ifWrite = true;
							}
						}
					}
					MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
					mfCusCreditConfig.setCreditId(mfCusCreditApply.getTemplateCreditId());
					mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
					model.addAttribute("busModel", mfCusCreditConfig.getBusModel());
					kindNo = mfCusCreditConfig.getAdaptationKindNo();
					String creditType =mfCusCreditApply.getCreditType();
					if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
						formId = prdctInterfaceFeign.getFormId(mfCusCreditApply.getTemplateCreditId(), WKF_NODE.report, null, null, User.getRegNo(request));
					} else if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
						formId = prdctInterfaceFeign.getFormId(mfCusCreditApply.getTemplateCreditId(), WKF_NODE.report, null, null, User.getRegNo(request));
					} else if (BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
						formId = prdctInterfaceFeign.getFormId(mfCusCreditApply.getTemplateCreditId(), WKF_NODE.report, null, null, User.getRegNo(request));
					}else {
					}
					model.addAttribute("creditType",creditType);
					if(ifWrite){
						formId = formId+"_edit";
					}else{
						formId = formId+"_show";
					}
					formappreport0002 = formService.getFormData(formId);
					getObjValue(formappreport0002, mfCusCreditApply);
					if(StringUtil.isNotEmpty(mfCusCreditApply.getCreditApproveId())){
						Task task = wkfInterfaceFeign.getTask(mfCusCreditApply.getCreditApproveId(), null);
						if(task!=null){
							String assignee = taskFeign.getAssignee(task.getId());
							if(StringUtil.isNotEmpty(assignee) && BizPubParm.CREDIT_SUPPLEMENT.equals(task.getActivityName())
									&& User.getRegNo(request).equals(assignee)){
								ifWrite = true;
							}
						}
					}
					//工程担保
					if(BizPubParm.BUS_MODEL_12.equals(mfCusCreditConfig.getBusModel())){
						model.addAttribute("projectShowFlag", BizPubParm.YES_NO_Y);
						if(StringUtil.isNotEmpty(mfCusCreditApply.getBreedNo())) {
							String per = "";
							String[] arrayStr = mfCusCreditApply.getBreedNo().split("\\|");
							for (String str : arrayStr) {
								per += "|" + new CodeUtils().getMapByKeyName("BUS_BREED").get(str);
							}
							this.changeFormProperty(formappreport0002, "breedName", "initValue", per.substring(1,per.length()));
						}
						//查询最近一期财务报表
						MfCusReportAcount mfCusReportAcount = mfCusReportAcountFeign.getReportOrdeyByWeek(cusNo);
						if(mfCusReportAcount!=null&&StringUtil.isNotEmpty(mfCusReportAcount.getWeeks())){
							String weeks = mfCusReportAcount.getWeeks();
							if(StringUtil.isNotEmpty(weeks)&&weeks.length()==6){
								weeks = weeks.substring(0,4)+"年"+weeks.substring(4,6)+"月";
							}else if(StringUtil.isNotEmpty(weeks)&&weeks.length()==4){
								weeks = weeks.substring(0,4)+"年";
							}
							model.addAttribute("weeks", weeks);
						}
					}
					//客户信息
					Map<String,String> cusInfoMap = new HashMap<String,String>();
					cusInfoMap.put("isLegalDegreeFlag","0");
					cusInfoMap.put("dishonestFlag","1");
					cusInfoMap.put("execNoticeFlag","1");
					if(StringUtil.isNotEmpty(cusNo)){
						MfCusCustomer mfCusCustomer = new MfCusCustomer();
						mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
						if(mfCusCustomer!=null){
							String cusType = mfCusCustomer.getCusType();
							if(cusType.startsWith("1")){
								MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
								mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
								if(mfCusCorpBaseInfo!=null){
									String legalDegree = mfCusCorpBaseInfo.getLegalDegree();
									if(StringUtil.isNotEmpty(legalDegree)&&("2".equals(legalDegree)||"3".equals(legalDegree)||"9".equals(legalDegree))){
										cusInfoMap.put("isLegalDegreeFlag","1");
									}
//
								}
							}
							MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
							mfCusDishonestInfo.setCusNo(cusNo);
							List<MfCusDishonestInfo> mfCusDishonestInfoList = mfCusDishonestInfoFeign.getAllList(mfCusDishonestInfo);
							if(mfCusDishonestInfoList.size()>0){
								cusInfoMap.put("dishonestFlag","0");
							}
							MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
							mfCusExecNotice.setCusNo(cusNo);
							List<MfCusExecNotice> mfCusExecNoticeList = mfCusExecNoticeFeign.getAllList(mfCusExecNotice);
							if(mfCusDishonestInfoList.size()>0){
								cusInfoMap.put("execNoticeFlag","0");
							}
							//获取实际控制人信息
							MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
							mfCusHighInfo.setCusNo(cusNo);
							mfCusHighInfo.setHighCusType("4");
							List<MfCusHighInfo> mfCusHighInfoList = cusInterfaceFeign.findMfCusHighInfoListByCusNo(mfCusHighInfo);
							if(mfCusHighInfoList.size()>0){
								cusInfoMap.put("highName",mfCusHighInfoList.get(0).getHighName());
								cusInfoMap.put("highPhone",mfCusHighInfoList.get(0).getPhone());
								cusInfoMap.put("underColleges",mfCusHighInfoList.get(0).getUnderColleges());
								cusInfoMap.put("major",mfCusHighInfoList.get(0).getMajor());
								cusInfoMap.put("education",mfCusHighInfoList.get(0).getEducation());
								cusInfoMap.put("majorDegree",mfCusHighInfoList.get(0).getMajorDegree());
								cusInfoMap.put("highIdNum",mfCusHighInfoList.get(0).getIdNum());
								cusInfoMap.put("highCusResume",mfCusHighInfoList.get(0).getHighCusResume());
							}
						}
					}
					if(StringUtil.isNotEmpty(mfCusCreditApply.getIsLegalDegreeFlag())){
						cusInfoMap.put("isLegalDegreeFlag",mfCusCreditApply.getIsLegalDegreeFlag());
					}
					if(StringUtil.isNotEmpty(appId)){
						//获取保证人
						List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(appId);
						String assureName ="";
						if(mfAssureInfoList!=null&&mfAssureInfoList.size()>0){
							for (int i = 0; i < mfAssureInfoList.size(); i++) {
								assureName =assureName+mfAssureInfoList.get(i).getAssureName()+",";
							}
						}
						cusInfoMap.put("assureName",assureName);
						//获取合作银行
						String agenciesButtName ="";
						String agenciesButtPhone ="";
						if(mfCusCreditApply!=null){
							String agenciesId = mfCusCreditApply.getAgenciesId();
							if(StringUtil.isNotEmpty(agenciesId)){
								cusInfoMap.put("agenciesName",mfCusCreditApply.getAgenciesName());
								String [] agenciesIds = agenciesId.split("\\|");
								for (int i = 0; i < agenciesIds.length; i++) {
									String id = agenciesIds[i];
									MfBusAgencies mfBusAgencies = new MfBusAgencies();
									mfBusAgencies.setAgenciesId(id);
									mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
									if(mfBusAgencies!=null){
										if(StringUtil.isNotEmpty(mfBusAgencies.getAgenciesButtName())){
											agenciesButtName =agenciesButtName+mfBusAgencies.getAgenciesButtName()+"|";
										}
										if(StringUtil.isNotEmpty(mfBusAgencies.getAgenciesButtPhone())){
											agenciesButtPhone =agenciesButtPhone+mfBusAgencies.getAgenciesButtPhone()+"|";
										}
									}
								}
								cusInfoMap.put("agenciesButtName",agenciesButtName);
								cusInfoMap.put("agenciesButtPhone",agenciesButtPhone);
							}
							getObjValue(formappreport0002, cusInfoMap);
						}
					}
				}
			}
		}else if ("2".equals(type)){
			MfTuningReport  mfTuningReport = new MfTuningReport();
			mfTuningReport.setAppId(appId);
			mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
			nodeNo = WKF_NODE.resp_investigation.getNodeNo();
			if(mfTuningReport!=null){
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(appId);
				mfBusApply = mfBusApplyFeign.getById(mfBusApply);
				cusNo = mfBusApply.getCusNo();
				if(mfBusApply!=null){
					if(StringUtil.isNotEmpty(mfBusApply.getAppId())){
						Task task = wkfInterfaceFeign.getTask(mfBusApply.getAppId(), null);
						if(task!=null){
							String assignee = taskFeign.getAssignee(task.getId());
							if(StringUtil.isNotEmpty(assignee) && WKF_NODE.supplement_data.getNodeNo().equals(task.getActivityName())
									&& User.getRegNo(request).equals(assignee)){
								ifWrite = true;
							}
						}
					}

					formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.resp_investigation, null, null, User.getRegNo(request));
					if(!BizPubParm.BUS_MODEL_12.equals(mfBusApply.getBusModel())){
						if(ifWrite){
							formId = formId+"_edit";
						}else{
							formId = formId+"_show";
						}
					}
					formappreport0002 = formService.getFormData(formId);
					String busModel = mfBusApply.getBusModel();
					model.addAttribute("busModel", busModel);
					if(BizPubParm.BUS_MODEL_12.equals(busModel)){
						MfBusApplySecond mfBusApplySecond = new MfBusApplySecond();
						mfBusApplySecond.setAppId(mfBusApply.getAppId());
						mfBusApplySecond = appInterfaceFeign.getMfBusApplySecondByAppId(mfBusApplySecond);
						if(mfBusApplySecond != null){
							mfBusApply.setAssureAmt(mfBusApplySecond.getAssureAmt());
							mfBusApply.setAssureAmtRate(mfBusApplySecond.getAssureAmtRate());
							mfBusApply.setProjectPayRemark(mfBusApplySecond.getProjectPayRemark());
							mfBusApply.setBeneficiary(mfBusApplySecond.getBeneficiary());
							mfBusApply.setGuaEndDate(mfBusApplySecond.getGuaEndDate());
							mfBusApply.setShouldFeeSum(mfBusApplySecond.getShouldFeeSum());
							mfBusApply.setCorpQua(mfBusApplySecond.getCorpQua());
							mfBusApply.setNetAssets(mfBusApplySecond.getNetAssets());
							mfBusApply.setAuthority(mfBusApplySecond.getAuthority());
							mfBusApply.setSurveyOpinion(mfBusApplySecond.getSurveyOpinion());
							mfBusApply.setStartUpSituation(mfBusApplySecond.getStartUpSituation());
							mfBusApply.setConstructionScope(mfBusApplySecond.getConstructionScope());
							mfBusApply.setOldAppName(mfBusApplySecond.getOldAppName());
							mfBusApply.setCollectAccount(mfBusApplySecond.getCollectAccount());
							mfBusApply.setCollectAccName(mfBusApplySecond.getCollectAccName());
							mfBusApply.setCollectBank(mfBusApplySecond.getCollectBank());
							mfBusApply.setCollectAccId(mfBusApplySecond.getCollectAccId());
							mfBusApply.setBondAccount(mfBusApplySecond.getBondAccount());
							mfBusApply.setBondAccName(mfBusApplySecond.getBondAccName());
							mfBusApply.setBondBank(mfBusApplySecond.getBondBank());
							mfBusApply.setBondAccId(mfBusApplySecond.getBondAccId());
						}
						String invoiceMationId = mfBusApply.getInvoiceMationId();
						if(StringUtil.isNotEmpty(invoiceMationId)){
							MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
							mfCusInvoiceMation.setId(invoiceMationId);
							mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
							if(mfCusInvoiceMation != null){
								mfBusApply.setTaxpayerNo(mfCusInvoiceMation.getTaxpayerNo());
								mfBusApply.setAddress(mfCusInvoiceMation.getAddress());
								mfBusApply.setTel(mfCusInvoiceMation.getTel());
								mfBusApply.setBillBankName(mfCusInvoiceMation.getBankName());
								mfBusApply.setAccountNumber(mfCusInvoiceMation.getAccountNumber());
							}
						}
						MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
						mfCusCreditContract.setCreditAppId(mfBusApply.getCreditAppId());
						mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
						if(mfCusCreditContract!=null){
							mfBusApply.setCreditSum(mfCusCreditContract.getCreditSum());
							mfBusApply.setCreditAmt(mfCusCreditContract.getAuthBal());
						}
					}
					getObjValue(formappreport0002, mfBusApply);
					kindNo = mfBusApply.getKindNo();
					model.addAttribute("appId",mfBusApply.getAppId());
					model.addAttribute("channelType",mfBusApply.getChannelType());
					//客户信息
					Map<String,String> cusInfoMap = new HashMap<String,String>();
					cusInfoMap.put("isLegalDegreeFlag",mfBusApply.getIsLegalDegreeFlag());
//					cusInfoMap.put("legalUniversity","");
					cusInfoMap.put("guaranteeOuterFlag","1");
					cusInfoMap.put("dishonestFlag","1");
					cusInfoMap.put("execNoticeFlag","1");
					if(StringUtil.isNotEmpty(cusNo)){
						MfCusCustomer mfCusCustomer = new MfCusCustomer();
						mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
						if(mfCusCustomer!=null){
							String cusType = mfCusCustomer.getCusType();
							if(cusType.startsWith("1")){
								MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
								mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
								if(mfCusCorpBaseInfo!=null){
									String legalDegree = mfCusCorpBaseInfo.getLegalDegree();
									if(StringUtil.isEmpty(mfBusApply.getIsLegalDegreeFlag())&&StringUtil.isNotEmpty(legalDegree)&&("2".equals(legalDegree)||"3".equals(legalDegree)||"9".equals(legalDegree))){
										cusInfoMap.put("isLegalDegreeFlag","1");
									}
//									String legalUniversity = mfCusCorpBaseInfo.getLegalUniversity();
//									if(StringUtil.isNotEmpty(legalUniversity )){
//										cusInfoMap.put("legalUniversity",legalUniversity);
//									}
								}
							}
							MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
							mfCusGuaranteeOuter.setCusNo(cusNo);
							List<MfCusGuaranteeOuter> mfCusGuaranteeOuterList  = cusInterfaceFeign.getMfCusGuaranteeOuterList(mfCusGuaranteeOuter);
							if(mfCusGuaranteeOuterList.size()>0){
								cusInfoMap.put("guaranteeOuterFlag","0");
							}
							MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
							mfCusDishonestInfo.setCusNo(cusNo);
							List<MfCusDishonestInfo> mfCusDishonestInfoList = mfCusDishonestInfoFeign.getAllList(mfCusDishonestInfo);
							if(mfCusDishonestInfoList.size()>0){
								cusInfoMap.put("dishonestFlag","0");
							}
							MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
							mfCusExecNotice.setCusNo(cusNo);
							List<MfCusExecNotice> mfCusExecNoticeList = mfCusExecNoticeFeign.getAllList(mfCusExecNotice);
							if(mfCusDishonestInfoList.size()>0){
								cusInfoMap.put("execNoticeFlag","0");
							}
							//获取实际控制人信息
							MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
							mfCusHighInfo.setCusNo(cusNo);
							mfCusHighInfo.setHighCusType("4");
							List<MfCusHighInfo> mfCusHighInfoList = cusInterfaceFeign.findMfCusHighInfoListByCusNo(mfCusHighInfo);
							if(mfCusHighInfoList.size()>0){
								cusInfoMap.put("highName",mfCusHighInfoList.get(0).getHighName());
								cusInfoMap.put("highPhone",mfCusHighInfoList.get(0).getPhone());
								cusInfoMap.put("underColleges",mfCusHighInfoList.get(0).getUnderColleges());
								cusInfoMap.put("major",mfCusHighInfoList.get(0).getMajor());
								cusInfoMap.put("education",mfCusHighInfoList.get(0).getEducation());
								cusInfoMap.put("majorDegree",mfCusHighInfoList.get(0).getMajorDegree());
								cusInfoMap.put("highIdNum",mfCusHighInfoList.get(0).getIdNum());
								cusInfoMap.put("highCusResume",mfCusHighInfoList.get(0).getHighCusResume());
							}
						}
					}
					if(StringUtil.isNotEmpty(mfBusApply.getIsLegalDegreeFlag())){
						cusInfoMap.put("isLegalDegreeFlag",mfBusApply.getIsLegalDegreeFlag());
					}
					if(StringUtil.isNotEmpty(appId)) {
						//获取保证人
						List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(appId);
						String assureName = "";
						if (mfAssureInfoList != null && mfAssureInfoList.size() > 0) {
							for (int i = 0; i < mfAssureInfoList.size(); i++) {
								assureName = assureName + mfAssureInfoList.get(i).getAssureName() + ",";
							}
						}
						cusInfoMap.put("assureName", assureName);
						//获取合作银行
						String agenciesId = mfBusApply.getAgenciesId();
						if (StringUtil.isNotEmpty(agenciesId)) {
							MfBusAgencies mfBusAgencies = new MfBusAgencies();
							mfBusAgencies.setAgenciesId(agenciesId);
							mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
							if (mfBusAgencies != null) {
								cusInfoMap.put("agenciesName", mfBusAgencies.getAgenciesName());
								cusInfoMap.put("agenciesButtName", mfBusAgencies.getAgenciesButtName());
								cusInfoMap.put("agenciesButtPhone", mfBusAgencies.getAgenciesButtPhone());
							}
						}
					}
					getObjValue(formappreport0002, cusInfoMap);
					this.changeFormProperty(formappreport0002, "applyOptionsId", "initValue", DateUtil.getShowDateTime(mfBusApply.getApplyOptionsId()));
					this.changeFormProperty(formappreport0002, "guaEndDate", "initValue", DateUtil.getShowDateTime(mfBusApply.getGuaEndDate()));
				}
			}
		}
		model.addAttribute("ifWrite", ifWrite);
		model.addAttribute("appId", appId);
		model.addAttribute("entranceType", type);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("formappreport0002", formappreport0002);
		model.addAttribute("kindNo", kindNo);
		model.addAttribute("query", "");
		return "/component/app/MfTuningReport_Query";
	}
}
