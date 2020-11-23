package  app.component.cus.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;

import app.base.User;
import app.component.busviewinterface.BusViewInterfaceFeign;
//import app.component.busviewinterface.BusViewInterface;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusAgencies;
import app.component.cus.entity.MfBusAgenciesConfig;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfBusAgenciesConfigFeign;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.cus.trenchsubsidiary.entity.MfTrenchCreditAmtModifyHis;
import app.component.cus.trenchsubsidiary.feign.MfTrenchCreditAmtModifyHisFeign;
import app.component.query.entity.MfQueryItem;
import app.component.query.feign.MfQueryItemFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfBusAgenciesAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 25 10:04:29 CST 2017
 **/
@Controller
@RequestMapping("/mfBusAgencies")
public class MfBusAgenciesController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusAgenciesFeign mfBusAgenciesFeign;
	@Autowired
	private MfBusAgenciesConfigFeign mfBusAgenciesConfigFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfTrenchCreditAmtModifyHisFeign mfTrenchCreditAmtModifyHisFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfQueryItemFeign mfQueryItemFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		String tableId="mfbusagencieslist";
		MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType("base", "MfBusAgenciesAction");
		if(mc != null){
			tableId = mc.getListModelDef();
		}
		model.addAttribute("tableId", tableId);
		// 获取展示名称
		MfQueryItem mfQueryItem = new MfQueryItem();
		mfQueryItem.setItemId("agencies");
		mfQueryItem.setIsBase(BizPubParm.YES_NO_Y);
		mfQueryItem = mfQueryItemFeign.getById(mfQueryItem);
		model.addAttribute("itemName", mfQueryItem.getItemName());
		model.addAttribute("showName", mfQueryItem.getShowName());
		return "component/cus/MfBusAgencies_List";
	}

	/**
	 *  上级资金机构选择页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectPage")
	public String getSelectPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "component/cus/MfBusAgencies_selectPage";
	}

	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		try {
			mfBusAgencies.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAgencies.setCriteriaList(mfBusAgencies, ajaxData);//我的筛选
			mfBusAgencies.setCustomSorts(ajaxData);// 自定义排序参数赋值
			//this.getRoleConditions(mfBusAgencies,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusAgencies", mfBusAgencies));
			ipage = mfBusAgenciesFeign.findByPage(ipage);
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
	
	@RequestMapping(value = "/getMfBusAgenciesListAjax")
	@ResponseBody
	public Map<String,Object> getMfBusAgenciesListAjax(int pageNo,MfBusAgencies mfBusAgencies)throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusAgencies", mfBusAgencies));
			ipage = mfBusAgenciesFeign.findByPage(ipage);
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
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String ,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String,Object> map=getMapByJson(ajaxData)==null?new HashMap():getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfbusagencies0001 = formService.getFormData(formId);
			getFormValue(formmfbusagencies0001, getMapByJson(ajaxData));
			if(this.validateFormData(formmfbusagencies0001)){
				MfBusAgencies mfBusAgencies = new MfBusAgencies();
				setObjValue(formmfbusagencies0001, mfBusAgencies);
				if(mfBusAgencies.getParentAgenciesName() != null && !"".equals(mfBusAgencies.getParentAgenciesName())
				&& mfBusAgencies.getParentAgenciesName().equals(mfBusAgencies.getAgenciesName())){
					dataMap.put("flag", "error");
					dataMap.put("msg","资金机构名称和上级资金机构名称不能一致！");
					return dataMap;
				}
				mfBusAgencies.setCreditBal(mfBusAgencies.getCreditAmt());
				mfBusAgenciesFeign.insert(mfBusAgencies);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/insertConfigAjax")
	@ResponseBody
	public Map<String ,Object> insertConfigAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String,Object> map=getMapByJson(ajaxData)==null?new HashMap():getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfbusagenciesconfig0001 = formService.getFormData(formId);
			getFormValue(formmfbusagenciesconfig0001, getMapByJson(ajaxData));
			
			if(this.validateFormData(formmfbusagenciesconfig0001)){
				MfBusAgenciesConfig mfBusAgenciesConfig = new MfBusAgenciesConfig();
				setObjValue(formmfbusagenciesconfig0001, mfBusAgenciesConfig);
				
				MfBusAgenciesConfig query = new MfBusAgenciesConfig();
				query.setAgenciesDetailUid(mfBusAgenciesConfig.getAgenciesDetailUid());
				List<MfBusAgenciesConfig> list = mfBusAgenciesConfigFeign.getMfBusAgenciesConfigList(query);
				if(list != null && list.size() > 0){
					dataMap.put("id", "");
					dataMap.put("flag", "error");
					dataMap.put("msg", "记录已经存在，不可以重复配置");
				}else{
					mfBusAgenciesConfig = mfBusAgenciesConfigFeign.insert(mfBusAgenciesConfig);
					dataMap.put("id", mfBusAgenciesConfig.getId());
					dataMap.put("flag", "success");
					dataMap.put("msg", "新增成功");
				}
				
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateConfigAjax")
	@ResponseBody
	public Map<String ,Object> updateConfigAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String,Object> map=getMapByJson(ajaxData)==null?new HashMap():getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfbusagenciesconfig0001 = formService.getFormData(formId);
			getFormValue(formmfbusagenciesconfig0001, getMapByJson(ajaxData));
			
			if(this.validateFormData(formmfbusagenciesconfig0001)){
				MfBusAgenciesConfig mfBusAgenciesConfig = new MfBusAgenciesConfig();
				setObjValue(formmfbusagenciesconfig0001, mfBusAgenciesConfig);
				mfBusAgenciesConfigFeign.update(mfBusAgenciesConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}



	
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		Map<String,Object> paramMap = getMapByJson(ajaxData);
		String limitAmt = String.valueOf(paramMap.get("limitAmt"));
		if(!"null".equals(limitAmt)){
			paramMap.put("limitAmt", limitAmt.replaceAll(",", ""));
		}
		String balanceAmt = String.valueOf(paramMap.get("balanceAmt"));
		if(!"null".equals(balanceAmt)){
			paramMap.put("balanceAmt", balanceAmt.replaceAll(",", ""));
		}
		MfBusAgencies mfBusAgenciesJsp=new Gson().fromJson(new Gson().toJson(paramMap), MfBusAgencies.class);
		MfBusAgencies mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgenciesJsp);
		if(mfBusAgencies!=null){
			try{
				mfBusAgencies = (MfBusAgencies)EntityUtil.reflectionSetVal(mfBusAgencies, mfBusAgenciesJsp, getMapByJson(ajaxData));
				mfBusAgenciesFeign.update(mfBusAgencies);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		String query="";
		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		request.setAttribute("ifBizManger", "3");
		try{
			//formmfbusagencies0002 = formService.getFormData("mfbusagencies0002");
			String formId = "mfbusagencies0002";
			MfCusType mfCusType=new MfCusType();
			mfCusType.setBaseType(BizPubParm.CUS_BASE_TYPE_ZIJIN);
			mfCusType.setUseFlag("1");
			List<MfCusType> list=mfCusTypeFeign.getAllList(mfCusType);
			
			if(list!=null&&list.size()>0){
				String cusType=list.get(0).getTypeNo();//第一个会是表单的默认项
				MfCusFormConfig mc=mfCusFormConfigFeign.getByCusType(cusType, "MfBusAgenciesAction");
				if(mc!=null){
					formId=mc.getAddModelDef();
				}
			}
			FormData formmfbusagencies0002 = formService.getFormData(formId);
			getFormValue(formmfbusagencies0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmfbusagencies0002)){
				mfBusAgencies = new MfBusAgencies();
				setObjValue(formmfbusagencies0002, mfBusAgencies);
				mfBusAgenciesFeign.update(mfBusAgencies);
//				mfBusAgencies=mfBusAgenciesFeign.getById(mfBusAgencies);
				Map<String,String> cusInfoMap=this.transViewBean(mfBusAgencies.getAgenciesUid());
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfBusAgencies.getCusType(), "MfBusAgenciesAction");
				if (mfCusFormConfig == null) {

				} else {
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formToAjaxByOne = formService.getFormData(formId);
				if (formToAjaxByOne.getFormId() == null) {
//					logger.error("资金机构客户类型为" + mfBusAgencies.getCusType() + "的MfBusAgenciesAction表单form" + formId + ".xml文件不存在");
				}
				getFormValue(formToAjaxByOne);
				getObjValue(formToAjaxByOne, mfBusAgencies);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formToAjaxByOne, "propertySeeTag", query);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("cusInfo", cusInfoMap);
				dataMap.put("flag", "success");
				dataMap.put("msg",  MessageEnum.SUCCEED_OPERATION.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",  MessageEnum.FAILED_OPERATION.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String agenciesId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formmfbusagencies0002 = formService.getFormData("mfbusagencies0002");
		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		mfBusAgencies.setAgenciesId(agenciesId);
		mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
		getObjValue(formmfbusagencies0002, mfBusAgencies,formData);
		if(mfBusAgencies!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String agenciesId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		mfBusAgencies.setAgenciesId(agenciesId);
		try {
			mfBusAgenciesFeign.delete(mfBusAgencies);
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

	@RequestMapping(value = "/deleteConfigAjax")
	@ResponseBody
	public Map<String,Object> deleteConfigAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfBusAgenciesConfig mfBusAgenciesConfig = new MfBusAgenciesConfig();
		mfBusAgenciesConfig.setId(id);
		try{
			mfBusAgenciesConfigFeign.delete(mfBusAgenciesConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg", "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "删除失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		String typeNo=request.getParameter("typeNo");
		String formId="";
		if(StringUtil.isNotEmpty(typeNo)){
			MfCusFormConfig mc=mfCusFormConfigFeign.getByCusType(typeNo, "MfBusAgenciesAction");
			if(mc!=null){
				formId=mc.getAddModelDef();
			}
		}else{
			MfCusType mfCusType=new MfCusType();
			mfCusType.setBaseType(BizPubParm.CUS_BASE_TYPE_ZIJIN);
			mfCusType.setUseFlag("1");
			List<MfCusType> list=mfCusTypeFeign.getAllList(mfCusType);
			
			if(list!=null&&list.size()>0){
				String cusType=list.get(0).getTypeNo();//第一个会是表单的默认项
				MfCusFormConfig mc=mfCusFormConfigFeign.getByCusType(cusType, "MfBusAgenciesAction");
				if(mc!=null){
					formId=mc.getAddModelDef();
				}
			}
		}
		if(StringUtil.isEmpty(formId)){
			formId="mfbusagencies0001";
		}
		FormData formmfbusagencies0001 = formService.getFormData(formId);
		getFormValue(formmfbusagencies0001);
		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		mfBusAgencies.setCusType(typeNo);
		getObjValue(formmfbusagencies0001, mfBusAgencies);
		model.addAttribute("formmfbusagencies0001", formmfbusagencies0001);
		model.addAttribute("query", "");
		return "component/cus/MfBusAgencies_Insert";
	}

	
	@RequestMapping(value = "/inputAgenciesConfig")
	public String inputAgenciesConfig(Model model,String agenciesUid) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfbusagenciesconfig0001 = formService.getFormData("mfbusagenciesconfig0001");
		getFormValue(formmfbusagenciesconfig0001);
		
		MfBusAgenciesConfig mfBusAgenciesConfig = new MfBusAgenciesConfig();
		mfBusAgenciesConfig.setAgenciesUid(agenciesUid);
		getObjValue(formmfbusagenciesconfig0001,mfBusAgenciesConfig);
		
		JSONObject json = new JSONObject();
		JSONArray agenciesBankListArray = new CodeUtils().getJSONArrayByKeyName("AGENCIES_BANK");
		JSONArray newAgenciesBankListArray = new JSONArray();
		JSONObject clearAgenciesBankObject = null;
		for(int i = 0;i < agenciesBankListArray.size(); i++){
			clearAgenciesBankObject = new JSONObject();
			clearAgenciesBankObject.put("id", agenciesBankListArray.getJSONObject(i).getString("optCode"));
			clearAgenciesBankObject.put("name", agenciesBankListArray.getJSONObject(i).getString("optName"));
			newAgenciesBankListArray.add(clearAgenciesBankObject);
		}
		json.put("agenciesBank", newAgenciesBankListArray);
		
		model.addAttribute("formmfbusagenciesconfig0001", formmfbusagenciesconfig0001);
		model.addAttribute("agenciesUid", agenciesUid);
		model.addAttribute("agenciesBank", json.toString());
		model.addAttribute("query", "");
		return "component/cus/MfBusAgencies_InsertConfig";
	}
	
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formmfbusagencies0002 = formService.getFormData("mfbusagencies0002");
		 getFormValue(formmfbusagencies0002);
		 MfBusAgencies mfBusAgencies = new MfBusAgencies();
		 setObjValue(formmfbusagencies0002, mfBusAgencies);
		 mfBusAgenciesFeign.insert(mfBusAgencies);
		 getObjValue(formmfbusagencies0002, mfBusAgencies);
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfBusAgencies", mfBusAgencies));
		 this.addActionMessage(model, "保存成功");
		 List<MfBusAgencies> mfBusAgenciesList = (List<MfBusAgencies>)mfBusAgenciesFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("formmfbusagencies0002", formmfbusagencies0002);
		model.addAttribute("mfBusAgenciesList", mfBusAgenciesList);
		model.addAttribute("query", "");
		return "component/cus/MfBusAgencies_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String agenciesId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		String formId = "mfbusagencies0002";
		
		// 动态获取formId
		MfCusType mfCusType=new MfCusType();
		mfCusType.setBaseType(BizPubParm.CUS_BASE_TYPE_ZIJIN);
		mfCusType.setUseFlag("1");
		List<MfCusType> list=mfCusTypeFeign.getAllList(mfCusType);
		if(list!=null&&list.size()>0){
			String cusType=list.get(0).getTypeNo();//第一个会是表单的默认项
			MfCusFormConfig mc=mfCusFormConfigFeign.getByCusType(cusType, "MfBusAgenciesAction");
			if(mc!=null){
				formId=mc.getAddModelDef();
			}
		}
		 FormData formmfbusagencies0002 = formService.getFormData(formId);
		 getFormValue(formmfbusagencies0002);
		 MfBusAgencies mfBusAgencies = new MfBusAgencies();
		 mfBusAgencies.setAgenciesId(agenciesId);
		 mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
		 getObjValue(formmfbusagencies0002, mfBusAgencies);
		 model.addAttribute("formmfbusagencies0002", formmfbusagencies0002);
		 model.addAttribute("query", "");
		return "component/cus/MfBusAgencies_Detail";
	}


	
	@RequestMapping(value = "/getMfBusAgenciesDetailWkfHis")
	public String getMfBusAgenciesDetailWkfHis(Model model,String wkfAppId,String agenciesUid,String agenciesDetailUid,String approvalStatus) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("agenciesUid", agenciesUid);
		model.addAttribute("agenciesDetailUid", agenciesDetailUid);
		model.addAttribute("approvalStatus", approvalStatus);
		return "component/cus/MfBusAgenciesDetail_WkfHis";
	}
	
	@RequestMapping(value = "/getAgenciesConfigById")
	public String getAgenciesConfigById(Model model,String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfbusagenciesconfig0001 = formService.getFormData("mfbusagenciesconfig0001");
		getFormValue(formmfbusagenciesconfig0001);
		MfBusAgenciesConfig mfBusAgenciesConfig = new MfBusAgenciesConfig();
		mfBusAgenciesConfig.setId(id);
		mfBusAgenciesConfig = mfBusAgenciesConfigFeign.getById(mfBusAgenciesConfig);
		getObjValue(formmfbusagenciesconfig0001, mfBusAgenciesConfig);
		
		model.addAttribute("agenciesDetailName" ,mfBusAgenciesConfig.getAgenciesDetailName());
		model.addAttribute("formmfbusagenciesconfig0001", formmfbusagenciesconfig0001);
		model.addAttribute("query", "");

		return "component/cus/MfBusAgenciesConfig_Detail";
	}
	
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String agenciesId) throws Exception {
		ActionContext.initialize(request,response);
		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		mfBusAgencies.setAgenciesId(agenciesId);
		mfBusAgenciesFeign.delete(mfBusAgencies);
		return getListPage(model);
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
		 FormData formmfbusagencies0002 = formService.getFormData("mfbusagencies0002");
		 getFormValue(formmfbusagencies0002);
		 boolean validateFlag = this.validateFormData(formmfbusagencies0002);
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
		 FormData formmfbusagencies0002 = formService.getFormData("mfbusagencies0002");
		 getFormValue(formmfbusagencies0002);
		 boolean validateFlag = this.validateFormData(formmfbusagencies0002);
	}
	
	@RequestMapping(value = "/getAgenciesBankNotShowAjax")
	@ResponseBody
	public Map<String,Object> getAgenciesBankNotShowAjax(String agenciesUid) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		try{
			JSONArray agenciesBankListArray = new CodeUtils().getJSONArrayByKeyName("AGENCIES_BANK");
			JSONArray newAgenciesBankListArray = new JSONArray();
			JSONObject clearAgenciesBankObject = null;
			MfBusAgenciesConfig mfBusAgenciesConfig ;
			for(int i = 0;i < agenciesBankListArray.size(); i++){
				mfBusAgenciesConfig = new  MfBusAgenciesConfig();
				mfBusAgenciesConfig.setAgenciesUid(agenciesUid);
				mfBusAgenciesConfig.setAgenciesDetailUid(agenciesBankListArray.getJSONObject(i).getString("optCode"));
				List<MfBusAgenciesConfig> list = mfBusAgenciesConfigFeign.getMfBusAgenciesConfigList(mfBusAgenciesConfig);
				if(list != null && list.size() > 0){
					
				}else{
					clearAgenciesBankObject = new JSONObject();
					clearAgenciesBankObject.put("id", agenciesBankListArray.getJSONObject(i).getString("optCode"));
					clearAgenciesBankObject.put("name", agenciesBankListArray.getJSONObject(i).getString("optName"));
					newAgenciesBankListArray.add(clearAgenciesBankObject);
				}
			}
			dataMap.put("agenciesBankNotShow", newAgenciesBankListArray);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getAgenciesBankInfoAjax")
	@ResponseBody
	public Map<String,Object> getAgenciesBankInfoAjax(String agenciesUid,String agenciesDetailUid) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		try{
			MfBusAgenciesConfig mfBusAgenciesConfig = new MfBusAgenciesConfig();
			mfBusAgenciesConfig.setAgenciesUid(agenciesUid);
			mfBusAgenciesConfig.setAgenciesDetailUid(agenciesDetailUid);
			List<MfBusAgenciesConfig> list = mfBusAgenciesConfigFeign.getMfBusAgenciesConfigList(mfBusAgenciesConfig);
			if(list != null && list.size() > 0){
				dataMap.put("mfBusAgenciesConfig", (MfBusAgenciesConfig)list.get(0));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}

	/**
	 * 获取授信产品支持的合作银行
	 * @param kindNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAgenciesListAjax")
	@ResponseBody
	public Map<String,Object> getAgenciesListAjax(String ajaxData,String kindNo,int pageNo,String areaNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		try{
			MfBusAgencies mfBusAgencies = new MfBusAgencies();
			mfBusAgencies.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAgencies.setSupportProducts(kindNo);
			mfBusAgencies.setParentAgenciesId(areaNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusAgencies", mfBusAgencies));
			ipage =mfBusAgenciesFeign.findAgenciesByKindNoPage(ipage);
			dataMap.put("ipage",ipage);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}
	/**
	 * 获取授信产品支持的合作银行
	 * @param kindNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAgenciesListByKindNoAjax")
	@ResponseBody
	public Map<String,Object> getAgenciesListByKindNoAjax(String ajaxData,String kindNo,int pageNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		try{
			MfBusAgencies mfBusAgencies = new MfBusAgencies();
			mfBusAgencies.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAgencies.setSupportProducts(kindNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusAgencies", mfBusAgencies));
			ipage =mfBusAgenciesFeign.getAgenciesListByKindNo(ipage);
			dataMap.put("ipage",ipage);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}
	@RequestMapping(value = "/getPopupData")
	@ResponseBody
	public Map<String,Object> getPopupData() throws Exception {
		ActionContext.initialize(request, response);

		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		List<MfBusAgencies> mfBusAgenciesList = mfBusAgenciesFeign.findAll(mfBusAgencies);

		JSONArray array = new JSONArray();
		for (MfBusAgencies ba : mfBusAgenciesList) {
			JSONObject obj = new JSONObject();
			obj.put("id", ba.getAgenciesUid());
			obj.put("name", ba.getAgenciesName());
			array.add(obj);
		}

		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("items", array);

		return dataMap;
	}
	/**
	 * 调视角
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAgenciesView")
	public String  getAgenciesView(Model model,String agenciesId,String busEntrance,String cusNo) throws Exception{
		ActionContext.initialize(request, response);
		try {
			Map<String,Object> dataMap=new HashMap<String,Object>();
			//String cusEntrance=BizPubParm.CUS_TYPE_SET_ZIJIN;//入口编号使用了客户设置的类型
			String baseType=BizPubParm.CUS_BASE_TYPE_ZIJIN;
			Map<String,String> parmMap=new HashMap<String,String>();
			MfBusAgencies mfBusAgencies=new MfBusAgencies();
            mfBusAgencies.setAgenciesId(agenciesId);
            if(StringUtil.isEmpty(agenciesId) && StringUtil.isNotEmpty(cusNo)){
                mfBusAgencies.setAgenciesUid(cusNo);
            }
            mfBusAgencies=mfBusAgenciesFeign.getById(mfBusAgencies);
            if(StringUtil.isEmpty(agenciesId)){
                agenciesId = mfBusAgencies.getAgenciesId();
            }
            parmMap.put("agenciesId", agenciesId);
            parmMap.put("baseType", baseType);
            parmMap.put("cusType", mfBusAgencies.getCusType());
			parmMap.put("agenciesUid", mfBusAgencies.getAgenciesUid());//参与其他关联都用的trenchUid
			parmMap.put("operable", "operable");//底部显示待完善信息块
			//parmMap.put("busClass", mfBusAgencies.getCusType().subSequence(0, 1).toString());
			String generalClass = "cus";
			parmMap.put("docParm", "cusNo="+mfBusAgencies.getAgenciesUid()+"&relNo="+mfBusAgencies.getAgenciesUid()+"&scNo="+BizPubParm.SCENCE_TYPE_DOC_CUS);
			Map<String,Object> cusViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, parmMap);
			dataMap.put("cusNo", mfBusAgencies.getAgenciesUid());
			dataMap.put("baseType", baseType);
			dataMap.putAll(cusViewMap);
			dataMap.put("agenciesId", agenciesId);
			model.addAttribute("dataMap", dataMap);
			
			// 查询额度追加历史
			MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
			mfTrenchCreditAmtModifyHis.setTrenchUid(mfBusAgencies.getAgenciesUid());
			List<MfTrenchCreditAmtModifyHis> mfTrenchCreditAmtModifyHisList = mfTrenchCreditAmtModifyHisFeign.getByTrenchUid(mfTrenchCreditAmtModifyHis);
			JsonTableUtil jtu = new JsonTableUtil();
			String  addCreditHisListHtml = jtu.getJsonStr("tablemfTrenchCreditAmtModifyHis","thirdTableTag",mfTrenchCreditAmtModifyHisList, null,true);
			model.addAttribute("addCreditHisListHtml", addCreditHisListHtml);
			model.addAttribute("trenchListSize", mfTrenchCreditAmtModifyHisList.size());
			
			jtu = new JsonTableUtil();
			//资金机构配置明细
			MfBusAgenciesConfig mfBusAgenciesConfig = new MfBusAgenciesConfig();
			mfBusAgenciesConfig.setAgenciesUid(mfBusAgencies.getAgenciesUid());
			List<MfBusAgenciesConfig> mfBusAgenciesConfigList = mfBusAgenciesConfigFeign.getMfBusAgenciesConfigList(mfBusAgenciesConfig);
			jtu = new JsonTableUtil();
			String addAgenciesConfigListHtml = jtu.getJsonStr("tablemfBusAgenciesConfig","thirdTableTag",mfBusAgenciesConfigList, null,true);
			model.addAttribute("addAgenciesConfigListHtml", addAgenciesConfigListHtml);
			model.addAttribute("agenciesConfigListSize", mfBusAgenciesConfigList.size());
			
			model.addAttribute("opNoType", BizPubParm.OP_NO_TYPE3);
			model.addAttribute("query", "");
		} catch (Exception e) {
			/*logger.error("获取资金机构详情视角失败",e);*/
			throw e;
		}
		return "component/cus/commonview/MfCusCustomer_ComView";
	}
	/**
	 * 将实体对象转换为主视图页面需要的参数对象
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/transViewBean")
	@ResponseBody
	public Map<String,String> transViewBean(String cusNo) throws Exception{
		MfCusCustomer mfCusCustomer=new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer=mfCusCustomerFeign.getById(mfCusCustomer);
		Map<String,String> cusInfoMap=new HashMap<String,String>();
		if(mfCusCustomer!=null){
			//名称
			cusInfoMap.put("cusName", mfCusCustomer.getCusName());
			//基本类型
			cusInfoMap.put("cusBaseType", mfCusCustomer.getCusType().substring(0,1));
			cusInfoMap.put("cusType", mfCusCustomer.getCusType());
			cusInfoMap.put("uId", mfCusCustomer.getCusNo());//业务编号
			//获取客户类型汉字
			MfCusType mfCusType=new MfCusType();
			mfCusType.setTypeNo(mfCusCustomer.getCusType());
			mfCusType=mfCusTypeFeign.getById(mfCusType);
			cusInfoMap.put("cusNameRate", mfCusType!=null?mfCusType.getTypeName():"未知");
			//对接人联系方式
			cusInfoMap.put("contactsTel", mfCusCustomer.getContactsTel());
			//对接人姓名
			cusInfoMap.put("contactsName", mfCusCustomer.getContactsTel());
			//资料完整度
			cusInfoMap.put("infIntegrity", mfCusCustomer.getInfIntegrity());
			
		}
		return cusInfoMap;
	}
	/**
	 * 资金机构的历史业务统计数据
	 * @return
	 */
	@RequestMapping(value = "/getAgenciesBusHisAjax")
	@ResponseBody
	public Map<String,Object> getAgenciesBusHisAjax(String agenciesUid){
		Logger logger = LoggerFactory.getLogger(MfBusAgenciesController.class);
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		try {
			Map<String,String> resulMap=mfBusAgenciesFeign.getTrenchBusHisAjax(agenciesUid);
			dataMap.put("flag", "success");
			dataMap.putAll(resulMap);
		} catch (Exception e) {
			logger.error("获取资金机构历史数据失败，",e);
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 获得渠道基本信息
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018-3-6 下午6:28:05
	 */
	@RequestMapping("/getAgenciesBaseHtmlAjax")
	@ResponseBody
	public Map<String, Object> getAgenciesBaseHtmlAjax(String agenciesUid) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		request.setAttribute("ifBizManger", "3");
		String formId = "";
		try{
			mfBusAgencies.setAgenciesUid(agenciesUid);
			mfBusAgencies=mfBusAgenciesFeign.getById(mfBusAgencies);
			MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfBusAgencies.getCusType(), "MfBusAgenciesAction");
			if (mfCusFormConfig == null) {

			} else {
				formId = mfCusFormConfig.getShowModelDef();
			}
			FormData formToAjaxByOne = formService.getFormData(formId);
			if (formToAjaxByOne.getFormId() == null) {
//				logger.error("渠道商客户类型为" + mfBusAgencies.getCusType() + "的MfBusAgenciesAction表单form" + formId + ".xml文件不存在");
			}
			getFormValue(formToAjaxByOne);
			getObjValue(formToAjaxByOne, mfBusAgencies);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formToAjaxByOne, "propertySeeTag", "");
			dataMap.put("htmlStr", htmlStr);
			// 查询额度追加历史
			MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
			mfTrenchCreditAmtModifyHis.setTrenchUid(mfBusAgencies.getAgenciesUid());
			List<MfTrenchCreditAmtModifyHis> mfTrenchCreditAmtModifyHisList = mfTrenchCreditAmtModifyHisFeign.getByTrenchUid(mfTrenchCreditAmtModifyHis);
			JsonTableUtil jtu = new JsonTableUtil();
			String  addCreditHisListHtml = jtu.getJsonStr("tablemfTrenchCreditAmtModifyHis","thirdTableTag",mfTrenchCreditAmtModifyHisList, null,true);
			dataMap.put("addCreditHisListHtml", addCreditHisListHtml);
			dataMap.put("flag", "success");
			dataMap.put("msg",  MessageEnum.SUCCEED_OPERATION.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",  MessageEnum.FAILED_OPERATION.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 方法描述： 根据产品编号
	 * @param kindNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月2日 上午10:37:02
	 */
	@RequestMapping("/getByKindNoAjax")
	@ResponseBody
	public Map<String, Object> getByKindNoAjax(String kindNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		try{
			MfBusAgencies mfBusAgencies = new MfBusAgencies();
			mfBusAgencies.setSupportProducts(kindNo);
			dataMap = mfBusAgenciesFeign.getByKindNo(mfBusAgencies);
			dataMap.put("flag", "success");
			dataMap.put("msg",  MessageEnum.SUCCEED_QUERY.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",  MessageEnum.ERROR_SELECT.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}	
	
	/**
	 * 
	 * <p>Title: getMfBusPageListAjax</p>  
	 * <p>Description: </p>  
	 * @param pageNo
	 * @param mfBusAgenciesConfig
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年8月31日 下午8:18:00
	 */
	@RequestMapping(value = "/getMfBusPageListAjax")
	@ResponseBody
	public Map<String,Object> getMfBusPageListAjax(int pageNo,MfBusAgenciesConfig mfBusAgenciesConfig,String ajaxData)throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				mfBusAgenciesConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			ipage.setParams(this.setIpageParams("mfBusAgenciesConfig", mfBusAgenciesConfig));
			ipage = mfBusAgenciesConfigFeign.findByPage(ipage);
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
	 * 方法描述： 打开资金机构分润页面
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月4日 上午11:07:22
	 */
	@RequestMapping(value = "/agenciesShareProfit")
	public String agenciesShareProfit(Model model,String cusNo,String calcBase) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("calcBase", calcBase);
		model.addAttribute("cusNo", cusNo);
		return "/component/cus/AgenciesShareProfit";
	}
	/**
	 * 方法描述： 资金机构分润按照客户数
	 * @param model
	 * @param cusNo
	 * @param calcBase
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月7日 上午10:28:55
	 */
	@RequestMapping(value = "/agenciesShareProfitByCus")
	public String agenciesShareProfitByCus(Model model,String cusNo,String calcBase) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("calcBase", calcBase);
		model.addAttribute("cusNo", cusNo);
		return "/component/cus/AgenciesShareProfit";
	}
}
