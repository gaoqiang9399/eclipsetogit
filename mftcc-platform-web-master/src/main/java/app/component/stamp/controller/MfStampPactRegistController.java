package app.component.stamp.controller;

import app.base.User;
import app.component.auth.entity.MfCusCreditContract;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.stamp.entity.MfStampCredit;
import app.component.stamp.entity.MfStampPact;
import app.component.stamp.entity.MfStampPactRegist;
import app.component.stamp.feign.MfStampPactFeign;
import app.component.stamp.feign.MfStampPactRegistFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;
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
@RequestMapping("/mfStampPactRegist")
public class MfStampPactRegistController extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private MfStampPactFeign mfStampPactFeign;
    @Autowired
    private MfStampPactRegistFeign mfStampPactRegistFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfSysKindFeign mfSysKindFeign;
    @Autowired
    private CreditApplyInterfaceFeign creditApplyInterfaceFeign;

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String id) throws Exception {
        MfStampPactRegist mfStampPactRegist = new MfStampPactRegist();
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formstamppactregistedit = formService.getFormData("stamppactregistedit");
        MfStampPact mfStampPact = new MfStampPact();
        mfStampPact.setId(id);
        mfStampPact = mfStampPactFeign.getById(mfStampPact);
        PropertyUtils.copyProperties(mfStampPactRegist, mfStampPact);
        mfStampPactRegist.setStampId(id);
        mfStampPactRegist.setOpNo(User.getRegNo(request));
        mfStampPactRegist.setOpName(User.getRegName(request));
        mfStampPactRegist.setBrNo(User.getOrgNo(request));
        mfStampPactRegist.setBrName(User.getOrgName(request));
        getObjValue(formstamppactregistedit, mfStampPactRegist);
        model.addAttribute("formstamppactregistedit", formstamppactregistedit);
        model.addAttribute("query", "");
        return "/component/stamp/MfStampPactRegist_Insert";
    }
    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail")
    public String detail(Model model, String id) throws Exception {
        MfStampPactRegist mfStampPactRegist = new MfStampPactRegist();
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formstamppactregistedit = formService.getFormData("stamppactregistedit");
        mfStampPactRegist.setStampId(id);
        mfStampPactRegist = mfStampPactRegistFeign.getById(mfStampPactRegist);
        getObjValue(formstamppactregistedit, mfStampPactRegist);
        model.addAttribute("formstamppactregistedit", formstamppactregistedit);
        model.addAttribute("query", "");
        return "/component/stamp/MfStampPactRegist_Detail";
    }
    /**
     * 新增用印
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
        MfStampPactRegist mfStampPactRegist = new MfStampPactRegist();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formstamppactregistedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formstamppactregistedit, getMapByJson(ajaxData));
            if (this.validateFormDataAnchor(formstamppactregistedit)) {
                setObjValue(formstamppactregistedit, mfStampPactRegist);
                dataMap.put("mfStampPactRegist", mfStampPactRegist);
                mfStampPactRegist = mfStampPactRegistFeign.insert(dataMap);
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
}
