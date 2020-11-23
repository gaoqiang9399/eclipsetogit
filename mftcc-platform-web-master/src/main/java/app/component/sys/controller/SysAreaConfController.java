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

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.sys.entity.SysAreaConf;
import app.component.sys.feign.SysAreaConfFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: SysAreaConfAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue May 10 03:41:01 GMT 2016
 **/
@Controller
@RequestMapping("/sysAreaConf")
public class SysAreaConfController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入sysAreaConfFeign
	@Autowired
	private SysAreaConfFeign sysAreaConfFeign;
	
	private String query;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//表单变量
	private FormData formsys1001;
	private FormData formsys1002;
	private FormData formsys1004;
	private FormService formService = new FormService();
	
	public SysAreaConfController(){
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
		return "/component/sys/SysAreaConf_List";
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
		SysAreaConf sysAreaConf = new SysAreaConf();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			sysAreaConf=(SysAreaConf)JSONObject.toBean(jb,SysAreaConf.class);
			//this.getRoleConditions(sysAreaConf,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysAreaConf", sysAreaConf));
			ipage = sysAreaConfFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			formsys1002 = formService.getFormData("sys1002");
			getFormValue(formsys1002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys1002)){
				SysAreaConf sysAreaConf = new SysAreaConf();
				setObjValue(formsys1002, sysAreaConf);
				sysAreaConf.setSts(BizPubParm.YES_NO_Y);
				sysAreaConfFeign.insert(sysAreaConf);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				getTableDataIpage(sysAreaConf.getRegionNo(), dataMap, tableId); ;
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formsys1002 = formService.getFormData("sys1002");
		getFormValue(formsys1002, getMapByJson(ajaxData));
		SysAreaConf sysAreaConfJsp = new SysAreaConf();
		setObjValue(formsys1002, sysAreaConfJsp);
		SysAreaConf sysAreaConf = sysAreaConfFeign.getById(sysAreaConfJsp);
		if(sysAreaConf!=null){
			try{
				sysAreaConf = (SysAreaConf)EntityUtil.reflectionSetVal(sysAreaConf, sysAreaConfJsp,dataMap);
				sysAreaConfFeign.update(sysAreaConf);
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysAreaConf sysAreaConf = new SysAreaConf();
		try{
			formsys1002 = formService.getFormData("sys1002");
			getFormValue(formsys1002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys1002)){
				sysAreaConf = new SysAreaConf();
				setObjValue(formsys1002, sysAreaConf);
				sysAreaConfFeign.update(sysAreaConf);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
				getTableDataIpage(sysAreaConf.getRegionNo(), dataMap, tableId); ;
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
	public Map<String,Object> getByIdAjax(String regionNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formsys1002 = formService.getFormData("sys1002");
		SysAreaConf sysAreaConf = new SysAreaConf();
		sysAreaConf.setRegionNo(regionNo);
		sysAreaConf = sysAreaConfFeign.getById(sysAreaConf);
		getObjValue(formsys1002, sysAreaConf,formData);
		if(sysAreaConf!=null){
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
	public Map<String, Object> deleteAjax(String regionNo) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysAreaConf sysAreaConf = new SysAreaConf();
		sysAreaConf.setRegionNo(regionNo);
		try {
			sysAreaConfFeign.delete(sysAreaConf);
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
		formsys1002 = formService.getFormData("sys1002");
		model.addAttribute("formsys1002", formsys1002);
		return "component/sys/SysAreaConf_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys1002 = formService.getFormData("sys1002");
		 getFormValue(formsys1002);
		 SysAreaConf sysAreaConf = new SysAreaConf();
		 setObjValue(formsys1002, sysAreaConf);
		 sysAreaConfFeign.insert(sysAreaConf);
		 getObjValue(formsys1002, sysAreaConf);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("sysAreaConf", sysAreaConf));
		 List<SysAreaConf> sysAreaConfList = (List<SysAreaConf>)sysAreaConfFeign.findByPage(ipage).getResult();
		 model.addAttribute("formsys1002", formsys1002);
		 model.addAttribute("sysAreaConfList", sysAreaConfList);
		 return "component/sys/SysAreaConf_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String regionNo) throws Exception{
		ActionContext.initialize(request,response);
		 formsys1002 = formService.getFormData("sys1002");
		 getFormValue(formsys1002);
		 SysAreaConf sysAreaConf = new SysAreaConf();
		sysAreaConf.setRegionNo(regionNo);
		 sysAreaConf = sysAreaConfFeign.getById(sysAreaConf);
		 getObjValue(formsys1002, sysAreaConf);
		 model.addAttribute("formsys1002", formsys1002);
		return "/component/sys/SysAreaConf_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String regionNo) throws Exception {
		ActionContext.initialize(request,
				response);
		SysAreaConf sysAreaConf = new SysAreaConf();
		sysAreaConf.setRegionNo(regionNo);
		sysAreaConfFeign.delete(sysAreaConf);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys1002 = formService.getFormData("sys1002");
		 getFormValue(formsys1002);
		 boolean validateFlag = this.validateFormData(formsys1002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys1002 = formService.getFormData("sys1002");
		 getFormValue(formsys1002);
		 boolean validateFlag = this.validateFormData(formsys1002);
	}
	/**
	 * c面，大区配置
	 * @return
	 */
	@RequestMapping(value = "/getConfPage")
	public String getConfPage(Model model){
		ActionContext.initialize(request,response);
		formsys1002=formService.getFormData("sys1002");
		formsys1001=formService.getFormData("sys1001");
		formsys1004=formService.getFormData("sys1004");
		model.addAttribute("formsys1002", formsys1002);
		model.addAttribute("formsys1001", formsys1001);
		model.addAttribute("formsys1001", formsys1004);
		return "/component/sys/SysAreaConf_List";
	}
	private void getTableDataIpage(String regionNo,Map<String,Object> dataMap,String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		SysAreaConf sysAreaConf = new SysAreaConf();
		sysAreaConf.setRegionNo(regionNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("sysAreaConf", sysAreaConf));
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List)sysAreaConfFeign.findByPage(ipage).getResult(), ipage,true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage",ipage);
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public FormData getFormsys1002() {
		return formsys1002;
	}
	public void setFormsys1002(FormData formsys1002) {
		this.formsys1002 = formsys1002;
	}
	public FormData getFormsys1001() {
		return formsys1001;
	}
	public void setFormsys1001(FormData formsys1001) {
		this.formsys1001 = formsys1001;
	}

	public FormData getFormsys1004() {
		return formsys1004;
	}

	public void setFormsys1004(FormData formsys1004) {
		this.formsys1004 = formsys1004;
	}
	
}
