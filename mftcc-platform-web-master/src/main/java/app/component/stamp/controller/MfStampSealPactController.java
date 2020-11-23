package app.component.stamp.controller;

import app.base.User;
import app.component.auth.entity.MfCusCreditContract;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.entity.MfBusPactExtend;
import app.component.pact.feign.MfBusPactExtendFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.stamp.entity.MfStampCredit;
import app.component.stamp.entity.MfStampPact;
import app.component.stamp.entity.MfStampSealPact;
import app.component.stamp.feign.MfStampPactFeign;
import app.component.stamp.feign.MfStampSealPactFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.commons.beanutils.PropertyUtils;
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

@Controller
@RequestMapping("/mfStampSealPact")
public class MfStampSealPactController extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private MfStampPactFeign mfStampPactFeign;
    @Autowired
    private MfStampSealPactFeign mfStampSealPactFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
    @Autowired
    private MfBusPactExtendFeign mfBusPactExtendFeign;

    @RequestMapping(value = "/getListPage")
    public String getOverPactListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/stamp/MfBusPact_List";
    }


    @RequestMapping(value = "/findPactListByAjax")
    @ResponseBody
    public Map<String, Object> findPactListByAjax(Integer pageNo, Integer pageSize, String tableId,
                                                      String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            mfBusPact.setPactSts("4");
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
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String id) throws Exception {
        MfStampSealPact mfStampSealPact = new MfStampSealPact();
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formstampsealpactedit = formService.getFormData("stampsealpactedit");
        MfStampPact mfStampPact = new MfStampPact();
        mfStampPact.setId(id);
        mfStampPact = mfStampPactFeign.getById(mfStampPact);
        List<MfBusPactExtend> mfBusPactExtendList = new ArrayList<MfBusPactExtend>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        mfBusPactExtend.setStampPactId(mfStampPact.getId());
        mfBusPactExtendList = mfBusPactExtendFeign.getAllListForStamp(mfBusPactExtend) ;

        PropertyUtils.copyProperties(mfStampSealPact, mfStampPact);
        mfStampSealPact.setStampId(id);
        mfStampSealPact.setStampTime(mfStampPact.getRegTime());
        mfStampSealPact.setOpNo(User.getRegNo(request));
        mfStampSealPact.setOpName(User.getRegName(request));
        mfStampSealPact.setBrNo(User.getOrgNo(request));
        mfStampSealPact.setBrName(User.getOrgName(request));
        mfStampSealPact.setRegTime(DateUtil.getDateTime());
        mfStampSealPact.setStampType(mfStampPact.getStampType());
        getObjValue(formstampsealpactedit, mfStampSealPact);
        model.addAttribute("formstampsealpactedit", formstampsealpactedit);
        model.addAttribute("mfStampSealPact", mfStampSealPact);
        model.addAttribute("query", "");
        model.addAttribute("mfBusPactExtendList", mfBusPactExtendList);
        return "/component/stamp/MfStampSealPact_Insert";
    }
    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail")
    public String detail(Model model, String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formstampsealpactedit = formService.getFormData("stampsealpactedit");
        MfStampSealPact mfStampSealPact = new MfStampSealPact();
        mfStampSealPact.setStampId(id);
        mfStampSealPact = mfStampSealPactFeign.getById(mfStampSealPact);
        List<MfBusPactExtend> mfBusPactExtendList = new ArrayList<MfBusPactExtend>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        mfBusPactExtend.setStampPactId(mfStampSealPact.getStampId());
        mfBusPactExtendList = mfBusPactExtendFeign.getAllListForStamp(mfBusPactExtend) ;
        getObjValue(formstampsealpactedit, mfStampSealPact);
        model.addAttribute("formstampsealpactedit", formstampsealpactedit);
        model.addAttribute("mfStampSealPact", mfStampSealPact);
        model.addAttribute("mfBusPactExtendList", mfBusPactExtendList);
        model.addAttribute("query", "");
        return "/component/stamp/MfStampSealPact_Detail";
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
        MfStampSealPact mfStampSealPact = new MfStampSealPact();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formstampsealpactedit = formService.getFormData("stampsealpactedit");
            getFormValue(formstampsealpactedit, getMapByJson(ajaxData));
            if (this.validateFormData(formstampsealpactedit)) {
                setObjValue(formstampsealpactedit, mfStampSealPact);
                mfStampSealPact.setOpNo(User.getRegNo(request));
                mfStampSealPact.setOpName(User.getRegName(request));
                mfStampSealPact.setBrNo(User.getOrgNo(request));
                mfStampSealPact.setBrName(User.getOrgName(request));
                dataMap.put("mfStampSealPact", mfStampSealPact);
                mfStampSealPact = mfStampSealPactFeign.insert(dataMap);
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
        FormData formstamppactapproval = formService.getFormData("stamppactapproval");
        MfStampPact mfStampPact = new MfStampPact();
        mfStampPact.setId(id);
        mfStampPact = mfStampPactFeign.getById(mfStampPact);
        getObjValue(formstamppactapproval, mfStampPact);
        CodeUtils codeUtils = new CodeUtils();
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, taskId);// 当前审批节点task
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
        opinionTypeMap.put("hideOpinionType", "4"); // 隐藏审批类型
        opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
        opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
        List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(formstamppactapproval, "opinionType", "optionArray", opinionTypeList);
        request.setAttribute("mfStampPact", mfStampPact);
        model.addAttribute("formstamppactapproval", formstamppactapproval);
        model.addAttribute("query", "");
        return "/component/stamp/StampPact_WkfViewPoint";
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
        MfStampPact mfStampPact = new MfStampPact();
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
        FormData formstamppactapproval = formService.getFormData("stamppactapproval");
        getFormValue(formstamppactapproval, map);
        setObjValue(formstamppactapproval, mfStampPact);
        dataMap = getMapByJson(ajaxData);
        String opinionType = String.valueOf(dataMap.get("opinionType"));
        String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
        JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
        try {
            mfStampPact.setCurrentSessionRegNo(User.getRegNo(request));
            mfStampPact.setCurrentSessionRegName(User.getRegName(request));
            mfStampPact.setCurrentSessionOrgNo(User.getOrgNo(request));
            mfStampPact.setCurrentSessionOrgName(User.getOrgName(request));
            dataMap.put("mfStampPact", mfStampPact);
            dataMap.put("approvalOpinion", approvalOpinion);
            TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, taskId);
            Result res = mfStampPactFeign.doCommit(taskId, id, opinionType, transition, User.getRegNo(request), nextUser, dataMap);
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
