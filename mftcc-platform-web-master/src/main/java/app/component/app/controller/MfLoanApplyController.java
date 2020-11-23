/**
 * Copyright (C)  版权所有
 * 文件名： MfLoanApplyAction.java
 * 包名： app.component.app.action
 * 说明：中汇-北京-个贷业务action
 *
 * @author YuShuai
 * @date 2017-5-31 下午8:26:46
 * @version V1.0
 */
package app.component.app.controller;

import app.base.SpringUtil;
import app.base.User;
import app.component.app.entity.*;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.app.feign.MfLoanApplyFeign;
import app.component.app.feign.MfTuningReportFeign;
import app.component.app.feign.MfBusFormConfigFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.entity.MfSysFeeItem;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.common.ApplyEnum.BUDGET_DEFFLAG_TYPE;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.component.cus.cusinvoicemation.feign.MfCusInvoiceMationFeign;
import app.component.cus.dishonestinfo.entity.MfCusDishonestInfo;
import app.component.cus.dishonestinfo.feign.MfCusDishonestInfoFeign;
import app.component.cus.entity.*;
import app.component.cus.execnotice.entity.MfCusExecNotice;
import app.component.cus.execnotice.feign.MfCusExecNoticeFeign;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusHighInfoFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.examinterface.ExamInterfaceFeign;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.model.feign.MfTemplateBizConfigFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sys.entity.SysUser;
import app.component.sysextendinterface.SysExtendInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.alibaba.fastjson.JSON;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import config.YmlConfig;
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
 * 类名： MfLoanApplyAction 描述：个贷业务所有action
 *
 * @author YuShuai
 * @date 2017-5-31 下午2:32:16
 *
 */
@Controller
@RequestMapping("/mfLoanApply")
public class MfLoanApplyController extends BaseFormBean {

    // 调用业务逻辑层
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;
    @Autowired
    private CalcInterfaceFeign calcInterfaceFeign;
    @Autowired
    private AppInterfaceFeign appInterfaceFeign;
    @Autowired
    private SysExtendInterfaceFeign sysExtendInterfaceFeign;
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfLoanApplyFeign mfLoanApplyFeign;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private MfBusAppKindFeign mfBusAppKindFeign;
    @Autowired
    private MfBusFormConfigFeign mfBusFormConfigFeign;
    @Autowired
    private WkfBusInterfaceFeign wkfBusInterfaceFeign;
    @Autowired
    private ExamInterfaceFeign examInterfaceFeign;
    @Autowired
    private TaskFeign taskFeign;
    //注入MfTuningReportBo
    @Autowired
    private MfTuningReportFeign mfTuningReportFeign;
    @Autowired
    private MfCusInvoiceMationFeign mfCusInvoiceMationFeign;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private MfTemplateBizConfigFeign mfTemplateBizConfigFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private MfCusDishonestInfoFeign mfCusDishonestInfoFeign;
    @Autowired
    private MfCusExecNoticeFeign mfCusExecNoticeFeign;
    @Autowired
    private MfBusCollateralRelFeign mfBusCollateralRelFeign;
    @Autowired
    private MfBusAgenciesFeign mfBusAgenciesFeign;
    @Autowired
    private MfCusCreditApplyFeign mfCusCreditApplyFeign;
    @Autowired
    private MfBusAppFeeFeign mfBusAppFeeFeign;
    @Autowired
    private MfCusHighInfoFeign mfCusHighInfoFeign;
    @Autowired
    private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
    @Autowired
    private AssureInterfaceFeign assureInterfaceFeign;
    // 审批参数

    // 异步参数
    // 全局变量

    /**
     * 方法描述： 进件公共表单 针对北京流程
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-31 下午2:52:50
     *
     */
    @RequestMapping(value = "/inputBusCommonForm")
    public String inputBusCommonForm(Model model, String ajaxData, String cusType, String kindNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String jsonStr = "";
        String cusBaseType = "";
        // 获取默认的客户类型
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (StringUtil.isEmpty(cusType)) {
            List<ParmDic> parmList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_TYPE");
            if (parmList.size() > 0) {
                cusType = parmList.get(0).getOptCode();
            } else {
                cusType = "100";
            }
        }
        cusBaseType = cusType.substring(0, 1);

        // 获取默认的产品编号
        if (StringUtil.isEmpty(kindNo)) {
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind.setKindProperty("2");
            mfSysKind.setUseFlag("1");
            mfSysKind.setCusType(cusType);
            mfSysKind.setBrNo(User.getOrgNo(request));
            mfSysKind.setRoleNoArray(User.getRoleNo(request));
            List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
            if (mfSysKindList != null && mfSysKindList.size() > 0) {
                kindNo = mfSysKindList.get(0).getKindNo();
            } else {
                mfSysKind.setUseFlag("1");
                mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
                kindNo = mfSysKindList.get(0).getKindNo();
            }
        }

        // 前台传入formType ：供保存的时候确定处理对应的业务 property:对应的dhcc:bootstarpTag的property
        ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        try {
            // 获取该节点配置的表单 kindNo 产品种类编号 nodeNo 节点编号 category 表单类别
            // 1：进件；2：流程节点；showType 表单类型 1：正常表单；2：列表；editFlag 是否允许编辑
            // 1：是；0：否；delFlag 0:多表单，1：单表单
            List<MfKindForm> kindFormList = prdctInterfaceFeign.getInitKindFormList(kindNo, BizPubParm.CUS_APPLY_NODE_NO, null);

            if (kindFormList != null && kindFormList.size() > 0) {
                for (MfKindForm mfKindForm : kindFormList) {

                    // 根据客户类型只获取对应的表单（个人或者企业）
                    if ("basecuscorp".equals(mfKindForm.getAddModelBase()) && "2".equals(cusBaseType)) {
                        continue;
                    }
                    if ("basecusperson".equals(mfKindForm.getAddModelBase()) && "1".equals(cusBaseType)) {
                        continue;
                    }
                    // 获取对应的实体对象
                    Object obj = mfLoanApplyFeign.getInitFormObj(mfKindForm, kindNo);

                    // 处理正常表单
                    if ("1".equals(mfKindForm.getShowType())) {
                        if (("basecusperson").equals(mfKindForm.getAddModelBase())) {// 处理个人客户基本信息
                            FormData formcusperson = formService.getFormData(mfKindForm.getAddModel());
                            getObjValue(formcusperson, obj);
                            map.put("formType", "basecusperson");
                            map.put("property", "formcusperson");
                            mapList.add(map);
                        }
                        if (("basecuscorp").equals(mfKindForm.getAddModelBase())) {// 处理企业客户基本信息
                            FormData formcuscorp = formService.getFormData(mfKindForm.getAddModel());
                            getObjValue(formcuscorp, obj);
                            map.put("formType", "basecuscorp");
                            map.put("property", "formcuscorp");
                            mapList.add(map);
                        }
                        if (("basebusapply").equals(mfKindForm.getAddModelBase())) {// 处理业务申请信息
                            FormData formbusapply = formService.getFormData(mfKindForm.getAddModel());
                            // 贷款投向
                            jsonStr = prdctInterfaceFeign.getFincUse(kindNo).toString();

                            if (StringUtil.isNotEmpty(((MfBusApply) obj).getAmtRange())) {
                                this.changeFormProperty(formbusapply, "appAmt", "alt",
                                        ((MfBusApply) obj).getAmtRange());
                            }
                            // 处理期限
                            if (StringUtil.isNotEmpty(((MfBusApply) obj).getTermRange())) {
                                this.changeFormProperty(formbusapply, "term", "alt", ((MfBusApply) obj).getTermRange());
                            }

                            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
                            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
                            MfSysKind mfSysKind = new MfSysKind();
                            mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
                            String rateUnit = rateTypeMap.get(mfSysKind.getRateType()).getRemark();
                            this.changeFormProperty(formbusapply, "fincRate", "unit", rateUnit);
                            this.changeFormProperty(formbusapply, "overRate", "unit", rateUnit);
                            this.changeFormProperty(formbusapply, "cmpdRate", "unit", rateUnit);
                            // 处理还款方式
                            List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfSysKind.getRepayType(),
                                    mfSysKind.getRepayTypeDef());
                            this.changeFormProperty(formbusapply, "repayType", "optionArray", repayTypeList);

                            getObjValue(formbusapply, obj);
                            map.put("formType", "basebusapply");
                            map.put("property", "formbusapply");
                            mapList.add(map);
                        }

                    } else {// 处理列表或者其他

                    }
                }
            } else {// 是否需要添加基础数据 暂时抛异常
                throw new Exception("需要添加基础数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        model.addAttribute("jsonStr", jsonStr);
        model.addAttribute("query", "");
        return "/component/app/inputBusCommonForm";
    }

    /**
     * 方法描述： 插入多表单内容
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-7-2 上午10:46:05
     */
    @RequestMapping(value = "/insertCommonAjax")
    @ResponseBody
    public Map<String, Object> insertCommonAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> resultMap = new HashMap<String, String>();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        MfBusApply mfBusApply = new MfBusApply();
        MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
        MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
        Map map = null;
        String url = null;
        try {
            // 前台传过来多个表单的数据 通过@符号分割
            if (ajaxData.endsWith("@")) {
                ajaxData = ajaxData.substring(0, ajaxData.length() - 1);
            }
            String[] dataStr = ajaxData.split("@");

            if (dataStr.length > 1) {
                // 循环前台传过来的数据分别处理业务数据
                for (String formData : dataStr) {
                    map = getMapByJson(formData);
                    // 处理正常表单内容
                    if (("basecusperson").equals(map.get("formType"))) {// 处理个人客户基本信息
                        FormData formcusperson = formService.getFormData((String) map.get("formId"));
                        getFormValue(formcusperson, map);


                        setObjValue(formcusperson, mfCusCustomer);
                        // 个人客户基本信息数据
                        setObjValue(formcusperson, mfCusPersBaseInfo);
                    }
                    if (("basecuscorp").equals(map.get("formType"))) {// 处理企业客户基本信息
                        FormData formcuscorp = formService.getFormData((String) map.get("formId"));
                        getFormValue(formcuscorp, map);

                        setObjValue(formcuscorp, mfCusCustomer);
                        setObjValue(formcuscorp, mfCusCorpBaseInfo);
                    }
                    if (("basebusapply").equals(map.get("formType"))) {// 处理业务申请信息
                        FormData formbusapply = formService.getFormData((String) map.get("formId"));
                        getFormValue(formbusapply, map);
                        setObjValue(formbusapply, mfBusApply);
                    }
                }
                resultMap = mfLoanApplyFeign.insertMultipleForms(mfCusCustomer, mfCusPersBaseInfo, mfCusCorpBaseInfo,
                        mfBusApply);
            }

            if ("0000".equals(resultMap.get("result"))) {
                if (StringUtil.isNotEmpty(resultMap.get("busModel"))) {// 不同的业务模式返回的页面不同
                    if (BizPubParm.BUS_MODEL_12.equals(resultMap.get("busModel"))) {
                        url = "/mfBusApply/getSummary.action?appId=" + resultMap.get("appId") + "&busEntrance=1";
                    } else {
                        url = "/mfCusCustomer/getById.action?cusNo=" + resultMap.get("cusNo") + "&appId="
                                + resultMap.get("appId") + "&cusType=" + resultMap.get("cusType");
                    }
                } else {
                    url = "/mfCusCustome/getById.action?cusNo=" + resultMap.get("cusNo") + "&appId="
                            + resultMap.get("appId") + "&cusType=" + resultMap.get("cusType");
                }
                String appId = resultMap.get("appId");
                String cusNo = resultMap.get("cusNo");
                dataMap.put("flag", "success");
                dataMap.put("url", url);
                dataMap.put("appId", appId);
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 第二次提报表单展示
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-11 上午11:49:15
     */
    @RequestMapping(value = "/inputTwoReportForm")
    public String inputTwoReportForm(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String scNo = WKF_NODE.two_report.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.two_report.getNodeNo();// 功能节点编号

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        // 处理数据
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);

        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.two_report, null, null, User.getRegNo(request));
        FormData formapply0001 = formService.getFormData(formId);
        getObjValue(formapply0001, mfBusApply);

        this.changeFormPropertyForRate(formapply0001, "fincRate", appId, mfBusApply.getKindNo());
        this.changeFormPropertyForRate(formapply0001, "overRate", appId, mfBusApply.getKindNo());

        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
        // 处理还款方式
        List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfBusAppKind.getRepayType(),
                mfBusAppKind.getRepayTypeDef());
        this.changeFormProperty(formapply0001, "repayType", "optionArray", repayTypeList);

        String jsonArrayStr = prdctInterfaceFeign.getFincUse(mfBusApply.getKindNo());
        com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
        String jsonStr = jsonArray.toString();
        scNo = BizPubParm.SCENCE_TYPE_DOC_TIBAO;
        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("scNo", scNo);
        model.addAttribute("jsonStr", jsonStr);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("query", "");
        return "/component/app/inputTwoReportForm";
    }

    /**
     * 方法描述： 进件后要件上传表单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-6-6 下午12:23:21
     */
    @RequestMapping(value = "/inputUploadForm")
    public String inputUploadForm(Model model, String ajaxData, String appId, String nodeNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        MfBusApply mfBusApply = new MfBusApply();
        Map<String, Object> dataMap = new HashMap<String, Object>();

        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusAppKind.setAppId(mfBusApply.getAppId());
        mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
        String cusNo = mfBusApply.getCusNo();
        String kindNo = mfBusApply.getKindNo();

        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.apply_input, null, null, User.getRegNo(request));
        FormData formapply0001 = formService.getFormData(formId);
        // 处理数据
        getObjValue(formapply0001, mfBusApply);
        // 处理利率
        this.changeFormPropertyForRate(formapply0001, "fincRate", appId, mfBusApply.getKindNo());
        this.changeFormPropertyForRate(formapply0001, "overRate", appId, mfBusApply.getKindNo());
        // 处理还款方式
        List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfBusAppKind.getRepayType(), mfBusApply.getRepayType());
        this.changeFormProperty(formapply0001, "repayType", "optionArray", repayTypeList);
        // 处理贷款方式
        String jsonStr = prdctInterfaceFeign.getFincUse(mfBusAppKind.getKindNo()).toString();
        // 处理期限金额的范围
        if ((mfBusAppKind.getMinAmt() != null) && (mfBusAppKind.getMaxAmt() != null)) {
            double minAmtD = mfBusAppKind.getMinAmt();
            double maxAmtD = mfBusAppKind.getMaxAmt();
            dataMap.put("minAmt", String.valueOf(minAmtD));
            dataMap.put("maxAmt", String.valueOf(maxAmtD));
        }
        // 处理期限
        if (null != mfBusAppKind.getTermType()) {
            String termType = mfBusAppKind.getTermType();// 合同期限 1月 2日
            int minTerm = mfBusAppKind.getMinTerm();// 合同期限下限
            int maxTerm = mfBusAppKind.getMaxTerm();// 合同期限上限
            dataMap.put("minTerm", String.valueOf(minTerm));
            dataMap.put("maxTerm", String.valueOf(maxTerm));
            dataMap.put("termType", termType);
        }

        Task task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
        Map<String, Object> map = creditApplyInterfaceFeign.getByCusNoAndKindNo(cusNo, kindNo);
        dataMap.put("creditAmt", map.get("creditAmt"));
        dataMap.put("creditflag", map.get("creditflag"));
        dataMap.put("kindCreditflag", map.get("kindCreditflag"));
        dataMap.put("kindCreditAmt", map.get("kindCreditAmt"));
        dataMap.put("kindName", mfBusAppKind.getKindName());
        dataMap.put("nodeName", task.getDescription());
        kindNo = mfBusAppKind.getKindNo();
        nodeNo = WKF_NODE.apply_input.getNodeNo();// 功能节点编号
        cusNo = mfBusApply.getCusNo();
        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("jsonStr", jsonStr);
        model.addAttribute("query", "");

        // ---------- begin 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        Map<String, ParmDic> SAVE_ONLY = new CodeUtils().getMapObjByKeyName("SAVE_ONLY");// 启用: 显示保存及提交两个按钮(支持保存未提交) 停用: 只显示保存按钮
        String SAVE_ONLY_3 = "0";
        if (SAVE_ONLY.containsKey("3")) {// 启用
            SAVE_ONLY_3 = "1";// 支持保存未提交
        }
        model.addAttribute("SAVE_ONLY_3", SAVE_ONLY_3);
        // ---------- end 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------

        return "/component/app/inputUploadForm";
    }

    /**
     * 方法描述： 更新二次提报的表单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-11 下午2:16:44
     */
    @RequestMapping(value = "/updateApplyInfo")
    @ResponseBody
    public Map<String, Object> updateApplyInfo(Model model, String ajaxData, String temporaryStorage) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        dataMap = getMapByJson(ajaxData);
        String formId = (String) dataMap.get("formId");
        FormData formapply0001 = formService.getFormData(formId);
        getFormValue(formapply0001, getMapByJson(ajaxData));
        MfBusApply mfBusApply = new MfBusApply();
        setObjValue(formapply0001, mfBusApply);
        try {
            Result result = mfLoanApplyFeign.updateAndCommit(mfBusApply, temporaryStorage);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            dataMap.put("flag", "success");
            dataMap.put("appSts", mfBusApply.getAppSts());
            dataMap.put("appId", mfBusApply.getAppId());
            dataMap.put("msg", result.getMsg());
            dataMap.put("wkfId", mfBusApply.getWkfAppId());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_SERVER);
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 分单表单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-31 下午4:03:23
     */
    @RequestMapping(value = "/inputReinPolicyForm")
    public String inputReinPolicyForm(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String scNo = WKF_NODE.reinsurance_policy.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.reinsurance_policy.getNodeNo();// 功能节点编号

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        String cusNo = mfBusApply.getCusNo();
        mfBusApply.setOpName("");
        mfBusApply.setOpNo("");

        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.reinsurance_policy, null, null, User.getRegNo(request));
        FormData formsys5003 = formService.getFormData(formId);

        this.changeFormPropertyForRate(formsys5003, "fincRate", appId, mfBusApply.getKindNo());
        this.changeFormPropertyForRate(formsys5003, "overRate", appId, mfBusApply.getKindNo());

        getObjValue(formsys5003, mfBusApply);

        // 初始化下一岗位人员数据，需要在下一节点配置角色
        Task task_ = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
        String[] next = taskFeign.getNextTaskExecutor(task_.getId());    // users,
        // groups,
        // level
        JSONArray userJsonArray = new JSONArray();
        if (StringUtil.isNotEmpty(next[1])) {
            String[] nextUserDesc = wkfInterfaceFeign.getWkfUserForRoleArr(next);
            String[] ids = nextUserDesc[0].split(",");
            String[] names = nextUserDesc[1].split(",");

            for (int i = 0; i < ids.length; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", ids[i]);
                jsonObject.put("name", names[i]);
                userJsonArray.add(jsonObject);
            }
        }

        ajaxData = userJsonArray.toString();
        model.addAttribute("scNo", scNo);
        model.addAttribute("formsys5003", formsys5003);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("query", "");
        return "/component/app/inputReinPolicyForm";
    }

    /**
     * 方法描述：面签分单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-31 下午4:03:23
     */
    @RequestMapping(value = "/inputReinPolicySignForm")
    public String inputReinPolicySignForm(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String scNo = WKF_NODE.reinsurance_policy.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.reinsurance_sign_policy.getNodeNo();// 功能节点编号

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        String cusNo = mfBusApply.getCusNo();
        mfBusApply.setOpName("");
        mfBusApply.setOpNo("");

        // 获取审批历史表中的最新一条审批历史数据
        MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
        mfBusApplyHis.setAppId(appId);
        List<MfBusApplyHis> list = appInterfaceFeign.getApplyHisListByAppId(mfBusApplyHis);
        if (list != null && list.size() > 0) {
            mfBusApplyHis = list.get(0);

            mfBusApply.setAppAmt(mfBusApplyHis.getAppAmt());
        }

        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.reinsurance_sign_policy, null, null, User.getRegNo(request));
        FormData formsys5003 = formService.getFormData(formId);

        this.changeFormPropertyForRate(formsys5003, "fincRate", appId, mfBusApply.getKindNo());
        this.changeFormPropertyForRate(formsys5003, "overRate", appId, mfBusApply.getKindNo());

        getObjValue(formsys5003, mfBusApply);
        List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
        JSONArray userJsonArray = new JSONArray();
        for (int i = 0; i < userList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userList.get(i).getOpNo());
            jsonObject.put("name", userList.get(i).getOpName());
            userJsonArray.add(jsonObject);
        }
        ajaxData = userJsonArray.toString();
        model.addAttribute("formsys5003", formsys5003);
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("query", "");
        return "/component/app/inputReinPolicyForm";
    }

    /**
     * 方法描述：上抵分单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-31 下午4:03:23
     */
    @RequestMapping(value = "/inputReinPolicyArrivalForm")
    public String inputReinPolicyArrivalForm(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String scNo = WKF_NODE.reinsurance_policy.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.reinsurance_arrival_policy.getNodeNo();// 功能节点编号

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        String cusNo = mfBusApply.getCusNo();
        mfBusApply.setOpName("");
        mfBusApply.setOpNo("");

        String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.reinsurance_arrival_policy, null, null, User.getRegNo(request));
        FormData formsys5003 = formService.getFormData(formId);

        getObjValue(formsys5003, mfBusApply);

        this.changeFormPropertyForRate(formsys5003, "fincRate", appId, mfBusApply.getKindNo());
        this.changeFormPropertyForRate(formsys5003, "overRate", appId, mfBusApply.getKindNo());

        List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
        JSONArray userJsonArray = new JSONArray();
        for (int i = 0; i < userList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userList.get(i).getOpNo());
            jsonObject.put("name", userList.get(i).getOpName());
            userJsonArray.add(jsonObject);
        }
        ajaxData = userJsonArray.toString();
        model.addAttribute("formsys5003", formsys5003);
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("query", "");
        return "/component/app/inputReinPolicyForm";
    }

    /**
     * 方法描述： 处理页面的利率
     *
     * @param formData
     * @param colum
     * @param appId
     * @param kindNo
     * @throws Exception
     *             void
     * @author YuShuai
     * @date 2017-8-3 上午10:28:10
     */
    private void changeFormPropertyForRate(FormData formData, String colum, String appId, String kindNo)
            throws Exception {
        String rateType = "";
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
        if (mfBusAppKind == null) {
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
            rateType = mfSysKind.getRateType();
        } else {
            rateType = mfBusAppKind.getRateType();
        }
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(rateType).getRemark();
        this.changeFormProperty(formData, colum, "unit", rateUnit);
    }

    /**
     * 方法描述： 处理分单人员的保存方法
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-31 下午4:08:38
     */
    @RequestMapping(value = "/insertNextUserAjax")
    @ResponseBody
    public Map<String, Object> insertNextUserAjax(String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map map = null;
            map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            FormData formsys5003 = formService.getFormData(formId);
            getFormValue(formsys5003, map);
            MfBusApply mfBusApply = new MfBusApply();
            setObjValue(formsys5003, mfBusApply);
            Result result = mfLoanApplyFeign.updatePolicyAndCommit(mfBusApply);
            mfBusApply = mfLoanApplyFeign.getByIdOrAppId(mfBusApply);
            dataMap.put("appId", appId);
            dataMap.put("appSts", mfBusApply.getAppSts());
            dataMap.put("msg", result.getMsg());
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
     * 方法描述： 风控调查（主要展示申请基本信息，提供要件线下已经获取系统进行勾选操作，目前天诚普惠的赎楼&过桥使用）
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @date 2018-1-3 下午4:08:03
     */
    @RequestMapping(value = "/riskInvestigate")
    public String riskInvestigate(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String cmpdRateType = "";
        String jsonStr = "";
        String scNo = WKF_NODE.risk_investigate.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.risk_investigate.getNodeNo();// 功能节点编号
        MfBusApply mfBusApply = new MfBusApply();
        FormData formapply0001;
        try {
            JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
            String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
            ajaxData = items;
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
            // 共同借款人
            jsonStr = cusInterfaceFeign.getCobBorrower(mfBusApply.getCusNo()).toString();

            String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.resp_investigation, null, null, User.getRegNo(request));
            formapply0001 = formService.getFormData(formId);

            getObjValue(formapply0001, mfBusApply);
            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
            this.changeFormProperty(formapply0001, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "overRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "cmpdRate", "unit", rateUnit);
            cmpdRateType = mfBusApply.getCmpFltRateShow();
            // 添加贷后检查模型数据
            List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
            this.changeFormProperty(formapply0001, "templateId", "optionArray", examList);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cmpdRateType", cmpdRateType);
        model.addAttribute("jsonStr", jsonStr);
        model.addAttribute("query", "");
        return "/component/app/riskInvestigate";
    }


    @RequestMapping(value = "/getCoborrNumList")
    @ResponseBody
    public Map<String,Object> getCoborrNumList(String ajaxData,int pageNo,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<String, Object>();
        MfCusCustomer  mfCusCustomer = new  MfCusCustomer();
        try {
            mfCusCustomer.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);//我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            mfCusCustomer.setCusNo(cusNo);
            //自定义查询Feign方法
            ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
            ipage =cusInterfaceFeign.getCoborrNumList(ipage);
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
     * 方法描述： 尽调报告提示是否进行下一步后的业务处理 下一步提交到业务审批
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-11 下午8:40:13
     */
    @RequestMapping(value = "/confirmTuningReport")
    public String confirmTuningReport(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String popFlag =request.getParameter("popFlag");
        String scNo = WKF_NODE.resp_investigation.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.resp_investigation.getNodeNo();// 功能节点编号
        if("3".equals(popFlag)){
            nodeNo = "refin_investigation";
        }
        String cmpdRateType = "";
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        FormData formapply0001;
        FormData formQuotaCalc;
        try {
            JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
            String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
            ajaxData = items;
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
            String invoiceMationId = mfBusApply.getInvoiceMationId();
            if(StringUtil.isNotEmpty(invoiceMationId)){
                MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
                mfCusInvoiceMation.setId(invoiceMationId);
                mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
                if(mfCusInvoiceMation != null){
                    mfBusApply.setTaxpayerNo(mfCusInvoiceMation.getTaxpayerNo());
                    mfBusApply.setAddress(mfCusInvoiceMation.getAddress());
                    mfBusApply.setTel(mfCusInvoiceMation.getTel());
                    mfBusApply.setBillBankName(mfCusInvoiceMation.getBankName());
                    mfBusApply.setAccountNumber(mfCusInvoiceMation.getAccountNumber());
                }
            }

            String busModel = mfBusApply.getBusModel();
            if(BizPubParm.BUS_MODEL_12.equals(busModel)){
                MfBusApplySecond mfBusApplySecond = new MfBusApplySecond();
                mfBusApplySecond.setAppId(mfBusApply.getAppId());
                mfBusApplySecond = appInterfaceFeign.getMfBusApplySecondByAppId(mfBusApplySecond);
                if(mfBusApplySecond != null){
                    mfBusApply.setAssureAmt(mfBusApplySecond.getAssureAmt());
                    mfBusApply.setAssureAmtRate(mfBusApplySecond.getAssureAmtRate());
                    mfBusApply.setProjectPayRemark(mfBusApplySecond.getProjectPayRemark());
                    mfBusApply.setBeneficiary(mfBusApplySecond.getBeneficiary());
                    mfBusApply.setGuaEndDate(mfBusApplySecond.getGuaEndDate());
                    mfBusApply.setShouldFeeSum(mfBusApplySecond.getShouldFeeSum());
                    mfBusApply.setCorpQua(mfBusApplySecond.getCorpQua());
                    mfBusApply.setNetAssets(mfBusApplySecond.getNetAssets());
                    if(StringUtil.isEmpty(mfBusApplySecond.getAuthority())){
                        mfBusApply.setAuthority(mfLoanApplyFeign.getAuthority(mfBusApply));
                    }else{
                        mfBusApply.setAuthority(mfBusApplySecond.getAuthority());
                    }
                    mfBusApply.setSurveyOpinion(mfBusApplySecond.getSurveyOpinion());
                    mfBusApply.setStartUpSituation(mfBusApplySecond.getStartUpSituation());
                    mfBusApply.setConstructionScope(mfBusApplySecond.getConstructionScope());
                    mfBusApply.setOldAppName(mfBusApplySecond.getOldAppName());
                    mfBusApply.setBondAccount(mfBusApplySecond.getBondAccount());
                    mfBusApply.setBondAccName(mfBusApplySecond.getBondAccName());
                    mfBusApply.setBondBank(mfBusApplySecond.getBondBank());
                    mfBusApply.setBondAccId(mfBusApplySecond.getBondAccId());
                    mfBusApply.setCollectAccount(mfBusApplySecond.getCollectAccount());
                    mfBusApply.setCollectAccName(mfBusApplySecond.getCollectAccName());
                    mfBusApply.setCollectBank(mfBusApplySecond.getCollectBank());
                    mfBusApply.setCollectAccId(mfBusApplySecond.getCollectAccId());
                }
            }
            String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.resp_investigation, null, null, User.getRegNo(request));
            formapply0001 = formService.getFormData(formId);
            //判断业务是否关联授信
            String guaranteeScheme1="";
            String opinion1="";
            String counterGuaranteeScheme1="";
            if(StringUtil.isNotEmpty(mfBusApply.getCreditAppId())){
                MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
                mfCusCreditApply.setCreditAppId(mfBusApply.getCreditAppId());
                mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
                if(mfCusCreditApply!=null){
                    guaranteeScheme1 = mfCusCreditApply.getGuaranteeScheme();
                    opinion1 = mfCusCreditApply.getOpinion();
                    counterGuaranteeScheme1 = mfCusCreditApply.getCounterGuaranteeScheme();
                    mfCusCreditApply.setCreditAmt(mfCusCreditApply.getAuthBal());
                }
                getObjValue(formapply0001, mfCusCreditApply);
            }
            MfBusAppFee mfBusAppFee = new MfBusAppFee();
            mfBusAppFee.setAppId(appId);
            List<MfBusAppFee> mfBusAppFeeList = mfBusAppFeeFeign.getMfBusAppFeeList(mfBusAppFee);
            Double danBao = 0.00;
            Double pinShen = 0.00;
            for (int i = 0; i < mfBusAppFeeList.size(); i++) {
                mfBusAppFee = mfBusAppFeeList.get(i);
                if("1".equals(mfBusAppFee.getItemNo())){
                    danBao =mfBusAppFee.getRateScale();
                }
                if("2".equals(mfBusAppFee.getItemNo())){
                    pinShen =mfBusAppFee.getRateScale();
                }
            }
            //同意提供{申请金额}万元贷款担保，期限{担保期限}月。  同意担保换期权，行权份额占行权后股权总额的    %。  担保年费率：{担保费率}%,评审费率：{评审费率}%。
            if(BizPubParm.BUS_MODEL_10.equals(mfBusApply.getBusModel()) && StringUtil.isEmpty(guaranteeScheme1)){
                if(StringUtil.isEmpty( mfBusApply.getGuaranteeScheme())){
                    StringBuffer guaranteeScheme =new StringBuffer("同意提供");
                    guaranteeScheme.append(mfBusApply.getAppAmt()/10000);
                    guaranteeScheme.append("万元贷款担保，期限").append(Double.valueOf(mfBusApply.getTerm())/12).append("+0年，");
                    guaranteeScheme.append("实际用款金额为").append(mfBusApply.getAppAmt()/10000).append("万元。合作银行为：");
                    guaranteeScheme.append(mfBusApply.getAgenciesName()).append("。\r\n");
                    guaranteeScheme.append("担保年费率：").append(danBao).append("%,评审费率：").append(pinShen).append("%").append("。");
                    guaranteeScheme.append("（注：在我司《关于调整部分科技型小微企业收费标准的通知》适用期间内新发生的业务，执行科技型小微企业收费政策，即担保费率1%/年，评审费率/次；政策适用期间之外发生的业务，按照我司正常收费标准收费。）");
                    mfBusApply.setGuaranteeScheme(guaranteeScheme.toString());
                }
            }
            //该公司符合创业保白名单准入，准入条件为        。已经完成对借款主体、实控人      的信用评价、债项评价和实地考察。 评审时点借款企业银行借款金额为       万元、实控人及其配偶个人经营贷金额合计       万元，本次提供     万元额度后，未超过近12个月纳税收入    万元 的40%。
            if(BizPubParm.BUS_MODEL_10.equals(mfBusApply.getBusModel()) && StringUtil.isEmpty(opinion1)){
                if(StringUtil.isEmpty( mfBusApply.getOpinion())){
                    MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
                    mfCusHighInfo.setCusNo(mfBusApply.getCusNo());
                    mfCusHighInfo.setHighCusType("4");
                    String shikongren="";
                    List<MfCusHighInfo> mfCusHighInfoList = mfCusHighInfoFeign.findByEntity(mfCusHighInfo);
                    if(mfCusHighInfoList.size()>0){
                        shikongren = mfCusHighInfoList.get(0).getHighName();
                    }
                    MfBusApplySecond mfBusApplySecond = new MfBusApplySecond();
                    mfBusApplySecond.setAppId(mfBusApply.getAppId());
                    mfBusApplySecond = appInterfaceFeign.getMfBusApplySecondByAppId(mfBusApplySecond);
                    StringBuffer opinion =new StringBuffer("该公司符合");
                    opinion.append(mfBusApply.getKindName()).append("产品主体资格认定,");
                    opinion.append("已经完成对借款主体以及实控人").append(shikongren);
                    opinion.append("的信用评价、债项评价和实地考察。");
                    opinion.append("\r\n");
                    opinion.append("评审时点借款企业银行借款金额（或者可用银行授信额度）为 ").append(mfBusApply.getAppAmt()/10000).append("万元、融资租赁余额      万元，对外担保金额       万元 ，实控人及其配偶个人银行借款金额合计          万元，");
                    opinion.append("本次提供").append(mfBusApply.getAppAmt()/10000).append("万元额度后，");
                    // 取超过近12个月纳税收入   {纳税收入（额度测算中字段）}
                    double calc3 = mfBusApplySecond.getCalc3()!= null ?mfBusApplySecond.getCalc3():0;
                    opinion.append("未超过近12个月纳税收入").append(calc3/10000).append("万元的30%。");
                    mfBusApply.setOpinion(opinion.toString());
                }
            }
            //反担保方案：{保证人姓名（多个逗号隔开，不显示企业）} 承担个人连带责任保证。
            if(BizPubParm.BUS_MODEL_10.equals(mfBusApply.getBusModel()) && StringUtil.isEmpty(counterGuaranteeScheme1)){
                if(StringUtil.isEmpty( mfBusApply.getCounterGuaranteeScheme())){
                    StringBuffer counterGuaranteeScheme =new StringBuffer();
                    List<MfBusCollateralDetailRel> list = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(mfBusApply.getAppId(),"pledge");
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
                    mfBusApply.setCounterGuaranteeScheme(counterGuaranteeScheme.toString());
                }
            }

            if(StringUtil.isEmpty(mfBusApply.getTongDunScore())){
                mfBusApply.setTongDunScore(cusInterfaceFeign.getTongDunByCusNo(mfBusApply.getCusNo(),mfBusApply.getCreditAppId()));
            }
            if(StringUtil.isEmpty(mfBusApply.getIfFirstLoan())){
                mfBusApply.setIfFirstLoan(mfBusApplyFeign.getIfFirstLoan(mfBusApply.getCusNo()));
            }
            getObjValue(formapply0001, mfBusApply);
            String cusNo = mfBusApply.getCusNo();
            Map<String,String> cusInfoMap = new HashMap<String,String>();
            cusInfoMap.put("guaranteeOuterFlag","1");
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
                            if(StringUtil.isEmpty(mfBusApply.getIsLegalDegreeFlag() )){
                                if(StringUtil.isNotEmpty(legalDegree)&&("2".equals(legalDegree)||"3".equals(legalDegree)||"9".equals(legalDegree))){
                                    cusInfoMap.put("isLegalDegreeFlag","1");
                                }
                            }
                            String legalUniversity = mfCusCorpBaseInfo.getLegalUniversity();
                            if(StringUtil.isEmpty(mfBusApply.getLegalUniversity() )){
                                cusInfoMap.put("legalUniversity",legalUniversity);
                            }
                        }
                    }
                    MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
                    mfCusGuaranteeOuter.setCusNo(cusNo);
                    List<MfCusGuaranteeOuter> mfCusGuaranteeOuterList  = cusInterfaceFeign.getMfCusGuaranteeOuterList(mfCusGuaranteeOuter);
                    if(mfCusGuaranteeOuterList.size()>0){
                        cusInfoMap.put("guaranteeOuterFlag","0");
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
                    }
                }
            }

            if(StringUtil.isNotEmpty(appId)) {
                //获取保证人
                List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(appId);
                String assureName = "";
                if (mfAssureInfoList != null && mfAssureInfoList.size() > 0) {
                    for (int i = 0; i < mfAssureInfoList.size(); i++) {
                        assureName = assureName + mfAssureInfoList.get(i).getAssureName() + ",";
                    }
                }
                cusInfoMap.put("assureName", assureName);
                //获取合作银行
                String agenciesId = mfBusApply.getAgenciesId();
                if (StringUtil.isNotEmpty(agenciesId)) {
                    MfBusAgencies mfBusAgencies = new MfBusAgencies();
                    mfBusAgencies.setAgenciesId(agenciesId);
                    mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
                    if (mfBusAgencies != null) {
                        cusInfoMap.put("agenciesName", mfBusAgencies.getAgenciesName());
                        cusInfoMap.put("agenciesButtName", mfBusAgencies.getAgenciesButtName());
                        cusInfoMap.put("agenciesButtPhone", mfBusAgencies.getAgenciesButtPhone());
                    }
                }
            }

            getObjValue(formapply0001, cusInfoMap);
            //都为空的时候再查客户表
            if(StringUtil.isEmpty(mfBusApply.getIsXiaoWeiFlag()) && StringUtil.isEmpty(mfBusApply.getIsJinYingFlag())
                    && StringUtil.isEmpty(mfBusApply.getIsBeiJingFlag()) && StringUtil.isEmpty(mfBusApply.getIsGaoKeJiFlag())
                    && StringUtil.isEmpty(mfBusApply.getInventionPatent())){
                Map<String,String> networkMap = cusInterfaceFeign.getCusNewWorkMap(mfBusApply.getCusNo());
                getObjValue(formapply0001, networkMap);
            }
            if(BizPubParm.BUS_MODEL_12.equals(busModel)){
                Map<String,Object> parmMap = new HashMap<>();
                parmMap.put("applyOptionsId",mfBusApply.getApplyOptionsId());
                getObjValue(formapply0001, parmMap);
            }
            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
            this.changeFormProperty(formapply0001, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "overRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "cmpdRate", "unit", rateUnit);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(mfBusApply.getCusNo());
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

            this.changeFormProperty(formapply0001, "manageOpNo", "initValue", mfCusCustomer.getCusMngNo());
            this.changeFormProperty(formapply0001, "manageOpName", "initValue", mfBusApply.getCusMngName());
            this.changeFormProperty(formapply0001, "manageEndDate", "initValue", DateUtil.getShowDateTime(mfBusApply.getManageEndDate()));
            this.changeFormProperty(formapply0001, "guaEndDate", "initValue", DateUtil.getShowDateTime(mfBusApply.getGuaEndDate()));
            // 获取项目名称
			YmlConfig ymlConfig = (YmlConfig) SpringUtil.getBean(YmlConfig.class);
			String projectName = ymlConfig.getSysParams().get("sys.project.name");
            //处理其他担保方式
            List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
            MfBusAppKind mfBusAppKindVouType = new MfBusAppKind();
            mfBusAppKindVouType.setAppId(appId);
            mfBusAppKindVouType= mfBusAppKindFeign.getById(mfBusAppKindVouType);
            if(StringUtil.isEmpty(mfBusAppKindVouType.getVouType())){
                List<ParmDic> pdl = (List<ParmDic>)new CodeUtils().getCacheByKeyName("VOU_TYPE");
                for(ParmDic parmDic:pdl){
                    if(mfBusApply.getVouType().equals(parmDic.getOptCode()) || "1".equals(parmDic.getOptCode())){
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
            this.changeFormProperty(formapply0001, "vouTypeOther", "optionArray", vouTypeOtherList);
            cmpdRateType = mfBusApply.getCmpFltRateShow();

            // 添加贷后检查模型数据
            List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
            this.changeFormProperty(formapply0001, "templateId", "optionArray", examList);

            //额度测算表单
            MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
            if("1".equals(mfSysKind.getIfCreditCalc())){
                formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.quota_calc, null, null, User.getRegNo(request));
                formQuotaCalc = formService.getFormData(formId);
                getObjValue(formQuotaCalc, mfCusCustomer);
                this.changeFormProperty(formQuotaCalc, "creditSum", "initValue", MathExtend.moneyStr(mfBusApply.getAppAmt()).replaceAll(",", ""));
                this.changeFormProperty(formQuotaCalc, "kindNo", "initValue", mfBusApply.getKindNo());
                model.addAttribute("formQuotaCalc", formQuotaCalc);
                model.addAttribute("ifQuotaCalc", mfSysKind.getIfCreditCalc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            throw e;
        }

      //查询共借人列表
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCoborrNo());
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
        ipage =  mfCusCustomerFeign.getCusListByCusNos(ipage);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr("tablecoborrNameList", "tableTag", (List) ipage.getResult(), ipage, true);
        model.addAttribute("tableHtml",tableHtml);

        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("mfBusApply", mfBusApply);
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cmpdRateType", cmpdRateType);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("query", "");
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));

        // ---------- begin 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        String SAVE_ONLY_4 = "1";
        model.addAttribute("SAVE_ONLY_4", SAVE_ONLY_4);
        // ---------- end 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------

        return "/component/app/confirmTuningReport";
    }

    /**
     * 方法描述： 尽调报告提示是否进行下一步后的业务处理 下一步提交到业务审批
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-11 下午8:40:13
     */
    @RequestMapping(value = "/confirmTuningReportCredit")
    public String confirmTuningReportCredit(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String popFlag =request.getParameter("popFlag");
        String scNo = WKF_NODE.credit_resp.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.credit_resp.getNodeNo();// 功能节点编号
        if("3".equals(popFlag)){
            nodeNo = "refin_investigation";
        }
        String cmpdRateType = "";
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        FormData formapply0001;
        FormData formQuotaCalc;
        try {
            JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
            String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
            ajaxData = items;
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
            String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.credit_resp, null, null, User.getRegNo(request));
            formapply0001 = formService.getFormData(formId);
            //判断业务是否关联授信
            String guaranteeScheme1 ="";
            String opinion1 ="";
            if(StringUtil.isNotEmpty(mfBusApply.getCreditAppId())){
                MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
                mfCusCreditApply.setCreditAppId(mfBusApply.getCreditAppId());
                mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
                if(mfCusCreditApply!=null){
                    guaranteeScheme1 = mfCusCreditApply.getGuaranteeScheme();
                    opinion1 = mfCusCreditApply.getOpinion();
                }
                getObjValue(formapply0001, mfCusCreditApply);
            }
            MfBusAppFee mfBusAppFee = new MfBusAppFee();
            mfBusAppFee.setAppId(appId);
            List<MfBusAppFee> mfBusAppFeeList = mfBusAppFeeFeign.getMfBusAppFeeList(mfBusAppFee);
            Double danBao = 0.00;
            Double pinShen = 0.00;
            for (int i = 0; i < mfBusAppFeeList.size(); i++) {
                mfBusAppFee = mfBusAppFeeList.get(i);
                if("1".equals(mfBusAppFee.getItemNo())){
                    danBao =mfBusAppFee.getRateScale();
                }
                if("2".equals(mfBusAppFee.getItemNo())){
                    pinShen =mfBusAppFee.getRateScale();
                }
            }
            if(StringUtil.isEmpty(guaranteeScheme1)){
                //同意提供{申请金额}万元贷款担保，期限{担保期限}月。  同意担保换期权，行权份额占行权后股权总额的    %。  担保年费率：{担保费率}%,评审费率：{评审费率}%。
                if(StringUtil.isEmpty(mfBusApply.getGuaranteeScheme())){
                    StringBuffer guaranteeScheme =new StringBuffer("同意提供");
                    guaranteeScheme.append(mfBusApply.getAppAmt()/10000);
                    guaranteeScheme.append("万元贷款担保，期限").append(mfBusApply.getTermShow()).append("。同意担保换期权，行权份额占行权后股权总额的    %。");
                    guaranteeScheme.append("担保年费率：").append(danBao).append("%,评审费率：").append(pinShen).append("%").append("。");
                    mfBusApply.setGuaranteeScheme(guaranteeScheme.toString());
                }
            }
            //该公司符合创业保白名单准入，准入条件为        。已经完成对借款主体、实控人      的信用评价、债项评价和实地考察。 评审时点借款企业银行借款金额为       万元、实控人及其配偶个人经营贷金额合计       万元，本次提供     万元额度后，未超过近12个月纳税收入    万元 的40%。
            if(StringUtil.isEmpty(opinion1)){
                if(StringUtil.isEmpty( mfBusApply.getOpinion())){
                    MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
                    mfCusHighInfo.setCusNo(mfBusApply.getCusNo());
                    mfCusHighInfo.setHighCusType("4");
                    String shikongren="";
                    List<MfCusHighInfo> mfCusHighInfoList = mfCusHighInfoFeign.findByEntity(mfCusHighInfo);
                    if(mfCusHighInfoList.size()>0){
                        shikongren = mfCusHighInfoList.get(0).getHighName();
                    }
                    StringBuffer opinion =new StringBuffer("该公司符合");
                    opinion.append(mfBusApply.getKindName()).append("白名单准入，准入条件为        。");
                    opinion.append("已经完成对借款主体、实控人").append(shikongren);
                    opinion.append("的信用评价、债项评价和实地考察。 评审时点借款企业银行借款金额为       万元、实控人及其配偶个人经营贷金额合计       万元，");
                    opinion.append("本次提供 ").append(mfBusApply.getAppAmt()/10000).append("万元额度后，未超过近12个月纳税收入    万元 的40%。");
                    mfBusApply.setOpinion(opinion.toString());
                }
            }

            if(StringUtil.isEmpty(mfBusApply.getTongDunScore())){
                mfBusApply.setTongDunScore(cusInterfaceFeign.getTongDunByCusNo(mfBusApply.getCusNo(),mfBusApply.getCreditAppId()));
            }
            if(StringUtil.isEmpty(mfBusApply.getIfFirstLoan())){
                mfBusApply.setIfFirstLoan(mfBusApplyFeign.getIfFirstLoan(mfBusApply.getCusNo()));
            }
            getObjValue(formapply0001, mfBusApply);
            String cusNo = mfBusApply.getCusNo();
            Map<String,String> cusInfoMap = new HashMap<String,String>();
            cusInfoMap.put("isLegalDegreeFlag",mfBusApply.getIsLegalDegreeFlag() );
            cusInfoMap.put("guaranteeOuterFlag","1");
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
                            if(StringUtil.isEmpty(mfBusApply.getIsLegalDegreeFlag() )){
                                if(StringUtil.isNotEmpty(legalDegree)&&("2".equals(legalDegree)||"3".equals(legalDegree)||"9".equals(legalDegree))){
                                    cusInfoMap.put("isLegalDegreeFlag","1");
                                }
                            }
                            String legalUniversity = mfCusCorpBaseInfo.getLegalUniversity();
                            if(StringUtil.isEmpty(mfBusApply.getLegalUniversity() )){
                                cusInfoMap.put("legalUniversity",legalUniversity);
                            }
                        }
                    }
                    MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
                    mfCusGuaranteeOuter.setCusNo(cusNo);
                    List<MfCusGuaranteeOuter> mfCusGuaranteeOuterList  = cusInterfaceFeign.getMfCusGuaranteeOuterList(mfCusGuaranteeOuter);
                    if(mfCusGuaranteeOuterList.size()>0){
                        cusInfoMap.put("guaranteeOuterFlag","0");
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
                    }
                }
            }
            Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
            if(StringUtil.isNotEmpty(appId)) {
                //获取保证人
                List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(appId);
                String assureName = "";
                if (mfAssureInfoList != null && mfAssureInfoList.size() > 0) {
                    for (int i = 0; i < mfAssureInfoList.size(); i++) {
                        assureName = assureName + mfAssureInfoList.get(i).getAssureName() + ",";
                    }
                }
                cusInfoMap.put("assureName", assureName);
                //获取合作银行
                String agenciesId = mfBusApply.getAgenciesId();
                if (StringUtil.isNotEmpty(agenciesId)) {
                    MfBusAgencies mfBusAgencies = new MfBusAgencies();
                    mfBusAgencies.setAgenciesId(agenciesId);
                    mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
                    if (mfBusAgencies != null) {
                        cusInfoMap.put("agenciesName", mfBusAgencies.getAgenciesName());
                        cusInfoMap.put("agenciesButtName", mfBusAgencies.getAgenciesButtName());
                        cusInfoMap.put("agenciesButtPhone", mfBusAgencies.getAgenciesButtPhone());
                    }
                }
            }
            getObjValue(formapply0001, cusInfoMap);

            //都为空的时候再查客户表
            if(StringUtil.isEmpty(mfBusApply.getIsXiaoWeiFlag()) && StringUtil.isEmpty(mfBusApply.getIsJinYingFlag())
                    && StringUtil.isEmpty(mfBusApply.getIsBeiJingFlag()) && StringUtil.isEmpty(mfBusApply.getIsGaoKeJiFlag())
                    && StringUtil.isEmpty(mfBusApply.getInventionPatent())){
                Map<String,String> networkMap = cusInterfaceFeign.getCusNewWorkMap(mfBusApply.getCusNo());
                getObjValue(formapply0001, networkMap);
            }


            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
            this.changeFormProperty(formapply0001, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "overRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "cmpdRate", "unit", rateUnit);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(mfBusApply.getCusNo());
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

            this.changeFormProperty(formapply0001, "manageOpNo", "initValue", mfCusCustomer.getCusMngNo());
            this.changeFormProperty(formapply0001, "manageOpName", "initValue", mfBusApply.getCusMngName());
            // 获取项目名称
            YmlConfig ymlConfig = (YmlConfig) SpringUtil.getBean(YmlConfig.class);
            String projectName = ymlConfig.getSysParams().get("sys.project.name");
            //处理其他担保方式
            List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
            MfBusAppKind mfBusAppKindVouType = new MfBusAppKind();
            mfBusAppKindVouType.setAppId(appId);
            mfBusAppKindVouType= mfBusAppKindFeign.getById(mfBusAppKindVouType);

            if(StringUtil.isEmpty(mfBusAppKindVouType.getVouType())){
                List<ParmDic> pdl = (List<ParmDic>)new CodeUtils().getCacheByKeyName("VOU_TYPE");
                for(ParmDic parmDic:pdl){
                    if(mfBusApply.getVouType().equals(parmDic.getOptCode()) || "1".equals(parmDic.getOptCode())){
                        continue;
                    }
                    OptionsList s= new OptionsList();
                    s.setOptionLabel(parmDic.getOptName());
                    s.setOptionValue(parmDic.getOptCode());
                    vouTypeOtherList.add(s);
                }
            }else{
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
            this.changeFormProperty(formapply0001, "vouTypeOther", "optionArray", vouTypeOtherList);
            cmpdRateType = mfBusApply.getCmpFltRateShow();

            // 添加贷后检查模型数据
            List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
            this.changeFormProperty(formapply0001, "templateId", "optionArray", examList);

            //额度测算表单
            MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
            if("1".equals(mfSysKind.getIfCreditCalc())){
                formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.quota_calc, null, null, User.getRegNo(request));
                formQuotaCalc = formService.getFormData(formId);
                getObjValue(formQuotaCalc, mfCusCustomer);
                this.changeFormProperty(formQuotaCalc, "creditSum", "initValue", MathExtend.moneyStr(mfBusApply.getAppAmt()).replaceAll(",", ""));
                this.changeFormProperty(formQuotaCalc, "kindNo", "initValue", mfBusApply.getKindNo());
                model.addAttribute("formQuotaCalc", formQuotaCalc);
                model.addAttribute("ifQuotaCalc", mfSysKind.getIfCreditCalc());
            }
//            if(StringUtil.isNotEmpty(mfBusApply.getVouType())){
//                this.changeFormProperty(formapply0001, "vouType", "initValue", dicMap.get(mfBusApply.getVouType()));
//            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            throw e;
        }

        //        //查询共借人列表
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCoborrNo());
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
        ipage =  mfCusCustomerFeign.getCusListByCusNos(ipage);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr("tablecoborrNameList", "tableTag", (List) ipage.getResult(), ipage, true);
        model.addAttribute("tableHtml",tableHtml);

        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("mfBusApply", mfBusApply);
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cmpdRateType", cmpdRateType);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("query", "");
        model.addAttribute("credtitFlag", "1");
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));

        // ---------- begin 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        String SAVE_ONLY_4 = "1";
        model.addAttribute("SAVE_ONLY_4", SAVE_ONLY_4);
        // ---------- end 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        return "/component/app/confirmTuningReport";
    }
    /**
     * 方法描述： 批单打印
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-11 下午8:40:13
     */
    @RequestMapping(value = "/batchPrinting")
    public String batchPrinting(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String popFlag =request.getParameter("popFlag");
        String scNo = WKF_NODE.batch_printing.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.batch_printing.getNodeNo();// 功能节点编号
        if("3".equals(popFlag)){
            nodeNo = "refin_investigation";
        }
        String cmpdRateType = "";
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        FormData formapply0001;
        FormData formQuotaCalc;
        try {
            JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
            String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
            ajaxData = items;
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);

            String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.batch_printing, null, null, User.getRegNo(request));
            formapply0001 = formService.getFormData(formId);

            getObjValue(formapply0001, mfBusApply);
            String cusNo = mfBusApply.getCusNo();
            Map<String,String> cusInfoMap = new HashMap<String,String>();
            cusInfoMap.put("isXiaoWeiFlag","0");
            cusInfoMap.put("isJinYingFlag","0");
            cusInfoMap.put("isBeiJingFlag","0");
            cusInfoMap.put("isGaoKeJiFlag","0");
            cusInfoMap.put("legalUniversity","");
            cusInfoMap.put("guaranteeOuterFlag","1");
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
                            String projSize = mfCusCorpBaseInfo.getProjSize();
                            if("3".equals(projSize)||"4".equals(projSize)){
                                cusInfoMap.put("isXiaoWeiFlag","1");
                            }
                            String beginDate = mfCusCorpBaseInfo.getBeginDate();
                            if(StringUtil.isNotEmpty(beginDate)){
                                int betweenDays =DateUtil.getDaysBetween(beginDate,DateUtil.getDate());
                                if(betweenDays>365){
                                    cusInfoMap.put("isJinYingFlag","1");
                                }
                            }
                            String careaProvice = mfCusCorpBaseInfo.getCareaProvice();
                            if(StringUtil.isNotEmpty(careaProvice)&&careaProvice.startsWith("110")){
                                cusInfoMap.put("isBeiJingFlag","1");
                            }
                            String ifHighTech = mfCusCorpBaseInfo.getIfHighTech();
                            if(StringUtil.isNotEmpty(ifHighTech)&&("2".equals(ifHighTech)||"3".equals(ifHighTech))){
                                cusInfoMap.put("isGaoKeJiFlag","1");
                            }
                            String legalUniversity = mfCusCorpBaseInfo.getLegalUniversity();
                            if(StringUtil.isNotEmpty(legalUniversity )){
                                cusInfoMap.put("legalUniversity",legalUniversity);
                            }
                        }
                    }
                    MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
                    mfCusGuaranteeOuter.setCusNo(cusNo);
                    List<MfCusGuaranteeOuter> mfCusGuaranteeOuterList  = cusInterfaceFeign.getMfCusGuaranteeOuterList(mfCusGuaranteeOuter);
                    if(mfCusGuaranteeOuterList.size()>0){
                        cusInfoMap.put("guaranteeOuterFlag","0");
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
                    }
                }
            }

            if(StringUtil.isNotEmpty(appId)) {
                //获取保证人
                List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(appId);
                String assureName = "";
                if (mfAssureInfoList != null && mfAssureInfoList.size() > 0) {
                    for (int i = 0; i < mfAssureInfoList.size(); i++) {
                        assureName = assureName + mfAssureInfoList.get(i).getAssureName() + ",";
                    }
                }
                cusInfoMap.put("assureName", assureName);
                //获取合作银行
                String agenciesId = mfBusApply.getAgenciesId();
                if (StringUtil.isNotEmpty(agenciesId)) {
                    MfBusAgencies mfBusAgencies = new MfBusAgencies();
                    mfBusAgencies.setAgenciesId(agenciesId);
                    mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
                    if (mfBusAgencies != null) {
                        cusInfoMap.put("agenciesName", mfBusAgencies.getAgenciesName());
                        cusInfoMap.put("agenciesButtName", mfBusAgencies.getAgenciesButtName());
                        cusInfoMap.put("agenciesButtPhone", mfBusAgencies.getAgenciesButtPhone());
                    }
                }
            }
            getObjValue(formapply0001, cusInfoMap);
            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
            this.changeFormProperty(formapply0001, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "overRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "cmpdRate", "unit", rateUnit);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(mfBusApply.getCusNo());
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

            this.changeFormProperty(formapply0001, "manageOpNo", "initValue", mfCusCustomer.getCusMngNo());
            this.changeFormProperty(formapply0001, "manageOpName", "initValue", mfBusApply.getCusMngName());
            // 获取项目名称
            YmlConfig ymlConfig = (YmlConfig) SpringUtil.getBean(YmlConfig.class);
            String projectName = ymlConfig.getSysParams().get("sys.project.name");
            //处理其他担保方式
            List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
            MfBusAppKind mfBusAppKindVouType = new MfBusAppKind();
            mfBusAppKindVouType.setAppId(appId);
            mfBusAppKindVouType= mfBusAppKindFeign.getById(mfBusAppKindVouType);
            if(StringUtil.isEmpty(mfBusAppKindVouType.getVouType())){
                List<ParmDic> pdl = (List<ParmDic>)new CodeUtils().getCacheByKeyName("VOU_TYPE");
                for(ParmDic parmDic:pdl){
                    if(mfBusApply.getVouType().equals(parmDic.getOptCode()) || "1".equals(parmDic.getOptCode())){
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
            this.changeFormProperty(formapply0001, "vouTypeOther", "optionArray", vouTypeOtherList);
            cmpdRateType = mfBusApply.getCmpFltRateShow();

            // 添加贷后检查模型数据
            List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
            this.changeFormProperty(formapply0001, "templateId", "optionArray", examList);

            //额度测算表单
            MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
            if("1".equals(mfSysKind.getIfCreditCalc())){
                formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.quota_calc, null, null, User.getRegNo(request));
                formQuotaCalc = formService.getFormData(formId);
                getObjValue(formQuotaCalc, mfCusCustomer);
                this.changeFormProperty(formQuotaCalc, "creditSum", "initValue", MathExtend.moneyStr(mfBusApply.getAppAmt()).replaceAll(",", ""));
                this.changeFormProperty(formQuotaCalc, "kindNo", "initValue", mfBusApply.getKindNo());
                model.addAttribute("formQuotaCalc", formQuotaCalc);
                model.addAttribute("ifQuotaCalc", mfSysKind.getIfCreditCalc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            throw e;
        }

        //查询共借人列表
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCoborrNo());
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
        ipage =  mfCusCustomerFeign.getCusListByCusNos(ipage);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr("tablecoborrNameList", "tableTag", (List) ipage.getResult(), ipage, true);
        model.addAttribute("tableHtml",tableHtml);

        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("mfBusApply", mfBusApply);
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cmpdRateType", cmpdRateType);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("query", "");
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));

        // ---------- begin 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        String SAVE_ONLY_4 = "0";
        model.addAttribute("SAVE_ONLY_4", SAVE_ONLY_4);
        // ---------- end 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------

        return "/component/app/confirmTuningReport";
    }
    /**
     * 方法描述： 批单打印
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-11 下午8:40:13
     */
    @RequestMapping(value = "/letterIntent")
    public String letterIntent(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String popFlag =request.getParameter("popFlag");
        String scNo = WKF_NODE.letter_intent.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.letter_intent.getNodeNo();// 功能节点编号
        if("3".equals(popFlag)){
            nodeNo = "refin_investigation";
        }
        String cmpdRateType = "";
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        FormData formapply0001;
        FormData formQuotaCalc;
        try {
            JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
            String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
            ajaxData = items;
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);

            String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.letter_intent, null, null, User.getRegNo(request));
            formapply0001 = formService.getFormData(formId);

            getObjValue(formapply0001, mfBusApply);
            String cusNo = mfBusApply.getCusNo();
            Map<String,String> cusInfoMap = new HashMap<String,String>();
            cusInfoMap.put("isXiaoWeiFlag","0");
            cusInfoMap.put("isJinYingFlag","0");
            cusInfoMap.put("isBeiJingFlag","0");
            cusInfoMap.put("isGaoKeJiFlag","0");
            cusInfoMap.put("legalUniversity","");
            cusInfoMap.put("guaranteeOuterFlag","1");
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
                            String projSize = mfCusCorpBaseInfo.getProjSize();
                            if("3".equals(projSize)||"4".equals(projSize)){
                                cusInfoMap.put("isXiaoWeiFlag","1");
                            }
                            String beginDate = mfCusCorpBaseInfo.getBeginDate();
                            if(StringUtil.isNotEmpty(beginDate)){
                                int betweenDays =DateUtil.getDaysBetween(beginDate,DateUtil.getDate());
                                if(betweenDays>365){
                                    cusInfoMap.put("isJinYingFlag","1");
                                }
                            }
                            String careaProvice = mfCusCorpBaseInfo.getCareaProvice();
                            if(StringUtil.isNotEmpty(careaProvice)&&careaProvice.startsWith("110")){
                                cusInfoMap.put("isBeiJingFlag","1");
                            }
                            String ifHighTech = mfCusCorpBaseInfo.getIfHighTech();
                            if(StringUtil.isNotEmpty(ifHighTech)&&("2".equals(ifHighTech)||"3".equals(ifHighTech))){
                                cusInfoMap.put("isGaoKeJiFlag","1");
                            }
                            String legalUniversity = mfCusCorpBaseInfo.getLegalUniversity();
                            if(StringUtil.isNotEmpty(legalUniversity )){
                                cusInfoMap.put("legalUniversity",legalUniversity);
                            }
                        }
                    }
                    MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
                    mfCusGuaranteeOuter.setCusNo(cusNo);
                    List<MfCusGuaranteeOuter> mfCusGuaranteeOuterList  = cusInterfaceFeign.getMfCusGuaranteeOuterList(mfCusGuaranteeOuter);
                    if(mfCusGuaranteeOuterList.size()>0){
                        cusInfoMap.put("guaranteeOuterFlag","0");
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
                    }
                }
            }

            if(StringUtil.isNotEmpty(appId)) {
                //获取保证人
                List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(appId);
                String assureName = "";
                if (mfAssureInfoList != null && mfAssureInfoList.size() > 0) {
                    for (int i = 0; i < mfAssureInfoList.size(); i++) {
                        assureName = assureName + mfAssureInfoList.get(i).getAssureName() + ",";
                    }
                }
                cusInfoMap.put("assureName", assureName);
                //获取合作银行
                String agenciesId = mfBusApply.getAgenciesId();
                if (StringUtil.isNotEmpty(agenciesId)) {
                    MfBusAgencies mfBusAgencies = new MfBusAgencies();
                    mfBusAgencies.setAgenciesId(agenciesId);
                    mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
                    if (mfBusAgencies != null) {
                        cusInfoMap.put("agenciesName", mfBusAgencies.getAgenciesName());
                        cusInfoMap.put("agenciesButtName", mfBusAgencies.getAgenciesButtName());
                        cusInfoMap.put("agenciesButtPhone", mfBusAgencies.getAgenciesButtPhone());
                    }
                }
            }
            getObjValue(formapply0001, cusInfoMap);
            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
            this.changeFormProperty(formapply0001, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "overRate", "unit", rateUnit);
            this.changeFormProperty(formapply0001, "cmpdRate", "unit", rateUnit);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(mfBusApply.getCusNo());
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

            this.changeFormProperty(formapply0001, "manageOpNo", "initValue", mfCusCustomer.getCusMngNo());
            this.changeFormProperty(formapply0001, "manageOpName", "initValue", mfBusApply.getCusMngName());
            // 获取项目名称
            YmlConfig ymlConfig = (YmlConfig) SpringUtil.getBean(YmlConfig.class);
            String projectName = ymlConfig.getSysParams().get("sys.project.name");
            //处理其他担保方式
            List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
            MfBusAppKind mfBusAppKindVouType = new MfBusAppKind();
            mfBusAppKindVouType.setAppId(appId);
            mfBusAppKindVouType= mfBusAppKindFeign.getById(mfBusAppKindVouType);
            if(StringUtil.isEmpty(mfBusAppKindVouType.getVouType())){
                List<ParmDic> pdl = (List<ParmDic>)new CodeUtils().getCacheByKeyName("VOU_TYPE");
                for(ParmDic parmDic:pdl){
                    if(mfBusApply.getVouType().equals(parmDic.getOptCode()) || "1".equals(parmDic.getOptCode())){
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
            this.changeFormProperty(formapply0001, "vouTypeOther", "optionArray", vouTypeOtherList);
            cmpdRateType = mfBusApply.getCmpFltRateShow();

            // 添加贷后检查模型数据
            List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
            this.changeFormProperty(formapply0001, "templateId", "optionArray", examList);

            //额度测算表单
            MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
            if("1".equals(mfSysKind.getIfCreditCalc())){
                formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.quota_calc, null, null, User.getRegNo(request));
                formQuotaCalc = formService.getFormData(formId);
                getObjValue(formQuotaCalc, mfCusCustomer);
                this.changeFormProperty(formQuotaCalc, "creditSum", "initValue", MathExtend.moneyStr(mfBusApply.getAppAmt()).replaceAll(",", ""));
                this.changeFormProperty(formQuotaCalc, "kindNo", "initValue", mfBusApply.getKindNo());
                model.addAttribute("formQuotaCalc", formQuotaCalc);
                model.addAttribute("ifQuotaCalc", mfSysKind.getIfCreditCalc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            throw e;
        }

        //查询共借人列表
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCoborrNo());
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
        ipage =  mfCusCustomerFeign.getCusListByCusNos(ipage);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr("tablecoborrNameList", "tableTag", (List) ipage.getResult(), ipage, true);
        model.addAttribute("tableHtml",tableHtml);

        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("mfBusApply", mfBusApply);
        model.addAttribute("busModel", mfBusApply.getBusModel());
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cmpdRateType", cmpdRateType);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("query", "");
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));

        // ---------- begin 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        String SAVE_ONLY_4 = "0";
        model.addAttribute("SAVE_ONLY_4", SAVE_ONLY_4);
        // ---------- end 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------

        return "/component/app/confirmTuningReport";
    }



    /**
     * 方法描述：移动端 调用资方 下一步提交到业务审批
     *
     * @return
     * @throws Exception
     *             String
     * @author zhang_dlei
     * @date 2017-5-11 下午8:40:13
     */
    @RequestMapping(value = "/confirmTuningReportForApp")
    public String confirmTuningReportForApp(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        FormData formcallService;
        String jsonStr = "";
        String cmpdRateType = "";
        ActionContext.initialize(request, response);
        String scNo = WKF_NODE.call_service.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.call_service.getNodeNo();// 功能节点编号
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
            String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
            ajaxData = items;
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
            // 共同借款人
            jsonStr = cusInterfaceFeign.getCobBorrower(mfBusApply.getCusNo()).toString();

            String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.call_service, null, null, User.getRegNo(request));
            formcallService = formService.getFormData(formId);

            getObjValue(formcallService, mfBusApply);
            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
            this.changeFormProperty(formcallService, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formcallService, "overRate", "unit", rateUnit);
            this.changeFormProperty(formcallService, "cmpdRate", "unit", rateUnit);
            cmpdRateType = mfBusApply.getCmpFltRateShow();

            // 添加贷后检查模型数据
            List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(appId, "apply");
            this.changeFormProperty(formcallService, "templateId", "optionArray", examList);

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            throw e;
        }
        model.addAttribute("formcallService", formcallService);
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cmpdRateType", cmpdRateType);
        model.addAttribute("jsonStr", jsonStr);
        model.addAttribute("query", "");
        return "/component/app/confirmTuningReportForApp";
    }

    /**
     * 方法描述： 保存尽调信息
     *
     * @return
     * @throws Exception
     *             String
     * @author 谢静霞
     * @date 2017-6-5 下午4:09:52
     */
    @RequestMapping(value = "/updateTuningReportAjax")
    @ResponseBody
    public Map<String, Object> updateTuningReportAjax(String ajaxData, String ajaxDataList, String nextUser, String temporaryStorage,String nodeNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String query = "";
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId((String) map.get("appId"));

            //判断当前节点是否可进行审批
            MfBusApply mfBusApply1 = mfBusApplyFeign.getById(mfBusApply);
            boolean uploadFalg = true;
            if(StringUtil.isEmpty(mfBusApply1.getIsRelCredit()) || !"1".equals(mfBusApply1.getIsRelCredit())){
                uploadFalg = mfBusApplyFeign.getIfUploadCredit(mfBusApply1.getCusNo(),mfBusApply1.getAppId(),WKF_NODE.resp_investigation.getNodeNo());
            }

            TaskImpl taskApprove = wkfInterfaceFeign.getTask(mfBusApply1.getWkfAppId(), "");
            if(nodeNo !=null && !nodeNo.equals(taskApprove.getActivityName())){
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
                return dataMap;
            }
            //处理基础利率类型为空问题  --- LPR利率不可写死处理
            mfBusApply.setBaseRateType(mfBusApply1.getBaseRateType());
            // mfBusApply = mfBusApplyFeign.getById(mfBusApply);

            // formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(),
            // WKF_NODE.resp_investigation, null, null);
            FormData formapply0001 = formService.getFormData((String) map.get("formId"));
            FormData formapply0003;
            getFormValue(formapply0001, map);
            setObjValue(formapply0001, mfBusApply);

            if("1".equals(mfBusApply.getIfFirstLoan())&&!"1".equals(temporaryStorage)&&!uploadFalg){
                dataMap.put("flag", "error");
                dataMap.put("uploadFalg", uploadFalg);
                dataMap.put("msg", "必须上传企业的征信报告，征信报告中信息概要部分，需为“该信用主体无信贷交易记录”！");
                return dataMap;
            }
            map.put("feeList", ajaxDataList);
            map.put("mfBusApply", mfBusApply);
            mfBusApplyFeign.updateMap(map);

            if (StringUtil.isNotEmpty(ajaxDataList)) {
                map.put("ajaxDataList", ajaxDataList);
            }
            new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusApply);
            map.put("mfBusApply", mfBusApply);
            map.put("nextUser", nextUser);
            map.put("opNo", User.getRegNo(request));
            map.put("opName", User.getRegName(request));
            map.put("brNo", User.getOrgNo(request));
            map.put("brName", User.getOrgName(request));
            map.put("temporaryStorage", temporaryStorage);
            Map<String, Object> resultDataMap = mfLoanApplyFeign.doConfirmTuningReport(map);

            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
            String appSts = mfBusApply.getAppSts();

            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            // 业务申请信息处理
            if (!(mfBusApply.getAppSts().equals(BizPubParm.APP_STS_UN_SUBMIT))) {
                // 一旦提交审批，则设置为query，解析出的申请表单不可单字段编辑
                query = "query";
            }
            if (mfBusApply.getBusModel().equals(BizPubParm.BUS_MODEL_5)) {// 应收账款融资(保理业务)
                formapply0003 = formService.getFormData("apply0006bl");
            } else {
                formapply0003 = formService.getFormData("apply0006");
            }
            getFormValue(formapply0003);
            getObjValue(formapply0003, mfBusApply);
            // 处理期限的展示单位。
            Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
            String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
            this.changeFormProperty(formapply0003, "term", "unit", termUnit);
            String htmlStr = jsonFormUtil.getJsonStr(formapply0003, "propertySeeTag", "");
            //面审信息展示
            FormData formappreport0002 = formService.getFormData("appReportBase_detail");
            MfTuningReport mfTuningReport = new MfTuningReport();
            mfTuningReport.setAppId(mfBusApply.getAppId());
            mfTuningReport = mfTuningReportFeign.getById(mfTuningReport);
            getFormValue(formappreport0002);
            getObjValue(formappreport0002, mfTuningReport);
            String appreportDetail = jsonFormUtil.getJsonStr(formappreport0002, "propertySeeTag", query);
            // 处理概要信息
            ArrayList<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
            Map<String, String> parmMap = new HashMap<String, String>();
            parmMap = new HashMap<String, String>();
            parmMap.put("name", "appAmt");
            parmMap.put("value", String.valueOf(mfBusApply.getAppAmt()));
            dataList.add(parmMap);
            parmMap = new HashMap<String, String>();
            parmMap.put("name", "term");
            parmMap.put("value", String.valueOf(mfBusApply.getTerm()));
            dataList.add(parmMap);
            parmMap = new HashMap<String, String>();
            parmMap.put("name", "fincRate");
            parmMap.put("value", String.valueOf(mfBusApply.getFincRate()));
            dataList.add(parmMap);
            JSONArray array = JSONArray.fromObject(dataList);

            dataMap.putAll(resultDataMap);
            dataMap.put("appSts", appSts);
            dataMap.put("appId", mfBusApply.getAppId());
            dataMap.put("wkfId", mfBusApply.getWkfAppId());
            dataMap.put("applyDetail", htmlStr);
            dataMap.put("appreportDetail", appreportDetail);
            dataMap.put("applyInfo", array.toString());
            dataMap.put("coborrNo", mfBusApply.getCoborrNo());
            dataMap.put("uploadFalg", uploadFalg);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
        }
        return dataMap;
    }

    /**
     * 方法描述：移动端 调用资方
     *
     * @return
     * @throws Exception
     *             String
     * @author 张冬磊
     * @date 2017-6-5 下午4:09:52
     */
    @RequestMapping(value = "/updateTuningReportForAppAjax")
    @ResponseBody
    public Map<String, Object> updateTuningReportForAppAjax(String ajaxData, String channelType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String query = "";
        FormData formapply0003;
        try {
            Map<String, Object> map = getMapByJson(ajaxData);

            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId((String) map.get("appId"));
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);

            String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.call_service, null, null, User.getRegNo(request));
            FormData formcallService = formService.getFormData(formId);

            getFormValue(formcallService, map);
            setObjValue(formcallService, mfBusApply);

            Map<String, Object> resultDataMap = mfLoanApplyFeign.doConfirmTuningReportForApp(mfBusApply, map,
                    channelType);

            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
            String appSts = mfBusApply.getAppSts();

            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            // 业务申请信息处理
            if (!(mfBusApply.getAppSts().equals(BizPubParm.APP_STS_UN_SUBMIT))) {
                // 一旦提交审批，则设置为query，解析出的申请表单不可单字段编辑
                query = "query";
            }
            if (mfBusApply.getBusModel().equals(BizPubParm.BUS_MODEL_5)) {// 应收账款融资(保理业务)
                formapply0003 = formService.getFormData("apply0006bl");
            } else {
                formapply0003 = formService.getFormData("apply0006");
            }
            getFormValue(formapply0003);
            getObjValue(formapply0003, mfBusApply);
            // 处理期限的展示单位。
            Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
            String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
            this.changeFormProperty(formapply0003, "term", "unit", termUnit);
            String htmlStr = jsonFormUtil.getJsonStr(formapply0003, "propertySeeTag", "");
            // 处理概要信息
            ArrayList<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
            Map<String, String> parmMap = new HashMap<String, String>();

            parmMap.put("name", "appAmt");
            parmMap.put("value", String.valueOf(mfBusApply.getAppAmt()));
            dataList.add(parmMap);

            parmMap = new HashMap<String, String>();
            parmMap.put("name", "term");
            parmMap.put("value", String.valueOf(mfBusApply.getTerm()));
            dataList.add(parmMap);

            parmMap = new HashMap<String, String>();
            parmMap.put("name", "fincRate");
            parmMap.put("value", String.valueOf(mfBusApply.getFincRate()));
            dataList.add(parmMap);
            JSONArray array = JSONArray.fromObject(dataList);

            dataMap.putAll(resultDataMap);
            dataMap.put("appSts", appSts);
            dataMap.put("appId", mfBusApply.getAppId());
            dataMap.put("wkfId", mfBusApply.getWkfAppId());
            dataMap.put("applyDetail", htmlStr);
            dataMap.put("applyInfo", array.toString());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 提交下一步成功
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-26 下午2:28:28
     */
    @RequestMapping(value = "/notarizationAjax")
    @ResponseBody
    public Map<String, Object> notarizationAjax(String ajaxData, String appId) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ActionContext.initialize(request, response);
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            // 提交流程
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfLoanApplyFeign.doCommit(mfBusApply);

            dataMap.put("msg", MessageEnum.SUCCEED_COMMIT.getMessage());
            dataMap.put("flag", "success");
            dataMap.put("appId", mfBusApply.getAppId());
            dataMap.put("node", "join_zh");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 资金机构确认表单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-18 下午6:01:02
     */

    @RequestMapping(value = "/capitalConfirmForm")
    public String capitalConfirmForm(Model model, String ajaxData, String pactId, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact.setAppId(appId);
        mfBusPact = mfLoanApplyFeign.processDataForPact(mfBusPact);
        FormData formfincapp0001 = formService.getFormData("zhpactshow0001");
        getObjValue(formfincapp0001, mfBusPact);
        String wkfAppId = mfBusPact.getWkfAppId();
        model.addAttribute("formfincapp0001", formfincapp0001);
        model.addAttribute("wkfAppId", wkfAppId);
        model.addAttribute("query", "");
        return "/component/pact/capitalConfirmForm";
    }

    /**
     * 方法描述： 资金机构确认直接跳过流程的节点，没有业务处理
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-18 下午7:34:13
     */
    @RequestMapping(value = "/updateForCapitalAjax")
    @ResponseBody
    public Map<String, Object> updateForCapitalAjax(String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            Result result = mfLoanApplyFeign.updateRegConfirmAnddoCommit(mfBusApply);
            dataMap.put("flag", "success");
            dataMap.put("msg", result.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 面签更新合同信息
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-31 下午6:58:10
     */
    @RequestMapping(value = "/updatePactInfoAjax")
    @ResponseBody
    public Map<String, Object> updatePactInfoAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            FormData formpact0008 = formService.getFormData(formId);
            getFormValue(formpact0008, getMapByJson(ajaxData));
            setObjValue(formpact0008, mfBusPact);
            Result result = mfLoanApplyFeign.updatePactAndCommit(mfBusPact);
            MfBusPact tmpMfBusPact = pactInterfaceFeign.getByAppId(mfBusPact.getAppId());
            String pactId = tmpMfBusPact.getPactId();
            dataMap.put("appId", mfBusPact.getAppId());
            dataMap.put("pactId", pactId);
            dataMap.put("flag", "success");
            dataMap.put("msg", result.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 根据产品种类和客户类型获取客户表单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-6-6 下午5:02:53
     */
    @RequestMapping(value = "/getCusFormByKindNo")
    public Map<String, Object> getCusFormByKindNo(Model model, String ajaxData, String kindNo, String formStage, String cusType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            String htmlStr = mfBusFormConfigFeign.getInputCusEntryForm(kindNo, formStage, cusType);
            if (htmlStr != null && !"".equals(htmlStr)) {
                dataMap.put("flag", "success");
                dataMap.put("html", htmlStr);
            } else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            e.printStackTrace();
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 单独提交流程 默认同意
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-6-7 下午10:01:08
     */
    @RequestMapping(value = "/commitProcessAjax")
    @ResponseBody
    public Map<String, Object> commitProcessAjax(String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);

            // Result result = mfLoanApplyFeign.doCommit(mfBusApply); // 提交流程
            Result result = wkfBusInterfaceFeign.doCommitNextStepWithUser(mfBusApply.getAppId(), mfBusApply.getWkfAppId(), User.getRegNo(request));// 提交流程
            if (result.isSuccess()) {
                mfBusApply = mfBusApplyFeign.getById(mfBusApply);
                dataMap.put("flag", "success");
                dataMap.put("msg", result.getMsg());
                dataMap.put("appId", mfBusApply.getAppId());
                dataMap.put("appSts", mfBusApply.getAppSts());
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
     * 方法描述： 征信表单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-6-8 下午4:58:35
     */
    @RequestMapping(value = "/inputCreditInquiry")
    public String inputCreditInquiry(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        FormData formapply0001;
        ActionContext.initialize(request, response);
        String scNo = WKF_NODE.credit_investigation.getScenceTypeDoc();// 要件场景
        String nodeNo = WKF_NODE.credit_investigation.getNodeNo();// 功能节点编号
        String nodeName = "";
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            Task task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
            nodeName = task.getDescription();
            MfBusAppKind mfBusAppKind = new MfBusAppKind();// 单字段编辑需要根据利率类型展示处理利率展示类型,
            mfBusAppKind.setAppId(mfBusApply.getAppId());
            mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
            mfBusApply.setFincRate(MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusApply.getFincRate(),
                    Integer.parseInt(mfBusAppKind.getYearDays()),
                    Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
            mfBusApply.setOverRate(MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusApply.getOverRate(),
                    Integer.parseInt(mfBusAppKind.getYearDays()),
                    Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
            String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.credit_investigation, null, null, User.getRegNo(request));
            formapply0001 = formService.getFormData(formId);

            getObjValue(formapply0001, mfBusApply);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }
        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("nodeName", nodeName);
        model.addAttribute("query", "");
        return "/component/app/inputCreditInquiry";
    }

    /**
     * 方法描述： 更新申请信息并且提交流程
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-6-8 下午5:11:23
     */
    @RequestMapping(value = "/updateCreditInquiry")
    public Map<String, Object> updateCreditInquiry(Model model, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            FormData formapply0001 = formService.getFormData(formId);
            getFormValue(formapply0001, map);
            setObjValue(formapply0001, mfBusApply);
            Result result = mfLoanApplyFeign.updateAndCommit(mfBusApply, "");
            if (result != null) {
                dataMap.put("appId", mfBusApply.getAppId());
                dataMap.put("flag", "success");
                dataMap.put("msg", result.getMsg());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 异步获取进件公共表单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-31 下午2:52:50
     */
    @RequestMapping(value = "/inputBusCommonFormAjax")
    @ResponseBody
    public Map<String, Object> inputBusCommonFormAjax(String ajaxData, String kindNo, String formStage, String cusType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 取第一个客户类型
        MfSysKind mfSysKind = new MfSysKind();
        try {
            mfSysKind.setUseFlag("1");
            List<MfSysKind> kindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
            // 默认取第一个产品表单
            if (kindNo == null) {
                kindNo = kindList.get(0).getKindNo();
            }
            if (formStage == null) {
                formStage = "apply";
            }
            CodeUtils code = new CodeUtils();
            List<ParmDic> parmList = (List<ParmDic>) code.getCacheByKeyName("CUS_TYPE");
            ParmDic parmDic = parmList.get(0);
            // 获取默认的客户类型
            if (cusType == null) {
                cusType = parmDic.getOptCode();
            }
            List<String> list = mfBusFormConfigFeign.getInputBusCommonForm(kindNo, formStage, cusType);
            dataMap.put("list", list);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
        }
        return dataMap;
    }

    /**
     * 方法描述： 更新
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-5-11 下午2:16:44
     */
    @RequestMapping(value = "/updateApplyCommonInfo")
    public Map<String, Object> updateApplyCommonInfo(Model model, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        dataMap = getMapByJson(ajaxData);
        String formId = (String) dataMap.get("formId");
        FormData formapply0001 = formService.getFormData(formId);
        getFormValue(formapply0001, getMapByJson(ajaxData));
        MfBusApply mfBusApply = new MfBusApply();
        setObjValue(formapply0001, mfBusApply);
        try {
            MfBusAppKind mfBusAppKind = new MfBusAppKind();
            mfBusAppKind.setAppId(mfBusApply.getAppId());
            mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
            mfBusApply = mfLoanApplyFeign.disProcessDataForApply(mfBusApply);
            mfBusApply.setKindName(null);
            mfBusApplyFeign.update(mfBusApply);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            dataMap.put("appId", mfBusApply.getAppId());
            dataMap.put("wkfId", mfBusApply.getWkfAppId());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_SERVER);
//			logger.error("更新申请信息失败", e);
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 根据产品编号获取费用列表
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-6-12 下午4:57:32
     */
    @RequestMapping(value = "/getFeeListByKindNoAjax")
    @ResponseBody
    public Map<String, Object> getFeeListByKindNoAjax(String ajaxData, String kindNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JsonTableUtil jsonTableUtil = new JsonTableUtil();
        List<MfSysFeeItem> mfBusSysFeeList = calcInterfaceFeign.getFeeItemList(kindNo);
        String tableHtml = jsonTableUtil.getJsonStr("tableapplyFee0001", "tableTag", mfBusSysFeeList, null, true);
        if (mfBusSysFeeList.size() > 0) {
            dataMap.put("flag", "1");
            dataMap.put("html", tableHtml);
        } else {
            dataMap.put("flag", "0");
        }
        return dataMap;
    }

    /**
     * 方法描述： 获取尽调报告应该完成项
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-6-13 下午3:26:36
     */
    @RequestMapping(value = "/getDueDiligenceList")
    public String getDueDiligenceList(Model model, String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        // 查询客户的资料是否都完整
        List<Map<String, String>> dataList = mfLoanApplyFeign.getDataComplete(mfBusApply);
        String scNo = BizPubParm.SCENCE_TYPE_DOC_INVESTIGATION;
        model.addAttribute("scNo", scNo);
        model.addAttribute("dataList", dataList);
        model.addAttribute("query", "");
        return "/component/app/getDueDiligenceList";
    }

    /**
     *@Description: 获取尽职报告是否填写完成
     *@Author: lyb
     *@date: 2018/9/20
     */
    @RequestMapping(value = "/getDueDiligenceReport")
    @ResponseBody
    public Map<String, Object> getDueDiligenceReport(String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
        mfTemplateBizConfig.setTemBizNo(appId);
        mfTemplateBizConfig.setNodeName("resp_investigation");
        List<MfTemplateBizConfig> mfTemplateReportList = mfTemplateBizConfigFeign.getDueDiligenceReport(mfTemplateBizConfig);
        Boolean rateFlag = true;
        if (mfTemplateBizConfig != null && mfTemplateReportList.size() > 0) {
            MfTemplateBizConfig mfTemplateBizConfig1 = new MfTemplateBizConfig();
            for (int i = 0; i < mfTemplateReportList.size(); i++) {
                mfTemplateBizConfig1 = mfTemplateReportList.get(i);
                if (StringUtil.isEmpty(mfTemplateBizConfig1.getDocFileName()) || StringUtil.isEmpty(mfTemplateBizConfig1.getDocFilePath())) {
                    rateFlag = false;
                }else {
                    rateFlag = true;
                    break;
                }
            }
        }
        dataMap.put("rateFlag", rateFlag);
        return dataMap;
    }

    /**
     * 方法描述： 提交的时候校验是否全部录入了信息
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-6-15 上午11:43:17
     */
    @RequestMapping(value = "/checkIfComplete")
    public Map<String, Object> checkIfComplete(Model model, String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        String str = "";
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            str = mfLoanApplyFeign.checkIfComplete(mfBusApply);
            if ("1111".equals(str)) {
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.FIRST_OPERATION.getMessage("资料完善"));
            } else if ("0000".equals(str)) {
                Result result = mfLoanApplyFeign.doCommit(mfBusApply);
                dataMap.put("ref", "1");
                dataMap.put("flag", "success");
                dataMap.put("msg", result.getMsg());
                dataMap.put("appId", appId);
            }else {
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 北京小贷单独出 供资金机构确认的表单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-6-16 上午10:12:10
     */
    @RequestMapping(value = "/confirmedLoan")
    public String confirmedLoan(Model model, String ajaxData, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String scNo = BizPubParm.SCENCE_TYPE_DOC_LOAN_CONFIRM;
        String nodeNo = WKF_NODE.loan_confirm.getNodeNo();
        Map<String, Object> dataMap = new HashMap<String, Object>();

        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);

        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(mfBusFincApp.getPactId());
        mfBusPact = pactInterfaceFeign.getById(mfBusPact);

        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(mfBusFincApp.getAppId());
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

        String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.loan_confirm, null, null, User.getRegNo(request));
        FormData formfincapp0004 = formService.getFormData(formId);

        String appId = mfBusPact.getAppId();
        String cusNo = mfBusPact.getCusNo();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setId(mfBusPact.getCollectAccId());
        mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);

        dataMap.put("fincId", fincId);
        dataMap.put("cusName", mfBusPact.getCusName());
        dataMap.put("kindName", mfBusPact.getKindName());
        dataMap.put("repayType", mfBusPact.getRepayType());
        dataMap.put("accountNo", mfCusBankAccManage.getAccountNo());
        dataMap.put("incomBank", mfCusBankAccManage.getBank());

        dataMap.put("pactAmt", mfBusPact.getPactAmt());
        dataMap.put("termMonth", mfBusFincApp.getTermMonth());
        dataMap.put("fincRate", MathExtend.showRateMethod(mfBusFincApp.getRateType(),
                String.valueOf(mfBusFincApp.getFincRate()), mfBusAppKind.getRateDecimalDigits()));
        dataMap.put("signDate", mfBusPact.getSignDate());

        getObjValue(formfincapp0004, dataMap);

        model.addAttribute("formfincapp0004", formfincapp0004);
        model.addAttribute("scNo", scNo);
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("appId", appId);
        model.addAttribute("query", "");
        return "/component/app/confirmedLoan";
    }

    /**
     * 方法描述： 进件处切换产品时切换表单
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-8-24 下午4:25:28
     */
    @RequestMapping(value = "/getBusForm")
    public Map<String, Object> getBusForm(Model model, String ajaxData, String kindNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
            mfBusApply.setAppId(WaterIdUtil.getWaterId("app"));
            mfBusApply.setAppTime(DateUtil.getDateTime());
            mfBusApply.setAppTimeShow(DateUtil.getDate());
            mfBusApply.setFincRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getFincRate(),
                    Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
            mfBusApply.setOverRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getOverRate(),
                    Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
            mfBusApply.setBusModel(mfSysKind.getBusModel());
            mfBusApply.setKindNo(mfSysKind.getKindNo());
            mfBusApply.setKindName(mfSysKind.getKindName());
            mfBusApply.setCusMngName(User.getRegName(request));
            mfBusApply.setCusMngNo(User.getRegNo(request));
            mfBusApply.setManageOpNo1(User.getRegNo(request));
            mfBusApply.setManageOpName1(User.getRegName(request));
            mfBusApply.setTermType(mfSysKind.getTermType());

            MfKindForm mfKindForm = prdctInterfaceFeign.getInitKindForm(kindNo, BizPubParm.CUS_APPLY_NODE_NO,
                    BUDGET_DEFFLAG_TYPE.DEFFLAG1.getCode());
            String formId = mfKindForm.getAddModel();
            FormData formbusapply = formService.getFormData(formId);
            if ((mfSysKind.getMinAmt() != null) && (mfSysKind.getMaxAmt() != null)) {
                double minAmtD = mfSysKind.getMinAmt();
                double maxAmtD = mfSysKind.getMaxAmt();
                String minAmt = MathExtend.moneyStr(minAmtD);// 融资金额下限
                String maxAmt = MathExtend.moneyStr(maxAmtD);// 融资金额上限
                String amt = minAmt + "-" + maxAmt + "元";
                this.changeFormProperty(formbusapply, "appAmt", "alt", amt);
                mfBusApply.setMaxAmt(maxAmt);
                mfBusApply.setMinAmt(minAmt);
            }
            // 处理期限
            if (null != mfSysKind.getTermType()) {
                String termType = mfSysKind.getTermType();// 合同期限 1月 2日
                int minTerm = mfSysKind.getMinTerm();// 合同期限下限
                int maxTerm = mfSysKind.getMaxTerm();// 合同期限上限
                String term = "";
                if ("1".equals(termType)) {
                    term = minTerm + "个月-" + maxTerm + "个月";
                } else if ("2".equals(termType)) {
                    term = minTerm + "日-" + maxTerm + "日";
                }else {
                }
                mfBusApply.setMaxTerm(maxTerm);
                mfBusApply.setMinTerm(minTerm);
                mfBusApply.setTermType(termType);
                this.changeFormProperty(formbusapply, "term", "alt", term);
            }
            getObjValue(formbusapply, mfBusApply);
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfSysKind.getRateType()).getRemark();
            this.changeFormProperty(formbusapply, "fincRate", "unit", rateUnit);

            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String htmlStr = jsonFormUtil.getJsonStr(formbusapply, "bootstarpTag", "");
            dataMap.put("flag", "success");
            dataMap.put("htmlStr", htmlStr);
            dataMap.put("formType", "basebusapply");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 校验风险拦截项
     *
     * @return
     * @throws Exception
     *             String
     * @author YuShuai
     * @date 2017-10-18 下午3:25:23
     */
    @RequestMapping(value = "/getRiskItemResultAjax")
    @ResponseBody
    public Map<String, Object> getRiskItemResultAjax(String ajaxData, String appId) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        ActionContext.initialize(request, response);
        try {
            dataMap = mfLoanApplyFeign.getRiskItemResultAjax(appId, User.getRegNo(request));
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            throw e;
        }
        return dataMap;
    }


}
