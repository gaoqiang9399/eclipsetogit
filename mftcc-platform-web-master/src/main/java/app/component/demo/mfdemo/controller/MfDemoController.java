package app.component.demo.mfdemo.controller;

import app.component.demo.mfdemo.entity.MfDemo;
import app.component.demo.mfdemo.feign.MfDemoFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
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

@Controller
@RequestMapping("/mfDemo")
public class MfDemoController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfDemoFeign mfDemoFeign;


    /**
     * 列表打开页面请求
     *
     * @param cusNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        JSONArray cusTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CUS_TYPE");
        model.addAttribute("cusTypeJsonArray", cusTypeJsonArray);
        return "/component/demo/mfdemo/MfDemo_List";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                              String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfDemo mfDemo = new MfDemo();
        try {
            mfDemo.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfDemo.setCriteriaList(mfDemo, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfDemo", mfDemo));
            ipage = mfDemoFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/input")
    public String input(Model model) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formmfdemo0001 = formService.getFormData("mfdemo0001");
        //如果需要可以在此通过调用来改变表单的属性值
//        List<OptionsList> optionsList = new ArrayList<OptionsList>();
//        // 给optionsList赋值
//        this.changeFormProperty(formmfdemo0001, "cusType", "optionArray", optionsList);
        model.addAttribute("formmfdemo0001", formmfdemo0001);
        model.addAttribute("query", "");
        return "/component/demo/mfdemo/MfDemo_Insert";
    }

    /**
     * AJAX新增
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            FormService formService = new FormService();
            String formId = (String)map.get("formId");
            FormData formmfdemo0001 = formService.getFormData(formId);
            getFormValue(formmfdemo0001, map);
            if(this.validateFormData(formmfdemo0001)){
                MfDemo mfDemo = new MfDemo();
                setObjValue(formmfdemo0001, mfDemo);
                mfDemoFeign.insert(mfDemo);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }




}
