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

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusApplySpouseSurvey;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusApplySpouseSurveyFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFamilyInfoFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusApplySpouseSurveyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Nov 28 09:34:32 CST 2017
 **/
@Controller
@RequestMapping("/mfCusApplySpouseSurvey")
public class MfCusApplySpouseSurveyController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired  
	private MfCusApplySpouseSurveyFeign mfCusApplySpouseSurveyFeign;
	@Autowired  
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired  
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired  
	private MfCusFamilyInfoFeign mfCusFamilyInfoFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/cus/MfCusApplySpouseSurvey_List";
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
		MfCusApplySpouseSurvey mfCusApplySpouseSurvey = new MfCusApplySpouseSurvey();
		try {
			mfCusApplySpouseSurvey.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusApplySpouseSurvey.setCriteriaList(mfCusApplySpouseSurvey, ajaxData);//我的筛选
			//mfCusApplySpouseSurvey.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusApplySpouseSurvey,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusApplySpouseSurvey", mfCusApplySpouseSurvey));
			ipage = mfCusApplySpouseSurveyFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
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
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		String formId="";
		String query="";
		Map<String , Object> map = getMapByJson(ajaxData);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo((String)map.get("cusNo"));
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusApplySpouseSurveyAction");
		if(mfCusFormConfig != null){
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusApplySpouseSurveyAction表单信息没有查询到");
		}else{
			try{
				FormData formcusApplySpouseSurveyBase = formService.getFormData(formId);
				getFormValue(formcusApplySpouseSurveyBase, getMapByJson(ajaxData));
				if(this.validateFormData(formcusApplySpouseSurveyBase)){
					MfCusApplySpouseSurvey mfCusApplySpouseSurvey = new MfCusApplySpouseSurvey();
					setObjValue(formcusApplySpouseSurveyBase, mfCusApplySpouseSurvey);
					mfCusApplySpouseSurveyFeign.insert(mfCusApplySpouseSurvey);
					String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusApplySpouseSurvey.getCusNo(), mfCusApplySpouseSurvey.getRelNo());//更新资料完整度

					String detailFormId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusApplySpouseSurveyAction").getShowModelDef();
					if(StringUtil.isEmpty(detailFormId)){
//						log.error("MfCusApplySpouseSurveyAction的detailFormId找不到");
					}
					FormData formcusApplySpouseSurveyDetail = formService.getFormData(detailFormId);
					getObjValue(formcusApplySpouseSurveyDetail, mfCusApplySpouseSurvey);
					this.getHttpRequest().setAttribute("ifBizManger","3");
					JsonFormUtil jsonFormUtil = new JsonFormUtil();
					String htmlStr = jsonFormUtil.getJsonStr(formcusApplySpouseSurveyDetail,"propertySeeTag",query);
					
					dataMap.put("mfCusCustomer", mfCusCustomer);
					dataMap.put("htmlStr", htmlStr);
					dataMap.put("htmlStrFlag","1");
					dataMap.put("infIntegrity",infIntegrity);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					
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
	public Map<String,Object> updateAjaxByOne(String formId,String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		//这里得到的formId是带form字符串的，比如formcuscorp0001
		if(StringUtil.isEmpty(formId)){
			formId = mfCusFormConfigFeign.getByCusType("base", "MfCusApplySpouseSurveyAction").getShowModel();
		}else{
			if(formId.indexOf("form") == -1){
			}else{
				formId = formId.substring(4);
			}
		}
		FormData formcusApplySpouseSurveyDetail = formService.getFormData(formId);
		getFormValue(formcusApplySpouseSurveyDetail, getMapByJson(ajaxData));
		MfCusApplySpouseSurvey mfCusApplySpouseSurveyJsp = new MfCusApplySpouseSurvey();
		setObjValue(formcusApplySpouseSurveyDetail, mfCusApplySpouseSurveyJsp);
		MfCusApplySpouseSurvey mfCusApplySpouseSurvey = mfCusApplySpouseSurveyFeign.getById(mfCusApplySpouseSurveyJsp);
		if(mfCusApplySpouseSurvey!=null){
			try{
				mfCusApplySpouseSurvey = (MfCusApplySpouseSurvey)EntityUtil.reflectionSetVal(mfCusApplySpouseSurvey, mfCusApplySpouseSurveyJsp, getMapByJson(ajaxData));
				mfCusApplySpouseSurveyFeign.update(mfCusApplySpouseSurvey);
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusApplySpouseSurvey mfCusApplySpouseSurvey = new MfCusApplySpouseSurvey();
		try{
			FormData formcusApplySpouseSurveyDetail = formService.getFormData("cusApplySpouseSurveyDetail");
			getFormValue(formcusApplySpouseSurveyDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusApplySpouseSurveyDetail)){
				mfCusApplySpouseSurvey = new MfCusApplySpouseSurvey();
				setObjValue(formcusApplySpouseSurveyDetail, mfCusApplySpouseSurvey);
				mfCusApplySpouseSurveyFeign.update(mfCusApplySpouseSurvey);
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
	public Map<String,Object> getByIdAjax(String surveyId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusApplySpouseSurveyDetail = formService.getFormData("cusApplySpouseSurveyDetail");
		MfCusApplySpouseSurvey mfCusApplySpouseSurvey = new MfCusApplySpouseSurvey();
		mfCusApplySpouseSurvey.setSurveyId(surveyId);
		mfCusApplySpouseSurvey = mfCusApplySpouseSurveyFeign.getById(mfCusApplySpouseSurvey);
		getObjValue(formcusApplySpouseSurveyDetail, mfCusApplySpouseSurvey,formData);
		if(mfCusApplySpouseSurvey!=null){
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
		MfCusApplySpouseSurvey mfCusApplySpouseSurvey = new MfCusApplySpouseSurvey();
		mfCusApplySpouseSurvey.setSurveyId(surveyId);
		try {
			mfCusApplySpouseSurveyFeign.delete(mfCusApplySpouseSurvey);
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
	public String input(Model model,String cusNo,String relNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		String formId=null;
		MfCusApplySpouseSurvey mfCusApplySpouseSurvey = new MfCusApplySpouseSurvey();
		mfCusApplySpouseSurvey.setCusNo(cusNo);
		mfCusApplySpouseSurvey.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
	
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusApplySpouseSurveyAction");
		if(mfCusFormConfig != null){
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusApplySpouseSurveyAction表单信息没有查询到");
		}else{
			FormData formcusApplySpouseSurveyBase = formService.getFormData(formId);
			if(formcusApplySpouseSurveyBase.getFormId() == null){
//				log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusApplySpouseSurveyAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcusApplySpouseSurveyBase);
				mfCusApplySpouseSurvey.setCusName(mfCusCustomer.getCusName());
				
				// 配偶姓名，身份证，居住地址回显
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelative("1");
				List<MfCusFamilyInfo> mfCusFamilyList = mfCusFamilyInfoFeign.getFamilyList(mfCusFamilyInfo);
				if(mfCusFamilyList!=null && mfCusFamilyList.size()>0){
					mfCusFamilyInfo = mfCusFamilyList.get(0);
					mfCusApplySpouseSurvey.setSpouseName(mfCusFamilyInfo.getRelName());
					mfCusApplySpouseSurvey.setSpouseIdentity(mfCusFamilyInfo.getIdNum());
					mfCusApplySpouseSurvey.setLiveAddress(mfCusFamilyInfo.getPostalAddress());
				}
				
				getObjValue(formcusApplySpouseSurveyBase, mfCusApplySpouseSurvey);
				model.addAttribute("formcusApplySpouseSurveyBase", formcusApplySpouseSurveyBase);
				model.addAttribute("query", "");
			}
		}
		return "/component/cus/MfCusApplySpouseSurvey_Insert";
	}
	
	
	/**
	 * 新增页面(业务)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model,String cusNo,String relNo,String kindNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfCusApplySpouseSurvey mfCusApplySpouseSurvey = new MfCusApplySpouseSurvey();
		String formId=null;
		mfCusApplySpouseSurvey.setCusNo(cusNo);
		mfCusApplySpouseSurvey.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
	
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusApplySpouseSurveyAction");
		if(mfCusFormConfig != null){
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			log.error("产品类型为"+kindNo+"的MfCusApplySpouseSurveyAction表单信息没有查询到");
		}else{
			FormData formcusApplySpouseSurveyBase = formService.getFormData(formId);
			if(formcusApplySpouseSurveyBase.getFormId() == null){
//				log.error("产品类型为"+kindNo+"的MfCusApplySpouseSurveyAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcusApplySpouseSurveyBase);
				mfCusApplySpouseSurvey.setCusName(mfCusCustomer.getCusName());
				
				// 配偶姓名，身份证，居住地址回显
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelative("1");
				List<MfCusFamilyInfo> mfCusFamilyList = mfCusFamilyInfoFeign.getFamilyList(mfCusFamilyInfo);
				if(mfCusFamilyList!=null && mfCusFamilyList.size()>0){
					mfCusFamilyInfo = mfCusFamilyList.get(0);
					mfCusApplySpouseSurvey.setSpouseName(mfCusFamilyInfo.getRelName());
					mfCusApplySpouseSurvey.setSpouseIdentity(mfCusFamilyInfo.getIdNum());
					mfCusApplySpouseSurvey.setLiveAddress(mfCusFamilyInfo.getPostalAddress());
				}
				
				getObjValue(formcusApplySpouseSurveyBase, mfCusApplySpouseSurvey);
				model.addAttribute("formcusApplySpouseSurveyBase", formcusApplySpouseSurveyBase);
				model.addAttribute("query", "");
			}
		}
		return "/component/cus/MfCusApplySpouseSurvey_Insert";
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
		FormData formcusApplySpouseSurveyBase = formService.getFormData("cusApplySpouseSurveyBase");
		getFormValue(formcusApplySpouseSurveyBase);
		MfCusApplySpouseSurvey mfCusApplySpouseSurvey = new MfCusApplySpouseSurvey();
		mfCusApplySpouseSurvey.setSurveyId(surveyId);
		mfCusApplySpouseSurvey = mfCusApplySpouseSurveyFeign.getById(mfCusApplySpouseSurvey);
		getObjValue(formcusApplySpouseSurveyBase, mfCusApplySpouseSurvey);
		model.addAttribute("formcusApplySpouseSurveyBase",formcusApplySpouseSurveyBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusApplySpouseSurvey_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcusApplySpouseSurveyDetail = formService.getFormData("cusApplySpouseSurveyDetail");
		 getFormValue(formcusApplySpouseSurveyDetail);
		 boolean validateFlag = this.validateFormData(formcusApplySpouseSurveyDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcusApplySpouseSurveyDetail = formService.getFormData("cusApplySpouseSurveyDetail");
		 getFormValue(formcusApplySpouseSurveyDetail);
		 boolean validateFlag = this.validateFormData(formcusApplySpouseSurveyDetail);
	}
}
