package app.component.thirdservice.shoujia.controller;

import app.component.thirdservice.entity.PropertyType;
import app.component.thirdservice.shoujia.feign.MfHouseEvalFeign;
import app.component.thirdservice.shoujia.feign.PropertyTypeFeign;
import cn.mftcc.util.StringUtil;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
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
@RequestMapping("/mfHouseEval")
public class MfHouseEvalController  extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private PropertyTypeFeign propertyTypeFeign;
    @Autowired
    private MfHouseEvalFeign mfHouseEvalFeign;


    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPropertyTypeList")
    public String getPropertyTypeList(Model model,String TypeCode,String AreaCode) throws Exception {
        ActionContext.initialize(request, response);

        //封装接口需要的参数
        Map<String,Object> parmMap = new HashMap<>();
        parmMap.put("AreaCode", AreaCode);
        parmMap.put("TypeCode", "0");
        parmMap.put("PropertyType","134");
        parmMap.put("interfaceNo","Z1006");
        //对接首佳接口
        Map<String,Object> map = mfHouseEvalFeign.getInterfaceResult(parmMap);
        PropertyType propertyType = new PropertyType();
        propertyType.setLev("1");
        List<PropertyType> list = propertyTypeFeign.getAllList(propertyType);
        if(list !=null && list.size()>0){
            model.addAttribute("ajaxData", JSONArray.fromObject(list).toString());
        }
        model.addAttribute("isStepLoading", "true");
        return "/component/thirdservice/shoujia/property_view";
    }

    @ResponseBody
    @RequestMapping(value = "/getChildAjax")
    public Map<String,Object> getChildAjax(String areaNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        PropertyType propertyType = new PropertyType();
        propertyType.setUplev(areaNo);
        List<PropertyType> list = propertyTypeFeign.getAllList(propertyType);
        dataMap.put("childNodeList", list);
        return dataMap;
    }

    @ResponseBody
    @RequestMapping(value = "/getSecondProperty")
    public Map<String,Object> getSecondProperty(String areaNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            PropertyType propertyType = new PropertyType();
            propertyType.setAreaNo(areaNo);
            List<PropertyType> list = propertyTypeFeign.getAllList(propertyType);
            if(list != null && list.size()>0){
                dataMap.put("areaNo", list.get(0).getUplev());
                dataMap.put("flag", "success");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", "未查询到物业类型信息");
            }
        }catch (Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg",e.getMessage());
        }
        return dataMap;
    }

    @RequestMapping(value = "/getCommuntyList")
    public String getCommuntyList(Model model,String PropertyType,String Address,String AreaCode) throws Exception {
        ActionContext.initialize(request, response);

        //封装接口需要的参数
        Map<String,Object> parmMap = new HashMap<>();
        parmMap.put("AreaCode", AreaCode);
        parmMap.put("TypeCode", "1");
        parmMap.put("PropertyType",PropertyType);
        parmMap.put("Address",Address);
        parmMap.put("interfaceNo","Z1007");
        //分页每次50条
        parmMap.put("PageIndex","1");
        parmMap.put("PageSize","10");
        //对接首佳接口
        Map<String,Object> map = mfHouseEvalFeign.getInterfaceResult(parmMap);
        if(map != null && "0000".equals(map.get("status"))){
            model.addAttribute("ajaxData", map.get("ajaxData"));
        }
        model.addAttribute("isStepLoading", "false");
        return "/component/thirdservice/shoujia/property_view";
    }


    @RequestMapping(value = "/getStorieList")
    public String getStorieList(Model model,String PropertyType,String ProjectId,String AreaCode) throws Exception {
        ActionContext.initialize(request, response);

        //封装接口需要的参数
        Map<String,Object> parmMap = new HashMap<>();
        parmMap.put("AreaCode", AreaCode);
        parmMap.put("TypeCode", "2");
        parmMap.put("PropertyType",PropertyType);
        parmMap.put("ProjectId",ProjectId);
        parmMap.put("interfaceNo","Z1008");
        //分页每次50条
        parmMap.put("PageIndex","1");
        parmMap.put("PageSize","15");
        //对接首佳接口
        Map<String,Object> map = mfHouseEvalFeign.getInterfaceResult(parmMap);
        if(map != null && "0000".equals(map.get("status"))){
            model.addAttribute("ajaxData", map.get("ajaxData"));
        }
        model.addAttribute("isStepLoading", "false");
        return "/component/thirdservice/shoujia/property_view";
    }

    @RequestMapping(value = "/getSingleStorieList")
    public String getSingleStorieList(Model model,String ThirdProperty,String ProjectId,String AreaCode,String FloorId) throws Exception {
        ActionContext.initialize(request, response);
        //封装接口需要的参数
        Map<String,Object> parmMap = new HashMap<>();
        parmMap.put("AreaCode", AreaCode);
        parmMap.put("TypeCode", "3");
        parmMap.put("ThirdProperty",ThirdProperty);
        parmMap.put("ProjectId",ProjectId);
        parmMap.put("FloorId",FloorId);
        parmMap.put("interfaceNo","Z1009");
        //分页每次50条
        parmMap.put("PageIndex","1");
        parmMap.put("PageSize","15");
        //对接首佳接口
        Map<String,Object> map = mfHouseEvalFeign.getInterfaceResult(parmMap);
        if(map != null && "0000".equals(map.get("status"))){
            model.addAttribute("ajaxData", map.get("ajaxData"));
        }
        model.addAttribute("isStepLoading", "false");
        return "/component/thirdservice/shoujia/property_view";
    }


    @ResponseBody
    @RequestMapping(value = "/evalHouseInfo")
    public Map<String,Object> evalHouseInfo(String ajaxData,String flag,String relNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            if(StringUtil.isNotEmpty(relNo)){
                Map<String,Object> map = getMapByJson(ajaxData);
                //封装各类型参数
                map.put("AreaCode", map.get("areaCode"));
                map.put("PropertyType",map.get("secondProperty"));
                map.put("ThirdProperty",map.get("thirdProperty"));
                map.put("ProjectName",map.get("communityName"));
                map.put("Address",map.get("address"));
                map.put("BuildArea",map.get("residentialArea"));
                map.put("CompletionYear",map.get("complateDate")!=null?map.get("complateDate").toString().replaceAll("-", ""):"");
                map.put("CurrentFloor",map.get("floorNum"));
                map.put("TotalFloor",map.get("floor"));
//                map.put("Address",(String)map.get("areaCodeName")+map.get("communityName"));
                map.put("flag",flag);
                map.put("relNo",relNo);
                if("1".equals(flag)){
                    map.put("Toward", map.get("orientation"));
                    map.put("TypeCode", "5");
                    map.put("interfaceNo","Z1005");
                }else if("2".equals(flag)){
                    map.put("TypeCode", "8");
                    map.put("interfaceNo","Z1010");
                }
                dataMap = mfHouseEvalFeign.getInterfaceResult(map);
                if("0000".equals(dataMap.get("status"))){
                    dataMap.put("flag", "success");
                }else{
                    dataMap.put("flag", "error");
                }
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", "未传关联编号！");
            }
        }catch (Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg",e.getMessage());
        }
        return dataMap;
    }

    @ResponseBody
    @RequestMapping(value = "/reEvalHouseInfo")
    public Map<String,Object> reEvalHouseInfo(String evalId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            dataMap = mfHouseEvalFeign.reEvalHouseInfo(evalId);
            if("0000".equals(dataMap.get("status"))){
                dataMap.put("flag", "success");
            }else{
                dataMap.put("flag", "error");
            }
        }catch (Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg",e.getMessage());
        }
        return dataMap;
    }

}
