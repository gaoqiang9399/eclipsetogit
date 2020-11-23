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
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusLegalMember;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusLegalMemberFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * Title: MfCusLegalMemberAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 01 09:37:30 CST 2016
 **/
@Controller
@RequestMapping("/mfCusLegalMember")
public class MfCusLegalMemberController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusLegalMemberBo
	@Autowired
	private MfCusLegalMemberFeign mfCusLegalMemberFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @param cusNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
		mfCusLegalMember.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusLegalMember", mfCusLegalMember));
		List<MfCusLegalMember> mfCusLegalMemberList = (List<MfCusLegalMember>) mfCusLegalMemberFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("mfCusLegalMemberList", mfCusLegalMemberList);
		model.addAttribute("mfCusLegalMember", mfCusLegalMember);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalMember_List";
	}

	@RequestMapping(value = "/getListPageBig")
	public String getListPageBig(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcuslegm00001 = formService.getFormData("cuslegm00001");
			MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
			mfCusLegalMember.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusLegalMember", mfCusLegalMember));
			List<MfCusLegalMember> mfCusLegalMemberList = (List<MfCusLegalMember>) mfCusLegalMemberFeign
					.findByPage(ipage).getResult();
			model.addAttribute("formcuslegm00001", formcuslegm00001);
			model.addAttribute("mfCusLegalMemberList", mfCusLegalMemberList);
			model.addAttribute("mfCusLegalMember", mfCusLegalMember);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalMember_ListBig";
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
		try {
			mfCusLegalMember.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusLegalMember.setCriteriaList(mfCusLegalMember, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusLegalMember,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusLegalMember", mfCusLegalMember));
			ipage = mfCusLegalMemberFeign.findByPage(ipage);
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
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusLegalMemberAction").getAddModel();
			}
			FormData formcuslegm00003 = formService.getFormData(formId);
			getFormValue(formcuslegm00003, map);
			if (this.validateFormData(formcuslegm00003)) {
				MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
				setObjValue(formcuslegm00003, mfCusLegalMember);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusLegalMember.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusLegalMember.setCusName(cusName);
				mfCusLegalMemberFeign.insert(mfCusLegalMember);

				// getTableData();
				mfCusLegalMember = new MfCusLegalMember();
				mfCusLegalMember.setCusNo(mfCusCustomer.getCusNo());
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusLegalMember", mfCusLegalMember));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tablecuslegm00001", "tableTag",
						(List<MfCusLegalMember>) mfCusLegalMemberFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
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
		FormData formcuslegm00002 = formService.getFormData("cuslegm00002");
		getFormValue(formcuslegm00002, getMapByJson(ajaxData));
		MfCusLegalMember mfCusLegalMemberJsp = new MfCusLegalMember();
		setObjValue(formcuslegm00002, mfCusLegalMemberJsp);
		MfCusLegalMember mfCusLegalMember = mfCusLegalMemberFeign.getById(mfCusLegalMemberJsp);
		if (mfCusLegalMember != null) {
			try {
				mfCusLegalMember = (MfCusLegalMember) EntityUtil.reflectionSetVal(mfCusLegalMember, mfCusLegalMemberJsp,
						getMapByJson(ajaxData));
				mfCusLegalMemberFeign.update(mfCusLegalMember);
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusLegalMemberAction").getAddModel();
			}
			FormData formcuslegm00003 = formService.getFormData(formId);
			getFormValue(formcuslegm00003, map);
			if (this.validateFormData(formcuslegm00003)) {
				MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
				setObjValue(formcuslegm00003, mfCusLegalMember);
				mfCusLegalMemberFeign.update(mfCusLegalMember);

				// getTableData();
				String cusNo = mfCusLegalMember.getCusNo();
				mfCusLegalMember = new MfCusLegalMember();
				mfCusLegalMember.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusLegalMember", mfCusLegalMember));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tablecuslegm00001", "tableTag",
						(List<MfCusLegalMember>) mfCusLegalMemberFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String legalMemberId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcuslegm00002 = formService.getFormData("cuslegm00002");
		MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
		mfCusLegalMember.setLegalMemberId(legalMemberId);
		mfCusLegalMember = mfCusLegalMemberFeign.getById(mfCusLegalMember);
		getObjValue(formcuslegm00002, mfCusLegalMember, formData);
		if (mfCusLegalMember != null) {
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
	 * @param ajaxData
	 * @param cusNo
	 * @param tableId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String legalMemberId, String ajaxData, String cusNo, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
		mfCusLegalMember.setLegalMemberId(legalMemberId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCusLegalMember = (MfCusLegalMember) JSONObject.toBean(jb, MfCusLegalMember.class);
			mfCusLegalMemberFeign.delete(mfCusLegalMember);
			getTableData(dataMap, cusNo, tableId);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
		mfCusLegalMember.setCusNo(cusNo);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusLegalMemberAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusLegalMemberAction表单信息没有查询到");
		} else {
			FormData formcuslegm00003 = formService.getFormData(formId);
			if (formcuslegm00003.getFormId() == null) {
				// logger.error("客户类型为" + mfCusCustomer.getCusType() +
				// "的MfCusLegalMemberAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcuslegm00003);
				getObjValue(formcuslegm00003, mfCusLegalMember);
			}
			model.addAttribute("formcuslegm00003", formcuslegm00003);
		}

		model.addAttribute("mfCusFormConfig", mfCusFormConfig);
		model.addAttribute("mfCusLegalMember", mfCusLegalMember);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("formId", formId);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalMember_Insert";
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
		FormData formcuslegm00002 = formService.getFormData("cuslegm00002");
		getFormValue(formcuslegm00002);
		MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
		setObjValue(formcuslegm00002, mfCusLegalMember);
		mfCusLegalMemberFeign.insert(mfCusLegalMember);
		getObjValue(formcuslegm00002, mfCusLegalMember);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusLegalMember", mfCusLegalMember));
		List<MfCusLegalMember> mfCusLegalMemberList = (List<MfCusLegalMember>) mfCusLegalMemberFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formcuslegm00002", formcuslegm00002);
		model.addAttribute("mfCusLegalMemberList", mfCusLegalMemberList);
		model.addAttribute("mfCusLegalMember", mfCusLegalMember);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalMember_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String legalMemberId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
		mfCusLegalMember.setLegalMemberId(legalMemberId);
		mfCusLegalMember = mfCusLegalMemberFeign.getById(mfCusLegalMember);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusLegalMember.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusLegalMemberAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			if ("1".equals(mfCusFormConfig.getShowType())) {
				formId = mfCusFormConfig.getShowModelDef();
			} else {
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusLegalMemberAction表单信息没有查询到");
		} else {
			FormData formcuslegm00003 = formService.getFormData(formId);
			if (formcuslegm00003.getFormId() == null) {
				// logger.error("客户类型为" + mfCusCustomer.getCusType() +
				// "的MfCusLegalMemberAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcuslegm00003);
				getObjValue(formcuslegm00003, mfCusLegalMember);
			}
			model.addAttribute("formcuslegm00003", formcuslegm00003);
		}

		model.addAttribute("mfCusFormConfig", mfCusFormConfig);
		model.addAttribute("mfCusLegalMember", mfCusLegalMember);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("formId", formId);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalMember_Detail";
	}

	/**
	 * 删除
	 * 
	 * @param cusNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String legalMemberId, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
		mfCusLegalMember.setLegalMemberId(legalMemberId);
		mfCusLegalMemberFeign.delete(mfCusLegalMember);
		return getListPage(model, cusNo);
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
		FormData formcuslegm00002 = formService.getFormData("cuslegm00002");
		getFormValue(formcuslegm00002);
		boolean validateFlag = this.validateFormData(formcuslegm00002);
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
		FormData formcuslegm00002 = formService.getFormData("cuslegm00002");
		getFormValue(formcuslegm00002);
		boolean validateFlag = this.validateFormData(formcuslegm00002);
	}

	/**
	 * 
	 * 方法描述： 操作完成刷新列表
	 * 
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @param dataMap
	 * @param cusNo
	 * @param tableId
	 * @date 2016-6-1 上午11:38:32
	 */
	private void getTableData(Map<String, Object> dataMap, String cusNo, String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
		mfCusLegalMember.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusLegalMember", mfCusLegalMember));
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				(List<MfCusLegalMember>) mfCusLegalMemberFeign.findByPage(ipage).getResult(), null, true);
		dataMap.put("tableData", tableHtml);
	}

}
