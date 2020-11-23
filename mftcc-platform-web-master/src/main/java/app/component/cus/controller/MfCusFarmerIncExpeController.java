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
import app.component.cus.entity.MfCusFarmerIncExpe;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFarmerIncExpeFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;

/**
 * Title: MfCusFarmerIncExpeAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 17 14:26:26 CST 2017
 **/
@Controller
@RequestMapping("/mfCusFarmerIncExpe")
public class MfCusFarmerIncExpeController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusFarmerIncExpeBo
	@Autowired
	private MfCusFarmerIncExpeFeign mfCusFarmerIncExpeFeign;
	//全局变量
	private String query = "";//初始化query为空
	// 全局变量
	// 异步参数
	// 表单变量
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
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
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFarmerIncExpe_List";
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
		MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
		try {
			mfCusFarmerIncExpe.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusFarmerIncExpe.setCriteriaList(mfCusFarmerIncExpe, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusFarmerIncExpe,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusFarmerIncExpe", mfCusFarmerIncExpe));
			// 自定义查询Bo方法
			ipage = mfCusFarmerIncExpeFeign.findByPage(ipage);
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
			throw new Exception(e.getMessage());
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
	public Map<String, Object> insertAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusFarmerIncExpeAction").getAddModel();
			}
			FormData formcusIncExpeBase = formService.getFormData(formId);
			getFormValue(formcusIncExpeBase, getMapByJson(ajaxData));
			if (this.validateFormData(formcusIncExpeBase)) {
				MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
				setObjValue(formcusIncExpeBase, mfCusFarmerIncExpe);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusFarmerIncExpe.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String detailFormId = mfCusFormConfigFeign
						.getByCusType(mfCusCustomer.getCusType(), "MfCusFarmerIncExpeAction").getShowModelDef();
				if (StringUtil.isEmpty(detailFormId)) {
					detailFormId = mfCusFormConfigFeign.getByCusType("base", "MfCusFarmerIncExpeAction")
							.getShowModelDef();
				}
				mfCusFarmerIncExpeFeign.insert(mfCusFarmerIncExpe);
				FormData formcusIncExpeDetail = formService.getFormData(detailFormId);
				getObjValue(formcusIncExpeDetail, mfCusFarmerIncExpe);

				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusIncExpeDetail, "propertySeeTag", query);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusFarmerIncExpe.getCusNo(),
						mfCusFarmerIncExpe.getRelNo());// 更新资料完整度

				dataMap.put("mfCusFarmerIncExpe", mfCusFarmerIncExpe);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("DataFullFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("htmlStrFlag", "1");
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
		// 原来formId为写死状态，修改为从ajaxData中获取
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcusIncExpeDetail = formService.getFormData(formId);
		// FormData formcusIncExpeDetail =
		// formService.getFormData("cusIncExpeDetail");
		getFormValue(formcusIncExpeDetail, getMapByJson(ajaxData));
		MfCusFarmerIncExpe mfCusFarmerIncExpeJsp = new MfCusFarmerIncExpe();
		setObjValue(formcusIncExpeDetail, mfCusFarmerIncExpeJsp);
		MfCusFarmerIncExpe mfCusFarmerIncExpe = mfCusFarmerIncExpeFeign.getById(mfCusFarmerIncExpeJsp);
		if (mfCusFarmerIncExpe != null) {
			try {
				mfCusFarmerIncExpe = (MfCusFarmerIncExpe) EntityUtil.reflectionSetVal(mfCusFarmerIncExpe,
						mfCusFarmerIncExpeJsp, getMapByJson(ajaxData));
				mfCusFarmerIncExpeFeign.update(mfCusFarmerIncExpe);
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
		MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = "";
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo((String) map.get("cusNo"));
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusFarmerIncExpeAction");
			if(mfCusFormConfig == null){
				formId="formcusIncExpeBase";
			}else{
				formId = mfCusFormConfig.getAddModelDef();
			}
			FormData formcusIncExpeDetail = formService.getFormData(formId);
			getFormValue(formcusIncExpeDetail, getMapByJson(ajaxData));
			if (this.validateFormData(formcusIncExpeDetail)) {
				mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
				setObjValue(formcusIncExpeDetail, mfCusFarmerIncExpe);
				mfCusFarmerIncExpeFeign.update(mfCusFarmerIncExpe);
				formId = mfCusFormConfig.getShowModelDef();
				formcusIncExpeDetail = formService.getFormData(formId);
				getFormValue(formcusIncExpeDetail);
				getObjValue(formcusIncExpeDetail, mfCusCustomer);
				getObjValue(formcusIncExpeDetail, mfCusFarmerIncExpe);
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusIncExpeDetail,"propertySeeTag",query);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String incomeExpensesId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusIncExpeDetail = formService.getFormData("cusIncExpeDetail");
		MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
		mfCusFarmerIncExpe.setIncomeExpensesId(incomeExpensesId);
		mfCusFarmerIncExpe = mfCusFarmerIncExpeFeign.getById(mfCusFarmerIncExpe);
		getObjValue(formcusIncExpeDetail, mfCusFarmerIncExpe, formData);
		if (mfCusFarmerIncExpe != null) {
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
	public Map<String, Object> deleteAjax(String incomeExpensesId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
		mfCusFarmerIncExpe.setIncomeExpensesId(incomeExpensesId);
		try {
			mfCusFarmerIncExpeFeign.delete(mfCusFarmerIncExpe);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
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
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusFarmerIncExpeAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusFarmerIncExpeAction表单信息没有查询到");
		} else {
			FormData formcusIncExpeBase = formService.getFormData(formId);
			if (formcusIncExpeBase.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusFarmerIncExpeAction表单form" + formId + ".xml文件不存在");
				 */
			} else {
				MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
				mfCusFarmerIncExpe.setCusNo(cusNo);
				mfCusFarmerIncExpe.setRelNo(relNo);
				mfCusFarmerIncExpe.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcusIncExpeBase);
				getObjValue(formcusIncExpeBase, mfCusCustomer);
				getObjValue(formcusIncExpeBase, mfCusFarmerIncExpe);
			}
			model.addAttribute("formcusIncExpeBase", formcusIncExpeBase);
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("projectName", projectName);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFarmerIncExpe_Insert";
	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String kindNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusFarmerIncExpeAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为" + kindNo +
			// "的MfCusFarmerIncExpeAction表单信息没有查询到");
		} else {
			FormData formcusIncExpeBase = formService.getFormData(formId);
			if (formcusIncExpeBase.getFormId() == null) {
				// logger.error("产品类型为" + kindNo +
				// "的MfCusFarmerIncExpeAction表单form" + formId + ".xml文件不存在");
			} else {
				MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
				mfCusFarmerIncExpe.setCusNo(cusNo);
				mfCusFarmerIncExpe.setRelNo(relNo);
				mfCusFarmerIncExpe.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcusIncExpeBase);
				getObjValue(formcusIncExpeBase, mfCusCustomer);
				getObjValue(formcusIncExpeBase, mfCusFarmerIncExpe);
			}
			model.addAttribute("formcusIncExpeBase", formcusIncExpeBase);
		}

		model.addAttribute("query", "");
		return "/component/cus/MfCusFarmerIncExpe_Insert";
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
		FormData formcusIncExpeDetail = formService.getFormData("cusIncExpeDetail");
		getFormValue(formcusIncExpeDetail);
		MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
		setObjValue(formcusIncExpeDetail, mfCusFarmerIncExpe);
		mfCusFarmerIncExpeFeign.insert(mfCusFarmerIncExpe);
		getObjValue(formcusIncExpeDetail, mfCusFarmerIncExpe);
		this.addActionMessage(model, "保存成功");
		List<MfCusFarmerIncExpe> mfCusFarmerIncExpeList = (List<MfCusFarmerIncExpe>) mfCusFarmerIncExpeFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusIncExpeDetail", formcusIncExpeDetail);
		model.addAttribute("mfCusFarmerIncExpeList", mfCusFarmerIncExpeList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFarmerIncExpe_Insert";
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
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String formId = "";
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusFarmerIncExpeAction");
		if (mfCusFormConfig == null) {
			formId ="cusIncExpeBase";
		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData formcusIncExpeBase = formService.getFormData(formId);
		getFormValue(formcusIncExpeBase);
		MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
		mfCusFarmerIncExpe.setCusNo(cusNo);
		mfCusFarmerIncExpe = mfCusFarmerIncExpeFeign.getById(mfCusFarmerIncExpe);
		getObjValue(formcusIncExpeBase, mfCusFarmerIncExpe);
		model.addAttribute("formcusIncExpeBase", formcusIncExpeBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFarmerIncExpe_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String incomeExpensesId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
		mfCusFarmerIncExpe.setIncomeExpensesId(incomeExpensesId);
		mfCusFarmerIncExpeFeign.delete(mfCusFarmerIncExpe);
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
		FormData formcusIncExpeDetail = formService.getFormData("cusIncExpeDetail");
		getFormValue(formcusIncExpeDetail);
		boolean validateFlag = this.validateFormData(formcusIncExpeDetail);
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
		FormData formcusIncExpeDetail = formService.getFormData("cusIncExpeDetail");
		getFormValue(formcusIncExpeDetail);
		boolean validateFlag = this.validateFormData(formcusIncExpeDetail);
	}

}
