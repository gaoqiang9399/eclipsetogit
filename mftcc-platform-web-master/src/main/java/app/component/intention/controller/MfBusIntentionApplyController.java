package app.component.intention.controller;

import app.component.intention.entity.MfBusIntentionApply;
import app.component.intention.feign.MfBusIntentionApplyFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 意向申请
 *
 * @author
 * @since 2020-02-21 14:48:04
 */
@Controller
@RequestMapping("/mfBusIntentionApply")
public class MfBusIntentionApplyController extends BaseFormBean {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 服务对象
     */
    @Autowired
    private MfBusIntentionApplyFeign mfBusIntentionApplyFeign;

    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        return "/component/intention/MfBusIntentionApply_List";
    }

    /**
     * 列表打开页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showApplyDetail")
    public String showApplyDetail(Model model,String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formbusIntentionApplyDetail = formService.getFormData("busIntentionApplyDetail");
        getFormValue(formbusIntentionApplyDetail);
        MfBusIntentionApply mfBusIntentionApply = mfBusIntentionApplyFeign.getById(id);
        getObjValue(formbusIntentionApplyDetail, mfBusIntentionApply);
        model.addAttribute("formbusIntentionApplyDetail", formbusIntentionApplyDetail);
        model.addAttribute("query", "");
        model.addAttribute("id", id);
        return "/component/intention/MfBusIntentionApply_Detail";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusIntentionApply mfBusIntentionApply = new MfBusIntentionApply();
        try {
            mfBusIntentionApply.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfBusIntentionApply.setCriteriaList(mfBusIntentionApply, ajaxData);//我的筛选
            mfBusIntentionApply.setCustomSorts(ajaxData);//自定义排序
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusIntentionApply", mfBusIntentionApply));
            ipage = mfBusIntentionApplyFeign.findByPage(ipage);
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

}