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
import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.information.entity.PssSupplierContacts;
import app.component.pss.information.entity.PssSupplierInfo;
import app.component.pss.information.feign.PssSupplierInfoFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssSupplierInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 01 16:25:37 SGT 2017
 **/
@Controller
@RequestMapping("/pssSupplierInfo")
public class PssSupplierInfoController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSupplierInfoFeign pssSupplierInfoFeign;
	@Autowired
	private ParmDicFeign parmDicFeign;
	// 全局变量
	// 表单变量
	@Autowired
	private PssConfigFeign pssConfigFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssSupplierInfo pssSupplierInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssSupplierInfoFeign.getAll(pssSupplierInfo), null,
				true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		/*
		 * formpsssupplierinfo0002 =
		 * formService.getFormData("psssupplierinfo0002"); PssSupplierInfo
		 * pssSupplierInfo = new PssSupplierInfo(); Ipage ipage =
		 * this.getIpage(); pssSupplierInfoList =
		 * (List<PssSupplierInfo>)pssSupplierInfoFeign.findByPage(ipage,
		 * pssSupplierInfo).getResult();
		 */
		JSONArray pssEnabledStatusJsonArray = new CodeUtils().getJSONArrayByKeyName("YES_NO");
		this.getHttpRequest().setAttribute("pssEnabledStatusJsonArray", pssEnabledStatusJsonArray);
		model.addAttribute("pssEnabledStatusJsonArray", pssEnabledStatusJsonArray);
		model.addAttribute("query", "");
		return "/component/pss/information/PssSupplierInfo_List";
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
		FormData formpsssupplierinfo0002 = formService.getFormData("psssupplierinfo0002");
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		List<PssSupplierInfo> pssSupplierInfoList = pssSupplierInfoFeign.getAll(pssSupplierInfo);
		model.addAttribute("formpsssupplierinfo0002", formpsssupplierinfo0002);
		model.addAttribute("pssSupplierInfoList", pssSupplierInfoList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssSupplierInfo_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsssupplierinfo0002 = formService.getFormData("psssupplierinfo0002");
			getFormValue(formpsssupplierinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsssupplierinfo0002)) {
				PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
				setObjValue(formpsssupplierinfo0002, pssSupplierInfo);
				pssSupplierInfoFeign.insert(pssSupplierInfo);
				getTableData(tableId, pssSupplierInfo);// 获取列表
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
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		try {
			FormData formpsssupplierinfo0002 = formService.getFormData("psssupplierinfo0002");
			getFormValue(formpsssupplierinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsssupplierinfo0002)) {
				pssSupplierInfo = new PssSupplierInfo();
				setObjValue(formpsssupplierinfo0002, pssSupplierInfo);
				pssSupplierInfoFeign.update(pssSupplierInfo);
				getTableData(tableId, pssSupplierInfo);// 获取列表
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
	public Map<String, Object> getByIdAjax(String supplierId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsssupplierinfo0002 = formService.getFormData("psssupplierinfo0002");
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		pssSupplierInfo.setSupplierId(supplierId);
		pssSupplierInfo = pssSupplierInfoFeign.getById(pssSupplierInfo);
		getObjValue(formpsssupplierinfo0002, pssSupplierInfo, formData);
		if (pssSupplierInfo != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String supplierId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		pssSupplierInfo.setSupplierId(supplierId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSupplierInfo = (PssSupplierInfo) JSONObject.toBean(jb, PssSupplierInfo.class);
			pssSupplierInfoFeign.delete(pssSupplierInfo);
			getTableData(tableId, pssSupplierInfo);// 获取列表
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		try {
			pssSupplierInfo.setCustomQuery(ajaxData);
			pssSupplierInfo.setCustomSorts(ajaxData);
			pssSupplierInfo.setCriteriaList(pssSupplierInfo, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("pssSupplierInfo", pssSupplierInfo));
			ipage = pssSupplierInfoFeign.findByPage(ipage);
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
		FormData formpsssupplierinfo0002 = formService.getFormData("psssupplierinfo0002");

		String supplierNo = pssSupplierInfoFeign.generateSupplierNo();

		Double taxRate = 0.00;
		List<PssConfig> pssConfigList = pssConfigFeign.getAll(new PssConfig());
		if (pssConfigList != null && pssConfigList.size() > 0) {
			taxRate = pssConfigList.get(0).getDutyRate();
		}

		JSONObject json = new JSONObject();

		// 供应商类别
		ParmDic queryUnit = new ParmDic();
		queryUnit.setKeyName("PSS_SUPPLIER_CATEGORY");
		List<ParmDic> supplierCategoryDicList = parmDicFeign.findlist(queryUnit);
		JSONArray supplierCategoryArray = new JSONArray();
		for (int i = 0; i < supplierCategoryDicList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", supplierCategoryDicList.get(i).getOptCode());
			jsonObject.put("name", supplierCategoryDicList.get(i).getOptName());
			supplierCategoryArray.add(jsonObject);
		}
		json.put("supplierCategoryArray", supplierCategoryArray);

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

		json.put("supplierNo", supplierNo);

		json.put("supplierId", "");

		json.put("taxRate", taxRate);

		json.put("originalAccountsPayed", 0.00);

		json.put("originalDepositPayed", 0.00);

		String ajaxData = json.toString();

		model.addAttribute("formpsssupplierinfo0002", formpsssupplierinfo0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/information/PssSupplierInfo_Detail";
	}

	@RequestMapping(value = "/saveSupplierInfoAjax")
	@ResponseBody
	public Map<String, Object> saveSupplierInfoAjax(String ajaxData, String pssSupplierContactsJson,
			String originalAccountsPayed, String originalDepositPayed, String saveOrEditFlag) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsssupplierinfo0002 = formService.getFormData("psssupplierinfo0002");
			getFormValue(formpsssupplierinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsssupplierinfo0002)) {
				PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
				setObjValue(formpsssupplierinfo0002, pssSupplierInfo);

				List<PssSupplierContacts> pssSupplierContactsList = PssPublicUtil
						.getMapByJsonObj(new PssSupplierContacts(), pssSupplierContactsJson);

				boolean success = pssSupplierInfoFeign.doSaveSupplierInfo(pssSupplierInfo, pssSupplierContactsList,
						originalAccountsPayed, originalDepositPayed, saveOrEditFlag);

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
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			String supplierId = (String) jsonObject.get("supplierId");
			if (StringUtil.isEmpty(supplierId)) {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
			}
			pssSupplierInfo.setSupplierId(supplierId);
			pssSupplierInfo = pssSupplierInfoFeign.getById(pssSupplierInfo);
			String enabledStatus = (String) jsonObject.get("enabledStatus");
			if (StringUtil.isNotEmpty(enabledStatus)) {
				if (PssEnumBean.ENABLED_STATUS.CLOSED.getValue().equals(enabledStatus)) {
					// 关闭
					pssSupplierInfoFeign.doCloseSupplierInfo(pssSupplierInfo);
				} else if (PssEnumBean.ENABLED_STATUS.ENABLED.getValue().equals(enabledStatus)) {
					// 启用
					pssSupplierInfoFeign.doEnableSupplierInfo(pssSupplierInfo);
				} else {
					throw new Exception(MessageEnum.FAILED_SAVE_CONTENT.getMessage("启用状态不正确"));
				}
			} else {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("启用状态"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/deleteSupplierInfoAjax")
	@ResponseBody
	public Map<String, Object> deleteSupplierInfoAjax(String supplierId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (supplierId == null || "".equals(supplierId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		pssSupplierInfo.setSupplierId(supplierId);
		try {
			boolean success = pssSupplierInfoFeign.deleteSupplierInfo(pssSupplierInfo);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("供应商删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("供应商删除"));
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
	public String getDetailPage(Model model, String supplierId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		if (StringUtil.isNotEmpty(supplierId)) {
			pssSupplierInfo.setSupplierId(supplierId);
			pssSupplierInfo = pssSupplierInfoFeign.getById(pssSupplierInfo);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		if (pssSupplierInfo == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			FormData formpsssupplierinfo0002 = formService.getFormData("psssupplierinfo0002");
			getObjValue(formpsssupplierinfo0002, pssSupplierInfo);

			JSONObject json = new JSONObject();

			// 供应商类别
			ParmDic queryUnit = new ParmDic();
			queryUnit.setKeyName("PSS_SUPPLIER_CATEGORY");
			List<ParmDic> supplierCategoryDicList = parmDicFeign.findlist(queryUnit);
			JSONArray supplierCategoryArray = new JSONArray();
			for (int i = 0; i < supplierCategoryDicList.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", supplierCategoryDicList.get(i).getOptCode());
				jsonObject.put("name", supplierCategoryDicList.get(i).getOptName());
				supplierCategoryArray.add(jsonObject);
			}
			json.put("supplierCategoryArray", supplierCategoryArray);

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

			json.put("supplierNo", pssSupplierInfo.getSupplierNo());

			json.put("supplierId", pssSupplierInfo.getSupplierId());

			json.put("taxRate", pssSupplierInfo.getTaxRate());

			json.put("originalAccountsPayed", pssSupplierInfo.getAccountsPayed());

			json.put("originalDepositPayed", pssSupplierInfo.getDepositPayed());

			String ajaxData = json.toString();
			model.addAttribute("formpsssupplierinfo0002", formpsssupplierinfo0002);
			model.addAttribute("ajaxData", ajaxData);
		}

		model.addAttribute("query", "");
		return "/component/pss/information/PssSupplierInfo_Detail";
	}

	@RequestMapping(value = "/batchDeleteSupplierInfoAjax")
	@ResponseBody
	public Map<String, Object> batchDeleteSupplierInfoAjax(String pssSupplierInfosJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSupplierInfo> pssSupplierInfoList = PssPublicUtil.getMapByJsonObj(new PssSupplierInfo(),
					pssSupplierInfosJson);
			if (pssSupplierInfoList != null && pssSupplierInfoList.size() > 0) {
				String msg = "";
				for (PssSupplierInfo pssSupplierInfo : pssSupplierInfoList) {
					try {
						boolean success = pssSupplierInfoFeign.deleteSupplierInfo(pssSupplierInfo);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("供应商[" + pssSupplierInfo.getSupplierNo() + "]删除") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("供应商[" + pssSupplierInfo.getSupplierNo() + "]删除") + "<br>";
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

	@RequestMapping(value = "/batchCloseSupplierInfoAjax")
	@ResponseBody
	public Map<String, Object> batchCloseSupplierInfoAjax(String pssSupplierInfosJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSupplierInfo> pssSupplierInfoList = PssPublicUtil.getMapByJsonObj(new PssSupplierInfo(),
					pssSupplierInfosJson);
			if (pssSupplierInfoList != null && pssSupplierInfoList.size() > 0) {
				String msg = "";
				for (PssSupplierInfo pssSupplierInfo : pssSupplierInfoList) {
					try {
						boolean success = pssSupplierInfoFeign.doCloseSupplierInfo(pssSupplierInfo);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("供应商[" + pssSupplierInfo.getSupplierNo() + "]关闭") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("供应商[" + pssSupplierInfo.getSupplierNo() + "]关闭") + "<br>";
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

	@RequestMapping(value = "/batchEnableSupplierInfoAjax")
	@ResponseBody
	public Map<String, Object> batchEnableSupplierInfoAjax(String pssSupplierInfosJson) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSupplierInfo> pssSupplierInfoList = PssPublicUtil.getMapByJsonObj(new PssSupplierInfo(),
					pssSupplierInfosJson);
			if (pssSupplierInfoList != null && pssSupplierInfoList.size() > 0) {
				String msg = "";
				for (PssSupplierInfo pssSupplierInfo : pssSupplierInfoList) {
					try {
						boolean success = pssSupplierInfoFeign.doEnableSupplierInfo(pssSupplierInfo);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("供应商[" + pssSupplierInfo.getSupplierNo() + "]启用") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("供应商[" + pssSupplierInfo.getSupplierNo() + "]启用") + "<br>";
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
