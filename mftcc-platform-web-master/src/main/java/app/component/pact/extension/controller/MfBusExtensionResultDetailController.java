package app.component.pact.extension.controller;

import app.component.common.EntityUtil;
import app.component.pact.extension.entity.MfBusExtensionResultDetail;
import app.component.pact.extension.feign.MfBusExtensionResultDetailFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusExtensionResultDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Sep 18 11:43:04 CST 2017
 **/
@Controller
@RequestMapping("/mfBusExtensionResultDetail")
public class MfBusExtensionResultDetailController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusExtensionResultDetailBo
	@Autowired
	private MfBusExtensionResultDetailFeign mfBusExtensionResultDetailFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "MfBusExtensionResultDetail_List";
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
		MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
		try {
			mfBusExtensionResultDetail.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusExtensionResultDetail.setCriteriaList(mfBusExtensionResultDetail, ajaxData);// 我的筛选
			// mfBusExtensionResultDetail.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfBusExtensionResultDetail,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusExtensionResultDetail", mfBusExtensionResultDetail));
			ipage = mfBusExtensionResultDetailFeign.findByPage(ipage);
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
			FormData formextension0002 = formService.getFormData("extension0002");
			getFormValue(formextension0002, getMapByJson(ajaxData));
			if (this.validateFormData(formextension0002)) {
				MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
				setObjValue(formextension0002, mfBusExtensionResultDetail);
				mfBusExtensionResultDetailFeign.insert(mfBusExtensionResultDetail);
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
		FormData formextension0002 = formService.getFormData("extension0002");
		getFormValue(formextension0002, getMapByJson(ajaxData));
		MfBusExtensionResultDetail mfBusExtensionResultDetailJsp = new MfBusExtensionResultDetail();
		setObjValue(formextension0002, mfBusExtensionResultDetailJsp);
		MfBusExtensionResultDetail mfBusExtensionResultDetail = mfBusExtensionResultDetailFeign.getById(mfBusExtensionResultDetailJsp);
		if (mfBusExtensionResultDetail != null) {
			try {
				mfBusExtensionResultDetail = (MfBusExtensionResultDetail) EntityUtil.reflectionSetVal(
						mfBusExtensionResultDetail, mfBusExtensionResultDetailJsp, getMapByJson(ajaxData));
				mfBusExtensionResultDetailFeign.update(mfBusExtensionResultDetail);
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
			FormData formextension0002 = formService.getFormData("extension0002");
			getFormValue(formextension0002, getMapByJson(ajaxData));
			if (this.validateFormData(formextension0002)) {
				MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
				setObjValue(formextension0002, mfBusExtensionResultDetail);
				mfBusExtensionResultDetailFeign.update(mfBusExtensionResultDetail);
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
	public Map<String, Object> getByIdAjax(String resultDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formextension0002 = formService.getFormData("extension0002");
		MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
		mfBusExtensionResultDetail.setResultDetailId(resultDetailId);
		mfBusExtensionResultDetail = mfBusExtensionResultDetailFeign.getById(mfBusExtensionResultDetail);
		getObjValue(formextension0002, mfBusExtensionResultDetail, formData);
		if (mfBusExtensionResultDetail != null) {
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
	public Map<String, Object> deleteAjax(String resultDetailId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
		mfBusExtensionResultDetail.setResultDetailId(resultDetailId);
		try {
			mfBusExtensionResultDetailFeign.delete(mfBusExtensionResultDetail);
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
		FormData formextension0002 = formService.getFormData("extension0002");
		model.addAttribute("formextension0002", formextension0002);
		model.addAttribute("query", "");
		return "MfBusExtensionResultDetail_Insert";
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
		FormData formextension0002 = formService.getFormData("extension0002");
		getFormValue(formextension0002);
		MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
		setObjValue(formextension0002, mfBusExtensionResultDetail);
		mfBusExtensionResultDetailFeign.insert(mfBusExtensionResultDetail);
		getObjValue(formextension0002, mfBusExtensionResultDetail);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfBusExtensionResultDetail", mfBusExtensionResultDetail));
		List<MfBusExtensionResultDetail> mfBusExtensionResultDetailList = (List<MfBusExtensionResultDetail>) mfBusExtensionResultDetailFeign.findByPage(ipage).getResult();
		model.addAttribute("mfBusExtensionResultDetailList", mfBusExtensionResultDetailList);
		model.addAttribute("formextension0002", formextension0002);
		model.addAttribute("query", "");
		return "MfBusExtensionResultDetail_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String resultDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formextension0002 = formService.getFormData("extension0001");
		getFormValue(formextension0002);
		MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
		mfBusExtensionResultDetail.setResultDetailId(resultDetailId);
		mfBusExtensionResultDetail = mfBusExtensionResultDetailFeign.getById(mfBusExtensionResultDetail);
		getObjValue(formextension0002, mfBusExtensionResultDetail);
		model.addAttribute("mfBusExtensionResultDetail", mfBusExtensionResultDetail);
		model.addAttribute("formextension0002", formextension0002);
		model.addAttribute("query", "");
		return "MfBusExtensionResultDetail_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String resultDetailId) throws Exception {
		ActionContext.initialize(request, response);
		MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
		mfBusExtensionResultDetail.setResultDetailId(resultDetailId);
		mfBusExtensionResultDetailFeign.delete(mfBusExtensionResultDetail);
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
		FormData formextension0002 = formService.getFormData("extension0002");
		getFormValue(formextension0002);
		boolean validateFlag = this.validateFormData(formextension0002);
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
		FormData formextension0002 = formService.getFormData("extension0002");
		getFormValue(formextension0002);
		boolean validateFlag = this.validateFormData(formextension0002);
	}

}
