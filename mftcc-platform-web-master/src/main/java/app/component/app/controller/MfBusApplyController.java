package app.component.app.controller;

import app.base.User;
import app.base.cacheinterface.BusiCacheInterface;
import app.component.app.entity.*;
import app.component.app.feign.*;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.entity.MfBusApplyAssureInfo;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.auth.entity.MfCusAgenciesCredit;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.entity.MfCusPorductCredit;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.auth.feign.MfCusPorductCreditFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.calc.core.entity.MfRepayAmt;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.entity.MfSysFeeItem;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.calc.penalty.entity.MfBusAppPenaltyMain;
import app.component.calc.penalty.feign.MfBusAppPenaltyMainFeign;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.feign.EvalInfoFeign;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.cus.cusgroup.entity.MfCusGroup;
import app.component.cus.cusgroup.feign.MfCusGroupFeign;
import app.component.cus.entity.*;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.feign.MfCusAssureCompanyFeign;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParLoanuse;
import app.component.nmd.entity.ParmDic;
import app.component.nmd.feign.MfSysRateBaseFeign;
import app.component.nmd.feign.ParLoanuseFeign;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppChild;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.repayplan.feign.MfRepayPlanFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.risk.entity.RiskBizItemRel;
import app.component.riskinterface.RiskInterfaceFeign;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.feign.SysTaskInfoFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.tech.wkf.bo.WfExecutionFeign;
import app.tech.wkf.entity.WfExecution;
import app.util.DataUtil;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.alibaba.fastjson.JSON;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


/**
 * Title: MfBusApplyAction.java
 * Description:getListPage
 *
 * @author:kaifa@dhcc.com.cn
 * @Sat May 21 10:40:47 CST 2016
 **/
@Controller
@RequestMapping("/mfBusApply")
public class MfBusApplyController extends BaseFormBean {
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    //注入MfBusApplyBo
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private WkfBusInterfaceFeign wkfBusInterfaceFeign;
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;
    @Autowired
    private CollateralInterfaceFeign collateralInterfaceFeign;
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private MfBusApplyWkfFeign mfBusApplyWkfFeign;
    @Autowired
    private NmdInterfaceFeign nmdInterfaceFeign;
    @Autowired
    private RiskInterfaceFeign riskInterfaceFeign;//风险配置接口
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
    @Autowired
    private MfBusAppKindFeign mfBusAppKindFeign;
    @Autowired
    private WfExecutionFeign wfExecutionFeign;
    @Autowired
    private BusViewInterfaceFeign busViewInterfaceFeign;
    @Autowired
    private MfBusApplyHisFeign mfBusApplyHisFeign;
    @Autowired
    private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;
    @Autowired
    private CalcInterfaceFeign calcInterfaceFeign;
    @Autowired
    private TaskFeign taskFeign;
    @Autowired
    private ParLoanuseFeign parLoanuseFeign;
    @Autowired
    private MfCusPorductCreditFeign mfCusPorductCreditFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;
    @Autowired
    private MfBusAppPenaltyMainFeign mfBusAppPenaltyMainFeign;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private MfCusCreditContractFeign mfCusCreditContractFeign;
    @Autowired
    private MfBusAgenciesFeign mfBusAgenciesFeign;

    @Autowired
    private SysTaskInfoFeign sysTaskInfoFeign;
    @Autowired
    private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
    @Autowired
    private MfCusGroupFeign mfCusGroupFeign;
    @Autowired
    private MfLoanApplyFeign mfLoanApplyFeign;
    @Autowired
    private MfCusAssureCompanyFeign mfCusAssureCompanyFeign;
    @Autowired
    private BusiCacheInterface busiCacheInterface;
    @Autowired
    private MfRepayPlanFeign mfRepayPlanFeign;
    @Autowired
    private MfBusApplyFincJxhjFeign mfBusApplyFincJxhjFeign;

    @Autowired
    private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
    @Autowired
    private PledgeBaseInfoFeign pledgeBaseInfoFeign;
    @Autowired
    private EvalInfoFeign evalInfoFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private MfSysRateBaseFeign mfSysRateBaseFeign;
    @Autowired
    private MfCusCreditApplyFeign mfCusCreditApplyFeign;
    @Autowired
    private AppInterfaceFeign appInterfaceFeign;
    @Autowired
    private AssureInterfaceFeign assureInterfaceFeign;
    @Autowired
    private MfBusAppFeeFeign mfBusAppFeeFeign;

    //全局变量

    /**
     * 方法描述： 跳转至进件列表页面
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-11-1 上午11:19:25
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model, String queryType) throws Exception {
        ActionContext.initialize(request,
                response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        CodeUtils codeUtils = new CodeUtils();
        JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("CUS_TYPE");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        JSONArray gdCusTypeJsonArray = codeUtils.getJSONArrayByKeyName("GD_CUS_TYPE");
        this.getHttpRequest().setAttribute("gdCusTypeJsonArray", gdCusTypeJsonArray);
        JSONArray appStsJsonArray = codeUtils.getJSONArrayByKeyName("APP_STS");
        this.getHttpRequest().setAttribute("appStsJsonArray", appStsJsonArray);
        JSONArray kindTypeJsonArray = codeUtils.getJSONArrayByKeyName("KIND_NO");
        this.getHttpRequest().setAttribute("kindTypeJsonArray", kindTypeJsonArray);
        //办理阶段
        //根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        this.getHttpRequest().setAttribute("flowNodeJsonArray", flowNodeJsonArray);

        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setKindProperty("2");
        mfSysKind.setUseFlag("1");
        mfSysKind.setBrNo(User.getOrgNo(request));
        mfSysKind.setRoleNoArray(User.getRoleNo(request));
        List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
        String kindNo = "";
        if (mfSysKindList != null && mfSysKindList.size() > 0) {
            kindNo = mfSysKindList.get(0).getKindNo();
        }
        model.addAttribute("kindNo", kindNo);
        model.addAttribute("queryType", queryType);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_List";
    }


    /**
     * 方法描述：进件列表
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-11-1 上午11:18:51
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Ipage ipage, Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            mfCusAndApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusAndApply.setCustomSorts(ajaxData);//自定义排序参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);//我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
            ipage = mfBusApplyFeign.getCusAndApply(ipage);
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

    @RequestMapping(value = "/findBusHandlingByPageAjaxExcel")
    @ResponseBody
    public void findBusHandlingByPageAjaxExcel(String tableId) throws Exception {
        //读取出文件的所有字节,并将所有字节写出给客户端
        ActionContext.initialize(request, response);
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            List<MfCusAndApply> mfCusAndApplyList = mfBusApplyFeign.findBusHandlingByPageAjaxExcel(mfCusAndApply);
            ExpExclUtil eu = new ExpExclUtil();
            HSSFWorkbook wb = eu.expExclTableForList(tableId, mfCusAndApplyList, null, false, "");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download; charset=utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("busHandling.xls", "UTF-8"));
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
     * 方法描述：查询业务办理中数据
     *
     * @param ipage
     * @param pageNo
     * @param pageSize
     * @param tableId
     * @param tableType
     * @param ajaxData
     * @return
     * @throws Exception Map<String,Object>
     * @author 仇招
     * @date 2018年8月27日 下午9:46:46
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findBusHandlingByPageAjax")
    @ResponseBody
    public Map<String, Object> findBusHandlingByPageAjax(Ipage ipage, Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            mfCusAndApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusAndApply.setCustomSorts(ajaxData);//自定义排序参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);//我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
            ipage = mfBusApplyFeign.findBusHandlingByPageAjax(ipage);
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
     * 打开渠道商推荐的客户申请详情列表
     */
    @RequestMapping(value = "/getTrhListPage")
    public String getTrhListPage(Model model, String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request,
                response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        CodeUtils codeUtils = new CodeUtils();
        JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("CUS_TYPE");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        JSONArray gdCusTypeJsonArray = codeUtils.getJSONArrayByKeyName("GD_CUS_TYPE");
        this.getHttpRequest().setAttribute("gdCusTypeJsonArray", gdCusTypeJsonArray);
        JSONArray appStsJsonArray = codeUtils.getJSONArrayByKeyName("APP_STS");
        this.getHttpRequest().setAttribute("appStsJsonArray", appStsJsonArray);
        JSONArray kindTypeJsonArray = codeUtils.getJSONArrayByKeyName("KIND_NO");
        this.getHttpRequest().setAttribute("kindTypeJsonArray", kindTypeJsonArray);
        //办理阶段
        //根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        this.getHttpRequest().setAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_trhList";
    }

    /**
     * 获取渠道商的推荐客户的申请详情列表
     */
    @RequestMapping(value = "/findTrenchApplyAjax")
    @ResponseBody
    public Map<String, Object> findTrenchApplyAjax(String ajaxData, int pageSize, int pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        String cusNo = request.getParameter("cusNo");
        mfCusAndApply.setChannelSourceNo(cusNo);
        try {
            mfCusAndApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusAndApply.setCustomSorts(ajaxData);//自定义排序参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);//我的筛选
//			this.getRoleConditions(mfCusAndApply,"1000000065");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
            //自定义查询Bo方法
            ipage = mfBusApplyFeign.getCusAndApplyForTrench(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
//			logger.error("获取渠道商的客户业务详情列表失败",e);
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
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
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formapply0001 = formService.getFormData("apply0001");
            getFormValue(formapply0001, getMapByJson(ajaxData));
            if (this.validateFormData(formapply0001)) {
                MfBusApply mfBusApply = new MfBusApply();
                setObjValue(formapply0001, mfBusApply);
                mfBusApplyFeign.insert(mfBusApply);
                dataMap.put("vouType", mfBusApply.getVouType());
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
     * 方法描述： ajax 异步 单个字段或多个字段更新(单子段编辑)
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-11-1 上午11:24:19
     */
    @RequestMapping(value = "/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApplyJsp = new MfBusApply();
        dataMap = getMapByJson(ajaxData);
        FormData formapply0006 = formService.getFormData(dataMap.get("formId").toString());
        getFormValue(formapply0006, getMapByJson(ajaxData));
        setObjValue(formapply0006, mfBusApplyJsp);
        MfBusApply mfBusApply = mfBusApplyFeign.getById(mfBusApplyJsp);
        if (mfBusApply != null) {
            try {
                mfBusApply = (MfBusApply) EntityUtil.reflectionSetVal(mfBusApply, mfBusApplyJsp, getMapByJson(ajaxData));
                String validateMes = mfBusApplyFeign.validateBuaApply(mfBusApply);
                if (StringUtil.isNotEmpty(validateMes)) {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", validateMes);
                } else {
                    dataMap.put("mfBusApply", mfBusApply);
                    mfBusApplyFeign.updateByOne(dataMap);
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
                throw e;
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
        }
        return dataMap;
    }

    /**
     * 选择共借人后更新
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjaxByCoborr")
    @ResponseBody
    public Map<String, Object> updateAjaxByCoborr(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApplyJsp = new MfBusApply();
        dataMap = getMapByJson(ajaxData);
        FormData formapply0006 = formService.getFormData(dataMap.get("formId").toString());
        getFormValue(formapply0006, getMapByJson(ajaxData));
        setObjValue(formapply0006, mfBusApplyJsp);
        MfBusApply mfBusApply = mfBusApplyFeign.getById(mfBusApplyJsp);
        if (mfBusApply != null) {
            try {
                mfBusApply = (MfBusApply) EntityUtil.reflectionSetVal(mfBusApply, mfBusApplyJsp, getMapByJson(ajaxData));
                String validateMes = mfBusApplyFeign.validateBuaApply(mfBusApply);
                if (StringUtil.isNotEmpty(validateMes)) {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", validateMes);
                } else {
                    dataMap.put("mfBusApply", mfBusApply);
                    mfBusApplyFeign.updateByOne(dataMap);
                    String  cusNos = dataMap.get("coborrNo").toString();
                    MfCusCustomer mfCusCustomer = new MfCusCustomer();
                    mfCusCustomer.setCusNo(cusNos);
                    Ipage ipage = this.getIpage();
                    ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
                    ipage =  mfCusCustomerFeign.getCusListByCusNos(ipage);
                    JsonTableUtil jtu = new JsonTableUtil();
                    String tableHtml = jtu.getJsonStr("tablemfCusBorrowerList", "tableTag", (List) ipage.getResult(), ipage, true);
                    dataMap.put("tableData",tableHtml);
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            FormData formapply0002 = formService.getFormData("apply0002");
            getFormValue(formapply0002, getMapByJson(ajaxData));
            if (this.validateFormData(formapply0002)) {
                setObjValue(formapply0002, mfBusApply);
                mfBusApplyFeign.update(mfBusApply);
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
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formapply0002 = formService.getFormData("apply0002");
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        getObjValue(formapply0002, mfBusApply, formData);
        if (mfBusApply != null) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        dataMap.put("appAmt", mfBusApply.getAppAmt());
        dataMap.put("termShow", mfBusApply.getTermShow());
        dataMap.put("appSts", mfBusApply.getAppSts());
        dataMap.put("wkfAppId", mfBusApply.getWkfAppId());
        return dataMap;
    }
    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getmfIdAjax")
    @ResponseBody
    public Map<String, Object> getmfIdAjax(String ajaxData, String wkfAppId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        WfExecution wfExecution=new WfExecution();
        wfExecution.setAppId(wkfAppId);
     wfExecution=wfExecutionFeign.getWkfId(wfExecution);


        if (wfExecution!=null&&wfExecution.getActivityname().equals("apply_approval")) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        return dataMap;
    }

    /**
     * 方法描述： 根据产品编号判断是否发生了业务
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-7-30 下午5:24:58
     */
    @RequestMapping(value = "/getMfBusApplyByKindNoAjax")
    @ResponseBody
    public Map<String, Object> getMfBusApplyByKindNoAjax(String ajaxData, String kindNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setKindNo(kindNo);
            List<MfBusApply> mfBusApplyList = mfBusApplyFeign.getApplyList(mfBusApply);
            dataMap.put("flag", "success");
            if (mfBusApplyList != null && mfBusApplyList.size() > 0) {
                dataMap.put("hasBiz", "1");
            } else {
                dataMap.put("hasBiz", "0");
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
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        try {
            mfBusApplyFeign.delete(mfBusApply);
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

    @RequestMapping(value = "/getPageFinUse")
    public String getPageFinUse(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        //融资用途的选项
        ParmDic parmDic = new ParmDic();
        parmDic.setKeyName("TRADE");
        List parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
        JSONArray array = JSONArray.fromObject(parmDicList);
        JSONObject json = new JSONObject();
        json.put("tradeList", array);

        model.addAttribute("query", "");
        return "/component/app/pageFinUse";
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     *  此功能废弃，请使用{@link MfBusApplyController#inputQuery}
     */

    @RequestMapping(value = "/input")
    public String input(Model model, String kindNo, String cusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formapply0007;
        //根据产品种类编号获取产品信息
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
        String vouType = mfSysKind.getVouTypeDef();
        mfSysKind.setVouType(vouType);//表单显示默认的担保方式
        String busModel = mfSysKind.getBusModel();//业务模式

        if (busModel.equals(BizPubParm.BUS_MODEL_5)) {
            formapply0007 = formService.getFormData("apply0007bl");
        } else {
            formapply0007 = formService.getFormData("apply0007");
        }

        //根据客户号获取客户信息
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        //保理申请
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setCusNo(cusNo);
        mfBusApply.setCusName(mfCusCustomer.getCusName());
        mfBusApply.setTermType(mfSysKind.getTermType());
        mfBusApply.setOverRate(mfSysKind.getOverRate());
        mfBusApply.setAppTime(DateUtil.getDateTime());
        mfBusApply.setAppTimeShow(DateUtil.getDate());
        //这里初始化申请ID了，所以新增时在service层没有再生成ID
        mfBusApply.setAppId(WaterIdUtil.getWaterId("app"));
        mfBusApply.setCusMngName(User.getRegName(request));
        //处理金额，double大于十位会变成科学计数法
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        if ((mfSysKind.getMinAmt() != null) && (mfSysKind.getMaxAmt() != null)) {
            double minAmtD = mfSysKind.getMinAmt();
            double maxAmtD = mfSysKind.getMaxAmt();
            String minAmt = df.format(minAmtD);//融资金额下限
            String maxAmt = df.format(maxAmtD);//融资金额上限
            String amt = minAmt + "-" + maxAmt + "元";
            this.changeFormProperty(formapply0007, "appAmt", "alt", amt);
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
                term = minTerm + "日-" + maxTerm + "日";
            }else {
            }
            this.changeFormProperty(formapply0007, "term", "alt", term);
        }
        getObjValue(formapply0007, mfBusApply);
        getObjValue(formapply0007, mfSysKind);
        model.addAttribute("formapply0007", formapply0007);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_Input";
    }

    /**
     * 方法描述： 进件列表业务申请
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-2-12 下午3:34:38
     */
    @RequestMapping(value = "/applyInput")
    public String applyInput(Model model, String ajaxData, String kindNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fromPage", ajaxData);
        //根据产品种类编号获取产品信息
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
        String vouType = mfSysKind.getVouTypeDef();
        mfSysKind.setVouType(vouType);//表单显示默认的担保方式
        String busModel = mfSysKind.getBusModel();//业务模式
        MfBusApply mfBusApply = new MfBusApply();

        String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_input, null, null, User.getRegNo(request));
        FormData formapply0007_query = formService.getFormData(formId);

        mfBusApply.setFincRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getFincRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
        mfBusApply.setOverRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getOverFltRateDef(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
        mfBusApply.setCmpdRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getCmpFltRateDef(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
        getObjValue(formapply0007_query, mfBusApply);
        getObjValue(formapply0007_query, mfSysKind);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfSysKind.getRateType()).getRemark();
        this.changeFormProperty(formapply0007_query, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0007_query, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0007_query, "cmpdRate", "unit", rateUnit);
        //处理还款方式
        List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
        this.changeFormProperty(formapply0007_query, "repayType", "optionArray", repayTypeList);
        this.changeFormProperty(formapply0007_query, "rateType", "initValue", mfSysKind.getRateType());
        JSONObject json = new JSONObject();
        json.put("rateType", JSONObject.fromObject(rateTypeMap));
        ajaxData = json.toString();
        model.addAttribute("formapply0007_query", formapply0007_query);
        model.addAttribute("ajaxData", json);
        model.addAttribute("busModel", busModel);
        model.addAttribute("kindNo", kindNo);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_applyInput";
//		return "MfBusApply_applyInput";
    }


    /**
     * 方法描述： 打开业务申请页面
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-8-21 下午3:51:50
     */
    @RequestMapping(value = "/inputQuery")
    public String inputQuery(Model model, String ajaxData, String cusNo, String kindNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fromPage", ajaxData);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setCusNo(cusNo);
        mfBusApply.setAppSts(BizPubParm.APP_STS_UN_COMPLETE);
        mfBusApply = mfBusApplyFeign.getByCusNoAppSts(mfBusApply);
        if (mfBusApply != null) {
            kindNo = mfBusApply.getKindNo();
        } else {
            mfBusApply = new MfBusApply();
        }

        //根据产品种类编号获取产品信息
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
        String vouType = mfSysKind.getVouTypeDef();
        mfSysKind.setVouType(vouType);//表单显示默认的担保方式
        String busModel = mfSysKind.getBusModel();//业务模式
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        String cusType = mfCusCustomer.getCusType();
        String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_input, null, null, User.getRegNo(request));
        FormData formapply0007_query = formService.getFormData(formId);

        mfBusApply.setFincRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getFincRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
        mfBusApply.setOverRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getOverRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
        mfBusApply.setCmpdRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getCmpdRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
        mfBusApply.setCusMngName(mfCusCustomer.getCusMngName());
        mfBusApply.setCusMngNo(mfCusCustomer.getCusMngNo());
        mfBusApply.setChannelSource(mfCusCustomer.getChannelSource());
        mfBusApply.setChannelSourceNo(mfCusCustomer.getChannelSourceNo());
        getObjValue(formapply0007_query, mfBusApply);
        getObjValue(formapply0007_query, mfSysKind);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfSysKind.getRateType()).getRemark();
        this.changeFormProperty(formapply0007_query, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0007_query, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0007_query, "cmpdRate", "unit", rateUnit);
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
        this.changeFormProperty(formapply0007_query, "baseRateType", "optionArray", baseRateTypeList);
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
        this.changeFormProperty(formapply0007_query, "icType", "optionArray", icTypeList);
        //处理还款方式
        List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
        this.changeFormProperty(formapply0007_query, "repayType", "optionArray", repayTypeList);
        this.changeFormProperty(formapply0007_query, "rateType", "initValue", mfSysKind.getRateType());
        JSONObject json = new JSONObject();
        json.put("rateType", JSONObject.fromObject(rateTypeMap));
        ajaxData = json.toString();
        model.addAttribute("formapply0007_query", formapply0007_query);
        model.addAttribute("ajaxData", json);
        model.addAttribute("busModel", busModel);
        model.addAttribute("kindNo", kindNo);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("cusType", cusType);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_InputQuery";
    }

    /**
     * @param @return
     * @param @throws Exception    参数
     * @return String    返回类型
     * @throws
     * @Title: inputUpdateQuery
     * @Description: 打开业务申请编辑页面
     */
    @RequestMapping(value = "/inputUpdateQuery")
    public String inputUpdateQuery(Model model, String ajaxData, String cusNo, String kindNo, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fromPage", ajaxData);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);

        mfBusApply.setBreedName1(mfBusApply.getBreedName());
        mfBusApply.setBreedNo1(mfBusApply.getBreedNo());
        mfBusApply.setVouType1(mfBusApply.getVouType());
        mfBusApply.setAgenciesId1(mfBusApply.getAgenciesId());
        mfBusApply.setAgenciesName1(mfBusApply.getAgenciesName());
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
            mfBusApply.setAuthority(mfBusApplySecond.getAuthority());
            mfBusApply.setSurveyOpinion(mfBusApplySecond.getSurveyOpinion());
            mfBusApply.setStartUpSituation(mfBusApplySecond.getStartUpSituation());
            mfBusApply.setConstructionScope(mfBusApplySecond.getConstructionScope());
            mfBusApply.setOldAppName(mfBusApplySecond.getOldAppName());
            mfBusApply.setCollectAccount(mfBusApplySecond.getCollectAccount());
            mfBusApply.setCollectAccName(mfBusApplySecond.getCollectAccName());
            mfBusApply.setCollectBank(mfBusApplySecond.getCollectBank());
            mfBusApply.setCollectAccId(mfBusApplySecond.getCollectAccId());
            mfBusApply.setBondAccount(mfBusApplySecond.getBondAccount());
            mfBusApply.setBondAccName(mfBusApplySecond.getBondAccName());
            mfBusApply.setBondBank(mfBusApplySecond.getBondBank());
            mfBusApply.setBondAccId(mfBusApplySecond.getBondAccId());
        }
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setCreditAppId(mfBusApply.getCreditAppId());
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
        if(mfCusCreditContract!=null){
            mfBusApply.setCreditSum(mfCusCreditContract.getCreditSum());
            mfBusApply.setCreditAmt(mfCusCreditContract.getAuthBal());
        }
        dataMap.put("updateType", "1");
        //根据产品种类编号获取产品信息
        MfBusAppKind mfBusAppkind = new MfBusAppKind();
        mfBusAppkind.setAppId(appId);
        mfBusAppkind = mfBusAppKindFeign.getById(mfBusAppkind);
        String cmpdRateType = mfBusAppkind.getCmpdRateType();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        kindNo = mfBusAppkind.getKindNo();
        //根据产品种类编号获取产品信息
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
        String vouType = mfSysKind.getVouTypeDef();
        mfSysKind.setVouType(vouType);//表单显示默认的担保方式
        String busModel = mfSysKind.getBusModel();//业务模式
        String formId = "";
        if (WKF_NODE.apply_rich.getNodeNo().equals(mfBusApply.getNodeNo())) {

            formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_rich, null, null, User.getRegNo(request));
        } else {

            formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_input, null, null, User.getRegNo(request));
        }
        if("6".equals(mfBusApply.getLoanKind())){
            formId = "comLoanApplyBase_GCDB_ZXCK";
        }else if("5".equals(mfBusApply.getLoanKind())){
            formId = "comLoanApplyBase_GCDB_XB";
        }
        FormData formapply0007_query = formService.getFormData(formId);
        mfBusApply.setFincRate(MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusApply.getFincRate(), Integer.parseInt(mfBusAppkind.getYearDays()), Integer.parseInt(mfBusAppkind.getRateDecimalDigits())));
        mfBusApply.setOverRate(MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusApply.getOverRate(), Integer.parseInt(mfBusAppkind.getYearDays()), Integer.parseInt(mfBusAppkind.getRateDecimalDigits())));
        mfBusApply.setCmpdRate(MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusApply.getCmpdRate(), Integer.parseInt(mfBusAppkind.getYearDays()), Integer.parseInt(mfBusAppkind.getRateDecimalDigits())));
        String IF_COUNT_APPAMT = new CodeUtils().getSingleValByKey("IF_COUNT_APPAMT");// 根据评估是否计算申请金额 0-否 1-是
        if("1".equals(IF_COUNT_APPAMT)){
            if(0==mfBusApply.getAppAmt()|| "".equals(mfBusApply.getAppAmt())){//申请金额为0或为空  申请金额=评估价值*抵质押率(多押品相加)
                List<MfBusCollateralDetailRel> detailList = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(appId, "pledge");
                if (detailList != null && detailList.size() > 0) {
                    Double evalValue=0.00; //押品价值
                    for (int i = 0; i < detailList.size(); i++) {
                        EvalInfo evalInfo = new EvalInfo();
                        evalInfo.setCollateralId(detailList.get(i).getCollateralId());
                        evalInfo = evalInfoFeign.getLatest(evalInfo);
                        if(null !=evalInfo){
                            if(null!=evalInfo.getEvalAmount() && null !=evalInfo.getMortRate()){
                                double mortRate = evalInfo.getMortRate() / 100;
                                evalValue+= MathExtend.multiply(evalInfo.getEvalAmount(),mortRate);
                            }
                        }
                    }
                    if(0.00!=evalValue){
                        double divide = MathExtend.divide(evalValue, 1000, 0, MathExtend.RoundingType.DOWN);
                        double appAmt = MathExtend.multiply(divide, 1000);
                        mfBusApply.setAppAmt(appAmt);
                        model.addAttribute("appConfirmAmt", String.valueOf(appAmt));
                    }

                }
            }
        }
        getObjValue(formapply0007_query, mfBusAppkind);
        getObjValue(formapply0007_query, mfBusApply);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        this.changeFormProperty(formapply0007_query, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0007_query, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0007_query, "cmpdRate", "unit", rateUnit);
        //处理还款方式
        List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfBusAppkind.getRepayType(), mfBusApply.getRepayType());
        this.changeFormProperty(formapply0007_query, "repayType", "optionArray", repayTypeList);
        this.changeFormProperty(formapply0007_query, "rateType", "initValue", mfBusApply.getRateType());
        this.changeFormProperty(formapply0007_query, "kindNo", "initValue", kindNo);
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
        this.changeFormProperty(formapply0007_query, "baseRateType", "optionArray", baseRateTypeList);

        //处理金额，double大于十位会变成科学计数法11111111
        DecimalFormat df = new DecimalFormat(",##0.00");
        if ((mfBusAppkind.getMinAmt() != null) && (mfBusAppkind.getMaxAmt() != null)) {
            double minAmtD = mfBusAppkind.getMinAmt();
            double maxAmtD = mfBusAppkind.getMaxAmt();
            String minAmt = df.format(minAmtD);//融资金额下限
            String maxAmt = df.format(maxAmtD);//融资金额上限
            String amt = minAmt + "-" + maxAmt + "元";
            this.changeFormProperty(formapply0007_query, "appAmt", "alt", amt);
            dataMap.put("minAmt", String.valueOf(minAmtD));
            dataMap.put("maxAmt", String.valueOf(maxAmtD));
        }

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
        this.changeFormProperty(formapply0007_query, "icType", "optionArray", icTypeList);
        //处理期限
        if (null != mfBusAppkind.getTermType()) {
            String termType = mfBusAppkind.getTermType();//合同期限 1月 2日
            int minTerm = mfBusAppkind.getMinTerm();//合同期限下限
            int maxTerm = mfBusAppkind.getMaxTerm();//合同期限上限
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
            this.changeFormProperty(formapply0007_query, "term", "alt", term);
        }

        //利率范围
        if ((mfBusAppkind.getMinFincRate() != null) && (mfBusAppkind.getMaxFincRate() != null)) {
            double minFincRateD = MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusAppkind.getMinFincRate(), Integer.parseInt(mfBusAppkind.getYearDays()), Integer.parseInt(mfBusAppkind.getRateDecimalDigits()));
            double maxFincRateD = MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusAppkind.getMaxFincRate(), Integer.parseInt(mfBusAppkind.getYearDays()), Integer.parseInt(mfBusAppkind.getRateDecimalDigits()));
            String minFincRate = df.format(minFincRateD);//融资利率下限
            String maxFincRate = df.format(maxFincRateD);//融资利率上限
            String fincRate = minFincRate + "-" + maxFincRate + rateUnit;
            this.changeFormProperty(formapply0007_query, "fincRate", "alt", fincRate);
            dataMap.put("minFincRate", String.valueOf(minFincRateD));
            dataMap.put("maxFincRate", String.valueOf(maxFincRateD));
        }
        String jsonArrayStr = prdctInterfaceFeign.getFincUse(kindNo);
        com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
        //贷款投向一级分类
        dataMap.put("fincUse", jsonArray);
        dataMap.put("fincUseCode", mfBusApply.getFincUse());
        dataMap.put("fincUseName", "1111111");

        //处理贷款投向二级分类
        List<Map<String, String>> fincUseSmMapList = new ArrayList<Map<String, String>>();
        if (StringUtil.isNotEmpty(mfBusApply.getFincUse())) {
            ParLoanuse parLoanuse = new ParLoanuse();
            parLoanuse.setUseNo(mfBusApply.getFincUse());
            parLoanuse = parLoanuseFeign.getById(parLoanuse);
            if (parLoanuse != null) {
                ParLoanuse par = new ParLoanuse();
                par.setUplev(parLoanuse.getUseNo());
                List<ParLoanuse> parList = parLoanuseFeign.getFincUseSm(par);
                for (int i = 0; i < parList.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    if (parList.get(i).getUseNo().equals(mfBusApply.getFincUseSm())) {
                        map.put("selected", "true");
                    }
                    map.put("id", parList.get(i).getUseNo());
                    map.put("name", parList.get(i).getUseName());
                    fincUseSmMapList.add(map);
                }
            }
            dataMap.put("fincUseSm", fincUseSmMapList);
        }

        String feeShowFlag = new CodeUtils().getSingleValByKey("APPLY_NODE_FEE_SHOW_FLAG");
        dataMap.put("feeShowFlag", feeShowFlag);
        dataMap.put("cmpdRateType", cmpdRateType);
        Map<String, Object> map = creditApplyInterfaceFeign.getByCusNoAndKindNo(cusNo, kindNo);
        dataMap.putAll(map);
        JSONObject json = new JSONObject();
        json.put("rateType", JSONObject.fromObject(rateTypeMap));
        json.put("data", JSONObject.fromObject(dataMap));
        ajaxData = json.toString();
        if (BizPubParm.YES_NO_Y.equals(feeShowFlag)) {
            //产品下配置的费用信息
            MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
            mfSysFeeItem.setFeeStdNo(kindNo);
            mfSysFeeItem.setNodeNo(WKF_NODE.apply_input.getNodeNo());
            Ipage ipage = this.getIpage();
            //自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfSysFeeItem", mfSysFeeItem));
            JsonTableUtil jtu = new JsonTableUtil();
            List<MfSysFeeItem> mfSysFeeItemList = (List<MfSysFeeItem>) (List<MfSysFeeItem>) calcInterfaceFeign.findFeeByPage(ipage).getResult();
            String feeHtmlStr = "";
            if (mfSysFeeItemList != null && mfSysFeeItemList.size() > 0) {
                feeHtmlStr = jtu.getJsonStr("tableapplynodefeelist", "tableTag", mfSysFeeItemList, null, true);
            }
            dataMap.put("feeHtmlStr", feeHtmlStr);
        }
        model.addAttribute("formapply0007_query", formapply0007_query);
        model.addAttribute("ajaxData", json);
        model.addAttribute("kindNo", kindNo);
        model.addAttribute("appId", appId);
        model.addAttribute("nodeNo", mfBusApply.getNodeNo());
        model.addAttribute("busModel", busModel);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("wkfAppId", mfBusApply.getWkfAppId());
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("processId", mfBusApply.getPrimaryApplyProcessId());
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_InputUpdateQuery";
    }

    /**
     * 方法描述： 获得申请数量
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-2-23 下午3:07:13
     */
    @RequestMapping(value = "/getApplyCountAjax")
    @ResponseBody
    public Map<String, Object> getApplyCountAjax() throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            mfBusApply.setAppSts(BizPubParm.APP_STS_PASS);
            Integer applyCount = mfBusApplyFeign.getApplyCount(mfBusApply);
            dataMap.put("flag", "success");
            dataMap.put("applyCount", applyCount);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/chooseFormAjax")
    @ResponseBody
    public Map<String, Object> chooseFormAjax(String ajaxData, String kindNo, String cusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
            //设置项目说明信息默认为空
            mfSysKind.setRemark("");
            List<ParmDic> parmDicList = getVouTypeOtherSelectByNoAjax(mfSysKind);
            dataMap.put("parmDicList", parmDicList);
            String cmpdRateType = mfSysKind.getCmpdRateType();
            String vouType = mfSysKind.getVouTypeDef();
            mfSysKind.setVouType(vouType);//表单显示默认的担保方式
            String busModel = mfSysKind.getBusModel();//业务模式

            String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_input, null, null, User.getRegNo(request));
            FormData formapply0007_query = formService.getFormData(formId);

            //根据客户号获取客户信息
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
            //保理申请
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setCusNo(cusNo);
            mfBusApply.setCusName(mfCusCustomer.getCusName());
            mfBusApply.setTermType(mfSysKind.getTermType());
            mfBusApply.setFincRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getFincRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
            mfBusApply.setChannelSource(mfCusCustomer.getChannelSource());
            mfBusApply.setChannelSourceNo(mfCusCustomer.getChannelSourceNo());
            //利率浮动值 或者是利率值
            Double overFloat = mfSysKind.getOverFltRateDef();
            Double cmpdFloat = mfSysKind.getCmpFltRateDef();
            Double overRate = mfSysKind.getOverRate();
            Double cmpdRate = mfSysKind.getCmpdRate();
            if (overFloat == null) {
                overFloat = 0.00;
            }
            if (cmpdFloat == null) {
                cmpdFloat = 0.00;
            }
            if (overRate == null) {
                overRate = 0.00;
            }
            if (cmpdRate == null) {
                cmpdRate = 0.00;
            }
            mfBusApply.setOverFloat(overFloat);
            mfBusApply.setCmpdFloat(cmpdFloat);
            mfBusApply.setOverRate(MathExtend.showRateMethod(mfSysKind.getRateType(), overRate, Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
            mfBusApply.setCmpdRate(MathExtend.showRateMethod(mfSysKind.getRateType(), cmpdRate, Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
            mfBusApply.setAppTime(DateUtil.getDateTime());
            mfBusApply.setAppTimeShow(DateUtil.getDate());
            //这里初始化申请ID了，所以新增时在service层没有再生成ID
            mfBusApply.setAppId(WaterIdUtil.getWaterId("app"));
            mfBusApply.setCusMngName(User.getRegName(request));
            mfBusApply.setCusMngNo(User.getRegNo(request));
            mfBusApply.setManageOpNo1(User.getRegNo(request));
            mfBusApply.setManageOpName1(User.getRegName(request));
            mfBusApply.setBusModel(mfSysKind.getBusModel());
            mfBusApply.setKindName(mfSysKind.getKindName());
            mfBusApply.setRepayType(mfSysKind.getRepayTypeDef());
            mfBusApply.setVouType(vouType);
            // 贷款类型 1 新增贷款 2 结清再贷
            String loanKind = "1";
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setCusNo(cusNo);
            String fincSts = "'5','6','7','A','B','C'";// 申请状态1未提交2审批中3已否决4审批通过待放款5放款已复核6已还款未复核7完结8补充资料A呆账B呆滞C核销
            mfBusFincApp.setFincSts(fincSts);
            List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getFincListByCusNoAndSts(mfBusFincApp);
            if (null != mfBusFincAppList && mfBusFincAppList.size() > 0) {
                loanKind = "2";
            }
            mfBusApply.setLoanKind(loanKind);


            //如果申请过业务并且未提交app_sts=0  则不允许申请新业务  (一个客户只能有一笔未提交的业务)
            MfBusApply parmBusApply = new MfBusApply();
            parmBusApply.setCusNo(cusNo);
            parmBusApply.setAppSts(BizPubParm.APP_STS_UN_COMPLETE);
            parmBusApply = mfBusApplyFeign.getByCusNoAppSts(parmBusApply);
            if (parmBusApply != null) {
                if (kindNo.equals(parmBusApply.getKindNo())) {
                    PropertyUtils.copyProperties(mfBusApply, parmBusApply);
                    mfBusApply.setAppTime(DateUtil.getDateTime());
                    mfBusApply.setAppTimeShow(DateUtil.getDate());
                }
            }

            //处理金额，double大于十位会变成科学计数法11111111
            DecimalFormat df = new DecimalFormat(",##0.00");
            if ((mfSysKind.getMinAmt() != null) && (mfSysKind.getMaxAmt() != null)) {
                double minAmtD = mfSysKind.getMinAmt();
                double maxAmtD = mfSysKind.getMaxAmt();
                String minAmt = df.format(minAmtD);//融资金额下限
                String maxAmt = df.format(maxAmtD);//融资金额上限
                String amt = minAmt + "-" + maxAmt + "元";
                this.changeFormProperty(formapply0007_query, "appAmt", "alt", amt);
                dataMap.put("minAmt", String.valueOf(minAmtD));
                dataMap.put("maxAmt", String.valueOf(maxAmtD));
            }
            //处理产品总融资金额上限
            if (mfSysKind.getTotalMaxAmt() != null && mfSysKind.getTotalMaxAmt()>0.00) {
                //产品可以融资的金额
                double canApplyAmt = mfBusApplyFeign.getCanApplyAmt(mfSysKind);
                dataMap.put("canApplyAmt", String.valueOf(canApplyAmt));
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
                this.changeFormProperty(formapply0007_query, "term", "alt", term);
            }
            mfBusApply.setCusMngName(mfCusCustomer.getCusMngName());
            mfBusApply.setCusMngNo(mfCusCustomer.getCusMngNo());
            getObjValue(formapply0007_query, mfSysKind);
            getObjValue(formapply0007_query, mfBusApply);
            //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfSysKind.getRateType()).getRemark();
            this.changeFormProperty(formapply0007_query, "fincRate", "unit", rateUnit);
            this.changeFormProperty(formapply0007_query, "overRate", "unit", rateUnit);
            this.changeFormProperty(formapply0007_query, "cmpdRate", "unit", rateUnit);

            //利率范围
            if ((mfSysKind.getMinFincRate() != null) && (mfSysKind.getMaxFincRate() != null)) {
                double minFincRateD = MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getMinFincRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits()));
                double maxFincRateD = MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getMaxFincRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits()));
                String minFincRate = df.format(minFincRateD);//融资利率下限
                String maxFincRate = df.format(maxFincRateD);//融资利率上限
                String fincRate = minFincRate + "-" + maxFincRate + rateUnit;
                this.changeFormProperty(formapply0007_query, "fincRate", "alt", fincRate);
                dataMap.put("minFincRate", String.valueOf(minFincRateD));
                dataMap.put("maxFincRate", String.valueOf(maxFincRateD));
            }

            //处理还款方式
            List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
            this.changeFormProperty(formapply0007_query, "repayType", "optionArray", repayTypeList);
            //处理产品子类
            String sunKindNo = mfSysKind.getSubKindNo();
            if (StringUtil.isNotEmpty(sunKindNo)) {
                String[] subKindArray = mfSysKind.getSubKindNo().split("\\|");
                Map<String, String> dicMap = new CodeUtils().getMapByKeyName("SUB_KIND_TYPE");
                List<OptionsList> subKindList = new ArrayList<OptionsList>();
                for (int i = 0; i < subKindArray.length; i++) {
                    OptionsList op = new OptionsList();
                    op.setOptionLabel(dicMap.get(subKindArray[i]));
                    op.setOptionValue(subKindArray[i]);
                    op.setOptionId(WaterIdUtil.getWaterId());
                    subKindList.add(op);
                }
                this.changeFormProperty(formapply0007_query, "subKindNo", "optionArray", subKindList);
            }
            this.changeFormProperty(formapply0007_query, "kindNo", "initValue", kindNo);
            this.changeFormProperty(formapply0007_query, "rateType", "initValue", mfSysKind.getRateType());
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
            this.changeFormProperty(formapply0007_query, "baseRateType", "optionArray", baseRateTypeList);
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
            this.changeFormProperty(formapply0007_query, "icType", "optionArray", icTypeList);
            //获取线下产品的列表
            mfSysKind = new MfSysKind();
            mfSysKind.setKindProperty("2");
            mfSysKind.setUseFlag("1");
            mfSysKind.setCusType(mfCusCustomer.getCusType());
            mfSysKind.setBrNo(User.getOrgNo(request));
            mfSysKind.setRoleNoArray(User.getRoleNo(request));
            List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);

            String jsonArrayStr = prdctInterfaceFeign.getFincUse(kindNo);
            com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonArrayStr);

            //共同借款人
//			JSONArray coborrower = cusInterface.getCobBorrower(mfCusCustomer.getCusNo());
            dataMap.put("fincUse", jsonArray);
//			dataMap.put("coborrower", coborrower);

            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String htmlStr = jsonFormUtil.getJsonStr(formapply0007_query, "bootstarpTag", "");
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
                List<MfSysFeeItem> mfSysFeeItemList = (List<MfSysFeeItem>) (List<MfSysFeeItem>) calcInterfaceFeign.findFeeByPage(ipage).getResult();
                String feeHtmlStr = "";
                if (mfSysFeeItemList != null && mfSysFeeItemList.size() > 0) {
                    feeHtmlStr = jtu.getJsonStr("tableapplynodefeelist", "tableTag", mfSysFeeItemList, null, true);
                }
                dataMap.put("feeHtmlStr", feeHtmlStr);
            }

            String creditPactNo = "";
            String creditPactId = "";

            dataMap.put("mfSysKindList", mfSysKindList);
            dataMap.put("creditPactNo", creditPactNo);
            dataMap.put("creditPactId", creditPactId);
            dataMap.put("feeShowFlag", feeShowFlag);
            dataMap.put("flag", "success");
            dataMap.put("htmlStr", htmlStr);
            dataMap.put("cmpdRateType", cmpdRateType);
            Map<String, Object> map = creditApplyInterfaceFeign.getByCusNoAndKindNo(cusNo, kindNo);
            dataMap.putAll(map);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }
        return dataMap;
    }

    /**
     *
     * @param repayType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repayplanInitAjax")
    @ResponseBody
    public Map<String, Object> repayplanInitAjax(String repayType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String planShowFlag ="0";
        try {
            if ("6".equals(repayType)) {
                JsonTableUtil jtu = new JsonTableUtil();
                planShowFlag ="1";
                String planHtmlStr = "";
                List<MfRepayPlan> mfRepayPlanList = new ArrayList<MfRepayPlan>();
                planHtmlStr = jtu.getJsonStr("tableapplyrepayplanlist", "tableTag", mfRepayPlanList, null, true);
                dataMap.put("planHtmlStr", planHtmlStr);
            }else{
            }
            dataMap.put("planShowFlag", planShowFlag);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }
        return dataMap;
    }
    @RequestMapping(value = "/createRiskAjax")
    @ResponseBody
    public Map<String, Object> createRiskAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formapply0007 = formService.getFormData("apply0007");
            getFormValue(formapply0007, getMapByJson(ajaxData));
            MfBusApply mfBusApply = new MfBusApply();
            setObjValue(formapply0007, mfBusApply);

            Map<String, Object> dataMapAll = new HashMap<String, Object>();
            Map<String, Object> dimeMap = new HashMap<String, Object>();
            Map<String, Object> parmMap = new HashMap<String, Object>();
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer = cusInterfaceFeign.getCusByCusNo(mfBusApply.getCusNo());
            dimeMap.put("CUS_TYPE", mfCusCustomer.getCusType());
            dimeMap.put("BUS_MODEL", mfBusApply.getBusModel());
            dimeMap.put("VOU_TYPE", mfBusApply.getVouType());
            dimeMap.put("KIND_NO", mfBusApply.getKindNo());
            parmMap.put("cusNo", mfBusApply.getCusNo());
            parmMap.put("appId", mfBusApply.getAppId());
            parmMap.put("pledgeNo", null);
            parmMap.put("pactNo", null);
            parmMap.put("fincId", null);
            dataMapAll.put("dimeMap", dimeMap);
            dataMapAll.put("parmMap", parmMap);
            dataMapAll.put("relNo", mfBusApply.getAppId());
            //riskInterfaceFeign.createRiskInterceptResult(dataMapAll);
            List<RiskBizItemRel> riskBizItemRelList = riskInterfaceFeign.findByRelNo(mfBusApply.getAppId());

            String passFlag = "pass";
            if (riskBizItemRelList == null || riskBizItemRelList.size() == 0) {

            } else {

                for (RiskBizItemRel rel : riskBizItemRelList) {
                    //sqlPassFlag为0时，表示不通过
                    if ("0".equals(rel.getSqlPassFlag())) {
                        passFlag = "passNot";
                        break;
                    }
                }
            }
            dataMap.put("flag", "success");
            dataMap.put("passFlag", passFlag);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }

        return dataMap;
    }

    /***
     * 业务新增（进件）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertForApply")
    public String insertForApply(Model model, String ajaxData, String busModel) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formapply0007;
        MfBusApply mfBusApply = new MfBusApply();
        if (busModel.equals(BizPubParm.BUS_MODEL_5)) {//应收账款融资(保理业务)
            formapply0007 = formService.getFormData("apply0007bl");
        } else {
            formapply0007 = formService.getFormData("apply0007");
        }
        //保存业务申请信息

        getFormValue(formapply0007);
        if (this.validateFormData(formapply0007)) {
            setObjValue(formapply0007, mfBusApply);
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
            mfBusApply.setPactModelNo(mfSysKind.getPactModelNo());
            mfBusApply.setPactModelName(mfSysKind.getPactModelName());
            mfBusApply.setResearchModelNo(mfSysKind.getResearchModelNo());
            mfBusApply.setResearchModelName(mfSysKind.getResearchModelName());
            mfBusApply = mfBusApplyFeign.insert(mfBusApply);
        }
        String appId = mfBusApply.getAppId();
		/*和getSummary()里的方法重复
		 //详情页面数据
		 vouType = mfBusApply.getVouType();
		 busModel=mfBusApply.getBusModel();
		 //金额格式化
		 mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt()/10000));
		 //申请信息
		 if(busModel.equals(BizPubParm.BUS_MODEL_5)){
		FormData 	 formapply0006 = formService.getFormData("apply0006bl");
		 }else{
		FormData 	 formapply0006 = formService.getFormData("apply0006");
		 }
		 getObjValue(formapply0006, mfBusApply);
		 //客户信息
		MfCusCustomer  mfCusCustomer = new MfCusCustomer();
		 mfCusCustomer.setCusNo(mfBusApply.getCusNo());
		 mfCusCustomer =cusInterfaceFeign.getById(mfCusCustomer);
		 headImg = mfCusCustomer.getHeadImg();
		 ifUploadHead = mfCusCustomer.getIfUploadHead();

		 wareHouseCusNo = "0";
		 coreCusNo = "0";
		 //押品信息
		 if(!vouType.equals(BizPubParm.VOU_TYPE_1)){
			 pleFlag="0";
		 }

		//获得前十条历史信息
		//String relNo = "appId-"+appId;
		String relNo = "cusNo-"+mfBusApply.getCusNo();
		bizInfoChangeList = bizInterfaceFeign.getTopTen(relNo);
		*/
//		getSummary();
        model.addAttribute("formapply0007", formapply0007);
        model.addAttribute("appId", appId);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_Detail";
    }

    @RequestMapping(value = "/insertForApplyAjax")
    @ResponseBody
    public Map<String, Object> insertForApplyAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formapply0007;
        try {
            Map map = getMapByJson(ajaxData);
            String busModel = (String) map.get("busModel");
            if (busModel.equals(BizPubParm.BUS_MODEL_5)) {//应收账款融资(保理业务)
                formapply0007 = formService.getFormData("apply0007bl");
            } else {
                formapply0007 = formService.getFormData("apply0007");
            }
            getFormValue(formapply0007, map);
            if (this.validateFormData(formapply0007)) {
                MfBusApply mfBusApply = new MfBusApply();
                setObjValue(formapply0007, mfBusApply);
                MfSysKind mfSysKind = new MfSysKind();
                mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
                mfBusApply.setPactModelNo(mfSysKind.getPactModelNo());
                mfBusApply.setPactModelName(mfSysKind.getPactModelName());
                mfBusApply.setResearchModelNo(mfSysKind.getResearchModelNo());
                mfBusApply.setResearchModelName(mfSysKind.getResearchModelName());
                mfBusApply = mfBusApplyFeign.insert(mfBusApply);
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
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/insertForApplyAjax_query")
    @ResponseBody
    public Map<String, Object> insertForApplyAjax_query(String ajaxData, String ajaxDataList, String parcel, String planDataList) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);

//			formId = prdctInterfaceFeign.getFormId(map.get("kindNo").toString(), WKF_NODE.apply_input, null, null);
            String formId = map.get("formId").toString();
            FormData formapply0007_query = formService.getFormData(formId);
            FormData calc = formService.getFormData("cybQuotaCalcBase");
            if ("0".equals(map.get("cmpFltRateShow"))) {// 不收复利
                // bug 9324 申请界面上如果设置是否收取复利，设置否时，默认复利利率上浮以及复利利率要取默认值-100以及0
                map.put("cmpdFloat", "-100");
                map.put("cmpdRate", "0.00");
            }

            getFormValue(formapply0007_query, map);
            getFormValue(calc, map);
            if (this.validateFormDataAnchor(formapply0007_query)) {
                MfBusApply mfBusApply = new MfBusApply();
                setObjValue(formapply0007_query, mfBusApply);
                setObjValue(calc, mfBusApply);
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfBusApply.getCusNo());
                if("0".equals(mfBusApply.getIsRelCredit())){
                    String loanKind = mfBusApply.getLoanKind();
                    if(!"5".equals(loanKind) ){
                        mfBusApply.setAgenciesId(mfBusApply.getAgenciesId1());
                        mfBusApply.setAgenciesName(mfBusApply.getAgenciesName1());
                        mfBusApply.setBreedNo(mfBusApply.getBreedNo1());
                        mfBusApply.setBreedName(mfBusApply.getBreedName1());
                    }
                }else{
                    if(StringUtil.isNotEmpty(mfBusApply.getVouType1())){
                        mfBusApply.setVouType(mfBusApply.getVouType1());
                    }
                }
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

                if (!"1".equals(map.get("updateType"))) {//更新不检查授信
                    dataMap = checkCredit(mfBusApply);
                    if ("error".equals(dataMap.get("flag").toString())) {
                        return dataMap;
                    }
                }else if("1".equals(map.get("updateType"))){
					//校验申请金额，申请期限是否大于0
                    if(MathExtend.comparison(mfBusApply.getAppAmt().toString(),"0.00")!=1){
                        dataMap.put("flag", "error");
                        dataMap.put("msg", "申请金额必须大于0！");
                        return dataMap;
                    }
				}
				//检验期限
                String busModel = mfBusApply.getBusModel();
                if(!BizPubParm.BUS_MODEL_12.equals(busModel)){//排除工程担保项目
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
                /*授信校验end*/

                String vouType = mfBusApply.getVouType();
                if (StringUtil.isNotEmpty(vouType) && "|".equals(vouType.substring(0, 1))) {
                    mfBusApply.setVouType(vouType.substring(1, vouType.length()));
                }
                //费用信息
                map.put("feeList", ajaxDataList);
                map.put("planDataList", planDataList);
                new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusApply);
                mfBusApply.setDataShowWay("1");//进件数据PC主端是否展示，内部人员申请默认展示
                map.put("mfBusApply", mfBusApply);
                mfBusApply = mfBusApplyFeign.insertApply(map);
                /*20190403 如果申请业务为借新还旧则将其保存至申请-旧借据关系表 --START*/
                if("3".equals(mfBusApply.getLoanKind())){
                    MfBusApplyFincJxhj mfBusApplyFincJxhj=new MfBusApplyFincJxhj();
                    mfBusApplyFincJxhj.setAppId(mfBusApply.getAppId());
                    mfBusApplyFincJxhj.setFincOldId(mfBusApply.getFincIdOld());
                    mfBusApplyFincJxhj.setOpNo(mfBusApply.getOpNo());
                    mfBusApplyFincJxhj.setCreateTime(DateUtil.getDateTime());
                    mfBusApplyFincJxhjFeign.insert(mfBusApplyFincJxhj);
                }
                /*20190403 如果申请业务为借新还旧则将其保存至申请-旧借据关系表 --END*/
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
//			throw e;
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
            //客户为分公司时  新增所属集团授信检查
            if ("1".equals(mfCusCustomer.getCusBaseType())) {
                MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
                mfCusCorpBaseInfo.setCusNo(mfBusApply.getCusNo());
                mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
                if (mfCusCorpBaseInfo != null && StringUtil.isNotEmpty(mfCusCorpBaseInfo.getIfGroup()) && "1".equals(mfCusCorpBaseInfo.getIfGroup())) {
                    MfCusGroup mfCusGroup = new MfCusGroup();
                    mfCusGroup.setIdNum(mfCusCorpBaseInfo.getGroupNo());
                    mfCusGroup = mfCusGroupFeign.getByIdNum(mfCusGroup);
                    if (mfCusGroup != null) {
                        dataMap = creditApplyInterfaceFeign.checkCredit(mfBusApply, "1", mfCusGroup.getGroupNo());
                        if ("error".equals(dataMap.get("flag").toString())) {
                            return dataMap;
                        }
                    }
                }
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
        String channelSourceNo = mfBusApply.getChannelSourceNo();
        if (channelSourceNo != null && !"".equals(channelSourceNo)) {
            mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(channelSourceNo);
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
            if (mfCusCustomer != null) {
                dataMap = creditApplyInterfaceFeign.checkCredit(mfBusApply, mfCusCustomer.getCusBaseType(), channelSourceNo);
                if ("error".equals(dataMap.get("flag").toString())) {
                    return dataMap;
                }
            }
        }

        // 2.判断是担保公司，进行授信检查
        String assureId = mfBusApply.getOrganType();
        if (StringUtil.isNotEmpty(assureId)) {
            MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
            mfCusAssureCompany.setAssureCompanyId(assureId);
            mfCusAssureCompany = mfCusAssureCompanyFeign.getById(mfCusAssureCompany);
            if (mfCusAssureCompany != null) {
                dataMap = creditApplyInterfaceFeign.checkCredit(mfBusApply, BizPubParm.CUS_BASE_TYPE_DANBAO, assureId);
                if ("error".equals(dataMap.get("flag").toString())) {
                    return dataMap;
                }
            }
        }

        return dataMap;
    }


    /**
     * @return
     * @throws Exception
     * @author czk
     * @Description: 在业务流中增加参与方比如仓储机构
     * date 2016-8-26
     */
    @RequestMapping(value = "/insertBusPartAjax")
    @ResponseBody
    public Map<String, Object> insertBusPartAjax(String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            FormData formcuscorpbase0004 = formService.getFormData("cuscorpbase0004");
            getFormValue(formcuscorpbase0004, getMapByJson(ajaxData));
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            setObjValue(formcuscorpbase0004, mfCusCustomer);
            mfCusCustomer = mfBusApplyFeign.doInsertBusPart(mfCusCustomer, appId);
            dataMap.put("cusNo", mfCusCustomer.getCusNo());
            dataMap.put("cusType", mfCusCustomer.getCusType());
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }


    /**
     * 方法描述： 获取当前业务节点的参数信息（url等）
     *
     * @return String
     * @author zhs
     * @date 2016-7-25 下午4:07:20
     */
    @RequestMapping(value = "/getTaskInfoAjax")
    @ResponseBody
    public Map<String, Object> getTaskInfoAjax(String wkfAppId, String appId) throws Exception {
        ActionContext.initialize(request, response);
        String reconsideration = new CodeUtils().getSingleValByKey("RECONSIDERATION");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String appOpNo = User.getRegNo(request);
        TaskImpl task = wkfInterfaceFeign.getTaskWithUser(wkfAppId, "", appOpNo);
        if (task == null) {//表示业务已经结束
            dataMap.put("wkfFlag", "0");

            //查询业务是否开启了流程，历史数据导入功能的数据没有关联流程
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(wkfAppId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            if (mfBusApply!=null){
                MfBusAppKind mfBusAppKind = new MfBusAppKind();
                mfBusAppKind.setAppId(mfBusApply.getAppId());
                mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
                if(StringUtil.isEmpty(mfBusAppKind.getBusFlowId())){
                    dataMap.put("flowFlag", "0");
                }
            }
        } else {//表示业务尚未结束
            //节点编号
            String nodeName = task.getActivityName();
            String currentUser = User.getRegNo(request);
            String assignee = taskFeign.getAssignee(task.getId());
            if (!"".equals(assignee) && assignee != null && !currentUser.equals(assignee)) {
                dataMap.put("assign", true);
            }
            dataMap.put("wkfFlag", "1");
            String url = taskFeign.openApproveUrl(task.getId());
            String title = task.getDescription();
            dataMap.put("result", task.getResult());
            dataMap.put("url", url);
            dataMap.put("title", title);
            dataMap.put("nodeName", nodeName);

//			//判断业务节点是否在pc端展示的
            boolean isShow = false;
            String titleDesc = title;
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(wkfAppId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);

            dataMap.put("mfBusApply", mfBusApply);
            MfKindFlow mfKindFlow = new MfKindFlow();
            mfKindFlow.setKindNo(mfBusApply.getKindNo());
            mfKindFlow.setCategory(BizPubParm.FLOW_CATEGORY_1);
            mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
            if(StringUtil.isEmpty(appId)){
                appId=mfBusApply.getAppId();
            }
            if (mfKindFlow != null && StringUtil.isNotEmpty(mfKindFlow.getExt1())) {
                if (mfKindFlow.getExt1().indexOf(nodeName) != -1) {//该节点在业务阶段不展示，需要在手机端操作
                    isShow = true;
                    titleDesc = "请在自助终端操作 [" + title + "] 环节";
                }
            }
            String ONLY_MNG_EDIT_APP = new CodeUtils().getSingleValByKey("ONLY_MNG_EDIT_APP");//判断当前节点是否在只允许在客户经理编辑
            if(StringUtil.isNotEmpty(ONLY_MNG_EDIT_APP)&&ONLY_MNG_EDIT_APP.contains(task.getActivityName())){
                String opNo = User.getRegNo(request);
                if(!opNo.equals(mfBusApply.getManageOpNo1())){
                    dataMap.put("ONLY_MNG_EDIT_APP","1");
                }
            }
            if (StringUtil.isNotEmpty(mfBusApply.getPactId()) || StringUtil.isNotEmpty(mfBusApply.getAppId())) {
                MfBusPact mfBusPact = new MfBusPact();
                mfBusPact.setAppId(mfBusApply.getAppId());
                mfBusPact.setPactId(mfBusApply.getPactId());
                mfBusPact = pactInterfaceFeign.getById(mfBusPact);
                if (mfBusPact!=null&&StringUtil.isNotEmpty(mfBusPact.getPutoutMethod())) {
                    if (WKF_NODE.putout_apply.getNodeNo().equals(nodeName) || WKF_NODE.review_finc.getNodeNo().equals(nodeName)) {
                        if (mfBusPact.getPutoutMethod().contains(BizPubParm.YES_NO_N)) {//如果包含客户经理放款
                            isShow = false;
                            titleDesc = title;
                        } else {
                            isShow = true;
                            titleDesc = "请在自助终端操作 [" + title + "] 环节";
                        }
                    }
                }

            }
            dataMap.put("isShow", isShow);
            dataMap.put("title", titleDesc);

            if ("primary_apply_approval".equals(nodeName)) {// 初选审批
                SysTaskInfo sti = new SysTaskInfo();
                sti.setBizPkNo(mfBusApply.getPrimaryAppId());
                sti.setUserNo(appOpNo);
                sti.setPasSts("0");
                sti.setPasMaxNo("1");// 审批任务
                sti.setPasMinNo("100");// 初选审批
                List<SysTaskInfo> stiList = sysTaskInfoFeign.getAllTask(sti);

                if (stiList.size() > 0) {
                    dataMap.put("sysTaskInfo", stiList.get(0));

                    // 审批任务
                    TaskImpl approvalTask = wkfInterfaceFeign.getTask("", stiList.get(0).getWkfTaskNo());
                    dataMap.put("approvalTask", approvalTask);
                }
            }

            if ("apply_approval".equals(nodeName)) {// 融资审批
                String bizPkNo = mfBusApply.getAppId();
                SysTaskInfo sti = new SysTaskInfo();
                if (BizPubParm.YES_NO_Y.equals(reconsideration) && StringUtil.isNotEmpty(mfBusApply.getReconsiderId())) {
                    Task resTask = wkfInterfaceFeign.getTask(mfBusApply.getReconsiderId(), null);
                    if (resTask != null) {
                        bizPkNo = mfBusApply.getReconsiderId();
                    }
                }
                sti.setBizPkNo(bizPkNo);
                sti.setUserNo(appOpNo);
                sti.setPasSts("0");
                sti.setPasMaxNo("1");// 审批任务
                sti.setPasMinNo("110");// 融资审批
                List<SysTaskInfo> stiList = sysTaskInfoFeign.getAllTask(sti);

                if (stiList.size() > 0) {
                    dataMap.put("sysTaskInfo", stiList.get(0));

                    // 审批任务
                    TaskImpl approvalTask = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), stiList.get(0).getWkfTaskNo());
                    dataMap.put("approvalTask", approvalTask);
                }
            }

            if ("contract_approval".equals(nodeName)) {// 合同审批
                SysTaskInfo sti = new SysTaskInfo();
                sti.setBizPkNo(mfBusApply.getPactId());
                sti.setUserNo(appOpNo);
                sti.setPasSts("0");
                sti.setPasMaxNo("1");// 审批任务
                sti.setPasMinNo("109");// 合同审批
                List<SysTaskInfo> stiList = sysTaskInfoFeign.getAllTask(sti);

                if (stiList.size() > 0) {
                    dataMap.put("sysTaskInfo", stiList.get(0));

                    // 审批任务
                    TaskImpl approvalTask = wkfInterfaceFeign.getTask("", stiList.get(0).getWkfTaskNo());
                    dataMap.put("approvalTask", approvalTask);
                }
            }

            if ("putout_approval".equals(nodeName)) {// 放款审批
                MfBusFincApp bfa = new MfBusFincApp();
                bfa.setAppId(appId);
                bfa = mfBusFincAppFeign.getByIdNewFinc(bfa);

                MfBusFincAppChild bfac = mfBusFincAppFeign.getMfBusFincAppChild(bfa);

                SysTaskInfo sti = new SysTaskInfo();
                sti.setBizPkNo(bfac.getFincChildId());
                sti.setUserNo(appOpNo);
                sti.setPasSts("0");
                sti.setPasMaxNo("1");// 审批任务
                sti.setPasMinNo("113");// 放款审批
                List<SysTaskInfo> stiList = sysTaskInfoFeign.getAllTask(sti);

                if (stiList.size() > 0) {
                    dataMap.put("sysTaskInfo", stiList.get(0));

                    // 审批任务
                    TaskImpl approvalTask = wkfInterfaceFeign.getTask("", stiList.get(0).getWkfTaskNo());
                    dataMap.put("approvalTask", approvalTask);
                }
            }

        }
        return dataMap;
    }

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String appId, String busEntrance) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formapply0001 = formService.getFormData("apply0001");
        getFormValue(formapply0001);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        getObjValue(formapply0001, mfBusApply);
        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_Detail";
    }

    @RequestMapping(value = "/getByNewId")
    @ResponseBody
    public String getByNewId(Model model, String appId) throws Exception {
        ActionContext.initialize(request, response);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        return mfBusApply.getCusNo();
    }

    /**
     * 方法描述： 获取申请摘要信息
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-3-28 下午4:08:04
     */
    @RequestMapping(value = "/getBusInfoAjax")
    @ResponseBody
    public Map<String, Object> getBusInfoAjax(String appId, String fincId, String busEntrance) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("showFlag", "none");//showFlag填写过申请信息的标志none-没有业务信息  apply-展示申请信息 pact-展示合同信息，默认不展示任何业务信息
        try {
            String rateUnit = "";
            if (StringUtil.isNotEmpty(appId) && !"null".equals(appId)) {
                MfBusApply mfBusApply = new MfBusApply();
                mfBusApply.setAppId(appId);
                mfBusApply = mfBusApplyFeign.getById(mfBusApply);
                if (mfBusApply != null) {
                    dataMap.put("showFlag", "apply");
                    mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt() / 10000));
                    mfBusApply = mfBusApplyFeign.processDataForApply(mfBusApply);// 处理利率格式
                    // 获取该客户除当前业务之外的其他的业务列表
                    MfBusApply mfBusApplyTmp = new MfBusApply();
                    mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
                    mfBusApplyTmp.setAppId(mfBusApply.getAppId());
                    List<MfBusApply> mfBusApplyList = mfBusApplyFeign.getOtherApplyList(mfBusApplyTmp);
                    dataMap.put("mfBusApplyList", mfBusApplyList);
                    dataMap.put("mfbusInfo", mfBusApply);
                    MfBusPact mfBusPact = new MfBusPact();
                    mfBusPact = pactInterfaceFeign.getByAppId(mfBusApply.getAppId());
                    // 如果合同实体不为null,表示数据到合同阶段
                    if (mfBusPact != null && !"apply".equals(busEntrance)) {
                        dataMap.put("showFlag", "pact");
                        mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);// 处理利率格式
                        mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt() / 10000));
                        dataMap.put("mfbusInfo", mfBusPact);

                        if (StringUtil.isNotEmpty(fincId)) {
                            // dataMap.put("mfbusInfo", mfBusFincApp);
                            MfBusFincApp mfBusFincApp = new MfBusFincApp();
                            mfBusFincApp.setFincId(fincId);
                            mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
                            if (mfBusFincApp != null) {
                                mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusFincApp.getPutoutAmt() / 10000));
                                dataMap.put("mfbusInfo", mfBusPact);
                            }
                        }
                    }
                    // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
                    Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
                    rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();

                    // 处理多业务
                    mfBusApplyTmp = new MfBusApply();
                    mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
                    mfBusApplyList = appInterfaceFeign.getMultiBusList(mfBusApplyTmp);
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
                }

            }
            dataMap.put("rateUnit", rateUnit);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
            throw e;
        }
        return dataMap;
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
        FormData formapply0002 = formService.getFormData("apply0002");
        getFormValue(formapply0002);
        boolean validateFlag = this.validateFormData(formapply0002);
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
        FormData formapply0002 = formService.getFormData("apply0002");
        getFormValue(formapply0002);
        boolean validateFlag = this.validateFormData(formapply0002);
    }


    /**
     * 方法描述：获取业务详情页面下的数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSummary")
    public String getSummary(Model model, String ajaxData, String appId, String busEntrance, String ifAnalysisTable,String queryFileApproval,String queryForm) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formapply0006;
        String query = "";
        String reconsiderId = "";
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        //获取详情信息
        dataMap = mfBusApplyFeign.getBusDetailInfo(mfBusApply);

        //业务申请信息处理
        mfBusApply = (MfBusApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusApply"), MfBusApply.class);
        if (!(mfBusApply.getAppSts().equals(BizPubParm.APP_STS_UN_SUBMIT))) {
            //一旦提交审批，则设置为query，解析出的申请表单不可单字段编辑
            query = "query";
            /**
             * 上次审批意见类型。
             * 如果上次审批的审批意见类型为发回补充资料，设置query为"",表单可编辑,要件可上传
             */
            query = getQueryBylastApproveType(appId);
        }
        //判断客户表单信息是否允许编辑
        String cusFormQuery = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(), BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
        //要件的权限
        String queryFile = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(), BizPubParm.FORM_EDIT_FLAG_FILE, User.getRegNo(request));
        if(StringUtil.isNotEmpty(queryFileApproval)){
            if("edit".equals(queryFileApproval)){
                queryFileApproval = "";
            }
            queryFile = queryFileApproval;
        }
        //factor传的是"",到web端就变成了null
        if (cusFormQuery == null) {
            cusFormQuery = "";
        }
        if (queryFile == null) {
            queryFile = "";
        }
        model.addAttribute("queryFile", queryFile);
        dataMap.put("guaranteeType", "loan");
        dataMap.put("busSubmitCnt", "0");
        if (!BizPubParm.APP_STS_UN_SUBMIT.equals(mfBusApply.getAppSts()) && !BizPubParm.APP_STS_PROCESS.equals(mfBusApply.getAppSts())) {
            dataMap.put("busSubmitCnt", "1");
        }
        //申请客户信息
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer = (MfCusCustomer) JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCustomer"), MfCusCustomer.class);
        //反欺诈-服务类型
        dataMap.put("itemType", BizPubParm.ANTIFRAUD_ITEM_TYPE);
        //反欺诈-服务编号
        dataMap.put("itemNo", BizPubParm.ANTIFRAUD_ITEM_NO);
        JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
        String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
        ajaxData = items;

        //是否展开审批历史
        String OPEN_APPROVE_HIS = new CodeUtils().getSingleValByKey("OPEN_APPROVE_HIS");
        model.addAttribute("OPEN_APPROVE_HIS", OPEN_APPROVE_HIS);

        //要件的展示方式：0块状1列表
        List<Object> parmDics = busiCacheInterface.getParmDicList("DOC_SHOW_TYPE");
        for (Object o : parmDics) {
            ParmDic p = (ParmDic) o;
            String docShowType = p.getOptCode();
            model.addAttribute("docShowType", docShowType);
        }
        if ("4".equals(busEntrance) || "apply_approve".equals(busEntrance)) {
            // 如果存在复议否决审批task, 说明当前处于否决复议审批, 需要展示否决复议审批历史
            Task taskAppro = wkfInterfaceFeign.getTask("reconsider" + mfBusApply.getAppId(), null);// 否决复议审批task
            if (taskAppro != null) {
                reconsiderId = "reconsider" + mfBusApply.getAppId();
            }
        }
        String OPEN_APPROVE_HIS_RECONSIDE = new CodeUtils().getSingleValByKey("OPEN_APPROVE_HIS_RECONSIDE");
        model.addAttribute("OPEN_APPROVE_HIS_RECONSIDE", OPEN_APPROVE_HIS_RECONSIDE);
        if ("1".equals(OPEN_APPROVE_HIS_RECONSIDE)) {
            // 如果存在复议否决审批task, 说明当前处于否决复议审批, 需要展示否决复议审批历史
            Task taskAppro = wkfInterfaceFeign.getTask("reconsider" + mfBusApply.getAppId(), null);// 否决复议审批task
            if (taskAppro != null) {
                reconsiderId = "reconsider" + mfBusApply.getAppId();
            }
        }
        //动态视图参数
        Map<String, String> parmMap = new HashMap<String, String>();
        parmMap.put("appId", appId);
        parmMap.put("wkfAppId", mfBusApply.getWkfAppId());
        parmMap.put("primaryAppId", mfBusApply.getPrimaryAppId());
        parmMap.put("cusNo", mfBusApply.getCusNo());
        parmMap.put("busModel", mfBusApply.getBusModel());
        parmMap.put("busStage", mfBusApply.getBusStage());
        parmMap.put("appSts", mfBusApply.getAppSts());
        parmMap.put("applyProcessId", mfBusApply.getApplyProcessId());
        parmMap.put("primaryApplyProcessId", mfBusApply.getPrimaryApplyProcessId());
        parmMap.put("vouType", mfBusApply.getVouType());
        parmMap.put("cusBaseType", mfCusCustomer.getCusBaseType());
        //获取授信id
        String creditAppId = "";
        if (StringUtil.isNotEmpty(mfBusApply.getCreditPactNo())) {
            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setPactNo(mfBusApply.getCreditPactNo());
            mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
            if (mfCusCreditContract != null) {
                creditAppId = mfCusCreditContract.getCreditAppId();
            }
            parmMap.put("creditAppId", creditAppId);
        }
        if (StringUtil.isEmpty(busEntrance)) {
            busEntrance = "apply_approve";//如果入口为空，为审批视图
        }
        String generalClass = "bus";
        if ("1".equals(busEntrance)) {
            busEntrance = "apply";
        } else if ("4".equals(busEntrance)) {
            busEntrance = "apply_approve";
        }else {
        }
        parmMap.put("busEntrance", busEntrance);
        String busClass = mfBusApply.getBusModel();
        Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMap(generalClass, busClass, busEntrance, parmMap);
        dataMap.putAll(busViewMap);

        model.addAttribute("dataMap", dataMap);
        model.addAttribute("mfBusApply", mfBusApply);
        model.addAttribute("busEntrance", busEntrance);
        model.addAttribute("ifAnalysisTable", ifAnalysisTable);
        model.addAttribute("mfCusCustomer", mfCusCustomer);
        model.addAttribute("query", "");
        model.addAttribute("busModel", mfBusApply.getBusModel());
        model.addAttribute("queryForm", queryForm);
        model.addAttribute("cusFormQuery", cusFormQuery);
        model.addAttribute("reconsiderId", reconsiderId);
        model.addAttribute("creditAppId", creditAppId);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        return "/component/app/MfBusApply_DynaDetail";
    }

    /**
     * 方法描述：重新计算逾期利率或者复利利率（用于单子段编辑，申请利率/利率上浮修改时重新计算逾期/复利利率）
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-9-19 上午11:50:36
     */
    @RequestMapping(value = "/getOverOrCmpdRateAjax")
    @ResponseBody
    public Map<String, Object> getOverOrCmpdRateAjax(String appId, Double fincRate, Double overFloat, Double cmpdFloat) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply.setFincRate(fincRate);
            mfBusApply.setOverFloat(overFloat);
            mfBusApply.setCmpdFloat(cmpdFloat);
            MfBusAppKind mfBusAppKind = new MfBusAppKind();
            mfBusAppKind.setAppId(appId);
            mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("mfBusApply", mfBusApply);
            paramMap.put("mfBusAppKind", mfBusAppKind);
            MfBusApply mfBusApplyTmp = mfBusApplyFeign.doDealRateFloat(paramMap);
            dataMap.put("overRate", MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusApplyTmp.getOverRate(), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
            dataMap.put("cmpdRate", MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusApplyTmp.getCmpdRate(), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
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
     * 方法描述：获取业务详情页面下的数据（法律）
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/getSummaryLawSuit")
    public String getSummaryLawSuit(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formapply0006;
        String query = "";
        String fundCusNo = "";
        String coreCusNo = "";
        String wareHouseCusNo = "";
        //业务申请信息
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setPactId(appId);
        mfBusApply = mfBusApplyFeign.getByPactId(mfBusApply);
        if (!(mfBusApply.getAppSts().equals(BizPubParm.APP_STS_UN_SUBMIT))) {
            //一旦提交审批，则设置为query，解析出的申请表单不可单字段编辑
            query = "query";
        }
        String busModel = mfBusApply.getBusModel();
        if (busModel.equals(BizPubParm.BUS_MODEL_5)) {//应收账款融资(保理业务)
            formapply0006 = formService.getFormData("apply0006bl");
        } else {
            formapply0006 = formService.getFormData("apply0006");
        }
        getObjValue(formapply0006, mfBusApply);

        // 处理期限的展示单位。
        String termUnit = "";
        if ("1".equals(mfBusApply.getTermType())) {
            termUnit = "个月";
        } else if ("2".equals(mfBusApply.getTermType())) {
            termUnit = "日";
        }else {
        }
        this.changeFormProperty(formapply0006, "term", "unit", termUnit);

        mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt() / 10000));
        //风险级别信息
        List<RiskBizItemRel> riskBizItemRelList = riskInterfaceFeign.findByRelNo(appId);
        String riskLevel = "-1";
        for (int i = 0; i < riskBizItemRelList.size(); i++) {
            String riskLevelThis = riskBizItemRelList.get(i).getRiskLevel();
            //风险级别越高，riskLevel越大；如果是风险级别是业务拒绝级，riskLevel为99
            if ("99".equals(riskLevelThis)) {
                riskLevel = riskLevelThis;
                break;
            } else {
                if (Integer.valueOf(riskLevelThis) > Integer.valueOf(riskLevel)) {
                    riskLevel = riskLevelThis;
                }
            }
        }
        String riskName = "风险检查通过";
        if (!"-1".equals(riskLevel)) {
            ParmDic parmDic = new ParmDic();
            parmDic.setKeyName("RISK_PREVENT_LEVEL");
            parmDic.setOptCode(riskLevel);
            parmDic = nmdInterfaceFeign.getParmDicById(parmDic);
            riskName = parmDic.getOptName();
        }
        //申请客户信息
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        String headImg = mfCusCustomer.getHeadImg();
        String ifUploadHead = mfCusCustomer.getIfUploadHead();
        //要件场景
        String scNo = BizPubParm.SCENCE_TYPE_DOC_APP;
        //关联企业：核心企业、资金机构、仓储机构等信息
        if (busModel.equals(BizPubParm.BUS_MODEL_1) || busModel.equals(BizPubParm.BUS_MODEL_2)) {//动产质押、仓单模式下登记 仓储机构
            if (StringUtil.isNotEmpty(mfBusApply.getCusNoWarehouse())) {//仓储机构信息
                wareHouseCusNo = mfBusApply.getCusNoWarehouse();// 已登记
            } else {
                wareHouseCusNo = "0";//未登记
            }
        } else if (busModel.equals(BizPubParm.BUS_MODEL_4)) {//保兑仓模式下登记核心企业
            if (StringUtil.isNotEmpty(mfBusApply.getCusNoCore())) {//核心企业信息
                coreCusNo = mfBusApply.getCusNoCore();// 已登记
            } else {
                coreCusNo = "0";//未登记
            }
        } else if (busModel.equals(BizPubParm.BUS_MODEL_5)) {//保理（应收账款）模式下登记核心企业、资金机构
            if (StringUtil.isNotEmpty(mfBusApply.getCusNoCore())) {//核心企业信息
                coreCusNo = mfBusApply.getCusNoCore();// 已登记
            } else {
                coreCusNo = "0";//未登记
            }
            if (StringUtil.isNotEmpty(mfBusApply.getCusNoFund())) {//资金机构信息
                fundCusNo = mfBusApply.getCusNoFund();// 已登记
            } else {
                fundCusNo = "0";//未登记
            }
        }else {
        }

        //查询业务流程历史
        List<WkfApprovalOpinion> hisTaskList = wkfInterfaceFeign.getWkfTaskHisList(mfBusApply.getWkfAppId());
        JSONArray array = JSONArray.fromObject(hisTaskList);//hisTaskList.get(0).getDbId()
        JSONObject json = new JSONObject();
        json.put("hisTaskList", array);
        String vouType = mfBusApply.getVouType();
        //处理多业务“下一笔”跳转
        MfBusApply mfBusApplyTmp = new MfBusApply();
        mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
        List<MfBusApply> mfBusApplyList = mfBusApplyFeign.getMultiBusList(mfBusApplyTmp);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("moreCount", mfBusApplyList.size());
        dataMap.put("nextAppId", "");
        dataMap.put("nextPactId", "");
        if (mfBusApplyList.size() > 1) {
            for (int i = 0; i < mfBusApplyList.size(); i++) {
                mfBusApplyTmp = mfBusApplyList.get(i);
                if (mfBusApplyTmp.getAppId().equals(mfBusApply.getAppId())) {
                    if (i == mfBusApplyList.size() - 1) {
                        dataMap.put("nextAppId", mfBusApplyList.get(0).getAppId());
                        dataMap.put("nextPactId", mfBusApplyList.get(0).getPactId());
                    } else {
                        dataMap.put("nextAppId", mfBusApplyList.get(i + 1).getAppId());
                        dataMap.put("nextPactId", mfBusApplyList.get(i + 1).getPactId());
                    }
                }
            }
        }

        JSONArray jsonArray = JSONArray.fromObject(mfCusCustomer);
        dataMap.put("customerJson", jsonArray.toString());
        model.addAttribute("formapply0006", formapply0006);
        model.addAttribute("query", query);
        model.addAttribute("vouType", vouType);
        model.addAttribute("riskName", riskName);
        model.addAttribute("headImg", headImg);
        model.addAttribute("ifUploadHead", ifUploadHead);
        model.addAttribute("coreCusNo", coreCusNo);
        model.addAttribute("fundCusNo", fundCusNo);

        model.addAttribute("wareHouseCusNo", wareHouseCusNo);
        model.addAttribute("scNo", scNo);
        return "/component/app/MfBusApplyApprov_Detail";
    }

    /**
     * 方法描述：获取业务审批中业务申请详情
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/getSummaryApprov")
    public String getSummaryApprov(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfBusPact mfBusPact = new MfBusPact();
        FormData formapply0006;
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        String busModel = mfBusApply.getBusModel();
        if (busModel.equals(BizPubParm.BUS_MODEL_5)) {//应收账款融资(保理业务)
            formapply0006 = formService.getFormData("apply0006bl");
        } else {
            formapply0006 = formService.getFormData("apply0006");
        }

        getObjValue(formapply0006, mfBusApply);
        mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt() / 10000));

        //获得前五条历史信息
        //String relNo = "appId-"+appId;
//		String relNo = "cusNo-"+mfBusApply.getCusNo();
//		bizInfoChangeList = bizInterfaceFeign.getTopFive(relNo);


        List<RiskBizItemRel> riskBizItemRelList = riskInterfaceFeign.findByRelNo(appId);
        String riskLevel = "-1";
        for (int i = 0; i < riskBizItemRelList.size(); i++) {
            if ("0".equals(riskBizItemRelList.get(i).getSqlPassFlag())) {
                String riskLevelThis = riskBizItemRelList.get(i).getRiskLevel();
                //风险级别越高，riskLevel越大；如果是风险级别是业务拒绝级，riskLevel为99
                if ("99".equals(riskLevelThis)) {
                    riskLevel = riskLevelThis;
                    break;
                } else {
                    if (Integer.valueOf(riskLevelThis) > Integer.valueOf(riskLevel)) {
                        riskLevel = riskLevelThis;
                    }
                }
            }
        }
        String riskName = "风险检查通过";
        if (!"-1".equals(riskLevel)) {
            ParmDic parmDic = new ParmDic();
            parmDic.setKeyName("RISK_PREVENT_LEVEL");
            parmDic.setOptCode(riskLevel);
            parmDic = nmdInterfaceFeign.getParmDicById(parmDic);
            riskName = parmDic.getOptName();
        }
        //申请客户信息
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        String headImg = mfCusCustomer.getHeadImg();
        String ifUploadHead = mfCusCustomer.getIfUploadHead();
        String scNo = BizPubParm.SCENCE_TYPE_DOC_APP;
        String wareHouseCusNo;
        if (StringUtil.isNotEmpty(mfBusApply.getCusNoWarehouse())) {//仓储机构信息
            wareHouseCusNo = mfBusApply.getCusNoWarehouse();
        } else {
            wareHouseCusNo = "0";
        }
        String coreCusNo;
        if (StringUtil.isNotEmpty(mfBusApply.getCusNoCore())) {//核心企业信息
            coreCusNo = mfBusApply.getCusNoCore();
        } else {
            coreCusNo = "0";
        }

        //审批通过则获取合同信息
        if (mfBusApply.getAppSts().equals(BizPubParm.APP_STS_PASS)) {
            mfBusPact = pactInterfaceFeign.getByAppId(mfBusApply.getAppId());
        }

        //查询业务流程历史
        List<WkfApprovalOpinion> hisTaskList = wkfInterfaceFeign.getWkfTaskHisList(mfBusApply.getWkfAppId());
        JSONArray array = JSONArray.fromObject(hisTaskList);//hisTaskList.get(0).getDbId()
        JSONObject json = new JSONObject();
        json.put("hisTaskList", array);

        String vouType = mfBusApply.getVouType();

        model.addAttribute("formapply0006", formapply0006);
        model.addAttribute("vouType", vouType);
        model.addAttribute("query", "");
        model.addAttribute("riskName", riskName);
        model.addAttribute("headImg", headImg);
        model.addAttribute("ifUploadHead", ifUploadHead);
        model.addAttribute("coreCusNo", coreCusNo);
        model.addAttribute("wareHouseCusNo", wareHouseCusNo);
        model.addAttribute("scNo", scNo);
        model.addAttribute("mfBusPact", mfBusPact);
        return "/component/app/MfBusApplyApprov_Detail";
    }


    /**
     * 方法描述：申请审批流程提交
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2016-6-7 下午5:58:30
     */
    @RequestMapping(value = "/processSubmitAjax")
    @ResponseBody
    public Map<String, Object> processSubmitAjax(String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //提交申请审批流程
            MfBusApply mfBusApply = mfBusApplyWkfFeign.submitProcessWithUser(appId, User.getRegNo(request), User.getRegName(request), User.getOrgNo(request), "");
            dataMap.put("appSts", mfBusApply.getAppSts());
            dataMap.put("node", "processaudit");
            dataMap.put("flag", "success");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("userRole", mfBusApply.getApproveNodeName());
            paramMap.put("opNo", mfBusApply.getApprovePartName());
            dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
        } catch (Exception e) {
//			logger.error("申请审批流程提交出错", e);
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 业务初选审批
     *
     * @param ajaxData
     * @param appId
     * @return
     * @throws Exception Map<String,Object>
     * @author zhs
     * @date 2018年5月11日 下午12:00:01
     */
    @RequestMapping(value = "/primaryProcessSubmitAjax")
    @ResponseBody
    public Map<String, Object> primaryProcessSubmitAjax(String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //提交申请审批流程
            MfBusApply mfBusApply = mfBusApplyWkfFeign.submitProcess(appId, "");
            dataMap.put("appSts", mfBusApply.getAppSts());
            dataMap.put("node", "processaudit");
            dataMap.put("flag", "success");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("userRole", mfBusApply.getApproveNodeName());
            paramMap.put("opNo", mfBusApply.getApprovePartName());
            dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
        } catch (Exception e) {
//			logger.error("申请审批流程提交出错", e);
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 多业务情况进行业务切换时获取相关申请以及押品信息
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-2-4 上午11:10:55
     */
    @RequestMapping(value = "/getSwitchBusInfoAjax")
    @ResponseBody
    public Map<String, Object> getSwitchBusInfoAjax(String appId) throws Exception {
        ActionContext.initialize(request, response);
        MfBusApply mfBusApplyTmp = new MfBusApply();
        MfBusApply mfBusApply = new MfBusApply();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("showFlag", "none");//showFlag填写过申请信息的标志none-没有业务信息  apply-展示申请信息 pact-展示合同信息，默认不展示任何业务信息
        try {
            List<MfBusApply> mfBusApplyList = new ArrayList<MfBusApply>();
            if (StringUtil.isNotEmpty(appId)) {
                mfBusApply.setAppId(appId);
                mfBusApply = mfBusApplyFeign.getById(mfBusApply);
                if (mfBusApply != null) {
                    dataMap.put("showFlag", "apply");
                    mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt() / 10000));
                    //获取该客户除当前业务之外的其他的业务列表

                    mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
                    mfBusApplyTmp.setAppId(mfBusApply.getAppId());
                    mfBusApplyList = mfBusApplyFeign.getOtherApplyList(mfBusApplyTmp);
                    dataMap.put("mfBusApplyList", mfBusApplyList);
                    dataMap.put("mfbusInfo", mfBusApply);
                    if (mfBusApply.getAppSts().equals(BizPubParm.APP_STS_PASS)) {
                        dataMap.put("showFlag", "pact");
                        MfBusPact mfBusPact = new MfBusPact();
                        mfBusPact = pactInterfaceFeign.getByAppId(mfBusApply.getAppId());
                        mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt() / 10000));
                        dataMap.put("mfbusInfo", mfBusPact);
                    }
                }
            }
            //押品信息
            MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
            mfBusCollateralRel.setAppId(appId);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap = collateralInterfaceFeign.getBusCollateralRelByAppId(mfBusCollateralRel);


            dataMap.put("mfBusApply", mfBusApply);
            dataMap.put("mfBusApplyList", mfBusApplyList);
            dataMap.put("mfBusPledge", resultMap.get("collateralRel"));
            dataMap.put("busModel", resultMap.get("busModel"));
            dataMap.put("flag", "success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage(""));
            throw e;
        }
        return dataMap;
    }



    /**
     * 分单
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOnlineList")
    public String getOnlineList(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        JSONArray cusTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CUS_TYPE");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        JSONArray gdCusTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("GD_CUS_TYPE");
        this.getHttpRequest().setAttribute("gdCusTypeJsonArray", gdCusTypeJsonArray);
        JSONArray appStsJsonArray = new CodeUtils().getJSONArrayByKeyName("APP_STS");
        this.getHttpRequest().setAttribute("appStsJsonArray", appStsJsonArray);

//		model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_OnlineList";
    }

    @RequestMapping(value = "/findOnlineByPageAjax")
    @ResponseBody
    public Map<String, Object> findOnlineByPageAjax(String ajaxData, int pageSize, int pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            mfCusAndApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);//我的筛选
//			this.getRoleConditions(mfCusAndApply,"1000000065");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            ipage = mfBusApplyFeign.getCusAndApplyOnline(ipage, mfCusAndApply);
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

    @RequestMapping(value = "/assignBill")
    public String assignBill(Model model, String ajaxData, String cusNo, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formapply0009 = formService.getFormData("apply0009");
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setCusNo(cusNo);
        mfBusApply.setAppId(appId);
        getObjValue(formapply0009, mfBusApply);
        model.addAttribute("formapply0009", formapply0009);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_assignBill";
    }

    @RequestMapping(value = "/assginBillAjax")
    @ResponseBody
    public Map<String, Object> assginBillAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            FormData formapply0009 = formService.getFormData("apply0009");
            getFormValue(formapply0009, getMapByJson(ajaxData));
            if (this.validateFormData(formapply0009)) {
                setObjValue(formapply0009, mfBusApply);
                mfBusApplyFeign.assginBill(mfBusApply);
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
     * 方法描述： 获取客户的多笔业务列表
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-2-27 上午10:15:39
     */
    @RequestMapping(value = "/getMultiBusList")
    public String getMultiBusList(Model model, String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setCusNo(cusNo);
        mfBusApply.setQueryType("1");
        List<MfBusApply> mfBusApplyList = mfBusApplyFeign.getMultiBusList(mfBusApply);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr( "tableapply0001", "tableTag", mfBusApplyList, null, true);
        model.addAttribute("mfBusApplyList", mfBusApplyList);
        model.addAttribute("mfBusApplyListSize", mfBusApplyList.size());
        model.addAttribute("tableHtml", tableHtml);

        mfBusApply.setQueryType("2");
        List<MfBusApply> mfBusApplyProjectList = mfBusApplyFeign.getMultiBusList(mfBusApply);
        if(mfBusApplyProjectList != null && mfBusApplyProjectList.size() >0){
            // 保函金额总和
            Double appProjectAmt = 0d;
            for (MfBusApply mbp : mfBusApplyProjectList) {
                appProjectAmt = DataUtil.add(appProjectAmt, mbp.getAppAmt(), 20);
            }
            appProjectAmt = DataUtil.retainDigit(appProjectAmt, 2);
            MfBusApply mfBusApply1 = new MfBusApply();
            mfBusApply1.setAppAmt(appProjectAmt);
            mfBusApply1.setTermShow("总金额");
            mfBusApplyProjectList.add(mfBusApply1);
        }
        String projectTableHtml = jtu.getJsonStr( "tableapply_GCDB", "tableTag", mfBusApplyProjectList, null, true);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        model.addAttribute("mfBusApplyProjectList", mfBusApplyProjectList);
        model.addAttribute("mfBusApplyProjectListSize", mfBusApplyProjectList.size());
        model.addAttribute("cusSubType", mfCusCustomer.getCusSubType());
        model.addAttribute("busEntrance", "apply");
        model.addAttribute("projectTableHtml", projectTableHtml);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_multiBusList";
    }

    /**
     * 增值服务，联网核查
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toAddServicePage")
    public Map<String, Object> toAddServicePage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String url = "";
        try {
            //个人客户
            if ("202".equals(ajaxData)) {
                url = "/servicemanage/personal/personServiceList.html";
            } else {//企业客户
                url = "/servicemanage/enterprise/enterpriseServiceList.html";
            }
            dataMap.put("url", url);
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
     * 方法描述： 获取业务头部详情信息
     *
     * @return
     * @throws Exception String
     * @author Javelin
     * @date 2017-7-27 上午8:54:01
     */
    @RequestMapping(value = "/getBusDetailInfoAjax")
    @ResponseBody
    public Map<String, Object> getBusDetailInfoAjax(String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        //获取详情信息
        dataMap = mfBusApplyFeign.getBusDetailInfo(mfBusApply);
        mfBusApply = (MfBusApply) JsonStrHandling.handlingStrToBean(dataMap.get("mfBusApply"), MfBusApply.class);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        dataMap.put("rateUnit", rateUnit);
        dataMap.put("mfBusApply", mfBusApply);
        return dataMap;
    }

    /**
     * 方法描述： 获取业务详情表单HTML
     *
     * @return
     * @throws Exception String
     * @author Javelin
     * @date 2017-7-27 下午3:32:23
     */
    @RequestMapping(value = "/getApplyDetailFormAjax")
    @ResponseBody
    public Map<String, Object> getApplyDetailFormAjax(String formId, String appId,String queryForm) throws Exception {
        FormService formService = new FormService();
        Gson gson = new Gson();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            //获取详情信息
            dataMap = mfBusApplyFeign.getBusDetailInfo(mfBusApply);
            //业务申请信息处理
            mfBusApply = (MfBusApply) gson.fromJson(gson.toJson(dataMap.get("mfBusApply")), new TypeToken<MfBusApply>() {
            }.getType());
            if(StringUtil.isEmpty(mfBusApply.getCoborrName())){
                mfBusApply.setCoborrName("未登记");
            }
            request.setAttribute("ifBizManger", "3");
            String query = "";
            //判断表单信息是否允许编辑
            query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(), BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
           if(StringUtil.isNotEmpty(queryForm)){
               if("edit".equals(queryForm)){
                   queryForm = "";
               }
               query = queryForm;
           }
            //factor传的是"",到web端就变成了null
            if (query == null) {
                query = "";
            }
            //formId改为读取mf_kind_form表中配置的表单编号（这样就能满足不同产品详情表单不同的场景）
            MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm(mfBusApply.getKindNo(), WKF_NODE.apply_input.getNodeNo());
            if (mfKindForm != null) {
                if (StringUtil.isNotEmpty(mfKindForm.getShowModel())) {
                    formId = mfKindForm.getShowModel();
                }
            }
            if (StringUtil.isEmpty(formId)) {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("业务详情表单编号formId"));
            } else {
                FormData formapply0006 = formService.getFormData(formId);
                getFormValue(formapply0006);
                //获得授信信息
                Map<String, Object> creditData = creditApplyInterfaceFeign.getCreditDataByAppId(appId);
                if (!creditData.isEmpty()) {
                    getObjValue(formapply0006, creditData);
                }
                //处理逾期违约金利率值和单位展示,
                MfBusAppPenaltyMain mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
                mfBusAppPenaltyMain.setAppId(mfBusApply.getAppId());
                mfBusAppPenaltyMain.setKindNo(mfBusApply.getKindNo());
                mfBusAppPenaltyMain.setPenaltyType("1");
                mfBusAppPenaltyMain = mfBusAppPenaltyMainFeign.getById(mfBusAppPenaltyMain);
                if (mfBusAppPenaltyMain != null) {
                    String penaltyReceiveType = mfBusAppPenaltyMain.getPenaltyReceiveType();
                    mfBusApply.setPenaltyCalRateFinc(mfBusAppPenaltyMain.getPenaltyReceiveValue());
                    if ("1".equals(penaltyReceiveType)) {
                        this.changeFormProperty(formapply0006, "penaltyCalRateFinc", "unit", "%");
                    } else {
                        this.changeFormProperty(formapply0006, "penaltyCalRateFinc", "unit", "元");
                    }
                }
                //提前结清约金利率值和单位展示
                mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
                mfBusAppPenaltyMain.setAppId(mfBusApply.getAppId());
                mfBusAppPenaltyMain.setKindNo(mfBusApply.getKindNo());
                mfBusAppPenaltyMain.setPenaltyType("2");
                mfBusAppPenaltyMain = mfBusAppPenaltyMainFeign.getById(mfBusAppPenaltyMain);
                if (mfBusAppPenaltyMain != null) {
                    String penaltyReceiveType = mfBusAppPenaltyMain.getPenaltyReceiveType();
                    mfBusApply.setEarlyRepayRate(mfBusAppPenaltyMain.getPenaltyReceiveValue());
                    this.changeFormProperty(formapply0006, "earlyRepayRate", "initValue", mfBusAppPenaltyMain.getPenaltyReceiveValue());
                    if ("1".equals(penaltyReceiveType)) {
                        this.changeFormProperty(formapply0006, "earlyRepayRate", "unit", "%");
                    } else {
                        this.changeFormProperty(formapply0006, "earlyRepayRate", "unit", "元");
                    }
                }
                Double creditSum = 0d;
                Double creditAmt = 0d;

                if(StringUtil.isNotEmpty(mfBusApply.getCreditPactId())){
                    MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                    mfCusCreditContract.setId(mfBusApply.getCreditPactId());
                    mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
                    if(mfCusCreditContract != null){
                        creditSum = mfCusCreditContract.getCreditSum();
                        creditAmt = mfCusCreditContract.getAuthBal();
                    }
                }else
                    if(StringUtil.isNotEmpty(mfBusApply.getCreditAppId())){
                    MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
                    mfCusCreditApply.setCreditAppId(mfBusApply.getCreditAppId());
                    mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
                    if(mfCusCreditApply != null){
                        creditSum = mfCusCreditApply.getCreditSum();
                        creditAmt = mfCusCreditApply.getAuthBal();
                    }
                }
                getObjValue(formapply0006, mfBusApply);

                this.changeFormProperty(formapply0006, "creditSum", "initValue", String.valueOf(creditSum));
                this.changeFormProperty(formapply0006, "creditAmt", "initValue", String.valueOf(creditAmt));
                //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
                Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
                String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
                this.changeFormProperty(formapply0006, "fincRate", "unit", rateUnit);
                this.changeFormProperty(formapply0006, "overRate", "unit", rateUnit);
                this.changeFormProperty(formapply0006, "cmpdRate", "unit", rateUnit);
                // 处理期限的展示单位。
                Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
                String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
                this.changeFormProperty(formapply0006, "term", "unit", termUnit);

                //处理还款方式
                List<OptionsList> repayTypeList = new ArrayList<OptionsList>();
                MfBusAppKind mfBusAppKindRepayType = new MfBusAppKind();
                mfBusAppKindRepayType.setAppId(appId);
                mfBusAppKindRepayType = mfBusAppKindFeign.getById(mfBusAppKindRepayType);
                if (StringUtil.isEmpty(mfBusAppKindRepayType.getRepayType())) {
                    List<ParmDic> pdl = (List<ParmDic>) new CodeUtils().getCacheByKeyName("REPAY_TYPE");
                    for (ParmDic parmDic : pdl) {
                        OptionsList s = new OptionsList();
                        s.setOptionLabel(parmDic.getOptName());
                        s.setOptionValue(parmDic.getOptCode());
                        repayTypeList.add(s);
                    }
                } else {
                    Map<String, String> dicMap = new CodeUtils().getMapByKeyName("REPAY_TYPE");
                    String[] repayTypes = mfBusAppKindRepayType.getRepayType().split("\\|");
                    for (int i = 0; i < repayTypes.length; i++) {
                        OptionsList s = new OptionsList();
                        s.setOptionValue(repayTypes[i]);
                        s.setOptionLabel(dicMap.get(repayTypes[i]));
                        repayTypeList.add(s);
                    }
                }

                this.changeFormProperty(formapply0006, "repayType", "optionArray", repayTypeList);

                //处理贷款投向
                List<OptionsList> fincUseList = new ArrayList<OptionsList>();
                MfBusAppKind mfBusAppKind = new MfBusAppKind();
                mfBusAppKind.setAppId(appId);
                mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
                if (StringUtil.isEmpty(mfBusAppKind.getFincUse())) {
                    List<ParmDic> pdl = (List<ParmDic>) new CodeUtils().getCacheByKeyName("TRADE");
                    for (ParmDic parmDic : pdl) {
                        OptionsList s = new OptionsList();
                        s.setOptionLabel(parmDic.getOptName());
                        s.setOptionValue(parmDic.getOptCode());
                        fincUseList.add(s);
                    }
                } else {
                    Map<String, String> dicMap = new CodeUtils().getMapByKeyName("TRADE");
                    String[] fincUses = mfBusAppKind.getFincUse().split("\\|");
                    for (int i = 0; i < fincUses.length; i++) {
                        OptionsList s = new OptionsList();
                        s.setOptionValue(fincUses[i]);
                        s.setOptionLabel(dicMap.get(fincUses[i]));
                        fincUseList.add(s);
                    }
                }
                this.changeFormProperty(formapply0006, "fincUse", "optionArray", fincUseList);

                //处理担保方式
                List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
                MfBusAppKind mfBusAppKindVouType = new MfBusAppKind();
                mfBusAppKindVouType.setAppId(appId);
                mfBusAppKindVouType = mfBusAppKindFeign.getById(mfBusAppKindVouType);
                if (StringUtil.isEmpty(mfBusAppKindVouType.getVouType())) {
                    List<ParmDic> pdl = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
                    for (ParmDic parmDic : pdl) {
                        OptionsList s = new OptionsList();
                        s.setOptionLabel(parmDic.getOptName());
                        s.setOptionValue(parmDic.getOptCode());
                        vouTypeList.add(s);
                    }
                } else {
                    Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
                    String[] vouTypes = mfBusAppKindVouType.getVouType().split("\\|");
                    for (int i = 0; i < vouTypes.length; i++) {
                        if(dicMap.containsKey(vouTypes[i])){
                            OptionsList s = new OptionsList();
                            s.setOptionValue(vouTypes[i]);
                            s.setOptionLabel(dicMap.get(vouTypes[i]));
                            vouTypeList.add(s);
                        }
                    }
                }
                this.changeFormProperty(formapply0006, "vouType", "optionArray", vouTypeList);

                //处理其他担保方式
                List<OptionsList> vouTypeOtherList = new ArrayList<OptionsList>();
                if (vouTypeList != null && vouTypeList.size() > 0) {
                    for (OptionsList options1 : vouTypeList) {
                        if ("1".equals(options1.getOptionValue())) {
                            continue;
                        }
                        OptionsList s1 = new OptionsList();
                        s1.setOptionLabel(options1.getOptionLabel());
                        s1.setOptionValue(options1.getOptionValue());
                        vouTypeOtherList.add(s1);
                    }
                }

                this.changeFormProperty(formapply0006, "vouTypeOther", "optionArray", vouTypeOtherList);

                //子产品列表  只确定了一个产品的，多个产品联动暂不能实现
                MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(mfBusApply.getKindNo());
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
                this.changeFormProperty(formapply0006, "subKindNo", "optionArray", subKindList);

                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formapply0006, "propertySeeTag", query);
                dataMap.put("htmlStr", htmlStr);
                dataMap.put("flag", "success");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return dataMap;
    }

    /**
     * 终止业务<br>
     * 此功能用于所有阶段，请修改者知悉<br>
     * 进件、签约，终止业务按钮<br>
     *
     * @return
     * @throws Exception
     * @author WangChao
     * @date 2017-8-2 下午3:24:06
     */
    @RequestMapping(value = "/disagree", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> disagree(Model model, String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApplyFeign.doDisagree(mfBusApply);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("appId", appId);
        dataMap.put("flag", "success");

        return dataMap;
    }

    /**
     * 方法描述： 业务终止页面
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-2-26 下午6:55:42
     */
    @RequestMapping(value = "/inputDisagreeBuss")
    public String inputDisagreeBuss(Model model, String appId, String pactId, String fincId, String busFlag,String busEntrance) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formdisagreeBuss = formService.getFormData("disagreeBuss");
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusApply.setDisagreeType("2");//终止业务否决
        mfBusApply.setDisagreeOpName(User.getRegName(request));
        mfBusApply.setDisagreeOpNo(User.getRegNo(request));
        mfBusApply.setDisagreeTime(DateUtil.getDateTime());
        mfBusApply.setDisagreeDate(DateUtil.getDate());
        mfBusApply.setFincId(fincId);
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact = pactInterfaceFeign.getByAppId(appId);
        if (mfBusPact != null) {
            mfBusApply.setAppAmt(mfBusPact.getPactAmt());
            mfBusApply.setFincRate(mfBusPact.getFincRate());
            mfBusApply.setTerm(mfBusPact.getTerm());
            mfBusApply.setTermType(mfBusPact.getTermType());
        }
        if (StringUtil.isNotEmpty(fincId)) {
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(fincId);
            mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
            if (mfBusFincApp != null) {
                mfBusApply.setAppAmt(mfBusFincApp.getPutoutAmt());
                mfBusApply.setFincRate(mfBusFincApp.getFincRate());
                mfBusApply.setTerm(mfBusFincApp.getTermMonth());
                mfBusApply.setTermType(mfBusFincApp.getTermType());
            }
        }
        MfBusAppKind mfBusAppKind = new MfBusAppKind();
        mfBusAppKind.setAppId(appId);
        mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
        mfBusApply.setFincRate(MathExtend.showRateMethod(mfBusApply.getRateType(), mfBusApply.getFincRate(), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
        getObjValue(formdisagreeBuss, mfBusApply);
        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
        this.changeFormProperty(formdisagreeBuss, "term", "unit", termUnit);
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        this.changeFormProperty(formdisagreeBuss, "fincRate", "unit", rateUnit);
        model.addAttribute("formdisagreeBuss", formdisagreeBuss);
        model.addAttribute("query", "");
        model.addAttribute("appId", appId);
        model.addAttribute("pactId", pactId);
        model.addAttribute("fincId", fincId);
        model.addAttribute("busFlag", busFlag);
        model.addAttribute("busEntrance", busEntrance);
        model.addAttribute("cusNo", mfBusApply.getCusNo());
        return "/component/app/inputDisagreeBuss";
    }


    /**
     * 方法描述： 终止业务信息保存
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-2-26 下午8:04:04
     */
    @RequestMapping(value = "/insertDisagreeInfoAjax")
    @ResponseBody
    public Map<String, Object> insertDisagreeInfoAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formdisagreeBuss = formService.getFormData("disagreeBuss");
        getFormValue(formdisagreeBuss, getMapByJson(ajaxData));
        if (this.validateFormData(formdisagreeBuss)) {
            MfBusApply mfBusApply = new MfBusApply();
            setObjValue(formdisagreeBuss, mfBusApply);
            dataMap = mfBusApplyFeign.doDisagree(mfBusApply);
        }
        return dataMap;
    }

    /**
     * 方法描述： 获得业务信息
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-8-24 下午6:18:27
     */
    @RequestMapping(value = "/getApplyDetailInfoAjax")
    @ResponseBody
    public Map<String, Object> getApplyDetailInfoAjax(String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        //获取详情信息
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        dataMap.put("mfBusApply", mfBusApply);

        TaskImpl task = wkfInterfaceFeign.getTaskWithUser(mfBusApply.getWkfAppId(), "", User.getRegNo(request));
        if (task == null) {//表示业务已经结束
            dataMap.put("wkfFlag", "0");
        } else {//表示业务尚未结束
            //节点编号
            dataMap.put("wkfFlag", "1");
            dataMap.put("nodeName", task.getActivityName());
        }
        return dataMap;
    }


    /**
     * 方法描述： 跳转至审批状态查询列表页面
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-8-30 下午5:09:23
     */
    @RequestMapping(value = "/getApprovalStsListPage")
    public String getApprovalStsListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        CodeUtils codeUtils = new CodeUtils();
        JSONArray appStsJsonArray = codeUtils.getJSONArrayByKeyName("APP_STS");
        this.getHttpRequest().setAttribute("appStsJsonArray", appStsJsonArray);
        JSONArray kindTypeJsonArray = codeUtils.getJSONArrayByKeyName("KIND_NO");
        this.getHttpRequest().setAttribute("kindTypeJsonArray", kindTypeJsonArray);
//		model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_ApprovalStsList";
    }

    /**
     * 方法描述：获取审批状态查询列表数据
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-8-30 下午5:10:35
     */
    @RequestMapping(value = "/findApprovalStsListByPageAjax")
    @ResponseBody
    public Map<String, Object> findApprovalStsListByPageAjax(String ajaxData, int pageSize, int pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            mfBusApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfBusApply.setCriteriaList(mfBusApply, ajaxData);//我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusApply", mfBusApply));
            //自定义查询Bo方法
            ipage = mfBusApplyFeign.findApprovalStsListByPage(ipage);
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
     * 方法描述： 根据业务上次审批意见类型获得query
     *
     * @param appId
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-9-2 下午5:32:17
     */
    @RequestMapping(value = "/getQueryBylastApproveType")
    public String getQueryBylastApproveType(String appId) throws Exception {
        MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
        mfBusApplyHis.setAppId(appId);
        /**
         * 上次审批意见类型。
         * 如果上次审批的审批意见类型为发回补充资料，设置query为"",表单可编辑,要件可上传
         */
        List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
        list = mfBusApplyHisFeign.getListByAppId(mfBusApplyHis);
        ;
        String query = "";
        if (list != null && list.size() > 0) {
            mfBusApplyHis = list.get(0);
            String lastApproveType = mfBusApplyHis.getApproveResult();
            if (StringUtil.isNotEmpty(lastApproveType) && AppConstant.OPINION_TYPE_DEALER.equals(lastApproveType)) {
                query = "";
            }
        }
        return query;
    }


    /**
     * 今日申请业务列表页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCashListPage")
    public String getCashListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request,
                response);
        String dataType = this.getHttpRequest().getParameter("dataType");
        this.getHttpRequest().setAttribute("dataType", dataType);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_ListForCash";
    }

    @RequestMapping(value = "/findByPageForCashAjax")
    @ResponseBody
    public Map<String, Object> findByPageForCashAjax(String ajaxData, int pageSize, int pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            mfCusAndApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);//我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            String dataType = this.getHttpRequest().getParameter("dataType");
            if ("applyCount".equals(dataType)) {//借款申请数列表
                mfCusAndApply.setRegTime(DateUtil.getDate());
            } else if ("vetoCount".equals(dataType)) {//借款否决数列表
                mfCusAndApply.setRegTime(DateUtil.getDate());
                mfCusAndApply.setAppSts("5");
            }else {
            }
            //自定义查询Bo方法
            ipage = mfBusApplyFeign.getCusAndApplyForCash(ipage, mfCusAndApply);
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
     * 方法描述：单独提交业务流程，并更新 业务状态(用于审批子流程完成之后，提交业务流程失败的情况，手动提交业务流程)
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-10-24 上午10:53:24
     */
    @RequestMapping(value = "/commitBusProcessAjax")
    @ResponseBody
    public Map<String, Object> commitBusProcessAjax(String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("flag", "success");
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
//			logger.info("手动进行业务流程提交，申请号：{}，合同号：{}，业务流程编号：{}",mfBusApply.getAppId(),mfBusApply.getPactId(),mfBusApply.getWkfAppId());
            Result result = wkfBusInterfaceFeign.doCommitNextStepWithUser(mfBusApply.getAppId(), mfBusApply.getWkfAppId(), User.getRegNo(request));
            if (null != result && result.isSuccess()) {
                dataMap.put("msg", result.getMsg());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", result.getMsg());
            }
        } catch (Exception e) {
//			logger.error("审批子流程完成,手动进行业务流程提交失败："+e.getMessage(),e);
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
            throw e;
        }
        return dataMap;
    }

    /**
     * 信贷数据推送提交
     * @param ajaxData
     * @param appId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditPushProcessSubmitAjax")
    @ResponseBody
    public Map<String, Object> creditPushProcessSubmitAjax(String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("flag", "success");
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            Result result = mfBusApplyFeign.creditPushProcessSubmit(mfBusApply);
            if (null != result && result.isSuccess()) {
                dataMap.put("msg", result.getMsg());
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
     * 方法描述： 风控尽调（主要对接第三方查询数据）
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-12-14 上午10:38:47
     */
    @RequestMapping(value = "/riskReport")
    public String riskReport(Model model, String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt() / 10000));
        mfBusApply = mfBusApplyFeign.processDataForApply(mfBusApply);//处理利率格式
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
        dataMap.put("termUnit", termUnit);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        //三方数据查询类型
        JSONArray array = new CodeUtils().getJSONArrayByKeyName("THIRD_SERVICE_TYPE");
        ajaxData = array.toString();
        model.addAttribute("mfBusApply", mfBusApply);
        model.addAttribute("mfCusCustomer", mfCusCustomer);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_RiskReport";
    }

    /**
     * 方法描述： 跳转至三方查询入参页面
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-12-20 上午9:40:42
     */
    @RequestMapping(value = "/thirdInput")
    public String thirdInput(Model model, String ajaxData, String id, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formapply0001;
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusApply.getCusNo());
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        formapply0001 = formService.getFormData("riskreportquery");
        getObjValue(formapply0001, mfBusApply);
        getObjValue(formapply0001, mfCusCustomer);
        this.changeFormProperty(formapply0001, "thirdQueryType", "initValue", ajaxData);
        if (ajaxData != null && BizPubParm.THIRD_SERVICE_TYPE80.equals(ajaxData)) {
            CodeUtils cu = new CodeUtils();
            JSONArray jsonArray = cu.getJSONArrayByKeyName("SEARCH_NO");
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonArray.getJSONObject(i).put("id", jsonArray.getJSONObject(i).getString("optCode"));
                jsonArray.getJSONObject(i).put("name", jsonArray.getJSONObject(i).getString("optName"));
            }
            JSONObject json = new JSONObject();
            json.put("searchNo", jsonArray);
            ajaxData = json.toString();
        }
        model.addAttribute("formapply0001", formapply0001);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_ThirdInput";
    }

    /**
     * 方法描述： 风控尽调保存提交下一步业务流程
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-12-15 上午10:52:08
     */
    @RequestMapping(value = "/riskReportAjax")
    @ResponseBody
    public Map<String, Object> riskReportAjax(String ajaxData, String appId, String wkfAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Result result = wkfBusInterfaceFeign.doCommitNextStepWithUser(appId, wkfAppId, User.getRegNo(request));
            if (result.isSuccess()) {
                dataMap.put("flag", "success");
                dataMap.put("msg", result.getMsg());
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
     * 方法描述： 跳转至渠道业务系统进件列表页面
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-12 下午3:48:58
     */
    @RequestMapping(value = "/getTrenchApplyListPage")
    public String getTrenchApplyListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        CodeUtils codeUtils = new CodeUtils();
        JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("CUS_TYPE");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        JSONArray gdCusTypeJsonArray = codeUtils.getJSONArrayByKeyName("GD_CUS_TYPE");
        this.getHttpRequest().setAttribute("gdCusTypeJsonArray", gdCusTypeJsonArray);
        JSONArray appStsJsonArray = codeUtils.getJSONArrayByKeyName("APP_STS");
        this.getHttpRequest().setAttribute("appStsJsonArray", appStsJsonArray);
        JSONArray kindTypeJsonArray = codeUtils.getJSONArrayByKeyName("KIND_NO");
        this.getHttpRequest().setAttribute("kindTypeJsonArray", kindTypeJsonArray);
        // 办理阶段
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        // JSONArray flowNodeJsonArray = prdctInterface.getFlowNodeArray();
        // this.getHttpRequest().setAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("channelSourceNo", (String) this.getHttpRequest().getSession().getAttribute("channelSourceNo"));
        return "/component/app/MfBusApply_TrenchBussList";
    }

    /**
     * 方法描述： 跳转渠道业务系统中业务查询页面
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-12 上午10:32:40
     */
    @RequestMapping(value = "/getTrenchBussList")
    public String getTrenchBussList() throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        CodeUtils codeUtils = new CodeUtils();
        JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("CUS_TYPE");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        JSONArray gdCusTypeJsonArray = codeUtils.getJSONArrayByKeyName("GD_CUS_TYPE");
        this.getHttpRequest().setAttribute("gdCusTypeJsonArray", gdCusTypeJsonArray);
        JSONArray appStsJsonArray = codeUtils.getJSONArrayByKeyName("APP_STS");
        this.getHttpRequest().setAttribute("appStsJsonArray", appStsJsonArray);
        JSONArray kindTypeJsonArray = codeUtils.getJSONArrayByKeyName("KIND_NO");
        this.getHttpRequest().setAttribute("kindTypeJsonArray", kindTypeJsonArray);
        // 办理阶段
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        this.getHttpRequest().setAttribute("flowNodeJsonArray", flowNodeJsonArray);
        return "/component/app/TrenchBussQueryList";
    }

    /**
     * 方法描述： 渠道业务系统进件列表
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-12 下午3:49:34
     */
    @RequestMapping(value = "/findTrenchApplyByPageAjax")
    @ResponseBody
    public Map<String, Object> findTrenchApplyByPageAjax(String ajaxData, Integer pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            mfCusAndApply = setCusAndApply(mfCusAndApply);
            mfCusAndApply.setAppSts(BizPubParm.APP_STS_PASS);
            mfCusAndApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusAndApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
            ipage = mfBusApplyFeign.findTrenchBussByPage(ipage);
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
     * 方法描述： 设置申请信息
     *
     * @param mfCusAndApply
     * @return
     * @throws Exception MfCusAndApply
     * @author 沈浩兵
     * @date 2018-3-8 下午4:16:52
     */
    private MfCusAndApply setCusAndApply(MfCusAndApply mfCusAndApply) throws Exception {
        String dataRang = (String) this.getHttpRequest().getSession().getAttribute("dataRang");
        String trenchOpNo = (String) this.getHttpRequest().getSession().getAttribute("trenchOpNo");
        String channelSourceNo = (String) this.getHttpRequest().getSession().getAttribute("channelSourceNo");
        if (BizPubParm.TRENCH_USER_DATA_RANG_1.equals(dataRang)) {// 本人
            mfCusAndApply.setTrenchOpNo(trenchOpNo);
            mfCusAndApply.setChannelSourceNo(channelSourceNo);
        } else if (BizPubParm.TRENCH_USER_DATA_RANG_2.equals(dataRang)) {// 本渠道
            mfCusAndApply.setChannelSourceNo(channelSourceNo);
        } else if (BizPubParm.TRENCH_USER_DATA_RANG_3.equals(dataRang)) {// 本渠道及其子渠道
            MfBusTrench mfBusTrench = new MfBusTrench();
            mfBusTrench.setTrenchUid(channelSourceNo);
            String trenchChildStr = cusInterfaceFeign.getTrenchChildStr(mfBusTrench);
            mfCusAndApply.setChannelSourceStr(trenchChildStr);
        }else {
        }
        return mfCusAndApply;
    }

    /**
     * 方法描述： 渠道业务系统中业务查询列表
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-12 上午10:39:07
     */
    @RequestMapping(value = "/findTrenchBussByPageAjax")
    @ResponseBody
    public Map<String, Object> findTrenchBussByPageAjax(String ajaxData, Integer pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            mfCusAndApply = setCusAndApply(mfCusAndApply);
            // mfCusAndApply.setAppSts(BizPubParm.APP_STS_PASS);
            mfCusAndApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusAndApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
            ipage = mfBusApplyFeign.findTrenchBussByPage(ipage);
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
     * 方法描述： 获得渠道业务系统中业务详情
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-9 下午3:56:16
     */
    @RequestMapping(value = "/getTrenchApplySummary")
    public String getTrenchApplySummary(Model model, String appId, String operable, String busEntrance, String reconsiderId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        // 获取详情信息
        dataMap = mfBusApplyFeign.getBusDetailInfo(mfBusApply);

        // 业务申请信息处理
        mfBusApply = JsonStrHandling.handlingStrToBean(dataMap.get("mfBusApply"), MfBusApply.class);

        // 判断客户表单信息是否允许编辑
        String cusFormQuery = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(), BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
        String query = cusFormQuery;
        //factor传的是"",到web端就变成了null
        if (query == null) {
            query = "";
        }
        dataMap.put("guaranteeType", "loan");
        FormData formapply0006;
        if (mfBusApply.getBusModel().equals(BizPubParm.BUS_MODEL_5)) {// 应收账款融资(保理业务)
            formapply0006 = formService.getFormData("apply0006bl");
        } else if (mfBusApply.getBusModel().equals(BizPubParm.BUS_MODEL_14) || mfBusApply.getBusModel().equals(BizPubParm.BUS_MODEL_15) || mfBusApply.getBusModel().equals(BizPubParm.BUS_MODEL_17)) {
            formapply0006 = formService.getFormData("apply0006db");
            dataMap.put("guaranteeType", "gua");
        } else {
            formapply0006 = formService.getFormData("apply0006");
        }
        dataMap.put("busSubmitCnt", "0");
        if (!BizPubParm.APP_STS_UN_SUBMIT.equals(mfBusApply.getAppSts()) && !BizPubParm.APP_STS_PROCESS.equals(mfBusApply.getAppSts())) {
            dataMap.put("busSubmitCnt", "1");
        }
        getObjValue(formapply0006, mfBusApply);
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        this.changeFormProperty(formapply0006, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0006, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0006, "cmpdRate", "unit", rateUnit);
        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
        this.changeFormProperty(formapply0006, "term", "unit", termUnit);
        // 申请客户信息
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer = JsonStrHandling.handlingStrToBean(dataMap.get("mfCusCustomer"), MfCusCustomer.class);
        // 反欺诈-服务类型
        dataMap.put("itemType", BizPubParm.ANTIFRAUD_ITEM_TYPE);
        // 反欺诈-服务编号
        dataMap.put("itemNo", BizPubParm.ANTIFRAUD_ITEM_NO);
        JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
        String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
        String ajaxData = items;
        Map<String, String> parmMap = new HashMap<String, String>();
        parmMap.put("appId", appId);
        parmMap.put("wkfAppId", mfBusApply.getWkfAppId());
        parmMap.put("cusNo", mfBusApply.getCusNo());
        parmMap.put("busModel", mfBusApply.getBusModel());
        parmMap.put("operable", operable);
        parmMap.put("appSts", mfBusApply.getAppSts());
        parmMap.put("applyProcessId", mfBusApply.getApplyProcessId());
        parmMap.put("vouType", mfBusApply.getVouType());
        parmMap.put("cusBaseType", mfCusCustomer.getCusBaseType());
        /*
         * if (StringUtil.isEmpty(busEntrance)) { busEntrance = "4";//如果入口为空，为审批视图 }
         */

        if ("4".equals(busEntrance)) {
            // 如果存在复议否决审批task, 说明当前处于否决复议审批, 需要展示否决复议审批历史
            Task taskAppro = wkfInterfaceFeign.getTask("reconsider" + mfBusApply.getAppId(), null);// 否决复议审批task
            if (taskAppro != null) {
                reconsiderId = "reconsider" + mfBusApply.getAppId();
            }
        }

        parmMap.put("busEntrance", busEntrance);
        Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMapByAppId(parmMap);
        dataMap.putAll(busViewMap);

        model.addAttribute("mfBusApply", mfBusApply);
        model.addAttribute("appId", appId);
        model.addAttribute("cusFormQuery", cusFormQuery);
        model.addAttribute("query", query);
        model.addAttribute("formapply0006", formapply0006);
        model.addAttribute("rateUnit", rateUnit);
        model.addAttribute("mfCusCustomer", mfCusCustomer);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("operable", operable);
        model.addAttribute("busEntrance", busEntrance);
        model.addAttribute("reconsiderId", reconsiderId);

        model.addAttribute("dataMap", dataMap);

        return "/component/app/MfBusApply_TrenchApplyDetail";
    }

    /**
     * 经销商复核
     *
     * @param model
     * @param ajaxData
     * @param appId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dealerReCheckAjax")
    @ResponseBody
    public Map<String, Object> dealerReCheckAjax(Model model, String ajaxData, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            mfBusApply.setAppId(appId);
            mfBusApply.setDataShowWay("1");//进件数据PC主端是否展示（0：不展示，1：展示），内部人员申请默认展示，经销商申请复核后展示
            mfBusApplyFeign.update(mfBusApply);
            dataMap.put("flag", "success");
            dataMap.put("msg", "复核成功！");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "复核失败！");
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述：  根据业务申请号和节点编号跳转到指定的节点
     *
     * @param appId
     * @return
     * @throws Exception Map<String,Object>
     * @author 沈浩兵
     * @date 2018年5月10日 上午12:32:57
     */
    @RequestMapping(value = "/doCommitToNodeByNodeNoAjax")
    @ResponseBody
    public Map<String, Object> doCommitToNodeByNodeNoAjax(String appId, String nodeNo)
            throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply.setNodeNo(nodeNo);
            mfBusApplyFeign.doCommitToNodeByNodeNo(mfBusApply);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getDepositRateByBaseTypeAjax")
    @ResponseBody
    public Map<String, Object> getDepositRateByBaseTypeAjax(String baseType, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            String kindNo = "1000";//默认循环额度贷款
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("cusNo", cusNo);
            parmMap.put("kindNo", kindNo);
            parmMap.put("baseType", baseType);
            MfCusPorductCredit mfCusPorductCredit = creditApplyInterfaceFeign.getByCusNoKindNoAndBaseType(parmMap);
            Double depositRate = 0D;
            if (null != mfCusPorductCredit) {
                depositRate = mfCusPorductCredit.getDepositRate();
            }
            dataMap.put("depositRate", depositRate);
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
     * 初始化申请进件业务
     *
     * @param model
     * @return
     * @throws
     * @author 周凯强
     * @date
     */
    @RequestMapping(value = "applyInitInput")
    public String applyInitInput(Model model,String liftId,String liftType,String liftKindNo,String liftCusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId = "initLoanApplyBase";
        FormData formapply0007_query = formService.getFormData(formId);
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        JSONObject json = new JSONObject();
        json.put("rateType", JSONObject.fromObject(rateTypeMap));
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", json);
        model.addAttribute("liftId", liftId);
        model.addAttribute("liftType", liftType);
        model.addAttribute("liftKindNo", liftKindNo);
        model.addAttribute("liftCusNo", liftCusNo);
        model.addAttribute("formapply0007_query", formapply0007_query);
        model.addAttribute("query", "");
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        return "/component/app/MfBusApply_applyInput";
    }

    /**
     * @param ajaxData
     * @param kindNo
     * @param cusNo
     * @return
     * @throws Exception
     * @author 周凯强
     * @date
     */
    @RequestMapping(value = "/chooseFormInitAjax")
    @ResponseBody
    public Map<String, Object> chooseFormInitAjax(String ajaxData, String kindNo, String cusNo,String liftType,String liftId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        // ---------------根据客户号获取客户信息--------------
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

        // -------------查询客户可以办理的产品集合-------------
        MfSysKind mfSysKindPd = new MfSysKind();
        mfSysKindPd.setKindProperty("2");
        mfSysKindPd.setUseFlag("1");
        mfSysKindPd.setCusType(mfCusCustomer.getCusType());
        mfSysKindPd.setBrNo(User.getOrgNo(request));
        mfSysKindPd.setRoleNoArray(User.getRoleNo(request));
        List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKindPd);

        // 授信合同号
        String creditPactNo = "";
        // 授信合同id
        String creditPactId = "";
        String initFormId = "initLoanApplyBase";
        try {
            // 1.此时该客户任何业务都不能办理
            if (mfSysKindList == null || mfSysKindList.size() == 0) {
                FormData formapply0007_query = formService.getFormData(initFormId);
                if(StringUtil.isNotEmpty(cusNo)){
                    MfBusApply mfBusApply = new MfBusApply();
                    mfBusApply.setIsAgainBus("0");
                    mfBusApply.setAgainBusLv(0.2);
                    MfCusCorpBaseInfo mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
                    if(mfCusCorpBaseInfo!=null){
                        String careaProvice = mfCusCorpBaseInfo.getCareaProvice();
                        if(StringUtil.isNotEmpty(careaProvice)&&careaProvice.startsWith("110")){
                            mfBusApply.setIsAgainBus("1");
                            mfBusApply.setAgainBusRate(0.5);
                        }
                        if(StringUtil.isNotEmpty(mfCusCorpBaseInfo.getProjSize())&&!"1".equals(mfCusCorpBaseInfo.getProjSize())){
                            mfBusApply.setIsAgainBus("1");
                            mfBusApply.setAgainBusRate(0.5);
                        }
                        if(StringUtil.isNotEmpty(mfCusCorpBaseInfo.getIfHighTech())&&"0".equals(mfCusCorpBaseInfo.getIfHighTech())){
                            mfBusApply.setAgainBusLv(0.1);
                        }
                        if(StringUtil.isNotEmpty(mfCusCorpBaseInfo.getWayClass())&&mfCusCorpBaseInfo.getWayClass().startsWith("R")){
                            mfBusApply.setAgainBusLv(0.1);
                        }
                        getObjValue(formapply0007_query, mfBusApply);
                    }
                }


                // ----修改说明
                this.changeFormProperty(formapply0007_query, "remark", "initValue",
                        MessageEnum.FIRST_CHECK_PRODUCT.getMessage());
                this.changeFormProperty(formapply0007_query, "remark", "fieldStyle", "text-danger");
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formapply0007_query, "bootstarpTag", "");
                dataMap.put("cusName", mfCusCustomer.getCusName());
                dataMap.put("htmlStr", htmlStr);
                dataMap.put("flag", "error");

            } else {
                // ------此时该客户可办理其他业务-----------
                if (StringUtil.isEmpty(kindNo)) {
                    // 2.产品号为空--------------------------
                    // ------获取此时客户可办理的默认表单----就是客户办理的第一个表单
                    // --------调用公共的表单封装方法------
                    dataMap = formContent(mfCusCustomer, mfSysKindList.get(0),liftType,liftId);

                } else {
                    // hasKindNo为判断其可以办理该产品的标志。
                    MfSysKind useableSysKind = null;
                    for (MfSysKind mfSysKind : mfSysKindList) {
                        if (kindNo.equals(mfSysKind.getKindNo())) {
                            // ----得到默认的表单的产品-------
                            useableSysKind = mfSysKind;
                            break;
                        }
                    }

                    // 3.此时该客户正好可以办理该产品
                    if (useableSysKind != null) {
                        // --------调用公共的表单封装方法------
                        dataMap = formContent(mfCusCustomer, useableSysKind,liftType,liftId);
                    } else {
                        // 4.该客户不可开办当前选中的产品，则根据产品号查询原来的产品
                        MfSysKind sysKind = prdctInterfaceFeign.getSysKindById(kindNo);
                        FormData formapply0007_query = formService.getFormData(initFormId);
                        // -------修改说明--------------
                        this.changeFormProperty(formapply0007_query, "remark", "initValue",
                                "该客户暂不能开办" + " “ " + sysKind.getKindName() + " ”业务产品， " + "请选择其他可以开办的产品");
                        this.changeFormProperty(formapply0007_query, "remark", "fieldStyle", "text-primary");
                        JsonFormUtil jsonFormUtil = new JsonFormUtil();
                        String htmlStr = jsonFormUtil.getJsonStr(formapply0007_query, "bootstarpTag", "");
                        dataMap.put("cusName", mfCusCustomer.getCusName());
                        dataMap.put("htmlStr", htmlStr);
                        dataMap.put("flag", "error");
                    }
                }
            }
            dataMap.put("mfSysKindList", mfSysKindList);
            dataMap.put("creditPactNo", creditPactNo);
            dataMap.put("creditPactId", creditPactId);
            return dataMap;

        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }

    }
    /**
     * @param ajaxData
     * @param kindNo
     * @param cusNo
     * @return
     * @throws Exception
     * @author 周凯强
     * @date
     */
    @RequestMapping(value = "/getKindNoAjax")
    @ResponseBody
    public Map<String, Object> getKindNoAjax(String cusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            // ---------------根据客户号获取客户信息--------------
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

            // -------------查询客户可以办理的产品集合-------------
            MfSysKind mfSysKindPd = new MfSysKind();
            mfSysKindPd.setKindProperty("2");
            mfSysKindPd.setUseFlag("1");
            mfSysKindPd.setCusType(mfCusCustomer.getCusType());
            mfSysKindPd.setBrNo(User.getOrgNo(request));
            mfSysKindPd.setRoleNoArray(User.getRoleNo(request));
            List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKindPd);
            if(mfSysKindList != null && mfSysKindList.size() > 0){
                dataMap.put("mfSysKind", mfSysKindList.get(0));
                dataMap.put("mfSysKindSize", mfSysKindList.size());
            }else{
                dataMap.put("mfSysKindSize", 0);
            }
            dataMap.put("flag", "success");
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "获取产品号失败");
        }
        return dataMap;
    }

    /**
     * 方法描述:获取客户的第一个授信信息
     *
     * @param cusNo
     * @param kindNo
     * @return
     */
    private MfCusPorductCredit getFirstMfCusPorductCredit(String cusNo, String kindNo) {
        MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
        mfCusPorductCredit.setCusNo(cusNo);
        mfCusPorductCredit.setKindNo(kindNo);
        List<MfCusPorductCredit> mfCusPorductCreditList = mfCusPorductCreditFeign.getMfCusPorductCreditListByCusNo(mfCusPorductCredit);
        if (mfCusPorductCreditList != null && mfCusPorductCreditList.size() > 0) {
            mfCusPorductCredit = mfCusPorductCreditList.get(0);
        }
        return mfCusPorductCredit;
    }

    /**
     * 方法描述:获取已经授信过的产品种类
     *
     * @param
     * @return
     * @throws Exception
     */
    private List<MfSysKind> getMfSysKindListByCreditProduct(List<MfCusPorductCredit> mfCusPorductCreditList) throws Exception {
        List<MfSysKind> mfSysKindList = new ArrayList<MfSysKind>();
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setUseFlag("1");
        List<MfSysKind> sysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
        for (MfCusPorductCredit cusPorductCredit : mfCusPorductCreditList) {
            for (MfSysKind sysKind : sysKindList) {
                if (sysKind.getKindNo().equals(cusPorductCredit.getKindNo())) {
                    mfSysKindList.add(sysKind);
                    break;
                }
            }
        }
        return mfSysKindList;
    }


    /**
     * <p>Description: 补充表单里面的内容 封装页面所需要的参数</p>
     *
     * @param mfCusCustomer
     * @param mfSysKind
     * @return
     * @throws Exception
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @author 周凯强
     * @date 2018年6月26日上午9:44:57
     */
    private Map<String, Object> formContent(MfCusCustomer mfCusCustomer, MfSysKind mfSysKind,String liftType,String liftId)
            throws Exception, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //处理担保方式
        List<ParmDic> parmDicList = getVouTypeOtherSelectByNoAjax(mfSysKind);
        Map<String,String> vouTypeMap =  new CodeUtils().getMapByKeyName("VOU_TYPE");
        dataMap.put("parmDicList" , parmDicList);
        List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
        if (StringUtil.isNotEmpty(mfSysKind.getVouTypeDef())) {
            OptionsList op = new OptionsList();
            op.setOptionLabel(vouTypeMap.get(mfSysKind.getVouTypeDef()));
            op.setOptionValue(mfSysKind.getVouTypeDef());
            op.setOptionId(WaterIdUtil.getWaterId());
            vouTypeList.add(op);
        }
        for (int i = 0; i < parmDicList.size(); i++) {
            ParmDic vouTypeDic = parmDicList.get(i);
            if (StringUtil.isNotEmpty(mfSysKind.getVouTypeDef())) {
                if (vouTypeDic.getOptCode().equals(mfSysKind.getVouTypeDef())) {
                    continue;
                }
            }
            OptionsList op = new OptionsList();
            op.setOptionLabel(vouTypeDic.getOptName());
            op.setOptionValue(vouTypeDic.getOptCode());
            op.setOptionId(WaterIdUtil.getWaterId());
            vouTypeList.add(op);
        }
        String cmpdRateType = mfSysKind.getCmpdRateType();
        String vouType = mfSysKind.getVouTypeDef();
        mfSysKind.setVouType(vouType);//表单显示默认的担保方式
        String busModel = mfSysKind.getBusModel();//业务模式

        String cusNo = mfCusCustomer.getCusNo();
        String kindNo = mfSysKind.getKindNo();
        String cusBaseType = mfCusCustomer.getCusBaseType();
        String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_input, null, null, User.getRegNo(request));
        MfBusApply mfBusApply = new MfBusApply();
        if(StringUtil.isNotEmpty(liftType)){
            MfBusApply mfBusApply1 = new MfBusApply();
            mfBusApply1.setAppId(liftId);
            mfBusApply1 = mfBusApplyFeign.getById(mfBusApply1);
            PropertyUtils.copyProperties(mfBusApply,mfBusApply1);
            MfBusPact mfBusPact = mfBusPactFeign.getByAppId(liftId);
            mfBusApply.setAppAmt(mfBusPact.getPactAmt());
            mfBusApply.setTerm(mfBusPact.getTerm());
            mfBusApply.setFincRate(mfBusPact.getFincRate());
            mfBusApply.setAppTimeShow(DateUtil.getShowDateTime(DateUtil.getDate()));
            mfBusApply.setAppTime(DateUtil.getDateTime());
            mfBusApply.setAppIdOld(mfBusApply1.getAppId());
            mfBusApply.setOldAppName(mfBusApply1.getAppName());
            mfBusApply.setBreedName1(mfBusApply1.getBreedName());
            mfBusApply.setBreedNo1(mfBusApply1.getBreedNo());
            mfBusApply.setVouType1(mfBusApply1.getVouType());
            mfBusApply.setAgenciesId1(mfBusApply1.getAgenciesId());
            mfBusApply.setAgenciesName1(mfBusApply1.getAgenciesName());
            if("3".equals(liftType)){
                formId = "comLoanApplyBase_GCDB_ZXCK";
                if("1008".equals(kindNo) || "1009".equals(kindNo) ||"1010".equals(kindNo) ||"1011".equals(kindNo) ||"1012".equals(kindNo) ||"1013".equals(kindNo)){
                    formId = "comLoanApplyBase_GCDB_ZXCK_NO_CREDIT";
                }
                mfBusApply.setLoanKind("6");//注销重开
                dataMap.put("breedName1",mfBusApply1.getBreedName());
            }else{
                formId = "comLoanApplyBase_GCDB_XB";
                if("1008".equals(kindNo) || "1009".equals(kindNo) ||"1010".equals(kindNo) ||"1011".equals(kindNo) ||"1012".equals(kindNo) ||"1013".equals(kindNo)){
                    formId = "comLoanApplyBase_GCDB_XB_NO_CREDIT";
                }
                mfBusApply.setLoanKind("5");//续保
            }
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
                mfBusApply.setAuthority(mfBusApplySecond.getAuthority());
                mfBusApply.setSurveyOpinion(mfBusApplySecond.getSurveyOpinion());
                mfBusApply.setStartUpSituation(mfBusApplySecond.getStartUpSituation());
                mfBusApply.setConstructionScope(mfBusApplySecond.getConstructionScope());
            }
        }else{
            //保理申请
            mfBusApply.setCusNo(cusNo);
            mfBusApply.setCusName(mfCusCustomer.getCusName());
            mfBusApply.setTermType(mfSysKind.getTermType());
            mfBusApply.setFincRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getFincRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
            mfBusApply.setCusMngName(mfCusCustomer.getCusMngName());
            mfBusApply.setCusMngNo(mfCusCustomer.getCusMngNo());
            //利率浮动值 或者是利率值
            Double overFloat = mfSysKind.getOverFltRateDef();
            Double cmpdFloat = mfSysKind.getCmpFltRateDef();
            Double overRate = mfSysKind.getOverRate();
            Double cmpdRate = mfSysKind.getCmpdRate();
            if (overFloat == null) {
                overFloat = 0.00;
            }
            if (cmpdFloat == null) {
                cmpdFloat = 0.00;
            }
            if (overRate == null) {
                overRate = 0.00;
            }
            if (cmpdRate == null) {
                cmpdRate = 0.00;
            }
            mfBusApply.setOverFloat(overFloat);
            mfBusApply.setCmpdFloat(cmpdFloat);
            mfBusApply.setOverRate(MathExtend.showRateMethod(mfSysKind.getRateType(), overRate, Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
            mfBusApply.setCmpdRate(MathExtend.showRateMethod(mfSysKind.getRateType(), cmpdRate, Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
            mfBusApply.setAppTime(DateUtil.getDateTime());
            mfBusApply.setAppTimeShow(DateUtil.getDate());
            mfBusApply.setCusMngName(mfCusCustomer.getCusMngName());
            mfBusApply.setCusMngNo(mfCusCustomer.getCusMngNo());
            mfBusApply.setManageOpNo1(User.getRegNo(request));
            mfBusApply.setManageOpName1(User.getRegName(request));
            mfBusApply.setBusModel(mfSysKind.getBusModel());
            mfBusApply.setKindName(mfSysKind.getKindName());
            mfBusApply.setRepayType(mfSysKind.getRepayTypeDef());
            mfBusApply.setVouType(vouType);

            //如果申请过业务并且未提交app_sts=0  则不允许申请新业务  (一个客户只能有一笔未提交的业务)
            MfBusApply parmBusApply = new MfBusApply();
            parmBusApply.setCusNo(cusNo);
            parmBusApply.setAppSts(BizPubParm.APP_STS_UN_COMPLETE);
            parmBusApply = mfBusApplyFeign.getByCusNoAppSts(parmBusApply);
            if (parmBusApply != null) {
                if (kindNo.equals(parmBusApply.getKindNo())) {
                    PropertyUtils.copyProperties(mfBusApply, parmBusApply);
                    mfBusApply.setAppTime(DateUtil.getDateTime());
                    mfBusApply.setAppTimeShow(DateUtil.getDate());
                }
            }
        }
        //这里初始化申请ID了，所以新增时在service层没有再生成ID
        mfBusApply.setAppId(WaterIdUtil.getWaterId("app"));
        //处理金额，double大于十位会变成科学计数法11111111
        DecimalFormat df = new DecimalFormat(",##0.00");
        if ((mfSysKind.getMinAmt() != null) && (mfSysKind.getMaxAmt() != null)) {
            double minAmtD = mfSysKind.getMinAmt();
            double maxAmtD = mfSysKind.getMaxAmt();
            String minAmt = df.format(minAmtD);//融资金额下限
            String maxAmt = df.format(maxAmtD);//融资金额上限
            String amt = minAmt + "-" + maxAmt + "元";
            //  this.changeFormProperty(formapply0007_query, "appAmt", "alt", amt);
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
            // this.changeFormProperty(formapply0007_query, "term", "alt", term);
        }
        FormData formapply0007_query = formService.getFormData(formId);

        getObjValue(formapply0007_query, mfSysKind);
        if(BizPubParm.BUS_MODEL_12.equals(busModel) && BizPubParm.CUS_TYPE_CORP.equals(cusBaseType)){
            MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
            mfCusCorpBaseInfo.setCusNo(mfCusCustomer.getCusNo());
            mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
            mfBusApply.setCorpQua(mfCusCorpBaseInfo.getCorpQua());
            mfBusApply.setNetAssets(mfCusCorpBaseInfo.getNetAssets());

            //企业内部授信总额
            Double amtRest = creditApplyInterfaceFeign.getCreditFincAmtSum(cusNo);
            if(amtRest!=null){
                mfBusApply.setMaxFincAmt(amtRest);
            }else {
                mfBusApply.setMaxFincAmt(0.00);
            }
        }
        getObjValue(formapply0007_query, mfBusApply);

        //mfSysKind和mfBusApply中remark重复，页面用到此字段，页面上会展示产品中的remark的值。此处清空
        this.changeFormProperty(formapply0007_query, "remark", "initValue", "");
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfSysKind.getRateType()).getRemark();
        this.changeFormProperty(formapply0007_query, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formapply0007_query, "overRate", "unit", rateUnit);
        this.changeFormProperty(formapply0007_query, "cmpdRate", "unit", rateUnit);

        //利率范围
        if ((mfSysKind.getMinFincRate() != null) && (mfSysKind.getMaxFincRate() != null)) {
            double minFincRateD = MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getMinFincRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits()));
            double maxFincRateD = MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getMaxFincRate(), Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits()));
            String minFincRate = df.format(minFincRateD);//融资利率下限
            String maxFincRate = df.format(maxFincRateD);//融资利率上限
            String fincRate = minFincRate + "-" + maxFincRate + rateUnit;
           // this.changeFormProperty(formapply0007_query, "fincRate", "alt", fincRate);
            dataMap.put("minFincRate", String.valueOf(minFincRateD));
            dataMap.put("maxFincRate", String.valueOf(maxFincRateD));
        }

        //处理还款方式
        List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
        this.changeFormProperty(formapply0007_query, "repayType", "optionArray", repayTypeList);
        this.changeFormProperty(formapply0007_query, "vouType", "optionArray", vouTypeList);
        //处理产品子类
        String sunKindNo = mfSysKind.getSubKindNo();
        if (StringUtil.isNotEmpty(sunKindNo)) {
            String[] subKindArray = mfSysKind.getSubKindNo().split("\\|");
            Map<String, String> dicMap = new CodeUtils().getMapByKeyName("SUB_KIND_TYPE");
            List<OptionsList> subKindList = new ArrayList<OptionsList>();
            for (int i = 0; i < subKindArray.length; i++) {
                OptionsList op = new OptionsList();
                op.setOptionLabel(dicMap.get(subKindArray[i]));
                op.setOptionValue(subKindArray[i]);
                op.setOptionId(WaterIdUtil.getWaterId());
                subKindList.add(op);
            }
            this.changeFormProperty(formapply0007_query, "subKindNo", "optionArray", subKindList);
        }
        this.changeFormProperty(formapply0007_query, "kindNo", "initValue", kindNo);
        this.changeFormProperty(formapply0007_query, "rateType", "initValue", mfSysKind.getRateType());
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
        this.changeFormProperty(formapply0007_query, "subKindNo", "optionArray", subKindList);
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
        this.changeFormProperty(formapply0007_query, "baseRateType", "optionArray", baseRateTypeList);

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
        this.changeFormProperty(formapply0007_query, "icType", "optionArray", icTypeList);
        //贷款投向
        String jsonArrayStr = prdctInterfaceFeign.getFincUse(kindNo);
        com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
        dataMap.put("fincUse", jsonArray);
        //共同借款人
//		JSONArray coborrower = cusInterface.getCobBorrower(mfCusCustomer.getCusNo());
//		dataMap.put("coborrower", coborrower);

        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        String htmlStr = jsonFormUtil.getJsonStr(formapply0007_query, "bootstarpTag", "");
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
            List<MfSysFeeItem> mfSysFeeItemList = (List<MfSysFeeItem>) (List<MfSysFeeItem>) calcInterfaceFeign.findFeeByPage(ipage).getResult();
            String feeHtmlStr = "";
            String tableId = "tableapplynodefeelist";
            if(BizPubParm.BUS_MODEL_12.equals(busModel)){
                tableId = "tableapplynodefeeguaranteelist";
            }
            if (mfSysFeeItemList != null && mfSysFeeItemList.size() > 0) {
                if(StringUtil.isNotEmpty(liftType)){
                    Gson gson = new Gson();
                    List<MfSysFeeItem> mfSysFeeItemList1 = new ArrayList<MfSysFeeItem>();
                    for (int i = 0;i < mfSysFeeItemList.size();i++) {
                        MfSysFeeItem mfSysFeeItem1 = gson.fromJson(gson.toJson(mfSysFeeItemList.get(i)),MfSysFeeItem.class) ;
                        MfBusAppFee mfBusAppFee = new MfBusAppFee();
                        mfBusAppFee.setAppId(liftId);
                        mfBusAppFee.setFeeStdNo(kindNo);
                        mfBusAppFee.setItemNo(mfSysFeeItem1.getItemNo());
                        mfBusAppFee = mfBusAppFeeFeign.getById(mfBusAppFee);
                        if(mfBusAppFee != null){
                            mfSysFeeItem1.setRateScale(mfBusAppFee.getRateScale());
                            mfSysFeeItem1.setReceivableFeeAmt(mfBusAppFee.getReceivableFeeAmt());
                        }
                        mfSysFeeItemList1.add(mfSysFeeItem1);
                    }
                    feeHtmlStr = jtu.getJsonStr(tableId, "tableTag", mfSysFeeItemList1, null, true);
                }else{
                    feeHtmlStr = jtu.getJsonStr(tableId, "tableTag", mfSysFeeItemList, null, true);
                }
            }
            dataMap.put("feeHtmlStr", feeHtmlStr);
        }
        dataMap.put("feeShowFlag", feeShowFlag);
        dataMap.put("flag", "success");
        dataMap.put("htmlStr", htmlStr);
        dataMap.put("busModel", busModel);
        dataMap.put("cmpdRateType", cmpdRateType);
        dataMap.put("firstKindNo", kindNo);
        Map<String, Object> map = creditApplyInterfaceFeign.getByCusNoAndKindNo(cusNo, kindNo);
        dataMap.putAll(map);

        //额度测算表单
        if("1".equals(mfSysKind.getIfCreditCalc())){
            formId = prdctInterfaceFeign.getFormId(mfSysKind.getKindNo(), WKF_NODE.quota_calc, null, null, User.getRegNo(request));
            FormData formQuotaCalc = formService.getFormData(formId);
            getObjValue(formQuotaCalc, mfCusCustomer);
            this.changeFormProperty(formQuotaCalc, "creditSum", "initValue", MathExtend.moneyStr(mfBusApply.getAppAmt()).replaceAll(",", ""));
            this.changeFormProperty(formQuotaCalc, "kindNo", "initValue", mfSysKind.getKindNo());
            String formQuotaCalcStr = jsonFormUtil.getJsonStr(formQuotaCalc, "bootstarpTag", "");
            dataMap.put("formQuotaCalcStr", formQuotaCalcStr);
            dataMap.put("ifQuotaCalc", mfSysKind.getIfCreditCalc());
        }
        return dataMap;
    }

    @RequestMapping("/getFincRateAjax")
    @ResponseBody
    public Map<String, Object> getFincRateAjax(String rateType, Double nominalRate, Double appAmt, Integer term) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppAmt(appAmt);
        mfBusApply.setTerm(term);
        mfBusApply.setRateType(rateType);
        dataMap = mfBusApplyFeign.getFincRateByNominalRate(mfBusApply);
        return dataMap;
    }

    /**
     * 获取列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBusinessListPage")
    public String getBusinessListPage(Model model, String score, String flag) throws Exception {
        ActionContext.initialize(request, response);
        if ("0".equals(flag)) {
            model.addAttribute("regDate", DateUtil.addByDay(-1));
        } else if ("1".equals(flag)) {
            model.addAttribute("regDate", DateUtil.getMinMonthDate(DateUtil.getDate(), "yyyyMMdd"));
        }else {
        }
        model.addAttribute("score", score);
        return "/component/app/MfBusApply_BusinessList";
    }

    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/findBusinessByPageAjax")
    public Map<String, Object> findBusinessByPageAjax(String score, String regDate, String ajaxData, Integer pageNo, Integer pageSize, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            mfBusApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusApply.setCriteriaList(mfBusApply, ajaxData);// 我的筛选
            mfBusApply.setCustomSorts(ajaxData);// 自定义排序
            if (StringUtil.isNotBlank(score) && StringUtil.isNotBlank(regDate)) {
                mfBusApply.setScore(score);
                mfBusApply.setRegDate(regDate);
                //只要fincsts有值,就回去查询51业务的未解保数据
                mfBusApply.setFincSts("5");
            }
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("mfBusApply", mfBusApply));
            // 自定义查询Bo方法
            ipage = mfBusApplyFeign.findBusinessByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }

    /**
     * 方法实现说明  复议申请初始化页面
     *
     * @param
     * @return
     * @throws
     * @author zkq
     * @date 2018/11/7 11:39
     */
    @RequestMapping(value = "/reconsiderInput")
    public String inputReconsiderQuery(Model model, String ajaxData, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        mfBusApply = mfLoanApplyFeign.processDataForApply(mfBusApply);
        String formId = "mfBusReconsiderApply001";
        FormData formapply0007_query = formService.getFormData(formId);
        getObjValue(formapply0007_query, mfBusApply);
        //产品信息
        String kindNo = mfBusApply.getKindNo();
        MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
        //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateType = mfBusApply.getRateType();

        String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
        this.changeFormProperty(formapply0007_query, "fincRate", "unit", rateUnit);

        //处理还款方式
        List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
        this.changeFormProperty(formapply0007_query, "repayType", "optionArray", repayTypeList);
        this.changeFormProperty(formapply0007_query, "repayTypeNew", "optionArray", repayTypeList);
        //处理金额，double大于十位会变成科学计数法11111111
        DecimalFormat df = new DecimalFormat(",##0.00");
        if ((mfSysKind.getMinAmt() != null) && (mfSysKind.getMaxAmt() != null)) {
            double minAmtD = mfSysKind.getMinAmt();
            double maxAmtD = mfSysKind.getMaxAmt();
            String minAmt = df.format(minAmtD);//融资金额下限
            String maxAmt = df.format(maxAmtD);//融资金额上限
            String amt = minAmt + "-" + maxAmt + "元";
            this.changeFormProperty(formapply0007_query, "appAmtNew", "alt", amt);
            model.addAttribute("minAmt", String.valueOf(minAmtD));
            model.addAttribute("maxAmt", String.valueOf(maxAmtD));
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
            model.addAttribute("minTerm", String.valueOf(minTerm));
            model.addAttribute("maxTerm", String.valueOf(maxTerm));
            model.addAttribute("termType", termType);
            this.changeFormProperty(formapply0007_query, "termNew", "alt", term);
        }
        model.addAttribute("formapply0007_query", formapply0007_query);
        model.addAttribute("kindNo", kindNo);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        model.addAttribute("appId", appId);
        return "/component/app/MfBusReconsiderApply_Input";
    }

    /*方法实现说明  得到其他担保方式集合
     * @author   zkq
     * @param
     * @return
     * @exception
     * @date        2018/12/10 11:34
     */
    private List<ParmDic> getVouTypeOtherSelectByNoAjax(MfSysKind mfSysKind) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<ParmDic> vouTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
        String vouType = "";
        vouType = mfSysKind.getVouType();
        String vouTypeArr[] = vouType.split("\\|");
        Map<String, String> strMap = new HashMap<String, String>();
        if (vouTypeArr.length > 0) {
            for (String str : vouTypeArr) {
                strMap.put(str, str);
            }
        }
        String vouTypeOwner = mfSysKind.getVouTypeDef();
        List<ParmDic> vouTypeResultList = new ArrayList<>();
        for (ParmDic dic : vouTypeList) {
            if (mfSysKind != null && strMap.get(dic.getOptCode()) == null) {
                continue;
            }
            if ("1".equals(dic.getOptCode())) {
                continue;
            }
            vouTypeResultList.add(dic);
        }
        return vouTypeResultList;
    }

    /*20190319yxl新增 借新还旧申请 新增 收费确认的节点*/
    @RequestMapping("/chargConfirmation")
    public String chargConfirmation(String appId, Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        // 功能节点编号
        //String nodeNo = WKF_NODE.contract_sign.getNodeNo();
        //Map<String, Object> dataMap = new HashMap<String, Object>();
        //根据申请id获得申请实体
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        //获得合同信息实体
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(mfBusApply.getPactId());
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        //计算利息 罚息 手续费
        String yuqiLiXi="0.00";//逾期利息
        String fuLiLiXi="0.00";//复利利息
        String weiYueJin="0.00";//违约金
        String shouXuFei="0.00";//手续费 ，对应mfRepayAmt里边的费用feiYong
        String lixi="0.00";
        String dangqianqihao="0";

        Map<String,String> parmMap=new HashMap<>();
        parmMap.put("fincId",mfBusApply.getFincIdOld());
        List<MfRepayAmt> mfRepayAmts=calcRepaymentInterfaceFeign.getCurTermYingShouAmtList(parmMap);
        //计算到当前期的费用合计
        for(MfRepayAmt mfRepayAmt1 : mfRepayAmts){
            if(("1").equals(mfRepayAmt1.getHeJiFlag())){
                yuqiLiXi= MathExtend.add(yuqiLiXi,mfRepayAmt1.getYuQiLiXi());
                fuLiLiXi= MathExtend.add(fuLiLiXi,mfRepayAmt1.getFuLiLiXi());
                weiYueJin= MathExtend.add(weiYueJin,mfRepayAmt1.getWeiYueJin());
                shouXuFei= MathExtend.add(shouXuFei,mfRepayAmt1.getFeiYong());
                lixi= MathExtend.add(lixi,mfRepayAmt1.getLiXi());
                dangqianqihao=mfRepayAmt1.getQiHao();
            }
        }
        //若果截止当前是第一期，利息为零，还款时候会少第一期的利息。
        //计算剩下期的本金和利息
        Map<String, String> mapParm=new HashMap<>();
        mapParm.put("fincId",mfBusApply.getFincIdOld());
        mapParm.put("termNum",dangqianqihao);
        List<MfRepayPlan> repayPlans= mfRepayPlanFeign.getPlanListByQihaoAndFincId(mapParm);
        if(repayPlans.size()!=0){
            for(MfRepayPlan mfRepayPlan : repayPlans){
                lixi= MathExtend.add(lixi,mfRepayPlan.getRepayIntst().toString());
            }
        }

        //手续费
        double feiYong = Double.valueOf(shouXuFei);
        //罚息=逾期利息+复利利息+违约金
        double faXi = Double.valueOf(yuqiLiXi) + Double.valueOf(fuLiLiXi) + Double.valueOf(weiYueJin);
        //利息
        double liXi = Double.valueOf(lixi);
        //将利息 罚息 手续费封装到借据实体中
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setLiXi1(liXi);
        mfBusFincApp.setFaXi1(faXi);
        mfBusFincApp.setFeiYong1(feiYong);
        mfBusFincApp.setFincId(mfBusApply.getFincIdOld());
        String formId = "applyChargConfirmation";
        FormData formapplyChargConfirmation = formService.getFormData(formId);
        /*利率展示处理 --START*/
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        mfBusFincApp.setFincRate(mfBusPact.getFincRate());
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formapplyChargConfirmation, "fincRate", "unit", rateUnit);
        /*利率展示处理 --END*/
        getObjValue(formapplyChargConfirmation, mfBusPact);
        getObjValue(formapplyChargConfirmation, mfBusApply);
        getObjValue(formapplyChargConfirmation, mfBusFincApp);
        model.addAttribute("query", "");
        model.addAttribute("formapplyChargConfirmation", formapplyChargConfirmation);
        return "/component/app/chargConfirmation";
    }

    //收费确认异步保存
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/verifyChargConfirmation")
    @ResponseBody
    public Map<String, Object> verifyChargConfirmation(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);//初始化
        Map<String, Object> dataMap = new HashMap<>();
        FormService formService = new FormService();
        String formId = "applyChargConfirmation";
        FormData formapplyChargConfirmation = formService.getFormData(formId);
        getFormValue(formapplyChargConfirmation, getMapByJson(ajaxData));
        if (this.validateFormData(formapplyChargConfirmation)) {
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            setObjValue(formapplyChargConfirmation, mfBusFincApp);
            //判断传过来的 罚息，利息，手续费是否分别大于原先旧借据的金额
            Double interest = mfBusFincApp.getInterest1();//人工输入的利息
            Double penalty = mfBusFincApp.getPenalty1();//人工输入的罚息
            Double fee = mfBusFincApp.getFee1();//人工输入的手续费

            Double liXi = mfBusFincApp.getLiXi1();//上一笔借据利息
            Double faXi = mfBusFincApp.getFaXi1();//上一笔借据罚息
            Double feiYong = mfBusFincApp.getFeiYong1();//上一笔借据手续费
            if (interest < liXi || penalty < faXi || fee < feiYong) {
                dataMap.put("flag", "error");
                dataMap.put("msg", "该笔贷款尚未结清利息、罚息、手续费，不支持借新还旧的放款确认!");
            } else {
                //收费确认验证通过，将收费信息保存至老的借据信息中
                //此处新建一个mfBusFincAppOld为了与mfbusfincapp区别，
                // 此处只是保存收费确认人工录入的信息以及截止收费确认时借据的利息等费用
                /*20190403 yxl收费确认相关信息保存至申请-借据关系表 --START*/
                MfBusApplyFincJxhj mfBusApplyFincJxhj=new MfBusApplyFincJxhj();
                //本接口修改mfBusApplyFincJxhj是根据appId修改的
                mfBusApplyFincJxhj.setAppId(mfBusFincApp.getAppId());
                mfBusApplyFincJxhj.setFaxi1(mfBusFincApp.getFaXi1());
                mfBusApplyFincJxhj.setLixi1(mfBusFincApp.getLiXi1());
                mfBusApplyFincJxhj.setFeiyong1(mfBusFincApp.getFeiYong1());
                mfBusApplyFincJxhj.setInterest1(mfBusFincApp.getInterest1());
                mfBusApplyFincJxhj.setFee1(mfBusFincApp.getFee1());
                mfBusApplyFincJxhj.setPenalty1(mfBusFincApp.getPenalty1());
                //收费确认时收费结余金额
                mfBusApplyFincJxhj.setConfirmBalance(interest+penalty+fee-liXi-faXi-feiYong);
                mfBusApplyFincJxhj.setConfirmChargDate(DateUtil.getDate());
                mfBusApplyFincJxhj.setLastUpdateTime(DateUtil.getDate());
                mfBusApplyFincJxhjFeign.update(mfBusApplyFincJxhj);
                /*20190403 yxl收费确认相关信息保存至申请-借据关系表 --END*/
                //推动业务进入下一个环节
                MfBusPact mfBusPact = new MfBusPact();
                mfBusPact.setPactId(mfBusFincApp.getPactId());
                mfBusPact = mfBusPactFeign.getById(mfBusPact);
                dataMap = mfBusApplyFeign.doChargConfirmation(mfBusPact);
                //保存成功后重载页面
                String detailFormId = "pact0004";
                MfBusPact mfBusPactTmp = new MfBusPact();
                mfBusPactTmp.setPactId(mfBusPact.getPactId());
                mfBusPactTmp = mfBusPactFeign.getById(mfBusPactTmp);
                mfBusPactTmp = mfBusPactFeign.processDataForPact(mfBusPactTmp);// 处理合同申请利率展示单位
                if (BizPubParm.BUS_MODEL_5.equals(mfBusPactTmp.getBusModel())) {// 应收账款融资(保理业务)
                    detailFormId = "pact0004bl";
                }
                FormData formpact0008 = formService.getFormData(formId);
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
                String htmlStr = jsonFormUtil.getJsonStr(formpact0008, "propertySeeTag", "");
                dataMap.put("pactDetailInfo", htmlStr);
                dataMap.put("pactSts", mfBusPactTmp.getPactSts());
                //dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());

            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", this.getFormulavaliErrorMsg());
        }

        ActionContext.initialize(request, response);
        Map<String, Object> resultMap = null;

        return dataMap;
    }
    /*20190402yxl ajax查询借新还旧关联旧借据 findFincOldAjax*/
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findFincOldAjax")
    @ResponseBody
    public Map<String, Object> findFincOldAjax(Ipage ipage, String tableId, String tableType,String appId) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply=new MfBusApply();
        MfBusFincApp mfBusFincAppOld=new MfBusFincApp();
        try {
            //当前申请信息
            mfBusApply.setAppId(appId);
            mfBusApply=mfBusApplyFeign.getById(mfBusApply);

            JsonTableUtil jtu = new JsonTableUtil();
            //如果是借新还旧业务则查询旧借据
            if ("3".equals((mfBusApply.getLoanKind()))){
                //关联旧借据
                MfBusApplyFincJxhj mfBusApplyFincJxhj=new MfBusApplyFincJxhj();
                mfBusApplyFincJxhj.setAppId(appId);
                mfBusApplyFincJxhj=mfBusApplyFincJxhjFeign.getById(mfBusApplyFincJxhj);
                mfBusFincAppOld.setFincId(mfBusApplyFincJxhj.getFincOldId());
                ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincAppOld));
                ipage=mfBusFincAppFeign.findByPage(ipage);
                String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
                dataMap.put("tableData",tableHtml);
            }else{
                dataMap.put("tableData","");
            }
            //自定义查询Bo方法


        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * @描述 表单利率类型发生变化时，利率范围随之改变
     * @参数 [kindNo, rateType]
     * @返回值 java.util.Map<java.lang.String,java.lang.Object>
     * @创建人  shenhaobing
     * @创建时间 2019/9/16
     * @修改人和其它信息
     */
    @RequestMapping(value = "/getFincRateRangeAjax")
    @ResponseBody
    public Map<String, Object> getFincRateRangeAjax(String kindNo,String rateType) throws Exception{
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
            String rateUnit = "";
            dataMap.put("fincRateRange", "");
            //利率范围
            if((mfSysKind.getMinFincRate()!=null)&&(mfSysKind.getMaxFincRate()!=null)){
                double minFincRateD = MathExtend.showRateMethod(rateType,mfSysKind.getMinFincRate(),Integer.parseInt(mfSysKind.getYearDays()),Integer.parseInt(mfSysKind.getRateDecimalDigits()));
                double maxFincRateD = MathExtend.showRateMethod(rateType,mfSysKind.getMaxFincRate(),Integer.parseInt(mfSysKind.getYearDays()),Integer.parseInt(mfSysKind.getRateDecimalDigits()));
                if(minFincRateD!=0.00 || maxFincRateD!=0.00){//利率范围在最大值最小值都是0的时候，前端表单不做申请期限的限制
                    String minFincRate = String.valueOf(minFincRateD);//融资利率下限
                    String maxFincRate = String.valueOf(maxFincRateD);//融资利率上限
                    //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
                    Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
                    rateUnit= rateTypeMap.get(rateType).getRemark();
                    String fincRateRange = minFincRate+"-"+maxFincRate+rateUnit;
                    dataMap.put("fincRateRange", fincRateRange);
                    dataMap.put("minFincRate", minFincRate);
                    dataMap.put("maxFincRate", maxFincRate);
                    dataMap.put("fincRate", MathExtend.showRateMethod(rateType, mfSysKind.getFincRate(), Integer.parseInt(mfSysKind.getYearDays()),Integer.parseInt(mfSysKind.getRateDecimalDigits())));
                }
            }
            dataMap.put("flag", "success");
        } catch(Exception e){
            dataMap.put("flag", "error");
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
            MfBusApply mfBusApplyTemp = new MfBusApply();
            if(StringUtil.isNotEmpty(appId) && !appId.equals("undefined")){
                mfBusApplyTemp.setAppId(appId);
                mfBusApplyTemp = mfBusApplyFeign.getById(mfBusApplyTemp);
                coborrName = mfBusApplyTemp.getCoborrName();
                coborrNum = mfBusApplyTemp.getCoborrNum();
                coborrNo = mfBusApplyTemp.getCoborrNo();
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
                String tableHtml = jtu.getJsonStr("tablecoborrNameList", "tableTag", (List) ipage.getResult(), ipage, true);
                dataMap.put("tableData", tableHtml);
                dataMap.put("coborrNumNew", coborrNumNew);
                dataMap.put("coborrNameNew", coborrNameNew);
                dataMap.put("coborrNoNew", coborrNoNew);
                if(StringUtil.isNotEmpty(appId)){
                    MfBusApply mfBusApply =  new MfBusApply();
                    mfBusApply.setAppId(mfBusApplyTemp.getAppId());
                    mfBusApply.setCoborrNo(coborrNoNew);
                    mfBusApply.setCoborrName(coborrNameNew);
                    mfBusApply.setCoborrNum(coborrNumNew);
                    mfBusApplyFeign.update(mfBusApply);
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
            String sign = "";
            if(StringUtil.isNotEmpty(appId)){
                MfBusApply mfBusApply =  new MfBusApply();
                mfBusApply.setAppId(appId);
                mfBusApply =  mfBusApplyFeign.getById(mfBusApply);
                cusNos=mfBusApply.getCoborrNo();
                sign="appId";
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
                String tableHtml = jtu.getJsonStr("tablecoborrNameList", "tableTag", (List) ipage.getResult(), ipage, true);
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
                dataMap.put("sign",sign);
                if(StringUtil.isNotEmpty(appId)){
                    MfBusApply mfBusApply =  new MfBusApply();
                    mfBusApply.setAppId(appId);
                    mfBusApply.setCoborrNo(cusNoss);
                    mfBusApply.setCoborrName(coborrName);
                    mfBusApply.setCoborrNum(idNums);
                    mfBusApplyFeign.update(mfBusApply);
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
     * 打开共借人列表，社会关系和客户
     * @param model
     * @throws Exception
     */
    @RequestMapping(value = "/getListPageForSelect")
    public String getListPageForSelect(Model model,String cusNo,String cusType,String appId) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo",cusNo);
        model.addAttribute("cusType",cusType);
        return "/component/cus/MfCusCoborrNum_ListForSelect";
    }


    @RequestMapping(value = "/getMeetingPlanListPage")
    public String getMeetingPlanListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/app/MfBusApply_MeetingPlanList";
    }

    @RequestMapping("/findByPageMeetingPlanAjax")
    @ResponseBody
    public Map<String, Object> findByPageMeetingPlanAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                                         String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            ajaxData = ajaxData.replaceAll("-","");
            mfBusApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusApply.setCriteriaList(mfBusApply, ajaxData);// 我的筛选
            mfBusApply.setCustomSorts(ajaxData);// 自定义排序
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
            ipage.setParams(this.setIpageParams("mfBusApply", mfBusApply));
            ipage = mfBusApplyFeign.findByPageMeetingPlan(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    @SuppressWarnings("rawtypes")
    @RequestMapping("/findCoborrNo")
    @ResponseBody
    public Map<String, Object> findCoborrNo(String appId) throws Exception {
        Map<String , Object> dataMap = new HashMap<>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            MfBusApply mfBusApplyTmp = mfBusApplyFeign.getById(mfBusApply);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            Ipage ipage = this.getIpage();
            if(mfBusApplyTmp.getCoborrNo()!=null){
                mfCusCustomer.setCusNo(mfBusApplyTmp.getCoborrNo());
                ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
                ipage =  mfCusCustomerFeign.getCusListByCusNos(ipage);
                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr("tablecoborrNameList", "tableTag", (List) ipage.getResult(), ipage, true);
                dataMap.put("tableHtml",tableHtml);
                dataMap.put("flag","success");
            }else{
                if(mfBusApplyTmp.getCoborrNum()!=null){
                    mfCusCustomer.setIdNum(mfBusApplyTmp.getCoborrNum());
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
    @SuppressWarnings("rawtypes")
    @RequestMapping("/getMeetingTimeBusAjax")
    @ResponseBody
    public Map<String, Object> getMeetingTimeBusAjax(String meetingTime) throws Exception {
        Map<String , Object> dataMap = new HashMap<>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setMeetingTime(meetingTime);
            List<MfBusApply> list = mfBusApplyFeign.getMeetingTimeBus(mfBusApply);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr("tableMakingMeetingSummaryBusList", "tableTag", list, null, true);
            dataMap.put("tableHtml",tableHtml);
            dataMap.put("flag","success");
        }catch (Exception e){
            dataMap.put("flag","error");
            dataMap.put("msg","根据上会时间查询业务信息失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * 查询共借人后列表不可操作
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getCocoborrByCusNo")
    @ResponseBody
    public Map<String, Object> getCocoborrByCusNo(String cusNos) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNos);
            Ipage ipage = this.getIpage();
            ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
            ipage =  mfCusCustomerFeign.getCusListByCusNos(ipage);
            List<Map>  cusList =  (List)ipage.getResult();
            if(cusList.size()>=0){
                String idNums = "";
                for(int i=0;i<cusList.size();i++){
                    if(StringUtil.isEmpty((String)cusList.get(i).get("idNum"))){
                        dataMap.put("flag", "error");
                        dataMap.put("msg", "所选客户有证件号码未完善，请重新选择");
                        dataMap.put("cusNo", cusList.get(i).get("cusNo"));
                        return dataMap;
                    }else{
                        idNums+=cusList.get(i).get("idNum");
                        if(i<cusList.size()-1){
                            idNums+="|";
                        }
                    }
                }
                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr("tablemfCusBorrowerList", "tableTag", (List) ipage.getResult(), ipage, true);
                dataMap.put("tableData",tableHtml);
                dataMap.put("flag","success");
                dataMap.put("idNums",idNums);
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", "没有共借人");
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "共同借款人展示错误");
            throw e;
        }

        return dataMap;
    }


    /**
     * @param ajaxData
     * @param term
     * @param termType
     * @param kindNo
     * @param fincRate
     * @param baseRateType
     * @return
     * @throws Exception
     * @desc 业务申请界面处理  基础利率以及浮动点数及百分比（输入期限或者修改期限时使用）
     * @author zkq
     */
    @RequestMapping(value = "/getCalcRateByTermAjax")
    @ResponseBody
    public Map<String, Object> getCalcRateByTermAjax(String ajaxData, String rateType, String term, String termType, String kindNo, String fincRate, String baseRateType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> parmMap = new HashMap<>();
        try {
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind.setKindNo(kindNo);
            mfSysKind = mfSysKindFeign.getById(mfSysKind);
            if ("4".equals(baseRateType)) {
                parmMap.put("term", term);
                parmMap.put("termType", termType);
                parmMap.put("yearDays", mfSysKind.getYearDays());
                dataMap = mfBusApplyFeign.getLprRate(parmMap);
                if ("success".equals(dataMap.get("flag")) && !"".equals(fincRate)) {
                    fincRate = MathExtend.saveYearRateMethod(rateType, fincRate, mfSysKind.getYearDays());
                    String baseRate = String.valueOf(dataMap.get("baseRate"));
                    String floatNumber = MathExtend.subtract(fincRate, baseRate);
                    floatNumber = MathExtend.multiply(floatNumber, "100");
                    dataMap.put("floatNumber", floatNumber);
                    dataMap.put("rateNo", dataMap.get("rateNo"));
                }
            } else {
                parmMap.put("term", term);
                parmMap.put("termType", termType);
                parmMap.put("baseRateType", baseRateType);
                dataMap = mfBusApplyFeign.getMfSysRate(parmMap);
                if ("success".equals(dataMap.get("flag")) && !"".equals(fincRate)) {
                    fincRate = MathExtend.saveYearRateMethod(rateType, fincRate, mfSysKind.getYearDays());
                    String baseRate = String.valueOf(dataMap.get("baseRate"));
                    String fincRateFloat = MathExtend.multiply(fincRate, "100");
                    fincRateFloat = MathExtend.divide(fincRateFloat, baseRate, Integer.valueOf(mfSysKind.getReturnPlanPoint()));
                    dataMap.put("fincRateFloat", fincRateFloat);
                }
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }
        return dataMap;
    }


    /**
     * @param ajaxData
     * @param
     * @param kindNo
     * @param fincRate
     * @param baseRateType
     * @return
     * @throws Exception
     * @desc 业务申请界面处理以及浮动点数及百分比（输入执行利率或修改逾期利率时使用）
     * @author zkq
     */
    @RequestMapping(value = "/getCalcRateByFincRateAjax")
    @ResponseBody
    public Map<String, Object> getCalcRateByFincRateAjax(String ajaxData, String rateType, String kindNo, String baseRate, String fincRate, String baseRateType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> parmMap = new HashMap<>();
        try {
            MfSysKind mfSysKind = new MfSysKind();
            mfSysKind.setKindNo(kindNo);
            mfSysKind = mfSysKindFeign.getById(mfSysKind);
            if ("4".equals(baseRateType)) {
                fincRate = MathExtend.saveYearRateMethod(rateType, fincRate, mfSysKind.getYearDays());
                String floatNumber = MathExtend.subtract(fincRate, baseRate);
                floatNumber = MathExtend.multiply(floatNumber, "100");
                dataMap.put("floatNumber", floatNumber);
            } else {
                fincRate = MathExtend.saveYearRateMethod(rateType, fincRate, mfSysKind.getYearDays());
                String fincRateFloat = MathExtend.multiply(fincRate, "100");
                fincRateFloat = MathExtend.divide(fincRateFloat, baseRate, Integer.valueOf(mfSysKind.getReturnPlanPoint()));
                dataMap.put("fincRateFloat", fincRateFloat);
            }
            dataMap.put("flag", "success");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }
        return dataMap;
    }
    /**
     *
     * 方法描述： 系统中客户经理列表（阳光银行）
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2019-1-9 下午3:59:13
     */
    @RequestMapping(value = "/getCusManageListPage")
    public String getCusManageListPage(Model model) throws Exception{
        ActionContext.initialize(request, response);
        return "/component/app/MfBusApply_cusManageList";
    }

    /**
     *
     * 方法描述： 获取系统中客户经理列表，以客户为主体，没有业务的仅展示客户信息，有业务的展示业务信息（阳光银行）
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2019-1-9 下午3:26:17
     */
    @RequestMapping(value = "/findCusManageListByPageAjax")
    @ResponseBody
    public Map<String, Object> findCusManageListByPageAjax(Ipage ipage,String ajaxData,Integer pageNo, Integer pageSize, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            mfCusAndApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusAndApply.setCustomSorts(ajaxData);//自定义排序参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);//我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
            //自定义查询Bo方法
            ipage = mfBusApplyFeign.findCusManageListByPage(ipage);
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
     * @描述 客户经理查询
     * @参数 [model, ajaxData]
     * @返回值 java.lang.String
     * @创建人  shenhaobing
     * @创建时间 2019-10-29
     * @修改人和其它信息
     */
    @RequestMapping(value = "/inputCusManage")
    public String inputCusManage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("cusMngNo",User.getRegNo(request));
        dataMap.put("cusMngName",User.getRegName(request));
        String formId = "inputCusManage";
        FormData forminputCusManage = formService.getFormData(formId);
        getFormValue(forminputCusManage);
        getObjValue(forminputCusManage, dataMap);
        model.addAttribute("forminputCusManage", forminputCusManage);
        model.addAttribute("query", "");
        return "/component/app/MfInputCusManage";
    }

    /**
     * @描述
     * @参数 [ipage, ajaxData, pageNo, pageSize, tableId, tableType]
     * @返回值 java.util.Map<java.lang.String,java.lang.Object>
     * @创建人  shenhaobing
     * @创建时间 2019-10-29
     * @修改人和其它信息
     */
    @RequestMapping(value = "/inputCusManageAjax")
    @ResponseBody
    public Map<String, Object> inputCusManageAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            dataMap = getMapByJson(ajaxData);
            FormService formService = new FormService();
            FormData forminputCusManage = formService.getFormData("inputCusManage");
            getFormValue(forminputCusManage, dataMap);
            if (this.validateFormDataAnchor(forminputCusManage)) {
                String queryType = (String) dataMap.get("queryType");
                List<MfCusAndApply> mfCusAndApplyList = null;
                List<MfCusCustomer> mfCusCustomerList = null;
                String tableId = "tablecusmanagecusquerylist";
                String  cusTableHtml = null;
                String  tableHtml = null;
                JsonTableUtil jtu = new JsonTableUtil();
                if ("1".equals(queryType)){//客户查询
                    tableId = "tablecusmanagequerylist";
                    mfCusAndApply.setCusName((String) dataMap.get("cusName"));
                    Ipage ipage = this.getIpage();
                    ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
                    mfCusAndApplyList = (List<MfCusAndApply>) mfBusApplyFeign.findCusManageListByPage(ipage).getResult();
                    if(mfCusAndApplyList!=null&&mfCusAndApplyList.size()>0){
                        tableHtml = jtu.getJsonStr(tableId,"tableTag",mfCusAndApplyList, null,true);
                    }
                }else if("2".equals(queryType)){//客户经理查询
                    tableId = "tablecusmanagecusquerylist";
                    MfCusCustomer mfCusCustomer = new MfCusCustomer();
                    mfCusCustomer.setCusMngNo((String) dataMap.get("cusMngNo"));
                    mfCusCustomerList = cusInterfaceFeign.getAllCusList(mfCusCustomer);
                    if(mfCusCustomerList!=null&&mfCusCustomerList.size()>0){
                        cusTableHtml = jtu.getJsonStr(tableId,"tableTag",mfCusCustomerList, null,true);
                    }

                    mfCusAndApply.setManageOpNo1((String) dataMap.get("cusMngNo"));
                    tableId = "tablecusmanagequerylist";
                    Ipage ipage = this.getIpage();
                    ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
                    mfCusAndApplyList = (List<MfCusAndApply>) mfBusApplyFeign.findCusManageListByPage(ipage).getResult();
                    if(mfCusAndApplyList!=null&&mfCusAndApplyList.size()>0){
                        tableHtml = jtu.getJsonStr(tableId,"tableTag",mfCusAndApplyList, null,true);
                    }
                }else {
                }
                if (tableHtml == null && cusTableHtml == null){
                    dataMap.put("flag","error");
                }else{
                    if (tableHtml != null){
                        dataMap.put("tableHtml",tableHtml);
                    }
                    if (cusTableHtml != null){
                        dataMap.put("cusTableHtml",cusTableHtml);
                    }
                    dataMap.put("flag","success");
                }
            }else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @ResponseBody
    @RequestMapping(value = "/getApplyChange")
    public Map<String,Object> getApplyChange(String appId) throws  Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApplyOld = new MfBusApply();
            mfBusApplyOld.setAppId(appId);
            mfBusApplyOld = mfBusApplyFeign.getById(mfBusApplyOld);
            String busModel = mfBusApplyOld.getBusModel();
            String kindNo = mfBusApplyOld.getKindNo();

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
                List<MfSysFeeItem> mfSysFeeItemList = (List<MfSysFeeItem>) (List<MfSysFeeItem>) calcInterfaceFeign.findFeeByPage(ipage).getResult();
                String feeHtmlStr = "";
                String tableId = "tableapplynodefeelist";
                if(BizPubParm.BUS_MODEL_12.equals(busModel)){
                    tableId = "tableapplynodefeeguaranteelist";
                }
                if (mfSysFeeItemList != null && mfSysFeeItemList.size() > 0) {
                    feeHtmlStr = jtu.getJsonStr(tableId, "tableTag", mfSysFeeItemList, null, true);
                }
                dataMap.put("feeHtmlStr", feeHtmlStr);
            }

            String jsonArrayStr = prdctInterfaceFeign.getFincUse(kindNo);
            CodeUtils cu = new CodeUtils();
            Map<String, String> tradeMap = cu.getMapByKeyName("TRADE");
            String fincUseDes = tradeMap.get(mfBusApplyOld.getFincUse());
            mfBusApplyOld.setFincUseDes(fincUseDes);

            dataMap.put("feeShowFlag", feeShowFlag);
            dataMap.put("flag", "success");
            dataMap.put("busModel", busModel);
            dataMap.put("mfBusApply", mfBusApplyOld);

        } catch (Exception e) {
            dataMap.put("flag", "error");
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述：查询保前数据：业务审批完成后缴费之前
     * @param ipage
     * @param pageNo
     * @param pageSize
     * @param tableId
     * @param tableType
     * @param ajaxData
     * @return
     * @throws Exception Map<String,Object>
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findLoanFrontByPageAjax")
    @ResponseBody
    public Map<String, Object> findLoanFrontByPageAjax(Ipage ipage, Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusApply mfBusApply = new MfBusApply();
        try {
            mfBusApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfBusApply.setCustomSorts(ajaxData);//自定义排序参数赋值
            mfBusApply.setCriteriaList(mfBusApply, ajaxData);//我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusApply", mfBusApply));
            ipage = mfBusApplyFeign.findLoanFrontByPage(ipage);
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
     * 获取合同签约以后的项目--归档申请
     * @param pageNo
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getMfBusApplyListForArchive")
    public Map<String, Object> getMfBusApplyListForArchive(int pageNo,String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusApply.setCustomSorts(ajaxData);
            mfBusApply.setCriteriaList(mfBusApply, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusApply",mfBusApply));
            ipage = mfBusApplyFeign.getMfBusApplyListForArchive(ipage);
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
     * 方法描述： 终止业务前校验是否退费完成
     *
     * @return
     * @throws Exception String
     * @date 2018-2-26 下午8:04:04
     */
    @RequestMapping(value = "/valiAmtRulesAjax")
    @ResponseBody
    public Map<String, Object> valiAmtRulesAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = getMapByJson(ajaxData);
        String appId = (String)dataMap.get("appId");
        resultMap = mfBusApplyFeign.valiAmtRules(appId);
        return resultMap;
    }
    @RequestMapping(value = "/checkIfEval")
    @ResponseBody
    public Map<String, Object> checkIfEval(String relNo,String type) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        boolean flag = true;
        try {
//            flag = mfBusApplyFeign.checkIfEval(relNo,type);
//            if(flag){
                dataMap.put("flag", "success");
//            }else{
//                dataMap.put("flag", "error");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }


}
