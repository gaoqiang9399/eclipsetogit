package app.component.eval.controller;

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

import app.component.eval.entity.MfCusAppEval;
import app.component.eval.feign.MfCusAppEvalFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/mfCusAppEval")
public class MfCusAppEvalController extends BaseFormBean {
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusAppEvalFeign mfCusAppEvalFeign;
	
	/**
	 *  列表打开页面请求
	 * @return
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		JSONArray evalLevelJsonArray = new CodeUtils().getJSONArrayByKeyName("EVAL_LEVEL");
		this.getHttpRequest().setAttribute("evalLevelJsonArray", evalLevelJsonArray);
		
		JSONArray cusTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CUS_TYPE");
		this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
		
		model.addAttribute("query", "");
		return "/component/eval/MfCusAppEval_List";
		
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,Ipage ipage) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusAppEval mfCusAppEval = new MfCusAppEval();
		try {
			mfCusAppEval.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusAppEval.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfCusAppEval.setCriteriaList(mfCusAppEval, ajaxData);//我的筛选
			//this.getRoleConditions(appEval,"1000000001");//记录级权限控制方法
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusAppEval", mfCusAppEval));
			//自定义查询Bo方法
			ipage = mfCusAppEvalFeign.findByPage(ipage);
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
