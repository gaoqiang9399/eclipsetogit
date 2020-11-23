package app.component.vouafter.controller;

import app.base.User;
import app.component.calc.charge.feign.MfBusChargeFeeFeign;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.finance.manage.entity.CwCollectConfim;
import app.component.finance.manage.entity.CwPayConfim;
import app.component.finance.manage.feign.CwCollectConfimFeign;
import app.component.finance.manage.feign.CwPayConfimFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.vouafter.entity.MfVouAfterPayManage;
import app.component.vouafter.feign.MfVouAfterPayManageFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
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
@RequestMapping("/mfVouAfterPayManage")
public class MfVouAfterPayManageController extends BaseFormBean {

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    private MfVouAfterPayManageFeign mfVouAfterPayManageFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfBusChargeFeeFeign mfBusChargeFeeFeign;
    @Autowired
    private CwPayConfimFeign cwPayConfimFeign;

    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/vouafter/MfVouAfterPayManage_List";
    }

    @RequestMapping(value = "/findListByAjax")
    @ResponseBody
    public Map<String, Object> findListByAjax(Integer pageNo, Integer pageSize, String tableId,String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfVouAfterPayManage mfVouAfterPayManage = new MfVouAfterPayManage();
        try {
            mfVouAfterPayManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfVouAfterPayManage.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfVouAfterPayManage.setCriteriaList(mfVouAfterPayManage, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfVouAfterPayManage", mfVouAfterPayManage));
            ipage = mfVouAfterPayManageFeign.findListByAjax(ipage);
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
     * 支出管理界面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String pactId, String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formmfvouafterpaymanageinsert = formService.getFormData("mfvouafterpaymanageinsert");
        MfVouAfterPayManage mfVouAfterPayManage = new MfVouAfterPayManage();
        /*if(StringUtil.isNotEmpty(id)){
            mfVouAfterPayManage.setId(id);
            mfVouAfterPayManage = mfVouAfterPayManageFeign.getById(mfVouAfterPayManage);
        }*/
        getObjValue(formmfvouafterpaymanageinsert, mfVouAfterPayManage);
        model.addAttribute("formmfvouafterpaymanageinsert", formmfvouafterpaymanageinsert);
        model.addAttribute("query", "");
        return "/component/vouafter/MfVouAfterPayManage_Insert";
    }

    /**
     * 查询合同列表-支出管理
     * @param pageNo
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getPactListForVouPay")
    public Map<String, Object> getPactListForVouPay(int pageNo,String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfBusPact.setCustomSorts(ajaxData);
            mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfBusPact",mfBusPact));
            ipage = mfVouAfterPayManageFeign.getPactListForVouPay(ipage);
            ipage.setParamsStr(ajaxData);
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
     * AJAX获取查询合同信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPactInfoAjax")
    @ResponseBody
    public Map<String, Object> getPactInfoAjax(String pactId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(pactId);
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        if (mfBusPact != null) {
            dataMap.put("flag", "success");
            dataMap.put("mfBusPact", mfBusPact);
            /*CwCollectConfim cwCollectConfim = new CwCollectConfim();
            cwCollectConfim.setPactId(mfBusPact.getPactId());
            cwCollectConfim = cwCollectConfimFeign.getById(cwCollectConfim);*/
            MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
            mfBusChargeFee.setPactId(mfBusPact.getPactId());
            mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
            dataMap.put("bankChargeAmt",mfBusChargeFee.getHandAmt());//手续费
            dataMap.put("bankBond",mfBusChargeFee.getBond());//保证金
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg","查询合同信息失败！");
        }
        return dataMap;
    }

    /**
     * 保存
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
        MfVouAfterPayManage mfVouAfterPayManage = new MfVouAfterPayManage();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formmfvouafterpaymanageinsert = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formmfvouafterpaymanageinsert, getMapByJson(ajaxData));
            if (this.validateFormDataAnchor(formmfvouafterpaymanageinsert)) {
                setObjValue(formmfvouafterpaymanageinsert, mfVouAfterPayManage);
                mfVouAfterPayManage.setOpNo(User.getRegNo(request));
                mfVouAfterPayManage.setOpName(User.getRegName(request));
                mfVouAfterPayManage.setBrNo(User.getOrgNo(request));
                mfVouAfterPayManage.setBrName(User.getOrgName(request));
                mfVouAfterPayManageFeign.insert(mfVouAfterPayManage);
                dataMap.put("flag", "success");
                dataMap.put("msg", "支出登记成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", "支出登记失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

    /**
     * 支出管理界面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editPayManage")
    public String editPayManage(Model model, String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formmfvouafterpaymanageinsert = formService.getFormData("mfvouafterpaymanageinsert");
        MfVouAfterPayManage mfVouAfterPayManage = new MfVouAfterPayManage();
        mfVouAfterPayManage.setId(id);
        mfVouAfterPayManage = mfVouAfterPayManageFeign.getById(mfVouAfterPayManage);
        getObjValue(formmfvouafterpaymanageinsert, mfVouAfterPayManage);
        model.addAttribute("formmfvouafterpaymanageinsert", formmfvouafterpaymanageinsert);
        model.addAttribute("query", "");
        return "/component/vouafter/MfVouAfterPayManage_Update";
    }

    /**
     * 保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map <String, Object> updateAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfVouAfterPayManage mfVouAfterPayManage = new MfVouAfterPayManage();
        try {
            dataMap = getMapByJson(ajaxData);
            FormData formmfvouafterpaymanageinsert = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formmfvouafterpaymanageinsert, getMapByJson(ajaxData));
            if (this.validateFormDataAnchor(formmfvouafterpaymanageinsert)) {
                setObjValue(formmfvouafterpaymanageinsert, mfVouAfterPayManage);
                CwPayConfim cwPayConfim = new CwPayConfim();
                cwPayConfim.setPayId(mfVouAfterPayManage.getId());
                cwPayConfim = cwPayConfimFeign.getById(cwPayConfim);
                if(!"1".equals(cwPayConfim.getStatus())){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "该支出已经支出确认，不能修改！");
                    return dataMap;
                }
                mfVouAfterPayManageFeign.update(mfVouAfterPayManage);
                dataMap.put("flag", "success");
                dataMap.put("msg", "修改成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", "修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }

}
