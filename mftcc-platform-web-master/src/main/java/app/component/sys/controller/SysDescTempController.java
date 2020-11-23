package app.component.sys.controller;

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

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.sys.entity.SysDescTemp;
import app.component.sys.feign.SysDescTempFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/sysDescTemp")
public class SysDescTempController extends BaseFormBean{
	//页面传值
	//注入sysDescTempFeign
	@Autowired
	private SysDescTempFeign sysDescTempFeign;
	//异步参数
	private String query;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	private FormData formdesctemp0001;
	private FormService formService = new FormService();
	
	public SysDescTempController() {
		query = "";
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		JSONObject jb = JSONObject.fromObject(ajaxData);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysDescTemp sysDescTemp = new SysDescTemp();
		sysDescTemp = (SysDescTemp)JSONObject.toBean(jb, SysDescTemp.class);
		sysDescTempFeign.update(sysDescTemp);
		dataMap.put("temp", sysDescTemp);
		dataMap.put("flag", "success");
		dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
		getTableDataIpage(dataMap);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String desctempNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysDescTemp sysDescTemp = new SysDescTemp();
		sysDescTemp.setDesctempNo(desctempNo);
		try {
			sysDescTempFeign.del(sysDescTemp);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		JSONObject jb = JSONObject.fromObject(ajaxData);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysDescTemp sysDescTemp = new SysDescTemp();
		sysDescTemp = (SysDescTemp)JSONObject.toBean(jb, SysDescTemp.class);
		sysDescTempFeign.insert(sysDescTemp);
		dataMap.put("temp", sysDescTemp);
		dataMap.put("flag", "success");
		dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		getTableDataIpage(dataMap);
		return dataMap;
	}
	
	private void getTableDataIpage(Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		SysDescTemp sysDescTemp = new SysDescTemp();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("sysDescTemp",sysDescTemp));
		String  tableHtml = jtu.getJsonStr("tabledesctemp0001","tableTag", (List)sysDescTempFeign.findByPage(ipage).getResult(), ipage,true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage",ipage);
	}
	
	/***
	 * 列表数据查询的异步方法
	 */
	@RequestMapping(value = "/findByPageAllAjax")
	@ResponseBody
	public Map<String, Object> findByPageAllAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysDescTemp sysDescTemp = new SysDescTemp();
		try {
			sysDescTemp = new SysDescTemp();
			JSONObject jb = JSONObject.fromObject(ajaxData);
			sysDescTemp = (SysDescTemp)JSONObject.toBean(jb, SysDescTemp.class);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("sysDescTemp", sysDescTemp));
			ipage =  sysDescTempFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtmlf = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtmlf);
			dataMap.put("ipage", ipage);
			dataMap.put("list", JSONArray.fromObject((List)ipage.getResult()));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		formdesctemp0001 = formService.getFormData("desctemp0001");
		model.addAttribute("formdesctemp0001", formdesctemp0001);
		return "/component/sys/SysDescTemp_List";
	}
	@RequestMapping(value = "/updateSession")
	@ResponseBody
	public Map<String, Object> updateSession() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		request.getSession().removeAttribute("pageInfoMsg");
		return dataMap;
	}
	
	public FormData getFormdesctemp0001() {
		return formdesctemp0001;
	}
	public void setFormdesctemp0001(FormData formdesctemp0001) {
		this.formdesctemp0001 = formdesctemp0001;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
}
