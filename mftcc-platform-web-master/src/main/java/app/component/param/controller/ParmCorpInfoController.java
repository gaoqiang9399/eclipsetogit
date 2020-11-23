package app.component.param.controller;

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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.param.entity.ParmCorpInfo;
import app.component.param.feign.ParmCorpInfoFeign;
import app.util.toolkit.Ipage;

/**
 * Title: ParmCorpInfoAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Aug 11 05:40:43 GMT 2016
 **/
@Controller
@RequestMapping("/parmCorpInfo")
public class ParmCorpInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入ParmCorpInfoBo
	@Autowired
	private ParmCorpInfoFeign parmCorpInfoFeign;

	// 全局变量
	// 异步参数
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/param/ParmCorpInfo_List";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
		try {
			parmCorpInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			parmCorpInfo.setCriteriaList(parmCorpInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(parmCorpInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = parmCorpInfoFeign.findByPage(ipage, parmCorpInfo);
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
			FormData formparam0004 = formService.getFormData("param0004");
			getFormValue(formparam0004, getMapByJson(ajaxData));
			if (this.validateFormData(formparam0004)) {
				ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
				setObjValue(formparam0004, parmCorpInfo);
				parmCorpInfoFeign.insert(parmCorpInfo);
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formparam0004 = formService.getFormData("param0004");
		getFormValue(formparam0004, getMapByJson(ajaxData));
		ParmCorpInfo parmCorpInfoJsp = new ParmCorpInfo();
		setObjValue(formparam0004, parmCorpInfoJsp);
		ParmCorpInfo parmCorpInfo = parmCorpInfoFeign.getById(parmCorpInfoJsp);
		if (parmCorpInfo != null) {
			try {
				parmCorpInfo = (ParmCorpInfo) EntityUtil.reflectionSetVal(parmCorpInfo, parmCorpInfoJsp,
						getMapByJson(ajaxData));
				parmCorpInfoFeign.update(parmCorpInfo);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formparam0004 = formService.getFormData("param0004");
			getFormValue(formparam0004, getMapByJson(ajaxData));
			if (this.validateFormData(formparam0004)) {
				ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
				setObjValue(formparam0004, parmCorpInfo);
				parmCorpInfoFeign.update(parmCorpInfo);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String corpNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formparam0004 = formService.getFormData("param0004");
		ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
		parmCorpInfo.setCorpNo(corpNo);
		parmCorpInfo = parmCorpInfoFeign.getById(parmCorpInfo);
		getObjValue(formparam0004, parmCorpInfo, formData);
		if (parmCorpInfo != null) {
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String corpNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
		parmCorpInfo.setCorpNo(corpNo);
		try {
			parmCorpInfoFeign.delete(parmCorpInfo);
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formparam0004 = formService.getFormData("param0004");
		ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
		parmCorpInfo.setOrgName(User.getOrgName(getHttpRequest()));
		parmCorpInfo.setRegName(User.getRegName(getHttpRequest()));
		parmCorpInfo.setRegDate(User.getSysDate(getHttpRequest()));
		parmCorpInfo.setLstDate(User.getSysDate(getHttpRequest()));
		getObjValue(formparam0004, parmCorpInfo);
		model.addAttribute("formparam0004", formparam0004);
		model.addAttribute("query", "");
		return "/component/param/ParmCorpInfo_Insert";
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
		FormData formparam0004 = formService.getFormData("param0004");
		getFormValue(formparam0004);
		ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
		setObjValue(formparam0004, parmCorpInfo);
		parmCorpInfoFeign.insert(parmCorpInfo);
		getObjValue(formparam0004, parmCorpInfo);
		this.addActionMessage(model, "保存成功");
		List<ParmCorpInfo> parmCorpInfoList = (List<ParmCorpInfo>) parmCorpInfoFeign.findByPage(this.getIpage(), parmCorpInfo).getResult();
		model.addAttribute("parmCorpInfoList", parmCorpInfoList);
		model.addAttribute("formparam0004", formparam0004);
		model.addAttribute("query", "");
		return "/component/param/ParmCorpInfo_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String corpNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formparam0004 = formService.getFormData("param0004");
		getFormValue(formparam0004);
		ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
		parmCorpInfo.setCorpNo(corpNo);
		parmCorpInfo = parmCorpInfoFeign.getById(parmCorpInfo);
		getObjValue(formparam0004, parmCorpInfo);
		model.addAttribute("parmCorpInfo", parmCorpInfo);
		model.addAttribute("formparam0004", formparam0004);
		model.addAttribute("query", "");
		return "/component/param/ParmCorpInfo_Detail";
	}

	/**
	 * 修改页面
	 */
	@RequestMapping(value = "/getUpdatePage")
	public String getUpdatePage(Model model, String corpNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formparam0004 = formService.getFormData("param0004");
		getFormValue(formparam0004);
		ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
		parmCorpInfo.setCorpNo(corpNo);
		parmCorpInfo = parmCorpInfoFeign.getById(parmCorpInfo);
		getObjValue(formparam0004, corpNo);
		model.addAttribute("corpNo", corpNo);
		model.addAttribute("formparam0004", formparam0004);
		model.addAttribute("query", "");
		return "/component/param/ParmCorpInfo_Update";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	public String update(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formparam0004 = formService.getFormData("param0004");
		getFormValue(formparam0004);
		ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
		setObjValue(formparam0004, parmCorpInfo);
		parmCorpInfoFeign.update(parmCorpInfo);
		this.addActionMessage(model, "更新成功");
		getObjValue(formparam0004, parmCorpInfo);
		model.addAttribute("parmCorpInfo", parmCorpInfo);
		model.addAttribute("formparam0004", formparam0004);
		model.addAttribute("query", "");
		return "/component/param/ParmCorpInfo_Update";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String corpNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		ParmCorpInfo parmCorpInfo = new ParmCorpInfo();
		parmCorpInfo.setCorpNo(corpNo);
		parmCorpInfoFeign.delete(parmCorpInfo);
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
		FormData formparam0004 = formService.getFormData("param0004");
		getFormValue(formparam0004);
		boolean validateFlag = this.validateFormData(formparam0004);
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
		FormData formparam0004 = formService.getFormData("param0004");
		getFormValue(formparam0004);
		boolean validateFlag = this.validateFormData(formparam0004);
	}


}
