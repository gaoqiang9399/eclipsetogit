package app.component.report.controller;

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
import app.component.report.entity.MfReportQueryConditionConfig;
import app.component.report.feign.MfReportQueryConditionConfigFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfReportQueryConditionConfigAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 24 17:03:14 CST 2017
 **/
@Controller
@RequestMapping("/mfReportQueryConditionConfig")
public class MfReportQueryConditionConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfReportQueryConditionConfigBo
	@Autowired
	private MfReportQueryConditionConfigFeign mfReportQueryConditionConfigFeign;
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
		return "/component/report/MfReportQueryConditionConfig_List";
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
		MfReportQueryConditionConfig mfReportQueryConditionConfig = new MfReportQueryConditionConfig();
		try {
			mfReportQueryConditionConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReportQueryConditionConfig.setCriteriaList(mfReportQueryConditionConfig, ajaxData);// 我的筛选
			// mfReportQueryConditionConfig.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReportQueryConditionConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfReportQueryConditionConfigFeign.findByPage(ipage, mfReportQueryConditionConfig);
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
			FormData formreportConfig0002 = formService.getFormData("reportConfig0002");
			getFormValue(formreportConfig0002, getMapByJson(ajaxData));
			if (this.validateFormData(formreportConfig0002)) {
				MfReportQueryConditionConfig mfReportQueryConditionConfig = new MfReportQueryConditionConfig();
				setObjValue(formreportConfig0002, mfReportQueryConditionConfig);
				mfReportQueryConditionConfigFeign.insert(mfReportQueryConditionConfig);
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
		FormData formreportConfig0002 = formService.getFormData("reportConfig0002");
		getFormValue(formreportConfig0002, getMapByJson(ajaxData));
		MfReportQueryConditionConfig mfReportQueryConditionConfigJsp = new MfReportQueryConditionConfig();
		setObjValue(formreportConfig0002, mfReportQueryConditionConfigJsp);
		MfReportQueryConditionConfig mfReportQueryConditionConfig = mfReportQueryConditionConfigFeign.getById(mfReportQueryConditionConfigJsp);
		if (mfReportQueryConditionConfig != null) {
			try {
				mfReportQueryConditionConfig = (MfReportQueryConditionConfig) EntityUtil.reflectionSetVal(
						mfReportQueryConditionConfig, mfReportQueryConditionConfigJsp, getMapByJson(ajaxData));
				mfReportQueryConditionConfigFeign.update(mfReportQueryConditionConfig);
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
			FormData formreportConfig0002 = formService.getFormData("reportConfig0002");
			getFormValue(formreportConfig0002, getMapByJson(ajaxData));
			if (this.validateFormData(formreportConfig0002)) {
				MfReportQueryConditionConfig mfReportQueryConditionConfig = new MfReportQueryConditionConfig();
				setObjValue(formreportConfig0002, mfReportQueryConditionConfig);
				mfReportQueryConditionConfigFeign.update(mfReportQueryConditionConfig);
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
	@RequestMapping(value = "/getConditonShowByIdAjax")
	@ResponseBody
	public Map<String, Object> getConditonShowByIdAjax(String reportId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReportQueryConditionConfig mfReportQueryConditionConfig = new MfReportQueryConditionConfig();
		mfReportQueryConditionConfig.setReportId(reportId);
		mfReportQueryConditionConfig = mfReportQueryConditionConfigFeign.getById(mfReportQueryConditionConfig);
		if (mfReportQueryConditionConfig != null) {
			dataMap.put("flag", "success");
			dataMap.put("result", mfReportQueryConditionConfig.getConditonShow());
		} else {
			dataMap.put("flag", "error");
		}
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReportQueryConditionConfig mfReportQueryConditionConfig = new MfReportQueryConditionConfig();
		mfReportQueryConditionConfig.setId(id);
		try {
			mfReportQueryConditionConfigFeign.delete(mfReportQueryConditionConfig);
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
		FormData formreportConfig0002 = formService.getFormData("reportConfig0002");
		model.addAttribute("formreportConfig0002", formreportConfig0002);
		model.addAttribute("query", "");
		return "/component/report/MfReportQueryConditionConfig_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formreportConfig0001 = formService.getFormData("reportConfig0001");
		getFormValue(formreportConfig0001);
		MfReportQueryConditionConfig mfReportQueryConditionConfig = new MfReportQueryConditionConfig();
		mfReportQueryConditionConfig.setId(id);
		mfReportQueryConditionConfig = mfReportQueryConditionConfigFeign.getById(mfReportQueryConditionConfig);
		getObjValue(formreportConfig0001, mfReportQueryConditionConfig);
		model.addAttribute("formreportConfig0001", formreportConfig0001);
		model.addAttribute("query", "");
		return "/component/report/MfReportQueryConditionConfig_Detail";
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
		FormData formreportConfig0002 = formService.getFormData("reportConfig0002");
		getFormValue(formreportConfig0002);
		boolean validateFlag = this.validateFormData(formreportConfig0002);
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
		FormData formreportConfig0002 = formService.getFormData("reportConfig0002");
		getFormValue(formreportConfig0002);
		boolean validateFlag = this.validateFormData(formreportConfig0002);
	}

}
