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

import app.component.finance.account.entity.CwComItem;
import app.component.finance.othreport.entity.CapJournalBean;
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
@RequestMapping("/capJournal")
public class CapJournalController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CapJournalFeign capJournalFeign;
	
	private String query;

	// 构造函数必须写，否则表单出不来
	public CapJournalController() {
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
			dataMap.put("date", DateUtil.getDate("yyyy-MM-dd"));
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<CwComItem> rlst = capJournalFeign.getselectDate("0",finBooks);
			dataMap.put("accNo", rlst.size() > 0 ? rlst.get(0).getAccNo() : "1001");
			model.addAttribute("dataMap", dataMap);
		} catch (Exception e) {
//			logger.error("getListPage方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			e.printStackTrace();
		}
		return "/component/finance/othreport/CapJournalList";
	}

	/**
	 * 方法描述： 查询资金日记账报表数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-5-17 下午6:21:32
	 */
	@RequestMapping(value = "/getCapJournalListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCapJournalListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<CapJournalBean> cwCapJournalList = capJournalFeign.getCapJournalList(formMap,finBooks);
			ipage.setResult(cwCapJournalList);
			ipage.setPageCounts(cwCapJournalList.size());
			// 返回相应的HTML方法
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

	/**
	 * 获取页面下拉框的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectDateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSelectDateAjax(String style) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<CwComItem> rlst = capJournalFeign.getselectDate(style,finBooks);
			dataMap.put("list", rlst);
		} catch (Exception e) {
//			logger.error("getSelectDateAjax方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			e.printStackTrace();
		}
		return dataMap;
	}

}
