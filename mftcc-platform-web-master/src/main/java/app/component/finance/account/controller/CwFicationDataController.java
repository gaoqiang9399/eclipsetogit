package app.component.finance.account.controller;

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
import app.component.finance.account.entity.CwFicationData;
import app.component.finance.account.feign.CwFicationDataFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwFicationDataAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jan 23 10:26:36 CST 2017
 **/
@Controller
@RequestMapping("/cwFicationData")
public class CwFicationDataController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CwFicationDataFeign
	@Autowired
	private CwFicationDataFeign cwFicationDataFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormData formficationdata0002;
	private FormService formService = new FormService();

	public CwFicationDataController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String txType) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("txType", txType);
		return "/component/finance/account/CwFicationData_List";
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
			String ajaxData, String txType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwFicationData cwFicationData = new CwFicationData();
		try {
			cwFicationData.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwFicationData.setCriteriaList(cwFicationData, ajaxData);// 我的筛选
			// this.getRoleConditions(cwFicationData,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("cwFicationData", cwFicationData);
			ipage.setParams(params);
			// 自定义查询Bo方法
			cwFicationData.setTxType(txType);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwFicationDataFeign.findByPage(ipage,finBooks);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 根据类别获取辅助核算数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-1 下午3:58:39
	 */
	@RequestMapping(value = "/getFicationDataAjax")
	@ResponseBody
	public Map<String, Object> getFicationDataAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("formMap", formMap);
			ipage.setParams(params);
			// 自定义查询Bo方法
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwFicationDataFeign.getFicationData(ipage,finBooks);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			formficationdata0002 = formService.getFormData("ficationdata0002");
			getFormValue(formficationdata0002, getMapByJson(ajaxData));
			if (this.validateFormData(formficationdata0002)) {
				CwFicationData cwFicationData = new CwFicationData();
				setObjValue(formficationdata0002, cwFicationData);
				R r = cwFicationDataFeign.insert(cwFicationData,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 异步新增辅助核算弹窗添加
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-3 下午4:23:52
	 */
	@RequestMapping(value = "/insertDialogAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertDialogAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			Map<String, String> formMap = new Gson().fromJson(ajaxData, Map.class);
			CwFicationData cwFicationData = new CwFicationData();
			cwFicationData.setTxType(formMap.get("txType"));
			cwFicationData.setTxCode(formMap.get("txCode"));
			cwFicationData.setTxName(formMap.get("txName"));
			cwFicationDataFeign.insert(cwFicationData,finBooks);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		formficationdata0002 = formService.getFormData("ficationdata0002");
		getFormValue(formficationdata0002, getMapByJson(ajaxData));
		CwFicationData cwFicationDataJsp = new CwFicationData();
		setObjValue(formficationdata0002, cwFicationDataJsp);
		CwFicationData cwFicationData = cwFicationDataFeign.getById(cwFicationDataJsp,finBooks);
		if (cwFicationData != null) {
			try {
				cwFicationData = (CwFicationData) EntityUtil.reflectionSetVal(cwFicationData, cwFicationDataJsp,
						getMapByJson(ajaxData));
				cwFicationDataFeign.update(cwFicationData,finBooks);
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
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			formficationdata0002 = formService.getFormData("ficationdata0002");
			getFormValue(formficationdata0002, getMapByJson(ajaxData));
			if (this.validateFormData(formficationdata0002)) {
				CwFicationData cwFicationData = new CwFicationData();
				setObjValue(formficationdata0002, cwFicationData);
				cwFicationDataFeign.update(cwFicationData,finBooks);
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String uid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		formficationdata0002 = formService.getFormData("ficationdata0002");
		CwFicationData cwFicationData = new CwFicationData();
		cwFicationData.setUid(uid);
		cwFicationData = cwFicationDataFeign.getById(cwFicationData,finBooks);
		getObjValue(formficationdata0002, cwFicationData, formData);
		if (cwFicationData != null) {
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
	public Map<String, Object> deleteAjax(String uid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwFicationData cwFicationData = new CwFicationData();
		cwFicationData.setUid(uid);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwFicationDataFeign.delete(cwFicationData,finBooks);
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
	public String input(Model model, String txType) throws Exception {
		ActionContext.initialize(request, response);
		CwFicationData cwFicationData = new CwFicationData();
		cwFicationData.setTxType(txType);
		formficationdata0002 = formService.getFormData("ficationdata0002");
		getObjValue(formficationdata0002, cwFicationData);
		model.addAttribute("formficationdata0002", formficationdata0002);
		model.addAttribute("query", query);
		return "/component/finance/account/CwFicationData_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formficationdata0002 = formService.getFormData("ficationdata0002");
		getFormValue(formficationdata0002);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwFicationData cwFicationData = new CwFicationData();
		setObjValue(formficationdata0002, cwFicationData);
		cwFicationDataFeign.insert(cwFicationData,finBooks);
		getObjValue(formficationdata0002, cwFicationData);
		Ipage ipage=this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwFicationData", cwFicationData);
		ipage.setParams(params);
		List<CwFicationData> cwFicationDataList = (List<CwFicationData>) cwFicationDataFeign
				.findByPage(ipage ,finBooks).getResult();
		model.addAttribute("cwFicationData", cwFicationData);
		model.addAttribute("formficationdata0002", formficationdata0002);
		model.addAttribute("cwFicationDataList", cwFicationDataList);
		model.addAttribute("query", query);
		return "/component/finance/account/CwFicationData_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String uid) throws Exception {
		ActionContext.initialize(request, response);
		formficationdata0002 = formService.getFormData("ficationdata0001");
		getFormValue(formficationdata0002);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwFicationData cwFicationData = new CwFicationData();
		cwFicationData.setUid(uid);
		cwFicationData = cwFicationDataFeign.getById(cwFicationData,finBooks);
		getObjValue(formficationdata0002, cwFicationData);
		model.addAttribute("cwFicationData", cwFicationData);
		model.addAttribute("formficationdata0002", formficationdata0002);
		model.addAttribute("query", "");
		return "/component/finance/account/CwFicationData_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String uid) throws Exception {
		ActionContext.initialize(request, response);
		CwFicationData cwFicationData = new CwFicationData();
		cwFicationData.setUid(uid);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwFicationDataFeign.delete(cwFicationData,finBooks);
		// TODO
		return getListPage(null, uid);
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
		formficationdata0002 = formService.getFormData("ficationdata0002");
		getFormValue(formficationdata0002);
		boolean validateFlag = this.validateFormData(formficationdata0002);
		model.addAttribute("formficationdata0002", formficationdata0002);
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
		formficationdata0002 = formService.getFormData("ficationdata0002");
		getFormValue(formficationdata0002);
		boolean validateFlag = this.validateFormData(formficationdata0002);
		model.addAttribute("formficationdata0002", formficationdata0002);
		model.addAttribute("query", query);
	}

	/**
	 * 方法描述：系统同步 员工、部门、客户到辅助核算数据表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sysDataSyncAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sysDataSyncAjax(String type) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//String type = ActionContext.getActionContext().getRequest().getParameter("type");
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwFicationDataFeign.insertSysDataFicationData(type,finBooks);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增一级科目失败");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

}
