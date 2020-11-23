package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import app.tech.oscache.CodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.google.gson.Gson;

import app.base.User;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCoreCompany;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusCoreCompanyFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusTypeFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfCusCoreCompanyAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jul 29 17:36:57 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCoreCompany")
public class MfCusCoreCompanyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCoreCompanyFeign mfCusCoreCompanyFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;
	@Autowired
	private MfCusCreditApplyFeign mfCusCreditApplyFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusCoreCompany_List";
	}
	
	
	@RequestMapping(value = "/getListPageForSelect")
	public String getListPageForSelect(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusCoreCompany_ListForSelect";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		try {
			mfCusCoreCompany.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCoreCompany.setCriteriaList(mfCusCoreCompany, ajaxData);// 我的筛选
			mfCusCoreCompany.setCustomSorts(ajaxData);// 自定义排序参数赋值
			// this.getRoleConditions(mfCusCoreCompany,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCoreCompany", mfCusCoreCompany));
			ipage = mfCusCoreCompanyFeign.findByPage(ipage);
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
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
			String formId = String.valueOf(map.get("formId"));
			FormData formCoreCompany0002 = formService.getFormData(formId);
			getFormValue(formCoreCompany0002, map);
			if (this.validateFormData(formCoreCompany0002)) {
				MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
				setObjValue(formCoreCompany0002, mfCusCoreCompany);
				mfCusCoreCompanyFeign.insert(mfCusCoreCompany);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			// logger.error("渠道新增失败",e);
			throw new Exception(e.getMessage());
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCoreCompany mfCusCoreCompanyJsp = new Gson().fromJson(new Gson().toJson(getMapByJson(ajaxData)),
				MfCusCoreCompany.class);
		MfCusCoreCompany mfCusCoreCompany = mfCusCoreCompanyFeign.getById(mfCusCoreCompanyJsp);
		if (mfCusCoreCompany != null) {
			try {
				mfCusCoreCompany = (MfCusCoreCompany) EntityUtil.reflectionSetVal(mfCusCoreCompany, mfCusCoreCompanyJsp,
						getMapByJson(ajaxData));
				mfCusCoreCompanyFeign.update(mfCusCoreCompany);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		String formId = "";
		String query = "";
		request.setAttribute("ifBizManger", "3");
		try {
			Map<String, Object> map = getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
			formId = String.valueOf(map.get("formId"));
			FormData formCoreCompany0002 = formService.getFormData(formId);
			getFormValue(formCoreCompany0002, map);
			if (this.validateFormData(formCoreCompany0002)) {
				setObjValue(formCoreCompany0002, mfCusCoreCompany);
				mfCusCoreCompanyFeign.update(mfCusCoreCompany);
				Map<String, String> cusInfoMap = this.transViewBean(mfCusCoreCompany.getCoreCompanyUid());
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCoreCompany.getCusType(),
						"MfCusCoreCompanyAction");
				if (mfCusFormConfig == null) {

				} else {
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formToAjaxByOne = formService.getFormData(formId);
				if (formToAjaxByOne.getFormId() == null) {
					// logger.error("渠道商客户类型为" + mfCusCoreCompany.getCusType() +
					// "的MfCusCoreCompanyAction表单form" + formId + ".xml文件不存在");
				}
				getFormValue(formToAjaxByOne);
				getObjValue(formToAjaxByOne, mfCusCoreCompany);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formToAjaxByOne, "propertySeeTag", query);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("cusInfo", cusInfoMap);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获得渠道基本信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2018-3-6 下午6:28:05
	 */
	@RequestMapping("/getTrenchBaseHtmlAjax")
	@ResponseBody
	public Map<String, Object> getTrenchBaseHtmlAjax(String coreCompanyUid) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		request.setAttribute("ifBizManger", "3");
		String formId = "";
		try {
			mfCusCoreCompany.setCoreCompanyUid(coreCompanyUid);
			mfCusCoreCompany = mfCusCoreCompanyFeign.getById(mfCusCoreCompany);
			MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCoreCompany.getCusType(),
					"MfCusCoreCompanyAction");
			if (mfCusFormConfig == null) {

			} else {
				formId = mfCusFormConfig.getShowModelDef();
			}
			FormData formToAjaxByOne = formService.getFormData(formId);
			if (formToAjaxByOne.getFormId() == null) {
				// logger.error("渠道商客户类型为" + mfCusCoreCompany.getCusType() +
				// "的MfCusCoreCompanyAction表单form" + formId + ".xml文件不存在");
			}
			getFormValue(formToAjaxByOne);
			getObjValue(formToAjaxByOne, mfCusCoreCompany);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formToAjaxByOne, "propertySeeTag", "");
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
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
	public Map<String, Object> getByIdAjax(String coreCompanyUid) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formCoreCompany0002 = formService.getFormData("trench0002");
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		mfCusCoreCompany.setCoreCompanyUid(coreCompanyUid);
		mfCusCoreCompany = mfCusCoreCompanyFeign.getById(mfCusCoreCompany);
		getObjValue(formCoreCompany0002, mfCusCoreCompany, formData);
		if (mfCusCoreCompany != null) {
			dataMap.put("flag", "success");
			dataMap.put("mfCusCoreCompany", mfCusCoreCompany);
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
	public Map<String, Object> deleteAjax(String coreCompanyUid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		mfCusCoreCompany.setCoreCompanyId(coreCompanyUid);
		try {
			mfCusCoreCompanyFeign.delete(mfCusCoreCompany);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
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
	public String input(Model model, String typeNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "";
		if (StringUtil.isNotEmpty(typeNo)) {
			MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType(typeNo, "MfCusCoreCompanyAction");
			if (mc != null) {
				formId = mc.getAddModelDef();
			}
		} else {
			MfCusType mfCusType = new MfCusType();
			mfCusType.setBaseType(BizPubParm.CUS_BASE_TYPE_HEXIAN);
			mfCusType.setUseFlag("1");
			List<MfCusType> list = mfCusTypeFeign.getAllList(mfCusType);

			if (list != null && list.size() > 0) {
				String cusType = list.get(0).getTypeNo();// 第一个会是表单的默认项
				MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType(cusType, "MfCusCoreCompanyAction");
				if (mc != null) {
					formId = mc.getAddModelDef();
				}
			}
		}
		if (StringUtil.isEmpty(formId)) {
			formId = "trench0002";
		}
		FormData formCoreCompany0002 = formService.getFormData(formId);
		getFormValue(formCoreCompany0002);
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		mfCusCoreCompany.setCusType(typeNo);
		getObjValue(formCoreCompany0002, mfCusCoreCompany);
		model.addAttribute("formCoreCompany0002", formCoreCompany0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusCoreCompany_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCoreCompany0002 = formService.getFormData("trench0002");
		getFormValue(formCoreCompany0002);
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		setObjValue(formCoreCompany0002, mfCusCoreCompany);
		mfCusCoreCompanyFeign.insert(mfCusCoreCompany);
		getObjValue(formCoreCompany0002, mfCusCoreCompany);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = new Ipage();
		ipage.setParams(this.setIpageParams("mfCusCoreCompany", mfCusCoreCompany));
		List<MfCusCoreCompany> mfCusCoreCompanyList = (List<MfCusCoreCompany>) mfCusCoreCompanyFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formCoreCompany0002", formCoreCompany0002);
		model.addAttribute("mfCusCoreCompanyList", mfCusCoreCompanyList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusCoreCompany_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String coreCompanyUid) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtrench0001 = formService.getFormData("trench0001");
		getFormValue(formtrench0001);
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		mfCusCoreCompany.setCoreCompanyId(coreCompanyUid);
		mfCusCoreCompany = mfCusCoreCompanyFeign.getById(mfCusCoreCompany);
		getObjValue(formtrench0001, mfCusCoreCompany);
		model.addAttribute("formtrench0001", formtrench0001);
		model.addAttribute("query", "");
		// //选择组件需要数据
		// SysInterface
		// sysInterface=(SysInterface)SourceTemplate.getSpringContextInstance().getBean("sysInterface",
		// SysInterface.class);
		// List<SysUser> userList=sysInterface.getAllUser(null);
		// List<Map<String,String>> usersData=new
		// ArrayList<Map<String,String>>();
		// for(SysUser user:userList){
		// Map<String,String> map=new HashMap<String,String>();
		// map.put("id", user.getOpNo());
		// map.put("name", user.getOpName()+"_"+user.getOpNo());
		// usersData.add(map);
		// }
		// dataMap=new HashMap<String,Object>();
		// dataMap.put("usersData", new Gson().toJson(usersData));
		return "/component/cus/MfCusCoreCompany_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String coreCompanyUid) throws Exception {
		ActionContext.initialize(request, response);
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		mfCusCoreCompany.setCoreCompanyId(coreCompanyUid);
		mfCusCoreCompanyFeign.delete(mfCusCoreCompany);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCoreCompany0002 = formService.getFormData("trench0002");
		getFormValue(formCoreCompany0002);
		boolean validateFlag = this.validateFormData(formCoreCompany0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCoreCompany0002 = formService.getFormData("trench0002");
		getFormValue(formCoreCompany0002);
		boolean validateFlag = this.validateFormData(formCoreCompany0002);
	}

	/**
	 * 方法描述： 获取渠道来源分页列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-7-29 下午5:43:01
	 */
	@RequestMapping(value = "/getChannelAjax")
	@ResponseBody
	public Map<String, Object> getChannelAjax(String ajaxData, int pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Ipage ipage = this.getIpage();
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		if (ajaxData != null) {
			mfCusCoreCompany.setCustomQuery(ajaxData);// 自定义查询参数赋值
		}
		mfCusCoreCompany.setCoreCompanyOpNo(User.getRegNo(request));
		ipage.setParams(this.setIpageParams("mfCusCoreCompany", mfCusCoreCompany));
		// ipage = mfCusCoreCompanyFeign.getChannelListPage(ipage);
		dataMap.put("ipage", ipage);
		return dataMap;
	}

	/**
	 * 获取视角所需对象信息（头部最基础的信息） 渠道商、资金机构共用
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getByIdForViewAjax")
	@ResponseBody
	public Map<String, Object> getByIdForViewAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// mfCusCoreCompany = new MfCusCoreCompany();
		// mfCusCoreCompany.setCoreCompanyId(coreCompanyUid);
		// mfCusCoreCompany = mfCusCoreCompanyFeign.getById(mfCusCoreCompany);
		Map<String, String> cusInfoMap = this.transViewBean(cusNo);
		dataMap.put("cusInfo", cusInfoMap);
		return dataMap;
	}

	/**
	 * 调视角
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCoreCompanyView")
	public String getCoreCompanyView(Model model, String coreCompanyUid, String busEntrance, String baseType)
			throws Exception {
		ActionContext.initialize(request, response);
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("baseType", baseType);
			MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
			mfCusCoreCompany.setCoreCompanyUid(coreCompanyUid);
			mfCusCoreCompany = mfCusCoreCompanyFeign.getById(mfCusCoreCompany);
			parmMap.put("cusType", mfCusCoreCompany.getCusType());
			parmMap.put("trenchUid", coreCompanyUid);// 参与其他关联都用的trenchUid
			parmMap.put("coreCompanyId", mfCusCoreCompany.getCoreCompanyId());
			parmMap.put("operable", "operable");// 底部显示待完善信息块
			String generalClass = "cus";
			parmMap.put("docParm", "cusNo=" + coreCompanyUid + "&relNo="
					+ coreCompanyUid + "&scNo=" + BizPubParm.SCENCE_TYPE_DOC_CUS);
			Map<String, Object> cusViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, parmMap);
			dataMap.put("cusNo", mfCusCoreCompany.getCoreCompanyUid());
			dataMap.put("baseType", baseType);
			dataMap.put("cusType", mfCusCoreCompany.getCusType());
			dataMap.putAll(cusViewMap);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap = mfCusCreditApplyFeign.getCreditData(coreCompanyUid, "","","");
			Map<String,Object> cusCreditDataMap = (Map<String,Object>)resultMap.get("cusCreditData");
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("cusNo", coreCompanyUid);
			model.addAttribute("cusName", mfCusCoreCompany.getCoreCompanyName());
			model.addAttribute("busEntrance", busEntrance);
			model.addAttribute("entrance", "credit");
			model.addAttribute("relId", (String)cusCreditDataMap.get("creditAppId"));
			model.addAttribute("creditAppId", (String)cusCreditDataMap.get("creditAppId"));
			model.addAttribute("query", "");
		} catch (Exception e) {
			// logger.error("获取渠道商详情视角失败",e);
			e.printStackTrace();
			throw e;
		}
		return "component/cus/commonview/MfCusCustomer_ComView";
	}

	/**
	 * 将实体对象转换为主视图页面需要的参数对象
	 * 
	 * @param 客户号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/transViewBean")
	@ResponseBody
	public Map<String, String> transViewBean(String cusNo) throws Exception {
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		Map<String, String> cusInfoMap = new HashMap<String, String>();
		if (mfCusCustomer != null) {
			// 名称
			cusInfoMap.put("cusName", mfCusCustomer.getCusName());
			// 基本类型
			cusInfoMap.put("cusBaseType", mfCusCustomer.getCusType().substring(0, 1));
			cusInfoMap.put("cusType", mfCusCustomer.getCusType());
			cusInfoMap.put("uId", mfCusCustomer.getCusNo());// 业务编号
			// 获取客户类型汉字
			MfCusType mfCusType = new MfCusType();
			mfCusType.setTypeNo(mfCusCustomer.getCusType());
			mfCusType = mfCusTypeFeign.getById(mfCusType);
			cusInfoMap.put("cusNameRate", mfCusType != null ? mfCusType.getTypeName() : "未知");
			// 对接人联系方式
			cusInfoMap.put("contactsTel", mfCusCustomer.getContactsTel());
			// 对接人姓名
			cusInfoMap.put("contactsName", mfCusCustomer.getContactsName());
			// 资料完整度
			cusInfoMap.put("infIntegrity", mfCusCustomer.getInfIntegrity());
			// 是否上传头像图片
			cusInfoMap.put("ifUploadHead", mfCusCustomer.getIfUploadHead());
			// 头像图片路径
			cusInfoMap.put("headImg", mfCusCustomer.getHeadImg());

		}
		return cusInfoMap;
	}

	/**
	 * 取不同小类的客户类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusTypeToShowAjax")
	@ResponseBody
	public Map<String, Object> getCusTypeToShowAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<>();
		String baseType = request.getParameter("baseType");
		MfCusType mfCusType = new MfCusType();
		mfCusType.setBaseType(baseType);
		mfCusType.setUseFlag("1");
		List<MfCusType> list = mfCusTypeFeign.getAllList(mfCusType);
		dataMap.put("cusTypeList", list);
		return dataMap;
	}

	/**
	 * 取不属于某个客户小类 不 对应的类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCusTypeNotShowAjax")
	@ResponseBody
	public Map<String, Object> getCusTypeNotShowAjax(String baseType) throws Exception {
		ActionContext.initialize(request, response);
		MfCusType mfCusType = new MfCusType();
		mfCusType.setBaseType(baseType);
		mfCusType.setUseFlag("1");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<MfCusType> list = mfCusTypeFeign.getAllListNotThisBaseType(mfCusType);
		dataMap.put("cusTypeList", list);
		return dataMap;
	}

	/**
	 * 渠道商的历史业务统计数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/getTrenchBusHisAjax")
	@ResponseBody
	public Map<String, Object> getTrenchBusHisAjax() {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String trenchUid = request.getParameter("trenchUid");
			// Map<String,String>
			// resulMap=mfCusCoreCompanyFeign.getTrenchBusHisAjax(trenchUid);
			dataMap.put("flag", "success");
			// dataMap.putAll(resulMap);
		} catch (Exception e) {
			// logger.error("获取渠道商历史数据失败，",e);
		}
		return dataMap;
	}

	/**
	 * 列表打开渠道额度预警页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTrenchWarnListPage")
	public String getTrenchWarnListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusCoreCompany_TrenchWarnList";
	}

	/***
	 * 渠道额度预警列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findTrenchWarnByPageAjax")
	@ResponseBody
	public Map<String, Object> findTrenchWarnByPageAjax(String ajaxData, String tableId, String tableType,
			Integer pageNo, Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		try {
			mfCusCoreCompany.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCoreCompany.setCriteriaList(mfCusCoreCompany, ajaxData);// 我的筛选
			mfCusCoreCompany.setCustomSorts(ajaxData);// 自定义排序参数赋值
			// this.getRoleConditions(mfCusCoreCompany,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCoreCompany", mfCusCoreCompany));
			// ipage = mfCusCoreCompanyFeign.findTrenchWarnByPage(ipage);
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

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getTrenchData")
	@ResponseBody
	public Map<String, Object> getTrenchData() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		// List<MfCusCoreCompany> mfCusCoreCompanyList =
		// mfCusCoreCompanyFeign.getMfCusCoreCompanyList();

		JSONArray array = new JSONArray();
		/*
		 * if(mfCusCoreCompanyList!=null&&mfCusCoreCompanyList.size()>0){ for
		 * (int i = 0; i < mfCusCoreCompanyList.size(); i++) { JSONObject obj =
		 * new JSONObject(); obj.put("id",
		 * mfCusCoreCompanyList.get(i).getCoreCompanyUid()); obj.put("name",
		 * mfCusCoreCompanyList.get(i).getCoreCompanyName()); array.add(obj); }
		 * }
		 */
		dataMap.put("items", array.toString());
		return dataMap;
	}

	/**
	 * 方法描述： 获得子渠道列表htmlStr
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2018-3-18 下午3:54:21
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/getTrenchBusListHtmlStrAjax")
	@ResponseBody
	public Map<String, Object> getTrenchBusListHtmlStrAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		mfCusCoreCompany.setCoreCompanyUid((String) request.getSession().getAttribute("channelSourceNo"));
		mfCusCoreCompany = mfCusCoreCompanyFeign.getById(mfCusCoreCompany);
		MfCusCoreCompany busTrench = new MfCusCoreCompany();
		// busTrench.setTrenchHierarchyNo(mfCusCoreCompany.getTrenchHierarchyNo());
		// List<MfCusCoreCompany> mfCusCoreCompanyList =
		// mfCusCoreCompanyFeign.getChildTrenchList(busTrench);
		JsonTableUtil jtu = new JsonTableUtil();
		// String tableHtml = jtu.getJsonStr("tablechildTrenchList", "tableTag",
		// mfCusCoreCompanyList, null, true);
		dataMap.put("flag", "success");
		// dataMap.put("tableHtml", tableHtml);

		return dataMap;
	}

	/**
	 * 资金机构的历史业务统计数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCoreCompanyBusHisAjax")
	@ResponseBody
	public Map<String, Object> getCoreCompanyBusHisAjax(String cusNo) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfBusAgenciesController.class);
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> resulMap = mfCusCoreCompanyFeign.getCoreCompanyBusHisAjax(cusNo);
			dataMap.put("flag", "success");
			dataMap.putAll(resulMap);
		} catch (Exception e) {
			logger.error("获取资金机构历史数据失败，", e);
            throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getDetailInfoAjax")
	@ResponseBody
	public Map<String, Object> getDetailInfoAjax(String cusNo,String cusType) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfBusAgenciesController.class);
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfCusFormConfig mc=mfCusFormConfigFeign.getByCusType(cusType, "MfCusCoreCompanyAction");
		String formId = "CoreCompanyDetail";
		if(mc!=null){
			formId =mc.getShowModelDef();
		}
		try {
			MfCusCoreCompany mfCusCoreCompany=new MfCusCoreCompany();
			mfCusCoreCompany.setCoreCompanyUid(cusNo);
			mfCusCoreCompany=mfCusCoreCompanyFeign.getById(mfCusCoreCompany);
			MfCusCreditContract mfCusCreditContract=new MfCusCreditContract();
			mfCusCreditContract.setCusNo(cusNo);
			mfCusCreditContract=mfCusCreditContractFeign.getByCusNo(mfCusCreditContract);
			if(mfCusCreditContract != null){
				mfCusCoreCompany.setCreditSum(mfCusCreditContract.getCreditSum());
				mfCusCoreCompany.setAuthBal(mfCusCreditContract.getAuthBal());
				mfCusCoreCompany.setIsCeilingLoop(mfCusCreditContract.getIsCeilingLoop());
			}
			FormData formcommon = formService.getFormData(formId);
			getFormValue(formcommon);
			getObjValue(formcommon, mfCusCoreCompany);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
			dataMap.put("flag", "success");
			dataMap.put("htmlStr",htmlStr);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			logger.error("获取核心企业详情信息失败",e);
		}
		return dataMap;
	}

	@RequestMapping(value = "/getByCoreCompanyUidAjax")
	@ResponseBody
	public Map<String, Object> getByCoreCompanyUidAjax(String coreCompanyUid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
		mfCusCoreCompany.setCoreCompanyUid(coreCompanyUid);
		mfCusCoreCompany = mfCusCoreCompanyFeign.getById(mfCusCoreCompany);
		if (mfCusCoreCompany != null) {
			CodeUtils codeUtils = new CodeUtils();
			String  onlineCreditType =  codeUtils.getSingleValByKey("APP_ONLINE_CREDIT_TYPE");
			if("0".equals(onlineCreditType)){
				dataMap.put("flag", "success");
				dataMap.put("mfCusCoreCompany", mfCusCoreCompany);
			}else{
				MfCusCreditContract mfCusCreditContract=new MfCusCreditContract();
				mfCusCreditContract.setCreditModel("1");
				mfCusCreditContract.setCusNo(coreCompanyUid);
				 mfCusCreditContract=  mfCusCreditContractFeign.getNewestCusCreditContracByCusNoAndCreditModel(mfCusCreditContract);
				if(mfCusCreditContract != null){
					mfCusCoreCompany.setCreditSum(mfCusCreditContract.getCreditSum());
					mfCusCoreCompany.setAuthBal(mfCusCreditContract.getAuthBal());
					dataMap.put("flag", "success");
					dataMap.put("mfCusCoreCompany", mfCusCoreCompany);
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", "该核心企业未授信!");
				}
			}

		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "选择客户出错!");
		}
		return dataMap;
	}
}
