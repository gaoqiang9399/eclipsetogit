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
import app.component.finance.cashier.feign.CwCashierAccountFeign;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: CwCashierAccountAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Mar 27 16:39:02 CST 2017
 **/
@Controller
@RequestMapping("/cwCashierAccount")
public class CwCashierAccountController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CwCashierAccountFeign
	@Autowired
	private CwCashierAccountFeign cwCashierAccountFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormData formcashier0002;
	private FormService formService = new FormService();

	public CwCashierAccountController() {
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
		return "/component/finance/cashier/CwCashierAccount_List";
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
		CwCashierAccount cwCashierAccount = new CwCashierAccount();
		try {
			cwCashierAccount.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwCashierAccount.setCriteriaList(cwCashierAccount, ajaxData);// 我的筛选
			// this.getRoleConditions(cwCashierAccount,"1000000001");//记录级权限控制方法
			if (cwCashierAccount.getCriteriaLists() == null || cwCashierAccount.getCriteriaLists().size() == 0) {
				cwCashierAccount.setAccountType("1");// 默认现金列表
			}
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("cwCashierAccount", cwCashierAccount);
			ipage.setParams(params);
			// 自定义查询Bo方法
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwCashierAccountFeign.findByPage(ipage,finBooks);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String accountType, String accountNo) throws Exception {
		ActionContext.initialize(request, response);
		formcashier0002 = formService.getFormData("cashier0002");
		getFormValue(formcashier0002);
		CwCashierAccount cwCashierAccount = new CwCashierAccount();
		cwCashierAccount.setAccountType(accountType);
		if (StringUtil.isNotEmpty(accountNo)) {
			cwCashierAccount.setAccountNo(accountNo);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwCashierAccount = cwCashierAccountFeign.getById(cwCashierAccount,finBooks);
		}
		getObjValue(formcashier0002, cwCashierAccount);
		model.addAttribute("cwCashierAccount", cwCashierAccount);
		model.addAttribute("formcashier0002", formcashier0002);
		model.addAttribute("query", query);
		return "/component/finance/cashier/CwCashierAccount_Insert";
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
			formcashier0002 = formService.getFormData("cashier0002");
			getFormValue(formcashier0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcashier0002)) {
				CwCashierAccount cwCashierAccount = new CwCashierAccount();
				setObjValue(formcashier0002, cwCashierAccount);
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwCashierAccountFeign.insert(cwCashierAccount,finBooks);
				if(r.isOk()) {
					
					dataMap.put("flag", "success");
					// dataMap.put("msg", "新增成功");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				// dataMap.put("msg",this.getFormulavaliErrorMsg());
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增失败");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			e.printStackTrace();
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
		CwCashierAccount cwCashierAccountJsp = new CwCashierAccount();
		setObjValue(formcashier0002, cwCashierAccountJsp);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		CwCashierAccount cwCashierAccount = cwCashierAccountFeign.getById(cwCashierAccountJsp,finBooks);
		if (cwCashierAccount != null) {
			try {
				cwCashierAccount = (CwCashierAccount) EntityUtil.reflectionSetVal(cwCashierAccount, cwCashierAccountJsp,
						getMapByJson(ajaxData));
				cwCashierAccountFeign.update(cwCashierAccount,finBooks);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "保存成功");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				// dataMap.put("msg", "新增失败");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "编号不存在,保存失败");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
				CwCashierAccount cwCashierAccount = new CwCashierAccount();
				String finBooks = (String) request.getSession().getAttribute("finBooks");
				setObjValue(formcashier0002, cwCashierAccount);
				cwCashierAccountFeign.update(cwCashierAccount,finBooks);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "更新成功");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				// dataMap.put("msg",this.getFormulavaliErrorMsg());
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			// dataMap.put("msg", "更新失败");
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
	public Map<String, Object> getByIdAjax(String accountType, String accountNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formcashier0002 = formService.getFormData("cashier0002");
		CwCashierAccount cwCashierAccount = new CwCashierAccount();
		cwCashierAccount.setAccountNo(accountNo);
		cwCashierAccount.setAccountType(accountType);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwCashierAccount = cwCashierAccountFeign.getById(cwCashierAccount,finBooks);
		getObjValue(formcashier0002, cwCashierAccount, formData);
		if (cwCashierAccount != null) {
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
	public Map<String, Object> deleteAjax(String accountType, String accountNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwCashierAccount cwCashierAccount = new CwCashierAccount();
		cwCashierAccount.setAccountNo(accountNo);
		cwCashierAccount.setAccountType(accountType);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwCashierAccountFeign.delete(cwCashierAccount,finBooks);
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
		CwCashierAccount cwCashierAccount = new CwCashierAccount();
		setObjValue(formcashier0002, cwCashierAccount);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwCashierAccountFeign.insert(cwCashierAccount,finBooks);
		getObjValue(formcashier0002, cwCashierAccount);
		Ipage ipage = this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwCashierAccount", cwCashierAccount);
		ipage.setParams(params);
		List<CwCashierAccount> cwCashierAccountList = (List<CwCashierAccount>) cwCashierAccountFeign
				.findByPage(ipage,finBooks).getResult();
		model.addAttribute("cwCashierAccount", cwCashierAccount);
		model.addAttribute("formcashier0002", formcashier0002);
		model.addAttribute("cwCashierAccountList", cwCashierAccountList);
		model.addAttribute("query", query);
		return "/component/finance/cashier/CwCashierAccount_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String accountNo, String accountType) throws Exception {
		ActionContext.initialize(request, response);
		formcashier0002 = formService.getFormData("cashier0001");
		getFormValue(formcashier0002);
		CwCashierAccount cwCashierAccount = new CwCashierAccount();
		cwCashierAccount.setAccountNo(accountNo);
		cwCashierAccount.setAccountType(accountType);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwCashierAccount = cwCashierAccountFeign.getById(cwCashierAccount,finBooks);
		getObjValue(formcashier0002, cwCashierAccount);
		model.addAttribute("cwCashierAccount", cwCashierAccount);
		model.addAttribute("formcashier0002", formcashier0002);
		model.addAttribute("query", query);
		return "/component/finance/cashier/CwCashierAccount_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String accountNo, String accountType) throws Exception {
		ActionContext.initialize(request, response);
		CwCashierAccount cwCashierAccount = new CwCashierAccount();
		cwCashierAccount.setAccountNo(accountNo);
		cwCashierAccount.setAccountType(accountType);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwCashierAccountFeign.delete(cwCashierAccount,finBooks);
		return getListPage();
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
