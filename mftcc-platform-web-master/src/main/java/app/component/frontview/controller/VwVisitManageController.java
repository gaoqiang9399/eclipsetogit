package app.component.frontview.controller;

import app.component.frontview.entity.MfAppCollectItem;
import app.component.frontview.entity.VwVisitManage;
import app.component.frontview.feign.VwVisitManageFeign;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/vwVisitManage")
public class VwVisitManageController  extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private VwVisitManageFeign vwVisitManageFeign;



    @ResponseBody
    @RequestMapping(value = "/findByPageAjax")
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        VwVisitManage vwVisitManage = new VwVisitManage();
        try {
            vwVisitManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
            vwVisitManage.setCriteriaList(vwVisitManage, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            Map<String,Object> params=new HashMap<>();
            params.put("vwVisitManage",vwVisitManage);
            ipage.setParams(params);
            ipage = vwVisitManageFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return dataMap;

    }
    @RequestMapping(value = "/findByPage")
    public String findByPage() throws Exception {
        return "component/frontview/VwVisitManage_List";
    }

   
}
