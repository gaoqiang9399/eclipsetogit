package  app.component.examine.controller;
import java.util.ArrayList;
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

import app.base.cache.feign.BusiCacheFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.doc.entity.DocBizSceConfig;
import app.component.doc.feign.DocBizSceConfigFeign;
import app.component.examine.entity.MfExamRiskLevelConfig;
import app.component.examine.entity.MfExamineTemplateConfig;
import app.component.examine.feign.MfExamRiskLevelConfigFeign;
import app.component.examine.feign.MfExamineTemplateConfigFeign;
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.param.entity.Scence;
import app.component.paraminterface.ParamInterfaceFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Title: MfExamineTemplateConfigAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Feb 09 15:35:54 CST 2017
 **/
@Controller
@RequestMapping(value="/mfExamineTemplateConfig")
public class MfExamineTemplateConfigController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入MfExamineTemplateConfigBo
	@Autowired
	private MfExamineTemplateConfigFeign mfExamineTemplateConfigFeign;
	@Autowired
	private MfExamRiskLevelConfigFeign mfExamRiskLevelConfigFeign;
	@Autowired
	private DocBizSceConfigFeign docBizSceConfigFeign;
	@Autowired
	private ParamInterfaceFeign paramInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterface;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private BusiCacheFeign busiCacheFeign;
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private Map<String,Object> dataMap;
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "MfExamineTemplateConfig_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findByPageAjax")
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		try {
			mfExamineTemplateConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineTemplateConfig.setCriteriaList(mfExamineTemplateConfig, ajaxData);//我的筛选
			//this.getRoleConditions(mfExamineTemplateConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamineTemplateConfigFeign.findByPage(ipage, mfExamineTemplateConfig);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
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
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		try{
			FormData formexatemp0002 = formService.getFormData("exatemp0002");
			getFormValue(formexatemp0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexatemp0002)){
				MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
				setObjValue(formexatemp0002, mfExamineTemplateConfig);
				mfExamineTemplateConfig=mfExamineTemplateConfigFeign.insert(mfExamineTemplateConfig);
				dataMap.put("examTempConfig", mfExamineTemplateConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateAjaxByOne")
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>();
		FormData formexatemp0002 = formService.getFormData("exatemp0002");
		getFormValue(formexatemp0002, getMapByJson(ajaxData));
		MfExamineTemplateConfig mfExamineTemplateConfigJsp = new MfExamineTemplateConfig();
		setObjValue(formexatemp0002, mfExamineTemplateConfigJsp);
		MfExamineTemplateConfig mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfigJsp);
		if(mfExamineTemplateConfig!=null){
			try{
				mfExamineTemplateConfig = (MfExamineTemplateConfig)EntityUtil.reflectionSetVal(mfExamineTemplateConfig, mfExamineTemplateConfigJsp, getMapByJson(ajaxData));
				mfExamineTemplateConfigFeign.update(mfExamineTemplateConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		try{
			FormData formexatemp0002 = formService.getFormData("exatemp0002");
			getFormValue(formexatemp0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexatemp0002)){
				mfExamineTemplateConfig = new MfExamineTemplateConfig();
				setObjValue(formexatemp0002, mfExamineTemplateConfig);
				mfExamineTemplateConfig.setRateMin(0.0);
				mfExamineTemplateConfig.setRateMax(100.0);
				mfExamineTemplateConfigFeign.update(mfExamineTemplateConfig);
				dataMap.put("configData", mfExamineTemplateConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 检查文档模板保存时，修改文档模板名称
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-10 下午2:05:24
	 */
	@ResponseBody
	@RequestMapping(value="/updateDocuTempalteAjax")
	public Map<String,Object> updateDocuTempalteAjax(String templateId,String docuTemplate) throws Exception {
		ActionContext.initialize(request,
				response);
		
		dataMap = new HashMap<String, Object>(); 
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		try{
			mfExamineTemplateConfig.setTemplateId(templateId);
			mfExamineTemplateConfig.setDocuTemplate(docuTemplate);
			mfExamineTemplateConfigFeign.update(mfExamineTemplateConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 获得
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-10 下午2:06:24
	 */
	@ResponseBody
	@RequestMapping(value="/copyFormTemplateAjax")
	public Map<String,Object> copyFormTemplateAjax(String templateId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		try{
			mfExamineTemplateConfig.setTemplateId(templateId);
			mfExamineTemplateConfig=mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
			String formTemplate = mfExamineTemplateConfig.getBaseFormTemplate();
			if(mfExamineTemplateConfig.getFormTemplate()==null||"".equals(mfExamineTemplateConfig.getFormTemplate())){
				//复制母版表单
				FormData form=formService.getFormData(mfExamineTemplateConfig.getBaseFormTemplate());
				if(form.getFormId()!=null){
					formTemplate = formTemplate+"_"+templateId;
				}else{
					formTemplate = BizPubParm.FORMTEMPLATE;
				}
				form.setFormId(formTemplate);
				formService.saveForm(form,"insert");
				mfExamineTemplateConfig.setTemplateId(templateId);
				mfExamineTemplateConfig.setFormTemplate(formTemplate);
				mfExamineTemplateConfigFeign.update(mfExamineTemplateConfig);
			}else{
				formTemplate=mfExamineTemplateConfig.getFormTemplate();
			}
			dataMap.put("flag", "success");
			dataMap.put("formTemplate", formTemplate);
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	
	/**
	 * 
	 * 方法描述： 
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-10 上午10:14:17
	 */
	@ResponseBody
	@RequestMapping(value="/updateStsAjax")
	public Map<String,Object> updateStsAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		try{
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formexatemp0002 = formService.getFormData("exatemp0002");
			getFormValue(formexatemp0002, jobj);
			mfExamineTemplateConfig = new MfExamineTemplateConfig();
			setObjValue(formexatemp0002, mfExamineTemplateConfig);
			int count = mfExamineTemplateConfigFeign.updateSts(mfExamineTemplateConfig);
			if(count>0){
				mfExamineTemplateConfig =mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
				List<MfExamineTemplateConfig> mfExamineTemplateConfigList = new ArrayList<MfExamineTemplateConfig>();
				mfExamineTemplateConfigList.add(mfExamineTemplateConfig);
				getTableData(mfExamineTemplateConfigList,tableId);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	private void getTableData(List<MfExamineTemplateConfig> list,String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"thirdTableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String templateId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexatemp0002 = formService.getFormData("exatemp0002");
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		mfExamineTemplateConfig.setTemplateId(templateId);
		mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
		getObjValue(formexatemp0002, mfExamineTemplateConfig,formData);
		if(mfExamineTemplateConfig!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * 
	 * 方法描述：   获得匹配到的检查模板配置信息
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-13 下午5:32:36
	 */
	@ResponseBody
	@RequestMapping(value="/getConfigMatchedListAjax")
	public Map<String,Object> getConfigMatchedListAjax(String relId,String entFlag,String templateType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>();
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		mfExamineTemplateConfig.setTemplateType(templateType);
		List<MfExamineTemplateConfig> mfExamineTemplateConfigList = mfExamineTemplateConfigFeign.getExamineTemplateList(mfExamineTemplateConfig);
		if(mfExamineTemplateConfigList!=null){
			dataMap.put("flag", "success");
			dataMap.put("templateList", mfExamineTemplateConfigList);
		}else{
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/deleteAjax")
	public Map<String,Object> deleteAjax(String templateId) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		mfExamineTemplateConfig.setTemplateId(templateId);
		try {
			mfExamineTemplateConfigFeign.delete(mfExamineTemplateConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/input")
	public String input(Model model,String templateType) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexatemp0002 = formService.getFormData("exatemp0002");
		JSONArray repayTypeJson =new CodeUtils().getJSONArrayByKeyName("REPAY_TYPE");
		String repayTypeItems=repayTypeJson.toString().replaceAll("optName", "name").replace("optCode", "id");
		JSONArray cusTypeJson =new CodeUtils().getJSONArrayByKeyName("CUS_TYPE");
		String cusTypeItems=cusTypeJson.toString().replaceAll("optName", "name").replace("optCode", "id");
		JSONArray kindNoJson =new CodeUtils().getJSONArrayByKeyName("KIND_NO");
		String kindNoItems=kindNoJson.toString().replaceAll("optName", "name").replace("optCode", "id");
		dataMap = new HashMap<String, Object>();
		dataMap.put("repayTypeItems", repayTypeItems);
		dataMap.put("cusTypeItems", cusTypeItems);
		dataMap.put("kindNoItems", kindNoItems);
		 model.addAttribute("formexatemp0002", formexatemp0002);
		 model.addAttribute("dataMap", dataMap);
		 model.addAttribute("query", "");
		model.addAttribute("templateType",templateType);
		return "component/examine/MfExamineTemplateConfig_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formexatemp0002 = formService.getFormData("exatemp0002");
		 getFormValue(formexatemp0002);
		 MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		 setObjValue(formexatemp0002, mfExamineTemplateConfig);
		 mfExamineTemplateConfigFeign.insert(mfExamineTemplateConfig);
		 getObjValue(formexatemp0002, mfExamineTemplateConfig);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamineTemplateConfig> mfExamineTemplateConfigList = (List<MfExamineTemplateConfig>)mfExamineTemplateConfigFeign.findByPage(this.getIpage(), mfExamineTemplateConfig).getResult();
		 model.addAttribute("formexatemp0002", formexatemp0002);
		 model.addAttribute("mfExamineTemplateConfigList", mfExamineTemplateConfigList);
		return "component/examine/MfExamineTemplateConfig_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String templateId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexatemp0002 = formService.getFormData("exatemp0002");
		 getFormValue(formexatemp0002);
		 MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		 mfExamineTemplateConfig.setTemplateId(templateId);
		 mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
		 getObjValue(formexatemp0002, mfExamineTemplateConfig);
		 JSONArray repayTypeJson =new CodeUtils().getJSONArrayByKeyName("REPAY_TYPE");
		 String repayTypeItems=repayTypeJson.toString().replaceAll("optName", "name").replace("optCode", "id");
		 JSONArray cusTypeJson =new CodeUtils().getJSONArrayByKeyName("CUS_TYPE");
		 String cusTypeItems=cusTypeJson.toString().replaceAll("optName", "name").replace("optCode", "id");
		 JSONArray kindNoJson =new CodeUtils().getJSONArrayByKeyName("KIND_NO");
		 String kindNoItems=kindNoJson.toString().replaceAll("optName", "name").replace("optCode", "id");
		 dataMap = new HashMap<String, Object>();
		 dataMap.put("repayTypeItems", repayTypeItems);
		 dataMap.put("cusTypeItems", cusTypeItems);
		 dataMap.put("kindNoItems", kindNoItems);
		 model.addAttribute("formexatemp0002", formexatemp0002);
		 model.addAttribute("dataMap", dataMap);
		 model.addAttribute("mfExamineTemplateConfig", mfExamineTemplateConfig);
		 model.addAttribute("query", "");
		 return "component/examine/MfExamineTemplateConfig_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(String templateId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		mfExamineTemplateConfig.setTemplateId(templateId);
		mfExamineTemplateConfigFeign.delete(mfExamineTemplateConfig);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexatemp0002 = formService.getFormData("exatemp0002");
		 getFormValue(formexatemp0002);
		 boolean validateFlag = this.validateFormData(formexatemp0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexatemp0002 = formService.getFormData("exatemp0002");
		 getFormValue(formexatemp0002);
		 boolean validateFlag = this.validateFormData(formexatemp0002);
	}
	/**
	 * 
	 * 方法描述： 跳转贷后配置页面
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-7-17 下午7:53:10
	 */
	@RequestMapping(value="/getLoanAfterConfig")
	public String getLoanAfterConfig(Model model) throws Exception{
		ActionContext.initialize(request, response);
		//获取贷后的保后跟踪模型配置列表
		JSONObject json = new JSONObject();
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		List<MfExamineTemplateConfig> mfExamineTemplateConfigList = new ArrayList<>();
		List<MfExamineTemplateConfig> mfExamineTemplateConfigAllList = mfExamineTemplateConfigFeign.getExamineTemplateList(mfExamineTemplateConfig);
		for(int i=0;mfExamineTemplateConfigAllList.size()>i;i++){
			mfExamineTemplateConfig = mfExamineTemplateConfigAllList.get(i);
			if("0".equals(mfExamineTemplateConfig.getTemplateType())||"2".equals(mfExamineTemplateConfig.getTemplateType())){
				mfExamineTemplateConfigList.add(mfExamineTemplateConfig);
			}
		}
		MfExamRiskLevelConfig mfExamRiskLevelConfig =new MfExamRiskLevelConfig();
		List<MfExamRiskLevelConfig> mfExamRiskLevelConfigList = mfExamRiskLevelConfigFeign.getExamRiskLevelConfigList(mfExamRiskLevelConfig);
		json=mfExamineTemplateConfigFeign.getDocAndUploadConfigData();
		json.put("mfExamineTemplateConfigListData", JSONArray.fromObject(mfExamineTemplateConfigList).toString());
		//获取贷后的风险模型配置列表
		mfExamineTemplateConfig.setTemplateType("1");
		List<MfExamineTemplateConfig> mfExamineTemplateConfigRiskList = mfExamineTemplateConfigFeign.getExamineTemplateList(mfExamineTemplateConfig);
		json.put("mfExamineTemplateConfigRiskListData", JSONArray.fromObject(mfExamineTemplateConfigRiskList).toString());
		//获取贷后的流程设置列表
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setCategory(BizPubParm.FLOW_CATEGORY_4);
		List<MfKindFlow> loanAfterflowList = prdctInterface.getKindFlowList(mfKindFlow);
		//贷后参数设置
		Map<String, ParmDic> dicMap = new CodeUtils().getMapObjByKeyName("PRE_REPAY_APPLY_FLAG");
		String preRepayApplyFlag = dicMap.get("1").getRemark();
		//核销数据来源
		Map<String, ParmDic> checkOffMap = new CodeUtils().getMapObjByKeyName("CHECKOFF_DATASOURCE");
		String checkoffDatasource = checkOffMap.get("1").getRemark();
		//核销数据来源 -逾期天数设置
		Map<String, ParmDic> checkOffOverDaysMap = new CodeUtils().getMapObjByKeyName("CHECKOFF_DATASOURCE_OVERDAYS");
		String checkoffDatasourceOverDays = checkOffOverDaysMap.get("1").getRemark();
		//核销数据来源 -逾期天数设置
		Map<String, ParmDic> finishAfterPrcpCheckMap = new CodeUtils().getMapObjByKeyName("FINISH_AFTER_PRCP_CHECK");
		String finishAfterPrcpCheck = finishAfterPrcpCheckMap.get("1").getRemark();
		//展期业务审批流程
		mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setFlowApprovalNo(WKF_NODE.extension_approve.getNodeNo());
		mfKindFlow=prdctInterface.getKindFlow(mfKindFlow);
		json.put("extenApproveFlow", mfKindFlow);
		json.putAll(mfExamineTemplateConfigFeign.getExtensionConfigData());
		String ajaxData = json.toString();
		model.addAttribute("mfExamineTemplateConfigList", mfExamineTemplateConfigList);
		model.addAttribute("mfExamineTemplateConfigRiskList", mfExamineTemplateConfigRiskList);
		model.addAttribute("mfExamRiskLevelConfigList", mfExamRiskLevelConfigList);
		model.addAttribute("loanAfterflowList", loanAfterflowList);
		model.addAttribute("preRepayApplyFlag", preRepayApplyFlag);
		model.addAttribute("checkoffDatasource", checkoffDatasource);
		model.addAttribute("checkoffDatasourceOverDays", checkoffDatasourceOverDays);
		model.addAttribute("finishAfterPrcpCheck", finishAfterPrcpCheck);
		model.addAttribute("ajaxData", ajaxData);
		return "/component/examine/MfLoanAfterConfig";
	}
	
	/**
	 * 
	 * 方法描述： 更新数据字典参数（用来处理全局参数中的启用禁用标志）
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-8-17 下午4:57:58
	 */
	@ResponseBody
	@RequestMapping(value="/updateParamUseFlagAjax")
	public Map<String,Object> updateParamUseFlagAjax(String keyName,String preRepayApplyFlag) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName(keyName);
		parmDic.setOptCode("1");
		parmDic = nmdInterfaceFeign.getParmDicById(parmDic);
		parmDic.setRemark(preRepayApplyFlag);
		nmdInterfaceFeign.updateParmDic(parmDic);
		busiCacheFeign.refreshAllParmDic();
		return dataMap;
	}
	/**
	 * 修改核销参数
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateCheckOffParamUseFlagAjax")
	public Map<String,Object> updateCheckOffParamUseFlagAjax(String checkoffDatasource,String checkoffDatasourceOverDays) throws Exception{
		ActionContext.initialize(request, response);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("CHECKOFF_DATASOURCE");
		parmDic.setOptCode("1");
		parmDic = nmdInterfaceFeign.getParmDicById(parmDic);
		parmDic.setRemark(checkoffDatasource);
		nmdInterfaceFeign.updateParmDic(parmDic);
		if("2".equals(checkoffDatasource)){
			//如果是逾期天数 修改逾期天数的值
			parmDic = new ParmDic();
			parmDic.setKeyName("CHECKOFF_DATASOURCE_OVERDAYS");
			parmDic.setOptCode("1");
			parmDic = nmdInterfaceFeign.getParmDicById(parmDic);
			parmDic.setRemark(checkoffDatasourceOverDays);
			nmdInterfaceFeign.updateParmDic(parmDic);
		}
		
		busiCacheFeign.refreshAllParmDic();
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 获得贷后检查表单
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-7-18 下午2:38:40
	 */
	@ResponseBody
	@RequestMapping(value="/getOpenFormTemplate")
	public Map<String,Object> getOpenFormTemplate(String templateId) throws Exception{
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		dataMap.put("flag", "success");
		dataMap.put("formId", mfExamineTemplateConfigFeign.getOpenFormTemplate(templateId));
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 打开贷后检查检查项配置页面
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-7-24 下午2:53:14
	 */
	@RequestMapping(value="/getExamineIndexConfigSetting")
	public String getExamineIndexConfigSetting(Model model,String templateId) throws Exception{
		ActionContext.initialize(request,response);
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		mfExamineTemplateConfig.setTemplateId(templateId);
		mfExamineTemplateConfig =mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
		model.addAttribute("mfExamineTemplateConfig", mfExamineTemplateConfig);
		return "component/examine/ExamineIndexConfig_Setting";
	}
	
	@ResponseBody
	@RequestMapping(value="/getByIdForExamIndexListAjax")
	public Map<String,Object> getByIdForExamIndexListAjax(String templateId) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
		mfExamineTemplateConfig.setTemplateId(templateId);
		mfExamineTemplateConfig =mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
		dataMap=mfExamineTemplateConfigFeign.getListData(templateId);
		dataMap.put("entityData", mfExamineTemplateConfig);
		dataMap.put("flag", "success");
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 打开检查模型要件配置页面
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-7-25 下午5:44:03
	 */
	@RequestMapping(value="/getExamDocConfig")
	public String getExamDocConfig(Model model,String tempSerialId) throws Exception {
		try {
			ActionContext.initialize(request, response);
			 FormService formService = new FormService();
			MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
			//mfExamineTemplateConfig.setTemplateId(templateId);
			mfExamineTemplateConfig.setTempSerialId(tempSerialId);
			mfExamineTemplateConfig =mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
			FormData formexamdoc = formService.getFormData("examdoc");
			getObjValue(formexamdoc, mfExamineTemplateConfig);
			model.addAttribute("formexamdoc", formexamdoc);
			// 查询要件场景
			Scence scence = new Scence();
			/**
			 * 暂时不清楚ScenceType什么意义
			 * 业务节点使用的01，其他非业务暂时使用02
			 */
			scence.setScenceType("02");
			scence.setUseFlag(BizPubParm.YES_NO_Y);
			List<Scence> scenceList = paramInterfaceFeign.getAllScence();

			// 按照客户类型查询已经配置的要件
			DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
			docBizSceConfig.setDime1(tempSerialId);
			List<DocBizSceConfig> docBizSceConfigList = docBizSceConfigFeign.getByDime(docBizSceConfig);

			String ajaxData = JSONArray.fromObject(docBizSceConfigList).toString();
			String scenceListAjaxData = JSONArray.fromObject(scenceList).toString();
			model.addAttribute("ajaxData", ajaxData);
			model.addAttribute("scenceListAjaxData", scenceListAjaxData);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return "getExamDocConfig";
	}
	
	@ResponseBody
	@RequestMapping(value="/updateDocAjax")
	public Map<String,Object> updateDocAjax(String ajaxData, String tempSerialId, String ajaxDataList) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> mapByJson = getMapByJson(ajaxData);
			String optCode = tempSerialId;//客户类型编号
			JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
			List<DocBizSceConfig> docBizSceConfigList = (List<DocBizSceConfig>) JSONArray.toList(jsonArray, new DocBizSceConfig(), new JsonConfig());
			mfExamineTemplateConfigFeign.updateDoc(optCode, docBizSceConfigList);
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

	
}
