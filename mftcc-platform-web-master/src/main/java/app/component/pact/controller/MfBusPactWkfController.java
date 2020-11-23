/**
 * Copyright (C) DXHM 版权所有
 * 文件名： mfBusPactWkfAction.java
 * 包名： app.component.app.action
 * 说明：
 * @author zhs
 * @date 2016-5-26 上午9:35:29
 * @version V1.0
 */
package app.component.pact.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.app.feign.MfBusApplyFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.ViewUtil;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFollowPact;
import app.component.pact.entity.MfBusPact;
import app.component.pact.entity.MfBusPactHis;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.feign.MfBusPactHisFeign;
import app.component.pact.feign.MfBusPactWkfFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.PropertyUtils;
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
 * 类名： mfBusPactWkfAction 描述：
 * 
 * @author zhs
 * @date 2016-5-26 上午9:35:29
 *
 *
 */
@Controller
@RequestMapping("/mfBusPactWkf")
public class MfBusPactWkfController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusPactWkfFeign mfBusPactWkfFeign;
	@Autowired
	private MfBusPactHisFeign mfBusPactHisFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WkfBusInterfaceFeign wkfBusInterfaceFeign;
	@Autowired
	private CalcInterfaceFeign calcInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;

	@Autowired
	private MfBusAppKindFeign mfBusAppKindFeign;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;

	/**
	 * 
	 * 方法描述： 进入审批视角（审批页面）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-5-26 上午10:26:55
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String pactId, String taskId, String hideOpinionType, String activityType, String isPrimary)
			throws Exception {
		FormService formService = new FormService();

		ActionContext.initialize(request, response);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		String pactIdTmp = pactId;
		if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是合同清稿审批
			pactIdTmp = mfBusPact.getPrimaryPactId();
		}
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(pactIdTmp, taskId);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

		Map<String, String> map = new HashMap<String, String>();
		// 获取从合同列表
		List<MfBusFollowPact> mfBusFollowPactList = new ArrayList<MfBusFollowPact>();
		Double pactAmt = mfBusPact.getPactAmt();
		mfBusFollowPactList = mfBusCollateralDetailRelFeign.getMfBusFollowPactList(mfBusPact.getAppId(), pactAmt);
		if (mfBusFollowPactList.size() == 0) {
			mfBusFollowPactList = null;
		}

		String storageMergeFlag = mfBusPact.getStorageMergeFlag();
		MfBusPactHis mfBusPactHis = new MfBusPactHis();
		mfBusPactHis.setPactId(pactId);
		if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是业务初选审批
			mfBusPactHis.setPrimaryPactId(mfBusPact.getPrimaryPactId());
		}
		String cusNo = mfBusPact.getCusNo();
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("approvalNodeNo", "contract_approval");// 合同审批
		model.addAttribute("storageMergeFlag", storageMergeFlag);
		model.addAttribute("cusNo", cusNo);
		// 处理原始的数据（利率转换）
		mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
		Double pactAmtShow = mfBusPact.getPactAmt();// 原始的合同金额
		Double fincRateShow = mfBusPact.getFincRate();// 原始的合同利率
		String termShow = mfBusPact.getTermShow();// 原始的合同期限
		String cmpdRateType = mfBusPact.getCmpFltRateShow();
		WKF_NODE node = WKF_NODE.contract_approval;
		if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是业务初选审批
			node = WKF_NODE.primary_contract_approval;
		}
		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(),node,mfBusPact.getAppId(), null, User.getRegNo(request));
		if ("pact_supplement_data".equals(nodeNo)) {
			formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.pact_supplement_data,mfBusPact.getAppId(), null, User.getRegNo(request));
		}
		FormData formpact0005 = formService.getFormData(formId);
		model.addAttribute("formId", formId);
		List<MfBusPactHis> list = new ArrayList<MfBusPactHis>();
		list = mfBusPactHisFeign.getListByDESC(mfBusPactHis);
		if (list != null && list.size() > 0) {
			mfBusPactHis = list.get(0);
			PropertyUtils.copyProperties(mfBusPact, mfBusPactHis);
			mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
			//处理基准利率老数据
			mfBusPact = mfBusPactFeign.doBaseRateOld(mfBusPact);
		}
		// 处理审批历史的数据（利率转换）
		mfBusPact.setPactAmtShow(pactAmtShow);
		mfBusPact.setFincRateShow(fincRateShow);
		mfBusPact.setTermShow(termShow);
		mfBusPact.setCmpFltRateShow(cmpdRateType);
		//获得授信信息
		Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(mfBusPact.getAppId());
		if (!creditData.isEmpty()) {
			getObjValue(formpact0005, creditData);
		}
        //处理市场报价利率重新发布提示与计算
        Map<String, Object> calcRateChangeMap = calcRateChange(mfBusPact);
        if ("false".equals(calcRateChangeMap.get("flag"))) {
            String floatNumber = String.valueOf(calcRateChangeMap.get("floatNumber"));
            Double baseRate = Double.valueOf(String.valueOf(calcRateChangeMap.get("baseRate")));
            mfBusPact.setBaseRate(baseRate);
            mfBusPact.setFloatNumber(floatNumber);
            model.addAttribute("baseRateChange", "false");
        } else {
            model.addAttribute("baseRateChange", "true");
        }
		getObjValue(formpact0005, mfBusPact);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();
		this.changeFormProperty(formpact0005, "term", "unit", termUnit);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位,月利率百分号
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formpact0005, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formpact0005, "fincRateShow", "unit", rateUnit);
		this.changeFormProperty(formpact0005, "overRate", "unit", rateUnit);
		this.changeFormProperty(formpact0005, "cmpdRate", "unit", rateUnit);
		map.put("cusNo", cusNo);
		map.put("busModel", mfBusPact.getBusModel());

		JSONArray mapArray = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
		JSONObject json = new JSONObject();
		for (int i = 0; i < mapArray.size(); i++) {
			mapArray.getJSONObject(i).put("id", mapArray.getJSONObject(i).getString("optCode"));
			mapArray.getJSONObject(i).put("name", mapArray.getJSONObject(i).getString("optName"));
		}
		json.put("map", mapArray);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		// 获得该申请相关的费用标准信息
		List<MfBusAppFee> mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(mfBusPact.getAppId(), null, null);

		//处理其他担保方式
		List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
		MfBusAppKind mfBusAppKindVouType = new MfBusAppKind();
		mfBusAppKindVouType.setAppId(mfBusPact.getAppId());
		mfBusAppKindVouType= mfBusAppKindFeign.getById(mfBusAppKindVouType);
		if(StringUtil.isEmpty(mfBusAppKindVouType.getVouType())){
			List<ParmDic> pdl = (List<ParmDic>)new CodeUtils().getCacheByKeyName("VOU_TYPE");
			for(ParmDic parmDic:pdl){
				if( "1".equals(parmDic.getOptCode())){
					continue;
				}
				OptionsList s= new OptionsList();
				s.setOptionLabel(parmDic.getOptName());
				s.setOptionValue(parmDic.getOptCode());
				vouTypeOtherList.add(s);
			}
		}else{
			Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
			String[] vouTypes = mfBusAppKindVouType.getVouType().split("\\|");
			for(int i=0;i<vouTypes.length;i++){
				if("1".equals(vouTypes[i])){
					continue;
				}
				OptionsList s= new OptionsList();;
				s.setOptionValue(vouTypes[i]);
				s.setOptionLabel(dicMap.get(vouTypes[i]));
				vouTypeOtherList.add(s);
			}
		}
		this.changeFormProperty(formpact0005, "vouTypeOther", "optionArray", vouTypeOtherList);




		scNo = BizPubParm.SCENCE_TYPE_DOC_PACT_APPROVAL;
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);
		model.addAttribute("scNo", scNo);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formpact0005, "opinionType", "optionArray", opinionTypeList);
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
        //放款渠道过滤
        opinionTypeList = new ArrayList<OptionsList>();
        List<ParmDic> opinionTypes = (List<ParmDic>) new CodeUtils().getCacheByKeyName("PUTOUT_METHOD");
        if(opinionTypes != null && opinionTypes.size()>0){
            for(ParmDic parmDic:opinionTypes){
                //当mf_bus_app_kind表中的is_fund参数是1时，放款渠道过滤掉客户经理放款
                if(BizPubParm.YES_NO_Y.equals(mfBusAppKind.getIsFund())){
                    if("0".equals(parmDic.getOptCode())){//放款渠道过滤掉客户经理放款
                        continue;
                    }
                }
                OptionsList s= new OptionsList();
                s.setOptionLabel(parmDic.getOptName());
                s.setOptionValue(parmDic.getOptCode());
                opinionTypeList.add(s);
            }
        }
        this.changeFormProperty(formpact0005, "putoutMethod", "optionArray", opinionTypeList);

        // 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formpact0005", formpact0005);
		model.addAttribute("pactId", pactId);
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("taskId", taskId);
		model.addAttribute("query", "");
		model.addAttribute("isPrimary", isPrimary);
		model.addAttribute("mfBusFollowPactList", mfBusFollowPactList);
		//电子文档列表查询方法（1-该节点只查询已经保存过的文档0-查询全部）
		String querySaveFlag = request.getParameter("querySaveFlag");
		model.addAttribute("querySaveFlag", querySaveFlag);
		model.addAttribute("approvalNodeNo", WKF_NODE.contract_approval.getNodeNo());
		return "/component/pact/wkf/WkfViewPoint";
	}

	/**
	 * 
	 * 方法描述： 审批提交（审批意见保存）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-5-26 上午10:53:17
	 */

	@RequestMapping(value = "/submitUpdate")
	public String submitUpdate(Model model, String pactId, String activityType) throws Exception {
		ActionContext.initialize(request, response);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		Result res = new Result();
		// res =
		// mfBusPactWkfFeign.doCommit(taskId,pactId,opinionType,approvalOpinion,transition,User.getRegNo(this.getHttpRequest()),nextUser,mfBusPact);
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		if (!res.isSuccess()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("pactId", pactId);
			ViewUtil.setViewPointParm(request, map);
			Map<String, String> otMap = new HashMap<String, String>();

			if ("signtask".equals(activityType)) {
				otMap.put("1", "同意");
				otMap.put("5", "不同意");
			} else {
				otMap.put("1", "同意");
				otMap.put("3", "退回上一环节");
				otMap.put("4", "退回初审");
				otMap.put("2", "否决");
			}
			model.addAttribute("otMap", otMap);
			return "input";
		}
		return "/component/pact/MfBusPact_List";

	}

	/**
	 * 
	 * 方法描述： 审批提交（审批意见保存）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-5-26 上午10:53:17
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String pactId, String ajaxDataList, String taskId,String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//判断当前节点是否可进行审批
//		TaskImpl taskApprove = wkfInterfaceFeign.getTask(pactId, "");
		String appOpNo = User.getRegNo(request);
		TaskImpl taskApprove = wkfInterfaceFeign.getTaskWithUser(pactId, "",appOpNo);
		if(!taskId.equals(String.valueOf(taskApprove.getDbid()))){//不相等则审批不在此环节
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
			return dataMap;
		}
		MfBusPact mfBusPact = new MfBusPact();
		MfBusPact mfBusPactTmp = new MfBusPact();
		mfBusPactTmp.setPactId(pactId);
		mfBusPactTmp = mfBusPactFeign.getById(mfBusPactTmp);
		mfBusPact.setAppId(mfBusPactTmp.getAppId());
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formpact0005 = formService.getFormData(formId);

		getFormValue(formpact0005, map);
		setObjValue(formpact0005, mfBusPact);
		if(mfBusPact.getTerm() !=null && !mfBusPact.getTerm().equals(mfBusPactTmp.getTerm())){//如果修改了合同期限，同步修改合同结束日期
			MfBusAppKind mfBusAppKind = new MfBusAppKind();
			mfBusAppKind.setAppId(mfBusPactTmp.getAppId());
			mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
			String beginDate = mfBusPactTmp.getBeginDate();
			String endDate = "";
			String endDateShow ="";
			String calcIntstFlag =mfBusAppKind.getCalcIntstFlag();
			String  pactEndDateShowFlag=  mfBusAppKind.getPactEnddateShowFlag();
			if(BizPubParm.TERM_TYPE_MONTH.equals(mfBusPactTmp.getTermType())){//融资期限类型为月
				endDate=DateUtil.addMonth(beginDate,  mfBusPact.getTerm());
				endDateShow=endDate;
			}else{//期限类型为日
				endDate=DateUtil.addDay(beginDate,  mfBusPact.getTerm());
				endDateShow=endDate;
			}
			if(BizPubParm.CALC_INTST_FLAG_2.equals(calcIntstFlag)){//2-首尾都计算
				endDateShow=DateUtil.addByDay(endDateShow, -1);
				endDate=DateUtil.addByDay(endDate,-1);
			}else if(BizPubParm.CALC_INTST_FLAG_1.equals(calcIntstFlag) && BizPubParm.PACT_ENDDATE_SHOW_FLAG_2.equals(pactEndDateShowFlag)){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为2时值为end_date-1,
				endDateShow=DateUtil.addByDay(endDateShow, -1);
			}else if(BizPubParm.CALC_INTST_FLAG_1.equals(calcIntstFlag) && BizPubParm.PACT_ENDDATE_SHOW_FLAG_3.equals(pactEndDateShowFlag)){////合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为 3    实际结束日期减一天，显示结束日期再减一天, 否则和end_date一致
				endDate=DateUtil.addByDay(endDate,-1);//合同结束日期 实际结束日期减一天
				endDateShow=DateUtil.addByDay(endDate,-1);//显示结束日期再减一天(在实际结束日期的基础上)
			}else if(BizPubParm.CALC_INTST_FLAG_1.equals(calcIntstFlag) && BizPubParm.PACT_ENDDATE_SHOW_FLAG_4.equals(pactEndDateShowFlag)){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为4时值为显示日期减去一天 实际结束日期减去一天  显示日期 和 实际结束一致  均是少一天
				endDateShow=DateUtil.addByDay(endDateShow, -1);
				endDate=DateUtil.addByDay(endDate,-1);//合同结束日期 实际结束日期减一天
			}else {
			}
			mfBusPact.setEndDate(endDate);
			mfBusPact.setEndDateShow(endDateShow);
		}
		mfBusPact = mfBusPactFeign.disProcessDataForPact(mfBusPact);
		mfBusPact.setCusNo(mfBusPactTmp.getCusNo());
		JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
		List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(),new JsonConfig());
		try {
			dataMap = getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			dataMap.put("termType", mfBusPactTmp.getTermType());
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusPact);
			dataMap.put("mfBusPact", mfBusPact);
			dataMap.put("mfBusAppFeeList", mfBusAppFeeList);
			dataMap.put("approvalOpinion", approvalOpinion);
			Result res = mfBusPactWkfFeign.doCommit(taskId, pactId, opinionType, transition,
					User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					// 审批通过之后提交业务到下一个阶段
					Result result = wkfBusInterfaceFeign.doCommitNextStepWithOpNo(mfBusPactTmp.getAppId(),mfBusPactTmp.getWkfAppId(),User.getRegNo(request));

					if (!result.isSuccess()) {
						dataMap.put("flag", "error");
					}else if (result.isSuccess()&&result.isEndSts()){
						result.setMsg(MessageEnum.SUCCEED_OPERATION.getMessage("合同审批") + " 业务进入贷后阶段");
					}else {
					}
					dataMap.put("msg", result.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", res.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;

	}
	/**
	 *
	 * 方法描述： 合同清稿审批提交（审批意见保存）
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-5-26 上午10:53:17
	 */
	@RequestMapping(value = "/submitUpdateForPrimaryAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateForPrimaryAjax(String ajaxData, String pactId, String primaryPactId, String ajaxDataList, String taskId,
                                                          String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //判断当前节点是否可进行审批
        TaskImpl taskApprove = wkfInterfaceFeign.getTask(primaryPactId, "");
        if(!taskId.equals(String.valueOf(taskApprove.getDbid()))){//不相等则审批不在此环节
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
            return dataMap;
        }
        MfBusPact mfBusPact = new MfBusPact();
        MfBusPact mfBusPactTmp = new MfBusPact();
        mfBusPactTmp.setPactId(pactId);
        mfBusPactTmp = mfBusPactFeign.getById(mfBusPactTmp);
        Map map = getMapByJson(ajaxData);
        String formId = (String) map.get("formId");
        FormData formpact0005 = formService.getFormData(formId);

        getFormValue(formpact0005, map);
        setObjValue(formpact0005, mfBusPact);
        mfBusPact = mfBusPactFeign.disProcessDataForPact(mfBusPact);
        mfBusPact.setCusNo(mfBusPactTmp.getCusNo());
        JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
        List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(),new JsonConfig());
        try {
            dataMap = getMapByJson(ajaxData);
            String opinionType = String.valueOf(dataMap.get("opinionType"));
            String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
            dataMap.put("termType", mfBusPactTmp.getTermType());
            new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusPact);
            dataMap.put("mfBusPact", mfBusPact);
            dataMap.put("mfBusAppFeeList", mfBusAppFeeList);
            Result res = mfBusPactWkfFeign.doCommitForPrimary(taskId, pactId,primaryPactId, opinionType, approvalOpinion, transition,
                    User.getRegNo(request), nextUser, dataMap);
            if (res.isSuccess()) {
                dataMap.put("flag", "success");
                if (res.isEndSts()) {
                    // 审批通过之后提交业务到下一个阶段
                    Result result = wkfBusInterfaceFeign.doCommitNextStepWithOpNo(mfBusPactTmp.getAppId(),mfBusPactTmp.getWkfAppId(),User.getRegNo(request));
                    if (!result.isSuccess()) {
                        dataMap.put("flag", "error");
                    }
                    dataMap.put("msg", result.getMsg());
                } else {
                    dataMap.put("msg", res.getMsg());
                }
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", res.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
            throw e;
        }
        return dataMap;

    }

    /**
     * @param mfBusApply
     * @return
     * @desc 判断市场报价利率重新发布
     * @Author 周凯强
     * @date 20191011
     */
    private Map<String, Object> calcRateChange(MfBusPact mfBusPact) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            Map<String, Object> parmMap = new HashMap<>();
			if (BizPubParm.BASE_RATE_TYPE4.equals(mfBusPact.getBaseRateType())) {
                parmMap.put("rateNo", mfBusPact.getLprNumber());
                dataMap = mfBusApplyFeign.getLprRateByRateNo(parmMap);
                if ("success".equals(dataMap.get("flag"))) {
                    String baseRateNew = String.valueOf(dataMap.get("baseRate"));
                    String baseRateOld = String.valueOf(mfBusPact.getBaseRate());
					if (MathExtend.comparison(baseRateOld, baseRateNew) == 0) {
                        dataMap.put("flag", "true");
                    } else {
                        String fincRate = String.valueOf(mfBusPact.getFincRate());
                        String floatNumber = MathExtend.subtract(fincRate, baseRateNew);
                        floatNumber = MathExtend.multiply(floatNumber, "100");
                        dataMap.put("floatNumber", floatNumber);
                        dataMap.put("flag", "false");
                    }
                }
            } else {
                dataMap.put("flag", "true");
            }
        } catch (Exception e) {
            throw e;
        }
        return dataMap;
    }

}
