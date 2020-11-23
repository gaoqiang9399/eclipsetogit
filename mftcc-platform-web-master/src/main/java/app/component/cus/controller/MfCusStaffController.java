package  app.component.cus.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusStaff;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusStaffFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
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
 * Title: MfCusStaffAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 01 09:33:50 CST 2016
 **/
@Controller
@RequestMapping("/mfCusStaff")
public class MfCusStaffController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusStaffFeign mfCusStaffFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusStaff_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusStaff mfCusStaff = new MfCusStaff();
		try {
			mfCusStaff.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusStaff.setCriteriaList(mfCusStaff, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusStaff,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusStaff", mfCusStaff));
			ipage = mfCusStaffFeign.findByPage(ipage );
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
	public Map<String, Object> insertAjax(String ajaxData,String query) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcusStaffBase = formService.getFormData(formId);
			getFormValue(formcusStaffBase, getMapByJson(ajaxData));
			if(this.validateFormData(formcusStaffBase)){
				String relNo = (String)map.get("relNo");
				String cusNo = (String)map.get("cusNo");
				MfCusStaff mfCusStaff = new MfCusStaff();
				setObjValue(formcusStaffBase, mfCusStaff);
				mfCusStaff.setRelNo(relNo);
				List<MfCusStaff> list = mfCusStaffFeign.getByCusNoAndYear(mfCusStaff);
				//当前客户下年份只能允许添加一条
				if(null != list && list.size() >0){
					dataMap.put("flag", "error");
					dataMap.put("msg", "此年份的数据已经存在！");
					return dataMap;
				}
				mfCusStaff = mfCusStaffFeign.insert(mfCusStaff);
				mfCusStaff.setRelNo(relNo);
				mfCusStaff.setCusNo(cusNo);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusStaff.getCusNo(),
						mfCusStaff.getRelNo());// 更新资料完整度
				JsonTableUtil jtu = new JsonTableUtil();
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusStaff", mfCusStaff));
				String  tableHtml = jtu.getJsonStr("tablecusStaffList","tableTag", (List<MfCusStaff>)mfCusStaffFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");
				dataMap.put("infIntegrity",infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
	
	/**listShowDetailAjax
	 * 详情页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String surveyId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusStaffAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + cusType +
			// "的MfCusHighInfoAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			this.setIfBizManger("3");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formMfCusStaff0004 = formService.getFormData(formId);
			MfCusStaff MfCusStaff = new MfCusStaff();
			MfCusStaff.setSurveyId(surveyId);
			MfCusStaff = mfCusStaffFeign.getById(MfCusStaff);
			getObjValue(formMfCusStaff0004, MfCusStaff, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formMfCusStaff0004, "propertySeeTag", "");
			if (MfCusStaff != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", MfCusStaff);
		}
		return dataMap;
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
		MfCusStaff MfCusStaff = new MfCusStaff();
		String formId="";
		MfCusStaff.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusStaffAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单信息没有查询到");
		} else {
			FormData formcusstaffBase = formService.getFormData(formId);
			if (formcusstaffBase.getFormId() == null) {
//				logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusstaffBase);
				MfCusStaff.setCusName(mfCusCustomer.getCusName());
				MfCusStaff.setCusNo(cusNo);
				getObjValue(formcusstaffBase, MfCusStaff);
			}
			model.addAttribute("formcusStaffBase", formcusstaffBase);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusStaff_Insert";
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
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcusstaff00003 = formService.getFormData(formId);
		getFormValue(formcusstaff00003, getMapByJson(ajaxData));
		MfCusStaff mfCusStaffJsp = new MfCusStaff();
		setObjValue(formcusstaff00003, mfCusStaffJsp);
		MfCusStaff mfCusStaff = mfCusStaffFeign.getById(mfCusStaffJsp);
		if(mfCusStaff!=null){
			try{
				mfCusStaff = (MfCusStaff)EntityUtil.reflectionSetVal(mfCusStaff, mfCusStaffJsp, getMapByJson(ajaxData));
				mfCusStaffFeign.update(mfCusStaff);
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
		MfCusStaff mfCusStaff = new MfCusStaff();
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			FormData formcusstaff00002 = formService.getFormData(formId);
			getFormValue(formcusstaff00002, getMapByJson(ajaxData));
			if(this.validateFormData(formcusstaff00002)){
				setObjValue(formcusstaff00002, mfCusStaff);
				List<MfCusStaff> list = mfCusStaffFeign.getByCusNoAndYearExcludeSelf(mfCusStaff);
				//当前客户下年份只能允许添加一条
				if(null != list && list.size() >0){
					dataMap.put("flag", "error");
					dataMap.put("msg", "此年份的数据已经存在！");
					return dataMap;
				}
				mfCusStaffFeign.update(mfCusStaff);
				String cusNo = mfCusStaff.getCusNo();
				mfCusStaff.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusStaff", mfCusStaff));
				String tableHtml = jtu.getJsonStr("tablecusStaffList", "tableTag",
						(List<MfCusStaff>) mfCusStaffFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String cusNo,String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map map = getMapByJson(ajaxData);
		String formId = (String)map.get("formId");
		FormData formcusstaff00002 = formService.getFormData(formId);
		MfCusStaff mfCusStaff = new MfCusStaff();
		mfCusStaff.setCusNo(cusNo);
		mfCusStaff = mfCusStaffFeign.getById(mfCusStaff);
		getObjValue(formcusstaff00002, mfCusStaff,formData);
		if(mfCusStaff!=null){
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
	public Map<String, Object> deleteAjax(String cusNo,String surveyId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusStaff mfCusStaff = new MfCusStaff();
		mfCusStaff.setCusNo(cusNo);
		mfCusStaff.setSurveyId(surveyId);
		try {
			mfCusStaffFeign.delete(mfCusStaff);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
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
	public String input(Model model,String cusNo,String relNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		
		MfCusStaff mfCusStaff = new MfCusStaff();
		mfCusStaff.setCusNo(cusNo);
		String formId = "";
		//根据获取的客户号查询该客户数据
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		//表单基本信息
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusStaffAction");
		if(mfCusFormConfig == null){
			
		}else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData formcusStaffBase = formService.getFormData(formId);
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单信息没有查询到");
		} else {
			mfCusStaff.setCusName(mfCusCustomer.getCusName());
			mfCusStaff.setCusNo(cusNo);
			mfCusStaff.setRelNo(relNo);
			getObjValue(formcusStaffBase, mfCusStaff);
			model.addAttribute("formcusStaffBase", formcusStaffBase);
			model.addAttribute("query", "");
		}	
		return "/component/cus/MfCusStaff_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model,String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map map = getMapByJson(ajaxData);
		String formId = (String)map.get("formId");
		FormData  formcusstaffBase = formService.getFormData(formId);
		 getFormValue(formcusstaffBase);
		MfCusStaff  mfCusStaff = new MfCusStaff();
		 setObjValue(formcusstaffBase, mfCusStaff);
		 mfCusStaffFeign.insert(mfCusStaff);
		 getObjValue(formcusstaffBase, mfCusStaff);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage =this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusStaff", mfCusStaff));
		 List<MfCusStaff> mfCusStaffList = (List<MfCusStaff>)mfCusStaffFeign.findByPage(ipage).getResult();
		model.addAttribute("formcusstaffBase", formcusstaffBase);
		model.addAttribute("mfCusStaffList", mfCusStaffList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusStaff_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo,String surveyId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusStaff mfCusStaff = new MfCusStaff();
		FormData formcusStaffBase= null;
		mfCusStaff.setCusNo(cusNo);
		mfCusStaff.setSurveyId(surveyId);
		mfCusStaff = mfCusStaffFeign.getById(mfCusStaff);
		
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusStaffAction");
		String formId="";
		if(mfCusFormConfig == null){
			
		}else{
			if("1".equals(mfCusFormConfig.getShowType())){
				formId = mfCusFormConfig.getShowModelDef();
			}else{
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusStaffAction表单信息没有查询到");
		}else{
			formcusStaffBase = formService.getFormData(formId);
			if(formcusStaffBase.getFormId() == null){
				//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusStaffAction表单form"+formId+".xml文件不存在");
			}else{
				 getFormValue(formcusStaffBase);
				 getObjValue(formcusStaffBase, mfCusStaff);
			}
		}
		model.addAttribute("formcusStaffBase", formcusStaffBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusStaff_Edit";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfCusStaff mfCusStaff = new MfCusStaff();
		mfCusStaff.setCusNo(cusNo);
		mfCusStaffFeign.delete(mfCusStaff);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model,String ajaxData) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		 Map map = getMapByJson(ajaxData);
		 String formId = (String)map.get("formId");
		 FormData  formcusstaff00002 = formService.getFormData(formId);
		 getFormValue(formcusstaff00002);
		 boolean validateFlag = this.validateFormData(formcusstaff00002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model,String ajaxData) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		 Map map = getMapByJson(ajaxData);
		 String formId = (String)map.get("formId");
		 FormData  formcusstaff00002 = formService.getFormData(formId);
		 getFormValue(formcusstaff00002);
		 boolean validateFlag = this.validateFormData(formcusstaff00002);
	}
}
