package app.component.pms.controller;

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

import app.component.common.EntityUtil;
import app.component.pms.entity.PmsFilterRel;
import app.component.pms.feign.PmsFilterRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: PmsFilterRelAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Fri May 13 01:04:42 GMT 2016
 **/
@Controller
@RequestMapping("/pmsFilterRel")
public class PmsFilterRelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入PmsFilterRelBo
	@Autowired
	private PmsFilterRelFeign pmsFilterRelFeign;
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
		return "/component/pms/PmsFilterRel_List";
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
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsFilterRel pmsFilterRel = new PmsFilterRel();
		try {
			pmsFilterRel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pmsFilterRel.setCriteriaList(pmsFilterRel, ajaxData);// 我的筛选
			// this.getRoleConditions(pmsFilterRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("pmsFilterRel",pmsFilterRel));
			// 自定义查询Bo方法
			ipage = pmsFilterRelFeign.findByPage(ipage);
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

	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax(String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsFilterRel pmsFilterRel = new PmsFilterRel();
		FormData formpms1002 = formService.getFormData("pms1002");
		getObjValue(formpms1002, pmsFilterRel);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpms1002, "bigFormTag", query);
		dataMap.put("formHtml", formHtml);
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
			FormData formpms1002 = formService.getFormData("pms1002");
			getFormValue(formpms1002, getMapByJson(ajaxData));
			if (this.validateFormData(formpms1002)) {
				PmsFilterRel pmsFilterRel = new PmsFilterRel();
				setObjValue(formpms1002, pmsFilterRel);
				pmsFilterRelFeign.insert(pmsFilterRel);
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
		FormData formpms1002 = formService.getFormData("pms1002");
		getFormValue(formpms1002, getMapByJson(ajaxData));
		PmsFilterRel pmsFilterRelJsp = new PmsFilterRel();
		setObjValue(formpms1002, pmsFilterRelJsp);
		PmsFilterRel pmsFilterRel = pmsFilterRelFeign.getById(pmsFilterRelJsp);
		if (pmsFilterRel != null) {
			try {
				pmsFilterRel = (PmsFilterRel) EntityUtil.reflectionSetVal(pmsFilterRel, pmsFilterRelJsp,
						getMapByJson(ajaxData));
				pmsFilterRelFeign.update(pmsFilterRel);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	@RequestMapping(value = "/updateInputAjax")
	@ResponseBody
	public Map<String, Object> updateInputAjax(String relNo,String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpms1003 = formService.getFormData("pms1003");
		PmsFilterRel pmsFilterRel = new PmsFilterRel();
		pmsFilterRel.setRelNo(relNo);
		pmsFilterRel = pmsFilterRelFeign.getById(pmsFilterRel);
		getObjValue(formpms1003, pmsFilterRel, formData);
		if (pmsFilterRel != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpms1003, "bigFormTag", query);
		dataMap.put("formHtml", formHtml);
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
		PmsFilterRel pmsFilterRel = new PmsFilterRel();
		try {
			FormData formpms1003 = formService.getFormData("pms1003");
			getFormValue(formpms1003, getMapByJson(ajaxData));
			if (this.validateFormData(formpms1003)) {
				pmsFilterRel = new PmsFilterRel();
				setObjValue(formpms1003, pmsFilterRel);
				pmsFilterRelFeign.update(pmsFilterRel);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String relNo,String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpms1002 = formService.getFormData("pms1002");
		PmsFilterRel pmsFilterRel = new PmsFilterRel();
		pmsFilterRel.setRelNo(relNo);
		pmsFilterRel = pmsFilterRelFeign.getById(pmsFilterRel);
		getObjValue(formpms1002, pmsFilterRel, formData);
		if (pmsFilterRel != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpms1002, "bigFormTag", query);
		dataMap.put("formHtml", formHtml);
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
	public Map<String, Object> deleteAjax(String relNo) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsFilterRel pmsFilterRel = new PmsFilterRel();
		pmsFilterRel.setRelNo(relNo);
		try {
			pmsFilterRelFeign.delete(pmsFilterRel);
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
		FormData formpms1002 = formService.getFormData("pms1002");
		model.addAttribute("formpms1002", formpms1002);
		model.addAttribute("query", "");
		return "/component/pms/PmsFilterRel_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpms1002 = formService.getFormData("pms1002");
		getFormValue(formpms1002);
		PmsFilterRel pmsFilterRel = new PmsFilterRel();
		setObjValue(formpms1002, pmsFilterRel);
		pmsFilterRelFeign.insert(pmsFilterRel);
		getObjValue(formpms1002, pmsFilterRel);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pmsFilterRel",pmsFilterRel));
		List<PmsFilterRel> pmsFilterRelList = (List<PmsFilterRel>) pmsFilterRelFeign.findByPage(ipage).getResult();
		model.addAttribute("formpms1002", formpms1002);
		model.addAttribute("pmsFilterRelList", pmsFilterRelList);
		model.addAttribute("query", "");
		return "/component/pms/PmsFilterRel_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpms1001 = formService.getFormData("pms1001");
		getFormValue(formpms1001);
		PmsFilterRel pmsFilterRel = new PmsFilterRel();
		pmsFilterRel.setRelNo(relNo);
		pmsFilterRel = pmsFilterRelFeign.getById(pmsFilterRel);
		getObjValue(formpms1001, pmsFilterRel);
		model.addAttribute("formpms1001", formpms1001);
		model.addAttribute("query", "");
		return "/component/pms/PmsFilterRel_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String relNo) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		PmsFilterRel pmsFilterRel = new PmsFilterRel();
		pmsFilterRel.setRelNo(relNo);
		pmsFilterRelFeign.delete(pmsFilterRel);
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
		FormData formpms1002 = formService.getFormData("pms1002");
		getFormValue(formpms1002);
		boolean validateFlag = this.validateFormData(formpms1002);
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
		FormData formpms1002 = formService.getFormData("pms1002");
		getFormValue(formpms1002);
		boolean validateFlag = this.validateFormData(formpms1002);
	}

}
