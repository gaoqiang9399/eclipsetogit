package  app.component.cus.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.YmlConfig;
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

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusSurveySocialCredit;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusSurveySocialCreditFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusSurveySocialCreditAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Sep 16 12:09:44 CST 2017
 **/
@Controller
@RequestMapping("/mfCusSurveySocialCredit")
public class MfCusSurveySocialCreditController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusSurveySocialCreditBo
	@Autowired
	private MfCusSurveySocialCreditFeign mfCusSurveySocialCreditFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private YmlConfig ymlConfig;
	//全局变量
	private String query = "";//初始化query为空
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusSurveySocialCredit_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusSurveySocialCredit mfCusSurveySocialCredit = new MfCusSurveySocialCredit();
		try {
			mfCusSurveySocialCredit.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusSurveySocialCredit.setCriteriaList(mfCusSurveySocialCredit, ajaxData);//我的筛选
			//mfCusSurveySocialCredit.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusSurveySocialCredit,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusSurveySocialCredit", mfCusSurveySocialCredit));
			ipage = mfCusSurveySocialCreditFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
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
	public Map<String, Object> insertAjax(String ajaxData,String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formsurvey_social_credit0002 =null;
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusSurveySocialCreditAction").getAddModel();
			}
			if(StringUtil.isEmpty(formId)){
				//			logger.error("MfCusSurveySocialCreditAction的formId找不到");
			}
		 	formsurvey_social_credit0002 = formService.getFormData(formId);
			getFormValue(formsurvey_social_credit0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsurvey_social_credit0002)){
		MfCusSurveySocialCredit mfCusSurveySocialCredit = new MfCusSurveySocialCredit();
				setObjValue(formsurvey_social_credit0002, mfCusSurveySocialCredit);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusSurveySocialCredit.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusSurveySocialCredit.setCusName(mfCusCustomer.getCusName());
				mfCusSurveySocialCreditFeign.insert(mfCusSurveySocialCredit);
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusSurveySocialCredit.getCusNo(), mfCusSurveySocialCredit.getRelNo());//更新资料完整度
				String cusNo = mfCusSurveySocialCredit.getCusNo();
				String relNo = mfCusSurveySocialCredit.getRelNo();
				MfCusSurveySocialCredit mfCusSurveySocialCreditTmp = new MfCusSurveySocialCredit();
				mfCusSurveySocialCreditTmp.setCusNo(cusNo);
				mfCusSurveySocialCreditTmp.setRelNo(relNo);
				mfCusSurveySocialCredit = mfCusSurveySocialCreditFeign.getById(mfCusSurveySocialCreditTmp);
				request.setAttribute("ifBizManger", "3");
				
				String detailFormId =mfCusFormConfigFeign.getByCusType("base", "MfCusSurveySocialCreditAction").getShowModel(); 
				if(mfCusSurveySocialCredit.getCusNo().equals(mfCusSurveySocialCredit.getRelNo())){
					detailFormId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusSurveySocialCreditAction").getShowModelDef();
				}else{
					MfBusApply mfBusApply  = appInterfaceFeign.getMfBusApplyByAppId(mfCusSurveySocialCredit.getRelNo());
					detailFormId = mfCusFormConfigFeign.getByCusType(mfBusApply.getKindNo(), "MfCusSurveySocialCreditAction").getShowModelDef();
				}
				if(StringUtil.isEmpty(detailFormId)){
					//			logger.error("MfCusSurveySocialCreditAction的detailFormId找不到");
				}
		 formsurvey_social_credit0002 = formService.getFormData(detailFormId);
				getFormValue(formsurvey_social_credit0002);
				getObjValue(formsurvey_social_credit0002, mfCusSurveySocialCredit);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				//String htmlStr = jsonFormUtil.getJsonStr(formsurvey_social_credit0002, "propertySeeTag", query);
				String htmlStr = jsonFormUtil.getJsonStr(formsurvey_social_credit0002, "propertySeeTag", "");
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");
				dataMap.put("infIntegrity",infIntegrity);
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
			throw new Exception(e.getMessage());
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
	public Map<String, Object> updateAjaxByOne(String ajaxData,String formId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formsurvey_social_credit0002 = formService.getFormData(formId);
		getFormValue(formsurvey_social_credit0002, getMapByJson(ajaxData));
		MfCusSurveySocialCredit mfCusSurveySocialCreditJsp = new MfCusSurveySocialCredit();
		setObjValue(formsurvey_social_credit0002, mfCusSurveySocialCreditJsp);
		MfCusSurveySocialCredit mfCusSurveySocialCredit = mfCusSurveySocialCreditFeign.getById(mfCusSurveySocialCreditJsp);
		if(mfCusSurveySocialCredit!=null){
			try{
				mfCusSurveySocialCredit = (MfCusSurveySocialCredit)EntityUtil.reflectionSetVal(mfCusSurveySocialCredit, mfCusSurveySocialCreditJsp, getMapByJson(ajaxData));
				mfCusSurveySocialCreditFeign.update(mfCusSurveySocialCredit);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusSurveySocialCredit mfCusSurveySocialCredit = new MfCusSurveySocialCredit();
		try{
		FormData 	fromsurveySocialCreditBase = formService.getFormData("surveySocialCreditBase");
			getFormValue(fromsurveySocialCreditBase, getMapByJson(ajaxData));
			if(this.validateFormData(fromsurveySocialCreditBase)){
				setObjValue(fromsurveySocialCreditBase, mfCusSurveySocialCredit);
				mfCusSurveySocialCreditFeign.update(mfCusSurveySocialCredit);

				String formId = "";
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusSurveySocialCredit.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusSurveySocialCreditAction");
				if(mfCusFormConfig == null){

				}else{
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData fromsurveySocialCreditDetail = formService.getFormData(formId);
				if(fromsurveySocialCreditDetail.getFormId() == null){
					//log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersBaseInfoAction表单form"+formId+".xml文件不存在");
				}
				getFormValue(fromsurveySocialCreditDetail);
				getObjValue(fromsurveySocialCreditDetail, mfCusCustomer);
				getObjValue(fromsurveySocialCreditDetail, mfCusSurveySocialCredit);
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(fromsurveySocialCreditDetail,"propertySeeTag",query);

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");

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
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formsurvey_social_credit0002 = formService.getFormData("survey_social_credit0002");
		MfCusSurveySocialCredit mfCusSurveySocialCredit = new MfCusSurveySocialCredit();
		mfCusSurveySocialCredit.setSerialNo(serialNo);
		mfCusSurveySocialCredit = mfCusSurveySocialCreditFeign.getById(mfCusSurveySocialCredit);
		getObjValue(formsurvey_social_credit0002, mfCusSurveySocialCredit,formData);
		if(mfCusSurveySocialCredit!=null){
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
	public Map<String, Object> deleteAjax(String serialNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusSurveySocialCredit mfCusSurveySocialCredit = new MfCusSurveySocialCredit();
		mfCusSurveySocialCredit.setSerialNo(serialNo);
		try {
			mfCusSurveySocialCreditFeign.delete(mfCusSurveySocialCredit);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo,String formId) throws Exception{

		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		//初始值：formId为null，formcusassets00003为null
		MfCusSurveySocialCredit mfCusSurveySocialCredit = new MfCusSurveySocialCredit();
		mfCusSurveySocialCredit.setCusNo(cusNo);
		mfCusSurveySocialCredit.setRelNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusSurveySocialCredit.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		FormData 	formsurvey_social_credit0002=null;
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusSurveySocialCreditAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusSurveySocialCreditAction表单信息没有查询到");
		} else {
		 	formsurvey_social_credit0002 = formService.getFormData(formId);
			if (formsurvey_social_credit0002.getFormId() == null) {
				//			logger.error("客户类型为" + mfCusCustomer.getCusType()
//						+ "的MfCusSurveySocialCreditAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formsurvey_social_credit0002);
				mfCusSurveySocialCredit.setCusName(mfCusCustomer.getCusName());
				/*mfCusSurveySocialCredit.setGambly("0");
				mfCusSurveySocialCredit.setOverdraftAmountExceed("0");
				mfCusSurveySocialCredit.setFabricateFalse("0");
				mfCusSurveySocialCredit.setFrequentlyOverdue("0");
				mfCusSurveySocialCredit.setIllegalMoney("0");
				mfCusSurveySocialCredit.setMafiaBackground("0");
				mfCusSurveySocialCredit.setMajorDiseases("0");
				mfCusSurveySocialCredit.setOperationDown("0");
				mfCusSurveySocialCredit.setOverdueLoan("0");
				mfCusSurveySocialCredit.setSeriousOverdueConditions("0");
				mfCusSurveySocialCredit.setRangAge("0");
				mfCusSurveySocialCredit.setOverYears("0");
				mfCusSurveySocialCredit.setOperationOneYear("0");
				mfCusSurveySocialCredit.setLessTwoYears("0");
				mfCusSurveySocialCredit.setLessThreeYears("0");
				mfCusSurveySocialCredit.setOverFiveTimes("0");
				mfCusSurveySocialCredit.setRefusedLoans("0");
				mfCusSurveySocialCredit.setAbnormalCredit("0");
				mfCusSurveySocialCredit.setBreakPromise("0");
				mfCusSurveySocialCredit.setCivilSuit("0");
				mfCusSurveySocialCredit.setCriminal("0");
				mfCusSurveySocialCredit.setCrownCase("0");
				mfCusSurveySocialCredit.setDebtGuaranteed("0");
				mfCusSurveySocialCredit.setDivorceProceedings("0");
				mfCusSurveySocialCredit.setDrugAbuse("0");
				mfCusSurveySocialCredit.setExecutiveRecord("0");
				mfCusSurveySocialCredit.setSeriousPenalties("0");
				mfCusSurveySocialCredit.setLoanAmountOver("0");*/
				getObjValue(formsurvey_social_credit0002, mfCusSurveySocialCredit);
			}
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("projectName", projectName);
		model.addAttribute("formsurvey_social_credit0002", formsurvey_social_credit0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSurveySocialCredit_Insert";
	}
	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-9-26 下午3:14:38
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo,String relNo,String kindNo) throws Exception{
		
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		//初始值：formId为null，formcusassets00003为null
		MfCusSurveySocialCredit mfCusSurveySocialCredit = new MfCusSurveySocialCredit();
		mfCusSurveySocialCredit.setCusNo(cusNo);
		mfCusSurveySocialCredit.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusSurveySocialCredit.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusSurveySocialCreditAction");
		String formId="";
		FormData 	formsurvey_social_credit0002=null;
		if (mfCusFormConfig == null) {
			
		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			//			logger.error("产品类型为"+kindNo+ "的MfCusSurveySocialCreditAction表单信息没有查询到");
		} else {
		 	formsurvey_social_credit0002 = formService.getFormData(formId);
			if (formsurvey_social_credit0002.getFormId() == null) {
				//			logger.error("产品类型为"+kindNo+ "的MfCusSurveySocialCreditAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formsurvey_social_credit0002);
				mfCusSurveySocialCredit.setCusName(mfCusCustomer.getCusName());
				getObjValue(formsurvey_social_credit0002, mfCusSurveySocialCredit);
			}
		}
		model.addAttribute("formsurvey_social_credit0002", formsurvey_social_credit0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSurveySocialCredit_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String serialNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  fromsurveySocialCreditBase = formService.getFormData("surveySocialCreditBase");
		 getFormValue(fromsurveySocialCreditBase);
		MfCusSurveySocialCredit  mfCusSurveySocialCredit = new MfCusSurveySocialCredit();
		mfCusSurveySocialCredit.setSerialNo(serialNo);
		 mfCusSurveySocialCredit = mfCusSurveySocialCreditFeign.getById(mfCusSurveySocialCredit);
		 getObjValue(fromsurveySocialCreditBase, mfCusSurveySocialCredit);
		model.addAttribute("fromsurveySocialCreditBase", fromsurveySocialCreditBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSurveySocialCredit_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formsurvey_social_credit0002 = formService.getFormData("survey_social_credit0002");
		 getFormValue(formsurvey_social_credit0002);
		 boolean validateFlag = this.validateFormData(formsurvey_social_credit0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formsurvey_social_credit0002 = formService.getFormData("survey_social_credit0002");
		 getFormValue(formsurvey_social_credit0002);
		 boolean validateFlag = this.validateFormData(formsurvey_social_credit0002);
	}
	

	
}
