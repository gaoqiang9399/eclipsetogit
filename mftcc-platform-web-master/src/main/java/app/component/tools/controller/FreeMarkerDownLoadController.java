package app.component.tools.controller;

import app.component.tools.feign.FreeMarkerDownLoadFeign;
import app.util.FileIOStreamTool;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/freeMarkerDownLoad")
public class FreeMarkerDownLoadController  extends BaseFormBean {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private FreeMarkerDownLoadFeign freeMarkerDownLoadFeign;


    @RequestMapping(value = "/generateZipPackage")
    @ResponseBody
    public Map<String, Object> generateZipPackage(@RequestBody JSONObject requestJso) {
        Map<String, String> param=JSONObject.fromObject(requestJso);
        return freeMarkerDownLoadFeign.freeMarkerDownLoad(param);
    }


    @RequestMapping(value = "/downLoadZipPackage")
    @ResponseBody
    public String downLoadZipPackage(String fileName) throws Exception {
        ActionContext.initialize(request, response);

        File fileZip = new File(ymlConfig.getUpload().get("freeMarkerDoc")+"/"+fileName+"/"+ fileName + ".zip");

        FileIOStreamTool.generateFileIOStream(fileZip, response, false);

        return null;
    }






}
