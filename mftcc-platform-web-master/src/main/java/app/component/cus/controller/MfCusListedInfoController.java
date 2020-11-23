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
import app.component.cus.entity.MfCusListedInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusListedInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusListedInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 09 16:41:38 CST 2017
 **/
@Controller
@RequestMapping("/mfCusListedInfo")
public class MfCusListedInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusListedInfoBo
	@Autowired
	private MfCusListedInfoFeign mfCusListedInfoFeign;
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
		return "component/cus/MfCusListedInfo_List";
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
		MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
		try {
			mfCusListedInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusListedInfo.setCriteriaList(mfCusListedInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusListedInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusListedInfo", mfCusListedInfo));
			ipage = mfCusListedInfoFeign.findByPage(ipage);
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
			String formId = map.get("formId").toString();
			FormData formcuslistinfo0002 = formService.getFormData(formId);
			getFormValue(formcuslistinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcuslistinfo0002)) {
				MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
				setObjValue(formcuslistinfo0002, mfCusListedInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusListedInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusListedInfo.setCusName(cusName);
				mfCusListedInfoFeign.insert(mfCusListedInfo);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusListedInfo.getCusNo(),
						mfCusListedInfo.getRelNo());// 更新客户信息完整度
				JsonTableUtil jtu = new JsonTableUtil();
				String cusNo = mfCusListedInfo.getCusNo();
				String relNo = mfCusListedInfo.getRelNo();
				mfCusListedInfo = new MfCusListedInfo();
				mfCusListedInfo.setCusNo(cusNo);
				mfCusListedInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();

				ipage.setParams(this.setIpageParams("mfCusListedInfo", mfCusListedInfo));
				String tableHtml = jtu.getJsonStr("tablecuslistinfo0001", "tableTag",
						(List<MfCusListedInfo>) mfCusListedInfoFeign.findByPage(ipage).getResult(), null, true);
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
		FormData formcuslistinfo0002 = formService.getFormData("cuslistinfo0002");
		getFormValue(formcuslistinfo0002, getMapByJson(ajaxData));
		MfCusListedInfo mfCusListedInfoJsp = new MfCusListedInfo();
		setObjValue(formcuslistinfo0002, mfCusListedInfoJsp);
		MfCusListedInfo mfCusListedInfo = mfCusListedInfoFeign.getById(mfCusListedInfoJsp);
		if (mfCusListedInfo != null) {
			try {
				mfCusListedInfo = (MfCusListedInfo) EntityUtil.reflectionSetVal(mfCusListedInfo, mfCusListedInfoJsp,
						getMapByJson(ajaxData));
				mfCusListedInfoFeign.update(mfCusListedInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusListedInfoAction").getAddModel();
			}
			FormData formcuslistinfo0002 = formService.getFormData(formId);
			getFormValue(formcuslistinfo0002, map);
			if (this.validateFormData(formcuslistinfo0002)) {
				MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
				setObjValue(formcuslistinfo0002, mfCusListedInfo);
				mfCusListedInfoFeign.update(mfCusListedInfo);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusListedInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

				String tableId = "tablecuslistinfo0001";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusListedInfoAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}

				String cusNo = mfCusListedInfo.getCusNo();
				mfCusListedInfo = new MfCusListedInfo();
				mfCusListedInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusListedInfo", mfCusListedInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						(List<MfCusListedInfo>) mfCusListedInfoFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String listedId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcuslistinfo0002 = formService.getFormData("cuslistinfo0002");
		MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
		mfCusListedInfo.setListedId(listedId);
		mfCusListedInfo = mfCusListedInfoFeign.getById(mfCusListedInfo);
		getObjValue(formcuslistinfo0002, mfCusListedInfo, formData);
		if (mfCusListedInfo != null) {
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
	public Map<String, Object> deleteAjax(String listedId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
		mfCusListedInfo.setListedId(listedId);
		try {
			mfCusListedInfoFeign.delete(mfCusListedInfo);
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

	/**
	 * 新增页面
	 * 
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
		MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
		mfCusListedInfo.setCusNo(cusNo);
		mfCusListedInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusListedInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusListedInfoAction表单信息没有查询到");
		} else {
			FormData formcuslistinfo0002 = formService.getFormData(formId);
			if (formcuslistinfo0002.getFormId() == null) {
				// logger.error("客户类型为" + mfCusCustomer.getCusType() +
				// "的MfCusListedInfoAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcuslistinfo0002);
				getObjValue(formcuslistinfo0002, mfCusListedInfo);
			}
			model.addAttribute("formcuslistinfo0002", formcuslistinfo0002);
		}
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfCusFormConfig", mfCusFormConfig);
		model.addAttribute("mfCusListedInfo", mfCusListedInfo);
		model.addAttribute("formId", formId);
		model.addAttribute("query", "");
		return "component/cus/MfCusListedInfo_Insert";
	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
		mfCusListedInfo.setCusNo(cusNo);
		mfCusListedInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusListedInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为" + kindNo + "的MfCusListedInfoAction表单信息没有查询到");
		} else {
			FormData formcuslistinfo0002 = formService.getFormData(formId);
			if (formcuslistinfo0002.getFormId() == null) {
				// logger.error("产品类型为" + kindNo + "的MfCusListedInfoAction表单form" + formId +
				// ".xml文件不存在");
			} else {
				getFormValue(formcuslistinfo0002);
				getObjValue(formcuslistinfo0002, mfCusListedInfo);
			}
			model.addAttribute("formcuslistinfo0002", formcuslistinfo0002);
		}
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfCusFormConfig", mfCusFormConfig);
		model.addAttribute("mfCusListedInfo", mfCusListedInfo);
		model.addAttribute("formId", formId);
		model.addAttribute("query", "");
		return "component/cus/MfCusListedInfo_Insert";
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
		FormData formcuslistinfo0002 = formService.getFormData("cuslistinfo0002");
		getFormValue(formcuslistinfo0002);
		MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
		setObjValue(formcuslistinfo0002, mfCusListedInfo);
		mfCusListedInfoFeign.insert(mfCusListedInfo);
		getObjValue(formcuslistinfo0002, mfCusListedInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusListedInfo", mfCusListedInfo));
		List<MfCusListedInfo> mfCusListedInfoList = (List<MfCusListedInfo>) mfCusListedInfoFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formcuslistinfo0002", formcuslistinfo0002);
		model.addAttribute("mfCusListedInfo", mfCusListedInfo);
		model.addAttribute("mfCusListedInfoList", mfCusListedInfoList);
		model.addAttribute("query", "");
		return "component/cus/MfCusListedInfo_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String listedId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuslistinfo0002 = formService.getFormData("cuslistinfo0002");
		MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
		mfCusListedInfo.setListedId(listedId);
		mfCusListedInfo = mfCusListedInfoFeign.getById(mfCusListedInfo);
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusListedInfo.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusListedInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			if ("1".equals(mfCusFormConfig.getShowType())) {
				formId = mfCusFormConfig.getShowModelDef();
			} else {
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if (StringUtil.isEmpty(formId)) {
		} else {
			formcuslistinfo0002 = formService.getFormData(formId);
			if (formcuslistinfo0002.getFormId() == null) {
			} else {
				getFormValue(formcuslistinfo0002);
				getObjValue(formcuslistinfo0002, mfCusListedInfo);
			}
		}
		model.addAttribute("formcuslistinfo0002", formcuslistinfo0002);
		model.addAttribute("mfCusListedInfo", mfCusListedInfo);
		model.addAttribute("query", "");
		return "component/cus/MfCusListedInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String listedId) throws Exception {
		ActionContext.initialize(request, response);
		MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
		mfCusListedInfo.setListedId(listedId);
		mfCusListedInfoFeign.delete(mfCusListedInfo);
		return getListPage(model);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String listedId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusListedInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusListedInfoAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcuslistinfo0003 = formService.getFormData(formId);
			MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
			mfCusListedInfo.setListedId(listedId);
			mfCusListedInfo = mfCusListedInfoFeign.getById(mfCusListedInfo);
			getObjValue(formcuslistinfo0003, mfCusListedInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcuslistinfo0003, "propertySeeTag", "");
			if (mfCusListedInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusListedInfo);
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
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
		MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcuslistinfo0003 = formService.getFormData(formId);
		getFormValue(formcuslistinfo0003, getMapByJson(ajaxData));
		MfCusListedInfo mfCusListedInfoNew = new MfCusListedInfo();
		setObjValue(formcuslistinfo0003, mfCusListedInfoNew);
		mfCusListedInfo.setListedId(mfCusListedInfoNew.getListedId());
		mfCusListedInfo = mfCusListedInfoFeign.getById(mfCusListedInfo);
		if (mfCusListedInfo != null) {
			try {
				mfCusListedInfo = (MfCusListedInfo) EntityUtil.reflectionSetVal(mfCusListedInfo, mfCusListedInfoNew,
						getMapByJson(ajaxData));
				mfCusListedInfoFeign.update(mfCusListedInfo);
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
		FormData formcuslistinfo0002 = formService.getFormData("cuslistinfo0002");
		getFormValue(formcuslistinfo0002);
		boolean validateFlag = this.validateFormData(formcuslistinfo0002);
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
		FormData formcuslistinfo0002 = formService.getFormData("cuslistinfo0002");
		getFormValue(formcuslistinfo0002);
		boolean validateFlag = this.validateFormData(formcuslistinfo0002);
	}

}
