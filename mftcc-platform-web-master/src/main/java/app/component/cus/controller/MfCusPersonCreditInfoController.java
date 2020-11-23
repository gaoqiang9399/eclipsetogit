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

import app.component.appinterface.AppInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonCreditInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonCreditInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;

/**
 * Title: MfCusPersonCreditInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Apr 11 09:23:17 CST 2017
 **/
@Controller
@RequestMapping("/mfCusPersonCreditInfo")
public class MfCusPersonCreditInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusPersonCreditInfoBo
	@Autowired
	private MfCusPersonCreditInfoFeign mfCusPersonCreditInfoFeign;
	// 全局变量
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	//全局变量
	private String query = "";//初始化query为空
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	// 异步参数
	// 表单变量
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
		return "/component/cus/MfCusPersonCreditInfo_List";
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
		MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
		try {
			mfCusPersonCreditInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPersonCreditInfo.setCriteriaList(mfCusPersonCreditInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusPersonCreditInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersonCreditInfo", mfCusPersonCreditInfo));
			ipage = mfCusPersonCreditInfoFeign.findByPage(ipage);
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
			FormData formcusperscredit0002 = formService.getFormData(formId);
			getFormValue(formcusperscredit0002, map);
			if (this.validateFormData(formcusperscredit0002)) {
				MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
				setObjValue(formcusperscredit0002, mfCusPersonCreditInfo);
				MfCusPersonCreditInfo mfCusPersonCreditInfop = new MfCusPersonCreditInfo();
				mfCusPersonCreditInfop = mfCusPersonCreditInfoFeign.getById(mfCusPersonCreditInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonCreditInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String detailFormId = mfCusFormConfigFeign
						.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonCreditInfoAction").getShowModelDef();
				if (StringUtil.isEmpty(detailFormId)) {
					detailFormId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonCreditInfoAction")
							.getShowModelDef();
				}
				mfCusPersonCreditInfoFeign.insert(mfCusPersonCreditInfo);
				FormData formcusperscredit0001 = formService.getFormData(detailFormId);
				getObjValue(formcusperscredit0001, mfCusPersonCreditInfo);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonCreditInfo.getCusNo(),
						mfCusPersonCreditInfo.getRelNo());// 更新资料完整度
				request.setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String cusNo = mfCusPersonCreditInfo.getCusNo();
				String relNo = mfCusPersonCreditInfo.getRelNo();
				mfCusPersonCreditInfo.setCusNo(cusNo);
				mfCusPersonCreditInfo.setRelNo(relNo);
				String htmlStr = jsonFormUtil.getJsonStr(formcusperscredit0001, "propertySeeTag", "");
				dataMap.put("mfCusPersonCreditInfo", mfCusPersonCreditInfo);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("DataFullFlag", "1");

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
	public Map<String, Object> updateAjaxByOne(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusperscredit0002 = formService.getFormData(formId);
		getFormValue(formcusperscredit0002, getMapByJson(ajaxData));
		MfCusPersonCreditInfo mfCusPersonCreditInfoJsp = new MfCusPersonCreditInfo();
		setObjValue(formcusperscredit0002, mfCusPersonCreditInfoJsp);
		MfCusPersonCreditInfo mfCusPersonCreditInfo = mfCusPersonCreditInfoFeign.getById(mfCusPersonCreditInfoJsp);
		if (mfCusPersonCreditInfo != null) {
			try {
				mfCusPersonCreditInfo = (MfCusPersonCreditInfo) EntityUtil.reflectionSetVal(mfCusPersonCreditInfo,
						mfCusPersonCreditInfoJsp, getMapByJson(ajaxData));
				mfCusPersonCreditInfoFeign.update(mfCusPersonCreditInfo);
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
		MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
		try {
			FormData formcusperscredit0002 = formService.getFormData("cusperscredit0002");
			getFormValue(formcusperscredit0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusperscredit0002)) {
				setObjValue(formcusperscredit0002, mfCusPersonCreditInfo);
				mfCusPersonCreditInfoFeign.update(mfCusPersonCreditInfo);

                String formId = "";
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonCreditInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonCreditInfoAction");
				if(mfCusFormConfig == null){

				}else{
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formcusCreditDetail = formService.getFormData(formId);
				if(formcusCreditDetail.getFormId() == null){
					//log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersBaseInfoAction表单form"+formId+".xml文件不存在");
				}
				getFormValue(formcusCreditDetail);
				getObjValue(formcusCreditDetail, mfCusCustomer);
				getObjValue(formcusCreditDetail, mfCusPersonCreditInfo);
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusCreditDetail,"propertySeeTag",query);

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
	public Map<String, Object> getByIdAjax(String creditId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusperscredit0002 = formService.getFormData("cusperscredit0002");
		MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
		mfCusPersonCreditInfo.setCreditId(creditId);
		mfCusPersonCreditInfo = mfCusPersonCreditInfoFeign.getById(mfCusPersonCreditInfo);
		getObjValue(formcusperscredit0002, mfCusPersonCreditInfo, formData);
		if (mfCusPersonCreditInfo != null) {
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
	public Map<String, Object> deleteAjax(String creditId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
		mfCusPersonCreditInfo.setCreditId(creditId);
		try {
			mfCusPersonCreditInfoFeign.delete(mfCusPersonCreditInfo);
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
	public String input(Model model, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		FormData formcusperscredit0002 = null;
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonCreditInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonCreditInfoAction表单信息没有查询到");
		} else {
			formcusperscredit0002 = formService.getFormData(formId);
			if (formcusperscredit0002.getFormId() == null) {
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonCreditInfoAction表单form"+formId+".xml文件不存在");
			} else {
				MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
				mfCusPersonCreditInfo.setCusNo(cusNo);
				mfCusPersonCreditInfo.setRelNo(relNo);
				mfCusPersonCreditInfo.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcusperscredit0002);
				getObjValue(formcusperscredit0002, mfCusCustomer);
				getObjValue(formcusperscredit0002, mfCusPersonCreditInfo);
			}
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("projectName", projectName);
		model.addAttribute("formcusperscredit0002", formcusperscredit0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonCreditInfo_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面 . * @return
	 * 
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-9-26 上午10:07:28
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String kindNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		FormData formcusperscredit0002 = null;
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusPersonCreditInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+kindNo+"的MfCusPersonCreditInfoAction表单信息没有查询到");
		} else {
			formcusperscredit0002 = formService.getFormData(formId);
			if (formcusperscredit0002.getFormId() == null) {
				// logger.error("产品类型为"+kindNo+"的MfCusPersonCreditInfoAction表单form"+formId+".xml文件不存在");
			} else {
				MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
				mfCusPersonCreditInfo.setCusNo(cusNo);
				mfCusPersonCreditInfo.setRelNo(relNo);
				mfCusPersonCreditInfo.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcusperscredit0002);
				getObjValue(formcusperscredit0002, mfCusCustomer);
				getObjValue(formcusperscredit0002, mfCusPersonCreditInfo);
			}
		}
		model.addAttribute("formcusperscredit0002", formcusperscredit0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonCreditInfo_Insert";
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
		FormData formcusperscredit0002 = formService.getFormData("cusperscredit0002");
		getFormValue(formcusperscredit0002);
		MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
		setObjValue(formcusperscredit0002, mfCusPersonCreditInfo);
		mfCusPersonCreditInfoFeign.insert(mfCusPersonCreditInfo);
		getObjValue(formcusperscredit0002, mfCusPersonCreditInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusPersonCreditInfo", mfCusPersonCreditInfo));
		List<MfCusPersonCreditInfo> mfCusPersonCreditInfoList = (List<MfCusPersonCreditInfo>) mfCusPersonCreditInfoFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusperscredit0002", formcusperscredit0002);
		model.addAttribute("mfCusPersonCreditInfoList", mfCusPersonCreditInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonCreditInfo_Insert";
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
		FormData formcusperscredit0001 = formService.getFormData("cusCreditBase");
		getFormValue(formcusperscredit0001);
		MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
		mfCusPersonCreditInfo.setCusNo(cusNo);
		mfCusPersonCreditInfo = mfCusPersonCreditInfoFeign.getById(mfCusPersonCreditInfo);
		getObjValue(formcusperscredit0001, mfCusPersonCreditInfo);
		model.addAttribute("formcusCreditBase", formcusperscredit0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonCreditInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String creditId) throws Exception {
		ActionContext.initialize(request, response);
		MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
		mfCusPersonCreditInfo.setCreditId(creditId);
		mfCusPersonCreditInfoFeign.delete(mfCusPersonCreditInfo);
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
		FormData formcusperscredit0002 = formService.getFormData("cusperscredit0002");
		getFormValue(formcusperscredit0002);
		boolean validateFlag = this.validateFormData(formcusperscredit0002);
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
		FormData formcusperscredit0002 = formService.getFormData("cusperscredit0002");
		getFormValue(formcusperscredit0002);
		boolean validateFlag = this.validateFormData(formcusperscredit0002);
	}

}
