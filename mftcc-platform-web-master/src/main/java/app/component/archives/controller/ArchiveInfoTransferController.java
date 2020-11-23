package app.component.archives.controller;

import app.base.User;
import app.component.archives.entity.ArchiveInfoMain;
import app.component.archives.entity.ArchiveInfoTransfer;
import app.component.archives.feign.ArchiveInfoMainFeign;
import app.component.archives.feign.ArchiveInfoTransferFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import com.core.domain.screen.FormData;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/archiveInfoTransfer")
public class ArchiveInfoTransferController extends BaseFormBean {
    //注入业务层
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ArchiveInfoMainFeign archiveInfoMainFeign;
    @Autowired
    private ArchiveInfoTransferFeign archiveInfoTransferFeign;

    /**
     * 列表有翻页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request,response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray statusJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_STATUS");
        model.addAttribute("statusJsonArray", statusJsonArray);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoTransfer_List";
    }

    /**
     * 查询接收确认列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPageForConfim")
    public String getListPageForConfim(Model model) throws Exception {
        ActionContext.initialize(request,response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray statusJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_STATUS");
        model.addAttribute("statusJsonArray", statusJsonArray);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoTransfer_ConfimList";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, String archiveStatus) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ArchiveInfoTransfer archiveInfoTransfer = new ArchiveInfoTransfer();
        try {
            archiveInfoTransfer.setCustomQuery(ajaxData);//自定义查询参数赋值
            archiveInfoTransfer.setCustomSorts(ajaxData);//自定义排序参数赋值
            archiveInfoTransfer.setCriteriaList(archiveInfoTransfer, ajaxData);//我的筛选
            if(archiveStatus != null && !"".equals(archiveStatus)){
                archiveInfoTransfer.setArchiveStatus(archiveStatus);
            }
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setParams(this.setIpageParams("archiveInfoTransfer", archiveInfoTransfer));
            //自定义查询Bo方法
            ipage = archiveInfoTransferFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage",ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("success", false);
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 档案移交
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchivetransferinsert = formService.getFormData("archivetransferinsert");
        ArchiveInfoTransfer archiveInfoTransfer = new ArchiveInfoTransfer();
        //更新信息
        archiveInfoTransfer.setOpNo(User.getRegNo(request));
        archiveInfoTransfer.setOpName(User.getRegName(request));
        archiveInfoTransfer.setBrNo(User.getOrgNo(request));
        archiveInfoTransfer.setBrName(User.getRegName(request));
        archiveInfoTransfer.setTransferDate(DateUtil.getDate());
        archiveInfoTransfer.setTransferNo(User.getRegNo(request));
        archiveInfoTransfer.setTransferName(User.getRegName(request));
        getObjValue(formarchivetransferinsert, archiveInfoTransfer);
        model.addAttribute("formarchivetransferinsert", formarchivetransferinsert);
        model.addAttribute("query", "");
        return "/component/archives/ArchiveInfoTransfer_Insert";
    }

    /**
     * AJAX档案移交
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            dataMap = getMapByJson(ajaxData);
            FormData formarchivetransferinsert = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formarchivetransferinsert, getMapByJson(ajaxData));
            if(this.validateFormData(formarchivetransferinsert)){
                ArchiveInfoTransfer archiveInfoTransfer = new ArchiveInfoTransfer();
                setObjValue(formarchivetransferinsert, archiveInfoTransfer);
                archiveInfoTransfer.setRegTime(DateUtil.getDateTime());
                archiveInfoTransferFeign.insert(archiveInfoTransfer);
                dataMap.put("flag", "success");
                dataMap.put("msg", "移交申请成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "移交申请失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * 档案移交确认
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/confimTransfer")
    public String confimTransfer(Model model,String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchivetransferconfim = formService.getFormData("archivetransferconfim");
        ArchiveInfoTransfer archiveInfoTransfer = new ArchiveInfoTransfer();
        archiveInfoTransfer.setId(id);
        archiveInfoTransfer = archiveInfoTransferFeign.getById(archiveInfoTransfer);
        getObjValue(formarchivetransferconfim, archiveInfoTransfer);
        model.addAttribute("formarchivetransferconfim", formarchivetransferconfim);
        model.addAttribute("query", "");
        return "/component/archives/ArchiveInfoTransfer_Confim";
    }

    /**
     * AJAX档案移交确认
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/confimAjax")
    @ResponseBody
    public Map<String, Object> confimAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            dataMap = getMapByJson(ajaxData);
            FormData formarchivetransferconfim = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formarchivetransferconfim, getMapByJson(ajaxData));
            if(this.validateFormData(formarchivetransferconfim)){
                ArchiveInfoTransfer archiveInfoTransfer = new ArchiveInfoTransfer();
                setObjValue(formarchivetransferconfim, archiveInfoTransfer);
                archiveInfoTransferFeign.confimTransfer(archiveInfoTransfer);
                dataMap.put("flag", "success");
                dataMap.put("msg", "接收确认成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "接收确认失败");
            throw e;
        }
        return dataMap;
    }

}
