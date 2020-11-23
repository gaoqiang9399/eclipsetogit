package app.component.archives.controller;

import app.component.query.entity.MfQueryItem;
import app.component.queryinterface.QueryInterfaceFeign;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中关村担保档案管理模块入口
 * @author weisd
 * @version 1.0
 * @date 2020/3/20 10:21
 */
@Controller
@RequestMapping("/archiveEntrance")
public class ArchiveEntranceController  extends BaseFormBean {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private QueryInterfaceFeign queryInterfaceFeign;

    @RequestMapping("/getEntrance")
    public String getEntrance(Model model, String type) throws Exception{
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap = new HashMap();
        //获取当前操作员的档案管理功能列表
        MfQueryItem mfQueryItem = new MfQueryItem();
        mfQueryItem.setFuncType(type);
        mfQueryItem.setIsBase("1");
        mfQueryItem.setAttentionFlag("0");
        List<MfQueryItem> mfQueryItemList = queryInterfaceFeign.getMfQueryItemList(mfQueryItem);
        JSONArray jsonArray = JSONArray.fromObject(mfQueryItemList);
        dataMap.put("mfQueryItemList", jsonArray);
        String ajaxData = jsonArray.toString();
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("mfQueryItemList", mfQueryItemList);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("query", "");
        return "/component/archives/entrance/ArchiveEntrance";
    }
}
