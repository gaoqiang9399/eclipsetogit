package app.component.auth.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.auth.feign.MfCusCreditConfigFeign;
import app.component.auth.entity.MfCusCreditConfig;
import app.component.nmd.entity.ParmDic;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysRole;
import app.component.sysInterface.SysInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: MfCusCreditConfigController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 13 16:22:08 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfCusCreditConfig")
public class MfCusCreditConfigController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCreditConfigFeign mfCusCreditConfigFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private MfSysKindFeign mfSysKindFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/auth/MfCusCreditConfig_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
		try {
			mfCusCreditConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCreditConfig.setCriteriaList(mfCusCreditConfig, ajaxData);//我的筛选
			//mfCusCreditConfig.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusCreditConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfCusCreditConfig", mfCusCreditConfig));
			//自定义查询Feign方法
			ipage = mfCusCreditConfigFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formcusCreditConfigAdd = formService.getFormData("cusCreditConfigAdd");
			getFormValue(formcusCreditConfigAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formcusCreditConfigAdd)){
				MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
				setObjValue(formcusCreditConfigAdd, mfCusCreditConfig);
				mfCusCreditConfig = mfCusCreditConfigFeign.insert(mfCusCreditConfig);
				dataMap.put("mfCusCreditConfig", mfCusCreditConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusCreditConfigAdd = formService.getFormData("cusCreditConfigAdd");
		getFormValue(formcusCreditConfigAdd, getMapByJson(ajaxData));
		MfCusCreditConfig mfCusCreditConfigJsp = new MfCusCreditConfig();
		setObjValue(formcusCreditConfigAdd, mfCusCreditConfigJsp);
		MfCusCreditConfig mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfigJsp);
		if(mfCusCreditConfig!=null){
			try{
				mfCusCreditConfig = (MfCusCreditConfig)EntityUtil.reflectionSetVal(mfCusCreditConfig, mfCusCreditConfigJsp, getMapByJson(ajaxData));
				mfCusCreditConfigFeign.update(mfCusCreditConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
		try{
			FormData formcusCreditConfigAdd = formService.getFormData("cusCreditConfigAdd");
			getFormValue(formcusCreditConfigAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formcusCreditConfigAdd)){
				mfCusCreditConfig = new MfCusCreditConfig();
				setObjValue(formcusCreditConfigAdd, mfCusCreditConfig);
				mfCusCreditConfigFeign.update(mfCusCreditConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String creditId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusCreditConfigAdd = formService.getFormData("cusCreditConfigAdd");
		MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
		mfCusCreditConfig.setCreditId(creditId);
		mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
		getObjValue(formcusCreditConfigAdd, mfCusCreditConfig,formData);
		if(mfCusCreditConfig!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	@RequestMapping(value = "/updateCreditConfigAjax")
	@ResponseBody
	public Map<String, Object> updateCreditConfigAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCusCreditConfig = (MfCusCreditConfig) JSONObject.toBean(jb, MfCusCreditConfig.class);
			dataMap = mfCusCreditConfigFeign.updateCreditConfig(mfCusCreditConfig);
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
	@RequestMapping(value = "/getCreditConfigList")
	@ResponseBody
	public Map<String, Object> getCreditConfigList(String creditModel) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
		mfCusCreditConfig.setCreditModel(creditModel);
		try {
			List<MfCusCreditConfig> mfCusCreditConfigs = mfCusCreditConfigFeign.getCreditConfigList(mfCusCreditConfig);
			JSONArray resultMap = new JSONArray();// 获取授信产品配置
			for (int i = 0; i < mfCusCreditConfigs.size(); i++) {
				String creditId = mfCusCreditConfigs.get(i).getCreditId();
				if(!"credit1".equals(creditId) && !"credit2".equals(creditId)){
					JSONObject obj = new JSONObject();
					obj.put("id", mfCusCreditConfigs.get(i).getCreditId());
					obj.put("name",  mfCusCreditConfigs.get(i).getCreditName());
					resultMap.add(obj);
				}
			}
			dataMap.put("templateCredit", resultMap);
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
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String creditId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
		mfCusCreditConfig.setCreditId(creditId);
		try {
			mfCusCreditConfigFeign.delete(mfCusCreditConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusCreditConfigAdd = formService.getFormData("cusCreditConfigAdd");
		JSONObject json = new JSONObject();
		// 角色
		SysRole sysRole = new SysRole ();
		JSONArray roleArray = JSONArray.fromObject(sysInterfaceFeign.getAllSysRole(sysRole));
		for (int i = 0; i < roleArray.size(); i++) {
			roleArray.getJSONObject(i).put("id", roleArray.getJSONObject(i).getString("roleNo"));
			roleArray.getJSONObject(i).put("name", roleArray.getJSONObject(i).getString("roleName"));
		}
		json.put("role", roleArray);
		CodeUtils cu = new CodeUtils();
		// 客户类型
		List<ParmDic> parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("CUS_TYPE");
		JSONArray cusTypeArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < cusTypeArray.size(); i++) {
			cusTypeArray.getJSONObject(i).put("id", cusTypeArray.getJSONObject(i).getString("optCode"));
			cusTypeArray.getJSONObject(i).put("name", cusTypeArray.getJSONObject(i).getString("optName"));
		}
		json.put("cusType", cusTypeArray);
		//产品
		List<MfSysKind> mfSysKinds = mfSysKindFeign.getSysKindList(new MfSysKind());
		JSONArray sysKindArray = new JSONArray();
		for (int i = 0; i < mfSysKinds.size(); i++) {
			JSONObject js = new JSONObject();
			js.put("id", mfSysKinds.get(i).getKindNo());
			js.put("name", mfSysKinds.get(i).getKindName());
			sysKindArray.add(js);
		}
		json.put("sysKind", sysKindArray);
		// 部门
		SysOrg sysOrg = new SysOrg ();
		JSONArray orgArray = JSONArray.fromObject( sysInterfaceFeign.getAllOrgJson(sysOrg));
		for (int i = 0; i < orgArray.size(); i++) {
			orgArray.getJSONObject(i).remove("iconSkin");
		}
		json.put("org", orgArray);
		// 模板授信
		MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
		mfCusCreditConfig.setCreditModel(BizPubParm.CREDIT_MODEL_CUS);
		List<MfCusCreditConfig> mfCusCreditConfigs = mfCusCreditConfigFeign.getCreditConfigList(mfCusCreditConfig);
		JSONArray resultMap = new JSONArray();// 获取授信产品配置
		for (int i = 0; i < mfCusCreditConfigs.size(); i++) {
			String creditId = mfCusCreditConfigs.get(i).getCreditId();
			if(!"credit1".equals(creditId) && !"credit2".equals(creditId)){
				JSONObject obj = new JSONObject();
				obj.put("id", mfCusCreditConfigs.get(i).getCreditId());
				obj.put("name",  mfCusCreditConfigs.get(i).getCreditName());
				resultMap.add(obj);
			}
		}
		json.put("templateCredit", resultMap);
		// 授信流程
		List<MfCusCreditConfig> mfCusCreditConfigList = mfCusCreditConfigFeign.getCreditAndProjectList();
		JSONArray apaCreditKindMap = new JSONArray();// 获取适配授信产品
		for (int i = 0; i < mfCusCreditConfigList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("id", mfCusCreditConfigList.get(i).getCreditId());
            obj.put("name",  mfCusCreditConfigList.get(i).getCreditName());
            apaCreditKindMap.add(obj);
		}
		json.put("apaCreditKind", apaCreditKindMap);
		//20200225 增加业务模式 xzp
		List<ParmDic> busModellist = (List<ParmDic>) cu.getCacheByKeyName("BUS_MODEL");
		JSONArray busModelArray = JSONArray.fromObject(busModellist);
		for (int i = 0; i < busModelArray.size(); i++) {
			busModelArray.getJSONObject(i).put("id", busModelArray.getJSONObject(i).getString("optCode"));
			busModelArray.getJSONObject(i).put("name", busModelArray.getJSONObject(i).getString("optName"));
		}
		json.put("busModel", busModelArray);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("formcusCreditConfigAdd", formcusCreditConfigAdd);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditConfig_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String creditId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusCreditConfigDetail = formService.getFormData("cusCreditConfigDetail");
		getFormValue(formcusCreditConfigDetail);
		MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
		mfCusCreditConfig.setCreditId(creditId);
		mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
		getObjValue(formcusCreditConfigDetail, mfCusCreditConfig);
		model.addAttribute("formcusCreditConfigDetail", formcusCreditConfigDetail);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditConfig_Detail";
	}

    /**
     * 根据客户号获取适配的授信流程
     * @param cusNo
     * @return
     * @throws Exception
     */
	@RequestMapping("/getListByCusNo")
    @ResponseBody
	public Map <String, Object> getListByCusNo(String cusNo) throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
        mfCusCreditConfig.setCusNo(cusNo);
        try {
            dataMap = mfCusCreditConfigFeign.getListByCusNo(mfCusCreditConfig);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "根据客户号获取授信流程失败");
        }
        return dataMap;
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusCreditConfigAdd = formService.getFormData("cusCreditConfigAdd");
		getFormValue(formcusCreditConfigAdd);
		boolean validateFlag = this.validateFormData(formcusCreditConfigAdd);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusCreditConfigAdd = formService.getFormData("cusCreditConfigAdd");
		getFormValue(formcusCreditConfigAdd);
		boolean validateFlag = this.validateFormData(formcusCreditConfigAdd);
	}


}
