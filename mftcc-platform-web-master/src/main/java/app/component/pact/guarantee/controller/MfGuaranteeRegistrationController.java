package  app.component.pact.guarantee.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.DateUtil;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfBusPact;
import app.component.pact.guarantee.feign.MfGuaranteeRegistrationFeign;
import app.component.pact.guarantee.entity.MfGuaranteeRegistration;
import app.component.pactinterface.PactInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: MfGuaranteeRegistrationController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 20 15:54:00 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfGuaranteeRegistration")
public class MfGuaranteeRegistrationController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfGuaranteeRegistrationFeign mfGuaranteeRegistrationFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/guarantee/MfGuaranteeRegistration_List";
	}
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReceivedListPage")
	public String getReceivedListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/guarantee/MfGuaranteeRegistration_ReceivedList";
	}
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMaintainInfoListPage")
	public String getMaintainInfoListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/guarantee/MfGuaranteeRegistration_MaintainInfoList";
	}
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLiftListPage")
	public String getLiftListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/guarantee/MfGuaranteeRegistration_LiftList";
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
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		try {
			mfGuaranteeRegistration.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfGuaranteeRegistration.setCriteriaList(mfGuaranteeRegistration, ajaxData);//我的筛选
			mfGuaranteeRegistration.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfGuaranteeRegistration,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfGuaranteeRegistration", mfGuaranteeRegistration));
			//自定义查询Feign方法
			ipage = mfGuaranteeRegistrationFeign.findByPage(ipage);
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
	@RequestMapping(value = "/findReceivedByPageAjax")
	public Map<String, Object> findReceivedByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		try {
			mfGuaranteeRegistration.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfGuaranteeRegistration.setCriteriaList(mfGuaranteeRegistration, ajaxData);//我的筛选
			mfGuaranteeRegistration.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfGuaranteeRegistration,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfGuaranteeRegistration", mfGuaranteeRegistration));
			//自定义查询Feign方法
			ipage = mfGuaranteeRegistrationFeign.findReceivedByPage(ipage);
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
	@RequestMapping(value = "/findMaintainInfoByPageAjax")
	public Map<String, Object> findMaintainInfoByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		try {
			mfGuaranteeRegistration.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfGuaranteeRegistration.setCriteriaList(mfGuaranteeRegistration, ajaxData);//我的筛选
			mfGuaranteeRegistration.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfGuaranteeRegistration,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfGuaranteeRegistration", mfGuaranteeRegistration));
			//自定义查询Feign方法
			ipage = mfGuaranteeRegistrationFeign.findMaintainInfoByPage(ipage);
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
	@RequestMapping(value = "/findLiftByPageAjax")
	public Map<String, Object> findLiftByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		try {
			mfGuaranteeRegistration.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfGuaranteeRegistration.setCriteriaList(mfGuaranteeRegistration, ajaxData);//我的筛选
			mfGuaranteeRegistration.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfGuaranteeRegistration,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfGuaranteeRegistration", mfGuaranteeRegistration));
			//自定义查询Feign方法
			ipage = mfGuaranteeRegistrationFeign.findLiftByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData,String flag,String ifSave) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String strName = "保函登记";
		try{
			FormData formguaranteeAdd = formService.getFormData("guaranteeAdd");
			getFormValue(formguaranteeAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formguaranteeAdd)){
				MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
				setObjValue(formguaranteeAdd, mfGuaranteeRegistration);
				if("insert".equals(flag)){
					if(BizPubParm.YES_NO_Y.equals(ifSave)){
						strName = "保存";
						mfGuaranteeRegistration.setGuaSts("0");
					}else{
						mfGuaranteeRegistration.setGuaSts("1");
					}
					MfGuaranteeRegistration mfGuaranteeRegistration1 = new MfGuaranteeRegistration();
					mfGuaranteeRegistration1.setAppId(mfGuaranteeRegistration.getAppId());
					mfGuaranteeRegistration1 = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration1);
					if(mfGuaranteeRegistration1 == null){
						mfGuaranteeRegistrationFeign.insert(mfGuaranteeRegistration);
					}else{
						mfGuaranteeRegistrationFeign.update(mfGuaranteeRegistration);
					}
				}else if("edit".equals(flag)){
					mfGuaranteeRegistrationFeign.update(mfGuaranteeRegistration);
					strName = "编辑";
				}else if("update".equals(flag)){
					String courierNo  = mfGuaranteeRegistration.getCourierNo();
					if(StringUtil.isEmpty(courierNo)){
						dataMap.put("flag", "error");
						dataMap.put("msg","快递单号不能为空！");
						return dataMap;
					}
					Double guaFee  = mfGuaranteeRegistration.getGuaFee();
					if(guaFee == null || guaFee == 0.00){
						dataMap.put("flag", "error");
						dataMap.put("msg","公司实际保费不能为空！");
						return dataMap;
					}
					Double handFee  = mfGuaranteeRegistration.getHandFee();
					if(handFee == null || handFee == 0.00){
						dataMap.put("flag", "error");
						dataMap.put("msg","实际银行手续费不能为空！");
						return dataMap;
					}
					mfGuaranteeRegistration.setGuaSts("2");
					mfGuaranteeRegistrationFeign.update(mfGuaranteeRegistration);
					strName = "保函收妥确认";
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg","未识别标识flag=" + flag);
				}
				dataMap.put("msg", strName + "成功");
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", strName + "失败");
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
		FormData formguaranteeAdd = formService.getFormData("guaranteeAdd");
		getFormValue(formguaranteeAdd, getMapByJson(ajaxData));
		MfGuaranteeRegistration mfGuaranteeRegistrationJsp = new MfGuaranteeRegistration();
		setObjValue(formguaranteeAdd, mfGuaranteeRegistrationJsp);
		MfGuaranteeRegistration mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistrationJsp);
		if(mfGuaranteeRegistration!=null){
			try{
				mfGuaranteeRegistration = (MfGuaranteeRegistration)EntityUtil.reflectionSetVal(mfGuaranteeRegistration, mfGuaranteeRegistrationJsp, getMapByJson(ajaxData));
				mfGuaranteeRegistrationFeign.update(mfGuaranteeRegistration);
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
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		try{
			FormData formguaranteeAdd = formService.getFormData("guaranteeAdd");
			getFormValue(formguaranteeAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formguaranteeAdd)){
				mfGuaranteeRegistration = new MfGuaranteeRegistration();
				setObjValue(formguaranteeAdd, mfGuaranteeRegistration);
				mfGuaranteeRegistrationFeign.update(mfGuaranteeRegistration);
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
		FormData formguaranteeAdd = formService.getFormData("guaranteeAdd");
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		mfGuaranteeRegistration.setId(id);
		mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
		getObjValue(formguaranteeAdd, mfGuaranteeRegistration,formData);
		if(mfGuaranteeRegistration!=null){
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
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		mfGuaranteeRegistration.setId(id);
		try {
			mfGuaranteeRegistrationFeign.delete(mfGuaranteeRegistration);
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
	@RequestMapping("/input")
	public String input(Model model,String pactId,String flag) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		String queryFile = "";
		String formId = "guaranteeAdd";
		String id = WaterIdUtil.getWaterId();
		if("insert".equals(flag)){
			mfGuaranteeRegistration.setPactId(pactId);
			mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
			if(mfGuaranteeRegistration == null){
				MfBusPact mfBusPact = new MfBusPact();
				mfBusPact.setPactId(pactId);
				mfBusPact = pactInterfaceFeign.getById(mfBusPact);
				mfGuaranteeRegistration = new MfGuaranteeRegistration();
				PropertyUtils.copyProperties(mfGuaranteeRegistration,mfBusPact);
				mfGuaranteeRegistration.setRegDate(DateUtil.getDate());
				mfGuaranteeRegistration.setOpName(User.getRegName(request));
				mfGuaranteeRegistration.setId(id);
			}else{
				id = mfGuaranteeRegistration.getId();
				mfGuaranteeRegistration.setRegDate(DateUtil.getDate());
				mfGuaranteeRegistration.setOpName(User.getRegName(request));
			}
		}else{
			formId = "guaranteeAddUpdate";
			mfGuaranteeRegistration.setPactId(pactId);
			mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
			if("detail".equals(flag)){
				formId = "guaranteeAddShow";
				queryFile = "query";
			}if("edit".equals(flag)) {
				formId = "guaranteeAddEdit";
			}
			id = mfGuaranteeRegistration.getId();
		}
		FormData formguaranteeAdd = formService.getFormData(formId);
		getObjValue(formguaranteeAdd, mfGuaranteeRegistration);
		model.addAttribute("formguaranteeAdd", formguaranteeAdd);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", mfGuaranteeRegistration.getCusNo());
		model.addAttribute("queryFile", queryFile);
		model.addAttribute("id", id);
		model.addAttribute("pactId", pactId);
		model.addAttribute("flag", flag);
		return "/component/pact/guarantee/MfGuaranteeRegistration_Insert";
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputMainTainInfo")
	public String inputMainTainInfo(Model model,String pactId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		String queryFile = "";
		String formId = "guaranteeAddUpdateMaintainInfo";
		mfGuaranteeRegistration.setPactId(pactId);
		mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
		String id = mfGuaranteeRegistration.getId();
		FormData formguaranteeAdd = formService.getFormData(formId);
		getObjValue(formguaranteeAdd, mfGuaranteeRegistration);
		model.addAttribute("formguaranteeAdd", formguaranteeAdd);
		model.addAttribute("query", "");
		model.addAttribute("queryFile", queryFile);
		model.addAttribute("id", id);
		model.addAttribute("pactId", pactId);
		return "/component/pact/guarantee/MfGuaranteeRegistration_MaintainInfoInsert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String pactId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formguaranteeDetail = formService.getFormData("guaranteeDetail");
		getFormValue(formguaranteeDetail);
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		mfGuaranteeRegistration.setPactId(pactId);
		mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
		getObjValue(formguaranteeDetail, mfGuaranteeRegistration);
		model.addAttribute("formguaranteeDetail", formguaranteeDetail);
		model.addAttribute("pactId", pactId);
		model.addAttribute("query", "query");
		return "/component/pact/guarantee/MfGuaranteeRegistration_Detail";
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
		FormData formguaranteeAdd = formService.getFormData("guaranteeAdd");
		getFormValue(formguaranteeAdd);
		boolean validateFlag = this.validateFormData(formguaranteeAdd);
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
		FormData formguaranteeAdd = formService.getFormData("guaranteeAdd");
		getFormValue(formguaranteeAdd);
		boolean validateFlag = this.validateFormData(formguaranteeAdd);
	}
}
