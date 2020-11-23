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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.sys.entity.SysNotice;
import app.component.sys.feign.SysNoticeFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: SysNoticeAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue Jul 26 04:21:37 GMT 2016
 **/
@Controller
@RequestMapping("/sysNotice")
public class SysNoticeController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//spring注入BO
	@Autowired
	private SysNoticeFeign sysNoticeFeign;
	//全局变量

	private String query;
	//异步参数
	//表单变量
	private FormData formsys8001;
	private FormData formsys8002;
	private FormService formService = new FormService();
	
	public SysNoticeController(){
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
		return "/component/sys/SysNotice_List";
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
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysNotice sysNotice = new SysNotice();
		try {
			sysNotice.setCustomQuery(ajaxData);//自定义查询参数赋值
			sysNotice.setCriteriaList(sysNotice, ajaxData);//我的筛选
			//this.getRoleConditions(sysNotice,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysNotice", sysNotice));
			sysNotice.setOptNo(User.getRegNo(request));
			ipage = sysNoticeFeign.findByPage(ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			formsys8002 = formService.getFormData("sys8002");
			getFormValue(formsys8002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys8002)){
				SysNotice sysNotice = new SysNotice();
				setObjValue(formsys8002, sysNotice);
				sysNotice.setBrNo("ALL");
				sysNotice.setRoleNo("ALL");
				sysNotice.setOpNo("ALL");
				sysNoticeFeign.insert(sysNotice);
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
	@RequestMapping(value = "/insertAndPushAjax")
	@ResponseBody
	public Map<String, Object> insertAndPushAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			formsys8002 = formService.getFormData("sys8002");
			getFormValue(formsys8002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys8002)){
				SysNotice sysNotice = new SysNotice();
				setObjValue(formsys8002, sysNotice);
				/*sysNotice.setBrNo("ALL");
				sysNotice.setRoleNo("ALL");
				sysNotice.setOpNo("ALL");*/
				sysNoticeFeign.insertAndPush(sysNotice);
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
		}
		return dataMap;
	}
	@RequestMapping(value = "/pushAjax")
	@ResponseBody
	public Map<String, Object> pushAjax(String no) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysNotice sysNotice = new SysNotice();
		sysNotice.setNo(no);
		sysNoticeFeign.push(sysNotice);
		if(sysNotice!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
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
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		formsys8002 = formService.getFormData("sys8002");
		getFormValue(formsys8002, getMapByJson(ajaxData));
		SysNotice sysNoticeJsp = new SysNotice();
		setObjValue(formsys8002, sysNoticeJsp);
		SysNotice sysNotice = sysNoticeFeign.getById(sysNoticeJsp);
		if(sysNotice!=null){
			try{
				sysNotice = (SysNotice)EntityUtil.reflectionSetVal(sysNotice, sysNoticeJsp, getMapByJson(ajaxData));
				sysNoticeFeign.update(sysNotice);
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
		SysNotice sysNotice = new SysNotice();
		try{
			formsys8002 = formService.getFormData("sys8002");
			getFormValue(formsys8002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys8002)){
				sysNotice = new SysNotice();
				setObjValue(formsys8002, sysNotice);
				sysNoticeFeign.update(sysNotice);
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
	public Map<String, Object> getByIdAjax(String no) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formsys8002 = formService.getFormData("sys8002");
		SysNotice sysNotice = new SysNotice();
		sysNotice.setNo(no);
		sysNotice = sysNoticeFeign.getById(sysNotice);
		getObjValue(formsys8002, sysNotice,formData);
		if(sysNotice!=null){
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
	public Map<String, Object> deleteAjax(String no) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysNotice sysNotice = new SysNotice();
		sysNotice.setNo(no);
		try {
			sysNoticeFeign.delete(sysNotice);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
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
	public String input(Model model,String brNo,String roleNo,String opNo)throws Exception{
		ActionContext.initialize(request,response);
		 formsys8002 = formService.getFormData("sys8002");
     	 getFormValue(formsys8002);
     	 SysNotice sysNotice = new SysNotice();
		 sysNotice.setBrNo(brNo);
		 sysNotice.setRoleNo(roleNo);
		 sysNotice.setOpNo(opNo);
		 getObjValue(formsys8002, sysNotice);
		 model.addAttribute("formsys8002", formsys8002);
		return "/component/sys/SysNotice_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys8002 = formService.getFormData("sys8002");
		 getFormValue(formsys8002);
		 SysNotice sysNotice = new SysNotice();
		 setObjValue(formsys8002, sysNotice);
		 sysNoticeFeign.insert(sysNotice);
		 getObjValue(formsys8002, sysNotice);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("sysNotice", sysNotice));
		 List<SysNotice> sysNoticeList = (List<SysNotice>)sysNoticeFeign.findByPage(ipage).getResult();
		 model.addAttribute("formsys8002", formsys8002);
		 model.addAttribute("sysNoticeList", sysNoticeList);
		 return "/component/sys/SysNotice_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String no) throws Exception{
		ActionContext.initialize(request,response);
		 formsys8001 = formService.getFormData("sys8001");
		 getFormValue(formsys8001);
		 SysNotice sysNotice = new SysNotice();
		sysNotice.setNo(no);
		 sysNotice = sysNoticeFeign.getById(sysNotice);
		 getObjValue(formsys8001, sysNotice);
		 model.addAttribute("formsys8001", formsys8001);
		return "/component/sys/SysNotice_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String no) throws Exception {
		ActionContext.initialize(request,response);
		SysNotice sysNotice = new SysNotice();
		sysNotice.setNo(no);
		sysNoticeFeign.delete(sysNotice);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys8002 = formService.getFormData("sys8002");
		 getFormValue(formsys8002);
		 boolean validateFlag = this.validateFormData(formsys8002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys8002 = formService.getFormData("sys8002");
		 getFormValue(formsys8002);
		 boolean validateFlag = this.validateFormData(formsys8002);
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public FormData getFormsys8002() {
		return formsys8002;
	}
	public void setFormsys8002(FormData formsys8002) {
		this.formsys8002 = formsys8002;
	}
	public FormData getFormsys8001() {
		return formsys8001;
	}
	public void setFormsys8001(FormData formsys8001) {
		this.formsys8001 = formsys8001;
	}
	
}
