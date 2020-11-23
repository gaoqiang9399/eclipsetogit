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
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.othreport.entity.DetailAccount;
import app.component.finance.othreport.feign.DetailAccountFeign;
import app.component.finance.util.CwPublicUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;

@Controller
@RequestMapping("/detailAccount")
public class DetailAccountController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private DetailAccountFeign detailAccountFeign;
	@Autowired
	public CwMonthKnotFeign cwMonthKnotFeign;
	@Autowired
	public CwToolsFeign cwToolsFeign;
	// 表单变量
	private String query;

	public DetailAccountController() {
		query = "";
	}

	/**
	 * 方法描述： 进入明细账报表页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-23 下午3:20:54
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String accNo, String beginWeek, String endWeek) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		// accNo不为空，表示是从总账页面跳转过来的
		if (StringUtil.isNotEmpty(accNo)) {
			dataMap.put("accNo", accNo);
			dataMap.put("accName", cwToolsFeign.getAccNameByAccNo(accNo,finBooks));
			dataMap.put("beginWeek", beginWeek);
			dataMap.put("endWeek", endWeek);
		} else {
			String noCloseWeek = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);
			dataMap.put("accNo", "");
			dataMap.put("beginWeek", noCloseWeek);
			dataMap.put("endWeek", noCloseWeek);
		}
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/othreport/detailAccountList";
	}

	/**
	 * 方法描述： 财务明细分类账报表查询
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-23 下午3:20:35
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = null;
			if ("y".equals(request.getParameter("dFlag"))) {// 从其他页面跳转到明细jsp
				formMap = new HashMap<String, String>();
				formMap.put("fromWhere", "");
				formMap.put("beginWeek", request.getParameter("beginWeek"));
				formMap.put("endWeek", request.getParameter("endWeek"));
				formMap.put("accNo", request.getParameter("accNo"));
			} else {// 正常请求
				formMap = CwPublicUtil.getMapByJson(ajaxData);
			}

			// 自定义查询Feign方法
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<DetailAccount> list = detailAccountFeign.getSeparateAccountData(formMap,finBooks);
			Ipage ipage = this.getIpage();
			ipage.setResult(list);
			ipage.setPageNo(list.size());// 异步传页面翻页参数
			ipage.setPageCounts(list.size());
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