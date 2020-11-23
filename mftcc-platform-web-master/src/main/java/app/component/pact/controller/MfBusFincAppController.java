 package app.component.pact.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.entity.MfBusApplyAssureInfo;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.core.entity.MfRepayAmt;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.feign.MfRepayPlanFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.penalty.entity.MfBusAppPenaltyMain;
import app.component.calc.penalty.feign.MfBusAppPenaltyMainFeign;
import app.component.calccoreinterface.CalcRepayPlanInterfaceFeign;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.channel.fund.entity.MfFundChannelRepayPlan;
import app.component.collateral.entity.InsInfo;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.collateral.feign.InsInfoFeign;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.compensatory.entity.MfBusCompensatoryConfirm;
import app.component.compensatory.feign.MfBusCompensatoryConfirmFeign;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusBankAccManageFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.whitename.feign.MfCusWhitenameInterfaceFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.examine.entity.MfExaminePact;
import app.component.examine.feign.MfExamineHisFeign;
import app.component.finance.bankacc.feign.CwCusBankAccManageFeign;
import app.component.msgconf.entity.CuslendWarning;
import app.component.msgconfinterface.MsgConfInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.advanceloan.entity.MfBusAdvanceLoan;
import app.component.pact.advanceloan.feign.MfBusAdvanceLoanFeign;
import app.component.pact.entity.*;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusFincAppChildFeign;
import app.component.pact.feign.MfBusFincAppAccountRecordFeign;
import app.component.pact.fiveclass.feign.MfPactFiveclassFeign;
import app.component.pact.repay.entity.MfFincRepayDetail;
import app.component.pact.repay.feign.MfFincRepayDetailFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.stamp.entity.MfStampBaseInfo;
import app.component.stamp.entity.MfStampPact;
import app.component.stamp.feign.MfStampPactFeign;
import app.component.sys.feign.SysTaskInfoFeign;
import app.component.thirdservice.esign.feign.EsignInterfaceFeign;
import app.component.vouafter.entity.MfVouAfterTrack;
import app.component.vouafter.feign.MfVouAfterTrackFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.DataUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import com.alibaba.fastjson.JSON;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.report.ExpExclUtil;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Title: MfBusFincAppAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Tue May 31 18:10:07 CST 2016
 **/
@Controller
@RequestMapping("/mfBusFincApp")
public class MfBusFincAppController extends BaseFormBean {
    private final Logger logger = LoggerFactory.getLogger(MfBusFincAppController.class);
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    // 注入MfBusFincAppBo
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;
    @Autowired
    private MfBusAppKindFeign mfBusAppKindFeign;
    @Autowired
    private MfVouAfterTrackFeign mfVouAfterTrackFeign;
    @Autowired
    private MfFincRepayDetailFeign mfFincRepayDetailFeign;
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;
    @Autowired
    private AppInterfaceFeign appInterfaceFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private AssureInterfaceFeign assureInterfaceFeign;
    @Autowired
    private CalcInterfaceFeign calcInterfaceFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private WkfBusInterfaceFeign wkfBusInterfaceFeign;

    @Autowired
    private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;
    @Autowired
    private CalcRepayPlanInterfaceFeign calcRepayPlanInterfaceFeign;
    @Autowired
    private MsgConfInterfaceFeign msgConfInterfaceFeign;
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private MfCusWhitenameInterfaceFeign mfCusWhitenameInterfaceFeign;
    @Autowired
    private MfBusFincAppAccountRecordFeign mfBusFincAppAccountRecordFeign;
    @Autowired
    private MfBusAppPenaltyMainFeign mfBusAppPenaltyMainFeign;
    @Autowired
    private CwCusBankAccManageFeign cwCusBankAccManageFeign;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private MfCusBankAccManageFeign      cusBankAccMangeFeign;
    @Autowired
    private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;

    @Autowired
    private MfBusFincAppChildFeign mfBusFincAppChildFeign;
    @Autowired
    private EsignInterfaceFeign esignInterfaceFeign;
    @Autowired
    private MfRepayPlanFeign mfRepayPlanFeign;
    @Autowired
    private MfPactFiveclassFeign mfPactFiveclassFeign;
    @Autowired
    private SysTaskInfoFeign sysTaskInfoFeign;
    @Autowired
    private MfStampPactFeign mfStampPactFeign;
    @Autowired
    private MfBusCompensatoryConfirmFeign mfBusCompensatoryConfirmFeign;
    @Autowired
    private MfExamineHisFeign mfExamineHisFeign;
    @Autowired
    private MfBusAdvanceLoanFeign mfBusAdvanceLoanFeign;
    @Autowired
    private CollateralInterfaceFeign collateralInterfaceFeign;
    @Autowired
    private PledgeBaseInfoFeign pledgeBaseInfoFeign;
    @Autowired
    private InsInfoFeign insInfoFeign;

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formfincapp0002 = formService.getFormData("fincapp0002");
        model.addAttribute("formfincapp0002", formfincapp0002);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_List";
    }

    @RequestMapping(value = "/generateRepayPlan")
    public String generateRepayPlan(Model model, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formfincapp003 = formService.getFormData("fincapp003");
        getFormValue(formfincapp003);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        getObjValue(formfincapp003, mfBusFincApp);

        // 从合同中获取合同开始日期和结束日期
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(mfBusFincApp.getPactId());
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("mfBusPact", mfBusPact);
        dataMap.put("mfBusFincApp", mfBusFincApp);
        FormData formgenerateRepayPlan0001 = formService.getFormData("generateRepayPlan0001");
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("formfincapp003", formfincapp003);
        model.addAttribute("formgenerateRepayPlan0001", formgenerateRepayPlan0001);
        model.addAttribute("query", "");
        return "/component/pact/MfBus_GenerateRepayPlan";
    }



    @RequestMapping(value = "/getViewPoint")
    public String getViewPoint(Model model, String fincId, String taskId, String hideOpinionType, String activityType)
            throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);

        Map<String, String> map = new HashMap<String, String>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusFincApp.getCusNo());
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

        String busModel = mfBusFincApp.getBusModel();
        MfBusFincAppHis mfBusFincAppHis = new MfBusFincAppHis();
        mfBusFincAppHis.setFincId(fincId);
        if (BizPubParm.BUS_MODEL_1.equals(busModel) || BizPubParm.BUS_MODEL_2.equals(busModel)) {// 动产质押业务
            map.put("cusNoWarehouse", mfBusFincApp.getCusNoWarehouse());// 仓储机构
        } else if (BizPubParm.BUS_MODEL_4.equals(busModel)) {// 保兑仓
            map.put("cusNoCore", mfBusFincApp.getCusNoCore());// 核心企业
        } else if (BizPubParm.BUS_MODEL_5.equals(busModel) || BizPubParm.BUS_MODEL_6.equals(busModel)) {// 保理
            map.put("cusNoCore", mfBusFincApp.getCusNoCore());
        }else {
        }

        map.put("cusNo", mfBusFincApp.getCusNo());
        map.put("busModel", mfBusFincApp.getBusModel());
        map.put("pactId", mfBusFincApp.getPactId());
        map.put("cusBaseType", mfCusCustomer.getCusBaseType());
        List<MfBusFincAppHis> list = mfBusFincAppFeign.getFincListByFincId(mfBusFincAppHis);
        if (list != null && list.size() > 0) {
            mfBusFincAppHis = list.get(0);
            PropertyUtils.copyProperties(mfBusFincApp, mfBusFincAppHis);
        }
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(mfBusFincApp.getAppId());
        mfBusPact = pactInterfaceFeign.getById(mfBusPact);
        // 通过借据号获取子借据号
        MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
        mfBusFincAppChild.setFincId(mfBusFincApp.getFincId());// 借据id
        mfBusFincAppChild.setPutoutCount(mfBusFincApp.getPutoutCount());// 放款次数
        mfBusFincAppChild = pactInterfaceFeign.getMfBusFincAppChildByInfo(mfBusFincAppChild);
        mfBusFincAppChild.setAgreeStorageFlag(mfBusFincApp.getAgreeStorageFlag());
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfBusFincAppChild.getFincChildId(), taskId);// 当前审批节点task
        String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("approvalNodeNo", "putout_approval");// 支用审批
        String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.putout_approval,
                mfBusPact.getAppId(), mfBusFincAppChild.getFincChildId(), User.getRegNo(request));
        if ("finc_supplement_data".equals(nodeNo)) {
            formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.finc_supplement_data,
                    mfBusPact.getAppId(), mfBusFincAppChild.getFincChildId(), User.getRegNo(request));
        }
        FormData formfincapp0005 = formService.getFormData(formId);

        mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);

        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        if (StringUtil.isNotEmpty(mfBusFincApp.getBankAccId())) {
            mfCusBankAccManage.setId(mfBusFincApp.getBankAccId());
            mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
            if (mfCusBankAccManage != null) {
                mfBusPact.setBankAccId(mfBusFincApp.getBankAccId());
                mfBusPact.setIncomBank(mfCusBankAccManage.getBank());
                mfBusPact.setIncomAccountName(mfCusBankAccManage.getAccountName());
            }
        }

        if (StringUtil.isNotEmpty(mfBusFincApp.getCollectAccId())) {
            mfCusBankAccManage = new MfCusBankAccManage();
            mfCusBankAccManage.setId(mfBusFincApp.getCollectAccId());
            mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
            if (mfCusBankAccManage != null) {
                mfBusPact.setCollectAccId(mfBusFincApp.getCollectAccId());
                mfBusPact.setCollectBank(mfCusBankAccManage.getBank());
                mfBusPact.setCollectAccName(mfCusBankAccManage.getAccountName());
            }
        }

        if (StringUtil.isNotEmpty(mfBusFincApp.getRepayAccId())) {
            mfCusBankAccManage = new MfCusBankAccManage();
            mfCusBankAccManage.setId(mfBusFincApp.getRepayAccId());
            mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
            if (mfCusBankAccManage != null) {
                mfBusPact.setRepayAccId(mfBusFincApp.getRepayAccId());
                mfBusPact.setRepayBank(mfCusBankAccManage.getBank());
                mfBusPact.setRepayAccName(mfCusBankAccManage.getAccountName());
            }
        }

        model.addAttribute("mfCusBankAccManage", mfCusBankAccManage);
        mfBusPact.setPayMethod(mfBusFincApp.getPayMethod());
        mfBusPact.setIfImmediateTransfer(mfBusFincApp.getIfImmediateTransfer());

        // 封装借据结束日期展示
        mfBusFincAppChild.setIntstEndDateShow(mfBusFincApp.getIntstEndDateShow());


        getObjValue(formfincapp0005, mfBusPact);
        mfBusFincAppChild.setBeginDate(mfBusPact.getBeginDate());
        //mfBusFincAppChild中fincRate与mfBusPact保持一致
        mfBusFincAppChild.setFincRate(mfBusPact.getFincRate());
        getObjValue(formfincapp0005, mfBusFincAppChild);
        model.addAttribute("mfBusFincAppChild", mfBusFincAppChild);
        // 获得该申请相关的费用标准信息
        List<MfBusAppFee> mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(mfBusFincApp.getAppId(), null, null);
        model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);
        map.put("appId", mfBusFincApp.getAppId());
        map.put("fincId", fincId);
        map.put("vouType", mfBusPact.getVouType());
        map.put("pactSts", mfBusPact.getPactSts());
        map.put("scNo", BizPubParm.SCENCE_TYPE_DOC_FINC_APPROVAL);
        ViewUtil.setViewPointParm(request, map);

        // 处理审批意见类型
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
        opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
        opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
        List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
                taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(formfincapp0005, "opinionType", "optionArray", opinionTypeList);

        // 获得当前审批岗位前面审批过得岗位信息
        JSONArray befNodesjsonArray = new JSONArray();
        befNodesjsonArray = wkfInterfaceFeign.getBefNodes(mfBusFincAppChild.getFincChildId(), null);
        request.setAttribute("befNodesjsonArray", befNodesjsonArray);
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusFincApp.getAppId());
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        // 放款申请表单中的还款方式修改时应收产品设置中的还款方式控制
        JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
        JSONArray repayDateSetMap = prdctInterfaceFeign.getRepayRulesMap(mfBusPact);
        for (int i = 0; i < repayDateSetMap.size(); i++) {
            String repayDateSet = repayDateSetMap.getJSONObject(i).getString("id").split("_")[1];
            if (StringUtil.isNotEmpty(repayDateSet) && StringUtil.isNotEmpty(mfBusAppKind.getRepayDateSet())) {
                if (repayDateSet.equals(mfBusAppKind.getRepayDateSet())) {
                    mfBusAppKind.setRepayDateSet(repayDateSetMap.getJSONObject(i).getString("id"));
                    break;
                }
            }
        }

        JSONObject json = new JSONObject();
        json.put("repayTypeMap", repayTypeMap);
        json.put("repayDateSetMap", repayDateSetMap);
        this.changeFormProperty(formfincapp0005, "repayDateSet", "initValue", mfBusAppKind.getRepayDateSet());
        this.changeFormProperty(formfincapp0005, "repayDateDef", "initValue", mfBusAppKind.getRepayDateDef());
        this.changeFormProperty(formfincapp0005, "manageOpNo3", "initValue", mfBusFincApp.getManageOpNo3());

        // 客户的账户列表
        mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
        mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);
        mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
        List<MfCusBankAccManage> mfCusBankAccManageList = cusInterfaceFeign
                .getMfCusBankAccListByCusNo(mfCusBankAccManage);
        JSONArray bankArray = JSONArray.fromObject(mfCusBankAccManageList);
        for (int i = 0; i < bankArray.size(); i++) {
            bankArray.getJSONObject(i).put("name",
                    bankArray.getJSONObject(i).getString("accountNo").replaceAll("([\\d]{4})(?=\\d)", "$1 "));
        }
        json.put("bankArray", bankArray);
        //添加客户证件号码
        this.changeFormProperty(formfincapp0005, "cusNameNum", "initValue", mfCusCustomer.getIdNum());
        // 封装阳光银行项目启用标记
        String mfToSunCBS = PropertiesUtil.getWebServiceProperty("mftf_to_cbs");
        json.put("mfToSunCBS", mfToSunCBS);
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        model.addAttribute("mfBusFincAppChild", mfBusFincAppChild);
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("formfincapp0005", formfincapp0005);
        model.addAttribute("query", "");
        return "/component/pact/MfFincAppViewPoint";
    }

    /**
     * 放款审批提交
     *
     * @return
     * @throws Exception
     * @author WangChao
     * @date 2017-7-1 下午2:41:12
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/updateProcess")
    @ResponseBody
    public Map<String, Object> updateProcess(Model model, String ajaxData, String fincId, String ajaxDataList,
                                             String taskId, String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        //判断当前节点是否可进行审批
        TaskImpl taskApprove = wkfInterfaceFeign.getTask(fincId, "");
        if(taskApprove!=null&&!taskId.equals(String.valueOf(taskApprove.getDbid()))){//不相等则审批不在此环节
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
            return dataMap;
        }
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        MfBusFincApp mfBusFincAppTmp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        Map map = getMapByJson(ajaxData);
        FormData formfincapp0005 = formService.getFormData((String) map.get("formId"));
        getFormValue(formfincapp0005, map);
        setObjValue(formfincapp0005, mfBusFincAppTmp);
        mfBusFincAppTmp.setWkfRepayId(mfBusFincApp.getWkfRepayId());
        mfBusFincAppTmp.setRateType(mfBusFincApp.getRateType());
        JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
        List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(),
                new JsonConfig());
        try {
            dataMap = getMapByJson(ajaxData);
            String opinionType = String.valueOf(dataMap.get("opinionType"));
            String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
            //用款审批界面取展示后台设置的利率，保存到库里面存年利率
            mfBusFincAppTmp = mfBusFincAppFeign.disProcessDataForFinc(mfBusFincAppTmp);
            mfBusFincAppTmp.setCurrentSessionOrgNo(User.getOrgNo(request));
            dataMap.put("mfBusFincApp", mfBusFincAppTmp);
            dataMap.put("mfBusAppFeeList", mfBusAppFeeList);
            dataMap.put("approvalOpinion", approvalOpinion);
            Result res = mfBusFincAppFeign.updateProcess(taskId, fincId, opinionType, transition,
                    User.getRegNo(request), nextUser, dataMap);
            dataMap = new HashMap<String, Object>();
            if (res.isSuccess()) {
                dataMap.put("flag", "success");
                if (res.isEndSts()) {
                    // 审批通过之后提交业务到下一个阶段
                    MfBusPact mfBusPact = new MfBusPact();
                    mfBusPact.setPactId(mfBusFincApp.getPactId());
                    mfBusPact = mfBusPactFeign.getById(mfBusPact);
                    Result result = wkfBusInterfaceFeign.doCommitNextStepWithUser(mfBusPact.getAppId(),
                            mfBusPact.getWkfAppId(),User.getRegNo(request));
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
     * 未放款解除
     *
     * @return
     * @throws Exception
     * @author WangChao
     * @date 2017-7-1 下午2:41:12
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/noPutoutAjax")
    @ResponseBody
    public Map<String, Object> noPutoutAjax(Model model, String appId, String fincId ) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        try {
           mfBusFincAppFeign.noPutout(appId,fincId);
            dataMap = new HashMap<String, Object>();
            dataMap.put("flag", "success");
            dataMap.put("msg", "业务已被退回到放款通知书环节");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
            throw e;
        }
        return dataMap;
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String sts5, String ajaxData,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            //如果传入cusNo,则查询该客户相关的借据
            if(StringUtil.isNotEmpty(cusNo)){
                mfBusFincApp.setCusNo(cusNo);
            }
            //20190315yxl 条件查询finc_sts=5 （放款已复核）的借据,以及借据状态不等于3的借据
            if (StringUtil.isNotEmpty(sts5)){
                mfBusFincApp.setFincSts("5");
                mfBusFincApp.setIsJxhj("true");
            }
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            // this.getRoleConditions(mfBusFincApp,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
            ipage = mfBusFincAppFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
             * dataMap.put("tableData",tableHtml);
             */
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 借新还旧带出之前的借据相关数据以及申请数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getWithAppByIdAjax")
    @ResponseBody
    public Map<String, Object> getWithAppByIdAjax(String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formfincapp0002 = formService.getFormData("fincapp0002");
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

//		MfBusPact mfBusPact = new MfBusPact();
//		mfBusPact.setPactId(mfBusFincApp.getPactId());
//		mfBusPact = mfBusPactFeign.getById(mfBusPact);

        MfBusApply mfbusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusFincApp.getAppId());
        //获取征信用途字典项
//		CodeUtils cd = new CodeUtils();
//		String FincUseSmDesText = cd.getCacheByKeyName("")
//		mfbusApply.setFincUseSmDes()
        getObjValue(formfincapp0002, mfBusFincApp, formData);

        /* 日期格式化 开始日期  到期日期 放在上边这一行下边以防止影响formfincapp0002内容生成 */
        mfBusFincApp.setIntstBeginDate(DateUtil.parseDateToTenStr(sdf.parse(mfBusFincApp.getIntstBeginDate())));
        mfBusFincApp.setIntstEndDateShow(DateUtil.parseDateToTenStr(sdf.parse(mfBusFincApp.getIntstEndDateShow())));
        //金额格式化 借据金额putoutAmt 借据余额loanBal
        mfBusFincApp.setPutoutAmt1( MathExtend.moneyStr(mfBusFincApp.getPutoutAmt()));
        mfBusFincApp.setLoanBal1(MathExtend.moneyStr(mfBusFincApp.getLoanBal()));
        /*20190326yxl*/
        //处理利率展示
        MfBusPact mfBusPact=new MfBusPact();
        mfBusPact.setPactId(mfBusFincApp.getPactId());
        mfBusPact=mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        mfBusFincApp.setFincRate(mfBusPact.getFincRate());
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        mfBusFincApp.setFincRateUnit(rateUnit);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位

        //担保方式
        dataMap.put("flag", "success");
        dataMap.put("mfBusFincApp", mfBusFincApp);
        dataMap.put("mfBusApply", mfbusApply);
        dataMap.put("formData", formData);
        return dataMap;
    }

    /**
     *
     * 方法描述：验证账号信息与合同中的是否一致
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2017-7-31 下午7:28:32
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/validateAccIsModifyAjax")
    @ResponseBody
    public Map<String, Object> validateAccIsModifyAjax(String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map map = getMapByJson(ajaxData);
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setAppId(appId);
            mfBusPact = pactInterfaceFeign.getById(mfBusPact);
            StringBuffer msg = new StringBuffer();
            if (map.containsKey("bankAccId")) {
                String bankAccId = (String) map.get("bankAccId");
                msg = checkAccSameWithPact("借款账号", bankAccId, mfBusPact.getBankAccId());
            }
            if (map.containsKey("repayAccId")) {
                String repayAccId = (String) map.get("repayAccId");
                msg = checkAccSameWithPact("还款账号", repayAccId, mfBusPact.getRepayAccId());
            }
            if (map.containsKey("collectAccId")) {
                String collectAccId = (String) map.get("collectAccId");
                msg = checkAccSameWithPact("收款账号", collectAccId, mfBusPact.getCollectAccId());
            }
            if (msg.length() == 0) {
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", msg.substring(0, msg.length() - 1));
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     *
     * @param accDesc 借款账号、还款账号、收款账号
     * @param bankAccId 银行卡id
     * @param bankAccIdInPact 合同中的银行卡id
     * @return
     */
    private StringBuffer checkAccSameWithPact(String accDesc, String bankAccId, String bankAccIdInPact) {
        StringBuffer msg = new StringBuffer();
        String projectName= ymlConfig.getSysParams().get("sys.project.name");
        if (StringUtil.isNotEmpty(bankAccId) && StringUtil.isNotEmpty(bankAccIdInPact) && !bankAccIdInPact.equals(bankAccId)) {
            MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
            mfCusBankAccManage.setId(bankAccId);
            mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);

            if (mfCusBankAccManage != null) {
                MfCusBankAccManage mfCusBankAccManageInPact = new MfCusBankAccManage();
                mfCusBankAccManageInPact.setId(bankAccIdInPact);
                mfCusBankAccManageInPact = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManageInPact);
                if(mfCusBankAccManageInPact != null){
                    if (!mfCusBankAccManage.getAccountNo().equals(mfCusBankAccManageInPact.getAccountNo())) {
                        msg.append(accDesc).append("(").append(mfCusBankAccManage.getAccountNo()).append(")与合同中的账号(")
                                .append(mfCusBankAccManageInPact.getAccountNo()).append(")、");
                    }
                }
            }
        }
        return msg;
    }

    /**
     *
     * 放款申请方法 新增借据
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData, String appId, String query, String temporaryStorage,String nodeNoOld) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);// 当前审批节点task
            String nodeNo = taskAppro.getActivityName();// 功能节点编号
            if(!nodeNoOld.equals(nodeNo)){//不相等则节点不在此环节
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
                return dataMap;
            }



            // 电签校验
            if(WKF_NODE.putout_print.equals(nodeNo)){//出账签约
                //如果当前节点是合同签订环节，判断是否已电子签章
                Map<String,Object> paramMap = new HashMap<String,Object>();
                paramMap.put("nodeNo",nodeNo);
                paramMap.put("appId",appId);
                Map<String,Object> resultMap = esignInterfaceFeign.getTemplateIfEsigned(paramMap);
                String esignFlag = (String)resultMap.get("flag");
                if(esignFlag == null || "".equals(esignFlag) || "error".equals(esignFlag)){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "出账签约失败，请先进行电签操作！");
                    return dataMap;
                }
            }
            if(WKF_NODE.putout_print.getNodeNo().equals(nodeNo)){//合同打印环节不保存数据，只更新流程数据
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setAppId(appId);
                mfBusFincApp.setOpNo(User.getRegNo(request));
                Map<String,Object> resultMap = pactInterfaceFeign.doBusinessCommit(mfBusFincApp);
                MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
                mfBusFincAppChild.setFincId((String)resultMap.get("fincId"));
                mfBusFincAppChild = mfBusFincAppChildFeign.getById(mfBusFincAppChild);
                FormData formfincapp0006 = formService.getFormData("fincapp0006");
                getFormValue(formfincapp0006);
                getObjValue(formfincapp0006, mfBusFincAppChild);
                // 期限单位格式化
                CodeUtils codeUtils = new CodeUtils();
                Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
                String termUnit = termTypeMap.get(mfBusFincAppChild.getTermType()).getRemark();
                this.changeFormProperty(formfincapp0006, "term", "unit", termUnit);
                // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
                Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
                String rateUnit = rateTypeMap.get(mfBusFincAppChild.getRateType()).getRemark();
                this.changeFormProperty(formfincapp0006, "fincRate", "unit", rateUnit);
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formfincapp0006, "propertySeeTag", query);
                String msg = mfBusFincAppFeign.getNextFlowRemid(mfBusFincAppChild);
                dataMap.put("htmlStr", htmlStr);
                dataMap.put("fincSts", mfBusFincAppChild.getFincSts());
                dataMap.put("flag", "success");
                dataMap.put("fincId", mfBusFincAppChild.getFincId());
                dataMap.put("msg",msg);
                if ("1".equals(temporaryStorage)) {// 暂存
                    dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
                }
                return dataMap;
            }
            Map map = getMapByJson(ajaxData);
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setAppId(appId);
            mfBusPact = pactInterfaceFeign.getById(mfBusPact);
            if (!"1".equals(temporaryStorage)) {// 暂存
                //判端是否已经全部进行过用印进行过合同登记
                boolean registFlag = true;
                MfStampBaseInfo mfStampBaseInfo = new MfStampBaseInfo();
                mfStampBaseInfo.setPactId(mfBusPact.getPactId());
                mfStampBaseInfo =mfStampPactFeign.getStampBaseInfoById(mfStampBaseInfo);
                if(mfStampBaseInfo!=null){
                    if("2".equals(mfStampBaseInfo.getStampSts())){
                        MfStampPact mfStampPact =new MfStampPact();
                        mfStampPact.setPactId(mfBusPact.getPactId());
                        List<MfStampPact> mfStampPactList = mfStampPactFeign.findAllList(mfStampPact);
                        if(mfStampPactList!=null&&mfStampPactList.size()>0){
                            registFlag = false;
                            for (int i = 0; i < mfStampPactList.size(); i++) {
                                if(!("4".equals(mfStampPactList.get(i).getStampSts())||"2".equals(mfStampPactList.get(i).getStampSts()))){
                                    registFlag=true;
                                }
                            }
                        }
                    }
                }
                if(registFlag){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "请先进行全部合同用印后，再进行放款通知书操作！");
                    return dataMap;
                }
                //判断是否存在提前放款
                MfBusAdvanceLoan mfBusAdvanceLoan = new MfBusAdvanceLoan();
                mfBusAdvanceLoan.setAppId(appId);
                boolean advanceFlag = false;
                mfBusAdvanceLoan = mfBusAdvanceLoanFeign.getByPactId(mfBusPact.getPactId());
                if(mfBusAdvanceLoan!=null){
                    String appOpNo = User.getRegNo(request);
                    TaskImpl task = wkfInterfaceFeign.getTaskWithUser(mfBusAdvanceLoan.getAdvanceLoanId(), "", appOpNo);
                    if(task!=null){
                        advanceFlag=true;
                    }
                }
                if(registFlag){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "已经发起提前放款申请,不能再申请放款！");
                    return dataMap;
                }
            }
            FormData formfincapp0003 = formService.getFormData((String) map.get("formId"));
            getFormValue(formfincapp0003, map);
            /**
             * 白名单管理处理：end
             */
            if (this.validateFormData(formfincapp0003)) {
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                setObjValue(formfincapp0003, mfBusFincApp);
                mfBusFincApp.setCusNo(mfBusPact.getCusNo());
                mfBusFincApp.setRateType(mfBusPact.getRateType());
                mfBusFincApp.setAppId(appId);
                //借据开始日不能早于合同开始日
                String fincBeginDate = mfBusFincApp.getIntstBeginDate();
                String pactBeginDate = mfBusPact.getBeginDate();
                if(StringUtil.isNotEmpty(fincBeginDate)){
                    if(DateUtil.isBefore(fincBeginDate, pactBeginDate))
                    {
                        dataMap.put("flag", "error");
                        dataMap.put("msg", "借据开始日期不能早于合同开始日！");
                        return dataMap;
                    }
                }
                //放款时额度检验
                Map<String,String> checkAmtMap = pactInterfaceFeign.checkPutoutAmt(mfBusFincApp);
                if("error".equals(checkAmtMap.get("flag"))){
                    dataMap.put("flag", checkAmtMap.get("flag"));
                    dataMap.put("msg", checkAmtMap.get("msg"));
                    return dataMap;
                }
                //检验是否登记变更
                boolean tipFlag =false;
                appId = mfBusPact.getAppId();
                List<MfBusCollateralDetailRel> mfBusCollateralDetailRelList = collateralInterfaceFeign.getCollateralDetailRelList(appId);
                if(mfBusCollateralDetailRelList.size()>0){
                    for (int i = 0; i < mfBusCollateralDetailRelList.size(); i++) {
                        MfBusCollateralDetailRel mfBusCollateralDetailRel = mfBusCollateralDetailRelList.get(i);
                        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                        pledgeBaseInfo.setPledgeNo(mfBusCollateralDetailRel.getCollateralId());
                        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
                        if(pledgeBaseInfo!=null){
                            if("C".equals(pledgeBaseInfo.getClassFirstNo())&&mfBusCollateralDetailRel.getIfRegister().equals("1")){
                                InsInfo insInfo = new InsInfo();
                                insInfo.setCollateralId(pledgeBaseInfo.getPledgeNo());
                                List<InsInfo> insInfoList = insInfoFeign.getListByCollateralId(insInfo);
                               if(insInfoList.size()==0){
                                   tipFlag = true;
                               }
                            }
                        }
                    }
                }
                if(tipFlag)
                {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "请完成应收账款登记！");
                    return dataMap;
                }
                // 调整 支付申请 支付申请业务处理
                // 验证该合同的可支用余额是否足够
                Map<String, String> checkMap = mfBusFincAppFeign.doCheckFincBal(mfBusFincApp,mfBusPact.getPactId());
                if ("success".equals(checkMap.get("flag"))) {
                    // cusInterfaceFeign.updateInfIntegrity(mfBusPact.getCusNo());
                    MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
                    new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusFincApp);
                    map.put("mfBusFincApp", mfBusFincApp);
                    map.put("temporaryStorage", temporaryStorage);
                    mfBusFincAppChild = mfBusFincAppFeign.doInsertFincAppWithUser(map,User.getRegNo(request),User.getRegName(request));
                    mfBusFincAppChild = mfBusFincAppFeign.disProcessDataForFincChildShow(mfBusFincAppChild);

                    // 初始化电签模板
                    mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
                    taskAppro = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);// 当前审批节点task
                    nodeNo = taskAppro.getActivityName();// 功能节点编号
                    mfBusApply.setNodeNo(nodeNo);
                    esignInterfaceFeign.initReplacePdf(mfBusApply);

                    FormData formfincapp0006 = formService.getFormData("fincapp0006");
                    getFormValue(formfincapp0006);
                    getObjValue(formfincapp0006, mfBusFincAppChild);
                    // 期限单位格式化
                    CodeUtils codeUtils = new CodeUtils();
                    Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
                    String termUnit = termTypeMap.get(mfBusFincAppChild.getTermType()).getRemark();
                    this.changeFormProperty(formfincapp0006, "term", "unit", termUnit);
                    // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
                    Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
                    String rateUnit = rateTypeMap.get(mfBusFincAppChild.getRateType()).getRemark();
                    this.changeFormProperty(formfincapp0006, "fincRate", "unit", rateUnit);
                    JsonFormUtil jsonFormUtil = new JsonFormUtil();
                    String htmlStr = jsonFormUtil.getJsonStr(formfincapp0006, "propertySeeTag", query);
                    String msg = mfBusFincAppFeign.getNextFlowRemid(mfBusFincAppChild);
                    dataMap.put("htmlStr", htmlStr);
                    dataMap.put("fincSts", mfBusFincAppChild.getFincSts());
                    dataMap.put("flag", "success");
                    dataMap.put("fincId", mfBusFincAppChild.getFincId());
                    dataMap.put("msg",msg);
                    if ("1".equals(temporaryStorage)) {// 暂存
                        dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
                    }
                    dataMap.put("advInterestBal", checkMap.get("advInterestBal"));
                    dataMap.put("advGuaranteeBal",checkMap.get("advGuaranteeBal"));
                } else {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", checkMap.get("msg"));
                }
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }

        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formfincapp0002 = formService.getFormData("fincapp0002");
        getFormValue(formfincapp0002, getMapByJson(ajaxData));
        MfBusFincApp mfBusFincAppJsp = new MfBusFincApp();
        setObjValue(formfincapp0002, mfBusFincAppJsp);
        MfBusFincApp mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincAppJsp);
        if (mfBusFincApp != null) {
            try {
                mfBusFincApp = (MfBusFincApp) EntityUtil.reflectionSetVal(mfBusFincApp, mfBusFincAppJsp,
                        getMapByJson(ajaxData));
                mfBusFincAppFeign.update(mfBusFincApp);
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
    public Map<String, Object> updateAjax(String ajaxData, String pactId, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formfincapp0002 = formService.getFormData("fincapp0002");
            getFormValue(formfincapp0002, getMapByJson(ajaxData));
            if (this.validateFormData(formfincapp0002)) {
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                setObjValue(formfincapp0002, mfBusFincApp);
                mfBusFincAppFeign.update(mfBusFincApp);
                mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setPactId(pactId);
                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfBusFincAppFeign.getList(mfBusFincApp), null,
                        true);
                dataMap.put("tableData", tableHtml);
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
     *
     * 方法描述： 放款审批提交(手动启动放款审批流程)
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2016-8-5 下午5:52:09
     */
    @RequestMapping(value = "/processSubmitAjax")
    @ResponseBody
    public Map<String, Object> processSubmitAjax(String fincId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            String orgNo = User.getOrgNo(request);
            mfBusFincApp = mfBusFincAppFeign.doInprocess(fincId, "",orgNo);
            dataMap.put("node", "processaudit");
            dataMap.put("processType", "finc");
            dataMap.put("fincSts", mfBusFincApp.getFincSts());
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_COMMIT.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            throw e;
        }
        return dataMap;
    }


    /**
     *
     * 方法描述： 放款复核页面
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2016-7-27 上午9:36:07
     */
    @RequestMapping(value = "/fincReview")
    public String fincReview(Model model, String appId, String pactId, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String scNo = BizPubParm.SCENCE_TYPE_DOC_REVIEW_FINC;
        FormData formfincapp0004 = formService.getFormData("fincapp0004");
        MfBusPact mfBusPact = null;
        MfBusFincApp mfBusFincApp = null;
        if (pactId == null && appId != null) {
            // 获取合同数据
            mfBusPact = new MfBusPact();
            mfBusPact = pactInterfaceFeign.getByAppId(appId);
            // 获取借据信息
            mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setAppId(appId);
            mfBusFincApp.setFincSts(BizPubParm.FINC_STS_PASS);// 待复核的借据
            mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        } else {
            // 获取合同数据
            mfBusPact = new MfBusPact();
            mfBusPact.setPactId(pactId);

            mfBusPact = pactInterfaceFeign.getById(mfBusPact);
            // 获取借据信息
            mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(fincId);
            mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

            List<MfBusAppFee> mfBusAppFeeList = calcInterfaceFeign.getBusFeeList(pactId, "4");// 节点4代表融资款放款
            model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);

        }
        getObjValue(formfincapp0004, mfBusFincApp);
        getObjValue(formfincapp0004, mfBusPact);

        model.addAttribute("scNo", scNo);
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("formfincapp0004", formfincapp0004);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_FincReview";
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formfincapp0002 = formService.getFormData("fincapp0002");
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        getObjValue(formfincapp0002, mfBusFincApp, formData);
        if (mfBusFincApp != null) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("mfBusFincApp", mfBusFincApp);
        dataMap.put("formData", formData);
        return dataMap;
    }

    /**
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String fincId, String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        try {
            mfBusFincAppFeign.delete(mfBusFincApp);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfBusFincAppFeign.getList(mfBusFincApp), null, true);
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
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/input")
    public String input(Model model, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);// 当前审批节点task
        String nodeNo = taskAppro.getActivityName();// 功能节点编号
        model.addAttribute("scNo", nodeNo);
        model.addAttribute("nodeNo", nodeNo);
        // 获取合同数据
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact = pactInterfaceFeign.getById(mfBusPact);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusPact.getCusNo());
        mfCusCustomer=mfCusCustomerFeign.getById(mfCusCustomer);
        mfBusPact.setCusNameNum(mfCusCustomer.getIdNum());
        mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);

        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

        String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.putout_apply, null, null,User.getRegNo(request));
        if(WKF_NODE.putout_print.getNodeNo().equals(nodeNo)){//出账签约不允许编辑表单
            formId = "putoutPrint";
            // 初始化电签模板
            mfBusApply.setNodeNo(nodeNo);
            esignInterfaceFeign.initReplacePdf(mfBusApply);
        }
        FormData formfincapp0006 = formService.getFormData(formId);
        MfBusFincAppChild fincAppChild = new MfBusFincAppChild();
        fincAppChild.setPactId(mfBusPact.getPactId());
        fincAppChild.setFincSts("1");
        fincAppChild = mfBusFincAppChildFeign.getById(fincAppChild);// 保存未提交回显数据

        if (fincAppChild == null) {
            fincAppChild = new MfBusFincAppChild();
        }

        fincAppChild.setOpName(User.getRegName(request));
        fincAppChild.setOpNo(User.getRegNo(request));
        fincAppChild.setPactId(mfBusPact.getPactId());
        fincAppChild.setChargeInterest(0);
        fincAppChild.setChargeFee(0);

        if (!WKF_NODE.putout_print.getNodeNo().equals(nodeNo)) {// 出账签约不默认
            fincAppChild.setIntstEndDate(mfBusPact.getEndDate());
            fincAppChild.setIntstBeginDate(mfBusPact.getBeginDate());
        }
        //合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天
        String pactEndDateShow = mfBusAppKind.getPactEnddateShowFlag();
        if(StringUtil.isEmpty(pactEndDateShow)){
            pactEndDateShow = prdctInterfaceFeign.getPactEndDateShowFlag();
        }

       /* if ("1".equals(mfBusAppKind.getTermType())) {// 月
            String intstEndDateShow = DateUtil.addMonth(DateUtil.getDate(), mfBusAppKind.getMaxFincTerm());
            if (BizPubParm.PACT_ENDDATE_SHOW_FLAG_2.equals(pactEndDateShow)) {
                intstEndDateShow = DateUtil.addByDay(intstEndDateShow, -1);
            }
            if (mfBusPact.getEndDateShow() != null
                    && DateUtil.compareEightDate(intstEndDateShow, mfBusPact.getEndDateShow()) == 1) {
                intstEndDateShow = mfBusPact.getEndDateShow();
            }
            fincAppChild.setIntstEndDateShow(intstEndDateShow);
        } else if ("2".equals(mfBusAppKind.getTermType())) {// 天
            String intstEndDateShow = DateUtil.addByDay(DateUtil.getDate(), mfBusAppKind.getMaxFincTerm());
            if (BizPubParm.PACT_ENDDATE_SHOW_FLAG_2.equals(pactEndDateShow)) {
                intstEndDateShow = DateUtil.addByDay(intstEndDateShow, -1);
            }
            if (BizPubParm.PACT_ENDDATE_SHOW_FLAG_2.equals(pactEndDateShow)) {
                intstEndDateShow = DateUtil.addByDay(intstEndDateShow, -1);
            }
            if (mfBusPact.getEndDateShow() != null
                    && DateUtil.compareEightDate(intstEndDateShow, mfBusPact.getEndDateShow()) == 1) {
                intstEndDateShow = mfBusPact.getEndDateShow();
            }
            fincAppChild.setIntstEndDateShow(intstEndDateShow);
        }*/
        //处理 借据的开始日期 借据结束日期  期限
        Map<String, Object> parmMapDateTerm = new HashMap<>();

        parmMapDateTerm.put("appId", mfBusPact.getAppId());
        parmMapDateTerm.put("pactBeginDate", mfBusPact.getBeginDate());
        parmMapDateTerm.put("pactEndDate", mfBusPact.getEndDate());
        parmMapDateTerm.put("pactEndDateShow", mfBusPact.getEndDateShow());
        parmMapDateTerm.put("pactTerm", mfBusPact.getTerm());

        Map<String, Object> resultMapDateTerm = mfBusFincAppFeign.doFincInitBeginAndEndDateAndTermByDateTerm(parmMapDateTerm);
        fincAppChild.setIntstBeginDate(String.valueOf(resultMapDateTerm.get("intstBeginDate")));
        fincAppChild.setIntstEndDate(String.valueOf(resultMapDateTerm.get("intstEndDate")));
        fincAppChild.setIntstEndDateShow(String.valueOf(resultMapDateTerm.get("intstEndDateShow")));
        String fincTerm = String.valueOf(resultMapDateTerm.get("fincTerm"));

        fincAppChild.setTermMonth(Integer.parseInt(fincTerm));

        //合同金额的展示不能这样修改（一笔合同多笔放款）
//		mfBusPact.setPutoutAmt(mfBusPact.getUsableFincAmt());
        //获得授信信息
        Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(appId);
        if (!creditData.isEmpty()) {
            getObjValue(formfincapp0006, creditData);
            mfBusPact.setCreditPactNo((String) creditData.get("creditPactNo"));
        }

        if (WKF_NODE.putout_print.getNodeNo().equals(nodeNo)) {// 出账签约
            fincAppChild.setIntstEndDateShow(fincAppChild.getIntstEndDate());
        }

        //根据合同号查询银行卡变更记录 如果有变更则取最新的变更记录那条已修改的银行卡信息
        MfBusFincAppAccountRecord mfBusFincAppAccountRecord = new MfBusFincAppAccountRecord();
        mfBusFincAppAccountRecord.setPactId(mfBusPact.getPactId());
        mfBusFincAppAccountRecord  =  mfBusFincAppAccountRecordFeign.getByNewest(mfBusFincAppAccountRecord);
        if(mfBusFincAppAccountRecord != null){//有变更记录
            mfBusPact.setBankAccId(mfBusFincAppAccountRecord.getBankAccId());
            mfBusPact.setRepayAccId(mfBusFincAppAccountRecord.getRepayAccId());
        }

        getObjValue(formfincapp0006, mfBusApply);
        getObjValue(formfincapp0006, mfBusPact);
        getObjValue(formfincapp0006, fincAppChild);

        //处理利率类型
        if ("1".equals(mfBusAppKind.getTermType())) {//月

            this.changeFormProperty(formfincapp0006, "termMonth", "unit", "个月");

        } else if ("2".equals(mfBusAppKind.getTermType())) {// 天

            this.changeFormProperty(formfincapp0006, "termMonth", "unit", "日");

        }else {
        }

        if(WKF_NODE.putout_print.getNodeNo().equals(nodeNo)){//出账签约不允许编辑表单
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setAppId(appId);
            mfBusFincApp = pactInterfaceFeign.getByIdNewFinc(mfBusFincApp);
            model.addAttribute("fincId",mfBusFincApp.getFincId());
            getObjValue(formfincapp0006, mfBusFincApp);
        }
        String wkfAppId = mfBusPact.getWkfAppId();
        model.addAttribute("wkfAppId", wkfAppId);
        // 放款申请表单中的还款方式修改时应收产品设置中的还款方式控制
        JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
        JSONArray repayDateSetMap = prdctInterfaceFeign.getRepayRulesMap(mfBusPact);
        for (int i = 0; i < repayDateSetMap.size(); i++) {
            String repayDateSet = repayDateSetMap.getJSONObject(i).getString("id").split("_")[1];
            if (StringUtil.isNotEmpty(repayDateSet) && StringUtil.isNotEmpty(mfBusAppKind.getRepayDateSet())) {
                if (repayDateSet.equals(mfBusAppKind.getRepayDateSet())) {
                    mfBusAppKind.setRepayDateSet(repayDateSetMap.getJSONObject(i).getString("id"));
                    break;
                }
            }
        }

        JSONObject json = new JSONObject();
        json.put("repayTypeMap", repayTypeMap);
        json.put("repayDateSetMap", repayDateSetMap);
        this.changeFormProperty(formfincapp0006, "repayDateSet", "initValue", mfBusAppKind.getRepayDateSet());
        this.changeFormProperty(formfincapp0006, "repayDateDef", "initValue", mfBusAppKind.getRepayDateDef());

        // 客户的账户列表
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
        mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);
        mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
        List<MfCusBankAccManage> mfCusBankAccManageList = cusInterfaceFeign
                .getMfCusBankAccListByCusNo(mfCusBankAccManage);
        JSONArray bankArray = JSONArray.fromObject(mfCusBankAccManageList);
        for (int i = 0; i < bankArray.size(); i++) {
            bankArray.getJSONObject(i).put("name",
                    bankArray.getJSONObject(i).getString("accountNo").replaceAll("([\\d]{4})(?=\\d)", "$1 "));
        }

        String fincAuthCycleFlag = new CodeUtils().getSingleValByKey("FINC_AUTH_CYCLE_FLAG");//是否判断放款循环标志 1:是   0：否
        if("1".equals(fincAuthCycleFlag)){
            MfBusFincApp mfBusFincApps = new MfBusFincApp();
            mfBusFincApps.setPactNo(mfBusPact.getPactNo());
            List<MfBusFincApp> mfBusFincAppList = pactInterfaceFeign.findByCusNo(mfBusFincApps);
            if(mfBusFincAppList.size()==0){
                this.changeFormProperty(formfincapp0006, "authCycle", "initValue", "0");
            }else{
                this.changeFormProperty(formfincapp0006, "authCycle", "initValue", "1");
            }
        }
        //表单展示合同利率与mfbusPact表中一致
        FormActive [] formActives = formfincapp0006.getFormActives();
        for(FormActive formActive:formActives)
        {
            if("fincRate".equals(formActive.getFieldName()))
            {
                formActive.setInitValue(String.valueOf(mfBusPact.getFincRate()));
            }
        }
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formfincapp0006, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formfincapp0006, "overRate", "unit", rateUnit);
        this.changeFormProperty(formfincapp0006, "cmpdRate", "unit", rateUnit);

        json.put("bankArray", bankArray);
        // 封装阳光银行项目启用标记
        String mfToSunCBS = PropertiesUtil.getWebServiceProperty("mftf_to_cbs");
        json.put("mfToSunCBS", mfToSunCBS);
        String ajaxData = json.toString();
        String jsonArrayStr = prdctInterfaceFeign.getFincUse(mfBusApply.getKindNo());
        com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
        model.addAttribute("fincUse", jsonArray);
        model.addAttribute("fincId", fincAppChild.getFincId());
        model.addAttribute("intstEndDateShow", DateUtil.getShowDateTime(fincAppChild.getIntstEndDateShow()));
        model.addAttribute("intstBeginDateShow", DateUtil.getShowDateTime(fincAppChild.getIntstBeginDate()));
        model.addAttribute("rateUnit", rateUnit);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("formfincapp0006", formfincapp0006);
        model.addAttribute("query", "");
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("pactBeginDate", DateUtil.getShowDateTime(mfBusPact.getBeginDate()));
        model.addAttribute("pactEndDate", DateUtil.getShowDateTime(mfBusPact.getEndDate()));
        String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
        model.addAttribute("sysProjectName", sysProjectName);
        model.addAttribute("kindNo", mfBusAppKind.getKindNo());

        // ---------- begin 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        Map<String, ParmDic> SAVE_ONLY = new CodeUtils().getMapObjByKeyName("SAVE_ONLY");// 启用: 显示保存及提交两个按钮(支持保存未提交) 停用: 只显示保存按钮
        String SAVE_ONLY_7 = "0";
        if (SAVE_ONLY.containsKey("7")) {// 启用
            SAVE_ONLY_7 = "1";// 支持保存未提交
        }
        model.addAttribute("SAVE_ONLY_7", SAVE_ONLY_7);
        // ---------- end 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------

        return "/component/pact/MfBusFincApp_Insert";
    }

    /**
     *
     * 方法描述： 校验放款申请金额和应收账款余额的大小，如果申请金额大于余额不允许放款
     *
     * @return
     * @author zhs
     * @date 2018-07-21 上午10:37:37
     */
    @RequestMapping(value = "/validatePutoutAmtAjax")
    @ResponseBody
    public Map<String, Object> validatePutoutAmtAjax(String putoutAmt, String appId) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            dataMap = mfBusFincAppFeign.validatePutoutAmt(putoutAmt,appId);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    @RequestMapping(value = "/validateBorrowCode")
    @ResponseBody
    public Map<String, Object> validateBorrowCode(String borrowCode) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            dataMap = mfBusFincAppFeign.validateBorrowCode(borrowCode);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }


    /**
     *
     * 方法描述： 校验借据到期日期是否在借据期限之间
     *
     * @return
     * @author zhs
     * @date 2017-11-29 下午4:09:37
     */
    @RequestMapping(value = "/validateFincTermAjax")
    @ResponseBody
    public Map<String, Object> validateFincTermAjax(String ajaxData, String appId) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String intstEndDate = ajaxData;
        try {
            MfBusAppKind mfBusAppKind = new MfBusAppKind();
            mfBusAppKind.setAppId(appId);
            mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
            dataMap.put("flag", "success");
            int[] monthDays = DateUtil.getMonthsAndDays(DateUtil.getDate(), intstEndDate);

            if ("1".equals(mfBusAppKind.getTermType())) {// 月
                int months = monthDays[0];
                int days = monthDays[1];
                if (months > 0) {
                    if ((months <= mfBusAppKind.getMinFincTerm()) || months >= mfBusAppKind.getMaxFincTerm()) {
                        if (days > 0) {
                            Map<String, Object> paramMap = new HashMap<String, Object>();
                            paramMap.put("info", "产品设置");
                            paramMap.put("field", "借据期限");
                            paramMap.put("value1", mfBusAppKind.getMinFincTerm() + "月");
                            paramMap.put("value2", mfBusAppKind.getMaxFincTerm() + "月");
                            dataMap.put("msg", MessageEnum.ONLY_APPLY_VALUE_SCOPE.getMessage(paramMap));
                            dataMap.put("flag", "error");
                        }
                    }
                }
            } else if ("2".equals(mfBusAppKind.getTermType())) {// 天
                int days = monthDays[1];
                if (days < mfBusAppKind.getMinFincTerm() || days > mfBusAppKind.getMaxFincTerm()) {
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("info", "产品设置");
                    paramMap.put("field", "借据期限");
                    paramMap.put("value1", mfBusAppKind.getMinFincTerm() + "天");
                    paramMap.put("value2", mfBusAppKind.getMaxFincTerm() + "天");
                    dataMap.put("msg", MessageEnum.ONLY_APPLY_VALUE_SCOPE.getMessage(paramMap));
                    dataMap.put("flag", "error");
                }
            }else {
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }

        return dataMap;
    }


    /**
     * 方法描述： 校验借据到期日期是否在借据期限之间
     *
     * @return
     * @author zhs
     * @date 2017-11-29 下午4:09:37
     */
    @RequestMapping(value = "/validateFincTermNewAjax")
    @ResponseBody
    public Map<String, Object> validateFincTermNewAjax(String term, String appId) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        try {
            MfBusAppKind mfBusAppKind = new MfBusAppKind();
            mfBusAppKind.setAppId(appId);
            mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

            if ("1".equals(mfBusAppKind.getTermType())) {// 月
                int months = Integer.parseInt(term);

                if (months < 0 || months < mfBusAppKind.getMinFincTerm() || months > mfBusAppKind.getMaxFincTerm()) {

                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("info", "产品设置");
                    paramMap.put("field", "借据期限");
                    paramMap.put("value1", mfBusAppKind.getMinFincTerm() + "月");
                    paramMap.put("value2", mfBusAppKind.getMaxFincTerm() + "月");
                    dataMap.put("msg", MessageEnum.ONLY_APPLY_VALUE_SCOPE.getMessage(paramMap));
                    dataMap.put("flag", "error");
                    return dataMap;
                }

            } else if ("2".equals(mfBusAppKind.getTermType())) {// 天
                int days = Integer.parseInt(term);
                if (days < mfBusAppKind.getMinFincTerm() || days > mfBusAppKind.getMaxFincTerm()) {
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("info", "产品设置");
                    paramMap.put("field", "借据期限");
                    paramMap.put("value1", mfBusAppKind.getMinFincTerm() + "天");
                    paramMap.put("value2", mfBusAppKind.getMaxFincTerm() + "天");
                    dataMap.put("msg", MessageEnum.ONLY_APPLY_VALUE_SCOPE.getMessage(paramMap));
                    dataMap.put("flag", "error");
                    return dataMap;
                }
            }else {
            }


            dataMap.put("flag", "success");

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }

        return dataMap;
    }



    /***
     * 新增
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insert")
    public String insert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formfincapp0002 = formService.getFormData("fincapp0002");
        getFormValue(formfincapp0002);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        setObjValue(formfincapp0002, mfBusFincApp);
        mfBusFincAppFeign.insert(mfBusFincApp);
        getObjValue(formfincapp0002, mfBusFincApp);
        this.addActionMessage(model, "保存成功");
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
        List<MfBusFincApp> mfBusFincAppList = (List<MfBusFincApp>) mfBusFincAppFeign
                .findByPage(ipage).getResult();
        model.addAttribute("mfBusFincAppList", mfBusFincAppList);
        model.addAttribute("formfincapp0002", formfincapp0002);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_Insert";
    }
    /**
     * 查询
     *
     * @return
     */
    @RequestMapping(value = "/getByIdApprov")
    public String getByIdApprov(Model model, String fincId) {
        ActionContext.initialize(request, response);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        List<WkfApprovalOpinion> hisTaskList = wkfInterfaceFeign.getWkfTaskHisList(mfBusFincApp.getWkfRepayId());
        JSONArray array = JSONArray.fromObject(hisTaskList);// hisTaskList.get(0).getState()
        JSONObject json = new JSONObject();
        json.put("hisTaskList", array);
        model.addAttribute("json", json);
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApprov_Detail";
    }

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFincApp")
    public String getFincApp(Model model, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formfincapp0002 = formService.getFormData("fincapp0002");
        // 获取放款申请数据
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
        getObjValue(formfincapp0002, mfBusFincApp);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
        this.changeFormProperty(formfincapp0002, "fincRate", "unit", rateUnit);
        if (mfBusFincApp.getFincSts().equals(BizPubParm.FINC_STS_AGREE)
                || mfBusFincApp.getFincSts().equals(BizPubParm.FINC_STS_FINISH)) {
            // MfBusRepayPlan mfBusRepayPlan = new MfBusRepayPlan();
            // mfBusRepayPlan.setFincId(fincId);
            // mfBusRepayPlanList = mfBusRepayPlanFeign.getList(mfBusRepayPlan);
            MfRepayPlan mfRepayPlan = new MfRepayPlan();
            mfRepayPlan.setFincId(fincId);
            List<MfRepayPlan> mfRepayPlanList = calcRepaymentInterfaceFeign.getMfRepayPlanList(mfRepayPlan);
            model.addAttribute("mfRepayPlanList", mfRepayPlanList);
        }
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("formfincapp0002", formfincapp0002);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_FincAppDetail";
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
        FormData formfincapp0002 = formService.getFormData("fincapp0002");
        getFormValue(formfincapp0002);
        boolean validateFlag = this.validateFormData(formfincapp0002);
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
        FormData formfincapp0002 = formService.getFormData("fincapp0002");
        getFormValue(formfincapp0002);
        boolean validateFlag = this.validateFormData(formfincapp0002);
    }

    /**
     * 方法描述：进入审批历史页面（获取审批信息）
     *
     * @return
     * @author ph
     *
     */
    @RequestMapping(value = "/getByIdAppHis")
    public String getByIdAppHis(Model model, String ajaxData) {
        ActionContext.initialize(request, response);
        return "/component/pact/MfBusPact_ApprovalHis";
    }

    /**
     * 还款到期列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRepayToDatePage")
    public String getRepayToDatePage(Model model, String ajaxData,String outFlag) throws Exception {
        ActionContext.initialize(request, response);
        String pactWarning = "0";
        String repayWarning = "0";
        Logger logger = LoggerFactory.getLogger(MfBusFincAppController.class);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        CodeUtils codeUtils = new CodeUtils();
        JSONArray kindTypeJsonArray = codeUtils.getJSONArrayByKeyName("KIND_NO");
        this.getHttpRequest().setAttribute("kindTypeJsonArray", kindTypeJsonArray);
        try {
            CuslendWarning cuslendWarning1 = new CuslendWarning();
            cuslendWarning1.setCuslendWarnNo("LOAN_TO_DATE_WARN");// 合同到期预警
            cuslendWarning1.setFlag("1");
            cuslendWarning1 = msgConfInterfaceFeign.getByIdAndFlag(cuslendWarning1);
            if (null != cuslendWarning1) {
                pactWarning = String.valueOf(cuslendWarning1.getCuslendDays());
            }
            CuslendWarning cuslendWarning2 = new CuslendWarning();
            cuslendWarning2.setCuslendWarnNo("REPAY_TO_DATE_WARN");// 还款到期预警
            cuslendWarning2.setFlag("1");
            cuslendWarning2 = msgConfInterfaceFeign.getByIdAndFlag(cuslendWarning2);
            if (null != cuslendWarning2) {
                repayWarning = String.valueOf(cuslendWarning2.getCuslendDays());
            }
            model.addAttribute("cuslendWarning1", cuslendWarning1);
            model.addAttribute("cuslendWarning2", cuslendWarning2);
        } catch (Exception e) {
            logger.error("获取预警信息出错", e);
        }
        model.addAttribute("pactWarning", pactWarning);
        model.addAttribute("repayWarning", repayWarning);
        model.addAttribute("outFlag", outFlag);
        model.addAttribute("query", "");
        return "/component/pact/repayplan/MfRepayToDate_List";

    }

    /**
     * 还款到期列表数据请求
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getRepayToDateAjax")
    @ResponseBody
    public Map<String, Object> getRepayToDateAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                  String ajaxData,Ipage ipage) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincAndRepay fincAndRepay = new MfBusFincAndRepay();
        String pactWarning = request.getParameter("pactWarning");// 提前几天预警
        String repayWarning = request.getParameter("repayWarning");// 提前几天预警
        String warningType = request.getParameter("warningType");// 预警类型 0 还款
        // 1合同
        String scopeType = "0";
        // 根据预警天数计算---合同结束日期
        String pactWarningDate = DateUtil.getDate();
        if (StringUtil.isNotEmpty(pactWarningDate)) {
            pactWarningDate = DateUtil.addByDay(Integer.parseInt(pactWarning));
        }
        String repayWarningDate = DateUtil.getDate();
        // 根据预警天数计算---还款结束日期
        if (StringUtil.isNotEmpty(repayWarningDate)) {
            repayWarningDate = DateUtil.addByDay(Integer.parseInt(repayWarning));
        }
        try {
            // 取出ajax数据
            Gson gson = new Gson();
            JSONArray jsonArray = gson.fromJson(ajaxData, JSONArray.class);
            if (jsonArray.get(0) instanceof JSONArray) {
                JSONArray jsonArraySub = jsonArray.getJSONArray(0);
                for (int i = 0; i < jsonArraySub.size(); i++) {
                    JSONObject jsonObj = (JSONObject) jsonArraySub.get(i);
                    if ("scope".equals(jsonObj.get("condition"))
                            && StringUtil.isNotEmpty((String) jsonObj.get("value"))) {
                        scopeType = (String) jsonObj.get("value");
                        if ("0".equals(scopeType)) {// 全部
                            scopeType = "0";
                        } else if ("1".equals(scopeType)) {// 合同到期
                            scopeType = "1";
                            fincAndRepay.setPactBeginDate(DateUtil.getDate());
                            fincAndRepay.setPactEndDate(pactWarningDate);
                        } else if ("2".equals(scopeType)) {// 还款到期
                            scopeType = "2";
                            fincAndRepay.setPlanBeginDate(DateUtil.getDate());
                            fincAndRepay.setPlanEndDate(repayWarningDate);
                        }else {
                        }
                    }
                }
            } else if (StringUtil.isNotEmpty(warningType)) {// 预警页面跳转过来
                if ("0".equals(warningType)) {// 还款到期
                    scopeType = "2";
                    fincAndRepay.setPlanBeginDate(DateUtil.getDate());
                    fincAndRepay.setPlanEndDate(repayWarningDate);
                } else if ("1".equals(warningType)) {// 合同到期
                    scopeType = "1";
                    fincAndRepay.setPactBeginDate(DateUtil.getDate());
                    fincAndRepay.setPactEndDate(pactWarningDate);
                }else {
                }

            }else {
            }
            fincAndRepay.setCustomQuery(ajaxData);// 自定义查询参数赋值
            fincAndRepay.setCriteriaList(fincAndRepay, ajaxData);// 我的筛选
            fincAndRepay.setCustomSorts(ajaxData);
            // this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("mfBusFincAndRepay", fincAndRepay);
            paramMap.put("scopeType", scopeType);
            paramMap.put("endDate", pactWarningDate);


            ipage.setParams(this.setIpageParams("paramMap", paramMap));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.getRepayToDateByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            // logger.error("获取还款到期列表失败", e);
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取还款到期列表失败"));
        }
        return dataMap;
    }

    /**
     * 还款逾期列表打开页面请求
     *
     * @return
     */
    @RequestMapping(value = "/getRepayOverDatePage")
    public String getRepayOverDatePage(Model model, String ajaxData) {
        ActionContext.initialize(request, response);
        String pactWarning = "0";
        String repayWarning = "0";
        try {
            CuslendWarning cuslendWarning1 = new CuslendWarning();
            cuslendWarning1.setCuslendWarnNo("LOAN_OVER_DATE_WARN");// 合同逾期预警
            cuslendWarning1.setFlag("1");
            cuslendWarning1 = msgConfInterfaceFeign.getByIdAndFlag(cuslendWarning1);
            if (null != cuslendWarning1) {
                pactWarning = String.valueOf(cuslendWarning1.getCuslendDays());
            }

            CuslendWarning cuslendWarning2 = new CuslendWarning();
            cuslendWarning2.setCuslendWarnNo("REPAY_OVER_DATE_WARN");// 还款逾期预警
            cuslendWarning2.setFlag("1");
            cuslendWarning2 = msgConfInterfaceFeign.getByIdAndFlag(cuslendWarning2);
            if (null != cuslendWarning2) {
                repayWarning = String.valueOf(cuslendWarning2.getCuslendDays());
            }

            model.addAttribute("cuslendWarning1", cuslendWarning1);
            model.addAttribute("cuslendWarning2", cuslendWarning2);
        } catch (Exception e) {
            // logger.error("获取预警信息出错", e);
        }
        model.addAttribute("pactWarning", pactWarning);
        model.addAttribute("repayWarning", repayWarning);
        model.addAttribute("query", "");
        return "/component/pact/repayplan/MfRepayOverDate_List";

    }

    /**
     * 还款逾期列表数据请求
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getRepayOverDateAjax")
    @ResponseBody
    public Map<String, Object> getRepayOverDateAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                    String ajaxData,Ipage ipage) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincAndRepay fincAndRepay = new MfBusFincAndRepay();
        String pactWarning = request.getParameter("pactWarning");// 延后几天提醒
        String repayWarning = request.getParameter("repayWarning");// 延后几天提醒
        String warningType = request.getParameter("warningType");// 提醒类型 0 还款
        // 1合同

        String scopeType = "0";
        // 根据预警天数计算---合同结束日期
        String pactWarningDate = DateUtil.getDate();
        if (StringUtil.isNotEmpty(pactWarningDate)) {
            pactWarningDate = DateUtil.addByDay(-Integer.parseInt(pactWarning));
        }
        String repayWarningDate = DateUtil.getDate();
        // 根据预警天数计算---还款结束日期
        if (StringUtil.isNotEmpty(repayWarningDate)) {
            repayWarningDate = DateUtil.addByDay(-Integer.parseInt(repayWarning));
        }
        try {
            // 取出ajax数据
            Gson gson = new Gson();
            JSONArray jsonArray = gson.fromJson(ajaxData, JSONArray.class);
            if (jsonArray.get(0) instanceof JSONArray) {
                JSONArray jsonArraySub = jsonArray.getJSONArray(0);
                for (int i = 0; i < jsonArraySub.size(); i++) {
                    JSONObject jsonObj = (JSONObject) jsonArraySub.get(i);
                    if ("scope".equals(jsonObj.get("condition"))
                            && StringUtil.isNotEmpty((String) jsonObj.get("value"))) {
                        String scope = (String) jsonObj.get("value");
                        if ("0".equals(scope)) {// 全部
                            scopeType = "0";
                        } else if ("1".equals(scope)) {// 合同逾期
                            scopeType = "1";
                            fincAndRepay.setPactEndDate(pactWarningDate);
                        } else if ("2".equals(scope)) {// 还款逾期
                            scopeType = "2";
                            fincAndRepay.setPlanEndDate(repayWarningDate);
                        }else {
                        }
                    }
                }
            } else if (StringUtil.isNotEmpty(warningType)) {// 预警页面跳转过来
                if ("0".equals(warningType)) {// 还款逾期
                    scopeType = "2";
                    fincAndRepay.setPlanBeginDate(DateUtil.getDate());
                    fincAndRepay.setPlanEndDate(repayWarningDate);
                } else if ("1".equals(warningType)) {// 合同逾期
                    scopeType = "1";
                    fincAndRepay.setPactBeginDate(DateUtil.getDate());
                    fincAndRepay.setPactEndDate(pactWarningDate);
                }else {
                }

            }else {
            }
            fincAndRepay.setCustomQuery(ajaxData);// 自定义查询参数赋值
            if ("0".equals(scopeType)) {
                fincAndRepay.setCriteriaList(fincAndRepay, ajaxData);// 我的筛选
            }
            fincAndRepay.setCustomSorts(ajaxData);
            // this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("mfBusFincAndRepay",fincAndRepay);
            paramMap.put("scopeType",scopeType);
            paramMap.put("endDate",pactWarningDate);
            ipage.setParams(this.setIpageParams("paramMap", paramMap));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.getRepayOverDateByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
             * dataMap.put("tableData",tableHtml);
             */
        } catch (Exception e) {
            // logger.error("获取还款到期列表失败", e);
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取还款逾期列表失败"));
        }
        return dataMap;
    }

    /**
     * 方法描述： 获取放款申请表单信息
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-7-28 上午10:20:22
     */
    @RequestMapping(value = "/getFincAppDetailFormAjax")
    @ResponseBody
    public Map<String, Object> getFincAppDetailFormAjax(String fincId, String formId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 判断详情页面还款按钮的显隐
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        // mfBusFincApp = mfBusFincAppFeign.getMfBusFincAppByExten(mfBusFincApp);
        mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(mfBusFincApp.getPactId());
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(mfBusPact.getAppId());
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);

        //formId改为读取mf_kind_form表中配置的表单编号（这样就能满足不同产品详情表单不同的场景）
        MfKindForm mfKindForm =prdctInterfaceFeign.getMfKindForm(mfBusApply.getKindNo(),WKF_NODE.putout_apply.getNodeNo());
        if(mfKindForm!=null){
            if(StringUtil.isNotEmpty(mfKindForm.getShowModel())){
                formId = mfKindForm.getShowModel();
            }
        }
        if(StringUtil.isEmpty(formId)){
            dataMap.put("flag","error");
            dataMap.put("msg",MessageEnum.NOT_FORM_EMPTY.getMessage("放款详情表单编号formId"));
        }else {
            FormData formfincapp0002 = formService.getFormData(formId);// 放款申请信息
            //获得授信信息
            Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(mfBusFincApp.getAppId());
            if (!creditData.isEmpty()) {
                getObjValue(formfincapp0002, creditData);
                mfBusPact.setCreditPactNo((String) creditData.get("creditPactNo"));
            }

            getObjValue(formfincapp0002, mfBusPact);
            mfBusFincApp.setPenaltyCalRateFinc(mfBusApply.getPenaltyCalRateFinc());
            getObjValue(formfincapp0002, mfBusFincApp);
            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
            this.changeFormProperty(formfincapp0002, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formfincapp0002, "overRate", "unit", rateUnit);
            this.changeFormProperty(formfincapp0002, "cmpdRate", "unit", rateUnit);
            //判断表单信息是否允许编辑
//		request.setAttribute("ifBizManger", "3");
            String query = cusInterfaceFeign.validateCusFormModify(mfBusPact.getCusNo(), mfBusPact.getAppId(), BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
            //factor传的是"",到web端就变成了null
            if (query == null) {
                query = "";
            }
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String htmlStr = jsonFormUtil.getJsonStr(formfincapp0002, "propertySeeTag", query);
            dataMap.put("htmlStr", htmlStr);
            dataMap.put("flag","success");
        }
        return dataMap;
    }

    /**
     *
     * 方法描述： 跳转到在履行的借据的贷后检查状态列表
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2017-8-2 上午11:41:05
     */
    @RequestMapping(value = "/getExamineStateListPage")
    public String getExamineStateListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义的筛选组件，从数据字典中的缓存中获取
        JSONArray riskLevelJsonArray = new CodeUtils().getJSONArrayByKeyName("EXAM_RISK_LEVEL");
        model.addAttribute("riskLevelJsonArray", riskLevelJsonArray);

        JSONArray fincExamineStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_EXAMINE_STATS");
        model.addAttribute("fincExamineStsJsonArray", fincExamineStsJsonArray);

        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_ExamineStateList";
    }

    /**
     *
     * 方法描述： 跳转到在履行的借据的贷后检查状态列表
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2017-8-2 上午11:41:05
     */
    @RequestMapping(value = "/getExamineStateForCusListPage")
    public String getExamineStateForCusListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义的筛选组件，从数据字典中的缓存中获取
        JSONArray riskLevelJsonArray = new CodeUtils().getJSONArrayByKeyName("EXAM_RISK_LEVEL");
        model.addAttribute("riskLevelJsonArray", riskLevelJsonArray);

        JSONArray fincExamineStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_EXAMINE_STATS");
        model.addAttribute("fincExamineStsJsonArray", fincExamineStsJsonArray);

        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_ExamineStateForCusList";
    }

    /***
     * 查询在履行的借据的贷后检查状态列表
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findExamineStateByPageAjax")
    @ResponseBody
    public Map<String, Object> findExamineStateByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                          String tableType, String ajaxData,Ipage ipage) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            // this.getRoleConditions(mfBusFincApp,"1000000001");//记录级权限控制方法
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.findExamineStateByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
             * dataMap.put("tableData",tableHtml);
             */
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 查询在履行的借据的贷后检查状态列表
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findExamineStateForCusByPageAjax")
    @ResponseBody
    public Map<String, Object> findExamineStateForCusByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                                String tableType, String ajaxData,Ipage ipage) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            // this.getRoleConditions(mfBusFincApp,"1000000001");//记录级权限控制方法
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.findExamineStateForCusByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
             * dataMap.put("tableData",tableHtml);
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
     *
     * 方法描述： 跳转计提利息查询页面
     *
     * @return
     * @author 沈浩兵
     * @date 2017-8-17 上午11:27:53
     */
    @RequestMapping(value = "/getInterestAccruedListPage")
    public String getInterestAccruedListPage(Model model, String ajaxData) {
        ActionContext.initialize(request, response);
        String queryMonth = DateUtil.getDate().substring(0, 6);
        model.addAttribute("queryMonth", queryMonth);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_InterestAccruedList";
    }

    /**
     *
     * 方法描述： 计提利息查询分页列表
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2017-8-17 上午11:36:01
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findInterestAccruedByPageAjax")
    @ResponseBody
    public Map<String, Object> findInterestAccruedByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                             String tableType, String ajaxData, String queryMonth,Ipage ipage) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        InterestAccruedBean interestAccruedBean = new InterestAccruedBean();
        try {
            interestAccruedBean.setCustomSorts(ajaxData);// 自定义排序参数赋值
            interestAccruedBean.setCustomQuery(ajaxData);// 自定义查询参数赋值
            interestAccruedBean.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            // this.getRoleConditions(mfBusFincApp,"1000000001");//记录级权限控制方法
            interestAccruedBean.setQueryMonth(queryMonth);
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("interestAccruedBean", interestAccruedBean));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.findInterestAccruedByPage(ipage);
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
     *
     * 方法描述： 获得放款历史列表html
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2017-8-22 上午11:12:20
     */
    @RequestMapping(value = "/getPutOutHisListAjax")
    @ResponseBody
    public Map<String, Object> getPutOutHisListAjax(String pactId,String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setPactId(pactId);
        JsonTableUtil jtu = new JsonTableUtil();
        List<MfBusFincApp> list = mfBusFincAppFeign.getPutOutHisList(mfBusFincApp);

        for (MfBusFincApp mfBusFincApp1: list) {
            mfBusFincApp1.setExt10("放款打印");
        }
        //合同信息
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        //表单编号
        //formId改为读取mf_kind_form表中配置的表单编号（这样就能满足不同产品详情表单不同的场景）
        MfKindForm mfKindForm =prdctInterfaceFeign.getMfKindForm(mfBusPact.getKindNo(),WKF_NODE.putout_apply.getNodeNo());
        if(mfKindForm!=null){
            if(StringUtil.isNotEmpty(mfKindForm.getListModel())){
                tableId = mfKindForm.getListModel();
            }
        }
        if(StringUtil.isEmpty(tableId)){
            dataMap.put("flag","error");
            dataMap.put("msg",MessageEnum.NOT_FORM_EMPTY.getMessage("放款历史列表编号tableId"));
        }else {
            String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
            dataMap.put("tableData", tableHtml);
            String putoutSts = mfBusFincAppFeign.getPactPutoutSts(pactId);
            dataMap.put("putoutSts", putoutSts);
            dataMap.put("flag","success");
            dataMap.put("fincId", list.size()==0?"":list.get(0).getFincId());
        }
        return dataMap;
    }

    /**
     *
     * 方法描述： 获得放款详情
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2017-8-22 上午11:50:33
     */
    @RequestMapping(value = "/getPutoutDetailById")
    public String getPutoutDetailById(Model model, String pactId, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        // 获取合同数据
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact = pactInterfaceFeign.getById(mfBusPact);
        mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        String formId = prdctInterfaceFeign.getFormId(mfBusFincApp.getKindNo(), WKF_NODE.putout_apply, null, null,User.getRegNo(request));
        FormData formfincapp0002 = formService.getFormData(formId);
        getFormValue(formfincapp0002);
        getObjValue(formfincapp0002, mfBusPact);
        MfBusFincAppHis mfBusFincAppHis = new MfBusFincAppHis();
        mfBusFincAppHis.setFincId(fincId);
        List<MfBusFincAppHis> mfBusFincAppHislist = mfBusFincAppFeign.getFincListByFincId(mfBusFincAppHis);
        if (mfBusFincAppHislist != null && mfBusFincAppHislist.size() > 0) {
            mfBusFincAppHis = mfBusFincAppHislist.get(0);
            getObjValue(formfincapp0002, mfBusFincAppHis);
        } else {
            getObjValue(formfincapp0002, mfBusFincApp);
        }
        // 设置表单元素不可编辑
        FormActive[] list = formfincapp0002.getFormActives();
        for (int i = 0; i < list.length; i++) {
            FormActive formActive = list[i];
            formActive.setReadonly("1");
        }
        MfFincRepayDetail mfFincRepayDetail = new MfFincRepayDetail();
        mfFincRepayDetail.setFincId(fincId);
        List<MfFincRepayDetail> mfFincRepayDetailList = mfFincRepayDetailFeign.getList(mfFincRepayDetail);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("mfFincRepayDetailList", mfFincRepayDetailList);
        model.addAttribute("formfincapp0002", formfincapp0002);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_PutoutDetail";
    }

    /**
     *
     * 方法描述： 放款历史下滑展示详情
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2017-8-26 上午8:51:46
     */
    @RequestMapping(value = "/listShowDetailAjax")
    @ResponseBody
    public Map<String, Object> listShowDetailAjax(String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        // request.setAttribute("ifBizManger","3");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 判断详情页面还款按钮的显隐
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
        //合同信息
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(mfBusFincApp.getPactId());
        mfBusPact = mfBusPactFeign.getById(mfBusPact);

        String formId = "fincapp0002";
        //formId改为读取mf_kind_form表中配置的表单编号（这样就能满足不同产品详情表单不同的场景）
        MfKindForm mfKindForm =prdctInterfaceFeign.getMfKindForm(mfBusPact.getKindNo(),WKF_NODE.putout_apply.getNodeNo());
        if(mfKindForm!=null){
            if(StringUtil.isNotEmpty(mfKindForm.getShowModel())){
                formId = mfKindForm.getShowModel();
            }
        }
        if(StringUtil.isEmpty(formId)){
            dataMap.put("flag","error");
            dataMap.put("msg",MessageEnum.NOT_FORM_EMPTY.getMessage("放款详情表单编号formId"));
        }else {
            FormData formfincapp0002 = formService.getFormData(formId);// 放款申请信息
            //获得授信信息
            Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(mfBusFincApp.getAppId());
            if (!creditData.isEmpty()) {
                creditData.put("creditPactAmt", MathExtend.moneyStr(creditData.get("creditPactAmt") + ""));
                creditData.put("projectAmt", MathExtend.moneyStr(creditData.get("projectAmt") + ""));
                getObjValue(formfincapp0002, creditData);
            }

            //处理逾期利率值和单位展示,
            MfBusAppPenaltyMain mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
            mfBusAppPenaltyMain.setAppId(mfBusPact.getAppId());
            mfBusAppPenaltyMain.setKindNo(mfBusPact.getKindNo());
            mfBusAppPenaltyMain.setPenaltyType("1");
            mfBusAppPenaltyMain = mfBusAppPenaltyMainFeign.getById(mfBusAppPenaltyMain);
            if (mfBusAppPenaltyMain != null) {
                String penaltyReceiveType = mfBusAppPenaltyMain.getPenaltyReceiveType();
                mfBusPact.setPenaltyCalRateFinc(mfBusAppPenaltyMain.getPenaltyReceiveValue());
                if ("1".equals(penaltyReceiveType)) {
                    this.changeFormProperty(formfincapp0002, "penaltyCalRateFinc", "unit", "%");
                } else {
                    this.changeFormProperty(formfincapp0002, "penaltyCalRateFinc", "unit", "元");
                }
            }
            //提前结清约金利率值和单位展示
            mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
            mfBusAppPenaltyMain.setAppId(mfBusPact.getAppId());
            mfBusAppPenaltyMain.setKindNo(mfBusPact.getKindNo());
            mfBusAppPenaltyMain.setPenaltyType("2");
            mfBusAppPenaltyMain = mfBusAppPenaltyMainFeign.getById(mfBusAppPenaltyMain);
            if (mfBusAppPenaltyMain != null) {
                String penaltyReceiveType = mfBusAppPenaltyMain.getPenaltyReceiveType();
                mfBusPact.setEarlyRepayRate(mfBusAppPenaltyMain.getPenaltyReceiveValue());
                if ("1".equals(penaltyReceiveType)) {
                    this.changeFormProperty(formfincapp0002, "earlyRepayRate", "unit", "%");
                } else {
                    this.changeFormProperty(formfincapp0002, "earlyRepayRate", "unit", "元");
                }
            }

            getObjValue(formfincapp0002, mfBusPact);
            getObjValue(formfincapp0002, mfBusFincApp);
            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
            this.changeFormProperty(formfincapp0002, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formfincapp0002, "overRate", "unit", rateUnit);
            this.changeFormProperty(formfincapp0002, "cmpdRate", "unit", rateUnit);
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String htmlStr = jsonFormUtil.getJsonStr(formfincapp0002, "propertySeeTag", "");
            dataMap.put("formHtml", htmlStr);
            dataMap.put("flag", "success");
            dataMap.put("formData", mfBusFincApp);
        }
        return dataMap;
    }

    /**
     *
     * 方法描述： 跳转至待转账的列表页面
     *
     * @return
     * @author zhs
     * @date 2017-9-11 下午2:57:44
     */
    @RequestMapping(value = "/getNoTransferListPage")
    public String getNoTransferListPage(Model model, String ajaxData) {
        ActionContext.initialize(request, response);
        return "/component/pact/MfBusFincApp_NoTransferList";
    }

    /**
     *
     * 方法描述： 受托支付情况下待转账的列表数据
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2017-9-11 下午5:34:55
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findNoTransferListByPageAjax")
    @ResponseBody
    public Map<String, Object> findNoTransferListByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                            String tableType, String ajaxData,Ipage ipage) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.findNoTransferListByPage(ipage);
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
     *
     * 方法描述： 受托支付情况下转账申请
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2017-9-11 下午5:39:27
     */
    @RequestMapping(value = "/transferAccApply")
    public String transferAccApply(Model model, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);

        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

        MfBusFincAppHis mfBusFincAppHis = new MfBusFincAppHis();
        mfBusFincAppHis.setFincId(fincId);
        List<MfBusFincAppHis> list = mfBusFincAppFeign.getFincListByFincId(mfBusFincAppHis);
        if (list != null && list.size() > 0) {
            mfBusFincAppHis = list.get(0);
            PropertyUtils.copyProperties(mfBusFincApp, mfBusFincAppHis);
        }
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(mfBusFincApp.getAppId());
        mfBusPact = pactInterfaceFeign.getById(mfBusPact);
        // 通过借据号获取子借据号
        MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
        mfBusFincAppChild.setFincId(mfBusFincApp.getFincId());// 借据id
        mfBusFincAppChild.setPutoutCount(mfBusFincApp.getPutoutCount());// 放款次数
        mfBusFincAppChild = pactInterfaceFeign.getMfBusFincAppChildByInfo(mfBusFincAppChild);

        FormData formfincapp0005 = formService.getFormData("finctransferaccapply");

        mfBusPact.setPayMethod(mfBusFincApp.getPayMethod());
        mfBusPact.setIfImmediateTransfer(mfBusFincApp.getIfImmediateTransfer());
        mfBusPact.setBankAccId(mfBusFincApp.getBankAccId());
        mfBusPact.setCollectAccId(mfBusFincApp.getCollectAccId());
        mfBusPact.setRepayAccId(mfBusFincApp.getRepayAccId());
        mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);

        getObjValue(formfincapp0005, mfBusPact);

        mfBusFincAppChild.setBankAccId(mfBusPact.getBankAccId());
        mfBusFincAppChild.setCollectAccId(mfBusPact.getCollectAccId());
        mfBusFincAppChild.setRepayAccId(mfBusPact.getRepayAccId());

        getObjValue(formfincapp0005, mfBusFincAppChild);
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusFincApp.getAppId());
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        // 放款申请表单中的还款方式修改时应收产品设置中的还款方式控制
        JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
        JSONArray repayDateSetMap = prdctInterfaceFeign.getRepayRulesMap(mfBusPact);
        for (int i = 0; i < repayDateSetMap.size(); i++) {
            String repayDateSet = repayDateSetMap.getJSONObject(i).getString("id").split("_")[1];
            if (StringUtil.isNotEmpty(repayDateSet) && StringUtil.isNotEmpty(mfBusAppKind.getRepayDateSet())) {
                if (repayDateSet.equals(mfBusAppKind.getRepayDateSet())) {
                    mfBusAppKind.setRepayDateSet(repayDateSetMap.getJSONObject(i).getString("id"));
                    break;
                }
            }
        }

        JSONObject json = new JSONObject();
        json.put("repayTypeMap", repayTypeMap);
        json.put("repayDateSetMap", repayDateSetMap);
        this.changeFormProperty(formfincapp0005, "repayDateSet", "initValue", mfBusAppKind.getRepayDateSet());
        this.changeFormProperty(formfincapp0005, "repayDateDef", "initValue", mfBusAppKind.getRepayDateDef());

        // 客户的账户列表
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
        mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);
        mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
        List<MfCusBankAccManage> mfCusBankAccManageList = cusInterfaceFeign
                .getMfCusBankAccListByCusNo(mfCusBankAccManage);
        JSONArray bankArray = JSONArray.fromObject(mfCusBankAccManageList);
        for (int i = 0; i < bankArray.size(); i++) {
            bankArray.getJSONObject(i).put("name",
                    bankArray.getJSONObject(i).getString("accountNo").replaceAll("([\\d]{4})(?=\\d)", "$1 "));
        }
        json.put("bankArray", bankArray);
        // 封装阳光银行项目启用标记
        String mfToSunCBS = PropertiesUtil.getWebServiceProperty("mftf_to_cbs");
        json.put("mfToSunCBS", mfToSunCBS);
        String ajaxData = json.toString();
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("mfBusFincAppChild", mfBusFincAppChild);
        model.addAttribute("mfCusBankAccManage", mfCusBankAccManage);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("formfincapp0005", formfincapp0005);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_TransferAccApply";
    }

    /**
     * 方法描述： 受托支付情况下转账申请
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-9-23 下午6:06:12
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/insertTransferAccAjax")
    @ResponseBody
    public Map<String, Object> insertTransferAccAjax(String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map map = getMapByJson(ajaxData);
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setAppId(appId);
            mfBusPact = pactInterfaceFeign.getById(mfBusPact);
            FormData formfincapp0003 = formService.getFormData((String) map.get("formId"));
            getFormValue(formfincapp0003, map);
            if (this.validateFormData(formfincapp0003)) {
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                setObjValue(formfincapp0003, mfBusFincApp);
                mfBusFincApp.setCusNo(mfBusPact.getCusNo());
                mfBusFincApp.setRateType(mfBusPact.getRateType());
                mfBusFincApp.setAppId(appId);
                mfBusFincAppFeign.insertTransferAcc(mfBusFincApp, map);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 获取在履行借据数据列表
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-10-19 下午2:14:14
     */
    @RequestMapping(value = "/getMultiBusList")
    public String getMultiBusList(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setCusNo(cusNo);
        List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getMultiBusList(mfBusFincApp);

        // 借据金额总额、借据余额总和
        Double putoutAmt = 0d;
        Double loanBal = 0d;
        for (MfBusFincApp mbfa : mfBusFincAppList) {
            putoutAmt = DataUtil.add(putoutAmt, mbfa.getPutoutAmt(), 20);
            loanBal = DataUtil.add(loanBal, mbfa.getLoanBal(), 20);
        }
        putoutAmt = DataUtil.retainDigit(putoutAmt, 2);
        loanBal = DataUtil.retainDigit(loanBal, 2);

        MfBusFincApp mfBusFincAppTmp1 = new MfBusFincApp();
        mfBusFincAppTmp1.setPutoutAmt(putoutAmt);
        mfBusFincAppTmp1.setLoanBal(loanBal);
        mfBusFincAppTmp1.setFincShowId("总金额");
        mfBusFincAppList.add(mfBusFincAppTmp1);


        model.addAttribute("mfBusFincAppList", mfBusFincAppList);
        model.addAttribute("busEntrance", "finc");
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_multiBusList";
    }


    /**
     * @param model
     * @param cusNo
     * @return
     * @throws Exception
     * @desc 根据客户号去查询该客户已完结的借据
     * @Author zkq
     * @date 20190624
     */
    @RequestMapping(value = "/getMultiBusFinishList")
    public String getMultiBusFinishList(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setCusNo(cusNo);
        mfBusFincApp.setQueryType("1");
        List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getMultiBusFinishList(mfBusFincApp);
        if(mfBusFincAppList != null && mfBusFincAppList.size() > 0){
            // 借据金额总额、借据余额总和
            Double putoutAmt = 0d;
            Double loanBal = 0d;
            for (MfBusFincApp mbfa : mfBusFincAppList) {
                putoutAmt = DataUtil.add(putoutAmt, mbfa.getPutoutAmt(), 20);
                loanBal = DataUtil.add(loanBal, mbfa.getLoanBal(), 20);
            }
            putoutAmt = DataUtil.retainDigit(putoutAmt, 2);
            loanBal = DataUtil.retainDigit(loanBal, 2);

            MfBusFincApp mfBusFincAppTmp1 = new MfBusFincApp();
            mfBusFincAppTmp1.setPutoutAmt(putoutAmt);
            mfBusFincAppTmp1.setLoanBal(loanBal);
            mfBusFincAppTmp1.setTermShow("总金额");
            mfBusFincAppList.add(mfBusFincAppTmp1);
        }
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr( "tablefincappFinish0001", "tableTag", mfBusFincAppList, null, true);
        model.addAttribute("mfBusFincAppList", mfBusFincAppList);
        model.addAttribute("mfBusFincAppListSize", mfBusFincAppList.size());
        model.addAttribute("tableHtml", tableHtml);

        mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setCusNo(cusNo);
        mfBusFincApp.setQueryType("2");
        List<MfBusFincApp> mfBusFincAppProjectList = mfBusFincAppFeign.getMultiBusFinishList(mfBusFincApp);
        if(mfBusFincAppProjectList != null && mfBusFincAppProjectList.size() > 0){
            // 借据金额总额、借据余额总和
            Double putoutAmt = 0d;
            Double loanBal = 0d;
            for (MfBusFincApp mbfa : mfBusFincAppProjectList) {
                putoutAmt = DataUtil.add(putoutAmt, mbfa.getPutoutAmt(), 20);
                loanBal = DataUtil.add(loanBal, mbfa.getLoanBal(), 20);
            }
            putoutAmt = DataUtil.retainDigit(putoutAmt, 2);
            loanBal = DataUtil.retainDigit(loanBal, 2);

            MfBusFincApp mfBusFincAppTmp1 = new MfBusFincApp();
            mfBusFincAppTmp1.setPutoutAmt(putoutAmt);
            mfBusFincAppTmp1.setBreedName("总金额");
            mfBusFincAppProjectList.add(mfBusFincAppTmp1);
        }
        String projectTableHtml = jtu.getJsonStr( "tablefincappFinish_GCDB", "tableTag", mfBusFincAppProjectList, null, true);
        model.addAttribute("mfBusFincAppProjectList", mfBusFincAppProjectList);
        model.addAttribute("mfBusFincAppProjectListSize", mfBusFincAppProjectList.size());
        model.addAttribute("projectTableHtml", projectTableHtml);


        model.addAttribute("busEntrance", "finc");
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_multiBusFinishList";
    }







    /**
     *
     * 方法描述： 跳转至放款审批状态查询列表页面（查询页面--业务）
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2017-10-17 下午2:53:01
     */
    @RequestMapping(value = "/getFincApprovalStsListPage")
    public String getFincApprovalStsListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        CodeUtils codeUtils = new CodeUtils();
        JSONArray appStsJsonArray = codeUtils.getJSONArrayByKeyName("FINC_STS");
        model.addAttribute("fincStsJsonArray", appStsJsonArray);
        JSONArray kindTypeJsonArray = codeUtils.getJSONArrayByKeyName("KIND_NO");
        model.addAttribute("kindTypeJsonArray", kindTypeJsonArray);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_FincApprovalStsList";
    }

    /**
     *
     * 方法描述： 获取放款审批状态查询（查询页面--业务）
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2017-10-17 下午3:26:13
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findFincApprovalStsListByPageAjax")
    @ResponseBody
    public Map<String, Object> findFincApprovalStsListByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.findFincApprovalStsListByPage(ipage);
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
     * 方法描述： 获取借据头部信息
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-10-20 上午10:39:45
     */
    @RequestMapping(value = "/getFincHeadInfoAjax")
    @ResponseBody
    public Map<String, Object> getFincHeadInfoAjax(String appId, String pactId, String fincId ) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 合同信息
        MfBusPact mfBusPact = new MfBusPact();
        //mfBusPact.setAppId(appId);
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        // 判断详情页面还款按钮的显隐
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);

        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        dataMap.put("rateUnit", rateUnit);
        // 金额格式化
        mfBusPact.setFincAmt(MathExtend.divide(String.valueOf(mfBusFincApp.getPutoutAmt()), "10000", mfBusFincApp.getPutoutAmt()==0?2:6));
        dataMap.put("pactBal", MathExtend.divide(String.valueOf(mfBusFincApp.getLoanBal()), "10000", mfBusFincApp.getLoanBal()==0?2:6));
        // 借据逾期天数
        int overDays = 0;
        dataMap.put("overDays", overDays);
        if (BizPubParm.OVERDUE_STS_1.equals(mfBusFincApp.getOverdueSts())) {
            MfRepayPlan mfRepayPlan = new MfRepayPlan();
            mfRepayPlan.setFincId(fincId);
            mfRepayPlan.setOutFlag(BizPubParm.OUT_FLAG_3);
            List<MfRepayPlan> mfRepayPlanList = calcRepayPlanInterfaceFeign.getMfBusRepayPlanListOnlyOne(mfRepayPlan);
            if (mfRepayPlanList != null && mfRepayPlanList.size()>0) {
                overDays = DateUtil.getIntervalDays(mfRepayPlanList.get(0).getPlanEndDate(),
                        DateUtil.getDate("yyyyMMdd"));
                if (overDays >= 0) {
                    dataMap.put("overDays", overDays);
                }
            }
        }
        dataMap.put("mfBusPact", mfBusPact);
        // 业务申请信息
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
        String busModel = mfBusApply.getBusModel();
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusPact.getAppId());
        mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
        dataMap.put("fincRate", MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusApply.getFincRate(),
                Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
        dataMap.put("mfBusApply", mfBusApply);
        // 关联企业：核心企业、资金机构、仓储机构等信息
        String wareHouseCusNo = "", coreCusNo = "", fundCusNo = "";
        if (busModel.equals(BizPubParm.BUS_MODEL_1) || busModel.equals(BizPubParm.BUS_MODEL_2)) {// 动产质押、仓单模式下登记
            // 仓储机构
            if (!StringUtil.isEmpty(mfBusApply.getCusNoWarehouse())) {// 仓储机构信息
                wareHouseCusNo = mfBusApply.getCusNoWarehouse();// 已登记
            } else {
                wareHouseCusNo = "0";// 未登记
            }
        } else if (busModel.equals(BizPubParm.BUS_MODEL_4)) {// 保兑仓模式下登记核心企业
            if (!StringUtil.isEmpty(mfBusApply.getCusNoCore())) {// 核心企业信息
                coreCusNo = mfBusApply.getCusNoCore();// 已登记
            } else {
                coreCusNo = "0";// 未登记
            }
        } else if (busModel.equals(BizPubParm.BUS_MODEL_5)) {// 保理（应收账款）模式下登记核心企业、资金机构
            if (!StringUtil.isEmpty(mfBusApply.getCusNoCore())) {// 核心企业信息
                coreCusNo = mfBusApply.getCusNoCore();// 已登记
            } else {
                coreCusNo = "0";// 未登记
            }
            if (!StringUtil.isEmpty(mfBusApply.getCusNoFund())) {// 资金机构信息
                fundCusNo = mfBusApply.getCusNoFund();// 已登记
            } else {
                fundCusNo = "0";// 未登记
            }
        }else {
        }
        dataMap.put("wareHouseCusNo", wareHouseCusNo);
        dataMap.put("coreCusNo", coreCusNo);
        dataMap.put("fundCusNo", fundCusNo);
        // 处理多业务
        MfBusApply mfBusApplyTmp = new MfBusApply();
        mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
        List<MfBusApply> mfBusApplyList = appInterfaceFeign.getMultiBusList(mfBusApplyTmp);
        dataMap.put("moreApplyCount", mfBusApplyList.size());

        MfBusFincApp mfBusFincAppTmp = new MfBusFincApp();
        mfBusFincAppTmp.setCusNo(mfBusApply.getCusNo());
        List<MfBusFincApp> mfBusFincApps = pactInterfaceFeign.getMultiBusList(mfBusFincAppTmp);;
        dataMap.put("moreFincCount", mfBusFincApps.size());

        MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
        mfVouAfterTrack.setCusNo(mfBusApply.getCusNo());
        List<MfVouAfterTrack> mfVouAfterTrackProjectList = mfVouAfterTrackFeign.getMultiBusList(mfVouAfterTrack);
        dataMap.put("moreVouAfterCount", mfVouAfterTrackProjectList.size());

//        MfBusPact mfBusPactTmp = new MfBusPact();
//        mfBusPactTmp.setCusNo(mfBusApply.getCusNo());
//        List<MfBusPact> mfBusPacts = pactInterfaceFeign.getMultiBusList(mfBusPactTmp);
        mfBusFincAppTmp.setFincSts("('5','6','7')");
        mfBusFincApps = pactInterfaceFeign.getMultiBusList(mfBusFincAppTmp);
        dataMap.put("morePactCount", mfBusFincApps.size());

        MfAssureInfo mfAssureInfo = new MfAssureInfo();
        mfAssureInfo.setAssureNo(mfBusApply.getCusNo());
        List<MfBusApplyAssureInfo> mfBusApplyAssureInfos = assureInterfaceFeign.getMultiBusList(mfAssureInfo);
        dataMap.put("moreAssureCount", mfBusApplyAssureInfos.size());

        List<MfBusFincApp> mfBusFincAppFinishs = pactInterfaceFeign.getMultiBusListFinish(mfBusFincAppTmp);
        dataMap.put("moreFincFinishCount", mfBusFincAppFinishs.size());

        //处理已还历史
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        mfRepayHistory.setCusNo(mfBusApply.getCusNo());
        List<MfRepayHistory> mfRepayHistoryList = pactInterfaceFeign.getRepayHistoryListByCusNo(mfRepayHistory);
        dataMap.put("moreReapyCount", mfRepayHistoryList.size());
        return dataMap;
    }

    /**
     * 今日放款量列表
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getTodayPutOutListAjax")
    @ResponseBody
    public Map<String, Object> getTodayPutOutListAjax(String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            mfBusFincApp.setIntstBeginDate(DateUtil.getDate());
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.getTodayPutOutCountByBeginDate(ipage, mfBusFincApp);
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

    @RequestMapping(value = "/getTodayPutOutListCountPage")
    public String getTodayPutOutListCountPage(Model model) {
        ActionContext.initialize(request, response);
        return "/component/pact/MfBusFincApp_TodayPutOutList";
    }

    /**
     * 待还款金额列表
     *
     * @return
     */
    @RequestMapping(value = "/getCashListPage")
    public String getCashListPage(Model model, String dataType) {
        ActionContext.initialize(request, response);
        model.addAttribute("dataType", dataType);
        return "/component/pact/MfBusFincApp_ListForCash";
    }

    /**
     * 逾期金额列表
     *
     * @return
     */
    @RequestMapping(value = "/getCashListForOverduePage")
    public String getCashListForOverduePage(Model model, String dataType) {
        ActionContext.initialize(request, response);
        model.addAttribute("dataType", dataType);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_OverdueListForCash";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByPageForCashAjax")
    @ResponseBody
    public Map<String, Object> findByPageForCashAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {

            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            String dataType = this.getHttpRequest().getParameter("dataType");
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            if ("repaySumAmt".equals(dataType)) {// 待还款列表
            } else if ("overdueSumAmt".equals(dataType)) {
                mfBusFincApp.setOverdueSts("1");
            }else {
            }
            ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
            ipage = mfBusFincAppFeign.findByPageForCash(ipage);
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
     * 获取电话催收列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPhoneCollectListPage")
    public String getPhoneCollectListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        // 从数据字典中拿到缓存
        CodeUtils codeUtils = new CodeUtils();
        JSONArray jsonArrayByKeyName = codeUtils.getJSONArrayByKeyName("TRACK_TYPE");
        model.addAttribute("jsonArrayByKeyName", jsonArrayByKeyName);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_phoneCollectList";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findPhoneCollectByPageAjax")
    @ResponseBody
    public Map<String, Object> findPhoneCollectByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, Ipage ipage) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfPhoneCollect mfPhoneCollect = new MfPhoneCollect();
        try {
            mfPhoneCollect.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfPhoneCollect.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfPhoneCollect.setCriteriaList(mfPhoneCollect, ajaxData);// 我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfPhoneCollect", mfPhoneCollect));

            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.getPhoneCollect(ipage);
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
     *
     * 方法描述： 拒绝放款保存
     * @return
     * @throws Exception
     * String
     * @author 沈浩兵
     * @date 2018-2-27 下午4:13:47
     */
    @RequestMapping(value = "/disagreeFincAjax")
    @ResponseBody
    public Map<String,Object> disagreeFincAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        FormData formfincapp0001 = formService.getFormData("disagreeBuss");
        getFormValue(formfincapp0001, getMapByJson(ajaxData));
        if(this.validateFormData(formfincapp0001)){
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            setObjValue(formfincapp0001, mfBusFincApp);
            dataMap=mfBusFincAppFeign.doDisagreeFinc(mfBusFincApp);
        }
        return dataMap;
    }

    /**
     *
     * 方法描述： 跳转至账号变更列表页面（查询）
     *
     * @return
     * @author zhs
     * @date 2017-12-12 上午9:19:36
     */
    @RequestMapping(value = "/getAccUpdListPage")
    public String getAccUpdListPage(Model model) {
        ActionContext.initialize(request, response);
        return "/component/pact/MfBusFincApp_AccUpdList";
    }

    /**
     *
     * 方法描述： 账号变更列表（查询）
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2017-12-12 上午9:20:00
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getAccUpdListPageAjax")
    @ResponseBody
    public Map<String, Object> getAccUpdListPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,Ipage ipage) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.getAccUpdListPage(ipage);
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
     *
     * 方法描述： 账号变更页面
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2017-12-12 上午9:21:17
     */
    @RequestMapping(value = "/bankAccUpdate")
    public String bankAccUpdate(Model model, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String id = WaterIdUtil.getWaterId();

        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact = pactInterfaceFeign.getById(mfBusPact);

        FormData formfincapp0005 = formService.getFormData("bankaccupdate");

        mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);
        this.changeFormProperty(formfincapp0005, "repayAccIdNew", "initValue", mfBusPact.getRepayAccId());//用于存未修改的还款账号
        this.changeFormProperty(formfincapp0005, "bankAccIdNew", "initValue", mfBusPact.getBankAccId());//用于存未修改的借款账号
        this.changeFormProperty(formfincapp0005, "collectAccIdNew", "initValue", mfBusPact.getCollectAccId());//用于存未修改的收款账号
        getObjValue(formfincapp0005, mfBusPact);

        // 客户的账户列表
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
        mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);
        mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
        List<MfCusBankAccManage> mfCusBankAccManageList = cusInterfaceFeign
                .getMfCusBankAccListByCusNo(mfCusBankAccManage);
        JSONArray bankArray = JSONArray.fromObject(mfCusBankAccManageList);
        for (int i = 0; i < bankArray.size(); i++) {
            bankArray.getJSONObject(i).put("name",bankArray.getJSONObject(i).getString("accountNo").replaceAll("([\\d]{4})(?=\\d)", "$1 "));
        }
        JSONObject json = new JSONObject();
        json.put("bankArray", bankArray);
        // 封装阳光银行项目启用标记
        String mfToSunCBS = PropertiesUtil.getWebServiceProperty("mftf_to_cbs");
        json.put("mfToSunCBS", mfToSunCBS);
        String ajaxData = json.toString();
        model.addAttribute("id",id);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("mfCusBankAccManage", mfCusBankAccManage);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("formfincapp0005", formfincapp0005);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_BankAccUpdate";
    }

    /**
     *
     * 方法描述： 账号变更方法
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2017-12-12 上午10:31:12
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/bankAccUpdateAjax")
    @ResponseBody
    public Map<String, Object> bankAccUpdateAjax(String ajaxData,String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincAppAccountRecord mfBusFincAppAccountRecord = new MfBusFincAppAccountRecord();
        try {
            Map map = getMapByJson(ajaxData);
            FormData formfincapp0003 = formService.getFormData((String) map.get("formId"));
            getFormValue(formfincapp0003, map);
            if (this.validateFormData(formfincapp0003)) {
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                MfBusPact mfBusPact = new MfBusPact();
                setObjValue(formfincapp0003, mfBusFincApp);
                setObjValue(formfincapp0003, mfBusPact);
                MfBusFincApp mfBusFincAppTmp = new MfBusFincApp();
                mfBusFincAppTmp.setFincId(mfBusFincApp.getFincId());
                mfBusFincAppTmp.setPactId(mfBusPact.getPactId());
                mfBusFincAppTmp.setRepayAccId(mfBusFincApp.getRepayAccId());
                mfBusFincAppTmp.setRepayBank(mfBusFincApp.getRepayBank());
                mfBusFincAppTmp.setRepayAccName(mfBusFincApp.getRepayAccName());
                mfBusFincAppTmp.setBankAccId(mfBusFincApp.getBankAccId());
                mfBusFincAppTmp.setIncomAccountName(mfBusFincApp.getIncomAccountName());
                mfBusFincAppTmp.setIncomBank(mfBusFincApp.getIncomBank());
                mfBusFincAppTmp.setCollectAccId(mfBusFincApp.getCollectAccId());
                mfBusFincAppTmp.setCollectAccName(mfBusFincApp.getCollectAccName());
                mfBusFincAppTmp.setCollectBank(mfBusFincApp.getCollectBank());
                Map<String,Object> parmMap = new HashMap<String,Object>();
                setObjValue(formfincapp0003, mfBusFincAppAccountRecord);
                new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusFincAppTmp);
                new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusFincAppAccountRecord);
                parmMap.put("mfBusFincApp",mfBusFincAppTmp);
                parmMap.put("mfBusFincAppAccountRecord",mfBusFincAppAccountRecord);
                mfBusFincAppFeign.updateBankId(parmMap);
                mfBusFincAppAccountRecord.setId(id);
                mfBusFincAppAccountRecord.setPactNo((String)map.get("pactNo"));
                mfBusFincAppAccountRecordFeign.updateRefundHistory(mfBusFincAppAccountRecord);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 获得渠道系统中还款中业务查询数据
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-12 上午11:53:24
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findTrenchBussByPageAjax")
    @ResponseBody
    public Map<String, Object> findTrenchBussByPageAjax(String fincSts, String ajaxData, Integer pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setFincSts(fincSts);
            mfBusFincApp = setBusFincApp(mfBusFincApp);
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            // this.getRoleConditions(mfBusFincApp,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.findTrenchFincByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法 dataMap.put("tableData",tableHtml);
             */
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }

        return dataMap;
    }

    private MfBusFincApp setBusFincApp(MfBusFincApp mfBusFincApp) throws Exception {
        String dataRang = (String) this.getHttpRequest().getSession().getAttribute("dataRang");
        String trenchOpNo = (String) this.getHttpRequest().getSession().getAttribute("trenchOpNo");
        String channelSourceNo = (String) this.getHttpRequest().getSession().getAttribute("channelSourceNo");
        if (BizPubParm.TRENCH_USER_DATA_RANG_1.equals(dataRang)) {// 本人
            mfBusFincApp.setTrenchOpNo(trenchOpNo);
            mfBusFincApp.setChannelSourceNo(channelSourceNo);
        } else if (BizPubParm.TRENCH_USER_DATA_RANG_2.equals(dataRang)) {// 本渠道
            mfBusFincApp.setChannelSourceNo(channelSourceNo);
        } else if (BizPubParm.TRENCH_USER_DATA_RANG_3.equals(dataRang)) {// 本渠道及其子渠道
            MfBusTrench mfBusTrench = new MfBusTrench();
            mfBusTrench.setTrenchUid(channelSourceNo);
            String trenchChildStr = cusInterfaceFeign.getTrenchChildStr(mfBusTrench);
            mfBusFincApp.setChannelSourceStr(trenchChildStr);
        }else {
        }
        return mfBusFincApp;
    }

    /**
     * 方法描述： 修改存出保证金相关字段
     * @return
     * @author Javelin
     * @date 2017-6-23 上午9:49:47
     */
    @RequestMapping(value = "/updateDepositOutByIdAjax")
    @ResponseBody
    public Map<String, Object> updateDepositOutByIdAjax(String ajaxData) {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            dataMap = getMapByJson(ajaxData);
            JSONObject json = JSONObject.fromObject(dataMap);
            MfBusFincApp mfBusFincApp = (MfBusFincApp) JSONObject.toBean(json, MfBusFincApp.class);
            String depositOutMarginAmt = json.getString("depositOutMarginAmt");
            depositOutMarginAmt = depositOutMarginAmt.replaceAll(",", "");
            mfBusFincApp.setDepositOutMarginAmt(Double.valueOf(depositOutMarginAmt));
            mfBusFincAppFeign.updateDepositOutById(mfBusFincApp);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getFincAppListAjax")
    @ResponseBody
    public Map<String, Object> getFincAppListAjax(String pactId) throws Exception{
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setPactId(pactId);
            List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getFincAppListByPactId(mfBusFincApp);
            if (mfBusFincAppList!=null&&mfBusFincAppList.size()>0) {
                dataMap.put("mfBusFincAppList", mfBusFincAppList);
                dataMap.put("flag", "success");
            }else{
                dataMap.put("flag", "error");
            }
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }
    @RequestMapping(value = "/getAgenciesChengJiaoListPage")
    public String getAgenciesChengJiaoListPage(Model model,String agenciesUid) {
        ActionContext.initialize(request, response);
        model.addAttribute("agenciesUid", agenciesUid);
        return "/component/pact/MfBusPact_AgenciesChengJiao";
    }
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findAgenciesChengJiaoByPageAjax")
    @ResponseBody
    public Map<String, Object> findAgenciesChengJiaoByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                               String ajaxData, String agenciesUid) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setCusNoFund(agenciesUid);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.findAgenciesChengJiaoByPageAjax(ipage);
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
    // 核心企业成交业务
    @RequestMapping(value = "/getCoreCompanyChengJiaoListPage")
    public String getCoreCompanyChengJiaoListPage(Model model,String cusNo) {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        return "/component/pact/MfBusPact_CoreCompanyChengJiao";
    }
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findCoreCompanyChengJiaoByPageAjax")
    @ResponseBody
    public Map<String, Object> findCoreCompanyChengJiaoByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                                  String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setCusNoCore(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.findCoreCompanyChengJiaoByPageAjax(ipage);
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

    // 仓储机构成交业务
    @RequestMapping(value = "/getWarehouseOrgChengJiaoListPage")
    public String getWarehouseOrgChengJiaoListPage(Model model,String cusNo) {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        return "/component/pact/MfBusPact_WarehouseOrgChengJiao";
    }
    //查询仓储及构成交业务
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findWarehouseOrgChengJiaoByPageAjax")
    @ResponseBody
    public Map<String, Object> findWarehouseOrgChengJiaoByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                                  String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setCusNoCore(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.findWarehouseOrgChengJiaoByPageAjax(ipage);
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
    @RequestMapping(value = "/getListPageForSelect")
    public String getListPageForSelect(Model model,String cusNo,String selectType,String sts5) {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("selectType", selectType);
        //20190315yxl 条件查询finc_sts=5 （放款已复核）的借据
        model.addAttribute("sts5", sts5);
        return "/component/pact/MfBusFincApp_ListForSelect";
    }


    @RequestMapping(value = "/getHistory")
    public String getHistory(Model model,String pactId,String ajaxData,String cusNo) {
        ActionContext.initialize(request, response);
        model.addAttribute("pactId",pactId);
        model.addAttribute("cusNo",cusNo);
        return "/component/pact/MfBusFincApp_ListHistory";
    }

    @RequestMapping(value = "/getHistoryDetails")
    public String getHistoryDetails(Model model,String id,String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        List<MfCusBankAccManage> list = new ArrayList<MfCusBankAccManage>();
        MfBusFincAppAccountRecord mfBusFincAppAccountRecord = new MfBusFincAppAccountRecord();
        mfBusFincAppAccountRecord.setId(id);
        mfBusFincAppAccountRecord = mfBusFincAppAccountRecordFeign.getById(mfBusFincAppAccountRecord);

        mfCusBankAccManage.setCusNo(mfBusFincAppAccountRecord.getCusNo());
        list = cusInterfaceFeign.getMfCusBankAccListByCusNo(mfCusBankAccManage);//获取该客户下的所有银行卡信息
        for(int num = 0;num<list.size();num++){
            if(list.get(num).getId().equals(mfBusFincAppAccountRecord.getRepayAccIdNew())){//修改前还款账号
                mfBusFincAppAccountRecord.setRepayAccIdNew(list.get(num).getAccountNo());
            }
            if(list.get(num).getId().equals(mfBusFincAppAccountRecord.getRepayAccId())){//还款账号
                mfBusFincAppAccountRecord.setRepayAccId(list.get(num).getAccountNo());
            }
            if(list.get(num).getId().equals(mfBusFincAppAccountRecord.getCollectAccIdNew())){//修改前借款账号
                mfBusFincAppAccountRecord.setCollectAccIdNew(list.get(num).getAccountNo());
            }
            if(list.get(num).getId().equals(mfBusFincAppAccountRecord.getCollectAccId())){//借款账号
                mfBusFincAppAccountRecord.setCollectAccId(list.get(num).getAccountNo());
            }
            if(list.get(num).getId().equals(mfBusFincAppAccountRecord.getBankAccIdNew())){//修改前收款账号
                mfBusFincAppAccountRecord.setBankAccIdNew(list.get(num).getAccountNo());
            }
            if(list.get(num).getId().equals(mfBusFincAppAccountRecord.getBankAccId())){//收款账号
                mfBusFincAppAccountRecord.setBankAccId(list.get(num).getAccountNo());
            }

        }
        FormData formBankAccupdateHistory = formService.getFormData("BankAccupdateHistory");
        getObjValue(formBankAccupdateHistory,mfBusFincAppAccountRecord);
        model.addAttribute("formBankAccupdateHistory", formBankAccupdateHistory);
        model.addAttribute("mfBusFincAppAccountRecord", mfBusFincAppAccountRecord);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_HistoryDetails";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getListHistory")
    @ResponseBody
    public Map<String, Object> getListHistory(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData,String pactId,String cusNo) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JsonTableUtil jtu = new JsonTableUtil();
        Ipage ipage = this.getIpage();
        ipage.setPageSize(pageSize);
        ipage.setPageNo(pageNo);// 异步传页面翻页参数
        String htmlStr = null;
        try {
            MfBusFincAppAccountRecord mfBusFincAppAccountRecord = new MfBusFincAppAccountRecord();
            mfBusFincAppAccountRecord.setPactId(pactId);
            mfBusFincAppAccountRecord.setCusNo(cusNo);
            ipage.setParams(this.setIpageParams("mfBusFincAppAccountRecord", mfBusFincAppAccountRecord));
            ipage = mfBusFincAppAccountRecordFeign.findByPage(ipage);
            htmlStr = jtu.getJsonStr(tableId, tableType,(List) ipage.getResult(),ipage, true);
            ipage.setResult(htmlStr);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     *
     * 方法描述： 获得借据数据源
     *
     * @return
     * @throws Exception
     *             String
     * @author ldy
     * @date 2017-5-17 下午4:36:57
     */
    @RequestMapping("/getJSONArrayByCusNoAjax")
    @ResponseBody
    public Map<String, Object> getJSONArrayByCusNoAjax(String cusNo,String fincId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCusNo(cusNo);
            mfBusFincApp.setFincId(fincId);
            JSONArray dataArray = mfBusFincAppFeign.getJSONArrayByCusNo(mfBusFincApp);
            dataMap.put("items", dataArray);
            dataMap.put("flag", "success");
            dataMap.put("msg", "成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", "加载客户借据明细数据源失败");
            throw e;
        }
        return dataMap;
    }
    /**
     *
     * 方法描述： 获得多选借据数据源
     *
     * @return
     * @throws Exception
     *             String
     * @author ldy
     * @date 2017-5-17 下午4:36:57
     */
    @RequestMapping("/getFincInfoHtmlByfincId")
    @ResponseBody
    public Map<String, Object> getFincInfoHtmlByfinId(String fincId,String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            String [] fincIdArr = fincId.split("\\|");
            List<MfBusFincApp> busFincAppList = new ArrayList<MfBusFincApp>();
            int loanNum = 0;
            String totalLoanAmt = "0.00";
            String loanBalance = "0.00";
            String overduePrincipal = "0.00";
            String overdueInterest = "0.00";
            String totalPenalty = "0.00";
            String normalInterest="0.00";
            String compoundInterest = "0.00";
            String pactNo="";
            String pactId="";
            String appName="";
            String appAmt="0.0";
            if(fincIdArr.length>0 && fincIdArr !=null && StringUtil.isNotBlank(fincIdArr[0])){
                loanNum = fincIdArr.length;
                for(int i=0;i<fincIdArr.length;i++){
                    MfBusFincApp mfBusFincApp = new MfBusFincApp();
                    Map<String, String> parmMap = new HashMap<String,String>();
                    mfBusFincApp.setFincId(fincIdArr[i]);
                    mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
                    pactNo=pactNo+mfBusFincApp.getPactNo()+"|";
                    pactId=pactId+mfBusFincApp.getPactId()+"|";
                    appName=appName+mfBusFincApp.getAppName()+"|";
                    appAmt=String.valueOf(mfBusFincApp.getPactAmt());
                    if (pactNo.length()>1){
                        pactNo = pactNo.substring(0,pactNo.length()-1);
                        pactId = pactId.substring(0,pactId.length()-1);
                        appName = appName.substring(0,appName.length()-1);
                    }
                    //把年利率转换为月利率显示
                    MfBusAppKind mfBusAppKind = new MfBusAppKind();
                    mfBusAppKind.setAppId(mfBusFincApp.getAppId());
                    mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
                    String fincRateStr = MathExtend.showRateMethod(mfBusFincApp.getRateType(), mfBusFincApp.getFincRate().toString(), mfBusAppKind.getYearDays(), Integer.valueOf(mfBusAppKind.getReturnPlanPoint()));
                    //获取利率单位
                    Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
                    String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
                    mfBusFincApp.setFincRate(Double.valueOf(fincRateStr));
                    String fincRate =mfBusFincApp.getFincRate().toString()+rateUnit;
                    mfBusFincApp.setFincRate1(fincRate);
                    parmMap.put("fincId", fincIdArr[i]);
                    totalLoanAmt = MathExtend.add(totalLoanAmt,String.valueOf(mfBusFincApp.getPutoutAmt()));
                    loanBalance = MathExtend.add(loanBalance,String.valueOf(mfBusFincApp.getLoanBal()));
                    busFincAppList.add(mfBusFincApp);
                    List<MfRepayAmt> repayAmtList = calcRepaymentInterfaceFeign.getCurTermYingShouAmtList(parmMap);
                    if(repayAmtList != null && repayAmtList.size()>0){
                        for(MfRepayAmt mra :repayAmtList){
                            if("2".equals(mra.getHeJiFlag())){
                                overduePrincipal = MathExtend.round(MathExtend.add(overduePrincipal,mra.getBenJin()), 2);//逾期本金
                                overdueInterest = MathExtend.round(MathExtend.add(overdueInterest,mra.getYuQiLiXi()), 2);//逾期利息
                                totalPenalty = MathExtend.round(MathExtend.add(totalPenalty,mra.getFaXi()), 2);//罚息利息
                                normalInterest = MathExtend.round(MathExtend.add(normalInterest,mra.getLiXi()), 2);//正常利息
                                compoundInterest = MathExtend.round(MathExtend.add(compoundInterest,mra.getFuLiLiXi()), 2);//复利利息
                            }
                        }
                    }
                }
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, "tableTag", busFincAppList, null, true);
            dataMap.put("tableData", tableHtml);
            dataMap.put("loanNum", loanNum);
            dataMap.put("totalLoanAmt", totalLoanAmt);
            dataMap.put("loanBalance", loanBalance);
            dataMap.put("overduePrincipal", overduePrincipal);
            dataMap.put("overdueInterest", overdueInterest);
            dataMap.put("totalPenalty", totalPenalty);
            dataMap.put("normalInterest", normalInterest);
            dataMap.put("compoundInterest", compoundInterest);
            dataMap.put("pactNo", pactNo);
            dataMap.put("pactId", pactId);
            dataMap.put("appName", appName);
            dataMap.put("flag", "success");
            dataMap.put("appAmt",appAmt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", "加载客户借据明细失败");
            throw e;
        }
        return dataMap;
    }


    /**
     * 获得银行合同到期预警列表页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getContractExpiresPage")
    public String getContractExpiresPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);

        String cuslendDays = "0";
        CuslendWarning queryCuslendWarn = new CuslendWarning();
        queryCuslendWarn.setCuslendWarnNo("BANK_CONTRACT_EXPIRES");// 获得银行合同到期预警
        queryCuslendWarn.setFlag("1");
        CuslendWarning cuslendWarn = msgConfInterfaceFeign.getByIdAndFlag(queryCuslendWarn);
        if (null != cuslendWarn) {
            cuslendDays = String.valueOf(cuslendWarn.getCuslendDays());
        }
        model.addAttribute("cuslendDays", cuslendDays);
        model.addAttribute("query", "");
        return "/component/pact/repayplan/MfContractExpires_List";

    }

    /**
     * 获得银行合同到期预警列表数据请求
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getContractExpiresAjax")
    @ResponseBody
    public Map<String, Object> getContractExpiresAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                      String ajaxData,Ipage ipage) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfFundChannelRepayPlan mfFundChannelRepayPlan = new MfFundChannelRepayPlan();
        String cuslendDays = request.getParameter("cuslendDays");// 提前几天预警

        // 根据预警天数计算---合同结束日期
        String endDate = DateUtil.getDate();
        if (StringUtil.isNotEmpty(endDate)) {
            endDate = DateUtil.addByDay(Integer.parseInt(cuslendDays));
        }
        try {
            mfFundChannelRepayPlan.setPlanBeginDate(DateUtil.getDate());
            mfFundChannelRepayPlan.setPlanEndDate(endDate);

            mfFundChannelRepayPlan.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfFundChannelRepayPlan.setCriteriaList(mfFundChannelRepayPlan, ajaxData);// 我的筛选
            mfFundChannelRepayPlan.setCustomSorts(ajaxData);
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("mfFundChannelRepayPlan", mfFundChannelRepayPlan);

            ipage.setParams(this.setIpageParams("paramMap", paramMap));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.getContractExpiresByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取还款到期列表失败"));
        }
        return dataMap;
    }

    /**
     * 获得银行合同逾期预警列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getContractOverduePage")
    public String getContractOverduePage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);

        String cuslendDays = "0";
        CuslendWarning queryCuslendWarn = new CuslendWarning();
        queryCuslendWarn.setCuslendWarnNo("BANK_CONTRACT_OVERDUE");// 获得银行合同到期预警
        queryCuslendWarn.setFlag("1");
        CuslendWarning cuslendWarn = msgConfInterfaceFeign.getByIdAndFlag(queryCuslendWarn);
        if (null != cuslendWarn) {
            cuslendDays = String.valueOf(cuslendWarn.getCuslendDays());
        }
        model.addAttribute("cuslendDays", cuslendDays);
        model.addAttribute("query", "");
        return "/component/pact/repayplan/MfContractOverdue_List";

    }

    /**
     * 获得银行合同逾期预警列表数据请求
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getContractOverdueAjax")
    @ResponseBody
    public Map<String, Object> getContractOverdueAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                      String ajaxData,Ipage ipage) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfFundChannelRepayPlan mfFundChannelRepayPlan = new MfFundChannelRepayPlan();
        String cuslendDays = request.getParameter("cuslendDays");// 逾期后预警几天

        // 根据预警天数计算---合同结束日期
        String endDate = DateUtil.getDate();
        if (StringUtil.isNotEmpty(endDate)) {
            endDate = DateUtil.addByDay(-Integer.parseInt(cuslendDays));
        }
        try {
            mfFundChannelRepayPlan.setPlanBeginDate(endDate);
            mfFundChannelRepayPlan.setPlanEndDate(DateUtil.getDate());

            mfFundChannelRepayPlan.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfFundChannelRepayPlan.setCriteriaList(mfFundChannelRepayPlan, ajaxData);// 我的筛选
            mfFundChannelRepayPlan.setCustomSorts(ajaxData);
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("mfFundChannelRepayPlan", mfFundChannelRepayPlan);

            ipage.setParams(this.setIpageParams("paramMap", paramMap));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.getContractExpiresByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取还款逾期列表失败"));
        }
        return dataMap;
    }
    /**
     * 方法描述： 根据渠道编号获取已放款业务信息，并计算分润金额
     * @param pageNo
     * @param pageSize
     * @param tableId
     * @param tableType
     * @param ajaxData
     * @param cusNo
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author 仇招
     * @date 2018年9月5日 下午4:03:35
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByTrenchAjax")
    @ResponseBody
    public Map<String, Object> findByTrenchAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                String ajaxData,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setChannelSourceNo(cusNo);
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            // this.getRoleConditions(mfBusFincApp,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
            ipage = mfBusFincAppFeign.findByTrench(ipage);
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
     * 方法描述：  根据资金机构编号获取已放款业务信息，并计算分润金额
     * @param pageNo
     * @param pageSize
     * @param tableId
     * @param tableType
     * @param ajaxData
     * @param cusNo
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author 仇招
     * @date 2018年9月7日 下午3:40:46
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByAgenciesAjax")
    @ResponseBody
    public Map<String, Object> findByAgenciesAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                  String ajaxData,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCusNoFund(cusNo);
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            // this.getRoleConditions(mfBusFincApp,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
            ipage = mfBusFincAppFeign.findByAgencies(ipage);
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
     * 方法描述： 获取提前还款借据列表
     * @param model
     * @return
     * @throws Exception
     * String
     * @author 仇招
     * @date 2018年9月10日 下午2:23:11
     */
    @RequestMapping(value = "/getEarlyRepayListPage")
    public String getEarlyRepayListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_STS");
        model.addAttribute("fincStsJsonArray", pactStsJsonArray);
        // 办理阶段
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_EarlyRepayList";
    }
    /**
     * 方法描述： 获取提前还款借据列表数据
     * @param pageNo
     * @param pageSize
     * @param tableId
     * @param tableType
     * @param ajaxData
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author 仇招
     * @date 2018年9月10日 下午2:23:36
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findEarlyRepayByPageAjax")
    @ResponseBody
    public Map<String, Object> findEarlyRepayByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                        String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setCustomSorts(ajaxData);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
            ipage = mfBusFincAppFeign.findEarlyRepayByPage(ipage);
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
     * 方法描述： 获取渠道推荐的逾期放款
     * @param model
     * @return
     * @throws Exception
     * String
     * @author 仇招
     * @date 2018年9月10日 下午9:45:28
     */
    @RequestMapping(value = "/getOverdueListPage")
    public String getOverdueListPage(Model model,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_STS");
        model.addAttribute("fincStsJsonArray", pactStsJsonArray);
        // 办理阶段
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_OverdueList";
    }
    /**
     * 方法描述： 根据渠道编号获取逾期放款
     * @param pageNo
     * @param pageSize
     * @param tableId
     * @param tableType
     * @param ajaxData
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author 仇招
     * @date 2018年9月10日 下午9:50:10
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findOverdueByPageAjax")
    @ResponseBody
    public Map<String, Object> findOverdueByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                     String ajaxData,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setCustomSorts(ajaxData);
            mfBusFincApp.setChannelSourceNo(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
            ipage = mfBusFincAppFeign.findOverdueByPage(ipage);
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
    @RequestMapping(value = "/findOverdueByPageAjaxExcel")
    @ResponseBody
    public void findOverdueByPageAjaxExcel(String tableId) throws Exception{
        //读取出文件的所有字节,并将所有字节写出给客户端
        ActionContext.initialize(request, response);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.findOverdueByPageAjaxExcel(mfBusFincApp);
            ExpExclUtil eu = new ExpExclUtil();
            HSSFWorkbook wb = eu.expExclTableForList(tableId, mfBusFincAppList,null,false,"");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download; charset=utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("earlyRepayment.xls", "UTF-8"));
            OutputStream stream = response.getOutputStream();
            wb.write(stream);
            wb.close();// HSSFWorkbook关闭
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * 还款到期列表打开页面请求
     *
     * @return
     */
    @RequestMapping(value = "/getPactToDatePage")
    public String getPactToDatePage(Model model, String ajaxData,String outFlag) {
        ActionContext.initialize(request, response);
        String pactWarning = "0";
        Logger logger = LoggerFactory.getLogger(MfBusFincAppController.class);
        try {
            CuslendWarning cuslendWarning1 = new CuslendWarning();
            cuslendWarning1.setCuslendWarnNo("PACT_TO_DATE_WARN");// 框架合同到期预警
            cuslendWarning1.setFlag("1");
            cuslendWarning1 = msgConfInterfaceFeign.getByIdAndFlag(cuslendWarning1);
            if (null != cuslendWarning1) {
                pactWarning = String.valueOf(cuslendWarning1.getCuslendDays());
            }
            model.addAttribute("cuslendWarning1", cuslendWarning1);
        } catch (Exception e) {
            logger.error("获取预警信息出错", e);
        }
        model.addAttribute("pactWarning", pactWarning);
        model.addAttribute("outFlag", outFlag);
        model.addAttribute("query", "");
        return "/component/pact/MfPactToDate_List";

    }

    /**
     * 合同到期到期列表数据请求
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getPactToDateAjax")
    @ResponseBody
    public Map<String, Object> getPactToDateAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                 String ajaxData,Ipage ipage) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincAndRepay fincAndRepay = new MfBusFincAndRepay();
        String pactWarning = request.getParameter("pactWarning");// 提前几天预警
        String repayWarning = request.getParameter("repayWarning");// 提前几天预警
        String warningType = request.getParameter("warningType");// 预警类型 0 还款
        // 1合同
        String scopeType = "0";
        // 根据预警天数计算---合同结束日期
        String pactWarningDate = DateUtil.getDate();
        if (StringUtil.isNotEmpty(pactWarningDate)) {
            pactWarningDate = DateUtil.addByDay(Integer.parseInt(pactWarning));
        }
        String repayWarningDate = DateUtil.getDate();
        if (StringUtil.isNotEmpty(repayWarningDate)) {
            repayWarningDate = DateUtil.addByDay(Integer.parseInt(repayWarning));
        }
        try {
            // 取出ajax数据
            Gson gson = new Gson();
            JSONArray jsonArray = gson.fromJson(ajaxData, JSONArray.class);
            if (jsonArray.get(0) instanceof JSONArray) {
                JSONArray jsonArraySub = jsonArray.getJSONArray(0);
                for (int i = 0; i < jsonArraySub.size(); i++) {
                    JSONObject jsonObj = (JSONObject) jsonArraySub.get(i);
                    if ("scope".equals(jsonObj.get("condition"))
                            && StringUtil.isNotEmpty((String) jsonObj.get("value"))) {
                        scopeType = (String) jsonObj.get("value");
                        if ("0".equals(scopeType)) {// 全部
                            scopeType = "0";
                        } else if ("1".equals(scopeType)) {// 合同到期
                            scopeType = "1";
                            fincAndRepay.setPactBeginDate(DateUtil.getDate());
                            fincAndRepay.setPactEndDate(pactWarningDate);
                        } else if ("2".equals(scopeType)) {// 还款到期
                            scopeType = "2";
                            fincAndRepay.setPlanBeginDate(DateUtil.getDate());
                            fincAndRepay.setPlanEndDate(repayWarningDate);
                        }else {
                        }
                    }
                }
            } else if (StringUtil.isNotEmpty(warningType)) {// 预警页面跳转过来
                if ("0".equals(warningType)) {// 还款到期
                    scopeType = "2";
                    fincAndRepay.setPlanBeginDate(DateUtil.getDate());
                    fincAndRepay.setPlanEndDate(repayWarningDate);
                } else if ("1".equals(warningType)) {// 合同到期
                    scopeType = "1";
                    fincAndRepay.setPactBeginDate(DateUtil.getDate());
                    fincAndRepay.setPactEndDate(pactWarningDate);
                }else {
                }

            }else {
            }
            fincAndRepay.setCustomQuery(ajaxData);// 自定义查询参数赋值
            fincAndRepay.setCriteriaList(fincAndRepay, ajaxData);// 我的筛选
            fincAndRepay.setCustomSorts(ajaxData);
            // this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("mfBusFincAndRepay", fincAndRepay);
            paramMap.put("scopeType", scopeType);
            paramMap.put("endDate", pactWarningDate);


            ipage.setParams(this.setIpageParams("paramMap", paramMap));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.getRepayToDateByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            // logger.error("获取还款到期列表失败", e);
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取框架合同到期列表失败"));
        }
        return dataMap;
    }



    @RequestMapping(value = "/getListPageBank")
    public String getAssetManageListPage(Model model, String ajaxData) {
        ActionContext.initialize(request, response);
        model.addAttribute("query", "");
        return "component/app/MfBusFincAppBank_List";

    }

    @RequestMapping(value = "/findBankAgriculturalAjaxExcel")
    @ResponseBody
    public void findBankAgriculturalAjaxExcel(String tableId) throws Exception {
        // 读取出文件的所有字节,并将所有字节写出给客户端
        ActionContext.initialize(request, response);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            Map<String, String> map = new HashMap<>();
            List<Map<String, String>> list = mfBusFincAppFeign.getBankAgricultural(map);

            ExpExclUtil eu = new ExpExclUtil();
            HSSFWorkbook wb = eu.expExclTableForList(tableId, list, null, false, "");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download; charset=utf-8");
            response.addHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode("earlyRepayment.xls", "UTF-8"));
            OutputStream stream = response.getOutputStream();
            response.setContentType("application/octet-stream");

            wb.write(stream);
            wb.close();// HSSFWorkbook关闭
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }







    @RequestMapping(value = "/getListPageBankDetail")
    public String getListPageBankDetail(Model model, String id) {
        ActionContext.initialize(request, response);
        model.addAttribute("exportId",id);
        model.addAttribute("query", "");
        return "component/app/MfBusFincAppBankDetail_List";

    }

    @RequestMapping(value = "/inputForBatch")
    public String inputForBatch(Model model,String appId,String pactId,String fincMainId) throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        // 获取合同数据
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact = pactInterfaceFeign.getById(mfBusPact);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusPact.getCusNo());
        mfCusCustomer=mfCusCustomerFeign.getById(mfCusCustomer);
        mfBusPact.setCusNameNum(mfCusCustomer.getIdNum());
        mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);
        //获取明细出账明细
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincMainId(fincMainId);
        List<MfBusFincApp> list = mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
        if(list!=null && list.size()>0){
            Double usableFincAmt = mfBusPact.getUsableFincAmt();
            for(MfBusFincApp fincApp :list){
                usableFincAmt = usableFincAmt -fincApp.getPutoutAmt();
            }
            mfBusPact.setUsableFincAmt(usableFincAmt);
        }

        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusPact.getAppId());
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

        String formId ="putoutApplyBase";// prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.putout_apply_sub, null, null,User.getRegNo(request));
        FormData formfincapp0006 = formService.getFormData(formId);

        mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setPactAmt(mfBusPact.getPactAmt());
        mfBusFincApp.setOpName(User.getRegName(request));
        mfBusFincApp.setOpNo(User.getRegNo(request));
        mfBusFincApp.setPactId(mfBusPact.getPactId());
        mfBusFincApp.setChargeInterest(0);
        mfBusFincApp.setChargeFee(0);
        mfBusFincApp.setIntstEndDate(mfBusPact.getEndDate());
        mfBusFincApp.setFincMainId(fincMainId);
        String pactEndDateShow =mfBusAppKind.getPactEnddateShowFlag();

        //合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天
        if(StringUtil.isEmpty(pactEndDateShow)){
            pactEndDateShow = prdctInterfaceFeign.getPactEndDateShowFlag();
        }
        if ("1".equals(mfBusAppKind.getTermType())) {// 月
            String intstEndDateShow = DateUtil.addMonth(DateUtil.getDate(), mfBusAppKind.getMaxFincTerm());
            if (BizPubParm.PACT_ENDDATE_SHOW_FLAG_2.equals(pactEndDateShow)) {
                intstEndDateShow = DateUtil.addByDay(intstEndDateShow, -1);
            }
            if (mfBusPact.getEndDateShow() != null
                    && DateUtil.compareEightDate(intstEndDateShow, mfBusPact.getEndDateShow()) == 1) {
                intstEndDateShow = mfBusPact.getEndDateShow();
            }
            mfBusFincApp.setIntstEndDateShow(intstEndDateShow);
        } else if ("2".equals(mfBusAppKind.getTermType())) {// 天
            String intstEndDateShow = DateUtil.addByDay(DateUtil.getDate(), mfBusAppKind.getMaxFincTerm());
            if (BizPubParm.PACT_ENDDATE_SHOW_FLAG_2.equals(pactEndDateShow)) {
                intstEndDateShow = DateUtil.addByDay(intstEndDateShow, -1);
            }
            if (BizPubParm.PACT_ENDDATE_SHOW_FLAG_2.equals(pactEndDateShow)) {
                intstEndDateShow = DateUtil.addByDay(intstEndDateShow, -1);
            }
            if (mfBusPact.getEndDateShow() != null
                    && DateUtil.compareEightDate(intstEndDateShow, mfBusPact.getEndDateShow()) == 1) {
                intstEndDateShow = mfBusPact.getEndDateShow();
            }
            mfBusFincApp.setIntstEndDateShow(intstEndDateShow);
        }else {
        }
        //获得授信信息
        Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(appId);
        if (!creditData.isEmpty()) {
            getObjValue(formfincapp0006, creditData);
            mfBusPact.setCreditPactNo((String) creditData.get("creditPactNo"));
        }

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        //根据合同号查询银行卡变更记录 如果有变更则取最新的变更记录那条已修改的银行卡信息
        MfBusFincAppAccountRecord mfBusFincAppAccountRecord = new MfBusFincAppAccountRecord();
        mfBusFincAppAccountRecord.setPactId(mfBusPact.getPactId());
        mfBusFincAppAccountRecord  =  mfBusFincAppAccountRecordFeign.getByNewest(mfBusFincAppAccountRecord);
        if(mfBusFincAppAccountRecord != null){//有变更记录
            mfBusPact.setBankAccId(mfBusFincAppAccountRecord.getBankAccId());
            mfBusPact.setRepayAccId(mfBusFincAppAccountRecord.getRepayAccId());
        }
        getObjValue(formfincapp0006, mfBusApply);
        getObjValue(formfincapp0006, mfBusFincApp);
        getObjValue(formfincapp0006, mfBusPact);

        String wkfAppId = mfBusPact.getWkfAppId();
        model.addAttribute("wkfAppId", wkfAppId);
        // 放款申请表单中的还款方式修改时应收产品设置中的还款方式控制
        JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
        JSONArray repayDateSetMap = prdctInterfaceFeign.getRepayRulesMap(mfBusPact);
        for (int i = 0; i < repayDateSetMap.size(); i++) {
            String repayDateSet = repayDateSetMap.getJSONObject(i).getString("id").split("_")[1];
            if (StringUtil.isNotEmpty(repayDateSet) && StringUtil.isNotEmpty(mfBusAppKind.getRepayDateSet())) {
                if (repayDateSet.equals(mfBusAppKind.getRepayDateSet())) {
                    mfBusAppKind.setRepayDateSet(repayDateSetMap.getJSONObject(i).getString("id"));
                    break;
                }
            }
        }

        JSONObject json = new JSONObject();
        json.put("repayTypeMap", repayTypeMap);
        json.put("repayDateSetMap", repayDateSetMap);
        this.changeFormProperty(formfincapp0006, "repayDateSet", "initValue", mfBusAppKind.getRepayDateSet());
        this.changeFormProperty(formfincapp0006, "repayDateDef", "initValue", mfBusAppKind.getRepayDateDef());

        // 客户的账户列表
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
        mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);
        mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
        List<MfCusBankAccManage> mfCusBankAccManageList = cusInterfaceFeign
                .getMfCusBankAccListByCusNo(mfCusBankAccManage);
        JSONArray bankArray = JSONArray.fromObject(mfCusBankAccManageList);
        for (int i = 0; i < bankArray.size(); i++) {
            bankArray.getJSONObject(i).put("name",bankArray.getJSONObject(i).getString("accountNo").replaceAll("([\\d]{4})(?=\\d)", "$1 "));
        }

        String fincAuthCycleFlag = new CodeUtils().getSingleValByKey("FINC_AUTH_CYCLE_FLAG");//是否判断放款循环标志 1:是   0：否

        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formfincapp0006, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formfincapp0006, "overRate", "unit", rateUnit);
        this.changeFormProperty(formfincapp0006, "cmpdRate", "unit", rateUnit);

        json.put("bankArray", bankArray);
        // 封装阳光银行项目启用标记
        String mfToSunCBS = PropertiesUtil.getWebServiceProperty("mftf_to_cbs");
        json.put("mfToSunCBS", mfToSunCBS);
        String ajaxData = json.toString();
        model.addAttribute("intstEndDateShow", DateUtil.getShowDateTime(mfBusFincApp.getIntstEndDateShow()));
        model.addAttribute("intstBeginDateShow", DateUtil.getShowDateTime(mfBusPact.getBeginDate()));
        model.addAttribute("rateUnit", rateUnit);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("formfincapp0006", formfincapp0006);
        model.addAttribute("query", "");
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("pactBeginDate", DateUtil.getShowDateTime(mfBusPact.getBeginDate()));
        model.addAttribute("pactEndDate", DateUtil.getShowDateTime(mfBusPact.getEndDate()));
        String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
        model.addAttribute("sysProjectName", sysProjectName);
        model.addAttribute("kindNo", mfBusAppKind.getKindNo());
        model.addAttribute("fincMainId", fincMainId);
        return "/component/pact/MfBusFincApp_InsertForBatch";
    }


    /**
     *
     * 放款申请方法 新增借据
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/insertForBatchAjax")
    @ResponseBody
    public Map<String, Object> insertForBatchAjax(String ajaxData, String appId,String fincMainId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map map = getMapByJson(ajaxData);
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setAppId(appId);
            mfBusPact = pactInterfaceFeign.getById(mfBusPact);
            FormData formfincapp0003 = formService.getFormData((String) map.get("formId"));
            getFormValue(formfincapp0003, map);
            if (this.validateFormData(formfincapp0003)) {
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                setObjValue(formfincapp0003, mfBusFincApp);
                mfBusFincApp.setCusNo(mfBusPact.getCusNo());
                mfBusFincApp.setRateType(mfBusPact.getRateType());
                mfBusFincApp.setAppId(appId);
                mfBusFincApp.setFincMainId(fincMainId);
                // 调整 支付申请 支付申请业务处理
                // 验证该合同的可支用余额是否足够
                Map<String, String> checkMap = mfBusFincAppFeign.doCheckFincBal(mfBusFincApp,mfBusPact.getPactId());
                if ("success".equals(checkMap.get("flag"))) {
                    // cusInterfaceFeign.updateInfIntegrity(mfBusPact.getCusNo());
                    MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
                    new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusFincApp);
                    map.put("mfBusFincApp", mfBusFincApp);
                    mfBusFincAppChild = mfBusFincAppFeign.doInsertFincAppForBatchWithUser(map,User.getRegNo(request),User.getRegName(request));
                    mfBusFincAppChild = mfBusFincAppFeign.disProcessDataForFincChildShow(mfBusFincAppChild);
                    //获取此次放款申请的明细列表
                    mfBusFincApp = new MfBusFincApp();
                    mfBusFincApp.setFincMainId(fincMainId);
                    List<MfBusFincApp> list = mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
                    Double usableFincAmtT =0.00;
                    if(list!=null && list.size()>0){
                        for(MfBusFincApp fincApp:list){
                            usableFincAmtT = usableFincAmtT + fincApp.getPutoutAmt();
                        }
                    }
                    JsonTableUtil jtu = new JsonTableUtil();
                    String tableHtml = jtu.getJsonStr("tablefincAppBatchListBase", "tableTag", list, null, true);
                    dataMap.put("htmlStr", tableHtml);
                    dataMap.put("fincSts", mfBusFincAppChild.getFincSts());
                    dataMap.put("flag", "success");
                    dataMap.put("fincId", mfBusFincAppChild.getFincId());
                    Double usableFincAmt = mfBusPact.getUsableFincAmt()-usableFincAmtT;
                    dataMap.put("usableFincAmt",usableFincAmt );
                } else {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", checkMap.get("msg"));
                }
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }

        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }


    @RequestMapping(value = "/updateForBatch")
    public String updateForBatch(Model model,String fincId) throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        //获取借据信息
        MfBusFincApp mfBusFincApp =new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        // 获取合同数据
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(mfBusFincApp.getAppId());
        mfBusPact = pactInterfaceFeign.getById(mfBusPact);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusPact.getCusNo());
        mfCusCustomer=mfCusCustomerFeign.getById(mfCusCustomer);
        mfBusPact.setCusNameNum(mfCusCustomer.getIdNum());
        mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);
        //获取明细出账明细
        MfBusFincApp mfBusFincAppParm = new MfBusFincApp();
        mfBusFincAppParm.setFincMainId(mfBusFincApp.getFincMainId());
        List<MfBusFincApp> list = mfBusFincAppFeign.getByFincMainId(mfBusFincAppParm);
        if(list!=null && list.size()>0){
            Double usableFincAmt = mfBusPact.getUsableFincAmt();
            for(MfBusFincApp fincApp :list){
                if(fincApp.getFincId().equals(fincId)){//排除当前的借据
                    continue;
                }
                usableFincAmt = usableFincAmt -fincApp.getPutoutAmt();
            }
            mfBusPact.setUsableFincAmt(usableFincAmt);
        }

        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusPact.getAppId());
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

        String formId ="putoutApplyDetailBase";// prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.putout_apply_sub, null, null,User.getRegNo(request));
        FormData formfincapp0006 = formService.getFormData(formId);
        //获得授信信息
        Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(mfBusFincApp.getAppId());
        if (!creditData.isEmpty()) {
            getObjValue(formfincapp0006, creditData);
            mfBusPact.setCreditPactNo((String) creditData.get("creditPactNo"));
        }

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(mfBusFincApp.getAppId());
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        //根据合同号查询银行卡变更记录 如果有变更则取最新的变更记录那条已修改的银行卡信息
        MfBusFincAppAccountRecord mfBusFincAppAccountRecord = new MfBusFincAppAccountRecord();
        mfBusFincAppAccountRecord.setPactId(mfBusPact.getPactId());
        mfBusFincAppAccountRecord  =  mfBusFincAppAccountRecordFeign.getByNewest(mfBusFincAppAccountRecord);
        if(mfBusFincAppAccountRecord != null){//有变更记录
            mfBusPact.setBankAccId(mfBusFincAppAccountRecord.getBankAccId());
            mfBusPact.setRepayAccId(mfBusFincAppAccountRecord.getRepayAccId());
        }
        getObjValue(formfincapp0006, mfBusApply);
        getObjValue(formfincapp0006, mfBusPact);
        getObjValue(formfincapp0006, mfBusFincApp);

        String wkfAppId = mfBusPact.getWkfAppId();
        model.addAttribute("wkfAppId", wkfAppId);
        // 放款申请表单中的还款方式修改时应收产品设置中的还款方式控制
        JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
        JSONArray repayDateSetMap = prdctInterfaceFeign.getRepayRulesMap(mfBusPact);
        for (int i = 0; i < repayDateSetMap.size(); i++) {
            String repayDateSet = repayDateSetMap.getJSONObject(i).getString("id").split("_")[1];
            if (StringUtil.isNotEmpty(repayDateSet) && StringUtil.isNotEmpty(mfBusAppKind.getRepayDateSet())) {
                if (repayDateSet.equals(mfBusAppKind.getRepayDateSet())) {
                    mfBusAppKind.setRepayDateSet(repayDateSetMap.getJSONObject(i).getString("id"));
                    break;
                }
            }
        }

        JSONObject json = new JSONObject();
        json.put("repayTypeMap", repayTypeMap);
        json.put("repayDateSetMap", repayDateSetMap);
        this.changeFormProperty(formfincapp0006, "repayDateSet", "initValue", mfBusAppKind.getRepayDateSet());
        this.changeFormProperty(formfincapp0006, "repayDateDef", "initValue", mfBusAppKind.getRepayDateDef());

        // 客户的账户列表
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
        mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);
        mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
        List<MfCusBankAccManage> mfCusBankAccManageList = cusInterfaceFeign
                .getMfCusBankAccListByCusNo(mfCusBankAccManage);
        JSONArray bankArray = JSONArray.fromObject(mfCusBankAccManageList);
        for (int i = 0; i < bankArray.size(); i++) {
            bankArray.getJSONObject(i).put("name",bankArray.getJSONObject(i).getString("accountNo").replaceAll("([\\d]{4})(?=\\d)", "$1 "));
        }

        String fincAuthCycleFlag = new CodeUtils().getSingleValByKey("FINC_AUTH_CYCLE_FLAG");//是否判断放款循环标志 1:是   0：否

        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formfincapp0006, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formfincapp0006, "overRate", "unit", rateUnit);
        this.changeFormProperty(formfincapp0006, "cmpdRate", "unit", rateUnit);

        json.put("bankArray", bankArray);
        // 封装阳光银行项目启用标记
        String mfToSunCBS = PropertiesUtil.getWebServiceProperty("mftf_to_cbs");
        json.put("mfToSunCBS", mfToSunCBS);
        String ajaxData = json.toString();
        model.addAttribute("intstEndDateShow", DateUtil.getShowDateTime(mfBusFincApp.getIntstEndDateShow()));
        model.addAttribute("intstBeginDateShow", DateUtil.getShowDateTime(mfBusPact.getBeginDate()));
        model.addAttribute("rateUnit", rateUnit);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("formfincapp0006", formfincapp0006);
        model.addAttribute("query", "");
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("pactBeginDate", DateUtil.getShowDateTime(mfBusPact.getBeginDate()));
        model.addAttribute("pactEndDate", DateUtil.getShowDateTime(mfBusPact.getEndDate()));
        String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
        model.addAttribute("sysProjectName", sysProjectName);
        model.addAttribute("kindNo", mfBusAppKind.getKindNo());
        model.addAttribute("fincMainId", mfBusFincApp.getFincMainId());
        return "/component/pact/MfBusFincApp_InsertForBatch";
    }


    /**
     *
     * 放款申请方法 新增借据
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/deleteForBatchAjax")
    @ResponseBody
    public Map<String, Object> deleteForBatchAjax(String pactId,String fincId,String fincMainId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(fincId);
            dataMap = mfBusFincAppFeign.deleteForBatch(mfBusFincApp);
            if("success".equals(dataMap.get("flag"))){
                //获取此次放款申请的明细列表
                mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setFincMainId(fincMainId);
                List<MfBusFincApp> list = mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
                //获取合同信息
                MfBusPact mfBusPact = new MfBusPact();
                mfBusPact.setPactId(pactId);
                mfBusPact = mfBusPactFeign.getById(mfBusPact);
                Double usableFincAmtT =0.00;
                String planPutoutCnt = "";
                if(list!=null && list.size()>0){
                    planPutoutCnt = list.size()+"";
                    for(MfBusFincApp fincApp:list){
                        usableFincAmtT = usableFincAmtT + fincApp.getPutoutAmt();
                    }

                }
                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr("tablefincAppBatchListBase", "tableTag", list, null, true);
                dataMap.put("htmlStr", tableHtml);

                Double usableFincAmt = mfBusPact.getUsableFincAmt() - usableFincAmtT;
                dataMap.put("usableFincAmt",usableFincAmt);
                dataMap.put("planPutoutCnt",planPutoutCnt);
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/updateRepayPlanMergeFlagAjax")
    @ResponseBody
    public Map<String, Object> updateRepayPlanMergeFlagAjax(String repayPlanMergeFlag,String fincId,String fincMainId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(fincId);
            mfBusFincApp.setRepayPlanMergeFlag(repayPlanMergeFlag);
            mfBusFincApp.setFincMainId(fincMainId);
            dataMap = mfBusFincAppFeign.updateRepayPlanMergeFlag(mfBusFincApp);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
    /**
     * 批量还款
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="batchRepayment")
    public  String batchRepayment(Model model) throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        String  formId = "fincBatchRepayment";
        FormData formfincBathRepayment = formService.getFormData(formId);
        Map<String,Object> parmMap = new HashMap<String,Object>();
        parmMap.put("repayDate",DateUtil.getDate());
        getObjValue(formfincBathRepayment, parmMap);
        model.addAttribute("formfincBathRepayment", formfincBathRepayment);
        model.addAttribute("query", "");
        return "/component/pact/MfBusFincApp_BatchRepayment";
    }
    /**
     * 根据客户号获取客户下的借款信息
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="getLoanInfoByCusNo")
    @ResponseBody
    public  Map<String,Object> getLoanInfoByCusNo(Model model,String cusNo) throws Exception{
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap = mfBusFincAppFeign.getLoanInfoByCusNo(cusNo);
        JSONArray jsonArray = JSONArray.fromObject(resultMap.get("mfBusFincAppList"));
        JSONArray fincList = new JSONArray();
        List<MfBusFincApp> mfBusFincAppList = (List<MfBusFincApp>) JSONArray.toList(jsonArray, new MfBusFincApp(), new JsonConfig());
        List<Map<String,Object>> fincHtmlList =  new ArrayList<Map<String,Object>>();
        if (mfBusFincAppList!=null&&mfBusFincAppList.size()>0){
            for (int i = 0; i < mfBusFincAppList.size(); i++) {
                Map<String,Object> fincMap = new HashMap<String,Object>();
                MfBusFincApp mfBusFincApp= mfBusFincAppList.get(i);
                //获取借据应还信息
                List<Map<String,Object>> fincInfoList =  new ArrayList<Map<String,Object>>();
                Map<String,Object> map1 = mfBusFincAppFeign.getYingHuanByCusNo(mfBusFincApp.getFincId(),DateUtil.getDate());
                map1.put("fincId",mfBusFincApp.getFincId());
                map1.put("fincShowId",mfBusFincApp.getFincShowId());
                map1.put("fincShowId",mfBusFincApp.getFincShowId());
                map1.put("infoType","应还");
                Map<String,Object> map2 = new HashMap<String,Object>();
                map2.put("benJin","");
                map2.put("liXi","");
                map2.put("yuQiLiXi","");
                map2.put("fuLiLiXi","");
                map2.put("faXi","");
                map2.put("weiYueJin","");
                map2.put("feiYong","");
                map2.put("infoType","实还");
                map2.put("fincId",mfBusFincApp.getFincId());
                map2.put("fincShowId",mfBusFincApp.getFincShowId());
                fincInfoList.add(map1);
                fincInfoList.add(map2);
                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr("tablefincBathchRepaymentBase", "tableTag", fincInfoList, null, true);
                fincMap.put("htmlStr", tableHtml);
                fincMap.put("mfBusFincApp", mfBusFincApp);
                fincHtmlList.add(fincMap);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",mfBusFincApp.getFincShowId());
                jsonObject.put("id",mfBusFincApp.getFincId());
                fincList.add(jsonObject);
            }
        }
        resultMap.put("fincList",fincList);
        resultMap.put("fincHtmlList",JSONArray.fromObject(fincHtmlList));
        return resultMap;
    }
    /**
     * 根据客户号获取客户下的借款信息
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="getFincInfoByFincNo")
    @ResponseBody
    public  Map<String,Object> getFincInfoByFincNo(Model model,String fincNo,String repayDate) throws Exception{
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Map<String,Object>> fincHtmlList =  new ArrayList<Map<String,Object>>();
        if(StringUtil.isEmpty(repayDate)){
            repayDate = DateUtil.getDate();
        }else{
            repayDate =repayDate.replaceAll("-","");
        }
        if(StringUtil.isNotEmpty(fincNo)){
            String[] fincList = fincNo.split("\\|");

            for (int i = 0; i < fincList.length; i++) {
                String fincId = fincList[i];
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setFincId(fincId);
                mfBusFincApp= mfBusFincAppFeign.getById(mfBusFincApp);
                if(mfBusFincApp!=null){
                    Map<String,Object> fincMap = new HashMap<String,Object>();
                    //获取借据应还信息
                    List<Map<String,Object>> fincInfoList =  new ArrayList<Map<String,Object>>();
                    Map<String,Object> map1 = mfBusFincAppFeign.getYingHuanByCusNo(mfBusFincApp.getFincId(),repayDate);
                    map1.put("fincId",mfBusFincApp.getFincId());
                    map1.put("fincShowId",mfBusFincApp.getFincShowId());
                    map1.put("fincShowId",mfBusFincApp.getFincShowId());
                    map1.put("infoType","应还");
                    Map<String,Object> map2 = new HashMap<String,Object>();
                    map2.put("benJin","");
                    map2.put("liXi","");
                    map2.put("yuQiLiXi","");
                    map2.put("fuLiLiXi","");
                    map2.put("faXi","");
                    map2.put("weiYueJin","");
                    map2.put("feiYong","");
                    map2.put("infoType","实还");
                    map2.put("fincId",mfBusFincApp.getFincId());
                    map2.put("fincShowId",mfBusFincApp.getFincShowId());
                    fincInfoList.add(map1);
                    fincInfoList.add(map2);
                    JsonTableUtil jtu = new JsonTableUtil();
                    String tableHtml = jtu.getJsonStr("tablefincBathchRepaymentBase", "tableTag", fincInfoList, null, true);
                    fincMap.put("htmlStr", tableHtml);
                    fincMap.put("mfBusFincApp", mfBusFincApp);
                    fincHtmlList.add(fincMap);
                }

            }
        }
        resultMap.put("fincHtmlList",fincHtmlList);
        return resultMap;
    }
    /**
     * 根据借据号获取还款顺序
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="getRepayOrder")
    @ResponseBody
    public  Map<String,Object> getRepayOrder(Model model,String fincId) throws Exception{
        Map<String,Object> resultMap = new HashMap<String,Object>();
        //获取还款顺序 用于还款页面计算金额使用   还款顺序 "YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,BenJin"
        String repaymentOrderStr=mfBusFincAppFeign.getRepaymentOrderStrName(fincId);
        resultMap.put("repaymentOrderStr",repaymentOrderStr);
        return resultMap;
    }
    /**
     *
     * 方法描述： 批量还款处理方法
     *
     * @return
     * @throws Exception
     *             String
     * @author ywh
     * @param ajaxList
     * @date 2019-05-13下午2:12:27
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/batchRepaymentAjax")
    @ResponseBody
    public Map<String, Object> batchRepaymentAjax(String ajaxData, String ajaxList) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> resMap = new HashMap<String, String>();
        Map<String, Object> parmMap = getMapByJson(ajaxData);
        JSONArray jsonArray = JSONArray.fromObject(ajaxList);
        parmMap.put("repayList", jsonArray);
        resMap = mfBusFincAppFeign.doBatchRepaymentOperate(parmMap);
        dataMap.put("flag", "success");
        dataMap.put("msg", "还款成功");
        return dataMap;
    }
    @RequestMapping(value = "/getFincSelectByCus")
    @ResponseBody
    public Map<String, Object> getFincSelectByCus(int pageNo, String cusNo, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            if (ajaxData != null) {
                mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            }
            mfBusFincApp.setCusNo(cusNo);
            mfBusFincApp.setFincSts("5");
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.getFincSelectByCus(ipage);
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
     * @param ajaxData
     * @return
     * @throws Exception
     * @desc 借据根据开始日期得出结束日期
     */
    @RequestMapping(value = "/getFincInststEndDateInfoMapAjax")
    @ResponseBody
    public Map<String, Object> getFincInststEndDateInfoMap(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            Map<String, String> parmMap = new HashMap<String, String>();
            String intstBeginDate = request.getParameter("intstBeginDate");//合同开始日期
            String appId = request.getParameter("appId");//申请号
            String pactId = request.getParameter("pactId");//合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天
            String term = request.getParameter("term");
            parmMap.put("intstBeginDate", intstBeginDate);
            parmMap.put("term", term);
            parmMap.put("appId", appId);
            parmMap.put("pactId", pactId);
            dataMap = mfBusFincAppFeign.getFincInststEndDateInfoMap(parmMap);

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }


    @RequestMapping(value = "/getTermByIntstEndDateAndIntstBeginDate")
    @ResponseBody
    public Map<String, Object> getTermByIntstEndDateAndIntstBeginDate(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            Map<String, Object> parmMap = new HashMap<String, Object>();
            String intstBeginDate = request.getParameter("intstBeginDate");//合同开始日期
            String appId = request.getParameter("appId");//申请号
            String pactId = request.getParameter("pactId");//合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天

            String intstEndDateShow = request.getParameter("intstEndDateShow");//借据的结束日期
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setPactId(pactId);

            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            String pactEndDateShow = mfBusPact.getEndDateShow();
            parmMap.put("pactEndDateShow", pactEndDateShow);
            parmMap.put("intstBeginDate", intstBeginDate);
            parmMap.put("intstEndDateShow", intstEndDateShow);
            parmMap.put("appId", appId);
            parmMap.put("pactId", pactId);
            dataMap = mfBusFincAppFeign.getTermByIntstEndDateAndIntstBeginDate(parmMap);

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    @RequestMapping(value = "/getextenEndDateInfoMapAjax")
    @ResponseBody
    public Map<String, Object> getextenEndDateInfoMapAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            Map<String, String> parmMap = new HashMap<String, String>();
            String intstBeginDate = request.getParameter("intstBeginDate");//合同开始日期
            String appId = request.getParameter("appId");//申请号
            String pactId = request.getParameter("pactId");//合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天
            String term = request.getParameter("term");
            parmMap.put("intstBeginDate", intstBeginDate);
            parmMap.put("term", term);
            parmMap.put("appId", appId);
            parmMap.put("pactId", pactId);
            dataMap = mfBusFincAppFeign.getextenEndDateInfoMap(parmMap);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * @Description 跳转到新贷后检查列表页面
     * @Author zhaomingguang
     * @DateTime 2019/9/18 9:06
     * @Param [model]
     * @return java.lang.String
     */
    @RequestMapping(value = "/getNewExamineStateListPage")
    public String getNewExamineStateListPage(Model model) {
        ActionContext.initialize(request, response);
        try {
            // 前台自定义的筛选组件，从数据字典中的缓存中获取
            JSONArray fincExamineStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_EXAMINE_STATS");
            model.addAttribute("fincExamineStsJsonArray", fincExamineStsJsonArray);
            model.addAttribute("query", "");
        }catch (Exception e){
            logger.error("跳转到新贷后检查列表页面失败"+e.getMessage(),e);
        }
        return "/component/pact/MfBusFincApp_NewExamineStateList";
    }

    /**
     * @Description 获取贷后检查任务列表的数据
     * @Author zhaomingguang
     * @DateTime 2019/9/25 18:33
     * @Param [pageNo, pageSize, tableId, tableType, ajaxData, ipage]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/findNewExamineStateByPageAjax")
    @ResponseBody
    public Map<String, Object> findNewExamineStateByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                          String tableType, String ajaxData,Ipage ipage) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            // 取出ajax数据
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.findExamineStateForFincByPageNew(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            logger.error("获取贷后检查任务列表的数据失败"+e.getMessage(),e);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * @Description 跳转到新增贷后检查任务页面
     * @Author zhaomingguang
     * @DateTime 2019/9/25 18:36
     * @Param [model]
     * @return java.lang.String
     */
    @RequestMapping(value = "/examineTaskInsert")
    public String examineTaskInsert(Model model){
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        try {
            String formId = "loanAfterCheckTaskBase001";
            FormData formapply0007_query = formService.getFormData(formId);
            Map<String, ParmDic> pasMinNoMap = new CodeUtils().getMapObjByKeyName("EXAMINE_DATA_OBJ");
            JSONObject json = new JSONObject();
            json.put("pasMinNo", JSONObject.fromObject(pasMinNoMap));
            model.addAttribute("ajaxData", json);
            model.addAttribute("formapply0007_query", formapply0007_query);
            model.addAttribute("query", "");
        }catch (Exception e){
            logger.error("跳转到新增贷后检查任务页面"+e.getMessage(),e);
        }
        return "/component/pact/MfBusFincApp_examineTaskInsert";
    }

    /**
     * @Description 根据客户号获取客户相关的信息
     * @Author zhaomingguang
     * @DateTime 2019/9/25 18:38
     * @Param [ajaxData]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/getExamineTaskFormAjax")
    @ResponseBody
    public Map<String,Object> getExamineTaskFormAjax(String ajaxData,String tableId){
            Map<String, Object> dataMap = new HashMap<String, Object>();
            ActionContext.initialize(request, response);
        try {
            JSONObject json = JSONObject.fromObject(ajaxData);
            Map<String, String> paraMap = (Map<String, String>) JSONObject.toBean(json, Map.class);
            String cusNo = paraMap.get("cusNo");

            // ---------------根据客户号获取客户信息--------------
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
            dataMap.put("idNum",mfCusCustomer.getIdNum());
            dataMap.put("cusTel",mfCusCustomer.getCusTel());
            //根据客户号获取其下面的贷款总额和贷款余额
            //获取该客户贷款余额
            String fincSts = "('5','6','7')";
            MfBusFincApp param = new MfBusFincApp();
            param.setCusNo(cusNo);
            param.setFincSts(fincSts);
            String amtRest = pactInterfaceFeign.getAllLoanbalByCusNoNew(param);
            //获取该客户贷款总额
            String amtSum = pactInterfaceFeign.getPutoutAmtByCusNo(param);
            dataMap.put("amtSum",MathExtend.moneyStr(amtSum));
            dataMap.put("amtRest",MathExtend.moneyStr(amtRest));
            //根据客户号判断合同是否存在逾期
            MfRepayPlan mfRepayPlan = new MfRepayPlan();
            mfRepayPlan.setCusNo(cusNo);
            mfRepayPlan.setOutFlag(BizPubParm.OUT_FLAG_3);
            List<MfRepayPlan> mfRepayPlanList = mfRepayPlanFeign.getMfBusRepayPlanListByCus(mfRepayPlan);
            if(CollectionUtils.isNotEmpty(mfRepayPlanList)){
                dataMap.put("isOverdue",BizPubParm.YES_NO_Y);
            }else{
                dataMap.put("isOverdue",BizPubParm.YES_NO_N);
            }
            //根据客户号获取其下面贷款的最差的五级分类
            String fiveclass = mfPactFiveclassFeign.getWorstMfPactFiveclassByCusNo(cusNo);
            dataMap.put("fiveClass",StringUtils.isEmpty(fiveclass)?"1":fiveclass);
            Map<String, ParmDic> fiveClassMap = new CodeUtils().getMapObjByKeyName("FIVE_STS");
            String fiveClassName = fiveClassMap.get(dataMap.get("fiveClass")).getOptName();
            dataMap.put("fiveClassName",fiveClassName);
            dataMap.put("flag", "success");

            //查询客户代偿信息
            MfBusCompensatoryConfirm mfBusCompensatoryConfirm = new MfBusCompensatoryConfirm();
            mfBusCompensatoryConfirm.setCusNo(cusNo);
            List<MfBusCompensatoryConfirm> list = mfBusCompensatoryConfirmFeign.getByPactId(mfBusCompensatoryConfirm);
            if (list!=null&&list.size()>0){
                List sideLevelList = new CodeUtils().getCacheByKeyName("INSIDE_LEVEL_H");
                dataMap.put("sideLevelList", sideLevelList);
            }else {
                List sideLevelList = new CodeUtils().getCacheByKeyName("INSIDE_LEVEL_Q");
                dataMap.put("sideLevelList", sideLevelList);
//                //保后跟踪时系统自动调用三方接口，若有新增工商处罚、法律诉讼（个人及公司）、同盾保后分值超过20分，则内部分级自动分级为预警
//                Map<String, String> threeMap = new HashMap<String, String>();
//                threeMap.put("cusNo",cusNo);
//                threeMap.put("cusName",mfCusCustomer.getCusName());
//                threeMap.put("idNum",mfCusCustomer.getIdNum());
//                threeMap.put("cusTel",mfCusCustomer.getCusTel());
//                Boolean flag =  mfExamineHisFeign.checkCallThreeChange(threeMap);
//                if(flag){
//                    dataMap.put("sideLevelFlag",flag);
//                }
            }
            MfExaminePact mfExaminePact = new MfExaminePact();
            List<MfExaminePact> mfExaminePactList = pactInterfaceFeign.getExaminePact(cusNo);
            if(mfExaminePactList.size()>0){
                dataMap.put("ifHasList", "1");
            }else{
                dataMap.put("ifHasList", "0");
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfExaminePactList, null, true);
            dataMap.put("tableData", tableHtml);
        } catch (Exception e) {
            logger.error("根据客户号获取客户相关的信息"+e.getMessage(),e);
            dataMap.put("flag", "error");
            dataMap.put("msg",e.getMessage());
        }
        return dataMap;
    }

    /**
     * @Description 跳转到选择合同号的页面
     * @Author zhaomingguang
     * @DateTime 2019/9/20 17:07
     * @Param [cusNo]
     * @return java.lang.String
     */
    @RequestMapping(value = "/getPactNoByCus")
    public String getPactNoByCus(Model model,String cusNo){
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        return "/component/pact/MfBusFincApp_pactListForSelect";
    }

    /**
     * @Description 跳转到选择借据号的页面
     * @Author zhaomingguang
     * @DateTime 2019/9/20 17:48
     * @Param [model, cusNo]
     * @return java.lang.String
     */
    @RequestMapping(value = "/getFincIdByCus")
    public String getFincIdByCus(Model model,String cusNo){
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        return "/component/pact/MfBusFincApp_fincListForSelect";
    }

    /**
     * @Description 根据客户号获取其下面所有已放款未完结的合同信息
     * @Author zhaomingguang
     * @DateTime 2019/9/20 18:00
     * @Param [pageNo, pageSize, tableId, tableType, ajaxData, cusNo]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/findPactByPageAjax")
    @ResponseBody
    public Map<String, Object> findPactByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                               String ajaxData,String cusNo) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            //如果传入cusNo,则查询该客户相关的借据
            if(StringUtil.isNotEmpty(cusNo)){
                mfBusPact.setCusNo(cusNo);
            }
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact",mfBusPact));
            ipage = mfBusPactFeign.findByPageByCusNo(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            logger.error("根据客户号获取其下面所有已放款未完结的合同信息"+e.getMessage(),e);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * @Description 根据客户号获取其下面所有已放款未完结的借据信息
     * @Author zhaomingguang
     * @DateTime 2019/9/20 18:01
     * @Param [pageNo, pageSize, tableId, tableType, ajaxData, cusNo]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/findFincByPageAjax")
    @ResponseBody
    public Map<String, Object> findFincByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData,String cusNo)  {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            //如果传入cusNo,则查询该客户相关的借据
            if(StringUtil.isNotEmpty(cusNo)){
                mfBusFincApp.setCusNo(cusNo);
            }
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
            ipage = mfBusFincAppFeign.findFincByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            logger.error("根据客户号获取其下面所有已放款未完结的借据信息"+e.getMessage(),e);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * @Description 根据合同编号获取合同相关的信息
     * @Author zhaomingguang
     * @DateTime 2019/9/20 19:31
     * @Param [pactNo]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/getPactInfoByPactNoAjax")
    @ResponseBody
    public Map<String,Object> getPactInfoByPactNoAjax(String pactNo){
        Map<String,Object> dataMap = new HashMap<>();
        try {
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setPactNo(pactNo);
            List<MfBusPact> mfBusPactList = mfBusPactFeign.getByPactNo(mfBusPact);
            if(CollectionUtils.isNotEmpty(mfBusPactList)){
                dataMap.put("mfBusPact",mfBusPactList.get(0));
            }
            dataMap.put("flag","success");
        }catch (Exception e){
            logger.error("根据合同编号获取合同相关的信息"+e.getMessage(),e);
            dataMap.put("flag","error");
            dataMap.put("msg",e.getMessage());
        }
        return dataMap;
    }

    /**
     * @Description 根据借据号获取借据相关的信息
     * @Author zhaomingguang
     * @DateTime 2019/9/20 19:31
     * @Param [fincId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/getfincInfoByFincIdAjax")
    @ResponseBody
    public Map<String,Object> getfincInfoByFincIdAjax(String fincId){
        Map<String,Object> dataMap = new HashMap<>();
        try {
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(fincId);
            MfBusFincApp resultMfBusFincApp = mfBusFincAppFeign.getMfBusFincAppById(mfBusFincApp);
            if(resultMfBusFincApp!=null){
                dataMap.put("mfBusFincApp",resultMfBusFincApp);
            }
            dataMap.put("flag","success");
        }catch (Exception e){
            logger.error("根据借据号获取借据相关的信息"+e.getMessage(),e);
            dataMap.put("flag","error");
            dataMap.put("msg",e.getMessage());
        }
        return dataMap;
    }

    /**
     * @Description 贷后检查任务新增信息保存
     * @Author zhaomingguang
     * @DateTime 2019/9/21 10:52
     * @Param [ajaxData]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/insertForExamineTaskAjax")
    @ResponseBody
    public Map<String,Object> insertForExamineTaskAjax (String ajaxData){
        Map<String,Object> dataMap = new HashMap<>();
        try {
            Map<String,Object> paraMap = getMapByJson(ajaxData);
            paraMap.put("regName",User.getRegName(request));
            paraMap.put("orgNo",User.getOrgNo(request));
            Map<String,Object> resultMap = sysTaskInfoFeign.insertForExamineTaskAjax(paraMap);
            dataMap.putAll(resultMap);
        }catch (Exception e){
            logger.error("贷后检查任务新增信息保存"+e.getMessage(),e);
            dataMap.put("flag","error");
            dataMap.put("msg",e.getMessage());
        }
        return dataMap;
    }


    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCapitalImplementListPage")
    public String getCapitalImplementListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/pact/MfCapitalImplement_List";
    }
    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCapitalImplementDetailListPage")
    public String getCapitalImplementDetailListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/pact/MfCapitalImplementDetail_List";
    }

    /***
     * 资金落实提醒列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findCapitalImplementWarningByPageAjax")
    @ResponseBody
    public Map<String, Object> findCapitalImplementWarningByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCapitalImplementWarning mfCapitalImplementWarning = new MfCapitalImplementWarning();
        try {
            mfCapitalImplementWarning.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCapitalImplementWarning.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfCapitalImplementWarning.setCriteriaList(mfCapitalImplementWarning, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCapitalImplementWarning", mfCapitalImplementWarning));
            ipage = mfBusFincAppFeign.findCapitalImplementWarningByPage(ipage);
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
    /***
     * 资金落实登记列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findCapitalImplementDetailByPageAjax")
    @ResponseBody
    public Map<String, Object> findCapitalImplementDetailByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCapitalImplementDetail mfCapitalImplementDetail = new MfCapitalImplementDetail();
        try {
            mfCapitalImplementDetail.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCapitalImplementDetail.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfCapitalImplementDetail.setCriteriaList(mfCapitalImplementDetail, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCapitalImplementDetail", mfCapitalImplementDetail));
            ipage = mfBusFincAppFeign.findCapitalImplementDetailByPage(ipage);
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
    @RequestMapping(value = "/findCapitalImplementDetailExcel")
    @ResponseBody
    public void findCapitalImplementDetailExcel(String tableId) throws Exception {
        //读取出文件的所有字节,并将所有字节写出给客户端
        ActionContext.initialize(request, response);
        MfCapitalImplementDetail mfCapitalImplementDetail = new MfCapitalImplementDetail();
        try {
            List<MfCapitalImplementDetail> mfCapitalImplementDetailList = mfBusFincAppFeign.findCapitalImplementDetailExcel(mfCapitalImplementDetail);
            ExpExclUtil eu = new ExpExclUtil();
            HSSFWorkbook wb = eu.expExclTableForList(tableId, mfCapitalImplementDetailList, null, false, "");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download; charset=utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("export.xls", "UTF-8"));
            OutputStream stream = response.getOutputStream();
            wb.write(stream);
            wb.close();// HSSFWorkbook关闭
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 方法描述： 获取收信息
     *
     * @return
     * @throws Exception String
     * @author Javelin
     * @date 2017-7-28 上午10:25:14
     */
    @RequestMapping(value = "/getCapitalDetaiListByFincIdAjax")
    @ResponseBody
    public Map<String, Object> getCapitalDetaiListByFincIdAjax(String fincId, String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 获取收益计划 信息
        MfCapitalImplementDetail mfCapitalImplementDetail = new MfCapitalImplementDetail();
        mfCapitalImplementDetail.setFincId(fincId);
        List<MfCapitalImplementDetail> mfCapitalImplementDetailList = mfBusFincAppFeign.getCapitalDetaiListByFincId(mfCapitalImplementDetail);
        if (StringUtil.isEmpty(tableId)) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("列表编号tableId"));
        } else {
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfCapitalImplementDetailList, null, true);
            dataMap.put("htmlStr", tableHtml);
            dataMap.put("flag", "success");
        }
        return dataMap;
    }
    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/capitalInput")
    public String capitalInput(Model model, String warningId,String entrance) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfCapitalImplementWarning mfCapitalImplementWarning = new MfCapitalImplementWarning();
        mfCapitalImplementWarning.setWarningId(warningId);
        mfCapitalImplementWarning = mfBusFincAppFeign.getCapitalById(mfCapitalImplementWarning);
        MfCapitalImplementDetail mfCapitalImplementDetail = new MfCapitalImplementDetail();

        if(mfCapitalImplementWarning!=null){
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(mfCapitalImplementWarning.getFincId());
            MfBusFincApp resultMfBusFincApp = mfBusFincAppFeign.getMfBusFincAppById(mfBusFincApp);

            PropertyUtils.copyProperties(mfCapitalImplementDetail, mfCapitalImplementWarning);
            mfCapitalImplementDetail.setTerm(resultMfBusFincApp.getTermMonth());
            mfCapitalImplementDetail.setKindNo(resultMfBusFincApp.getKindNo());
            mfCapitalImplementDetail.setKindName(resultMfBusFincApp.getKindName());
            mfCapitalImplementDetail.setGuaranteAmt(mfCapitalImplementWarning.getWarningAmt());
            mfCapitalImplementDetail.setCusMngNo(resultMfBusFincApp.getManageOpNo1());
            mfCapitalImplementDetail.setCusMngName(resultMfBusFincApp.getManageOpName1());
            mfCapitalImplementDetail.setSybOpNo(resultMfBusFincApp.getManageBrNo1());
            mfCapitalImplementDetail.setSybOpName(resultMfBusFincApp.getManageBrName1());
            FormData formcapitalbase = formService.getFormData("capitalbase");
            getObjValue(formcapitalbase, mfCapitalImplementDetail);
            model.addAttribute("formcapitalbase", formcapitalbase                               );
            model.addAttribute("query", "");
            if(StringUtil.isNotEmpty(entrance)&&entrance.equals("1")){
                model.addAttribute("entrance", "task");
            }else{
                model.addAttribute("entrance", "loanAfter");
            }
        }
        return "/component/pact/MfCapitalImplementDetail_Insert";
    }
    /**
     * 资金落实详情
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/capitalDetail")
    public String capitalInput(Model model, String warningId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfCapitalImplementDetail mfCapitalImplementDetail = new MfCapitalImplementDetail();
        mfCapitalImplementDetail.setWarningId(warningId);
        mfCapitalImplementDetail = mfBusFincAppFeign.getCapitalDetailById(mfCapitalImplementDetail);
        if(mfCapitalImplementDetail!=null){
            FormData formcapitaldetail = formService.getFormData("capitaldetail");
            getObjValue(formcapitaldetail, mfCapitalImplementDetail);
            model.addAttribute("formcapitaldetail", formcapitaldetail);
            model.addAttribute("query", "");
        }
        return "/component/pact/MfCapitalImplementDetail_Detail";
    }
    /**
     *
     * 新增资金落实
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/insertCapitalAjax")
    @ResponseBody
    public Map<String, Object> insertCapitalAjax(String ajaxData, String query) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            Map<String,Object> map = getMapByJson(ajaxData);
            FormData formcapitalbase = formService.getFormData("capitalbase");
            getFormValue(formcapitalbase, map);
            if (this.validateFormData(formcapitalbase)) {
                MfCapitalImplementDetail mfCapitalImplementDetail = new MfCapitalImplementDetail();
                setObjValue(formcapitalbase, mfCapitalImplementDetail);
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setFincId(mfCapitalImplementDetail.getFincId());
                mfBusFincApp =mfBusFincAppFeign.getById(mfBusFincApp);
                if(mfBusFincApp!=null){
                    mfCapitalImplementDetail.setPactNo(mfBusFincApp.getPactNo());
                    mfCapitalImplementDetail.setTerm(mfBusFincApp.getTermMonth());
                    mfCapitalImplementDetail.setCusMngBrNo(mfBusFincApp.getManageBrNo1());
                    mfCapitalImplementDetail.setCusMngBrName(mfBusFincApp.getManageBrName1());
                }
                mfBusFincAppFeign.insertCapitalAjax(mfCapitalImplementDetail);
                dataMap.put("flag", "success");
                dataMap.put("msg", "登记资金落实成功！");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }

        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }


    /**
     * 放款解除
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/putOutCancelAjax")
    @ResponseBody
    public Map<String, Object> putOutCancelAjax(Model model, String ajaxData, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);

        Map<String, Object> dataMap = new HashMap<String, Object>();

        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        try {
            dataMap.put("mfBusFincApp",mfBusFincApp);
            dataMap.put("fincId",fincId);
            dataMap.put("opNo",User.getRegNo(request));
            Result res = mfBusFincAppFeign.putOutCancel(dataMap);
            dataMap = new HashMap<String, Object>();
            if (res.isSuccess()) {
                dataMap.put("flag", "success");
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
}
