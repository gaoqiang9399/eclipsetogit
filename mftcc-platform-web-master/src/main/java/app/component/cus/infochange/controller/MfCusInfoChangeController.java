package app.component.cus.infochange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.cus.infochange.entity.MfCusInfoChangeChild;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.component.cus.infochange.entity.MfCusInfoChange;
import app.component.cus.infochange.entity.MfCusKeyInfoFields;
import app.component.cus.infochange.feign.MfCusInfoChangeFeign;
import app.component.cus.infochange.feign.MfCusKeyInfoFieldsFeign;
import app.component.nmd.entity.NmdArea;
import app.component.nmd.feign.NmdAreaFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

/**
 * 类名： MfCusInfoChangeController 描述：信息变更申请
 *
 * @author 仇招
 * @date 2018年5月29日 上午11:16:49
 */
@Controller
@RequestMapping("/mfCusInfoChange")
public class MfCusInfoChangeController extends BaseFormBean {
    @Autowired
    private MfCusInfoChangeFeign mfCusInfoChangeFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
    @Autowired
    private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private MfCusKeyInfoFieldsFeign mfCusKeyInfoFieldsFeign;
    @Autowired
    private NmdAreaFeign nmdAreaFeign;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getListPage")
    public String getListPage(Model model, String cusNo) throws Exception {
        model.addAttribute("cusNo", cusNo);
        return "/component/cus/infochange/MfCusInfoChange_List";
    }

    /***
     * 列表数据查询
     *
     * @param ajaxData
     * @param tableType
     * @param tableId
     * @param pageNo,Integer
     *            pageSize
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableType,
                                              String tableId, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
        mfCusInfoChange.setCusNo(cusNo);
        try {
            // mfCusInfoChange.setCustomQuery(ajaxData);// 自定义查询参数赋值
            // mfCusInfoChange.setCriteriaList(mfCusInfoChange, ajaxData);//
            // 我的筛选
            // mfCusInfoChange.setCustomSorts(ajaxData);// 自定义排序
            // this.getRoleConditions(mfCusInfoChange,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCusInfoChange", mfCusInfoChange));
            ipage = mfCusInfoChangeFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX新增
     *
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData forminfochangebase = formService.getFormData("infochangebase");
            getFormValue(forminfochangebase, getMapByJson(ajaxData));
            if (this.validateFormData(forminfochangebase)) {
                MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
                setObjValue(forminfochangebase, mfCusInfoChange);
                mfCusInfoChangeFeign.insert(mfCusInfoChange);
                mfCusInfoChange = mfCusInfoChangeFeign.submitProcess(mfCusInfoChange);
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("userRole", mfCusInfoChange.getApproveNodeName());
                paramMap.put("opNo", mfCusInfoChange.getApprovePartName());
                dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * ajax 异步 单个字段或多个字段更新
     *
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData forminfochangedetail = formService.getFormData("infochangedetail");
        getFormValue(forminfochangedetail, getMapByJson(ajaxData));
        MfCusInfoChange mfCusInfoChangeJsp = new MfCusInfoChange();
        setObjValue(forminfochangedetail, mfCusInfoChangeJsp);
        MfCusInfoChange mfCusInfoChange = mfCusInfoChangeFeign.getById(mfCusInfoChangeJsp);
        if (mfCusInfoChange != null) {
            try {
                mfCusInfoChange = (MfCusInfoChange) EntityUtil.reflectionSetVal(mfCusInfoChange, mfCusInfoChangeJsp,
                        getMapByJson(ajaxData));
                mfCusInfoChangeFeign.update(mfCusInfoChange);
                dataMap.put("flag", "success");
                dataMap.put("msg", "保存成功");
            } catch (Exception e) {
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
     * AJAX更新保存
     *
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
        try {
            FormData forminfochangebase = formService.getFormData("infochangebase");
            getFormValue(forminfochangebase, getMapByJson(ajaxData));
            if (this.validateFormData(forminfochangebase)) {
                mfCusInfoChange = new MfCusInfoChange();
                setObjValue(forminfochangebase, mfCusInfoChange);
                mfCusInfoChangeFeign.update(mfCusInfoChange);
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "更新失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @param changeId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String changeId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData forminfochangebase = formService.getFormData("infochangebase");
        MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
        mfCusInfoChange.setChangeId(changeId);
        mfCusInfoChange = mfCusInfoChangeFeign.getById(mfCusInfoChange);
        getObjValue(forminfochangebase, mfCusInfoChange, formData);
        if (mfCusInfoChange != null) {
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
     * @param changeId
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String changeId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
        mfCusInfoChange.setChangeId(changeId);
        try {
            mfCusInfoChangeFeign.delete(mfCusInfoChange);
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
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/input")
    public String input(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        Gson gson = new Gson();
        MfCusInfoChange mfCusInfoChange = gson.fromJson(ajaxData, MfCusInfoChange.class);
        mfCusCustomer.setCusNo(mfCusInfoChange.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        mfCusInfoChange.setCusName(mfCusCustomer.getCusName());
        mfCusInfoChange.setRegTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
        mfCusInfoChange.setOpNo(User.getRegNo(request));
        mfCusInfoChange.setOpName(User.getRegName(request));
        mfCusInfoChange.setChangeId(WaterIdUtil.getWaterId());
        FormData forminfochangebase = formService.getFormData("infochangebase");
        getObjValue(forminfochangebase, mfCusInfoChange);
        Map<String, Object> flagMap = getIfUpdateFlag(mfCusInfoChange);
        model.addAttribute("beforeValueShow", mfCusInfoChange.getBeforeTheChangeShow());
        model.addAttribute("aftervalueShow", mfCusInfoChange.getAfterTheChangeShow());
        model.addAttribute("changeFieldName", mfCusInfoChange.getChangeFieldName());
        model.addAttribute("fieldType", mfCusInfoChange.getFieldUpdateType());
        model.addAttribute("flagMap", flagMap);
        model.addAttribute("forminfochangebase", forminfochangebase);
        model.addAttribute("mfCusInfoChange", mfCusInfoChange);
        model.addAttribute("query", "");
        return "/component/cus/infochange/MfCusInfoChange_Insert";
    }

    /**
     * 方法描述： 获取显示值
     *
     * @return String
     * @author 仇招
     * @date 2018年5月30日 下午7:00:47
     */
    private String getShowValue(String value, MfCusKeyInfoFields mfCusKeyInfoFields) throws Exception {
        String fieldType = mfCusKeyInfoFields.getFieldType();
        if (fieldType != null) {
            switch (fieldType) {
                case "2"://字典项
                    CodeUtils cu = new CodeUtils();
                    Map<String, String> cuMap = cu.getMapByKeyName(mfCusKeyInfoFields.getSelectName());
                    value = cuMap.get(value);
                    break;
                case "3"://日期
                    if (!value.contains("-")) {
                        value = DateUtil.getShowDateTime(value);
                    }
                    break;
                case "4"://金额
                    if (!value.contains(",")) {
                        value = MathExtend.moneyStr(Double.valueOf(value));
                    }
                    break;
                case "5"://地址
                    NmdArea nmdArea = new NmdArea();
                    nmdArea.setAreaNo(value);
                    value = nmdAreaFeign.getAllLevById(nmdArea);
                    break;
                default:
                    break;
            }
        }
        return value;
    }

    /**
     * 方法描述： 获取是否允许表单提交标志
     *
     * @param mfCusInfoChange
     * @return
     * @throws Exception Map<String,Object>
     * @author 仇招
     * @date 2018年6月1日 下午5:50:42
     */
    private Map<String, Object> getIfUpdateFlag(MfCusInfoChange mfCusInfoChange) throws Exception {
        Map<String, Object> flagMap = new HashMap<String, Object>();
        flagMap.put("flag", BizPubParm.YES_NO_Y);
        // 判断修改前和后改后的值是否一致，如果一致则不让修改
        String beforeTheChange = mfCusInfoChange.getBeforeTheChange();
        if (beforeTheChange == null) {
            beforeTheChange = "";
        }
        String afterTheChange = mfCusInfoChange.getAfterTheChange();
        if (afterTheChange == null) {
            afterTheChange = "";
        }
        if (beforeTheChange.equals(afterTheChange)) {
            flagMap.put("flag", BizPubParm.YES_NO_N);
            flagMap.put("msg", "修改前和修改后的值相同，请重新核实！");
        }

        String ifHaveUnfinish = mfCusInfoChangeFeign.getIfHaveUnfinish(mfCusInfoChange);
        if (BizPubParm.YES_NO_Y.equals(ifHaveUnfinish)) {
            flagMap.put("flag", BizPubParm.YES_NO_N);
            flagMap.put("msg", "存在未提交或者正在审批的关于" + mfCusInfoChange.getChangeFieldName() + "的修改");
        }
        return flagMap;
    }

    /**
     * 查询
     *
     * @param changeId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(Model model, String changeId, String entryFlag, String ifEdit) throws Exception {
        ActionContext.initialize(request, response);
        String query = "query";
        FormService formService = new FormService();
        FormData forminfochangedetail = formService.getFormData("infochangedetail");
        getFormValue(forminfochangedetail);
        MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
        mfCusInfoChange.setChangeId(changeId);
        mfCusInfoChange = mfCusInfoChangeFeign.getById(mfCusInfoChange);
        mfCusInfoChange.setRegTime(DateUtil.getShowDateTime(mfCusInfoChange.getRegTime()));
        mfCusInfoChange.setLstModTime(DateUtil.getShowDateTime(mfCusInfoChange.getLstModTime()));
        if ((ifEdit != null && BizPubParm.YES_NO_Y.equals(ifEdit)) || BizPubParm.APP_STS_UN_SUBMIT.equals(mfCusInfoChange.getApplySts())) {
            request.setAttribute("ifBizManger", "3");
            query = "";
        }
        String afterTheChangeShow = mfCusInfoChange.getAfterTheChangeShow();
        String beforeTheChangeShow = mfCusInfoChange.getBeforeTheChangeShow();
        mfCusInfoChange.setAfterTheChangeShow(afterTheChangeShow);
        mfCusInfoChange.setBeforeTheChangeShow(beforeTheChangeShow);
        getObjValue(forminfochangedetail, mfCusInfoChange);
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        String htmlStrCorp = jsonFormUtil.getJsonStr(forminfochangedetail, "propertySeeTag", query);
        model.addAttribute("htmlStrCorp", htmlStrCorp);
        model.addAttribute("mfCusInfoChange", mfCusInfoChange);
        model.addAttribute("changeId", changeId);
        model.addAttribute("entryFlag", entryFlag);
        model.addAttribute("query", query);
        return "/component/cus/infochange/MfCusInfoChange_Detail";
    }


    /**
     * 新增校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void validateInsert() throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData forminfochangebase = formService.getFormData("infochangebase");
        getFormValue(forminfochangebase);
        boolean validateFlag = this.validateFormData(forminfochangebase);
    }

    /**
     * 修改校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void validateUpdate() throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData forminfochangebase = formService.getFormData("infochangebase");
        getFormValue(forminfochangebase);
        boolean validateFlag = this.validateFormData(forminfochangebase);
    }

    /**
     * 方法描述： 打开调薪调岗申请审批页面
     *
     * @return
     * @throws Exception String
     * @author 仇招
     * @date 2017-12-12下午20:09:27
     */
    @RequestMapping("/getViewPoint")
    public String getViewPoint(Model model, String changeId, String hideOpinionType, String taskId, String activityType)
            throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        request.setAttribute("ifBizManger", "3");
        MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
        mfCusInfoChange.setChangeId(changeId);
        mfCusInfoChange = mfCusInfoChangeFeign.getById(mfCusInfoChange);
        mfCusInfoChange.setApprovePartNo(null);
        mfCusInfoChange.setApprovePartName(null);
        mfCusInfoChange.setRegTime(DateUtil.getShowDateTime(mfCusInfoChange.getRegTime()));
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(changeId, null);// 当前审批节点task
        if ("supplement_data".equals(taskAppro.getName())) {
            model.addAttribute("ifEdit", BizPubParm.YES_NO_Y);
        } else {
            model.addAttribute("ifEdit", BizPubParm.YES_NO_N);
        }
        // 初始化基本信息表单、工作经历表单
        FormData formCusInfoChangeApprove = formService.getFormData("cusInfoChangeApprove");
        // 实体对象放到表单对象中
        getObjValue(formCusInfoChangeApprove, mfCusInfoChange);
        // 处理审批意见类型
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
        opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
        opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
        List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
                taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(formCusInfoChangeApprove, "opinionType", "optionArray", opinionTypeList);
        // 获得当前审批岗位前面审批过得岗位信息
        JSONArray befNodesjsonArray = new JSONArray();
        befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
        request.setAttribute("befNodesjsonArray", befNodesjsonArray);
        model.addAttribute("changeId", changeId);
        model.addAttribute("mfCusInfoChange", mfCusInfoChange);
        model.addAttribute("formCusInfoChangeApprove", formCusInfoChangeApprove);
        model.addAttribute("taskId", taskId);
        model.addAttribute("activityType", activityType);
        model.addAttribute("query", "");
        return "/component/cus/infochange/WkfCusInfoChangeViewPoint";
    }

    /**
     * 方法描述： 审批意见保存提交
     *
     * @return
     * @throws Exception String
     * @author 仇招
     * @date 2017-12-13 上午10:09:47
     */
    @RequestMapping("/submitUpdateAjax")
    @ResponseBody
    public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,
                                                String nextUser) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> formDataMap = getMapByJson(ajaxData);
        // 初始化基本信息表单、工作经历表单
        FormData formCusInfoChangeApprove = formService.getFormData("cusInfoChangeApprove");
        getFormValue(formCusInfoChangeApprove, formDataMap);
        MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
        setObjValue(formCusInfoChangeApprove, mfCusInfoChange);
        Result res;
        try {
            new FeignSpringFormEncoder().addParamsToBaseDomain(mfCusInfoChange);
            formDataMap.put("mfCusInfoChange", mfCusInfoChange);
            res = mfCusInfoChangeFeign.doCommit(taskId, transition, nextUser, formDataMap);
            dataMap = new HashMap<String, Object>();
            if (res.isSuccess()) {
                dataMap.put("flag", "success");
                if (res.isEndSts()) {
                    dataMap.put("msg", res.getMsg());
                } else {
                    dataMap.put("msg", res.getMsg());
                }
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
        }
        return dataMap;
    }

    /**
     * 方法描述： 提交审批流程
     *
     * @return
     * @throws Exception String
     * @author 仇招
     * @date 2017-12-13 上午10:09:47
     */
    @RequestMapping("/submitProcessAjax")
    @ResponseBody
    public Map<String, Object> submitProcessAjax(String changeId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
        mfCusInfoChange.setChangeId(changeId);
        mfCusInfoChange = mfCusInfoChangeFeign.getById(mfCusInfoChange);
        try {
            mfCusInfoChange = mfCusInfoChangeFeign.submitProcess(mfCusInfoChange);
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("userRole", mfCusInfoChange.getApproveNodeName());
            paramMap.put("opNo", mfCusInfoChange.getApprovePartName());
            dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
            dataMap.put("flag", "success");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
        }
        return dataMap;
    }

    /**
     * 方法描述： 展示客户业务列表信息
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019年8月6日 下午19:25:30
     */
    @RequestMapping(value = "/fieldUpdateList")
    @ResponseBody
    public Map<String, Object> fieldUpdateList(String changeId, String beforeValueShow, String aftervalueShow, String changeFieldName,String fieldType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String htmlStr = "";
        try {
            if (null != beforeValueShow && null != aftervalueShow) {
                JsonTableUtil jtu = new JsonTableUtil();
                List<MfCusInfoChangeChild> showList = new ArrayList<MfCusInfoChangeChild>();
                String beforeTheChange[] = beforeValueShow.split("~");//变更之前的值
                String afterTheChange[] = aftervalueShow.split("~");//变更之后的值
                String changeFieldNames[] = changeFieldName.split("~");//变更字段
                String fieldUpdateType[]=fieldType.split(",");//字段类型，处理地址编号不展示
                for (int i = 0; i < beforeTheChange.length; i++) {
                    if("5".equals(fieldUpdateType[i])){
                        continue;
                    }
                    MfCusInfoChangeChild mfCusInfoChangeChild = new MfCusInfoChangeChild();
                    mfCusInfoChangeChild.setBeforeTheChange(beforeTheChange[i]);
                    mfCusInfoChangeChild.setAfterTheChange(afterTheChange[i]);
                    mfCusInfoChangeChild.setChangeFieldName(changeFieldNames[i]);
                    showList.add(mfCusInfoChangeChild);
                }
                htmlStr = jtu.getJsonStr("tablechangeInfo0001", "tableTag", showList, null, true);
            } else {
                MfCusInfoChangeChild mfCusInfoChangeChild = new MfCusInfoChangeChild();
                mfCusInfoChangeChild.setChangeId(changeId);
                List<MfCusInfoChangeChild> fieldUpdateList = mfCusInfoChangeFeign.fieldUpdateList(mfCusInfoChangeChild);
                String tableFormId = "tablechangeInfo0001";
                JsonTableUtil jtu = new JsonTableUtil();
                htmlStr = jtu.getJsonStr(tableFormId, "tableTag", fieldUpdateList, null, true);
            }

            dataMap.put("htmlStr", htmlStr);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping("/ifApprovlRecordInfo")
    @ResponseBody
    public  Map<String, Object> ifApprovlRecordInfo(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            Gson gson = new Gson();
            MfCusInfoChange mfCusInfoChange = gson.fromJson(ajaxData, MfCusInfoChange.class);
            mfCusCustomer.setCusNo(mfCusInfoChange.getCusNo());
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
            mfCusInfoChange.setCusName(mfCusCustomer.getCusName());
            mfCusInfoChange.setRegTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
            mfCusInfoChange.setOpNo(User.getRegNo(request));
            mfCusInfoChange.setOpName(User.getRegName(request));
            mfCusInfoChange.setChangeId(WaterIdUtil.getWaterId());
            mfCusInfoChange.setApplySts("2");
            mfCusInfoChangeFeign.insert(mfCusInfoChange);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 查询客户是否有信息变更记录
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019年9月17日 下午09:37:30
     */
    @RequestMapping(value = "/getListByCusNoAjax")
    @ResponseBody
    public Map<String, Object> getListByCusNoAjax(String cusNo) throws Exception {
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
            mfCusInfoChange.setCusNo(cusNo);
            List<MfCusInfoChange> mfCusInfoChangeList =mfCusInfoChangeFeign.getMfCusInfoChangeList(mfCusInfoChange);
            if(mfCusInfoChangeList.size()>0 && mfCusInfoChangeList!=null){
                dataMap.put("flag", "success");
            }else{
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg","根据客户号获取信息变更记录失败！");
            throw e;
        }
        return dataMap;
    }


}
