package app.component.app.controller;

import java.util.HashMap;
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

import app.component.app.entity.MfBusRepayRules;
import app.component.app.feign.MfBusRepayRulesFeign;
import app.component.common.EntityUtil;

/**
 * Title: MfBusRepayRulesAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 28 16:38:07 CST 2017
 **/
@Controller
@RequestMapping("/mfBusRepayRules")
public class MfBusRepayRulesController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusRepayRulesBo
	@Autowired
	private MfBusRepayRulesFeign mfBusRepayRulesFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		/**没有搜索到页面路径**/
		return "MfBusRepayRules_List";
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formapp0002 = formService.getFormData("app0002");
			getFormValue(formapp0002, getMapByJson(ajaxData));
			if(this.validateFormData(formapp0002)){
		MfBusRepayRules mfBusRepayRules = new MfBusRepayRules();
				setObjValue(formapp0002, mfBusRepayRules);
				mfBusRepayRulesFeign.insert(mfBusRepayRules);
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
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formapp0002 = formService.getFormData("app0002");
		getFormValue(formapp0002, getMapByJson(ajaxData));
		MfBusRepayRules mfBusRepayRulesJsp = new MfBusRepayRules();
		setObjValue(formapp0002, mfBusRepayRulesJsp);
		MfBusRepayRules mfBusRepayRules = mfBusRepayRulesFeign.getById(mfBusRepayRulesJsp);
		if(mfBusRepayRules!=null){
			try{
				mfBusRepayRules = (MfBusRepayRules)EntityUtil.reflectionSetVal(mfBusRepayRules, mfBusRepayRulesJsp, getMapByJson(ajaxData));
				mfBusRepayRulesFeign.update(mfBusRepayRules);
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
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusRepayRules mfBusRepayRules = new MfBusRepayRules();
		try{
		FormData 	formapp0002 = formService.getFormData("app0002");
			getFormValue(formapp0002, getMapByJson(ajaxData));
			if(this.validateFormData(formapp0002)){
				setObjValue(formapp0002, mfBusRepayRules);
				mfBusRepayRulesFeign.update(mfBusRepayRules);
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
	public Map<String, Object> getByIdAjax(String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formapp0002 = formService.getFormData("app0002");
		MfBusRepayRules mfBusRepayRules = new MfBusRepayRules();
		mfBusRepayRules.setId(id);
		mfBusRepayRules = mfBusRepayRulesFeign.getById(mfBusRepayRules);
		getObjValue(formapp0002, mfBusRepayRules,formData);
		if(mfBusRepayRules!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusRepayRules mfBusRepayRules = new MfBusRepayRules();
		mfBusRepayRules.setId(id);
		try {
			mfBusRepayRulesFeign.delete(mfBusRepayRules);
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
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formapp0002 = formService.getFormData("app0002");
		model.addAttribute("formapp0002", formapp0002);
		model.addAttribute("query", "");
		/**没有搜索到页面路径**/
		return "MfBusRepayRules_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formapp0001 = formService.getFormData("app0001");
		 getFormValue(formapp0001);
		MfBusRepayRules  mfBusRepayRules = new MfBusRepayRules();
		mfBusRepayRules.setId(id);
		 mfBusRepayRules = mfBusRepayRulesFeign.getById(mfBusRepayRules);
		 getObjValue(formapp0001, mfBusRepayRules);
		model.addAttribute("formapp0001", formapp0001);
		model.addAttribute("query", "");
		/**没有搜索到页面路径**/
		return "MfBusRepayRules_Detail";
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
		FormData  formapp0002 = formService.getFormData("app0002");
		 getFormValue(formapp0002);
		 boolean validateFlag = this.validateFormData(formapp0002);
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
		FormData  formapp0002 = formService.getFormData("app0002");
		 getFormValue(formapp0002);
		 boolean validateFlag = this.validateFormData(formapp0002);
	}
	
	
}
