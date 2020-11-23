package app.component.frontview.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import app.component.common.EntityUtil;
import app.component.frontview.entity.VwProductManage;
import app.component.frontview.feign.VwProductManageFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.util.toolkit.Ipage;
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

/**
 * Title: VwProductManageController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 26 15:30:56 CST 2018
 **/
@Controller
@RequestMapping(value = "/vwProductManage")
public class VwProductManageController extends BaseFormBean{
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private VwProductManageFeign vwProductManageFeign;

    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/frontview/VwProductManage_List";
    }
    //选择组件的查询（查询为 产品种类表-mf_sys_kind）
    @RequestMapping("/getsyskindinfo")
    @ResponseBody
    public Map<String, Object> getsyskindinfo(String ajaxData,String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        VwProductManage vwProductManage = new VwProductManage();
        try {
            vwProductManage.setCustomQuery(ajaxData);//自定义查询参数赋值
            //vwProductManage.setCriteriaList(vwProductManage, ajaxData);//我的筛选
            //vwProductManage.setCustomSorts(ajaxData);//自定义排序
            //this.getRoleConditions(vwProductManage,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("vwProductManage", vwProductManage));
            //自定义查询Feign方法
            ipage = vwProductManageFeign.getsyskindinfo(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage",ipage);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/findByPageAjax")
    public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        VwProductManage vwProductManage = new VwProductManage();
        try {
            vwProductManage.setCustomQuery(ajaxData);//自定义查询参数赋值
            vwProductManage.setCriteriaList(vwProductManage, ajaxData);//我的筛选
            vwProductManage.setCustomSorts(ajaxData);//自定义排序
            //this.getRoleConditions(vwProductManage,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("vwProductManage", vwProductManage));
            //自定义查询Feign方法
            ipage = vwProductManageFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage",ipage);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX新增
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            FormData formaddvmproduct = formService.getFormData("addvmproduct");
            getFormValue(formaddvmproduct, getMapByJson(ajaxData));
            if(this.validateFormData(formaddvmproduct)){
                VwProductManage vwProductManage = new VwProductManage();
                vwProductManage.setProductNo(WaterIdUtil.getWaterId());
                vwProductManage.setChoicetst("1");
                setObjValue(formaddvmproduct, vwProductManage);
                vwProductManageFeign.insert(vwProductManage);
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
     * ajax 异步 单个字段或多个字段更新
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData forminfosvwproduct = formService.getFormData("infosvwproduct");
        getFormValue(forminfosvwproduct, getMapByJson(ajaxData));
        VwProductManage vwProductManageJsp = new VwProductManage();
        setObjValue(forminfosvwproduct, vwProductManageJsp);
        VwProductManage vwProductManage = vwProductManageFeign.getById(vwProductManageJsp);
        if(vwProductManage!=null){
            try{
                vwProductManage = (VwProductManage)EntityUtil.reflectionSetVal(vwProductManage, vwProductManageJsp, getMapByJson(ajaxData));
                vwProductManageFeign.update(vwProductManage);
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
    }
    /**
     * AJAX更新保存
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        VwProductManage vwProductManage = new VwProductManage();
        try{
            FormData forminfosvwproduct = formService.getFormData("infosvwproduct");
            getFormValue(forminfosvwproduct, getMapByJson(ajaxData));
            if(this.validateFormData(forminfosvwproduct)){
                vwProductManage = new VwProductManage();
                setObjValue(forminfosvwproduct, vwProductManage);
                vwProductManageFeign.update(vwProductManage);
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "更新失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX获取查看
     * @return
     * @throws Exception
     */
    @RequestMapping("/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String productNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String,Object> formData = new HashMap<String,Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData forminfosvwproduct = formService.getFormData("infosvwproduct");
        VwProductManage vwProductManage = new VwProductManage();
        vwProductManage.setProductNo(productNo);
        vwProductManage = vwProductManageFeign.getById(vwProductManage);
        getObjValue(forminfosvwproduct, vwProductManage,formData);
        if(vwProductManage!=null){
            dataMap.put("flag", "success");
        }else{
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }
    /**
     * Ajax异步删除
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String productNo) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        VwProductManage vwProductManage = new VwProductManage();
        vwProductManage.setProductNo(productNo);
        try {
            vwProductManageFeign.delete(vwProductManage);
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
     * @return
     * @throws Exception
     */
    @RequestMapping("/input")
    public String input(Model model) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formaddvmproduct = formService.getFormData("addvmproduct");
        model.addAttribute("formaddvmproduct", formaddvmproduct);
        model.addAttribute("query", "");
        return "/component/frontview/VwProductManage_Insert";
    }
    /**
     * 详情页面查询
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(Model model,String productNo) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData forminfosvwproduct = formService.getFormData("infosvwproduct");
        getFormValue(forminfosvwproduct);
        VwProductManage vwProductManage = new VwProductManage();
        vwProductManage.setProductNo(productNo);
        vwProductManage = vwProductManageFeign.getById(vwProductManage);
        getObjValue(forminfosvwproduct, vwProductManage);
        model.addAttribute("forminfosvwproduct", forminfosvwproduct);
        model.addAttribute("query", "");
        return "/component/frontview/VwProductManage_Detail";
    }

    /**
     * 新增校验
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void validateInsert() throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData forminfosvwproduct = formService.getFormData("infosvwproduct");
        getFormValue(forminfosvwproduct);
        boolean validateFlag = this.validateFormData(forminfosvwproduct);
    }
    /**
     * 修改校验
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void validateUpdate() throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData forminfosvwproduct = formService.getFormData("infosvwproduct");
        getFormValue(forminfosvwproduct);
        boolean validateFlag = this.validateFormData(forminfosvwproduct);
    }
}
