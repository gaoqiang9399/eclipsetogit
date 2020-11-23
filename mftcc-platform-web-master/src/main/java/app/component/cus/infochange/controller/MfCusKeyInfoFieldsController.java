package app.component.cus.infochange.controller;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusTable;
import app.component.cus.feign.MfCusTableFeign;
import app.component.cus.infochange.entity.MfCusInfoChange;
import app.component.cus.infochange.entity.MfCusKeyInfoFields;
import app.component.cus.infochange.feign.MfCusKeyInfoFieldsFeign;
import app.component.nmd.entity.NmdArea;
import app.component.nmd.feign.NmdAreaFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
 * 类名： MfCusKeyInfoFieldsController 描述：客户信息关键字段
 *
 * @author 仇招
 * @date 2018年5月29日 上午11:55:46
 */
@Controller
@RequestMapping("/mfCusKeyInfoFields")
public class MfCusKeyInfoFieldsController extends BaseFormBean {
    @Autowired
    private MfCusKeyInfoFieldsFeign mfCusKeyInfoFieldsFeign;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private NmdAreaFeign nmdAreaFeign;

    @Autowired
    private MfCusTableFeign mfCusTableFeign;

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getListPage")
    public String getListPage() throws Exception {
        return "/component/cus/infochange/MfCusKeyInfoFields_List";
    }

    /***
     * 列表数据查询
     *
     * @param ajaxData
     * @param pageNo
     * @param tableType
     * @param tableId
     * @param pageSize
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, String tableType, String tableId,
                                              Integer pageSize) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusKeyInfoFields mfCusKeyInfoFields = new MfCusKeyInfoFields();
        try {
            mfCusKeyInfoFields.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusKeyInfoFields.setCriteriaList(mfCusKeyInfoFields, ajaxData);// 我的筛选
            mfCusKeyInfoFields.setCustomSorts(ajaxData);// 自定义排序
            // this.getRoleConditions(mfCusKeyInfoFields,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfCusKeyInfoFields", mfCusKeyInfoFields));
            // 自定义查询Bo方法
            ipage = mfCusKeyInfoFieldsFeign.findByPage(ipage);
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
     * @param ajaxData
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
            FormData formmfcuskeyinfofieldsbase = formService.getFormData("mfcuskeyinfofieldsbase");
            getFormValue(formmfcuskeyinfofieldsbase, getMapByJson(ajaxData));
            if (this.validateFormData(formmfcuskeyinfofieldsbase)) {
                MfCusKeyInfoFields mfCusKeyInfoFields = new MfCusKeyInfoFields();
                setObjValue(formmfcuskeyinfofieldsbase, mfCusKeyInfoFields);
                mfCusKeyInfoFieldsFeign.insert(mfCusKeyInfoFields);
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
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formmfcuskeyinfofieldsbase = formService.getFormData("mfcuskeyinfofieldsbase");
        getFormValue(formmfcuskeyinfofieldsbase, getMapByJson(ajaxData));
        MfCusKeyInfoFields mfCusKeyInfoFieldsJsp = new MfCusKeyInfoFields();
        setObjValue(formmfcuskeyinfofieldsbase, mfCusKeyInfoFieldsJsp);
        MfCusKeyInfoFields mfCusKeyInfoFields = mfCusKeyInfoFieldsFeign.getById(mfCusKeyInfoFieldsJsp);
        if (mfCusKeyInfoFields != null) {
            try {
                mfCusKeyInfoFields = (MfCusKeyInfoFields) EntityUtil.reflectionSetVal(mfCusKeyInfoFields,
                        mfCusKeyInfoFieldsJsp, getMapByJson(ajaxData));
                mfCusKeyInfoFieldsFeign.update(mfCusKeyInfoFields);
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
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusKeyInfoFields mfCusKeyInfoFields = new MfCusKeyInfoFields();
        try {
            FormData formmfcuskeyinfofieldsbase = formService.getFormData("mfcuskeyinfofieldsbase");
            getFormValue(formmfcuskeyinfofieldsbase, getMapByJson(ajaxData));
            if (this.validateFormData(formmfcuskeyinfofieldsbase)) {
                mfCusKeyInfoFields = new MfCusKeyInfoFields();
                setObjValue(formmfcuskeyinfofieldsbase, mfCusKeyInfoFields);
                mfCusKeyInfoFieldsFeign.update(mfCusKeyInfoFields);
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
     * @param keyInfoId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String keyInfoId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formmfcuskeyinfofieldsbase = formService.getFormData("mfcuskeyinfofieldsbase");
        MfCusKeyInfoFields mfCusKeyInfoFields = new MfCusKeyInfoFields();
        mfCusKeyInfoFields.setKeyInfoId(keyInfoId);
        mfCusKeyInfoFields = mfCusKeyInfoFieldsFeign.getById(mfCusKeyInfoFields);
        getObjValue(formmfcuskeyinfofieldsbase, mfCusKeyInfoFields, formData);
        if (mfCusKeyInfoFields != null) {
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
     * @param keyInfoId
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String keyInfoId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusKeyInfoFields mfCusKeyInfoFields = new MfCusKeyInfoFields();
        mfCusKeyInfoFields.setKeyInfoId(keyInfoId);
        try {
            mfCusKeyInfoFieldsFeign.delete(mfCusKeyInfoFields);
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
        FormData formmfcuskeyinfofieldsbase = formService.getFormData("mfcuskeyinfofieldsbase");
        model.addAttribute("formmfcuskeyinfofieldsbase", formmfcuskeyinfofieldsbase);
        model.addAttribute("query", "");
        return "/component/cus/infochange/MfCusKeyInfoFields_Insert";
    }

    /**
     * 查询
     *
     * @param keyInfoId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(Model model, String keyInfoId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formmfcuskeyinfofieldsdetail = formService.getFormData("mfcuskeyinfofieldsdetail");
        getFormValue(formmfcuskeyinfofieldsdetail);
        MfCusKeyInfoFields mfCusKeyInfoFields = new MfCusKeyInfoFields();
        mfCusKeyInfoFields.setKeyInfoId(keyInfoId);
        mfCusKeyInfoFields = mfCusKeyInfoFieldsFeign.getById(mfCusKeyInfoFields);
        getObjValue(formmfcuskeyinfofieldsdetail, mfCusKeyInfoFields);
        model.addAttribute("formmfcuskeyinfofieldsdetail", formmfcuskeyinfofieldsdetail);
        model.addAttribute("query", "");
        return "/component/cus/infochange/MfCusKeyInfoFields_Detail";
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
        FormData formmfcuskeyinfofieldsbase = formService.getFormData("mfcuskeyinfofieldsbase");
        getFormValue(formmfcuskeyinfofieldsbase);
        boolean validateFlag = this.validateFormData(formmfcuskeyinfofieldsbase);
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
        FormData formmfcuskeyinfofieldsbase = formService.getFormData("mfcuskeyinfofieldsbase");
        getFormValue(formmfcuskeyinfofieldsbase);
        boolean validateFlag = this.validateFormData(formmfcuskeyinfofieldsbase);
    }

    /**
     * 方法描述： 查询保存项，是否是客户信息关键字，如果是提交审批，否则可以直接修改
     *
     * @param keyInfoId
     * @return
     * @throws Exception Map<String,Object>
     * @author cd
     * @date 2019年8月6日 下午19:25:30
     */
    @RequestMapping("/ifKeyField")
    @ResponseBody
    public Map<String, Object> ifKeyField(String ajaxData, String identification,String updateType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Gson gson = new Gson();
        try {
            String ifKeyField = BizPubParm.YES_NO_N;//默认不走审批
            MfCusKeyInfoFields mfCusKeyInfoFields = new MfCusKeyInfoFields();
            if(identification.isEmpty()){//处理为空问题
                dataMap.put("ifKeyField", ifKeyField);
                return dataMap;
            }
            mfCusKeyInfoFields.setIdentification(identification);
            List<MfCusKeyInfoFields> fieldSlist = mfCusKeyInfoFieldsFeign.getByField(mfCusKeyInfoFields);
            //把表单传过来的值转换成map
            List<Map<String, String>> list = new ArrayList<>();
            list = new Gson().fromJson(ajaxData, new TypeToken<List<Map<String, String>>>() {
                    }.getType()
            );
            Map<String, String> updeteMap = new HashMap<>();
            for (Map<String, String> o : list
            ) {
                updeteMap.put(o.get("name"), o.get("value"));
            }
            if (fieldSlist.size() > 0 && ("1").equals(updateType)) {//处理表单修改和新增方法不规范问题
                MfCusTable mfCusTable=new MfCusTable();
                mfCusKeyInfoFields = new MfCusKeyInfoFields();
                for (int i = 0; i < fieldSlist.size(); i++) {
                    mfCusKeyInfoFields = fieldSlist.get(i);
                    break;
                }
                String cusNoAlias[] = mfCusKeyInfoFields.getCusNoAlias().split("_");//客户号
                String cusNo="";
                for(int i=0;i<cusNoAlias.length;i++){
                    if(0==i){
                        cusNo+=cusNoAlias[i];
                        continue;
                    }
                    cusNo+=cusNoAlias[i].substring(0,1).toUpperCase()+ cusNoAlias[i].substring(1);
                }
                mfCusTable.setCusNo(updeteMap.get(cusNo));
                mfCusTable.setTableName(mfCusKeyInfoFields.getFormName());
                mfCusTable=mfCusTableFeign.getById(mfCusTable);
                if(null !=mfCusTable && "0".equals(mfCusTable.getDataFullFlag())){
                    dataMap.put("ifKeyField", ifKeyField);
                    return dataMap;
                }
            }

            if (fieldSlist.size() > 0) {
                String voidField = "";//配置的字段
                String condition = "";//条件名
                String whereValue = "";//条件值
                String changeFieldName = "";//变更中文的字段
                String changeField = "";//变更英文的字段
                String afterTheChange = "";//变更后的值
                String afterTheChangeShow = "";//变更后的展示值
                String beforeTheChange = "";//变更之前的值
                String beforeTheChangeShow = "";//变更之前的展示值
                String ifApproval = "";//配置字段是否有走审批
                String ifUpdateOnlyForm = "";//是否更新唯一表
                String cusNo = "";
                String fieldType = "";//字段类型
                mfCusKeyInfoFields = new MfCusKeyInfoFields();
                for (int i = 0; i < fieldSlist.size(); i++) {
                    mfCusKeyInfoFields = fieldSlist.get(i);
                    voidField = voidField + mfCusKeyInfoFields.getFieldName() + ",";
                }
                condition = mfCusKeyInfoFields.getRelationNum();
                whereValue = (String) updeteMap.get(condition);
                mfCusKeyInfoFields.setVoidField(voidField + mfCusKeyInfoFields.getCusNoAlias());
                mfCusKeyInfoFields.setWhereValue(whereValue);
                Map<String, Object> primaryValue = mfCusKeyInfoFieldsFeign.getPrimaryValue(mfCusKeyInfoFields);
                cusNo = (String) primaryValue.get(mfCusKeyInfoFields.getCusNoAlias());//客户号
                for (int i = 0; i < fieldSlist.size(); i++) {
                    MfCusKeyInfoFields mckif = new MfCusKeyInfoFields();
                    String field = "";//字段英文名
                    String fieldName = "";///字段名称
                    mckif = fieldSlist.get(i);
                    fieldName = mckif.getFieldName();
                    field = mckif.getField();
                    if (false == updeteMap.containsKey(field)) {
                        continue;
                    }
                    String newValue = (String) updeteMap.get(field);
                    String oldValue = String.valueOf(primaryValue.get(fieldName)) ;
                    Map<String, String> map = new HashMap<>();
                    if ("4".equals(mckif.getFieldType())) {//处理金额
                        newValue = newValue.replaceAll(",", "");
                        if(0==MathExtend.comparison(newValue,oldValue)){
                            continue;
                        }
                    }
                    if ("3".equals(mckif.getFieldType())) {//处理日期
                        newValue = newValue.replaceAll("-", "");
                    }
                    if (newValue.equals(oldValue)){
                        continue;
                    } else {
                        beforeTheChange = beforeTheChange + oldValue + ",";
                        if ("3".equals(mckif.getFieldType()) && !"".equals(oldValue)) {//处理修改之前日期
                                oldValue = getShowValue(oldValue, mckif);
                        }
                        updeteMap.put(field, oldValue);
                        changeFieldName = changeFieldName + fieldName + "~";
                        changeField = changeField + field + ",";
                        afterTheChange = afterTheChange + newValue + ",";
                        ifUpdateOnlyForm = ifUpdateOnlyForm + mckif.getIfUpdateOnlyForm() + ",";
                        ifApproval = ifApproval + mckif.getIfApproval() + ",";
                        if (!BizPubParm.YES_NO_Y.equals(mckif.getFieldType())) {
                            newValue = getShowValue(newValue, mckif);
                            if (!"".equals(oldValue)) {
                                oldValue = getShowValue(oldValue, mckif);
                            }
                        }
                        afterTheChangeShow = afterTheChangeShow + newValue + "~";
                        beforeTheChangeShow = beforeTheChangeShow + oldValue + "~";
                        fieldType = fieldType + mckif.getFieldType() + ",";

                        ifKeyField = BizPubParm.YES_NO_Y;
                    }
                }
                if (BizPubParm.YES_NO_Y.equals(ifKeyField)) {
                    MfCusInfoChange mfCusInfoChange = new MfCusInfoChange();
                    mfCusInfoChange.setAfterTheChange(afterTheChange.substring(0, afterTheChange.length() - 1));
                    mfCusInfoChange.setAfterTheChangeShow(afterTheChangeShow.substring(0, afterTheChangeShow.length() - 1));
                    mfCusInfoChange.setBeforeTheChange(beforeTheChange.substring(0, beforeTheChange.length() - 1));
                    mfCusInfoChange.setBeforeTheChangeShow(beforeTheChangeShow.substring(0, beforeTheChangeShow.length() - 1));
                    mfCusInfoChange.setChangeField(changeField.substring(0, changeField.length() - 1));
                    mfCusInfoChange.setChangeFieldName(changeFieldName.substring(0, changeFieldName.length() - 1));
                    mfCusInfoChange.setFormName(mfCusKeyInfoFields.getFormName());
                    mfCusInfoChange.setIdentification(mfCusKeyInfoFields.getIdentification());
                    mfCusInfoChange.setConditionName(mfCusKeyInfoFields.getRelationNumName());
                    mfCusInfoChange.setConditionNameValue(whereValue);
                    mfCusInfoChange.setIdentification(mfCusKeyInfoFields.getIdentification());
                    mfCusInfoChange.setIfUpdateOnlyForm(ifUpdateOnlyForm.substring(0, ifUpdateOnlyForm.length() - 1));
                    mfCusInfoChange.setFieldUpdateType(fieldType.substring(0, fieldType.length() - 1));
                    mfCusInfoChange.setCusNo(cusNo);
                    dataMap.put("mfCusInfoChange", gson.toJson(mfCusInfoChange));
                    List<Map<String, String>> resMap = new ArrayList<>();
                    for (String key : updeteMap.keySet()) {
                        Map<String, String> m = new HashMap<>();
                        m.put("name", key);
                        m.put("value", updeteMap.get(key));
                        resMap.add(m);
                    }
                    dataMap.put("updeteJson", gson.toJson(resMap));
                    if (ifApproval.indexOf("1") != -1) {//字段包含1,说明有字段需要审批
                        dataMap.put("ifApprovl", "1");
                    } else {
                        dataMap.put("ifApprovl", "0");
                    }

                }

            }
            dataMap.put("ifKeyField", ifKeyField);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }
        return dataMap;
    }


    /**
     * 方法描述： 获取显示值
     *
     * @return String
     * @author 仇招
     * @date 2018年5月30日 下午7:00:47
     */
    private String getShowValue(String value, MfCusKeyInfoFields mfCusKeyInfoFields) throws Exception {
        String fieldType = mfCusKeyInfoFields.getFieldType();
        if (fieldType != null) {
            switch (fieldType) {
                case "2"://字典项
                    CodeUtils cu = new CodeUtils();
                    Map<String, String> cuMap = cu.getMapByKeyName(mfCusKeyInfoFields.getSelectName());
                    value = cuMap.get(value);
                    break;
                case "3"://日期
                    if (!value.contains("-")) {
                        value = DateUtil.getShowDateTime(value);
                    }
                    break;
                case "4"://金额
                    if (!value.contains(",")) {
                        value = MathExtend.moneyStr(Double.valueOf(value));
                    }
                    break;
                case "5"://地址
                    NmdArea nmdArea = new NmdArea();
                    nmdArea.setAreaNo(value);
                    value = nmdAreaFeign.getAllLevById(nmdArea);
                    break;
                default:
                    break;
            }
        }
        return value;
    }
}
