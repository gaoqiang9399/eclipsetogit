package app.component.cus.controller;

import java.util.ArrayList;
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
import app.component.cus.entity.MfCusEquityInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusEquityInfoFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfCusEquityInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 31 15:43:25 CST 2016
 **/
@Controller
@RequestMapping("/mfCusEquityInfo")
public class MfCusEquityInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusEquityInfoBo
	@Autowired
	private MfCusEquityInfoFeign mfCusEquityInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	// 全局变量

	// 异步参数

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		mfCusEquityInfo.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusEquityInfo", mfCusEquityInfo));
		List<MfCusEquityInfo> mfCusEquityInfoList = (List<MfCusEquityInfo>) mfCusEquityInfoFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("query", "");
		model.addAttribute("mfCusEquityInfoList", mfCusEquityInfoList);
		return "/component/cus/MfCusEquityInfo_List";
	}

	/**
	 * 
	 * 方法描述： 获得大表单中股权投资情况信息列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-5-31 下午4:13:52
	 */
	@RequestMapping(value = "/getListPageBig")
	public String getListPageBig(Model model, String ajaxData, String cusNo) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		FormData formcusequ00001 = formService.getFormData("cusequ00001");
		List<MfCusEquityInfo> mfCusEquityInfoList = new ArrayList<MfCusEquityInfo>();
		try {
			MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
			mfCusEquityInfo.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusEquityInfo", mfCusEquityInfo));
			mfCusEquityInfoList = (List<MfCusEquityInfo>) mfCusEquityInfoFeign.findByPage(ipage).getResult();
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("formcusequ00001", formcusequ00001);
		model.addAttribute("mfCusEquityInfoList", mfCusEquityInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusEquityInfo_ListBig";
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
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		try {
			mfCusEquityInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusEquityInfo.setCriteriaList(mfCusEquityInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusEquityInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusEquityInfo", mfCusEquityInfo));
			ipage = mfCusEquityInfoFeign.findByPage(ipage);
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusEquityInfoAction").getAddModel();
			}
			FormData formcusequ00003 = formService.getFormData(formId);

			getFormValue(formcusequ00003, map);
			if (this.validateFormData(formcusequ00003)) {
				MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
				setObjValue(formcusequ00003, mfCusEquityInfo);
				if (mfCusEquityInfo.getPushCapitalType() !=null && mfCusEquityInfo.getPushCapitalType().length() == 1) {
					mfCusEquityInfo.setPushCapitalType(mfCusEquityInfo.getPushCapitalType() + "|");
				}
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusEquityInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusEquityInfo.setCusName(cusName);
				//企业对外投资社会信用代码不能与当前企业客户重复
				if("B".equals(mfCusEquityInfo.getIdType()) && "B".equals(mfCusCustomer.getIdType()))
				{
					if(mfCusEquityInfo.getOrgNo().equals(mfCusCustomer.getIdNum()))
					{
						dataMap.put("flag", "error");
						dataMap.put("msg", "社会信用代码与当前企业客户重复！");
						return dataMap;
					}
				}
				mfCusEquityInfoFeign.insert(mfCusEquityInfo);
				// getTableData();
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusEquityInfo.getCusNo(),
						mfCusEquityInfo.getRelNo());// 更新客户信息完整度
				String relNo = mfCusEquityInfo.getRelNo();
				mfCusEquityInfo.setCusNo(mfCusCustomer.getCusNo());
				mfCusEquityInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusEquityInfo", mfCusEquityInfo));
				String tableHtml = jtu.getJsonStr("tablecusequ00001", "tableTag",
						(List<MfCusEquityInfo>) mfCusEquityInfoFeign.findByPage(ipage).getResult(), null, true);
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
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		getFormValue(formcusequ00002, getMapByJson(ajaxData));
		MfCusEquityInfo mfCusEquityInfoJsp = new MfCusEquityInfo();
		setObjValue(formcusequ00002, mfCusEquityInfoJsp);
		MfCusEquityInfo mfCusEquityInfo = mfCusEquityInfoFeign.getById(mfCusEquityInfoJsp);
		if (mfCusEquityInfo != null) {
			try {
				mfCusEquityInfo = (MfCusEquityInfo) EntityUtil.reflectionSetVal(mfCusEquityInfo, mfCusEquityInfoJsp,
						getMapByJson(ajaxData));
				mfCusEquityInfoFeign.update(mfCusEquityInfo);
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
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		try {

			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusEquityInfoAction").getAddModel();
			}
			FormData formcusequ00003 = formService.getFormData(formId);

			getFormValue(formcusequ00003, map);
			if (this.validateFormData(formcusequ00003)) {
				setObjValue(formcusequ00003, mfCusEquityInfo);
				mfCusEquityInfoFeign.update(mfCusEquityInfo);

				// getTableData();
				String cusNo = mfCusEquityInfo.getCusNo();
				mfCusEquityInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusEquityInfo", mfCusEquityInfo));
				String tableHtml = jtu.getJsonStr("tablecusequ00001", "tableTag",
						(List<MfCusEquityInfo>) mfCusEquityInfoFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String equityInfoId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		mfCusEquityInfo.setEquityInfoId(equityInfoId);
		mfCusEquityInfo = mfCusEquityInfoFeign.getById(mfCusEquityInfo);
		getObjValue(formcusequ00002, mfCusEquityInfo, formData);
		if (mfCusEquityInfo != null) {
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
	public Map<String, Object> deleteAjax(String equityInfoId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		mfCusEquityInfo.setEquityInfoId(equityInfoId);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusEquityInfo = (MfCusEquityInfo)JSONObject.toBean(jb,
			// MfCusEquityInfo.class);
			mfCusEquityInfoFeign.delete(mfCusEquityInfo);
			// getTableData();
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
	public String input(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusequ00003 = null;
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		mfCusEquityInfo.setCusNo(cusNo);
		mfCusEquityInfo.setRelNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		mfCusEquityInfo.setCusName(mfCusCustomer.getCusName());
		mfCusEquityInfo.setCusNo(mfCusCustomer.getCusNo());
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusEquityInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// //
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusEquityInfoAction表单信息没有查询到");
		} else {
			formcusequ00003 = formService.getFormData(formId);
			if (formcusequ00003.getFormId() == null) {
				// //
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusEquityInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusequ00003);
				getObjValue(formcusequ00003, mfCusEquityInfo);
			}
		}
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		String ajaxData = items;
		model.addAttribute("formcusequ00003", formcusequ00003);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/cus/MfCusEquityInfo_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-9-25 下午6:24:57
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String ajaxData, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusequ00003 = null;
		String formId = "";
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		mfCusEquityInfo.setCusNo(cusNo);
		mfCusEquityInfo.setRelNo(relNo);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		mfCusEquityInfo.setCusName(mfCusCustomer.getCusName());
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusEquityInfoAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// //
			// logger.error("产品类型为"+kindNo+"的MfCusEquityInfoAction表单信息没有查询到");
		} else {
			formcusequ00003 = formService.getFormData(formId);
			if (formcusequ00003.getFormId() == null) {
				// //
				// logger.error("产品类型为"+kindNo+"的MfCusEquityInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusequ00003);
				getObjValue(formcusequ00003, mfCusEquityInfo);
			}
		}
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		ajaxData = items;
		model.addAttribute("formcusequ00003", formcusequ00003);
		model.addAttribute("query", "");
		return "/component/cus/MfCusEquityInfo_Insert";
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
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		getFormValue(formcusequ00002);
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		setObjValue(formcusequ00002, mfCusEquityInfo);
		mfCusEquityInfoFeign.insert(mfCusEquityInfo);
		getObjValue(formcusequ00002, mfCusEquityInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusEquityInfo", mfCusEquityInfo));
		List<MfCusEquityInfo> mfCusEquityInfoList = (List<MfCusEquityInfo>) mfCusEquityInfoFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusequ00002", formcusequ00002);
		model.addAttribute("mfCusEquityInfoList", mfCusEquityInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusEquityInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String equityInfoId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusequ00003 = null;
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		mfCusEquityInfo.setEquityInfoId(equityInfoId);
		mfCusEquityInfo = mfCusEquityInfoFeign.getById(mfCusEquityInfo);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusEquityInfo.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusEquityInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			if ("1".equals(mfCusFormConfig.getShowType())) {
				formId = mfCusFormConfig.getShowModelDef();
			} else {
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if (StringUtil.isEmpty(formId)) {
			// //
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusEquityInfoAction表单信息没有查询到");
		} else {
			formcusequ00003 = formService.getFormData(formId);
			if (formcusequ00003.getFormId() == null) {
				// //
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusEquityInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusequ00003);
				getObjValue(formcusequ00003, mfCusEquityInfo);
			}
		}
		ajaxData = items;
		model.addAttribute("formcusequ00003", formcusequ00003);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/cus/MfCusEquityInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String equityInfoId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		mfCusEquityInfo.setEquityInfoId(equityInfoId);
		mfCusEquityInfoFeign.delete(mfCusEquityInfo);
		return getListPage(model, cusNo);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String ajaxData, String cusNo, String equityInfoId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusEquityInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// //
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusEquityInfoAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			request.setAttribute("ifBizManger","3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusequ00004 = formService.getFormData(formId);
			MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
			mfCusEquityInfo.setEquityInfoId(equityInfoId);
			mfCusEquityInfo = mfCusEquityInfoFeign.getById(mfCusEquityInfo);
			getObjValue(formcusequ00004, mfCusEquityInfo);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusequ00004, "propertySeeTag", "");
			if (mfCusEquityInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusEquityInfo);
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
	public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusequ00004 = formService.getFormData(formId);
		getFormValue(formcusequ00004, getMapByJson(ajaxData));
		MfCusEquityInfo mfCusEquityInfoNew = new MfCusEquityInfo();
		setObjValue(formcusequ00004, mfCusEquityInfoNew);
		mfCusEquityInfo.setEquityInfoId(mfCusEquityInfoNew.getEquityInfoId());
		mfCusEquityInfo = mfCusEquityInfoFeign.getById(mfCusEquityInfo);
		if (mfCusEquityInfo != null) {
			try {
				mfCusEquityInfo = (MfCusEquityInfo) EntityUtil.reflectionSetVal(mfCusEquityInfo, mfCusEquityInfoNew,
						getMapByJson(ajaxData));
				mfCusEquityInfoFeign.update(mfCusEquityInfo);
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
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		getFormValue(formcusequ00002);
		boolean validateFlag = this.validateFormData(formcusequ00002);
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
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		getFormValue(formcusequ00002);
		boolean validateFlag = this.validateFormData(formcusequ00002);
	}

	/**
	 * 
	 * 方法描述： 操作完成，刷新列表
	 * 
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2016-5-31 下午4:31:08
	 */
	private void getTableData(String cusNo, String tableId, Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		mfCusEquityInfo.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusEquityInfo", mfCusEquityInfo));
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				(List<MfCusEquityInfo>) mfCusEquityInfoFeign.findByPage(ipage).getResult(), null, true);
		dataMap.put("tableData", tableHtml);
	}

}
