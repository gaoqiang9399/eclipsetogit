package  app.component.cus.controller;
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
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.collateral.entity.CertiInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusGpsReg;
import app.component.cus.entity.MfCusApplyPersonSurvey;
import app.component.cus.feign.MfBusGpsRegFeign;
import app.component.cus.feign.MfCusApplyPersonSurveyFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfBusGpsRegAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 11 14:05:05 CST 2017
 **/
@Controller
@RequestMapping("/mfBusGpsReg")
public class MfBusGpsRegController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterface;
	@Autowired
	private MfBusGpsRegFeign mfBusGpsRegFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusApplyPersonSurveyFeign mfCusApplyPersonSurveyFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/cus/MfBusGpsReg_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
		try {
			mfBusGpsReg.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusGpsReg.setCriteriaList(mfBusGpsReg, ajaxData);//我的筛选
			//mfBusGpsReg.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfBusGpsReg,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsReg));
			ipage = mfBusGpsRegFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		String nodeNo="";
		String query="";
		try{
			dataMap = getMapByJson(ajaxData);
			String formId = (String)dataMap.get("formId");
			String appId= (String)dataMap.get("appId");
			if(StringUtil.isEmpty(formId)){
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(appId);
				mfBusApply = mfBusApplyFeign.getById(mfBusApply);
				TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
				nodeNo = task.getActivityName();// 功能节点编号

				formId = prdctInterface.getFormId(mfBusApply.getKindNo(), WKF_NODE.gps_reg, null, null, User.getRegNo(request));
			}
			if(StringUtil.isEmpty(formId)){
//				logger.error("MfBusGpsRegAction的formId找不到");
			}
			FormData formcusBusGpsRegDetail = formService.getFormData(formId);
			getFormValue(formcusBusGpsRegDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusBusGpsRegDetail)){
				MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
				setObjValue(formcusBusGpsRegDetail, mfBusGpsReg);
				MfBusApply mfBusApply  = appInterfaceFeign.getMfBusApplyByAppId(appId);
//				formId = mfCusFormConfigFeign.getByCusType(mfBusApply.getKindNo(), "MfBusGpsRegAction").getShowModelDef();
				mfBusGpsRegFeign.insert(mfBusGpsReg);
				//回调展示
				mfBusGpsReg = new MfBusGpsReg(); 
				mfBusGpsReg.setAppId(appId);
				mfBusGpsReg = mfBusGpsRegFeign.getById(mfBusGpsReg);
				

				formcusBusGpsRegDetail = formService.getFormData("cusBusGpsRegDetail");
				getFormValue(formcusBusGpsRegDetail);
				getObjValue(formcusBusGpsRegDetail, mfBusGpsReg);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusBusGpsRegDetail, "propertySeeTag", query);
				dataMap.put("gpsDetailInfo", htmlStr);
				dataMap.put("nodeNo", nodeNo);
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
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		FormData formcusBusGpsRegDetail = formService.getFormData("cusBusGpsRegDetail");
		getFormValue(formcusBusGpsRegDetail, getMapByJson(ajaxData));
		MfBusGpsReg mfBusGpsRegJsp = new MfBusGpsReg();
		setObjValue(formcusBusGpsRegDetail, mfBusGpsRegJsp);
		MfBusGpsReg mfBusGpsReg = mfBusGpsRegFeign.getById(mfBusGpsRegJsp);
		if(mfBusGpsReg!=null){
			try{
				mfBusGpsReg = (MfBusGpsReg)EntityUtil.reflectionSetVal(mfBusGpsReg, mfBusGpsRegJsp, getMapByJson(ajaxData));
				mfBusGpsRegFeign.update(mfBusGpsReg);
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
	 * ajax 资产管理和车管验车下的单子段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String,Object> updateByOneAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		FormData formcusBusGpsRegDetail = formService.getFormData("cusBusGpsRegBase");
		getFormValue(formcusBusGpsRegDetail, getMapByJson(ajaxData));
		MfBusGpsReg mfBusGpsRegJsp = new MfBusGpsReg();
		setObjValue(formcusBusGpsRegDetail, mfBusGpsRegJsp);
		MfBusGpsReg mfBusGpsReg = mfBusGpsRegFeign.getById(mfBusGpsRegJsp);
		if(mfBusGpsReg!=null){
			try{
				mfBusGpsReg = (MfBusGpsReg)EntityUtil.reflectionSetVal(mfBusGpsReg, mfBusGpsRegJsp, getMapByJson(ajaxData));
				mfBusGpsRegFeign.update(mfBusGpsReg);
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
		MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
		try{
			FormData formcusBusGpsRegDetail = formService.getFormData("cusBusGpsRegDetail");
			getFormValue(formcusBusGpsRegDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusBusGpsRegDetail)){
				mfBusGpsReg = new MfBusGpsReg();
				setObjValue(formcusBusGpsRegDetail, mfBusGpsReg);
				mfBusGpsRegFeign.update(mfBusGpsReg);
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
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String appId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
//		if(StringUtil.isEmpty(formId)){
//			MfBusApply mfBusApply  = appInterface.getMfBusApplyByAppId(appId);
//			formId = mfCusFormConfigFeign.getByCusType(mfBusApply.getKindNo(), "MfBusGpsRegAction").getShowModelDef();
//		}

		FormData formcusBusGpsRegDetail = formService.getFormData("cusBusGpsRegDetail");
		MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
		mfBusGpsReg.setAppId(appId);
		mfBusGpsReg = mfBusGpsRegFeign.getById(mfBusGpsReg);
		getObjValue(formcusBusGpsRegDetail, mfBusGpsReg);
		if(mfBusGpsReg!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formcusBusGpsRegDetail, "propertySeeTag", "");
		dataMap.put("htmlStr", htmlStr);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String gpsId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
		mfBusGpsReg.setGpsId(gpsId);
		try {
			mfBusGpsRegFeign.delete(mfBusGpsReg);
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
	 * 
	 * 方法描述： GPS登记展示是列表信息
	 * @param model
	 * @param appId
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2018年6月4日 下午5:36:42
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model,String appId) throws Exception{
		ActionContext.initialize(request,response);
		
		MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
		mfBusGpsReg.setAppId(appId);
		Ipage ipage = this.getIpage();
		//自定义查询Bo方法
		ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsReg));
		ipage = mfBusGpsRegFeign.findByPage(ipage);
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr("tablecargps0001","tableTag",(List)ipage.getResult(), ipage,true);
		if(ipage.getPageCounts() < 1){
			tableHtml = "<font color='#E17E7F'>请先进行GPS信息登记</font></form>";
		}
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
		String wkfAppId = mfBusApply.getWkfAppId();
		String taskId = task.getId();
		String nodeNo = task.getActivityName();// 功能节点编号
		model.addAttribute("appId", appId);
		model.addAttribute("wkfAppId", wkfAppId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("taskId", taskId);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("query", "");
		model.addAttribute("tableHtml", tableHtml);
		return "/component/cus/MfBusGpsReg_InsertList";
	}
	
	/**
	 * 描述：获取gps列表信息
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/getGpsInfoListAjax")
	@ResponseBody
	public Map<String, String> getGpsInfoListAjax(String appId){
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			ActionContext.initialize(request,response);
			
			MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
			mfBusGpsReg.setAppId(appId);
			Ipage ipage = this.getIpage();
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsReg));
			ipage = mfBusGpsRegFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr("tablecargps0001","tableTag",(List)ipage.getResult(), ipage,true);
			dataMap.put("flag", "success");
			dataMap.put("query", "query");
			dataMap.put("tableHtml", tableHtml);
			if(ipage.getPageCounts() > 0){
				dataMap.put("complete", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/validateGpsInfoAjax")
	@ResponseBody
	public Map<String, String> validateGpsInfoAjax(String appId){
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
			mfBusGpsReg.setAppId(appId);
			int count = mfBusGpsRegFeign.validateGpsInfo(mfBusGpsReg);
			if(count < 1){
				dataMap.put("msg", MessageEnum.FIRST_OPERATION.getMessage("GPS信息登记"));
				dataMap.put("tipMsg", "<font color='#E17E7F'>请先进行GPS信息登记</font></form>");
				dataMap.put("complete", "0");
			}else {
				dataMap.put("complete", "1");
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/insertGpsAjax")
	@ResponseBody
	public Map<String,Object> insertGpsAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			dataMap = getMapByJson(ajaxData);
			String formId = (String)dataMap.get("formId");
			String appId= (String)dataMap.get("appId");
			if(StringUtil.isEmpty(formId)){
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(appId);
				mfBusApply = mfBusApplyFeign.getById(mfBusApply);
				formId = prdctInterface.getFormId(mfBusApply.getKindNo(), WKF_NODE.gps_reg, null, null, User.getRegNo(request));
			}
			if(StringUtil.isEmpty(formId)){
//				logger.error("MfBusGpsRegAction的formId找不到");
			}
			FormData formcusBusGpsRegDetail = formService.getFormData(formId);
			getFormValue(formcusBusGpsRegDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusBusGpsRegDetail)){
				MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
				setObjValue(formcusBusGpsRegDetail, mfBusGpsReg);
                mfBusGpsRegFeign.insertNoSubmit(mfBusGpsReg);
                //回调展示
                String relNo= (String)dataMap.get("relNo");
                if (StringUtil.isNotBlank(relNo)) {
				    PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				    pledgeBaseInfo.setPledgeNo(mfBusGpsReg.getExt1());
                    pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
                    if(null !=pledgeBaseInfo){
						pledgeBaseInfo.setRefFlag("1");
						pledgeBaseInfo.setKeepStatus("1");
						pledgeBaseInfo.setCusName(mfBusGpsReg.getCusName());
						pledgeBaseInfoFeign.update(pledgeBaseInfo);
					}
					MfBusGpsReg mfBusGpsRegNew = new MfBusGpsReg();
					mfBusGpsRegNew.setRelNo(relNo);
					Ipage ipage = this.getIpage();
					ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsRegNew));
					ipage = mfBusGpsRegFeign.findByPage(ipage);
					JsonTableUtil jtu = new JsonTableUtil();
					String  tableHtmlCar = jtu.getJsonStr("tablecarGpsDetail","tableTag",(List<CertiInfo>)ipage.getResult(), null,true);
					dataMap.put("htmlStr", tableHtmlCar);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					return dataMap;
				}
				MfBusGpsReg mfBusGpsRegShow = new MfBusGpsReg();
				mfBusGpsRegShow.setAppId(appId);
				Ipage ipage = this.getIpage();
				//自定义查询Bo方法
				ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsRegShow));
				ipage = mfBusGpsRegFeign.findByPage(ipage);
				JsonTableUtil jtu = new JsonTableUtil();
				String  tableHtml = jtu.getJsonStr("tablecargps0001","tableTag",(List)ipage.getResult(), ipage,true);
				@SuppressWarnings("unchecked")
				String  tableHtmlCar = jtu.getJsonStr("tablecarGpsDetail","tableTag",(List<CertiInfo>)ipage.getResult(), null,true);
				dataMap.put("gpsListInfo", tableHtml);
				dataMap.put("htmlStr", tableHtmlCar);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	// gps列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String gpsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		request.setAttribute("ifBizManger", "3");
		FormData formcusBusGpsRegDetail = formService.getFormData("cusBusGpsRegDetail");
		getFormValue(formcusBusGpsRegDetail);
		MfBusGpsReg mfBusGpsRegShow = new MfBusGpsReg();
		mfBusGpsRegShow.setGpsId(gpsId);
		mfBusGpsRegShow = mfBusGpsRegFeign.getById(mfBusGpsRegShow);
		getObjValue(formcusBusGpsRegDetail, mfBusGpsRegShow);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStrGps = jsonFormUtil.getJsonStr(formcusBusGpsRegDetail, "propertySeeTag", "");
		if (mfBusGpsRegShow != null) {
			dataMap.put("formHtml", htmlStrGps);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	
	
	// gps列表展示详情，单字段编辑
	@RequestMapping(value = "/listCarShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listCarShowDetailAjax(String gpsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		request.setAttribute("ifBizManger", "3");
		FormData formcusBusGpsRegDetail = formService.getFormData("cusBusGpsRegBase");
		getFormValue(formcusBusGpsRegDetail);
		MfBusGpsReg mfBusGpsRegShow = new MfBusGpsReg();
		mfBusGpsRegShow.setGpsId(gpsId);
		mfBusGpsRegShow = mfBusGpsRegFeign.getById(mfBusGpsRegShow);
		getObjValue(formcusBusGpsRegDetail, mfBusGpsRegShow);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStrGps = jsonFormUtil.getJsonStr(formcusBusGpsRegDetail, "propertySeeTag", "");
		if (mfBusGpsRegShow != null) {
			dataMap.put("formHtml", htmlStrGps);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	
	
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputForList")
	public String inputForList(Model model,String appId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
		String nodeNo = task.getActivityName();// 功能节点编号
		String wkfAppId = mfBusApply.getWkfAppId();
		String formId = prdctInterface.getFormId(mfBusApply.getKindNo(), WKF_NODE.gps_reg, null, null, User.getRegNo(request));
		FormData formcusBusGpsRegDetail = formService.getFormData(formId);
		getFormValue(formcusBusGpsRegDetail);
		MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
		MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
		mfBusGpsReg.setCusNo(mfBusApply.getCusNo());
		mfBusGpsReg.setCusName(mfBusApply.getCusName());
		if(mfBusPact!=null){
			mfBusGpsReg.setPactAmt(mfBusPact.getPactAmt());
		}else{
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			mfBusGpsReg.setPactAmt(mfBusApply.getAppAmt());
		}
		mfBusGpsReg.setAppId(appId);
		mfBusGpsReg.setOpName(mfBusApply.getCusMngName());
		mfBusGpsReg.setPledgeMethod(mfBusApply.getVouType());
		
		// 施工区域
		MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
		mfCusApplyPersonSurvey.setRelNo(appId);
		mfCusApplyPersonSurvey =  mfCusApplyPersonSurveyFeign.getById(mfCusApplyPersonSurvey);
		if(mfCusApplyPersonSurvey!=null){
			mfBusGpsReg.setWorkArea(mfCusApplyPersonSurvey.getEngineeringArea());
		}
		getObjValue(formcusBusGpsRegDetail, mfBusGpsReg);
		model.addAttribute("formcusBusGpsRegDetail", formcusBusGpsRegDetail);
		model.addAttribute("query", "");
		model.addAttribute("nodeNo", nodeNo);
		return "/component/cus/MfBusGpsReg_InsertTemp";
	}
	
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String appId,String collateralNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		if (mfBusApply == null) {
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(collateralNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			String classId = pledgeBaseInfo.getClassId();
            String pledgeMethod = pledgeBaseInfo.getPledgeMethod();
            MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,"MfBusGpsRegAction", "");
			String formId = null;
			if (mfCollateralFormConfig == null) {

			} else {
				formId = mfCollateralFormConfig.getAddModelDef();
			}
			FormData formcusBusGpsReg = formService.getFormData(formId);
			MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
			mfBusGpsReg.setRelNo(collateralNo);
			getObjValue(formcusBusGpsReg, mfBusGpsReg);
			model.addAttribute("formcusBusGpsRegDetail", formcusBusGpsReg);
			model.addAttribute("pledgeMethod", pledgeMethod);
			model.addAttribute("query", "");
			return "/component/cus/MfBusGpsReg_CarInsert";
		}
		TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
		String nodeNo = task.getActivityName();// 功能节点编号
		String wkfAppId = mfBusApply.getWkfAppId();
		String formId = prdctInterface.getFormId(mfBusApply.getKindNo(), WKF_NODE.gps_reg, null, null, User.getRegNo(request));
		FormData formcusBusGpsRegDetail = formService.getFormData(formId);
		getFormValue(formcusBusGpsRegDetail);
		MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
		MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
		mfBusGpsReg.setCusNo(mfBusApply.getCusNo());
		mfBusGpsReg.setCusName(mfBusApply.getCusName());
		if(mfBusPact!=null){
			mfBusGpsReg.setPactAmt(mfBusPact.getPactAmt());
		}else{
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			mfBusGpsReg.setPactAmt(mfBusApply.getAppAmt());
		}
		mfBusGpsReg.setAppId(appId);
		mfBusGpsReg.setOpName(User.getRegName(request));

		// 施工区域
		MfCusApplyPersonSurvey mfCusApplyPersonSurvey = new MfCusApplyPersonSurvey();
		mfCusApplyPersonSurvey.setRelNo(appId);
		mfCusApplyPersonSurvey =  mfCusApplyPersonSurveyFeign.getById(mfCusApplyPersonSurvey);
		if(mfCusApplyPersonSurvey!=null){
			mfBusGpsReg.setWorkArea(mfCusApplyPersonSurvey.getEngineeringArea());
		}
		getObjValue(formcusBusGpsRegDetail, mfBusGpsReg);
		model.addAttribute("formcusBusGpsRegDetail", formcusBusGpsRegDetail);
		model.addAttribute("query", "");
		model.addAttribute("nodeNo", nodeNo);
		return "/component/cus/MfBusGpsReg_Insert";
	}
	
	/**
	 * 车管验车新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputCar")
	public String inputCar(Model model,String appId,String collateralNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,"MfBusGpsRegAction", "");

		String formId = null;
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		FormData formcusBusGpsRegDetail = formService.getFormData(formId);
		getFormValue(formcusBusGpsRegDetail);
		MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
		MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
		mfBusGpsReg.setCusNo(mfBusApply.getCusNo());
		mfBusGpsReg.setCusName(mfBusApply.getCusName());
		if(mfBusPact!=null){
			mfBusGpsReg.setPactAmt(mfBusPact.getPactAmt());
		}else{
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			mfBusGpsReg.setPactAmt(mfBusApply.getAppAmt());
		}
		mfBusGpsReg.setAppId(appId);
		mfBusGpsReg.setOpName(User.getRegName(request));
		mfBusGpsReg.setRelNo(collateralNo);
		getObjValue(formcusBusGpsRegDetail, mfBusGpsReg);
		model.addAttribute("formcusBusGpsRegDetail", formcusBusGpsRegDetail);
		model.addAttribute("query", "");
		model.addAttribute("pledgeMethod", mfBusApply.getVouType());
		model.addAttribute("cusNo", mfBusApply.getCusNo());
		model.addAttribute("cusName", mfBusApply.getCusName());
		return "/component/cus/MfBusGpsReg_CarInsert";
	}

	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String gpsId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcusBusGpsRegBase = formService.getFormData("cusBusGpsRegBase");
		getFormValue(formcusBusGpsRegBase);
		MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
		mfBusGpsReg.setGpsId(gpsId);
		mfBusGpsReg = mfBusGpsRegFeign.getById(mfBusGpsReg);
		getObjValue(formcusBusGpsRegBase, mfBusGpsReg);
		model.addAttribute("formcusBusGpsRegBase", formcusBusGpsRegBase);
		model.addAttribute("query", "");
		return "/component/cus/MfBusGpsReg_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcusBusGpsRegDetail = formService.getFormData("cusBusGpsRegDetail");
		 getFormValue(formcusBusGpsRegDetail);
		 boolean validateFlag = this.validateFormData(formcusBusGpsRegDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcusBusGpsRegDetail = formService.getFormData("cusBusGpsRegDetail");
		 getFormValue(formcusBusGpsRegDetail);
		 boolean validateFlag = this.validateFormData(formcusBusGpsRegDetail);
	}
}
