package app.component.aop.controller;

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

import app.component.aop.entity.MfUniqueConfig;
import app.component.aop.feign.MfUniqueConfigFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfUniqueConfigAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 17:54:49 CST 2017
 **/
@Controller
@RequestMapping(value = "/mfUniqueConfig")
public class MfUniqueConfigController extends BaseFormBean {
	// 注入MfUniqueConfigBo
	@Autowired
	private MfUniqueConfigFeign mfUniqueConfigFeign;

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
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/aop/MfUniqueConfig_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUniqueConfig mfUniqueConfig = new MfUniqueConfig();
		try {
			mfUniqueConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfUniqueConfig.setCriteriaList(mfUniqueConfig, ajaxData);// 我的筛选
			// mfUniqueConfig.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfUniqueConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfUniqueConfigFeign.findByPage(ipage, mfUniqueConfig);
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

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formuniqual0002 = formService.getFormData("uniqual0002");
			getFormValue(formuniqual0002, getMapByJson(ajaxData));
			if (this.validateFormData(formuniqual0002)) {
				MfUniqueConfig mfUniqueConfig = new MfUniqueConfig();
				setObjValue(formuniqual0002, mfUniqueConfig);
				mfUniqueConfigFeign.insert(mfUniqueConfig);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formuniqual0002 = formService.getFormData("uniqual0002");
		getFormValue(formuniqual0002, getMapByJson(ajaxData));
		MfUniqueConfig mfUniqueConfigJsp = new MfUniqueConfig();
		setObjValue(formuniqual0002, mfUniqueConfigJsp);
		MfUniqueConfig mfUniqueConfig = mfUniqueConfigFeign.getById(mfUniqueConfigJsp);
		if (mfUniqueConfig != null) {
			try {
				mfUniqueConfig = (MfUniqueConfig) EntityUtil.reflectionSetVal(mfUniqueConfig, mfUniqueConfigJsp,
						getMapByJson(ajaxData));
				mfUniqueConfigFeign.update(mfUniqueConfig);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formuniqual0002 = formService.getFormData("uniqual0002");
			getFormValue(formuniqual0002, getMapByJson(ajaxData));
			if (this.validateFormData(formuniqual0002)) {
				MfUniqueConfig mfUniqueConfig = new MfUniqueConfig();
				setObjValue(formuniqual0002, mfUniqueConfig);
				mfUniqueConfigFeign.update(mfUniqueConfig);
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
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String configId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formuniqual0002 = formService.getFormData("uniqual0002");
		MfUniqueConfig mfUniqueConfig = new MfUniqueConfig();
		mfUniqueConfig.setConfigId(configId);
		mfUniqueConfig = mfUniqueConfigFeign.getById(mfUniqueConfig);
		getObjValue(formuniqual0002, mfUniqueConfig, formData);
		if (mfUniqueConfig != null) {
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
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String configId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUniqueConfig mfUniqueConfig = new MfUniqueConfig();
		mfUniqueConfig.setConfigId(configId);
		try {
			mfUniqueConfigFeign.delete(mfUniqueConfig);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formuniqual0002 = formService.getFormData("uniqual0002");
		model.addAttribute("formuniqual0002", formuniqual0002);
		model.addAttribute("query", "");
		return "/component/aop/MfUniqueConfig_Insert";
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
		FormService formService = new FormService();
		FormData formuniqual0002 = formService.getFormData("uniqual0002");
		getFormValue(formuniqual0002);
		MfUniqueConfig mfUniqueConfig = new MfUniqueConfig();
		setObjValue(formuniqual0002, mfUniqueConfig);
		mfUniqueConfigFeign.insert(mfUniqueConfig);
		getObjValue(formuniqual0002, mfUniqueConfig);
		this.addActionMessage(model,"保存成功");
		List<MfUniqueConfig> mfUniqueConfigList = (List<MfUniqueConfig>) mfUniqueConfigFeign.findByPage(this.getIpage(), mfUniqueConfig)
				.getResult();
		model.addAttribute("formuniqual0002", formuniqual0002);
		model.addAttribute("mfUniqueConfigList", mfUniqueConfigList);
		model.addAttribute("query", "");
		return "/component/aop/MfUniqueConfig_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String configId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formuniqual0001 = formService.getFormData("uniqual0001");
		getFormValue(formuniqual0001);
		MfUniqueConfig mfUniqueConfig = new MfUniqueConfig();
		mfUniqueConfig.setConfigId(configId);
		mfUniqueConfig = mfUniqueConfigFeign.getById(mfUniqueConfig);
		getObjValue(formuniqual0001, mfUniqueConfig);
		model.addAttribute("formuniqual0001", formuniqual0001);
		model.addAttribute("query", "");
		return "/component/aop/MfUniqueConfig_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String configId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfUniqueConfig mfUniqueConfig = new MfUniqueConfig();
		mfUniqueConfig.setConfigId(configId);
		mfUniqueConfigFeign.delete(mfUniqueConfig);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formuniqual0002 = formService.getFormData("uniqual0002");
		getFormValue(formuniqual0002);
		boolean validateFlag = this.validateFormData(formuniqual0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formuniqual0002 = formService.getFormData("uniqual0002");
		getFormValue(formuniqual0002);
		boolean validateFlag = this.validateFormData(formuniqual0002);
	}

}
