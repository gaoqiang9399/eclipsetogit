package app.component.app.controller;

/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfBusApplyWkfAction.java
 * 包名： app.component.app.action
 * 说明：
 *
 * @author zhs
 * @date 2016-5-26 上午9:35:29
 * @version V1.0
 */

import app.base.User;
import app.component.app.entity.*;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfBusApplyWkfFeign;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.app.feign.MfBusApplyHisFeign;
import app.component.app.feign.MfTuningReportFeign;
import app.component.app.feign.MfLoanApplyFeign;
import app.component.app.makepolicymeeting.entity.MfReviewMember;
import app.component.app.makepolicymeeting.feign.MfReviewMemberFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.feign.MfAssureInfoFeign;
import app.component.auth.entity.MfCusAgenciesCredit;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditApproveInfo;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditApproveInfoFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.calc.penalty.entity.MfBusAppPenaltyMain;
import app.component.calc.penalty.feign.MfBusAppPenaltyMainFeign;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.ApplyEnum;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.ViewUtil;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.component.examinterface.ExamInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import app.component.sysextendinterface.SysExtendInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.oscache.feign.CodeUtilsFeign;
import app.tech.upload.FeignSpringFormEncoder;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.*;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
 * 类名： MfBusApplyWkfAction
 * 描述：
 * @author zhs
 * @date 2016-5-26 上午9:35:29
 *
 *
 */
@Controller
@RequestMapping("/mfBusApplyWkf")
public class MfBusApplyWkfController extends BaseFormBean {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfBusApplyWkfFeign mfBusApplyWkfFeign;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private MfBusApplyHisFeign mfBusApplyHisFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private CalcInterfaceFeign calcInterfaceFeign;
    @Autowired
    private AppInterfaceFeign appInterfaceFeign;
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private ExamInterfaceFeign examInterfaceFeign;
    @Autowired
    private WkfBusInterfaceFeign wkfBusInterfaceFeign;
    //授信业务控制
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
    @Autowired
    private SysExtendInterfaceFeign sysExtendInterfaceFeign;
    @Autowired
    private CollateralInterfaceFeign collateralInterfaceFeign;
    //尽调信息
    @Autowired
    private MfTuningReportFeign mfTuningReportFeign;

    private List<MfBusAppFee> mfBusAppFeeList;
    @Autowired
    private MfLoanApplyFeign mfLoanApplyFeign;

    @Autowired
    private MfBusCollateralRelFeign mfBusCollateralRelFeign;
    @Autowired
    private MfReviewMemberFeign mfReviewMemberFeign;
    @Autowired
    private MfBusAppPenaltyMainFeign mfBusAppPenaltyMainFeign;
    @Autowired
    private MfBusAppKindFeign mfBusAppKindFeign;
    @Autowired
    private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;

    @Autowired
    private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfCusCreditApplyFeign mfCusCreditApplyFeign;
    @Autowired
    private MfBusAppFeeFeign mfBusAppFeeFeign;
    @Autowired
    private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
    @Autowired
    private MfAssureInfoFeign mfAssureInfoFeign;
    @Autowired
    private SysUserFeign sysUserFeign;
    @Autowired
    private MfCusCreditApproveInfoFeign mfCusCreditApproveInfoFeign;


    /**
     *
     * 方法描述： 进入审批视角（审批页面）
     * @param model
     * @param ajaxData
     * @param appId 申请编号
     * @param taskId 当前任务id
     * @param hideOpinionType 隐藏的意见类型 格式是1@2
     * @param activityType
     * @param isPrimary 是否为业务初选审批
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2016-5-26 上午10:26:55
     */
    @RequestMapping(value = "/getViewPoint")
    public String getViewPoint(Model model, String ajaxData, String appId, String taskId, String hideOpinionType, String activityType, String isPrimary,String ifAnalysisTable) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String queryForm = "";
        String queryFile = "";
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        String appIdTmp = appId;
        if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是业务初选审批
            appIdTmp = mfBusApply.getPrimaryAppId();
        }
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(appIdTmp, taskId);// 当前审批节点task
        String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
        // 当前岗位类型 pass正常审批流转到的岗位 rollback发回重审流程到的岗位
        JSONObject lstResult = wkfInterfaceFeign.getLstResult(taskId, appId,User.getRegNo(request));
        String approveType = String.valueOf(lstResult.get("lstResult"));
        CodeUtils cu = new CodeUtils();
        mfBusApply.setReplyFincRate(mfBusApply.getFincRate());
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        String busModel = mfBusApply.getBusModel();
        String ifChargeback = BizPubParm.YES_NO_N;
        String ifRiskApprovalSupp = BizPubParm.YES_NO_N;
        String ifFirstReviewMeet = BizPubParm.YES_NO_Y;
        JSONArray jsonArray = new JSONArray();
        if(BizPubParm.BUS_MODEL_12.equals(busModel)){
            MfBusApplySecond mfBusApplySecond = mfBusApplyFeign.getSecondByAppId(appId);
            if(mfBusApplySecond != null){
                PropertyUtils.copyProperties(mfBusApply,mfBusApplySecond);
                ifChargeback = mfBusApplySecond.getIfChargeback();
                if(BizPubParm.YES_NO_Y.equals(ifChargeback)){
                     queryForm = "edit";
                     queryFile = "query";
                }
                ifRiskApprovalSupp = mfBusApplySecond.getIfRiskApprovalSupp();
                if(BizPubParm.YES_NO_Y.equals(ifRiskApprovalSupp)){
                     queryForm = "query";
                     queryFile = "edit";
                }
                //风险审核人
                if("riskApproval".equals(nodeNo) || "riskAgainApproval".equals(nodeNo)){
                    MfBusAppFee mfBusAppFee = new MfBusAppFee();
                    mfBusAppFee.setAppId(appId);
                    mfBusAppFee.setItemNo("1");
                    mfBusAppFee = mfBusAppFeeFeign.getById(mfBusAppFee);
                    String termType = mfBusApply.getTermType();
                    String termShow = "";
                    Double yearRate = 0.00;
                    if(mfBusAppFee!=null){
                        if(BizPubParm.TERM_TYPE_MONTH.equals(termType)){
                            termShow = mfBusApply.getTermShow();
                            // {担保费率}/{保函期限}*12
                            yearRate = MathExtend.round(MathExtend.multiply(MathExtend.divide(mfBusAppFee.getRateScale(),mfBusApply.getTerm()),12),2);
                        }else{
                            termShow = DateUtil.getShowDateTime(mfBusApplySecond.getGuaEndDate());
                            // {担保费率}/{截止日-当前日期}*365（保后小数点后两位，如1.88%）
                            yearRate = MathExtend.round(MathExtend.multiply(MathExtend.divide(mfBusAppFee.getRateScale(),DateUtil.getDay(mfBusApplySecond.getGuaEndDate())),365),2);
                        }
                        // 担保方案
                        String guaPlan = mfBusApply.getGuaPlan();
                        if(StringUtil.isEmpty(guaPlan)){
                            Map<String,String> ext2Map = cu.getMapByKeyName("GUARANTEE_FORM");
                            StringBuilder guaPlanSB = new StringBuilder();
                            guaPlanSB.append("提供").append(MathExtend.moneyStr(mfBusApply.getAppAmt())).append(mfBusApply.getAgenciesName())
                                    .append(mfBusApply.getBreedName()).append(ext2Map.get(mfBusApply.getExt2())).append("担保，期限为保函开立之日起至")
                                    .append(termShow).append("止，项目名称为").append(mfBusApply.getProjectName()).append("，受益人为")
                                    .append(mfBusApply.getExt4()).append("，担保费率（含评审费）为").append(mfBusAppFee.getRateScale()).append("%/次，保函的综合年化收益率约为")
                                    .append(yearRate).append("%，一次性收取。");
                            guaPlan = guaPlanSB.toString();
                            mfBusApply.setGuaPlan(guaPlan);
                        }
                    }
                    // 反担保方案
                    String reverseGuaPlan = mfBusApply.getReverseGuaPlan();
                    if(StringUtil.isEmpty(reverseGuaPlan)){
                        Map<String,String> cusPersRelMap = cu.getMapByKeyName("CUS_PERS_REL");
                        StringBuilder reverseGuaPlanSB = new StringBuilder();
                        StringBuilder perSB = new StringBuilder();
                        StringBuilder corpSB = new StringBuilder();
                        MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
                        mfBusCollateralRel.setAppId(appId);
                        mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
                        if(mfBusCollateralRel != null){
                            MfBusCollateralDetailRel mfBusCollateralDetailRelQuery = new MfBusCollateralDetailRel();
                            mfBusCollateralDetailRelQuery.setBusCollateralId(mfBusCollateralRel.getBusCollateralId());
                            List<MfBusCollateralDetailRel> mfBusCollateralDetailRelList = mfBusCollateralDetailRelFeign.getCollateralDetailRel(mfBusCollateralDetailRelQuery);
                            for (MfBusCollateralDetailRel mfBusCollateralDetailRel: mfBusCollateralDetailRelList) {
                                MfAssureInfo mfAssureInfo = new MfAssureInfo();
                                mfAssureInfo.setId(mfBusCollateralDetailRel.getCollateralId());
                                mfAssureInfo = mfAssureInfoFeign.getById(mfAssureInfo);
                                if(mfAssureInfo != null){
                                    String assureType = mfAssureInfo.getAssureType();
                                    if("2".equals(assureType)){//企业法人担保
                                        perSB.append(cusPersRelMap.get(mfAssureInfo.getRelation())).append(mfAssureInfo.getAssureName()).append("、");
                                    }else if("1".equals(assureType)){
                                        corpSB.append(mfAssureInfo.getAssureName()).append("、");
                                    }
                                }
                            }
                            if(StringUtil.isNotEmpty(perSB.toString())){
                                perSB.deleteCharAt(perSB.length()-1);
                                perSB.append("个人连带责任保证。");
                            }
                            if(StringUtil.isNotEmpty(corpSB.toString())){
                                corpSB.deleteCharAt(corpSB.length()-1);
                                corpSB.append("连带责任保证。");
                            }
                            reverseGuaPlanSB.append(perSB).append(corpSB);
                            mfBusApply.setReverseGuaPlan(reverseGuaPlanSB.toString());
                        }
                    }
                    mfBusApply.setFirstApprovalUser(mfBusApplySecond.getRiskManagePersNo());
                    mfBusApply.setFirstApprovalUserName(mfBusApplySecond.getRiskManagePersName());
                    mfBusApply.setIfUploadData(mfBusApplySecond.getIfUploadData());
                }else if("riskGroupLeader".equals(nodeNo)) {//风险审核人之前的风控组长
                    String riskApprovalPer = mfBusApplySecond.getRiskApprovalPer();
                    if(StringUtil.isEmpty(riskApprovalPer)){
                        String creditAppId = mfBusApply.getCreditAppId();
                        if(StringUtil.isNotEmpty(creditAppId)){
                            MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
                            mfCusCreditApproveInfo.setCreditAppId(creditAppId);
                            mfCusCreditApproveInfo = mfCusCreditApproveInfoFeign.getById(mfCusCreditApproveInfo);
                            riskApprovalPer = mfCusCreditApproveInfo.getRiskApprovalPer();
                        }else{
                            MfBusApply mfBusApply1 = new MfBusApply();
                            mfBusApply1.setCusNo(mfBusApply.getCusNo());
                            riskApprovalPer = mfBusApplyFeign.getLastedByCusNo(mfBusApply1);
                        }
                    }

                    if(StringUtil.isNotEmpty(riskApprovalPer)){
                        SysUser sysUser = new SysUser();
                        sysUser.setOpNo(riskApprovalPer);
                        sysUser = sysUserFeign.getById(sysUser);
                        mfBusApply.setFirstApprovalUser(riskApprovalPer);
                        mfBusApply.setFirstApprovalUserName(sysUser.getOpName());
                    }else{
                        mfBusApply.setFirstApprovalUser(null);
                        mfBusApply.setFirstApprovalUserName(null);
                    }
                }
            }
        }
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        CodeUtils codeUtils = new CodeUtils();

        Double appAmt1 = mfBusApply.getAppAmt();
        String termShow = mfBusApply.getTermShow();
        String cmpdRateType = mfBusApply.getCmpFltRateShow();
        String vouType = mfBusApply.getVouType();
        MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
        mfBusApplyHis.setAppId(appId);
        if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是业务初选审批
            mfBusApplyHis.setPrimaryAppId(mfBusApply.getPrimaryAppId());
        }
        List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
        list = mfBusApplyHisFeign.getListByAppId(mfBusApplyHis);

        if (list != null && list.size() > 0) {
            mfBusApplyHis = list.get(0);
            //上次审批意见类型
            model.addAttribute("lastApproveResult", mfBusApplyHis.getApproveResult());
            PropertyUtils.copyProperties(mfBusApply, mfBusApplyHis);
            mfBusApply = mfBusPactFeign.doBaseRateBusOld(mfBusApply);
        }
        mfBusApply.setAppAmt1(appAmt1);
        mfBusApply.setTermShow(termShow);
        mfBusApply.setCmpFltRateShow(cmpdRateType);
        mfBusApply.setVouType(vouType);
        //获得该申请相关的费用标准信息
        mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(appId, null, null);

        WKF_NODE node = WKF_NODE.apply_approval;
        if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是业务初选审批
            node = WKF_NODE.primary_apply_approval;
        }

        String defFalg = ApplyEnum.BUDGET_DEFFLAG_TYPE.DEFFLAG1.getCode();
        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), node, appId, null, User.getRegNo(request));
        if("credit_firstAppr".equals(nodeNo)){
            MfKindForm mfKindForm = prdctInterfaceFeign.getInitKindForm(mfBusApply.getKindNo(), nodeNo, defFalg);
            if (mfKindForm != null) {
                formId = mfKindForm.getAddModel();
            }
        }
        FormData formapply0003 = formService.getFormData(formId);

        JSONObject json = new JSONObject();
        // 操作员
        List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
        JSONArray userJsonArray = new JSONArray();
        for (int i = 0; i < userList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userList.get(i).getOpNo());
            jsonObject.put("name", userList.get(i).getOpName());
            userJsonArray.add(jsonObject);
        }
        json.put("userJsonArray", userJsonArray);
        //获取产品设置的担保方式
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusApply.getAppId());
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        String kind_vouType = mfBusAppKind.getVouType();
        String[] vouTypeArray = kind_vouType.split("\\|");
        Map<String, String> vouTypeMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
        JSONArray map = new JSONArray();
        for (int j = 0; j < vouTypeArray.length; j++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vouTypeArray[j]);
            jsonObject.put("name", vouTypeMap.get(vouTypeArray[j]));
            map.add(jsonObject);
        }

        //处理其他担保方式
        List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
        MfBusAppKind mfBusAppKindVouType = new MfBusAppKind();
        mfBusAppKindVouType.setAppId(appId);
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
                if( "1".equals(vouTypes[i])){
                    continue;
                }
                OptionsList s= new OptionsList();
                s.setOptionValue(vouTypes[i]);
                s.setOptionLabel(dicMap.get(vouTypes[i]));
                vouTypeOtherList.add(s);
            }
        }
        MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
        mfCusPersBaseInfo.setCusNo(mfBusApply.getCusNo());
        mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
        getObjValue(formapply0003,mfCusPersBaseInfo);
        this.changeFormProperty(formapply0003, "vouTypeOther", "optionArray", vouTypeOtherList);
        json.put("map", map);
        ajaxData = json.toString();
        // 获得客户的授信额度信息
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCusNo(mfBusApply.getCusNo());
        mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
        Double tmpBal = 0.00;
        if (mfCusCreditApply != null) {
            tmpBal = mfCusCreditApply.getCreditSum();
        } else {
            mfCusCreditApply = new MfCusCreditApply();
        }

        //获取核心企业授信可用额度
        mfCusCreditApply.setCusNo(mfBusApply.getCusNoCore());
        mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
        if (mfCusCreditApply == null) {
            mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setAuthBal(0.00);
        } else {
            mfCusCreditApply.setAuthBal(mfCusCreditApply.getCreditSum());
        }
        mfCusCreditApply.setCreditSum(tmpBal);

        //获取反担保方案
        try {
            MfTuningReport mfTuningReport = new MfTuningReport();
            mfTuningReport.setAppId(mfBusApply.getAppId());
            mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
            if (mfTuningReport != null) {
                mfBusApply.setExt1(mfTuningReport.getExt1());//反担保方案
            }
        } catch (Exception e) {
//			logger.error("反担保方案取值展示失败，此处理未影响业务流程流转,",e);
        }
        //获得授信信息
        Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(appId);
        if (!creditData.isEmpty()) {
            getObjValue(formapply0003, creditData);
        }

        getObjValue(formapply0003, mfCusCustomer);// 需要放在mfCusCreditApply之后，mfCusCreditApply有可能是查出的首个核心企业授信，会覆盖掉想要的(如cusType)客户信息
        MfBusApply apply = new MfBusApply();
        apply.setAppId(appId);
        apply = mfBusApplyFeign.getById(mfBusApply);

        //处理逾期利率值和单位展示,
        MfBusAppPenaltyMain mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
        mfBusAppPenaltyMain.setAppId(mfBusApply.getAppId());
        mfBusAppPenaltyMain.setKindNo(mfBusApply.getKindNo());
        mfBusAppPenaltyMain.setPenaltyType("1");
        mfBusAppPenaltyMain = mfBusAppPenaltyMainFeign.getById(mfBusAppPenaltyMain);
        if(mfBusAppPenaltyMain != null){
            String penaltyReceiveType = mfBusAppPenaltyMain.getPenaltyReceiveType();
            mfBusApply.setPenaltyCalRateFinc(mfBusAppPenaltyMain.getPenaltyReceiveValue());
            if("1".equals(penaltyReceiveType)){
                this.changeFormProperty(formapply0003, "penaltyCalRateFinc", "unit","%");
            }else {
                this.changeFormProperty(formapply0003, "penaltyCalRateFinc", "unit","元");
            }
        }
        //提前结清约金利率值和单位展示
        mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
        mfBusAppPenaltyMain.setAppId(mfBusApply.getAppId());
        mfBusAppPenaltyMain.setKindNo(mfBusApply.getKindNo());
        mfBusAppPenaltyMain.setPenaltyType("2");
        mfBusAppPenaltyMain = mfBusAppPenaltyMainFeign.getById(mfBusAppPenaltyMain);
        if (mfBusAppPenaltyMain != null){
            String penaltyReceiveType = mfBusAppPenaltyMain.getPenaltyReceiveType();
            mfBusApply.setEarlyRepayRate(mfBusAppPenaltyMain.getPenaltyReceiveValue());
            if("1".equals(penaltyReceiveType)){
                this.changeFormProperty(formapply0003, "earlyRepayRate", "unit","%");
            }else {
                this.changeFormProperty(formapply0003, "earlyRepayRate", "unit","元");
            }
        }
        String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
        mfBusApply.setOpinionReason(null);
        mfBusApply.setSubOpinionReason(null);
        //处理市场报价利率重新发布提示与计算
        Map<String, Object> calcRateChangeMap = calcRateChange(mfBusApply);
        if ("false".equals(calcRateChangeMap.get("flag"))) {
            String floatNumber = String.valueOf(calcRateChangeMap.get("floatNumber"));
            Double baseRate = Double.valueOf(String.valueOf(calcRateChangeMap.get("baseRate")));
            mfBusApply.setBaseRate(baseRate);
            mfBusApply.setFloatNumber(floatNumber);
            model.addAttribute("baseRateChange", "false");
        } else {
            model.addAttribute("baseRateChange", "true");
        }
        mfBusApply.setAgenciesId1(mfBusApply.getAgenciesId());
        mfBusApply.setAgenciesName1(mfBusApply.getAgenciesName());
        if(BizPubParm.BUS_MODEL_12.equals(busModel)){
            if(BizPubParm.YES_NO_N.equals(mfBusApply.getIsRelCredit())){
                mfBusApply.setBreedNo1(mfBusApply.getBreedNo());
                mfBusApply.setBreedName1(mfBusApply.getBreedName());
            }
        }else{
            mfBusApply.setBreedNo1(mfBusApply.getBreedNo());
            mfBusApply.setBreedName1(mfBusApply.getBreedName());
        }
        getObjValue(formapply0003, mfBusApply);
        // 处理审批意见类型
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        if(!BizPubParm.BUS_MODEL_12.equals(busModel)
                || (BizPubParm.BUS_MODEL_12.equals(busModel) && !"reviewMeet".equals(nodeNo))){
            hideOpinionType = hideOpinionType + "@6.4@6.5";//隐藏有条件同意
        }
        if(BizPubParm.BUS_MODEL_12.equals(busModel) && "reviewMeet".equals(nodeNo)){
            hideOpinionType = hideOpinionType + "@6@4";//隐藏发回重审/退回补充资料
        }
        if(BizPubParm.BUS_MODEL_12.equals(busModel) && "agencyCheck".equals(nodeNo)){
            hideOpinionType = hideOpinionType + "@4";//隐藏发回重审
        }
        if(BizPubParm.BUS_MODEL_12.equals(busModel) && "riskApproval".equals(nodeNo) && BizPubParm.YES_NO_Y.equals(ifChargeback)){//退单回来的只有否决选项
            hideOpinionType = hideOpinionType + "@6@4@1@5";//隐藏发回重审/退回补充资料/同意
        }

        opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
        opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
        opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号

        if (BizPubParm.YES_NO_Y.equals(isPrimary)) {//如果是业务初选审批
            model.addAttribute("approvalNodeNo", "primary_apply_approval");// 初选审批
        } else {
            model.addAttribute("approvalNodeNo", "apply_approval");// 融资审批
        }
        opinionTypeMap.put("opinionType","APPLY_OPINION_TYPE");
        if("comRiskCheck".equals(nodeNo)){
            opinionTypeMap.put("opinionType","RISK_OPINION_TYPE");
        }
        List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(formapply0003, "opinionType", "optionArray", opinionTypeList);

        //添加贷后检查模型数据
        List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
        this.changeFormProperty(formapply0003, "templateId", "optionArray", examList);
        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
        this.changeFormProperty(formapply0003, "term", "unit", termUnit);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        this.changeFormProperty(formapply0003, "replyFincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "cmpdRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "nominalRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "ext2", "initValue", mfBusApply.getExt2());
        //处理还款方式
        List<OptionsList> repayTypeList = getRepayTypeList(mfBusAppKind.getRepayType(), mfBusAppKind.getRepayTypeDef());
        this.changeFormProperty(formapply0003, "repayType", "optionArray", repayTypeList);

        if ("WangXin".equals(sysProjectName) && "1503747716556".equals(nodeNo)) {// 网信 && 审查经理
            // 2017-11-08 begin 网信: 系统规则授信环节，系统计算出的授信额度未显示给 审核经理；
            Double creditQuota = collateralInterfaceFeign.getCreditQuota(appId);
            creditQuota = MathExtend.divide(creditQuota, 10000, 0);// 万位四舍五入
            creditQuota = MathExtend.multiply(creditQuota, 10000);// 万位四舍五入
            this.changeFormProperty(formapply0003, "appAmt", "initValue", creditQuota.toString());
            // 2017-11-08 begin 网信: 系统规则授信环节，系统计算出的授信额度未显示给 审核经理；
        }
        //处理产品子类
        String kindNo = mfBusApply.getKindNo();
        MfSysKind 	mfSysKind  = prdctInterfaceFeign.getSysKindById(kindNo);
        String sunKindNo = mfSysKind.getSubKindNo();
        if(StringUtil.isNotEmpty(sunKindNo)) {
            String[] subKindArray = sunKindNo.split("\\|");
            Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("SUB_KIND_TYPE");
            List<OptionsList> subKindList = new ArrayList<OptionsList>();
            for(int i=0;i<subKindArray.length;i++){
                OptionsList op= new OptionsList();
                op.setOptionLabel(dicMap.get(subKindArray[i]));
                op.setOptionValue(subKindArray[i]);
                op.setOptionId(WaterIdUtil.getWaterId());
                subKindList.add(op);
            }
            this.changeFormProperty(formapply0003, "subKindNo", "optionArray", subKindList);
            this.changeFormProperty(formapply0003, "subKindNo", "initValue", mfBusApply.getSubKindNo());
        }

        //获得当前审批岗位前面审批过得岗位信息
        JSONArray befNodesjsonArray = new JSONArray();
        befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
        JSONArray befNodesjsonArray1 = JSONArray.fromObject(befNodesjsonArray);
        if(befNodesjsonArray != null && befNodesjsonArray.size() > 0){
            for(int i=0;i<befNodesjsonArray.size();i++){
                JSONObject js = (JSONObject)befNodesjsonArray.get(i);
                String nodeName = js.getString("name");
                if("busTravel".equals(nodeName) || "riskAgainApproval".equals(nodeName)){
                    befNodesjsonArray1.remove(js);
                }
                if(nodeNo.equals("riskAgainApproval") && "riskApproval".equals(nodeName)){
                    befNodesjsonArray1.remove(js);
                }
                if(nodeName.equals("riskGroupLeader")){
                    befNodesjsonArray1.remove(js);
                }
            }
        }
        request.setAttribute("befNodesjsonArray", befNodesjsonArray1);
        // 是否展示拒单原因 1-展示 0-不展示
        String approveRefuseType = new CodeUtils().getSingleValByKey("APPROVE_REFUSE_TYPE");
        request.setAttribute("approveRefuseType", approveRefuseType);
        request.setAttribute("appId", appId);
        request.setAttribute("mfBusApply", mfBusApply);
        request.setAttribute("ajaxData", ajaxData);
        request.setAttribute("scNo", scNo);
        model.addAttribute("formapply0003", formapply0003);
        model.addAttribute("query", "");
        model.addAttribute("queryForm", queryForm);
        model.addAttribute("queryFile", queryFile);
        model.addAttribute("isPrimary", isPrimary);
        model.addAttribute("ifAnalysisTable", ifAnalysisTable);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("busModel", busModel);
        model.addAttribute("from", "apply");
        model.addAttribute("ifFirstReviewMeet", ifFirstReviewMeet);
        model.addAttribute("taskId", taskId);
        model.addAttribute("approveType", approveType);
        return "/component/app/wkf/WkfViewPoint";
    }

    /**
     *
     * 方法描述： 进入审批视角（审批页面）,展示客户详情
     * @return
     * @throws Exception
     * String
     * @author 沈浩兵
     * @date 2017-11-17 下午1:43:23
     */
    @RequestMapping(value = "/getViewPointByCusInfo")
    public String getViewPointByCusInfo(Model model, String ajaxData, String appId, String taskId, String hideOpinionType, String activityType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);

        TaskImpl taskAppro = wkfInterfaceFeign.getTask(appId, taskId);// 当前审批节点task
        String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusApply.setReplyFincRate(mfBusApply.getFincRate());
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);


        Double appAmt1 = mfBusApply.getAppAmt();
        String termShow = mfBusApply.getTermShow();
        String cmpdRateType = mfBusApply.getCmpFltRateShow();
        String vouType = mfBusApply.getVouType();
        MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
        mfBusApplyHis.setAppId(appId);
        List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
        list = mfBusApplyHisFeign.getListByAppId(mfBusApplyHis);

        if (list != null && list.size() > 0) {
            mfBusApplyHis = list.get(0);
            if (taskAppro.getActivityName().equals(WKF_NODE.supplement_data.getNodeNo())) {
                String lastApproveType = BizPubParm.APP_STS_SUPPLEMENT;
                request.setAttribute("lastApproveType", lastApproveType);
            }
            PropertyUtils.copyProperties(mfBusApply, mfBusApplyHis);
        }

        mfBusApply.setAppAmt1(appAmt1);
        mfBusApply.setTermShow(termShow);
        mfBusApply.setCmpFltRateShow(cmpdRateType);
        mfBusApply.setVouType(vouType);
        //获得该申请相关的费用标准信息
        mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(appId, null, null);


        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.apply_approval, appId, null, User.getRegNo(request));
        FormData formapply0003 = formService.getFormData(formId);

        // 2017-11-08 begin 网信: 系统规则授信环节，系统计算出的授信额度未显示给 审核经理；
        Double creditQuota = collateralInterfaceFeign.getCreditQuota(appId);
        this.changeFormProperty(formapply0003, "creditQuota", "initValue", creditQuota.toString());
        // 2017-11-08 begin 网信: 系统规则授信环节，系统计算出的授信额度未显示给 审核经理；

        JSONObject json = new JSONObject();
        // 操作员
        List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
        JSONArray userJsonArray = new JSONArray();
        for (int i = 0; i < userList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userList.get(i).getOpNo());
            jsonObject.put("name", userList.get(i).getOpName());
            userJsonArray.add(jsonObject);
        }
        json.put("userJsonArray", userJsonArray);
        //获取产品设置的担保方式
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusApply.getAppId());
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        String kind_vouType = mfBusAppKind.getVouType();
        String[] vouTypeArray = kind_vouType.split("\\|");
        Map<String, String> vouTypeMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
        JSONArray map = new JSONArray();
        for (int j = 0; j < vouTypeArray.length; j++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vouTypeArray[j]);
            jsonObject.put("name", vouTypeMap.get(vouTypeArray[j]));
            map.add(jsonObject);
        }
        json.put("map", map);
        ajaxData = json.toString();
        // 获得客户的授信额度信息
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCusNo(mfBusApply.getCusNo());
        mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
        Double tmpBal = 0.00;
        if (mfCusCreditApply != null) {
            tmpBal = mfCusCreditApply.getCreditSum();
        }

        //获取核心企业授信可用额度
        mfCusCreditApply.setCusNo(mfBusApply.getCusNoCore());
        mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
        if (mfCusCreditApply == null) {
            mfCusCreditApply.setAuthBal(0.00);
        } else {
            mfCusCreditApply.setAuthBal(mfCusCreditApply.getCreditSum());
        }
        mfCusCreditApply.setCreditSum(tmpBal);

        //获取反担保方案
        try {
            MfTuningReport mfTuningReport = new MfTuningReport();
            mfTuningReport.setAppId(mfBusApply.getAppId());
            mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
            if (mfTuningReport != null) {
                mfBusApply.setExt1(mfTuningReport.getExt1());//反担保方案
            }
        } catch (Exception e) {
//			logger.error("反担保方案取值展示失败，此处理未影响业务流程流转,",e);
        }
        getObjValue(formapply0003, mfCusCreditApply);
        getObjValue(formapply0003, mfBusApply);

        // 处理审批意见类型
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
        opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
        List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(formapply0003, "opinionType", "optionArray", opinionTypeList);

        //添加贷后检查模型数据
        List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
        this.changeFormProperty(formapply0003, "templateId", "optionArray", examList);
        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
        this.changeFormProperty(formapply0003, "term", "unit", termUnit);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        this.changeFormProperty(formapply0003, "replyFincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "cmpdRate", "unit", rateUnit);
        //处理还款方式
        List<OptionsList> repayTypeList = getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
        this.changeFormProperty(formapply0003, "repayType", "optionArray", repayTypeList);
        //获得当前审批岗位前面审批过得岗位信息
        JSONArray befNodesjsonArray = new JSONArray();
        befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
        request.setAttribute("befNodesjsonArray", befNodesjsonArray);
        request.setAttribute("appId", appId);
        model.addAttribute("formapply0003", formapply0003);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("scNo", scNo);
        model.addAttribute("query", "");
        return "/component/app/wkf/WkfViewPointByCusInfo";
    }

    /**
     *
     * 方法描述： 获得补充资料表单html
     * @return
     * @throws Exception
     * String
     * @author 沈浩兵
     * @date 2017-9-3 下午3:13:56
     */
    @RequestMapping(value = "/getApproveFormDataAjax")
    @ResponseBody
    public Map<String, Object> getApproveFormDataAjax(String ajaxData, String appId, String activityType,String isPrimary) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        if("1".equals(isPrimary)){
        	appId = mfBusApply.getPrimaryAppId();
        }
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(appId, null);// 当前审批节点task
        String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

        
        mfBusApply.setReplyFincRate(mfBusApply.getFincRate());
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);


        Double appAmt1 = mfBusApply.getAppAmt();
        String termShow = mfBusApply.getTermShow();
        String cmpdRateType = mfBusApply.getCmpFltRateShow();

        mfBusApply.setAppAmt1(appAmt1);
        mfBusApply.setTermShow(termShow);
        mfBusApply.setCmpFltRateShow(cmpdRateType);
        //获得该申请相关的费用标准信息
        mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(appId, null, null);


        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.supplement_data, appId, null, User.getRegNo(request));
        FormData formapply0003 = formService.getFormData(formId);
        JSONObject json = new JSONObject();
        // 操作员
        List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
        JSONArray userJsonArray = new JSONArray();
        for (int i = 0; i < userList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userList.get(i).getOpNo());
            jsonObject.put("name", userList.get(i).getOpName());
            userJsonArray.add(jsonObject);
        }
        json.put("userJsonArray", userJsonArray);
        //获取产品设置的担保方式
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusApply.getAppId());
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        String vouType = mfBusAppKind.getVouType();
        String[] vouTypeArray = vouType.split("\\|");
        Map<String, String> vouTypeMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
        JSONArray map = new JSONArray();
        for (int j = 0; j < vouTypeArray.length; j++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vouTypeArray[j]);
            jsonObject.put("name", vouTypeMap.get(vouTypeArray[j]));
            map.add(jsonObject);
        }
        dataMap.put("map", map);

        //处理其他担保方式
        List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
        if(StringUtil.isEmpty(vouType)){
            List<ParmDic> pdl = (List<ParmDic>)new CodeUtils().getCacheByKeyName("VOU_TYPE");
            for(ParmDic parmDic:pdl){
                OptionsList s= new OptionsList();
                s.setOptionLabel(parmDic.getOptName());
                s.setOptionValue(parmDic.getOptCode());
                vouTypeOtherList.add(s);
            }
        }else{
            Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
            String[] vouTypes = vouType.split("\\|");
            for(int i=0;i<vouTypes.length;i++){
                OptionsList s= new OptionsList();
                s.setOptionValue(vouTypes[i]);
                s.setOptionLabel(dicMap.get(vouTypes[i]));
                vouTypeOtherList.add(s);
            }
        }
        this.changeFormProperty(formapply0003, "vouTypeOther", "optionArray", vouTypeOtherList);


        // 获得客户的授信额度信息
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        MfCusCreditApply mfCusCreditApplyTmp = new MfCusCreditApply();
        mfCusCreditApplyTmp.setCusNo(mfBusApply.getCusNo());
        mfCusCreditApplyTmp = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApplyTmp);
        Double tmpBal = 0.00;
        if (mfCusCreditApplyTmp != null) {
            tmpBal = mfCusCreditApplyTmp.getCreditSum();
        }

        //获取核心企业授信可用额度
        mfCusCreditApplyTmp = new MfCusCreditApply();
        mfCusCreditApplyTmp.setCusNo(mfBusApply.getCusNoCore());
        mfCusCreditApplyTmp = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApplyTmp);
        if (mfCusCreditApplyTmp == null) {
            mfCusCreditApply.setAuthBal(0.00);
        } else {
            mfCusCreditApply.setAuthBal(mfCusCreditApplyTmp.getCreditSum());
        }
        mfCusCreditApply.setCreditSum(tmpBal);
        getObjValue(formapply0003, mfCusCreditApply);
        getObjValue(formapply0003, mfBusApply);

        //处理审批意见类型
        List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), null);

        this.changeFormProperty(formapply0003, "opinionType", "optionArray", opinionTypeList);
        //添加贷后检查模型数据
        List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
        this.changeFormProperty(formapply0003, "templateId", "optionArray", examList);
        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
        this.changeFormProperty(formapply0003, "term", "unit", termUnit);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        this.changeFormProperty(formapply0003, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "cmpdRate", "unit", rateUnit);
        //处理还款方式
        List<OptionsList> repayTypeList = getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
        this.changeFormProperty(formapply0003, "repayType", "optionArray", repayTypeList);
        //获得当前审批岗位前面审批过得岗位信息
        JSONArray befNodesjsonArray = new JSONArray();
        befNodesjsonArray = wkfInterfaceFeign.getBefNodes(appId, null);
        request.setAttribute("befNodesjsonArray", befNodesjsonArray);

        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        String htmlStr = jsonFormUtil.getJsonStr(formapply0003, "bootstarpTag", "");
        dataMap.put("htmlStr", htmlStr);
        return dataMap;
    }

    /**
     * 方法描述：  进入审批视角-审批页面（注：资金机构节点使用）
     * @return
     * @throws Exception
     * String
     * @author Javelin
     * @date 2017-7-30 上午10:45:40
     */
    @RequestMapping(value = "/getAgencyViewPoint")
    public String getAgencyViewPoint(Model model, String ajaxData, String appId, String activityType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(appId, null);// 当前审批节点task
        String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusApply.setReplyFincRate(mfBusApply.getFincRate());
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);


        Double appAmt1 = mfBusApply.getAppAmt();
        String termShow = mfBusApply.getTermShow();
        MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
        mfBusApplyHis.setAppId(appId);
        List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
        list = mfBusApplyHisFeign.getListByAppId(mfBusApplyHis);

        if (list != null && list.size() > 0) {
            mfBusApplyHis = list.get(0);
            PropertyUtils.copyProperties(mfBusApply, mfBusApplyHis);
        }

        mfBusApply.setAppAmt1(appAmt1);
        mfBusApply.setTermShow(termShow);

        //获得该申请相关的费用标准信息
        mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(appId, null, null);
        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.apply_approval, appId, null, User.getRegNo(request));
        FormData formapply0003 = formService.getFormData(formId);
        JSONObject json = new JSONObject();
        // 操作员
        List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
        JSONArray userJsonArray = new JSONArray();
        for (int i = 0; i < userList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userList.get(i).getOpNo());
            jsonObject.put("name", userList.get(i).getOpName());
            userJsonArray.add(jsonObject);
        }
        json.put("userJsonArray", userJsonArray);
        JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
        for (int i = 0; i < map.size(); i++) {
            map.getJSONObject(i).put("id", map.getJSONObject(i).getString("optCode"));
            map.getJSONObject(i).put("name", map.getJSONObject(i).getString("optName"));
        }
        json.put("map", map);
        ajaxData = json.toString();
        // 获得客户的授信额度信息
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCusNo(mfBusApply.getCusNo());
        mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
        Double tmpBal = 0.00;
        if (mfCusCreditApply != null) {
            tmpBal = mfCusCreditApply.getCreditSum();
        }

        //获取核心企业授信可用额度
        mfCusCreditApply.setCusNo(mfBusApply.getCusNoCore());
        mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
        if (mfCusCreditApply == null) {
            mfCusCreditApply.setAuthBal(0.00);
        } else {
            mfCusCreditApply.setAuthBal(mfCusCreditApply.getCreditSum());
        }
        mfCusCreditApply.setCreditSum(tmpBal);
        getObjValue(formapply0003, mfCusCreditApply);
        getObjValue(formapply0003, mfBusApply);

        //处理审批意见类型
        List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), null);
        this.changeFormProperty(formapply0003, "opinionType", "optionArray", opinionTypeList);

        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
        this.changeFormProperty(formapply0003, "term", "unit", termUnit);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        this.changeFormProperty(formapply0003, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "cmpdRate", "unit", rateUnit);
        //获得当前审批岗位前面审批过得岗位信息
        JSONArray befNodesjsonArray = new JSONArray();
        befNodesjsonArray = wkfInterfaceFeign.getBefNodes(appId, null);
        request.setAttribute("befNodesjsonArray", befNodesjsonArray);
        request.setAttribute("appId", appId);
        model.addAttribute("formapply0003", formapply0003);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("scNo", scNo);
        model.addAttribute("query", "");
        return "/component/app/wkf/WkfAgencyViewPoint";
    }

    /**
     *
     * 方法描述： 审批提交（审批意见保存）
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2016-5-26 上午10:53:17
     */

    @RequestMapping(value = "/submitUpdate")
    public String submitUpdate(Model model, String ajaxData, String appId, String taskId, String opinionType, String approvalOpinion, String transition, String nextUser, String activityType) throws Exception {
        ActionContext.initialize(request, response);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        Result res;
        Map<String, Object> parmMap1 = new HashMap<String, Object>();
        parmMap1.put("mfBusApply", mfBusApply);
        parmMap1.put("mfBusAppFeeList", mfBusAppFeeList);
        parmMap1.put("approvalOpinion", approvalOpinion);
        res = mfBusApplyWkfFeign.doCommit(taskId, appId, opinionType, transition, User.getRegNo(this.getHttpRequest()), nextUser, parmMap1);
        if (!res.isSuccess()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("appId", appId);
            ViewUtil.setViewPointParm(request, map);
            HashMap<String, String> otMap = new HashMap<String, String>();
            if ("signtask".equals(activityType)) {
                otMap.put("1", "同意");
                otMap.put("5", "不同意");
            } else {
                otMap.put("1", "同意");
                otMap.put("3", "退回上一环节");
                otMap.put("4", "退回初审");
                otMap.put("2", "否决");
            }
            model.addAttribute("query", "");
            return "input";
        }
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_List";

    }

    /**
     *
     * 方法描述： 审批提交（审批意见保存）
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2016-5-26 上午10:53:17t
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/submitUpdateAjax")
    @ResponseBody
    public Map<String, Object> submitUpdateAjax(String ajaxData, String ajaxDataList, String taskId, String appId, String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //判断当前节点是否可进行审批
//        TaskImpl taskApprove = wkfInterfaceFeign.getTask(appId, "");
        String appOpNo = User.getRegNo(request);
        TaskImpl taskApprove = wkfInterfaceFeign.getTaskWithUser(appId, "",appOpNo);
        if(!taskId.equals(String.valueOf(taskApprove.getDbid()))){//不相等则审批不在此环节
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
            return dataMap;
        }
        Map map = getMapByJson(ajaxData);
        FormData formapply0003 = formService.getFormData((String) map.get("formId"));
        getFormValue(formapply0003, map);
        MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId((String) map.get("appId"));// 申请信息

        setObjValue(formapply0003, mfBusApply);
        String busModel = mfBusApply.getBusModel();
        if("0".equals(mfBusApply.getIsRelCredit())){
            mfBusApply.setAgenciesId(mfBusApply.getAgenciesId1());
            mfBusApply.setAgenciesName(mfBusApply.getAgenciesName1());
            mfBusApply.setBreedNo(mfBusApply.getBreedNo1());
            mfBusApply.setBreedName(mfBusApply.getBreedName1());
        }
        if(!BizPubParm.BUS_MODEL_12.equals(busModel)){
            //检验期限
            if(StringUtil.isNotEmpty(mfBusApply.getCreditAppId())){
                MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
                mfCusAgenciesCredit.setCreditAppId(mfBusApply.getCreditAppId());
                mfCusAgenciesCredit.setAgenciesId(mfBusApply.getAgenciesId());
                mfCusAgenciesCredit = mfCusCreditApplyFeign.getMfCusAgenciesCreditById(mfCusAgenciesCredit);
                if(mfCusAgenciesCredit!=null){
                    String potoutBeginDate = mfCusAgenciesCredit.getPotoutBeginDate();
                    if(StringUtil.isNotEmpty(potoutBeginDate)){
                        String nowDate = DateUtil.getDate();
                        int[] monthsAndDays =   DateUtil.getMonthsAndDays(nowDate,potoutBeginDate);
                        if(monthsAndDays!=null){
                            int months = monthsAndDays[1];
                            int term1 = months+mfCusAgenciesCredit.getExtendTerm();
                            if(mfBusApply.getTerm()>term1){
                                dataMap.put("flag", "error");
                                dataMap.put("msg", "申请期限不能大于合作银行提款期，延长期之和！");
                                return dataMap;
                            }
                        }
                    }else{
                        int term1 = mfCusAgenciesCredit.getPutoutTerm()+mfCusAgenciesCredit.getExtendTerm();
                        if(mfBusApply.getTerm()>term1){
                            dataMap.put("flag", "error");
                            dataMap.put("msg", "申请期限不能大于合作银行提款期，延长期之和！");
                            return dataMap;
                        }
                    }
                }
            }
        }
        dataMap = getMapByJson(ajaxData);
        String opinionType = String.valueOf(dataMap.get("opinionType"));
        String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
        JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
        mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(), new JsonConfig());
        try {
            mfBusApply = mfLoanApplyFeign.disProcessDataForApply(mfBusApply);
            new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusApply);
            dataMap.put("mfBusApply", mfBusApply);
            dataMap.put("mfBusAppFeeList", mfBusAppFeeList);
            dataMap.put("approvalOpinion", approvalOpinion);
            TaskImpl taskAppro = wkfInterfaceFeign.getTask(appId, taskId);
            Result res = mfBusApplyWkfFeign.doCommit(taskId, appId, opinionType, transition, User.getRegNo(request), nextUser, dataMap);
            if (res.isSuccess()) {
                if (StringUtil.isNotBlank(mfBusApply.getInfoOffer())) {
                    MfCusCustomer mfCusCustomer = new MfCusCustomer();
                    mfCusCustomer.setCusNo(mfBusApply.getCusNo());
                    mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

                    if (BizPubParm.CUS_TYPE_PERS.equals(mfCusCustomer.getCusBaseType())) {// 客户类型CUS_TYPE - 2 个人客户
                        MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
                        mfCusPersBaseInfo.setCusNo(mfBusApply.getCusNo());
                        mfCusPersBaseInfo.setInfoOffer(mfBusApply.getInfoOffer());
                        mfCusPersBaseInfoFeign.update(mfCusPersBaseInfo);
                    } else if (BizPubParm.CUS_TYPE_CORP.equals(mfCusCustomer.getCusBaseType())) {// 客户类型CUS_TYPE - 1 对公客户
                        MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
                        mfCusCorpBaseInfo.setCusNo(mfBusApply.getCusNo());
                        mfCusCorpBaseInfo.setInfoOffer(mfBusApply.getInfoOffer());
                        mfCusCorpBaseInfoFeign.update(mfCusCorpBaseInfo);
                    }else {
                    }
                }
                dataMap.put("flag", "success");
                if (res.isEndSts()) {
                    // 业务审批通过之后提交业务到下一个阶段，此处代码为 app.component.app.feign.impl.MfBusApplyBoImpl.submitApplyApprovalPass业务中迁出
                    Result result = wkfBusInterfaceFeign.doCommitNextStepWithOpNo(mfBusApply.getAppId(), mfBusApply.getWkfAppId(), User.getRegNo(request));
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
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
            throw e;
        }
        return dataMap;

    }

    
    /**
     *
     * 方法描述： 审批提交（审批意见保存）
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2016-5-26 上午10:53:17
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/submitUpdateForPrimaryAjax")
    @ResponseBody
    public Map<String, Object> submitUpdateForPrimaryAjax(String ajaxData, String ajaxDataList, String taskId, String appId, String primaryAppId, String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //判断当前节点是否可进行审批
        TaskImpl taskApprove = wkfInterfaceFeign.getTask(primaryAppId, "");
        if(!taskId.equals(String.valueOf(taskApprove.getDbid()))){//不相等则审批不在此环节
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
            return dataMap;
        }
        Map map = getMapByJson(ajaxData);
        FormData formapply0003 = formService.getFormData((String) map.get("formId"));
        getFormValue(formapply0003, map);
        MfBusApply mfBusApply = new MfBusApply();
        setObjValue(formapply0003, mfBusApply);
        dataMap = getMapByJson(ajaxData);
        String opinionType = String.valueOf(dataMap.get("opinionType"));
        String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
        JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
        mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(), new JsonConfig());
        try {
            mfBusApply = mfLoanApplyFeign.disProcessDataForApply(mfBusApply);
            new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusApply);
            dataMap.put("mfBusApply", mfBusApply);
            dataMap.put("mfBusAppFeeList", mfBusAppFeeList);
            Result res = mfBusApplyWkfFeign.doCommitForPrimary(taskId, appId, primaryAppId, opinionType, approvalOpinion, transition, User.getRegNo(request), nextUser, dataMap);
            if (res.isSuccess()) {
                dataMap.put("flag", "success");
                if (res.isEndSts()) {
                    // 业务审批通过之后提交业务到下一个阶段，此处代码为 app.component.app.feign.impl.MfBusApplyBoImpl.submitApplyApprovalPass业务中迁出
                    Result result = wkfBusInterfaceFeign.doCommitNextStepWithOpNo(mfBusApply.getAppId(), mfBusApply.getWkfAppId(), User.getRegNo(request));
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
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
            throw e;
        }
        return dataMap;

    }

    /**
     *
     * 方法描述： 获取还款方式选择项列表
     * @param repayType
     * @return
     * @throws Exception
     * List<OptionsList>
     * @author zhs
     * @date 2017-7-19 下午4:08:35
     */
    private List<OptionsList> getRepayTypeList(String repayType, String repayTypeDef) throws Exception {
        String[] repayTypeArray = repayType.split("\\|");
        Map<String, String> dicMap = new CodeUtils().getMapByKeyName("REPAY_TYPE");
        List<OptionsList> repayTypeList = new ArrayList<OptionsList>();
        if (StringUtil.isNotEmpty(repayTypeDef)) {
            OptionsList op = new OptionsList();
            op.setOptionLabel(dicMap.get(repayTypeDef));
            op.setOptionValue(repayTypeDef);
            repayTypeList.add(op);
        }
        for (int i = 0; i < repayTypeArray.length; i++) {
            if (StringUtil.isNotEmpty(repayTypeDef)) {
                if (repayTypeArray[i].equals(repayTypeDef)) {
                    continue;
                }
            }
            OptionsList op = new OptionsList();
            op.setOptionLabel(dicMap.get(repayTypeArray[i]));
            op.setOptionValue(repayTypeArray[i]);
            repayTypeList.add(op);
        }
        return repayTypeList;
    }


    /**
     *
     * 方法描述：审批评估页面(网信项目专用)
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2016-5-26 上午10:26:55
     */
    @RequestMapping(value = "/getEvalViewPoint")
    public String getEvalViewPoint(Model model, String ajaxData, String appId, String taskId, String hideOpinionType, String activityType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);

        TaskImpl taskAppro = wkfInterfaceFeign.getTask(appId, taskId);// 当前审批节点task
        String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusApply.setReplyFincRate(mfBusApply.getFincRate());
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);


        Double appAmt1 = mfBusApply.getAppAmt();
        String termShow = mfBusApply.getTermShow();
        String cmpdRateType = mfBusApply.getCmpFltRateShow();
        MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
        mfBusApplyHis.setAppId(appId);
        List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
        list = mfBusApplyHisFeign.getListByAppId(mfBusApplyHis);

        if (list != null && list.size() > 0) {
            mfBusApplyHis = list.get(0);
            if (taskAppro.getActivityName().equals(WKF_NODE.supplement_data.getNodeNo())) {
                String lastApproveType = BizPubParm.APP_STS_SUPPLEMENT;
                request.setAttribute("lastApproveType", lastApproveType);
            }
            PropertyUtils.copyProperties(mfBusApply, mfBusApplyHis);
        }

        mfBusApply.setAppAmt1(appAmt1);
        mfBusApply.setTermShow(termShow);
        mfBusApply.setCmpFltRateShow(cmpdRateType);
        //获得该申请相关的费用标准信息
        mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(appId, null, null);


        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.apply_approval, appId, null, User.getRegNo(request));
        FormData formapply0003 = formService.getFormData(formId);
        JSONObject json = new JSONObject();
        // 操作员
        List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
        JSONArray userJsonArray = new JSONArray();
        for (int i = 0; i < userList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userList.get(i).getOpNo());
            jsonObject.put("name", userList.get(i).getOpName());
            userJsonArray.add(jsonObject);
        }
        json.put("userJsonArray", userJsonArray);
        //获取产品设置的担保方式
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusApply.getAppId());
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        String vouType = mfBusAppKind.getVouType();
        String[] vouTypeArray = vouType.split("\\|");
        Map<String, String> vouTypeMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
        JSONArray map = new JSONArray();
        for (int j = 0; j < vouTypeArray.length; j++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vouTypeArray[j]);
            jsonObject.put("name", vouTypeMap.get(vouTypeArray[j]));
            map.add(jsonObject);
        }
        json.put("map", map);
        ajaxData = json.toString();
        // 获得客户的授信额度信息
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCusNo(mfBusApply.getCusNo());
        mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
        Double tmpBal = 0.00;
        if (mfCusCreditApply != null) {
            tmpBal = mfCusCreditApply.getCreditSum();
        }

        //获取核心企业授信可用额度
        mfCusCreditApply.setCusNo(mfBusApply.getCusNoCore());
        mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
        if (mfCusCreditApply == null) {
            mfCusCreditApply.setAuthBal(0.00);
        } else {
            mfCusCreditApply.setAuthBal(mfCusCreditApply.getCreditSum());
        }
        mfCusCreditApply.setCreditSum(tmpBal);
        getObjValue(formapply0003, mfCusCreditApply);
        getObjValue(formapply0003, mfBusApply);

        //隐藏审批类型
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        opinionTypeMap.put("hideOpinionType", hideOpinionType);

        //处理审批意见类型
        List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);

        this.changeFormProperty(formapply0003, "opinionType", "optionArray", opinionTypeList);
        //添加贷后检查模型数据
        List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
        this.changeFormProperty(formapply0003, "templateId", "optionArray", examList);
        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
        this.changeFormProperty(formapply0003, "term", "unit", termUnit);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        this.changeFormProperty(formapply0003, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0003, "cmpdRate", "unit", rateUnit);
        //处理还款方式
        List<OptionsList> repayTypeList = getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
        this.changeFormProperty(formapply0003, "repayType", "optionArray", repayTypeList);
        //获得当前审批岗位前面审批过得岗位信息
        JSONArray befNodesjsonArray = new JSONArray();
        befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
        request.setAttribute("befNodesjsonArray", befNodesjsonArray);
        request.setAttribute("appId", appId);
        model.addAttribute("formapply0003", formapply0003);
        model.addAttribute("query", "");
        return "/component/app/wkf/WkfEvalViewPoint";
    }


    /**
     *
     * 方法描述： 审批评估页面(网信项目专用)
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2016-5-26 上午10:53:17
     */
    @RequestMapping(value = "/submitEvalUpdateAjax")
    @ResponseBody
    public Map<String, Object> submitEvalUpdateAjax(String ajaxData, String ajaxDataList, String taskId, String appId, String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map map = getMapByJson(ajaxData);
        FormData formapply0003 = formService.getFormData((String) map.get("formId"));
        getFormValue(formapply0003, map);
        MfBusApply mfBusApply = new MfBusApply();
        setObjValue(formapply0003, mfBusApply);
        //给评估信息赋值
        EvalInfo evalInfo = new EvalInfo();
        setObjValue(formapply0003, evalInfo);

        dataMap = getMapByJson(ajaxData);
        String opinionType = String.valueOf(dataMap.get("opinionType"));
        String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
        JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
        mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(), new JsonConfig());
        try {
            mfBusApply = mfLoanApplyFeign.disProcessDataForApply(mfBusApply);
            Result res = mfBusApplyWkfFeign.submitEvalApprove(taskId, appId, opinionType, approvalOpinion, transition, User.getRegNo(this.getHttpRequest()), nextUser, mfBusApply, mfBusAppFeeList, dataMap, evalInfo);
            if (res.isSuccess()) {
                dataMap.put("flag", "success");
                if (res.isEndSts()) {
                    // 业务审批通过之后提交业务到下一个阶段，此处代码为 app.component.app.feign.impl.MfBusApplyBoImpl.submitApplyApprovalPass业务中迁出
                    Result result = wkfBusInterfaceFeign.doCommitNextStepWithUser(mfBusApply.getAppId(), mfBusApply.getWkfAppId(), User.getRegNo(request));
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
    private Map<String, Object> calcRateChange(MfBusApply mfBusApply) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            Map<String, Object> parmMap = new HashMap<>();
            if (BizPubParm.BASE_RATE_TYPE4.equals(mfBusApply.getBaseRateType())) {
                parmMap.put("rateNo", mfBusApply.getLprNumber());
                dataMap = mfBusApplyFeign.getLprRateByRateNo(parmMap);
                if ("success".equals(dataMap.get("flag"))) {
                    String baseRateNew = String.valueOf(dataMap.get("baseRate"));
                    String baseRateOld = String.valueOf(mfBusApply.getBaseRate());
                    if (MathExtend.comparison(baseRateOld, baseRateNew) == 0) {
                        dataMap.put("flag", "true");
                    } else {
                        String fincRate = String.valueOf(mfBusApply.getFincRate());
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
