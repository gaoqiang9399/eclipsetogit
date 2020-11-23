package  app.component.msgconf.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.component.common.EntityUtil;
import app.component.msgconf.entity.AppMessageForTrench;
import app.component.msgconf.feign.AppMessageForTrenchFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

/**
 * Title: AppMessageForTrenchController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 01 10:56:11 CST 2018
 **/
@Controller
@RequestMapping("/appMessageForTrench")
public class AppMessageForTrenchController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入appMessageForTrenchFeign
	@Autowired
	private AppMessageForTrenchFeign appMessageForTrenchFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	public FormService formService = new FormService();
	

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
//	public String getListPage() throws Exception {
//		ActionContext.initialize(request,
//				response);
//		return "AppMessageForTrench_List";
//	}
	/***
	 * 列表数据查询
	 * @param pageNo 
	 * @param tableId 
	 * @param tableType 
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,Ipage ipage, int pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		AppMessageForTrench appMessageForTrench = new AppMessageForTrench();
		try {
			appMessageForTrench.setCustomQuery(ajaxData);//自定义查询参数赋值
			appMessageForTrench.setCriteriaList(appMessageForTrench, ajaxData);//我的筛选
			//appMessageForTrench.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(appMessageForTrench,"1000000001");//记录级权限控制方法
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("appMessageForTrench", appMessageForTrench));
			//自定义查询Bo方法
			ipage = appMessageForTrenchFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formmsgconf0002 = formService.getFormData("msgconf0002");
			getFormValue(formmsgconf0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmsgconf0002)){
				AppMessageForTrench appMessageForTrench = new AppMessageForTrench();
				setObjValue(formmsgconf0002, appMessageForTrench);
				appMessageForTrenchFeign.insert(appMessageForTrench);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @param ajaxData 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		FormData formmsgconf0002 = formService.getFormData("msgconf0002");
		getFormValue(formmsgconf0002, getMapByJson(ajaxData));
		AppMessageForTrench appMessageForTrenchJsp = new AppMessageForTrench();
		setObjValue(formmsgconf0002, appMessageForTrenchJsp);
		AppMessageForTrench appMessageForTrench = appMessageForTrenchFeign.getById(appMessageForTrenchJsp);
		if(appMessageForTrench!=null){
			try{
				appMessageForTrench = (AppMessageForTrench)EntityUtil.reflectionSetVal(appMessageForTrench, appMessageForTrenchJsp, getMapByJson(ajaxData));
				appMessageForTrenchFeign.update(appMessageForTrench);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @param ajaxData 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		AppMessageForTrench appMessageForTrench = new AppMessageForTrench();
		try{
			FormData formmsgconf0002 = formService.getFormData("msgconf0002");
			getFormValue(formmsgconf0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmsgconf0002)){
				appMessageForTrench = new AppMessageForTrench();
				setObjValue(formmsgconf0002, appMessageForTrench);
				appMessageForTrenchFeign.update(appMessageForTrench);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formmsgconf0002 = formService.getFormData("msgconf0002");
		AppMessageForTrench appMessageForTrench = new AppMessageForTrench();
		appMessageForTrench.setId(id);
		appMessageForTrench = appMessageForTrenchFeign.getById(appMessageForTrench);
		getObjValue(formmsgconf0002, appMessageForTrench,formData);
		if(appMessageForTrench!=null){
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
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		AppMessageForTrench appMessageForTrench = new AppMessageForTrench();
		appMessageForTrench.setId(id);
		try {
			appMessageForTrenchFeign.delete(appMessageForTrench);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
//	public String input() throws Exception{
//		ActionContext.initialize(request,response);
//		FormData formmsgconf0002 = formService.getFormData("msgconf0002");
//		return "AppMessageForTrench_Insert";
//	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
//	public String getById() throws Exception{
//		ActionContext.initialize(request,response);
//		FormData formmsgconf0001 = formService.getFormData("msgconf0001");
//		 getFormValue(formmsgconf0001);
//		 AppMessageForTrench appMessageForTrench = new AppMessageForTrench();
//		appMessageForTrench.setId(id);
//		 appMessageForTrench = appMessageForTrenchFeign.getById(appMessageForTrench);
//		 getObjValue(formmsgconf0001, appMessageForTrench);
//		return "AppMessageForTrench_Detail";
//	}


}
