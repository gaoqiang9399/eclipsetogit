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
import app.component.sys.entity.SysAreaRegionRel;
import app.component.sys.feign.SysAreaRegionRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: SysAreaRegionRelAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue May 10 03:42:07 GMT 2016
 **/
@Controller
@RequestMapping("/sysAreaRegionRel")
public class SysAreaRegionRelController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入sysAreaRegionRelFeign
	@Autowired
	private SysAreaRegionRelFeign sysAreaRegionRelFeign;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	private String query;
	//异步参数

	//表单变量
	private FormData formsys1003;
	private FormData formsys1004;
	private FormService formService = new FormService();
	
	public SysAreaRegionRelController(){
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
		return "/component/sys/SysAreaRegionRel_List";
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
		SysAreaRegionRel sysAreaRegionRel = new SysAreaRegionRel();
		try {
			
			JSONObject jb = JSONObject.fromObject(ajaxData);
			sysAreaRegionRel=(SysAreaRegionRel)JSONObject.toBean(jb,SysAreaRegionRel.class);
			//this.getRoleConditions(sysAreaRegionRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysAreaRegionRel", sysAreaRegionRel));
			ipage = sysAreaRegionRelFeign.findByPage(ipage);
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
			formsys1004 = formService.getFormData("sys1004");
			getFormValue(formsys1004, getMapByJson(ajaxData));
			if(this.validateFormData(formsys1004)){
				
				SysAreaRegionRel sysAreaRegionRel = new SysAreaRegionRel();
				setObjValue(formsys1004, sysAreaRegionRel);
				int count=sysAreaRegionRelFeign.getCountByProvNo(sysAreaRegionRel);
				if(count>0){
					dataMap.put("flag", "error");
					dataMap.put("msg",sysAreaRegionRel.getProvName()+",已经存在不能新增！");
				}else{
					sysAreaRegionRelFeign.insert(sysAreaRegionRel);
					String regionNo=sysAreaRegionRel.getRegionNo();
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					getTableDataIpage(regionNo, tableId, dataMap); ;
				}
				
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
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formsys1004 = formService.getFormData("sys1004");
		getFormValue(formsys1004, getMapByJson(ajaxData));
		SysAreaRegionRel sysAreaRegionRelJsp = new SysAreaRegionRel();
		setObjValue(formsys1004, sysAreaRegionRelJsp);
		SysAreaRegionRel sysAreaRegionRel = sysAreaRegionRelFeign.getById(sysAreaRegionRelJsp);
		if(sysAreaRegionRel!=null){
			try{
				sysAreaRegionRel = (SysAreaRegionRel)EntityUtil.reflectionSetVal(sysAreaRegionRel, sysAreaRegionRelJsp,dataMap);
				sysAreaRegionRelFeign.update(sysAreaRegionRel);
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
		SysAreaRegionRel sysAreaRegionRel = new SysAreaRegionRel();
		try{
			formsys1004 = formService.getFormData("sys1004");
			getFormValue(formsys1004, getMapByJson(ajaxData));
			if(this.validateFormData(formsys1004)){
				sysAreaRegionRel = new SysAreaRegionRel();
				setObjValue(formsys1004, sysAreaRegionRel);
				sysAreaRegionRelFeign.update(sysAreaRegionRel);
				String regionNo=sysAreaRegionRel.getRegionNo();
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
				getTableDataIpage(regionNo, tableId, dataMap); ;
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
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		formsys1004 = formService.getFormData("sys1004");
		SysAreaRegionRel sysAreaRegionRel = new SysAreaRegionRel();
		sysAreaRegionRel.setId(id);
		sysAreaRegionRel = sysAreaRegionRelFeign.getById(sysAreaRegionRel);
		getObjValue(formsys1004, sysAreaRegionRel,formData);
		if(sysAreaRegionRel!=null){
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
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysAreaRegionRel sysAreaRegionRel = new SysAreaRegionRel();
		sysAreaRegionRel.setId(id);
		try {
			sysAreaRegionRelFeign.delete(sysAreaRegionRel);
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
	@ResponseBody
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		formsys1004 = formService.getFormData("sys1004");
		model.addAttribute("formsys1004", formsys1004);
		return "/component/sys/SysAreaRegionRel_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	@ResponseBody
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys1004 = formService.getFormData("sys1004");
		 getFormValue(formsys1004);
		 SysAreaRegionRel sysAreaRegionRel = new SysAreaRegionRel();
		 setObjValue(formsys1004, sysAreaRegionRel);
		 sysAreaRegionRelFeign.insert(sysAreaRegionRel);
		 getObjValue(formsys1004, sysAreaRegionRel);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("sysAreaRegionRel", sysAreaRegionRel));
		 List<SysAreaRegionRel> sysAreaRegionRelList = (List<SysAreaRegionRel>)sysAreaRegionRelFeign.findByPage(ipage).getResult();
		 model.addAttribute("formsys1004", formsys1004);
		 model.addAttribute("sysAreaRegionRelList", sysAreaRegionRelList);
		 return "/component/sys/SysAreaRegionRel_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	@ResponseBody
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request,response);
		 formsys1004 = formService.getFormData("sys1004");
		 getFormValue(formsys1004);
		 SysAreaRegionRel sysAreaRegionRel = new SysAreaRegionRel();
		sysAreaRegionRel.setId(id);
		 sysAreaRegionRel = sysAreaRegionRelFeign.getById(sysAreaRegionRel);
		 getObjValue(formsys1004, sysAreaRegionRel);
		 model.addAttribute("formsys1004", formsys1004);
		return "/component/sys/SysAreaRegionRel_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(String id) throws Exception {
		ActionContext.initialize(request,response);
		SysAreaRegionRel sysAreaRegionRel = new SysAreaRegionRel();
		sysAreaRegionRel.setId(id);
		sysAreaRegionRelFeign.delete(sysAreaRegionRel);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys1004 = formService.getFormData("sys1004");
		 getFormValue(formsys1004);
		 boolean validateFlag = this.validateFormData(formsys1004);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys1004 = formService.getFormData("sys1004");
		 getFormValue(formsys1004);
		 boolean validateFlag = this.validateFormData(formsys1004);
	}
	private void getTableDataIpage(String regionNo,String tableId,Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		SysAreaRegionRel sysAreaRegionRel = new SysAreaRegionRel();
	//	sysAreaRegionRel.setId(id);
		sysAreaRegionRel.setRegionNo(regionNo);
		Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("sysAreaRegionRel", sysAreaRegionRel));
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List)sysAreaRegionRelFeign.findByPage(ipage).getResult(), ipage,true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage",ipage);
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public FormData getFormsys1004() {
		return formsys1004;
	}
	public void setFormsys1004(FormData formsys1004) {
		this.formsys1004 = formsys1004;
	}
	public FormData getFormsys1003() {
		return formsys1003;
	}
	public void setFormsys1003(FormData formsys1003) {
		this.formsys1003 = formsys1003;
	}
}
