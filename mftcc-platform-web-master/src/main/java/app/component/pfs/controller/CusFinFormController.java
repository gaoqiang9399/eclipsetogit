package app.component.pfs.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.pfs.entity.CusFinForm;
import app.component.pfs.entity.CusFinModel;
import app.component.pfs.feign.CusFinFormFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CusFinFormAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Jun 01 07:24:53 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinForm")
public class CusFinFormController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CusFinFormBo
	@Autowired
	private CusFinFormFeign cusFinFormFeign;
	// 全局变量
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinForm_List";
	}

	@RequestMapping(value = "/getListPageForSelect")
	public String getListPageForSelect(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinForm_ListForSelect";
	}

	@RequestMapping(value = "/findByPageAjax1")
	@ResponseBody
	public Map<String, Object> findByPageAjax1(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String accRule) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinForm cusFinForm = new CusFinForm();
		try {
			cusFinForm.setAccRule(accRule);

			cusFinForm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cusFinForm.setCriteriaList(cusFinForm, ajaxData);// 我的筛选
			// this.getRoleConditions(cusFinForm,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("cusFinForm", cusFinForm));
			ipage = cusFinFormFeign.getForGradefindByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
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

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinForm cusFinForm = new CusFinForm();
		try {
			cusFinForm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cusFinForm.setCriteriaList(cusFinForm, ajaxData);// 我的筛选
			// this.getRoleConditions(cusFinForm,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("cusFinForm", cusFinForm));
			ipage = cusFinFormFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
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

	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinForm cusFinForm = new CusFinForm();
		FormData formpfs1002 = formService.getFormData("pfs1002");
		getObjValue(formpfs1002, cusFinForm);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs1002, "bigFormTag", query);
		dataMap.put("formHtml", formHtml);
		return dataMap;
	}

	@RequestMapping(value = "/toInsertAjax")
	@ResponseBody
	public Map<String, Object> toInsertAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs1002 = formService.getFormData("pfs1002");
			FormData formpfs2002 = formService.getFormData("pfs2002");
			getFormValue(formpfs1002, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs1002)) {
				CusFinForm cusFinForm = new CusFinForm();
				setObjValue(formpfs1002, cusFinForm);
				getObjValue(formpfs2002, cusFinForm);
				JsonFormUtil jfu = new JsonFormUtil();
				String formHtml = jfu.getJsonStr(formpfs2002, "bigFormTag", query);
				dataMap.put("formHtml", formHtml);
				List<CusFinModel> modelList = cusFinFormFeign.getCusFinModel(cusFinForm.getReportType(), cusFinForm.getAccRule());
				dataMap.put("modelList", modelList);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs2001 = formService.getFormData("pfs2001");
			getFormValue(formpfs2001, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs2001)) {
				CusFinForm cusFinForm = new CusFinForm();
				setObjValue(formpfs2001, cusFinForm);
				cusFinForm.setAccRule("1");
				CusFinForm cusFinFormTmp = new CusFinForm();
				cusFinFormTmp = cusFinFormFeign.getById(cusFinForm);
				if (cusFinFormTmp == null) {
					cusFinForm.setFuncName("commonformuladao");
					cusFinFormFeign.insert(cusFinForm);
					dataMap.put("cusFinForm", cusFinForm);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				} else {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("公式编号"));
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs2001 = formService.getFormData("pfs2001");
			getFormValue(formpfs2001, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs2001)) {
				CusFinForm cusFinForm = new CusFinForm();
				setObjValue(formpfs2001, cusFinForm);
				cusFinForm.setAccRule("1");
				cusFinForm.setFuncName("commonformuladao");
				cusFinFormFeign.update(cusFinForm);
				dataMap.put("cusFinForm", cusFinForm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * @param formNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String formNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			CusFinForm cusFinForm = new CusFinForm();
			// 因为在lease中有各种会计准则，factor系统中全部默认会计准则为1
			cusFinForm.setAccRule("1");
			cusFinForm.setFormNo(formNo);
			cusFinForm = cusFinFormFeign.getById(cusFinForm);
			dataMap.put("flag", "success");
			dataMap.put("cusFinForm", cusFinForm);
		} catch (Exception e) {
			dataMap.put("flag", "error");

		}

		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * @param accRule 
	 * @param formNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String accRule, String formNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinForm cusFinForm = new CusFinForm();
		cusFinForm.setAccRule(accRule);
		cusFinForm.setFormNo(formNo);
		try {
			cusFinFormFeign.delete(cusFinForm);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
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
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs2001 = formService.getFormData("pfs2001");
		model.addAttribute("formpfs2001", formpfs2001);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinForm_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs2001 = formService.getFormData("pfs2001");
		getFormValue(formpfs2001);
		CusFinForm cusFinForm = new CusFinForm();
		setObjValue(formpfs2001, cusFinForm);
		cusFinFormFeign.insert(cusFinForm);
		getObjValue(formpfs2001, cusFinForm);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("cusFinForm", cusFinForm));
		List<CusFinForm> cusFinFormList = (List<CusFinForm>) cusFinFormFeign.findByPage(ipage).getResult();
		model.addAttribute("formpfs2001", formpfs2001);
		model.addAttribute("cusFinFormList", cusFinFormList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinForm_Detail";
	}

	/**
	 * 查询
	 * @param accRule 
	 * @param formNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String accRule, String formNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs2001 = formService.getFormData("pfs2001");
		getFormValue(formpfs2001);
		CusFinForm cusFinForm = new CusFinForm();
		cusFinForm.setAccRule(accRule);
		cusFinForm.setFormNo(formNo);
		cusFinForm = cusFinFormFeign.getById(cusFinForm);
		getObjValue(formpfs2001, cusFinForm);
		model.addAttribute("formpfs2001", formpfs2001);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinForm_Detail";
	}

	/**
	 * 删除
	 * @param accRule 
	 * @param formNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String accRule, String formNo) throws Exception {
		ActionContext.initialize(request, response);
		CusFinForm cusFinForm = new CusFinForm();
		cusFinForm.setAccRule(accRule);
		cusFinForm.setFormNo(formNo);
		cusFinFormFeign.delete(cusFinForm);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs2001 = formService.getFormData("pfs2001");
		getFormValue(formpfs2001);
		boolean validateFlag = this.validateFormData(formpfs2001);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs2001 = formService.getFormData("pfs2001");
		getFormValue(formpfs2001);
		boolean validateFlag = this.validateFormData(formpfs2001);
	}


}
