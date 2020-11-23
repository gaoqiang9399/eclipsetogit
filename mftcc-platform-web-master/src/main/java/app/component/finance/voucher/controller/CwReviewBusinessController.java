package app.component.finance.voucher.controller;

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
import app.component.finance.util.CWEnumBean;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import app.component.finance.voucher.entity.CwReviewBusiness;
import app.component.finance.voucher.feign.CwReviewBusinessFeign;
import app.component.prdct.entity.MfSysKind;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: CwReviewBusinessAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 21 11:46:05 CST 2017
 **/
@Controller
@RequestMapping("/cwReviewBusiness")
public class CwReviewBusinessController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwReviewBusinessFeign cwReviewBusinessFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormService formService = new FormService();

	public CwReviewBusinessController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		List<MfSysKind> sysKindList = cwReviewBusinessFeign.getKindList();
		JSONArray kindArray = new JSONArray();
		if (sysKindList != null && sysKindList.size() > 0) {
			for (int i = 0; i < sysKindList.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("optName", sysKindList.get(i).getKindName());
				json.put("optCode", sysKindList.get(i).getKindNo());
				kindArray.add(json);
			}
		}

		Map<String, String> map = CWEnumBean.XD_SERVICE_DATA.getMap();
		// JSONArray jArray0 = JSONArray.fromObject(map);
		// JSONObject json0 = null;
		JSONArray jArray0 = new JSONArray();
		if (!map.isEmpty()) {
			for (String key : map.keySet()) {
				JSONObject json0 = new JSONObject();
				json0.put("optName", map.get(key));
				json0.put("optCode", key);
				jArray0.add(json0);
			}
		}
		model.addAttribute("jsonKind", kindArray);
		model.addAttribute("jsonProType", jArray0);
		return "/component/finance/voucher/CwReviewBusiness_List";
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
		CwReviewBusiness cwReviewBusiness = new CwReviewBusiness();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwReviewBusiness.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwReviewBusiness.setCustomSorts(ajaxData);// 自定义排序参数赋值
			cwReviewBusiness.setCriteriaList(cwReviewBusiness, ajaxData);// 我的筛选
			// this.getRoleConditions(cwCashierAccount,"1000000001");//记录级权限控制方法
			/*
			 * if (cwReviewBusiness.getCriteriaLists() == null ||
			 * cwReviewBusiness.getCriteriaLists().size() == 0) {
			 * cwReviewBusiness.setReviewState("0");//默认未复核 }
			 */
			// this.getRoleConditions(cwReviewBusiness,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwReviewBusiness", cwReviewBusiness);
			ipage.setParams(params);
			ipage = cwReviewBusinessFeign.findByPage(ipage,finBooks);
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
	 * 方法描述： 批量复核业务数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-5-3 下午5:20:13
	 */
	@RequestMapping(value = "/busBatchReviewAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> busBatchReviewAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwReviewBusinessFeign.doBusBatchReview(paramMap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				// dataMap.put("msg", "新增成功");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				if (!"0000".equals(r.getResult())) {
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage((String)r.getResult()));
				}
				
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
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
			FormData formreview0001 = formService.getFormData("review0001");
			getFormValue(formreview0001, getMapByJson(ajaxData));
			if (this.validateFormData(formreview0001)) {
				CwReviewBusiness cwReviewBusiness = new CwReviewBusiness();
				setObjValue(formreview0001, cwReviewBusiness);
				cwReviewBusinessFeign.insert(cwReviewBusiness);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "新增成功");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增失败");
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
		FormData formreview0001 = formService.getFormData("review0001");
		getFormValue(formreview0001, getMapByJson(ajaxData));
		CwReviewBusiness cwReviewBusinessJsp = new CwReviewBusiness();
		setObjValue(formreview0001, cwReviewBusinessJsp);
		CwReviewBusiness cwReviewBusiness = cwReviewBusinessFeign.getById(cwReviewBusinessJsp);
		if (cwReviewBusiness != null) {
			try {
				String finBooks = (String) request.getSession().getAttribute("finBooks");
				cwReviewBusiness = (CwReviewBusiness) EntityUtil.reflectionSetVal(cwReviewBusiness, cwReviewBusinessJsp,
						getMapByJson(ajaxData));
				cwReviewBusinessFeign.update(cwReviewBusiness,finBooks);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "保存成功");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				// dataMap.put("msg", "新增失败");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formreview0001 = formService.getFormData("review0001");
			getFormValue(formreview0001, getMapByJson(ajaxData));
			if (this.validateFormData(formreview0001)) {
				CwReviewBusiness cwReviewBusiness = new CwReviewBusiness();
				setObjValue(formreview0001, cwReviewBusiness);
				cwReviewBusinessFeign.update(cwReviewBusiness,finBooks);
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
	public Map<String, Object> getByIdAjax(String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formreview0001 = formService.getFormData("review0001");
		CwReviewBusiness cwReviewBusiness = new CwReviewBusiness();
		cwReviewBusiness.setTraceNo(traceNo);
		cwReviewBusiness = cwReviewBusinessFeign.getById(cwReviewBusiness);
		getObjValue(formreview0001, cwReviewBusiness, formData);
		if (cwReviewBusiness != null) {
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
	public Map<String, Object> deleteAjax(String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwReviewBusiness cwReviewBusiness = new CwReviewBusiness();
		cwReviewBusiness.setTraceNo(traceNo);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwReviewBusinessFeign.delete(cwReviewBusiness,finBooks);
			dataMap.put("flag", "success");
			// dataMap.put("msg", "成功");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			// dataMap.put("msg", "失败");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 查看业务记账规则是否有业务记账规则
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-2 上午11:30:39
	 */
	@RequestMapping(value = "/doCheckHaveRulesAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doCheckHaveRulesAjax(String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwReviewBusiness cwReviewBusiness = new CwReviewBusiness();
		cwReviewBusiness.setTraceNo(traceNo);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwReviewBusinessFeign.doCheckHaveRules(cwReviewBusiness,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_REVIEW_TONEXT.getMessage("已复核"));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
			e.printStackTrace();
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
		FormData formreview0001 = formService.getFormData("review0001");
		model.addAttribute("formreview0001", formreview0001);
		model.addAttribute("query", query);
		return "/component/finance/voucher/CwReviewBusiness_Insert";
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
		FormData formreview0001 = formService.getFormData("review0001");
		getFormValue(formreview0001);
		CwReviewBusiness cwReviewBusiness = new CwReviewBusiness();
		setObjValue(formreview0001, cwReviewBusiness);
		cwReviewBusinessFeign.insert(cwReviewBusiness);
		getObjValue(formreview0001, cwReviewBusiness);
//		this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		// 自定义查询Bo方法
		Map<String, Object> params = new HashMap<>();
		params.put("cwReviewBusiness", cwReviewBusiness);
		ipage.setParams(params);
		List<CwReviewBusiness> cwReviewBusinessList = (List<CwReviewBusiness>) cwReviewBusinessFeign
				.findByPage(ipage,finBooks).getResult();
		model.addAttribute("cwReviewBusiness", cwReviewBusiness);
		model.addAttribute("cwReviewBusinessList", cwReviewBusinessList);
		model.addAttribute("formreview0001", formreview0001);
		model.addAttribute("query", query);
		return "/component/finance/voucher/CwReviewBusiness_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormData formreview0001 = formService.getFormData("review0001");
		getFormValue(formreview0001);
		CwReviewBusiness cwReviewBusiness = new CwReviewBusiness();
		cwReviewBusiness.setTraceNo(traceNo);
		cwReviewBusiness = cwReviewBusinessFeign.getById(cwReviewBusiness);
		getObjValue(formreview0001, cwReviewBusiness);
		model.addAttribute("cwReviewBusiness", cwReviewBusiness);
		model.addAttribute("formreview0001", formreview0001);
		model.addAttribute("query", query);
		return "/component/finance/voucher/CwReviewBusiness_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwReviewBusiness cwReviewBusiness = new CwReviewBusiness();
		cwReviewBusiness.setTraceNo(traceNo);
		cwReviewBusinessFeign.delete(cwReviewBusiness,finBooks);
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
		ActionContext.initialize(request, response);
		FormData formreview0001 = formService.getFormData("review0001");
		getFormValue(formreview0001);
		boolean validateFlag = this.validateFormData(formreview0001);
		model.addAttribute("formreview0001", formreview0001);
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
		FormData formreview0001 = formService.getFormData("review0001");
		getFormValue(formreview0001);
		boolean validateFlag = this.validateFormData(formreview0001);
		model.addAttribute("formreview0001", formreview0001);
		model.addAttribute("query", query);
	}

}
