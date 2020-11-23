package app.component.cus.controller;

import app.base.SpringUtil;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusSharePledge;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusSharePledgeFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import config.YmlConfig;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mfCusSharePledge")
public class MfCusSharePledgeController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;


    @Autowired
    private MfCusSharePledgeFeign mfCusSharePledgeFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private MfCusFormConfigFeign mfCusFormConfigFeign;
    // 全局变量
    @SuppressWarnings("unchecked")
    //方法描述： 跳转股权质押信息新增页面
    @RequestMapping(value = "/input")
    public String input(Model model,String cusNo) throws Exception {
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                "MfCusSharePledgeAction");
        String formId = "cusSharePledge";
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getAddModelDef();
        }
        FormData formcusSharePledge = formService.getFormData(formId);
        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        mfCusSharePledge.setCusNo(cusNo);
        getObjValue(formcusSharePledge, mfCusSharePledge);
        //model.addAttribute("mfCusSharePledge",mfCusSharePledge);
        model.addAttribute("formcusSharePledge", formcusSharePledge);
        model.addAttribute("query", "");
        return "/component/cus/MfCusSharePledge_Insert";
    }

    /***
     * 新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert")
    public String insert(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcusSharePledge = formService.getFormData("cusSharePledge");
        getFormValue(formcusSharePledge);
        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        setObjValue(formcusSharePledge, mfCusSharePledge);
        mfCusSharePledgeFeign.insert(mfCusSharePledge);
        getObjValue(formcusSharePledge, mfCusSharePledge);
        this.addActionMessage(model, "保存成功");
        List<MfCusSharePledge> mfCusSharePledgeList = (List<MfCusSharePledge>) mfCusSharePledgeFeign.findByPage(this.getIpage())
                .getResult();
        model.addAttribute("formcusSharePledge", formcusSharePledge);
        model.addAttribute("mfCusSharePledgeList", mfCusSharePledgeList);
        model.addAttribute("query", "");
        return "/component/cus/MfCusHighInfo_Insert";
    }
    /**
     * AJAX新增
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String,Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            FormService formService = new FormService();
            Map<String, Object> map = getMapByJson(ajaxData);
            String formId = map.get("formId").toString();
            FormData formcusSharePledge = formService.getFormData(formId);
            getFormValue(formcusSharePledge, map);
            if(this.validateFormData(formcusSharePledge)){
                MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
                setObjValue(formcusSharePledge, mfCusSharePledge);
                if(StringUtil.isNotEmpty(mfCusSharePledge.getCusNo())){
                    mfCusSharePledge.setRelNo(mfCusSharePledge.getCusNo());
                }
                String cusNo = mfCusSharePledge.getCusNo();
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusSharePledge.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                mfCusSharePledgeFeign.insert(mfCusSharePledge);

                String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusSharePledge.getCusNo(),
                        mfCusSharePledge.getCusNo());// 更新客户信息完整度

                String tableId = "tablecusSharePledgeListBase";
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusSharePledgeAction");
                if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
                    tableId = "table" + mfCusFormConfig.getListModelDef();
                }
                mfCusSharePledge = new MfCusSharePledge();
                mfCusSharePledge.setCusNo(cusNo);
                mfCusSharePledge.setRelNo(cusNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfCusSharePledge", mfCusSharePledge));
                @SuppressWarnings("unchecked")
                List<MfCusSharePledge> list = (List<MfCusSharePledge>) mfCusSharePledgeFeign.findByPage(ipage).getResult();
                JsonTableUtil jtu = new JsonTableUtil();
                String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
                dataMap.put("htmlStr", tableHtml);
                dataMap.put("htmlStrFlag", "1");
                dataMap.put("infIntegrity", infIntegrity);
                dataMap.put("flag", "success");
                dataMap.put("msg", "新增成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw e;
        }
        return dataMap;
    }


    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        mfCusSharePledge.setCusNo(cusNo);
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfCusSharePledge", mfCusSharePledge));
        List<MfCusSharePledge> mfCusSharePledgeList = (List<MfCusSharePledge>) mfCusSharePledgeFeign.findByPage(ipage).getResult();
        model.addAttribute("query", "");
        model.addAttribute("mfCusSharePledgeList", mfCusSharePledgeList);
        return "/component/cus/MfCusSharePledge_List";
    }

    /**
     * 列表数据查询
     * @param ajaxData
     * @return
     * @throws Exception
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        try {
            mfCusSharePledge.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusSharePledge.setCriteriaList(mfCusSharePledge, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfCusSharePledge", mfCusSharePledge));
            ipage = mfCusSharePledgeFeign.findByPage(ipage);
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

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getListPageBig")
    public String getListPageBig(Model model, String cusNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcusSharePledge = null;
        try {
            formcusSharePledge = formService.getFormData("cusSharePledge");
            MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
            mfCusSharePledge.setCusNo(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setParams(this.setIpageParams("mfCusSharePledge", mfCusSharePledge));
            List<MfCusSharePledge> mfCusSharePledgeList = (List<MfCusSharePledge>) mfCusSharePledgeFeign
                    .findByPage(ipage).getResult();
            model.addAttribute("mfCusSharePledgeList", mfCusSharePledgeList);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        model.addAttribute("formcusSharePledge", formcusSharePledge);
        model.addAttribute("query", "");
        return "/component/cus/MfCusSharePledge_ListBig";
    }
    /**
     *
     * AJAX更新保存
     * @param ajaxData
     * @return
     * @throws Exception
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            if (StringUtil.isEmpty(formId)) {
                formId = mfCusFormConfigFeign.getByCusType("base", "MfCusSharePledgeAction").getAddModel();
            }
            FormData formcusSharePldege = formService.getFormData(formId);
            getFormValue(formcusSharePldege, map);
            if (this.validateFormData(formcusSharePldege)) {
                setObjValue(formcusSharePldege, mfCusSharePledge);
                mfCusSharePledgeFeign.update(mfCusSharePledge);

                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusSharePledge.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

                String tableId = "tablecusSharePledge";
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusSharePledgeAction");
                if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
                    tableId = "table" + mfCusFormConfig.getListModelDef();
                }

                String cusNo = mfCusSharePledge.getCusNo();
                mfCusSharePledge.setCusNo(cusNo);
                Ipage ipage = this.getIpage();
                JsonTableUtil jtu = new JsonTableUtil();
                ipage.setParams(this.setIpageParams("mfCusSharePledge", mfCusSharePledge));
                String tableHtml = jtu.getJsonStr(tableId, "tableTag",
                        (List<MfCusSharePledge>) mfCusSharePledgeFeign.findByPage(ipage).getResult(), null, true);
                dataMap.put("htmlStr", tableHtml);
                dataMap.put("htmlStrFlag", "1");

                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
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
    public Map<String, Object> deleteAjax(String sharePledgeId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        mfCusSharePledge.setSharePledgeId(sharePledgeId);
        try {
            mfCusSharePledgeFeign.delete(mfCusSharePledge);
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
    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public String delete(Model model, String sharePledgeId, String cusNo) throws Exception {
        ActionContext.initialize(request, response);

        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        mfCusSharePledge.setSharePledgeId(sharePledgeId);
        mfCusSharePledgeFeign.delete(mfCusSharePledge);
        return getListPage(model, cusNo);
    }

    // 列表展示详情，单字段编辑
    @RequestMapping(value = "/listShowDetailAjax")
    @ResponseBody
    public Map<String, Object> listShowDetailAjax(String cusNo, String sharePledgeId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String cusType = "";
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        cusType = mfCusCustomer.getCusType();
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusSharePledgeAction");
        String formId = null;
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getShowModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
            // logger.error("客户类型为"+cusType+"的MfCusShareholderAction表单信息没有查询到");
        } else {
            Map<String, Object> formData = new HashMap<String, Object>();
            request.setAttribute("ifBizManger", "3");
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            FormData formcusSharePledgeInfo = formService.getFormData(formId);
            MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
            mfCusSharePledge.setSharePledgeId(sharePledgeId);
            mfCusSharePledge = mfCusSharePledgeFeign.getById(mfCusSharePledge);
            getObjValue(formcusSharePledgeInfo, mfCusSharePledge, formData);
            String htmlStrCorp = jsonFormUtil.getJsonStr(formcusSharePledgeInfo, "propertySeeTag", "");
            if (mfCusSharePledge != null) {
                dataMap.put("formHtml", htmlStrCorp);
                dataMap.put("flag", "success");
            } else {
                dataMap.put("msg", "获取详情失败");
                dataMap.put("flag", "error");
            }
            dataMap.put("formData", mfCusSharePledge);
        }
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
    public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
        ActionContext.initialize(request, response);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> map = getMapByJson(ajaxData);

        formId = (String) map.get("formId");
        FormService formService = new FormService();
        FormData formcusSharePlegeInfoDetail = formService.getFormData(formId);
        getFormValue(formcusSharePlegeInfoDetail, map);

        MfCusSharePledge mfCusSharePledgeNew = new MfCusSharePledge();
        setObjValue(formcusSharePlegeInfoDetail, mfCusSharePledgeNew);

        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        mfCusSharePledge.setSharePledgeId(mfCusSharePledgeNew.getSharePledgeId());
        mfCusSharePledge = mfCusSharePledgeFeign.getById(mfCusSharePledge);
        if (mfCusSharePledge != null) {
            try {
                mfCusSharePledge = (MfCusSharePledge) EntityUtil.reflectionSetVal(mfCusSharePledge, mfCusSharePledgeNew,
                        map);
                mfCusSharePledgeFeign.update(mfCusSharePledge);
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

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String sharePledgeId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formcusSharePledgeInfoDetail = formService.getFormData("cusSharePledgeInfoDetail");
        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        mfCusSharePledge.setSharePledgeId(sharePledgeId);
        mfCusSharePledge = mfCusSharePledgeFeign.getById(mfCusSharePledge);
        getObjValue(formcusSharePledgeInfoDetail, mfCusSharePledge, formData);
        if (mfCusSharePledge != null) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getByIdAjax(String sharePledgeId,Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcusSharePledgeInfoDetail = formService.getFormData("cusSharePledgeInfoDetail");
        MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
        mfCusSharePledge.setSharePledgeId(sharePledgeId);
        mfCusSharePledge = mfCusSharePledgeFeign.getById(mfCusSharePledge);

        String cusType = "";
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfCusSharePledge.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        cusType = mfCusCustomer.getCusType();

        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusSharePledgeAction");
        String formId = "";
        if (mfCusFormConfig == null) {

        } else {
            if ("1".equals(mfCusFormConfig.getShowType())) {
                formId = mfCusFormConfig.getShowModelDef();
            } else {
                formId = mfCusFormConfig.getAddModelDef();
            }
        }
        if (StringUtil.isEmpty(formId)) {
            // logger.error("客户类型为"+cusType+"的MfCusShareholderAction表单信息没有查询到");
        } else {
            formcusSharePledgeInfoDetail = formService.getFormData(formId);
            if (formcusSharePledgeInfoDetail.getFormId() == null) {
                // logger.error("客户类型为"+cusType+"的MfCusShareholderAction表单form"+formId+".xml文件不存在");
            } else {
                getFormValue(formcusSharePledgeInfoDetail);
                getObjValue(formcusSharePledgeInfoDetail, mfCusSharePledge);
            }
        }
        model.addAttribute("formcusSharePledgeInfoDetail", formcusSharePledgeInfoDetail);
        model.addAttribute("query", "");
        return "/component/cus/MfCusSharePledge_Detail";
    }
}


