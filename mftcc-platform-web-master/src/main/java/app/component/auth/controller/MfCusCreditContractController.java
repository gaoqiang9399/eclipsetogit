package app.component.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.component.auth.entity.*;
import app.component.auth.feign.*;
import app.component.cus.entity.MfBusAgencies;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.model.feign.MfTemplateBizConfigFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.MathExtend;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.msgconf.entity.PliWarning;
import app.component.msgconf.feign.PliWarningFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfCusCreditContractAction.java
 * Description:授信协议视图控制
 *
 * @author:LJW
 * @Tue Mar 07 15:39:22 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCreditContract")
public class MfCusCreditContractController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    //注入MfCusCreditContractBo
    @Autowired
    private MfCusCreditContractFeign mfCusCreditContractFeign;
    @Autowired
    private MfCusPorductCreditFeign porductCreditFeign;
    @Autowired
    private AppInterfaceFeign appInterfaceFeign;
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
    @Autowired
    private PliWarningFeign pliWarningFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private MfCusCustomerFeign cusCustomerFeign;
    @Autowired
    private MfCusCreditAdjustApplyFeign mfCusCreditAdjustApplyFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private MfCusCreditConfigFeign  mfCusCreditConfigFeign;
    @Autowired
    private MfCusCreditApplyFeign mfCusCreditApplyFeign;
    @Autowired
    private MfBusAgenciesFeign mfBusAgenciesFeign;
    @Autowired
    private MfTemplateBizConfigFeign mfTemplateBizConfigFeign;
    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        return "/component/auth/MfCusCreditContract_List";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map <String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
         MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        try {
            mfCusCreditContract.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusCreditContract.setCriteriaList(mfCusCreditContract, ajaxData);//我的筛选
            //this.getRoleConditions(mfCusCreditContract,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            ipage = mfCusCreditContractFeign.findByPage(ipage, mfCusCreditContract);
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
     * AJAX新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map <String, Object> insertAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            FormData formcreditpact0002 = formService.getFormData("creditpact0002");
            getFormValue(formcreditpact0002, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditpact0002)) {
                MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                setObjValue(formcreditpact0002, mfCusCreditContract);
                mfCusCreditContractFeign.insert(mfCusCreditContract);
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
    public Map <String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formcreditpact0002 = formService.getFormData("creditpact0002");
        getFormValue(formcreditpact0002, getMapByJson(ajaxData));
        MfCusCreditContract mfCusCreditContractJsp = new MfCusCreditContract();
        setObjValue(formcreditpact0002, mfCusCreditContractJsp);
        MfCusCreditContract mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContractJsp);
        if (mfCusCreditContract != null) {
            try {
                mfCusCreditContract = (MfCusCreditContract) EntityUtil.reflectionSetVal(mfCusCreditContract, mfCusCreditContractJsp, getMapByJson(ajaxData));
                mfCusCreditContractFeign.update(mfCusCreditContract);
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
    public Map <String, Object> updateAjax(String ajaxData, String wkfAppId, String commitType, String kindNos, String creditAmts, String amountLands, String monthTotalRates, String temporaryStorage) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditpact0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));//"creditpact0001"
            getFormValue(formcreditpact0001, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditpact0001)) {
                MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                setObjValue(formcreditpact0001, mfCusCreditContract);

                if (StringUtil.isNotEmpty(mfCusCreditContract.getPactNo())) {
                    // 合同编号唯一性检查
                    MfCusCreditContract cusCreditContract = new MfCusCreditContract();
                    cusCreditContract.setPactNo(mfCusCreditContract.getPactNo());
                    List<MfCusCreditContract> mfCusCreditContractlList = mfCusCreditContractFeign.getMfCusCreditContractList(cusCreditContract);
                    if ((mfCusCreditContractlList.size() == 1 && !mfCusCreditContractlList.get(0).getPactNo().equals(mfCusCreditContract.getPactNo())) || mfCusCreditContractlList.size() > 1) {
                        Map<String, String> msgMap = new HashMap<String, String>();
                        msgMap.put("pactNo", cusCreditContract.getPactNo());
                        msgMap.put("followType", "");
                        dataMap.put("flag", "error");
                        dataMap.put("msg", MessageEnum.EXIST_PACT_NO.getMessage(msgMap));
                        return dataMap;
                    }
                }
                //合同生成文件是否全部生成
                boolean saveFlag =false;
                String saveName ="";
                MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
                mfTemplateBizConfig.setNodeNo(BizPubParm.WKF_NODE.protocolPrint.getNodeNo());
                mfTemplateBizConfig.setAppId(mfCusCreditContract.getCreditAppId());
                List<MfTemplateBizConfig> mfTemplateBizConfigs = mfTemplateBizConfigFeign.getBizConfigList(mfTemplateBizConfig);
                if(mfTemplateBizConfigs.size()>0){
                    for (int i = 0; i < mfTemplateBizConfigs.size(); i++) {
                        mfTemplateBizConfig = mfTemplateBizConfigs.get(i);
                        if("1".equals(mfTemplateBizConfig.getIfMustInput())&&StringUtil.isEmpty(mfTemplateBizConfig.getDocFilePath())){
                            saveFlag =true;
                            saveName = mfTemplateBizConfig.getTemplateNameZh();
                            break;
                        }
                    }
                }
                if(saveFlag){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "请先保存"+saveName+"后，再进行保存！");
                    return dataMap;
                }
                //mfCusCreditContractFeign.submitContractSign(mfCusCreditContract,wkfAppId,commitType);
                String firstApprovalUser = dataMap.get("firstApprovalUser") == null ? "" : (String) dataMap.get("firstApprovalUser");
                mfCusCreditContractFeign.submitContractSign(mfCusCreditContract, wkfAppId, commitType, firstApprovalUser, temporaryStorage);
                //此时合同修改授信产品信息
                if (!"".equals(kindNos) && !"".equals(creditAmts) && !"".equals(amountLands) && !"".equals(monthTotalRates) && !"1".equals(temporaryStorage)) {
                    JSONArray kindNoXins = JSONArray.fromObject(kindNos);
                    JSONArray creditAmtXins = JSONArray.fromObject(creditAmts);
                    JSONArray amountLandXins = JSONArray.fromObject(amountLands);
                    JSONArray monthTotalRateXins = JSONArray.fromObject(monthTotalRates);
                    if (creditAmtXins != null && creditAmtXins.size() > 0 && !creditAmtXins.isEmpty() && amountLandXins != null && amountLandXins.size() > 0) {
                        MfCusPorductCredit porductCredit = new MfCusPorductCredit();
                        porductCredit.setCreditAppId(mfCusCreditContract.getCreditAppId());
                        //根据授信申请id删除授信产品表里面的数据
                        porductCreditFeign.deleteByCreditId(porductCredit);

                        MfCusPorductCredit cusPorductCredit = new MfCusPorductCredit();
                        MfSysKind mfSysKind = new MfSysKind();
                        for (int i = 0, count = creditAmtXins.size(); i < count; i++) {
                            if (StringUtil.isNotEmpty(kindNoXins.getString(i))) {
                                cusPorductCredit.setKindNo(kindNoXins.getString(i));
                                mfSysKind.setKindNo(kindNoXins.getString(i));
                            }
                            if (StringUtil.isNotEmpty(creditAmtXins.getString(i))) {
                                cusPorductCredit.setCreditAmt(Double.valueOf(creditAmtXins.getString(i)));
                                cusPorductCredit.setCreditBal(Double.valueOf(creditAmtXins.getString(i)));
                            }
                            if (amountLandXins.getString(i) != null && !"null".equals(amountLandXins.getString(i)) && StringUtil.isNotEmpty(amountLandXins.getString(i))) {
                                cusPorductCredit.setAmountLand(Integer.parseInt(amountLandXins.getString(i)));
                            }
                            if (monthTotalRateXins.getString(i) != null && !"null".equals(monthTotalRateXins.getString(i)) && StringUtil.isNotEmpty(monthTotalRateXins.getString(i))) {
                                cusPorductCredit.setMonthTotalRate(Double.valueOf(monthTotalRateXins.getString(i)));
                            }
                            cusPorductCredit.setCreditAppId(mfCusCreditContract.getCreditAppId());
                            //根据产品号查询出产品的名字
                            MfSysKind sysKind = mfSysKindFeign.getById(mfSysKind);
                            if (sysKind != null) {
                                cusPorductCredit.setKindName(sysKind.getKindName());
                            }
                            //修改授信产品表
                            porductCreditFeign.deleteKindInsert(cusPorductCredit);
                        }
                    }
                    mfCusCreditContract.setIsValid(BizPubParm.YES_NO_Y);
                    mfCusCreditContractFeign.updateIsValidByOldPactNo(mfCusCreditContract);
                }
//				mfCusCreditContractFeign.update(mfCusCreditContract);
                if (dataMap.get("creditType") != null && dataMap.get("oldPactNo") != null && dataMap.get("creditType").equals(BizPubParm.CREDIT_TYPE_TRANSFER) && !"".equals(dataMap.get("oldPactNo")) && !"1".equals(temporaryStorage)) {
                    MfCusCreditContract queryOld = new MfCusCreditContract();
                    queryOld.setPactNo((String) dataMap.get("oldPactNo"));
                    queryOld = mfCusCreditContractFeign.getById(queryOld);
                    if (queryOld != null) {
                        //queryOld.setCreditType(BizPubParm.CREDIT_TYPE_TRANSFER);
                        queryOld.setIsValid(BizPubParm.YES_NO_N);
                        mfCusCreditContractFeign.updateIsValidByOldPactNo(queryOld);
                    }
                }

                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 合同生成暂存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateFormAjax")
    @ResponseBody
    public Map <String, Object> updateFormAjax(String ajaxData, String wkfAppId, String commitType, String kindNos, String creditAmts, String amountLands, String monthTotalRates, String temporaryStorage) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditpact0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));//"creditpact0001"
            getFormValue(formcreditpact0001, getMapByJson(ajaxData));
            //if (this.validateFormData(formcreditpact0001)) {
                MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                setObjValue(formcreditpact0001, mfCusCreditContract);

                if (StringUtil.isNotEmpty(mfCusCreditContract.getPactNo())) {
                    // 合同编号唯一性检查
                    MfCusCreditContract cusCreditContract = new MfCusCreditContract();
                    cusCreditContract.setPactNo(mfCusCreditContract.getPactNo());
                    List<MfCusCreditContract> mfCusCreditContractlList = mfCusCreditContractFeign.getMfCusCreditContractList(cusCreditContract);
                    if ((mfCusCreditContractlList.size() == 1 && !mfCusCreditContractlList.get(0).getPactNo().equals(mfCusCreditContract.getPactNo())) || mfCusCreditContractlList.size() > 1) {
                        Map<String, String> msgMap = new HashMap<String, String>();
                        msgMap.put("pactNo", cusCreditContract.getPactNo());
                        msgMap.put("followType", "");
                        dataMap.put("flag", "error");
                        dataMap.put("msg", MessageEnum.EXIST_PACT_NO.getMessage(msgMap));
                        return dataMap;
                    }
                }
                //此时合同修改授信产品信息
			    mfCusCreditContractFeign.update(mfCusCreditContract);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            /*} else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/updateForManageAjax")
    @ResponseBody
    public Map <String, Object> updateForManageAjax(String ajaxData, String wkfAppId, String commitType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcreditpact0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));//"creditpact0001"
            getFormValue(formcreditpact0001, getMapByJson(ajaxData));
            if (this.validateFormData(formcreditpact0001)) {
                MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
                setObjValue(formcreditpact0001, mfCusCreditContract);

                // 合同编号唯一性检查
//                MfCusCreditContract cusCreditContract = new MfCusCreditContract();
//                cusCreditContract.setPactNo(mfCusCreditContract.getPactNo());
//                List <MfCusCreditContract> mfCusCreditContractlList = mfCusCreditContractFeign.getMfCusCreditContractList(cusCreditContract);
//                if ((mfCusCreditContractlList.size() == 1 && !mfCusCreditContractlList.get(0).getPactId().equals(mfCusCreditContract.getPactId()))
//                        || mfCusCreditContractlList.size() > 1) {
//                    Map <String, String> msgMap = new HashMap <String, String>();
//                    msgMap.put("pactNo", cusCreditContract.getPactNo());
//                    msgMap.put("followType", "");
//                    dataMap.put("flag", "error");
//                    dataMap.put("msg", MessageEnum.EXIST_PACT_NO.getMessage(msgMap));
//                    return dataMap;
//                }

                String firstApprovalUser = dataMap.get("firstApprovalUser") == null ? "" : (String) dataMap.get("firstApprovalUser");
                mfCusCreditContractFeign.submitContractSign(mfCusCreditContract, wkfAppId, commitType, firstApprovalUser, "");
//				mfCusCreditContractFeign.update(mfCusCreditContract);

                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map <String, Object> getByIdAjax(String ajaxData, String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> formData = new HashMap <String, Object>();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formcreditpact0002 = formService.getFormData("creditpact0002");
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setId(id);
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
        getObjValue(formcreditpact0002, mfCusCreditContract, formData);
        if (mfCusCreditContract != null) {
            dataMap.put("flag", "success");
            mfCusCreditContract.setCreditSumShowStr(MathExtend.moneyStr(mfCusCreditContract.getCreditSum(),2));
            mfCusCreditContract.setAuthBalShowStr(MathExtend.moneyStr(mfCusCreditContract.getAuthBal(),2));
            mfCusCreditContract.setBeginDate(DateUtil.getShowDateTime(mfCusCreditContract.getBeginDate()));
            mfCusCreditContract.setEndDate(DateUtil.getShowDateTime(mfCusCreditContract.getEndDate()));
            dataMap.put("mfCusCreditContract", mfCusCreditContract);

            //获取流程信息
            MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
            mfCusCreditConfig.setCreditId(mfCusCreditContract.getTemplateCreditId());
            mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
            if (mfCusCreditConfig != null) {
                dataMap.put("adaptationKindNo", mfCusCreditConfig.getAdaptationKindNo());
            }
            //查询合作银行信息
            MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
            mfCusAgenciesCredit.setCreditAppId(mfCusCreditContract.getCreditAppId());
            List<MfCusAgenciesCredit> mfCusAgenciesCreditList = mfCusCreditApplyFeign.getByCreditAppId(mfCusAgenciesCredit);
            JSONArray busAgenciesArray = new JSONArray();
            JSONObject busJson = new JSONObject();
            for (int i = 0; i < mfCusAgenciesCreditList.size(); i++) {
                mfCusAgenciesCredit = mfCusAgenciesCreditList.get(i);
                MfBusAgencies mfBusagencies = new MfBusAgencies();
                mfBusagencies.setAgenciesId(mfCusAgenciesCredit.getAgenciesId());
                mfBusagencies = mfBusAgenciesFeign.getById(mfBusagencies);
                JSONObject js = new JSONObject();
                js.put("id", mfCusAgenciesCredit.getAgenciesId());
                js.put("name", mfCusAgenciesCredit.getAgenciesName());
                js.put("hidcreditAmt", MathExtend.add(mfBusagencies.getCreditBal(),mfCusAgenciesCredit.getCreditAmt()));//剩余額度加上上次授信額度
                js.put("amt", mfCusAgenciesCredit.getCreditAmt());
                busAgenciesArray.add(js);
            }
            busJson.put("busAgencies", busAgenciesArray);
            dataMap.put("busJson", busJson);
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", "获取授信合同失败");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }

    /**
     * Ajax异步删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAjax")
    @ResponseBody
    public Map <String, Object> deleteAjax(String ajaxData, String id) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setId(id);
        try {
            mfCusCreditContractFeign.delete(mfCusCreditContract);
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
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcreditpact0002 = formService.getFormData("creditpact0002");
        model.addAttribute("formcreditpact0002", formcreditpact0002);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCreditContract_Insert";
    }

    /***
     * 新增
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insert")
    public String insert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcreditpact0002 = formService.getFormData("creditpact0002");
        getFormValue(formcreditpact0002);
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        setObjValue(formcreditpact0002, mfCusCreditContract);
        mfCusCreditContractFeign.insert(mfCusCreditContract);
        getObjValue(formcreditpact0002, mfCusCreditContract);
        this.addActionMessage(model, "保存成功");
        @SuppressWarnings("unused")
        List <MfCusCreditContract> mfCusCreditContractList = (List <MfCusCreditContract>) mfCusCreditContractFeign.findByPage(this.getIpage(), mfCusCreditContract).getResult();
        model.addAttribute("formcreditpact0002", formcreditpact0002);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCreditContract_Insert";
    }

    /**
     * 查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String ajaxData, String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcreditpact0001 = formService.getFormData("creditpact0001");
        getFormValue(formcreditpact0001);
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setId(id);
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
        getObjValue(formcreditpact0001, mfCusCreditContract);
        model.addAttribute("formcreditpact0001", formcreditpact0001);
        model.addAttribute("query", "");
        return "/component/auth/MfCusCreditContract_Detail";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public String delete(Model model, String ajaxData, String id) throws Exception {
        ActionContext.initialize(request,
                response);
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setId(id);
        mfCusCreditContractFeign.delete(mfCusCreditContract);
        return getListPage(model);
    }

    /**
     * 新增校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/validateInsert")
    public void validateInsert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcreditpact0002 = formService.getFormData("creditpact0002");
        getFormValue(formcreditpact0002);
        boolean validateFlag = this.validateFormData(formcreditpact0002);
    }

    /**
     * 修改校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/validateUpdate")
    public void validateUpdate(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcreditpact0002 = formService.getFormData("creditpact0002");
        getFormValue(formcreditpact0002);
        boolean validateFlag = this.validateFormData(formcreditpact0002);
    }

    @RequestMapping(value = "/getCreditPactListAjax")
    @ResponseBody
    public Map <String, Object> getCreditPactListAjax(String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        try {
            String creditModel = request.getParameter("creditModel");
            String projectType = request.getParameter("projectType");
            mfCusCreditContract.setCusNo(cusNo);
            mfCusCreditContract.setCreditSts("1");
            mfCusCreditContract.setCreditModel(creditModel);
            mfCusCreditContract.setProjectType(projectType);
            List <MfCusCreditContract> mfCusCreditContractList = mfCusCreditContractFeign.getMfCusCreditContractList(mfCusCreditContract);
            List <MfCusCreditContract> mfCusCreditContractList1 = new ArrayList <MfCusCreditContract>();
            for (MfCusCreditContract mfCusCreditContract1 : mfCusCreditContractList) {
                String creditAppId = mfCusCreditContract1.getCreditAppId();
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setCreditAppId(creditAppId);
                List <MfCusCreditAdjustApply> mfCusCreditAdjustApplyList = mfCusCreditAdjustApplyFeign.getCreditAdjustApplyList(mfCusCreditAdjustApply);
                boolean flag = false;
                for (MfCusCreditAdjustApply mfCusCreditAdjustApply1 : mfCusCreditAdjustApplyList) {
                    String creditSts = mfCusCreditAdjustApply1.getCreditSts();
                    String wkfAppId = mfCusCreditAdjustApply1.getWkfAppId();
                    TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
                    if (task != null) {
                        flag = true;
                    }
                }
                if(!flag){
                    mfCusCreditContractList1.add(mfCusCreditContract1);
                }
            }
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            if (mfCusCreditContractList1 != null && mfCusCreditContractList1.size() > 0) {
                for (int i = 0; i < mfCusCreditContractList1.size(); i++) {
                    jsonObject = new JSONObject();
                    jsonObject.put("id", mfCusCreditContractList1.get(i).getId());
                    if (BizPubParm.CREDIT_MODEL_CUS.equals(creditModel)) {
                        jsonObject.put("name", mfCusCreditContractList1.get(i).getPactNo());
                    } else {
                        jsonObject.put("name", mfCusCreditContractList1.get(i).getProjectName());
                    }
                    jsonArray.add(jsonObject);
                }
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
            }
            dataMap.put("items", jsonArray.toString());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    @RequestMapping(value = "/getCreditPactListFinishedAjax")
    @ResponseBody
    public Map <String, Object> getCreditPactListFinishedAjax(String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        try {
            String creditModel = request.getParameter("creditModel");
            String projectType = request.getParameter("projectType");
            String kindNo = request.getParameter("kindNo");
            if(kindNo != null && "1005".equals(kindNo)){//非风险项目
                projectType = "2";
            }else{
                projectType = "1";
            }
            mfCusCreditContract.setCusNo(cusNo);
            mfCusCreditContract.setCreditSts("1");
            mfCusCreditContract.setCreditModel(creditModel);
            mfCusCreditContract.setProjectType(projectType);
            List <MfCusCreditContract> mfCusCreditContractList = mfCusCreditContractFeign.getMfCusCreditContractList(mfCusCreditContract);
            List <MfCusCreditContract> mfCusCreditContractList1 = new ArrayList <MfCusCreditContract>();
            for (MfCusCreditContract mfCusCreditContract1 : mfCusCreditContractList) {
                String creditAppId = mfCusCreditContract1.getCreditAppId();
                MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
                mfCusCreditAdjustApply.setCreditAppId(creditAppId);
                List <MfCusCreditAdjustApply> mfCusCreditAdjustApplyList = mfCusCreditAdjustApplyFeign.getCreditAdjustApplyList(mfCusCreditAdjustApply);
                boolean flag = false;
                for (MfCusCreditAdjustApply mfCusCreditAdjustApply1 : mfCusCreditAdjustApplyList) {
                    String creditSts = mfCusCreditAdjustApply1.getCreditSts();
                    String wkfAppId = mfCusCreditAdjustApply1.getWkfAppId();
                    TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
                    if (task != null) {
                        flag = true;
                    }
                }
                if(flag){
                    mfCusCreditContract1.setIfExistAdjustCrediting(BizPubParm.YES_NO_Y);
                }else{
                    mfCusCreditContract1.setIfExistAdjustCrediting(BizPubParm.YES_NO_N);
                }
                mfCusCreditContractList1.add(mfCusCreditContract1);
            }
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            if (mfCusCreditContractList1 != null && mfCusCreditContractList1.size() > 0) {
                for (int i = 0; i < mfCusCreditContractList1.size(); i++) {
                    jsonObject = new JSONObject();
                    jsonObject.put("id", mfCusCreditContractList1.get(i).getId());
                    if (BizPubParm.CREDIT_MODEL_CUS.equals(creditModel)) {
                        jsonObject.put("name", mfCusCreditContractList1.get(i).getPactNo());
                    } else {
                        jsonObject.put("name", mfCusCreditContractList1.get(i).getProjectName());
                    }
                    jsonArray.add(jsonObject);
                }
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
            }
            dataMap.put("items", jsonArray.toString());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    @RequestMapping(value = "/getCreditPactInfoByPactAjax")
    @ResponseBody
    public Map <String, Object> getCreditPactInfoByPactAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setId(id);
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
        if (mfCusCreditContract != null) {
            String creditAppId = mfCusCreditContract.getCreditAppId();
            MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
            mfCusCreditAdjustApply.setCreditAppId(creditAppId);
            List <MfCusCreditAdjustApply> mfCusCreditAdjustApplyList = mfCusCreditAdjustApplyFeign.getCreditAdjustApplyList(mfCusCreditAdjustApply);
            boolean flag = false;
            for (MfCusCreditAdjustApply mfCusCreditAdjustApply1 : mfCusCreditAdjustApplyList) {
                String creditSts = mfCusCreditAdjustApply1.getCreditSts();
                String wkfAppId = mfCusCreditAdjustApply1.getWkfAppId();
                TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
                if (task != null) {
                    flag = true;
                }
            }
            if(flag){
                dataMap.put("ifExistAdjustCrediting",BizPubParm.YES_NO_Y);
            }else{
                dataMap.put("ifExistAdjustCrediting",BizPubParm.YES_NO_N);
            }
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("mfCusCreditContract", mfCusCreditContract);
        return dataMap;
    }

    /**
     * 验证申请金额是否大于最高额合同额度、资金机构总额、资金机构项目额度 先校验是否大于最高额合同额度 不大于最高额合同额度情况下再去是否大于校验资金机构总额、资金机构项目额度
     *
     * @param creditPactId
     * @param creditProjectId
     * @param cusNoFund
     * @param appAmt
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkCreditPactAmtAjax")
    @ResponseBody
    public Map <String, Object> checkCreditPactAmtAjax(String creditPactId, String creditProjectId, String cusNoFund, double appAmt) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            dataMap = mfCusCreditContractFeign.checkCreditPactAmt(creditPactId, creditProjectId, cusNoFund, appAmt);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCusCreditContractDetailAjax")
    @ResponseBody
    public Map <String, Object> getCusCreditContractDetailAjax(String creditAppId, String formId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setCreditAppId(creditAppId);
        mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
        FormData formcusCreditContractDetail = null;
                String query = "";
        if (mfCusCreditContract != null) {
            MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
            mfCusCreditConfig.setCreditId(mfCusCreditContract.getTemplateCreditId());
            mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
            if(mfCusCreditConfig!=null&&BizPubParm.BUS_MODEL_12.equals(mfCusCreditConfig.getBusModel())){
                formId = "cusCreditContractDetail25";
            }
            formcusCreditContractDetail = formService.getFormData(formId);
            dataMap.put("pactSign", "1");
            String cusNo = mfCusCreditContract.getCusNo();
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(cusNo);
            MfCusCustomer cusCustomer = cusCustomerFeign.getById(mfCusCustomer);
            if (cusCustomer != null) {
                mfCusCreditContract.setCusMngName(cusCustomer.getCusMngName());
            }
            query = cusCustomerFeign.validateCreditCusFormModify(mfCusCreditContract.getCusNo(),creditAppId,BizPubParm.FORM_EDIT_FLAG_BUS,User.getRegNo(request),"2");
        } else {
            dataMap.put("pactSign", "0");
        }
        request.setAttribute("ifBizManger", "3");
        getObjValue(formcusCreditContractDetail, mfCusCreditContract);
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        if(query==null){
            query="query";
        }
        dataMap.put("query", query);
        String htmlStr = jsonFormUtil.getJsonStr(formcusCreditContractDetail, "propertySeeTag", query);
        dataMap.put("cusCreditContractDetail", htmlStr);
        return dataMap;
    }

    /**
     * 根据客户号和授信模式查询客户是否授信
     *
     * @return
     * @throws Exception String
     * @author 段泽宇
     * @date 2018-7-16 下午22:31:16
     */
    @RequestMapping(value = "/checkCreditByCusNoAjax")
    @ResponseBody
    public Map <String, Object> checkCreditByCusNoAjax(String cusNo, String appId, String baseType) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {

            MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
            dataMap = creditApplyInterfaceFeign.checkCredit(mfBusApply, baseType, cusNo);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }


    /**
     * AJAX获取查看    银行借款合同的登记日期在授信合同结束之前
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByPactIdAjax")
    @ResponseBody
    public Map <String, Object> getByPactIdAjax(String ajaxData, String pactId) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setId(pactId);
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);
        if (mfCusCreditContract != null) {
            dataMap.put("flag", "success");
            dataMap.put("mfCusCreditContract", mfCusCreditContract);
        } else {
            dataMap.put("flag", "error");
        }
        return dataMap;
    }


    /**
     * 获得授信到期预警列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCreditExpiresPage")
    public String getCreditExpiresPage(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);

        String cuslendDays = "0";
        PliWarning querypliWarning = new PliWarning();
        querypliWarning.setPliWarnNo("PLI_CREDIT_EXPIRES");
        PliWarning pliWarning = pliWarningFeign.getById(querypliWarning);//获得授信到期预警
        if (null != pliWarning) {
            if ("1".equals(pliWarning.getFlag())) {
                cuslendDays = String.valueOf(pliWarning.getPliDays());
            }
        }

        model.addAttribute("cuslendDays", cuslendDays);
        model.addAttribute("query", "");
        return "/component/auth/MfCreditExpires_List";

    }

    /**
     * 获得授信到期预警列表数据请求
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getCreditExpiresAjax")
    @ResponseBody
    public Map <String, Object> getCreditExpiresAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                     String ajaxData, Ipage ipage) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();

        String cuslendDays = request.getParameter("cuslendDays");// 提前几天预警
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        // 根据预警天数计算---结束日期
        String endDate = DateUtil.getDate();
        if (StringUtil.isNotEmpty(endDate)) {
            endDate = DateUtil.addByDay(Integer.parseInt(cuslendDays));
        }
        try {
            mfCusCreditContract.setBeginDate(DateUtil.getDate());
            mfCusCreditContract.setEndDate(endDate);

            mfCusCreditContract.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusCreditContract.setCriteriaList(mfCusCreditContract, ajaxData);// 我的筛选
            mfCusCreditContract.setCustomSorts(ajaxData);
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            Map <String, Object> paramMap = new HashMap <String, Object>();
            paramMap.put("mfCusCreditContract", mfCusCreditContract);

            ipage.setParams(this.setIpageParams("paramMap", paramMap));
            // 自定义查询Bo方法
            ipage = mfCusCreditContractFeign.getCreditExpireByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取授信到期列表失败"));
        }
        return dataMap;
    }

    /**
     * 查询该客户和满足适配产品的所有有效 已完成的授信合同
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCreditContractAllByCus")
    public String getCreditContractAllByCus(Model model,String cusNo, String kindNo)throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("adaptationKindNo", kindNo);
    return "/component/cus/MfCusCreditContract_ListForSelect";
    }


    /***
     *进件申请中  合同查询
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByPage")
    @ResponseBody
    public Map <String, Object> findByPage(String adaptationKindNo,String cusNo,Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map <String, Object> dataMap = new HashMap <String, Object>();

        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
        try {
            mfCusCreditConfig.setAdaptationKindNo(adaptationKindNo);
            List<MfCusCreditConfig>  creditConfigList =  mfCusCreditConfigFeign.getCredtByAdaptationKindNo(mfCusCreditConfig);
            //自定义查询条件
            mfCusCreditContract.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusCreditContract.setCriteriaList(mfCusCreditContract, ajaxData);//我的筛选

            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            List<String > list = new ArrayList<>();
            for (int i=0;i<creditConfigList.size();i++){
                mfCusCreditContract.setTemplateCreditId(creditConfigList.get(i).getCreditId());
                list.add(creditConfigList.get(i).getCreditId());
            }
            Map <String, Object> paramMap = new HashMap <String, Object>();
            mfCusCreditContract.setCusNo(cusNo);
            mfCusCreditContract.setTemplateCreditIdList(list);
            paramMap.put("mfCusCreditContract", mfCusCreditContract);
            ipage.setParams(this.setIpageParams("paramMap", paramMap));
            ipage = mfCusCreditContractFeign.getContractListByPage(ipage);
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
     * 根据idhuoq选择的授信合同
     * @return
     */
    @ResponseBody
    @RequestMapping("/getByChooseIdAjax")
   public Map <String, Object> getByChooseIdAjax(String id) throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusCreditContract mfCusCreditContract =  new MfCusCreditContract();
        mfCusCreditContract.setId(id);
        mfCusCreditContract =  mfCusCreditContractFeign.getById(mfCusCreditContract);

        if(mfCusCreditContract!=null){
            if(StringUtil.isNotEmpty(mfCusCreditContract.getCreVouType())){
                Map<String,String > map = new CodeUtils().getMapByKeyName("VOU_TYPE");
                mfCusCreditContract.setVouTypeName(map.get(mfCusCreditContract.getCreVouType()));
            }
            dataMap.put("flag", "success");
            dataMap.put("contractInfo", mfCusCreditContract);
        }else{
            dataMap.put("flag", "error");
        }
        return dataMap;
   }

}
