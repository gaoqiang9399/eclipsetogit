package app.component.stamp.controller;

import app.base.User;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.stamp.entity.MfStampCreditRegist;
import app.component.stamp.feign.MfStampCreditRegistFeign;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
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
@RequestMapping("/mfStampCreditRegist")
public class MfStampCreditRegistController extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private MfStampCreditRegistFeign mfStampCreditRegistFeign;
    @Autowired
    private MfCusCreditContractFeign mfCusCreditContractFeign;
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
        MfStampCreditRegist mfStampCreditRegist = new MfStampCreditRegist();
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formstampcreditregistedit = formService.getFormData("stampcreditregistedit");
        MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
        mfCusCreditContract.setId(id);
        mfCusCreditContract = mfCusCreditContractFeign.getById(mfCusCreditContract);

        mfStampCreditRegist.setContractId(mfCusCreditContract.getId());
        mfStampCreditRegist.setCusNo(mfCusCreditContract.getCusNo());
        mfStampCreditRegist.setCusName(mfCusCreditContract.getCusName());
        mfStampCreditRegist.setCreditPactNo(mfCusCreditContract.getCreditPactNo());
        //合作银行
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(mfCusCreditContract.getCreditAppId());
        mfCusCreditApply = creditApplyInterfaceFeign.getCusCreditApply(mfCusCreditApply);
        mfStampCreditRegist.setAgenciesId(mfCusCreditApply.getAgenciesId());
        mfStampCreditRegist.setAgenciesName(mfCusCreditApply.getAgenciesName());
        mfStampCreditRegist.setKindNo(mfCusCreditContract.getKindNo());
        //产品信息
        MfSysKind mfSysKind = new MfSysKind();
        mfSysKind.setKindNo(mfCusCreditContract.getKindNo());
        MfSysKind sysKind = mfSysKindFeign.getById(mfSysKind);
        if (sysKind != null) {
            mfStampCreditRegist.setKindName(sysKind.getKindName());
        }
        mfStampCreditRegist.setCreditSum(mfCusCreditContract.getCreditSum());
        mfStampCreditRegist.setCreditTerm(mfCusCreditContract.getCreditTerm());

        getObjValue(formstampcreditregistedit, mfStampCreditRegist);
        model.addAttribute("formstampcreditregistedit", formstampcreditregistedit);
        model.addAttribute("query", "");
        return "/component/stamp/MfStampCreditRegist_Insert";
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
        MfStampCreditRegist mfStampCreditRegist = new MfStampCreditRegist();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formstampcreditregistedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formstampcreditregistedit, getMapByJson(ajaxData));
            if (this.validateFormData(formstampcreditregistedit)) {
                setObjValue(formstampcreditregistedit, mfStampCreditRegist);
                mfStampCreditRegist.setOpNo(User.getRegNo(request));
                mfStampCreditRegist.setOpName(User.getRegName(request));
                mfStampCreditRegist.setBrNo(User.getOrgNo(request));
                mfStampCreditRegist.setBrName(User.getOrgName(request));
                dataMap.put("mfStampCreditRegist", mfStampCreditRegist);
                mfStampCreditRegist = mfStampCreditRegistFeign.insert(dataMap);
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
