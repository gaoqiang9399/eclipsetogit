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

import app.base.User;
import app.component.finance.cwtools.feign.CwMonthKnotFeign;
import app.component.finance.othreport.feign.CwKemuHuiZongFeign;
import app.component.finance.util.CwPublicUtil;
import app.util.toolkit.Ipage;

/**
 * 类名： KemuHuiZongAction 描述：
 * 
 * @author 刘争帅
 * @date 2017-1-10 上午10:34:59
 *
 *
 */
@Controller
@RequestMapping("/cwKemuHuiZong")
public class CwKemuHuiZongController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwKemuHuiZongFeign cwKemuHuiZongFeign;
	@Autowired
	public CwMonthKnotFeign cwMonthKnotFeign;
	// 全局变量
	private String query;

	public CwKemuHuiZongController() {
		query = "";
	}

	/**
	 * 科目汇总列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		// 修改凭证整理的最小结账周期
		String date = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);
		model.addAttribute("date", date);
		return "/component/finance/othreport/CwKemuHuizong_List";
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
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			// cwComItem.setCustomQuery(ajaxData);//自定义查询参数赋值
			// cwComItem.setCriteriaList(cwKemuHuiZong, ajaxData);//我的筛选
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			formMap.put("regNo", User.getRegNo(request));
			formMap.put("regName", User.getRegName(request));
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 根据条件 获取凭证总张数，附件总张数
			Map<String, String> teMap = cwKemuHuiZongFeign.getMapBymap(formMap,finBooks);
			String pzsum = String.valueOf(teMap.get("pzsum"));
			String fjsum = String.valueOf(teMap.get("fjsum"));
			// 自定义查询Feign方法
			Map<String, Object> params = new HashMap<>();
			params.put("formMap", formMap);
			ipage.setParams(params);
			ipage = cwKemuHuiZongFeign.findByPage(ipage,finBooks);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("pzsum", pzsum);
			dataMap.put("fjsum", fjsum);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
