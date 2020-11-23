package app.component.pact.feerefund.controller;

import app.base.User;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.dissolution.entity.MfBusDissolutionGuarantee;
import app.component.dissolution.feign.MfBusDissolutionGuaranteeFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feerefund.entity.MfBusFeeRefund;
import app.component.pact.feerefund.feign.MfBusFeeRefundFeign;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusPactChangeController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 14 14:38:18 CST 2018
 **/
@Controller
@RequestMapping(value ="/mfBusFeeRefund")
public class MfBusFeeRefundController extends BaseFormBean{

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfBusFeeRefundFeign mfBusFeeRefundFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private MfBusDissolutionGuaranteeFeign mfBusDissolutionGuaranteeFeign;
    @Autowired
    private MfBusAppFeeFeign mfBusAppFeeFeign;
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/pact/feerefund/MfBusFeeRefund_List";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                              String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFeeRefund mfBusFeeRefund = new MfBusFeeRefund();
        try {
            mfBusFeeRefund.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfBusFeeRefund.setCriteriaList(mfBusFeeRefund, ajaxData);//我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusFeeRefund",mfBusFeeRefund));
            ipage = mfBusFeeRefundFeign.findByPage(ipage);
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
     * 新增页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model) throws Exception{
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        String formId = "feeRefundInit";
        FormData formfeeRefundBase = formService.getFormData(formId);
        model.addAttribute("formfeeRefundBase",formfeeRefundBase);
        model.addAttribute("query","");
        return "/component/pact/feerefund/MfBusFeeRefund_Insert";
    }

    /**
     * AJAX新增
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map map = getMapByJson(ajaxData);
            FormData formfeeRefundBase = formService.getFormData((String)map.get("formId"));
            getFormValue(formfeeRefundBase, map);
            if (this.validateFormData(formfeeRefundBase)) {
                MfBusFeeRefund MfBusFeeRefund = new MfBusFeeRefund();
                setObjValue(formfeeRefundBase, MfBusFeeRefund);
                dataMap = mfBusFeeRefundFeign.insert(MfBusFeeRefund);
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    @ResponseBody
    @RequestMapping(value = "/getFeeRefundApply")
    public Map<String,Object> getFeeRefundApply(String pactId) throws  Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        MfBusFeeRefund mfBusFeeRefund = new MfBusFeeRefund();
        MfBusPact mfBusPact = new MfBusPact();
        MfBusFincApp mfBusFincApp = null;
        MfBusAppFee mfBusAppFee = null;
        try{
            mfBusPact.setPactId(pactId);
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(mfBusPact.getCusNo());
            mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
            String formId = prdctInterfaceFeign.getFormId(BizPubParm.fee_refund_type, BizPubParm.WKF_NODE.fee_refund_apply, null, null, User.getRegNo(request));
            FormData formfeeRefundBase = formService.getFormData(formId);
            PropertyUtils.copyProperties(mfBusFeeRefund, mfBusPact);
            mfBusFeeRefund.setFeeId(WaterIdUtil.getWaterId());
            //解保日期
            MfBusDissolutionGuarantee mfBusDissolutionGuarantee = new MfBusDissolutionGuarantee();
            mfBusDissolutionGuarantee.setPactId(mfBusPact.getPactId());
            mfBusDissolutionGuarantee = mfBusDissolutionGuaranteeFeign.getDisGuarantee(mfBusDissolutionGuarantee);
            if(mfBusDissolutionGuarantee != null && "2".equals(mfBusDissolutionGuarantee.getDissolutionType())){
                mfBusFeeRefund.setUnprotectDate(mfBusDissolutionGuarantee.getDissolutionDate());
                mfBusFeeRefund.setDissAmt(mfBusDissolutionGuarantee.getAmt());
            }else{
                mfBusFeeRefund.setDissAmt(0d);
            }
            //计算应退费用
            Map<String,Object> parmMap = new HashMap<>();
            parmMap.put("mfBusPact", mfBusPact);
            parmMap.put("date", mfBusFeeRefund.getUnprotectDate());
//            Double appAmtTmp = mfBusFeeRefundFeign.calcAppAmt(parmMap);
//            mfBusFeeRefund.setAppAmtTmp(appAmtTmp);
            mfBusFeeRefund.setAssureAmt(mfBusPact.getPactAmt());
            mfBusFeeRefund.setFeeAmt(mfBusPact.getActualGuaranteeAmt());
            mfBusFeeRefund.setRemark("");
            mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setPactId(mfBusPact.getPactId());
            List<MfBusFincApp> fincList = mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
            if(fincList != null && fincList.size()>0){
                mfBusFincApp = fincList.get(0);
                String termMonth;
                int [] term  = DateUtil.getMonthsAndDays(mfBusFincApp.getIntstBeginDate(), mfBusFincApp.getIntstEndDate());
                if(term[0]>0){
                    termMonth = term[0]+"月";
                    if(term[1]>0){
                        termMonth += term[1]+"天";
                    }
                }else{
                    termMonth = term[1]+"天";
                }
                int refundTerm = 0;
                if(MathExtend.comparison(DateUtil.getDate(), mfBusPact.getEndDate()) != 1){
                    int [] rs = DateUtil.getMonthsAndDays(mfBusFincApp.getIntstBeginDate(), DateUtil.getDate());
                    if(rs[1]>0){
                        refundTerm = rs[0]+1;
                    }else{
                        refundTerm = rs[0];
                    }
                }else{
                    //暂时不处理超过合同期限的担保费
                    refundTerm = mfBusPact.getTerm();
                }
                refundTerm = mfBusPact.getTerm()-refundTerm;
                if(refundTerm<0){
                    refundTerm = 0;
                }
                mfBusFeeRefund.setRefundTerm(refundTerm);
                mfBusFeeRefund.setPutoutAmt(mfBusFincApp.getPutoutAmtReal());
                mfBusFeeRefund.setInstsBeginDate(mfBusFincApp.getIntstBeginDate());
                mfBusFeeRefund.setInstsEndDate(mfBusFincApp.getIntstEndDate());
                mfBusFeeRefund.setTermMonth(termMonth);
            }
            mfBusAppFee = new MfBusAppFee();
            mfBusAppFee.setAppId(mfBusPact.getAppId());
            mfBusAppFee.setItemNo("1");
            List<MfBusAppFee> feeList = mfBusAppFeeFeign.getList(mfBusAppFee);
            if(feeList != null && feeList.size()>0){
                mfBusAppFee = feeList.get(0);
                mfBusFeeRefund.setFeeRate(mfBusAppFee.getRateScale());
            }

            Map<String,Object> rsMap = mfBusFincAppFeign.getFeeCollectInfo(mfBusPact,DateUtil.getDate());
            if(rsMap != null){
                mfBusFeeRefund.setReguaranteeAmt((Double) rsMap.get("reguaranteeAmt"));
                mfBusFeeRefund.setGuaranteeAmt((Double) rsMap.get("guaranteeAmt"));
            }
            getObjValue(formfeeRefundBase, mfCusCustomer);
            getObjValue(formfeeRefundBase, mfBusPact);
            getObjValue(formfeeRefundBase, mfBusFeeRefund);
            MfCusCorpBaseInfo mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(mfBusPact.getCusNo());
            if(mfCusCorpBaseInfo != null){
                this.changeFormProperty(formfeeRefundBase, "taxpayerIdentityNumber", "initValue", mfCusCorpBaseInfo.getTaxpayerIdentityNumber());
            }

            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String htmlStr = jsonFormUtil.getJsonStr(formfeeRefundBase,"bootstarpTag","");
            dataMap.put("flag","success");
            dataMap.put("htmlStr",htmlStr);
            dataMap.put("pactId",mfBusFeeRefund.getPactId());
            dataMap.put("appId",mfBusFeeRefund.getAppId());
            dataMap.put("cusNo",mfBusFeeRefund.getCusNo());
            dataMap.put("relNo",mfBusFeeRefund.getFeeId());
            dataMap.put("nodeNo",BizPubParm.fee_refund_type);
        }catch (Exception e){
            dataMap.put("flag","error");
        }
        return dataMap;
    }

    @RequestMapping("/submitUpdateAjax")
    @ResponseBody
    public Map<String, Object> submitUpdateAjax(String ajaxData, String feeId, String ajaxDataList, String taskId,
                                                String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFeeRefund mfBusFeeRefund = new MfBusFeeRefund();

        try {
            dataMap = getMapByJson(ajaxData);
            String formId = (String) dataMap.get("formId");
            FormData formfeeRefundBase = formService.getFormData(formId);
            getFormValue(formfeeRefundBase, dataMap);
            setObjValue(formfeeRefundBase, mfBusFeeRefund);
            String opinionType = String.valueOf(dataMap.get("opinionType"));
            String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
            new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusFeeRefund);
            dataMap.put("mfBusFeeRefund", mfBusFeeRefund);
            Result res = mfBusFeeRefundFeign.doCommit(taskId, feeId, opinionType, approvalOpinion, transition,
                    User.getRegNo(request), nextUser, dataMap);
            if (res!=null && res.isSuccess()) {
                dataMap.put("flag", "success");
                dataMap.put("msg", res.getMsg());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", res.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
            throw e;
        }
        return dataMap;

    }


    @ResponseBody
    @RequestMapping(value = "/getPactList")
    public Map<String, Object> getPactList(int pageNo,String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusPact",mfBusPact));
            ipage = mfBusFeeRefundFeign.getPactList(ipage);
            ipage.setParamsStr(ajaxData);
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
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String feeId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        MfBusFeeRefund mfBusFeeRefund = new MfBusFeeRefund();
        MfBusPact mfBusPact = new MfBusPact();
        try{
            mfBusFeeRefund.setFeeId(feeId);
            mfBusFeeRefund = mfBusFeeRefundFeign.getById(mfBusFeeRefund);
            mfBusPact.setPactId(mfBusFeeRefund.getPactId());
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(mfBusPact.getCusNo());
            FormData formfeeRefundDetail = formService.getFormData("feeRefundDetail");
            MfCusCorpBaseInfo mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(mfBusPact.getCusNo());
            if(mfCusCorpBaseInfo != null){
                this.changeFormProperty(formfeeRefundDetail, "taxpayerIdentityNumber", "initValue", mfCusCorpBaseInfo.getTaxpayerIdentityNumber());
            }
            getObjValue(formfeeRefundDetail, mfCusCustomer);
            getObjValue(formfeeRefundDetail, mfBusPact);
            getObjValue(formfeeRefundDetail, mfBusFeeRefund);

            model.addAttribute("formfeeRefundDetail", formfeeRefundDetail);
            model.addAttribute("query", "");
            model.addAttribute("relNo", mfBusFeeRefund.getFeeId());
            model.addAttribute("nodeNo", BizPubParm.fee_refund_type);
        }catch (Exception e){
           throw e;
        }
        return "/component/pact/feerefund/MfBusFeeRefund_Detail";
    }
}
