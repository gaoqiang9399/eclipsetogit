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

import app.component.pss.information.entity.PssCustomerContacts;
import app.component.pss.information.feign.PssCustomerContactsFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssCustomerContactsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 01 16:24:00 SGT 2017
 **/
@Controller
@RequestMapping("/pssCustomerContacts")
public class PssCustomerContactsController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssCustomerContactsFeign pssCustomerContactsFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,PssCustomerContacts pssCustomerContacts) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssCustomerContactsFeign.getAll(pssCustomerContacts),
				null, true);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpsscustomercontacts0002 = formService.getFormData("psscustomercontacts0002");
		PssCustomerContacts pssCustomerContacts = new PssCustomerContacts();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pssCustomerContacts",pssCustomerContacts));
		List<PssCustomerContacts> pssCustomerContactsList = (List<PssCustomerContacts>) pssCustomerContactsFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formpsscustomercontacts0002", formpsscustomercontacts0002);
		model.addAttribute("pssCustomerContactsList", pssCustomerContactsList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssCustomerContacts_List";
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
		FormData formpsscustomercontacts0002 = formService.getFormData("psscustomercontacts0002");
		PssCustomerContacts pssCustomerContacts = new PssCustomerContacts();
		List<PssCustomerContacts> pssCustomerContactsList = pssCustomerContactsFeign.getAll(pssCustomerContacts);
		model.addAttribute("formpsscustomercontacts0002", formpsscustomercontacts0002);
		model.addAttribute("pssCustomerContactsList", pssCustomerContactsList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssCustomerContacts_List";
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
			FormData formpsscustomercontacts0002 = formService.getFormData("psscustomercontacts0002");
			getFormValue(formpsscustomercontacts0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscustomercontacts0002)) {
				PssCustomerContacts pssCustomerContacts = new PssCustomerContacts();
				setObjValue(formpsscustomercontacts0002, pssCustomerContacts);
				pssCustomerContactsFeign.insert(pssCustomerContacts);
				getTableData(tableId,pssCustomerContacts);// 获取列表
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
		PssCustomerContacts pssCustomerContacts = new PssCustomerContacts();
		try {
			FormData formpsscustomercontacts0002 = formService.getFormData("psscustomercontacts0002");
			getFormValue(formpsscustomercontacts0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscustomercontacts0002)) {
				pssCustomerContacts = new PssCustomerContacts();
				setObjValue(formpsscustomercontacts0002, pssCustomerContacts);
				pssCustomerContactsFeign.update(pssCustomerContacts);
				getTableData(tableId,pssCustomerContacts);// 获取列表
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
	public Map<String, Object> getByIdAjax(String customerContactsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsscustomercontacts0002 = formService.getFormData("psscustomercontacts0002");
		PssCustomerContacts pssCustomerContacts = new PssCustomerContacts();
		pssCustomerContacts.setCustomerContactsId(customerContactsId);
		pssCustomerContacts = pssCustomerContactsFeign.getById(pssCustomerContacts);
		getObjValue(formpsscustomercontacts0002, pssCustomerContacts, formData);
		if (pssCustomerContacts != null) {
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
	public Map<String, Object> deleteAjax(String customerContactsId,String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCustomerContacts pssCustomerContacts = new PssCustomerContacts();
		pssCustomerContacts.setCustomerContactsId(customerContactsId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssCustomerContacts = (PssCustomerContacts) JSONObject.toBean(jb, PssCustomerContacts.class);
			pssCustomerContactsFeign.delete(pssCustomerContacts);
			getTableData(tableId,pssCustomerContacts);// 获取列表
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
			String ajaxData,String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCustomerContacts pssCustomerContacts = new PssCustomerContacts();
		List<PssCustomerContacts> pssCustomerContactsList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("pssCustomerContacts",pssCustomerContacts));
			if (cusNo == null || "".equals(cusNo)) {
				 pssCustomerContactsList = new ArrayList<PssCustomerContacts>();
			} else {
				pssCustomerContacts.setCusNo(cusNo);
				// 自定义查询Bo方法
				ipage = pssCustomerContactsFeign.findByPage(ipage);
				pssCustomerContactsList = (List<PssCustomerContacts>) ipage.getResult();
			}

			if (pssCustomerContactsList != null && pssCustomerContactsList.size() > 0) {
				if (pssCustomerContactsList.size() < 5) {
					for (int i = pssCustomerContactsList.size() + 1; i <= 5; i++) {
						pssCustomerContacts = new PssCustomerContacts();
						pssCustomerContacts.setSequence(i);
						pssCustomerContactsList.add(pssCustomerContacts);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					pssCustomerContacts = new PssCustomerContacts();
					pssCustomerContacts.setSequence(i);
					pssCustomerContactsList.add(pssCustomerContacts);
				}
			}

			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssCustomerContactsList, ipage, true);
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
