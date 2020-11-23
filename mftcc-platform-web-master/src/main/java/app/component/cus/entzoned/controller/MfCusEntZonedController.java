package app.component.cus.entzoned.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entzoned.entity.MfCusEntZoned;
import app.component.cus.entzoned.feign.MfCusEntZonedFeign;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
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
@RequestMapping("/mfCusEntZoned")
public class MfCusEntZonedController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusEntZonedFeign mfCusEntZonedFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	
	
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
		return "/component/cus/entzoned/MfCusEntZoned_List";
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
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		try {
			mfCusCorpBaseInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCorpBaseInfo.setCriteriaList(mfCusCorpBaseInfo, ajaxData);//我的筛选
			//mfCusGuaLoanOuterSum.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusGuaLoanOuterSum,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCorpBaseInfo", mfCusCorpBaseInfo));
			//自定义查询Bo方法
			ipage = mfCusCorpBaseInfoFeign.findPageWithEntZoned(ipage);
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
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findHisByPageAjax")
	public Map<String, Object> findHisByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
											  String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusEntZoned mfCusEntZoned = new MfCusEntZoned();
		try {
			mfCusEntZoned.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusEntZoned.setCriteriaList(mfCusEntZoned, ajaxData);//我的筛选
			mfCusEntZoned.setCusNo(cusNo);
			//mfCusGuaLoanOuterSum.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusGuaLoanOuterSum,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusEntZoned", mfCusEntZoned));
			//自定义查询Bo方法
			ipage = mfCusEntZonedFeign.findByPage(ipage);
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
	public String input(Model model, String cusNo, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusEntZoned mfCusEntZoned = new MfCusEntZoned();
		mfCusEntZoned.setCusNo(cusNo);
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		mfCusCorpBaseInfo.setCusNo(cusNo);
		mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusEntZonedAction");
		String formId = "";
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isNotEmpty(formId)) {
			FormData formmfcusEntZonedBase = formService.getFormData(formId);
			if (formmfcusEntZonedBase.getFormId() != null) {
				getFormValue(formmfcusEntZonedBase);
				mfCusEntZoned.setCusName(mfCusCustomer.getCusName());
				mfCusEntZoned.setCusNo(mfCusCustomer.getCusNo());
				mfCusEntZoned.setIdNum(mfCusCustomer.getIdNum());
				mfCusEntZoned.setOldProjSize(mfCusCorpBaseInfo.getProjSize());
				mfCusEntZoned.setWayClass(mfCusCorpBaseInfo.getWayClass());
				mfCusEntZoned.setWayClassDes(mfCusCorpBaseInfo.getWayClassDes());
				mfCusEntZoned.setAmtSum(mfCusCorpBaseInfo.getAssetSum());
				mfCusEntZoned.setIncomeSum(mfCusCorpBaseInfo.getBussIncome());
				mfCusEntZoned.setExt1(mfCusCorpBaseInfo.getEmpCnt());
				MfCusEntZoned mfCusEntZonedTo = new  MfCusEntZoned();
				mfCusEntZonedTo.setId(mfCusEntZoned.getCusNo());
				mfCusEntZonedTo = mfCusEntZonedFeign.getById(mfCusEntZonedTo);
				if(mfCusEntZonedTo!=null){
					mfCusEntZoned.setRemark(mfCusEntZonedTo.getRemark());
					mfCusEntZoned.setWayMaxClass(mfCusEntZonedTo.getWayMaxClass());
					mfCusEntZoned.setAmtSum(mfCusEntZonedTo.getAmtSum());
					mfCusEntZoned.setIncomeSum(mfCusEntZonedTo.getIncomeSum());
					mfCusEntZoned.setPeople(mfCusEntZonedTo.getPeople());
					if(mfCusEntZonedTo.getPeople()!=null){
						mfCusEntZoned.setExt1(Integer.valueOf(mfCusEntZonedTo.getPeople()));
					}
				}
				getObjValue(formmfcusEntZonedBase, mfCusEntZoned);
			}
			model.addAttribute("formmfcusEntZonedBase", formmfcusEntZonedBase);
		}
		List<MfCusEntZoned> mfCusEntZonedList = mfCusEntZonedFeign.getAllList(mfCusEntZoned);
		//日期格式转化
		for(MfCusEntZoned mfCusEntZonedTo : mfCusEntZonedList){
			mfCusEntZonedTo.setRegTime(DateUtil.getShowDateTime(mfCusEntZonedTo.getRegTime()));
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String tableId = "tablecusEntZonedBaseList";
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfCusEntZonedList, null, true);
		model.addAttribute("mfCusEntZonedList", mfCusEntZonedList);
		model.addAttribute("tableHtml", tableHtml);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", cusNo);
		return "/component/cus/entzoned/MfCusEntZoned_Insert";
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
		MfCusEntZoned mfCusEntZoned = new MfCusEntZoned();
		mfCusEntZoned.setId(id);
		FormData formmfCusEntZonedBase = formService.getFormData("cusMeManageBase");
		String flag = "update";
		getFormValue(formmfCusEntZonedBase);
		mfCusEntZoned = mfCusEntZonedFeign.getById(mfCusEntZoned);
		getObjValue(formmfCusEntZonedBase, mfCusEntZoned);
		model.addAttribute("formmfCusEntZonedBase", formmfCusEntZonedBase);
		model.addAttribute("query", "");
		return "/component/cus/entzoned/MfCusEntZoned_Detail";
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
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusEntZonedAction").getAddModel();
			}
			FormData cusCourtInfoBase = formService.getFormData(formId);
			getFormValue(cusCourtInfoBase, getMapByJson(ajaxData));
			if (this.validateFormData(cusCourtInfoBase)) {
				MfCusEntZoned mfCusEntZoned = new MfCusEntZoned();
				mfCusEntZoned.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(cusCourtInfoBase, mfCusEntZoned);
				if(mfCusEntZoned.getWayClass().length()<5){
					dataMap.put("flag", "error");
					dataMap.put("msg", "行业需要选到第四级！");
					return dataMap;
				}
				mfCusEntZoned.setPeople(mfCusEntZoned.getExt1().toString());
				dataMap = mfCusEntZonedFeign.insert(mfCusEntZoned);
				String cusNo = mfCusEntZoned.getCusNo();
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusEntZoned.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "mfCusEntZoned";
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusEntZonedAction");				
				if(mfCusFormConfig != null){
					if(StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())){
						tableId = mfCusFormConfig.getListModelDef();
					}
				}
				mfCusEntZoned = new MfCusEntZoned();
				mfCusEntZoned.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusEntZoned", mfCusEntZoned));
				String tableHtml = jtu.getJsonStr("table"+tableId, "tableTag",
						(List<MfCusEntZoned>) mfCusEntZonedFeign.findByPage(ipage).getResult(), null, true);
				
				dataMap.put("mfCusEntZoned", mfCusEntZoned);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("DataFullFlag", "1");
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
		MfCusEntZoned mfCusEntZonedJsp = new MfCusEntZoned();
		setObjValue(formcusper00002, mfCusEntZonedJsp);
		MfCusEntZoned mfCusEntZoned = mfCusEntZonedFeign.getById(mfCusEntZonedJsp);
		if(mfCusEntZoned!=null){
			try{
				mfCusEntZoned = (MfCusEntZoned) EntityUtil.reflectionSetVal(mfCusEntZoned, mfCusEntZonedJsp, getMapByJson(ajaxData));
				mfCusEntZonedFeign.update(mfCusEntZoned);
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
		MfCusEntZoned mfCusEntZoned = new MfCusEntZoned();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusEntZonedAction").getAddModel();
			}
			FormData formmfcusMeManageBase = formService.getFormData(formId);
			getFormValue(formmfcusMeManageBase, map);
			if (this.validateFormData(formmfcusMeManageBase)) {

				setObjValue(formmfcusMeManageBase, mfCusEntZoned);
				mfCusEntZonedFeign.update(mfCusEntZoned);

				String cusNo = mfCusEntZoned.getCusNo();
				mfCusEntZoned.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusEntZoned", mfCusEntZoned));
				String tableHtml = jtu.getJsonStr("tablecusMeManageBase", "tableTag",
						(List<MfCusEntZoned>) mfCusEntZonedFeign.findByPage(ipage).getResult(), null,
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
		MfCusEntZoned mfCusEntZoned = new MfCusEntZoned();
		mfCusEntZoned.setId(id);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusEquityInfo = (MfCusEquityInfo)JSONObject.toBean(jb,
			// MfCusEquityInfo.class);
			mfCusEntZonedFeign.delete(mfCusEntZoned);
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
				"MfCusEntZonedAction");
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
			MfCusEntZoned mfCusEntZoned = new MfCusEntZoned();
			mfCusEntZoned.setId(id);
			mfCusEntZoned = mfCusEntZonedFeign.getById(mfCusEntZoned);
			getObjValue(formcusMeManage0003, mfCusEntZoned, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusMeManage0003, "propertySeeTag", "");
			if (mfCusEntZoned != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusEntZoned);
		}
		return dataMap;
	}
}
