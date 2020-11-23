package  app.component.cus.controller;
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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusAdministrativePenalty;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusAdministrativePenaltyFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

/**
 * Title: MfCusAdministrativePenaltyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Apr 23 16:01:02 CST 2018
 **/
@Controller
@RequestMapping("/mfCusAdministrativePenalty")
public class MfCusAdministrativePenaltyController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCusAdministrativePenaltyFeign mfCusAdministrativePenaltyFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "MfCusAdministrativePenalty_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		dataMap = new HashMap<String, Object>(); 
		MfCusAdministrativePenalty mfCusAdministrativePenalty = new MfCusAdministrativePenalty();
		try {
			mfCusAdministrativePenalty.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusAdministrativePenalty.setCriteriaList(mfCusAdministrativePenalty, ajaxData);//我的筛选
			//mfCusAdministrativePenalty.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusAdministrativePenalty,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfCusAdministrativePenaltyFeign.findByPage(ipage, mfCusAdministrativePenalty);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		try{
			FormData formmfcusadministrativepenalty0001 = formService.getFormData("mfcusadministrativepenalty0002");
			getFormValue(formmfcusadministrativepenalty0001, getMapByJson(ajaxData));
			if(this.validateFormData(formmfcusadministrativepenalty0001)){
				MfCusAdministrativePenalty mfCusAdministrativePenalty = new MfCusAdministrativePenalty();
				setObjValue(formmfcusadministrativepenalty0001, mfCusAdministrativePenalty);
				mfCusAdministrativePenaltyFeign.insert(mfCusAdministrativePenalty);
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
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>();
		MfCusAdministrativePenalty mfCusAdministrativePenalty = new MfCusAdministrativePenalty();
		FormData formmfcusadministrativepenalty0002 = formService.getFormData("mfcusadministrativepenalty0002");
		getFormValue(formmfcusadministrativepenalty0002, getMapByJson(ajaxData));
		MfCusAdministrativePenalty mfCusAdministrativePenaltyJsp = new MfCusAdministrativePenalty();
		setObjValue(formmfcusadministrativepenalty0002, mfCusAdministrativePenaltyJsp);
		mfCusAdministrativePenalty = mfCusAdministrativePenaltyFeign.getById(mfCusAdministrativePenaltyJsp);
		if(mfCusAdministrativePenalty!=null){
			try{
				mfCusAdministrativePenalty = (MfCusAdministrativePenalty)EntityUtil.reflectionSetVal(mfCusAdministrativePenalty, mfCusAdministrativePenaltyJsp, getMapByJson(ajaxData));
				mfCusAdministrativePenaltyFeign.update(mfCusAdministrativePenalty);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		MfCusAdministrativePenalty mfCusAdministrativePenalty = new MfCusAdministrativePenalty();
		try{
			FormData formmfcusadministrativepenalty0002 = formService.getFormData("mfcusadministrativepenalty0002");
			getFormValue(formmfcusadministrativepenalty0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmfcusadministrativepenalty0002)){
				mfCusAdministrativePenalty = new MfCusAdministrativePenalty();
				setObjValue(formmfcusadministrativepenalty0002, mfCusAdministrativePenalty);
				mfCusAdministrativePenaltyFeign.update(mfCusAdministrativePenalty);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String surveyId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		Map<String,Object> formData = new HashMap<String,Object>();
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		FormData formmfcusadministrativepenalty0002 = formService.getFormData("mfcusadministrativepenalty0002");
		MfCusAdministrativePenalty mfCusAdministrativePenalty = new MfCusAdministrativePenalty();
		mfCusAdministrativePenalty.setSurveyId(surveyId);
		mfCusAdministrativePenalty = mfCusAdministrativePenaltyFeign.getById(mfCusAdministrativePenalty);
		getObjValue(formmfcusadministrativepenalty0002, mfCusAdministrativePenalty,formData);
		if(mfCusAdministrativePenalty!=null){
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String surveyId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		dataMap = new HashMap<String, Object>(); 
		MfCusAdministrativePenalty mfCusAdministrativePenalty = new MfCusAdministrativePenalty();
		mfCusAdministrativePenalty.setSurveyId(surveyId);
		try {
			mfCusAdministrativePenaltyFeign.delete(mfCusAdministrativePenalty);
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
	public String input(Model model,String cusNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfcusadministrativepenalty0001 = formService.getFormData("mfcusadministrativepenalty0001");
		MfCusAdministrativePenalty mfCusAdministrativePenalty = new MfCusAdministrativePenalty();
		mfCusAdministrativePenalty.setCusNo(cusNo);
		String formId="";
		//根据获取的客户号查询该客户数据
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		//表单基本信息
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"mfCusAdministrativePenaltyAction");
		if(mfCusFormConfig == null){
			
		}else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单信息没有查询到");
		} else {
			FormData formcusAssOutsideBase = formService.getFormData(formId);
			if (formcusAssOutsideBase.getFormId() == null) {
//				logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单form" + formId
//						+ ".xml文件不存在");
			} else {
				getFormValue(formcusAssOutsideBase);
				mfCusAdministrativePenalty.setCusName(mfCusCustomer.getCusName());
				mfCusAdministrativePenalty.setCusNo(cusNo);
				mfCusAdministrativePenalty.setOpName(User.getRegName(request));
				mfCusAdministrativePenalty.setOpNo(User.getRegNo(request));
				getObjValue(formcusAssOutsideBase, mfCusAdministrativePenalty);
			};
		model.addAttribute("formmfcusadministrativepenalty0001", formmfcusadministrativepenalty0001);
		model.addAttribute("query", "");
		}
		return "/component/cus/MfCusAdministrativePenalty_Insert";
	}
	
	/**inputBiz
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model,String cusNo,String relNo,String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusAdministrativePenalty mfcusadministrativepenalty = new MfCusAdministrativePenalty();
		String formId="";
		mfcusadministrativepenalty.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusAssureOutsideAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单信息没有查询到");
		} else {
			FormData formmfcusadministrativepenalty = formService.getFormData(formId);
			if (formmfcusadministrativepenalty.getFormId() == null) {
//				logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formmfcusadministrativepenalty);
				mfcusadministrativepenalty.setCusName(mfCusCustomer.getCusName());
				mfcusadministrativepenalty.setCusNo(cusNo);
				mfcusadministrativepenalty.setOpName(User.getRegName(request));
				mfcusadministrativepenalty.setOpNo(User.getRegNo(request));
				getObjValue(formmfcusadministrativepenalty, mfcusadministrativepenalty);
			}
			model.addAttribute("formcusAssOutsideBase", formmfcusadministrativepenalty);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusBranchOrganization_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String surveyId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfcusadministrativepenalty0001 = formService.getFormData("mfcusadministrativepenalty0001");
		 getFormValue(formmfcusadministrativepenalty0001);
		 MfCusAdministrativePenalty mfCusAdministrativePenalty = new MfCusAdministrativePenalty();
		mfCusAdministrativePenalty.setSurveyId(surveyId);
		 mfCusAdministrativePenalty = mfCusAdministrativePenaltyFeign.getById(mfCusAdministrativePenalty);
		 getObjValue(formmfcusadministrativepenalty0001, mfCusAdministrativePenalty);
		 model.addAttribute("formmfcusadministrativepenalty0001",formmfcusadministrativepenalty0001);
		 model.addAttribute("query","");
		return "/component/cus/MfCusAdministrativePenalty_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formmfcusadministrativepenalty0002 = formService.getFormData("mfcusadministrativepenalty0002");
		 getFormValue(formmfcusadministrativepenalty0002);
		 boolean validateFlag = this.validateFormData(formmfcusadministrativepenalty0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formmfcusadministrativepenalty0002 = formService.getFormData("mfcusadministrativepenalty0002");
		 getFormValue(formmfcusadministrativepenalty0002);
		 boolean validateFlag = this.validateFormData(formmfcusadministrativepenalty0002);
	}
	
	
	
}
