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
import app.component.wkf.entity.WkfType;
import app.component.wkf.feign.WkfTypeFeign;
import app.util.toolkit.Ipage;

/**
 * Title: WkfTypeAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Fri Jul 01 06:17:10 GMT 2016
 **/
@Controller
@RequestMapping(value = "/wkfType")
public class WkfTypeController extends BaseFormBean{
	//注入WkfTypeBo
	@Autowired
	private WkfTypeFeign wkfTypeFeign;
	
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
		return "/component/wkf/WkfType_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfType wkfType = new WkfType();
		try {
			wkfType.setCustomQuery(ajaxData);//自定义查询参数赋值
			wkfType.setCriteriaList(wkfType, ajaxData);//我的筛选
			//this.getRoleConditions(wkfType,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = wkfTypeFeign.findByPage(ipage, wkfType);
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
	@RequestMapping(value = "/inputAjax")
	public Map<String, Object> inputAjax(String query) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		JsonFormUtil jfu = new JsonFormUtil();
		FormService formService = new FormService();
		FormData formwkf5002 = formService.getFormData("wkf5002");
		String  formHtml = jfu.getJsonStr(formwkf5002,"bigFormTag",query);
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
	public Map<String, Object> insertAjax(String ajaxData,String query)throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formwkf5002 = formService.getFormData("wkf5002");
			getFormValue(formwkf5002, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf5002)){
				WkfType wkfType = new WkfType();
				setObjValue(formwkf5002, wkfType);
				
				WkfType wt = new WkfType();
				wt = wkfTypeFeign.getById(wkfType);
				if(wt!=null){
					dataMap.put("flag", "error");
					dataMap.put("msg", "流程视角编号已存在!");
				}else{
					wkfTypeFeign.insert(wkfType);
					JsonFormUtil jfu = new JsonFormUtil();
					String  formHtml = jfu.getJsonStr(formwkf5002,"bigFormTag",query);
					dataMap.put("formHtml",formHtml);
					dataMap.put("flag", "success");
					dataMap.put("msg", "新增成功");
				}
				
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/addAjax")
	public Map<String, Object> addAjax(String wkfVpNo,String wkfVpName) throws Exception {
		ActionContext.initialize(request,response);
	    Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{	
				WkfType wt = new WkfType();
				wt.setWkfVpNo(wkfVpNo);
				wt.setWkfVpName(wkfVpName);
				wt = wkfTypeFeign.getById(wt);
				if("".equals(wkfVpNo)){
					dataMap.put("flag", "error");
					dataMap.put("msg", "编号不能为空！");
				}else if("".equals(wkfVpName)){
					dataMap.put("flag", "error");
					dataMap.put("msg", "名称不能为空");
				}else if(wt != null){
					dataMap.put("flag", "error");
					dataMap.put("msg", "编号已存在！");
				}else{
					WkfType wkfType = new WkfType();
					wkfType.setWkfVpName(wkfVpName);
					wkfType.setWkfVpNo(wkfVpNo);
					wkfTypeFeign.insert(wkfType);
					dataMap.put("flag", "success");
					dataMap.put("msg", "新增成功");
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
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formwkf5002 = formService.getFormData("wkf5002");
		getFormValue(formwkf5002, getMapByJson(ajaxData));
		WkfType wkfTypeJsp = new WkfType();
		setObjValue(formwkf5002, wkfTypeJsp);
		WkfType wkfType = wkfTypeFeign.getById(wkfTypeJsp);
		if(wkfType!=null){
			try{
				wkfType = (WkfType)EntityUtil.reflectionSetVal(wkfType, wkfTypeJsp, getMapByJson(ajaxData));
				wkfTypeFeign.update(wkfType);
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
	@ResponseBody
	@RequestMapping(value = "/getTableData")
	private void getTableData(String tableId,List<WkfType> list) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
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
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		WkfType wkfType = new WkfType();
		try{
			FormData formwkf5002 = formService.getFormData("wkf5002");
			getFormValue(formwkf5002, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf5002)){
				wkfType = new WkfType();
				setObjValue(formwkf5002, wkfType);
				wkfTypeFeign.update(wkfType);
				wkfType=wkfTypeFeign.getById(wkfType);
				List<WkfType> list = new ArrayList<WkfType>();
				list.add(wkfType);
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
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String wkfVpNo,String query) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formwkf5002 = formService.getFormData("wkf5002");
		WkfType wkfType = new WkfType();
		wkfType.setWkfVpNo(wkfVpNo);
		wkfType = wkfTypeFeign.getById(wkfType);
		getObjValue(formwkf5002, wkfType,formData);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formwkf5002,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		if(wkfType!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllAjax")
	public Map<String, Object> getAllAjax() throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		dataMap.put("flag", "success");
		dataMap.put("nodes", wkfTypeFeign.getAll());
		return dataMap;
	}
	/**
	 * 自动下拉
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getSelectAjax")
	public Map<String, Object> getSelectAjax(String ajaxData,String query) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfType wkfType = new WkfType();
		String parm = "";
		try {
			EntityUtil entityUtil = new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(wkfType,ajaxData,query,parm,null));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String wkfVpNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfType wkfType = new WkfType();
		wkfType.setWkfVpNo(wkfVpNo);
		try {
			wkfTypeFeign.delete(wkfType);
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
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf5002 = formService.getFormData("wkf5002");
		model.addAttribute("formwkf5002", formwkf5002);
		model.addAttribute("query", "");
		return "/component/wkf/WkfType_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formwkf5002 = formService.getFormData("wkf5002");
		 getFormValue(formwkf5002);
		 WkfType wkfType = new WkfType();
		 setObjValue(formwkf5002, wkfType);
		 wkfTypeFeign.insert(wkfType);
		 getObjValue(formwkf5002, wkfType);
		 this.addActionMessage(model, "保存成功");
		 List<WkfType> wkfTypeList = (List<WkfType>)wkfTypeFeign.findByPage(this.getIpage(), wkfType).getResult();
		 model.addAttribute("formwkf5002", formwkf5002);
		 model.addAttribute("wkfTypeList", wkfTypeList);
	     model.addAttribute("query", "");
		return "/component/wkf/WkfType_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String wkfVpNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formwkf5001 = formService.getFormData("wkf5001");
		 getFormValue(formwkf5001);
		 WkfType wkfType = new WkfType();
		wkfType.setWkfVpNo(wkfVpNo);
		 wkfType = wkfTypeFeign.getById(wkfType);
		 getObjValue(formwkf5001, wkfType);
		 model.addAttribute("formwkf5001", formwkf5001);
		 model.addAttribute("wkfType", wkfType);
	     model.addAttribute("query", "");
		return "/component/wkf/WkfType_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String wkfVpNo) throws Exception {
		ActionContext.initialize(request,response);
		WkfType wkfType = new WkfType();
		wkfType.setWkfVpNo(wkfVpNo);
		wkfTypeFeign.delete(wkfType);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formwkf5002 = formService.getFormData("wkf5002");
		 getFormValue(formwkf5002);
		 boolean validateFlag = this.validateFormData(formwkf5002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formwkf5002 = formService.getFormData("wkf5002");
		 getFormValue(formwkf5002);
		 boolean validateFlag = this.validateFormData(formwkf5002);
	}
}
