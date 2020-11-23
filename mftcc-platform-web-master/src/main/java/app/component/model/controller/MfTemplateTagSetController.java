package app.component.model.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.feign.MfNewTemplateTagSetFeign;
import config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.model.entity.MfTemplateTagBase;
import app.component.model.entity.MfTemplateTagSet;
import app.component.model.feign.MfTemplateTagSetFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfTemplateTagSetAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Jun 25 09:23:41 CST 2017
 **/
@Controller
@RequestMapping("/mfTemplateTagSet")
public class MfTemplateTagSetController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfTemplateTagSetBo
	@Autowired
	private MfTemplateTagSetFeign mfTemplateTagSetFeign;
	@Autowired
	private MfNewTemplateTagSetFeign mfNewTemplateTagSetFeign;
	// 使用pageoffice的版本标识 0：老版本 1：新版本
	@Value("${mftcc.pageoffice.office_version:0}")
	private String pageOfficeSts;
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateTagSet_List";
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
		try {
			mfTemplateTagSet.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfTemplateTagSet.setCriteriaList(mfTemplateTagSet, ajaxData);// 我的筛选
			// mfTemplateTagSet.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfTemplateTagSet,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfTemplateTagSet",mfTemplateTagSet));
			if("0".equals(pageOfficeSts)){ //判断pageoffice的版本
				ipage = mfTemplateTagSetFeign.findByPage(ipage);
			}else if("1".equals(pageOfficeSts)){
				ipage = mfNewTemplateTagSetFeign.findByPage(ipage);
			}else {
			}
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formtemplateset0002 = formService.getFormData("templateset0002");
			getFormValue(formtemplateset0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtemplateset0002)) {
				MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
				setObjValue(formtemplateset0002, mfTemplateTagSet);
				if("0".equals(pageOfficeSts)){
					mfTemplateTagSetFeign.insert(mfTemplateTagSet);
				}else if("1".equals(pageOfficeSts)){
					mfNewTemplateTagSetFeign.insert(mfTemplateTagSet);
				}else {
				}
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtemplateset0002 = formService.getFormData("templateset0002");
		getFormValue(formtemplateset0002, getMapByJson(ajaxData));
		MfTemplateTagSet mfTemplateTagSetJsp = new MfTemplateTagSet();
		setObjValue(formtemplateset0002, mfTemplateTagSetJsp);
		MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
		if("0".equals(pageOfficeSts)){
			mfTemplateTagSet = mfTemplateTagSetFeign.getById(mfTemplateTagSetJsp);
		}else if("1".equals(pageOfficeSts)){
			mfTemplateTagSet = mfNewTemplateTagSetFeign.getById(mfTemplateTagSetJsp);
		}else {
		}
		if (mfTemplateTagSet != null) {
			try {
				mfTemplateTagSet = (MfTemplateTagSet) EntityUtil.reflectionSetVal(mfTemplateTagSet, mfTemplateTagSetJsp,
						getMapByJson(ajaxData));
				if("0".equals(pageOfficeSts)){
					mfTemplateTagSetFeign.update(mfTemplateTagSet);
				}else if("1".equals(pageOfficeSts)){
					mfNewTemplateTagSetFeign.update(mfTemplateTagSet);
				}else {
				}
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formtemplateset0002 = formService.getFormData("templateset0002");
			getFormValue(formtemplateset0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtemplateset0002)) {
				MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
				setObjValue(formtemplateset0002, mfTemplateTagSet);
				if("0".equals(pageOfficeSts)){
					mfTemplateTagSetFeign.update(mfTemplateTagSet);
				}else if("1".equals(pageOfficeSts)){
					mfNewTemplateTagSetFeign.update(mfTemplateTagSet);
				}else {
				}
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String seriaNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtemplateset0002 = formService.getFormData("templateset0002");
		MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
		mfTemplateTagSet.setSeriaNo(seriaNo);
		if("0".equals(pageOfficeSts)){
			mfTemplateTagSet = mfTemplateTagSetFeign.getById(mfTemplateTagSet);
		}else if("1".equals(pageOfficeSts)){
			mfTemplateTagSet = mfNewTemplateTagSetFeign.getById(mfTemplateTagSet);
		}else {
		}
		getObjValue(formtemplateset0002, mfTemplateTagSet, formData);
		if (mfTemplateTagSet != null) {
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
	public Map<String, Object> deleteAjax(String seriaNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
		mfTemplateTagSet.setSeriaNo(seriaNo);
		try {
			if("0".equals(pageOfficeSts)){
				mfTemplateTagSetFeign.delete(mfTemplateTagSet);
			}else if("1".equals(pageOfficeSts)){
				mfNewTemplateTagSetFeign.delete(mfTemplateTagSet);
			}else {
			}
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formtemplateset0002 = formService.getFormData("templateset0002");
		model.addAttribute("formtemplateset0002", formtemplateset0002);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateTagSet_Insert";
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
		FormData formtemplateset0002 = formService.getFormData("templateset0002");
		getFormValue(formtemplateset0002);
		MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
		setObjValue(formtemplateset0002, mfTemplateTagSet);
		if("0".equals(pageOfficeSts)){
			mfTemplateTagSetFeign.insert(mfTemplateTagSet);
		}else if("1".equals(pageOfficeSts)){
			mfNewTemplateTagSetFeign.insert(mfTemplateTagSet);
		}else {
		}
		getObjValue(formtemplateset0002, mfTemplateTagSet);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfTemplateTagSet",mfTemplateTagSet));
		List<MfTemplateTagSet> mfTemplateTagSetList = (List<MfTemplateTagSet>) mfTemplateTagSetFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formtemplateset0002", formtemplateset0002);
		model.addAttribute("mfTemplateTagSetList", mfTemplateTagSetList);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateTagSet_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String seriaNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formtemplateset0001 = formService.getFormData("templateset0001");
		getFormValue(formtemplateset0001);
		MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
		mfTemplateTagSet.setSeriaNo(seriaNo);
		if("0".equals(pageOfficeSts)){
			mfTemplateTagSet = mfTemplateTagSetFeign.getById(mfTemplateTagSet);
		}else if("1".equals(pageOfficeSts)){
			mfTemplateTagSet = mfNewTemplateTagSetFeign.getById(mfTemplateTagSet);
		}else {
		}
		getObjValue(formtemplateset0001, mfTemplateTagSet);
		model.addAttribute("formtemplateset0001", formtemplateset0001);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateTagSet_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String seriaNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
		mfTemplateTagSet.setSeriaNo(seriaNo);
		if("0".equals(pageOfficeSts)){
			mfTemplateTagSetFeign.delete(mfTemplateTagSet);
		}else if("1".equals(pageOfficeSts)){
			mfNewTemplateTagSetFeign.delete(mfTemplateTagSet);
		}else {
		}
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
		FormData formtemplateset0002 = formService.getFormData("templateset0002");
		getFormValue(formtemplateset0002);
		boolean validateFlag = this.validateFormData(formtemplateset0002);
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
		FormData formtemplateset0002 = formService.getFormData("templateset0002");
		getFormValue(formtemplateset0002);
		boolean validateFlag = this.validateFormData(formtemplateset0002);
	}

	/**
	 * 
	 * 方法描述： 模板标签配置功能。从LoanTemplateTagSetAction迁移过来的
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2018-1-12 上午9:04:38
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/baseTagSet")
	public String baseTagSet(Model model, String templateNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<MfTemplateTagBase> mfTemplateTagBaseList = new ArrayList<MfTemplateTagBase>();
		String groupFlag;
		String ajaxData;
		try {
			if("0".equals(pageOfficeSts)){
				dataMap = mfTemplateTagSetFeign.getTagsBaseList(templateNo);
			}else if("1".equals(pageOfficeSts)){
				dataMap = mfNewTemplateTagSetFeign.getTagsBaseList(templateNo);
			}else {
			}
			groupFlag = (String) dataMap.get("groupFlag");
			mfTemplateTagBaseList = (List<MfTemplateTagBase>) dataMap.get("mfTemplateTagBaseList");
			ajaxData = new Gson().toJson(mfTemplateTagBaseList);
		} catch (Exception e) {
			throw e;
		}
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("groupFlag", groupFlag);
		model.addAttribute("templateNo", templateNo);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateTagSet_baseTagSet";
	}

}
