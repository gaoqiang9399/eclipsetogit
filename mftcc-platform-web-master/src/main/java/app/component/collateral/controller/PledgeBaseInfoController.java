package app.component.collateral.controller;

import app.base.ServiceException;
import app.base.SpringUtil;
import app.base.User;
import app.component.accnt.entity.MfAccntRepayDetail;
import app.component.accnt.feign.MfAccntRepayDetailFeign;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.collateral.carcheck.entity.MfCarCheckForm;
import app.component.collateral.carcheck.feign.MfCarCheckFormFeign;
import app.component.collateral.entity.*;
import app.component.collateral.feign.*;
import app.component.collateral.maintenance.entity.MfMaintenance;
import app.component.collateral.maintenance.feign.MfMaintenanceFeign;
import app.component.collateral.movable.entity.MfMoveableCheckInventoryInfo;
import app.component.collateral.movable.feign.MfMoveableCheckInventoryInfoFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusGpsReg;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfBusGpsRegFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.importexcel.entity.MfCusImportExcelHis;
import app.component.msgconf.entity.MfMsgPledge;
import app.component.msgconfinterface.MsgConfInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.receaccount.entity.MfBusReceBal;
import app.component.pact.receaccount.entity.MfBusReceBaseInfo;
import app.component.pact.receaccount.feign.MfBusReceBalFeign;
import app.component.pact.receaccount.feign.MfBusReceBaseInfoFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.rules.entity.NumberBigBean;
import app.component.rulesinterface.RulesInterfaceFeign;
import app.component.wkf.entity.Result;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.*;
import cn.mftcc.util.DateUtil;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Title: PledgeBaseInfoAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 21 16:24:12 CST 2017
 **/
@Controller
@RequestMapping("/pledgeBaseInfo")
public class PledgeBaseInfoController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private PledgeBaseInfoFeign pledgeBaseInfoFeign;
    @Autowired
    private RulesInterfaceFeign rulesInterfaceFeign;
    @Autowired
    private MfCollateralTableFeign mfCollateralTableFeign;
    @Autowired
    private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private FairInfoFeign fairInfoFeign;
    @Autowired
    private InsInfoFeign insInfoFeign;
    @Autowired
    private MfBusAppKindFeign mfBusAppKindFeign;
    @Autowired
    private CertiInfoFeign certiInfoFeign;
    @Autowired
    private MfCollateralClassFeign mfCollateralClassFeign;
    // @Autowired
    private PledgeBaseInfoBill pledgeBaseInfoBill;
    @Autowired
    private PledgeBaseInfoBillFeign pledgeBaseInfoBillFeign;
    // @Autowired
    private PledgeBaseInfoHead pledgeBaseInfoHead;
    @Autowired
    private MfBusCollateralRelFeign mfBusCollateralRelFeign;
    @Autowired
    private AppInterfaceFeign appInterfaceFeign;
    @Autowired
    private MsgConfInterfaceFeign msgConfInterfaceFeign;// 信息配置接口注入
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;
    @Autowired
    private MfMoveableCheckInventoryInfoFeign mfMoveableCheckInventoryInfoFeign;
    @Autowired
    private EvalInfoFeign evalInfoFeign;
    @Autowired
    private MfBusGpsRegFeign mfBusGpsRegFeign;
    @Autowired
    private ChkInfoFeign chkInfoFeign;
    @Autowired
    private CollateralInterfaceFeign collateralInterfaceFeign;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private MfCarCheckFormFeign mfCarCheckFormFeign;
    @Autowired
    private MfMaintenanceFeign mfMaintenanceFeign;
    @Autowired
    private MfCollateralInsuranceClaimsFeign mfCollateralInsuranceClaimsFeign;
    @Autowired
    private MfAccntRepayDetailFeign mfAccntRepayDetailFeign;
    @Autowired
    private MfBusReceBalFeign mfBusReceBalFeign;
    @Autowired
    private MfBusReceBaseInfoFeign mfBusReceBaseInfoFeign;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private MfCusCreditContractFeign mfCusCreditContractFeign;

    @Autowired
    private FunGuGuFeign funGuGuFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
    private HSSFWorkbook workbook = null;

    public static Logger log = LoggerFactory.getLogger(PledgeBaseInfoController.class);
    public Gson gson = new Gson();

    /**
     * 获取列表数据
     * f
     *
     * @return
     * @throws Exception
     */

    private void getTableData(Map<String, Object> dataMap, String tableId, PledgeBaseInfo pledgeBaseInfo)
            throws Exception {
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, "tableTag", pledgeBaseInfoFeign.getAll(pledgeBaseInfo), null, true);
        dataMap.put("tableData", tableHtml);
    }

    /**
     * 列表有翻页
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getListPage")
    public String getListPage(Model model, Ipage ipage) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formdlpledgebaseinfo0002 = formService.getFormData("dlpledgebaseinfo0002");
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        ipage.setParams(this.setIpageParams("pledgeBaseInfo", pledgeBaseInfo));
        List<PledgeBaseInfo> pledgeBaseInfoList = (List<PledgeBaseInfo>) pledgeBaseInfoFeign.findByPage(ipage)
                .getResult();
        model.addAttribute("pledgeBaseInfoList", pledgeBaseInfoList);
        model.addAttribute("formdlpledgebaseinfo0002", formdlpledgebaseinfo0002);
        model.addAttribute("query", "");
        return "/component/collateral/PledgeBaseInfo_List";
    }

    /**
     * 列表全部无翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getListAll")
    public String getListAll(Model model) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formdlpledgebaseinfo0002 = formService.getFormData("dlpledgebaseinfo0002");
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        List<PledgeBaseInfo> pledgeBaseInfoList = pledgeBaseInfoFeign.getAll(pledgeBaseInfo);
        model.addAttribute("formdlpledgebaseinfo0002", formdlpledgebaseinfo0002);
        model.addAttribute("pledgeBaseInfoList", pledgeBaseInfoList);
        return "/component/collateral/PledgeBaseInfo_List";
    }

    /**
     * 押品到期列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getPledgeToDatePage")
    public String getPledgeToDatePage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        String pledgeWarning = "0";
        try {
            MfMsgPledge mfMsgPledge = new MfMsgPledge();
            mfMsgPledge.setId("PLEDGE_TO_DATE_WARN");
            mfMsgPledge = msgConfInterfaceFeign.getById(mfMsgPledge);
            if (null != mfMsgPledge && "1".equals(mfMsgPledge.getUseFlag())) {
                pledgeWarning = String.valueOf(mfMsgPledge.getIntervalExpireDays());// 押品到期预警
            }
        } catch (Exception e) {
            // logger.error("获取预警信息出错", e);
        }
        this.getHttpRequest().setAttribute("pledgeWarning", pledgeWarning);
        JSONArray keepStatusJsonArray = new CodeUtils().getJSONArrayByKeyName("KEEP_STATUS");
        this.getHttpRequest().setAttribute("keepStatusJsonArray", keepStatusJsonArray);
        JSONArray scopeJsonArray = new JSONArray();
        String[] array = {"全部", "押品到期"};
        for (int i = 0; i < array.length; i++) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("optName", array[i]);
            jsonObj.put("optCode", String.valueOf(i));
            scopeJsonArray.add(jsonObj);
        }
        this.getHttpRequest().setAttribute("scopeJsonArray", scopeJsonArray);

        model.addAttribute("query", "");
        return "/component/collateral/PledgeToDate_List";

    }

    /**
     * 押品到期列表数据请求
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/getPledgeToDateAjax")
    @ResponseBody
    public Map<String, Object> getPledgeToDateAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                                   String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        @SuppressWarnings("unused")
        String pledgeWarning = request.getParameter("pledgeWarning");// 提前几天预警
        String warningType = request.getParameter("warningType");// 0 押品到期
        String scopeType = "0";
        // 根据预警天数计算---押品结束日期
        String pledgeWarningDate = DateUtil.getDate();
        if (StringUtil.isNotEmpty(pledgeWarningDate)) {
            pledgeWarningDate = DateUtil.addByDay((Integer.parseInt(pledgeWarning)));
        }

        try {
            // 取出ajax数据
            Gson gson = new Gson();
            JSONArray jsonArray = gson.fromJson(ajaxData, JSONArray.class);
            if (jsonArray.get(0) instanceof JSONArray) {
                JSONArray jsonArraySub = jsonArray.getJSONArray(0);
                for (int i = 0; i < jsonArraySub.size(); i++) {
                    JSONObject jsonObj = (JSONObject) jsonArraySub.get(i);
                    if ("scope".equals((String) jsonObj.get("condition"))
                            && StringUtil.isNotEmpty((String) jsonObj.get("value"))) {
                        scopeType = (String) jsonObj.get("value");
                        if ("0".equals(scopeType)) {// 全部
                            // pledgeBaseInfo.setPleExpiryDate(DateUtil.getDate());
                            pledgeBaseInfo.setPleExpiryDate(pledgeWarningDate);
                        } else if ("1".equals(scopeType)) {//// 押品到期
                            pledgeBaseInfo.setPleExpiryDate(pledgeWarningDate);
                        } else {
                            pledgeBaseInfo.setPleExpiryDate(DateUtil.getDate());
                        }
                    }
                }
            } else if (StringUtil.isNotEmpty(warningType)) {// 预警页面跳转过来
                if ("0".equals(warningType)) {// 押品到期
                    pledgeBaseInfo.setPleExpiryDate(pledgeWarningDate);
                } else if ("1".equals(warningType)) {// 押品到期
                    pledgeBaseInfo.setPleExpiryDate(pledgeWarningDate);
                } else {
                    pledgeBaseInfo.setPleExpiryDate(pledgeWarningDate);
                }

            } else {
                // pledgeBaseInfo.setPleExpiryDate(DateUtil.getDate());
                pledgeBaseInfo.setPleExpiryDate(pledgeWarningDate);
            }

            pledgeBaseInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
            pledgeBaseInfo.setCriteriaList(pledgeBaseInfo, ajaxData);// 我的筛选
            pledgeBaseInfo.setCustomSorts(ajaxData);
            // this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("pledgeBaseInfo", pledgeBaseInfo));
            // 自定义查询Feign方法
            ipage = pledgeBaseInfoFeign.getPledgeToDateByPage(ipage);
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
     * 发票号码重复校验
     */
    @RequestMapping("/validateDupExtOstr04Ajax")
    @ResponseBody
    public Map<String, Object> validateDupExtOstr04Ajax(String ajaxData) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (StringUtil.isEmpty(ajaxData)) {
            dataMap.put("result", "1");
        } else {
            String validate = pledgeBaseInfoFeign.validateDupExtOstr04(ajaxData);
            if ("0".equals(validate)) {
                dataMap.put("result", "0");
            } else {
                dataMap.put("result", "1");
            }
        }
        return dataMap;
    }

    /**
     * 订单号码重复校验
     */
    @RequestMapping("/validateDupExtOstr07Ajax")
    @ResponseBody
    public Map<String, Object> validateDupExtOstr07Ajax(String ajaxData) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (StringUtil.isEmpty(ajaxData)) {
            dataMap.put("result", "1");
        } else {
            String validate = pledgeBaseInfoFeign.validateDupExtOstr07(ajaxData);
            if ("0".equals(validate)) {
                dataMap.put("result", "0");
            } else {
                dataMap.put("result", "1");
            }
        }
        return dataMap;
    }

    /**
     * 刷新头部信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/refreshPageHeadAjax")
    @ResponseBody
    public Map<String, Object> refreshPageHeadAjax(String collateralNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(collateralNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        String cusNo = pledgeBaseInfo.getCusNo();
        MfCusCustomer mfcc = new MfCusCustomer();
        mfcc.setCusNo(cusNo);
        @SuppressWarnings("unused")
        MfCusCustomer mfCusCustomer = mfCusCustomerFeign.getById(mfcc);
        CodeUtils cu = new CodeUtils();
        Map<String, String> cusTypeMap = cu.getMapByKeyName("KEEP_STATUS");
        String keepStatusName = cusTypeMap.get(pledgeBaseInfo.getKeepStatus());
        dataMap.put("keepStatusName", keepStatusName);
        pledgeBaseInfoHead = new PledgeBaseInfoHead();
        // 是否是应收账款
        if (BizPubParm.COLLATERAL_TYPE_01.equals(pledgeBaseInfo.getClassModel())) {
            pledgeBaseInfoHead.setHeadFistName("应收账款净值");
            if ("".equals(pledgeBaseInfo.getExtNum01()) || pledgeBaseInfo.getExtNum01() != null) {
                pledgeBaseInfoHead.setHeadFistValue(MathExtend.moneyStr(pledgeBaseInfo.getExtNum01() / 10000));
            }
            pledgeBaseInfoHead.setHeadFistUnit("万");
            pledgeBaseInfoHead.setHeadSecName("到期日");
            pledgeBaseInfoHead.setHeadSecValue(DateUtil.getShowDateTime(pledgeBaseInfo.getExtDstr02()));
            pledgeBaseInfoHead.setHeadSecUnit("");
            pledgeBaseInfoHead.setHeadThirdName("押品类别");
            pledgeBaseInfoHead.setHeadThirdValue(pledgeBaseInfo.getClassSecondName());
        } else {
//            EvalInfo ei = new EvalInfo();
//            ei.setCollateralId(collateralNo);
//            EvalInfo evalInfo = evalInfoFeign.getLatest(ei);
//            if (evalInfo != null) {
//                pledgeBaseInfoHead.setHeadFistName("押品价值");
//                pledgeBaseInfoHead.setHeadFistValue(MathExtend.moneyStr(evalInfo.getConfirmAmount() / 10000));
//                pledgeBaseInfoHead.setHeadFistUnit("万");
//                pledgeBaseInfoHead.setHeadSecName("抵押率");
//                pledgeBaseInfoHead.setHeadSecValue(MathExtend.moneyStr(evalInfo.getMortRate()));
//                pledgeBaseInfoHead.setHeadSecUnit("%");
//                pledgeBaseInfoHead.setHeadThirdName("押品类别");
//                pledgeBaseInfoHead.setHeadThirdValue(pledgeBaseInfo.getClassSecondName());
//                pledgeBaseInfoHead.setHeadThirdUnit("");
//            } else {
//                pledgeBaseInfoHead.setHeadFistName("押品价值");
//                pledgeBaseInfoHead.setHeadFistValue("0.00");
//                pledgeBaseInfoHead.setHeadFistUnit("万");
//                pledgeBaseInfoHead.setHeadSecName("抵押率");
//                pledgeBaseInfoHead.setHeadSecValue("0");
//                pledgeBaseInfoHead.setHeadSecUnit("%");
//                pledgeBaseInfoHead.setHeadThirdName("押品类别");
//                pledgeBaseInfoHead.setHeadThirdValue(pledgeBaseInfo.getClassSecondName());
//                pledgeBaseInfoHead.setHeadThirdUnit("");
//            }
            if (pledgeBaseInfo.getPleValue() != null) {
                pledgeBaseInfoHead.setHeadFistValue(MathExtend.moneyStr(pledgeBaseInfo.getPleValue() / 10000));
            } else {
                pledgeBaseInfoHead.setHeadFistValue("0.00");
            }
            pledgeBaseInfoHead.setHeadFistUnit("万");
            pledgeBaseInfoHead.setHeadSecName("抵押率");
            if (pledgeBaseInfo.getPledgeRate() != null) {
                pledgeBaseInfoHead.setHeadSecValue(MathExtend.moneyStr(pledgeBaseInfo.getPledgeRate()));
            } else {
                pledgeBaseInfoHead.setHeadSecValue("0.00");
            }
            pledgeBaseInfoHead.setHeadSecUnit("%");
            pledgeBaseInfoHead.setHeadThirdName("押品类别");
            pledgeBaseInfoHead.setHeadThirdValue(pledgeBaseInfo.getClassSecondName());
            pledgeBaseInfoHead.setHeadThirdUnit("");
        }
        if (pledgeBaseInfoHead != null) {
            dataMap.put("displayColRate", pledgeBaseInfoHead.getHeadFistValue());
            dataMap.put("displayColValue", pledgeBaseInfoHead.getHeadSecValue());
        }


        return dataMap;
    }

    /**
     * AJAX分页查询
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                              String tableType, String classFirstNo, String assetProperty) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            if (StringUtil.isEmpty(classFirstNo)) {
                classFirstNo = "A,B,C,D";
            }
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setCustomQuery(ajaxData);
            pledgeBaseInfo.setCustomSorts(ajaxData);
            pledgeBaseInfo.setCriteriaList(pledgeBaseInfo, ajaxData);
            pledgeBaseInfo.setClassFirstNoList(Arrays.asList(classFirstNo.split(",")));
            pledgeBaseInfo.setAssetProperty(assetProperty);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("pledgeBaseInfo", pledgeBaseInfo));
            ipage = pledgeBaseInfoFeign.findByPage(ipage);
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
     * AJAX新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData, String caseId, String ifPrivateAssets) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            // JSONObject jb = JSONObject.fromObject(ajaxData);
            // pledgeBaseInfo = (PledgeBaseInfo)JSONObject.toBean(jb,
            // PledgeBaseInfo.class);

            dataMap = getMapByJson(ajaxData);
            // 检查押品展示号重复
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setPledgeShowNo(String.valueOf(dataMap.get("pledgeShowNo")));
            pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
            if (pledgeBaseInfo != null) {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_PLEDGESHOWNO_REPETITION.getMessage());
                return dataMap;
            }
            String classId = (String) dataMap.get("classId");
            MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId + "-1", "PledgeBaseInfoAction", "");
            String formId = "";
            if (mfCollateralFormConfig != null) {
                formId = mfCollateralFormConfig.getAddModelDef();
            }
            FormData formdlpledgebaseinfo0004 = null;
            if (StringUtil.isEmpty(formId)) {
                // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
                // "的PledgeBaseInfoAction表单信息没有查询到");
            } else {
                formdlpledgebaseinfo0004 = formService.getFormData(formId);
                if (formdlpledgebaseinfo0004.getFormId() == null) {
                    formdlpledgebaseinfo0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
                    // logger.error("押品类型为" +
                    // mfCollateralFormConfig.getFormType() +
                    // "的PledgeBaseInfoAction表单form"
                    // + formId + ".xml文件不存在");
                    // log.error("押品类型为"+mfCollateralFormConfig.getFormType()+"的PledgeBaseInfoAction表单form"+formId+".xml文件不存在");
                } else {
                }
            }

            getFormValue(formdlpledgebaseinfo0004, dataMap);
            String pledgeNo = "";
            if (this.validateFormDataAnchor(formdlpledgebaseinfo0004)) {
                pledgeBaseInfo = new PledgeBaseInfo();
                setObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                pledgeNo = WaterIdUtil.getWaterId();
                // 调用规则引擎生成押品编号
                /*
                 * Map<String, Object> dataMap = new HashMap<String, Object>();
                 * dataMap.put("cusNo", pledgeBaseInfo.getCusNo());
                 * dataMap.put("noType", BizPubParm.NO_TYPE_COLLATERAL);//押品编号类型
                 * NumberBigBean numberBigBean =
                 * rulesInterfaceFeign.getNumberBigBean(dataMap);
                 * if(numberBigBean.getResultNo()!=null&&!"".equals(
                 * numberBigBean.getResultNo())){
                 * pledgeNo=numberBigBean.getResultNo(); }
                 */
                MfCollateralClass mfCollateralClass = new MfCollateralClass();
                mfCollateralClass.setClassId(classId);
                mfCollateralClass = mfCollateralClassFeign.getById(mfCollateralClass);
                pledgeBaseInfo.setClassFirstNo(mfCollateralClass.getClassFirstNo());
                pledgeBaseInfo.setClassSecondName(mfCollateralClass.getClassSecondName());
                pledgeBaseInfo.setPledgeNo(pledgeNo);
                pledgeBaseInfo.setCaseId(caseId);
                if (ifPrivateAssets != null && BizPubParm.YES_NO_Y.equals(ifPrivateAssets)) {//自有资产
                    pledgeBaseInfo.setAssetProperty("1");
                }
                pledgeBaseInfoFeign.insert(pledgeBaseInfo);
            }
            // 获得验车单htmlStr
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            FormData FormcommuniMediaDetail = formService.getFormData("communiMediaDetail");
            getFormValue(FormcommuniMediaDetail);
            getObjValue(FormcommuniMediaDetail, pledgeBaseInfo);
            String htmlStr = jsonFormUtil.getJsonStr(FormcommuniMediaDetail, "propertySeeTag", "");
            dataMap.put("htmlStr", htmlStr);
            dataMap = new HashMap<String, Object>();
            // dataMap.put("vouType", vouType);
            dataMap.put("pledge", pledgeBaseInfo);
            dataMap.put("pledgeNo", pledgeNo);
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
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        try {
            dataMap = getMapByJson(ajaxData);
            String pledgeNo = (String) dataMap.get("pledgeNo");
            pledgeBaseInfo.setPledgeNo(pledgeNo);
            pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
            MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
                    .getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "PledgeBaseInfoAction", "");

            String formId = "";
            if (mfCollateralFormConfig == null) {

            } else {
                formId = mfCollateralFormConfig.getAddModelDef();
            }
            FormData formdlpledgebaseinfo0004 = null;
            if (StringUtil.isEmpty(formId)) {
                // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
                // "的PledgeBaseInfoAction表单信息没有查询到");
            } else {
                formdlpledgebaseinfo0004 = formService.getFormData(formId);
                if (formdlpledgebaseinfo0004.getFormId() == null) {
                    // logger.error("押品类型为" +
                    // mfCollateralFormConfig.getFormType() +
                    // "的PledgeBaseInfoAction表单form"
                    // + formId + ".xml文件不存在");
                } else {
                }
            }

            getFormValue(formdlpledgebaseinfo0004, getMapByJson(ajaxData));
            if (this.validateFormData(formdlpledgebaseinfo0004)) {
                pledgeBaseInfo = new PledgeBaseInfo();
                setObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                pledgeBaseInfoFeign.update(pledgeBaseInfo);

                // getTableData();//获取列表

                /*
                 * 当query为"query"或者ifBizManger为"0"时，解析的表单中不可单字段编辑；
                 * 当ifBizManger为"1"或""时，解析的表单中设置的可编辑的字段可以单字段编辑；
                 * 当ifBizManger为"2"时，解析的表单中所有非只读的字段可以单字段编辑；
                 */
                request.setAttribute("ifBizManger", "3");
                // 获得基本信息的展示表单ID，并将表单解析
                formId = mfCollateralFormConfig.getShowModelDef();
                FormData formdlpledgebaseinfo0006 = formService.getFormData(formId);
                if (formdlpledgebaseinfo0006.getFormId() == null) {
                    // logger.error("押品类型为" +
                    // mfCollateralFormConfig.getFormType() +
                    // "的PledgeBaseInfoAction表单form"
                    // + formId + ".xml文件不存在");
                }
                getFormValue(formdlpledgebaseinfo0006);
                getObjValue(formdlpledgebaseinfo0006, pledgeBaseInfo);
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formdlpledgebaseinfo0006, "propertySeeTag", "");

                dataMap = new HashMap<String, Object>();
                dataMap.put("htmlStr", htmlStr);
                dataMap.put("htmlStrFlag", "1");
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
    @RequestMapping("/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String ajaxData, String pledgeNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formdlpledgebaseinfo0002 = formService.getFormData("dlpledgebaseinfo0002");
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(pledgeNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        getObjValue(formdlpledgebaseinfo0002, pledgeBaseInfo, formData);
        if (pledgeBaseInfo != null) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }

    /**
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/certiInfo")
    @ResponseBody
    public Map<String, Object> deleteAjax(String ajaxData, String pledgeNo, String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(pledgeNo);
        try {
            JSONObject jb = JSONObject.fromObject(ajaxData);
            pledgeBaseInfo = (PledgeBaseInfo) JSONObject.toBean(jb, PledgeBaseInfo.class);
            pledgeBaseInfoFeign.delete(pledgeBaseInfo);
            getTableData(dataMap, tableId, pledgeBaseInfo);// 获取列表
            dataMap.put("flag", "success");
            dataMap.put("msg", "成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
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
    @SuppressWarnings({"unchecked"})
    @RequestMapping("/getById")
    public String getById(Model model, String pledgeNo, String collateralType) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(pledgeNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        EvalInfo evalInfo;
        String query = "";
        if (pledgeBaseInfo != null) {
            dataMap.put("flag", "success");
            CodeUtils cu = new CodeUtils();
            Map<String, String> cusTypeMap = cu.getMapByKeyName("KEEP_STATUS");
            String keepStatusName = cusTypeMap.get(pledgeBaseInfo.getKeepStatus());
            model.addAttribute("keepStatusName", keepStatusName);
            if (!CollateralConstant.REGIST.equals(pledgeBaseInfo.getKeepStatus())) {
                query = "query";
            } else {
                query = "";
                request.setAttribute("ifBizManger", "3");
            }
            pledgeBaseInfoHead = new PledgeBaseInfoHead();
            if (BizPubParm.COLLATERAL_TYPE_01.equals(pledgeBaseInfo.getClassModel())) {
                pledgeBaseInfoHead.setHeadFistName("应收账款净值");
                // pledgeBaseInfoHead.setHeadFistValue(MathExtend.moneyStr(pledgeBaseInfo.getExtNum01()/10000));
                if (pledgeBaseInfo.getPleValue() != null) {
                    pledgeBaseInfoHead.setHeadFistValue(MathExtend.moneyStr(pledgeBaseInfo.getPleValue() / 10000));
                } else {
                    pledgeBaseInfoHead.setHeadFistValue("0.00");
                }
                pledgeBaseInfoHead.setHeadFistUnit("万");
                pledgeBaseInfoHead.setHeadSecName("到期日");
                if (StringUtil.isEmpty(pledgeBaseInfo.getPleExpiryDate())) {
                    pledgeBaseInfoHead.setHeadSecValue("未登记");
                } else {
                    pledgeBaseInfoHead.setHeadSecValue(DateUtil.getShowDateTime(pledgeBaseInfo.getPleExpiryDate()));
                }
                pledgeBaseInfoHead.setHeadSecUnit("");
                pledgeBaseInfoHead.setHeadThirdName("押品类别");
                pledgeBaseInfoHead.setHeadThirdValue(pledgeBaseInfo.getClassSecondName());
                pledgeBaseInfoHead.setHeadThirdUnit("");
            } else {
                EvalInfo ei = new EvalInfo();
                ei.setCollateralId(pledgeNo);
                evalInfo = evalInfoFeign.getLatest(ei);
                pledgeBaseInfoHead.setHeadFistName("押品价值");
                if (pledgeBaseInfo.getPleValue() != null) {
                    pledgeBaseInfoHead.setHeadFistValue(MathExtend.moneyStr(pledgeBaseInfo.getPleValue() / 10000));
                } else {
                    pledgeBaseInfoHead.setHeadFistValue("0.00");
                }
                pledgeBaseInfoHead.setHeadFistUnit("万");
                pledgeBaseInfoHead.setHeadSecName("抵押率");
                if (pledgeBaseInfo.getPledgeRate() != null) {
                    pledgeBaseInfoHead.setHeadSecValue(MathExtend.moneyStr(pledgeBaseInfo.getPledgeRate()));
                } else {
                    pledgeBaseInfoHead.setHeadSecValue("0.00");
                }
                pledgeBaseInfoHead.setHeadSecUnit("%");
                pledgeBaseInfoHead.setHeadThirdName("押品类别");
                pledgeBaseInfoHead.setHeadThirdValue(pledgeBaseInfo.getClassSecondName());
                pledgeBaseInfoHead.setHeadThirdUnit("");
                List<EvalInfo> evalInfoList = evalInfoFeign.getAll(ei);
                model.addAttribute("evalInfoList", evalInfoList);
            }

            ChkInfo ci = new ChkInfo();
            ci.setCollateralId(pledgeNo);
            List<ChkInfo> chkInfoList = chkInfoFeign.getAll(ci);
            model.addAttribute("chkInfoList", chkInfoList);

        } else {
            dataMap.put("flag", "error");
        }
        // 查询已经录入信息的表单
        MfCollateralTable mfCollateralTable = new MfCollateralTable();
        mfCollateralTable.setCollateralNo(pledgeNo);
        List<MfCollateralTable> collateralTableList = mfCollateralTableFeign.getList(mfCollateralTable);
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        JsonTableUtil jtu = new JsonTableUtil();
        String tableId = "";
        String formId = "";

        for (int i = 0; i < collateralTableList.size(); i++) {
            if ("0".equals(collateralTableList.get(i).getDataFullFlag())) {
                continue;
            }
            String action = collateralTableList.get(i).getAction();
            String htmlStr = "";
            tableId = "table" + collateralTableList.get(i).getShowModelDef();
            formId = collateralTableList.get(i).getShowModelDef();
            FormData formcommon = formService.getFormData(formId);
            if (formcommon.getFormId() == null) {
                if (formId.indexOf("_") != -1) {
                    Integer num = formId.indexOf("_");
                    formId = formId.substring(0, num);
                }
            }
            if ("PledgeBaseInfoAction".equals(action)) {
                formcommon = formService.getFormData(formId);
                getFormValue(formcommon);
                searchClassSecondName(pledgeBaseInfo);
                getObjValue(formcommon, pledgeBaseInfo);
                htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
            } else if ("MfCarCheckFormAction".equals(action)) { // 验车单
                MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
                mfCarCheckForm.setPledgeNo(pledgeNo);
                mfCarCheckForm.setCheckType("1");
                mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfCarCheckForm", mfCarCheckForm));
                htmlStr = jtu.getJsonStr("tablecarCheckFormList", "tableTag",
                        (List<MfCarCheckForm>) mfCarCheckFormFeign.findByPage(ipage).getResult(), null, true);
            } else if ("PledgeBaseInfoBillAction".equals(action)) {// table
                pledgeBaseInfoBill = new PledgeBaseInfoBill();
                pledgeBaseInfoBill.setPledgeNo(pledgeNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<PledgeBaseInfoBill>) pledgeBaseInfoBillFeign.findByPageNotRegister(ipage).getResult(), null, true);
            } else if ("EvalInfoAction".equals(action)) {// table
                evalInfo = new EvalInfo();
                evalInfo.setCollateralId(pledgeNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("evalInfo", evalInfo));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<EvalInfo>) evalInfoFeign.findByPage(ipage).getResult(), null, true);
            } else if ("FairInfoAction".equals(action)) {// form
                FairInfo fairInfo = new FairInfo();
                fairInfo.setCollateralId(pledgeNo);
                fairInfo = fairInfoFeign.getByCollateralId(fairInfo);
                formcommon = formService.getFormData(formId);
                getFormValue(formcommon);
                getObjValue(formcommon, fairInfo);
                htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
            } else if ("InsInfoAction".equals(action)) {// table
                InsInfo insInfo = new InsInfo();
                insInfo.setCollateralId(pledgeNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("insInfo", insInfo));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<InsInfo>) insInfoFeign.findByPage(ipage).getResult(), null, true);
            } else if ("MfBusGpsRegAction".equals(action)) { // GPS安装
                MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
                mfBusGpsReg.setRelNo(pledgeNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsReg));
                List<MfBusGpsReg> mfBusGpsReglist = (List<MfBusGpsReg>) mfBusGpsRegFeign.findByPage(ipage).getResult();
                htmlStr = jtu.getJsonStr(tableId, "tableTag", mfBusGpsReglist, null, true);
            } else if ("CertiInfoAction".equals(action)) {// form
                CertiInfo certiInfo = new CertiInfo();
                certiInfo.setCollateralId(pledgeNo);
                certiInfo = certiInfoFeign.getByCollateralId(certiInfo);
                formcommon = formService.getFormData(formId);
                getObjValue(formcommon, certiInfo);
                htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
            } else if ("ChkInfoAction".equals(action)) {// table
                ChkInfo chkInfo = new ChkInfo();
                chkInfo.setCollateralId(pledgeNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("chkInfo", chkInfo));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<ChkInfo>) chkInfoFeign.findByPage(ipage).getResult(), null, true);
            } else if ("MfMoveableCheckInventoryInfoAction".equals(action)) {// form
                MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
                mfMoveableCheckInventoryInfo.setPledgeNo(pledgeNo);
                mfMoveableCheckInventoryInfo = mfMoveableCheckInventoryInfoFeign.getById(mfMoveableCheckInventoryInfo);
                /*
                 * certiInfo = new CertiInfo();
                 * certiInfo.setCollateralId(pledgeNo); certiInfo =
                 * certiInfoFeign.getByCollateralId(certiInfo);
                 */
                formcommon = formService.getFormData(formId);
                getFormValue(formcommon);
                getObjValue(formcommon, mfMoveableCheckInventoryInfo);
                htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
            } else if ("MfMaintenanceAction".equals(action)) {
                MfMaintenance mfMaintenance = new MfMaintenance();
                mfMaintenance.setCollateralId(pledgeNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfMaintenance", mfMaintenance));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<MfMaintenance>) mfMaintenanceFeign.findByPage(ipage).getResult(), null, true);
            } else if ("MfCollateralInsuranceClaimsAction".equals(action)) {
                MfCollateralInsuranceClaims mfCollateralInsuranceClaims2 = new MfCollateralInsuranceClaims();
                mfCollateralInsuranceClaims2.setCollateralId(pledgeNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfCollateralInsuranceClaims", mfCollateralInsuranceClaims2));
                htmlStr = jtu.getJsonStr(tableId, "tableTag", (List<MfCollateralInsuranceClaims>) mfCollateralInsuranceClaimsFeign.findByPage(ipage).getResult(), null, true);
            } else if ("MfAccntRepayDetailAction".equals(action)) {// table
                MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
                mfAccntRepayDetail.setTransferId(pledgeNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfAccntRepayDetail", mfAccntRepayDetail));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<MfAccntRepayDetail>) mfAccntRepayDetailFeign.findByPage(ipage).getResult(), null, true);
            }else {
            }
            collateralTableList.get(i).setHtmlStr(htmlStr);
        }

        dataMap.put("collateralTableList", collateralTableList);
        JSONObject jb = JSONObject.fromObject(dataMap);
        dataMap = jb;
        // appId
        String cusNo = pledgeBaseInfo.getCusNo();
        String appId = mfBusCollateralRelFeign.getAppIdByCollateralId(pledgeNo);
        String pactId = "";
        if (StringUtil.isNotEmpty(appId)) {
            MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
            if (mfBusPact != null) {
                pactId = mfBusPact.getPactId();
            }
            MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
            mfBusCollateralRel.setAppId(appId);
            mfBusCollateralRel.setCollateralType(collateralType);
            mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
            model.addAttribute("keepId", mfBusCollateralRel.getKeepId());
        }
        model.addAttribute("pactId", pactId);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("appId", StringUtil.KillNull(appId));
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("pledgeBaseInfo", pledgeBaseInfo);
        model.addAttribute("pledgeBaseInfoHead", pledgeBaseInfoHead);
        model.addAttribute("busEntrance", request.getParameter("busEntrance"));
        model.addAttribute("query", query);
        return "/component/collateral/MfBusCollateral_Detail";
    }

    /**
     * AJAX获取查看应收账款查看
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked"})
    @RequestMapping("/getByIdForAccount")
    public String getByIdForAccount(Model model, String receId, String fincId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
//		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
//		pledgeBaseInfo.setPledgeNo(pledgeNo);
//		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
        mfBusReceBaseInfo.setReceId(receId);
        mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
        boolean repayFlag = false;
        boolean jiefuFlag = false;
        EvalInfo evalInfo;
        String query = "";
        if (mfBusReceBaseInfo != null) {
            dataMap.put("flag", "success");
            CodeUtils cu = new CodeUtils();
            Map<String, String> cusTypeMap = cu.getMapByKeyName("RECE_STS");
            String keepStatusName = cusTypeMap.get(mfBusReceBaseInfo.getReceSts());
            model.addAttribute("keepStatusName", keepStatusName);
            /*
             * 当query为"query"或者ifBizManger为"0"时，解析的表单中不可单字段编辑；
             * 当ifBizManger为"1"或""时，解析的表单中设置的可编辑的字段可以单字段编辑；
             * 当ifBizManger为"2"时，解析的表单中所有非只读的字段可以单字段编辑；
             */
            if (StringUtil.isNotEmpty(fincId)) {
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setFincId(fincId);
                mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
                if (mfBusFincApp != null) {
                    if ("5".equals(mfBusFincApp.getFincSts())) {
                        repayFlag = true;
                    }
                    //根据借据号获取账款结余
                    MfBusReceBal mfBusReceBal = new MfBusReceBal();
                    mfBusReceBal.setFincId(fincId);
                    mfBusReceBal = mfBusReceBalFeign.getById(mfBusReceBal);
                    if (mfBusReceBal != null) {
                        if (mfBusReceBal.getBalAmt() != null && mfBusReceBal.getBalAmt() > 0) {
                            jiefuFlag = true;
                        }
                    }
                }
            }
            if (!CollateralConstant.REGIST.equals(mfBusReceBaseInfo.getReceSts())) {
                query = "query";
            } else {
                query = "";
                request.setAttribute("ifBizManger", "3");
            }
            model.addAttribute("repayFlag", repayFlag);
            model.addAttribute("fincId", fincId);
            model.addAttribute("jiefuFlag", jiefuFlag);
            pledgeBaseInfoHead = new PledgeBaseInfoHead();
            pledgeBaseInfoHead.setHeadFistName("应收账款净值");
            if (mfBusReceBaseInfo.getReceAmt() != null) {
                pledgeBaseInfoHead.setHeadFistValue(MathExtend.moneyStr(mfBusReceBaseInfo.getReceAmt() / 10000));
            } else {
                pledgeBaseInfoHead.setHeadFistValue("0.00");
            }
            pledgeBaseInfoHead.setHeadFistUnit("万");
            pledgeBaseInfoHead.setHeadSecName("到期日");
            if (StringUtil.isEmpty(mfBusReceBaseInfo.getReceEndDate())) {
                pledgeBaseInfoHead.setHeadSecValue("未登记");
            } else {
                pledgeBaseInfoHead.setHeadSecValue(DateUtil.getShowDateTime(mfBusReceBaseInfo.getReceEndDate()));
            }
            pledgeBaseInfoHead.setHeadSecUnit("");
            pledgeBaseInfoHead.setHeadThirdName("押品类别");
            pledgeBaseInfoHead.setHeadThirdValue("应收账款");
            pledgeBaseInfoHead.setHeadThirdUnit("");

            ChkInfo ci = new ChkInfo();
            ci.setCollateralId(receId);
            List<ChkInfo> chkInfoList = chkInfoFeign.getAll(ci);
            model.addAttribute("chkInfoList", chkInfoList);

        } else {
            dataMap.put("flag", "error");
        }
        // 查询已经录入信息的表单
        MfCollateralTable mfCollateralTable = new MfCollateralTable();
        mfCollateralTable.setCollateralNo(receId);
        List<MfCollateralTable> collateralTableList = mfCollateralTableFeign.getList(mfCollateralTable);
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        JsonTableUtil jtu = new JsonTableUtil();
        String tableId = "";
        String formId = "";
        //获取表单编号
        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType("17061115550593210", "PledgeBaseInfoAction", "E");
        String baseFormId = mfCollateralFormConfig.getShowModelDef();
        FormData receBaseInfoForm = formService.getFormData(baseFormId);
        if (receBaseInfoForm.getFormId() == null) {
            receBaseInfoForm = formService.getFormData(mfCollateralFormConfig.getShowModel());
        }
        getFormValue(receBaseInfoForm);
        getObjValue(receBaseInfoForm, mfBusReceBaseInfo);
        String htmlStr = jsonFormUtil.getJsonStr(receBaseInfoForm, "propertySeeTag", query);
        dataMap.put("htmlStr", htmlStr);
        for (int i = 0; i < collateralTableList.size(); i++) {
            if ("0".equals(collateralTableList.get(i).getDataFullFlag())) {
                continue;
            }
            String action = collateralTableList.get(i).getAction();
            tableId = "table" + collateralTableList.get(i).getShowModelDef();
            formId = collateralTableList.get(i).getShowModelDef();
            FormData formcommon = formService.getFormData(formId);
            if (formcommon.getFormId() == null) {
                if (formId.indexOf("_") != -1) {
                    Integer num = formId.indexOf("_");
                    formId = formId.substring(0, num);
                }
            }
            if ("PledgeBaseInfoAction".equals(action)) {
            } else if ("MfCarCheckFormAction".equals(action)) { // 验车单
                MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
                mfCarCheckForm.setPledgeNo(receId);
                mfCarCheckForm.setCheckType("1");
                mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfCarCheckForm", mfCarCheckForm));
                htmlStr = jtu.getJsonStr("tablecarCheckFormList", "tableTag",
                        (List<MfCarCheckForm>) mfCarCheckFormFeign.findByPage(ipage).getResult(), null, true);
            } else if ("PledgeBaseInfoBillAction".equals(action)) {// table
                pledgeBaseInfoBill = new PledgeBaseInfoBill();
                pledgeBaseInfoBill.setPledgeNo(receId);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<PledgeBaseInfoBill>) pledgeBaseInfoBillFeign.findByPageNotRegister(ipage).getResult(), null, true);
            } else if ("EvalInfoAction".equals(action)) {// table
                evalInfo = new EvalInfo();
                evalInfo.setCollateralId(receId);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("evalInfo", evalInfo));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<EvalInfo>) evalInfoFeign.findByPage(ipage).getResult(), null, true);
            } else if ("FairInfoAction".equals(action)) {// form
                FairInfo fairInfo = new FairInfo();
                fairInfo.setCollateralId(receId);
                fairInfo = fairInfoFeign.getByCollateralId(fairInfo);
                formcommon = formService.getFormData(formId);
                getFormValue(formcommon);
                getObjValue(formcommon, fairInfo);
                htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
            } else if ("InsInfoAction".equals(action)) {// table
                InsInfo insInfo = new InsInfo();
                insInfo.setCollateralId(receId);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("insInfo", insInfo));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<InsInfo>) insInfoFeign.findByPage(ipage).getResult(), null, true);
            } else if ("MfBusGpsRegAction".equals(action)) { // GPS安装
                MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
                mfBusGpsReg.setRelNo(receId);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsReg));
                List<MfBusGpsReg> mfBusGpsReglist = (List<MfBusGpsReg>) mfBusGpsRegFeign.findByPage(ipage).getResult();
                htmlStr = jtu.getJsonStr(tableId, "tableTag", mfBusGpsReglist, null, true);
            } else if ("CertiInfoAction".equals(action)) {// form
                CertiInfo certiInfo = new CertiInfo();
                certiInfo.setCollateralId(receId);
                certiInfo = certiInfoFeign.getByCollateralId(certiInfo);
                formcommon = formService.getFormData(formId);
                getObjValue(formcommon, certiInfo);
                htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
            } else if ("ChkInfoAction".equals(action)) {// table
                ChkInfo chkInfo = new ChkInfo();
                chkInfo.setCollateralId(receId);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("chkInfo", chkInfo));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<ChkInfo>) chkInfoFeign.findByPage(ipage).getResult(), null, true);
            } else if ("MfMoveableCheckInventoryInfoAction".equals(action)) {// form
                MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
                mfMoveableCheckInventoryInfo.setPledgeNo(receId);
                mfMoveableCheckInventoryInfo = mfMoveableCheckInventoryInfoFeign.getById(mfMoveableCheckInventoryInfo);
                formcommon = formService.getFormData(formId);
                getFormValue(formcommon);
                getObjValue(formcommon, mfMoveableCheckInventoryInfo);
                htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
            } else if ("MfMaintenanceAction".equals(action)) {
                MfMaintenance mfMaintenance = new MfMaintenance();
                mfMaintenance.setCollateralId(receId);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfMaintenance", mfMaintenance));
                htmlStr = jtu.getJsonStr(tableId, "tableTag",
                        (List<MfMaintenance>) mfMaintenanceFeign.findByPage(ipage).getResult(), null, true);
            } else if ("MfCollateralInsuranceClaimsAction".equals(action)) {
                MfCollateralInsuranceClaims mfCollateralInsuranceClaims2 = new MfCollateralInsuranceClaims();
                mfCollateralInsuranceClaims2.setCollateralId(receId);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfCollateralInsuranceClaims", mfCollateralInsuranceClaims2));
                htmlStr = jtu.getJsonStr(tableId, "tableTag", (List<MfCollateralInsuranceClaims>) mfCollateralInsuranceClaimsFeign.findByPage(ipage).getResult(), null, true);
            }else {
            }
            collateralTableList.get(i).setHtmlStr(htmlStr);
        }

        dataMap.put("collateralTableList", collateralTableList);

        JSONObject jb = JSONObject.fromObject(dataMap);
        dataMap = jb;
        // appId
        String cusNo = mfBusReceBaseInfo.getCusNo();
        String appId = mfBusCollateralRelFeign.getAppIdByCollateralId(receId);
        String pactId = "";
        if (StringUtil.isNotEmpty(appId)) {
            MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
            if (mfBusPact != null) {
                pactId = mfBusPact.getPactId();
            }
            MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
            mfBusCollateralRel.setAppId(appId);
            mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
            //	model.addAttribute("keepId", mfBusCollateralRel.getKeepId());
        }
        model.addAttribute("pactId", pactId);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("appId", StringUtil.KillNull(appId));
        model.addAttribute("dataMap", dataMap);

       /* // bug修改：出库按钮的控制
        MfBusPact mbp = pactInterfaceFeign.getMfBusPactByPleId(receId);
        if (mbp != null) {
            String busSts = mbp.getPactSts();
            model.addAttribute("busSts", busSts);
        }*/
        model.addAttribute("mfBusReceBaseInfo", mfBusReceBaseInfo);
        model.addAttribute("pledgeBaseInfoHead", pledgeBaseInfoHead);
        model.addAttribute("query", query);
        return "/component/collateral/MfBusCollateral_DetailForAccount";
    }

    private void searchClassSecondName(PledgeBaseInfo pledgeBaseInfo) {
        if (StringUtil.isNotEmpty(pledgeBaseInfo.getClassSecondName())) {
            MfCollateralClass mfCollateralClass = new MfCollateralClass();
            mfCollateralClass.setClassId(pledgeBaseInfo.getClassId());
            mfCollateralClass = mfCollateralClassFeign.getById(mfCollateralClass);
            pledgeBaseInfo.setClassFirstNo(mfCollateralClass.getClassFirstNo());
            pledgeBaseInfo.setClassSecondName(mfCollateralClass.getClassSecondName());
        }

    }

    /***
     * 方法描述： 押品新增主页面
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/collateralClassInput")
    public String collateralClassInput(Model model, String collateralNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        // 查询已经录入信息的表单
        MfCollateralTable mfCollateralTable = new MfCollateralTable();
        mfCollateralTable.setCollateralNo(collateralNo);
        List<MfCollateralTable> collateralTableList = mfCollateralTableFeign.getList(mfCollateralTable);
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        JsonTableUtil jtu = new JsonTableUtil();
        for (int i = 0; i < collateralTableList.size(); i++) {
            if ("0".equals(collateralTableList.get(i).getDataFullFlag())) {
                continue;
            }
            String action = collateralTableList.get(i).getAction();
            String htmlStr = "";
            if ("PledgeBaseAction".equals(action)) {
                // pledgeBase = new PledgeBase();
                // pledgeBase.setPledgeId(collateralNo);
                // pledgeBase = pledgeBaseFeign.getById(pledgeBase);
                // FormData formcommon =
                // formService.getFormData(collateralTableList.get(i).getShowModelDef());
                // getFormValue(formcommon);
                // getObjValue(formcommon, pledgeBase);
                // htmlStr = jsonFormUtil.getJsonStr(formcommon,
                // "propertySeeTag", query);

                PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                pledgeBaseInfo.setPledgeNo(collateralNo);
                pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
                FormData formcommon = formService.getFormData(collateralTableList.get(i).getShowModelDef());
                getFormValue(formcommon);
                getObjValue(formcommon, pledgeBaseInfo);
                htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
            }

            collateralTableList.get(i).setHtmlStr(htmlStr);
        }

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("collateralTableList", collateralTableList);
        JSONObject jb = JSONObject.fromObject(dataMap);
        dataMap = jb;

        System.out.println(jb);

        model.addAttribute("jtu", jtu);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        return "/component/collateral/collateralClassInput";
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/input")
    public String input(Model model, String collateralNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(collateralNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);

        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
                .getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "PledgeBaseInfoAction", "");
        String formId = null;
        if (mfCollateralFormConfig == null) {

        } else {
            formId = mfCollateralFormConfig.getAddModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
            // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
            // "的PledgeBaseInfoAction表单信息没有查询到");
        } else {
            FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
            if (formdlpledgebaseinfo0004.getFormId() == null) {
                // logger.error("押品类型为" + mfCollateralFormConfig.getFormType() +
                // "的PledgeBaseInfoAction表单form" + formId
                // + ".xml文件不存在");
            } else {
                getFormValue(formdlpledgebaseinfo0004);
                getObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
            }
        }

        model.addAttribute("query", "");
        return "/component/collateral/PledgeBaseInfo_Insert";
    }

    /**
     * 车管验车新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/inputCar")
    public String inputCar(Model model, String collateralNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(collateralNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);

        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
                .getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "PledgeBaseInfoAction", "");
        String formId = null;
        if (mfCollateralFormConfig == null) {

        } else {
            formId = mfCollateralFormConfig.getAddModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
            // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
            // "的PledgeBaseInfoAction表单信息没有查询到");
        } else {
            FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
            if (formdlpledgebaseinfo0004.getFormId() == null) {
                // logger.error("押品类型为" + mfCollateralFormConfig.getFormType() +
                // "的PledgeBaseInfoAction表单form" + formId
                // + ".xml文件不存在");
            } else {
                getFormValue(formdlpledgebaseinfo0004);
                getObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
            }
        }

        model.addAttribute("query", "");
        return "/component/collateral/PledgeBaseInfo_Insert";
    }

    /**
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData, String pledgeNo, String formId) throws Exception {

        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 这里得到的formId是带form字符串的，比如formcuscorp0001
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        dataMap = getMapByJson(ajaxData);
        pledgeNo = (String) dataMap.get("pledgeNo");
        pledgeBaseInfo.setPledgeNo(pledgeNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);

        if (StringUtil.isEmpty(formId)) {
            formId = mfCollateralFormConfigFeign
                    .getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "PledgeBaseInfoAction", "").getShowModelDef();
        } else {
            if (formId.indexOf("form") == -1) {
            } else {
                formId = formId.substring(4);
            }
        }
        FormData formdlpledgebaseinfo0006 = formService.getFormData(formId);
        getFormValue(formdlpledgebaseinfo0006, getMapByJson(ajaxData));
        PledgeBaseInfo pledgeBaseInfoJsp = new PledgeBaseInfo();
        setObjValue(formdlpledgebaseinfo0006, pledgeBaseInfoJsp);
        if (pledgeBaseInfo != null) {
            try {
                pledgeBaseInfo = (PledgeBaseInfo) EntityUtil.reflectionSetVal(pledgeBaseInfo, pledgeBaseInfoJsp, getMapByJson(ajaxData));

                if (pledgeBaseInfo.getPleOriginalValue() != null && pledgeBaseInfo.getPleOriginalValue() > 0 && pledgeBaseInfo.getPledgeRate() != null && pledgeBaseInfo.getPledgeRate() > 0) {
                    Double pleValue = MathExtend.multiply(pledgeBaseInfo.getPleOriginalValue(), pledgeBaseInfo.getPledgeRate());
                    pleValue = MathExtend.divide(pleValue, 100);
                    pledgeBaseInfo.setPleValue(pleValue);
                }
                if(dataMap.containsKey("relId")&&dataMap.containsKey("pledgeType")){
                    String relId = (String) dataMap.get("relId");
                    if(StringUtil.isNotEmpty(relId)){
                        MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
                        mfBusCollateralRel.setAppId(relId);
                        mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
                        MfBusCollateralDetailRel mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
                        mfBusCollateralDetailRel.setCollateralId(pledgeNo);
                        mfBusCollateralDetailRel.setBusCollateralId(mfBusCollateralRel.getBusCollateralId());
                        mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRel);
                        if(mfBusCollateralDetailRel!=null){
                            MfBusCollateralDetailRel mfBusCollateralDetailRelUpdate = new MfBusCollateralDetailRel();
                            mfBusCollateralDetailRelUpdate.setId(mfBusCollateralDetailRel.getId());
                            mfBusCollateralDetailRelUpdate.setIfRegister(pledgeBaseInfo.getPledgeType());
                            mfBusCollateralDetailRelFeign.update(mfBusCollateralDetailRelUpdate);
                        }
                    }

                }


                pledgeBaseInfoFeign.update(pledgeBaseInfo);
                dataMap.put("flag", "success");
                dataMap.put("msg", "保存成功");
            } catch (Exception e) {
                e.printStackTrace();
                dataMap.put("flag", "error");
                dataMap.put("msg", "新增失败");
                throw e;
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", "编号不存在,保存失败");
        }
        return dataMap;
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/inputInsert")
    public String inputInsert(Model model, String classId, String classSecondName, String cusNo, String cusName,
                              String vouType) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setClassId(classId);
        pledgeBaseInfo.setClassSecondName(classSecondName);
        pledgeBaseInfo.setCusNo(cusNo);
        pledgeBaseInfo.setCusName(cusName);
        pledgeBaseInfo.setPledgeMethod(vouType);
        pledgeBaseInfo.setKeepStatus(CollateralConstant.REGIST);// 登记
        pledgeBaseInfo.setRefFlag("0");// 是否被关联
        pledgeBaseInfo.setDelflag("0");// 未删除

        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
                "PledgeBaseInfoAction", "");
        String formId = null;
        if (mfCollateralFormConfig == null) {

        } else {
            formId = mfCollateralFormConfig.getAddModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
            // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
            // "的PledgeBaseInfoAction表单信息没有查询到");
        } else {
            FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
            if (formdlpledgebaseinfo0004.getFormId() == null) {
                // logger.error("押品类型为" + mfCollateralFormConfig.getFormType() +
                // "的PledgeBaseInfoAction表单form" + formId
                // + ".xml文件不存在");
            } else {
                getFormValue(formdlpledgebaseinfo0004);
                getObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
            }
        }

        model.addAttribute("query", "");
        return "/component/collateral/PledgeBaseInfo_inputInsert";
    }

    /**
     * 方法描述： 跳转选择客户押品弹框
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-5-2 下午6:03:55
     */
    @RequestMapping("/getPledgeInfoPageForSelect")
    public String getPledgeInfoPageForSelect(Model model, String cusNo, String appId, String pledgeMethod)
            throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("appId", appId);
        model.addAttribute("pledgeMethod", pledgeMethod);
        model.addAttribute("pledgeMethod", pledgeMethod);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("appId", appId);
        return "/component/collateral/pledgeInfo_select";
    }

    /**
     * 方法描述： 跳转选择客户押品弹框
     *
     * @return
     * @throws Exception String
     * @author 徐孟涛
     * @date 2018-11-9 下午6:03:55
     */
    @RequestMapping("/getPledgeInfoPageForSelect2")
    public String getPledgeInfoPageForSelect2(Model model, String cusNo, String appId, String collateralType)
            throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("appId", appId);
        model.addAttribute("collateralType", collateralType);
        return "/component/collateral/pledgeInfo_select2";
    }

    /**
     * 方法描述： 获得选择押品分页列表
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-5-2 下午6:03:32
     */
    @RequestMapping("/getPledgeInfoListByCusNoPage")
    @ResponseBody
    public Map<String, Object> getPledgeInfoListByCusNoPage(String ajaxData, String cusNo, String pledgeMethod,
                                                            Integer pageNo, Integer pageSize, String tableId, String tableType, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setCustomQuery(ajaxData);
            pledgeBaseInfo.setCustomSorts(ajaxData);
            pledgeBaseInfo.setCriteriaList(pledgeBaseInfo, ajaxData);
            if ("null".equals(cusNo)) {
                cusNo = "";
            }
            if ("null".equals(pledgeMethod)) {
                pledgeMethod = "";
            }
            pledgeBaseInfo.setCusNo(cusNo);
            pledgeBaseInfo.setPledgeMethod(pledgeMethod);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("pledgeBaseInfo", pledgeBaseInfo));
            ipage = pledgeBaseInfoFeign.getPledgeInfoListByCusNoPage(ipage, appId);
            JsonTableUtil jtu = new JsonTableUtil();
            @SuppressWarnings("rawtypes")
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
     * 方法描述： 获得选择押品分页列表
     *
     * @return
     * @throws Exception String
     * @author 徐孟涛
     * @date 2018-11-20 下午6:03:32
     */
    @RequestMapping("/getPledgeInfoListByCusNoPage2")
    @ResponseBody
    public Map<String, Object> getPledgeInfoListByCusNoPage2(String ajaxData, String cusNo, String pledgeMethod,
                                                             Integer pageNo, Integer pageSize, String tableId, String tableType, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setCustomQuery(ajaxData);
            pledgeBaseInfo.setCustomSorts(ajaxData);
            pledgeBaseInfo.setCriteriaList(pledgeBaseInfo, ajaxData);
            if ("null".equals(cusNo)) {
                cusNo = "";
            }
            pledgeBaseInfo.setCusNo(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("pledgeBaseInfo", pledgeBaseInfo));
            //ipage = pledgeBaseInfoFeign.findByPage(ipage);
            List<PledgeBaseInfo> pledgeBaseInfoList = pledgeBaseInfoFeign.getByAppId(appId, "pledge");
            //ipage.setResult(pledgeBaseInfoList);
            //ipage.setPageCounts(pledgeBaseInfoList.size());

            JsonTableUtil jtu = new JsonTableUtil();
            @SuppressWarnings("rawtypes")
            String tableHtml = jtu.getJsonStr(tableId, tableType, pledgeBaseInfoList, ipage, true);
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
     * 方法描述： 获得选择押品分页列表,与业务关联的押品，并过滤场景已使用的押品
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-8-14 下午9:43:57
     */
    @RequestMapping("/getPledgeListByBussPageSelect")
    public String getPledgeListByBussPageSelect() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/collateral/pledgeInfo_selectByBuss";
    }

    /**
     * 方法描述： 获得选择押品分页列表,与业务关联的押品
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-5-2 下午6:03:32
     */
    @RequestMapping("/getPledgeListByBussPage")
    @ResponseBody
    public Map<String, Object> getPledgeListByBussPage(String ajaxData, String cusNo, String pledgeNoStr,
                                                       Integer pageNo, Integer pageSize, String tableId, String tableType, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setCustomQuery(ajaxData);
            pledgeBaseInfo.setCustomSorts(ajaxData);
            pledgeBaseInfo.setCriteriaList(pledgeBaseInfo, ajaxData);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            ipage = pledgeBaseInfoFeign.getPledgeInfoListByBussPage(ipage, pledgeBaseInfo, appId, pledgeNoStr);
            JsonTableUtil jtu = new JsonTableUtil();
            @SuppressWarnings("rawtypes")
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
     * 方法描述： 根据申请号获取该客户关联的为录入公证或者权证信息的押品列表
     *
     * @return
     * @throws Exception String
     * @author YuShuai
     * @date 2017-6-3 上午9:18:27
     */
    @RequestMapping("/getPledgeInfoListByAppIdPage")
    @ResponseBody
    public Map<String, Object> getPledgeInfoListByAppIdPage(String ajaxData, String cusNo, String pleFlag,
                                                            String pledgeMethod, Integer pageNo, Integer pageSize,
                                                            String tableId, String tableType, String appId,String collateralType)
            throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setCustomQuery(ajaxData);
            pledgeBaseInfo.setCustomSorts(ajaxData);
            pledgeBaseInfo.setCriteriaList(pledgeBaseInfo, ajaxData);
            pledgeBaseInfo.setCusNo(cusNo);
            pledgeBaseInfo.setPledgeMethod(pledgeMethod);
            pledgeBaseInfo.setExtOstr01(appId);
            pledgeBaseInfo.setExtOstr02(pleFlag);
            pledgeBaseInfo.setExtOstr03(collateralType);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("pledgeBaseInfo", pledgeBaseInfo));
            ipage = pledgeBaseInfoFeign.getPledgeInfoListByAppIdPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            @SuppressWarnings("rawtypes")
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
     * 方法描述：
     *
     * @return
     * @throws Exception String
     * @author YuShuai
     * @date 2017-6-14 下午5:58:15
     */
    @RequestMapping("/getPledgeListByAppInfo")
    @ResponseBody
    public Map<String, Object> getPledgeListByAppInfo(String ajaxData, String cusNo, String tableName,
                                                      String pledgeMethod, Integer pageNo, Integer pageSize, String tableId, String tableType, String appId)
            throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setCustomQuery(ajaxData);
            pledgeBaseInfo.setCustomSorts(ajaxData);
            pledgeBaseInfo.setCriteriaList(pledgeBaseInfo, ajaxData);
            pledgeBaseInfo.setCusNo(cusNo);
            pledgeBaseInfo.setPledgeMethod(pledgeMethod);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            // ipage = pledgeBaseInfoFeign.getPledgeInfoListByAppIdPage(ipage,
            // pledgeBaseInfo, appId,pleFlag);
            ipage = pledgeBaseInfoFeign.getPledgeListByAppInfo(ipage, pledgeBaseInfo, appId, tableName);
            JsonTableUtil jtu = new JsonTableUtil();
            @SuppressWarnings("rawtypes")
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
     * @描述 资产菜单中打开押品新增页面
     * @参数 [model, classFirstNo, entranceType]
     * @返回值 java.lang.String
     * @创建人  shenhaobing
     * @创建时间 2019/9/21
     * @修改人和其它信息
     */
    @SuppressWarnings("deprecation")
    @RequestMapping("/inputInsertNew")
    public String inputInsertNew(Model model, String classFirstNo, String entranceType) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        MfCollateralClass mfCollateralClass = new MfCollateralClass();
        if (StringUtil.isEmpty(classFirstNo)) {
            classFirstNo = "A,B,C,D";
        }
        mfCollateralClass.setClassFirstNoList(Arrays.asList(classFirstNo.split(",")));
        mfCollateralClass.setUseFlag(CollateralConstant.USED);
        List<MfCollateralClass> list = mfCollateralClassFeign.getAll(mfCollateralClass);
        Map<String, String> vouTypeMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
        Map<String, String> dicMap = new HashMap<String, String>();
        if (list != null && list.size() > 0) {
            mfCollateralClass = list.get(0);
            List<ParmDic> vouTypeArray = new ArrayList<ParmDic>();
            for (int k = 0; k < list.size(); k++) {
                MfCollateralClass mfCollateralClassTmp = new MfCollateralClass();
                mfCollateralClassTmp = list.get(k);
                String[] vouTypes = mfCollateralClassTmp.getVouType().split("\\|");
                for (String type : vouTypes) {
                    if (!dicMap.containsKey(type)) {
                        dicMap.put(type, type);
                        ParmDic parmDic = new ParmDic();
                        parmDic.setOptCode(type);
                        parmDic.setOptName(vouTypeMap.get(type));
                        vouTypeArray.add(parmDic);
                    }
                }
            }
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setClassId(mfCollateralClass.getClassId());
            pledgeBaseInfo.setClassFirstNo(mfCollateralClass.getClassFirstNo());
            pledgeBaseInfo.setClassSecondName(mfCollateralClass.getClassSecondName());
            String[] vouArray = mfCollateralClass.getVouType().split("\\|");
            pledgeBaseInfo.setPledgeMethod(vouArray[0]);
            String vouType = vouArray[0];

            List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
            OptionsList op = new OptionsList();
            op.setOptionLabel(vouTypeMap.get(vouType));
            op.setOptionValue(vouType);
            vouTypeList.add(op);
            for (ParmDic parmDic : vouTypeArray) {
                if (parmDic.getOptCode().equals(vouType)) {
                    continue;
                }
                op = new OptionsList();
                op.setOptionLabel(parmDic.getOptName());
                op.setOptionValue(parmDic.getOptCode());
                vouTypeList.add(op);
            }
            pledgeBaseInfo.setKeepStatus(CollateralConstant.REGIST);// 登记
            pledgeBaseInfo.setRefFlag("0");// 是否被关联
            pledgeBaseInfo.setDelflag("0");// 未删除

            MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(mfCollateralClass.getClassId() + "-1", "PledgeBaseInfoAction", "");
            if (mfCollateralFormConfig != null) {
                String formId = mfCollateralFormConfig.getAddModelDef();
                if (StringUtil.isEmpty(formId)) {
                    // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
                    // "的PledgeBaseInfoAction表单信息没有查询到");
                } else {
                    FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
                    if (formdlpledgebaseinfo0004.getFormId() == null) {
                        // 子版表单意外丢失，使用母版。避免前台表单空白或报错。
                        formId = mfCollateralFormConfig.getAddModel();
                        formdlpledgebaseinfo0004 = formService.getFormData(formId);
                        // logger.error("押品类型为" +
                        // mfCollateralFormConfig.getFormType() +
                        // "的PledgeBaseInfoAction表单form"
                        // + formId + ".xml文件不存在");
                    }
                    getFormValue(formdlpledgebaseinfo0004);
                    getObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                    this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeList);
                    model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
                }
            }
            JSONObject json = new JSONObject();
            JSONArray collClassArray = JSONArray.fromObject(list);
            JSONArray collClassArrayNew = new JSONArray();
            for (int i = 0; i < collClassArray.size(); i++) {
                String vouTypeTmp = collClassArray.getJSONObject(i).getString("vouType");
                if (vouTypeTmp.indexOf(vouType) != -1) {
                    collClassArray.getJSONObject(i).put("id", collClassArray.getJSONObject(i).getString("classId"));
                    collClassArray.getJSONObject(i).put("name", collClassArray.getJSONObject(i).getString("classSecondName"));
                    collClassArrayNew.add(collClassArray.getJSONObject(i));
                }
            }
            json.put("collClass", collClassArrayNew);
            String ajaxData = json.toString();

            model.addAttribute("ajaxData", ajaxData);
            model.addAttribute("query", "");
            model.addAttribute("entranceType", entranceType);
            model.addAttribute("classFirstNo", classFirstNo);
            return "/component/collateral/PledgeBaseInfo_InputInsertNew";
        } else {
            ActionContext.getActionContext().getRequest().setAttribute("flagMsg", "请先配置好押品类型并启用");
            model.addAttribute("mfCollateralClass", mfCollateralClass);
            model.addAttribute("query", "");
            return "/component/collateral/PledgeBaseInfo_inputInsertNewError";
        }

    }

    /**
     * 方法描述： 指定客户新增押品
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-8-15 下午5:04:54
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/inputInsertByCusNo")
    public String inputInsertByCusNo(Model model, String cusNo, String caseId, String ifPrivateAssets) throws
            Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        MfCollateralClass mfCollateralClass = new MfCollateralClass();
        List<MfCollateralClass> list = mfCollateralClassFeign.getAll(mfCollateralClass);
        // 对押品类别列表进行处理
        List<MfCollateralClass> tempList = new ArrayList<MfCollateralClass>();
        for (MfCollateralClass mcc : list) {
            if (null == mcc.getClassFirstNo() || "".equals(mcc.getClassFirstNo())
                    || CollateralConstant.NOUSED.equals(mcc.getUseFlag())) {
                tempList.add(mcc);
                continue;
            }
        }
        list.removeAll(tempList);
        if (list.size() > 0 && null == mfCollateralClass.getClassId()) {
            mfCollateralClass = list.get(0);
        }
        if (mfCollateralClass.getClassId() == null || "".equals(mfCollateralClass.getClassId())) {
            ActionContext.getActionContext().getRequest().setAttribute("flagMsg", "请先配置好押品类型并启用");
            model.addAttribute("query", "");
            return "/component/collateral/PledgeBaseInfo_inputInsertNewError";
        }
        List<ParmDic> vouTypeArray = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
        for (int k = 0; k < list.size(); k++) {
            if (vouTypeArray.get(0).getOptCode().equals(list.get(k).getVouType().split("\\|")[0])) {
                mfCollateralClass = list.get(k);
                break;
            }
        }
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setClassId(mfCollateralClass.getClassId());
        pledgeBaseInfo.setClassFirstNo(mfCollateralClass.getClassFirstNo());
        pledgeBaseInfo.setClassSecondName(mfCollateralClass.getClassSecondName());
        String[] vouArray = mfCollateralClass.getVouType().split("\\|");
        pledgeBaseInfo.setPledgeMethod(vouArray[0]);
        String vouType = vouArray[0];
        Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
        List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
        OptionsList op = new OptionsList();
        op.setOptionLabel(dicMap.get(vouType));
        op.setOptionValue(vouType);
        vouTypeList.add(op);
        for (ParmDic parmDic : vouTypeArray) {
            if (!BizPubParm.VOU_TYPE_1.equals(parmDic.getOptCode())
                    && !BizPubParm.VOU_TYPE_2.equals(parmDic.getOptCode())) {
                if (parmDic.getOptCode().equals(vouType)) {
                    continue;
                }
                op = new OptionsList();
                op.setOptionLabel(parmDic.getOptName());
                op.setOptionValue(parmDic.getOptCode());
                vouTypeList.add(op);
            }
        }
        pledgeBaseInfo.setKeepStatus(CollateralConstant.REGIST);// 登记
        pledgeBaseInfo.setRefFlag("0");// 是否被关联
        pledgeBaseInfo.setDelflag("0");// 未删除
        pledgeBaseInfo.setCusNo(cusNo);
        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
                .getByPledgeImPawnType(mfCollateralClass.getClassId() + "-1", "PledgeBaseInfoAction", "");
        if (mfCollateralFormConfig != null) {
            String formId = mfCollateralFormConfig.getAddModelDef();
            if (StringUtil.isEmpty(formId)) {
                // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
                // "的PledgeBaseInfoAction表单信息没有查询到");
            } else {
                FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
                if (formdlpledgebaseinfo0004.getFormId() == null) {
                    // 子版表单意外丢失，使用母版。避免前台表单空白或报错。
                    formId = mfCollateralFormConfig.getAddModel();
                    formdlpledgebaseinfo0004 = formService.getFormData(formId);
                    // logger.error("押品类型为" +
                    // mfCollateralFormConfig.getFormType() +
                    // "的PledgeBaseInfoAction表单form"
                    // + formId + ".xml文件不存在");
                }
                getFormValue(formdlpledgebaseinfo0004);
                getObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeList);
                MfCusCustomer mfCusCustomer1 = new MfCusCustomer();
                mfCusCustomer1.setCusNo(cusNo);
                mfCusCustomer1 = mfCusCustomerFeign.getById(mfCusCustomer1);
                this.changeFormProperty(formdlpledgebaseinfo0004, "cusName", "initValue", mfCusCustomer1.getCusName());
                model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
            }
        }
        // 客户选择组件
        JSONObject json = new JSONObject();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        List<MfCusCustomer> cusList = new ArrayList<MfCusCustomer>();
        cusList.add(mfCusCustomer);
        JSONArray cusArray = JSONArray.fromObject(cusList);
        for (int i = 0; i < cusArray.size(); i++) {
            cusArray.getJSONObject(i).put("id", cusArray.getJSONObject(i).getString("cusNo"));
            // 新版组件修改
            cusArray.getJSONObject(i).put("name", cusArray.getJSONObject(i).getString("cusName"));
        }
        json.put("cus", cusArray);
        JSONArray collClassArray = JSONArray.fromObject(list);
        for (int i = 0; i < collClassArray.size(); i++) {
            collClassArray.getJSONObject(i).put("id", collClassArray.getJSONObject(i).getString("classId"));
            collClassArray.getJSONObject(i).put("name", collClassArray.getJSONObject(i).getString("classSecondName"));
            collClassArray.getJSONObject(i).put("classFirstNo", collClassArray.getJSONObject(i).getString("classFirstNo"));
        }
        json.put("collClass", collClassArray);
        String ajaxData = json.toString();

        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("caseId", caseId);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("ifPrivateAssets", ifPrivateAssets);
        model.addAttribute("query", "");
        return "/component/collateral/PledgeBaseInfo_inputByCusNo";
    }

    /**
     * ajax 异步显示押品信息表单
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/freshPledgeFormAjax")
    @ResponseBody
    public Map<String, Object> freshPledgeFormAjax(String ajaxData, String classId, String busModelNo, String
            appId, String classFirstNo, String entrFlag) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCollateralClass mfCollateralClass = new MfCollateralClass();
            if (StringUtil.isEmpty(classFirstNo)) {
                classFirstNo = "A,B,C,D";
            }
            mfCollateralClass.setClassFirstNoList(Arrays.asList(classFirstNo.split(",")));
            mfCollateralClass.setUseFlag(CollateralConstant.USED);
            List<MfCollateralClass> list = mfCollateralClassFeign.getAll(mfCollateralClass);
            // 当前的押品类别
            mfCollateralClass = new MfCollateralClass();
            for (MfCollateralClass mcc : list) {
                if (mcc.getClassId().equals(classId)) {
                    mfCollateralClass = mcc;
                    break;
                }
            }
            dataMap = getMapByJson(ajaxData);
            //默认担保方式处理
            String pledgeMethod = dataMap.get("pledgeMethod").toString();
            String[] vouArray = {pledgeMethod};
            if (StringUtil.isEmpty(pledgeMethod)) {// 如果页面没有传来默认的担保方式
                vouArray = mfCollateralClass.getVouType().split("\\|");
                pledgeMethod = vouArray[0];
//				for (String str : vouArray) {
//					vouTypes += str + ",";
//				}
//				vouTypes = vouTypes.substring(0, vouTypes.length() - 1);
            }

            //处理表单信息
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            if (StringUtil.isNotEmpty(appId)) {
                MfBusApply mfBusApply = new MfBusApply();
                mfBusApply.setAppId(appId);
                mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
                if (mfBusApply != null) {
                    pledgeBaseInfo.setCusName(mfBusApply.getCusName());
                    dataMap.put("cusName", mfBusApply.getCusName());
                    pledgeBaseInfo.setCusNo(mfBusApply.getCusNo());
                }
            }
            String oldFormId = String.valueOf(dataMap.get("formId"));
            FormData formdlpledgebaseinfo0004 = formService.getFormData(oldFormId);
            getObjValue(formdlpledgebaseinfo0004, dataMap);
            setObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);

            pledgeBaseInfo.setClassId(mfCollateralClass.getClassId());
            pledgeBaseInfo.setClassFirstNo(mfCollateralClass.getClassFirstNo());
            pledgeBaseInfo.setClassSecondName(mfCollateralClass.getClassSecondName());
            pledgeBaseInfo.setKeepStatus(CollateralConstant.REGIST);// 登记
            pledgeBaseInfo.setRefFlag("0");// 是否被关联
            pledgeBaseInfo.setDelflag("0");// 未删除
            pledgeBaseInfo.setPledgeMethod(pledgeMethod);

            dataMap = new HashMap<String, Object>();
            // 这里得到的formId是带form字符串的，比如formcuscorp0001
            MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId + "-1", "PledgeBaseInfoAction", "");
            String formId = null;
            if (mfCollateralFormConfig != null) {
                formId = mfCollateralFormConfig.getAddModelDef();
                if (StringUtil.isEmpty(formId)) {
                } else {
                    formdlpledgebaseinfo0004 = formService.getFormData(formId);
                    if (formdlpledgebaseinfo0004.getFormId() == null) {
                        dataMap.put("flag", "error");
                        dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("表单：form" + formId));
                        return dataMap;
                    }
                }
            }
            formdlpledgebaseinfo0004 = formService.getFormData(formId);
            getObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
            //担保方式列表
            Map<String, String> vouTypeMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
            Map<String, String> dicMap = new HashMap<String, String>();
            List<ParmDic> vouTypeArray = new ArrayList<ParmDic>();
            MfBusAppKind mfBusAppKind = new MfBusAppKind();
            String vouTypeBus = "";
            if (StringUtil.isNotEmpty(appId)) {
                mfBusAppKind.setAppId(appId);
                mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
                if(mfBusAppKind!=null){
                    vouTypeBus = mfBusAppKind.getVouType();
                }
            }
            for (int k = 0; k < list.size(); k++) {
                MfCollateralClass mfCollateralClassTmp = new MfCollateralClass();
                mfCollateralClassTmp = list.get(k);
                String[] vouTypesTmp = mfCollateralClassTmp.getVouType().split("\\|");
                for (String type : vouTypesTmp) {
                    if (!dicMap.containsKey(type)) {
                        dicMap.put(type, type);
                        ParmDic parmDic = new ParmDic();
                        parmDic.setOptCode(type);
                        parmDic.setOptName(vouTypeMap.get(type));
                        if (StringUtil.isNotEmpty(appId)) {
                            if(vouTypeBus.contains(type)){
                                vouTypeArray.add(parmDic);
                            }
                        }else{
                            vouTypeArray.add(parmDic);
                        }
                    }
                }
            }

            List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
            OptionsList op = new OptionsList();
            op.setOptionLabel(vouTypeMap.get(pledgeMethod));
            op.setOptionValue(pledgeMethod);
            vouTypeList.add(op);
            String vouTypes = "";
            if ("collateral".equals(entrFlag)) {
                for (ParmDic parmDic : vouTypeArray) {
                    vouTypes = vouTypes + parmDic.getOptCode() + "|";
                    if (parmDic.getOptCode().equals(pledgeMethod)) {
                        continue;
                    }
                    op = new OptionsList();
                    op.setOptionLabel(parmDic.getOptName());
                    op.setOptionValue(parmDic.getOptCode());
                    vouTypeList.add(op);
                }
            }
            this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeList);

            // 资产类别选择组件
            JSONArray collClassArray = JSONArray.fromObject(list);
            if ("".equals(busModelNo)) {
                for (int i = 0; i < collClassArray.size(); i++) {
                    collClassArray.getJSONObject(i).put("id", collClassArray.getJSONObject(i).getString("classId"));
                    collClassArray.getJSONObject(i).put("name", collClassArray.getJSONObject(i).getString("classSecondName"));
                }
            } else {
                collClassArray = mfBusCollateralRelFeign.getCollClassByBusModel(busModelNo);
            }
            // 根据担保方式过滤押品类别
            JSONArray collClassArrayNew = new JSONArray();
            for (int i = 0; i < collClassArray.size(); i++) {
                String vouTypeTmp = collClassArray.getJSONObject(i).getString("vouType");
                if (vouTypeTmp.indexOf(pledgeMethod) != -1||StringUtil.isEmpty(vouTypeTmp)) {
                    collClassArrayNew.add(collClassArray.getJSONObject(i));
                }
            }
            dataMap.put("classId", classId);
            dataMap.put("collClass", collClassArrayNew);
            //根据产品设置过滤押品类别
            if (StringUtil.isNotEmpty(appId)) {
                MfBusApply mfBusApply = new MfBusApply();
                mfBusApply.setAppId(appId);
                mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
                if (mfBusApply != null) {
                    JSONArray collClassArrayKind = new JSONArray();
                    String kindNo = mfBusApply.getKindNo();
                    MfSysKind mfSysKind = new MfSysKind();
                    mfSysKind.setKindNo(kindNo);
                    mfSysKind = mfSysKindFeign.getById(mfSysKind);
                    String assetClass =mfSysKind.getAssetClass();
                    if(StringUtil.isNotEmpty(assetClass)){
                        for (int i = 0; i < collClassArrayNew.size(); i++) {
                            JSONObject mfCollateralClass2 = (JSONObject) collClassArrayNew.get(i);
                            //押品类别的担保方式等于当前担保方式范围之内的
                            if (assetClass.indexOf(mfCollateralClass2.getString("id")) != -1) {
                                collClassArrayKind.add(mfCollateralClass2);
                                dataMap.put("classId", mfCollateralClass2.getString("id"));
                            }
                        }
                        dataMap.put("collClass", collClassArrayKind);
                    }
                }
            }
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String formHtml = jsonFormUtil.getJsonStr(formdlpledgebaseinfo0004, "bootstarpTag", "");
            dataMap.put("flag", "success");
            dataMap.put("formId", formId);
            dataMap.put("formHtml", formHtml);
            dataMap.put("vouType", vouTypes);
            String companyId = PropertiesUtil.getSysParamsProperty("sys.project.name");
            dataMap.put("companyId", companyId);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }

        return dataMap;
    }

    @RequestMapping("/getPledgeShowNoAjax")
    @ResponseBody
    public Map<String, Object> getPledgeShowNoAjax(String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            // 调用规则引擎生成押品展示编号
            String pledgeShowNo = WaterIdUtil.getWaterId();
            NumberBigBean numberBigBean = new NumberBigBean();
            numberBigBean.setCusNo(cusNo);
            numberBigBean.setNoType(BizPubParm.NO_TYPE_COLLATERAL);
            numberBigBean = rulesInterfaceFeign.getNumberBigBean(numberBigBean);
            if (numberBigBean.getResultNo() != null && !"".equals(numberBigBean.getResultNo())) {
                pledgeShowNo = numberBigBean.getResultNo();
            }
            dataMap.put("flag", "success");
            dataMap.put("pledgeShowNo", pledgeShowNo);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }

        return dataMap;
    }

    @RequestMapping("/getPledgeBaseInfoAjax")
    @ResponseBody
    public Map<String, Object> getPledgeBaseInfoAjax(String ajaxData, String pledgeNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setPledgeNo(pledgeNo);
            pledgeBaseInfo = pledgeBaseInfoFeign.getPledgeBaseInfo(pledgeBaseInfo);
            if (pledgeBaseInfo != null) {
                dataMap.put("flag", "success");
                dataMap.put("pledgeBaseInfo", pledgeBaseInfo);
            } else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }

        return dataMap;
    }

    @RequestMapping("/getCarCheckDetail")
    public String getCarCheckDetail(Model model, String appId, String cusNo, String query) throws Exception {
        try {
            query = "";
            request.setAttribute("ifBizManger", "3");
            request.setAttribute("query", query);
            String pledgeNo = null;
            PledgeBaseInfo pledgeBaseInfo = null;
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            String pledgeMethod = mfBusApply.getVouType();
            // 判断客户表单信息是否允许编辑
            List<PledgeBaseInfo> pledgeBaseInfoList = collateralInterfaceFeign.getPledgeBaseInfoListByAppId(appId);
            List<MfCollateralTable> mfCollateralTables = new ArrayList<MfCollateralTable>();
            if (pledgeBaseInfoList.size() > 0 && pledgeBaseInfoList != null) {
                pledgeNo = pledgeBaseInfoList.get(0).getPledgeNo();
                pledgeBaseInfo = new PledgeBaseInfo();
                pledgeBaseInfo.setPledgeNo(pledgeNo);
                pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
            }
            mfCollateralTables = getDyFormHtml(pledgeNo, pledgeBaseInfo, query);
            String ajaxData = new Gson().toJson(mfCollateralTables);
            model.addAttribute("cusNo", cusNo);
            model.addAttribute("appId", appId);
            model.addAttribute("pledgeMethod", pledgeMethod);
            model.addAttribute("pledgeNo", pledgeNo);
            model.addAttribute("ajaxData", ajaxData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "/component/collateral/MfCollateralTable_CarCheck";
    }

    @SuppressWarnings("unchecked")
    public List<MfCollateralTable> getDyFormHtml(String pledgeNo, PledgeBaseInfo pledgeBaseInfo, String query)
            throws Exception {
        // 查询已经录入信息的表单
        FormService formService = new FormService();
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        JsonTableUtil jtu = new JsonTableUtil();
        String tableId = "";
        String formId = "";
        List<MfCollateralTable> mfCollateralTableList = new ArrayList<MfCollateralTable>();

        MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
        mfCollateralFormConfig.setFormType("D12");
        List<MfCollateralFormConfig> collateralFormConfigList = mfCollateralFormConfigFeign
                .getByFormType(mfCollateralFormConfig);
        for (int i = 0; i < collateralFormConfigList.size(); i++) {
            MfCollateralFormConfig collateralFormConfig = collateralFormConfigList.get(i);
            MfCollateralTable mfCollateralTable = new MfCollateralTable();
            mfCollateralTable.setDelFlag(BizPubParm.YES_NO_N);
            mfCollateralTable.setDataFullFlag(BizPubParm.YES_NO_N);
            if ("0".equals(collateralFormConfig.getUseFlag())) {
                continue;
            }
            String action = collateralFormConfigList.get(i).getAction();
            String htmlStr = "";
            tableId = "table" + collateralFormConfigList.get(i).getShowModelDef();
            formId = collateralFormConfigList.get(i).getShowModelDef();
            FormData formcommon = formService.getFormData(formId);
            if (formcommon.getFormId() == null) {
                if (formId.indexOf("_") != -1) {
                    Integer num = formId.indexOf("_");
                    formId = formId.substring(0, num);
                }
            }
            if ("InsInfoAction".equals(action)) {// table 保险
                if (StringUtil.isNotEmpty(pledgeNo)) {
                    InsInfo insInfo = new InsInfo();
                    insInfo.setCollateralId(pledgeNo);
                    Ipage ipage = this.getIpage();
                    ipage.setParams(this.setIpageParams("insInfo", insInfo));
                    List<InsInfo> list = (List<InsInfo>) insInfoFeign.findByPage(ipage).getResult();
                    if (list != null && list.size() > 0) {
                        ipage.setParams(this.setIpageParams("insInfo", insInfo));
                        htmlStr = jtu.getJsonStr(tableId, "tableTag", list, null, true);
                        mfCollateralTable.setDataFullFlag(BizPubParm.YES_NO_Y);
                    }
                }
                mfCollateralTable = assignment(collateralFormConfig, mfCollateralTable);
                mfCollateralTable.setHtmlStr(htmlStr);
                mfCollateralTableList.add(mfCollateralTable);
            } else if ("PledgeBaseInfoAction".equals(action)) { // 车辆信息
                if (StringUtil.isNotEmpty(pledgeNo)) {
                    formcommon = formService.getFormData(formId);
                    getFormValue(formcommon);
                    if (pledgeBaseInfo != null) {
                        searchClassSecondName(pledgeBaseInfo);
                        getObjValue(formcommon, pledgeBaseInfo);
                        htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
                        mfCollateralTable.setDataFullFlag(BizPubParm.YES_NO_Y);
                    }
                }
                mfCollateralTable = assignment(collateralFormConfig, mfCollateralTable);
                mfCollateralTable.setTableDes("车辆信息");
                mfCollateralTable.setHtmlStr(htmlStr);
                mfCollateralTableList.add(mfCollateralTable);
            } else if ("MfBusGpsRegAction".equals(action)) { // GPS安装
                if (StringUtil.isNotEmpty(pledgeNo)) {
                    MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
                    mfBusGpsReg.setRelNo(pledgeNo);
                    Ipage ipage = this.getIpage();
                    ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsReg));
                    List<MfBusGpsReg> mfBusGpsReglist = (List<MfBusGpsReg>) mfBusGpsRegFeign.findByPage(ipage)
                            .getResult();
                    if (mfBusGpsReglist != null && mfBusGpsReglist.size() > 0) {
                        ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsReg));
                        htmlStr = jtu.getJsonStr(tableId, "tableTag", mfBusGpsReglist, null, true);
                        mfCollateralTable.setDataFullFlag(BizPubParm.YES_NO_Y);
                    }
                }
                mfCollateralTable = assignment(collateralFormConfig, mfCollateralTable);
                mfCollateralTable.setHtmlStr(htmlStr);
                mfCollateralTableList.add(mfCollateralTable);
            } else if ("MfCarCheckFormAction".equals(action)) { // 验车单
                if (StringUtil.isNotEmpty(pledgeNo)) {
                    formcommon = formService.getFormData("CarCheckFormBase");
                    getFormValue(formcommon);
                    MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
                    mfCarCheckForm.setPledgeNo(pledgeNo);
                    mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
                    if (mfCarCheckForm != null) {
                        getObjValue(formcommon, mfCarCheckForm);
                        htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
                        mfCollateralTable.setDataFullFlag(BizPubParm.YES_NO_Y);
                    }
                }
                mfCollateralTable = assignment(collateralFormConfig, mfCollateralTable);
                mfCollateralTable.setTableDes("验车单");
                mfCollateralTable.setHtmlStr(htmlStr);
                mfCollateralTableList.add(mfCollateralTable);
            }else {
            }
        }
        return mfCollateralTableList;
    }

    public MfCollateralTable assignment(MfCollateralFormConfig mfCollateralFormConfig,
                                        MfCollateralTable mfCollateralTable) {
        mfCollateralTable.setTableName(mfCollateralFormConfig.getTableName());
        mfCollateralTable.setTableDes(mfCollateralFormConfig.getTableDes());
        mfCollateralTable.setAction(mfCollateralFormConfig.getAction());
        mfCollateralTable.setAddModelDef(mfCollateralFormConfig.getAddModelDef());
        mfCollateralTable.setShowModelDef(mfCollateralFormConfig.getShowModelDef());
        mfCollateralTable.setListModel(mfCollateralFormConfig.getListModel());
        mfCollateralTable.setShowType(mfCollateralFormConfig.getShowType());
        return mfCollateralTable;
    }

    /**
     * 方法描述： 提交车管验车到下一步流程
     *
     * @param
     * @return
     * @throws ServiceException Result
     * @author 段泽宇
     * @date 2018年7月12日 下午19:22:22
     */
    @RequestMapping(value = "/submitForm")
    @ResponseBody
    public Map<String, Object> submitForm(Model model, String pledgeNo, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            if (StringUtil.isEmpty(pledgeNo)) {
                String infoName = "车辆的信息";
                dataMap.put("flag", "checkError");
                dataMap.put("msg", MessageEnum.FIRST_COMPLETE_INFORMAATION.getMessage(infoName));
                return dataMap;
            }
            MfCollateralTable mfCollateralTable = new MfCollateralTable();
            MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
            mfCollateralFormConfig.setFormType("D12");
            List<MfCollateralFormConfig> mfCollateralFormConfigs = mfCollateralFormConfigFeign
                    .getByFormType(mfCollateralFormConfig);
            for (int i = 0; i < mfCollateralFormConfigs.size(); i++) {
                MfCollateralFormConfig mfCollateralFormConfig2 = mfCollateralFormConfigs.get(i);
                mfCollateralTable = new MfCollateralTable();
                mfCollateralTable.setTableName(mfCollateralFormConfig2.getTableName());
                mfCollateralTable.setCollateralNo(pledgeNo);
                mfCollateralTable.setCollateralType("D12");
                mfCollateralTable = mfCollateralTableFeign.getById(mfCollateralTable);
                String tableName = mfCollateralFormConfig2.getTableName();
                if ("mf_bus_gps_reg".equals(tableName) || "mf_car_check_form".equals(tableName)
                        || "pledge_base_info".equals(tableName) || "ins_info".equals(tableName)) {
                    if (("1").equals(mfCollateralFormConfig2.getIsMust()) &&
                            mfCollateralTable != null && ("0").equals(mfCollateralTable.getDataFullFlag())) {
                        dataMap.put("msg", "车管验车中的" + mfCollateralFormConfig2.getTableDes() + "信息不能为空");
                        dataMap.put("flag", "checkError");
                        return dataMap;
                    }
                }
            }

            Result result = pledgeBaseInfoFeign.doCommit(appId, User.getRegNo(request));
            //更新订单状态
            MfBusApply paramBean = new MfBusApply();
            paramBean.setAppId(appId);
            appInterfaceFeign.updateApply(paramBean);

            if (result.isSuccess()) {
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SUBMIT_TONEXT.getMessage(result.getMsg()));
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
     * @param caseId
     * @return Map<String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                               Object>
     * @方法描述： 根据案件编号获取相关押品
     * @author 仇招
     * @date 2018年9月26日 上午10:49:13
     */
    @RequestMapping(value = "/getListByCaseIdAjax")
    @ResponseBody
    public Map<String, Object> getListByCaseIdAjax(String caseId) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setCaseId(caseId);
            List<PledgeBaseInfo> pledgeBaseInfoList = pledgeBaseInfoFeign.getListByCaseId(pledgeBaseInfo);
            JSONArray pledgeBaseInfoMap = new JSONArray();
            for (int i = 0; i < pledgeBaseInfoList.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("id", pledgeBaseInfoList.get(i).getPledgeNo());
                obj.put("name", pledgeBaseInfoList.get(i).getPledgeName());
                pledgeBaseInfoMap.add(obj);
            }
            dataMap.put("pledgeBaseInfoMap", pledgeBaseInfoMap);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
        }
        return dataMap;
    }

    /**
     * @param cusNo
     * @param
     * @return Map<String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                               Object>
     * @方法描述： 根据客户号和资产属性查找押品列表
     * @author 仇招
     * @date 2018年9月27日 下午3:36:10
     */
    @RequestMapping(value = "/getListByCusNoAndAssetPropertyAjax")
    @ResponseBody
    public Map<String, Object> getListByCusNoAndAssetPropertyAjax(String cusNo, String assetProperty) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setCusNo(cusNo);
            pledgeBaseInfo.setAssetProperty(assetProperty);
            List<PledgeBaseInfo> pledgeBaseInfoList = pledgeBaseInfoFeign.getListByCusNoAndAssetProperty(pledgeBaseInfo);
            JSONArray pledgeBaseInfoMap = new JSONArray();
            for (int i = 0; i < pledgeBaseInfoList.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("id", pledgeBaseInfoList.get(i).getPledgeNo());
                obj.put("name", pledgeBaseInfoList.get(i).getPledgeName());
                pledgeBaseInfoMap.add(obj);
            }
            dataMap.put("pledgeBaseInfoMap", pledgeBaseInfoMap);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
        }
        return dataMap;
    }

    /**
     * @param
     * @param
     * @return Map<String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                               Object>
     * @方法描述： 根据选择的押品清单验证申请金额是否符合要求
     * @author ywh
     * @date 2019年05月20日 11:14
     */
    @RequestMapping(value = "/verificationAppAmt")
    @ResponseBody
    public Map<String, Object> verificationAppAmt(String pledgeBillNo, String appAmt, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String creditProjectId = request.getParameter("creditProjectId");
        Double pledgeBillValue = 0.00;
        Double loanRatio = 1.0;
        appAmt = appAmt.replaceAll(",", "");
        if (StringUtil.isNotEmpty(pledgeBillNo)) {
            String[] pledgeBillNos = pledgeBillNo.split("\\|");
            for (int i = 0; i < pledgeBillNos.length; i++) {
                String billNo = pledgeBillNos[i];
                PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
                pledgeBaseInfoBill.setPledgeBillNo(billNo);
                pledgeBaseInfoBill = pledgeBaseInfoBillFeign.getById(pledgeBaseInfoBill);
                if (pledgeBaseInfoBill != null) {
                    if (pledgeBaseInfoBill.getUnitPrice() != null && pledgeBaseInfoBill.getCount() != null) {
                        //调用规则计算货值
                        Map<String, Object> rulesMap = new HashMap<String, Object>();
                        rulesMap.put("ruleName", BizPubParm.PLEDGE_VALUE_RULES_NO);
                        rulesMap.put("minUnitPrice", pledgeBaseInfoBill.getMinUnitPrice());
                        rulesMap.put("singleNum", pledgeBaseInfoBill.getSingleNum());
                        rulesMap.put("count", pledgeBaseInfoBill.getCount());
                        rulesMap.put("unitPrice", pledgeBaseInfoBill.getUnitPrice());
                        rulesMap.put("clacType", pledgeBaseInfoBill.getClacType());
                        Map<String, Object> resultMap = rulesInterfaceFeign.getPleValue(rulesMap);
                        if (resultMap != null && resultMap.containsKey("pleValue")) {
                            pledgeBillValue = MathExtend.add(pledgeBillValue, (Double) resultMap.get("pleValue"));
                        }
                    }
                }
            }
            //获取该客户的授信合同
            if (StringUtil.isNotEmpty(creditProjectId)) {
                MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                mfCusCreditContract.setId(creditProjectId);
                mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
                if (mfCusCreditContract != null && mfCusCreditContract.getLoanRatio() != null) {
                    loanRatio = mfCusCreditContract.getLoanRatio();
                }
            }
            dataMap.put("sumBillValue", pledgeBillValue);
            pledgeBillValue = MathExtend.multiply(pledgeBillValue, loanRatio);
            dataMap.put("billNoFlag", 0);
            dataMap.put("pledgeBillValue", pledgeBillValue);
            if (StringUtil.isNotEmpty(appAmt)) {
                int low = MathExtend.comparison(appAmt, String.valueOf(pledgeBillValue));
                if (low <= 0) {
                    dataMap.put("billNoFlag", 0);
                    dataMap.put("pledgeBillValue", pledgeBillValue);
                } else {
                    dataMap.put("billNoFlag", 1);
                    dataMap.put("pledgeBillValue", pledgeBillValue);
                    dataMap.put("msg", "申请金额不能大于货值金额*" + loanRatio * 100 + "%");
                }
            }
        } else {
            dataMap.put("billNoFlag", 0);
            dataMap.put("pledgeBillValue", 0);
        }
        dataMap.put("flag", "success");
        return dataMap;
    }

    /**
     * @param ajaxData
     * @param cusNo
     * @param pledgeMethod
     * @param pageNo
           根据客户号查询押品信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/getPledgePageByCusNo")
    @ResponseBody
    public Map<String, Object> getPledgePageByCusNo(String ajaxData, String cusNo, String pledgeMethod,
                                                    Integer pageNo, String refFlag,
                                                    String keepStatus) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setCustomQuery(ajaxData);
            pledgeBaseInfo.setCustomSorts(ajaxData);
            pledgeBaseInfo.setCriteriaList(pledgeBaseInfo, ajaxData);
            pledgeBaseInfo.setCusNo(cusNo);
            pledgeBaseInfo.setPledgeMethod(pledgeMethod);
            pledgeBaseInfo.setRefFlag(refFlag);
            pledgeBaseInfo.setKeepStatus(keepStatus);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setParams(this.setIpageParams("pledgeBaseInfo", pledgeBaseInfo));
            ipage = pledgeBaseInfoFeign.getPledgePageByCusNo(ipage);
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
     * @param cityCode
     * @param communityName
     * @return Map<String       ,       Object>
     * @方法描述： 根据城市编号和小区名字查询匹配的小区
     * @author cd
     * @date 2019年8月20日 下午8:20:10
     */
    @RequestMapping(value = "/selectResidentialarea")
    @ResponseBody
    public Map<String, Object> selectResidentialarea(String cityCode, String communityName) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> requestMap = new HashMap<String, Object>();
        try {
            String ifValue = "1";//1.有值；2.无值；0.未知错误
            paramMap.put("cityCode", cityCode);
            paramMap.put("communityName", communityName);
            log.info("####调用**接口开始####");
            log.info("请求参数" + gson.toJson(paramMap));
            requestMap = funGuGuFeign.request3101(paramMap);
            log.info("返回结果" + gson.toJson(requestMap));
            Map<String, String> headMap = (Map<String, String>) requestMap.get("Head");
            log.info("#######" + gson.toJson(requestMap) + "#######");
            if ("0000".equals(headMap.get("ResCode"))) {
                Object body = requestMap.get("Body");
                if (null == body) {
                    dataMap.put("ifValue", "2");
                    return dataMap;
                }
                String gsonStr = gson.toJson(requestMap.get("Body"));
                log.info("####data#####" + gsonStr);
                dataMap.put("ifValue", "1");
                dataMap.put("gsonStr", gsonStr);
            } else {
                dataMap.put("msg", headMap.get("ResMsg"));
                dataMap.put("ifValue", "0");
                return dataMap;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            dataMap.put("flag", "error");
            dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
            throw e;
        }
        return dataMap;

    }


    /**
     * @param cityCode
     * @param communityID
     * @param buildingID
     * @param unitID
     * @return Map<String       ,       Object>
     * @方法描述： 根据城市编号和小区编号查询符合楼栋、单元、户
     * @author cd
     * @date 2019年8月20日 下午8:20:10
     */
    @RequestMapping(value = "/selectHouseInfo")
    @ResponseBody
    public Map<String, Object> selectHouseInfo(String cityCode, String communityID, String buildingID, String unitID) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> requestMap = new HashMap<String, Object>();
        String ifValue = "1";//1.有值；2.无值；0.未知错误
        try {
            paramMap.put("cityCode", cityCode);
            paramMap.put("communityID", communityID);
            if (!"".equals(buildingID) && null != buildingID) {
                paramMap.put("buildingID", buildingID);
            }
            if (!"".equals(unitID) && null != unitID) {
                paramMap.put("unitID", unitID);
            }
            log.info("####调用**接口开始####");
            log.info("请求参数" + gson.toJson(paramMap));
            requestMap = funGuGuFeign.request3102(paramMap);
            log.info("返回结果" + gson.toJson(requestMap));
            Map<String, String> headMap = (Map<String, String>) requestMap.get("Head");
            log.info("#######" + gson.toJson(requestMap) + "#######");
            if ("0000".equals(headMap.get("ResCode"))) {
                Object body = requestMap.get("Body");
                if (body instanceof Map) {
                    Map<String, Object> bodyMap = (Map<String, Object>) body;
                    String gsonStr = gson.toJson(bodyMap.get("List"));
                    dataMap.put("ifValue", "1");
                    dataMap.put("gsonStr", gsonStr);
                } else {
                    dataMap.put("ifValue", "2");
                }
            } else {
                dataMap.put("msg", headMap.get("ResMsg"));
                dataMap.put("ifValue", "0");
                return dataMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
            throw e;
        }
        return dataMap;
    }


    /**
     * @param gsonStr
     * @param dataType
     * @return Map<String       ,       Object>
     * @方法描述： 解析数据封装成弹出数据
     * @author cd
     * @date 2019年9月7日 下午17:20:10
     */
    @RequestMapping(value = "/analysisData")
    @ResponseBody
    public Map<String, Object> analysisData(String gsonStr, String dataType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Ipage ipage = this.getIpage();
        log.info("####接收data#####" + gsonStr);
        List<Map<String, Object>> listMap = gson.fromJson(gsonStr, new TypeToken<List<Map<String, Object>>>() {
                }.getType()
        );
        log.info("####开始封装#####" + listMap);
        if (listMap.size() > 0) {
            ipage.setPageSize(listMap.size());
            List<LinkedHashMap<String, String>> linkedHashMaps = new LinkedList<LinkedHashMap<String, String>>();
            for (int i = 0; i < listMap.size(); i++) {
                LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
                if ("c".equals(dataType)) {//小区（c）
                    linkedHashMap.put("communityName", (String) listMap.get(i).get("residentialareaName"));
                    linkedHashMap.put("communityId", (String) listMap.get(i).get("communityID"));
                    linkedHashMap.put("extOstr15", (String) listMap.get(i).get("districtName"));
                } else if ("b".equals(dataType)) {//楼栋（b）
                    linkedHashMap.put("buildingName", (String) listMap.get(i).get("name"));
                    linkedHashMap.put("buildingId", (String) listMap.get(i).get("GUID"));
                } else if ("u".equals(dataType)) {//单元（u）
                    linkedHashMap.put("unitName", (String) listMap.get(i).get("name"));
                    linkedHashMap.put("unitId", (String) listMap.get(i).get("GUID"));
                } else {//户（h）
                    linkedHashMap.put("houseName", (String) listMap.get(i).get("name"));
                    linkedHashMap.put("houseId", (String) listMap.get(i).get("GUID"));
                }
                linkedHashMaps.add(i, linkedHashMap);
            }
            ipage.initPageCounts(new Integer[]{(int) linkedHashMaps.size()});// 初始化分页类
            ipage.setResult(linkedHashMaps);
            dataMap.put("ipage", ipage);
            log.info("####封装成功####");
        }
        return dataMap;

    }
    /**
     * 房产在线估值 对接调用首佳接口
     */
    @RequestMapping(value = "/onlineAssessment")
    @ResponseBody
   public Map<String, Object> onlineAssessment(String ajaxData , String artificial, String pledgeNo) throws  Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> requestMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        try {
            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
            getFormValue(formdlpledgebaseinfo0004, map);
            if (this.validateFormData(formdlpledgebaseinfo0004)) {
                FormActive[] list = formdlpledgebaseinfo0004.getFormActives();
                for (int i = 0; i < list.length; i++) {//数据字典转字符串
                    FormActive formActive = list[i];
                    if (null != formActive.getFieldSize()) {
                        String value = formActive.getInitValue();
                        if("HOUSE_TYPE".equals(formActive.getFieldSize())){
                            if("10008".equals(value)){
                                list[i].setInitValue("别墅");
                            }
                            if("10009".equals(value)){
                                list[i].setInitValue("住宅");
                            }
                            continue;
                        }
                        if (!"".equals(value) || null != value) {
                            CodeUtils cu = new CodeUtils();
                            Map<String, String> cuMap = cu.getMapByKeyName(formActive.getFieldSize());
                            value = cuMap.get(value);
                            list[i].setInitValue(value);
                        }
                    }
                }
                PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                setObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                paramMap.put("AreaCode", pledgeBaseInfo.getCityCode());//国际码
                paramMap.put("Address", pledgeBaseInfo.getAddress());//产证地址
                paramMap.put("ProjectName", pledgeBaseInfo.getPleAddress());//小区名称
                paramMap.put("ProjectName", pledgeBaseInfo.getPleAddress());//小区名称
                paramMap.put("PropertyType", "92");
                paramMap.put("ThirdProperty", "93");
                paramMap.put("TypeCode", "5");
                if (!"1".equals(artificial)) {//在线估值
                    log.info("####调用**接口开始####");
                    log.info("请求参数" + gson.toJson(paramMap));
                       String url = ymlConfig.getCloudnew().get("ip")+"/dataPlatformApi/data";
                       String resutlString=  HttpClientUtil.sendPost(paramMap,url);
                      log.info("返回结果" + gson.toJson(resutlString));
                      com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(resutlString);
                      if(("1").equals(jsonObject.get("ReturnCode"))){
                          log.info("####成功####");
                          EvalInfo evalInfo = new EvalInfo();
                          evalInfo.setEvalMethod("03");
                          String totalPrice = (String) jsonObject.get("TotalPrice");//总价
                          double value = new Double(totalPrice);
                          evalInfo.setEvalAmount(value * 10000);
                          String price = (String) jsonObject.get("UnitPrice");//单价
                          double valuesPrice = new Double(price);
                          evalInfo.setEvalPrice(valuesPrice);
                          evalInfo.setEvalDate(DateUtil.getDate());
//                            evalInfo.setMortRate(pledgeBaseInfo.getPledgeRate());//调整为由表单赋值
                          evalInfo.setEvalOrgName("首佳");
                          evalInfo.setRemark("如对评估结果存在异议，请使用人工估值进行二次评估！");
                          FormData formonlineDlevalinfo0002 = formService.getFormData("onlineDlevalinfo0002");
                          getObjValue(formonlineDlevalinfo0002, evalInfo);
                          JsonFormUtil jsonFormUtil = new JsonFormUtil();
                          String htmlStrCorp = jsonFormUtil.getJsonStr(formonlineDlevalinfo0002, "bootstarpTag", "");
                          dataMap.put("evalState", "1");
                          dataMap.put("formHtml", htmlStrCorp);
                      }else {
                          dataMap.put("evalState", "2");
                          dataMap.put("msg", "在线估值未果已转人工申请！");
                          return dataMap;
                      }
                }else{
                    dataMap.put("msg", "估值方式错误！");
                    return dataMap;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
            throw e;
        }
        return dataMap;

    }


    /**
     * @param ajaxData
     * @param artificial
     * @param pledgeNo
     * @return Map<String               ,       Object>
     * @方法描述： 房产在线估值和人工估值申请
     * @author cd
     * @date 2019年8月20日 下午8:20:10
     */
    @RequestMapping(value = "/assessment")
    @ResponseBody
    public Map<String, Object> assessment(String ajaxData, String artificial, String pledgeNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> requestMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        try {
            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
            getFormValue(formdlpledgebaseinfo0004, map);
            if (this.validateFormData(formdlpledgebaseinfo0004)) {
                FormActive[] list = formdlpledgebaseinfo0004.getFormActives();
                for (int i = 0; i < list.length; i++) {//数据字典转字符串
                    FormActive formActive = list[i];
                    if (null != formActive.getFieldSize()) {
                        String value = formActive.getInitValue();
                        if("HOUSE_TYPE".equals(formActive.getFieldSize())){
                            if("10008".equals(value)){
                                list[i].setInitValue("别墅");
                            }
                            if("10009".equals(value)){
                                list[i].setInitValue("住宅");
                            }
                            continue;
                        }
                        if (!"".equals(value) || null != value) {
                            CodeUtils cu = new CodeUtils();
                            Map<String, String> cuMap = cu.getMapByKeyName(formActive.getFieldSize());
                            value = cuMap.get(value);
                            list[i].setInitValue(value);
                        }
                    }
                }
                PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                setObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                paramMap.put("cityCode", pledgeBaseInfo.getCityCode());
                paramMap.put("address", pledgeBaseInfo.getAddress());
                paramMap.put("communityID", pledgeBaseInfo.getCommunityId());
                paramMap.put("buildingArea", String.valueOf(pledgeBaseInfo.getFloorArea()));
                paramMap.put("houseType", pledgeBaseInfo.getHouseType());
                paramMap.put("buildingName", pledgeBaseInfo.getBuildingName());
                paramMap.put("buildingID", pledgeBaseInfo.getBuildingId());
                paramMap.put("unitName", pledgeBaseInfo.getUnitName());
                paramMap.put("unitID", pledgeBaseInfo.getUnitId());
                paramMap.put("houseName", pledgeBaseInfo.getHouseName());
                paramMap.put("houseID", pledgeBaseInfo.getHouseId());
                paramMap.put("communityName", pledgeBaseInfo.getPleAddress());
                paramMap.put("totalFloor", pledgeBaseInfo.getTotalFloor());
                paramMap.put("floor", pledgeBaseInfo.getFloor());
                paramMap.put("roomType", pledgeBaseInfo.getRoomType());
                paramMap.put("toward", pledgeBaseInfo.getToward());
                paramMap.put("buildYear", pledgeBaseInfo.getBuildYear());
                paramMap.put("specialFactors", pledgeBaseInfo.getSpecialFactors());
                if ("1".equals(artificial)) {//人工评值
                    paramMap.put("customPhone", pledgeBaseInfo.getCustomPhone());
                    paramMap.put("floorHeight", pledgeBaseInfo.getFloorHeight());
                    paramMap.put("enquiryReason", pledgeBaseInfo.getEnquiryReason());
                    paramMap.put("obligee", pledgeBaseInfo.getCertificateName());
                    log.info("####调用**接口开始####");
                    log.info("请求参数" + gson.toJson(paramMap));
                    requestMap = funGuGuFeign.request3104(paramMap);
                    log.info("返回结果" + gson.toJson(requestMap));
                    Map<String, String> headMap = (Map<String, String>) requestMap.get("Head");
                    if ("0000".equals(headMap.get("ResCode"))) {
                        log.info("####成功####");
                        Map<String, Object> bodyMap = (Map<String, Object>) requestMap.get("Body");
                        log.info("人工申请" + gson.toJson(bodyMap));
                        String jobID = (String) bodyMap.get("jobID");
                        if (jobID.indexOf("-") != -1) {
                            dataMap.put("msg", "申请人工估值失败");
                            dataMap.put("evalState", "0");
                            return dataMap;
                        }
                        EvalInfo evalInfo = new EvalInfo();
                        evalInfo.setEvalTaskNum((String) bodyMap.get("jobID"));
                        evalInfo.setExt2(pledgeBaseInfo.getCityCode());
                        evalInfo.setExt3((String) bodyMap.get("phoneNumber"));
                        evalInfo.setCollateralId(pledgeNo);
                        evalInfo.setEvalDate(DateUtil.getDate());
                        evalInfo.setEvalId(WaterIdUtil.getWaterId());
                        evalInfo.setMortRate(pledgeBaseInfo.getPledgeRate());
                        evalInfo.setEvalState("2");
                        evalInfo.setExt1("2");//列表显示用
                        evalInfoFeign.insertArtificialApply(evalInfo);
                        dataMap.put("msg", "人工申请成功，请等待结果");
                        log.info("####人工申请成功####");
                        return dataMap;
                    } else {
                        dataMap.put("msg", headMap.get("ResMsg"));
                        return dataMap;
                    }
                } else {//在线估值
                    log.info("####调用**接口开始####");
                    log.info("请求参数" + gson.toJson(paramMap));
                   requestMap = funGuGuFeign.request3103(paramMap);
                    log.info("返回结果" + gson.toJson(requestMap));
                    Map<String, String> headMap = (Map<String, String>) requestMap.get("Head");
                    if ("0000".equals(headMap.get("ResCode"))) {
                        String evalState = "";//估值状态
                        log.info("####成功####");
                        Map<String, Object> bodyMap = (Map<String, Object>) requestMap.get("Body");
                        log.info("在线估值" + gson.toJson(bodyMap));
                        String statusCode = (String) bodyMap.get("statusCode");
                        if ("ENQUIRY-SUCCESS".equalsIgnoreCase(statusCode)) {
                            EvalInfo evalInfo = new EvalInfo();
                            evalInfo.setEvalMethod("03");
                            String totalPrice = (String) bodyMap.get("totalPrice");
                            double value = new Double(totalPrice);
                            evalInfo.setEvalAmount(value * 10000);
                            String price = (String) bodyMap.get("price");
                            double valuesPrice = new Double(price);
                            evalInfo.setEvalPrice(valuesPrice);
                            evalInfo.setEvalDate(DateUtil.getDate());
//                            evalInfo.setMortRate(pledgeBaseInfo.getPledgeRate());//调整为由表单赋值
                            evalInfo.setEvalOrgName("房估估");
                            evalInfo.setRemark("如对评估结果存在异议，请使用人工估值进行二次评估！");
                            FormData formonlineDlevalinfo0002 = formService.getFormData("onlineDlevalinfo0002");
                            getObjValue(formonlineDlevalinfo0002, evalInfo);
                            /*// 设置表单元素不可编辑
                            FormActive[] evalInfoList = formdlevalinfo0002.getFormActives();
                            for (int i = 0; i < evalInfoList.length; i++) {
                                FormActive formActive = evalInfoList[i];
                                if ("validTerm".equals(formActive.getFieldName())) {//评估有效期可以进行填写
                                    continue;
                                }
                                formActive.setReadonly("1");
                                formActive.setOnclick("disabled");
                                if ("evalDate".equals(formActive.getFieldName())) {//设置时间不可点击
                                    evalInfoList[i].setUnit("");
                                }
                            }*/
                            JsonFormUtil jsonFormUtil = new JsonFormUtil();
                            String htmlStrCorp = jsonFormUtil.getJsonStr(formonlineDlevalinfo0002, "bootstarpTag", "");
                            dataMap.put("evalState", "1");
                            dataMap.put("formHtml", htmlStrCorp);
                        } else if ("ENQUIRY-NO-PRICE".equalsIgnoreCase(statusCode)) {
                            dataMap.put("msg", "在线估值未出值！");
                            dataMap.put("evalState", "0");
                            return dataMap;
                        } else {
                            String jobID = (String) bodyMap.get("jobID");
                            if (jobID.indexOf("-") != -1) {
                                dataMap.put("msg", "申请人工估值失败");
                                return dataMap;
                            }
                            dataMap.put("evalTaskNum", (String) bodyMap.get("jobID"));
                            dataMap.put("evalState", "2");
                            dataMap.put("msg", "在线估值未果已转人工申请！");
                            return dataMap;
                        }

                    } else {
                        dataMap.put("msg", headMap.get("ResMsg"));
                        return dataMap;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
            throw e;
        }
        return dataMap;

    }

    /**
     * @param cityCode
     * @param pledgeNo
     * @param evalTaskNum
     * @return Map<String       ,       Object>
     * @方法描述： 房产在线估值未出值，已转人工
     * @author cd
     * @date 2019年8月20日 下午8:20:10
     */
    @RequestMapping(value = "/artificialApplyInsert")
    @ResponseBody
    public Map<String, Object> artificialApplyInsert(String cityCode, String pledgeNo, String evalTaskNum, Double pledgeRate) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        EvalInfo evalInfo = new EvalInfo();
        evalInfo.setEvalTaskNum(evalTaskNum);
        evalInfo.setExt1(cityCode);
        evalInfo.setCollateralId(pledgeNo);
        evalInfo.setEvalDate(DateUtil.getDate());
        evalInfo.setEvalId(WaterIdUtil.getWaterId());
        evalInfo.setMortRate(pledgeRate);
        evalInfo.setEvalState("2");
        evalInfo.setExt1("2");//列表显示用
        evalInfoFeign.insertArtificialApply(evalInfo);
        dataMap.put("msg", "人工申请成功，请等待结果");
        log.info("####已转人工成功####");
        return dataMap;
    }
    /**
     * 打开所有权人列表页面
     * @param model
     * @throws Exception
     */
    @RequestMapping(value = "/getCertificateName")
    public String getCertificateName(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/cus/MfCertificateName_ListForSelect";
    }

    /**
     *打开在保应收账款押品页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListByAccount")
    public String getListByAccount(Model model) throws Exception{
        ActionContext.initialize(request, response);
        return "/component/collateral/pledgeInfo_account";

    }

    /**
     * 展示对应合同的应收账款质押
     * @param appId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/getAccountByAppId")
    @ResponseBody
    public  Map<String, Object> getAccountByAppId(String appId) throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //查询应收账款质押
            String classFirstNo = "C";
            List<PledgeBaseInfo> list = pledgeBaseInfoFeign.getAccountByAppId(appId, classFirstNo);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr("tableaccountInfoBase", "tableTag", list, null, true);
            dataMap.put("tableHtml", tableHtml);
            dataMap.put("flag", "success");
        }catch (Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
           return dataMap;
    }

    /**
     * 查看每个应收账款详情
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getAccountByPledge")
    @ResponseBody
    public Map<String, Object> getAccountByPledge(String pledgeNo) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(pledgeNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        FormData formdlpledgebaseinfo0004 = formService.getFormData("accountBaseInfoBase");
        getObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        String formHtml = jsonFormUtil.getJsonStr(formdlpledgebaseinfo0004, "bootstarpTag", "");
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


    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/updateByOne")
    @ResponseBody
    public Map<String, Object> updateByOne(String ajaxData) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formdlpledgebaseinfo0004 = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formdlpledgebaseinfo0004, getMapByJson(ajaxData));
            if (this.validateFormData(formdlpledgebaseinfo0004)) {
                setObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
                dataMap.put("pledgeBaseInfo", pledgeBaseInfo);
                pledgeBaseInfo.setLstModDate(DateUtil.getDate());
                pledgeBaseInfo.setLstModTime(DateUtil.getDateTime());
                pledgeBaseInfoFeign.update(pledgeBaseInfo);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
     * 方法描述： 导入对应的应收账款
     * @author 例子
     * @date
     */
     @RequestMapping(value = "/importAccountExcel",method=RequestMethod.POST)
     @ResponseBody
    public Result importAccountExcel(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  @RequestParam(value = "file") MultipartFile file,String appId) throws Exception {
         Result result=new Result();
         try {
             String originalFilename = file.getOriginalFilename();
             String type = file.getContentType();
             System.out.println("目标文件名称："+originalFilename+",目标文件类型："+type);
             List<PledgeBaseInfo> list = readExcel(file,originalFilename);
             pledgeBaseInfoFeign.importExcel(list,appId);
            result.setSuccess(true);
         } catch (Exception e) {
             e.printStackTrace();
             result.setSuccess(false);
             result.setMsg("import error!");
         }

         return result;
    }
    public  List<PledgeBaseInfo>   readExcel(MultipartFile file, String fileName) throws Exception{

            FileInputStream fileInputStream = (FileInputStream)file.getInputStream();
            //创建Excel工作薄
            Workbook work = null;
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            if (".xls".equals(fileType)) {
                work = new HSSFWorkbook(fileInputStream);  //2003-
            } else if (".xlsx".equals(fileType)) {
                work = new XSSFWorkbook(fileInputStream);  //2007+
            }
            if (null == work) {
                throw new Exception("创建Excel工作薄为空！");
            }
            Sheet sheet = null;
            Row row = null;
            Cell cell = null;
            //遍历Excel中的sheet
            sheet = work.getSheetAt(0);
            List<PledgeBaseInfo> list=new ArrayList<PledgeBaseInfo>();
            //遍历当前sheet中的所有行
            //包涵头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                //读取一行
                row = sheet.getRow(j);
                //去掉空行和表头
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                pledgeBaseInfo.setPledgeShowNo(getCellValue(row.getCell(0)));//押品编号
                pledgeBaseInfo.setClassSecondName(getCellValue(row.getCell(1)));//押品类别
                pledgeBaseInfo.setPledgeName(getCellValue(row.getCell(2)));//押品名称
                pledgeBaseInfo.setCertificateName(getCellValue(row.getCell(3)));//所有权人名称
                pledgeBaseInfo.setKeepStatus(getCellValue(row.getCell(4)));//押品状态
                pledgeBaseInfo.setExtDstr03(getCellValue(row.getCell(5)));//开票日期
                System.out.println(row.getCell(5).getCellStyle().getDataFormatString());
                pledgeBaseInfo.setPleInvoiceNum(getCellValue(row.getCell(6)));//发票号码
                if(getCellValue(row.getCell(7)).equals("人民币")){
                    pledgeBaseInfo.setExtDic02("CNY");//发票币种
                }else if(getCellValue(row.getCell(7)).equals("日元")){
                    pledgeBaseInfo.setExtDic02("JPY");
                }else if(getCellValue(row.getCell(7)).equals("美元")){
                    pledgeBaseInfo.setExtDic02("USD");
                }else if(getCellValue(row.getCell(7)).equals("欧元")){
                    pledgeBaseInfo.setExtDic02("EUR");
                }else{
                    pledgeBaseInfo.setExtDic02("GBP");
                }
                pledgeBaseInfo.setPleInvoiceAmount(Double.valueOf(getCellValue(row.getCell(8))));    //票面金额
                pledgeBaseInfo.setPleValueDate(getCellValue(row.getCell(9)));//应收账款生效日
                pledgeBaseInfo.setPleExpiryDate(getCellValue(row.getCell(10)));//应收账款到期日
                pledgeBaseInfo.setPleValue(Double.valueOf(getCellValue(row.getCell(11))));//应收账款净值
                if(row.getCell(12) == null ||getCellValue(row.getCell(12)).equals("现金")){
                    pledgeBaseInfo.setExtDic03("1");//支付方式
                }else{
                    pledgeBaseInfo.setExtDic03("2");//银行转账
                }
                if(row.getCell(13) == null){
                  pledgeBaseInfo.setExtOstr07("");
                }else{
                    pledgeBaseInfo.setExtOstr07(getCellValue(row.getCell(13)));//订单编号
                }
                if(row.getCell(14) == null){
                    pledgeBaseInfo.setExtLstr02("");
                }else{
                    pledgeBaseInfo.setExtLstr02(getCellValue(row.getCell(14)));//付款条件
                }
                if(row.getCell(15) == null){
                    pledgeBaseInfo.setExtInt01(0);
                }else{
                    pledgeBaseInfo.setExtInt01(Integer.valueOf(getCellValue(row.getCell(15))));//主折扣天数
                }
                if(row.getCell(16) == null){
                    pledgeBaseInfo.setExtNum03(0.00);
                }else{
                    pledgeBaseInfo.setExtNum03(Double.valueOf(getCellValue(row.getCell(16))));// 主折扣率
                }
                if(row.getCell(17) == null){
                    pledgeBaseInfo.setExtInt02(0);
                }else{
                    pledgeBaseInfo.setExtInt02(Integer.valueOf(getCellValue(row.getCell(17)))); //次折扣天数
                }
                if(row.getCell(18) == null){
                    pledgeBaseInfo.setExtNum04(0.00);
                }else{
                    pledgeBaseInfo.setExtNum04(Double.valueOf(getCellValue(row.getCell(18))));//次折扣率
                }
                if(row.getCell(19) == null){
                    pledgeBaseInfo.setRemark("");
                }else{
                    pledgeBaseInfo.setRemark(getCellValue(row.getCell(19)));    //说明
                }
                list.add(pledgeBaseInfo);
                }
        return list;
            }


    public static String getCellValue(Cell cell){
        String value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化字符类型的数字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if("General".equals(cell.getCellStyle().getDataFormatString())){
                    value = df.format(cell.getNumericCellValue());
                }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
                    value = sdf.format(cell.getDateCellValue());
                }else{
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue()+"";
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 打开合同清单上传
     *
     * @author LJW date 2017-4-14
     */
    @RequestMapping(value = "/uploadBill")
    public String getListPage1(Model model, String appId,String collateralId) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("appId",appId);
        model.addAttribute("collateralId",collateralId);
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(collateralId);
        pledgeBaseInfo= pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        List<PledgeBaseInfoBill> list=new ArrayList<PledgeBaseInfoBill>();
        model.addAttribute("uploadBillList",list);
        model.addAttribute("pledgeBaseInfo",pledgeBaseInfo);
        return "/component/collateral/UploadBill";
    }
    // 下载模板
    @RequestMapping(value = "/exportExcelAjax")
    @ResponseBody
    public Map<String, Object> exportExcelAjax() throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            String path = PropertiesUtil.getUploadProperty("uploadFinModelFilePath");
            File file = new File(path);
            // 判断文件是否存在
            String destFile = path + File.separator + "acountBill.xlsx";
            file = new File(destFile);
            if (!file.exists()) {
                throw new Exception("PoiExportExcelUtil:"+destFile+" 文件不存在");
            }
            dataMap.put("flag", "success");
            dataMap.put("exportFlag", "success");
            dataMap.put("path", destFile);
            dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("导出"));
            if (path == null) {
                dataMap.put("exportFlag", "error");
                dataMap.put("msg", MessageEnum.NO_FILE.getMessage("模板导出路径"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     * 方法描述： 导入对应的合同清单
     * @author 例子
     * @date
     */
    @RequestMapping(value = "/importBillExcel",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> importBillExcel(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "file") MultipartFile file,String appId,String collateralId) throws Exception {
        Map<String,Object> dataMap=new HashMap<String,Object>();
        try {
            String originalFilename = file.getOriginalFilename();
            String type = file.getContentType();
            System.out.println("目标文件名称："+originalFilename+",目标文件类型："+type);

            //添加文件上传格式验证，过滤可能威胁系统安全非法后缀
            if(originalFilename.endsWith(".exe")||originalFilename.endsWith(".jsp")||originalFilename.endsWith(".bat")
                    ||originalFilename.endsWith(".dll")||originalFilename.endsWith(".com")||originalFilename.endsWith(".sh")||originalFilename.endsWith(".py")){
                dataMap.put("flag", "error");
                dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
                return dataMap;
            }
            List<PledgeBaseInfoBill> list = readBillExcel(file,originalFilename);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr("tablepledgebillupload", "tableTag", list, null, true);
            dataMap.put("flag", "success");
            dataMap.put("tableHtml", tableHtml);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
        }
        return dataMap;
    }

    public  List<PledgeBaseInfoBill>   readBillExcel(MultipartFile file, String fileName) throws Exception{
        FileInputStream fileInputStream = (FileInputStream)file.getInputStream();
        //创建Excel工作薄
        Workbook work = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            work = new HSSFWorkbook(fileInputStream);  //2003-
        } else if (".xlsx".equals(fileType)) {
            work = new XSSFWorkbook(fileInputStream);  //2007+
        }
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        //遍历Excel中的sheet
        sheet = work.getSheetAt(0);
        List<PledgeBaseInfoBill> list=new ArrayList<PledgeBaseInfoBill>();
        //遍历当前sheet中的所有行
        //包涵头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部
        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
            //读取一行
            row = sheet.getRow(j);
            //去掉空行和表头
            if (row == null || row.getFirstCellNum() == j) {
                continue;
            }
            if("合同签订日期".equals(getCellValue(row.getCell(1)))){
                continue;
            }
            PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
            pledgeBaseInfoBill.setInstockData(getCellValue(row.getCell(1)));//合同签订时间
            pledgeBaseInfoBill.setBillName(getCellValue(row.getCell(2)));//买方名称
            pledgeBaseInfoBill.setAscriptionBus(getCellValue(row.getCell(3)));//基础合同名称
            pledgeBaseInfoBill.setStorehouse(getCellValue(row.getCell(4)));//合同编号
            pledgeBaseInfoBill.setUnitPrice(Double.valueOf(getCellValue(row.getCell(5))));    //合同金额(元)
            pledgeBaseInfoBill.setSingleWeight(Double.valueOf(getCellValue(row.getCell(6))));//应收账款余额(元)
            list.add(pledgeBaseInfoBill);
        }
        return list;
    }
    @RequestMapping(value = "/saveBillAjax")
    @ResponseBody
    public Map<String, Object> saveBillAjax( String appId, String collateralId, String ajaxDataList) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
            List<PledgeBaseInfoBill> list = (List<PledgeBaseInfoBill>)JSONArray.toList(jsonArray, new PledgeBaseInfoBill(), new JsonConfig());
            for (int i = 0; i < list.size(); i++) {
                PledgeBaseInfoBill pledgeBaseInfoBill = list.get(i);
                pledgeBaseInfoBill.setPledgeNo(collateralId);
            }
            pledgeBaseInfoBillFeign.insertList(list);
            List<PledgeBaseInfoBill> result =pledgeBaseInfoBillFeign.findListByPledgeNo(collateralId);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr("tabledlpledgebaseinfobill0004" , "tableTag",
                    result, null, true);
            dataMap.put("htmlStr", tableHtml);
            dataMap.put("htmlStrFlag", "1");
            dataMap.put("pledgeNo", collateralId);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
        }
        return dataMap;
    }
}
