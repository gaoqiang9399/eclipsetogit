package app.component.model.controller;

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
import app.component.model.entity.MfTemplateModel;
import app.component.model.feign.MfTemplateModelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfTemplateModelAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Nov 19 11:18:13 CST 2016
 **/
@Controller
@RequestMapping("/mfTemplateModel")
public class MfTemplateModelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfTemplateModelBo
	@Autowired
	private MfTemplateModelFeign mfTemplateModelFeign;
	// 全局变量
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * @param mfTemplateModel 
	 * @param mfTemplateModel 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfTemplateModel mfTemplateModel = new MfTemplateModel();
		List<MfTemplateModel> mfTemplateModelList = mfTemplateModelFeign.getTemplateModelList(mfTemplateModel );
		model.addAttribute("mfTemplateModelList", mfTemplateModelList);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateModel_List";
	}

	@RequestMapping(value = "/searchAjax")
	@ResponseBody
	public Map<String, Object> searchAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		MfTemplateModel mfTemplateModel = new MfTemplateModel();
		mfTemplateModel.setCustomQuery(ajaxData);
		List<MfTemplateModel> mfTemplateModelList = mfTemplateModelFeign.getTemplateModelList(mfTemplateModel);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("mfTemplateModelList", mfTemplateModelList);
		return dataMap;
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
		MfTemplateModel mfTemplateModel = new MfTemplateModel();
		try {
			mfTemplateModel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfTemplateModel.setCriteriaList(mfTemplateModel, ajaxData);// 我的筛选
			// this.getRoleConditions(mfTemplateModel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfTemplateModelFeign.findByPage(ipage, mfTemplateModel);
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
			FormData formtemplatemodel0002 = formService.getFormData("templatemodel0002");
			getFormValue(formtemplatemodel0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtemplatemodel0002)) {
				MfTemplateModel mfTemplateModel = new MfTemplateModel();
				setObjValue(formtemplatemodel0002, mfTemplateModel);
				mfTemplateModel = mfTemplateModelFeign.insert(mfTemplateModel);

				mfTemplateModel = new MfTemplateModel();
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tabletemplatemodel0001", "tableTag",
						(List<MfTemplateModel>) mfTemplateModelFeign.findByPage(ipage, mfTemplateModel).getResult(),
						null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
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
		FormData formtemplatemodel0002 = formService.getFormData("templatemodel0002");
		getFormValue(formtemplatemodel0002, getMapByJson(ajaxData));
		MfTemplateModel mfTemplateModelJsp = new MfTemplateModel();
		setObjValue(formtemplatemodel0002, mfTemplateModelJsp);
		MfTemplateModel mfTemplateModel = mfTemplateModelFeign.getById(mfTemplateModelJsp);
		if (mfTemplateModel != null) {
			try {
				mfTemplateModel = (MfTemplateModel) EntityUtil.reflectionSetVal(mfTemplateModel, mfTemplateModelJsp,
						getMapByJson(ajaxData));
				mfTemplateModelFeign.update(mfTemplateModel);
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
			FormData formtemplatemodel0002 = formService.getFormData("templatemodel0002");
			getFormValue(formtemplatemodel0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtemplatemodel0002)) {
				MfTemplateModel mfTemplateModel = new MfTemplateModel();
				setObjValue(formtemplatemodel0002, mfTemplateModel);
				mfTemplateModelFeign.update(mfTemplateModel);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());

				mfTemplateModel = new MfTemplateModel();
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tabletemplatemodel0001", "tableTag",
						(List<MfTemplateModel>) mfTemplateModelFeign.findByPage(ipage, mfTemplateModel).getResult(),
						null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
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
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String modelNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtemplatemodel0002 = formService.getFormData("templatemodel0002");
		MfTemplateModel mfTemplateModel = new MfTemplateModel();
		mfTemplateModel.setModelNo(modelNo);
		mfTemplateModel = mfTemplateModelFeign.getById(mfTemplateModel);
		getObjValue(formtemplatemodel0002, mfTemplateModel, formData);
		if (mfTemplateModel != null) {
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
	public Map<String, Object> deleteAjax(String modelNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTemplateModel mfTemplateModel = new MfTemplateModel();
		mfTemplateModel.setModelNo(modelNo);
		try {
			mfTemplateModelFeign.delete(mfTemplateModel);
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
		FormData formtemplatemodel0002 = formService.getFormData("templatemodel0002");
		model.addAttribute("formtemplatemodel0002", formtemplatemodel0002);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateModel_Insert";
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
		FormData formtemplatemodel0002 = formService.getFormData("templatemodel0002");
		getFormValue(formtemplatemodel0002);
		MfTemplateModel mfTemplateModel = new MfTemplateModel();
		setObjValue(formtemplatemodel0002, mfTemplateModel);
		mfTemplateModelFeign.insert(mfTemplateModel);
		getObjValue(formtemplatemodel0002, mfTemplateModel);
		this.addActionMessage(model, "保存成功");
		List<MfTemplateModel> mfTemplateModelList = (List<MfTemplateModel>) mfTemplateModelFeign.findByPage(this.getIpage(), mfTemplateModel)
				.getResult();
		model.addAttribute("formtemplatemodel0002", formtemplatemodel0002);
		model.addAttribute("mfTemplateModelList", mfTemplateModelList);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateModel_Insert";
	}

	/**
	 * 查询
	 * @param modelNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String modelNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formtemplatemodel0002 = formService.getFormData("templatemodel0002");
		getFormValue(formtemplatemodel0002);
		MfTemplateModel mfTemplateModel = new MfTemplateModel();
		mfTemplateModel.setModelNo(modelNo);
		mfTemplateModel = mfTemplateModelFeign.getById(mfTemplateModel);
		getObjValue(formtemplatemodel0002, mfTemplateModel);
		model.addAttribute("formtemplatemodel0002", formtemplatemodel0002);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateModel_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String modelNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfTemplateModel mfTemplateModel = new MfTemplateModel();
		mfTemplateModel.setModelNo(modelNo);
		mfTemplateModelFeign.delete(mfTemplateModel);
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
		FormData formtemplatemodel0002 = formService.getFormData("templatemodel0002");
		getFormValue(formtemplatemodel0002);
		boolean validateFlag = this.validateFormData(formtemplatemodel0002);
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
		FormData formtemplatemodel0002 = formService.getFormData("templatemodel0002");
		getFormValue(formtemplatemodel0002);
		boolean validateFlag = this.validateFormData(formtemplatemodel0002);
	}

}
