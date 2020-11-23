package app.component.auth.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.feign.MfAssureInfoFeign;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.auth.entity.*;
import app.component.auth.feign.MfCusCreditAdjustApplyFeign;
import app.component.auth.feign.MfCusCreditModelFeign;
import app.component.auth.feign.MfCusCreditApplyHisFeign;
import app.component.auth.feign.MfCusCreditUseHisFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusPorductCreditFeign;
import app.component.auth.feign.MfCusCreditContractDetailFeign;
import app.component.auth.feign.MfCusCreditInterfaceFeign;
import app.component.auth.feign.MfCusCreditApproveInfoFeign;
import app.component.auth.feign.MfCreditProjectPassSignFeign;
import app.component.auth.feign.MfCreditFrozenThawFeign;
import app.component.auth.feign.MfCusCreditConfigFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.calc.core.entity.MfBusFeePlan;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.feign.MfBusFeePlanFeign;
import app.component.calc.core.feign.MfRepayHistoryFeign;
import app.component.calc.fee.entity.MfSysFeeItem;
import app.component.calc.fee.feign.MfSysFeeItemFeign;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.channel.feign.MfChannelBusFeign;
import app.component.channel.fund.entity.MfFundChannelFinc;
import app.component.channel.fund.entity.MfFundChannelRepayHistory;
import app.component.channel.fund.entity.MfFundChannelRepayPlan;
import app.component.collateral.entity.*;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.PledgeBaseInfoBillFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.compensatory.entity.MfBusCompensatoryConfirm;
import app.component.compensatory.feign.MfBusCompensatoryConfirmFeign;
import app.component.cus.cooperating.entity.MfCusCooperativeAgency;
import app.component.cus.cooperating.feign.MfCusCooperativeAgencyFeign;
import app.component.cus.cusgroup.entity.MfCusGroup;
import app.component.cus.cusgroup.feign.MfCusGroupFeign;
import app.component.cus.cuslevel.entity.MfCusClassify;
import app.component.cus.cuslevel.feign.MfCusClassifyFeign;
import app.component.cus.dishonestinfo.entity.MfCusDishonestInfo;
import app.component.cus.dishonestinfo.feign.MfCusDishonestInfoFeign;
import app.component.cus.entity.*;
import app.component.cus.execnotice.entity.MfCusExecNotice;
import app.component.cus.execnotice.feign.MfCusExecNoticeFeign;
import app.component.cus.feign.*;
import app.component.cus.relation.feign.MfCusRelationFeign;
import app.component.cus.report.entity.MfCusReportAcount;
import app.component.cus.report.feign.MfCusReportAcountFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.doc.entity.DocBizManageParam;
import app.component.docinterface.DocInterfaceFeign;
import app.component.eval.feign.AppEvalFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.entity.MfBusPactExtend;
import app.component.pact.feign.MfBusPactExtendFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.pledge.entity.MfBusFund;
import app.component.pledge.entity.MfBusFundDetail;
import app.component.pledge.feign.MfBusFundDetailFeign;
import app.component.pledge.feign.MfBusFundFeign;
import app.component.pledgeinterface.PledgeInterfaceFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.entity.MfKindNodeFee;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfKindNodeFeeFeign;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.recourse.feign.MfBusRecourseConfirmFeign;
import app.component.risk.entity.RiskBizItemRel;
import app.component.riskinterface.RiskInterfaceFeign;
import app.component.rules.entity.NumberBigBean;
import app.component.rules.feign.NumberDataFeign;
import app.component.thirdRecord.feign.MfFundChannelRepayHistoryFeign;
import app.component.thirdRecord.feign.MfFundChannelRepayPlanFeign;
import app.component.thirdservice.esign.feign.EsignInterfaceFeign;
import app.component.tuning.feign.MfCusCreditTuningDetailFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.TaskFeign;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.DataUtil;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.HttpClientUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.ListIterator;

/**
 * Title: MfCusCreditApplyAction.java
 * Description:授信申请视图控制层
 *
 * @author:LJW
 * @Mon Feb 27 10:43:09 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCreditApply")
public class MfCusCreditApplyController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    //注入MfCusCreditApplyBo
    @Autowired
    private MfCusCreditApplyFeign mfCusCreditApplyFeign;
    @Autowired
    private MfCusCooperativeAgencyFeign mfCusCooperativeAgencyFeign;
    @Autowired
    private MfCusPorductCreditFeign mfCusPorductCreditFeign;
    @Autowired
    private MfBusFundFeign mfBusFundFeign;//惠农贷基金
    @Autowired
    private MfBusFundDetailFeign mfBusFundDetailFeign;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign; //客户业务操作
    @Autowired
    private MfCusCreditModelFeign mfCusCreditModelFeign;  //授信配置业务控制操作
    @Autowired
    private MfCusCreditContractFeign mfCusCreditContractFeign;    //授信协议业务控制
    @Autowired
    private MfCusCreditApplyHisFeign mfCusCreditApplyHisFeign;
    @Autowired
    private PledgeInterfaceFeign pledgeInterfaceFeign;  //押品业务控制
    @Autowired
    private MfCusTypeFeign mfCusTypeFeign; // 客户类别
    @Autowired
    private DocInterfaceFeign docInterfaceFeign; //要件操作
    @Autowired
    private MfCusCreditAdjustApplyFeign mfCusCreditAdjustApplyFeign;
    //流程控制
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;

    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;  //产品对外接口

    @Autowired
    private WorkflowDwrFeign workflowDwrFeign;
    @Autowired
    private EsignInterfaceFeign esignInterfaceFeign;
    @Autowired
    private TaskFeign taskFeign;

    @Autowired
    private MfCusCreditContractDetailFeign mfCusCreditContractDetailFeign;
    @Autowired
    private MfCusCreditInterfaceFeign mfCusCreditInterfaceFeign;
    @Autowired
    private BusViewInterfaceFeign busViewInterfaceFeign;
    @Autowired
    private RiskInterfaceFeign riskInterfaceFeign;
    @Autowired
    private MfCusClassifyFeign mfCusClassifyFeign;
    @Autowired
    private MfCusRelationFeign mfCusRelationFeign;

    @Autowired
    private MfCusCreditApproveInfoFeign mfCusCreditApproveInfoFeign;

    @Autowired
    private MfCusCreditUseHisFeign mfCusCreditUseHisFeign;

    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfBusRecourseConfirmFeign mfBusRecourseConfirmFeign;
    @Autowired
    private MfRepayHistoryFeign mfRepayHistoryFeign;
    @Autowired
    private MfBusFeePlanFeign mfBusFeePlanFeign;
    @Autowired
    private MfFundChannelRepayHistoryFeign mfFundChannelRepayHistoryFeign;
    @Autowired
    private MfFundChannelRepayPlanFeign mfFundChannelRepayPlanFeign;
    @Autowired
    private AppEvalFeign appEvalFeign;
    @Autowired
    private MfAssureInfoFeign assureInfoFeign;
    @Autowired
    private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
    @Autowired
    private MfBusCollateralRelFeign mfBusCollateralRelFeign;
    @Autowired
    private MfChannelBusFeign mfChannelBusFeign;
    @Autowired
    private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
    @Autowired
    private MfCusGroupFeign mfCusGroupFeign;

    @Autowired
    private MfBusCompensatoryConfirmFeign   mfBusCompensatoryConfirmFeign;
    @Autowired
    private MfCreditProjectPassSignFeign mfCreditProjectPassSignFeign;
    @Autowired
    private NumberDataFeign numberDataFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private MfCreditFrozenThawFeign mfCreditFrozenThawFeign;
    @Autowired
    private MfCusCreditConfigFeign mfCusCreditConfigFeign;
    @Autowired
    private CalcInterfaceFeign calcInterfaceFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private MfBusAgenciesFeign mfBusAgenciesFeign;
    @Autowired
    private MfCusDishonestInfoFeign mfCusDishonestInfoFeign;
    @Autowired
    private MfCusExecNoticeFeign mfCusExecNoticeFeign;
    @Autowired
    private MfSysFeeItemFeign mfSysFeeItemFeign;
    @Autowired
    private MfBusPactExtendFeign mfBusPactExtendFeign;
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;
    @Autowired
    MfCusCreditTuningDetailFeign mfCusCreditTuningDetailFeign;
    @Autowired
    private MfCusReportAcountFeign mfCusReportAcountFeign;
    @Autowired
    private MfCusHighInfoFeign mfCusHighInfoFeign;
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
    @Autowired
    private CollateralInterfaceFeign collateralInterfaceFeign;
    @Autowired
    private PledgeBaseInfoFeign pledgeBaseInfoFeign;
    @Autowired
    private PledgeBaseInfoBillFeign pledgeBaseInfoBillFeign;
    @Autowired
    private AssureInterfaceFeign assureInterfaceFeign;
    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        return "/component/auth/MfCusCreditApply_List";
    }
    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            mfCusCreditApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusCreditApply.setCriteriaList(mfCusCreditApply, ajaxData);//我的筛选
            //this.getRoleConditions(mfCusCreditApply,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            ipage = mfCusCreditApplyFeign.findByPage(ipage, mfCusCreditApply);
            JsonTableUtil jtu = new JsonTableUtil();
            String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage",ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 新增授信(惠农贷)
     *
     * @param kindNos
     * @param kindNames
     * @param creditAmts
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjaxHND")
    @ResponseBody
    public Map <String, Object> insertAjaxHND(String ajaxData, String ajaxDataList) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            Map <String, Object> map = getMapByJson(ajaxData);
            String formId = map.get("formId").toString();
            FormData formcreditapply0001 = formService.getFormData(formId);
            getFormValue(formcreditapply0001, getMapByJson(ajaxData));
            String faUrl = ymlConfig.getSysParams().get("sys.project.fa.url");
            if (this.validateFormData(formcreditapply0001)) {
                setObjValue(formcreditapply0001, mfCusCreditApply);
                MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
                mfCusCooperativeAgency.setOrgaNo(mfCusCreditApply.getCusNo());
                mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getById(mfCusCooperativeAgency);
                mfCusCreditApply.setCusType(mfCusCooperativeAgency.getExt4());
                mfCusCreditApply.setOpNo(mfCusCooperativeAgency.getOpNo());
                //押品
                List <PledgeBaseInfo> pledgeBaseInfoList = new ArrayList <PledgeBaseInfo>();
                Map <String, Object> pledgeBaseInfoMap = new HashMap <String, Object>();
                Map <String, Object> mfCusCreditApplyMap = new HashMap <String, Object>();
                Gson gson = new Gson();
                Double redeemFundSum = 0.0;//选择的基金质押总额
                List <MfBusFundDetail> mfBusFundDetailList = gson.fromJson(ajaxDataList, new TypeToken <List <MfBusFundDetail>>() {
                }.getType());
                for (int i = 0; i < mfBusFundDetailList.size(); i++) {
                    mfBusFundDetailList.get(i).setPledgeStatus("02");
                    mfBusFundDetailFeign.update(mfBusFundDetailList.get(i));
                    PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                    pledgeBaseInfo.setCusNo(mfCusCreditApply.getCusNo());//客户号
                    pledgeBaseInfo.setCusName(mfCusCreditApply.getCusName());//合作社名
                    pledgeBaseInfo.setCertificateName(mfCusCreditApply.getCusName());//所有权人名称
                    pledgeBaseInfo.setPledgeNo(mfBusFundDetailList.get(i).getId());//基金id
                    pledgeBaseInfo.setPledgeName(mfBusFundDetailList.get(i).getFundSimpleName());//基金名
                    pledgeBaseInfo.setClassFirstNo("A");
                    pledgeBaseInfo.setPleOriginalValue(mfBusFundDetailList.get(i).getApplyPurchaseAmount());//基金购买总额
                    pledgeBaseInfo.setPleValue(mfBusFundDetailList.get(i).getRedeemAmount());//基金质押总额
                    redeemFundSum = redeemFundSum + pledgeBaseInfo.getPleValue();
                    pledgeBaseInfoList.add(pledgeBaseInfo);
                }
                pledgeBaseInfoMap.put("pledgeMethod", "04");
                pledgeBaseInfoMap.put("classId", "17061310520592426");
                pledgeBaseInfoMap.put("collateralType", "pledge");
                pledgeBaseInfoMap.put("isQuote", "0");
                pledgeBaseInfoMap.put("opNo", mfCusCooperativeAgency.getOpNo());
                String[] strArr1 = {"1000"};
                mfCusCreditApplyMap.put("kindNos", strArr1);
                String[] strArr2 = {"凤安惠农贷"};
                mfCusCreditApplyMap.put("kindNames", strArr2);
                mfCusCreditApplyMap.put("creditAmts", mfCusCreditApply.getCreditAmt());
                mfCusCreditApplyMap.put("baseType", "");
                String[] strArr4 = {"1"};
                mfCusCreditApplyMap.put("amountLands", strArr4);
                Map <String, Object> parmMap = new HashMap <String, Object>();
                parmMap.put("mfCusCreditApply", mfCusCreditApply);
                parmMap.put("mfCusCreditApplyMap", mfCusCreditApplyMap);
                parmMap.put("pledgeBaseInfoList", pledgeBaseInfoList);
                parmMap.put("pledgeBaseInfoMap", pledgeBaseInfoMap);
                String json = HttpClientUtil.sendPostJson(gson.toJson(parmMap), faUrl + "/mfCusCreditApply/pushMfCusCreditApply");
                MfBusFundDetail mfbusfunddetail = new MfBusFundDetail();
                dataMap.put("mfCusCreditApply", mfCusCreditApply);
                dataMap = new HashMap <String, Object>();
                dataMap.put("creditAppId", mfCusCreditApply.getCreditAppId());
                dataMap.put("wkfAppId", mfCusCreditApply.getWkfAppId());
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "保存失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }

    /**
     * 新增授信
     *
     * @param ajaxData
     * @param agenciesArrs
     * @param kindNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map <String, Object> insertAjax(String ajaxData, String agenciesArrs,String breedArrs,String kindNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String creditModel = request.getParameter("creditModel");
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
            FormData calc = formService.getFormData("cybQuotaCalcBase");
            getFormValue(formcreditapply0001, getMapByJson(ajaxData));
            getFormValue(calc, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditapply0001)) {
                setObjValue(formcreditapply0001, mfCusCreditApply);
                setObjValue(calc, mfCusCreditApply);
                mfCusCreditApply.setKindNo(kindNo);
                if(StringUtil.isNotEmpty(kindNo)){
                    MfSysKind mfSysKind = new MfSysKind();
                    mfSysKind.setKindNo(kindNo);
                    mfSysKind = mfSysKindFeign.getById(mfSysKind);
                    if(mfSysKind!=null){
                        mfCusCreditApply.setKindName(mfSysKind.getKindName());
                    }
                }
                JSONArray agenciesList = JSONArray.fromObject(agenciesArrs);
                JSONArray breedList = JSONArray.fromObject(breedArrs);
                for (int i = 0; i < agenciesList.size(); i++) {
                    JSONObject jsonObject = (JSONObject) agenciesList.get(i);
                    String agenciesId =jsonObject.getString("agenciesId");
                    //判断合作银行下是否含有业务品种
                    boolean agenciesHaveBreed = false;
                    for (int j = 0; j < breedList.size(); j++) {
                        JSONObject breed = (JSONObject) breedList.get(j);
                        if(agenciesId.equals(breed.getString("breedAgenciesId"))){
                            agenciesHaveBreed =true;
                        }
                    }
                    if(!agenciesHaveBreed){
                        dataMap.put("flag", "error");
                        dataMap.put("msg", "合作银行:"+jsonObject.getString("agenciesName")+"下未添加业务品种！");
                        return dataMap;
                    }
                }
                dataMap.put("agenciesArrs", agenciesArrs);
                dataMap.put("breedArrs", breedArrs);
                new FeignSpringFormEncoder().addParamsToBaseDomain(mfCusCreditApply);
                mfCusCreditApply.setCreditModel(creditModel);
                dataMap.put("mfCusCreditApply", mfCusCreditApply);
                //插入授信信息
                mfCusCreditApply = mfCusCreditApplyFeign.insertStartProcess(dataMap);
                dataMap = new HashMap <String, Object>();
                dataMap.put("creditAppId", mfCusCreditApply.getCreditAppId());
                dataMap.put("wkfAppId", mfCusCreditApply.getWkfAppId());
                dataMap.put("cusNo", mfCusCreditApply.getCusNo());
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //新增授信产品异常时，需要删除授信信息
            mfCusCreditApplyFeign.delete(mfCusCreditApply);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    @RequestMapping(value = "/insertStopAjax")
    @ResponseBody
    public Map <String, Object> insertStopAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditStopAdd = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditStopAdd, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditStopAdd)) {
                setObjValue(formcreditStopAdd, mfCusCreditApply);
                dataMap = mfCusCreditApplyFeign.insertStop(mfCusCreditApply);
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 授信失效
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertInvalidAjax")
    @ResponseBody
    public Map <String, Object> insertInvalidAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditInvalidAdd = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditInvalidAdd, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditInvalidAdd)) {
                setObjValue(formcreditInvalidAdd, mfCusCreditContract);
                dataMap = mfCusCreditContractFeign.insertInvalid(mfCusCreditContract);
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 新增授信（经销商）
     *
     * @param kindNos
     * @param kindNames
     * @param creditAmts
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dealerInsertAjax")
    @ResponseBody
    public Map <String, Object> dealerInsertAjax(String ajaxData, String kindNos, String kindNames, String creditAmts, String baseTypes) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String creditModel = request.getParameter("creditModel");
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditapply0001, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditapply0001)) {
                setObjValue(formcreditapply0001, mfCusCreditApply);
                //客户信息
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusCreditApply.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
                //通过客户类型查询匹配的授信模型
                MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
                mfCusCreditModel.setCusTypeNo(mfCusCreditApply.getCusType());
                mfCusCreditModel = mfCusCreditModelFeign.getByCusTypeNo(mfCusCreditModel);
                if (mfCusCreditModel != null) {
                    mfCusCreditApply.setModelId(mfCusCreditModel.getModelId());
                }
                dataMap.put("kindNos", kindNos);
                dataMap.put("kindNames", kindNames);
                dataMap.put("creditAmts", creditAmts);
                dataMap.put("baseTypes", baseTypes);
                new FeignSpringFormEncoder().addParamsToBaseDomain(mfCusCreditApply);
                mfCusCreditApply.setCreditModel(creditModel);
                mfCusCreditApply.setClientType("7");//添加数据来源，数据字典项 CLIENT_TYPE 1:PC 2:交易前端 3:appAndroid 4:appIos 5:微信 7:经销商前端
                mfCusCreditApply.setDataShowWay("0");//进件数据PC主端是否展示（0：不展示，1：展示），内部人员申请默认展示，经销商申请复核后展示
                mfCusCreditApply.setDealerBusStage("1");//经销商业务办理阶段（1：经办、2：复核）
                mfCusCreditApply.setAppStationId(User.getRegNo(request));
                mfCusCreditApply.setAppStationName(User.getRegName(request));
                mfCusCreditApply.setAppDate(DateUtil.getDate());
                dataMap.put("mfCusCreditApply", mfCusCreditApply);
                //插入授信信息
                mfCusCreditApply = mfCusCreditApplyFeign.insertStartProcess(dataMap);
                //授信--产品信息
                //getMfCusPorductCreditDatas(mfCusCreditApply,request);
                dataMap = new HashMap <String, Object>();
                dataMap.put("creditAppId", mfCusCreditApply.getCreditAppId());
                dataMap.put("wkfAppId", mfCusCreditApply.getWkfAppId());
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //新增授信产品异常时，需要删除授信信息
            mfCusCreditApplyFeign.delete(mfCusCreditApply);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 对授信-产品实体 属性设置值
     *
     * @author LJW
     * date 2017-3-1
     */
    @SuppressWarnings("unused")
    private void getMfCusPorductCreditDatas(MfCusCreditApply creditApply, HttpServletRequest request) {
        List <MfCusPorductCredit> mfCusPorductCredits = new ArrayList <MfCusPorductCredit>();
        MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
        JSONArray kindNos = JSONArray.fromObject(request.getParameter("kindNos"));
        JSONArray kindNames = JSONArray.fromObject(request.getParameter("kindNames"));
        JSONArray creditAmts = JSONArray.fromObject(request.getParameter("creditAmts"));
        String kindNo = creditApply.getKindNo();
        if ((kindNos.size() > 0 && !kindNos.isEmpty()) || (!"".equals(kindNo) && kindNo != null)) {
            mfCusPorductCredit.setId(WaterIdUtil.getWaterId());
            mfCusPorductCredit.setCreditAppId(creditApply.getCreditAppId());
            mfCusPorductCredit.setKindNo(kindNo);
            mfCusPorductCredit.setKindName(creditApply.getKindName());
            mfCusPorductCredit.setCreditAmt(creditApply.getCreditAmt());
            mfCusPorductCredits.add(mfCusPorductCredit);
            for (int i = 0, count = kindNos.size(); i < count; i++) {
                MfCusPorductCredit cusPorductCredit = new MfCusPorductCredit();
                cusPorductCredit.setId(WaterIdUtil.getWaterId());
                cusPorductCredit.setCreditAppId(creditApply.getCreditAppId());
                cusPorductCredit.setKindNo(kindNos.getString(i));
                cusPorductCredit.setKindName(kindNames.getString(i));
                cusPorductCredit.setCreditAmt(Double.valueOf(creditAmts.getString(i)));
                mfCusPorductCredits.add(cusPorductCredit);
            }
            mfCusPorductCreditFeign.insertByBatch(mfCusPorductCredits);
        }
    }

    /**
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjaxByOne")
    @ResponseBody
    public Map <String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = getMapByJson(ajaxData);
        FormData formcreditapply0001_new = formService.getFormData(dataMap.get("formId").toString());;
        getFormValue(formcreditapply0001_new, dataMap);
        MfCusCreditApply mfCusCreditApplyJsp = new MfCusCreditApply();
        setObjValue(formcreditapply0001_new, mfCusCreditApplyJsp);
        mfCusCreditApplyJsp.setWkfAppId(null);
        MfCusCreditApply mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApplyJsp);
        if (mfCusCreditApply != null) {
            try {
                mfCusCreditApply = (MfCusCreditApply) EntityUtil.reflectionSetVal(mfCusCreditApply, mfCusCreditApplyJsp, getMapByJson(ajaxData));
                if(mfCusCreditApply.getCreditSum()!=null){
                    mfCusCreditApply.setAuthBal(mfCusCreditApply.getCreditSum());
                }
                mfCusCreditApplyFeign.update(mfCusCreditApply);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
                throw e;
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
        }
        return dataMap;
    }

    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map <String, Object> updateAjax(String ajaxData, String creditModel, String appId,String formData) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormService formService = new FormService();
        String cusNo = null;
        String relNo = null;
        try {
            if (StringUtil.isEmpty(ajaxData) || "null".equals(ajaxData)) {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
                return dataMap;
            }

            dataMap = getMapByJson(ajaxData);
            if(StringUtil.isBlank(creditModel)){
                String creditAppId = (String) dataMap.get("creditAppId");
                if(StringUtil.isNotBlank(creditAppId)){
                    MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
                    mfCusCreditApply.setCreditAppId(creditAppId);
                    mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
                    if(mfCusCreditApply!=null){
                        creditModel =mfCusCreditApply.getTemplateCreditId().replaceAll("credit","");
                    }
                }

            }
            String formId = null;
            if (BizPubParm.CREDIT_TYPE_ADJUST.equals(dataMap.get("creditType"))) {
                String creditId = "credit3";
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setAdjustAppId(appId);
                mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                if(mfCusCreditAdjustApply != null){
                    creditId =mfCusCreditAdjustApply.getCreditId();
                }
                formId = prdctInterfaceFeign.getFormId(creditId, WKF_NODE.report, null, null, User.getRegNo(request));
            } else if (BizPubParm.CREDIT_TYPE_ADD.equals(dataMap.get("creditType"))) {
                formId = prdctInterfaceFeign.getFormId("credit" + creditModel, WKF_NODE.report, null, null, User.getRegNo(request));
            } else if (BizPubParm.CREDIT_TYPE_RENEW.equals(dataMap.get("creditType"))) {
                formId = prdctInterfaceFeign.getFormId("creditRenew" + creditModel, WKF_NODE.report, null, null, User.getRegNo(request));
            }else {
            }

            FormData formcreditapply0001 = formService.getFormData(formId);// "creditapply0001"
            getFormValue(formcreditapply0001, dataMap);
            //if (this.validateFormData(formcreditapply0001)) {
            if(BizPubParm.CREDIT_TYPE_ADJUST.equals(dataMap.get("creditType"))){//授信调整
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                //当页面表单formData有值，补充表单使用
                if(StringUtil.isNotEmpty(formData)){
                    Map <String, Object> formDataMap = getMapByJson(formData);
                    FormData supplementOrder = formService.getFormData("supplementOrder");// "creditapply0001"
                    getFormValue(supplementOrder, formDataMap);
                    setObjValue(supplementOrder, mfCusCreditAdjustApply);
                }
                setObjValue(formcreditapply0001, mfCusCreditAdjustApply);
                dataMap.put("mfCusCreditAdjustApply",mfCusCreditAdjustApply);
                cusNo = mfCusCreditAdjustApply.getCusNo();
                relNo = mfCusCreditAdjustApply.getAdjustAppId();
            }else{
                MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
                setObjValue(formcreditapply0001, mfCusCreditApply);
                // 更新授信信息及授信产品
                dataMap.put("mfCusCreditApply", mfCusCreditApply);
                cusNo = mfCusCreditApply.getCusNo();
                relNo = mfCusCreditApply.getCreditAppId();
            }
            dataMap.put("creditType", dataMap.get("creditType"));

                /*boolean uploadFalg = mfBusApplyFeign.getIfUploadCredit(cusNo,relNo,WKF_NODE.report.getNodeNo());
                if(!uploadFalg){
                    dataMap.put("flag", "error");
                    dataMap.put("uploadFalg", uploadFalg);
                    dataMap.put("msg", "必须上传企业的征信报告，征信报告中信息概要部分，需为“该信用主体无信贷交易记录”！");
                    return dataMap;
                }*/
            mfCusCreditApplyFeign.submitCreditInspectReport(dataMap);
//                TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfCusCreditApply.getWkfAppId(), null);// 当前审批节点task
//                String nodeNo = taskAppro.getActivityName();// 功能节点编号
//                if (nodeNo != null && WKF_NODE.protocol_manage.getNodeNo().equals(nodeNo)) {
//                    MfBusApply mfBusApply = new MfBusApply();
//                    mfBusApply.setNodeNo(nodeNo);
//                    mfBusApply.setCusNo(mfCusCreditApply.getCusNo());
//                    mfBusApply.setCusName(mfCusCreditApply.getCusName());
//                    mfBusApply.setOpNo(mfCusCreditApply.getOpNo());
//                    mfBusApply.setOpName(mfCusCreditApply.getOpName());
//                    mfBusApply.setBrNo(mfCusCreditApply.getBrNo());
//                    mfBusApply.setBrName(mfCusCreditApply.getBrName());
//                    mfBusApply.setEsignId(mfCusCreditApply.getCreditAppId());
//                    esignInterfaceFeign.initReplacePdf(mfBusApply);
//                }
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
//            } else {
//                dataMap.put("flag", "error");
//                dataMap.put("msg", this.getFormulavaliErrorMsg());
//            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateForConfigAjax")
    @ResponseBody
    public Map <String, Object> updateForConfigAjax(String ajaxData, String creditModel, String temporaryStorage, String nodeNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormService formService = new FormService();
        try {
            dataMap = getMapByJson(ajaxData);

            String formId = null;
            if (BizPubParm.CREDIT_TYPE_ADJUST.equals(dataMap.get("creditType"))) {
                MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("creditAdjust" + creditModel, nodeNo);
                if (mfKindForm != null) {
                    formId = mfKindForm.getAddModel();
                }
            } else if (BizPubParm.CREDIT_TYPE_ADD.equals(dataMap.get("creditType"))) {
                MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("credit" + creditModel, nodeNo);
                if (mfKindForm != null) {
                    formId = mfKindForm.getAddModel();
                }
            } else if (BizPubParm.CREDIT_TYPE_RENEW.equals(dataMap.get("creditType"))) {
                MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("creditRenew" + creditModel, nodeNo);
                if (mfKindForm != null) {
                    formId = mfKindForm.getAddModel();
                }
            }else {
            }
            FormData formcreditapply0001 = formService.getFormData(formId);// "creditapply0001"
            getFormValue(formcreditapply0001, dataMap);
            if (this.validateFormData(formcreditapply0001)) {
                MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
                setObjValue(formcreditapply0001, mfCusCreditApply);
                // 更新授信信息及授信产品
                dataMap.put("mfCusCreditApply", mfCusCreditApply);
                mfCusCreditApplyFeign.submitCreditInspectReport(dataMap);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @param creditAppId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map <String, Object> getByIdAjax(String ajaxData, String creditAppId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> formData = new HashMap <String, Object>();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formcreditapply0002 = formService.getFormData("creditapply0002");
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(creditAppId);
        mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
        getObjValue(formcreditapply0002, mfCusCreditApply, formData);
        if (mfCusCreditApply != null) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        dataMap.put("creditApply", mfCusCreditApply);
        return dataMap;
    }

    /**
     * Ajax异步删除
     *
     * @param creditAppId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAjax")
    @ResponseBody
    public Map <String, Object> deleteAjax(String ajaxData, String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(creditAppId);
        try {
            mfCusCreditApplyFeign.delete(mfCusCreditApply);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 授信新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String formId = "";
        String faUrl = PropertiesUtil.getSysParamsProperty("sys.project.fa.url");
        String projectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String creditAppId = WaterIdUtil.getWaterId("SX");
        mfCusCreditApply.setCreditAppId(creditAppId);
        String cusNo = request.getParameter("cusNo");
        // 获取评级授信额度
        Double evalCreditAmt = appEvalFeign.getEvalCreditAmtByCusNo(cusNo);
        if (evalCreditAmt != null || !"".equals(evalCreditAmt)) {
            model.addAttribute("evalCreditAmt", MathExtend.moneyStr(evalCreditAmt));
        } else {
            model.addAttribute("evalCreditAmt", evalCreditAmt);
        }
        String creditModel = request.getParameter("creditModel");//授信模式1客户授信2项目授信
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        Gson gson = new Gson();
        //惠农贷跨平台请求
        if ("HNDHZS".equals(projectName)) {
            String mfCusCustomerJson = HttpClientUtil.sendPostJson(gson.toJson(mfCusCustomer), faUrl + "/mfCusCustomer/getById");
            mfCusCustomer = gson.fromJson(mfCusCustomerJson, MfCusCustomer.class);
        } else {
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        }
        // 获取业务身份
        MfCusType mfCusType = new MfCusType();
        mfCusType.setTypeNo(mfCusCustomer.getCusType());
        //惠农贷跨平台请求
        if ("HNDHZS".equals(projectName)) {
            String mfCusTypeJson = HttpClientUtil.sendPostJson(gson.toJson(mfCusType), faUrl + "/mfCusType/getById");
            mfCusType = gson.fromJson(mfCusTypeJson, MfCusType.class);
        } else {
            mfCusType = mfCusTypeFeign.getById(mfCusType);
        }
        String baseType = mfCusType.getBaseType();
        mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
        mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
        mfCusCreditApply.setCusNo(cusNo);
        mfCusCreditApply.setCreditModel(creditModel);
        //获得打开授信页面所需要的参数
        if ("HNDHZS".equals(projectName)) {
            String mfCusCreditApplyJson = HttpClientUtil.sendPostJson(gson.toJson(mfCusCreditApply), faUrl + "/mfCusCreditApply/initCusCreditedInput");
            dataMap = gson.fromJson(mfCusCreditApplyJson, Map.class);
        } else {
            dataMap = mfCusCreditApplyFeign.initCusCreditedInput(mfCusCreditApply);
        }
        /**
         * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
         */
        String creditFlag = String.valueOf(dataMap.get("creditFlag"));
        String termFlag = String.valueOf(dataMap.get("termFlag"));
        //惠农贷跨平台请求
        if ("HNDHZS".equals(projectName)) {
            Map <String, Object> param = new HashMap <String, Object>();
            param.put("kindNo", "credit" + creditModel);
            String regNo = User.getRegNo(request);
            param.put("regNo", regNo);
            formId = HttpClientUtil.sendPostJson(gson.toJson(param), faUrl + "/prdctInterface/getFormIdHND");
        } else {
            //获得授信申请表单
            formId = prdctInterfaceFeign.getFormId("credit" + creditModel, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
        }
        FormData formcreditapply0001 = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
        //客户授信支持授信调整
        String creditProductBatchId = "SXCP" + WaterIdUtil.getWaterId();
        String creditType = (String) dataMap.get("creditType");
        model.addAttribute("creditType", creditType);
        String adjustAppId = null;
        if (BizPubParm.CREDIT_MODEL_CUS.equals(creditModel)) {
            //授信调整，授信类型不可编辑，默认为授信调整
            if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
                //惠农贷跨平台请求
                if ("HNDHZS".equals(projectName)) {
                    Map <String, Object> param = new HashMap <String, Object>();
                    param.put("kindNo", "credit" + creditModel);
                    param.put("node", WKF_NODE.credit_apply);
                    String regNo = User.getRegNo(request);
                    param.put("regNo", regNo);
                    formId = HttpClientUtil.sendPostJson(gson.toJson(param), faUrl + "/prdctInterface/getFormIdHND");
                } else {
                    formId = prdctInterfaceFeign.getFormId("creditAdjust" + creditModel, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
                }
                formcreditapply0001 = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
                getObjValue(formcreditapply0001, dataMap.get("mfCusCreditContract"));
                adjustAppId = WaterIdUtil.getWaterId("adj");
                MfCusCreditApply creditApply = new MfCusCreditApply();
                creditApply.setCusNo(cusNo);
                //惠农贷跨平台请求
                if ("HNDHZS".equals(projectName)) {
                    String creditApplyJson = HttpClientUtil.sendPostJson(gson.toJson(creditApply), faUrl + "/mfCusCreditApply/getByCusNoAndOrederFirst");
                    creditApply = gson.fromJson(creditApplyJson, MfCusCreditApply.class);
                } else {
                    creditApply = mfCusCreditApplyFeign.getByCusNoAndOrederFirst(creditApply);
                }
                this.changeFormProperty(formcreditapply0001, "creditAppId", "initValue", creditApply.getCreditAppId());
            }
            //未授信过，授信类型不可编辑，默认为新增授信
            if (BizPubParm.CREDIT_FLAG_NO.equals(creditFlag) || BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
                mfCusCreditApply = new MfCusCreditApply();
                mfCusCreditApply.setCreditAppId(creditAppId);
                mfCusCreditApply.setBeginDate(DateUtil.getDate());
                mfCusCreditApply.setOpName(User.getRegName(request));
                mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
                mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
                mfCusCreditApply.setCusNo(cusNo);
                mfCusCreditApply.setCreditType(BizPubParm.CREDIT_TYPE_ADD);
                mfCusCreditApply.setResponsibleBrNo(User.getOrgNo(request));
                mfCusCreditApply.setResponsibleBrName(User.getOrgName(request));
                mfCusCreditApply.setCreditProductBatchId(creditProductBatchId);//授信产品批次号
                getObjValue(formcreditapply0001, mfCusCreditApply);
                Map <String, Object> parmMap = new HashMap <String, Object>();
                parmMap.put("cusBaseType", baseType);
                parmMap.put("idNum", mfCusCustomer.getIdNum());
                parmMap.put("cusTel", mfCusCustomer.getCusTel());
                getObjValue(formcreditapply0001, parmMap);
            }
        } else if (BizPubParm.CREDIT_MODEL_PROJECT.equals(creditModel)) {
            mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
            mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
            mfCusCreditApply.setCusNo(cusNo);
            mfCusCreditApply.setCreditType(BizPubParm.CREDIT_TYPE_ADD);
            mfCusCreditApply.setResponsibleBrNo(User.getOrgNo(request));
            mfCusCreditApply.setResponsibleBrName(User.getOrgName(request));
            getObjValue(formcreditapply0001, mfCusCreditApply);
        }else {
        }

        //获取授信选择的所有的产品
        JSONObject json = new JSONObject();
        List <MfSysKind> mfSysKinds = new ArrayList <MfSysKind>();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setUseFlag("1");
        //惠农贷跨平台请求
        if ("HNDHZS".equals(projectName)) {
            String mfSysKindsJson = HttpClientUtil.sendPostJson(gson.toJson(mfSysKind), faUrl + "/prdctInterface/getSysKindList");
            mfSysKinds = gson.fromJson(mfSysKindsJson, new TypeToken <List <MfSysKind>>() {
            }.getType());
        } else {
            mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
        }
        JSONArray array = JSONArray.fromObject(mfSysKinds);
        json.put("mfSysKinds", array);

        request.setAttribute("json", json);
        request.setAttribute("termFlag", termFlag);
        request.setAttribute("creditFlag", creditFlag);
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("baseType", baseType);
        model.addAttribute("query", "");
        model.addAttribute("creditType", creditType);

        String scNo = "credit_apply";
        if (StringUtil.isNotEmpty(scNo)) {
            DocBizManageParam dbmp = new DocBizManageParam();
            dbmp.setScNo(scNo);// 场景编号
            dbmp.setRelNo(mfCusCreditApply.getCreditAppId());// 业务编号
            dbmp.setDime("credit");// 产品类型
            dbmp.setCusNo(cusNo);// 客户号
            dbmp.setRegNo(User.getRegNo(request));
            dbmp.setOrgNo(User.getOrgNo(request));
            docInterfaceFeign.initiaBiz(dbmp);
        }
        model.addAttribute("creditAppId", mfCusCreditApply.getCreditAppId());
        model.addAttribute("scNo", scNo);
        model.addAttribute("projectName", projectName);

        String CREDIT_END_DATE_REDUCE = new CodeUtils().getSingleValByKey("CREDIT_END_DATE_REDUCE");// 授信结束日自动减一天
        if (StringUtil.isNotEmpty(CREDIT_END_DATE_REDUCE)) {
            model.addAttribute("CREDIT_END_DATE_REDUCE", CREDIT_END_DATE_REDUCE);
        }

        if ("HNDHZS".equals(projectName)) {
            MfBusFund mfBusFund = new MfBusFund();
            MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
            mfBusFund.setCusNo(cusNo);
            List <MfBusFund> mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);
            String mfBusFundStr = "";
            for (int i = 0; i < mfBusFundList.size(); i++) {
                MfBusFund mfBusFundQuery = mfBusFundList.get(i);
                if (i == mfBusFundList.size() - 1) {
                    mfBusFundStr = mfBusFundStr + "'" + mfBusFundQuery.getId() + "'";
                } else {
                    mfBusFundStr = mfBusFundStr + "'" + mfBusFundQuery.getId() + "'" + ",";
                }
            }
            mfBusFundDetail.setFk(mfBusFundStr);
            List <MfBusFundDetail> mfBusFundDetailList = mfBusFundDetailFeign.getMfBusFundDetailList(mfBusFundDetail);
            model.addAttribute("mfBusFundDetailList", mfBusFundDetailList);
            return "/component/auth/MfCusCreditApply_InsertHND";
        }
        return "/component/auth/MfCusCreditApply_Insert";
    }

    /**
     * 授信申请提交页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submitInput")
    public String submitInput(Model model, String creditAppId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Gson gons = new Gson();

        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(creditAppId);
        mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);

        String cusNo = mfCusCreditApply.getCusNo();

        Map <String, Object> dataMap = mfCusCreditApplyFeign.initCusCreditedInput(mfCusCreditApply);
        String creditType = (String) dataMap.get("creditType");
        String creditModel = mfCusCreditApply.getCreditModel();//授信模式1客户授信2项目授信
        String creditFlag = String.valueOf(dataMap.get("creditFlag"));
        String termFlag = String.valueOf(dataMap.get("termFlag"));

        // 获取业务身份
        MfCusType mfCusType = new MfCusType();
        mfCusType.setTypeNo(mfCusCreditApply.getCusType());
        mfCusType = mfCusTypeFeign.getById(mfCusType);
        String baseType = mfCusType.getBaseType();

        // 获得授信申请表单
        String formId = prdctInterfaceFeign.getFormId("credit" + creditModel, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
        FormData formcreditapply0001 = formService.getFormData(formId);
        getObjValue(formcreditapply0001, mfCusCreditApply);

        String adjustAppId = null;
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
            MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
            mfCusCreditAdjustApply.setCusNo(cusNo);
            mfCusCreditAdjustApply.setCreditModel(creditModel);
            mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getByCusNo(mfCusCreditAdjustApply);
            adjustAppId = mfCusCreditAdjustApply.getAdjustAppId();

            formId = prdctInterfaceFeign.getFormId("creditAdjust" + creditModel, WKF_NODE.credit_apply, null, null, User.getRegNo(request));

            formcreditapply0001 = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
            getObjValue(formcreditapply0001, dataMap.get("mfCusCreditContract"));
            getObjValue(formcreditapply0001, mfCusCreditAdjustApply);
            this.changeFormProperty(formcreditapply0001, "adjustAppId", "initValue", adjustAppId);
            this.changeFormProperty(formcreditapply0001, "creditAppId", "initValue", mfCusCreditApply.getCreditAppId());
        }

        // 获取授信选择的所有的产品
        JSONObject json = new JSONObject();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setUseFlag("1");
        List <MfSysKind> mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
        JSONArray array = JSONArray.fromObject(mfSysKinds);
        json.put("mfSysKinds", array);

        MfCusPorductCredit porduct = new MfCusPorductCredit();
        porduct.setCreditAppId(creditAppId);
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
            porduct.setCreditAppId(adjustAppId);
        }
        List <MfCusPorductCredit> porductList = mfCusPorductCreditFeign.getByCreditAppId(porduct);

        model.addAttribute("json", json);
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("porductList", gons.toJson(porductList));
        // model.addAttribute("porductList", porductList);
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("query", "");

        model.addAttribute("baseType", baseType);
        model.addAttribute("creditType", creditType);
        model.addAttribute("creditFlag", creditFlag);
        model.addAttribute("termFlag", termFlag);
        model.addAttribute("cusNo", cusNo);

        // 获取评级授信额度
        Double evalCreditAmt = appEvalFeign.getEvalCreditAmtByCusNo(cusNo);
        if (evalCreditAmt != null || !"".equals(evalCreditAmt)) {
            model.addAttribute("evalCreditAmt", MathExtend.moneyStr(evalCreditAmt));
        } else {
            model.addAttribute("evalCreditAmt", evalCreditAmt);
        }

        model.addAttribute("creditAppId", mfCusCreditApply.getCreditAppId());
        model.addAttribute("scNo", "credit_apply");

        // ---------- begin 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        Map <String, ParmDic> SAVE_ONLY = new CodeUtils().getMapObjByKeyName("SAVE_ONLY");// 启用: 显示保存及提交两个按钮(支持保存未提交) 停用: 只显示保存按钮
        String SAVE_ONLY_1 = "0";
        if (SAVE_ONLY.containsKey("1")) {// 启用
            SAVE_ONLY_1 = "1";// 支持保存未提交
        }
        model.addAttribute("SAVE_ONLY_1", SAVE_ONLY_1);
        // ---------- end 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------

        // result = wkfInterface.doCommit(task.getId(), AppConstant.OPINION_TYPE_ARREE, "", transition, opNo, nextUser);

        return "/component/auth/MfCusCreditApply_submitInput";
    }

    /**
     * 授信申请提交
     *
     * @param ajaxData
     * @param kindNos
     * @param kindNames
     * @param creditAmts
     * @param amountLands
     * @param monthTotalRates
     * @param baseTypes
     * @param baseType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submitInputAjax")
    @ResponseBody
    public Map <String, Object> submitInputAjax(String ajaxData, String kindNos, String kindNames, String creditAmts, String amountLands, String monthTotalRates, String baseTypes, String baseType, String temporaryStorage) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);

        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String creditModel = request.getParameter("creditModel");
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditapply0001, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditapply0001)) {
                setObjValue(formcreditapply0001, mfCusCreditApply);
                //客户信息
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusCreditApply.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
                //新增分公司授信，授信额度不能大于所属集团可用额度
                if (baseType != null && "2".equals(baseType)) {
                    MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
                    mfCusCorpBaseInfo.setCusNo(mfCusCreditApply.getCusNo());
                    mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
                    //如果是子公司，授信额度不能大于所属集团可用额度
                    if (mfCusCorpBaseInfo != null && StringUtil.isNotEmpty(mfCusCorpBaseInfo.getIfGroup()) && "1".equals(mfCusCorpBaseInfo.getIfGroup())) {
                        MfCusGroup mfCusGroup = new MfCusGroup();
                        mfCusGroup.setIdNum(mfCusCorpBaseInfo.getGroupNo());
                        mfCusGroup = mfCusGroupFeign.getByIdNum(mfCusGroup);
                        //获取所属集团可用额度
                        MfCusCreditUseHis mfCusCreditUseHis = new MfCusCreditUseHis();
                        mfCusCreditUseHis.setCusNo(mfCusGroup.getGroupNo());
                        List <MfCusCreditUseHis> list = mfCusCreditUseHisFeign.getMfCusCreditUseHis(mfCusCreditUseHis);
                        String authBal = "";
                        if (list.size() > 0) {
                            mfCusCreditUseHis = list.get(0);
                            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                            mfCusCreditContract.setCreditAppId(mfCusCreditUseHis.getCreditAppId());
                            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
                            authBal = MathExtend.moneyStr(mfCusCreditContract.getCreditSum());//所属集团可用额度
                        } else {
                            dataMap.put("msg", "所属集团暂未授信!");
                            return dataMap;
                        }

                        String creditSum = MathExtend.moneyStr(mfCusCreditApply.getCreditSum());
                        if (MathExtend.comparison(creditSum.replaceAll(",", ""), authBal.replaceAll(",", "")) == 1) {
                            dataMap.put("msg", "授信额度不能大于所属集团授信额度!");
                            return dataMap;
                        }
                    }
                }
                //通过客户类型查询匹配的授信模型
                MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
                mfCusCreditModel.setCusTypeNo(mfCusCreditApply.getCusType());
                mfCusCreditModel = mfCusCreditModelFeign.getByCusTypeNo(mfCusCreditModel);
                if (mfCusCreditModel != null) {
                    mfCusCreditApply.setModelId(mfCusCreditModel.getModelId());
                }
                mfCusCreditApply.setBaseType(baseType);

                dataMap.put("kindNos", kindNos);
                dataMap.put("kindNames", kindNames);
                dataMap.put("creditAmts", creditAmts);
                dataMap.put("baseTypes", baseTypes);
                dataMap.put("baseType", baseType);
                dataMap.put("amountLands", amountLands);
                dataMap.put("monthTotalRates", monthTotalRates);
                new FeignSpringFormEncoder().addParamsToBaseDomain(mfCusCreditApply);
                mfCusCreditApply.setCreditModel(creditModel);
                dataMap.put("mfCusCreditApply", mfCusCreditApply);

                //插入授信信息
                dataMap.put("temporaryStorage", temporaryStorage);
                mfCusCreditApply = mfCusCreditApplyFeign.submitInput(dataMap);

                dataMap = new HashMap <String, Object>();
                dataMap.put("creditAppId", mfCusCreditApply.getCreditAppId());
                dataMap.put("wkfAppId", mfCusCreditApply.getWkfAppId());
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //新增授信产品异常时，需要删除授信信息
            mfCusCreditApplyFeign.delete(mfCusCreditApply);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }

        return dataMap;
    }

    /**
     * 授信新增页面（经销商）
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dealerInput")
    public String dealerInput(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String cusNo = request.getParameter("cusNo");
        String creditModel = request.getParameter("creditModel");//授信模式1客户授信2项目授信
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        // 获取业务身份
        MfCusType mfCusType = new MfCusType();
        mfCusType.setTypeNo(mfCusCustomer.getCusType());
        mfCusType = mfCusTypeFeign.getById(mfCusType);
        mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
        mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
        mfCusCreditApply.setCusNo(cusNo);
        mfCusCreditApply.setCreditModel(creditModel);
        //获得打开授信页面所需要的参数
        Map <String, Object> dataMap = mfCusCreditApplyFeign.initCusCreditedInput(mfCusCreditApply);
        /**
         * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
         */
        String creditFlag = String.valueOf(dataMap.get("creditFlag"));
        String termFlag = String.valueOf(dataMap.get("termFlag"));
        //mfCusCreditApply=(MfCusCreditApply) dataMap.get("mfCusCreditApply");
        mfCusCreditApply = (MfCusCreditApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditApply"), MfCusCreditApply.class);
        //获得授信申请表单
        String formId = prdctInterfaceFeign.getFormId("credit" + creditModel, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
        FormData formcreditapply0001 = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
        //客户授信支持授信调整
        if (BizPubParm.CREDIT_MODEL_CUS.equals(creditModel)) {
            //授信调整，授信类型不可编辑，默认为授信调整
            if (BizPubParm.CREDIT_TYPE_ADJUST.equals(mfCusCreditApply.getCreditType())) {
                formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));//原始String.valueOf(dataMap.get("formId"))
                getObjValue(formcreditapply0001, dataMap.get("mfCusCreditContract"));
                this.changeFormProperty(formcreditapply0001, "creditType", "readonly", "1");
            }
            //未授信过，授信类型不可编辑，默认为新增授信
            if (BizPubParm.CREDIT_FLAG_NO.equals(creditFlag) || BizPubParm.CREDIT_TYPE_ADD.equals(mfCusCreditApply.getCreditType())) {
                mfCusCreditApply = new MfCusCreditApply();
                mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
                mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
                mfCusCreditApply.setCusNo(cusNo);
                mfCusCreditApply.setCreditType(BizPubParm.CREDIT_TYPE_ADD);
                mfCusCreditApply.setResponsibleBrNo(User.getOrgNo(request));
                mfCusCreditApply.setResponsibleBrName(User.getOrgName(request));
                getObjValue(formcreditapply0001, mfCusCreditApply);
                this.changeFormProperty(formcreditapply0001, "creditType", "readonly", "1");
            }
        } else if (BizPubParm.CREDIT_MODEL_PROJECT.equals(creditModel)) {
            mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
            mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
            mfCusCreditApply.setCusNo(cusNo);
            mfCusCreditApply.setCreditType(BizPubParm.CREDIT_TYPE_ADD);
            mfCusCreditApply.setResponsibleBrNo(User.getOrgNo(request));
            mfCusCreditApply.setResponsibleBrName(User.getOrgName(request));
            getObjValue(formcreditapply0001, mfCusCreditApply);
            this.changeFormProperty(formcreditapply0001, "creditType", "readonly", "1");
        }else {
        }

        //获取授信选择的所有的产品
        JSONObject json = new JSONObject();
        List <MfSysKind> mfSysKinds = new ArrayList <MfSysKind>();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setUseFlag("1");
        mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
        JSONArray array = JSONArray.fromObject(mfSysKinds);
        json.put("mfSysKinds", array);

        request.setAttribute("json", json);
        request.setAttribute("termFlag", termFlag);
        request.setAttribute("creditFlag", creditFlag);
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("query", "");
        return "/hm/auth/MfCusCreditApply_DealerInsert";
    }

    /**
     * 方法描述： 切换授信类型获得授信表单html
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-6-23 上午11:11:09
     */
    @RequestMapping(value = "/getProjectAdjustFormHtmlAjax")
    @ResponseBody
    public Map <String, Object> getProjectAdjustFormHtmlAjax(String credidPactId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String creditModel = request.getParameter("creditModel");
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setId(credidPactId);
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
        mfCusCreditApply.setCreditAppId(mfCusCreditContract.getCreditAppId());
        mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
        mfCusCreditApply.setCreditSum(mfCusCreditContract.getCreditSum());
        mfCusCreditApply.setCreditTerm(mfCusCreditContract.getCreditTerm() + "");
        mfCusCreditApply.setBeginDate(mfCusCreditContract.getBeginDate());
        mfCusCreditApply.setEndDate(mfCusCreditContract.getEndDate());
        mfCusCreditApply.setIsCeilingLoop(mfCusCreditContract.getIsCeilingLoop());
        mfCusCreditApply.setCreditType(BizPubParm.CREDIT_TYPE_ADJUST);
        MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
        mfCusCreditAdjustApply.setAdjCreditSum(0.00);
        mfCusCreditAdjustApply.setAdjCreditTerm(mfCusCreditContract.getCreditTerm() + "");
        mfCusCreditAdjustApply.setAdjBeginDate(mfCusCreditContract.getBeginDate());
        mfCusCreditAdjustApply.setAdjEndDate(mfCusCreditContract.getEndDate());
        mfCusCreditAdjustApply.setAdjIsCeilingLoop(mfCusCreditContract.getIsCeilingLoop());
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        String formId = prdctInterfaceFeign.getFormId("creditAdjust" + creditModel, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
        FormData formcreditapply0001 = formService.getFormData(formId);
        getObjValue(formcreditapply0001, mfCusCreditAdjustApply);
        getObjValue(formcreditapply0001, mfCusCreditApply);
        String formHtml = jsonFormUtil.getJsonStr(formcreditapply0001, "bootstarpTag", "");
        dataMap.put("formHtml", formHtml);
        dataMap.put("flag", "success");
        return dataMap;
    }

    /***
     * 新增
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insert")
    public String insert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcreditapply0002 = formService.getFormData("creditapply0002");
        getFormValue(formcreditapply0002);
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        setObjValue(formcreditapply0002, mfCusCreditApply);
        mfCusCreditApplyFeign.insert(mfCusCreditApply);
        getObjValue(formcreditapply0002, mfCusCreditApply);
        this.addActionMessage(model, "保存成功");
        List <MfCusCreditApply> mfCusCreditApplyList = (List <MfCusCreditApply>) mfCusCreditApplyFeign.findByPage(this.getIpage(), mfCusCreditApply).getResult();
        model.addAttribute("mfCusCreditApplyList", mfCusCreditApplyList);
        model.addAttribute("formcreditapply0002", formcreditapply0002);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCreditApply_Insert";
    }

    /**
     * 根据id查询单条
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String creditAppId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcreditapply0001 = formService.getFormData("creditapply0001");
        getFormValue(formcreditapply0001);
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(creditAppId);
        mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
        getObjValue(formcreditapply0001, mfCusCreditApply);
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCreditApply_Detail";
    }

    /**
     * 打开登记尽调报告页面
     *
     * @author LJW
     * date 2017-2-28
     */
    @RequestMapping(value = "/getOrderDescFirst")
    public String getOrderDescFirst(Model model, String creditAppId) throws Exception {
        FormService formService = new FormService();
        FormData formQuotaCalc ;
        String formId = null;
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String wkfAppId = request.getParameter("wkfAppId");
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("wkfAppId", wkfAppId);

        //获得尽调报告数据
        dataMap = mfCusCreditApplyFeign.getCreditReportDataMap(dataMap);
        JSONObject json = new JSONObject();
        json.put("mfCusPorductCreditList", JSONArray.fromObject(dataMap.get("mfCusPorductCreditList")));
        json.put("mfSysKinds", JSONArray.fromObject(dataMap.get("mfSysKinds")));
        String creditModel = String.valueOf(dataMap.get("creditModel"));//授信模式1客户授信2项目授信
        String creditType = String.valueOf(dataMap.get("creditType"));
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId((String)dataMap.get("creditId"), WKF_NODE.report, null, null, User.getRegNo(request));
        } else if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId((String)dataMap.get("creditId"), WKF_NODE.report, null, null, User.getRegNo(request));
        } else if (BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId("creditRenew" + creditModel, WKF_NODE.report, null, null, User.getRegNo(request));
        }else {
        }
        String processId =  String.valueOf(dataMap.get("processId"));
        FormData formcreditapply0001 = formService.getFormData(formId);
        getFormValue(formcreditapply0001);
        String adjustAppId = null;
        String creditId = null;
        MfCusCreditApply mfCusCreditApply = null;
        MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
        String isLegalDegreeFlag ="";
        String isGaoKeJiFlag ="";
        String manageEndDate = "";
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType) || BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            if (dataMap.get("mfCusCreditContract") != null) {
                MfCusCreditContract mfCusCreditContract = JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditContract"), MfCusCreditContract.class);
                getObjValue(formcreditapply0001, mfCusCreditContract);
            } else {
                getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
            }
            MfCusCreditAdjustApply mfCusCreditAdjustApply = (MfCusCreditAdjustApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditAdjustApply"), MfCusCreditAdjustApply.class);
            adjustAppId = mfCusCreditAdjustApply.getAdjustAppId();
            getObjValue(formcreditapply0001, mfCusCreditAdjustApply);
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);

            //授信調整時展示補充報告
            if(BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)){
                FormData formsupplementOrder = formService.getFormData("supplementOrder");
                getFormValue(formsupplementOrder);
                getObjValue(formsupplementOrder, mfCusCreditAdjustApply);
                model.addAttribute("formsupplementOrder", formsupplementOrder);
                JSONArray reportMatterArray = new JSONArray();
                List<ParmDic> reportMatter = new CodeUtils().getCacheByKeyName("REPORT_MATTER");
                if (reportMatter != null && reportMatter.size() > 0) {
                    for (int i = 0; i < reportMatter.size(); i++) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", reportMatter.get(i).getOptCode());
                        obj.put("name", reportMatter.get(i).getOptName());
                        reportMatterArray.add(obj);
                    }
                }
                model.addAttribute("reportMatter", reportMatterArray);
            }
            this.changeFormProperty(formcreditapply0001, "creditSum", "initValue", mfCusCreditAdjustApply.getAdjCreditSum().toString());
            this.changeFormProperty(formcreditapply0001, "creditTerm", "initValue", mfCusCreditAdjustApply.getAdjCreditTerm().toString());
            this.changeFormProperty(formcreditapply0001, "isCeilingLoop", "initValue", mfCusCreditAdjustApply.getAdjIsCeilingLoop());
            this.changeFormProperty(formcreditapply0001, "beginDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjBeginDate()));
            this.changeFormProperty(formcreditapply0001, "endDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjEndDate()));
            creditAppId = adjustAppId;

            //额度测算表单
            creditId = mfCusCreditAdjustApply.getCreditId();
            if(StringUtil.isNotEmpty(creditId)){
                mfCusCreditConfig.setCreditId(creditId);
                mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
                if(mfCusCreditConfig != null && StringUtil.isNotEmpty(mfCusCreditConfig.getAdaptationKindNo())){
                    MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(mfCusCreditConfig.getAdaptationKindNo());
                    if(mfSysKind != null && "1".equals(mfSysKind.getIfCreditCalc())){
                        formId = prdctInterfaceFeign.getFormId(mfCusCreditConfig.getAdaptationKindNo(), WKF_NODE.quota_calc, null, null, User.getRegNo(request));
                        formQuotaCalc = formService.getFormData(formId);
                        getObjValue(formQuotaCalc, mfCusCreditAdjustApply);
                        this.changeFormProperty(formQuotaCalc, "kindNo", "initValue", mfCusCreditConfig.getAdaptationKindNo());
                        this.changeFormProperty(formQuotaCalc, "isCeilingLoop", "initValue", mfCusCreditAdjustApply.getAdjIsCeilingLoop());
                        this.changeFormProperty(formQuotaCalc, "creditSum", "initValue", MathExtend.moneyStr(mfCusCreditAdjustApply.getAdjCreditSum()).replaceAll(",", ""));
                        model.addAttribute("ifQuotaCalc", mfSysKind.getIfCreditCalc());
                        model.addAttribute("formQuotaCalc", formQuotaCalc);
                    }
                }
            }

        } else {
            mfCusCreditApply = (MfCusCreditApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditApply"),MfCusCreditApply.class);
            //初始化综合授信描述
            StringBuffer creditDesc =new StringBuffer("额度");
            creditDesc.append(mfCusCreditApply.getCreditSum()/10000);
            creditDesc.append("万元，业务品种为贷款，授信期限");
            creditDesc.append(mfCusCreditApply.getCreditTerm()).append("个月,提款期");
            creditDesc.append(mfCusCreditApply.getCreditTerm()).append("个月，单笔业务期限不超过").append(mfCusCreditApply.getCreditTerm());
            creditDesc.append("个月");
            mfCusCreditApply.setCreditDesc(creditDesc.toString());




            //额度测算表单
            creditId = mfCusCreditApply.getTemplateCreditId();
            if(StringUtil.isNotEmpty(creditId)){
                mfCusCreditConfig.setCreditId(creditId);
                mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
                if(mfCusCreditConfig != null && StringUtil.isNotEmpty(mfCusCreditConfig.getAdaptationKindNo())){
                    MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(mfCusCreditConfig.getAdaptationKindNo());
                    if(mfSysKind!=null){
                        if(StringUtil.isEmpty(mfCusCreditApply.getGuaranteeScheme())){
                            MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
                            mfSysFeeItem.setFeeStdNo(mfCusCreditConfig.getAdaptationKindNo());
                            List<MfSysFeeItem> mfSysFeeItemList = mfSysFeeItemFeign.getAll(mfSysFeeItem);
                            Double danBao = 0.00;
                            Double pinShen = 0.00;
                            for (int i = 0; i < mfSysFeeItemList.size(); i++) {
                                mfSysFeeItem = mfSysFeeItemList.get(i);
                                if("1".equals(mfSysFeeItem.getItemNo())){
                                    danBao =mfSysFeeItem.getRateScale();
                                }
                                if("2".equals(mfSysFeeItem.getItemNo())){
                                    pinShen =mfSysFeeItem.getRateScale();
                                }
                            }
                            if(BizPubParm.BUS_MODEL_10.equals(mfCusCreditConfig.getBusModel()) && StringUtil.isEmpty(mfCusCreditApply.getGuaranteeScheme())){

                                //同意提供{申请金额}万元贷款担保，期限{担保期限}月。  同意担保换期权，行权份额占行权后股权总额的    %。  担保年费率：{担保费率}%,评审费率：{评审费率}%。
                                StringBuffer guaranteeScheme =new StringBuffer("同意提供");
                                guaranteeScheme.append(mfCusCreditApply.getCreditSum()/10000);
                                guaranteeScheme.append("万元贷款担保，期限").append(Double.valueOf(mfCusCreditApply.getCreditTerm())/12).append("+0年，");
                                guaranteeScheme.append("实际用款金额为").append(mfCusCreditApply.getCreditSum()/10000).append("万元。合作银行为：");
                                MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
                                mfCusAgenciesCredit.setCreditAppId(mfCusCreditApply.getCreditAppId());
                                List<MfCusAgenciesCredit> list = mfCusCreditApplyFeign.getByCreditAppId(mfCusAgenciesCredit);
                                if(null != list && list.size() > 0){
                                    MfCusAgenciesCredit temp;
                                    int count =0;
                                    for(int i = 0;i<list.size();i++){
                                        temp = list.get(i);
                                        count++;
                                        if(list.size()>1 && list.size() == count){
                                            guaranteeScheme.append(temp.getAgenciesName());
                                        }else{
                                            guaranteeScheme.append(temp.getAgenciesName()).append("、");
                                        }
                                  }
                                }
                                guaranteeScheme.append("。\r\n");
                                // mf_cus_agencies_credit
                                guaranteeScheme.append("担保年费率：").append(danBao).append("%,评审费率：").append(pinShen).append("%").append("。");
                                guaranteeScheme.append("（注：在我司《关于调整部分科技型小微企业收费标准的通知》适用期间内新发生的业务，执行科技型小微企业收费政策，即担保费率1%/年，评审费率/次；政策适用期间之外发生的业务，按照我司正常收费标准收费。）");
                                mfCusCreditApply.setGuaranteeScheme(guaranteeScheme.toString());
                            }
                            //该公司符合创业保白名单准入，准入条件为        。已经完成对借款主体、实控人      的信用评价、债项评价和实地考察。 评审时点借款企业银行借款金额为       万元、实控人及其配偶个人经营贷金额合计       万元，本次提供     万元额度后，未超过近12个月纳税收入    万元 的40%。
                            if(BizPubParm.BUS_MODEL_10.equals(mfCusCreditConfig.getBusModel()) && StringUtil.isEmpty( mfCusCreditApply.getOpinion())){
                                MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
                                mfCusHighInfo.setCusNo(mfCusCreditApply.getCusNo());
                                mfCusHighInfo.setHighCusType("4");
                                String shikongren="";
                                List<MfCusHighInfo> mfCusHighInfoList = mfCusHighInfoFeign.findByEntity(mfCusHighInfo);
                                if(mfCusHighInfoList.size()>0){
                                    shikongren = mfCusHighInfoList.get(0).getHighName();
                                }
                                StringBuffer opinion =new StringBuffer("该公司符合");
                                opinion.append(mfSysKind.getKindName()).append("产品主体资格认定,");
                                opinion.append("已经完成对借款主体以及实控人").append(shikongren);
                                opinion.append("的信用评价、债项评价和实地考察。");
                                opinion.append("\r\n");
                                opinion.append("评审时点借款企业银行借款金额（或者可用银行授信额度）为 ").append(mfCusCreditApply.getCreditSum()/10000).append("万元、融资租赁余额      万元，对外担保金额       万元 ，实控人及其配偶个人银行借款金额合计          万元，");
                                opinion.append("本次提供").append(mfCusCreditApply.getCreditSum()/10000).append("万元额度后，");
                                // 取超过近12个月纳税收入   {纳税收入（额度测算中字段）}
                                double calc3 = mfCusCreditApply.getCalc3()!= null ?mfCusCreditApply.getCalc3():0;
                                opinion.append("未超过近12个月纳税收入").append(calc3/10000).append("万元的30%。");
                                mfCusCreditApply.setOpinion(opinion.toString());
                            }

                            //反担保方案：{保证人姓名（多个逗号隔开，不显示企业）} 承担个人连带责任保证。
                            if(BizPubParm.BUS_MODEL_10.equals(mfCusCreditConfig.getBusModel()) && StringUtil.isEmpty(mfCusCreditApply.getCounterGuaranteeScheme())){
                                if(StringUtil.isEmpty( mfCusCreditApply.getCounterGuaranteeScheme())){
                                    StringBuffer counterGuaranteeScheme =new StringBuffer();
                                    List<MfBusCollateralDetailRel> list = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(mfCusCreditApply.getCreditAppId(),"pledge");
                                    if(null != list && list.size()>0){
                                        for (int i = 0; i < list.size(); i++) {
                                            // 获取业务关联的保证信息
                                            MfAssureInfo mfAssureInfo = new MfAssureInfo();
                                            mfAssureInfo.setId(list.get(i).getCollateralId());
                                            mfAssureInfo = assureInterfaceFeign.getById(mfAssureInfo);
                                            //只拼接自然人反担保
                                            if (mfAssureInfo != null && "2".equals(mfAssureInfo.getAssureType())) {
                                                counterGuaranteeScheme.append(mfAssureInfo.getAssureName()).append(",");
                                            }
                                        }
                                    }
                                    counterGuaranteeScheme.append("承担个人连带责任保证。");
                                    mfCusCreditApply.setCounterGuaranteeScheme(counterGuaranteeScheme.toString());
                                }
                            }
                        }
                    }
                    if(mfSysKind != null && "1".equals(mfSysKind.getIfCreditCalc())){
                        formId = prdctInterfaceFeign.getFormId(mfCusCreditConfig.getAdaptationKindNo(), WKF_NODE.quota_calc, null, null, User.getRegNo(request));
                        formQuotaCalc = formService.getFormData(formId);
                        getObjValue(formQuotaCalc, mfCusCreditApply);
                        this.changeFormProperty(formQuotaCalc, "kindNo", "initValue", mfCusCreditConfig.getAdaptationKindNo());
                        this.changeFormProperty(formQuotaCalc, "creditSum", "initValue", MathExtend.moneyStr(mfCusCreditApply.getCreditSum()).replaceAll(",", ""));
                        model.addAttribute("ifQuotaCalc", mfSysKind.getIfCreditCalc());
                        model.addAttribute("formQuotaCalc", formQuotaCalc);
                    }
                }
            }
            if(StringUtil.isEmpty(mfCusCreditApply.getTongDunScore())){
                mfCusCreditApply.setTongDunScore(cusInterfaceFeign.getTongDunByCusNo(mfCusCreditApply.getCusNo(),mfCusCreditApply.getCreditAppId()));
            }
            isLegalDegreeFlag =mfCusCreditApply.getIsLegalDegreeFlag();
            isGaoKeJiFlag =  mfCusCreditApply.getIsGaoKeJiFlag();
            if(StringUtil.isEmpty(mfCusCreditApply.getIfFirstApply())){
                mfCusCreditApply.setIfFirstApply(mfBusApplyFeign.getIfFirstLoan(mfCusCreditApply.getCusNo()));
            }
            if(StringUtil.isEmpty(mfCusCreditApply.getIfFirstLoan())){
                mfCusCreditApply.setIfFirstLoan(mfBusApplyFeign.getIfFirstLoan(mfCusCreditApply.getCusNo()));
            }
            getObjValue(formcreditapply0001,mfCusCreditApply);
            if(StringUtil.isNotEmpty(mfCusCreditApply.getManageEndDate())){
                manageEndDate =mfCusCreditApply.getManageEndDate();
            }
        }

        //客户信息
        String cusNo = (String) dataMap.get("cusNo");
        Map<String,String> cusInfoMap = new HashMap<String,String>();
        //cusInfoMap.put("isLegalDegreeFlag","0");
        //cusInfoMap.put("legalUniversity","");
        // cusInfoMap.put("guaranteeOuterFlag","1");
        cusInfoMap.put("dishonestFlag","1");
        cusInfoMap.put("execNoticeFlag","1");
        if(StringUtil.isNotEmpty(cusNo)){
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
            if(mfCusCustomer!=null){
                String cusType = mfCusCustomer.getCusType();
                if(cusType.startsWith("1")){
                    MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
                    mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
                    if(mfCusCorpBaseInfo!=null){
                        String legalDegree = mfCusCorpBaseInfo.getLegalDegree();
                        if(StringUtil.isNotEmpty(legalDegree)&&("1".equals(legalDegree)||"2".equals(legalDegree)||"3".equals(legalDegree)||"9".equals(legalDegree))){
                            if(StringUtil.isEmpty(isLegalDegreeFlag )){
                                cusInfoMap.put("isLegalDegreeFlag","1");
                            }
                        }
                        String ifHighTech = mfCusCorpBaseInfo.getIfHighTech();
                        if(StringUtil.isNotEmpty(isGaoKeJiFlag)&&StringUtil.isNotEmpty(ifHighTech)&&("2".equals(ifHighTech)||"3".equals(ifHighTech))){
                            cusInfoMap.put("isGaoKeJiFlag","1");
                        }
                    }
                }
                MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
                mfCusDishonestInfo.setCusNo(cusNo);
                List<MfCusDishonestInfo> mfCusDishonestInfoList = mfCusDishonestInfoFeign.getAllList(mfCusDishonestInfo);
                if(mfCusDishonestInfoList.size()>0){
                    cusInfoMap.put("dishonestFlag","0");
                }
                MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
                mfCusExecNotice.setCusNo(cusNo);
                List<MfCusExecNotice> mfCusExecNoticeList = mfCusExecNoticeFeign.getAllList(mfCusExecNotice);
                if(mfCusDishonestInfoList.size()>0){
                    cusInfoMap.put("execNoticeFlag","0");
                }
                //获取实际控制人信息
                MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
                mfCusHighInfo.setCusNo(cusNo);
                mfCusHighInfo.setHighCusType("4");
                List<MfCusHighInfo> mfCusHighInfoList = cusInterfaceFeign.findMfCusHighInfoListByCusNo(mfCusHighInfo);
                if(mfCusHighInfoList.size()>0){
                    cusInfoMap.put("highName",mfCusHighInfoList.get(0).getHighName());
                    cusInfoMap.put("highPhone",mfCusHighInfoList.get(0).getPhone());
                    cusInfoMap.put("underColleges",mfCusHighInfoList.get(0).getUnderColleges());
                    cusInfoMap.put("major",mfCusHighInfoList.get(0).getMajor());
                    cusInfoMap.put("education",mfCusHighInfoList.get(0).getEducation());
                    cusInfoMap.put("majorDegree",mfCusHighInfoList.get(0).getMajorDegree());
                    cusInfoMap.put("highIdNum",mfCusHighInfoList.get(0).getIdNum());
                    cusInfoMap.put("highCusResume",mfCusHighInfoList.get(0).getHighCusResume());
                    cusInfoMap.put("phone",mfCusHighInfoList.get(0).getPhone());
                    cusInfoMap.put("majorDegree",mfCusHighInfoList.get(0).getMajorDegree());
                }
            }
        }

        if(StringUtil.isNotEmpty(creditAppId)){
            //获取保证人
            List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(creditAppId);
            String assureName ="";
            if(mfAssureInfoList!=null&&mfAssureInfoList.size()>0){
                for (int i = 0; i < mfAssureInfoList.size(); i++) {
                    assureName =assureName+mfAssureInfoList.get(i).getAssureName()+",";
                }
            }
            cusInfoMap.put("assureName",assureName);
            //获取合作银行
            mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
            String agenciesButtName ="";
            String agenciesButtPhone ="";
            if(mfCusCreditApply!=null){
                String agenciesId = mfCusCreditApply.getAgenciesId();
                if(StringUtil.isNotEmpty(agenciesId)){
                    cusInfoMap.put("agenciesName",mfCusCreditApply.getAgenciesName());
                    String [] agenciesIds = agenciesId.split("\\|");
                    for (int i = 0; i < agenciesIds.length; i++) {
                        String id = agenciesIds[i];
                        MfBusAgencies mfBusAgencies = new MfBusAgencies();
                        mfBusAgencies.setAgenciesId(id);
                        mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
                        if(mfBusAgencies!=null){
                            if(StringUtil.isNotEmpty(mfBusAgencies.getAgenciesButtName())){
                                agenciesButtName =agenciesButtName+mfBusAgencies.getAgenciesButtName()+"|";
                            }
                            if(StringUtil.isNotEmpty(mfBusAgencies.getAgenciesButtPhone())){
                                agenciesButtPhone =agenciesButtPhone+mfBusAgencies.getAgenciesButtPhone()+"|";
                            }
                        }
                    }
                    cusInfoMap.put("agenciesButtName",agenciesButtName);
                    cusInfoMap.put("agenciesButtPhone",agenciesButtPhone);
                }

            }
        }

        getObjValue(formcreditapply0001, cusInfoMap);

        //都为空的时候再查客户表
        if(mfCusCreditApply != null){
            if(StringUtil.isEmpty(mfCusCreditApply.getIsXiaoWeiFlag()) && StringUtil.isEmpty(mfCusCreditApply.getIsJinYingFlag())
                    && StringUtil.isEmpty(mfCusCreditApply.getIsBeiJingFlag()) && StringUtil.isEmpty(mfCusCreditApply.getIsGaoKeJiFlag())
                    && StringUtil.isEmpty(mfCusCreditApply.getInventionPatent())){
                Map<String,String> networkMap = cusInterfaceFeign.getCusNewWorkMap(cusNo);
                getObjValue(formcreditapply0001, networkMap);
            }
        }

        TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
        String cusFinRptShowFlag = "0";
        List <ParmDic> dicList = (List <ParmDic>) new CodeUtils().getCacheByKeyName("CUS_FIN_DATA_SHOW_FLAG");
        if (dicList != null && dicList.size() > 0) {
            for (ParmDic parmDic : dicList) {
                if (task.getActivityName().equals(parmDic.getOptCode())) {
                    cusFinRptShowFlag = parmDic.getRemark();
                }
            }
        }

        //工程担保
        if(BizPubParm.BUS_MODEL_12.equals(mfCusCreditConfig.getBusModel())){
            if(StringUtil.isNotEmpty(dataMap.get("authority").toString())){
                this.changeFormProperty(formcreditapply0001, "authority", "initValue", dataMap.get("authority"));
            }
            model.addAttribute("projectShowFlag", BizPubParm.YES_NO_Y);
            if(StringUtil.isNotEmpty(mfCusCreditApply.getBreedNo())) {
                String per = "";
                String[] arrayStr = mfCusCreditApply.getBreedNo().split("\\|");
                for (String str : arrayStr) {
                    per += "|" + new CodeUtils().getMapByKeyName("BUS_BREED").get(str);
                }
                this.changeFormProperty(formcreditapply0001, "breedName", "initValue", per.substring(1,per.length()));
            }
            //查询最近一期财务报表
            MfCusReportAcount mfCusReportAcount = mfCusReportAcountFeign.getReportOrdeyByWeek(cusNo);
            if(mfCusReportAcount!=null&&StringUtil.isNotEmpty(mfCusReportAcount.getWeeks())){
                String weeks = mfCusReportAcount.getWeeks();
                if(StringUtil.isNotEmpty(weeks)&&weeks.length()==6){
                    weeks = weeks.substring(0,4)+"年"+weeks.substring(4,6)+"月";
                }else if(StringUtil.isNotEmpty(weeks)&&weeks.length()==4){
                    weeks = weeks.substring(0,4)+"年";
                }
                model.addAttribute("weeks", weeks);
            }
        }

        model.addAttribute("cusFinRptShowFlag", cusFinRptShowFlag);
        request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
        request.setAttribute("modelNo", String.valueOf(dataMap.get("modelNo")));
        request.setAttribute("json", json);
        request.setAttribute("kindMap", dataMap.get("kindMap"));
        request.setAttribute("appId", dataMap.get("appId"));
        request.setAttribute("creditType", creditType);
        request.setAttribute("wkfAppId", dataMap.get("wkfAppId"));
        if(StringUtil.isNotEmpty(manageEndDate)){
            this.changeFormProperty(formcreditapply0001, "manageEndDate", "initValue", DateUtil.getShowDateTime(mfCusCreditApply.getManageEndDate()));
        }
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("baseType", String.valueOf(dataMap.get("cusType")).substring(0, 1));
        model.addAttribute("creditFlag", "1");
        model.addAttribute("query", "");
        model.addAttribute("nodeNo", WKF_NODE.report.getNodeNo());
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("creditAppId", creditAppId);
        model.addAttribute("cusNo", dataMap.get("cusNo"));
        model.addAttribute("processId", processId);
        return "/component/auth/MfCusCreditApply_Detail";
    }
    /**
     * 打开批单打印页面
     *
     * @author LJW
     * date 2017-2-28
     */
    @RequestMapping(value = "/creditBatchPrinting")
    public String creditBatchPrinting(Model model, String creditAppId) throws Exception {
        FormService formService = new FormService();
        String formId = null;
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String wkfAppId = request.getParameter("wkfAppId");
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("wkfAppId", wkfAppId);
        //获得尽调报告数据
        dataMap = mfCusCreditApplyFeign.getCreditReportDataMap(dataMap);
        JSONObject json = new JSONObject();
        json.put("mfCusPorductCreditList", JSONArray.fromObject(dataMap.get("mfCusPorductCreditList")));
        json.put("mfSysKinds", JSONArray.fromObject(dataMap.get("mfSysKinds")));
        String creditModel = String.valueOf(dataMap.get("creditModel"));//授信模式1客户授信2项目授信
        String creditType = String.valueOf(dataMap.get("creditType"));
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId((String)dataMap.get("creditId"), WKF_NODE.credit_batch_printing, null, null, User.getRegNo(request));
        } else if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId((String)dataMap.get("creditId"), WKF_NODE.credit_batch_printing, null, null, User.getRegNo(request));
        } else if (BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId("creditRenew" + creditModel, WKF_NODE.credit_batch_printing, null, null, User.getRegNo(request));
        }else {
        }
        FormData formcreditapply0001 = formService.getFormData(formId);
        getFormValue(formcreditapply0001);
        String adjustAppId = null;
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType) || BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            if (dataMap.get("mfCusCreditContract") != null) {
                MfCusCreditContract mfCusCreditContract = JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditContract"), MfCusCreditContract.class);
                getObjValue(formcreditapply0001, mfCusCreditContract);
            } else {
                getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
            }
            MfCusCreditAdjustApply mfCusCreditAdjustApply = (MfCusCreditAdjustApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditAdjustApply"), MfCusCreditAdjustApply.class);
            adjustAppId = mfCusCreditAdjustApply.getAdjustAppId();
            getObjValue(formcreditapply0001, mfCusCreditAdjustApply);

            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);

            this.changeFormProperty(formcreditapply0001, "creditSum", "initValue", mfCusCreditAdjustApply.getAdjCreditSum().toString());
            this.changeFormProperty(formcreditapply0001, "creditTerm", "initValue", mfCusCreditAdjustApply.getAdjCreditTerm().toString());
            this.changeFormProperty(formcreditapply0001, "isCeilingLoop", "initValue", mfCusCreditAdjustApply.getAdjIsCeilingLoop());
            this.changeFormProperty(formcreditapply0001, "beginDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjBeginDate()));
            this.changeFormProperty(formcreditapply0001, "endDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjEndDate()));

            creditAppId = adjustAppId;
        } else {
            getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
        }


        TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
        String cusFinRptShowFlag = "0";
        List <ParmDic> dicList = (List <ParmDic>) new CodeUtils().getCacheByKeyName("CUS_FIN_DATA_SHOW_FLAG");
        if (dicList != null && dicList.size() > 0) {
            for (ParmDic parmDic : dicList) {
                if (task.getActivityName().equals(parmDic.getOptCode())) {
                    cusFinRptShowFlag = parmDic.getRemark();
                }
            }
        }
        model.addAttribute("cusFinRptShowFlag", cusFinRptShowFlag);
        request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
        request.setAttribute("modelNo", String.valueOf(dataMap.get("modelNo")));
        request.setAttribute("json", json);
        request.setAttribute("kindMap", dataMap.get("kindMap"));
        request.setAttribute("appId", dataMap.get("appId"));
        request.setAttribute("creditType", creditType);
        request.setAttribute("wkfAppId", dataMap.get("wkfAppId"));
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("baseType", String.valueOf(dataMap.get("cusType")).substring(0, 1));
        model.addAttribute("creditFlag", "1");
        model.addAttribute("query", "");
        model.addAttribute("nodeNo", WKF_NODE.credit_batch_printing.getNodeNo());
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("creditAppId", creditAppId);
        model.addAttribute("cusNo", dataMap.get("cusNo"));
        return "/component/auth/MfCusCreditApply_BatchPrint";
    }
    /**
     * 打开授信担保意向书页面
     *
     * @author LJW
     * date 2017-2-28
     */
    @RequestMapping(value = "/creditLetterIntent")
    public String creditLetterIntent(Model model, String creditAppId) throws Exception {
        FormService formService = new FormService();
        String formId = null;
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String wkfAppId = request.getParameter("wkfAppId");
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("wkfAppId", wkfAppId);
        //获得尽调报告数据
        dataMap = mfCusCreditApplyFeign.getCreditReportDataMap(dataMap);
        JSONObject json = new JSONObject();
        json.put("mfCusPorductCreditList", JSONArray.fromObject(dataMap.get("mfCusPorductCreditList")));
        json.put("mfSysKinds", JSONArray.fromObject(dataMap.get("mfSysKinds")));
        String creditModel = String.valueOf(dataMap.get("creditModel"));//授信模式1客户授信2项目授信
        String creditType = String.valueOf(dataMap.get("creditType"));
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId((String)dataMap.get("creditId"), WKF_NODE.credit_letter_intent, null, null, User.getRegNo(request));
        } else if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId((String)dataMap.get("creditId"), WKF_NODE.credit_letter_intent, null, null, User.getRegNo(request));
        } else if (BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId("creditRenew" + creditModel, WKF_NODE.credit_letter_intent, null, null, User.getRegNo(request));
        }else {
        }
        FormData formcreditapply0001 = formService.getFormData(formId);
        getFormValue(formcreditapply0001);
        String adjustAppId = null;
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType) || BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            if (dataMap.get("mfCusCreditContract") != null) {
                MfCusCreditContract mfCusCreditContract = JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditContract"), MfCusCreditContract.class);
                getObjValue(formcreditapply0001, mfCusCreditContract);
            } else {
                getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
            }
            MfCusCreditAdjustApply mfCusCreditAdjustApply = (MfCusCreditAdjustApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditAdjustApply"), MfCusCreditAdjustApply.class);
            adjustAppId = mfCusCreditAdjustApply.getAdjustAppId();
            getObjValue(formcreditapply0001, mfCusCreditAdjustApply);

            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);

            this.changeFormProperty(formcreditapply0001, "creditSum", "initValue", mfCusCreditAdjustApply.getAdjCreditSum().toString());
            this.changeFormProperty(formcreditapply0001, "creditTerm", "initValue", mfCusCreditAdjustApply.getAdjCreditTerm().toString());
            this.changeFormProperty(formcreditapply0001, "isCeilingLoop", "initValue", mfCusCreditAdjustApply.getAdjIsCeilingLoop());
            this.changeFormProperty(formcreditapply0001, "beginDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjBeginDate()));
            this.changeFormProperty(formcreditapply0001, "endDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjEndDate()));

            creditAppId = adjustAppId;
        } else {
            getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
        }


        TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
        String cusFinRptShowFlag = "0";
        List <ParmDic> dicList = (List <ParmDic>) new CodeUtils().getCacheByKeyName("CUS_FIN_DATA_SHOW_FLAG");
        if (dicList != null && dicList.size() > 0) {
            for (ParmDic parmDic : dicList) {
                if (task.getActivityName().equals(parmDic.getOptCode())) {
                    cusFinRptShowFlag = parmDic.getRemark();
                }
            }
        }
        model.addAttribute("cusFinRptShowFlag", cusFinRptShowFlag);
        request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
        request.setAttribute("modelNo", String.valueOf(dataMap.get("modelNo")));
        request.setAttribute("json", json);
        request.setAttribute("kindMap", dataMap.get("kindMap"));
        request.setAttribute("appId", dataMap.get("appId"));
        request.setAttribute("creditType", creditType);
        request.setAttribute("wkfAppId", dataMap.get("wkfAppId"));
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("baseType", String.valueOf(dataMap.get("cusType")).substring(0, 1));
        model.addAttribute("creditFlag", "1");
        model.addAttribute("query", "");
        model.addAttribute("nodeNo", WKF_NODE.credit_letter_intent.getNodeNo());
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("creditAppId", creditAppId);
        model.addAttribute("cusNo", dataMap.get("cusNo"));
        return "/component/auth/MfCusCreditApply_LetterIntent";
    }
    /**
     * 打开首次委托担保
     *
     * @author LJW
     * date 2017-2-28
     */
    @RequestMapping(value = "/firstApply")
    public String firstApply(Model model, String creditAppId) throws Exception {
        FormService formService = new FormService();
        String formId = null;
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String wkfAppId = request.getParameter("wkfAppId");
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("wkfAppId", wkfAppId);
        String kindNo="";
        //获得尽调报告数据
        dataMap = mfCusCreditApplyFeign.getCreditReportDataMap(dataMap);
        JSONObject json = new JSONObject();
        json.put("mfCusPorductCreditList", JSONArray.fromObject(dataMap.get("mfCusPorductCreditList")));
        String creditModel = String.valueOf(dataMap.get("creditModel"));//授信模式1客户授信2项目授信
        String creditType = String.valueOf(dataMap.get("creditType"));
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId((String)dataMap.get("creditId"), WKF_NODE.first_apply, null, null, User.getRegNo(request));
        } else if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId((String)dataMap.get("creditId"), WKF_NODE.first_apply, null, null, User.getRegNo(request));
        } else if (BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            formId = prdctInterfaceFeign.getFormId("creditRenew" + creditModel, WKF_NODE.first_apply, null, null, User.getRegNo(request));
        }else {
        }
        FormData formcreditapply0001 = formService.getFormData(formId);
        getFormValue(formcreditapply0001);
        String adjustAppId = null;
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType) || BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            if (dataMap.get("mfCusCreditContract") != null) {
                MfCusCreditContract mfCusCreditContract = JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditContract"), MfCusCreditContract.class);
                getObjValue(formcreditapply0001, mfCusCreditContract);
            } else {
                getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
            }
            MfCusCreditAdjustApply mfCusCreditAdjustApply = (MfCusCreditAdjustApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditAdjustApply"), MfCusCreditAdjustApply.class);
            adjustAppId = mfCusCreditAdjustApply.getAdjustAppId();
            getObjValue(formcreditapply0001, mfCusCreditAdjustApply);
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
            this.changeFormProperty(formcreditapply0001, "creditSum", "initValue", mfCusCreditAdjustApply.getAdjCreditSum().toString());
            this.changeFormProperty(formcreditapply0001, "creditTerm", "initValue", mfCusCreditAdjustApply.getAdjCreditTerm().toString());
            this.changeFormProperty(formcreditapply0001, "isCeilingLoop", "initValue", mfCusCreditAdjustApply.getAdjIsCeilingLoop());
            this.changeFormProperty(formcreditapply0001, "beginDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjBeginDate()));
            this.changeFormProperty(formcreditapply0001, "endDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjEndDate()));

            creditAppId = adjustAppId;
        } else {
            MfCusCreditApply mfCusCreditApply =  JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditApply"), MfCusCreditApply.class);
            mfCusCreditApply.setAgenciesId(null);
            mfCusCreditApply.setAgenciesName(null);
            getObjValue(formcreditapply0001, mfCusCreditApply);
        }


        TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);


        String cusFinRptShowFlag = "0";
        List <ParmDic> dicList = (List <ParmDic>) new CodeUtils().getCacheByKeyName("CUS_FIN_DATA_SHOW_FLAG");
        if (dicList != null && dicList.size() > 0) {
            for (ParmDic parmDic : dicList) {
                if (task.getActivityName().equals(parmDic.getOptCode())) {
                    cusFinRptShowFlag = parmDic.getRemark();
                }
            }
        }
        String cusNo = (String) dataMap.get("cusNo");
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        MfBusApply mfBusApply = new MfBusApply();
        String kindNos = (String) dataMap.get("kindNos");
        if (StringUtil.isNotEmpty(kindNos)) {
            String[] kindNoArr = kindNos.split("\\|");
            kindNo = kindNoArr[0];
            List<OptionsList> kindNoList = new ArrayList<OptionsList>();
            //根据产品种类编号获取产品信息
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
            OptionsList optionsList = new  OptionsList();
            optionsList.setOptionId(WaterIdUtil.getWaterId());
            optionsList.setOptionLabel(mfSysKind.getKindName());
            optionsList.setOptionLabel(kindNo);
            this.changeFormProperty(formcreditapply0001, "kindNo", "optionArray", kindNoList);
        } else {
            // 获取产品号 默认取线下
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind.setKindProperty("2");
            mfSysKind.setUseFlag("1");
            mfSysKind.setCusType(mfCusCustomer.getCusType());
            mfSysKind.setBrNo(User.getOrgNo(request));
            mfSysKind.setRoleNoArray(User.getRoleNo(request));
            List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
            if (mfSysKindList != null && mfSysKindList.size() > 0) {
                kindNo = mfSysKindList.get(0).getKindNo();
            }
        }
        //根据产品种类编号获取产品信息
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
        String vouType = mfSysKind.getVouTypeDef();
        mfSysKind.setVouType(vouType);//表单显示默认的担保方式
        String busModel = mfSysKind.getBusModel();//业务模式
        String cusType = mfCusCustomer.getCusType();
        //授信合同和
        String  creditPactNo = (String) dataMap.get("creditPactNo");
        mfBusApply.setCreditPactNo(creditPactNo);
        mfBusApply.setFincRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getFincRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
        mfBusApply.setOverRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getOverRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
        mfBusApply.setCmpdRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getCmpdRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
        mfBusApply.setCusMngName(mfCusCustomer.getCusMngName());
        mfBusApply.setCusMngNo(mfCusCustomer.getCusMngNo());
        mfBusApply.setNominalRate(mfBusApply.getFincRate());
        mfBusApply.setChannelSource(mfCusCustomer.getChannelSource());
        mfBusApply.setChannelSourceNo(mfCusCustomer.getChannelSourceNo());
        mfBusApply.setManageOpNo1( User.getRegNo(request));
        mfBusApply.setManageOpName1( User.getRegName(request));
        mfBusApply.setAppTimeShow(DateUtil.getDate());
        getObjValue(formcreditapply0001, mfBusApply);
        getObjValue(formcreditapply0001, mfSysKind);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfSysKind.getRateType()).getRemark();
        this.changeFormProperty(formcreditapply0001, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formcreditapply0001, "overRate", "unit", rateUnit);
        this.changeFormProperty(formcreditapply0001, "cmpdRate", "unit", rateUnit);
        //处理金额，double大于十位会变成科学计数法11111111
        DecimalFormat df = new DecimalFormat(",##0.00");
        if ((mfSysKind.getMinAmt() != null) && (mfSysKind.getMaxAmt() != null)) {
            double minAmtD = mfSysKind.getMinAmt();
            double maxAmtD = mfSysKind.getMaxAmt();
            String minAmt = df.format(minAmtD);//融资金额下限
            String maxAmt = df.format(maxAmtD);//融资金额上限
            String amt = minAmt + "-" + maxAmt + "元";
            //this.changeFormProperty(formcreditapply0001, "appAmt", "alt", amt);
            dataMap.put("minAmt", String.valueOf(minAmtD));
            dataMap.put("maxAmt", String.valueOf(maxAmtD));
        }
        //处理期限
        if (null != mfSysKind.getTermType()) {
            String termType = mfSysKind.getTermType();//合同期限 1月 2日
            int minTerm = mfSysKind.getMinTerm();//合同期限下限
            int maxTerm = mfSysKind.getMaxTerm();//合同期限上限
            String term = "";
            if ("1".equals(termType)) {
                term = minTerm + "个月-" + maxTerm + "个月";
            } else if ("2".equals(termType)) {
                term = minTerm + "天-" + maxTerm + "天";
            }else {
            }
            dataMap.put("minTerm", String.valueOf(minTerm));
            dataMap.put("maxTerm", String.valueOf(maxTerm));
            dataMap.put("termType", termType);
            // this.changeFormProperty(formcreditapply0001, "term", "alt", term);
        }
        //处理基础利率类型
        String baseRateType = mfSysKind.getBaseRateType();
        OptionsList baseRateTypeOp;
        List<OptionsList> baseRateTypeList = new ArrayList<OptionsList>();
        if (StringUtil.isNotEmpty(baseRateType)) {
            String[] baseRateTypeArray = baseRateType.split("\\|");
            Map<String, String> dicMap = new CodeUtils().getMapByKeyName("BASE_RATE_TYPE");
            for (int i = 0; i < baseRateTypeArray.length; i++) {
                baseRateTypeOp = new OptionsList();
                baseRateTypeOp.setOptionLabel(dicMap.get(baseRateTypeArray[i]));
                baseRateTypeOp.setOptionValue(baseRateTypeArray[i]);
                baseRateTypeOp.setOptionId(WaterIdUtil.getWaterId());
                baseRateTypeList.add(baseRateTypeOp);
            }
        } else {
            List<ParmDic> pds = (List<ParmDic>) new CodeUtils().getCacheByKeyName("BASE_RATE_TYPE");
            for (ParmDic parmDic : pds) {
                baseRateTypeOp = new OptionsList();
                baseRateTypeOp.setOptionLabel(parmDic.getOptName());
                baseRateTypeOp.setOptionValue(parmDic.getOptCode());
                baseRateTypeList.add(baseRateTypeOp);
            }
        }
        this.changeFormProperty(formcreditapply0001, "baseRateType", "optionArray", baseRateTypeList);
        //处理计息方式
        String icType = mfSysKind.getIcType();
        OptionsList icTypeOp;
        List<OptionsList> icTypeList = new ArrayList<OptionsList>();
        if (StringUtil.isNotEmpty(icType)) {
            String[] icTypeArray = icType.split("\\|");
            Map<String, String> dicMap = new CodeUtils().getMapByKeyName("IC_TYPE");
            for (int i = 0; i < icTypeArray.length; i++) {
                icTypeOp = new OptionsList();
                icTypeOp.setOptionLabel(dicMap.get(icTypeArray[i]));
                icTypeOp.setOptionValue(icTypeArray[i]);
                icTypeOp.setOptionId(WaterIdUtil.getWaterId());
                icTypeList.add(icTypeOp);
            }
        } else {
            List<ParmDic> pdIcTypes = (List<ParmDic>) new CodeUtils().getCacheByKeyName("IC_TYPE");
            for (ParmDic parmDic : pdIcTypes) {
                icTypeOp = new OptionsList();
                icTypeOp.setOptionLabel(parmDic.getOptName());
                icTypeOp.setOptionValue(parmDic.getOptCode());
                icTypeList.add(icTypeOp);
            }
        }
        this.changeFormProperty(formcreditapply0001, "icType", "optionArray", icTypeList);
        //处理还款方式
        List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
        this.changeFormProperty(formcreditapply0001, "repayType", "optionArray", repayTypeList);
        this.changeFormProperty(formcreditapply0001, "rateType", "initValue", mfSysKind.getRateType());

        JSONObject jsonNew = new JSONObject();
        jsonNew.put("rateType", JSONObject.fromObject(rateTypeMap));
        String ajaxData = json.toString();
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("ajaxData", json);
        model.addAttribute("busModel", busModel);
        model.addAttribute("kindNo", kindNo);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("cusType", cusType);
        model.addAttribute("query", "");
        model.addAttribute("cusFinRptShowFlag", cusFinRptShowFlag);
        request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
        request.setAttribute("modelNo", String.valueOf(dataMap.get("modelNo")));
        request.setAttribute("json", json);
        request.setAttribute("kindMap", dataMap.get("kindMap"));
        request.setAttribute("creditType", creditType);
        request.setAttribute("wkfAppId", dataMap.get("wkfAppId"));
        model.addAttribute("baseType", String.valueOf(dataMap.get("cusType")).substring(0, 1));
        model.addAttribute("creditFlag", "1");
        model.addAttribute("query", "");
        model.addAttribute("nodeNo", WKF_NODE.first_apply.getNodeNo());
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("creditAppId", creditAppId);
        return "/component/auth/MfCusCreditApply_firstApply";
    }

    @RequestMapping(value = "/getFeeHtmlForKindAjax")
    @ResponseBody
    public Map<String, Object> getFeeHtmlForKindAjax(String kindNo,String creditAppId) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //处理费用信息
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(creditAppId);
        mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
        Double danbaoFeeRate =0.00;
        Double pinshenFeeRate=0.00 ;
        if (mfCusCreditApply != null) {
            danbaoFeeRate =mfCusCreditApply.getDanBaoFeeRate();
            pinshenFeeRate = mfCusCreditApply.getPinShenFeeRate();
        }else{
        }
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        String feeShowFlag = new CodeUtils().getSingleValByKey("APPLY_NODE_FEE_SHOW_FLAG");
        if (BizPubParm.YES_NO_Y.equals(feeShowFlag)) {
            //产品下配置的费用信息
            MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
            mfSysFeeItem.setFeeStdNo(kindNo);
            mfSysFeeItem.setNodeNo(WKF_NODE.apply_input.getNodeNo());
            Ipage ipage = this.getIpage();
            //自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfSysFeeItem", mfSysFeeItem));
            JsonTableUtil jtu = new JsonTableUtil();
            List<MfSysFeeItem> mfSysFeeItemList = (List<MfSysFeeItem>) calcInterfaceFeign.findFeeByPage(ipage).getResult();
            List<MfSysFeeItem> mfSysFeeItemListNew = new ArrayList<MfSysFeeItem>();
            String feeHtmlStr = "";
            if (mfSysFeeItemList != null && mfSysFeeItemList.size() > 0) {
                for (int i = 0; i < mfSysFeeItemList.size(); i++) {
                    mfSysFeeItem = JsonStrHandling.handlingStrToBean(mfSysFeeItemList.get(i),
                            MfSysFeeItem.class);
                    MfKindNodeFee mfKindNodeFee = new MfKindNodeFee();
                    mfKindNodeFee.setKindNo(kindNo);
                    mfKindNodeFee.setItemNo(mfSysFeeItem.getItemNo());
                    mfKindNodeFee.setNodeNo(WKF_NODE.apply_input.getNodeNo());
                    mfKindNodeFee = calcInterfaceFeign.getMfKindNodeFee(mfKindNodeFee);
                    if(mfKindNodeFee!=null){
                        mfSysFeeItem.setOptPower(mfKindNodeFee.getOptPower());
                    }
                    if("1".equals(mfSysFeeItem.getItemNo())&&danbaoFeeRate!=null&&danbaoFeeRate>0){
                        mfSysFeeItem.setRateScale(danbaoFeeRate);
                    }
                    if("8".equals(mfSysFeeItem.getItemNo())&&pinshenFeeRate!=null&&pinshenFeeRate>0){
                        mfSysFeeItem.setRateScale(pinshenFeeRate);
                    }
                    mfSysFeeItemListNew.add(mfSysFeeItem);
                }
            }
            if (mfSysFeeItemListNew != null && mfSysFeeItemListNew.size() > 0) {
                feeHtmlStr = jtu.getJsonStr("tableapplynodefeelist", "tableTag", mfSysFeeItemListNew, null, true);
            }
            dataMap.put("feeHtmlStr", feeHtmlStr);
            dataMap.put("flag","success");
        }
        dataMap.put("feeShowFlag", feeShowFlag);
        return dataMap;
    }
    @RequestMapping(value = "/insertForApplyAjax")
    @ResponseBody
    public Map<String, Object> insertForApplyAjax(String ajaxData, String ajaxDataList, String parcel,String wkfAppId,String planDataList) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            MfCusCreditApply mfCusCreditApplyQuery = new MfCusCreditApply();
            mfCusCreditApplyQuery.setWkfAppId(wkfAppId);
            mfCusCreditApplyQuery = mfCusCreditApplyFeign.getById(mfCusCreditApplyQuery);
            String creditAppId = null;
            String pactNo="";
            if (mfCusCreditApplyQuery != null) {
                creditAppId = mfCusCreditApplyQuery.getCreditAppId();
                pactNo = mfCusCreditApplyQuery.getCreditAppNo();
            }else{
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setWkfAppId(wkfAppId);
                mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                pactNo = mfCusCreditAdjustApply.getCreditAppNo();
                creditAppId  = mfCusCreditAdjustApply.getAdjustAppId();
                //处理授信担保环节，从押品页面跳授信信息没有审批流程问题
                map.put("adjustAppId", mfCusCreditAdjustApply.getAdjustAppId());
            }
            //业务进入下一个流程
            TaskImpl task = wkfInterfaceFeign.getTaskWithUser(wkfAppId, null, User.getRegNo(request));
            String url = "";
            String transition = workflowDwrFeign.findNextTransition(task.getId());
            map.put("taskId",task.getId());
            map.put("opinionType",AppConstant.OPINION_TYPE_ARREE);
            map.put("url",url);
            map.put("transition",transition);
            map.put("opNo", User.getRegNo(request));
            map.put("nextUser", "");
            map.put("wkfAppId", wkfAppId);
            String formId = map.get("formId").toString();
            FormData formapply0007_query = formService.getFormData(formId);
            if ("0".equals(map.get("cmpFltRateShow"))) {// 不收复利
                map.put("cmpdFloat", "-100");
                map.put("cmpdRate", "0.00");
            }
            getFormValue(formapply0007_query, map);
            if (this.validateFormDataAnchor(formapply0007_query)) {
                MfBusApply mfBusApply = new MfBusApply();
                if (mfCusCreditApplyQuery != null) {
                    PropertyUtils.copyProperties(mfBusApply,mfCusCreditApplyQuery);
                    mfBusApply.setManageOpNo1(mfCusCreditApplyQuery.getManageOpNo());
                }else{
                    MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                    mfCusCreditAdjustApply.setWkfAppId(wkfAppId);
                    mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                    PropertyUtils.copyProperties(mfBusApply,mfCusCreditAdjustApply);
                }
                setObjValue(formapply0007_query, mfBusApply);
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfBusApply.getCusNo());

                String SHARE_CUS_STATS_CTRL = new CodeUtils().getSingleValByKey("SHARE_CUS_STATS_CTRL");// 申请业务后是否改为非共享状态
                if ("1".equals(SHARE_CUS_STATS_CTRL)) {
                    mfCusCustomer.setShareCusStats("1");
                    mfCusCustomerFeign.update(mfCusCustomer);
                }

                mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
                String channelSourceNo = mfCusCustomer.getChannelSourceNo();
                if (channelSourceNo != null && !"".equals(channelSourceNo)) {
                    if (mfBusApply.getChannelSourceNo() == null || "".equals(mfBusApply.getChannelSourceNo())) {
                        mfBusApply.setChannelSourceNo(channelSourceNo);
                        mfBusApply.setChannelSource(mfCusCustomer.getChannelSource());
                    }
                }

                //校验申请金额，申请期限是否大于0
                if(MathExtend.comparison(mfBusApply.getAppAmt().toString(),"0.00")!=1){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "申请金额必须大于0！");
                    return dataMap;
                }
                if(MathExtend.comparison(mfBusApply.getTerm().toString(),"0")!=1){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "申请期限必须大于0！");
                    return dataMap;
                }

                String vouType = mfBusApply.getVouType();
                if (StringUtil.isNotEmpty(vouType) && "|".equals(vouType.substring(0, 1))) {
                    mfBusApply.setVouType(vouType.substring(1, vouType.length()));
                }
                //费用信息
                map.put("feeList", ajaxDataList);
                new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusApply);
                mfBusApply.setDataShowWay("1");//进件数据PC主端是否展示，内部人员申请默认展示
                mfBusApply.setCreditAppId(creditAppId);
                mfBusApply.setIsCreditFlag("1");
                map.put("mfBusApply", mfBusApply);
                map.put("planDataList", planDataList);
                mfBusApply = mfBusApplyFeign.insertApplyForCredit(map);
                dataMap.put("appId", mfBusApply.getAppId());
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
        }
        return dataMap;
    }
    /**
     * 打开其他类型尽调报告页面
     *
     * @author LJW
     * date 2017-2-28
     */
    @RequestMapping(value = "/investigation")
    public String investigation(Model model, String creditAppId) throws Exception {
        FormService formService = new FormService();
        String formId = null;
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String wkfAppId = request.getParameter("wkfAppId");
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("wkfAppId", wkfAppId);
        //获得尽调报告数据
        dataMap = mfCusCreditApplyFeign.getCreditReportDataMap(dataMap);
        JSONObject json = new JSONObject();
        json.put("mfCusPorductCreditList", JSONArray.fromObject(dataMap.get("mfCusPorductCreditList")));
        json.put("mfSysKinds", JSONArray.fromObject(dataMap.get("mfSysKinds")));
        String creditModel = String.valueOf(dataMap.get("creditModel"));//授信模式1客户授信2项目授信
        String creditType = String.valueOf(dataMap.get("creditType"));
        String nodeNo = "";
        TaskImpl task = wkfInterfaceFeign.getTaskWithUser(wkfAppId, null, User.getRegNo(request));
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(creditAppId);
        mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(dataMap.get("creditType"))) {
            MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("creditAdjust" + creditModel, nodeNo);
            if (mfKindForm != null) {
                formId = mfKindForm.getAddModel();
            }
        } else if (BizPubParm.CREDIT_TYPE_ADD.equals(dataMap.get("creditType"))) {
            MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm(mfCusCreditApply.getTemplateCreditId(), nodeNo);
            if (mfKindForm!=null){
                formId = mfKindForm.getAddModel();
            }
        } else if (BizPubParm.CREDIT_TYPE_RENEW.equals(dataMap.get("creditType"))) {
            MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("creditRenew" + creditModel, nodeNo);
            if (mfKindForm != null) {
                formId = mfKindForm.getAddModel();
            }
        }else {
        }

        FormData formcreditapply0001 = formService.getFormData(formId);
        getFormValue(formcreditapply0001);
        String adjustAppId = null;
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType) || BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            if (dataMap.get("mfCusCreditContract") != null) {
                MfCusCreditContract mfCusCreditContract = JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditContract"), MfCusCreditContract.class);
                getObjValue(formcreditapply0001, mfCusCreditContract);
            } else {
                getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
            }
            MfCusCreditAdjustApply mfCusCreditAdjustApply = (MfCusCreditAdjustApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditAdjustApply"), MfCusCreditAdjustApply.class);
            adjustAppId = mfCusCreditAdjustApply.getAdjustAppId();
            getObjValue(formcreditapply0001, mfCusCreditAdjustApply);

            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);

            this.changeFormProperty(formcreditapply0001, "creditSum", "initValue", mfCusCreditAdjustApply.getAdjCreditSum().toString());
            this.changeFormProperty(formcreditapply0001, "creditTerm", "initValue", mfCusCreditAdjustApply.getAdjCreditTerm().toString());
            this.changeFormProperty(formcreditapply0001, "isCeilingLoop", "initValue", mfCusCreditAdjustApply.getAdjIsCeilingLoop());
            this.changeFormProperty(formcreditapply0001, "beginDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjBeginDate()));
            this.changeFormProperty(formcreditapply0001, "endDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjEndDate()));

            creditAppId = adjustAppId;
        } else {
            getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
        }

        //要件的展示方式：0块状1列表
        model.addAttribute("docShowType", "1");
        request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
        request.setAttribute("cusNo", String.valueOf(dataMap.get("cusNo")));
        request.setAttribute("modelNo", String.valueOf(dataMap.get("modelNo")));
        request.setAttribute("cusNo", String.valueOf(dataMap.get("cusNo")));
        request.setAttribute("json", json);
        request.setAttribute("kindMap", dataMap.get("kindMap"));
        request.setAttribute("appId", dataMap.get("appId"));
        request.setAttribute("creditType", creditType);
        request.setAttribute("wkfAppId", dataMap.get("wkfAppId"));
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("baseType", String.valueOf(dataMap.get("cusType")).substring(0, 1));
        model.addAttribute("creditFlag", "1");
        model.addAttribute("query", "");
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("creditAppId", creditAppId);
        model.addAttribute("creditModel", creditModel);
        return "/component/auth/MfCusCreditApply_DetailForInvest";
    }

    /**
     * 方法描述： 获得已授信的产品额度
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-6-24 上午10:27:21
     */
    @RequestMapping(value = "/getPorductCreditAjax")
    @ResponseBody
    public Map <String, Object> getPorductCreditAjax(String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String adjustAppId = request.getParameter("adjustAppId");
        //查询授信产品信息
        dataMap = mfCusPorductCreditFeign.getPorductCreditApplyAndAdj(creditAppId, adjustAppId);
        CodeUtils cu = new CodeUtils();
        Map <String, ParmDic> kindNoMap = cu.getMapObjByKeyName("KIND_NO");
        dataMap.put("kindMap", kindNoMap);
        dataMap.put("flag", "success");
        return dataMap;
    }

    /**
     * 授信合同签约
     *
     * @author LJW
     * date 2017-3-7
     */
    @RequestMapping(value = "/protocolForManage")
    public String protocolForManage(Model model, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String wkfAppId = request.getParameter("wkfAppId");
        if (null == wkfAppId || "".equals(wkfAppId)) {
            wkfAppId = request.getParameter("appNo");
        }
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        String creditAppId = "";
        String cusNo = "";
        String kindNo = "";
        String nodeNo = "";
        try {
            mfCusCreditApply.setWkfAppId(wkfAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getByWkfId(mfCusCreditApply);
            if (mfCusCreditApply == null) {
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setWkfAppId(wkfAppId);
                mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                if (mfCusCreditAdjustApply != null) {
                    mfCusCreditApply = new MfCusCreditApply();
                    mfCusCreditApply.setCreditAppId(mfCusCreditAdjustApply.getCreditAppId());
                    mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
                    kindNo = mfCusCreditAdjustApply.getCreditId();
                    nodeNo = mfCusCreditAdjustApply.getNodeNo();
                    creditAppId = mfCusCreditAdjustApply.getAdjustAppId();
                    cusNo = mfCusCreditAdjustApply.getCusNo();
                }
            }else{
                kindNo = mfCusCreditApply.getTemplateCreditId();
                nodeNo = mfCusCreditApply.getNodeNo();
                creditAppId = mfCusCreditApply.getCreditAppId();
                cusNo = mfCusCreditApply.getCusNo();
            }
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
            String formId = prdctInterfaceFeign.getFormId(kindNo,WKF_NODE.protocolPrint , null, null, User.getRegNo(request));;
            if(WKF_NODE.protocol_manage.getNodeNo().equals(nodeNo)){
                formId = prdctInterfaceFeign.getFormId(kindNo,WKF_NODE.protocol_manage , null, null, User.getRegNo(request));
            }
            FormData 	formcreditpact0001 = formService.getFormData(formId);//"creditpact0001"
            MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
            getFormValue(formcreditpact0001);
            getObjValue(formcreditpact0001, mfCusCreditApply);
            //授信合同开始日为最后有权审批人审批通过的时间，mf_cus_credit_apply_his，最后一条reg_date
            MfCusCreditApplyHis mfCusCreditApplyHis = new MfCusCreditApplyHis();
            mfCusCreditApplyHis.setCreditAppId(creditAppId);
            mfCusCreditApplyHis = mfCusCreditApplyHisFeign.getLastByCreditAppId(mfCusCreditApplyHis);
            if(mfCusCreditApplyHis!=null){
                String beginDate = mfCusCreditApplyHis.getRegTime();
                mfCusCreditContract.setBeginDate(beginDate);
            }
            getObjValue(formcreditpact0001, mfCusCreditContract);
            //合同明细列表
            mfCusCreditContractDetail = new MfCusCreditContractDetail();
            mfCusCreditContractDetail.setCreditAppId(creditAppId);
            List<MfCusCreditContractDetail> mfCusCreditContractDetailList = mfCusCreditContractDetailFeign.getMfCusCreditContractDetailList(mfCusCreditContractDetail);
            if (mfCusCreditContract!=null) {
                request.setAttribute("pactId", mfCusCreditContract.getId());
            }

            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
            MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
            mfCusPorductCredit.setCreditAppId(creditAppId);
            List <MfCusPorductCredit> mfCusPorductCredits = mfCusPorductCreditFeign.getByCreditAppIdDesc(mfCusPorductCredit);

            JSONObject json = new JSONObject();
            JSONArray array = JSONArray.fromObject(mfCusPorductCredits);
            json.put("mfCusPorductCreditList", array);
            //获取所有产品
            List <MfSysKind> mfSysKinds = new ArrayList <MfSysKind>();
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind.setUseFlag("1");
            mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
            array = JSONArray.fromObject(mfSysKinds);
            json.put("mfSysKinds", array);
            request.setAttribute("creditType", mfCusCreditContract.getCreditType());
            request.setAttribute("json", json);
            request.setAttribute("cusType", mfCusCustomer.getCusType());
            model.addAttribute("mfCusCreditContractDetailList", mfCusCreditContractDetailList);
            //在表单里面添加两个字段
            //客户的授信产品不为空
            if (mfCusPorductCredits != null && mfCusPorductCredits.size() > 0) {
                //给表单内容赋值的时候只能是string
                for (MfCusPorductCredit mfCusPorductCredit2 : mfCusPorductCredits) {
                    Double creditAmt = mfCusPorductCredit2.getCreditAmt();
                    String creditAmtNew = creditAmt.toString();
                    this.changeFormProperty(formcreditpact0001, "kindNameNew", "initValue", mfCusPorductCredit2.getKindName());
                    this.changeFormProperty(formcreditpact0001, "creditAmtNew", "initValue", creditAmtNew);
                }
            }
            model.addAttribute("formcreditpact0001", formcreditpact0001);
            model.addAttribute("nodeNo",nodeNo);
            model.addAttribute("wkfAppId", wkfAppId);
            model.addAttribute("cusNo", cusNo);
            model.addAttribute("creditAppId", creditAppId);
            //为了展示调整前的产品额度
            if (BizPubParm.CREDIT_TYPE_ADJUST.equals(mfCusCreditContract.getCreditType())) {
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
                mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                model.addAttribute("creditAppIdPro", mfCusCreditAdjustApply.getCreditAppId());
                model.addAttribute("adjAppIdPro", creditAppId);
            }
            model.addAttribute("query", "");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfCusCreditContract_DetailForManage";
    }


    /**
     * 授信合同签约
     *
     * @author LJW
     * date 2017-3-7
     */
    @RequestMapping(value = "/protocolPrint")
    public String protocolPrint(Model model, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String wkfAppId = request.getParameter("wkfAppId");
        if (null == wkfAppId || "".equals(wkfAppId)) {
            wkfAppId = request.getParameter("appNo");
        }
        MfCusCreditApply mfCusCreditApply = null;
        MfCusCreditContract mfCusCreditContract = null;
        MfCusCreditAdjustApply mfCusCreditAdjustApply = null;
        String creditAppId = "";
        String cusNo = "";
        String busModel = "";
        try {
            String kindNo = "";
            mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setWkfAppId(wkfAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getByWkfId(mfCusCreditApply);
            if (mfCusCreditApply == null) {
                mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setWkfAppId(wkfAppId);
                mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                if (mfCusCreditAdjustApply != null) {
                    mfCusCreditApply = new MfCusCreditApply();
                    mfCusCreditApply.setCreditAppId(mfCusCreditAdjustApply.getCreditAppId());
                    mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);

                    creditAppId = mfCusCreditAdjustApply.getAdjustAppId();
                    cusNo = mfCusCreditAdjustApply.getCusNo();
                    kindNo = mfCusCreditAdjustApply.getCreditId();
                }
            } else {
                kindNo = mfCusCreditApply.getTemplateCreditId();
                creditAppId = mfCusCreditApply.getCreditAppId();
                cusNo = mfCusCreditApply.getCusNo();
            }
            mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
            if (BizPubParm.CREDIT_AMT_BUSS_USE.equals(mfCusCreditContract.getCreditType())) {
                kindNo = "creditRenew" + mfCusCreditContract.getCreditModel();
            }
            if (mfCusCreditApply.getCreditType().equals(BizPubParm.CREDIT_TYPE_TRANSFER)) {//取转贷业务form表单
                kindNo = "transferpact";
            }
            MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
            mfCusCreditConfig.setCreditId(kindNo);
            mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
            if(mfCusCreditConfig!=null){
                String   adaptationKindNo = mfCusCreditConfig.getAdaptationKindNo();
                busModel = mfCusCreditConfig.getBusModel();
                if(StringUtil.isNotEmpty(adaptationKindNo)){
                    MfSysKind mfSysKind = new MfSysKind();
                    mfSysKind =prdctInterfaceFeign.getSysKindById(adaptationKindNo);
                    if(mfSysKind!=null){
                        mfCusCreditContract.setKindNo(mfSysKind.getKindNo());
                        mfCusCreditContract.setKindName(mfSysKind.getKindName());
                    }
                }
                if(BizPubParm.BUS_MODEL_12.equals(busModel)){
                    MfCusCreditApplyHis mfCusCreditApplyHis = new MfCusCreditApplyHis();
                    mfCusCreditApplyHis.setCreditAppId(creditAppId);
                    mfCusCreditApplyHis = mfCusCreditApplyHisFeign.getLastByCreditAppId(mfCusCreditApplyHis);
                    mfCusCreditContract.setBeginDate(mfCusCreditApplyHis.getRegTime().substring(0,8));
                    mfCusCreditContract.setEndDate(DateUtil.addMonth(mfCusCreditContract.getBeginDate(), mfCusCreditContract.getCreditTerm()));
                    mfCusCreditContract.setBankBeginDate(mfCusCreditContract.getBeginDate());
                    mfCusCreditContract.setBankEndDate(mfCusCreditContract.getEndDate());
                    String CREDIT_END_DATE_REDUCE = new CodeUtils().getSingleValByKey("CREDIT_END_DATE_REDUCE");// 授信结束日自动减一天
                    if (StringUtil.isNotEmpty(CREDIT_END_DATE_REDUCE)) {
                        model.addAttribute("CREDIT_END_DATE_REDUCE", CREDIT_END_DATE_REDUCE);
                    }
                }
            }
            mfCusCreditContract.setLoaner(mfCusCreditContract.getCusName());
            mfCusCreditContract.setPinShenFeeRate(mfCusCreditApply.getPinShenFeeRate());
            mfCusCreditContract.setDanBaoFeeRate(mfCusCreditApply.getDanBaoFeeRate());
            mfCusCreditContract.setBkcxdbOppositeParty(mfCusCreditContract.getAgenciesName());
            //报批的决策文件编号
            String approvaDocNo ="";
            MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
            mfCusAgenciesCredit.setCreditAppId(mfCusCreditContract.getCreditAppId());
            List<MfCusAgenciesCredit> mfCusAgenciesCreditList = mfCusCreditApplyFeign.getByCreditAppId(mfCusAgenciesCredit);
            if(mfCusAgenciesCreditList.size()>0){                mfCusAgenciesCredit = mfCusAgenciesCreditList.get(0);
                approvaDocNo ="QXN字"+mfCusAgenciesCredit.getBankPactNo();
            }
            mfCusCreditContract.setApprovaDocNo(approvaDocNo);
            //获取保证人
            List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(mfCusCreditContract.getCreditAppId());
            String sxdfOppositeParty =mfCusCreditContract.getCusName()+",";
            String fdbbzOppositeParty = "";
            if(mfAssureInfoList!=null&&mfAssureInfoList.size()>0){
                for (int i = 0; i < mfAssureInfoList.size(); i++) {
                    fdbbzOppositeParty =fdbbzOppositeParty+mfAssureInfoList.get(i).getAssureName();
                    sxdfOppositeParty = sxdfOppositeParty+mfAssureInfoList.get(i).getAssureName();
                }
            }
            mfCusCreditContract.setFdbbzOppositeParty(fdbbzOppositeParty);
            mfCusCreditContract.setSxdfOppositeParty(sxdfOppositeParty);
            mfCusCreditContract.setZhuTerm(mfCusCreditContract.getCreditTerm());
            mfCusCreditContract.setGuaranteeAmt(mfCusCreditContract.getCreditSum());

            mfCusCreditContract.setPinShenRate(mfCusCreditApply.getPinShenFeeRate());
            mfCusCreditContract.setDanBaoRate(mfCusCreditApply.getDanBaoFeeRate());
            if(mfCusCreditContract.getPinShenRate()!=null){
                mfCusCreditContract.setPinShenFee(MathExtend.multiply(mfCusCreditContract.getGuaranteeAmt(),mfCusCreditContract.getPinShenRate())/100);
            }
            if(mfCusCreditContract.getDanBaoRate()!=null){
                mfCusCreditContract.setDanBaoFee(MathExtend.multiply(mfCusCreditContract.getGuaranteeAmt(),mfCusCreditContract.getDanBaoRate())/100);
            }
            String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.protocolPrint, null, null, User.getRegNo(request));
            FormData formcreditpact0001 = formService.getFormData(formId);//"creditpact0001"
            MfCusCreditContractDetail mfCusCreditContractDetail = new MfCusCreditContractDetail();
            getFormValue(formcreditpact0001);
            getObjValue(formcreditpact0001, mfCusCreditApply);
            /*if(mfCusCreditContract.getLoanHis()==null){
                MfBusPact mfBusPactQuery = new MfBusPact();
                mfBusPactQuery.setCusNo(mfCusCreditContract.getCusNo());
                mfCusCreditContract.setLoanHis(mfBusPactFeign.getPactSumByCusNo(mfBusPactQuery));
            }*/
            getObjValue(formcreditpact0001, mfCusCreditContract);
            if (mfCusCreditApply.getCreditType().equals(BizPubParm.CREDIT_TYPE_TRANSFER)) {
                MfCusCreditContract mfCusCreditContractQuery = new MfCusCreditContract();
                mfCusCreditContractQuery.setCreditAppId(mfCusCreditApply.getOldCreditAppId());
                mfCusCreditContractQuery = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContractQuery);
                if (mfCusCreditContractQuery != null) {
                    this.changeFormProperty(formcreditpact0001, "oldPactNo", "initValue", mfCusCreditContractQuery.getPactNo());
                    this.changeFormProperty(formcreditpact0001, "oldCreditSum", "initValue", String.valueOf(mfCusCreditContractQuery.getCreditSum()));
                    this.changeFormProperty(formcreditpact0001, "oldCreditTerm", "initValue", mfCusCreditContractQuery.getCreditTerm().toString());
                    this.changeFormProperty(formcreditpact0001, "oldRegTime", "initValue", mfCusCreditContractQuery.getRegTime().substring(0, 4) + "-" + mfCusCreditContractQuery.getRegTime().substring(4, 6) + "-" + mfCusCreditContractQuery.getRegTime().substring(6, 8));
                    this.changeFormProperty(formcreditpact0001, "oldBeginDate", "initValue", mfCusCreditContractQuery.getBeginDate().substring(0, 4) + "-" + mfCusCreditContractQuery.getBeginDate().substring(4, 6) + "-" + mfCusCreditContractQuery.getBeginDate().substring(6, 8));
                    this.changeFormProperty(formcreditpact0001, "oldEndDate", "initValue", mfCusCreditContractQuery.getEndDate().substring(0, 4) + "-" + mfCusCreditContractQuery.getEndDate().substring(4, 6) + "-" + mfCusCreditContractQuery.getEndDate().substring(6, 8));
                }
            }
            //合同明细列表
            mfCusCreditContractDetail = new MfCusCreditContractDetail();
            mfCusCreditContractDetail.setCreditAppId(creditAppId);
            List <MfCusCreditContractDetail> mfCusCreditContractDetailList = mfCusCreditContractDetailFeign.getMfCusCreditContractDetailList(mfCusCreditContractDetail);
            if (mfCusCreditContract != null) {
                request.setAttribute("pactId", mfCusCreditContract.getId());
            }

            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
            MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
            mfCusPorductCredit.setCreditAppId(creditAppId);
            List <MfCusPorductCredit> mfCusPorductCredits = mfCusPorductCreditFeign.getByCreditAppIdDesc(mfCusPorductCredit);

            JSONObject json = new JSONObject();
            JSONArray array = JSONArray.fromObject(mfCusPorductCredits);
            json.put("mfCusPorductCreditList", array);
            //获取所有产品
            List <MfSysKind> mfSysKinds = new ArrayList <MfSysKind>();
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind.setUseFlag("1");
            mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
            for (MfSysKind kind : mfSysKinds) {
                kind.setRemark("");
            }
            array = JSONArray.fromObject(mfSysKinds);
            json.put("mfSysKinds", array);
            request.setAttribute("creditType", mfCusCreditContract.getCreditType());
            request.setAttribute("json", json);
            request.setAttribute("cusType", mfCusCustomer.getCusType());
            model.addAttribute("mfCusCreditContractDetailList", mfCusCreditContractDetailList);
            //在表单里面添加两个字段
            //客户的授信产品不为空
            if (mfCusPorductCredits != null && mfCusPorductCredits.size() > 0) {
                //给表单内容赋值的时候只能是string
                for (MfCusPorductCredit mfCusPorductCredit2 : mfCusPorductCredits) {
                    Double creditAmt = mfCusPorductCredit2.getCreditAmt();
                    String creditAmtNew = creditAmt.toString();
                    this.changeFormProperty(formcreditpact0001, "kindNameNew", "initValue", mfCusPorductCredit2.getKindName());
                    this.changeFormProperty(formcreditpact0001, "creditAmtNew", "initValue", creditAmtNew);
                }
            }
            model.addAttribute("formcreditpact0001", formcreditpact0001);
            model.addAttribute("nodeNo", WKF_NODE.protocolPrint.getNodeNo());
            model.addAttribute("wkfAppId", wkfAppId);
            model.addAttribute("cusNo", cusNo);
            model.addAttribute("creditAppId", creditAppId);
            model.addAttribute("processId", mfCusCreditApply.getCreditPactProcessId());
            //为了展示调整前的产品额度
            if (BizPubParm.CREDIT_TYPE_ADJUST.equals(mfCusCreditContract.getCreditType())) {
                model.addAttribute("creditAppIdPro", mfCusCreditAdjustApply.getCreditAppId());
                model.addAttribute("adjAppIdPro", creditAppId);
            }
            model.addAttribute("query", "");
            MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
            mfBusPactExtend.setCreditAppId(creditAppId);
            List<MfBusPactExtend> mfBusPactExtendList = mfBusPactExtendFeign.getAllListForCredit(mfBusPactExtend) ;
            model.addAttribute("mfBusPactExtendList", mfBusPactExtendList);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        // ---------- begin 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        Map <String, ParmDic> SAVE_ONLY = new CodeUtils().getMapObjByKeyName("SAVE_ONLY");// 启用: 显示保存及提交两个按钮(支持保存未提交) 停用: 只显示保存按钮
        String SAVE_ONLY_2 = "0";
        if (SAVE_ONLY.containsKey("2")) {// 启用
            SAVE_ONLY_2 = "1";// 支持保存未提交
        }
        model.addAttribute("SAVE_ONLY_2", SAVE_ONLY_2);
        model.addAttribute("busModel", busModel);
        // ---------- end 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------

        return "/component/auth/MfCusCreditContract_Detail";
    }

    /**
     * 方法描述： 获得是否保存过文档模板
     *
     * @author LJW
     */
    @RequestMapping(value = "/getIfSaveModleInfo")
    @ResponseBody
    public Map <String, Object> getIfSaveModleInfo(Model model, String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            String relId = request.getParameter("relId");
            String tempType = request.getParameter("tempType");
            creditAppId = request.getParameter("creditAppId");
            dataMap.put("relId", relId);
            dataMap.put("creditAppId", creditAppId);
            dataMap.put("tempType", tempType);
            dataMap.put("opNo", User.getRegNo(request));
            dataMap = mfCusCreditApplyFeign.getIfSaveCreditTemplateInfo(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
        }

        return dataMap;
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public String delete(Model model, String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(creditAppId);
        mfCusCreditApplyFeign.delete(mfCusCreditApply);
        return getListPage(model);
    }

    /**
     * 检查客户企业是否授信过
     *
     * @author LJW
     * date 2017-2-28
     */
    @RequestMapping(value = "/checkIsCredit")
    @ResponseBody
    public Map <String, Object> checkIsCredit(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
        try {
            String cusType = request.getParameter("cusType");
            //通过客户类型查询匹配的授信模型
            mfCusCreditModel.setCusTypeNo(cusType);
            mfCusCreditModel = mfCusCreditModelFeign.getByCusTypeNo(mfCusCreditModel);
            if (mfCusCreditModel == null) {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.NO_CREDIT_MODEL.getMessage());
                return dataMap;
            }
            mfCusCreditApply.setCusNo(cusNo);
            List <MfCusCreditApply> mfCusCreditApplies = mfCusCreditApplyFeign.getByCusNos(mfCusCreditApply);
            dataMap.put("flag", "0"); //未授信过
            boolean isFlag = false;
            if (mfCusCreditApplies != null && mfCusCreditApplies.size() > 0) {
                for (MfCusCreditApply mfCusCreditApply1 : mfCusCreditApplies) {
                    if (mfCusCreditApply1.getCreditSum() != null) {
                        isFlag = true;
                    }
                }
                if (isFlag) {
                    dataMap.put("flag", "1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("客户授信申请"));
        }
        return dataMap;
    }

    /**
     * 发起授信申请时开启业务流程
     *
     * @author LJW
     * date 2017-2-28
     */
    @RequestMapping(value = "/startCreditProcess")
    @ResponseBody
    public Map <String, Object> startCreditProcess(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            String cusNo = request.getParameter("cusNo");
            String cusType = request.getParameter("cusType");
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
            mfCusCreditApply.setCusNo(cusNo);
            mfCusCreditApply.setCusType(cusType);
            mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
            //发起授信时判断是否新增
            mfCusCreditApply.setIsNew("add");
            List <MfCusCreditApply> mfCusCreditApplies = mfCusCreditApplyFeign.getByCusNos(mfCusCreditApply);
            boolean isFlag = false;
            if (mfCusCreditApplies != null && mfCusCreditApplies.size() > 0) {
                for (MfCusCreditApply mfCusCreditApply1 : mfCusCreditApplies) {
                    if (mfCusCreditApply1.getCreditSum() != null) {
                        isFlag = true;
                    }
                }
                if (isFlag) {
                    mfCusCreditApply.setIsNew("update");
                }
            }
            mfCusCreditApply = mfCusCreditApplyFeign.insert(mfCusCreditApply);
            dataMap.put("flag", "success");
            dataMap.put("wkfAppId", mfCusCreditApply.getWkfAppId());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 检查授信申请业务流程是否结束
     *
     * @author LJW
     * date 2017-3-20
     */
    @RequestMapping(value = "/checkWkfEndSts")
    @ResponseBody
    public Map <String, Object> checkWkfEndSts(Model model, String wkfAppId, String cusNo, String creditModel) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String projectName = ymlConfig.getSysParams().get("sys.project.name");
        try {
            if ("HNDHZS".equals(projectName)) {
                Gson gson = new Gson();
                Map <String, Object> paramMap = new HashMap <String, Object>();
                paramMap.put("cusNo", cusNo);
                paramMap.put("wkfAppId", wkfAppId);
                paramMap.put("creditModel", creditModel);
                String faUrl = ymlConfig.getSysParams().get("sys.project.fa.url");
                String paramMapJson = HttpClientUtil.sendPostJson(gson.toJson(paramMap), faUrl + "/mfCusCreditApply/checkWkfEndStsHND");
                dataMap = gson.fromJson(paramMapJson, Map.class);
            } else {
                dataMap = mfCusCreditApplyFeign.checkWkfEndSts(cusNo, wkfAppId, creditModel);
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 根据流程业务id获取本次流程的状态信息
     *
     * @author LJW
     * date 2017-3-23
     */
    @RequestMapping(value = "/getCreditStsInfo")
    @ResponseBody
    public Map <String, Object> getCreditStsInfo(Model model, String wkfAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            mfCusCreditApply.setWkfAppId(wkfAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getByWkfId(mfCusCreditApply);
            mfCusCreditApply.setApplySum(MathExtend.moneyStr(mfCusCreditApply.getCreditSum() / 10000));   //金额格式化
            dataMap.put("status", mfCusCreditApply.getCreditSts());
            dataMap.put("creditSum", mfCusCreditApply.getCreditSum());
            dataMap.put("applySum", mfCusCreditApply.getApplySum());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 方法描述： 获得授信申请的类型新增授信申请，授信调整申请
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-6-26 下午9:49:54
     */
    @RequestMapping(value = "/getAppCreditType")
    @ResponseBody
    public Map <String, Object> getAppCreditType(Model model, String wkfAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            mfCusCreditApply.setWkfAppId(wkfAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getByWkfId(mfCusCreditApply);

            mfCusCreditApply.setApplySum(MathExtend.moneyStr(mfCusCreditApply.getCreditSum() / 10000));   //金额格式化
            dataMap.put("status", mfCusCreditApply.getCreditSts());
            dataMap.put("creditSum", mfCusCreditApply.getCreditSum());
            dataMap.put("applySum", mfCusCreditApply.getApplySum());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }


    /**
     * 查看授信申请信息
     *
     * @author date 2017-3-21
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/openHisData")
    public String openHisData(Model model, String wkfAppId, String cusNo, String creditModel) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        Gson gson = new Gson();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        FormData formcreditapply0001_new = formService.getFormData("creditapply0001_new");
        FormData formcreditpact0001_new = formService.getFormData("creditpact0001_new");
        FormData formcredittemplateinfo = formService.getFormData("credittemplateinfo");
        getFormValue(formcreditpact0001_new);
        getFormValue(formcreditapply0001_new);
        String creditAppId = null;
        String adjCreditAppId = null;
        String creditApproveId = null;
        String query = null;

        try {
            dataMap.put("pactId", request.getParameter("pactId"));
            dataMap.put("wkfAppId", wkfAppId);
            dataMap.put("cusNo", cusNo);
            dataMap.put("creditModel", creditModel);
            String projectName = ymlConfig.getSysParams().get("sys.project.name");
            String hzsUrl = ymlConfig.getSysParams().get("sys.project.hzs.url");
            //惠农贷项目
            if ("HNDHZS".equals(projectName)) {
                String dataMapJson = HttpClientUtil.sendPostJson(gson.toJson(dataMap), hzsUrl + "/mfCusCreditApply/getCreditDetailDataMap");
                dataMap = gson.fromJson(dataMapJson, Map.class);
            } else {
                dataMap = mfCusCreditApplyFeign.getCreditDetailDataMap(dataMap);
            }

            JSONObject json = new JSONObject();
            json = JSONObject.fromObject(dataMap.get("json"));
            String creditType = String.valueOf(dataMap.get("creditType"));
            String cusName = "";
            if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
                //mfCusCreditApply=(MfCusCreditApply) dataMap.get("mfCusCreditApply");
                mfCusCreditApply = (MfCusCreditApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditApply"), MfCusCreditApply.class);
                request.setAttribute("creditSts", mfCusCreditApply.getCreditSts());
                request.setAttribute("beginDate", mfCusCreditApply.getBeginDate());
                request.setAttribute("endDate", mfCusCreditApply.getEndDate());
            }
            if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
                //MfCusCreditAdjustApply mfCusCreditAdjustApply =(MfCusCreditAdjustApply) dataMap.get("mfCusCreditAdjustApply");
                MfCusCreditAdjustApply mfCusCreditAdjustApply = (MfCusCreditAdjustApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditAdjustApply"), MfCusCreditAdjustApply.class);
                request.setAttribute("creditSts", mfCusCreditAdjustApply.getCreditSts());
                request.setAttribute("beginDate", mfCusCreditAdjustApply.getAdjBeginDate());
                request.setAttribute("endDate", mfCusCreditAdjustApply.getAdjEndDate());
            }
            MfCusCreditUseHis mfCusCreditUseHis = (MfCusCreditUseHis) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditUseHis"), MfCusCreditUseHis.class);
            mfCusCreditUseHis.setCreditSum(DataUtil.div(mfCusCreditUseHis.getCreditSum(), 10000D, 2));
            mfCusCreditUseHis.setCreditOccupySum(DataUtil.div(mfCusCreditUseHis.getCreditOccupySum(), 10000D, 2));
            mfCusCreditUseHis.setCreditUsableSum(DataUtil.div(mfCusCreditUseHis.getCreditUsableSum(), 10000D, 2));

            cusNo = String.valueOf(dataMap.get("cusNo"));
            creditAppId = String.valueOf(dataMap.get("creditAppId"));
            adjCreditAppId = String.valueOf(dataMap.get("adjCreditAppId"));
            creditApproveId = String.valueOf(dataMap.get("creditApproveId"));
            query = "query";
            JSONArray showParmsJson = JSONArray.fromObject(dataMap.get("showParmsJson"));
            JSONObject creditHisJsonObject = JSONObject.fromObject(dataMap.get("creditHisJsonObject"));
            JSONObject dateValuesJsonObject = JSONObject.fromObject(dataMap.get("dateValuesJsonObject"));
            List <String> dateValues = (List <String>) dataMap.get("dateValues");
			/*showParmsJson=new JSONArray();
			showParmsJson.add(creditHisJsonObject);*/
            MfCusCreditContract mfCusCreditContract = (MfCusCreditContract) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditContract"), MfCusCreditContract.class);
            getObjValue(formcreditpact0001_new, mfCusCreditContract);
            List <MfCusCreditUseHis> mfCusCreditUseHisList = (List <MfCusCreditUseHis>) dataMap.get("mfCusCreditUseHisList");
            List <MfCusCreditChildContract> mfCusCreditChildContractList = (List <MfCusCreditChildContract>) dataMap.get("mfCusCreditChildContractList");
            List <MfCusCreditContractDetail> mfCusCreditContractDetailList = (List <MfCusCreditContractDetail>) dataMap.get("mfCusCreditContractDetailList");

            // 获取授信状态名称名称
            String creditStsName = getCreditAppStsName(creditAppId);

            request.setAttribute("mfCusCreditUseHis", mfCusCreditUseHis);
            request.setAttribute("creditApproveId", creditApproveId);
            request.setAttribute("mfCusCreditUseHisList", mfCusCreditUseHisList);
            request.setAttribute("mfCusCreditChildContractList", mfCusCreditChildContractList);
            request.setAttribute("mfCusCreditContractDetailList", mfCusCreditContractDetailList);
            request.setAttribute("cusNo", cusNo);
            request.setAttribute("cusName", cusName);
            request.setAttribute("json", json);
            request.setAttribute("creditHisJsonObject", creditHisJsonObject);
            request.setAttribute("dateValuesJsonObject", dateValuesJsonObject);
            request.setAttribute("dateValues", dateValuesJsonObject);
            request.setAttribute("dateValues", String.valueOf(dateValues));
            request.setAttribute("showParmsJson", showParmsJson);
            request.setAttribute("creditType", creditType);
            request.setAttribute("creditModel", creditModel);
            request.setAttribute("creditStsName", creditStsName);

            model.addAttribute("formcreditpact0001_new", formcreditpact0001_new);
            model.addAttribute("formcredittemplateinfo", formcredittemplateinfo);
            model.addAttribute("creditAppId", creditAppId);
            model.addAttribute("adjCreditAppId", adjCreditAppId);
            model.addAttribute("query", query);
            model.addAttribute("projectName", projectName);

            MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
            List <MfCusPorductCredit> mfCusCreditProductDetailList = new ArrayList <MfCusPorductCredit>();
            if (StringUtil.isNotEmpty(adjCreditAppId)) {
                mfCusPorductCredit.setCreditAppId(adjCreditAppId);
            } else {
                mfCusPorductCredit.setCreditAppId(creditAppId);
            }
            if ("HNDHZS".equals(projectName)) {
                String mfCusPorductCreditJson = HttpClientUtil.sendPostJson(gson.toJson(mfCusPorductCredit), hzsUrl + "/mfCusPorductCredit/getMfCusPorductCreditList");
                mfCusCreditProductDetailList = gson.fromJson(mfCusPorductCreditJson, new TypeToken <List <MfCusPorductCredit>>() {
                }.getType());
            } else {
                mfCusCreditProductDetailList = mfCusPorductCreditFeign.getMfCusPorductCreditList(mfCusPorductCredit);
            }
            model.addAttribute("mfCusCreditProductDetailList", mfCusCreditProductDetailList);
            //惠农贷项目
            if ("HNDHZS".equals(projectName)) {

            } else {
                List <MfCusPorductCredit> mfCusPorductCreditList = mfCusPorductCreditFeign.getByCreditAppId(mfCusPorductCredit);
                model.addAttribute("mfCusPorductCreditList", mfCusPorductCreditList);
                MfAssureInfo assureInfoCusNo = new MfAssureInfo();
                assureInfoCusNo.setAssureNo(cusNo);
                List <MfAssureInfo> assureInfoCusNos = assureInfoFeign.findAssureInfoById(assureInfoCusNo);
                List <MfAssureInfo> assureInfoCusNo2s = FengZhuangAssureInfo(assureInfoCusNos);
                model.addAttribute("assureInfoCusNo2s", assureInfoCusNo2s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfCusCreditOpenHisData";
    }

    /**
     * <p>Title: FengZhuangAssureInfo</p>
     * <p>Description:封装保证人所需要的参数 </p>
     *
     * @param assureInfos
     * @return
     * @throws Exception
     * @author zkq
     * @date 2018年8月30日 上午10:28:06
     */
    public List <MfAssureInfo> FengZhuangAssureInfo(List <MfAssureInfo> assureInfos) throws Exception {

        if (assureInfos != null && assureInfos.size() > 0) {
            //运用迭代器可以移除集合的内容
            ListIterator <MfAssureInfo> listIterator = assureInfos.listIterator();
            while (listIterator.hasNext()) {
                MfAssureInfo mfAssureInfo2 = listIterator.next();
                // 查询出保证信息押品关联id
                MfBusCollateralDetailRel mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
                mfBusCollateralDetailRel.setCollateralId(mfAssureInfo2.getId());
                MfBusCollateralDetailRel mfBusCollateralDetailRel2 = mfBusCollateralDetailRelFeign
                        .getById(mfBusCollateralDetailRel);
                // 查询出与授信业务相关联的appId通过业务押品关联表
                // 1.判断押品是否被删除
                if (mfBusCollateralDetailRel2 != null) {
                    MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
                    mfBusCollateralRel.setBusCollateralId(mfBusCollateralDetailRel2.getBusCollateralId());
                    MfBusCollateralRel mfBusCollateralRel2 = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
                    // 2.判断押品录入的时候是否关联业务
                    if (mfBusCollateralRel2 != null) {
                        // 通过授信申请的appId判断押品的保证信息是否是从授信入口进行录入的
                        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
                        mfCusCreditApply.setCreditAppId(mfBusCollateralRel2.getAppId());
                        MfCusCreditApply cusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
                        if (cusCreditApply != null) {

                            // 通过授信申请的APPID查询授信合同号
                            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                            mfCusCreditContract.setCreditAppId(mfBusCollateralRel2.getAppId());
                            MfCusCreditContract cusCreditContract = mfCusCreditContractFeign
                                    .getById(mfCusCreditContract);
                            Double loanBal = 0.0;
                            if (cusCreditContract != null) {
                                // 授信起止时间
                                mfAssureInfo2.setCreditStartDate(cusCreditContract.getBeginDate());
                                mfAssureInfo2.setCreditEndDate(cusCreditContract.getEndDate());
                                mfAssureInfo2.setAssureAmt(cusCreditContract.getCreditSum());
                                //授信编号
                                String creditPactNo = cusCreditContract.getPactNo();
                                mfAssureInfo2.setContractId(creditPactNo);

                                String creditPactId = cusCreditContract.getId();
                                // 授信号合同id是否为空
                                if (!"".equals(creditPactId) && StringUtil.isNotEmpty(creditPactId)) {
                                    MfBusApply mfBusApply = new MfBusApply();
                                    mfBusApply.setCreditPactId(creditPactId);
                                    List <MfBusApply> mfBusApplys = mfBusApplyFeign
                                            .getMfBusApplyByCreditPactId(mfBusApply);
                                    // 判断客户是否申请过业务

                                    if (mfBusApplys != null && mfBusApplys.size() > 0) {
                                        for (MfBusApply busApply : mfBusApplys) {
                                            String busPactId = busApply.getPactId();
                                            //判断该客户是否和银行签订过合同
                                            if (!"".equals(busPactId) && StringUtil.isNotEmpty(busPactId)) {
                                                MfFundChannelFinc mfFundChannelFinc = new MfFundChannelFinc();
                                                mfFundChannelFinc.setPactId(busPactId);
                                                MfFundChannelFinc mfFundChannelFincByPactId = mfChannelBusFeign
                                                        .getMfFundChannelFincByPactId(mfFundChannelFinc);
                                                if (mfFundChannelFincByPactId != null) {
                                                    loanBal += mfFundChannelFincByPactId.getLoanBal();
                                                } else {
                                                    MfBusPact busPact = mfBusPactFeign.getByAppId(busApply.getAppId());
                                                    loanBal += busPact.getPactAmt();
                                                }
                                            } else {
                                                loanBal += 0.0;
                                            }
                                        }
                                        mfAssureInfo2.setLoanBal(loanBal);
                                    } else {
                                        mfAssureInfo2.setLoanBal(loanBal);
                                    }
                                } else {
                                    mfAssureInfo2.setLoanBal(loanBal);
                                }

                            } else {
                                mfAssureInfo2.setLoanBal(loanBal);
                                mfAssureInfo2.setAssureAmt(0.0);
                            }

                            // 根据客户号查询出借款客户的证件号码
                            MfCusCustomer mfCusCustomer = new MfCusCustomer();
                            mfCusCustomer.setCusNo(mfAssureInfo2.getCusNo());
                            MfCusCustomer mfCusCustomer2 = mfCusCustomerFeign.getById(mfCusCustomer);
                            if (mfCusCustomer2 != null) {
                                mfAssureInfo2.setCusIdNum(mfCusCustomer2.getIdNum());
                            }

                        } else {
                            //此保证信息不是从授信入口录入的移除(业务)
                            listIterator.remove();
                        }

                    } else {
                        //此保证信息不是从授信入口录入的移除(资产)
                        listIterator.remove();
                    }
                } else {
                    //此保证信息已经被删除
                    listIterator.remove();
                }

            }

        }
        return assureInfos;
    }


    /**
     * 授信申请历史页面-查看详情信息
     *
     * @author LJW
     * date 2017-4-1
     */
    @RequestMapping(value = "/queryCreditHisDetail")
    public String queryCreditHisDetail(Model model, String creditAppId, String creditUseHisId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        //String creditApproveId = request.getParameter("creditApproveId");
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        FormData formcreditapply0001_new = formService.getFormData("creditapply0001_new");
        FormData formcreditpact0001_new = formService.getFormData("creditpact0001_new");
        FormData formcredittemplateinfo = formService.getFormData("credittemplateinfo");
        getFormValue(formcreditpact0001_new);
        getFormValue(formcreditapply0001_new);
        getFormValue(formcredittemplateinfo);
        String adjustAppId = request.getParameter("adjustAppId");
        try {
            //授信申请信息
            MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
            String wkfAppId = "";
            if (mfCusCreditApply != null) {
                wkfAppId = mfCusCreditApply.getWkfAppId();
            }else{
                mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
                mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                if (mfCusCreditAdjustApply != null) {
                    wkfAppId = mfCusCreditAdjustApply.getWkfAppId();
                }
            }



            dataMap.put("wkfAppId", wkfAppId);
            dataMap.put("creditUseHisId", creditUseHisId);
            dataMap = mfCusCreditApplyFeign.getCreditDetailHisDataMap(dataMap);
            String creditApproveId = String.valueOf(dataMap.get("creditApproveId"));
            String cusNo = String.valueOf(dataMap.get("cusNo"));
            creditAppId = String.valueOf(dataMap.get("creditAppId"));
            //授信产品信息
//			List<MfCusPorductCredit> mfCusPorductCredits=(List<MfCusPorductCredit>) dataMap.get("mfCusPorductCredits");
            Gson gson = new Gson();
            List <MfCusPorductCredit> mfCusPorductCredits = gson.fromJson(gson.toJson(dataMap.get("mfCusPorductCredits")), new TypeToken <List <MfCusPorductCredit>>() {
            }.getType());
            //获得用信实体，封装最新的授信历史信息
            MfCusCreditUseHis mfCusCreditUseHis = (MfCusCreditUseHis) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditUseHis"), MfCusCreditUseHis.class);
            //授信协议
            //MfCusCreditContract mfCusCreditContract =(MfCusCreditContract) dataMap.get("mfCusCreditContract");
            MfCusCreditContract mfCusCreditContract = (MfCusCreditContract) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditContract"), MfCusCreditContract.class);
            getObjValue(formcreditpact0001_new, mfCusCreditContract);

            //query = "query";
            getObjValue(formcreditapply0001_new, mfCusCreditApply);
            JSONArray showParmsJson = JSONArray.fromObject(dataMap.get("showParmsJson"));
            List <String> productTitles = new ArrayList <String>();
            List <Double> productValues = new ArrayList <Double>();
            productTitles.add("授信总额");
            productValues.add(mfCusCreditUseHis.getCreditSum());
            if (mfCusPorductCredits.size() > 0 && !mfCusPorductCredits.isEmpty()) {
                for (MfCusPorductCredit mfPorductCredit : mfCusPorductCredits) {
                    productTitles.add(mfPorductCredit.getKindName());
                    productValues.add(mfPorductCredit.getCreditAmt());
                }
            }
            JSONArray productTitleJson = JSONArray.fromObject(productTitles);
            JSONArray productValueJson = JSONArray.fromObject(productValues);

            //展示当前用信额度
            MfCusCreditUseHis mfCusCreditUseHisCurrent = new MfCusCreditUseHis();
            mfCusCreditUseHisCurrent.setCreditAppId(creditAppId);
            mfCusCreditUseHisCurrent.setCreditUseHisId(creditUseHisId);
            mfCusCreditUseHisCurrent = mfCusCreditUseHisFeign.getById(mfCusCreditUseHisCurrent);
            mfCusCreditUseHisCurrent.setApplySum(String.valueOf(MathExtend.divide(mfCusCreditUseHisCurrent.getCreditSum(), 10000)));
            mfCusCreditUseHisCurrent.setBeginDate(mfCusCreditUseHis.getBeginDate());
            mfCusCreditUseHisCurrent.setEndDate(mfCusCreditUseHis.getEndDate());
            if(mfCusCreditApply!=null){
                mfCusCreditUseHisCurrent.setIsCeilingLoop(mfCusCreditApply.getIsCeilingLoop());
            }else{
                mfCusCreditUseHisCurrent.setIsCeilingLoop(mfCusCreditAdjustApply.getIsCeilingLoop());
            }


            request.setAttribute("cusNo", cusNo);
            request.setAttribute("creditAppId", creditAppId);
            request.setAttribute("pactId", dataMap.get("pactId"));
            request.setAttribute("creditApproveId", creditApproveId);
            request.setAttribute("mfCusCreditUseHis", mfCusCreditUseHisCurrent);
            request.setAttribute("showParmsJson", showParmsJson);
            request.setAttribute("productTitleJson", productTitleJson);
            request.setAttribute("productValueJson", productValueJson);
            model.addAttribute("formcreditapply0001_new", formcreditapply0001_new);
            model.addAttribute("formcreditpact0001_new", formcreditpact0001_new);
            model.addAttribute("formcredittemplateinfo", formcredittemplateinfo);
            model.addAttribute("query", "");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfCusCreditOpenHisDetailData";
    }

    /**
     * 方法描述： 获取当前业务节点的参数信息（url等）
     *
     * @return String
     * @date 2016-7-25 下午4:07:20
     */
    @RequestMapping(value = "/getTaskInfoAjax")
    @ResponseBody
    public Map <String, Object> getTaskInfoAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String wkfAppId = request.getParameter("wkfAppId");
        try {
            dataMap.put("ONLY_MNG_EDIT_CREDIT","0");
            String manageOpNo ="";//客户经理

            String creditSts = "";
            String creditAppId = "";
            mfCusCreditApply.setWkfAppId(wkfAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getByWkfId(mfCusCreditApply);
            if (mfCusCreditApply != null) {
                creditSts = mfCusCreditApply.getCreditSts();
                creditAppId = mfCusCreditApply.getCreditAppId();
                dataMap.put("approveNodeNo", mfCusCreditApply.getApproveNodeNo());
                dataMap.put("approveNodeName", mfCusCreditApply.getApproveNodeName());
                dataMap.put("approvePartName", mfCusCreditApply.getApprovePartName());
                String clientType = mfCusCreditApply.getClientType();
                String dataShowWay = mfCusCreditApply.getDataShowWay();
                if (clientType != null && "7".equals(clientType) && dataShowWay != null) {//经销商发起
                    dataMap.put("dataShowWay", dataShowWay);
                } else {
                    dataMap.put("dataShowWay", "");
                }
                manageOpNo = mfCusCreditApply.getManageOpNo();


            } else {
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setWkfAppId(wkfAppId);
                mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                creditSts = mfCusCreditAdjustApply.getCreditSts();
                creditAppId = mfCusCreditAdjustApply.getAdjustAppId();
                dataMap.put("approveNodeNo", mfCusCreditAdjustApply.getApproveNodeNo());
                dataMap.put("approveNodeName", mfCusCreditAdjustApply.getApproveNodeName());
                dataMap.put("approvePartName", mfCusCreditAdjustApply.getApprovePartName());
                manageOpNo = mfCusCreditAdjustApply.getManageOpNo();
            }
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
            if(mfCusCreditContract != null){
                creditSts = mfCusCreditContract.getCreditSts();
            }
            //根据业务流程编号
            TaskImpl task = wkfInterfaceFeign.getTaskWithUser(wkfAppId, null, User.getRegNo(request));
            if (task == null) {//表示业务已经结束
                dataMap.put("wkfFlag", "0");
            } else {//表示业务尚未结束
                dataMap.put("wkfFlag", "1");
                String url = taskFeign.openApproveUrl(task.getId());
                String title = task.getDescription();
                dataMap.put("result", task.getResult());
                dataMap.put("url", url);
                dataMap.put("title", title);
                dataMap.put("nodeName", task.getActivityName());
                String ONLY_MNG_EDIT_CREDIT = new CodeUtils().getSingleValByKey("ONLY_MNG_EDIT_CREDIT");//判断当前节点是否在只允许在客户经理编辑
                if(StringUtil.isNotEmpty(ONLY_MNG_EDIT_CREDIT)&&ONLY_MNG_EDIT_CREDIT.contains(task.getActivityName())){
                    String opNo = User.getRegNo(request);
                    if(!opNo.equals(manageOpNo)){
                        dataMap.put("ONLY_MNG_EDIT_CREDIT","1");
                    }

                }
                dataMap.put("creditSts", creditSts);  //授信审批状态
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("当前业务节点的参数"));
            throw e;
        }
        return dataMap;
    }


    /**
     * 方法描述：单独提交业务流程，并更新 业务状态(用于审批子流程完成之后，提交业务流程失败的情况，手动提交业务流程)
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-10-24 上午10:53:24
     */
    @RequestMapping(value = "/commitBusProcessAjax")
    @ResponseBody
    public Map <String, Object> commitBusProcessAjax(String wkfAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        dataMap.put("flag", "success");
        try {

            Task task = wkfInterfaceFeign.getTask(wkfAppId, null);
            String transition1 = workflowDwrFeign.findNextTransition(task.getId());
            Result result = wkfInterfaceFeign.doCommit(task.getId(), AppConstant.OPINION_TYPE_ARREE, "", transition1, User.getRegNo(request), "");
            if (null != result && result.isSuccess()) {
                if (!result.isEndSts()) {
                    task = wkfInterfaceFeign.getTask(wkfAppId, null);
                    dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL_TONEXT.getMessage(task.getDescription()));
                }
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", result.getMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
            throw e;
        }
        return dataMap;
    }

    /**
     * 将流程提交到下一个节点
     *
     * @author LJW
     * date 2017-3-3
     */
    @RequestMapping(value = "/doCommitWkf")
    @ResponseBody
    public Map <String, Object> doCommitWkf(Model model, String wkfAppId, String ajaxData,String formData) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap<String,Object>();
        if(StringUtil.isNotEmpty(ajaxData)){
            dataMap = getMapByJson(ajaxData);
        }
        String cusNo = null;
        String relNo = null;
        FormService formService = new FormService();
        MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
        MfCusCreditApply mfCusCreditApplyQuery = new MfCusCreditApply();
        mfCusCreditApplyQuery.setWkfAppId(wkfAppId);
        mfCusCreditApplyQuery = mfCusCreditApplyFeign.getById(mfCusCreditApplyQuery);
        String creditModel = "1";
        String creditType = "1";
        String creditId = "credit1";
        if (mfCusCreditApplyQuery != null) {
            creditModel = mfCusCreditApplyQuery.getCreditModel();
            creditType = mfCusCreditApplyQuery.getCreditType();
            creditId = mfCusCreditApplyQuery.getTemplateCreditId();
            cusNo = mfCusCreditApplyQuery.getCusNo();
            relNo = mfCusCreditApplyQuery.getCreditAppId();
            MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
            mfCusCreditConfig.setCreditId(mfCusCreditApplyQuery.getTemplateCreditId());
            mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
            String busModel = mfCusCreditConfig.getBusModel();
            if(!BizPubParm.BUS_MODEL_12.equals(busModel)){
                //校验合作银行和业务品种
                MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
                mfCusAgenciesCredit.setCreditAppId(mfCusCreditApplyQuery.getCreditAppId());
                List<MfCusAgenciesCredit> mfCusAgenciesCreditList= mfCusCreditApplyFeign.getByCreditAppId(mfCusAgenciesCredit);
                MfBusBreedCredit mfBusBreedCredit = new MfBusBreedCredit();
                mfBusBreedCredit.setCreditAppId(mfCusCreditApplyQuery.getCreditAppId());
                List<MfBusBreedCredit> mfBusBreedCreditList = mfCusCreditApplyFeign.getBreedByCreditAppId(mfBusBreedCredit);

                if(mfCusAgenciesCreditList.size()>0){
                    for (int i = 0; i < mfCusAgenciesCreditList.size(); i++) {
                        mfCusAgenciesCredit =mfCusAgenciesCreditList.get(i);
                        String agenciesId =mfCusAgenciesCredit.getAgenciesId();
                        //判断合作银行下是否含有业务品种
                        boolean agenciesHaveBreed = false;
                        if(mfBusBreedCreditList.size()<0){
                            dataMap.put("flag", "error");
                            dataMap.put("msg","该授信下未录入业务品种！");
                            return dataMap;
                        }else{
                            for (int j = 0; j < mfBusBreedCreditList.size(); j++) {
                                mfBusBreedCredit  = mfBusBreedCreditList.get(j);
                                if(agenciesId.equals(mfBusBreedCredit.getBreedAgenciesId())){
                                    agenciesHaveBreed =true;
                                }
                            }
                            if(!agenciesHaveBreed){
                                dataMap.put("flag", "error");
                                dataMap.put("msg", "合作银行:"+mfCusAgenciesCredit.getAgenciesName()+"下未添加业务品种！");
                                return dataMap;
                            }
                        }

                    }
                }else{
                    dataMap.put("flag", "error");
                    dataMap.put("msg","该授信下未录入合作银行！");
                    return dataMap;
                }
            }else{
                MfCusCreditApply mfCusCreditApply1 = new MfCusCreditApply();
                mfCusCreditApply1.setWkfAppId(wkfAppId);
                mfCusCreditApply1.setAuthority((String)dataMap.get("authority"));
                mfCusCreditApplyFeign.update(mfCusCreditApply1);
            }
        }else{
            mfCusCreditAdjustApply.setWkfAppId(wkfAppId);
            mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
            cusNo = mfCusCreditAdjustApply.getCusNo();
            relNo = mfCusCreditAdjustApply.getAdjustAppId();
            creditModel = mfCusCreditAdjustApply.getCreditModel();
            creditType = mfCusCreditAdjustApply.getCreditType();
            creditId = mfCusCreditAdjustApply.getCreditId();
            //处理授信担保环节，从押品页面跳授信信息没有审批流程问题
            dataMap.put("adjustAppId", mfCusCreditAdjustApply.getAdjustAppId());
            //当页面表单formData有值，尽调补充表单使用
            if(StringUtil.isNotEmpty(formData)){
                Map <String, Object> formDataMap = getMapByJson(formData);
                FormData supplementOrder = formService.getFormData("supplementOrder");// "creditapply0001"
                getFormValue(supplementOrder, formDataMap);
                setObjValue(supplementOrder, mfCusCreditAdjustApply);
            }
        }
        try {


            String formId = prdctInterfaceFeign.getFormId(creditId, WKF_NODE.report, null, null, User.getRegNo(request));
            if (BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
                formId = prdctInterfaceFeign.getFormId("creditRenew" + creditModel, WKF_NODE.report, null, null, User.getRegNo(request));
            }
            FormData formcreditapply0001 = formService.getFormData(formId);// "creditapply0001"
            getFormValue(formcreditapply0001, dataMap);
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            setObjValue(formcreditapply0001, mfCusCreditApply);
            boolean uploadFalg = mfBusApplyFeign.getIfUploadCredit(cusNo,relNo,WKF_NODE.report.getNodeNo());
            if("1".equals( mfCusCreditApply.getIfFirstLoan())&&!uploadFalg){
                dataMap.put("flag", "error");
                dataMap.put("uploadFalg", uploadFalg);
                dataMap.put("msg", "必须上传企业的征信报告，征信报告中信息概要部分，需为“该信用主体无信贷交易记录”！");
                return dataMap;
            }
            String commitType = request.getParameter("commitType");  //用户保存表示是流程中那个节点url
            //业务进入下一个流程
            TaskImpl task = wkfInterfaceFeign.getTaskWithUser(wkfAppId, null, User.getRegNo(request));
            String url = "";
            if ("PROTOCOL".equals(commitType)) {  //授信协议
                url = "4#MfCusCreditApplyAction.queryCreditProtocol?wkfAppId=" + wkfAppId;
            }
            if ("REPORT".equals(commitType)) { //尽职报告
                url = "3#MfCusCreditApplyAction.queryCreditReport?wkfAppId=" + wkfAppId;
            }
            // 尽调节点修改业务申请
            if(BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)&&WKF_NODE.report.getNodeNo().equals(task.getActivityName())){
                setObjValue(formcreditapply0001, mfCusCreditAdjustApply);
                mfCusCreditAdjustApplyFeign.update(mfCusCreditAdjustApply);
            }
            String transition = workflowDwrFeign.findNextTransition(task.getId());
            dataMap.put("taskId",task.getId());
            dataMap.put("opinionType",AppConstant.OPINION_TYPE_ARREE);
            dataMap.put("url",url);
            dataMap.put("transition",transition);
            dataMap.put("opNo", User.getRegNo(request));
            dataMap.put("nextUser", "");
            dataMap.put("wkfAppId", wkfAppId);
            dataMap.put("creditType", creditType);
            dataMap.put("nodeNo", task.getActivityName());
            dataMap.put("mfCusCreditApply",mfCusCreditApply);
            Result result = mfCusCreditApplyFeign.doCommitWkf(dataMap);
            if (result != null && result.isSuccess()) {
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", result.getMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 将流程提交到下一个节点
     *
     * @author LJW
     * date 2017-3-3
     */
    @RequestMapping(value = "/doCommitWkfForConfig")
    @ResponseBody
    public Map <String, Object> doCommitWkfForConfig(Model model, String wkfAppId, String ajaxData, String nodeNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = getMapByJson(ajaxData);
        FormService formService = new FormService();
        MfCusCreditApply mfCusCreditApplyQuery = new MfCusCreditApply();
        mfCusCreditApplyQuery.setWkfAppId(wkfAppId);
        mfCusCreditApplyQuery = mfCusCreditApplyFeign.getById(mfCusCreditApplyQuery);
        String creditModel = "1";
        String creditType = "1";
        if (mfCusCreditApplyQuery != null) {
            creditModel = mfCusCreditApplyQuery.getCreditModel();
            creditType = mfCusCreditApplyQuery.getCreditType();
        }
        try {
            String formId = (String) dataMap.get("formId");
            if (StringUtil.isNotEmpty(formId)) {
                if (BizPubParm.CREDIT_TYPE_ADJUST.equals(dataMap.get("creditType"))) {
                    MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("creditAdjust" + creditModel, nodeNo);
                    if (mfKindForm != null) {
                        formId = mfKindForm.getAddModel();
                    }
                } else if (BizPubParm.CREDIT_TYPE_ADD.equals(dataMap.get("creditType"))) {
                    MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("credit" + creditModel, nodeNo);
                    if (mfKindForm != null) {
                        formId = mfKindForm.getAddModel();
                    }
                } else if (BizPubParm.CREDIT_TYPE_RENEW.equals(dataMap.get("creditType"))) {
                    MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("creditRenew" + creditModel, nodeNo);
                    if (mfKindForm != null) {
                        formId = mfKindForm.getAddModel();
                    }
                } else {
                    MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("credit" + creditModel, nodeNo);
                    if (mfKindForm != null) {
                        formId = mfKindForm.getAddModel();
                    }
                }
            }

            FormData formcreditapply0001 = formService.getFormData(formId);// "creditapply0001"
            getFormValue(formcreditapply0001, dataMap);
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            setObjValue(formcreditapply0001, mfCusCreditApply);
            String commitType = request.getParameter("commitType");  //用户保存表示是流程中那个节点url
            //业务进入下一个流程
            TaskImpl task = wkfInterfaceFeign.getTaskWithUser(wkfAppId, null, User.getRegNo(request));
            String url = "";
            if ("PROTOCOL".equals(commitType)) {  //授信协议
                url = "4#MfCusCreditApplyAction.queryCreditProtocol?wkfAppId=" + wkfAppId;
            }
            if ("REPORT".equals(commitType)) { //尽职报告
                url = "3#MfCusCreditApplyAction.queryCreditReport?wkfAppId=" + wkfAppId;
            }
            String transition = workflowDwrFeign.findNextTransition(task.getId());
            Result result = wkfInterfaceFeign.doCommit(task.getId(), AppConstant.OPINION_TYPE_ARREE, "", transition, User.getRegNo(request), "");
            if (result != null && result.isSuccess()) {
                if (StringUtil.isNotBlank(ajaxData)) {

                    // 修改业务申请
                    if (dataMap.containsKey("creditAppId")) {
                        mfCusCreditApply.setCreditAppId(dataMap.get("creditAppId").toString());
                    }
                    if (dataMap.containsKey("projectSurvey")) {
                        mfCusCreditApply.setProjectSurvey(dataMap.get("projectSurvey").toString());
                    }
                    dataMap.put("mfCusCreditApply", mfCusCreditApply);
                    dataMap.put("nodeNo", nodeNo);
                    mfCusCreditApplyFeign.updateForConfig(dataMap);
                }
                //更新业务阶段
                mfCusCreditApplyFeign.updateCreditBusStage(wkfAppId, wkfAppId, User.getRegNo(request));
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", result.getMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 将流程提交到下一个节点
     *
     * @author LJW
     * date 2017-3-3
     */
    @RequestMapping(value = "/doCommitByOpinionTypeWkf")
    @ResponseBody
    public Map <String, Object> doCommitByOpinionTypeWkf(Model model, String wkfAppId, String opinionType) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            //业务进入下一个流程
            TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
            String transition = workflowDwrFeign.findNextTransition(task.getId());
            //AppConstant.OPINION_TYPE_ARREE="1";//同意
            //OPINION_TYPE_RESTART="4";//发回重审
            Result result = wkfInterfaceFeign.doCommit(task.getId(), opinionType, "", transition, User.getRegNo(request), "");
            if (result != null && result.isSuccess()) {
                dataMap.put("flag", "success");
                dataMap.put("flag", result.getMsg());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", result.getMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 根据流程中配置的url判断是哪个节点
     *
     * @author LJW
     * date 2017-3-31
     */
    @RequestMapping(value = "/getNodeFlag")
    public String getNodeFlag(String url) {
        String[] strs = url.split("\\?")[0].split("_")[1].split("\\.");
        String method = strs[0];
        String nodeFlag = "";
        if ("inputCredit".equals(method)) {   //担保信息
            nodeFlag = "plege";
        } else if ("getOrderDescFirst".equals(method)) { //尽调报告
            nodeFlag = "report";
        } else if ("processSubmitAjax".equals(method)) { //授信审批
            nodeFlag = "credit_approval";
        } else if ("protocolPrint".equals(method)) {//授信协议
            nodeFlag = "protocolPrint";
        }else {
        }
        return nodeFlag;
    }

    /**
     * 授信审批提交
     *
     * @author LJW
     * date 2017-3-3
     */
    @RequestMapping(value = "/processSubmitAjax")
    @ResponseBody
    public Map <String, Object> processSubmitAjax(String wkfAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            mfCusCreditApply.setWkfAppId(wkfAppId);
            //mfCusCreditApply = mfCusCreditApplyFeign.getByWkfId(mfCusCreditApply);
            //提交合同审批流程
            mfCusCreditApply = mfCusCreditApplyFeign.submitProcess(mfCusCreditApply);
            dataMap.put("node", "processaudit");
            dataMap.put("flag", "success");
            Map <String, String> paramMap = new HashMap <String, String>();
            paramMap.put("userRole", mfCusCreditApply.getApproveNodeName());
            paramMap.put("opNo", mfCusCreditApply.getApprovePartName());
            dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 跳转到授信审批页面
     */
    @RequestMapping(value = "/creditApprove")
    public String creditApprove(Model model, String cusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        dataMap.put("cusNo", cusNo);
        dataMap = mfCusCreditApplyFeign.getCreditApproveDataMap(dataMap);
        String creditAppId = String.valueOf(dataMap.get("creditAppId"));
        String creditType = String.valueOf(dataMap.get("creditType"));
        ViewUtil.setViewPointParm(request, dataMap);

        FormData formcreditapprove0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
        getFormValue(formcreditapprove0001);
        if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
            getObjValue(formcreditapprove0001, dataMap.get("mfCusCreditApply"));
        } else {
            getObjValue(formcreditapprove0001, dataMap.get("mfCusCreditApply"));
            getObjValue(formcreditapprove0001, dataMap.get("mfCusCreditAdjustApply"));
        }
        //获得审批节点信息
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(creditAppId, null);
        String activityType = taskAppro.getActivityType();
        //处理审批意见类型
        List <OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), null);
        this.changeFormProperty(formcreditapprove0001, "approveResult", "optionArray", opinionTypeList);
        request.setAttribute("creditAppId", creditAppId);
        request.setAttribute("cusNo", String.valueOf(dataMap.get("cusNo")));
        request.setAttribute("creditType", String.valueOf(dataMap.get("creditType")));
        model.addAttribute("formcreditapprove0001", formcreditapprove0001);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCreditApplyHis_Detail";
    }

    /**
     * 提交审批
     *
     * @param id
     * @param taskId
     * @param transition
     * @param nextUser
     * @author LJW
     * date 2017-3-6
     */
    @RequestMapping(value = "/submitApproveAjax")
    @ResponseBody
    public Map <String, Object> submitApproveAjax(String ajaxData, String id, String taskId, String transition, String nextUser) throws Exception {
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            //更新授信审批信息
            updateApproveData(ajaxData);
            MfCusCreditApplyHis mfCusCreditApplyHis = new MfCusCreditApplyHis();
            mfCusCreditApplyHis.setId(id);
            mfCusCreditApplyHis = mfCusCreditApplyHisFeign.getById(mfCusCreditApplyHis);
            dataMap = getMapByJson(ajaxData);
            String opinionType = String.valueOf(dataMap.get("approveResult"));
            String approvalOpinion = String.valueOf(dataMap.get("approveRemark"));
            mfCusCreditApplyHis.setCurrentSessionOrgNo(User.getOrgNo(request));
            Result res = mfCusCreditApplyFeign.doCommitApprove(taskId, id, opinionType, approvalOpinion, transition, User.getRegNo(this.getHttpRequest()), nextUser, mfCusCreditApplyHis);
            if (res.isSuccess()) {
                dataMap.put("flag", "success");
                if (res.isEndSts()) {
                    dataMap.put("msg", res.getMsg());
                } else {
                    dataMap.put("msg", res.getMsg());
                }
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 打开授信审批历史页面
     *
     * @author LJW
     * date 2017-3-18
     */
    @RequestMapping(value = "/creditApprovalHis")
    public String creditApprovalHis(Model model, String ajaxData) throws Exception {
        return "/component/auth/credit_ApprovalHis";
    }

    /**
     * 提交审批时更新审批历史表数据
     *
     * @author LJW
     * date 2017-3-28
     */
    private void updateApproveData(String ajaxData) {
        MfCusCreditApplyHis mfCusCreditApplyHis = new MfCusCreditApplyHis();
        FormService formService = new FormService();
        FormData formcreditapprove0001 = formService.getFormData("creditapprove0001");
        Map <String, Object> jsonDatas = getMapByJson(ajaxData);
        getFormValue(formcreditapprove0001, jsonDatas);
        setObjValue(formcreditapprove0001, mfCusCreditApplyHis);

        String[] creditAmts = ((String) jsonDatas.get("creditAmts")).split(",");
        String[] kindNames = ((String) jsonDatas.get("kindNames")).split(",");
        List <MfCusCreditApplyHis> mfCusCreditApplyHiss = new ArrayList <MfCusCreditApplyHis>();
        mfCusCreditApplyHiss.add(mfCusCreditApplyHis);
        if (kindNames != null && kindNames.length > 0 && !Arrays.asList(kindNames).contains("")) {
            for (int i = 0, count = kindNames.length; i < count; i++) {
                MfCusCreditApplyHis mfCusCreditApplyHisTmp = new MfCusCreditApplyHis();
                mfCusCreditApplyHisTmp.setId(WaterIdUtil.getWaterId());
                mfCusCreditApplyHisTmp.setCreditAppId(mfCusCreditApplyHis.getCreditAppId());
                mfCusCreditApplyHisTmp.setCusNo(mfCusCreditApplyHis.getCusNo());
                mfCusCreditApplyHisTmp.setCusType(mfCusCreditApplyHis.getCusType());
                mfCusCreditApplyHisTmp.setCusName(mfCusCreditApplyHis.getCusName());
                mfCusCreditApplyHisTmp.setCreditSum(mfCusCreditApplyHis.getCreditSum());
                mfCusCreditApplyHisTmp.setIsCeilingLoop(mfCusCreditApplyHis.getIsCeilingLoop());
                mfCusCreditApplyHisTmp.setCreditTerm(mfCusCreditApplyHis.getCreditTerm());
                mfCusCreditApplyHisTmp.setBeginDate(mfCusCreditApplyHis.getBeginDate());
                mfCusCreditApplyHisTmp.setEndDate(mfCusCreditApplyHis.getEndDate());
                mfCusCreditApplyHisTmp.setKindNo((String) jsonDatas.get("kindNo_" + (i + 1)));
                mfCusCreditApplyHisTmp.setKindName(kindNames[i]);
                mfCusCreditApplyHisTmp.setCreditAmt(Double.valueOf(creditAmts[i]));
                mfCusCreditApplyHiss.add(mfCusCreditApplyHisTmp);
            }
        }
        //提交审批时更新审批历史表 先删除，再插入
        MfCusCreditApplyHis CreditApplyHis = new MfCusCreditApplyHis();
        CreditApplyHis.setCreditAppId(mfCusCreditApplyHis.getCreditAppId());
        mfCusCreditApplyHisFeign.delete(CreditApplyHis);
        mfCusCreditApplyHisFeign.insertByBatch(mfCusCreditApplyHiss);
    }


    /**
     * 向授信审批实体中注入值
     *
     * @author LJW
     * date 2017-3-4
     */
    @SuppressWarnings("unused")
    private MfCusCreditApplyHis getMfCusCreditApplyHisData(MfCusCreditApply mfCusCreditApplyArg,
                                                           List <MfCusPorductCredit> mfCusPorductCredits, MfCusCustomer mfCusCustomer) {
        List <MfCusCreditApplyHis> mfCusCreditApplyHiss = new ArrayList <MfCusCreditApplyHis>();
        MfCusCreditApplyHis cusCreditApplyHis = new MfCusCreditApplyHis();
        if (mfCusPorductCredits.size() > 0 && !mfCusPorductCredits.isEmpty()) {
            for (MfCusPorductCredit mfCusPorductCredit : mfCusPorductCredits) {
                MfCusCreditApplyHis mfCusCreditApplyHis = new MfCusCreditApplyHis();
                mfCusCreditApplyHis.setId(WaterIdUtil.getWaterId());
                mfCusCreditApplyHis.setCreditAppId(mfCusCreditApplyArg.getCreditAppId());
                mfCusCreditApplyHis.setCusNo(mfCusCustomer.getCusNo());
                mfCusCreditApplyHis.setCusType(mfCusCustomer.getCusType());
                mfCusCreditApplyHis.setCusName(mfCusCustomer.getCusName());
                mfCusCreditApplyHis.setCreditSum(mfCusCreditApplyArg.getCreditSum());
                mfCusCreditApplyHis.setIsCeilingLoop(mfCusCreditApplyArg.getIsCeilingLoop());
                mfCusCreditApplyHis.setCreditTerm(mfCusCreditApplyArg.getCreditTerm());
                mfCusCreditApplyHis.setBeginDate(mfCusCreditApplyArg.getBeginDate());
                mfCusCreditApplyHis.setEndDate(mfCusCreditApplyArg.getEndDate());
                mfCusCreditApplyHis.setKindNo(mfCusPorductCredit.getKindNo());
                mfCusCreditApplyHis.setKindName(mfCusPorductCredit.getKindName());
                mfCusCreditApplyHis.setCreditAmt(mfCusPorductCredit.getCreditAmt());
                mfCusCreditApplyHis.setApproveResult("1");
                mfCusCreditApplyHiss.add(mfCusCreditApplyHis);
            }
            mfCusCreditApplyHisFeign.insertByBatch(mfCusCreditApplyHiss);
            cusCreditApplyHis = mfCusCreditApplyHiss.get(0);
        } else {
            cusCreditApplyHis.setId(WaterIdUtil.getWaterId());
            cusCreditApplyHis.setCreditAppId(mfCusCreditApplyArg.getCreditAppId());
            cusCreditApplyHis.setCusNo(mfCusCustomer.getCusNo());
            cusCreditApplyHis.setCusType(mfCusCustomer.getCusType());
            cusCreditApplyHis.setCusName(mfCusCustomer.getCusName());
            cusCreditApplyHis.setCreditSum(mfCusCreditApplyArg.getCreditSum());
            cusCreditApplyHis.setIsCeilingLoop(mfCusCreditApplyArg.getIsCeilingLoop());
            cusCreditApplyHis.setCreditTerm(mfCusCreditApplyArg.getCreditTerm());
            cusCreditApplyHis.setBeginDate(mfCusCreditApplyArg.getBeginDate());
            cusCreditApplyHis.setEndDate(mfCusCreditApplyArg.getEndDate());
            mfCusCreditApplyHisFeign.insert(cusCreditApplyHis);
        }
        return cusCreditApplyHis;
    }

    /**
     * 新增校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/validateInsert")
    public void validateInsert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcreditapply0002 = formService.getFormData("creditapply0002");
        getFormValue(formcreditapply0002);
        boolean validateFlag = this.validateFormData(formcreditapply0002);
    }

    /**
     * 修改校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/validateUpdate")
    public void validateUpdate(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcreditapply0002 = formService.getFormData("creditapply0002");
        getFormValue(formcreditapply0002);
        boolean validateFlag = this.validateFormData(formcreditapply0002);
    }

    /**
     * 方法描述：
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-8-27 下午12:19:56
     */
    @RequestMapping(value = "/getCreditTemplateDataAjax")
    @ResponseBody
    public Map <String, Object> getCreditTemplateDataAjax(String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String tempType = request.getParameter("tempType");
        String relId = request.getParameter("relId");
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("tempType", tempType);
        //获得尽调报告数据
        dataMap = mfCusCreditApplyFeign.getCreditTemplateDataMap(dataMap);

        dataMap.put("relId", relId);
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("tempType", tempType);
        dataMap.put("opNo", User.getRegNo(request));
        dataMap.putAll(mfCusCreditApplyFeign.getIfSaveCreditTemplateInfo(dataMap));
        return dataMap;
    }

    /**
     * 方法描述： 尽调计划
     *
     * @param model
     * @param creditAppId
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018年4月9日 下午4:33:02
     */
    @RequestMapping(value = "/geReportPlan")
    public String geReportPlan(Model model, String creditAppId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String wkfAppId = request.getParameter("wkfAppId");
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("wkfAppId", wkfAppId);
        //获得尽调报告数据
        dataMap = mfCusCreditApplyFeign.getCreditReportDataMap(dataMap);
        JSONObject json = new JSONObject();
        json.put("mfCusPorductCreditList", JSONArray.fromObject(dataMap.get("mfCusPorductCreditList")));
        //json=(JSONObject) dataMap.get("json");

        String creditModel = String.valueOf(dataMap.get("creditModel"));
        String formId = prdctInterfaceFeign.getFormId("credit" + creditModel, WKF_NODE.report_plan, null, null, User.getRegNo(request));
        FormData formcreditapply0001 = formService.getFormData(formId);//String.valueOf(dataMap.get("formId"))
        getFormValue(formcreditapply0001);
        String creditType = String.valueOf(dataMap.get("creditType"));
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
            MfCusCreditContract mfCusCreditContract = (MfCusCreditContract) dataMap.get("mfCusCreditContract");
            if (mfCusCreditContract != null) {
                getObjValue(formcreditapply0001, mfCusCreditContract);
            } else {
                getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
            }
            getObjValue(formcreditapply0001, dataMap.get("mfCusCreditAdjustApply"));
        } else {
            getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
        }
        //设置表单元素不可编辑
        request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
        request.setAttribute("modelNo", String.valueOf(dataMap.get("modelNo")));
        request.setAttribute("json", json);
        request.setAttribute("kindMap", dataMap.get("kindMap"));
        request.setAttribute("appId", dataMap.get("appId"));
        request.setAttribute("creditType", creditType);
        request.setAttribute("wkfAppId", dataMap.get("wkfAppId"));
        request.setAttribute("creditAppId", creditAppId);
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("query", "");
        model.addAttribute("nodeNo", WKF_NODE.report_plan.getNodeNo());
        model.addAttribute("cusNo", String.valueOf(dataMap.get("cusNo")));
        model.addAttribute("creditAppId", creditAppId);
        return "/component/auth/MfCusCreditReportPlan";
    }

    /**
     * 新增授信
     *
     * @param kindNos
     * @param kindNames
     * @param creditAmts
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertReportPlanAjax")
    @ResponseBody
    public Map <String, Object> insertReportPlanAjax(String ajaxData, String creditAppId, String wkfAppId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditapply0001, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditapply0001)) {
                setObjValue(formcreditapply0001, mfCusCreditApply);
                mfCusCreditApplyFeign.update(mfCusCreditApply);
                dataMap = new HashMap <String, Object>();
                //业务进入下一个流程
                TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
                String transition = workflowDwrFeign.findNextTransition(task.getId());
                Result result = wkfInterfaceFeign.doCommit(task.getId(), AppConstant.OPINION_TYPE_ARREE, "", transition, User.getRegNo(request), "");
                if (result != null && result.isSuccess()) {
                    task = wkfInterfaceFeign.getTask(wkfAppId, null);
                    result.setMsg(MessageEnum.SUCCEED_APPROVAL_TONEXT.getMessage(task.getDescription()));
                    dataMap.put("flag", "success");
                } else {
                    dataMap.put("flag", "error");
                }
                dataMap.put("msg", result.getMsg());
                dataMap.put("creditAppId", mfCusCreditApply.getCreditAppId());
                dataMap.put("wkfAppId", mfCusCreditApply.getWkfAppId());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //新增授信产品异常时，需要删除授信信息
            mfCusCreditApplyFeign.delete(mfCusCreditApply);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 方法描述： 确认产品
     *
     * @param model
     * @param creditAppId
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018年4月9日 下午4:49:22
     */
    @RequestMapping(value = "/confirmProduct")
    public String confirmProduct(Model model, String creditAppId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String wkfAppId = request.getParameter("wkfAppId");
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("wkfAppId", wkfAppId);
        //获得尽调报告数据
        dataMap = mfCusCreditApplyFeign.getCreditReportDataMap(dataMap);
        JSONObject json = new JSONObject();
        json.put("mfCusPorductCreditList", JSONArray.fromObject(dataMap.get("mfCusPorductCreditList")));
        //json=(JSONObject) dataMap.get("json");
        String creditModel = String.valueOf(dataMap.get("creditModel"));
        String formId = prdctInterfaceFeign.getFormId("credit" + creditModel, WKF_NODE.confirm_product, null, null, User.getRegNo(request));
        FormData formcreditProjectProduct = formService.getFormData(formId);//String.valueOf(dataMap.get("formId"))
        getFormValue(formcreditProjectProduct);
        String creditType = String.valueOf(dataMap.get("creditType"));
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
            MfCusCreditContract mfCusCreditContract = (MfCusCreditContract) dataMap.get("mfCusCreditContract");
            if (mfCusCreditContract != null) {
                getObjValue(formcreditProjectProduct, mfCusCreditContract);
            } else {
                getObjValue(formcreditProjectProduct, dataMap.get("mfCusCreditApply"));
            }
            getObjValue(formcreditProjectProduct, dataMap.get("mfCusCreditAdjustApply"));
        } else {
            getObjValue(formcreditProjectProduct, dataMap.get("mfCusCreditApply"));
        }
        //设置表单元素不可编辑
        request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
        request.setAttribute("modelNo", String.valueOf(dataMap.get("modelNo")));
        request.setAttribute("json", json);
        request.setAttribute("kindMap", dataMap.get("kindMap"));
        request.setAttribute("appId", dataMap.get("appId"));
        request.setAttribute("creditType", creditType);
        request.setAttribute("wkfAppId", dataMap.get("wkfAppId"));
        request.setAttribute("creditAppId", dataMap.get("creditAppId"));
        model.addAttribute("formcreditProjectProduct", formcreditProjectProduct);
        model.addAttribute("query", "");
        model.addAttribute("nodeNo", WKF_NODE.confirm_product.getNodeNo());
        model.addAttribute("cusNo", String.valueOf(dataMap.get("cusNo")));
        model.addAttribute("creditAppId", creditAppId);
        return "/component/auth/MfCusCreditConfirmProduct";
    }

    /**
     * 方法描述： 确认产品保存
     *
     * @param ajaxData
     * @param creditAppId
     * @param wkfAppId
     * @return
     * @throws Exception Map<String,Object>
     * @author 沈浩兵
     * @date 2018年4月4日 下午7:45:23
     */
    @RequestMapping(value = "/saveConfirmProductAjax")
    @ResponseBody
    public Map <String, Object> saveConfirmProductAjax(String ajaxData, String creditAppId, String wkfAppId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditProjectProduct = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditProjectProduct, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditProjectProduct)) {
                setObjValue(formcreditProjectProduct, mfCusCreditApply);
                CodeUtils codeUtils = new CodeUtils();
                Map <String, String> parmData = codeUtils.getMapByKeyName("KIND_NO");
                mfCusCreditApply.setProjectProduct(parmData.get(mfCusCreditApply.getProjectProductNo()));
                mfCusCreditApplyFeign.update(mfCusCreditApply);
                dataMap = new HashMap <String, Object>();
                //业务进入下一个流程
                TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
                String transition = workflowDwrFeign.findNextTransition(task.getId());
                Result result = wkfInterfaceFeign.doCommit(task.getId(), AppConstant.OPINION_TYPE_ARREE, "", transition, User.getRegNo(request), "");
                if (result != null && result.isSuccess()) {
                    task = wkfInterfaceFeign.getTask(wkfAppId, null);
                    //更新业务阶段
                    mfCusCreditApply = new MfCusCreditApply();
                    mfCusCreditApply.setCreditAppId(creditAppId);
                    mfCusCreditApply.setBusStage(task.getDescription());
                    mfCusCreditApply.setNodeNo(task.getActivityName());
                    mfCusCreditApply.setLstModTime(DateUtil.getDateTime());
                    mfCusCreditApplyFeign.update(mfCusCreditApply);

                    result.setMsg(MessageEnum.SUCCEED_APPROVAL_TONEXT.getMessage(task.getDescription()));
                    dataMap.put("flag", "success");
                } else {
                    dataMap.put("flag", "error");
                }
                dataMap.put("msg", result.getMsg());
                dataMap.put("creditAppId", mfCusCreditApply.getCreditAppId());
                dataMap.put("wkfAppId", mfCusCreditApply.getWkfAppId());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //新增授信产品异常时，需要删除授信信息
            mfCusCreditApplyFeign.delete(mfCusCreditApply);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 列表立项查询打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCreditProjectListPage")
    public String getCreditProjectListPage(Model model) throws Exception {
        return "/component/auth/MfCusCreditApplyProject_List";
    }

    /***
     * 列表立项查询数据查询
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByPageAjaxProject")
    @ResponseBody
    public Map <String, Object> findByPageAjaxProject(String ajaxData, int pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            mfCusCreditApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusCreditApply.setCriteriaList(mfCusCreditApply, ajaxData);//我的筛选
            mfCusCreditApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfCusCreditApply.setCreditModel(BizPubParm.CREDIT_MODEL_PROJECT);
            //this.getRoleConditions(mfCusCoreCompany,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Feign方法
            ipage.setParams(this.setIpageParams("mfCusCreditApply", mfCusCreditApply));
            ipage = mfCusCreditApplyFeign.findByPageProject(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
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

    /**
     * 方法描述： 获得授信数据
     *
     * @param ajaxData
     * @param cusNo       客户号
     * @param creditModel 授信模式
     * @return
     * @throws Exception Map<String,Object>
     * @author 沈浩兵
     * @date 2018年4月26日 上午11:10:50
     */
    @RequestMapping(value = "/getCreditDataAjax")
    @ResponseBody
    public Map <String, Object> getCreditDataAjax(String ajaxData, String cusNo, String creditModel, String creditType, String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            dataMap = mfCusCreditApplyFeign.getCreditData(cusNo, creditModel, creditType, creditAppId);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 获取客户授信信息
     * @param ajaxData
     * @param cusNo
     * @param creditType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCusCreditDataAjax")
    @ResponseBody
    public Map <String, Object> getCusCreditDataAjax(String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            dataMap = mfCusCreditApplyFeign.getCusCreditData(cusNo);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }


    @RequestMapping(value = "/getCreditApplyFormHtmlAjax")
    @ResponseBody
    public Map <String, Object> getCreditApplyFormHtmlAjax(String cusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String creditType = request.getParameter("creditType");
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
        mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
        mfCusCreditApply.setCusNo(cusNo);
        mfCusCreditApply.setCreditType(creditType);
        dataMap = mfCusCreditApplyFeign.initCusCreditedInput(mfCusCreditApply);
        MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
        mfCusCreditModel.setCusTypeNo(mfCusCreditApply.getCusType());
        mfCusCreditModel = mfCusCreditModelFeign.getByCusTypeNo(mfCusCreditModel);
        String formId = "";
        if (null == mfCusCreditModel) {
            formId = "creditapply0001";
        } else {
            formId = mfCusCreditModel.getCreditFormId();
        }
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType)) {
            if (null == mfCusCreditModel) {
                formId = "adjustapp0001";
            } else {
                formId = mfCusCreditModel.getCreditAdjustFormId();
            }
            //mfCusCreditApply=(MfCusCreditApply) dataMap.get("mfCusCreditApply");
            mfCusCreditApply = (MfCusCreditApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditApply"), MfCusCreditApply.class);
            mfCusCreditApply.setCreditType(creditType);
        }
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        FormData formcreditapply0001 = formService.getFormData(formId);
        getObjValue(formcreditapply0001, mfCusCreditApply);
        String formHtml = jsonFormUtil.getJsonStr(formcreditapply0001, "bootstarpTag", "");
        dataMap.put("formHtml", formHtml);
        dataMap.put("flag", "success");
        return dataMap;
    }

    /**
     * 方法描述：经销商授信复核
     *
     * @param ajaxData
     * @param cusNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dealerCreditReCheckAjax")
    @ResponseBody
    public Map <String, Object> dealerCreditReCheckAjax(String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCusNo(cusNo);
            mfCusCreditApply.setCreditModel(BizPubParm.CREDIT_MODEL_CUS);
            mfCusCreditApply = mfCusCreditApplyFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
            if (mfCusCreditApply != null) {
                MfCusCreditApply mcca = new MfCusCreditApply();
                mcca.setCreditAppId(mfCusCreditApply.getCreditAppId());
                mcca.setDealerBusStage("2");//复核
                mcca.setDataShowWay("1");//进件数据PC主端是否展示（0：不展示，1：展示），内部人员申请默认展示，经销商申请复核后展示
                mcca.setChkStationId(User.getRegNo(request));
                mcca.setChkStationName(User.getRegName(request));
                mcca.setChkTime(DateUtil.getTime());
                mfCusCreditApplyFeign.update(mcca);
                dataMap.put("flag", "success");
                dataMap.put("msg", "复核成功！");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", "授信信息不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 根据授信业务类型（1风险2非风险）获得立项授信申请表单
     *
     * @param credidPactId
     * @return
     * @throws Exception Map<String,Object>
     * @author 沈浩兵
     * @date 2018年5月18日 上午10:48:20
     */
    @RequestMapping(value = "/getApplyFormHtmlByProjectTypeAjax")
    @ResponseBody
    public Map <String, Object> getApplyFormHtmlByProjectTypeAjax(String cusNo, String projectType, String creditModel) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCusNo(cusNo);
        if (mfCusCustomer != null) {
            mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
            mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
        }
        mfCusCreditApply.setCreditModel(creditModel);
        mfCusCreditApply.setProjectType(projectType);
        mfCusCreditApply.setCreditType(BizPubParm.CREDIT_TYPE_ADD);
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        String kindNo = "credit" + mfCusCreditApply.getCreditModel();
        if ("2".equals(projectType)) {//非风险
            kindNo = "credit" + creditModel + "-" + projectType;
        }
        String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
        FormData formcreditapply0001 = formService.getFormData(formId);
        getObjValue(formcreditapply0001, mfCusCreditApply);
        String formHtml = jsonFormUtil.getJsonStr(formcreditapply0001, "bootstarpTag", "");
        dataMap.put("formHtml", formHtml);
        dataMap.put("flag", "success");
        return dataMap;
    }

    @RequestMapping(value = "/getcreditSumAjax")
    @ResponseBody
    public Map <String, Object> getcreditSumAjax(String creditAppId, String cusNo) throws Exception {
        Map <String, Object> dataMap = new HashMap <String, Object>();
        JSONObject dataObj = new JSONObject();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        String idNum = mfCusCustomerFeign.getById(mfCusCustomer).getIdNum();
        //idNum = "91110108551385082Q";
        dataObj.put("agentId", idNum);
        String projectName = PropertiesUtil.getWebServiceProperty("sys.project.name");
        //projectName = "texBee";
        String json = mfCusCreditInterfaceFeign.queryCreditInfo(projectName, dataObj.toString());
        dataObj = JSONObject.fromObject(json);
        if ("1111".equals(dataObj.get("resultCode"))) {
            dataMap.put("msgFlag", "0");
            dataMap.put("msg", "查询数据异常");
            return dataMap;
        }
        JSONObject data = JSONObject.fromObject(dataObj.get("result"));
        JSONArray dataArray = JSONArray.fromObject(data.get("loanAgreementDetail"));//获取贷款合同明细
        if ("1".equals(data.get("result"))) {//如果查询成功
            data = JSONObject.fromObject(dataArray.getJSONObject(0));
            Double limitAmount = Double.parseDouble(MathExtend.divide(data.get("limitAmount").toString(), "100"));//获取JSON里的授信额度参数并转化为Double对象
            dataMap.put("creditSum", limitAmount);
            dataMap.put("msgFlag", "1");
        } else {
            dataMap.put("msgFlag", "0");
            dataMap.put("msg", "查询数据异常");
        }
        return dataMap;
    }

    /**
     * 方法描述： 客户授信菜单列表
     *
     * @param model
     * @param ajaxData
     * @return
     * @throws Exception String
     * @author YuShuai
     * @date 2018年6月23日 下午2:20:24
     */
    @RequestMapping(value = "/getCusCreditApplyListPage")
    public String getCusCreditApplyListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("CUS_TYPE");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCredit_List";
    }

    /**
     * 方法描述： ajax获取列表数据
     *
     * @param pageNo
     * @param pageSize
     * @param tableId
     * @param tableType
     * @param ajaxData
     * @param cusNo
     * @return
     * @throws Exception Map<String,Object>
     * @author YuShuai
     * @date 2018年6月23日 下午2:30:10
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findListAjax")
    @ResponseBody
    public Map <String, Object> findListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                             String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        try {
            mfCusCreditApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusCreditApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfCusCreditApply.setCriteriaList(mfCusCreditApply, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCusCreditApply", mfCusCreditApply));
            ipage = mfCusCreditApplyFeign.findListAjax(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
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
     * 归档时选择已完成授信
     * @param pageNo
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findListForArchiveAjax")
    @ResponseBody
    public Map <String, Object> findListForArchiveAjax(int pageNo,String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusCreditApply.setCustomSorts(ajaxData);
            mfCusCreditApply.setCriteriaList(mfCusCreditApply, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfCusCreditApply",mfCusCreditApply));
            ipage = mfCusCreditApplyFeign.findListForArchiveAjax(ipage);
            ipage.setParamsStr(ajaxData);
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
     * 方法描述： 获取可授信的客户列表
     *
     * @param pageNo
     * @param formId
     * @param element
     * @param ajaxData
     * @param ifFilterFlag
     * @return
     * @throws Exception Map<String,Object>
     * @author YuShuai
     * @date 2018年6月26日 下午4:48:02
     */
    @RequestMapping(value = "/getCreditUserList")
    @ResponseBody
    public Map <String, Object> getCreditUserList(int pageNo, Integer pageSize,String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);
            Map <String, Object> paramMap = new HashMap <String, Object>();
            paramMap.put("mfCusCustomer", mfCusCustomer);
            paramMap.put("ajaxData", ajaxData);
            ipage.setParams(this.setIpageParams("paramMap", paramMap));
            ipage = mfCusCreditApplyFeign.getCreditUserList(ipage);
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
     * 方法描述： 获取视角上客户授信概要信息
     *
     * @param creditAppId
     * @return
     * @throws Exception Map<String,Object>
     * @author YuShuai
     * @date 2018年7月6日 下午8:39:21
     */
    @RequestMapping(value = "/getCreditInfoAjax")
    @ResponseBody
    public Map <String, Object> getCreditInfoAjax(String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            Map <String, Object> map = mfCusCreditApplyFeign.getCreditInfoAjax(creditAppId);
            dataMap.putAll(map);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }



    /**
     * 方法描述： 授信菜单下新增客户授信
     * @param model
     * @return
     * @throws Exception
     * String
     * @author YuShuai
     * @date 2018年7月11日 上午10:08:11
     */
    @RequestMapping(value = "/inputForCreditView")
    public String inputForCreditView(Model model,String showType) throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String creditAppId = WaterIdUtil.getWaterId("SX");
        mfCusCreditApply.setCreditAppId(creditAppId);
        String cusNo = request.getParameter("cusNo");
        String creditModel = request.getParameter("creditModel");//授信模式1客户授信2项目授信
        String templateCreditId = request.getParameter("templateCreditId");//授信流程id
        String templateCreditName = request.getParameter("creditName");//授信流程名称
        String adaptationKindNo = request.getParameter("adaptationKindNo") ;//授信流程适配产品编号
        //查询对应产品名称
        if(StringUtil.isNotEmpty(adaptationKindNo) && !adaptationKindNo.equals("null")){
            MfSysKind mfSysKind =new MfSysKind();
            mfSysKind.setKindNo(adaptationKindNo);
            mfSysKind = mfSysKindFeign.getById(mfSysKind);
            mfCusCreditApply.setKindNo(adaptationKindNo);
            mfCusCreditApply.setKindName(mfSysKind.getKindName());
        }
        //查询流程配置
        MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
        mfCusCreditConfig.setCreditId(templateCreditId);
        mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
        String busModel =  mfCusCreditConfig.getBusModel();
        model.addAttribute("adaptationKindNo", adaptationKindNo);
        //根据产品可选择合作银行
        JSONObject busJson = new JSONObject();
        MfBusAgencies mfBusAgencies =  new MfBusAgencies();
        mfBusAgencies.setSupportProducts(adaptationKindNo);
        List<MfBusAgencies> mfBusAgenciesList =  mfBusAgenciesFeign.getAgenciesByKindNo(mfBusAgencies);
        JSONArray busAgenciesArray = new JSONArray();
        for (int i = 0; i < mfBusAgenciesList.size(); i++) {
            JSONObject js = new JSONObject();
            js.put("id", mfBusAgenciesList.get(i).getAgenciesUid());
            js.put("name", mfBusAgenciesList.get(i).getAgenciesName());
            busAgenciesArray.add(js);
        }
        busJson.put("busAgencies", busAgenciesArray);
        String  ajaxData= busJson.toString();
        model.addAttribute("ajaxData",ajaxData);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusCorpBaseInfo mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
        mfCusCreditApply.setIsAgainBus("0");
        String careaProvice = mfCusCorpBaseInfo.getCareaProvice();
        if(StringUtil.isNotEmpty(careaProvice)&&careaProvice.startsWith("110")){
            mfCusCreditApply.setIsAgainBus("1");
            mfCusCreditApply.setAgainBusRate(0.5);
        }
        if(StringUtil.isNotEmpty(mfCusCorpBaseInfo.getProjSize())&&!"1".equals(mfCusCorpBaseInfo.getProjSize())){
            mfCusCreditApply.setIsAgainBus("1");
            mfCusCreditApply.setAgainBusRate(0.5);
        }
        // 获取业务身份
        MfCusType mfCusType = new MfCusType();
        mfCusType.setTypeNo(mfCusCustomer.getCusType());
        mfCusType = mfCusTypeFeign.getById(mfCusType);
        mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
        mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
        mfCusCreditApply.setCusNo(cusNo);
        mfCusCreditApply.setCreditModel(creditModel);
        mfCusCreditApply.setCreditType(BizPubParm.CREDIT_TYPE_ADD);
        mfCusCreditApply.setTemplateCreditId(templateCreditId);
        mfCusCreditApply.setTemplateCreditName(templateCreditName);
        //工程展示企业资质和资产
        if(BizPubParm.BUS_MODEL_12.equals(busModel) && BizPubParm.CUS_TYPE_CORP.equals(mfCusCustomer.getCusBaseType())){
            mfCusCreditApply.setCorpQua(mfCusCorpBaseInfo.getCorpQua());
            mfCusCreditApply.setNetAssets(mfCusCorpBaseInfo.getNetAssets());
        }
        //获得打开授信页面所需要的参数
        /**
         * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
         */
        String creditFlag = BizPubParm.CREDIT_FLAG_NO;
        String termFlag = BizPubParm.TERM_FLAG_NO;
        //获得授信申请表单
        String formId = prdctInterfaceFeign.getFormId(templateCreditId, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
        // String formId = prdctInterfaceFeign.getFormId("credit"+creditModel, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
        FormData formcreditapply0001 = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
        //客户授信支持授信调整
        String creditProductBatchId = "SXCP" + WaterIdUtil.getWaterId();
        if (BizPubParm.CREDIT_MODEL_CUS.equals(creditModel)) {
            //未授信过，授信类型不可编辑，默认为新增授信
            if(BizPubParm.CREDIT_FLAG_NO.equals(creditFlag)||BizPubParm.CREDIT_TYPE_ADD.equals(mfCusCreditApply.getCreditType())){
                //mfCusCreditApply = new MfCusCreditApply();
                mfCusCreditApply.setCreditAppId(creditAppId);
                //mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
                //mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
                //mfCusCreditApply.setCusNo(cusNo);
                //mfCusCreditApply.setCreditType(BizPubParm.CREDIT_TYPE_ADD);
                mfCusCreditApply.setResponsibleBrNo(User.getOrgNo(request));
                mfCusCreditApply.setResponsibleBrName(User.getOrgName(request));
                mfCusCreditApply.setCreditProductBatchId(creditProductBatchId);//授信产品批次号，
                mfCusCreditApply.setManageOpNo(User.getRegNo(request));//办理人编号
                mfCusCreditApply.setManageOpName(User.getRegName(request));//办理人员
                getObjValue(formcreditapply0001, mfCusCreditApply);
                this.changeFormProperty(formcreditapply0001, "creditType", "readonly", "1");
            }
        }else if (BizPubParm.CREDIT_MODEL_PROJECT.equals(creditModel)) {
            //mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            //mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
            //mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
            //mfCusCreditApply.setCusNo(cusNo);
            //mfCusCreditApply.setCreditType(BizPubParm.CREDIT_TYPE_ADD);
            mfCusCreditApply.setResponsibleBrNo(User.getOrgNo(request));
            mfCusCreditApply.setResponsibleBrName(User.getOrgName(request));
            mfCusCreditApply.setManageOpNo(User.getRegNo(request));//办理人编号
            mfCusCreditApply.setManageOpName(User.getRegName(request));//办理人员
            NumberBigBean numberBigBean = new NumberBigBean();
            numberBigBean.setNoType(BizPubParm.NO_TYPE_PROJECT);
            numberBigBean.setCusNo(cusNo);
            numberBigBean.setCusType(mfCusType.getTypeNo());
            numberBigBean.setCreditModel(creditModel);
            numberBigBean = numberDataFeign.getNumberDataByRules(numberBigBean);
            String resultNo = numberBigBean.getResultNo();
            if(StringUtil.isNotEmpty(resultNo)){
                mfCusCreditApply.setProjectNo(resultNo);
            }
            getObjValue(formcreditapply0001, mfCusCreditApply);
            List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
            Map<String,String> dicMap=  new CodeUtils().getMapByKeyName("VOU_TYPE");
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind.setKindNo(adaptationKindNo);
            mfSysKind = mfSysKindFeign.getById(mfSysKind);
            String vouType = "";
            if (mfSysKind != null) {
                vouType = mfSysKind.getVouType();
            }
            String vouTypeArr[] = vouType.split("\\|");
            for(int i=0;i<vouTypeArr.length;i++){
                OptionsList s= new OptionsList();;
                s.setOptionValue(vouTypeArr[i]);
                s.setOptionLabel(dicMap.get(vouTypeArr[i]));
                vouTypeList.add(s);
            }
            this.changeFormProperty(formcreditapply0001, "vouType", "optionArray", vouTypeList);
        }else {
        }
        String creditAddFlag = new CodeUtils().getSingleValByKey("CUS_CREDIT_ADD_PRO");//是否是否允许单独新增产品  1:是   0：否
        if("1".equals(creditAddFlag)){
//			//获取期限内所有产品额度之和
//			String creditSum = mfCusCreditApplyFeign.getCreditProSum(cusNo);
            this.changeFormProperty(formcreditapply0001, "creditSum", "readonly", "1");
//			this.changeFormProperty(formcreditapply0001, "creditSum", "initValue", creditSum);
        }

        String scNo = request.getParameter("nodeNo");
        if (StringUtil.isNotEmpty(scNo)) {
            DocBizManageParam dbmp = new DocBizManageParam();
            dbmp.setScNo(scNo);// 场景编号
            dbmp.setRelNo(mfCusCreditApply.getCreditAppId());// 业务编号
            dbmp.setDime("credit" + creditModel);// 产品类型
            dbmp.setCusNo(cusNo);// 客户号
            dbmp.setRegNo(User.getRegNo(request));
            dbmp.setOrgNo(User.getOrgNo(request));
            docInterfaceFeign.initiaBiz(dbmp);
        }

        // 显示评分等级
        Map <String, Object> paramMap = new HashMap <String, Object>();
        paramMap.put("cusNo", cusNo);
        paramMap.put("useType", BizPubParm.USE_TYPE_1);
        Map <String, Object> appEvalMap = appEvalFeign.getCurrAppEvalData(paramMap);
        String cusLevelName = (String) appEvalMap.get("cusLevelName");
        this.changeFormProperty(formcreditapply0001, "cusLevelName", "initValue", cusLevelName);

        JSONObject json = new JSONObject();
        List <MfSysKind> mfSysKinds = new ArrayList <MfSysKind>();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setUseFlag("1");
        mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
        mfSysKinds = mfCusCreditApplyFeign.getApplyKindList(mfSysKinds, cusNo);
        List <MfSysKind> mfSysKindNews = new ArrayList <MfSysKind>();
        for (MfSysKind kind : mfSysKinds) {
            if(StringUtil.isNotEmpty(adaptationKindNo)){
                if(!adaptationKindNo.equals(kind.getKindNo())){
                    continue;
                }
            }
            kind.setRemark("");
            mfSysKindNews.add(kind);
        }
        JSONArray array = JSONArray.fromObject(mfSysKindNews);
        json.put("mfSysKinds", array);

        // 显示产品综合费率
        if (mfSysKinds.size() > 0) {
            Double monthTotalRate = mfSysKinds.get(0).getMonthTotalRate();
            if (monthTotalRate != null) {
                this.changeFormProperty(formcreditapply0001, "monthTotalRate", "initValue", monthTotalRate.toString());
                this.changeFormProperty(formcreditapply0001, "hidmonthTotalRate", "initValue", monthTotalRate.toString());
            }
        }
        //区分工程担保
        String bankShowFlag = "";//是否展示合作银行列表
        if(BizPubParm.BUS_MODEL_12.equals(busModel)){
            bankShowFlag = BizPubParm.YES_NO_N;
            //企业内部授信总额
            Double amtRest = creditApplyInterfaceFeign.getCreditFincAmtSum(cusNo);
            if(amtRest==null){
                amtRest = 0.00;
            }
            this.changeFormProperty(formcreditapply0001, "fincAmt", "initValue", amtRest.toString());
        }else {
            bankShowFlag = BizPubParm.YES_NO_Y;
        }

        request.setAttribute("json", json);
        request.setAttribute("termFlag", termFlag);
        request.setAttribute("creditFlag", creditFlag);
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("query", "");
        model.addAttribute("creditType", mfCusCreditApply.getCreditType());
        model.addAttribute("creditAddFlag", creditAddFlag);
        model.addAttribute("creditAppId", creditAppId);
        model.addAttribute("initFlag", BizPubParm.YES_NO_N);
        //显示合作银行及业务品种
        model.addAttribute("bankShowFlag", bankShowFlag);
        model.addAttribute("busModel", busModel);

        String CREDIT_END_DATE_REDUCE = new CodeUtils().getSingleValByKey("CREDIT_END_DATE_REDUCE");// 授信结束日自动减一天
        if (StringUtil.isNotEmpty(CREDIT_END_DATE_REDUCE)) {
            model.addAttribute("CREDIT_END_DATE_REDUCE", CREDIT_END_DATE_REDUCE);
        }
        model.addAttribute("scNo", scNo);
        if ("".equals(showType)) {
            showType = "2";
        }
        if(StringUtil.isNotEmpty(mfCusCreditApply.getKindNo())){
            MfSysKind kind = prdctInterfaceFeign.getSysKindById(mfCusCreditApply.getKindNo());
            if(kind != null && "1".equals(kind.getIfCreditCalc())){
                formId = prdctInterfaceFeign.getFormId(kind.getKindNo(), WKF_NODE.quota_calc, null, null, User.getRegNo(request));
                FormData formQuotaCalc = formService.getFormData(formId);
                getObjValue(formQuotaCalc, mfCusCreditApply);
                this.changeFormProperty(formQuotaCalc, "kindNo", "initValue", mfCusCreditApply.getKindNo());
                model.addAttribute("ifQuotaCalc", kind.getIfCreditCalc());
                model.addAttribute("formQuotaCalc", formQuotaCalc);
            }
        }


        model.addAttribute("showType", showType);
        return "/component/auth/MfCusCreditApply_input";
    }
    @RequestMapping(value = "/inputForCreditStop")
    public String inputForCreditStop(Model model,String wkfAppId) throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setWkfAppId(wkfAppId);
        mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
        FormData formcreditStopAdd = formService.getFormData("creditStopAdd");
        if(mfCusCreditApply == null){
            MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
            mfCusCreditAdjustApply.setWkfAppId(wkfAppId);
            mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
            mfCusCreditAdjustApply.setCreditSum(mfCusCreditAdjustApply.getAdjCreditSum());
            mfCusCreditAdjustApply.setCreditTerm(mfCusCreditAdjustApply.getAdjCreditTerm());
            getObjValue(formcreditStopAdd,mfCusCreditAdjustApply);
            this.changeFormProperty(formcreditStopAdd, "beginDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjBeginDate()));
            this.changeFormProperty(formcreditStopAdd, "endDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjEndDate()));

        }else{
            getObjValue(formcreditStopAdd,mfCusCreditApply);
        }
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setWkfAppId(wkfAppId);
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
        if(mfCusCreditContract != null){
            getObjValue(formcreditStopAdd,mfCusCreditContract);
        }
        this.changeFormProperty(formcreditStopAdd, "stopOpNo", "initValue", User.getRegNo(request));
        this.changeFormProperty(formcreditStopAdd, "stopOpName", "initValue", User.getRegName(request));
        this.changeFormProperty(formcreditStopAdd, "stopBrNo", "initValue", User.getOrgNo(request));
        this.changeFormProperty(formcreditStopAdd, "stopBrName", "initValue", User.getOrgName(request));
        this.changeFormProperty(formcreditStopAdd, "stopDate", "initValue", DateUtil.getShowDateTime(DateUtil.getDate()));
        model.addAttribute("formcreditStopAdd", formcreditStopAdd);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCreditApply_inputStop";
    }

    /**
     * 授信失效
     * @param model
     * @param wkfAppId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputForCreditInvalid")
    public String inputForCreditInvalid(Model model,String wkfAppId) throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        FormData formcreditInvalidAdd = formService.getFormData("creditInvalidAdd");
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setWkfAppId(wkfAppId);
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
        if(mfCusCreditContract != null){
            String cusNo = mfCusCreditContract.getCusNo();
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
            if(mfCusCustomer != null){
                mfCusCreditContract.setCusType(mfCusCustomer.getCusType());
            }
            getObjValue(formcreditInvalidAdd,mfCusCreditContract);
        }
        this.changeFormProperty(formcreditInvalidAdd, "invalidOpNo", "initValue", User.getRegNo(request));
        this.changeFormProperty(formcreditInvalidAdd, "invalidOpName", "initValue", User.getRegName(request));
        this.changeFormProperty(formcreditInvalidAdd, "invalidBrNo", "initValue", User.getOrgNo(request));
        this.changeFormProperty(formcreditInvalidAdd, "invalidBrName", "initValue", User.getOrgName(request));
        this.changeFormProperty(formcreditInvalidAdd, "invalidDate", "initValue", DateUtil.getShowDateTime(DateUtil.getDate()));
        model.addAttribute("formcreditInvalidAdd", formcreditInvalidAdd);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCreditApply_inputInvalid";
    }


    @RequestMapping(value = "/inputForCreditAdj")
    public String inputForCreditAdj(Model model, String cusNo, String creditId,String creditName,String creditModel) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        // 获取业务身份
        MfCusType mfCusType = new MfCusType();
        mfCusType.setTypeNo(mfCusCustomer.getCusType());
        mfCusType = mfCusTypeFeign.getById(mfCusType);
        String creditFlag = BizPubParm.CREDIT_FLAG_YES;
        String termFlag = null;
        //获得授信申请表单
        String formId = prdctInterfaceFeign.getFormId(creditId, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
        FormData formcreditapply0001 = formService.getFormData(formId);
        getObjValue(formcreditapply0001,mfCusCustomer);
        MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
        mfCusCreditAdjustApply.setCreditId(creditId);
        mfCusCreditAdjustApply.setCreditName(creditName);
        mfCusCreditAdjustApply.setCreditType(BizPubParm.CREDIT_TYPE_ADJUST);
        mfCusCreditAdjustApply.setCreditModel(creditModel);
        mfCusCreditAdjustApply.setManageOpNo(User.getRegNo(request));
        mfCusCreditAdjustApply.setManageOpName(User.getRegName(request));
        getObjValue(formcreditapply0001,mfCusCreditAdjustApply);
        MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
        mfCusCreditConfig.setCreditId(creditId);
        mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
        String adaptationCreditId = mfCusCreditConfig.getAdaptationCreditId();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setCusNo(cusNo);
        mfCusCreditContract.setCreditSts(BizPubParm.YES_NO_Y);
        mfCusCreditContract.setTabSts(BizPubParm.CREDIT_STS_TAB_D);
        mfCusCreditContract.setEndDate(DateUtil.getDate());
        List<MfCusCreditContract> mfCusCreditContractList = mfCusCreditContractFeign.getCusAndProjectCreditContractListByCusNo(mfCusCreditContract);
        MfCusCreditAdjustApply mfCusCreditAdjustApply1 = new MfCusCreditAdjustApply();
        mfCusCreditAdjustApply1.setCusNo(cusNo);
        List <MfCusCreditAdjustApply> mfCusCreditAdjustApplyList = mfCusCreditAdjustApplyFeign.getMfCusCreditAdjustApplyListByCusNo(mfCusCreditAdjustApply1);
        List<String> adjustPactIdList = new ArrayList<String>();
        for (MfCusCreditAdjustApply mfCusCreditAdjustApply2 : mfCusCreditAdjustApplyList) {
            String creditSts = mfCusCreditAdjustApply2.getCreditSts();
            if (StringUtil.isNotEmpty(creditSts) &&
                    (BizPubParm.CREDIT_STS_0.equals(creditSts) ||
                            BizPubParm.CREDIT_STS_1.equals(creditSts) ||
                            BizPubParm.CREDIT_STS_2.equals(creditSts) ||
                            BizPubParm.CREDIT_STS_3.equals(creditSts)
                    )) {
                adjustPactIdList.add(mfCusCreditAdjustApply2.getCreditPactId());
            }
        }
        JSONArray creditContractMap = new JSONArray();// 获取适配授信产品
        JSONObject json = new JSONObject();
        for (MfCusCreditContract mfCusCreditContract1: mfCusCreditContractList) {
            String creditModel1 = mfCusCreditContract1.getCreditModel();
            String creditId1 = mfCusCreditContract1.getTemplateCreditId();
            String creditPactId = mfCusCreditContract1.getId();
            JSONObject obj = new JSONObject();
            if(BizPubParm.CREDIT_MODEL_TEMPORARY.equals(creditModel)){
                if(BizPubParm.CREDIT_MODEL_PROJECT.equals(creditModel1)){
                    continue;
                }
            }
            if(BizPubParm.CREDIT_MODEL_ADJUST.equals(creditModel)){
                if(StringUtil.isNotEmpty(adaptationCreditId) && !adaptationCreditId.contains(creditId1)){
                    continue;
                }
            }
            if(adjustPactIdList.contains(creditPactId)){
                continue;
            }
            obj.put("id", mfCusCreditContract1.getId());
            obj.put("name",  mfCusCreditContract1.getPactNo());
            creditContractMap.add(obj);
        }
        json.put("creditContractMap", creditContractMap);

        List <MfSysKind> mfSysKinds = new ArrayList <MfSysKind>();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setUseFlag("1");
        mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
        mfSysKinds = mfCusCreditApplyFeign.getApplyKindList(mfSysKinds, cusNo);
        JSONArray array = JSONArray.fromObject(mfSysKinds);
        json.put("mfSysKinds", array);

        String CREDIT_END_DATE_REDUCE = new CodeUtils().getSingleValByKey("CREDIT_END_DATE_REDUCE");// 授信结束日自动减一天
        if (StringUtil.isNotEmpty(CREDIT_END_DATE_REDUCE)) {
            model.addAttribute("CREDIT_END_DATE_REDUCE", CREDIT_END_DATE_REDUCE);
        }
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", ajaxData);
        request.setAttribute("json", json);
        request.setAttribute("termFlag", termFlag);
        request.setAttribute("creditFlag", creditFlag);
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("query", "");
        model.addAttribute("creditType", BizPubParm.CREDIT_TYPE_ADJUST);
        //显示合作银行及业务品种
        model.addAttribute("bankShowFlag", BizPubParm.YES_NO_Y);
        if(StringUtil.isNotEmpty(mfCusCreditConfig.getAdaptationKindNo())){
            MfSysKind kind = prdctInterfaceFeign.getSysKindById(mfCusCreditConfig.getAdaptationKindNo());
            if(kind != null && "1".equals(kind.getIfCreditCalc())){
                formId = prdctInterfaceFeign.getFormId(mfCusCreditConfig.getAdaptationKindNo(), WKF_NODE.quota_calc, null, null, User.getRegNo(request));
                FormData formQuotaCalc = formService.getFormData(formId);
                getObjValue(formQuotaCalc, mfCusCreditAdjustApply);
                this.changeFormProperty(formQuotaCalc, "kindNo", "initValue", mfCusCreditConfig.getAdaptationKindNo());
                model.addAttribute("ifQuotaCalc", kind.getIfCreditCalc());
                model.addAttribute("formQuotaCalc", formQuotaCalc);
            }
        }
        return "/component/auth/MfCusCreditApply_input";
    }

    /**
     * 方法描述： 客户授信视角
     *
     * @param cusNo
     * @param busEntrance
     * @param relNo
     * @param ipage
     * @param model
     * @return
     * @throws Exception String
     * @author YuShuai
     * @date 2018年7月2日 上午10:32:19
     */
    @RequestMapping(value = "/getCusCreditView")
    public String getCusCreditView(String cusNo, String busEntrance, String creditAppId, String scNo, Ipage ipage, Model model, String entrance,String nodeNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        /*
         * 当query为"query"或者ifBizManger为"0"时，解析的表单中不可单字段编辑； 当ifBizManger为"1"或""时，解析的表单中设置的可编辑的字段可以单字段编辑； 当ifBizManger为"2"时，解析的表单中所有非只读的字段可以单字段编辑；
         */
        request.setAttribute("ifBizManger", "3");
        String wkfAppId = null;
        String query = "";
        //获取视图
        Map <String, String> paramMap = new HashMap <String, String>();
        paramMap.put("cusNo", cusNo);
        paramMap.put("creditAppId", creditAppId);
        paramMap.put("nodeNo", nodeNo);
        String generalClass = "cus";//视角所属大类为客户
        String isValid = "";
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(creditAppId);
        mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
        String primaryId = "";
        String creditModel;
        String creditName = "";
        String creditSts = BizPubParm.CREDIT_STS_1;
        String creditType = BizPubParm.CREDIT_TYPE_ADD;
        Double creditSum = 0.00;
        Double authBal = 0.00;
        String creditTerm = "";
        String creditTermShow = "";
        String appNodeNo = "";//当前业务所处节点
        String busModel = "";//业务模式
        if (mfCusCreditApply == null) {
            MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
            mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
            mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
            wkfAppId = mfCusCreditAdjustApply.getWkfAppId();
            creditModel = mfCusCreditAdjustApply.getCreditModel();
            creditSts = mfCusCreditAdjustApply.getCreditSts();
            creditType = mfCusCreditAdjustApply.getCreditType();
            creditName = mfCusCreditAdjustApply.getCreditName();
            creditSum = mfCusCreditAdjustApply.getAdjCreditSum();
            authBal = mfCusCreditAdjustApply.getAdjAuthBal();
            creditTerm = mfCusCreditAdjustApply.getAdjCreditTerm();
            creditTermShow = mfCusCreditAdjustApply.getAdjCreditTermShow();
            cusNo = mfCusCreditAdjustApply.getCusNo();
            appNodeNo = mfCusCreditAdjustApply.getNodeNo();
        } else {
            creditSts = mfCusCreditApply.getCreditSts();
            wkfAppId = mfCusCreditApply.getWkfAppId();
            isValid = mfCusCreditApply.getIsValid();
            creditModel = mfCusCreditApply.getCreditModel();
            primaryId = mfCusCreditApply.getPrimaryAppId();
            creditType = mfCusCreditApply.getCreditType();
            creditName = mfCusCreditApply.getTemplateCreditName();
            creditSum = mfCusCreditApply.getCreditSum();
            authBal = mfCusCreditApply.getAuthBal();
            creditTerm = mfCusCreditApply.getCreditTerm();
            creditTermShow = mfCusCreditApply.getCreditTermShow();
            cusNo = mfCusCreditApply.getCusNo();
            appNodeNo = mfCusCreditApply.getNodeNo();
            model.addAttribute("templateCreditId", mfCusCreditApply.getTemplateCreditId());
            MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
            mfCusCreditConfig.setCreditId(mfCusCreditApply.getTemplateCreditId());
            mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
            busModel = mfCusCreditConfig.getBusModel();
        }

        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setCreditAppId(creditAppId);
        mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
        String pactId = "";
        if (mfCusCreditContract != null) {
            pactId = mfCusCreditContract.getPactId();
            creditSum = mfCusCreditContract.getCreditSum();
            authBal = mfCusCreditContract.getAuthBal();
            creditTerm = String.valueOf(mfCusCreditContract.getCreditTerm());
            creditTermShow  = String.valueOf(mfCusCreditContract.getCreditTermShow());
        }

        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

        // 2、风险级别信息
        String riskLevel = "-1";
        String riskName = "风险检查通过";
        if("1".equals(creditModel)){  //客户授信时查询风险级别
            List <RiskBizItemRel> riskBizItemRelList = riskInterfaceFeign.findByRelNo(cusNo);
            for (int i = 0; i < riskBizItemRelList.size(); i++) {
                String riskLevelThis = riskBizItemRelList.get(i).getRiskLevel();
                // 风险级别越高，riskLevel越大；如果是风险级别是业务拒绝级，riskLevel为99
                if ("99".equals(riskLevelThis)) {
                    riskLevel = riskLevelThis;
                    break;
                } else {
                    if (Integer.valueOf(riskLevelThis) > Integer.valueOf(riskLevel)) {
                        riskLevel = riskLevelThis;
                    }
                }
            }

            if (!"-1".equals(riskLevel) && !"0".equals(riskLevel)) {
                Map <String, String> dicMap = new CodeUtils().getMapByKeyName("RISK_PREVENT_LEVEL");
                riskName = dicMap.get(riskLevel);
            }
            dataMap.put("riskLevel", riskLevel);
            dataMap.put("riskName", riskName);
        }
        paramMap.put("appNodeNo", appNodeNo);
        if(BizPubParm.BUS_MODEL_12.equals(busModel)){
            paramMap.put("busClass", busModel);
        }else {
            paramMap.put("busClass", "base");
        }
        Map <String, Object> busViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, paramMap);
        dataMap.putAll(busViewMap);
        //判断该授信是否在发起首笔担保业务
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setCreditAppId(creditAppId);
        mfBusApply = mfBusApplyFeign.getMfBusApplyByCredit(mfBusApply);
        if(mfBusApply!=null){
            model.addAttribute("isCreditFlag", "1");
        }else{
            model.addAttribute("isCreditFlag", "0");
        }
        //判断抵质押额度
        boolean tipFlag =false;
        List<MfBusCollateralDetailRel> mfBusCollateralDetailRelList = collateralInterfaceFeign.getCollateralDetailRelList(creditAppId);
        if(mfBusCollateralDetailRelList.size()>0){
            for (int i = 0; i < mfBusCollateralDetailRelList.size(); i++) {
                MfBusCollateralDetailRel mfBusCollateralDetailRel = mfBusCollateralDetailRelList.get(i);
                PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                pledgeBaseInfo.setPledgeNo(mfBusCollateralDetailRel.getCollateralId());
                pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
                if(pledgeBaseInfo!=null){
                    if("C".equals(pledgeBaseInfo.getClassFirstNo())&&mfBusCollateralDetailRel.getIfRegister().equals("1")){
                        PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
                        pledgeBaseInfoBill.setIsHis("0");
                        pledgeBaseInfoBill.setPledgeNo(pledgeBaseInfo.getPledgeNo());
                        List<PledgeBaseInfoBill> pledgeBaseInfoBillList = pledgeBaseInfoBillFeign.getBillListByPledgeNo(pledgeBaseInfoBill);
                        double billSum = 0.00;
                        for (int j = 0; j < pledgeBaseInfoBillList.size(); j++) {
                            if(pledgeBaseInfoBillList.get(j).getSingleWeight()!=null){
                                billSum =MathExtend.add(billSum,pledgeBaseInfoBillList.get(j).getSingleWeight());
                            }
                        }
                        if(pledgeBaseInfo.getExtNum12()!=null&&billSum<pledgeBaseInfo.getExtNum12()){
                            tipFlag = true;
                        }
                    }
                }
            }
        }
        if(tipFlag){
            model.addAttribute("tips", "应收账款余额小于应收账款抵押余值");
        }
        // 获取授信状态名称名称
        String creditStsName = getCreditAppStsName(creditAppId);
        String opNo = mfCusCustomer.getOpNo();
//        query = mfCusCustomerFeign.validateCreditCusFormModify(cusNo, creditAppId, BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
        model.addAttribute("opNo", opNo);
        model.addAttribute("appNodeNo", appNodeNo);
        model.addAttribute("creditStsName", creditStsName);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("mfCusCustomer", mfCusCustomer);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("useType", BizPubParm.USE_TYPE_3);
        model.addAttribute("scNo", BizPubParm.CUSTOMER_CREDIT_ID);
        model.addAttribute("wkfAppId", wkfAppId);
        model.addAttribute("creditType", creditType);
        model.addAttribute("relNo", creditAppId);
        model.addAttribute("relId", creditAppId);
        model.addAttribute("busEntrance", busEntrance);
        model.addAttribute("creditAppId", creditAppId);
        model.addAttribute("baseType", mfCusCustomer.getCusBaseType());
        model.addAttribute("entrance", entrance);
        model.addAttribute("query", query);
        model.addAttribute("creditSts", creditSts);
        model.addAttribute("creditName", creditName);
        model.addAttribute("pactId", pactId);
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("isValid", isValid);
        model.addAttribute("primaryId", primaryId);
        model.addAttribute("creditModel", creditModel);
        model.addAttribute("creditSum", MathExtend.divide(String.valueOf(creditSum), "10000", creditSum==0?2:6));
        model.addAttribute("authBal", MathExtend.divide(String.valueOf(authBal), "10000", authBal==0?2:6));
        model.addAttribute("creditTerm", creditTerm);
        model.addAttribute("creditTermShow", creditTermShow);
        model.addAttribute("busModel", busModel);
        return "/component/auth/MfCusCredit_DynaDetail";
    }

    /**
     * 方法描述： 获取当前授信能否进行冻结，解冻操作
     *
     * @param creditAppId
     * @return
     * @throws Exception Map<String,Object>
     * @author YuShuai
     * @date 2018年7月6日 下午8:39:21
     */
    @RequestMapping(value = "/getFrozenThawFlag")
    @ResponseBody
    public Map <String, Object> getFrozenThawFlag(String creditAppId, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String frozeFlag = BizPubParm.YES_NO_N;//是否允许冻结
        String thawFlag = BizPubParm.YES_NO_N;//是否允许解冻
        try {
            //获取授信合同
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
            if (mfCusCreditContract != null) {
                String creditModel = mfCusCreditContract.getCreditModel();
                if(StringUtil.isNotEmpty(creditModel) && (BizPubParm.CREDIT_MODEL_CUS.equals(creditModel) || BizPubParm.CREDIT_MODEL_PROJECT.equals(creditModel))){
                    String creditSts = mfCusCreditContract.getCreditSts();
                    String beginDate = mfCusCreditContract.getBeginDate();
                    String endDate = mfCusCreditContract.getEndDate();
                    String currDate = DateUtil.getDate();
                    MfCreditFrozenThaw mfCreditFrozenThaw = new MfCreditFrozenThaw();
                    mfCreditFrozenThaw.setCreditContractId(mfCusCreditContract.getId());
                    List<MfCreditFrozenThaw> mfCreditFrozenThawList = mfCreditFrozenThawFeign.getFrozenThawList(mfCreditFrozenThaw);
                    if(mfCreditFrozenThawList != null && mfCreditFrozenThawList.size() > 0) {
                        mfCreditFrozenThaw = mfCreditFrozenThawList.get(0);
                    }
                    String operaSts = mfCreditFrozenThaw.getOperaSts();
                    if(BizPubParm.CREDIT_PACT_STS_1.equals(creditSts) && currDate.compareTo(beginDate) >= 0 && currDate.compareTo(endDate) <= 0){
                        if(StringUtil.isEmpty(operaSts) ||
                                (StringUtil.isNotEmpty(operaSts) &&
                                        !BizPubParm.CREDIT_STS_0.equals(operaSts) &&
                                        !BizPubParm.CREDIT_STS_1.equals(operaSts) &&
                                        !BizPubParm.CREDIT_STS_2.equals(operaSts)
                                )){
                            frozeFlag = BizPubParm.YES_NO_Y;
                        }
                    }
                    if (BizPubParm.CREDIT_PACT_STS_6.equals(creditSts)) {
                        if(StringUtil.isEmpty(operaSts) ||
                                (StringUtil.isNotEmpty(operaSts) &&
                                        !BizPubParm.CREDIT_STS_0.equals(operaSts) &&
                                        !BizPubParm.CREDIT_STS_1.equals(operaSts) &&
                                        !BizPubParm.CREDIT_STS_2.equals(operaSts)
                                )){
                            thawFlag = BizPubParm.YES_NO_Y;
                        }
                    }
                }
            }
            dataMap.put("flag", "success");
            dataMap.put("frozeFlag", frozeFlag);
            dataMap.put("thawFlag", thawFlag);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getMfCusCreditUseHisInfoAjax")
    @ResponseBody
    public Map <String, Object> getMfCusCreditUseHisInfoAjax(String wkfAppId,String cusNo,String pactId,String creditModel) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String ifShowUseHisFlag = BizPubParm.YES_NO_N;
        try {
            dataMap.put("wkfAppId",wkfAppId);
            dataMap.put("cusNo",cusNo);
            dataMap.put("pactId",pactId);
            dataMap.put("creditModel",creditModel);
            dataMap = mfCusCreditApplyFeign.getCreditDetailDataMap(dataMap);
            // 授信用信历史
            List <MfCusCreditUseHis> mfCusCreditUseHisList = (List <MfCusCreditUseHis>) dataMap.get("mfCusCreditUseHisList");
            if(mfCusCreditUseHisList != null && mfCusCreditUseHisList.size() > 0){
                ifShowUseHisFlag = BizPubParm.YES_NO_Y;
                JsonTableUtil jtu = new JsonTableUtil();
                String  mfCusCreditUseHisListHtml = jtu.getJsonStr("tablecreditapplyhist001","thirdTableTag",mfCusCreditUseHisList, null,true);
                dataMap.put("mfCusCreditUseHisListHtml", mfCusCreditUseHisListHtml);
            }
            dataMap.put("flag", "success");
            dataMap.put("ifShowUseHisFlag", ifShowUseHisFlag);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
    @RequestMapping(value = "/getMfCusPorductCreditListAjax")
    @ResponseBody
    public Map <String, Object> getMfCusPorductCreditListAjax(String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String ifShowPorductCreditFlag = BizPubParm.YES_NO_N;
        try {
            MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
            mfCusPorductCredit.setCreditAppId(creditAppId);
            List <MfCusPorductCredit> mfCusCreditProductDetailList = mfCusPorductCreditFeign.getMfCusPorductCreditList(mfCusPorductCredit);
            if(mfCusCreditProductDetailList != null && mfCusCreditProductDetailList.size() > 0){
                ifShowPorductCreditFlag = BizPubParm.YES_NO_Y;
                JsonTableUtil jtu = new JsonTableUtil();
                String  mfCusCreditUseHisListHtml = jtu.getJsonStr("tablecreditproduct0004","thirdTableTag",mfCusCreditProductDetailList, null,true);
                dataMap.put("mfCusCreditUseHisListHtml", mfCusCreditUseHisListHtml);
            }
            dataMap.put("flag", "success");
            dataMap.put("ifShowPorductCreditFlag", ifShowPorductCreditFlag);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * // 初始化授信调整，临额调整按钮
     * @param creditAppId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCreditAdjustFlag")
    @ResponseBody
    public Map <String, Object> getCreditAdjustFlag(String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String ifAdjust = BizPubParm.YES_NO_N;//是否允许授信调整
        String ifTemporary = BizPubParm.YES_NO_N;//是否允许临额调整
        try {
            //获取授信合同
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
            if (mfCusCreditContract != null) {
                String creditModel = mfCusCreditContract.getCreditModel();
                if(StringUtil.isNotEmpty(creditModel) && (BizPubParm.CREDIT_MODEL_CUS.equals(creditModel) || BizPubParm.CREDIT_MODEL_PROJECT.equals(creditModel))){
                    String creditSts = mfCusCreditContract.getCreditSts();
                    String beginDate = mfCusCreditContract.getBeginDate();
                    String endDate = mfCusCreditContract.getEndDate();
                    String currDate = DateUtil.getDate();
                    if(BizPubParm.CREDIT_PACT_STS_1.equals(creditSts) && currDate.compareTo(beginDate) >= 0 && currDate.compareTo(endDate) <= 0){
                        MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                        mfCusCreditAdjustApply.setCreditPactId(mfCusCreditContract.getId());
                        boolean ifAdjusting = false;
                        List<MfCusCreditAdjustApply> mfCusCreditAdjustApplyList = mfCusCreditAdjustApplyFeign.getCreditAdjustApplyList(mfCusCreditAdjustApply);
                        for (MfCusCreditAdjustApply mfCusCreditAdjustApply1: mfCusCreditAdjustApplyList) {
                            String adjustCreditSts = mfCusCreditAdjustApply1.getCreditSts();
                            if(!BizPubParm.CREDIT_STS_4.equals(adjustCreditSts) &&
                                    !BizPubParm.CREDIT_STS_5.equals(adjustCreditSts) &&
                                    !BizPubParm.CREDIT_STS_7.equals(adjustCreditSts) &&
                                    !BizPubParm.CREDIT_STS_8.equals(adjustCreditSts)
                                    ){
                                ifAdjusting = true;
                                break;
                            }
                        }
                        if(!ifAdjusting){
                            MfCreditFrozenThaw mfCreditFrozenThaw = new MfCreditFrozenThaw();
                            mfCreditFrozenThaw.setCreditContractId(mfCusCreditContract.getId());
                            List<MfCreditFrozenThaw> mfCreditFrozenThawList = mfCreditFrozenThawFeign.getFrozenThawList(mfCreditFrozenThaw);
                            if(mfCreditFrozenThawList != null && mfCreditFrozenThawList.size() > 0) {
                                mfCreditFrozenThaw = mfCreditFrozenThawList.get(0);
                            }
                            String operaSts = mfCreditFrozenThaw.getOperaSts();
                            if(StringUtil.isEmpty(operaSts) ||
                                    (StringUtil.isNotEmpty(operaSts) &&
                                            !BizPubParm.CREDIT_STS_0.equals(operaSts) &&
                                            !BizPubParm.CREDIT_STS_1.equals(operaSts) &&
                                            !BizPubParm.CREDIT_STS_2.equals(operaSts)
                                    )){
                                ifAdjust = BizPubParm.YES_NO_Y;
                                if(BizPubParm.CREDIT_MODEL_CUS.equals(creditModel)){
                                    ifTemporary = BizPubParm.YES_NO_Y;
                                }
                            }
                        }
                    }
                }
            }
            dataMap.put("flag", "success");
            dataMap.put("ifAdjust", ifAdjust);
            dataMap.put("ifTemporary", ifTemporary);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
    /**
     * // 初始化授信终止，授信失效按钮
     * @param creditAppId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCreditStopInvalidFlag")
    @ResponseBody
    public Map <String, Object> getCreditStopInvalidFlag(String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String ifStop = BizPubParm.YES_NO_N;//是否允许授信终止
        String ifInvalid = BizPubParm.YES_NO_N;//是否允许授信失效
        try {
            String nodeNo = null;
            String creditSts = null;
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
            if(mfCusCreditApply == null){
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
                mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                if(mfCusCreditAdjustApply != null){
                    nodeNo = mfCusCreditAdjustApply.getNodeNo();
                    creditSts = mfCusCreditAdjustApply.getCreditSts();
                }
            }else{
                nodeNo = mfCusCreditApply.getNodeNo();
                creditSts = mfCusCreditApply.getCreditSts();
            }
            //获取授信合同
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
            if (mfCusCreditContract != null) {
                String creditModel = mfCusCreditContract.getCreditModel();
                String creditPactSts = mfCusCreditContract.getCreditSts();
                if(StringUtil.isNotEmpty(creditModel) && (BizPubParm.CREDIT_MODEL_CUS.equals(creditModel) || BizPubParm.CREDIT_MODEL_PROJECT.equals(creditModel) || BizPubParm.CREDIT_MODEL_TEMPORARY.equals(creditModel))){
                    String beginDate = mfCusCreditContract.getBeginDate();
                    String endDate = mfCusCreditContract.getEndDate();
                    String currDate = DateUtil.getDate();
                    if(BizPubParm.CREDIT_PACT_STS_1.equals(creditPactSts) && currDate.compareTo(beginDate) >= 0 && currDate.compareTo(endDate) <= 0){
                        MfCreditFrozenThaw mfCreditFrozenThaw = new MfCreditFrozenThaw();
                        mfCreditFrozenThaw.setCreditContractId(mfCusCreditContract.getId());
                        List<MfCreditFrozenThaw> mfCreditFrozenThawList = mfCreditFrozenThawFeign.getFrozenThawList(mfCreditFrozenThaw);
                        if(mfCreditFrozenThawList != null && mfCreditFrozenThawList.size() > 0) {
                            mfCreditFrozenThaw = mfCreditFrozenThawList.get(0);
                        }
                        String operaSts = mfCreditFrozenThaw.getOperaSts();
                        if(StringUtil.isEmpty(operaSts) ||
                                (StringUtil.isNotEmpty(operaSts) &&
                                        !BizPubParm.CREDIT_STS_0.equals(operaSts) &&
                                        !BizPubParm.CREDIT_STS_1.equals(operaSts) &&
                                        !BizPubParm.CREDIT_STS_2.equals(operaSts)
                                )){
                            ifInvalid = BizPubParm.YES_NO_Y;
                        }
                    }
                }
            }
            // 节点非空且不处于审批状态时可终止
            if(StringUtil.isNotEmpty(nodeNo) &&
                    !WKF_NODE.credit_primary_approval.getNodeNo().equals(nodeNo) &&
                    !WKF_NODE.credit_approval.getNodeNo().equals(nodeNo) &&
                    !WKF_NODE.primary_credit_pact_approval.getNodeNo().equals(nodeNo) &&
                    !WKF_NODE.credit_pact_approval.getNodeNo().equals(nodeNo)
                    ){
                if(StringUtil.isNotEmpty(creditSts) && (
                        BizPubParm.CREDIT_STS_0.equals(creditSts) ||
                                BizPubParm.CREDIT_STS_1.equals(creditSts) ||
                                BizPubParm.CREDIT_STS_2.equals(creditSts) ||
                                BizPubParm.CREDIT_STS_3.equals(creditSts)
                )){
                    ifStop = BizPubParm.YES_NO_Y;
                }
            }
            dataMap.put("flag", "success");
            dataMap.put("ifStop", ifStop);
            dataMap.put("ifInvalid", ifInvalid);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    public String getCreditAppStsName(String creditAppId) throws Exception {
        String result = "流程中";
        String sts = null;
        String nodeNo = "finish";
        if (StringUtil.isNotEmpty(creditAppId)) {
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
            if (mfCusCreditContract != null) {
                sts = mfCusCreditContract.getCreditSts();
            } else {
                Map <String, Object> map = mfCusCreditApplyFeign.getCreditInfoAjax(creditAppId);
                if (BizPubParm.CREDIT_TYPE_ADD.equals(map.get("creditType"))) {
                    MfCusCreditApply mfCusCreditApply = JsonStrHandling.handlingStrToBean(map.get("mfCusCreditApply"), MfCusCreditApply.class);
                    sts = mfCusCreditApply.getCreditSts();
                    nodeNo = mfCusCreditApply.getNodeNo();
                } else {
                    MfCusCreditAdjustApply mfCusCreditAdjustApply = JsonStrHandling.handlingStrToBean(map.get("mfCusCreditAdjustApply"), MfCusCreditAdjustApply.class);
                    sts = mfCusCreditAdjustApply.getCreditSts();
                    nodeNo = mfCusCreditAdjustApply.getNodeNo();
                }
            }
            if (StringUtil.isNotEmpty(sts)) {
                switch (sts) {
                    case "0":
                        result = "签约中";
                        break;
                    case "1":
                        if ("finish".equals(nodeNo)) {
                            result = "已签约";
                        } else {
                            result = "申请中";
                        }
                        break;
                    case "2":
                        result = "审批中";
                        break;
                    case "3":
                        result = "审批通过";
                        break;
                    case "4":
                        result = "已否决";
                        break;
                    case "5":
                        result = "已签约";
                        break;
                    case "6":
                        result = "已冻结";
                        break;
                    case "7":
                        result = "已失效";
                        break;
                    case "8":
                        result = "已终止";
                        break;
                    default:
                        break;
                }
            }

        }
        return result;
    }
    /**
     * 查询授信状态
     * @param creditAppId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCreditAppStsAjax")
    @ResponseBody
    public Map <String, Object> getCreditAppStsAjax(String creditAppId) throws Exception {
        Map <String, Object> dataMap = new HashMap <String, Object>();
        dataMap.put("creditStsName",getCreditAppStsName(creditAppId));
        dataMap.put("flag","success");
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCusCreditApplyDetailAjax")
    @ResponseBody
    public Map <String, Object> getCusCreditApplyDetailAjax(String creditAppId, String formId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formcusCreditApplyDetail = formService.getFormData(formId);
        Map <String, Object> resMap = mfCusCreditApplyFeign.getCreditInfoAjax(creditAppId);
        String cusNo = "";
        String query = "";
        if (BizPubParm.CREDIT_TYPE_ADD.equals(resMap.get("creditType"))) {
            MfCusCreditApply mfCusCreditApply = JsonStrHandling.handlingStrToBean(resMap.get("mfCusCreditApply"), MfCusCreditApply.class);
            MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
            mfCusCreditConfig.setCreditId(mfCusCreditApply.getTemplateCreditId());
            mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
            if(mfCusCreditConfig!=null&&BizPubParm.BUS_MODEL_12.equals(mfCusCreditConfig.getBusModel())){
                formcusCreditApplyDetail = formService.getFormData("CusCreditApplyDetail25");
            }
            getObjValue(formcusCreditApplyDetail, mfCusCreditApply);
            cusNo = mfCusCreditApply.getCusNo();
        } else {
            MfCusCreditAdjustApply mfCusCreditAdjustApply = JsonStrHandling.handlingStrToBean(resMap.get("mfCusCreditAdjustApply"), MfCusCreditAdjustApply.class);
            mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
            resMap.put("cusNo", mfCusCreditAdjustApply.getCusNo());
            resMap.put("cusName", mfCusCreditAdjustApply.getCusName());
            resMap.put("creditSum", mfCusCreditAdjustApply.getAdjCreditSum());
            resMap.put("creditTerm", mfCusCreditAdjustApply.getAdjCreditTerm());
            resMap.put("creditTermShow", mfCusCreditAdjustApply.getAdjCreditTermShow());
            resMap.put("creditAppNo", mfCusCreditAdjustApply.getCreditAppNo());
            resMap.put("creditSts", mfCusCreditAdjustApply.getCreditSts());
            resMap.put("remark", mfCusCreditAdjustApply.getRemark());
            resMap.put("isCeilingLoop", mfCusCreditAdjustApply.getAdjIsCeilingLoop());
            resMap.put("beginDate", mfCusCreditAdjustApply.getAdjBeginDate());
            resMap.put("endDate", mfCusCreditAdjustApply.getAdjEndDate());
            resMap.put("creditModel", mfCusCreditAdjustApply.getCreditModel());
            resMap.put("opNo", mfCusCreditAdjustApply.getOpNo());
            resMap.put("opName", mfCusCreditAdjustApply.getOpName());
            resMap.put("creditAppId", mfCusCreditAdjustApply.getAdjustAppId());
            resMap.put("busStage", mfCusCreditAdjustApply.getBusStage());
            resMap.put("manageOpName", mfCusCreditAdjustApply.getManageOpName());
            getObjValue(formcusCreditApplyDetail, resMap);
            cusNo = mfCusCreditAdjustApply.getCusNo();
        }
        JsonFormUtil jsonFormUtil = new JsonFormUtil();

        request.setAttribute("ifBizManger", "3");
        query = mfCusCustomerFeign.validateCreditCusFormModify(cusNo,creditAppId,BizPubParm.FORM_EDIT_FLAG_BUS,User.getRegNo(request),"1");
        if(query==null){
            query="query";
        }
        String htmlStr = jsonFormUtil.getJsonStr(formcusCreditApplyDetail, "propertySeeTag", StringUtil.KillNull(query));
        dataMap.put("cusCreditApplyDetail", htmlStr);
        dataMap.put("query", query);
        return dataMap;
    }

    /**
     * 方法描述： 授信新增表单
     * @param model
     * @return
     * @throws Exception
     * String
     * @author YuShuai
     * @date 2018年7月9日 下午7:52:16
     */
    @RequestMapping(value="creditInitInput")
    public  String creditInitInput(Model model,String showType,String cusNo,String creditAppId,String creditModel) throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        String  formId = "initCreditApplyBase";
        FormData formcreditapply0001 = formService.getFormData(formId);
        //获取授信流程配置
        JSONObject json = new JSONObject();
        MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
        if(StringUtil.isNotEmpty(cusNo)){
            formId = "initCreditAdjustBase";
            formcreditapply0001 = formService.getFormData(formId);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
            this.changeFormProperty(formcreditapply0001, "cusNo", "initValue", mfCusCustomer.getCusNo());
            this.changeFormProperty(formcreditapply0001, "cusName", "initValue", mfCusCustomer.getCusName());
            this.changeFormProperty(formcreditapply0001, "creditAppId", "initValue", creditAppId);
            model.addAttribute("creditAppId", creditAppId);
            model.addAttribute("cusNo", cusNo);
            mfCusCreditConfig.setCusNo(cusNo);
            mfCusCreditConfig.setCreditAppId(creditAppId);
            mfCusCreditConfig.setCreditModel(creditModel);
            Map<String,Object> resultMap = mfCusCreditConfigFeign.getListByCusNo(mfCusCreditConfig);
            json.put("templateCredit", resultMap.get("creditFlowMap"));
        }else{
            List<MfCusCreditConfig> mfCusCreditConfigs = mfCusCreditConfigFeign.getCreditConfigList(mfCusCreditConfig);
            JSONArray resultMap = new JSONArray();// 获取授信产品配置
            for (int i = 0; i < mfCusCreditConfigs.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("id", mfCusCreditConfigs.get(i).getCreditId());
                obj.put("name",  mfCusCreditConfigs.get(i).getCreditName());
                resultMap.add(obj);
            }
            json.put("templateCredit", resultMap);
        }
        if(showType == null || "".equals(showType)){
            showType = "2";
        }
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("showType", showType);
        model.addAttribute("query", "");
        model.addAttribute("initFlag", BizPubParm.YES_NO_Y);
        return "/component/auth/MfCusCreditApply_input";
    }

    /**
     * 立项授信申请
     *
     * @param model
     * @param showType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "projectCreditInitInput")
    public String projectCreditInitInput(Model model, String showType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId = "projectCreditApplyBase";
        FormData formcreditapply0001 = formService.getFormData(formId);
        if (showType == null || "".equals(showType)) {
            showType = "2";
        }
        String CREDIT_END_DATE_REDUCE = new CodeUtils().getSingleValByKey("CREDIT_END_DATE_REDUCE");// 授信结束日自动减一天
        if (StringUtil.isNotEmpty(CREDIT_END_DATE_REDUCE)) {
            model.addAttribute("CREDIT_END_DATE_REDUCE", CREDIT_END_DATE_REDUCE);
        }
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("showType", showType);
        model.addAttribute("query", "");
        model.addAttribute("creditModel", "2");
        return "/component/auth/MfCusCreditApply_projectInput";
    }

    /**
     * @param cusNo       客户号
     * @param adjustAppId 授信调整申请号
     * @param creditType  授信类型: 1-新增授信, 2-调整授信, 3-续签
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changeForm")
    @ResponseBody
    public Map <String, Object> changeForm(String cusNo, String adjustAppId, String creditType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);

        Map <String, Object> resultMap = new HashMap <String, Object>();
        try {
            String kindNo = "";
            if ("2".equals(creditType)) {
                kindNo = "creditAdjust1";
            } else if ("3".equals(creditType)) {
                kindNo = "creditRenew1";
            }else {
            }

            String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.credit_apply, null, null, User.getRegNo(request));
            FormData formcreditapply0001 = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))

            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
            mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
            mfCusCreditApply.setCusNo(cusNo);
            mfCusCreditApply.setCreditModel("1");

            //获得打开授信页面所需要的参数
            Map <String, Object> dataMap = mfCusCreditApplyFeign.initCusCreditedInput(mfCusCreditApply);

            getObjValue(formcreditapply0001, dataMap.get("mfCusCreditContract"));
            this.changeFormProperty(formcreditapply0001, "adjustAppId", "initValue", adjustAppId);
            this.changeFormProperty(formcreditapply0001, "creditType", "initValue", creditType);

            String htmlStr = new JsonFormUtil().getJsonStr(formcreditapply0001, "bootstarpTag", "");

            resultMap.put("flag", "success");
            resultMap.put("htmlStr", htmlStr);
        } catch (Exception e) {
            resultMap.put("flag", "error");
            throw e;
        }
        return resultMap;
    }

    @RequestMapping(value = "/insertTransferAjax")
    @ResponseBody
    public Map <String, Object> insertTransferAjax(String ajaxData, String kindNos, String kindNames, String creditAmts, String baseTypes, String baseType, String amountLands) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        String creditModel = request.getParameter("creditModel");
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditapply0001, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditapply0001)) {
                setObjValue(formcreditapply0001, mfCusCreditApply);
                //客户信息
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusCreditApply.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                mfCusCreditApply.setCusType(mfCusCustomer.getCusType());
                //通过客户类型查询匹配的授信模型
                MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
                mfCusCreditModel.setCusTypeNo(mfCusCreditApply.getCusType());
                mfCusCreditModel = mfCusCreditModelFeign.getByCusTypeNo(mfCusCreditModel);
                if (mfCusCreditModel != null) {
                    mfCusCreditApply.setModelId(mfCusCreditModel.getModelId());
                }
                mfCusCreditApply.setBaseType(baseType);
                dataMap.put("kindNos", kindNos);
                dataMap.put("kindNames", kindNames);
                dataMap.put("creditAmts", creditAmts);
                dataMap.put("baseTypes", baseTypes);
                dataMap.put("amountLands", amountLands);
                new FeignSpringFormEncoder().addParamsToBaseDomain(mfCusCreditApply);
                mfCusCreditApply.setCreditModel(creditModel);
                dataMap.put("mfCusCreditApply", mfCusCreditApply);
                //插入授信信息
                mfCusCreditApply = mfCusCreditApplyFeign.insertStartProcess(dataMap);
                //授信--产品信息
                //getMfCusPorductCreditDatas(mfCusCreditApply,request);
                dataMap = new HashMap <String, Object>();
                dataMap.put("creditAppId", mfCusCreditApply.getCreditAppId());
                dataMap.put("wkfAppId", mfCusCreditApply.getWkfAppId());
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //新增授信产品异常时，需要删除授信信息
            mfCusCreditApplyFeign.delete(mfCusCreditApply);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    @RequestMapping(value = "/transferCreditCheck")
    @ResponseBody
    public Map <String, Object> transferCreditCheck(String oldCreditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> resultMap = new HashMap <String, Object>();
        boolean flag = false;
        try {
            Double currBj = 0.00;//应还本金
            Double factBj = 0.00;//实还本金
            Double currFee = 0.00;//应还担保费
            Double factFee = 0.00;//实还担保费
            Double currLx = 0.00;//应还利息
            Double factLx = 0.00;//实还利息
            String msg = "";
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setCreditPactId(oldCreditAppId);
            ;
            List <MfBusApply> mfBusApplyList = mfBusApplyFeign.getMfBusApplyByCreditPactId(mfBusApply);
            if (mfBusApplyList != null && mfBusApplyList.size() > 0) {
                int mfBusRecourseConfirmCount = 0;//发生过代偿业务的合同笔数
                //判断授信业务所关联的所有合同是否都发生过代偿,并累计出应还本金
                for (MfBusApply query : mfBusApplyList) {
                    MfBusPact mfBusPact = new MfBusPact();
                    mfBusPact.setPactId(query.getPactId());
                    mfBusPact = mfBusPactFeign.getById(mfBusPact);

                    MfBusCompensatoryConfirm mfBusCompensatoryConfirm = new MfBusCompensatoryConfirm();
                    mfBusCompensatoryConfirm.setPactId(mfBusPact.getPactId());
                    List <MfBusCompensatoryConfirm> mfBusCompensatoryConfirmList = mfBusCompensatoryConfirmFeign.getByPactId(mfBusCompensatoryConfirm);

					/*MfBusRecourseConfirm mfBusRecourseConfirm = new MfBusRecourseConfirm();
					mfBusRecourseConfirm.setPactId(mfBusPact.getPactId());
					List<MfBusRecourseConfirm> mfBusRecourseConfirmList = mfBusRecourseConfirmFeign.getMfBusRecourseConfirmList(mfBusRecourseConfirm);*/

                    if (mfBusCompensatoryConfirmList != null && mfBusCompensatoryConfirmList.size() > 0) {
                        mfBusRecourseConfirmCount++;
                    }
                    currBj = MathExtend.add(currBj, mfBusPact.getPactAmt());
                }

                if (mfBusApplyList.size() == mfBusRecourseConfirmCount) {
                    //累计实还担保费
                    for (MfBusApply query : mfBusApplyList) {
                        MfRepayHistory mfRepayHistory = new MfRepayHistory();
                        mfRepayHistory.setPactId(query.getPactId());
                        List <MfRepayHistory> mfRepayHistoryList = mfRepayHistoryFeign.getList(mfRepayHistory);
                        for (MfRepayHistory mfRepayHistoryQuery : mfRepayHistoryList) {
                            //factBj = MathExtend.add(factBj, mfRepayHistoryQuery.getPrcpSum());
                            //factLx = MathExtend.add(factLx,MathExtend.add(MathExtend.add(MathExtend.add(MathExtend.add(mfRepayHistoryQuery.getNormIntstSum(),mfRepayHistoryQuery.getOverIntstSum()),mfRepayHistoryQuery.getCmpdIntstSum()),mfRepayHistoryQuery.getCmpdIntstPartSum()==null?0:mfRepayHistoryQuery.getCmpdIntstPartSum()),mfRepayHistoryQuery.getOverIntstPartSum()==null?0:mfRepayHistoryQuery.getOverIntstPartSum()));
                            factFee = MathExtend.add(factFee, mfRepayHistoryQuery.getFeeSum());
                        }
                    }
                    //累计实还本金、实还利息
                    for (MfBusApply query : mfBusApplyList) {
                        MfFundChannelRepayHistory mfFundChannelRepayHistory = new MfFundChannelRepayHistory();
                        mfFundChannelRepayHistory.setPactId(query.getPactId());
                        List <MfFundChannelRepayHistory> mfFundChannelRepayHistoryList = mfFundChannelRepayHistoryFeign.getMfFundChannelRepayHistoryList(mfFundChannelRepayHistory);
                        for (MfFundChannelRepayHistory mfFundChannelRepayHistoryQuery : mfFundChannelRepayHistoryList) {
                            factBj = MathExtend.add(factBj, mfFundChannelRepayHistoryQuery.getPrcpSum());
                            factLx = MathExtend.add(factLx, mfFundChannelRepayHistoryQuery.getNormIntstSum());
                        }
                    }
                    //累计应收利息
                    for (MfBusApply query : mfBusApplyList) {
                        MfFundChannelRepayPlan mfFundChannelRepayPlan = new MfFundChannelRepayPlan();
                        mfFundChannelRepayPlan.setPactId(query.getPactId());
                        List <MfFundChannelRepayPlan> mfFundChannelRepayPlanList = mfFundChannelRepayPlanFeign.getMfFundChannelRepayPlanList(mfFundChannelRepayPlan);
                        for (MfFundChannelRepayPlan mfFundChannelRepayPlanQuery : mfFundChannelRepayPlanList) {
                            currLx = MathExtend.add(currLx, mfFundChannelRepayPlanQuery.getRepayIntst());
                        }
                    }
                    //累计应收担保费
                    for (MfBusApply query : mfBusApplyList) {
                        MfBusFeePlan mfBusFeePlan = new MfBusFeePlan();
                        mfBusFeePlan.setPactId(query.getPactId());
                        List <MfBusFeePlan> mfBusFeePlanList = mfBusFeePlanFeign.getFeePlanList(mfBusFeePlan);
                        for (MfBusFeePlan mfBusFeePlanQuery : mfBusFeePlanList) {
                            currFee = MathExtend.add(currFee, mfBusFeePlanQuery.getFeeAmt1());
                        }
                    }

                    if (MathExtend.subtract(currFee, factFee) == 0 && MathExtend.subtract(currLx, factLx) == 0 && MathExtend.subtract(MathExtend.multiply(currBj, 0.10), factBj) <= 0) {
                        flag = true;
                    } else {
                        flag = false;
                        if (MathExtend.subtract(currLx, factLx) != 0) {
                            msg = "".equals(msg) ? msg + "利息未还" : msg + ",利息未还";
                        }
                        if (MathExtend.subtract(currFee, factFee) != 0) {
                            msg = "".equals(msg) ? msg + "担保费未还" : msg + ",担保费未还";
                        }
                        if (MathExtend.subtract(MathExtend.multiply(currBj, 0.10), factBj) > 0) {
                            msg = "".equals(msg) ? msg + "本金至少归还10%" : msg + ",本金至少归还10%";
                        }
                    }
                } else {
                    flag = false;
                    msg = "缺少代偿记录,不允许转贷";
                }
            } else {
                flag = false;
            }

            if (flag == true) {
                resultMap.put("flag", "success");
            } else {
                resultMap.put("flag", "error");
                resultMap.put("msg", "".equals(msg) ? "该笔业务不满足转贷条件" : "该笔业务不满足转贷条件：" + msg);
            }
        } catch (Exception e) {
            resultMap.put("flag", "error");
            resultMap.put("msg", "交易异常");
            throw e;
        }
        return resultMap;

    }

    @RequestMapping(value = "transferCreditInitInput")
    public String transferCreditInitInput(Model model, String oldCreditAppId, String oldCreditSum, String oldCreditTerm, String cusName, String oldBeginDate, String oldEndDate, String cusType, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        String formId = "initTransferCreditApplyBase";
        FormData formcreditapply0001 = formService.getFormData(formId);

        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setCreditAppId(oldCreditAppId);
        List <MfCusCreditContract> mfCusCreditContractList = mfCusCreditContractFeign.getMfCusCreditContractList(mfCusCreditContract);
        String oldPactNo = mfCusCreditContractList.get(0).getPactNo();

        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

        MfCusType mfCusType = new MfCusType();
        mfCusType.setTypeNo(mfCusCustomer.getCusType());
        mfCusType = mfCusTypeFeign.getById(mfCusType);
        String baseType = mfCusType.getBaseType();
        String creditAppId = WaterIdUtil.getWaterId("SX");

        this.changeFormProperty(formcreditapply0001, "oldCreditAppId", "initValue", oldCreditAppId);
        this.changeFormProperty(formcreditapply0001, "oldPactNo", "initValue", oldPactNo);
        this.changeFormProperty(formcreditapply0001, "oldCreditSum", "initValue", oldCreditSum);
        this.changeFormProperty(formcreditapply0001, "oldCreditTerm", "initValue", oldCreditTerm);
        this.changeFormProperty(formcreditapply0001, "cusName", "initValue", cusName);
        this.changeFormProperty(formcreditapply0001, "oldBeginDate", "initValue", oldBeginDate.substring(0, 4) + "-" + oldBeginDate.substring(4, 6) + "-" + oldBeginDate.substring(6, 8));
        this.changeFormProperty(formcreditapply0001, "oldEndDate", "initValue", oldEndDate.substring(0, 4) + "-" + oldEndDate.substring(4, 6) + "-" + oldEndDate.substring(6, 8));
        this.changeFormProperty(formcreditapply0001, "cusType", "initValue", cusType);
        this.changeFormProperty(formcreditapply0001, "cusNo", "initValue", cusNo);
        this.changeFormProperty(formcreditapply0001, "creditAppId", "initValue", creditAppId);
        this.changeFormProperty(formcreditapply0001, "creditType", "initValue", "9");

        String creditAddFlag = new CodeUtils().getSingleValByKey("CUS_CREDIT_ADD_PRO");//是否是否允许单独新增产品  1:是   0：否

        String creditFlag = BizPubParm.CREDIT_FLAG_NO;
        String termFlag = BizPubParm.TERM_FLAG_NO;

        String scNo = request.getParameter("nodeNo");

        JSONObject json = new JSONObject();
        List <MfSysKind> mfSysKinds = new ArrayList <MfSysKind>();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setUseFlag("1");
        mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
        mfSysKinds = mfCusCreditApplyFeign.getApplyKindList(mfSysKinds, cusNo);
        JSONArray array = JSONArray.fromObject(mfSysKinds);
        json.put("mfSysKinds", array);

        request.setAttribute("json", json);
        request.setAttribute("termFlag", termFlag);
        request.setAttribute("creditFlag", creditFlag);

        model.addAttribute("scNo", scNo);
        model.addAttribute("creditAddFlag", creditAddFlag);

        model.addAttribute("creditAppId", creditAppId);
        model.addAttribute("cusType", mfCusCustomer.getCusType());
        model.addAttribute("baseType", baseType);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("creditModel", BizPubParm.CREDIT_MODEL_CUS);
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCreditApply_transferInput";
    }

    /**
     * 额度测算
     *
     * @param creditAppId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditlimitAmtCountAjax")
    @ResponseBody
    public Map <String, Object> creditlimitAmtCountAjax(String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        Map <String, Object> parmMap = new HashMap <String, Object>();
        String kindNo = request.getParameter("kindNo");
        String amountLand = request.getParameter("amountLand");
        parmMap.put("kindNo", kindNo);
        parmMap.put("amountLand", amountLand);
        // 获取授信产品信息
        dataMap = mfCusCreditApplyFeign.creditlimitAmtCount(parmMap);
        // 获取产品信息
        MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
        dataMap.put("creditAmt", dataMap.get("result").toString());
        dataMap.put("maxKindAmt", mfSysKind.getMaxAmt().toString());
        dataMap.put("flag", "success");
        return dataMap;
    }

    /**
     * 方法描述： 获取立项尽调报告调查历史
     *
     * @param
     * @return
     * @throws Exception String
     * @author ywh
     * @date 2019-14-23 上午10:41:21
     */
    @RequestMapping(value = "/getInvestHistoryInit")
    @ResponseBody
    public Map <String, Object> getInvestHistoryInit(String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        List <Map <String, Object>> investHistoryList = mfCusCreditApplyFeign.getInvestHistoryInit(creditAppId);
        String tableHtml = "";
        if (investHistoryList.size() > 0) {
            JsonTableUtil jtu = new JsonTableUtil();
            tableHtml = jtu.getJsonStr("tableinvesthistory", "tableTag", investHistoryList, null, true);
        }
        dataMap.put("htmlStr", tableHtml);
        return dataMap;
    }

    /**
     * 打开其他类型尽调报告页面
     *
     * @author LJW
     * date 2017-2-28
     */
    @RequestMapping(value = "/getInvestHistory")
    public String getInvestHistory(Model model, String creditAppId, String investType) throws Exception {
        FormService formService = new FormService();
        String formId = null;
        ActionContext.initialize(request, response);
        String wkfAppId = request.getParameter("wkfAppId");
        Map <String, Object> dataMap = new HashMap <String, Object>();
        if (StringUtil.isNotEmpty(creditAppId)) {
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
            wkfAppId = mfCusCreditApply.getWkfAppId();
        }
        dataMap.put("creditAppId", creditAppId);
        dataMap.put("wkfAppId", wkfAppId);
        //获得尽调报告数据
        dataMap = mfCusCreditApplyFeign.getCreditReportDataMap(dataMap);
        JSONObject json = new JSONObject();
        json.put("mfCusPorductCreditList", JSONArray.fromObject(dataMap.get("mfCusPorductCreditList")));
        json.put("mfSysKinds", JSONArray.fromObject(dataMap.get("mfSysKinds")));
        String creditModel = String.valueOf(dataMap.get("creditModel"));//授信模式1客户授信2项目授信
        String creditType = String.valueOf(dataMap.get("creditType"));
        //获取尽调报告类型配置的字段
        String nodeNo = "";
        if ("1".equals(investType)) {
            nodeNo = "risk_investigation";
        } else if ("2".equals(investType)) {
            nodeNo = "legal_investigation";
        } else if ("3".equals(investType)) {
            nodeNo = "data_investigation";
        } else if ("4".equals(investType)) {
            nodeNo = "association_investigation";
        } else if ("5".equals(investType)) {
            nodeNo = "report";
        }else {
        }
        //获取当前调查下的分组
        List <Map <String, Object>> groupFiled = new ArrayList <Map <String, Object>>();
        model.addAttribute("groupFiled", JSONArray.fromObject(groupFiled));
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(dataMap.get("creditType"))) {
            MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("creditAdjust" + creditModel, nodeNo);
            if (mfKindForm != null) {
                formId = mfKindForm.getAddModel();
            }
        } else if (BizPubParm.CREDIT_TYPE_ADD.equals(dataMap.get("creditType"))) {
            MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("credit" + creditModel, nodeNo);
            if (mfKindForm != null) {
                formId = mfKindForm.getAddModel();
            }
        } else if (BizPubParm.CREDIT_TYPE_RENEW.equals(dataMap.get("creditType"))) {
            MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm("creditRenew" + creditModel, nodeNo);
            if (mfKindForm != null) {
                formId = mfKindForm.getAddModel();
            }
        }else {
        }

        FormData formcreditapply0001 = formService.getFormData(formId);
        getFormValue(formcreditapply0001);
        String adjustAppId = null;
        if (BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType) || BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            if (dataMap.get("mfCusCreditContract") != null) {
                MfCusCreditContract mfCusCreditContract = JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditContract"), MfCusCreditContract.class);
                getObjValue(formcreditapply0001, mfCusCreditContract);
            } else {
                getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
            }
            MfCusCreditAdjustApply mfCusCreditAdjustApply = (MfCusCreditAdjustApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCreditAdjustApply"), MfCusCreditAdjustApply.class);
            adjustAppId = mfCusCreditAdjustApply.getAdjustAppId();
            getObjValue(formcreditapply0001, mfCusCreditAdjustApply);

            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(creditAppId);
            mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);

            this.changeFormProperty(formcreditapply0001, "creditSum", "initValue", mfCusCreditAdjustApply.getAdjCreditSum().toString());
            this.changeFormProperty(formcreditapply0001, "creditTerm", "initValue", mfCusCreditAdjustApply.getAdjCreditTerm().toString());
            this.changeFormProperty(formcreditapply0001, "isCeilingLoop", "initValue", mfCusCreditAdjustApply.getAdjIsCeilingLoop());
            this.changeFormProperty(formcreditapply0001, "beginDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjBeginDate()));
            this.changeFormProperty(formcreditapply0001, "endDate", "initValue", DateUtil.getShowDateTime(mfCusCreditAdjustApply.getAdjEndDate()));

            creditAppId = adjustAppId;
        } else {
            getObjValue(formcreditapply0001, dataMap.get("mfCusCreditApply"));
        }

        //要件的展示方式：0块状1列表
        model.addAttribute("docShowType", "1");
        request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
        request.setAttribute("modelNo", String.valueOf(dataMap.get("modelNo")));
        request.setAttribute("cusNo", String.valueOf(dataMap.get("cusNo")));
        request.setAttribute("json", json);
        request.setAttribute("kindMap", dataMap.get("kindMap"));
        request.setAttribute("appId", dataMap.get("appId"));
        request.setAttribute("creditType", creditType);
        request.setAttribute("wkfAppId", dataMap.get("wkfAppId"));
        model.addAttribute("formcreditapply0001", formcreditapply0001);
        model.addAttribute("baseType", String.valueOf(dataMap.get("cusType")).substring(0,1));
        model.addAttribute("creditFlag", "1");
        model.addAttribute("query", "");
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("creditModel", creditModel);
        return "/component/auth/MfCusCreditApply_InvestDetail";
    }

    /**
     * 获取授信性质以及客户授信的状态
     * @param creditModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCreditConfigList")
    @ResponseBody
    public Map<String, Object> getCreditConfigList(String creditId,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
        mfCusCreditConfig.setCreditId(creditId);
        try {
            mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
            if(mfCusCreditConfig == null){
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
            }else{
                dataMap.put("flag", "success");
                dataMap.put("mfCusCreditConfig",mfCusCreditConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 额度测算
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/calcQuotaAjax")
    @ResponseBody
    public Map<String, Object> calcQuotaAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            dataMap = mfCusCreditApplyFeign.calcQuotaAjax(map);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getMfCusCreditAgenciesListAjax")
    @ResponseBody
    public Map<String, Object> getMfCusCreditAgenciesListAjax(String creditAppId,String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
        mfCusAgenciesCredit.setCreditAppId(creditAppId);
        List<MfCusAgenciesCredit> mfCusAgenciesCreditList = mfCusCreditApplyFeign.getByCreditAppId(mfCusAgenciesCredit);
        if(mfCusAgenciesCreditList != null && mfCusAgenciesCreditList.size()>0){
            dataMap.put("ifHasList", 1);
        }else{
            dataMap.put("ifHasList", 0);
        }
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfCusAgenciesCreditList, null, true);
        dataMap.put("mfCusAgenciesCreditList", mfCusAgenciesCreditList);
        dataMap.put("htmlStr", tableHtml);
        return dataMap;
    }
    @RequestMapping(value = "/getAgenciesListByCreditAppId")
    @ResponseBody
    public Map<String, Object> getAgenciesListByCreditAppId(String creditAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
        mfCusAgenciesCredit.setCreditAppId(creditAppId);
        List<MfCusAgenciesCredit> mfCusAgenciesCreditList = mfCusCreditApplyFeign.getByCreditAppId(mfCusAgenciesCredit);
        JSONArray bankArray = new JSONArray();
        for (int i = 0; i < mfCusAgenciesCreditList.size(); i++) {
            mfCusAgenciesCredit = mfCusAgenciesCreditList.get(i);
            JSONObject object = new JSONObject();
            object.put("id", mfCusAgenciesCredit.getAgenciesId());
            object.put("name",mfCusAgenciesCredit.getAgenciesName());
            object.put("bankCreditAmt",mfCusAgenciesCredit.getCreditBal());
            object.put("putoutTerm",mfCusAgenciesCredit.getPutoutTerm());
            object.put("extendTerm",mfCusAgenciesCredit.getExtendTerm());
            bankArray.add(object);
        }
        dataMap.put("items", bankArray);
        dataMap.put("flag", "success");
        return dataMap;
    }
    @RequestMapping(value = "/getBreedListByCreditAppId")
    @ResponseBody
    public Map<String, Object> getBreedListByCreditAppId(String creditAppId,String agenciesId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusBreedCredit mfBusBreedCredit = new MfBusBreedCredit();
        mfBusBreedCredit.setCreditAppId(creditAppId);
        mfBusBreedCredit.setBreedAgenciesId(agenciesId);
        List<MfBusBreedCredit> mfBusBreedCreditList = mfCusCreditApplyFeign.getBreedByCreditAppId(mfBusBreedCredit);
        Map<String, String> breedMap = new CodeUtils().getMapByKeyName("BUS_BREED");
        JSONArray bankArray = new JSONArray();
        for (int i = 0; i < mfBusBreedCreditList.size(); i++) {
            mfBusBreedCredit = mfBusBreedCreditList.get(i);
            JSONObject object = new JSONObject();
            String breedNo =mfBusBreedCredit.getBreedNo();
            String[] breeds = breedNo.split("\\|");
            for (int j = 0; j < breeds.length; j++) {
                String breed = breeds[j];
                if(StringUtil.isNotEmpty(breed)){
                    object.put("id", breed);
                    object.put("name",breedMap.get(breed));
                    object.put("breedCreditAmt",mfBusBreedCredit.getCreditBal());
                    bankArray.add(object);
                }
            }
        }
        dataMap.put("items", bankArray);
        dataMap.put("flag", "success");
        return dataMap;
    }
    @RequestMapping(value = "/getMfBusBreedListAjax")
    @ResponseBody
    public Map<String, Object> getMfBusBreedListAjax(String creditAppId,String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusBreedCredit mfBusBreedCredit = new MfBusBreedCredit();
        mfBusBreedCredit.setCreditAppId(creditAppId);
        List<MfBusBreedCredit> mfBusBreedCreditList = mfCusCreditApplyFeign.getBreedByCreditAppId(mfBusBreedCredit);
        if(mfBusBreedCreditList != null && mfBusBreedCreditList.size()>0){
            dataMap.put("ifHasBreedList", 1);
        }else{
            dataMap.put("ifHasBreedList", 0);
        }
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfBusBreedCreditList, null, true);
        dataMap.put("htmlStr", tableHtml);
        dataMap.put("mfBusBreedCreditList", mfBusBreedCreditList);
        return dataMap;
    }
    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/agenciesInput")
    @ResponseBody
    public Map<String,Object> agenciesInput()throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map dataMap = new HashMap<>();
        String formId="creditAgenciesBase";
        try {
            FormData formcreditAgenciesBase = formService.getFormData(formId);
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String formHtml = jsonFormUtil.getJsonStr(formcreditAgenciesBase, "bootstarpTag", "");
            dataMap.put("flag", "success");
            dataMap.put("formHtml", formHtml);
        }catch (Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
    /**
     * 授信时删除合作银行，提示信息
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteAgencies")
    @ResponseBody
    public Map<String,Object> deleteAgencies(String id)throws Exception{
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try {
            dataMap =  mfCusCreditApplyFeign.deleteAgencies(id);
        }catch (Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
    /**
     * 授信时删除业务品种，提示信息
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteBreed")
    @ResponseBody
    public Map<String,Object> deleteBreed(String id)throws Exception{
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try {
            dataMap =  mfCusCreditApplyFeign.deleteBreed(id);
        }catch (Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/agenciesInputView")
    public String agenciesInputView(Model model,String creditAppId)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="creditAgenciesBase";
        try {
            FormData formcreditAgenciesBase = formService.getFormData(formId);
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
            if(mfCusCreditApply!=null){
                mfCusCreditApply.setAgenciesId(null);
                mfCusCreditApply.setAgenciesName(null);
                String templateCreditId =mfCusCreditApply.getTemplateCreditId();
                MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
                mfCusCreditConfig.setCreditId(templateCreditId);
                mfCusCreditConfig =mfCusCreditConfigFeign.getById(mfCusCreditConfig);
                if(mfCusCreditConfig!=null){
                    model.addAttribute("adaptationKindNo", mfCusCreditConfig.getAdaptationKindNo());
                }
                model.addAttribute("creditSum", mfCusCreditApply.getCreditSum());
                getObjValue(formcreditAgenciesBase, mfCusCreditApply);
            }else{
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
                mfCusCreditAdjustApply =mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                if(mfCusCreditAdjustApply!=null){
                    mfCusCreditAdjustApply.setAgenciesId(null);
                    mfCusCreditAdjustApply.setAgenciesName(null);
                    getObjValue(formcreditAgenciesBase, mfCusCreditAdjustApply);
                    model.addAttribute("creditSum", mfCusCreditAdjustApply.getAdjCreditSum());
                }
            }
            model.addAttribute("formcreditAgenciesBase", formcreditAgenciesBase);
            model.addAttribute("query", "");
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfCusCreditAgenice_Insert";
    }
    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/agenciesApplyView")
    public String agenciesApplyView(Model model,String kindNo)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="applyAgenciesBase";
        try {
            FormData formcreditAgenciesBase = formService.getFormData(formId);
            model.addAttribute("adaptationKindNo", kindNo);
            model.addAttribute("formcreditAgenciesBase", formcreditAgenciesBase);
            model.addAttribute("query", "");
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfApplyAgenice_Insert";
    }
    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/breedInputView")
    public String breedInputView(Model model,String creditAppId)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map dataMap = new HashMap<>();
        String formId="creditBreedBase";
        try {
            FormData formcreditBreedBase = formService.getFormData(formId);
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
            if(mfCusCreditApply!=null){
                mfCusCreditApply.setAgenciesId(null);
                mfCusCreditApply.setAgenciesName(null);
                String templateCreditId =mfCusCreditApply.getTemplateCreditId();
                MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
                mfCusCreditConfig.setCreditId(templateCreditId);
                mfCusCreditConfig =mfCusCreditConfigFeign.getById(mfCusCreditConfig);
                if(mfCusCreditConfig!=null){
                    model.addAttribute("adaptationKindNo", mfCusCreditConfig.getAdaptationKindNo());
                }
                model.addAttribute("creditSum", mfCusCreditApply.getCreditSum());
                getObjValue(formcreditBreedBase, mfCusCreditApply);
            }else{
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
                mfCusCreditAdjustApply =mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                if(mfCusCreditAdjustApply!=null){
                    mfCusCreditAdjustApply.setAgenciesId(null);
                    mfCusCreditAdjustApply.setAgenciesName(null);
                    getObjValue(formcreditBreedBase, mfCusCreditAdjustApply);
                    model.addAttribute("creditSum", mfCusCreditAdjustApply.getAdjCreditSum());
                }
            }
            model.addAttribute("formcreditBreedBase", formcreditBreedBase);
            model.addAttribute("query", "");
            model.addAttribute("creditAppId",creditAppId);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfBusBreed_Insert";
    }
    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/agenciesDetailView")
    public String agenciesDetailView(Model model,String id)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="creditAgenciesBase";
        try {
            FormData formcreditAgenciesBase = formService.getFormData(formId);
            MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
            mfCusAgenciesCredit.setId(id);
            mfCusAgenciesCredit = mfCusCreditApplyFeign.getMfCusAgenciesCreditById(mfCusAgenciesCredit);
            mfCusAgenciesCredit.setBankCreditAmt(mfCusAgenciesCredit.getCreditAmt());
            getObjValue(formcreditAgenciesBase, mfCusAgenciesCredit);
            String creditAppId = mfCusAgenciesCredit.getCreditAppId();
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
            if(mfCusCreditApply!=null){
                mfCusCreditApply.setAgenciesId(null);
                mfCusCreditApply.setAgenciesName(null);
                String templateCreditId =mfCusCreditApply.getTemplateCreditId();
                MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
                mfCusCreditConfig.setCreditId(templateCreditId);
                mfCusCreditConfig =mfCusCreditConfigFeign.getById(mfCusCreditConfig);
                if(mfCusCreditConfig!=null){
                    model.addAttribute("adaptationKindNo", mfCusCreditConfig.getAdaptationKindNo());
                }
                model.addAttribute("creditSum", mfCusCreditApply.getCreditSum());
                getObjValue(formcreditAgenciesBase, mfCusCreditApply);
            }else{
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
                mfCusCreditAdjustApply =mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                if(mfCusCreditAdjustApply!=null){
                    mfCusCreditAdjustApply.setAgenciesId(null);
                    mfCusCreditAdjustApply.setAgenciesName(null);
                    getObjValue(formcreditAgenciesBase, mfCusCreditAdjustApply);
                    model.addAttribute("creditSum", mfCusCreditAdjustApply.getAdjCreditSum());
                }
            }
            model.addAttribute("formcreditAgenciesBase", formcreditAgenciesBase);
            model.addAttribute("query", "");
            model.addAttribute("creditAppId", creditAppId);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfCusCreditAgenice_Detail";
    }
    /**
     * 授信时添加业务品种授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/breedDetailView")
    public String breedDetailView(Model model,String id)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="creditBreedDetail";
        try {
            FormData formcreditBreedBase= formService.getFormData(formId);
            MfBusBreedCredit mfBusBreedCredit = new MfBusBreedCredit();
            mfBusBreedCredit.setId(id);
            mfBusBreedCredit = mfCusCreditApplyFeign.getMfBusBreedCreditById(mfBusBreedCredit);
            mfBusBreedCredit.setBreedCreditAmt(mfBusBreedCredit.getCreditAmt());
            MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
            mfCusAgenciesCredit.setCreditAppId(mfBusBreedCredit.getCreditAppId());
            mfCusAgenciesCredit.setAgenciesId(mfBusBreedCredit.getBreedAgenciesId());
            mfCusAgenciesCredit =  mfCusCreditApplyFeign.getMfCusAgenciesCreditById(mfCusAgenciesCredit);
            if(mfCusAgenciesCredit!=null){
                mfBusBreedCredit.setAgenciesCreditAmt(mfCusAgenciesCredit.getCreditAmt());
            }
            getObjValue(formcreditBreedBase, mfBusBreedCredit);
            String creditAppId = mfBusBreedCredit.getCreditAppId();
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
            if(mfCusCreditApply!=null){
                mfCusCreditApply.setAgenciesId(null);
                mfCusCreditApply.setAgenciesName(null);
                String templateCreditId =mfCusCreditApply.getTemplateCreditId();
                MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
                mfCusCreditConfig.setCreditId(templateCreditId);
                mfCusCreditConfig =mfCusCreditConfigFeign.getById(mfCusCreditConfig);
                if(mfCusCreditConfig!=null){
                    model.addAttribute("adaptationKindNo", mfCusCreditConfig.getAdaptationKindNo());
                }
                model.addAttribute("creditSum", mfCusCreditApply.getCreditSum());
                getObjValue(formcreditBreedBase, mfCusCreditApply);
            }else{
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
                mfCusCreditAdjustApply =mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
                if(mfCusCreditAdjustApply!=null){
                    mfCusCreditAdjustApply.setAgenciesId(null);
                    mfCusCreditAdjustApply.setAgenciesName(null);
                    getObjValue(formcreditBreedBase, mfCusCreditAdjustApply);
                    model.addAttribute("creditSum", mfCusCreditAdjustApply.getAdjCreditSum());
                }
            }
            model.addAttribute("formcreditBreedBase", formcreditBreedBase);
            model.addAttribute("query", "");
            model.addAttribute("creditAppId", creditAppId);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfBusBreed_Detail";
    }
    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/agenciesInsertAjax")
    @ResponseBody
    public  Map <String, Object> agenciesInsertAjax(String ajaxData)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditAgenciesBase = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditAgenciesBase, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditAgenciesBase)) {
                setObjValue(formcreditAgenciesBase, mfCusAgenciesCredit);
                mfCusAgenciesCredit.setOpNo(User.getRegNo(request));
                mfCusAgenciesCredit.setOpName(User.getRegName(request));
                mfCusAgenciesCredit.setCreditAmt(mfCusAgenciesCredit.getBankCreditAmt());
                mfCusAgenciesCredit.setCreditBal(mfCusAgenciesCredit.getBankCreditAmt());
                mfCusAgenciesCredit = mfCusCreditApplyFeign.agenciesInsertAjax(mfCusAgenciesCredit);
                dataMap.put("flag", "success");
                dataMap.put("msg", "插入成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     * 授信时添加业务品种授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/breedInsertAjax")
    @ResponseBody
    public  Map <String, Object> breedInsertAjax(String ajaxData)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfBusBreedCredit mfBusBreedCredit = new MfBusBreedCredit();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditAgenciesBase = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditAgenciesBase, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditAgenciesBase)) {
                setObjValue(formcreditAgenciesBase, mfBusBreedCredit);
                mfBusBreedCredit.setOpNo(User.getRegNo(request));
                mfBusBreedCredit.setOpName(User.getRegName(request));
                mfBusBreedCredit.setCreditAmt(mfBusBreedCredit.getBreedCreditAmt());
                mfBusBreedCredit.setCreditBal(mfBusBreedCredit.getBreedCreditAmt());
                mfBusBreedCredit = mfCusCreditApplyFeign.breedInsertAjax(mfBusBreedCredit);
                dataMap.put("flag", "success");
                dataMap.put("msg", "插入成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/agenciesUpdateAjax")
    @ResponseBody
    public  Map <String, Object> agenciesUpdateAjax(String ajaxData)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditAgenciesBase = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditAgenciesBase, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditAgenciesBase)) {
                setObjValue(formcreditAgenciesBase, mfCusAgenciesCredit);
                //判断合作银行下的业务品种额度
                MfBusBreedCredit mfBusBreedCredit = new MfBusBreedCredit();
                mfBusBreedCredit.setBreedAgenciesId(mfCusAgenciesCredit.getAgenciesId());
                mfBusBreedCredit.setCreditAppId(mfCusAgenciesCredit.getCreditAppId());
                List<MfBusBreedCredit> mfBusBreedCreditList = mfCusCreditApplyFeign.getBreedByCreditAppId(mfBusBreedCredit);
                if(mfBusBreedCreditList.size()>0){
                    for (int i = 0; i < mfBusBreedCreditList.size(); i++) {
                        mfBusBreedCredit = mfBusBreedCreditList.get(i);
                        if(mfBusBreedCredit.getCreditAmt()>mfCusAgenciesCredit.getBankCreditAmt()){
                            dataMap.put("flag", "error");
                            dataMap.put("msg", "该合作银行下的业务品种额度为："+mfBusBreedCredit.getCreditAmt()+"，请调整业务品种额度后再修改！");
                            return dataMap ;
                        }

                    }
                }
                mfCusAgenciesCredit.setOpNo(User.getRegNo(request));
                mfCusAgenciesCredit.setOpName(User.getRegName(request));
                mfCusAgenciesCredit.setCreditAmt(mfCusAgenciesCredit.getBankCreditAmt());
                mfCusAgenciesCredit.setCreditBal(mfCusAgenciesCredit.getBankCreditAmt());
                mfCusAgenciesCredit = mfCusCreditApplyFeign.agenciesUpdateAjax(mfCusAgenciesCredit);
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/breedUpdateAjax")
    @ResponseBody
    public  Map <String, Object> breedUpdateAjax(String ajaxData)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfBusBreedCredit mfBusBreedCredit = new MfBusBreedCredit();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditAgenciesBase = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcreditAgenciesBase, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditAgenciesBase)) {
                setObjValue(formcreditAgenciesBase, mfBusBreedCredit);
                mfBusBreedCredit.setOpNo(User.getRegNo(request));
                mfBusBreedCredit.setOpName(User.getRegName(request));
                mfBusBreedCredit.setCreditAmt(mfBusBreedCredit.getBreedCreditAmt());
                mfBusBreedCredit.setCreditBal(mfBusBreedCredit.getBreedCreditAmt());
                mfBusBreedCredit = mfCusCreditApplyFeign.breedUpdateAjax(mfBusBreedCredit);
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     * 授信时添加合作银行,银行数据初始化
     * @param cusNo
     * @param creditTemplateId
     * @return
     */
    @RequestMapping(value = "/bankInit")
    @ResponseBody
    public Map<String,Object> bankInit(String cusNo,String creditTemplateId)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JSONArray bankArray = new JSONArray();
        MfBankNameArea mfBankNameArea = new MfBankNameArea();
        mfBankNameArea.setLevel("1");
        List<MfBankNameArea> mfBankNameAreaList= mfCusCreditApplyFeign.getMfBankNameAreaList(mfBankNameArea);
        for (MfBankNameArea mfBankNameArea1 : mfBankNameAreaList) {
            JSONObject object = new JSONObject();
            object.put("id", mfBankNameArea1.getBankNo());
            object.put("name",mfBankNameArea1.getBankName());
            bankArray.add(object);
        }
        dataMap.put("items", bankArray);
        dataMap.put("flag", "success");
        return dataMap;
    }
    /**
     * 授信时添加业务品种时 合作银行数据初始化
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/breedBankInit")
    @ResponseBody
    public Map<String,Object> breedBankInit(String agenciesArr)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JSONArray agenciesArray = JSONArray.fromObject(agenciesArr);
        JSONArray bankArray = new JSONArray();
        for (int i = 0; i < agenciesArray.size(); i++) {
            JSONObject agenciesObject = (JSONObject) agenciesArray.get(i);
            JSONObject object = new JSONObject();
            object.put("id", agenciesObject.get("agenciesId"));
            object.put("name",agenciesObject.get("agenciesName"));
            object.put("bankCreditAmt",agenciesObject.get("bankCreditAmt"));
            bankArray.add(object);
        }
        dataMap.put("items", bankArray);
        dataMap.put("flag", "success");
        return dataMap;
    }
    /**
     * 授信时添加业务品种时 合作银行数据初始化
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/breedBankInitForEdit")
    @ResponseBody
    public Map<String,Object> breedBankInitForEdit(String creditAppId)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
        mfCusAgenciesCredit.setCreditAppId(creditAppId);
        List<MfCusAgenciesCredit> mfCusAgenciesCreditList = mfCusCreditApplyFeign.getByCreditAppId(mfCusAgenciesCredit);
        JSONArray bankArray = new JSONArray();
        for (int i = 0; i < mfCusAgenciesCreditList.size(); i++) {
            mfCusAgenciesCredit = mfCusAgenciesCreditList.get(i);
            JSONObject object = new JSONObject();
            object.put("id", mfCusAgenciesCredit.getAgenciesId());
            object.put("name",mfCusAgenciesCredit.getAgenciesName());
            object.put("bankCreditAmt",mfCusAgenciesCredit.getCreditAmt());
            bankArray.add(object);
        }
        dataMap.put("items", bankArray);
        dataMap.put("flag", "success");
        return dataMap;
    }
    /**
     * 授信时添加合作银行,银行地区数据初始化
     * @param bankNo
     * @param
     * @return
     */
    @RequestMapping(value = "/areaInit")
    @ResponseBody
    public Map<String,Object> areaInit(String bankNo)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JSONArray bankArray = new JSONArray();
        MfBankNameArea mfBankNameArea = new MfBankNameArea();
        mfBankNameArea.setLevel("2");
        mfBankNameArea.setUpNo(bankNo);
        List<MfBankNameArea> mfBankNameAreaList= mfCusCreditApplyFeign.getMfBankNameAreaList(mfBankNameArea);
        for (MfBankNameArea mfBankNameArea1 : mfBankNameAreaList) {
            JSONObject object = new JSONObject();
            object.put("id", mfBankNameArea1.getBankNo());
            object.put("name",mfBankNameArea1.getBankName());
            bankArray.add(object);
        }
        dataMap.put("items", bankArray);
        dataMap.put("flag", "success");
        return dataMap;
    }

    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/breedInput")
    @ResponseBody
    public Map<String,Object> breedInput(String agenciesArr)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map dataMap = new HashMap<>();
        String formId="creditBreedBase";
        try {
            FormData formcreditAgenciesBase = formService.getFormData(formId);
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String formHtml = jsonFormUtil.getJsonStr(formcreditAgenciesBase, "bootstarpTag", "");
            dataMap.put("flag", "success");
            dataMap.put("formHtml", formHtml);
            dataMap.put("agenciesArr", agenciesArr);
        }catch (Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 授信时添加业务品种数据初始化
     * @param kindNo 产品编号
     * @param breedNoArr 已选业务品质
     * @return
     */
    @RequestMapping(value = "/breedInit")
    @ResponseBody
    public Map<String,Object> breedInit(String kindNo,String breedNoArr,String agenciesId)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> breedMap = new CodeUtils().getMapByKeyName("BUS_BREED");
        JSONArray breedArray = new JSONArray();
        JSONArray breedArrayNew = new JSONArray();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setKindNo(kindNo);
        mfSysKind = mfSysKindFeign.getById(mfSysKind);
        if (mfSysKind!=null&&StringUtil.isNotEmpty(mfSysKind.getBusBreed())) {
            String busBreed = mfSysKind.getBusBreed();
            String[] breeds = busBreed.split("\\|");
            for (int i = 0; i < breeds.length; i++) {
                String breed = breeds[i];
                JSONObject object = new JSONObject();
                object.put("id",breed);
                object.put("name",breedMap.get(breed));
                breedArray.add(object);
            }
            if(StringUtil.isNotEmpty(breedNoArr)){
                JSONArray breedNoArrs = JSONArray.fromObject(breedNoArr);
                for (int i = 0; i < breedArray.size(); i++) {
                    JSONObject object = (JSONObject) breedArray.get(i);
                    String breed =object.getString("id");
                    boolean flag =true;
                    for (int j = 0; j < breedNoArrs.size(); j++) {
                        JSONObject breedId = (JSONObject) breedNoArrs.get(j);
                        String breedNo = breedId.getString("breedId");
                        String agenciesIdThis = breedId.getString("agenciesId");
                        String[] breedNos = breedNo.split("\\|");
                        for (int k = 0; k < breedNos.length; k++) {
                            String no = breedNos[k];
                            if(no.equals(breed)&&agenciesIdThis.equals(agenciesId)){
                                flag = false;
                            }
                        }
                    }
                    if(flag){
                        breedArrayNew.add(object);
                    }
                }
            }else{
                breedArrayNew.addAll(breedArray);
            }
        }
        dataMap.put("items", breedArrayNew);
        dataMap.put("flag", "success");
        return dataMap;
    }
    /**
     * 授信时添加业务品种数据初始化
     * @param kindNo 产品编号
     * @param breedNoArr 已选业务品质
     * @return
     */
    @RequestMapping(value = "/breedInitForApply")
    @ResponseBody
    public Map<String,Object> breedInitForApply(String kindNo)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> breedMap = new CodeUtils().getMapByKeyName("BUS_BREED");
        JSONArray breedArray = new JSONArray();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setKindNo(kindNo);
        mfSysKind = mfSysKindFeign.getById(mfSysKind);
        if (StringUtil.isNotEmpty(mfSysKind.getBusBreed())) {
            String busBreed = mfSysKind.getBusBreed();
            String[] breeds = busBreed.split("\\|");
            for (int i = 0; i < breeds.length; i++) {
                String breed = breeds[i];
                JSONObject object = new JSONObject();
                object.put("id",breed);
                object.put("name",breedMap.get(breed));
                breedArray.add(object);
            }
        }
        dataMap.put("items", breedArray);
        dataMap.put("flag", "success");
        return dataMap;
    }
    /**
     * 授信时添加业务品种数据初始化
     * @param kindNo 产品编号
     * @param creditAppId
     * @return
     */
    @RequestMapping(value = "/breedInitForEdit")
    @ResponseBody
    public Map<String,Object> breedInitForEdit(String kindNo,String creditAppId,String agenciesId,String id)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> breedMap = new CodeUtils().getMapByKeyName("BUS_BREED");
        JSONArray breedArray = new JSONArray();
        JSONArray breedArrayNew = new JSONArray();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setKindNo(kindNo);
        mfSysKind = mfSysKindFeign.getById(mfSysKind);
        if (StringUtil.isNotEmpty(mfSysKind.getBusBreed())) {
            String busBreed = mfSysKind.getBusBreed();
            String[] breeds = busBreed.split("\\|");
            for (int i = 0; i < breeds.length; i++) {
                String breed = breeds[i];
                JSONObject object = new JSONObject();
                object.put("id",breed);
                object.put("name",breedMap.get(breed));
                breedArray.add(object);
            }
            MfBusBreedCredit mfBusBreedCredit = new MfBusBreedCredit();
            mfBusBreedCredit.setCreditAppId(creditAppId);
            mfBusBreedCredit.setBreedAgenciesId(agenciesId);
            List<MfBusBreedCredit> mfBusBreedCreditList = mfCusCreditApplyFeign.getBreedByCreditAppId(mfBusBreedCredit);
            if(mfBusBreedCreditList.size()>0){
                for (int i = 0; i < breedArray.size(); i++) {
                    JSONObject object = (JSONObject) breedArray.get(i);
                    String breed =object.getString("id");
                    boolean flag =true;
                    for (int j = 0; j < mfBusBreedCreditList.size(); j++) {
                        mfBusBreedCredit = mfBusBreedCreditList.get(j);
                        if(mfBusBreedCredit.getId().equals(id)){

                        }else{
                            String breedNo =mfBusBreedCredit.getBreedNo();
                            String[] breedNos = breedNo.split("\\|");
                            for (int k = 0; k < breedNos.length; k++) {
                                String no = breedNos[k];
                                if(no.equals(breed)){
                                    flag = false;
                                }
                            }
                        }

                    }
                    if(flag){
                        breedArrayNew.add(object);
                    }
                }
            }else{
                breedArrayNew.addAll(breedArray);
            }
        }
        dataMap.put("items", breedArrayNew);
        dataMap.put("flag", "success");
        return dataMap;
    }
    /**
     * 授信时获取业务品种名称
     * @param breedNo 已选业务品质
     * @return
     */
    @RequestMapping(value = "/getNameByBreedNo")
    @ResponseBody
    public Map<String,Object> getNameByBreedNo(String breedNos)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> breedMap = new CodeUtils().getMapByKeyName("BUS_BREED");
        String breedName = "";
        if (StringUtil.isNotEmpty(breedNos)) {
            String[] breeds = breedNos.split("\\|");
            for (int i = 0; i < breeds.length; i++) {
                String breedNo = breeds[i];
                breedName =breedName+breedMap.get(breedNo)+"|";
            }
        }
        dataMap.put("breedName", breedName);
        dataMap.put("flag", "success");
        return dataMap;
    }

    /**
     * 授信合同生成时添加非业务生成合同
     * @param
     * @return
     */
    @RequestMapping(value = "/addPactExtend")
    public String addPactExtend(Model model,String creditAppId,String cusNo,String queryType)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="pactExtendCredit";
        if("2".equals(queryType)){
            formId = "pactExtendCredit_GCDB";
        }
        try {
            FormData formpactExtendCredit= formService.getFormData(formId);
            MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
            mfBusPactExtend.setCreditAppId(creditAppId);
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(creditAppId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
            if(mfCusCreditApply!=null){
                mfBusPactExtend.setCusNo(mfCusCreditApply.getCusNo());
                mfBusPactExtend.setCusName(mfCusCreditApply.getCusName());
                MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                mfCusCreditContract.setCreditAppId(creditAppId);
                mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
                if(mfCusCreditContract!=null){
                    mfBusPactExtend.setCreditPactId(mfCusCreditContract.getId());
                    mfBusPactExtend.setCreditPactNo(mfCusCreditContract.getPactNo());
                }
            }
            getObjValue(formpactExtendCredit, mfBusPactExtend);
            List<OptionsList> templateList = new ArrayList<OptionsList>();
            Map<String, String> templateMap = new CodeUtils().getMapByKeyName("OTHER_PACT");
            String [] templates = {"301","302","303","304","305","399","119","120"};
            for (int i = 0; i < templates.length; i++) {
                String template = templates[i];
                Map<String ,Object> resultMap = mfBusPactExtendFeign.initTemplate(creditAppId,template);
                if("0".equals(String.valueOf(resultMap.get("ifInit")))){
                    OptionsList op= new OptionsList();
                    op.setOptionLabel(templateMap.get(template));
                    op.setOptionValue(template);
                    op.setOptionId(WaterIdUtil.getWaterId());
                    templateList.add(op);
                }
            }
            this.changeFormProperty(formpactExtendCredit, "templateId", "optionArray", templateList);
            model.addAttribute("formpactExtendCredit", formpactExtendCredit);
            model.addAttribute("templateList", templateList);
            model.addAttribute("query", "");
            model.addAttribute("creditAppId", creditAppId);
            model.addAttribute("queryType", queryType);
            model.addAttribute("id", WaterIdUtil.getWaterId());
            model.addAttribute("cusNo", cusNo);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfBusPactExtendCredit_Insert";
    }
    /**
     * 授信合同生成时添加非业务生成合同
     * @param
     * @return
     */
    @RequestMapping(value = "/pactExtendDetail")
    public String pactExtendDetail(Model model,String creditAppId,String id)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="pactExtendCreditDetail";
        try {
            FormData formpactExtendCreditDetail= formService.getFormData(formId);
            MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
            mfBusPactExtend.setId(id);
            mfBusPactExtend = mfBusPactExtendFeign.getById(mfBusPactExtend);
            getObjValue(formpactExtendCreditDetail, mfBusPactExtend);
            model.addAttribute("formpactExtendCreditDetail", formpactExtendCreditDetail);
            model.addAttribute("query", "");
            model.addAttribute("creditAppId", creditAppId);
            model.addAttribute("id", id);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfBusPactExtendCredit_Detail";
    }
    /**
     * 授信合同生成时添加非业务生成合同
     * @param
     * @return
     */
    @RequestMapping(value = "/pactExtendView")
    public String pactExtendView(Model model,String creditAppId,String id)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="pactExtendCreditDetail";
        try {
            FormData formpactExtendCreditDetail= formService.getFormData(formId);
            MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
            mfBusPactExtend.setId(id);
            mfBusPactExtend = mfBusPactExtendFeign.getById(mfBusPactExtend);
            getObjValue(formpactExtendCreditDetail, mfBusPactExtend);
            model.addAttribute("formpactExtendCreditDetail", formpactExtendCreditDetail);
            model.addAttribute("query", "");
            model.addAttribute("creditAppId", creditAppId);
            model.addAttribute("id", id);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/auth/MfBusPactExtendCredit_view";
    }
    /**
     * 授信合同生成时添加非业务生成合同初始化
     * @param bankNo
     * @param
     * @return
     */
    @RequestMapping(value = "/pactExtendInit")
    @ResponseBody
    public Map<String,Object> pactExtendInit(String creditAppId)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JSONArray templateArray = new JSONArray();
        Map<String, String> templateMap = new CodeUtils().getMapByKeyName("OTHER_PACT");
        String [] templates = {"301","302","303","304","305","399","219","220"};
        for (int i = 0; i < templates.length; i++) {
            String template = templates[i];
            Map<String ,Object> resultMap = mfBusPactExtendFeign.initTemplate(creditAppId,template);
            if("0".equals(String.valueOf(resultMap.get("ifInit")))){
                JSONObject object = new JSONObject();
                object.put("id",template);
                object.put("name",templateMap.get(template));
                templateArray.add(object);
            }
        }
        dataMap.put("items", templateArray);
        dataMap.put("flag", "success");
        return dataMap;
    }
    /**
     * 授信合同生成时添加非业务生成合同,合作银行初始化
     * @param bankNo
     * @param
     * @return
     */
    @RequestMapping(value = "/pactAgenceisInit")
    @ResponseBody
    public Map<String,Object> pactAgenceisInit(String creditAppId,String templateId,String cusNo)throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JSONArray agenciesArray = new JSONArray();
        MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
        mfCusAgenciesCredit.setCreditAppId(creditAppId);
        List<MfCusAgenciesCredit> mfCusAgenciesCreditList= mfCusCreditApplyFeign.getByCreditAppId(mfCusAgenciesCredit);
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        mfBusPactExtend.setCreditAppId(creditAppId);
        mfBusPactExtend.setTemplateId(templateId);
        List<MfBusPactExtend> mfBusPactExtendList = mfBusPactExtendFeign.getAllListForCredit(mfBusPactExtend);
        for (int i = 0; i < mfCusAgenciesCreditList.size(); i++) {
            boolean flag = true;
            mfCusAgenciesCredit = mfCusAgenciesCreditList.get(i);
            for (int j = 0; j < mfBusPactExtendList.size(); j++) {
                if(mfCusAgenciesCredit.getAgenciesId().equals(mfBusPactExtendList.get(j).getAgenciesId())){
                    flag = false;
                }
            }
            if(flag){
                JSONObject object = new JSONObject();
                object.put("id",mfCusAgenciesCredit.getAgenciesId());
                object.put("name",mfCusAgenciesCredit.getAgenciesName());
                agenciesArray.add(object);
            }
        }
        dataMap.put("items", agenciesArray);
        Map<String, String> templateMap = new CodeUtils().getMapByKeyName("OTHER_PACT");
        dataMap.put("pactName", templateMap.get(templateId).replaceAll("抵押用-",""));
        if("301".equals(templateId)){
            if(StringUtil.isNotEmpty(cusNo)){
                MfCusCorpBaseInfo mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
                if(mfCusCorpBaseInfo!=null){
                    dataMap.put("address", mfCusCorpBaseInfo.getCommAddress());
                    dataMap.put("legalRepresName", mfCusCorpBaseInfo.getLegalRepresName());
                    dataMap.put("client", mfCusCorpBaseInfo.getCusName());
                }
            }
        }else  if ("120".equals(templateId)){
            if(StringUtil.isNotEmpty(cusNo)){
                MfCusCorpBaseInfo mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
                if(mfCusCorpBaseInfo!=null){
                    dataMap.put("client", mfCusCorpBaseInfo.getCusName());
                }
            }
        }
        dataMap.put("flag", "success");
        return dataMap;
    }
    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/insertExtendAjax")
    @ResponseBody
    public  Map <String, Object> insertExtendAjax(String ajaxData)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formmfBusPactExtend = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formmfBusPactExtend, getMapByJson(ajaxData));
            if (this.validateFormDataAnchor(formmfBusPactExtend)) {
                setObjValue(formmfBusPactExtend, mfBusPactExtend);
                Map<String, String> templateMap = new CodeUtils().getMapByKeyName("OTHER_PACT");
                mfBusPactExtend.setTemplateName(templateMap.get(mfBusPactExtend.getTemplateId()));
                mfBusPactExtend.setIsMust("0");
                mfBusPactExtendFeign.insertAjax(mfBusPactExtend);
                dataMap.put("flag", "success");
                dataMap.put("msg", "新增成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     * 授信时添加合作银行授信额度
     * @param
     * @return
     */
    @RequestMapping(value = "/updateExtendAjax")
    @ResponseBody
    public  Map <String, Object> updateExtendAjax(String ajaxData)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formmfBusPactExtend = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formmfBusPactExtend, getMapByJson(ajaxData));
            if (this.validateFormDataAnchor(formmfBusPactExtend)) {
                setObjValue(formmfBusPactExtend, mfBusPactExtend);
                mfBusPactExtendFeign.updateExtendAjax(mfBusPactExtend);
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    @RequestMapping(value = "/deleteExtendAjax")
    @ResponseBody
    public  Map <String, Object> deleteExtendAjax(String id)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        mfBusPactExtend.setId(id);
        try {
            mfBusPactExtendFeign.deleteExtendAjax(mfBusPactExtend);
            dataMap.put("flag", "success");
            dataMap.put("msg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getExendListHtmlAjax")
    @ResponseBody
    public Map<String, Object> getExendListHtmlAjax(String creditAppId,String ajaxData,
                                                    String tableId,String queryType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        try {
            mfBusPactExtend.setCreditAppId(creditAppId);
            if("2".equals(queryType)){
                mfBusPactExtend.setStampPactId("1");
            }
            List<MfBusPactExtend> mfBusPactExtendList = mfBusPactExtendFeign.getAllListForCredit(mfBusPactExtend);
            if(mfBusPactExtendList.size()>0){
                dataMap.put("ifHasList", "1");
            }else{
                dataMap.put("ifHasList", "0");
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfBusPactExtendList, null, true);
            dataMap.put("tableData", tableHtml);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 获取当事人
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getClientAjax")
    @ResponseBody
    public Map<String, Object> getClientAjax(String creditAppId,String templateId,String agenciesId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        try {
            mfBusPactExtend.setCreditAppId(creditAppId);
            dataMap.put("client", mfBusPactExtendFeign.getClientAjax(creditAppId,templateId,agenciesId));
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
        }
        return dataMap;
    }
}
