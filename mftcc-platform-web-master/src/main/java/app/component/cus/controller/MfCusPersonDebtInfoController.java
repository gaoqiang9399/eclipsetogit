package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.sys.entity.MfUserPermission;
import config.YmlConfig;
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
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonDebtInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonDebtInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusPersonDebtInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Apr 11 09:20:57 CST 2017
 **/
@Controller
@RequestMapping("/mfCusPersonDebtInfo")
public class MfCusPersonDebtInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusPersonDebtInfoBo
	@Autowired
	private MfCusPersonDebtInfoFeign mfCusPersonDebtInfoFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private YmlConfig ymlConfig;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusPersonDebtInfo_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		try {
			mfCusPersonDebtInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPersonDebtInfo.setCriteriaList(mfCusPersonDebtInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusPersonDebtInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysBtnDef", mfCusPersonDebtInfo));
			ipage = mfCusPersonDebtInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonDebtInfoAction").getAddModel();
			}
			FormData formcuspersdebt0002 = formService.getFormData(formId);
			getFormValue(formcuspersdebt0002, map);

			if (this.validateFormDataAnchor(formcuspersdebt0002)) {
				MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				setObjValue(formcuspersdebt0002, mfCusPersonDebtInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonDebtInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				if(!"配偶".equals(mfCusPersonDebtInfo.getRelationToLoaner())){
					String cusName = mfCusCustomer.getCusName();
					mfCusPersonDebtInfo.setCusName(cusName);
				}

				mfCusPersonDebtInfoFeign.insert(mfCusPersonDebtInfo);
				
				// 更新资料完整度
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonDebtInfo.getCusNo(), mfCusPersonDebtInfo.getRelNo());
				
				String tableId = "tablecuspersdebt0001";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonDebtInfoAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				
				String cusNo = mfCusPersonDebtInfo.getCusNo();
				String relNo = mfCusPersonDebtInfo.getRelNo();
				mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				mfCusPersonDebtInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo", mfCusPersonDebtInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", (List<MfCusPersonDebtInfo>) mfCusPersonDebtInfoFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());

			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String formId = "";
		if (StringUtil.isEmpty(formId)) {
			formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonDebtInfoAction").getShowModel();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formcuspersdebt0002 = formService.getFormData(formId);
		getFormValue(formcuspersdebt0002, getMapByJson(ajaxData));
		MfCusPersonDebtInfo mfCusPersonDebtInfoJsp = new MfCusPersonDebtInfo();
		setObjValue(formcuspersdebt0002, mfCusPersonDebtInfoJsp);
		MfCusPersonDebtInfo mfCusPersonDebtInfo = mfCusPersonDebtInfoFeign.getById(mfCusPersonDebtInfoJsp);
		if (mfCusPersonDebtInfo != null) {
			try {
				mfCusPersonDebtInfo = (MfCusPersonDebtInfo) EntityUtil.reflectionSetVal(mfCusPersonDebtInfo,
						mfCusPersonDebtInfoJsp, getMapByJson(ajaxData));
				mfCusPersonDebtInfoFeign.update(mfCusPersonDebtInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonDebtInfoAction").getAddModel();
			}
			FormData formcuspersdebt0002 = formService.getFormData(formId);
			getFormValue(formcuspersdebt0002, map);

			if (this.validateFormData(formcuspersdebt0002)) {
				setObjValue(formcuspersdebt0002, mfCusPersonDebtInfo);
				mfCusPersonDebtInfoFeign.update(mfCusPersonDebtInfo);

				String cusNo = mfCusPersonDebtInfo.getCusNo();
				mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo", mfCusPersonDebtInfo));
				String tableHtml = jtu.getJsonStr("tablecusDebtListBase", "tableTag",
						(List<MfCusPersonDebtInfo>) mfCusPersonDebtInfoFeign.findByPage(ipage).getResult(), null, true);
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
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String debtId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcuspersdebt0002 = formService.getFormData("cuspersdebt0002");
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		mfCusPersonDebtInfo.setDebtId(debtId);
		mfCusPersonDebtInfo = mfCusPersonDebtInfoFeign.getById(mfCusPersonDebtInfo);
		getObjValue(formcuspersdebt0002, mfCusPersonDebtInfo, formData);
		if (mfCusPersonDebtInfo != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
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
	public Map<String, Object> deleteAjax(String debtId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		mfCusPersonDebtInfo.setDebtId(debtId);
		try {
			mfCusPersonDebtInfoFeign.delete(mfCusPersonDebtInfo);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo, String relNo, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersdebt0002 = null;
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		mfCusPersonDebtInfo.setCusNo(cusNo);
		mfCusPersonDebtInfo.setRelNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		// debtType = "debtType=1";
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonDebtInfoAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonDebtInfoAction表单信息没有查询到");
		} else {
			formcuspersdebt0002 = formService.getFormData(formId);
			getFormValue(formcuspersdebt0002);
			getObjValue(formcuspersdebt0002, mfCusPersonDebtInfo);
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("projectName", projectName);
		model.addAttribute("formcuspersdebt0002", formcuspersdebt0002);
		model.addAttribute("query", "");
		model.addAttribute("cusType", mfCusCustomer.getCusType());
		model.addAttribute("cusNo",cusNo);
		return "/component/cus/MfCusPersonDebtInfo_Insert";
	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersdebt0002 = null;
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		mfCusPersonDebtInfo.setCusNo(cusNo);
		mfCusPersonDebtInfo.setRelNo(relNo);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusPersonDebtInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+ kindNo
			// +"的MfCusPersonDebtInfoAction表单信息没有查询到");
		} else {
			formcuspersdebt0002 = formService.getFormData(formId);
			getFormValue(formcuspersdebt0002);
			getObjValue(formcuspersdebt0002, mfCusPersonDebtInfo);
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("projectName", projectName);
		model.addAttribute("formcuspersdebt0002", formcuspersdebt0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonDebtInfo_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersdebt0002 = formService.getFormData("cuspersdebt0002");
		getFormValue(formcuspersdebt0002);
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		setObjValue(formcuspersdebt0002, mfCusPersonDebtInfo);
		mfCusPersonDebtInfoFeign.insert(mfCusPersonDebtInfo);
		getObjValue(formcuspersdebt0002, mfCusPersonDebtInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo", mfCusPersonDebtInfo));
		List<MfCusPersonDebtInfo> mfCusPersonDebtInfoList = (List<MfCusPersonDebtInfo>) mfCusPersonDebtInfoFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcuspersdebt0002", formcuspersdebt0002);
		model.addAttribute("mfCusPersonDebtInfoList", mfCusPersonDebtInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonDebtInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String debtId,String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		String formId = "";

		mfCusPersonDebtInfo.setDebtId(debtId);
		mfCusPersonDebtInfo = mfCusPersonDebtInfoFeign.getById(mfCusPersonDebtInfo);
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		if(mfCusCustomer!=null){
			mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusPersonDebtInfoAction");
		}
		if(mfCusFormConfig!=null){
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			formId = "cuspersdebt0001";
		}
		FormData formcuspersdebt0001 = formService.getFormData(formId);
		getFormValue(formcuspersdebt0001);

		getObjValue(formcuspersdebt0001, mfCusPersonDebtInfo);
		model.addAttribute("formcuspersdebt0001", formcuspersdebt0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonDebtInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String debtId) throws Exception {
		ActionContext.initialize(request, response);
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		mfCusPersonDebtInfo.setDebtId(debtId);
		mfCusPersonDebtInfoFeign.delete(mfCusPersonDebtInfo);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersdebt0002 = formService.getFormData("cuspersdebt0002");
		getFormValue(formcuspersdebt0002);
		boolean validateFlag = this.validateFormData(formcuspersdebt0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersdebt0002 = formService.getFormData("cuspersdebt0002");
		getFormValue(formcuspersdebt0002);
		boolean validateFlag = this.validateFormData(formcuspersdebt0002);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String ajaxData, String cusNo, String debtId)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonDebtInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonDebtInfoAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcuspersdebt0003 = formService.getFormData(formId);
			MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
			mfCusPersonDebtInfo.setDebtId(debtId);
			mfCusPersonDebtInfo = mfCusPersonDebtInfoFeign.getById(mfCusPersonDebtInfo);
			getObjValue(formcuspersdebt0003, mfCusPersonDebtInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcuspersdebt0003, "propertySeeTag", "");
			if (mfCusPersonDebtInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusPersonDebtInfo);
		}
		return dataMap;
	}

	/**
	 * 根据负债类型获取对应的表单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDebtTypeFormAjax")
	@ResponseBody
	public Map<String, Object> getDebtTypeFormAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(ajaxData, Map.class);
		String cusNo = map.get("cusNo").toString();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonDebtInfoAction");
		String formId = "";
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonDebtInfoAction表单信息没有查询到");
			dataMap.put("msg", "获取表单失败");
			dataMap.put("flag", "error");
		} else {
			FormData formcuspersdebt0002 = formService.getFormData(formId);
			if (formcuspersdebt0002.getFormId() == null) {
				dataMap.put("msg", "获取表单失败");
				dataMap.put("flag", "error");
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonDebtInfoAction表单form"+formId+".xml文件不存在");
			} else {
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				mfCusPersonDebtInfo.setRelNo(cusNo);
				mfCusPersonDebtInfo.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcuspersdebt0002);
				getObjValue(formcuspersdebt0002, mfCusPersonDebtInfo);
				String htmlStr = jsonFormUtil.getJsonStr(formcuspersdebt0002, "bootstarpTag", "");
				dataMap.put("flag", "success");
				dataMap.put("formData", htmlStr);
				dataMap.put("formId", formId);
			}
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
	public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcuspersdebt0003 = formService.getFormData(formId);
		getFormValue(formcuspersdebt0003, getMapByJson(ajaxData));
		MfCusPersonDebtInfo mfCusPersonDebtInfoNew = new MfCusPersonDebtInfo();
		setObjValue(formcuspersdebt0003, mfCusPersonDebtInfoNew);
		mfCusPersonDebtInfo.setDebtId(mfCusPersonDebtInfoNew.getDebtId());
		mfCusPersonDebtInfo = mfCusPersonDebtInfoFeign.getById(mfCusPersonDebtInfo);
		if (mfCusPersonDebtInfo != null) {
			try {
				mfCusPersonDebtInfo = (MfCusPersonDebtInfo) EntityUtil.reflectionSetVal(mfCusPersonDebtInfo,
						mfCusPersonDebtInfoNew, getMapByJson(ajaxData));
				mfCusPersonDebtInfoFeign.update(mfCusPersonDebtInfo);
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

	/**
	 * 方法描述： 选择组件获取负债人的方法
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-8-21 上午10:55:08
	 */
	@RequestMapping(value = "/findPersonDebInfoByPage")
	@ResponseBody
	public Map<String, Object> findPersonDebInfoByPage(int pageNo,String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				mfCusPersonDebtInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			mfCusPersonDebtInfo.setCusNo(cusNo);
			ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo", mfCusPersonDebtInfo));
			ipage.setParamsStr(ajaxData);
			ipage = mfCusPersonDebtInfoFeign.findPersonDebInfoByPage(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
