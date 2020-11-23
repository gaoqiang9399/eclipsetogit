package  app.component.sys.controller;
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

import app.component.common.EntityUtil;
import app.component.sys.entity.SysBtnDef;
import app.component.sys.entity.SysBtnUrl;
import app.component.sys.entity.SysRoleUrl;
import app.component.sys.feign.SysBtnDefFeign;
import app.component.sys.feign.SysBtnUrlFeign;
import app.component.sys.feign.SysRoleUrlFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: SysBtnDefAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Mon Aug 22 07:31:44 GMT 2016
 **/
@Controller
@RequestMapping("/sysBtnDef")
public class SysBtnDefController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入sysBtnDefFeign
	@Autowired  
	private SysBtnDefFeign sysBtnDefFeign;
	@Autowired  
	private SysBtnUrlFeign sysBtnUrlFeign;
	@Autowired  
	private SysRoleUrlFeign sysRoleUrlFeign;
	
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//全局变量
	private String query;
	
	//表单变量
	private FormData formsys7001;
	private FormData formsys7002;
	private FormService formService = new FormService();
	
	public SysBtnDefController(){
		query = "";
	}
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/SysBtnDef_List.";
	}
	@RequestMapping(value = "/getListTree")
	public String getListTree() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/SysBtnDef_Tree";
	}
	@RequestMapping(value = "/getListTreeRole")
	public String getListTreeRole() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/SysBtnDef_TreeRole";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysBtnDef sysBtnDef = new SysBtnDef();
		try {
			sysBtnDef.setCustomQuery(ajaxData);//自定义查询参数赋值
			sysBtnDef.setCriteriaList(sysBtnDef, ajaxData);//我的筛选
			//this.getRoleConditions(sysBtnDef,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysBtnDef", sysBtnDef));
			ipage = sysBtnDefFeign.findByPage(ipage);
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
	 * 按级别和父级编号查询
	 * @return	JSONArray
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByLvAjax")
	@ResponseBody
	public Map<String, Object> findByLvAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysBtnDef sysBtnDef = new SysBtnDef();
		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			sysBtnDef = (SysBtnDef) JSONObject.toBean(jsonObject, SysBtnDef.class);
			JSONArray jsonArray = sysBtnDefFeign.findByLv(sysBtnDef);
			dataMap.put("flag", "success");
			dataMap.put("json", jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 按级别和父级编号查询
	 * @return	JSONArray
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByLvForRoleNoAjax")
	@ResponseBody
	public Map<String, Object> findByLvForRoleNoAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysBtnDef sysBtnDef = new SysBtnDef();
		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			sysBtnDef = (SysBtnDef) JSONObject.toBean(jsonObject, SysBtnDef.class);
			JSONArray jsonArray = sysBtnDefFeign.findByLvForRoleNo(sysBtnDef);
			dataMap.put("flag", "success");
			dataMap.put("json", jsonArray);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			SysBtnDef sysBtnDef = new SysBtnDef();
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			sysBtnDef = (SysBtnDef) JSONObject.toBean(jsonObject, SysBtnDef.class);
			SysBtnDef sbd = new SysBtnDef();
			String msg = "";
			boolean EmptyFlag= false;
			if("1".equals(sysBtnDef.getLv())){
				if(sysBtnDef.getComponentName()==null||"".equals(sysBtnDef.getComponentName())){
					EmptyFlag = true;
					msg ="新增失败，组件名称不能为空！";
				}else{
					sbd.setComponentName(sysBtnDef.getComponentName());
					sbd = sysBtnDefFeign.getByComponentName(sbd);
					msg ="新增失败，组件名称已存在！";
				}
			}else if("2".equals(sysBtnDef.getLv())){
				if(sysBtnDef.getFunNo()==null||"".equals(sysBtnDef.getFunNo())){
					EmptyFlag = true;
					msg ="新增失败，功能编号不能为空！";
				}else{
					sbd.setFunNo(sysBtnDef.getFunNo());
					sbd.setUpId(sysBtnDef.getUpId());
					sbd = sysBtnDefFeign.getByFunNo(sbd);
					msg ="新增失败，功能编号已存在！";
				}
			}else if("3".equals(sysBtnDef.getLv())){
				if(sysBtnDef.getBtnNo()==null||"".equals(sysBtnDef.getBtnNo())){
					EmptyFlag = true;
					msg ="新增失败，按钮编号不能为空！";
				}else{
					sbd.setBtnNo(sysBtnDef.getBtnNo());
					sbd.setUpId(sysBtnDef.getUpId());
					sbd = sysBtnDefFeign.getByBtnNo(sbd);
					msg ="新增失败，按钮编号已存在！";
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败，lv值为空或未知！");
			}
			if(sbd==null&&!EmptyFlag){
				sysBtnDefFeign.insert(sysBtnDef);
				dataMap.put("node", sysBtnDef);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", msg);
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formsys7002 = formService.getFormData("sys7002");
		getFormValue(formsys7002, getMapByJson(ajaxData));
		SysBtnDef sysBtnDefJsp = new SysBtnDef();
		setObjValue(formsys7002, sysBtnDefJsp);
		SysBtnDef sysBtnDef = sysBtnDefFeign.getById(sysBtnDefJsp);
		if(sysBtnDef!=null){
			try{
				sysBtnDef = (SysBtnDef)EntityUtil.reflectionSetVal(sysBtnDef, sysBtnDefJsp, getMapByJson(ajaxData));
				sysBtnDefFeign.update(sysBtnDef);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysBtnDef sysBtnDef = new SysBtnDef();
		try{
				sysBtnDef = new SysBtnDef();
				JSONObject jsonObject = JSONObject.fromObject(ajaxData);
				sysBtnDef = (SysBtnDef) JSONObject.toBean(jsonObject, SysBtnDef.class);
				SysBtnDef sbd = new SysBtnDef();
				boolean EmptyFlag= false;
				String msg = "";
				if("1".equals(sysBtnDef.getLv())){
					if(sysBtnDef.getComponentName()==null||"".equals(sysBtnDef.getComponentName())){
						EmptyFlag = true;
						msg ="更新失败，组件名称不能为空！";
					}else{
						sbd.setComponentName(sysBtnDef.getComponentName());
						sbd = sysBtnDefFeign.getByComponentName(sbd);
						msg ="更新失败，组件名称已存在！";
					}
				}else if("2".equals(sysBtnDef.getLv())){
					if(sysBtnDef.getFunNo()==null||"".equals(sysBtnDef.getFunNo())){
						EmptyFlag = true;
						msg ="更新失败，功能编号不能为空！";
					}else{
						sbd.setFunNo(sysBtnDef.getFunNo());
						sbd.setUpId(sysBtnDef.getUpId());
						sbd = sysBtnDefFeign.getByFunNo(sbd);
						msg ="更新失败，功能编号已存在！";
					}
				}else if("3".equals(sysBtnDef.getLv())){
					if(sysBtnDef.getBtnNo()==null||"".equals(sysBtnDef.getBtnNo())){
						EmptyFlag = true;
						msg ="更新失败，按钮编号不能为空！";
					}else{
						sbd.setBtnNo(sysBtnDef.getBtnNo());
						sbd.setUpId(sysBtnDef.getUpId());
						sbd = sysBtnDefFeign.getByBtnNo(sbd);
						msg ="更新失败，按钮编号已存在！";
					}
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", "新增失败，lv值为空或未知！");
				}
				if(!EmptyFlag&&(sbd==null||sbd.getId().equals(sysBtnDef.getId()))){
					sysBtnDefFeign.update(sysBtnDef);
					dataMap.put("node", sysBtnDef);
					dataMap.put("flag", "success");
					dataMap.put("msg", "更新成功");
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", msg);
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
	 * AJAX获取url sys_btn_url
	 * 
	 */
	@RequestMapping(value = "/getUrlAjax")
	@ResponseBody
	public Map<String, Object> getUrlAjax(String id) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysBtnUrl sysBtnUrl = new SysBtnUrl();
		sysBtnUrl.setBtnId(id);
		List<SysBtnUrl> sysBtnUrlList = sysBtnUrlFeign.getById(sysBtnUrl);
		if(sysBtnUrlList.size() > 0){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", sysBtnUrlList);
		return dataMap;
	}
	
	
	/**
	 * AJAX更新sys_btn_url
	 * 
	 */
	@RequestMapping(value = "/updateUrlAjax")
	@ResponseBody
	public Map<String, Object> updateUrlAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysBtnUrl sysBtnUrl = new SysBtnUrl();
		try{
		  	sysBtnUrl = new SysBtnUrl();
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			sysBtnUrl = (SysBtnUrl) JSONObject.toBean(jsonObject, SysBtnUrl.class);
			String msg = "";
			sysBtnUrlFeign.update(sysBtnUrl);
			dataMap.put("node", sysBtnUrl);
		    dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
			
	  }catch(Exception e){
	 	e.printStackTrace();
		dataMap.put("flag", "error");
		dataMap.put("msg", "更新失败");
		throw e;
	  }
		return dataMap;
	}
	/**
	 * AJAX插入  sys_btn_url
	 * 
	 */
	@RequestMapping(value = "/insertUrlAjax")
	@ResponseBody
	public Map<String, Object> insertUrlAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			SysBtnUrl sysBtnUrl = new SysBtnUrl();
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			sysBtnUrl = (SysBtnUrl) JSONObject.toBean(jsonObject, SysBtnUrl.class);
			sysBtnUrlFeign.insert(sysBtnUrl);
			dataMap.put("node", sysBtnUrl);
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
	/**
	 * AJAX删除sys_btn_url
	 * 
	 */
	@RequestMapping(value = "/delUrlAjax")
	@ResponseBody
	public Map<String, Object> delUrlAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			SysBtnUrl sysBtnUrl = new SysBtnUrl();
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			sysBtnUrl = (SysBtnUrl) JSONObject.toBean(jsonObject, SysBtnUrl.class);
			sysBtnUrlFeign.delete(sysBtnUrl);
			dataMap.put("flag", "success");
			dataMap.put("msg", "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
			
		}

		return dataMap;
	}
	
	/**
	 * AJAX 查询role_no & btn_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRoleUrlAjax")
	@ResponseBody
	public Map<String, Object> getRoleUrlAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysRoleUrl sysRoleUrl = new SysRoleUrl();
		JSONObject jsonObject = JSONObject.fromObject(ajaxData);
		sysRoleUrl = (SysRoleUrl) JSONObject.toBean(jsonObject, SysRoleUrl.class);
		List<SysRoleUrl> sysRoleUrlList = sysRoleUrlFeign.getById(sysRoleUrl);
		if(sysRoleUrlList.size() > 0){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", sysRoleUrlList);
		return dataMap;
	}
	
	/**
	 * AJAX 删除sys_role_url
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delRoleUrlAjax")
	@ResponseBody
	public Map<String, Object> delRoleUrlAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			SysRoleUrl sysRoleUrl = new SysRoleUrl();
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			sysRoleUrl = (SysRoleUrl) JSONObject.toBean(jsonObject, SysRoleUrl.class);
			sysRoleUrlFeign.delete(sysRoleUrl);
			dataMap.put("flag", "success");
			dataMap.put("msg", "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
			
		}

		return dataMap;
	}
	
	
	/**
	 * AJAX 保存sys_role_url
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertRoleUrl")
	@ResponseBody
	public Map<String, Object> insertRoleUrl(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			SysRoleUrl sysRoleUrl = new SysRoleUrl();
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			sysRoleUrl = (SysRoleUrl) JSONObject.toBean(jsonObject, SysRoleUrl.class);
			sysRoleUrlFeign.insert(sysRoleUrl);
			dataMap.put("node", sysRoleUrl);
			dataMap.put("flag", "success");
			dataMap.put("msg", "保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
			
		}

		return dataMap;
	}
	
	
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formsys7002 = formService.getFormData("sys7002");
		SysBtnDef sysBtnDef = new SysBtnDef();
		sysBtnDef.setId(id);
		sysBtnDef = sysBtnDefFeign.getById(sysBtnDef);
		getObjValue(formsys7002, sysBtnDef,formData);
		if(sysBtnDef!=null){
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id,String lv) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysBtnDef sysBtnDef = new SysBtnDef();
		sysBtnDef.setId(id);
		sysBtnDef.setLv(lv);
		try {
			sysBtnDefFeign.delete(sysBtnDef);
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
		formsys7002 = formService.getFormData("sys7002");
		model.addAttribute("formsys7002", formsys7002);
		return "/component/sys/SysBtnDef_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys7002 = formService.getFormData("sys7002");
		 getFormValue(formsys7002);
		 SysBtnDef sysBtnDef = new SysBtnDef();
		 setObjValue(formsys7002, sysBtnDef);
		 sysBtnDefFeign.insert(sysBtnDef);
		 getObjValue(formsys7002, sysBtnDef);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("sysBtnDef", sysBtnDef));
		 List<SysBtnDef> sysBtnDefList = (List<SysBtnDef>)sysBtnDefFeign.findByPage(ipage).getResult();
		 model.addAttribute("formsys7002", formsys7002);
		 model.addAttribute("sysBtnDefList", sysBtnDefList);
		 return "/component/sys/SysBtnDef_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request,response);
		 formsys7001 = formService.getFormData("sys7001");
		 getFormValue(formsys7001);
		 SysBtnDef sysBtnDef = new SysBtnDef();
		sysBtnDef.setId(id);
		 sysBtnDef = sysBtnDefFeign.getById(sysBtnDef);
		 getObjValue(formsys7001, sysBtnDef);
		 model.addAttribute("formsys7001", formsys7001);
		return "/component/sys/SysBtnDef_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String id, String lv) throws Exception {
		ActionContext.initialize(request,response);
		SysBtnDef sysBtnDef = new SysBtnDef();
		sysBtnDef.setId(id);
		sysBtnDef.setLv(lv);
		sysBtnDefFeign.delete(sysBtnDef);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys7002 = formService.getFormData("sys7002");
		 getFormValue(formsys7002);
		 boolean validateFlag = this.validateFormData(formsys7002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys7002 = formService.getFormData("sys7002");
		 getFormValue(formsys7002);
		 boolean validateFlag = this.validateFormData(formsys7002);
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public FormData getFormsys7002() {
		return formsys7002;
	}
	public void setFormsys7002(FormData formsys7002) {
		this.formsys7002 = formsys7002;
	}
	public FormData getFormsys7001() {
		return formsys7001;
	}
	public void setFormsys7001(FormData formsys7001) {
		this.formsys7001 = formsys7001;
	}
	

	


	
	
}
