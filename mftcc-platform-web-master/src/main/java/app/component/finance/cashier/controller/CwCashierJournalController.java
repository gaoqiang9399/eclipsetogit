package app.component.finance.cashier.controller;

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

import app.component.common.EntityUtil;
import app.component.finance.cashier.entity.CwCashierAccount;
import app.component.finance.cashier.entity.CwCashierJournal;
import app.component.finance.cashier.feign.CwCashierJournalFeign;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.util.CwPublicUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: CwCashierJournalAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Mar 27 16:43:21 CST 2017
 **/
@Controller
@RequestMapping("/cwCashierJournal")
public class CwCashierJournalController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwCashierJournalFeign cwCashierJournalFeign;
	@Autowired
	private CwToolsFeign cwToolsFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormData formcashier0002;
	private FormService formService = new FormService();

	public CwCashierJournalController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String txdate = DateUtil.getDate();
		dataMap.put("weeks", txdate.substring(0, 6));
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		List<CwCashierAccount> list = cwToolsFeign.getCwCashierAccountList(new CwCashierAccount(),finBooks);
		dataMap.put("accounts", list);

		JSONObject json = new JSONObject();
		JSONArray roleArray = JSONArray.fromObject(list);
		for (int i = 0; i < roleArray.size(); i++) {
			if (i == 0) {
				String uid = roleArray.getJSONObject(i).getString("uid");
				ActionContext.getActionContext().getRequest().setAttribute("uid", uid);
			}
			roleArray.getJSONObject(i).put("id", roleArray.getJSONObject(i).getString("uid"));
			roleArray.getJSONObject(i).put("name", roleArray.getJSONObject(i).getString("accountName"));
		}
		json.put("accountData", roleArray);
		String ajaxData = json.toString();
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("ajaxData", ajaxData);
		return "/component/finance/cashier/CwCashierJournal_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		try {
			// cwCashierJournal.setCustomQuery(ajaxData);//自定义查询参数赋值
			// cwCashierJournal.setCriteriaList(cwCashierJournal, ajaxData);//我的筛选
			// this.getRoleConditions(cwCashierJournal,"1000000001");//记录级权限控制方法
			Map<String, String> paramMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("paramMap", paramMap);
			ipage.setParams(params);
			// 自定义查询Bo方法
			ipage = cwCashierJournalFeign.getCashierJournalPage(ipage, finBooks);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map paramMap = CwPublicUtil.toMap(ajaxData);
			CwCashierJournal cwCashierJournal = (CwCashierJournal) CwPublicUtil.convertMap(CwCashierJournal.class, paramMap);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwCashierJournalFeign.insert(cwCashierJournal,finBooks);
			dataMap.put("flag", "success");
			// dataMap.put("msg", "新增成功");

			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增失败");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
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
		CwCashierJournal cwCashierJournal = new CwCashierJournal();
		cwCashierJournal.setUid(uid);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwCashierJournalFeign.delete(cwCashierJournal,finBooks);
			dataMap.put("flag", "success");
			// dataMap.put("msg", "成功");

			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			// dataMap.put("msg", "失败");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 进入核对总账报表页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-4-1 下午2:10:08
	 */
	@RequestMapping(value = "/toCashierReport")
	public String toCashierReport(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String txdate = DateUtil.getDate();
		model.addAttribute("weeks", txdate.substring(0, 6));
		return "/component/finance/cashier/CwCashierReport";
	}

	/**
	 * 方法描述： 获取核对总账报表数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-4-1 下午2:10:28
	 */
	@RequestMapping(value = "/getCashierReportAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCashierReportAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwCashierJournal cwCashierJournal = new CwCashierJournal();
		try {
			Map<String, String> paramMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("paramMap", paramMap);
			ipage.setParams(params);
			// 自定义查询Bo方法
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwCashierJournalFeign.getCashierReportData(ipage,finBooks);
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
		formcashier0002 = formService.getFormData("cashier0002");
		getFormValue(formcashier0002, getMapByJson(ajaxData));
		CwCashierJournal cwCashierJournalJsp = new CwCashierJournal();
		setObjValue(formcashier0002, cwCashierJournalJsp);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwCashierJournal cwCashierJournal = cwCashierJournalFeign.getById(cwCashierJournalJsp,finBooks);
		if (cwCashierJournal != null) {
			try {
				cwCashierJournal = (CwCashierJournal) EntityUtil.reflectionSetVal(cwCashierJournal, cwCashierJournalJsp,
						getMapByJson(ajaxData));
				cwCashierJournalFeign.update(cwCashierJournal,finBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				// dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				// dataMap.put("msg", "新增失败");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "编号不存在,保存失败");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
			formcashier0002 = formService.getFormData("cashier0002");
			getFormValue(formcashier0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcashier0002)) {
				CwCashierJournal cwCashierJournal = new CwCashierJournal();
				String finBooks = (String) request.getSession().getAttribute("finBooks");
				setObjValue(formcashier0002, cwCashierJournal);
				cwCashierJournalFeign.update(cwCashierJournal,finBooks);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "更新成功");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();

			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			// dataMap.put("msg", "更新失败");
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
	public Map<String, Object> getByIdAjax(String weeks, String accountNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formcashier0002 = formService.getFormData("cashier0002");
		CwCashierJournal cwCashierJournal = new CwCashierJournal();
		cwCashierJournal.setWeeks(weeks);
		cwCashierJournal.setAccountNo(accountNo);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwCashierJournal = cwCashierJournalFeign.getById(cwCashierJournal,finBooks);
		getObjValue(formcashier0002, cwCashierJournal, formData);
		if (cwCashierJournal != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
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
		formcashier0002 = formService.getFormData("cashier0002");
		model.addAttribute("formcashier0002", formcashier0002);
		model.addAttribute("query", query);
		return "/component/finance/cashier/CwCashierJournal_Insert";
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
		formcashier0002 = formService.getFormData("cashier0002");
		getFormValue(formcashier0002);
		CwCashierJournal cwCashierJournal = new CwCashierJournal();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		setObjValue(formcashier0002, cwCashierJournal);
		cwCashierJournalFeign.insert(cwCashierJournal,finBooks);
		getObjValue(formcashier0002, cwCashierJournal);
//		this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwCashierJournal", cwCashierJournal);
		ipage.setParams(params);
		List<CwCashierJournal> cwCashierJournalList = (List<CwCashierJournal>) cwCashierJournalFeign
				.findByPage(ipage,finBooks).getResult();
		model.addAttribute("cwCashierJournal", cwCashierJournal);
		model.addAttribute("formcashier0002", formcashier0002);
		model.addAttribute("cwCashierJournalList", cwCashierJournalList);
		model.addAttribute("query", query);
		return "/component/finance/cashier/CwCashierJournal_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String weeks, String accountNo) throws Exception {
		ActionContext.initialize(request, response);
		formcashier0002 = formService.getFormData("cashier0001");
		getFormValue(formcashier0002);
		CwCashierJournal cwCashierJournal = new CwCashierJournal();
		cwCashierJournal.setWeeks(weeks);
		cwCashierJournal.setAccountNo(accountNo);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwCashierJournal = cwCashierJournalFeign.getById(cwCashierJournal,finBooks);
		getObjValue(formcashier0002, cwCashierJournal);
		model.addAttribute("cwCashierJournal", cwCashierJournal);
		model.addAttribute("formcashier0002", formcashier0002);
		model.addAttribute("query", query);
		return "/component/finance/cashier/CwCashierJournal_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String weeks, String accountNo) throws Exception {
		ActionContext.initialize(request, response);
		CwCashierJournal cwCashierJournal = new CwCashierJournal();
		cwCashierJournal.setWeeks(weeks);
		cwCashierJournal.setAccountNo(accountNo);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwCashierJournalFeign.delete(cwCashierJournal,finBooks);
		return getListPage(model);
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
		formcashier0002 = formService.getFormData("cashier0002");
		getFormValue(formcashier0002);
		boolean validateFlag = this.validateFormData(formcashier0002);
		model.addAttribute("formcashier0002", formcashier0002);
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
		formcashier0002 = formService.getFormData("cashier0002");
		getFormValue(formcashier0002);
		boolean validateFlag = this.validateFormData(formcashier0002);
		model.addAttribute("formcashier0002", formcashier0002);
		model.addAttribute("query", query);
	}

}
