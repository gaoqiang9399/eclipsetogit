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

import app.component.wkf.entity.WkfBusinessConfig;
import app.component.wkf.feign.WkfBusinessConfigFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
* Title: WkfBusinessConfigAction.java
* Description:
* @author:renyongxian@dhcc.com.cn
* @Thu Feb 28 12:59:54 GMT 2013
**/
@Controller
@RequestMapping(value = "/wkfBusinessConfig")
public class WkfBusinessConfigController extends BaseFormBean {


	//ע��WkfBusinessConfigBo
	@Autowired
	private WkfBusinessConfigFeign wkfBusinessConfigFeign;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage")
	 public String findByPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0010 = formService.getFormData("wkf0010");
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		getFormValue(formwkf0010);
		setObjValue(formwkf0010, wkfBusinessConfig);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfBusinessConfig", wkfBusinessConfig));
		List<WkfBusinessConfig> wkfBusinessConfigList = (List) wkfBusinessConfigFeign.findByPage(ipage).getResult();
		model.addAttribute("formwkf0010", formwkf0010);
		model.addAttribute("query", ""); 
		return "/component/wkf/WkfBusinessConfig_List";
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0011 = formService.getFormData("wkf0011");
		model.addAttribute("formwkf0011", formwkf0011);
		model.addAttribute("query", "");
		return "/component/wkf/WkfBusinessConfig_Insert";
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0011 = formService.getFormData("wkf0011");
		getFormValue(formwkf0011);
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		setObjValue(formwkf0011, wkfBusinessConfig);
		wkfBusinessConfigFeign.insert(wkfBusinessConfig);
		getObjValue(formwkf0011, wkfBusinessConfig);
		model.addAttribute("formwkf0011", formwkf0011);
		model.addAttribute("query", "");
		return "/component/wkf/WkfBusinessConfig_Detail";
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0011 = formService.getFormData("wkf0011");
		getFormValue(formwkf0011);
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		setObjValue(formwkf0011, wkfBusinessConfig);
		wkfBusinessConfigFeign.update(wkfBusinessConfig);
		getObjValue(formwkf0011, wkfBusinessConfig);
		model.addAttribute("formwkf0011", formwkf0011);
		model.addAttribute("query", "");
		return "/component/wkf/WkfBusinessConfig_Detail";
	}
	
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	public String del(Model model,String objAttributeName,String objName) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0010 = formService.getFormData("wkf0010");
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		wkfBusinessConfig.setObjAttributeName(objAttributeName);
		wkfBusinessConfig.setObjName(objName);
		wkfBusinessConfigFeign.del(wkfBusinessConfig);
		this.addActionMessage(model, "操作成功");
		wkfBusinessConfig = new WkfBusinessConfig();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfBusinessConfig", wkfBusinessConfig));
		List<WkfBusinessConfig> wkfBusinessConfigList = (List) wkfBusinessConfigFeign.findByPage(ipage).getResult();
		model.addAttribute("formwkf0010", formwkf0010);
		model.addAttribute("query", "");
		return "/component/wkf/WkfBusinessConfig_List";
	}

	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String objAttributeName,String objName) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0011 = formService.getFormData("wkf0011");
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		wkfBusinessConfig.setObjAttributeName(objAttributeName);
		wkfBusinessConfig.setObjName(objName);
		wkfBusinessConfig = wkfBusinessConfigFeign.getById(wkfBusinessConfig);
		getObjValue(formwkf0011, wkfBusinessConfig);
		model.addAttribute("formwkf0011", formwkf0011);
		model.addAttribute("query", "");
		return "/component/wkf/WkfBusinessConfig_Detail";
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model){
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formwkf0011 = formService.getFormData("wkf0011");
		 getFormValue(formwkf0011);
		 validateFormData(formwkf0011);
		 WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		 setObjValue(formwkf0011, wkfBusinessConfig);
		 if(null!=wkfBusinessConfigFeign.getById(wkfBusinessConfig)) {
             this.addActionError(model, "操作失败");
         }
		 model.addAttribute("formwkf0011", formwkf0011);
		 model.addAttribute("query", "");
   }
   
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model){
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formwkf0011 = formService.getFormData("wkf0011");
		 getFormValue(getFormwkf0011());
		 validateFormData(formwkf0011);
		 model.addAttribute("formwkf0011", formwkf0011);
		 model.addAttribute("query", "");
  	}
	private FormData getFormwkf0011() {
		// TODO Auto-generated method stub
		return null;
	}


	/**新系统begin**/
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model)  throws Exception {
		ActionContext.initialize(request,response);
		
		return "/component/wkf/WkfBusinessConfig_ListPage";
	}
	
	/***
	 * 标准例子
	 * 列表数据查询的异步方法
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		try {
			wkfBusinessConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			wkfBusinessConfig.setCriteriaList(wkfBusinessConfig, ajaxData);//我的筛选
			//this.getRoleConditions(appProject,"1000000001");//记录级权限控制方法
//			appProject.setRegNo(User.getRegName(this.getHttpRequest()));
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			
			ipage.setParams(this.setIpageParams("wkfBusinessConfig", wkfBusinessConfig));
			//自定义查询Bo方法
			ipage = wkfBusinessConfigFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formwkf0011 = formService.getFormData("wkf0011");
			getFormValue(formwkf0011, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf0011)){
				WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
				setObjValue(formwkf0011, wkfBusinessConfig);
				String attributeType = wkfBusinessConfig.getAttributeType();
				if(!attributeType.endsWith("|")){
					attributeType += "|";
				}
				wkfBusinessConfig.setAttributeType(attributeType);
				wkfBusinessConfigFeign.insert(wkfBusinessConfig);
				
				Ipage ipage = this.getIpage();
				ipage.setPageNo(pageNo);//异步传页面翻页参数
				//自定义查询Bo方法
				ipage.setParams(this.setIpageParams("wkfBusinessConfig", new WkfBusinessConfig()));
				ipage = wkfBusinessConfigFeign.findByPage(ipage );
				JsonTableUtil jtu = new JsonTableUtil();
				String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
				ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
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
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		FormService formService = new FormService();
		try{
			FormData formwkf0050 = formService.getFormData("wkf0050");
			getFormValue(formwkf0050, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf0050)){
				wkfBusinessConfig = new WkfBusinessConfig();
				setObjValue(formwkf0050, wkfBusinessConfig);
				String attributeType = wkfBusinessConfig.getAttributeType();
				if(!attributeType.endsWith("|")){
					attributeType += "|";
				}
				wkfBusinessConfig.setAttributeType(attributeType);
				wkfBusinessConfigFeign.update(wkfBusinessConfig);
				wkfBusinessConfig = wkfBusinessConfigFeign.getById(wkfBusinessConfig);
				List<WkfBusinessConfig> list = new ArrayList<WkfBusinessConfig>();
				list.add(wkfBusinessConfig);
				String objName = wkfBusinessConfig.getObjName();
				getTableData(null, objName, list);
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
	@ResponseBody
	@RequestMapping(value = "/inputAjax")
	public Map<String, Object> inputAjax(String query) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		FormData formwkf0011 = formService.getFormData("wkf0011");
		JsonFormUtil jfu = new JsonFormUtil();
		if (query==null) {
			query="";
		}
		String  formHtml = jfu.getJsonStr(formwkf0011,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		return dataMap;
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String objAttributeName,String objName,String query) throws Exception {
		ActionContext.initialize(request,
				response);
	    Map<String, Object> dataMap = new HashMap<String, Object>(); 
	    FormService formService = new FormService();
		FormData formwkf0050 = formService.getFormData("wkf0050");
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		wkfBusinessConfig.setObjAttributeName(objAttributeName);
		wkfBusinessConfig.setObjName(objName);
		if(query==null){
			query="";
		}
		wkfBusinessConfig = wkfBusinessConfigFeign.getById(wkfBusinessConfig);
		getObjValue(formwkf0050,wkfBusinessConfig);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formwkf0050,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String objAttributeName,String objName) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
		wkfBusinessConfig.setObjAttributeName(objAttributeName);
		wkfBusinessConfig.setObjName(objName);
		try {
			wkfBusinessConfigFeign.del(wkfBusinessConfig);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("wkfBusinessConfig"));
			List<WkfBusinessConfig> wkfBusinessConfigList = (List) wkfBusinessConfigFeign.findByPage(ipage).getResult();
			getTableData(null, objName, wkfBusinessConfigList);//获取列表
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

	@RequestMapping(value = "/getTableData1")
	private void getTableData(Model model,String tableId,List<WkfBusinessConfig> list) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTableData")
	private void getTableData(Model model,int pageNo,String tableId,WkfBusinessConfig wkfBusinessConfig) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Ipage ipage = this.getIpage();
		ipage.setPageNo(pageNo);//异步传页面翻页参数
		//自定义查询Bo方法
		ipage.setParams(this.setIpageParams("wkfBusinessConfig", wkfBusinessConfig));
		ipage = wkfBusinessConfigFeign.findByPage(ipage);
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List)ipage.getResult(), ipage,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 更新启用状态
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStsAjax")
	public Map<String, Object> updateStsAjax(String ajaxData)throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			WkfBusinessConfig wkfBusinessConfig = new WkfBusinessConfig();
			JSONObject jb=JSONObject.fromObject(ajaxData);
			wkfBusinessConfig=(WkfBusinessConfig)JSONObject.toBean(jb,WkfBusinessConfig.class);
			wkfBusinessConfigFeign.updateSts(wkfBusinessConfig);
			dataMap.put("flag", "true");
			dataMap.put("msg", "更新成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "false");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	/**新系统end**/

	
}