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
import app.component.rec.entity.RecallConfigPhone;
import app.component.rec.feign.MfCollectionFormConfigFeign;
import app.component.rec.feign.RecallConfigPhoneFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: RecallConfigPhoneAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 09:28:27 CST 2017
 **/
@Controller
@RequestMapping(value = "/recallConfigPhone")
public class RecallConfigPhoneController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private RecallConfigPhoneFeign recallConfigPhoneFeign;
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
		FormData formrecallconfigphone0002 = formService.getFormData("recallconfigphone0002");
		RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
		Ipage ipage = this.getIpage();
		List<RecallConfigPhone> recallConfigPhoneList = (List<RecallConfigPhone>) recallConfigPhoneFeign
				.findByPage(ipage, recallConfigPhone).getResult();
		model.addAttribute("formrecallconfigphone0002", formrecallconfigphone0002);
		model.addAttribute("recallConfigPhoneList", recallConfigPhoneList);
		return "RecallConfigPhone_List";
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
		FormData formrecallconfigphone0002 = formService.getFormData("recallconfigphone0002");
		RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
		List<RecallConfigPhone> recallConfigPhoneList = recallConfigPhoneFeign.getAll(recallConfigPhone);
		model.addAttribute("formrecallconfigphone0002", formrecallconfigphone0002);
		model.addAttribute("recallConfigPhoneList", recallConfigPhoneList);
		return "RecallConfigPhone_List";
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
			// formdlrecallconfigphone0003 =
			// formService.getFormData("dlrecallconfigphone0003");
			// getFormValue(formdlrecallconfigphone0003,
			// getMapByJson(ajaxData));
			FormData formdlrecallconfig0001 = formService.getFormData("dlrecallconfig0001");
			getFormValue(formdlrecallconfig0001, getMapByJson(ajaxData));
			// if(this.validateFormData(formdlrecallconfigphone0003)){
			RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
			setObjValue(formdlrecallconfig0001, recallConfigPhone);
			List<RecallConfigPhone> list = recallConfigPhoneFeign.getByRecKindNo(recallConfigPhone);
			if (list.size() == 0) {
				String phoneNo = WaterIdUtil.getWaterId();
				recallConfigPhone.setPhoneNo(phoneNo);
				recallConfigPhoneFeign.insert(recallConfigPhone);
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigPhone.getPhoneNo());
				recallConfig.setRecallWay("2");
				recallConfig.setNoteCondition(recallConfigPhone.getRecType());
				recallConfig.setModelName("电话催收");
				recallConfig.setRecallDesc(recallConfigPhone.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigPhone.getRecKindNo());
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
		RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
		try {
			FormData formdlrecallconfigphone0003 = formService.getFormData("dlrecallconfigphone0003");
			getFormValue(formdlrecallconfigphone0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlrecallconfigphone0003)) {
				recallConfigPhone = new RecallConfigPhone();
				setObjValue(formdlrecallconfigphone0003, recallConfigPhone);
				// List<RecallConfigPhone> list =
				// recallConfigPhoneFeign.getByRecKindNo(recallConfigPhone);
				// if(list.size() == 0){
				recallConfigPhoneFeign.update(recallConfigPhone);
				// getTableData();//获取列表
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigPhone.getPhoneNo());
				recallConfig.setRecallWay("2");
				recallConfig.setNoteCondition(recallConfigPhone.getRecType());
				recallConfig.setModelName("电话催收");
				recallConfig.setRecallDesc(recallConfigPhone.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigPhone.getRecKindNo());
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
	public Map<String, Object> getByIdAjax(String phoneNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formrecallconfigphone0002 = formService.getFormData("recallconfigphone0002");
		RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
		recallConfigPhone.setPhoneNo(phoneNo);
		recallConfigPhone = recallConfigPhoneFeign.getById(recallConfigPhone);
		getObjValue(formrecallconfigphone0002, recallConfigPhone, formData);
		if (recallConfigPhone != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	@RequestMapping(value = "/getById")
	public String getById(Model model, String phoneNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfigphone0003 = formService.getFormData("dlrecallconfigphone0003");
		getFormValue(formdlrecallconfigphone0003);
		RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
		recallConfigPhone.setPhoneNo(phoneNo);
		recallConfigPhone = recallConfigPhoneFeign.getById(recallConfigPhone);
		getObjValue(formdlrecallconfigphone0003, recallConfigPhone);
		model.addAttribute("formdlrecallconfigphone0003", formdlrecallconfigphone0003);
		return "RecallConfigPhone_Detail";
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String phoneNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
		recallConfigPhone.setPhoneNo(phoneNo);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// recallConfigPhone = (RecallConfigPhone)JSONObject.toBean(jb,
			// RecallConfigPhone.class);
			recallConfigPhoneFeign.delete(recallConfigPhone);
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
	public String input(Model model, String collectionPlanNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
		recallConfigPhone.setCollectionPlanNo(collectionPlanNo);

		MfCollectionFormConfig mfCollectionFormConfig = mfCollectionFormConfigFeign.getByCollectionTypeAndAction("1",
				"RecallConfigPhoneAction");
		String formId = null;
		if (mfCollectionFormConfig == null) {

		} else {
			formId = mfCollectionFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("催收类型为" + mfCollectionFormConfig.getFormType() +
			// "的RecallConfigPhoneAction表单信息没有查询到");
		} else {
			FormData formdlrecallconfigphone0003 = formService.getFormData(formId);
			if (formdlrecallconfigphone0003.getFormId() == null) {
				// logger.error("催收类型为" + mfCollectionFormConfig.getFormType() +
				// "的RecallConfigPhoneAction表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlrecallconfigphone0003);
				getObjValue(formdlrecallconfigphone0003, recallConfigPhone);
				model.addAttribute("formdlrecallconfigphone0003", formdlrecallconfigphone0003);
			}
		}

		return "RecallConfigPhone_Insert";
	}

	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);

		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formdlrecallconfigphone0004 = formService.getFormData("dlrecallconfigphone0004");
		getFormValue(formdlrecallconfigphone0004, getMapByJson(ajaxData));

		RecallConfigPhone recallConfigPhoneJsp = new RecallConfigPhone();
		setObjValue(formdlrecallconfigphone0004, recallConfigPhoneJsp);

		RecallConfigPhone recallConfigPhone = recallConfigPhoneFeign.getById(recallConfigPhoneJsp);
		if (recallConfigPhone != null) {
			try {
				recallConfigPhone = (RecallConfigPhone) EntityUtil.reflectionSetVal(recallConfigPhone,
						recallConfigPhoneJsp, getMapByJson(ajaxData));
				recallConfigPhoneFeign.update(recallConfigPhone);

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
