package app.component.receivables.controller;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.receivables.entity.MfBankReceivablesBuss;
import app.component.receivables.feign.MfBankReceivablesBussFeign;
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

/**
 * Title: MfBankReceivablesBussController.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 09 10:15:54 CST 2017
 **/
@Controller
@RequestMapping("/mfBankReceivablesBuss")
public class MfBankReceivablesBussController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBankReceivablesBussFeign mfBankReceivablesBussFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfBankReceivablesBuss_List";
	}

	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
		try {
			mfBankReceivablesBuss.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBankReceivablesBuss.setUserFlag(BizPubParm.YES_NO_Y);
			mfBankReceivablesBuss.setCriteriaList(mfBankReceivablesBuss, ajaxData);// 我的筛选
			// this.getRoleConditions(mfBankReceivablesBuss,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfBankReceivablesBuss", mfBankReceivablesBuss));
			ipage = mfBankReceivablesBussFeign.findByPage(ipage);
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

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findBussByPageAjax")
    @ResponseBody
    public Map<String, Object> findBussByPageAjax(String ajaxData, int pageNo, String tableId, String tableType,String fincId,String cusNo,String pactId,String useType)
            throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
        try {
        	String transferId = request.getParameter("transferId");
            mfBankReceivablesBuss.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBankReceivablesBuss.setCusNo(cusNo);
            mfBankReceivablesBuss.setFincId(fincId);
            mfBankReceivablesBuss.setPactId(pactId);
			mfBankReceivablesBuss.setTradingPurpose(useType);
            mfBankReceivablesBuss.setUserFlag(BizPubParm.YES_NO_N);
			mfBankReceivablesBuss.setTransferId(transferId);
            mfBankReceivablesBuss.setCriteriaList(mfBankReceivablesBuss, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Feign方法
            ipage.setParams(this.setIpageParams("mfBankReceivablesBuss", mfBankReceivablesBuss));
            ipage = mfBankReceivablesBussFeign.findBussByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData,String relNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String query="";
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			FormData formcusstate00004 = formService.getFormData(formId);
			getFormValue(formcusstate00004, map);
			if (this.validateFormData(formcusstate00004)) {
				MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
				setObjValue(formcusstate00004, mfBankReceivablesBuss);
				mfBankReceivablesBuss = mfBankReceivablesBussFeign.insert(mfBankReceivablesBuss);
				JsonTableUtil jtu = new JsonTableUtil();
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfBankReceivablesBuss", mfBankReceivablesBuss));
				String tableHtml = jtu.getJsonStr("tableBankReceivablesList", "tableTag",
						(List<MfBankReceivablesBuss>) mfBankReceivablesBussFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
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
	public Map<String, Object> updateAjaxByOne(String formId,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusstate00003 = formService.getFormData(formId);
		getFormValue(formcusstate00003, getMapByJson(ajaxData));
		MfBankReceivablesBuss mfBankReceivablesBussJsp = new MfBankReceivablesBuss();
		setObjValue(formcusstate00003, mfBankReceivablesBussJsp);
		MfBankReceivablesBuss mfBankReceivablesBuss = mfBankReceivablesBussFeign.getById(mfBankReceivablesBussJsp);
		if (mfBankReceivablesBuss != null) {
			try {
				mfBankReceivablesBuss = (MfBankReceivablesBuss) EntityUtil.reflectionSetVal(mfBankReceivablesBuss, mfBankReceivablesBussJsp,
						getMapByJson(ajaxData));
				mfBankReceivablesBussFeign.update(mfBankReceivablesBuss);
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formcusstate00002 = formService.getFormData("cusstate00002");
			getFormValue(formcusstate00002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusstate00002)) {
				MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
				setObjValue(formcusstate00002, mfBankReceivablesBuss);
				mfBankReceivablesBussFeign.update(mfBankReceivablesBuss);
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
	public Map<String, Object> getByIdAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusstate00002 = formService.getFormData("cusstate00002");
		MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
		mfBankReceivablesBuss.setCusNo(cusNo);
		mfBankReceivablesBuss = mfBankReceivablesBussFeign.getById(mfBankReceivablesBuss);
		getObjValue(formcusstate00002, mfBankReceivablesBuss, formData);
		if (mfBankReceivablesBuss != null) {
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
	public Map<String, Object> deleteAjax(String receivablesId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
		mfBankReceivablesBuss.setReceivablesId(receivablesId);
		try {
			mfBankReceivablesBussFeign.delete(mfBankReceivablesBuss);
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
	public String input(Model model,String cusNo,String relNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBankReceivablesBuss mfBankReceivablesBuss=new MfBankReceivablesBuss();
		mfBankReceivablesBuss.setCusNo(cusNo);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfBankReceivablesBuss.setCusName(mfCusCustomer.getCusName());
		String formId="BankReceivablesBase";
		FormData formBankReceivablesBase = formService.getFormData(formId);
		getFormValue(formBankReceivablesBase);
		getObjValue(formBankReceivablesBase, mfBankReceivablesBuss);
		model.addAttribute("formBankReceivablesBase", formBankReceivablesBase);
		model.addAttribute("query", "");
		return "/component/receivables/MfBankReceivablesBuss_Insert";
	}

	/***
	 * 新增
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusstate00002 = formService.getFormData("cusstate00002");
		getFormValue(formcusstate00002);
		MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
		setObjValue(formcusstate00002, mfBankReceivablesBuss);
		mfBankReceivablesBussFeign.insert(mfBankReceivablesBuss);
		getObjValue(formcusstate00002, mfBankReceivablesBuss);
		this.addActionMessage(null, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfBankReceivablesBuss", mfBankReceivablesBuss));
		List<MfBankReceivablesBuss> mfBankReceivablesBussList = (List<MfBankReceivablesBuss>) mfBankReceivablesBussFeign.findByPage(ipage).getResult();
		model.addAttribute("mfBankReceivablesBussList", mfBankReceivablesBussList);
		model.addAttribute("formcusstate00002", formcusstate00002);
		model.addAttribute("query", "");
		return "/component/cus/MfBankReceivablesBuss_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
		mfBankReceivablesBuss.setCusNo(cusNo);
		mfBankReceivablesBuss = mfBankReceivablesBussFeign.getById(mfBankReceivablesBuss);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);

		String formId="";

		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfBankReceivablesBussAction表单信息没有查询到");
		} else {
			FormData formcusstate00003 = formService.getFormData(formId);
			if (formcusstate00003.getFormId() == null) {
//				logger.error(
//						"客户类型为" + mfCusCustomer.getCusType() + "的MfBankReceivablesBussAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusstate00003);
				getObjValue(formcusstate00003, mfBankReceivablesBuss);
			}
			model.addAttribute("formcusstate00003", formcusstate00003);
			model.addAttribute("query", "");
		}

		return "/component/cus/MfBankReceivablesBuss_Detail";
	}

	/**
	 * 删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
		mfBankReceivablesBuss.setCusNo(cusNo);
		mfBankReceivablesBussFeign.delete(mfBankReceivablesBuss);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 *
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusstate00002 = formService.getFormData("cusstate00002");
		getFormValue(formcusstate00002);
		boolean validateFlag = this.validateFormData(formcusstate00002);
	}

	/**
	 * 修改校验
	 *
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusstate00002 = formService.getFormData("cusstate00002");
		getFormValue(formcusstate00002);
		boolean validateFlag = this.validateFormData(formcusstate00002);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo,String receivablesId) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String formId="";

		if (StringUtil.isEmpty(formId)) {
			//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfBankReceivablesBussAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","2");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusgoods0003 = formService.getFormData(formId);
			MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
			mfBankReceivablesBuss.setReceivablesId(receivablesId);
			mfBankReceivablesBuss = mfBankReceivablesBussFeign.getById(mfBankReceivablesBuss);
			getObjValue(formcusgoods0003, mfBankReceivablesBuss, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusgoods0003, "propertySeeTag", "");
			if (mfBankReceivablesBuss != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfBankReceivablesBuss);
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
	public Map<String, Object> updateByOneAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBankReceivablesBuss mfBankReceivablesBuss = new MfBankReceivablesBuss();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcusgoods0003 = formService.getFormData(formId);
		getFormValue(formcusgoods0003, getMapByJson(ajaxData));
		MfBankReceivablesBuss mfBankReceivablesBussNew = new MfBankReceivablesBuss();
		setObjValue(formcusgoods0003, mfBankReceivablesBussNew);
		mfBankReceivablesBuss.setReceivablesId(mfBankReceivablesBussNew.getReceivablesId());
		mfBankReceivablesBuss = mfBankReceivablesBussFeign.getById(mfBankReceivablesBuss);
		if (mfBankReceivablesBuss != null) {
			try {
				mfBankReceivablesBuss = (MfBankReceivablesBuss) EntityUtil.reflectionSetVal(mfBankReceivablesBuss, mfBankReceivablesBussNew,
						getMapByJson(ajaxData));
				mfBankReceivablesBussFeign.update(mfBankReceivablesBuss);
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
}
