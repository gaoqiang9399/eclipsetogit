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
import app.component.finance.paramset.entity.CwVchRuleDetail;
import app.component.finance.paramset.feign.CwVchRuleDetailFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwVchRuleDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 10 15:48:51 CST 2017
 **/
@Controller
@RequestMapping("/cwVchRuleDetail")
public class CwVchRuleDetailController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwVchRuleDetailFeign cwVchRuleDetailFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	public CwVchRuleDetailController() {
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
		return "/component/finance/paramset/CwVchRuleDetail_List";
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
		CwVchRuleDetail cwVchRuleDetail = new CwVchRuleDetail();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwVchRuleDetail.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwVchRuleDetail.setCriteriaList(cwVchRuleDetail, ajaxData);// 我的筛选
			// this.getRoleConditions(cwVchRuleDetail,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwVchRuleDetail", cwVchRuleDetail);
			ipage.setParams(params);
			ipage = cwVchRuleDetailFeign.findByPage(ipage,finBooks);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formcwvchruledetail0002 = formService.getFormData("cwvchruledetail0002");
			getFormValue(formcwvchruledetail0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwvchruledetail0002)) {
				CwVchRuleDetail cwVchRuleDetail = new CwVchRuleDetail();
				setObjValue(formcwvchruledetail0002, cwVchRuleDetail);
				cwVchRuleDetailFeign.insert(cwVchRuleDetail,finBooks);
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
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwvchruledetail0002 = formService.getFormData("cwvchruledetail0002");
		getFormValue(formcwvchruledetail0002, getMapByJson(ajaxData));
		CwVchRuleDetail cwVchRuleDetailJsp = new CwVchRuleDetail();
		setObjValue(formcwvchruledetail0002, cwVchRuleDetailJsp);
		CwVchRuleDetail cwVchRuleDetail = cwVchRuleDetailFeign.getById(cwVchRuleDetailJsp,finBooks);
		if (cwVchRuleDetail != null) {
			try {
				cwVchRuleDetail = (CwVchRuleDetail) EntityUtil.reflectionSetVal(cwVchRuleDetail, cwVchRuleDetailJsp,
						getMapByJson(ajaxData));
				cwVchRuleDetailFeign.update(cwVchRuleDetail,finBooks);
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
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formcwvchruledetail0002 = formService.getFormData("cwvchruledetail0002");
			getFormValue(formcwvchruledetail0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwvchruledetail0002)) {
				CwVchRuleDetail cwVchRuleDetail = new CwVchRuleDetail();
				setObjValue(formcwvchruledetail0002, cwVchRuleDetail);
				cwVchRuleDetailFeign.update(cwVchRuleDetail,finBooks);
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
	public Map<String, Object> getByIdAjax(String traceNo) throws Exception {
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwvchruledetail0002 = formService.getFormData("cwvchruledetail0002");
		CwVchRuleDetail cwVchRuleDetail = new CwVchRuleDetail();
		cwVchRuleDetail.setTraceNo(traceNo);
		cwVchRuleDetail = cwVchRuleDetailFeign.getById(cwVchRuleDetail,finBooks);
		getObjValue(formcwvchruledetail0002, cwVchRuleDetail, formData);
		if (cwVchRuleDetail != null) {
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
	public Map<String, Object> deleteAjax(String traceNo, String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchRuleDetail cwVchRuleDetail = new CwVchRuleDetail();
		cwVchRuleDetail.setTraceNo(traceNo);
		cwVchRuleDetail.setUuid(uuid);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwVchRuleDetailFeign.delete(cwVchRuleDetail,finBooks);
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
	 * 
	 * 方法描述： 根据uuid删除
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-4-20 上午11:55:25
	 */
	@RequestMapping(value = "/deletebyUuid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletebyUuid(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchRuleDetail cwVchRuleDetail = new CwVchRuleDetail();
		// cwVchRuleDetail.setTraceNo(traceNo);
		cwVchRuleDetail.setUuid(uuid);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwVchRuleDetailFeign.deletebyUuid(cwVchRuleDetail,finBooks);
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
		FormData formcwvchruledetail0002 = formService.getFormData("cwvchruledetail0002");
		model.addAttribute("formcwvchruledetail0002", formcwvchruledetail0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleDetail_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		FormData formcwvchruledetail0002 = formService.getFormData("cwvchruledetail0002");
		getFormValue(formcwvchruledetail0002);
		CwVchRuleDetail cwVchRuleDetail = new CwVchRuleDetail();
		setObjValue(formcwvchruledetail0002, cwVchRuleDetail);
		cwVchRuleDetailFeign.insert(cwVchRuleDetail,finBooks);
		getObjValue(formcwvchruledetail0002, cwVchRuleDetail);
//		this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwVchRuleDetail", cwVchRuleDetail);
		ipage.setParams(params);
		List<CwVchRuleDetail> cwVchRuleDetailList = (List<CwVchRuleDetail>) cwVchRuleDetailFeign.findByPage(ipage,finBooks)
				.getResult();
		model.addAttribute("cwVchRuleDetail", cwVchRuleDetail);
		model.addAttribute("cwVchRuleDetailList", cwVchRuleDetailList);
		model.addAttribute("formcwvchruledetail0002", formcwvchruledetail0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleDetail_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String traceNo) throws Exception {
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		FormData formcwvchruledetail0001 = formService.getFormData("cwvchruledetail0001");
		getFormValue(formcwvchruledetail0001);
		CwVchRuleDetail cwVchRuleDetail = new CwVchRuleDetail();
		cwVchRuleDetail.setTraceNo(traceNo);
		cwVchRuleDetail = cwVchRuleDetailFeign.getById(cwVchRuleDetail,finBooks);
		getObjValue(formcwvchruledetail0001, cwVchRuleDetail);
		model.addAttribute("cwVchRuleDetail", cwVchRuleDetail);
		model.addAttribute("formcwvchruledetail0001", formcwvchruledetail0001);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleDetail_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String traceNo) throws Exception {
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		CwVchRuleDetail cwVchRuleDetail = new CwVchRuleDetail();
		cwVchRuleDetail.setTraceNo(traceNo);
		cwVchRuleDetailFeign.delete(cwVchRuleDetail,finBooks);
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
		FormData formcwvchruledetail0002 = formService.getFormData("cwvchruledetail0002");
		getFormValue(formcwvchruledetail0002);
		boolean validateFlag = this.validateFormData(formcwvchruledetail0002);
		model.addAttribute("formcwvchruledetail0002", formcwvchruledetail0002);
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
		FormData formcwvchruledetail0002 = formService.getFormData("cwvchruledetail0002");
		getFormValue(formcwvchruledetail0002);
		boolean validateFlag = this.validateFormData(formcwvchruledetail0002);
		model.addAttribute("formcwvchruledetail0002", formcwvchruledetail0002);
		model.addAttribute("query", query);
	}

}
