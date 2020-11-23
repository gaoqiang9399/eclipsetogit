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
import app.component.sys.entity.MfBusModel;
import app.component.sys.feign.MfBusModelFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusModelAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Oct 25 11:45:14 CST 2017
 **/
@Controller
@RequestMapping("/mfBusModel")
public class MfBusModelController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入mfBusModelFeign
	@Autowired 
	private MfBusModelFeign mfBusModelFeign;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	private String query;
	
	//表单变量
	private FormData formbusmodel0001;
	private FormData formbusmodel0002;
	private FormService formService = new FormService();
	
	public MfBusModelController(){
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
		return "/component/sys/MfBusModel_List";
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
		MfBusModel mfBusModel = new MfBusModel();
		try {
			mfBusModel.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusModel.setCriteriaList(mfBusModel, ajaxData);//我的筛选
			//mfBusModel.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfBusModel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusModel", mfBusModel));
			ipage = mfBusModelFeign.findByPage(ipage);
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
			formbusmodel0002 = formService.getFormData("busmodel0002");
			getFormValue(formbusmodel0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbusmodel0002)){
				MfBusModel mfBusModel = new MfBusModel();
				setObjValue(formbusmodel0002, mfBusModel);
				mfBusModelFeign.insert(mfBusModel);
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
		formbusmodel0002 = formService.getFormData("busmodel0002");
		getFormValue(formbusmodel0002, getMapByJson(ajaxData));
		MfBusModel mfBusModelJsp = new MfBusModel();
		setObjValue(formbusmodel0002, mfBusModelJsp);
		MfBusModel mfBusModel = mfBusModelFeign.getById(mfBusModelJsp);
		if(mfBusModel!=null){
			try{
				mfBusModel = (MfBusModel)EntityUtil.reflectionSetVal(mfBusModel, mfBusModelJsp, getMapByJson(ajaxData));
				mfBusModelFeign.update(mfBusModel);
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
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusModel mfBusModel = new MfBusModel();
		try{
			formbusmodel0002 = formService.getFormData("busmodel0002");
			getFormValue(formbusmodel0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbusmodel0002)){
				mfBusModel = new MfBusModel();
				setObjValue(formbusmodel0002, mfBusModel);
				mfBusModelFeign.update(mfBusModel);
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
	public Map<String,Object> getByIdAjax(String modelId) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formbusmodel0002 = formService.getFormData("busmodel0002");
		MfBusModel mfBusModel = new MfBusModel();
		mfBusModel.setModelId(modelId);
		mfBusModel = mfBusModelFeign.getById(mfBusModel);
		getObjValue(formbusmodel0002, mfBusModel,formData);
		if(mfBusModel!=null){
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
	public Map<String, Object> deleteAjax(String modelId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusModel mfBusModel = new MfBusModel();
		mfBusModel.setModelId(modelId);
		try {
			mfBusModelFeign.delete(mfBusModel);
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
		formbusmodel0002 = formService.getFormData("busmodel0002");
		model.addAttribute("formbusmodel0002", formbusmodel0002);
		return "/component/sys/MfBusModel_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getById")
	public String getById(String modelId,Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formbusmodel0001 = formService.getFormData("busmodel0001");
		 getFormValue(formbusmodel0001);
		 MfBusModel mfBusModel = new MfBusModel();
		mfBusModel.setModelId(modelId);
		 mfBusModel = mfBusModelFeign.getById(mfBusModel);
		 getObjValue(formbusmodel0001, mfBusModel);
		 model.addAttribute("formbusmodel0001", formbusmodel0001);
		return "/component/sys/MfBusModel_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formbusmodel0002 = formService.getFormData("busmodel0002");
		 getFormValue(formbusmodel0002);
		 boolean validateFlag = this.validateFormData(formbusmodel0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formbusmodel0002 = formService.getFormData("busmodel0002");
		 getFormValue(formbusmodel0002);
		 boolean validateFlag = this.validateFormData(formbusmodel0002);
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public FormData getFormbusmodel0002() {
		return formbusmodel0002;
	}
	public void setFormbusmodel0002(FormData formbusmodel0002) {
		this.formbusmodel0002 = formbusmodel0002;
	}
	public FormData getFormbusmodel0001() {
		return formbusmodel0001;
	}
	public void setFormbusmodel0001(FormData formbusmodel0001) {
		this.formbusmodel0001 = formbusmodel0001;
	}
	
}
