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

import app.base.cache.feign.BusiCacheFeign;
import app.component.common.EntityUtil;
import app.component.sys.entity.SysRoleButton;
import app.component.sys.feign.SysRoleButtonFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: SysRoleButtonAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue Aug 23 08:28:20 GMT 2016
 **/
@Controller
@RequestMapping("/sysRoleButton")
public class SysRoleButtonController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private SysRoleButtonFeign sysRoleButtonFeign;
	@Autowired
	private BusiCacheFeign busiCacheFeign;
	//全局变量
	private String query;

	//表单变量
	private FormData formsys4001;
	private FormData formsys4002;
	private FormService formService = new FormService();
	
	public SysRoleButtonController(){
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
		return "/component/sys/SysRoleButton_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysRoleButton sysRoleButton = new SysRoleButton();
		try {
			sysRoleButton.setCustomQuery(ajaxData);//自定义查询参数赋值
			sysRoleButton.setCriteriaList(sysRoleButton, ajaxData);//我的筛选
			//this.getRoleConditions(sysRoleButton,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysRoleButton", sysRoleButton));
			ipage = sysRoleButtonFeign.findByPage(ipage);
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
	@RequestMapping(value = "/refurbishBtnCacheAjax")
	@ResponseBody
	public Map<String, Object> refurbishBtnCacheAjax() throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			CodeUtils cu = new CodeUtils();
			busiCacheFeign.refreshSysRoleButtonCache();
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_OPERATION.getMessage("刷新按钮缓存"));
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
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
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			formsys4002 = formService.getFormData("sys4002");
			getFormValue(formsys4002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys4002)){
				SysRoleButton sysRoleButton = new SysRoleButton();
				setObjValue(formsys4002, sysRoleButton);
				sysRoleButtonFeign.insert(sysRoleButton);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/insertByJSONArrayAjax")
	@ResponseBody
	public Map<String, Object> insertByJSONArrayAjax(String ajaxData,String funNo,String roleNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{	
			JSONArray jsonArray = JSONArray.fromObject(ajaxData);
			sysRoleButtonFeign.insertByJSONArray(jsonArray, funNo,roleNo);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
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
		Map<String,Object> dataMap = new HashMap<String, Object>();
		formsys4002 = formService.getFormData("sys4002");
		getFormValue(formsys4002, getMapByJson(ajaxData));
		SysRoleButton sysRoleButtonJsp = new SysRoleButton();
		setObjValue(formsys4002, sysRoleButtonJsp);
		SysRoleButton sysRoleButton = sysRoleButtonFeign.getById(sysRoleButtonJsp);
		if(sysRoleButton!=null){
			try{
				sysRoleButton = (SysRoleButton)EntityUtil.reflectionSetVal(sysRoleButton, sysRoleButtonJsp, getMapByJson(ajaxData));
				sysRoleButtonFeign.update(sysRoleButton);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysRoleButton sysRoleButton = new SysRoleButton();
		try{
			formsys4002 = formService.getFormData("sys4002");
			getFormValue(formsys4002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys4002)){
				sysRoleButton = new SysRoleButton();
				setObjValue(formsys4002, sysRoleButton);
				sysRoleButtonFeign.update(sysRoleButton);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formsys4002 = formService.getFormData("sys4002");
		SysRoleButton sysRoleButton = new SysRoleButton();
		sysRoleButton.setId(id);
		sysRoleButton = sysRoleButtonFeign.getById(sysRoleButton);
		getObjValue(formsys4002, sysRoleButton,formData);
		if(sysRoleButton!=null){
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
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysRoleButton sysRoleButton = new SysRoleButton();
		sysRoleButton.setId(id);
		try {
			sysRoleButtonFeign.delete(sysRoleButton);
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
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		formsys4002 = formService.getFormData("sys4002");
		model.addAttribute("formsys4002", formsys4002);
		return "/component/sys/SysRoleButton_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys4002 = formService.getFormData("sys4002");
		 getFormValue(formsys4002);
		 SysRoleButton sysRoleButton = new SysRoleButton();
		 setObjValue(formsys4002, sysRoleButton);
		 sysRoleButtonFeign.insert(sysRoleButton);
		 getObjValue(formsys4002, sysRoleButton);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("sysRoleButton", sysRoleButton));
		 List<SysRoleButton> sysRoleButtonList = (List<SysRoleButton>)sysRoleButtonFeign.findByPage(ipage).getResult();
		 model.addAttribute("formsys4002", formsys4002);
		 model.addAttribute("sysRoleButtonList", sysRoleButtonList);
		 return "/component/sys/SysRoleButton_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String id,Model model) throws Exception{
		ActionContext.initialize(request,response);
		formsys4001 = formService.getFormData("sys4001");
		getFormValue(formsys4001);
		SysRoleButton sysRoleButton = new SysRoleButton();
		sysRoleButton.setId(id);
		sysRoleButton = sysRoleButtonFeign.getById(sysRoleButton);
		getObjValue(formsys4001, sysRoleButton);
		model.addAttribute("formsys4001", formsys4001);
		return "/component/sys/SysRoleButton_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String id) throws Exception {
		ActionContext.initialize(request,
				response);
		SysRoleButton sysRoleButton = new SysRoleButton();
		sysRoleButton.setId(id);
		sysRoleButtonFeign.delete(sysRoleButton);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys4002 = formService.getFormData("sys4002");
		 getFormValue(formsys4002);
		 boolean validateFlag = this.validateFormData(formsys4002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys4002 = formService.getFormData("sys4002");
		 getFormValue(formsys4002);
		 boolean validateFlag = this.validateFormData(formsys4002);
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public FormData getFormsys4002() {
		return formsys4002;
	}
	public void setFormsys4002(FormData formsys4002) {
		this.formsys4002 = formsys4002;
	}
	public FormData getFormsys4001() {
		return formsys4001;
	}
	public void setFormsys4001(FormData formsys4001) {
		this.formsys4001 = formsys4001;
	}
}
