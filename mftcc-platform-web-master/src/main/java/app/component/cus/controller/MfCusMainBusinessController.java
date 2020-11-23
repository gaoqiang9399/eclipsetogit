package app.component.cus.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.cusmemanage.entity.MfCusMeManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusMainBusiness;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusMainBusinessFeign;
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

@Controller
@RequestMapping("/mfCusMainBusiness")
public class MfCusMainBusinessController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusMainBusinessFeign mfCusMainBusinessFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	
	
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
		return "/component/cus/memanage/MfCusMeManage_List";
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
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
		try {
			mfCusMainBusiness.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusMainBusiness.setCriteriaList(mfCusMainBusiness, ajaxData);//我的筛选
			mfCusMainBusiness.setDelFlag(BizPubParm.YES_NO_N);
			//mfCusGuaLoanOuterSum.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusGuaLoanOuterSum,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusMainBusiness", mfCusMainBusiness));
			//自定义查询Bo方法
			ipage = mfCusMainBusinessFeign.findByPage(ipage);
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
	 * 新增页面
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
		mfCusMainBusiness.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusMainBusinessAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formmfcusMainBusinessBase = formService.getFormData(formId);
			if (formmfcusMainBusinessBase.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else { 
				getFormValue(formmfcusMainBusinessBase);
				mfCusMainBusiness.setCusName(mfCusCustomer.getCusName());
				getObjValue(formmfcusMainBusinessBase, mfCusMainBusiness);
			}
			model.addAttribute("formmfcusMainBusinessBase", formmfcusMainBusinessBase);
			model.addAttribute("cusNo",cusNo);
			model.addAttribute("cusName",mfCusCustomer.getCusName());
		 }
		model.addAttribute("query", "");
		return "/component/cus/MfCusMainBusiness_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
		mfCusMainBusiness.setCusNo(cusNo);
		mfCusMainBusiness.setMainType("1");//只查询客户下市场需求
		String formId = "";
		mfCusMainBusiness = mfCusMainBusinessFeign.getByMainType(mfCusMainBusiness);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusMainBusiness.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusMainBusinessAction");
		if(mfCusFormConfig!=null){
			formId = mfCusFormConfig.getAddModelDef();
		}else{
			formId="cusMeManageBase";
		}
		FormData formcususMeManageBase = formService.getFormData(formId);
		getFormValue(formcususMeManageBase);
		getObjValue(formcususMeManageBase, mfCusMainBusiness);
		model.addAttribute("formmfcusMainBusinessBase", formcususMeManageBase);
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("cusName",mfCusCustomer.getCusName());
		model.addAttribute("query", "");
		return "/component/cus/MfCusMainBusiness_Detail";
	}
	/**
	 * AJAX新增
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusMainBusinessAction").getAddModel();
			}
			FormData cusCourtInfoBase = formService.getFormData(formId);
			
			getFormValue(cusCourtInfoBase, getMapByJson(ajaxData));
			if (this.validateFormData(cusCourtInfoBase)) {
				MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
				mfCusMainBusiness.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(cusCourtInfoBase, mfCusMainBusiness);
				dataMap = mfCusMainBusinessFeign.insert(mfCusMainBusiness);
				String cusNo = mfCusMainBusiness.getCusNo();
				String relNo = mfCusMainBusiness.getCusNo();

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusMainBusiness.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusMainBussinessAction");
				formId = "cusMainBusinessDetail";
				if(formId==null){
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formcusMainBusinessDetail = formService.getFormData(formId);

				MfCusMainBusiness mfCusMainBusiness1 = new MfCusMainBusiness();
				mfCusMainBusiness1.setMainId(String.valueOf(dataMap.get("mainId")));
				mfCusMainBusiness1.setMainType("1");
				mfCusMainBusiness1 = mfCusMainBusinessFeign.getByMainType(mfCusMainBusiness1);
				getFormValue(formcusMainBusinessDetail);
				getObjValue(formcusMainBusinessDetail, mfCusMainBusiness1);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusMainBusinessDetail, "propertySeeTag", "");
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo, relNo);// 更新资料完整度
				dataMap.put("mfCusMainBusiness", mfCusMainBusiness);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("DataFullFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
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
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String formId = (String) getMapByJson(ajaxData).get("formId");
		FormData formcusper00002 = formService.getFormData(formId);
		getFormValue(formcusper00002, getMapByJson(ajaxData));
		MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
		setObjValue(formcusper00002, mfCusMainBusiness);
		MfCusMainBusiness mfCusMainBusiness1 = mfCusMainBusinessFeign.getById(mfCusMainBusiness);
		if(mfCusMainBusiness1!=null){
			try{
				mfCusMainBusiness1 = (MfCusMainBusiness) EntityUtil.reflectionSetVal(mfCusMainBusiness1, mfCusMainBusiness, getMapByJson(ajaxData));
				mfCusMainBusinessFeign.update(mfCusMainBusiness);
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
		MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusMeManageAction").getAddModel();
			}
			FormData formmfcusMeManageBase = formService.getFormData(formId);
			getFormValue(formmfcusMeManageBase, map);
			if (this.validateFormData(formmfcusMeManageBase)) {

				setObjValue(formmfcusMeManageBase, mfCusMainBusiness);

				mfCusMainBusinessFeign.update(mfCusMainBusiness);
				formId = "cusMainBusinessDetail";
				FormData formcusMainBusinessDetail = formService.getFormData(formId);

				MfCusMainBusiness mfCusMainBusiness1 = new MfCusMainBusiness();
				mfCusMainBusiness1.setMainId(String.valueOf(dataMap.get("mainId")));
				mfCusMainBusiness1.setMainType("1");
				mfCusMainBusiness1 = mfCusMainBusinessFeign.getByMainType(mfCusMainBusiness1);
				getFormValue(formcusMainBusinessDetail);
				getObjValue(formcusMainBusinessDetail, mfCusMainBusiness1);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusMainBusinessDetail, "propertySeeTag", "");
				dataMap.put("mfCusMainBusiness", mfCusMainBusiness);
				dataMap.put("htmlStr", htmlStr);
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
	public Map<String, Object> deleteAjax(String mainId,String mainType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
		mfCusMainBusiness.setMainId(mainId);
		try {
			mfCusMainBusinessFeign.delete(mfCusMainBusiness);
			// getTableData();
			dataMap.put("flag", "success");
			dataMap.put("mainType", mainType);
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
				"MfCusMainBusinessAction");
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
			MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
			mfCusMainBusiness.setMainId(id);
			mfCusMainBusiness = mfCusMainBusinessFeign.getById(mfCusMainBusiness);
			getObjValue(formcusMeManage0003, mfCusMainBusiness, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusMeManage0003, "propertySeeTag", "");
			if (mfCusMainBusiness != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusMainBusiness);
		}
		return dataMap;
	}

	/**
	 * 上下游表单信息保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertInfo")
	@ResponseBody
	public Map<String, Object> insertInfo(String ajaxData) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map map = getMapByJson(ajaxData);
		String formId = (String)map.get("formId");
		FormData cusMainBusinessInfoBase = formService.getFormData(formId);
		getFormValue(cusMainBusinessInfoBase, getMapByJson(ajaxData));
		if (this.validateFormData(cusMainBusinessInfoBase)) {
			MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
			setObjValue(cusMainBusinessInfoBase, mfCusMainBusiness);
			mfCusMainBusiness.setSaveFlag("0");//暂时保存
			dataMap = mfCusMainBusinessFeign.insertInfo(mfCusMainBusiness);
			//展示上游信息与客户的列表
			String tableId="";
			String mainType = (String)map.get("mainType");//信息类型
			MfCusMainBusiness mainBusiness = new MfCusMainBusiness();
			if(mainType.equals("2")){//上游信息
				 tableId = "mainBusinessInfoList";
				 mainBusiness.setMainType("2");
				 mainBusiness.setCusNo(mfCusMainBusiness.getCusNo());
			}else{//下游客户
				 tableId = "mainBusinessCusInfoList";
				mainBusiness.setMainType("3");
				mainBusiness.setCusNo(mfCusMainBusiness.getCusNo());
			}
			List<MfCusMainBusiness> list = mfCusMainBusinessFeign.getAllList(mainBusiness);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("table"+tableId, "tableTag",
					list, null, true);

			dataMap.put("mfCusMainBusiness", mfCusMainBusiness);
			dataMap.put("htmlStr", tableHtml);
			dataMap.put("mainType",mainType);//信息类型
			dataMap.put("DataFullFlag", "1");
			dataMap.put("htmlStrFlag", "1");
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", this.getFormulavaliErrorMsg());
		}

		return dataMap;
	}

	@RequestMapping("/inputForList")
	public String inputForList(Model model,String cusNo,String mainType,String cusName) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "";
		if (mainType.equals("2")) {//上游信息
			formId = "cusMainBusinessInfoBase";
		} else {
			formId = "cusMainBusinessCusInfoBase";
		}
		MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
		mfCusMainBusiness.setCusNo(cusNo);
		mfCusMainBusiness.setCusName(cusName);
		mfCusMainBusiness.setMainType(mainType);
		FormData formdlcertiinfo0003 = formService.getFormData(formId);
		getFormValue(formdlcertiinfo0003);
		getObjValue(formdlcertiinfo0003, mfCusMainBusiness);
		model.addAttribute("formdlcertiinfo0003", formdlcertiinfo0003);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("cusName", cusName);
		model.addAttribute("mainType", mainType);
		model.addAttribute("query", "");
		return "/component/cus/MfCusMainBusiness_ForSelect";
	}
	@RequestMapping("/getInfoListHtmlForAjax")
	@ResponseBody
	public Map<String, Object> getInfoListHtmlForAjax(String cusNo,String mainType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusMainBusiness mainBusiness = new MfCusMainBusiness();
		try {

			mainBusiness.setMainType(mainType);
			mainBusiness.setCusNo(cusNo);
			List<MfCusMainBusiness> cusMainBusinessesList = mfCusMainBusinessFeign.getAllList(mainBusiness);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml="";
			if(mainType.equals("2")){//上游信息
				tableHtml = jtu.getJsonStr("tablemainBusinessInfoList", "tableTag", cusMainBusinessesList, null, true);
			}else{//下游客户
				tableHtml = jtu.getJsonStr("tablemainBusinessCusInfoList", "tableTag", cusMainBusinessesList, null, true);
			}
			dataMap.put("mainType",mainType);
			dataMap.put("tableData", tableHtml);
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

	@RequestMapping(value = "/deleteAjaxForSelect")
	@ResponseBody
	public Map<String, Object> deleteAjaxForSelect(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
		mfCusMainBusiness.setCusNo(cusNo);
		mfCusMainBusiness.setSaveFlag("0");//暂时保存的
		try {
			mfCusMainBusinessFeign.deleteForSelect(mfCusMainBusiness);
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
}
