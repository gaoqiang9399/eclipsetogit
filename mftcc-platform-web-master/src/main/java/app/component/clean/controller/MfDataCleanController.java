package app.component.clean.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.sys.entity.SysUser;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.clean.entity.MfDataClean;
import app.component.clean.feign.MfDataCleanFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Title: MfDataCleanController.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 07 11:53:20 CST 2017
 **/
@Controller
@RequestMapping("/mfDataClean")
public class MfDataCleanController extends BaseFormBean {
    // 注入业务层
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfDataCleanFeign mfDataCleanFeign;

    /**
     * 方法描述： 列表打开页面请求
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-4-24 上午11:20:16
     */
    @RequestMapping("/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/clean/MfDataClean_List";
    }

    /**
     * 方法描述： 获取列表数据
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-4-24 下午12:03:04
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                              String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfDataClean mfDataClean = new MfDataClean();
        try {
            mfDataClean.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfDataClean.setCriteriaList(mfDataClean, ajaxData);// 我的筛选
            // this.getRoleConditions(mfBusApply,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);// 异步传页面翻页尺寸

            ipage.setParams(this.setIpageParams("mfDataClean", mfDataClean));

            // 自定义查询Feign方法
            ipage = mfDataCleanFeign.findByPage(ipage);
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
     * 方法描述： 获取清理详情页面
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-4-25 上午10:03:04
     */
    @RequestMapping("/getDetailPage")
    public String getDetailPage(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfDataClean mfDataClean = new MfDataClean();
        mfDataClean.setCusNo(cusNo);
        dataMap = mfDataCleanFeign.getDetailPage(mfDataClean);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("cusNo", cusNo);
        return "/component/clean/MfDataClean_Detail";
    }

    /**
     * 方法描述： 验证客户信息是否能够被删除（判断该客户的押品是否被其他项目引用）
     *
     * @return String
     * @author zhs
     * @date 2017-4-28 下午4:49:59
     */
    @RequestMapping("/verifyCusCleanFlagAjax")
    @ResponseBody
    public Map<String, Object> verifyCusCleanFlagAjax(String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfDataClean mfDataClean = new MfDataClean();
        mfDataClean.setCusNo(cusNo);
        try {
            dataMap = mfDataCleanFeign.verifyCusCleanFlag(mfDataClean);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("清理"));
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 清理单条客户数据
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-4-7 下午3:19:51
     */
    @RequestMapping("/deleteCusAjax")
    @ResponseBody
    public Map<String, Object> deleteCusAjax(String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("cusNo", cusNo);
        try {
            mfDataCleanFeign.deleteCus(paramMap);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("清理"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("清理"));
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 一键清理客户所有数据（包括客户、业务、押品及归档信息）
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-4-7 下午3:21:30
     */
    @RequestMapping("/deleteBatchAjax")
    @ResponseBody
    public Map<String, Object> deleteBatchAjax(String password) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            if(StringUtils.isNotEmpty(password) && password.equals("zgcdb@123")){
                mfDataCleanFeign.deleteBatch();
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("清理"));
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", "密码错误！");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("清理"));
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 清理单条业务数据
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-4-7 下午3:19:51
     */
    @RequestMapping("/deleteBusAjax")
    @ResponseBody
    public Map<String, Object> deleteBusAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("ajaxData", ajaxData);
        try {
            mfDataCleanFeign.deleteBus(paramMap);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("清理"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("清理"));
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 批量清理业务数据
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-4-7 下午3:21:30
     */
    @RequestMapping("/deleteBusBatchAjax")
    @ResponseBody
    public Map<String, Object> deleteBusBatchAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JSONArray jsonArray = JSONArray.fromObject(ajaxData);
        List<Map<String, String>> paramList = (List<Map<String, String>>) JSONArray.toList(jsonArray,
                new HashMap<String, String>(), new JsonConfig());
        try {
            mfDataCleanFeign.deleteBusBatch(paramList);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 跳转部门清理页面
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-3-13 下午19:00:30
     */
    @RequestMapping(value = "/brClearInfo")
    public String brclearInfo(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        String formId = "clearBrEdit";
        FormData formclearBrEdit = formService.getFormData(formId);
        getFormValue(formclearBrEdit);
        model.addAttribute("formclearBrEdit", formclearBrEdit);
        model.addAttribute("query", "");
        return "/component/clean/MfBrClearInfo";
    }


    /**
     * 方法描述： 根据部门选择操作员
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-3-13 下午19:00:30
     */
    @RequestMapping(value = "/selectOpByBr")
    @ResponseBody
    public Map<String, Object> selectOpByBr(int pageNo, String ajaxData, String brNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            sysUser.setBrNo(brNo);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setParams(this.setIpageParams("sysUser", sysUser));
            ipage = mfDataCleanFeign.selectOpByBr(ipage);
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
     * 方法描述： 展示客户业务列表信息
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-3-13 下午19:00:30
     */
    @RequestMapping(value = "/cusBusinessInfo")
    @ResponseBody
    public Map<String, Object> cusBusinessInfo(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        try {
            Map map = getMapByJson(ajaxData);
            FormData formclearBrEdit = formService.getFormData((String) map.get("formId"));
            getFormValue(formclearBrEdit, map);
            MfDataClean mfDataClean = new MfDataClean();
            setObjValue(formclearBrEdit, mfDataClean);
            List<MfDataClean> brInfoList = mfDataCleanFeign.cusBusinessInfo(mfDataClean);
            String htmlStr = "";
            String tableFormId = "tablebrclear0001";
            JsonTableUtil jtu = new JsonTableUtil();
            htmlStr = jtu.getJsonStr(tableFormId, "tableTag", brInfoList, null, true);
            dataMap.put("htmlStr", htmlStr);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }

        return dataMap;
    }


    /**
     * 方法描述： 部门客户清理
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-3-13 下午19:00:30
     */
    @RequestMapping(value = "/dataBrClear")
    @ResponseBody
    public Map<String, Object> dataBrClear(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> paramMap = new HashMap<String, String>();
        FormService formService = new FormService();
        try {
            Map map = getMapByJson(ajaxData);
            FormData formclearBrEdit = formService.getFormData((String) map.get("formId"));
            getFormValue(formclearBrEdit, map);
            //非空检验
            if (this.validateFormData(formclearBrEdit)) {
                setObjValue(formclearBrEdit, paramMap);
                Map<String, Object> clear = mfDataCleanFeign.dataBrClear(paramMap);
                dataMap.put("noClearName", clear.get("noClearName"));
                dataMap.put("cleanFlag", clear.get("cleanFlag"));
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("清理"));
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("清理"));
            throw e;
        }
        return dataMap;

    }
}
