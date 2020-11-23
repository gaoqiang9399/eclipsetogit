package app.component.finance.finreport.controller;

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

import app.component.finance.account.entity.CwComItem;
import app.component.finance.finreport.entity.CapDayReportBean;
import app.component.finance.finreport.feign.CapDayReportFeign;
import app.component.finance.othreport.feign.CapJournalFeign;
import app.component.finance.util.CwPublicUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;

/**
 * 现金日记账Action层
 * 
 * @author Yanght
 *
 */
@Controller
@RequestMapping("/capDayReport")
public class CapDayReportController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CapDayReportFeign capDayReportFeign;
	@Autowired
	private CapJournalFeign capJournalFeign;


	/**
	 * 跳转至资金日报表展现页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("date", DateUtil.getDate("yyyy-MM-dd"));
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		List<CwComItem> rlst = capJournalFeign.getselectDate("0",finBooks);
		dataMap.put("accNo", rlst.size() > 0 ? rlst.get(0).getAccNo() : "1001");
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/finreport/CapDayReportList";
	}

	@RequestMapping(value = "/getCapDayReportDataAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCapDayReportDataAjax(Integer pageNo, Integer pageSize, String tableId,
			String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<CapDayReportBean> list = capDayReportFeign.getCapDayReportData(formMap,finBooks);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setResult(list);
			ipage.setPageCounts(list.size());
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("list", list);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("查询凭证列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
