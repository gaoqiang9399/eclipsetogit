package app.component.examine.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.calc.core.entity.MfRepayPlan;
import app.component.common.PasConstant;
import app.component.compensatory.entity.MfBusCompensatoryConfirm;
import app.component.cus.report.entity.MfCusReportAcount;
import app.component.cus.report.feign.MfCusReportAcountFeign;
import app.component.eval.entity.AppEval;
import app.component.eval.feign.AppEvalFeign;
import app.component.eval.feign.AppPropertyFeign;
import app.component.examine.entity.*;
import app.component.nmd.entity.ParmDic;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pfs.entity.CusFinMain;
import app.component.pfsinterface.PfsInterfaceFeign;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.entity.SysTaskInfoIgnore;
import app.component.sys.feign.SysTaskInfoFeign;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.examine.feign.MfExamineApproveFeign;
import app.component.examine.feign.MfExamineDetailFeign;
import app.component.examine.feign.MfExamineHisFeign;
import app.component.examine.feign.MfExamineTemplateConfigFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.fiveclass.entity.MfPactFiveclass;
import app.component.pact.fiveclass.feign.MfPactFiveclassFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: MfExamineHisAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Feb 13 16:22:29 CST 2017
 **/
@Controller
@RequestMapping(value = "/mfExamineHis")
public class MfExamineHisController extends BaseFormBean {
	private static Logger logger = LoggerFactory.getLogger(MfExamineHisController.class);
	// 注入MfExamineHisBo
	@Autowired
	private MfExamineHisFeign mfExamineHisFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private MfExamineDetailFeign mfExamineDetailFeign;
	@Autowired
	private MfPactFiveclassFeign mfPactFiveclassFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfExamineTemplateConfigFeign mfExamineTemplateConfigFeign;
	@Autowired
	private MfExamineApproveFeign mfExamineApproveFeign;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
    private SysTaskInfoFeign sysTaskInfoFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private PfsInterfaceFeign pfsInterfaceFeign;
	@Autowired
	private AppEvalFeign appEvalFeign;
	@Autowired
	private MfCusReportAcountFeign mfCusReportAcountFeign;
	@Autowired
	private AppPropertyFeign appPropertyFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "MfExamineHis_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			mfExamineHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfExamineHis.setCriteriaList(mfExamineHis, ajaxData);// 我的筛选
			// this.getRoleConditions(mfExamineHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfExamineHisFeign.findByPage(ipage, mfExamineHis);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
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
	 * 
	 * 方法描述： 获得检查历史列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-17 下午3:20:20
	 */
	@ResponseBody
	@RequestMapping(value = "/getExamHisListAjax")
	public Map<String, Object> getExamHisListAjax(String pactId, int pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			mfExamineHis.setPactId(pactId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfExamineHisFeign.findByPage(ipage, mfExamineHis);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tableexamhis0001", "tableTag", (List) ipage.getResult(), ipage, true);
			dataMap.put("tableData", tableHtml);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData,String entrance) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formexamhis001 = formService.getFormData("examhis001");
			getFormValue(formexamhis001, getMapByJson(ajaxData));
			if (this.validateFormData(formexamhis001)) {
				MfExamineHis mfExamineHis = new MfExamineHis();
				setObjValue(formexamhis001, mfExamineHis);
				if(StringUtil.isEmpty(entrance)){//空或1为默认入口，2为搜索中的风险登记入口
					entrance="1";
				}
				String examiningFlag = mfExamineHisFeign.getCheckExistExaminingFlag(mfExamineHis.getFincId(),mfExamineHis.getCusNameSupplier(),entrance,mfExamineHis.getPactId());
				// 如果存在审批进行的贷后检查，不再重复保存
				if (BizPubParm.YES_NO_Y.equals(examiningFlag)) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NO_TASK_EXIST.getMessage());
					return dataMap;
				}
				mfExamineHisFeign.insert(mfExamineHis);
				String examHisId = mfExamineHis.getExamHisId();
				String templateId = String.valueOf(getMapByJson(ajaxData).get("templateId"));
				MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
				mfExamineTemplateConfig.setTemplateId(templateId);
				mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
				dataMap.putAll(mfExamineHisFeign.getListData(examHisId, templateId));
				dataMap.put("flag", "success");
				if (mfExamineTemplateConfig.getFormTemplate() == null) {
					dataMap.put("baseForm", mfExamineTemplateConfig.getBaseFormTemplate());
				} else {
					dataMap.put("baseForm", mfExamineTemplateConfig.getFormTemplate());
				}
				dataMap.put("flag", "success");
				dataMap.put("entityData", mfExamineHis);
				dataMap.put("examHisId", mfExamineHis.getExamHisId());
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/insertForCusAjax")
	public Map<String, Object> insertForCusAjax(String ajaxData,String entrance) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formexamhisForCus = formService.getFormData("examhisForCus");
			getFormValue(formexamhisForCus, getMapByJson(ajaxData));
			if (this.validateFormData(formexamhisForCus)) {
				MfExamineHis mfExamineHis = new MfExamineHis();
				setObjValue(formexamhisForCus, mfExamineHis);
				if(StringUtil.isEmpty(entrance)){//空或1为默认入口，2为搜索中的风险登记入口
					entrance="1";
				}
				String examiningFlag = mfExamineHisFeign.getCheckExistExaminingFlag(mfExamineHis.getFincId(),mfExamineHis.getCusNoSupplier(),entrance,mfExamineHis.getPactId());
				// 如果存在审批进行的贷后检查，不再重复保存
				if (BizPubParm.YES_NO_Y.equals(examiningFlag)) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NO_TASK_EXIST.getMessage());
					return dataMap;
				}
				mfExamineHisFeign.insert(mfExamineHis);
				String examHisId = mfExamineHis.getExamHisId();
				String templateId = String.valueOf(getMapByJson(ajaxData).get("templateId"));
				MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
				mfExamineTemplateConfig.setTemplateId(templateId);
				mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
				dataMap.putAll(mfExamineHisFeign.getListData(examHisId, templateId));
				dataMap.put("flag", "success");
				if (StringUtil.isEmpty(mfExamineTemplateConfig.getFormTemplate())) {
					dataMap.put("baseForm", mfExamineTemplateConfig.getBaseFormTemplate());
				} else {
					dataMap.put("baseForm", mfExamineTemplateConfig.getFormTemplate());
				}
				dataMap.put("flag", "success");
				dataMap.put("entityData", mfExamineHis);
				dataMap.put("examHisId", mfExamineHis.getExamHisId());
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：保存检查卡
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-27 上午10:34:17
	 */
	@ResponseBody
	@RequestMapping(value = "/examCardSaveAjax")
	public Map<String, Object> examCardSaveAjax(String examHisId, String templateId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		ajaxData =StringEscapeUtils.unescapeHtml4(ajaxData);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap.put("examHisId", examHisId);
			dataMap.put("templateId", templateId);
			dataMap.put("ajaxData", ajaxData);
			dataMap.put("regNo",User.getRegNo(request));
			dataMap.put("regName",User.getRegName(request));
			MfExamineHis mfExamineHis = new MfExamineHis();
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
			/*
			 * if(BizPubParm.EXAMINE_STAGE_CARD.equals(mfExamineHis.
			 * getExamineStage())){ dataMap.put("flag", "error");
			 * dataMap.put("msg", MessageEnum.NO_TASK_EXIST.getMessage());
			 * return dataMap; }
			 */
			mfExamineHisFeign.examCardSave(dataMap);
			dataMap.putAll(mfExamineHisFeign.getListData(examHisId, templateId));
			dataMap.put("flag", "success");
			dataMap.put("entityData", mfExamineHis);
			dataMap.put("examHisId", mfExamineHis.getExamHisId());
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formexamhis0002 = formService.getFormData("examhis0002");
		getFormValue(formexamhis0002, getMapByJson(ajaxData));
		MfExamineHis mfExamineHisJsp = new MfExamineHis();
		setObjValue(formexamhis0002, mfExamineHisJsp);
		MfExamineHis mfExamineHis = mfExamineHisFeign.getById(mfExamineHisJsp);
		if (mfExamineHis != null) {
			try {
				mfExamineHis = (MfExamineHis) EntityUtil.reflectionSetVal(mfExamineHis, mfExamineHisJsp,
						getMapByJson(ajaxData));
				mfExamineHisFeign.update(mfExamineHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String formId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			FormData formexamhis001 = formService.getFormData(formId);
			if (formexamhis001.getFormId() == null) {
				formexamhis001 = formService.getFormData("baseexaminfo");
			}
			getFormValue(formexamhis001, getMapByJson(ajaxData));
			if (this.validateFormData(formexamhis001)) {
				mfExamineHis = new MfExamineHis();
				setObjValue(formexamhis001, mfExamineHis);
				//如果回访日期不为空则检查结束时间为回访日期
				if(StringUtil.isNotEmpty(mfExamineHis.getReturnVisitDate())) {
					mfExamineHis.setEndDate(mfExamineHis.getReturnVisitDate());
				}
				//如果页面上没有传入检查结束时间，则把当前时间传入
				if(StringUtil.isEmpty(mfExamineHis.getEndDate())){
					mfExamineHis.setEndDate(DateUtil.getDate());
				}
				mfExamineHis.setExamineStage("3");
				//判断是否需要走风控
				if(!"1".equals(mfExamineHis.getSideLevel())){
					mfExamineHis.setIfRiskApprove("1");
				}else{
					if(StringUtil.isNotEmpty(mfExamineHis.getSideLevel())&&!mfExamineHis.getRiskLevel().equals(mfExamineHis.getSideLevel())){
						mfExamineHis.setIfRiskApprove("1");
					}else {
						mfExamineHis.setIfRiskApprove("0");
					}
				}

				mfExamineHisFeign.updateSavePdf(mfExamineHis);
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
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjaxSecond")
	public Map<String, Object> updateAjaxSecond(String ajaxData,String ajaxData2) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			FormData formexamhis002 = formService.getFormData("examhis002");
			FormData formexamhis003 = formService.getFormData("examhis003");
			getFormValue(formexamhis002, getMapByJson(ajaxData));
			getFormValue(formexamhis003, getMapByJson(ajaxData2));
			if (this.validateFormData(formexamhis002)) {
				mfExamineHis = new MfExamineHis();
				setObjValue(formexamhis002, mfExamineHis);
				setObjValue(formexamhis003, mfExamineHis);
				//校验报表
				MfExamineHis mfExamineHisQuery = new MfExamineHis();
				mfExamineHisQuery.setExamHisId(mfExamineHis.getExamHisId());
				mfExamineHisQuery = mfExamineHisFeign.getById(mfExamineHisQuery);
				int  termValidity = appPropertyFeign.getTermValidity(mfExamineHisQuery.getCusNo(),mfExamineHisQuery.getRptDate(), mfExamineHisQuery.getRptDate().substring(4,6),mfExamineHisQuery.getAccountId());
				if("12".equals(mfExamineHisQuery.getRptDate().substring(4,6))){
					if(termValidity<3){
						dataMap.put("flag", "error");
						dataMap.put("msg", "请上传连续的财务报表");
						return dataMap;
					}
				}else{
					if(termValidity<2){
						dataMap.put("flag", "error");
						dataMap.put("msg", "请上传连续的财务报表");
						return dataMap;
					}
				}
				mfExamineHis.setExamineStage("2");
				//存储需要计算的财务报表
				String templateAccount = appPropertyFeign.getExamineAccount(mfExamineHisQuery.getCusNo(),mfExamineHisQuery.getRptDate(), mfExamineHisQuery.getRptDate().substring(4,6),mfExamineHisQuery.getAccountId());
				mfExamineHis.setTemplateAccount(templateAccount);

				mfExamineHisFeign.update(mfExamineHis);
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
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 把新的检查文档模板名称更新到检查登记历史表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-14 下午3:42:36
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDocuTemplateAjax")
	public Map<String, Object> updateDocuTemplateAjax(String examHisId, String docuTemplate) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis.setDocuTemplate(docuTemplate);
			mfExamineHisFeign.update(mfExamineHis);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：提交贷后检查到审批
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-14 下午5:17:05
	 */
	@ResponseBody
	@RequestMapping(value = "/submitAjax")
	public Map<String, Object> submitAjax(String examHisId,String entrance,String firstApprovalUser) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfExamineHis mfExamineHis = new MfExamineHis();
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
			// 如果检查状态为已提交或已完成，不再重复提交
			if ( BizPubParm.EXAMINE_STAGE_COMPLETE.equals(mfExamineHis.getExamineStage())) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.NO_TASK_EXIST.getMessage());
				return dataMap;
			}
			if(StringUtil.isEmpty(entrance)){//空或1为默认入口，2为搜索中的风险登记入口
				entrance="1";
			}
			String nextUser = firstApprovalUser;
			mfExamineHis = mfExamineHisFeign.submitProcess(examHisId,entrance,User.getRegNo(request),User.getRegName(request),User.getOrgNo(request),nextUser);
			if (StringUtil.isEmpty(mfExamineHis.getExamProcessId())) {
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保后检查完成"));
			} else {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfExamineHis.getApproveNodeName());
				paramMap.put("opNo", mfExamineHis.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 审批提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-15 下午9:39:55
	 */
	@ResponseBody
	@RequestMapping(value = "/submitUpdateAjax")
	public Map<String, Object> submitUpdateAjax(String examHisId, String ajaxData, String activityType, String taskId,
			String transition, String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfExamineHis mfExamineHis = new MfExamineHis();
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
			dataMap = getMapByJson(ajaxData);
			MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
			mfPactFiveclass.setFincId(mfExamineHis.getFincId());
			mfPactFiveclass = mfPactFiveclassFeign.getByFincId(mfPactFiveclass);
			if (mfPactFiveclass != null) {
				dataMap.put("fiveclass", mfPactFiveclass.getFiveclass());
			} else {
				dataMap.put("fiveclass", "1");
			}
			dataMap.put("activityType", activityType);
			dataMap.put("orgNo", User.getOrgNo(request));
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfExamineHis);
			dataMap.put("mfExamineHis", mfExamineHis);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			Result res = mfExamineHisFeign.doCommit(taskId, examHisId, opinionType, approvalOpinion, transition,
					User.getRegNo(request), nextUser,  dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String examHisId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formexamhis0002 = formService.getFormData("examhis0002");
		MfExamineHis mfExamineHis = new MfExamineHis();
		mfExamineHis.setExamHisId(examHisId);
		mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
		getObjValue(formexamhis0002, mfExamineHis, formData);
		if (mfExamineHis != null) {
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
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String examHisId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		mfExamineHis.setExamHisId(examHisId);
		try {
			mfExamineHisFeign.delete(mfExamineHis);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获得检查表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-13 下午8:11:59
	 */
	@ResponseBody
	@RequestMapping(value = "/getFormTemplateAjax")
	public Map<String, Object> getFormTemplateAjax(String formTemplate, String examHisId, String query)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formData = formService.getFormData(formTemplate);
			MfExamineHis mfExamineHis = new MfExamineHis();
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
			MfExamineDetail mfExamineDetail = new MfExamineDetail();
			mfExamineDetail.setExamHisId(examHisId);
			map = mfExamineDetailFeign.getExamDetailMap(mfExamineDetail);
			String htmlStr = "";
			if (formData.getFormId() == null) {
				formData = formService.getFormData(BizPubParm.FORMTEMPLATE);
			}
			// 设置表单元素不可编辑
			FormActive[] list = formData.getFormActives();
			for (int i = 0; i < list.length; i++) {
				FormActive formActive = list[i];
				formActive.setReadonly("1");
			}
			if (map != null && !map.isEmpty()) {
				map.put("remark", mfExamineHis.getRemark());
				getObjValue(formData, map);
			}
			htmlStr = jsonFormUtil.getJsonStr(formData, "bootstarpTag", query);
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 新增时或编辑贷后检查
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-15 上午10:24:45
	 */
	@ResponseBody
	@RequestMapping(value = "/getEditFormTemplateAjax")
	public Map<String, Object> getEditFormTemplateAjax(String formTemplate, String examHisId, int pactNo, String query)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formData = formService.getFormData(formTemplate);
			String htmlStr = "";
			if (formData.getFormId() == null) {
				formData = formService.getFormData(BizPubParm.FORMTEMPLATE);
			}
			map.put("examHisId", examHisId);
			map.put("pactNo", pactNo);
			getObjValue(formData, map);
			htmlStr = jsonFormUtil.getJsonStr(formData, "bootstarpTag", query);
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @param examOpNo
	 * @param beginDate
	 * @param endDate
	 * @param templateId
	 * @param examHisId
	 * @param fincId
	 * @param appId
	 * @param pactId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String docuTemplate, String examOpNo, String beginDate, String endDate,
			String templateId, String examHisId, String fincId, String appId, String pactId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formexamhis001 = formService.getFormData("examhis001");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (docuTemplate != null) {
			dataMap.put("examOpNo", examOpNo);
			dataMap.put("beginDate", beginDate);
			dataMap.put("endDate", endDate);
			dataMap.put("templateId", templateId);
			dataMap.put("docuTemplate", docuTemplate);
			dataMap.put("examHisId", examHisId);
			getObjValue(formexamhis001, dataMap);
		} else {
			MfExamineHis mfExamineHis = new MfExamineHis();
			examHisId = WaterIdUtil.getWaterId("exam");
			mfExamineHis.setExamHisId(examHisId);
			getObjValue(formexamhis001, mfExamineHis);
		}
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		// 通过查询功能进入贷后，无法取得fincId
		mfBusFincApp.setAppId(appId);
		mfBusFincApp.setPactId(pactId);
		mfBusFincApp = pactInterfaceFeign.getByFincIdOrwkfId(mfBusFincApp);
		mfBusFincApp = pactInterfaceFeign.processDataForFincApp(mfBusFincApp);// 处理利率展示格式
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		// mfBusPact =
		// pactInterfaceFeign.processDataForPact(mfBusPact);//处理利率展示格式
		getObjValue(formexamhis001, mfBusPact);
		mfBusFincApp.setTermShow(mfBusPact.getTermShow());
		getObjValue(formexamhis001, mfBusFincApp);
		/*
		 * //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位 Map<String, ParmDic> rateTypeMap
		 * = new CodeUtils().getMapObjByKeyName("RATE_TYPE"); String rateUnit=
		 * rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		 * this.changeFormProperty(formexamhis001, "fincRate", "unit",
		 * rateUnit);
		 */
		String cusNo = mfBusFincApp.getCusNo();
		appId = mfBusFincApp.getAppId();
		model.addAttribute("formexamhis001", formexamhis001);
		model.addAttribute("appId", appId);
		model.addAttribute("cusNo", cusNo);

		return "MfExamineHis_Insert";
	}

	/**
	 * 
	 * 方法描述： 打开发起贷后检查页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-26 下午10:18:55
	 */
	@RequestMapping(value = "/ExamineApply")
	@SuppressWarnings("unchecked")
	public String ExamineApply(Model model, String docuTemplate, String examOpNo, String beginDate, String endDate,
							   String templateId, String examHisId, String fincId, String appId, String pactId,String entrance) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
		model.addAttribute("scNo", scNo);
		FormData formexamhis001 = formService.getFormData("examhis001");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (docuTemplate != null) {
			dataMap.put("examOpNo", examOpNo);
			dataMap.put("beginDate", beginDate);
			dataMap.put("endDate", endDate);
			dataMap.put("templateId", templateId);
			dataMap.put("docuTemplate", docuTemplate);
			dataMap.put("examHisId", examHisId);
			getObjValue(formexamhis001, dataMap);
		} else {
			MfExamineHis mfExamineHis = new MfExamineHis();
			examHisId = WaterIdUtil.getWaterId("exam");
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis.setFiveclass(BizPubParm.FIN_TYPE_1);
			getObjValue(formexamhis001, mfExamineHis);
		}
		Map<String, Object> initDataMap = new HashMap<String, Object>();
		initDataMap = mfExamineHisFeign.initInputData(null,"", fincId);
		String getFlag = String.valueOf(initDataMap.get("getFlag"));
		dataMap = JSONObject.fromObject(initDataMap);
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		// 通过查询功能进入贷后，无法取得fincId
		mfBusFincApp.setAppId(appId);
		mfBusFincApp.setPactId(pactId);
		mfBusFincApp = pactInterfaceFeign.getByFincIdOrwkfId(mfBusFincApp);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);

		request.setAttribute("cusNo", mfBusFincApp.getCusNo());
		request.setAttribute("fincId", mfBusFincApp.getFincId());
		request.setAttribute("getFlag", getFlag);
		List<MfExamineCard> examCardListDataList = (List<MfExamineCard>) initDataMap.get("examCardListData");
		model.addAttribute("examCardListDataList", examCardListDataList);
		dataMap.put("examCardListDataList", examCardListDataList);
		request.setAttribute("initDataMap", initDataMap);
		String cusNo = mfBusFincApp.getCusNo();
		appId = mfBusFincApp.getAppId();
		String returnStr = "ExamineApply";
		List<OptionsList> examOptionsList = mfExamineHisFeign.getConfigMatchedByBussList(pactId, "finc");
		if ("add".equals(getFlag)) {
			getObjValue(formexamhis001, mfBusPact);
			// 解决合同到期日被借据中上次检查日期覆盖掉问题
			mfBusFincApp.setEndDate(mfBusPact.getEndDate());
			getObjValue(formexamhis001, mfBusFincApp);
			// 添加贷后检查模型数据
			this.changeFormProperty(formexamhis001, "templateId", "optionArray", examOptionsList);
		} else if ("apply".equals(getFlag)) {
			MfExamineHis mfExamineHis = (MfExamineHis) JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class);
//			MfExamineHis mfExamineHis = (MfExamineHis) initDataMap.get("mfExamineHis");
			templateId = mfExamineHis.getTemplateId();
			examHisId = mfExamineHis.getExamHisId();
			getObjValue(formexamhis001, mfExamineHis);
			getObjValue(formexamhis001, mfBusPact);
			mfBusFincApp.setEndDate(mfBusPact.getEndDate());
			getObjValue(formexamhis001, mfBusFincApp);
			// 添加贷后检查模型数据
			examOptionsList = new ArrayList<OptionsList>();
			OptionsList optionsList = new OptionsList();
			optionsList.setOptionLabel(mfExamineHis.getTemplateName());
			optionsList.setOptionValue(mfExamineHis.getTemplateId());
			examOptionsList.add(optionsList);
			this.changeFormProperty(formexamhis001, "templateId", "optionArray", examOptionsList);
			model.addAttribute("templateId", templateId);
			model.addAttribute("resultFormId", String.valueOf(initDataMap.get("resultFormId")));
		} else if ("detail".equals(getFlag)) {
			MfExamineHis mfExamineHis =JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class) ;
			templateId = mfExamineHis.getTemplateId();
			examHisId = mfExamineHis.getExamHisId();
			getObjValue(formexamhis001, mfExamineHis);
			getObjValue(formexamhis001, mfBusPact);
			mfBusFincApp.setEndDate(mfBusPact.getEndDate());
			getObjValue(formexamhis001, mfBusFincApp);
			// 添加贷后检查模型数据
			this.changeFormProperty(formexamhis001, "templateId", "optionArray", examOptionsList);
			mfExamineHis = new MfExamineHis();
			mfExamineHis.setPactId(mfBusPact.getPactId());
			List<MfExamineHis> mfExamineHisList = mfExamineHisFeign.getMfExamineHisList(mfExamineHis);
			model.addAttribute("mfExamineHisList", mfExamineHisList);
			model.addAttribute("templateId", templateId);
			model.addAttribute("resultFormId", String.valueOf(initDataMap.get("resultFormId")));
			String entDetailflag = "detail";
			model.addAttribute("entDetailflag", entDetailflag);
			returnStr = "ExamineDetailResult";
		}else {
		}
		model.addAttribute("formexamhis001", formexamhis001);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		model.addAttribute("getFlag", getFlag);
		model.addAttribute("examHisId", examHisId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("appId", appId);
		model.addAttribute("pactId", pactId);
		model.addAttribute("entrance", entrance);
		return "component/examine/"+returnStr;
	}
	
	/**
	 * 
	 * 方法描述： 打开发起贷后检查页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-26 下午10:18:55
	 */
	@RequestMapping(value = "/ExamineApply4Risk")
	@SuppressWarnings("unchecked")
	public String ExamineApply4Risk(Model model, String docuTemplate, String examOpNo, String beginDate, String endDate,
			String templateId, String examHisId, String fincId, String appId, String pactId,String entrance) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
		model.addAttribute("scNo", scNo);
		FormData formexamhis001 = formService.getFormData("examhis001");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (docuTemplate != null) {
			dataMap.put("examOpNo", examOpNo);
			dataMap.put("beginDate", beginDate);
			dataMap.put("endDate", endDate);
			dataMap.put("templateId", templateId);
			dataMap.put("docuTemplate", docuTemplate);
			dataMap.put("examHisId", examHisId);
			getObjValue(formexamhis001, dataMap);
		} else {
			MfExamineHis mfExamineHis = new MfExamineHis();
			examHisId = WaterIdUtil.getWaterId("exam");
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis.setFiveclass(BizPubParm.FIN_TYPE_1);
			getObjValue(formexamhis001, mfExamineHis);
		}
		Map<String, Object> initDataMap = new HashMap<String, Object>();
		initDataMap = mfExamineHisFeign.initInputData4Risk(null, fincId);
		String getFlag = String.valueOf(initDataMap.get("getFlag"));
		dataMap = JSONObject.fromObject(initDataMap);
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		// 通过查询功能进入贷后，无法取得fincId
		mfBusFincApp.setAppId(appId);
		mfBusFincApp.setPactId(pactId);
		mfBusFincApp = pactInterfaceFeign.getByFincIdOrwkfId(mfBusFincApp);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);

		request.setAttribute("cusNo", mfBusFincApp.getCusNo());
		request.setAttribute("fincId", mfBusFincApp.getFincId());
		request.setAttribute("getFlag", getFlag);
		List<MfExamineCard> examCardListDataList = (List<MfExamineCard>) initDataMap.get("examCardListData");
		model.addAttribute("examCardListDataList", examCardListDataList);
		dataMap.put("examCardListDataList", examCardListDataList);
		request.setAttribute("initDataMap", initDataMap);
		String cusNo = mfBusFincApp.getCusNo();
		appId = mfBusFincApp.getAppId();
		String returnStr = "ExamineApply4Risk";
		List<OptionsList> examOptionsList = mfExamineHisFeign.getConfigMatchedByBussList(pactId, "finc");
		if ("add".equals(getFlag)) {
			getObjValue(formexamhis001, mfBusPact);
			// 解决合同到期日被借据中上次检查日期覆盖掉问题
			mfBusFincApp.setEndDate(mfBusPact.getEndDate());
			getObjValue(formexamhis001, mfBusFincApp);
			// 添加贷后检查模型数据
			this.changeFormProperty(formexamhis001, "templateId", "optionArray", examOptionsList);
			returnStr = "ExamineApply4Risk";
		} else if ("apply".equals(getFlag)) {
			MfExamineHis mfExamineHis = (MfExamineHis) JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class);
//			MfExamineHis mfExamineHis = (MfExamineHis) initDataMap.get("mfExamineHis");
			templateId = mfExamineHis.getTemplateId();
			examHisId = mfExamineHis.getExamHisId();
			getObjValue(formexamhis001, mfExamineHis);
			getObjValue(formexamhis001, mfBusPact);
			mfBusFincApp.setEndDate(mfBusPact.getEndDate());
			getObjValue(formexamhis001, mfBusFincApp);
			// 添加贷后检查模型数据
			examOptionsList = new ArrayList<OptionsList>();
			OptionsList optionsList = new OptionsList();
			optionsList.setOptionLabel(mfExamineHis.getTemplateName());
			optionsList.setOptionValue(mfExamineHis.getTemplateId());
			examOptionsList.add(optionsList);
			this.changeFormProperty(formexamhis001, "templateId", "optionArray", examOptionsList);
			model.addAttribute("templateId", templateId);
			model.addAttribute("resultFormId", String.valueOf(initDataMap.get("resultFormId")));
			returnStr = "ExamineApply4Risk";
		} else if ("detail".equals(getFlag)) {
			MfExamineHis mfExamineHis =JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class) ;
			templateId = mfExamineHis.getTemplateId();
			examHisId = mfExamineHis.getExamHisId();
			getObjValue(formexamhis001, mfExamineHis);
			getObjValue(formexamhis001, mfBusPact);
			mfBusFincApp.setEndDate(mfBusPact.getEndDate());
			getObjValue(formexamhis001, mfBusFincApp);
			// 添加贷后检查模型数据
			this.changeFormProperty(formexamhis001, "templateId", "optionArray", examOptionsList);
			mfExamineHis = new MfExamineHis();
			mfExamineHis.setPactId(mfBusPact.getPactId());
			List<MfExamineHis> mfExamineHisList = mfExamineHisFeign.getMfExamineHisList(mfExamineHis);
			model.addAttribute("mfExamineHisList", mfExamineHisList);
			model.addAttribute("templateId", templateId);
			model.addAttribute("resultFormId", String.valueOf(initDataMap.get("resultFormId")));
			String entDetailflag = "detail";
			model.addAttribute("entDetailflag", entDetailflag);
			returnStr = "ExamineDetailResult";
		}else {
		}
		model.addAttribute("formexamhis001", formexamhis001);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		model.addAttribute("getFlag", getFlag);
		model.addAttribute("examHisId", examHisId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("appId", appId);
		model.addAttribute("pactId", pactId);
		model.addAttribute("entrance", entrance);
		
		return "component/examine/"+returnStr;
	}
	
	/**
	 * 
	 * 方法描述： 打开发起贷后检查页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-26 下午10:18:55
	 */
	@RequestMapping(value = "/ExamineApply4RiskAjax")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public Map<String,Object> ExamineApply4RiskAjax(String docuTemplate, String examOpNo, String beginDate, String endDate,
			String templateId, String examHisId, String fincId, String appId, String pactId,String entrance) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
		FormData formexamhis001 = formService.getFormData("examhis001");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (docuTemplate != null) {
			
		} else {
			MfExamineHis mfExamineHis = new MfExamineHis();
			examHisId = WaterIdUtil.getWaterId("exam");
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis.setFiveclass(BizPubParm.FIN_TYPE_1);
			getObjValue(formexamhis001, mfExamineHis);
		}
		Map<String, Object> initDataMap = new HashMap<String, Object>();
		initDataMap = mfExamineHisFeign.initInputData4Risk(null, fincId);
		String getFlag = String.valueOf(initDataMap.get("getFlag"));
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		// 通过查询功能进入贷后，无法取得fincId
		mfBusFincApp.setAppId(appId);
		mfBusFincApp.setPactId(pactId);
		mfBusFincApp = pactInterfaceFeign.getByFincIdOrwkfId(mfBusFincApp);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);

		request.setAttribute("cusNo", mfBusFincApp.getCusNo());
		request.setAttribute("fincId", mfBusFincApp.getFincId());
		request.setAttribute("getFlag", getFlag);
		List<MfExamineCard> examCardListDataList = (List<MfExamineCard>) initDataMap.get("examCardListData");
		request.setAttribute("initDataMap", initDataMap);
		String cusNo = mfBusFincApp.getCusNo();
		appId = mfBusFincApp.getAppId();
		dataMap.put("getFlag", "add");
		List<OptionsList> examOptionsList = mfExamineHisFeign.getConfigMatchedByBussList(pactId, "finc");
		if ("add".equals(getFlag)) {
			getObjValue(formexamhis001, mfBusPact);
			// 解决合同到期日被借据中上次检查日期覆盖掉问题
			mfBusFincApp.setEndDate(mfBusPact.getEndDate());
			getObjValue(formexamhis001, mfBusFincApp);
			// 添加贷后检查模型数据
			this.changeFormProperty(formexamhis001, "templateId", "optionArray", examOptionsList);
			dataMap.put("getFlag", "add");
		} else if ("apply".equals(getFlag)) {
			MfExamineHis mfExamineHis = (MfExamineHis) JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class);
//			MfExamineHis mfExamineHis = (MfExamineHis) initDataMap.get("mfExamineHis");
			templateId = mfExamineHis.getTemplateId();
			examHisId = mfExamineHis.getExamHisId();
			getObjValue(formexamhis001, mfExamineHis);
			getObjValue(formexamhis001, mfBusPact);
			mfBusFincApp.setEndDate(mfBusPact.getEndDate());
			getObjValue(formexamhis001, mfBusFincApp);
			// 添加贷后检查模型数据
			examOptionsList = new ArrayList<OptionsList>();
			OptionsList optionsList = new OptionsList();
			optionsList.setOptionLabel(mfExamineHis.getTemplateName());
			optionsList.setOptionValue(mfExamineHis.getTemplateId());
			examOptionsList.add(optionsList);
			this.changeFormProperty(formexamhis001, "templateId", "optionArray", examOptionsList);
			dataMap.put("getFlag", "apply");
		} else if ("detail".equals(getFlag)) {
			MfExamineHis mfExamineHis =JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class) ;
			templateId = mfExamineHis.getTemplateId();
			examHisId = mfExamineHis.getExamHisId();
			getObjValue(formexamhis001, mfExamineHis);
			getObjValue(formexamhis001, mfBusPact);
			mfBusFincApp.setEndDate(mfBusPact.getEndDate());
			getObjValue(formexamhis001, mfBusFincApp);
			// 添加贷后检查模型数据
			this.changeFormProperty(formexamhis001, "templateId", "optionArray", examOptionsList);
			mfExamineHis = new MfExamineHis();
			mfExamineHis.setPactId(mfBusPact.getPactId());
			dataMap.put("getFlag", "detail");
		}else {
		}

		dataMap.put("flag", "success");
		return dataMap;
	}
	
	@RequestMapping(value = "/examineApplyForCus")
	@SuppressWarnings("unchecked")
	public String examineApplyForCus(Model model,String taskId, String docuTemplate, String examOpNo, String beginDate, String endDate,
			String templateId, String examHisId,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
		model.addAttribute("scNo", scNo);
		if(StringUtil.isNotEmpty(taskId)){
			model.addAttribute("taskId", taskId);
		}
		FormData formexamhisForCus = formService.getFormData("examhisForCus");
		//获取客户信息
		MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
		//获取该客户贷款余额
		String amtRest = pactInterfaceFeign.getAllLoanbalByCusNo(cusNo);
		//获取该客户贷款总额
		MfBusFincApp param = new MfBusFincApp();
		param.setCusNo(cusNo);
		String amtSum = pactInterfaceFeign.getPutoutAmtByCusNo(param);
		//把贷客户号,款余额和贷款总额放入form中
		Map<String,String >paramMap = new HashMap<>();
		paramMap.put("amtRest", MathExtend.moneyStr(amtRest));
		paramMap.put("amtSum", MathExtend.moneyStr(amtSum));
		paramMap.put("cusNo", cusNo);
//		paramMap.put("", arg1)
		getObjValue(formexamhisForCus, paramMap);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusName",mfCusCustomer.getCusName());
		if (docuTemplate != null) {
			dataMap.put("examOpNo", examOpNo);
			dataMap.put("beginDate", beginDate);
			dataMap.put("endDate", endDate);
			dataMap.put("templateId", templateId);
			dataMap.put("docuTemplate", docuTemplate);
			dataMap.put("examHisId", examHisId);
			getObjValue(formexamhisForCus, dataMap);
		} else {
			MfExamineHis mfExamineHis = new MfExamineHis();
			examHisId = WaterIdUtil.getWaterId("exam");
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis.setFiveclass(BizPubParm.FIN_TYPE_1);
			mfExamineHis.setCusNoSupplier(cusNo);
			getObjValue(formexamhisForCus, mfExamineHis);
		}
		Map<String, Object> initDataMap = new HashMap<String, Object>();
		initDataMap = mfExamineHisFeign.initInputData(cusNo, "",null);
		String getFlag = String.valueOf(initDataMap.get("getFlag"));
		dataMap = JSONObject.fromObject(initDataMap);

		request.setAttribute("cusNo", cusNo);
		request.setAttribute("getFlag", getFlag);
		List<MfExamineCard> examCardListDataList = (List<MfExamineCard>) initDataMap.get("examCardListData");
		model.addAttribute("examCardListDataList", examCardListDataList);
		dataMap.put("examCardListDataList", examCardListDataList);
		request.setAttribute("initDataMap", initDataMap);
		String returnStr = "ExamineApplyForCus";
		List<OptionsList> examOptionsList = mfExamineHisFeign.getConfigMatchedByCusList(cusNo);
		if ("add".equals(getFlag)) {
//			getObjValue(formexamhisForCus, mfBusPact);
//			getObjValue(formexamhisForCus, mfBusFincApp);
			// 添加贷后检查模型数据
			getObjValue(formexamhisForCus, mfCusCustomer);
			this.changeFormProperty(formexamhisForCus, "templateId", "optionArray", examOptionsList);
		} else if ("apply".equals(getFlag)) {
			MfExamineHis mfExamineHis = (MfExamineHis) JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class);
//			MfExamineHis mfExamineHis = (MfExamineHis) initDataMap.get("mfExamineHis");
			templateId = mfExamineHis.getTemplateId();
			examHisId = mfExamineHis.getExamHisId();
			getObjValue(formexamhisForCus, mfExamineHis);
			getObjValue(formexamhisForCus, mfCusCustomer);
			
//			getObjValue(formexamhisForCus, mfBusPact);
//			getObjValue(formexamhisForCus, mfBusFincApp);
			// 添加贷后检查模型数据
			examOptionsList = new ArrayList<OptionsList>();
			OptionsList optionsList = new OptionsList();
			optionsList.setOptionLabel(mfExamineHis.getTemplateName());
			optionsList.setOptionValue(mfExamineHis.getTemplateId());
			examOptionsList.add(optionsList);
			this.changeFormProperty(formexamhisForCus, "templateId", "optionArray", examOptionsList);
			model.addAttribute("templateId", templateId);
			model.addAttribute("resultFormId", String.valueOf(initDataMap.get("resultFormId")));
		} else if ("detail".equals(getFlag)) {
			MfExamineHis mfExamineHis =JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class) ;
			templateId = mfExamineHis.getTemplateId();
			examHisId = mfExamineHis.getExamHisId();
			getObjValue(formexamhisForCus, mfExamineHis);
			getObjValue(formexamhisForCus, mfCusCustomer);
			// 添加贷后检查模型数据
			this.changeFormProperty(formexamhisForCus, "templateId", "optionArray", examOptionsList);
			mfExamineHis = new MfExamineHis();
			mfExamineHis.setCusNoSupplier(cusNo);
			List<MfExamineHis> mfExamineHisList = mfExamineHisFeign.getMfExamineHisList(mfExamineHis);
			model.addAttribute("mfExamineHisList", mfExamineHisList);
			model.addAttribute("templateId", templateId);
			model.addAttribute("resultFormId", String.valueOf(initDataMap.get("resultFormId")));
			String entDetailflag = "detail";
			model.addAttribute("entDetailflag", entDetailflag);
			returnStr = "ExamineDetailResult";
		}else {
		}
		model.addAttribute("formexamhisForCus", formexamhisForCus);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		model.addAttribute("getFlag", getFlag);
		model.addAttribute("examHisId", examHisId);
		model.addAttribute("cusName",mfCusCustomer.getCusName());
		model.addAttribute("cusNo", cusNo);
		return "component/examine/"+returnStr;
	}

	/**
	 * 
	 * 方法描述： 检查详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param fincId
	 * @param pactId
	 * @date 2017-7-27 下午9:27:07
	 */
	@RequestMapping(value = "/ExamineDetailResult")
	public String ExamineDetailResult(Model model, String fincId, String pactId,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
		model.addAttribute("scNo", scNo);
		FormData formexamhis001 = formService.getFormData("examhis001");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> initDataMap = new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(fincId)){
			initDataMap = mfExamineHisFeign.initInputData("","", fincId);
		}else if(StringUtil.isNotEmpty(pactId)){
			initDataMap = mfExamineHisFeign.initInputData("",pactId, "");
		}else if(StringUtil.isNotEmpty(cusNo)){
			initDataMap = mfExamineHisFeign.initInputData(cusNo, "","");
		}else{
			throw new Exception("查看贷后检查结果：客户号、合同号和借据号都为空");
		}
		
		String getFlag = String.valueOf(initDataMap.get("getFlag"));
		// dataMap.put("listData", initDataMap.get("listData"));
		dataMap = JSONObject.fromObject(initDataMap);
		
		request.setAttribute("getFlag", getFlag);
		List<MfExamineCard> examCardListDataList = (List<MfExamineCard>) initDataMap.get("examCardListData");
		request.setAttribute("initDataMap", initDataMap);
		MfExamineHis mfExamineHis = JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class) ;
		if (mfExamineHis != null) {
			String examHisId = mfExamineHis.getExamHisId();
			model.addAttribute("examHisId", examHisId);
			String templateId = mfExamineHis.getTemplateId();
			model.addAttribute("templateId", templateId);
		}
		mfExamineHis = new MfExamineHis();
		if(StringUtil.isNotEmpty(fincId)){
			mfExamineHis.setFincId(fincId);
		}else if(StringUtil.isNotEmpty(pactId)){
			mfExamineHis.setPactId(pactId);
		}else if(StringUtil.isNotEmpty(cusNo)){
			mfExamineHis.setCusNoSupplier(cusNo);
		}else {
		}
		
		List<MfExamineHis> mfExamineHisList = mfExamineHisFeign.getMfExamineHisList(mfExamineHis);
		request.setAttribute("mfExamineHisList", mfExamineHisList);
		request.setAttribute("resultFormId", String.valueOf(initDataMap.get("resultFormId")));
		String entDetailflag = "detail";
		model.addAttribute("entDetailflag", entDetailflag);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("cusNo", cusNo);
		return "component/examine/ExamineDetailResult";
	}

	/**
	 * 
	 * 方法描述： 检查历史超链获得检查详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-29 下午7:45:48
	 */
	@RequestMapping(value = "/ExamineDetailByList")
	public String ExamineDetailByList(Model model, String fincId, String examHisId) throws Exception {
		ActionContext.initialize(request, response);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
		model.addAttribute("scNo", scNo);
		model.addAttribute("examHisId", examHisId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> initDataMap = new HashMap<String, Object>();
		initDataMap = mfExamineHisFeign.getExamineDetailByList(fincId, examHisId);
		dataMap = JSONObject.fromObject(initDataMap);
		model.addAttribute("dataMap", dataMap);
		List<MfExamineCard> examCardListDataList = (List<MfExamineCard>) initDataMap.get("examCardListData");
		model.addAttribute("examCardListDataList", examCardListDataList);
		request.setAttribute("initDataMap", initDataMap);
		MfExamineHis mfExamineHis =JsonStrHandling.handlingStrToBean(initDataMap.get("mfExamineHis"), MfExamineHis.class) ;
		List<MfExamineHis> mfExamineHisList = (List<MfExamineHis>) initDataMap.get("mfExamineHisList");
		request.setAttribute("mfExamineHisList", mfExamineHisList);
		request.setAttribute("templateId", String.valueOf(mfExamineHis.getTemplateId()));
		request.setAttribute("resultFormId", String.valueOf(initDataMap.get("resultFormId")));
		String entDetailflag = "hisList";
		model.addAttribute("entDetailflag", entDetailflag);
		return "component/examine/ExamineDetailResult";
	}

	/**
	 * 
	 * 方法描述：获得当前是否存在正在进行的贷后检查
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-27 下午9:43:09
	 */
	@ResponseBody
	@RequestMapping(value = "/getExamineResultAjax")
	public Map<String, Object> getExamineResultAjax(String fincId,String cusNo,String pactId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfExamineHisFeign.getExamineResultFlag(fincId,cusNo,pactId);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 跳转到登记检查情况表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-14 下午2:15:16
	 */
	@RequestMapping(value = "/formTemplateInput")
	public String formTemplateInput(Model model, String examHisId, String templateType, String formTemplate)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		mfExamineHis.setExamHisId(examHisId);
		mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
		if (mfExamineHis != null) {
			String pactId = mfExamineHis.getPactId();
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactId(pactId);
			mfBusPact = pactInterfaceFeign.getById(mfBusPact);
			model.addAttribute("mfBusPact", mfBusPact);
		}
		if ("1".equals(templateType)) {
			FormData formexamFormTemp = formService.getFormData("examDocuTemp");
			String docuTemplate = mfExamineHis.getDocuTemplate();
			String examDetailFlag = null;
			if (docuTemplate.startsWith(examHisId)) {
				examDetailFlag = "1";
			} else {
				examDetailFlag = "0";
			}
			model.addAttribute("examDetailFlag", examDetailFlag);
		}
		FormData formexamFormTemp = null;
		if ("2".equals(templateType)) {
			formexamFormTemp = formService.getFormData(formTemplate);
			if (formexamFormTemp.getFormId() == null) {
				formexamFormTemp = formService.getFormData(BizPubParm.FORMTEMPLATE);
			}
			MfExamineDetail mfExamineDetail = new MfExamineDetail();
			mfExamineDetail.setExamHisId(examHisId);
			List<MfExamineDetail> list = mfExamineDetailFeign.getExamDetailList(mfExamineDetail);
			String examDetailFlag = null;
			if (list != null && list.size() > 0) {
				examDetailFlag = "1";
				dataMap = mfExamineDetailFeign.getExamDetailMap(mfExamineDetail);
			} else {
				examDetailFlag = "0";
			}
			model.addAttribute("examDetailFlag", examDetailFlag);
		}
		getObjValue(formexamFormTemp, dataMap);
		getObjValue(formexamFormTemp, mfExamineHis);
		model.addAttribute("formexamFormTemp", formexamFormTemp);
		model.addAttribute("dataMap", dataMap);
		return "component/examine/MfExamineHis_update";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formexamhis0002 = formService.getFormData("examhis0002");
		getFormValue(formexamhis0002);
		MfExamineHis mfExamineHis = new MfExamineHis();
		setObjValue(formexamhis0002, mfExamineHis);
		mfExamineHisFeign.insert(mfExamineHis);
		getObjValue(formexamhis0002, mfExamineHis);
		this.addActionMessage(model, "保存成功");
		List<MfExamineHis> mfExamineHisList = (List<MfExamineHis>) mfExamineHisFeign
				.findByPage(this.getIpage(), mfExamineHis).getResult();
		model.addAttribute("mfExamineHisList", mfExamineHisList);
		return "component/examine/MfExamineHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String examHisId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formexamhis0001 = formService.getFormData("examhis0001");
		getFormValue(formexamhis0001);
		MfExamineHis mfExamineHis = new MfExamineHis();
		mfExamineHis.setExamHisId(examHisId);
		mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
		getObjValue(formexamhis0001, mfExamineHis);
		model.addAttribute("formexamhis0001", formexamhis0001);
		return "component/examine/MfExamineHis_Detail";
	}

	/**
	 * 
	 * 方法描述：获得检查详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-17 下午4:17:29
	 */
	@RequestMapping(value = "/getExamDetail")
	public String getExamDetail(Model model, String examHisId, String formTemplate) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> map = new HashMap<String, Object>();
		FormData formexamFormTemp = formService.getFormData(formTemplate);
		getFormValue(formexamFormTemp);
		MfExamineDetail mfExamineDetail = new MfExamineDetail();
		mfExamineDetail.setExamHisId(examHisId);
		map = mfExamineDetailFeign.getExamDetailMap(mfExamineDetail);
		if (formexamFormTemp.getFormId() == null) {
			formexamFormTemp = formService.getFormData(BizPubParm.FORMTEMPLATE);
		}
		// 设置表单元素不可编辑
		FormActive[] list = formexamFormTemp.getFormActives();
		for (int i = 0; i < list.length; i++) {
			FormActive formActive = list[i];
			formActive.setReadonly("1");
		}
		if (map != null && !map.isEmpty()) {
			getObjValue(formexamFormTemp, map);
		} else {
			getObjValue(formexamFormTemp, mfExamineDetail);
		}
		model.addAttribute("formexamFormTemp", formexamFormTemp);
		return "component/examine/MfExamineHis_ExamDetail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String examHisId) throws Exception {
		ActionContext.initialize(request, response);
		MfExamineHis mfExamineHis = new MfExamineHis();
		mfExamineHis.setExamHisId(examHisId);
		mfExamineHisFeign.delete(mfExamineHis);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formexamhis0002 = formService.getFormData("examhis0002");
		getFormValue(formexamhis0002);
		boolean validateFlag = this.validateFormData(formexamhis0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formexamhis0002 = formService.getFormData("examhis0002");
		getFormValue(formexamhis0002);
		boolean validateFlag = this.validateFormData(formexamhis0002);
	}

	/**
	 * 
	 * 方法描述： 打开检查审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-15 下午2:51:41
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String examHisId, String activityType,String hideOpinionType ) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
		model.addAttribute("scNo", scNo);
		model.addAttribute("examHisId", examHisId);
		FormData formexamapprov001 = formService.getFormData("examapprov001");
		MfExamineHis mfExamineHis = new MfExamineHis();
		mfExamineHis.setExamHisId(examHisId);
		mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		mfExamineTemplateConfig.setTemplateId(mfExamineHis.getTemplateId());
		mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
		String templateType = mfExamineTemplateConfig.getTemplateType();
		model.addAttribute("templateType", templateType);
		String templateId = mfExamineHis.getTemplateId();
		model.addAttribute("templateId", templateId);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(mfExamineHis.getPactId());
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		if (BizPubParm.BUS_MODEL_1.equals(mfBusPact.getBusModel())
				|| BizPubParm.BUS_MODEL_2.equals(mfBusPact.getBusModel())) {
			dataMap.put("cusNoWarehouse", mfBusPact.getCusNoWarehouse());// 仓储机构
		} else if (BizPubParm.BUS_MODEL_4.equals(mfBusPact.getBusModel())
				|| BizPubParm.BUS_MODEL_5.equals(mfBusPact.getBusModel())
				|| BizPubParm.BUS_MODEL_6.equals(mfBusPact.getBusModel())) {
			dataMap.put("cusNoCore", mfBusPact.getCusNoCore());// 核心企业
		}else {
		}
		String cusNo = mfBusPact.getCusNo();
		String pactId = mfBusPact.getPactId();
		String appId = mfBusPact.getAppId();
		String fincId = mfExamineHis.getFincId();
		dataMap.put("cusNo", mfBusPact.getCusNo());
		dataMap.put("busModel", mfBusPact.getBusModel());
		dataMap.put("pactId", mfBusPact.getPactId());
		dataMap.put("appId", mfBusPact.getAppId());
		// 检查审批的场景号
		dataMap.put("scNo", scNo);
		ViewUtil.setViewPointParm(request, dataMap);
		MfExamineApprove mfExamineApprove = new MfExamineApprove();
		mfExamineApprove.setExamHisId(examHisId);
		mfExamineApprove = mfExamineApproveFeign.getById(mfExamineApprove);
		mfExamineApprove.setFiveClass(BizPubParm.FIVE_STS_1TH);
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFincId(mfExamineHis.getFincId());
		mfPactFiveclass = mfPactFiveclassFeign.getByFincId(mfPactFiveclass);
		if (mfPactFiveclass != null) {
			mfExamineApprove.setFiveClass(mfPactFiveclass.getFiveclass());
		}
		// mfExamineApprove.setAdjRiskLevel(mfExamineApprove.getRiskLevel());
		getObjValue(formexamapprov001, mfBusPact);
		getObjValue(formexamapprov001, mfExamineApprove);

		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(examHisId, null);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskAppro.getId(), User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		this.changeFormProperty(formexamapprov001, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formexamapprov001", formexamapprov001);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "component/examine/WkfExamViewPoint";
	}
	
	
	/**
	 * 
	 * 方法描述： 打开检查审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-15 下午2:51:41
	 */
	@RequestMapping(value = "/getViewPointForCus")
	public String getViewPointForCus(Model model, String examHisId, String taskId,String activityType,String hideOpinionType) throws Exception {
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(examHisId, null);
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
		model.addAttribute("scNo", scNo);
		model.addAttribute("examHisId", examHisId);
		String formId ="examapproveForCus1000";
		if(taskAppro!=null&&"node4596463489".equals(taskAppro.getName())){
			formId ="examapproveForCus1000_fk";
		}
		FormData formexamapproveForCus = formService.getFormData(formId);
		/**
		 * 获取贷后检查信息
		 */
		MfExamineHis mfExamineHis = new MfExamineHis();
		mfExamineHis.setExamHisId(examHisId);
		mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
		getObjValue(formexamapproveForCus, mfExamineHis);
		/**
		 * 获取贷后检查配置信息
		 */
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		mfExamineTemplateConfig.setTemplateId(mfExamineHis.getTemplateId());
		mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
		String templateType = mfExamineTemplateConfig.getTemplateType();
		model.addAttribute("templateType", templateType);
		String templateId = mfExamineHis.getTemplateId();
		model.addAttribute("templateId", templateId);
		MfCusCustomer mfCusCustomer = null;
		/**
		 * 获取客户相关信息
		 */
		if(StringUtil.isNotEmpty(mfExamineHis.getCusNo())){
			String cusNo = mfExamineHis.getCusNo();
			//获取客户信息
			mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
			//获取该客户贷款余额
			String amtRest = pactInterfaceFeign.getAllLoanbalByCusNo(cusNo);
			//获取该客户贷款总额
			MfBusFincApp param = new MfBusFincApp();
			param.setCusNo(cusNo);
			String amtSum = pactInterfaceFeign.getPutoutAmtByCusNo(param);
			//把贷客户号,款余额和贷款总额放入form中
			Map<String,String >paramMap = new HashMap<>();
			paramMap.put("amtRest", MathExtend.moneyStr(amtRest));
			paramMap.put("amtSum", MathExtend.moneyStr(amtSum));
			getObjValue(formexamapproveForCus, paramMap);
		}
		
		dataMap.put("cusNo", mfExamineHis.getCusNoSupplier());

		// 检查审批的场景号
		dataMap.put("scNo", scNo);
		ViewUtil.setViewPointParm(request, dataMap);
		/**
		 * 获取贷后检查审批信息
		 */
		MfExamineApprove mfExamineApprove = new MfExamineApprove();
		mfExamineApprove.setExamHisId(examHisId);
		mfExamineApprove = mfExamineApproveFeign.getById(mfExamineApprove);

		// mfExamineApprove.setAdjRiskLevel(mfExamineApprove.getRiskLevel());
		
		getObjValue(formexamapproveForCus, mfExamineApprove);
		//将客户信息传入表单
		if(mfCusCustomer!=null){
			mfCusCustomer.setEndDate(mfExamineApprove.getEndDate());//解决跟踪时间被客户中的营业到期时间覆盖的问题
			getObjValue(formexamapproveForCus, mfCusCustomer);
		}
		MfExaminePact mfExaminePact = new MfExaminePact();
		mfExaminePact.setExamId(examHisId);
		List<MfExaminePact> mfExaminePactList = mfExamineHisFeign.getExaminePactList(mfExaminePact);

		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		if(StringUtil.isNotEmpty(hideOpinionType)){
			
			opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
			opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		}
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formexamapproveForCus, "opinionType", "optionArray", opinionTypeList);
		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		// 获取财务报表信息 LJW
		//获取连续的财务报表
		List<MfCusReportAcount> cusFinMainList=mfCusReportAcountFeign.getContinuityReport(mfExamineHis.getCusNo(),mfExamineHis.getRptDate(),mfExamineHis.getRptDate().substring(4,6),mfExamineHis.getAccountId());
		cusFinMainList=setReportIsUsed(cusFinMainList);
		dataMap.put("cusFinMainList", cusFinMainList);
		model.addAttribute("cusFinMainList", cusFinMainList);
		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("cusBaseType", mfCusCustomer.getCusBaseType());
		model.addAttribute("cusNo", mfExamineHis.getCusNo());
		model.addAttribute("nodeNo", taskAppro.getName());
		model.addAttribute("formexamapproveForCus", formexamapproveForCus);
		model.addAttribute("mfBusPactExamineList", mfExaminePactList);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "component/examine/WkfExamViewPointForCus";
	}

	/**
	 * 方法描述： 获取检查历史列表信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-28 上午10:55:29
	 */
	@ResponseBody
	@RequestMapping(value = "/getMfExamineHisListAjax")
	public Map<String, Object> getMfExamineHisListAjax(Model model,@RequestParam(required = false) String fincId, String tableId,
													   @RequestParam(required = false) String cusNo, @RequestParam(required = false) String pactId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 检查历史信息
		MfExamineHis mfExamineHis = new MfExamineHis();
		if(StringUtil.isNotEmpty(fincId)){
			mfExamineHis.setFincId(fincId);
		}
		if(StringUtil.isNotEmpty(pactId)){
			mfExamineHis.setPactId(pactId);
		}
		if(StringUtil.isNotEmpty(cusNo)){
			mfExamineHis.setCusNoSupplier(cusNo);
		}
		List<MfExamineHis> mfExamineHisList = mfExamineHisFeign.getMfExamineHisSubmitList(mfExamineHis);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfExamineHisList, null, true);
		dataMap.put("size", mfExamineHisList.size());
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/getByIdAjaxForForm")
	public Map<String, Object> getByIdAjaxForForm(String examHisId, String query, String formId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formexamFormTemp = formService.getFormData(formId);
		/**
		 * 获取跟踪信息
		 */
		MfExamineHis mfExamineHis = new MfExamineHis();
		mfExamineHis.setExamHisId(examHisId);
		mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);
		
		/**
		 * 获取子表单信息
		 */
		if (formexamFormTemp.getFormId() == null) {
			MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
			mfExamineTemplateConfig.setTemplateId(mfExamineHis.getTemplateId());
			mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
			formexamFormTemp = formService.getFormData(mfExamineTemplateConfig.getBaseFormTemplate());
		}
		/**
		 * 获取客户相关信息
		 */
		if(StringUtil.isNotEmpty(mfExamineHis.getCusNoSupplier())){
			String cusNo = mfExamineHis.getCusNoSupplier();
			//获取客户信息
			MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
			//获取该客户贷款余额
			String amtRest = pactInterfaceFeign.getAllLoanbalByCusNo(cusNo);
			//获取该客户贷款总额
			MfBusFincApp param = new MfBusFincApp();
			param.setCusNo(cusNo);
			String amtSum = pactInterfaceFeign.getPutoutAmtByCusNo(param);
			//把贷客户号,款余额和贷款总额放入form中
			Map<String,String >paramMap = new HashMap<>();
			paramMap.put("amtRest", MathExtend.moneyStr(amtRest));
			paramMap.put("amtSum", MathExtend.moneyStr(amtSum));
			getObjValue(formexamFormTemp, paramMap);
			getObjValue(formexamFormTemp, mfCusCustomer);
		}
		/**
		 * 获取合同相关信息
		 */
		if(StringUtil.isNotEmpty(mfExamineHis.getPactId())){
			
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactId(mfExamineHis.getPactId());
			mfBusPact = pactInterfaceFeign.getById(mfBusPact);
			getObjValue(formexamFormTemp, mfBusPact);
		}
		/**
		 * 获取五级分类数据
		 */
		mfExamineHis.setEndDate(DateUtil.getDate());
		if(StringUtil.isNotEmpty(mfExamineHis.getFincId())){
			MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
			mfPactFiveclass.setFincId(mfExamineHis.getFincId());
			mfPactFiveclass = mfPactFiveclassFeign.getByFincId(mfPactFiveclass);
			if (mfPactFiveclass != null) {
				mfExamineHis.setAdjFiveClass(mfPactFiveclass.getFiveclass());
				mfExamineHis.setFiveclass(mfPactFiveclass.getFiveclass());
			} else {
				mfExamineHis.setAdjFiveClass("1");
				mfExamineHis.setFiveclass("1");
			}
			
		}
		getObjValue(formexamFormTemp, mfExamineHis);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formexamFormTemp, "bootstarpTag", "");
		dataMap.put("formHtml", formHtml);
		dataMap.putAll(mfExamineHisFeign.getListData(examHisId, mfExamineHis.getTemplateId()));
		if (mfExamineHis != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	

	/**
	 * 
	 * 方法描述： 手动指派贷后检查任务
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-8-2 下午3:23:01
	 */
	@ResponseBody
	@RequestMapping(value = "/assignExamineTaskAjax")
	public Map<String, Object> assignExamineTaskAjax(String assignType, String fincId, String executorsStr, String cusNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String orgNo = User.getOrgNo(request);
		String regName = User.getRegName(request);
		if(StringUtil.isEmpty(fincId)){
			fincId = null;
		}
		if(StringUtil.isEmpty(cusNo)){
			cusNo = null;
		}
		dataMap = mfExamineHisFeign.submitAssignExamineTask(assignType, fincId, executorsStr,cusNo, orgNo, regName);
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获得检查模板保存情况
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-8-28 上午11:56:26
	 */
	@ResponseBody
	@RequestMapping(value = "/getSaveTemplateInfoAjax")
	public Map<String, Object> getSaveTemplateInfoAjax(String examHisId, String templateId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = mfExamineHisFeign.getSaveTemplateInfo(examHisId, templateId);
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述： 获取风险登记页面（贷后列表）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-8-28 上午11:56:26
	 */
	@RequestMapping(value = "/getExamList4RiskPage")
	public String getExamList4RiskPage() throws Exception {
		ActionContext.initialize(request, response);
		return "component/examine/MfExamineHis_List4risk";
	}
	
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getExamList4RiskPageAjax")
	public Map<String, Object> getExamList4RiskPageAjax(String ajaxData, int pageNo,int pageSize, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			ActionContext.initialize(request, response);
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
				mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
				mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
				mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
				// mfBusFincApp.setPactSts(pactSts);
				// this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
				Ipage ipage = this.getIpage();
				ipage.setPageSize(pageSize);
				ipage.setPageNo(pageNo);// 异步传页面翻页参数
				// 自定义查询Bo方法
				ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
				ipage = mfBusFincAppFeign.findLoanAfterByPage(ipage);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
				ipage.setResult(tableHtml);
				dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllMfExamineHisAjax") 
	public Map<String, Object> getAllMfExamineHisAjax(String cusNo)  throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			ActionContext.initialize(request, response);
			mfExamineHis.setCusNoSupplier(cusNo);
			List<MfExamineHis> list = mfExamineHisFeign.getMfExamineHisList(mfExamineHis);
			dataMap.put("flag", "success");
			dataMap.put("size", list.size());
			dataMap.put("examList", list);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * @Description 跳转到贷后检查忽略原因页面
	 * @Author zhaomingguang
	 * @DateTime 2019/9/21 17:06
	 * @Param [model, pasNos]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/ignoreExamineTask")
	public String ignoreExamineTask (Model model,String pasNo ){
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formignoreExamineTask = formService.getFormData("ignoreExamineTask");
		changeFormProperty(formignoreExamineTask,"pasNos","initValue",pasNo);
		model.addAttribute("query", "");
		model.addAttribute("formignoreExamineTask",formignoreExamineTask);
		return "component/examine/MfExamineHis_ignoreExamineTask";
	}
    /**
     * @Description 单笔和批量忽略贷后检查任务
     * @Author zhaomingguang
     * @DateTime 2019/9/21 17:31
     * @Param [ajaxData]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
	@RequestMapping(value = "/insertIgnoreExamineTaskAjax")
    @ResponseBody
	public Map<String,Object> insertIgnoreExamineTaskAjax(String ajaxData){
        Map<String,Object> dataMap = new HashMap<>();
	    try {
	        Map<String,Object> paraMap = getMapByJson(ajaxData);
	        paraMap.put("opNo",User.getRegNo(request));
	        paraMap.put("opName",User.getRegName(request));
	        paraMap.put("orgNo",User.getOrgNo(request));
	        paraMap.put("orgName",User.getOrgName(request));
            Map<String,Object> resMap = sysTaskInfoFeign.insertIgnoreExamineTaskAjax(paraMap);
            dataMap.putAll(resMap);
        }catch (Exception e){
	    	logger.error("单笔和批量忽略贷后检查任务"+e.getMessage(),e);
            dataMap.put("flag","error");
            dataMap.put("msg",e.getMessage());
        }
	    return dataMap;
    }
	/**
	 * @Description 新贷后检查任务指派（包含单笔和批量）
	 * @Author zhaomingguang
	 * @DateTime 2019/9/23 9:13
	 * @Param [ executorsStr, pasNo]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/assignExamineTaskAjaxNew")
	@ResponseBody
	public Map<String, Object> assignExamineTaskAjaxNew( String executorsStr, String pasNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String orgNo = User.getOrgNo(request);
			String regName = User.getRegName(request);

			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("executorsStr",executorsStr);
			paraMap.put("pasNo",pasNo);
			paraMap.put("orgNo",orgNo);
			paraMap.put("regName",regName);

			dataMap = mfExamineHisFeign.submitAssignExamineTaskNew(paraMap);

		}catch (Exception e){
			logger.error("新贷后检查任务指派（包含单笔和批量）"+e.getMessage(),e);
		}
		return dataMap;
	}
	/**
	 * @Description 跳转到检查登记页面
	 * @Author zhaomingguang
	 * @DateTime 2019/9/23 15:46
	 * @Param [model, pasNo]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/examineApplyNew")
	public String examineApplyNew(Model model, @RequestParam(required = false) String pasNo, @RequestParam(required = false) String cusNo,
								  @RequestParam(required = false) String pactId, @RequestParam(required = false) String fincId,
								  @RequestParam(required = false) String examEntrance){
		try {
			ActionContext.initialize(request, response);
			FormService formService = new FormService();
			String formId = "examhisRegister";
			FormData formexamhisnew = formService.getFormData(formId);
			boolean isPasNo = StringUtils.isNotEmpty(pasNo)?true:false;
			Map<String,String> paraMap = new HashMap<>();
			if(isPasNo){//说明是从贷后入口进来的操作
				paraMap.putAll(getParaMap(pasNo));
			}else{//说明是从业务入口直接进来的操作（客户、合同、借据）
				paraMap.put("cusNo",cusNo);
				paraMap.put("pactId",pactId);
				paraMap.put("fincId",fincId);
				paraMap.put("examEntrance",examEntrance);
			}
			//获取页面表单需要的数据信息
			Map<String, Object> initDataMap = mfExamineHisFeign.getExamHisFormData(paraMap);
			initDataMap = JSONObject.fromObject(initDataMap);
			if(isPasNo){
				initDataMap.put("pasMinNo",paraMap.get("pasMinNo"));
				initDataMap.put("pasNo",pasNo);
			}
			//详情页面时使用详情表单解决模板类型没值
			if("detail".equals(MapUtils.getString(initDataMap,"getFlag"))){
				formexamhisnew = formService.getFormData("examhisRegisterDetail");
			}
			getObjValue(formexamhisnew, initDataMap);
			MfExaminePact mfExaminePact = new MfExaminePact();
			mfExaminePact.setExamId(String.valueOf(initDataMap.get("examHisId")));
			List<MfExaminePact> mfExaminePactList = mfExamineHisFeign.getExaminePactList(mfExaminePact);
			if(StringUtils.isNotEmpty(pasNo)){
				MfExamineHis mfExamineHisQuery = new MfExamineHis();
				mfExamineHisQuery.setExamHisId(initDataMap.get("examHisId").toString());
				mfExamineHisQuery = mfExamineHisFeign.getById(mfExamineHisQuery);
				getObjValue(formexamhisnew, mfExamineHisQuery);
			}

			//单独获取
			List<OptionsList> examOptionsList = null;
			if(StringUtils.isNotEmpty(paraMap.get("pactId"))){
				examOptionsList	= mfExamineHisFeign.getConfigMatchedByBussList(paraMap.get("pactId"),"finc");
			}else{
				examOptionsList	= mfExamineHisFeign.getConfigMatchedByCusList(paraMap.get("cusNo"));
			}
			changeFormProperty(formexamhisnew, "templateId", "optionArray", examOptionsList);
			List<MfExamineCard> examCardListDataList = (List<MfExamineCard>) initDataMap.get("examCardListData");
			model.addAttribute("examCardListDataList", examCardListDataList);
			model.addAttribute("dataMap", initDataMap);

			String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
			model.addAttribute("scNo", scNo);
			model.addAttribute("examHisId", MapUtils.getString(initDataMap,"examHisId"));
			model.addAttribute("mfBusPactExamineList", mfExaminePactList);
			model.addAttribute("templateId", MapUtils.getString(initDataMap,"templateId",""));
			model.addAttribute("resultFormId", MapUtils.getString(initDataMap,"resultFormId",""));
			model.addAttribute("formexamhis001", formexamhisnew);
			model.addAttribute("query", "detail".equals(MapUtils.getString(initDataMap,"getFlag"))?"query":"");
			model.addAttribute("examEntrance",examEntrance);//业务入口标识
			model.addAttribute("cusNo",MapUtils.getString(initDataMap,"cusNo",""));//客户号
			model.addAttribute("appId",MapUtils.getString(initDataMap,"appId",""));//申请编号
			model.addAttribute("pactId",MapUtils.getString(initDataMap,"pactId",""));//合同编号
			model.addAttribute("fincId",MapUtils.getString(initDataMap,"fincId",""));//借据编号
		}catch (Exception e){
			logger.error("跳转到检查登记页面"+e.getMessage(),e);
		}
		return "component/examine/ExamineApplyNew";
	}
	/**
	 * @Description 跳转到检查登记页面
	 * @Author zhaomingguang
	 * @DateTime 2019/9/23 15:46
	 * @Param [model, pasNo]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/examineApplyAfter")
	public String examineApplyAfter(Model model, @RequestParam(required = false) String pasNo, @RequestParam(required = false) String cusNo,
								  @RequestParam(required = false) String pactId, @RequestParam(required = false) String fincId,
								  @RequestParam(required = false) String examEntrance){
		try {
			ActionContext.initialize(request, response);
			FormService formService = new FormService();
			String formId = "examhisRegister";
			FormData formexamhisnew = formService.getFormData(formId);
			String formId2 = "examhis002";
			FormData formexamhis002 = formService.getFormData(formId2);
			String formId3 = "examhis003";
			FormData formexamhis003 = formService.getFormData(formId3);
			boolean isPasNo = StringUtils.isNotEmpty(pasNo)?true:false;
			Map<String,String> paraMap = new HashMap<>();
			if(isPasNo){//说明是从贷后入口进来的操作
				paraMap.putAll(getParaMap(pasNo));
			}else{//说明是从业务入口直接进来的操作（客户、合同、借据）
				paraMap.put("cusNo",cusNo);
				paraMap.put("pactId",pactId);
				paraMap.put("fincId",fincId);
				paraMap.put("examEntrance",examEntrance);
			}
			//获取页面表单需要的数据信息
			Map<String, Object> initDataMap = mfExamineHisFeign.getExamHisFormData(paraMap);
			initDataMap = JSONObject.fromObject(initDataMap);
			if(isPasNo){
				initDataMap.put("pasMinNo",paraMap.get("pasMinNo"));
				initDataMap.put("pasNo",pasNo);
			}
			//详情页面时使用详情表单解决模板类型没值
			if("detail".equals(MapUtils.getString(initDataMap,"getFlag"))){
				formexamhisnew = formService.getFormData("examhisRegisterDetail");
			}
			getObjValue(formexamhisnew, initDataMap);
			MfExaminePact mfExaminePact = new MfExaminePact();
			mfExaminePact.setExamId(String.valueOf(initDataMap.get("examHisId")));
			List<MfExaminePact> mfExaminePactList = mfExamineHisFeign.getExaminePactList(mfExaminePact);
			if(StringUtils.isNotEmpty(pasNo)){
				MfExamineHis mfExamineHisQuery = new MfExamineHis();
				mfExamineHisQuery.setExamHisId(initDataMap.get("examHisId").toString());
				mfExamineHisQuery = mfExamineHisFeign.getById(mfExamineHisQuery);
				model.addAttribute("examineStage", mfExamineHisQuery.getExamineStage());
				getObjValue(formexamhisnew, mfExamineHisQuery);
				getObjValue(formexamhis002, mfExamineHisQuery);
				getObjValue(formexamhis003, mfExamineHisQuery);
				if(StringUtil.isNotEmpty(mfExamineHisQuery.getCusNo())){
					//获取客户信息
					MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(mfExamineHisQuery.getCusNo());
					if(mfCusCustomer!=null){
						model.addAttribute("cusBaseType", mfCusCustomer.getCusBaseType());
					}
					// 获取财务报表信息 LJW
					//获取连续的财务报表
					List<MfCusReportAcount> cusFinMainList=mfCusReportAcountFeign.getContinuityReport(mfExamineHisQuery.getCusNo(),mfExamineHisQuery.getRptDate(),mfExamineHisQuery.getRptDate().substring(4,6),mfExamineHisQuery.getAccountId());
					cusFinMainList=setReportIsUsed(cusFinMainList);
	//				List<MfCusReportAcount> mfCusReportAcountList = pfsInterfaceFeign.getAll(cusFinMain);
//					if (cusFinMainList != null && !cusFinMainList.isEmpty()) {
//						for (CusFinMain cFinMain : cusFinMainList) { // 检查财务报表具体报表是否填写
//							cFinMain.setFinCapFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "1"));
//							cFinMain.setFinProFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "2"));
//							cFinMain.setFinCashFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "3"));
//							cFinMain.setFinSubjectFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "5"));
//						}
//					}
					model.addAttribute("cusFinMainList", cusFinMainList);
				}
			}
			//单独获取
			List<OptionsList> examOptionsList = null;
			if(StringUtils.isNotEmpty(paraMap.get("pactId"))){
				examOptionsList	= mfExamineHisFeign.getConfigMatchedByBussList(paraMap.get("pactId"),"finc");
			}else{
				examOptionsList	= mfExamineHisFeign.getConfigMatchedByCusList(paraMap.get("cusNo"));
			}
			changeFormProperty(formexamhisnew, "templateId", "optionArray", examOptionsList);
			List<MfExamineCard> examCardListDataList = (List<MfExamineCard>) initDataMap.get("examCardListData");
			model.addAttribute("examCardListDataList", examCardListDataList);
			model.addAttribute("dataMap", initDataMap);

			String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;

			model.addAttribute("scNo", scNo);
			model.addAttribute("examHisId", MapUtils.getString(initDataMap,"examHisId"));
			model.addAttribute("mfBusPactExamineList", mfExaminePactList);
			model.addAttribute("templateId", MapUtils.getString(initDataMap,"templateId",""));
			model.addAttribute("resultFormId", MapUtils.getString(initDataMap,"resultFormId",""));
			model.addAttribute("formexamhis001", formexamhisnew);
			model.addAttribute("formexamhis002", formexamhis002);
			model.addAttribute("formexamhis003", formexamhis003);
			model.addAttribute("query", "detail".equals(MapUtils.getString(initDataMap,"getFlag"))?"query":"");
			model.addAttribute("examEntrance",examEntrance);//业务入口标识
			model.addAttribute("cusNo",MapUtils.getString(initDataMap,"cusNo",""));//客户号
			model.addAttribute("appId",MapUtils.getString(initDataMap,"appId",""));//申请编号
			model.addAttribute("pactId",MapUtils.getString(initDataMap,"pactId",""));//合同编号
			model.addAttribute("fincId",MapUtils.getString(initDataMap,"fincId",""));//借据编号
		}catch (Exception e){
			logger.error("跳转到检查登记页面"+e.getMessage(),e);
		}
		return "component/examine/ExamineApplyAfter";
	}

	@RequestMapping(value = "/queryCusFinDataAjax")
	@ResponseBody
	public Map<String, Object> queryCusFinDataAjax(String ajaxData, String cusNo,String examHisId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isNotEmpty(examHisId)){
				MfExamineHis mfExamineHisQuery = new MfExamineHis();
				mfExamineHisQuery.setExamHisId(examHisId);
				mfExamineHisQuery = mfExamineHisFeign.getById(mfExamineHisQuery);
				if(StringUtil.isNotEmpty(cusNo)){
					//获取客户信息
					MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(mfExamineHisQuery.getCusNo());
					if(mfCusCustomer!=null){
						dataMap.put("cusType",mfCusCustomer.getCusBaseType());
					}
					//获取连续的财务报表
					List<MfCusReportAcount> cusFinMainList=mfCusReportAcountFeign.getContinuityReport(mfExamineHisQuery.getCusNo(),mfExamineHisQuery.getRptDate(),mfExamineHisQuery.getRptDate().substring(4,6),mfExamineHisQuery.getAccountId());
					cusFinMainList=setReportIsUsed(cusFinMainList);
					dataMap.put("flag", "success");
					dataMap.put("cusFinMainList", cusFinMainList);
				}
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	//判断财报是否被引用
	public List<MfCusReportAcount>  setReportIsUsed(List<MfCusReportAcount> list) throws Exception {
		Iterator it=list.iterator();
		while(it.hasNext()){
			MfCusReportAcount mfCusReportAccunt=(MfCusReportAcount)it.next();
			AppEval appEval=new AppEval();
			appEval.setCusNo(mfCusReportAccunt.getCusNo());
			appEval.setRptDate(mfCusReportAccunt.getWeeks());
			appEval.setRptType(mfCusReportAccunt.getReportType());
			List appEvalList=appEvalFeign.getListByCusNoAndRptDateAndRptType(appEval);
			if(appEvalList!=null&&appEvalList.size()>0){
				mfCusReportAccunt.setIsUsed(BizPubParm.YES_NO_Y);
			}else{
				mfCusReportAccunt.setIsUsed(BizPubParm.YES_NO_N);
			}
		}
		return list;
	}
	/**
	 * @Description 模型匹配数据入库
	 * @Author zhaomingguang
	 * @DateTime 2019/9/24 10:22
	 * @Param [ajaxData, entrance]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/insertAjaxNew")
	@ResponseBody
	public Map<String, Object> insertAjaxNew(String ajaxData,String checkCardFlag,String templateType) {

		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String,Object> paraMap = getMapByJson(ajaxData);
			String formId = "examhisRegister";
			if(StringUtil.isNotEmpty(templateType)&&"2".equals(templateType)){
				formId = "examhisRegister001";
			}
			FormData formexamhis001 = formService.getFormData(formId);
			getFormValue(formexamhis001, getMapByJson(ajaxData));
			if (this.validateFormData(formexamhis001)) {
				MfExamineHis mfExamineHis = new MfExamineHis();
				setObjValue(formexamhis001, mfExamineHis);
				mfExamineHis.setExamineType(templateType);
				if(!"2".equals(templateType)){
					mfExamineHis.setExamineType("0");
					String examiningFlag = mfExamineHisFeign.getCheckExistExaminingFlagNew(mfExamineHis);
					// 如果存在审批进行的贷后检查，不再重复保存
					if (BizPubParm.YES_NO_Y.equals(examiningFlag)) {
						dataMap.put("flag", "error");
						dataMap.put("msg", "存在正在处理中的贷后检查任务，请勿重复提交");
						return dataMap;
					}
				}
				if(StringUtil.isEmpty(mfExamineHis.getTemplateId())){
					mfExamineHis.setTemplateId("1000");
				}
				mfExamineHis.setAmtRest(null);
				//判断是否是从业务入口直接进行的贷后检查操作（有可能没有与之对应的贷后检查任务）
				if(StringUtils.isEmpty(mfExamineHis.getPasNo())){
					paraMap.put("regName",User.getRegName(request));
					paraMap.put("orgNo",User.getOrgNo(request));
					paraMap.put("userNo",User.getRegNo(request));
					paraMap.put("dueDate",DateUtil.getDate("yyyy-MM-dd"));
					paraMap.put("cusNo",mfExamineHis.getCusNo());
					Map<String,Object> resMap = sysTaskInfoFeign.insertForExamineTaskAjax(paraMap);
					if("success".equals(MapUtils.getString(resMap,"flag"))){
						mfExamineHis.setPasNo(MapUtils.getString(resMap,"sysPasNo"));
					}else{
                        dataMap.put("flag", "error");
                        dataMap.put("msg", MapUtils.getString(resMap,"msg"));
                        return dataMap;
					}
				}
				if("hide".equals(checkCardFlag)){
					mfExamineHis.setExamOpNo(User.getRegNo(request));
					mfExamineHis.setExamOpName(User.getRegName(request));
				}
				mfExamineHis.setExamineStage("2");
				mfExamineHisFeign.insert(mfExamineHis);
				String examHisId = mfExamineHis.getExamHisId();
				String templateId ="1000";
				MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
				mfExamineTemplateConfig.setTemplateId(templateId);
				mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
				dataMap.putAll(mfExamineHisFeign.getListData(examHisId, templateId));
				if(BizPubParm.YES_NO_N.equals(dataMap.get("indexFlag"))){
					//说明该检查模型下面没有设置检查卡的内容，将当前操作员的信息更新到检查人字段上
					MfExamineHis updateObj = new MfExamineHis();
					updateObj.setExamOpNo(User.getRegNo(request));
					updateObj.setExamOpName(User.getRegName(request));
					updateObj.setExamHisId(examHisId);
					mfExamineHisFeign.update(updateObj);
				}
				dataMap.put("flag", "success");
				if (mfExamineTemplateConfig.getFormTemplate() == null) {
					dataMap.put("baseForm", mfExamineTemplateConfig.getBaseFormTemplate());
				} else {
					dataMap.put("baseForm", mfExamineTemplateConfig.getFormTemplate());
				}
				MfExamineHis mfExamineHisQuery = new MfExamineHis();
				mfExamineHisQuery.setExamHisId(examHisId);
				mfExamineHisQuery = mfExamineHisFeign.getById(mfExamineHisQuery);
				FormData formcommon = formService.getFormData("examhis002");
				getFormValue(formcommon);
				getObjValue(formcommon, mfExamineHisQuery);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcommon, "bootstarpTag", "");
				FormData formcommon2 = formService.getFormData("examhis003");
				getFormValue(formcommon2);
				getObjValue(formcommon2, mfExamineHisQuery);
				String htmlStr2 = jsonFormUtil.getJsonStr(formcommon2, "bootstarpTag", "");
				dataMap.put("flag", "success");
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStr2", htmlStr2);
				dataMap.put("entityData", mfExamineHis);
				dataMap.put("examHisId", mfExamineHis.getExamHisId());
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			logger.error("模型匹配数据入库"+e.getMessage(),e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
		}
		return dataMap;
	}
	/**
	 * @Description 获取页面展示表单数据
	 * @Author zhaomingguang
	 * @DateTime 2019/9/27 11:19
	 * @Param [examHisId, query, formId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/getByIdAjaxForFormNew")
	@ResponseBody
	public Map<String, Object> getByIdAjaxForFormNew(String examHisId, String query, String formId){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ActionContext.initialize(request, response);
			FormService formService = new FormService();
			FormData formexamFormTemp = formService.getFormData(formId);
			/**
			 * 获取跟踪信息
			 */
			MfExamineHis mfExamineHis = new MfExamineHis();
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);

			/**
			 * 获取子表单信息
			 */
			if (formexamFormTemp.getFormId() == null) {
				MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
				mfExamineTemplateConfig.setTemplateId(mfExamineHis.getTemplateId());
				mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
				formexamFormTemp = formService.getFormData(mfExamineTemplateConfig.getFormTemplate());
			}
			//获取页面表单内容
			Map<String,Object> resultMap = mfExamineHisFeign.getBaseExaminfoNewData(mfExamineHis);
			if(StringUtil.isNotEmpty(mfExamineHis.getEndDate())){
				resultMap.put("endDate",DateUtil.getShowDateTime(mfExamineHis.getEndDate()));
			}
			if(StringUtil.isNotEmpty(mfExamineHis.getRemark())){
				resultMap.put("remark",mfExamineHis.getRemark());
			}
			resultMap.put("examOpName",mfExamineHis.getExamOpName());
			resultMap.put("examHisId",examHisId);
			getObjValue(formexamFormTemp, resultMap);
			getObjValue(formexamFormTemp, mfExamineHis);
			JsonFormUtil jfu = new JsonFormUtil();
			String formHtml = jfu.getJsonStr(formexamFormTemp, "bootstarpTag", "");
			dataMap.put("formHtml", formHtml);
			dataMap.putAll(mfExamineHisFeign.getListData(examHisId, mfExamineHis.getTemplateId()));
			if (mfExamineHis != null) {
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
		}catch (Exception e){
			logger.error("获取页面展示表单数据"+e.getMessage(),e);
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * @Description 跳转到查看忽略原因的页面
	 * @Author zhaomingguang
	 * @DateTime 2019/9/24 19:55
	 * @Param [model, pasNo, pasSts]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/ignoreExamineDetail")
	public String ignoreExamineDetail(Model model,String pasNo,String pasSts){
		try {
			FormService formService = new FormService();
			ActionContext.initialize(request, response);
			FormData formignoreExamineDetail = formService.getFormData("ignoreExamineDetail");
			SysTaskInfoIgnore sysTaskInfoIgnore = sysTaskInfoFeign.getSysTaskInfoIgnore(pasNo);
			String createTime = sysTaskInfoIgnore.getCreateDate()+" "+sysTaskInfoIgnore.getCreateTime();
			sysTaskInfoIgnore.setCreateTime(DateUtil.getFormatDateTime(createTime,"yyyy-MM-dd hh:mm:ss"));
			getObjValue(formignoreExamineDetail,sysTaskInfoIgnore);
			model.addAttribute("query", "");
			model.addAttribute("formignoreExamineDetail",formignoreExamineDetail);
		}catch (Exception e){
			logger.error("跳转到查看忽略原因的页面"+e.getMessage(),e);
		}
		return "component/examine/MfExamineHis_ignoreExamineDetail";
	}

	/**
	 * @Description 跳转到选择贷后检查任务列表
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 11:01
	 * @Param [model]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/getExamineHisData")
	public String getExamineHisData(Model model){
		model.addAttribute("query","");
		return "component/examine/MfExamineHis_examineHisListForSelect";
	}

	/**
	 * @Description 获取贷后检查任务页面的显示数据
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 11:01
	 * @Param [ajaxData]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/getExamineHisDataByPageAjax")
	@ResponseBody
	public Map<String,Object> getExamineHisDataByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
														  String ajaxData){
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			mfExamineHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfExamineHis.setCriteriaList(mfExamineHis, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfExamineHis",mfExamineHis));
			ipage = mfExamineHisFeign.getExamineHisDataByPageAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			logger.error("获取贷后检查任务页面的显示数据"+e.getMessage(),e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	/**
	 * @Description 根据贷后检查的id获取贷后检查信息
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 12:42
	 * @Param [examHisId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/getExamineHisInfo")
	@ResponseBody
	public Map<String,Object> getExamineHisInfo(String examHisId){
		Map<String,Object> dataMap = new HashMap<>();
		try {
			MfExamineHis mfExamineHis = new MfExamineHis();
			mfExamineHis.setExamHisId(examHisId);
			MfExamineHis resultObj = mfExamineHisFeign.getById(mfExamineHis);
			dataMap.putAll(mfExamineHisFeign.getListData(resultObj.getExamHisId(),resultObj.getTemplateId()));
			dataMap.put("flag","success");
			dataMap.put("mfExamineHis",resultObj);
		}catch (Exception e){
			logger.error("根据贷后检查的id获取贷后检查信息"+e.getMessage(),e);
			dataMap.put("flag","error");
			dataMap.put("msg",e.getMessage());
		}
		return dataMap;
	}

	/**
	 * @Description 从客户视图、合同视图、借据视图跳转到贷后检查详情页面
	 * @Author zhaomingguang
	 * @DateTime 2019/9/27 10:34
	 * @Param [pasNo]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/examineDetailResultNew")
	public String examineDetailResultNew(Model model,String pasNo){
		try {
			ActionContext.initialize(request, response);
			Map<String,String> paraMap = new HashMap<>();
			paraMap.putAll(getParaMap(pasNo));
			//获取页面表单需要的数据信息
			Map<String, Object> initDataMap = mfExamineHisFeign.getExamHisFormData(paraMap);
			initDataMap = JSONObject.fromObject(initDataMap);
			initDataMap.put("pasMinNo",paraMap.get("pasMinNo"));
			initDataMap.put("pasNo",pasNo);

			//单独获取
			List<MfExamineCard> examCardListDataList = (List<MfExamineCard>) initDataMap.get("examCardListData");
			model.addAttribute("examCardListDataList", examCardListDataList);
			model.addAttribute("dataMap", initDataMap);

			String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
			List<MfExamineHis> mfExamineHisList = (List<MfExamineHis>) initDataMap.get("mfExamineHisList");
			request.setAttribute("mfExamineHisList", mfExamineHisList);
			model.addAttribute("scNo", scNo);
			model.addAttribute("examHisId", MapUtils.getString(initDataMap,"examHisId"));
			model.addAttribute("templateId", MapUtils.getString(initDataMap,"templateId",""));
			model.addAttribute("resultFormId", MapUtils.getString(initDataMap,"resultFormId",""));
			model.addAttribute("query", "");
		}catch (Exception e){
			logger.error("跳转到贷后检查详情页面"+e.getMessage(),e);
		}
		return "component/examine/ExamineDetailResultNew";
	}

	/**
	 * @Description 获取查询数据需要的参数
	 * @Author zhaomingguang
	 * @DateTime 2019/9/27 11:03
	 * @Param [pasNo]
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 */
	private Map<String,String> getParaMap(String pasNo)throws Exception{
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		SysTaskInfo resultObj = sysTaskInfoFeign.getById(sysTaskInfo);
		String pasMinNo = resultObj.getPasMinNo();
		Map<String,String> paraMap = new HashMap<>();
		if(PasConstant.PAS_SUB_TYPE_LLC_TASK_CUS_BASE.equals(pasMinNo)){//客户
			paraMap.put("cusNo",resultObj.getBizPkNo());
		}else if(PasConstant.PAS_SUB_TYPE_LLC_TASK_PACT_BASE.equals(pasMinNo)){//合同
			paraMap.put("pactNo",resultObj.getBizPkNo());
			paraMap.put("pactId",resultObj.getBizPkNo());
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactId(resultObj.getBizPkNo());
			mfBusPact = mfBusPactFeign.getById(mfBusPact);
			if(mfBusPact!=null){
				paraMap.put("cusNo",mfBusPact.getCusNo());
			}
		}else if(PasConstant.PAS_SUB_TYPE_LLC_TASK_FINC_BASE.equals(pasMinNo)){//借据
			paraMap.put("fincId",resultObj.getBizPkNo());
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincId(resultObj.getBizPkNo());
			MfBusFincApp resultMfBusFincApp = mfBusFincAppFeign.getMfBusFincAppById(mfBusFincApp);
			paraMap.put("cusNo",resultMfBusFincApp.getCusNo());
			paraMap.put("pactNo",resultMfBusFincApp.getPactId());
			paraMap.put("pactId",resultMfBusFincApp.getPactId());
		}else {
		}
		paraMap.put("pasMinNo",pasMinNo);
		paraMap.put("pasNo",pasNo);
		return paraMap;
	}
    /**
     * @Description 跳转到贷后检查登记页面
     * @Author zhaomingguang
     * @DateTime 2019/10/18 9:01
     * @Param [model]
     * @return java.lang.String
     */
    @RequestMapping("/examineHisRegister")
	public String examineHisRegister(Model model,String templateType){
        try {
            ActionContext.initialize(request, response);
            FormService formService = new FormService();
            String formId = "examhisRegister";
            if(StringUtil.isNotEmpty(templateType)&&"2".equals(templateType)){
				formId = "examhisRegister001";
			}
            FormData formexamhisnew = formService.getFormData(formId);
            //获取页面表单需要的数据信息
            Map<String, Object> initDataMap = new HashMap<>();
            MfExamineHis mfExamineHis = new MfExamineHis();
            initDataMap.put("mfExamineHis", mfExamineHis);
            initDataMap.put("examHisId", WaterIdUtil.getWaterId("exam"));
            initDataMap.put("getFlag", "add");
            initDataMap = JSONObject.fromObject(initDataMap);
            getObjValue(formexamhisnew, initDataMap);
            model.addAttribute("dataMap", initDataMap);
            String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
            model.addAttribute("scNo", scNo);
            model.addAttribute("examHisId", MapUtils.getString(initDataMap,"examHisId"));
            model.addAttribute("templateId", MapUtils.getString(initDataMap,"templateId",""));
            model.addAttribute("resultFormId", MapUtils.getString(initDataMap,"resultFormId",""));
            model.addAttribute("formexamhis001", formexamhisnew);
            model.addAttribute("query", "detail".equals(MapUtils.getString(initDataMap,"getFlag"))?"query":"");
            model.addAttribute("examEntrance","loanAfter");//贷后业务入口标识
			model.addAttribute("templateType", templateType);
        }catch (Exception e){
            logger.error("跳转到检查登记页面"+e.getMessage(),e);
        }
        return "component/examine/ExamineApplyAfter";
    }

    /**
     * @Description 获取贷后检查模型数据
     * @Author zhaomingguang
     * @DateTime 2019/10/18 11:30
     * @Param [ajaxData]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/getConfigMatchedListAjax")
    @ResponseBody
    public Map<String,Object> getConfigMatchedListAjax(String ajaxData){
        Map<String,Object> dataMap = new HashMap<String, Object>();
        try {
            ActionContext.initialize(request,response);
            Map<String,Object> paraMap = JSONObject.fromObject(ajaxData);
            List<OptionsList> examOptionsList = null;
            if(StringUtils.isNotEmpty(MapUtils.getString(paraMap,"pactId"))){
                examOptionsList	= mfExamineHisFeign.getConfigMatchedByBussList(MapUtils.getString(paraMap,"pactId"),"finc");
            }else{
                examOptionsList	= mfExamineHisFeign.getConfigMatchedByCusList(MapUtils.getString(paraMap,"cusNo"));
            }
            if(CollectionUtils.isNotEmpty(examOptionsList)){
                dataMap.put("flag", "success");
                dataMap.put("templateList", examOptionsList);
            }else{
                dataMap.put("flag", "error");
            }
        }catch (Exception e){
            dataMap.put("flag", "error");
            logger.error("获取贷后检查模型数据失败"+e.getMessage(),e);
        }
        return dataMap;
    }

	/**
	 * @Description 跳转到贷后检查登记页面
	 * @Author zhaomingguang
	 * @DateTime 2019/10/18 9:01
	 * @Param [model]
	 * @return java.lang.String
	 */
	@RequestMapping("/examineHisAudit")
	public String examineHisAudit(Model model,String templateType){
		try {
			ActionContext.initialize(request, response);
			FormService formService = new FormService();
			String formId = "examhisRegister";
			if(StringUtil.isNotEmpty(templateType)&&"2".equals(templateType)){
				formId = "examhisRegister001";
			}
			FormData formexamhisnew = formService.getFormData(formId);
			//获取页面表单需要的数据信息
			Map<String, Object> initDataMap = new HashMap<>();
			MfExamineHis mfExamineHis = new MfExamineHis();
			initDataMap.put("mfExamineHis", mfExamineHis);
			initDataMap.put("examHisId", WaterIdUtil.getWaterId("exam"));
			initDataMap.put("getFlag", "add");
			initDataMap = JSONObject.fromObject(initDataMap);
			getObjValue(formexamhisnew, initDataMap);
			model.addAttribute("dataMap", initDataMap);
			String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
			model.addAttribute("scNo", scNo);
			model.addAttribute("examHisId", MapUtils.getString(initDataMap,"examHisId"));
			model.addAttribute("templateId", MapUtils.getString(initDataMap,"templateId",""));
			model.addAttribute("resultFormId", MapUtils.getString(initDataMap,"resultFormId",""));
			model.addAttribute("formexamhis001", formexamhisnew);
			model.addAttribute("query", "detail".equals(MapUtils.getString(initDataMap,"getFlag"))?"query":"");
			model.addAttribute("examEntrance","loanAfter");//贷后业务入口标识
			model.addAttribute("templateType", templateType);
		}catch (Exception e){
			logger.error("跳转到检查登记页面"+e.getMessage(),e);
		}
		return "component/examine/ExamineApplyAudit";
	}

	/**
	 * @Description 模型匹配数据入库
	 * @DateTime 2019/9/24 10:22
	 * @Param [ajaxData, entrance]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/insertAuditAjax")
	@ResponseBody
	public Map<String, Object>  insertAuditAjax(Model model,String ajaxData,String checkCardFlag,String templateType) {

		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String,Object> initDataMap = getMapByJson(ajaxData);
			String formId = "examhisnew";
			if(StringUtil.isNotEmpty(templateType)&&"2".equals(templateType)){
				formId = "examhisRegister001";
			}
			FormData formexamhis001 = formService.getFormData(formId);
			getFormValue(formexamhis001, getMapByJson(ajaxData));
			if (this.validateFormData(formexamhis001)) {
				MfExamineHis mfExamineHis = new MfExamineHis();
				setObjValue(formexamhis001, mfExamineHis);
				if(!"2".equals(templateType)){
					mfExamineHis.setExamineType("0");
					String examiningFlag = mfExamineHisFeign.getCheckExistExaminingFlagNew(mfExamineHis);
					// 如果存在审批进行的贷后检查，不再重复保存
					if (BizPubParm.YES_NO_Y.equals(examiningFlag)) {
						dataMap.put("flag", "error");
						dataMap.put("msg", "存在正在处理中的贷后检查任务，请勿重复提交");
						//return dataMap;
					}
				}

				/*//判断是否是从业务入口直接进行的贷后检查操作（有可能没有与之对应的贷后检查任务）
				if(StringUtils.isEmpty(mfExamineHis.getPasNo())){
					paraMap.put("regName",User.getRegName(request));
					paraMap.put("orgNo",User.getOrgNo(request));
					paraMap.put("userNo",User.getRegNo(request));
					paraMap.put("dueDate",DateUtil.getDate("yyyy-MM-dd"));
					paraMap.put("cusNo",mfExamineHis.getCusNoSupplier());
					Map<String,Object> resMap = sysTaskInfoFeign.insertForExamineTaskAjax(paraMap);
					if("success".equals(MapUtils.getString(resMap,"flag"))){
						mfExamineHis.setPasNo(MapUtils.getString(resMap,"sysPasNo"));
					}else{
						dataMap.put("flag", "error");
						dataMap.put("msg", MapUtils.getString(resMap,"msg"));
						//return dataMap;
					}
				}*/
				if("hide".equals(checkCardFlag)){
					mfExamineHis.setExamOpNo(User.getRegNo(request));
					mfExamineHis.setExamOpName(User.getRegName(request));
				}
				mfExamineHis.setExamineType(templateType);//使用老字段检查类型存储模板类型，0保后检查，2实地核查
				mfExamineHisFeign.insert(mfExamineHis);
				String examHisId = mfExamineHis.getExamHisId();
				String templateId = MapUtils.getString(initDataMap,"templateId");
				MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
				mfExamineTemplateConfig.setTemplateId(templateId);
				mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);

				String conFormId = mfExamineTemplateConfig.getFormTemplate();
				FormData baseForm = formService.getFormData(conFormId);
				String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
				model.addAttribute("scNo", scNo);
				model.addAttribute("examHisId", examHisId);
				model.addAttribute("templateId", MapUtils.getString(initDataMap,"templateId",""));
				model.addAttribute("resultFormId", conFormId);
				model.addAttribute("formexamhis001", formexamhis001);
				model.addAttribute("query", "detail".equals(MapUtils.getString(initDataMap,"getFlag"))?"query":"");
				model.addAttribute("cusNo",MapUtils.getString(initDataMap,"cusNo",""));//客户号
				model.addAttribute("appId",MapUtils.getString(initDataMap,"appId",""));//申请编号
				model.addAttribute("pactId",MapUtils.getString(initDataMap,"pactId",""));//合同编号
				model.addAttribute("fincId",MapUtils.getString(initDataMap,"fincId",""));//借据编号

				dataMap.put("flag", "success");
				dataMap.put("entityData", mfExamineHis);
				dataMap.put("examHisId", mfExamineHis.getExamHisId());
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {

			}
		} catch (Exception e) {
			logger.error("模型匹配"+e.getMessage(),e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
		}
		return dataMap;
	}

	/**
	 * @Description 跳转到检查登记页面
	 * @DateTime 2019/9/23 15:46
	 * @Param [model, pasNo]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/examineRegister")
	public String examineRegister(Model model, @RequestParam(required = false) String examHisId){
		try {
			ActionContext.initialize(request, response);
			FormService formService = new FormService();
			String formId = "examhisRegister001";
			FormData formexamhisnew = formService.getFormData(formId);

			Map<String,String> paraMap = new HashMap<>();
			MfExamineHis mfExamineHis = new MfExamineHis();
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);

			getObjValue(formexamhisnew, mfExamineHis);

			MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
			mfExamineTemplateConfig.setTemplateId(mfExamineHis.getTemplateId());
			mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
			String conFormId = mfExamineTemplateConfig.getFormTemplate();

			Map<String,Object> initDataMap = new HashMap<>();
			String getFlag = "apply";
			initDataMap.put("getFlag",getFlag);
			initDataMap.put("examHisId", mfExamineHis.getExamHisId());
			initDataMap.put("templateId", mfExamineHis.getTemplateId());
			initDataMap = JSONObject.fromObject(initDataMap);
			model.addAttribute("dataMap", initDataMap);

			String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
			model.addAttribute("scNo", scNo);
			model.addAttribute("examHisId", examHisId);
			model.addAttribute("templateId", mfExamineHis.getTemplateId());
			model.addAttribute("resultFormId", conFormId);
			model.addAttribute("formexamhis001", formexamhisnew);
			model.addAttribute("query", getFlag);
			model.addAttribute("examEntrance","examEntrance");//业务入口标识
			model.addAttribute("cusNo",MapUtils.getString(initDataMap,"cusNo",""));//客户号
			model.addAttribute("appId",MapUtils.getString(initDataMap,"appId",""));//申请编号
			model.addAttribute("pactId",MapUtils.getString(initDataMap,"pactId",""));//合同编号
			model.addAttribute("fincId",MapUtils.getString(initDataMap,"fincId",""));//借据编号
		}catch (Exception e){
			logger.error("跳转到检查登记页面"+e.getMessage(),e);
		}
		return "component/examine/ExamineApplyAudit";
	}

	/**
	 * @Description 获取实地考察页面展示表单数据
	 * @Param [examHisId, query, formId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/getByIdAjaxAuditForm")
	@ResponseBody
	public Map<String, Object> getByIdAjaxAuditForm(String examHisId, String query, String formId){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ActionContext.initialize(request, response);
			FormService formService = new FormService();
			FormData formexamFormTemp = formService.getFormData(formId);
			/**
			 * 获取跟踪信息
			 */
			MfExamineHis mfExamineHis = new MfExamineHis();
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);

			/**
			 * 获取子表单信息
			 */
			if (formexamFormTemp.getFormId() == null) {
				MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
				mfExamineTemplateConfig.setTemplateId(mfExamineHis.getTemplateId());
				mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
				formexamFormTemp = formService.getFormData(mfExamineTemplateConfig.getFormTemplate());
			}
			//获取页面表单内容
			Map<String,Object> resultMap = new HashMap<>();
			if(StringUtil.isNotEmpty(mfExamineHis.getEndDate())){
				resultMap.put("endDate",DateUtil.getShowDateTime(mfExamineHis.getEndDate()));
			}
			if(StringUtil.isNotEmpty(mfExamineHis.getRemark())){
				resultMap.put("remark",mfExamineHis.getRemark());
			}
			resultMap.put("examOpName",mfExamineHis.getExamOpName());
			resultMap.put("examHisId",examHisId);
			getObjValue(formexamFormTemp, resultMap);
			getObjValue(formexamFormTemp, mfExamineHis);
			JsonFormUtil jfu = new JsonFormUtil();
			String formHtml = jfu.getJsonStr(formexamFormTemp, "bootstarpTag", "");
			dataMap.put("formHtml", formHtml);
			if (mfExamineHis != null) {
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
		}catch (Exception e){
			logger.error("获取页面展示表单数据"+e.getMessage(),e);
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * @Description 跳转到检查详情页面
	 * @Param [model, pasNo]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/examineDetail")
	public String examineDetail(Model model, @RequestParam(required = false) String examHisId){
		try {
			ActionContext.initialize(request, response);
			FormService formService = new FormService();
			String formId = "examhisRegister002";
			FormData formexamhisnew = formService.getFormData(formId);

			Map<String,String> paraMap = new HashMap<>();
			MfExamineHis mfExamineHis = new MfExamineHis();
			mfExamineHis.setExamHisId(examHisId);
			mfExamineHis = mfExamineHisFeign.getById(mfExamineHis);

			getObjValue(formexamhisnew, mfExamineHis);

			MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
			mfExamineTemplateConfig.setTemplateId(mfExamineHis.getTemplateId());
			mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
			String conFormId = mfExamineTemplateConfig.getFormTemplate();

			//添加贷后检查模型数据
			/*List<OptionsList> opinionTypeList = new ArrayList<OptionsList>();
			OptionsList optionsList= new OptionsList();
			optionsList= new OptionsList();
			optionsList.setOptionValue(mfExamineTemplateConfig.getTemplateId());
			optionsList.setOptionLabel(mfExamineTemplateConfig.getTemplateName());
			optionsList.setOptionId("");
			opinionTypeList.add(optionsList);
			changeFormProperty(formexamhisnew, "templateId", "optionArray", opinionTypeList);*/

			Map<String,Object> initDataMap = new HashMap<>();
			String getFlag = "detail";
			initDataMap.put("getFlag",getFlag);
			initDataMap.put("examHisId", mfExamineHis.getExamHisId());
			initDataMap.put("templateId", mfExamineHis.getTemplateId());
			initDataMap = JSONObject.fromObject(initDataMap);
			model.addAttribute("dataMap", initDataMap);

			String scNo = BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK;
			model.addAttribute("scNo", scNo);
			model.addAttribute("examHisId", examHisId);
			model.addAttribute("templateId", mfExamineHis.getTemplateId());
			model.addAttribute("resultFormId", conFormId);
			model.addAttribute("formexamhis001", formexamhisnew);
			model.addAttribute("query", "query");
			model.addAttribute("examEntrance","examEntrance");//业务入口标识
			model.addAttribute("cusNo",MapUtils.getString(initDataMap,"cusNo",""));//客户号
			model.addAttribute("appId",MapUtils.getString(initDataMap,"appId",""));//申请编号
			model.addAttribute("pactId",MapUtils.getString(initDataMap,"pactId",""));//合同编号
			model.addAttribute("fincId",MapUtils.getString(initDataMap,"fincId",""));//借据编号
		}catch (Exception e){
			logger.error("跳转到检查登记页面"+e.getMessage(),e);
		}
		return "component/examine/ExamineApplyAudit";
	}

	/**
	 * 检查登记提交
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAuditAjax")
	public Map<String, Object> updateAuditAjax(String formId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			FormData formexamhis001 = formService.getFormData(formId);
			if (formexamhis001.getFormId() == null) {
				formexamhis001 = formService.getFormData("baseexaminfoNew_1008");
			}
			getFormValue(formexamhis001, getMapByJson(ajaxData));
			if (this.validateFormData(formexamhis001)) {
				mfExamineHis = new MfExamineHis();
				setObjValue(formexamhis001, mfExamineHis);
				//如果页面上没有传入检查结束时间，则把当前时间传入
				if(StringUtil.isEmpty(mfExamineHis.getEndDate())){
					mfExamineHis.setEndDate(DateUtil.getDate());
				}
				mfExamineHis.setExamineStage("4");//更新检查阶段为完成
				mfExamineHisFeign.update(mfExamineHis);
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
			throw e;
		}
		return dataMap;
	}
	/**
	 * @Description 自动分级
	 * @Author zhaomingguang
	 * @DateTime 2019/9/25 18:38
	 * @Param [ajaxData]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/autoLevel")
	@ResponseBody
	public Map<String,Object> autoLevel(String ajaxData,String tableId){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		try {
			JSONObject json = JSONObject.fromObject(ajaxData);
			Map<String, String> paraMap = (Map<String, String>) JSONObject.toBean(json, Map.class);
			String cusNo = paraMap.get("cusNo");

			// ---------------根据客户号获取客户信息--------------
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
			//查询客户代偿信息
			//保后跟踪时系统自动调用三方接口，若有新增工商处罚、法律诉讼（个人及公司）、同盾保后分值超过20分，则内部分级自动分级为预警
			Map<String, String> threeMap = new HashMap<String, String>();
			threeMap.put("cusNo",cusNo);
			threeMap.put("cusName",mfCusCustomer.getCusName());
			threeMap.put("idNum",mfCusCustomer.getIdNum());
			threeMap.put("cusTel",mfCusCustomer.getCusTel());
			dataMap =  mfExamineHisFeign.autoLevel(threeMap);
		} catch (Exception e) {
			logger.error("根据客户号获取客户相关的信息"+e.getMessage(),e);
			dataMap.put("flag", "error");
			dataMap.put("msg",e.getMessage());
		}
		return dataMap;
	}
}
