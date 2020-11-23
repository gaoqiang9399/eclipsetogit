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
import app.component.rec.entity.RecallConfigOut;
import app.component.rec.feign.MfCollectionFormConfigFeign;
import app.component.rec.feign.RecallConfigOutFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: RecallConfigOutAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 17:09:28 CST 2017
 **/
@Controller
@RequestMapping(value = "/recallConfigOut")
public class RecallConfigOutController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private RecallConfigOutFeign recallConfigOutFeign;
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
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfigout0002 = formService.getFormData("dlrecallconfigout0002");
		RecallConfigOut recallConfigOut = new RecallConfigOut();
		Ipage ipage = this.getIpage();
		List<RecallConfigOut> recallConfigOutList = (List<RecallConfigOut>) recallConfigOutFeign
				.findByPage(ipage, recallConfigOut).getResult();
		model.addAttribute("formdlrecallconfigout0002", formdlrecallconfigout0002);
		model.addAttribute("recallConfigOutList", recallConfigOutList);
		return "RecallConfigOut_List";
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
		FormData formdlrecallconfigout0002 = formService.getFormData("dlrecallconfigout0002");
		RecallConfigOut recallConfigOut = new RecallConfigOut();
		List<RecallConfigOut> recallConfigOutList = recallConfigOutFeign.getAll(recallConfigOut);
		model.addAttribute("formdlrecallconfigout0002", formdlrecallconfigout0002);
		model.addAttribute("recallConfigOutList", recallConfigOutList);
		return "RecallConfigOut_List";
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
			// formdlrecallconfigout0003 =
			// formService.getFormData("dlrecallconfigout0003");
			// getFormValue(formdlrecallconfigout0003, getMapByJson(ajaxData));
			FormData formdlrecallconfig0001 = formService.getFormData("dlrecallconfig0001");
			getFormValue(formdlrecallconfig0001, getMapByJson(ajaxData));
			// if(this.validateFormData(formdlrecallconfigout0003)){
			RecallConfigOut recallConfigOut = new RecallConfigOut();
			setObjValue(formdlrecallconfig0001, recallConfigOut);
			List<RecallConfigOut> list = recallConfigOutFeign.getByRecKindNo(recallConfigOut);
			if (list.size() == 0) {
				String outNo = WaterIdUtil.getWaterId();
				recallConfigOut.setOutNo(outNo);
				recallConfigOutFeign.insert(recallConfigOut);
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigOut.getOutNo());
				recallConfig.setRecallWay("5");
				recallConfig.setNoteCondition(recallConfigOut.getRecType());
				recallConfig.setModelName("委外催收");
				recallConfig.setRecallDesc(recallConfigOut.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigOut.getRecKindNo());
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfigOut recallConfigOut = new RecallConfigOut();
		try {
			FormData formdlrecallconfigout0003 = formService.getFormData("dlrecallconfigout0003");
			getFormValue(formdlrecallconfigout0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlrecallconfigout0003)) {
				recallConfigOut = new RecallConfigOut();
				setObjValue(formdlrecallconfigout0003, recallConfigOut);
				// List<RecallConfigOut> list =
				// recallConfigOutFeign.getByRecKindNo(recallConfigOut);
				// if(list.size() == 0){
				recallConfigOutFeign.update(recallConfigOut);
				// getTableData();//获取列表
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigOut.getOutNo());
				recallConfig.setRecallWay("5");
				recallConfig.setNoteCondition(recallConfigOut.getRecType());
				recallConfig.setModelName("委外催收");
				recallConfig.setRecallDesc(recallConfigOut.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigOut.getRecKindNo());
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
	public Map<String, Object> getByIdAjax(String outNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formdlrecallconfigout0002 = formService.getFormData("dlrecallconfigout0002");
		RecallConfigOut recallConfigOut = new RecallConfigOut();
		recallConfigOut.setOutNo(outNo);
		recallConfigOut = recallConfigOutFeign.getById(recallConfigOut);
		getObjValue(formdlrecallconfigout0002, recallConfigOut, formData);
		if (recallConfigOut != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	@RequestMapping(value = "/getById")
	public String getById(Model model, String outNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfigout0003 = formService.getFormData("dlrecallconfigout0003");
		getFormValue(formdlrecallconfigout0003);
		RecallConfigOut recallConfigOut = new RecallConfigOut();
		recallConfigOut.setOutNo(outNo);
		recallConfigOut = recallConfigOutFeign.getById(recallConfigOut);
		getObjValue(formdlrecallconfigout0003, recallConfigOut);
		model.addAttribute("formdlrecallconfigout0003", formdlrecallconfigout0003);
		return "RecallConfigOut_Detail";
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String outNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfigOut recallConfigOut = new RecallConfigOut();
		recallConfigOut.setOutNo(outNo);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// recallConfigOut = (RecallConfigOut)JSONObject.toBean(jb,
			// RecallConfigOut.class);
			recallConfigOutFeign.delete(recallConfigOut);
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
	 * @param collectionPlanNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String collectionPlanNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		RecallConfigOut recallConfigOut = new RecallConfigOut();
		recallConfigOut.setCollectionPlanNo(collectionPlanNo);

		MfCollectionFormConfig mfCollectionFormConfig = mfCollectionFormConfigFeign.getByCollectionTypeAndAction("1",
				"RecallConfigOutAction");

		String formId = null;
		if (mfCollectionFormConfig == null) {

		} else {
			formId = mfCollectionFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("催收类型为" + mfCollectionFormConfig.getFormType() +
			// "的RecallConfigOutAction表单信息没有查询到");
		} else {
			FormData formdlrecallconfigout0003 = formService.getFormData(formId);
			if (formdlrecallconfigout0003.getFormId() == null) {
				// logger.error("催收类型为" + mfCollectionFormConfig.getFormType() +
				// "的RecallConfigOutAction表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlrecallconfigout0003);
				getObjValue(formdlrecallconfigout0003, recallConfigOut);
				model.addAttribute("formdlrecallconfigout0003", formdlrecallconfigout0003);
			}
		}

		return "RecallConfigOut_Insert";
	}

	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 

		FormData formdlrecallconfigout0004 = formService.getFormData("dlrecallconfigout0004");
		getFormValue(formdlrecallconfigout0004, getMapByJson(ajaxData));

		RecallConfigOut recallConfigOutJsp = new RecallConfigOut();
		setObjValue(formdlrecallconfigout0004, recallConfigOutJsp);

		RecallConfigOut recallConfigOut = recallConfigOutFeign.getById(recallConfigOutJsp);
		if (recallConfigOut != null) {
			try {
				recallConfigOut = (RecallConfigOut) EntityUtil.reflectionSetVal(recallConfigOut, recallConfigOutJsp,
						getMapByJson(ajaxData));
				recallConfigOutFeign.update(recallConfigOut);

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
