package app.component.tools.controller;

import app.component.examine.entity.MfExamineTemplateConfig;
import app.component.examinterface.ExamInterfaceFeign;
import app.component.freeMarker.entity.FreeMarkerDownloadModel;
import app.component.msgconf.entity.PliWarning;
import app.component.tools.entity.MfToolsDownload;
import app.component.tools.feign.FreeMarkerDownloadModelFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/freeMarkerDownloadModel")
public class FreeMarkerDownloadModelController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private FreeMarkerDownloadModelFeign freeMarkerDownloadModelFeign;
    @Autowired
    private ExamInterfaceFeign examInterfaceFeign;
    /**
     * 查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String templateNo) throws Exception{
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        FormData formfreeMarkerModel = formService.getFormData("formfreeMarkerModel");
        getFormValue(formfreeMarkerModel);
      //  MfToolsDownload mfToolsDownload = new MfToolsDownload();
        FreeMarkerDownloadModel freeMarkerDownloadModel=new FreeMarkerDownloadModel();
        freeMarkerDownloadModel.setTemplateNo(templateNo);
     //   mfToolsDownload.setSerialNo(serialNo);
        freeMarkerDownloadModel = freeMarkerDownloadModelFeign.getById(freeMarkerDownloadModel);
        getObjValue(formfreeMarkerModel, freeMarkerDownloadModel);
        model.addAttribute("formfreeMarkerModel", formfreeMarkerModel);
        model.addAttribute("query", "");
        return "/component/tools/FreeMarkerModel_Detail";
    }
    /**
     * 新增页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getInputPage")
    public String getInputPage(Model model, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        FormData formfreeMarker0002 = formService.getFormData("freeMarker0002");
        Map<String, Object> dataMap = new HashMap<String,Object>();
        List<MfExamineTemplateConfig> allExamineTemplate = examInterfaceFeign.getAllExamineTemplate();
        JSONArray examineTempArray = JSONArray.fromObject(allExamineTemplate);
        for (int i = 0; i < examineTempArray.size(); i++) {
            examineTempArray.getJSONObject(i).put("id",
                    examineTempArray.getJSONObject(i).getString("templateId"));
            examineTempArray.getJSONObject(i).put("name",
                    examineTempArray.getJSONObject(i).getString("templateName"));
        }
        dataMap.put("examineTempArray", examineTempArray);

        //发送对象
        JSONArray reciverTypeMap =new CodeUtils().getJSONArrayByKeyName("MSG_RECIVER_TYPE");
        String reciverTypeItems=reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("reciverTypeItems", reciverTypeItems);

        //发送方式
        JSONArray sendTypeMap =new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
        String sendTypeItems=sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("sendTypeItems", sendTypeItems);

        //变量来源类型
        JSONArray varUsageMap =new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
        String varUserageItems=varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
        dataMap.put("varUserageItems", varUserageItems);
        model.addAttribute("formfreeMarker0002", formfreeMarker0002);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        return "/component/tools/FreeMarkerModel_Insert";
    }
    /**
     * 列表有翻页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        FormData formfreeMarker0002 = formService.getFormData("freeMarker0002");
        Ipage ipage = this.getIpage();
        ipage = freeMarkerDownloadModelFeign.findByPage(ipage);
        model.addAttribute("formfreeMarker0002", formfreeMarker0002);
        model.addAttribute("ipage", ipage);
        model.addAttribute("query", "");
        return "/component/tools/FreeMarkerModel_List";
    }
    /**
     * AJAX获取查看
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDetailPage")
    public String getDetailPage(Model model, String templateNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formfreeMarker0002 = formService.getFormData("freeMarker0002");
     //   PliWarning pliWarning = new PliWarning();
        FreeMarkerDownloadModel freeMarkerDownloadModel=new FreeMarkerDownloadModel();
        freeMarkerDownloadModel.setTemplateNo(templateNo);
        freeMarkerDownloadModel=freeMarkerDownloadModelFeign.getById(freeMarkerDownloadModel);

        getObjValue(formfreeMarker0002, freeMarkerDownloadModel);

        model.addAttribute("formfreeMarker0002", formfreeMarker0002);
        this.changeFormProperty(formfreeMarker0002,"templateNo","readonly","1");
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        return "/component/tools/FreeMarkerModel_Detail";
    }
    /**
     * AJAX更新保存
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjaxp")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
   //     PliWarning pliWarning = new PliWarning();
        FreeMarkerDownloadModel freeMarkerDownloadModel=new FreeMarkerDownloadModel();
        try{
            FormData 	formfreeMarker0002 = formService.getFormData("freeMarker0002");
            getFormValue(formfreeMarker0002, getMapByJson(ajaxData));
            if(this.validateFormData(formfreeMarker0002)){
                setObjValue(formfreeMarker0002, freeMarkerDownloadModel);
                freeMarkerDownloadModelFeign.update(freeMarkerDownloadModel);
                getTableData(tableId, dataMap, freeMarkerDownloadModel);//获取列表
                dataMap.put("flag", "success");
                dataMap.put("msg", "更新成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "更新失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }
    /**
     * AJAX新增
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String args ="";
        try{
            FormData 	formfreeMarker0002 = formService.getFormData("freeMarker0002");
            getFormValue(formfreeMarker0002, getMapByJson(ajaxData));
            if(this.validateFormData(formfreeMarker0002)){
                FreeMarkerDownloadModel freeMarkerDownloadModel=new FreeMarkerDownloadModel();
                setObjValue(formfreeMarker0002, freeMarkerDownloadModel);
                FreeMarkerDownloadModel freeMarkerDownloadModel1=new FreeMarkerDownloadModel();
                freeMarkerDownloadModel1.setTemplateNo(freeMarkerDownloadModel.getTemplateNo());
                freeMarkerDownloadModel1= freeMarkerDownloadModelFeign.getById(freeMarkerDownloadModel1);
                if(freeMarkerDownloadModel1==null){
                    freeMarkerDownloadModelFeign.insert(freeMarkerDownloadModel);

                    getTableData(tableId, dataMap, freeMarkerDownloadModel);//获取列表
                    dataMap.put("flag", "success");
                    dataMap.put("msg", "新增成功");

                }else{
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "已存在模板编号");
                }

            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }
    /**
     * 获取列表数据
     * @param tableId
     * @param freeMarkerDownloadModel
     * @return
     * @throws Exception
     */
    private void getTableData(String tableId,Map<String,Object> dataMap, FreeMarkerDownloadModel freeMarkerDownloadModel) throws Exception {
        JsonTableUtil jtu = new JsonTableUtil();
        String  tableHtml = jtu.getJsonStr(tableId,"tableTag", freeMarkerDownloadModelFeign.getAll(freeMarkerDownloadModel), null,true);
        dataMap.put("tableData",tableHtml);
    }
    /**
     * 列表有翻页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
     FreeMarkerDownloadModel freeMarkerDownloadModel=new FreeMarkerDownloadModel();

        Ipage ipage = this.getIpage();
        ipage.setPageSize(pageSize);
        ipage.setPageNo(pageNo);
        ipage.setParams(this.setIpageParams("freeMarkerDownloadModel", freeMarkerDownloadModel));
        ipage = freeMarkerDownloadModelFeign.findByPage(ipage);
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, tableType,
                (List) ipage.getResult(), ipage, true);
        ipage.setResult(tableHtml);
        dataMap.put("ipage", ipage);
        return dataMap;
    }
    /**
     * AJAX更新保存
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateFlagAjax")
    @ResponseBody
    public Map<String, Object> updateFlagAjax(String ajaxData,String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONObject jobj = JSONObject.fromObject(ajaxData);
            FormData 	formfreeMarker0002 = formService.getFormData("freeMarker0002");
            getFormValue(formfreeMarker0002, jobj);
          //  PliWarning 	pliWarning = new PliWarning();
            FreeMarkerDownloadModel freeMarkerDownloadModel=new FreeMarkerDownloadModel();
            setObjValue(formfreeMarker0002, freeMarkerDownloadModel);
            int count = freeMarkerDownloadModelFeign.updateFlag(freeMarkerDownloadModel);
            if (count > 0) {
                freeMarkerDownloadModel = freeMarkerDownloadModelFeign.getById(freeMarkerDownloadModel);
                ArrayList<FreeMarkerDownloadModel> freeMarkerDownloadModelArrayList = new ArrayList<FreeMarkerDownloadModel>();
                freeMarkerDownloadModelArrayList.add(freeMarkerDownloadModel);
                getTableData(tableId, dataMap, freeMarkerDownloadModelArrayList);
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
    private void getTableData(String tableId,Map<String,Object> dataMap,List<FreeMarkerDownloadModel> list) throws Exception {
        JsonTableUtil jtu = new JsonTableUtil();
        String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null,
                true);
        dataMap.put("tableData", tableHtml);
    }


}
