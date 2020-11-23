package app.component.finance.manage.controller;

import app.base.User;
import app.component.calc.charge.feign.MfBusChargeFeeFeign;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.common.BizPubParm;
import app.component.common.DateUtil;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.component.cus.cusinvoicemation.feign.MfCusInvoiceMationFeign;
import app.component.finance.manage.entity.CwCollectConfim;
import app.component.finance.manage.feign.CwCollectConfimFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.guarantee.entity.MfGuaranteeRegistration;
import app.component.pact.guarantee.feign.MfGuaranteeRegistrationFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
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
@RequestMapping("/cwCollectConfim")
public class CwCollectConfimController  extends BaseFormBean {

    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private CwCollectConfimFeign cwCollectConfimFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfBusChargeFeeFeign mfBusChargeFeeFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private MfCusInvoiceMationFeign mfCusInvoiceMationFeign;
    @Autowired
    private MfGuaranteeRegistrationFeign mfGuaranteeRegistrationFeign;

    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model,String queryType) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        JSONArray statusJsonArray = new CodeUtils().getJSONArrayByKeyName("BILL_STS");
        this.getHttpRequest().setAttribute("statusJsonArray", statusJsonArray);
        String showName = "费用确认";
        if("2".equals(queryType) ){
            showName = "收费复核";
        }
        model.addAttribute("queryType",queryType);
        model.addAttribute("showName",showName);
        return "/component/finance/manage/CwCollectConfim_List";
    }

    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId,
                                                  String tableType, String ajaxData,String queryType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CwCollectConfim cwCollectConfim = new CwCollectConfim();
        try {
            cwCollectConfim.setCustomQuery(ajaxData);// 自定义查询参数赋值
            cwCollectConfim.setCustomSorts(ajaxData);// 自定义排序参数赋值
            cwCollectConfim.setCriteriaList(cwCollectConfim, ajaxData);// 我的筛选
//            cwCollectConfim.setStatus(BizPubParm.COLLECT_STATUS_1);//查询未到账确认的
            cwCollectConfim.setQueryType(queryType);//查询类型
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("cwCollectConfim", cwCollectConfim));
            ipage = cwCollectConfimFeign.findByPage(ipage);
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
     * 到账确认界面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String pactId, String id) throws Exception {
        CwCollectConfim cwCollectConfim = new CwCollectConfim();
        cwCollectConfim.setId(id);
        cwCollectConfim = cwCollectConfimFeign.getById(cwCollectConfim);
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        FormData formcwcollectconfimedit = null;
        //确定是小微还是工程担保
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setKindNo(mfBusPact.getKindNo());
        mfSysKind = mfSysKindFeign.getById(mfSysKind);
        if(BizPubParm.YES_NO_Y.equals(mfSysKind.getIfSmailCamll())){//是小微
            formcwcollectconfimedit = formService.getFormData("cwcollectconfimedit");
        }else if(BizPubParm.YES_NO_N.equals(mfSysKind.getIfSmailCamll())){//工程担保
            formcwcollectconfimedit = formService.getFormData("cwcollectconfimedit_engineer");
        }
        //获取缴款通知书的账号信息
        MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
        mfBusChargeFee.setChargeId(cwCollectConfim.getChargeId());
        mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
        //应收担保费和应收评审费
        cwCollectConfim.setReguaranteeAmt(mfBusChargeFee.getGuaranteeAmt());
        cwCollectConfim.setReassessAmt(mfBusChargeFee.getReviewAmt());
        //不含税担保费、不含税评审费
        cwCollectConfim.setGuaranteeAmtTax(mfBusChargeFee.getGuaranteeAmtTax());
        cwCollectConfim.setReviewAmtTax(mfBusChargeFee.getReviewAmtTax());
        //到账信息
        if(mfBusChargeFee.getActualReceivedAmt()!=null){
            cwCollectConfim.setActualReceivedAmt(mfBusChargeFee.getActualReceivedAmt());
        }
        if(StringUtil.isEmpty(cwCollectConfim.getReceviedDate())){
            cwCollectConfim.setReceviedDate(mfBusChargeFee.getReceviedDate());
        }
        if(StringUtil.isEmpty(cwCollectConfim.getRepayDate())){
            cwCollectConfim.setRepayDate(mfBusChargeFee.getRepayDate());
        }

        cwCollectConfim.setBankChargeAmt(mfBusChargeFee.getHandAmt());
        cwCollectConfim.setRedepositAmt(mfBusChargeFee.getBond());
        //到账账号信息
        cwCollectConfim.setReceivedAccId(mfBusChargeFee.getCollectAccId());
        cwCollectConfim.setReceviedAccout(mfBusChargeFee.getCollectAccount());
        cwCollectConfim.setReceviedAccoutName(mfBusChargeFee.getCollectAccName());
        cwCollectConfim.setReceviedBankName(mfBusChargeFee.getCollectBank());
        //如果实收金额大于应收金额，需要展示应退费金额
        if(mfBusChargeFee.getActualReceivedAmt()!=null&&mfBusChargeFee.getGuaranteeAmt()!=null&&mfBusChargeFee.getReviewAmt()!=null){
            if(mfBusChargeFee.getActualReceivedAmt() > MathExtend.add(mfBusChargeFee.getGuaranteeAmt(), mfBusChargeFee.getReviewAmt())){
                cwCollectConfim.setRefundAmt(MathExtend.subtract(mfBusChargeFee.getActualReceivedAmt(), MathExtend.add(mfBusChargeFee.getGuaranteeAmt(), mfBusChargeFee.getReviewAmt())));
            }
        }
        //发票类型
        cwCollectConfim.setInvoiceType(mfBusChargeFee.getInvoiceType());
        //纳税人识别号信息
        MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
        mfCusInvoiceMation.setId(mfBusChargeFee.getPayTaxesId());
        mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
        if(mfCusInvoiceMation != null){
            cwCollectConfim.setTaxpayerId(mfBusChargeFee.getPayTaxesId());
            cwCollectConfim.setTaxpayerNo(mfCusInvoiceMation.getTaxpayerNo());
            cwCollectConfim.setAddress(mfCusInvoiceMation.getAddress());
            cwCollectConfim.setTel(mfCusInvoiceMation.getTel());
            cwCollectConfim.setBankName(mfCusInvoiceMation.getBankName());
            cwCollectConfim.setAccountNumber(mfCusInvoiceMation.getAccountNumber());
        }
        //是否免税
        cwCollectConfim.setIfDutyFree(mfBusChargeFee.getTaxFlag());
        cwCollectConfim.setGuaranteeAmtFee(mfBusChargeFee.getGuaranteeAmtFee());//本次收费对应的担保金额
        cwCollectConfim.setGreenChannel(mfBusChargeFee.getGreenChannel());//绿色通道编号
        cwCollectConfim.setEconType(mfBusChargeFee.getEconType());//企业划型
        cwCollectConfim.setAccountAmt(mfBusChargeFee.getAccountAmt());
        MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
        mfGuaranteeRegistration.setAppId(mfBusChargeFee.getAppId());
        mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
        if(mfGuaranteeRegistration != null){
            cwCollectConfim.setGuaFee(mfGuaranteeRegistration.getGuaFee());
            cwCollectConfim.setHandFee(mfGuaranteeRegistration.getHandFee());
            cwCollectConfim.setGuaRemark(mfGuaranteeRegistration.getGuaRemark());
        }
        getObjValue(formcwcollectconfimedit, cwCollectConfim);
        String scNo = BizPubParm.WKF_NODE.fee.getScenceTypeDoc();// 要件场景
        String nodeNo = BizPubParm.WKF_NODE.charge_fee.getNodeNo();// 功能节点编号
        model.addAttribute("appId", mfBusChargeFee.getAppId());
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("busModel", mfBusPact.getBusModel());
        model.addAttribute("chargeId", mfBusChargeFee.getChargeId());
        model.addAttribute("feeChargeType", mfBusChargeFee.getFeeChargeType());
        model.addAttribute("formcwcollectconfimedit", formcwcollectconfimedit);
        model.addAttribute("cusNo",mfBusPact.getCusNo());
        model.addAttribute("cwCollectConfim", cwCollectConfim);
        model.addAttribute("query", "");
        model.addAttribute("collectType", cwCollectConfim.getCollectType());
        return "/component/finance/manage/CwCollectConfim_Insert";
    }

    /**
     * 新增
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
        CwCollectConfim cwCollectConfim = new CwCollectConfim();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcwcollectconfimedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcwcollectconfimedit, getMapByJson(ajaxData));
            if (this.validateFormData(formcwcollectconfimedit)) {
                setObjValue(formcwcollectconfimedit, cwCollectConfim);
                cwCollectConfim.setOpNo(User.getRegNo(request));
                cwCollectConfim.setOpName(User.getRegName(request));
                cwCollectConfim.setBrNo(User.getOrgNo(request));
                cwCollectConfim.setBrName(User.getOrgName(request));
                dataMap.put("cwCollectConfim", cwCollectConfim);
                cwCollectConfim = cwCollectConfimFeign.insert(dataMap);
                dataMap.put("flag", "success");
                dataMap.put("msg", "操作成功！");
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

    /**
     * 到账确认界面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDetailPage")
    public String getDetailPage(Model model, String pactId, String id) throws Exception {
        CwCollectConfim cwCollectConfim = new CwCollectConfim();
        cwCollectConfim.setId(id);
        cwCollectConfim = cwCollectConfimFeign.getById(cwCollectConfim);
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        String busModel = mfBusPact.getBusModel();
        FormData formcwcollectconfimdetail = null;
        //确定是小微还是工程担保
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setKindNo(mfBusPact.getKindNo());
        mfSysKind = mfSysKindFeign.getById(mfSysKind);
        if(BizPubParm.YES_NO_Y.equals(mfSysKind.getIfSmailCamll())){//是小微
            formcwcollectconfimdetail = formService.getFormData("cwcollectconfimdetail");
        }else if(BizPubParm.YES_NO_N.equals(mfSysKind.getIfSmailCamll())){//工程担保
            formcwcollectconfimdetail = formService.getFormData("cwcollectconfimedit_engineer");
        }
        //获取缴款通知书的账号信息
        MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
        mfBusChargeFee.setChargeId(cwCollectConfim.getChargeId());
        mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
        //应收担保费和应收评审费
        cwCollectConfim.setReguaranteeAmt(mfBusChargeFee.getGuaranteeAmt());
        cwCollectConfim.setReassessAmt(mfBusChargeFee.getReviewAmt());
        cwCollectConfim.setBankChargeAmt(mfBusChargeFee.getHandAmt());
        //不含税担保费、不含税评审费
        cwCollectConfim.setGuaranteeAmtTax(mfBusChargeFee.getGuaranteeAmtTax());
        cwCollectConfim.setReviewAmtTax(mfBusChargeFee.getReviewAmtTax());
        cwCollectConfim.setBankChargeAmtTax(mfBusChargeFee.getHandAmtTax());
        cwCollectConfim.setRedepositAmt(mfBusChargeFee.getBond());
        cwCollectConfim.setAccountAmt(mfBusChargeFee.getAccountAmt());
        //到账信息
        if(cwCollectConfim.getActualReceivedAmt()!=null){
            cwCollectConfim.setActualReceivedAmt(mfBusChargeFee.getActualReceivedAmt());
        }
        cwCollectConfim.setReceviedDate(mfBusChargeFee.getRepayDate());
        //到账账号信息
        cwCollectConfim.setReceivedAccId(mfBusChargeFee.getCollectAccId());
        cwCollectConfim.setReceviedAccout(mfBusChargeFee.getCollectAccount());
        cwCollectConfim.setReceviedAccoutName(mfBusChargeFee.getCollectAccName());
        cwCollectConfim.setReceviedBankName(mfBusChargeFee.getCollectBank());
        if(!BizPubParm.BUS_MODEL_12.equals(busModel) ){
            //如果实收金额大于应收金额，需要展示应退费金额
            if(mfBusChargeFee.getActualReceivedAmt() != null){
                if(mfBusChargeFee.getActualReceivedAmt() > MathExtend.add(mfBusChargeFee.getGuaranteeAmt(), mfBusChargeFee.getReviewAmt())){
                    cwCollectConfim.setRefundAmt(MathExtend.subtract(mfBusChargeFee.getActualReceivedAmt(), MathExtend.add(mfBusChargeFee.getGuaranteeAmt(), mfBusChargeFee.getReviewAmt())));
                }
            }
        }
        //纳税人识别号信息
        MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
        mfCusInvoiceMation.setId(mfBusChargeFee.getPayTaxesId());
        mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
        if(mfCusInvoiceMation != null){
            cwCollectConfim.setTaxpayerId(mfBusChargeFee.getPayTaxesId());
            cwCollectConfim.setTaxpayerNo(mfCusInvoiceMation.getTaxpayerNo());
            cwCollectConfim.setAddress(mfCusInvoiceMation.getAddress());
            cwCollectConfim.setTel(mfCusInvoiceMation.getTel());
            cwCollectConfim.setBankName(mfCusInvoiceMation.getBankName());
            cwCollectConfim.setAccountNumber(mfCusInvoiceMation.getAccountNumber());
        }
        //是否免税
        cwCollectConfim.setGuaranteeAmtFee(mfBusChargeFee.getGuaranteeAmtFee());//本次收费对应的担保金额
        cwCollectConfim.setGreenChannel(mfBusChargeFee.getGreenChannel());//绿色通道编号
        cwCollectConfim.setEconType(mfBusChargeFee.getEconType());//企业划型
        MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
        mfGuaranteeRegistration.setAppId(mfBusChargeFee.getAppId());
        mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
        if(mfGuaranteeRegistration != null){
            cwCollectConfim.setGuaFee(mfGuaranteeRegistration.getGuaFee());
            cwCollectConfim.setHandFee(mfGuaranteeRegistration.getHandFee());
            cwCollectConfim.setGuaRemark(mfGuaranteeRegistration.getGuaRemark());
        }
        getObjValue(formcwcollectconfimdetail, cwCollectConfim);
        String scNo = BizPubParm.WKF_NODE.fee.getScenceTypeDoc();// 要件场景
        String nodeNo = BizPubParm.WKF_NODE.charge_fee.getNodeNo();// 功能节点编号
        model.addAttribute("appId", mfBusChargeFee.getAppId());
        model.addAttribute("feeChargeType", mfBusChargeFee.getFeeChargeType());
        model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("chargeSts", mfBusChargeFee.getAppSts());
        model.addAttribute("formcwcollectconfimdetail", formcwcollectconfimdetail);
        model.addAttribute("cusNo",mfBusPact.getCusNo());
        model.addAttribute("cwCollectConfim", cwCollectConfim);
        model.addAttribute("query", "");
        model.addAttribute("collectType", cwCollectConfim.getCollectType());
        return "/component/finance/manage/CwCollectConfim_Details";
    }

    /**
     * 到账确认
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/collectConfimAjax")
    @ResponseBody
    public Map <String, Object> collectConfimAjax(String ajaxData,String busModel) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        CwCollectConfim cwCollectConfim = new CwCollectConfim();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcwcollectconfimedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcwcollectconfimedit, getMapByJson(ajaxData));
            if (this.validateFormData(formcwcollectconfimedit)) {
                setObjValue(formcwcollectconfimedit, cwCollectConfim);
                if(!BizPubParm.BUS_MODEL_12.equals(busModel)){
                    //实际到账金额>=应收总金额，否则系统进行拦截，不能保存
                    Double sumAmt = MathExtend.add(cwCollectConfim.getReguaranteeAmt(), cwCollectConfim.getReassessAmt()) ;
                    if(cwCollectConfim.getActualReceivedAmt() < sumAmt){
                        dataMap.put("flag", "error");
                        dataMap.put("msg", "实际到账金额不能小于应收总金额！");
                        return dataMap;
                    }
                }
//                else{
//                    //实际到账金额>=应收总金额，否则系统进行拦截，不能保存
//                    Double sumAmt = MathExtend.add(MathExtend.add(cwCollectConfim.getReguaranteeAmt(), cwCollectConfim.getRedepositAmt()),cwCollectConfim.getBankChargeAmt()) ;
//                    if(cwCollectConfim.getActualReceivedAmt() < sumAmt){
//                        dataMap.put("flag", "error");
//                        dataMap.put("msg", "实际到账金额不能小于应收总金额！");
//                        return dataMap;
//                    }
//                }
                cwCollectConfim.setOpNo(User.getRegNo(request));
                cwCollectConfim.setOpName(User.getRegName(request));
                cwCollectConfim.setBrNo(User.getOrgNo(request));
                cwCollectConfim.setBrName(User.getOrgName(request));
                dataMap = cwCollectConfimFeign.collectConfim(cwCollectConfim);
                //生成缴款书
                mfBusChargeFeeFeign.doChargeReplaceToPdf(cwCollectConfim.getChargeId());
//                dataMap.put("flag", "success");
//                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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

    /**
     * 发回重审
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/rollbackAjax")
    @ResponseBody
    public Map <String, Object> rollbackAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        CwCollectConfim cwCollectConfim = new CwCollectConfim();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcwcollectconfimedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcwcollectconfimedit, getMapByJson(ajaxData));
            if (this.validateFormData(formcwcollectconfimedit)) {
                setObjValue(formcwcollectconfimedit, cwCollectConfim);
                cwCollectConfim.setOpNo(User.getRegNo(request));
                cwCollectConfim.setOpName(User.getRegName(request));
                cwCollectConfim.setBrNo(User.getOrgNo(request));
                cwCollectConfim.setBrName(User.getOrgName(request));
                dataMap = cwCollectConfimFeign.rallback(cwCollectConfim);
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

    @RequestMapping(value = "/getBatchConfimListPage")
    public String getBatchConfimListPage(Model model,String ids,String queryType,String showName) throws Exception {
        ActionContext.initialize(request, response);
        CwCollectConfim cwCollectConfim = null;
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        List<CwCollectConfim> cwCollectConfimList = new ArrayList<CwCollectConfim>();
        if(null !=ids && !"".equals(ids)){
            String[] idArr = ids.split(",");
            for(int i=0;i<idArr.length;i++){
                cwCollectConfim = new CwCollectConfim();
                cwCollectConfim.setId(idArr[i]);
                cwCollectConfim = cwCollectConfimFeign.getById(cwCollectConfim);
                if(StringUtil.isNotEmpty(cwCollectConfim.getCollectTime())){
                    cwCollectConfim.setCollectTime(cn.mftcc.util.DateUtil.getShowDateTime(cwCollectConfim.getCollectTime()));
                }
                if(cwCollectConfim != null){
                    cwCollectConfimList.add(cwCollectConfim);
                }
            }
        }
        model.addAttribute("cwCollectConfimList", cwCollectConfimList);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableId = "tablecwcollectbatchlist";
        if("2".equals(queryType)){
            tableId = "tablecwcollectbatchlist_GCDB";
        }
        String tableHtml = jtu.getJsonStr(tableId, "tableTag", cwCollectConfimList, null, true);
        model.addAttribute("tableHtml", tableHtml);
        model.addAttribute("queryType", queryType);
        model.addAttribute("showName", showName);
        return "/component/finance/manage/CwCollectConfim_ListBatch";
    }

    /**
     * 批量到账确认
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchConfim")
    @ResponseBody
    public Map <String, Object> batchConfim(String ids, String actualAmts,String collectTimes,String approvalRemarks) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            CwCollectConfim cwCollectConfim = new CwCollectConfim();
            cwCollectConfim.setIds(ids);
            cwCollectConfim.setActualAmts(actualAmts);
            cwCollectConfim.setCollectTimes(collectTimes);
            cwCollectConfim.setApprovalRemarks(approvalRemarks);
            //20200622 增加审核人 add by chenyingying
            cwCollectConfim.setOpName(User.getRegName(request));
            dataMap = cwCollectConfimFeign.batchConfim(cwCollectConfim);

            String[] idArr = ids.split(",");
            for(int i=0;i<idArr.length;i++){
                CwCollectConfim confim = new CwCollectConfim();
                confim.setId(idArr[i]);
                confim = cwCollectConfimFeign.getById(confim);
                if(confim!=null&& StringUtil.isNotEmpty(confim.getChargeId())){
                    //生成缴款书
                    mfBusChargeFeeFeign.doChargeReplaceToPdf(confim.getChargeId());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
}
