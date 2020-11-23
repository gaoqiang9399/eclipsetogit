package app.component.cus.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusDesignatedRecipient;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusDesignatedRecipientFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
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

/**
 * Title: MfCusDesignatedRecipientController.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Thu Apr 11 14:24:30 CST 2019
 **/
@Controller
@RequestMapping("/mfCusDesignatedRecipient")
public class MfCusDesignatedRecipientController extends BaseFormBean {
    private FormService formService = new FormService();

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;

    @Autowired
    private MfCusFormConfigFeign mfCusFormConfigFeign;

    @Autowired
    private MfCusDesignatedRecipientFeign mfCusDesignatedRecipientFeign;

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);

        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusDesignatedRecipientAction");
        String formId = "";
        if (mfCusFormConfig != null) {
            formId = mfCusFormConfig.getAddModelDef();
        }
        FormData formData = formService.getFormData(formId);

        MfCusDesignatedRecipient recipient = new MfCusDesignatedRecipient();
        recipient.setRelNo(cusNo);

        getObjValue(formData, recipient);

        model.addAttribute("cusNo", cusNo);
        model.addAttribute("formData", formData);
        model.addAttribute("query", "");

        return "/component/cus/MfCusDesignatedRecipient_Insert";
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
        ActionContext.initialize(request, response);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map ajaxDataMap = getMapByJson(ajaxData);

            FormData formData = formService.getFormData(ajaxDataMap.get("formId").toString());
            getFormValue(formData, ajaxDataMap);

            if (this.validateFormData(formData)) {
                MfCusDesignatedRecipient recipient = new MfCusDesignatedRecipient();
                setObjValue(formData, recipient);
                mfCusDesignatedRecipientFeign.insert(recipient);

                recipient = new MfCusDesignatedRecipient();
                recipient.setRelNo(ajaxDataMap.get("relNo").toString());
                recipient.setIsDelete("0");

                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfCusDesignatedRecipient", recipient));
                String tableHtml = new JsonTableUtil().getJsonStr("tablerecipientListBase", "tableTag", (List<MfCusDesignatedRecipient>) mfCusDesignatedRecipientFeign.findByPage(ipage).getResult(), null, true);

                dataMap.put("htmlStr", tableHtml);
                dataMap.put("htmlStrFlag", "1");
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");

            Map<String, String> parmMap = new HashMap<String, String>();
            parmMap.put("operation", "保存");
            parmMap.put("reason", e.getMessage());
            dataMap.put("msg", MessageEnum.FAILED_OPERATION_CONTENT.getMessage(parmMap));
        }
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
    public Map<String, Object> deleteAjax(String serialNo) throws Exception {
        ActionContext.initialize(request, response);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCusDesignatedRecipient recipient = new MfCusDesignatedRecipient();
            recipient.setSerialNo(serialNo);
            recipient.setIsDelete("1");// 软删除
            mfCusDesignatedRecipientFeign.update(recipient);

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

    // 列表展示详情，单字段编辑
    @RequestMapping("/listShowDetailAjax")
    @ResponseBody
    public Map<String, Object> listShowDetailAjax(String serialNo, String relNo) throws Exception {
        ActionContext.initialize(request, response);

        this.getHttpRequest().setAttribute("ifBizManger", "3");// 允许单字段编辑

        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(relNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusDesignatedRecipientAction");
        String formId = "";
        if (mfCusFormConfig != null) {
            formId = mfCusFormConfig.getShowModelDef();
        }

        MfCusDesignatedRecipient recipient = new MfCusDesignatedRecipient();
        recipient.setSerialNo(serialNo);
        recipient = mfCusDesignatedRecipientFeign.getBean(recipient);

        FormData formData = formService.getFormData(formId);
        getObjValue(formData, recipient);
        String htmlStrCorp = new JsonFormUtil().getJsonStr(formData, "propertySeeTag", "");

        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (recipient != null) {
            dataMap.put("formHtml", htmlStrCorp);
            dataMap.put("flag", "success");
        } else {
            dataMap.put("msg", "获取详情失败");
            dataMap.put("flag", "error");
        }

        // dataMap.put("formData", mfOaArchivesFamily);

        return dataMap;
    }

    /**
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateByOneAjax")
    @ResponseBody
    public Map<String, Object> updateByOneAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);

        Map ajaxDataMap = getMapByJson(ajaxData);
        String formId = (String) ajaxDataMap.get("formId");

        FormData formcusgoods0003 = formService.getFormData(formId);
        getFormValue(formcusgoods0003, ajaxDataMap);

        MfCusDesignatedRecipient recipientNew = new MfCusDesignatedRecipient();
        setObjValue(formcusgoods0003, recipientNew);

        MfCusDesignatedRecipient recipient = new MfCusDesignatedRecipient();
        recipient.setSerialNo(recipientNew.getSerialNo());
        recipient = mfCusDesignatedRecipientFeign.getBean(recipient);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (recipient != null) {
            try {
                recipient = (MfCusDesignatedRecipient) EntityUtil.reflectionSetVal(recipient, recipientNew, ajaxDataMap);
                mfCusDesignatedRecipientFeign.update(recipient);

                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
                throw new Exception(e.getMessage());
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
        }

        return dataMap;
    }
}
