package app.component.pact.controller;

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
import app.component.pact.entity.MfRecoverPactAmtHistory;
import app.component.pact.feign.MfRecoverPactAmtHistoryFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfRecoverPactAmtHistoryAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Jul 23 15:21:36 CST 2017
 **/
@Controller
@RequestMapping("/mfRecoverPactAmtHistory")
public class MfRecoverPactAmtHistoryController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfRecoverPactAmtHistoryBo
	@Autowired
	private MfRecoverPactAmtHistoryFeign mfRecoverPactAmtHistoryFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "MfRecoverPactAmtHistory_List";
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
		MfRecoverPactAmtHistory mfRecoverPactAmtHistory = new MfRecoverPactAmtHistory();
		try {
			mfRecoverPactAmtHistory.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfRecoverPactAmtHistory.setCriteriaList(mfRecoverPactAmtHistory, ajaxData);// 我的筛选
			// mfRecoverPactAmtHistory.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfRecoverPactAmtHistory,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfRecoverPactAmtHistoryFeign.findByPage(ipage, mfRecoverPactAmtHistory);
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
			FormData formrecoverpactamt0002 = formService.getFormData("recoverpactamt0002");
			getFormValue(formrecoverpactamt0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrecoverpactamt0002)) {
				MfRecoverPactAmtHistory mfRecoverPactAmtHistory = new MfRecoverPactAmtHistory();
				setObjValue(formrecoverpactamt0002, mfRecoverPactAmtHistory);
				mfRecoverPactAmtHistoryFeign.insert(mfRecoverPactAmtHistory);
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrecoverpactamt0002 = formService.getFormData("recoverpactamt0002");
		getFormValue(formrecoverpactamt0002, getMapByJson(ajaxData));
		MfRecoverPactAmtHistory mfRecoverPactAmtHistoryJsp = new MfRecoverPactAmtHistory();
		setObjValue(formrecoverpactamt0002, mfRecoverPactAmtHistoryJsp);
		MfRecoverPactAmtHistory mfRecoverPactAmtHistory = mfRecoverPactAmtHistoryFeign.getById(mfRecoverPactAmtHistoryJsp);
		if (mfRecoverPactAmtHistory != null) {
			try {
				mfRecoverPactAmtHistory = (MfRecoverPactAmtHistory) EntityUtil.reflectionSetVal(mfRecoverPactAmtHistory,
						mfRecoverPactAmtHistoryJsp, getMapByJson(ajaxData));
				mfRecoverPactAmtHistoryFeign.update(mfRecoverPactAmtHistory);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrecoverpactamt0002 = formService.getFormData("recoverpactamt0002");
			getFormValue(formrecoverpactamt0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrecoverpactamt0002)) {
				MfRecoverPactAmtHistory mfRecoverPactAmtHistory = new MfRecoverPactAmtHistory();
				setObjValue(formrecoverpactamt0002, mfRecoverPactAmtHistory);
				mfRecoverPactAmtHistoryFeign.update(mfRecoverPactAmtHistory);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String recoverHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrecoverpactamt0002 = formService.getFormData("recoverpactamt0002");
		MfRecoverPactAmtHistory mfRecoverPactAmtHistory = new MfRecoverPactAmtHistory();
		mfRecoverPactAmtHistory.setRecoverHisId(recoverHisId);
		mfRecoverPactAmtHistory = mfRecoverPactAmtHistoryFeign.getById(mfRecoverPactAmtHistory);
		getObjValue(formrecoverpactamt0002, mfRecoverPactAmtHistory, formData);
		if (mfRecoverPactAmtHistory != null) {
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
	public Map<String, Object> deleteAjax(String recoverHisId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRecoverPactAmtHistory mfRecoverPactAmtHistory = new MfRecoverPactAmtHistory();
		mfRecoverPactAmtHistory.setRecoverHisId(recoverHisId);
		try {
			mfRecoverPactAmtHistoryFeign.delete(mfRecoverPactAmtHistory);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrecoverpactamt0002 = formService.getFormData("recoverpactamt0002");
		model.addAttribute("formrecoverpactamt0002", formrecoverpactamt0002);
		model.addAttribute("query", "");
		return "MfRecoverPactAmtHistory_Insert";
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
		FormData formrecoverpactamt0002 = formService.getFormData("recoverpactamt0002");
		getFormValue(formrecoverpactamt0002);
		MfRecoverPactAmtHistory mfRecoverPactAmtHistory = new MfRecoverPactAmtHistory();
		setObjValue(formrecoverpactamt0002, mfRecoverPactAmtHistory);
		mfRecoverPactAmtHistoryFeign.insert(mfRecoverPactAmtHistory);
		getObjValue(formrecoverpactamt0002, mfRecoverPactAmtHistory);
		this.addActionMessage(model, "保存成功");
		List<MfRecoverPactAmtHistory> mfRecoverPactAmtHistoryList = (List<MfRecoverPactAmtHistory>) mfRecoverPactAmtHistoryFeign
				.findByPage(this.getIpage(), mfRecoverPactAmtHistory).getResult();
		model.addAttribute("mfRecoverPactAmtHistoryList", mfRecoverPactAmtHistoryList);
		model.addAttribute("formrecoverpactamt0002", formrecoverpactamt0002);
		model.addAttribute("query", "");
		return "MfRecoverPactAmtHistory_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String recoverHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrecoverpactamt0002 = formService.getFormData("recoverpactamt0001");
		getFormValue(formrecoverpactamt0002);
		MfRecoverPactAmtHistory mfRecoverPactAmtHistory = new MfRecoverPactAmtHistory();
		mfRecoverPactAmtHistory.setRecoverHisId(recoverHisId);
		mfRecoverPactAmtHistory = mfRecoverPactAmtHistoryFeign.getById(mfRecoverPactAmtHistory);
		getObjValue(formrecoverpactamt0002, mfRecoverPactAmtHistory);
		model.addAttribute("mfRecoverPactAmtHistory", mfRecoverPactAmtHistory);
		model.addAttribute("formrecoverpactamt0002", formrecoverpactamt0002);
		model.addAttribute("query", "");
		return "MfRecoverPactAmtHistory_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String recoverHisId) throws Exception {
		ActionContext.initialize(request, response);
		MfRecoverPactAmtHistory mfRecoverPactAmtHistory = new MfRecoverPactAmtHistory();
		mfRecoverPactAmtHistory.setRecoverHisId(recoverHisId);
		mfRecoverPactAmtHistoryFeign.delete(mfRecoverPactAmtHistory);
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
		FormData formrecoverpactamt0002 = formService.getFormData("recoverpactamt0002");
		getFormValue(formrecoverpactamt0002);
		boolean validateFlag = this.validateFormData(formrecoverpactamt0002);
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
		FormData formrecoverpactamt0002 = formService.getFormData("recoverpactamt0002");
		getFormValue(formrecoverpactamt0002);
		boolean validateFlag = this.validateFormData(formrecoverpactamt0002);
	}

}
