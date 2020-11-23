package app.component.cus.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusGuaranteeOuter;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusGuaranteeOuterFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import config.YmlConfig;
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

/**
 * Title: MfCusGuaranteeOuterAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Feb 07 10:36:26 CST 2017
 **/
@Controller
@RequestMapping("/mfCusGuaranteeOuter")
public class MfCusGuaranteeOuterController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusGuaranteeOuterBo
	@Autowired
	private MfCusGuaranteeOuterFeign mfCusGuaranteeOuterFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private YmlConfig ymlConfig;
	// 全局变量
	// 异步参数
	// 表单变量

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
		return "/component/cus/MfCusGuaranteeOuter_List";
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
		MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
		try {
			mfCusGuaranteeOuter.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusGuaranteeOuter.setCriteriaList(mfCusGuaranteeOuter, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusGuaranteeOuter,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusGuaranteeOuter", mfCusGuaranteeOuter));
			// 自定义查询Bo方法
			ipage = mfCusGuaranteeOuterFeign.findByPage(ipage);
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
			FormData formcusguaranteeouter0002 = formService.getFormData(formId);
			getFormValue(formcusguaranteeouter0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusguaranteeouter0002)) {
				MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
				setObjValue(formcusguaranteeouter0002, mfCusGuaranteeOuter);
				String balanceOut = (String)map.get("balanceOut");//贷款利息
				String loanTime = (String)map.get("loanTime");//贷款期限
                if(StringUtil.isNotEmpty(balanceOut)){
                    mfCusGuaranteeOuter.setBalanceOut(Double.valueOf(balanceOut));
                }
                if(StringUtil.isNotEmpty(loanTime))
                {
                    mfCusGuaranteeOuter.setLoanTime(Integer.valueOf(loanTime));
                }
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusGuaranteeOuter.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusGuaranteeOuter.setCusName(cusName);
				mfCusGuaranteeOuterFeign.insert(mfCusGuaranteeOuter);

				// 更新客户信息完整度
				String cusNo = mfCusGuaranteeOuter.getCusNo();
				String relNo = mfCusGuaranteeOuter.getRelNo();
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo, relNo);
				
				String tableId = "tablecusguaranteeouter0001";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusGuaranteeOuterAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				
				mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
				mfCusGuaranteeOuter.setCusNo(cusNo);
				mfCusGuaranteeOuter.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusGuaranteeOuter", mfCusGuaranteeOuter));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", (List<MfCusGuaranteeOuter>) mfCusGuaranteeOuterFeign.findByPage(ipage).getResult(), null, true);
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
		FormData formcusguaranteeouter0002 = formService.getFormData("cusguaranteeouter0002");
		getFormValue(formcusguaranteeouter0002, getMapByJson(ajaxData));
		MfCusGuaranteeOuter mfCusGuaranteeOuterJsp = new MfCusGuaranteeOuter();
		setObjValue(formcusguaranteeouter0002, mfCusGuaranteeOuterJsp);
		MfCusGuaranteeOuter mfCusGuaranteeOuter = mfCusGuaranteeOuterFeign.getById(mfCusGuaranteeOuterJsp);
		if (mfCusGuaranteeOuter != null) {
			try {
				mfCusGuaranteeOuter = (MfCusGuaranteeOuter) EntityUtil.reflectionSetVal(mfCusGuaranteeOuter,
						mfCusGuaranteeOuterJsp, getMapByJson(ajaxData));
				mfCusGuaranteeOuterFeign.update(mfCusGuaranteeOuter);
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
		MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusGuaranteeOuterAction").getAddModel();
			}
			FormData formcusguaranteeouter0002 = formService.getFormData(formId);

			getFormValue(formcusguaranteeouter0002, map);

			if (this.validateFormData(formcusguaranteeouter0002)) {
				mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
				setObjValue(formcusguaranteeouter0002, mfCusGuaranteeOuter);

				String balanceOut = (String)map.get("balanceOut");//贷款利息
				String loanTime = (String)map.get("loanTime");//贷款期限
				if(StringUtil.isNotEmpty(balanceOut)){
					mfCusGuaranteeOuter.setBalanceOut(Double.valueOf(balanceOut));
				}
				if(StringUtil.isNotEmpty(loanTime))
				{
					mfCusGuaranteeOuter.setLoanTime(Integer.valueOf(loanTime));
				}

				mfCusGuaranteeOuterFeign.update(mfCusGuaranteeOuter);

				String cusNo = mfCusGuaranteeOuter.getCusNo();
				//从mf_cus_form_config中读取tableId
				String tableId = "";
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusGuaranteeOuterAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}

				mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
				mfCusGuaranteeOuter.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusGuaranteeOuter", mfCusGuaranteeOuter));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						(List<MfCusGuaranteeOuter>) mfCusGuaranteeOuterFeign.findByPage(ipage).getResult(), null, true);
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
		FormData formcusguaranteeouter0002 = formService.getFormData("cusguaranteeouter0002");
		MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
		mfCusGuaranteeOuter.setId(id);
		mfCusGuaranteeOuter = mfCusGuaranteeOuterFeign.getById(mfCusGuaranteeOuter);
		getObjValue(formcusguaranteeouter0002, mfCusGuaranteeOuter, formData);
		if (mfCusGuaranteeOuter != null) {
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
		MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
		mfCusGuaranteeOuter.setId(id);
		try {
			mfCusGuaranteeOuterFeign.delete(mfCusGuaranteeOuter);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
		mfCusGuaranteeOuter.setCusNo(cusNo);
		mfCusGuaranteeOuter.setRelNo(cusNo);
		mfCusGuaranteeOuter.setIsMutual("1");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusGuaranteeOuterAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formcusguaranteeouter0002 = formService.getFormData(formId);
			if (formcusguaranteeouter0002.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else {
				getFormValue(formcusguaranteeouter0002);
				mfCusGuaranteeOuter.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusguaranteeouter0002, mfCusGuaranteeOuter);
			}
			model.addAttribute("formcusguaranteeouter0002", formcusguaranteeouter0002);
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("projectName", projectName);
		model.addAttribute("query", "");
		model.addAttribute("cusType", mfCusCustomer.getCusType());
		return "/component/cus/MfCusGuaranteeOuter_Insert";
	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
		mfCusGuaranteeOuter.setCusNo(cusNo);
		mfCusGuaranteeOuter.setRelNo(relNo);
		mfCusGuaranteeOuter.setIsMutual("1");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusGuaranteeOuterAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为" + kindNo +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formcusguaranteeouter0002 = formService.getFormData(formId);
			if (formcusguaranteeouter0002.getFormId() == null) {
				// logger.error("产品类型为" + kindNo +
				// "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusguaranteeouter0002);
				mfCusGuaranteeOuter.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusguaranteeouter0002, mfCusGuaranteeOuter);
			}
			model.addAttribute("formcusguaranteeouter0002", formcusguaranteeouter0002);
		}

		model.addAttribute("query", "");
		return "/component/cus/MfCusGuaranteeOuter_Insert";
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
		FormData formcusguaranteeouter0002 = formService.getFormData("cusguaranteeouter0002");
		getFormValue(formcusguaranteeouter0002);
		MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
		setObjValue(formcusguaranteeouter0002, mfCusGuaranteeOuter);
		mfCusGuaranteeOuterFeign.insert(mfCusGuaranteeOuter);
		getObjValue(formcusguaranteeouter0002, mfCusGuaranteeOuter);
		this.addActionMessage(model, "保存成功");
		List<MfCusGuaranteeOuter> mfCusGuaranteeOuterList = (List<MfCusGuaranteeOuter>) mfCusGuaranteeOuterFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusguaranteeouter0002", formcusguaranteeouter0002);
		model.addAttribute("mfCusGuaranteeOuterList", mfCusGuaranteeOuterList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusGuaranteeOuter_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusGuaranteeOuterAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {

		} else {
			//FormData formcusguaranteeouter0002 = formService.getFormData("cusguaranteeouter0002");
			FormData formcusguaranteeouter0002 = formService.getFormData(formId);
			getFormValue(formcusguaranteeouter0002);
			MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
			mfCusGuaranteeOuter.setId(id);
			mfCusGuaranteeOuter = mfCusGuaranteeOuterFeign.getById(mfCusGuaranteeOuter);
			getObjValue(formcusguaranteeouter0002, mfCusGuaranteeOuter);
			model.addAttribute("formcusguaranteeouter0002", formcusguaranteeouter0002);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusGuaranteeOuter_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
		mfCusGuaranteeOuter.setId(id);
		mfCusGuaranteeOuterFeign.delete(mfCusGuaranteeOuter);
		return getListPage(model);
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
				"MfCusGuaranteeOuterAction");
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
			request.setAttribute("ifBizManger","3");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusguaranteeouter0003 = formService.getFormData(formId);
			MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
			mfCusGuaranteeOuter.setId(id);
			mfCusGuaranteeOuter = mfCusGuaranteeOuterFeign.getById(mfCusGuaranteeOuter);
			getObjValue(formcusguaranteeouter0003, mfCusGuaranteeOuter, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusguaranteeouter0003, "propertySeeTag", "");
			if (mfCusGuaranteeOuter != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusGuaranteeOuter);
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
	public Map<String, Object> updateByOneAjax(String formId, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusguaranteeouter0003 = formService.getFormData(formId);
		getFormValue(formcusguaranteeouter0003, getMapByJson(ajaxData));
		MfCusGuaranteeOuter mfCusGuaranteeOuterNew = new MfCusGuaranteeOuter();
		setObjValue(formcusguaranteeouter0003, mfCusGuaranteeOuterNew);
		mfCusGuaranteeOuter.setId(mfCusGuaranteeOuterNew.getId());
		mfCusGuaranteeOuter = mfCusGuaranteeOuterFeign.getById(mfCusGuaranteeOuter);
		if (mfCusGuaranteeOuter != null) {
			try {
				mfCusGuaranteeOuter = (MfCusGuaranteeOuter) EntityUtil.reflectionSetVal(mfCusGuaranteeOuter,
						mfCusGuaranteeOuterNew, getMapByJson(ajaxData));
				mfCusGuaranteeOuterFeign.update(mfCusGuaranteeOuter);
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
		FormData formcusguaranteeouter0002 = formService.getFormData("cusguaranteeouter0002");
		getFormValue(formcusguaranteeouter0002);
		boolean validateFlag = this.validateFormData(formcusguaranteeouter0002);
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
		FormData formcusguaranteeouter0002 = formService.getFormData("cusguaranteeouter0002");
		getFormValue(formcusguaranteeouter0002);
		boolean validateFlag = this.validateFormData(formcusguaranteeouter0002);
	}

}
