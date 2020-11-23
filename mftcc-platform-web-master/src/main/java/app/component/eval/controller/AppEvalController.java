package app.component.eval.controller;

import app.base.ServiceException;
import app.base.User;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.report.entity.MfCusReportAcount;
import app.component.cus.report.feign.MfCusReportAcountFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.eval.entity.EvalScenceFinVal;
import app.component.eval.entity.EvalScenceDlRel;
import app.component.eval.entity.EvalScenceFinRel;
import app.component.eval.entity.AppEval;
import app.component.eval.entity.EvalCompreVal;
import app.component.eval.entity.EvalScenceConfig;
import app.component.eval.entity.MfEvalGradeCard;
import app.component.eval.entity.EvalScenceDxVal;
import app.component.eval.entity.EvalScenceDxRel;
import app.component.eval.entity.EvalScenceRestrictVal;
import app.component.eval.entity.EvalScoreGradeConfig;
import app.component.eval.entity.EvalScenceDlVal;
import app.component.eval.entity.EvalScenceDxVal;
import app.component.eval.entity.EvalScenceAdjVal;
import app.component.eval.entity.EvalScenceRestrictRel;
import app.component.eval.entity.EvalScenceAdjRel;
import app.component.eval.feign.*;
import app.component.eval.util.EvalUtil;
import app.component.nmd.entity.ParmDic;
import app.component.pfs.entity.CusFinCapData;
import app.component.pfs.entity.CusFinMain;
import app.component.pfs.feign.CusFinMainFeign;
import app.component.pfsinterface.PfsInterfaceFeign;
import app.component.risk.entity.RiskBizItemRel;
import app.component.risk.feign.RiskBizItemRelFeign;
import app.component.rules.feign.EvalDataFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Title: AppEvalController.java Description:
 *
 * @author:@dhcc.com.cn
 * @Wed Mar 23 03:42:29 GMT 2016
 **/
@Controller
@RequestMapping("/appEval")
public class AppEvalController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private AppEvalFeign appEvalFeign;
    @Autowired
    private EvalScenceDlValFeign evalScenceDlValFeign;
    @Autowired
    private EvalScenceDxValFeign evalScenceDxValFeign;
    @Autowired
    private EvalScenceFinValFeign evalScenceFinValFeign;
    @Autowired
    private EvalScenceAdjValFeign evalScenceAdjValFeign;
    @Autowired
    private EvalScenceRestrictValFeign evalScenceRestrictValFeign;
    @Autowired
    private EvalScenceFinRelFeign evalScenceFinRelFeign;
    @Autowired
    private EvalScenceDlRelFeign evalScenceDlRelFeign;
    @Autowired
    private EvalScenceDxRelFeign evalScenceDxRelFeign;
    @Autowired
    private EvalScenceAdjRelFeign evalScenceAdjRelFeign;
    @Autowired
    private EvalScenceRestrictRelFeign evalScenceRestrictRelFeign;
    @Autowired
    private EvalCompreValFeign evalCompreValFeign;
    @Autowired
    private EvalScenceConfigFeign evalScenceConfigFeign;
    @Autowired
    private EvalScoreGradeConfigFeign evalScoreGradeConfigFeign;
    @Autowired
    private EvalSysAssessFeign evalSysAssessFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private PfsInterfaceFeign pfsInterfaceFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private CusFinMainFeign cusFinMainFeign;

    @Autowired
    private WorkflowDwrFeign workflowDwrFeign;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private MfCusReportAcountFeign mfCusReportAcountFeign;
    @Autowired
    private RiskBizItemRelFeign riskBizItemRelFeign;
    @Autowired
    private AppPropertyFeign appPropertyFeign;
    @Autowired
    private EvalDataFeign evalDataFeign;

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/eval/getListPage";
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findByPageAjax")
    @ResponseBody
    public Map <String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                               String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        AppEval appEval = new AppEval();
        try {
            appEval.setCustomQuery(ajaxData);// 自定义查询参数赋值
            appEval.setCriteriaList(appEval, ajaxData);// 我的筛选
            // this.getRoleConditions(appEval,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            // 自定义查询Feign方法
            ipage = appEvalFeign.findByPage(ipage, appEval);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List <?>) ipage.getResult(), ipage, true);
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
     * 查看评级结果
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getDetailResult")
    public String getDetailResult(Model model, String cusNo, String appSts,String useType,String appId,String creditAppId,String evalAppNo,String relNo,String gradeType,String evalClass) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
        String cusBaseType = mfCusCustomer.getCusBaseType();
        RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
        AppEval appEval = new AppEval();
        String parmName = "EVAL_LEVEL";
        String showName = "评级";
        String tableId = "tableeval1003";
        if (BizPubParm.USE_TYPE_1.equals(useType)) {
            appEval.setCusNo(cusNo);
        } else if (BizPubParm.USE_TYPE_2.equals(useType)) {
            parmName = "RISK_PREVENT_LEVEL";
            appEval.setAppId(appId);
            showName = "风险检查";
            tableId = "tableRiskCheckList";
        } else if (BizPubParm.USE_TYPE_3.equals(useType)){
            parmName = "RISK_PREVENT_LEVEL";
            appEval.setCreditAppId(creditAppId);
            showName = "风险检查";
            tableId = "tableRiskCheckList";
        }else if (BizPubParm.USE_TYPE_4.equals(useType)){
            parmName = "RISK_PREVENT_LEVEL";
            appEval.setCusNo(cusNo);
            showName = "授信测算";
            tableId = "tableRiskCheckList";
        }else {
        }

        appEval.setUseType(useType);
        appEval.setEvalSts(appSts);
        appEval.setEvalAppNo(evalAppNo);
        appEval.setGradeType(gradeType);
        appEval.setEvalClass(evalClass);
        List <AppEval> appEvalList = appEvalFeign.getListForCusNoAndSts(appEval);
        FormData formeval1006 = formService.getFormData("eval1006");
        FormData formeval2008 = formService.getFormData("eval2008");
        EvalCompreVal evalCompreVal = new EvalCompreVal();
        appEval = new AppEval();
        if (appEvalList != null && appEvalList.size() > 0) {
            appEval = appEvalList.get(0);
            EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
            evalScenceConfig.setEvalScenceNo(appEval.getEvalScenceNo());
            evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
            Map <String, Object> scoreAllMap = new HashMap <String, Object>();
            scoreAllMap = appEvalFeign.getAllScoreForEchart(new HashMap <String, String>());
            List <Double> maxScoreList = new ArrayList <Double>();
            List <Double> minScoreList = new ArrayList <Double>();
            List <Double> avgScoreList = new ArrayList <Double>();
            List <Double> currentScoreList = new ArrayList <Double>();
            currentScoreList.add(appEval.getFinScore());
            currentScoreList.add(appEval.getDlScore());
            currentScoreList.add(appEval.getDxScore());
            currentScoreList.add(appEval.getAdjScore());
            currentScoreList.add(appEval.getManAdjScore());
            if (scoreAllMap != null) {
                // 最大值
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("FINMAXSCORE").toString()));
                // maxScoreList.add(Double.parseDouble(scoreAllMap.get("DLMAXSCORE").toString())+Double.parseDouble(scoreAllMap.get("FINMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("DLMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("DXMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMAXSCORE").toString()));
                // 最小值
                minScoreList.add(Double.parseDouble(scoreAllMap.get("FINMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("DLMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("DXMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMINSCORE").toString()));
                // 平均值
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("FINAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("DLAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("DXAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("ADJAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("MANGAVGSCORE").toString()));
            }
            Map <String, Object> map = new HashMap <String, Object>();
            CodeUtils cu = new CodeUtils();
            Map <String, String> cusTypeMap = cu.getMapByKeyName(parmName);
            Map <String, ParmDic> cusTypeMapObj = cu.getMapObjByKeyName(parmName);
            String evalAssess = "";
            if (cusTypeMapObj.get(appEval.getMangGrade()) != null) {
                evalAssess = cusTypeMapObj.get(appEval.getMangGrade()).getRemark();
            }
            String level = cusTypeMap.get(appEval.getMangGrade());
            if (appEval.getApprovalGrade() != null && !"".equals(appEval.getApprovalGrade())) {
                evalAssess = cusTypeMapObj.get(appEval.getApprovalGrade()).getRemark();
                level = cusTypeMap.get(appEval.getApprovalGrade());
            }
            if (level == null || "".equals(level)) {
                level = cusTypeMap.get(appEval.getGrade());
            }
            if(StringUtil.isNotEmpty(appEval.getGrade())){
                EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
                evalScoreGradeConfig.setEvalLevel(appEval.getGrade());
                evalScoreGradeConfig.setEvalClass(BizPubParm.EVAL_CLASS_1);
                evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
                if(evalScoreGradeConfig != null){
                    level = evalScoreGradeConfig.getEvalGradeLevel();
                }
            }
            if(StringUtil.isNotEmpty(appEval.getDebtGrade())){
                EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
                evalScoreGradeConfig.setEvalLevel(appEval.getDebtGrade());
                evalScoreGradeConfig.setEvalClass(BizPubParm.EVAL_CLASS_2);
                evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
                if(evalScoreGradeConfig != null){
                    map.put("debtLevel", evalScoreGradeConfig.getEvalGradeLevel());
                }else{
                    map.put("debtLevel", "未评估");
                }
            }else{
                map.put("debtLevel", "未评估");
            }

            boolean ifExisRisk = false;
            riskBizItemRel.setRelNo(appEval.getEvalAppNo());
            riskBizItemRel.setNodeNo(BizPubParm.WKF_NODE.cus_eval_tip.getNodeNo());
            riskBizItemRel.setRiskLevel(BizPubParm.EVAL_TRIGGER_LEVEL_2);
            List<RiskBizItemRel> itemRelList = riskBizItemRelFeign.getAll(riskBizItemRel);
            if(itemRelList != null && itemRelList.size()>0){
                ifExisRisk = true;
            }
            model.addAttribute("ifExisRisk", ifExisRisk);
            String evalStartDate = appEval.getEvalStartDate();
            String evalEndDate = appEval.getEvalEndDate();
            map.put("entityData", appEval);
            map.put("evalAssess", evalAssess);
            map.put("avgScore", avgScoreList);
            map.put("maxScore", minScoreList);
            map.put("currentScore", currentScoreList);
            map.put("level", level);
            map.put("evalStartDate", evalStartDate);
            map.put("evalEndDate", evalEndDate);
            JSONObject jb = JSONObject.fromObject(map);
            dataMap = jb;
            evalAppNo = appEval.getEvalAppNo();
            dataMap.put("entityData", appEval);
            JsonTableUtil jtu = new JsonTableUtil();
            Ipage ipage = new Ipage();
            if (appEvalList.size() > 0 && appEvalList != null) {
                for (AppEval appeval : appEvalList) {
                    EvalScenceConfig esc = new EvalScenceConfig();
                    esc.setEvalScenceNo(appeval.getEvalScenceNo());
                    esc = evalScenceConfigFeign.getById(esc);
                    if (esc != null) {
                        appeval.setEvalScenceName(esc.getEvalScenceName());
                    }
                    if(StringUtil.isNotEmpty(appeval.getApprovalGrade())){
                        EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
                        evalScoreGradeConfig.setEvalLevel(appeval.getApprovalGrade());
                        evalScoreGradeConfig.setEvalClass(appeval.getEvalClass());
                        evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
                        if(evalScoreGradeConfig != null){
                            appeval.setApprovalGrade(evalScoreGradeConfig.getEvalGradeLevel());
                        }
                    }
                    if(StringUtil.isNotEmpty(appeval.getGrade())){
                        EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
                        evalScoreGradeConfig.setEvalLevel(appeval.getGrade());
                        evalScoreGradeConfig.setEvalClass(BizPubParm.EVAL_CLASS_1);
                        evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
                        if(evalScoreGradeConfig != null){
                            appeval.setGrade(evalScoreGradeConfig.getEvalGradeLevel());
                        }
                    }
                    if(StringUtil.isNotEmpty(appeval.getDebtGrade())){
                        EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
                        evalScoreGradeConfig.setEvalLevel(appeval.getDebtGrade());
                        evalScoreGradeConfig.setEvalClass(BizPubParm.EVAL_CLASS_2);
                        evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
                        if(evalScoreGradeConfig != null){
                            appeval.setDebtGrade(evalScoreGradeConfig.getEvalGradeLevel());
                        }
                    }

                }
            }

            if(BizPubParm.GRADE_TYPE_4.equals(appEval.getGradeType()) && BizPubParm.EVAL_CLASS_2.equals(appEval.getEvalClass())){
                tableId = "tableevaldebtlist";
            }
            String tableHtml = jtu.getJsonStr(tableId, "tableTag", appEvalList, ipage, true);
            dataMap.put("tableHtml", tableHtml);
            String evalType = BizPubParm.EVAL_TYPE_EXTERIOR;
            //外部评级不查询场景和模型
            if (!evalType.equals(appEval.getEvalType())) {
                map.put("evalIndexType", evalScenceConfig.getEvalIndexTypeRel());
                // 评级场景查询
                EvalScenceConfig evalScenceConfigTmp = new EvalScenceConfig();
                evalScenceConfigTmp.setEvalScenceNo(appEval.getEvalScenceNo());
                evalScenceConfigTmp = evalScenceConfigFeign.getById(evalScenceConfigTmp);
                String evalIndexTypeRel = evalScenceConfigTmp.getEvalIndexTypeRel();
                dataMap.putAll(appEvalFeign.getEvalListDataForResult(evalAppNo, appEval.getEvalScenceNo()));
                List <MfEvalGradeCard> evalGradeCardList = (List <MfEvalGradeCard>) dataMap.get("gradeCardListData");
                model.addAttribute("evalGradeCardList", evalGradeCardList);
            }
            dataMap = JSONObject.fromObject(dataMap);
            model.addAttribute("dataMap", dataMap);
            evalCompreVal.setEvalAppNo(evalAppNo);
            evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
            getObjValue(formeval2008, evalCompreVal);
            String detailFlag = "detail";
            model.addAttribute("formeval2008", formeval2008);
            model.addAttribute("detailFlag", detailFlag);
        }
        getObjValue(formeval1006, appEval);
        model.addAttribute("formeval1006", formeval1006);
        String evalRulesConfirmFlag = new CodeUtils().getSingleValByKey("EVAL_RULES_CONFIRM_FLAG");
        model.addAttribute("evalRulesConfirmFlag", evalRulesConfirmFlag);
        model.addAttribute("appEval", appEval);
        model.addAttribute("showName", showName);
        model.addAttribute("useType", useType);
        model.addAttribute("cusBaseType", cusBaseType);
        model.addAttribute("query", "");
        return "/component/eval/AppEval_DetailResult";
    }

    /**
     * 方法描述： 评级历史详情
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-9-22 下午2:36:35
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getEvalHisDetailResult")
    public String getEvalHisDetailResult(Model model, String evalAppNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        AppEval appEval = new AppEval();
        appEval.setEvalAppNo(evalAppNo);
        appEval = appEvalFeign.getById(appEval);
        String useType = appEval.getUseType();
        String parmName = "EVAL_LEVEL";
        String showName = "评级";
        if (BizPubParm.USE_TYPE_2.equals(useType)) {
            parmName = "RISK_PREVENT_LEVEL";
            showName = "风险检查";
        } else if (BizPubParm.USE_TYPE_3.equals(useType)){
            parmName = "RISK_PREVENT_LEVEL";
            showName = "风险检查";
        } else if (BizPubParm.USE_TYPE_4.equals(useType)){
            parmName = "RISK_PREVENT_LEVEL";
            showName = "授信测算";
        }else {
        }
        MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(appEval.getCusNo());
        String cusBaseType = mfCusCustomer.getCusBaseType();
        FormData formeval1006 = formService.getFormData("eval1006");
        FormData formeval2008 = formService.getFormData("eval2008");
        EvalCompreVal evalCompreVal = new EvalCompreVal();
        EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
        evalScenceConfig.setEvalScenceNo(appEval.getEvalScenceNo());
        evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
        Map <String, Object> scoreAllMap = new HashMap <String, Object>();
        scoreAllMap = appEvalFeign.getAllScoreForEchart(new HashMap <String, String>());
        List <Double> maxScoreList = new ArrayList <Double>();
        List <Double> minScoreList = new ArrayList <Double>();
        List <Double> avgScoreList = new ArrayList <Double>();
        List <Double> currentScoreList = new ArrayList <Double>();
        currentScoreList.add(appEval.getFinScore());
        currentScoreList.add(appEval.getDlScore());
        currentScoreList.add(appEval.getDxScore());
        currentScoreList.add(appEval.getAdjScore());
        currentScoreList.add(appEval.getManAdjScore());
        if (scoreAllMap != null) {
            // 最大值
            maxScoreList.add(Double.parseDouble(scoreAllMap.get("FINMAXSCORE").toString()));
            // maxScoreList.add(Double.parseDouble(scoreAllMap.get("DLMAXSCORE").toString())+Double.parseDouble(scoreAllMap.get("FINMAXSCORE").toString()));
            maxScoreList.add(Double.parseDouble(scoreAllMap.get("DLMAXSCORE").toString()));
            maxScoreList.add(Double.parseDouble(scoreAllMap.get("DXMAXSCORE").toString()));
            maxScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMAXSCORE").toString()));
            maxScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMAXSCORE").toString()));
            // 最小值
            minScoreList.add(Double.parseDouble(scoreAllMap.get("FINMINSCORE").toString()));
            minScoreList.add(Double.parseDouble(scoreAllMap.get("DLMINSCORE").toString()));
            minScoreList.add(Double.parseDouble(scoreAllMap.get("DXMINSCORE").toString()));
            minScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMINSCORE").toString()));
            minScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMINSCORE").toString()));
            // 平均值
            avgScoreList.add(Double.parseDouble(scoreAllMap.get("FINAVGSCORE").toString()));
            avgScoreList.add(Double.parseDouble(scoreAllMap.get("DLAVGSCORE").toString()));
            avgScoreList.add(Double.parseDouble(scoreAllMap.get("DXAVGSCORE").toString()));
            avgScoreList.add(Double.parseDouble(scoreAllMap.get("ADJAVGSCORE").toString()));
            avgScoreList.add(Double.parseDouble(scoreAllMap.get("MANGAVGSCORE").toString()));
        }
        Map <String, Object> map = new HashMap <String, Object>();
        CodeUtils cu = new CodeUtils();
        Map <String, String> cusTypeMap = cu.getMapByKeyName(parmName);
        Map <String, ParmDic> cusTypeMapObj = cu.getMapObjByKeyName(parmName);
        String evalAssess = "";
        if (cusTypeMapObj.get(appEval.getMangGrade()) != null) {
            evalAssess = cusTypeMapObj.get(appEval.getMangGrade()).getRemark();
        }
        String level = cusTypeMap.get(appEval.getMangGrade());
        if (appEval.getApprovalGrade() != null && !"".equals(appEval.getApprovalGrade())) {
            evalAssess = cusTypeMapObj.get(appEval.getApprovalGrade()).getRemark();
            level = cusTypeMap.get(appEval.getApprovalGrade());
        }
        if (level == null || "".equals(level)) {
        }
        if(StringUtil.isNotEmpty(appEval.getGrade())){
            level = cusTypeMap.get(appEval.getGrade());
            EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
            evalScoreGradeConfig.setEvalLevel(appEval.getGrade());
            evalScoreGradeConfig.setEvalClass(BizPubParm.EVAL_CLASS_1);
            evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
            level = evalScoreGradeConfig.getEvalGradeLevel();
        }
        if(StringUtil.isNotEmpty(appEval.getDebtGrade())){
            EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
            evalScoreGradeConfig.setEvalLevel(appEval.getDebtGrade());
            evalScoreGradeConfig.setEvalClass(BizPubParm.EVAL_CLASS_2);
            evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
            if(evalScoreGradeConfig != null){
                map.put("debtLevel", evalScoreGradeConfig.getEvalGradeLevel());
            }else{
                map.put("debtLevel", "未评估");
            }
        }else{
            map.put("debtLevel", "未评估");
        }
        boolean ifExisRisk = false;
        riskBizItemRel.setRelNo(appEval.getEvalAppNo());
        riskBizItemRel.setNodeNo(BizPubParm.WKF_NODE.cus_eval_tip.getNodeNo());
        riskBizItemRel.setRiskLevel(BizPubParm.EVAL_TRIGGER_LEVEL_2);
        List<RiskBizItemRel> itemRelList = riskBizItemRelFeign.getAll(riskBizItemRel);
        if(itemRelList != null && itemRelList.size()>0){
            ifExisRisk = true;
        }
        model.addAttribute("ifExisRisk", ifExisRisk);
        String evalStartDate = appEval.getEvalStartDate();
        String evalEndDate = appEval.getEvalEndDate();
        map.put("entityData", appEval);
        map.put("evalAssess", evalAssess);
        map.put("evalIndexType", evalScenceConfig.getEvalIndexTypeRel());
        map.put("avgScore", avgScoreList);
        map.put("maxScore", minScoreList);
        map.put("currentScore", currentScoreList);
        map.put("level", level);
        map.put("evalStartDate", evalStartDate);
        map.put("evalEndDate", evalEndDate);
        JSONObject jb = JSONObject.fromObject(map);
        dataMap = jb;
        evalAppNo = appEval.getEvalAppNo();
        // 评级场景查询
        EvalScenceConfig evalScenceConfigTmp = new EvalScenceConfig();
        evalScenceConfigTmp.setEvalScenceNo(appEval.getEvalScenceNo());
        evalScenceConfigTmp = evalScenceConfigFeign.getById(evalScenceConfigTmp);
        String evalIndexTypeRel = evalScenceConfigTmp.getEvalIndexTypeRel();
        dataMap.put("entityData", appEval);
        dataMap.putAll(appEvalFeign.getEvalListDataForResult(evalAppNo, appEval.getEvalScenceNo()));
        List <MfEvalGradeCard> evalGradeCardList = (List <MfEvalGradeCard>) dataMap.get("gradeCardListData");
        dataMap = JSONObject.fromObject(dataMap);
        evalCompreVal.setEvalAppNo(evalAppNo);
        evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
        getObjValue(formeval2008, evalCompreVal);
        getObjValue(formeval1006, appEval);
        String detailFlag = "hisDetail";
        model.addAttribute("evalGradeCardList", evalGradeCardList);
        model.addAttribute("detailFlag", detailFlag);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("appEval", appEval);
        model.addAttribute("useType", useType);
        model.addAttribute("showName", showName);
        model.addAttribute("formeval2008", formeval2008);
        model.addAttribute("formeval1006", formeval1006);
        model.addAttribute("cusBaseType", cusBaseType);
        model.addAttribute("query", "");
        return "/component/eval/AppEval_DetailResult";
    }

    /**
     * 方法描述： 获得评级评分卡详情
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-8-23 下午6:28:25
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getEvalCardDetailAjax")
    @ResponseBody
    public Map <String, Object> getEvalCardDetailAjax(String ajaxData, String cusNo, String appSts) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        AppEval appEval = new AppEval();
        appEval.setCusNo(cusNo);
        appEval.setEvalStage(appSts);
        List <AppEval> appEvalList = appEvalFeign.getListForCusNoAndSts(appEval);
        FormData formeval1006 = formService.getFormData("eval1006");
        FormData formeval2008 = formService.getFormData("eval2008");
        EvalCompreVal evalCompreVal = new EvalCompreVal();
        appEval = new AppEval();
        if (appEvalList != null && appEvalList.size() > 0) {
            appEval = appEvalList.get(0);
            EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
            evalScenceConfig.setEvalScenceNo(appEval.getEvalScenceNo());
            evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
            Map <String, Object> scoreAllMap = new HashMap <String, Object>();
            scoreAllMap = appEvalFeign.getAllScoreForEchart(new HashMap <String, String>());
            List <Double> maxScoreList = new ArrayList <Double>();
            List <Double> minScoreList = new ArrayList <Double>();
            List <Double> avgScoreList = new ArrayList <Double>();
            List <Double> currentScoreList = new ArrayList <Double>();
            currentScoreList.add(appEval.getFinScore());
            currentScoreList.add(appEval.getDlScore());
            currentScoreList.add(appEval.getDxScore());
            currentScoreList.add(appEval.getAdjScore());
            currentScoreList.add(appEval.getManAdjScore());
            if (scoreAllMap != null) {
                // 最大值
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("FINMAXSCORE").toString()));
                // maxScoreList.add(Double.parseDouble(scoreAllMap.get("DLMAXSCORE").toString())+Double.parseDouble(scoreAllMap.get("FINMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("DLMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("DXMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMAXSCORE").toString()));
                // 最小值
                minScoreList.add(Double.parseDouble(scoreAllMap.get("FINMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("DLMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("DXMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMINSCORE").toString()));
                // 平均值
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("FINAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("DLAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("DXAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("ADJAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("MANGAVGSCORE").toString()));
            }
            Map <String, Object> map = new HashMap <String, Object>();
            CodeUtils cu = new CodeUtils();
            Map <String, String> cusTypeMap = cu.getMapByKeyName("EVAL_LEVEL");
            Map <String, ParmDic> cusTypeMapObj = cu.getMapObjByKeyName("EVAL_LEVEL");
            String evalAssess = cusTypeMapObj.get(appEval.getMangGrade()).getRemark();
            String level = cusTypeMap.get(appEval.getMangGrade());
            if (appEval.getApprovalGrade() != null && !"".equals(appEval.getApprovalGrade())) {
                evalAssess = cusTypeMapObj.get(appEval.getApprovalGrade()).getRemark();
                level = cusTypeMap.get(appEval.getApprovalGrade());
            }

            map.put("entityData", appEval);
            map.put("evalAssess", evalAssess);
            map.put("evalIndexType", evalScenceConfig.getEvalIndexTypeRel());
            map.put("avgScore", avgScoreList);
            map.put("maxScore", minScoreList);
            map.put("currentScore", currentScoreList);
            map.put("level", level);
            dataMap.putAll(map);
            String evalAppNo = appEval.getEvalAppNo();
            // 评级场景查询
            EvalScenceConfig evalScenceConfigTmp = new EvalScenceConfig();
            evalScenceConfigTmp.setEvalScenceNo(appEval.getEvalScenceNo());
            evalScenceConfigTmp = evalScenceConfigFeign.getById(evalScenceConfigTmp);
            dataMap.put("entityData", appEval);
            dataMap.putAll(appEvalFeign.getEvalListData(evalAppNo, appEval.getEvalScenceNo()));
            List <MfEvalGradeCard> evalGradeCardList = (List <MfEvalGradeCard>) dataMap.get("gradeCardListData");
            evalCompreVal.setEvalAppNo(evalAppNo);
            evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
            getObjValue(formeval2008, evalCompreVal);
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        getObjValue(formeval1006, appEval);
        return dataMap;
    }

    /**
     * 方法描述： 获得评级评分卡详情
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-8-23 下午6:28:25
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getEvalCardDetailAjaxForResult")
    @ResponseBody
    public Map <String, Object> getEvalCardDetailAjaxForResult(String ajaxData, String cusNo, String appSts) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        AppEval appEval = new AppEval();
        appEval.setCusNo(cusNo);
        appEval.setEvalStage(appSts);
        List <AppEval> appEvalList = appEvalFeign.getListForCusNoAndSts(appEval);
        FormData formeval1006 = formService.getFormData("eval1006");
        FormData formeval2008 = formService.getFormData("eval2008");
        EvalCompreVal evalCompreVal = new EvalCompreVal();
        appEval = new AppEval();
        if (appEvalList != null && appEvalList.size() > 0) {
            appEval = appEvalList.get(0);
            EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
            evalScenceConfig.setEvalScenceNo(appEval.getEvalScenceNo());
            evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
            Map <String, Object> scoreAllMap = new HashMap <String, Object>();
            scoreAllMap = appEvalFeign.getAllScoreForEchart(new HashMap <String, String>());
            List <Double> maxScoreList = new ArrayList <Double>();
            List <Double> minScoreList = new ArrayList <Double>();
            List <Double> avgScoreList = new ArrayList <Double>();
            List <Double> currentScoreList = new ArrayList <Double>();
            currentScoreList.add(appEval.getFinScore());
            currentScoreList.add(appEval.getDlScore());
            currentScoreList.add(appEval.getDxScore());
            currentScoreList.add(appEval.getAdjScore());
            currentScoreList.add(appEval.getManAdjScore());
            if (scoreAllMap != null) {
                // 最大值
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("FINMAXSCORE").toString()));
                // maxScoreList.add(Double.parseDouble(scoreAllMap.get("DLMAXSCORE").toString())+Double.parseDouble(scoreAllMap.get("FINMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("DLMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("DXMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMAXSCORE").toString()));
                maxScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMAXSCORE").toString()));
                // 最小值
                minScoreList.add(Double.parseDouble(scoreAllMap.get("FINMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("DLMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("DXMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMINSCORE").toString()));
                minScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMINSCORE").toString()));
                // 平均值
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("FINAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("DLAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("DXAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("ADJAVGSCORE").toString()));
                avgScoreList.add(Double.parseDouble(scoreAllMap.get("MANGAVGSCORE").toString()));
            }
            Map <String, Object> map = new HashMap <String, Object>();
            CodeUtils cu = new CodeUtils();
            Map <String, String> cusTypeMap = cu.getMapByKeyName("EVAL_LEVEL");
            Map <String, ParmDic> cusTypeMapObj = cu.getMapObjByKeyName("EVAL_LEVEL");
            String evalAssess = cusTypeMapObj.get(appEval.getMangGrade()).getRemark();
            String level = cusTypeMap.get(appEval.getMangGrade());
            if (appEval.getApprovalGrade() != null && !"".equals(appEval.getApprovalGrade())) {
                evalAssess = cusTypeMapObj.get(appEval.getApprovalGrade()).getRemark();
                level = cusTypeMap.get(appEval.getApprovalGrade());
            }
            String evalRulesConfirmFlag = new CodeUtils().getSingleValByKey("EVAL_RULES_CONFIRM_FLAG");
            map.put("evalRulesConfirmFlag", evalRulesConfirmFlag);
            map.put("entityData", appEval);
            map.put("evalAssess", evalAssess);
            map.put("evalIndexType", evalScenceConfig.getEvalIndexTypeRel());
            map.put("avgScore", avgScoreList);
            map.put("maxScore", minScoreList);
            map.put("currentScore", currentScoreList);
            map.put("level", level);
            dataMap.putAll(map);
            String evalAppNo = appEval.getEvalAppNo();
            // 评级场景查询
            EvalScenceConfig evalScenceConfigTmp = new EvalScenceConfig();
            evalScenceConfigTmp.setEvalScenceNo(appEval.getEvalScenceNo());
            evalScenceConfigTmp = evalScenceConfigFeign.getById(evalScenceConfigTmp);
            dataMap.put("entityData", appEval);
            dataMap.putAll(appEvalFeign.getEvalListDataForResult(evalAppNo, appEval.getEvalScenceNo()));
            List <MfEvalGradeCard> evalGradeCardList = (List <MfEvalGradeCard>) dataMap.get("gradeCardListData");
            evalCompreVal.setEvalAppNo(evalAppNo);
            evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
            getObjValue(formeval2008, evalCompreVal);
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        getObjValue(formeval1006, appEval);
        return dataMap;
    }


    /**
     *
     * 方法描述： 获得未提交的评级申请
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2016-11-23 下午1:48:27
     */
    @RequestMapping("/getUnfinishedAppEval")
    @ResponseBody
    public Map<String, Object> getUnfinishedAppEval(String cusNo, String cusBaseType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        AppEval appEval = new AppEval();
        List<AppEval> appEvalList = appEvalFeign.getUnfinishedList(cusNo);
        if (BizPubParm.CUS_TYPE_CORP.equals(cusBaseType)) {
            CusFinMain cusFinMain = new CusFinMain();
            cusFinMain.setCusNo(cusNo);
            List<CusFinMain> cusFinMainList = cusFinMainFeign.getAll(cusFinMain);
            Boolean isFlag = false;
            String reportConfirmFlag =  new CodeUtils().getSingleValByKey("REPORT_CONFIRM_FLAG");
            for (CusFinMain c : cusFinMainList) {

                if("2".equals(reportConfirmFlag)){
                    isFlag = true;
                }else{
                    if (c.getFinRptSts().equals("1")) {
                        isFlag = true;
                    }
                }
            }
            if (isFlag) {
                dataMap.put("status", "1");
            } else {

                dataMap.put("status", "0");
                if("2".equals(reportConfirmFlag)){
                    dataMap.put("msg", MessageEnum.FIRST_FINC_VERIFY2.getMessage());
                }else{
                    dataMap.put("msg", MessageEnum.FIRST_FINC_VERIFY.getMessage());
                }

            }
        } else {
            dataMap.put("status", "1");
        }
        if (appEvalList != null && appEvalList.size() > 0) {
            appEval = appEvalList.get(0);
            String evalAppNo = appEval.getEvalAppNo();
            if ("2".equals(appEval.getEvalSts())) {// 审批中
                dataMap.put("flag", "2");
            } else if ("0".equals(appEval.getEvalSts()) || "1".equals(appEval.getEvalSts())) {// 未提交
                dataMap.put("flag", "1");
            } else if ("4".equals(appEval.getEvalSts())) {// 审批完成
                dataMap.put("flag", "4");
                String nowTime = DateUtil.getDate();
                String endTime = appEval.getEvalEndDate();
                if (null!=endTime) {
                    //评级到期时间比较发起评级的当前时间,1为未到期2为到期（针对已经存在评级完成）
                    Object timeLimit = endTime.compareTo(nowTime) > 0 ? "1":"2";
                    dataMap.put("timeLimit",timeLimit);
                }

            }else {
            }
            dataMap.put("evalAppNo", evalAppNo);
        } else {
            dataMap.put("flag", "0");
        }
        return dataMap;
    }


    /**
     * 方法描述： 获得未提交的评级申请
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2016-11-23 下午1:48:27
     */
    @RequestMapping("/getUnfinishedAppEvalUseType")
    @ResponseBody
    public Map <String, Object> getUnfinishedAppEval(String param) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        Map <String, Object> paramMap = new Gson().fromJson(param, Map.class);
        AppEval appEval = new AppEval();
        List <AppEval> appEvalList = appEvalFeign.getUnfinishedListUseType(paramMap);
        String useType = (String) paramMap.get("useType");
        String cusNo = (String) paramMap.get("cusNo");
        MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
        if (BizPubParm.USE_TYPE_1.equals(useType)) {
            if (BizPubParm.CUS_TYPE_CORP.equals(mfCusCustomer.getCusBaseType())) {
                MfCusReportAcount mfCusReportAcount = new MfCusReportAcount();
                mfCusReportAcount.setCusNo(cusNo);
                List<MfCusReportAcount> acounts = mfCusReportAcountFeign.getList(mfCusReportAcount);
                Boolean isFlag = false;
                String reportConfirmFlag = new CodeUtils().getSingleValByKey("REPORT_CONFIRM_FLAG");
                for ( MfCusReportAcount c : acounts) {
                    if ("2".equals(reportConfirmFlag)) {
                        isFlag = true;
                    } else if ("2".equals(c.getReportSts())) {
                        isFlag = true;
                    }else {
                    }
                }
                if (isFlag) {
                    dataMap.put("status", "1");
                } else {
                    dataMap.put("status", "0");
                    if ("2".equals(reportConfirmFlag)) {
                        dataMap.put("msg", MessageEnum.FIRST_FINC_VERIFY2.getMessage());
                    } else {
                        dataMap.put("msg", MessageEnum.FIRST_FINC_VERIFY.getMessage());
                    }
                }
            } else {
                dataMap.put("status", "1");
            }
            //校验是否进行信用评级
            String gradeType = (String) paramMap.get("gradeType");
            if(BizPubParm.GRADE_TYPE_4.equals(gradeType)){
                String evalClass = (String) paramMap.get("evalClass");
                if(BizPubParm.EVAL_CLASS_2.equals(evalClass)){
                    AppEval queryEval = new AppEval();
                    queryEval.setCusNo(cusNo);
                    queryEval.setUseType(useType);
                    queryEval.setGradeType(gradeType);
                    queryEval.setEvalClass(BizPubParm.EVAL_CLASS_1);
                    queryEval.setEvalSts("4");
                    //暂时不添加关联编号
//                  queryEval.setRelNo((String) paramMap.get("relNo"));
                    List <AppEval> evalList = appEvalFeign.getListForCusNoAndSts(queryEval);
                    if(evalList == null || evalList.size()==0){
                        dataMap.put("ifCreditEval", "0");
                        dataMap.put("warnMsg", "请先进行信用评级！");
                    }
                }
            }
        } else {
            dataMap.put("status", "1");
        }

        if (appEvalList != null && appEvalList.size() > 0) {
            appEval = appEvalList.get(0);
            String evalAppNo = appEval.getEvalAppNo();
            if ("2".equals(appEval.getEvalSts())) {// 审批中
                dataMap.put("flag", "2");
            } else if ("0".equals(appEval.getEvalSts()) || "1".equals(appEval.getEvalSts())) {// 未提交
                dataMap.put("flag", "1");
            } else if ("4".equals(appEval.getEvalSts())) {// 审批完成
                dataMap.put("flag", "4");
                String nowTime = DateUtil.getDate();
                String endTime = appEval.getEvalEndDate();
                if (null != endTime) {
                    //评级到期时间比较发起评级的当前时间,1为未到期2为到期（针对已经存在评级完成）
                    Object timeLimit = endTime.compareTo(nowTime) > 0 ? "1" : "2";
                    dataMap.put("timeLimit", timeLimit);
                }

            }else {
            }
            dataMap.put("evalAppNo", evalAppNo);
        } else {
            dataMap.put("flag", "0");
        }
        return dataMap;
    }


    /**
     * 检查是否填写了财务报表
     *
     * @author LJW date 2017-2-27
     */
    @RequestMapping("/checkFinanceStatement")
    @ResponseBody
    public Map <String, Object> checkFinanceStatement(String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        String wkfAppId = request.getParameter("wkfAppId");
        Map <String, Object> dataMap = new HashMap <String, Object>();
        CusFinMain cusFinMain = new CusFinMain();
        cusFinMain.setCusNo(cusNo);
        List <CusFinMain> cusFinMainList = cusFinMainFeign.getAll(cusFinMain);
        Boolean isFlag = false;
        for (CusFinMain c : cusFinMainList) {
            if ("1".equals(c.getFinRptSts())) {
                isFlag = true;
            }
        }
        if (isFlag) {
            dataMap.put("fullFlag", "1"); // 财务报表信息完整
            // 业务进入下一个流程
            TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
            String url = "";
            String transition = workflowDwrFeign.findNextTransition(task.getId());
            wkfInterfaceFeign.doCommit(task.getId(), AppConstant.OPINION_TYPE_ARREE, url, transition,
                    User.getRegNo(request), "");
        } else {
            dataMap.put("fullFlag", "0"); // 财务报表信息不完整
            dataMap.put("msg", "请完善客户财务报表信息");
        }
        return dataMap;
    }

    /**
     * 查看评级结果
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getDetailResultPers")
    public String getDetailResultPers(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        List <AppEval> appEvalList = appEvalFeign.getListForCusNo(cusNo);
        FormData formeval1006 = formService.getFormData("eval1006");
        AppEval appEval = new AppEval();
        if (appEvalList != null && appEvalList.size() > 0) {
            for (AppEval appEcalTemp : appEvalList) {
                if ("4".equals(appEcalTemp.getEvalSts())) {
                    appEval = appEcalTemp;
                    EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
                    evalScenceConfig.setEvalScenceNo(appEval.getEvalScenceNo());
                    evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
                    Map <String, Object> scoreAllMap = new HashMap <String, Object>();
                    scoreAllMap = appEvalFeign.getAllScoreForEchart(null);
                    List <Double> maxScoreList = new ArrayList <Double>();
                    List <Double> minScoreList = new ArrayList <Double>();
                    List <Double> avgScoreList = new ArrayList <Double>();
                    List <Double> currentScoreList = new ArrayList <Double>();
                    currentScoreList.add(appEval.getDlScore() + appEval.getFinScore());
                    currentScoreList.add(appEval.getDxScore());
                    currentScoreList.add(appEval.getAdjScore());
                    currentScoreList.add(appEval.getManAdjScore());
                    if (scoreAllMap != null) {
                        // 最大值
                        maxScoreList.add(Double.parseDouble(scoreAllMap.get("DLMAXSCORE").toString())
                                + Double.parseDouble(scoreAllMap.get("FINMAXSCORE").toString()));
                        maxScoreList.add(Double.parseDouble(scoreAllMap.get("DXMAXSCORE").toString()));
                        maxScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMAXSCORE").toString()));
                        maxScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMAXSCORE").toString()));
                        // 最小值
                        minScoreList.add(Double.parseDouble(scoreAllMap.get("DLMINSCORE").toString())
                                + Double.parseDouble(scoreAllMap.get("FINMINSCORE").toString()));
                        minScoreList.add(Double.parseDouble(scoreAllMap.get("DXMINSCORE").toString()));
                        minScoreList.add(Double.parseDouble(scoreAllMap.get("ADJMINSCORE").toString()));
                        minScoreList.add(Double.parseDouble(scoreAllMap.get("MANGMINSCORE").toString()));
                        // 平均值
                        avgScoreList.add(Double.parseDouble(scoreAllMap.get("DLAVGSCORE").toString())
                                + Double.parseDouble(scoreAllMap.get("FINAVGSCORE").toString()));
                        avgScoreList.add(Double.parseDouble(scoreAllMap.get("DXAVGSCORE").toString()));
                        avgScoreList.add(Double.parseDouble(scoreAllMap.get("ADJAVGSCORE").toString()));
                        avgScoreList.add(Double.parseDouble(scoreAllMap.get("MANGAVGSCORE").toString()));
                    }
                    Map <String, Object> map = new HashMap <String, Object>();
                    String evalAssess = evalSysAssessFeign.getByLevel(appEval.getMangGrade());
                    map.put("entityData", appEval);
                    map.put("evalAssess", evalAssess);
                    map.put("evalIndexType", evalScenceConfig.getEvalIndexTypeRel());
                    map.put("avgScore", avgScoreList);
                    map.put("maxScore", minScoreList);
                    map.put("currentScore", currentScoreList);
                    JSONObject jb = JSONObject.fromObject(map);
                    dataMap = jb;
                    break;
                }
            }
        }
        getObjValue(formeval1006, appEval);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("formeval1006", formeval1006);
        model.addAttribute("query", "");
        return "/component/eval/getDetailResultPers";
    }

    /**
     * 评级申请
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/initiateApp")
    public String initiateApp(Model model, String param, String wkfAppId, String timeLimit) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formappeval0001 = new FormData();
        Map <String, Object> paramMap = new Gson().fromJson(param, Map.class);
        String useType = (String) paramMap.get("useType");
        String gradeType = (String) paramMap.get("gradeType");
        String cusNo = (String) paramMap.get("cusNo");
        String appId = (String) paramMap.get("appId");
        String relNo = (String) paramMap.get("relNo");
        String evalClass = (String) paramMap.get("evalClass");
        String creditAppId = (String) paramMap.get("creditAppId");
        model.addAttribute("timeLimit", timeLimit);
        MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
        FormService formService = new FormService();
        Boolean getFlag = false;
        model.addAttribute("getFlag", getFlag);
        FormData formeval1001 = formService.getFormData("eval1001");
        FormData formeval1003 = formService.getFormData("eval1003");
        FormData formeval2006 = formService.getFormData("eval2006");
        FormData formeval2007 = formService.getFormData("eval2007");

        //如果是债项评级取同一个申请表单
        if(BizPubParm.GRADE_TYPE_4.equals(gradeType) && BizPubParm.EVAL_CLASS_2.equals(evalClass)){
            formappeval0001 = formService.getFormData("debteval0001");
        }else{
            if (BizPubParm.CUS_TYPE_PERS.equals(mfCusCustomer.getCusBaseType())) {
                formappeval0001 = formService.getFormData("appevalpers0001");
                formeval1001 = formService.getFormData("evalpers1001");
            }else{
                formappeval0001 = formService.getFormData("appeval0001");
            }
        }
        AppEval appEval = new AppEval();
        appEval.setRelNo(relNo);
        if (BizPubParm.USE_TYPE_1.equals(useType)) {//客户评级
            //获得客户的财务报表
            CusFinMain cusFinMain = new CusFinMain();
            cusFinMain.setCusNo(cusNo);
            cusFinMain.setAccRule("1");
            List <CusFinMain> cusFinMainList = cusFinMainFeign.getListByAccRule(cusFinMain);
            List <CusFinMain> cusFinMainYearList = new ArrayList <CusFinMain>();
            //找出最新一期的报表
            for (int i = 0; i < cusFinMainList.size(); i++) {
                //年报
                if ("3".equals(cusFinMainList.get(i).getFinRptType())) {
                    cusFinMainYearList.add(cusFinMainList.get(i));
                }
            }
            //获得年报最新的
            Integer finRpt = -1;
            if (cusFinMainYearList.size() > 0) {
                for (int j = 0; j < cusFinMainYearList.size(); j++) {
                    CusFinMain cusFinMainNew = cusFinMainYearList.get(j);
                    Integer finRptDateInteger = Integer.parseInt(cusFinMainNew.getFinRptDate());
                    if (finRptDateInteger > finRpt) {
                        finRpt = finRptDateInteger;
                    }
                }
                appEval.setRptType("3");
                appEval.setRptDate(finRpt.toString());
            }
        }

        if (mfCusCustomer != null) {
            String scenceNo = BizPubParm.EVAL_CREDIT_SCEN_NO;
            String cusType = mfCusCustomer.getCusType();
            String cusBaseType = mfCusCustomer.getCusBaseType();
            model.addAttribute("scenceNo", scenceNo);
            model.addAttribute("cusType", cusType);
            model.addAttribute("cusBaseType", cusBaseType);
            appEval.setCusNo(cusNo);
            appEval.setCusName(mfCusCustomer.getCusName());
            appEval.setCusType(mfCusCustomer.getCusType());
            appEval.setEvalScenceNo(scenceNo);
            appEval.setAppId(appId);
            appEval.setCreditAppId(creditAppId);
            appEval.setUseType(useType);
            appEval.setTotalCredited(mfCusCustomer.getPreCreditSum());
        }
        String showName = "评级";
        String pinFenName ="评级卡";
        if(BizPubParm.USE_TYPE_2.equals(useType)){
            showName = "风险检查";
            formappeval0001 = formService.getFormData("appRiskCheckAdd");
            formeval1001 = formService.getFormData("appRiskCheckDetail");
        }else if(BizPubParm.USE_TYPE_3.equals(useType)){
            showName = "风险检查";
            formappeval0001 = formService.getFormData("creditRiskCheckAdd");
            formeval1001 = formService.getFormData("creditRiskCheckDetail");
        }else if(BizPubParm.USE_TYPE_4.equals(useType)){
            showName = "授信测算";
            formappeval0001 = formService.getFormData("creditRiskCheckAdd");
            formeval1001 = formService.getFormData("creditRiskCheckDetail");
            pinFenName ="测算卡";
        }else {
        }
        getObjValue(formappeval0001, appEval);
        getObjValue(formeval1001, appEval);
        model.addAttribute("formeval1001", formeval1001);
        model.addAttribute("formeval1003", formeval1003);
        model.addAttribute("formeval2006", formeval2006);
        model.addAttribute("formeval2007", formeval2007);
        model.addAttribute("formappeval0001", formappeval0001);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("appId", appId);
        model.addAttribute("showName", showName);
        model.addAttribute("pinFenName", pinFenName);
        model.addAttribute("creditAppId", creditAppId);
        model.addAttribute("useType", useType);
        model.addAttribute("gradeType", gradeType);
        model.addAttribute("evalClass", evalClass);
        model.addAttribute("query", "");
        return "/component/eval/AppEval_InitiateApp";
    }

    /**
     * 外部评级申请
     *
     * @return
     */
    @RequestMapping("/initManEavl")
    public String initManEavl(Model model, String cusNo, String timeLimit) {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormService formService = new FormService();
        Boolean getFlag = false;
        model.addAttribute("getFlag", getFlag);
        model.addAttribute("timeLimit", timeLimit);
        FormData formeval1001 = formService.getFormData("evalpersman");
/*		FormData formeval1003 = formService.getFormData("eval1003");
		FormData formeval2006 = formService.getFormData("eval2006");
		FormData formeval2007 = formService.getFormData("eval2007");
		FormData formappeval0001 = formService.getFormData("appeval0001");*/
        AppEval appEval = new AppEval();
        try {
            MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
            if (mfCusCustomer != null) {
                String scenceNo = BizPubParm.EVAL_CREDIT_SCEN_NO;
                String cusType = mfCusCustomer.getCusType();
                String cusBaseType = mfCusCustomer.getCusBaseType();
                model.addAttribute("scenceNo", scenceNo);
                model.addAttribute("cusType", cusType);
                model.addAttribute("cusBaseType", cusBaseType);
                // 个人
                if (BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)) {
                    //formappeval0001 = formService.getFormData("appevalpers0001");
                    formeval1001 = formService.getFormData("evalpersman");
                }
                appEval.setCusNo(cusNo);
                appEval.setCusName(mfCusCustomer.getCusName());
                appEval.setCusType(mfCusCustomer.getCusType());
                appEval.setEvalScenceNo(scenceNo);
                //getObjValue(formappeval0001, appEval);
                String projectName = ymlConfig.getSysParams().get("sys.project.name");
                getObjValue(formeval1001, appEval);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("formevalpersman", formeval1001);
/*		model.addAttribute("formeval1003", formeval1003);
		model.addAttribute("formeval2006", formeval2006);
		model.addAttribute("formeval2007", formeval2007);
		model.addAttribute("formappeval0001", formappeval0001);*/
        //String year = DateUtil.getYear()+"";
        String[] date = DateUtil.getYearMonthDay();
        String year = date[0];
        String monthDay = date[1] + date[2];
        String evalAppNo = "CRE-EVEAl-" + year + "-" + (int) ((Math.random() * 9 + 1) * 1000000);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("evalAppNo", evalAppNo);
        model.addAttribute("evalType", BizPubParm.EVAL_TYPE_EXTERIOR);
        model.addAttribute("query", "");
        return "/component/eval/AppEval_InitManEavl";
    }

    /**
     * 评级个人申请
     *
     * @return
     */
    @RequestMapping("/initiateAppPers")
    public String initiateAppPers(Model model, String cusNo, String cusType) {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Boolean getFlag = false;
        model.addAttribute("getFlag", getFlag);
        FormData formeval1001 = formService.getFormData("eval1001");
        FormData formeval1003 = formService.getFormData("eval1003");
        FormData formeval2006 = formService.getFormData("eval2006");
        FormData formeval2007 = formService.getFormData("eval2007");
        AppEval appEval = new AppEval();
        try {
            Object obj = cusInterfaceFeign.getCusByCusNo(cusNo);
            if (obj != null) {
                JSONObject jb = JSONObject.fromObject(obj);
                String scenceNo = BizPubParm.EVAL_CREDIT_PERS_SCEN_NO;
                cusNo = jb.getString("cusNo");
                String cusName = jb.getString("cusName");
                appEval.setCusNo(cusNo);
                appEval.setCusName(cusName);
                appEval.setCusType(cusType);
                appEval.setEvalScenceNo(scenceNo);
                getObjValue(formeval1003, appEval);
                getObjValue(formeval1001, appEval);
                EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
                evalScenceConfig.setEvalScenceNo(scenceNo);
                evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
                String evalIndexTypeRel = evalScenceConfig.getEvalIndexTypeRel();

                this.changeFormProperty(formeval1001, "rptDate", "fieldType", 99);
                this.changeFormProperty(formeval1001, "rptDate", "mustInput", "0");
                this.changeFormProperty(formeval1003, "rptDate", "fieldType", 99);
                this.changeFormProperty(formeval1003, "rptDate", "mustInput", "0");
                if (evalIndexTypeRel.indexOf("3") == -1) {
                    this.changeFormProperty(formeval2006, "dxScore", "fieldType", 99);
                    this.changeFormProperty(formeval2006, "dxScore", "mustInput", "0");
                    this.changeFormProperty(formeval2006, "dxScorePercent", "fieldType", 99);
                    this.changeFormProperty(formeval2006, "dxScorePercent", "mustInput", "0");
                }
                if (evalIndexTypeRel.indexOf("1") == -1 && evalIndexTypeRel.indexOf("2") == -1) {
                    this.changeFormProperty(formeval2006, "dlScore", "fieldType", 99);
                    this.changeFormProperty(formeval2006, "dlScore", "mustInput", "0");
                    this.changeFormProperty(formeval2006, "dlScorePercent", "fieldType", 99);
                    this.changeFormProperty(formeval2006, "dlScorePercent", "mustInput", "0");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("formeval1001", formeval1001);
        model.addAttribute("formeval1003", formeval1003);
        model.addAttribute("formeval2006", formeval2006);
        model.addAttribute("formeval2007", formeval2007);
        model.addAttribute("query", "");
        return "/component/eval/initiateAppPers";
    }

    /**
     * 评级查看或修改
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getDetailInfo")
    public String getDetailInfo(Model model, String cusNo, String evalAppNo, String wkfAppId) {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        Boolean getFlag = true;
        model.addAttribute("getFlag", getFlag);
        FormData formeval1001 = formService.getFormData("eval1001");
        FormData formeval1003 = formService.getFormData("eval1003");
        FormData formeval2006 = formService.getFormData("eval2006");// 综合评分
        FormData formeval2007 = formService.getFormData("eval2007");// 综合评分
        AppEval appEval = new AppEval();
        String showName = "评级";
        String pinFenName ="评级卡";
        try {
            appEval.setEvalAppNo(evalAppNo);
            appEval = appEvalFeign.getById(appEval);
            model.addAttribute("appEval", appEval);
            String appSts = appEval.getEvalSts();
            String useType = appEval.getUseType();
            if(StringUtil.isEmpty(cusNo)){
                cusNo = appEval.getCusNo();
            }
            model.addAttribute("appSts", appSts);
            MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
            if (mfCusCustomer != null) {
                String cusBaseType = mfCusCustomer.getCusBaseType();
                // 个人
                if (BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)) {
                    formeval1001 = formService.getFormData("evalpers1001");
                }
                model.addAttribute("cusBaseType", cusBaseType);
                model.addAttribute("cusType", mfCusCustomer.getCusType());
                appEval.setTotalCredited(mfCusCustomer.getPreCreditSum());
            }
            if(BizPubParm.USE_TYPE_2.equals(useType)){
                showName = "风险检查";
                formeval1001 = formService.getFormData("appRiskCheckDetail");
            }else if(BizPubParm.USE_TYPE_3.equals(useType)){
                showName = "风险检查";
                formeval1001 = formService.getFormData("creditRiskCheckDetail");
            }else if(BizPubParm.USE_TYPE_4.equals(useType)){
                showName = "授信测算";
                pinFenName ="测算卡";
                formeval1001 = formService.getFormData("creditRiskCheckDetail");
            }else {
            }
            model.addAttribute("useType", useType);
            getObjValue(formeval1001, appEval);
            getObjValue(formeval1003, appEval);
            dataMap.put("entityData", appEval);
            String evalScenceNo = appEval.getEvalScenceNo();
            model.addAttribute("evalScenceNo", evalScenceNo);
            // 评级场景查询
            EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
            evalScenceConfig.setEvalScenceNo(evalScenceNo);
            evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
			/*String evalIndexTypeRel = evalScenceConfig.getEvalIndexTypeRel();
			model.addAttribute("evalIndexTypeRel", evalIndexTypeRel);*/
            // 综合查询
//            EvalCompreVal evalCompreVal = new EvalCompreVal();
//            evalCompreVal.setEvalAppNo(evalAppNo);
//            evalCompreVal.setEvalScenceNo(evalScenceNo);
//            evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
//            getObjValue(formeval1001, evalCompreVal);
//            getObjValue(formeval2006, evalCompreVal);
//            getObjValue(formeval2007, evalCompreVal);
            // 定性值
            EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
            evalScenceDxVal.setEvalAppNo(evalAppNo);
            evalScenceDxVal.setEvalScenceNo(evalScenceNo);
            evalScenceDxVal = evalScenceDxValFeign.getById(evalScenceDxVal);
            model.addAttribute("evalScenceDxVal", evalScenceDxVal);
            dataMap.putAll(appEvalFeign.getEvalListData(evalAppNo, evalScenceNo));
            List <MfEvalGradeCard> evalGradeCardList = (List <MfEvalGradeCard>) dataMap.get("gradeCardListData");
            model.addAttribute("evalGradeCardList", evalGradeCardList);
            dataMap = JSONObject.fromObject(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("evalAppNo", evalAppNo);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("showName", showName);
        model.addAttribute("formeval1001", formeval1001);
        model.addAttribute("formeval1003", formeval1003);
        model.addAttribute("formeval2006", formeval2006);
        model.addAttribute("formeval2007", formeval2007);
        model.addAttribute("pinFenName", pinFenName);
        model.addAttribute("query", "");
        return "/component/eval/AppEval_InitiateApp";
    }

    /**
     * 评级查看或修改
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getDetailInfoPers")
    public String getDetailInfoPers(Model model, String cusNo, String evalAppNo) {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        Boolean getFlag = true;
        model.addAttribute("getFlag", getFlag);
        FormData formeval1001 = formService.getFormData("eval1001");
        FormData formeval2006 = formService.getFormData("eval2006");// 综合评分
        FormData formeval2007 = formService.getFormData("eval2007");// 综合评分
        AppEval appEval = new AppEval();
        try {
            appEval.setEvalAppNo(evalAppNo);
            appEval = appEvalFeign.getById(appEval);
            String appSts = appEval.getEvalSts();
            model.addAttribute("appSts", appSts);
            getObjValue(formeval1001, appEval);
            dataMap.put("entityData", appEval);
            String evalScenceNo = appEval.getEvalScenceNo();
            // 评级场景查询
            EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
            evalScenceConfig.setEvalScenceNo(evalScenceNo);
            evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
            String evalIndexTypeRel = evalScenceConfig.getEvalIndexTypeRel();
            // 综合查询
            EvalCompreVal evalCompreVal = new EvalCompreVal();
            evalCompreVal.setEvalAppNo(evalAppNo);
            evalCompreVal.setEvalScenceNo(evalScenceNo);
            evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
            getObjValue(formeval2006, evalCompreVal);
            getObjValue(formeval2007, evalCompreVal);
            // 定性值
            EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
            evalScenceDxVal.setEvalAppNo(evalAppNo);
            evalScenceDxVal.setEvalScenceNo(evalScenceNo);
            evalScenceDxVal = evalScenceDxValFeign.getById(evalScenceDxVal);
            model.addAttribute("evalScenceDxVal", evalScenceDxVal);

            getListData(dataMap, evalIndexTypeRel, evalScenceNo, evalAppNo);
            getDlFinScore(dataMap, evalScenceNo, evalAppNo);
            dataMap = JSONObject.fromObject(dataMap);

            this.changeFormProperty(formeval1001, "rptDate", "fieldType", 99);
            this.changeFormProperty(formeval1001, "rptDate", "mustInput", "0");
            if (evalIndexTypeRel.indexOf("3") == -1) {
                this.changeFormProperty(formeval2006, "dxScore", "fieldType", 99);
                this.changeFormProperty(formeval2006, "dxScore", "mustInput", "0");
                this.changeFormProperty(formeval2006, "dxScorePercent", "fieldType", 99);
                this.changeFormProperty(formeval2006, "dxScorePercent", "mustInput", "0");
            }
            if (evalIndexTypeRel.indexOf("1") == -1 && evalIndexTypeRel.indexOf("2") == -1) {
                this.changeFormProperty(formeval2006, "dlScore", "fieldType", 99);
                this.changeFormProperty(formeval2006, "dlScore", "mustInput", "0");
                this.changeFormProperty(formeval2006, "dlScorePercent", "fieldType", 99);
                this.changeFormProperty(formeval2006, "dlScorePercent", "mustInput", "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("formeval1001", formeval1001);
        model.addAttribute("formeval2006", formeval2006);
        model.addAttribute("formeval2007", formeval2007);
        model.addAttribute("query", "");
        return "/component/eval/getDetailInfoPers";
    }

    /**
     * 方法描述： 获得定性评分详情
     *
     * @return String
     * @author 沈浩兵
     * @date 2016-12-22 下午3:44:33
     */
    @RequestMapping("/getDxDetailInfoAjax")
    @ResponseBody
    public Map <String, Object> getDxDetailInfoAjax(String ajaxData, String evalScenceNo, String evalAppNo) {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        Map <String, Object> listData = new HashMap <String, Object>();
        try {
            List <EvalScenceDxRel> evalScenceDxRelList = evalScenceDxRelFeign.getForScenceNo(evalScenceNo);
            listData.put("DX", evalScenceDxRelList);
            dataMap.put("listData", listData);
            EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
            evalScenceDxVal.setEvalAppNo(evalAppNo);
            evalScenceDxVal.setEvalScenceNo(evalScenceNo);
            evalScenceDxVal = evalScenceDxValFeign.getById(evalScenceDxVal);
            dataMap.put("dxData", evalScenceDxVal);
        } catch (Exception e) {
            e.printStackTrace();
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
    public Map <String, Object> insertAjax(String ajaxData, String cusBaseType) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            FormData formappeval0001 = formService.getFormData("appeval0001");
            // 个人
            if (BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)) {
                formappeval0001 = formService.getFormData("appevalpers0001");
            }
            getFormValue(formappeval0001, getMapByJson(ajaxData));
            if (this.validateFormData(formappeval0001)) {
                AppEval appEval = new AppEval();
                setObjValue(formappeval0001, appEval);
                boolean validataFalag = true;
                String rptDate = appEval.getRptDate();
                String evalEndDate = appEval.getEvalEndDate();
                String sysData = User.getSysDate(this.getHttpRequest());
                // 获取财务指标参数
                Map <String, Double> pfsMap = new HashMap <String, Double>();
                Map <String, Object> appMap = new HashMap <String, Object>();
                if (!BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)) {
                    // 调整参数传递
                    CusFinCapData cusFinCapData = new CusFinCapData();
                    cusFinCapData.setCusNo(appEval.getCusNo());
                    cusFinCapData.setFinRptDate(appEval.getRptDate());
                    cusFinCapData.setFinRptType(appEval.getRptType());
                    pfsMap = pfsInterfaceFeign.getFinIndicators(cusFinCapData);
                    if (pfsMap == null) {
                        validataFalag = false;
                        dataMap.put("msg", MessageEnum.NO_FILE.getMessage("报表"));
                    }
                    if (validataFalag && Integer.parseInt(sysData) <= Integer.parseInt(rptDate)) {
                        validataFalag = false;
                        Map <String, String> paramMap = new HashMap <String, String>();
                        paramMap.put("timeOne", "财报日期");
                        paramMap.put("timeTwo", "系统日期");
                        dataMap.put("msg", MessageEnum.NOT_FORM_TIME.getMessage(paramMap));
                    }

                }
                if (validataFalag && Integer.parseInt(sysData) > Integer.parseInt(evalEndDate)) {
                    validataFalag = false;
                    Map <String, String> paramMap = new HashMap <String, String>();
                    paramMap.put("timeOne", "到期日期");
                    paramMap.put("timeTwo", "系统日期");
                    dataMap.put("msg", MessageEnum.NOT_SMALL_TIME.getMessage(paramMap));
                }
                // 业务参数
                if (validataFalag) {
                    appEval.setEvalSts("0");
                    // appEval.setEvalStartDate(User.getSysDate(this.getHttpRequest()));
                    appEval.setEvalStartDate(DateUtil.getDate());
                    new FeignSpringFormEncoder().addParamsToBaseDomain(appEval);
                    appMap.put("appEval", appEval);
                    appMap.put("pfsMap", pfsMap);
                    appEval = appEvalFeign.insert(appMap);
                    String evalAppNo = appEval.getEvalAppNo();
                    String evalScenceNo = appEval.getEvalScenceNo();
                    EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
                    evalScenceConfig.setEvalScenceNo(evalScenceNo);
                    evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
                    String evalIndexTypeRel = evalScenceConfig.getEvalIndexTypeRel();
                    dataMap.putAll(appEvalFeign.getEvalListData(evalAppNo, evalScenceNo));
                    // 综合查询
                    EvalCompreVal evalCompreVal = new EvalCompreVal();
                    evalCompreVal.setEvalAppNo(evalAppNo);
                    evalCompreVal.setEvalScenceNo(evalScenceNo);
                    evalCompreVal = evalCompreValFeign.getById(evalCompreVal);

                    dataMap.put("ECV", evalCompreVal);
                    dataMap.put("entityData", appEval);
                    dataMap.put("evalIndexTypeRel", evalIndexTypeRel);
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                } else {
                    dataMap.put("flag", "error");
                }
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
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
    @RequestMapping("/insertPersAjax")
    @ResponseBody
    public Map <String, Object> insertPersAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            FormData formeval1003 = formService.getFormData("eval1003");
            this.changeFormProperty(formeval1003, "rptDate", "fieldType", 99);
            this.changeFormProperty(formeval1003, "rptDate", "mustInput", "0");
            getFormValue(formeval1003, getMapByJson(ajaxData));
            Map <String, Double> cusMap = new HashMap <String, Double>();
            Map <String, Object> appMap = new HashMap <String, Object>();
            if (this.validateFormData(formeval1003)) {
                AppEval appEval = new AppEval();
                setObjValue(formeval1003, appEval);
                appEval.setEvalSts("0");
                // appEval.setEvalStartDate(User.getSysDate(this.getHttpRequest()));
                appEval.setEvalStartDate(DateUtil.getDate());
                new FeignSpringFormEncoder().addParamsToBaseDomain(appEval);
                appMap.put("appEval", appEval);
                appMap.put("cusMap", cusMap);
                appEvalFeign.insert(appMap);
                String evalAppNo = appEval.getEvalAppNo();
                String evalScenceNo = appEval.getEvalScenceNo();
                EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
                evalScenceConfig.setEvalScenceNo(evalScenceNo);
                evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
                String evalIndexTypeRel = evalScenceConfig.getEvalIndexTypeRel();
                getListData(dataMap, evalIndexTypeRel, evalScenceNo, evalAppNo);
                getDlFinScore(dataMap, evalScenceNo, appEval.getEvalAppNo());
                // 综合查询
                EvalCompreVal evalCompreVal = new EvalCompreVal();
                evalCompreVal.setEvalAppNo(evalAppNo);
                evalCompreVal.setEvalScenceNo(evalScenceNo);
                evalCompreVal = evalCompreValFeign.getById(evalCompreVal);

                dataMap.put("ECV", evalCompreVal);
                dataMap.put("entityData", appEval);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 评级申请提交
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/evalSubmitAjax")
    @ResponseBody
    public Map <String, Object> evalSubmitAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            FormData formeval1001 = formService.getFormData("eval1001");
            String useType = request.getParameter("useType");
            if(StringUtil.isNotEmpty(useType)){
                if(BizPubParm.USE_TYPE_2.equals(useType)){
                    formeval1001 = formService.getFormData("appRiskCheckDetail");
                }else if(BizPubParm.USE_TYPE_3.equals(useType)){
                    formeval1001 = formService.getFormData("creditRiskCheckDetail");
                }else {
                }
            }
            getFormValue(formeval1001, getMapByJson(ajaxData));
            if (this.validateFormData(formeval1001)) {
                AppEval appEval = new AppEval();
                EvalCompreVal evalCompreVal = new EvalCompreVal();
                setObjValue(formeval1001, appEval);
                setObjValue(formeval1001, evalCompreVal);
                appEval.setManAdjScore(evalCompreVal.getManAdjustScore());
                // appEvalFeign.updateAppEvalInfo(appEval,evalCompreVal);
                Map <String, Object> appMap = new HashMap <String, Object>();
                new FeignSpringFormEncoder().addParamsToBaseDomain(appEval);
                appMap.put("appEval", appEval);
                appMap.put("evalCompreVal", evalCompreVal);
                appEval = appEvalFeign.updateSubmit(appMap);
                if (StringUtil.isEmpty(appEval.getAppWorkFlowNo())) {
                    //这里未启用流程不需要提交申请
                    dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("评级"));
                } else {
                    Map <String, String> paramMap = new HashMap <String, String>();
                    paramMap.put("userRole", appEval.getApproveNodeName());
                    paramMap.put("opNo", appEval.getApprovePartName());
                    dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
                }
                AppEval appEval1 = new AppEval();
                appEval1.setEvalAppNo(appEval.getEvalAppNo());
                appEval1 = appEvalFeign.getById(appEval);
                String cusLevelName ="";
                if(StringUtil.isNotEmpty(useType) && (BizPubParm.USE_TYPE_2.equals(useType) || BizPubParm.USE_TYPE_3.equals(useType))){
                    cusLevelName = (new CodeUtils().getMapByKeyName("RISK_PREVENT_LEVEL")).get(appEval.getMangGrade());//获取评级字典项
                }else{
                    cusLevelName = (new CodeUtils().getMapByKeyName("EVAL_LEVEL")).get(appEval.getMangGrade());//获取评级字典项
                }
                dataMap.put("cusLevelName", cusLevelName);
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 外部评级申请提交
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/evalManSubmitAjax")
    @ResponseBody
    public Map <String, Object> evalManSubmitAjax(String ajaxData, String evalAppNo, String timeLimit) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            FormData formeval1001 = formService.getFormData("evalpersman");
            getFormValue(formeval1001, getMapByJson(ajaxData));
            String useType = "1";//标识评级
            if (this.validateFormData(formeval1001)) {
                AppEval appEval = new AppEval();
                EvalCompreVal evalCompreVal = new EvalCompreVal();
                setObjValue(formeval1001, appEval);
                new FeignSpringFormEncoder().addParamsToBaseDomain(appEval);
                //状态为外部评级
                appEval.setEvalType(BizPubParm.EVAL_TYPE_EXTERIOR);
                if ("1".equals(timeLimit) || "2".equals(timeLimit)) {
                    appEval.setTimeLimit(timeLimit);
                } else {
                    appEval.setTimeLimit(BizPubParm.EVAL_TIME_First);
                }
                appEval.setEvalAppNo(evalAppNo);
                //起始时间
                appEval.setEvalStartDate(DateUtil.getDate());
                //机评等级字段也存储为客户经理建议等级
                appEval.setGrade(appEval.getMangGrade());
                appEval.setUseType(useType);
                //添加机构名称
                Map <String, Object> map = new HashMap <>();
                map = getMapByJson(ajaxData);
                appEval.setInstitutionName(map.get("institutionName").toString());
                appEval = appEvalFeign.insertMan(appEval);
                appEval = appEvalFeign.updateManSubmit(appEval);
                if (StringUtil.isEmpty(appEval.getAppWorkFlowNo())) {
                    dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("外部评级申请提交"));
                } else {
                    Map <String, String> paramMap = new HashMap <String, String>();
                    paramMap.put("userRole", appEval.getApproveNodeName());
                    paramMap.put("opNo", appEval.getApprovePartName());
                    dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
                }
                String cusLevelName = (new CodeUtils().getMapByKeyName("EVAL_LEVEL")).get(appEval.getMangGrade());//获取评级字典项
                dataMap.put("cusLevelName", cusLevelName);
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
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
    public Map <String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            FormData formeval1003 = formService.getFormData("eval1003");
            getFormValue(formeval1003, getMapByJson(ajaxData));
            AppEval appEval = new AppEval();
            setObjValue(formeval1003, appEval);
            if ("2".equals(appEval.getCusType().substring(0, 1))) {// 个人
                this.changeFormProperty(formeval1003, "rptDate", "fieldType", 99);
                this.changeFormProperty(formeval1003, "rptDate", "mustInput", "0");
            }
            if (this.validateFormData(formeval1003)) {
                appEval.setLstRegName(User.getRegName(this.getHttpRequest()));
                appEvalFeign.updateSave(appEval);
                String evalAppNo = appEval.getEvalAppNo();
                EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
                evalScenceConfig.setEvalScenceNo(appEval.getEvalScenceNo());
                evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
                String evalIndexTypeRel = evalScenceConfig.getEvalIndexTypeRel();
                getListData(dataMap, evalIndexTypeRel, appEval.getEvalScenceNo(), evalAppNo);
                getDlFinScore(dataMap, appEval.getEvalScenceNo(), appEval.getEvalAppNo());
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

    @RequestMapping("/updateFormAjax")
    @ResponseBody
    public Map <String, Object> updateFormAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            FormData formeval1001 = formService.getFormData("eval1001");
            getFormValue(formeval1001, getMapByJson(ajaxData));
            if (this.validateFormData(formeval1001)) {
                AppEval appEval = new AppEval();
                setObjValue(formeval1001, appEval);
                EvalCompreVal evalCompreVal = new EvalCompreVal();
                setObjValue(formeval1001, evalCompreVal);
                /*
                 * String grade = appEval.getGrade(); String mangGrade =
                 * appEval.getMangGrade();
                 * if(Integer.parseInt(grade)<=Integer.parseInt(mangGrade)){
                 */
                appEval.setLstRegName(User.getRegName(this.getHttpRequest()));
                appEvalFeign.updateAppEvalInfo(appEval, evalCompreVal);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
                /*
                 * }else{ dataMap.put("flag", "error");
                 * dataMap.put("msg","客户经理建议等级不能高于机评等级"); }
                 */
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
    public Map <String, Object> getByIdAjax(String ajaxData, String evalAppNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> formData = new HashMap <String, Object>();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formeval1003 = formService.getFormData("eval1003");
        AppEval appEval = new AppEval();
        appEval.setEvalAppNo(evalAppNo);
        appEval = appEvalFeign.getById(appEval);
        getObjValue(formeval1003, appEval, formData);
        if (appEval != null) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }

    @RequestMapping("/getByIdAjaxForForm")
    @ResponseBody
    public Map <String, Object> getByIdAjaxForForm(String evalAppNo, String evalFormId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formeval1001 = formService.getFormData(evalFormId);
        AppEval appEval = new AppEval();
        appEval.setEvalAppNo(evalAppNo);
        appEval = appEvalFeign.getById(appEval);
        //String grade = getEvalLevel(appEval.getEvalScenceNo(), appEval.getTotalScore(), appEval.getGrade());// 获取机评等级
        //appEval.setGrade(grade);
        appEval.setTotalScoreTmp(appEval.getTotalScore());
        // 客户经理建议等级默认为机评等级带出
        // 综合查询
//        EvalCompreVal evalCompreVal = new EvalCompreVal();
//        evalCompreVal.setEvalAppNo(evalAppNo);
//        evalCompreVal.setEvalScenceNo(appEval.getEvalScenceNo());
//        evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
//        evalCompreVal.setFinScorePercent(MathExtend.multiply(evalCompreVal.getFinScorePercent(), 100));
//        evalCompreVal.setDlScorePercent(MathExtend.multiply(evalCompreVal.getDlScorePercent(), 100));
//        evalCompreVal.setDxScorePercent(MathExtend.multiply(evalCompreVal.getDxScorePercent(), 100));
        EvalScenceRestrictVal evalScenceRestrictVal = new EvalScenceRestrictVal();
        evalScenceRestrictVal.setEvalAppNo(evalAppNo);
        evalScenceRestrictVal = evalScenceRestrictValFeign.getById(evalScenceRestrictVal);
//        if (evalScenceRestrictVal != null) {
//            evalCompreVal.setRestrictLevel(evalScenceRestrictVal.getEvalRestrictLevel());
//        } else {
//            evalCompreVal.setRestrictLevel("0");
//        }
//        getObjValue(formeval1001, evalCompreVal);
//        appEval.setRestrictLevel(evalCompreVal.getRestrictLevel());
        MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(appEval.getCusNo());
        if (mfCusCustomer != null) {
            appEval.setTotalCredited(mfCusCustomer.getPreCreditSum());
        }
        getObjValue(formeval1001, appEval);
        String useType = appEval.getUseType();
        if(BizPubParm.USE_TYPE_1.equals(useType)){
            if (!BizPubParm.CUS_TYPE_CORP.equals(appEval.getCusType())) {
                // this.changeFormProperty(formeval1001, "rptDate", "fieldType",
                // 99);
                this.changeFormProperty(formeval1001, "rptDate", "mustInput", "0");
            }
        }

        EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
        if(StringUtil.isNotEmpty(appEval.getEvalClass())){
            if(appEval.getEvalClass().equals(BizPubParm.EVAL_CLASS_1)){
                evalScoreGradeConfig.setEvalClass(BizPubParm.EVAL_CLASS_1);
                List<OptionsList> optionsLists = evalScoreGradeConfigFeign.getGradeLevelList(evalScoreGradeConfig);
                this.changeFormProperty(formeval1001, "grade", "optionArray", optionsLists);

                if(StringUtil.isNotEmpty(appEval.getEvalScenceSubNo())){
                    EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
                    evalScenceConfig.setEvalScenceNo(appEval.getEvalScenceSubNo());
                    evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
                    if(evalScenceConfig != null && StringUtil.isNotEmpty(evalScenceConfig.getEvalClass())){
                        evalScoreGradeConfig.setEvalClass(evalScenceConfig.getEvalClass());
                        optionsLists = evalScoreGradeConfigFeign.getGradeLevelList(evalScoreGradeConfig);
                        this.changeFormProperty(formeval1001, "debtGrade", "optionArray", optionsLists);
                    }
                }
            }
        }

        getFormDataValData(dataMap, formeval1001, "bootstarpTag");
        dataMap.putAll(appEvalFeign.getEvalListData(evalAppNo, appEval.getEvalScenceNo()));
        dataMap.put("flag", "success");
        return dataMap;
    }

    /**
     * 方法描述： 获得选择财报详情
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2016-12-22 下午6:54:54
     */
    @RequestMapping("/getByIdAjaxForChoseFinForm")
    @ResponseBody
    public Map <String, Object> getByIdAjaxForChoseFinForm(String evalFormId, String evalAppNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formeval1003 = formService.getFormData(evalFormId);
        AppEval appEval = new AppEval();
        appEval.setEvalAppNo(evalAppNo);
        appEval = appEvalFeign.getById(appEval);
        String grade = getEvalLevel(appEval.getEvalScenceNo(), appEval.getTotalScore(), appEval.getGrade());// 获取机评等级
        appEval.setGrade(grade);
        String useType = appEval.getUseType();
        getObjValue(formeval1003, appEval);
        if(BizPubParm.USE_TYPE_1.equals(useType)){
            this.changeFormProperty(formeval1003, "evalEndDate", "mustInput", "0");
            this.changeFormProperty(formeval1003, "evalEndDate", "onclick", "");
            this.changeFormProperty(formeval1003, "rptDate", "mustInput", "0");
            this.changeFormProperty(formeval1003, "rptDate", "onclick", "");
            if (!BizPubParm.CUS_TYPE_CORP.equals(appEval.getCusType())) {
                // this.changeFormProperty(formeval1003, "rptDate", "fieldType",
                // 99);
                this.changeFormProperty(formeval1003, "rptDate", "mustInput", "0");
            }
        }
        getFormDataValData(dataMap, formeval1003, "bootstarpTag");
        dataMap.put("flag", "success");
        return dataMap;
    }

    @RequestMapping("/getEvalLevel")
    public String getEvalLevel(String scenceNo, double totalScore, String grade) {
        List <EvalScoreGradeConfig> evalScoreGradeConfigList = new ArrayList <EvalScoreGradeConfig>();
        EvalScoreGradeConfig gradeConfig = new EvalScoreGradeConfig();
        evalScoreGradeConfigList = evalScoreGradeConfigFeign.getAll(gradeConfig);
        String evalLevel = "1";
        // 获取评级级别
        evalLevel = EvalUtil.JudgeComputingEvalLevel(evalScoreGradeConfigList, totalScore, evalLevel);
        if (StringUtil.isNotEmpty(grade)) {
            if (Integer.parseInt(grade) > Integer.parseInt(evalLevel)) {
                evalLevel = grade;
            }
        }
        return evalLevel;
    }

    private void getFormDataValData(Map <String, Object> dataMap, FormData formObj, String formType) throws Exception {
        JsonFormUtil jfu = new JsonFormUtil();
        String formHtml = jfu.getJsonStr(formObj, formType, "");
        dataMap.put("formHtml", formHtml);
    }

    /**
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public Map <String, Object> deleteAjax(String ajaxData, String evalAppNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        AppEval appEval = new AppEval();
        appEval.setEvalAppNo(evalAppNo);
        try {
            appEvalFeign.delete(appEval);
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
     * Ajax异步更新状态
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateStsAjax")
    @ResponseBody
    public Map <String, Object> updateStsAjax(String ajaxData, String evalAppNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        AppEval appEval = new AppEval();
        appEval.setEvalAppNo(evalAppNo);
        appEval.setEvalSts("1");
        try {
            appEvalFeign.updateSts(appEval);
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
    @RequestMapping("/input")
    public String input(Model model) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formeval1003 = formService.getFormData("eval1003");
        model.addAttribute("formeval1003", formeval1003);
        model.addAttribute("query", "");
        return "/component/eval/input";
    }

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(Model model, String evalAppNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formeval1001 = formService.getFormData("eval1001");
        getFormValue(formeval1001);
        AppEval appEval = new AppEval();
        appEval.setEvalAppNo(evalAppNo);
        appEval = appEvalFeign.getById(appEval);
        getObjValue(formeval1001, appEval);
        model.addAttribute("formeval1001", formeval1001);
        model.addAttribute("query", "");
        return "/component/eval/query";
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(String evalAppNo) throws Exception {
        ActionContext.initialize(request, response);
        AppEval appEval = new AppEval();
        appEval.setEvalAppNo(evalAppNo);
        appEvalFeign.delete(appEval);
        return getListPage();
    }

    public void getDlFinScore(Map <String, Object> dataMap, String evalScenceNo, String evalAppNo) {
        if (dataMap.get("finData") == null) {
            EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
            evalScenceFinVal.setEvalAppNo(evalAppNo);
            evalScenceFinVal.setEvalScenceNo(evalScenceNo);
            evalScenceFinVal = evalScenceFinValFeign.getById(evalScenceFinVal);
            dataMap.put("finData", evalScenceFinVal);
        }
        if (dataMap.get("dlData") == null) {
            EvalScenceDlVal evalScenceDlVal = new EvalScenceDlVal();
            evalScenceDlVal.setEvalAppNo(evalAppNo);
            evalScenceDlVal.setEvalScenceNo(evalScenceNo);
            evalScenceDlVal = evalScenceDlValFeign.getById(evalScenceDlVal);
            dataMap.put("dlData", evalScenceDlVal);
        }
        EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
        evalScenceDxVal.setEvalAppNo(evalAppNo);
        evalScenceDxVal.setEvalScenceNo(evalScenceNo);
        evalScenceDxVal = evalScenceDxValFeign.getById(evalScenceDxVal);
        dataMap.put("dxData", evalScenceDxVal);
        EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
        evalScenceAdjVal.setEvalAppNo(evalAppNo);
        evalScenceAdjVal.setEvalScenceNo(evalScenceNo);
        evalScenceAdjVal = evalScenceAdjValFeign.getById(evalScenceAdjVal);
        dataMap.put("adjData", evalScenceAdjVal);
    }

    @SuppressWarnings("unchecked")
    public void getListData(Map <String, Object> dataMap, String evalIndexTypeRel, String evalScenceNo, String evalAppNo)
            throws Exception {
        Map <String, Object> listData = new HashMap <String, Object>();
        for (String type : evalIndexTypeRel.split("|")) {
            if ("1".equals(type)) {// 财务评分指标
                EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
                evalScenceFinVal.setEvalAppNo(evalAppNo);
                evalScenceFinVal.setEvalScenceNo(evalScenceNo);
                evalScenceFinVal = evalScenceFinValFeign.getById(evalScenceFinVal);
                dataMap.put("finData", evalScenceFinVal);
                JSONObject jsonObj = JSONObject.fromObject(evalScenceFinVal.getScoreList());
                EvalScenceFinRel scenceFinRel = new EvalScenceFinRel();
                scenceFinRel.setScenceNo(evalScenceNo);
                // scenceFinRel.setGradeCardId(gradeCardId);
                List <EvalScenceFinRel> evalScenceFinRelList = evalScenceFinRelFeign.getForScenceNo(scenceFinRel);
                for (EvalScenceFinRel evalScenceFinRel : evalScenceFinRelList) {
                    String finCode = evalScenceFinRel.getFinCode();
                    if (jsonObj.get(finCode) != null) {
                        JSONArray array = JSONArray.fromObject(jsonObj.get(finCode));
                        evalScenceFinRel.setFinCode(array.getString(0));
                        evalScenceFinRel.setStdScore(Double.parseDouble(array.getString(1)));
                    } else {
                        evalScenceFinRel.setFinCode("0");
                    }
                }
                listData.put("FIN", evalScenceFinRelList);
                List <EvalScenceDlRel> evalScenceDlRelList = null;
                /*
                 * if(listData.get("DL")!=null){ evalScenceDlRelList =
                 * (ArrayList<EvalScenceDlRel>)listData.get("DL"); }
                 */
                evalScenceDlRelList = this.setDlForFin(evalScenceFinRelList, evalScenceDlRelList);
                listData.put("FIN", evalScenceDlRelList);
            } else if ("2".equals(type)) {// 定量评分指标
                EvalScenceDlVal evalScenceDlVal = new EvalScenceDlVal();
                evalScenceDlVal.setEvalAppNo(evalAppNo);
                evalScenceDlVal.setEvalScenceNo(evalScenceNo);
                evalScenceDlVal = evalScenceDlValFeign.getById(evalScenceDlVal);
                dataMap.put("dlData", evalScenceDlVal);
                JSONObject jsonObj = JSONObject.fromObject(evalScenceDlVal.getScoreList());
                List <EvalScenceDlRel> evalScenceDlRelList = evalScenceDlRelFeign.getForScenceNo(evalScenceNo);
                for (EvalScenceDlRel evalScenceDlRel : evalScenceDlRelList) {
                    String javaItem = evalScenceDlRel.getJavaItem();
                    if (jsonObj.get(javaItem) != null) {
                        JSONArray array = JSONArray.fromObject(jsonObj.get(javaItem));
                        evalScenceDlRel.setJavaItem(array.getString(0));
                        evalScenceDlRel.setStdCore(Double.parseDouble(array.getString(1)));
                    } else {
                        evalScenceDlRel.setJavaItem("0");
                    }
                }
                if (listData.get("DL") != null) {
                    List <EvalScenceDlRel> evalScenceDlRelList2 = (ArrayList <EvalScenceDlRel>) listData.get("DL");
                    evalScenceDlRelList.addAll(evalScenceDlRelList2);
                }
                listData.put("DL", evalScenceDlRelList);
            } else if ("3".equals(type)) {// 定性评分指标
                List <EvalScenceDxRel> evalScenceDxRelList = evalScenceDxRelFeign.getForScenceNo(evalScenceNo);
                listData.put("DX", evalScenceDxRelList);
            } else if ("4".equals(type)) {// 调整评分指标
                List <EvalScenceAdjRel> evalScenceAdjRelList = evalScenceAdjRelFeign.getForScenceNo(evalScenceNo);
                listData.put("ADJ", evalScenceAdjRelList);
            } else if ("5".equals(type)) {// 约束等级指标
                List <EvalScenceRestrictRel> evalScenceRestrictRelList = evalScenceRestrictRelFeign
                        .getForScenceNo(evalScenceNo);
                listData.put("RES", evalScenceRestrictRelList);
            }else {
            }
        }
        dataMap.put("listData", listData);
    }

    public List <EvalScenceDlRel> setDlForFin(List <EvalScenceFinRel> evalScenceFinRelList,
                                              List <EvalScenceDlRel> evalScenceDlRelList) {
        if (evalScenceDlRelList == null) {
            evalScenceDlRelList = new ArrayList <EvalScenceDlRel>();
        }
        for (EvalScenceFinRel fin : evalScenceFinRelList) {
            EvalScenceDlRel dl = new EvalScenceDlRel();
            dl.setIndexNo(fin.getIndexNo());
            dl.setIndexName(fin.getIndexName());
            dl.setJavaItem(fin.getFinCode());
            dl.setLevel(fin.getLevel());
            dl.setUpIndexNo(fin.getUpIndexNo());
            dl.setStdCore(fin.getStdScore());
            dl.setIndexNo(fin.getIndexNo());
            evalScenceDlRelList.add(dl);
        }
        return evalScenceDlRelList;
    }

    /**
     * 方法描述： 保存评分卡
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-5-26 下午6:50:05
     */
    @RequestMapping("/updateEvalAjax")
    @ResponseBody
    public Map <String, Object> updateEvalAjax(String ajaxData, String evalAppNo, String evalScenceNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            dataMap.put("evalAppNo", evalAppNo);
            dataMap.put("evalScenceNo", evalScenceNo);
            dataMap.put("ajaxData", ajaxData);
            dataMap.put("opNo", User.getRegNo(request));
            dataMap.put("opName", User.getRegName(request));
            ajaxData = ajaxData.replaceAll("&amp;", "&");
            dataMap.put("ajaxData", ajaxData);
            AppEval appEval = appEvalFeign.updateDxAdj(dataMap);
            dataMap.putAll(appEvalFeign.getEvalListData(evalAppNo, evalScenceNo));
            dataMap.put("flag", "success");
            dataMap.put("appEval", appEval);
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
     * 方法描述： 处理评级申请信息
     *
     * @param ajaxData
     * @param cusBaseType
     * @return
     * @throws Exception Map<String,Object>
     * @author 沈浩兵
     * @date 2018年6月7日 下午3:36:44
     */
    @RequestMapping("/insertEvalApplyAjax")
    @ResponseBody
    public Map <String, Object> insertEvalApplyAjax(String ajaxData, String cusBaseType, String timeLimit,String gradeType,String evalClass,String accountId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        Map <String, Object> appMap = new HashMap <String, Object>();
        Map <String, Object> ajaxMap = new HashMap <String, Object>();
        try {
            FormData formappeval0001 = formService.getFormData("appeval0001");

            //如果是债项评级取同一个申请表单
            if(BizPubParm.GRADE_TYPE_4.equals(gradeType) && BizPubParm.EVAL_CLASS_2.equals(evalClass)){
                formappeval0001 = formService.getFormData("debteval0001");
            }else{
                if (BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)) {
                    formappeval0001 = formService.getFormData("appevalpers0001");
                }else{
                    formappeval0001 = formService.getFormData("appeval0001");
                }
            }

            String useType = request.getParameter("useType");
            if(StringUtil.isNotEmpty(useType)){
                if(BizPubParm.USE_TYPE_2.equals(useType)){
                    formappeval0001 = formService.getFormData("appRiskCheckAdd");
                }else if(BizPubParm.USE_TYPE_3.equals(useType)){
                    formappeval0001 = formService.getFormData("creditRiskCheckAdd");
                }else if(BizPubParm.USE_TYPE_4.equals(useType)){
                    formappeval0001 = formService.getFormData("creditRiskCheckAdd");
                }else {
                }
            }
            ajaxMap = getMapByJson(ajaxData);
            appMap.put("accountId", accountId);
            getFormValue(formappeval0001,ajaxMap);
            AppEval appEval = new AppEval();
            setObjValue(formappeval0001, appEval);
            boolean validataFalag = true;
            String rptDate = appEval.getRptDate();
            String evalEndDate = appEval.getEvalEndDate();
            String sysData = User.getSysDate(this.getHttpRequest());
            if(StringUtil.isNotEmpty(useType) && BizPubParm.USE_TYPE_1.equals(useType)){
                // 获取财务指标参数
                Map <String, Double> pfsMap = new HashMap <String, Double>();
                if(BizPubParm.GRADE_TYPE_4.equals(gradeType) && (BizPubParm.EVAL_CLASS_2.equals(evalClass))){
                }else if (!BizPubParm.CUS_TYPE_PERS.equals(cusBaseType) ) {
                    // 调整参数传递
                    CusFinCapData cusFinCapData = new CusFinCapData();
                    cusFinCapData.setCusNo(appEval.getCusNo());
                    cusFinCapData.setFinRptDate(appEval.getRptDate());
                    cusFinCapData.setFinRptType(appEval.getRptType());
                    pfsMap = pfsInterfaceFeign.getFinIndicators(cusFinCapData);
                    if (pfsMap == null) {
                        validataFalag = false;
                        dataMap.put("msg", MessageEnum.NO_FILE.getMessage("报表"));
                    }
                    if (validataFalag && Integer.parseInt(sysData) <= Integer.parseInt(rptDate)) {
                        validataFalag = false;
                        Map <String, String> paramMap = new HashMap <String, String>();
                        paramMap.put("timeOne", "财报日期");
                        paramMap.put("timeTwo", "系统日期");
                        dataMap.put("msg", MessageEnum.NOT_FORM_TIME.getMessage(paramMap));
                    }

                }
                appMap.put("pfsMap", pfsMap);
                if (validataFalag && StringUtil.isNotEmpty(evalEndDate)&&Integer.parseInt(sysData) > Integer.parseInt(evalEndDate)) {
                    validataFalag = false;
                    Map <String, String> paramMap = new HashMap <String, String>();
                    paramMap.put("timeOne", "到期日期");
                    paramMap.put("timeTwo", "系统日期");
                    dataMap.put("msg", MessageEnum.NOT_SMALL_TIME.getMessage(paramMap));
                }

                if( BizPubParm.GRADE_TYPE_4.equals(gradeType) && BizPubParm.EVAL_CLASS_1.equals(evalClass)){
                    if(StringUtil.isNotEmpty(appEval.getRptDate())){
                        if(appEval.getRptDate().length()<=4){
                            validataFalag = false;
                            dataMap.put("msg", "请选择月报！");
                        }
                        int  termValidity = appPropertyFeign.getTermValidity(appEval.getCusNo(),appEval.getRptDate(), appEval.getRptDate().substring(4,6),(String)appMap.get("accountId"));
                        if(termValidity == 0){
                            validataFalag = false;
                            dataMap.put("msg", "无连续的财务报表！");
                        }
                        if(termValidity==1&&"12".equals(appEval.getRptDate().substring(4,6))){
                            validataFalag = false;
                            dataMap.put("msg", "无连续的财务报表！");
                        }

                    }
                }


            }


            // 业务参数
            if (validataFalag) {
                // appEval.setEvalStartDate(User.getSysDate(this.getHttpRequest()));
                appEval.setEvalStartDate(DateUtil.getDate());
                appEval.setEvalType(BizPubParm.EVAL_TYPE_INNER);//内部评级
                if ("1".equals(timeLimit) || "2".equals(timeLimit)) {
                    appEval.setTimeLimit(timeLimit);
                } else {
                    appEval.setTimeLimit(BizPubParm.EVAL_TIME_First);
                }
                new FeignSpringFormEncoder().addParamsToBaseDomain(appEval);
                appEval.setUseType(useType);
                appMap.put("appEval", appEval);
                appEval = appEvalFeign.insertEvalApply(appMap);
                String evalAppNo = appEval.getEvalAppNo();
                String evalScenceNo = appEval.getEvalScenceNo();
                EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
                evalScenceConfig.setEvalScenceNo(evalScenceNo);
                evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
                String evalIndexTypeRel = evalScenceConfig.getEvalIndexTypeRel();
                dataMap.putAll(appEvalFeign.getEvalListData(evalAppNo, evalScenceNo));
                // 综合查询
                EvalCompreVal evalCompreVal = new EvalCompreVal();
                evalCompreVal.setEvalAppNo(evalAppNo);
                evalCompreVal.setEvalScenceNo(evalScenceNo);
                evalCompreVal = evalCompreValFeign.getById(evalCompreVal);

                dataMap.put("ECV", evalCompreVal);
                dataMap.put("entityData", appEval);
                dataMap.put("evalIndexTypeRel", evalIndexTypeRel);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            return  dataMap;
        }
        return dataMap;
    }

    /**
     * 方法描述： 获得评级评分卡及指标信息
     *
     * @param ajaxData
     * @param evalAppNo
     * @param evalScenceNo
     * @return
     * @throws Exception Map<String,Object>
     * @author 沈浩兵
     * @date 2018年6月7日 下午8:40:30
     */
    @RequestMapping("/getEvalListDataAjax")
    @ResponseBody
    public Map <String, Object> getEvalListDataAjax(String ajaxData, String evalAppNo, String evalScenceNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            dataMap.putAll(appEvalFeign.getEvalListData(evalAppNo, evalScenceNo));
            dataMap.put("flag", "success");
        } catch (ServiceException e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述：  获得客户当前评级信息
     *
     * @param cusNo
     * @return
     * @throws Exception Map<String,Object>
     * @author 沈浩兵
     * @date 2018年6月13日 下午4:30:23
     */
    @RequestMapping("/getCurrAppEvalDataAjax")
    @ResponseBody
    public Map <String, Object> getCurrAppEvalDataAjax(String param) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        Map <String, Object> paramMap = new Gson().fromJson(param, Map.class);
        try {
            dataMap = appEvalFeign.getCurrAppEvalData(paramMap);
        } catch (ServiceException e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getFinancialRatio")
    public String getFinancialRatio(Model model, String evalAppNo) throws Exception {
        ActionContext.initialize(request, response);
       try{
           Map<String,Object> dataMap = evalDataFeign.getFinancialRatio(evalAppNo);
           model.addAttribute("htmlStr", dataMap.get("htmlStr"));
           model.addAttribute("query", "");
       }catch(Exception e){
           e.printStackTrace();
       }
       return "/component/eval/AppEval_Financial";
    }
}
