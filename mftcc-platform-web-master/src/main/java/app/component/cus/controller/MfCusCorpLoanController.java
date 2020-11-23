package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCorpLoan;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCorpLoanFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusCorpLoanAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 13 17:44:07 CST 2018
 **/
@Controller
@RequestMapping("/mfCusCorpLoan")
public class MfCusCorpLoanController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入MfCusCorpLoanBo
	@Autowired
	private MfCusCorpLoanFeign mfCusCorpLoanFeign;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "/component/cus/MfCusCorpLoan_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,Integer pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
		try {
			mfCusCorpLoan.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCorpLoan.setCriteriaList(mfCusCorpLoan, ajaxData);//我的筛选
			//mfCusCorpLoan.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusCorpLoan,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCorpLoan", mfCusCorpLoan));
			ipage = mfCusCorpLoanFeign.findByPage(ipage);
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
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormService formService = new FormService();
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcusCorpLoanDetail = formService.getFormData(formId);
			getFormValue(formcusCorpLoanDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusCorpLoanDetail)){
				MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
				setObjValue(formcusCorpLoanDetail, mfCusCorpLoan);
				if(StringUtil.isNotEmpty(mfCusCorpLoan.getCusNo())){
					mfCusCorpLoan.setRelNo(mfCusCorpLoan.getCusNo());	
				}
				mfCusCorpLoanFeign.insert(mfCusCorpLoan);
				
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusCorpLoan.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "tablecusCorpLoanListBase";
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpLoanAction");				
				if(mfCusFormConfig != null){
					if(StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())){
						tableId = "table" + mfCusFormConfig.getListModelDef();
					}
				}
				
				String cusNo = mfCusCorpLoan.getCusNo();
				
				String relNo = mfCusCorpLoan.getRelNo();
				if(StringUtil.isEmpty(relNo)){
					relNo=cusNo;
				}
				mfCusCorpLoan = new MfCusCorpLoan();
				mfCusCorpLoan.setCusNo(cusNo);
				mfCusCorpLoan.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusCorpLoan", mfCusCorpLoan));
				JsonTableUtil jtu = new JsonTableUtil();
				List<MfCusCorpLoan> list=(List<MfCusCorpLoan>)mfCusCorpLoanFeign.findByPage(ipage).getResult();
				String  tableHtml = jtu.getJsonStr(tableId,"tableTag", list, null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");
				
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
	
	
	//列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String cusNo,String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap=new HashMap<String,Object>();
		String cusType="";
		String formId="";
		String query="";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType=mfCusCustomer.getCusType();
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusCorpLoanAction");
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getShowModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//				logger.error("客户类型为"+cusType+"的MfCusBankAccManageAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}else{
			Map<String,Object> formData = new HashMap<String,Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusbank00002 = formService.getFormData(formId);
			MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
			mfCusCorpLoan.setId(id);
			mfCusCorpLoan = mfCusCorpLoanFeign.getById(mfCusCorpLoan);
			getObjValue(formcusbank00002, mfCusCorpLoan,formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusbank00002,"propertySeeTag",query);
			if(mfCusCorpLoan!=null){
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusCorpLoan);
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String,Object> updateByOneAjax(String formId,String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusbank00002 = formService.getFormData(formId);
		getFormValue(formcusbank00002, getMapByJson(ajaxData));
		MfCusCorpLoan mfCusCorpLoanNew = new MfCusCorpLoan();
		setObjValue(formcusbank00002, mfCusCorpLoanNew);
		mfCusCorpLoan.setId(mfCusCorpLoanNew.getId());
		mfCusCorpLoan = mfCusCorpLoanFeign.getById(mfCusCorpLoan);
		if(mfCusCorpLoan!=null){
			try{
				mfCusCorpLoan = (MfCusCorpLoan)EntityUtil.reflectionSetVal(mfCusCorpLoan, mfCusCorpLoanNew, getMapByJson(ajaxData));
				mfCusCorpLoanFeign.update(mfCusCorpLoan);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		FormService formService =null;
		FormData formcusCorpLoanDetail = formService.getFormData("cusCorpLoanDetail");
		getFormValue(formcusCorpLoanDetail, getMapByJson(ajaxData));
		MfCusCorpLoan mfCusCorpLoanJsp = new MfCusCorpLoan();
		setObjValue(formcusCorpLoanDetail, mfCusCorpLoanJsp);
		MfCusCorpLoan mfCusCorpLoan = mfCusCorpLoanFeign.getById(mfCusCorpLoanJsp);
		if(mfCusCorpLoan!=null){
			try{
				mfCusCorpLoan = (MfCusCorpLoan)EntityUtil.reflectionSetVal(mfCusCorpLoan, mfCusCorpLoanJsp, getMapByJson(ajaxData));
				mfCusCorpLoanFeign.update(mfCusCorpLoan);
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
		try{
			FormService formService = new FormService();
			FormData formcusCorpLoanDetail = formService.getFormData("cusCorpLoanDetail");
			getFormValue(formcusCorpLoanDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusCorpLoanDetail)){
				mfCusCorpLoan = new MfCusCorpLoan();
				setObjValue(formcusCorpLoanDetail, mfCusCorpLoan);
				mfCusCorpLoanFeign.update(mfCusCorpLoan);
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
	public Map<String,Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formcusCorpLoanDetail = formService.getFormData("cusCorpLoanDetail");
		MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
		mfCusCorpLoan.setId(id);
		mfCusCorpLoan = mfCusCorpLoanFeign.getById(mfCusCorpLoan);
		getObjValue(formcusCorpLoanDetail, mfCusCorpLoan,formData);
		if(mfCusCorpLoan!=null){
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
	public Map<String,Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
		mfCusCorpLoan.setId(id);
		try {
			mfCusCorpLoanFeign.delete(mfCusCorpLoan);
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
	public String input(Model model,String cusNo,String relNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		String formId = "";
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusCorpLoanAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formcusCorpLoanDetail = formService.getFormData(formId);
			if (formcusCorpLoanDetail.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else {
				getFormValue(formcusCorpLoanDetail);
				mfCusCorpLoan.setCusName(mfCusCustomer.getCusName());
				mfCusCorpLoan.setCusNo(cusNo);
				
				getObjValue(formcusCorpLoanDetail, mfCusCorpLoan);
				model.addAttribute("formcusCorpLoanBase", formcusCorpLoanDetail);
			}
		}
		
		model.addAttribute("query","");
		return "/component/cus/MfCusCorpLoan_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formcusCorpLoanBase = formService.getFormData("cusCorpLoanBase");
		 getFormValue(formcusCorpLoanBase);
		 MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
		mfCusCorpLoan.setId(id);
		 mfCusCorpLoan = mfCusCorpLoanFeign.getById(mfCusCorpLoan);
		 getObjValue(formcusCorpLoanBase, mfCusCorpLoan);
		 model.addAttribute("formcusCorpLoanBase",formcusCorpLoanBase);
		 model.addAttribute("query","");
		return "/component/cus/MfCusCorpLoan_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcusCorpLoanDetail = formService.getFormData("cusCorpLoanDetail");
		 getFormValue(formcusCorpLoanDetail);
		 boolean validateFlag = this.validateFormData(formcusCorpLoanDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcusCorpLoanDetail = formService.getFormData("cusCorpLoanDetail");
		 getFormValue(formcusCorpLoanDetail);
		 boolean validateFlag = this.validateFormData(formcusCorpLoanDetail);
	}
	
	
}
