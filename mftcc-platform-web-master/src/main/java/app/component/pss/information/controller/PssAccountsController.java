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

import app.component.pss.information.entity.PssAccounts;
import app.component.pss.information.feign.PssAccountsFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * Title: PssAccountsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Dec 20 19:34:19 SGT 2017
 **/
@Controller
@RequestMapping("/pssAccounts")
public class PssAccountsController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssAccountsFeign pssAccountsFeign;

	// 全局变量
	// 表单变量
	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,PssAccounts pssAccounts) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssAccountsFeign.getAll(pssAccounts), null, true);
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
		/**
		 * FormData formpssaccounts0002 =
		 * formService.getFormData("pssaccounts0002"); PssAccounts pssAccounts =
		 * new PssAccounts(); Ipage ipage = this.getIpage(); pssAccountsList =
		 * (List<PssAccounts>)pssAccountsFeign.findByPage(ipage,
		 * pssAccounts).getResult();
		 **/
		model.addAttribute("query", "");
		return "/component/pss/information/PssAccounts_List";
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
		FormData formpssaccounts0002 = formService.getFormData("pssaccounts0002");
		PssAccounts pssAccounts = new PssAccounts();
		List<PssAccounts> pssAccountsList = pssAccountsFeign.getAll(pssAccounts);
		model.addAttribute("formpssaccounts0002", formpssaccounts0002);
		model.addAttribute("pssAccountsList", pssAccountsList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssAccounts_List";
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
			FormData formpssaccounts0002 = formService.getFormData("pssaccounts0002");
			getFormValue(formpssaccounts0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssaccounts0002)) {
				PssAccounts pssAccounts = new PssAccounts();
				setObjValue(formpssaccounts0002, pssAccounts);
				pssAccountsFeign.insert(pssAccounts);
				getTableData(tableId,pssAccounts);// 获取列表
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
		PssAccounts pssAccounts = new PssAccounts();
		try {
			FormData formpssaccounts0002 = formService.getFormData("pssaccounts0002");
			getFormValue(formpssaccounts0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssaccounts0002)) {
				pssAccounts = new PssAccounts();
				setObjValue(formpssaccounts0002, pssAccounts);
				pssAccountsFeign.update(pssAccounts);
				getTableData(tableId,pssAccounts);// 获取列表
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
	public Map<String, Object> getByIdAjax(String accountId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssaccounts0002 = formService.getFormData("pssaccounts0002");
		PssAccounts pssAccounts = new PssAccounts();
		pssAccounts.setAccountId(accountId);
		pssAccounts = pssAccountsFeign.getById(pssAccounts);
		getObjValue(formpssaccounts0002, pssAccounts, formData);
		if (pssAccounts != null) {
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
	public Map<String, Object> deleteAjax(String accountId,String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssAccounts pssAccounts = new PssAccounts();
		pssAccounts.setAccountId(accountId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssAccounts = (PssAccounts) JSONObject.toBean(jb, PssAccounts.class);
			pssAccountsFeign.delete(pssAccounts);
			getTableData(tableId,pssAccounts);// 获取列表
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
		PssAccounts pssAccounts = new PssAccounts();
		try {
			pssAccounts.setCustomQuery(ajaxData);
			pssAccounts.setCustomSorts(ajaxData);
			pssAccounts.setCriteriaList(pssAccounts, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage = pssAccountsFeign.findByPage(ipage);
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
	public String getAddPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssaccounts0002 = formService.getFormData("pssaccounts0002");

		model.addAttribute("formpssaccounts0002", formpssaccounts0002);
		model.addAttribute("query", "");
		return "/component/pss/information/PssAccounts_Detail";
	}

	@RequestMapping(value = "/saveAccountAjax")
	@ResponseBody
	public Map<String, Object> saveAccountAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssaccounts0002 = formService.getFormData("pssaccounts0002");
			getFormValue(formpssaccounts0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssaccounts0002)) {
				PssAccounts pssAccounts = new PssAccounts();
				setObjValue(formpssaccounts0002, pssAccounts);

				boolean success = pssAccountsFeign.doSaveAccounts(pssAccounts);

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

	@RequestMapping(value = "/deleteAccountAjax")
	@ResponseBody
	public Map<String, Object> deleteAccountAjax(String accountId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (accountId == null || "".equals(accountId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssAccounts pssAccounts = new PssAccounts();
		pssAccounts.setAccountId(accountId);
		try {
			boolean success = pssAccountsFeign.deleteAccount(pssAccounts);

			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("账户删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("账户删除"));
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
	public String getDetailPage(Model model, String accountId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssAccounts pssAccounts = new PssAccounts();
		if (StringUtil.isNotEmpty(accountId)) {
			pssAccounts.setAccountId(accountId);
			pssAccounts = pssAccountsFeign.getById(pssAccounts);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		if (pssAccounts == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			FormData formpssaccounts0002 = formService.getFormData("pssaccounts0002");
			getObjValue(formpssaccounts0002, pssAccounts);
			model.addAttribute("formpssaccounts0002", formpssaccounts0002);
		}

		model.addAttribute("pssAccounts", pssAccounts);
		model.addAttribute("query", "");
		return "/component/pss/information/PssAccounts_Detail";
	}

}
