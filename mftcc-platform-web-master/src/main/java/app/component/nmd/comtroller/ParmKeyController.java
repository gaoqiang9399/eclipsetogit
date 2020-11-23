package app.component.nmd.comtroller;

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

import app.base.cache.feign.BusiCacheFeign;
import app.component.nmd.entity.ParmDic;
import app.component.nmd.entity.ParmKey;
import app.component.nmd.feign.ParmDicFeign;
import app.component.nmd.feign.ParmKeyFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: ParmKeyAction.java Description:
 * 
 * @author:jiangyunxin@dhcc.com.cn
 * @Thu Apr 10 09:10:06 GMT 2014
 **/
@Controller
@RequestMapping("/parmKey")
public class ParmKeyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ParmKeyFeign parmKeyFeign;
	@Autowired
	private ParmDicFeign parmDicFeign;
	@Autowired
	private BusiCacheFeign busiCacheFeign;

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd0001 = formService.getFormData("nmd0001");
		model.addAttribute("formnmd0001", formnmd0001);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_Dic_list";
	}

	/***
	 * 列表数据查询的异步方法
	 * @param tableId 
	 * @param tableType 
	 * @param tableIdc 
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAllAjax")
	public Map<String, Object> findByPageAllAjax(String ajaxData, String tableId, String tableType, String tableIdc) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ParmKey parmKey = new ParmKey();
			ParmDic parmDic = new ParmDic();
			JSONObject jb = JSONObject.fromObject(ajaxData);
			parmKey = (ParmKey) JSONObject.toBean(jb, ParmKey.class);
			parmDic.setKeyName(parmKey.getKeyName());
			Ipage ipage = this.getIpage();
			Ipage ipagec = (Ipage) ipage.clone();
			ipage.setParams(this.setIpageParams("parmKey", parmKey));
			ipage = parmKeyFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtmlf = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtmlf);
			ipagec.setParams(this.setIpageParams("parmDic", parmDic));
			ipagec = parmDicFeign.findByPage(ipagec);
			String tableHtmlc = jtu.getJsonStr(tableIdc, tableType, (List) ipagec.getResult(), ipage, true);
			ipagec.setResult(tableHtmlc);
			dataMap.put("ipage", ipage);
			dataMap.put("ipagec", ipagec);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 列表数据查询的异步方法
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ParmKey parmKey = new ParmKey();
			JSONObject jb = JSONObject.fromObject(ajaxData);
			parmKey = (ParmKey) JSONObject.toBean(jb, ParmKey.class);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("parmKey", parmKey));
			ipage = parmKeyFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	private void getTableDataIpage(String keyName, String tableId, Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		ParmKey parmKey = new ParmKey();
		parmKey.setKeyName(keyName);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmKey", parmKey));
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				(List) parmKeyFeign.findByPage(ipage).getResult(), ipage, true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
	}

	/**
	 * AJAX新增
	 * @param tableId 
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
			FormData formnmd0001 = formService.getFormData("nmd0001");
			getFormValue(formnmd0001, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd0001)) {
				ParmKey parmKey = new ParmKey();
				setObjValue(formnmd0001, parmKey);
				if (parmKeyFeign.getById(parmKey) != null) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage());
				} else {
					parmKeyFeign.insert(parmKey);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					String keyName = parmKey.getKeyName();
					getTableDataIpage(keyName, tableId, dataMap);
				}
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
	 * AJAX新增
	 * @param tableId 
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
		try {
			FormData formnmd0001 = formService.getFormData("nmd0001");
			getFormValue(formnmd0001, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd0001)) {
				ParmKey parmKey = new ParmKey();
				setObjValue(formnmd0001, parmKey);
				parmKeyFeign.update(parmKey);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				String keyName = parmKey.getKeyName();
				getTableDataIpage(keyName, tableId, dataMap);
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
	 * AJAX获取查看
	 * @param keyName 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax( String keyName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formnmd0001 = formService.getFormData("nmd0001");
		ParmKey parmKey = new ParmKey();
		parmKey.setKeyName(keyName);
		parmKey = parmKeyFeign.getById(parmKey);
		getObjValue(formnmd0001, parmKey, formData);
		if (parmKey != null) {
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
	public Map<String, Object> deleteAjax(String keyName) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmKey parmKey = new ParmKey();
		parmKey.setKeyName(keyName);
		try {
			parmKeyFeign.del(parmKey);
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
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3011 = formService.getFormData("nmd3011");
		ParmKey parmKey = new ParmKey();
		getFormValue(formnmd3011);
		setObjValue(formnmd3011, parmKey);
		// String roleNo = User.getRoleNo(this.getHttpRequest());
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmKey", parmKey));
		List parmKeyList = (List) parmKeyFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3011", formnmd3011);
		model.addAttribute("parmKeyList", parmKeyList);
		model.addAttribute("query", "");
		return "list";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageForManage")
	public String findByPageForManage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3015 = formService.getFormData("nmd3015");
		ParmKey parmKey = new ParmKey();
		getFormValue(formnmd3015);
		setObjValue(formnmd3015, parmKey);
		parmKey.setIfEdit("1");// 可编辑
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmKey", parmKey));
		List parmKeyList = (List) parmKeyFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3015", formnmd3015);
		model.addAttribute("parmKeyList", parmKeyList);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_ListForManage";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3012 = formService.getFormData("nmd3012");
		ParmKey parmKey = new ParmKey();
		getObjValue(formnmd3012, parmKey);
		model.addAttribute("formnmd3012", formnmd3012);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_Insert";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputForManage")
	public String inputForManage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3016 = formService.getFormData("nmd3016");
		ParmKey parmKey = new ParmKey();
		getObjValue(formnmd3016, parmKey);
		model.addAttribute("formnmd3016", formnmd3016);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_InsertForManage";
	}

	/**
	 * 新增保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3012 = formService.getFormData("nmd3012");
		getFormValue(formnmd3012);
		ParmKey parmKey = new ParmKey();
		setObjValue(formnmd3012, parmKey);
		parmKeyFeign.insert(parmKey);

		String keyName = parmKey.getKeyName();

		ArrayList<String[]> tabList = new ArrayList<String[]>();
		String[] tab = null;
		tab = new String[2];
		tab[0] = "数据字典主表信息";
		tab[1] = "ParmKey/getById?" + "keyName=" + keyName;
		tabList.add(tab);

		tab = new String[2];
		tab[0] = "字典项定义表子表信息";
		tab[1] = "ParmDic/findByPage?" + "keyName=" + keyName;
		tabList.add(tab);

		model.addAttribute("formnmd3012", formnmd3012);
		model.addAttribute("tabList", tabList);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_Tab";
	}

	/**
	 * 新增保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertForManage")
	public String insertForManage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3016 = formService.getFormData("nmd3016");
		getFormValue(formnmd3016);
		ParmKey parmKey = new ParmKey();
		setObjValue(formnmd3016, parmKey);
		parmKeyFeign.insert(parmKey);

		String keyName = parmKey.getKeyName();

		ArrayList<String[]> tabList = new ArrayList<String[]>();
		String[] tab = null;
		tab = new String[2];
		tab[0] = "数据字典主表信息";
		tab[1] = "ParmKey/getByIdForManage?" + "keyName=" + keyName;
		tabList.add(tab);

		tab = new String[2];
		tab[0] = "字典项定义表子表信息";
		tab[1] = "ParmDic/findByPage?" + "keyName=" + keyName;
		tabList.add(tab);

		model.addAttribute("formnmd3016", formnmd3016);
		model.addAttribute("tabList", tabList);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_TabForManage";
	}

	/**
	 * 修改操作
	 * @param keyName 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTabForManage")
	public String getTabForManage(Model model, String keyName) {
		ActionContext.initialize(request, response);

		ArrayList<String[]> tabList = new ArrayList<String[]>();
		String[] tab = null;
		tab = new String[2];
		tab[0] = "数据字典信息";
		tab[1] = "ParmKeyAction_getByIdForManage.action?" + "keyName=" + keyName + "&query=query";
		tabList.add(tab);

		tab = new String[2];
		tab[0] = "字典项定义表信息";
		tab[1] = "ParmDicAction_findByPageForManage.action?" + "keyName=" + keyName;
		tabList.add(tab);
		model.addAttribute("tabList", tabList);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_TabForManage";
	}

	/**
	 * 修改操作
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTab")
	public String getTab(Model model, String keyName) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		ArrayList<String[]> tabList = new ArrayList<String[]>();
		String[] tab = null;
		tab = new String[2];
		tab[0] = "数据字典信息";
		tab[1] = "ParmKeyAction_getById.action?" + "keyName=" + keyName;
		tabList.add(tab);

		tab = new String[2];
		tab[0] = "字典项定义表信息";
		tab[1] = "ParmDicAction_findByPage.action?" + "keyName=" + keyName;
		tabList.add(tab);
		model.addAttribute("tabList", tabList);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_Tab";
	}

	/**
	 * 查看操作
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTabViewForManage")
	public String getTabViewForManage(Model model, String keyName) {
		ActionContext.initialize(request, response);
		ArrayList<String[]> tabList = new ArrayList<String[]>();
		String[] tab = null;
		tab = new String[2];
		tab[0] = "数据字典信息";
		tab[1] = "ParmKeyAction_getByIdForManage.action?" + "keyName=" + keyName + "&query=query";
		tabList.add(tab);

		tab = new String[2];
		tab[0] = "字典项定义表信息";
		tab[1] = "ParmDicAction_findByPageViewForManage.action?" + "keyName=" + keyName;
		tabList.add(tab);
		model.addAttribute("tabList", tabList);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_TabForManage";
	}

	/**
	 * 查看操作
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTabView")
	public String getTabView(Model model, String keyName) {
		ActionContext.initialize(request, response);
		ArrayList<String[]> tabList = new ArrayList<String[]>();
		String[] tab = null;
		tab = new String[2];
		tab[0] = "数据字典信息";
		tab[1] = "ParmKeyAction_getById.action?" + "keyName=" + keyName + "&query=query";
		tabList.add(tab);

		tab = new String[2];
		tab[0] = "字典项定义表信息";
		tab[1] = "ParmDicAction_findByPageView.action?" + "keyName=" + keyName;
		tabList.add(tab);
		model.addAttribute("tabList", tabList);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_Tab";
	}

	/**
	 * 修改保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3012 = formService.getFormData("nmd3012");
		getFormValue(formnmd3012);
		ParmKey parmKey = new ParmKey();
		setObjValue(formnmd3012, parmKey);
		parmKeyFeign.update(parmKey);
		getObjValue(formnmd3012, parmKey);
		this.changeFormProperty(formnmd3012, "keyName", "readonly", "1");
		this.addActionMessage(model, "操作成功！");
		model.addAttribute("formnmd3012", formnmd3012);
		model.addAttribute("parmKey", parmKey);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_Detail";
	}

	/**
	 * 修改保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateForManage")
	public String updateForManage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3016 = formService.getFormData("nmd3016");
		getFormValue(formnmd3016);
		ParmKey parmKey = new ParmKey();
		setObjValue(formnmd3016, parmKey);
		parmKeyFeign.update(parmKey);
		getObjValue(formnmd3016, parmKey);
		this.changeFormProperty(formnmd3016, "keyName", "readonly", "1");
		this.addActionMessage(model, "操作成功！");
		model.addAttribute("formnmd3016", formnmd3016);
		model.addAttribute("parmKey", parmKey);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_DetailForManage";
	}

	/**
	 * 删除操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delForManage")
	public String delForManage(Model model, String keyName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3015 = formService.getFormData("nmd3015");
		ParmKey parmKey = new ParmKey();
		parmKey.setKeyName(keyName);
		parmKeyFeign.del(parmKey);
		this.addActionMessage(model, "删除成功");
		Ipage ipage = this.getIpage();
		parmKey.setIfEdit("1");// 可编辑
		ipage.setParams(this.setIpageParams("parmKey", parmKey));
		List parmKeyList = (List) parmKeyFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3015", formnmd3015);
		model.addAttribute("parmKey", parmKey);
		model.addAttribute("parmKeyList", parmKeyList);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_ListForManage";
	}

	/**
	 * 删除操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	public String del(Model model, String keyName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3011 = formService.getFormData("nmd3011");
		ParmKey parmKey = new ParmKey();
		parmKey.setKeyName(keyName);
		parmKeyFeign.del(parmKey);
		this.addActionMessage(model, "删除成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmKey", parmKey));
		List parmKeyList = (List) parmKeyFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3011", formnmd3011);
		model.addAttribute("parmKey", parmKey);
		model.addAttribute("parmKeyList", parmKeyList);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_List";
	}

	/**
	 * 查询操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String keyName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3012 = formService.getFormData("nmd3012");
		ParmKey parmKey = new ParmKey();
		parmKey.setKeyName(keyName);
		parmKey = parmKeyFeign.getById(parmKey);
		getObjValue(formnmd3012, parmKey);
		this.changeFormProperty(formnmd3012, "keyName", "readonly", "1");
		model.addAttribute("formnmd3012", formnmd3012);
		model.addAttribute("parmKey", parmKey);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_Detail";
	}

	/**
	 * 查询操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdForManage")
	public String getByIdForManage(Model model, String keyName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3016 = formService.getFormData("nmd3016");
		ParmKey parmKey = new ParmKey();
		parmKey.setKeyName(keyName);
		parmKey = parmKeyFeign.getById(parmKey);
		getObjValue(formnmd3016, parmKey);
		this.changeFormProperty(formnmd3016, "keyName", "readonly", "1");
		model.addAttribute("formnmd3016", formnmd3016);
		model.addAttribute("parmKey", parmKey);
		model.addAttribute("query", "");
		return "component/nmd/ParmKey_DetailForManage";
	}

	@ResponseBody
	@RequestMapping(value = "/refreshParmKey")
	public Map<String, Object> refreshParmKey() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		busiCacheFeign.refreshAllParmDic();
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 * 刷新业务描述模版
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/refreshDescTemp")
	public Map<String, Object> refreshDescTemp(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		busiCacheFeign.refreshAllParmDic();
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 * 新增保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3012 = formService.getFormData("nmd3012");
		getFormValue(formnmd3012);
		validateFormData(formnmd3012);
		ParmKey parmKey = new ParmKey();
		setObjValue(formnmd3012, parmKey);
		if (null != parmKeyFeign.getById(parmKey)) {
			this.addActionError(model,"关键字已存在！");
		}

	}

	/**
	 * 新增保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsertForManage")
	public void validateInsertForManage(Model model) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3016 = formService.getFormData("nmd3016");
		getFormValue(formnmd3016);
		validateFormData(formnmd3016);
		ParmKey parmKey = new ParmKey();
		setObjValue(formnmd3016, parmKey);
		if (null != parmKeyFeign.getById(parmKey)) {
			this.addActionError(model,"关键字已存在！");
		}

	}

	/**
	 * 修改保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3012 = formService.getFormData("nmd3012");
		getFormValue(formnmd3012);
		validateFormData(formnmd3012);
	}

	/**
	 * 修改保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/validateUpdateForManage")
	public void validateUpdateForManage(Model model) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3016 = formService.getFormData("nmd3016");
		getFormValue(formnmd3016);
		validateFormData(formnmd3016);
	}

}
