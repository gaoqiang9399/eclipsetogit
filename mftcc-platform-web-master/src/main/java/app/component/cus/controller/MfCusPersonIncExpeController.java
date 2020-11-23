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

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonIncExpe;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonIncExpeFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusPersonIncExpeAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon May 30 09:03:56 CST 2016
 **/
@Controller
@RequestMapping("/mfCusPersonIncExpe")
public class MfCusPersonIncExpeController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusPersonIncExpeBo
	@Autowired
	private MfCusPersonIncExpeFeign mfCusPersonIncExpeFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	// 全局变量
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	//全局变量
	private String query = "";//初始化query为空
	// public Logger log =
	// LoggerFactory.getLogger(MfCusPersonIncExpeAction.class);
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
		return "/component/cus/MfCusPersonIncExpe_List";
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
		MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
		try {
			mfCusPersonIncExpe.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPersonIncExpe.setCriteriaList(mfCusPersonIncExpe, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusPersonIncExpe,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersonIncExpe", mfCusPersonIncExpe));
			ipage = mfCusPersonIncExpeFeign.findByPage(ipage);
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonIncExpeAction").getAddModel();
			}
			FormData formcusinc00002 = formService.getFormData(formId);
			getFormValue(formcusinc00002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusinc00002)) {
				MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
				setObjValue(formcusinc00002, mfCusPersonIncExpe);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonIncExpe.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusPersonIncExpe.setCusName(mfCusCustomer.getCusName());

				mfCusPersonIncExpeFeign.insert(mfCusPersonIncExpe);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonIncExpe.getCusNo(),
						mfCusPersonIncExpe.getRelNo());// 更新客户信息完整度

				String detailFormId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonIncExpeAction")
						.getShowModel();
				if (mfCusPersonIncExpe.getCusNo().equals(mfCusPersonIncExpe.getRelNo())) {
					detailFormId = mfCusFormConfigFeign
							.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonIncExpeAction").getShowModelDef();
				} else {
					MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfCusPersonIncExpe.getRelNo());
					detailFormId = mfCusFormConfigFeign.getByCusType(mfBusApply.getKindNo(), "MfCusPersonIncExpeAction")
							.getShowModelDef();
				}
				if (StringUtil.isEmpty(detailFormId)) {
					// logger.error("MfCusPersonIncExpeAction的detailFormId找不到");
				}
				FormData formcusinc00003 = formService.getFormData(detailFormId);
				getObjValue(formcusinc00003, mfCusPersonIncExpe);

				request.setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusinc00003, "propertySeeTag", "");

				dataMap.put("mfCusPersonIncExpe", mfCusPersonIncExpe);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("DataFullFlag", "1");

				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("infIntegrity", infIntegrity);
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
		FormData formcusinc00002 = formService.getFormData("cusinc00002");
		getFormValue(formcusinc00002, getMapByJson(ajaxData));
		MfCusPersonIncExpe mfCusPersonIncExpeJsp = new MfCusPersonIncExpe();
		setObjValue(formcusinc00002, mfCusPersonIncExpeJsp);
		MfCusPersonIncExpe mfCusPersonIncExpe = mfCusPersonIncExpeFeign.getById(mfCusPersonIncExpeJsp);
		if (mfCusPersonIncExpe != null) {
			try {
				mfCusPersonIncExpe = (MfCusPersonIncExpe) EntityUtil.reflectionSetVal(mfCusPersonIncExpe,
						mfCusPersonIncExpeJsp, getMapByJson(ajaxData));
				mfCusPersonIncExpeFeign.update(mfCusPersonIncExpe);
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
		MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = "";
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo((String) map.get("cusNo"));
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonIncExpeAction");
			if(mfCusFormConfig == null){

			}else{
				formId = mfCusFormConfig.getAddModelDef();
			}
			FormData formcusinc00002 = formService.getFormData(formId);
			getFormValue(formcusinc00002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusinc00002)) {
				setObjValue(formcusinc00002, mfCusPersonIncExpe);
				mfCusPersonIncExpeFeign.update(mfCusPersonIncExpe);
				formId = mfCusFormConfig.getShowModelDef();
				FormData formcusPaymentDetail = formService.getFormData(formId);
				getFormValue(formcusPaymentDetail);
				getObjValue(formcusPaymentDetail, mfCusCustomer);
				getObjValue(formcusPaymentDetail, mfCusPersonIncExpe);
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusPaymentDetail,"propertySeeTag",query);

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusinc00002 = formService.getFormData("cusinc00002");
		MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
		mfCusPersonIncExpe.setCusNo(cusNo);
		mfCusPersonIncExpe = mfCusPersonIncExpeFeign.getById(mfCusPersonIncExpe);
		getObjValue(formcusinc00002, mfCusPersonIncExpe, formData);
		if (mfCusPersonIncExpe != null) {
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
	public Map<String, Object> deleteAjax(String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
		mfCusPersonIncExpe.setCusNo(cusNo);
		try {
			mfCusPersonIncExpeFeign.delete(mfCusPersonIncExpe);
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
		FormData formcusinc00002 = null;
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonIncExpeAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonIncExpeAction表单信息没有查询到");
		} else {
			formcusinc00002 = formService.getFormData(formId);
			if (formcusinc00002.getFormId() == null) {
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonIncExpeAction表单form"+formId+".xml文件不存在");
			} else {
				MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
				mfCusPersonIncExpe.setCusNo(cusNo);
				mfCusPersonIncExpe.setRelNo(relNo);
				mfCusPersonIncExpe.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcusinc00002);
				getObjValue(formcusinc00002, mfCusPersonIncExpe);
			}
		}
		model.addAttribute("formcusinc00002", formcusinc00002);
		model.addAttribute("relNo", relNo);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonIncExpe_Insert";
	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		FormData formcusinc00002 = null;
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);

		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusPersonIncExpeAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// log.error("产品类型为"+ kindNo +"的MfCusPersonIncExpeAction表单信息没有查询到");
		} else {
			formcusinc00002 = formService.getFormData(formId);
			if (formcusinc00002.getFormId() == null) {
				// log.error("产品类型为"+ kindNo
				// +"的MfCusPersonIncExpeAction表单form"+formId+".xml文件不存在");
			} else {
				MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
				mfCusPersonIncExpe.setCusNo(cusNo);
				mfCusPersonIncExpe.setRelNo(relNo);
				mfCusPersonIncExpe.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcusinc00002);
				getObjValue(formcusinc00002, mfCusCustomer);
				getObjValue(formcusinc00002, mfCusPersonIncExpe);
			}
		}
		model.addAttribute("formcusinc00002", formcusinc00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonIncExpe_Insert";
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
		FormData formcusinc00002 = formService.getFormData("cusinc00002");
		getFormValue(formcusinc00002);
		MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
		setObjValue(formcusinc00002, mfCusPersonIncExpe);
		mfCusPersonIncExpeFeign.insert(mfCusPersonIncExpe);
		getObjValue(formcusinc00002, mfCusPersonIncExpe);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusPersonIncExpe", mfCusPersonIncExpe));
		List<MfCusPersonIncExpe> mfCusPersonIncExpeList = (List<MfCusPersonIncExpe>) mfCusPersonIncExpeFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formcusinc00002", formcusinc00002);
		model.addAttribute("mfCusPersonIncExpeList", mfCusPersonIncExpeList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonIncExpe_Insert";
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

        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                "MfCusPersonIncExpeAction");
        String formId = "cusPaymentBase";
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getAddModelDef();
        }
        FormData formcusinc00001 = formService.getFormData(formId);
		getFormValue(formcusinc00001);
		MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
		mfCusPersonIncExpe.setCusNo(cusNo);
		mfCusPersonIncExpe = mfCusPersonIncExpeFeign.getById(mfCusPersonIncExpe);
		getObjValue(formcusinc00001, mfCusPersonIncExpe);
		model.addAttribute("formcusPaymentBase", formcusinc00001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonIncExpe_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
		mfCusPersonIncExpe.setCusNo(cusNo);
		mfCusPersonIncExpeFeign.delete(mfCusPersonIncExpe);
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
		FormData formcusinc00002 = formService.getFormData("cusinc00002");
		getFormValue(formcusinc00002);
		boolean validateFlag = this.validateFormData(formcusinc00002);
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
		FormData formcusinc00002 = formService.getFormData("cusinc00002");
		getFormValue(formcusinc00002);
		boolean validateFlag = this.validateFormData(formcusinc00002);
	}

}
