package app.component.stamp.controller;

import app.base.User;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.msgconf.feign.PliWarningFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.stamp.entity.MfStampCredit;
import app.component.stamp.feign.MfStampCreditFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mfStampCredit")
public class MfStampCreditController extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private MfStampCreditFeign mfStampCreditFeign;
    @Autowired
    private MfCusCreditContractFeign mfCusCreditContractFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;

    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request,response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("CUS_TYPE");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        model.addAttribute("query", "");
        return "/component/stamp/MfCusCredit_List";
    }

    @RequestMapping(value = "/findStampContract")
    @ResponseBody
    public Map<String, Object> findStampContract(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map <String, Object> dataMap = new HashMap<String, Object>();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        try {
            mfCusCreditContract.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusCreditContract.setCriteriaList(mfCusCreditContract, ajaxData);//我的筛选
//            mfCusCreditContract.setCreditSts(BizPubParm.CREDIT_STS_3);
            //this.getRoleConditions(mfCusCreditContract,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfCusCreditContract", mfCusCreditContract));
            //自定义查询Bo方法
            ipage = mfStampCreditFeign.findStampContract(ipage);
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
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String id) throws Exception {
        MfStampCredit mfStampCredit = new MfStampCredit();
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formstampcreditedit = formService.getFormData("stampcreditedit");
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setId(id);
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
        mfStampCredit.setProtocolNo(WaterIdUtil.getWaterId());
        mfStampCredit.setKindNo(mfCusCreditContract.getKindNo());
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setKindNo(mfCusCreditContract.getKindNo());
        MfSysKind sysKind = mfSysKindFeign.getById(mfSysKind);
        if (sysKind != null) {
            mfStampCredit.setKindName(sysKind.getKindName());
        }
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(mfCusCreditContract.getCreditAppId());
        mfCusCreditApply = creditApplyInterfaceFeign.getCusCreditApply(mfCusCreditApply);
        mfStampCredit.setAgenciesId(mfCusCreditApply.getAgenciesId());
        mfStampCredit.setAgenciesName(mfCusCreditApply.getAgenciesName());
        mfStampCredit.setCreditSum(mfCusCreditContract.getCreditSum());
        mfStampCredit.setCreditTerm(mfCusCreditContract.getCreditTerm());
        mfStampCredit.setCusNo(mfCusCreditContract.getCusNo());
        mfStampCredit.setCusName(mfCusCreditContract.getCusName());
        mfStampCredit.setContractId(mfCusCreditContract.getId());
        getObjValue(formstampcreditedit, mfStampCredit);
        model.addAttribute("formstampcreditedit", formstampcreditedit);
        model.addAttribute("query", "");
        return "/component/stamp/MfStampCredit_Insert";
    }

    /**
     * 新增用印
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map <String, Object> insertAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfStampCredit mfStampCredit = new MfStampCredit();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
            FormData formstampcreditedit = formService.getFormData("stampcreditedit");
            getFormValue(formstampcreditedit, getMapByJson(ajaxData));
            if (this.validateFormData(formstampcreditedit)) {
                setObjValue(formstampcreditedit, mfStampCredit);
                mfStampCredit.setOpNo(User.getRegNo(request));
                mfStampCredit.setOpName(User.getRegName(request));
                mfStampCredit.setBrNo(User.getOrgNo(request));
                mfStampCredit.setBrName(User.getOrgName(request));
                dataMap.put("mfStampCredit", mfStampCredit);
                mfStampCredit = mfStampCreditFeign.insertStartProcess(dataMap);
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

    @RequestMapping(value = "/getViewPoint")
    public String getViewPoint(Model model, String id, String taskId, String activityType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formstampcreditapproval = formService.getFormData("stampcreditapproval");
        MfStampCredit mfStampCredit = new MfStampCredit();
        mfStampCredit.setId(id);
        mfStampCredit = mfStampCreditFeign.getById(mfStampCredit);
        getObjValue(formstampcreditapproval, mfStampCredit);
        CodeUtils codeUtils = new CodeUtils();
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, taskId);// 当前审批节点task
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
        opinionTypeMap.put("hideOpinionType", "4"); // 隐藏审批类型
        opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
        opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
        List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(formstampcreditapproval, "opinionType", "optionArray", opinionTypeList);
        request.setAttribute("mfStampCredit", mfStampCredit);
        model.addAttribute("formstampcreditapproval", formstampcreditapproval);
        model.addAttribute("query", "");
        return "/component/stamp/StampCredit_WkfViewPoint";
    }

    /**
     *
     * 方法描述： 审批提交（审批意见保存）
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2016-5-26 上午10:53:17t
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/submitUpdateAjax")
    @ResponseBody
    public Map<String, Object> submitUpdateAjax(String ajaxData, String ajaxDataList, String taskId, String id, String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfStampCredit mfStampCredit = new MfStampCredit();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //判断当前节点是否可进行审批
//        TaskImpl taskApprove = wkfInterfaceFeign.getTask(appId, "");
        String appOpNo = User.getRegNo(request);
        TaskImpl taskApprove = wkfInterfaceFeign.getTaskWithUser(id, "",appOpNo);
        if(!taskId.equals(String.valueOf(taskApprove.getDbid()))){//不相等则审批不在此环节
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
            return dataMap;
        }
        Map map = getMapByJson(ajaxData);
        FormData formstampcreditapproval = formService.getFormData((String) map.get("formId"));
        getFormValue(formstampcreditapproval, map);
        setObjValue(formstampcreditapproval, mfStampCredit);
        dataMap = getMapByJson(ajaxData);
        String opinionType = String.valueOf(dataMap.get("opinionType"));
        String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
        JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
        try {
            mfStampCredit.setCurrentSessionRegNo(User.getRegNo(request));
            mfStampCredit.setCurrentSessionRegName(User.getRegName(request));
            mfStampCredit.setCurrentSessionOrgNo(User.getOrgNo(request));
            mfStampCredit.setCurrentSessionOrgName(User.getOrgName(request));
            dataMap.put("mfStampCredit", mfStampCredit);
            dataMap.put("approvalOpinion", approvalOpinion);
            TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, taskId);
            Result res = mfStampCreditFeign.doCommit(taskId, id, opinionType, transition, User.getRegNo(request), nextUser, dataMap);
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
}
