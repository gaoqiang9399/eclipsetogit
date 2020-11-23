package app.component.assureamt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.assureamt.entity.MfBusAssureAmt;
import app.component.assureamt.feign.MfBusAssureAmtFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfBusTrenchFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfBusAssureAmtAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 11 16:08:36 CST 2018
 **/
@Controller
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
@RequestMapping("/mfBusAssureAmt")
public class MfBusAssureAmtController extends BaseFormBean {
	@Autowired
	private MfBusAssureAmtFeign mfBusAssureAmtFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfBusTrenchFeign mfBusTrenchFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/assureamt/MfBusAssureAmt_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @param ajaxData
	 * @param pageNo
	 * @param pageSize
	 * @param tableId
	 * @param tableType
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAssureAmt mfBusAssureAmt = new MfBusAssureAmt();
		try {
			mfBusAssureAmt.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusAssureAmt.setCriteriaList(mfBusAssureAmt, ajaxData);// 我的筛选
			mfBusAssureAmt.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusAssureAmt", mfBusAssureAmt));
			ipage = mfBusAssureAmtFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo((String) map.get("cusNo"));
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			if (StringUtil.isEmpty(formId)) {
				String cusType = mfCusCustomer.getCusType();
				formId = mfCusFormConfigFeign.getByCusType(cusType, "MfBusAssureAmtAction").getAddModel();
			}
			FormData formassureamtbase = formService.getFormData(formId);
			getFormValue(formassureamtbase, getMapByJson(ajaxData));
			if (this.validateFormData(formassureamtbase)) {
				MfBusAssureAmt mfBusAssureAmt = new MfBusAssureAmt();
				setObjValue(formassureamtbase, mfBusAssureAmt);
				mfBusAssureAmt.setCusName(mfCusCustomer.getCusName());
				mfBusAssureAmt = mfBusAssureAmtFeign.insert(mfBusAssureAmt);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfBusAssureAmt.getCusNo(),
						mfBusAssureAmt.getCusNo());// 更新客户信息完整度

				String tableId = "tableassureamtlist";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfBusAssureAmtAction");
				if (mfCusFormConfig != null) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfBusAssureAmt", mfBusAssureAmt));
				String htmlStr = jtu.getJsonStr(tableId, "tableTag",
						(List<MfBusAssureAmt>) mfBusAssureAmtFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", htmlStr);
				MfBusTrench mfBusTrench = new MfBusTrench();
				mfBusTrench.setTrenchUid(mfCusCustomer.getCusNo());
				mfBusTrench = mfBusTrenchFeign.getById(mfBusTrench);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				formId = "trenchBaseDetail";
				mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfBusTrenchAction");
				if (mfCusFormConfig != null) {
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formcommon = formService.getFormData(formId);
				getFormValue(formcommon);
				getObjValue(formcommon, mfBusTrench);
				request.setAttribute("ifBizManger", "3");
				String basehtmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
				dataMap.put("basehtmlStr", basehtmlStr);
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassureamtbase = formService.getFormData("assureamtbase");
		getFormValue(formassureamtbase, getMapByJson(ajaxData));
		MfBusAssureAmt mfBusAssureAmtJsp = new MfBusAssureAmt();
		setObjValue(formassureamtbase, mfBusAssureAmtJsp);
		MfBusAssureAmt mfBusAssureAmt = mfBusAssureAmtFeign.getById(mfBusAssureAmtJsp);
		if (mfBusAssureAmt != null) {
			try {
				mfBusAssureAmt = (MfBusAssureAmt) EntityUtil.reflectionSetVal(mfBusAssureAmt, mfBusAssureAmtJsp,
						getMapByJson(ajaxData));
				mfBusAssureAmtFeign.update(mfBusAssureAmt);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
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
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAssureAmt mfBusAssureAmt = new MfBusAssureAmt();
		try {
			FormData formassureamtbase = formService.getFormData("assureamtbase");
			getFormValue(formassureamtbase, getMapByJson(ajaxData));
			if (this.validateFormData(formassureamtbase)) {
				mfBusAssureAmt = new MfBusAssureAmt();
				setObjValue(formassureamtbase, mfBusAssureAmt);
				mfBusAssureAmtFeign.update(mfBusAssureAmt);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param assureAmtId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String assureAmtId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassureamtbase = formService.getFormData("assureamtbase");
		MfBusAssureAmt mfBusAssureAmt = new MfBusAssureAmt();
		mfBusAssureAmt.setAssureAmtId(assureAmtId);
		mfBusAssureAmt = mfBusAssureAmtFeign.getById(mfBusAssureAmt);
		getObjValue(formassureamtbase, mfBusAssureAmt, formData);
		if (mfBusAssureAmt != null) {
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
	 * @param assureAmtId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String assureAmtId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAssureAmt mfBusAssureAmt = new MfBusAssureAmt();
		mfBusAssureAmt.setAssureAmtId(assureAmtId);
		try {
			mfBusAssureAmtFeign.delete(mfBusAssureAmt);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String cusNo, String relNo) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfBusAssureAmtController.class);
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassureamtbase = null;
		String cusType = "";
		String formId = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfBusAssureAmtAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			logger.error("客户类型为" + cusType + "的MfBusAssureAmtAction表单信息没有查询到");
		} else {
			formassureamtbase = formService.getFormData(formId);
			if (formassureamtbase.getFormId() == null) {
				logger.error("客户类型为" + cusType + "的MfBusAssureAmtAction表单form" + formId + ".xml文件不存在");
			} else {
				MfBusTrench mfBusTrench = new MfBusTrench();
				mfBusTrench.setTrenchUid(cusNo);
				mfBusTrench = mfBusTrenchFeign.getByUId(mfBusTrench);
				MfBusAssureAmt mfBusAssureAmt = new MfBusAssureAmt();
				mfBusAssureAmt.setCusNo(cusNo);
				mfBusAssureAmt.setLstModTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
				Double assureBal = mfBusTrench.getAssureBal();
				if (assureBal == null) {
					assureBal = 0.00;
				}
				mfBusAssureAmt.setBalAmt(assureBal);
				mfBusAssureAmt.setBalAmtCalc(assureBal);
				getObjValue(formassureamtbase, mfBusAssureAmt);
			}
		}
		model.addAttribute("formassureamtbase", formassureamtbase);
		model.addAttribute("query", "");
		return "/component/assureamt/MfBusAssureAmt_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param assureAmtId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String assureAmtId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassureamtdetail = formService.getFormData("assureamtdetail");
		getFormValue(formassureamtdetail);
		MfBusAssureAmt mfBusAssureAmt = new MfBusAssureAmt();
		mfBusAssureAmt.setAssureAmtId(assureAmtId);
		mfBusAssureAmt = mfBusAssureAmtFeign.getById(mfBusAssureAmt);
		getObjValue(formassureamtdetail, mfBusAssureAmt);
		model.addAttribute("formassureamtdetail", formassureamtdetail);
		model.addAttribute("query", "");
		return "/component/assureamt/MfBusAssureAmt_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassureamtbase = formService.getFormData("assureamtbase");
		getFormValue(formassureamtbase);
		boolean validateFlag = this.validateFormData(formassureamtbase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassureamtbase = formService.getFormData("assureamtbase");
		getFormValue(formassureamtbase);
		boolean validateFlag = this.validateFormData(formassureamtbase);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String assureAmtId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Logger logger = LoggerFactory.getLogger(MfBusAssureAmtController.class);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfBusAssureAmtAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfBusAssureAmtAction表单信息没有查询到");
			dataMap.put("msg", "客户类型为" + mfCusCustomer.getCusType() + "的MfBusAssureAmtAction表单信息没有查询到");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
//			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formassureamtdetail = new FormService().getFormData(formId);
			MfBusAssureAmt mfBusAssureAmt = new MfBusAssureAmt();
			mfBusAssureAmt.setAssureAmtId(assureAmtId);
			mfBusAssureAmt = mfBusAssureAmtFeign.getById(mfBusAssureAmt);
			mfBusAssureAmt.setLstModTime(DateUtil.getShowDateTime(mfBusAssureAmt.getLstModTime()));
			getObjValue(formassureamtdetail, mfBusAssureAmt, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formassureamtdetail, "propertySeeTag", "query");
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
			dataMap.put("formData", mfBusAssureAmt);
		}

		return dataMap;
	}
}
