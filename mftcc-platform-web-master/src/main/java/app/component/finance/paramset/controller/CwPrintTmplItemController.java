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

import app.component.common.EntityUtil;
import app.component.finance.paramset.entity.CwPrintTmplItem;
import app.component.finance.paramset.feign.CwPrintTmplItemFeign;
import app.util.toolkit.Ipage;

/**
 * Title: CwPrintTmplItemAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Sep 15 14:30:33 CST 2017
 **/
@Controller
@RequestMapping("/cwPrintTmplItem")
public class CwPrintTmplItemController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwPrintTmplItemFeign cwPrintTmplItemFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	public CwPrintTmplItemController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/paramset/CwPrintTmplItem_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwPrintTmplItem cwPrintTmplItem = new CwPrintTmplItem();
		try {
			cwPrintTmplItem.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwPrintTmplItem.setCriteriaList(cwPrintTmplItem, ajaxData);// 我的筛选
			// cwPrintTmplItem.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(cwPrintTmplItem,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwPrintTmplItem", cwPrintTmplItem);
			ipage.setParams(params);
			ipage = cwPrintTmplItemFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
			FormData formparamset0002 = formService.getFormData("paramset0002");
			getFormValue(formparamset0002, getMapByJson(ajaxData));
			if (this.validateFormData(formparamset0002)) {
				CwPrintTmplItem cwPrintTmplItem = new CwPrintTmplItem();
				setObjValue(formparamset0002, cwPrintTmplItem);
				cwPrintTmplItemFeign.insert(cwPrintTmplItem);
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
		FormData formparamset0002 = formService.getFormData("paramset0002");
		getFormValue(formparamset0002, getMapByJson(ajaxData));
		CwPrintTmplItem cwPrintTmplItemJsp = new CwPrintTmplItem();
		setObjValue(formparamset0002, cwPrintTmplItemJsp);
		CwPrintTmplItem cwPrintTmplItem = cwPrintTmplItemFeign.getById(cwPrintTmplItemJsp);
		if (cwPrintTmplItem != null) {
			try {
				cwPrintTmplItem = (CwPrintTmplItem) EntityUtil.reflectionSetVal(cwPrintTmplItem, cwPrintTmplItemJsp,
						getMapByJson(ajaxData));
				cwPrintTmplItemFeign.update(cwPrintTmplItem);
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
			FormData formparamset0002 = formService.getFormData("paramset0002");
			getFormValue(formparamset0002, getMapByJson(ajaxData));
			if (this.validateFormData(formparamset0002)) {
				CwPrintTmplItem cwPrintTmplItem = new CwPrintTmplItem();
				setObjValue(formparamset0002, cwPrintTmplItem);
				cwPrintTmplItemFeign.update(cwPrintTmplItem);
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
	public Map<String, Object> getByIdAjax(String busType, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formparamset0002 = formService.getFormData("paramset0002");
		CwPrintTmplItem cwPrintTmplItem = new CwPrintTmplItem();
		cwPrintTmplItem.setBusType(busType);
		cwPrintTmplItem.setId(id);
		cwPrintTmplItem = cwPrintTmplItemFeign.getById(cwPrintTmplItem);
		getObjValue(formparamset0002, cwPrintTmplItem, formData);
		if (cwPrintTmplItem != null) {
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
	public Map<String, Object> deleteAjax(String busType, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwPrintTmplItem cwPrintTmplItem = new CwPrintTmplItem();
		cwPrintTmplItem.setBusType(busType);
		cwPrintTmplItem.setId(id);
		try {
			cwPrintTmplItemFeign.delete(cwPrintTmplItem);
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
		FormData formparamset0002 = formService.getFormData("paramset0002");
		model.addAttribute("formparamset0002", formparamset0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwPrintTmplItem_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String busType, String id) throws Exception {
		ActionContext.initialize(request, response);
		FormData formparamset0001 = formService.getFormData("paramset0001");
		getFormValue(formparamset0001);
		CwPrintTmplItem cwPrintTmplItem = new CwPrintTmplItem();
		cwPrintTmplItem.setBusType(busType);
		cwPrintTmplItem.setId(id);
		cwPrintTmplItem = cwPrintTmplItemFeign.getById(cwPrintTmplItem);
		getObjValue(formparamset0001, cwPrintTmplItem);
		model.addAttribute("cwPrintTmplItem", cwPrintTmplItem);
		model.addAttribute("formparamset0001", formparamset0001);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwPrintTmplItem_Detail";
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
		FormData formparamset0002 = formService.getFormData("paramset0002");
		getFormValue(formparamset0002);
		boolean validateFlag = this.validateFormData(formparamset0002);
		model.addAttribute("formparamset0002", formparamset0002);
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
		FormData formparamset0002 = formService.getFormData("paramset0002");
		getFormValue(formparamset0002);
		boolean validateFlag = this.validateFormData(formparamset0002);
		model.addAttribute("formparamset0002", formparamset0002);
		model.addAttribute("query", query);
	}

	/**
	 * 
	 * 方法描述： 根据type获取模板内容
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-15 下午4:15:44
	 */
	@RequestMapping(value = "/getTmplObjByTypeAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTmplObjByTypeAjax(String busType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwPrintTmplItem cwPrintTmplItem = new CwPrintTmplItem();
		cwPrintTmplItem.setBusType(busType);
		// cwPrintTmplItem.setId(id);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			Map<String, Object> tdMap = cwPrintTmplItemFeign.getTmplObjByType(cwPrintTmplItem,finBooks);

			dataMap.put("itemPlate", tdMap.get("itemPlate"));

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

	@RequestMapping(value = "/getTmplCodeByTypeAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTmplCodeByTypeAjax(String busType) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwPrintTmplItem cwPrintTmplItem = new CwPrintTmplItem();
		cwPrintTmplItem.setBusType(busType);
		// cwPrintTmplItem.setId(id);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			Map<String, Object> tdMap = cwPrintTmplItemFeign.getTmplCodeByType(cwPrintTmplItem,finBooks);

			dataMap.put("mapobj", tdMap.get("mapobj"));

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
	 * 
	 * 方法描述： 保存模版的内容
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-16 上午9:57:30
	 */
	@RequestMapping(value = "/saveTmplCodeAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTmplCodeAjax(String ajaxData) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			Map<String, Object> dataMaps = cwPrintTmplItemFeign.doSaveTmplCode(formMap,finBooks);
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
	 * 
	 * 方法描述： 选择打印方式
	 * 
	 * @return
	 * @throws Exceptions
	 *             String
	 * @author lzshuai
	 * @date 2017-9-18 下午3:23:47
	 */
	@RequestMapping(value = "/choosePrintType")
	public String choosePrintType(Model model, String fincId, String repayId, String busType, String cusNo, String appId, String pactId, String pactModelNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("fincId", fincId);// 借据号
		model.addAttribute("repayId", repayId);// 还款历史ID
		model.addAttribute("busType", busType);// busType,是还款还是放款
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("appId", appId);
		model.addAttribute("pactId", pactId);
		model.addAttribute("pactModelNo", pactModelNo);
		return "/component/finance/paramset/choosePrintType";
	}

	@RequestMapping(value = "/getPrintMessageByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPrintMessageByIdAjax(String fincId, String repayId, String busType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap.put("fincId", fincId);// 借据号
			dataMap.put("repayId", repayId);// 还款历史ID
			dataMap.put("busType", busType);//// busType,是还款还是放款
			dataMap = cwPrintTmplItemFeign.getPrintMessageById(dataMap);
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

}
