package app.component.finance.assets.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.finance.assets.entity.CwAssetsClass;
import app.component.finance.assets.feign.CwAssetsClassFeign;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: CwAssetsClassAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 14 17:21:08 CST 2017
 **/
@Controller
@RequestMapping("/cwAssetsClass")
public class CwAssetsClassController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CwAssetsClassFeign
	@Autowired
	private CwAssetsClassFeign cwAssetsClassFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormData formassetsclass0001;
	private FormData formassetsclass0002;
	private FormService formService = new FormService();

	public CwAssetsClassController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/assets/CwAssetsClass_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwAssetsClass cwAssetsClass = new CwAssetsClass();
		try {
			cwAssetsClass.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwAssetsClass.setCriteriaList(cwAssetsClass, ajaxData);// 我的筛选
			// this.getRoleConditions(cwAssetsClass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("cwAssetsClass", cwAssetsClass);
			ipage.setParams(params);
			// 自定义查询Bo方法
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwAssetsClassFeign.findByPage(ipage,finBooks);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String uuid) throws Exception {
		ActionContext.initialize(request, response);
		formassetsclass0002 = formService.getFormData("assetsclass0002");
		if (StringUtil.isNotEmpty(uuid)) {
			getFormValue(formassetsclass0002);
			CwAssetsClass cwAssetsClass = new CwAssetsClass();
			cwAssetsClass.setUuid(uuid);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwAssetsClass = cwAssetsClassFeign.getById(cwAssetsClass,finBooks);
			getObjValue(formassetsclass0002, cwAssetsClass);
			model.addAttribute("cwAssetsClass", cwAssetsClass);
		}
		model.addAttribute("query", "");
		model.addAttribute("formassetsclass0002", formassetsclass0002);
		return "/component/finance/assets/CwAssetsClass_Insert";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formassetsclass0002 = formService.getFormData("assetsclass0002");
			getFormValue(formassetsclass0002, getMapByJson(ajaxData));
			if (this.validateFormData(formassetsclass0002)) {
				CwAssetsClass cwAssetsClass = new CwAssetsClass();
				setObjValue(formassetsclass0002, cwAssetsClass);
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwAssetsClassFeign.insert(cwAssetsClass,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("cwAssetsClass", cwAssetsClass);
					// dataMap.put("msg", "新增成功");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				// dataMap.put("msg",this.getFormulavaliErrorMsg());
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增失败");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			e.printStackTrace();
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
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formassetsclass0002 = formService.getFormData("assetsclass0002");
		getFormValue(formassetsclass0002, getMapByJson(ajaxData));
		CwAssetsClass cwAssetsClassJsp = new CwAssetsClass();
		setObjValue(formassetsclass0002, cwAssetsClassJsp);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		CwAssetsClass cwAssetsClass = cwAssetsClassFeign.getById(cwAssetsClassJsp,finBooks);
		if (cwAssetsClass != null) {
			try {
				cwAssetsClass = (CwAssetsClass) EntityUtil.reflectionSetVal(cwAssetsClass, cwAssetsClassJsp,
						getMapByJson(ajaxData));
				cwAssetsClassFeign.update(cwAssetsClass,finBooks);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "保存成功");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				// dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "编号不存在,保存失败");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formassetsclass0002 = formService.getFormData("assetsclass0002");
			getFormValue(formassetsclass0002, getMapByJson(ajaxData));
			if (this.validateFormData(formassetsclass0002)) {
				CwAssetsClass cwAssetsClass = new CwAssetsClass();
				setObjValue(formassetsclass0002, cwAssetsClass);
				// cwAssetsClass.setUuid(uuid);
				String finBooks = (String) request.getSession().getAttribute("finBooks");
				cwAssetsClassFeign.update(cwAssetsClass,finBooks);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "更新成功");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			// dataMap.put("msg", "更新失败");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formassetsclass0002 = formService.getFormData("assetsclass0002");
		CwAssetsClass cwAssetsClass = new CwAssetsClass();
		cwAssetsClass.setUuid(uuid);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwAssetsClass = cwAssetsClassFeign.getById(cwAssetsClass,finBooks);
		getObjValue(formassetsclass0002, cwAssetsClass, formData);
		if (cwAssetsClass != null) {
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
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwAssetsClass cwAssetsClass = new CwAssetsClass();
		cwAssetsClass.setUuid(uuid);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwAssetsClassFeign.delete(cwAssetsClass,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				// dataMap.put("msg", "成功");
				dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "失败");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formassetsclass0002 = formService.getFormData("assetsclass0002");
		getFormValue(formassetsclass0002);
		CwAssetsClass cwAssetsClass = new CwAssetsClass();
		setObjValue(formassetsclass0002, cwAssetsClass);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwAssetsClassFeign.insert(cwAssetsClass,finBooks);
		getObjValue(formassetsclass0002, cwAssetsClass);
//		this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwAssetsClass", cwAssetsClass);
		ipage.setParams(params);
		List<CwAssetsClass> cwAssetsClassList = (List<CwAssetsClass>) cwAssetsClassFeign.findByPage(ipage,finBooks)
				.getResult();
		model.addAttribute("cwAssetsClass", cwAssetsClass);
		model.addAttribute("formassetsclass0002", formassetsclass0002);
		model.addAttribute("cwAssetsClassList", cwAssetsClassList);
		model.addAttribute("query", query);
		return "/component/finance/assets/CwAssetsClass_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String uuid) throws Exception {
		ActionContext.initialize(request, response);
		formassetsclass0001 = formService.getFormData("assetsclass0001");
		getFormValue(formassetsclass0001);
		CwAssetsClass cwAssetsClass = new CwAssetsClass();
		cwAssetsClass.setUuid(uuid);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwAssetsClass = cwAssetsClassFeign.getById(cwAssetsClass,finBooks);
		getObjValue(formassetsclass0001, cwAssetsClass);
		model.addAttribute("cwAssetsClass", cwAssetsClass);
		model.addAttribute("formassetsclass0001", formassetsclass0001);
		model.addAttribute("query", query);
		return "/component/finance/assets/CwAssetsClass_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		CwAssetsClass cwAssetsClass = new CwAssetsClass();
		cwAssetsClass.setUuid(uuid);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwAssetsClassFeign.delete(cwAssetsClass,finBooks);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formassetsclass0002 = formService.getFormData("assetsclass0002");
		getFormValue(formassetsclass0002);
		boolean validateFlag = this.validateFormData(formassetsclass0002);
		model.addAttribute("formassetsclass0002", formassetsclass0002);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formassetsclass0002 = formService.getFormData("assetsclass0002");
		getFormValue(formassetsclass0002);
		boolean validateFlag = this.validateFormData(formassetsclass0002);
		model.addAttribute("formassetsclass0002", formassetsclass0002);
		model.addAttribute("query", query);
	}

}
