package app.component.collateral.carcheck.controller;

import app.base.User;
import app.component.collateral.carcheck.entity.MfCarCheckForm;
import app.component.collateral.carcheck.feign.MfCarCheckFormFeign;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusGpsReg;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.nmd.entity.ParmDic;
import app.component.wkf.entity.Result;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCarCheckFormAction.java
 * Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 11 15:37:03 CST 2018
 **/
@Controller
@RequestMapping("/mfCarCheckForm")
public class MfCarCheckFormController extends BaseFormBean {

    @Autowired
    private MfCarCheckFormFeign mfCarCheckFormFeign;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private PledgeBaseInfoFeign pledgeBaseInfoFeign;
    @Autowired
    private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;


    /**
     * 打开车管验车页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String collateralNo, String classId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formCarCheckFormDetail = formService.getFormData("CarCheckFormDetail");
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        pledgeBaseInfo.setPledgeNo(collateralNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        mfCarCheckForm.setPledgeNo(collateralNo);
        if(StringUtil.isNotEmpty(pledgeBaseInfo.getVehiclePlate())){
            mfCarCheckForm.setCarLicense(pledgeBaseInfo.getVehiclePlate());
        }
        if(StringUtil.isNotEmpty(pledgeBaseInfo.getPleMediaModel())){
            mfCarCheckForm.setModelName(pledgeBaseInfo.getPleMediaModel());
        }
        mfCarCheckForm.setCheckOpName(pledgeBaseInfo.getRegCusName());
        mfCarCheckForm.setCheckBrName(pledgeBaseInfo.getRegOrgName());
        mfCarCheckForm.setDepartureTime(DateUtil.getDate());
        mfCarCheckForm.setCheckType("1");
        getObjValue(formCarCheckFormDetail, mfCarCheckForm);
        model.addAttribute("mfCarCheckForm", mfCarCheckForm);
        model.addAttribute("formCarCheckFormDetail", formCarCheckFormDetail);
        model.addAttribute("query", "");
        return "/component/collateral/carcheck/MfCarCheckForm_Insert";
    }

    /**
     * 打开用户验车页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputCus")
    public String inputCus(Model model, String cusNo, String appId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formCarCheckFormDetail = formService.getFormData("CarCheckFormDetail");
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        MfCarCheckForm mfCarCheckForm1 = new MfCarCheckForm();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        mfCarCheckForm1.setAppId(appId);
        mfCarCheckForm1 = mfCarCheckFormFeign.getById(mfCarCheckForm1);
        String pledgeNo = mfCarCheckForm1.getPledgeNo();
        pledgeBaseInfo.setPledgeNo(pledgeNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        mfCarCheckForm.setCarLicense(pledgeBaseInfo.getVehiclePlate());
        mfCarCheckForm.setModelName(pledgeBaseInfo.getPleMediaModel());
        mfCarCheckForm.setPledgeNo(pledgeNo);
        mfCarCheckForm.setAppId(appId);
        mfCarCheckForm.setCheckType("2");
        mfCarCheckForm.setShop(mfCusCustomer.getChannelSource());
        mfCarCheckForm.setCusName(mfCusCustomer.getCusName());
        mfCarCheckForm.setCheckOpName(mfCarCheckForm.getCusName());
        mfCarCheckForm.setDepartureTime(DateUtil.getDate());
        mfCarCheckForm.setAppId(appId);
        getObjValue(formCarCheckFormDetail, mfCarCheckForm);
        model.addAttribute("mfCarCheckForm", mfCarCheckForm);
        model.addAttribute("formCarCheckFormDetail", formCarCheckFormDetail);
        model.addAttribute("query", "");
        return "/component/collateral/carcheck/MfCarCheckForm_InsertCus";
    }


    /**
     * 打开登记提车信息页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputGetCarInfo")
    public String inputGetCarInfo(Model model, String appId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formCarCheckFormDetail = formService.getFormData("carCheckForm_getCarInfo");
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        mfCarCheckForm.setAppId(appId);
        mfCarCheckForm.setCheckType("1");
        mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
        getObjValue(formCarCheckFormDetail, mfCarCheckForm);
        model.addAttribute("mfCarCheckForm", mfCarCheckForm);
        model.addAttribute("carCheckForm_getCarInfo", formCarCheckFormDetail);
        model.addAttribute("query", "");
        return "/component/collateral/carcheck/MfCarCheckForm_inputGetCarInfo";
    }

    /**
     * 打开登记提车信息页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveGetCarInfoAjax")
    @ResponseBody
    public Map<String, Object> saveGetCarInfoAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            String formId = map.get("formId").toString();
            FormData formCarCheckFormDetail = formService.getFormData(formId);
            getFormValue(formCarCheckFormDetail, getMapByJson(ajaxData));
            if (this.validateFormData(formCarCheckFormDetail)) {
                MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
                setObjValue(formCarCheckFormDetail, mfCarCheckForm);
                mfCarCheckFormFeign.saveGetCarInfo(mfCarCheckForm);
                dataMap.put("msg", MessageEnum.SUCCEED_COMMIT.getMessage());
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "保存失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }

    /**
     * 车管验车新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputCar")
    public String inputCar(Model model, String collateralNo, String cusNo, String appId, String checkType) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        pledgeBaseInfo.setPledgeNo(collateralNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "MfCarCheckFormAction", "");
        String formId = null;
        if (mfCollateralFormConfig == null) {

        } else {
            formId = mfCollateralFormConfig.getAddModelDef();
        }
        FormData formCarCheckFormDetail = formService.getFormData(formId);
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        mfCarCheckForm.setCusNo(cusNo);
        mfCarCheckForm.setShop(mfCusCustomer.getChannelSource());
        mfCarCheckForm.setCusName(mfCusCustomer.getCusName());
        mfCarCheckForm.setPledgeNo(collateralNo);
        if(StringUtil.isNotEmpty(pledgeBaseInfo.getVehiclePlate())){
            mfCarCheckForm.setCarLicense(pledgeBaseInfo.getVehiclePlate());
        }
        if(StringUtil.isNotEmpty(pledgeBaseInfo.getPleMediaModel())){
            mfCarCheckForm.setModelName(pledgeBaseInfo.getPleMediaModel());
        }
        mfCarCheckForm.setAppId(appId);
        mfCarCheckForm.setCheckType(checkType);
        mfCarCheckForm.setCheckOpName(pledgeBaseInfo.getRegCusName());
        mfCarCheckForm.setCheckBrName(pledgeBaseInfo.getRegOrgName());
        mfCarCheckForm.setDepartureTime(DateUtil.getDate());
        getObjValue(formCarCheckFormDetail, mfCarCheckForm);
        model.addAttribute("mfCarCheckForm", mfCarCheckForm);
        model.addAttribute("formCarCheckFormDetail", formCarCheckFormDetail);
        model.addAttribute("query", "");
        return "/component/collateral/carcheck/MfCarCheckForm_Insert";
    }

    /**
     * 新增复验验车单页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputCheck")
    public String inputCheck(Model model, String appId) throws Exception {
        ActionContext.initialize(request, response);
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        mfCarCheckForm.setAppId(appId);
        mfCarCheckForm.setCheckType("1");
        mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
        MfCarCheckForm carCheckForm = new MfCarCheckForm();
        carCheckForm.setAppId(appId);
        carCheckForm.setCheckType("2");
        carCheckForm = mfCarCheckFormFeign.getById(carCheckForm);
        model.addAttribute("mfCarCheckForm", mfCarCheckForm);
        model.addAttribute("carCheckForm", carCheckForm);
        model.addAttribute("query", "");
        return "/component/collateral/carcheck/MfCarCheckForm_inputCheckInsert";
    }

    /**
     * 检查复验验车单按钮是否显示可点击
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCarCheck")
    @ResponseBody
    public Map<String, Object> getCarCheck(Model model, String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        MfCarCheckForm carCheckForm = new MfCarCheckForm();
        if (StringUtil.isEmpty(appId)) {
            dataMap.put("flag", "error");
            return dataMap;
        } else {
            mfCarCheckForm.setAppId(appId);
            mfCarCheckForm.setCheckType("1");
            mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
            carCheckForm.setAppId(appId);
            carCheckForm.setCheckType("2");
            carCheckForm = mfCarCheckFormFeign.getById(carCheckForm);
            if (mfCarCheckForm == null && carCheckForm == null) {
                dataMap.put("flag", "error");
                return dataMap;
            }
            dataMap.put("flag", "success");
        }
        dataMap.put("flag", "success");
        return dataMap;
    }

    // 列表展示详情，单字段编辑
    @RequestMapping("/listShowDetailAjax")
    @ResponseBody
    public Map<String, Object> listShowDetailAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        request.setAttribute("ifBizManger", "3");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        mfCarCheckForm.setId(id);
        mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(mfCarCheckForm.getPledgeNo());
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        String classId = pledgeBaseInfo.getClassId();
        MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
                "MfCarCheckFormAction", "");
        String formId = "";
        if (mfCollateralFormConfig == null) {
        } else {
            formId = mfCollateralFormConfig.getListModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
            // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
            // "的InsInfoController列表详情表单信息没有查询到");
        }
        FormData formCarCheckFormBase = formService.getFormData(formId);
        if (formCarCheckFormBase.getFormId() == null) {
            // Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
            // "的InsInfoController列表详情表单信息没有查询到");
            dataMap.put("msg", "form" + formId + "获取失败");
            dataMap.put("flag", "error");
            return dataMap;
        }
        getObjValue(formCarCheckFormBase, mfCarCheckForm, formData);
        String htmlStrCorp = jsonFormUtil.getJsonStr(formCarCheckFormBase, "propertySeeTag", "");
        dataMap.put("formHtml", htmlStrCorp);
        dataMap.put("flag", "success");
        dataMap.put("formData", mfCarCheckForm);
        return dataMap;
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        request.setAttribute("ifBizManger", "3");
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            String formId = map.get("formId").toString();
            FormData formCarCheckFormDetail = formService.getFormData(formId);
            getFormValue(formCarCheckFormDetail, getMapByJson(ajaxData));
            if (this.validateFormData(formCarCheckFormDetail)) {
                setObjValue(formCarCheckFormDetail, mfCarCheckForm);
                mfCarCheckForm.setId(WaterIdUtil.getWaterId());
                mfCarCheckFormFeign.insert(mfCarCheckForm);
                //获得验车单htmlStr
                String checkType = mfCarCheckForm.getCheckType();
                String cusNo = mfCarCheckForm.getCusNo();
                if ("1".equals(checkType) && StringUtil.isEmpty(cusNo)) {
                    MfCarCheckForm mfCarCheckFormNew = new MfCarCheckForm();
                    mfCarCheckFormNew.setPledgeNo(mfCarCheckForm.getPledgeNo());
                    Ipage ipage = this.getIpage();
                    ipage.setParams(this.setIpageParams("mfCarCheckForm", mfCarCheckFormNew));
                    ipage = mfCarCheckFormFeign.findByPage(ipage);
                    JsonTableUtil jtu = new JsonTableUtil();
                    String  tableHtml = jtu.getJsonStr("tablecarCheckFormList","tableTag",(List<MfCarCheckForm>)ipage.getResult(), null,true);
                    dataMap.put("htmlStr", tableHtml);
                }else if("1".equals(checkType) && StringUtil.isNotEmpty(cusNo)){
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                FormData formCarCheckFormBase = formService.getFormData("CarCheckFormBase");
                getFormValue(formCarCheckFormBase);
                getObjValue(formCarCheckFormBase, mfCarCheckForm);
                String htmlStr = jsonFormUtil.getJsonStr(formCarCheckFormBase, "propertySeeTag", "");
                dataMap.put("htmlStr", htmlStr);
                }else {
                }
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "保存失败");
            throw new Exception(e.getMessage());
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
    public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 这里得到的formId是带form字符串的，比如formcuscorp0001
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        dataMap = getMapByJson(ajaxData);
        String id = (String) dataMap.get("id");
        mfCarCheckForm.setId(id);
        mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
        String pledgeNo = mfCarCheckForm.getPledgeNo();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(pledgeNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        if (StringUtil.isEmpty(formId)) {
            formId = mfCollateralFormConfigFeign
                    .getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "MfBusGpsRegAction", "").getShowModelDef();
        } else {
            if (formId.indexOf("form") == -1) {
            } else {
                formId = formId.substring(4);
            }
        }
        FormData formmfCarCheckForm = formService.getFormData(formId);
        getFormValue(formmfCarCheckForm, getMapByJson(ajaxData));
        MfCarCheckForm mfCarCheckFormJsp = new MfCarCheckForm();
        setObjValue(formmfCarCheckForm, mfCarCheckFormJsp);
        if (mfCarCheckForm != null) {
            try {
                mfCarCheckForm = (MfCarCheckForm) EntityUtil.reflectionSetVal(mfCarCheckForm, mfCarCheckFormJsp,
                        getMapByJson(ajaxData));
                mfCarCheckFormFeign.update(mfCarCheckForm);
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
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData, String formId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 这里得到的formId是带form字符串的，比如formcuscorp0001
        MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
        dataMap = getMapByJson(ajaxData);
        String id = (String) dataMap.get("id");
        mfCarCheckForm.setId(id);
        mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
        String pledgeNo = mfCarCheckForm.getPledgeNo();
        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
        pledgeBaseInfo.setPledgeNo(pledgeNo);
        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
        if (StringUtil.isEmpty(formId)) {
            formId = mfCollateralFormConfigFeign
                    .getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "MfBusGpsRegAction", "").getShowModelDef();
        } else {
            if (formId.indexOf("form") == -1) {
            } else {
                formId = formId.substring(4);
            }
        }
        FormData formmfCarCheckForm = formService.getFormData(formId);
        getFormValue(formmfCarCheckForm, getMapByJson(ajaxData));
        MfCarCheckForm mfCarCheckFormJsp = new MfCarCheckForm();
        setObjValue(formmfCarCheckForm, mfCarCheckFormJsp);
        if (mfCarCheckForm != null) {
            try {
                mfCarCheckForm = (MfCarCheckForm) EntityUtil.reflectionSetVal(mfCarCheckForm, mfCarCheckFormJsp,
                        getMapByJson(ajaxData));
                mfCarCheckFormFeign.update(mfCarCheckForm);
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

    /*
     * 提交客户验车流程
     * */
    @RequestMapping("/submitCusAjax")
    @ResponseBody
    public Map<String, Object> submitCusAjax(Model model, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            String formId = map.get("formId").toString();
            FormData formCarCheckFormDetail = formService.getFormData(formId);
            getFormValue(formCarCheckFormDetail, getMapByJson(ajaxData));
            if (this.validateFormData(formCarCheckFormDetail)) {
                MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
                setObjValue(formCarCheckFormDetail, mfCarCheckForm);
                String appId = mfCarCheckForm.getAppId();
                mfCarCheckForm.setId(WaterIdUtil.getWaterId());
                mfCarCheckFormFeign.insert(mfCarCheckForm);
                Result result = pledgeBaseInfoFeign.doCommit(appId, User.getRegNo(request));
                dataMap.put("flag", "success");
                dataMap.put("msg", result.getMsg());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "保存失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }


}
