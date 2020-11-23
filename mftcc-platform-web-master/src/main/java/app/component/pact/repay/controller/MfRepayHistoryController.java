package app.component.pact.repay.controller;

import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayHistoryDetail;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.entity.MfRepayRecheck;
import app.component.calc.core.feign.MfRepayHistoryDetailFeign;
import app.component.calc.core.feign.MfRepayHistoryFeign;
import app.component.calc.core.feign.MfRepayPlanFeign;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.wkf.entity.Result;
import app.util.DataUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
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

import static com.itextpdf.text.pdf.PdfName.ca;

/**
 * Title: MfRepayHistoryAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Sun Jun 12 15:33:55 CST 2016
 **/
@Controller
@RequestMapping("/mfRepayHistory")
public class MfRepayHistoryController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    // 注入MfRepayHistoryBo
    @Autowired
    private MfRepayHistoryFeign mfRepayHistoryFeign;
    @Autowired
    private MfRepayHistoryDetailFeign mfRepayHistoryDetailFeign;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;
    @Autowired
    private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;
    @Autowired
    private MfRepayPlanFeign mfRepayPlanFeign;

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("query", "");
        return "/component/pact/repay/MfRepayHistory_List";
    }

    /**
     * 还款复核列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRecheckListPage")
    public String getRecheckListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("query", "");
        return "/component/pact/repay/MfBusRepayRecheck_List";
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        System.out.println(123);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        try {
            mfRepayHistory.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfRepayHistory.setCustomSorts(ajaxData);// 自定义查询参数赋值
            mfRepayHistory.setCriteriaList(mfRepayHistory, ajaxData);// 我的筛选
            // this.getRoleConditions(mfRepayHistory,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfRepayHistory", mfRepayHistory));
            ipage = mfRepayHistoryFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
             * dataMap.put("tableData",tableHtml);
             */
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRepayRecheckList")
    @ResponseBody
    public Map<String, Object> getRepayRecheckList(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfRepayRecheck mfRepayRecheck = new MfRepayRecheck();
        try {
            mfRepayRecheck.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfRepayRecheck.setCustomSorts(ajaxData);
            mfRepayRecheck.setCriteriaList(mfRepayRecheck, ajaxData);// 我的筛选
            // this.getRoleConditions(mfRepayHistory,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfRepayRecheck", mfRepayRecheck));
            ipage = mfRepayHistoryFeign.getRepayRecheckList(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            dataMap.put("tableHtml", tableHtml);


        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX新增
     *
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
            FormData formrepay0002 = formService.getFormData("repay0002");
            getFormValue(formrepay0002, getMapByJson(ajaxData));
            if (this.validateFormData(formrepay0002)) {
                MfRepayHistory mfRepayHistory = new MfRepayHistory();
                setObjValue(formrepay0002, mfRepayHistory);
                mfRepayHistoryFeign.insert(mfRepayHistory);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
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
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formrepay0002 = formService.getFormData("repay0002");
        getFormValue(formrepay0002, getMapByJson(ajaxData));
        MfRepayHistory mfRepayHistoryJsp = new MfRepayHistory();
        setObjValue(formrepay0002, mfRepayHistoryJsp);
        MfRepayHistory mfRepayHistory = mfRepayHistoryFeign.getById(mfRepayHistoryJsp);
        if (mfRepayHistory != null) {
            try {
                mfRepayHistory = (MfRepayHistory) EntityUtil.reflectionSetVal(mfRepayHistory, mfRepayHistoryJsp,
                        getMapByJson(ajaxData));
                mfRepayHistoryFeign.update(mfRepayHistory);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
                throw e;
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
        }
        return dataMap;
    }

    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formrepay0002 = formService.getFormData("repay0002");
            getFormValue(formrepay0002, getMapByJson(ajaxData));
            if (this.validateFormData(formrepay0002)) {
                MfRepayHistory mfRepayHistory = new MfRepayHistory();
                setObjValue(formrepay0002, mfRepayHistory);
                mfRepayHistoryFeign.update(mfRepayHistory);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/busRecheckAjax")
    @ResponseBody
    public Map<String, Object> busRecheckAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = getMapByJson(ajaxData);
        try {
            String repayIds = paramMap.get("repayIds").toString();
            String arr[] = repayIds.split(",");
            if (arr.length > 0) {
                for (int i = 0; i < arr.length; i++) {
                    MfRepayHistory mfRepayHistory = new MfRepayHistory();
                    mfRepayHistory.setRepayId(arr[i]);
                    mfRepayHistory.setRecheckSts("1");
                    mfRepayHistoryFeign.update(mfRepayHistory);
                }
                dataMap.put("flag", "success");
                dataMap.put("msg", "复核成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", "复核失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "复核失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * 批量打印回款凭证
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchPrint")
    public String batchPrint(Model model, String repayIds) throws Exception {
        try {
            String arr[] = repayIds.split(",");
            List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

            for (String repayId : arr) {
                Map<String, Object> dataMap = getBatchPrintData(repayId);
                listData.add(dataMap);
            }
            model.addAttribute("listData", listData);

        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }
        return "/component/pact/repay/MfBatchPrintPaymentVoucher";
    }

    /**
     * 获取还款凭证数据源
     *
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, Object> getBatchPrintData(String repayId) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        MfRepayRecheck mfRepayRecheck = new MfRepayRecheck();
        mfRepayRecheck.setRepayId(repayId);
        mfRepayRecheck = mfRepayHistoryFeign.getRepayRecheckByRepayId(mfRepayRecheck);
        if (mfRepayRecheck != null) {
            if (mfRepayRecheck.getRecvAmtSys() != null) {
                String amtArr[] = String.valueOf(mfRepayRecheck.getRecvAmtSys()).split("\\.");
                String amtStr = amtArr[0];
                int count = amtStr.length();
                for (int i = 1; i <= 9 - count; i++) {
                    amtStr = "-" + amtStr;
                }
                mfRepayRecheck.setAmt(amtStr + amtArr[1]);

            }
            parm.put("mfRepayRecheck", mfRepayRecheck);
        }
        return parm;

    }

    /**
     * 批量打印利息单
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchPrintIntst")
    public String batchPrintIntst(Model model, String repayIds) throws Exception {
        try {
            String arr[] = repayIds.split(",");
            List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

            for (String repayId : arr) {
                List<Map<String, Object>> mfRepayRecheckList = getBatchPrintIntstData(repayId);
                listData.addAll(mfRepayRecheckList);
            }
            model.addAttribute("listData", listData);

        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }
        return "/component/pact/repay/MfBatchPrintInterestList";
    }

    /**
     * 获取利息单数据源
     *
     * @param appId
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBatchPrintIntstData(String repayId) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        MfRepayRecheck mfRepayRecheck = new MfRepayRecheck();
        mfRepayRecheck.setRepayId(repayId);
        mfRepayRecheck = mfRepayHistoryFeign.getRepayRecheckByRepayId(mfRepayRecheck);//获取客户信息和合同展示号
        List<Map<String, Object>> recheckList = new ArrayList<Map<String, Object>>();
        if (mfRepayRecheck != null) {
            MfRepayHistoryDetail mfRepayHistoryDetail = new MfRepayHistoryDetail();
            mfRepayHistoryDetail.setRepayId(repayId);
            //跟据还款历史repayId获取还款历史明细列表
            List<MfRepayHistoryDetail> mfRepayHistoryDetailList = mfRepayHistoryDetailFeign.getMfRepayHistoryDetailList(mfRepayHistoryDetail);
            if (mfRepayHistoryDetailList.size() > 0) {

                MfRepayPlan mfRepayPlan = new MfRepayPlan();
                for (MfRepayHistoryDetail mrhdl : mfRepayHistoryDetailList) {
                    mfRepayPlan.setPactId(mrhdl.getPactId());
                    mfRepayPlan.setFincId(mrhdl.getFincId());
                    mfRepayPlan.setTermNum(mrhdl.getTermNum());
                    mfRepayPlan = mfRepayPlanFeign.getRepayPlanByBean(mfRepayPlan);//根据合同号和期号获取还款计划实体

                    if (mfRepayPlan != null) {
                        String planBeginDate = DateUtil.getShowDateTime(mfRepayPlan.getPlanBeginDate());
                        String planEndDate = DateUtil.getShowDateTime(mfRepayPlan.getPlanEndDate());
                        StringBuffer currentDate = new StringBuffer();//利息单起止日期
                        mfRepayRecheck.setCurrentDate(currentDate.append(planBeginDate).append("/").append(planEndDate).toString());
                        mfRepayRecheck.setCurrentRepayIntstCh(MathExtend.numberToChinese(mfRepayPlan.getRepayIntst()));//应收利息大写
                        mfRepayRecheck.setCurrentRepayIntst(MathExtend.moneyStr(mfRepayPlan.getRepayIntst()));//应收利息小写
                        mfRepayRecheck.setPrcpSum(MathExtend.moneyStr(mfRepayPlan.getRepayPrcp()));
                        parm.put("mfRepayRecheck", mfRepayRecheck);
                    }
                    recheckList.add(parm);
                }
            }
        }
        return recheckList;

    }

    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/singleRecheckAjax")
    @ResponseBody
    public Map<String, Object> singleRecheckAjax(String repayId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfRepayHistory mfRepayHistory = new MfRepayHistory();
            if (StringUtil.isNotEmpty(repayId)) {
                mfRepayHistory.setRepayId(repayId);
                mfRepayHistory.setRecheckSts("1");
                mfRepayHistoryFeign.update(mfRepayHistory);
                dataMap.put("flag", "success");
                dataMap.put("msg", "复核成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", "获取还款信息编号失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "复核失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String repayId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formrepay0002 = formService.getFormData("repay0002");
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        mfRepayHistory.setRepayId(repayId);
        mfRepayHistory = mfRepayHistoryFeign.getById(mfRepayHistory);
        getObjValue(formrepay0002, mfRepayHistory, formData);
        if (mfRepayHistory != null) {
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
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String repayId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        mfRepayHistory.setRepayId(repayId);
        try {
            mfRepayHistoryFeign.delete(mfRepayHistory);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 新增页面
     *
     * @param appId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String appId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        mfRepayHistory = mfRepayHistoryFeign.setMfRepayHistoryInfo(appId);
        FormData formrepay0003 = formService.getFormData("repay0003");
        getObjValue(formrepay0003, mfRepayHistory);
        model.addAttribute("formrepay0003", formrepay0003);
        model.addAttribute("mfRepayHistory", mfRepayHistory);
        model.addAttribute("query", "");
        return "component/pact/repay/MfRepayHistory_Insert";
    }

    /***
     * 新增
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insert")
    public String insert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formrepay0002 = formService.getFormData("repay0002");
        getFormValue(formrepay0002);
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        setObjValue(formrepay0002, mfRepayHistory);
        mfRepayHistoryFeign.insert(mfRepayHistory);
        getObjValue(formrepay0002, mfRepayHistory);
        this.addActionMessage(model, "保存成功");
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfRepayHistory", mfRepayHistory));
        List<MfRepayHistory> mfRepayHistoryList = (List<MfRepayHistory>) mfRepayHistoryFeign
                .findByPage(ipage).getResult();
        model.addAttribute("formrepay0002", formrepay0002);
        model.addAttribute("mfRepayHistory", mfRepayHistory);
        model.addAttribute("mfRepayHistoryList", mfRepayHistoryList);
        model.addAttribute("query", "");
        return "component/pact/repay/MfRepayHistory_Insert";
    }

    /**
     * 查询
     *
     * @param fincId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formrepay0001 = formService.getFormData("repay0001");
        getFormValue(formrepay0001);
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        mfRepayHistory.setFincId(fincId);
        mfRepayHistory.setAccountFlag("0");
        mfRepayHistory = mfRepayHistoryFeign.getReviewInfo(mfRepayHistory);
        getObjValue(formrepay0001, mfRepayHistory);
        model.addAttribute("formrepay0001", formrepay0001);
        model.addAttribute("mfRepayHistory", mfRepayHistory);
        model.addAttribute("query", "");
        return "component/pact/repay/MfRepayHistory_Detail";
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public String delete(Model model, String repayId) throws Exception {
        ActionContext.initialize(request, response);
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        mfRepayHistory.setRepayId(repayId);
        mfRepayHistoryFeign.delete(mfRepayHistory);
        return getListPage(model);
    }

    /**
     * 新增校验
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/validateInsert")
    public void validateInsert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formrepay0002 = formService.getFormData("repay0002");
        getFormValue(formrepay0002);
        boolean validateFlag = this.validateFormData(formrepay0002);
    }

    /**
     * 修改校验
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/validateUpdate")
    public void validateUpdate(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formrepay0002 = formService.getFormData("repay0002");
        getFormValue(formrepay0002);
        boolean validateFlag = this.validateFormData(formrepay0002);
    }

    /**
     * 方法描述： 还款复核
     *
     * @param fincId
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2016-8-18 下午5:20:04
     */
    @RequestMapping(value = "/repayReviewAjax")
    @ResponseBody
    public Map<String, Object> repayReviewAjax(String pactId, String fincId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfRepayHistory mfRepayHistory = new MfRepayHistory();
            mfRepayHistory.setPactId(pactId);
            mfRepayHistory.setFincId(fincId);
            Result result = mfRepayHistoryFeign.doRepayReview(mfRepayHistory);
            if (result.isSuccess()) {
                if (result.isEndSts()) {
                    // 获取收款计划列表
                    // MfBusRepayPlan mfBusRepayPlan = new MfBusRepayPlan();
                    // mfBusRepayPlan.setFincId(mfRepayHistory.getFincId());
                    // JsonTableUtil jtu = new JsonTableUtil();
                    // String tableHtml2 =
                    // jtu.getJsonStr("tablerepayplan0003","tableTag",mfBusRepayPlanFeign.getList(mfBusRepayPlan),
                    // null,true);

                    MfRepayPlan mfRepayPlan = new MfRepayPlan();
                    mfRepayPlan.setFincId(mfRepayHistory.getFincId());
                    JsonTableUtil jtu = new JsonTableUtil();
                    String tableHtml = jtu.getJsonStr("tablerepayplan0003", "tableTag",
                            calcRepaymentInterfaceFeign.getMfRepayPlanList(mfRepayPlan), null, true);

                    dataMap.put("tableHtml", tableHtml);
                    dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("复核"));
                    dataMap.put("node", "repayed");
                } else {
                    dataMap.put("msg", MessageEnum.SUCCEED_REVIEW_TONEXT.getMessage(result.getMsg()));
                    dataMap.put("node", "repaying");
                }
                // MfBusPact mfBusPact = new MfBusPact();
                // mfBusPact.setPactId(mfRepayHistory.getPactId());
                // mfBusPact=pactInterfaceFeign.getById(mfBusPact);
                // dataMap.put("wkfAppId", mfBusPact.getWkfAppId());
            }
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("复核"));
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 获取还款历史列表信息
     *
     * @param tableId
     * @return
     * @throws Exception String
     * @author Javelin
     * @date 2017-7-28 上午10:41:21
     */
    @RequestMapping(value = "/getMfRepayHistoryListAjax")
    @ResponseBody
    public Map<String, Object> getMfRepayHistoryListAjax(String fincId, String tableId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 判断详情页面还款按钮的显隐
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
        mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
        // 还款历史
        String tableHtml = "";
        if (mfBusFincApp != null) {
            MfRepayHistory mfRepayHistory = new MfRepayHistory();
            mfRepayHistory.setFincId(mfBusFincApp.getFincId());
            List<MfRepayHistory> mfRepayHistoryList = mfRepayHistoryFeign.getList(mfRepayHistory);
            // 获取减免费用总和 只是用于展示 存放在 deductNormIntstSum 字段中展示
            for (MfRepayHistory repayHistory : mfRepayHistoryList) {
                Double duectSum = MathExtend.add(repayHistory.getDeductCmpdIntstSum(),
                        repayHistory.getDeductNormIntstSum());
                duectSum = MathExtend.add(duectSum, repayHistory.getDeductOverIntstSum());
                duectSum = MathExtend.add(duectSum, repayHistory.getDeductFeeSum());
                duectSum = MathExtend.add(duectSum, repayHistory.getDeductPenaltySum());
                repayHistory.setDeductNormIntstSum(duectSum);
                if (repayHistory.getBalAmt() == null) {// 结余金额空处理
                    repayHistory.setBalAmt(0.0);
                }
                //处理正常利息和逾期利息合计 normAndOverIntstAndSum
                Double normAndOverIntstAndSum = MathExtend.add(repayHistory.getNormIntstSum(), repayHistory.getOverIntstSum());
                repayHistory.setNormAndOverIntstAndSum(normAndOverIntstAndSum);
                //处理罚息 = 本金罚息 + 利息罚息
                double overIntstPartSum = 0.00;
                double cmpdIntstPartSum = 0.00;
                if (repayHistory.getOverIntstPartSum() != null) {
                    overIntstPartSum = repayHistory.getOverIntstPartSum();
                }
                if (repayHistory.getCmpdIntstPartSum() != null) {
                    cmpdIntstPartSum = repayHistory.getCmpdIntstPartSum();
                }
                double faXiSum = MathExtend.add(overIntstPartSum, cmpdIntstPartSum);
                repayHistory.setFaXiSum(faXiSum);
            }
            if (mfRepayHistoryList.size() > 0) {
                JsonTableUtil jtu = new JsonTableUtil();
                tableHtml = jtu.getJsonStr(tableId, "tableTag", mfRepayHistoryList, null, true);
            }
        }
        dataMap.put("htmlStr", tableHtml);
        return dataMap;
    }

    /**
     * 业务查询-还款历史查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRepayHisList")
    public String getRepayHisList(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/pact/repay/MfRepayHistory_queryList";
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findRepayHisByPageAjax")
    @ResponseBody
    public Map<String, Object> findRepayHisByPageAjax(Ipage ipage, Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                      String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        try {
            mfRepayHistory.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfRepayHistory.setCriteriaList(mfRepayHistory, ajaxData);// 我的筛选
            mfRepayHistory.setCustomSorts(ajaxData);//自定义排序
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfRepayHistory", mfRepayHistory));
            // 自定义查询Bo方法
            ipage = mfRepayHistoryFeign.findRepayHisByPage(ipage);
            String resultJsonStr = JSON.toJSONString(ipage.getResult());
            List<MfRepayHistory> mfRepayHistoryList = JSON.parseArray(resultJsonStr, MfRepayHistory.class);

            // 获取减免费用总和 只是用于展示 存放在 deductNormIntstSum 字段中展示
            for (MfRepayHistory repayHistory : mfRepayHistoryList) {
                Double duectSum = MathExtend.add(repayHistory.getDeductCmpdIntstSum(),
                        repayHistory.getDeductNormIntstSum());
                duectSum = MathExtend.add(duectSum, repayHistory.getDeductOverIntstSum());
                duectSum = MathExtend.add(duectSum, repayHistory.getDeductFeeSum());
                duectSum = MathExtend.add(duectSum, repayHistory.getDeductPenaltySum());
                repayHistory.setDeductNormIntstSum(duectSum);
                if (repayHistory.getBalAmt() == null) {// 结余金额空处理
                    repayHistory.setBalAmt(0.0);
                }
                //处理正常利息和逾期利息合计 normAndOverIntstAndSum
                Double normAndOverIntstAndSum = MathExtend.add(repayHistory.getNormIntstSum(), repayHistory.getOverIntstSum());
                repayHistory.setNormAndOverIntstAndSum(normAndOverIntstAndSum);
                //处理罚息 = 本金罚息 + 利息罚息
                double overIntstPartSum = 0.00;
                double cmpdIntstPartSum = 0.00;
                if (repayHistory.getOverIntstPartSum() != null) {
                    overIntstPartSum = repayHistory.getOverIntstPartSum();
                }
                if (repayHistory.getCmpdIntstPartSum() != null) {
                    cmpdIntstPartSum = repayHistory.getCmpdIntstPartSum();
                }
                double faXiSum = MathExtend.add(overIntstPartSum, cmpdIntstPartSum);
                repayHistory.setFaXiSum(faXiSum);
            }
            ipage.setResult(mfRepayHistoryList);
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
     * 业务查询-还款历史明细查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRepayHisDetailList")
    public String getRepayHisDetailList(Model model, String repayId) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("repayId", repayId);
        return "/component/pact/repay/MfRepayHistoryDetail_queryList";
    }

    /***
     * 明细列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findRepayHisDetailByPageAjax")
    @ResponseBody
    public Map<String, Object> findRepayHisDetailByPageAjax(String repayId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfRepayHistoryDetail mfRepayHistoryDetail = new MfRepayHistoryDetail();
        mfRepayHistoryDetail.setRepayId(repayId);
        try {
            // 查询Bo方法
            List<MfRepayHistoryDetail> list = mfRepayHistoryDetailFeign.findRepayHisDetailList(mfRepayHistoryDetail);
            // 获取减免费用总和 只是用于展示 存放在 deductNormIntstSum 字段中展示
            for (MfRepayHistoryDetail mfRepayHistoryDetail1 : list) {
                //计算减免金额
                Double duectSum = MathExtend.add(mfRepayHistoryDetail1.getDeductCmpdIntstSum(),
                        mfRepayHistoryDetail1.getDeductNormIntstSum());
                duectSum = MathExtend.add(duectSum, mfRepayHistoryDetail1.getDeductOverIntstSum());
                duectSum = MathExtend.add(duectSum, mfRepayHistoryDetail1.getDeductFeeSum());
                duectSum = MathExtend.add(duectSum, mfRepayHistoryDetail1.getDeductPenaltySum());
                mfRepayHistoryDetail1.setDeductNormIntstSum(duectSum);
                //处理正常利息和逾期利息合计 normAndOverIntstAndSum
                Double normAndOverIntstAndSum = MathExtend.add(mfRepayHistoryDetail1.getNormIntstSum(), mfRepayHistoryDetail1.getOverIntstSum());
                mfRepayHistoryDetail1.setNormAndOverIntstAndSum(normAndOverIntstAndSum);
                //处理罚息 = 本金罚息 + 利息罚息
                double overIntstPartSum = 0.00;
                double cmpdIntstPartSum = 0.00;
                if (mfRepayHistoryDetail1.getOverIntstPartSum() != null) {
                    overIntstPartSum = mfRepayHistoryDetail1.getOverIntstPartSum();
                }
                if (mfRepayHistoryDetail1.getCmpdIntstPartSum() != null) {
                    cmpdIntstPartSum = mfRepayHistoryDetail1.getCmpdIntstPartSum();
                }
                double faXiSum = MathExtend.add(overIntstPartSum, cmpdIntstPartSum);
                mfRepayHistoryDetail1.setFaXiSum(faXiSum);
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtmlRepayHisDetail = jtu.getJsonStr("tablerepayhis0001_detail_query", "tableTag", list, null, true);
            dataMap.put("tableHtmlRepayHisDetail", tableHtmlRepayHisDetail);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/doRepayHistoryRepaymentNumber")
    @ResponseBody
    public Map<String, Object> doRepayHistoryRepaymentNumber() throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        try {

            dataMap = mfRepayHistoryFeign.doRepayHistoryRepaymentNumber();

        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }

        return dataMap;
    }


    /**
     * @param model
     * @param cusNo
     * @return
     * @throws Exception
     * @desc 根据客户号去查询该客户已完结的借据
     * @Author zkq
     * @date 20190624
     */
    @RequestMapping(value = "/getMfRepayHistoryList")
    public String getMfRepayHistoryList(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        MfRepayHistory mfRepayHistory = new MfRepayHistory();
        mfRepayHistory.setCusNo(cusNo);
        List<MfRepayHistory> mfRepayHistoryList = mfRepayHistoryFeign.getRepayHistoryListByCusNo(mfRepayHistory);

        model.addAttribute("mfRepayHistoryList", mfRepayHistoryList);
        model.addAttribute("busEntrance", "finc");
        model.addAttribute("query", "");
        return "/component/pact/MfRepayHistory_multiBusList";
    }





}
