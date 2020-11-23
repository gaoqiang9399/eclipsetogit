package app.component.pact.stopintst.controller;

import app.base.User;
import app.component.calc.core.entity.MfRepayAmt;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppChild;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.stopintst.feign.MfBusStopIntstApplyFeign;
import app.component.pact.stopintst.entity.MfBusStopIntstApply;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import app.tech.oscache.CodeUtils;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mfBusStopIntstApply")
public class MfBusStopIntstApplyController extends BaseFormBean {
    @Autowired
    private MfBusStopIntstApplyFeign mfBusStopIntstApplyFeign;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;

    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;

    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;


    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request,response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray STOP_INTST = codeUtils.getJSONArrayByKeyName("STOP_INTST");
        this.getHttpRequest().setAttribute("STOP_INTST", STOP_INTST);
        model.addAttribute("query", "");
        return "component/pact/stopintst/MfBusStopIntstApply_List";
    }
    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusStopIntstApply mfBusStopIntstApply = new MfBusStopIntstApply();
        try {
            mfBusStopIntstApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfBusStopIntstApply.setCriteriaList(mfBusStopIntstApply, ajaxData);//我的筛选
            mfBusStopIntstApply.setCustomSorts(ajaxData);//自定义排序
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);//异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusStopIntstApply",mfBusStopIntstApply));
            //自定义查询Bo方法
            ipage = mfBusStopIntstApplyFeign.findByPage(ipage);
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
     *
     * 方法描述： 借据列表
     *
     * @return
     * @throws Exception
     *             String
     * @author zhs
     * @param pageSize
     * @param pageNo
     * @date 2017-8-15 上午9:24:53
     */
    @RequestMapping(value = "/getFincListAjax")
    @ResponseBody
    public Map<String, Object> getFincListAjax(String ajaxData, String cusNo,Integer pageSize, Integer pageNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setCustomQuery(ajaxData);
            mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
            mfBusFincApp.setCusNo(cusNo);
            mfBusFincApp.setFincSts("5");
            mfBusFincApp.setStopIntstFlag("0");
            ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
            ipage = mfBusFincAppFeign.findByPage(ipage);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }

    //合同选择组件查询
    @RequestMapping(value = "/getBusFincAppChildinfo")
    @ResponseBody
    public Map<String, Object> getBusFincAppChildinfo(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception{
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp=new MfBusFincApp();
        try {
            mfBusFincApp.setCustomQuery(ajaxData);//自定义查询参数赋值
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);//异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
            //自定义查询Bo方法
            ipage = mfBusStopIntstApplyFeign.getBusFincAppinfo(ipage);
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
    //停息新增页面
    @RequestMapping(value = "/insetintst")
    public String register(Model model) throws Exception {
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        FormData formmfarchiveborrowbase = formService.getFormData("stopintstinsert");
        MfBusStopIntstApply mfBusStopIntstApply = new MfBusStopIntstApply();
        mfBusStopIntstApply.setApplyDate(DateUtil.getDateTime());//获取系统当前时间
        getObjValue(formmfarchiveborrowbase, mfBusStopIntstApply);
        model.addAttribute("mfBusStopIntstApply",mfBusStopIntstApply);
        model.addAttribute("formstopintstinsert", formmfarchiveborrowbase);
        model.addAttribute("query", "");
        return "component/pact/stopintst/MfBusStopIntstApply_Insert";
    }

    //停息确认页面
    @RequestMapping(value = "/stopInstConfirm")
    public String stopInstConfirm(Model model,String id) throws Exception {
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        FormData formstopintstconfirm = formService.getFormData("stopintstconfirm");
        MfBusStopIntstApply mfBusStopIntstApply = new MfBusStopIntstApply();
        mfBusStopIntstApply.setStopId(id);
        mfBusStopIntstApply =  mfBusStopIntstApplyFeign.getById(mfBusStopIntstApply);
        getObjValue(formstopintstconfirm, mfBusStopIntstApply);
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("fincId",mfBusStopIntstApply.getFincId());
        List<MfRepayAmt> list =mfBusStopIntstApplyFeign.getCurTermYingShouAmtList(parmMap);
        MfRepayAmt mfRepayAmt = new MfRepayAmt();

        double benJin=0;
        double liXi=0;
        double yuQiLiXi=0;
        double fuLiLiXi=0;
        double faXi=0;
        double weiYueJin=0;
        double feiYong=0;
        for(int i =0 ;i < list.size();i++){
            if("2".equals(list.get(i).getHeJiFlag())) {
                benJin += Double.valueOf(list.get(i).getBenJin());
                liXi += Double.valueOf(list.get(i).getLiXi());
                yuQiLiXi += Double.valueOf(list.get(i).getYuQiLiXi());
                fuLiLiXi += Double.valueOf(list.get(i).getFuLiLiXi());
                faXi += Double.valueOf(list.get(i).getFaXi());
                weiYueJin += Double.valueOf(list.get(i).getWeiYueJin());
                feiYong += Double.valueOf(list.get(i).getFeiYong());
            }

        }
        this.changeFormProperty(formstopintstconfirm, "benJin", "initValue", String.valueOf(benJin));
        this.changeFormProperty(formstopintstconfirm, "liXi", "initValue",  String.valueOf(liXi));
        this.changeFormProperty(formstopintstconfirm, "yuQiLiXi", "initValue",  String.valueOf(yuQiLiXi));
        this.changeFormProperty(formstopintstconfirm, "fuLiLiXi", "initValue",  String.valueOf(fuLiLiXi));
        this.changeFormProperty(formstopintstconfirm, "faXi", "initValue",  String.valueOf(faXi));
        this.changeFormProperty(formstopintstconfirm, "weiYueJin", "initValue",  String.valueOf(weiYueJin));
        this.changeFormProperty(formstopintstconfirm, "total", "initValue",  String.valueOf(liXi+yuQiLiXi+fuLiLiXi+weiYueJin));
        //this.changeFormProperty(formstopintstconfirm, "feiYong", "initValue",  String.valueOf(feiYong));

        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusStopIntstApply.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        this.changeFormProperty(formstopintstconfirm, "cusMngName", "initValue",  mfCusCustomer.getCusMngName());
        model.addAttribute("mfBusStopIntstApply",mfBusStopIntstApply);
        model.addAttribute("formstopintstconfirm", formstopintstconfirm);
        model.addAttribute("query", "");
        return "component/pact/stopintst/MfBusStopIntstConfirm";
    }

    //停息确认页面
    @RequestMapping(value = "/showDetail")
    public String showDetail(Model model,String id) throws Exception {
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        FormData formstopintstconfirm = formService.getFormData("stopintstconfirm");
        MfBusStopIntstApply mfBusStopIntstApply = new MfBusStopIntstApply();
        mfBusStopIntstApply.setStopId(id);
        mfBusStopIntstApply =  mfBusStopIntstApplyFeign.getById(mfBusStopIntstApply);
        getObjValue(formstopintstconfirm, mfBusStopIntstApply);
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("fincId",mfBusStopIntstApply.getFincId());
        List<MfRepayAmt> list =mfBusStopIntstApplyFeign.getCurTermYingShouAmtList(parmMap);
        MfRepayAmt mfRepayAmt = new MfRepayAmt();

        double benJin=0;
        double liXi=0;
        double yuQiLiXi=0;
        double fuLiLiXi=0;
        double faXi=0;
        double weiYueJin=0;
        double feiYong=0;
        for(int i =0 ;i < list.size();i++){
            if("2".equals(list.get(i).getHeJiFlag())){
                benJin+=Double.valueOf(list.get(i).getBenJin());
                liXi+=Double.valueOf(list.get(i).getLiXi());
                yuQiLiXi+=Double.valueOf(list.get(i).getYuQiLiXi());
                fuLiLiXi+=Double.valueOf(list.get(i).getFuLiLiXi());
                faXi+=Double.valueOf(list.get(i).getFaXi());
                weiYueJin+=Double.valueOf(list.get(i).getWeiYueJin());
                feiYong+=Double.valueOf(list.get(i).getFeiYong());
            }


        }
        this.changeFormProperty(formstopintstconfirm, "benJin", "initValue", String.valueOf(benJin));
        this.changeFormProperty(formstopintstconfirm, "liXi", "initValue",  String.valueOf(liXi));
        this.changeFormProperty(formstopintstconfirm, "yuQiLiXi", "initValue",  String.valueOf(yuQiLiXi));
        this.changeFormProperty(formstopintstconfirm, "fuLiLiXi", "initValue",  String.valueOf(fuLiLiXi));
        this.changeFormProperty(formstopintstconfirm, "faXi", "initValue",  String.valueOf(faXi));
        this.changeFormProperty(formstopintstconfirm, "weiYueJin", "initValue",  String.valueOf(weiYueJin));
        this.changeFormProperty(formstopintstconfirm, "total", "initValue",  String.valueOf(liXi+yuQiLiXi+fuLiLiXi+weiYueJin));
        //this.changeFormProperty(formstopintstconfirm, "feiYong", "initValue",  String.valueOf(feiYong));

        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusStopIntstApply.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        this.changeFormProperty(formstopintstconfirm, "cusMngName", "initValue",  mfCusCustomer.getCusMngName());
        model.addAttribute("mfBusStopIntstApply",mfBusStopIntstApply);
        model.addAttribute("formstopintstconfirm", formstopintstconfirm);
        model.addAttribute("query", "");
        return "component/pact/stopintst/MfBusStopIntstDetail";
    }

    /**
     * AJAX申请新增
     * @return
     * @throws Exception archiveInfoMain
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formmfarchiveborrowbase = formService.getFormData("stopintstinsert");
            getFormValue(formmfarchiveborrowbase, getMapByJson(ajaxData));
            if(this.validateFormData(formmfarchiveborrowbase)) {
                MfBusStopIntstApply mfBusStopIntstApply = new MfBusStopIntstApply();
                mfBusStopIntstApply.setStopId(WaterIdUtil.getWaterId());
                setObjValue(formmfarchiveborrowbase, mfBusStopIntstApply);
                mfBusStopIntstApply.setStopSts("2");
                mfBusStopIntstApply.setShowflag("1");
                mfBusStopIntstApply.setLstModTime(DateUtil.getDateTime());
                mfBusStopIntstApply.setRegTime(DateUtil.getDateTime());
                mfBusStopIntstApply.setOpNo(User.getRegNo(request));
                mfBusStopIntstApply.setOpName(User.getRegName(request));
                mfBusStopIntstApply.setBrName(User.getOrgName(request));
                mfBusStopIntstApply.setBrNo(User.getOrgNo(request));
                mfBusStopIntstApply.setApplyName(User.getRegName(request));
                mfBusStopIntstApply.setApplyNo(User.getRegNo(request));

                /*停息结算总额*/
                Map<String, Object> parmMap = new HashMap<String, Object>();
                parmMap.put("fincId",mfBusStopIntstApply.getFincId());
                List<MfRepayAmt> list =mfBusStopIntstApplyFeign.getCurTermYingShouAmtList(parmMap);
                MfRepayAmt mfRepayAmt = new MfRepayAmt();

                double benJin=0;
                double liXi=0;
                double yuQiLiXi=0;
                double fuLiLiXi=0;
                double faXi=0;
                double weiYueJin=0;
                double feiYong=0;
                for(int i =0 ;i < list.size();i++){
                    if("2".equals(list.get(i).getHeJiFlag())) {
                        benJin += Double.valueOf(list.get(i).getBenJin());
                        liXi += Double.valueOf(list.get(i).getLiXi());
                        yuQiLiXi += Double.valueOf(list.get(i).getYuQiLiXi());
                        fuLiLiXi += Double.valueOf(list.get(i).getFuLiLiXi());
                        faXi += Double.valueOf(list.get(i).getFaXi());
                        weiYueJin += Double.valueOf(list.get(i).getWeiYueJin());
                        feiYong += Double.valueOf(list.get(i).getFeiYong());
                    }

                }
                mfBusStopIntstApply.setStopAmt(liXi+yuQiLiXi+fuLiLiXi+weiYueJin);

                mfBusStopIntstApply=mfBusStopIntstApplyFeign.submitProcess(mfBusStopIntstApply);
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("userRole", mfBusStopIntstApply.getApproveNodeName());
                paramMap.put("opNo", mfBusStopIntstApply.getApprovePartName());
                dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
                dataMap.put("flag", "success");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX停息
     * @return
     * @throws Exception archiveInfoMain
     */
    @RequestMapping(value = "/stopIntstAjax")
    @ResponseBody
    public Map<String, Object> stopIntstAjax(String ajaxData,String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formstopintstconfirm = formService.getFormData("stopintstconfirm");
            getFormValue(formstopintstconfirm, getMapByJson(ajaxData));
            if(this.validateFormData(formstopintstconfirm)) {
                MfBusStopIntstApply mfBusStopIntstApply = new MfBusStopIntstApply();
                setObjValue(formstopintstconfirm, mfBusStopIntstApply);
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("fincId",mfBusStopIntstApply.getFincId());
                paramMap.put("stopIntstDate",mfBusStopIntstApply.getStopPlanDate());
                Map<String,String> map = mfBusStopIntstApplyFeign.doMfBusFincAppStopIntst(paramMap);
                if("9999".equals(map.get("code").toString())){
                    dataMap.put("flag", "error");
                    dataMap.put("msg",map.get("msg").toString());
                }else{
                    dataMap.put("flag", "success");
                    dataMap.put("msg","停息成功");

                }

            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "停息失败");
            throw e;
        }
        return dataMap;
    }

    //显示停息审批页面
    @RequestMapping(value = "/getstopinstsApproval")
    public String getstopinstsApproval(Model model,String stopId,String taskId,String activityType,String hideOpinionType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(stopId, null);// 当前审批节点task
        FormData formprerepayapplyadd = formService.getFormData("stopintstapprove");
        MfBusStopIntstApply mfBusStopIntstApply = new MfBusStopIntstApply();
        mfBusStopIntstApply.setStopId(stopId);
        mfBusStopIntstApply = mfBusStopIntstApplyFeign.getById(mfBusStopIntstApply);
        getObjValue(formprerepayapplyadd, mfBusStopIntstApply);
        this.changeFormProperty(formprerepayapplyadd, "appTime", "initValue", mfBusStopIntstApply.getApplyDate());
        // 处理审批意见类型
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
        opinionTypeMap.put("processDefinitionId",taskAppro.getProcessDefinitionId());//流程id
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
        opinionTypeMap.put("nodeNo",nodeNo);
        List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
                taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(formprerepayapplyadd, "opinionType", "optionArray", opinionTypeList);
        model.addAttribute("stopId", stopId);
        model.addAttribute("formstopintstapprove", formprerepayapplyadd);
        model.addAttribute("mfBusStopIntstApply",mfBusStopIntstApply);
        model.addAttribute("taskId",taskId);
        model.addAttribute("activityType", activityType);
        model.addAttribute("query", "");
        return "component/pact/stopintst/MfBusStopInstApply_approve";
    }
    //提交审批
    @RequestMapping(value = "/submitUpdateAjax")
    @ResponseBody
    public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appNo, String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map map = getMapByJson(ajaxData);
        String formId = (String) map.get("formId");
        FormData formprerepayapplyadd = formService.getFormData(formId);
        getFormValue(formprerepayapplyadd, map);
        MfBusStopIntstApply mfBusStopIntstApply = new MfBusStopIntstApply();
        setObjValue(formprerepayapplyadd, mfBusStopIntstApply);
        Result res;
        try {
            dataMap = getMapByJson(ajaxData);
            dataMap.put("orgNo", User.getOrgNo(request));
            String opinionType = String.valueOf(dataMap.get("opinionType"));
            String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
            res = mfBusStopIntstApplyFeign.doCommit(taskId, appNo, opinionType, approvalOpinion, transition,
                    User.getRegNo(this.getHttpRequest()), nextUser, mfBusStopIntstApply);
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
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
            throw e;
        }
        return dataMap;
    }
}
