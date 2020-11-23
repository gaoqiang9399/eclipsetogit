package app.component.cus.memeanage.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.cusmemanage.entity.MfCusMeManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.memeanage.feign.MfCusMeManageFeign;
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
@RequestMapping("/mfCusMeManage")
public class MfCusMeManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusMeManageFeign mfCusMeManageFeign;
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
		MfCusMeManage mfCusMeManage = new MfCusMeManage();
		try {
			mfCusMeManage.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusMeManage.setCriteriaList(mfCusMeManage, ajaxData);//我的筛选
			mfCusMeManage.setDelFlag(BizPubParm.YES_NO_N);
			//mfCusGuaLoanOuterSum.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusGuaLoanOuterSum,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusMeManage", mfCusMeManage));
			//自定义查询Bo方法
			ipage = mfCusMeManageFeign.findByPage(ipage);
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
		MfCusMeManage mfCusMeManage = new MfCusMeManage();
		mfCusMeManage.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusMeManageAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formmfcusMeManageBase = formService.getFormData(formId);
			if (formmfcusMeManageBase.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else { 
				getFormValue(formmfcusMeManageBase);
				mfCusMeManage.setCusName(mfCusCustomer.getCusName());
				getObjValue(formmfcusMeManageBase, mfCusMeManage);
			}
			model.addAttribute("formmfcusMeManageBase", formmfcusMeManageBase);
		}
//		model.addAttribute("cusName", mfCusCustomer.getCusName());
//		model.addAttribute("cusNo", mfCusCustomer.getCusNo());
		model.addAttribute("query", "");
		return "/component/cus/memanage/MfCusMeManage_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusMeManage mfCusMeManage = new MfCusMeManage();
		mfCusMeManage.setId(id);
		String formId = "";
		mfCusMeManage = mfCusMeManageFeign.getById(mfCusMeManage);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusMeManage.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusMeManageAction");
		if(mfCusFormConfig!=null){
			formId = mfCusFormConfig.getAddModelDef();
		}else{
			formId="cusMeManageBase";
		}
		FormData formcususMeManageBase = formService.getFormData(formId);
		getFormValue(formcususMeManageBase);
		getObjValue(formcususMeManageBase, mfCusMeManage);
		model.addAttribute("formcususMeManageBase", formcususMeManageBase);
		model.addAttribute("query", "");
		return "/component/cus/memanage/MfCusMeManage_Detail";
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusMeManageAction").getAddModel();
			}
			FormData cusCourtInfoBase = formService.getFormData(formId);
			
			getFormValue(cusCourtInfoBase, getMapByJson(ajaxData));
			if (this.validateFormData(cusCourtInfoBase)) {
				MfCusMeManage mfCusMeManage = new MfCusMeManage();
				mfCusMeManage.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(cusCourtInfoBase, mfCusMeManage);
				dataMap = mfCusMeManageFeign.insert(mfCusMeManage);
				String cusNo = mfCusMeManage.getCusNo();
				String relNo = mfCusMeManage.getCusNo();

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusMeManage.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "mfCusMeManage";
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusMeManageAction");				
				if(mfCusFormConfig != null){
					if(StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())){
						tableId = mfCusFormConfig.getListModelDef();
					}
				}
				
				mfCusMeManage = new MfCusMeManage();
				mfCusMeManage.setCusNo(cusNo);
//				MfCusMeManage.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusMeManage", mfCusMeManage));
				String tableHtml = jtu.getJsonStr("table"+tableId, "tableTag",
						(List<MfCusMeManage>) mfCusMeManageFeign.findByPage(ipage).getResult(), null, true);
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo,relNo);// 更新资料完整度
				dataMap.put("mfCusMeManage", mfCusMeManage);
				dataMap.put("htmlStr", tableHtml);
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
		MfCusMeManage mfCusMeManageJsp = new MfCusMeManage();
		setObjValue(formcusper00002, mfCusMeManageJsp);
		MfCusMeManage mfCusMeManage = mfCusMeManageFeign.getById(mfCusMeManageJsp);
		if(mfCusMeManage!=null){
			try{
				mfCusMeManage = (MfCusMeManage) EntityUtil.reflectionSetVal(mfCusMeManage, mfCusMeManageJsp, getMapByJson(ajaxData));
				mfCusMeManageFeign.update(mfCusMeManage);
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
		MfCusMeManage mfCusMeManage = new MfCusMeManage();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusMeManageAction").getAddModel();
			}
			FormData formmfcusMeManageBase = formService.getFormData(formId);
			getFormValue(formmfcusMeManageBase, map);
			if (this.validateFormData(formmfcusMeManageBase)) {

				setObjValue(formmfcusMeManageBase, mfCusMeManage);
				mfCusMeManageFeign.update(mfCusMeManage);

				String cusNo = mfCusMeManage.getCusNo();
				mfCusMeManage.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusMeManage", mfCusMeManage));
				String tableHtml = jtu.getJsonStr("tablemfCusMeManage", "tableTag",
						(List<MfCusMeManage>) mfCusMeManageFeign.findByPage(ipage).getResult(), null,
						true);
				dataMap.put("htmlStr", tableHtml);
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusMeManage mfCusMeManage = new MfCusMeManage();
		mfCusMeManage.setId(id);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusEquityInfo = (MfCusEquityInfo)JSONObject.toBean(jb,
			// MfCusEquityInfo.class);
			mfCusMeManageFeign.delete(mfCusMeManage);
			// getTableData();
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
				"MfCusMeManageAction");
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
			MfCusMeManage mfCusMeManage = new MfCusMeManage();
			mfCusMeManage.setId(id);
			mfCusMeManage = mfCusMeManageFeign.getById(mfCusMeManage);
			getObjValue(formcusMeManage0003, mfCusMeManage, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusMeManage0003, "propertySeeTag", "");
			if (mfCusMeManage != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusMeManage);
		}
		return dataMap;
	}
}
