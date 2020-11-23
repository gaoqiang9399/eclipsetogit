package app.component.rec.controller;

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

import app.component.common.EntityUtil;
import app.component.rec.entity.MfCollectionFormConfig;
import app.component.rec.entity.RecallConfig;
import app.component.rec.entity.RecallConfigVisit;
import app.component.rec.feign.MfCollectionFormConfigFeign;
import app.component.rec.feign.RecallConfigVisitFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: RecallConfigVisitAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 16:29:22 CST 2017
 **/
@Controller
@RequestMapping(value = "/recallConfigVisit")
public class RecallConfigVisitController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private RecallConfigVisitFeign recallConfigVisitFeign;
	@Autowired
	private MfCollectionFormConfigFeign mfCollectionFormConfigFeign;

	// 全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfigvisit0002 = formService.getFormData("dlrecallconfigvisit0002");
		RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
		Ipage ipage = this.getIpage();
		List<RecallConfigVisit> recallConfigVisitList = (List<RecallConfigVisit>) recallConfigVisitFeign
				.findByPage(ipage, recallConfigVisit).getResult();
		model.addAttribute("formdlrecallconfigvisit0002", formdlrecallconfigvisit0002);
		model.addAttribute("recallConfigVisitList", recallConfigVisitList);
		return "RecallConfigVisit_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfigvisit0002 = formService.getFormData("dlrecallconfigvisit0002");
		RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
		List<RecallConfigVisit> recallConfigVisitList = recallConfigVisitFeign.getAll(recallConfigVisit);
		model.addAttribute("formdlrecallconfigvisit0002", formdlrecallconfigvisit0002);
		model.addAttribute("recallConfigVisitList", recallConfigVisitList);
		return "RecallConfigVisit_List";
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
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			// formdlrecallconfigvisit0003 =
			// formService.getFormData("dlrecallconfigvisit0003");
			// getFormValue(formdlrecallconfigvisit0003,
			// getMapByJson(ajaxData));
			FormData formdlrecallconfig0001 = formService.getFormData("dlrecallconfig0001");
			getFormValue(formdlrecallconfig0001, getMapByJson(ajaxData));
			// if(this.validateFormData(formdlrecallconfigvisit0003)){
			RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
			setObjValue(formdlrecallconfig0001, recallConfigVisit);
			List<RecallConfigVisit> list = recallConfigVisitFeign.getByRecKindNo(recallConfigVisit);
			if (list.size() == 0) {
				String visitNo = WaterIdUtil.getWaterId();
				recallConfigVisit.setVisitNo(visitNo);
				recallConfigVisitFeign.insert(recallConfigVisit);
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigVisit.getVisitNo());
				recallConfig.setRecallWay("4");
				recallConfig.setNoteCondition(recallConfigVisit.getRecType());
				recallConfig.setModelName("外访催收");
				recallConfig.setRecallDesc(recallConfigVisit.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigVisit.getRecKindNo());
				dataMap.put("recItem", recallConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "该产品已增加过此种催收方案");
			}
			// getTableData();//获取列表
			/**
			 * MfCollectionFormConfig mfCollectionFormConfig =
			 * mfCollectionFormConfigFeign.getByCollectionTypeAndAction("1","RecallConfigVisitAction");
			 * if(mfCollectionFormConfig == null){
			 * 
			 * }else{ formId = mfCollectionFormConfig.getShowModelDef(); }
			 * formdlrecallconfigvisit0004 = formService.getFormData(formId);
			 * if(formdlrecallconfigvisit0004.getFormId() == null){
			 * log.error("催收类型为"+mfCollectionFormConfig.getFormType()+"的RecallConfigVisitAction表单form"+formId+".xml文件不存在");
			 * } getFormValue(formdlrecallconfigvisit0004);
			 * getObjValue(formdlrecallconfigvisit0004, recallConfigVisit);
			 * JsonFormUtil jsonFormUtil = new JsonFormUtil(); String htmlStr =
			 * jsonFormUtil.getJsonStr(formdlrecallconfigvisit0004,
			 * "propertySeeTag", query);
			 * 
			 * dataMap.put("htmlStr", htmlStr); dataMap.put("htmlStrFlag","1");
			 **/
			// dataMap.put("flag", "success");
			// dataMap.put("msg", "新增成功");
			// }else{
			// dataMap.put("flag", "error");
			// dataMap.put("msg",this.getFormulavaliErrorMsg());
			// }
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
		try {
			FormData formdlrecallconfigvisit0003 = formService.getFormData("dlrecallconfigvisit0003");
			getFormValue(formdlrecallconfigvisit0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlrecallconfigvisit0003)) {
				recallConfigVisit = new RecallConfigVisit();
				setObjValue(formdlrecallconfigvisit0003, recallConfigVisit);
				// List<RecallConfigVisit> list =
				// recallConfigVisitFeign.getByRecKindNo(recallConfigVisit);
				// if(list.size() == 0){
				recallConfigVisitFeign.update(recallConfigVisit);
				// getTableData();//获取列表
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigVisit.getVisitNo());
				recallConfig.setRecallWay("4");
				recallConfig.setNoteCondition(recallConfigVisit.getRecType());
				recallConfig.setModelName("外访催收");
				recallConfig.setRecallDesc(recallConfigVisit.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigVisit.getRecKindNo());
				dataMap.put("recItem", recallConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
				// }else{
				// dataMap.put("flag", "error");
				// dataMap.put("msg", "该产品已增加过此种催收方案");
				// }
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
	public Map<String, Object> getByIdAjax(String visitNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formdlrecallconfigvisit0002 = formService.getFormData("dlrecallconfigvisit0002");
		RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
		recallConfigVisit.setVisitNo(visitNo);
		recallConfigVisit = recallConfigVisitFeign.getById(recallConfigVisit);
		getObjValue(formdlrecallconfigvisit0002, recallConfigVisit, formData);
		if (recallConfigVisit != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	public String getById(Model model, String visitNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfigvisit0003 = formService.getFormData("dlrecallconfigvisit0003");
		getFormValue(formdlrecallconfigvisit0003);
		RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
		recallConfigVisit.setVisitNo(visitNo);
		recallConfigVisit = recallConfigVisitFeign.getById(recallConfigVisit);
		getObjValue(formdlrecallconfigvisit0003, recallConfigVisit);
		model.addAttribute("formdlrecallconfigvisit0003", formdlrecallconfigvisit0003);
		return "RecallConfigVisit_Detail";
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String visitNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
		recallConfigVisit.setVisitNo(visitNo);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// recallConfigVisit = (RecallConfigVisit)JSONObject.toBean(jb,
			// RecallConfigVisit.class);
			recallConfigVisitFeign.delete(recallConfigVisit);
			// getTableData();//获取列表
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
	public String input(Model model, String collectionPlanNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
		recallConfigVisit.setCollectionPlanNo(collectionPlanNo);

		MfCollectionFormConfig mfCollectionFormConfig = mfCollectionFormConfigFeign.getByCollectionTypeAndAction("1",
				"RecallConfigVisitAction");

		String formId = null;
		if (mfCollectionFormConfig == null) {

		} else {
			formId = mfCollectionFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("催收类型为" + mfCollectionFormConfig.getFormType() +
			// "的RecallConfigVisitAction表单信息没有查询到");
		} else {
			FormData formdlrecallconfigvisit0003 = formService.getFormData(formId);
			if (formdlrecallconfigvisit0003.getFormId() == null) {
				// logger.error("催收类型为" + mfCollectionFormConfig.getFormType() +
				// "的RecallConfigVisitAction表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlrecallconfigvisit0003);
				getObjValue(formdlrecallconfigvisit0003, recallConfigVisit);
				model.addAttribute("formdlrecallconfigvisit0003", formdlrecallconfigvisit0003);
			}
		}

		return "RecallConfigVisit_Insert";
	}

	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formdlrecallconfigvisit0004 = formService.getFormData("dlrecallconfigvisit0004");
		getFormValue(formdlrecallconfigvisit0004, getMapByJson(ajaxData));

		RecallConfigVisit recallConfigVisitJsp = new RecallConfigVisit();
		setObjValue(formdlrecallconfigvisit0004, recallConfigVisitJsp);

		RecallConfigVisit recallConfigVisit = recallConfigVisitFeign.getById(recallConfigVisitJsp);
		if (recallConfigVisit != null) {
			try {
				recallConfigVisit = (RecallConfigVisit) EntityUtil.reflectionSetVal(recallConfigVisit,
						recallConfigVisitJsp, getMapByJson(ajaxData));
				recallConfigVisitFeign.update(recallConfigVisit);

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

}
