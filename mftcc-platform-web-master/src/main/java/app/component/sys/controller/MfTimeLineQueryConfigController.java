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
import app.component.sys.entity.MfTimeLineQueryConfig;
import app.component.sys.feign.MfTimeLineQueryConfigFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfTimeLineQueryConfigAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Nov 24 18:01:55 CST 2017
 **/
@Controller
@RequestMapping("/mfTimeLineQueryConfig")
public class MfTimeLineQueryConfigController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入mfTimeLineQueryConfigFeign
	@Autowired
	private MfTimeLineQueryConfigFeign mfTimeLineQueryConfigFeign;
	//全局变量
	private String query;
	
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//异步参数
	private FormData formtimequery0001;
	private FormData formtimequery0002;
	private FormService formService = new FormService();
	
	public MfTimeLineQueryConfigController(){
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
		return "/component/sys/MfTimeLineQueryConfig_List";
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
		MfTimeLineQueryConfig mfTimeLineQueryConfig = new MfTimeLineQueryConfig();
		try {
			mfTimeLineQueryConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfTimeLineQueryConfig.setCriteriaList(mfTimeLineQueryConfig, ajaxData);//我的筛选
			//mfTimeLineQueryConfig.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfTimeLineQueryConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfTimeLineQueryConfig", mfTimeLineQueryConfig));
			ipage = mfTimeLineQueryConfigFeign.findByPage(ipage);
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
			formtimequery0002 = formService.getFormData("timequery0002");
			getFormValue(formtimequery0002, getMapByJson(ajaxData));
			if(this.validateFormData(formtimequery0002)){
				MfTimeLineQueryConfig mfTimeLineQueryConfig = new MfTimeLineQueryConfig();
				setObjValue(formtimequery0002, mfTimeLineQueryConfig);
				mfTimeLineQueryConfigFeign.insert(mfTimeLineQueryConfig);
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
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formtimequery0002 = formService.getFormData("timequery0002");
		getFormValue(formtimequery0002, getMapByJson(ajaxData));
		MfTimeLineQueryConfig mfTimeLineQueryConfigJsp = new MfTimeLineQueryConfig();
		setObjValue(formtimequery0002, mfTimeLineQueryConfigJsp);
		MfTimeLineQueryConfig mfTimeLineQueryConfig = mfTimeLineQueryConfigFeign.getById(mfTimeLineQueryConfigJsp);
		if(mfTimeLineQueryConfig!=null){
			try{
				mfTimeLineQueryConfig = (MfTimeLineQueryConfig)EntityUtil.reflectionSetVal(mfTimeLineQueryConfig, mfTimeLineQueryConfigJsp, getMapByJson(ajaxData));
				mfTimeLineQueryConfigFeign.update(mfTimeLineQueryConfig);
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfTimeLineQueryConfig mfTimeLineQueryConfig = new MfTimeLineQueryConfig();
		try{
			formtimequery0002 = formService.getFormData("timequery0002");
			getFormValue(formtimequery0002, getMapByJson(ajaxData));
			if(this.validateFormData(formtimequery0002)){
				mfTimeLineQueryConfig = new MfTimeLineQueryConfig();
				setObjValue(formtimequery0002, mfTimeLineQueryConfig);
				mfTimeLineQueryConfigFeign.update(mfTimeLineQueryConfig);
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
	public Map<String,Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formtimequery0002 = formService.getFormData("timequery0002");
		MfTimeLineQueryConfig mfTimeLineQueryConfig = new MfTimeLineQueryConfig();
		mfTimeLineQueryConfig.setId(id);
		mfTimeLineQueryConfig = mfTimeLineQueryConfigFeign.getById(mfTimeLineQueryConfig);
		getObjValue(formtimequery0002, mfTimeLineQueryConfig,formData);
		if(mfTimeLineQueryConfig!=null){
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
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfTimeLineQueryConfig mfTimeLineQueryConfig = new MfTimeLineQueryConfig();
		mfTimeLineQueryConfig.setId(id);
		try {
			mfTimeLineQueryConfigFeign.delete(mfTimeLineQueryConfig);
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
		formtimequery0002 = formService.getFormData("timequery0002");
		model.addAttribute("formtimequery0002", formtimequery0002);
		return "/component/sys/MfTimeLineQueryConfig_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request,response);
		 formtimequery0001 = formService.getFormData("timequery0001");
		 getFormValue(formtimequery0001);
		 MfTimeLineQueryConfig  mfTimeLineQueryConfig = new MfTimeLineQueryConfig();
		mfTimeLineQueryConfig.setId(id);
		 mfTimeLineQueryConfig = mfTimeLineQueryConfigFeign.getById(mfTimeLineQueryConfig);
		 getObjValue(formtimequery0001, mfTimeLineQueryConfig);
		 model.addAttribute("formtimequery0001", formtimequery0001);
		return "/component/sys/MfTimeLineQueryConfig_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formtimequery0002 = formService.getFormData("timequery0002");
		 getFormValue(formtimequery0002);
		 boolean validateFlag = this.validateFormData(formtimequery0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formtimequery0002 = formService.getFormData("timequery0002");
		 getFormValue(formtimequery0002);
		 boolean validateFlag = this.validateFormData(formtimequery0002);
	}
	
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public FormData getFormtimequery0002() {
		return formtimequery0002;
	}
	public void setFormtimequery0002(FormData formtimequery0002) {
		this.formtimequery0002 = formtimequery0002;
	}
	public FormData getFormtimequery0001() {
		return formtimequery0001;
	}
	public void setFormtimequery0001(FormData formtimequery0001) {
		this.formtimequery0001 = formtimequery0001;
	}
	
}
