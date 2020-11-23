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
import app.component.sys.entity.MfQueryAccountInfo;
import app.component.sys.feign.MfQueryAccountInfoFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfQueryAccountInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jan 23 11:30:41 CST 2018
 **/
@Controller
@RequestMapping("/mfQueryAccountInfo")
public class MfQueryAccountInfoController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入mfQueryAccountInfoFeign
	@Autowired
	private MfQueryAccountInfoFeign mfQueryAccountInfoFeign;
	private String query;
	

	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//表单变量
	private FormData formQueryOp0001;
	private FormData formQueryOp0002;
	private FormService formService = new FormService();
	
	public MfQueryAccountInfoController(){
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
		return "/component/sys/MfQueryAccountInfo_List";
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
		MfQueryAccountInfo mfQueryAccountInfo = new MfQueryAccountInfo();
		try {
			mfQueryAccountInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfQueryAccountInfo.setCriteriaList(mfQueryAccountInfo, ajaxData);//我的筛选
			//mfQueryAccountInfo.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfQueryAccountInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfQueryAccountInfo", mfQueryAccountInfo));
			ipage = mfQueryAccountInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
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
			formQueryOp0002 = formService.getFormData("QueryOp0002");
			getFormValue(formQueryOp0002, getMapByJson(ajaxData));
			if(this.validateFormData(formQueryOp0002)){
				MfQueryAccountInfo mfQueryAccountInfo = new MfQueryAccountInfo();
				setObjValue(formQueryOp0002, mfQueryAccountInfo);
				mfQueryAccountInfoFeign.insert(mfQueryAccountInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
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
		formQueryOp0002 = formService.getFormData("QueryOp0002");
		getFormValue(formQueryOp0002, getMapByJson(ajaxData));
		MfQueryAccountInfo mfQueryAccountInfoJsp = new MfQueryAccountInfo();
		setObjValue(formQueryOp0002, mfQueryAccountInfoJsp);
		MfQueryAccountInfo mfQueryAccountInfo = mfQueryAccountInfoFeign.getById(mfQueryAccountInfoJsp);
		if(mfQueryAccountInfo!=null){
			try{
				mfQueryAccountInfo = (MfQueryAccountInfo)EntityUtil.reflectionSetVal(mfQueryAccountInfo, mfQueryAccountInfoJsp, getMapByJson(ajaxData));
				mfQueryAccountInfoFeign.update(mfQueryAccountInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	public Map<String, Object> updateAjax(String ajaxData ) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfQueryAccountInfo mfQueryAccountInfo = new MfQueryAccountInfo();
		try{
			formQueryOp0002 = formService.getFormData("QueryOp0002");
			getFormValue(formQueryOp0002, getMapByJson(ajaxData));
			if(this.validateFormData(formQueryOp0002)){
				mfQueryAccountInfo = new MfQueryAccountInfo();
				setObjValue(formQueryOp0002, mfQueryAccountInfo);
				mfQueryAccountInfoFeign.update(mfQueryAccountInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
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
	public Map<String,Object> getByIdAjax(String queryOpNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formQueryOp0002 = formService.getFormData("QueryOp0002");
		MfQueryAccountInfo mfQueryAccountInfo = new MfQueryAccountInfo();
		mfQueryAccountInfo.setQueryOpNo(queryOpNo);
		mfQueryAccountInfo = mfQueryAccountInfoFeign.getById(mfQueryAccountInfo);
		getObjValue(formQueryOp0002, mfQueryAccountInfo,formData);
		if(mfQueryAccountInfo!=null){
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
	public Map<String, Object> deleteAjax(String queryOpNo) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfQueryAccountInfo mfQueryAccountInfo = new MfQueryAccountInfo();
		mfQueryAccountInfo.setQueryOpNo(queryOpNo);
		try {
			mfQueryAccountInfoFeign.delete(mfQueryAccountInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
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
		formQueryOp0002 = formService.getFormData("QueryOp0002");
		model.addAttribute("formQueryOp0002", formQueryOp0002);
		return "/component/sys/MfQueryAccountInfo_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model ,String queryOpNo) throws Exception{
		ActionContext.initialize(request,response);
		 formQueryOp0001 = formService.getFormData("QueryOp0001");
		 getFormValue(formQueryOp0001);
		 MfQueryAccountInfo mfQueryAccountInfo = new MfQueryAccountInfo();
		mfQueryAccountInfo.setQueryOpNo(queryOpNo);
		 mfQueryAccountInfo = mfQueryAccountInfoFeign.getById(mfQueryAccountInfo);
		 getObjValue(formQueryOp0001, mfQueryAccountInfo);
		 model.addAttribute("formQueryOp0001", formQueryOp0001);
		return "/component/sys/MfQueryAccountInfo_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formQueryOp0002 = formService.getFormData("QueryOp0002");
		 getFormValue(formQueryOp0002);
		 boolean validateFlag = this.validateFormData(formQueryOp0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formQueryOp0002 = formService.getFormData("QueryOp0002");
		 getFormValue(formQueryOp0002);
		 boolean validateFlag = this.validateFormData(formQueryOp0002);
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public FormData getFormQueryOp0002() {
		return formQueryOp0002;
	}
	public void setFormQueryOp0002(FormData formQueryOp0002) {
		this.formQueryOp0002 = formQueryOp0002;
	}
	public FormData getFormQueryOp0001() {
		return formQueryOp0001;
	}
	public void setFormQueryOp0001(FormData formQueryOp0001) {
		this.formQueryOp0001 = formQueryOp0001;
	}
	
}