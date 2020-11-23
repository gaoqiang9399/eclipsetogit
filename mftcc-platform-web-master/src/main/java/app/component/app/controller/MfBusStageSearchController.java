package app.component.app.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.prdctinterface.PrdctInterfaceFeign;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.app.entity.MfBusStageSearch;
import app.component.app.feign.MfBusStageSearchFeign;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping("/mfBusStageSearch")
public class MfBusStageSearchController extends BaseFormBean {
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusStageSearchFeign mfBusStageSearchFeign;

	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	// 全局变量
	//异步参数
	//表单变量
	
	/**
	 *  列表打开页面请求
	 * @return
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception{
		ActionContext.initialize(request, response);
		// 办理阶段
		// 根据业务模式获取所有的流程节点信息（过滤重复的）
		JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
		JSONArray jsonArray = JSONArray.fromObject(
				"[{\"optCode\":\"待提款\",\"optName\":\"待提款\"},{\"optCode\":\"待放款\",\"optName\":\"待放款\"},{\"optCode\":\"放款复核\",\"optName\":\"放款复核\"}]");
		flowNodeJsonArray.addAll(jsonArray);
		model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
		return "component/app/MfBusStageSearch_List";
		
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusStageSearch mfBusStageSearch = new MfBusStageSearch();
		try {
			mfBusStageSearch.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusStageSearch.setCriteriaList(mfBusStageSearch, ajaxData);//我的筛选
			//this.getRoleConditions(appEval,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusStageSearch", mfBusStageSearch));
			//自定义查询Bo方法
			ipage = mfBusStageSearchFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List<?>)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}



}
