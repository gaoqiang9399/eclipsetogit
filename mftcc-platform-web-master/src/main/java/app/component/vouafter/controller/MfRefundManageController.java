package app.component.vouafter.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplySecond;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calc.charge.feign.MfBusChargeFeeFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.common.EntityUtil;
import app.component.doc.entity.DocBizManageParam;
import app.component.docinterface.DocInterfaceFeign;
import app.component.finance.manage.entity.CwCollectConfim;
import app.component.finance.manage.feign.CwCollectConfimFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.guarantee.entity.MfGuaranteeRegistration;
import app.component.pact.guarantee.feign.MfGuaranteeRegistrationFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.vouafter.entity.MfRefundManage;
import app.component.vouafter.entity.MfRefundManageHis;
import app.component.vouafter.feign.MfRefundManageFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
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
 * Title: MfRefundManageController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 07 19:03:05 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfRefundManage")
public class MfRefundManageController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfRefundManageFeign mfRefundManageFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CwCollectConfimFeign cwCollectConfimFeign;
	@Autowired
	private MfBusChargeFeeFeign mfBusChargeFeeFeign;
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private MfBusAppFeeFeign mfBusAppFeeFeign;
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
		return "/component/vouafter/MfRefundManage_List";
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
		MfRefundManage mfRefundManage = new MfRefundManage();
		try {
			mfRefundManage.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfRefundManage.setCriteriaList(mfRefundManage, ajaxData);//我的筛选
			mfRefundManage.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfRefundManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfRefundManage", mfRefundManage));
			//自定义查询Feign方法
			ipage = mfRefundManageFeign.findByPage(ipage);
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
			FormData formRefundManagebase = formService .getFormData((String)paramMap.get("formId"));
			getFormValue(formRefundManagebase, paramMap);
			if (this.validateFormData(formRefundManagebase)) {
				MfRefundManage mfRefundManage = new MfRefundManage();
				setObjValue(formRefundManagebase, mfRefundManage);
				mfRefundManageFeign.insert(mfRefundManage);
				mfRefundManage = mfRefundManageFeign.submitProcess(mfRefundManage);
				paramMap.put("userRole", mfRefundManage.getApproveNodeName());
				paramMap.put("opNo", mfRefundManage.getApprovePartName());
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
		MfRefundManage mfRefundManageJsp = new MfRefundManage();
		setObjValue(formvouafterbase, mfRefundManageJsp);
		MfRefundManage mfRefundManage = mfRefundManageFeign.getById(mfRefundManageJsp);
		if(mfRefundManage!=null){
			try{
				mfRefundManage = (MfRefundManage)EntityUtil.reflectionSetVal(mfRefundManage, mfRefundManageJsp, getMapByJson(ajaxData));
				mfRefundManageFeign.update(mfRefundManage);
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
		MfRefundManage mfRefundManage = new MfRefundManage();
		try{
			FormData formvouafterbase = formService.getFormData("vouafterbase");
			getFormValue(formvouafterbase, getMapByJson(ajaxData));
			if(this.validateFormData(formvouafterbase)){
				mfRefundManage = new MfRefundManage();
				setObjValue(formvouafterbase, mfRefundManage);
				mfRefundManageFeign.update(mfRefundManage);
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
		MfRefundManage mfRefundManage = new MfRefundManage();
		mfRefundManage.setId(id);
		mfRefundManage = mfRefundManageFeign.getById(mfRefundManage);
		getObjValue(formvouafterbase, mfRefundManage,formData);
		if(mfRefundManage!=null){
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
		MfRefundManage mfRefundManage = new MfRefundManage();
		mfRefundManage.setId(id);
		try {
			mfRefundManageFeign.delete(mfRefundManage);
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
		MfBusApplySecond mfBusApplySecond = new MfBusApplySecond();
		mfBusApplySecond.setAppId(mfBusPact.getAppId());
		mfBusApplySecond = appInterfaceFeign.getMfBusApplySecondByAppId(mfBusApplySecond);
		CwCollectConfim cwCollectConfim = new CwCollectConfim();
		cwCollectConfim.setPactId(pactId);
		cwCollectConfim = cwCollectConfimFeign.getById(cwCollectConfim);
		double aetActualReceivedAmt = 0.00;
		double refundAmt = 0.00;
		String receviedDate = "";
		String collectAccId = "";
		String collectAccount = "";
		String collectAccName = "";
		String collectBank = "";
		String chargeId = "";
		if(cwCollectConfim==null){
			MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
			mfBusChargeFee.setPactId(pactId);
			mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
			aetActualReceivedAmt = mfBusChargeFee.getActualReceivedAmt();
			refundAmt = mfBusChargeFee.getRefundAmt();
			receviedDate = mfBusChargeFee.getReceviedDate();
			collectAccId = mfBusChargeFee.getCollectAccId();
			collectAccount = mfBusChargeFee.getCollectAccount();
			collectAccName = mfBusChargeFee.getCollectAccName();
			collectBank = mfBusChargeFee.getCollectBank();
			chargeId = mfBusChargeFee.getChargeId();
		}else {
			aetActualReceivedAmt = cwCollectConfim.getActualReceivedAmt();
			refundAmt = cwCollectConfim.getRefundAmt();
			receviedDate = cwCollectConfim.getReceviedDate();
			collectAccId = cwCollectConfim.getReceivedAccId();
			collectAccount = cwCollectConfim.getReceviedAccout();
			collectAccName = cwCollectConfim.getReceviedAccoutName();
			collectBank = cwCollectConfim.getReceviedBankName();
			chargeId = cwCollectConfim.getChargeId();
		}
		MfRefundManage mfRefundManage = new MfRefundManage();
		String id = WaterIdUtil.getWaterId();
        mfRefundManage.setId(id);
        mfRefundManage.setAppId(mfBusApply.getAppId());
        mfRefundManage.setAppName(mfBusApply.getAppName());
        mfRefundManage.setCusName(mfBusApply.getCusName());
        mfRefundManage.setCusNo(mfBusApply.getCusNo());
        mfRefundManage.setKindName(mfBusApply.getKindName());
        mfRefundManage.setKindNo(mfBusApply.getKindNo());
        mfRefundManage.setPactNo(mfBusPact.getPactNo());
        mfRefundManage.setPactId(mfBusPact.getPactId());
        mfRefundManage.setPactAmt(mfBusPact.getPactAmt());
        mfRefundManage.setActualReceivedAmt(aetActualReceivedAmt);
        mfRefundManage.setRefundAmt(refundAmt);
        mfRefundManage.setReceviedDate(receviedDate);
        mfRefundManage.setCollectAccId(collectAccId);
        mfRefundManage.setCollectAccount(collectAccount);
        mfRefundManage.setCollectAccName(collectAccName);
        mfRefundManage.setCollectBank(collectBank);
		//获取缴款通知书的账号信息
		MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
		mfBusChargeFee.setChargeId(chargeId);
		mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
//		mfRefundManage.setSumAmt(mfBusChargeFee.getAccountAmt());
//		mfRefundManage.setGuaranteeAmt(mfBusChargeFee.getGuaranteeAmt());
//		mfRefundManage.setBond(mfBusChargeFee.getBond());
//		mfRefundManage.setHandAmt(mfBusChargeFee.getHandAmt());
		mfRefundManage.setActualReceivedGuaranteeAmt(mfBusChargeFee.getGuaranteeAmt());
		mfRefundManage.setActualReceivedBond(mfBusChargeFee.getBond());
		mfRefundManage.setActualReceivedHandAmt(mfBusChargeFee.getHandAmt());
		mfRefundManage.setPayTaxesNo(mfBusChargeFee.getPayTaxesNo());
		mfRefundManage.setTermType(mfBusApply.getTermType());
		mfRefundManage.setTerm(mfBusApply.getTerm());
		mfRefundManage.setGuaEndDate(mfBusApplySecond.getGuaEndDate());
		mfRefundManage.setActualTerm(mfBusApply.getTerm());
		mfRefundManage.setActualGuaEndDate(mfBusApplySecond.getGuaEndDate());
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setAppId(mfBusApply.getAppId());
		mfBusAppFee.setItemNo("1");
		List<MfBusAppFee> busAppFeeGuaranteeList = mfBusAppFeeFeign.getMfBusAppFeeList(mfBusAppFee);
		if(busAppFeeGuaranteeList != null && busAppFeeGuaranteeList.size() > 0){
			mfRefundManage.setGuaranteeRate(busAppFeeGuaranteeList.get(0).getRateScale());
		}
		mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setAppId(mfBusApply.getAppId());
		mfBusAppFee.setItemNo("3");
		List<MfBusAppFee> busAppFeeHandList = mfBusAppFeeFeign.getMfBusAppFeeList(mfBusAppFee);
		if(busAppFeeHandList != null && busAppFeeHandList.size() > 0){
			mfRefundManage.setHandRate(busAppFeeHandList.get(0).getRateScale());
		}
		mfRefundManage.setActualPactAmt(mfBusApply.getAppAmt());
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
		mfGuaranteeRegistration.setAppId(mfBusApply.getAppId());
		mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
		if(mfGuaranteeRegistration != null){
			mfRefundManage.setGuaranteeAmt(mfGuaranteeRegistration.getGuaFee());
			mfRefundManage.setHandAmt(mfGuaranteeRegistration.getHandFee());
		}
		mfRefundManage.setBond(mfBusApplySecond.getAssureAmt());
		FormData formRefundManagebase = formService.getFormData("RefundManagebase");
        getObjValue(formRefundManagebase, mfRefundManage);


		//节点要件关联编号
		String relNo = id;
		String nodeNo = "refundManage";
		String kindNo = mfRefundManage.getKindNo();

		// 要件初始化
		DocBizManageParam dbmp = new DocBizManageParam();
		dbmp.setScNo(nodeNo);// 场景编号
		dbmp.setRelNo(relNo);// 业务编号
		dbmp.setDime(kindNo);//
		dbmp.setCusNo(mfRefundManage.getCusNo());
		dbmp.setRegNo(User.getRegNo(request));
		dbmp.setOrgNo(User.getOrgNo(request));
		docInterfaceFeign.initiaBiz(dbmp);

		model.addAttribute("formRefundManagebase", formRefundManagebase);
		model.addAttribute("id", id);
        model.addAttribute("query", "");
		model.addAttribute("nodeNo", nodeNo);
        return "/component/vouafter/MfRefundManage_Insert";
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
        FormData formRefundManageinit = formService.getFormData("RefundManageinit");
        model.addAttribute("formRefundManageinit", formRefundManageinit);
        model.addAttribute("query", "");
		return "/component/vouafter/MfRefundManage_InitBusInfo";
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
		FormData formRefundManagedetail = formService.getFormData("RefundManagedetail");
		getFormValue(formRefundManagedetail);
		MfRefundManage mfRefundManage = new MfRefundManage();
		mfRefundManage.setId(id);
		mfRefundManage = mfRefundManageFeign.getById(mfRefundManage);
		getObjValue(formRefundManagedetail, mfRefundManage);
		//节点要件关联编号
		model.addAttribute("id", id);
		model.addAttribute("mfRefundManage", mfRefundManage);
		model.addAttribute("formRefundManagedetail", formRefundManagedetail);
		model.addAttribute("query", "query");
		String refundQueryFile = "query";
		if("supplement_data".equals(nodeNo)){
			refundQueryFile = "";
		}
		model.addAttribute("refundQueryFile", refundQueryFile);
		model.addAttribute("from", from);
		model.addAttribute("nodeNo", "refundManage");
		String temParm1 = "cusNo=" + mfRefundManage.getCusNo() +
				"&appId=" + mfRefundManage.getAppId() + "&pactId=" +
				mfRefundManage.getPactId() + "&refundId=" + id + "&nodeNo=refundManage";
		model.addAttribute("temParm1", temParm1);
		return "/component/vouafter/MfRefundManage_Detail";
	}

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRefundManageListPage")
	public String getRefundManageListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfRefundManage_SelectList";
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
	@RequestMapping(value = "/findRefundManageByPageAjax")
	@ResponseBody
	public Map<String, Object> findRefundManageByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
											  String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRefundManage mfRefundManage = new MfRefundManage();
		try {
			mfRefundManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfRefundManage.setCriteriaList(mfRefundManage, ajaxData);// 我的筛选
			mfRefundManage.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfRefundManage", mfRefundManage));
			ipage = mfRefundManageFeign.findRefundManageByPage(ipage);
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
		MfRefundManage mfRefundManage = new MfRefundManage();
		mfRefundManage.setId(id);
		mfRefundManage = mfRefundManageFeign.getById(mfRefundManage);
		mfRefundManage.setApprovePartNo(null);
		mfRefundManage.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);// 当前审批节点task
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		String formId = "RefundManageApprove";
		if("supplement_data".equals(nodeNo)){
			formId = "RefundManagesupplementApprove";
		}
		// 初始化基本信息表单、工作经历表单
		FormData formRefundManageApprove = formService.getFormData(formId);
		// 实体对象放到表单对象中
		getObjValue(formRefundManageApprove, mfRefundManage);
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
		this.changeFormProperty(formRefundManageApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("id", id);
		model.addAttribute("mfRefundManage", mfRefundManage);
		model.addAttribute("formRefundManageApprove", formRefundManageApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/vouafter/WkfRefundManageViewPoint";
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
		FormData formRefundManageApprove = formService.getFormData("RefundManageApprove");
		getFormValue(formRefundManageApprove, formDataMap);
		MfRefundManage mfRefundManage = new MfRefundManage();
		MfRefundManageHis mfRefundManageHis = new MfRefundManageHis();
		setObjValue(formRefundManageApprove, mfRefundManage);
		setObjValue(formRefundManageApprove, mfRefundManageHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfRefundManage);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfRefundManageHis);
			formDataMap.put("mfRefundManage", mfRefundManage);
			formDataMap.put("mfRefundManageHis", mfRefundManageHis);
			res = mfRefundManageFeign.doCommit(taskId, transition, nextUser,
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
}
