package  app.component.prdct.controller;
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
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.feign.MfKindFormFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfKindFormAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jul 01 12:01:13 CST 2017
 **/
@Controller
@RequestMapping("/ mfKindForm")
public class MfKindFormController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入mfKindFormFeign
	@Autowired
	private MfKindFormFeign mfKindFormFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindForm_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindForm mfKindForm = new MfKindForm();
		try {
			mfKindForm.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfKindForm.setCriteriaList(mfKindForm, ajaxData);//我的筛选
			//this.getRoleConditions(mfKindForm,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfKindFormFeign.findByPage(ipage, mfKindForm);
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
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		try{
			FormData formkindform0002 = formService.getFormData("kindform0002");
			getFormValue(formkindform0002, getMapByJson(ajaxData));
			if(this.validateFormData(formkindform0002)){
				MfKindForm mfKindForm = new MfKindForm();
				setObjValue(formkindform0002, mfKindForm);
				mfKindFormFeign.insert(mfKindForm);
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
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formkindform0002 = formService.getFormData("kindform0002");
		getFormValue(formkindform0002, getMapByJson(ajaxData));
		MfKindForm mfKindFormJsp = new MfKindForm();
		setObjValue(formkindform0002, mfKindFormJsp);
		MfKindForm mfKindForm = mfKindFormFeign.getById(mfKindFormJsp);
		if(mfKindForm!=null){
			try{
				mfKindForm = (MfKindForm)EntityUtil.reflectionSetVal(mfKindForm, mfKindFormJsp, getMapByJson(ajaxData));
				mfKindFormFeign.update(mfKindForm);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
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
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		MfKindForm mfKindForm = new MfKindForm();
		try{
			FormData formkindform0002 = formService.getFormData("kindform0002");
			getFormValue(formkindform0002, getMapByJson(ajaxData));
			if(this.validateFormData(formkindform0002)){
				mfKindForm = new MfKindForm();
				setObjValue(formkindform0002, mfKindForm);
				mfKindFormFeign.update(mfKindForm);
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
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String kindFormId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formkindform0002 = formService.getFormData("kindform0002");
		MfKindForm mfKindForm = new MfKindForm();
		mfKindForm.setKindFormId(kindFormId);
		mfKindForm = mfKindFormFeign.getById(mfKindForm);
		getObjValue(formkindform0002, mfKindForm,formData);
		if(mfKindForm!=null){
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
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String kindFormId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindForm mfKindForm = new MfKindForm();
		mfKindForm.setKindFormId(kindFormId);
		try {
			mfKindFormFeign.delete(mfKindForm);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formkindform0002 = formService.getFormData("kindform0002");
		model.addAttribute("query", "");
		model.addAttribute("formkindflow0002",formkindform0002);
		return "/component/prdct/MfKindForm_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formkindform0002 = formService.getFormData("kindform0002");
		 getFormValue(formkindform0002);
		 MfKindForm mfKindForm = new MfKindForm();
		 setObjValue(formkindform0002, mfKindForm);
		 mfKindFormFeign.insert(mfKindForm);
		 getObjValue(formkindform0002, mfKindForm);
		 
		 this.addActionMessage(model,"保存成功");
		 List<MfKindForm> mfKindFormList = (List<MfKindForm>)mfKindFormFeign.findByPage(this.getIpage(), mfKindForm).getResult();
		 model.addAttribute("query", "");
		 model.addAttribute("formkindform0002",formkindform0002);
		 return "/component/prdct/MfKindForm_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String kindFormId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formkindform0001 = formService.getFormData("kindform0001");
		 getFormValue(formkindform0001);
		 MfKindForm mfKindForm = new MfKindForm();
		mfKindForm.setKindFormId(kindFormId);
		 mfKindForm = mfKindFormFeign.getById(mfKindForm);
		 getObjValue(formkindform0001, mfKindForm);
		 model.addAttribute("query", "");
		 model.addAttribute("formkindform0001",formkindform0001);
		return "/component/prdct/MfKindForm_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String kindFormId) throws Exception {
		ActionContext.initialize(request,response);
		MfKindForm mfKindForm = new MfKindForm();
		mfKindForm.setKindFormId(kindFormId);
		mfKindFormFeign.delete(mfKindForm);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formkindform0002 = formService.getFormData("kindform0002");
		 getFormValue(formkindform0002);
		 boolean validateFlag = this.validateFormData(formkindform0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formkindform0002 = formService.getFormData("kindform0002");
		 getFormValue(formkindform0002);
		 boolean validateFlag = this.validateFormData(formkindform0002);
	}
	
}
