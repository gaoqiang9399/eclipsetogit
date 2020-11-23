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
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.rec.entity.MfCollectionFormConfig;
import app.component.rec.entity.RecallConfig;
import app.component.rec.entity.RecallConfigLaw;
import app.component.rec.feign.MfCollectionFormConfigFeign;
import app.component.rec.feign.RecallConfigLawFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: RecallConfigLawAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 17:49:46 CST 2017
 **/
@Controller
@RequestMapping(value = "/recallConfigLaw")
public class RecallConfigLawController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private RecallConfigLawFeign recallConfigLawFeign;
	@Autowired
	private MfCollectionFormConfigFeign mfCollectionFormConfigFeign;
	// 全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	private Map<String, Object> dataMap;

	/**
	 * 获取列表数据
	 * 
	 * @param tableId
	 * @param recallConfigLaw
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTableData")
	private void getTableData(String tableId, RecallConfigLaw recallConfigLaw) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", recallConfigLawFeign.getAll(recallConfigLaw), null,
				true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfiglaw0002 = formService.getFormData("dlrecallconfiglaw0002");
		RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
		Ipage ipage = this.getIpage();
		List<RecallConfigLaw> recallConfigLawList = (List<RecallConfigLaw>) recallConfigLawFeign.findByPage(ipage, recallConfigLaw)
				.getResult();
		model.addAttribute("formdlrecallconfiglaw0002", formdlrecallconfiglaw0002);
		model.addAttribute("recallConfigLawList", recallConfigLawList);
		return "RecallConfigLaw_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfiglaw0002 = formService.getFormData("dlrecallconfiglaw0002");
		RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
		List<RecallConfigLaw> recallConfigLawList = recallConfigLawFeign.getAll(recallConfigLaw);
		model.addAttribute("formdlrecallconfiglaw0002", formdlrecallconfiglaw0002);
		model.addAttribute("recallConfigLawList", recallConfigLawList);
		return "RecallConfigLaw_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			FormData formdlrecallconfig0001 = formService.getFormData("dlrecallconfig0001");
			getFormValue(formdlrecallconfig0001, getMapByJson(ajaxData));
			RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
			setObjValue(formdlrecallconfig0001, recallConfigLaw);
			List<RecallConfigLaw> list = recallConfigLawFeign.getByRecKindNo(recallConfigLaw);
			if (list.size() == 0) {
				String lawNo = WaterIdUtil.getWaterId();
				recallConfigLaw.setLawNo(lawNo);
				recallConfigLawFeign.insert(recallConfigLaw);
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigLaw.getLawNo());
				recallConfig.setRecallWay("6");
				recallConfig.setNoteCondition(recallConfigLaw.getRecType());
				recallConfig.setModelName("法务催收");
				recallConfig.setRecallDesc(recallConfigLaw.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigLaw.getRecKindNo());
				dataMap.put("recItem", recallConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "该产品已增加过此种催收方案");
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
		try {
			FormData formdlrecallconfiglaw0003 = formService.getFormData("dlrecallconfiglaw0003");
			getFormValue(formdlrecallconfiglaw0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlrecallconfiglaw0003)) {
				recallConfigLaw = new RecallConfigLaw();
				setObjValue(formdlrecallconfiglaw0003, recallConfigLaw);
				// List<RecallConfigLaw> list =
				// recallConfigLawFeign.getByRecKindNo(recallConfigLaw);
				// if(list.size() == 0){
				recallConfigLawFeign.update(recallConfigLaw);
				// getTableData();//获取列表
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigLaw.getLawNo());
				recallConfig.setRecallWay("6");
				recallConfig.setNoteCondition(recallConfigLaw.getRecType());
				recallConfig.setModelName("法务催收");
				recallConfig.setRecallDesc(recallConfigLaw.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigLaw.getRecKindNo());
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
	public Map<String, Object> getByIdAjax(String lawNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formdlrecallconfiglaw0002 = formService.getFormData("dlrecallconfiglaw0002");
		RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
		recallConfigLaw.setLawNo(lawNo);
		recallConfigLaw = recallConfigLawFeign.getById(recallConfigLaw);
		getObjValue(formdlrecallconfiglaw0002, recallConfigLaw, formData);
		if (recallConfigLaw != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	@RequestMapping(value = "/getById")
	public String getById(Model model,String lawNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfiglaw0003 = formService.getFormData("dlrecallconfiglaw0003");
		getFormValue(formdlrecallconfiglaw0003);
		RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
		recallConfigLaw.setLawNo(lawNo);
		recallConfigLaw = recallConfigLawFeign.getById(recallConfigLaw);
		getObjValue(formdlrecallconfiglaw0003, recallConfigLaw);
		model.addAttribute("formdlrecallconfiglaw0003", formdlrecallconfiglaw0003);
		return "RecallConfigLaw_Detail";
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String lawNo) throws Exception {
		ActionContext.initialize(request, response);

		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
		recallConfigLaw.setLawNo(lawNo);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// recallConfigLaw = (RecallConfigLaw)JSONObject.toBean(jb,
			// RecallConfigLaw.class);
			recallConfigLawFeign.delete(recallConfigLaw);
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
	@RequestMapping(value = "/input")
	public String input(Model model,String collectionPlanNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
		recallConfigLaw.setCollectionPlanNo(collectionPlanNo);
		String formId = null;
		MfCollectionFormConfig mfCollectionFormConfig = mfCollectionFormConfigFeign.getByCollectionTypeAndAction("1",
				"RecallConfigLawAction");

		if (mfCollectionFormConfig == null) {

		} else {
			formId = mfCollectionFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			Log.error("催收类型为" + mfCollectionFormConfig.getFormType() + "的RecallConfigLawAction表单信息没有查询到");
		} else {
			FormData formdlrecallconfiglaw0003 = formService.getFormData(formId);
			if (formdlrecallconfiglaw0003.getFormId() == null) {
//				logger.error("催收类型为" + mfCollectionFormConfig.getFormType() + "的RecallConfigLawAction表单form" + formId
//						+ ".xml文件不存在");
			} else {
				getFormValue(formdlrecallconfiglaw0003);
				getObjValue(formdlrecallconfiglaw0003, recallConfigLaw);
				model.addAttribute("formdlrecallconfiglaw0003", formdlrecallconfiglaw0003);
			}
		}

		return "RecallConfigLaw_Insert";
	}

	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 

		FormData formdlrecallconfiglaw0004 = formService.getFormData("dlrecallconfiglaw0004");
		getFormValue(formdlrecallconfiglaw0004, getMapByJson(ajaxData));

		RecallConfigLaw recallConfigLawJsp = new RecallConfigLaw();
		setObjValue(formdlrecallconfiglaw0004, recallConfigLawJsp);

		RecallConfigLaw recallConfigLaw = recallConfigLawFeign.getById(recallConfigLawJsp);
		if (recallConfigLaw != null) {
			try {
				recallConfigLaw = (RecallConfigLaw) EntityUtil.reflectionSetVal(recallConfigLaw, recallConfigLawJsp,
						getMapByJson(ajaxData));
				recallConfigLawFeign.update(recallConfigLaw);

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
