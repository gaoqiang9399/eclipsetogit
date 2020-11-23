package  app.component.examine.controller;
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
import app.component.examine.entity.MfExamineDocTemplate;
import app.component.examine.feign.MfExamineDocTemplateFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfExamineDocTemplateAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 03 10:56:54 CST 2017
 **/
@Controller
@RequestMapping(value="/mfExamineDocTemplate")
public class MfExamineDocTemplateController extends BaseFormBean{
	//注入MfExamineDocTemplateBo
	@Autowired
	private MfExamineDocTemplateFeign mfExamineDocTemplateFeign;
	//全局变量
	private Map<String,Object> dataMap;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "MfExamineDocTemplate_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findByPageAjax")
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType,Ipage ipage) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineDocTemplate mfExamineDocTemplate = new MfExamineDocTemplate();
		try {
			mfExamineDocTemplate.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineDocTemplate.setCriteriaList(mfExamineDocTemplate, ajaxData);//我的筛选
			//mfExamineDocTemplate.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfExamineDocTemplate,"1000000001");//记录级权限控制方法
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfExamineDocTemplate", mfExamineDocTemplate));
			ipage = mfExamineDocTemplateFeign.findByPage(ipage);
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
	 * 
	 * 方法描述： 获得检查模型下配置的模板
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-8-6 下午11:38:47
	 */
	@ResponseBody
	@RequestMapping(value="/getExamDocTemplateListAjax")
	public Map<String,Object> getExamDocTemplateListAjax(String examineModelId) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineDocTemplate mfExamineDocTemplate = new MfExamineDocTemplate();
		try {
			mfExamineDocTemplate.setExamineModelId(examineModelId);
			List<MfExamineDocTemplate> mfExamineDocTemplateList = mfExamineDocTemplateFeign.getMfExamineDocTemplateList(mfExamineDocTemplate);
			if(mfExamineDocTemplateList!=null&&mfExamineDocTemplateList.size()>0){
				dataMap.put("flag","success");
				dataMap.put("docTemplateList",mfExamineDocTemplateList);
			}else{
				dataMap.put("flag","error");
			}
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
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String examineModelId,String templateNo) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>();
		try{
			MfExamineDocTemplate mfExamineDocTemplate=new MfExamineDocTemplate();
			mfExamineDocTemplate.setExamineModelId(examineModelId);
			mfExamineDocTemplate.setTemplateNo(templateNo);
			dataMap=mfExamineDocTemplateFeign.insert(mfExamineDocTemplate);
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
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
	@ResponseBody
	@RequestMapping(value="/updateAjaxByOne")
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formexaminedoc0002 = formService.getFormData("examinedoc0002");
		getFormValue(formexaminedoc0002, getMapByJson(ajaxData));
		MfExamineDocTemplate mfExamineDocTemplateJsp = new MfExamineDocTemplate();
		setObjValue(formexaminedoc0002, mfExamineDocTemplateJsp);
		MfExamineDocTemplate mfExamineDocTemplate = mfExamineDocTemplateFeign.getById(mfExamineDocTemplateJsp);
		if(mfExamineDocTemplate!=null){
			try{
				mfExamineDocTemplate = (MfExamineDocTemplate)EntityUtil.reflectionSetVal(mfExamineDocTemplate, mfExamineDocTemplateJsp, getMapByJson(ajaxData));
				mfExamineDocTemplateFeign.update(mfExamineDocTemplate);
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
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineDocTemplate mfExamineDocTemplate = new MfExamineDocTemplate();
		try{
			FormData formexaminedoc0002 = formService.getFormData("examinedoc0002");
			getFormValue(formexaminedoc0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexaminedoc0002)){
				mfExamineDocTemplate = new MfExamineDocTemplate();
				setObjValue(formexaminedoc0002, mfExamineDocTemplate);
				mfExamineDocTemplateFeign.update(mfExamineDocTemplate);
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
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexaminedoc0002 = formService.getFormData("examinedoc0002");
		MfExamineDocTemplate mfExamineDocTemplate = new MfExamineDocTemplate();
		mfExamineDocTemplate.setId(id);
		mfExamineDocTemplate = mfExamineDocTemplateFeign.getById(mfExamineDocTemplate);
		getObjValue(formexaminedoc0002, mfExamineDocTemplate,formData);
		if(mfExamineDocTemplate!=null){
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
	@RequestMapping(value="/deleteAjax")
	public Map<String,Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineDocTemplate mfExamineDocTemplate = new MfExamineDocTemplate();
		mfExamineDocTemplate.setId(id);
		try {
			mfExamineDocTemplateFeign.delete(mfExamineDocTemplate);
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
	@RequestMapping(value="/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexaminedoc0002 = formService.getFormData("examinedoc0002");
		model.addAttribute("formexaminedoc0002", formexaminedoc0002);
		return "MfExamineDocTemplate_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insert")
	public String insert(Model model,Ipage ipage) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formexaminedoc0002 = formService.getFormData("examinedoc0002");
		 getFormValue(formexaminedoc0002);
		 MfExamineDocTemplate mfExamineDocTemplate = new MfExamineDocTemplate();
		 setObjValue(formexaminedoc0002, mfExamineDocTemplate);
		 mfExamineDocTemplateFeign.insert(mfExamineDocTemplate);
		 getObjValue(formexaminedoc0002, mfExamineDocTemplate);
		 this.addActionMessage(model,"保存成功");
		 ipage.setParams(this.setIpageParams("mfExamineDocTemplate", mfExamineDocTemplate));
		 List<MfExamineDocTemplate> mfExamineDocTemplateList = (List<MfExamineDocTemplate>)mfExamineDocTemplateFeign.findByPage(ipage).getResult();
		 model.addAttribute("formexaminedoc0002", formexaminedoc0002);
		 model.addAttribute("mfExamineDocTemplateList", mfExamineDocTemplateList);
		return "MfExamineDocTemplate_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formexaminedoc0001 = formService.getFormData("examinedoc0001");
		 getFormValue(formexaminedoc0001);
		 MfExamineDocTemplate mfExamineDocTemplate = new MfExamineDocTemplate();
		mfExamineDocTemplate.setId(id);
		 mfExamineDocTemplate = mfExamineDocTemplateFeign.getById(mfExamineDocTemplate);
		 getObjValue(formexaminedoc0001, mfExamineDocTemplate);
		 model.addAttribute("formexaminedoc0001", formexaminedoc0001);
		return "MfExamineDocTemplate_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(String id) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamineDocTemplate mfExamineDocTemplate = new MfExamineDocTemplate();
		mfExamineDocTemplate.setId(id);
		mfExamineDocTemplateFeign.delete(mfExamineDocTemplate);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexaminedoc0002 = formService.getFormData("examinedoc0002");
		 getFormValue(formexaminedoc0002);
		 boolean validateFlag = this.validateFormData(formexaminedoc0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexaminedoc0002 = formService.getFormData("examinedoc0002");
		 getFormValue(formexaminedoc0002);
		 boolean validateFlag = this.validateFormData(formexaminedoc0002);
	}
	
	
}
