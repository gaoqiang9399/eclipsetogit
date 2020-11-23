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
import app.component.cus.entity.MfCusApplyPersonSurvey;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.feign.MfCusApplyPersonSurveyFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFamilyInfoFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusApplyPersonSurveyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Nov 28 09:32:23 CST 2017
 **/
@Controller
@RequestMapping("/mfCusApplyPersonSurvey")
public class MfCusApplyPersonSurveyController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCusApplyPersonSurveyFeign mfCusApplyPersonSurveyFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
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
		return "/component/cus/MfCusApplyPersonSurvey_List";
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
		MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
		try {
			mfCusApplyPersonSurvey.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusApplyPersonSurvey.setCriteriaList(mfCusApplyPersonSurvey, ajaxData);//我的筛选
			//mfCusApplyPersonSurvey.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusApplyPersonSurvey,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusApplyPersonSurvey", mfCusApplyPersonSurvey));
			ipage = mfCusApplyPersonSurveyFeign.findByPage(ipage);
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
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusApplyPersonSurveyAction");
		if(mfCusFormConfig != null){
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusApplyPersonSurveyAction表单信息没有查询到");
		}else{
			try{
				FormData formcusApplyPersonSurveyBase = formService.getFormData(formId);
				getFormValue(formcusApplyPersonSurveyBase, getMapByJson(ajaxData));
				if(this.validateFormData(formcusApplyPersonSurveyBase)){
					MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
					setObjValue(formcusApplyPersonSurveyBase, mfCusApplyPersonSurvey);
					mfCusApplyPersonSurveyFeign.insert(mfCusApplyPersonSurvey);
					
					String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusApplyPersonSurvey.getCusNo(), mfCusApplyPersonSurvey.getRelNo());//更新资料完整度
					String detailFormId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusApplyPersonSurveyAction").getShowModelDef();
					if(StringUtil.isEmpty(detailFormId)){
//						log.error("MfCusApplyPersonSurveyAction的detailFormId找不到");
					}
					FormData formcusApplyPersonSurveyDetail = formService.getFormData(detailFormId);
					getObjValue(formcusApplyPersonSurveyDetail, mfCusApplyPersonSurvey);
					this.getHttpRequest().setAttribute("ifBizManger","3");
					JsonFormUtil jsonFormUtil = new JsonFormUtil();
					String htmlStr = jsonFormUtil.getJsonStr(formcusApplyPersonSurveyDetail,"propertySeeTag",query);
					
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
	@RequestMapping(value = "updateAjaxByOne")
	@ResponseBody
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcusApplyPersonSurveyDetail = formService.getFormData(formId);
		getFormValue(formcusApplyPersonSurveyDetail, getMapByJson(ajaxData));
		MfCusApplyPersonSurvey mfCusApplyPersonSurveyJsp = new MfCusApplyPersonSurvey();
		setObjValue(formcusApplyPersonSurveyDetail, mfCusApplyPersonSurveyJsp);
		MfCusApplyPersonSurvey mfCusApplyPersonSurvey = mfCusApplyPersonSurveyFeign.getById(mfCusApplyPersonSurveyJsp);
		if(mfCusApplyPersonSurvey!=null){
			try{
				mfCusApplyPersonSurvey = (MfCusApplyPersonSurvey)EntityUtil.reflectionSetVal(mfCusApplyPersonSurvey, mfCusApplyPersonSurveyJsp, getMapByJson(ajaxData));
				mfCusApplyPersonSurveyFeign.update(mfCusApplyPersonSurvey);
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
	@RequestMapping(value = "updateAjax")
	@ResponseBody
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
		try{
			FormData formcusApplyPersonSurveyDetail = formService.getFormData("cusApplyPersonSurveyDetail");
			getFormValue(formcusApplyPersonSurveyDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusApplyPersonSurveyDetail)){
				mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
				setObjValue(formcusApplyPersonSurveyDetail, mfCusApplyPersonSurvey);
				mfCusApplyPersonSurveyFeign.update(mfCusApplyPersonSurvey);
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
	@RequestMapping(value = "getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String surveyId) throws Exception {
		ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusApplyPersonSurveyDetail = formService.getFormData("cusApplyPersonSurveyDetail");
		MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
		mfCusApplyPersonSurvey.setSurveyId(surveyId);
		mfCusApplyPersonSurvey = mfCusApplyPersonSurveyFeign.getById(mfCusApplyPersonSurvey);
		getObjValue(formcusApplyPersonSurveyDetail, mfCusApplyPersonSurvey,formData);
		if(mfCusApplyPersonSurvey!=null){
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
	@RequestMapping(value = "deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String surveyId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
		mfCusApplyPersonSurvey.setSurveyId(surveyId);
		try {
			mfCusApplyPersonSurveyFeign.delete(mfCusApplyPersonSurvey);
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
	@RequestMapping(value = "input")
	public String input(Model model,String cusNo,String relNo) throws Exception{
		ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		String formId=null;
		MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
		mfCusApplyPersonSurvey.setCusNo(cusNo);
		mfCusApplyPersonSurvey.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
	
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusApplyPersonSurveyAction");
		if(mfCusFormConfig != null){
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusApplyPersonSurveyAction表单信息没有查询到");
		}else{
			FormData formcusApplyPersonSurveyBase = formService.getFormData(formId);
			if(formcusApplyPersonSurveyBase.getFormId() == null){
//				log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusApplyPersonSurveyAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcusApplyPersonSurveyBase);
				mfCusApplyPersonSurvey.setCusName(mfCusCustomer.getCusName());
				
				// 回显申请人名称，申请人身份证，居住地址
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				if(mfCusPersBaseInfo!=null){
					mfCusApplyPersonSurvey.setApplyPersonName(mfCusPersBaseInfo.getCusName());
					mfCusApplyPersonSurvey.setApplyPersonIdentity(mfCusPersBaseInfo.getIdNum());
					mfCusApplyPersonSurvey.setLiveAddress(mfCusPersBaseInfo.getCommAddress());
				}
				
				//回显配偶姓名
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelative("1");
				List<MfCusFamilyInfo> mfCusFamilyList = mfCusFamilyInfoFeign.getFamilyList(mfCusFamilyInfo);
				if(mfCusFamilyList!=null && mfCusFamilyList.size()>0){
					mfCusFamilyInfo = mfCusFamilyList.get(0);
					mfCusApplyPersonSurvey.setSpouseName(mfCusFamilyInfo.getRelName());
				}
				
				getObjValue(formcusApplyPersonSurveyBase, mfCusApplyPersonSurvey);
				model.addAttribute("formcusApplyPersonSurveyBase", formcusApplyPersonSurveyBase);
				model.addAttribute("query", "");
			}
		}
		return "/component/cus/MfCusApplyPersonSurvey_Insert";
	}
	
	
	/**
	 * 新增页面(业务)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "inputBiz")
	public String inputBiz(Model model,String cusNo,String relNo,String kindNo) throws Exception{
		ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		String formId=null;
		MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
		mfCusApplyPersonSurvey.setCusNo(cusNo);
		mfCusApplyPersonSurvey.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
	
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusApplyPersonSurveyAction");
		if(mfCusFormConfig != null){
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			log.error("产品类型为"+kindNo+"的MfCusApplyPersonSurveyAction表单信息没有查询到");
		}else{
			FormData formcusApplyPersonSurveyBase = formService.getFormData(formId);
			if(formcusApplyPersonSurveyBase.getFormId() == null){
//				log.error("产品类型为"+kindNo+"的MfCusApplyPersonSurveyAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcusApplyPersonSurveyBase);
				mfCusApplyPersonSurvey.setCusName(mfCusCustomer.getCusName());
				
				// 回显申请人名称，申请人身份证，居住地址
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				if(mfCusPersBaseInfo!=null){
					mfCusApplyPersonSurvey.setApplyPersonName(mfCusPersBaseInfo.getCusName());
					mfCusApplyPersonSurvey.setApplyPersonIdentity(mfCusPersBaseInfo.getIdNum());
					mfCusApplyPersonSurvey.setLiveAddress(mfCusPersBaseInfo.getCommAddress());
				}
				
				//回显配偶姓名
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelative("1");
				List<MfCusFamilyInfo> mfCusFamilyList = mfCusFamilyInfoFeign.getFamilyList(mfCusFamilyInfo);
				if(mfCusFamilyList!=null && mfCusFamilyList.size()>0){
					mfCusFamilyInfo = mfCusFamilyList.get(0);
					mfCusApplyPersonSurvey.setSpouseName(mfCusFamilyInfo.getRelName());
				}
				
				getObjValue(formcusApplyPersonSurveyBase, mfCusApplyPersonSurvey);
				model.addAttribute("formcusApplyPersonSurveyBase", formcusApplyPersonSurveyBase);
				model.addAttribute("query", "");
			}
		}
		return "/component/cus/MfCusApplyPersonSurvey_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getById")
	public String getById(Model model,String surveyId) throws Exception{
		ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formcusApplyPersonSurveyBase = formService.getFormData("cusApplyPersonSurveyBase");
		 getFormValue(formcusApplyPersonSurveyBase);
		 MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
		 mfCusApplyPersonSurvey.setSurveyId(surveyId);
		 mfCusApplyPersonSurvey = mfCusApplyPersonSurveyFeign.getById(mfCusApplyPersonSurvey);
		 getObjValue(formcusApplyPersonSurveyBase, mfCusApplyPersonSurvey);
		 model.addAttribute("formcusApplyPersonSurveyBase", formcusApplyPersonSurveyBase);
		 model.addAttribute("query", "");
		return "/component/cus/MfCusApplyPersonSurvey_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcusApplyPersonSurveyDetail = formService.getFormData("cusApplyPersonSurveyDetail");
		 getFormValue(formcusApplyPersonSurveyDetail);
		boolean validateFlag = this.validateFormData(formcusApplyPersonSurveyDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcusApplyPersonSurveyDetail = formService.getFormData("cusApplyPersonSurveyDetail");
		 getFormValue(formcusApplyPersonSurveyDetail);
		 boolean validateFlag = this.validateFormData(formcusApplyPersonSurveyDetail);
	}
}
