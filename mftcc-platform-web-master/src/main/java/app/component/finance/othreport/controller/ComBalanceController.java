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

import app.component.finance.cwtools.feign.CwMonthKnotFeign;
import app.component.finance.othreport.entity.ComBalanceBean;
import app.component.finance.othreport.feign.ComBalanceFeign;
import app.component.finance.util.CwPublicUtil;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping("/comBalance")
public class ComBalanceController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CwMonthKnotFeign
	@Autowired
	public CwMonthKnotFeign cwMonthKnotFeign;
	@Autowired
	public ComBalanceFeign comBalanceFeign;
	
	private String query;

	// 构造函数必须写，否则表单出不来
	public ComBalanceController() {
		query = "";
	}

	/**
	 * 跳转至科目余额表展现页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			String week = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);
			dataMap.put("weeks", week);
			model.addAttribute("dataMap", dataMap);
		} catch (Exception e) {
//			logger.error("getListPage方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			e.printStackTrace();
		}
		return "/component/finance/othreport/ComBalanceList";
	}

	@RequestMapping(value = "/getComBalanceListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getComBalanceListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<ComBalanceBean> cwComBalanceLsit = comBalanceFeign.getComBalanceData(formMap,finBooks);
			// 返回相应的HTML方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageCounts(cwComBalanceLsit.size());// 异步传页面翻页参数
			ipage.setPageSize(cwComBalanceLsit.size());// 异步传页面翻页参数
			ipage.setResult(cwComBalanceLsit);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("查询凭证列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
