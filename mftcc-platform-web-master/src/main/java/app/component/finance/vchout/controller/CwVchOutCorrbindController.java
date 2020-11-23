package app.component.finance.vchout.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
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
import app.component.finance.vchout.entity.CwVchOutCorrbind;
import app.component.finance.vchout.feign.CwVchOutCorrbindFeign;
import app.util.toolkit.Ipage;

/**
 * Title: CwVchOutCorrbindAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 18 17:40:34 CST 2017
 **/
@Controller
@RequestMapping("/cwVchOutCorrbind")
public class CwVchOutCorrbindController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwVchOutCorrbindFeign cwVchOutCorrbindFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormData formvchout0001;
	private FormData formvchout0002;
	private FormService formService = new FormService();

	public CwVchOutCorrbindController() {
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
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwVchOutCorrbindFeign.checkCorrTypeCounts(finBooks);
		return "/component/finance/vchout/CwVchOutCorrbind_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchOutCorrbind cwVchOutCorrbind = new CwVchOutCorrbind();
		try {
			cwVchOutCorrbind.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwVchOutCorrbind.setCriteriaList(cwVchOutCorrbind, ajaxData);// 我的筛选
			if (cwVchOutCorrbind.getCriteriaLists() == null || cwVchOutCorrbind.getCriteriaLists().size() == 0) {
				cwVchOutCorrbind.setCorrType("9");// 默认科目列表
			}
			// cwVchOutCorrbind.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(cwVchOutCorrbind,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Feign方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwVchOutCorrbind", cwVchOutCorrbind);
			ipage.setParams(params);
			ipage = cwVchOutCorrbindFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX更新对应关系保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCorrBindAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCorrBindAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchOutCorrbind cwVchOutCorrbind = new CwVchOutCorrbind();
		try {
			PropertyUtils.copyProperties(cwVchOutCorrbind, getMapByJson(ajaxData));
			cwVchOutCorrbindFeign.update(cwVchOutCorrbind);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
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
			formvchout0002 = formService.getFormData("vchout0002");
			getFormValue(formvchout0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvchout0002)) {
				CwVchOutCorrbind cwVchOutCorrbind = new CwVchOutCorrbind();
				setObjValue(formvchout0002, cwVchOutCorrbind);
				cwVchOutCorrbindFeign.insert(cwVchOutCorrbind);
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
			throw new Exception(e.getMessage());
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
		formvchout0002 = formService.getFormData("vchout0002");
		getFormValue(formvchout0002, getMapByJson(ajaxData));
		CwVchOutCorrbind cwVchOutCorrbindJsp = new CwVchOutCorrbind();
		setObjValue(formvchout0002, cwVchOutCorrbindJsp);
		CwVchOutCorrbind cwVchOutCorrbind = cwVchOutCorrbindFeign.getById(cwVchOutCorrbindJsp);
		if (cwVchOutCorrbind != null) {
			try {
				cwVchOutCorrbind = (CwVchOutCorrbind) EntityUtil.reflectionSetVal(cwVchOutCorrbind, cwVchOutCorrbindJsp,
						getMapByJson(ajaxData));
				cwVchOutCorrbindFeign.update(cwVchOutCorrbind);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formvchout0002 = formService.getFormData("vchout0002");
			getFormValue(formvchout0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvchout0002)) {
				CwVchOutCorrbind cwVchOutCorrbind = new CwVchOutCorrbind();
				setObjValue(formvchout0002, cwVchOutCorrbind);
				cwVchOutCorrbindFeign.update(cwVchOutCorrbind);
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
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String corrId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formvchout0002 = formService.getFormData("vchout0002");
		CwVchOutCorrbind cwVchOutCorrbind = new CwVchOutCorrbind();
		cwVchOutCorrbind.setCorrId(corrId);
		cwVchOutCorrbind = cwVchOutCorrbindFeign.getById(cwVchOutCorrbind);
		getObjValue(formvchout0002, cwVchOutCorrbind, formData);
		if (cwVchOutCorrbind != null) {
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
	public Map<String, Object> deleteAjax(String corrId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchOutCorrbind cwVchOutCorrbind = new CwVchOutCorrbind();
		cwVchOutCorrbind.setCorrId(corrId);
		try {
			cwVchOutCorrbindFeign.delete(cwVchOutCorrbind);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formvchout0002 = formService.getFormData("vchout0002");
		model.addAttribute("formvchout0002", formvchout0002);
		model.addAttribute("query", query);
		return "/component/finance/vchout/CwVchOutCorrbind_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String corrId) throws Exception {
		ActionContext.initialize(request, response);
		formvchout0001 = formService.getFormData("vchout0001");
		getFormValue(formvchout0001);
		CwVchOutCorrbind cwVchOutCorrbind = new CwVchOutCorrbind();
		cwVchOutCorrbind.setCorrId(corrId);
		cwVchOutCorrbind = cwVchOutCorrbindFeign.getById(cwVchOutCorrbind);
		getObjValue(formvchout0001, cwVchOutCorrbind);
		model.addAttribute("formvchout0002", formvchout0002);
		model.addAttribute("cwVchOutCorrbind", cwVchOutCorrbind);
		model.addAttribute("query", query);
		return "/component/finance/vchout/CwVchOutCorrbind_Detail";
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
		formvchout0002 = formService.getFormData("vchout0002");
		getFormValue(formvchout0002);
		Boolean validateFlag = this.validateFormData(formvchout0002);
		model.addAttribute("formvchout0002", formvchout0002);
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
		formvchout0002 = formService.getFormData("vchout0002");
		getFormValue(formvchout0002);
		Boolean validateFlag = this.validateFormData(formvchout0002);
		model.addAttribute("formvchout0002", formvchout0002);
		model.addAttribute("query", query);
	}

}
