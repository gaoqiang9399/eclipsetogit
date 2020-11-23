package app.component.cus.controller;

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
import app.component.cus.entity.MfCusModifyConfig;
import app.component.cus.feign.MfCusModifyConfigFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfCusModifyConfigAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Nov 09 15:09:29 CST 2017
 **/
@Controller
@RequestMapping("/mfCusModifyConfig")
public class MfCusModifyConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusModifyConfigBo
	@Autowired
	private MfCusModifyConfigFeign mfCusModifyConfigFeign;

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
		return "component/cus/MfCusModifyConfig_List";
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
		MfCusModifyConfig mfCusModifyConfig = new MfCusModifyConfig();
		try {
			mfCusModifyConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusModifyConfig.setCriteriaList(mfCusModifyConfig, ajaxData);// 我的筛选
			// mfCusModifyConfig.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusModifyConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusModifyConfig", mfCusModifyConfig));
			ipage = mfCusModifyConfigFeign.findByPage(ipage);
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
			FormData formcusmodify0002 = formService.getFormData("cusmodify0002");
			getFormValue(formcusmodify0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusmodify0002)) {
				MfCusModifyConfig mfCusModifyConfig = new MfCusModifyConfig();
				setObjValue(formcusmodify0002, mfCusModifyConfig);
				mfCusModifyConfigFeign.insert(mfCusModifyConfig);
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
		FormData formcusmodify0002 = formService.getFormData("cusmodify0002");
		getFormValue(formcusmodify0002, getMapByJson(ajaxData));
		MfCusModifyConfig mfCusModifyConfigJsp = new MfCusModifyConfig();
		setObjValue(formcusmodify0002, mfCusModifyConfigJsp);
		MfCusModifyConfig mfCusModifyConfig = mfCusModifyConfigFeign.getById(mfCusModifyConfigJsp);
		if (mfCusModifyConfig != null) {
			try {
				mfCusModifyConfig = (MfCusModifyConfig) EntityUtil.reflectionSetVal(mfCusModifyConfig,
						mfCusModifyConfigJsp, getMapByJson(ajaxData));
				mfCusModifyConfigFeign.update(mfCusModifyConfig);
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
			FormData formcusmodify0002 = formService.getFormData("cusmodify0002");
			getFormValue(formcusmodify0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusmodify0002)) {
				MfCusModifyConfig mfCusModifyConfig = new MfCusModifyConfig();
				setObjValue(formcusmodify0002, mfCusModifyConfig);
				mfCusModifyConfigFeign.update(mfCusModifyConfig);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusmodify0002 = formService.getFormData("cusmodify0002");
		MfCusModifyConfig mfCusModifyConfig = new MfCusModifyConfig();
		mfCusModifyConfig.setId(id);
		mfCusModifyConfig = mfCusModifyConfigFeign.getById(mfCusModifyConfig);
		getObjValue(formcusmodify0002, mfCusModifyConfig, formData);
		if (mfCusModifyConfig != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusModifyConfig mfCusModifyConfig = new MfCusModifyConfig();
		mfCusModifyConfig.setId(id);
		try {
			mfCusModifyConfigFeign.delete(mfCusModifyConfig);
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
		FormData formcusmodify0002 = formService.getFormData("cusmodify0002");
		model.addAttribute("formcusmodify0002", formcusmodify0002);
		model.addAttribute("query", "");
		return "component/cus/MfCusModifyConfig_Insert";
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
		FormData formcusmodify0002 = formService.getFormData("cusmodify0002");
		getFormValue(formcusmodify0002);
		MfCusModifyConfig mfCusModifyConfig = new MfCusModifyConfig();
		setObjValue(formcusmodify0002, mfCusModifyConfig);
		mfCusModifyConfigFeign.insert(mfCusModifyConfig);
		getObjValue(formcusmodify0002, mfCusModifyConfig);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusModifyConfig", mfCusModifyConfig));
		List<MfCusModifyConfig> mfCusModifyConfigList = (List<MfCusModifyConfig>) mfCusModifyConfigFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formcusmodify0002", formcusmodify0002);
		model.addAttribute("mfCusModifyConfig", mfCusModifyConfig);
		model.addAttribute("mfCusModifyConfigList", mfCusModifyConfigList);
		model.addAttribute("query", "");
		return "component/cus/MfCusModifyConfig_Insert";
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
		FormData formcusmodify0001 = formService.getFormData("cusmodify0001");
		getFormValue(formcusmodify0001);
		MfCusModifyConfig mfCusModifyConfig = new MfCusModifyConfig();
		mfCusModifyConfig.setId(id);
		mfCusModifyConfig = mfCusModifyConfigFeign.getById(mfCusModifyConfig);
		getObjValue(formcusmodify0001, mfCusModifyConfig);
		model.addAttribute("formcusmodify0001", formcusmodify0001);
		model.addAttribute("mfCusModifyConfig", mfCusModifyConfig);
		model.addAttribute("query", "");
		return "component/cus/MfCusModifyConfig_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusModifyConfig mfCusModifyConfig = new MfCusModifyConfig();
		mfCusModifyConfig.setId(id);
		mfCusModifyConfigFeign.delete(mfCusModifyConfig);
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
		FormData formcusmodify0002 = formService.getFormData("cusmodify0002");
		getFormValue(formcusmodify0002);
		boolean validateFlag = this.validateFormData(formcusmodify0002);
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
		FormData formcusmodify0002 = formService.getFormData("cusmodify0002");
		getFormValue(formcusmodify0002);
		boolean validateFlag = this.validateFormData(formcusmodify0002);
	}

}
