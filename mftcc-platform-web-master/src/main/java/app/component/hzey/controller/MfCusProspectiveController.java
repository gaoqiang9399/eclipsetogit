package  app.component.hzey.controller;
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

import app.component.app.entity.MfCusAndApply;
import app.component.hzey.feign.MfCusProspectiveFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * 杭州恩义潜在客户action
 **/
@Controller
@RequestMapping("/mfCusProspective")
public class MfCusProspectiveController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusIcloudManageBo
	@Autowired
	private MfCusProspectiveFeign mfCusProspectiveFeign;
	//全局变量
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		//从数据字典中拿到缓存
		JSONArray jsonArrayByKeyName = new CodeUtils().getJSONArrayByKeyName("TRACK_TYPE");
		model.addAttribute("jsonArrayByKeyName", jsonArrayByKeyName);
		return "/component/hzey/MfCusProspective_List";
	}
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageAjax")
	@ResponseBody
	public Map<String, Object> getListPageAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		//从数据字典中拿到缓存
		JSONArray jsonArrayByKeyName = new CodeUtils().getJSONArrayByKeyName("TRACK_TYPE");
		dataMap.put("jsonArrayByKeyName",jsonArrayByKeyName);
		dataMap.put("flag", "success");
		return dataMap;
	}
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, Ipage ipage) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusAndApply mfCusAndApply = new MfCusAndApply();
		try {
			mfCusAndApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusAndApply.setCustomSorts(ajaxData);//自定义排序参数赋值
			mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);//我的筛选
//			this.getRoleConditions(mfCusAndApply,"1000000065");//记录级权限控制方法
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
			ipage = mfCusProspectiveFeign.getCusAndApply(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
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
