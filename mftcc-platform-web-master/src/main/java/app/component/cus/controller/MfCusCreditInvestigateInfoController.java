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
import app.component.cus.entity.MfCusCreditInvestigateInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCreditInvestigateInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusCreditInvestigateInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Oct 16 11:47:03 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCreditInvestigateInfo")
public class MfCusCreditInvestigateInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCreditInvestigateInfoFeign mfCusCreditInvestigateInfoFeign;
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
		return "/component/cus/MfCusCreditInvestigateInfo_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = new MfCusCreditInvestigateInfo();
		try {
			mfCusCreditInvestigateInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCreditInvestigateInfo.setCriteriaList(mfCusCreditInvestigateInfo, ajaxData);// 我的筛选
			// mfCusCreditInvestigateInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusCreditInvestigateInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCreditInvestigateInfo", mfCusCreditInvestigateInfo));
			ipage = mfCusCreditInvestigateInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> map = getMapByJson(ajaxData);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo((String) map.get("cusNo"));
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusCreditInvestigateInfoAction");
		String formId="";
		String query="";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			log.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusCreditInvestigateInfoAction表单信息没有查询到");
		} else {
			try {
				FormData formcusCreditIinvestigateInfoBase = formService.getFormData(formId);
				getFormValue(formcusCreditIinvestigateInfoBase, getMapByJson(ajaxData));
				if (this.validateFormData(formcusCreditIinvestigateInfoBase)) {
					MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = new MfCusCreditInvestigateInfo();
					setObjValue(formcusCreditIinvestigateInfoBase, mfCusCreditInvestigateInfo);
					mfCusCreditInvestigateInfoFeign.insert(mfCusCreditInvestigateInfo);
					String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusCreditInvestigateInfo.getCusNo(),
							mfCusCreditInvestigateInfo.getRelNo());// 更新资料完整度
					/*
					 * cusNo = mfCusCreditInvestigateInfo.getCusNo(); relNo =
					 * mfCusCreditInvestigateInfo.getRelNo();
					 * MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo =
					 * new MfCusCreditInvestigateInfo();
					 * mfCusCreditInvestigateInfo.setCusNo(cusNo);
					 * mfCusCreditInvestigateInfo.setRelNo(relNo);
					 */
					/*
					 * Ipage ipage = this.getIpage(); JsonTableUtil jtu = new
					 * JsonTableUtil(); String tableHtml =
					 * jtu.getJsonStr("tablecusCreditIinvestigateInfoBase",
					 * "tableTag",
					 * (List<MfCusPersonCorp>)mfCusCreditInvestigateInfoFeign.
					 * findByPage(ipage,
					 * mfCusCreditInvestigateInfo).getResult(), null,true);
					 */

					String detailFormId = mfCusFormConfigFeign
							.getByCusType(mfCusCustomer.getCusType(), "MfCusCreditInvestigateInfoAction")
							.getShowModelDef();
					if (StringUtil.isEmpty(detailFormId)) {
//						logger.error("MfCusCreditInvestigateInfoAction的detailFormId找不到");
					}
					FormData formcusCreditIinvestigateInfoDetail = formService.getFormData(detailFormId);
					getObjValue(formcusCreditIinvestigateInfoDetail, mfCusCreditInvestigateInfo);

					this.getHttpRequest().setAttribute("ifBizManger", "3");
					JsonFormUtil jsonFormUtil = new JsonFormUtil();
					String htmlStr = jsonFormUtil.getJsonStr(formcusCreditIinvestigateInfoDetail, "propertySeeTag",
							query);

					dataMap.put("mfCusCustomer", mfCusCustomer);
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
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		}
		return dataMap;
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusCreditInvestigateInfoAction");
		String formId="";
		String query="";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			log.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusCreditInvestigateInfoAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","3");
			
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusCreditIinvestigateInfoDetail = formService.getFormData(formId);
			MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = new MfCusCreditInvestigateInfo();
			mfCusCreditInvestigateInfo.setId(id);
			mfCusCreditInvestigateInfo = mfCusCreditInvestigateInfoFeign.getById(mfCusCreditInvestigateInfo);
			getObjValue(formcusCreditIinvestigateInfoDetail, mfCusCreditInvestigateInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusCreditIinvestigateInfoDetail, "propertySeeTag", query);
			if (mfCusCreditInvestigateInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusCreditInvestigateInfo);
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
		FormData formcusCreditIinvestigateInfoDetail = formService.getFormData(formId);
		getFormValue(formcusCreditIinvestigateInfoDetail, getMapByJson(ajaxData));
		MfCusCreditInvestigateInfo mfCusCreditInvestigateInfoJsp = new MfCusCreditInvestigateInfo();
		setObjValue(formcusCreditIinvestigateInfoDetail, mfCusCreditInvestigateInfoJsp);
		MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = mfCusCreditInvestigateInfoFeign.getById(mfCusCreditInvestigateInfoJsp);
		if (mfCusCreditInvestigateInfo != null) {
			try {
				mfCusCreditInvestigateInfo = (MfCusCreditInvestigateInfo) EntityUtil.reflectionSetVal(
						mfCusCreditInvestigateInfo, mfCusCreditInvestigateInfoJsp, getMapByJson(ajaxData));
				mfCusCreditInvestigateInfoFeign.update(mfCusCreditInvestigateInfo);
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
		try {
			FormData formcusCreditIinvestigateInfoDetail = formService.getFormData("cusCreditIinvestigateInfoDetail");
			getFormValue(formcusCreditIinvestigateInfoDetail, getMapByJson(ajaxData));
			if (this.validateFormData(formcusCreditIinvestigateInfoDetail)) {
				MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = new MfCusCreditInvestigateInfo();
				setObjValue(formcusCreditIinvestigateInfoDetail, mfCusCreditInvestigateInfo);
				mfCusCreditInvestigateInfoFeign.update(mfCusCreditInvestigateInfo);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusCreditIinvestigateInfoDetail = formService.getFormData("cusCreditIinvestigateInfoDetail");
		MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = new MfCusCreditInvestigateInfo();
		mfCusCreditInvestigateInfo.setId(id);
		mfCusCreditInvestigateInfo = mfCusCreditInvestigateInfoFeign.getById(mfCusCreditInvestigateInfo);
		getObjValue(formcusCreditIinvestigateInfoDetail, mfCusCreditInvestigateInfo, formData);
		if (mfCusCreditInvestigateInfo != null) {
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = new MfCusCreditInvestigateInfo();
		mfCusCreditInvestigateInfo.setId(id);
		try {
			mfCusCreditInvestigateInfoFeign.delete(mfCusCreditInvestigateInfo);
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
	public String input(Model model,String cusNo,String relNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = new MfCusCreditInvestigateInfo();
		mfCusCreditInvestigateInfo.setCusNo(cusNo);
		mfCusCreditInvestigateInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusCreditInvestigateInfoAction");
		String formId="";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			log.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusCreditInvestigateInfoAction表单信息没有查询到");
		} else {
			FormData formcusCreditIinvestigateInfoBase = formService.getFormData(formId);
			if (formcusCreditIinvestigateInfoBase.getFormId() == null) {
//				log.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusCreditInvestigateInfoAction表单form" + formId
//						+ ".xml文件不存在");
			} else {
				getFormValue(formcusCreditIinvestigateInfoBase);
				mfCusCreditInvestigateInfo.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusCreditIinvestigateInfoBase, mfCusCreditInvestigateInfo);
			}
			model.addAttribute("formcusCreditIinvestigateInfoBase", formcusCreditIinvestigateInfoBase);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusCreditInvestigateInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusCreditIinvestigateInfoBase = formService.getFormData("cusCreditIinvestigateInfoBase");
		getFormValue(formcusCreditIinvestigateInfoBase);
		MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = new MfCusCreditInvestigateInfo();
		mfCusCreditInvestigateInfo.setId(id);
		mfCusCreditInvestigateInfo = mfCusCreditInvestigateInfoFeign.getById(mfCusCreditInvestigateInfo);
		getObjValue(formcusCreditIinvestigateInfoBase, mfCusCreditInvestigateInfo);
		model.addAttribute("formcusCreditIinvestigateInfoBase", formcusCreditIinvestigateInfoBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusCreditInvestigateInfo_Detail";
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
		FormData formcusCreditIinvestigateInfoDetail = formService.getFormData("cusCreditIinvestigateInfoDetail");
		getFormValue(formcusCreditIinvestigateInfoDetail);
		boolean validateFlag = this.validateFormData(formcusCreditIinvestigateInfoDetail);
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
		FormData formcusCreditIinvestigateInfoDetail = formService.getFormData("cusCreditIinvestigateInfoDetail");
		getFormValue(formcusCreditIinvestigateInfoDetail);
		boolean validateFlag = this.validateFormData(formcusCreditIinvestigateInfoDetail);
	}
}
