package  app.component.cus.controller;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfKindAttrExt;
import app.component.cus.feign.MfKindAttrExtFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * Title: MfKindAttrExtController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Oct 10 14:33:04 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfKindAttrExt")
public class MfKindAttrExtController extends BaseFormBean{
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfKindAttrExtFeign mfKindAttrExtFeign;


    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/cus/MfKindAttrExt_List";
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
        MfKindAttrExt mfKindAttrExt = new MfKindAttrExt();
        try {
            mfKindAttrExt.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfKindAttrExt.setCriteriaList(mfKindAttrExt, ajaxData);//我的筛选
            //mfKindAttrExt.setCustomSorts(ajaxData);//自定义排序
            //this.getRoleConditions(mfKindAttrExt,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("mfKindAttrExt", mfKindAttrExt));
            //自定义查询Feign方法
            ipage = mfKindAttrExtFeign.findByPage(ipage);
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
            FormData formkindAttrExtDetail = formService.getFormData("kindAttrExtDetail");
            getFormValue(formkindAttrExtDetail, getMapByJson(ajaxData));
            if(this.validateFormData(formkindAttrExtDetail)){
                MfKindAttrExt mfKindAttrExt = new MfKindAttrExt();
                setObjValue(formkindAttrExtDetail, mfKindAttrExt);
                mfKindAttrExtFeign.insert(mfKindAttrExt);
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
    public Map<String, Object> updateAjaxByOne(String kindNo) throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfKindAttrExt mfKindAttrExtJsp = new MfKindAttrExt();
        mfKindAttrExtJsp.setKindNo(kindNo);
        MfKindAttrExt mfKindAttrExt = mfKindAttrExtFeign.getById(mfKindAttrExtJsp);
        if(mfKindAttrExt!=null){
            try{
                String commonKind = mfKindAttrExt.getCommonKind();
                if(BizPubParm.YES_NO_Y.equals(commonKind)){
                    mfKindAttrExt.setCommonKind(BizPubParm.YES_NO_N);
                }else {
                    mfKindAttrExt.setCommonKind(BizPubParm.YES_NO_Y);
                }
                mfKindAttrExtFeign.update(mfKindAttrExt);
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            }catch(Exception e){
                dataMap.put("flag", "error");
                dataMap.put("msg", "更新失败");
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
        MfKindAttrExt mfKindAttrExt = new MfKindAttrExt();
        try{
            FormData formkindAttrExtDetail = formService.getFormData("kindAttrExtDetail");
            getFormValue(formkindAttrExtDetail, getMapByJson(ajaxData));
            if(this.validateFormData(formkindAttrExtDetail)){
                mfKindAttrExt = new MfKindAttrExt();
                setObjValue(formkindAttrExtDetail, mfKindAttrExt);
                mfKindAttrExtFeign.update(mfKindAttrExt);
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
    public Map<String, Object> getByIdAjax(String kindNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String,Object> formData = new HashMap<String,Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formkindAttrExtDetail = formService.getFormData("kindAttrExtDetail");
        MfKindAttrExt mfKindAttrExt = new MfKindAttrExt();
        mfKindAttrExt.setKindNo(kindNo);
        mfKindAttrExt = mfKindAttrExtFeign.getById(mfKindAttrExt);
        getObjValue(formkindAttrExtDetail, mfKindAttrExt,formData);
        if(mfKindAttrExt!=null){
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
    public Map<String, Object> deleteAjax(String kindNo) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfKindAttrExt mfKindAttrExt = new MfKindAttrExt();
        mfKindAttrExt.setKindNo(kindNo);
        try {
            mfKindAttrExtFeign.delete(mfKindAttrExt);
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
        FormData formkindAttrExtDetail = formService.getFormData("kindAttrExtDetail");
        //过滤已经配置的产品
        Map<String,String>  kindNoMap = new CodeUtils().getMapByKeyName("KIND_NO");
        MfKindAttrExt mfKindAttrExt = new MfKindAttrExt();
        List<MfKindAttrExt> mfKindAttrExtList = mfKindAttrExtFeign.getMfKindAttrExtList(mfKindAttrExt);
        for(MfKindAttrExt kindAttrExt :mfKindAttrExtList){
            kindNoMap.remove(kindAttrExt.getKindNo());
        }
        List<OptionsList> kindList = new ArrayList<OptionsList>();
        Iterator kindNoIt = kindNoMap.entrySet().iterator();
        while(kindNoIt.hasNext()){
            Map.Entry entry = (Map.Entry) kindNoIt.next();
            OptionsList s= new OptionsList();
            s.setOptionValue((String) entry.getKey());
            s.setOptionLabel((String) entry.getValue());
            kindList.add(s);
        }
		this.changeFormProperty(formkindAttrExtDetail, "kindNo", "optionArray", kindList);
        model.addAttribute("formkindAttrExtDetail", formkindAttrExtDetail);
        model.addAttribute("query", "");
        return "/component/cus/MfKindAttrExt_Insert";
    }
    /**
     * 查询
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(Model model,String kindNo) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formkindAttrExtbase = formService.getFormData("kindAttrExtbase");
        getFormValue(formkindAttrExtbase);
        MfKindAttrExt mfKindAttrExt = new MfKindAttrExt();
        mfKindAttrExt.setKindNo(kindNo);
        mfKindAttrExt = mfKindAttrExtFeign.getById(mfKindAttrExt);
        getObjValue(formkindAttrExtbase, mfKindAttrExt);
        model.addAttribute("formkindAttrExtbase", formkindAttrExtbase);
        model.addAttribute("query", "");
        return "/component/cus/MfKindAttrExt_Detail";
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
        FormData formkindAttrExtDetail = formService.getFormData("kindAttrExtDetail");
        getFormValue(formkindAttrExtDetail);
        boolean validateFlag = this.validateFormData(formkindAttrExtDetail);
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
        FormData formkindAttrExtDetail = formService.getFormData("kindAttrExtDetail");
        getFormValue(formkindAttrExtDetail);
        boolean validateFlag = this.validateFormData(formkindAttrExtDetail);
    }
}
