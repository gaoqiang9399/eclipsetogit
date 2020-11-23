package  app.component.cus.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusHighInfo;
import app.component.cus.entity.MfCusShed;
import app.component.cus.entity.MfCusShed;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusShedFeign;
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
 * Title: MfCusShedAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 20 10:04:14 CST 2018
 **/
@Controller
@RequestMapping("/mfCusShed")
public class MfCusShedController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCusShedFeign mfCusShedFeign;
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
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/cus/MfCusShed_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusShed mfCusShed = new MfCusShed();
		try {
			mfCusShed.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusShed.setCriteriaList(mfCusShed, ajaxData);//我的筛选
			//mfCusShed.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusShed,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusShed", mfCusShed));
			//自定义查询Bo方法
			ipage = mfCusShedFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData, String cusNo, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcusShedDetail = formService.getFormData(formId);
			getFormValue(formcusShedDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusShedDetail)){
				MfCusShed mfCusShed = new MfCusShed();
				setObjValue(formcusShedDetail, mfCusShed);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				if(cusNo!=null) {
					mfCusCustomer.setCusNo(cusNo);
				}else {
					cusNo = mfCusShed.getCusNo();
					mfCusCustomer.setCusNo(cusNo);
				}
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusShed.setCusName(cusName);
				mfCusShedFeign.insert(mfCusShed);
				String relNo = mfCusShed.getRelNo();
				getTableData(cusNo, relNo, tableId, dataMap);
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo, mfCusShed.getRelNo());// 更新客户信息完整度
				mfCusShed = new MfCusShed();
				mfCusShed.setCusNo(cusNo);
				mfCusShed.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusShed", mfCusShed));
				String tableHtml = jtu.getJsonStr("tablecusShedList", "tableTag",
						(List<MfCusShed>) mfCusShedFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusShedDetail = formService.getFormData("cusShedDetail");
		getFormValue(formcusShedDetail, getMapByJson(ajaxData));
		MfCusShed mfCusShedJsp = new MfCusShed();
		setObjValue(formcusShedDetail, mfCusShedJsp);
		MfCusShed mfCusShed = mfCusShedFeign.getById(mfCusShedJsp);
		if(mfCusShed!=null){
			try{
				mfCusShed = (MfCusShed)EntityUtil.reflectionSetVal(mfCusShed, mfCusShedJsp, getMapByJson(ajaxData));
				mfCusShedFeign.update(mfCusShed);
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
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusShedAction").getAddModel();
			}
			FormData formcusShedBase = formService.getFormData(formId);

			getFormValue(formcusShedBase,map);
			if(this.validateFormData(formcusShedBase)){
				MfCusShed mfCusShed= new MfCusShed();
				setObjValue(formcusShedBase, mfCusShed);
				mfCusShedFeign.update(mfCusShed);
				String  cusNo = mfCusShed.getCusNo();
				mfCusShed= new MfCusShed();
				mfCusShed.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusShed", mfCusShed));
				JsonTableUtil jtu = new JsonTableUtil();
				String  tableHtml = jtu.getJsonStr("tablecusShedList","tableTag", (List<MfCusShed>)mfCusShedFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");

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
	public Map<String, Object>  getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusShedDetail = formService.getFormData("cusShedDetail");
		MfCusShed mfCusShed = new MfCusShed();
		mfCusShed.setId(id);
		mfCusShed = mfCusShedFeign.getById(mfCusShed);
		getObjValue(formcusShedDetail, mfCusShed,formData);
		if(mfCusShed!=null){
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
	public Map<String,Object> deleteAjax(String id, String ajaxData, String cusNo, String tableId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusShed mfCusShed = new MfCusShed();
		mfCusShed.setId(id);
		try {
			mfCusShedFeign.delete(mfCusShed);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		
		MfCusShed mfCusShed = new MfCusShed();
		mfCusShed.setCusNo(cusNo);
		//根据获取的客户号查询该客户数据
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		//表单基本信息
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusShedAction");
		String formId = "";
		if(mfCusFormConfig == null){
			formId = "cusShedDetail";		
		}else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData formcusShedDetail = formService.getFormData(formId);
		mfCusShed.setCusName(mfCusCustomer.getCusName());
		mfCusShed.setRelNo(relNo);
		mfCusShed.setOpName(User.getRegName(request));
		mfCusShed.setOpNo(User.getRegNo(request));
		getObjValue(formcusShedDetail, mfCusShed);
		model.addAttribute("formcusShedDetail", formcusShedDetail);
		model.addAttribute("query", "");
		return "/component/cus/MfCusShed_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id,String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusShedBase = formService.getFormData("cusShedBase");
		getFormValue(formcusShedBase);
		MfCusShed mfCusShed = new MfCusShed();
		mfCusShed.setId(id);
		mfCusShed.setCusNo(cusNo);
		mfCusShed = mfCusShedFeign.getById(mfCusShed);
		getObjValue(formcusShedBase, mfCusShed);
		model.addAttribute("formcusShedBase", formcusShedBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusShed_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception{
		 FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		 FormData formcusShedDetail = formService.getFormData("cusShedDetail");
		 getFormValue(formcusShedDetail);
		 boolean validateFlag = this.validateFormData(formcusShedDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception{
		 FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		 FormData formcusShedDetail = formService.getFormData("cusShedDetail");
		 getFormValue(formcusShedDetail);
		 boolean validateFlag = this.validateFormData(formcusShedDetail);
	}
	
	private void getTableData(String cusNo, String relNo, String tableId, Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusShed mfCusShed = new MfCusShed();
		mfCusShed.setCusNo(cusNo);
		mfCusShed.setRelNo(relNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusShed", mfCusShed));
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				(List<MfCusShed>) mfCusShedFeign.findByPage(ipage).getResult(), null, true);
		dataMap.put("tableData", tableHtml);
	}
	
	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusShedAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusShedAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusShedDetail = formService.getFormData(formId);
			MfCusShed mfCusShed = new MfCusShed();
			mfCusShed.setId(id);
			mfCusShed = mfCusShedFeign.getById(mfCusShed);
			getObjValue(formcusShedDetail, mfCusShed, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusShedDetail, "propertySeeTag", "");
			if (mfCusShed != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusShed);
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String, Object> updateByOneAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusShed mfCusShed = new MfCusShed();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcusShedDetail = formService.getFormData(formId);
		getFormValue(formcusShedDetail, getMapByJson(ajaxData));
		MfCusShed mfCusShedNew = new MfCusShed();
		setObjValue(formcusShedDetail, mfCusShedNew);
		mfCusShed.setId(mfCusShedNew.getId());
		mfCusShed = mfCusShedFeign.getById(mfCusShed);
		if (mfCusShed != null) {
			try {
				mfCusShed = (MfCusShed) EntityUtil.reflectionSetVal(mfCusShed, mfCusShedNew,
						getMapByJson(ajaxData));
				mfCusShedFeign.update(mfCusShed);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}
}
