package app.component.model.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.model.entity.MfTemplateEsignerList;
import app.component.model.feign.MfTemplateEsignerListFeign;
import app.util.toolkit.Ipage;
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

/**
 * Title: MfTemplateEsignerListController.java
 * Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Mon Mar 04 14:14:25 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfTemplateEsignerList")
public class MfTemplateEsignerListController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfTemplateEsignerListFeign mfTemplateEsignerListFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;


    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        model.addAttribute("idNum",mfCusCustomer.getIdNum());
        return "/component/model/MfTemplateEsignerList_List";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/findByPageAjax")
    public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo, Integer pageSize, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfTemplateEsignerList mfTemplateEsignerList = new MfTemplateEsignerList();
        try {
            mfTemplateEsignerList.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfTemplateEsignerList.setCriteriaList(mfTemplateEsignerList, ajaxData);//我的筛选
            mfTemplateEsignerList.setCustomSorts(ajaxData);//自定义排序
            mfTemplateEsignerList.setEsignerNo(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("mfTemplateEsignerList", mfTemplateEsignerList));
            //自定义查询Feign方法
            ipage = mfTemplateEsignerListFeign.findByPageByCoreCompany(ipage);
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
            FormData formMfTemplateEsignerListAdd = formService.getFormData("MfTemplateEsignerListAdd");
            getFormValue(formMfTemplateEsignerListAdd, getMapByJson(ajaxData));
            if (this.validateFormData(formMfTemplateEsignerListAdd)) {
                MfTemplateEsignerList mfTemplateEsignerList = new MfTemplateEsignerList();
                setObjValue(formMfTemplateEsignerListAdd, mfTemplateEsignerList);
                mfTemplateEsignerListFeign.insert(mfTemplateEsignerList);
                dataMap.put("flag", "success");
                dataMap.put("msg", "新增成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw e;
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
        FormData formMfTemplateEsignerListAdd = formService.getFormData("MfTemplateEsignerListAdd");
        getFormValue(formMfTemplateEsignerListAdd, getMapByJson(ajaxData));
        MfTemplateEsignerList mfTemplateEsignerListJsp = new MfTemplateEsignerList();
        setObjValue(formMfTemplateEsignerListAdd, mfTemplateEsignerListJsp);
        MfTemplateEsignerList mfTemplateEsignerList = mfTemplateEsignerListFeign.getById(mfTemplateEsignerListJsp);
        if (mfTemplateEsignerList != null) {
            try {
                mfTemplateEsignerList = (MfTemplateEsignerList) EntityUtil.reflectionSetVal(mfTemplateEsignerList, mfTemplateEsignerListJsp, getMapByJson(ajaxData));
                mfTemplateEsignerListFeign.update(mfTemplateEsignerList);
                dataMap.put("flag", "success");
                dataMap.put("msg", "保存成功");
            } catch (Exception e) {
                dataMap.put("flag", "error");
                dataMap.put("msg", "新增失败");
                throw e;
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", "编号不存在,保存失败");
        }
        return dataMap;
    }

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
        MfTemplateEsignerList mfTemplateEsignerList = new MfTemplateEsignerList();
        try {
            FormData formMfTemplateEsignerListAdd = formService.getFormData("MfTemplateEsignerListAdd");
            getFormValue(formMfTemplateEsignerListAdd, getMapByJson(ajaxData));
            if (this.validateFormData(formMfTemplateEsignerListAdd)) {
                mfTemplateEsignerList = new MfTemplateEsignerList();
                setObjValue(formMfTemplateEsignerListAdd, mfTemplateEsignerList);
                mfTemplateEsignerListFeign.update(mfTemplateEsignerList);
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
    public Map<String, Object> getByIdAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formMfTemplateEsignerListAdd = formService.getFormData("MfTemplateEsignerListAdd");
        MfTemplateEsignerList mfTemplateEsignerList = new MfTemplateEsignerList();
        mfTemplateEsignerList.setId(id);
        mfTemplateEsignerList = mfTemplateEsignerListFeign.getById(mfTemplateEsignerList);
        getObjValue(formMfTemplateEsignerListAdd, mfTemplateEsignerList, formData);
        if (mfTemplateEsignerList != null) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }

    @RequestMapping("/getNotSignedCount")
    @ResponseBody
    public Map<String, Object> getNotSignedCount(String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfTemplateEsignerList mfTemplateEsignerList = new MfTemplateEsignerList();
        mfTemplateEsignerList.setEsignerNo(cusNo);
        mfTemplateEsignerList.setCriteriaList(mfTemplateEsignerList, "[[{\"treeId\":\"my_filter\",\"checked\":true,\"andOr\":\"1\",\"condition\":\"ifEsigned\",\"type\":\"0\",\"dicType\":\"0\",\"value\":\"0\",\"noValue\":false,\"singleValue\":false,\"betweenValue\":false,\"listValue\":true,\"likeValue\":false,\"tId\":\"my_filter_2\",\"filterName\":\"未签约\"},{\"treeId\":\"my_filter\",\"checked\":true,\"andOr\":\"1\",\"condition\":\"ifEsigned\",\"type\":\"0\",\"dicType\":\"0\",\"value\":\"1\",\"noValue\":false,\"singleValue\":false,\"betweenValue\":false,\"listValue\":true,\"likeValue\":false,\"tId\":\"my_filter_2\",\"filterName\":\"未签约\"}],{\"customQuery\":\"\"},{\"customSorts\":\"[]\"}]");
        try {
            Integer count = mfTemplateEsignerListFeign.getNotSignedCount(mfTemplateEsignerList);
            dataMap.put("flag", "success");
            dataMap.put("count", count);
        } catch (Exception e) {
            dataMap.put("flag", "error");
        }
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
    public Map<String, Object> deleteAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfTemplateEsignerList mfTemplateEsignerList = new MfTemplateEsignerList();
        mfTemplateEsignerList.setId(id);
        try {
            mfTemplateEsignerListFeign.delete(mfTemplateEsignerList);
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
    public String input(Model model) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formMfTemplateEsignerListAdd = formService.getFormData("MfTemplateEsignerListAdd");
        model.addAttribute("formMfTemplateEsignerListAdd", formMfTemplateEsignerListAdd);
        model.addAttribute("query", "");
        return "/component/model/MfTemplateEsignerList_Insert";
    }

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(Model model, String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formMfTemplateEsignerListDetail = formService.getFormData("MfTemplateEsignerListDetail");
        getFormValue(formMfTemplateEsignerListDetail);
        MfTemplateEsignerList mfTemplateEsignerList = new MfTemplateEsignerList();
        mfTemplateEsignerList.setId(id);
        mfTemplateEsignerList = mfTemplateEsignerListFeign.getById(mfTemplateEsignerList);
        getObjValue(formMfTemplateEsignerListDetail, mfTemplateEsignerList);
        model.addAttribute("formMfTemplateEsignerListDetail", formMfTemplateEsignerListDetail);
        model.addAttribute("query", "");
        return "/component/model/MfTemplateEsignerList_Detail";
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
        FormData formMfTemplateEsignerListAdd = formService.getFormData("MfTemplateEsignerListAdd");
        getFormValue(formMfTemplateEsignerListAdd);
        boolean validateFlag = this.validateFormData(formMfTemplateEsignerListAdd);
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
        FormData formMfTemplateEsignerListAdd = formService.getFormData("MfTemplateEsignerListAdd");
        getFormValue(formMfTemplateEsignerListAdd);
        boolean validateFlag = this.validateFormData(formMfTemplateEsignerListAdd);
    }

    @RequestMapping(value = "/findListForDZQY")
    public String findListForDZQY(Model model, String nodeNo, String temBizNo, String templateNo) throws Exception {
        ActionContext.initialize(request, response);
        MfTemplateEsignerList mfTemplateEsignerList = new MfTemplateEsignerList();
        mfTemplateEsignerList.setTemBizNo(temBizNo);
        mfTemplateEsignerList.setNodeNo(nodeNo);
        mfTemplateEsignerList.setTemplateNo(templateNo);
        List<MfTemplateEsignerList> mfTemplateEsignerLists = mfTemplateEsignerListFeign.getEsigners(mfTemplateEsignerList);


        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr("tableesignlist0001", "tableTag", mfTemplateEsignerLists, null, true);
        model.addAttribute("tableHtml", tableHtml);
        model.addAttribute("mfTemplateEsignerLists", mfTemplateEsignerLists);
        return "/component/model/MfTemplateEsignerList_List2";
    }

    @RequestMapping(value = "/findListForCor")
    @ResponseBody
    public Map<String, Object>  findListForCor(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfTemplateEsignerList mfTemplateEsignerList = new MfTemplateEsignerList();
        try{
            mfTemplateEsignerList.setEsignerNo(cusNo);
            mfTemplateEsignerList.setNodeNo("contract_print");
            mfTemplateEsignerList.setTemplateNo("19052815551120511");
            List<MfTemplateEsignerList> mfTemplateEsignerLists = mfTemplateEsignerListFeign.getEsignersCor(mfTemplateEsignerList);
            dataMap.put("mfTemplateEsignerLists",mfTemplateEsignerLists);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }

        return dataMap;
    }
}
