package app.component.model.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.entity.MfSysTemplate;
import app.component.model.feign.MfNewTemplateTagSetFeign;
import app.component.model.entity.MfTemplateTagSet;
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
import app.component.model.entity.LoanTemplateTagBase;
import app.component.model.entity.LoanTemplateTagSet;
import app.component.model.feign.LoanTemplateTagSetFeign;
import app.component.model.feign.MfSysTemplateFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;

/**
 * Title: LoanTemplateTagSetAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Sep 05 18:28:09 CST 2016
 **/
@Controller
@RequestMapping("/loanTemplateTagSet")
public class LoanTemplateTagSetController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入LoanTemplateTagSetBo
	@Autowired
	private LoanTemplateTagSetFeign loanTemplateTagSetFeign;
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;
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
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/model/LoanTemplateTagSet_List";
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
		LoanTemplateTagSet loanTemplateTagSet = new LoanTemplateTagSet();
		try {
			loanTemplateTagSet.setCustomQuery(ajaxData);// 自定义查询参数赋值
			loanTemplateTagSet.setCriteriaList(loanTemplateTagSet, ajaxData);// 我的筛选
			// this.getRoleConditions(loanTemplateTagSet,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = loanTemplateTagSetFeign.findByPage(ipage, loanTemplateTagSet);
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
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formtagset0002 = formService.getFormData("tagset0002");
			getFormValue(formtagset0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtagset0002)) {
				LoanTemplateTagSet loanTemplateTagSet = new LoanTemplateTagSet();
				setObjValue(formtagset0002, loanTemplateTagSet);
				loanTemplateTagSetFeign.insert(loanTemplateTagSet);
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtagset0002 = formService.getFormData("tagset0002");
		getFormValue(formtagset0002, getMapByJson(ajaxData));
		LoanTemplateTagSet loanTemplateTagSetJsp = new LoanTemplateTagSet();
		setObjValue(formtagset0002, loanTemplateTagSetJsp);
		LoanTemplateTagSet loanTemplateTagSet = loanTemplateTagSetFeign.getById(loanTemplateTagSetJsp);
		if (loanTemplateTagSet != null) {
			try {
				loanTemplateTagSet = (LoanTemplateTagSet) EntityUtil.reflectionSetVal(loanTemplateTagSet,
						loanTemplateTagSetJsp, getMapByJson(ajaxData));
				loanTemplateTagSetFeign.update(loanTemplateTagSet);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formtagset0002 = formService.getFormData("tagset0002");
			getFormValue(formtagset0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtagset0002)) {
				LoanTemplateTagSet loanTemplateTagSet = new LoanTemplateTagSet();
				setObjValue(formtagset0002, loanTemplateTagSet);
				loanTemplateTagSetFeign.update(loanTemplateTagSet);
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTagSetAjax")
	@ResponseBody
	public Map<String, Object> updateTagSetAjax(String templateNo,String tagkeyname,String tagkeyno) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		LoanTemplateTagSet loanTemplateTagSet = new LoanTemplateTagSet();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		try {
			mfSysTemplate.setTemplateNo(templateNo);
			mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
			String templateNameEn = mfSysTemplate.getTemplateNameEn();
			String templateNameZh = mfSysTemplate.getTemplateNameZh();
			// modelNameZh
			// =URLDecoder.decode(URLDecoder.decode(modelNameZh,"UTF-8"),"UTF-8");
			tagkeyname = URLDecoder.decode(URLDecoder.decode(tagkeyname, "UTF-8"), "UTF-8");
			tagkeyname = tagkeyname.replaceAll(",", "@");
			tagkeyno = tagkeyno.replaceAll(",", "@");
			String templatePath = PropertiesUtil.getPageOfficeConfigProperty("office_sys_template");
			if (StringUtil.isEmpty(templatePath)) {
				String realFilePath = request.getSession().getServletContext().getRealPath("/");
				templatePath = realFilePath + "component" + File.separator + "model" + File.separator + "docmodel"
						+ File.separator;
			}
			MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
			if("0".equals(pageOfficeSts)){
				loanTemplateTagSet.setTemplateNo(templateNo);
				loanTemplateTagSet.setTemplateEnName(templateNameEn);
				loanTemplateTagSet.setTemplateCnName(templateNameZh);
				loanTemplateTagSet.setTagKeyName(tagkeyname);
				loanTemplateTagSet.setTagKeyNo(tagkeyno);
				loanTemplateTagSet.setTemplatePath(templatePath);
			loanTemplateTagSetFeign.updateTagSet(loanTemplateTagSet);
			}else if("1".equals(pageOfficeSts)){
				mfTemplateTagSet.setTemplateNo(templateNo);
				mfTemplateTagSet.setTemplateEnName(templateNameEn);
				mfTemplateTagSet.setTemplateCnName(templateNameZh);
				mfTemplateTagSet.setTagKeyName(tagkeyname);
				mfTemplateTagSet.setTagKeyNo(tagkeyno);
				mfTemplateTagSet.setTemplatePath(templatePath);
				mfNewTemplateTagSetFeign.updateTagSet(mfTemplateTagSet);
			}else {
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String seriaNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtagset0002 = formService.getFormData("tagset0002");
		LoanTemplateTagSet loanTemplateTagSet = new LoanTemplateTagSet();
		loanTemplateTagSet.setSeriaNo(seriaNo);
		loanTemplateTagSet = loanTemplateTagSetFeign.getById(loanTemplateTagSet);
		getObjValue(formtagset0002, loanTemplateTagSet, formData);
		if (loanTemplateTagSet != null) {
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
		LoanTemplateTagSet loanTemplateTagSet = new LoanTemplateTagSet();
		loanTemplateTagSet.setSeriaNo(seriaNo);
		try {
			loanTemplateTagSetFeign.delete(loanTemplateTagSet);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formtagset0002 = formService.getFormData("tagset0002");
		model.addAttribute("formtagset0002", formtagset0002);
		model.addAttribute("query", "");
		return "/component/model/LoanTemplateTagSet_Insert";
	}

	/**
	 * 
	 * 方法描述： 标签配置
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-6 下午3:40:22
	 */
	@RequestMapping(value = "/baseTagSet")
	public String baseTagSet(Model model, String templateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<LoanTemplateTagBase> loanTemplateTagBaseList = new ArrayList<LoanTemplateTagBase>();
		String ajaxData;
		try {
			Map<String, String> map = new CodeUtils().getMapByKeyName("BOOKMARK_CLASS");
			/*** 字典项升序排列 */
			Map<String, String> sortMmap = new TreeMap<String, String>(new Comparator<String>() {
				@Override
                public int compare(String obj1, String obj2) {
					// 升序排序
					return obj1.compareTo(obj2);
				}
			});
			sortMmap.putAll(map);
			List<Map<String, String>> parmMapList = new ArrayList<Map<String, String>>();
			Iterator it = sortMmap.entrySet().iterator();
			while (it.hasNext()) {
				Map<String, String> temMap = new HashMap<String, String>();
				Entry entry = (Entry) it.next();
				temMap.put("optCode", (String) entry.getKey());
				temMap.put("optName", (String) entry.getValue());
				parmMapList.add(temMap);
			}
			/*** 字典项升序排列结束 */
			String groupFlag = new Gson().toJson(parmMapList);
			model.addAttribute("groupFlag", groupFlag);
			model.addAttribute("templateNo", templateNo);
			loanTemplateTagBaseList = loanTemplateTagSetFeign.getTagsBaseList(templateNo);
			ajaxData = new Gson().toJson(loanTemplateTagBaseList);
		} catch (Exception e) {
			throw e;
		}
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/model/LoanTemplateTagSet_baseTagSet";
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
		FormData formtagset0002 = formService.getFormData("tagset0002");
		getFormValue(formtagset0002);
		LoanTemplateTagSet loanTemplateTagSet = new LoanTemplateTagSet();
		setObjValue(formtagset0002, loanTemplateTagSet);
		loanTemplateTagSetFeign.insert(loanTemplateTagSet);
		getObjValue(formtagset0002, loanTemplateTagSet);
		this.addActionMessage(model, "保存成功");
		List<LoanTemplateTagSet> loanTemplateTagSetList = (List<LoanTemplateTagSet>) loanTemplateTagSetFeign
				.findByPage(this.getIpage(), loanTemplateTagSet).getResult();
		model.addAttribute("formtagset0002", formtagset0002);
		model.addAttribute("loanTemplateTagSetList", loanTemplateTagSetList);
		model.addAttribute("query", "");
		return "/component/model/LoanTemplateTagSet_Insert";
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
		FormData formtagset0002 = formService.getFormData("tagset0001");
		getFormValue(formtagset0002);
		LoanTemplateTagSet loanTemplateTagSet = new LoanTemplateTagSet();
		loanTemplateTagSet.setSeriaNo(seriaNo);
		loanTemplateTagSet = loanTemplateTagSetFeign.getById(loanTemplateTagSet);
		getObjValue(formtagset0002, loanTemplateTagSet);
		model.addAttribute("formtagset0002", formtagset0002);
		model.addAttribute("query", "");
		return "/component/model/LoanTemplateTagSet_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String seriaNo) throws Exception {
		ActionContext.initialize(request, response);
		LoanTemplateTagSet loanTemplateTagSet = new LoanTemplateTagSet();
		loanTemplateTagSet.setSeriaNo(seriaNo);
		loanTemplateTagSetFeign.delete(loanTemplateTagSet);
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
		FormData formtagset0002 = formService.getFormData("tagset0002");
		getFormValue(formtagset0002);
		boolean validateFlag = this.validateFormData(formtagset0002);
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
		FormData formtagset0002 = formService.getFormData("tagset0002");
		getFormValue(formtagset0002);
		boolean validateFlag = this.validateFormData(formtagset0002);
	}

}
