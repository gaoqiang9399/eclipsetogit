package app.component.finance.manage.controller;

import app.base.User;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.common.BizPubParm;
import app.component.finance.manage.entity.CwPayConfim;
import app.component.finance.manage.feign.CwPayConfimFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feerefund.entity.MfBusFeeRefund;
import app.component.pact.feign.MfBusPactFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
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
@RequestMapping("/cwPayConfim")
public class CwPayConfimController  extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private CwPayConfimFeign cwPayConfimFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfBusAppFeeFeign mfBusAppFeeFeign;

    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/finance/manage/CwPayConfim_List";
    }

    @RequestMapping(value = "/findRefundListByAjax")
    @ResponseBody
    public Map<String, Object> findRefundListByAjax(Integer pageNo, Integer pageSize, String tableId,
                                                  String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CwPayConfim cwPayConfim = new CwPayConfim();
        try {
            cwPayConfim.setCustomQuery(ajaxData);// 自定义查询参数赋值
            cwPayConfim.setCustomSorts(ajaxData);// 自定义排序参数赋值
            cwPayConfim.setCriteriaList(cwPayConfim, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("cwPayConfim", cwPayConfim));
            ipage = cwPayConfimFeign.findListByAjax(ipage);
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
    public String input(Model model, String id, String pactId) throws Exception {
        CwPayConfim cwPayConfim = new CwPayConfim();
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcwpayconfimedit = formService.getFormData("cwpayconfimedit");
        cwPayConfim.setId(id);
        cwPayConfim = cwPayConfimFeign.getById(cwPayConfim);
        getObjValue(formcwpayconfimedit, cwPayConfim);
        model.addAttribute("formcwpayconfimedit", formcwpayconfimedit);
        model.addAttribute("cusNo",cwPayConfim.getCusNo());
        model.addAttribute("query", "");
        return "/component/finance/manage/CwPayConfim_Insert";
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
        CwPayConfim cwPayConfim = new CwPayConfim();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formcwpayconfimedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formcwpayconfimedit, getMapByJson(ajaxData));
            if (this.validateFormData(formcwpayconfimedit)) {
                setObjValue(formcwpayconfimedit, cwPayConfim);
                cwPayConfimFeign.update(cwPayConfim);
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
