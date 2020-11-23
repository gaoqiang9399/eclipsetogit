package app.component.carmodel.controller;

import app.component.carmodel.entity.MfCarModel;
import app.component.carmodel.entity.MfCarModelDetail;
import app.component.carmodel.feign.MfCarModelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import feign.Feign;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * @创始人 shenhaobing
 * @创建时间 2018/7/20
 * @描述
 */
@Controller
@RequestMapping("/mfCarModel")
public class MfCarModelController  extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCarModelFeign mfCarModelFeign;
    @RequestMapping("/getCarModelPageForSelect")
    public String getCarModelPageForSelect(Model model)
            throws Exception {
        ActionContext.initialize(request, response);
        return "/component/carmodel/carModel_select";
    }

    /**
     *
     * 方法描述： 获得选择押品分页列表
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @date 2017-5-2 下午6:03:32
     */
    @RequestMapping("/getCarModelListByPage")
    @ResponseBody
    public Map<String, Object> getCarModelListByPage(String ajaxData,Integer pageNo, Integer pageSize, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCarModel mfCarModel = new MfCarModel();
            mfCarModel.setCustomQuery(ajaxData);
            mfCarModel.setCustomSorts(ajaxData);
            mfCarModel.setCriteriaList(mfCarModel, ajaxData);

            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfCarModel", mfCarModel));
            ipage = mfCarModelFeign.getCarModelListByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            @SuppressWarnings("rawtypes")
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

    @RequestMapping("/getCarModelDetailById")
    @ResponseBody
    public Map<String, Object> getCarModelDetailById(String modelId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCarModelDetail mfCarModelDetail = new MfCarModelDetail();
            mfCarModelDetail.setModelId(modelId);
            mfCarModelDetail = mfCarModelFeign.getCarModelDetailById(mfCarModelDetail);
            if(mfCarModelDetail!=null){
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                String colorsDic = mfCarModelDetail.getColorsDic();
                if (StringUtil.isNotEmpty(colorsDic)){
                    String[] colorsDicArr = colorsDic.split("\\|");
                    for (int i = 0;i<colorsDicArr.length;i++){
                        String[] colorsArr = colorsDicArr[i].split("-");
                        jsonObject = new JSONObject();
                        jsonObject.put("id",colorsArr[0]);
                        jsonObject.put("name",colorsArr[1]);
                        jsonArray.add(jsonObject);
                    }
                }
                dataMap.put("mfCarModelDetail",mfCarModelDetail);
                if (jsonArray.size()>0){
                    dataMap.put("flag", "success");
                    dataMap.put("items",jsonArray);
                }else{
                    dataMap.put("flag", "error");
                }
            }else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
}
