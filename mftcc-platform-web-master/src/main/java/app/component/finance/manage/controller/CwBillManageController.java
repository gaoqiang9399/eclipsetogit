package app.component.finance.manage.controller;

import app.base.User;
import app.component.calc.charge.feign.MfBusChargeFeeFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.common.BizPubParm;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.component.cus.cusinvoicemation.feign.MfCusInvoiceMationFeign;
import app.component.finance.manage.entity.CwBillManage;
import app.component.finance.manage.entity.CwCollectConfim;
import app.component.finance.manage.feign.CwBillManageFeign;
import app.component.finance.manage.feign.CwCollectConfimFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cwBillManage")
public class CwBillManageController extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private CwBillManageFeign cwBillManageFeign;
    @Autowired
    private CwCollectConfimFeign cwCollectConfimFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private MfBusChargeFeeFeign mfBusChargeFeeFeign;
    @Autowired
    private MfCusInvoiceMationFeign mfCusInvoiceMationFeign;

    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        JSONArray statusJsonArray = new CodeUtils().getJSONArrayByKeyName("BILL_STS");
        this.getHttpRequest().setAttribute("statusJsonArray", statusJsonArray);
        JSONArray typeJsonArray = new CodeUtils().getJSONArrayByKeyName("ZINVOICE_TYPE");
        this.getHttpRequest().setAttribute("typeJsonArray", typeJsonArray);
        return "/component/finance/manage/CwBillManage_List";
    }

    @RequestMapping(value = "/findPactListByAjax")
    @ResponseBody
    public Map<String, Object> findPactListByAjax(Integer pageNo, Integer pageSize, String tableId,
                                                  String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CwBillManage cwBillManage = new CwBillManage();
        try {
            cwBillManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
            cwBillManage.setCustomSorts(ajaxData);// 自定义排序参数赋值
            cwBillManage.setCriteriaList(cwBillManage, ajaxData);// 我的筛选
//            cwCollectConfim.setStatus(BizPubParm.COLLECT_STATUS_2);//查询已到账确认的
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("cwBillManage", cwBillManage));
            ipage = cwBillManageFeign.findPactListByAjax(ipage);
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
     * 费用开票界面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String pactId, String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        CwBillManage cwBillManage = new CwBillManage();
        cwBillManage.setId(id);
        cwBillManage = cwBillManageFeign.getById(cwBillManage);
        FormData formcwbillmanageedit = null;
        //获取缴款通知书的账号信息
        CwCollectConfim cwCollectConfim = new CwCollectConfim();
        cwCollectConfim.setId(cwBillManage.getCollectId());
        cwCollectConfim = cwCollectConfimFeign.getById(cwCollectConfim);
        MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
        mfBusChargeFee.setChargeId(cwCollectConfim.getChargeId());
        mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
        //确定是小微还是工程担保
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setKindNo(cwBillManage.getKindNo());
        mfSysKind = mfSysKindFeign.getById(mfSysKind);
        if(BizPubParm.YES_NO_Y.equals(mfSysKind.getIfSmailCamll())){//是小微
            formcwbillmanageedit = formService.getFormData("cwbillmanageedit");
            cwBillManage.setActualReceivedAmt(MathExtend.add(cwBillManage.getReguaranteeAmt(), cwBillManage.getReassessAmt()));
            cwBillManage.setReviewAmtTax(mfBusChargeFee.getReviewAmtTax());//不含税评审费
        }else if(BizPubParm.YES_NO_N.equals(mfSysKind.getIfSmailCamll())){//工程担保
            formcwbillmanageedit = formService.getFormData("cwbillmanageedit_GCDB");
            cwBillManage.setActualReceivedAmt(MathExtend.add(cwBillManage.getReguaranteeAmt(), cwBillManage.getBankChargeAmt()));
            cwBillManage.setBankChargeAmtTax(mfBusChargeFee.getHandAmtTax());
        }
        //不含税担保费
        cwBillManage.setGuaranteeAmtTax(mfBusChargeFee.getGuaranteeAmtTax());
        //纳税人识别号反显赋值
        if(cwBillManage.getTaxpayerId() != null && !"".equals(cwBillManage.getTaxpayerId())){
            MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
            mfCusInvoiceMation.setId(cwBillManage.getTaxpayerId());
            mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
            cwBillManage.setTaxpayerNum(mfCusInvoiceMation.getTaxpayerNo());
            cwBillManage.setAddress(mfCusInvoiceMation.getAddress());
            cwBillManage.setTelphone(mfCusInvoiceMation.getTel());
            cwBillManage.setBankAccount(mfCusInvoiceMation.getBankName());
            cwBillManage.setAccount(mfCusInvoiceMation.getAccountNumber());
            cwBillManage.setBillCusName(mfCusInvoiceMation.getCusName());
        }
        cwBillManage.setBillRemark(mfBusChargeFee.getBillRemark());
        getObjValue(formcwbillmanageedit, cwBillManage);
        model.addAttribute("formcwbillmanageedit", formcwbillmanageedit);
        model.addAttribute("cusNo",cwBillManage.getCusNo());
        model.addAttribute("query", "");
        return "/component/finance/manage/CwBillManage_Insert";
    }

    /**
     * 费用开票
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
        CwBillManage cwBillManage = new CwBillManage();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcwbillmanageedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcwbillmanageedit, getMapByJson(ajaxData));
            if (this.validateFormData(formcwbillmanageedit)) {
                setObjValue(formcwbillmanageedit, cwBillManage);
                cwBillManage.setOpNo(User.getRegNo(request));
                cwBillManage.setOpName(User.getRegName(request));
                cwBillManage.setBrNo(User.getOrgNo(request));
                cwBillManage.setBrName(User.getOrgName(request));
                dataMap = cwBillManageFeign.insert(cwBillManage);
                if(!"0".equals(cwBillManage.getBillCode())){
                    dataMap.put("flag", "error");
                    if(cwBillManage.getBillMsg() != null ){
                        dataMap.put("msg", cwBillManage.getBillMsg());
                    }else{
                        dataMap.put("msg", "费用开票失败！");
                    }
                }else{
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                }
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
     * 修改
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doInvoiceAjax")
    @ResponseBody
    public Map <String, Object> doInvoiceAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        CwBillManage cwBillManage = new CwBillManage();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcwbillmanageedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcwbillmanageedit, getMapByJson(ajaxData));
            if (this.validateFormData(formcwbillmanageedit)) {
                setObjValue(formcwbillmanageedit, cwBillManage);
                dataMap = cwBillManageFeign.doBill(cwBillManage);
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
     * 发送信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendMessage")
    @ResponseBody
    public Map <String, Object> sendMessage(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        CwBillManage cwBillManage = new CwBillManage();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcwbillmanageedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcwbillmanageedit, getMapByJson(ajaxData));
            if (this.validateFormData(formcwbillmanageedit)) {
                setObjValue(formcwbillmanageedit, cwBillManage);

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

    /**
     * 批量开票
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchDelivery")
    @ResponseBody
    public Map <String, Object> batchDelivery(String ids) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        CwBillManage cwBillManage = new CwBillManage();
        try {
            cwBillManage.setOpNo(User.getRegNo(request));
            cwBillManage.setOpName(User.getRegName(request));
            cwBillManage.setBrNo(User.getOrgNo(request));
            cwBillManage.setBrName(User.getOrgName(request));
            cwBillManage.setIds(ids);
            dataMap = cwBillManageFeign.batchDelivery(cwBillManage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    @RequestMapping(value = "/getBillManageAccountByPactId")
    @ResponseBody
    public Map<String, Object> getBillManageAccountByPactId(int pageNo,String pactId) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CwBillManage cwBillManage = new CwBillManage();
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            Map<String,Object> paramMap = new HashMap<String,Object>();
            cwBillManage.setPactId(pactId);
            cwBillManage.setBillSts("3");
            paramMap.put("cwBillManage", cwBillManage);
            ipage.setParams(this.setIpageParams("paramMap", paramMap));
            ipage = cwBillManageFeign.getBillManageAccountByPactId(ipage);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
}
