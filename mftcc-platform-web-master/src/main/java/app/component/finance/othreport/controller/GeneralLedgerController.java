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
import app.component.finance.othreport.entity.GeneralLedgerShow;
import app.component.finance.othreport.feign.GeneralLedgerFeign;
import app.component.finance.util.CwPublicUtil;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping("/generalLedger")
public class GeneralLedgerController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private GeneralLedgerFeign generalLedgerFeign;
	@Autowired
	public CwMonthKnotFeign cwMonthKnotFeign;
	// 表单变量
	private String query;

	// 构造函数必须写，否则表单出不来
	public GeneralLedgerController() {
		query = "";
	}

	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		String date = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);// 取最小未结账期
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("date", date);
		dataMap.put("maxCloseWeek", date);
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/othreport/GeneralLedgerReport";
	}

	/**
	 * 方法描述： 获取总账报表HTML
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-22 下午2:27:58
	 */
	@RequestMapping(value = "/getGeneralLedgerAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getGeneralLedgerAjax(String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
		try {
			Ipage ipage = this.getIpage();
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<GeneralLedgerShow> list = generalLedgerFeign.getGeneralLedgerData(formMap,finBooks);
			ipage.setResult(list);
			ipage.setPageNo(list.size());// 异步传页面翻页参数
			ipage.setPageCounts(list.size());
			ipage.setPageSum(list.size());
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

}
