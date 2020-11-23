package app.component.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonJob;
import app.component.model.feign.MfSysTemplateEsignerTypeFeign;
import app.component.model.entity.MfSysTemplateEsignerType;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
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

/**
 * Title: MfSysTemplateEsignerTypeController.java
 * Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Mon Mar 04 14:11:45 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfSysTemplateEsignerType")
public class MfSysTemplateEsignerTypeController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfSysTemplateEsignerTypeFeign mfSysTemplateEsignerTypeFeign;


    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/model/MfSysTemplateEsignerType_List";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/findByPageAjax")
    public Map <String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo, Integer pageSize) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
        try {
            mfSysTemplateEsignerType.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfSysTemplateEsignerType.setCriteriaList(mfSysTemplateEsignerType, ajaxData);//我的筛选
            //mfSysTemplateEsignerType.setCustomSorts(ajaxData);//自定义排序
            //this.getRoleConditions(mfSysTemplateEsignerType,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("mfSysTemplateEsignerType", mfSysTemplateEsignerType));
            //自定义查询Feign方法
            ipage = mfSysTemplateEsignerTypeFeign.findByPage(ipage);
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
    public Map <String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            FormData formmfSysTemplateEsignerTypeAdd = formService.getFormData("mfSysTemplateEsignerTypeAdd");
            getFormValue(formmfSysTemplateEsignerTypeAdd, getMapByJson(ajaxData));
            if (this.validateFormData(formmfSysTemplateEsignerTypeAdd)) {
                MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
                setObjValue(formmfSysTemplateEsignerTypeAdd, mfSysTemplateEsignerType);
                mfSysTemplateEsignerTypeFeign.insert(mfSysTemplateEsignerType);
                JsonTableUtil jtu = new JsonTableUtil();
                String esignerTypeTableHtml = jtu.getJsonStr(
                        "tablemfSysTemplateEsignerTypeList",
                        "tableTag", mfSysTemplateEsignerTypeFeign.getByTemplateNo(mfSysTemplateEsignerType), null, true);
                dataMap.put("esignerTypeTableHtml", esignerTypeTableHtml);
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
    public Map <String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formmfSysTemplateEsignerTypeAdd = formService.getFormData("mfSysTemplateEsignerTypeAdd");
        getFormValue(formmfSysTemplateEsignerTypeAdd, getMapByJson(ajaxData));
        MfSysTemplateEsignerType mfSysTemplateEsignerTypeJsp = new MfSysTemplateEsignerType();
        setObjValue(formmfSysTemplateEsignerTypeAdd, mfSysTemplateEsignerTypeJsp);
        MfSysTemplateEsignerType mfSysTemplateEsignerType = mfSysTemplateEsignerTypeFeign.getById(mfSysTemplateEsignerTypeJsp);
        if (mfSysTemplateEsignerType != null) {
            try {
                mfSysTemplateEsignerType = (MfSysTemplateEsignerType) EntityUtil.reflectionSetVal(mfSysTemplateEsignerType, mfSysTemplateEsignerTypeJsp, getMapByJson(ajaxData));
                mfSysTemplateEsignerTypeFeign.update(mfSysTemplateEsignerType);
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
    public Map <String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
        try {
            FormData formmfSysTemplateEsignerTypeAdd = formService.getFormData("mfSysTemplateEsignerTypeAdd");
            getFormValue(formmfSysTemplateEsignerTypeAdd, getMapByJson(ajaxData));
            if (this.validateFormData(formmfSysTemplateEsignerTypeAdd)) {
                mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
                setObjValue(formmfSysTemplateEsignerTypeAdd, mfSysTemplateEsignerType);
                mfSysTemplateEsignerTypeFeign.update(mfSysTemplateEsignerType);
                JsonTableUtil jtu = new JsonTableUtil();
                String esignerTypeTableHtml = jtu.getJsonStr(
                        "tablemfSysTemplateEsignerTypeList",
                        "tableTag", mfSysTemplateEsignerTypeFeign.getByTemplateNo(mfSysTemplateEsignerType), null, true);
                dataMap.put("esignerTypeTableHtml", esignerTypeTableHtml);
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
    public Map <String, Object> getByIdAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> formData = new HashMap <String, Object>();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        FormData formmfSysTemplateEsignerTypeAdd = formService.getFormData("mfSysTemplateEsignerTypeAdd");
        MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
        mfSysTemplateEsignerType.setId(id);
        mfSysTemplateEsignerType = mfSysTemplateEsignerTypeFeign.getById(mfSysTemplateEsignerType);
        getObjValue(formmfSysTemplateEsignerTypeAdd, mfSysTemplateEsignerType, formData);
        if (mfSysTemplateEsignerType != null) {
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
    public Map <String, Object> deleteAjax(String id,String templateNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
        mfSysTemplateEsignerType.setId(id);
        mfSysTemplateEsignerType.setTemplateNo(templateNo);
        try {
            mfSysTemplateEsignerTypeFeign.delete(mfSysTemplateEsignerType);
            JsonTableUtil jtu = new JsonTableUtil();
            String esignerTypeTableHtml = jtu.getJsonStr(
                    "tablemfSysTemplateEsignerTypeList",
                    "tableTag", mfSysTemplateEsignerTypeFeign.getByTemplateNo(mfSysTemplateEsignerType), null, true);
            dataMap.put("esignerTypeTableHtml", esignerTypeTableHtml);
            dataMap.put("flag", "success");
            dataMap.put("msg", "删除成功");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "删除失败");
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
    public String input(Model model, String templateNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formmfSysTemplateEsignerTypeAdd = formService.getFormData("mfSysTemplateEsignerTypeAdd");
        MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
        mfSysTemplateEsignerType.setTemplateNo(templateNo);
        getObjValue(formmfSysTemplateEsignerTypeAdd, mfSysTemplateEsignerType);
        model.addAttribute("formmfSysTemplateEsignerTypeAdd", formmfSysTemplateEsignerTypeAdd);
        model.addAttribute("query", "");
        return "/component/model/MfSysTemplateEsignerType_Insert";
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
        FormData formmfSysTemplateEsignerTypeAdd = formService.getFormData("mfSysTemplateEsignerTypeAdd");
        getFormValue(formmfSysTemplateEsignerTypeAdd);
        MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
        mfSysTemplateEsignerType.setId(id);
        mfSysTemplateEsignerType = mfSysTemplateEsignerTypeFeign.getById(mfSysTemplateEsignerType);
        getObjValue(formmfSysTemplateEsignerTypeAdd, mfSysTemplateEsignerType);
        model.addAttribute("formmfSysTemplateEsignerTypeAdd", formmfSysTemplateEsignerTypeAdd);
        model.addAttribute("query", "");
        return "/component/model/MfSysTemplateEsignerType_Detail";
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
        FormData formmfSysTemplateEsignerTypeAdd = formService.getFormData("mfSysTemplateEsignerTypeAdd");
        getFormValue(formmfSysTemplateEsignerTypeAdd);
        boolean validateFlag = this.validateFormData(formmfSysTemplateEsignerTypeAdd);
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
        FormData formmfSysTemplateEsignerTypeAdd = formService.getFormData("mfSysTemplateEsignerTypeAdd");
        getFormValue(formmfSysTemplateEsignerTypeAdd);
        boolean validateFlag = this.validateFormData(formmfSysTemplateEsignerTypeAdd);
    }

    // 列表展示详情，单字段编辑
    @RequestMapping(value = "/listShowDetailAjax")
    @ResponseBody
    public Map <String, Object> listShowDetailAjax(String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        String formId = "mfSysTemplateEsignerTypeDetail";
        Map <String, Object> formData = new HashMap <String, Object>();
        request.setAttribute("ifBizManger", "3");
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        FormData formmfSysTemplateEsignerTypeDetail = formService.getFormData(formId);
        MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
        mfSysTemplateEsignerType.setId(id);
        mfSysTemplateEsignerType = mfSysTemplateEsignerTypeFeign.getById(mfSysTemplateEsignerType);
        getObjValue(formmfSysTemplateEsignerTypeDetail, mfSysTemplateEsignerType, formData);
        String htmlStrCorp = jsonFormUtil.getJsonStr(formmfSysTemplateEsignerTypeDetail, "propertySeeTag", "");
        if (mfSysTemplateEsignerType != null) {
            dataMap.put("formHtml", htmlStrCorp);
            dataMap.put("flag", "success");
        } else {
            dataMap.put("msg", "获取详情失败");
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", mfSysTemplateEsignerType);
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
    public Map <String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
        Map map = getMapByJson(ajaxData);
        formId = (String) map.get("formId");
        FormData formcusjob00003 = formService.getFormData(formId);
        getFormValue(formcusjob00003, getMapByJson(ajaxData));
        MfSysTemplateEsignerType mfSysTemplateEsignerTypeNew = new MfSysTemplateEsignerType();
        setObjValue(formcusjob00003, mfSysTemplateEsignerTypeNew);
        mfSysTemplateEsignerType.setId(mfSysTemplateEsignerTypeNew.getId());
        mfSysTemplateEsignerType = mfSysTemplateEsignerTypeFeign.getById(mfSysTemplateEsignerType);
        if (mfSysTemplateEsignerType != null) {
            try {
                mfSysTemplateEsignerType = (MfSysTemplateEsignerType) EntityUtil.reflectionSetVal(mfSysTemplateEsignerType, mfSysTemplateEsignerTypeNew,
                        getMapByJson(ajaxData));
                mfSysTemplateEsignerTypeFeign.update(mfSysTemplateEsignerType);
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
