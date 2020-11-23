package app.component.pact.controller;

import app.base.ServiceException;
import app.base.User;
import app.base.cacheinterface.BusiCacheInterface;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.entity.MfBusApplyAssureInfo;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.biz.entity.BizInfoChange;
import app.component.bizinterface.BizInterfaceFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.calc.charge.feign.MfBusChargeFeeFeign;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.feign.MfRepayHistoryFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.calc.penalty.entity.MfBusAppPenaltyMain;
import app.component.calc.penalty.feign.MfBusAppPenaltyMainFeign;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.carmodel.entity.MfCarBrand;
import app.component.collateral.entity.InsInfo;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.collateral.feign.*;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.compensatory.entity.MfBusCompensatoryApprove;
import app.component.compensatory.feign.MfBusCompensatoryApplyFeign;
import app.component.compensatory.feign.MfBusCompensatoryApproveFeign;
import app.component.cus.entity.*;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.examine.entity.MfExamineHis;
import app.component.examine.feign.MfExamineHisFeign;
import app.component.finance.bankacc.entity.CwCusBankAccManage;
import app.component.finance.bankacc.feign.CwCusBankAccManageFeign;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.model.feign.MfTemplateBizConfigFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.advanceloan.entity.MfBusAdvanceLoan;
import app.component.pact.advanceloan.feign.MfBusAdvanceLoanFeign;
import app.component.pact.entity.*;
import app.component.pact.extension.entity.MfBusExtensionApply;
import app.component.pact.extension.feign.MfBusExtensionApplyFeign;
import app.component.pact.feign.*;
import app.component.pact.receaccount.entity.MfBusReceBal;
import app.component.pact.receaccount.feign.MfBusReceBalFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.pledge.feign.MfHighGuaranteeContractFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.rec.entity.RecallBase;
import app.component.recinterface.RecInterfaceFeign;
import app.component.thirdservice.esign.feign.EsignInterfaceFeign;
import app.component.vouafter.entity.MfVouAfterTrack;
import app.component.vouafter.feign.MfVouAfterTrackFeign;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.component.wkf.entity.WkfApprovalUser;
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
import cn.mftcc.util.EntityUtil;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.report.ExpExclUtil;
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
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * Title: MfBusPactAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Fri May 27 14:34:25 CST 2016
 **/
@Controller
@RequestMapping(value = "/mfBusPact")
public class MfBusPactController extends BaseFormBean {
    private static Logger logger = LoggerFactory.getLogger(MfBusPactController.class);
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    // 注入MfBusPactBo
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private AppInterfaceFeign appInterfaceFeign;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;
    @Autowired
    private MfBusFincAppChildFeign mfBusFincAppChildFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private BizInterfaceFeign bizInterfaceFeign;
    @Autowired
    private RecInterfaceFeign recInterfaceFeign;
    @Autowired
    private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private BusViewInterfaceFeign busViewInterfaceFeign;
    @Autowired
    private CollateralInterfaceFeign collateralInterfaceFeign;
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;
    @Autowired
    private AssureInterfaceFeign assureInterfaceFeign;

    @Autowired
    private MfBusPactWkfFeign mfBusPactWkfFeign;
    @Autowired
    private MfRepayHistoryFeign mfRepayHistoryFeign;
    @Autowired
    private MfExamineHisFeign mfExamineHisFeign;
    @Autowired
    private MfBusCollateralDetailRelFeign MfBusCollateralDetailRelFeign;
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
    @Autowired
    private MfBusAppPenaltyMainFeign mfBusAppPenaltyMainFeign;
    @Autowired
    private MfVouAfterTrackFeign mfVouAfterTrackFeign;
    @Autowired
    private MfBusReceBalFeign mfBusReceBalFeign;
    @Autowired
    private MfCusCreditContractFeign mfCusCreditContractFeign;
    @Autowired
    private MfBusAgenciesFeign mfBusAgenciesFeign;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;

    @Autowired
    private MfBusCompensatoryApplyFeign mfBusCompensatoryApplyFeign;

    @Autowired
    private MfBusCompensatoryApproveFeign mfBusCompensatoryApproveFeign;
    @Autowired
    private MfHighGuaranteeContractFeign mfHighGuaranteeContractFeign;
    @Autowired
    private BusiCacheInterface busiCacheInterface;

    @Autowired
    private MfBusAppKindFeign mfBusAppKindFeign;
    @Autowired
    private EsignInterfaceFeign esignInterfaceFeign;

    @Autowired
    private MfBusExtensionApplyFeign mfBusExtensionApplyFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private MfBusAppFeeFeign mfBusAppFeeFeign;
    @Autowired
    private MfBusChargeFeeFeign mfBusChargeFeeFeign;

    @Autowired
    private MfBusAdvanceLoanFeign mfBusAdvanceLoanFeign;
    @Autowired
    private MfBusCollateralRelFeign mfBusCollateralRelFeign;
    @Autowired
    private MfTemplateBizConfigFeign mfTemplateBizConfigFeign;
    @Autowired
    private MfBusPactExtendFeign mfBusPactExtendFeign;
    @Autowired
    private PledgeBaseInfoFeign pledgeBaseInfoFeign;
    @Autowired
    private PledgeBaseInfoBillFeign pledgeBaseInfoBillFeign;
    @Autowired
    private InsInfoFeign insInfoFeign;

    /**
     * 历史完结业务请求页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFinshPactListPage")
    public String getFinshPactListPage(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        return "/component/pact/MfBusPactFinish_List";
    }

    /**
     * 历史完结业务请求页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOverPactListPage")
    public String getOverPactListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/postproject/MfBusPactOver_List";
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findOverListByPageAjax")
    @ResponseBody
    public Map<String, Object> findOverListByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                      String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.getBusPactOverList(ipage);
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
     * 历史完结业务请求页面 可传多用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFinshPactTrhListPage")
    public String getFinshPactTrhListPage(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        return "/component/pact/MfBusPactFinish_trhList";
    }

    /**
     * 签约合同列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("PACT_STS");
        model.addAttribute("pactStsJsonArray", pactStsJsonArray);
        JSONArray kindTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("KIND_NO");
        model.addAttribute("kindTypeJsonArray", kindTypeJsonArray);
        // 办理阶段
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        JSONArray jsonArray = JSONArray.fromObject(
                "[{\"optCode\":\"待提款\",\"optName\":\"待提款\"},{\"optCode\":\"待放款\",\"optName\":\"待放款\"},{\"optCode\":\"放款复核\",\"optName\":\"放款复核\"}]");
        flowNodeJsonArray.addAll(jsonArray);
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_SignList";
    }

    /**
     * 贷后合同列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getLoanAfterPactListPage")
    public String getLoanAfterPactListPage(Model model, String ajaxData, String queryType) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_STS");
        model.addAttribute("fincStsJsonArray", pactStsJsonArray);
        // 办理阶段
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("queryType", queryType);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_List";
    }

    /***
     * 签约合同列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findSignByPageAjax")
    @ResponseBody
    public Map<String, Object> findSignByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                  String ajaxData, String cusNo, String pactSts) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setCusNo(cusNo);
            mfBusPact.setPactSts(pactSts);
            // this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.findSignPactByPage(ipage);
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
     * 贷后合同列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setCusNo(cusNo);
            // mfBusFincApp.setPactSts(pactSts);
            // this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
            String isOpen = PropertiesUtil.getCloudProperty("cloud.loanafter_filter");
            // 默认关闭
            if ("1".equals(isOpen)) {// 开启
                String roleNo = PropertiesUtil.getCloudProperty("cloud.loanafter_roleno");
                if (StringUtil.isNotEmpty(roleNo)) {
                    String[] roleNoArray = User.getRoleNo(request);
                    for (String role : roleNoArray) {
                        if (roleNo.equals(role)) {
                            mfBusFincApp.setOverdueSts(BizPubParm.OVERDUE_STS_1);
                        }
                    }
                }
            }
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.findLoanAfterByPage(ipage);
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

    @RequestMapping(value = "/findByPageAjaxExcel")
    @ResponseBody
    public void findByPageAjaxExcel(String tableId) throws Exception {
        //读取出文件的所有字节,并将所有字节写出给客户端
        ActionContext.initialize(request, response);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            String isOpen = PropertiesUtil.getCloudProperty("cloud.loanafter_filter");
            // 默认关闭
            if ("1".equals(isOpen)) {// 开启
                String roleNo = PropertiesUtil.getCloudProperty("cloud.loanafter_roleno");
                if (StringUtil.isNotEmpty(roleNo)) {
                    String[] roleNoArray = User.getRoleNo(request);
                    for (String role : roleNoArray) {
                        if (roleNo.equals(role)) {
                            mfBusFincApp.setOverdueSts(BizPubParm.OVERDUE_STS_1);
                        }
                    }
                }
            }
            List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.findByPageAjaxExcel(mfBusFincApp);
            ExpExclUtil eu = new ExpExclUtil();
            HSSFWorkbook wb = eu.expExclTableForList(tableId, mfBusFincAppList, null, false, "");
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


    /***
     * 贷后合同列表数据查询(支持多客户)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjaxForTrh")
    @ResponseBody
    public Map<String, Object> findByPageAjaxForTrh(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                    String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setChannelSourceNo(cusNo);
            // mfBusFincApp.setPactSts(pactSts);
            // this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            // 自定义查询Bo方法
            ipage = mfBusFincAppFeign.findLoanAfterByPageForTrh(ipage);
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
     * 从合同列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findFollowPactNoByPageAjax")
    @ResponseBody
    public Map<String, Object> findFollowPactNoByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                          String tableType, String ajaxData, String appId, MfBusFincApp mfBusFincApp) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.findLoanAfterByPage(ipage);
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
     * 批量提额合同列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findPactBatchByPageAjax")
    @ResponseBody
    public Map<String, Object> findPactBatchByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                       String tableType, String ajaxData, Ipage ipage) throws Exception {
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
            ipage = mfBusPactFeign.findPactBatchByPage(ipage);
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
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCleanListPage")
    public String getCleanListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/pact/MfBusPact_cleanList";
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findCleanListByPageAjax")
    @ResponseBody
    public Map<String, Object> findCleanListByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                       String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage = mfBusPactFeign.getBusDataCleanList(ipage, mfBusPact);
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

    @RequestMapping(value = "/getPactList")
    public String getPactList(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/pact/MfBusPact_PactList";
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPactListAjax")
    @ResponseBody
    public Map<String, Object> getPactListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                               String ajaxData, String opNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setOpNo(opNo);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.findByPage(ipage);
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
     * 打开到账确认列表
     * @author weisd
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCheckListPage")
    public String getCheckListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/pact/MfBusPact_CheckList";
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCheckListAjax")
    @ResponseBody
    public Map<String, Object> getCheckListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                               String ajaxData, String opNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
//            mfBusPact.setOpNo(opNo);
 //           mfBusPact.setNodeNo(WKF_NODE.account_confirm.getNodeNo());
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.findCheckByPage(ipage);
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
     * 方法描述：到账确认
     * @author weisd
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2018-4-17 下午3:56:10
     */
    @RequestMapping(value = "/inputForCheck")
    public String inputForCheck(Model model, String appId, String pactId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formpactcheck = formService.getFormData("pactcheck");
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        //应收担保费和应收评审费
        MfBusAppFee mfBusAppFee = new MfBusAppFee();
        mfBusAppFee.setAppId(mfBusPact.getAppId());
        List<MfBusAppFee> mfBusAppFeeList = mfBusAppFeeFeign.getList(mfBusAppFee);
        for(MfBusAppFee busAppFee : mfBusAppFeeList){
            if("1".equals(busAppFee.getItemNo())){//担保费
                mfBusPact.setReguaranteeAmt(MathExtend.multiply(mfBusPact.getPactAmt(), busAppFee.getRateScale()));
            }else if("8".equals(busAppFee.getItemNo())){//评审费
                mfBusPact.setReassessAmt(MathExtend.multiply(mfBusPact.getPactAmt(), busAppFee.getRateScale()));
            }
        }
        //缴款通知书
        MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
        mfBusChargeFee.setAppId(mfBusPact.getAppId());
        mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
        mfBusPact.setActualReceivedAmt(mfBusChargeFee.getAccountAmt());
        mfBusPact.setReceviedDate(mfBusChargeFee.getRepayDate());
        mfBusPact.setReceviedAccout(mfBusChargeFee.getCollectAccount());
        mfBusPact.setReceviedAccoutName(mfBusChargeFee.getCollectAccName());
        mfBusPact.setReceviedBankName(mfBusChargeFee.getCollectBank());
        //应退费金额
        mfBusPact.setRefundAmt(MathExtend.subtract(mfBusChargeFee.getAccountAmt(),MathExtend.add(mfBusPact.getReguaranteeAmt(), mfBusPact.getReassessAmt())));
        getObjValue(formpactcheck, mfBusPact);
        model.addAttribute("appId", appId);
        model.addAttribute("pactId", pactId);
        model.addAttribute("formpactcheck", formpactcheck);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_InsertForCheck";
    }

    /**
     * 到账确认并提交主流程到下一环节
     *
     * @author weisd
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateForCheck")
    @ResponseBody
    public Map<String, Object> updateForCheck(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formpactcheck = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formpactcheck, getMapByJson(ajaxData));
            if (this.validateFormData(formpactcheck)) {
                MfBusPact mfBusPact = new MfBusPact();
                setObjValue(formpactcheck, mfBusPact);
                //实际到账金额>=应收总金额，否则系统进行拦截，不能保存
                Double sumAmt = MathExtend.add(mfBusPact.getReguaranteeAmt(), mfBusPact.getReassessAmt()) ;
                if(mfBusPact.getActualReceivedAmt() < sumAmt){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "实际到账金额不能小于应收总金额！");
                    return dataMap;
                }
                mfBusPactFeign.updateForCheck(mfBusPact);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formpact0002 = formService.getFormData("pact0002");
            getFormValue(formpact0002, getMapByJson(ajaxData));
            if (this.validateFormData(formpact0002)) {
                MfBusPact mfBusPact = new MfBusPact();
                setObjValue(formpact0002, mfBusPact);
                mfBusPactFeign.insert(mfBusPact);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
        dataMap = getMapByJson(ajaxData);
        MfBusPact mfBusPactJsp = new MfBusPact();
        mfBusPactJsp.setPactId(String.valueOf(dataMap.get("pactId")));
        MfBusPact mfBusPact = mfBusPactFeign.getById(mfBusPactJsp);
        if (mfBusPact != null) {
            FormData formpact0004 = null;
            String formId = (String)dataMap.get("formId");
            if(StringUtil.isEmpty(formId)){
                if (mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_5)) {// 应收账款融资(保理)
                    formId = "pact0004bl";
                } else {
                    formId = "pact0004";
                }
            }
            formpact0004 = formService.getFormData(formId);
            getFormValue(formpact0004, getMapByJson(ajaxData));
            mfBusPactJsp = new MfBusPact();
            setObjValue(formpact0004, mfBusPactJsp);
            try {
                mfBusPact = (MfBusPact) EntityUtil.reflectionSetVal(mfBusPact, mfBusPactJsp, getMapByJson(ajaxData));
                mfBusPactFeign.updatePactByOne(mfBusPact, dataMap);
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
    public Map<String, Object>  updateAjax(String ajaxData, String busModel, String appId, String query, String temporaryStorage, String nodeNoOld)
            throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            FormData formpact0008 = formService.getFormData(formId);
            getFormValue(formpact0008, getMapByJson(ajaxData));
            if (this.validateFormDataAnchor(formpact0008)) {
                mfBusPact = new MfBusPact();
                setObjValue(formpact0008, mfBusPact);
                // 合同编号唯一性检查
                MfBusPact mfBusPactNo = new MfBusPact();
                mfBusPactNo.setPactNo(mfBusPact.getPactNo());
                List<MfBusPact> mfBusPactList;
                mfBusPactList = mfBusPactFeign.getByPactNo(mfBusPactNo);
                if ((mfBusPactList.size() == 1 && !mfBusPactList.get(0).getPactId().equals(mfBusPact.getPactId()))
                        || mfBusPactList.size() > 1) {
                    Map<String, String> msgMap = new HashMap<String, String>();
                    msgMap.put("pactNo", mfBusPact.getPactNo());
                    msgMap.put("followType", "");
                    dataMap.put("flag", "error");
                    dataMap.put("msg", MessageEnum.EXIST_PACT_NO.getMessage(msgMap));
                    return dataMap;
                }
                //合同生成文件是否全部生成
                boolean saveFlag =false;
                String saveName ="";
                MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
                mfTemplateBizConfig.setNodeNo(WKF_NODE.contract_sign.getNodeNo());
                mfTemplateBizConfig.setAppId(appId);
                List<MfTemplateBizConfig> mfTemplateBizConfigs = mfTemplateBizConfigFeign.getBizConfigList(mfTemplateBizConfig);
                if(mfTemplateBizConfigs.size()>0){
                    for (int i = 0; i < mfTemplateBizConfigs.size(); i++) {
                        mfTemplateBizConfig = mfTemplateBizConfigs.get(i);
                        if("1".equals(mfTemplateBizConfig.getIfMustInput())&&StringUtil.isEmpty(mfTemplateBizConfig.getDocFilePath())){
                            saveFlag =true;
                            saveName = mfTemplateBizConfig.getTemplateNameZh();
                            break;
                        }
                    }
                }
                if(saveFlag){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "请先保存"+saveName+"后，再进行保存！");
                    return dataMap;
                }
                //最高额担保合同额度验证
                mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
                if ("contract_sign".equalsIgnoreCase(mfBusApply.getNodeNo())) {
                    Map<String, Object> checkResult = mfHighGuaranteeContractFeign.checkLimit(mfBusPact.getAppId(), mfBusPact.getPactAmt());
                    if ("error".equals((String) checkResult.get("flag"))) {
                        return checkResult;
                    }
                }
                mfBusPact.setAppId(appId);
                mfBusPact.setUsableFincAmt(mfBusPact.getPactAmt());
                mfBusPact.setOpNo(User.getRegNo(request));
                mfBusPact.setOpName(User.getRegName(request));
                mfBusPact.setBrNo(User.getOrgNo(request));
                mfBusPact.setBrName(User.getOrgName(request));
                /**
                 * 表单上是int,实体里面是string，当初设计字段时类型有问题。
                 */
                mfBusPact.setRepayDateDef(StringUtil.KillBlankAndTrim((String) map.get("repayDateDef"), ""));
                TaskImpl taskApprove = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);// 当前审批节点task
                if (!nodeNoOld.equals(taskApprove.getActivityName())) {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
                    return dataMap;
                }
                mfBusPact = mfBusPactFeign.disProcessDataForPact(mfBusPact);
                // 更新合同数据
                new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusPact);
                map.put("mfBusPact", mfBusPact);
                map.put("pactNodeNo", "contract_sign_no");
                map.put("temporaryStorage", temporaryStorage);
                mfBusPactFeign.updatePact(map);
                String detailFormId = "pact0004";
                MfBusPact mfBusPactTmp = new MfBusPact();
                mfBusPactTmp.setPactId(mfBusPact.getPactId());
                mfBusPactTmp = mfBusPactFeign.getById(mfBusPactTmp);
                mfBusPactTmp = mfBusPactFeign.processDataForPact(mfBusPactTmp);// 处理合同申请利率展示单位
                if (BizPubParm.BUS_MODEL_5.equals(mfBusPactTmp.getBusModel())) {// 应收账款融资(保理业务)
                    detailFormId = "pact0004bl";
                }
                formpact0008 = formService.getFormData(detailFormId);
                getFormValue(formpact0008);
                getObjValue(formpact0008, mfBusPactTmp);
                // 期限单位格式化
                CodeUtils codeUtils = new CodeUtils();
                Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
                String termUnit = termTypeMap.get(mfBusPactTmp.getTermType()).getRemark();
                this.changeFormProperty(formpact0008, "term", "unit", termUnit);
                // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
                Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
                String rateUnit = rateTypeMap.get(mfBusPactTmp.getRateType()).getRemark();
                this.changeFormProperty(formpact0008, "fincRate", "unit", rateUnit);
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formpact0008, "propertySeeTag", query);
                dataMap.put("pactDetailInfo", htmlStr);
                dataMap.put("flag", "success");
                dataMap.put("pactSts", mfBusPactTmp.getPactSts());
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            }else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }

            /*
             * if(this.validateFormData(formpact0008)){ mfBusPact = new
             * MfBusPact(); setObjValue(formpact0008, mfBusPact);
             * mfBusPact.setAppId(appId); mfBusPactFeign.updatePact(mfBusPact);
             * dataMap.put("flag", "success");
             * dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
             * }else{ dataMap.put("flag", "error");
             * dataMap.put("msg",this.getFormulavaliErrorMsg()); }
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
     * 合同生成暂存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateFormAjax")
    @ResponseBody
    public Map<String, Object>  updateFormAjax(String ajaxData, String busModel, String appId, String query, String temporaryStorage, String nodeNoOld)
            throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            FormData formpact0008 = formService.getFormData(formId);
            getFormValue(formpact0008, getMapByJson(ajaxData));
            mfBusPact = new MfBusPact();
            setObjValue(formpact0008, mfBusPact);
            // 合同编号唯一性检查
            MfBusPact mfBusPactNo = new MfBusPact();
            mfBusPactNo.setPactNo(mfBusPact.getPactNo());
            List<MfBusPact> mfBusPactList;
            mfBusPactList = mfBusPactFeign.getByPactNo(mfBusPactNo);
            if ((mfBusPactList.size() == 1 && !mfBusPactList.get(0).getPactId().equals(mfBusPact.getPactId()))
                    || mfBusPactList.size() > 1) {
                Map<String, String> msgMap = new HashMap<String, String>();
                msgMap.put("pactNo", mfBusPact.getPactNo());
                msgMap.put("followType", "");
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.EXIST_PACT_NO.getMessage(msgMap));
                return dataMap;
            }
            //最高额担保合同额度验证
            mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
            if ("contract_sign".equalsIgnoreCase(mfBusApply.getNodeNo())) {
                Map<String, Object> checkResult = mfHighGuaranteeContractFeign.checkLimit(mfBusPact.getAppId(), mfBusPact.getPactAmt());
                if ("error".equals((String) checkResult.get("flag"))) {
                    return checkResult;
                }
            }
            mfBusPact.setAppId(appId);
            mfBusPact.setUsableFincAmt(mfBusPact.getPactAmt());
            mfBusPact.setOpNo(User.getRegNo(request));
            mfBusPact.setOpName(User.getRegName(request));
            mfBusPact.setBrNo(User.getOrgNo(request));
            mfBusPact.setBrName(User.getOrgName(request));
            /**
             * 表单上是int,实体里面是string，当初设计字段时类型有问题。
             */
            mfBusPact.setRepayDateDef(StringUtil.KillBlankAndTrim((String) map.get("repayDateDef"), ""));
            TaskImpl taskApprove = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);// 当前审批节点task
            if (!nodeNoOld.equals(taskApprove.getActivityName())) {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
                return dataMap;
            }
            // 更新合同数据
            mfBusPactFeign.update(mfBusPact);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    private Map<String, Object> checkCredit(MfBusApply mfBusApply) throws Exception {
        // 1.客户授信检查
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (mfCusCustomer != null) {
            dataMap = creditApplyInterfaceFeign.checkCredit(mfBusApply, mfCusCustomer.getCusBaseType(), mfBusApply.getCusNo());
            if ("error".equals(dataMap.get("flag").toString())) {
                return dataMap;
            }
        }
        // 2.判断是否关联资金机构，如果已关联资金机构，进行授信检查
        String cusNoFund = mfBusApply.getCusNoFund();
        if (cusNoFund != null && !"".equals(cusNoFund)) {
            MfBusAgencies mfBusAgencies = new MfBusAgencies();
            mfBusAgencies.setAgenciesUid(cusNoFund);
            mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
            if (mfBusAgencies != null) {
                dataMap = creditApplyInterfaceFeign.checkCredit(mfBusApply, BizPubParm.CUS_BASE_TYPE_ZIJIN, cusNoFund);
                if ("error".equals(dataMap.get("flag").toString())) {
                    return dataMap;
                }
            }
        }
        // 3.判断是否关联渠道商或者个人渠道，如果已关联渠道商或者个人渠道，进行授信检查
        String channelSource = mfBusApply.getChannelSourceNo();
        if (channelSource != null && !"".equals(channelSource)) {
            mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(channelSource);
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
            if (mfCusCustomer != null) {
                dataMap = creditApplyInterfaceFeign.checkCredit(mfBusApply, mfCusCustomer.getCusBaseType(), channelSource);
                if ("error".equals(dataMap.get("flag").toString())) {
                    return dataMap;
                }
            }
        }
        return dataMap;
    }

    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjaxForManage")
    @ResponseBody
    public Map<String, Object> updateAjaxForManage(String ajaxData, String busModel, String appId, String query)
            throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            FormData formpact0008 = formService.getFormData(formId);
            getFormValue(formpact0008, getMapByJson(ajaxData));
            mfBusPact = new MfBusPact();
            setObjValue(formpact0008, mfBusPact);
            // 合同编号唯一性检查
            MfBusPact mfBusPactNo = new MfBusPact();
            mfBusPactNo.setPactNo(mfBusPact.getPactNo());
            List<MfBusPact> mfBusPactList;
            mfBusPactList = mfBusPactFeign.getByPactNo(mfBusPactNo);
            if ((mfBusPactList.size() == 1 && !mfBusPactList.get(0).getPactId().equals(mfBusPact.getPactId()))
                    || mfBusPactList.size() > 1) {
                Map<String, String> msgMap = new HashMap<String, String>();
                msgMap.put("pactNo", mfBusPact.getPactNo());
                msgMap.put("followType", "");
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.EXIST_PACT_NO.getMessage(msgMap));
                return dataMap;
            }
            mfBusPact.setAppId(appId);
            mfBusPact.setUsableFincAmt(mfBusPact.getPactAmt());
            /**
             * 表单上是int,实体里面是string，当初设计字段时类型有问题。
             */
            mfBusPact.setRepayDateDef(StringUtil.KillBlankAndTrim((String) map.get("repayDateDef"), ""));
            mfBusPact = mfBusPactFeign.disProcessDataForPact(mfBusPact);
            // 更新合同数据
            new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusPact);
            map.put("mfBusPact", mfBusPact);
            map.put("contractManage", "0");
            mfBusPactFeign.updatePact(map);

            String detailFormId = "pact0004";
            MfBusPact mfBusPactTmp = new MfBusPact();
            mfBusPactTmp.setPactId(mfBusPact.getPactId());
            mfBusPactTmp = mfBusPactFeign.getById(mfBusPactTmp);
            mfBusPactTmp = mfBusPactFeign.processDataForPact(mfBusPactTmp);// 处理合同申请利率展示单位
            if (BizPubParm.BUS_MODEL_5.equals(mfBusPactTmp.getBusModel())) {// 应收账款融资(保理业务)
                detailFormId = "pact0004bl";
            }
            formpact0008 = formService.getFormData(detailFormId);
            getFormValue(formpact0008);
            getObjValue(formpact0008, mfBusPactTmp);
            // 期限单位格式化
            CodeUtils codeUtils = new CodeUtils();
            Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
            String termUnit = termTypeMap.get(mfBusPactTmp.getTermType()).getRemark();
            this.changeFormProperty(formpact0008, "term", "unit", termUnit);
            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusPactTmp.getRateType()).getRemark();
            this.changeFormProperty(formpact0008, "fincRate", "unit", rateUnit);
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String htmlStr = jsonFormUtil.getJsonStr(formpact0008, "propertySeeTag", query);
            dataMap.put("pactDetailInfo", htmlStr);
            dataMap.put("flag", "success");
            dataMap.put("pactSts", mfBusPactTmp.getPactSts());
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 移动端:AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateForAppAjax")
    @ResponseBody
    public Map<String, Object> updateForAppAjax(String ajaxData, String busModel, String appId, String query)
            throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            if (StringUtil.isEmpty(formId)) {
                if (BizPubParm.BUS_MODEL_5.equals(busModel) || BizPubParm.BUS_MODEL_13.equals(busModel)) {// 应收账款融资(保理业务)
                    formId = "pact0008bl";
                } else {
                    formId = "pact0008";
                }
                formId = "pact0008";
            }
            FormData formpact0008 = formService.getFormData(formId);
            getFormValue(formpact0008, getMapByJson(ajaxData));
            mfBusPact = new MfBusPact();
            setObjValue(formpact0008, mfBusPact);
            mfBusPact.setAppId(appId);
            mfBusPact.setUsableFincAmt(mfBusPact.getPactAmt());
            mfBusPact = mfBusPactFeign.disProcessDataForPact(mfBusPact);
            mfBusPactFeign.updatePactForApp(mfBusPact);

            String detailFormId = "pact0004";

            MfBusPact mfBusPactTmp = new MfBusPact();
            mfBusPactTmp.setPactId(mfBusPact.getPactId());
            mfBusPactTmp = mfBusPactFeign.getById(mfBusPactTmp);
            mfBusPactTmp = mfBusPactFeign.processDataForPact(mfBusPactTmp);// 处理合同申请利率展示单位
            if (BizPubParm.BUS_MODEL_5.equals(mfBusPactTmp.getBusModel())) {// 应收账款融资(保理业务)
                detailFormId = "pact0004bl";
            }
            formpact0008 = formService.getFormData(detailFormId);
            getFormValue(formpact0008);
            getObjValue(formpact0008, mfBusPactTmp);
            // 期限单位格式化
            CodeUtils codeUtils = new CodeUtils();
            Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
            String termUnit = termTypeMap.get(mfBusPactTmp.getTermType()).getRemark();
            this.changeFormProperty(formpact0008, "term", "unit", termUnit);
            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusPactTmp.getRateType()).getRemark();
            this.changeFormProperty(formpact0008, "fincRate", "unit", rateUnit);
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String htmlStr = jsonFormUtil.getJsonStr(formpact0008, "propertySeeTag", query);
            dataMap.put("pactDetailInfo", htmlStr);
            dataMap.put("flag", "success");
            dataMap.put("pactSts", mfBusPactTmp.getPactSts());
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String pactId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formpact0002 = formService.getFormData("pact0002");
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        getObjValue(formpact0002, mfBusPact, formData);
        if (mfBusPact != null) {
            dataMap.put("flag", "success");
            dataMap.put("mfBusPact", "mfBusPact");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        dataMap.put("beginDate", mfBusPact.getBeginDate());
        dataMap.put("endDate", mfBusPact.getEndDate());
        dataMap.put("termShow", mfBusPact.getTermShow());
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
    public Map<String, Object> deleteAjax(String pactId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        try {
            mfBusPactFeign.delete(mfBusPact);
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
    @RequestMapping(value = "/input")
    public String input(Model model, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String nodeNo = WKF_NODE.contract_sign.getNodeNo();// 功能节点编号

        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        String pactId = mfBusPact.getPactId();
        Object coborrNum = cusInterfaceFeign.getCobBorrowerForShow(mfBusPact.getCoborrNum()).toString();
        Task task = wkfInterfaceFeign.getTask(mfBusPact.getWkfAppId(), null);
        String formId = "";
        if (WKF_NODE.contract_sign.getNodeNo().equals(task.getActivityName())) {
            nodeNo = WKF_NODE.contract_sign.getNodeNo();// 功能节点编号
            formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.contract_sign, null, null, User.getRegNo(request));
        } else if (WKF_NODE.contract_print.getNodeNo().equals(task.getActivityName())) {
            nodeNo = WKF_NODE.contract_print.getNodeNo();// 功能节点编号
            formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.contract_print, null, null, User.getRegNo(request));
            // 初始化电签模板
            MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
            mfBusApply.setNodeNo(nodeNo);
            esignInterfaceFeign.initReplacePdf(mfBusApply);
        }else {
        }

        FormData formpact0008 = formService.getFormData(formId);
        Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(appId);
        if (!creditData.isEmpty()) {
            getObjValue(formpact0008, creditData);
            mfBusPact.setCreditPactNo((String) creditData.get("creditPactNo"));
        }
        // 合同签约中的还款方式修改时应收产品设置中的还款方式控制
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        if (mfBusAppKind != null) {
            // 默认不带出
//            mfBusPact.setRepayDateSet(mfBusAppKind.getRepayDateSet());
            mfBusPact.setRepayDateDef(mfBusAppKind.getRepayDateDef());
            if (mfBusAppKind.getGraceDays() != null) {
                mfBusPact.setGracePeriodFinc(mfBusAppKind.getGraceDays());
            }
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
                this.changeFormProperty(formpact0008, "penaltyCalRateFinc", "unit", "%");
            } else {
                this.changeFormProperty(formpact0008, "penaltyCalRateFinc", "unit", "元");
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
                this.changeFormProperty(formpact0008, "earlyRepayRate", "unit", "%");
            } else {
                this.changeFormProperty(formpact0008, "earlyRepayRate", "unit", "元");
            }
        }

        //获取合作机构
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        //合同生成是需要根据是否授信新的业务需要拷贝对应的授信合同
        //判断是否已经初始化挂
        if(!"1".equals(mfBusPact.getInitCreditPact())){
            //授信下同时发起业务
            if("1".equals(mfBusApply.getIsCreditFlag())){
                //授信下的文档信息拷贝到业务下
                if(StringUtil.isNotEmpty(mfBusApply.getCreditAppId())){
                    MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
                    mfTemplateBizConfig.setTemBizNo(mfBusApply.getCreditAppId());
                    mfTemplateBizConfig.setNodeNo(WKF_NODE.protocolPrint.getNodeNo());
                    List<MfTemplateBizConfig> mfTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(mfTemplateBizConfig);
                    for (int i = 0; i < mfTemplateBizConfigList.size(); i++) {
                        mfTemplateBizConfig = mfTemplateBizConfigList.get(i);
                        MfTemplateBizConfig mfTemplateBizConfigUpdate = new MfTemplateBizConfig();//将授信下的该合作银行设置为已经使用
                        mfTemplateBizConfigUpdate.setTemplateBizConfigId(mfTemplateBizConfig.getTemplateBizConfigId());
                        if((mfBusApply.getAgenciesId()!=null&&mfBusApply.getAgenciesId().equals(mfTemplateBizConfig.getExt1()))||StringUtil.isEmpty(mfTemplateBizConfig.getExt1())){
                            mfTemplateBizConfig.setTemplateBizConfigId(WaterIdUtil.getWaterId());
                            mfTemplateBizConfig.setTemBizNo(mfBusApply.getAppId());
                            mfTemplateBizConfig.setAppId(mfBusApply.getAppId());
                            mfTemplateBizConfig.setKindNo(mfBusApply.getKindNo());
                            mfTemplateBizConfig.setNodeNo(WKF_NODE.contract_sign.getNodeNo());
                            mfTemplateBizConfig.setStampSts("0");
                            mfTemplateBizConfigFeign.insert(mfTemplateBizConfig);
                            //将授信下的该合作银行设置为已经使用
                            mfTemplateBizConfigUpdate.setExt4("1");
                            mfTemplateBizConfigFeign.update(mfTemplateBizConfigUpdate);
                        }
                    }
                }
            }else{
                //授信下的文档信息拷贝到业务下,如果该合作银行的业务合同没使用过，需要再次带出
                if(StringUtil.isNotEmpty(mfBusApply.getCreditAppId())){
                    MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
                    mfTemplateBizConfig.setTemBizNo(mfBusApply.getCreditAppId());
                    mfTemplateBizConfig.setNodeNo(WKF_NODE.protocolPrint.getNodeNo());
                    List<MfTemplateBizConfig> mfTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(mfTemplateBizConfig);
                    for (int i = 0; i < mfTemplateBizConfigList.size(); i++) {
                        mfTemplateBizConfig = mfTemplateBizConfigList.get(i);
                        MfTemplateBizConfig mfTemplateBizConfigUpdate = new MfTemplateBizConfig();//将授信下的该合作银行设置为已经使用
                        mfTemplateBizConfigUpdate.setTemplateBizConfigId(mfTemplateBizConfig.getTemplateBizConfigId());
                        if(mfBusApply.getAgenciesId()!=null&&mfBusApply.getAgenciesId().equals(mfTemplateBizConfig.getExt1())&&!"1".equals(mfTemplateBizConfig.getExt4())){
                            mfTemplateBizConfig.setTemplateBizConfigId(WaterIdUtil.getWaterId());
                            mfTemplateBizConfig.setTemBizNo(mfBusApply.getAppId());
                            mfTemplateBizConfig.setAppId(mfBusApply.getAppId());
                            mfTemplateBizConfig.setKindNo(mfBusApply.getKindNo());
                            mfTemplateBizConfig.setNodeNo(WKF_NODE.contract_sign.getNodeNo());
                            mfTemplateBizConfig.setStampSts("0");
                            mfTemplateBizConfigFeign.insert(mfTemplateBizConfig);
                            //将授信下的该合作银行设置为已经使用
                            mfTemplateBizConfigUpdate.setExt4("1");
                            mfTemplateBizConfigFeign.update(mfTemplateBizConfigUpdate);
                        }
                    }
                }
            }
            MfBusPact mfBusPactUpate = new MfBusPact();
            mfBusPactUpate.setPactId(mfBusPact.getPactId());
            mfBusPactUpate.setInitCreditPact("1");
            mfBusPactFeign.update(mfBusPactUpate);
        }
        mfBusApply.setRemark(mfBusPact.getRemark());
        if(mfBusPact.getLoanHis()==null){
            MfBusPact mfBusPactQuery = new MfBusPact();
            mfBusPactQuery.setCusNo(mfBusPact.getCusNo());
            mfBusPact.setLoanHis(mfBusPactFeign.getPactSumByCusNo(mfBusPactQuery));
        }
        getObjValue(formpact0008, mfBusApply);
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
        //获取配置的费用
        MfBusAppFee mfBusAppFee = new MfBusAppFee();
        mfBusAppFee.setAppId(mfBusApply.getAppId());
        List<MfBusAppFee> mfBusAppFeeList = mfBusAppFeeFeign.getMfBusAppFeeList(mfBusAppFee);
        if(mfBusAppFeeList.size()>0){
            for (int i = 0; i < mfBusAppFeeList.size(); i++) {
                mfBusAppFee = mfBusAppFeeList.get(i);
                if("2".equals(mfBusAppFee.getItemNo())){
                    mfBusPact.setPinShenFeeRate(mfBusAppFee.getRateScale());
                }
                if("1".equals(mfBusAppFee.getItemNo())){
                    mfBusPact.setDanBaoFeeRate(mfBusAppFee.getRateScale());
                }
            }
        }
        mfBusPact.setLoaner(mfBusPact.getCusName());
        mfBusPact.setBkcxdbOppositeParty(mfBusPact.getAgenciesName());
        //报批的决策文件编号
        mfBusPact.setApprovaDocNo(mfBusApply.getDeptBathchNo());
        //获取保证人
        List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(mfBusApply.getAppId());
        String sxdfOppositeParty =mfBusPact.getCusName()+",";
        String fdbbzOppositeParty = "";
        if(mfAssureInfoList!=null&&mfAssureInfoList.size()>0){
            for (int i = 0; i < mfAssureInfoList.size(); i++) {
                fdbbzOppositeParty =fdbbzOppositeParty+mfAssureInfoList.get(i).getAssureName();
                sxdfOppositeParty = sxdfOppositeParty+mfAssureInfoList.get(i).getAssureName();
            }
        }
        mfBusPact.setFdbbzOppositeParty(fdbbzOppositeParty);
        mfBusPact.setSxdfOppositeParty(sxdfOppositeParty);
        mfBusPact.setZhuTerm(mfBusPact.getTerm());
        mfBusPact.setGuaranteeAmt(mfBusPact.getPactAmt());

        mfBusPact.setPinShenRate(mfBusPact.getPinShenFeeRate());
        mfBusPact.setDanBaoRate(mfBusPact.getDanBaoFeeRate());
        if(mfBusPact.getPinShenRate()!=null){
            mfBusPact.setPinShenFee(MathExtend.multiply(mfBusPact.getPactAmt(),mfBusPact.getPinShenRate())/100);
        }
        if(mfBusPact.getDanBaoRate()!=null){
            mfBusPact.setDanBaoFee(MathExtend.multiply(mfBusPact.getPactAmt(),mfBusPact.getDanBaoRate())/100);
        }
        mfBusPact.setRemark(null);
        if(mfBusPact.getLoanHis()==null){
            MfBusPact mfBusPactQuery = new MfBusPact();
            mfBusPactQuery.setCusNo(mfBusPact.getCusNo());
            mfBusPact.setLoanHis(mfBusPactFeign.getPactSumByCusNo(mfBusPactQuery));
        }
        getObjValue(formpact0008, mfBusPact);
        this.changeFormProperty(formpact0008, "ifImmediateTransfer", "initValue", "0");
        // 获取从合同列表
        List<MfBusFollowPact> mfBusFollowPactList = new ArrayList<MfBusFollowPact>();
        Double pactAmt = mfBusPact.getPactAmt();
        mfBusFollowPactList = MfBusCollateralDetailRelFeign.getMfBusFollowPactList(appId, pactAmt);
        if (mfBusFollowPactList.size() == 0) {
            mfBusFollowPactList = null;
        }
        //放款渠道过滤
        List<OptionsList> opinionTypeList = new ArrayList<OptionsList>();
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
        this.changeFormProperty(formpact0008, "putoutMethod", "optionArray", opinionTypeList);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formpact0008, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formpact0008, "overRate", "unit", rateUnit);
        this.changeFormProperty(formpact0008, "cmpdRate", "unit", rateUnit);

        String wkfAppId = mfBusPact.getWkfAppId();


        JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
        JSONArray vouTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusPact.getVouType(), "VOU_TYPE");
        JSONObject json = new JSONObject();
        JSONArray map = mfBusPactFeign.getRepayMode();
        JSONArray repayDateSetMap = prdctInterfaceFeign.getRepayRulesMap(mfBusPact);
        for (int i = 0; i < repayDateSetMap.size(); i++) {
            String repayDateSet = repayDateSetMap.getJSONObject(i).getString("id").split("_")[1];
            repayDateSetMap.getJSONObject(i).put("id", repayDateSet);
        }

        json.put("repayTypeMap", repayTypeMap);
        json.put("vouTypeMap", vouTypeMap);
        json.put("repayModeMap", map);
        json.put("repayDateSetMap", repayDateSetMap);

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
        Map<String, String> parmMap = new HashMap<String, String>();
        parmMap.put("appId", appId);
        String calcIntstFlag = mfBusAppKind.getCalcIntstFlag();
        String pactEndDateShowFlag = mfBusAppKind.getPactEnddateShowFlag();
        //合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天
        if (StringUtil.isEmpty(pactEndDateShowFlag)) {
            pactEndDateShowFlag = prdctInterfaceFeign.getPactEndDateShowFlag();
        }
        //子产品列表  只确定了一个产品的，多个产品联动暂不能实现
        MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusPact.getKindNo());
        List<OptionsList> subKindList = new ArrayList<OptionsList>();
        if (StringUtil.isEmpty(mfSysKind.getSubKindNo())) {
            List<ParmDic> pdl = (List<ParmDic>) new CodeUtils().getCacheByKeyName("SUB_KIND_TYPE");
            for (ParmDic parmDic : pdl) {
                OptionsList s = new OptionsList();
                s.setOptionLabel(parmDic.getOptName());
                s.setOptionValue(parmDic.getOptCode());
                subKindList.add(s);
            }
        } else {
            Map<String, String> dicMap = new CodeUtils().getMapByKeyName("SUB_KIND_TYPE");
            String[] subKindNos = mfSysKind.getSubKindNo().split("\\|");
            for (int i = 0; i < subKindNos.length; i++) {
                OptionsList s = new OptionsList();
                s.setOptionValue(subKindNos[i]);
                s.setOptionLabel(dicMap.get(subKindNos[i]));
                subKindList.add(s);
            }
        }
        this.changeFormProperty(formpact0008, "subKindNo", "optionArray", subKindList);

        //处理其他担保方式
        List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
        MfBusAppKind mfBusAppKindVouType = new MfBusAppKind();
        mfBusAppKindVouType.setAppId(appId);
        mfBusAppKindVouType = mfBusAppKindFeign.getById(mfBusAppKindVouType);
        if (StringUtil.isEmpty(mfBusAppKindVouType.getVouType())) {
            List<ParmDic> pdl = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
            for (ParmDic parmDic : pdl) {
                if ("1".equals(parmDic.getOptCode())) {
                    continue;
                }
                OptionsList s = new OptionsList();
                s.setOptionLabel(parmDic.getOptName());
                s.setOptionValue(parmDic.getOptCode());
                vouTypeOtherList.add(s);
            }
        } else {
            Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
            String[] vouTypes = mfBusAppKindVouType.getVouType().split("\\|");
            for (int i = 0; i < vouTypes.length; i++) {
                if ("1".equals(vouTypes[i])) {
                    continue;
                }
                OptionsList s = new OptionsList();
                ;
                s.setOptionValue(vouTypes[i]);
                s.setOptionLabel(dicMap.get(vouTypes[i]));
                vouTypeOtherList.add(s);
            }
        }
        this.changeFormProperty(formpact0008, "vouTypeOther", "optionArray", vouTypeOtherList);
        // 从合同展示号 是否启用  1:启用
        String followPactNoShowSts = new CodeUtils().getSingleValByKey("FOLLOW_PACTNO_SHOW_STS");
        model.addAttribute("followPactNoShowSts", followPactNoShowSts);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("appId", appId);
        model.addAttribute("pactId", pactId);
        model.addAttribute("busModel", mfBusPact.getBusModel());
        model.addAttribute("coborrNum", coborrNum);
        model.addAttribute("repayType", mfBusPact.getRepayType());
        model.addAttribute("formId", formId);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("rateUnit", rateUnit);
        model.addAttribute("wkfAppId", wkfAppId);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("mfBusFollowPactList", mfBusFollowPactList);
        model.addAttribute("calcIntstFlag", calcIntstFlag);
        model.addAttribute("pactEndDateShowFlag", pactEndDateShowFlag);
        model.addAttribute("formpact0008", formpact0008);
        model.addAttribute("query", "");

        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        mfBusPactExtend.setAppId(appId);
        List<MfBusPactExtend> mfBusPactExtendList = mfBusPactExtendFeign.getAllListForApply(mfBusPactExtend) ;
        model.addAttribute("mfBusPactExtendList", mfBusPactExtendList);
        // ---------- begin 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------
        Map<String, ParmDic> SAVE_ONLY = new CodeUtils().getMapObjByKeyName("SAVE_ONLY");// 启用: 显示保存及提交两个按钮(支持保存未提交) 停用: 只显示保存按钮
        String SAVE_ONLY_5 = "0";
        if (WKF_NODE.contract_sign.getNodeNo().equals(task.getActivityName())) {
            if (SAVE_ONLY.containsKey("5")) {// 启用
                SAVE_ONLY_5 = "1";// 支持保存未提交
            }
        } else if (WKF_NODE.contract_print.getNodeNo().equals(task.getActivityName())) {
            if (SAVE_ONLY.containsKey("6")) {// 启用
                SAVE_ONLY_5 = "1";// 支持保存未提交
            }
        }else {
        }
        model.addAttribute("SAVE_ONLY_5", SAVE_ONLY_5);
        // ---------- end 是否支持 保存未提交 详见字典项SAVE_ONLY 启用禁用字典项控制 ----------

        return "/component/pact/MfBusPact_Insert";
    }

    @ResponseBody
    @RequestMapping(value = "/getPactListForArchive")
    public Map<String, Object> getPactListForArchive(int pageNo,String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusPact",mfBusPact));
            ipage = mfBusPactFeign.getPactList(ipage);
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
    @ResponseBody
    @RequestMapping(value = "/getVouAfterTrackByCusNoAjax")
    public Map<String, Object> getVouAfterTrackByCusNoAjax(String cusNo) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setCusNo(cusNo);
            List<MfBusPact> mfBusPactList = mfBusPactFeign.getVouAfterTrackByCusNo(mfBusPact);
            JSONArray pactInfoMap = new JSONArray();
            for (MfBusPact mfBusPact1 : mfBusPactList) {
                JSONObject obj = new JSONObject();
                obj.put("id", mfBusPact1.getPactId());
                obj.put("name", mfBusPact1.getPactNo());
                pactInfoMap.add(obj);
            }
            dataMap.put("pactInfoMap", pactInfoMap);
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
     * AJAX获取查看
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPactByIdAjax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPactByIdAjax(String pactId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        if(mfBusPact!=null){
            dataMap.put("mfBusPact",mfBusPact);
            dataMap.put("flag", "success");
        }else{
            dataMap.put("flag", "error");
        }
        return dataMap;
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputForManage")
    public String inputForManage(Model model, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String nodeNo = WKF_NODE.contract_sign.getNodeNo();// 功能节点编号

        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        String pactId = mfBusPact.getPactId();
        Object coborrNum = null;
        coborrNum = cusInterfaceFeign.getCobBorrower(mfBusPact.getCusNo()).toString();
        Task task = wkfInterfaceFeign.getTask(mfBusPact.getWkfAppId(), null);
        String formId = "";
        if (WKF_NODE.contract_sign.getNodeNo().equals(task.getActivityName())) {
            nodeNo = WKF_NODE.contract_sign.getNodeNo();// 功能节点编号
            formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.contract_sign, null, null, User.getRegNo(request));
        } else if (WKF_NODE.contract_print.getNodeNo().equals(task.getActivityName())) {
            nodeNo = WKF_NODE.contract_print.getNodeNo();// 功能节点编号
            formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.contract_print, null, null, User.getRegNo(request));
        } else if (WKF_NODE.contract_sign_manage.getNodeNo().equals(task.getActivityName())) {
            nodeNo = WKF_NODE.contract_sign_manage.getNodeNo();// 功能节点编号
            formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.contract_sign_manage, null, null, User.getRegNo(request));
            model.addAttribute("contractManage", "1");
        }else {
        }
        FormData formpact0008 = formService.getFormData(formId);
        Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(appId);
        if (!creditData.isEmpty()) {
            getObjValue(formpact0008, creditData);
            mfBusPact.setCreditPactNo((String) creditData.get("creditPactNo"));
        }
        // 合同签约中的还款方式修改时应收产品设置中的还款方式控制
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        if (mfBusAppKind != null) {
            mfBusPact.setRepayDateSet(mfBusAppKind.getRepayDateSet());
            mfBusPact.setRepayDateDef(mfBusAppKind.getRepayDateDef());
        }
        getObjValue(formpact0008, mfBusPact);
        this.changeFormProperty(formpact0008, "ifImmediateTransfer", "initValue", "0");
        // 获取从合同列表
        List<MfBusFollowPact> mfBusFollowPactList = new ArrayList<MfBusFollowPact>();
        Double pactAmt = mfBusPact.getPactAmt();
        mfBusFollowPactList = MfBusCollateralDetailRelFeign.getMfBusFollowPactList(appId, pactAmt);
        if (mfBusFollowPactList.size() == 0) {
            mfBusFollowPactList = null;
        }

        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formpact0008, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formpact0008, "overRate", "unit", rateUnit);
        this.changeFormProperty(formpact0008, "cmpdRate", "unit", rateUnit);

        String wkfAppId = mfBusPact.getWkfAppId();

        JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
        JSONArray vouTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusPact.getVouType(), "VOU_TYPE");
        JSONArray map = mfBusPactFeign.getRepayMode();
        JSONArray repayDateSetMap = prdctInterfaceFeign.getRepayRulesMap(mfBusPact);
        for (int i = 0; i < repayDateSetMap.size(); i++) {
            String repayDateSet = repayDateSetMap.getJSONObject(i).getString("id").split("_")[1];
            repayDateSetMap.getJSONObject(i).put("id", repayDateSet);
        }
        JSONObject json = new JSONObject();
        json.put("repayTypeMap", repayTypeMap);
        json.put("vouTypeMap", vouTypeMap);
        json.put("repayModeMap", map);
        json.put("repayDateSetMap", repayDateSetMap);
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
        Map<String, String> parmMap = new HashMap<String, String>();
        parmMap.put("appId", appId);
        String calcIntstFlag = mfBusAppKind.getCalcIntstFlag();
        String pactEndDateShowFlag = prdctInterfaceFeign.getPactEndDateShowFlag();
        //合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天
        if (StringUtil.isEmpty(pactEndDateShowFlag)) {
            pactEndDateShowFlag = prdctInterfaceFeign.getPactEndDateShowFlag();
        }
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("appId", appId);
        model.addAttribute("pactId", pactId);
        model.addAttribute("coborrNum", coborrNum);
        model.addAttribute("formId", formId);
        model.addAttribute("rateUnit", rateUnit);
        model.addAttribute("wkfAppId", wkfAppId);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("mfBusFollowPactList", mfBusFollowPactList);
        model.addAttribute("calcIntstFlag", calcIntstFlag);
        model.addAttribute("pactEndDateShowFlag", pactEndDateShowFlag);
        model.addAttribute("formpact0008", formpact0008);
        model.addAttribute("query", "");
        //电子文档列表查询方法（1-该节点只查询已经保存过的文档0-查询全部）
        String querySaveFlag = request.getParameter("querySaveFlag");
        model.addAttribute("querySaveFlag", querySaveFlag);
        return "/component/pact/MfBusPact_InsertForManage";
    }

    /**
     * 移动端合同签约流程:新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputForApp")
    public String inputForApp(Model model, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String nodeNo = WKF_NODE.contract_sign.getNodeNo();// 功能节点编号

        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        String pactId = mfBusPact.getPactId();
        Object coborrNum = cusInterfaceFeign.getCobBorrower(mfBusPact.getCusNo()).toString();
        String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), WKF_NODE.contract_sign, null, null, User.getRegNo(request));
        FormData formpact0008 = formService.getFormData(formId);
        mfBusPact.setSignDate(DateUtil.getDate());
        mfBusPact.setBeginDate(DateUtil.getDate());
        if ("1".equals(mfBusPact.getTermType())) {// 1-月
            mfBusPact.setEndDate(DateUtil.addMonth(mfBusPact.getBeginDate(), mfBusPact.getTerm()));
        } else if ("2".equals(mfBusPact.getTermType())) {// 2-天
            mfBusPact.setEndDate(DateUtil.addDay(mfBusPact.getBeginDate(), mfBusPact.getTerm()));
        }else {
        }
        ;
        getObjValue(formpact0008, mfBusPact);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formpact0008, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formpact0008, "overRate", "unit", rateUnit);
        this.changeFormProperty(formpact0008, "cmpdRate", "unit", rateUnit);
        String wkfAppId = mfBusPact.getWkfAppId();

        // 合同签约中的还款方式修改时应收产品设置中的还款方式控制
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
        JSONArray vouTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusPact.getVouType(), "VOU_TYPE");
        JSONObject json = new JSONObject();
        json.put("repayTypeMap", repayTypeMap);
        json.put("vouTypeMap", vouTypeMap);
        // 客户的账户列表
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
        mfCusBankAccManage.setUseFlag("1");
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

        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("pactId", pactId);
        model.addAttribute("coborrNum", coborrNum);
        model.addAttribute("formId", formId);
        model.addAttribute("rateUnit", rateUnit);
        model.addAttribute("wkfAppId", wkfAppId);
        model.addAttribute("ajaxData", ajaxData);

        model.addAttribute("formpact0008", formpact0008);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_InsertForApp";
    }

    /***
     * 新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert")
    public String insert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formpact0002 = formService.getFormData("pact0002");
        getFormValue(formpact0002);
        MfBusPact mfBusPact = new MfBusPact();
        setObjValue(formpact0002, mfBusPact);
        mfBusPactFeign.insert(mfBusPact);
        getObjValue(formpact0002, mfBusPact);
        this.addActionMessage(model, "保存成功");
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
        List<MfBusPact> mfBusPactList = (List<MfBusPact>) mfBusPactFeign.findByPage(ipage)
                .getResult();
        model.addAttribute("mfBusPactList", mfBusPactList);
        model.addAttribute("formpact0002", formpact0002);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_Insert";
    }

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String appId, String pactId, String fincId, String operable, String busEntrance, String examHisId, String compensatoryApproveId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        request.setAttribute("ifBizManger", "3");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 合同信息
        MfBusPact mfBusPact = new MfBusPact();
        if(StringUtil.isEmpty(pactId)){
            mfBusPact.setAppId(appId);
        }
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        dataMap.put("guaranteeType", "loan");
        // 金额格式化
        mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt() / 10000));
        if (mfBusPact.getUnrepayFincAmt() == null) {
            mfBusPact.setUnrepayFincAmt(0.00);
        }
        dataMap.put("pactBal", MathExtend.moneyStr(mfBusPact.getUnrepayFincAmt() / 10000));
        // 合同逾期天数
        int overDays = 0;
        dataMap.put("overDays", overDays);
        if (!StringUtil.isEmpty(mfBusPact.getEndDate())) {
            overDays = DateUtil.getIntervalDays(DateUtil.getStr(mfBusPact.getEndDate()),
                    DateUtil.getDate("yyyy-MM-dd"));
        }
        if (overDays >= 0) {
            dataMap.put("overDays", overDays);
        }
        // 期限单位格式化
        CodeUtils codeUtils = new CodeUtils();
        Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();

        pactId = mfBusPact.getPactId();
        // 检查历史信息
        MfExamineHis mfExamineHis = new MfExamineHis();
        mfExamineHis.setPactId(mfBusPact.getPactId());
        List<MfExamineHis> mfExamineHisList = mfExamineHisFeign.getMfExamineHisList(mfExamineHis);
        model.addAttribute("mfExamineHisList", mfExamineHisList);
        // 催收信息
        RecallBase recallBase = new RecallBase();
        recallBase.setPactId(mfBusPact.getPactId());
        recallBase.setCusNo(mfBusPact.getCusNo());
        List<RecallBase> recallBaseList = recInterfaceFeign.getRecallBaseList(recallBase);
        dataMap.put("hasRecallFlag", "0");
        if (recallBaseList != null && recallBaseList.size() > 0) {
            dataMap.put("hasRecallFlag", "1");
            recallBase.setRecallSts(BizPubParm.RECALL_STS_2);
            List<RecallBase> recallList = recInterfaceFeign.getRecallBaseList(recallBase);
            dataMap.put("recallingFlag", "0");
            if (recallList != null && recallList.size() > 0) {
                dataMap.put("recallingFlag", "1");
            }
        }
        model.addAttribute("recallBaseList", recallBaseList);
        JSONObject jb = JSONObject.fromObject(dataMap);
        model.addAttribute("recParam", jb);
        // 业务申请信息
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
        // 客户信息
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusPact.getCusNo());
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        String headImg = mfCusCustomer.getHeadImg();
        String ifUploadHead = mfCusCustomer.getIfUploadHead();
        String busModel = mfBusApply.getBusModel();
        // 关联企业：核心企业、资金机构、仓储机构等信息
        String wareHouseCusNo = "0";
        String fundCusNo = "0";
        String coreCusNo = "0";
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
        model.addAttribute("headImg", headImg);
        model.addAttribute("ifUploadHead", ifUploadHead);
        model.addAttribute("busModel", busModel);
        model.addAttribute("wareHouseCusNo", wareHouseCusNo);
        model.addAttribute("coreCusNo", coreCusNo);
        model.addAttribute("fundCusNo", fundCusNo);

        List<WkfApprovalOpinion> hisTaskList = wkfInterfaceFeign.getWkfTaskHisList(mfBusPact.getWkfAppId());
        JSONArray array = JSONArray.fromObject(hisTaskList);// hisTaskList.get(0).getState()
        JSONObject json = new JSONObject();
        json.put("hisTaskList", array);
        // 处理多业务
        MfBusApply mfBusApplyTmp = new MfBusApply();
        mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
        List<MfBusApply> mfBusApplyList = appInterfaceFeign.getMultiBusList(mfBusApplyTmp);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        if (StringUtil.isNotEmpty(fincId)) {
            mfBusFincApp.setFincId(fincId);
            mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        } else {
            mfBusFincApp.setAppId(mfBusPact.getAppId());
            mfBusFincApp = mfBusFincAppFeign.getByIdNewFinc(mfBusFincApp);
            if (mfBusFincApp != null) {
                fincId = mfBusFincApp.getFincId();
            }
        }

        String fincChidId = "";
        // 通过借据号获取子借据号
        if (mfBusFincApp != null) {
            MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
            mfBusFincAppChild.setFincId(mfBusFincApp.getFincId());// 借据id
            mfBusFincAppChild.setPutoutCount(mfBusFincApp.getPutoutCount());// 放款次数
            mfBusFincAppChild = mfBusFincAppChildFeign.getMfBusFincAppChildByInfo(mfBusFincAppChild);
            // 前台用childId处理
            if (mfBusFincAppChild != null) {
                fincChidId = mfBusFincAppChild.getFincChildId();
            }
        }
        model.addAttribute("mfBusApplyList", mfBusApplyList);
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("fincChidId", fincChidId);
        dataMap.put("moreCount", mfBusApplyList.size());
        String fiveFlag = ActionContext.getActionContext().getRequest().getParameter("fiveFlag");
        if (StringUtil.isNotEmpty(fiveFlag)) {
            dataMap.put("fiveFlag", fiveFlag);// 20170705
            // bug修改：有此标记只显示五级分类的审批流程和审批历史
            dataMap.put("fiveclassId", ActionContext.getActionContext().getRequest().getParameter("fiveclassId"));
        }
        // 动态业务视图参数封装
        Map<String, String> parmMap = new HashMap<String, String>();
        parmMap.put("pactId", pactId);
        parmMap.put("appId", mfBusPact.getAppId());
        parmMap.put("primaryAppId", mfBusApply.getPrimaryAppId());
        parmMap.put("operable", operable);
        parmMap.put("wkfAppId", mfBusPact.getWkfAppId());
        parmMap.put("pactSts", mfBusPact.getPactSts());
        parmMap.put("applyProcessId", mfBusApply.getApplyProcessId());
        parmMap.put("primaryApplyProcessId", mfBusApply.getPrimaryApplyProcessId());
        parmMap.put("pactProcessId", mfBusPact.getPactProcessId());
        parmMap.put("vouType", mfBusPact.getVouType());
        if (mfBusFincApp != null) {
            parmMap.put("fincId", mfBusFincApp.getFincId());
            parmMap.put("fincChidId", fincChidId);
            parmMap.put("fincSts", mfBusFincApp.getFincSts());
            parmMap.put("fincMainId", mfBusFincApp.getFincMainId());
        } else {
            parmMap.put("fincChidId", "");
            parmMap.put("fincSts", "");
            parmMap.put("fincMainId", "");
        }
        parmMap.put("fincProcessId", mfBusPact.getFincProcessId());
        parmMap.put("examHisId", StringUtil.isNotEmpty(examHisId) ? examHisId : "");
        //查询缴款通知书审批历史参数
        MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
        mfBusChargeFee.setAppId(appId);
        mfBusChargeFee.setPactId(pactId);
        mfBusChargeFee.setFeeChargeType("1");//合同视角默认展示初次收费
        mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
        if (mfBusChargeFee!=null) {
            parmMap.put("chargeId", mfBusChargeFee.getChargeId());
            parmMap.put("chargeSts", mfBusChargeFee.getAppSts());
        }
        MfBusAdvanceLoan mfBusAdvanceLoan = mfBusAdvanceLoanFeign.getByPactId(pactId);
        if(null != mfBusAdvanceLoan){
            parmMap.put("advanceLoanId", mfBusAdvanceLoan.getAdvanceLoanId());
            parmMap.put("advanceLoanSts", mfBusAdvanceLoan.getAppSts());
        }else{
            parmMap.put("advanceLoanId", "");
            parmMap.put("advanceLoanSts","");
        }
        parmMap.put("nodeNo", mfBusApply.getNodeNo());


        if (StringUtil.isNotEmpty(fiveFlag)) {
            model.addAttribute("fiveFlag", fiveFlag);// 20170705
            // bug修改：有此标记只显示五级分类的审批流程和审批历史
            model.addAttribute("fiveclassId", ActionContext.getActionContext().getRequest().getParameter("fiveclassId"));
        } else {
            model.addAttribute("fiveFlag", "");// 20170705 bug修改：有此标记只显示五级分类的审批流程和审批历史
            model.addAttribute("fiveclassId", "");
        }
        parmMap.put("cusNo", mfBusApply.getCusNo());
        parmMap.put("busModel", mfBusApply.getBusModel());
        if (StringUtil.isEmpty(busEntrance)) {
            busEntrance = "5";// 如果入口为空，为审批视图
        }
        parmMap.put("busEntrance", busEntrance);
        // 获得应收账款转让状态
        String transferSts = collateralInterfaceFeign.getReceivablesTransferSts(mfBusPact.getAppId());
        request.setAttribute("transferSts", transferSts);
        parmMap.put("appId", mfBusPact.getAppId());
        String generalClass = "bus";
        //if("2".equals(busEntrance) || !busEntrance.contains("pact")){
        if ("2".equals(busEntrance)) {
            busEntrance = "pact";
        } else if ("5".equals(busEntrance)) {
            busEntrance = "pact_approve";
        }else {
        }
        if("pact_approve".equals(busEntrance)){
            model.addAttribute("approvalNodeNo", WKF_NODE.contract_approval.getNodeNo());
        }
        String busClass = null;
        busClass = mfBusApply.getBusModel();

        //获取授信id
        String creditAppId = "";
        if (StringUtil.isNotEmpty(mfBusPact.getCreditPactNo())) {
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setPactNo(mfBusPact.getCreditPactNo());
            mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
            if (mfCusCreditContract != null) {
                creditAppId = mfCusCreditContract.getCreditAppId();
            }
            parmMap.put("creditAppId", creditAppId);
        }
        //展示代偿审批历史所用
        String compensatoryId = "";
        String appSts = "";
        String approveProcessId = "";
        if (compensatoryApproveId != null && StringUtil.isNotEmpty(compensatoryApproveId)) {

            MfBusCompensatoryApprove mfBusCompensatoryApprove = new MfBusCompensatoryApprove();
            mfBusCompensatoryApprove.setId(compensatoryApproveId);
            MfBusCompensatoryApprove busCompensatoryApprove = mfBusCompensatoryApproveFeign.getById(mfBusCompensatoryApprove);
            if (busCompensatoryApprove != null) {
                compensatoryId = busCompensatoryApprove.getId();
                MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
                mfBusCompensatoryApply.setCompensatoryId(compensatoryApproveId);
                mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getById(mfBusCompensatoryApply);
                if (mfBusCompensatoryApply != null) {
                    appSts = mfBusCompensatoryApply.getAppSts();
                    approveProcessId = mfBusCompensatoryApply.getApproveProcessId();
                }
            }
            model.addAttribute("compensatoryId", compensatoryId);
            model.addAttribute("compensatoryappSts", appSts);
            model.addAttribute("approveProcessId", approveProcessId);
        }

        //Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMapByAppId(parmMap);
        Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMap(generalClass, busClass, busEntrance, parmMap);
        // 判断客户表单信息是否允许编辑
        String query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(), BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
        //factor传的是"",到web端就变成了null
        if (query == null) {
            query = "";
        }
        //要件的权限
        String queryFile = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(), BizPubParm.FORM_EDIT_FLAG_FILE, User.getRegNo(request));
        if (queryFile == null) {
            queryFile = "";
        }

        //判断合同是否可以进行提前解约（1、额度可循环的未完结合同 2、合同下所有借据均已完结 ）
        String pactEndFlag = mfBusPactFeign.getPactEndFlag(mfBusPact);
        dataMap.put("pactEndFlag", pactEndFlag);


        //要件的展示方式：0块状1列表
        List<Object> parmDics = busiCacheInterface.getParmDicList("DOC_SHOW_TYPE");
        for (Object o : parmDics) {
            ParmDic p = (ParmDic) o;
            String docShowType = p.getOptCode();
            model.addAttribute("docShowType", docShowType);
        }

        model.addAttribute("json", json);
        model.addAttribute("fincChidId", fincChidId);
        model.addAttribute("fincId", fincId);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("transferSts", transferSts);
        model.addAttribute("parmMap", parmMap);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("busEntrance", busEntrance);
        model.addAttribute("mfCusCustomer", mfCusCustomer);
        model.addAttribute("query", query);
        model.addAttribute("queryFile", queryFile);
        model.addAttribute("mfBusApply", mfBusApply);
        //是否展开审批历史
        String OPEN_APPROVE_HIS = new CodeUtils().getSingleValByKey("OPEN_APPROVE_HIS");
        model.addAttribute("OPEN_APPROVE_HIS", OPEN_APPROVE_HIS);
        if (busViewMap.isEmpty()) {
            return "/component/pact/MfBusPact_SignDetail";
        } else {
            dataMap.putAll(busViewMap);

            model.addAttribute("dataMap", dataMap);
            return "/component/pact/MfBusPact_DynaDetail";
        }
    }

    /**
     * 方法描述： 获取合同信息以及最新的借据信息
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-10-20 下午1:56:29
     */
    @RequestMapping(value = "/getPactFincDetailInfoAjax")
    @ResponseBody
    public Map<String, Object> getPactFincDetailInfoAjax(String appId,String pactId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact.setPactId(pactId);
        // 获取合同详情信息
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        dataMap.put("mfBusPact", mfBusPact);
        dataMap.put("pactSts", mfBusPact.getPactSts());
        dataMap.put("busModel", mfBusPact.getBusModel());
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setAppId(mfBusPact.getAppId());
        mfBusFincApp = mfBusFincAppFeign.getByIdNewFinc(mfBusFincApp);
        if (mfBusFincApp != null) {
            dataMap.put("mfBusFincApp", mfBusFincApp);
            dataMap.put("fincSts", mfBusFincApp.getFincSts());
        } else {
            dataMap.put("fincSts", "");
        }
        //查询缴款通知书审批人员
        MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
        mfBusChargeFee.setAppId(mfBusPact.getAppId());
        mfBusChargeFee.setPactId(mfBusPact.getPactId());
        mfBusChargeFee.setFeeChargeType("1");
        mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
        if (mfBusChargeFee != null) {
            dataMap.put("approveName", mfBusChargeFee.getApprovePartName());
        }
        return dataMap;
    }

    /**
     * 查询 通过借据号查询 合同信息(贷后使用)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPactFincById")
    public String getPactFincById(Model model, String appId, String pactId, String fincId, String busEntrance,
                                  String operable, String isShowBtn) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        request.setAttribute("ifBizManger", "3");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 合同信息
        MfBusPact mfBusPact = new MfBusPact();
        if(StringUtil.isEmpty(pactId)){
            mfBusPact.setAppId(appId);
        }
        // 判断详情页面还款按钮的显隐
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        if(mfBusFincApp!=null&&StringUtil.isEmpty(pactId)&&StringUtil.isEmpty(appId)){
            pactId =mfBusFincApp.getPactId();
            appId = mfBusFincApp.getAppId();
        }
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);

        // 根据申请appId获取系统参数信息
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        String cmpdRateType = mfBusAppKind.getCmpdRateType();
        //展期前利息是否全部收取：0-不收取、1-收取
        String extenLixiType = mfBusAppKind.getExtenLixiType();
        if (cmpdRateType == null) {
            cmpdRateType = "0";
        }
        model.addAttribute("cmpdRateType", cmpdRateType);
        dataMap.put("guaranteeType", "loan");
        FormData formpact0004 = null;
        if (mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_5)) {
            formpact0004 = formService.getFormData("pact0004bl");
        } else if (mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_14)
                || mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_15)
                || mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_17)) {
            formpact0004 = formService.getFormData("pact0004db");
            dataMap.put("guaranteeType", "gua");
        } else {
            formpact0004 = formService.getFormData("pact0004");
        }

        getFormValue(formpact0004);

        // 金额格式化
        mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt() / 10000));
        if (mfBusPact.getUnrepayFincAmt() == null) {
            mfBusPact.setUnrepayFincAmt(0.00);
        }
        dataMap.put("pactBal", MathExtend.moneyStr(mfBusPact.getUnrepayFincAmt() / 10000));
        // 合同逾期天数
        int overDays = 0;
        dataMap.put("overDays", overDays);
        if (DateUtil.compareTo(mfBusPact.getEndDate(), DateUtil.getDate()) > 0) {
            overDays = DateUtil.getIntervalDays(DateUtil.getStr(mfBusPact.getEndDate()), DateUtil.getDate("yyyyMMdd"));
            if (overDays >= 0) {
                dataMap.put("overDays", overDays);
            }
        }
        getObjValue(formpact0004, mfBusPact);
        // 期限单位格式化
        CodeUtils codeUtils = new CodeUtils();
        Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();
        this.changeFormProperty(formpact0004, "term", "unit", termUnit);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formpact0004, "fincRate", "unit", rateUnit);
        pactId = mfBusPact.getPactId();
        // 检查历史信息
        MfExamineHis mfExamineHis = new MfExamineHis();
        mfExamineHis.setPactId(mfBusPact.getPactId());
        List<MfExamineHis> mfExamineHisList = mfExamineHisFeign.getMfExamineHisList(mfExamineHis);
        model.addAttribute("rateUnit", rateUnit);
        model.addAttribute("pactId", pactId);
        model.addAttribute("mfExamineHisList", mfExamineHisList);
        // 催收信息
        RecallBase recallBase = new RecallBase();
        recallBase.setPactId(mfBusPact.getPactId());
        recallBase.setCusNo(mfBusPact.getCusNo());
        List<RecallBase> recallBaseList = recInterfaceFeign.getRecallBaseList(recallBase);
        dataMap.put("hasRecallFlag", "0");
        if (recallBaseList != null && recallBaseList.size() > 0) {
            dataMap.put("hasRecallFlag", "1");
            recallBase.setRecallSts(BizPubParm.RECALL_STS_2);
            List<RecallBase> recallList = recInterfaceFeign.getRecallBaseList(recallBase);
            dataMap.put("recallingFlag", "0");
            if (recallList != null && recallList.size() > 0) {
                dataMap.put("recallingFlag", "1");
            }
        }
        model.addAttribute("recallBaseList", recallBaseList);
        JSONObject jb = JSONObject.fromObject(dataMap);
        request.setAttribute("recParam", jb);
        // 业务申请信息
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
        // 客户信息
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusPact.getCusNo());
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        String headImg = mfCusCustomer.getHeadImg();
        String ifUploadHead = mfCusCustomer.getIfUploadHead();
        String busModel = mfBusApply.getBusModel();
        String wareHouseCusNo = "0";
        String coreCusNo = "0";
        String fundCusNo = "0";
        // 关联企业：核心企业、资金机构、仓储机构等信息
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
        model.addAttribute("headImg", headImg);
        model.addAttribute("ifUploadHead", ifUploadHead);
        model.addAttribute("busModel", busModel);
        model.addAttribute("wareHouseCusNo", wareHouseCusNo);
        model.addAttribute("coreCusNo", coreCusNo);
        model.addAttribute("fundCusNo", fundCusNo);
        // List<WkfApprovalOpinion> hisTaskList =
        // wkfInterfaceFeign.getWkfTaskHisList(mfBusPact.getWkfAppId());
        // 调整贷后页面 不按照业务流程 出相关页面
        List<WkfApprovalOpinion> hisTaskList = new ArrayList<WkfApprovalOpinion>();
        JSONArray array = JSONArray.fromObject(hisTaskList);// hisTaskList.get(0).getState()
        JSONObject json = new JSONObject();
        json.put("hisTaskList", array);


        /***展期前收息 按钮是否能够操作 start *****/
        String extenLiXiOperateFlag = "1";// 按钮是否能够操作 0能收取 1不能收取
        //展期前利息收取按钮是否能够操作（收取利息）：0-能收取、1-不能收取
        String extenLixiButtonType = mfBusFincApp.getExtenLixiButtonType();
        //展期业务状态0未展期1展期中2展期完成
        String extenSts = mfBusFincApp.getExtenSts();
        //展期中 并且展期前利息是全部收取 且 未收取
        if (BizPubParm.EXTENSTS1.equals(extenSts) && BizPubParm.EXTENLIXITYPE1.equals(extenLixiType) && BizPubParm.EXTENLIXIBUTTONTYPE0.equals(extenLixiButtonType)) {
            extenLiXiOperateFlag = "0";//能收取
        }
        model.addAttribute("extenLiXiOperateFlag", extenLiXiOperateFlag);//展期前利息收取按钮是否能够操作 0能收取 1不能收取
        /***展期前收息 按钮是否能够操作 end *****/
        mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
        FormData formfincapp0002 = formService.getFormData("fincapp0002");// 放款申请信息
        getObjValue(formfincapp0002, mfBusFincApp);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        this.changeFormProperty(formfincapp0002, "fincRate", "unit", rateUnit);
        // 获取收益计划 信息
        MfRepayPlan mfRepayPlan = new MfRepayPlan();
        mfRepayPlan.setFincId(fincId);
        List<MfRepayPlan> mfRepayPlanList = calcRepaymentInterfaceFeign.getMfRepayPlanList(mfRepayPlan);
        String ifShowRepayHistory = "0";
        List<MfRepayHistory> mfRepayHistoryList = null;
        // 还款历史
        if (mfBusFincApp != null) {
            MfRepayHistory mfRepayHistory = new MfRepayHistory();
            mfRepayHistory.setFincId(mfBusFincApp.getFincId());
            mfRepayHistoryList = mfRepayHistoryFeign.getList(mfRepayHistory);
            if (mfRepayHistoryList.size() > 0) {
                ifShowRepayHistory = "1";
            } else {
                ifShowRepayHistory = "0";
            }
        }
        // 处理多业务
        MfBusApply mfBusApplyTmp = new MfBusApply();
        mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
        List<MfBusApply> mfBusApplyList = appInterfaceFeign.getMultiBusList(mfBusApplyTmp);
        dataMap.put("moreCount", mfBusApplyList.size());
        String operateflag = "";
        if (mfBusFincApp != null && !"5".equals(mfBusPact.getPutoutSts())) {
            String fincSts = mfBusFincApp.getFincSts();
            if (fincSts.equals(BizPubParm.FINC_STS_AGREE) || fincSts.equals(BizPubParm.FINC_STS_FINISH)
                    || fincSts.equals(BizPubParm.FINC_STS_REPAY)) {
                // 还款操作 按钮是否能用标记处理
                operateflag = mfRepayHistoryFeign.getRepaymentOperateFlag(mfBusFincApp);

            }
        }
        //判断能否操作尾款结付按钮
        String tailRepayFlag = "0";
        if (StringUtil.isNotEmpty(fincId)) {
            //根据借据号获取账款结余
            MfBusReceBal mfBusReceBal = new MfBusReceBal();
            mfBusReceBal.setFincId(fincId);
            mfBusReceBal = mfBusReceBalFeign.getById(mfBusReceBal);
            if (mfBusReceBal != null) {
                if (mfBusReceBal.getBalAmt() != null && mfBusReceBal.getBalAmt() > 0) {
                    tailRepayFlag = "1";
                }
            }
        }
        model.addAttribute("tailRepayFlag", tailRepayFlag);
        model.addAttribute("mfRepayHistoryList", mfRepayPlanList);
        model.addAttribute("mfRepayHistoryList", mfRepayHistoryList);
        model.addAttribute("ifShowRepayHistory", ifShowRepayHistory);
        model.addAttribute("mfBusApplyList", mfBusApplyList);
        model.addAttribute("operateflag", operateflag);
        // 动态业务视图参数封装
        Map<String, String> parmMap = new HashMap<String, String>();
        parmMap.put("fincId", fincId);
        parmMap.put("pactId", mfBusPact.getPactId());
        parmMap.put("pactProcessId", mfBusPact.getPactProcessId());
        parmMap.put("pactSts", mfBusPact.getPactSts());
        parmMap.put("appId", mfBusPact.getAppId());
        parmMap.put("operable", operable);
        parmMap.put("wkfRepayId", mfBusFincApp.getWkfRepayId());
        parmMap.put("fincSts", mfBusFincApp.getFincSts());
        parmMap.put("wkfAppId", mfBusPact.getWkfAppId());
        parmMap.put("pactSts", mfBusPact.getPactSts());
        parmMap.put("cusNo", mfBusApply.getCusNo());
        parmMap.put("busModel", mfBusApply.getBusModel());
        parmMap.put("vouType", mfBusApply.getVouType());
        String canRepay = mfBusFincApp.getCanRepay();
        parmMap.put("canRepay", mfBusFincApp.getCanRepay());
        parmMap.put("cmpdRateType", cmpdRateType);
        if (StringUtil.isEmpty(busEntrance)) {
            busEntrance = "6";// 如果入口为空，为审批视图
        }
        parmMap.put("busEntrance", busEntrance);
        parmMap.put("appId", appId);
        // 通过借据号获取子借据号
        MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
        mfBusFincAppChild.setFincId(mfBusFincApp.getFincId());// 借据id
        mfBusFincAppChild.setPutoutCount(mfBusFincApp.getPutoutCount());// 放款次数
        mfBusFincAppChild = pactInterfaceFeign.getMfBusFincAppChildByInfo(mfBusFincAppChild);
        if (mfBusFincAppChild != null) {
            parmMap.put("fincChidId", mfBusFincAppChild.getFincChildId());
        }
        parmMap.put("fincProcessId", mfBusPact.getFincProcessId());
        String generalClass = "bus";
        if ("3".equals(busEntrance)) {
            busEntrance = "finc";
        } else if ("6".equals(busEntrance)) {
            busEntrance = "finc_approve";
        }else {
        }
        //TODO 设置busClass;
        String busClass = null;
        busClass = mfBusApply.getBusModel();
        //获取授信id
        String creditAppId = "";
        if (StringUtil.isNotEmpty(mfBusPact.getCreditPactNo())) {
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setPactNo(mfBusPact.getCreditPactNo());
            mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
            if (mfCusCreditContract != null) {
                creditAppId = mfCusCreditContract.getCreditAppId();
            }
            parmMap.put("creditAppId", creditAppId);
        }
        String query = cusInterfaceFeign.validateCusFormModify(mfBusPact.getCusNo(), mfBusPact.getAppId(), BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
        //factor传的是"",到web端就变成了null
        if (query == null) {
            query = "";
        }
        //要件的权限
        String queryFile = cusInterfaceFeign.validateCusFormModify(mfBusPact.getCusNo(), mfBusPact.getAppId(), BizPubParm.FORM_EDIT_FLAG_FILE, User.getRegNo(request));
        //factor传的是"",到web端就变成了null
        if (queryFile == null) {
            queryFile = "";
        }
        //展期审批状态 处于审批节点1，非审批节点0
        String extenApprove = "0";
        //展期业务状态 实时的  0未展期1展期中2展期完成
        if ("1".equals(extenSts)) {
            //展期申请表中取最新的一条展期信息
            MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
            mfBusExtensionApply.setFincId(fincId);
            mfBusExtensionApply = mfBusExtensionApplyFeign.getNewExtensionByFincId(mfBusExtensionApply);
            parmMap.put("extensionApplyId", mfBusExtensionApply.getExtensionApplyId());
            if (BizPubParm.EXTENSION_BUS_STAGE_APPROVE.equals(mfBusExtensionApply.getExtenBusStage())) {
                extenApprove = "1";
            }
            //展期申请ID
            model.addAttribute("extensionApplyId", mfBusExtensionApply.getExtensionApplyId());
        }
        //查询缴款通知书审批历史参数
        MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
        mfBusChargeFee.setAppId(appId);
        mfBusChargeFee.setPactId(pactId);
        mfBusChargeFee.setFeeChargeType("1");//合同视角默认展示初次收费
        mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
        if (mfBusChargeFee!=null) {
            parmMap.put("chargeId", mfBusChargeFee.getChargeId());
            parmMap.put("chargeSts", mfBusChargeFee.getAppSts());
        }
        //Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMapByAppId(parmMap);
        Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMap(generalClass, busClass, busEntrance, parmMap);

        //要件的展示方式：0块状1列表
        List<Object> parmDics = busiCacheInterface.getParmDicList("DOC_SHOW_TYPE");
        for (Object o : parmDics) {
            ParmDic p = (ParmDic) o;
            String docShowType = p.getOptCode();
            model.addAttribute("docShowType", docShowType);
        }

        //处理 还款计划是否可编辑问题
        Map<String, Object> mapEditorRepayplan = mfBusPactFeign.doIsUpdatePrePlan(fincId);
        //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
        String mfBusEditorRepayplanFlag = (String) mapEditorRepayplan.get("mfBusEditorRepayplanFlag");
        model.addAttribute("mfBusEditorRepayplanFlag", mfBusEditorRepayplanFlag);
        model.addAttribute("canRepay", canRepay);
        model.addAttribute("extenApprove", extenApprove);
        model.addAttribute("extenSts", extenSts);
        model.addAttribute("json", json);
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("busEntrance", busEntrance);
        model.addAttribute("parmMap", parmMap);
        model.addAttribute("formfincapp0002", formfincapp0002);
        model.addAttribute("formpact0004", formpact0004);
        model.addAttribute("operable", operable);
        model.addAttribute("query", query);
        model.addAttribute("queryFile", queryFile);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        model.addAttribute("isShowBtn", isShowBtn);
        //String projectName =ymlConfig.getSysParams().get("sys.project.name");
        //request.setAttribute("projectName", projectName);

        if (busViewMap.isEmpty()) {
            return "/component/pact/MfBusPact_Detail";
        } else {
            dataMap.putAll(busViewMap);
            model.addAttribute("dataMap", dataMap);
            return "/component/pact/MfBusFinc_DynaDetail";
        }

    }

    /**
     * 根据展期申请ID  获取展期的申请信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPactFincByIdDetail")
    public String getPactFincByIdDetail(Model model, String appId, String pactId, String fincId, String extensionId, String busEntrance,
                                        String operable) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        request.setAttribute("ifBizManger", "3");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 合同信息
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);

        // 根据申请appId获取系统参数信息
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        String cmpdRateType = mfBusAppKind.getCmpdRateType();
        //展期前利息是否全部收取：0-不收取、1-收取
        String extenLixiType = mfBusAppKind.getExtenLixiType();
        if (cmpdRateType == null) {
            cmpdRateType = "0";
        }
        model.addAttribute("cmpdRateType", cmpdRateType);
        dataMap.put("guaranteeType", "loan");
        FormData formpact0004 = null;
        if (mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_5)) {
            formpact0004 = formService.getFormData("pact0004bl");
        } else if (mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_14)
                || mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_15)
                || mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_17)) {
            formpact0004 = formService.getFormData("pact0004db");
            dataMap.put("guaranteeType", "gua");
        } else {
            formpact0004 = formService.getFormData("pact0004");
        }

        String PROJECT_ID = new CodeUtils().getSingleValByKey("PROJECT_ID");// 项目标识
        getFormValue(formpact0004);

        // 金额格式化
        mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt() / 10000));
        if (mfBusPact.getUnrepayFincAmt() == null) {
            mfBusPact.setUnrepayFincAmt(0.00);
        }
        dataMap.put("pactBal", MathExtend.moneyStr(mfBusPact.getUnrepayFincAmt() / 10000));
        // 合同逾期天数
        int overDays = 0;
        dataMap.put("overDays", overDays);
        if (DateUtil.compareTo(mfBusPact.getEndDate(), DateUtil.getDate()) > 0) {
            overDays = DateUtil.getIntervalDays(DateUtil.getStr(mfBusPact.getEndDate()), DateUtil.getDate("yyyyMMdd"));
            if (overDays >= 0) {
                dataMap.put("overDays", overDays);
            }
        }
        getObjValue(formpact0004, mfBusPact);
        // 期限单位格式化
        CodeUtils codeUtils = new CodeUtils();
        Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();
        this.changeFormProperty(formpact0004, "term", "unit", termUnit);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formpact0004, "fincRate", "unit", rateUnit);
        pactId = mfBusPact.getPactId();
        // 检查历史信息
        MfExamineHis mfExamineHis = new MfExamineHis();
        mfExamineHis.setPactId(mfBusPact.getPactId());
        List<MfExamineHis> mfExamineHisList = mfExamineHisFeign.getMfExamineHisList(mfExamineHis);
        model.addAttribute("rateUnit", rateUnit);
        model.addAttribute("pactId", pactId);
        model.addAttribute("mfExamineHisList", mfExamineHisList);
        // 催收信息
        RecallBase recallBase = new RecallBase();
        recallBase.setPactId(mfBusPact.getPactId());
        recallBase.setCusNo(mfBusPact.getCusNo());
        List<RecallBase> recallBaseList = recInterfaceFeign.getRecallBaseList(recallBase);
        dataMap.put("hasRecallFlag", "0");
        if (recallBaseList != null && recallBaseList.size() > 0) {
            dataMap.put("hasRecallFlag", "1");
            recallBase.setRecallSts(BizPubParm.RECALL_STS_2);
            List<RecallBase> recallList = recInterfaceFeign.getRecallBaseList(recallBase);
            dataMap.put("recallingFlag", "0");
            if (recallList != null && recallList.size() > 0) {
                dataMap.put("recallingFlag", "1");
            }
        }
        model.addAttribute("recallBaseList", recallBaseList);
        JSONObject jb = JSONObject.fromObject(dataMap);
        request.setAttribute("recParam", jb);
        // 业务申请信息
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
        // 客户信息
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusPact.getCusNo());
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        String headImg = mfCusCustomer.getHeadImg();
        String ifUploadHead = mfCusCustomer.getIfUploadHead();
        String busModel = mfBusApply.getBusModel();
        String wareHouseCusNo = "0";
        String coreCusNo = "0";
        String fundCusNo = "0";
        // 关联企业：核心企业、资金机构、仓储机构等信息
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
        model.addAttribute("headImg", headImg);
        model.addAttribute("ifUploadHead", ifUploadHead);
        model.addAttribute("busModel", busModel);
        model.addAttribute("wareHouseCusNo", wareHouseCusNo);
        model.addAttribute("coreCusNo", coreCusNo);
        model.addAttribute("fundCusNo", fundCusNo);
        // List<WkfApprovalOpinion> hisTaskList =
        // wkfInterfaceFeign.getWkfTaskHisList(mfBusPact.getWkfAppId());
        // 调整贷后页面 不按照业务流程 出相关页面
        List<WkfApprovalOpinion> hisTaskList = new ArrayList<WkfApprovalOpinion>();
        JSONArray array = JSONArray.fromObject(hisTaskList);// hisTaskList.get(0).getState()
        JSONObject json = new JSONObject();
        json.put("hisTaskList", array);

        // 判断详情页面还款按钮的显隐
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        /***展期前收息 按钮是否能够操作 start *****/
        String extenLiXiOperateFlag = "1";// 按钮是否能够操作 0能收取 1不能收取
        //展期前利息收取按钮是否能够操作（收取利息）：0-能收取、1-不能收取
        String extenLixiButtonType = mfBusFincApp.getExtenLixiButtonType();
        //展期业务状态0未展期1展期中2展期完成
        String extenSts = mfBusFincApp.getExtenSts();
        //展期中 并且展期前利息是全部收取 且 未收取
        if (BizPubParm.EXTENSTS1.equals(extenSts) && BizPubParm.EXTENLIXITYPE1.equals(extenLixiType) && BizPubParm.EXTENLIXIBUTTONTYPE0.equals(extenLixiButtonType)) {
            extenLiXiOperateFlag = "0";//能收取
        }
        model.addAttribute("extenLiXiOperateFlag", extenLiXiOperateFlag);//展期前利息收取按钮是否能够操作 0能收取 1不能收取
        /***展期前收息 按钮是否能够操作 end *****/
        mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
        FormData formfincapp0002 = formService.getFormData("fincapp0002");// 放款申请信息
        getObjValue(formfincapp0002, mfBusFincApp);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        this.changeFormProperty(formfincapp0002, "fincRate", "unit", rateUnit);
        // 获取收益计划 信息
        MfRepayPlan mfRepayPlan = new MfRepayPlan();
        mfRepayPlan.setFincId(fincId);
        List<MfRepayPlan> mfRepayPlanList = calcRepaymentInterfaceFeign.getMfRepayPlanList(mfRepayPlan);

        if (mfRepayPlanList != null && mfRepayPlanList.size() > 0) {
            int j = 0;
            for (int i = 0; i < mfRepayPlanList.size(); i++) {
                String outFlag = mfRepayPlanList.get(i).getOutFlag();
                if (!outFlag.equals("1")) {
                    j++;
                }
            }
        }

        String ifShowRepayHistory = "0";
        List<MfRepayHistory> mfRepayHistoryList = null;
        // 还款历史
        if (mfBusFincApp != null) {
            MfRepayHistory mfRepayHistory = new MfRepayHistory();
            mfRepayHistory.setFincId(mfBusFincApp.getFincId());
            mfRepayHistoryList = mfRepayHistoryFeign.getList(mfRepayHistory);
            if (mfRepayHistoryList.size() > 0) {
                ifShowRepayHistory = "1";
            } else {
                ifShowRepayHistory = "0";
            }
        }
        // 处理多业务
        MfBusApply mfBusApplyTmp = new MfBusApply();
        mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
        List<MfBusApply> mfBusApplyList = appInterfaceFeign.getMultiBusList(mfBusApplyTmp);
        dataMap.put("moreCount", mfBusApplyList.size());
        String operateflag = "";
        if (mfBusFincApp != null && !"5".equals(mfBusPact.getPutoutSts())) {
            String fincSts = mfBusFincApp.getFincSts();
            if (fincSts.equals(BizPubParm.FINC_STS_AGREE) || fincSts.equals(BizPubParm.FINC_STS_FINISH)
                    || fincSts.equals(BizPubParm.FINC_STS_REPAY)) {
                // 还款操作 按钮是否能用标记处理
                operateflag = mfRepayHistoryFeign.getRepaymentOperateFlag(mfBusFincApp);

            }
        }
        //判断能否操作尾款结付按钮
        String tailRepayFlag = "0";
        if (StringUtil.isNotEmpty(fincId)) {
            //根据借据号获取账款结余
            MfBusReceBal mfBusReceBal = new MfBusReceBal();
            mfBusReceBal.setFincId(fincId);
            mfBusReceBal = mfBusReceBalFeign.getById(mfBusReceBal);
            if (mfBusReceBal != null) {
                if (mfBusReceBal.getBalAmt() != null && mfBusReceBal.getBalAmt() > 0) {
                    tailRepayFlag = "1";
                }
            }
        }
        model.addAttribute("tailRepayFlag", tailRepayFlag);
        model.addAttribute("mfRepayHistoryList", mfRepayPlanList);
        model.addAttribute("mfRepayHistoryList", mfRepayHistoryList);
        model.addAttribute("ifShowRepayHistory", ifShowRepayHistory);
        model.addAttribute("mfBusApplyList", mfBusApplyList);
        model.addAttribute("operateflag", operateflag);
        // 动态业务视图参数封装
        Map<String, String> parmMap = new HashMap<String, String>();
        parmMap.put("fincId", fincId);
        parmMap.put("pactId", mfBusPact.getPactId());
        parmMap.put("appId", mfBusPact.getAppId());
        parmMap.put("operable", operable);
        parmMap.put("wkfRepayId", mfBusFincApp.getWkfRepayId());
        parmMap.put("fincSts", mfBusFincApp.getFincSts());
        parmMap.put("wkfAppId", mfBusPact.getWkfAppId());
        parmMap.put("pactSts", mfBusPact.getPactSts());
        parmMap.put("cusNo", mfBusApply.getCusNo());
        parmMap.put("busModel", mfBusApply.getBusModel());
        parmMap.put("vouType", mfBusApply.getVouType());
        String canRepay = mfBusFincApp.getCanRepay();
        parmMap.put("canRepay", mfBusFincApp.getCanRepay());
        parmMap.put("cmpdRateType", cmpdRateType);
        if (StringUtil.isEmpty(busEntrance)) {
            busEntrance = "6";// 如果入口为空，为审批视图
        }
        parmMap.put("busEntrance", busEntrance);
        parmMap.put("appId", appId);
        // 通过借据号获取子借据号
        MfBusFincAppChild mfBusFincAppChild = new MfBusFincAppChild();
        mfBusFincAppChild.setFincId(mfBusFincApp.getFincId());// 借据id
        mfBusFincAppChild.setPutoutCount(mfBusFincApp.getPutoutCount());// 放款次数
        mfBusFincAppChild = pactInterfaceFeign.getMfBusFincAppChildByInfo(mfBusFincAppChild);
        parmMap.put("fincChidId", mfBusFincAppChild.getFincChildId());
        parmMap.put("fincProcessId", mfBusPact.getFincProcessId());
        String generalClass = "bus";
        if ("3".equals(busEntrance)) {
            busEntrance = "finc_extension";
        }
        //TODO 设置busClass;
        String busClass = null;
        busClass = mfBusApply.getBusModel();
        //获取授信id
        String creditAppId = "";
        if (StringUtil.isNotEmpty(mfBusPact.getCreditPactNo())) {
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setPactNo(mfBusPact.getCreditPactNo());
            mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
            if (mfCusCreditContract != null) {
                creditAppId = mfCusCreditContract.getCreditAppId();
            }
            parmMap.put("creditAppId", creditAppId);
        }
        String query = cusInterfaceFeign.validateCusFormModify(mfBusPact.getCusNo(), mfBusPact.getAppId(), BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
        //factor传的是"",到web端就变成了null
        if (query == null) {
            query = "";
        }

        //要件的权限
        String queryFile = cusInterfaceFeign.validateCusFormModify(mfBusPact.getCusNo(), mfBusPact.getAppId(), BizPubParm.FORM_EDIT_FLAG_FILE, User.getRegNo(request));
        //factor传的是"",到web端就变成了null
        if (queryFile == null) {
            queryFile = "";
        }


        //Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMapByAppId(parmMap);
        Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMap(generalClass, busClass, busEntrance, parmMap);

        //要件的展示方式：0块状1列表
        List<Object> parmDics = busiCacheInterface.getParmDicList("DOC_SHOW_TYPE");
        for (Object o : parmDics) {
            ParmDic p = (ParmDic) o;
            String docShowType = p.getOptCode();
            model.addAttribute("docShowType", docShowType);
        }
        //查询产品的所属机构和协办机构
        String ext1 = "", ext3 = "";


        model.addAttribute("canRepay", canRepay);

        model.addAttribute("extenSts", extenSts);
        model.addAttribute("json", json);
        model.addAttribute("mfBusFincApp", mfBusFincApp);
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("busEntrance", busEntrance);
        model.addAttribute("parmMap", parmMap);
        model.addAttribute("formfincapp0002", formfincapp0002);
        model.addAttribute("formpact0004", formpact0004);
        model.addAttribute("operable", operable);
        model.addAttribute("query", query);
        model.addAttribute("queryFile", queryFile);

        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));

        MfBusExtensionApply mfBusExtensionApplyNew = new MfBusExtensionApply();
        mfBusExtensionApplyNew.setFincId(fincId);
        dataMap = mfBusExtensionApplyFeign.getExtensionDetailInfo(mfBusExtensionApplyNew);
//		 mfBusExtensionApply = (MfBusExtensionApply) dataMap.get("extensionApply");
        mfBusExtensionApplyNew = (MfBusExtensionApply) JsonStrHandling.handlingStrToBean(dataMap.get("extensionApply"), MfBusExtensionApply.class);

        if (!extensionId.equals(mfBusExtensionApplyNew.getExtensionApplyId())) {
            model.addAttribute("queryExtensionFile", "query");
        }


        //处理 还款计划是否可编辑问题
        Map<String, Object> mapEditorRepayplan = mfBusPactFeign.doIsUpdatePrePlan(fincId);
        //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
        String mfBusEditorRepayplanFlag = (String) mapEditorRepayplan.get("mfBusEditorRepayplanFlag");
        model.addAttribute("mfBusEditorRepayplanFlag", mfBusEditorRepayplanFlag);
        model.addAttribute("extensionId", extensionId);

        dataMap.putAll(busViewMap);
        model.addAttribute("dataMap", dataMap);
        return "/component/pact/MfBusFinc_DynaDetailExtension";
    }


    /**
     * 查询 还款后 回调方法
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPactFincByRepay")
    @ResponseBody
    public Map<String, Object> getPactFincByRepay(Model model, String fincId) throws Exception {
        ActionContext.initialize(request, response);
        request.setAttribute("ifBizManger", "3");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 判断详情页面还款按钮的显隐
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        // 获取收益计划信息
        MfRepayPlan mfRepayPlan = new MfRepayPlan();
        mfRepayPlan.setFincId(fincId);
        List<MfRepayPlan> mfRepayPlanList = calcRepaymentInterfaceFeign.getMfRepayPlanList(mfRepayPlan);
        // 还款历史
        List<MfRepayHistory> mfRepayHistoryList = null;
        if (mfBusFincApp != null) {
            MfRepayHistory mfRepayHistory = new MfRepayHistory();
            mfRepayHistory.setFincId(mfBusFincApp.getFincId());
            mfRepayHistoryList = mfRepayHistoryFeign.getList(mfRepayHistory);
        }
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtmlRepayPlan = jtu.getJsonStr("tablerepayplan0003", "tableTag", mfRepayPlanList, null, true);
        String tableHtmlRepayHistory = jtu.getJsonStr("tablerepayhis0001", "tableTag", mfRepayHistoryList, null, true);

        dataMap.put("tableHtmlRepayPlan", tableHtmlRepayPlan);
        dataMap.put("tableHtmlRepayHistory", tableHtmlRepayHistory);
        dataMap.put("fincSts", mfBusFincApp.getFincSts());
        dataMap.put("extenLiXiOperateFlag", mfBusFincApp.getExtenLixiButtonType());
        return dataMap;
    }

    /**
     * 查询合同信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdApprov")
    public String getByIdApprov(Model model, String appId, String pactId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        // 合同信息

        MfBusPact mfBusPact = new MfBusPact();
        if (pactId == null) {
            mfBusPact.setAppId(appId);
        }
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        String busModel = mfBusPact.getBusModel();
        FormData formpact0004 = null;
        if (busModel.equals(BizPubParm.BUS_MODEL_5)) {
            formpact0004 = formService.getFormData("pact0004bl");
        } else {
            formpact0004 = formService.getFormData("pact0004");
        }

        getFormValue(formpact0004);
        // 金额格式化
        mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getUsableFincAmt() / 10000));
        getObjValue(formpact0004, mfBusPact);

        pactId = mfBusPact.getPactId();

        String relNo = "cusNo-" + mfBusPact.getCusNo();
        List<BizInfoChange> bizInfoChangeList = bizInterfaceFeign.getTopFive(relNo);

        List<WkfApprovalOpinion> hisTaskList = wkfInterfaceFeign.getWkfTaskHisList(mfBusPact.getWkfAppId());
        JSONArray array = JSONArray.fromObject(hisTaskList);// hisTaskList.get(0).getState()
        JSONObject json = new JSONObject();
        json.put("hisTaskList", array);

        model.addAttribute("busModel", busModel);
        model.addAttribute("bizInfoChangeList", bizInfoChangeList);
        model.addAttribute("formpact0004", formpact0004);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPactApprov_Detail";
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public String delete(Model model, String pactId) throws Exception {
        ActionContext.initialize(request, response);
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPactFeign.delete(mfBusPact);
        return getListPage(model);
    }

    /**
     * 新增校验
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/validateInsert")
    public void validateInsert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formpact0002 = formService.getFormData("pact0002");
        getFormValue(formpact0002);
        boolean validateFlag = this.validateFormData(formpact0002);
    }

    /**
     * 修改校验
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/validateUpdate")
    public void validateUpdate(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formpact0002 = formService.getFormData("pact0002");
        getFormValue(formpact0002);
        boolean validateFlag = this.validateFormData(formpact0002);
    }

    /**
     * 方法描述：合同审批流程提交
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2016-6-7 下午5:58:30
     */

    @RequestMapping(value = "/processSubmit")
    public String processSubmit(Model model, String appId, String pactId) throws Exception {
        ActionContext.initialize(request, response);
        // 提交合同审批流程
        MfBusPact mfBusPactTmp = mfBusPactFeign.getByAppId(appId);
        String regName = User.getRegName(request);
        String orgNo = User.getOrgNo(request);
        MfBusPact mfBusPact = mfBusPactWkfFeign.submitProcess(mfBusPactTmp.getPactId(), null, regName, orgNo);

        Map<String, String> map = new HashMap<String, String>();
        map.put("pactId", pactId);
        map.put("pactSts", mfBusPact.getPactSts());
        model.addAttribute("jsonBean", JSONObject.fromObject(map).toString());
        model.addAttribute("pageInfo", "合同信息");
        model.addAttribute("mfBusPact", mfBusPact);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_List";
    }

    /**
     * 方法描述：合同审批流程提交
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2016-6-7 下午5:58:30
     */
    @RequestMapping(value = "/processSubmitAjax")
    @ResponseBody
    public Map<String, Object> processSubmitAjax(String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            // 提交合同审批流程
            MfBusPact mfBusPactTmp = mfBusPactFeign.getByAppId(appId);
            String regName = User.getRegName(request);
            String orgNo = User.getOrgNo(request);
            MfBusPact mfBusPact = mfBusPactWkfFeign.submitProcess(mfBusPactTmp.getPactId(), null, regName, orgNo);
            dataMap.put("node", "processaudit");
            dataMap.put("processType", "pact");
            dataMap.put("pactSts", mfBusPact.getPactSts());
            dataMap.put("flag", "success");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("userRole", mfBusPact.getApproveNodeName());
            paramMap.put("opNo", mfBusPact.getApprovePartName());
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
     * 方法描述：合同审批流程提交
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2016-6-7 下午5:58:30
     */
    @RequestMapping(value = "/primaryProcessSubmitAjax")
    @ResponseBody
    public Map<String, Object> primaryProcessSubmitAjax(String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            // 提交合同审批流程
            MfBusPact mfBusPactTmp = mfBusPactFeign.getByAppId(appId);
            String regName = User.getRegName(request);
            String orgNo = User.getOrgNo(request);
            MfBusPact mfBusPact = mfBusPactWkfFeign.submitProcess(mfBusPactTmp.getPactId(), null, regName, orgNo);
            dataMap.put("node", "processaudit");
            dataMap.put("processType", "pact");
            dataMap.put("pactSts", mfBusPact.getPactSts());
            dataMap.put("flag", "success");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("userRole", mfBusPact.getApproveNodeName());
            paramMap.put("opNo", mfBusPact.getApprovePartName());
            dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getPactApprovalInfo")
    public String getPactApprovalInfo(Model model, String pactId) throws Exception {
        ActionContext.initialize(request, response);
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        model.addAttribute("query", "");
        return "";
    }

    /**
     * 方法描述：提前解约合同(主要针对额度可循环的合同，所有的借据都已完结了，合同还没有到授信结束日期的，可以进行提前解约终止合同)
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2018-4-17 下午3:56:10
     */
    @RequestMapping(value = "/inputPactPreEnd")
    public String inputPactPreEnd(Model model, String appId, String pactId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formpact0001 = formService.getFormData("pactPreEnd");
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(appId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact.setDisagreeType("3");//提前解约合同
        mfBusPact.setDisagreeOpName(User.getRegName(request));
        mfBusPact.setDisagreeOpNo(User.getRegNo(request));
        mfBusPact.setDisagreeTime(DateUtil.getDateTime());
        mfBusPact.setDisagreeDate(DateUtil.getDate());
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
        mfBusPact.setFincRate(MathExtend.showRateMethod(mfBusPact.getRateType(), mfBusPact.getFincRate(), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
        getObjValue(formpact0001, mfBusPact);
        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();
        this.changeFormProperty(formpact0001, "term", "unit", termUnit);
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formpact0001, "fincRate", "unit", rateUnit);
        model.addAttribute("formpact0001", formpact0001);
        model.addAttribute("appId", appId);
        model.addAttribute("pactId", pactId);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_inputPactPreEnd";
    }

    /**
     * 方法描述： 提前解约合同保存(主要针对额度可循环的合同，所有的借据都已完结了，合同还没有到授信结束日期的，可以进行提前解约终止合同)
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2018-4-17 下午3:56:15
     */
    @RequestMapping(value = "/insertPactPreEndInfoAjax")
    @ResponseBody
    public Map<String, Object> insertPactPreEndInfoAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        FormData formpact0001 = formService.getFormData("pactPreEnd");
        getFormValue(formpact0001, getMapByJson(ajaxData));
        if (this.validateFormData(formpact0001)) {
            MfBusPact mfBusPact = new MfBusPact();
            setObjValue(formpact0001, mfBusPact);
            dataMap = mfBusPactFeign.pactPreEnd(mfBusPact);
        }
        return dataMap;
    }


    /**
     * 方法描述： 统计客户历史完结业务
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-3-28 上午11:06:03
     */
    @RequestMapping(value = "/getCompleteBusDataAjax")
    @ResponseBody
    public Map<String, Object> getCompleteBusDataAjax(String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> parmMap = new HashMap<String, Object>();
            List<String> itemList = new ArrayList<String>();
            itemList.add(BizPubParm.PACT_STS_FINISH);
            itemList.add(BizPubParm.PACT_STS_SEAL);
            parmMap.put("cusNo", cusNo);
            parmMap.put("itemList", itemList);
            parmMap.put("cusType", null);
            dataMap = mfBusPactFeign.getBusStatisticalData(parmMap);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getAllAjax")
    @ResponseBody
    public Map<String, Object> getAllAjax(String ajaxData, String query) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        String parm = "pactSts=" + BizPubParm.PACT_STS_PASS;
        try {
            EntityUtil entityUtil = new EntityUtil();
            dataMap.put("data", entityUtil.prodAutoMenu(mfBusPact, ajaxData, query, parm, null));
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 获取合同业务头部详情信息
     *
     * @return
     * @throws Exception String
     * @author Javelin
     * @date 2017-7-27 下午4:34:52
     */
    @RequestMapping(value = "/getPactHeadInfoAjax")
    @ResponseBody
    public Map<String, Object> getPactHeadInfoAjax(String appId, String pactId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 合同信息
        MfBusPact mfBusPact = new MfBusPact();
        //mfBusPact.setAppId(appId);
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        dataMap.put("rateUnit", rateUnit);
        // 金额格式化
        mfBusPact.setFincAmt(MathExtend.divide(String.valueOf(mfBusPact.getPactAmt()), "10000", mfBusPact.getPactAmt()==0?2:6));
        dataMap.put("pactBal", MathExtend.moneyStr(mfBusPact.getUnrepayFincAmt() / 10000));
        // 合同逾期天数
        int overDays = 0;
        dataMap.put("overDays", overDays);
        if (!StringUtil.isEmpty(mfBusPact.getEndDate())
                && DateUtil.compareTo(mfBusPact.getEndDate(), DateUtil.getDate()) > 0) {
            overDays = DateUtil.getIntervalDays(DateUtil.getStr(mfBusPact.getEndDate()), DateUtil.getDate("yyyyMMdd"));
            if (overDays >= 0) {
                dataMap.put("overDays", overDays);
            }
        }
        //判断抵质押额度
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
            dataMap.put("tips", "应收账款余额小于应收账款抵押余值");
        }
        dataMap.put("mfBusPact", mfBusPact);
        // 业务申请信息
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
        String busModel = mfBusApply.getBusModel();
        dataMap.put("mfBusApply", mfBusApply);
        String wareHouseCusNo = "0";
        String coreCusNo = "0";
        String fundCusNo = "0";
        // 关联企业：核心企业、资金机构、仓储机构等信息
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


//        MfBusPact mfBusPactTmp = new MfBusPact();
//        mfBusPactTmp.setCusNo(mfBusApply.getCusNo());
//        List<MfBusPact> mfBusPacts = pactInterfaceFeign.getMultiBusList(mfBusPactTmp);
        mfBusFincAppTmp.setFincSts("('5','6','7')");
        mfBusFincApps = pactInterfaceFeign.getMultiBusList(mfBusFincAppTmp);
        dataMap.put("morePactCount", mfBusFincApps.size());

        MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
        mfVouAfterTrack.setCusNo(mfBusApply.getCusNo());
        List<MfVouAfterTrack> mfVouAfterTrackProjectList = mfVouAfterTrackFeign.getMultiBusList(mfVouAfterTrack);
        dataMap.put("moreVouAfterCount", mfVouAfterTrackProjectList.size());

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
     * 方法描述： 获取合同业务详情表单HTML
     *
     * @return
     * @throws Exception String
     * @author Javelin
     * @date 2017-7-27 下午4:34:44
     */
    @RequestMapping(value = "/getPactDetailFormAjax")
    @ResponseBody
    public Map<String, Object> getPactDetailFormAjax(String appId, String pactId, String busEntrance, String formId)
            throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
		request.setAttribute("ifBizManger", "3");
        // 合同信息
        MfBusPact mfBusPact = new MfBusPact();
        if(StringUtil.isEmpty(pactId)){
           mfBusPact.setAppId(appId);
        }
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.getMfBusPactByExten(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        // 判断客户表单信息是否允许编辑
        String query = cusInterfaceFeign.validateCusFormModify(mfBusPact.getCusNo(), mfBusPact.getAppId(), BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
        //factor传的是"",到web端就变成了null
        if (query == null) {
            query = "";
        }
        //formId改为读取mf_kind_form表中配置的表单编号（这样就能满足不同产品详情表单不同的场景）
        MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm(mfBusPact.getKindNo(), WKF_NODE.contract_sign.getNodeNo());
        if (mfKindForm != null) {
            if (StringUtil.isNotEmpty(mfKindForm.getShowModel())) {
                formId = mfKindForm.getShowModel();
            }
        }
        if (StringUtil.isEmpty(formId)) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("合同详情表单编号formId"));
        } else {

            FormData formpact0004 = formService.getFormData(formId);
            getFormValue(formpact0004);

            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
            this.changeFormProperty(formpact0004, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formpact0004, "overRate", "unit", rateUnit);
            this.changeFormProperty(formpact0004, "cmpdRate", "unit", rateUnit);
            this.changeFormProperty(formpact0004, "extenFincRate", "unit", rateUnit);
            // 金额格式化
            mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt() / 10000));
            //获得授信信息
            Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(appId);
            if (!creditData.isEmpty()) {
                getObjValue(formpact0004, creditData);
                mfBusPact.setCreditPactNo((String) creditData.get("creditPactNo"));
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
                    this.changeFormProperty(formpact0004, "penaltyCalRateFinc", "unit", "%");
                } else {
                    this.changeFormProperty(formpact0004, "penaltyCalRateFinc", "unit", "元");
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
                    this.changeFormProperty(formpact0004, "earlyRepayRate", "unit", "%");
                } else {
                    this.changeFormProperty(formpact0004, "earlyRepayRate", "unit", "元");
                }
            }

            //处理产品子类
            String kindNo = mfBusPact.getKindNo();
            MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
            String sunKindNo = mfSysKind.getSubKindNo();
            if (StringUtil.isNotEmpty(sunKindNo)) {
                String[] subKindArray = sunKindNo.split("\\|");
                Map<String, String> dicMap = new CodeUtils().getMapByKeyName("SUB_KIND_TYPE");
                List<OptionsList> subKindList = new ArrayList<OptionsList>();
                for (int i = 0; i < subKindArray.length; i++) {
                    OptionsList op = new OptionsList();
                    op.setOptionLabel(dicMap.get(subKindArray[i]));
                    op.setOptionValue(subKindArray[i]);
                    op.setOptionId(WaterIdUtil.getWaterId());
                    subKindList.add(op);
                }
                this.changeFormProperty(formpact0004, "subKindNo", "optionArray", subKindList);
                this.changeFormProperty(formpact0004, "subKindNo", "initValue", mfBusPact.getSubKindNo());
            }
            //获取配置的费用
            MfBusAppFee mfBusAppFee = new MfBusAppFee();
            mfBusAppFee.setAppId(mfBusPact.getAppId());
            List<MfBusAppFee> mfBusAppFeeList = mfBusAppFeeFeign.getMfBusAppFeeList(mfBusAppFee);
            if(mfBusAppFeeList.size()>0){
                for (int i = 0; i < mfBusAppFeeList.size(); i++) {
                    mfBusAppFee = mfBusAppFeeList.get(i);
                    if("2".equals(mfBusAppFee.getItemNo())){
                        mfBusPact.setPinShenFeeRate(mfBusAppFee.getRateScale());
                    }
                    if("1".equals(mfBusAppFee.getItemNo())){
                        mfBusPact.setDanBaoFeeRate(mfBusAppFee.getRateScale());
                    }
                }
            }
            mfBusPact.setLoaner(mfBusPact.getCusName());
            mfBusPact.setBkcxdbOppositeParty(mfBusPact.getAgenciesName());
            //获取保证人
            List<MfAssureInfo> mfAssureInfoList = mfBusCollateralRelFeign.getAssureListByAppid(mfBusPact.getAppId());
            String sxdfOppositeParty =mfBusPact.getCusName()+",";
            String fdbbzOppositeParty = "";
            if(mfAssureInfoList!=null&&mfAssureInfoList.size()>0){
                for (int i = 0; i < mfAssureInfoList.size(); i++) {
                    fdbbzOppositeParty =fdbbzOppositeParty+mfAssureInfoList.get(i).getAssureName();
                    sxdfOppositeParty = sxdfOppositeParty+mfAssureInfoList.get(i).getAssureName();
                }
            }
            mfBusPact.setFdbbzOppositeParty(fdbbzOppositeParty);
            mfBusPact.setSxdfOppositeParty(sxdfOppositeParty);
            mfBusPact.setZhuTerm(mfBusPact.getTerm());
            mfBusPact.setGuaranteeAmt(mfBusPact.getPactAmt());

            mfBusPact.setPinShenRate(mfBusPact.getPinShenFeeRate());
            mfBusPact.setDanBaoRate(mfBusPact.getDanBaoFeeRate());
            if(mfBusPact.getPinShenRate()!=null){
                mfBusPact.setPinShenFee(MathExtend.multiply(mfBusPact.getPactAmt(),mfBusPact.getPinShenRate())/100);
            }
            if(mfBusPact.getDanBaoRate()!=null){
                mfBusPact.setDanBaoFee(MathExtend.multiply(mfBusPact.getPactAmt(),mfBusPact.getDanBaoRate())/100);
            }
            getObjValue(formpact0004, mfBusPact);

            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(mfBusPact.getAppId());
            mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
            this.changeFormProperty(formpact0004, "vouOpition", "initValue", mfBusApply.getVouOption());
            // 期限单位格式化
            CodeUtils codeUtils = new CodeUtils();
            Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
            String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();
            this.changeFormProperty(formpact0004, "term", "unit", termUnit);

            // 还款日设置赋值
            MfBusAppKind mfBusAppKind = new MfBusAppKind();
            mfBusAppKind.setAppId(mfBusPact.getAppId());
            mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
            this.changeFormProperty(formpact0004, "repayDateSet", "initValue", mfBusAppKind.getRepayDateSet());

            this.changeFormProperty(formpact0004, "interestCollectType", "initValue", mfBusAppKind.getInterestCollectType());

            //处理其他担保方式
            List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
            MfBusAppKind mfBusAppKindVouType = new MfBusAppKind();
            mfBusAppKindVouType.setAppId(appId);
            mfBusAppKindVouType = mfBusAppKindFeign.getById(mfBusAppKindVouType);
            if(mfBusAppKindVouType != null){
                if (StringUtil.isEmpty(mfBusAppKindVouType.getVouType())) {
                    List<ParmDic> pdl = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
                    for (ParmDic parmDic : pdl) {
                        if ("1".equals(parmDic.getOptCode())) {
                            continue;
                        }
                        OptionsList s = new OptionsList();
                        s.setOptionLabel(parmDic.getOptName());
                        s.setOptionValue(parmDic.getOptCode());
                        vouTypeOtherList.add(s);
                    }
                } else {
                    Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
                    String[] vouTypes = mfBusAppKindVouType.getVouType().split("\\|");
                    for (int i = 0; i < vouTypes.length; i++) {
                        if ("1".equals(vouTypes[i])) {
                            continue;
                        }
                        OptionsList s = new OptionsList();
                        ;
                        s.setOptionValue(vouTypes[i]);
                        s.setOptionLabel(dicMap.get(vouTypes[i]));
                        vouTypeOtherList.add(s);
                    }
                }
                this.changeFormProperty(formpact0004, "vouTypeOther", "optionArray", vouTypeOtherList);
            }


            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            request.setAttribute("ifBizManger", "3");
            String htmlStr = jsonFormUtil.getJsonStr(formpact0004, "propertySeeTag", query);
            dataMap.put("htmlStr", htmlStr);
            dataMap.put("busModel", mfBusPact.getBusModel());
            dataMap.put("query", query);
        }
        return dataMap;
    }

    /**
     * 方法描述： 获取收款计划信息
     *
     * @return
     * @throws Exception String
     * @author Javelin
     * @date 2017-7-28 上午10:25:14
     */
    @RequestMapping(value = "/getMfRepayPlanListAjax")
    @ResponseBody
    public Map<String, Object> getMfRepayPlanListAjax(String fincId, String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 获取收益计划 信息
        MfRepayPlan mfRepayPlan = new MfRepayPlan();
        mfRepayPlan.setFincId(fincId);
        List<MfRepayPlan> mfRepayPlanList = calcRepaymentInterfaceFeign.getMfRepayPlanList(mfRepayPlan);
        if (mfRepayPlanList != null && mfRepayPlanList.size() > 0) {
            String overDays;
            String txdate = DateUtil.getDate();
            for (MfRepayPlan repayPlan : mfRepayPlanList) {
                overDays = repayPlan.getOverDays();
                if (StringUtil.isEmpty(overDays) || "0".equals(overDays)) {
                    if (BizPubParm.OUT_FLAG_3.equals(repayPlan.getOutFlag())&&StringUtil.isNotEmpty(repayPlan.getPlanEndDate())) {
                        overDays = String.valueOf(DateUtil.getDaysBetween(repayPlan.getPlanEndDate(), txdate));
                    } else if (BizPubParm.OUT_FLAG_1.equals(repayPlan.getOutFlag())&&StringUtil.isNotEmpty(repayPlan.getRepayDate())) {
                        overDays = String.valueOf(DateUtil.getDaysBetween(repayPlan.getPlanEndDate(), repayPlan.getRepayDate()));
                    } else {
                        overDays = "0";
                    }
                    repayPlan.setOverDays(overDays);
                }
            }
        }
        //借据信息
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

        //表单编号
        //formId改为读取mf_kind_form表中配置的表单编号（这样就能满足不同产品详情表单不同的场景）
        MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm(mfBusFincApp.getKindNo(), WKF_NODE.review_finc.getNodeNo());
        if (mfKindForm != null) {
            if (StringUtil.isNotEmpty(mfKindForm.getListModel())) {
                tableId = mfKindForm.getListModel();
            }
        }
        if (StringUtil.isEmpty(tableId)) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("收款计划列表编号tableId"));
        } else {
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfRepayPlanList, null, true);
            dataMap.put("htmlStr", tableHtml);
            dataMap.put("flag", "success");
        }
        return dataMap;
    }

    /**
     * 方法描述： 获取还款计划合并信息
     *
     * @return
     * @throws Exception String
     * @author wd
     * @date 2018-12-05上午10:25:14
     */
    @RequestMapping(value = "/getMfRepayPlanMergeListAjax")
    @ResponseBody
    public Map<String, Object> getMfRepayPlanMergeListAjax(String fincId, String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 获取收益计划 信息
        MfRepayPlan mfRepayPlan = new MfRepayPlan();
        mfRepayPlan.setFincId(fincId);
        List<MfRepayPlan> mfRepayPlanList = calcRepaymentInterfaceFeign.getMfRepayPlanMergeList(mfRepayPlan);
        String tableHtml = "";
        if (mfRepayPlanList != null && mfRepayPlanList.size() > 0) {
            JsonTableUtil jtu = new JsonTableUtil();
            tableHtml = jtu.getJsonStr(tableId, "tableTag", mfRepayPlanList, null, true);
        }
        dataMap.put("htmlStr", tableHtml);
        return dataMap;
    }

    /**
     * 跳转至资金机构放款确认列表
     */
    @RequestMapping(value = "/getCaptialConfirmListPage")
    public String getCaptialConfirmListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("PACT_STS");
        model.addAttribute("pactStsJsonArray", pactStsJsonArray);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_CaptitalConfirmList";
    }

    /**
     * 获取资金机构放款确认列表
     *
     * @return
     */
    @RequestMapping(value = "/getCaptitalAmtConfirmListAjax")
    @ResponseBody
    public Map<String, Object> getCaptitalAmtConfirmListAjax(Integer pageNo, Integer pageSize, String tableId,
                                                             String tableType, String ajaxData, String cusNo, String pactSts) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setCusNo(cusNo);
            mfBusPact.setPactSts(pactSts);
            String sts = ActionContext.getActionContext().getRequest().getParameter("sts");// 0:待确认、1：已确认
            if ("0".equals(sts)) {
                mfBusPact.setExt9("open");
            } else if ("1".equals(sts)) {
                mfBusPact.setExt9("completed");
            }else {
            }
            // this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.getCaptitalAmtConfirmList(ipage);
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
     * 跳转至资金机构放款确认历史列表
     */
    @RequestMapping(value = "/getCaptialConfirmHistPage")
    public String getCaptialConfirmHistPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("PACT_STS");
        model.addAttribute("pactStsJsonArray", pactStsJsonArray);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_captitalconfirmhist";
    }

    /**
     * 跳转至返费功能页面
     */
    @RequestMapping(value = "/getReturnFeePage")
    public String getReturnFeePage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_STS");
        model.addAttribute("fincStsJsonArray", pactStsJsonArray);
        return "/component/pact/MfBusPact_returnfeeList";
    }

    /**
     * 返费管理列表数据
     */
    @RequestMapping(value = "/findReturnFeeListAjax")
    @ResponseBody
    public Map<String, Object> findReturnFeeListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                     String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setCusNo(cusNo);
            // mfBusFincApp.setPactSts(pactSts);
            // this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.getReturnFeeList(ipage);
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
     * 拒绝业务<br>
     * 放款及放款之前都允许否决, 以权限控制此功能是否开放<br>
     *
     * @return
     * @throws Exception
     * @author WangChao
     * @date 2017-11-6 下午4:16:36
     */
    @RequestMapping(value = "/disagree2")
    @ResponseBody
    public Map<String, Object> disagree2(Model model, String appId) throws Exception {
        ActionContext.initialize(request, response);

        String regNo = User.getRegNo(request);

        mfBusPactFeign.doDisagree2(appId, regNo);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("appId", appId);
        dataMap.put("flag", "success");

        return dataMap;
    }

    /**
     * 方法描述： 获得业务在合同审批、放款审批、转让审批中时的审批岗位和审批人员
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-8-24 下午6:33:05
     */
    @RequestMapping(value = "/getBussFlowApproveInfoAjax")
    @ResponseBody
    public Map<String, Object> getBussFlowApproveInfoAjax(String pactId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap = mfBusPactFeign.getBussFlowApproveInfo(pactId);
        dataMap.put("flag", "success");
        return dataMap;
    }

    /**
     * 方法描述：根据流程定义获取第一个审批节点的审批用户
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-9-1 上午10:28:32
     */
    @RequestMapping(value = "/getUserForFristTaskByPage")
    public String getUserForFristTaskByPage(Model model, String processId) throws Exception {
        ActionContext.initialize(request, response);
        List<WkfApprovalUser> wkfApprovalUserList = mfBusPactFeign.getUserForFristTask(processId, User.getRegNo(request));
        model.addAttribute("wkfApprovalUserList", wkfApprovalUserList);
        model.addAttribute("query", "");
        return "/component/wkf/WkfApprovalUser_ListApprovalUser";
    }

    /**
     * 方法描述： 根据流程定义、节点名称获取审批节点的审批用户
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-12-1 下午3:26:19
     */
    @RequestMapping(value = "/getUserForTaskByPage")
    public String getUserForTaskByPage(Model model, String processId, String nodeName, String opNo) throws Exception {
        ActionContext.initialize(request, response);
        Object wkfApprovalUserList = mfBusPactFeign.getUserForTask(processId, nodeName, opNo);
        model.addAttribute("wkfApprovalUserList", wkfApprovalUserList);
        model.addAttribute("query", "");
        return "/component/pact/WkfApprovalUser_ListApprovalUser";
    }

    /**
     * 方法描述： 获取在履行合同数据列表
     *
     * @return
     * @throws Exception String
     * @author Javelin
     * @date 2017-10-19 下午2:14:14
     */
    @RequestMapping(value = "/getMultiBusList")
    public String getMultiBusList(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);

        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setCusNo(cusNo);
        mfBusFincApp.setFincSts("('5','6')");
        mfBusFincApp.setQueryType("1");
        List<MfBusFincApp> mfBusFincAppList = pactInterfaceFeign.getMultiBusList(mfBusFincApp);
        if(mfBusFincAppList != null && mfBusFincAppList.size() > 0){
            // 合同金额总和
            Double pactAmt = 0d;
            for (MfBusFincApp mbp : mfBusFincAppList) {
                pactAmt = DataUtil.add(pactAmt, mbp.getPutoutAmt(), 20);
            }
            pactAmt = DataUtil.retainDigit(pactAmt, 2);
            mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setPutoutAmt(pactAmt);
            mfBusFincApp.setTermShow("总金额");
            mfBusFincAppList.add(mfBusFincApp);
        }
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr( "tablepact0003", "tableTag", mfBusFincAppList, null, true);
        model.addAttribute("mfBusFincAppList", mfBusFincAppList);
        model.addAttribute("mfBusFincAppListSize", mfBusFincAppList.size());
        model.addAttribute("tableHtml", tableHtml);

        mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setCusNo(cusNo);
        mfBusFincApp.setFincSts("('5','6')");
        mfBusFincApp.setQueryType("2");
        List<MfBusFincApp> mfBusFincAppProjectList = pactInterfaceFeign.getMultiBusList(mfBusFincApp);
        if(mfBusFincAppProjectList != null && mfBusFincAppProjectList.size() > 0) {
            // 合同金额总和
            Double pactProjectAmt = 0d;
            for (MfBusFincApp mbp : mfBusFincAppProjectList) {
                pactProjectAmt = DataUtil.add(pactProjectAmt, mbp.getPutoutAmt(), 20);
            }
            pactProjectAmt = DataUtil.retainDigit(pactProjectAmt, 2);
            mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setPutoutAmt(pactProjectAmt);
            mfBusFincApp.setBreedName("总金额");
            mfBusFincAppProjectList.add(mfBusFincApp);
        }
        String projectTableHtml = jtu.getJsonStr( "tablepact_GCDB", "tableTag", mfBusFincAppProjectList, null, true);
        model.addAttribute("projectTableHtml", projectTableHtml);
        model.addAttribute("mfBusFincAppProjectList", mfBusFincAppProjectList);
        model.addAttribute("mfBusFincAppProjectListSize", mfBusFincAppProjectList.size());

        model.addAttribute("busEntrance", "pact");
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_multiBusList";
    }

    /**
     * 获取合同电子签章文件url
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getElecFileAjax")
    @ResponseBody
    public Map<String, Object> getElecFileAjax(String pactId) throws Exception {
        ActionContext.initialize(request, response);
        String tempNo = request.getParameter("tempNo");
        String url = mfBusPactFeign.getElecFile(pactId, tempNo);
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("flag", "success");
        dataMap.put("url", url);
        return dataMap;
    }

    /**
     * 方法描述： 跳转渠道系统合同签约列表
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-17 下午5:43:22
     */
    @RequestMapping(value = "/getTrenchListPage")
    public String getTrenchListPage() throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("PACT_STS");
        this.getHttpRequest().setAttribute("pactStsJsonArray", pactStsJsonArray);
        JSONArray kindTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("KIND_NO");
        this.getHttpRequest().setAttribute("kindTypeJsonArray", kindTypeJsonArray);
        // 办理阶段
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        JSONArray jsonArray = JSONArray.fromObject("[{\"optCode\":\"待提款\",\"optName\":\"待提款\"},{\"optCode\":\"待放款\",\"optName\":\"待放款\"},{\"optCode\":\"放款复核\",\"optName\":\"放款复核\"}]");
        flowNodeJsonArray.addAll(jsonArray);
        this.getHttpRequest().setAttribute("flowNodeJsonArray", flowNodeJsonArray);
        return "/component/pact/MfBusPact_TrenchSignList";
    }

    /**
     * 方法描述： 渠道系统合同签约列表
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-17 下午5:45:31
     */
    @RequestMapping(value = "/findTrenchSignByPageAjax")
    @ResponseBody
    public Map<String, Object> findTrenchSignByPageAjax(String ajaxData, String cusNo, String pactSts, Integer pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact = setMfBusPactInfo(mfBusPact);
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setCusNo(cusNo);
            mfBusPact.setPactSts(pactSts);
            // this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.findTrenchSignPactByPage(ipage);
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

    private MfBusPact setMfBusPactInfo(MfBusPact mfBusPact) throws Exception {
        String dataRang = (String) this.getHttpRequest().getSession().getAttribute("dataRang");
        String trenchOpNo = (String) this.getHttpRequest().getSession().getAttribute("trenchOpNo");
        String channelSourceNo = (String) this.getHttpRequest().getSession().getAttribute("channelSourceNo");
        if (BizPubParm.TRENCH_USER_DATA_RANG_1.equals(dataRang)) {// 本人
            mfBusPact.setTrenchOpNo(trenchOpNo);
            mfBusPact.setChannelSourceNo(channelSourceNo);
        } else if (BizPubParm.TRENCH_USER_DATA_RANG_2.equals(dataRang)) {// 本渠道
            mfBusPact.setChannelSourceNo(channelSourceNo);
        } else if (BizPubParm.TRENCH_USER_DATA_RANG_3.equals(dataRang)) {// 本渠道及其子渠道
            MfBusTrench mfBusTrench = new MfBusTrench();
            mfBusTrench.setTrenchUid(channelSourceNo);
            String trenchChildStr = cusInterfaceFeign.getTrenchChildStr(mfBusTrench);
            mfBusPact.setChannelSourceStr(trenchChildStr);
        }else {
        }
        return mfBusPact;
    }

    /**
     * 方法描述： 查询业务下的费用合同查询跳转页面
     *
     * @return
     * @throws Exception String
     * @author 段泽宇
     * @date 2018-5-4 下午16:43:05
     */
    @RequestMapping(value = "/getFeePactQueryListPage")
    public String getFeePactQueryListPage(Model model, String ajaxData) throws Exception {
        return "/component/pact/MfFeePactQueryList";
    }

    /**
     * 方法描述： 费用合同查询列表
     *
     * @return
     * @throws Exception String
     * @author 段泽宇
     * @date 2018-5-5 上午10:30:31
     */
    @RequestMapping(value = "/findFeePactByPageAjax")
    @ResponseBody
    public Map<String, Object> findFeePactByPageAjax(String ajaxData, String cusNo, String pactSts, Integer pageSize, Integer pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact = setMfBusPactInfo(mfBusPact);
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setCusNo(cusNo);
            mfBusPact.setPactSts(pactSts);
            // this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.findFeePactByPageAjax(ipage);
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
     * 已完结的合同列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFinishedPactListByPageAjax")
    @ResponseBody
    public Map<String, Object> getFinishedPactListByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                             String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setCusNo(cusNo);
            // this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.getFinishedPactListByPage(ipage);
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
     * 银行还款列表
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPageBankRepay")
    public String getListPageBankRepay(Model model) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("PACT_STS");
        model.addAttribute("pactStsJsonArray", pactStsJsonArray);
        JSONArray kindTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("KIND_NO");
        model.addAttribute("kindTypeJsonArray", kindTypeJsonArray);
        // 办理阶段
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        JSONArray jsonArray = JSONArray.fromObject(
                "[{\"optCode\":\"待提款\",\"optName\":\"待提款\"},{\"optCode\":\"待放款\",\"optName\":\"待放款\"},{\"optCode\":\"放款复核\",\"optName\":\"放款复核\"}]");
        flowNodeJsonArray.addAll(jsonArray);
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_bankRepayList";
    }

    /***
     * 银行还款列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findBankRepayByPageAjax")
    @ResponseBody
    public Map<String, Object> findBankRepayByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                       String ajaxData, String cusNo, String pactSts) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setCusNo(cusNo);
            mfBusPact.setPactSts(pactSts);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.findBankRepayByPage(ipage);
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
     * 方法描述：合同签约节点 合同开始日期修改 获取合同结束日期
     *
     * @param ajaxData
     * @return
     * @throws Exception Map<String,Object>
     * @author wd
     * @date 2018年6月30日 上午10:55:47
     */

    @RequestMapping(value = "/getPactEndDateInfoMapAjax")
    @ResponseBody
    public Map<String, Object> getPactEndDateInfoMapAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //{"beginDate":beginDate,"term":term,"termType":termType,"calcIntstFlag":calcIntstFlag,"pactEndDateShowFlag":pactEndDateShowFlag,"pactEndDateShowFlag":appId,"pactId":pactId},			String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");
            Map<String, String> parmMap = new HashMap<String, String>();
            String beginDate = request.getParameter("beginDate");//合同开始日期
            String term = request.getParameter("term");//合同期限
            String termType = request.getParameter("termType");//合同期限类型
            String calcIntstFlag = request.getParameter("calcIntstFlag");//1-算头不算尾 2-首尾都计算
            String pactEndDateShowFlag = request.getParameter("pactEndDateShowFlag");//合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天
            String appId = request.getParameter("appId");//申请号
            String pactId = request.getParameter("pactId");//合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天
            parmMap.put("beginDate", beginDate);
            parmMap.put("term", term);
            parmMap.put("termType", termType);
            parmMap.put("calcIntstFlag", calcIntstFlag);
            parmMap.put("pactEndDateShowFlag", pactEndDateShowFlag);
            parmMap.put("appId", appId);
            parmMap.put("pactId", pactId);
            dataMap = mfBusPactFeign.getPactEndDateInfoMap(parmMap);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }


    @RequestMapping(value = "/getAgenciesCusListPage")
    public String getAgenciesCusListPage(Model model, String agenciesUid) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("agenciesUid", agenciesUid);
        return "/component/pact/MfBusPact_AgenciesCus";
    }

    @RequestMapping(value = "/findAgenciesCusByPageAjax")
    @ResponseBody
    public Map<String, Object> findAgenciesCusByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                         String ajaxData, String agenciesUid) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setCusNoFund(agenciesUid);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.findAgenciesCusByPageAjax(ipage);
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
     * 方法描述： 获取核心企业成交客户
     *
     * @param model
     * @param cusNo
     * @return
     * @throws Exception String
     * @author 仇招
     * @date 2018年7月16日 下午7:49:53
     */
    @RequestMapping(value = "/getCoreCompanyCusListPage")
    public String getCoreCompanyCusListPage(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        return "/component/pact/MfBusPact_CoreCompanyCus";
    }

    @RequestMapping(value = "/findCoreCompanyCusByPageAjax")
    @ResponseBody
    public Map<String, Object> findCoreCompanyCusByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                            String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setCusNoCore(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.findCoreCompanyCusByPageAjax(ipage);
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
     * 仓储机构
     *
     * @return
     * @throws
     * @author 仇招
     * @date 2019/4/18 14:48
     */
    @RequestMapping(value = "/getWarehouseOrgCusListPage")
    public String getWarehouseOrgCusListPage(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        return "/component/pact/MfBusPact_WarehouseOrgCus";
    }

    /**
     * 仓储机构
     *
     * @return
     * @throws
     * @author 仇招
     * @date 2019/4/18 14:48
     */
    @RequestMapping(value = "/findWarehouseOrgCusByPageAjax")
    @ResponseBody
    public Map<String, Object> findWarehouseOrgCusByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                             String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setCusNoCore(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.findWarehouseOrgCusByPageAjax(ipage);
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
     * 方法描述： 验证合同编号唯一性
     *
     * @param pactId
     * @param pactNo
     * @return
     * @throws Exception String
     * @author 付晨
     * @date 2018年7月31日 下午7:49:53
     */
    @RequestMapping(value = "/verityPactNo")
    @ResponseBody
    public Map<String, Object> verityPactNo(String pactNo, String pactId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        MfBusPact mfBusPactTo = new MfBusPact();
        mfBusPact.setPactNo(pactNo);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPactTo.setPactId(pactId);
        mfBusPactTo = mfBusPactFeign.getById(mfBusPactTo);//用流水号获取合同信息，根据合同信息去判断传来的pactNo是否是该合同的pactNo
        if (mfBusPact != null && !mfBusPactTo.getPactNo().equals(pactNo)) {
            dataMap.put("flag", "0");
            dataMap.put("msg", "该合同编号已存在，请重新输入");
        } else {
            dataMap.put("flag", "1");
        }
        return dataMap;
    }

    /**
     * 方法描述:合同结束日期修改后,自动计算期数
     *
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getTermByEndDateBeginDateAjax")
    @ResponseBody
    public Map<String, Object> getTermByEndDateBeginDateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            String beginDate = request.getParameter("beginDate").replaceAll("-", "");//合同开始日期
            String endDate = request.getParameter("endDate").replaceAll("-", "");//合同结束日期
            String term = String.valueOf(DateUtil.getMonthDiff(beginDate, endDate));//合同期限
            dataMap.put("term", term);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * findByPageAjax
     * 方法描述:凤安项目-合作社向凤安平台推送待放款借据
     *
     * @param pactInfosJson
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pushPactInfosAjax")
    @ResponseBody
    public Map<String, Object> pushPactInfosAjax(String pactInfosJson) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            mfBusPactFeign.pushPactInfosAjax(pactInfosJson);

            dataMap.put("flag", true);
            dataMap.put("msg", "放款请求信息发送成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            dataMap.put("flag", false);
            dataMap.put("msg", ex.getMessage());
        }
        return dataMap;
    }

    /**
     * 贷后合同列表打开页面请求打印用款申请单
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getLoanAfterPactListPageForPrint")
    public String getLoanAfterPactListPageForPrint(Model model, String ajaxData, String queryType) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray pactStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_STS");
        model.addAttribute("fincStsJsonArray", pactStsJsonArray);
        // 办理阶段
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("queryType", queryType);
        model.addAttribute("query", "");
        return "/component/pact/MfBusPact_printList";
    }

    /***
     * 贷后合同列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findLoanAfterByPageAjax")
    @ResponseBody
    public Map<String, Object> findLoanAfterByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                       String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setCusNo(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.findLoanAfteringByPage(ipage);
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

    @RequestMapping(value = "/getCerCode")
    @ResponseBody
    public Map<String, Object> getCerCode(Model model, String temBizNo, String templateNo, String nodeNo, String esignerNo, String certInfoContent) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String appId = temBizNo;
        String creditAppId = temBizNo;
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(esignerNo);
        try {
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
            if (!certInfoContent.contains(mfCusCustomer.getIdNum())) {
                paramMap.put("flag", "error");
                paramMap.put("msg", "证书与签章主体不匹配，请选择正确的证书！");
                return paramMap;
            }
            //判断是授信签约还是融资签约
            if ("protocol_manage".equals(nodeNo)) {//授信
                MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                mfCusCreditContract.setCreditAppId(creditAppId);
                mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
            } else {//合同
                MfBusPact mfBusPact = new MfBusPact();
                mfBusPact.setAppId(appId);
                mfBusPact = mfBusPactFeign.getById(mfBusPact);
                String pactId = mfBusPact.getPactId();
                paramMap.put("pactId", pactId);

            }
            paramMap.put("cusNo", esignerNo);//客户号
            paramMap.put("creditAppId", creditAppId);
            paramMap.put("appId", appId);
            paramMap.put("temBizNo", temBizNo);
            paramMap.put("nodeNo", nodeNo);
            paramMap.put("templateNo", templateNo);//模板编号
            paramMap = esignInterfaceFeign.DZQY(paramMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramMap;
    }

    @RequestMapping("/getPdfView")
    public String getPdfView(Model model, String templateNo, String id, String pdfHash, String temBizNo, String nodeNo, String cusNo) throws Exception {

        try {
            System.out.print("-------------->进入" + nodeNo);
            FormService formService = new FormService();
            ActionContext.initialize(request, response);
            //放入form表单
            FormData frontcus00004 = formService.getFormData("frontcus00004");
            getFormValue(frontcus00004);
            this.changeFormProperty(frontcus00004, "nodeNo", "initValue", nodeNo);
            this.changeFormProperty(frontcus00004, "templateNo", "initValue", templateNo);
            this.changeFormProperty(frontcus00004, "id", "initValue", id);
            this.changeFormProperty(frontcus00004, "pdfHash", "initValue", pdfHash);

//			this.changeFormProperty(frontcus00004, "id", "initValue", "111");
//			this.changeFormProperty(frontcus00004, "pdfHash", "initValue","111");
            model.addAttribute("frontcus00004", frontcus00004);
            model.addAttribute("query", "");
            model.addAttribute("id", id);
            model.addAttribute("pdfHash", pdfHash);
            model.addAttribute("templateNo", templateNo);
            model.addAttribute("temBizNo", temBizNo);
            model.addAttribute("nodeNo", nodeNo);
            model.addAttribute("creditAppId", temBizNo);
            model.addAttribute("appId", temBizNo);
            model.addAttribute("cusNo", cusNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/component/pact/MfBusPact_getCerCode";
    }

    @RequestMapping("/getPdf")
    @ResponseBody
    public Map<String, Object> getPdf(String templateNo, String nodeNo, String id, String signature, String temBizNo, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("templateNo", templateNo);
            paramMap.put("nodeNo", nodeNo);
            paramMap.put("id", id);
            paramMap.put("signature", signature);
            paramMap.put("cusNo", cusNo);
            if ("protocol_manage".equals(nodeNo)) {
                paramMap.put("creditAppId", temBizNo);//授信申请号
            } else if ("contract_print".equals(nodeNo)) {
                paramMap.put("appId", temBizNo);
            }else {
            }

            data = esignInterfaceFeign.getPdf(paramMap);
            data.put("templateNo", templateNo);
            data.put("temBizNo", temBizNo);
            data.put("nodeNo", nodeNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @RequestMapping(value = "/getCerCodeCor")
    @ResponseBody
    public Map<String, Object> getCerCodeCor(Model model, String temBizNo, String templateNo, String nodeNo, String esignerNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String appId = temBizNo;
        String creditAppId = temBizNo;
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(esignerNo);
        try {
//			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
//			if(!certInfoContent.contains(mfCusCustomer.getIdNum())){
//				paramMap.put("flag", "error");
//				paramMap.put("msg", "证书与签章主体不匹配，请选择正确的证书！");
//				return paramMap;
//			}
            //判断是授信签约还是融资签约
            if ("protocol_manage".equals(nodeNo)) {//授信
                MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                mfCusCreditContract.setCreditAppId(creditAppId);
                mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
            } else {//合同
                MfBusPact mfBusPact = new MfBusPact();
                mfBusPact.setAppId(appId);
                mfBusPact = mfBusPactFeign.getById(mfBusPact);
                String pactId = mfBusPact.getPactId();
                paramMap.put("pactId", pactId);

            }
            paramMap.put("cusNo", esignerNo);//客户号
            paramMap.put("creditAppId", creditAppId);
            paramMap.put("appId", appId);
            paramMap.put("temBizNo", temBizNo);
            paramMap.put("nodeNo", nodeNo);
            paramMap.put("templateNo", templateNo);//模板编号
            paramMap = esignInterfaceFeign.DZQY(paramMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramMap;
    }


    /**
     * 处理拒绝放款和终止业务流程中按钮操作问题
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/operationSts")
    @ResponseBody
    public Map<String, Object> operationSts(String pactId, String fincId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        try {
            String fincSts="";
            if (!"".equals(fincId) && fincId !=null) {
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setFincId(fincId);
                mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
                if (null != mfBusFincApp && pactId.equalsIgnoreCase(mfBusFincApp.getPactId())) {
                    fincSts=mfBusFincApp.getFincSts();
                }
            }
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            dataMap.put("fincSts",fincSts );
            dataMap.put("pactSts", mfBusPact.getPactSts());

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * 跳转货值预警列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/collateralAmtWarnList")
    public String collateralAmtWarnList(Model model) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray kindTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("KIND_NO");
        model.addAttribute("kindTypeJsonArray", kindTypeJsonArray);
        model.addAttribute("query", "");
        return "/component/pact/MfCollateralAmtWarnList_List";
    }
    /**
     * 货值预警
     * @param pageNo
     * @param pageSize
     * @param tableId
     * @param tableType
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/collateralAmtWarnListAjax")
    @ResponseBody
    public Map<String, Object> collateralAmtWarnListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                         String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            // this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.collateralAmtWarnList(ipage);
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
     * @Description 根据客户号、合同号判断该客户下面是否有正在执行的贷款信息
     * @Author zhaomingguang
     * @DateTime 2019/10/8 10:20
     * @Param [cusNo]
     * @return java.lang.Object
     */
    @RequestMapping(value = "/checkCusInfoByAjax")
    @ResponseBody
    public Object checkCusInfoByAjax(String ajaxData){
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            JSONObject json = JSONObject.fromObject(ajaxData);
            Map<String, String> paraMap = (Map<String, String>) JSONObject.toBean(json, Map.class);
            String cusNo = paraMap.get("cusNo");
            if(StringUtils.isNotEmpty(cusNo)){
                mfBusPact.setCusNo(cusNo);
            }
            String pactId = paraMap.get("pactId");
            if(StringUtils.isNotEmpty(pactId)){
                mfBusPact.setPactId(pactId);
            }
            dataMap = mfBusPactFeign.checkHavePactByCusNo(mfBusPact);
        } catch (Exception e) {
            logger.error("根据客户号判断该客户下面是否有正在执行的贷款信息"+e.getMessage(),e);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * @param mfBusPact
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

    /**
     * 共同借款人列表
     * @param appId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/findCoborrNo")
    @ResponseBody
    public Map<String, Object> findCoborrNo(String appId) throws Exception {
        Map<String , Object> dataMap = new HashMap<>();
        try {
            MfBusPact mfBusPact =  mfBusPactFeign.getByAppId(appId);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            Ipage ipage = this.getIpage();
            if(mfBusPact.getCoborrNo()!=null){
                mfCusCustomer.setCusNo(mfBusPact.getCoborrNo());
                ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
                ipage =  mfCusCustomerFeign.getCusListByCusNos(ipage);
                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr("tablecoborrNamePactList", "tableTag", (List) ipage.getResult(), ipage, true);
                dataMap.put("tableHtml",tableHtml);
                dataMap.put("flag","success");
            }else{
                if(mfBusPact.getCoborrNum()!=null){
                    mfCusCustomer.setIdNum(mfBusPact.getCoborrNum());
                    List<MfCusCustomer>  list= mfBusApplyFeign.getFamilyByIdNum(mfCusCustomer);
                    JsonTableUtil jtu = new JsonTableUtil();
                    String tableHtml = jtu.getJsonStr("tablecoborrNameList", "tableTag", list , ipage, true);
                    dataMap.put("tableHtml",tableHtml);
                    dataMap.put("flag","success");
                }else{
                    dataMap.put("flag","error");
                    dataMap.put("msg","无共借人");
                }
            }
        }catch (Exception e){
            dataMap.put("flag","error");
            dataMap.put("msg","无共借人");
            throw e;
        }
        return dataMap;
    }

    /**
     * 转化社会关系并限制共借人长度
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getCocoborrNum")
    @ResponseBody
    public Map<String, Object> getCocoborrNum(String appId,String cusNo,String coborrNo,String coborrName,String coborrNum, String cusMngNo,String cusMngName) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            String coborrNameNew = "";
            String coborrNumNew = "";
            String coborrNoNew = "";
            MfBusPact mfBusPact = new MfBusPact();
            if(StringUtil.isNotEmpty(appId) && !appId.equals("undefined")){
                mfBusPact = mfBusPactFeign.getByAppId(appId);
                coborrName = mfBusPact.getCoborrName();
                coborrNum = mfBusPact.getCoborrNum();
                coborrNo = mfBusPact.getCoborrNo();
            }
            //共借人不超过5位
            String[]  cusNoArray = coborrNo.split("\\|");
            List<String> list=Arrays.asList(cusNoArray);
            List<String> arrayList=new ArrayList<String>(list);//转换为ArrayLsit调用相关的remove方法
            if(arrayList.contains(cusNo)){//ture,包含
                coborrNameNew = coborrName;
                coborrNumNew = coborrNum;
                coborrNoNew = coborrNo;
                dataMap.put("flag", "success");
            }else {
                if (cusNoArray.length == 5) {
                    coborrNameNew = coborrName;
                    coborrNumNew = coborrNum;
                    coborrNoNew = coborrNo;
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "共同借款人已经有5位，不能选择");
                } else {
                    //当前所选共借人是证件号是否完善
                    MfCusCustomer mfCusCustomer = new MfCusCustomer();
                    mfCusCustomer.setCusNo(cusNo);
                    MfCusCustomer mfCusCustomerTemp = mfCusCustomerFeign.getById(mfCusCustomer);
                    if(mfCusCustomerTemp==null){//不是客户社会关系
                        //将社会关系的共借人转化成客户
                        MfBusApply mfBusApply = new MfBusApply();
                        mfBusApply.setCoborrNo(cusNo);//当前选择的共借人
                        mfBusApply.setCusMngName(cusMngName);
                        mfBusApply.setCusMngNo(cusMngNo);
                        mfBusApply.setCoborrName(coborrNo);//原本的共借人号
                        MfCusCustomer temp = mfBusApplyFeign.getCoborrForCus(mfBusApply);
                        if (temp == null) {
                            coborrNameNew = coborrName;
                            coborrNumNew = coborrNum;
                            coborrNoNew = coborrNo;
                            dataMap.put("flag", "error");
                            dataMap.put("msg", "社会关系联网核查出错");
                        } else {
                            if (StringUtil.isNotEmpty(coborrName)) {
                                coborrNameNew += coborrName + "|" + temp.getCusName();
                                coborrNumNew += coborrNum + "|" + temp.getIdNum();
                                coborrNoNew += coborrNo + "|" + temp.getCusNo();
                            } else {
                                coborrNameNew = temp.getCusName();
                                coborrNumNew = temp.getIdNum();
                                coborrNoNew = temp.getCusNo();
                            }
                            dataMap.put("flag", "success");
                        }
                    }else{
                        if (StringUtil.isEmpty(mfCusCustomerTemp.getIdNum())) {
                            coborrNameNew = coborrName;
                            coborrNumNew = coborrNum;
                            coborrNoNew = coborrNo;
                            dataMap.put("flag", "error");
                            dataMap.put("msg", "所选客户证件号码未完善，请重新选择");
                        }else{
                            if (StringUtil.isNotEmpty(coborrName)) {
                                coborrNameNew += coborrName + "|" + mfCusCustomerTemp.getCusName();
                                coborrNumNew += coborrNum + "|" + mfCusCustomerTemp.getIdNum();
                                coborrNoNew += coborrNo + "|" + mfCusCustomerTemp.getCusNo();
                            } else {
                                coborrNameNew = mfCusCustomerTemp.getCusName();
                                coborrNumNew = mfCusCustomerTemp.getIdNum();
                                coborrNoNew = mfCusCustomerTemp.getCusNo();
                            }
                            dataMap.put("flag", "success");
                        }
                    }

                }
            }
            //共借人列表
            MfCusCustomer cusCustomer = new MfCusCustomer();
            cusCustomer.setCusNo(coborrNoNew);
            Ipage ipage = this.getIpage();
            ipage.setParams(this.setIpageParams("mfCusCustomer", cusCustomer));
            ipage = mfCusCustomerFeign.getCusListByCusNos(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr("tablecoborrNamePactList", "tableTag", (List) ipage.getResult(), ipage, true);
            dataMap.put("tableData", tableHtml);
            dataMap.put("coborrNumNew", coborrNumNew);
            dataMap.put("coborrNameNew", coborrNameNew);
            dataMap.put("coborrNoNew", coborrNoNew);
            if(StringUtil.isNotEmpty(appId)){
                MfBusPact mfBusPactTemp = new MfBusPact();
                mfBusPactTemp.setAppId(appId);
                mfBusPactTemp.setCoborrNo(coborrNoNew);
                mfBusPactTemp.setCoborrName(coborrNameNew);
                mfBusPactTemp.setCoborrNum(coborrNumNew);
                mfBusPactFeign.update(mfBusPactTemp);
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "共同借款人展示错误");
            throw e;
        }
        return dataMap;
    }

    /**
     * 列表删除一个共同借款人
     * @param cusNos
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/deleteCoborr")
    @ResponseBody
    public Map<String,Object> deleteCoborr(String cusNos,String cusNo,String appId)throws Exception{
        Map<String,Object> dataMap = new HashMap<>();
        try {
            if(StringUtil.isNotEmpty(appId)){
                MfBusPact mfBusPact = mfBusPactFeign.getByAppId(appId);
                cusNos=mfBusPact.getCoborrNo();
            }
            String[]  cusNoArray = cusNos.split("\\|");
            List<String> list=Arrays.asList(cusNoArray);
            List<String> arrayList=new ArrayList<String>(list);//转换为ArrayLsit调用相关的remove方法
            arrayList.remove(cusNo);
            String cusNoss="";
            for(int i=0;i<arrayList.size();i++){
                if(i<arrayList.size()-1){
                    cusNoss+=arrayList.get(i)+"|";
                }else{
                    cusNoss+=arrayList.get(i);
                }
            }
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNoss);
            Ipage ipage = this.getIpage();
            ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
            ipage =  mfCusCustomerFeign.getCusListByCusNos(ipage);
            List<Map>  cusList =  (List)ipage.getResult();
            if(cusList.size()>=0){
                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr("tablecoborrNamePactList", "tableTag", (List) ipage.getResult(), ipage, true);
                dataMap.put("tableData",tableHtml);
                dataMap.put("flag","success");
                String idNums = "";
                String coborrName = "";
                for(int i=0;i<cusList.size();i++){
                    idNums+=cusList.get(i).get("idNum");
                    coborrName+=cusList.get(i).get("cusName");
                    if(i<cusList.size()-1){
                        idNums+="|";
                        coborrName+="|";
                    }
                }
                if(cusList.size()==0){
                    dataMap.put("last","last");
                }
                dataMap.put("coborrName",coborrName);
                dataMap.put("idNums",idNums);
                dataMap.put("cusNoss",cusNoss);
                if(StringUtil.isNotEmpty(appId)){
                    MfBusPact mfBusPact =new MfBusPact();
                    mfBusPact.setAppId(appId);
                    mfBusPact.setCoborrNo(cusNoss);
                    mfBusPact.setCoborrName(coborrName);
                    mfBusPact.setCoborrNum(idNums);
                    mfBusPactFeign.update(mfBusPact);
                }
            }else {
                dataMap.put("flag", "error");
                dataMap.put("msg", "共同借款人展示错误");
            }
        }catch (Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "共同借款人展示错误");
            throw e;
        }
        return dataMap;

    }

    /**
     *
     * 在保应收账款质押数据
     */
    /**
     *
     * @param ajaxData
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/getDataByAccount")
    @ResponseBody
    public Map<String, Object> getDataByAccount(String ajaxData, Integer pageNo, Integer pageSize,String tableType,String tableId) throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusPactFeign.getListByAccount(ipage);
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
     *
     更新应收账款
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/accountInput")
    public String accountInput(Model model, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        //根据APPID把合同详情查出来
        MfBusPact mfBusPact=mfBusPactFeign.getByAppId(appId);
        model.addAttribute("pactId",mfBusPact.getPactId());
        model.addAttribute("appId",appId);
        return "/component/collateral/pledgeInfo_accountDetail";
    }
    /**
     *
     *
     更新应收账款
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/accountInputNew")
    public String accountInputNew(Model model, String appId,String pledgeNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        //根据APPID把合同详情查出来
        MfBusPact mfBusPact=mfBusPactFeign.getByAppId(appId);
        FormData formaccoutPactDetail = formService.getFormData("accoutPactDetail");
        getObjValue(formaccoutPactDetail, mfBusPact);
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(pledgeNo);
        pledgeBaseInfo=pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        FormData formbusinessAccountsBase2 = formService.getFormData("businessAccountsBase2");
        getObjValue(formbusinessAccountsBase2, pledgeBaseInfo);
        //查询应收账款质押
        List<PledgeBaseInfoBill> accountInfoBaseList = pledgeBaseInfoBillFeign.findNewByPledgeNo(pledgeNo);
        InsInfo insInfo = new InsInfo();
        insInfo.setCollateralId(pledgeNo);
        List<InsInfo> insInfoList = insInfoFeign.getListByCollateralId(insInfo);
        model.addAttribute("pactId",mfBusPact.getPactId());
        model.addAttribute("collateralId",pledgeNo);
        model.addAttribute("query","");
        model.addAttribute("formaccoutPactDetail", formaccoutPactDetail);
        model.addAttribute("formbusinessAccountsBase2", formbusinessAccountsBase2);
        model.addAttribute("accountInfoBaseList", accountInfoBaseList);
        model.addAttribute("insInfoList", insInfoList);
        model.addAttribute("appId",appId);
        return "/component/collateral/PledgeInfo_AccountDetailNew";
    }
    /**
     * 授信合同生成时添加非业务生成合同
     * @param
     * @return
     */
    @RequestMapping(value = "/addPactExtend")
    public String addPactExtend(Model model,String appId,String cusNo,String queryType)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="pactExtendApply";
        if("2".equals(queryType)){
            formId = "pactExtendApply_GCDB";
        }
        try {
            FormData formpactExtendCredit= formService.getFormData(formId);
            MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
            mfBusPactExtend.setAppId(appId);
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            if(mfBusApply!=null){
                mfBusPactExtend.setCusNo(mfBusApply.getCusNo());
                mfBusPactExtend.setCusName(mfBusApply.getCusName());
                if(!"2".equals(queryType)){
                    mfBusPactExtend.setAgenciesId(mfBusApply.getAgenciesId());
                    mfBusPactExtend.setAgenciesName(mfBusApply.getAgenciesName());
                }
                MfBusPact mfBusPact = mfBusPactFeign.getByAppId(appId);
                if(mfBusPact!=null){
                    mfBusPactExtend.setPactId(mfBusPact.getPactId());
                    mfBusPactExtend.setPactNo(mfBusPact.getPactNo());
                }
            }
            getObjValue(formpactExtendCredit, mfBusPactExtend);
            List<OptionsList> templateList = new ArrayList<OptionsList>();
            Map<String, String> templateMap = new CodeUtils().getMapByKeyName("OTHER_PACT");
            String [] templates = {"301","302","303","304","305","399","119","120","219","220"};
            for (int i = 0; i < templates.length; i++) {
                String template = templates[i];
                Map<String ,Object> resultMap = mfBusPactExtendFeign.initTemplateForApp(appId,template);
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
            model.addAttribute("appId", appId);
            model.addAttribute("id", WaterIdUtil.getWaterId());
            model.addAttribute("cusNo", cusNo);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/pact/MfBusPactExtendApply_Insert";
    }

    /**
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getExendListHtmlAjax")
    @ResponseBody
    public Map<String, Object> getExendListHtmlAjax(String appId,String ajaxData,
                                                    String tableId,String queryType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        try {
            List<MfBusPactExtend> mfBusPactExtendList = new ArrayList<>();
            if(StringUtil.isNotEmpty(appId)){
                mfBusPactExtend.setAppId(appId);
                if("2".equals(queryType)){
                    mfBusPactExtend.setStampPactId("1");
                }
                mfBusPactExtendList = mfBusPactExtendFeign.getAllListForApply(mfBusPactExtend);
            }
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
     * 授信合同生成时添加非业务生成合同
     * @param
     * @return
     */
    @RequestMapping(value = "/pactExtendDetail")
    public String pactExtendDetail(Model model,String appId,String id)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="pactExtendApplyDetail";
        try {
            FormData formpactExtendCreditDetail= formService.getFormData(formId);
            MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
            mfBusPactExtend.setId(id);
            mfBusPactExtend = mfBusPactExtendFeign.getById(mfBusPactExtend);
            getObjValue(formpactExtendCreditDetail, mfBusPactExtend);
            model.addAttribute("formpactExtendCreditDetail", formpactExtendCreditDetail);
            model.addAttribute("query", "");
            model.addAttribute("appId", appId);
            model.addAttribute("id", id);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/pact/MfBusPactExtendApply_Detail";
    }
    /**
     * 授信合同生成时添加非业务生成合同
     * @param
     * @return
     */
    @RequestMapping(value = "/pactExtendView")
    public String pactExtendView(Model model,String appId,String id,String queryType)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="pactExtendCreditDetail";
        if("2".equals(queryType)){
            formId="pactExtendCreditDetail_GCDB";
        }
        try {
            FormData formpactExtendCreditDetail= formService.getFormData(formId);
            MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
            mfBusPactExtend.setId(id);
            mfBusPactExtend = mfBusPactExtendFeign.getById(mfBusPactExtend);
            getObjValue(formpactExtendCreditDetail, mfBusPactExtend);
            model.addAttribute("formpactExtendCreditDetail", formpactExtendCreditDetail);
            model.addAttribute("query", "");
            model.addAttribute("appId", appId);
            model.addAttribute("id", id);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/pact/MfBusPactExtendApply_view";
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
        }else  if ("120".equals(templateId)||"220".equals(templateId)){
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
     * 获取当事人
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getClientAjax")
    @ResponseBody
    public Map<String, Object> getClientAjax(String appId,String templateId,String agenciesId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        try {
            mfBusPactExtend.setAppId(appId);
            dataMap.put("client", mfBusPactExtendFeign.getClientApplyAjax(appId,templateId));
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
