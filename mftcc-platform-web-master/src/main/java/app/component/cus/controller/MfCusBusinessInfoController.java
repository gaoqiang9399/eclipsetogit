package app.component.cus.controller;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mfCusBusinessInfo")
public class MfCusBusinessInfoController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private MfCusFormConfigFeign mfCusFormConfigFeign;

    /**
     * 新增页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/input")
    public String input(Model model, String cusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
        mfCusCorpBaseInfo.setCusNo(cusNo);
        mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                "MfCusBusinessInfoAction");
        String formId = "cusBusinessInfoBase";
        if (mfCusFormConfig != null) {
            formId = mfCusFormConfig.getAddModelDef();
        }
        FormData formcusBusinessInfoBase = formService.getFormData(formId);
        getFormValue(formcusBusinessInfoBase);
        mfCusCorpBaseInfo.setCusName(mfCusCustomer.getCusName());
        getObjValue(formcusBusinessInfoBase, mfCusCustomer);
        getObjValue(formcusBusinessInfoBase, mfCusCorpBaseInfo);
        model.addAttribute("formcusBusinessInfoBase", formcusBusinessInfoBase);
        model.addAttribute("query", "");
        return "/component/cus/MfCusBusinessInfo_Insert";
    }
    @RequestMapping("/getById")
    public String getById(Model model, String cusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
        mfCusCorpBaseInfo.setCusNo(cusNo);
        mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                "MfCusBusinessInfoAction");
        String formId = "cusBusinessInfoBase";
        if (mfCusFormConfig != null) {
            formId = mfCusFormConfig.getAddModelDef();
        }
        FormData formcusBusinessInfoBase = formService.getFormData(formId);
        getFormValue(formcusBusinessInfoBase);
        mfCusCorpBaseInfo.setCusName(mfCusCustomer.getCusName());
        getObjValue(formcusBusinessInfoBase, mfCusCustomer);
        getObjValue(formcusBusinessInfoBase, mfCusCorpBaseInfo);
        model.addAttribute("formcusBusinessInfoBase", formcusBusinessInfoBase);
        model.addAttribute("query", "");
        return "/component/cus/MfCusBusinessInfo_Insert";
    }
    /**
     * AJAX新增
     *
     * @param ajaxData
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
            String formId = (String) map.get("formId");
            if (StringUtil.isEmpty(formId)) {
                formId = mfCusFormConfigFeign.getByCusType("base", "MfCusBusinessInfoAction").getAddModel();
            }
            FormData formcusBusinessInfoBase = formService.getFormData(formId);
            getFormValue(formcusBusinessInfoBase, getMapByJson(ajaxData));
            if (this.validateFormData(formcusBusinessInfoBase)) {
                MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
                setObjValue(formcusBusinessInfoBase, mfCusCorpBaseInfo);
                mfCusCorpBaseInfo.setCompanyInfoType(BizPubParm.COMPANY_INFO_TYPE_2);
                if(mfCusCorpBaseInfo.getWayClass().length()<5){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "行业需要选到第四级！");
                    return dataMap;
                }
                mfCusCorpBaseInfo =  mfCusCorpBaseInfoFeign.update(mfCusCorpBaseInfo);
                String cusNo = mfCusCorpBaseInfo.getCusNo();
                String relNo = mfCusCorpBaseInfo.getCusNo();
                request.setAttribute("ifBizManger", "3");
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusCorpBaseInfo.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusBusinessInfoAction");
                formId = "cusBusinessInfoDetail";
                formId = mfCusFormConfig.getShowModelDef();
                FormData formcusBusinessInfoDetail = formService.getFormData(formId);
                getFormValue(formcusBusinessInfoDetail);
                getObjValue(formcusBusinessInfoDetail, mfCusCorpBaseInfo);
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formcusBusinessInfoDetail, "propertySeeTag", "");
                String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo, relNo);// 更新资料完整度
                dataMap.put("mfCusCorpBaseInfo", mfCusCorpBaseInfo);
                dataMap.put("htmlStr", htmlStr);
                dataMap.put("DataFullFlag", "1");
                dataMap.put("infIntegrity", infIntegrity);
                dataMap.put("htmlStrFlag", "1");
                 dataMap.put("flag", "success");
                dataMap.put("msg", "新增成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw new Exception(e.getMessage());
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
        String cusNo = (String) getMapByJson(ajaxData).get("cusNo");
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        String formId = (String) getMapByJson(ajaxData).get("formId");
        if (StringUtil.isEmpty(formId)) {
            formId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusBusinessInfoAction")
                    .getShowModelDef();
        }
        FormData formcusBusinessInfoDetail = formService.getFormData(formId);
        getFormValue(formcusBusinessInfoDetail, getMapByJson(ajaxData));
        MfCusCorpBaseInfo mfCusCorpBaseInfoJsp = new MfCusCorpBaseInfo();
        setObjValue(formcusBusinessInfoDetail, mfCusCorpBaseInfoJsp);
        if(null != mfCusCorpBaseInfoJsp && null != mfCusCorpBaseInfoJsp.getWayClass() && !"".equals( mfCusCorpBaseInfoJsp.getWayClass().trim())){
            if(mfCusCorpBaseInfoJsp.getWayClass().length()<5){
                dataMap.put("flag", "error");
                dataMap.put("msg", "行业需要选到第四级！");
                return dataMap;
            }
        }
        MfCusCorpBaseInfo mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfoJsp);
        if (mfCusCorpBaseInfo != null) {
            try {
                mfCusCorpBaseInfo = (MfCusCorpBaseInfo) EntityUtil.reflectionSetVal(mfCusCorpBaseInfo,
                        mfCusCorpBaseInfoJsp, getMapByJson(ajaxData));
                PropertyUtils.copyProperties(mfCusCustomer, mfCusCorpBaseInfo);
                mfCusCorpBaseInfo.setCompanyInfoType(BizPubParm.COMPANY_INFO_TYPE_2);
                mfCusCorpBaseInfoFeign.update(mfCusCorpBaseInfo);
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

}