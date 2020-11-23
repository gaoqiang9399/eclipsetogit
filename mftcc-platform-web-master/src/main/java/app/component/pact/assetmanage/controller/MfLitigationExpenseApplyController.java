package app.component.pact.assetmanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.common.EntityUtil;
import app.component.lawsuit.entity.MfLawsuit;
import app.component.lawsuit.feign.MfLawsuitFeign;
import cn.mftcc.util.MathExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.pact.assetmanage.entity.MfAssetManage;
import app.component.pact.assetmanage.entity.MfLitigationExpenseApply;
import app.component.pact.assetmanage.feign.MfAssetManageFeign;
import app.component.pact.assetmanage.feign.MfLitigationExpenseApplyFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.TaskFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

/**
 * @ClassName MfAssetManageController
 * @Description
 * @Author Liu Haobin
 * @Date 2018年6月15日 下午3:10:55
 */
@Controller
@RequestMapping("/mfLitigationExpenseApply")
public class MfLitigationExpenseApplyController extends BaseFormBean {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfAssetManageFeign mfAssetManageFeign;
    @Autowired
    private TaskFeign taskFeign;

    @Autowired
    private MfLitigationExpenseApplyFeign mfLitigationExpenseApplyFeign;
    @Autowired
    private MfLawsuitFeign mfLawsuitFeign;

    /**
     * 总产管理列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAssetManageListPage")
    public String getAssetManageListPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        JSONArray classMethodJsonArray = new CodeUtils().getJSONArrayByKeyName("ASSET_APPLY_STATUS");
        model.addAttribute("classMethodJsonArray", classMethodJsonArray);
        model.addAttribute("query", "");
        return "component/pact/assetmanage/MfLitigationExpenseApply_List";
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String applyFlag) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
        mfLitigationExpenseApply.setRegDate(DateUtil.getDate());
        mfLitigationExpenseApply.setLitigationId(WaterIdUtil.getWaterId("LE"));
        mfLitigationExpenseApply.setApplicant(User.getRegName(request));
        mfLitigationExpenseApply.setShowFlag("1");
        FormData formlitigationexpenseapplybase = formService.getFormData("litigationexpenseapplybase");
        getObjValue(formlitigationexpenseapplybase, mfLitigationExpenseApply);
        String isFlag = mfLitigationExpenseApplyFeign.isApproval();//是否开启审批标志  用于展示不同的提交提示信息
        model.addAttribute("formlitigationexpenseapplybase", formlitigationexpenseapplybase);
        model.addAttribute("mfLitigationExpenseApply", mfLitigationExpenseApply);
        model.addAttribute("query", "");
        model.addAttribute("isFlag", isFlag);
        model.addAttribute("applyFlag", applyFlag);
        return "/component/pact/assetmanage/MfLitigationExpenseApply_Insert";
    }

    /**
     * 获取已通过诉讼申请列表
     */

    @RequestMapping(value = "/getAssetManageList")
    @ResponseBody
    public Map<String, Object> getAssetManageList(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                  String ajaxData, String applyFlag) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfAssetManage mfAssetManage = new MfAssetManage();
        mfAssetManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
        mfAssetManage.setCriteriaList(mfAssetManage, ajaxData);// 我的筛选
        Ipage ipage = this.getIpage();
        ipage.setPageSize(pageSize);
        ipage.setPageNo(pageNo);// 异步传页面翻页参数
        ipage.setParams(this.setIpageParams("mfAssetManage", mfAssetManage));
        ipage = mfLitigationExpenseApplyFeign.getAssetManageList(ipage, applyFlag);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
        ipage.setResult(tableHtml);
        dataMap.put("ipage", ipage);
        dataMap.put("flag", "success");
        return dataMap;
    }

    /**
     * 获取已通过的诉讼申请相关信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getInfo")
    @ResponseBody
    public Map<String, Object> getInfo(Model model, String assetId, String applyFlag) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        try {
            MfAssetManage mfAssetManage = new MfAssetManage();
            MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
            mfAssetManage.setAssetId(assetId);
            mfAssetManage = mfAssetManageFeign.getById(mfAssetManage);
            if ("0".equals(applyFlag)) {
                mfLitigationExpenseApply.setRegDate(DateUtil.getDate());
                mfLitigationExpenseApply.setApplicant(User.getRegName(request));
                mfLitigationExpenseApply.setCusName(mfAssetManage.getCusName());
                mfLitigationExpenseApply.setLitigationAmount(mfAssetManage.getOverduePrincipal() + mfAssetManage.getOverdueInterest());
                mfLitigationExpenseApply.setCusNo(mfAssetManage.getCusNo());
                mfLitigationExpenseApply.setAssetId(mfAssetManage.getAssetId());
                FormData formlitigationexpenseapplybase = formService.getFormData("litigationexpenseapplybase");

                getObjValue(formlitigationexpenseapplybase, mfLitigationExpenseApply, formData);
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formlitigationexpenseapplybase, "bootstarpTag", "");
                dataMap.put("bean", mfLitigationExpenseApply);
                dataMap.put("htmlStr", htmlStr);
            } else {
                FormData formassetmanagebase = formService.getFormData("assetmanagebase");

                getObjValue(formassetmanagebase, mfAssetManage, formData);
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formassetmanagebase, "bootstarpTag", "");
                dataMap.put("bean", mfAssetManage);
                dataMap.put("htmlStr", htmlStr);
            }
            dataMap.put("formData", formData);
            dataMap.put("flag", "success");

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
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
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
        try {
            mfLitigationExpenseApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfLitigationExpenseApply.setCriteriaList(mfLitigationExpenseApply, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfLitigationExpenseApply", mfLitigationExpenseApply));
            ipage = mfLitigationExpenseApplyFeign.findByPage(ipage);
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
     * 诉讼费用申请新增和更新
     *
     * @param ajaxData
     * @return
     * @throws Exception 20180620
     */
    @RequestMapping(value = "/insertApplyAjax")
    @ResponseBody
    public Map<String, Object> insertApplyAjax(String ajaxData, String flag) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        //FormData formcreditapply0001 = formService.getFormData("assetmanagebase");
        MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
        try {
            dataMap = getMapByJson(ajaxData);
            if ("1".equals(flag)) {
                FormData formlitigationexpenseapply0001 = formService.getFormData("litigationexpenseapply0001");
                getFormValue(formlitigationexpenseapply0001, getMapByJson(ajaxData));
                if (this.validateFormData(formlitigationexpenseapply0001)) {
                    setObjValue(formlitigationexpenseapply0001, mfLitigationExpenseApply);
                    mfLitigationExpenseApplyFeign.update(mfLitigationExpenseApply);
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
                } else {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", this.getFormulavaliErrorMsg());
                }
            } else {
                FormData formlitigationexpenseapplybase = formService.getFormData("litigationexpenseapplybase");
                getFormValue(formlitigationexpenseapplybase, getMapByJson(ajaxData));
                if (this.validateFormData(formlitigationexpenseapplybase)) {
                    setObjValue(formlitigationexpenseapplybase, mfLitigationExpenseApply);
                    mfLitigationExpenseApplyFeign.mfLitigationExpenseApply(mfLitigationExpenseApply);
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());

                } else {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", this.getFormulavaliErrorMsg());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }


    /**
     * 申请确认
     *
     * @param model
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/getPointView")
    public String getPointView(Model model, String litigationId, String wfkAppId, String taskId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        //String taskId = request.getParameter("taskId");
        MfAssetManage mfAssetManage = new MfAssetManage();
        MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
        mfLitigationExpenseApply.setLitigationId(litigationId);
        mfLitigationExpenseApply = mfLitigationExpenseApplyFeign.getById(mfLitigationExpenseApply);
        mfLitigationExpenseApply.setTaskId(taskId);
        TaskImpl taskAppro = mfAssetManageFeign.getTask(mfLitigationExpenseApply.getWkfAppId());
        String activityType = taskAppro.getActivityType();
        FormData formlitigationexpenseapplyapproval = formService.getFormData("litigationexpenseapplyapproval");
        getObjValue(formlitigationexpenseapplyapproval, mfLitigationExpenseApply);
        //处理审批意见类型
        List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), null);
        List<OptionsList> opinionTypeList1 = new ArrayList<OptionsList>();
        for (int i = 0; i < opinionTypeList.size(); i++) {
            if (("同意").equals(opinionTypeList.get(i).getOptionLabel()) || ("否决").equals(opinionTypeList.get(i).getOptionLabel())) {
                opinionTypeList1.add(opinionTypeList.get(i));
            }
        }
        this.changeFormProperty(formlitigationexpenseapplyapproval, "opinionType", "optionArray", opinionTypeList1);
        model.addAttribute("formlitigationexpenseapplyapproval", formlitigationexpenseapplyapproval);
        model.addAttribute("mfLitigationExpenseApply", mfLitigationExpenseApply);
        model.addAttribute("query", "");
        return "/component/pact/assetmanage/LitigationExpenseApply_PointView";
    }

    /**
     * 申请保存和开启审批流程
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/applyUpdate")
    @ResponseBody
    public Map<String, Object> applyUpdate(String litigationId, String ajaxData, String applyStatus) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
        FormService formService = new FormService();
        try {
            dataMap = getMapByJson(ajaxData);
            if ("0".equals(applyStatus)) {
                FormData formlitigationexpenseapply0001 = formService.getFormData("litigationexpenseapply0001");
                getFormValue(formlitigationexpenseapply0001, getMapByJson(ajaxData));
                if (this.validateFormData(formlitigationexpenseapply0001)) {
                    setObjValue(formlitigationexpenseapply0001, mfLitigationExpenseApply);
                    mfLitigationExpenseApplyFeign.update(mfLitigationExpenseApply);
                    mfLitigationExpenseApply = mfLitigationExpenseApplyFeign.getById(mfLitigationExpenseApply);
                    mfLitigationExpenseApply = mfLitigationExpenseApplyFeign.submitProcess(mfLitigationExpenseApply, null);
                    dataMap.put("flag", "success");
                    //dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                    //dataMap.put("flag", "success");
                    dataMap.put("mfLitigationExpenseApply", mfLitigationExpenseApply);
                    dataMap.put("msg", mfLitigationExpenseApply.getMsg());
                } else {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", this.getFormulavaliErrorMsg());
                }
            } else {
                FormData formlitigationexpenseapplybase = formService.getFormData("litigationexpenseapplybase");
                getFormValue(formlitigationexpenseapplybase, getMapByJson(ajaxData));
                if (this.validateFormData(formlitigationexpenseapplybase)) {
                    setObjValue(formlitigationexpenseapplybase, mfLitigationExpenseApply);
                    mfLitigationExpenseApplyFeign.mfLitigationExpenseApply(mfLitigationExpenseApply);
                    mfLitigationExpenseApply = mfLitigationExpenseApplyFeign.getById(mfLitigationExpenseApply);
                    mfLitigationExpenseApply = mfLitigationExpenseApplyFeign.submitProcess(mfLitigationExpenseApply, null);
                    dataMap.put("flag", "success");
                    //dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                    //dataMap.put("flag", "success");
                    dataMap.put("mfLitigationExpenseApply", mfLitigationExpenseApply);
                    dataMap.put("msg", mfLitigationExpenseApply.getMsg());
                } else {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", this.getFormulavaliErrorMsg());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
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
        String formId = (String) dataMap.get("formId");
        FormData formcostregdetail = formService.getFormData(formId);
        getFormValue(formcostregdetail, getMapByJson(ajaxData));
        MfLitigationExpenseApply mfLitigationExpenseApplyJsp = new MfLitigationExpenseApply();
        setObjValue(formcostregdetail, mfLitigationExpenseApplyJsp);
        MfLitigationExpenseApply mlea = mfLitigationExpenseApplyFeign.getById(mfLitigationExpenseApplyJsp);
        if (mlea != null) {
            try {
                mlea = (MfLitigationExpenseApply) EntityUtil.reflectionSetVal(mlea, mfLitigationExpenseApplyJsp, getMapByJson(ajaxData));
                mfLitigationExpenseApplyFeign.update(mlea);
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
     * 审批流程处理
     *
     * @param
     * @return dataMap
     * @throws Exception
     */
    @RequestMapping(value = "/updateForSubmit")
    @ResponseBody
    public Map<String, Object> updateForSubmit(String ajaxData, String appNo, String litigationId, String nextUser) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
        nextUser = request.getParameter("nextUser");
        try {
            mfLitigationExpenseApply.setLitigationId(litigationId);
            mfLitigationExpenseApply = mfLitigationExpenseApplyFeign.getById(mfLitigationExpenseApply);
            dataMap = getMapByJson(ajaxData);
            FormData formlitigationexpenseapplyapproval = new FormService().getFormData("litigationexpenseapplyapproval");
            getFormValue(formlitigationexpenseapplyapproval, getMapByJson(ajaxData));
            String taskId = (String) dataMap.get("taskId");
            setObjValue(formlitigationexpenseapplyapproval, mfLitigationExpenseApply);
            dataMap.put("mfLitigationExpenseApply", mfLitigationExpenseApply);
            Result res = mfLitigationExpenseApplyFeign.updateForSubmit(taskId, mfLitigationExpenseApply.getWkfAppId(), mfLitigationExpenseApply.getOpinionType(), mfLitigationExpenseApply.getApprovalOpinion(), taskFeign.getTransitionsStr(taskId), User.getRegNo(this.getHttpRequest()), "", dataMap);
            dataMap.put("flag", "success");
            dataMap.put("msg", res.getMsg());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 点击申请状态查看详情和审批历史，待提交时点击编辑
     *
     * @param litigationId
     * @param applyStatus
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showInfo")
    public String showInfo(Model model, String litigationId, String applyStatus) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String query = "";
        FormService formService = new FormService();
        MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
        mfLitigationExpenseApply.setLitigationId(litigationId);
        mfLitigationExpenseApply = mfLitigationExpenseApplyFeign.getById(mfLitigationExpenseApply);
        if ("0".equals(applyStatus)) {
            FormData formlitigationexpenseapply0001 = formService.getFormData("litigationexpenseapply0001");
            getObjValue(formlitigationexpenseapply0001, mfLitigationExpenseApply);
            model.addAttribute("mfLitigationExpenseApply", mfLitigationExpenseApply);
            model.addAttribute("formlitigationexpenseapply0001", formlitigationexpenseapply0001);
            model.addAttribute("query", query);
            return "/component/pact/assetmanage/MfLitigationExpenseApply_Update";
        } else {
            FormData formlitigationexpenseapplyinfo = formService.getFormData("litigationexpenseapplyinfo");
            getObjValue(formlitigationexpenseapplyinfo, mfLitigationExpenseApply);
            model.addAttribute("mfLitigationExpenseApply", mfLitigationExpenseApply);
            model.addAttribute("wkfAppId", mfLitigationExpenseApply.getWkfAppId());
            model.addAttribute("formlitigationexpenseapplyinfo", formlitigationexpenseapplyinfo);
            model.addAttribute("query", query);
            return "/component/pact/assetmanage/MfLitigationExpenseApply_Detail";
        }

    }

    /**
     * 点击打开费用汇总表单
     *
     * @param
     * @param
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFeeSumDetail")
    public String getFeeSumDetail(Model model, String assetId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String query = "";
        MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
        mfLitigationExpenseApply.setAssetId(assetId);
        List<MfLitigationExpenseApply> applyList = mfLitigationExpenseApplyFeign.findByAssetId(mfLitigationExpenseApply);
        if (applyList.size() > 0) {
            String filingFee = "0.00";
            String saveCost = "0.00";
            String announcementFee ="0.00";
            String appraisalCost ="0.00";
            String postFee ="0.00";
            String executionFee ="0.00";
            String securityPremium ="0.00";
            String barFee ="0.00";
            String assessmentFee ="0.00";
            String otherCharges ="0.00";
            String executionPrcp ="0.00";//执行本金
            String executionIntst ="0.00";//执行利息
            for (MfLitigationExpenseApply list : applyList) {
                filingFee = MathExtend.add(filingFee, list.getFilingFee().toString());
                saveCost = MathExtend.add(saveCost, list.getSaveCost().toString());
                announcementFee = MathExtend.add(announcementFee, list.getAnnouncementFee().toString());
                appraisalCost = MathExtend.add(appraisalCost, list.getAppraisalCost().toString());
                executionFee = MathExtend.add(executionFee, list.getExecutionFee().toString());
                securityPremium = MathExtend.add(securityPremium, list.getSecurityPremium().toString());
                barFee = MathExtend.add(barFee, list.getBarFee().toString());
                postFee = MathExtend.add(postFee,list.getPostFee().toString());
                if (list.getAssessmentFee()!=null){
                    assessmentFee = MathExtend.add(assessmentFee, list.getAssessmentFee().toString());

                }
                if (list.getOtherCharges()!=null){
                    otherCharges = MathExtend.add(otherCharges, list.getOtherCharges().toString());
                }

            }
            mfLitigationExpenseApply.setFilingFeeStr(MathExtend.moneyStr(filingFee));
            mfLitigationExpenseApply.setSaveCostStr(MathExtend.moneyStr(saveCost));
            mfLitigationExpenseApply.setAnnouncementFeeStr(MathExtend.moneyStr(announcementFee));
            mfLitigationExpenseApply.setAppraisalCostStr(MathExtend.moneyStr(appraisalCost));
            mfLitigationExpenseApply.setPostFeeStr(MathExtend.moneyStr(postFee));
            mfLitigationExpenseApply.setExecutionFeeStr(MathExtend.moneyStr(executionFee));
            mfLitigationExpenseApply.setSecurityPremiumStr(MathExtend.moneyStr(securityPremium));
            mfLitigationExpenseApply.setBarFeeStr(MathExtend.moneyStr(barFee));
            mfLitigationExpenseApply.setAssessmentFeeStr(MathExtend.moneyStr(assessmentFee));
            mfLitigationExpenseApply.setOtherChargesStr((otherCharges));
            mfLitigationExpenseApply.setExecutionPrcpStr(MathExtend.moneyStr(executionPrcp));//执行本金
            mfLitigationExpenseApply.setExecutionIntstStr(MathExtend.moneyStr(executionIntst));//执行利息
        }else {
            mfLitigationExpenseApply.setFilingFeeStr(MathExtend.moneyStr("0.00"));
            mfLitigationExpenseApply.setSaveCostStr(MathExtend.moneyStr("0.00"));
            mfLitigationExpenseApply.setAnnouncementFeeStr(MathExtend.moneyStr("0.00"));
            mfLitigationExpenseApply.setAppraisalCostStr(MathExtend.moneyStr("0.00"));
            mfLitigationExpenseApply.setPostFeeStr(MathExtend.moneyStr("0.00"));
            mfLitigationExpenseApply.setExecutionFeeStr(MathExtend.moneyStr("0.00"));
            mfLitigationExpenseApply.setSecurityPremiumStr(MathExtend.moneyStr("0.00"));
            mfLitigationExpenseApply.setBarFeeStr(MathExtend.moneyStr("0.00"));
            mfLitigationExpenseApply.setAssessmentFeeStr(MathExtend.moneyStr("0.00"));
            mfLitigationExpenseApply.setOtherChargesStr(("0.00"));
            mfLitigationExpenseApply.setExecutionPrcpStr(MathExtend.moneyStr("0.00"));//执行本金
            mfLitigationExpenseApply.setExecutionIntstStr(MathExtend.moneyStr("0.00"));//执行利息
        }

        FormData formfeesumdetail = formService.getFormData("feesumdetail");
        getObjValue(formfeesumdetail, mfLitigationExpenseApply);
        model.addAttribute("formfeesumdetail", formfeesumdetail);
        model.addAttribute("query", "");
        return "/component/pact/assetmanage/MfFeeSumDetail";

    }
    /**
     * 单子段编辑额回调处理
     *
     * @param
     * @return dataMap
     * @throws Exception
     */
    @RequestMapping(value = "/dealCostSumAmtAjax")
    @ResponseBody
    public Map<String, Object> dealCostSumAmtAjax(String assetId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();

        try {
            mfLitigationExpenseApply.setAssetId(assetId);
            List<MfLitigationExpenseApply> mfLitigationExpenseApplyList = mfLitigationExpenseApplyFeign.findByAssetId(mfLitigationExpenseApply);
           Double costSum = 0.00;
            if (mfLitigationExpenseApplyList.size()>0){
                for (MfLitigationExpenseApply lists : mfLitigationExpenseApplyList){
                    costSum = MathExtend.add(costSum,lists.getFeeAmt());
                }
            }
            MfLawsuit mfLawsuit = new MfLawsuit();
            mfLawsuit.setCaseId(assetId);
            mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
            if(mfLawsuit !=null && mfLawsuit.getCost()!=null){//更新法律诉讼表中费用金额
                MfLawsuit lawsuit = new MfLawsuit();
                lawsuit.setCaseId(assetId);
                lawsuit.setCost(costSum);
                mfLawsuitFeign.update(lawsuit);
            }
            dataMap.put("flag", "success");
            dataMap.put("costSum", MathExtend.moneyStr(costSum));
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }
}
