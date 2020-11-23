package app.component.cus.gualoan.controller;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.entity.MfCusGuaLoanOuterSum;
import app.component.cus.gualoan.feign.MfCusGuaLoanOuterSumFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import org.apache.commons.beanutils.PropertyUtils;
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
@RequestMapping("/mfCusGuaLoanOuterSum")
public class MfCusGuaLoanOuterSumController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusGuaLoanOuterSumFeign mfCusGuaLoanOuterSumFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	//全局变量
	private String query = "";//初始化query为空
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
		return "/component/cus/gualoan/MfCusGuaLoanOuterSum_List";
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
		MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum = new MfCusGuaLoanOuterSum();
		try {
			mfCusGuaLoanOuterSum.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusGuaLoanOuterSum.setCriteriaList(mfCusGuaLoanOuterSum, ajaxData);// 我的筛选
			// mfCusGuaLoanOuterSum.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusGuaLoanOuterSum,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusGuaLoanOuterSum", mfCusGuaLoanOuterSum));
			// 自定义查询Bo方法
			ipage = mfCusGuaLoanOuterSumFeign.findByPage(ipage);
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
		MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum = new MfCusGuaLoanOuterSum();
		mfCusGuaLoanOuterSum.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusGuaLoanOuterSumAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formmfgualoan0001 = formService.getFormData(formId);
			if (formmfgualoan0001.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else {
				getFormValue(formmfgualoan0001);
				mfCusGuaLoanOuterSum.setCusName(mfCusCustomer.getCusName());
				getObjValue(formmfgualoan0001, mfCusGuaLoanOuterSum);
			}
			model.addAttribute("formmfgualoan0001", formmfgualoan0001);
		}
		// model.addAttribute("cusName", mfCusCustomer.getCusName());
		// model.addAttribute("cusNo", mfCusCustomer.getCusNo());
		model.addAttribute("query", "");
		return "/component/cus/gualoan/MfCusGuaLoanOuterSum_Insert";
	}

	/**
	 * AJAX新增
	 * 
	 * @param startFlowFlag
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusGuaLoanOuterSumAction").getAddModel();
			}
			FormData mfgualoan0001 = formService.getFormData(formId);

			// FormData mfgualoan0001 =
			// formService.getFormData("cusGualoanBase");
			// FormData mfgualoandetail =
			// formService.getFormData("cusGualoanDetail");
			getFormValue(mfgualoan0001, getMapByJson(ajaxData));
			if (this.validateFormData(mfgualoan0001)) {
				MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum = new MfCusGuaLoanOuterSum();
				mfCusGuaLoanOuterSum.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(mfgualoan0001, mfCusGuaLoanOuterSum);
				if (null != mfCusGuaLoanOuterSum.getGuaranteeBal() && null != mfCusGuaLoanOuterSum.getGuaranteeAmt()
						&& mfCusGuaLoanOuterSum.getGuaranteeBal() > mfCusGuaLoanOuterSum.getGuaranteeAmt()) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "担保余额不能大于担保总额");
				} else if (null != mfCusGuaLoanOuterSum.getLoanBal() && null != mfCusGuaLoanOuterSum.getLoanAmt()
						&& mfCusGuaLoanOuterSum.getLoanBal() > mfCusGuaLoanOuterSum.getLoanAmt()) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "贷款余额不能大于贷款总额");
				} else {
					dataMap = mfCusGuaLoanOuterSumFeign.insert(mfCusGuaLoanOuterSum);
					String cusNo = mfCusGuaLoanOuterSum.getCusNo();
					String relNo = mfCusGuaLoanOuterSum.getCusNo();
					request.setAttribute("ifBizManger", "3");

					MfCusCustomer mfCusCustomer = new MfCusCustomer();
					mfCusCustomer.setCusNo(mfCusGuaLoanOuterSum.getCusNo());
					mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
					MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
							"MfCusGuaLoanOuterSumAction");
					if (mfCusFormConfig == null) {

					} else {
						formId = mfCusFormConfig.getShowModelDef();
					}
					FormData mfgualoandetail = formService.getFormData(formId);
					if (mfgualoandetail.getFormId() == null) {
						// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpBaseInfoAction表单form"+formId+".xml文件不存在");
					}

					getFormValue(mfgualoandetail);
					getObjValue(mfgualoandetail, mfCusGuaLoanOuterSum);
					JsonFormUtil jsonFormUtil = new JsonFormUtil();
					String htmlStr = jsonFormUtil.getJsonStr(mfgualoandetail, "propertySeeTag", "");
					String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo, relNo);// 更新资料完整度
					dataMap.put("mfCusGuaLoanOuterSum", mfCusGuaLoanOuterSum);
					dataMap.put("htmlStr", htmlStr);
					dataMap.put("DataFullFlag", "1");
					dataMap.put("infIntegrity", infIntegrity);
					dataMap.put("htmlStrFlag", "1");

					dataMap.put("flag", "success");
					dataMap.put("msg", "新增成功");
				}
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
	 * 查询
	 * @return
	 */
	@RequestMapping("/getById")
	public String  getById(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusGuaLoanOuterSumAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			//FormData  formmfgualoan0001 = formService.getFormData("mfgualoan0001");
			FormData formmfgualoan0001 = formService.getFormData(formId);
			getFormValue(formmfgualoan0001);

			MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum = new MfCusGuaLoanOuterSum();
			mfCusGuaLoanOuterSum.setCusNo(cusNo);
			mfCusGuaLoanOuterSum  = mfCusGuaLoanOuterSumFeign.getById(mfCusGuaLoanOuterSum);
			getObjValue(formmfgualoan0001, mfCusGuaLoanOuterSum);

			model.addAttribute("formmfgualoan0001", formmfgualoan0001);

			model.addAttribute("query", "");
		}
		return "/component/cus/gualoan/MfCusGuaLoanOuterSum_Detail";
	}

	/**
	 * ajax更新保存
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusNo = (String) getMapByJson(ajaxData).get("cusNo");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String formId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusGuaLoanOuterSumAction")
				.getShowModelDef();
		if (StringUtil.isEmpty(formId)) {
			formId = mfCusFormConfigFeign.getByCusType("base", "MfCusGuaLoanOuterSumAction").getShowModelDef();
		}
		FormData formcusGualoanDetail = formService.getFormData(formId);
		getFormValue(formcusGualoanDetail, getMapByJson(ajaxData));
		MfCusGuaLoanOuterSum mfCusGuaLoanOuterSumJsp = new MfCusGuaLoanOuterSum();
		setObjValue(formcusGualoanDetail, mfCusGuaLoanOuterSumJsp);
		MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum = mfCusGuaLoanOuterSumFeign.getById(mfCusGuaLoanOuterSumJsp);
		if (mfCusGuaLoanOuterSum != null) {
			try {
				mfCusGuaLoanOuterSum = (MfCusGuaLoanOuterSum) EntityUtil.reflectionSetVal(mfCusGuaLoanOuterSum,
						mfCusGuaLoanOuterSumJsp, getMapByJson(ajaxData));
				PropertyUtils.copyProperties(mfCusCustomer, mfCusGuaLoanOuterSum);
				mfCusGuaLoanOuterSumFeign.update(mfCusGuaLoanOuterSum);

				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusGualoanDetail,"propertySeeTag",query);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
		String cusNo = (String) getMapByJson(ajaxData).get("cusNo");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String formId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusGuaLoanOuterSumAction")
				.getShowModelDef();
		if (StringUtil.isEmpty(formId)) {
			formId = mfCusFormConfigFeign.getByCusType("base", "MfCusGuaLoanOuterSumAction").getShowModel();
		}
		FormData formcusper00002 = formService.getFormData(formId);
		getFormValue(formcusper00002, getMapByJson(ajaxData));
		MfCusGuaLoanOuterSum mfCusGuaLoanOuterSumJsp = new MfCusGuaLoanOuterSum();
		setObjValue(formcusper00002, mfCusGuaLoanOuterSumJsp);
		MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum = mfCusGuaLoanOuterSumFeign.getById(mfCusGuaLoanOuterSumJsp);
		if (mfCusGuaLoanOuterSum != null) {
			try {
				mfCusGuaLoanOuterSum = (MfCusGuaLoanOuterSum) EntityUtil.reflectionSetVal(mfCusGuaLoanOuterSum,
						mfCusGuaLoanOuterSumJsp, getMapByJson(ajaxData));
				PropertyUtils.copyProperties(mfCusCustomer, mfCusGuaLoanOuterSum);
				mfCusGuaLoanOuterSumFeign.update(mfCusGuaLoanOuterSum);
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

}
