package app.component.dissolution.controller;

import app.base.User;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.compensatory.feign.MfBusCompensatoryApplyFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.dissolution.entity.MfBusDissolutionGuarantee;
import app.component.dissolution.feign.MfBusDissolutionGuaranteeFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feerefund.feign.MfBusFeeRefundFeign;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 解保管理信息表(MfBusDissolutionGuarantee)表控制层
 *
 * @author
 * @since 2020-02-29 20:54:37
 */
@Controller
@RequestMapping("/mfBusDissolutionGuarantee")
public class MfBusDissolutionGuaranteeController  extends BaseFormBean {

    private static Logger log = LoggerFactory.getLogger(MfBusDissolutionGuaranteeController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 服务对象
     */
     @Autowired
    private MfBusDissolutionGuaranteeFeign mfBusDissolutionGuaranteeFeign;

    @Autowired
    private MfBusPactFeign mfBusPactFeign;

    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;


    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;

    @Autowired
    private MfBusFeeRefundFeign mfBusFeeRefundFeign;
    //代偿
    @Autowired
    private MfBusCompensatoryApplyFeign mfBusCompensatoryApplyFeign;

    /**
     * 解保登记合同列表页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/dissolution/MfBusDissolutionGuarantee_ContactList";
    }

    /**
     * 解保历史列表页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDissolutionListPage")
    public String getDissolutionListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/dissolution/MfBusDissolutionGuarantee_HisList";
    }


    /***
     * 合同列表数据
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        try {
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfBusDissolutionGuaranteeFeign.findByPage(ipage);
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

    /***
     * 解保历史列表数据
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByDissolutionPageAjax")
    @ResponseBody
    public Map<String, Object> findByDissolutionPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusDissolutionGuarantee mfBusDissolutionGuarantee = new MfBusDissolutionGuarantee();
        try {
            mfBusDissolutionGuarantee.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusDissolutionGuarantee.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusDissolutionGuarantee.setCriteriaList(mfBusDissolutionGuarantee, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusDissolutionGuarantee", mfBusDissolutionGuarantee));
            ipage = mfBusDissolutionGuaranteeFeign.findByDissolutionPage(ipage);
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
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model,String pactId) throws Exception {
        log.debug("合同ID:{}",pactId);
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formbusDissolutionGuaranteeInput = formService.getFormData("busDissolutionGuaranteeInput");
        getFormValue(formbusDissolutionGuaranteeInput);
        MfBusDissolutionGuarantee mfBusDissolutionGuarantee = new MfBusDissolutionGuarantee();
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact =  mfBusPactFeign.getById(mfBusPact);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfBusPact.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setPactId(pactId);
        List<MfBusFincApp> list = mfBusFincAppFeign.getByPactId(mfBusFincApp);
        //申请金额
        double applyPutoutAmt =0.0;
        //实际放款金额
        double realPutoutAmt =0.0;
        //还款金额
        double repayAmt;
        //未还款金额
        double loanBal =0.0;
        for (Iterator<MfBusFincApp> iterator = list.iterator(); iterator.hasNext(); ) {
            MfBusFincApp app =  iterator.next();
            applyPutoutAmt = MathExtend.add(applyPutoutAmt,app.getPutoutAmt()) ;
            realPutoutAmt =  MathExtend.add(realPutoutAmt,app.getPutoutAmtReal());
            loanBal = MathExtend.add(loanBal,app.getLoanBal());
        }
        repayAmt = MathExtend.subtract(realPutoutAmt,loanBal);
        String cusType = new CodeUtils().getMapByKeyName("CUS_TYPE").get(mfCusCustomer.getCusType());
        mfBusDissolutionGuarantee.setCusType(cusType);
        mfBusDissolutionGuarantee.setCusName(mfCusCustomer.getCusName());
        mfBusDissolutionGuarantee.setPactNo(mfBusPact.getPactNo());
        mfBusDissolutionGuarantee.setPactAmt(mfBusPact.getPactAmt());
        mfBusDissolutionGuarantee.setApplyPutoutAmt(applyPutoutAmt);
        mfBusDissolutionGuarantee.setRealPutoutAmt(realPutoutAmt);
        mfBusDissolutionGuarantee.setRepayAmt(repayAmt);
        //在保金额(=实际放款金额)
        mfBusDissolutionGuarantee.setAmt(realPutoutAmt);
        mfBusDissolutionGuarantee.setPactId(pactId);
        getObjValue(formbusDissolutionGuaranteeInput, mfBusDissolutionGuarantee);
        model.addAttribute("pactId", pactId);
        model.addAttribute("formbusDissolutionGuaranteeInput", formbusDissolutionGuaranteeInput);
        model.addAttribute("query", "");
        return "/component/dissolution/MfBusDissolutionGuarantee_Input";
    }

    /**
     * 解保信息明细信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail")
    public String detail(Model model,String id) throws Exception {
        log.debug("解保信息ID:{}",id);
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formbusDissolutionGuaranteeDetail = formService.getFormData("busDissolutionGuaranteeDetail");
        getFormValue(formbusDissolutionGuaranteeDetail);
        MfBusDissolutionGuarantee mfBusDissolutionGuarantee = mfBusDissolutionGuaranteeFeign.getById(id);
        String cusType = new CodeUtils().getMapByKeyName("CUS_TYPE").get(mfBusDissolutionGuarantee.getCusType());
        mfBusDissolutionGuarantee.setCusType(cusType);
        getObjValue(formbusDissolutionGuaranteeDetail, mfBusDissolutionGuarantee);
        model.addAttribute("formbusDissolutionGuaranteeDetail", formbusDissolutionGuaranteeDetail);
        model.addAttribute("query", "");
        return "/component/dissolution/MfBusDissolutionGuarantee_Detail";
    }


    /**
     * 计算提前解保退费金额
     * @param pactId
     * @param dissolutionDate
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/calculateRefund")
    @ResponseBody
    public Map<String, Object> calculateRefund(String pactId,String dissolutionDate) throws Exception {
        log.debug("calculateRefund pactId:{}",pactId);
        log.debug("calculateRefund dissolutionDate:{}",dissolutionDate);
        Map<String, Object> dataMap = new HashMap();
        try {
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setPactId(pactId);
            mfBusPact =  mfBusPactFeign.getById(mfBusPact);
            //计算应退费用
            Map<String,Object> parmMap = new HashMap<>();
            parmMap.put("mfBusPact", mfBusPact);
            parmMap.put("date",DateUtil.getYYYYMMDD(dissolutionDate));
            Double appAmtTmp = mfBusFeeRefundFeign.calcAppAmt(parmMap);
            dataMap.put("flag", "success");
            dataMap.put("amt", appAmtTmp);
            dataMap.put("msg", "请求成功");
        }catch (Exception e) {
                dataMap.put("flag", "error");
                dataMap.put("msg", e.getMessage());
                throw e;
        }
        return dataMap;
    }

    /**
     *   解保登记
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public Map<String, Object> insert(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap();
        try {
            FormData busDissolutionGuaranteeInput = formService.getFormData("busDissolutionGuaranteeInput");
            getFormValue(busDissolutionGuaranteeInput, getMapByJson(ajaxData));
            if(this.validateFormData(busDissolutionGuaranteeInput)){
                MfBusDissolutionGuarantee mfBusDissolutionGuarantee = new MfBusDissolutionGuarantee();
                setObjValue(busDissolutionGuaranteeInput, mfBusDissolutionGuarantee);
                //如果实际放款金额>已还款金额，则可以操作提前解保和代偿解保。代偿解保需要先发起代偿申请流程审批之后，才能更改此状态。
                if(mfBusDissolutionGuarantee.getRealPutoutAmt() > mfBusDissolutionGuarantee.getRepayAmt() && mfBusDissolutionGuarantee.getDissolutionType().equals("3")){
                    List<MfBusCompensatoryApply> mfBusCompensatoryApplys = mfBusCompensatoryApplyFeign.getByPactId(mfBusDissolutionGuarantee.getPactId());
                    if(null == mfBusCompensatoryApplys){
                        dataMap.put("flag", "error");
                        dataMap.put("msg", "代偿解保需先发起代偿申请流程！");
                        return dataMap;
                    }
                }
                MfBusPact mfBusPact = new MfBusPact();
                mfBusPact.setPactId(mfBusDissolutionGuarantee.getPactId());
                mfBusPact =  mfBusPactFeign.getById(mfBusPact);
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfBusPact.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                mfBusDissolutionGuarantee.setCusType(mfCusCustomer.getCusType());
                mfBusDissolutionGuarantee.setCusNo(mfCusCustomer.getCusNo());
                mfBusDissolutionGuarantee.setCusName(mfCusCustomer.getCusName());
                mfBusDissolutionGuarantee.setPactNo(mfBusPact.getPactNo());
                mfBusDissolutionGuarantee.setAmt(mfBusPact.getPactAmt());
                mfBusDissolutionGuarantee.setOpNo(User.getRegNo(this.request));
                mfBusDissolutionGuarantee.setOpName(User.getRegName(this.request));
                mfBusDissolutionGuarantee.setId(WaterIdUtil.getWaterId());
                mfBusDissolutionGuarantee.setRegTime(DateUtil.getDateTime());
                mfBusDissolutionGuaranteeFeign.insert(mfBusDissolutionGuarantee);
                dataMap.put("flag", "success");
                dataMap.put("msg", "解保成功！");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

}