package app.component.cus.closingmanage.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.closingmanage.entity.MfCusClosingManage;
import app.component.cus.closingmanage.feign.MfCusClosingManageFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.recourse.entity.MfBusRecourseApply;
import app.component.recourse.feign.MfBusRecourseApplyFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
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

@Controller
@RequestMapping("/mfCusClosingManage")
public class MfCusClosingManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusClosingManageFeign mfCusClosingManageFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusRecourseApplyFeign mfBusRecourseApplyFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	
	
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/cus/closingmanage/MfCusClosingManage_List";
	}
	
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusClosingManage mfCusClosingManage = new MfCusClosingManage();
		try {
			mfCusClosingManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusClosingManage.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfCusClosingManage.setCriteriaList(mfCusClosingManage, ajaxData);// 我的筛选
			mfCusClosingManage.setCusNo(cusNo);
			// mfBusFincApp.setPactSts(pactSts);
			// this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
			String isOpen = PropertiesUtil.getCloudProperty("cloud.loanafter_filter");
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusClosingManage", mfCusClosingManage));
			ipage = mfCusClosingManageFeign.findByPage(ipage);
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
	 * 新增页面
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String cusNo, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusClosingManage mfCusClosingManage = new MfCusClosingManage();
		mfCusClosingManage.setNextBrName(User.getOrgName(request));
		mfCusClosingManage.setNextBrNo(User.getOrgNo(request));
		mfCusClosingManage.setId(WaterIdUtil.getWaterId());
		String formId = "cusClosingManageBase";
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formcusClosingManageBase = formService.getFormData(formId);
			if (formcusClosingManageBase.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else { 
				getFormValue(formcusClosingManageBase);
				getObjValue(formcusClosingManageBase,mfCusClosingManage);
			}
			model.addAttribute("formcusClosingManageBase", formcusClosingManageBase);
		}
		model.addAttribute("query", "");
		model.addAttribute("id", mfCusClosingManage.getId());
		return "/component/cus/closingmanage/MfCusClosingManage_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusClosingManage mfCusClosingManage = new MfCusClosingManage();
		mfCusClosingManage.setId(id);
		FormData formcusClosingManageDetail = formService.getFormData("cusClosingManageDetail");
		String flag = "update";
		getFormValue(formcusClosingManageDetail);
		mfCusClosingManage = mfCusClosingManageFeign.getById(mfCusClosingManage);
		getObjValue(formcusClosingManageDetail, mfCusClosingManage);
		model.addAttribute("formcusClosingManageDetail", formcusClosingManageDetail);
		model.addAttribute("id", mfCusClosingManage.getId());
		model.addAttribute("query", "");
		return "/component/cus/closingmanage/MfCusClosingManage_Detail";
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
		String formId = (String) getMapByJson(ajaxData).get("formId");
		FormData formcusper00002 = formService.getFormData(formId);
		getFormValue(formcusper00002, getMapByJson(ajaxData));
		MfCusClosingManage mfCusClosingManageJsp = new MfCusClosingManage();
		setObjValue(formcusper00002, mfCusClosingManageJsp);
		MfCusClosingManage mfCusClosingManage = mfCusClosingManageFeign.getById(mfCusClosingManageJsp);
		if(mfCusClosingManage!=null){
			try{
				mfCusClosingManage = (MfCusClosingManage) EntityUtil.reflectionSetVal(mfCusClosingManage, mfCusClosingManageJsp, getMapByJson(ajaxData));
				mfCusClosingManageFeign.update(mfCusClosingManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
		MfCusClosingManage mfCusClosingManage = new MfCusClosingManage();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusClosingManageAction").getAddModel();
			}
			FormData formmfcusMeManageBase = formService.getFormData(formId);
			getFormValue(formmfcusMeManageBase, map);
			if (this.validateFormData(formmfcusMeManageBase)) {

				setObjValue(formmfcusMeManageBase, mfCusClosingManage);
				mfCusClosingManageFeign.update(mfCusClosingManage);

				String cusNo = mfCusClosingManage.getCusNo();
				mfCusClosingManage.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusClosingManage", mfCusClosingManage));
				String tableHtml = jtu.getJsonStr("tablecusMeManageBase", "tableTag",
						(List<MfCusClosingManage>) mfCusClosingManageFeign.findByPage(ipage).getResult(), null,
						true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusClosingManage mfCusClosingManage = new MfCusClosingManage();
		mfCusClosingManage.setId(id);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusEquityInfo = (MfCusEquityInfo)JSONObject.toBean(jb,
			// MfCusEquityInfo.class);
			mfCusClosingManageFeign.delete(mfCusClosingManage);
			// getTableData();
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
	
	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusClosingManageAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusMeManage0003 = formService.getFormData(formId);
			MfCusClosingManage mfCusClosingManage = new MfCusClosingManage();
			mfCusClosingManage.setId(id);
			mfCusClosingManage = mfCusClosingManageFeign.getById(mfCusClosingManage);
			getObjValue(formcusMeManage0003, mfCusClosingManage, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusMeManage0003, "propertySeeTag", "");
			if (mfCusClosingManage != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusClosingManage);
		}
		return dataMap;
	}

	@RequestMapping("/selectAppName")
	@ResponseBody
	public Map<String, Object> selectAppName(String ajaxData,int pageNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
		try {
			mfBusRecourseApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusRecourseApply", mfBusRecourseApply));
			//自定义查询Bo方法
			ipage = mfBusRecourseApplyFeign.findByAppName(ipage);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 保存结案申请信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData,String ajaxDataList)throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcusClosingManageBase = formService.getFormData(formId);
			getFormValue(formcusClosingManageBase, getMapByJson(ajaxData));
			if(this.validateFormData(formcusClosingManageBase)){
				MfCusClosingManage mfCusClosingManage = new MfCusClosingManage();
				setObjValue(formcusClosingManageBase, mfCusClosingManage);
				mfCusClosingManage.setClosingListStr(ajaxDataList);
				dataMap = mfCusClosingManageFeign.insertClosingManage(mfCusClosingManage);
				Map<String, String> paramMap = new HashMap<String, String>();
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 进入审批视角（审批页面）
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author fuchen
	 * @date 2020-03-24 上午10:26:55
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String id, String taskId, String hideOpinionType, String activityType, String isPrimary)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		if(id == null && taskId.indexOf(",")!=-1){//未知原因id有时为null.
			id = taskId.split(",")[0];
		}
		MfCusClosingManage mfCusClosingManage = new MfCusClosingManage();
		mfCusClosingManage.setId(id);
		mfCusClosingManage = mfCusClosingManageFeign.getById(mfCusClosingManage);
		String idTmp = id;
		taskId = id;
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(idTmp, id);// 当前审批节点task
	//	String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

		Map<String, String> map = new HashMap<String, String>();
		String cusNo = mfCusClosingManage.getCusNo();
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("cusNo", cusNo);
		// 处理原始的数据（利率转换）
		BizPubParm.WKF_NODE node = BizPubParm.WKF_NODE.contract_approval;
		String formId = "cusClosingManageWkf";
		FormData formcusClosingManageWkf = formService.getFormData(formId);
		model.addAttribute("formId", formId);
		mfCusClosingManage.setApprovalOpinion(null);
		getObjValue(formcusClosingManageWkf,mfCusClosingManage);

		//scNo = BizPubParm.SCENCE_TYPE_DOC_PACT_APPROVAL;
		//model.addAttribute("scNo", scNo);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		List<OptionsList> opinionTypeListNew = new ArrayList<>();
		for(OptionsList Options : opinionTypeList){
			if(Options.getOptionValue().equals("1") || Options.getOptionValue().equals("2") ){
				opinionTypeListNew.add(Options);
			}
		}
		//this.changeFormProperty(formcusClosingManageWkf, "opinionType", "optionArray", opinionTypeListNew);
		//添加项目标识原因，审批初始化弹出层的字段有的客户不需要没配置，还在后面展示。（这个问题解决了可以去掉标识）
		//BusCheckMessageBean busCheckMessageBean = mfBusCheckConfFeign.doBusCheckOperation(new BusParamsBean(),"newhope");
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(String.valueOf(taskAppro.getDbid()), User.getRegNo(request));
		this.changeFormProperty(formcusClosingManageWkf, "opinionType", "optionArray", opinionTypeListNew);
		this.changeFormProperty(formcusClosingManageWkf, "taskId", "initValue", taskId);
		this.changeFormProperty(formcusClosingManageWkf, "dbid", "initValue", String.valueOf(taskAppro.getDbid()));
		this.changeFormProperty(formcusClosingManageWkf, "wkfId", "initValue", id);
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formcusClosingManageWkf", formcusClosingManageWkf);
		model.addAttribute("taskId", id);
		model.addAttribute("id", id);
		model.addAttribute("cusNo", mfCusClosingManage.getCusNo());
		model.addAttribute("mfCusClosingManage", mfCusClosingManage);
		model.addAttribute("query", "");
		model.addAttribute("isPrimary", isPrimary);
		return "/component/cus/closingmanage/WkfClosingManageViewPoint";
	}

	/**
	 * 将流程提交到下一个节点
	 * @author fc
	 * date 2020-03-24
	 */
	@RequestMapping(value = "/doCommitWkf")
	@ResponseBody
	public Map<String, Object> doCommitWkf(Model model, String wkfId,String ajaxData)throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap.put("regNo",User.getRegNo(request));
			dataMap.put("wkfId",wkfId);
			dataMap.put("ajaxData",ajaxData);
			Map<String, Object> jsonDatas = getMapByJson(ajaxData);
			dataMap.put("jsonDatas",jsonDatas);
			dataMap.put("compensatoryId",jsonDatas.get("compensatoryId"));
			dataMap = mfCusClosingManageFeign.doCommitWkf(dataMap);
			if(dataMap.get("result") != null && BizPubParm.YES_NO_Y.equals(dataMap.get("result"))){
				dataMap.put("msg", dataMap.get("msg"));
				dataMap.put("flag", "success");
			}else{
				dataMap.put("msg", MessageEnum.ERROR_SERVER);
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
			throw e;
		}
		return dataMap;
	}
}
