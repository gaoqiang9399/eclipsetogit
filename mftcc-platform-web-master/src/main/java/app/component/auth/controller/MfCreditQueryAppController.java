package app.component.auth.controller;

import app.base.User;
import app.component.auth.entity.MfCreditQueryApp;
import app.component.auth.feign.MfCreditQueryAppFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
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
 * Title: MfCreditQueryAppController.java
 * Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 26 10:48:35 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfCreditQueryApp")
public class MfCreditQueryAppController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCreditQueryAppFeign mfCreditQueryAppFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;


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
        int count = mfCreditQueryAppFeign.getCountByCusNo(cusNo);
        model.addAttribute("count",count);
        return "/component/auth/MfCreditQueryApp_List";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/findByPageAjax")
    public Map <String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo, Integer pageSize, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCreditQueryApp mfCreditQueryApp = new MfCreditQueryApp();
        try {
            mfCreditQueryApp.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCreditQueryApp.setCriteriaList(mfCreditQueryApp, ajaxData);//我的筛选
            mfCreditQueryApp.setCusNo(cusNo);
            //mfCreditQueryApp.setCustomSorts(ajaxData);//自定义排序
            //this.getRoleConditions(mfCreditQueryApp,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("mfCreditQueryApp", mfCreditQueryApp));
            //自定义查询Feign方法
            ipage = mfCreditQueryAppFeign.findByPage(ipage);
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
            FormData formCreditQueryAppAdd = formService.getFormData("CreditQueryAppAdd");
            getFormValue(formCreditQueryAppAdd, getMapByJson(ajaxData));
            if (this.validateFormData(formCreditQueryAppAdd)) {
                MfCreditQueryApp mfCreditQueryApp = new MfCreditQueryApp();
                setObjValue(formCreditQueryAppAdd, mfCreditQueryApp);
                mfCreditQueryAppFeign.insert(mfCreditQueryApp);
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
        FormData formCreditQueryAppAdd = formService.getFormData("CreditQueryAppAdd");
        getFormValue(formCreditQueryAppAdd, getMapByJson(ajaxData));
        MfCreditQueryApp mfCreditQueryAppJsp = new MfCreditQueryApp();
        setObjValue(formCreditQueryAppAdd, mfCreditQueryAppJsp);
        MfCreditQueryApp mfCreditQueryApp = mfCreditQueryAppFeign.getById(mfCreditQueryAppJsp);
        if (mfCreditQueryApp != null) {
            try {
                mfCreditQueryApp = (MfCreditQueryApp) EntityUtil.reflectionSetVal(mfCreditQueryApp, mfCreditQueryAppJsp, getMapByJson(ajaxData));
                mfCreditQueryAppFeign.update(mfCreditQueryApp);
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
        MfCreditQueryApp mfCreditQueryApp = new MfCreditQueryApp();
        try {
            FormData formCreditQueryAppAdd = formService.getFormData("CreditQueryAppAdd");
            getFormValue(formCreditQueryAppAdd, getMapByJson(ajaxData));
            if (this.validateFormData(formCreditQueryAppAdd)) {
                mfCreditQueryApp = new MfCreditQueryApp();
                setObjValue(formCreditQueryAppAdd, mfCreditQueryApp);
                mfCreditQueryAppFeign.update(mfCreditQueryApp);
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
     * 方法实现说明
     *
     * @return
     * @throws
     * @author 仇招
     * 作废功能
     * @date 2019/3/26 17:21
     */
    @RequestMapping("/invalidAjax")
    @ResponseBody
    public Map <String, Object> invalidAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCreditQueryApp mfCreditQueryApp = new MfCreditQueryApp();
        mfCreditQueryApp.setId(id);
        mfCreditQueryApp.setAppSts(BizPubParm.CREDIT_QUERY_APP_STS_INVALID);
        try {
            mfCreditQueryAppFeign.update(mfCreditQueryApp);
            dataMap.put("flag", "success");
            dataMap.put("msg", "更新成功");
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
        FormData formCreditQueryAppAdd = formService.getFormData("CreditQueryAppAdd");
        MfCreditQueryApp mfCreditQueryApp = new MfCreditQueryApp();
        mfCreditQueryApp.setId(id);
        mfCreditQueryApp = mfCreditQueryAppFeign.getById(mfCreditQueryApp);
        getObjValue(formCreditQueryAppAdd, mfCreditQueryApp, formData);
        if (mfCreditQueryApp != null) {
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
    public Map <String, Object> deleteAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map <String, Object> dataMap = new HashMap <String, Object>();
        MfCreditQueryApp mfCreditQueryApp = new MfCreditQueryApp();
        mfCreditQueryApp.setId(id);
        try {
            mfCreditQueryAppFeign.delete(mfCreditQueryApp);
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
    public String input(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        // 获取个人信息
        MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
        // 初始化表单
        String id = WaterIdUtil.getWaterId("cqa");
        MfCreditQueryApp mfCreditQueryApp = new MfCreditQueryApp();
        mfCreditQueryApp.setId(id);
        mfCreditQueryApp.setOpName(User.getRegName(request));
        mfCreditQueryApp.setRegDate(DateUtil.getDate());

        FormData formCreditQueryAppAdd = formService.getFormData("CreditQueryAppAdd");
        this.getObjValue(formCreditQueryAppAdd, mfCusCustomer);
        this.getObjValue(formCreditQueryAppAdd, mfCreditQueryApp);

        model.addAttribute("formCreditQueryAppAdd", formCreditQueryAppAdd);
        model.addAttribute("query", "");
        return "/component/auth/MfCreditQueryApp_Insert";
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
        FormData formCreditQueryAppDetail = formService.getFormData("CreditQueryAppDetail");
        getFormValue(formCreditQueryAppDetail);
        MfCreditQueryApp mfCreditQueryApp = new MfCreditQueryApp();
        mfCreditQueryApp.setId(id);
        mfCreditQueryApp = mfCreditQueryAppFeign.getById(mfCreditQueryApp);
        // 获取个人信息
        MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(mfCreditQueryApp.getCusNo());

        getObjValue(formCreditQueryAppDetail, mfCreditQueryApp);
        model.addAttribute("formCreditQueryAppDetail", formCreditQueryAppDetail);
        model.addAttribute("mfCreditQueryApp", mfCreditQueryApp);
        model.addAttribute("cusType", mfCusCustomer.getCusType());
        model.addAttribute("query", "");
        return "/component/auth/MfCreditQueryApp_Detail";
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
        FormData formCreditQueryAppAdd = formService.getFormData("CreditQueryAppAdd");
        getFormValue(formCreditQueryAppAdd);
        boolean validateFlag = this.validateFormData(formCreditQueryAppAdd);
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
        FormData formCreditQueryAppAdd = formService.getFormData("CreditQueryAppAdd");
        getFormValue(formCreditQueryAppAdd);
        boolean validateFlag = this.validateFormData(formCreditQueryAppAdd);
    }
}
