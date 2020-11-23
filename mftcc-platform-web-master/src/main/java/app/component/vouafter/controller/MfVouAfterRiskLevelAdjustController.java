package app.component.vouafter.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.doc.entity.DocBizManageParam;
import app.component.docinterface.DocInterfaceFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.guarantee.entity.MfGuaranteeRegistration;
import app.component.pact.guarantee.feign.MfGuaranteeRegistrationFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.vouafter.entity.MfVouAfterRiskLevelAdjust;
import app.component.vouafter.entity.MfVouAfterRiskLevelAdjustHis;
import app.component.vouafter.feign.MfVouAfterRiskLevelAdjustFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.DataUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfVouAfterRiskLevelAdjustController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 07 19:03:05 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfVouAfterRiskLevelAdjust")
public class MfVouAfterRiskLevelAdjustController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfVouAfterRiskLevelAdjustFeign mfVouAfterRiskLevelAdjustFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfGuaranteeRegistrationFeign mfGuaranteeRegistrationFeign;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfVouAfterRiskLevelAdjust_List";
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
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		try {
			mfVouAfterRiskLevelAdjust.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfVouAfterRiskLevelAdjust.setCriteriaList(mfVouAfterRiskLevelAdjust, ajaxData);//我的筛选
			mfVouAfterRiskLevelAdjust.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfVouAfterRiskLevelAdjust,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfVouAfterRiskLevelAdjust", mfVouAfterRiskLevelAdjust));
			//自定义查询Feign方法
			ipage = mfVouAfterRiskLevelAdjustFeign.findByPage(ipage);
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
		Map<String, Object> paramMap = getMapByJson(ajaxData);
		try {
			FormData formVouAfterRiskLevelAdjustbase = formService .getFormData((String)paramMap.get("formId"));
			getFormValue(formVouAfterRiskLevelAdjustbase, paramMap);
			if (this.validateFormData(formVouAfterRiskLevelAdjustbase)) {
				MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
				setObjValue(formVouAfterRiskLevelAdjustbase, mfVouAfterRiskLevelAdjust);
				mfVouAfterRiskLevelAdjustFeign.insert(mfVouAfterRiskLevelAdjust);
				mfVouAfterRiskLevelAdjust = mfVouAfterRiskLevelAdjustFeign.submitProcess(mfVouAfterRiskLevelAdjust);
				paramMap.put("userRole", mfVouAfterRiskLevelAdjust.getApproveNodeName());
				paramMap.put("opNo", mfVouAfterRiskLevelAdjust.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase, getMapByJson(ajaxData));
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjustJsp = new MfVouAfterRiskLevelAdjust();
		setObjValue(formvouafterbase, mfVouAfterRiskLevelAdjustJsp);
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = mfVouAfterRiskLevelAdjustFeign.getById(mfVouAfterRiskLevelAdjustJsp);
		if(mfVouAfterRiskLevelAdjust!=null){
			try{
				mfVouAfterRiskLevelAdjust = (MfVouAfterRiskLevelAdjust)EntityUtil.reflectionSetVal(mfVouAfterRiskLevelAdjust, mfVouAfterRiskLevelAdjustJsp, getMapByJson(ajaxData));
				mfVouAfterRiskLevelAdjustFeign.update(mfVouAfterRiskLevelAdjust);
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
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		try{
			FormData formvouafterbase = formService.getFormData("vouafterbase");
			getFormValue(formvouafterbase, getMapByJson(ajaxData));
			if(this.validateFormData(formvouafterbase)){
				mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
				setObjValue(formvouafterbase, mfVouAfterRiskLevelAdjust);
				mfVouAfterRiskLevelAdjustFeign.update(mfVouAfterRiskLevelAdjust);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		mfVouAfterRiskLevelAdjust.setId(id);
		mfVouAfterRiskLevelAdjust = mfVouAfterRiskLevelAdjustFeign.getById(mfVouAfterRiskLevelAdjust);
		getObjValue(formvouafterbase, mfVouAfterRiskLevelAdjust,formData);
		if(mfVouAfterRiskLevelAdjust!=null){
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		mfVouAfterRiskLevelAdjust.setId(id);
		try {
			mfVouAfterRiskLevelAdjustFeign.delete(mfVouAfterRiskLevelAdjust);
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
	public String input(Model model,String pactId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(mfBusPact.getAppId());
        mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setAppId(mfBusPact.getAppId());
		mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
        mfGuaranteeRegistration.setAppId(mfBusPact.getAppId());
        mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		String id = WaterIdUtil.getWaterId();
        mfVouAfterRiskLevelAdjust.setId(id);
        mfVouAfterRiskLevelAdjust.setAppId(mfBusApply.getAppId());
        mfVouAfterRiskLevelAdjust.setAppName(mfBusApply.getAppName());
        mfVouAfterRiskLevelAdjust.setCusName(mfBusApply.getCusName());
        mfVouAfterRiskLevelAdjust.setCusNo(mfBusApply.getCusNo());
        mfVouAfterRiskLevelAdjust.setManageOpNo1(mfBusPact.getManageOpNo1());
        mfVouAfterRiskLevelAdjust.setManageOpName1(mfBusPact.getManageOpName1());
        mfVouAfterRiskLevelAdjust.setKindName(mfBusApply.getKindName());
        mfVouAfterRiskLevelAdjust.setKindNo(mfBusApply.getKindNo());
        mfVouAfterRiskLevelAdjust.setBreedNo(mfBusApply.getBreedNo());
        mfVouAfterRiskLevelAdjust.setBreedName(mfBusApply.getBreedName());
        mfVouAfterRiskLevelAdjust.setTermType(mfBusApply.getTermType());
        mfVouAfterRiskLevelAdjust.setTerm(mfBusApply.getTerm());
        mfVouAfterRiskLevelAdjust.setBeneficiary(mfBusApply.getExt4());
        mfVouAfterRiskLevelAdjust.setProjectName(mfBusApply.getProjectName());
        mfVouAfterRiskLevelAdjust.setOpenDate(mfGuaranteeRegistration.getOpenDate());
        mfVouAfterRiskLevelAdjust.setGuaranteeEndDate(mfGuaranteeRegistration.getGuaranteeEndDate());
        mfVouAfterRiskLevelAdjust.setPactNo(mfBusPact.getPactNo());
        mfVouAfterRiskLevelAdjust.setPactId(mfBusPact.getPactId());
        mfVouAfterRiskLevelAdjust.setPactAmt(mfBusPact.getPactAmt());
        mfVouAfterRiskLevelAdjust.setRiskLevel(mfBusFincApp.getRiskLevel());
		mfVouAfterRiskLevelAdjust.setTermShow(mfBusPact.getTermShow());

		FormData formvouafterriskleveladjustbase = formService.getFormData("vouafterriskleveladjustbase");
        getObjValue(formvouafterriskleveladjustbase, mfVouAfterRiskLevelAdjust);
		model.addAttribute("formvouafterriskleveladjustbase", formvouafterriskleveladjustbase);
		model.addAttribute("id", id);
        model.addAttribute("query", "");

        return "/component/vouafter/MfVouAfterRiskLevelAdjust_Insert";
    }
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initBusInfo")
	public String initBusInfo(Model model) throws Exception{
		ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formvouafterriskleveladjustinit = formService.getFormData("vouafterriskleveladjustinit");
        model.addAttribute("formvouafterriskleveladjustinit", formvouafterriskleveladjustinit);
        model.addAttribute("query", "");
		return "/component/vouafter/MfVouAfterRiskLevelAdjust_InitBusInfo";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id,String from,String nodeNo) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formvouafterriskleveladjustdetail = formService.getFormData("vouafterriskleveladjustdetail");
		getFormValue(formvouafterriskleveladjustdetail);
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		mfVouAfterRiskLevelAdjust.setId(id);
		mfVouAfterRiskLevelAdjust = mfVouAfterRiskLevelAdjustFeign.getById(mfVouAfterRiskLevelAdjust);
		getObjValue(formvouafterriskleveladjustdetail, mfVouAfterRiskLevelAdjust);
		//节点要件关联编号
		model.addAttribute("id", id);
		model.addAttribute("mfVouAfterRiskLevelAdjust", mfVouAfterRiskLevelAdjust);
		model.addAttribute("formvouafterriskleveladjustdetail", formvouafterriskleveladjustdetail);
		model.addAttribute("query", "query");
		String queryFile = "query";
		if("supplement_data".equals(nodeNo)){
			queryFile = "";
		}
		model.addAttribute("queryFile", queryFile);
		model.addAttribute("from", from);
		return "/component/vouafter/MfVouAfterRiskLevelAdjust_Detail";
	}

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getvouAfterRiskLevelAdjustListPage")
	public String getvouAfterRiskLevelAdjustListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfVouAfterRiskLevelAdjust_SelectList";
	}

	/***
	 * 列表数据查询
	 *
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/findVouAfterRiskLevelAdjustByPageAjax")
	@ResponseBody
	public Map<String, Object> findVouAfterRiskLevelAdjustByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
											  String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		try {
			mfVouAfterRiskLevelAdjust.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfVouAfterRiskLevelAdjust.setCriteriaList(mfVouAfterRiskLevelAdjust, ajaxData);// 我的筛选
			mfVouAfterRiskLevelAdjust.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfVouAfterRiskLevelAdjust", mfVouAfterRiskLevelAdjust));
			ipage = mfVouAfterRiskLevelAdjustFeign.findVouAfterRiskLevelAdjustByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
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
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
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
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
	}

	/**
	 *
	 * 方法描述： 打开调薪调岗申请审批页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String id, String hideOpinionType, String taskId,
							   String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		mfVouAfterRiskLevelAdjust.setId(id);
		mfVouAfterRiskLevelAdjust = mfVouAfterRiskLevelAdjustFeign.getById(mfVouAfterRiskLevelAdjust);
		mfVouAfterRiskLevelAdjust.setApprovePartNo(null);
		mfVouAfterRiskLevelAdjust.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);// 当前审批节点task
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		String formId = "VouAfterRiskLevelAdjustApprove";
		if("supplement_data".equals(nodeNo)){
			formId = "VouAfterRiskLevelAdjustsupplementApprove";
		}
		// 初始化基本信息表单、工作经历表单
		FormData formVouAfterRiskLevelAdjustApprove = formService.getFormData(formId);
		// 实体对象放到表单对象中
		getObjValue(formVouAfterRiskLevelAdjustApprove, mfVouAfterRiskLevelAdjust);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		if("agencyManage".equals(nodeNo)){
			hideOpinionType = hideOpinionType + "@4";
		}
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formVouAfterRiskLevelAdjustApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("id", id);
		model.addAttribute("mfVouAfterRiskLevelAdjust", mfVouAfterRiskLevelAdjust);
		model.addAttribute("formVouAfterRiskLevelAdjustApprove", formVouAfterRiskLevelAdjustApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/vouafter/WkfRiskLevelAdjustViewPoint";
	}

	/**
	 *
	 * 方法描述： 审批意见保存提交
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,
												String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		FormData formVouAfterRiskLevelAdjustApprove = formService.getFormData("VouAfterRiskLevelAdjustApprove");
		getFormValue(formVouAfterRiskLevelAdjustApprove, formDataMap);
		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		MfVouAfterRiskLevelAdjustHis mfVouAfterRiskLevelAdjustHis = new MfVouAfterRiskLevelAdjustHis();
		setObjValue(formVouAfterRiskLevelAdjustApprove, mfVouAfterRiskLevelAdjust);
		setObjValue(formVouAfterRiskLevelAdjustApprove, mfVouAfterRiskLevelAdjustHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfVouAfterRiskLevelAdjust);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfVouAfterRiskLevelAdjustHis);
			formDataMap.put("mfVouAfterRiskLevelAdjust", mfVouAfterRiskLevelAdjust);
			formDataMap.put("mfVouAfterRiskLevelAdjustHis", mfVouAfterRiskLevelAdjustHis);
			res = mfVouAfterRiskLevelAdjustFeign.doCommit(taskId, transition, nextUser,
					formDataMap);
			dataMap = new HashMap<String, Object>();
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}
	@RequestMapping(value = "/getMultiBusList")
	public String getMultiBusList(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);

		MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust = new MfVouAfterRiskLevelAdjust();
		mfVouAfterRiskLevelAdjust.setCusNo(cusNo);
		List<MfVouAfterRiskLevelAdjust> mfVouAfterRiskLevelAdjustProjectList = mfVouAfterRiskLevelAdjustFeign.getMultiBusList(mfVouAfterRiskLevelAdjust);
		if(mfVouAfterRiskLevelAdjustProjectList != null && mfVouAfterRiskLevelAdjustProjectList.size() > 0){
			// 申请金额总和
			Double appAmt = 0d;
			for (MfVouAfterRiskLevelAdjust mbfa : mfVouAfterRiskLevelAdjustProjectList) {
				appAmt = DataUtil.add(appAmt, mbfa.getAppAmt(), 20);
			}
			appAmt = DataUtil.retainDigit(appAmt, 2);

			MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjustTmp1 = new MfVouAfterRiskLevelAdjust();
			mfVouAfterRiskLevelAdjustTmp1.setAppAmt(appAmt);
			mfVouAfterRiskLevelAdjustTmp1.setBreedName("总金额");
			mfVouAfterRiskLevelAdjustProjectList.add(mfVouAfterRiskLevelAdjustTmp1);
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String projectTableHtml = jtu.getJsonStr( "tableVouAfterRiskLevelAdjust_GCDB", "tableTag", mfVouAfterRiskLevelAdjustProjectList, null, true);
		model.addAttribute("mfVouAfterRiskLevelAdjustProjectList", mfVouAfterRiskLevelAdjustProjectList);
		model.addAttribute("mfVouAfterRiskLevelAdjustProjectListSize", mfVouAfterRiskLevelAdjustProjectList.size());
		model.addAttribute("projectTableHtml", projectTableHtml);


		model.addAttribute("busEntrance", "pact");
		model.addAttribute("query", "");
		return "/component/vouafter/MfVouAfterRiskLevelAdjust_multiBusList";
	}
}
