package app.component.pact.receaccount.controller;

import app.base.User;
import app.component.accnt.entity.MfAdjustRecord;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.receaccount.entity.MfBusFincAmtConfirm;
import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pact.receaccount.entity.MfBusReceBaseInfo;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.feign.MfBusFincAmtConfirmFeign;
import app.component.pact.receaccount.feign.MfBusReceTransferFeign;
import app.component.pact.receaccount.feign.MfBusReceBaseInfoFeign;
import app.component.pact.receaccount.feign.MfBusReceBalFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.recinterface.RecInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusFincAmtConfirmController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@Controller
@RequestMapping("/mfBusFincAmtConfirm")
public class MfBusFincAmtConfirmController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusFincAmtConfirmFeign mfBusFincAmtConfirmFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusReceTransferFeign mfBusReceTransferFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WkfBusInterfaceFeign wkfBusInterfaceFeign;
	@Autowired
	private RecInterfaceFeign recInterfaceFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusReceBaseInfoFeign mfBusReceBaseInfoFeign;
	@Autowired
	private MfBusReceBalFeign mfBusReceBalFeign;
	/**
	 * 方法描述：跳转至额度确认列表页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception{
		ActionContext.initialize(request, response);
		JSONArray confirmStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_STS");
		model.addAttribute("confirmStsJsonArray", confirmStsJsonArray);
		return "/component/pact/receaccount/MfBusFincAmtConfirm_List";
	}

	/**
	 * 方法描述：获取融资额度确认列表数据
	 * @param pageNo
	 * @param pageSize
	 * @param tableId
	 * @param tableType
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
		try {
			mfBusFincAmtConfirm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusFincAmtConfirm.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusFincAmtConfirm.setCriteriaList(mfBusFincAmtConfirm, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusFincAmtConfirm", mfBusFincAmtConfirm));
			ipage = mfBusFincAmtConfirmFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}



	/**
	 *
	 * 方法描述 获取可以进行融资额度确认的合同列表
	 * @param [pageNo, ajaxData]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/9/14 16:54
	 */
	@RequestMapping(value = "/getPactListForFincConfirmAjax")
	@ResponseBody
	public Map<String,Object> getPactListForFincConfirmAjax(Integer pageNo,String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		MfBusPact mfBusPact = new MfBusPact();
		try {
			mfBusPact.setCustomQuery(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
			ipage = mfBusPactFeign.getPactListForFincConfirm(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/input")
	public String input(Model model,String pactId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		//获取合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
		// 功能节点编号
		String nodeNo = BizPubParm.WKF_NODE.finc_amt_confirm.getNodeNo();
		//获取formId
		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), BizPubParm.WKF_NODE.finc_amt_confirm, null, null,User.getRegNo(request));
		FormData formfincamtconfirmbase = formService.getFormData(formId);
		MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
		mfBusFincAmtConfirm.setTerm(mfBusPact.getTerm());
		mfBusFincAmtConfirm.setTermType(mfBusPact.getTermType());
		mfBusFincAmtConfirm.setAppAmt(mfBusPact.getPactAmt());
		mfBusPact.setRemark(mfBusFincAmtConfirm.getRemark());
		getObjValue(formfincamtconfirmbase, mfBusFincAmtConfirm);
		getObjValue(formfincamtconfirmbase, mfBusPact);

		// 放款申请表单中的还款方式修改时应收产品设置中的还款方式控制
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formfincamtconfirmbase, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formfincamtconfirmbase, "overRate", "unit", rateUnit);
		this.changeFormProperty(formfincamtconfirmbase, "cmpdRate", "unit", rateUnit);

		JSONObject json = new JSONObject();
		json.put("repayTypeMap", repayTypeMap);
		model.addAttribute("ajaxData", json.toString());
		model.addAttribute("query", "");
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("kindNo", mfBusPact.getKindNo());
		model.addAttribute("nodeNo", BizPubParm.WKF_NODE.finc_amt_confirm.getNodeNo());
		model.addAttribute("formfincamtconfirmbase", formfincamtconfirmbase);

		return "/component/pact/receaccount/MfBusFincAmtConfirm_Insert";
	}

	/**
	 *
	 * 方法描述 融资额度确认申请，保存融资申请主表
	 * @param [ajaxData,pactId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/9/14 18:10
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			Map map = getMapByJson(ajaxData);
			FormData formrecefincappbase = formService.getFormData((String) map.get("formId"));
			getFormValue(formrecefincappbase, map);
			if (this.validateFormData(formrecefincappbase)) {
				//融资额度申请信息
				MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
				setObjValue(formrecefincappbase, mfBusFincAmtConfirm);
				map.put("mfBusFincAmtConfirm",mfBusFincAmtConfirm);
				mfBusFincAmtConfirm = mfBusFincAmtConfirmFeign.insert(map);
				if(StringUtil.isNotEmpty(mfBusFincAmtConfirm.getConfirmProccessId())){
					Map<String,String> parmMap = new HashMap<String,String>();
					parmMap.put("content","保存成功");
					parmMap.put("operation","提交审批");
					dataMap.put("msg", MessageEnum.CONFIRM_DETAIL_OPERATION.getMessage(parmMap));
					dataMap.put("confirmId",mfBusFincAmtConfirm.getConfirmId());
					dataMap.put("ifApproval","1");
				}else{
					dataMap.put("ifApproval","0");
					dataMap.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
				}
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("flag", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}


	@RequestMapping("/submitProcess")
	@ResponseBody
	public Map<String,Object> submitProcess(String pactId,String confirmId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
			mfBusFincAmtConfirm.setConfirmId(confirmId);
			mfBusFincAmtConfirm.setPactId(pactId);
			dataMap = mfBusFincAmtConfirmFeign.submitProcess(mfBusFincAmtConfirm);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}


	/**
	 *
	 * 方法描述 获取融资申请额度详情页面
	 * @param [model, fincMainId, appId, pactId, fincId, operable, busEntrance]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/9/15 11:30
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String confirmId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//融资信息
		MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
		mfBusFincAmtConfirm.setConfirmId(confirmId);
		mfBusFincAmtConfirm = mfBusFincAmtConfirmFeign.getById(mfBusFincAmtConfirm);
		mfBusFincAmtConfirm = mfBusFincAmtConfirmFeign.processDataForConfirm(mfBusFincAmtConfirm);
		// 合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(mfBusFincAmtConfirm.getPactId());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);

		// 功能节点编号
		String nodeNo = BizPubParm.WKF_NODE.finc_amt_confirm.getNodeNo();
		//获取formId
		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), BizPubParm.WKF_NODE.finc_amt_confirm, null, null,User.getRegNo(request));
		FormData formfincamtconfirmbase = formService.getFormData(formId);

		getObjValue(formfincamtconfirmbase, mfBusPact);
		getObjValue(formfincamtconfirmbase, mfBusFincAmtConfirm);
		// 放款申请表单中的还款方式修改时应收产品设置中的还款方式控制
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formfincamtconfirmbase, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formfincamtconfirmbase, "overRate", "unit", rateUnit);
		this.changeFormProperty(formfincamtconfirmbase, "cmpdRate", "unit", rateUnit);

		JSONObject json = new JSONObject();
		json.put("repayTypeMap", repayTypeMap);
		model.addAttribute("ajaxData", json.toString());
		model.addAttribute("confirmId", confirmId);
		model.addAttribute("confirmSts", mfBusFincAmtConfirm.getConfirmSts());
		model.addAttribute("query", "");
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("kindNo", mfBusPact.getKindNo());
		model.addAttribute("nodeNo", BizPubParm.WKF_NODE.finc_amt_confirm.getNodeNo());
		model.addAttribute("formfincamtconfirmbase", formfincamtconfirmbase);

		return "/component/pact/receaccount/MfBusFincAmtConfirm_Detail";
	}

	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String,Object> updateAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			Map map = getMapByJson(ajaxData);
			FormData formrecefincappbase = formService.getFormData((String) map.get("formId"));
			getFormValue(formrecefincappbase, map);
			if (this.validateFormData(formrecefincappbase)) {
				//融资额度申请信息
				MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
				setObjValue(formrecefincappbase, mfBusFincAmtConfirm);
				map.put("mfBusFincAmtConfirm",mfBusFincAmtConfirm);
				mfBusFincAmtConfirm = mfBusFincAmtConfirmFeign.update(map);
				if(StringUtil.isNotEmpty(mfBusFincAmtConfirm.getConfirmProccessId())){
					Map<String,String> parmMap = new HashMap<String,String>();
					parmMap.put("content","保存成功");
					parmMap.put("operation","提交审批");
					dataMap.put("msg", MessageEnum.CONFIRM_DETAIL_OPERATION.getMessage(parmMap));
					dataMap.put("confirmId",mfBusFincAmtConfirm.getConfirmId());
					dataMap.put("ifApproval","1");
				}else{
					dataMap.put("ifApproval","0");
					dataMap.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
				}
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}



	/**
	 *
	 * 方法描述 跳转至融资审批页面
	 * @param [model, appId, transMainId, taskId, hideOpinionType, activityType]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/8/17 11:25
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model,String confirmId, String taskId, String hideOpinionType, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(confirmId, taskId);// 当前审批节点task
		//融资申请主信息
		MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
		mfBusFincAmtConfirm.setConfirmId(confirmId);
		mfBusFincAmtConfirm = mfBusFincAmtConfirmFeign.getById(mfBusFincAmtConfirm);
		mfBusFincAmtConfirm = mfBusFincAmtConfirmFeign.processDataForConfirm(mfBusFincAmtConfirm);

		//获取合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(mfBusFincAmtConfirm.getAppId());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);

		// 功能节点编号
		String nodeNo = BizPubParm.WKF_NODE.finc_amt_confirm_approval.getNodeNo();
		//获取formId
		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), BizPubParm.WKF_NODE.finc_amt_confirm_approval, null, null,User.getRegNo(request));
		FormData formconfirmapprovalbase = formService.getFormData(formId);

		getObjValue(formconfirmapprovalbase, mfBusPact);
		getObjValue(formconfirmapprovalbase, mfBusFincAmtConfirm);


		CodeUtils codeUtils = new CodeUtils();

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = codeUtils.getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formconfirmapprovalbase, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formconfirmapprovalbase, "overRate", "unit", rateUnit);
		this.changeFormProperty(formconfirmapprovalbase, "cmpdRate", "unit", rateUnit);

		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", taskAppro.getActivityName());// 当前节点编号
		List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formconfirmapprovalbase, "opinionType", "optionArray", opinionTypeList);

		//还款方式
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
		JSONObject json = new JSONObject();
		json.put("repayTypeMap", repayTypeMap);

		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));

		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("appId", mfBusFincAmtConfirm.getAppId());
		model.addAttribute("cusNo", mfBusPact.getCusNo());
		model.addAttribute("pactId", mfBusPact.getPactId());
		model.addAttribute("confirmId", confirmId);
		model.addAttribute("ajaxData", json.toString());
		model.addAttribute("nodeNo", taskAppro.getActivityName());
		model.addAttribute("query", "");
		model.addAttribute("formconfirmapprovalbase", formconfirmapprovalbase);
		return "/component/pact/receaccount/FincAmtConfirmWkfViewPoint";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appId, String confirmId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
		dataMap = getMapByJson(ajaxData);
		FormData formconfirmapprovalbase = formService.getFormData((String) dataMap.get("formId"));
		getFormValue(formconfirmapprovalbase, dataMap);
		setObjValue(formconfirmapprovalbase, mfBusFincAmtConfirm);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		try {
			dataMap.put("mfBusFincAmtConfirm", mfBusFincAmtConfirm);
			Result res = mfBusFincAmtConfirmFeign.doCommit(taskId, appId,confirmId, opinionType, approvalOpinion, transition, User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", res.getMsg());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", res.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;

	}

}
