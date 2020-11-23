package app.component.pss.information.controller;

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

import app.component.nmd.entity.ParmDic;
import app.component.nmd.feign.ParmDicFeign;
import app.component.pss.information.entity.PssCustomerContacts;
import app.component.pss.information.entity.PssCustomerInfo;
import app.component.pss.information.feign.PssCustomerInfoFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.component.sys.entity.SysUser;
import app.component.sysInterface.SysInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssCustomerInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 01 16:13:22 SGT 2017
 **/
@Controller
@RequestMapping("/pssCustomerInfo")
public class PssCustomerInfoController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssCustomerInfoFeign pssCustomerInfoFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private ParmDicFeign parmDicFeign;

	// 全局变量
	// 表单变量
	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,PssCustomerInfo pssCustomerInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssCustomerInfoFeign.getAll(pssCustomerInfo), null,
				true);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		/*
		 * formpsscustomerinfo0002 =
		 * formService.getFormData("psscustomerinfo0002"); PssCustomerInfo
		 * pssCustomerInfo = new PssCustomerInfo(); Ipage ipage =
		 * this.getIpage(); pssCustomerInfoList =
		 * (List<PssCustomerInfo>)pssCustomerInfoFeign.findByPage(ipage,
		 * pssCustomerInfo).getResult();
		 */
		JSONArray pssEnabledStatusJsonArray = new CodeUtils().getJSONArrayByKeyName("YES_NO");
		this.getHttpRequest().setAttribute("pssEnabledStatusJsonArray", pssEnabledStatusJsonArray);
		model.addAttribute("pssEnabledStatusJsonArray", pssEnabledStatusJsonArray);
		model.addAttribute("query", "");
		return "/component/pss/information/PssCustomerInfo_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpsscustomerinfo0002 = formService.getFormData("psscustomerinfo0002");
		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		List<PssCustomerInfo> pssCustomerInfoList = pssCustomerInfoFeign.getAll(pssCustomerInfo);
		model.addAttribute("formpsscustomerinfo0002", formpsscustomerinfo0002);
		model.addAttribute("pssCustomerInfoList", pssCustomerInfoList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssCustomerInfo_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsscustomerinfo0002 = formService.getFormData("psscustomerinfo0002");
			getFormValue(formpsscustomerinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscustomerinfo0002)) {
				PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
				setObjValue(formpsscustomerinfo0002, pssCustomerInfo);
				pssCustomerInfoFeign.insert(pssCustomerInfo);
				getTableData(tableId,pssCustomerInfo);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		try {
			FormData formpsscustomerinfo0002 = formService.getFormData("psscustomerinfo0002");
			getFormValue(formpsscustomerinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscustomerinfo0002)) {
				pssCustomerInfo = new PssCustomerInfo();
				setObjValue(formpsscustomerinfo0002, pssCustomerInfo);
				pssCustomerInfoFeign.update(pssCustomerInfo);
				getTableData(tableId,pssCustomerInfo);// 获取列表
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
	public Map<String, Object> getByIdAjax(String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsscustomerinfo0002 = formService.getFormData("psscustomerinfo0002");
		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		pssCustomerInfo.setCusNo(cusNo);
		pssCustomerInfo = pssCustomerInfoFeign.getById(pssCustomerInfo);
		getObjValue(formpsscustomerinfo0002, pssCustomerInfo, formData);
		if (pssCustomerInfo != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String cusNo,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		pssCustomerInfo.setCusNo(cusNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssCustomerInfo = (PssCustomerInfo) JSONObject.toBean(jb, PssCustomerInfo.class);
			pssCustomerInfoFeign.delete(pssCustomerInfo);
			getTableData(tableId,pssCustomerInfo);// 获取列表
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

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		try {
			pssCustomerInfo.setCustomQuery(ajaxData);
			pssCustomerInfo.setCustomSorts(ajaxData);
			pssCustomerInfo.setCriteriaList(pssCustomerInfo, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("pssCustomerInfo",pssCustomerInfo));
			ipage = pssCustomerInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);

		} catch (Exception ex) {
			ex.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", ex.getMessage());
			throw ex;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getAddPage")
	public String getAddPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsscustomerinfo0002 = formService.getFormData("psscustomerinfo0002");

		String cusCode = pssCustomerInfoFeign.generateCusCode();

		JSONObject json = new JSONObject();

		// 销售人员选择组件
		List<SysUser> sysUserList = sysInterfaceFeign.getAllUser(new SysUser());
		JSONArray salerArray = new JSONArray();
		JSONObject salerJSONObject = new JSONObject();
		salerJSONObject.put("id", "-1");
		salerJSONObject.put("name", "(空)");
		salerArray.add(salerJSONObject);
		for (SysUser sysUser : sysUserList) {
			salerJSONObject = new JSONObject();
			salerJSONObject.put("id", sysUser.getOpNo());
			salerJSONObject.put("name", sysUser.getOpName());
			salerArray.add(salerJSONObject);
		}
		json.put("saler", salerArray);

		// 客户类别
		ParmDic queryUnit = new ParmDic();
		queryUnit.setKeyName("PSS_CUS_CATEGORY");
		List<ParmDic> cusCategoryDicList = parmDicFeign.findlist(queryUnit);
		JSONArray cusCategoryArray = new JSONArray();
		for (int i = 0; i < cusCategoryDicList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", cusCategoryDicList.get(i).getOptCode());
			jsonObject.put("name", cusCategoryDicList.get(i).getOptName());
			cusCategoryArray.add(jsonObject);
		}
		json.put("cusCategoryArray", cusCategoryArray);

		// 客户等级
		queryUnit = new ParmDic();
		queryUnit.setKeyName("PSS_CUS_GRADE");
		List<ParmDic> cusGradeDicList = parmDicFeign.findlist(queryUnit);
		JSONArray cusGradeArray = new JSONArray();
		for (int i = 0; i < cusGradeDicList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", cusGradeDicList.get(i).getOptCode());
			jsonObject.put("name", cusGradeDicList.get(i).getOptName());
			cusGradeArray.add(jsonObject);
		}
		json.put("cusGradeArray", cusGradeArray);

		JSONArray isFirstListArray = new CodeUtils().getJSONArrayByKeyName("YES_NO");
		JSONArray newisFirstListArray = new JSONArray();
		JSONObject clearIsFirstObject = null;
		for (int i = 0; i < isFirstListArray.size(); i++) {
			clearIsFirstObject = new JSONObject();
			clearIsFirstObject.put("id", isFirstListArray.getJSONObject(i).getString("optCode"));
			clearIsFirstObject.put("name", isFirstListArray.getJSONObject(i).getString("optName"));
			newisFirstListArray.add(clearIsFirstObject);
		}
		json.put("isfirstContactMan", newisFirstListArray);

		json.put("cusCode", cusCode);

		json.put("cusNo", "");

		json.put("originalAccountsReceived", 0.00);

		json.put("originalDepositReceived", 0.00);

		String ajaxData = json.toString();

		model.addAttribute("formpsscustomerinfo0002", formpsscustomerinfo0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/information/PssCustomerInfo_Detail";
	}

	@RequestMapping(value = "/saveCustomerInfoAjax")
	@ResponseBody
	public Map<String, Object> saveCustomerInfoAjax(String ajaxData,String pssCustomerContactsJson,String originalAccountsReceived,String originalDepositReceived,String saveOrEditFlag) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsscustomerinfo0002 = formService.getFormData("psscustomerinfo0002");
			getFormValue(formpsscustomerinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscustomerinfo0002)) {
				PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
				setObjValue(formpsscustomerinfo0002, pssCustomerInfo);

				List<PssCustomerContacts> pssCustomerContactsList = PssPublicUtil
						.getMapByJsonObj(new PssCustomerContacts(), pssCustomerContactsJson);

				boolean success = pssCustomerInfoFeign.doSaveCustomerInfo(pssCustomerInfo, pssCustomerContactsList,
						originalAccountsReceived, originalDepositReceived, saveOrEditFlag);

				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
				}

			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/updateEnabledStatusAjax")
	@ResponseBody
	public Map<String, Object> updateEnabledStatusAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			String cusNo = (String) jsonObject.get("cusNo");
			if (StringUtil.isEmpty(cusNo)) {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
			}
			pssCustomerInfo.setCusNo(cusNo);
			pssCustomerInfo = pssCustomerInfoFeign.getById(pssCustomerInfo);
			String enabledStatus = (String) jsonObject.get("enabledStatus");
			if (StringUtil.isNotEmpty(enabledStatus)) {
				if (PssEnumBean.ENABLED_STATUS.CLOSED.getValue().equals(enabledStatus)) {
					// 关闭
					pssCustomerInfoFeign.doCloseCustomerInfo(pssCustomerInfo);
				} else if (PssEnumBean.ENABLED_STATUS.ENABLED.getValue().equals(enabledStatus)) {
					// 启用
					pssCustomerInfoFeign.doEnableCustomerInfo(pssCustomerInfo);
				} else {
					throw new Exception(MessageEnum.FAILED_SAVE_CONTENT.getMessage("启用状态不正确"));
				}
			} else {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("启用状态"));
			}
			/**
			 * pssCustomerInfo = pssCustomerInfoFeign.getById(pssCustomerInfo);
			 * List<PssCustomerInfo> pssCustomerInfoList = new
			 * ArrayList<PssCustomerInfo>();
			 * pssCustomerInfoList.add(pssCustomerInfo);
			 * getTableDataList(pssCustomerInfoList);
			 **/
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * private void getTableDataList(List<PssCustomerInfo> list) throws
	 * Exception { JsonTableUtil jtu = new JsonTableUtil(); String tableHtml =
	 * jtu.getJsonStr(tableId,"tableTag", list, null,true);
	 * dataMap.put("tableData",tableHtml); }
	 **/
	@RequestMapping(value = "/deleteCustomerInfoAjax")
	@ResponseBody
	public Map<String, Object> deleteCustomerInfoAjax(String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (cusNo == null || "".equals(cusNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		pssCustomerInfo.setCusNo(cusNo);
		try {
			boolean success = pssCustomerInfoFeign.deleteCustomerInfo(pssCustomerInfo);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("客户删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("客户删除"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		if (StringUtil.isNotEmpty(cusNo)) {
			pssCustomerInfo.setCusNo(cusNo);
			pssCustomerInfo = pssCustomerInfoFeign.getById(pssCustomerInfo);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		if (pssCustomerInfo == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			FormData formpsscustomerinfo0002 = formService.getFormData("psscustomerinfo0002");
			getObjValue(formpsscustomerinfo0002, pssCustomerInfo);

			JSONObject json = new JSONObject();

			// 销售人员选择组件
			List<SysUser> sysUserList = sysInterfaceFeign.getAllUser(new SysUser());
			JSONArray salerArray = new JSONArray();
			JSONObject salerJSONObject = new JSONObject();
			salerJSONObject.put("id", "-1");
			salerJSONObject.put("name", "(空)");
			salerArray.add(salerJSONObject);
			for (SysUser sysUser : sysUserList) {
				salerJSONObject = new JSONObject();
				salerJSONObject.put("id", sysUser.getOpNo());
				salerJSONObject.put("name", sysUser.getOpName());
				salerArray.add(salerJSONObject);
			}
			json.put("saler", salerArray);

			// 客户类别
			ParmDic queryUnit = new ParmDic();
			queryUnit.setKeyName("PSS_CUS_CATEGORY");
			List<ParmDic> cusCategoryDicList = parmDicFeign.findlist(queryUnit);
			JSONArray cusCategoryArray = new JSONArray();
			for (int i = 0; i < cusCategoryDicList.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", cusCategoryDicList.get(i).getOptCode());
				jsonObject.put("name", cusCategoryDicList.get(i).getOptName());
				cusCategoryArray.add(jsonObject);
			}
			json.put("cusCategoryArray", cusCategoryArray);

			// 客户等级
			queryUnit = new ParmDic();
			queryUnit.setKeyName("PSS_CUS_GRADE");
			List<ParmDic> cusGradeDicList = parmDicFeign.findlist(queryUnit);
			JSONArray cusGradeArray = new JSONArray();
			for (int i = 0; i < cusGradeDicList.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", cusGradeDicList.get(i).getOptCode());
				jsonObject.put("name", cusGradeDicList.get(i).getOptName());
				cusGradeArray.add(jsonObject);
			}
			json.put("cusGradeArray", cusGradeArray);

			JSONArray isFirstListArray = new CodeUtils().getJSONArrayByKeyName("YES_NO");
			JSONArray newisFirstListArray = new JSONArray();
			JSONObject clearIsFirstObject = null;
			for (int i = 0; i < isFirstListArray.size(); i++) {
				clearIsFirstObject = new JSONObject();
				clearIsFirstObject.put("id", isFirstListArray.getJSONObject(i).getString("optCode"));
				clearIsFirstObject.put("name", isFirstListArray.getJSONObject(i).getString("optName"));
				newisFirstListArray.add(clearIsFirstObject);
			}
			json.put("isfirstContactMan", newisFirstListArray);

			json.put("cusCode", pssCustomerInfo.getCusCode());

			json.put("cusNo", pssCustomerInfo.getCusNo());

			json.put("originalAccountsReceived", pssCustomerInfo.getAccountsReceived());

			json.put("originalDepositReceived", pssCustomerInfo.getDepositReceived());

			String ajaxData = json.toString();
			model.addAttribute("formpsscustomerinfo0002", formpsscustomerinfo0002);
			model.addAttribute("ajaxData", ajaxData);
		}

	
		model.addAttribute("query", "");
		return "/component/pss/information/PssCustomerInfo_Detail";
	}

	@RequestMapping(value = "/batchDeleteCustomerInfoAjax")
	@ResponseBody
	public Map<String, Object> batchDeleteCustomerInfoAjax(String pssCustomerInfosJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssCustomerInfo> pssCustomerInfoList = PssPublicUtil.getMapByJsonObj(new PssCustomerInfo(),
					pssCustomerInfosJson);
			if (pssCustomerInfoList != null && pssCustomerInfoList.size() > 0) {
				String msg = "";
				for (PssCustomerInfo pssCustomerInfo : pssCustomerInfoList) {
					try {
						boolean success = pssCustomerInfoFeign.deleteCustomerInfo(pssCustomerInfo);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("客户[" + pssCustomerInfo.getCusCode() + "]删除") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION.getMessage("客户[" + pssCustomerInfo.getCusCode() + "]删除")
									+ "<br>";
						}
					} catch (Exception e) {
						msg += e.getMessage() + "<br>";
					}
				}
				dataMap.put("success", true);
				dataMap.put("msg", msg);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/batchCloseCustomerInfoAjax")
	@ResponseBody
	public Map<String, Object> batchCloseCustomerInfoAjax(String pssCustomerInfosJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssCustomerInfo> pssCustomerInfoList = PssPublicUtil.getMapByJsonObj(new PssCustomerInfo(),
					pssCustomerInfosJson);
			if (pssCustomerInfoList != null && pssCustomerInfoList.size() > 0) {
				String msg = "";
				for (PssCustomerInfo pssCustomerInfo : pssCustomerInfoList) {
					try {
						boolean success = pssCustomerInfoFeign.doCloseCustomerInfo(pssCustomerInfo);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("客户[" + pssCustomerInfo.getCusCode() + "]关闭") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION.getMessage("客户[" + pssCustomerInfo.getCusCode() + "]关闭")
									+ "<br>";
						}
					} catch (Exception e) {
						msg += e.getMessage() + "<br>";
					}
				}
				dataMap.put("success", true);
				dataMap.put("msg", msg);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/batchEnableCustomerInfoAjax")
	@ResponseBody
	public Map<String, Object> batchEnableCustomerInfoAjax(String pssCustomerInfosJson) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssCustomerInfo> pssCustomerInfoList = PssPublicUtil.getMapByJsonObj(new PssCustomerInfo(),
					pssCustomerInfosJson);
			if (pssCustomerInfoList != null && pssCustomerInfoList.size() > 0) {
				String msg = "";
				for (PssCustomerInfo pssCustomerInfo : pssCustomerInfoList) {
					try {
						boolean success = pssCustomerInfoFeign.doEnableCustomerInfo(pssCustomerInfo);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("客户[" + pssCustomerInfo.getCusCode() + "]启用") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION.getMessage("客户[" + pssCustomerInfo.getCusCode() + "]启用")
									+ "<br>";
						}
					} catch (Exception e) {
						msg += e.getMessage() + "<br>";
					}
				}
				dataMap.put("success", true);
				dataMap.put("msg", msg);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
