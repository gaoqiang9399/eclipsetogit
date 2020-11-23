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
import app.component.pfs.entity.CusFinPayAcc;
import app.component.pfs.feign.CusFinListFeign;
import app.component.pfs.feign.CusFinPayAccFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: CusFinPayAccAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:30:42 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinPayAcc")
public class CusFinPayAccController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入CusFinPayAccBo
	@Autowired
	private CusFinPayAccFeign cusFinPayAccFeign;
	@Autowired
	private CusFinListFeign cusFinListFeign;
	//全局变量
	//异步参数
	//表单变量
	
	
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
		FormData formpfs0060 = formService.getFormData("pfs0060");
		CusFinPayAcc cusFinPayAcc = new CusFinPayAcc();
		cusFinPayAcc.setCusNo(cusNo);
		cusFinPayAcc.setRptDate(rptDate);
		Ipage ipage = this.getIpage();
		List<CusFinPayAcc> cusFinPayAccList = (List<CusFinPayAcc>)cusFinPayAccFeign.findByPage(ipage, cusFinPayAcc).getResult();
		model.addAttribute("formpfs0060", formpfs0060);
		model.addAttribute("cusFinPayAccList", cusFinPayAccList);
		model.addAttribute("query", "");
		return "getListPage";
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
		CusFinPayAcc cusFinPayAcc = new CusFinPayAcc();
		try {
			cusFinPayAcc.setCustomQuery(ajaxData);//自定义查询参数赋值
			cusFinPayAcc.setCriteriaList(cusFinPayAcc, ajaxData);//我的筛选
			//this.getRoleConditions(cusFinPayAcc,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = cusFinPayAccFeign.findByPage(ipage, cusFinPayAcc);
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
	private void getTableData(String cusNo, String rptDate, String tableId, Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		CusFinPayAcc cusFinPayAcc = new CusFinPayAcc();
		cusFinPayAcc.setCusNo(cusNo);
		cusFinPayAcc.setRptDate(rptDate);
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", cusFinPayAccFeign.getAll(cusFinPayAcc), null,true);
		dataMap.put("tableData",tableHtml);
		}
	/**
	 * AJAX新增
	 * @param cusNo 
	 * @param rptDate 
	 * @param tableId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String cusNo, String rptDate, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs0060 = formService.getFormData("pfs0060");
			getFormValue(formpfs0060, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs0060)){
		CusFinPayAcc cusFinPayAcc = new CusFinPayAcc();
				setObjValue(formpfs0060, cusFinPayAcc);
				cusFinPayAccFeign.insert(cusFinPayAcc);
				cusNo=cusFinPayAcc.getCusNo();
				rptDate=cusFinPayAcc.getRptDate();
				getTableData(cusNo, rptDate, tableId, dataMap);
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
	/****
	 * ajax新增
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
		CusFinPayAcc cusFinPayAcc = new CusFinPayAcc();
		cusFinPayAcc.setCusNo(cusNo);
		cusFinPayAcc.setCusName(cusFinList.getCusName());
		cusFinPayAcc.setRptDate(rptDate);
		cusFinPayAcc.setRegOrgNo(User.getOrgNo(this.getHttpRequest()));
		cusFinPayAcc.setRegNo(User.getRegNo(this.getHttpRequest()));
		cusFinPayAcc.setRegDate(User.getSysDate(this.getHttpRequest()));
		cusFinPayAcc.setLstDate(User.getSysDate(this.getHttpRequest()));
		FormData formpfs0060 = formService.getFormData("pfs0060");
		getObjValue(formpfs0060, cusFinPayAcc);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs0060, "formTag", query);
		dataMap.put("formHtml", formHtml);
		dataMap.put("flag", "success");
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData,String cusNo, String rptDate, String tableId,String query) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpfs0060 = formService.getFormData("pfs0060");
		getFormValue(formpfs0060, getMapByJson(ajaxData));
		CusFinPayAcc cusFinPayAccJsp = new CusFinPayAcc();
		setObjValue(formpfs0060, cusFinPayAccJsp);
		CusFinPayAcc cusFinPayAcc = cusFinPayAccFeign.getById(cusFinPayAccJsp);
		if(cusFinPayAcc!=null){
			try{
				cusFinPayAcc = (CusFinPayAcc)EntityUtil.reflectionSetVal(cusFinPayAcc, cusFinPayAccJsp, getMapByJson(ajaxData));
				cusFinPayAccFeign.update(cusFinPayAcc);
				getTableData(cusNo, rptDate, tableId, dataMap);
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
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpfs0060 = formService.getFormData("pfs0060");
			getFormValue(formpfs0060, getMapByJson(ajaxData));
			if(this.validateFormData(formpfs0060)){
				CusFinPayAcc cusFinPayAcc = new CusFinPayAcc();
				setObjValue(formpfs0060, cusFinPayAcc);
				cusFinPayAccFeign.update(cusFinPayAcc);
				String cusNo = cusFinPayAcc.getCusNo();
				String rptDate = cusFinPayAcc.getRptDate();
				getTableData(cusNo, rptDate, tableId, dataMap);
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
		FormData formpfs0060 = formService.getFormData("pfs0060");
		CusFinPayAcc cusFinPayAcc = new CusFinPayAcc();
		cusFinPayAcc.setId(id);
		cusFinPayAcc = cusFinPayAccFeign.getById(cusFinPayAcc);
		getObjValue(formpfs0060, cusFinPayAcc,formData);
		if(cusFinPayAcc!=null){
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
	 * @param tableId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String id, String tableId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CusFinPayAcc cusFinPayAcc = new CusFinPayAcc();
		cusFinPayAcc.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			cusFinPayAcc = (CusFinPayAcc)JSONObject.toBean(jb, CusFinPayAcc.class);
			cusFinPayAccFeign.delete(cusFinPayAcc);
			String cusNo = cusFinPayAcc.getCusNo();
			String rptDate = cusFinPayAcc.getRptDate();
			getTableData(cusNo, rptDate, tableId, dataMap);
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
		FormData formpfs0060 = formService.getFormData("pfs0060");
		model.addAttribute("formpfs0060", formpfs0060);
		model.addAttribute("query", "");
		return "input";
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
		FormData  formpfs0060 = formService.getFormData("pfs0060");
		 getFormValue(formpfs0060);
		CusFinPayAcc  cusFinPayAcc = new CusFinPayAcc();
		 setObjValue(formpfs0060, cusFinPayAcc);
		 cusFinPayAccFeign.insert(cusFinPayAcc);
		 getObjValue(formpfs0060, cusFinPayAcc);
		 this.addActionMessage(model, "保存成功");
		 List<CusFinPayAcc> cusFinPayAccList = (List<CusFinPayAcc>)cusFinPayAccFeign.findByPage(this.getIpage(), cusFinPayAcc).getResult();
		model.addAttribute("formpfs0060", formpfs0060);
		model.addAttribute("cusFinPayAccList", cusFinPayAccList);
		model.addAttribute("query", "");
		return "insert";
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
		FormData  formpfs0059 = formService.getFormData("pfs0059");
		 getFormValue(formpfs0059);
		CusFinPayAcc  cusFinPayAcc = new CusFinPayAcc();
		cusFinPayAcc.setId(id);
		 cusFinPayAcc = cusFinPayAccFeign.getById(cusFinPayAcc);
		 getObjValue(formpfs0059, cusFinPayAcc);
		model.addAttribute("formpfs0059", formpfs0059);
		model.addAttribute("query", "");
		return "query";
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
		CusFinPayAcc cusFinPayAcc = new CusFinPayAcc();
		cusFinPayAcc.setId(id);
		cusFinPayAccFeign.delete(cusFinPayAcc);
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
		FormData  formpfs0060 = formService.getFormData("pfs0060");
		 getFormValue(formpfs0060);
		 boolean validateFlag = this.validateFormData(formpfs0060);
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
		FormData  formpfs0060 = formService.getFormData("pfs0060");
		 getFormValue(formpfs0060);
		 boolean validateFlag = this.validateFormData(formpfs0060);
	}
	
}
