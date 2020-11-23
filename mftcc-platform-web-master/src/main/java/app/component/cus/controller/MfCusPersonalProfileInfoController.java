package app.component.cus.controller;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
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
@RequestMapping("/mfCusPersonalProfileInfo")
public class MfCusPersonalProfileInfoController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
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
        MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
        mfCusPersBaseInfo.setCusNo(cusNo);
        mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                "MfCusPersonalProfileInfoAction");
        String formId = "cusPersonalProfileInfoBase";
        if (mfCusFormConfig != null) {
            formId = mfCusFormConfig.getAddModelDef();
        }
        FormData formCusPersonalProfileInfoBase = formService.getFormData(formId);
        getFormValue(formCusPersonalProfileInfoBase);
        mfCusPersBaseInfo.setCusName(mfCusCustomer.getCusName());
        getObjValue(formCusPersonalProfileInfoBase, mfCusPersBaseInfo);
        model.addAttribute("formCusPersonalProfileInfoBase", formCusPersonalProfileInfoBase);
        model.addAttribute("query", "");
        return "/component/cus/MfCusPersonalProfileInfo_Insert";
    }

    @RequestMapping("/getById")
    public String getById(Model model, String cusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
        mfCusPersBaseInfo.setCusNo(cusNo);
        mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                "MfCusPersonalProfileInfoAction");
        String formId = "cusPersonalProfileInfoBase";
        if (mfCusFormConfig != null) {
            formId = mfCusFormConfig.getAddModelDef();
        }
        FormData formCusPersonalProfileInfoBase = formService.getFormData(formId);
        getFormValue(formCusPersonalProfileInfoBase);
        mfCusPersBaseInfo.setCusName(mfCusCustomer.getCusName());
        getObjValue(formCusPersonalProfileInfoBase, mfCusPersBaseInfo);
        model.addAttribute("formCusPersonalProfileInfoBase", formCusPersonalProfileInfoBase);
        model.addAttribute("query", "");
        return "/component/cus/MfCusPersonalProfileInfo_Detail";
    }

    /**
     * AJAX新增
     *
     * @param startFlowFlag
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
                formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonalProfileInfoAction").getAddModel();
            }
            FormData formCusPersonalProfileInfoBase = formService.getFormData(formId);
            getFormValue(formCusPersonalProfileInfoBase, getMapByJson(ajaxData));
            if (this.validateFormData(formCusPersonalProfileInfoBase)) {
                MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
                setObjValue(formCusPersonalProfileInfoBase, mfCusPersBaseInfo);
                mfCusPersBaseInfo.setPersonInfoType(BizPubParm.PERSON_INFO_TYPE_2);
                mfCusPersBaseInfoFeign.update(mfCusPersBaseInfo);
                String cusNo = mfCusPersBaseInfo.getCusNo();
                String relNo = mfCusPersBaseInfo.getCusNo();
                request.setAttribute("ifBizManger", "3");
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusPersBaseInfo.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusPersonalProfileInfoAction");
                formId = mfCusFormConfig.getShowModelDef();
                FormData formCusPersonalProfileInfoDetail = formService.getFormData(formId);
                getFormValue(formCusPersonalProfileInfoDetail);
                getObjValue(formCusPersonalProfileInfoDetail, mfCusPersBaseInfo);
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formCusPersonalProfileInfoDetail, "propertySeeTag", "");
                String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo, relNo);// 更新资料完整度
                dataMap.put("mfCusPersBaseInfo", mfCusPersBaseInfo);
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
            formId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonalProfileInfoAction")
                    .getShowModelDef();
        }
        FormData formCusPersonalProfileInfoDetail = formService.getFormData(formId);
        getFormValue(formCusPersonalProfileInfoDetail, getMapByJson(ajaxData));
        MfCusPersBaseInfo mfCusPersBaseInfoJsp = new MfCusPersBaseInfo();
        setObjValue(formCusPersonalProfileInfoDetail, mfCusPersBaseInfoJsp);
        MfCusPersBaseInfo mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfoJsp);
        if (mfCusPersBaseInfo != null) {
            try {
                mfCusPersBaseInfo = (MfCusPersBaseInfo) EntityUtil.reflectionSetVal(mfCusPersBaseInfo,
                        mfCusPersBaseInfoJsp, getMapByJson(ajaxData));
                PropertyUtils.copyProperties(mfCusCustomer, mfCusPersBaseInfo);
                mfCusPersBaseInfo.setPersonInfoType(BizPubParm.PERSON_INFO_TYPE_2);
                mfCusPersBaseInfoFeign.update(mfCusPersBaseInfo);
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
     * AJAX修改
     *
     * @param startFlowFlag
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

            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            if (StringUtil.isEmpty(formId)) {
                formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonalProfileInfoAction").getAddModel();
            }
            FormData formCusPersonalProfileInfoBase = formService.getFormData(formId);
            getFormValue(formCusPersonalProfileInfoBase, getMapByJson(ajaxData));
            if (this.validateFormData(formCusPersonalProfileInfoBase)) {
                MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
                setObjValue(formCusPersonalProfileInfoBase, mfCusPersBaseInfo);
                mfCusPersBaseInfo.setPersonInfoType(BizPubParm.PERSON_INFO_TYPE_2);
                mfCusPersBaseInfoFeign.update(mfCusPersBaseInfo);
                String cusNo = mfCusPersBaseInfo.getCusNo();
                String relNo = mfCusPersBaseInfo.getCusNo();
                request.setAttribute("ifBizManger", "3");
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusPersBaseInfo.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusPersonalProfileInfoAction");
                formId = mfCusFormConfig.getShowModelDef();
                FormData formCusPersonalProfileInfoDetail = formService.getFormData(formId);
                getFormValue(formCusPersonalProfileInfoDetail);
                getObjValue(formCusPersonalProfileInfoDetail, mfCusPersBaseInfo);
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formCusPersonalProfileInfoDetail, "propertySeeTag", "");
                String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo, relNo);// 更新资料完整度
                dataMap.put("mfCusPersBaseInfo", mfCusPersBaseInfo);
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

}
