package app.component.cus.payment.controller;

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
import app.component.cus.entity.MfCusAssureCompany;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusAssureCompanyFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.payment.entity.MfCusAssurePayment;
import app.component.cus.payment.feign.MfCusAssurePaymentFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusAssurePaymentAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 11 10:32:26 CST 2018
 **/
@Controller
@RequestMapping("/mfCusAssurePayment")
public class MfCusAssurePaymentController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入mfCusAssurePaymentFeign
	@Autowired
	private MfCusAssurePaymentFeign mfCusAssurePaymentFeign;
	@Autowired
	private MfCusAssureCompanyFeign mfCusAssureCompanyFeign;
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
		return "/component/cus/payment/MfCusAssurePayment_List";
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
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssurePayment mfCusAssurePayment = new MfCusAssurePayment();
		try {
			mfCusAssurePayment.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusAssurePayment.setCriteriaList(mfCusAssurePayment, ajaxData);// 我的筛选
			// mfCusAssurePayment.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusAssurePayment,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusAssurePayment", mfCusAssurePayment));
			ipage = mfCusAssurePaymentFeign.findByPage(ipage);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> ajaxDataMap = getMapByJson(ajaxData);
		try {
			FormData formassurepaymentbase = formService.getFormData(ajaxDataMap.get("formId").toString());
			getFormValue(formassurepaymentbase, getMapByJson(ajaxData));
			if (this.validateFormData(formassurepaymentbase)) {
				MfCusAssurePayment mfCusAssurePayment = new MfCusAssurePayment();
				setObjValue(formassurepaymentbase, mfCusAssurePayment);
				mfCusAssurePaymentFeign.insert(mfCusAssurePayment);
				String cusNo = ajaxDataMap.get("assureNo").toString();
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				// 更新资料完整度
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo,
						cusNo);// 更新客户信息完整度
				String tableId = "";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusAssurePaymentAction");
				if (mfCusFormConfig != null) {
					tableId = mfCusFormConfig.getListModelDef();
				}
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				request.setAttribute("ifBizManger", "3");
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusAssurePayment", mfCusAssurePayment));
				String tableFormId = "tableassurepaymentlist";
				String htmlStr = "";
				if (StringUtil.isNotEmpty(tableId)) {
					tableFormId = "table" + tableId;
				}
				JsonTableUtil jtu = new JsonTableUtil();
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfCusAssurePayment>) mfCusAssurePaymentFeign.findPageByAssureNo(ipage).getResult(), null, true);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassurepaymentbase = formService.getFormData("assurepaymentbase");
		getFormValue(formassurepaymentbase, getMapByJson(ajaxData));
		MfCusAssurePayment mfCusAssurePaymentJsp = new MfCusAssurePayment();
		setObjValue(formassurepaymentbase, mfCusAssurePaymentJsp);
		MfCusAssurePayment mfCusAssurePayment = mfCusAssurePaymentFeign.getById(mfCusAssurePaymentJsp);
		if (mfCusAssurePayment != null) {
			try {
				mfCusAssurePayment = (MfCusAssurePayment) EntityUtil.reflectionSetVal(mfCusAssurePayment,
						mfCusAssurePaymentJsp, getMapByJson(ajaxData));
				mfCusAssurePaymentFeign.update(mfCusAssurePayment);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssurePayment mfCusAssurePayment = new MfCusAssurePayment();
		try {
			FormData formassurepaymentbase = formService.getFormData("assurepaymentbase");
			getFormValue(formassurepaymentbase, getMapByJson(ajaxData));
			if (this.validateFormData(formassurepaymentbase)) {
				mfCusAssurePayment = new MfCusAssurePayment();
				setObjValue(formassurepaymentbase, mfCusAssurePayment);
				mfCusAssurePaymentFeign.update(mfCusAssurePayment);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	public Map<String, Object> getByIdAjax(String ajaxData, String paymentId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		FormData formassurepaymentbase = formService.getFormData("assurepaymentbase");
		MfCusAssurePayment mfCusAssurePayment = new MfCusAssurePayment();
		mfCusAssurePayment.setPaymentId(paymentId);
		mfCusAssurePayment = mfCusAssurePaymentFeign.getById(mfCusAssurePayment);
		getObjValue(formassurepaymentbase, mfCusAssurePayment, formData);
		if (mfCusAssurePayment != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String paymentId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssurePayment mfCusAssurePayment = new MfCusAssurePayment();
		mfCusAssurePayment.setPaymentId(paymentId);
		try {
			mfCusAssurePaymentFeign.delete(mfCusAssurePayment);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
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
	public String input(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCusAssureCompany  mfCusAssureCompany=new MfCusAssureCompany();
		mfCusAssureCompany.setCusNo(cusNo);
		mfCusAssureCompany = mfCusAssureCompanyFeign.getById(mfCusAssureCompany);
		MfCusAssurePayment mfCusAssurePayment = new MfCusAssurePayment();
		if(mfCusAssureCompany != null){
			mfCusAssurePayment.setAssureNo(mfCusAssureCompany.getCusNo());
			mfCusAssurePayment.setAssureName(mfCusAssureCompany.getAssureCompanyName());
		}
		FormData formassurepaymentbase = formService.getFormData("assurepaymentbase");
		getObjValue(formassurepaymentbase, mfCusAssurePayment);
		model.addAttribute("formassurepaymentbase", formassurepaymentbase);
		model.addAttribute("query", ""); 
		return "/component/cus/payment/MfCusAssurePayment_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String paymentId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassurepaymentdetail = formService.getFormData("assurepaymentdetail");
		getFormValue(formassurepaymentdetail);
		MfCusAssurePayment mfCusAssurePayment = new MfCusAssurePayment();
		mfCusAssurePayment.setPaymentId(paymentId);
		mfCusAssurePayment = mfCusAssurePaymentFeign.getById(mfCusAssurePayment);
		getObjValue(formassurepaymentdetail, mfCusAssurePayment);
		model.addAttribute("formassurepaymentdetail", formassurepaymentdetail);
		model.addAttribute("query", "");
		return "/component/cus/payment/MfCusAssurePayment_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
			FormService formService = new FormService();
		 FormData formassurepaymentbase = formService.getFormData("assurepaymentbase");
		 getFormValue(formassurepaymentbase);
		 boolean validateFlag = this.validateFormData(formassurepaymentbase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
			FormService formService = new FormService();
		 FormData formassurepaymentbase = formService.getFormData("assurepaymentbase");
		 getFormValue(formassurepaymentbase);
		 boolean validateFlag = this.validateFormData(formassurepaymentbase);
	}

}
