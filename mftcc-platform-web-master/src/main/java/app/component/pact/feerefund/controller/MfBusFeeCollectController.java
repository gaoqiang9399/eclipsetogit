package app.component.pact.feerefund.controller;

import app.component.calc.charge.feign.MfBusChargeFeeFeign;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.common.BizPubParm;
import app.component.finance.manage.entity.CwCollectConfim;
import app.component.finance.manage.feign.CwCollectConfimFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
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
@RequestMapping(value ="/mfBusFeeCollect")
public class MfBusFeeCollectController extends BaseFormBean{

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private CwCollectConfimFeign cwCollectConfimFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;
    @Autowired
    private MfBusChargeFeeFeign mfBusChargeFeeFeign;

    /**
     * 新增初始化页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model) throws Exception{
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        String formId = "feeRefundInit";
        FormData formfeeCollectBase = formService.getFormData(formId);
        model.addAttribute("formfeeCollectBase",formfeeCollectBase);
        model.addAttribute("query","");
        return "/component/pact/feerefund/MfBusFeeCollect_Insert";
    }

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/pact/feerefund/MfBusFeeCollect_List";
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
        MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
        try {
            mfBusChargeFee.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfBusChargeFee.setCriteriaList(mfBusChargeFee, ajaxData);//我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);
            //自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusChargeFee",mfBusChargeFee));
            ipage = mfBusChargeFeeFeign.getFeeCollectList(ipage);
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
            ipage = mfBusPactFeign.getFeeCollectSource(ipage);
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

    @ResponseBody
    @RequestMapping(value = "/getFeeCollectApply")
    public Map<String,Object> getFeeCollectApply(String pactId) throws  Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        CwCollectConfim cwcollectConfim = new CwCollectConfim();
        MfBusPact mfBusPact = new MfBusPact();
        try{
            mfBusPact.setPactId(pactId);
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            FormData formfeeCollectBase = formService.getFormData("feecollectInput");
            PropertyUtils.copyProperties(cwcollectConfim, mfBusPact);
            Map<String,Object> map = mfBusFincAppFeign.getFeeCollectInfo(mfBusPact,DateUtil.getDate());
            getObjValue(formfeeCollectBase, cwcollectConfim);
            this.changeFormProperty(formfeeCollectBase, "actualGuaranteeAmt", "initValue", "0.00");
            this.changeFormProperty(formfeeCollectBase, "actualAssessAmt", "initValue", "0.00");
            this.changeFormProperty(formfeeCollectBase, "actualRedepositAmt", "initValue", "0.00");
            this.changeFormProperty(formfeeCollectBase, "actualBankChargeAmt", "initValue", "0.00");
            this.changeFormProperty(formfeeCollectBase, "actualBankDepositAmt", "initValue", "0.00");
            this.changeFormProperty(formfeeCollectBase, "remark", "initValue", "");
            this.changeFormProperty(formfeeCollectBase, "pactTerm", "initValue", String.valueOf(mfBusPact.getTerm()));
            this.changeFormProperty(formfeeCollectBase, "reguaranteeAmt", "initValue",String.valueOf(map.get("guaranteeAmt")));
            this.changeFormProperty(formfeeCollectBase, "guaranteeAmt", "initValue", String.valueOf(map.get("guaranteeAmt")));

            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String htmlStr = jsonFormUtil.getJsonStr(formfeeCollectBase,"bootstarpTag","");
            dataMap.put("flag","success");
            dataMap.put("htmlStr",htmlStr);
            dataMap.put("pactId",mfBusPact.getPactId());
            dataMap.put("resMap",map);
        }catch (Exception e){
            dataMap.put("flag","error");
        }
        return dataMap;
    }

    /**
     * AJAX新增
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/calcReguaranteeAmt")
    @ResponseBody
    public Map<String, Object> calcReguaranteeAmt(String pactId,String dateStr) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try{
            mfBusPact.setPactId(pactId);
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            dataMap = mfBusFincAppFeign.getFeeCollectInfo(mfBusPact,dateStr);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "计算应收担保费异常");
            throw e;
        }
        return dataMap;
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
            FormData formfeeCollectBase = formService.getFormData((String)map.get("formId"));
            getFormValue(formfeeCollectBase, map);
            if (this.validateFormData(formfeeCollectBase)) {
                CwCollectConfim cwCollectConfim = new CwCollectConfim();
                setObjValue(formfeeCollectBase, cwCollectConfim);
                cwCollectConfim.setStatus(BizPubParm.COLLECT_STATUS_1);
                cwCollectConfimFeign.insertCwCollectConfim(cwCollectConfim);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SUBMIT_TONEXT.getMessage(
                        new CodeUtils().getMapByKeyName("FEE_COLLECT_STS").get(BizPubParm.COLLECT_STATUS_1)));
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

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        CwCollectConfim cwCollectConfim = new CwCollectConfim();
        MfBusPact mfBusPact = new MfBusPact();
        try{
            cwCollectConfim.setId(id);
            cwCollectConfim = cwCollectConfimFeign.getById(cwCollectConfim);

            mfBusPact.setPactId(cwCollectConfim.getPactId());
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            Map<String,Object> map = mfBusFincAppFeign.getFeeCollectInfo(mfBusPact,cwCollectConfim.getReceviedDate());

            FormData formfeeCollectDetail = formService.getFormData("feeCollectDetail");
            getObjValue(formfeeCollectDetail, cwCollectConfim);

            this.changeFormProperty(formfeeCollectDetail, "reguaranteeAmt", "initValue",String.valueOf(map.get("guaranteeAmt")));
            this.changeFormProperty(formfeeCollectDetail, "guaranteeAmt", "initValue", String.valueOf(map.get("guaranteeAmt")));


            model.addAttribute("formfeeCollectDetail", formfeeCollectDetail);
            model.addAttribute("query", "");
            model.addAttribute("resMap", map);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "/component/pact/feerefund/MfBusFeeCollect_Detail";
    }

}
