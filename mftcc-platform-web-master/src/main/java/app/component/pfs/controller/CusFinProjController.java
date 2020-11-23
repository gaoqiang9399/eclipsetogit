package  app.component.pfs.controller;
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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.pfs.entity.CusFinList;
import app.component.pfs.entity.CusFinProj;
import app.component.pfs.feign.CusFinListFeign;
import app.component.pfs.feign.CusFinProjFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: CusFinProjAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:29:07 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinProj")
public class CusFinProjController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入CusFinProjBo
	@Autowired
	private CusFinProjFeign cusFinProjFeign;
	@Autowired
	private CusFinListFeign cusFinListFeign;
	//全局变量
	//异步参数
	//表单变量
	
	
	private void getTableData(Ipage ipage, Integer pageSize, Integer pageNo, String cusNo, String rptDate,
			String tableId, Map<String, Object> dataMap) throws Exception {
			ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		// 自定义查询Bo方法
		CusFinProj cusFinProj = new CusFinProj();
		cusFinProj.setCusNo(cusNo);
		cusFinProj.setRptDate(rptDate);
		ipage = cusFinProjFeign.findByPage(ipage, cusFinProj);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",(List) ipage.getResult(), null, true);
		dataMap.put("tableData", tableHtml);
		}
	/**
	 * 列表打开页面请求
	 * @param cusNo 
	 * @param rptDate 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo, String rptDate) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formpfs0058 = formService.getFormData("pfs0058");
		CusFinProj cusFinProj = new CusFinProj();
		cusFinProj.setCusNo(cusNo);
		cusFinProj.setRptDate(rptDate);
		Ipage ipage = this.getIpage();
		List<CusFinProj> cusFinProjList = (List<CusFinProj>)cusFinProjFeign.findByPage(ipage, cusFinProj).getResult();
		model.addAttribute("formpfs0058", formpfs0058);
		model.addAttribute("cusFinProjList", cusFinProjList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinProj_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CusFinProj cusFinProj = new CusFinProj();
		try {
			cusFinProj.setCustomQuery(ajaxData);//自定义查询参数赋值
			cusFinProj.setCriteriaList(cusFinProj, ajaxData);//我的筛选
			//this.getRoleConditions(cusFinProj,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = cusFinProjFeign.findByPage(ipage, cusFinProj);
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
	 * @param ipage 
	 * @param pageSize 
	 * @param pageNo 
	 * @param tableId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, Ipage ipage, Integer pageSize, Integer pageNo, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs0058 = formService.getFormData("pfs0058");
			getFormValue(formpfs0058, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs0058)){
		CusFinProj cusFinProj = new CusFinProj();
				setObjValue(formpfs0058, cusFinProj);
				cusFinProjFeign.insert(cusFinProj);
				String cusNo = cusFinProj.getCusNo();
				String rptDate = cusFinProj.getRptDate();
				getTableData(ipage, pageSize, pageNo, cusNo, rptDate, tableId, dataMap);
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
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpfs0058 = formService.getFormData("pfs0058");
		getFormValue(formpfs0058, getMapByJson(ajaxData));
		CusFinProj cusFinProjJsp = new CusFinProj();
		setObjValue(formpfs0058, cusFinProjJsp);
		CusFinProj cusFinProj = cusFinProjFeign.getById(cusFinProjJsp);
		if(cusFinProj!=null){
			try{
				cusFinProj = (CusFinProj)EntityUtil.reflectionSetVal(cusFinProj, cusFinProjJsp, getMapByJson(ajaxData));
				cusFinProjFeign.update(cusFinProj);
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
	public Map<String, Object> updateAjax(String ajaxData, Ipage ipage, Integer pageSize, Integer pageNo, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs0058 = formService.getFormData("pfs0058");
			getFormValue(formpfs0058, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs0058)){
				CusFinProj cusFinProj = new CusFinProj();
				setObjValue(formpfs0058, cusFinProj);
				cusFinProjFeign.update(cusFinProj);
				String cusNo = cusFinProj.getCusNo();
				String rptDate = cusFinProj.getRptDate();
				getTableData(ipage, pageSize, pageNo, cusNo, rptDate, tableId, dataMap);
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
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formpfs0058 = formService.getFormData("pfs0058");
		CusFinProj cusFinProj = new CusFinProj();
		cusFinProj.setId(id);
		cusFinProj = cusFinProjFeign.getById(cusFinProj);
		getObjValue(formpfs0058, cusFinProj,formData);
		if(cusFinProj!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, Ipage ipage, Integer pageSize, Integer pageNo, String tableId, String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CusFinProj cusFinProj = new CusFinProj();
		cusFinProj.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			cusFinProj = (CusFinProj)JSONObject.toBean(jb, CusFinProj.class);
			cusFinProjFeign.delete(cusFinProj);
			String cusNo = cusFinProj.getCusNo();
			String rptDate = cusFinProj.getRptDate();
			getTableData(ipage, pageSize, pageNo, cusNo, rptDate, tableId, dataMap);
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
	/****
	 * ajax新增获取数据
	 * @param cusNo 
	 * @param rptDate 
	 * @param query 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax(String ajaxData, String cusNo, String rptDate, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinList cusFinList = new CusFinList();
		 cusFinList.setCusNo(cusNo);
		 cusFinList.setRptDate(rptDate);
		 cusFinList = cusFinListFeign.getById(cusFinList);
		 
		CusFinProj  cusFinProj = new CusFinProj();
		 cusFinProj.setCusNo(cusNo);
		 cusFinProj.setCusName(cusFinList.getCusName());
		 cusFinProj.setRptDate(rptDate);
		 cusFinProj.setRegOrgNo(User.getOrgNo(this.getHttpRequest()));
		 cusFinProj.setRegNo(User.getRegNo(this.getHttpRequest()));
		 cusFinProj.setRegDate(User.getSysDate(this.getHttpRequest()));
		 cusFinProj.setLstDate(User.getSysDate(this.getHttpRequest()));
		FormData formpfs0058 = formService.getFormData("pfs0058");
		getObjValue(formpfs0058, cusFinProj);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs0058, "formTag", query);
		dataMap.put("formHtml", formHtml);
		dataMap.put("flag", "success");
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formpfs0058 = formService.getFormData("pfs0058");
		model.addAttribute("formpfs0058", formpfs0058);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinProj_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formpfs0058 = formService.getFormData("pfs0058");
		 getFormValue(formpfs0058);
		CusFinProj  cusFinProj = new CusFinProj();
		 setObjValue(formpfs0058, cusFinProj);
		 cusFinProjFeign.insert(cusFinProj);
		 getObjValue(formpfs0058, cusFinProj);
		 this.addActionMessage(model, "保存成功");
		 List<CusFinProj> cusFinProjList = (List<CusFinProj>)cusFinProjFeign.findByPage(this.getIpage(), cusFinProj).getResult();
		model.addAttribute("formpfs0058", formpfs0058);
		model.addAttribute("cusFinProjList", cusFinProjList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinProj_Detail";
	}
	/**
	 * 查询
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formpfs0057 = formService.getFormData("pfs0057");
		 getFormValue(formpfs0057);
		CusFinProj  cusFinProj = new CusFinProj();
		cusFinProj.setId(id);
		 cusFinProj = cusFinProjFeign.getById(cusFinProj);
		 getObjValue(formpfs0057, cusFinProj);
		model.addAttribute("formpfs0057", formpfs0057);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinProj_Detail";
	}
	/**
	 * 删除
	 * @param id 
	 * @param cusNo 
	 * @param rpsDate 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String id, String cusNo, String rpsDate) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		CusFinProj cusFinProj = new CusFinProj();
		cusFinProj.setId(id);
		cusFinProjFeign.delete(cusFinProj);
		return getListPage(model,cusNo,rpsDate);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formpfs0058 = formService.getFormData("pfs0058");
		 getFormValue(formpfs0058);
		 boolean validateFlag = this.validateFormData(formpfs0058);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formpfs0058 = formService.getFormData("pfs0058");
		 getFormValue(formpfs0058);
		 boolean validateFlag = this.validateFormData(formpfs0058);
	}
	
}
