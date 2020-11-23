package app.component.cus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.auth.entity.MfCusCreditConfig;
import app.component.auth.feign.MfCusCreditConfigFeign;
import app.component.prdct.entity.MfKindNodeTemplate;
import app.component.prdct.feign.MfKindNodeTemplateFeign;
import config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.Table;
import com.core.service.screen.FormService;
import com.core.service.screen.TableService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.auth.entity.MfCusCreditModel;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.doc.entity.DocBizSceConfig;
import app.component.doc.entity.DocModel;
import app.component.doc.feign.DocBizSceConfigFeign;
import app.component.doc.feign.DocModelFeign;
import app.component.eval.entity.EvalScenceConfig;
import app.component.eval.entity.EvalScoreGradeConfig;
import app.component.evalinterface.EvalInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.param.entity.Scence;
import app.component.param.feign.ScenceFeign;
import app.component.pfs.entity.CusFinForm;
import app.component.pfsinterface.PfsInterfaceFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Title: MfCusFormConfigAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon May 23 18:32:40 CST 2016
 **/
@Controller
@RequestMapping("/mfCusFormConfig")
public class MfCusFormConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusFormConfigBo
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;
	@Autowired
	private DocModelFeign docModelFeign;
	@Autowired
	private ScenceFeign scenceFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private EvalInterfaceFeign evalInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private PfsInterfaceFeign pfsInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	// 全局变量
	@Autowired
	private DocBizSceConfigFeign docBizSceConfigFeign;
	// 全局变量
	@Autowired
	private MfKindNodeTemplateFeign mfKindNodeTemplateFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfCusCreditConfigFeign mfCusCreditConfigFeign;
	// 异步参数
	// 表单变量

	/**
	 * 处理客户类型要件配置请求
	 */
	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDocAjax")
	@ResponseBody
	public Map<String, Object> updateDocAjax(String ajaxData, String ajaxDataList) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocModel docModel = new DocModel();
		try {
			FormData formdocmodel0001 = formService.getFormData("docmodel0001");
			getFormValue(formdocmodel0001, getMapByJson(ajaxData));
			Map<String, Object> mapByJson = getMapByJson(ajaxData);
			String optCode = (String) mapByJson.get("optCode");// 客户类型编号
			JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
			List<DocBizSceConfig> docBizSceConfigList = (List<DocBizSceConfig>) JSONArray.toList(jsonArray,
					new DocBizSceConfig(), new JsonConfig());
			mfCusFormConfigFeign.updateDoc(optCode, docBizSceConfigList);
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
	 * 打开客户类型配置要件的页面
	 */
	@RequestMapping(value = "/getEditDocPage")
	public String getEditDocPage(Model model, String optCode) throws Exception {
		try {
			FormService formService = new FormService();
			ActionContext.initialize(request, response);
			// optcode 客户类型编号
			// optName 客户名称
			CodeUtils cu = new CodeUtils();
			Map<String, String> cusTypeMap = cu.getMapByKeyName("CUS_TYPE");
			Object optName = cusTypeMap.get(optCode); // 用客户类型编号,取客户类型名称

			FormData formdocmodelcus0001 = formService.getFormData("docmodelcus0001");
			getFormValue(formdocmodelcus0001);
			this.changeFormProperty(formdocmodelcus0001,"cusTypeDes","initValue",optName);

			// 查询要件场景
			Scence scence = new Scence();
			scence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
			scence.setUseFlag(BizPubParm.YES_NO_Y);
			List<Scence> scenceList = scenceFeign.findByType(scence);

			// 按照客户类型查询已经配置的要件
			DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
			docBizSceConfig.setDime1(optCode);
			List<DocBizSceConfig> docBizSceConfigList = docBizSceConfigFeign.getByDime(docBizSceConfig);

			String ajaxData = JSONArray.fromObject(docBizSceConfigList).toString();
			String scenceListAjaxData = JSONArray.fromObject(scenceList).toString();
			model.addAttribute("formdocmodelcus0001", formdocmodelcus0001);
			model.addAttribute("ajaxData", ajaxData);
			model.addAttribute("scenceListAjaxData", scenceListAjaxData);
			model.addAttribute("scenceList", scenceList);
			model.addAttribute("optCode", optCode);
			model.addAttribute("query", "");
			return "/component/cus/editDocPageUpdate";
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
	}
	/**
	 * 打开操作员配置要件的页面
	 */
	@RequestMapping(value = "/getUserEditDocPage")
	public String getUserEditDocPage(Model model, String optCode) throws Exception {
		try {
			FormService formService = new FormService();
			ActionContext.initialize(request, response);

			CodeUtils cu = new CodeUtils();
			Map<String, String> cusTypeMap = cu.getMapByKeyName("OP_NO_TYPE");
			Object optName = cusTypeMap.get(optCode); // 用操作员类型取操作员类型名称

			FormData formdocmodelcus0001 = formService.getFormData("docmodeluser0001");
			getFormValue(formdocmodelcus0001);
			this.changeFormProperty(formdocmodelcus0001,"cusTypeDes","initValue",optName);
			// 查询要件场景
			Scence scence = new Scence();
			scence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
			scence.setUseFlag(BizPubParm.YES_NO_Y);
			List<Scence> scenceList = scenceFeign.findByType(scence);

			// 按照客户类型查询已经配置的要件
			DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
			docBizSceConfig.setDime1(optCode);
			List<DocBizSceConfig> docBizSceConfigList = docBizSceConfigFeign.getByDime(docBizSceConfig);

			String ajaxData = JSONArray.fromObject(docBizSceConfigList).toString();
			String scenceListAjaxData = JSONArray.fromObject(scenceList).toString();
			model.addAttribute("formdocmodelcus0001", formdocmodelcus0001);
			model.addAttribute("ajaxData", ajaxData);
			model.addAttribute("scenceListAjaxData", scenceListAjaxData);
			model.addAttribute("scenceList", scenceList);
			model.addAttribute("optCode", optCode);
			model.addAttribute("query", "");
			return "/component/cus/editDocPageUpdate";
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
	}

	/**
	 * 打开客户类型配置模板的页面
	 */
	@RequestMapping(value = "/getEditTemplatePage")
	public String getEditTemplatePage(Model model, String optCode) throws Exception {
		try {
			ActionContext.initialize(request, response);
            CodeUtils cu = new CodeUtils();
            Map<String, String> cusTypeMap = cu.getMapByKeyName("CUS_TYPE");
            Object optName = cusTypeMap.get(optCode); // 用客户类型编号,取客户类型名称
            model.addAttribute("cusType", optCode);
			model.addAttribute("cusTypeName", optName);
			return "/component/cus/editTemplatePageUpdate";
		} catch (Exception e) {
		    e.printStackTrace();
			throw e;
		}
	}
	@RequestMapping(value = "/findTemplatePage")
    @ResponseBody
	public Map<String,Object> findTemplatePage(Model model, String cusType) throws Exception {
        Map<String,Object> dataMap = new HashMap<String,Object>();
		try {
			ActionContext.initialize(request, response);
			MfKindNodeTemplate mfKindNodeTemplate = new MfKindNodeTemplate();
			mfKindNodeTemplate.setNodeNo(cusType);
			List<MfKindNodeTemplate> mfKindNodeTemplates = mfKindNodeTemplateFeign.getCusTemplateConfig(mfKindNodeTemplate);
            dataMap.put("mfKindNodeTemplates",mfKindNodeTemplates);
            dataMap.put("flag","success") ;
            dataMap.put("msg","获取模板列表成功") ;
		} catch (Exception e) {
            dataMap.put("flag","error") ;
            dataMap.put("msg","获取模板列表失败") ;
		    e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

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
		return "/component/cus/MfCusFormConfig_List";
	}

	/**
	 * @author czk
	 * @Description: 选多个产品种类的页面 date 2016-12-21
	 */
	@RequestMapping(value = "/getCusTypeForMutiSel")
	public String getCusTypeForMutiSel(Model model, String ajaxData) throws Exception {

		ActionContext.initialize(request, response);
		List<ParmDic> parmDiclist = nmdInterfaceFeign.findByParmDicAllByKename("CUS_TYPE");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusTypelist", parmDiclist);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/cus/MfCusType_MutiSel";
	}

	/**
	 * 
	 * 方法描述：根据客户类型，获得该客户类型的表单配置的页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-5-24 上午10:39:31
	 */
	@RequestMapping(value = "/getAllFormConList")
	public String getAllFormConList(Model model, String optCode, String fromPage) throws Exception {
		List<ParmDic> parmDiclist = new ArrayList<ParmDic>();
		List<MfCusFormConfig> mfCusFormConfigList = new ArrayList<MfCusFormConfig>();
		String optName = "";
		try {
			MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
			String formType = optCode;
			CodeUtils cu = new CodeUtils();
			Map<String, String> cusTypeMap = cu.getMapByKeyName("CUS_TYPE");
			optName = cusTypeMap.get(optCode);

			mfCusFormConfigFeign.updateFormByCusType(formType);
			mfCusFormConfig = new MfCusFormConfig();
			mfCusFormConfig.setFormType(formType);
			mfCusFormConfigList = mfCusFormConfigFeign.getAll(mfCusFormConfig);
			model.addAttribute("formType", formType);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("mfCusFormConfigList", mfCusFormConfigList);
		model.addAttribute("parmDiclist", parmDiclist);
		model.addAttribute("optName", optName);
		model.addAttribute("fromPage", fromPage);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFormConfig_configList";
	}

	/**
	 * @author czk
	 * @Description: 根据客户类型，获得该客户类型下的表单信息(未配置的和已配置的)，并转换成json以满足zTree的格式要求。 date
	 *               2016-11-21
	 */
	@RequestMapping(value = "/getAllFormJsonAjax")
	@ResponseBody
	public Map<String, Object> getAllFormJsonAjax(String formType) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
			mfCusFormConfig.setFormType(formType);
			List<MfCusFormConfig> mfCusFormConfigList = mfCusFormConfigFeign.getAll(mfCusFormConfig);
			// dicArray.re
			List<MfCusFormConfig> list1 = new ArrayList<MfCusFormConfig>();
			List<MfCusFormConfig> list2 = new ArrayList<MfCusFormConfig>();
			for (int i = 0; i < mfCusFormConfigList.size(); i++) {
				mfCusFormConfig = mfCusFormConfigList.get(i);
				if ("0".equals(mfCusFormConfig.getUseFlag())) {
					list1.add(mfCusFormConfig);
				} else if ("1".equals(mfCusFormConfig.getUseFlag())) {
					list2.add(mfCusFormConfig);
				}else {
				}
			}
			JSONArray dicArray1 = JSONArray.fromObject(list1);
			JSONArray dicArray2 = JSONArray.fromObject(list2);
			for (int i = 0; i < dicArray1.size(); i++) {
				dicArray1.getJSONObject(i).put("id", dicArray1.getJSONObject(i).getString("id"));
				dicArray1.getJSONObject(i).put("name", dicArray1.getJSONObject(i).getString("nameZn"));
				dicArray1.getJSONObject(i).put("pId", "0");
				dicArray1.getJSONObject(i).put("open", true);
			}
			for (int i = 0; i < dicArray2.size(); i++) {
				dicArray2.getJSONObject(i).put("id", dicArray2.getJSONObject(i).getString("id"));
				dicArray2.getJSONObject(i).put("name", dicArray2.getJSONObject(i).getString("nameZn"));
				dicArray2.getJSONObject(i).put("pId", "0");
				dicArray2.getJSONObject(i).put("open", true);
			}
			dataMap.put("flag", "success");
			dataMap.put("sel", dicArray2);
			dataMap.put("unsel", dicArray1);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());

		}

		return dataMap;
	}

	/**
	 * @author czk
	 * @Description: 异步保存表单配置信息 date 2016-9-9
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveFormConfigAjax")
	@ResponseBody
	public Map<String, Object> saveFormConfigAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray jsonArray = JSONArray.fromObject(ajaxData);
			List<MfCusFormConfig> mfCusFormConfigList = (List<MfCusFormConfig>) JSONArray.toList(jsonArray,
					new MfCusFormConfig(), new JsonConfig());

			mfCusFormConfigFeign.updateList(mfCusFormConfigList);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}

	/**
	 * @author czk
	 * @Description: 判断某客户类型的某个表单是否存在，如果不存在，则生成表单xml，并将子表单xml返回。 date 2016-11-21
	 */
	@RequestMapping(value = "/checkAndCreateFormAjax")
	@ResponseBody
	public Map<String, Object> checkAndCreateFormAjax(String id, String optCode, Double weight) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			String formIdThis = "";
			MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
			mfCusFormConfig.setId(id);
			mfCusFormConfig = mfCusFormConfigFeign.getById(mfCusFormConfig);
			FormService formService = new FormService();
			TableService tableService = new TableService();
			if ("1".equals(optCode)) {// 表示是新增表单
				if (mfCusFormConfig.getAddModel().equals(mfCusFormConfig.getAddModelDef())) {
					mfCusFormConfig
							.setAddModelDef(mfCusFormConfig.getAddModel() + "_add" + mfCusFormConfig.getFormType());
					FormData form = formService.getFormData(mfCusFormConfig.getAddModel());
					form.setFormId(mfCusFormConfig.getAddModelDef());
					form.setTitle("title");
					formService.saveForm(form, "insert");
					MfCusFormConfig mfCusFormCon = new MfCusFormConfig();
					mfCusFormCon.setId(id);
					mfCusFormCon.setAddModelDef(mfCusFormConfig.getAddModelDef());
					mfCusFormCon.setWeight(weight);
					mfCusFormConfigFeign.update(mfCusFormCon);
				}
				formIdThis = mfCusFormConfig.getAddModelDef();
			} else if ("2".equals(optCode)) {// 表示是展示表单
				if (mfCusFormConfig.getShowModel().equals(mfCusFormConfig.getShowModelDef())) {
					mfCusFormConfig
							.setShowModelDef(mfCusFormConfig.getShowModel() + "_show" + mfCusFormConfig.getFormType());
					FormData form = formService.getFormData(mfCusFormConfig.getShowModel());
					form.setFormId(mfCusFormConfig.getShowModelDef());
					form.setTitle("title");
					formService.saveForm(form, "insert");
					MfCusFormConfig mfCusFormCon = new MfCusFormConfig();
					mfCusFormCon.setId(id);
					mfCusFormCon.setShowModelDef(mfCusFormConfig.getShowModelDef());
					mfCusFormCon.setWeight(weight);
					mfCusFormConfigFeign.update(mfCusFormCon);
				}
				formIdThis = mfCusFormConfig.getShowModelDef();
			}else if("3".equals(optCode)){//表示是列表表单
				if(mfCusFormConfig.getListModel().equals(mfCusFormConfig.getListModelDef())){
					mfCusFormConfig.setListModelDef(mfCusFormConfig.getListModel()+"_list"+mfCusFormConfig.getFormType());
					Table table = tableService.getTables(mfCusFormConfig.getListModel());
					table.setTableId(mfCusFormConfig.getListModelDef());
					table.setName("title");
					tableService.saveTable(table,"insert");
					MfCusFormConfig mfCusFormCon = new MfCusFormConfig();
					mfCusFormCon.setId(id);
					mfCusFormCon.setListModelDef(mfCusFormConfig.getListModelDef());
					mfCusFormCon.setWeight(weight);
					mfCusFormConfigFeign.update(mfCusFormCon);
				}
				formIdThis = mfCusFormConfig.getListModelDef();
			}else {
			}
			dataMap.put("flag", "success");
			dataMap.put("formId", formIdThis);

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/saveFormConfigAjax1")
	@ResponseBody
	public Map<String, Object> saveFormConfigAjax1(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			JSONArray jsonArray = JSONArray.fromObject(ajaxData);
			List<MfCusFormConfig> mfCusFormConfigList = (List<MfCusFormConfig>) JSONArray.toList(jsonArray,
					new MfCusFormConfig(), new JsonConfig());
			for (int i = 0; i < mfCusFormConfigList.size(); i++) {
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigList.get(i);
				String cusType = mfCusFormConfig.getFormType();
				if ("1".equals(mfCusFormConfig.getUseFlag())) {
					if (mfCusFormConfig.getAddModel().equals(mfCusFormConfig.getAddModelDef())) {
						mfCusFormConfig.setAddModelDef(mfCusFormConfig.getAddModel() + "_add" + cusType);
						FormData form = formService.getFormData(mfCusFormConfig.getAddModel());
						form.setFormId(mfCusFormConfig.getAddModelDef());
						form.setTitle("title");
						formService.saveForm(form, "insert");

					}
					if ("1".equals(mfCusFormConfig.getShowType())) {// 展示形式还是表单
																	// 1表单2列表
						if (mfCusFormConfig.getShowModel().equals(mfCusFormConfig.getShowModelDef())) {
							mfCusFormConfig.setShowModelDef(mfCusFormConfig.getShowModel() + "_show" + cusType);

							FormData form = formService.getFormData(mfCusFormConfig.getShowModel());
							form.setFormId(mfCusFormConfig.getShowModelDef());
							form.setTitle("title");
							formService.saveForm(form, "insert");
						}
					}
				}
			}
			mfCusFormConfigFeign.updateList(mfCusFormConfigList);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}

	/**
	 * @author czk
	 * @Description: 将子表单重置成模板。 date 2016-9-26
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetFormAjax")
	@ResponseBody
	public Map<String, Object> resetFormAjax(String id) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			// 每个表单都有新增表单，当showType为1时，还会有展示表单；showtype为1表示信息以表单形式展示，为2表示以列表形式展示
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				boolean updateFlag = false;// 是否需要执行数据更新。

				MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
				mfCusFormConfig.setId(ids[i]);
				mfCusFormConfig = mfCusFormConfigFeign.getById(mfCusFormConfig);

				MfCusFormConfig mfCusFormCon = new MfCusFormConfig();
				mfCusFormCon.setId(ids[i]);
				// 先删除子表单，再将子表单编号的字段和母表单设置一致
				if (mfCusFormConfig.getAddModel().equals(mfCusFormConfig.getAddModelDef())) {// 子表单和模板一致

				} else {// 子表单和模板不一致时，删除子表单
					FormData form = formService.getFormData(mfCusFormConfig.getAddModelDef());
					form.setFormId(mfCusFormConfig.getAddModelDef());
					formService.saveForm(form, "delete");
					mfCusFormCon.setAddModelDef(mfCusFormConfig.getAddModel());
					updateFlag = true;
				}

				if (mfCusFormConfig.getShowModel().equals(mfCusFormConfig.getShowModelDef())) {

				} else {
					FormData form = formService.getFormData(mfCusFormConfig.getShowModelDef());
					form.setFormId(mfCusFormConfig.getShowModelDef());
					formService.saveForm(form, "delete");
					mfCusFormCon.setShowModelDef(mfCusFormConfig.getShowModel());
					updateFlag = true;
				}

				// 判断列表表单是否为空
				if (mfCusFormConfig.getListModel() != null && mfCusFormConfig.getListModelDef() != null) {
					if (mfCusFormConfig.getListModel().equals(mfCusFormConfig.getListModelDef())) {

					} else {
						FormData form = formService.getFormData(mfCusFormConfig.getListModelDef());
						form.setFormId(mfCusFormConfig.getListModelDef());
						formService.saveForm(form, "delete");
						mfCusFormCon.setListModelDef(mfCusFormConfig.getListModel());
						updateFlag = true;
					}
				}
				if (updateFlag) {
					mfCusFormConfigFeign.update(mfCusFormCon);
				}
			}

			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/getAllFormConList1")
	public String getAllFormConList1(Model model, String ajaxData) throws Exception {
		ArrayList<MfCusFormConfig> mfCusFormConfigList = new ArrayList<MfCusFormConfig>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
			Ipage ipage = this.getIpage();
			mfCusFormConfigList = (ArrayList<MfCusFormConfig>) mfCusFormConfigFeign.getAll(mfCusFormConfig);
		} catch (Exception e) {
			e.printStackTrace();

			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("mfCusFormConfigList", mfCusFormConfigList);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFormConfig_ListTmp1";
	}

	/**
	 * 
	 * 方法描述： 获得客户类型
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-5-26 下午5:55:46
	 */
	@RequestMapping(value = "/getAllList")
	public String getAllList(Model model, String ajaxData) throws Exception {
		ArrayList<ParmDic> parmDiclist = new ArrayList<ParmDic>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmDic parmDic = new ParmDic();
		try {
			parmDic.setKeyName("CUS_TYPE");
			parmDiclist = (ArrayList<ParmDic>) nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("parmDiclist", parmDiclist);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFormConfig_List";
	}

	/**
	 * 
	 * 方法描述： 跳转客户设置页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-22 上午9:32:40
	 */
	@RequestMapping(value = "/getMfCusConfig")
	public String getMfCusConfig(Model model, String ajaxData, String fromPage) throws Exception {
		ParmDic parmDic = new ParmDic();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 客户表单配置
			// parmDic.setKeyName("CUS_TYPE");
			// cusTypeParmDiclist =
			// (List<ParmDic>)nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
			MfCusType mfCusType = new MfCusType();
			//mfCusType.setUseFlag("1");//客户设置中显示所有的客户类型，无论是否启用
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusType", mfCusType));
			List<Map<String, Object>> cusTypeMList = (List<Map<String, Object>>) mfCusTypeFeign.findByPage(ipage)
					.getResult();
			List<MfCusType> cusTypeList = new ArrayList<>();
			for (Map<String, Object> mctM : cusTypeMList) {
				MfCusType mct = (MfCusType) JsonStrHandling.handlingStrToBean(mctM, MfCusType.class);
				String baseType = mct.getBaseType();// 客户身份
				CodeUtils codeUtils = new CodeUtils();
				mct.setBaseType(codeUtils.getMapObjByKeyName("CUS_BASE_TYPE").get(baseType).getOptName());
				cusTypeList.add(mct);
			}
			// 评级模型配置信息
			EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
			List<EvalScenceConfig> evalScenceConfigList = evalInterfaceFeign.getEvalScenceConfigList(evalScenceConfig);
			List<EvalScenceConfig> evalScenceConfigKH=new ArrayList<>();
			List<EvalScenceConfig> evalScenceConfigFX=new ArrayList<>();
			List<EvalScenceConfig> evalScenceConfigEC=new ArrayList<>();
			List<EvalScenceConfig> evalScenceConfigXY=new ArrayList<>();
			List<EvalScenceConfig> evalScenceConfigZX=new ArrayList<>();
			for(int i=0;i<evalScenceConfigList.size();i++){
				if(BizPubParm.GRADE_TYPE_2.equals(evalScenceConfigList.get(i).getGradeType())){
					evalScenceConfigFX.add(evalScenceConfigList.get(i));
				}else if(BizPubParm.GRADE_TYPE_3.equals(evalScenceConfigList.get(i).getGradeType())) {
                    evalScenceConfigEC.add(evalScenceConfigList.get(i));
                }else if(BizPubParm.GRADE_TYPE_4.equals(evalScenceConfigList.get(i).getGradeType())) {
					if(BizPubParm.EVAL_CLASS_1.equals(evalScenceConfigList.get(i).getEvalClass())){
						evalScenceConfigXY.add(evalScenceConfigList.get(i));
					}else{
						evalScenceConfigZX.add(evalScenceConfigList.get(i));
					}
				}else{
                    evalScenceConfigKH.add(evalScenceConfigList.get(i));
                }
			}
			// 分数等级配置
			List<EvalScoreGradeConfig> EvalScoreGradeConfigList = evalInterfaceFeign.getGradeConfigsList();
			// 授信模型配置
			MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
			List<MfCusCreditModel> mfCusCreditModelList = creditApplyInterfaceFeign
					.getMfCusCreditModelList(mfCusCreditModel);
			// 客户分类
			parmDic.setKeyName("CLASSIFY_TYPE");
			List<ParmDic> cusClassifyParmDiclist = (List<ParmDic>) nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
			// 财务报表配置
			parmDic.setKeyName("REPORT_TYPE");
			List cusFinParmDiclist = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
			// 财务指标配置
			CusFinForm cusFinForm = new CusFinForm();
			cusFinForm.setAccRule("1");
			List<CusFinForm> cusFinFormList = pfsInterfaceFeign.getCusFinFormList(cusFinForm);
			// 客户相关流程设置
			MfKindFlow mfKindFlow = new MfKindFlow();
			mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
			mfKindFlow.setCategory(BizPubParm.FLOW_CATEGORY_5);
			List<MfKindFlow> cusflowList = prdctInterfaceFeign.getKindFlowList(mfKindFlow);

            // 授信
            List<MfCusCreditConfig> mfCusCreditConfigs = mfCusCreditConfigFeign.getAllList();


			model.addAttribute("cusflowList", cusflowList);
			model.addAttribute("evalScenceConfigKH", evalScenceConfigKH);
			model.addAttribute("evalScenceConfigFX", evalScenceConfigFX);
			model.addAttribute("evalScenceConfigXY", evalScenceConfigXY);
			model.addAttribute("evalScenceConfigZX", evalScenceConfigZX);
			model.addAttribute("evalScenceConfigEC", evalScenceConfigEC);
			model.addAttribute("EvalScoreGradeConfigList", EvalScoreGradeConfigList);
			model.addAttribute("mfCusCreditModelList", mfCusCreditModelList);
			model.addAttribute("cusClassifyParmDiclist", cusClassifyParmDiclist);
			model.addAttribute("cusFinParmDiclist", cusFinParmDiclist);
			model.addAttribute("cusFinFormList", cusFinFormList);
			model.addAttribute("cusTypeList", cusTypeList);
			model.addAttribute("mfCusCreditConfigs", mfCusCreditConfigs);
			model.addAttribute("fromPage", fromPage);

		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		model.addAttribute("query", "");
		return "/component/cus/MfCusConfig";
	}

	/**
	 *	方法描述： 跳转移动设置页面
	 * @param model
	 * @param ajaxData
	 * @param fromPage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMfCusConfigToApp")
	public String getMfCusConfigToApp(Model model, String ajaxData, String fromPage) throws Exception {
		ParmDic parmDic = new ParmDic();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			MfCusType mfCusType = new MfCusType();
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusType", mfCusType));
			List<Map<String, Object>> cusTypeMList = (List<Map<String, Object>>) mfCusTypeFeign.findByPage(ipage)
					.getResult();
			List<MfCusType> cusTypeList = new ArrayList<>();
			for (Map<String, Object> mctM : cusTypeMList) {
				MfCusType mct = (MfCusType) JsonStrHandling.handlingStrToBean(mctM, MfCusType.class);
				String baseType = mct.getBaseType();// 客户身份
				CodeUtils codeUtils = new CodeUtils();
				mct.setBaseType(codeUtils.getMapObjByKeyName("CUS_BASE_TYPE").get(baseType).getOptName());
				cusTypeList.add(mct);
			}

			//获取移动端后台地址
			String appPath = ymlConfig.getWebservice().get("appPath");
			model.addAttribute("appPath", appPath);

			model.addAttribute("cusTypeList", cusTypeList);
			model.addAttribute("fromPage", fromPage);

		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		model.addAttribute("query", "");
		return "/component/cus/MfCusConfig_App";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-21 上午10:46:14
	 */
	@RequestMapping(value = "/cusTypeInsertAjax")
	@ResponseBody
	public Map<String, Object> cusTypeInsertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			FormData formcusTypeConfig0001 = formService.getFormData("cusTypeConfig0001");
			dataMap = getMapByJson(ajaxData);
			getFormValue(formcusTypeConfig0001, getMapByJson(ajaxData));
			if (this.validateFormData(formcusTypeConfig0001)) {
				resultMap = mfCusFormConfigFeign.insertCusTypeConifg(dataMap);
				dataMap.put("remark", resultMap.get("remark"));
				dataMap.put("optName", resultMap.get("optName"));
				dataMap.put("optCode", resultMap.get("optCode"));
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获得该客户类型的表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-5-26 下午5:58:02
	 *//*
		 * @RequestMapping(value = "/getCusForms") public String
		 * getCusForms(Model model, String ajaxData) throws Exception{
		 * 
		 * model.addAttribute("formcusfcon00001", formcusfcon00001);
		 * model.addAttribute("query", ""); return "getCusForms"; }
		 */
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String formType, String regView) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		try {
		    MfCusType mfCusType = new MfCusType();
		    mfCusType.setTypeNo(formType);
		    mfCusType = mfCusTypeFeign.getById(mfCusType);
		    String cusBaseType = mfCusType.getBaseType();
			mfCusFormConfig.setFormType(formType);
			mfCusFormConfig.setRegView(regView);
			mfCusFormConfig.setFormBaseType(cusBaseType);
			mfCusFormConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusFormConfig.setCriteriaList(mfCusFormConfig, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusFormConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusFormConfig", mfCusFormConfig));
			// 自定义查询Bo方法
			ipage = mfCusFormConfigFeign.findByPage(ipage);
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

	@RequestMapping(value = "/updateStsAjax")
	@ResponseBody
	public Map<String, Object> updateStsAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			String id = (String) jobj.get("id");
			if (StringUtil.isEmpty(id)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				return dataMap;
			}

			mfCusFormConfig.setId(id);

			String useFlag = (String) jobj.get("useFlag");
			if (useFlag != null) {
				mfCusFormConfig.setUseFlag(useFlag);
				if ("0".equals(useFlag)) {
					MfCusFormConfig temp = new MfCusFormConfig();
					temp.setId((String) jobj.get("id"));
					temp = mfCusFormConfigFeign.getById(temp);
					if ("1".equals(temp.getIsBase())) {
						dataMap.put("flag", "error");
						dataMap.put("msg", MessageEnum.NOT_FORM_BASIS.getMessage());
						return dataMap;
					}
				}
			}
			String isBase = (String) jobj.get("isBase");
			if (isBase != null) {
				mfCusFormConfig.setIsBase(isBase);
				if ("1".equals(isBase)) {
					mfCusFormConfig.setUseFlag("1");
				}
			}
			mfCusFormConfig.setIsMust((String) jobj.get("isMust"));
			mfCusFormConfigFeign.updateSts(mfCusFormConfig);

			mfCusFormConfig = mfCusFormConfigFeign.getById(mfCusFormConfig);
			ArrayList<MfCusFormConfig> mfCusFormConfigList = new ArrayList<MfCusFormConfig>();
			mfCusFormConfigList.add(mfCusFormConfig);
			getTableDataList(mfCusFormConfigList, tableId);

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

	private void getTableDataList(List<MfCusFormConfig> list, String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
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
		FormData formcusfcon00002 = formService.getFormData("cusfcon00002");
		getFormValue(formcusfcon00002, getMapByJson(ajaxData));
		MfCusFormConfig mfCusFormConfigJsp = new MfCusFormConfig();
		setObjValue(formcusfcon00002, mfCusFormConfigJsp);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getById(mfCusFormConfigJsp);
		if (mfCusFormConfig != null) {
			try {
				mfCusFormConfig = (MfCusFormConfig) EntityUtil.reflectionSetVal(mfCusFormConfig, mfCusFormConfigJsp,
						getMapByJson(ajaxData));
				mfCusFormConfigFeign.update(mfCusFormConfig);
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
	 * 方法描述：更新所占权重
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateWeightAjax")
	@ResponseBody
	public Map<String, Object> updateWeightAjax(Double weight, String id) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		mfCusFormConfig.setWeight(weight);
		mfCusFormConfig = mfCusFormConfigFeign.getById(mfCusFormConfig);
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("id", weight);
		if (mfCusFormConfig != null) {
			dataMap.put("flag", "error");
			return dataMap;
		}
		mfCusFormConfig = new MfCusFormConfig();
		mfCusFormConfig.setId(id);
		mfCusFormConfig = mfCusFormConfigFeign.getById(mfCusFormConfig);
		if (mfCusFormConfig == null) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			return dataMap;
		}
		mfCusFormConfig.setWeight(weight);
		mfCusFormConfigFeign.update(mfCusFormConfig);
		dataMap.put("flag", "success");

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
	public Map<String, Object> deleteAjax(String id, String formType, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		mfCusFormConfig.setId(id);
		try {
			mfCusFormConfigFeign.delete(mfCusFormConfig);
			getTableData(formType, tableId);
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
	 * 
	 * 方法描述： 修改表单启用备用状态
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-5-27 下午2:56:16
	 */
	@RequestMapping(value = "/updateUseFlagAjax")
	@ResponseBody
	public Map<String, Object> updateUseFlagAjax(String id, String formType, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		mfCusFormConfig.setId(id);
		try {
			mfCusFormConfig = mfCusFormConfigFeign.getById(mfCusFormConfig);
			if (("1").equals(mfCusFormConfig.getUseFlag())) {
				mfCusFormConfig.setUseFlag("0");
			} else {
				mfCusFormConfig.setUseFlag("1");
			}
			mfCusFormConfigFeign.update(mfCusFormConfig);
			getTableData(formType, tableId);
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
		FormData formcusfcon00002 = formService.getFormData("cusfcon00002");
		model.addAttribute("formcusfcon00002", formcusfcon00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFormConfig_Insert";
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
		FormData formcusfcon00002 = formService.getFormData("cusfcon00002");
		getFormValue(formcusfcon00002);
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		setObjValue(formcusfcon00002, mfCusFormConfig);
		mfCusFormConfigFeign.insert(mfCusFormConfig);
		getObjValue(formcusfcon00002, mfCusFormConfig);
		this.addActionMessage(model, "保存成功");
		List<MfCusFormConfig> mfCusFormConfigList = (List<MfCusFormConfig>) mfCusFormConfigFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusfcon00002", formcusfcon00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFormConfig_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusfcon00001 = formService.getFormData("cusfcon00001");
		getFormValue(formcusfcon00001);
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		mfCusFormConfig.setId(id);
		mfCusFormConfig = mfCusFormConfigFeign.getById(mfCusFormConfig);
		getObjValue(formcusfcon00001, mfCusFormConfig);
		model.addAttribute("formcusfcon00001", formcusfcon00001);
		model.addAttribute("query", "");
		return "MfCusFormConfig_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		mfCusFormConfig.setId(id);
		mfCusFormConfigFeign.delete(mfCusFormConfig);
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
		FormData formcusfcon00002 = formService.getFormData("cusfcon00002");
		getFormValue(formcusfcon00002);
		boolean validateFlag = this.validateFormData(formcusfcon00002);
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
		FormData formcusfcon00002 = formService.getFormData("cusfcon00002");
		getFormValue(formcusfcon00002);
		boolean validateFlag = this.validateFormData(formcusfcon00002);
	}

	private void getTableData(String formType, String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		mfCusFormConfig.setFormType(formType);
		Ipage ipage = this.getIpage();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfCusFormConfigFeign.getAll(mfCusFormConfig), null,
				true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
	}
	/**
	 *@描述 客户授信流程、环节要件和模板配置
	 *@参数
	 *@返回值
	 *@创建人  shenhaobing
	 *@创建时间  2019/4/10
	 *@修改人和其它信息
	 */
    @RequestMapping(value = "/getCreditConfig")
    public String getCreditConfig(Model model,String creditId,String creditModel,String configType) throws Exception{
        ActionContext.initialize(request, response);
        JSONObject json = new JSONObject();
        List<ParmDic> parmDicList  = new CodeUtils().getCacheByKeyName("CREDIT_CONFIG_TYPE");
        json.put("configTypeList", JSONArray.fromObject(parmDicList));
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("configTypeList", JSONArray.fromObject(parmDicList));
        model.addAttribute("creditId", creditId);
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("configType", configType);
        return "/component/cus/MfSysCreditConfig";
    }
	/**
	 *@描述刷新模块
	 *@参数
	 *@返回值
	 *@创建人  zff
	 *@创建时间  2019/7/12
	 *@修改人和其它信息
	 */
	@RequestMapping(value = "/refreshModle")
	@ResponseBody
	public  Map<String, Object> refreshModle(String formType) throws Exception{
		ActionContext.initialize(request, response);
		String result=mfCusFormConfigFeign.getrefreshModle(formType);
		Map <String, Object> map=new HashMap<>();
		if("success".equals(result)){
			map.put("flag","success");
		}else{
			map.put("flag","faile");
		}
		return map;
	}
}
