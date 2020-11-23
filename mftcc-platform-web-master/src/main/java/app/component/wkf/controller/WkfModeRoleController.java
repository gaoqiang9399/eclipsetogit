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

import app.component.wkf.entity.WkfModeRole;
import app.component.wkf.feign.WkfModeRoleFeign;
import app.util.toolkit.Ipage;

/**
 * Title: WkfModeRoleAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Wed Jan 27 03:09:54 GMT 2016
 **/
@Controller
@RequestMapping(value = "/wkfModeRole")
public class WkfModeRoleController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入WkfModeRoleBo
	@Autowired
	private WkfModeRoleFeign wkfModeRoleFeign;
	
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
		ActionContext.initialize(request,
				response);
		return "/component/wkf/WkfModeRole_List";
	}
	@RequestMapping(value = "/getListPageForConf") 
	public String getListPageForConf(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		//wkfVpName = new String(wkfVpName.getBytes("ISO-8859-1"),"UTF-8");
		return "/component/wkf/WkfModeRole_ListForConf";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfModeRole wkfModeRole = new WkfModeRole();
		try {
			wkfModeRole.setCustomQuery(ajaxData);//自定义查询参数赋值
			wkfModeRole.setCustomSorts(ajaxData);
			wkfModeRole.setCriteriaList(wkfModeRole, ajaxData);//我的筛选
			//this.getRoleConditions(wkfModeRole,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = wkfModeRoleFeign.findByPage(ipage, wkfModeRole);
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
	@RequestMapping(value = "/findByVpNoGroupByModeNoAjax")
	public Map<String, Object> findByVpNoGroupByModeNoAjax(String wkfVpNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfModeRole wkfModeRole = new WkfModeRole();
		wkfModeRole.setWkfVpNo(wkfVpNo);
		try {
			List<WkfModeRole> wkfModeRoleList = wkfModeRoleFeign.findByVpNoGroupByModeNo(wkfModeRole);
			dataMap.put("list",wkfModeRoleList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@ResponseBody
	@RequestMapping(value = "/findByVpNoAndModeNoAjax")
	public Map<String, Object> findByVpNoAndModeNoAjax(String wkfVpNo,String wkfModeNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfModeRole wkfModeRole = new WkfModeRole();
		wkfModeRole.setWkfVpNo(wkfVpNo);
		wkfModeRole.setWkfModeNo(wkfModeNo);
		try {
			List<WkfModeRole> wkfModeRoleList = wkfModeRoleFeign.findByVpNoAndModeNo(wkfModeRole);
			dataMap.put("list",wkfModeRoleList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getTableData")
	private void getTableData(String tableId,List<WkfModeRole> list) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"thirdTableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	@ResponseBody
	@RequestMapping(value = "/inputAjax")
	public Map<String, Object> inputAjax(String query) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		WkfModeRole wkfModeRole = new WkfModeRole();	
		FormData formwkf4012 = formService.getFormData("wkf4012");
		getObjValue(formwkf4012,wkfModeRole);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formwkf4012,"bigFormTag",query);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			
			FormData formwkf4012 = formService.getFormData("wkf4012");
			getFormValue(formwkf4012, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf4012)){
				WkfModeRole wkfModeRole = new WkfModeRole();
				setObjValue(formwkf4012, wkfModeRole);
				wkfModeRoleFeign.insert(wkfModeRole);
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertModeAjax")
	public Map<String, Object> insertModeAjax(String wkfModeName,String wkfVpNo,String wkfModeNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
				if("".equals(wkfModeName)||wkfModeName==null){
					dataMap.put("flag", "error");
					dataMap.put("msg","模块描述不能为空！");
					return dataMap;
				}
				if("".equals(wkfVpNo)||wkfVpNo==null){
					dataMap.put("flag", "error");
					dataMap.put("msg","刷新重试！");
					return dataMap;
				}
				if("".equals(wkfModeNo)||wkfModeNo==null){
					dataMap.put("flag", "error");
					dataMap.put("msg","模块编号不能为空！");
					return dataMap;
				}
				WkfModeRole wkfModeRole = new WkfModeRole();
				wkfModeRole.setWkfModeName(wkfModeName);
				wkfModeRole.setWkfModeNo(wkfModeNo);
				wkfModeRole.setWkfVpNo(wkfVpNo);
				wkfModeRole.setWkfVpMenuNo("temp");
				wkfModeRoleFeign.insert(wkfModeRole);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	@ResponseBody
	@RequestMapping(value = "/insertRoleAjax")
	public Map<String, Object> insertRoleAjax(String wkfModeNo,String wkfVpNo,String wkfVpMenuNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
				WkfModeRole wkfModeRole = new WkfModeRole();
				wkfModeRole.setWkfModeNo(wkfModeNo);
				wkfModeRole.setWkfVpNo(wkfVpNo);
				wkfModeRole.setWkfVpMenuNo(wkfVpMenuNo);
				wkfModeRoleFeign.insertRole(wkfModeRole);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
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
		FormService formService = new FormService();
		WkfModeRole wkfModeRole = new WkfModeRole();	
		try{
			FormData formwkf4012 = formService.getFormData("wkf4012");
			getFormValue(formwkf4012, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf4012)){
				setObjValue(formwkf4012, wkfModeRole);
				//如果更改后的主键值没有改变
				if(wkfModeRole.getWkfVpMenuNo().equals(wkfModeRole.getOldWkfMenuNo()) && 
						wkfModeRole.getWkfModeNo().equals(wkfModeRole.getOldWkfModeNo()) && 
						wkfModeRole.getWkfVpNo().equals(wkfModeRole.getOldWkfVpNo())) {
					wkfModeRoleFeign.update(wkfModeRole);
				}else {
					//如果主键值改变，则将旧的记录删除，增加新的记录
					WkfModeRole oldWkfModeRole = new WkfModeRole();
					oldWkfModeRole.setWkfModeNo(wkfModeRole.getOldWkfModeNo()); 
					oldWkfModeRole.setWkfVpMenuNo(wkfModeRole.getOldWkfMenuNo());
					oldWkfModeRole.setWkfVpNo(wkfModeRole.getOldWkfVpNo());
					wkfModeRoleFeign.updateForChangeId(oldWkfModeRole, wkfModeRole);
				}

				List<WkfModeRole> list = new ArrayList<WkfModeRole>();
				list.add(wkfModeRole);
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
	public Map<String, Object> getByIdAjax(String wkfModeNo,String wkfVpNo,String wkfVpMenuNo,String query) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		
		FormData formwkf4012 = formService.getFormData("wkf4012");
		WkfModeRole wkfModeRole = new WkfModeRole();
		wkfModeRole.setWkfModeNo(wkfModeNo);
		wkfModeRole.setWkfVpNo(wkfVpNo);
		wkfModeRole.setWkfVpMenuNo(wkfVpMenuNo);
		wkfModeRole = wkfModeRoleFeign.getById(wkfModeRole);
		wkfModeRole.setOldWkfMenuNo(wkfVpMenuNo);
		wkfModeRole.setOldWkfModeNo(wkfModeNo);
		wkfModeRole.setOldWkfVpNo(wkfVpNo);
		getObjValue(formwkf4012, wkfModeRole);
		if(wkfModeRole!=null){
			JsonFormUtil jfu = new JsonFormUtil();
			String  formHtml = jfu.getJsonStr(formwkf4012,"bigFormTag",query);
			dataMap.put("formHtml",formHtml);
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String wkfModeNo,String wkfVpNo,String wkfVpMenuNo) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfModeRole wkfModeRole = new WkfModeRole();
		wkfModeRole.setWkfModeNo(wkfModeNo);
		wkfModeRole.setWkfVpNo(wkfVpNo);
		wkfModeRole.setWkfVpMenuNo(wkfVpMenuNo);
		try {
			wkfModeRoleFeign.delete(wkfModeRole);
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
	@ResponseBody
	@RequestMapping(value = "/deleteByModeNoAjax")
	public Map<String, Object> deleteByModeNoAjax(String wkfModeNo,String wkfVpNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		WkfModeRole wkfModeRole = new WkfModeRole();
		wkfModeRole.setWkfModeNo(wkfModeNo);
		wkfModeRole.setWkfVpNo(wkfVpNo);
		try {
			wkfModeRoleFeign.deleteByModeNo(wkfModeRole);
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
	
}
