package app.component.pss.information.controller;

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
import com.core.struts.taglib.JsonTableUtil;

import app.component.pss.information.entity.PssSupplierContacts;
import app.component.pss.information.feign.PssSupplierContactsFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssSupplierContactsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 01 16:32:54 SGT 2017
 **/
@Controller
@RequestMapping("/pssSupplierContacts")
public class PssSupplierContactsController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSupplierContactsFeign pssSupplierContactsFeign;

	// 全局变量
	// 表单变量
	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssSupplierContacts pssSupplierContacts) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssSupplierContactsFeign.getAll(pssSupplierContacts),
				null, true);
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
		FormData formpsssuppliercontacts0002 = formService.getFormData("psssuppliercontacts0002");
		PssSupplierContacts pssSupplierContacts = new PssSupplierContacts();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pssSupplierContacts", pssSupplierContacts));
		List<PssSupplierContacts> pssSupplierContactsList = (List<PssSupplierContacts>) pssSupplierContactsFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formpsssuppliercontacts0002", formpsssuppliercontacts0002);
		model.addAttribute("pssSupplierContactsList", pssSupplierContactsList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssSupplierContacts_List";
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
		FormData formpsssuppliercontacts0002 = formService.getFormData("psssuppliercontacts0002");
		PssSupplierContacts pssSupplierContacts = new PssSupplierContacts();
		List<PssSupplierContacts> pssSupplierContactsList = pssSupplierContactsFeign.getAll(pssSupplierContacts);
		model.addAttribute("formpsssuppliercontacts0002", formpsssuppliercontacts0002);
		model.addAttribute("pssSupplierContactsList", pssSupplierContactsList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssSupplierContacts_List";
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
			FormData formpsssuppliercontacts0002 = formService.getFormData("psssuppliercontacts0002");
			getFormValue(formpsssuppliercontacts0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsssuppliercontacts0002)) {
				PssSupplierContacts pssSupplierContacts = new PssSupplierContacts();
				setObjValue(formpsssuppliercontacts0002, pssSupplierContacts);
				pssSupplierContactsFeign.insert(pssSupplierContacts);
				getTableData(tableId, pssSupplierContacts);// 获取列表
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
		PssSupplierContacts pssSupplierContacts = new PssSupplierContacts();
		try {
			FormData formpsssuppliercontacts0002 = formService.getFormData("psssuppliercontacts0002");
			getFormValue(formpsssuppliercontacts0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsssuppliercontacts0002)) {
				pssSupplierContacts = new PssSupplierContacts();
				setObjValue(formpsssuppliercontacts0002, pssSupplierContacts);
				pssSupplierContactsFeign.update(pssSupplierContacts);
				getTableData(tableId, pssSupplierContacts);// 获取列表
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
	public Map<String, Object> getByIdAjax(String supplierContactsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsssuppliercontacts0002 = formService.getFormData("psssuppliercontacts0002");
		PssSupplierContacts pssSupplierContacts = new PssSupplierContacts();
		pssSupplierContacts.setSupplierContactsId(supplierContactsId);
		pssSupplierContacts = pssSupplierContactsFeign.getById(pssSupplierContacts);
		getObjValue(formpsssuppliercontacts0002, pssSupplierContacts, formData);
		if (pssSupplierContacts != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String supplierContactsId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSupplierContacts pssSupplierContacts = new PssSupplierContacts();
		pssSupplierContacts.setSupplierContactsId(supplierContactsId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSupplierContacts = (PssSupplierContacts) JSONObject.toBean(jb, PssSupplierContacts.class);
			pssSupplierContactsFeign.delete(pssSupplierContacts);
			getTableData(tableId, pssSupplierContacts);// 获取列表
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
			String ajaxData, String supplierId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSupplierContacts pssSupplierContacts = new PssSupplierContacts();
		List<PssSupplierContacts> pssSupplierContactsList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("pssSupplierContacts", pssSupplierContacts));
			if (supplierId == null || "".equals(supplierId)) {
				pssSupplierContactsList = new ArrayList<PssSupplierContacts>();
			} else {
				pssSupplierContacts.setSupplierId(supplierId);
				// 自定义查询Bo方法
				ipage = pssSupplierContactsFeign.findByPage(ipage);
				pssSupplierContactsList = (List<PssSupplierContacts>) ipage.getResult();
			}

			if (pssSupplierContactsList != null && pssSupplierContactsList.size() > 0) {
				if (pssSupplierContactsList.size() < 5) {
					for (int i = pssSupplierContactsList.size() + 1; i <= 5; i++) {
						pssSupplierContacts = new PssSupplierContacts();
						pssSupplierContacts.setSequence(i);
						pssSupplierContactsList.add(pssSupplierContacts);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					pssSupplierContacts = new PssSupplierContacts();
					pssSupplierContacts.setSequence(i);
					pssSupplierContactsList.add(pssSupplierContacts);
				}
			}

			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssSupplierContactsList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);

		} catch (Exception ex) {
			ex.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", ex.getMessage());
			throw ex;
		}
		return dataMap;
	}

}
