package app.component.auth.controller;

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

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.auth.entity.MfCusCreditSearch;
import app.component.auth.feign.MfCusCreditSearchFeign;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping("/mfCusCreditSearch")
public class MfCusCreditSearchController extends BaseFormBean {
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCreditSearchFeign mfCusCreditSearchFeign;
	// 全局变量
	//异步参数
	//表单变量
	
	
	/**
	 *  列表打开页面请求
	 * @return
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model){
		return "/component/auth/MfCusCreditSearch_List";
		
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditSearch mfCusCreditSearch = new MfCusCreditSearch();
		try {
			mfCusCreditSearch.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCreditSearch.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfCusCreditSearch.setCriteriaList(mfCusCreditSearch, ajaxData);//我的筛选
			//this.getRoleConditions(CreditSearch,"1000000001");//记录级权限控制方法
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCreditSearch", mfCusCreditSearch));
			ipage = mfCusCreditSearchFeign.findByPage(ipage);
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
