package app.component.cus.controller;

import app.base.User;
import app.component.cus.entity.MfCusBusService;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusSaleProduct;
import app.component.cus.feign.MfCusBusServiceFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.EntityUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
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
 * Title: MfCusBusServiceController.java
 * Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Fri Sep 28 10:52:04 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfCusBusService")
public class MfCusBusServiceController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCusBusServiceFeign mfCusBusServiceFeign;
    @Autowired
    private MfCusFormConfigFeign mfCusFormConfigFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private SysInterfaceFeign sysInterfaceFeign;


    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/cus/MfCusBusService_List";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/findByPageAjax")
    public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo, Integer pageSize) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBusService mfCusBusService = new MfCusBusService();
        try {
            mfCusBusService.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusBusService.setCriteriaList(mfCusBusService, ajaxData);//我的筛选
            //mfCusBusService.setCustomSorts(ajaxData);//自定义排序
            //this.getRoleConditions(mfCusBusService,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("mfCusBusService", mfCusBusService));
            //自定义查询Feign方法
            ipage = mfCusBusServiceFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            if (StringUtil.isEmpty(formId)) {
                formId = mfCusFormConfigFeign.getByCusType("base", "MfCusBusServiceAction").getAddModel();
            }
            FormData formcusbusserviceBase = formService.getFormData(formId);
            getFormValue(formcusbusserviceBase, map);
            if (this.validateFormData(formcusbusserviceBase)) {
                MfCusBusService mfCusBusService = new MfCusBusService();
                setObjValue(formcusbusserviceBase, mfCusBusService);
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusBusService.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                String cusName = mfCusCustomer.getCusName();
                mfCusBusServiceFeign.insert(mfCusBusService);
                dataMap.put("mfCusBusService", mfCusBusService);
                String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusBusService.getCusNo(),
                        mfCusBusService.getCusNo());// 更新客户信息完整度

                String tableId = "tablecuscusbusserviceBase";
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusBusServiceAction");
                if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
                    tableId = "table" + mfCusFormConfig.getListModelDef();
                }

                String cusNo = mfCusBusService.getCusNo();
                mfCusBusService = new MfCusBusService();
                mfCusBusService.setCusNo(cusNo);
                mfCusBusService.setDelFlag("0");
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfCusBusService", mfCusBusService));
                JsonTableUtil jtu = new JsonTableUtil();
                @SuppressWarnings("unchecked")
                List<MfCusBusService> list = (List<MfCusBusService>) mfCusBusServiceFeign.findByPage(ipage)
                        .getResult();
                String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
                dataMap.put("htmlStr", tableHtml);
                dataMap.put("htmlStrFlag", "1");
                dataMap.put("infIntegrity", infIntegrity);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw e;
        }
        return dataMap;
    }

    //列表展示详情，单字段编辑
    @RequestMapping(value = "/listShowDetailAjax")
    @ResponseBody
    public Map<String,Object> listShowDetailAjax(String cusNo,String busId) throws Exception {
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        Map<String,Object> dataMap=new HashMap<String,Object>();
        String cusType="";
        String formId="";
        String query="";
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        cusType=mfCusCustomer.getCusType();
        MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusBusServiceAction");
        if(mfCusFormConfig == null){

        }else{
            formId = mfCusFormConfig.getShowModelDef();
        }
        if(StringUtil.isEmpty(formId)){
//			logger.error("客户类型为"+cusType+"的MfCusBankAccManageAction表单信息没有查询到");
            dataMap.put("msg", "获取详情失败");
            dataMap.put("flag", "error");
        }else{
            Map<String,Object> formData = new HashMap<String,Object>();
            request.setAttribute("ifBizManger", "3");
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            FormData formcuscusbusserviceBase = formService.getFormData(formId);
            MfCusBusService mfCusBusService = new MfCusBusService();
            mfCusBusService.setBusId(busId);
            mfCusBusService = mfCusBusServiceFeign.getById(mfCusBusService);
            getObjValue(formcuscusbusserviceBase, mfCusBusService,formData);
            query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
            if(query == null){
                query = "";
            }
            String htmlStrCorp = jsonFormUtil.getJsonStr(formcuscusbusserviceBase,"propertySeeTag",query);
            if(mfCusBusService!=null){
                dataMap.put("formHtml", htmlStrCorp);
                dataMap.put("flag", "success");
            }else{
                dataMap.put("msg", "获取详情失败");
                dataMap.put("flag", "error");
            }
            dataMap.put("formData", mfCusBusService);
        }
        return dataMap;
    }

    /**
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formcuscusbusserviceDetail = formService.getFormData("cuscusbusserviceDetail");
        getFormValue(formcuscusbusserviceDetail, getMapByJson(ajaxData));
        MfCusBusService mfCusBusServiceJSP = new MfCusBusService();
        setObjValue(formcuscusbusserviceDetail, mfCusBusServiceJSP);
        MfCusBusService mfCusBusService = mfCusBusServiceFeign.getById(mfCusBusServiceJSP);
        if(mfCusBusService!=null){
            try{
                if(mfCusBusServiceJSP.getInNum()!=null||mfCusBusServiceJSP.getInPrice()!=null
                        ||mfCusBusServiceJSP.getExpNum()!=null||mfCusBusServiceJSP.getExpPrice()!=null
                ){
                    mfCusBusService = (MfCusBusService) EntityUtil.reflectionSetVal(mfCusBusService, mfCusBusServiceJSP, getMapByJson(ajaxData));
                    mfCusBusService = clacAmt(mfCusBusService);
                }
                mfCusBusServiceFeign.update(mfCusBusService);
                dataMap.put("flag", "success");
                dataMap.put("msg", "保存成功");
            }catch(Exception e){
                dataMap.put("flag", "error");
                dataMap.put("msg", "新增失败");
                throw e;
            }
        }else{
            dataMap.put("flag", "error");
            dataMap.put("msg", "编号不存在,保存失败");
        }
        return dataMap;
    };
    public MfCusBusService clacAmt(MfCusBusService mfCusBusService){
        Integer inNum = mfCusBusService.getInNum() ;//收入数量
        Double inPrice= mfCusBusService.getInPrice();//收入单价
        Double busIncome = mfCusBusService.getBusIncome();//收入合计
        Integer expNum = mfCusBusService.getExpNum();//支出数量
        Double expPrice = mfCusBusService.getExpPrice();//支出单价
        Double busExp = mfCusBusService.getBusExp();//支出合计
        Double profit = mfCusBusService.getProfit();//销售毛利润
        Double profitRate = mfCusBusService.getProfitRate();//毛利率

        if(inNum!=null&&inPrice!=null){
            busIncome = inNum*inPrice;
            mfCusBusService.setBusIncome(busIncome);
        }

        if(expNum!=null&&expPrice!=null){
            busExp = expNum*expPrice;
            mfCusBusService.setBusExp(busExp);
        }

        if(busIncome!=null&&busExp!=null){
            profit = busIncome - busExp;
            mfCusBusService.setProfit(profit);
        }

        if(busIncome!=null&&busExp!=null){
            profitRate = (busExp/busIncome)*100;
            mfCusBusService.setProfitRate(profitRate);
        }
        return mfCusBusService;
    };


    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBusService mfCusBusService = new MfCusBusService();
        try {
            FormData formcuscusbusserviceDetail = formService.getFormData("cuscusbusserviceDetail");
            getFormValue(formcuscusbusserviceDetail, getMapByJson(ajaxData));
            if (this.validateFormData(formcuscusbusserviceDetail)) {
                mfCusBusService = new MfCusBusService();
                setObjValue(formcuscusbusserviceDetail, mfCusBusService);
                mfCusBusServiceFeign.update(mfCusBusService);
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "更新失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String busId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formcuscusbusserviceDetail = formService.getFormData("cuscusbusserviceDetail");
        MfCusBusService mfCusBusService = new MfCusBusService();
        mfCusBusService.setBusId(busId);
        mfCusBusService = mfCusBusServiceFeign.getById(mfCusBusService);
        getObjValue(formcuscusbusserviceDetail, mfCusBusService, formData);
        if (mfCusBusService != null) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }

    /**
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String busId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBusService mfCusBusService = new MfCusBusService();
        mfCusBusService.setBusId(busId);
        try {
            mfCusBusServiceFeign.delete(mfCusBusService);
            dataMap.put("flag", "success");
            dataMap.put("msg", "成功");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
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
    @RequestMapping("/input")
    public String input(Model model,String cusNo,String relNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formcusbusserviceBase = null;
        MfCusBusService mfCusBusService = new MfCusBusService();
        mfCusBusService.setCusNo(cusNo);
        String cusType="";
        String formId="";
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        cusType=mfCusCustomer.getCusType();
        mfCusBusService.setCusName(mfCusCustomer.getCusName());
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusBusServiceAction");
        if(mfCusFormConfig == null){

        }else{
            formId = mfCusFormConfig.getAddModelDef();
        }
        if(StringUtil.isEmpty(formId)){
        }else{
            formcusbusserviceBase = formService.getFormData(formId);
            if(formcusbusserviceBase.getFormId() == null){
            }else{
                getFormValue(formcusbusserviceBase);
                getObjValue(formcusbusserviceBase, mfCusBusService);
            }
        }
        Map<String,Object> dataMap = new HashMap<String,Object>();
        model.addAttribute("formcusbusserviceBase", formcusbusserviceBase);
        model.addAttribute("query", "");
        return "/component/cus/MfCusBusService_Insert";
    }

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(Model model, String busId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formcusbusserviceBase = formService.getFormData("cusbusserviceBase");
        getFormValue(formcusbusserviceBase);
        MfCusBusService mfCusBusService = new MfCusBusService();
        mfCusBusService.setBusId(busId);
        mfCusBusService = mfCusBusServiceFeign.getById(mfCusBusService);
        getObjValue(formcusbusserviceBase, mfCusBusService);
        model.addAttribute("formcusbusserviceBase", formcusbusserviceBase);
        model.addAttribute("query", "");
        return "/component/cus/MfCusBusService_Detail";
    }

    /**
     * 新增校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void validateInsert() throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formcuscusbusserviceDetail = formService.getFormData("cuscusbusserviceDetail");
        getFormValue(formcuscusbusserviceDetail);
        boolean validateFlag = this.validateFormData(formcuscusbusserviceDetail);
    }

    /**
     * 修改校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void validateUpdate() throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formcuscusbusserviceDetail = formService.getFormData("cuscusbusserviceDetail");
        getFormValue(formcuscusbusserviceDetail);
        boolean validateFlag = this.validateFormData(formcuscusbusserviceDetail);
    }
}
