package app.component.cus.report.controller;


import app.component.cus.report.entity.MfCusReportAcount;
import app.component.cus.report.entity.MfCusReportCalc;
import app.component.cus.report.entity.MfCusReportData;
import app.component.cus.report.entity.MfCusReportItem;
import app.component.cus.report.feign.MfCusReportAcountFeign;
import app.component.cus.report.feign.MfCusReportCalcFeign;
import app.component.cus.report.feign.MfCusReportDataFeign;
import app.component.cus.report.feign.MfCusReportItemFeign;
import app.component.cus.report.util.EncryptUtil;
import app.component.cus.report.util.JsonUtils;
import app.component.cusinterface.CusInterfaceFeign;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/mfCusReportData")
public class MfCusReportDataController extends BaseFormBean {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private MfCusReportDataFeign mfCusReportDataFeign;
    @Autowired
    private MfCusReportAcountFeign mfCusReportAcountFeign;
    @Autowired
    private MfCusReportItemFeign mfCusReportItemFeign;
    @Autowired
    private MfCusReportCalcFeign mfCusReportCalcFeign;
    @Autowired
    private YmlConfig ymlConfig;


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/inputReportOnline")
    public String inputReportOnline(Model model, String cusNo, String reportType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<MfCusReportData> assetsList = new ArrayList<MfCusReportData>();
        List<MfCusReportData> assetsList1 = new ArrayList<MfCusReportData>();
        List<MfCusReportData> cashList = new ArrayList<MfCusReportData>();
        List<MfCusReportData> profList = new ArrayList<MfCusReportData>();
        boolean subjectBal=false;
        MfCusReportAcount mfCusReportAcount = new MfCusReportAcount();
        MfCusReportData mfCusReportData = null;
        List<MfCusReportItem> list = null;
        MfCusReportItem mfCusReportItem = null;
        String query = "";
        //资产负债表
        if("001".equals(reportType)||"005".equals(reportType)){
            mfCusReportItem = new  MfCusReportItem();
            mfCusReportItem.setReportTypeId("001");
            mfCusReportItem.setReportItemType("0");
            list = mfCusReportItemFeign.getList(mfCusReportItem);
            for (MfCusReportItem item : list){
                mfCusReportData = new MfCusReportData();
                PropertyUtils.copyProperties(mfCusReportData, item);
                assetsList.add(mfCusReportData);
                mfCusReportData = null;
                getCalInfo(dataMap,item.getReportItemId());
            }

            mfCusReportItem = new  MfCusReportItem();
            mfCusReportItem.setReportTypeId("001");
            mfCusReportItem.setReportItemType("1");
            list = mfCusReportItemFeign.getList(mfCusReportItem);
            for (MfCusReportItem item : list){
                mfCusReportData = new MfCusReportData();
                PropertyUtils.copyProperties(mfCusReportData, item);
                assetsList1.add(mfCusReportData);
                mfCusReportData = null;
                getCalInfo(dataMap,item.getReportItemId());
            }
        }


        //现金流量表
        if("002".equals(reportType)||"005".equals(reportType)) {
            mfCusReportItem = new MfCusReportItem();
            mfCusReportItem.setReportTypeId("002");
            list = mfCusReportItemFeign.getList(mfCusReportItem);
            for (MfCusReportItem item : list) {
                mfCusReportData = new MfCusReportData();
                PropertyUtils.copyProperties(mfCusReportData, item);
                cashList.add(mfCusReportData);
                mfCusReportData = null;
                getCalInfo(dataMap,item.getReportItemId());
            }
        }
        //利润表
        if("003".equals(reportType)||"005".equals(reportType)) {
            mfCusReportItem = new MfCusReportItem();
            mfCusReportItem.setReportTypeId("003");
            list = mfCusReportItemFeign.getList(mfCusReportItem);
            for (MfCusReportItem item : list) {
                mfCusReportData = new MfCusReportData();
                PropertyUtils.copyProperties(mfCusReportData, item);
                profList.add(mfCusReportData);
                mfCusReportData = null;
                getCalInfo(dataMap,item.getReportItemId());
            }
        }

        if("004".equals(reportType)||"005".equals(reportType)) {
            subjectBal=true;
        }
        mfCusReportAcount.setCusNo(cusNo);
        dataMap.put("mfCusReportAcount", mfCusReportAcount);
        dataMap.put("assetsList", assetsList);
        dataMap.put("assetsList1", assetsList1);
        dataMap.put("cashList", cashList);
        dataMap.put("profList", profList);
        dataMap.put("subjectBal", subjectBal);
        dataMap = JSONObject.fromObject(dataMap);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", query);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("reportTypeId", reportType);
        return "/component/pfs/CusReport_InsertOnline";
    }



    public void  getCalInfo(Map<String, Object> dataMap,String reportItemId) throws Exception {
        MfCusReportCalc mfCusReportCalc=new MfCusReportCalc();
        mfCusReportCalc.setReportItemId(reportItemId);
        List<MfCusReportCalc> list=mfCusReportCalcFeign.getList(mfCusReportCalc);
        if(list!=null&&list.size()>0){
            dataMap.put(reportItemId, list);
        }
    }
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insertOnlineAjax")
    @ResponseBody
    public Map<String, Object> insertOnlineAjax(String ajaxData, String cusNo, String reportTypeId, String reportAcount) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
//            JSONArray jsonArray1 = JSONArray.fromObject(ajaxData);
//            List<MfCusReportData> mfCusReportDataList = (List<MfCusReportData>) JSONArray.toList(jsonArray1,
//                    new MfCusReportData(), new JsonConfig());
            dataMap.put("ajaxData", ajaxData);
            dataMap.put("reportAcount", reportAcount);
            dataMap.put("reportTypeId", reportTypeId);
            mfCusReportDataFeign.insertOrUpdateOnline(dataMap);
            JSONObject jsonObject = JSONObject.fromObject(reportAcount);
            MfCusReportAcount mfCusReportAcount = (MfCusReportAcount) JSONObject.toBean(jsonObject, MfCusReportAcount.class);
            mfCusReportAcount = mfCusReportAcountFeign.getById(mfCusReportAcount);
            if (mfCusReportAcount != null){
                dataMap.put("assetsId", mfCusReportAcount.getAssetsDataId());
                dataMap.put("cashId", mfCusReportAcount.getCashDataId());
                dataMap.put("incomId", mfCusReportAcount.getIncomeDataId());
            }
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jb = JSONObject.fromObject(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage()+(jb.get("message")==null?"":"["+jb.get("message")+"]"));
        }
        return dataMap;
    }

    @RequestMapping(value = "/isExistReportAjax")
    @ResponseBody
    public Map<String, Object> isExistReportAjax(String cusNo, String reportTypeId, String reportAcount) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = JSONObject.fromObject(reportAcount);
            MfCusReportAcount mfCusReportAcount = (MfCusReportAcount) JSONObject.toBean(jsonObject, MfCusReportAcount.class);
            mfCusReportAcount = mfCusReportAcountFeign.getById(mfCusReportAcount);
            String isExist = "false";
            if (mfCusReportAcount != null){
                if ("001".equals(reportTypeId) && StringUtil.isNotEmpty(mfCusReportAcount.getAssetsDataId())){
                    isExist = "true";
                }else if ("002".equals(reportTypeId) && StringUtil.isNotEmpty(mfCusReportAcount.getCashDataId())){
                    isExist = "true";
                }else if ("003".equals(reportTypeId) && StringUtil.isNotEmpty(mfCusReportAcount.getIncomeDataId())){
                    isExist = "true";
                }else {
                }
            }
            dataMap.put("isExist", isExist);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
        }
        return dataMap;
    }

    /**
     * 保存/更新
     *
     * @author LJW date 2017-4-18
     * @param finRptDate
     * @param ajaxData
     * @param finRptType
     * @param relationCorpName
     * @param relationCorpNo
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insertOrUpdateAjax")
    @ResponseBody
    public Map<String, Object> insertOrUpdateAjax(String cusNo, String finRptDate, String ajaxData, String finRptType,
                                                  String relationCorpName, String relationCorpNo,String reportType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONArray jsonArray1 = JSONArray.fromObject(ajaxData);
            List<MfCusReportData> mfCusReportDataList = (List<MfCusReportData>) JSONArray.toList(jsonArray1,
                    new MfCusReportData(), new JsonConfig());
            MfCusReportData cusFinCapData = new MfCusReportData();
            MfCusReportAcount mfCusReportAcount=new MfCusReportAcount();
            mfCusReportAcount.setWeeks(finRptDate);
            mfCusReportAcount.setReportType(finRptType);
            mfCusReportAcount.setCusNo(cusNo);
            String accRule = "1";
            mfCusReportAcount.setAccRule(accRule);
            List<MfCusReportAcount> list=mfCusReportAcountFeign.getList(mfCusReportAcount);
            if(list!=null&&list.size()==1){
                mfCusReportAcount=list.get(0);
                String dataId="";
                if("1".equals(reportType)){
                    dataId=mfCusReportAcount.getAssetsDataId();
                }else   if("2".equals(reportType)){
                    dataId=mfCusReportAcount.getIncomeDataId();
                }else   if("3".equals(reportType)){
                    dataId=mfCusReportAcount.getCashDataId();
                }else   if("5".equals(reportType)){
                    dataId=mfCusReportAcount.getBalanceDataId();
                }else {
                }
                mfCusReportAcountFeign.insertOrUpdate(mfCusReportDataList,dataId);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", "数据异常,请联系管理员");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
        }
        return dataMap;
    }


    @ResponseBody
    @RequestMapping(value = "/visitFinancePlatForm")
    public Map<String, Object> visitFinancePlatForm(String cusNo) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String token = "";
        String sign = "";
        String url = ymlConfig.getWebservice().get("fincReportUrl");
        String key = ymlConfig.getWebservice().get("fincReportKey");
        String companyname = cusNo;
        try {
            if(StringUtil.isNotEmpty(cusNo)){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
                String time = sdf.format(new Date());
                companyname = EncryptUtil.encryptbykey(companyname,key);
                token = cusNo+time;
                //括号中参数为 加密前的companyname +加密前的token +time
                sign = EncryptUtil.md5(cusNo+token+time);
                Map<String,String> map = new HashMap<String,String>();
                map.put("companyname", cusNo);//加密前的companyname
                map.put("token", token);//不加密的token
                map.put("time", time);
                map.put("sign", sign);//MD5加密后的sign
                map.put("systemSource", "2");//MD5加密后的sign
                String check = JsonUtils.objectToJson(map);
                check = EncryptUtil.encryptbykey(check,key);
                dataMap.put("flag", "success");
                dataMap.put("check", check);
                dataMap.put("name", companyname);
                dataMap.put("url", url);
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", "客户编号cusNo不能为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_SERVER);
        }
        return dataMap;
    }

}
