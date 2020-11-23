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
import app.component.oa.archive.entity.MfOaArchivesFamily;
import app.component.oa.archive.feign.MfOaArchivesFamilyFeign;
import app.component.oa.feign.MfOaFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;

@Controller
@RequestMapping("/mfOaArchivesFamily")
public class MfOaArchivesFamilyController extends BaseFormBean {
	@Autowired
	private MfOaArchivesFamilyFeign mfOaArchivesFamilyFeign;
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
		return "/component/oa/archive/MfOaArchivesFamily_List";
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
		MfOaArchivesFamily mfOaArchivesFamily = new MfOaArchivesFamily();
		try {
			mfOaArchivesFamily.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaArchivesFamily.setCriteriaList(mfOaArchivesFamily, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaArchivesFamily,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaArchivesFamily", mfOaArchivesFamily));
			ipage = mfOaArchivesFamilyFeign.findByPage(ipage);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formarchivefamily0001 = formService.getFormData(
					mfOaFormConfigFeign.getFormByAction("MfOaArchivesFamilyAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formarchivefamily0001, getMapByJson(ajaxData));
			if (this.validateFormData(formarchivefamily0001)) {
				MfOaArchivesFamily mfOaArchivesFamily = new MfOaArchivesFamily();
				setObjValue(formarchivefamily0001, mfOaArchivesFamily);
				MfOaArchivesFamily mfOaArchivesFamilyOld = mfOaArchivesFamilyFeign.getById(mfOaArchivesFamily);
				if (mfOaArchivesFamilyOld == null) {
					mfOaArchivesFamily.setFamilyId(WaterIdUtil.getWaterId());
					mfOaArchivesFamilyFeign.insert(mfOaArchivesFamily);
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				} else {
					mfOaArchivesFamilyFeign.update(mfOaArchivesFamily);
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}
				JsonTableUtil jtu = new JsonTableUtil();
				String familyTableHtml = jtu.getJsonStr(
						mfOaFormConfigFeign.getFormByAction("MfOaArchivesFamilyAction", BizPubParm.SHOW_TYPE3),
						"tableTag", mfOaArchivesFamilyFeign.getByBaseId(mfOaArchivesFamily), null, true);
				dataMap.put("familyTableHtml", familyTableHtml);
				dataMap.put("flag", "success");
				dataMap.put("mfOaArchivesFamily", mfOaArchivesFamily);
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
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String familyId, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesFamily mfOaArchivesFamily = new MfOaArchivesFamily();
		mfOaArchivesFamily.setFamilyId(familyId);
		mfOaArchivesFamily.setBaseId(baseId);
		try {
			mfOaArchivesFamilyFeign.delete(mfOaArchivesFamily);
			JsonTableUtil jtu = new JsonTableUtil();
			String familyTableHtml = jtu.getJsonStr(
					mfOaFormConfigFeign.getFormByAction("MfOaArchivesFamilyAction", BizPubParm.SHOW_TYPE3), "tableTag",
					mfOaArchivesFamilyFeign.getByBaseId(mfOaArchivesFamily), null, true);
			dataMap.put("familyTableHtml", familyTableHtml);
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
	public String input(Model model, String familyId, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchivefamily0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaArchivesFamilyAction", BizPubParm.SHOW_TYPE1));
		MfOaArchivesFamily mfOaArchivesFamily = new MfOaArchivesFamily();
		mfOaArchivesFamily.setFamilyId(familyId);
		mfOaArchivesFamily = mfOaArchivesFamilyFeign.getById(mfOaArchivesFamily);
		if (mfOaArchivesFamily == null) {
			mfOaArchivesFamily = new MfOaArchivesFamily();
			mfOaArchivesFamily.setBaseId(baseId);
		}
		getObjValue(formarchivefamily0001, mfOaArchivesFamily);

		model.addAttribute("formarchivefamily0001", formarchivefamily0001);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesFamily_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String familyId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchivefamily0001 = formService.getFormData("archivefamily0001");
		getFormValue(formarchivefamily0001);
		MfOaArchivesFamily mfOaArchivesFamily = new MfOaArchivesFamily();
		mfOaArchivesFamily.setFamilyId(familyId);
		mfOaArchivesFamily = mfOaArchivesFamilyFeign.getById(mfOaArchivesFamily);
		getObjValue(formarchivefamily0001, mfOaArchivesFamily);
		model.addAttribute("formarchivefamily0001", formarchivefamily0001);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesFamily_Detail";
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String familyId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaArchivesFamily mfOaArchivesFamily = new MfOaArchivesFamily();
		mfOaArchivesFamily.setFamilyId(familyId);
		mfOaArchivesFamily = mfOaArchivesFamilyFeign.getById(mfOaArchivesFamily);
		String fromId = mfOaFormConfigFeign.getFormByAction("MfOaArchivesFamilyAction", BizPubParm.SHOW_TYPE2);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData formarchivefamily0002 = formService.getFormData(fromId);
		getObjValue(formarchivefamily0002, mfOaArchivesFamily, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formarchivefamily0002, "propertySeeTag", "");
		if (mfOaArchivesFamily != null) {
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", mfOaArchivesFamily);
		return dataMap;
	}

}
