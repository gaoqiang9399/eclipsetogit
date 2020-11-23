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
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusLogisticsInformation;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusLogisticsInformationFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

/**
 * Title: MfCusLogisticsInformationAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Apr 23 15:57:22 CST 2018
 **/
@Controller
@RequestMapping("/mfCusLogisticsInformation")
public class MfCusLogisticsInformationController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired
	private MfCusLogisticsInformationFeign mfCusLogisticsInformationFeign;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
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
		return "MfCusLogisticsInformation_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
		try {
			mfCusLogisticsInformation.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusLogisticsInformation.setCriteriaList(mfCusLogisticsInformation, ajaxData);//我的筛选
			//mfCusLogisticsInformation.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusLogisticsInformation,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusLogisticsInformation", mfCusLogisticsInformation));
			ipage = mfCusLogisticsInformationFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formmfcuslogisticsinformation0002 = formService.getFormData(formId);
			getFormValue(formmfcuslogisticsinformation0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmfcuslogisticsinformation0002)){
				String relNo = (String)map.get("relNo");
				MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
				setObjValue(formmfcuslogisticsinformation0002, mfCusLogisticsInformation);
				mfCusLogisticsInformationFeign.insert(mfCusLogisticsInformation);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusLogisticsInformation.getCusNo(),
						mfCusLogisticsInformation.getRelNo());// 更新资料完整度
				JsonTableUtil jtu = new JsonTableUtil();
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusLogisticsInformation", mfCusLogisticsInformation));
				String  tableHtml = jtu.getJsonStr("tableCusLogisticsInformationList","tableTag", (List<MfCusLogisticsInformation>)mfCusLogisticsInformationFeign.findByPage(ipage).getResult(), null,true);
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
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
		FormData formmfcuslogisticsinformation0002 = formService.getFormData(formId);
		getFormValue(formmfcuslogisticsinformation0002, getMapByJson(ajaxData));
		MfCusLogisticsInformation mfCusLogisticsInformationJsp = new MfCusLogisticsInformation();
		setObjValue(formmfcuslogisticsinformation0002, mfCusLogisticsInformationJsp);
		mfCusLogisticsInformation = mfCusLogisticsInformationFeign.getById(mfCusLogisticsInformationJsp);
		if(mfCusLogisticsInformation!=null){
			try{
				mfCusLogisticsInformation = (MfCusLogisticsInformation)EntityUtil.reflectionSetVal(mfCusLogisticsInformation, mfCusLogisticsInformationJsp, getMapByJson(ajaxData));
				mfCusLogisticsInformationFeign.update(mfCusLogisticsInformation);
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formmfcuslogisticsinformation0002 = formService.getFormData(formId);
			getFormValue(formmfcuslogisticsinformation0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmfcuslogisticsinformation0002)){
				mfCusLogisticsInformation = new MfCusLogisticsInformation();
				setObjValue(formmfcuslogisticsinformation0002, mfCusLogisticsInformation);
				mfCusLogisticsInformationFeign.update(mfCusLogisticsInformation);
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
	public Map<String, Object> getByIdAjax(String surveyId,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = map.get("formId").toString();
		FormData formmfcuslogisticsinformation0002 = formService.getFormData(formId);
		MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
		mfCusLogisticsInformation.setSurveyId(surveyId);
		mfCusLogisticsInformation = mfCusLogisticsInformationFeign.getById(mfCusLogisticsInformation);
		getObjValue(formmfcuslogisticsinformation0002, mfCusLogisticsInformation,formData);
		if(mfCusLogisticsInformation!=null){
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
	public Map<String, Object> deleteAjax(String surveyId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
		mfCusLogisticsInformation.setSurveyId(surveyId);
		try {
			mfCusLogisticsInformationFeign.delete(mfCusLogisticsInformation);
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
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo,String relNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();

		MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
		mfCusLogisticsInformation.setCusNo(cusNo);
		String formId = "";
		//根据获取的客户号查询该客户数据
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		//表单基本信息
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusLogisticsInformationAction");
		if(mfCusFormConfig == null){
					
		}else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData formcusLogisticsInformationBase = formService.getFormData(formId);
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单信息没有查询到");
		} else {
			FormData formcuslogisticsinformationBase = formService.getFormData(formId);
		if (formcuslogisticsinformationBase.getFormId() == null) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单form" + formId
//			+ ".xml文件不存在");
		} else {
		getFormValue(formcuslogisticsinformationBase);
		mfCusLogisticsInformation.setCusName(mfCusCustomer.getCusName());
		mfCusLogisticsInformation.setCusNo(cusNo);
		mfCusLogisticsInformation.setRelNo(relNo);
		mfCusLogisticsInformation.setOpName(User.getRegName(request));
		mfCusLogisticsInformation.setOpNo(User.getRegNo(request));
		getObjValue(formcuslogisticsinformationBase, mfCusLogisticsInformation);
		getObjValue(formcusLogisticsInformationBase, mfCusLogisticsInformation);
		};
		model.addAttribute("formcusLogisticsInformationBase", formcusLogisticsInformationBase);
		model.addAttribute("query", "");
		}	
		return "/component/cus/MfCusLogisticsInformation_Insert";
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusType = "";
		request.setAttribute("ifBizManger", "3");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusLogisticsInformationAction");
		String formId = "";
		formId = mfCusFormConfig.getShowModelDef();
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + cusType +
			// "的MfCusHighInfoAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formmfcusLogisticsInformation0004 = formService.getFormData(formId);
			MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
			mfCusLogisticsInformation.setSurveyId(surveyId);
			mfCusLogisticsInformation = mfCusLogisticsInformationFeign.getById(mfCusLogisticsInformation);
			getObjValue(formmfcusLogisticsInformation0004, mfCusLogisticsInformation, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formmfcusLogisticsInformation0004, "propertySeeTag", "");
			if (mfCusLogisticsInformation != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusLogisticsInformation);
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
		MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
		String formId="";
		mfCusLogisticsInformation.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "mfCusLogisticsInformationAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单信息没有查询到");
		} else {
			FormData formcuslogisticsinformationBase = formService.getFormData(formId);
			if (formcuslogisticsinformationBase.getFormId() == null) {
//				logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcuslogisticsinformationBase);
				mfCusLogisticsInformation.setCusName(mfCusCustomer.getCusName());
				mfCusLogisticsInformation.setCusNo(cusNo);
				mfCusLogisticsInformation.setOpName(User.getRegName(request));
				mfCusLogisticsInformation.setOpNo(User.getRegNo(request));
				getObjValue(formcuslogisticsinformationBase, mfCusLogisticsInformation);
			}
			model.addAttribute("formcusLogisticsInformationBase", formcuslogisticsinformationBase);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusLogisticsInformation_Insert";
	}
	
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String surveyId,Model model,String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = map.get("formId").toString();
		FormData formcusLogisticsInformationBase = formService.getFormData(formId);
		getFormValue(formcusLogisticsInformationBase);
		MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
		mfCusLogisticsInformation.setSurveyId(surveyId);
		mfCusLogisticsInformation = mfCusLogisticsInformationFeign.getById(mfCusLogisticsInformation);
		getObjValue(formcusLogisticsInformationBase, mfCusLogisticsInformation);
		model.addAttribute("formcusLogisticsInformationBase", formcusLogisticsInformationBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusLogisticsInformation_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = map.get("formId").toString();
		FormData formmfcuslogisticsinformation0002 = formService.getFormData(formId);
		getFormValue(formmfcuslogisticsinformation0002);
		boolean validateFlag = this.validateFormData(formmfcuslogisticsinformation0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = map.get("formId").toString();
		FormData formmfcuslogisticsinformation0002 = formService.getFormData(ajaxData);
		getFormValue(formmfcuslogisticsinformation0002);
		boolean validateFlag = this.validateFormData(formmfcuslogisticsinformation0002);
	}
}
