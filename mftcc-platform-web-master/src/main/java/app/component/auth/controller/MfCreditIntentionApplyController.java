package app.component.auth.controller;

import app.base.User;
import app.component.auth.entity.MfCreditIntentionApply;
import app.component.auth.entity.MfCreditFrozenThaw;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCreditOaApproveDetails;
import app.component.auth.feign.MfCreditIntentionApplyFeign;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.common.ApplyEnum;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.DateUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
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
 * Title: MfCusCreditApplyAction.java
 * Description:授信申请意向视图控制层
 * @author:ywh

 **/
@Controller
@RequestMapping("/mfCreditIntentionApply")
public class MfCreditIntentionApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;
	@Autowired
	private MfCusCreditApplyFeign mfCusCreditApplyFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfCreditIntentionApplyFeign mfCreditIntentionApplyFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;
	/**
	 * 授信意向新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String creditType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String formId = "";
		MfCreditIntentionApply mfCreditIntentionApply = new MfCreditIntentionApply();
		String creditId = WaterIdUtil.getWaterId();
		mfCreditIntentionApply.setCreditId(creditId);
		String cusNo = request.getParameter("cusNo");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);

		Gson gson = new Gson();
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		// 获取业务身份
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(mfCusCustomer.getCusType());
		mfCusType = mfCusTypeFeign.getById(mfCusType);

		String baseType = mfCusType.getBaseType();
		mfCreditIntentionApply.setCusName(mfCusCustomer.getCusName());
		mfCreditIntentionApply.setCusNo(cusNo);
		mfCreditIntentionApply.setCusType(mfCusCustomer.getCusType());
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
		mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
		mfCusCreditApply.setCusNo(cusNo);
		mfCusCreditApply.setCreditModel("1");//取客户授信
		dataMap = mfCusCreditApplyFeign.initCusCreditedInput(mfCusCreditApply);
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		String creditFlag=String.valueOf(dataMap.get("creditFlag"));
		String termFlag=String.valueOf(dataMap.get("termFlag"));
		formId = prdctInterfaceFeign.getFormId("credit3", WKF_NODE.credit_apply, null, null, User.getRegNo(request));
		FormData formcreditapply0001 = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		//客户授信支持授信调整
		if(StringUtil.isEmpty(creditType)){
			creditType = (String) dataMap.get("creditType");
		}
		//授信调整，授信类型不可编辑，默认为授信调整
		if(BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)){
			formId = prdctInterfaceFeign.getFormId("credit4", WKF_NODE.credit_apply, null, null, User.getRegNo(request));
			formcreditapply0001 = formService.getFormData(formId);
			Map<String,Object> mfCusCreditContract = (Map<String, Object>) dataMap.get("mfCusCreditContract");
			mfCreditIntentionApply.setCreditAppId((String) mfCusCreditContract.get("creditAppId"));
			mfCreditIntentionApply.setJichuAmt(Double.parseDouble(String.valueOf(mfCusCreditContract.get("creditSum"))));
			mfCreditIntentionApply.setAppDate(DateUtil.getDate());
			mfCreditIntentionApply.setOpName(User.getRegName(request));
			getObjValue(formcreditapply0001, mfCreditIntentionApply);
			this.changeFormProperty(formcreditapply0001, "creditType", "initValue", "5");
		}
		//未授信过，授信类型不可编辑，默认为新增授信
		if(BizPubParm.CREDIT_TYPE_ADD.equals(creditType)){
			mfCreditIntentionApply.setCusName(mfCusCustomer.getCusName());
			mfCreditIntentionApply.setCusNo(cusNo);
			mfCreditIntentionApply.setCreditType(BizPubParm.CREDIT_TYPE_INTENTION);
			mfCreditIntentionApply.setBeginDate(DateUtil.getDate());
			mfCreditIntentionApply.setOpName(User.getRegName(request));
			getObjValue(formcreditapply0001, mfCreditIntentionApply);
		}
		Map<String,Object> parmMap = new HashMap<String,Object>();
		parmMap.put("cusBaseType",baseType);
		parmMap.put("idNum",mfCusCustomer.getIdNum());
		parmMap.put("cusTel",mfCusCustomer.getCusTel());
		
		
		getObjValue(formcreditapply0001, parmMap);

		request.setAttribute("termFlag", termFlag);
		request.setAttribute("creditFlag", creditFlag);
		model.addAttribute("formcreditapply0001", formcreditapply0001);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("baseType", baseType);
		model.addAttribute("query", "");
		model.addAttribute("creditType", creditType);
		model.addAttribute("creditId", mfCreditIntentionApply.getCreditAppId());
		String CREDIT_END_DATE_REDUCE = new CodeUtils().getSingleValByKey("CREDIT_END_DATE_REDUCE");// 授信结束日自动减一天
		if (StringUtil.isNotEmpty(CREDIT_END_DATE_REDUCE)) {
			model.addAttribute("CREDIT_END_DATE_REDUCE", CREDIT_END_DATE_REDUCE);
		}
		return "/component/auth/MfCreditIntentionApply_Insert";
	}
	/**
	 * 授信意向新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputIntention")
	public String inputIntention(Model model,String creditType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String formId = "";
		MfCreditIntentionApply mfCreditIntentionApply = new MfCreditIntentionApply();
		String creditId = WaterIdUtil.getWaterId();
		mfCreditIntentionApply.setCreditId(creditId);
		formId = prdctInterfaceFeign.getFormId("credit3", WKF_NODE.credit_apply, null, null, User.getRegNo(request));
		if("2".equals(creditType)){
			formId="CreditIntentionLinBase";
		}
		FormData formcreditapply0001 = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		mfCreditIntentionApply.setCreditType(BizPubParm.CREDIT_TYPE_INTENTION);
		mfCreditIntentionApply.setBeginDate(DateUtil.getDate());
		mfCreditIntentionApply.setOpName(User.getRegName(request));
		mfCreditIntentionApply.setCreditType(creditType);
		getObjValue(formcreditapply0001, mfCreditIntentionApply);
		model.addAttribute("formcreditapply0001", formcreditapply0001);
		model.addAttribute("query", "");
		model.addAttribute("creditType", creditType);
		String CREDIT_END_DATE_REDUCE = new CodeUtils().getSingleValByKey("CREDIT_END_DATE_REDUCE");// 授信结束日自动减一天
		if (StringUtil.isNotEmpty(CREDIT_END_DATE_REDUCE)) {
			model.addAttribute("CREDIT_END_DATE_REDUCE", CREDIT_END_DATE_REDUCE);
		}
		return "/component/auth/MfCreditIntentionApply_Insert2";
	}
	/**
	 * 查看授信申请列表
	 *
	 * @author
	 * date 2017-3-21
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/openHisData")
	public String openHisData(Model model, String cusNo)throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("cusNo",cusNo);
		return "/component/auth/MfCreditIntentionApply_List";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCreditIntentionApply mfCreditIntentionApply = new MfCreditIntentionApply();
		mfCreditIntentionApply.setCusNo(cusNo);
		try {
			mfCreditIntentionApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCreditIntentionApply.setCriteriaList(mfCreditIntentionApply, ajaxData);//我的筛选
			mfCreditIntentionApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCreditIntentionApply", mfCreditIntentionApply));
			ipage = mfCreditIntentionApplyFeign.findByPage(ipage);
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
	 * 根据id查询单条
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String creditId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formCreditIntentionBase = formService.getFormData("CreditIntentionDetail");
		getFormValue(formCreditIntentionBase);
		MfCreditIntentionApply mfCreditIntentionApply = new MfCreditIntentionApply();
		mfCreditIntentionApply.setCreditId(creditId);
		mfCreditIntentionApply = mfCreditIntentionApplyFeign.getById(mfCreditIntentionApply);
		if(StringUtil.isNotEmpty(mfCreditIntentionApply.getAppDate())){
			mfCreditIntentionApply.setAppDate(DateUtil.getDiyDate(mfCreditIntentionApply.getAppDate(),"yyyy-MM-dd"));
		}
		getObjValue(formCreditIntentionBase, mfCreditIntentionApply);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCreditIntentionApply.getCusNo());
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		// 获取业务身份
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(mfCusCustomer.getCusType());
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		String baseType = mfCusType.getBaseType();
		Map<String,Object> parmMap = new HashMap<String,Object>();
		parmMap.put("cusBaseType",baseType);
		parmMap.put("idNum",mfCusCustomer.getIdNum());
		parmMap.put("cusTel",mfCusCustomer.getCusTel());
		String oaApproveFlag = "0";
		String creditType = mfCreditIntentionApply.getCreditType();
		if(BizPubParm.CREDIT_TYPE_INTENTION.equals(creditType)){
			String sendCusType = new CodeUtils().getSingleValByKey("CREDIT_SEND_TO_OA");
			if(StringUtil.isNotEmpty(sendCusType)){
				if(sendCusType.contains(mfCusCustomer.getCusType())){
					//获取OA审批历史数据
					MfCreditOaApproveDetails mfCreditOaApproveDetails = new MfCreditOaApproveDetails();
					mfCreditOaApproveDetails.setCreditId(mfCreditIntentionApply.getCreditId());
					List<MfCreditOaApproveDetails> mfCreditOaApproveDetailsList = mfCreditIntentionApplyFeign.getOaDetails(mfCreditOaApproveDetails);
					oaApproveFlag ="1";
					model.addAttribute("mfCreditOaApproveDetailsList",mfCreditOaApproveDetailsList);
				}
			}
			model.addAttribute("linshiFlag", "0");
		}else if(BizPubParm.CREDIT_TYPE_LINSHI.equals(creditType)){
			oaApproveFlag ="0";
			model.addAttribute("linshiFlag", "1");
		}else {
		}
		//如果为临时授信展示审批历史
		String creditTypeOp = mfCreditIntentionApply.getCreditType();

		getObjValue(formCreditIntentionBase, parmMap);
		model.addAttribute("formCreditIntentionBase", formCreditIntentionBase);
		model.addAttribute("oaApproveFlag", oaApproveFlag);
		model.addAttribute("mfCreditIntentionApply", mfCreditIntentionApply);
		model.addAttribute("query", "");
		return "/component/auth/MfCreditIntentionApply_Detail";
	}
	/**
	 * 新增授信意向
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCreditIntentionApply mfCreditIntentionApply = new MfCreditIntentionApply();
		String creditModel = request.getParameter("creditModel");
		try{
			dataMap=getMapByJson(ajaxData);
			FormData 	formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcreditapply0001, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditapply0001)){
				setObjValue(formcreditapply0001, mfCreditIntentionApply);
				//客户信息
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCreditIntentionApply.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				dataMap.put("mfCreditIntentionApply", mfCreditIntentionApply);
				//插入授信信息
				mfCreditIntentionApply = mfCreditIntentionApplyFeign.insertStartProcess(dataMap);
				dataMap.put("flag", "success");
				if(StringUtil.isNotEmpty(mfCreditIntentionApply.getApproveNodeName())){
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("userRole", mfCreditIntentionApply.getApproveNodeName());
					paramMap.put("opNo", mfCreditIntentionApply.getApprovePartName());
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				}else{
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			//新增授信产品异常时，需要删除授信信息
			//mfCusCreditApplyFeign.delete(mfCusCreditApply);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 获取当前客户意向申请状态
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIntentionStatus")
	@ResponseBody
	public Map<String, Object> getIntentionStatus(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfCreditIntentionApply mfCreditIntentionApply = new MfCreditIntentionApply();
			mfCreditIntentionApply.setCusNo(cusNo);
			List<MfCreditIntentionApply> mfCreditIntentionApplyList = mfCreditIntentionApplyFeign.getAllByCusNo(mfCreditIntentionApply);
			if(mfCreditIntentionApplyList!=null&&mfCreditIntentionApplyList.size()>0){
				mfCreditIntentionApply = mfCreditIntentionApplyList.get(0);
				//判断最新的申请是意向还是临时额度
				String creditType = mfCreditIntentionApply.getCreditType();
				String creditSts = mfCreditIntentionApply.getCreditSts();
				if(BizPubParm.CREDIT_TYPE_INTENTION.equals(creditType)){
					if("2".equals(creditSts)){
						dataMap.put("intentionStatus", "1");//未发起申请意向
						dataMap.put("message", "OA审批中");//未发起申请意向
					}else if("3".equals(creditSts)){
						dataMap.put("intentionStatus", "2");//未发起申请意向
						dataMap.put("message", "意向通过");//未发起申请意向
					}else if("4".equals(creditSts)){
						dataMap.put("intentionStatus", "3");//未发起申请意向
						dataMap.put("message", "意向否决");//未发起申请意向
					}else {
					}
				}else if (BizPubParm.CREDIT_TYPE_LINSHI.equals(creditType)){
					if("2".equals(creditSts)){
						dataMap.put("intentionStatus", "4");//未发起申请意向
						dataMap.put("message", "临额审批中");//未发起申请意向
					}else if("3".equals(creditSts)){
						dataMap.put("intentionStatus", "5");//未发起申请意向
						dataMap.put("message", "临额通过");//未发起申请意向
					}else if("4".equals(creditSts)){
						dataMap.put("intentionStatus", "6");//未发起申请意向
						dataMap.put("message", "临额否决");//未发起申请意向
					}else {
					}
				}else {
				}
			}else{
				dataMap.put("intentionStatus", "0");//未发起申请意向
				dataMap.put("message", "未申请");//未发起申请意向
			}
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 打开授信临时额度审批页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param cusNo
	 * @param
	 * @date 2017-6-26 上午10:45:56
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String ajaxData, String cusNo, String creditId, String taskId,
							   String hideOpinionType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		dataMap.put("creditId", creditId);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(creditId, null);
		dataMap = mfCusCreditApplyFeign.getCreditApproveDataMap(dataMap);
		String creditType = String.valueOf(dataMap.get("creditType"));
		String formId = null;
		String nodeNo = taskAppro.getName();
		String defFalg = ApplyEnum.BUDGET_DEFFLAG_TYPE.DEFFLAG1.getCode();
		String kindNo = "credit4";
		formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.credit_approval, null, null,
				User.getRegNo(request));
		FormData formcreditapprinfo0001 = formService.getFormData(formId);// String.valueOf(dataMap.get("formId"))
		getFormValue(formcreditapprinfo0001);
		MfCreditIntentionApply mfCreditIntentionApply = new MfCreditIntentionApply();
		mfCreditIntentionApply.setCreditId(creditId);
		mfCreditIntentionApply= mfCreditIntentionApplyFeign.getById(mfCreditIntentionApply);
		getObjValue(formcreditapprinfo0001, mfCreditIntentionApply);

		String activityType = taskAppro.getActivityType();
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formcreditapprinfo0001, "opinionType", "optionArray", opinionTypeList);
		request.setAttribute("creditType", creditType);
		request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		// 获取客户业务身份
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(mfCusCustomer.getCusType());
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		String baseType="";
		if (mfCusType != null) {
			model.addAttribute("baseType", mfCusType.getBaseType());
			baseType =  mfCusType.getBaseType();
		}
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray=wkfInterfaceFeign.getBefNodes(creditId, User.getRegNo(request));
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(mfCreditIntentionApply.getCreditAppId());
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		Map<String,Object> parmMap = new HashMap<String,Object>();
		parmMap.put("cusBaseType",baseType);
		parmMap.put("jichuAmt",MathExtend.moneyStr(mfCusCreditContract.getCreditSum()));
		parmMap.put("beginDate",mfCreditIntentionApply.getBeginDate());
		parmMap.put("endDate",mfCreditIntentionApply.getEndDate());
		parmMap.put("creditAmtZong",MathExtend.add(mfCusCreditContract.getCreditSum(),mfCreditIntentionApply.getCreditSum()));
		parmMap.put("idNum",mfCusCustomer.getIdNum());
		parmMap.put("cusTel",mfCusCustomer.getCusTel());
		getObjValue(formcreditapprinfo0001, parmMap);
		model.addAttribute("formcreditapprinfo0001", formcreditapprinfo0001);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("creditId", creditId);
		model.addAttribute("nodeNo", nodeNo);
		return "/component/auth/MfCusCreditApprove_linshi";
	}
	/**
	 *
	 * 方法描述： 授信审批保存提交
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author ywh
	 * @param taskId
	 * @param creditId
	 * @param transition
	 * @param nextUser
	 * @date 2017-6-26 下午2:49:49
	 */
	@RequestMapping(value = "/submitApproveAjax")
	@ResponseBody
	public Map<String, Object> submitApproveAjax(String ajaxData, String taskId, String creditId,
												 String transition, String nextUser) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		dataMap = getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approveRemark"));
		FormData formcreditapprinfo0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
		getFormValue(formcreditapprinfo0001, getMapByJson(ajaxData));
		MfCreditIntentionApply mfCreditIntentionApply = new MfCreditIntentionApply();
		setObjValue(formcreditapprinfo0001, mfCreditIntentionApply);
		dataMap.put("mfCreditIntentionApply", mfCreditIntentionApply);
		dataMap.put("taskId", taskId);
		dataMap.put("creditId", creditId);
		dataMap.put("transition", transition);
		dataMap.put("opNo", User.getRegNo(request));
		dataMap.put("nextUser", nextUser);
		dataMap.put("opinionType", opinionType);
		dataMap.put("approvalOpinion", approvalOpinion);
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("regNo", User.getRegNo(request));
		Result res = mfCreditIntentionApplyFeign.doCommit(dataMap);
		if (res.isSuccess()) {
			dataMap.put("flag", "success");
			if (res.isEndSts()) {
				dataMap.put("msg", "临时额度审批完成");
			} else {
				dataMap.put("msg", res.getMsg());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}
	/**
	 * 授信意向新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/frozenThawInput")
	public String frozenThawInput(Model model,String operaType,String cusNo,String creditAppId ) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String formId = "";
		MfCreditFrozenThaw mfCreditFrozenThaw = new MfCreditFrozenThaw();
		String operaId = WaterIdUtil.getWaterId();
		mfCreditFrozenThaw.setOperaId(operaId);
		mfCreditFrozenThaw.setOperaType(operaType);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);

		Gson gson = new Gson();
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		// 获取业务身份
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(mfCusCustomer.getCusType());
		mfCusType = mfCusTypeFeign.getById(mfCusType);

		String baseType = mfCusType.getBaseType();
		mfCreditFrozenThaw.setCusName(mfCusCustomer.getCusName());
		mfCreditFrozenThaw.setCusNo(cusNo);
		mfCreditFrozenThaw.setCusType(mfCusCustomer.getCusType());
		mfCreditFrozenThaw.setCreditAppId(creditAppId);
		MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
		mfCusCreditContract.setCreditAppId(creditAppId);
		mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
		if(mfCusCreditContract!=null){
			mfCreditFrozenThaw.setCreditContractId(mfCusCreditContract.getId());
			mfCreditFrozenThaw.setCreditSum(mfCusCreditContract.getCreditSum());
			mfCreditFrozenThaw.setCreditBal(mfCusCreditContract.getAuthBal());
		}
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		formId = prdctInterfaceFeign.getFormId("credit5", WKF_NODE.credit_apply, null, null, User.getRegNo(request));
		FormData formfrzoenDetail = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))

		getObjValue(formfrzoenDetail, mfCreditFrozenThaw);
		Map<String,Object> parmMap = new HashMap<String,Object>();
		parmMap.put("cusBaseType",baseType);
		parmMap.put("idNum",mfCusCustomer.getIdNum());
		parmMap.put("cusTel",mfCusCustomer.getCusTel());
		getObjValue(formfrzoenDetail, parmMap);
		model.addAttribute("formfrzoenDetail", formfrzoenDetail);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("query", "");
		return "/component/auth/MfCreditFrozenThaw_Insert";
	}
	/**
	 * 新增授信冻结以及
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertFrozeThawAjax")
	@ResponseBody
	public Map<String, Object> insertFrozeThawAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCreditFrozenThaw mfCreditFrozenThaw = new MfCreditFrozenThaw();
		try{
			dataMap=getMapByJson(ajaxData);
			FormData 	formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcreditapply0001, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditapply0001)){
				setObjValue(formcreditapply0001, mfCreditFrozenThaw);
				//客户信息
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCreditFrozenThaw.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				dataMap.put("mfCreditFrozenThaw", mfCreditFrozenThaw);
				//插入授信信息
				mfCreditFrozenThaw = mfCreditIntentionApplyFeign.insertFrozenThaw(dataMap);
				dataMap.put("flag", "success");
				if(StringUtil.isNotEmpty(mfCreditFrozenThaw.getApproveNodeName())){
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("userRole", mfCreditFrozenThaw.getApproveNodeName());
					paramMap.put("opNo", mfCreditFrozenThaw.getApprovePartName());
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
					dataMap.put("ifApprove", BizPubParm.YES_NO_Y);
				}else{
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                    dataMap.put("ifApprove", BizPubParm.YES_NO_N);
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			//新增授信产品异常时，需要删除授信信息
			//mfCusCreditApplyFeign.delete(mfCusCreditApply);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 打开授信临时额度审批页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param cusNo
	 * @param
	 * @date 2017-6-26 上午10:45:56
	 */
	@RequestMapping(value = "/getFrozenViewPoint")
	public String getFrozenViewPoint(Model model, String ajaxData, String cusNo, String operaId, String taskId,
							   String hideOpinionType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		dataMap.put("operaId", operaId);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(operaId, null);
		String formId = null;
		String nodeNo = taskAppro.getName();
		String kindNo = "credit5";
		formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.credit_approval, null, null,
				User.getRegNo(request));
		FormData formcreditapprinfo0001 = formService.getFormData(formId);// String.valueOf(dataMap.get("formId"))
		getFormValue(formcreditapprinfo0001);
		MfCreditFrozenThaw mfCreditFrozenThaw = new MfCreditFrozenThaw();
		mfCreditFrozenThaw.setOperaId(operaId);
		mfCreditFrozenThaw= mfCreditIntentionApplyFeign.getMfCreditFrozenThawById(mfCreditFrozenThaw);
		mfCreditFrozenThaw.setOperaDate(DateUtil.getDiyDate(mfCreditFrozenThaw.getOperaDate(),"yyyy-MM-dd"));
		getObjValue(formcreditapprinfo0001, mfCreditFrozenThaw);

		String activityType = taskAppro.getActivityType();
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formcreditapprinfo0001, "opinionType", "optionArray", opinionTypeList);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		// 获取客户业务身份
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(mfCusCustomer.getCusType());
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		String baseType="";
		if (mfCusType != null) {
			model.addAttribute("baseType", mfCusType.getBaseType());
			baseType =  mfCusType.getBaseType();
		}
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray=wkfInterfaceFeign.getBefNodes(operaId, User.getRegNo(request));
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		Map<String,Object> parmMap = new HashMap<String,Object>();
		parmMap.put("cusBaseType",baseType);
		parmMap.put("idNum",mfCusCustomer.getIdNum());
		parmMap.put("cusTel",mfCusCustomer.getCusTel());
		getObjValue(formcreditapprinfo0001, parmMap);
		model.addAttribute("formcreditapprinfo0001", formcreditapprinfo0001);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("creditAppId", mfCreditFrozenThaw.getCreditAppId());
		model.addAttribute("operaId", operaId);
		return "/component/auth/MfCusCreditApprove_frozen";
	}
	/**
	 *
	 * 方法描述： 授信审批保存提交
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author ywh
	 * @param taskId
	 * @param creditId
	 * @param transition
	 * @param nextUser
	 * @date 2017-6-26 下午2:49:49
	 */
	@RequestMapping(value = "/submitFrozenApproveAjax")
	@ResponseBody
	public Map<String, Object> submitFrozenApproveAjax(String ajaxData, String taskId, String operaId,
												 String transition, String nextUser) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		dataMap = getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approveRemark"));
		FormData formcreditapprinfo0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
		getFormValue(formcreditapprinfo0001, getMapByJson(ajaxData));
		MfCreditFrozenThaw mfCreditFrozenThaw = new MfCreditFrozenThaw();
		setObjValue(formcreditapprinfo0001, mfCreditFrozenThaw);
		dataMap.put("mfCreditFrozenThaw", mfCreditFrozenThaw);
		dataMap.put("taskId", taskId);
		dataMap.put("operaId", operaId);
		dataMap.put("transition", transition);
		dataMap.put("opNo", User.getRegNo(request));
		dataMap.put("nextUser", nextUser);
		dataMap.put("opinionType", opinionType);
		dataMap.put("approvalOpinion", approvalOpinion);
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("regNo", User.getRegNo(request));
		Result res = mfCreditIntentionApplyFeign.doCommitForFrozen(dataMap);
		if (res.isSuccess()) {
			dataMap.put("flag", "success");
			if (res.isEndSts()) {
				dataMap.put("msg", "冻结/解冻额度审批完成");
			} else {
				dataMap.put("msg", res.getMsg());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}
	/**
	 * 方法描述： 获取授信历史列表信息
	 *
	 * @param tableId
	 * @return
	 * @throws Exception String
	 * @author Javelin
	 * @date 2017-7-28 上午10:41:21
	 */
	@RequestMapping(value = "/getFrozenThawListAjax")
	@ResponseBody
	public Map<String, Object> getFrozenThawListAjax(String creditAppId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 判断详情页面还款按钮的显隐
		MfCreditFrozenThaw mfCreditFrozenThaw = new MfCreditFrozenThaw();
		mfCreditFrozenThaw.setCreditAppId(creditAppId);
		List<MfCreditFrozenThaw> mfCreditFrozenThawList =mfCreditIntentionApplyFeign.getFrozenThawList(mfCreditFrozenThaw);
		String tableHtml = "";
		if (mfCreditFrozenThawList.size() > 0) {
			JsonTableUtil jtu = new JsonTableUtil();
			tableHtml = jtu.getJsonStr(tableId, "tableTag", mfCreditFrozenThawList, null, true);
		}
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}
	/**
	 * 根据id查询单条
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFrozenById")
	public String getFrozenById(Model model, String operaId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfrzoenBase = formService.getFormData("frozenDetail");
		getFormValue(formfrzoenBase);
		MfCreditFrozenThaw mfCreditFrozenThaw = new MfCreditFrozenThaw();
		mfCreditFrozenThaw.setOperaId(operaId);
		mfCreditFrozenThaw = mfCreditIntentionApplyFeign.getMfCreditFrozenThawById(mfCreditFrozenThaw);
		mfCreditFrozenThaw.setOperaDate(DateUtil.getDiyDate(mfCreditFrozenThaw.getOperaDate(),"yyyy-MM-dd"));
		getObjValue(formfrzoenBase, mfCreditFrozenThaw);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCreditFrozenThaw.getCusNo());
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		// 获取业务身份
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(mfCusCustomer.getCusType());
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		String baseType = mfCusType.getBaseType();
		Map<String,Object> parmMap = new HashMap<String,Object>();
		parmMap.put("cusBaseType",baseType);
		parmMap.put("idNum",mfCusCustomer.getIdNum());
		parmMap.put("cusTel",mfCusCustomer.getCusTel());
		getObjValue(formfrzoenBase, parmMap);
		model.addAttribute("formfrzoenBase", formfrzoenBase);
		model.addAttribute("mfCreditFrozenThaw", mfCreditFrozenThaw);
		model.addAttribute("query", "");
		return "/component/auth/MfCreditFrozenThaw_Detail";
	}
}

