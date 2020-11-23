package app.component.finance.manage.controller;

import app.base.User;
import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.compensatory.feign.MfBusCompensatoryApplyFeign;
import app.component.finance.manage.entity.CwBillManage;
import app.component.finance.manage.entity.CwRecourseConfim;
import app.component.finance.manage.feign.CwRecourseConfimFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.recourse.entity.MfBusRecourseApply;
import app.component.recourse.feign.MfBusRecourseApplyFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cwRecourseConfim")
public class CwRecourseConfimController extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private CwRecourseConfimFeign cwRecourseConfimFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfBusRecourseApplyFeign mfBusRecourseApplyFeign;
    @Autowired
    private MfBusCompensatoryApplyFeign mfBusCompensatoryApplyFeign;

    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/finance/manage/CwRecourseConfim_List";
    }

    @RequestMapping(value = "/findPactListByAjax")
    @ResponseBody
    public Map<String, Object> findPactListByAjax(Integer pageNo, Integer pageSize, String tableId,
                                                  String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
        try {
            mfBusRecourseApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusRecourseApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusRecourseApply.setCriteriaList(mfBusRecourseApply, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusRecourseApply", mfBusRecourseApply));
            ipage = cwRecourseConfimFeign.findListByAjax(ipage);
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
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String recourseId) throws Exception {
        FormService formService = new FormService();
        FormData formcwrecourseconfimedit = formService.getFormData("cwrecourseconfimedit");
        CwRecourseConfim cwRecourseConfim = new CwRecourseConfim();
        ActionContext.initialize(request, response);
        MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
        mfBusRecourseApply.setRecourseId(recourseId);
        mfBusRecourseApply = mfBusRecourseApplyFeign.getById(mfBusRecourseApply);

        cwRecourseConfim.setRecourseId(recourseId);
        cwRecourseConfim.setPactId(mfBusRecourseApply.getPactId());
        cwRecourseConfim.setCusNo(mfBusRecourseApply.getCusNo());
        cwRecourseConfim.setCusName(mfBusRecourseApply.getCusName());
        //赋值产品
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(mfBusRecourseApply.getPactId());
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        cwRecourseConfim.setPactNo(mfBusPact.getPactNo());
        cwRecourseConfim.setPactAmt(mfBusPact.getPactAmt());
        cwRecourseConfim.setKindNo(mfBusPact.getKindNo());
        cwRecourseConfim.setKindName(mfBusPact.getKindName());
        //赋值代偿信息--代偿金额
        MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
        mfBusCompensatoryApply.setCompensatoryId(mfBusRecourseApply.getCompensatoryId());
        mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getById(mfBusCompensatoryApply);
//        cwRecourseConfim.setComposationAmt(mfBusCompensatoryApply.getCompensatoryFee());

        cwRecourseConfim.setRecourseAmt(mfBusRecourseApply.getActualAmount());
        cwRecourseConfim.setReceviedDate(mfBusRecourseApply.getRecoverDate());
        getObjValue(formcwrecourseconfimedit, cwRecourseConfim);
        model.addAttribute("formcwrecourseconfimedit", formcwrecourseconfimedit);
        model.addAttribute("cusNo",cwRecourseConfim.getCusNo());
        model.addAttribute("query", "");
        return "/component/finance/manage/CwRecourseConfim_Insert";
    }

    /**
     * 新增用印
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap<String, Object>();
        CwRecourseConfim cwRecourseConfim = new CwRecourseConfim();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcwrecourseconfimedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcwrecourseconfimedit, getMapByJson(ajaxData));
            if (this.validateFormData(formcwrecourseconfimedit)) {
                setObjValue(formcwrecourseconfimedit, cwRecourseConfim);
                cwRecourseConfim.setOpNo(User.getRegNo(request));
                cwRecourseConfim.setOpName(User.getRegName(request));
                cwRecourseConfim.setBrNo(User.getOrgNo(request));
                cwRecourseConfim.setBrName(User.getOrgName(request));
                dataMap.put("cwRecourseConfim", cwRecourseConfim);
                cwRecourseConfim = cwRecourseConfimFeign.insert(dataMap);
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
