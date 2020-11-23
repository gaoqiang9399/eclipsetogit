package  app.component.oa.controller;
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
import app.component.oa.entity.MfOaFormConfig;
import app.component.oa.feign.MfOaFormConfigFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaFormConfigAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 22 11:00:33 CST 2017
 **/
@Controller
@RequestMapping("/mfOaFormConfig")
public class MfOaFormConfigController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaFormConfigFeign mfOaFormConfigFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/MfOaFormConfig_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			MfOaFormConfig mfOaFormConfig = new MfOaFormConfig();
			mfOaFormConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfOaFormConfig.setCriteriaList(mfOaFormConfig, ajaxData);//我的筛选
			//mfOaFormConfig.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfOaFormConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfOaFormConfig", mfOaFormConfig));
			//自定义查询Bo方法
			ipage = mfOaFormConfigFeign.findByPage(ipage);
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
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formoa0002 =new FormService().getFormData("oa0002");
			getFormValue(formoa0002, getMapByJson(ajaxData));
			if(this.validateFormData(formoa0002)){
				MfOaFormConfig mfOaFormConfig = new MfOaFormConfig();
				setObjValue(formoa0002, mfOaFormConfig);
				mfOaFormConfigFeign.insert(mfOaFormConfig);
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
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formoa0002 =new FormService().getFormData("oa0002");
		getFormValue(formoa0002, getMapByJson(ajaxData));
		MfOaFormConfig mfOaFormConfigJsp = new MfOaFormConfig();
		setObjValue(formoa0002, mfOaFormConfigJsp);
		MfOaFormConfig mfOaFormConfig = mfOaFormConfigFeign.getById(mfOaFormConfigJsp);
		if(mfOaFormConfig!=null){
			try{
				mfOaFormConfig = (MfOaFormConfig)EntityUtil.reflectionSetVal(mfOaFormConfig, mfOaFormConfigJsp, getMapByJson(ajaxData));
				mfOaFormConfigFeign.update(mfOaFormConfig);
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
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			MfOaFormConfig mfOaFormConfig = new MfOaFormConfig();
			FormData formoa0002 =new FormService().getFormData("oa0002");
			getFormValue(formoa0002, getMapByJson(ajaxData));
			if(this.validateFormData(formoa0002)){
				mfOaFormConfig = new MfOaFormConfig();
				setObjValue(formoa0002, mfOaFormConfig);
				mfOaFormConfigFeign.update(mfOaFormConfig);
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
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String,Object> formData = new HashMap<String,Object>();
		FormData formoa0002 = new FormService().getFormData("oa0002");
		MfOaFormConfig mfOaFormConfig = new MfOaFormConfig();
		mfOaFormConfig.setId(id);
		mfOaFormConfig = mfOaFormConfigFeign.getById(mfOaFormConfig);
		getObjValue(formoa0002, mfOaFormConfig,formData);
		if(mfOaFormConfig!=null){
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
	@ResponseBody
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			MfOaFormConfig mfOaFormConfig = new MfOaFormConfig();
			mfOaFormConfig.setId(id);
			mfOaFormConfigFeign.delete(mfOaFormConfig);
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormData formoa0002 = new FormService().getFormData("oa0002");
		model.addAttribute("formoa0002", formoa0002);
		model.addAttribute("query", "");
		return "/component/oa/MfOaFormConfig_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String id,Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormData formoa0001 = new FormService().getFormData("oa0001");
		getFormValue(formoa0001);
		MfOaFormConfig mfOaFormConfig = new MfOaFormConfig();
		mfOaFormConfig.setId(id);
		mfOaFormConfig = mfOaFormConfigFeign.getById(mfOaFormConfig);
		getObjValue(formoa0001, mfOaFormConfig);
		model.addAttribute("formoa0001", formoa0001);
		model.addAttribute("mfOaFormConfig", mfOaFormConfig);
		model.addAttribute("query", "");
		return "/component/oa/MfOaFormConfig_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormData formoa0002 = new FormService().getFormData("oa0002");
		getFormValue(formoa0002);
		boolean validateFlag = this.validateFormData(formoa0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormData formoa0002 = new FormService().getFormData("oa0002");
		getFormValue(formoa0002);
		boolean validateFlag = this.validateFormData(formoa0002);
	}
	
	
}
