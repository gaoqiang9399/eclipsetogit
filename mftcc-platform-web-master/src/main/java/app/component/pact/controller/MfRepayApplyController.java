package app.component.pact.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfRepayApply;
import app.component.pact.entity.MfRepayFinc;
import app.component.pact.feign.MfRepayApplyFeign;
import app.component.pact.repay.feign.MfRepayFincFeign;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfRepayApplyController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 15 15:14:11 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfRepayApply")
public class MfRepayApplyController extends BaseFormBean{
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfRepayApplyFeign mfRepayApplyFeign;
    @Autowired
    private MfRepayFincFeign mfRepayFincFeign;


    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/pact/repayapp/MfRepayApply_List";
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
        MfRepayApply mfRepayApply = new MfRepayApply();
        try {
            mfRepayApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfRepayApply.setCriteriaList(mfRepayApply, ajaxData);//我的筛选
            mfRepayApply.setCustomSorts(ajaxData);//自定义排序
            //this.getRoleConditions(mfRepayApply,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("mfRepayApply", mfRepayApply));
            //自定义查询Feign方法
            ipage = mfRepayApplyFeign.findByPage(ipage);
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
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData, String ajaxList) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> parmMap = new HashMap<String, Object>();
        JSONArray jsonArray = JSONArray.fromObject(ajaxList);
        MfRepayApply mfRepayApply = new MfRepayApply();
        try {
            FormData formfincBatchRepayment = formService.getFormData("fincRepayment");
            getFormValue(formfincBatchRepayment, getMapByJson(ajaxData));
            if (this.validateFormData(formfincBatchRepayment)) {
                mfRepayApply.setOpNo(User.getRegNo(request));
                mfRepayApply.setOpName(User.getRegName(request));
                mfRepayApply.setBrNo(User.getOrgNo(request));
                mfRepayApply.setBrName(User.getOrgName(request));
                setObjValue(formfincBatchRepayment, mfRepayApply);
                parmMap.put("mfRepayApply",mfRepayApply);
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
                return dataMap;
            }
            List<MfRepayFinc> mfRepayFincList = new ArrayList<MfRepayFinc>();
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject js = jsonArray.getJSONObject(i);
                    MfRepayFinc mfRepayFinc = (MfRepayFinc) JsonStrHandling.handlingStrToBean(js, MfRepayFinc.class);
                    mfRepayFinc.setOpNo(User.getRegNo(request));
                    mfRepayFinc.setOpName(User.getRegName(request));
                    mfRepayFinc.setBrNo(User.getOrgNo(request));
                    mfRepayFinc.setBrName(User.getOrgName(request));
                    mfRepayFincList.add(mfRepayFinc);
                }
                parmMap.put("mfRepayFincList",mfRepayFincList);
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", "无借据信息，还款申请保存失败");
                return dataMap;
            }
            mfRepayApplyFeign.insert(parmMap);
            dataMap.put("flag", "success");
            dataMap.put("msg", "还款申请保存成功");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "保存失败");
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
        FormData formRepayAppAdd = formService.getFormData("RepayAppAdd");
        getFormValue(formRepayAppAdd, getMapByJson(ajaxData));
        MfRepayApply mfRepayApplyJsp = new MfRepayApply();
        setObjValue(formRepayAppAdd, mfRepayApplyJsp);
        MfRepayApply mfRepayApply = mfRepayApplyFeign.getById(mfRepayApplyJsp);
        if(mfRepayApply!=null){
            try{
                mfRepayApply = (MfRepayApply)EntityUtil.reflectionSetVal(mfRepayApply, mfRepayApplyJsp, getMapByJson(ajaxData));
                mfRepayApplyFeign.update(mfRepayApply);
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
        MfRepayApply mfRepayApply = new MfRepayApply();
        try{
            FormData formRepayAppAdd = formService.getFormData("RepayAppAdd");
            getFormValue(formRepayAppAdd, getMapByJson(ajaxData));
            if(this.validateFormData(formRepayAppAdd)){
                mfRepayApply = new MfRepayApply();
                setObjValue(formRepayAppAdd, mfRepayApply);
                mfRepayApplyFeign.update(mfRepayApply);
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
    public Map<String, Object> getByIdAjax(String repayAppId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String,Object> formData = new HashMap<String,Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formRepayAppAdd = formService.getFormData("RepayAppAdd");
        MfRepayApply mfRepayApply = new MfRepayApply();
        mfRepayApply.setRepayAppId(repayAppId);
        mfRepayApply = mfRepayApplyFeign.getById(mfRepayApply);
        getObjValue(formRepayAppAdd, mfRepayApply,formData);
        if(mfRepayApply!=null){
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
    public Map<String, Object> deleteAjax(String repayAppId) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfRepayApply mfRepayApply = new MfRepayApply();
        mfRepayApply.setRepayAppId(repayAppId);
        try {
            mfRepayApplyFeign.delete(mfRepayApply);
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
     * @return
     * @throws Exception
     */
    @RequestMapping("/input")
    public String input(Model model) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formRepayAppAdd = formService.getFormData("RepayAppAdd");
        model.addAttribute("formRepayAppAdd", formRepayAppAdd);
        model.addAttribute("query", "");
        return "/component/pact/repayapp/MfRepayApply_Insert";
    }
    /**
     * 查询
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(Model model,String repayAppId) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formRepayAppDetail = formService.getFormData("RepayAppDetail");
        getFormValue(formRepayAppDetail);
        MfRepayApply mfRepayApply = new MfRepayApply();
        mfRepayApply.setRepayAppId(repayAppId);
        mfRepayApply = mfRepayApplyFeign.getById(mfRepayApply);
        String appSts=mfRepayApply.getAppSts();
        if("3".equals(appSts)){
            String  formId = "fincRepaymentConfirm";
            FormData formfincRepaymentConfirm = formService.getFormData(formId);
            mfRepayApply.setRepayAppId(repayAppId);
            getObjValue(formfincRepaymentConfirm, mfRepayApply);
            model.addAttribute("repayAppId", repayAppId);
            model.addAttribute("formfincRepaymentConfirm", formfincRepaymentConfirm);
            model.addAttribute("query", "");
            return "/component/pact/MfBusFincApp_repaymentConfirm";
        }
        getObjValue(formRepayAppDetail, mfRepayApply);
        model.addAttribute("formRepayAppDetail", formRepayAppDetail);
        MfRepayFinc mfRepayFinc = new MfRepayFinc();
        mfRepayFinc.setRepayAppId(repayAppId);
        List<MfRepayFinc> mfRepayFincList = mfRepayFincFeign.getByRepayAppId(mfRepayFinc);
        JsonTableUtil jtu = new JsonTableUtil();
        String  tableHtml = jtu.getJsonStr("tablemfRepayFincList","thirdTableTag",mfRepayFincList, null,true);
        model.addAttribute("tableHtml", tableHtml);
        model.addAttribute("repayAppId", repayAppId);
        model.addAttribute("appSts", appSts);
        model.addAttribute("query", "");
        return "/component/pact/repayapp/MfRepayApply_Detail";
    }


    /**
     * 还款申请
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="repaymentApply")
    public  String repaymentApply(Model model,String repayAppId,String source) throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        String  formId = "fincRepayment";
        FormData formfincRepayment = formService.getFormData(formId);
        Map<String,Object> parmMap = new HashMap<String,Object>();
        parmMap.put("repayDate",DateUtil.getDate());
        getObjValue(formfincRepayment, parmMap);
        String query = "";
        if(StringUtil.isNotEmpty(repayAppId)){
            formId = "repayApplyBatch";
            formfincRepayment = formService.getFormData(formId);
            MfRepayApply mfRepayApply = new MfRepayApply();
            mfRepayApply.setRepayAppId(repayAppId);
            mfRepayApply = mfRepayApplyFeign.getById(mfRepayApply);
            getObjValue(formfincRepayment, mfRepayApply);
        }
        model.addAttribute("repayAppId", repayAppId);
        model.addAttribute("formfincRepayment", formfincRepayment);
        model.addAttribute("query", query);
        model.addAttribute("source", source);
        return "/component/pact/MfBusFincApp_Repayment";
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
        FormData formRepayAppAdd = formService.getFormData("RepayAppAdd");
        getFormValue(formRepayAppAdd);
        boolean validateFlag = this.validateFormData(formRepayAppAdd);
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
        FormData formRepayAppAdd = formService.getFormData("RepayAppAdd");
        getFormValue(formRepayAppAdd);
        boolean validateFlag = this.validateFormData(formRepayAppAdd);
    }

    /**
     *
     * 方法描述： 还款确认
     * @return
     * @throws Exception
     * String
     * @author cd
     * @date 2019-6-26 09.04
     */
    @RequestMapping("/repaymentConfirm")
    @ResponseBody
    public Map<String, Object> repaymentConfirm(String ajaxData, String repayAppId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> parmMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        MfRepayApply mfRepayApply=new MfRepayApply();
        Map<String, Object> map = getMapByJson(ajaxData);
        String formId = (String) map.get("formId");
        try {
            FormData formfincBatchRepayment = formService.getFormData(formId);
            getFormValue(formfincBatchRepayment, getMapByJson(ajaxData));
            setObjValue(formfincBatchRepayment, mfRepayApply);
            mfRepayApply.setRepayAppId(repayAppId);
            mfRepayApply.setOpNo(User.getRegNo(request));
            mfRepayApply.setOpName(User.getRegName(request));
            mfRepayApply.setBrNo(User.getOrgNo(request));
            mfRepayApply.setBrName(User.getOrgName(request));
            mfRepayApplyFeign.insertConfirmData(mfRepayApply);
            dataMap.put("flag", "success");
            dataMap.put("msg", "提交说明成功");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "提交说明失败");
        }

        return dataMap;

    }

    /**
     * 还款界面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="repaymentApproval")
    public  String repaymentApproval(Model model,String repayAppId,String source) throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        String  formId = "repaymentApply";
        FormData formrepaymentApply = formService.getFormData(formId);
        Map<String,Object> parmMap = new HashMap<String,Object>();
        getObjValue(formrepaymentApply, parmMap);
        String query = "";
        if(StringUtil.isNotEmpty(repayAppId)){
            formId = "repaymentApply";
            formrepaymentApply = formService.getFormData(formId);
            MfRepayApply mfRepayApply = new MfRepayApply();
            mfRepayApply.setRepayAppId(repayAppId);
            mfRepayApply = mfRepayApplyFeign.getById(mfRepayApply);
            getObjValue(formrepaymentApply, mfRepayApply);
        }
        String repayId = WaterIdUtil.getWaterId("repay");
        model.addAttribute("repayAppId", repayAppId);
        model.addAttribute("repayId", repayId);
        model.addAttribute("formrepaymentApply", formrepaymentApply);
        model.addAttribute("query", query);
        model.addAttribute("source", source);
        return "/component/pact/MfBusFincApp_repaymentApproval";
    }

}
