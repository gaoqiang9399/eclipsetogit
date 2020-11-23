package app.component.audit.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfCusAndApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.audit.feign.MfBusRiskAuditFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.common.BizPubParm;
import app.component.examine.entity.MfExamineTemplateConfig;
import app.component.examine.feign.MfExamineTemplateConfigFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.risk.audit.entity.MfBusRiskAudit;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.report.ExpExclUtil;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 风控稽核表(MfBusRiskAudit)表控制层
 *
 * @author makejava
 * @since 2020-03-09 18:28:06
 */
@Controller
@RequestMapping("/mfBusRiskAudit")
public class MfBusRiskAuditController extends BaseFormBean {

    private final Logger logger = LoggerFactory.getLogger(MfBusRiskAuditController.class);
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private MfExamineTemplateConfigFeign mfExamineTemplateConfigFeign;
    @Autowired
    private MfBusRiskAuditFeign mfBusRiskAuditFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private MfCusCreditContractFeign mfCusCreditContractFeign;
    @Autowired
    private MfCusCreditApplyFeign mfCusCreditApplyFeign;

    /**
     * @Description 跳转到待稽核列表页面登记
     * @Author jialei
     * @Param [model]
     * @return java.lang.String
     */
    @RequestMapping(value = "/getAccurateListPage")
    public String getAccurateListPage(Model model)  throws Exception {
        ActionContext.initialize(request, response);

//('batch_printing','letter_intent','contract_sign','contract_approval','contract_print','charge-fee','charge-fee-approve','putout_apply','certidInfo_reg','review_finc')
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        JSONArray jsonArray = JSONArray.fromObject(
                "[{\"optCode\":\"batch_printing\",\"optName\":\"批单打印\"}," +
                 "{\"optCode\":\"letter_intent\",\"optName\":\"担保意向书\"}," +
                 "{\"optCode\":\"contract_sign\",\"optName\":\"合同生成\"}," +
                 "{\"optCode\":\"contract_approval\",\"optName\":\"合同审批\"}," +
                        "{\"optCode\":\"contract_print\",\"optName\":\"合同打印\"}," +
                        "{\"optCode\":\"charge-fee\",\"optName\":\"缴款通知书\"}," +
                        "{\"optCode\":\"charge-fee-approve\",\"optName\":\"缴款通知书审核\"}," +
                        "{\"optCode\":\"account_confirm\",\"optName\":\"到账确认\"}," +
                        "{\"optCode\":\"expense_confirm\",\"optName\":\"费用确认\"}," +
                        "{\"optCode\":\"putout_apply\",\"optName\":\"放款通知书\"}," +
                        "{\"optCode\":\"certidInfo_reg\",\"optName\":\"抵质押落实\"}," +
                        "{\"optCode\":\"review_finc\",\"optName\":\"放款确认\"}" +

                 "]");
        flowNodeJsonArray.addAll(jsonArray);
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("query", "");
        model.addAttribute("busModel", BizPubParm.BUS_MODEL_10);
        return "/component/risk/audit/MfBusRiskAudit_accurateList";
    }
    /**
     * @Description 跳转到待稽核列表页面通知
     * @Author jialei
     * @Param [model]
     * @return java.lang.String
     */
    @RequestMapping(value = "/getAccurateListPageForNotice")
    public String getAccurateListPageForNotice(Model model)  throws Exception {
        ActionContext.initialize(request, response);

//('batch_printing','letter_intent','contract_sign','contract_approval','contract_print','charge-fee','charge-fee-approve','putout_apply','certidInfo_reg','review_finc')
        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        JSONArray jsonArray = JSONArray.fromObject(
                "[{\"optCode\":\"batch_printing\",\"optName\":\"批单打印\"}," +
                        "{\"optCode\":\"letter_intent\",\"optName\":\"担保意向书\"}," +
                        "{\"optCode\":\"contract_sign\",\"optName\":\"合同生成\"}," +
                        "{\"optCode\":\"contract_approval\",\"optName\":\"合同审批\"}," +
                        "{\"optCode\":\"contract_print\",\"optName\":\"合同打印\"}," +
                        "{\"optCode\":\"charge-fee\",\"optName\":\"缴款通知书\"}," +
                        "{\"optCode\":\"charge-fee-approve\",\"optName\":\"缴款通知书审核\"}," +
                        "{\"optCode\":\"account_confirm\",\"optName\":\"到账确认\"}," +
                        "{\"optCode\":\"expense_confirm\",\"optName\":\"费用确认\"}," +
                        "{\"optCode\":\"putout_apply\",\"optName\":\"放款通知书\"}," +
                        "{\"optCode\":\"certidInfo_reg\",\"optName\":\"抵质押落实\"}," +
                        "{\"optCode\":\"review_finc\",\"optName\":\"放款确认\"}" +

                        "]");
        flowNodeJsonArray.addAll(jsonArray);
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("query", "");
        model.addAttribute("busModel", BizPubParm.BUS_MODEL_10);
        return "/component/risk/audit/MfBusRiskAudit_accurateListForNotice";
    }
    /**
     * 选择稽核类型
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/chooseAuditType")
    public String choosePrintType(Model model,String appId) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("appId", appId);
        return "/component/risk/audit/chooseAuditType";
    }

    /**
     * 选择模板类型
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/chooseAuditConfig")
    public String chooseAuditConfig(Model model,String appId,String busModel) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        String pactId  = "";
        String appName = "";
        String cusMngName = "";
        String cusMngNo = "";
        String pactNo = "";

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        if(mfBusApply==null){//工程的授信稽核
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(appId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);

            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(appId);
            mfCusCreditContract = mfCusCreditContractFeign.getLstContractByCreditAppId(mfCusCreditContract);

            pactId = mfCusCreditContract.getPactId();
            appName = mfCusCreditApply.getCreditAppNo();
            cusMngName = mfCusCreditApply.getManageOpName();
            cusMngNo = mfCusCreditApply.getManageOpNo();
            pactNo = mfCusCreditContract.getPactNo();
        }else {
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setAppId(appId);
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            pactId = mfBusApply.getPactId();
            appName = mfBusApply.getBankXiYi();
            cusMngName = mfBusApply.getCusMngName();
            cusMngNo = mfBusApply.getCusMngNo();
            if(mfBusPact!=null){
                pactNo = mfBusPact.getPactNo();
            }
        }

        String formId = "chooseAuditConfig";
        FormData baseform = formService.getFormData(formId);

        List<OptionsList> opinionTypeList = new ArrayList<OptionsList>();
        OptionsList optionsList= new OptionsList();

        MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
        mfExamineTemplateConfig.setTemplateType("1");
        List<MfExamineTemplateConfig> mfExamineTemplateConfigRiskList = mfExamineTemplateConfigFeign.getExamineTemplateList(mfExamineTemplateConfig);
        if(mfExamineTemplateConfigRiskList != null && mfExamineTemplateConfigRiskList.size()>0){
            mfExamineTemplateConfig = mfExamineTemplateConfigRiskList.get(0);
            optionsList = new OptionsList();
            optionsList.setOptionValue(mfExamineTemplateConfig.getTemplateId());
            optionsList.setOptionLabel(mfExamineTemplateConfig.getTemplateName());
            optionsList.setOptionId("");
            opinionTypeList.add(optionsList);
        }
        changeFormProperty(baseform, "templateId", "optionArray", opinionTypeList);

        MfBusRiskAudit mfBusRiskAudit = new MfBusRiskAudit();
        mfBusRiskAudit.setPactId(pactId);//合同号
        mfBusRiskAudit.setPactNo(pactNo);//合同号
        mfBusRiskAudit.setAppId(appId);//申请号
        mfBusRiskAudit.setAppName(appName);//项目名称
        mfBusRiskAudit.setChargeName(cusMngName);
        mfBusRiskAudit.setChargeNo(cusMngNo);
        mfBusRiskAudit.setOpNo(User.getRegNo(request));
        mfBusRiskAudit.setOpName(User.getRegName(request));
        mfBusRiskAudit.setBrNo(User.getOrgNo(request));
        mfBusRiskAudit.setBrName(User.getOrgName(request));
        mfBusRiskAudit.setAuditDate(DateUtil.getDate());
        getObjValue(baseform, mfBusRiskAudit);

        model.addAttribute("baseform", baseform);
        model.addAttribute("appId", appId);
        model.addAttribute("busModel", busModel);
        model.addAttribute("query", "");
        return "/component/risk/audit/chooseAuditConfig";
    }



    /**
     * @Description 获取待稽核列表的数据
     * @Author jialei
     * @Param [pageNo, pageSize, tableId, tableType, ajaxData, ipage]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/findAccurateByPageAjax")
    @ResponseBody
    public Map<String, Object> findAccurateByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                             String tableType, String ajaxData, Ipage ipage,String busModel) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            // 取出ajax数据
            mfCusAndApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfCusAndApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);// 我的筛选
            mfCusAndApply.setBusModel(busModel);
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
            // 自定义查询Bo方法
            if(BizPubParm.BUS_MODEL_10.equals(busModel)){//小微
                ipage = mfBusApplyFeign.getPactAndApply(ipage);
            }else {//工程
                ipage = mfBusApplyFeign.getCreditAndApplyRisk(ipage);
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            logger.error("获取风控稽核任务列表的数据失败"+e.getMessage(),e);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     * @Description 获取待稽核列表的数据
     * @Author jialei
     * @Param [pageNo, pageSize, tableId, tableType, ajaxData, ipage]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/findAccurateByPageAjaxForNotice")
    @ResponseBody
    public Map<String, Object> findAccurateByPageAjaxForNotice(Integer pageNo, Integer pageSize, String tableId,
                                                      String tableType, String ajaxData, Ipage ipage,String busModel) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusAndApply mfCusAndApply = new MfCusAndApply();
        try {
            // 取出ajax数据
            mfCusAndApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfCusAndApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);// 我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfCusAndApply", mfCusAndApply));
            if(BizPubParm.BUS_MODEL_10.equals(busModel)){//小微
                ipage = mfBusApplyFeign.getPactAndApply(ipage);
            }else {//工程
                ipage = mfBusApplyFeign.getCreditAndApplyRisk(ipage);
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            logger.error("获取风控稽核任务列表的数据失败"+e.getMessage(),e);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     *
     * 方法描述： 跳转稽核登记页面
     * @param model
     * @param appId
     * @return
     * @throws Exception
     * String
     * @author 贾磊
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String appId, String templateId,String busModel) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        String guaranteeScheme = "";//担保方案
        String counterGuaranteeScheme = "";//反担保方案
        String manageControl  = "";
        String pactId  = "";
        String appName = "";
        String cusMngName = "";
        String cusMngNo = "";
        String pactNo = "";
        String cusNo = "";
        String cusName = "";

        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        if(mfBusApply==null){//工程的授信稽核
            MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
            mfCusCreditApply.setCreditAppId(appId);
            mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);

            MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
            mfCusCreditContract.setCreditAppId(appId);
            mfCusCreditContract = mfCusCreditContractFeign.getLstContractByCreditAppId(mfCusCreditContract);

            guaranteeScheme = mfCusCreditApply.getGuaranteeScheme();
            counterGuaranteeScheme = mfCusCreditApply.getCounterGuaranteeScheme();
            manageControl = mfCusCreditApply.getManageControl();
            pactId = mfCusCreditContract.getPactId();
            appName = mfCusCreditApply.getCreditAppNo();
            cusMngName = mfCusCreditApply.getManageOpName();
            cusMngNo = mfCusCreditApply.getManageOpNo();
            pactNo = mfCusCreditContract.getPactNo();
            cusNo = mfCusCreditContract.getCusNo();
            cusName = mfCusCreditContract.getCusName();
        }else {
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setAppId(appId);
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            guaranteeScheme = mfBusApply.getGuaranteeScheme();
            counterGuaranteeScheme = mfBusApply.getCounterGuaranteeScheme();
            manageControl = mfBusApply.getManageControl();
            pactId = mfBusApply.getPactId();
            appName = mfBusApply.getBankXiYi();
            cusMngName = mfBusApply.getCusMngName();
            cusMngNo = mfBusApply.getCusMngNo();
            if(mfBusPact!=null){
               pactNo = mfBusPact.getPactNo();
            }
            cusNo = mfBusApply.getCusNo();
            cusName = mfBusApply.getCusName();
        }

        //查询风控模型配置的表单
        MfExamineTemplateConfig mfExamineTemplateConfig = new MfExamineTemplateConfig();
        mfExamineTemplateConfig.setTemplateId(templateId);
        mfExamineTemplateConfig = mfExamineTemplateConfigFeign.getById(mfExamineTemplateConfig);
        String formId = mfExamineTemplateConfig.getFormTemplate();
        FormData baseform = formService.getFormData(formId);

        MfBusRiskAudit mfBusRiskAudit = new MfBusRiskAudit();
        mfBusRiskAudit.setPactId(pactId);//合同号
        mfBusRiskAudit.setPactNo(pactNo);//合同号
        mfBusRiskAudit.setAppId(appId);//申请号
        mfBusRiskAudit.setAppName(appName);//项目名称
        mfBusRiskAudit.setTemplateId(templateId);
        mfBusRiskAudit.setChargeName(cusMngName);
        mfBusRiskAudit.setChargeNo(cusMngNo);
        mfBusRiskAudit.setCusNo(cusNo);
        mfBusRiskAudit.setCusName(cusName);
        mfBusRiskAudit.setOpNo(User.getRegNo(request));
        mfBusRiskAudit.setOpName(User.getRegName(request));
        mfBusRiskAudit.setBrNo(User.getOrgNo(request));
        mfBusRiskAudit.setBrName(User.getOrgName(request));
        mfBusRiskAudit.setAuditDate(DateUtil.getDate());
        mfBusRiskAudit.setGuaScheme(guaranteeScheme);
        mfBusRiskAudit.setGuaSchemeBack(counterGuaranteeScheme);
        mfBusRiskAudit.setManageMeasures(manageControl);
        mfBusRiskAudit.setBusModel(busModel);
        getObjValue(baseform, mfBusRiskAudit);

        model.addAttribute("baseform", baseform);
        model.addAttribute("appId", appId);
        model.addAttribute("busModel", busModel);
        model.addAttribute("query", "");
        return "component/risk/audit/MfBusRiskAudit_Insert";
    }

    /**
     *
     * 方法描述： 稽核登记新增
     * @param ajaxData
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author 贾磊
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> map = getMapByJson(ajaxData);
        String formId = (String) map.get("formId");
        FormData baseform = formService.getFormData(formId);
        getFormValue(baseform, getMapByJson(ajaxData));
        MfBusRiskAudit mfBusRiskAudit = new MfBusRiskAudit();
        setObjValue(baseform, mfBusRiskAudit);

        mfBusRiskAudit.setFromId(formId);
        mfBusRiskAudit.setOpNo(User.getRegNo(request));
        mfBusRiskAudit.setOpName(User.getRegName(request));
        mfBusRiskAudit.setBrNo(User.getOrgNo(request));
        mfBusRiskAudit.setBrName(User.getOrgName(request));
        map.put("mfBusRiskAudit", mfBusRiskAudit);
        try {
            MfBusRiskAudit mfBusRiskAuditResult = mfBusRiskAuditFeign.insertAjax(map);
            dataMap.put("appSts", mfBusRiskAuditResult.getAppSts());
            dataMap.put("flag", "success");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("userRole", mfBusRiskAuditResult.getApproveNodeName());
            paramMap.put("opNo", mfBusRiskAuditResult.getApprovePartName());
            dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     *
     * 方法描述： 跳转审批页面
     * @param model
     * @param auditId
     * @return
     * @throws Exception
     * String
     * @author 贾磊
     */
    @RequestMapping(value = "/getAuditView")
    public String getAuditView(Model model, String auditId,String taskId,String hideOpinionType) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        MfBusRiskAudit mfBusRiskAudit = new MfBusRiskAudit();
        mfBusRiskAudit.setAuditId(auditId);
        mfBusRiskAudit = mfBusRiskAuditFeign.getById(mfBusRiskAudit);
        String formId ="mfBusRiskAudit001";


        TaskImpl taskAppro = wkfInterfaceFeign.getTask(auditId, taskId);
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
//        if("risk_supplement_data".equals(nodeNo)){
//            formId ="mfBusRiskAudit002";
//        }
        FormData baseform = formService.getFormData(formId);
        getObjValue(baseform, mfBusRiskAudit);


        String activityType = taskAppro.getActivityType();
        // 处理审批意见类型
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型

        opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
        opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
        // 处理审批意见类型
        List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
                taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(baseform, "opinionType", "optionArray", opinionTypeList);

        model.addAttribute("baseform", baseform);
        model.addAttribute("query", "");
        model.addAttribute("auditId", auditId);
        model.addAttribute("cusNo", mfBusRiskAudit.getCusNo());
        model.addAttribute("nodeNo", BizPubParm.SCENCE_TYPE_DOC_RISK_AUDIT);
        model.addAttribute("taskId", taskId);
        return "/component/risk/audit/MfBusRiskAudit_approve";
    }

    /**
     *
     * 方法描述： 审批提交（审批意见保存）
     * @return
     * @throws Exception
     * StringMfBusApplyWkfBoImpl
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/doSubmitAjax")
    @ResponseBody
    public Map<String, Object> doSubmitAjax(String ajaxData, String ajaxDataList, String taskId, String auditId, String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map map = getMapByJson(ajaxData);
        auditId = (String) map.get("auditId");

        MfBusRiskAudit mfBusRiskAudit = new MfBusRiskAudit();
        mfBusRiskAudit.setAuditId(auditId);
        mfBusRiskAudit = mfBusRiskAuditFeign.getById(mfBusRiskAudit);

        FormData formapply0003 = formService.getFormData((String) map.get("formId"));
        getFormValue(formapply0003, map);

        setObjValue(formapply0003, mfBusRiskAudit);
        mfBusRiskAudit.setApproveNodeName(User.getRegNo(request));
        mfBusRiskAudit.setApproveNodeNo(User.getRegName(request));
        mfBusRiskAudit.setApprovePartNo(User.getOrgNo(request));
        mfBusRiskAudit.setApprovePartName(User.getOrgName(request));

        dataMap = getMapByJson(ajaxData);
        String opinionType = String.valueOf(dataMap.get("opinionType"));
        String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
        JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
        try {
            dataMap.put("mfBusRiskAudit", mfBusRiskAudit);
            dataMap.put("approvalOpinion", approvalOpinion);
            Result res = mfBusRiskAuditFeign.doCommit(taskId, auditId, opinionType,approvalOpinion ,transition, User.getRegNo(request), nextUser, dataMap);
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

    /**
     *
     * 方法描述： 稽核通知
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/pushMessage")
    @ResponseBody
    public Map<String, Object> pushMessage(String appId,String auditType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String opNo = User.getRegNo(request);
        try {
            dataMap= mfBusRiskAuditFeign.pushMessage(appId, auditType,opNo);
            dataMap.put("flag", "success");
            dataMap.put("msg","已发送对应项目客户经理");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
            throw e;
        }
        return dataMap;

    }

    /**
     * @Description 跳转到稽核列表页面
     * @Author jialei
     * @Param [model]
     * @return java.lang.String
     */
    @RequestMapping(value = "/getAuditListPage")
    public String getAuditListPage(Model model) {
        ActionContext.initialize(request, response);
        try {
            // 前台自定义的筛选组件，从数据字典中的缓存中获取
            JSONArray fincExamineStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_EXAMINE_STATS");
            model.addAttribute("fincExamineStsJsonArray", fincExamineStsJsonArray);
            model.addAttribute("query", "");
        } catch (Exception e) {
            logger.error("跳转到稽核列表页面失败" + e.getMessage(), e);
        }
        return "/component/risk/audit/MfBusRiskAudit_list";

    }
    /**
     * @Description 获取待稽核列表的数据
     * @Author jialei
     * @Param [pageNo, pageSize, tableId, tableType, ajaxData, ipage]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                      String tableType, String ajaxData, Ipage ipage) {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusRiskAudit mfBusRiskAudit = new MfBusRiskAudit();
        try {
            // 取出ajax数据
            mfBusRiskAudit.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusRiskAudit.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusRiskAudit.setCriteriaList(mfBusRiskAudit, ajaxData);// 我的筛选
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusRiskAudit", mfBusRiskAudit));
            // 自定义查询Bo方法
            ipage = mfBusRiskAuditFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            logger.error("获取风控稽核任务列表的数据失败"+e.getMessage(),e);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     *
     * 方法描述： 跳转详情页面
     * @param model
     * @param appId
     * @return
     * @throws Exception
     * String
     * @author 贾磊
     */
    @RequestMapping(value = "/getDetailPage")
    public String getDetailPage(Model model, String auditId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        String formId = "baseexaminfoNew_1003";
        FormData baseform = formService.getFormData(formId);

        MfBusRiskAudit mfBusRiskAudit = new MfBusRiskAudit();
        mfBusRiskAudit.setAuditId(auditId);
        mfBusRiskAudit = mfBusRiskAuditFeign.getById(mfBusRiskAudit);

        getObjValue(baseform, mfBusRiskAudit);

        model.addAttribute("baseform", baseform);
        model.addAttribute("mfBusRiskAudit", mfBusRiskAudit);
        model.addAttribute("cusNo", mfBusRiskAudit.getCusNo());
        model.addAttribute("auditId", auditId);
        model.addAttribute("nodeNo", BizPubParm.SCENCE_TYPE_DOC_RISK_AUDIT);
        model.addAttribute("query", "query");
        return "component/risk/audit/MfBusRiskAudit_Detail";
    }

    /**
     * 导出借据信息
     * @param ajaxData
     * @param pactId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportExcelAjax")
    @ResponseBody
    public void exportExcelAjax(String ajaxData,String tableId,String appId,String busModel) throws Exception{
        try {
            MfCusAndApply mfCusAndApply = new MfCusAndApply();
            mfCusAndApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusAndApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
//            mfCusAndApply.setNodeNo("'putout_apply','certidInfo_reg','review_finc'");
            List<MfCusAndApply> listExport = new ArrayList<MfCusAndApply>();
            if(StringUtil.isNotEmpty(appId)){
                String[] appIds = appId.split(",");
                for (int i = 0; i < appIds.length; i++) {
                    String id = appIds[i];
                    if(StringUtil.isNotEmpty(id)){
                        List<MfCusAndApply> list = null;
                        mfCusAndApply.setAppId(id);
                        if(BizPubParm.BUS_MODEL_12.equals(busModel)){
                            list = mfBusApplyFeign.getCreditAndApplyRiskList(mfCusAndApply);
                        }else {
                            list = mfBusApplyFeign.getPactAndApplyList(mfCusAndApply);
                        }
                        listExport.addAll(list);
                    }
                }
            }
            if(listExport.size()>0){
                String fileName = "合同稽核_"+System.currentTimeMillis()+".xls";//目标文件
                ExpExclUtil eu = new ExpExclUtil();
                HSSFWorkbook wb = eu.expExclTableForList(tableId, listExport,null,false,"");
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/x-download; charset=utf-8");
                response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
                OutputStream stream = response.getOutputStream();
                wb.write(stream);
                wb.close();// HSSFWorkbook关闭
                stream.flush();
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @Description 跳转到待稽核列表页面登记
     * @Author jialei
     * @Param [model]
     * @return java.lang.String
     */
    @RequestMapping(value = "/getAccurateListPageGc")
    public String getAccurateListPageGc(Model model)  throws Exception {
        ActionContext.initialize(request, response);

        // 根据业务模式获取所有的流程节点信息（过滤重复的）
        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("query", "");
        model.addAttribute("busModel", BizPubParm.BUS_MODEL_12);
        return "/component/risk/audit/MfBusRiskAudit_accurateListGc";
    }

    /**
     * @Description 跳转到待稽核列表页面通知
     * @Author jialei
     * @Param [model]
     * @return java.lang.String
     */
    @RequestMapping(value = "/getAccurateListPageForNoticeGc")
    public String getAccurateListPageForNoticeGc(Model model)  throws Exception {
        ActionContext.initialize(request, response);

        JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
        model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
        model.addAttribute("query", "");
        model.addAttribute("busModel", BizPubParm.BUS_MODEL_12);
        return "/component/risk/audit/MfBusRiskAudit_accurateListForNoticeGc";
    }
}