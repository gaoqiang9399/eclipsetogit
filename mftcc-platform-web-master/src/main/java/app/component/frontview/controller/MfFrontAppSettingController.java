package app.component.frontview.controller;

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
import app.component.frontview.entity.MfFrontAppSetting;
import app.component.frontview.feign.MfFrontAppSettingFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfFrontAppSettingAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jul 20 11:25:24 CST 2017
 **/
@Controller
@RequestMapping("/mfFrontAppSetting")
public class MfFrontAppSettingController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfFrontAppSettingBo
	@Autowired
	private MfFrontAppSettingFeign mfFrontAppSettingFeign;
	// 全局变量

	/**
	 * 移动端客服设置
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMobileFormHtmlAjax")
	@ResponseBody
	public Map<String, Object> getMobileFormHtmlAjax(String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");// 没有这个参数,不能生成单字段编辑的表单
		MfFrontAppSetting mfFrontAppSetting = new MfFrontAppSetting();
		mfFrontAppSetting = mfFrontAppSettingFeign.get();// 获取基础数据
		FormData formfrontmobilesetting0001 = formService.getFormData("frontmobilesetting0001");
		getFormValue(formfrontmobilesetting0001);
		getObjValue(formfrontmobilesetting0001, mfFrontAppSetting);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formfrontmobilesetting0001, "propertySeeTag", query);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", htmlStr);
		return dataMap;
	}

	/**
	 * 移动端分享设置
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMobileShareHtmlAjax")
	@ResponseBody
	public Map<String, Object> getMobileShareHtmlAjax(String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");// 没有这个参数,不能生成单字段编辑的表单
		MfFrontAppSetting mfFrontAppSetting = new MfFrontAppSetting();
		mfFrontAppSetting = mfFrontAppSettingFeign.get();// 获取基础数据
		FormData formfrontmobileshare0001 = formService.getFormData("frontmobileshare0001");
		getFormValue(formfrontmobileshare0001);
		getObjValue(formfrontmobileshare0001, mfFrontAppSetting);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formfrontmobileshare0001, "propertySeeTag", query);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", htmlStr);
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新 第一次添加执行插入逻辑
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
		FormData formfrontmobileshare0001 = formService.getFormData("frontmobileshare0001");// 2个表单公用一个实体表
		FormData formfrontmobilesetting0001 = formService.getFormData("frontmobilesetting0001");
		getFormValue(formfrontmobileshare0001, getMapByJson(ajaxData));
		getFormValue(formfrontmobilesetting0001, getMapByJson(ajaxData));
		MfFrontAppSetting mfFrontAppSettingJsp = new MfFrontAppSetting();
		setObjValue(formfrontmobileshare0001, mfFrontAppSettingJsp);
		setObjValue(formfrontmobilesetting0001, mfFrontAppSettingJsp);
		MfFrontAppSetting mfFrontAppSetting = mfFrontAppSettingFeign.get();
		if (mfFrontAppSetting != null) {
			try {
				mfFrontAppSetting = (MfFrontAppSetting) EntityUtil.reflectionSetVal(mfFrontAppSetting, mfFrontAppSettingJsp, getMapByJson(ajaxData));
				mfFrontAppSettingFeign.update(mfFrontAppSetting);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		} else {// 没有数据执行插入方法
			mfFrontAppSettingFeign.insert(mfFrontAppSettingJsp);
			dataMap.put("flag", "success");
			dataMap.put("msg", "保存成功");
		}
		return dataMap;
	}

	/********** 以下自动生成代码 **************/
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/frontview/MfFrontAppSetting_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFrontAppSetting mfFrontAppSetting = new MfFrontAppSetting();
		try {
			mfFrontAppSetting.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfFrontAppSetting.setCriteriaList(mfFrontAppSetting, ajaxData);// 我的筛选
			// mfFrontAppSetting.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfFrontAppSetting,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfFrontAppSettingFeign.findByPage(ipage, mfFrontAppSetting);
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
			FormData formfrontappsetting0002 = formService.getFormData("frontappsetting0002");
			getFormValue(formfrontappsetting0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfrontappsetting0002)) {
				MfFrontAppSetting mfFrontAppSetting = new MfFrontAppSetting();
				setObjValue(formfrontappsetting0002, mfFrontAppSetting);
				mfFrontAppSettingFeign.insert(mfFrontAppSetting);
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
			FormData formfrontappsetting0002 = formService.getFormData("frontappsetting0002");
			getFormValue(formfrontappsetting0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfrontappsetting0002)) {
				MfFrontAppSetting mfFrontAppSetting = new MfFrontAppSetting();
				setObjValue(formfrontappsetting0002, mfFrontAppSetting);
				mfFrontAppSettingFeign.update(mfFrontAppSetting);
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
		FormData formfrontappsetting0002 = formService.getFormData("frontappsetting0002");
		MfFrontAppSetting mfFrontAppSetting = new MfFrontAppSetting();
		mfFrontAppSetting.setId(id);
		mfFrontAppSetting = mfFrontAppSettingFeign.getById(mfFrontAppSetting);
		getObjValue(formfrontappsetting0002, mfFrontAppSetting, formData);
		if (mfFrontAppSetting != null) {
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
		MfFrontAppSetting mfFrontAppSetting = new MfFrontAppSetting();
		mfFrontAppSetting.setId(id);
		try {
			mfFrontAppSettingFeign.delete(mfFrontAppSetting);
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
		FormData formfrontappsetting0002 = formService.getFormData("frontappsetting0002");
		model.addAttribute("formfrontappsetting0002", formfrontappsetting0002);
		model.addAttribute("query", "");
		return "/component/frontview/MfFrontAppSetting_Insert";
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
		FormData formfrontappsetting0002 = formService.getFormData("frontappsetting0001");
		getFormValue(formfrontappsetting0002);
		MfFrontAppSetting mfFrontAppSetting = new MfFrontAppSetting();
		mfFrontAppSetting.setId(id);
		mfFrontAppSetting = mfFrontAppSettingFeign.getById(mfFrontAppSetting);
		getObjValue(formfrontappsetting0002, mfFrontAppSetting);
		model.addAttribute("mfFrontAppSetting", mfFrontAppSetting);
		model.addAttribute("formfrontappsetting0002", formfrontappsetting0002);
		model.addAttribute("query", "");
		return "/component/frontview/MfFrontAppSetting_Detail";
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
		FormData formfrontappsetting0002 = formService.getFormData("frontappsetting0002");
		getFormValue(formfrontappsetting0002);
		boolean validateFlag = this.validateFormData(formfrontappsetting0002);
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
		FormData formfrontappsetting0002 = formService.getFormData("frontappsetting0002");
		getFormValue(formfrontappsetting0002);
		boolean validateFlag = this.validateFormData(formfrontappsetting0002);
	}
}
