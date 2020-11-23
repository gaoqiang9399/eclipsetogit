package app.component.oa.archive.controller;

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

import app.component.common.BizPubParm;
import app.component.oa.archive.entity.MfOaArchivesEducation;
import app.component.oa.archive.feign.MfOaArchivesEducationFeign;
import app.component.oa.feign.MfOaFormConfigFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;

@Controller
@RequestMapping("/mfOaArchivesEducation")
public class MfOaArchivesEducationController extends BaseFormBean {
	@Autowired
	private MfOaArchivesEducationFeign mfOaArchivesEducationFeign;
	@Autowired
	private MfOaFormConfigFeign mfOaFormConfigFeign;
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
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/oa/archive/MfOaArchivesEducation_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesEducation mfOaArchivesEducation = new MfOaArchivesEducation();
		try {
			mfOaArchivesEducation.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaArchivesEducation.setCriteriaList(mfOaArchivesEducation, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaArchivesEducation,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaArchivesEducation", mfOaArchivesEducation));
			ipage = mfOaArchivesEducationFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formarchiveeducation0001 = formService.getFormData(
					mfOaFormConfigFeign.getFormByAction("MfOaArchivesEducationAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formarchiveeducation0001, getMapByJson(ajaxData));
			if (this.validateFormData(formarchiveeducation0001)) {
				MfOaArchivesEducation mfOaArchivesEducation = new MfOaArchivesEducation();
				MfOaArchivesEducation mfOaArchivesEducation1 = new MfOaArchivesEducation();
				setObjValue(formarchiveeducation0001, mfOaArchivesEducation);
				mfOaArchivesEducation1 = mfOaArchivesEducationFeign.getById(mfOaArchivesEducation);
				if (mfOaArchivesEducation1 == null) {
					mfOaArchivesEducation.setEducationId(WaterIdUtil.getWaterId());
					mfOaArchivesEducationFeign.insert(mfOaArchivesEducation);
				} else {
					mfOaArchivesEducationFeign.update(mfOaArchivesEducation);
				}
				CodeUtils codeUtils = new CodeUtils();
				mfOaArchivesEducation
						.setEducation((codeUtils.getMapByKeyName("EDU")).get(mfOaArchivesEducation.getEducation()));
				dataMap.put("mfOaArchivesEducation", mfOaArchivesEducation);
				JsonTableUtil jtu = new JsonTableUtil();
				String educationTableHtml = jtu.getJsonStr(
						mfOaFormConfigFeign.getFormByAction("MfOaArchivesEducationAction", BizPubParm.SHOW_TYPE3),
						"tableTag", mfOaArchivesEducationFeign.getByBaseId(mfOaArchivesEducation), null, true);
				dataMap.put("educationTableHtml", educationTableHtml);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;
	}



	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String educationId,String baseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesEducation mfOaArchivesEducation = new MfOaArchivesEducation();
		mfOaArchivesEducation.setEducationId(educationId);
		mfOaArchivesEducation.setBaseId(baseId);
		try {
			mfOaArchivesEducationFeign.delete(mfOaArchivesEducation);
			JsonTableUtil jtu = new JsonTableUtil();
			String educationTableHtml = jtu.getJsonStr(
					mfOaFormConfigFeign.getFormByAction("MfOaArchivesEducationAction", BizPubParm.SHOW_TYPE3),
					"tableTag", mfOaArchivesEducationFeign.getByBaseId(mfOaArchivesEducation), null, true);
			dataMap.put("educationTableHtml", educationTableHtml);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model,String educationId,String baseId) throws Exception {
		ActionContext.initialize(request, response);FormService formService = new FormService();
		FormData formarchiveeducation0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaArchivesEducationAction", BizPubParm.SHOW_TYPE1));
		MfOaArchivesEducation mfOaArchivesEducation = new MfOaArchivesEducation();
		mfOaArchivesEducation.setEducationId(educationId);
		mfOaArchivesEducation = mfOaArchivesEducationFeign.getById(mfOaArchivesEducation);
		if (mfOaArchivesEducation == null) {
			mfOaArchivesEducation = new MfOaArchivesEducation();
			mfOaArchivesEducation.setBaseId(baseId);
		}
		getObjValue(formarchiveeducation0001, mfOaArchivesEducation);
		model.addAttribute("formarchiveeducation0001", formarchiveeducation0001);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesEducation_Insert";
	}


	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String educationId) throws Exception {
		ActionContext.initialize(request, response);FormService formService = new FormService();
		FormData formarchiveeducation0001 = formService.getFormData("archiveeducation0001");
		getFormValue(formarchiveeducation0001);
		MfOaArchivesEducation mfOaArchivesEducation = new MfOaArchivesEducation();
		mfOaArchivesEducation.setEducationId(educationId);
		mfOaArchivesEducation = mfOaArchivesEducationFeign.getById(mfOaArchivesEducation);
		getObjValue(formarchiveeducation0001, mfOaArchivesEducation);
		model.addAttribute("formarchiveeducation0001", formarchiveeducation0001);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesEducation_Detail";
	}


	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String educationId) throws Exception {
		ActionContext.initialize(request, response);FormService formService = new FormService();
		MfOaArchivesEducation mfOaArchivesEducation = new MfOaArchivesEducation();
		mfOaArchivesEducation.setEducationId(educationId);
		mfOaArchivesEducation = mfOaArchivesEducationFeign.getById(mfOaArchivesEducation);
		String fromId = mfOaFormConfigFeign.getFormByAction("MfOaArchivesEducationAction", BizPubParm.SHOW_TYPE2);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData formarchiveeducation0002 = formService.getFormData(fromId);
		getObjValue(formarchiveeducation0002, mfOaArchivesEducation, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formarchiveeducation0002, "propertySeeTag", "");
		if (mfOaArchivesEducation != null) {
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", mfOaArchivesEducation);
		return dataMap;
	}
}
