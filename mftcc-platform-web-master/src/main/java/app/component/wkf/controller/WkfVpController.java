package  app.component.wkf.controller;
import java.util.ArrayList;
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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.wkf.entity.WkfVp;
import app.component.wkf.feign.WkfVpFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: WkfVpAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Wed Jan 27 03:07:15 GMT 2016
 **/
@Controller
@RequestMapping(value = "/wkfVp")
public class WkfVpController extends BaseFormBean{
	//注入WkfVpBo
	@Autowired
	private WkfVpFeign wkfVpFeign;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		//wkfVpName = new String(wkfVpName.getBytes("ISO-8859-1"),"UTF-8");
		return "/component/wkf/WkfVp_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,String wkfVpNo,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfVp wkfVp = new WkfVp();
		try {
			wkfVp.setCustomQuery(ajaxData);//自定义查询参数赋值
			wkfVp.setCriteriaList(wkfVp, ajaxData);//我的筛选
			wkfVp.setWkfVpNo(wkfVpNo);
			//this.getRoleConditions(wkfVp,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = wkfVpFeign.findByPage(ipage, wkfVp);
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
	
	@ResponseBody
	@RequestMapping(value = "/findByRoleAjax")
	public Map<String, Object> findByRoleAjax(String wkfVpNo,String wkfModeNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfVp wkfVp = new WkfVp();
		wkfVp.setWkfVpNo(wkfVpNo);
		wkfVp.setWkfModeNo(wkfModeNo);
		try {
			List<WkfVp> wkfVpList = wkfVpFeign.findByRole(wkfVp);
			dataMap.put("list",wkfVpList);
			dataMap.put("flag","success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@ResponseBody
	@RequestMapping(value = "/inputAjax")
	public Map<String, Object> inputAjax(String wkfVpNo,String wkfVpName,String query) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WkfVp wkfVp = new WkfVp();
		wkfVp.setWkfVpNo(wkfVpNo);
		wkfVp.setUrlType("1");
		wkfVp.setUpWkfVpMenuNo(wkfVpNo);
		wkfVp.setWkfVpName(wkfVpName);
		JsonFormUtil jfu = new JsonFormUtil();
		FormData formwkf4002 = formService.getFormData("wkf4002");
		getObjValue(formwkf4002,wkfVp);
		String  formHtml = jfu.getJsonStr(formwkf4002,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData,String query) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formwkf4002 = formService.getFormData("wkf4002");
			getFormValue(formwkf4002, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf4002)){
				WkfVp wkfVp = new WkfVp();
				setObjValue(formwkf4002, wkfVp);
				wkfVpFeign.insert(wkfVp);
				JsonFormUtil jfu = new JsonFormUtil();
				String  formHtml = jfu.getJsonStr(formwkf4002,"bigFormTag",query);
				dataMap.put("wkfVpNo",wkfVp.getWkfVpNo());
				dataMap.put("formHtml",formHtml);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formwkf4002 = formService.getFormData("wkf4002");
		getFormValue(formwkf4002, getMapByJson(ajaxData));
		WkfVp wkfVpJsp = new WkfVp();
		JSONObject jb = JSONObject.fromObject(ajaxData);
		setObjValue(formwkf4002, wkfVpJsp);
		WkfVp wkfVp = wkfVpFeign.getById(wkfVpJsp);
		if(wkfVp!=null){
			try{
				wkfVp = (WkfVp)EntityUtil.reflectionSetVal(wkfVp, wkfVpJsp,jb);
				wkfVpFeign.update(wkfVp);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfVp wkfVp = new WkfVp();
		try{
			FormData formwkf4003 = formService.getFormData("wkf4003");
			getFormValue(formwkf4003, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf4003)){
				wkfVp = new WkfVp();
				setObjValue(formwkf4003, wkfVp);
				wkfVpFeign.update(wkfVp);
				wkfVp=wkfVpFeign.getById(wkfVp);
				List<WkfVp> list = new ArrayList<WkfVp>();
				list.add(wkfVp);
				getTableData(ajaxData, list);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getTableData")
	private void getTableData(String tableId,List<WkfVp> list) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"thirdTableTag", list, null,false);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String wkfVpNo,String wkfVpMenuNo,String query) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formwkf4003 = formService.getFormData("wkf4003");
		WkfVp wkfVp = new WkfVp();
		wkfVp.setWkfVpNo(wkfVpNo);
		wkfVp.setWkfVpMenuNo(wkfVpMenuNo);
		wkfVp = wkfVpFeign.getById(wkfVp);
		getObjValue(formwkf4003, wkfVp,formData);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formwkf4003,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		if(wkfVp!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * AJAX获取LIST
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByVpNoAjax")
	public Map<String, Object> getByVpNoAjax(String wkfVpNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		dataMap.put("flag", "success");
		dataMap.put("nodes", wkfVpFeign.getByVpNo(wkfVpNo));
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String wkfVpNo,String wkfVpMenuNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfVp wkfVp = new WkfVp();
		wkfVp.setWkfVpNo(wkfVpNo);
		wkfVp.setWkfVpMenuNo(wkfVpMenuNo);
		try {
			wkfVpFeign.delete(wkfVp);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 自动下拉
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllAjax")
	public Map<String, Object> getAllAjax(String wkfVpNo,String query,String wkfVpMenuNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
	    wkfVpMenuNo = new String(wkfVpMenuNo.getBytes("ISO-8859-1"),"gbk");
		WkfVp wkfVp = new WkfVp();
		if(wkfVpNo==null||"".equals(wkfVpNo)){
			wkfVpNo = "null";
		}
		String wkfVpMenuName = wkfVpMenuNo;
		if(wkfVpMenuName==null||"".equals(wkfVpMenuName)){
			wkfVpMenuName = "null";
		}
		String parm = "wkfVpNo="+wkfVpNo;
		String ajaxData = "wkfVpMenuName";
		try {
			EntityUtil entityUtil = new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(wkfVp,ajaxData,query,parm,null));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	} 
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf4002 = formService.getFormData("wkf4002");
		model.addAttribute("formwkf4002", formwkf4002);
		model.addAttribute("query", "");
		return "/component/wkf/WkfVp_Insert"; 
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf4002 = formService.getFormData("wkf4002");
		getFormValue(formwkf4002);
		WkfVp wkfVp = new WkfVp();
		setObjValue(formwkf4002, wkfVp);
		wkfVpFeign.insert(wkfVp);
		getObjValue(formwkf4002, wkfVp);
	    this.addActionMessage(model, "保存成功");
		List<WkfVp> wkfVpList = (List<WkfVp>)wkfVpFeign.findByPage(this.getIpage(), wkfVp).getResult();
		model.addAttribute("formwkf4002", formwkf4002);
		model.addAttribute("wkfVpList", wkfVpList);
		model.addAttribute("query", "");
		return "/component/wkf/WkfVp_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getById")
	public String getById(Model model,String wkfVpNo,String wkfVpMenuNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf4002 = formService.getFormData("wkf4002");
		getFormValue(formwkf4002);
		WkfVp wkfVp = new WkfVp();
		wkfVp.setWkfVpNo(wkfVpNo);
		wkfVp.setWkfVpMenuNo(wkfVpMenuNo);
		wkfVp = wkfVpFeign.getById(wkfVp);
		getObjValue(formwkf4002, wkfVp);
		return "/component/wkf/WkfVp_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete")
	public String delete(Model model,String wkfVpNo,String wkfVpMenuNo) throws Exception {
		ActionContext.initialize(request,response);
		WkfVp wkfVp = new WkfVp();
		wkfVp.setWkfVpNo(wkfVpNo);
		wkfVp.setWkfVpMenuNo(wkfVpMenuNo);
		wkfVpFeign.delete(wkfVp);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formwkf4002 = formService.getFormData("wkf4002");
		 getFormValue(formwkf4002);
		 boolean validateFlag = this.validateFormData(formwkf4002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formwkf4002 = formService.getFormData("wkf4002");
		 getFormValue(formwkf4002);
		 boolean validateFlag = this.validateFormData(formwkf4002);
	}
	
}
