package app.component.pact.repayplan.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplyFincJxhj;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfBusApplyFincJxhjFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.entity.MfRepayPlanParameter;
import app.component.calccoreinterface.CalcRepayPlanInterfaceFeign;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.feign.MfCusBankAccManageFeign;
import app.component.finance.bankacc.feign.CwCusBankAccManageFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppChild;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppChildFeign;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pact.receaccount.entity.MfBusReceAndFinc;
import app.component.pact.receaccount.entity.MfBusReceBaseInfo;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.feign.MfBusFincAppMainFeign;
import app.component.pact.receaccount.feign.MfBusReceBaseInfoFeign;
import app.component.pact.receaccount.feign.MfBusReceTransferFeign;
import app.component.pact.repayplan.feign.MfRepayPlanFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.pactinterface.PactRepayPlanInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.thirdpaygatewayinterface.ThirdPayGateWayInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WkfInterfaceFeign;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
 * MfRepayPlanAction
 *
 * @author Jiasl 2017-06-02
 */
@Controller
@RequestMapping("/mfRepayPlan")
public class MfRepayPlanController extends BaseFormBean {
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;// 注入合同接口
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;// 注入借据接口
    @Autowired
    private MfBusPactFeign mfBusPactFeign;// 注入借据接口
    @Autowired
    private CalcRepayPlanInterfaceFeign calcRepayPlanInterfaceFeign;// 注入规则接口
    @Autowired
    private MfRepayPlanFeign mfRepayPlanFeign;// 注入还款计划
    @Autowired
    private AppInterfaceFeign appInterfaceFeign;
    @Autowired
    private MfBusFincAppChildFeign mfBusFincAppChildFeign;// 注入借据子表
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private PactRepayPlanInterfaceFeign pactRepayPlanInterfaceFeign;
    @Autowired
    private ThirdPayGateWayInterfaceFeign thirdPayGateWayInterfaceFeign;
    @Autowired
    private WkfBusInterfaceFeign wkfBusInterfaceFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private MfBusFincAppMainFeign mfBusFincAppMainFeign;
    @Autowired
    private MfBusReceTransferFeign mfBusReceTransferFeign;
    @Autowired
    private MfBusReceBaseInfoFeign mfBusReceBaseInfoFeign;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private CwCusBankAccManageFeign cwCusBankAccManageFeign;
    @Autowired
    private MfCusBankAccManageFeign mfCusBankAccManageFeign;

    @Autowired
    private MfCusBankAccManageFeign cusBankAccMangeFeign;

    @Autowired
    private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;

    @Autowired
    private MfBusApplyFincJxhjFeign mfBusApplyFincJxhjFeign;
    private static Gson gson = new Gson();
    @Autowired
    private app.component.calc.core.feign.MfRepayPlanFeign mfRepayPlanFeign2;// 注入还款计划
//	@Autowired
//	private MfBusFincAppMain
    // 表单

    /**
     * 方法描述： 还款计划页面
     *
     * @param appId
     * @return
     * @throws Exception String
     * @author jiasl
     * @date 2017-6-02 上午10:39:07
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/repayPlanList")
    public String repayPlanList(Model model, String ajaxData, String appId, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        List<MfRepayPlan> repayPlanList = new ArrayList<MfRepayPlan>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> mapParm = new HashMap<String, String>();
        Map<String, String> delayDateMap = new HashMap<String, String>();
        MfBusAppKind mfBusAppKind = null;

        MfBusPact mfBusPact = null;
        MfBusFincAppChild mfBusFincAppChild = null;
        MfBusAppKind mfBusAppKindShow = null;
        FormData formrepayplanlist0001 = null;
        FormData formrepayplanlist0002 = null;
        String pactId  = "";
        if (StringUtil.isNotEmpty(appId)) {
            // 调用封装的还款计划方法
            if (StringUtil.isNotEmpty(fincId)) {
                mapParm.put("fincId", fincId);
            }
            dataMap = mfRepayPlanFeign.getRepayPlanList(appId, mapParm);
            repayPlanList = (List<MfRepayPlan>) dataMap.get("repayPlanList");// 还款计划列表
            mfBusFincAppChild = (MfBusFincAppChild) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusFincAppChild"), MfBusFincAppChild.class);// 借据实体
            fincId = mfBusFincAppChild.getFincId();
            mfBusPact = (MfBusPact) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusPact"), MfBusPact.class);// 合同信息
            pactId = mfBusPact.getPactId();
            String cusNo = mfBusPact.getCusNo();
            mfBusPact.setBeginDate(DateUtil.getShowDateTime(mfBusPact.getBeginDate()));// 合同开始日期
            String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.review_finc, null, null, User.getRegNo(request));
//            formId = "repayPlanBase";
//            formrepayplanlist0001 = formService.getFormData(formId);

            formrepayplanlist0002 = formService.getFormData("repayPlanRightBase");

            mfBusAppKind = (MfBusAppKind) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusAppKind"), MfBusAppKind.class);// 参数实体
            Map<String, Object> dataAppKindShowMap = (Map<String, Object>) dataMap.get("dataAppKindShowMap");
            mfBusAppKindShow = (MfBusAppKind) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusAppKindShow"), MfBusAppKind.class);// 参数展示实体
            delayDateMap = (Map<String, String>) dataMap.get("delayDateMap");// 顺延日期
            Gson gson = new Gson();
            // 还款计划发放日期控制：0：借据起息日期（可选范围：借据起息日到当前日期） 1：当前时间（可选范围：无控制）
            String repayPlanStartDate = new CodeUtils().getSingleValByKey("REPAY_PLAN_DATE_SET");
            //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
            String mfBusEditorRepayplanFlag = (String) dataMap.get("mfBusEditorRepayplanFlag");
            model.addAttribute("repayPlanStartDate", repayPlanStartDate);
            model.addAttribute("repayPlanList", repayPlanList);
            model.addAttribute("delayDateMap", gson.toJson(delayDateMap));
            mfBusPact.setFincRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfBusPact.getFincRate(),
                    Integer.parseInt(mfBusAppKind.getYearDays()),
                    Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
            mfBusPact.setOverRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfBusPact.getOverRate(),
                    Integer.parseInt(mfBusAppKind.getYearDays()),
                    Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
            model.addAttribute("pactId", pactId);
            model.addAttribute("cusNo", cusNo);
            model.addAttribute("fincId", fincId);
            model.addAttribute("mfBusEditorRepayplanFlag", mfBusEditorRepayplanFlag);
        }
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        if(StringUtil.isEmpty(mfBusApply.getIsCreditFlag())){
            mfBusApply.setIsCreditFlag("0");
        }
        getObjValue(formrepayplanlist0002, mfBusApply);
        getObjValue(formrepayplanlist0002, mfBusAppKind);
        getObjValue(formrepayplanlist0002, mfBusPact) ;
        //数据初始化展示
        if(StringUtil.isNotEmpty(pactId)){
            if(StringUtil.isEmpty(mfBusApply.getCreditAppId())){
                //如果时二次放款
                MfBusFincApp mfBusFincAppQuery = new MfBusFincApp();
                mfBusFincAppQuery.setPactId(pactId);
                List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getMfBusFincAppListAllByPactId(mfBusFincAppQuery);
                if(mfBusFincAppList.size()>0){
                    MfBusFincApp mfBusFincApp = mfBusFincAppList.get(0);
                    getObjValue(formrepayplanlist0002, mfBusFincApp);
                }
            }else{
                //如果时二次放款
                MfBusFincApp mfBusFincAppQuery = new MfBusFincApp();
                mfBusFincAppQuery.setPactId(pactId);
                List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getMfBusFincAppListAllByPactId(mfBusFincAppQuery);
                if(mfBusFincAppList.size()>0){
                    MfBusFincApp mfBusFincApp = mfBusFincAppList.get(0);
                    if(StringUtil.isNotEmpty(mfBusFincApp.getCreditBeginDate())){
                        model.addAttribute("creditBeginDate", mfBusFincApp.getCreditBeginDate().substring(0,4)+"-"+mfBusFincApp.getCreditBeginDate().substring(4,6)+"-"+mfBusFincApp.getCreditBeginDate().substring(6,8));
                    }
                    if(StringUtil.isNotEmpty(mfBusFincApp.getCreditEndDate())){
                        model.addAttribute("creditEndDate", mfBusFincApp.getCreditEndDate().substring(0,4)+"-"+mfBusFincApp.getCreditEndDate().substring(4,6)+"-"+mfBusFincApp.getCreditEndDate().substring(6,8));
                    }
                    if(StringUtil.isNotEmpty(mfBusFincApp.getZgeSignDate())){
                        model.addAttribute("zgeSignDate", mfBusFincApp.getZgeSignDate().substring(0,4)+"-"+mfBusFincApp.getZgeSignDate().substring(4,6)+"-"+mfBusFincApp.getZgeSignDate().substring(6,8));
                    }
                    getObjValue(formrepayplanlist0002, mfBusFincApp);
                }else {
                    //取授信下得上一笔统合0
                    MfBusApply mfBusApplyQuery =new MfBusApply();
                    mfBusApplyQuery.setCusNo(mfBusApply.getCusNo());
                    mfBusApplyQuery.setAgenciesId(mfBusApply.getAgenciesId());
                    mfBusApplyQuery.setCreditAppId(mfBusApply.getCreditAppId());
                    List<MfBusApply> list = mfBusApplyFeign.getApplyList(mfBusApplyQuery);
                    if (list.size()>0){
                        for (int i = 0; i < list.size(); i++) {
                            mfBusApplyQuery = list.get(i);
                            if(!mfBusApply.getAppId().equals(mfBusApplyQuery.getAppId())){
                                //如果时二次放款
                                MfBusFincApp mfBusFincAppQuery2 = new MfBusFincApp();
                                mfBusFincAppQuery2.setPactId(mfBusApplyQuery.getAppId());
                                List<MfBusFincApp> mfBusFincAppList2 = mfBusFincAppFeign.getMfBusFincAppListAllByPactId(mfBusFincAppQuery2);
                                if(mfBusFincAppList2.size()>0){
                                    MfBusFincApp mfBusFincApp = mfBusFincAppList.get(1);
                                    if(StringUtil.isNotEmpty(mfBusFincApp.getCreditBeginDate())){
                                        model.addAttribute("creditBeginDate", mfBusFincApp.getCreditBeginDate().substring(0,4)+"-"+mfBusFincApp.getCreditBeginDate().substring(4,6)+"-"+mfBusFincApp.getCreditBeginDate().substring(6,8));
                                    }
                                    if(StringUtil.isNotEmpty(mfBusFincApp.getCreditEndDate())){
                                        model.addAttribute("creditEndDate", mfBusFincApp.getCreditEndDate().substring(0,4)+"-"+mfBusFincApp.getCreditEndDate().substring(4,6)+"-"+mfBusFincApp.getCreditEndDate().substring(6,8));
                                    }
                                    if(StringUtil.isNotEmpty(mfBusFincApp.getZgeSignDate())){
                                        model.addAttribute("zgeSignDate", mfBusFincApp.getZgeSignDate().substring(0,4)+"-"+mfBusFincApp.getZgeSignDate().substring(4,6)+"-"+mfBusFincApp.getZgeSignDate().substring(6,8));
                                    }
                                    getObjValue(formrepayplanlist0002, mfBusFincApp);
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }






        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formrepayplanlist0002, "fincRate", "unit", rateUnit);
//        this.changeFormProperty(formrepayplanlist0002, "bankRate", "unit", rateUnit);
        CodeUtils codeUtil = new CodeUtils();
        //发放日期、到期日期日期控制。0-可以修改日期 1-不可以修改日期
        String putDateRead = codeUtil.getSingleValByKey("PUT_DATE_READ");
        //获取还款计划结束日期是否需要通过参数控制减一的所需的参数  0 不需要修改  1 需要每一期的结束日期减一
        String repayPlanShowFlag = codeUtil.getSingleValByKey("REPAY_PLAN_SHOW_FLAG");
        if (BizPubParm.REPAYPLANSHOWFLAG_1.equals(repayPlanShowFlag)) {
            mfBusFincAppChild.setIntstBeginDate(mfBusPact.getBeginDate());
            mfBusFincAppChild.setIntstEndDate(DateUtil.getShowDateTime(DateUtil.addByDay(mfBusPact.getEndDate(),-1)));
        }
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);// 当前任务节点task
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前任务节点编号

        getObjValue(formrepayplanlist0002, mfBusFincAppChild);
        //将申请类型存入表单隐藏域nKind控制是否自动结清上笔贷款的选择框显示与否
//        Map<String, String> loanKind = new HashMap<>();
//        loanKind.put("loanKind", mfBusApply.getLoanKind());
//        getObjValue(formrepayplanlist0002, loanKind);
        this.changeFormProperty(formrepayplanlist0002, "endDate", "optionArray", delayDateMap);
        //从数据字典中获取借款编码状态
        String borrowCodeType = codeUtil.getSingleValByKey("BORROW_CODE_TYPE");
        model.addAttribute("borrowCodeType", borrowCodeType);
//        model.addAttribute("formrepayplanlist0001", formrepayplanlist0001);
        model.addAttribute("formrepayplanlist0002", formrepayplanlist0002);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("mfBusAppKind", mfBusAppKind);
        model.addAttribute("mfBusAppKindShow", mfBusAppKindShow);
        model.addAttribute("mfBusFincAppChild", mfBusFincAppChild);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("putDateRead", putDateRead);
        model.addAttribute("query", "");
        model.addAttribute("appId", appId);
        model.addAttribute("fincId", fincId);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        return "component/pact/repayplan/MfRepayPlan_RepayPlanList";
    }

    /**
     * 方法描述： 三方放款（还款计划页面）
     *
     * @param appId
     * @return
     * @throws Exception String
     * @author haoyang
     * @date 2018-10-31 上午10:39:07
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/thirdPayRepayPlanList")
    public String thirdPayRepayPlanList(Model model, String ajaxData, String appId) throws Exception {
        //调用 原还款计划 方法（此处处理逻辑相同，返回的页面不同，为不影响原有方法，故新增此方法）
        repayPlanList(model, ajaxData, appId, "");

        //封装网关代发网关数据，查询支付通道
        List<Map<String, Object>> channels = Lists.newArrayList();
        Map<String, Object> gatewayResultMap = thirdPayGateWayInterfaceFeign.getChannelConfigList();//接收网关返回信息
        String resultCode = (String) gatewayResultMap.get("resultCode");
        if ("0000".equals(resultCode)) {//请求成功
            channels = ((List<Map<String, Object>>) gatewayResultMap.get("channels"));
        }
        model.addAttribute("channels", JSONArray.fromObject(channels));
        return "component/pact/repayplan/MfRepayPlan_ThirdPayRepayPlanList";
    }

    /**
     * 方法描述： 保理融资还款计划页面
     *
     * @param appId
     * @return
     * @throws Exception String
     * @author jiasl
     * @date 2017-6-02 上午10:39:07
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/repayPlanListForAccount")
    public String repayPlanListForAccount(Model model, String ajaxData, String appId, String fincMainId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String nodeNo = WKF_NODE.review_finc.getNodeNo();// 功能节点编号

        MfBusAppKind mfBusAppKind = new MfBusAppKind();

        MfBusPact mfBusPact = new MfBusPact();
        FormData formrepayplanlist0001 = null;
        if (StringUtil.isNotEmpty(appId) && StringUtil.isNotEmpty(fincMainId)) {
            MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
            mfBusFincAppMain.setFincMainId(fincMainId);
            mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
            if (mfBusFincAppMain != null) {
                String pactId = mfBusFincAppMain.getPactId();
                mfBusPact.setPactId(pactId);
                mfBusPact = pactInterfaceFeign.getById(mfBusPact);
                String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.review_finc, null, null, User.getRegNo(request));
                formrepayplanlist0001 = formService.getFormData(formId);
                mfBusAppKind.setAppId(appId);
                mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
                //处理利率
                mfBusFincAppMain.setFincRate(MathExtend.showRateMethod(mfBusFincAppMain.getRateType(), mfBusFincAppMain.getFincRate(),
                        Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
                mfBusFincAppMain.setOverRate(MathExtend.showRateMethod(mfBusFincAppMain.getRateType(), mfBusFincAppMain.getOverRate(),
                        Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
                getObjValue(formrepayplanlist0001, mfBusAppKind);
                getObjValue(formrepayplanlist0001, mfBusPact);
                getObjValue(formrepayplanlist0001, mfBusFincAppMain);

                // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
                Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
                String rateUnit = rateTypeMap.get(mfBusFincAppMain.getRateType()).getRemark();
                this.changeFormProperty(formrepayplanlist0001, "fincRate", "unit", rateUnit);
                mfBusPact.setFincRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfBusPact.getFincRate(),
                        Integer.parseInt(mfBusAppKind.getYearDays()),
                        Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
                mfBusPact.setOverRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfBusPact.getOverRate(),
                        Integer.parseInt(mfBusAppKind.getYearDays()),
                        Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));


                List<MfBusFincApp> mfBusFincAppList = new ArrayList<MfBusFincApp>();
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setFincMainId(fincMainId);
                mfBusFincAppList = mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
                List<MfBusReceAndFinc> mfBusReceAndFincList = new ArrayList<MfBusReceAndFinc>();
                if (mfBusFincAppList != null) {
                    for (int i = 0; i < mfBusFincAppList.size(); i++) {
                        mfBusFincApp = mfBusFincAppList.get(i);
                        String tranId = mfBusFincApp.getTransId();
                        if (StringUtil.isNotEmpty(tranId)) {
                            MfBusReceAndFinc mfBusReceAndFinc = new MfBusReceAndFinc();
                            mfBusReceAndFinc.setFincId(mfBusFincApp.getFincId());
                            mfBusReceAndFinc.setFincMainId(mfBusFincAppMain.getFincMainId());
                            mfBusReceAndFinc.setFincShowId(mfBusFincApp.getFincShowId());
                            mfBusReceAndFinc.setPutoutAmt(mfBusFincApp.getPutoutAmt());
                            MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
                            mfBusReceTransfer.setTransId(tranId);
                            mfBusReceAndFinc.setReceTransAmt(mfBusReceTransfer.getReceTransAmt());//转让金额
                            mfBusReceTransfer = mfBusReceTransferFeign.getById(mfBusReceTransfer);
                            if (mfBusReceTransfer != null) {
                                String receId = mfBusReceTransfer.getReceId();
                                MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
                                mfBusReceBaseInfo.setReceId(receId);
                                mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
                                if (mfBusReceBaseInfo != null) {
                                    mfBusReceAndFinc.setReceId(mfBusReceBaseInfo.getReceId());
                                    mfBusReceAndFinc.setAppId(mfBusFincApp.getAppId());//申请编号
                                    mfBusReceAndFinc.setReceNo(mfBusReceBaseInfo.getReceNo());//合同号/发票号
                                    mfBusReceAndFinc.setReceName(mfBusReceBaseInfo.getReceName());//账款名称
                                    mfBusReceAndFinc.setReceInvoiceAmt(mfBusReceBaseInfo.getReceInvoiceAmt());//合同/发票金额
                                    mfBusReceAndFinc.setReceAmt(mfBusReceBaseInfo.getReceAmt());//应收账款金额
                                    mfBusReceAndFinc.setReceTransAmt(mfBusReceBaseInfo.getReceTransAmt());
                                    mfBusReceAndFinc.setReceBeginDate(mfBusReceBaseInfo.getReceBeginDate());//合同/发票日
                                    mfBusReceAndFinc.setReceEndDate(mfBusReceBaseInfo.getReceEndDate());//账款到期日
                                    mfBusReceAndFincList.add(mfBusReceAndFinc);
                                }
                            }
                        }

                    }
                }
                JsonTableUtil jtu = new JsonTableUtil();
                Ipage ipage = this.getIpage();
                String tableHtml = jtu.getJsonStr("tablerepayplanfinclist", "thirdTableTag", mfBusReceAndFincList, ipage, true);
                JSONObject json = new JSONObject();
                json.put("tableHtml", tableHtml);
                model.addAttribute("ajaxData", json.toString());
            }
        }
        model.addAttribute("formrepayplanlist0001", formrepayplanlist0001);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("query", "");
        model.addAttribute("appId", appId);
        return "component/pact/repayplan/MfRepayPlan_RepayPlanListForAccount";
    }

    /**
     * 方法描述： 保理融资还款计划页面试算还款计划展示
     *
     * @param appId
     * @return
     * @throws Exception String
     * @author jiasl
     * @date 2017-6-02 上午10:39:07
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getPlanListForAccountByFincId")
    public String getPlanListForAccountByFincId(Model model, String fincId) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("fincId", fincId);
        return "component/pact/MfRepayPlanListShow";
    }

    /**
     * 方法描述： 保理融资还款计划页面试算还款计划展示数据获取
     *
     * @param appId
     * @return
     * @throws Exception String
     * @author jiasl
     * @date 2017-6-02 上午10:39:07
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getPlanListForAccountByFincIdAjax")
    @ResponseBody
    public Map<String, Object> getPlanListForAccountByFincIdAjax(String ajaxData, int pageNo, String tableId, String tableType, String fincId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(30);
            //自定义查询Feign方法
            if (StringUtil.isNotEmpty(fincId)) {
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setFincId(fincId);
                mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
                if (mfBusFincApp != null) {
                    String appId = mfBusFincApp.getAppId();
                    if (StringUtil.isNotEmpty(appId)) {
                        MfBusAppKind mfBusAppKind = new MfBusAppKind();
                        mfBusAppKind.setAppId(appId);
                        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
                        MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
                        mfBusFincAppChild.setFincId(fincId);
                        mfBusFincAppChild = mfBusFincAppChildFeign.getById(mfBusFincAppChild);
                        Map<String, Object> parmMap = new HashMap<String, Object>();
                        parmMap.put("mfBusAppKind", mfBusAppKind);
                        parmMap.put("mfBusFincAppChild", mfBusFincAppChild);
                        Map<String, Object> resultMap = pactRepayPlanInterfaceFeign.getMfRepayPlanListByAppId(parmMap);
                        JSONArray repayList = JSONArray.fromObject(resultMap.get("repayPlanList"));
                        List<MfRepayPlan> mfRepayPlanList = new ArrayList<MfRepayPlan>();
                        for (int i = 0; i < repayList.size(); i++) {
                            MfRepayPlan mfRepayPlan = JsonStrHandling.handlingStrToBean(repayList.get(i), MfRepayPlan.class);
                            mfRepayPlanList.add(mfRepayPlan);
                        }
                        ipage.setPageCounts(mfRepayPlanList.size());
                        JsonTableUtil jtu = new JsonTableUtil();
                        String tableHtml = jtu.getJsonStr(tableId, tableType, mfRepayPlanList, ipage, true);
                        ipage.setResult(tableHtml);
                        dataMap.put("ipage", ipage);
                    }
                }
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
     * 保存还款计划 保存还款计划
     *
     * @param appId
     * @param pageSize
     * @param pageNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repayPlanAjax")
    @ResponseBody
    public Map<String, Object> repayPlanAjax(String ajaxData, String appId, String fincId, Integer pageSize, Integer pageNo,String nodeNoOld)
            throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //检查该笔借据是否已发起三方放款申请
        Map<String, String> thirdPayDataMap = new HashMap<String, String>();
        MfBusFincApp mfBusFincAppCheck = new MfBusFincApp();
        mfBusFincAppCheck.setFincId(fincId);
        mfBusFincAppCheck.setAppId(appId);
        mfBusFincAppCheck = mfBusFincAppFeign.getById(mfBusFincAppCheck);
        thirdPayDataMap = mfRepayPlanFeign.checkPutoutApplyInfoByFincId(mfBusFincAppCheck.getFincId());
        String code = (String) thirdPayDataMap.get("code");
        if ("9999".equals(code)) {//不允许再次放款
            dataMap.put("code", thirdPayDataMap.get("code"));
            dataMap.put("msg", thirdPayDataMap.get("msg"));
            return dataMap;
        }

        String beginDate = request.getParameter("beginDate");// 借据开始日期
        String endDate = request.getParameter("endDate");// 借据到期日期
        String planListData = request.getParameter("planListData");// 还款计划列表数据
        String planListSize = request.getParameter("planListSize");// 还款计划期数
        String repayFeeSum = request.getParameter("repayFeeSum");// 提前收取的费用总和
        String repayIntstSum = request.getParameter("repayIntstSum");// 提前收取的利息总和
        String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");// 多次放款还款计划合并：1-启用、0-禁用
        String interestCollectType = request.getParameter("interestCollectType");// 利息收息方式：1-上收息 2-下收息
        String borrowCode = request.getParameter("borrowCode");//获取借款编码的值
        String autoSettledAccount = request.getParameter("autoSettledAccount");
        String putoutAmtRealFormat = request.getParameter("putoutAmtRealFormat");//银行实际发放金额
        //判断放款金额和还款计划本金
        if(StringUtil.isNotEmpty(putoutAmtRealFormat)){
            Map<String, String> formMap = CwPublicUtil.getMapByJson(planListData);
            putoutAmtRealFormat = putoutAmtRealFormat.replaceAll(",","");
            Double putoutAmt = Double.parseDouble(putoutAmtRealFormat);
            Double prcp=0.00;
            int count=new Integer(planListSize).intValue();//list的条数
            for(int i=1;i<=count;i++){
                prcp = MathExtend.add(prcp,Double.parseDouble(formMap.get("repayPrcp"+i)));
            }
            if(putoutAmt-prcp>0||putoutAmt-prcp<0){
                dataMap.put("flag", "error");
                dataMap.put("msg", "银行实际放款金额和还款计划之和不等，请调整！");
                return dataMap;
            }
        }
        Map map = getMapByJson(ajaxData);
        try {
            // "putOutChargeIntstFlag":putOutChargeIntstFlag,"putOutChargeIntst":putOutChargeIntst},
            String putOutChargeIntstFlag = request.getParameter("putOutChargeIntstFlag");// 0 表示不是放款时收取 1 表示 是固定还款日
            // 且是放款时收取
            String putOutChargeIntst = request.getParameter("putOutChargeIntst");// 放款时收取的利息(存在固定还款日时)
            String putOutChargeFee = request.getParameter("putOutChargeFee");// 放款时收取的一次性费用
            String feeCollectWay = request.getParameter("feeCollectWay");// 费用收息方式：1-上收费 2下收费
            Map<String, Object> putOutFeeMap = new HashMap<String, Object>();
            putOutFeeMap.put("putOutChargeFee", putOutChargeFee);
            putOutFeeMap.put("feeCollectWay", feeCollectWay);
            // 借据主表
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            FormData formfincapp0003 = formService.getFormData("repayPlanRightBase");
            getFormValue(formfincapp0003, map);
            setObjValue(formfincapp0003, mfBusFincApp);
            mfBusFincApp.setFincId(fincId);
            mfBusFincApp.setAppId(appId);
            // 是否用连连支付自动放款 0：不自动放款 1：自动放款 mahao 20171107
            if(StringUtil.isNotEmpty(putoutAmtRealFormat)) {
                putoutAmtRealFormat = putoutAmtRealFormat.replaceAll(",", "");
                mfBusFincApp.setPutoutAmt(Double.parseDouble(putoutAmtRealFormat));
                mfBusFincApp.setLoanBal(mfBusFincApp.getPutoutAmt());
                mfBusFincApp.setPutoutAmtReal(mfBusFincApp.getPutoutAmt());
            }

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("mfBusFincApp", JSONObject.fromObject(mfBusFincApp));
            paramMap.put("putOutFeeMap", putOutFeeMap);
            paramMap.put("planListData", planListData);
            MfBusPact busPact = new MfBusPact();
            busPact.setAppId(appId);
            busPact = mfBusPactFeign.getById(busPact);
            TaskImpl taskApprove = wkfInterfaceFeign.getTask(busPact.getWkfAppId(), null);
            //判断当前节点是否与流程相符
            if(!nodeNoOld.equals(taskApprove.getActivityName())){//不相等则节点不在此环节
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
                return dataMap;
            }
                // 保存还款计划并流转到下一审批流程
                //合同信息
                MfBusPact mfBusPact = new MfBusPact();
                mfBusPact.setAppId(appId);
                mfBusPact = mfBusPactFeign.getById(mfBusPact);
                TaskImpl task = wkfInterfaceFeign.getTask(mfBusPact.getWkfAppId(), null);
                String nodeNo = task.getActivityName();

                //批量放款申请还款计划保存方法
                if (BizPubParm.WKF_NODE.review_finc_batch.getNodeNo().equals(nodeNo)) {
                    dataMap = mfRepayPlanFeign.doApprovalProcessForBatch(beginDate, endDate, "",
                            planListSize, repayFeeSum, repayIntstSum, multipleLoanPlanMerge, interestCollectType,
                            putOutChargeIntstFlag, putOutChargeIntst, paramMap);
                } else {
                    dataMap = mfRepayPlanFeign.doApprovalProcess(beginDate, endDate, "",
                            planListSize, repayFeeSum, repayIntstSum, multipleLoanPlanMerge, interestCollectType,
                            putOutChargeIntstFlag, putOutChargeIntst, paramMap);
                }


                String flag = String.valueOf(dataMap.get("flag"));
                if ("success".equals(dataMap.get("flag"))) {
                    // 获取还款计划
                    Ipage ipage = this.getIpage();
                    ipage.setPageSize(pageSize);
                    ipage.setPageNo(pageNo);// 异步传页面翻页参数
                    JsonTableUtil jtu = new JsonTableUtil();
                    String tableHtml = jtu.getJsonStr("tablerepayplan0003", "tableTag", (List) dataMap.get("repayPlanList"),
                            ipage, true);
                    dataMap.put("tableHtml", tableHtml);
                }
            if ("success".equals(dataMap.get("flag"))) {
                String infoMsg = dataMap.containsKey("infoMsg") ? dataMap.get("infoMsg").toString() : "";
                dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("放款") + " 业务进入保后阶段"
                        + (StringUtil.isNotEmpty(infoMsg) ? "," + infoMsg : ""));
                dataMap.put("flag", "success");
            }
            /*20190328 yxl 当业务类型为借新还旧时，选择了自动结清上笔借据的时候进行上笔借据的结算 --START*/
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            if ("3".equals(mfBusApply.getLoanKind()) && "1".equals(autoSettledAccount)) {
                MfBusApplyFincJxhj mfBusApplyFincJxhj=new MfBusApplyFincJxhj();
                mfBusApplyFincJxhj.setAppId(appId);
                mfBusApplyFincJxhj=mfBusApplyFincJxhjFeign.getById(mfBusApplyFincJxhj);
                //获旧的借据对象
                MfBusFincApp mfBusFincAppOld = new MfBusFincApp();
                mfBusFincAppOld.setFincId(mfBusApplyFincJxhj.getFincOldId());
                mfBusFincAppOld = mfBusFincAppFeign.getById(mfBusFincAppOld);
                //封装还款接口参数
                Map<String, String> parmMapInfo = new HashMap<>();
                //fincId  借据号
                parmMapInfo.put("fincId", mfBusFincAppOld.getFincId());
                //repayAmt 还款金额
                Double repayAmt = mfBusFincAppOld.getLoanBal() + mfBusApplyFincJxhj.getInterest1() + mfBusApplyFincJxhj.getPenalty1() + mfBusApplyFincJxhj.getFee1();
                parmMapInfo.put("repayAmt", repayAmt.toString());
                //repayDate 还款日期 （如果为空默认当前日期）
                parmMapInfo.put("repayDate",mfBusApplyFincJxhj.getConfirmChargDate());
                //repayFlag 还款状态 1 正常状态（还到当前期 默认为1 ） 2 全部还款 （获取所有的还款计划 按照金额依次还款）
                parmMapInfo.put("repayFlag", "2");
                //balAgainstType  多余的还款金额是否存放到结余中状态  0 多还的金额不做处理直接返回  1 多余的金额放到结余表中（买方还款使用）
                //parmMapInfo.put("balAgainstType","");
                //ticketFlag  是否已开普票 0 未开普票 1 已开普票 （可以不传 不传值 会默认为 0 ）
                //parmMapInfo.put("ticketFlag","");
                //
                //满足要求则 则根据登记金额及收取时间，自动插入上笔贷款的还款历史和费用收取历史
                //如果收取费用多余应还利息罚息手续费，则把多余的钱存到结余表balAgainstType
                if (mfBusApplyFincJxhj.getConfirmBalance()>0){
                    parmMapInfo.put("balAgainstType","1");
                }


                //封装还款还款结果
                Map<String, Object> resultMap = new HashMap<>();
                resultMap=calcRepaymentInterfaceFeign.doRepaymentByFincInfo(parmMapInfo);
                if (!"0000".equals(resultMap.get("code"))){
                    dataMap.put("flag", "error");
                    dataMap.put("msg","结清上笔贷款还款失败");
                }

            }
            /*20190328 yxl 当业务类型为借新还旧时，选择了自动结清上笔借据的时候进行上笔借据的结算 --END*/

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            Map<String, String> parmMap = new HashMap<String, String>();

            parmMap.put("operation", "保存还款计划");
            parmMap.put("reason", e.getMessage());
            dataMap.put("msg", MessageEnum.FAILED_OPERATION_CONTENT.getMessage(parmMap));

            // 连连主动放款失败原因提示
            String isAutoLoan = new CodeUtils().getSingleValByKey("IS_AUTO_LOAN");
            if ("1".equals(isAutoLoan)) {
                dataMap.put("msg", "调用连连自动放款失败，原因：" + e.getMessage() + ",再次放款请前往查询-->放款列表操作");
            }
        }
        return dataMap;
    }


    /**
     * 方法描述：自定义还款计划 进行提前还款之后 获取还款计划页面
     *
     * @param appId
     * @return
     * @throws Exception String
     * @author zkq
     * @date 2017-6-02 上午10:39:07
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/preRepayPlanList")
    public String preRepayPlanList(Model model, String appId, String fincIdNew) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String nodeNo = WKF_NODE.review_finc.getNodeNo();// 功能节点编号

        List<MfRepayPlan> repayPlanList = new ArrayList<MfRepayPlan>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> mapParm = new HashMap<String, String>();
        Map<String, String> delayDateMap = new HashMap<String, String>();
        MfBusAppKind mfBusAppKind = null;

        MfBusPact mfBusPact = null;
        MfBusFincAppChild mfBusFincAppChild = null;
        MfBusAppKind mfBusAppKindShow = null;
        FormData formrepayplanlist0001 = null;
        if (appId != null) {
            // 调用封装的还款计划方法
            dataMap = mfRepayPlanFeign.getPreRepayPlanList(appId, fincIdNew, mapParm);

            Object repayPlanListNew = dataMap.get("repayPlanList");// 还款计划列表
            JSONArray jsonArray = JSONArray.fromObject(repayPlanListNew);
            repayPlanList = JSONArray.toList(jsonArray,new MfRepayPlan(), new JsonConfig());
            //处理 当期参数
            for (MfRepayPlan mfRepayPlan : repayPlanList) {

                if ("0".equals(mfRepayPlan.getOutFlag())) {
                    mfRepayPlan.setCurrentNum("0");
                    break;
                }

            }
            mfBusFincAppChild = (MfBusFincAppChild) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusFincAppChild"), MfBusFincAppChild.class);// 借据实体
            String fincId = mfBusFincAppChild.getFincId();
            mfBusPact = (MfBusPact) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusPact"), MfBusPact.class);// 合同信息
            String pactId = mfBusPact.getPactId();
            String cusNo = mfBusPact.getCusNo();
            mfBusPact.setBeginDate(DateUtil.getShowDateTime(mfBusPact.getBeginDate()));// 合同开始日期

            String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.review_finc, null, null, User.getRegNo(request));
            formrepayplanlist0001 = formService.getFormData(formId);

            mfBusAppKind = (MfBusAppKind) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusAppKind"), MfBusAppKind.class);// 参数实体
            Map<String, Object> dataAppKindShowMap = (Map<String, Object>) dataMap.get("dataAppKindShowMap");
            mfBusAppKindShow = (MfBusAppKind) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusAppKindShow"), MfBusAppKind.class);// 参数展示实体
            delayDateMap = (Map<String, String>) dataMap.get("delayDateMap");// 顺延日期
            Gson gson = new Gson();
            // 还款计划发放日期控制：0：借据起息日期（可选范围：借据起息日到当前日期） 1：当前时间（可选范围：无控制）
            String repayPlanStartDate = new CodeUtils().getSingleValByKey("REPAY_PLAN_DATE_SET");
            //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
            String mfBusEditorRepayplanFlag = (String) dataMap.get("mfBusEditorRepayplanFlag");
            model.addAttribute("repayPlanStartDate", repayPlanStartDate);
            model.addAttribute("repayPlanList", repayPlanList);
            model.addAttribute("delayDateMap", gson.toJson(delayDateMap));
            mfBusPact.setFincRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfBusPact.getFincRate(),
                    Integer.parseInt(mfBusAppKind.getYearDays()),
                    Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
            mfBusPact.setOverRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfBusPact.getOverRate(),
                    Integer.parseInt(mfBusAppKind.getYearDays()),
                    Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
            model.addAttribute("pactId", pactId);
            model.addAttribute("cusNo", cusNo);
            model.addAttribute("fincId", fincId);
            model.addAttribute("mfBusEditorRepayplanFlag", mfBusEditorRepayplanFlag);


        }

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        getObjValue(formrepayplanlist0001, mfBusApply);

        getObjValue(formrepayplanlist0001, mfBusAppKind);

        getObjValue(formrepayplanlist0001, mfBusPact);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formrepayplanlist0001, "fincRate", "unit", rateUnit);
        CodeUtils codeUtil = new CodeUtils();
        //发放日期、到期日期日期控制。0-可以修改日期 1-不可以修改日期
        String putDateRead = codeUtil.getSingleValByKey("PUT_DATE_READ");
        //获取还款计划结束日期是否需要通过参数控制减一的所需的参数  0 不需要修改  1 需要每一期的结束日期减一
        String repayPlanShowFlag = codeUtil.getSingleValByKey("REPAY_PLAN_SHOW_FLAG");
        if (BizPubParm.REPAYPLANSHOWFLAG_1.equals(repayPlanShowFlag)) {
            mfBusFincAppChild.setIntstBeginDate(mfBusPact.getBeginDate());
            mfBusFincAppChild.setIntstEndDate(DateUtil.getShowDateTime(mfBusPact.getEndDate()));
        }
        //处理上次还款日期
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincIdNew);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        String lastReturnDate = mfBusFincApp.getLastReturnDate();
        model.addAttribute("lastReturnDate", lastReturnDate);
        //从数据字典中获取借款编码状态
        String borrowCodeType = codeUtil.getSingleValByKey("BORROW_CODE_TYPE");
        model.addAttribute("borrowCodeType", borrowCodeType);
        model.addAttribute("formrepayplanlist0001", formrepayplanlist0001);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("mfBusAppKind", mfBusAppKind);
        model.addAttribute("mfBusAppKindShow", mfBusAppKindShow);
        model.addAttribute("mfBusFincAppChild", mfBusFincAppChild);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("putDateRead", putDateRead);
        model.addAttribute("query", "");
        model.addAttribute("appId", appId);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        return "component/pact/repayplan/MfRepayPlan_PreRepayPlanList";
    }






    /**
     * 保理业务保存还款计划 保存还款计划
     *
     * @param appId
     * @param pageSize
     * @param pageNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repayPlanAjaxForAccount")
    @ResponseBody
    public Map<String, Object> repayPlanAjaxForAccount(String ajaxData, String fincListData, String appId, Integer pageSize, Integer pageNo)
            throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Map<String, Object> parmMap = getMapByJson(ajaxData);
            paramMap.put("ajaxData", parmMap);
            paramMap.put("fincListData", fincListData);
            // 保存还款计划并流转到下一审批流程
            dataMap = mfRepayPlanFeign.doApprovalProcessForAccount(paramMap);
            String infoMsg = dataMap.containsKey("infoMsg") ? dataMap.get("infoMsg").toString() : "";
            dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("放款") + " 业务进入贷后阶段"
                    + (StringUtil.isNotEmpty(infoMsg) ? "," + infoMsg : ""));
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("保存还款计划"));
        }
        return dataMap;
    }

    /**
     * 存在三方，进行三方放款
     *
     * @param appId
     * @param pageSize
     * @param pageNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/thirdPayRepayPlanAjax")
    @ResponseBody
    public Map<String, Object> thirdPayRepayPlanAjax(String ajaxData, String fincId, Integer pageSize, Integer pageNo)
            throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //获取放款时银行卡进行预签约判断
        MfBusFincApp mfBusFincApp1 = new MfBusFincApp();
        mfBusFincApp1.setFincId(fincId);
        mfBusFincApp1 = mfBusFincAppFeign.getById(mfBusFincApp1);
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setId(mfBusFincApp1.getBankAccId());
        mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
        String bankAuthSts = mfCusBankAccManage.getBankAuthSts();
        String bankCode = mfCusBankAccManage.getBankCode();//银行简称
        String branch = mfCusBankAccManage.getBranch(); //分行
        String subbranch = mfCusBankAccManage.getSubbranch();//支行
        String province = mfCusBankAccManage.getProvince();//省
        String city = mfCusBankAccManage.getCity();//市
        String accProperty = mfCusBankAccManage.getAccProperty(); //02对公01对私
        if (bankCode == null || bankCode == "") {
            bankCode = "CCB";
            MfCusBankAccManage mfCusBankAccManage1 = new MfCusBankAccManage();
            mfCusBankAccManage1.setId(mfCusBankAccManage.getId());
            mfCusBankAccManage1.setBankCode(bankCode);
            mfCusBankAccManageFeign.update(mfCusBankAccManage1);
        }
        //对公付款及对私大于5万的付款必填项分行、支行、省、市
        String putoutAmtReal = request.getParameter("putoutAmtReal");//真实放款金额
        if ("02".equals(mfCusBankAccManage.getAccProperty()) || ("01".equals(mfCusBankAccManage.getAccProperty()) && Integer.parseInt(putoutAmtReal) > 50000)) {
            if ((branch == null || branch == "") || (subbranch == null || subbranch == "") || (province == null || province == "") || (city == null || city == "")) {
                dataMap.put("msg", "对公付款及对私大于5万的付款,分行、支行、省、市不能为空");
                return dataMap;
            }
        }
        if ("0".equals(bankAuthSts)) {
            dataMap.put("msg", "银行卡未进行预签约，不能进行三方放款");
            return dataMap;
        } else if ("2".equals(bankAuthSts)) {
            dataMap.put("msg", "银行卡预签约失败，不能进行三方放款");
            return dataMap;
        }else {
        }
        try {
            String beginDate = request.getParameter("beginDate");// 借据开始日期
            String endDate = request.getParameter("endDate");// 借据到期日期
            String planListData = request.getParameter("planListData");// 还款计划列表数据
            String planListSize = request.getParameter("planListSize");// 还款计划期数
            String putoutAmt = request.getParameter("putoutAmt");//放款总额
            putoutAmt = MathExtend.round(putoutAmt, 0);
            //String putoutAmtReal = request.getParameter("putoutAmtReal");//真实放款金额
            putoutAmtReal = MathExtend.round(putoutAmtReal, 0);
            String repayFeeSum = request.getParameter("repayFeeSum");// 提前收取的费用总和
            String repayIntstSum = request.getParameter("repayIntstSum");// 提前收取的利息总和
            String putOutChargeIntst = request.getParameter("putOutChargeIntst");// 放款时收取的利息(存在固定还款日时)
            String putOutChargeFee = request.getParameter("putOutChargeFee");// 放款时收取的一次性费用
            // 借据主表
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            //封装还款计划列表 封装还款计划列表数据
            Map<String, String> formMap = CwPublicUtil.getMapByJson(planListData);
            List<MfRepayPlan> repayPlanList = new ArrayList<MfRepayPlan>();
            MfRepayPlan mfRepayPlan = null;
            int count = new Integer(planListSize).intValue();//list的条数
            for (int i = 1; i <= count; i++) {
                mfRepayPlan = new MfRepayPlan();
                mfRepayPlan.setLoanAmt(Double.valueOf(putoutAmt));
                mfRepayPlan.setTermNum(Integer.valueOf((formMap.get("termNum" + i))));//期号
                mfRepayPlan.setPlanBeginDate(DateUtil.getYYYYMMDD(formMap.get("planBeginDate" + i)));//开始日期
                mfRepayPlan.setPlanEndDate(DateUtil.getYYYYMMDD(formMap.get("planEndDate" + i)));//结束日期
                mfRepayPlan.setRepayPrcp(Double.parseDouble(formMap.get("repayPrcp" + i)));//应还本金
                mfRepayPlan.setRepayIntst(Double.parseDouble(formMap.get("repayIntst" + i)));//应还利息
                mfRepayPlan.setRepayIntstOrig(Double.parseDouble(formMap.get("repayIntstOrig" + i)));//本期计划应还提前收取前的利息
                mfRepayPlan.setFeeSum(Double.parseDouble(formMap.get("feeSum" + i)));//费用总额
                mfRepayPlan.setRepayPrcpIntstSum(Double.parseDouble(formMap.get("repayPrcpIntstSum" + i)));//应还总额
                mfRepayPlan.setRepayPrcpBalAfter(Double.parseDouble(formMap.get("repayPrcpBalAfter" + i)));//期末本金余额（减本期应还本金）
                mfRepayPlan.setRepayPrcpBal(0.00);//期末本金余额（未减本期应还本金）
                mfRepayPlan.setPlanId(WaterIdUtil.getWaterId("plan"));
                mfRepayPlan.setRepayDate("");//实际还款日期
                mfRepayPlan.setExpiryIntstDate(DateUtil.getYYYYMMDD(formMap.get("planEndDate" + i)));//本期结息日期
                mfRepayPlan.setOutFlag("0");//还款状态（0 -未还款 1-已还完 2-部分还款 3-逾期）
                mfRepayPlan.setRegTime(DateUtil.getDateTime());//登记时间
                mfRepayPlan.setLstModTime(DateUtil.getDateTime());//修改时间、】
                mfRepayPlan.setExt1("");//备用字段1
                mfRepayPlan.setExt2("");//备用字段2
                mfRepayPlan.setExt3("");//备用字段3
                mfRepayPlan.setExt4("");//备用字段4
                mfRepayPlan.setExt5("");//备用字段5
                mfRepayPlan.setRegDate(DateUtil.getDate());
                mfRepayPlan.setLstModDate(DateUtil.getDate());
                //wd 添加逾期相关信息
                mfRepayPlan.setShouldPenaltyRate(0.00);
                mfRepayPlan.setHasPenaltyRate(0.00);
                mfRepayPlan.setOverIntst(0.00);
                mfRepayPlan.setCmpdIntst(0.00);
                repayPlanList.add(mfRepayPlan);
            }

            mfBusFincApp.setFincId(fincId);
            new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusFincApp);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("fincId", fincId);
            //repayplanState 还款计划的获取   0 通过规则获取  1 从相关记录表获取（third_pay_repay_plan） 如果值为 1 需要传入相应的还款计划列表
            paramMap.put("repayplanState", "1");
            paramMap.put("putoutAmt", putoutAmt);
            paramMap.put("putoutAmtReal", putoutAmtReal);
            paramMap.put("beginDate", beginDate);
            paramMap.put("endDate", endDate);
            paramMap.put("planListData", repayPlanList);//repayplanState 状态为 0 时 不用传 为 1 时需要传入还款计划列表
            paramMap.put("repayFeeSum", repayFeeSum);//
            paramMap.put("repayIntstSum", repayIntstSum);
            paramMap.put("putOutChargeIntst", putOutChargeIntst);
            paramMap.put("putOutChargeFee", putOutChargeFee);
            paramMap.put("userType", accProperty);
            dataMap = mfRepayPlanFeign.doRepayPlan(paramMap);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("保存还款计划"));
        }
        return dataMap;
    }

    /**
     * 发放日期改变重新获取还款计划
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPlanListByBeginDateAjax")
    @ResponseBody
    public Map<String, Object> getPlanListByBeginDateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfRepayPlanParameter mfRepayPlanParameter = new MfRepayPlanParameter();
        String beginDate = request.getParameter("beginDate");// 借据开始日期
        String endDate = request.getParameter("endDate");// 到期日期
        String fincId = request.getParameter("fincId");// 借据ID
        String beginDateHidden = request.getParameter("beginDateHidden");// 借据默认开始日期
        String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");// 多次放款还款计划是否合并
        // 1-启用、0-禁用
        String interestCollectType = request.getParameter("interestCollectType");// 利息收息方式：1-上收息
        // 2-下收息
        String repayDateSet = request.getParameter("repayDateSet");// 还款日设置：1-贷款发放日
        // 2-月末
        // 3-固定还款日
        String yearDays = request.getParameter("yearDays");// 计息天数
        String normCalcType = request.getParameter("normCalcType");// 利息计算方式（按月计息：每月30天/按日计息：实际天数）1按月结息2按日结息
        String secondNormCalcType = request.getParameter("secondNormCalcType");// 不足期计息类型
        // 1-按月计息|2-按实际天数计息
        String returnPlanPoint = request.getParameter("returnPlanPoint");// 保留小数位数
        // 2-两位|1-一位|0-不保留
        String returnPlanRound = request.getParameter("returnPlanRound");// 还款计划舍入方式
        // 2-四舍五入
        String instCalcBase = request.getParameter("instCalcBase");// 提前还款利息计算基数：1-按借据余额、2-按提前还款本金
        String preInstCollectType = request.getParameter("preInstCollectType");// 预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
        String repayDateDef = request.getParameter("repayDateDef");// 固定还款日
        String rulesNo = request.getParameter("rulesNo");
        String appId = request.getParameter("appId");
        String putoutAmt = request.getParameter("putoutAmt");
        Map<String, String> mapParm = new HashMap<String, String>();
        try {
            if (appId != null) {
                mapParm.put("appId", appId);
                mapParm.put("fincId", fincId);
                mapParm.put("beginDateHidden", beginDateHidden);

                mfRepayPlanParameter.setPutoutDate(DateUtil.getYYYYMMDD(beginDate));// 开始日期
                mfRepayPlanParameter.setEndDate(DateUtil.getYYYYMMDD(endDate));// 结束日期
                mfRepayPlanParameter.setRepayDayType(repayDateSet);// 还款日方式
                // 1-固定还款日|2-随放款日
                mfRepayPlanParameter.setFixedRepayDayType(preInstCollectType);// 预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
                mfRepayPlanParameter.setFixedRepayDay(repayDateDef);// 固定还款日
                 mfRepayPlanParameter.setLoanAmt(Double.valueOf(putoutAmt));//贷款金额
                // mfRepayPlanParameter.setFincRate(fincRate);//年利率
                mfRepayPlanParameter.setIntstDays(yearDays);// 计息天数 （360/365）
                mfRepayPlanParameter.setNotAdequacyCalType(secondNormCalcType);// 不足期计息类型
                // 1-按月计息|2-按实际天数计息
                mfRepayPlanParameter.setDecimalDigits(returnPlanPoint);// 保留小数位数
                // 2-两位|1-一位|0-不保留
                mfRepayPlanParameter.setInterestReckonMode(normCalcType);// 计息方式
                // 1按月结息2按日结息
                // mfRepayPlanParameter.setRepayType(repayType);//还款方式
                mfRepayPlanParameter.setIntstBase(instCalcBase);// 利息计算基础
                // (1-贷款金额;2-本金余额)
                mfRepayPlanParameter.setInterestCollectType(interestCollectType);// 利息收息方式：1-上收息
                // 2-下收息
                mfRepayPlanParameter.setMultipleLoanPlanMerge(multipleLoanPlanMerge);// 多次放款还款计划合并：1-启用、0-禁用
                mfRepayPlanParameter.setRounding(returnPlanRound);// 四舍五入2
                mfRepayPlanParameter.setRulesNo(rulesNo);

                // 调用封装的还款计划方法
                dataMap = mfRepayPlanFeign.getPlanListByBeginDate(mfRepayPlanParameter, mapParm);
                if (dataMap != null) {
                    dataMap.put("flag", "success");
                } else {
                    dataMap.put("flag", "error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("修改发放日期获取还款计划"));
        }
        return dataMap;
    }

    /**
     * 到期日期重新调用获取还款计划
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPlanListByEndDateAjax")
    @ResponseBody
    public Map<String, Object> getPlanListByEndDateAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String beginDate = request.getParameter("beginDate");// 借据开始日期
        String endDate = request.getParameter("endDate");// 到期日期
        String fincId = request.getParameter("fincId");// 借据id
        String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");// 多次放款还款计划是否合并
        // 1-启用、0-禁用
        String interestCollectType = request.getParameter("interestCollectType");// 利息收息方式：1-上收息
        // 2-下收息
        String repayDateSet = request.getParameter("repayDateSet");// 还款日设置：1-贷款发放日
        // 2-月末
        // 3-固定还款日
        String yearDays = request.getParameter("yearDays");// 计息天数
        String normCalcType = request.getParameter("normCalcType");// 利息计算方式（按月计息：每月30天/按日计息：实际天数）1按月结息2按日结息
        String secondNormCalcType = request.getParameter("secondNormCalcType");// 不足期计息类型
        // 1-按月计息|2-按实际天数计息
        String returnPlanPoint = request.getParameter("returnPlanPoint");// 保留小数位数
        // 2-两位|1-一位|0-不保留
        String returnPlanRound = request.getParameter("returnPlanRound");// 还款计划舍入方式
        // 2-四舍五入
        String instCalcBase = request.getParameter("instCalcBase");// 提前还款利息计算基数：1-按借据余额、2-按提前还款本金
        String putoutAmt = request.getParameter("putoutAmt");// 放款金额
        String preInstCollectType = request.getParameter("preInstCollectType");
        String repayDateDef = request.getParameter("repayDateDef");
        String rulesNo = request.getParameter("rulesNo");
        String appId = request.getParameter("appId");
        Map<String, String> mapParm = new HashMap<String, String>();
        MfRepayPlanParameter mfRepayPlanParameter = new MfRepayPlanParameter();

        try {
            if (appId != null) {
                mapParm.put("appId", appId);
                mapParm.put("fincId", fincId);

                mfRepayPlanParameter.setPutoutDate(DateUtil.getYYYYMMDD(beginDate));// 开始日期
                mfRepayPlanParameter.setEndDate(DateUtil.getYYYYMMDD(endDate));// 结束日期
                mfRepayPlanParameter.setRepayDayType(repayDateSet);// 还款日方式
                // 1-固定还款日|2-随放款日
                mfRepayPlanParameter.setFixedRepayDayType(preInstCollectType);// 预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
                mfRepayPlanParameter.setFixedRepayDay(repayDateDef);// 固定还款日
                // mfRepayPlanParameter.setLoanAmt(Double.valueOf(putoutAmt));//贷款金额
                // mfRepayPlanParameter.setFincRate(fincRate);//年利率
                mfRepayPlanParameter.setIntstDays(yearDays);// 计息天数 （360/365）
                mfRepayPlanParameter.setNotAdequacyCalType(secondNormCalcType);// 不足期计息类型
                // 1-按月计息|2-按实际天数计息
                mfRepayPlanParameter.setDecimalDigits(returnPlanPoint);// 保留小数位数
                // 2-两位|1-一位|0-不保留
                mfRepayPlanParameter.setInterestReckonMode(normCalcType);// 计息方式
                // 1按月结息2按日结息
                // mfRepayPlanParameter.setRepayType(repayType);//还款方式
                mfRepayPlanParameter.setIntstBase(instCalcBase);// 利息计算基础
                // (1-贷款金额;2-本金余额)
                mfRepayPlanParameter.setInterestCollectType(interestCollectType);// 利息收息方式：1-上收息
                // 2-下收息
                mfRepayPlanParameter.setMultipleLoanPlanMerge(multipleLoanPlanMerge);// 多次放款还款计划合并：1-启用、0-禁用
                mfRepayPlanParameter.setRounding(returnPlanRound);// 四舍五入2
                mfRepayPlanParameter.setRulesNo(rulesNo);// 规则唯一编号
                mfRepayPlanParameter.setLoanAmt(Double.valueOf(putoutAmt));//贷款金额
                dataMap = mfRepayPlanFeign.getPlanListByEndDate(mfRepayPlanParameter, mapParm);
                if (dataMap != null) {
                    dataMap.put("flag", "success");
                } else {
                    dataMap.put("flag", "error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("修改到期日期获取还款计划"));
        }
        return dataMap;
    }

    /**
     * 应还本金改变调用还款计划
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changePlanByPrcpAjax")
    @ResponseBody
    public Map<String, Object> changePlanByPrcpAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String repayPlanListParm = request.getParameter("repayPlanList");// 还款计划List
        Gson gson = new Gson();
        List<MfRepayPlan> list = gson.fromJson(repayPlanListParm, new TypeToken<List<MfRepayPlan>>() {
        }.getType());
        String appId = request.getParameter("appId");
        String fincId = request.getParameter("fincId");
        String modTermNum = request.getParameter("modTermNum");// 修改当前期数
        String loanAmt = request.getParameter("loanAmt");// 借据金额
        String fincRate = request.getParameter("fincRate");// 年利率（执行利率）
        String intstModify = request.getParameter("intstModify");// 利息修改状态
        // 0：未修改利息
        // 1：已修改利息

        String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");// 多次放款还款计划是否合并
        // 1-启用、0-禁用
        String interestCollectType = request.getParameter("interestCollectType");// 利息收息方式：1-上收息
        // 2-下收息
        String repayDateSet = request.getParameter("repayDateSet");// 还款日设置：1-贷款发放日
        // 2-月末
        // 3-固定还款日
        String yearDays = request.getParameter("yearDays");// 计息天数
        String normCalcType = request.getParameter("normCalcType");// 利息计算方式（按月计息：每月30天/按日计息：实际天数）1按月结息2按日结息
        String secondNormCalcType = request.getParameter("secondNormCalcType");// 不足期计息类型
        // 1-按月计息|2-按实际天数计息
        String returnPlanPoint = request.getParameter("returnPlanPoint");// 保留小数位数
        // 2-两位|1-一位|0-不保留
        String returnPlanRound = request.getParameter("returnPlanRound");// 还款计划舍入方式
        // 2-四舍五入
        String instCalcBase = request.getParameter("instCalcBase");// 提前还款利息计算基数：1-按借据余额、2-按提前还款本金
        String preInstCollectType = request.getParameter("preInstCollectType");
        String repayDateDef = request.getParameter("repayDateDef");
        String rulesNo = request.getParameter("rulesNo");

        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> mapParm = new HashMap<String, String>();
        MfRepayPlanParameter mfRepayPlanParameter = new MfRepayPlanParameter();
        mapParm.put("appId", appId);
        mapParm.put("fincId", fincId);
        mapParm.put("modTermNum", modTermNum);
        mapParm.put("intstModify", intstModify);

        mfRepayPlanParameter.setFincRate(fincRate);
        mfRepayPlanParameter.setLoanAmt(Double.valueOf(loanAmt));
        mfRepayPlanParameter.setRepayDayType(repayDateSet);// 还款日方式
        // 1-固定还款日|2-随放款日
        mfRepayPlanParameter.setFixedRepayDayType(preInstCollectType);// 预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
        mfRepayPlanParameter.setFixedRepayDay(repayDateDef);// 固定还款日
        mfRepayPlanParameter.setIntstDays(yearDays);// 计息天数 （360/365）
        mfRepayPlanParameter.setNotAdequacyCalType(secondNormCalcType);// 不足期计息类型
        // 1-按月计息|2-按实际天数计息
        mfRepayPlanParameter.setDecimalDigits(returnPlanPoint);// 保留小数位数
        // 2-两位|1-一位|0-不保留
        mfRepayPlanParameter.setInterestReckonMode(normCalcType);// 计息方式
        // 1按月结息2按日结息
        mfRepayPlanParameter.setIntstBase(instCalcBase);// 利息计算基础
        // (1-贷款金额;2-本金余额)
        mfRepayPlanParameter.setInterestCollectType(interestCollectType);// 利息收息方式：1-上收息
        // 2-下收息
        mfRepayPlanParameter.setMultipleLoanPlanMerge(multipleLoanPlanMerge);// 多次放款还款计划合并：1-启用、0-禁用
        mfRepayPlanParameter.setRounding(returnPlanRound);// 四舍五入2
        mfRepayPlanParameter.setRulesNo(rulesNo);
        try {
            //封装本金修改调用的方法
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("list", list);//List<MfRepayPlan>
            parmMap.put("mfRepayPlanParameter", mfRepayPlanParameter);//MfRepayPlanParameter
            parmMap.put("mapParm", mapParm);//Map<String, String>
            dataMap = mfRepayPlanFeign.getChangePlanByPrcp(parmMap);
            if (dataMap != null) {
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("修改应还本金获取还款计划"));
        }
        return dataMap;
    }


    /**
     * 保存还款计划 保存还款计划
     *
     * @param fincId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doEditorPrePlanAjax")
    @ResponseBody
    public Map<String, Object> doEditorPrePlanAjax(String ajaxData, String fincId) throws Exception {

        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        String planListData = request.getParameter("planListData");// 还款计划列表数据
        String planListSize = request.getParameter("planListSize");// 还款计划期数

        try {
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(fincId);
            mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("mfBusFincApp", JSONObject.fromObject(mfBusFincApp));
            paramMap.put("planListData", planListData);
            dataMap = mfRepayPlanFeign.doEditorPrePlan(planListSize, paramMap);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("调整还款计划"));

        }
        return dataMap;
    }








    /**
     * 改变结束日期调用还款计划
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changePlanByEndDateAjax")
    @ResponseBody
    public Map<String, Object> changePlanByEndDateAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String repayPlanListParm = request.getParameter("repayPlanList");// 还款计划List
        Gson gson = new Gson();
        List<MfRepayPlan> list = gson.fromJson(repayPlanListParm, new TypeToken<List<MfRepayPlan>>() {
        }.getType());
        String modTermNum = request.getParameter("modTermNum");// 修改当前期数
        String loanAmt = request.getParameter("loanAmt");// 借据金额
        String fincRate = request.getParameter("fincRate");// 年利率（执行利率）
        String intstModify = request.getParameter("intstModify");// 利息修改状态
        // 0：未修改利息
        // 1：已修改利息

        String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");// 多次放款还款计划是否合并
        // 1-启用、0-禁用
        String interestCollectType = request.getParameter("interestCollectType");// 利息收息方式：1-上收息
        // 2-下收息
        String repayDateSet = request.getParameter("repayDateSet");// 还款日设置：1-贷款发放日
        // 2-月末
        // 3-固定还款日
        String yearDays = request.getParameter("yearDays");// 计息天数
        String normCalcType = request.getParameter("normCalcType");// 利息计算方式（按月计息：每月30天/按日计息：实际天数）1按月结息2按日结息
        String secondNormCalcType = request.getParameter("secondNormCalcType");// 不足期计息类型
        // 1-按月计息|2-按实际天数计息
        String returnPlanPoint = request.getParameter("returnPlanPoint");// 保留小数位数
        // 2-两位|1-一位|0-不保留
        String returnPlanRound = request.getParameter("returnPlanRound");// 还款计划舍入方式
        // 2-四舍五入
        String instCalcBase = request.getParameter("instCalcBase");// 提前还款利息计算基数：1-按借据余额、2-按提前还款本金
        String preInstCollectType = request.getParameter("preInstCollectType");
        String repayDateDef = request.getParameter("repayDateDef");
        String rulesNo = request.getParameter("rulesNo");
        String appId = request.getParameter("appId");
        String fincId = request.getParameter("fincId");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> mapParm = new HashMap<String, String>();
        MfRepayPlanParameter mfRepayPlanParameter = new MfRepayPlanParameter();
        mapParm.put("appId", appId);
        mapParm.put("modTermNum", modTermNum);
        mapParm.put("intstModify", intstModify);
        mapParm.put("fincId", fincId);
        mfRepayPlanParameter.setFincRate(fincRate);
        mfRepayPlanParameter.setLoanAmt(Double.valueOf(loanAmt));
        mfRepayPlanParameter.setRepayDayType(repayDateSet);// 还款日方式
        // 1-固定还款日|2-随放款日
        mfRepayPlanParameter.setFixedRepayDayType(preInstCollectType);// 预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
        mfRepayPlanParameter.setFixedRepayDay(repayDateDef);// 固定还款日
        mfRepayPlanParameter.setIntstDays(yearDays);// 计息天数 （360/365）
        mfRepayPlanParameter.setNotAdequacyCalType(secondNormCalcType);// 不足期计息类型
        // 1-按月计息|2-按实际天数计息
        mfRepayPlanParameter.setDecimalDigits(returnPlanPoint);// 保留小数位数
        // 2-两位|1-一位|0-不保留
        mfRepayPlanParameter.setInterestReckonMode(normCalcType);// 计息方式
        // 1按月结息2按日结息
        mfRepayPlanParameter.setIntstBase(instCalcBase);// 利息计算基础
        // (1-贷款金额;2-本金余额)
        mfRepayPlanParameter.setInterestCollectType(interestCollectType);// 利息收息方式：1-上收息
        // 2-下收息
        mfRepayPlanParameter.setMultipleLoanPlanMerge(multipleLoanPlanMerge);// 多次放款还款计划合并：1-启用、0-禁用
        mfRepayPlanParameter.setRounding(returnPlanRound);// 四舍五入2
        mfRepayPlanParameter.setRulesNo(rulesNo);

        try {
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("list", list);
            parmMap.put("mfRepayPlanParameter", mfRepayPlanParameter);
            parmMap.put("mapParm", mapParm);
            dataMap = mfRepayPlanFeign.getChangePlanByEndDate(parmMap);
            if (dataMap != null) {
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("修改本期结束日期获取还款计划"));
        }
        return dataMap;
    }

    /**
     * 改变应还利息调用还款计划
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changePlanByIntstAjax")
    @ResponseBody
    public Map<String, Object> changePlanByIntstAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String repayPlanListParm = request.getParameter("repayPlanList");// 还款计划List
        Gson gson = new Gson();
        List<MfRepayPlan> list = gson.fromJson(repayPlanListParm, new TypeToken<List<MfRepayPlan>>() {
        }.getType());
        String modTermNum = request.getParameter("modTermNum");// 修改当前期数
        String loanAmt = request.getParameter("loanAmt");// 借据金额
        String fincRate = request.getParameter("fincRate");// 年利率（执行利率）

        String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");// 多次放款还款计划是否合并
        // 1-启用、0-禁用
        String interestCollectType = request.getParameter("interestCollectType");// 利息收息方式：1-上收息
        // 2-下收息
        String repayDateSet = request.getParameter("repayDateSet");// 还款日设置：1-贷款发放日
        // 2-月末
        // 3-固定还款日
        String yearDays = request.getParameter("yearDays");// 计息天数
        String normCalcType = request.getParameter("normCalcType");// 利息计算方式（按月计息：每月30天/按日计息：实际天数）1按月结息2按日结息
        String secondNormCalcType = request.getParameter("secondNormCalcType");// 不足期计息类型
        // 1-按月计息|2-按实际天数计息
        String returnPlanPoint = request.getParameter("returnPlanPoint");// 保留小数位数
        // 2-两位|1-一位|0-不保留
        String returnPlanRound = request.getParameter("returnPlanRound");// 还款计划舍入方式
        // 2-四舍五入
        String instCalcBase = request.getParameter("instCalcBase");// 提前还款利息计算基数：1-按借据余额、2-按提前还款本金
        String preInstCollectType = request.getParameter("preInstCollectType");// 固定还款日还款方式
        // 1-合并第一期|2-独立一期|3-放款时收取
        String repayDateDef = request.getParameter("repayDateDef");// /默认还款日
        // 当repay_date_set为固定还款日时,该字段才有值
        String rulesNo = request.getParameter("rulesNo");
        String appId = request.getParameter("appId");
        String fincId = request.getParameter("fincId");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> mapParm = new HashMap<String, String>();
        MfRepayPlanParameter mfRepayPlanParameter = new MfRepayPlanParameter();
        mapParm.put("appId", appId);
        mapParm.put("modTermNum", modTermNum);
        mapParm.put("digits", returnPlanPoint);
        mapParm.put("fincId", fincId);

        mfRepayPlanParameter.setFincRate(fincRate);
        mfRepayPlanParameter.setLoanAmt(Double.valueOf(loanAmt));
        mfRepayPlanParameter.setRepayDayType(repayDateSet);// 还款日方式
        // 1-固定还款日|2-随放款日
        mfRepayPlanParameter.setFixedRepayDayType(preInstCollectType);// 预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
        mfRepayPlanParameter.setFixedRepayDay(repayDateDef);// 固定还款日
        mfRepayPlanParameter.setIntstDays(yearDays);// 计息天数 （360/365）
        mfRepayPlanParameter.setNotAdequacyCalType(secondNormCalcType);// 不足期计息类型
        // 1-按月计息|2-按实际天数计息
        mfRepayPlanParameter.setDecimalDigits(returnPlanPoint);// 保留小数位数
        // 2-两位|1-一位|0-不保留
        mfRepayPlanParameter.setInterestReckonMode(normCalcType);// 计息方式
        // 1按月结息2按日结息
        mfRepayPlanParameter.setIntstBase(instCalcBase);// 利息计算基础
        // (1-贷款金额;2-本金余额)
        mfRepayPlanParameter.setInterestCollectType(interestCollectType);// 利息收息方式：1-上收息
        // 2-下收息
        mfRepayPlanParameter.setMultipleLoanPlanMerge(multipleLoanPlanMerge);// 多次放款还款计划合并：1-启用、0-禁用
        mfRepayPlanParameter.setRounding(returnPlanRound);// 四舍五入2
        mfRepayPlanParameter.setRulesNo(rulesNo);
        try {
            //封装数据
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("list", list);//List<MfRepayPlan>
            parmMap.put("mfRepayPlanParameter", mfRepayPlanParameter);//MfRepayPlanParameter
            parmMap.put("mapParm", mapParm);//Map<String, String>
            dataMap = mfRepayPlanFeign.getChangePlanByIntst(parmMap);
            if (dataMap != null) {
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("修改应还利息获取还款计划"));
        }
        return dataMap;
    }

    /**
     * 功能描述:通过调整的期限和开始日期结束日期重新获取还款计划列表
     *
     * @param: [ajaxData]
     * @return: java.util.Map<java.lang.String   ,   java.lang.Object>
     * @auther: wd
     * @date: 2018/12/17 14:12
     */
    @RequestMapping(value = "/getPlanListByPeriodAjax")
    @ResponseBody
    public Map<String, Object> getPlanListByPeriodAjax(String ajaxData) throws Exception {
//		FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String beginDate = request.getParameter("beginDate");// 借据开始日期
        String endDate = request.getParameter("endDate");// 到期日期
        String periodAdjust = request.getParameter("periodAdjust");//要调整的期数
        String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");// 多次放款还款计划是否合并
        // 1-启用、0-禁用
        String interestCollectType = request.getParameter("interestCollectType");// 利息收息方式：1-上收息
        // 2-下收息
        String repayDateSet = request.getParameter("repayDateSet");// 还款日设置：1-贷款发放日
        // 2-月末
        // 3-固定还款日
        String yearDays = request.getParameter("yearDays");// 计息天数
        String normCalcType = request.getParameter("normCalcType");// 利息计算方式（按月计息：每月30天/按日计息：实际天数）1按月结息2按日结息
        String secondNormCalcType = request.getParameter("secondNormCalcType");// 不足期计息类型
        // 1-按月计息|2-按实际天数计息
        String returnPlanPoint = request.getParameter("returnPlanPoint");// 保留小数位数
        // 2-两位|1-一位|0-不保留
        String returnPlanRound = request.getParameter("returnPlanRound");// 还款计划舍入方式
        // 2-四舍五入
        String instCalcBase = request.getParameter("instCalcBase");// 提前还款利息计算基数：1-按借据余额、2-按提前还款本金
        String preInstCollectType = request.getParameter("preInstCollectType");
        String repayDateDef = request.getParameter("repayDateDef");
        String rulesNo = request.getParameter("rulesNo");
        String appId = request.getParameter("appId");
        Map<String, String> mapParm = new HashMap<String, String>();
        MfRepayPlanParameter mfRepayPlanParameter = new MfRepayPlanParameter();
        String outMaxPeriodAdjust = "0";
        try {
            //获取两个日期之间的天数
            int daysBetween = DateUtil.getDaysBetween(DateUtil.getYYYYMMDD(beginDate), DateUtil.getYYYYMMDD(endDate));
            if (MathExtend.comparison(periodAdjust, String.valueOf(daysBetween)) > 0) {
                periodAdjust = String.valueOf(daysBetween);
                outMaxPeriodAdjust = "1";
            }
            if (appId != null) {
                mapParm.put("appId", appId);
                mfRepayPlanParameter.setPutoutDate(DateUtil.getYYYYMMDD(beginDate));// 开始日期
                mfRepayPlanParameter.setEndDate(DateUtil.getYYYYMMDD(endDate));// 结束日期
                mfRepayPlanParameter.setRepayDayType(repayDateSet);// 还款日方式
                // 1-固定还款日|2-随放款日
                mfRepayPlanParameter.setFixedRepayDayType(preInstCollectType);// 预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
                mfRepayPlanParameter.setFixedRepayDay(repayDateDef);// 固定还款日
                // mfRepayPlanParameter.setLoanAmt(Double.valueOf(putoutAmt));//贷款金额
                // mfRepayPlanParameter.setFincRate(fincRate);//年利率
                mfRepayPlanParameter.setIntstDays(yearDays);// 计息天数 （360/365）
                mfRepayPlanParameter.setNotAdequacyCalType(secondNormCalcType);// 不足期计息类型
                // 1-按月计息|2-按实际天数计息
                mfRepayPlanParameter.setDecimalDigits(returnPlanPoint);// 保留小数位数
                // 2-两位|1-一位|0-不保留
                mfRepayPlanParameter.setInterestReckonMode(normCalcType);// 计息方式
                // 1按月结息2按日结息
                // mfRepayPlanParameter.setRepayType(repayType);//还款方式
                mfRepayPlanParameter.setIntstBase(instCalcBase);// 利息计算基础
                // (1-贷款金额;2-本金余额)
                mfRepayPlanParameter.setInterestCollectType(interestCollectType);// 利息收息方式：1-上收息
                // 2-下收息
                mfRepayPlanParameter.setMultipleLoanPlanMerge(multipleLoanPlanMerge);// 多次放款还款计划合并：1-启用、0-禁用
                mfRepayPlanParameter.setRounding(returnPlanRound);// 四舍五入2
                mfRepayPlanParameter.setRulesNo(rulesNo);// 规则唯一编号
                mfRepayPlanParameter.setPeriodAdjust(periodAdjust);//调整的期数
                dataMap = mfRepayPlanFeign.getPlanListByPeriod(mfRepayPlanParameter, mapParm);
                if (dataMap != null) {
                    dataMap.put("flag", "success");
                    dataMap.put("outMaxPeriodAdjust", outMaxPeriodAdjust);
                } else {
                    dataMap.put("flag", "error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("修改调整期限获取还款计划"));
        }
        return dataMap;
    }


    /**
     * @param ajaxData
     * @return
     * @throws Exception
     * @desc 修改预先支付利息收取方式获取还款计划
     * @Author zkq
     * @date 20190703
     */

    @RequestMapping(value = "/changePreInstCollectTypeAjax")
    @ResponseBody
    public Map<String, Object> changePreInstCollectTypeAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        String preInstCollectType = request.getParameter("preInstCollectType");

        String appId = request.getParameter("appId");
        Map<String, String> mapParm = new HashMap<String, String>();
        MfRepayPlanParameter mfRepayPlanParameter = new MfRepayPlanParameter();

        try {
            if (appId != null) {
                mapParm.put("appId", appId);
                mapParm.put("preInstCollectType", preInstCollectType);//预先支付利息收取方式
                dataMap = mfRepayPlanFeign.changePreInstCollectType(appId, mapParm);
                if (dataMap != null) {
                    dataMap.put("flag", "success");
                } else {
                    dataMap.put("flag", "error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("修改预先支付利息收取方式获取还款计划"));
        }
        return dataMap;
    }
    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model,String appId) throws Exception {
        ActionContext.initialize(request, response);
        MfRepayPlan mfRepayPlan = new MfRepayPlan();
        mfRepayPlan.setAppId(appId);
        Ipage ipage= new Ipage();
        ipage.setParams(this.setIpageParams("mfRepayPlan", mfRepayPlan));
        ipage = mfRepayPlanFeign2.findPageById(ipage);
        Gson gson = new Gson();
        String result =gson.toJson(ipage.getResult());
        List<MfRepayPlan> mfrepayplanList = gson.fromJson(result,new TypeToken<List<MfRepayPlan>>(){}.getType());
        //处理费率指单位只有 利率为百分比时 才处理
        model.addAttribute("query", "");
        model.addAttribute("ipage", ipage);
        model.addAttribute("mfrepayplanList", mfrepayplanList);
        return "/component/calc/fee/MfBusAppRepayplan_List";
    }
}
