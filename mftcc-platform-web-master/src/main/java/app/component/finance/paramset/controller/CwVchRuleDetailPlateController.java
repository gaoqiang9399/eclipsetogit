package app.component.finance.paramset.controller;

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
import app.component.finance.paramset.entity.CwVchRuleDetailPlate;
import app.component.finance.paramset.feign.CwVchRuleDetailPlateFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwVchRuleDetailPlateAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 09 20:13:26 CST 2017
 **/
@Controller
@RequestMapping("/cwVchRuleDetailPlate")
public class CwVchRuleDetailPlateController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwVchRuleDetailPlateFeign cwVchRuleDetailPlateFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	public CwVchRuleDetailPlateController() {
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
		return "/component/finance/paramset/CwVchRuleDetailPlate_List";
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
		CwVchRuleDetailPlate cwVchRuleDetailPlate = new CwVchRuleDetailPlate();
		try {
			cwVchRuleDetailPlate.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwVchRuleDetailPlate.setCriteriaList(cwVchRuleDetailPlate, ajaxData);// 我的筛选
			// this.getRoleConditions(cwVchRuleDetailPlate,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwVchRuleDetailPlate", cwVchRuleDetailPlate);
			ipage.setParams(params);
			ipage = cwVchRuleDetailPlateFeign.findByPage(ipage);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcwvchruledetailplate0002 = formService.getFormData("cwvchruledetailplate0002");
			getFormValue(formcwvchruledetailplate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwvchruledetailplate0002)) {
				CwVchRuleDetailPlate cwVchRuleDetailPlate = new CwVchRuleDetailPlate();
				setObjValue(formcwvchruledetailplate0002, cwVchRuleDetailPlate);
				cwVchRuleDetailPlateFeign.insert(cwVchRuleDetailPlate);
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
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwvchruledetailplate0002 = formService.getFormData("cwvchruledetailplate0002");
		getFormValue(formcwvchruledetailplate0002, getMapByJson(ajaxData));
		CwVchRuleDetailPlate cwVchRuleDetailPlateJsp = new CwVchRuleDetailPlate();
		setObjValue(formcwvchruledetailplate0002, cwVchRuleDetailPlateJsp);
		CwVchRuleDetailPlate cwVchRuleDetailPlate = cwVchRuleDetailPlateFeign.getById(cwVchRuleDetailPlateJsp);
		if (cwVchRuleDetailPlate != null) {
			try {
				cwVchRuleDetailPlate = (CwVchRuleDetailPlate) EntityUtil.reflectionSetVal(cwVchRuleDetailPlate,
						cwVchRuleDetailPlateJsp, getMapByJson(ajaxData));
				cwVchRuleDetailPlateFeign.update(cwVchRuleDetailPlate);
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
			FormData formcwvchruledetailplate0002 = formService.getFormData("cwvchruledetailplate0002");
			getFormValue(formcwvchruledetailplate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwvchruledetailplate0002)) {
				CwVchRuleDetailPlate cwVchRuleDetailPlate = new CwVchRuleDetailPlate();
				setObjValue(formcwvchruledetailplate0002, cwVchRuleDetailPlate);
				cwVchRuleDetailPlateFeign.update(cwVchRuleDetailPlate);
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwvchruledetailplate0002 = formService.getFormData("cwvchruledetailplate0002");
		CwVchRuleDetailPlate cwVchRuleDetailPlate = new CwVchRuleDetailPlate();
		cwVchRuleDetailPlate.setUuid(uuid);
		cwVchRuleDetailPlate = cwVchRuleDetailPlateFeign.getById(cwVchRuleDetailPlate);
		getObjValue(formcwvchruledetailplate0002, cwVchRuleDetailPlate, formData);
		if (cwVchRuleDetailPlate != null) {
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
		CwVchRuleDetailPlate cwVchRuleDetailPlate = new CwVchRuleDetailPlate();
		cwVchRuleDetailPlate.setUuid(uuid);
		try {
			cwVchRuleDetailPlateFeign.delete(cwVchRuleDetailPlate);
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
		ActionContext.initialize(request, response);
		FormData formcwvchruledetailplate0002 = formService.getFormData("cwvchruledetailplate0002");
		model.addAttribute("formcwvchruledetailplate0002",formcwvchruledetailplate0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleDetailPlate_Insert";
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
		FormData formcwvchruledetailplate0002 = formService.getFormData("cwvchruledetailplate0002");
		getFormValue(formcwvchruledetailplate0002);
		CwVchRuleDetailPlate cwVchRuleDetailPlate = new CwVchRuleDetailPlate();
		setObjValue(formcwvchruledetailplate0002, cwVchRuleDetailPlate);
		cwVchRuleDetailPlateFeign.insert(cwVchRuleDetailPlate);
		getObjValue(formcwvchruledetailplate0002, cwVchRuleDetailPlate);
//		this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		// 自定义查询Bo方法
		Map<String, Object> params = new HashMap<>();
		params.put("cwVchRuleDetailPlate", cwVchRuleDetailPlate);
		ipage.setParams(params);
		List<CwVchRuleDetailPlate> cwVchRuleDetailPlateList = (List<CwVchRuleDetailPlate>) cwVchRuleDetailPlateFeign
				.findByPage(ipage).getResult();
		model.addAttribute("cwVchRuleDetailPlate",cwVchRuleDetailPlate);
		model.addAttribute("cwVchRuleDetailPlateList",cwVchRuleDetailPlateList);
		model.addAttribute("formcwvchruledetailplate0002",formcwvchruledetailplate0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleDetailPlate_Insert";
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
		FormData formcwvchruledetailplate0001 = formService.getFormData("cwvchruledetailplate0001");
		getFormValue(formcwvchruledetailplate0001);
		CwVchRuleDetailPlate cwVchRuleDetailPlate = new CwVchRuleDetailPlate();
		cwVchRuleDetailPlate.setUuid(uuid);
		cwVchRuleDetailPlate = cwVchRuleDetailPlateFeign.getById(cwVchRuleDetailPlate);
		getObjValue(formcwvchruledetailplate0001, cwVchRuleDetailPlate);
		model.addAttribute("cwVchRuleDetailPlate",cwVchRuleDetailPlate);
		model.addAttribute("formcwvchruledetailplate0001",formcwvchruledetailplate0001);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleDetailPlate_Detail";
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
		CwVchRuleDetailPlate cwVchRuleDetailPlate = new CwVchRuleDetailPlate();
		cwVchRuleDetailPlate.setUuid(uuid);
		cwVchRuleDetailPlateFeign.delete(cwVchRuleDetailPlate);
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
		FormData formcwvchruledetailplate0002 = formService.getFormData("cwvchruledetailplate0002");
		getFormValue(formcwvchruledetailplate0002);
		boolean validateFlag = this.validateFormData(formcwvchruledetailplate0002);
		model.addAttribute("formcwvchruledetailplate0002",formcwvchruledetailplate0002);
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
		FormData formcwvchruledetailplate0002 = formService.getFormData("cwvchruledetailplate0002");
		getFormValue(formcwvchruledetailplate0002);
		boolean validateFlag = this.validateFormData(formcwvchruledetailplate0002);
		model.addAttribute("formcwvchruledetailplate0002",formcwvchruledetailplate0002);
		model.addAttribute("query", query);
	}

}
