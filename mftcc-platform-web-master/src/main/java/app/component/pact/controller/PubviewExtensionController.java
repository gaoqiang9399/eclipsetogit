package app.component.pact.controller;

import app.component.app.entity.MfBusAppKind;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.common.BizPubParm;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.extension.entity.MfBusExtensionResultDetail;
import app.component.pact.extension.feign.MfBusExtensionResultDetailFeign;
import app.component.pact.feign.MfBusFincAppFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.MathExtend;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pubviewExtension")
public class PubviewExtensionController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private MfBusExtensionResultDetailFeign mfBusExtensionResultDetailFeign;

    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;

    @Autowired
    private MfBusAppKindFeign mfBusAppKindFeign;

    /**
     * 展期列表
     * @param fincId
     * @param pageNo
     * @param tableId
     * @param tableType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getExtensionListAjax")
    @ResponseBody
    public Map<String, Object> getExtensionListAjax(String fincId, Integer pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        try {
            MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
            mfBusExtensionResultDetail.setFincId(fincId);

            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            mfBusFincApp.setFincId(fincId);
            mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

            MfBusAppKind mfBusAppKind = new MfBusAppKind();
            mfBusAppKind.setAppId(mfBusFincApp.getAppId());
            mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);

            // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
            // 处理期限的展示单位。
            Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
            String termUnit = termTypeMap.get(mfBusFincApp.getTermType()).getRemark();

            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数

            ipage.setParams(this.setIpageParams("mfBusExtensionResultDetail", mfBusExtensionResultDetail));
            ipage = mfBusExtensionResultDetailFeign.findByPage(ipage);

            List<Map<String, Object>> list = (List<Map<String, Object>>) ipage.getResult();
            for (Map<String, Object> bean : list) {

                String extenFincRate = String.valueOf(MathExtend.showRateMethod(String.valueOf(bean.get("rateType")), (Double) bean.get("extenFincRate"), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
                extenFincRate = MathExtend.round(extenFincRate, Integer.parseInt(mfBusAppKind.getRateDecimalDigits()));
                bean.put("extenFincRate", extenFincRate + rateUnit);
                String extenOverRate = String.valueOf(MathExtend.showRateMethod(String.valueOf(bean.get("rateType")), (Double) bean.get("extenOverRate"), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
                extenOverRate = MathExtend.round(extenOverRate, Integer.parseInt(mfBusAppKind.getRateDecimalDigits()));
                bean.put("extenOverRate", extenOverRate + rateUnit);
                String extenCmpdRate = String.valueOf(MathExtend.showRateMethod(String.valueOf(bean.get("rateType")), (Double) bean.get("extenCmpdRate"), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
                extenCmpdRate = MathExtend.round(extenCmpdRate, Integer.parseInt(mfBusAppKind.getRateDecimalDigits()));
                bean.put("extenCmpdRate", extenCmpdRate + rateUnit);

                String extenOverFloat = bean.get("extenOverFloat").toString();
                extenOverFloat = MathExtend.round(extenOverFloat, Integer.parseInt(mfBusAppKind.getRateDecimalDigits()));
                bean.put("extenOverFloat", extenOverFloat + rateUnit);
                String extenCmpdFloat = bean.get("extenCmpdFloat").toString();
                extenCmpdFloat = MathExtend.round(extenCmpdFloat, Integer.parseInt(mfBusAppKind.getRateDecimalDigits()));
                bean.put("extenCmpdFloat", extenCmpdFloat + rateUnit);

                //处理单位
                bean.put("extenTerm", bean.get("extenTerm") + termUnit);

                //处理   展期状态
                String extenBusStage = bean.get("extenBusStage").toString();
                if (BizPubParm.EXTENSION_BUS_STAGE_COMPLATE.equals(extenBusStage)) {
                    bean.put("extenBusStage", "展期通过");
                } else if (BizPubParm.EXTENSION_BUS_STAGE_DISAGREE.equals(extenBusStage)) {
                    bean.put("extenBusStage", "展期否决");
                } else {
                    bean.put("extenBusStage", "展期申请中");
                }

            }

            //判断是否有展期列表
            int pageSum = ipage.getPageSum();
            dataMap.put("pageSum", pageSum);
            String tableHtml = new JsonTableUtil().getJsonStr(tableId, "tableTag", (List) list, ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("htmlStr", tableHtml);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }

        return dataMap;
    }

    // 列表展示详情，单字段编辑
    @RequestMapping(value = "/listShowDetailAjax")
    @ResponseBody
    public Map<String, Object> listShowDetailAjax(String resultDetailId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);

        MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
        mfBusExtensionResultDetail.setResultDetailId(resultDetailId);
        mfBusExtensionResultDetail = mfBusExtensionResultDetailFeign.getById(mfBusExtensionResultDetail);

        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincId(mfBusExtensionResultDetail.getFincId());
        mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

        // MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusFamilyInfoAction");
        // String formId = mfCusFormConfig.getShowModelDef();
        String formId = "extensionListDetail";
        FormData formData = formService.getFormData(formId);

        if ("2".equals(mfBusExtensionResultDetail.getRateType())) {
            Double extenFincRate = MathExtend.divide(mfBusExtensionResultDetail.getExtenFincRate(), 1.2, 6);
            mfBusExtensionResultDetail.setExtenFincRate(extenFincRate);

            Double extenOverRate = MathExtend.divide(mfBusExtensionResultDetail.getExtenOverRate(), 1.2, 6);
            mfBusExtensionResultDetail.setExtenOverRate(extenOverRate);

            Double extenCmpdRate = MathExtend.divide(mfBusExtensionResultDetail.getExtenCmpdRate(), 1.2, 6);
            mfBusExtensionResultDetail.setExtenCmpdRate(extenCmpdRate);
        }

        getObjValue(formData, mfBusExtensionResultDetail);

        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
        this.changeFormProperty(formData, "extenFincRate", "unit", rateUnit);
        this.changeFormProperty(formData, "extenOverRate", "unit", rateUnit);
        this.changeFormProperty(formData, "extenCmpdRate", "unit", rateUnit);

        // 处理期限的展示单位。
        Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
        String termUnit = termTypeMap.get(mfBusFincApp.getTermType()).getRemark();
        this.changeFormProperty(formData, "extenTerm", "unit", termUnit);

        String htmlStrCorp = new JsonFormUtil().getJsonStr(formData, "propertySeeTag", "");

        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (mfBusExtensionResultDetail != null) {
            dataMap.put("formHtml", htmlStrCorp);
            dataMap.put("flag", "success");
        } else {
            dataMap.put("msg", "获取详情失败");
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", mfBusExtensionResultDetail);

        return dataMap;
    }

}
