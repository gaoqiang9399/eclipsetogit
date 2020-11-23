package app.component.collateral.maintenance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.base.User;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.CertiInfo;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateral.maintenance.feign.MfMaintenanceFeign;
import app.component.common.EntityUtil;
import app.component.collateral.maintenance.entity.MfMaintenance;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: MfMaintenanceAction.java
 * Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 15 15:31:13 CST 2018
 **/
@Controller
@RequestMapping("/mfMaintenance")
public class MfMaintenanceController extends BaseFormBean {
    @Autowired
    private MfMaintenanceFeign mfMaintenanceFeign;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
    @Autowired
    private PledgeBaseInfoFeign pledgeBaseInfoFeign;


    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        MfMaintenance mfMaintenance = new MfMaintenance();
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfMaintenance",mfMaintenance));
        @SuppressWarnings("unchecked")
        List<MfMaintenance> mfMaintenanceList = (List<MfMaintenance>) mfMaintenanceFeign.findByPage(ipage).getResult();
        model.addAttribute("mfMaintenanceList", mfMaintenanceList);
        model.addAttribute("query", "");
        return "/component/collateral/maintenance/MfMaintenance_List";
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
            dataMap = getMapByJson(ajaxData);
            FormData formdlinsinfo0002 = formService.getFormData(dataMap.get("formId").toString());
            getFormValue(formdlinsinfo0002, getMapByJson(ajaxData));
            if (this.validateFormData(formdlinsinfo0002)) {
                MfMaintenance mfMaintenance = new MfMaintenance();
                String insId = WaterIdUtil.getWaterId();
                setObjValue(formdlinsinfo0002, mfMaintenance);
                mfMaintenance.setMaintenanceId(insId);
                String collateralNo = mfMaintenance.getCollateralId();
                mfMaintenanceFeign.insert(mfMaintenance);

                // 获得基本信息的展示表单ID，并将表单解析
                MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
                        .getByPledgeImPawnType(mfMaintenance.getClassId(), "MfMaintenanceAction", "");
                String tableId = null;
                if (mfCollateralFormConfig == null) {

                } else {
                    tableId = mfCollateralFormConfig.getShowModelDef();
                }

                if (StringUtil.isEmpty(tableId)) {
                    // Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
                    // + "的InsInfoController列表table" + tableId
                    // + ".xml文件不存在");
                }

                mfMaintenance = new MfMaintenance();
                mfMaintenance.setCollateralId(collateralNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfMaintenance",mfMaintenance));
                JsonTableUtil jtu = new JsonTableUtil();
                @SuppressWarnings("unchecked")
                String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
                        (List<CertiInfo>) mfMaintenanceFeign.findByPage(ipage).getResult(), null, true);
                dataMap.put("htmlStr", tableHtml);
                dataMap.put("htmlStrFlag", "1");
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateByOneAjax")
    @ResponseBody
    public Map<String, Object> updateByOneAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 这里得到的formId是带form字符串的，比如formcuscorp0001
        String formId = "";
        formId = getMapByJson(ajaxData).get("formId").toString();
        if (StringUtil.isEmpty(formId)) {
            // formId =
            // mfCollateralFormConfigFeign.getByPledgeImPawnType("base",
            // "insInfoAction").getShowModel();
        } else {
            if (formId.indexOf("form") == -1) {
            } else {
                formId = formId.substring(4);
            }
        }
        FormData formdlinsinfo0005 = formService.getFormData(formId);
        getFormValue(formdlinsinfo0005, getMapByJson(ajaxData));
        MfMaintenance mfMaintenanceJsp = new MfMaintenance();
        setObjValue(formdlinsinfo0005, mfMaintenanceJsp);
        MfMaintenance mfMaintenance = mfMaintenanceFeign.getById(mfMaintenanceJsp);
        if (mfMaintenance != null) {
            try {
                mfMaintenance = (MfMaintenance) EntityUtil.reflectionSetVal(mfMaintenance, mfMaintenanceJsp, getMapByJson(ajaxData));
                mfMaintenanceFeign.update(mfMaintenance);
                dataMap.put("flag", "success");
                dataMap.put("msg", "保存成功");
            } catch (Exception e) {
                e.printStackTrace();
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
        MfMaintenance mfMaintenance = new MfMaintenance();
        try {
            FormData formdlinsinfo0002 = formService.getFormData("dlinsinfo0002");
            getFormValue(formdlinsinfo0002, getMapByJson(ajaxData));
            if (this.validateFormData(formdlinsinfo0002)) {
                setObjValue(formdlinsinfo0002, mfMaintenance);
                mfMaintenanceFeign.update(mfMaintenance);

                // 获得基本信息的展示表单ID，并将列表解析
                MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
                        .getByPledgeImPawnType(mfMaintenance.getClassId(), "MfMaintenanceAction", "");
                String tableId = "";
                if (mfCollateralFormConfig == null) {

                } else {
                    tableId = mfCollateralFormConfig.getShowModelDef();
                }

                if (StringUtil.isEmpty(tableId)) {
                    // Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
                    // + "的EvalInfoController列表table" + tableId
                    // + ".xml文件不存在");
                }

                String collateralNo = mfMaintenance.getCollateralId();
                mfMaintenance = new MfMaintenance();
                mfMaintenance.setCollateralId(collateralNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfMaintenance",mfMaintenance));
                JsonTableUtil jtu = new JsonTableUtil();
                @SuppressWarnings("unchecked")
                String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
                        (List<EvalInfo>) mfMaintenanceFeign.findByPage(ipage).getResult(), null, true);
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
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String maintenanceId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfMaintenance mfMaintenance = new MfMaintenance();
        mfMaintenance.setMaintenanceId(maintenanceId);
        mfMaintenance = mfMaintenanceFeign.getById(mfMaintenance);
        if (mfMaintenance != null) {
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }


    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/input")
    public String input(Model model, String collateralNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();

        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(collateralNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        String classId = pledgeBaseInfo.getClassId();

        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
                "MfMaintenanceAction", "");
        String formId = "";
        if (mfCollateralFormConfig == null) {

        } else {
            formId = mfCollateralFormConfig.getAddModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
            // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
            // "的InsInfoController表单信息没有查询到");
        } else {
            MfMaintenance mfMaintenance = new MfMaintenance();
            mfMaintenance.setCollateralId(collateralNo);
            mfMaintenance.setClassId(classId);
            mfMaintenance.setOpName(User.getRegName(request));
            mfMaintenance.setRegTime(DateUtil.getDateTime());
            FormData formmaintenancebase = formService.getFormData(formId);
            if (formmaintenancebase.getFormId() == null) {
                // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
                // "的InsInfoController表单form" + formId
                // + ".xml文件不存在");
            } else {
                getFormValue(formmaintenancebase);
                getObjValue(formmaintenancebase, mfMaintenance);
                model.addAttribute("formmaintenancebase", formmaintenancebase);
            }
        }
        model.addAttribute("query", "");
        return "/component/collateral/maintenance/MfMaintenance_Insert";
    }

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(Model model, String maintenanceId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        MfMaintenance mfMaintenance = new MfMaintenance();
        mfMaintenance.setMaintenanceId(maintenanceId);
        mfMaintenance = mfMaintenanceFeign.getById(mfMaintenance);

        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(mfMaintenance.getCollateralId());
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        String classId = pledgeBaseInfo.getClassId();
        mfMaintenance.setClassId(classId);// 修改时候用
        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
                "MfMaintenanceAction", "");
        String formId = "";
        if (mfCollateralFormConfig == null) {

        } else {
            if ("1".equals(mfCollateralFormConfig.getShowType())) {
                formId = mfCollateralFormConfig.getShowModelDef();
            } else {
                formId = mfCollateralFormConfig.getAddModelDef();
            }
        }

        if (StringUtil.isEmpty(formId)) {
            // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
            // "的InsInfoController表单信息没有查询到");
        } else {
            FormData formdlinsinfo0002 = formService.getFormData(formId);
            if (formdlinsinfo0002.getFormId() == null) {
                // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
                // "的InsInfoController表单form" + formId
                // + ".xml文件不存在");
            } else {
                getFormValue(formdlinsinfo0002);
                getObjValue(formdlinsinfo0002, mfMaintenance);
                model.addAttribute("maintenancedetail", formdlinsinfo0002);
            }
        }

        model.addAttribute("query", "");
        return "/component/collateral/maintenance/MfMaintenance_Detail";
    }


    // 列表展示详情，单字段编辑
    @RequestMapping("/listShowDetailAjax")
    @ResponseBody
    public Map<String, Object> listShowDetailAjax(String maintenanceId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        request.setAttribute("ifBizManger", "2");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        MfMaintenance mfMaintenance = new MfMaintenance();
        mfMaintenance.setMaintenanceId(maintenanceId);
        mfMaintenance = mfMaintenanceFeign.getById(mfMaintenance);
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(mfMaintenance.getCollateralId());
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        String classId = pledgeBaseInfo.getClassId();
        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
                "MfMaintenanceAction", "");
        String formId = "";
        if (mfCollateralFormConfig == null) {
        } else {
            formId = mfCollateralFormConfig.getListModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
            // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
            // "的InsInfoController列表详情表单信息没有查询到");
        }
        FormData formdlinsinfo0003 = formService.getFormData("maintenancedetail");
        if (formdlinsinfo0003.getFormId() == null) {
            // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
            // "的InsInfoController列表详情表单信息没有查询到");
            dataMap.put("msg", "form" + formId + "获取失败");
            dataMap.put("flag", "error");
            return dataMap;
        }
        getObjValue(formdlinsinfo0003, mfMaintenance, formData);
        String htmlStrCorp = jsonFormUtil.getJsonStr(formdlinsinfo0003, "propertySeeTag", "");
        dataMap.put("formHtml", htmlStrCorp);
        dataMap.put("flag", "success");
        dataMap.put("formData", mfMaintenance);
        return dataMap;
    }



}
