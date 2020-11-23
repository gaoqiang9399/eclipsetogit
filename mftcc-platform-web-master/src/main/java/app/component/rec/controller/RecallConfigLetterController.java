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
import app.component.rec.entity.RecallConfigLetter;
import app.component.rec.feign.MfCollectionFormConfigFeign;
import app.component.rec.feign.RecallConfigLetterFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: RecallConfigLetterAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 15:31:18 CST 2017
 **/
@Controller
@RequestMapping(value="/recallConfigLetter")
public class RecallConfigLetterController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private RecallConfigLetterFeign recallConfigLetteFeign;
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
	@RequestMapping(value="/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfigletter0002 = formService.getFormData("dlrecallconfigletter0002");
		RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
		Ipage ipage = this.getIpage();
		List<RecallConfigLetter> recallConfigLetterList = (List<RecallConfigLetter>) recallConfigLetteFeign.findByPage(ipage, recallConfigLetter)
				.getResult();
		model.addAttribute("formdlrecallconfigletter0002", formdlrecallconfigletter0002);
		model.addAttribute("recallConfigLetterList", recallConfigLetterList);
		return "RecallConfigLetter_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfigletter0002 = formService.getFormData("dlrecallconfigletter0002");
		RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
		List<RecallConfigLetter> recallConfigLetterList = recallConfigLetteFeign.getAll(recallConfigLetter);
		model.addAttribute("formdlrecallconfigletter0002", formdlrecallconfigletter0002);
		model.addAttribute("recallConfigLetterList", recallConfigLetterList);
		return "RecallConfigLetter_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			// formdlrecallconfigletter0003 =
			// formService.getFormData("dlrecallconfigletter0003");
			// getFormValue(formdlrecallconfigletter0003,
			// getMapByJson(ajaxData));
			FormService formService = new FormService();
			FormData formdlrecallconfig0001 = formService.getFormData("dlrecallconfig0001");
			getFormValue(formdlrecallconfig0001, getMapByJson(ajaxData));
			// if(this.validateFormData(formdlrecallconfigletter0003)){
			RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
			setObjValue(formdlrecallconfig0001, recallConfigLetter);
			List<RecallConfigLetter> list = recallConfigLetteFeign.getByRecKindNo(recallConfigLetter);
			if (list.size() == 0) {
				String letterNo = WaterIdUtil.getWaterId();
				recallConfigLetter.setLetterNo(letterNo);
				if ("1".equals(recallConfigLetter.getNoteCondition())) {
					recallConfigLetter.setModelName("信函催收" + "(应还款日前)");
				} else if ("2".equals(recallConfigLetter.getNoteCondition())) {
					recallConfigLetter.setModelName("信函催收" + "(逾期后)");
				} else {
					recallConfigLetter.setModelName("信函催收" + "(按固定日期)");
				}
				recallConfigLetter.setReturnAfterInteval("1");// 信函邮件规则的"逾期发送频率"默认每天发送.
				recallConfigLetteFeign.insert(recallConfigLetter);
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigLetter.getLetterNo());
				recallConfig.setRecallWay("3");
				recallConfig.setNoteCondition(recallConfigLetter.getNoteCondition() + recallConfigLetter.getRecType());
				recallConfig.setModelName(recallConfigLetter.getModelName());
				recallConfig.setRecallDesc(recallConfigLetter.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigLetter.getRecKindNo());
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
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
		try {
			FormData formdlrecallconfigletter0003 = formService.getFormData("dlrecallconfigletter0003");
			getFormValue(formdlrecallconfigletter0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlrecallconfigletter0003)) {
				recallConfigLetter = new RecallConfigLetter();
				setObjValue(formdlrecallconfigletter0003, recallConfigLetter);
				// List<RecallConfigLetter> list =
				// recallConfigLetteFeign.getByRecKindNo(recallConfigLetter);
				// if(list.size() == 0){
				recallConfigLetteFeign.update(recallConfigLetter);
				// getTableData();//获取列表
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigLetter.getLetterNo());
				recallConfig.setRecallWay("3");
				recallConfig.setNoteCondition(recallConfigLetter.getNoteCondition() + recallConfigLetter.getRecType());
				recallConfig.setModelName(recallConfigLetter.getModelName());
				recallConfig.setRecallDesc(recallConfigLetter.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigLetter.getRecKindNo());
				dataMap.put("recItem", recallConfig);
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
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String letterNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formdlrecallconfigletter0002 = formService.getFormData("dlrecallconfigletter0002");
		RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
		recallConfigLetter.setLetterNo(letterNo);
		recallConfigLetter = recallConfigLetteFeign.getById(recallConfigLetter);
		getObjValue(formdlrecallconfigletter0002, recallConfigLetter, formData);
		if (recallConfigLetter != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	@RequestMapping(value="/getById")
	public String getById(Model model,String letterNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfigletter0003 = formService.getFormData("dlrecallconfigletter0003");
		getFormValue(formdlrecallconfigletter0003);
		RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
		recallConfigLetter.setLetterNo(letterNo);
		recallConfigLetter = recallConfigLetteFeign.getById(recallConfigLetter);
		getObjValue(formdlrecallconfigletter0003, recallConfigLetter);
		model.addAttribute("formdlrecallconfigletter0003", formdlrecallconfigletter0003);
		return "RecallConfigLetter_Detail";
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/deleteAjax")
	public Map<String,Object> deleteAjax(String letterNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
		recallConfigLetter.setLetterNo(letterNo);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// recallConfigLetter = (RecallConfigLetter)JSONObject.toBean(jb,
			// RecallConfigLetter.class);
			recallConfigLetteFeign.delete(recallConfigLetter);
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
	@RequestMapping(value="/input")
	public String input(Model model,String collectionPlanNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
		recallConfigLetter.setCollectionPlanNo(collectionPlanNo);
		String formId =null;
		MfCollectionFormConfig mfCollectionFormConfig = mfCollectionFormConfigFeign.getByCollectionTypeAndAction("1",
				"RecallConfigLetterAction");

		if (mfCollectionFormConfig == null) {

		} else {
			formId = mfCollectionFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			Log.error("催收类型为" + mfCollectionFormConfig.getFormType() + "的RecallConfigLetterAction表单信息没有查询到");
		} else {
			FormData formdlrecallconfigletter0003 = formService.getFormData(formId);
			if (formdlrecallconfigletter0003.getFormId() == null) {
//				logger.error("催收类型为" + mfCollectionFormConfig.getFormType() + "的RecallConfigLetterAction表单form" + formId
//						+ ".xml文件不存在");
			} else {
				getFormValue(formdlrecallconfigletter0003);
				getObjValue(formdlrecallconfigletter0003, recallConfigLetter);
				model.addAttribute("formdlrecallconfigletter0003", formdlrecallconfigletter0003);
			}
		}

		return "RecallConfigLetter_Insert";
	}

	@ResponseBody
	@RequestMapping(value="/updateAjaxByOne")
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formdlrecallconfigletter0004 = formService.getFormData("dlrecallconfigletter0004");
		getFormValue(formdlrecallconfigletter0004, getMapByJson(ajaxData));

		RecallConfigLetter recallConfigLetterJsp = new RecallConfigLetter();
		setObjValue(formdlrecallconfigletter0004, recallConfigLetterJsp);

		RecallConfigLetter recallConfigLetter = recallConfigLetteFeign.getById(recallConfigLetterJsp);
		if (recallConfigLetter != null) {
			try {
				recallConfigLetter = (RecallConfigLetter) EntityUtil.reflectionSetVal(recallConfigLetter,
						recallConfigLetterJsp, getMapByJson(ajaxData));
				recallConfigLetteFeign.update(recallConfigLetter);

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
