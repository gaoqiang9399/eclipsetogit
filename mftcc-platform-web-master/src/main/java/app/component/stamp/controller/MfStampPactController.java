package app.component.stamp.controller;

import app.base.User;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.pact.entity.MfBusPactExtend;
import app.component.pact.feign.MfBusPactExtendFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.stamp.entity.MfStampBaseInfo;
import app.component.stamp.entity.MfStampPact;
import app.component.stamp.feign.MfStampPactFeign;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.component.sysTaskInfoInterface.SysTaskInfoInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mfStampPact")
public class MfStampPactController extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private MfStampPactFeign mfStampPactFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
    @Autowired
    private SysInterfaceFeign sysInterfaceFeign;
    @Autowired
    private SysTaskInfoInterfaceFeign sysTaskInfoInterfaceFeign;
    @Autowired
    private SysUserFeign sysUserFeign;
    @Autowired
    private MfBusPactExtendFeign mfBusPactExtendFeign;

    @RequestMapping(value = "/getListPage")
    public String getOverPactListPage(Model model,String queryType) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("queryType", queryType);
        return "/component/stamp/MfBusPact_List";
    }
    @RequestMapping(value = "/getListPageForSeal")
    public String getListPageForSeal(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/stamp/MfBusPact_ListForSeal";
    }
    @RequestMapping(value = "/findPactListByAjax")
    @ResponseBody
    public Map<String, Object> findPactListByAjax(Integer pageNo, Integer pageSize, String tableId,
                                                      String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfStampPact mfStampPact = new MfStampPact();
        try {
            mfStampPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfStampPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfStampPact.setCriteriaList(mfStampPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfStampPact", mfStampPact));
            ipage = mfStampPactFeign.findByPageStamp(ipage);
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
    @RequestMapping(value = "/findPactListGCDBByAjax")
    @ResponseBody
    public Map<String, Object> findPactListGCDBByAjax(Integer pageNo, Integer pageSize, String tableId,
                                                      String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfStampPact mfStampPact = new MfStampPact();
        try {
            mfStampPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfStampPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfStampPact.setCriteriaList(mfStampPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfStampPact", mfStampPact));
            ipage = mfStampPactFeign.findPactListGCDB(ipage);
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
    @RequestMapping(value = "/findPactListByAjaxForSeal")
    @ResponseBody
    public Map<String, Object> findPactListByAjaxForSeal(Integer pageNo, Integer pageSize, String tableId,
                                                         String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfStampPact mfStampPact = new MfStampPact();
        try {
            mfStampPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfStampPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfStampPact.setCriteriaList(mfStampPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfStampPact", mfStampPact));
            mfStampPact.setStampSts("'3','4','5'");
            ipage = mfStampPactFeign.findByPage(ipage);
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
    public String input(Model model, String queryType) throws Exception {
        MfStampPact mfStampPact = new MfStampPact();
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData forminitStampApplyBase = formService.getFormData("initStampApplyBase");
        model.addAttribute("queryType", queryType);
        model.addAttribute("forminitStampApplyBase", forminitStampApplyBase);
        model.addAttribute("query", "");
        return "/component/stamp/MfStampPact_Insert";
    }
    /**
     * 选择用印合同后，新增申请页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputForView")
    public String inputForView(Model model, String stampId,String queryType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId = "stamppactedit";
        if(StringUtil.isNotEmpty(queryType) && "2".equals(queryType)){
            formId = "stamppactedit_GCDB";
        }
        FormData formstamppactedit = formService.getFormData(formId);
        MfStampPact mfStampPact = mfStampPactFeign.initData(stampId);

        List<MfBusPactExtend> mfBusPactExtendList = new ArrayList<MfBusPactExtend>();
        getObjValue(formstamppactedit, mfStampPact);
        if("1".equals(mfStampPact.getStampType())){
            MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
            mfBusPactExtend.setCreditAppId(mfStampPact.getCreditAppId());
            mfBusPactExtend.setStampPactId("1");
            mfBusPactExtendList = mfBusPactExtendFeign.getAllListForCredit(mfBusPactExtend) ;
        }else{
            MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
            mfBusPactExtend.setAppId(mfStampPact.getAppId());
            mfBusPactExtend.setStampPactId("1");
            mfBusPactExtendList = mfBusPactExtendFeign.getAllListForApply(mfBusPactExtend) ;
        }
        model.addAttribute("formstamppactedit", formstamppactedit);
        model.addAttribute("mfBusPactExtendList", mfBusPactExtendList);
        model.addAttribute("stampId", stampId);
        model.addAttribute("query", "");
        model.addAttribute("stampType", mfStampPact.getStampType());
        model.addAttribute("queryType", queryType);
        model.addAttribute("mfStampPact", mfStampPact);
        return "/component/stamp/MfStampPact_inputView";
    }
    /**
     * 新增用印
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map <String, Object> insertAjax(String ajaxData,String templateId,String docNum,String extendId,String docExtendNum,String queryType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfStampPact mfStampPact = new MfStampPact();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formstamppactedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formstamppactedit, getMapByJson(ajaxData));
            if (this.validateFormData(formstamppactedit)) {
                setObjValue(formstamppactedit, mfStampPact);
                mfStampPact.setOpNo(User.getRegNo(request));
                mfStampPact.setOpName(User.getRegName(request));
                mfStampPact.setBrNo(User.getOrgNo(request));
                mfStampPact.setBrName(User.getOrgName(request));
                mfStampPact.setQueryType(queryType);
                dataMap.put("mfStampPact", mfStampPact);
                dataMap.put("templateId", templateId);
                dataMap.put("extendId", extendId);
                dataMap.put("docNum", docNum);
                dataMap.put("docExtendNum", docExtendNum);
                mfStampPact = mfStampPactFeign.insertStartProcess(dataMap);
                //封装提示到下一岗位的消息
                String msg = "操作成功！";
                TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfStampPact.getId(), null);
                if(taskAppro != null) {
                    String approvePartName = "";
                    //封装审批岗位及审批人信息
                    if (taskAppro.getAssignee() != null && !"".equals(taskAppro.getAssignee()) && !"null".equals(taskAppro.getAssignee())) {
                        SysUser sysUser = new SysUser();
                        sysUser.setOpNo(taskAppro.getAssignee());
                        sysUser = sysUserFeign.getById(sysUser);
                        approvePartName = sysUser.getOpName();
                        Map<String, String> paramMap = new HashMap<String, String>();
                        paramMap.put("approvalType", "审批同意，");
                        paramMap.put("userRole", taskAppro.getDescription() + "岗位下的" + approvePartName);
                        paramMap.put("operate", "审批");
                        msg = MessageEnum.SUCCEED_APPROVAL_COMMON.getMessage(paramMap);
                    } else {
                        //assignee为空时，当前审批配置的是角色
                        List<SysTaskInfo> list = new ArrayList<SysTaskInfo>();
                        SysTaskInfo sysTaskInfo1 = new SysTaskInfo();
                        sysTaskInfo1.setBizPkNo(mfStampPact.getId());
                        sysTaskInfo1.setWkfTaskNo(String.valueOf(taskAppro.getDbid()));
                        list = sysTaskInfoInterfaceFeign.getByAppNoTaskId(sysTaskInfo1);
                        for (int i = 0; i < list.size(); i++) {
                            SysUser sysUser = new SysUser();
                            sysUser.setOpNo(list.get(i).getUserNo());
                            sysUser = sysUserFeign.getById(sysUser);
                            if (i == list.size() - 1) {
                                approvePartName = approvePartName + sysUser.getOpName();
                            } else {
                                approvePartName = approvePartName + sysUser.getOpName()+ "/";
                            }

                        }
                        Map<String, String> paramMap = new HashMap<String, String>();
                        paramMap.put("approvalType", "审批同意，");
                        paramMap.put("userRole", taskAppro.getDescription() + "岗位下的" + approvePartName);
                        paramMap.put("operate", "审批");
                        msg = MessageEnum.SUCCEED_APPROVAL_COMMON.getMessage(paramMap);
                    }
                }
                dataMap.put("flag", "success");
                dataMap.put("msg", msg);
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
    public String getViewPoint(Model model, String id, String taskId, String activityType,String queryType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String fromId = "stamppactapproval";
        if("2".equals(queryType)){
            fromId = "stamppactapproval_GCDB";
        }
        FormData formstamppactapproval = formService.getFormData(fromId);
        MfStampPact mfStampPact = new MfStampPact();
        mfStampPact.setId(id);
        mfStampPact = mfStampPactFeign.getById(mfStampPact);
        List<MfBusPactExtend> mfBusPactExtendList = new ArrayList<MfBusPactExtend>();
        MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
        mfBusPactExtend.setStampPactId(mfStampPact.getId());
        mfBusPactExtendList = mfBusPactExtendFeign.getAllListForStamp(mfBusPactExtend) ;
        JsonTableUtil jtu = new JsonTableUtil();
        String tableId = "tablepactExtendStampView";
        if(StringUtil.isNotEmpty(queryType) && "2".equals(queryType)){
            tableId = "tablepactExtendStamp_GCDB";
        }
        String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", mfBusPactExtendList, null, true);
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
        model.addAttribute("formstamppactapproval", formstamppactapproval);
        model.addAttribute("query", "");
        model.addAttribute("mfStampPact", mfStampPact);
        model.addAttribute("queryType", queryType);
        model.addAttribute("tableHtml", tableHtml);
        return "/component/stamp/StampPact_WkfViewPoint";
    }
    @RequestMapping(value = "/stampDetail")
    public String stampDetail(Model model, String id,String queryType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId = "stampApplyDetail";
        if("2".equals(queryType)){
            formId = "stampApplyDetail_GCDB";
        }
        FormData formstampApplyDetail = formService.getFormData(formId);
        MfStampPact mfStampPact = new MfStampPact();
        mfStampPact.setId(id);
        mfStampPact = mfStampPactFeign.getById(mfStampPact);
        getObjValue(formstampApplyDetail, mfStampPact);
        String extendId =mfStampPact.getExtendId();
        String docExtendNum ="";
        if(StringUtil.isNotEmpty(mfStampPact.getDocExtendNum())){
            docExtendNum =mfStampPact.getDocExtendNum();
        }
        List<MfBusPactExtend> mfBusPactExtendList = new ArrayList<MfBusPactExtend>();
        if(StringUtil.isNotEmpty(extendId)){
            String[] extendIds = extendId.split(",");
            String[] docExtendNums = docExtendNum.split(",");
            for (int i = 0; i < extendIds.length; i++) {
                String s = extendIds[i];
                if(StringUtil.isNotEmpty(s)){
                    MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
                    mfBusPactExtend.setId(s);
                    mfBusPactExtend = mfBusPactExtendFeign.getById(mfBusPactExtend);
                    if(mfBusPactExtend!=null){
                        if(docExtendNums.length>0){
                            mfBusPactExtend.setDocExtendNum(docExtendNums[i]);
                        }
                        mfBusPactExtendList.add(mfBusPactExtend);
                    }
                }

            }
        }
        JsonTableUtil jtu = new JsonTableUtil();
        String tableId = "tablepactExtendStampView";
        if(StringUtil.isNotEmpty(queryType) && "2".equals(queryType)){
            tableId = "tablepactExtendStamp_GCDB";
        }
        String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", mfBusPactExtendList, null, true);
        request.setAttribute("mfStampPact", mfStampPact);
        model.addAttribute("formstampApplyDetail", formstampApplyDetail);
        model.addAttribute("query", "");
        model.addAttribute("queryType", queryType);
        model.addAttribute("mfStampPact", mfStampPact);
        model.addAttribute("tableHtml", tableHtml);
        return "/component/stamp/StampPact_ApplyDetail";
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

    /**
     * 方法描述： 获取可用印的合同列表数据
     *
     * @param pageNo
     * @param ajaxData
     * @return
     * @throws Exception Map<String,Object>
     * @author YuShuai
     * @date 2018年6月26日 下午4:48:02
     */
    @RequestMapping(value = "/getStampBaseList")
    @ResponseBody
    public Map <String, Object> getStampBaseList(int pageNo, Integer pageSize,String ajaxData,String queryType) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfStampBaseInfo mfStampBaseInfo = new MfStampBaseInfo();
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);
            Map <String, Object> paramMap = new HashMap <String, Object>();
            mfStampBaseInfo.setStampSts("0|1");
            mfStampBaseInfo.setQueryType(queryType);
            paramMap.put("mfStampBaseInfo", mfStampBaseInfo);
            paramMap.put("ajaxData", ajaxData);
            mfStampBaseInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
            ipage.setParams(paramMap);
            ipage = mfStampPactFeign.getStampBaseListNew(ipage);
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
     * 方法描述： 获取可用印的合同列表数据
     *
     * @param pageNo
     * @param ajaxData
     * @return
     * @throws Exception Map<String,Object>
     * @author YuShuai
     * @date 2018年6月26日 下午4:48:02
     */
    @RequestMapping(value = "/getStampBaseListNew")
    @ResponseBody
    public Map <String, Object> getStampBaseListNew(int pageNo, Integer pageSize,String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfStampBaseInfo mfStampBaseInfo = new MfStampBaseInfo();
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);
            Map <String, Object> paramMap = new HashMap <String, Object>();
            mfStampBaseInfo.setStampSts("0|1");
            paramMap.put("mfStampBaseInfo", mfStampBaseInfo);
            paramMap.put("ajaxData", ajaxData);
            mfStampBaseInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
            ipage.setParams(paramMap);
            ipage = mfStampPactFeign.getStampBaseListNew(ipage);
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
     * 授信合同生成时添加非业务生成合同
     * @param
     * @return
     */
    @RequestMapping(value = "/pactExtendView")
    public String pactExtendView(Model model,String appId,String id)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String formId="pactExtendCreditDetail";
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
}
