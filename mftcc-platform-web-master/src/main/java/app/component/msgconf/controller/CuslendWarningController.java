package app.component.msgconf.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.entity.MfTemplateTagBase;
import app.component.model.entity.MfTemplateTagSet;
import app.component.model.feign.MfNewTemplateTagBaseFeign;
import app.component.model.feign.MfNewTemplateTagSetFeign;
import app.component.model.feign.MfTemplateTagSetFeign;
import app.component.nmd.entity.ParmDic;
import app.component.sys.entity.SysRole;
import app.component.sys.feign.SysRoleFeign;
import cn.mftcc.util.PropertiesUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.msgconf.entity.CuslendWarning;
import app.component.msgconf.entity.MfMsgVar;
import app.component.msgconf.feign.CuslendWarningFeign;
import app.component.msgconf.feign.MfMsgVarFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: CuslendWarningAction.java
 * Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Thu Jul 13 10:44:08 CST 2017
 **/

/**
 * @author LiuYF
 *
 */
@Controller
@RequestMapping("/cuslendWarning")
public class CuslendWarningController extends BaseFormBean {
    // 注入业务层
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private CuslendWarningFeign cuslendWarningFeign;
    @Autowired
    private MfMsgVarFeign mfMsgVarFeign;
    @Autowired
    private SysRoleFeign sysRoleFeign;
    @Autowired
    private MfTemplateTagSetFeign mfTemplateTagSetFeign;
    @Autowired
    private MfNewTemplateTagSetFeign mfNewTemplateTagSetFeign;
    @Autowired
    private MfNewTemplateTagBaseFeign mfNewTemplateTagBaseFeign;
    @Value("${mftcc.pageoffice.office_version:0}")
    private String pageOfficeSts;
    // 全局变量
    // 表单变量

    /**
     * 获取列表数据
     *
     * @param cuslendWarning
     * @return
     * @throws Exception
     */
    private void getTableData(Map<String, Object> dataMap, String tableId, CuslendWarning cuslendWarning)
            throws Exception {
        JsonTableUtil jtu = new JsonTableUtil();

        String tableHtml = jtu.getJsonStr(tableId, "tableTag", cuslendWarningFeign.getAll(cuslendWarning), null, true);
        dataMap.put("tableData", tableHtml);
    }

    private void getTableData(List<CuslendWarning> list, Map<String, Object> dataMap, String tableId) throws Exception {
        JsonTableUtil jtu = new JsonTableUtil();
        Ipage ipage = this.getIpage();
        String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, ipage, true);
        dataMap.put("tableData", tableHtml);
    }

    /**
     * 列表有翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcuslend0002 = formService.getFormData("cuslend0002");
        CuslendWarning cuslendWarning = new CuslendWarning();
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("cuslendWarning", cuslendWarning));
        ipage = cuslendWarningFeign.findByPage(ipage);
        model.addAttribute("formcuslend0002", formcuslend0002);
        model.addAttribute("ipage", ipage);
        model.addAttribute("query", "");
        return "/component/msgconf/CuslendWarning_List";
    }

    /**
     * 列表有翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMsgModleListPage")
    public String getMsgModleListPage(Model model, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcuslend0002 = formService.getFormData("cuslend0002");
        CuslendWarning cuslendWarning = new CuslendWarning();
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("cuslendWarning", cuslendWarning));
        ipage = cuslendWarningFeign.findByPage(ipage);
        model.addAttribute("formcuslend0002", formcuslend0002);
        model.addAttribute("ipage", ipage);
        model.addAttribute("query", "");
        return "/component/msgconf/MfMsgModle_List";
    }
    /**
     *
     * 方法描述： 模板标签配置功能。从LoanTemplateTagSetAction迁移过来的
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2018-1-12 上午9:04:38
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/baseTagSet")
    public String baseTagSet(Model model, String templateNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<MfTemplateTagBase> mfTemplateTagBaseList = new ArrayList<MfTemplateTagBase>();
        String groupFlag;
        String ajaxData;
        try {
            if(pageOfficeSts.equals("0")){
                dataMap = mfTemplateTagSetFeign.getTagsBaseList(templateNo);
            }else if(pageOfficeSts.equals("1")){
                dataMap = mfNewTemplateTagSetFeign.getTagsBaseList(templateNo);
            }else {
            }
            groupFlag = (String) dataMap.get("groupFlag");
            mfTemplateTagBaseList = (List<MfTemplateTagBase>) dataMap.get("mfTemplateTagBaseList");
            ajaxData = new Gson().toJson(mfTemplateTagBaseList);
        } catch (Exception e) {
            throw e;
        }
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("groupFlag", groupFlag);
        model.addAttribute("templateNo", templateNo);
        model.addAttribute("query", "");
        return "/component/msgconf/MsgTemplateTagSet_baseTagSet";
    }

    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateTagSetAjax")
    @ResponseBody
    public Map<String, Object> updateTagSetAjax(String templateNo,String tagkeyname,String tagkeyno) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            tagkeyname = URLDecoder.decode(URLDecoder.decode(tagkeyname, "UTF-8"), "UTF-8");
            tagkeyname = tagkeyname.replaceAll(",", "@");
            tagkeyno = tagkeyno.replaceAll(",", "@");
            String templatePath = PropertiesUtil.getPageOfficeConfigProperty("office_sys_template");
            if (StringUtil.isEmpty(templatePath)) {
                String realFilePath = request.getSession().getServletContext().getRealPath("/");
                templatePath = realFilePath + "component" + File.separator + "model" + File.separator + "docmodel"
                        + File.separator;
            }
            MfTemplateTagSet mfTemplateTagSet = new MfTemplateTagSet();
            if(pageOfficeSts.equals("0")){
//                loanTemplateTagSet.setTemplateNo(templateNo);
//                loanTemplateTagSet.setTemplateEnName(templateNameEn);
//                loanTemplateTagSet.setTemplateCnName(templateNameZh);
//                loanTemplateTagSet.setTagKeyName(tagkeyname);
//                loanTemplateTagSet.setTagKeyNo(tagkeyno);
//                loanTemplateTagSet.setTemplatePath(templatePath);
//                loanTemplateTagSetFeign.updateTagSet(loanTemplateTagSet);
            }else if(pageOfficeSts.equals("1")){
                mfTemplateTagSet.setTemplateNo(templateNo);
                mfTemplateTagSet.setTagKeyName(tagkeyname);
                mfTemplateTagSet.setTagKeyNo(tagkeyno);
                mfTemplateTagSet.setTemplatePath(templatePath);
                mfNewTemplateTagSetFeign.updateTagSet(mfTemplateTagSet);
            }else {
            }
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 列表有翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDetailPage")
    public String getDetailPage(Model model, String cuslendWarnNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 发送对象
        JSONArray reciverTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_RECIVER_TYPE");
        String reciverTypeItems = reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("reciverTypeItems", reciverTypeItems);

        // 发送方式
        JSONArray sendTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
        String sendTypeItems = sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("sendTypeItems", sendTypeItems);

        // 变量来源类型
        JSONArray varUsageMap = new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
        String varUserageItems = varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("varUserageItems", varUserageItems);

        // 产品
        JSONArray varKindMap = new CodeUtils().getJSONArrayByKeyName("KIND_NO");
        String varKindItems = varKindMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("varKindItems", varKindItems);

        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCuslendWarnNo(cuslendWarnNo);
        cuslendWarning = cuslendWarningFeign.getById(cuslendWarning);
        String formId="";
        if ("4".equals(cuslendWarning.getMsgType())){
            //角色名称
            JSONArray roleList=JSONArray.fromObject(sysRoleFeign.getAllList(new SysRole()));
            String roles = roleList.toString().replaceAll("roleName", "name").replace("roleNo", "id");
            model.addAttribute("roles", roles);
            formId="recallBaseWarning";
        }else {
            model.addAttribute("roles", "[]");
            formId="cuslend0002";
        }
        FormData formcuslend0002 = formService.getFormData(formId);
        getObjValue(formcuslend0002, cuslendWarning);
        model.addAttribute("formcuslend0002", formcuslend0002);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        return "/component/msgconf/CuslendWarning_Detail";
    }

    /**
     * 列表有翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMsgModleDetailPage")
    public String getMsgModleDetailPage(Model model, String cuslendWarnNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> reslutMap = new HashMap<String, String>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 发送对象
        JSONArray reciverTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_RECIVER_TYPE");
        String reciverTypeItems = reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("reciverTypeItems", reciverTypeItems);

        // 发送方式
        JSONArray sendTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
        String sendTypeItems = sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("sendTypeItems", sendTypeItems);

        // 变量来源类型
        JSONArray varUsageMap = new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
        String varUserageItems = varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("varUserageItems", varUserageItems);

        FormData formmsgconfig0002 = formService.getFormData("msgconfig0002");
        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCuslendWarnNo(cuslendWarnNo);
        cuslendWarning = cuslendWarningFeign.getById(cuslendWarning);
        this.changeFormProperty(formmsgconfig0002,"cuslendWarnContent","fieldType",5);
        getObjValue(formmsgconfig0002, cuslendWarning);

        model.addAttribute("formmsgconfig0002", formmsgconfig0002);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        return "/component/msgconf/MfMsgModle_Detail";
    }

    /**
     * 列表全部无翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListAll")
    public String getListAll(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcuslend0002 = formService.getFormData("cuslend0002");
        CuslendWarning cuslendWarning = new CuslendWarning();
        List<CuslendWarning> cuslendWarningList = cuslendWarningFeign.getAll(cuslendWarning);
        model.addAttribute("formcuslend0002", formcuslend0002);
        model.addAttribute("cuslendWarningList", cuslendWarningList);
        model.addAttribute("query", "");
        return "/component/msgconf/CuslendWarning_List";
    }

    /**
     * 列表全部无翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSMSInputPage")
    public String getSMSInputPage(Model model, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formmsgconfig0002 = formService.getFormData("msgconfig0002");
        CuslendWarning cuslendWarning = new CuslendWarning();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 发送对象
        JSONArray reciverTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_RECIVER_TYPE");
        String reciverTypeItems = reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("reciverTypeItems", reciverTypeItems);

        // 发送方式
        JSONArray sendTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
        String sendTypeItems = sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("sendTypeItems", sendTypeItems);

        // 变量来源类型
        JSONArray varUsageMap = new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
        String varUserageItems = varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("varUserageItems", varUserageItems);

        model.addAttribute("formmsgconfig0002", formmsgconfig0002);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        return "/component/msgconf/MfMsgModle_Insert";
    }

    @RequestMapping(value = "/getModelInputPage")
    public String getModelInputPage(Model model, String cuslendWarnNo, String varUsage) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcuslendmodel0002 = formService.getFormData("cuslendmodel0002");
        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCuslendWarnNo(cuslendWarnNo);
        cuslendWarning = cuslendWarningFeign.getById(cuslendWarning);
        getObjValue(formcuslendmodel0002, cuslendWarning);

        Map<String, String> vumap = new CodeUtils().getMapByKeyName("BOOKMARK_CLASS");
        MfMsgVar mfMsgVar = new MfMsgVar();
        List<MfMsgVar> mmvlist = null;
        Map<String, List<MfMsgVar>> vuListMap = new HashMap<String, List<MfMsgVar>>();
        //        String[] varUsageArr = varUsage.split(",");
//        for (String vuStr : varUsageArr) {
//            if ("".equals(vuStr)) {
//                continue;
//            }
//            mfMsgVar.setVarUsage(vuStr);
//            mfMsgVar.setVarType("01");
//            mmvlist = mfMsgVarFeign.getListByVarUsage(mfMsgVar);
//            vuListMap.put(vuStr + "_" + vumap.get(vuStr), mmvlist);
//        }

        //获取已经设置的标签信息
        MfTemplateTagSet mfTemplateTagSet=new MfTemplateTagSet();
        mfTemplateTagSet.setTemplateNo(cuslendWarnNo);
        mfTemplateTagSet=mfNewTemplateTagSetFeign.getById(mfTemplateTagSet);
        if(mfTemplateTagSet!=null){
            //根据设置的标签好查询标签信息
            String keyNo=mfTemplateTagSet.getTagKeyNo();
            String[] keyNos=keyNo.split("@");
            for(String key:keyNos){
                MfTemplateTagBase mfTemplateTagBase=new MfTemplateTagBase();
                mfTemplateTagBase.setKeyNo(key);
                mfTemplateTagBase=mfNewTemplateTagBaseFeign.getById(mfTemplateTagBase);
                String vuStr=mfTemplateTagBase.getGroupFlag();
                String mapKey=vuStr + "_" + vumap.get(vuStr);
                List list=new ArrayList();
                MfTemplateTagBase mfTemplateTagBase2=new MfTemplateTagBase();
                mfTemplateTagBase2.setKeyNo(mfTemplateTagBase.getKeyNo());
                mfTemplateTagBase2.setTagKeyName(mfTemplateTagBase.getTagKeyName());
                if(vuListMap.get(mapKey)!=null){
                    list=vuListMap.get(mapKey);
                    list.add(mfTemplateTagBase2);
                }else{
                    list.add(mfTemplateTagBase2);
                }
                vuListMap.put(mapKey, list);
            }
        }else{
            model.addAttribute("msg", "请先设置标签信息");
        }
        JSONObject json = new JSONObject();
        json.put("vuListMap", JSONObject.fromObject(vuListMap));

        String ajaxData = json.toString();

        model.addAttribute("formcuslendmodel0002", formcuslendmodel0002);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("query", "");
        return "/component/msgconf/Cuslend_Model_Input";
    }

    /**
     * 列表有翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCustomQuery(ajaxData);
        cuslendWarning.setCriteriaList(cuslendWarning, ajaxData);
        cuslendWarning.setMsgType("1");
        Ipage ipage = this.getIpage();
        ipage.setPageSize(pageSize);
        ipage.setPageNo(pageNo);
        ipage.setParams(this.setIpageParams("cuslendWarning", cuslendWarning));
        ipage = cuslendWarningFeign.findByPage(ipage);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
        ipage.setResult(tableHtml);
        dataMap.put("ipage", ipage);
        return dataMap;
    }

    /**
     * 消息模板列表有翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findMsgByPageAjax")
    @ResponseBody
    public Map<String, Object> findMsgByPageAjax(String ajaxData, int pageSize, int pageNo, String tableId, String tableType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCustomQuery(ajaxData);
        cuslendWarning.setMsgType("2");
        cuslendWarning.setCriteriaList(cuslendWarning, ajaxData);
        Ipage ipage = this.getIpage();
        ipage.setPageSize(pageSize);
        ipage.setPageNo(pageNo);
        ipage.setParams(this.setIpageParams("cuslendWarning", cuslendWarning));
        ipage = cuslendWarningFeign.findByPage(ipage);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
        ipage.setResult(tableHtml);
        dataMap.put("ipage", ipage);
        return dataMap;
    }

    /**
     * 列表全部无翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getInputPage")
    public String getInputPage(Model model, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcuslend0002 = formService.getFormData("cuslend0002");
        CuslendWarning cuslendWarning = new CuslendWarning();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 发送对象
        JSONArray reciverTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_RECIVER_TYPE");
        String reciverTypeItems = reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("reciverTypeItems", reciverTypeItems);

        // 发送方式
        JSONArray sendTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
        String sendTypeItems = sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("sendTypeItems", sendTypeItems);

        // 变量来源类型
        JSONArray varUsageMap = new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
        String varUserageItems = varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("varUserageItems", varUserageItems);

        // 产品
        JSONArray varKindMap = new CodeUtils().getJSONArrayByKeyName("KIND_NO");
        String varKindItems = varKindMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("varKindItems", varKindItems);

        model.addAttribute("formcuslend0002", formcuslend0002);
        this.changeFormProperty(formcuslend0002,"cuslendWarnContent","mustInput",1);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        return "/component/msgconf/CuslendWarning_Insert";
    }

    /**
     * AJAX新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            dataMap = getMapByJson(ajaxData);
            String formId = (String) dataMap.get("formId");
            if (StringUtil.isEmpty(formId)) {
                formId = "cuslend0002";
            }
            FormData formcuslend0002 = formService.getFormData(formId);
            getFormValue(formcuslend0002, getMapByJson(ajaxData));
            if (this.validateFormData(formcuslend0002)) {
                CuslendWarning cuslendWarning = new CuslendWarning();
                setObjValue(formcuslend0002, cuslendWarning);
                String args = getArgsStr(cuslendWarning.getVarUsageRel(), cuslendWarning.getCuslendWarnContent());
                cuslendWarning.setCuslendContentArgs(args);
                cuslendWarning.setCuslendWarnNo(WaterIdUtil.getWaterId());
                cuslendWarning.setFlag("0");
                cuslendWarning.setMsgType("1");
                cuslendWarningFeign.insert(cuslendWarning);
                getTableData(dataMap, tableId, cuslendWarning);// 获取列表
                dataMap.put("flag", "success");
                dataMap.put("msg", "新增成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }
    @RequestMapping(value = "/getModelInputPageByTag")
    public String getModelInputPageByTag(Model model, String cuslendWarnNo, String varUsage) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcuslendmodel0002 = formService.getFormData("cuslendmodel0002");
        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCuslendWarnNo(cuslendWarnNo);
        cuslendWarning=cuslendWarningFeign.getById(cuslendWarning);
        getObjValue(formcuslendmodel0002, cuslendWarning);

        Map<String, String> vumap = new CodeUtils().getMapByKeyName("BOOKMARK_CLASS");
        MfMsgVar mfMsgVar = new MfMsgVar();
        List<MfMsgVar> mmvlist = null;
        Map<String, List<MfTemplateTagBase>> vuListMap = new HashMap<String, List<MfTemplateTagBase>>();
//        String[] varUsageArr = varUsage.split(",");
//        for (String vuStr : varUsageArr) {
//            if ("".equals(vuStr)) {
//                continue;
//            }
//            mfMsgVar.setVarUsage(vuStr);
//            mfMsgVar.setVarType("01");
//            mmvlist = mfMsgVarFeign.getListByVarUsage(mfMsgVar);
//            vuListMap.put(vuStr + "_" + vumap.get(vuStr), mmvlist);
//        }

        //获取已经设置的标签信息
        MfTemplateTagSet mfTemplateTagSet=new MfTemplateTagSet();
        mfTemplateTagSet.setTemplateNo(cuslendWarnNo);
        mfTemplateTagSet=mfNewTemplateTagSetFeign.getById(mfTemplateTagSet);
        if(mfTemplateTagSet!=null){
            //根据设置的标签好查询标签信息
            String keyNo=mfTemplateTagSet.getTagKeyNo();
            String[] keyNos=keyNo.split("@");
            for(String key:keyNos){
                MfTemplateTagBase mfTemplateTagBase=new MfTemplateTagBase();
                mfTemplateTagBase.setKeyNo(key);
                mfTemplateTagBase=mfNewTemplateTagBaseFeign.getById(mfTemplateTagBase);
                String vuStr=mfTemplateTagBase.getGroupFlag();
                String mapKey=vuStr + "_" + vumap.get(vuStr);
                List list=new ArrayList();
                MfTemplateTagBase mfTemplateTagBase2=new MfTemplateTagBase();
                mfTemplateTagBase2.setKeyNo(mfTemplateTagBase.getKeyNo());
                mfTemplateTagBase2.setTagKeyName(mfTemplateTagBase.getTagKeyName());
                if(vuListMap.get(mapKey)!=null){
                    list=vuListMap.get(mapKey);
                    list.add(mfTemplateTagBase2);
                }else{
                    list.add(mfTemplateTagBase2);
                }
                vuListMap.put(mapKey, list);
            }
        }else{
            model.addAttribute("msg", "请先设置标签信息");
        }
        JSONObject json = new JSONObject();
        json.put("vuListMap", JSONObject.fromObject(vuListMap));

        String ajaxData = json.toString();

        model.addAttribute("formcuslendmodel0002", formcuslendmodel0002);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("query", "");
        return "/component/msgconf/Cuslend_Model_Input";
    }


    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            String formId="";
            Map<String,Object> ajaxMap= getMapByJson(ajaxData);
            if(ajaxMap.containsKey("msgType")){
                if ("4".equals(ajaxMap.get("msgType").toString())){
                    formId="recallBaseWarning";
                }else {
                    formId="cuslend0002";
                }
            }else{
                formId="cuslend0002";
            }

            FormData formcuslend0002 = formService.getFormData(formId);
            getFormValue(formcuslend0002, getMapByJson(ajaxData));
            if (this.validateFormDataAnchor(formcuslend0002)) {
                CuslendWarning cuslendWarning = new CuslendWarning();
                setObjValue(formcuslend0002, cuslendWarning);
                if(!"".equals(cuslendWarning.getVarUsageRel())&&cuslendWarning.getVarUsageRel()!=null){
                    String args = getArgsStr(cuslendWarning.getVarUsageRel(), cuslendWarning.getCuslendWarnContent());
                    //不修改预警类型
                    cuslendWarning.setMsgType(null);
                    cuslendWarning.setCuslendContentArgs(args);
                }
                cuslendWarningFeign.update(cuslendWarning);
                getTableData(dataMap, tableId, cuslendWarning);// 获取列表
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "更新失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }

    @RequestMapping(value = "/updateSMSAjax")
    @ResponseBody
    public Map<String, Object> updateSMSAjax(String ajaxData, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formmsgconfig0002 = formService.getFormData("msgconfig0002");
            getFormValue(formmsgconfig0002, getMapByJson(ajaxData));
            if (this.validateFormData(formmsgconfig0002)) {
                CuslendWarning cuslendWarning = new CuslendWarning();
                setObjValue(formmsgconfig0002, cuslendWarning);
                String args = getArgsStr(cuslendWarning.getVarUsageRel(), cuslendWarning.getCuslendWarnContent());
                cuslendWarning.setCuslendContentArgs(args);
                cuslendWarningFeign.update(cuslendWarning);
                getTableData(dataMap, tableId, cuslendWarning);// 获取列表
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "更新失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }

    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateFlagAjax")
    @ResponseBody
    public Map<String, Object> updateFlagAjax(String ajaxData, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONObject jobj = JSONObject.fromObject(ajaxData);
            FormData formcuslend0002 = formService.getFormData("cuslend0002");
            getFormValue(formcuslend0002, jobj);
            CuslendWarning cuslendWarning = new CuslendWarning();
            setObjValue(formcuslend0002, cuslendWarning);
            int count = cuslendWarningFeign.updateFlag(cuslendWarning);
            if (count > 0) {
                cuslendWarning = cuslendWarningFeign.getById(cuslendWarning);
                ArrayList<CuslendWarning> cuslendWarningList = new ArrayList<CuslendWarning>();
                cuslendWarningList.add(cuslendWarning);
                getTableData(cuslendWarningList, dataMap, tableId);
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
    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateDueWarningFlagAjax")
    @ResponseBody
    public Map<String, Object> updateDueWarningFlagAjax(String ajaxData, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONObject jobj = JSONObject.fromObject(ajaxData);
            FormData formcuslend0002 = formService.getFormData("cuslend0002");
            getFormValue(formcuslend0002, jobj);
            CuslendWarning cuslendWarning = new CuslendWarning();
            setObjValue(formcuslend0002, cuslendWarning);
            int count = cuslendWarningFeign.updateFlag(cuslendWarning);
            if (count > 0) {
                cuslendWarning = cuslendWarningFeign.getDueWarningDetailPage(cuslendWarning);
                ArrayList<CuslendWarning> cuslendWarningList = new ArrayList<CuslendWarning>();
                cuslendWarningList.add(cuslendWarning);
                getTableData(cuslendWarningList, dataMap, tableId);
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
    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String cuslendWarnNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formcuslend0002 = formService.getFormData("cuslend0002");
        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCuslendWarnNo(cuslendWarnNo);
        cuslendWarning = cuslendWarningFeign.getById(cuslendWarning);
        getObjValue(formcuslend0002, cuslendWarning, formData);
        if (cuslendWarning != null) {
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
    @RequestMapping(value = "/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String cuslendWarnNo, String tableId, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCuslendWarnNo(cuslendWarnNo);
        try {
            JSONObject jb = JSONObject.fromObject(ajaxData);
            cuslendWarning = (CuslendWarning) JSONObject.toBean(jb, CuslendWarning.class);
            cuslendWarningFeign.delete(cuslendWarning);
            getTableData(dataMap, tableId, cuslendWarning);// 获取列表
            dataMap.put("flag", "success");
            dataMap.put("msg", "成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }


    private String getArgsStr(String varUsageTem, String modelContentTem) throws Exception {
        String argsStr = "";
        String varNameTem = "";
        String varUsageTemStr = "";
        Set<String> argsStrSet = new HashSet<String>();
        Map<String, String> argsStrMap = new HashMap<String, String>();
        // 对变量来源类型进行处理
        String[] strArr = varUsageTem.split("\\|");
        for (String str : strArr) {
            if (!"".equals(str)) {
                varUsageTemStr = varUsageTemStr + ",'" + str + "'";
            }
        }
        if (varUsageTemStr.startsWith(",")) {
            varUsageTemStr = varUsageTemStr.substring(1);
        }
        if (StringUtil.isNotEmpty(varUsageTemStr)) {
            varUsageTemStr = "(" + varUsageTemStr + ")";
        }
        MfMsgVar mfMsgVar = new MfMsgVar();
        mfMsgVar.setVarUsage(varUsageTemStr);
        if (StringUtil.isNotEmpty(varUsageTemStr)) {
            List<MfMsgVar> mmvlist = mfMsgVarFeign.getListByVarUsages(mfMsgVar);
            for (MfMsgVar mmv : mmvlist) {
                if ("02".equals(mmv.getVarType())) {
                    // argsStr = argsStr + "|" + mmv.getVarId();
                    if (null != argsStrMap.get(mmv.getVarTb())) {
                        argsStrMap.put(mmv.getVarTb(), argsStrMap.get(mmv.getVarTb()) + "|" + mmv.getVarId());
                    } else {
                        argsStrMap.put(mmv.getVarTb(), "|" + mmv.getVarId());
                    }
                    continue;
                }
                varNameTem = mmv.getVarName();
                varNameTem = "{" + varNameTem + "}";
                if (modelContentTem.contains(varNameTem)) {
                    argsStr = argsStr + "|" + mmv.getVarId();
                    argsStrSet.add(mmv.getVarTb());
                }
            }
            // set遍历
            for (String str : argsStrSet) {
                if (null != argsStrMap.get(str)) {
                    argsStr = argsStr + argsStrMap.get(str);
                }
            }

            if (argsStr.startsWith("|")) {
                argsStr = argsStr.substring(1);
            }
        }
        return argsStr;
    }

    /**
     * 方法描述： 校验是否已经存在该预警类型
     * @return
     * @throws Exception
     * String
     * @author YuShuai
     * @date 2018-6-27 下午5:49:14
     * 20180712 沈浩兵
     */
    @RequestMapping(value = "/checkWarnType")
    @ResponseBody
    public Map<String, Object> checkWarnType(String ajaxData, String jsonStr) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map map = getMapByJson(ajaxData);
        boolean flag = false;
        try {
            CuslendWarning cuslendWarning = new CuslendWarning();
            cuslendWarning.setCuslendWarnType((String) map.get("cuslendWarnType"));
            cuslendWarning.setMsgType("1");
            List<CuslendWarning> list = cuslendWarningFeign.geMsgPlateList(cuslendWarning);
            if ("insert".equals(jsonStr)) {
                if (list == null || list.size() == 0) {
                    flag = true;
                }
            } else {
                if (list != null && list.size() == 1) {
                    flag = true;
                }
            }
            if (!flag) {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage("该预警类型已存在"));
            } else {
                dataMap.put("flag", "success");
                dataMap.put("msg", "成功");
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }

    /**
     * 列表有翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDueWarningListPage")
    public String getDueWarningListPage(Model model, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        model.addAttribute("query", "");
        return "/component/msgconf/MfDueWaring_List";
    }

    /**
     * 消息模板列表有翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findDueWarningByPageAjax")
    @ResponseBody
    public Map<String, Object> findDueWarningByPageAjax(String ajaxData, int pageSize, int pageNo, String tableId, String tableType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCustomQuery(ajaxData);
        cuslendWarning.setMsgType("3");
        cuslendWarning.setCriteriaList(cuslendWarning, ajaxData);
        Ipage ipage = this.getIpage();
        ipage.setPageSize(pageSize);
        ipage.setPageNo(pageNo);
        ipage.setParams(this.setIpageParams("cuslendWarning", cuslendWarning));
        ipage = cuslendWarningFeign.getDueWarningPage(ipage);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
        ipage.setResult(tableHtml);
        dataMap.put("ipage", ipage);
        dataMap.put("tableHtml", tableHtml);
        return dataMap;
    }


    /**
     * AJAX新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertCuslendWarnAjax")
    @ResponseBody
    public Map<String, Object> insertCuslendWarnAjax(String ajaxData, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            dataMap = getMapByJson(ajaxData);
            String formId = (String) dataMap.get("formId");
            if (StringUtil.isEmpty(formId)) {
                formId = "duewarningbase";
            }
            FormData formcuslend0002 = formService.getFormData(formId);
            getFormValue(formcuslend0002, getMapByJson(ajaxData));
            if (this.validateFormData(formcuslend0002)) {
                CuslendWarning cuslendWarning = new CuslendWarning();
                setObjValue(formcuslend0002, cuslendWarning);
                cuslendWarning.setCuslendWarnNo(WaterIdUtil.getWaterId());
                cuslendWarning.setFlag("1");
                cuslendWarning.setMsgType("3");
                cuslendWarningFeign.insert(cuslendWarning);
                getTableData(dataMap, tableId, cuslendWarning);// 获取列表
                dataMap.put("flag", "success");
                dataMap.put("msg", "新增成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }


    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateDueWarningAjax")
    @ResponseBody
    public Map<String, Object> updateDueWarningAjax(String ajaxData, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            dataMap = getMapByJson(ajaxData);
            String formId = (String) dataMap.get("formId");
            FormData formcuslend0002 = formService.getFormData(formId);
            getFormValue(formcuslend0002, getMapByJson(ajaxData));
            if (this.validateFormData(formcuslend0002)) {
                CuslendWarning cuslendWarning = new CuslendWarning();
                setObjValue(formcuslend0002, cuslendWarning);
                cuslendWarningFeign.update(cuslendWarning);
                getTableData(dataMap, tableId, cuslendWarning);// 获取列表
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "更新失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }
    /*20190426 yxl 新增 催收预警 设置相关*/
    //获取催收预警列表
    @RequestMapping(value = "/getRecallWarningListPage")
    public String getreCallWarningListPage(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcuslend0002 = formService.getFormData("recallBaseWarning");
        CuslendWarning cuslendWarning = new CuslendWarning();
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("cuslendWarning", cuslendWarning));
        ipage = cuslendWarningFeign.findByPage(ipage);
        model.addAttribute("formcuslend0002", formcuslend0002);
        model.addAttribute("ipage", ipage);
        model.addAttribute("query", "");
        return "/component/msgconf/RecallBaseWarning_List";
    }
    //查询催收预警列表
    @RequestMapping(value = "/findRecallBaseWarningListByPageAjax")
    @ResponseBody
    public Map<String, Object> findRecallBaseWarningListByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                                                   String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CuslendWarning cuslendWarning = new CuslendWarning();
        cuslendWarning.setCustomQuery(ajaxData);
        cuslendWarning.setCriteriaList(cuslendWarning, ajaxData);
        cuslendWarning.setMsgType("4");
        Ipage ipage = this.getIpage();
        ipage.setPageSize(pageSize);
        ipage.setPageNo(pageNo);
        ipage.setParams(this.setIpageParams("cuslendWarning", cuslendWarning));
        ipage = cuslendWarningFeign.findByPage(ipage);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
        ipage.setResult(tableHtml);
        dataMap.put("ipage", ipage);
        return dataMap;
    }
    @RequestMapping(value = "/addRecallBaseWarning")
    public String addRecallBaseWarning(Model model, String ajaxData,String brNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcuslend0002 = formService.getFormData("recallBaseWarning");
        CuslendWarning cuslendWarning = new CuslendWarning();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 发送对象
        JSONArray reciverTypeMap = new CodeUtils().getJSONArrayByKeyName("RECEIVE_PEOPLE_TYPE");
        String reciverTypeItems = reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("reciverTypeItems", reciverTypeItems);

        // 发送方式
        JSONArray sendTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
        String sendTypeItems = sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("sendTypeItems", sendTypeItems);

        // 变量来源类型
        JSONArray varUsageMap = new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
        String varUserageItems = varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("varUserageItems", varUserageItems);

        //角色名称
        JSONArray roleList=JSONArray.fromObject(sysRoleFeign.getAllList(new SysRole()));
        String roles = roleList.toString().replaceAll("roleName", "name").replace("roleNo", "id");

        model.addAttribute("formcuslend0002", formcuslend0002);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        model.addAttribute("roles", roles);
        model.addAttribute("ajaxData", "ajaxData");

        return "/component/msgconf/RecallBaseWarning_Insert";
    }
    //保存催收预警
    @RequestMapping(value = "/insertRecallBaseAjax")
    @ResponseBody
    public Map<String, Object> insertRecallBaseAjax(String ajaxData, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            dataMap = getMapByJson(ajaxData);
            String formId = (String) dataMap.get("formId");
            if (StringUtil.isEmpty(formId)) {
                formId = "recallBaseWarning";
            }
            FormData formcuslend0002 = formService.getFormData(formId);
            getFormValue(formcuslend0002, getMapByJson(ajaxData));
            if (this.validateFormDataAnchor(formcuslend0002)) {
                CuslendWarning cuslendWarning = new CuslendWarning();
                setObjValue(formcuslend0002, cuslendWarning);
                //String args = getArgsStr(cuslendWarning.getVarUsageRel(), cuslendWarning.getCuslendWarnContent());
                //cuslendWarning.setCuslendContentArgs(args);
                cuslendWarning.setCuslendWarnNo(WaterIdUtil.getWaterId());
                cuslendWarning.setFlag("0");
                cuslendWarning.setMsgType("4");//msgType 4为催收预警。
                cuslendWarningFeign.insert(cuslendWarning);
                getTableData(dataMap, tableId, cuslendWarning);// 获取列表
                dataMap.put("flag", "success");
                dataMap.put("msg", "新增成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }
}
