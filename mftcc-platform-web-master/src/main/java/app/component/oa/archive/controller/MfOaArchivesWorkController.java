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
import app.component.oa.archive.entity.MfOaArchivesWork;
import app.component.oa.archive.feign.MfOaArchivesWorkFeign;
import app.component.oa.feign.MfOaFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;

@Controller
@RequestMapping("/mfOaArchivesWork")
public class MfOaArchivesWorkController extends BaseFormBean {
	@Autowired
	private MfOaArchivesWorkFeign mfOaArchivesWorkFeign;
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
		return "/component/oa/archive/MfOaArchivesWork_List";
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
			String tableType, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesWork mfOaArchivesWork = new MfOaArchivesWork();
		mfOaArchivesWork.setBaseId(baseId);
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaArchivesWork", mfOaArchivesWork));
			ipage = mfOaArchivesWorkFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), null, true);
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formarchivework0002 = formService
					.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaArchivesWorkAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formarchivework0002, getMapByJson(ajaxData));
			if (this.validateFormData(formarchivework0002)) {
				MfOaArchivesWork mfOaArchivesWork = new MfOaArchivesWork();
				setObjValue(formarchivework0002, mfOaArchivesWork);
				if ("".equals(mfOaArchivesWork.getWorkId())) {
					mfOaArchivesWork.setWorkId(WaterIdUtil.getWaterId());
					mfOaArchivesWorkFeign.insert(mfOaArchivesWork);
				} else {
					mfOaArchivesWorkFeign.update(mfOaArchivesWork);
				}
				JsonTableUtil jtu = new JsonTableUtil();
				String workTableHtml = jtu.getJsonStr(
						mfOaFormConfigFeign.getFormByAction("MfOaArchivesWorkAction", BizPubParm.SHOW_TYPE3),
						"tableTag", mfOaArchivesWorkFeign.getByBaseId(mfOaArchivesWork), null, true);
				dataMap.put("workTableHtml", workTableHtml);
				dataMap.put("mfOaArchivesWork", mfOaArchivesWork);
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
	public Map<String, Object> deleteAjax(String workId, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesWork mfOaArchivesWork = new MfOaArchivesWork();
		mfOaArchivesWork.setWorkId(workId);
		mfOaArchivesWork.setBaseId(baseId);
		try {
			mfOaArchivesWorkFeign.delete(mfOaArchivesWork);
			JsonTableUtil jtu = new JsonTableUtil();
			String workTableHtml = jtu.getJsonStr(
					mfOaFormConfigFeign.getFormByAction("MfOaArchivesWorkAction", BizPubParm.SHOW_TYPE3), "tableTag",
					mfOaArchivesWorkFeign.getByBaseId(mfOaArchivesWork), null, true);
			dataMap.put("workTableHtml", workTableHtml);
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
	@RequestMapping("/input")
	public String input(Model model, String workId, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchivework0002 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaArchivesWorkAction", BizPubParm.SHOW_TYPE1));
		MfOaArchivesWork mfOaArchivesWork = new MfOaArchivesWork();
		mfOaArchivesWork.setWorkId(workId);
		mfOaArchivesWork = mfOaArchivesWorkFeign.getById(mfOaArchivesWork);
		if (mfOaArchivesWork == null) {
			mfOaArchivesWork = new MfOaArchivesWork();
			mfOaArchivesWork.setBaseId(baseId);
		}
		getObjValue(formarchivework0002, mfOaArchivesWork);
		model.addAttribute("formarchivework0002", formarchivework0002);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesWork_Insert";
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String workId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaArchivesWork mfOaArchivesWork = new MfOaArchivesWork();
		mfOaArchivesWork.setWorkId(workId);
		mfOaArchivesWork = mfOaArchivesWorkFeign.getById(mfOaArchivesWork);
		String fromId = mfOaFormConfigFeign.getFormByAction("MfOaArchivesWorkAction", BizPubParm.SHOW_TYPE2);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData formarchivework0002 = formService.getFormData(fromId);
		getObjValue(formarchivework0002, mfOaArchivesWork, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formarchivework0002, "propertySeeTag", "");
		if (mfOaArchivesWork != null) {
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", mfOaArchivesWork);
		return dataMap;
	}

}
