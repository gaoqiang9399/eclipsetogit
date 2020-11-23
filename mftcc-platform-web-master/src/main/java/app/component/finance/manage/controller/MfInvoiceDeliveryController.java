package app.component.finance.manage.controller;

import app.base.User;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.component.cus.cusinvoicemation.feign.MfCusInvoiceMationFeign;
import app.component.finance.manage.entity.CwBillManage;
import app.component.finance.manage.entity.MfInvoiceDelivery;
import app.component.finance.manage.feign.CwBillManageFeign;
import app.component.finance.manage.feign.MfInvoiceDeliveryFeign;
import app.component.pact.entity.MfBusPact;
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
@RequestMapping("/mfInvoiceDelivery")
public class MfInvoiceDeliveryController  extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //注入MfCusDotValueBo
    @Autowired
    private CwBillManageFeign cwBillManageFeign;
    @Autowired
    private MfInvoiceDeliveryFeign mfInvoiceDeliveryFeign;
    @Autowired
    private MfCusInvoiceMationFeign mfCusInvoiceMationFeign;


    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/finance/manage/MfInvoiceDelivery_List";
    }

    @RequestMapping(value = "/findListByAjax")
    @ResponseBody
    public Map<String, Object> findListByAjax(Integer pageNo, Integer pageSize, String tableId,
                                                  String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CwBillManage cwBillManage = new CwBillManage();
        try {
            cwBillManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
            cwBillManage.setCustomSorts(ajaxData);// 自定义排序参数赋值
            cwBillManage.setCriteriaList(cwBillManage, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("cwBillManage", cwBillManage));
            ipage = mfInvoiceDeliveryFeign.findListByAjax(ipage);
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
    public String input(Model model, String id) throws Exception {
        MfInvoiceDelivery mfInvoiceDelivery = new MfInvoiceDelivery();
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData forminvoicedeliveryedit = formService.getFormData("invoicedeliveryedit");
        CwBillManage cwBillManage = new CwBillManage();
        cwBillManage.setId(id);
        cwBillManage = cwBillManageFeign.getById(cwBillManage);

        mfInvoiceDelivery.setBillId(id);
        mfInvoiceDelivery.setCusNo(cwBillManage.getCusNo());
        mfInvoiceDelivery.setCusName(cwBillManage.getCusName());
        mfInvoiceDelivery.setPactId(cwBillManage.getPactId());
        mfInvoiceDelivery.setPactAmt(cwBillManage.getPactAmt());
        mfInvoiceDelivery.setPactTerm(cwBillManage.getPactTerm());
        mfInvoiceDelivery.setKindName(cwBillManage.getKindName());
        if(cwBillManage.getTaxpayerId() != null && !"".equals(cwBillManage.getTaxpayerId())){
            MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
            mfCusInvoiceMation.setId(cwBillManage.getTaxpayerId());
            mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
            mfInvoiceDelivery.setTaxpayerNum(mfCusInvoiceMation.getTaxpayerNo());
            mfInvoiceDelivery.setAddress(mfCusInvoiceMation.getAddress());
            mfInvoiceDelivery.setTelphone(mfCusInvoiceMation.getTel());
            mfInvoiceDelivery.setBankAccount(mfCusInvoiceMation.getBankName());
            mfInvoiceDelivery.setAccount(mfCusInvoiceMation.getAccountNumber());
            mfInvoiceDelivery.setBillRemark(mfCusInvoiceMation.getRemark());
        }
        mfInvoiceDelivery.setBillSts(cwBillManage.getBillSts());
        getObjValue(forminvoicedeliveryedit, mfInvoiceDelivery);
        model.addAttribute("forminvoicedeliveryedit", forminvoicedeliveryedit);
        model.addAttribute("query", "");
        return "/component/finance/manage/MfInvoiceDelivery_Insert";
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
        MfInvoiceDelivery mfInvoiceDelivery = new MfInvoiceDelivery();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData forminvoicedeliveryedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(forminvoicedeliveryedit, getMapByJson(ajaxData));
            if (this.validateFormData(forminvoicedeliveryedit)) {
                setObjValue(forminvoicedeliveryedit, mfInvoiceDelivery);
                mfInvoiceDelivery.setOpNo(User.getRegNo(request));
                mfInvoiceDelivery.setOpName(User.getRegName(request));
                mfInvoiceDelivery.setBrNo(User.getOrgNo(request));
                mfInvoiceDelivery.setBrName(User.getOrgName(request));
                dataMap.put("mfInvoiceDelivery", mfInvoiceDelivery);
                mfInvoiceDelivery = mfInvoiceDeliveryFeign.insert(dataMap);
                dataMap.put("flag", "success");
                dataMap.put("msg", "寄送登记成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", "寄送登记失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 收妥确认
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/receiveConfimAjax")
    @ResponseBody
    public Map <String, Object> receiveConfimAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            mfInvoiceDeliveryFeign.receiveConfimAjax(id);
            dataMap.put("flag", "success");
            dataMap.put("msg", "收妥确认成功！");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 批量收妥确认
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deliveryBillsAjax")
    @ResponseBody
    public Map <String, Object> deliveryBillsAjax(String ids) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            mfInvoiceDeliveryFeign.deliveryBillsAjax(ids);
            dataMap.put("flag", "success");
            dataMap.put("msg", "操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
}
