package app.component.pfs.controller;

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

import app.component.pfs.entity.CusFinModel;
import app.component.pfs.entity.CusFinValid;
import app.component.pfs.feign.CusFinValidFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

@Controller
@RequestMapping("/cusFinValid")
public class CusFinValidController extends BaseFormBean{
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CusFinValidFeign cusFinValidFeign;
	
	
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		return "getListPage";
	}
	/***
	 * 列表数据查询的异步方法
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CusFinValid cusFinValid = new CusFinValid();
		try {
			cusFinValid.setCustomQuery(ajaxData);//自定义查询参数赋值
			cusFinValid.setCriteriaList(cusFinValid, ajaxData);//我的筛选
			//this.getRoleConditions(demo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage = cusFinValidFeign.findByPage(ipage, cusFinValid);
			//返回相应的HTML方法
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
	
	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CusFinValid cusFinValid = new CusFinValid();	
		FormData formpfs1002 = formService.getFormData("pfs1002");
		getObjValue(formpfs1002,cusFinValid);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formpfs1002,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		return dataMap;
	}
	/**
	 * AJAX新增
	 * @param query 
	 */
	@RequestMapping(value = "/toInsertAjax")
	@ResponseBody
	public Map<String, Object> toInsertAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs1002 = formService.getFormData("pfs1002");
		FormData 	formpfs1001 = formService.getFormData("pfs1001");
			getFormValue(formpfs1002, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs1002)){
		CusFinValid cusFinValid = new CusFinValid();
				setObjValue(formpfs1002, cusFinValid);
				getObjValue(formpfs1001,cusFinValid);
				JsonFormUtil jfu = new JsonFormUtil();
				String  formHtml = jfu.getJsonStr(formpfs1001,"bigFormTag",query);
				dataMap.put("formHtml",formHtml);
				List<CusFinModel> modelList = cusFinValidFeign.getCusFinModel(cusFinValid.getReportType(), cusFinValid.getAccRule());
				dataMap.put("modelList",modelList);
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
	 * AJAX新增
	 * @param cusNo 
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId, Integer pageNo, Integer pageSize, Ipage ipage, Object cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs1001 = formService.getFormData("pfs1001");
			getFormValue(formpfs1001, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs1001)){
		CusFinValid cusFinValid = new CusFinValid();
				setObjValue(formpfs1001, cusFinValid);
				String exps = cusFinValid.getFiller();
				cusFinValidFeign.insert(cusFinValid,exps);
				getTableData(dataMap);//获取列表
				dataMap.put("validNo",cusFinValid.getValidNo());
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId, Integer pageNo, Integer pageSize, Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs1001 = formService.getFormData("pfs1001");
			getFormValue(formpfs1001, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs1001)){
		CusFinValid cusFinValid = new CusFinValid();
				setObjValue(formpfs1001, cusFinValid);
				String exps = cusFinValid.getFiller();
				cusFinValidFeign.update(cusFinValid,exps);
				cusFinValid = cusFinValidFeign.getById(cusFinValid.getValidNo());
				List<CusFinValid> list = new ArrayList<CusFinValid>();
				list.add(cusFinValid);
				getTableData(list,tableId,dataMap);//获取列表
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String validNo, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formpfs1001 = formService.getFormData("pfs1001");
		CusFinValid cusFinValid = cusFinValidFeign.getById(validNo);
		String exps = cusFinValid.getLeftExp() + "=" + cusFinValid.getRightExp();
		List<CusFinModel> modelList = cusFinValidFeign.getCusFinModel(cusFinValid.getReportType(), cusFinValid.getAccRule());
		cusFinValid.setFiller(exps);
		getObjValue(formpfs1001,cusFinValid);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formpfs1001,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		dataMap.put("modelList",modelList);
		return dataMap;
	}
	
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String validNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			cusFinValidFeign.delete(validNo);
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
	
	private void getTableData(Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = "";//jtu.getJsonStr(tableId,"tableTag", cusFinParmFeign.getAllList(cusFinParm), null,true);
		dataMap.put("tableData",tableHtml);
	}
	
	private void getTableData(List<CusFinValid> list, String tableId, Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	
	
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model, String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs1001 = formService.getFormData("pfs1001");
		getFormValue(formpfs1001);
		CusFinValid cusFinValid = new CusFinValid();
		setObjValue(formpfs1001, cusFinValid);
		Ipage ipage = this.getIpage();
		List cusFinValidList = (List)cusFinValidFeign.findByPage(ipage, cusFinValid).getResult();
		model.addAttribute("formpfs1001", formpfs1001);
		model.addAttribute("cusFinValidList", cusFinValidList);
		model.addAttribute("query", "");
		return "list";
	}
	
	@RequestMapping(value = "/inputMain")
	public String inputMain(Model model, String ajaxData, CusFinValid cusFinValid) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs1001 = formService.getFormData("pfs1001");
		getObjValue(formpfs1001,cusFinValid);
		model.addAttribute("formpfs1001", formpfs1001);
		model.addAttribute("query", "");
		return "input_main";
	}
	
	@RequestMapping(value = "/inputSon")
	public String inputSon(Model model, String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs1001 = formService.getFormData("pfs1001");
		CusFinValid cusFinValid = new CusFinValid();
		getFormValue(formpfs1001);
		getObjValue(formpfs1001,cusFinValid);
		List<CusFinModel> modelList = cusFinValidFeign.getCusFinModel(cusFinValid.getReportType(), cusFinValid.getAccRule());
		String query = "add";
		model.addAttribute("formpfs1001", formpfs1001);
		model.addAttribute("modelList", modelList);
		model.addAttribute("query", query);
		return "input_son";
	}
	
	@RequestMapping(value = "/insertOrUpdate")
	public String insertOrUpdate(Model model, String ajaxData, Object query) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		String exps;
		FormData 	formpfs1001 = formService.getFormData("pfs1001");
		if("add".equals(query)){
		
		CusFinValid 	cusFinValid = new CusFinValid();
			getFormValue(formpfs1001);
			setObjValue(formpfs1001,cusFinValid);
			exps = cusFinValid.getFiller();
			if(exps.split("=").length != 2){
				this.addActionMessage(model, "操作失败,请核对公式格式[XXXX=aaaa+bbbb]");
			}else{
				cusFinValidFeign.insert(cusFinValid,exps);
			}
		}else{
		CusFinValid 	cusFinValid = new CusFinValid();
			getFormValue(formpfs1001);
			setObjValue(formpfs1001,cusFinValid);
			exps = cusFinValid.getFiller();
			if(exps.split("=").length != 2){
				this.addActionMessage(model, "操作失败,请核对公式格式[XXXX=aaaa+bbbb]");
			}else{
				cusFinValidFeign.update(cusFinValid,exps);
			}
		}
		Ipage ipage = this.getIpage();
		CusFinValid cusFinValid = null;
		List cusFinValidList = (List)cusFinValidFeign.findByPage(ipage, cusFinValid).getResult();
		model.addAttribute("formpfs1001", formpfs1001);
		model.addAttribute("cusFinValidList", cusFinValidList);
		model.addAttribute("query", "");
		return "list";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, String ajaxData) throws Exception{
		
		return "";
	}
	
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String query) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs1001 = formService.getFormData("pfs1001");
		CusFinValid cusFinValid = cusFinValidFeign.getById(query);
		String exps = cusFinValid.getLeftExp() + "=" + cusFinValid.getRightExp();
		List<CusFinModel> modelList = cusFinValidFeign.getCusFinModel(cusFinValid.getReportType(), cusFinValid.getAccRule());
		cusFinValid.setFiller(exps);
		this.getObjValue( formpfs1001,cusFinValid);
		model.addAttribute("formpfs1001", formpfs1001);
		model.addAttribute("modelList", modelList);
		model.addAttribute("query", "");
		return "input_son";
	}
	
	@RequestMapping(value = "/del")
	public String del(Model model, String ajaxData, String validNo, CusFinValid cusFinValid) throws Exception{
		ActionContext.initialize(request, response);
		cusFinValidFeign.delete(validNo);
		Ipage ipage = this.getIpage();
		List cusFinValidList = (List)cusFinValidFeign.findByPage(ipage, cusFinValid).getResult();
		model.addAttribute("cusFinValidList", cusFinValidList);
		model.addAttribute("query", "");
		return "list";
	}

}
