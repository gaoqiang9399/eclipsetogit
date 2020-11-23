package app.component.finance.paramset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.component.common.EntityUtil;
import app.component.finance.paramset.entity.CwJitiItem;
import app.component.finance.paramset.feign.CwJitiItemFeign;
import app.util.toolkit.Ipage;

/**
 * Title: CwJitiItemAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 22 09:34:09 CST 2017
 **/
@Controller
@RequestMapping("/cwJitiItem")
public class CwJitiItemController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwJitiItemFeign cwJitiItemFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	public CwJitiItemController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(String jtId) throws Exception {
		ActionContext.initialize(request, response);
		ActionContext.getActionContext().getRequest().setAttribute("jtId", jtId);
		return "/component/finance/paramset/CwJitiItem_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, String jtId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwJitiItem cwJitiItem = new CwJitiItem();
		try {
			cwJitiItem.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwJitiItem.setCriteriaList(cwJitiItem, ajaxData);// 我的筛选
			// cwJitiItem.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(cwJitiItem,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			cwJitiItem.setJtId(jtId);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			Map<String, Object> params = new HashMap<>();
			params.put("cwJitiItem", cwJitiItem);
			ipage.setParams(params);
			ipage = cwJitiItemFeign.findByPage(ipage,finBooks);

			List<Map<String,Object>> itlistMap = (List) ipage.getResult();
			Gson gson=new Gson();
			String listJson = gson.toJson(itlistMap);
			List<CwJitiItem> itlist=gson.fromJson(listJson, new TypeToken<List<CwJitiItem>>(){}.getType());  
			String jtItemJtBal = "";
			String jKemu = "";
			String dKemu = "";
			if (itlist != null && itlist.size() > 0) {
				jtItemJtBal = itlist.get(0).getJtItemJtBal();
				jKemu = itlist.get(0).getJtItemJKm();
				dKemu = itlist.get(0).getJtItemDKm();
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("jtItemJtBal", jtItemJtBal);
			dataMap.put("jKemu", jKemu);
			dataMap.put("dKemu", dKemu);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formcwjitiitem0002 = formService.getFormData("cwjitiitem0002");
			getFormValue(formcwjitiitem0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwjitiitem0002)) {
				CwJitiItem cwJitiItem = new CwJitiItem();
				setObjValue(formcwjitiitem0002, cwJitiItem);
				cwJitiItemFeign.insert(cwJitiItem,finBooks);
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwjitiitem0002 = formService.getFormData("cwjitiitem0002");
		getFormValue(formcwjitiitem0002, getMapByJson(ajaxData));
		CwJitiItem cwJitiItemJsp = new CwJitiItem();
		setObjValue(formcwjitiitem0002, cwJitiItemJsp);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwJitiItem cwJitiItem = cwJitiItemFeign.getById(cwJitiItemJsp,finBooks);
		if (cwJitiItem != null) {
			try {
				cwJitiItem = (CwJitiItem) EntityUtil.reflectionSetVal(cwJitiItem, cwJitiItemJsp,
						getMapByJson(ajaxData));
				cwJitiItemFeign.update(cwJitiItem,finBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formcwjitiitem0002 = formService.getFormData("cwjitiitem0002");
			getFormValue(formcwjitiitem0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwjitiitem0002)) {
				CwJitiItem cwJitiItem = new CwJitiItem();
				setObjValue(formcwjitiitem0002, cwJitiItem);
				cwJitiItemFeign.update(cwJitiItem,finBooks);
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String jtItemId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwjitiitem0002 = formService.getFormData("cwjitiitem0002");
		CwJitiItem cwJitiItem = new CwJitiItem();
		cwJitiItem.setJtItemId(jtItemId);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwJitiItem = cwJitiItemFeign.getById(cwJitiItem,finBooks);
		getObjValue(formcwjitiitem0002, cwJitiItem, formData);
		if (cwJitiItem != null) {
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
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String jtItemId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwJitiItem cwJitiItem = new CwJitiItem();
		cwJitiItem.setJtItemId(jtItemId);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwJitiItemFeign.delete(cwJitiItem,finBooks);
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
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwjitiitem0002 = formService.getFormData("cwjitiitem0002");
		model.addAttribute("formcwjitiitem0002", formcwjitiitem0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwJitiItem_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String jtItemId) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwjitiitem0001 = formService.getFormData("cwjitiitem0001");
		getFormValue(formcwjitiitem0001);
		CwJitiItem cwJitiItem = new CwJitiItem();
		cwJitiItem.setJtItemId(jtItemId);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwJitiItem = cwJitiItemFeign.getById(cwJitiItem,finBooks);
		getObjValue(formcwjitiitem0001, cwJitiItem);
		model.addAttribute("cwJitiItem", cwJitiItem);
		model.addAttribute("formcwjitiitem0001", formcwjitiitem0001);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwJitiItem_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwjitiitem0002 = formService.getFormData("cwjitiitem0002");
		getFormValue(formcwjitiitem0002);
		boolean validateFlag = this.validateFormData(formcwjitiitem0002);
		model.addAttribute("formcwjitiitem0002", formcwjitiitem0002);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwjitiitem0002 = formService.getFormData("cwjitiitem0002");
		getFormValue(formcwjitiitem0002);
		boolean validateFlag = this.validateFormData(formcwjitiitem0002);
		model.addAttribute("formcwjitiitem0002", formcwjitiitem0002);
		model.addAttribute("query", query);
	}

}
