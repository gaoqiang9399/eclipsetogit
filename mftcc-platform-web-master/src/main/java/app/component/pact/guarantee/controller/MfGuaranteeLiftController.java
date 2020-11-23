package app.component.pact.guarantee.controller;

import app.base.User;
import app.component.common.DateUtil;
import app.component.common.EntityUtil;
import app.component.pact.guarantee.entity.MfGuaranteeLift;
import app.component.pact.guarantee.entity.MfGuaranteeRegistration;
import app.component.pact.guarantee.feign.MfGuaranteeLiftFeign;
import app.component.pact.guarantee.feign.MfGuaranteeRegistrationFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfGuaranteeLiftController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 24 15:24:59 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfGuaranteeLift")
public class MfGuaranteeLiftController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfGuaranteeLiftFeign mfGuaranteeLiftFeign;
	@Autowired
	private MfGuaranteeRegistrationFeign mfGuaranteeRegistrationFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/guarantee/MfGuaranteeLift_List";
	}
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRenewInsuranceListPage")
	public String getRenewInsuranceListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/guarantee/MfGuaranteeLift_RenewInsuranceList";
	}
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLogoutReopenListPage")
	public String getLogoutReopenListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/guarantee/MfGuaranteeLift_LogoutReopenList";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
		try {
			mfGuaranteeLift.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfGuaranteeLift.setCriteriaList(mfGuaranteeLift, ajaxData);//我的筛选
			//mfGuaranteeLift.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfGuaranteeLift,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfGuaranteeLift", mfGuaranteeLift));
			//自定义查询Feign方法
			ipage = mfGuaranteeLiftFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findRenewInsuranceByPageAjax")
	public Map<String, Object> findRenewInsuranceByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
		try {
			mfGuaranteeLift.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfGuaranteeLift.setCriteriaList(mfGuaranteeLift, ajaxData);//我的筛选
			//mfGuaranteeLift.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfGuaranteeLift,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfGuaranteeLift", mfGuaranteeLift));
			//自定义查询Feign方法
			ipage = mfGuaranteeLiftFeign.findRenewInsuranceByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findLogoutReopenByPageAjax")
	public Map<String, Object> findLogoutReopenByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
		try {
			mfGuaranteeLift.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfGuaranteeLift.setCriteriaList(mfGuaranteeLift, ajaxData);//我的筛选
			//mfGuaranteeLift.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfGuaranteeLift,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfGuaranteeLift", mfGuaranteeLift));
			//自定义查询Feign方法
			ipage = mfGuaranteeLiftFeign.findLogoutReopenByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			FormData formguaranteeLiftAdd = formService.getFormData(formId);
			getFormValue(formguaranteeLiftAdd, map);
			if(this.validateFormData(formguaranteeLiftAdd)){
				MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
				setObjValue(formguaranteeLiftAdd, mfGuaranteeLift);
				mfGuaranteeLift.setGuaId(mfGuaranteeLift.getId());
				mfGuaranteeLift = mfGuaranteeLiftFeign.insert(mfGuaranteeLift);
				String liftType = mfGuaranteeLift.getLiftType();
				if("2".equals(liftType) || "3".equals(liftType) || "4".equals(liftType) ){
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("userRole", mfGuaranteeLift.getApproveNodeName());
					paramMap.put("opNo", mfGuaranteeLift.getApprovePartName());
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				}else{
					dataMap.put("msg", "解保成功");
				}
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formguaranteeLiftAdd = formService.getFormData("guaranteeLiftAdd");
		getFormValue(formguaranteeLiftAdd, getMapByJson(ajaxData));
		MfGuaranteeLift mfGuaranteeLiftJsp = new MfGuaranteeLift();
		setObjValue(formguaranteeLiftAdd, mfGuaranteeLiftJsp);
		MfGuaranteeLift mfGuaranteeLift = mfGuaranteeLiftFeign.getById(mfGuaranteeLiftJsp);
		if(mfGuaranteeLift!=null){
			try{
				mfGuaranteeLift = (MfGuaranteeLift)EntityUtil.reflectionSetVal(mfGuaranteeLift, mfGuaranteeLiftJsp, getMapByJson(ajaxData));
				mfGuaranteeLiftFeign.update(mfGuaranteeLift);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
		try{
			FormData formguaranteeLiftAdd = formService.getFormData("guaranteeLiftAdd");
			getFormValue(formguaranteeLiftAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formguaranteeLiftAdd)){
				mfGuaranteeLift = new MfGuaranteeLift();
				setObjValue(formguaranteeLiftAdd, mfGuaranteeLift);
				mfGuaranteeLiftFeign.update(mfGuaranteeLift);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formguaranteeLiftAdd = formService.getFormData("guaranteeLiftAdd");
		MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
		mfGuaranteeLift.setId(id);
		mfGuaranteeLift = mfGuaranteeLiftFeign.getById(mfGuaranteeLift);
		getObjValue(formguaranteeLiftAdd, mfGuaranteeLift,formData);
		if(mfGuaranteeLift!=null){
			dataMap.put("flag", "success");
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
		mfGuaranteeLift.setId(id);
		try {
			mfGuaranteeLiftFeign.delete(mfGuaranteeLift);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputLift")
	public String inputLift(Model model,String pactId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		String queryFile = "";
		String formId = "guaranteeAddLift";
		mfGuaranteeRegistration.setPactId(pactId);
		mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
		mfGuaranteeRegistration.setRegDate(DateUtil.getDate());
		mfGuaranteeRegistration.setOpName(User.getRegName(request));
		String id = mfGuaranteeRegistration.getId();
		FormData formguaranteeAddLift = formService.getFormData(formId);
		getObjValue(formguaranteeAddLift, mfGuaranteeRegistration);
		model.addAttribute("formguaranteeAddLift", formguaranteeAddLift);
		model.addAttribute("query", "");
		model.addAttribute("queryFile", queryFile);
		model.addAttribute("id", id);
		model.addAttribute("pactId", pactId);
		return "/component/pact/guarantee/MfGuaranteeRegistration_LiftInsert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String pactId,String from) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formguaranteeLiftDetail = formService.getFormData("guaranteeAddLiftDetail");
		getFormValue(formguaranteeLiftDetail);
		MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
		mfGuaranteeLift.setPactId(pactId);
		mfGuaranteeLift = mfGuaranteeLiftFeign.getById(mfGuaranteeLift);
		getObjValue(formguaranteeLiftDetail, mfGuaranteeLift);
		model.addAttribute("formguaranteeLiftDetail", formguaranteeLiftDetail);
		model.addAttribute("guaId", mfGuaranteeLift.getGuaId());
		model.addAttribute("pactId", pactId);
		model.addAttribute("id", mfGuaranteeLift.getId());
		model.addAttribute("query", "");
		model.addAttribute("queryFile", "query");
		model.addAttribute("liftType", mfGuaranteeLift.getLiftType());
		model.addAttribute("from", from);
		return "/component/pact/guarantee/MfGuaranteeLift_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formguaranteeLiftAdd = formService.getFormData("guaranteeLiftAdd");
		getFormValue(formguaranteeLiftAdd);
		boolean validateFlag = this.validateFormData(formguaranteeLiftAdd);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formguaranteeLiftAdd = formService.getFormData("guaranteeLiftAdd");
		getFormValue(formguaranteeLiftAdd);
		boolean validateFlag = this.validateFormData(formguaranteeLiftAdd);
	}

	/**
	 *
	 * 方法描述： 打开调薪调岗申请审批页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String id, String hideOpinionType, String taskId,
							   String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
		mfGuaranteeLift.setId(id);
		mfGuaranteeLift = mfGuaranteeLiftFeign.getById(mfGuaranteeLift);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formGuaranteeLiftApprove = formService.getFormData("GuaranteeLiftApprove");
		// 实体对象放到表单对象中
		getObjValue(formGuaranteeLiftApprove, mfGuaranteeLift);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formGuaranteeLiftApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("id", id);
		model.addAttribute("guaId", mfGuaranteeLift.getGuaId());
		model.addAttribute("pactId", mfGuaranteeLift.getPactId());
		model.addAttribute("mfGuaranteeLift", mfGuaranteeLift);
		model.addAttribute("formGuaranteeLiftApprove", formGuaranteeLiftApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/pact/guarantee/WkfGuaranteeLiftViewPoint";
	}

	/**
	 *
	 * 方法描述： 审批意见保存提交
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,
												String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		FormData formGuaranteeLiftApprove = formService.getFormData("GuaranteeLiftApprove");
		getFormValue(formGuaranteeLiftApprove, formDataMap);
		MfGuaranteeLift mfGuaranteeLift = new MfGuaranteeLift();
		setObjValue(formGuaranteeLiftApprove, mfGuaranteeLift);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfGuaranteeLift);
			formDataMap.put("mfGuaranteeLift", mfGuaranteeLift);
			res = mfGuaranteeLiftFeign.doCommit(taskId, transition, nextUser,
					formDataMap);
			dataMap = new HashMap<String, Object>();
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
		}
		return dataMap;
	}

}
