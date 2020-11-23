package app.component.eval.controller;

import app.component.eval.entity.AppProperty;
import app.component.eval.entity.EvalScenceConfig;
import app.component.eval.entity.MfEvalIndexSub;
import app.component.eval.entity.MfFormItemConfig;
import app.component.eval.feign.AppPropertyFeign;
import app.component.eval.feign.MfEvalIndexSubFeign;
import app.component.eval.feign.MfFormItemConfigFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pfs.entity.CusFinForm;
import app.component.pfsinterface.PfsInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import app.tech.oscache.CodeUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: AppPropertyController.java Description:
 *
 * @author:jzh@dhcc.com.cn
 * @Tue Mar 29 01:44:29 GMT 2016
 **/
@Controller
@RequestMapping("/appProperty")
public class AppPropertyController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private AppPropertyFeign appPropertyFeign;
    @Autowired
    private MfFormItemConfigFeign mfFormItemConfigFeign;
    @Autowired
    private PfsInterfaceFeign pfsInterfaceFeign;
    @Autowired
    private MfEvalIndexSubFeign mfEvalIndexSubFeign;

    /**
     * 列表有翻页
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formapp0091 = formService.getFormData("app0091");
        AppProperty appProperty = new AppProperty();
        Ipage ipage = this.getIpage();
        List<AppProperty> appPropertyList = (List<AppProperty>) appPropertyFeign.findByPage(ipage, appProperty).getResult();
        model.addAttribute("formapp0091", formapp0091);
        model.addAttribute("appPropertyList", appPropertyList);
        model.addAttribute("query", "");
        return "/component/eval/getListPage";
    }

    @RequestMapping("/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                              String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        AppProperty appProperty = new AppProperty();
        try {
            appProperty.setCustomQuery(ajaxData);
            appProperty.setCriteriaList(appProperty, ajaxData);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);
            ipage = appPropertyFeign.findByPage(ipage, appProperty);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List<?>) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 列表全部无翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getListAll")
    public String getListAll(Model model) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formapp0091 = formService.getFormData("app0091");
        AppProperty appProperty = new AppProperty();
        List<AppProperty> appPropertyList = appPropertyFeign.getAll(appProperty);
        model.addAttribute("appPropertyList", appPropertyList);
        model.addAttribute("formapp0091", formapp0091);
        model.addAttribute("query", "");
        return "/component/eval/getListPage";
    }

    @RequestMapping("/inputAjax")
    @ResponseBody
    public Map<String, Object> inputAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        AppProperty appProperty = new AppProperty();
        FormData formapp0091 = formService.getFormData("app0091");
        getObjValue(formapp0091, appProperty);
        JsonFormUtil jfu = new JsonFormUtil();
        String formHtml = jfu.getJsonStr(formapp0091, "bigFormTag", "");
        dataMap.put("formHtml", formHtml);
        dataMap.put("flag", "success");
        return dataMap;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/input")
    public String input(Model model, String propertyAppType) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formapp0091 = formService.getFormData("app0091");
        AppProperty appProperty = new AppProperty();
        appProperty.setPropertyAppType(propertyAppType);
        getObjValue(formapp0091, appProperty);
        MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
        mfFormItemConfig.setUseFlag("1");
        Map<String, Object> dataMap = mfFormItemConfigFeign.getMfFormItemConfigList(mfFormItemConfig);
        List<MfFormItemConfig> getAllUseablelist = (List<MfFormItemConfig>) dataMap.get("getAllUseablelist");
        List<MfFormItemConfig> getByformEnList = (List<MfFormItemConfig>) dataMap.get("getByformEnList");
        JSONArray allUseableArray = JSONArray.fromObject(getAllUseablelist);
        JSONArray byformEnArray = JSONArray.fromObject(getByformEnList);
        JSONObject json = new JSONObject();
        json.put("allUseablelist", allUseableArray);
        json.put("byformEnList", byformEnArray);
        model.addAttribute("formapp0091", formapp0091);
        model.addAttribute("getByformEnList", getByformEnList);
        model.addAttribute("json", json);
        model.addAttribute("query", "");
        return "/component/eval/AppProperty_Insert";
    }

    /**
     * 方法描述：
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2016-11-7 下午4:05:47
     */
    @RequestMapping("/getByformEnList")
    @ResponseBody
    public Map<String, Object> getByformEnList(String formNameEn) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
        mfFormItemConfig.setUseFlag("1");
        mfFormItemConfig.setFormNameEn(formNameEn);
        List<MfFormItemConfig> list = mfFormItemConfigFeign.getFormNameEnList(mfFormItemConfig);
        if (list != null) {
            dataMap.put("list", list);
            dataMap.put("flag", "success");
        } else {
            dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
            dataMap.put("flag", "error");
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
            FormData formapp0091 = formService.getFormData("app0091");
            getFormValue(formapp0091, getMapByJson(ajaxData));
            if (this.validateFormData(formapp0091)) {
                AppProperty appProperty = new AppProperty();
                AppProperty appPropertyTmp = new AppProperty();
                setObjValue(formapp0091, appProperty);
                appPropertyTmp.setPropertyNo(appProperty.getPropertyNo());
                appPropertyTmp = appPropertyFeign.getById(appPropertyTmp);
                if (appPropertyTmp != null && (appPropertyTmp.getPropertyId()).equals(appProperty.getPropertyId())) {
                    appPropertyFeign.update(appProperty);
                    dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
                    dataMap.put("flag", "success");
                } else if (appPropertyTmp != null && !(appPropertyTmp.getPropertyId()).equals(appProperty.getPropertyId())) {
                    String msgContent = null;
                    String propertyNo = appPropertyTmp.getPropertyNo();//指标
                    String propertyName = appPropertyTmp.getPropertyName();//指标名称
                    appProperty.setPropertyNo(propertyNo);
                    appProperty = appPropertyFeign.getById(appProperty);
                    String evalPropertyType = null;//指标类型
                    String evalScenceName1 = null;//评级模型名称
                    String evalScenceName2 = null;//评级模型名称
                    Map<String, ParmDic> typeMap = new CodeUtils().getMapObjByKeyName("EVAL_INDEX_TYPE");
                    evalPropertyType = typeMap.get(appProperty.getEvalPropertyType()).getOptName();
                    //evalPropertyType = (new CodeUtils().getMapByKeyName("EVAL_INDEX_TYPE")).get(appProperty.getEvalPropertyType().);
                    List<EvalScenceConfig> evalScenceConfigList = appPropertyFeign.getEvalScenceConfigListByAppPro(appPropertyTmp);
                    if (null != evalScenceConfigList && evalScenceConfigList.size() > 0) {//如果有使用该指标的评级模型，则带出模型名称
                        EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
                        if(evalScenceConfigList.size()>1){
                            evalScenceConfig = evalScenceConfigList.get(0);
                            evalScenceName1 = evalScenceConfig.getEvalScenceName();
                            evalScenceConfig = evalScenceConfigList.get(1);
                            evalScenceName2 = evalScenceConfig.getEvalScenceName();
                            msgContent = "评级指标："+propertyNo+"，指标名称："+propertyName+"，指标类型："+evalPropertyType+"已存在，并用于系统"+evalScenceName1+"、"+evalScenceName2+"等评级模型中。";
                        }else {
                            evalScenceConfig = evalScenceConfigList.get(0);
                            evalScenceName1 = evalScenceConfig.getEvalScenceName();
                            msgContent = "评级指标："+propertyNo+"，指标名称："+propertyName+"，指标类型："+evalPropertyType+"已存在，并用于系统"+evalScenceName1+"评级模型中。";
                        }
                    }else {
                        msgContent = "评级指标："+propertyNo+"，指标名称："+propertyName+"，指标类型："+evalPropertyType+"已存在！";
                    }
                    dataMap.put("flag", "error");
                    dataMap.put("msg", msgContent);
                } else {
                    appProperty = appPropertyFeign.insert(appProperty);
                    dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                    dataMap.put("flag", "success");
                }
                dataMap.put("propertyId", appProperty.getPropertyId());
                dataMap.put("propertyNo", appProperty.getPropertyNo());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
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
        try {
            FormData formapp0091 = formService.getFormData("app0091");
            getFormValue(formapp0091, getMapByJson(ajaxData));
            if (this.validateFormData(formapp0091)) {
                AppProperty appProperty = new AppProperty();
                AppProperty appPropertyTmp = new AppProperty();
                setObjValue(formapp0091, appProperty);
                appPropertyTmp.setPropertyNo(appProperty.getPropertyNo());
                appPropertyTmp = appPropertyFeign.getById(appPropertyTmp);
                if (appPropertyTmp != null && !(appPropertyTmp.getPropertyId()).equals(appProperty.getPropertyId())) {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("该评级指标"));
                } else {
                    appPropertyFeign.update(appProperty);
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
                }
                dataMap.put("propertyId", appProperty.getPropertyId());
                dataMap.put("propertyNo", appProperty.getPropertyNo());
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
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateStsAjax")
    @ResponseBody
    public Map<String, Object> updateStsAjax(String ajaxData, String tableId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONObject jobj = JSONObject.fromObject(ajaxData);
            FormData formapp0091 = formService.getFormData("app0091");
            getFormValue(formapp0091, jobj);
            AppProperty appProperty = new AppProperty();
            setObjValue(formapp0091, appProperty);
            int count = appPropertyFeign.updateSts(appProperty);
            if (count > 0) {
                appProperty = appPropertyFeign.getById(appProperty);
                List<AppProperty> list = new ArrayList<AppProperty>();
                list.add(appProperty);
                getTableData(dataMap, tableId, list);// 获取列表
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    private void getTableData(Map<String, Object> dataMap, String tableId, List<AppProperty> list) throws Exception {
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null, true);
        dataMap.put("tableData", tableHtml);
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String ajaxData, String propertyNo, String propertyAppType) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formapp0091 = formService.getFormData("app0091");
        AppProperty appProperty = new AppProperty();
        appProperty.setPropertyNo(propertyNo);
        appProperty.setPropertyAppType(propertyAppType);
        appProperty = appPropertyFeign.getById(appProperty);
        getObjValue(formapp0091, appProperty, formData);
        if (appProperty != null) {
            JsonFormUtil jfu = new JsonFormUtil();
            this.changeFormProperty(formapp0091, "propertyNo", "readonly", "1");
            String formHtml = jfu.getJsonStr(formapp0091, "bigFormTag", "");
            dataMap.put("formHtml", formHtml);
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/getById")
    public String getById(Model model, String propertyId, String propertyNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formapp0091 = formService.getFormData("app0091");
        AppProperty appProperty = new AppProperty();
        appProperty.setPropertyId(propertyId);
        appProperty.setPropertyNo(propertyNo);
        appProperty = appPropertyFeign.getById(appProperty);
        CusFinForm cusFinForm = pfsInterfaceFeign.getcusFinForm(appProperty.getPropertyNo());
        if (cusFinForm != null) {
            appProperty.setDataSource(cusFinForm.getFormDesc());
        }
        getObjValue(formapp0091, appProperty);
        // 获得评级指标子项
        MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
        mfEvalIndexSub.setPropertyNo(propertyNo);
        List<MfEvalIndexSub> mfEvalIndexSubList = mfEvalIndexSubFeign.getMfEvalIndexSubList(mfEvalIndexSub);

        MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
        mfFormItemConfig.setUseFlag("1");
        Map<String, Object> dataMap = mfFormItemConfigFeign.getMfFormItemConfigList(mfFormItemConfig);
        List<MfFormItemConfig> getAllUseablelist = (List<MfFormItemConfig>) dataMap.get("getAllUseablelist");
        List<MfFormItemConfig> getByformEnList = (List<MfFormItemConfig>) dataMap.get("getByformEnList");
        JSONArray allUseableArray = JSONArray.fromObject(getAllUseablelist);
        JSONArray byformEnArray = JSONArray.fromObject(getByformEnList);
        JSONObject json = new JSONObject();
        json.put("allUseablelist", allUseableArray);
        json.put("byformEnList", byformEnArray);
        model.addAttribute("mfEvalIndexSubList", mfEvalIndexSubList);
        model.addAttribute("json", json);
        model.addAttribute("formapp0091", formapp0091);
        model.addAttribute("getByformEnList", getByformEnList);
        model.addAttribute("appProperty", appProperty);
        model.addAttribute("query", "");
        return "/component/eval/AppProperty_Detail";
    }

    /**
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String ajaxData, String propertyId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        AppProperty appProperty = new AppProperty();
        appProperty.setPropertyId(propertyId);
        try {
            appPropertyFeign.delete(appProperty);
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
     * 获取业务list
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getAppPropertyForList")
    @ResponseBody
    public Map<String, Object> getAppPropertyForList(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONObject jb = JSONObject.fromObject(ajaxData);
            dataMap = appPropertyFeign.getAppPropertyByTableFlag(jb);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 打开添加指标项
     *
     * @param model
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018年5月21日 下午4:22:46
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/inputAppProperty")
    public String inputAppProperty(Model model) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formapp0091 = formService.getFormData("app0091");
        AppProperty appProperty = new AppProperty();
        //appProperty.setPropertyAppType(propertyAppType);
        getObjValue(formapp0091, appProperty);
        MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
        mfFormItemConfig.setUseFlag("1");
        Map<String, Object> dataMap = mfFormItemConfigFeign.getMfFormItemConfigList(mfFormItemConfig);
        List<MfFormItemConfig> getAllUseablelist = (List<MfFormItemConfig>) dataMap.get("getAllUseablelist");
        List<MfFormItemConfig> getByformEnList = (List<MfFormItemConfig>) dataMap.get("getByformEnList");
        JSONArray allUseableArray = JSONArray.fromObject(getAllUseablelist);
        JSONArray byformEnArray = JSONArray.fromObject(getByformEnList);
        JSONObject json = new JSONObject();
        json.put("allUseablelist", allUseableArray);
        json.put("byformEnList", byformEnArray);
        model.addAttribute("formapp0091", formapp0091);
        model.addAttribute("getByformEnList", getByformEnList);
        model.addAttribute("json", json);
        model.addAttribute("query", "");
        return "/component/eval/AppProperty_InsertAppProperty";
    }

    @RequestMapping("/getAllAppPropertyList")
    @ResponseBody
    public Map<String, Object> getAllAppPropertyList(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            AppProperty appProperty = new AppProperty();
            List<AppProperty> appPropertyList = appPropertyFeign.getAll(appProperty);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            if (appPropertyList != null && appPropertyList.size() > 0) {
                for (int i = 0; i < appPropertyList.size(); i++) {
                    jsonObject.put("id", appPropertyList.get(i).getPropertyNo());
                    jsonObject.put("name", appPropertyList.get(i).getPropertyName());
                    jsonArray.add(jsonObject);
                }
            }
            dataMap.put("items", jsonArray);
            dataMap.put("flag", "success");
            dataMap.put("appPropertyList", appPropertyList);
            dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
        }
        return dataMap;
    }
}
