package app.component.archives.controller;

import app.base.User;
import app.component.archives.entity.*;
import app.component.archives.feign.ArchiveInfoBorrowDetailFeign;
import app.component.archives.feign.ArchiveInfoBorrowFeign;
import app.component.archives.feign.ArchiveInfoMainFeign;
import app.component.common.BizPubParm;
import app.component.pact.repay.entity.MfPreRepayApply;
import app.component.stamp.entity.MfStampCredit;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import app.component.sysTaskInfoInterface.SysTaskInfoInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/archiveInfoBorrow")
public class ArchiveInfoBorrowController  extends BaseFormBean {

    @Autowired
    private ArchiveInfoBorrowFeign archiveInfoBorrowFeign;
    @Autowired
    private ArchiveInfoBorrowDetailFeign archiveInfoBorrowDetailFeign;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private ArchiveInfoMainFeign archiveInfoMainFeign;
    @Autowired
    private SysTaskInfoInterfaceFeign sysTaskInfoInterfaceFeign;
    @Autowired
    private SysUserFeign sysUserFeign;

    /**
     * 借阅列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPageForIn")
    public String getListPageForIn(Model model) throws Exception {
        ActionContext.initialize(request,response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("borrow_sts");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        JSONArray docTypeJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_TYPE");
        this.getHttpRequest().setAttribute("docTypeJsonArray", docTypeJsonArray);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoBorrow_InList";
    }

    /**
     * 借出列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPageForOut")
    public String getListPageForOut(Model model) throws Exception {
        ActionContext.initialize(request,response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("borrow_sts");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        JSONArray docTypeJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_TYPE");
        this.getHttpRequest().setAttribute("docTypeJsonArray", docTypeJsonArray);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoBorrow_OutList";
    }

    /**
     * 档案归还列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPageForReturn")
    public String getListPageForReturn(Model model) throws Exception {
        ActionContext.initialize(request,response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("borrow_sts");
        this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
        JSONArray docTypeJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_TYPE");
        this.getHttpRequest().setAttribute("docTypeJsonArray", docTypeJsonArray);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoBorrow_ReturnList";
    }

    /**
     * 根据detailNo查询借出记录
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBorrowVoucherDetail")
    public String getBorrowVoucherDetail(Model model,String archiveDetailNo) throws Exception {
        ActionContext.initialize(request, response);
        //资料列表
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        List<ArchiveInfoBorrow> borrowOutList = new ArrayList<ArchiveInfoBorrow>();
        archiveInfoBorrow.setOptType(BizPubParm.ARCHIVE_OPT_TYPE_02);
        archiveInfoBorrow.setArchiveDetailNo(archiveDetailNo);
        borrowOutList = archiveInfoBorrowFeign.getBorrowVoucherDetail(archiveInfoBorrow);
        model.addAttribute("borrowOutList", borrowOutList);
        model.addAttribute("query", "");
        return "/component/archives/ArchiveInfoBorrow_VoucherDetail";
    }

    /**
     * 根据appId查询借阅借出记录弹窗
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBorrowPageForQuery")
    public String getBorrowPageForQuery(Model model,String appId) throws Exception {
        ActionContext.initialize(request, response);
        //资料列表
        List<ArchiveInfoBorrow> borrowInList = new ArrayList<ArchiveInfoBorrow>();
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        archiveInfoBorrow.setAppId(appId);
        archiveInfoBorrow.setOptType(BizPubParm.ARCHIVE_OPT_TYPE_01);
        borrowInList = archiveInfoBorrowFeign.getBorrowListByAppId(archiveInfoBorrow);

        List<ArchiveInfoBorrow> borrowOutList = new ArrayList<ArchiveInfoBorrow>();
        archiveInfoBorrow.setOptType(BizPubParm.ARCHIVE_OPT_TYPE_02);
        borrowOutList = archiveInfoBorrowFeign.getBorrowListByAppId(archiveInfoBorrow);
        model.addAttribute("borrowInList", borrowInList);
        model.addAttribute("borrowOutList", borrowOutList);
        model.addAttribute("query", "");
        return "/component/archives/ArchiveInfoBorrow_DetailDoc";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String optType,String status) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        try {
            archiveInfoBorrow.setCustomQuery(ajaxData);//自定义查询参数赋值
            archiveInfoBorrow.setCriteriaList(archiveInfoBorrow, ajaxData);//我的筛选
            archiveInfoBorrow.setCustomSorts(ajaxData);//自定义排序
            archiveInfoBorrow.setOptType(optType);
            if(status != null && !"".equals(status)){
                archiveInfoBorrow.setStatus(status);
            }
            //档案归还时，只查询审批通过的记录
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);//异步传页面翻页参数
            ipage.setParams(this.setIpageParams("archiveInfoBorrow",archiveInfoBorrow));
            //自定义查询Bo方法
            ipage = archiveInfoBorrowFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage",ipage);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 借阅申请页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputIn")
    public String inputIn(Model model) throws Exception{
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        FormData formarchiveborrowin = formService.getFormData("archiveborrowin");
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        archiveInfoBorrow.setOpNo(User.getRegNo(request));
        archiveInfoBorrow.setOpName(User.getRegName(request));
        archiveInfoBorrow.setBrNo(User.getOrgNo(request));
        archiveInfoBorrow.setBrName(User.getOrgName(request));
        archiveInfoBorrow.setBorrowerNo(User.getRegNo(request));
        archiveInfoBorrow.setBorrowerName(User.getRegName(request));
        archiveInfoBorrow.setBorrowingDate(DateUtil.getDate());
        getObjValue(formarchiveborrowin, archiveInfoBorrow);
        model.addAttribute("formarchiveborrowin", formarchiveborrowin);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoBorrow_In";
    }

    /**
     * AJAX借阅申请新增
     * @return
     * @throws Exception archiveInfoBorrow
     */
    @RequestMapping(value = "/insertInAjax")
    @ResponseBody
    public Map<String, Object> insertInAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            FormData formarchiveborrowin = formService.getFormData("archiveborrowin");
            getFormValue(formarchiveborrowin, getMapByJson(ajaxData));
            if(this.validateFormDataAnchor(formarchiveborrowin)){
                ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
                setObjValue(formarchiveborrowin, archiveInfoBorrow);
                archiveInfoBorrow.setOptType(BizPubParm.ARCHIVE_OPT_TYPE_01);
                //添加系统默认字段
                archiveInfoBorrow.setLstModTime(DateUtil.getDateTime());
                archiveInfoBorrow.setRegTime(DateUtil.getDateTime());
                archiveInfoBorrow = archiveInfoBorrowFeign.insert(archiveInfoBorrow);
                //封装提示到下一岗位的消息
                String msg = "操作成功！";
                TaskImpl taskAppro = wkfInterfaceFeign.getTask(archiveInfoBorrow.getBorrowId(), null);
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
                        sysTaskInfo1.setBizPkNo(archiveInfoBorrow.getBorrowId());
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
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "操作失败！");
            throw e;
        }
        return dataMap;
    }

    /**
     * 借出申请界面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputOut")
    public String inputOut(Model model) throws Exception{
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        FormData formarchiveborrowout = formService.getFormData("archiveborrowout");
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        archiveInfoBorrow.setOpNo(User.getRegNo(request));
        archiveInfoBorrow.setOpName(User.getRegName(request));
        archiveInfoBorrow.setBrNo(User.getOrgNo(request));
        archiveInfoBorrow.setBrName(User.getOrgName(request));
        archiveInfoBorrow.setBorrowerNo(User.getRegNo(request));
        archiveInfoBorrow.setBorrowerName(User.getRegName(request));
        archiveInfoBorrow.setBorrowingDate(DateUtil.getDate());
        getObjValue(formarchiveborrowout, archiveInfoBorrow);
        model.addAttribute("formarchiveborrowout", formarchiveborrowout);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoBorrow_Out";
    }

    /**
     * AJAX借出申请新增
     * @return
     * @throws Exception archiveInfoBorrow
     */
    @RequestMapping(value = "/insertOutAjax")
    @ResponseBody
    public Map<String, Object> insertOutAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            FormData formarchiveborrowout = formService.getFormData("archiveborrowout");
            getFormValue(formarchiveborrowout, getMapByJson(ajaxData));
            if(this.validateFormDataAnchor(formarchiveborrowout)){
                ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
                setObjValue(formarchiveborrowout, archiveInfoBorrow);
                archiveInfoBorrow.setOptType(BizPubParm.ARCHIVE_OPT_TYPE_02);//借出申请
                //添加系统默认字段
                archiveInfoBorrow.setLstModTime(DateUtil.getDateTime());
                archiveInfoBorrow.setRegTime(DateUtil.getDateTime());
                archiveInfoBorrow = archiveInfoBorrowFeign.insert(archiveInfoBorrow);
                //封装提示到下一岗位的消息
                String msg = "操作成功！";
                TaskImpl taskAppro = wkfInterfaceFeign.getTask(archiveInfoBorrow.getBorrowId(), null);
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
                        sysTaskInfo1.setBizPkNo(archiveInfoBorrow.getBorrowId());
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
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "操作失败！");
            throw e;
        }
        return dataMap;
    }

    /**
     * 档案归还界面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputReturn")
    public String inputReturn(Model model, String borrowId) throws Exception{
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        FormData formarchiveborrowreturn = formService.getFormData("archiveborrowreturn");
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        archiveInfoBorrow.setBorrowId(borrowId);
        archiveInfoBorrow = archiveInfoBorrowFeign.getById(archiveInfoBorrow);
        //通过明细表来反显资料信息
        ArchiveInfoBorrowDetail archiveInfoBorrowDetail = new ArchiveInfoBorrowDetail();
        archiveInfoBorrowDetail.setBorrowId(borrowId);
        List<ArchiveInfoBorrowDetail> archiveInfoBorrowDetailList = archiveInfoBorrowDetailFeign.getListByArchiveInfoBorrowDetail(archiveInfoBorrowDetail);
        String docName = "";
        for(ArchiveInfoBorrowDetail borrowDetail : archiveInfoBorrowDetailList){
            docName = docName + "," + borrowDetail.getDocName();
        }
        docName = docName.substring(1);
        archiveInfoBorrow.setDocName(docName);
//        archiveInfoBorrow.setRevertNo(User.getRegNo(request));
//        archiveInfoBorrow.setRevertName(User.getRegName(request));
        archiveInfoBorrow.setRevertActualTime(DateUtil.getDate());
        getObjValue(formarchiveborrowreturn, archiveInfoBorrow);
        model.addAttribute("formarchiveborrowreturn", formarchiveborrowreturn);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoBorrow_Return";
    }

    /**
     * AJAX档案归还保存
     * @return
     * @throws Exception archiveInfoBorrow
     */
    @RequestMapping(value = "/insertReturnAjax")
    @ResponseBody
    public Map<String, Object> insertReturnAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            FormData formarchiveborrowreturn = formService.getFormData("archiveborrowreturn");
            getFormValue(formarchiveborrowreturn, getMapByJson(ajaxData));
            if(this.validateFormDataAnchor(formarchiveborrowreturn)){
                ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
                setObjValue(formarchiveborrowreturn, archiveInfoBorrow);
                archiveInfoBorrowFeign.returnArchive(archiveInfoBorrow);
                dataMap.put("msg", "档案归还成功！");
                dataMap.put("flag", "success");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "档案归还失败！");
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getViewPoint")
    public String getViewPoint(Model model, String borrowId, String taskId, String activityType,String optType,String docType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchiveborrowapproval = null;
        if(BizPubParm.ARCHIVE_OPT_TYPE_01.equals(optType)){//借阅
            formarchiveborrowapproval = formService.getFormData("borrowinapproval");
        }else if(BizPubParm.ARCHIVE_OPT_TYPE_02.equals(optType)){//借出
            formarchiveborrowapproval = formService.getFormData("borrowoutapproval");
        }
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        archiveInfoBorrow.setBorrowId(borrowId);
        archiveInfoBorrow = archiveInfoBorrowFeign.getById(archiveInfoBorrow);
        //通过明细表来反显资料信息
        ArchiveInfoBorrowDetail archiveInfoBorrowDetail = new ArchiveInfoBorrowDetail();
        archiveInfoBorrowDetail.setBorrowId(borrowId);
        List<ArchiveInfoBorrowDetail> archiveInfoBorrowDetailList = archiveInfoBorrowDetailFeign.getListByArchiveInfoBorrowDetail(archiveInfoBorrowDetail);
        String docName = "";
        for(ArchiveInfoBorrowDetail borrowDetail : archiveInfoBorrowDetailList){
            docName = docName + "," + borrowDetail.getDocName();
        }
        docName = docName.substring(1);
        archiveInfoBorrow.setDocName(docName);
        getObjValue(formarchiveborrowapproval, archiveInfoBorrow);
        CodeUtils codeUtils = new CodeUtils();
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(borrowId, taskId);// 当前审批节点task
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
        opinionTypeMap.put("hideOpinionType", "4"); // 隐藏审批类型
        opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
        opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
        List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(formarchiveborrowapproval, "opinionType", "optionArray", opinionTypeList);
        request.setAttribute("archiveInfoBorrow", archiveInfoBorrow);
        model.addAttribute("formarchiveborrowapproval", formarchiveborrowapproval);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoBorrow_WkfViewPoint";
    }

    /**
     * 档案查询中--借出详情
     * @param model
     * @param borrowId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOutDetail")
    public String getOutDetail(Model model, String borrowId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchiveborrowdetail = null;
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        archiveInfoBorrow.setBorrowId(borrowId);
        archiveInfoBorrow = archiveInfoBorrowFeign.getById(archiveInfoBorrow);
        if(BizPubParm.ARCHIVE_OPT_TYPE_01.equals(archiveInfoBorrow.getOptType())){//借阅
            formarchiveborrowdetail = formService.getFormData("archiveborrowindetail");
        }else if(BizPubParm.ARCHIVE_OPT_TYPE_02.equals(archiveInfoBorrow.getOptType())){//借出
            formarchiveborrowdetail = formService.getFormData("archiveborrowoutdetail");
        }
        //通过明细表来反显资料信息
        ArchiveInfoBorrowDetail archiveInfoBorrowDetail = new ArchiveInfoBorrowDetail();
        archiveInfoBorrowDetail.setBorrowId(borrowId);
        List<ArchiveInfoBorrowDetail> archiveInfoBorrowDetailList = archiveInfoBorrowDetailFeign.getListByArchiveInfoBorrowDetail(archiveInfoBorrowDetail);
        String docName = "";
        for(ArchiveInfoBorrowDetail borrowDetail : archiveInfoBorrowDetailList){
            docName = docName + "," + borrowDetail.getDocName();
        }
        docName = docName.substring(1);
        archiveInfoBorrow.setDocName(docName);
        getObjValue(formarchiveborrowdetail, archiveInfoBorrow);
        CodeUtils codeUtils = new CodeUtils();
        request.setAttribute("archiveInfoBorrow", archiveInfoBorrow);
        model.addAttribute("formarchiveborrowdetail", formarchiveborrowdetail);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoBorrow_OutDetail";
    }

    //审批提交
    @RequestMapping(value = "/submitUpdateAjax")
    @ResponseBody
    public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String borrowId, String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map map = getMapByJson(ajaxData);

        String formId = (String) map.get("formId");
        FormData formarchiveborrowapproval = formService.getFormData(formId);
        getFormValue(formarchiveborrowapproval, map);
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        setObjValue(formarchiveborrowapproval, archiveInfoBorrow);
        Result res;
        try {
            dataMap = getMapByJson(ajaxData);
            dataMap.put("orgNo", User.getOrgNo(request));
            String opinionType = String.valueOf(dataMap.get("opinionType"));
            String approveOpinion = String.valueOf(dataMap.get("approveOpinion"));
            res = archiveInfoBorrowFeign.doCommit(taskId, borrowId, opinionType, approveOpinion, transition,User.getRegNo(this.getHttpRequest()), nextUser, archiveInfoBorrow);
            if (res.isSuccess()) {
                dataMap.put("flag", "success");
                if (res.isEndSts()) {
                    dataMap.put("msg", res.getMsg());
                } else {
                    if(AppConstant.OPINION_TYPE_REFUSE.equals(opinionType)){
                        dataMap.put("msg", res.getMsg());
                    }else{
                        Map<String, String> paramMap = new HashMap<String, String>();
                        String approvePartName = "";
                        TaskImpl taskAppro = wkfInterfaceFeign.getTask(borrowId, null);
                        if(taskAppro != null){
                            //封装审批岗位及审批人信息
                            if(taskAppro.getAssignee()!=null){
                                SysUser sysUser = new SysUser();
                                sysUser.setOpNo(taskAppro.getAssignee());
                                sysUser = sysUserFeign.getById(sysUser);
                                approvePartName = sysUser.getOpName();
                                paramMap.put("approvalType","审批同意！");
                                paramMap.put("userRole", taskAppro.getDescription() + "岗位下的" + approvePartName);
                                paramMap.put("operate","审批" );
                            }else{
                                //assignee为空时，当前审批配置的是角色
                                List<SysTaskInfo> list = new ArrayList<SysTaskInfo>();
                                SysTaskInfo sysTaskInfo1 = new SysTaskInfo();
                                sysTaskInfo1.setBizPkNo(borrowId);
                                sysTaskInfo1.setWkfTaskNo(String.valueOf(taskAppro.getDbid()));
                                list = sysTaskInfoInterfaceFeign.getByAppNoTaskId(sysTaskInfo1);
                                for (int i = 0;i<list.size();i++) {
                                    SysUser sysUser = new SysUser();
                                    sysUser.setOpNo(list.get(i).getUserNo());
                                    sysUser = sysUserFeign.getById(sysUser);
                                    if (i == list.size() - 1) {
                                        approvePartName = approvePartName + sysUser.getOpName();
                                    } else {
                                        approvePartName = approvePartName + sysUser.getOpName()+ "/";
                                    }
                                }
                                paramMap.put("approvalType","审批同意！");
                                paramMap.put("userRole", taskAppro.getDescription() + "岗位下的" + approvePartName);
                                paramMap.put("operate","审批" );
                            }
                        }
                        dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL_COMMON.getMessage(paramMap));
                    }
                }
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
     * 借阅
     * @param archiveMainNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getTableHtmlAjax")
    @ResponseBody
    public Map<String, Object> getTableHtmlAjax(String archiveMainNo) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (archiveMainNo != null && !"".equals(archiveMainNo)) {
            try {
                ArchiveInfoBorrowDetail archiveInfoBorrowDetail = new ArchiveInfoBorrowDetail();
                archiveInfoBorrowDetail.setArchiveMainNo(archiveMainNo);
                archiveInfoBorrowDetail.setOptType(BizPubParm.ARCHIVE_OPT_TYPE_01);
                List<ArchiveInfoBorrowDetail> archiveInfoBorrowDetailInList = archiveInfoBorrowDetailFeign.getListByArchiveInfoBorrowDetail(archiveInfoBorrowDetail);
                JsonTableUtil jsonTableUtil = new JsonTableUtil();
                String tableHtmlIn = jsonTableUtil.getJsonStr("tablearchiveborrowdetailin", "tableTag", archiveInfoBorrowDetailInList, null, true);
                dataMap.put("tableHtmlIn", tableHtmlIn);

                archiveInfoBorrowDetail = new ArchiveInfoBorrowDetail();
                archiveInfoBorrowDetail.setArchiveMainNo(archiveMainNo);
                archiveInfoBorrowDetail.setOptType(BizPubParm.ARCHIVE_OPT_TYPE_02);
                List<ArchiveInfoBorrowDetail> archiveInfoBorrowDetailOutList = archiveInfoBorrowDetailFeign.getListByArchiveInfoBorrowDetail(archiveInfoBorrowDetail);
                String tableHtmlOut = jsonTableUtil.getJsonStr("tablearchiveborrowdetailout", "tableTag", archiveInfoBorrowDetailOutList, null, true);
                dataMap.put("tableHtmlOut", tableHtmlOut);
                dataMap.put("flag", "success");
            } catch (Exception e) {
                dataMap.put("flag", "error");
                dataMap.put("msg", "获取借阅借出文件出错！");
                e.printStackTrace();
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", "归档编号不能为空！");
        }
        return dataMap;
    }

    /**
     * AJAX更新保存
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjax")
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        try{
            FormService formService = new FormService();
            FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowbase");
            getFormValue(formmfarchiveborrowbase, getMapByJson(ajaxData));
            if(this.validateFormData(formmfarchiveborrowbase)){
                setObjValue(formmfarchiveborrowbase, archiveInfoBorrow);
                archiveInfoBorrowFeign.update(archiveInfoBorrow);
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "更新失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX获取查看
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String borrowId) throws Exception {
        ActionContext.initialize(request,response);
        Map<String,Object> formData = new HashMap<String,Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowbase");
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        archiveInfoBorrow.setBorrowId(borrowId);
        archiveInfoBorrow = archiveInfoBorrowFeign.getById(archiveInfoBorrow);
        getObjValue(formmfarchiveborrowbase, archiveInfoBorrow,formData);
        if(archiveInfoBorrow!=null){
            dataMap.put("flag", "success");
        }else{
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }
    /**
     * Ajax异步删除
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String borrowId) throws Exception{
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        archiveInfoBorrow.setBorrowId(borrowId);
        try {
            archiveInfoBorrowFeign.delete(archiveInfoBorrow);
            dataMap.put("flag", "success");
            dataMap.put("msg", "成功");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * 查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(String borrowId) throws Exception{
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        FormData formmfarchiveborrowdetail = formService.getFormData("mfarchiveborrowdetail");
        getFormValue(formmfarchiveborrowdetail);
        ArchiveInfoBorrow archiveInfoBorrow = new ArchiveInfoBorrow();
        archiveInfoBorrow.setBorrowId(borrowId);
        archiveInfoBorrow = archiveInfoBorrowFeign.getById(archiveInfoBorrow);
        getObjValue(formmfarchiveborrowdetail, archiveInfoBorrow);
        return "MfArchiveBorrow_Detail";
    }
}
