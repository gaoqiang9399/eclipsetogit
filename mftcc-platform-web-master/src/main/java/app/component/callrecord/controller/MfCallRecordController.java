package app.component.callrecord.controller;

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

import app.component.callrecord.entity.MfCallRecord;
import app.component.callrecord.feign.MfCallRecordFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * @类名： MfCallRecordController
 * 
 * @描述：通话记录
 * @author 仇招
 * @date 2018年9月15日 上午11:42:33
 */
@Controller
@RequestMapping("/mfCallRecord")
public class MfCallRecordController extends BaseFormBean {
	@Autowired
	private MfCallRecordFeign mfCallRecordFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/callrecord/MfCallRecord_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @param pageNo
	 * @param ajaxData
	 * @param tableId
	 * @param tableType
	 * @param pageSize
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public Map<String, Object> findByPageAjax(Integer pageNo, String ajaxData, String tableId, String tableType,
			Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCallRecord mfCallRecord = new MfCallRecord();
		try {
			mfCallRecord.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCallRecord.setCriteriaList(mfCallRecord, ajaxData);// 我的筛选
			mfCallRecord.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfCallRecord", mfCallRecord));
			// 自定义查询Bo方法
			ipage = mfCallRecordFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formCallRecordBase = formService.getFormData("CallRecordBase");
			getFormValue(formCallRecordBase, getMapByJson(ajaxData));
			if (this.validateFormData(formCallRecordBase)) {
				MfCallRecord mfCallRecord = new MfCallRecord();
				setObjValue(formCallRecordBase, mfCallRecord);
				mfCallRecordFeign.insert(mfCallRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formCallRecordBase = formService.getFormData("CallRecordBase");
		getFormValue(formCallRecordBase, getMapByJson(ajaxData));
		MfCallRecord mfCallRecordJsp = new MfCallRecord();
		setObjValue(formCallRecordBase, mfCallRecordJsp);
		MfCallRecord mfCallRecord = mfCallRecordFeign.getById(mfCallRecordJsp);
		if (mfCallRecord != null) {
			try {
				mfCallRecord = (MfCallRecord) EntityUtil.reflectionSetVal(mfCallRecord, mfCallRecordJsp,
						getMapByJson(ajaxData));
				mfCallRecordFeign.update(mfCallRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
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
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCallRecord mfCallRecord = new MfCallRecord();
		try {
			FormData formCallRecordBase = formService.getFormData("CallRecordBase");
			getFormValue(formCallRecordBase, getMapByJson(ajaxData));
			if (this.validateFormData(formCallRecordBase)) {
				mfCallRecord = new MfCallRecord();
				setObjValue(formCallRecordBase, mfCallRecord);
				mfCallRecordFeign.update(mfCallRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param callRecordId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String callRecordId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formCallRecordBase = formService.getFormData("CallRecordBase");
		MfCallRecord mfCallRecord = new MfCallRecord();
		mfCallRecord.setCallRecordId(callRecordId);
		mfCallRecord = mfCallRecordFeign.getById(mfCallRecord);
		getObjValue(formCallRecordBase, mfCallRecord, formData);
		if (mfCallRecord != null) {
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
	 * @param callRecordId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String callRecordId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCallRecord mfCallRecord = new MfCallRecord();
		mfCallRecord.setCallRecordId(callRecordId);
		try {
			mfCallRecordFeign.delete(mfCallRecord);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCallRecordBase = formService.getFormData("CallRecordBase");
		model.addAttribute("formCallRecordBase", formCallRecordBase);
		model.addAttribute("query", "");
		return "/component/callrecord/MfCallRecord_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param callRecordId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String callRecordId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCallRecordDetail = formService.getFormData("CallRecordDetail");
		getFormValue(formCallRecordDetail);
		MfCallRecord mfCallRecord = new MfCallRecord();
		mfCallRecord.setCallRecordId(callRecordId);
		mfCallRecord = mfCallRecordFeign.getById(mfCallRecord);
		getObjValue(formCallRecordDetail, mfCallRecord);
		model.addAttribute("formCallRecordDetail", formCallRecordDetail);
		model.addAttribute("query", "");
		return "/component/callrecord/MfCallRecord_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCallRecordBase = formService.getFormData("CallRecordBase");
		getFormValue(formCallRecordBase);
		boolean validateFlag = this.validateFormData(formCallRecordBase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCallRecordBase = formService.getFormData("CallRecordBase");
		getFormValue(formCallRecordBase);
		boolean validateFlag = this.validateFormData(formCallRecordBase);
	}

}
