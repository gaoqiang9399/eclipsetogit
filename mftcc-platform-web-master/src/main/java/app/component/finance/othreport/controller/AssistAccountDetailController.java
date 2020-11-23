/**
 * Copyright (C) DXHM 版权所有
 * 文件名： KemuHuiZongAction.java
 * 包名： app.component.finance.othreport.action
 * 说明：
 * @author 刘争帅
 * @date 2017-1-10 上午10:34:59
 * @version V1.0
 */
package app.component.finance.othreport.controller;

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

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.finance.account.entity.CwFication;
import app.component.finance.cwtools.feign.CwMonthKnotFeign;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.othreport.entity.AccountBalance;
import app.component.finance.othreport.entity.AccountLedger;
import app.component.finance.othreport.feign.AssistAccountDetailFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * 类名： AssistAccountDetailAction 描述：
 * 
 * @author 刘争帅
 * @date 2017-2-6 上午10:34:59
 *
 *
 */
@Controller
@RequestMapping("/assistAccountDetail")
public class AssistAccountDetailController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入AssistAccountDetailFeign
	@Autowired
	private AssistAccountDetailFeign assistAccountDetailFeign;
	@Autowired
	public CwMonthKnotFeign cwMonthKnotFeign;
	@Autowired
	public CwToolsFeign cwToolsFeign;

	private String query;

	// 构造函数必须写，否则表单出不来
	public AssistAccountDetailController() {
		query = "";
	}

	/**
	 * 辅助核算明细账页面请求
	 * 
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String weeks = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);
		dataMap.put("weeks", weeks);
		List<CwFication> fications = cwToolsFeign.getCwFicationList(new CwFication(),finBooks);
		dataMap.put("fications", new Gson().toJson(fications));
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/othreport/AssistAccountDetail_List";
	}

	/**
	 * 方法描述： 获取辅助核算明细表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-4 上午10:15:52
	 */
	@RequestMapping(value = "/getAccountLedgerAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAccountLedgerAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			// 自定义查询Feign方法
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = assistAccountDetailFeign.getAccountLedgerData(formMap,finBooks);
			if(r.isOk()) {
				List<AccountLedger> list=(List<AccountLedger>) r.getResult();
				Ipage ipage = this.getIpage();
				ipage.setPageNo(pageNo);// 异步传页面翻页参数
				ipage.setPageCounts(list.size());
				ipage.setResult(list);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
				ipage.setResult(tableHtml);
				dataMap.put("ipage", ipage);
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取辅助核算余额表列表
	 * 
	 * @return String
	 * @author lzshuai
	 * @date 2017-2-13 上午10:38:21
	 */
	@RequestMapping(value = "/getBlanceListPage")
	public String getBlanceListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String weeks = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);
		dataMap.put("weeks", weeks);
		List<CwFication> fications = cwToolsFeign.getCwFicationList(new CwFication(),finBooks);
		dataMap.put("fications", new Gson().toJson(fications));
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/othreport/AssistAccountBlance_List";
	}

	/**
	 * 方法描述： 获取辅助核算余额表列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-4 上午10:22:09
	 */
	@RequestMapping(value = "/getAccountBalanceAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAccountBalanceAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			// 自定义查询Feign方法
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = assistAccountDetailFeign.getAccountBalanceData(formMap,finBooks);
			if(r.isOk()) {
				List<AccountBalance> list = (List<AccountBalance>) r.getResult();
				Ipage ipage = this.getIpage();
				ipage.setPageNo(pageNo);// 异步传页面翻页参数
				ipage.setPageCounts(list.size());
				ipage.setResult(list);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
				ipage.setResult(tableHtml);
				dataMap.put("ipage", ipage);
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取辅助核算余额表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-2-13 上午11:01:05
	 */
	@RequestMapping(value = "/findBalanceByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findBalanceByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			System.out.println(formMap);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			//
			// 自定义查询Feign方法
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			Map<String, Object> params = new HashMap<>();
			params.put("formMap", formMap);
			ipage.setParams(params);
			R r = assistAccountDetailFeign.findBalanceByPage(ipage,finBooks);
			if(r.isOk()) {
				ipage = (Ipage) r.getResult();
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
				ipage.setResult(tableHtml);
				dataMap.put("ipage", ipage);
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}

		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

}
