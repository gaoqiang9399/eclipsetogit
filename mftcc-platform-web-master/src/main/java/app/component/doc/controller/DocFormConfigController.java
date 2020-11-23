package app.component.doc.controller;

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
import app.component.doc.entity.DocFormConfig;
import app.component.doc.feign.DocFormConfigFeign;
import app.util.toolkit.Ipage;

/**
 * Title: DocFormConfigController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon May 15 15:37:13 CST 2017
 **/
@Controller
@RequestMapping("/docFormConfig")
public class DocFormConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DocFormConfigFeign docFormConfigFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/doc/DocFormConfig_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocFormConfig docFormConfig = new DocFormConfig();
		try {
			docFormConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			docFormConfig.setCriteriaList(docFormConfig, ajaxData);// 我的筛选
			// docFormConfig.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(docFormConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = docFormConfigFeign.findByPage(ipage, docFormConfig);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdocform0002 = formService.getFormData("docform0002");
			getFormValue(formdocform0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdocform0002)) {
				DocFormConfig docFormConfig = new DocFormConfig();
				setObjValue(formdocform0002, docFormConfig);
				docFormConfigFeign.insert(docFormConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdocform0002 = formService.getFormData("docform0002");
		getFormValue(formdocform0002, getMapByJson(ajaxData));
		DocFormConfig docFormConfigJsp = new DocFormConfig();
		setObjValue(formdocform0002, docFormConfigJsp);
		DocFormConfig docFormConfig = docFormConfigFeign.getById(docFormConfigJsp);
		if (docFormConfig != null) {
			try {
				docFormConfig = (DocFormConfig) EntityUtil.reflectionSetVal(docFormConfig, docFormConfigJsp,
						getMapByJson(ajaxData));
				docFormConfigFeign.update(docFormConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdocform0002 = formService.getFormData("docform0002");
			getFormValue(formdocform0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdocform0002)) {
				DocFormConfig docFormConfig = new DocFormConfig();
				setObjValue(formdocform0002, docFormConfig);
				docFormConfigFeign.update(docFormConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody public Map<String, Object> getByIdAjax(String ajaxData, String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdocform0002 = formService.getFormData("docform0002");
		DocFormConfig docFormConfig = new DocFormConfig();
		docFormConfig.setId(id);
		docFormConfig = docFormConfigFeign.getById(docFormConfig);
		getObjValue(formdocform0002, docFormConfig, formData);
		if (docFormConfig != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocFormConfig docFormConfig = new DocFormConfig();
		docFormConfig.setId(id);
		try {
			docFormConfigFeign.delete(docFormConfig);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdocform0002 = formService.getFormData("docform0002");
		model.addAttribute("formdocform0002", formdocform0002);
		model.addAttribute("query", "");
		return "/component/doc/DocFormConfig_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdocform0002 = formService.getFormData("docform0002");
		getFormValue(formdocform0002);
		DocFormConfig docFormConfig = new DocFormConfig();
		setObjValue(formdocform0002, docFormConfig);
		docFormConfigFeign.insert(docFormConfig);
		getObjValue(formdocform0002, docFormConfig);
		this.addActionMessage(model, "保存成功");
		@SuppressWarnings("unchecked")
		List<DocFormConfig> docFormConfigList = (List<DocFormConfig>) docFormConfigFeign
				.findByPage(this.getIpage(), docFormConfig).getResult();
		model.addAttribute("docFormConfigList", docFormConfigList);
		model.addAttribute("formdocform0002", formdocform0002);
		model.addAttribute("query", "");
		return "/component/doc/DocFormConfig_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdocform0001 = formService.getFormData("docform0001");
		getFormValue(formdocform0001);
		DocFormConfig docFormConfig = new DocFormConfig();
		docFormConfig.setId(id);
		docFormConfig = docFormConfigFeign.getById(docFormConfig);
		getObjValue(formdocform0001, docFormConfig);
		model.addAttribute("formdocform0001", formdocform0001);
		model.addAttribute("query", "");
		return "/component/doc/DocFormConfig_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String id) throws Exception {
		ActionContext.initialize(request, response);
		DocFormConfig docFormConfig = new DocFormConfig();
		docFormConfig.setId(id);
		docFormConfigFeign.delete(docFormConfig);
		return getListPage();
	}

}
