package app.component.entrustedpayment.controller;

import app.base.User;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusBankAccManageFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.entrustedpayment.entity.MfBusEntrustedPayment;
import app.component.entrustedpayment.feign.MfBusEntrustedPaymentFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
@RequestMapping("/mfBusEntrustedPayment")
public class MfBusEntrustedPaymentController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfBusEntrustedPaymentFeign mfBusEntrustedPaymentFeign;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;
    @Autowired
    private MfCusBankAccManageFeign mfCusBankAccManageFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;

    @RequestMapping("/getListPage")
    public String getListPage(){
        ActionContext.initialize(request, response);
        return "/component/mfBusEntrustedPayment/MfBusEntrustedPayment_List";
    }


    //受托支付管理是以到贷后的借据为主(里面的自定义筛选之所以可以这样写是在做的时候，有效的受托登记与借据是一一对应的(后期考虑整改))
    @RequestMapping("/getListPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                              String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        try {
            if(ajaxData.indexOf("appState")>0||ajaxData.indexOf("payState")>0){//判断是否有状态筛选,如果是，依据登记表作为主表查询，否则依据借据表作为主表查询。
                    MfBusEntrustedPayment mfBusEntrustedPayment = new MfBusEntrustedPayment();
                    mfBusEntrustedPayment.setCustomQuery(ajaxData);
                    mfBusEntrustedPayment.setCriteriaList(mfBusEntrustedPayment,ajaxData);
                Ipage ipage = this.getIpage();
                ipage.setPageNo(pageNo);
                ipage.setPageSize(pageSize);// 异步传页面翻页参数
                // 自定义查询Feign方法
                ipage.setParams(this.setIpageParams("mfBusEntrustedPayment", mfBusEntrustedPayment));
                ipage.setResult(mfBusEntrustedPayment);
                ipage = mfBusEntrustedPaymentFeign.findByPage(ipage);

                for (int i = 0; i < ((List<Map<String, Object>>) ipage.getResult()).size(); i++) {//遍历查询出的数据，查询借据表填充字段
                    Map<String, Object> map = ((List<Map<String, Object>>) ipage.getResult()).get(i);
                    MfBusFincApp mfBusFincApp = new MfBusFincApp();
                    mfBusFincApp.setFincId(map.get("fincId").toString());
                    mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
                    String collectAcc = mfBusFincApp.getCollectAccId();
                    if (collectAcc != null && collectAcc!="") {
                        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
                        mfCusBankAccManage.setId(collectAcc);
                        mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
                        map.put("collectAccount", mfCusBankAccManage.getAccountNo());
                        map.put("collectAccName", mfCusBankAccManage.getAccountName());
                        map.put("collectBank", mfCusBankAccManage.getBank());
                    }
                    map.put("epRegTime",map.get("regTime").toString());
                    map.put("appName",mfBusFincApp.getAppName());
                    map.put("cusName",mfBusFincApp.getCusName());
                    map.put("fincShowId",mfBusFincApp.getFincShowId());
                    map.put("putoutAmtReal",mfBusFincApp.getPutoutAmtReal());
                    map.put("transferFlag",mfBusFincApp.getTransferFlag());
                    ((List<Map<String, Object>>) ipage.getResult()).set(i, map);
                }

                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
                ipage.setResult(tableHtml);
                dataMap.put("ipage", ipage);
            }else {
                MfBusFincApp mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
                mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
                mfBusFincApp.setPayMethod("2");
                mfBusFincApp.setFincSts("5");
                // this.getRoleConditions(appEval,"1000000001");//记录级权限控制方法
                Ipage ipage = this.getIpage();
                ipage.setPageNo(pageNo);
                ipage.setPageSize(pageSize);// 异步传页面翻页参数
                // 自定义查询Feign方法
                ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
                ipage = mfBusFincAppFeign.findByPage(ipage);
                for (int i = 0; i < ((List<Map<String, Object>>) ipage.getResult()).size(); i++) {//遍历借据表数据，查询登记表填充字段
                    Map<String, Object> map = ((List<Map<String, Object>>) ipage.getResult()).get(i);
                    if (map.get("collectAccId") != null&&!"".equals(map.get("collectAccId").toString())) {
                        String collectAcc = map.get("collectAccId").toString();
                        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
                        mfCusBankAccManage.setId(collectAcc);
                        mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
                        map.put("collectAccount", mfCusBankAccManage.getAccountNo());
                        map.put("collectAccName", mfCusBankAccManage.getAccountName());
                        map.put("collectBank", mfCusBankAccManage.getBank());
                    }
                    MfBusEntrustedPayment mfBusEntrustedPayment = new MfBusEntrustedPayment();
                    mfBusEntrustedPayment.setFincId(map.get("fincId").toString());
                    mfBusEntrustedPayment = mfBusEntrustedPaymentFeign.getByFincId(mfBusEntrustedPayment);
                    if (mfBusEntrustedPayment != null) {
                        map.put("epAmt", mfBusEntrustedPayment.getEpAmt());
                        map.put("epRegTime",mfBusEntrustedPayment.getRegTime());
                        //因为考虑到要查看受托登记的详情 故在此需要把 有效的受托登记id传到列表上
                        map.put("epId",mfBusEntrustedPayment.getEpId());
                        map.put("appState",mfBusEntrustedPayment.getAppState());//控制打印按钮（审批通过的可以打印）

                    }else{
                        map.put("appState","3");//未登记时,用3标识,代替null
                    }
                    ((List<Map<String, Object>>) ipage.getResult()).set(i, map);
                }
                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
                ipage.setResult(tableHtml);
                dataMap.put("ipage", ipage);
            }
        }catch (Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/epRegister")
    public String epRegister(Model model, String fincId) throws Exception{
        ActionContext.initialize(request, response);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        FormService formService = new FormService();
        FormData entrustedpayment0002 = formService.getFormData("entrustedpayment0002");
        try {
            mfBusFincApp.setFincId(fincId);
            mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(mfBusFincApp.getCusNo());
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
            mfBusFincApp.setIdNum(mfCusCustomer.getIdNum());
            //借款账户
            MfCusBankAccManage merchantBank = new MfCusBankAccManage();
            merchantBank.setId(mfBusFincApp.getBankAccId());
            MfCusBankAccManage mfCusBankAccManage = mfCusBankAccManageFeign.getById(merchantBank);
            mfBusFincApp.setIncomAccountName(mfCusBankAccManage.getAccountName());
            mfBusFincApp.setIncomBank(mfCusBankAccManage.getBank());

            //收款账户
            merchantBank = new MfCusBankAccManage();
            if(!"".equals(mfBusFincApp.getCollectAccId())){
                merchantBank.setId(mfBusFincApp.getCollectAccId());
                mfCusBankAccManage = mfCusBankAccManageFeign.getById(merchantBank);
                mfBusFincApp.setCollectAccount(mfCusBankAccManage.getAccountNo());
                mfBusFincApp.setCollectAccName(mfCusBankAccManage.getAccountName());
                mfBusFincApp.setCollectBank(mfCusBankAccManage.getBank());
            }
           /* //收款账户
            MfCusBankAccManage cusBank = new MfCusBankAccManage();
            cusBank.setCusNo(mfBusFincApp.getCusNo());
            cusBank.setUseFlag("1");
            cusBank.setUseType("6");
            cusBank.setDelFlag("0");
            cusBank.setCompanyId(mfBusFincApp.getKindNo());
            List<MfCusBankAccManage> cusBankList = cusInterfaceFeign.getMfCusBankAccListByCusNo(cusBank);
            if (cusBankList != null && cusBankList.size() > 0) {
                MfCusBankAccManage mfcbam = cusBankList.get(0);
                mfBusFincApp.setCollectAccId(mfcbam.getAccountNo());
                mfBusFincApp.setCollectAccName(mfcbam.getAccountName());
                mfBusFincApp.setCollectBank(mfcbam.getBank());
            }*/
            getObjValue(entrustedpayment0002, mfBusFincApp);
            MfBusEntrustedPayment mfBusEntrustedPayment  = new MfBusEntrustedPayment();
            mfBusEntrustedPayment.setFincId(fincId);
            mfBusEntrustedPayment = mfBusEntrustedPaymentFeign.getByFincId(mfBusEntrustedPayment);
            if(null!=mfBusEntrustedPayment && !"3".equals(mfBusEntrustedPayment.getAppState())){
                model.addAttribute("flag","error");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("fincId",mfBusFincApp.getFincId());
        model.addAttribute("cusNo",mfBusFincApp.getCusNo());
        model.addAttribute("appId",mfBusFincApp.getAppId());
        model.addAttribute("pactId",mfBusFincApp.getPactId());
        model.addAttribute("formentrustedpayment0002",entrustedpayment0002);
        model.addAttribute("nodeNo","entrusted_payment");
        model.addAttribute("query", "");
        return "/component/mfBusEntrustedPayment/MfBusEntrustedPayment_epRegister";
    }
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String,Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<>();
        FormService formService = new FormService();
        try {
            FormData entrustedpayment0002 = formService.getFormData("entrustedpayment0002");
            Map<String, Object> ajaxMap = getMapByJson(ajaxData);
            getFormValue(entrustedpayment0002, ajaxMap);
            if (this.validateFormData(entrustedpayment0002)) {
                MfBusEntrustedPayment mfBusEntrustedPayment = new MfBusEntrustedPayment();
                setObjValue(entrustedpayment0002, mfBusEntrustedPayment);

                MfBusEntrustedPayment mfbep = mfBusEntrustedPaymentFeign.getByFincId(mfBusEntrustedPayment);
                String msg = "";

                if (null == mfbep) {
                    //此时该借据没有有效的数据
                    mfBusEntrustedPayment.setIsUse("0");
                    ajaxMap.put("mfBusEntrustedPayment", mfBusEntrustedPayment);
                    msg = mfBusEntrustedPaymentFeign.insertAndStartProcess(ajaxMap);
                    dataMap.put("flag", "success");
                } else if ("3".equals(mfbep.getAppState())){
                    //此时的存在受托登记的有效数据，先把已存在的数据修改为无效
                    mfbep.setIsUse("1");
                    mfBusEntrustedPaymentFeign.update(mfbep);
                    mfBusEntrustedPayment.setIsUse("0");
                    ajaxMap.put("mfBusEntrustedPayment", mfBusEntrustedPayment);
                    msg = mfBusEntrustedPaymentFeign.insertAndStartProcess(ajaxMap);
                    dataMap.put("flag", "success");

                }

                else {
                    dataMap.put("flag", "error");
                    msg = "数据已存在,请勿重复提交!";
                }

                dataMap.put("msg", msg);
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            e.printStackTrace();
        }
        return dataMap;
    }

    @RequestMapping(value = "/epDetil")
    public String epDetil(Model model, String fincId) throws Exception{
        ActionContext.initialize(request, response);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        FormService formService = new FormService();
        FormData entrustedpayment0001 = formService.getFormData("entrustedpayment0001");
        try {
            mfBusFincApp.setFincId(fincId);
            mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(mfBusFincApp.getCusNo());
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
            mfBusFincApp.setIdNum(mfCusCustomer.getIdNum());
            //借款账户
            MfCusBankAccManage merchantBank = new MfCusBankAccManage();
            merchantBank.setId(mfBusFincApp.getBankAccId());
            MfCusBankAccManage mfCusBankAccManage = mfCusBankAccManageFeign.getById(merchantBank);
            mfBusFincApp.setIncomAccountName(mfCusBankAccManage.getAccountName());
            mfBusFincApp.setIncomBank(mfCusBankAccManage.getBank());

            //收款账户
            merchantBank = new MfCusBankAccManage();
            merchantBank.setId(mfBusFincApp.getCollectAccId());
            mfCusBankAccManage = mfCusBankAccManageFeign.getById(merchantBank);
            mfBusFincApp.setCollectAccount(mfCusBankAccManage.getAccountNo());
            mfBusFincApp.setCollectAccName(mfCusBankAccManage.getAccountName());
            mfBusFincApp.setCollectBank(mfCusBankAccManage.getBank());

           /* //收款账户
            MfCusBankAccManage cusBank = new MfCusBankAccManage();
            cusBank.setCusNo(mfBusFincApp.getCusNo());
            cusBank.setUseFlag("1");
            cusBank.setUseType("6");
            cusBank.setDelFlag("0");
            cusBank.setCompanyId(mfBusFincApp.getKindNo());
            List<MfCusBankAccManage> cusBankList = cusInterfaceFeign.getMfCusBankAccListByCusNo(cusBank);
            if (cusBankList != null && cusBankList.size() > 0) {
                MfCusBankAccManage mfcbam = cusBankList.get(0);
                mfBusFincApp.setCollectAccId(mfcbam.getAccountNo());
                mfBusFincApp.setCollectAccName(mfcbam.getAccountName());
                mfBusFincApp.setCollectBank(mfcbam.getBank());
            }*/
           MfBusEntrustedPayment mfBusEntrustedPayment = new MfBusEntrustedPayment();
           mfBusEntrustedPayment.setFincId(fincId);
           mfBusEntrustedPayment = mfBusEntrustedPaymentFeign.getByFincId(mfBusEntrustedPayment);
           getObjValue(entrustedpayment0001, mfBusFincApp);
           getObjValue(entrustedpayment0001,mfBusEntrustedPayment);
            model.addAttribute("mfBusEntrustedPayment",mfBusEntrustedPayment);
            model.addAttribute("appState",mfBusEntrustedPayment.getAppState());
            model.addAttribute("epId",mfBusEntrustedPayment.getEpId());
        } catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("fincId",mfBusFincApp.getFincId());
        model.addAttribute("cusNo",mfBusFincApp.getCusNo());
        model.addAttribute("appId",mfBusFincApp.getAppId());
        model.addAttribute("pactId",mfBusFincApp.getPactId());
        model.addAttribute("formentrustedpayment0001",entrustedpayment0001);
        model.addAttribute("nodeNo","entrusted_payment");
        model.addAttribute("query", "");
        return "/component/mfBusEntrustedPayment/MfBusEntrustedPayment_epDetail";
    }
    @RequestMapping(value = "/getViewAppr")
    public String getViewAppr(Model model,String epId,String fincId,String taskId){
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusEntrustedPayment mfBusEntrustedPayment = new MfBusEntrustedPayment();
        try {
            mfBusEntrustedPayment.setEpId(epId);
            mfBusEntrustedPayment = mfBusEntrustedPaymentFeign.getById(mfBusEntrustedPayment);
            TaskImpl taskAppro = wkfInterfaceFeign.getTask(epId, null);
            String nodeId = taskAppro.getActivityName();
            dataMap.put("nodeId", nodeId);
            JSONArray userJsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", mfBusEntrustedPayment.getOpNo());
            jsonObject.put("name", mfBusEntrustedPayment.getOpName());
            userJsonArray.add(jsonObject);
            model.addAttribute("ajaxData", userJsonArray.toString());
            FormData formentrustedpayment0003 = new FormService().getFormData("entrustedpayment0003");

            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(mfBusEntrustedPayment.getFincId());
            mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

            //此页面是受托登记的审批页面，在此处不应该审核借据里面的账号信息
            //借款账户
            MfCusBankAccManage merchantBank = new MfCusBankAccManage();
            merchantBank.setId(mfBusEntrustedPayment.getBankAccId());
            MfCusBankAccManage mfCusBankAccManage = mfCusBankAccManageFeign.getById(merchantBank);
            mfBusFincApp.setIncomAccountName(mfCusBankAccManage.getAccountName());
            mfBusFincApp.setIncomBank(mfCusBankAccManage.getBank());

            //收款账户
            merchantBank = new MfCusBankAccManage();
            merchantBank.setId(mfBusEntrustedPayment.getCollectAccId());
            mfCusBankAccManage = mfCusBankAccManageFeign.getById(merchantBank);
            mfBusFincApp.setCollectAccount(mfCusBankAccManage.getAccountNo());
            mfBusFincApp.setCollectAccName(mfCusBankAccManage.getAccountName());
            mfBusFincApp.setCollectBank(mfCusBankAccManage.getBank());

            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(mfBusFincApp.getCusNo());
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
            getObjValue(formentrustedpayment0003,mfCusCustomer);
            getObjValue(formentrustedpayment0003,mfBusFincApp);
            getObjValue(formentrustedpayment0003, mfBusEntrustedPayment);
            // 处理审批意见类型
            String activityType = taskAppro.getActivityType();
            Map<String, String> opinionTypeMap = new HashMap<String, String>();
            opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
            List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
                    taskAppro.getCouldRollback(), opinionTypeMap);
            this.changeFormProperty(formentrustedpayment0003, "opinionType", "optionArray", opinionTypeList);
            String nodeNo = "entrusted_payment";// 功能节点编号
            model.addAttribute("nodeNo", nodeNo);
            model.addAttribute("mfBusEntrustedPayment",mfBusEntrustedPayment);
            model.addAttribute("formentrustedpayment0003",formentrustedpayment0003);
            model.addAttribute("taskId",taskAppro.getId());
            model.addAttribute("pactId",mfBusEntrustedPayment.getPactId());
            model.addAttribute("fincId",mfBusEntrustedPayment.getFincId());
            model.addAttribute("epId",epId);
            model.addAttribute("appState",mfBusEntrustedPayment.getAppState());
            model.addAttribute("appId", mfBusEntrustedPayment.getAppId());
            model.addAttribute("cusNo", mfBusEntrustedPayment.getCusNo());
            model.addAttribute("appNo", mfBusEntrustedPayment.getEpId());
            model.addAttribute("query", "");
        }catch (Exception e){
            e.printStackTrace();
        }

        return "/component/mfBusEntrustedPayment/MfBusEntrustedPayment_getAppr";
    }


    @RequestMapping(value = "/approveSubmitAjax")
    @ResponseBody
    public Map<String, Object> approveSubmitAjax(String ajaxData, String taskId,String opinionType,String approvalOpinion,
                                                String transition, String nextUser,String appId,String appNo,String epId) throws Exception{
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<>();
        if(ajaxData!=null) {
            dataMap = getMapByJson(ajaxData);
        }
        dataMap.put("opinionType",opinionType);
        dataMap.put("approvalOpinion",approvalOpinion);
        dataMap.put("orgNo", User.getOrgNo(request));
        dataMap.put("opNo", User.getRegNo(request));
        dataMap.put("opName", User.getRegName(request));
        Result res;
        try{
            res = mfBusEntrustedPaymentFeign.commitProcess(epId,taskId,appNo, appId, transition!=null?transition:"", nextUser!=null?nextUser:"", dataMap);
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
        }catch(Exception e){
            e.printStackTrace();
        }
        return dataMap;
    }

    @RequestMapping(value = "/getParams")
    @ResponseBody
    public Map<String, Object> getParams(String epId) throws Exception{
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<>();

        try{
            MfBusEntrustedPayment mfBusEntrustedPayment = new MfBusEntrustedPayment();
            mfBusEntrustedPayment.setEpId(epId);
            mfBusEntrustedPayment = mfBusEntrustedPaymentFeign.getById(mfBusEntrustedPayment);
            dataMap.put("appId",mfBusEntrustedPayment.getAppId());
            dataMap.put("appNo",mfBusEntrustedPayment.getEpId());
            dataMap.put("cusNo",mfBusEntrustedPayment.getCusNo());
            dataMap.put("pactId",mfBusEntrustedPayment.getPactId());
            dataMap.put("fincId",mfBusEntrustedPayment.getFincId());
            dataMap.put("flag","success");
            dataMap.put("msg","");
        }catch(Exception e){
            dataMap.put("flag","error");
            dataMap.put("msg","参数获取错误,请稍候重试或联系管理员！");
            e.printStackTrace();
        }
        return dataMap;
    }

    @ResponseBody
    @RequestMapping("/updatePayState")
    public Map<String, Object> updatePayState(String epId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<>();
        try {
            MfBusEntrustedPayment mfBusEntrustedPayment = new MfBusEntrustedPayment();
            mfBusEntrustedPayment.setEpId(epId);
            mfBusEntrustedPayment.setPayState("1");
            mfBusEntrustedPaymentFeign.update(mfBusEntrustedPayment);
            mfBusEntrustedPayment = mfBusEntrustedPaymentFeign.getById(mfBusEntrustedPayment);
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(mfBusEntrustedPayment.getFincId());
            mfBusFincApp.setTransferFlag("1");
            mfBusFincAppFeign.update(mfBusFincApp);
            dataMap.put("flag","success");
            dataMap.put("msg","操作完成!");
        }catch(Exception e){
            dataMap.put("flag","error");
            dataMap.put("msg","错误，请联系管理员!");
            e.printStackTrace();
        }
        return dataMap;
    }
}
