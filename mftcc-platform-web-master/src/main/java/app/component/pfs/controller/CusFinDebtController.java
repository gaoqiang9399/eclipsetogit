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
import app.component.pfs.entity.CusFinDebt;
import app.component.pfs.entity.CusFinList;
import app.component.pfs.feign.CusFinDebtFeign;
import app.component.pfs.feign.CusFinListFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: CusFinDebtAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:22:19 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinDebt")
public class CusFinDebtController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入CusFinDebtBo
	@Autowired
	private CusFinDebtFeign cusFinDebtFeign;
	@Autowired
	private CusFinListFeign cusFinListFeign;
	//全局变量
	//异步参数
	//表单变量
	
	private void getTableData(Ipage ipage, Integer pageSize, Integer pageNo, String cusNo, String rptDate, String tableId, Map<String,Object> dataMap) throws Exception {
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		// 自定义查询Bo方法
		CusFinDebt cusFinDebt = new CusFinDebt();
		cusFinDebt.setCusNo(cusNo);
		cusFinDebt.setRptDate(rptDate);
		ipage = cusFinDebtFeign.findByPage(ipage, cusFinDebt);
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
		FormData formpfs0052 = formService.getFormData("pfs0052");
		CusFinDebt cusFinDebt = new CusFinDebt();
		cusFinDebt.setCusNo(cusNo);
		cusFinDebt.setRptDate(rptDate);
		Ipage ipage = this.getIpage();
		List<CusFinDebt> cusFinDebtList = (List<CusFinDebt>)cusFinDebtFeign.findByPage(ipage, cusFinDebt).getResult();
		model.addAttribute("formpfs0052", formpfs0052);
		model.addAttribute("cusFinDebtList", cusFinDebtList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinDebt_List";
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
		CusFinDebt cusFinDebt = new CusFinDebt();
		try {
			cusFinDebt.setCustomQuery(ajaxData);//自定义查询参数赋值
			cusFinDebt.setCriteriaList(cusFinDebt, ajaxData);//我的筛选
			//this.getRoleConditions(cusFinDebt,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = cusFinDebtFeign.findByPage(ipage, cusFinDebt);
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
		 
		CusFinDebt  cusFinDebt = new CusFinDebt();
		 cusFinDebt.setCusNo(cusNo);
		 cusFinDebt.setCusName(cusFinList.getCusName());
		 cusFinDebt.setRptDate(rptDate);
		 cusFinDebt.setRegOrgNo(User.getOrgNo(this.getHttpRequest()));
		 cusFinDebt.setRegNo(User.getRegNo(this.getHttpRequest()));
		 cusFinDebt.setRegDate(User.getSysDate(this.getHttpRequest()));
		 cusFinDebt.setLstDate(User.getSysDate(this.getHttpRequest()));
		FormData formpfs0052 = formService.getFormData("pfs0052");
		getObjValue(formpfs0052, cusFinDebt);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs0052, "formTag", query);
		dataMap.put("formHtml", formHtml);
		dataMap.put("flag", "success");
		return dataMap;
	}
	/**
	 * AJAX新增
	 * @param pageSize 
	 * @param tableId 
	 * @param pageNo 
	 * @param ipage 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, Integer pageSize, String tableId, Integer pageNo, Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs0052 = formService.getFormData("pfs0052");
			getFormValue(formpfs0052, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs0052)){
		CusFinDebt cusFinDebt = new CusFinDebt();
				setObjValue(formpfs0052, cusFinDebt);
				cusFinDebtFeign.insert(cusFinDebt);
				String cusNo = cusFinDebt.getCusNo();
				String rptDate = cusFinDebt.getRptDate();
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
		FormData formpfs0052 = formService.getFormData("pfs0052");
		getFormValue(formpfs0052, getMapByJson(ajaxData));
		CusFinDebt cusFinDebtJsp = new CusFinDebt();
		setObjValue(formpfs0052, cusFinDebtJsp);
		CusFinDebt cusFinDebt = cusFinDebtFeign.getById(cusFinDebtJsp);
		if(cusFinDebt!=null){
			try{
				cusFinDebt = (CusFinDebt)EntityUtil.reflectionSetVal(cusFinDebt, cusFinDebtJsp, getMapByJson(ajaxData));
				cusFinDebtFeign.update(cusFinDebt);
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
	 * @param ipage 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, Integer pageSize, String tableId, Integer pageNo, Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs0052 = formService.getFormData("pfs0052");
			getFormValue(formpfs0052, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs0052)){
				CusFinDebt cusFinDebt = new CusFinDebt();
				setObjValue(formpfs0052, cusFinDebt);
				cusFinDebtFeign.update(cusFinDebt);
				String cusNo = cusFinDebt.getCusNo();
				String rptDate = cusFinDebt.getRptDate();
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formpfs0052 = formService.getFormData("pfs0052");
		CusFinDebt cusFinDebt = new CusFinDebt();
		cusFinDebt.setId(id);
		cusFinDebt = cusFinDebtFeign.getById(cusFinDebt);
		getObjValue(formpfs0052, cusFinDebt,formData);
		if(cusFinDebt!=null){
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
	public Map<String, Object> deleteAjax(String id,String ajaxData, Integer pageSize, String tableId, Integer pageNo, Ipage ipage) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CusFinDebt cusFinDebt = new CusFinDebt();
		cusFinDebt.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			cusFinDebt = (CusFinDebt)JSONObject.toBean(jb, CusFinDebt.class);
			cusFinDebtFeign.delete(cusFinDebt);
			String cusNo = cusFinDebt.getCusNo();
			String rptDate = cusFinDebt.getRptDate();
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
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formpfs0052 = formService.getFormData("pfs0052");
		model.addAttribute("formpfs0052", formpfs0052);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinDebt_Insert";
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
		FormData  formpfs0052 = formService.getFormData("pfs0052");
		 getFormValue(formpfs0052);
		CusFinDebt  cusFinDebt = new CusFinDebt();
		 setObjValue(formpfs0052, cusFinDebt);
		 cusFinDebtFeign.insert(cusFinDebt);
		 getObjValue(formpfs0052, cusFinDebt);
		 this.addActionMessage(model, "保存成功");
		 List<CusFinDebt> cusFinDebtList = (List<CusFinDebt>)cusFinDebtFeign.findByPage(this.getIpage(), cusFinDebt).getResult();
		model.addAttribute("formpfs0052", formpfs0052);
		model.addAttribute("cusFinDebtList", cusFinDebtList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinDebt_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formpfs0051 = formService.getFormData("pfs0051");
		 getFormValue(formpfs0051);
		CusFinDebt  cusFinDebt = new CusFinDebt();
		cusFinDebt.setId(id);
		 cusFinDebt = cusFinDebtFeign.getById(cusFinDebt);
		 getObjValue(formpfs0051, cusFinDebt);
		model.addAttribute("formpfs0051", formpfs0051);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinDebt_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id,String cusNo, String rptDate) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		CusFinDebt cusFinDebt = new CusFinDebt();
		cusFinDebt.setId(id);
		cusFinDebtFeign.delete(cusFinDebt);
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
		FormData  formpfs0052 = formService.getFormData("pfs0052");
		 getFormValue(formpfs0052);
		 boolean validateFlag = this.validateFormData(formpfs0052);
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
		FormData  formpfs0052 = formService.getFormData("pfs0052");
		 getFormValue(formpfs0052);
		 boolean validateFlag = this.validateFormData(formpfs0052);
	}
	
}
