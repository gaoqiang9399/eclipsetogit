package app.component.cus.share.controller;

import app.base.User;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.share.entity.MfCusCustomerShare;
import app.component.cus.share.feign.MfCusCustomerShareFeign;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  客户分享
 * @author xuzhenpeng
 * @version 1.0
 * @date 2020/2/17 17:20
 */
@Controller
@RequestMapping("/mfCusCustomerShare")
public class MfCusCustomerShareController extends BaseFormBean {

    private Logger log = LoggerFactory.getLogger(MfCusCustomerShareController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private MfCusCustomerShareFeign mfCusCustomerShareFeign;

    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;

    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        log.debug("客户编号:{}",cusNo);
        model.addAttribute("cusNo", cusNo);
        return "/component/cus/share/MfCusCustomerShare_List";
    }

    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model,String cusNo) throws Exception {
        log.debug("客户编号:{}",cusNo);
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formcusCustomerShareInput = formService.getFormData("cusCustomerShareInput");
        getFormValue(formcusCustomerShareInput);
        MfCusCustomerShare mfCusCustomerShare = new MfCusCustomerShare();
        mfCusCustomerShare.setCusNo(cusNo);
        mfCusCustomerShare.setCusMngNo(User.getRegNo(request));
        mfCusCustomerShare.setCusMngName(User.getRegName(request));
        getObjValue(formcusCustomerShareInput, mfCusCustomerShare);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("formcusCustomerShareInput", formcusCustomerShareInput);
        model.addAttribute("query", "");
        return "/component/cus/share/MfCusCustomerShare_Input";
    }

    /**
     *  新增客户共享信息
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public Map<String, Object> insert(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap();
        try {
            FormData cusCustomerShareInput = formService.getFormData("cusCustomerShareInput");
            getFormValue(cusCustomerShareInput, getMapByJson(ajaxData));
            if(this.validateFormData(cusCustomerShareInput)){
                MfCusCustomerShare mfCusCustomerShare = new MfCusCustomerShare();
                setObjValue(cusCustomerShareInput, mfCusCustomerShare);
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusCustomerShare.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                if(null != mfCusCustomer){
                    String opNo = User.getRegNo(request);
                    if(!opNo.equals(mfCusCustomer.getCusMngNo())){
                        dataMap.put("flag", "error");
                        dataMap.put("msg","您不是此客户项目经理，不允许操作！");
                        return dataMap;
                    }
                }else{
                    dataMap.put("flag", "error");
                    dataMap.put("msg","客户信息不存在！");
                    return dataMap;
                }
                MfCusCustomerShare tempCustomerShare = new MfCusCustomerShare();
                tempCustomerShare.setCusNo(mfCusCustomerShare.getCusNo());
                tempCustomerShare.setShareMngNo(mfCusCustomerShare.getShareMngNo());
                List<MfCusCustomerShare> tempList = mfCusCustomerShareFeign.getCusCustomerShareList(tempCustomerShare);
                if(null != tempList && tempList.size() >0){
                    dataMap.put("flag", "error");
                    dataMap.put("msg","共享的项目经理已存在！");
                    return dataMap;
                }
                mfCusCustomerShare.setId(WaterIdUtil.getWaterId());
                mfCusCustomerShare.setShareDate(DateUtil.getDateTime());
                mfCusCustomerShareFeign.insert(mfCusCustomerShare);
                dataMap.put("flag", "success");
                dataMap.put("msg", "新增成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     *  获取客户共享列表
     * @param cusNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCusCustomerShareList")
    @ResponseBody
    public Map<String, Object> getCusCustomerShareList(String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap();
        log.debug("客户编号:{}",cusNo);
        MfCusCustomerShare mfCusCustomerShare=new MfCusCustomerShare();
        try {
            mfCusCustomerShare.setCusNo(cusNo);
            List<MfCusCustomerShare> customerShareList = mfCusCustomerShareFeign.getCusCustomerShareList(mfCusCustomerShare);
            MfCusCustomer mfCusCustomer = new MfCusCustomer();
            mfCusCustomer.setCusNo(mfCusCustomerShare.getCusNo());
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr("tablecusCustomerShareList", "tableTag",customerShareList, null, true);
            dataMap.put("flag", "success");
            dataMap.put("tableHtml", tableHtml);
            dataMap.put("cusMngNo", mfCusCustomer.getCusMngNo());
            dataMap.put("opNo", User.getRegNo(request));
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     *  删除客户共享信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public Map<String, Object> deleteById(String id) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap();
        log.debug("客户共享ID:{}",id);
        try {
            MfCusCustomerShare mfCusCustomerShare = new MfCusCustomerShare();
            mfCusCustomerShare.setId(id);
            List<MfCusCustomerShare> shareds = mfCusCustomerShareFeign.getCusCustomerShareList(mfCusCustomerShare);
            if(!User.getRegNo(request).equals( shareds.get(0).getCusMngNo())){
                dataMap.put("flag", "error");
                dataMap.put("msg", "您不是此客户管户项目经理，不能删除！");
                return dataMap;
            }
            mfCusCustomerShareFeign.deleteById(id);
            dataMap.put("flag", "success");
            dataMap.put("msg", "删除成功！");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     *  获取客户共享列表
     * @param cusNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCusCustomerShareStatus")
    @ResponseBody
    public Map<String, Object> getCusCustomerShareStatus(String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap();
        log.debug("客户编号:{}",cusNo);
        MfCusCustomerShare mfCusCustomerShare=new MfCusCustomerShare();
        try {
            mfCusCustomerShare.setCusNo(cusNo);
            List<MfCusCustomerShare> customerShareList = mfCusCustomerShareFeign.getCusCustomerShareList(mfCusCustomerShare);
            if(null != customerShareList &&  customerShareList.size() > 0){
                dataMap.put("status", "1");
            }else{
                dataMap.put("status", "0");
            }
            dataMap.put("flag", "success");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

}
