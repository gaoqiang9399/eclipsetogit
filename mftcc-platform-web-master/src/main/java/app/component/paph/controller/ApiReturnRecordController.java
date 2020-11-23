package app.component.paph.controller;

import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.autoapproveinterface.AutoApproveInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.*;
import app.component.cus.feign.*;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.paph.entity.ApiReturnRecord;
import app.component.paph.feign.ApiReturnRecordFeign;
import app.component.thirdservice.autoapproval.entity.MfAutoApprovalRecord;
import app.component.thirdservice.util.PdfUtil;
import app.component.thirdservice.util.ServletFreeMarker;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import config.YmlConfig;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Title: ApiReturnRecordAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 01 16:40:11 CST 2018
 **/
@Controller
@RequestMapping("/apiReturnRecord")
public class ApiReturnRecordController extends BaseFormBean{

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    @Autowired
    private ApiReturnRecordFeign apiReturnRecordFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private AutoApproveInterfaceFeign autoApproveInterfaceFeign;
    @Autowired
    private MfCusBankAccManageFeign mfCusBankAccManageFeign;
    @Autowired
    private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
    @Autowired
    private MfCusPersonJobFeign mfCusPersonJobFeign;
    @Autowired
    private MfCusFamilyInfoFeign mfCusFamilyInfoFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private MfCusHighInfoFeign mfCusHighInfoFeign;

    @Autowired
    private YmlConfig ymlConfig;
    @RequestMapping(value = "/riskManagement")
    public String riskManagement(Model model, String ajaxData, String appId,String cusNo) throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String,Object>();
        if(appId != null && !"".equals(appId)){

            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(appId);
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt()/10000));
            mfBusApply = mfBusApplyFeign.processDataForApply(mfBusApply);//处理利率格式
            //处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
            Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
            String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
            // 处理期限的展示单位。
            Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
            String termUnit= termTypeMap.get(mfBusApply.getTermType()).getRemark();
            dataMap.put("termUnit", termUnit);
            dataMap.put("rateUnit", rateUnit);
            model.addAttribute("mfBusApply", mfBusApply);
            model.addAttribute("dataMap", dataMap);
        }
        MfCusCustomer mfCusCustomer = new  MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
        JSONArray array= new CodeUtils().getJSONArrayByKeyName("THIRD_SERVICE_TYPE_HK");
        ajaxData = array.toString();
        model.addAttribute("mfCusCustomer", mfCusCustomer);
        model.addAttribute("baseType", mfCusCustomer.getCusBaseType());
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("appId", appId);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_RiskManagement";
    }

    @ResponseBody
    @RequestMapping(value = "/getApprovalFlag")
    public Map<String, String> getApprovalFlag(String appId) throws Exception{
        ActionContext.initialize(request, response);
        Map<String, String> dataMap = new HashMap<String,String>();
        MfAutoApprovalRecord mfAutoApprovalRecord = new MfAutoApprovalRecord();
        try {
            mfAutoApprovalRecord.setAppId(appId);
            mfAutoApprovalRecord = apiReturnRecordFeign.getApprovalFlagByAppId(mfAutoApprovalRecord);
            if(mfAutoApprovalRecord != null){
                if("0".equals(mfAutoApprovalRecord.getFlag())){
                    dataMap.put("flag","SelectError");
                }else {
                    dataMap.put("flag","success");
                }
            }else{
                dataMap.put("flag","null");
            }
        }catch (Exception e){
            dataMap.put("flag","error");
            dataMap.put("msg",e.getMessage());
        }
        return dataMap;
    }

    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/findByPageAjax")
    public Map<String, Object> findByPageAjax(String ajaxData,Integer pageNo,Integer pageSize,String tableId,String tableType,String cusNo) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        try {
            apiReturnRecord.setCustomQuery(ajaxData);// 自定义查询参数赋值
            apiReturnRecord.setCriteriaList(apiReturnRecord, ajaxData);// 我的筛选
            apiReturnRecord.setCustomSorts(ajaxData);// 自定义排序
            apiReturnRecord.setReturnId(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);
            ipage.setParams(this.setIpageParams("apiReturnRecord",apiReturnRecord));
            // 自定义查询Bo方法

            ipage = apiReturnRecordFeign.findByPage(ipage);
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


    @RequestMapping(value = "/inputResult")
    public String inputResult(Model model,String returnId,String threeParty,Integer id,String appId) throws Exception {
        ActionContext.initialize(request,response);
        model.addAttribute("returnId",returnId);
        model.addAttribute("threeParty", threeParty);
        model.addAttribute("token", request.getParameter("token"));
        model.addAttribute("id", id);
        if("TTYY".equals(threeParty)){
            Map<String, Object> dataMap = new HashMap<String,Object>();
            String fourResult = "";
            ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
            apiReturnRecord.setReturnId(returnId);
            apiReturnRecord.setThreeParty(threeParty);
            apiReturnRecord.setId(id);
            apiReturnRecord = apiReturnRecordFeign.getById(apiReturnRecord);
            String txTime = DateUtil.getFormatDateTime(apiReturnRecord.getTxTime(), "yyyy年MM月dd日 HH:mm:ss");
            apiReturnRecord.setTxTime(txTime);
            String httpStrs = apiReturnRecord.getReqPacketEncrypt();
            Map<String, Object> resultMap = new Gson().fromJson(httpStrs, Map.class);
            Map<String, Object> firstMap = (Map<String, Object>) resultMap.get("oirData");
            if(firstMap == null){
                String message = (String) resultMap.get("errormessage");
                model.addAttribute("message",message);
            }else{
                fourResult = (String) firstMap.get("FourResult");
                dataMap = getYYResult(firstMap);
                //黑产信息
                model.addAttribute("blackDetailMap",dataMap.get("blackDetailMap"));
                //查询账户基本信息
                MfBusApply mfBusApply = new MfBusApply();
                if(StringUtil.isNotBlank(appId)){
                    mfBusApply.setAppId(appId);
                    mfBusApply = mfBusApplyFeign.getById(mfBusApply);
                    model.addAttribute("mfBusApply",mfBusApply);
                }
                //查询客户信息
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(returnId);
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                model.addAttribute("mfCusCustomer",mfCusCustomer);
                //银行卡号
                MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
                mfCusBankAccManage.setCusNo(returnId);
                mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
                model.addAttribute("mfCusBankAccManage",mfCusBankAccManage);
                //邮箱
                MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
                mfCusPersBaseInfo.setCusNo(returnId);
                mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
                model.addAttribute("mfCusPersBaseInfo",mfCusPersBaseInfo);
                //职业信息公司名称
                MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
                mfCusPersonJob.setCusNo(returnId);
                mfCusPersonJob = mfCusPersonJobFeign.getNewById(mfCusPersonJob);
                model.addAttribute("mfCusPersonJob",mfCusPersonJob);
                //紧急联系人
                MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
                mfCusFamilyInfo.setCusNo(returnId);
                List<MfCusFamilyInfo> mfCusFamilyInfoList = mfCusFamilyInfoFeign.findByCusNo(mfCusFamilyInfo);
                model.addAttribute("mfCusFamilyInfoList",mfCusFamilyInfoList);
                //借款平台信息
                MfBusPact mfBusPact = new MfBusPact();
                if(StringUtil.isNotBlank(appId)){
                    mfBusPact = mfBusPactFeign.getByAppId(appId);
                    model.addAttribute("mfBusPact",mfBusPact);
                }
            }
            if(StringUtil.isNotBlank(fourResult)){
                dataMap.put("fourResult",fourResult);
            }
            Random rand = new Random();
            int random = rand.nextInt(999);
            model.addAttribute("random",random);
            model.addAttribute("apiReturnRecord",apiReturnRecord);
            model.addAttribute("dataMap",dataMap);
            model.addAttribute("query","");
            return "/component/app/MfBusApply_RiskManagementYYHtml";
        }
        return "/component/app/MfBusApply_RiskManagementResult";
    }

    private Map<String,Object> getYYResult(Map<String,Object> firstMap) {
        List<String> riskLabelList = new ArrayList<String>();
        List<String> riskDetailList = new ArrayList<String>();
        Map<String,Object> dataMap = new HashMap<String,Object>();
        Map<String,Object> parmMap = new HashMap<String,Object>();
        Map<String,Object> blackDetailMap = new HashMap<String,Object>();
        if(firstMap.get("RiskLabel").toString().length()>2) {
            riskLabelList = ( List<String>) firstMap.get("RiskLabel");
        }
        if(firstMap.get("RiskDetail").toString().length()>2) {
            parmMap = (Map<String,Object>) firstMap.get("RiskDetail");
        }
        if(firstMap.get("BlackDetail").toString().length()>1) {
            blackDetailMap = (Map<String,Object>) firstMap.get("BlackDetail");
            dataMap.put("blackDetailMap",blackDetailMap);
        }
        //判断反欺诈编号
        if(riskLabelList.size()>0){
            for(int i = 0;i<riskLabelList.size(); i++){
                dataMap.put(riskLabelList.get(i),"1");
            }
            dataMap.put("riskLablList",riskLabelList.size());
        }
        if(parmMap.size()>0){
            if(parmMap.get("AF25")!=null){
                List AF25 = (List)parmMap.get("AF25");
                parmMap.put("AF25",AF25.get(0));
            }
            if(parmMap.get("AF26")!=null){
                List AF26 = (List)parmMap.get("AF26");
                parmMap.put("AF26",AF26.get(0));
            }
            if(parmMap.get("AF27")!=null){
                List AF27 = (List)parmMap.get("AF27");
                parmMap.put("AF27",AF27.get(0));
            }
            if(parmMap.get("AF28")!=null){
                List AF28 = (List)parmMap.get("AF28");
                parmMap.put("AF28",AF28.get(0));
            }
            if(parmMap.get("AF29")!=null){
                List AF29 = (List)parmMap.get("AF29");
                parmMap.put("AF29",AF29.get(0));
            }
            if(parmMap.get("AF30")!=null){
                List AF30 = (List)parmMap.get("AF30");
                parmMap.put("AF30",AF30.get(0));
            }
            dataMap.put("parmMap",parmMap);
        }
        return dataMap;
    }

    /**
     * @方法描述： 海卡蜜罐
     * @param model
     * @param returnId
     * @param threeParty
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author 仇招
     * @date 2018年10月10日 下午7:47:10
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/getMGResult")
    public Map<String,Object> getMGResult(Model model,String returnId,String threeParty,Integer id) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String,Object>();
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        apiReturnRecord.setReturnId(returnId);
        apiReturnRecord.setThreeParty(threeParty);
        apiReturnRecord.setId(id);
        apiReturnRecord = apiReturnRecordFeign.getById(apiReturnRecord);
        String httpStrs = apiReturnRecord.getReqPacketEncrypt();
        Map<String, Object> resultMap = new Gson().fromJson(httpStrs, Map.class);
        String code = (String) resultMap.get("code");
        String message = (String) resultMap.get("message");
        if (code == null || !"0".equals(code)) {
            dataMap.put("flag","error");
            dataMap.put("msg",message);
            return dataMap;
        }
        Map<String, Object> firstMap = (Map<String, Object>) resultMap.get("result");
        // 生成html
        ServletFreeMarker sfm = new ServletFreeMarker(request.getServletContext(), "/component/thirdservice/juxinli/ftl");
        httpStrs = sfm.generateHtml(firstMap, "honeypot.html");
        dataMap.put("httpStrs",httpStrs);
        dataMap.put("flag","success");
        dataMap.put("msg",message);
        return dataMap;
    }
    /**
     * @方法描述： 解析聚信立-蜜罐数据
     * @param model
     * @param returnId
     * @param threeParty
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author 仇招
     * @date 2018年10月10日 下午7:36:33
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/getJXLMGResultAjax")
    public Map<String,Object> getJXLMGResultAjax(Model model,String returnId,String threeParty,Integer id) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String,Object>();
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        apiReturnRecord.setReturnId(returnId);
        apiReturnRecord.setThreeParty(threeParty);
        apiReturnRecord.setId(id);
        apiReturnRecord = apiReturnRecordFeign.getById(apiReturnRecord);
        String httpStrs = apiReturnRecord.getReqPacketEncrypt();
        Map<String, Object> resultMap = new Gson().fromJson(httpStrs, Map.class);
        Map<String, Object> firstMap = (Map<String, Object>) resultMap.get("data");
        // 生成html
        ServletFreeMarker sfm = new ServletFreeMarker(request.getServletContext(), "/component/thirdservice/juxinli/ftl");
        httpStrs = sfm.generateHtml(firstMap, "honeypot.html");
        //创建pdf文件名
        String authCode= "showpdf";
        String rootPath=request.getServletContext().getRealPath("/component/thirdservice/juxinli");
        System.out.print("读取相对路径成功");
        //根据生成的html生成pdf
        PdfUtil.createAuthCodePdf(httpStrs,authCode,rootPath);
        dataMap.put("authCode",authCode);
        dataMap.put("httpStrs",httpStrs);
        dataMap.put("flag","success");
        return dataMap;
    }

    //生成天天有余pdf
    @RequestMapping(value = "/showTTYYpdf")
    @ResponseBody
    public Map<String,Object> showTTYYpdf(String ttyyHtml) throws Exception {
        Map<String, Object> dataMap = new HashMap<String,Object>();
        //创建pdf文件名
        String authCode= "showpdf";
        ttyyHtml = ttyyHtml.replace("&lt;","<" + "");
        ttyyHtml = ttyyHtml.replace("&gt;",">" + "");
        String rootPath=request.getServletContext().getRealPath("/component/thirdservice/tongdu");
        //根据生成的html生成pdf
        PdfUtil.createAuthCodePdf(ttyyHtml,authCode+"ttyy",rootPath);
        dataMap.put("authCode",authCode);
        return dataMap;
    }



    //读取pdf文件
    @RequestMapping(value = "/showpdf")
    @ResponseBody
    public Map<String,Object> showpdf(String authCode) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String,Object>();
        OutputStream out=null;
        InputStream in=null;
        try {
            String rootPath=request.getServletContext().getRealPath("/component/thirdservice/tongdu");
            File f = new File(rootPath+File.separator+File.separator+authCode+".pdf");
            if(f!=null && f.exists()){
                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Length",String.valueOf(f.length()));
                in = new FileInputStream(f);
                out = new BufferedOutputStream(response.getOutputStream());
                byte[] data = new byte[1024];
                int len = 0;
                while (-1 != (len=in.read(data, 0, data.length))) {
                    out.write(data, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag","error");
            dataMap.put("msg","查询出错");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataMap;
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/getFHResult")
    public Map<String,Object> getFHResult(Model model,String returnId,String threeParty,Integer id) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String,Object>();
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        apiReturnRecord.setReturnId(returnId);
        apiReturnRecord.setThreeParty(threeParty);
        apiReturnRecord.setId(id);
        apiReturnRecord = apiReturnRecordFeign.getById(apiReturnRecord);
        String httpStrs = apiReturnRecord.getReqPacketEncrypt();
        Map<String, Object> resultMap = new Gson().fromJson(httpStrs, Map.class);
        String code = (String) resultMap.get("code");
        String message = (String) resultMap.get("message");
        if (code == null || !"0".equals(code)) {
            dataMap.put("flag","errno");
            dataMap.put("msg",message);
            return dataMap;
        }
        Map<String, Object> firstMap = (Map<String, Object>) resultMap.get("result");
        // 生成html
        ServletFreeMarker sfm = new ServletFreeMarker(request.getServletContext(), "/component/thirdservice/juxinli/ftl");
        if("FH_ZX".equals(threeParty)){
            httpStrs = sfm.generateHtml(firstMap, "fahaizhixing.html");
        }else{
            httpStrs = sfm.generateHtml(firstMap, "fahaishixin.html");
        }
        dataMap.put("httpStrs",httpStrs);
        dataMap.put("flag","success");
        dataMap.put("msg",message);
        return dataMap;
    }
    /**
     * @方法描述： 手机号在网时长
     * @param model
     * @param returnId
     * @param threeParty
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author 仇招
     * @date 2018年10月1日 下午5:55:20
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/getBRDurationAjax")
    public Map<String,Object> getBRDurationAjax(Model model,String returnId,String threeParty,Integer id) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String,Object>();
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        apiReturnRecord.setReturnId(returnId);
        apiReturnRecord.setThreeParty(threeParty);
        apiReturnRecord.setId(id);
        apiReturnRecord = apiReturnRecordFeign.getById(apiReturnRecord);
        if(BizPubParm.YES_NO_N.equals(apiReturnRecord.getReqSts())){
            dataMap.put("flag","error");
            dataMap.put("msg",apiReturnRecord.getReqPacketEncrypt());
            return dataMap;
        }
        String httpStrs = apiReturnRecord.getReqPacketEncrypt();
        Map<String, Object> resultMap = new Gson().fromJson(httpStrs, Map.class);
        // 生成html
        ServletFreeMarker sfm = new ServletFreeMarker(request.getServletContext(), "/component/thirdservice/juxinli/ftl");
        httpStrs = sfm.generateHtml(resultMap, "duration.html");
        dataMap.put("httpStrs",httpStrs);
        dataMap.put("flag","success");
        return dataMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/inputTDResult")
    public String inputTDResult(Model model,String returnId,String threeParty,Integer id) throws Exception {
        ActionContext.initialize(request,response);
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        apiReturnRecord.setReturnId(returnId);
        apiReturnRecord.setThreeParty(threeParty);
        apiReturnRecord.setId(id);
        apiReturnRecord = apiReturnRecordFeign.getById(apiReturnRecord);
        String httpStrs = apiReturnRecord.getReqPacketEncrypt();
        Map<String, Object> resultMap = new Gson().fromJson(httpStrs, Map.class);
        String oriData = (String) resultMap.get("oriData");
        Map<String,Object> oriDataMap = new Gson().fromJson(oriData,Map.class);
        List<Object> list = new ArrayList();
        list.add(oriDataMap);
        Gson gson = new Gson();
        String td_data = gson.toJson(list);
        model.addAttribute("td_data",td_data);
        model.addAttribute("flag", "success");
        return "/component/app/MfBusApply_RiskManagementTDResult";
    }
    /**
     * @方法描述： 解析百融数据
     * @param model
     * @param orderNo
     * @return
     * @throws Exception
     * String
     * @author 仇招
     * @date 2018年9月30日 下午8:15:39
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/inputBRResult")
    public String inputBRResult(Model model,String orderNo) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> tokenMap = autoApproveInterfaceFeign.getBrReportToken();
        Map<String,Object> resultMap = (Map<String,Object>)tokenMap.get("data");
        model.addAttribute("swiftNumber", orderNo);
        model.addAttribute("token", resultMap.get("token"));
        return "/component/app/MfBusApply_RiskManagementBRResult";
    }
    /**
     * @方法描述： 查询三方数据
     * @param model
     * @param cusNo
     * @param appId
     * @param type
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author 仇招
     * @date 2018年10月9日 下午5:29:25
     */
    @ResponseBody
    @RequestMapping(value = "/getThirdAjax")
    public  Map<String, Object> getThirdAjax(Model model, String cusNo, String cusname,String appId, String type,String ajaxData,String cusnexus,String becusno,String contactsTel) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, String> paramMap = new HashMap<String,String>();
        Map<String, String> map = new HashMap<String,String>();
        Map<String, Object> dataMap = new HashMap<String,Object>();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        CodeUtils cu = new CodeUtils();
        Map<String, String> strategyIdMap = cu.getMapByKeyName("STRATEGY_ID");
        // bizCode自定义
        Map<String,String> bizCodeMap = cu.getMapByKeyName("BIZ_CODE_TD");
        String strategyId = strategyIdMap.get(BizPubParm.STRATEGY_ID_1);
        String cusTel = mfCusCustomer.getCusTel();
        String cusName = mfCusCustomer.getCusName();
        String idNum = mfCusCustomer.getIdNum();
        switch(type){
            case BizPubParm.THREE_PARTY_TD_DATA://同盾
                map.put("cusNo", cusNo);
                map.put("id_number", idNum);
                map.put("account_name", cusName);
                map.put("account_mobile", cusTel);
                map.put("target", type);
                map.put("biz_code",bizCodeMap.get("BIZ_CODE"));
                MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
                mfCusFamilyInfo.setCusNo(cusNo);
                mfCusFamilyInfo.setRelative("1");
                mfCusFamilyInfo.setDelFlag(BizPubParm.YES_NO_N);
                List<MfCusFamilyInfo> mfCusFamilyInfoList = cusInterfaceFeign.getFamilyList(mfCusFamilyInfo);
                if (mfCusFamilyInfoList != null && mfCusFamilyInfoList.size() > 0){
                    map.put("contact1_id_number",mfCusFamilyInfoList.get(0).getIdNum());
                    map.put("contact1_mobile",mfCusFamilyInfoList.get(0).getRelTel());
                    map.put("contact1_name",mfCusFamilyInfoList.get(0).getRelName());
                    map.put("contact1_relation","spouse");
                }
                dataMap = apiReturnRecordFeign.getThirdSelect(map);
                break;

            case BizPubParm.THREE_PARTY_BR_DURATION://百融手机号在网时长
                map = new HashMap<String, String>();
                map.put("cusNo", cusNo);
                map.put("mobile", cusTel);
                dataMap = autoApproveInterfaceFeign.getBRDurationData(map);
                break;
            case BizPubParm.THREE_PARTY_BR_PRE_LOAN1:
                map = new HashMap<String, String>();
                map.put("cusNo", cusNo);
                map.put("mobile", cusTel);
                map.put("idCardName", cusName);
                map.put("idCardNum", idNum);
                map.put("linkMobile", null);
                map.put("timeRange", null);
                strategyId = strategyIdMap.get(BizPubParm.STRATEGY_ID_1);
                map.put("strategyId", strategyId);
                map.put("strategyIdFlag", BizPubParm.STRATEGY_ID_1);
                dataMap = autoApproveInterfaceFeign.getBRPreLoanData(map);
                break;
            case BizPubParm.THREE_PARTY_BR_PRE_LOAN2:
                map = new HashMap<String, String>();
                //3共借人客户id换成社会关系人id 2保证人类型时存储三方关联id
                if(!"".equals(cusnexus)){
                    if(!"1".equals(cusnexus)){
                        map.put("void",becusno);
                    }
                    map.put("cusname",cusname);
                    map.put("cusNo", cusNo);
                    map.put("mobile", contactsTel);
                    map.put("idCardName", cusname);
                    map.put("idCardNum", becusno);
                    map.put("linkMobile", null);
                    map.put("timeRange", null);
                }else{
                    map.put("cusname",cusname);
                    map.put("cusNo", cusNo);
                    map.put("mobile", cusTel);
                    map.put("idCardName", cusName);
                    map.put("idCardNum", idNum);
                    map.put("linkMobile", null);
                    map.put("timeRange", null);
                }
                strategyId = strategyIdMap.get(BizPubParm.STRATEGY_ID_2);
                map.put("strategyId", strategyId);
                map.put("strategyIdFlag", BizPubParm.STRATEGY_ID_2);
                dataMap = autoApproveInterfaceFeign.getBRPreLoanData(map);
                break;
            case BizPubParm.THREE_PARTY_BR_DATA://百融-贷前管理
                map = new HashMap<String, String>();
                //3共借人客户id换成社会关系人id 2保证人类型时存储三方关联id
                if(!"".equals(cusnexus)){
                    if(!"1".equals(cusnexus)){
                        map.put("void",becusno);
                    }
                    map.put("cusname",cusname);
                    map.put("cusNo", cusNo);
                    map.put("mobile", contactsTel);
                    map.put("idCardName", cusname);
                    map.put("idCardNum", becusno);
                    map.put("linkMobile", null);
                    map.put("timeRange", null);
                }else{
                    map.put("cusname",cusname);
                    map.put("cusNo", cusNo);
                    map.put("mobile", cusTel);
                    map.put("idCardName", cusName);
                    map.put("idCardNum", idNum);
                    map.put("linkMobile", null);
                    map.put("timeRange", null);
                }
                strategyId = strategyIdMap.get(BizPubParm.STRATEGY_ID_3);
                map.put("strategyId", strategyId);
                map.put("strategyIdFlag", BizPubParm.STRATEGY_ID_3);
                dataMap = autoApproveInterfaceFeign.getBRPreLoanData(map);
                break;
            case BizPubParm.THREE_PARTY_JXL_MG://聚信立-蜜罐
                map = new HashMap<String, String>();
                //3共借人客户id换成社会关系人id 2保证人类型时存储三方关联id
                if(!"".equals(cusnexus)){
                    if(!"1".equals(cusnexus)){
                        map.put("void",becusno);
                    }
                    map.put("cusname",cusname);
                    map.put("cusNo", cusNo);
                    map.put("phone", contactsTel);
                    map.put("name", cusname);
                    map.put("id_card", becusno);
                }else{
                    map.put("cusname",cusname);
                    map.put("cusNo", cusNo);
                    map.put("phone", cusTel);
                    map.put("name", cusName);
                    map.put("id_card", idNum);
                }
                dataMap = autoApproveInterfaceFeign.getJxlMgData(map);
                break;
            case BizPubParm.THREE_PARTY_TTYY://天天有余
                Map<String, Object> ajaxMap= getMapByJson(ajaxData);
                map = new HashMap<String, String>();
                map.put("cusNo", ajaxMap.get("cusNo").toString());
                map.put("appId", appId);
                map.put("idCardName", ajaxMap.get("cusName").toString());
                map.put("idCardNum", ajaxMap.get("idNum").toString());
                map.put("bankCardNum", ajaxMap.get("ext1").toString());
                map.put("bankPreMobile", ajaxMap.get("cusTel").toString());
                dataMap = autoApproveInterfaceFeign.getTTYYData(map);
                break;
            default:
                paramMap.put("cusNo", cusNo);
                paramMap.put("name", cusName);
                paramMap.put("idcard", idNum);
                paramMap.put("phone", cusTel);
                paramMap.put("pbtype", "DKSP");
                if(mfBusApply != null){
                    paramMap.put("proid", mfBusApply.getKindName());
                    paramMap.put("partner", mfBusApply.getKindName());
                }
                paramMap.put("target", type);
                paramMap.put("custType", "GR");
                dataMap = apiReturnRecordFeign.getThirdSelect(paramMap);
                break;
        }
        String flag = (String)dataMap.get("flag");
        if(flag != null && ("success".equals(flag) || "noData".equals(flag))){
            dataMap.put("flag","success");
            dataMap.put("msg",dataMap.get("msg"));
        }else {
            dataMap.put("flag","error");
            dataMap.put("msg",MessageEnum.ERROR_SELECT.getMessage());

        }
        return dataMap;
    }


    /**
     * AJAX根据客户号获取三方查询记录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getApiReturnRecordByCusNoAjax")
    @ResponseBody
    public Map<String, Object> getApiListByCusNoAjax(String cusNo,String threeParty,Integer id) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
            apiReturnRecord.setReturnId(cusNo);
            apiReturnRecord.setThreeParty(threeParty);
            apiReturnRecord.setId(id);
            ApiReturnRecord mfApiReturnRecord =apiReturnRecordFeign.getById(apiReturnRecord);
            if(mfApiReturnRecord!=null){
                dataMap.put("flag", "success");
            }else{
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg","根据客户号获取三方查询记录失败！");
            throw e;
        }
        return dataMap;
    }
    /**
     * @方法描述： 蜜风数据查询页面
     * @param model
     * @param returnId
     * @param threeParty
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author ywh
     * @date 2018年10月18日 下午7:36:33
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getJXLMFResultAjax")
    public String getJXLMFResultAjax(Model mode,String cusname,String cusnexus,String becusno,String contactsTel) throws Exception {
        ActionContext.initialize(request,response);
        try {
            String cusNo = request.getParameter("cusNo");
            mode.addAttribute("cusNo",cusNo);
            if(!"".equals(cusnexus)){
                MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
                mfCusPersBaseInfo.setCusTel(contactsTel);
                //mfCusPersBaseInfo.setCommAddress();
                mfCusPersBaseInfo.setIdNum(becusno);
                mfCusPersBaseInfo.setCusName(cusname);
                mode.addAttribute("mfCusPersBaseInfo",mfCusPersBaseInfo);
            }else if (StringUtil.isNotEmpty(cusNo)){
                MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
                mfCusPersBaseInfo.setCusNo(cusNo);
                mfCusPersBaseInfo = cusInterfaceFeign.getByCusNo(mfCusPersBaseInfo);
                mode.addAttribute("mfCusPersBaseInfo",mfCusPersBaseInfo);
            }else {
            }
            boolean flag = false;
            List<Map<String, Object>> sources = null;
            double width = 0.00;
            int count = 0;
            String url;
            String result;
            Map<String, Object> dataMap = autoApproveInterfaceFeign.getMFDataSource();
            sources = (List<Map<String, Object>>) dataMap.get("data");
            flag = (boolean) dataMap.get("flag");
            if (sources == null || sources.size() == 0) {
                width = 0.50;
            } else {
                int sz = sources.size();
                width = 1.0 / (2 + sz);
                count = 1;
            }
            mode.addAttribute("width", width * 100);
            mode.addAttribute("sources", sources);
            mode.addAttribute("flag", flag);
            mode.addAttribute("count", count);
            mode.addAttribute("becusno", becusno);
            mode.addAttribute("cusnexus", cusnexus);
            mode.addAttribute("cusname", cusname);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/component/thirdservice/juxinli/ThirdJxlBeeRecord_input";
    }
    /**
     * @方法描述： 蜜蜂数据提交申请表单获取回执信息
     * @param model
     * @param returnId
     * @param threeParty
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author ywh
     * @date 2018年10月18日 下午7:36:33
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/honeybeeApplicationsAjax")
    @ResponseBody
    public Map<String, Object> honeybeeApplicationsAjax(Model mode,String ajaxData,String cusname,String cusnexus,String becusno) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            JSONObject paramMap = JSONObject.fromObject(ajaxData);
            // 参数验证
            Map<String, String> param = new HashMap<String, String>();
            param.put("name",(String) paramMap.get("name"));
            param.put("id_card_num",(String)paramMap.get("idcard"));
            param.put("cell_phone_num",(String)paramMap.get("phone"));
            param.put("home_addr",(String)paramMap.get("homeProvince")+ (String)paramMap.get("homeCity")
                    + (String)paramMap.get("homeAddr"));
            param.put("work_addr",(String)paramMap.get("workProvince") + (String)paramMap.get("workCity")
                    +  (String)paramMap.get("workAddr"));
            param.put("contact_tel", getStringArrBySplit("@", JSONArray.fromObject(paramMap.get("contactTel"))));
            param.put("contact_name",
                    getStringArrBySplit("@", JSONArray.fromObject(paramMap.get("contactName"))));
            param.put("contact_type",
                    getStringArrBySplit("@", JSONArray.fromObject(paramMap.get("contactType"))));
            param.put("website", this.getStringArrBySplit("@", JSONArray.fromObject(paramMap.get("website"))));
            param.put("category", this.getStringArrBySplit("@", JSONArray.fromObject(paramMap.get("category"))));
            param.put("skip_mobile", (String) paramMap.get("skip"));
            param.put("cusNo",paramMap.getString("cusNo"));

            //3共借人客户id换成社会关系人id 2保证人类型时存储三方关联id
            if(!"".equals(cusnexus)){
                param.put("cusname",cusname);
                if(!"1".equals(cusnexus)){
                    param.put("void",becusno);
                }
            }
            String url;

            Map<String, Object> resultMap = autoApproveInterfaceFeign.honeybeeApplications(param);
            Map<String, Object> dMap = (Map<String, Object>) resultMap
                    .get("data");
            if (dMap!=null){
                double code = (Double) dMap.get("code");
                int intcode = (int) code;
                boolean success = (boolean) dMap.get("success");
                String message = (String) dMap.get("message");
                Map<String, Object> map = (Map<String, Object>) dMap.get("data");
                dataMap.put("code", code);
                dataMap.put("success", success);
                dataMap.put("msg", message);
                dataMap.put("result", map);
                dataMap.put("flg", "success");
            }else{
                dataMap.put("success", "error");
                dataMap.put("msg", resultMap.get("msg"));
            }


            ;

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flg", "error");
            dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
        }
        return dataMap;
    }
    private String getStringArrBySplit(String split, JSONArray arr) {
        if (arr == null || arr.size() == 0) {
            return null;
        }
        if (split == null) {
            return arr.toString();
        } else {
            StringBuffer sb = new StringBuffer();
            int len = arr.size();
            for (int i = 0; i < len; i++) {
                if (i != len - 1) {
                    sb.append(arr.get(i).toString());
                    sb.append(split);
                } else {
                    sb.append(arr.get(i).toString());
                }
            }
            return sb.toString();
        }
    }
    /**
     * 通过Json串转成Map
     * @Title: getMapByJson
     * @param @param ajaxData
     * @return Map    返回类型
     * @throws
     */
    @Override
    public Map<String, Object> getMapByJson(String ajaxData){
        Map<String, Object> map = new HashMap<String,Object>();
        JSONArray ja = JSONArray.fromObject(ajaxData);
        //int i =0 ;
        for(Object obj:ja){
            JSONObject jb = JSONObject.fromObject(obj);
            String key = jb.getString("name");
            String value = jb.getString("value");
            if(!"null".equals(map.get(key))&&map.get(key)!=null){
                String []array = {};
                if(map.get(key) instanceof String[]){
                    array = (String [])ArrayUtils.add((String[])map.get(key),value);
                }else{
                    array = (String [])ArrayUtils.add(array,map.get(key));
                    array = (String [])ArrayUtils.add(array,value);
                }
                map.put(key, array);
            }else{
                map.put(key, value);
            }
        }
        return map;
    }
    /**
     * 数据采集
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/honeybeeCollectAjax")
    @ResponseBody
    public  Map<String, Object> honeybeeCollectAjax(String token,String skip,String account,String password,String collectWebsite,String captcha,String queryPwd,String type,String method,String cusNo) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            // 参数验证
            Map<String, String> param = new HashMap<String, String>();
            param.put("token", token);
            if ("true".equals(skip)) {// 调用跳过
                Map<String, Object> resultMap = autoApproveInterfaceFeign.skip(param);
                Map<String, Object> dMap = (Map<String, Object>) resultMap.get("data");
                boolean success = (boolean) dMap.get("success");// 是否成功
                Map<String, Object> secondMap = (Map<String, Object>) dMap.get("data");
                type = (String) secondMap.get("type");// ERROR:错误类型的响应结果,CONTROL控制类型的响应结果,RUNNING
                // 正在运行（超时会出现）
                String content = (String) secondMap.get("content");// 信息
                Object process_code = secondMap.get("process_code");//
                boolean finish = (boolean) secondMap.get("finish");// 所有的采集流程是否结束
                Map<String, Object> nextMap = (Map<String, Object>) secondMap
                        .get("next_datasource");// 下一个需要采集的数据源
                Map<String, Object> reqMsgTplMap = (Map<String, Object>) secondMap
                        .get("req_msg_tpl");// type
                dataMap.put("flg", "success");
                dataMap.put("success", success);
                dataMap.put("type", type);
                dataMap.put("msg", content);
                dataMap.put("process_code", process_code);
                dataMap.put("finish", finish);
                dataMap.put("nextMap", nextMap);
                dataMap.put("reqMsgTplMap", reqMsgTplMap);

            } else {
                param.put("account", account);
                param.put("password", password);
                param.put("website", collectWebsite);
                param.put("captcha", captcha);
                param.put("queryPwd", queryPwd);
                param.put("type", type);
                param.put("method", method);
                param.put("cusNo",cusNo);
                Map<String, Object> resultMap = autoApproveInterfaceFeign.collect(param);
                Map<String, Object> dMap = (Map<String, Object>) resultMap.get("data");
                boolean success = (boolean) dMap.get("success");// 是否成功
                Map<String, Object> secondMap = (Map<String, Object>) dMap.get("data");
                type = (String) secondMap.get("type");// ERROR:错误类型的响应结果,CONTROL控制类型的响应结果,RUNNING
                // 正在运行（超时会出现）
                String content = (String) secondMap.get("content");// 信息
                double process_code = (double) secondMap.get("process_code");//
                int intProcessCcode = (int) process_code;
                boolean finish = (boolean) secondMap.get("finish");// 所有的采集流程是否结束
                Map<String, Object> nextMap = (Map<String, Object>) secondMap
                        .get("next_datasource");// 下一个需要采集的数据源
                Map<String, Object> reqMsgTplMap = (Map<String, Object>) secondMap
                        .get("req_msg_tpl");// type
                dataMap.put("flg", "success");
                dataMap.put("success", success);
                dataMap.put("type", type);
                dataMap.put("msg", content);
                dataMap.put("process_code", process_code);
                dataMap.put("finish", finish);
                dataMap.put("nextMap", nextMap);
                dataMap.put("reqMsgTplMap", reqMsgTplMap);
            }
        } catch (Exception e) {
            dataMap.put("flg", "error");
            dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
            e.printStackTrace();
            throw e;
        }
        return dataMap;
    }
    /**
     * 聚信力-蜜蜂查看报告状态
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/lookpreAjax")
    @ResponseBody
    public Map<String, Object> lookpreAjax(String token,String cusNo) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        apiReturnRecord.setToken(token);
        try {
            apiReturnRecord = autoApproveInterfaceFeign.getApiReturnRecordByToken(apiReturnRecord);
            if (apiReturnRecord == null) {
                dataMap.put("flag", "error");
                dataMap.put("msg", "报告不存在");
            }
            String httpStrs = apiReturnRecord.getReqPacketEncrypt();
            if ("4".equals(apiReturnRecord.getBeeStatus())) {// 报告已成功
                dataMap.put("flag", "success");
                dataMap.put("msg", "成功");
                Map<String, Object> resultMap = new Gson().fromJson(httpStrs, Map.class);
                Map<String, Object> firstMap = (Map<String, Object>) resultMap.get("report_data");
                // 生成html
                ServletFreeMarker sfm = new ServletFreeMarker(request.getServletContext(), "/component/thirdservice/juxinli/ftl");
                httpStrs = sfm.generateHtml(firstMap, "honeybee.html");
                //创建pdf文件名
                String authCode= "beepdf";
                String rootPath=request.getServletContext().getRealPath("/component/thirdservice/juxinli");
                System.out.print("读取相对路径成功");
                //根据生成的html生成pdf
                PdfUtil.createAuthCodebeePdf(httpStrs,authCode,rootPath);
                dataMap.put("authCode",authCode);
                dataMap.put("httpStrs",httpStrs);
            } else if ("0".equals(apiReturnRecord.getBeeStatus())// 以获取token
                    || "1".equals(apiReturnRecord.getBeeStatus())// 开始采集 运营商
                    || "2".equals(apiReturnRecord.getBeeStatus())// 开始采集 电商
                    || "3".equals(apiReturnRecord.getBeeStatus())) {// 流程结束
                // 请求第三方
                Map<String, String> param = new HashMap<String, String>();
                param.put("token", apiReturnRecord.getToken());
                param.put("cusNo", cusNo);
                dataMap =autoApproveInterfaceFeign.getReportSts(param);
                if(dataMap!=null){
                    String flag = (String) dataMap.get("flag");
                    if ("success".equals(flag)){
                        dataMap = autoApproveInterfaceFeign.getReport(param);
                        Map<String, Object> firstMap = (Map<String, Object>) dataMap.get("data");
                        Map<String, Object> secondMap = (Map<String, Object>) firstMap.get("report_data");
                        // 生成html
                        ServletFreeMarker sfm = new ServletFreeMarker(request.getServletContext(), "/component/thirdservice/juxinli/ftl");
                        httpStrs = sfm.generateHtml(secondMap, "honeybee.html");
                        dataMap.put("httpStrs",httpStrs);
                    }
                }
            }else {
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }
        return dataMap;
    }
    /**
     * 查看报告
     *
     * @return
     * @throws Exception
     */
    public Map<String, Object> reportAjax(String token) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        apiReturnRecord.setToken(token);
        if (apiReturnRecord == null) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "报告不存在");
        } else {

        }
        return dataMap;

    }

    /**
     * @方法描述： 打开天天有余页面
     * @param model
     * @param cusNo
     * @return
     * @throws Exception
     * String
     * @author 段泽宇
     * @date 2018年10月18日 上午11:40:39
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getYouYuAjax")
    public String getYouYuAjax(Model model,String cusNo,String type) throws Exception {
        ActionContext.initialize(request,response);
        FormService formService = new FormService();
        FormData formyouyuriskManagement = formService.getFormData("youyuriskManagement");
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        mfCusBankAccManage.setCusNo(cusNo);
        mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
        if(mfCusBankAccManage != null){
            mfCusCustomer.setExt1(mfCusBankAccManage.getAccountNo());
        }
        getObjValue(formyouyuriskManagement,mfCusCustomer);
        model.addAttribute("formyouyuriskManagement",formyouyuriskManagement);
        model.addAttribute("type",type);
        model.addAttribute("cusNo",cusNo);
        model.addAttribute("query", "");
        return "/component/app/MfBusApply_RiskManagementYYResult";
    }
    /**
     * @方法描述： 打开风控核查页面
     * @param model
     * @param cusNo
     * @return
     * @throws Exception
     * @author 陈迪
     * @date 2020年01月04日 下午5:20:00
     */
    @RequestMapping(value = "/openRiskVerification")
    public String openRiskVerification(Model model, String cusNo) throws Exception{
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo", cusNo);
        MfCusHighInfo mfCusHighInfo=new MfCusHighInfo();
            // 自定义查询Bo方法
        mfCusHighInfo.setCusNo(cusNo);
        List<MfCusHighInfo> corpPartyList = mfCusHighInfoFeign.getCorpPartyList(mfCusHighInfo);
        Map<String, String> cuMap = new CodeUtils().getMapByKeyName("HIGH_TYPE");
        for (int i=0;i<corpPartyList.size();i++){
            if("50".equals(corpPartyList.get(i).getHighCusType())){
                corpPartyList.get(i).setHighCusType("授信反担保人");
            }else if("51".equals(corpPartyList.get(i).getHighCusType())){
                corpPartyList.get(i).setHighCusType("业务反担保人");
            }else{
                corpPartyList.get(i).setHighCusType(cuMap.get(corpPartyList.get(i).getHighCusType()));
            }
        }
        model.addAttribute("corpPartyList",corpPartyList);
        List<ApiReturnRecord> apiReturnRecordList = apiReturnRecordFeign.getRiskOutcomeList(cusNo);
        model.addAttribute("riskVerificationList",apiReturnRecordList);
        return "/component/cus/MfCus_RiskManagement";
    }

    /**
     * @方法描述： 查询关系人列表
     * @param ajaxData
     * @param cusNo
     * @return
     * @throws Exception
     * @author 陈迪
     * @date 2020年01月04日 下午5:20:00
     */
    @RequestMapping(value = "/getCorpPartyList")
    @ResponseBody
    public Map<String, Object> getCorpPartyList(String ajaxData, Integer pageSize, Integer pageNo, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusHighInfo mfCusHighInfo=new MfCusHighInfo();
        try {
            // 自定义查询Bo方法
            mfCusHighInfo.setCusNo(cusNo);
            List<MfCusHighInfo> corpPartyList = mfCusHighInfoFeign.getCorpPartyList(mfCusHighInfo);
            Map<String, String> cuMap = new CodeUtils().getMapByKeyName("HIGH_TYPE");
            for (int i=0;i<corpPartyList.size();i++){
                if("50".equals(corpPartyList.get(i).getHighCusType())){
                    corpPartyList.get(i).setHighCusType("授信反担保人");
                }else if("51".equals(corpPartyList.get(i).getHighCusType())){
                    corpPartyList.get(i).setHighCusType("业务反担保人");
                }else{
                    corpPartyList.get(i).setHighCusType(cuMap.get(corpPartyList.get(i).getHighCusType()));
                }
            }
            String tableHtml = "";
            JsonTableUtil jtu = new JsonTableUtil();
            tableHtml = jtu.getJsonStr("tablecorpPartyList", "tableTag",corpPartyList, null, true);
            dataMap.put("flag", "success");
            dataMap.put("tableHtml", tableHtml);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }



    /**
     * @方法描述： 查询风控核查结果列表
     * @param ajaxData
     * @param cusNo
     * @return
     * @throws Exception
     * @author 陈迪
     * @date 2020年01月04日 下午5:20:00
     */
    @RequestMapping(value = "/getRiskOutcomeList")
    @ResponseBody
    public Map<String, Object> getRiskOutcomeList(String ajaxData, Integer pageSize, Integer pageNo, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        try {
            // 自定义查询Bo方法
            List<ApiReturnRecord> apiReturnRecordList = apiReturnRecordFeign.getRiskOutcomeList(cusNo);
            String tableHtml = "";
            JsonTableUtil jtu = new JsonTableUtil();
            tableHtml = jtu.getJsonStr("tableriskVerificationList", "tableTag",apiReturnRecordList, null, true);
            dataMap.put("flag", "success");
            dataMap.put("tableHtml", tableHtml);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * @方法描述： 同盾风控核查
     * @param partyName
     * @param partyTel
     * @param partyIdNum
     * @param cusNo
     * @return
     * @throws Exception
     * @author 陈迪
     * @date 2020年01月04日 下午5:20:00
     */
    @RequestMapping(value = "/getTongdunCheck")
    @ResponseBody
    public Map<String, Object> getTongdunCheck(String partyName,String partyTel,String partyIdNum, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> map = new HashMap<>();
        map.put("partyName", partyName);
        map.put("partyTel", partyTel);
        map.put("partyIdNum", partyIdNum);
        map.put("cusNo", cusNo);
        dataMap = autoApproveInterfaceFeign.tongdunCheck(map);
        return dataMap;
    }


    /**
     * @方法描述： 解析风控核查结果信息
     * @param returnId
     * @param id
     * @return
     * @throws Exception
     * @author 陈迪
     * @date 2020年01月04日 下午5:20:00
     */
    @RequestMapping(value = "/openCheckReport")
    public String openCheckReport(Model model,String returnId,Integer id) throws Exception {
        ActionContext.initialize(request,response);
        ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
        apiReturnRecord.setReturnId(returnId);
        apiReturnRecord.setId(id);
        apiReturnRecord = apiReturnRecordFeign.getById(apiReturnRecord);
        if(null !=apiReturnRecord || BizPubParm.YES_NO_N.equals(apiReturnRecord.getReqSts())){
            Gson gson = new Gson();
            JSONObject data= JSONObject.fromObject(apiReturnRecord.getReqPacketEncrypt());
            JSONObject resultDesc= JSONObject.fromObject(data.get("result_desc"));
            JSONObject antifraudMap= JSONObject.fromObject(resultDesc.get("ANTIFRAUD"));
            model.addAttribute("finalDecision",antifraudMap.get("final_decision"));
            model.addAttribute("finalScore",antifraudMap.get("final_score"));
            model.addAttribute("riskItemsList",antifraudMap.get("risk_items"));
            //同盾查询条件
            String reqParam = apiReturnRecord.getReqParam();
            Map<String, String> requestParaMap = gson.fromJson(reqParam, new TypeToken<Map<String,String>>(){}.getType());
            model.addAttribute("requestParaMap",requestParaMap);
            model.addAttribute("message","查询成功");
        }else {
            model.addAttribute("message","查询解析失败");
        }
        return "/component/cus/MfCus_RiskManagementTDHtml";
    }

}