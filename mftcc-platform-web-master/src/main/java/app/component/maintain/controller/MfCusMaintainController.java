package app.component.maintain.controller;
import app.base.User;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.component.maintain.feign.MfCusMaintainFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import app.util.toolkit.Ipage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mfCusMaintain")
public class MfCusMaintainController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCusMaintainFeign mfCusMaintainFeign;
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;

    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;

    /**
     *
     * 方法描述： 跳转客户姓名修改编辑表单
     *
     * @return
     * @throws Exception String
     * @author 陈迪
     * @date 2019-02-21 下午15:25:00
     */
    @RequestMapping(value = "/cusNameInput")
    public String cusNameInput(Model model,String ajaxData) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        String formId = "cusNameEdit";
        FormData formcusNameEdit = formService.getFormData(formId);
        getFormValue(formcusNameEdit);
        model.addAttribute("formcusNameEdit", formcusNameEdit);
        model.addAttribute("query", "");
        return "/component/maintain/MfCusMaintain_cusNameInput";
    }


    /**
     *
     * 方法描述： 跳转客户证件号码修改编辑表单
     *
     * @return
     * @throws Exception String
     * @author 陈迪
     * @date 2019-02-25 下午14:54:00
     */
    @RequestMapping(value = "/cusIdNumInput")
    public String cusIdNumInput(Model model,String ajaxData)  throws Exception {
        ActionContext.initialize(request,response);
        FormService formService=new FormService();
        String formId="cusIdNumEdit";
        FormData formcusIdNumEdit=formService.getFormData(formId);
        getFormValue(formcusIdNumEdit);
        model.addAttribute("formcusIdNumEdit",formcusIdNumEdit);
        model.addAttribute("query","");
        return "/component/maintain/MfCusMaintain_cusIdNumInput";
    }

    /**
     *
     * 方法描述： 跳转客户证件号码修改编辑表单
     *
     * @return
     * @throws Exception String
     * @author 陈迪
     * @date 2019-02-26 下午19:54:00
     */
    @RequestMapping(value = "/cusTelInput")
    public String cusTelInput(Model model,String ajaxData)  throws Exception {
        ActionContext.initialize(request,response);
        FormService formService=new FormService();
        String formId="cusTelEdit";
        FormData formcusTelEdit=formService.getFormData(formId);
        getFormValue(formcusTelEdit);
        model.addAttribute("formcusTelEdit",formcusTelEdit);
        model.addAttribute("query","");
        return "/component/maintain/MfCusMaintain_cusTelInput";
    }
    /**
     *
     * 方法描述：客户姓名修改
     *
     * @return
     * @throws Exception String
     * @author  陈迪
     * @param ajaxData
     * @date 2019-02-22 下午17:29:50
     */
    @RequestMapping(value = "/updateNameAjax")
    @ResponseBody
    public Map<String, Object> updateNameAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        try {
            Map map = getMapByJson(ajaxData);
            FormData formcusNameEdit = formService.getFormData((String)map.get("formId"));
            getFormValue(formcusNameEdit, map);
            if(this.validateFormDataAnchor(formcusNameEdit)){
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                setObjValue(formcusNameEdit, mfCusCustomer);
                mfCusCustomer = mfCusMaintainFeign.updateCusName(mfCusCustomer);
                dataMap.put("mfCusCustomer", mfCusCustomer);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
            }catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }


    /**
     *
     * 方法描述：客户证件号码修改
     *
     * @return
     * @throws Exception String
     * @author  陈迪
     * @param ajaxData
     * @date 2019-02-22 下午17:29:50
     */
    @RequestMapping(value = "/updateIdNumAjax")
    @ResponseBody
    public Map<String, Object> updateIdNumAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        try {
            Map map = getMapByJson(ajaxData);
            FormData formidNumEdit = formService.getFormData((String)map.get("formId"));
            getFormValue(formidNumEdit, map);
            if(this.validateFormDataAnchor(formidNumEdit)){
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                setObjValue(formidNumEdit, mfCusCustomer);
                mfCusCustomer = mfCusMaintainFeign.updateIdNum(mfCusCustomer);
                dataMap.put("mfCusCustomer", mfCusCustomer);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;

    }


    /**
     *
     * 方法描述：客户手机号码修改
     *
     * @return
     * @throws Exception String
     * @author  陈迪
     * @param ajaxData
     * @date 2019-02-26 下午19:55:50
     */
    @RequestMapping(value = "/updateCusTelAjax")
    @ResponseBody
    public Map<String, Object> updateCusTelAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        try {
            Map map = getMapByJson(ajaxData);
            FormData formcusTelEdit = formService.getFormData((String)map.get("formId"));
            getFormValue(formcusTelEdit, map);
            if(this.validateFormDataAnchor(formcusTelEdit)){
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                setObjValue(formcusTelEdit, mfCusCustomer);
                mfCusCustomer = mfCusMaintainFeign.updateCusTel(mfCusCustomer);
                dataMap.put("mfCusCustomer", mfCusCustomer);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;

    }


    /**
     * 方法描述： 查询所有客户信息
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-3-18 下午12:20:30
     */
    @RequestMapping(value = "/selectCusInfo")
    @ResponseBody
    public Map<String, Object> selectCusInfo(int pageNo, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
            ipage =mfCusMaintainFeign.findByPage(ipage);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;

    }


}