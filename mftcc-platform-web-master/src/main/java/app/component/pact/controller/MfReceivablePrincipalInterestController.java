package app.component.pact.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.pact.entity.MfReceivablePrincipalInterest;
import app.component.pact.feign.MfReceivablePrincipalInterestFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mfReceivablePrincipalInterest")
public class MfReceivablePrincipalInterestController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceivablePrincipalInterestFeign mfReceivablePrincipalInterestFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) {
		ActionContext.initialize(request, response);
		return "component/pact/MfReceivablePrincipalInterest_List";

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
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablePrincipalInterest mfReceivablePrincipalInterest = new MfReceivablePrincipalInterest();
		String scopeType = "0";
		try {
			// 取出ajax数据
			Gson gson = new Gson();
			JSONArray jsonArray = gson.fromJson(ajaxData, JSONArray.class);
			if (jsonArray.get(0) instanceof JSONArray) {
				JSONArray jsonArraySub = jsonArray.getJSONArray(0);
				for (int i = 0; i < jsonArraySub.size(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArraySub.get(i);
					if ("scope".equals((String) jsonObj.get("condition"))
							&& StringUtil.isNotEmpty((String) jsonObj.get("value"))) {
						scopeType = (String) jsonObj.get("value");
						if ("1".equals(scopeType)) {// 月内到期
							mfReceivablePrincipalInterest.setScopeType(scopeType);
							mfReceivablePrincipalInterest
									.setMaxMonthDate(DateUtil.getMaxMonthDate(DateUtil.getDate(), "yyyyMMdd"));
						}
					}
				}
			}

			mfReceivablePrincipalInterest.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReceivablePrincipalInterest.setCriteriaList(mfReceivablePrincipalInterest, ajaxData);// 我的筛选
			// this.getRoleConditions(appEval,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("mfReceivablePrincipalInterest",mfReceivablePrincipalInterest);
			paramMap.put("scopeType",scopeType);
			ipage.setParams(this.setIpageParams("paramMap", paramMap));
			// 自定义查询Bo方法
			ipage = mfReceivablePrincipalInterestFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List<?>) ipage.getResult(), ipage, true);
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
