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
import app.component.pfs.feign.CusFinListFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: CusFinListAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Mon Aug 29 09:43:44 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinList")
public class CusFinListController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入CusFinListBo
	@Autowired
	private CusFinListFeign cusFinListFeign;
//	@Autowired
	//private CusRenterFeign cusRenterFeign;
	//全局变量
	//异步参数
	//表单变量
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo, String rptDate) throws Exception {
		FormService formService = new FormService();
		FormData formpfs = formService.getFormData("pfs0042");
		CusFinList cusFinList = new CusFinList();
		cusFinList.setCusNo(cusNo);
		cusFinList.setRptDate(rptDate);
		Ipage ipage = this.getIpage();
		List<CusFinList> cusFinListList = (List<CusFinList>)cusFinListFeign.findByPage(ipage, cusFinList).getResult();
		model.addAttribute("formpfs", formpfs);
		model.addAttribute("cusFinListList", cusFinListList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinList_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CusFinList cusFinList = new CusFinList();
		try {
			cusFinList.setCustomQuery(ajaxData);//自定义查询参数赋值
			cusFinList.setCriteriaList(cusFinList, ajaxData);//我的筛选
			//this.getRoleConditions(cusFinList,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = cusFinListFeign.findByPage(ipage, cusFinList);
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
	public Map<String, Object> insertAjax(String ajaxData,String cusNo, String rptDate, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs0042 = formService.getFormData("pfs0042");
			getFormValue(formpfs0042, getMapByJson(ajaxData));
			
			if(this.validateFormData(formpfs0042)){
		CusFinList cusFinList = new CusFinList();
				setObjValue(formpfs0042, cusFinList);
				boolean flag=cusFinListFeign.getFlag(cusFinList);
				if(flag){
				cusFinListFeign.insert(cusFinList);
				getTableData(cusNo,rptDate,tableId,dataMap);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", "新增失败,报表日期不能重复!");
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
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpfs0042 = formService.getFormData("pfs0042");
		getFormValue(formpfs0042, getMapByJson(ajaxData));
		CusFinList cusFinListJsp = new CusFinList();
		setObjValue(formpfs0042, cusFinListJsp);
		CusFinList cusFinList = cusFinListFeign.getById(cusFinListJsp);
		if(cusFinList!=null){
			try{
				cusFinList = (CusFinList)EntityUtil.reflectionSetVal(cusFinList, cusFinListJsp, getMapByJson(ajaxData));
				cusFinListFeign.update(cusFinList);
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
	private void getTableData(String cusNo, String rptDate, String tableId,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		CusFinList cusFinList = new CusFinList();
		cusFinList.setCusNo(cusNo);
		cusFinList.setRptDate(rptDate);
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", cusFinListFeign.getAll(cusFinList), null,true);
		dataMap.put("tableData",tableHtml);
		}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String cusNo, String rptDate, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs0042 = formService.getFormData("pfs0042");
			getFormValue(formpfs0042, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs0042)){
				CusFinList cusFinList = new CusFinList();
				setObjValue(formpfs0042, cusFinList);
				cusFinListFeign.update(cusFinList);
				getTableData(cusNo,rptDate,tableId,dataMap);
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
	 * @param cusNo 
	 * @param rptDate 
	 * @param query 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String cusNo, String rptDate, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formpfs = formService.getFormData("pfs0043");
		CusFinList cusFinList = new CusFinList();
		cusFinList.setCusNo(cusNo);
		cusFinList.setRptDate(rptDate);
		cusFinList = cusFinListFeign.getById(cusFinList);
		getObjValue(formpfs, cusFinList,formData);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs, "formTag", query);
		dataMap.put("formHtml", formHtml);
		dataMap.put("flag", "success");
		if(cusFinList!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param cusNo 
	 * @param rptDate 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String cusNo, String rptDate,String tableId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CusFinList cusFinList = new CusFinList();
		cusFinList.setCusNo(cusNo);
		cusFinList.setRptDate(rptDate);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			cusFinList = (CusFinList)JSONObject.toBean(jb, CusFinList.class);
			cusFinListFeign.delete(cusFinList);
			getTableData(cusNo,rptDate,tableId,dataMap);
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
	
	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax(String ajaxData, String cusNo, String rptDate, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		/*CusRenter cusRenter = new CusRenter();
		cusRenter.setCusNo(cusNo);
		cusRenter = cusRenterFeign.getById(cusRenter);*/
		
		
		CusFinList cusFinList = new CusFinList();
		cusFinList.setCusNo(cusNo);
		cusFinList.setRptDate(rptDate);
		//cusFinList.setCusName(cusRenter.getCusName());
		cusFinList.setRegOrgNo(User.getOrgNo(this.getHttpRequest()));
		cusFinList.setRegNo(User.getRegNo(this.getHttpRequest()));
		cusFinList.setRegDate(User.getSysDate(this.getHttpRequest()));
		cusFinList.setLstDate(User.getSysDate(this.getHttpRequest()));
		FormData formpfs = formService.getFormData("pfs0042");
		getObjValue(formpfs, cusFinList);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs, "formTag", query);
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
		FormData formpfs0042 = formService.getFormData("pfs0042");
		model.addAttribute("formpfs0042", formpfs0042);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinList_Insert";
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
		FormData  formpfs0042 = formService.getFormData("pfs0042");
		 getFormValue(formpfs0042);
		CusFinList  cusFinList = new CusFinList();
		 setObjValue(formpfs0042, cusFinList);
		 cusFinListFeign.insert(cusFinList);
		 getObjValue(formpfs0042, cusFinList);
		 this.addActionMessage(model, "保存成功");
		 List<CusFinList> cusFinListList = (List<CusFinList>)cusFinListFeign.findByPage(this.getIpage(), cusFinList).getResult();
		model.addAttribute("formpfs0042", formpfs0042);
		model.addAttribute("cusFinListList", cusFinListList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinList_Detail";
	}
	/**
	 * 查询
	 * @param cusNo 
	 * @param rptDate 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String cusNo, String rptDate) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formpfs0042 = formService.getFormData("pfs0042");
		 getFormValue(formpfs0042);
		CusFinList  cusFinList = new CusFinList();
		 cusFinList.setCusNo(cusNo);
		 cusFinList.setRptDate(rptDate);
		 cusFinList = cusFinListFeign.getById(cusFinList);
		 getObjValue(formpfs0042, cusFinList);
		 
		model.addAttribute("formpfs0042", formpfs0042);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinListMainDetail";
	}
	@RequestMapping(value = "/getDetailInf")
	public String getDetailInf(Model model, String ajaxData, String cusNo, String rptDate)throws Exception{
		
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formpfs0042 = formService.getFormData("pfs0042");
		 getFormValue(formpfs0042);
		CusFinList  cusFinList = new CusFinList();
		 cusFinList.setCusNo(cusNo);
		 cusFinList.setRptDate(rptDate);
		 cusFinList = cusFinListFeign.getById(cusFinList);
		 getObjValue(formpfs0042, cusFinList);
		 
		model.addAttribute("formpfs0042", formpfs0042);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinList_Detail";
	}
	/**
	 * 删除
	 * @param cusNo 
	 * @param rptDate 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String cusNo, String rptDate) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		CusFinList cusFinList = new CusFinList();
		cusFinList.setCusNo(cusNo);
		cusFinListFeign.delete(cusFinList);
		return getListPage(model,cusNo,rptDate);
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
		FormData  formpfs0042 = formService.getFormData("pfs0042");
		 getFormValue(formpfs0042);
		 boolean validateFlag = this.validateFormData(formpfs0042);
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
		FormData  formpfs0042 = formService.getFormData("pfs0042");
		 getFormValue(formpfs0042);
		 boolean validateFlag = this.validateFormData(formpfs0042);
	}
	
}
