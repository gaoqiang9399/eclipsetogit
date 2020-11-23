package app.component.cus.controller;

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
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusLegalEmployInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusLegalEmployInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusLegalEmployInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 03 17:52:29 CST 2017
 **/
@Controller
@RequestMapping("/mfCusLegalEmployInfo")
public class MfCusLegalEmployInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusLegalEmployInfoBo
	@Autowired
	private MfCusLegalEmployInfoFeign mfCusLegalEmployInfoFeign;
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
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/cus/MfCusLegalEmployInfo_List";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		try {
			mfCusLegalEmployInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusLegalEmployInfo.setCriteriaList(mfCusLegalEmployInfo, ajaxData);// 我的筛选
			// mfCusLegalEmployInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusLegalEmployInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusLegalEmployInfo", mfCusLegalEmployInfo));
			ipage = mfCusLegalEmployInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusEquityInfoAction").getAddModel();
			}
			FormData formcusemploy00001 = formService.getFormData(formId);
			getFormValue(formcusemploy00001, map);
			if (this.validateFormData(formcusemploy00001)) {
				MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
				setObjValue(formcusemploy00001, mfCusLegalEmployInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusLegalEmployInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusLegalEmployInfo.setCusName(cusName);
				mfCusLegalEmployInfoFeign.insert(mfCusLegalEmployInfo);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusLegalEmployInfo.getCusNo(),
						mfCusLegalEmployInfo.getRelNo());// 更新客户信息完整度
				String relNo = mfCusLegalEmployInfo.getRelNo();
				mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
				mfCusLegalEmployInfo.setCusNo(mfCusCustomer.getCusNo());
				mfCusLegalEmployInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusLegalEmployInfo", mfCusLegalEmployInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tablecusemploy00001", "tableTag",
						(List<MfCusLegalEmployInfo>) mfCusLegalEmployInfoFeign.findByPage(ipage)
								.getResult(),
						null, true);
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
		FormData formcusemploy00002 = formService.getFormData("cusemploy00002");
		getFormValue(formcusemploy00002, getMapByJson(ajaxData));
		MfCusLegalEmployInfo mfCusLegalEmployInfoJsp = new MfCusLegalEmployInfo();
		setObjValue(formcusemploy00002, mfCusLegalEmployInfoJsp);
		MfCusLegalEmployInfo mfCusLegalEmployInfo = mfCusLegalEmployInfoFeign.getById(mfCusLegalEmployInfoJsp);
		if (mfCusLegalEmployInfo != null) {
			try {
				mfCusLegalEmployInfo = (MfCusLegalEmployInfo) EntityUtil.reflectionSetVal(mfCusLegalEmployInfo,
						mfCusLegalEmployInfoJsp, getMapByJson(ajaxData));
				mfCusLegalEmployInfoFeign.update(mfCusLegalEmployInfo);
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
		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		try {

			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusLegalEquityInfoAction").getAddModel();
			}
			FormData formcusemploy00001 = formService.getFormData(formId);

			getFormValue(formcusemploy00001, map);
			if (this.validateFormData(formcusemploy00001)) {
				setObjValue(formcusemploy00001, mfCusLegalEmployInfo);
				mfCusLegalEmployInfoFeign.update(mfCusLegalEmployInfo);

				String cusNo = mfCusLegalEmployInfo.getCusNo();
				mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
				mfCusLegalEmployInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusLegalEmployInfo", mfCusLegalEmployInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tablecusCorpRepServeListBase", "tableTag",
						(List<MfCusLegalEmployInfo>) mfCusLegalEmployInfoFeign.findByPage(ipage)
								.getResult(),
						null, true);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusemploy00002 = formService.getFormData("cusemploy00002");
		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		mfCusLegalEmployInfo.setId(id);
		mfCusLegalEmployInfo = mfCusLegalEmployInfoFeign.getById(mfCusLegalEmployInfo);
		getObjValue(formcusemploy00002, mfCusLegalEmployInfo, formData);
		if (mfCusLegalEmployInfo != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		mfCusLegalEmployInfo.setId(id);
		try {
			mfCusLegalEmployInfoFeign.delete(mfCusLegalEmployInfo);
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
	 * @param cusNo 
	 * @param relNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusemploy00001 = formService.getFormData("cusemploy00001");

		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		mfCusLegalEmployInfo.setCusNo(cusNo);
		mfCusLegalEmployInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		mfCusLegalEmployInfo.setCusName(mfCusCustomer.getCusName());
		mfCusLegalEmployInfo.setCusNo(mfCusCustomer.getCusNo());
		mfCusLegalEmployInfo.setIsLegal("1");
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusLegalEmployInfoAction");
		String formId =null;
		if (mfCusFormConfig == null) {

		} else {
			formId  = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusLegalEmployInfoAction表单信息没有查询到");
		} else {
			formcusemploy00001 = formService.getFormData(formId);
			if (formcusemploy00001.getFormId() == null) {
				//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusLegalEmployInfoAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusemploy00001);
				getObjValue(formcusemploy00001, mfCusLegalEmployInfo);
			}
		}
		model.addAttribute("formcusemploy00001", formcusemploy00001);
		model.addAttribute("formId", formId);
		model.addAttribute("mfCusLegalEmployInfo", mfCusLegalEmployInfo);
		model.addAttribute("mfCusFormConfig", mfCusFormConfig);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("query", "");
		return "/component/cus/MfCusLegalEmployInfo_Insert";

	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String ajaxData, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusemploy00001 = formService.getFormData("cusemploy00001");

		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		mfCusLegalEmployInfo.setCusNo(cusNo);
		mfCusLegalEmployInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		mfCusLegalEmployInfo.setCusName(mfCusCustomer.getCusName());
		mfCusLegalEmployInfo.setCusNo(mfCusCustomer.getCusNo());
		mfCusLegalEmployInfo.setIsLegal("1");
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusLegalEmployInfoAction");
		String formId =null;
		if (mfCusFormConfig == null) {

		} else {
			formId  = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			//logger.error("产品类型为" + kindNo + "的MfCusLegalEmployInfoAction表单信息没有查询到");
		} else {
			formcusemploy00001 = formService.getFormData(formId);
			if (formcusemploy00001.getFormId() == null) {
				//logger.error("产品类型为" + kindNo + "的MfCusLegalEmployInfoAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusemploy00001);
				getObjValue(formcusemploy00001, mfCusLegalEmployInfo);
			}
		}
		model.addAttribute("formcusemploy00001", formcusemploy00001);
		model.addAttribute("formId", formId);
		model.addAttribute("mfCusLegalEmployInfo", mfCusLegalEmployInfo);
		model.addAttribute("mfCusFormConfig", mfCusFormConfig);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("query", "");
		return "/component/cus/MfCusLegalEmployInfo_Insert";

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
		FormData formcusemploy00002 = formService.getFormData("cusemploy00002");
		getFormValue(formcusemploy00002);
		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		setObjValue(formcusemploy00002, mfCusLegalEmployInfo);
		mfCusLegalEmployInfoFeign.insert(mfCusLegalEmployInfo);
		getObjValue(formcusemploy00002, mfCusLegalEmployInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusLegalEmployInfo", mfCusLegalEmployInfo));
		List<MfCusLegalEmployInfo> mfCusLegalEmployInfoList = (List<MfCusLegalEmployInfo>) mfCusLegalEmployInfoFeign
				.findByPage(ipage).getResult();
		model.addAttribute("mfCusLegalEmployInfoList", mfCusLegalEmployInfoList);
		model.addAttribute("mfCusLegalEmployInfo", mfCusLegalEmployInfo);
		model.addAttribute("formcusemploy00002", formcusemploy00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusLegalEmployInfo_Insert";
	}

	/**
	 * 查询
	 * @param id 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusemploy00001 = formService.getFormData("cusemploy00001");
		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		mfCusLegalEmployInfo.setId(id);
		mfCusLegalEmployInfo = mfCusLegalEmployInfoFeign.getById(mfCusLegalEmployInfo);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusLegalEmployInfo.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusLegalEmployInfoAction");
		String formId =null;
		if (mfCusFormConfig == null) {

		} else {
			if ("1".equals(mfCusFormConfig.getShowType())) {
				formId  = mfCusFormConfig.getShowModelDef();
			} else {
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if (StringUtil.isEmpty(formId)) {
			//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusLegalEquityInfoAction表单信息没有查询到");
		} else {
			formcusemploy00001 = formService.getFormData(formId);
			if (formcusemploy00001.getFormId() == null) {
				//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusLegalEquityInfoAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusemploy00001);
				getObjValue(formcusemploy00001, mfCusLegalEmployInfo);
			}
		}

		model.addAttribute("formcusemploy00001", formcusemploy00001);
		model.addAttribute("formId", formId);
		model.addAttribute("mfCusLegalEmployInfo", mfCusLegalEmployInfo);
		model.addAttribute("mfCusFormConfig", mfCusFormConfig);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("query", "");
		return "/component/cus/MfCusLegalEmployInfo_Detail";
	}

	/**
	 * 删除
	 * @param id 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,  String id) throws Exception {
		ActionContext.initialize(request, response);
		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		mfCusLegalEmployInfo.setId(id);
		mfCusLegalEmployInfoFeign.delete(mfCusLegalEmployInfo);
		return getListPage(model);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusLegalEmployInfoAction");
		String formId =null;
		if (mfCusFormConfig == null) {

		} else {
			formId  = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusLegalEmployInfoAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusemploy00003 = formService.getFormData(formId);
			MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
			mfCusLegalEmployInfo.setId(id);
			mfCusLegalEmployInfo = mfCusLegalEmployInfoFeign.getById(mfCusLegalEmployInfo);
			getObjValue(formcusemploy00003, mfCusLegalEmployInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusemploy00003, "propertySeeTag", "");
			if (mfCusLegalEmployInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusLegalEmployInfo);
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @param formId 
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
		MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusemploy00003 = formService.getFormData(formId);
		getFormValue(formcusemploy00003, getMapByJson(ajaxData));
		MfCusLegalEmployInfo mfCusLegalEmployInfoNew = new MfCusLegalEmployInfo();
		setObjValue(formcusemploy00003, mfCusLegalEmployInfoNew);
		mfCusLegalEmployInfo.setId(mfCusLegalEmployInfoNew.getId());
		mfCusLegalEmployInfo = mfCusLegalEmployInfoFeign.getById(mfCusLegalEmployInfo);
		if (mfCusLegalEmployInfo != null) {
			try {
				mfCusLegalEmployInfo = (MfCusLegalEmployInfo) EntityUtil.reflectionSetVal(mfCusLegalEmployInfo,
						mfCusLegalEmployInfoNew, getMapByJson(ajaxData));
				mfCusLegalEmployInfoFeign.update(mfCusLegalEmployInfo);
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
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusemploy00002 = formService.getFormData("cusemploy00002");
		getFormValue(formcusemploy00002);
		boolean validateFlag = this.validateFormData(formcusemploy00002);
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
		FormData formcusemploy00002 = formService.getFormData("cusemploy00002");
		getFormValue(formcusemploy00002);
		boolean validateFlag = this.validateFormData(formcusemploy00002);
	}

}
