package app.component.archives.controller;

import app.base.User;
import app.base.cacheinterface.BusiCacheInterface;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.archives.entity.ArchiveInfoMain;
import app.component.archives.entity.ArchiveInfoTransfer;
import app.component.archives.entity.ArchiveInfoVoucherReturn;
import app.component.archives.feign.ArchiveInfoMainFeign;
import app.component.archives.feign.ArchiveInfoTransferFeign;
import app.component.archives.feign.ArchiveInfoVoucherReturnFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.collateral.entity.CertiInfo;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.CertiInfoFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.BizPubParm;
import app.component.doc.entity.DocManage;
import app.component.doc.feign.DocManageFeign;
import app.component.nmd.entity.ParLoanuse;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.stamp.entity.MfStampCredit;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
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
@RequestMapping("/archiveInfoVoucherReturn")
public class ArchiveInfoVoucherReturnController  extends BaseFormBean {
    //注入业务层
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ArchiveInfoMainFeign archiveInfoMainFeign;
    @Autowired
    private ArchiveInfoVoucherReturnFeign archiveInfoVoucherReturnFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @Autowired
    private MfCusCreditApplyFeign mfCusCreditApplyFeign;
    @Autowired
    private BusiCacheInterface busiCacheInterface;
    @Autowired
    private DocManageFeign docManageFeign;
    @Autowired
    private PledgeBaseInfoFeign pledgeBaseInfoFeign;
    @Autowired
    private CertiInfoFeign certiInfoFeign;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;

    /**
     * 列表有翻页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request,response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray stateJsonArray = codeUtils.getJSONArrayByKeyName("RETURN_STATE");
        this.getHttpRequest().setAttribute("stateJsonArray", stateJsonArray);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoVoucherReturn_List";
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
        JSONArray stateJsonArray = codeUtils.getJSONArrayByKeyName("RETURN_STATE");
        this.getHttpRequest().setAttribute("stateJsonArray", stateJsonArray);
        model.addAttribute("query", "");
        return "component/archives/ArchiveInfoVoucherReturn_ConfimList";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, String returnState) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
        try {
            archiveInfoVoucherReturn.setCustomQuery(ajaxData);//自定义查询参数赋值
            archiveInfoVoucherReturn.setCustomSorts(ajaxData);//自定义排序参数赋值
            archiveInfoVoucherReturn.setCriteriaList(archiveInfoVoucherReturn, ajaxData);//我的筛选
            if(returnState != null && !"".equals(returnState)){
                archiveInfoVoucherReturn.setReturnState(returnState);
            }
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage.setParams(this.setIpageParams("archiveInfoVoucherReturn", archiveInfoVoucherReturn));
            //自定义查询Bo方法
            ipage = archiveInfoVoucherReturnFeign.findByPage(ipage);
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
     * 凭证退还申请
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchivevoucherinsert = null;
        ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
        //根据申请人是否为资产保全部来判断，是正常凭证退还，还是凭证处置
        String brNo = User.getOrgNo(request);
        CodeUtils cu = new CodeUtils();
        Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
        String zichanbaoquan = constantMap.get("zichanbaoquan");
        if(brNo.contains(zichanbaoquan)){
            formarchivevoucherinsert = formService.getFormData("archivevoucherinsert_deal");
            archiveInfoVoucherReturn.setDealMode("02");//凭证处置
        }else{
            formarchivevoucherinsert = formService.getFormData("archivevoucherinsert");
            archiveInfoVoucherReturn.setDealMode("01");//正常凭证退还
        }
        //更新信息
        String id = WaterIdUtil.getWaterId();
        archiveInfoVoucherReturn.setId(id);
        archiveInfoVoucherReturn.setOpNo(User.getRegNo(request));
        archiveInfoVoucherReturn.setOpName(User.getRegName(request));
        archiveInfoVoucherReturn.setBrNo(User.getOrgNo(request));
        archiveInfoVoucherReturn.setBrName(User.getRegName(request));
        archiveInfoVoucherReturn.setReturnDate(DateUtil.getDate());
        archiveInfoVoucherReturn.setReturnNo(User.getRegNo(request));
        archiveInfoVoucherReturn.setReturnName(User.getRegName(request));

        getObjValue(formarchivevoucherinsert, archiveInfoVoucherReturn);
        model.addAttribute("formarchivevoucherinsert", formarchivevoucherinsert);
        model.addAttribute("query", "");
        model.addAttribute("id", id);
        return "/component/archives/ArchiveInfoVoucherReturn_Insert";
    }

    /**
     * 凭证退还--授信
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputForCredit")
    public String inputForCredit(Model model,String creditAppId,String docNo, String docName,String archiveMainNo, String archiveDetailNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchivevoucherinsert = null;
        ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
        //根据申请人是否为资产保全部来判断，是正常凭证退还，还是凭证处置
        String brNo = User.getOrgNo(request);
        CodeUtils cu = new CodeUtils();
        Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
        String zichanbaoquan = constantMap.get("zichanbaoquan");
        if(brNo.contains(zichanbaoquan)){
            formarchivevoucherinsert = formService.getFormData("archivevoucherinsert_deal");
            archiveInfoVoucherReturn.setDealMode("02");//凭证处置
        }else{
            formarchivevoucherinsert = formService.getFormData("archivevouchercreditinsert");
            archiveInfoVoucherReturn.setDealMode("01");//正常凭证退还
        }

        //更新信息
        MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
        mfCusCreditApply.setCreditAppId(creditAppId);
        mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
        String id = WaterIdUtil.getWaterId();
        archiveInfoVoucherReturn.setId(id);
        archiveInfoVoucherReturn.setCreditAppId(creditAppId);
        archiveInfoVoucherReturn.setCusNo(mfCusCreditApply.getCusNo());
        archiveInfoVoucherReturn.setCusName(mfCusCreditApply.getCusName());
        archiveInfoVoucherReturn.setArchivePactStatus("01");//授信
        archiveInfoVoucherReturn.setCreditAppNo(mfCusCreditApply.getCreditAppNo());
        archiveInfoVoucherReturn.setDocNo(docNo);
        archiveInfoVoucherReturn.setDocName(docName);
        archiveInfoVoucherReturn.setArchiveMainNo(archiveMainNo);
        archiveInfoVoucherReturn.setArchiveDetailNo(archiveDetailNo);
        archiveInfoVoucherReturn.setOpNo(User.getRegNo(request));
        archiveInfoVoucherReturn.setOpName(User.getRegName(request));
        archiveInfoVoucherReturn.setBrNo(User.getOrgNo(request));
        archiveInfoVoucherReturn.setBrName(User.getRegName(request));
        archiveInfoVoucherReturn.setReturnDate(DateUtil.getDate());
        archiveInfoVoucherReturn.setReturnNo(User.getRegNo(request));
        archiveInfoVoucherReturn.setReturnName(User.getRegName(request));
        getObjValue(formarchivevoucherinsert, archiveInfoVoucherReturn);
        model.addAttribute("formarchivevoucherinsert", formarchivevoucherinsert);

        //授信项下业务
        List<ArchiveInfoMain> archiveInfoMainList = new ArrayList<ArchiveInfoMain>();
        ArchiveInfoMain archiveInfoMainQuery = new ArchiveInfoMain();
        archiveInfoMainQuery.setCreditAppId(creditAppId);
        archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMainQuery);
        List<MfBusApply> mfBusApplyList = new ArrayList<MfBusApply>();
        String appIds = "";
        for(ArchiveInfoMain main : archiveInfoMainList){
            if(StringUtil.isNotEmpty(main.getAppId())){
                if(!appIds.contains(main.getAppId())){
                    appIds += main.getAppId() + ",";
                    MfBusApply mfBusApply = new MfBusApply();
                    mfBusApply.setAppId(main.getAppId());
                    mfBusApply = mfBusApplyFeign.getById(mfBusApply);
                    if(mfBusApply != null){
                        mfBusApplyList.add(mfBusApply);
                    }
                }
            }
        }
        model.addAttribute("query", "");
        model.addAttribute("mfBusApplyList", mfBusApplyList);
        model.addAttribute("id", id);
        return "/component/archives/ArchiveInfoVoucherReturn_Insert";
    }

    /**
     * 凭证退还--业务
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputForBus")
    public String inputForBus(Model model,String appId,String docNo, String docName,String archiveMainNo, String archiveDetailNo) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchivevoucherinsert = null;
        ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
        //根据申请人是否为资产保全部来判断，是正常凭证退还，还是凭证处置
        String brNo = User.getOrgNo(request);
        CodeUtils cu = new CodeUtils();
        Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
        String zichanbaoquan = constantMap.get("zichanbaoquan");
        if(brNo.contains(zichanbaoquan)){
            formarchivevoucherinsert = formService.getFormData("archivevoucherinsert_deal");
            archiveInfoVoucherReturn.setDealMode("02");//凭证处置
        }else{
            formarchivevoucherinsert = formService.getFormData("archivevouchercreditinsert");
            archiveInfoVoucherReturn.setDealMode("01");//正常凭证退还
        }

        //更新信息
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        String id = WaterIdUtil.getWaterId();
        archiveInfoVoucherReturn.setId(id);
        archiveInfoVoucherReturn.setAppId(appId);
        archiveInfoVoucherReturn.setAppName(mfBusApply.getAppName());
        archiveInfoVoucherReturn.setPactId(mfBusApply.getPactId());
        archiveInfoVoucherReturn.setPactNo(mfBusApply.getLetterPactNo());
        archiveInfoVoucherReturn.setCusNo(mfBusApply.getCusNo());
        archiveInfoVoucherReturn.setCusName(mfBusApply.getCusName());
        archiveInfoVoucherReturn.setArchivePactStatus("02");//业务
        archiveInfoVoucherReturn.setDocNo(docNo);
        archiveInfoVoucherReturn.setDocName(docName);
        archiveInfoVoucherReturn.setArchiveMainNo(archiveMainNo);
        archiveInfoVoucherReturn.setArchiveDetailNo(archiveDetailNo);
        archiveInfoVoucherReturn.setOpNo(User.getRegNo(request));
        archiveInfoVoucherReturn.setOpName(User.getRegName(request));
        archiveInfoVoucherReturn.setBrNo(User.getOrgNo(request));
        archiveInfoVoucherReturn.setBrName(User.getRegName(request));
        archiveInfoVoucherReturn.setReturnDate(DateUtil.getDate());
        archiveInfoVoucherReturn.setReturnNo(User.getRegNo(request));
        archiveInfoVoucherReturn.setReturnName(User.getRegName(request));
        getObjValue(formarchivevoucherinsert, archiveInfoVoucherReturn);
        model.addAttribute("formarchivevoucherinsert", formarchivevoucherinsert);

        //业务
        List<MfBusApply> mfBusApplyList = new ArrayList<MfBusApply>();
        mfBusApplyList.add(mfBusApply);
        model.addAttribute("query", "");
        model.addAttribute("mfBusApplyList", mfBusApplyList);
        model.addAttribute("id", id);
        return "/component/archives/ArchiveInfoVoucherReturn_Insert";
    }

    @RequestMapping("/editForConfim")
    public String editForConfim(Model model,String certiId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formarchivecertiedit = formService.getFormData("archivecertiedit");
        getFormValue(formarchivecertiedit);
        if(StringUtil.isNotEmpty(certiId)){
            CertiInfo certiInfo = new CertiInfo();
            certiInfo.setCertiId(certiId);
            certiInfo = certiInfoFeign.getById(certiInfo);
            PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
            pledgeBaseInfo.setPledgeNo(certiInfo.getCollateralId());
            pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
            getObjValue(formarchivecertiedit, pledgeBaseInfo);
            getObjValue(formarchivecertiedit, certiInfo);
            model.addAttribute("collateralId", certiInfo.getCollateralId());
        }
        model.addAttribute("query", "");
        model.addAttribute("formarchivecertiedit", formarchivecertiedit);
        return "/component/archives/ArchiveInfoVoucherReturn_EditForVoucherReturn";
    }

    /**上传还款凭证界面
     *
     * @param model
     * @param appId
     * @return
     * @throws Exception
     */
    @RequestMapping("/editForApp")
    public String editForApp(Model model,String appId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formarchivevoucherbus = formService.getFormData("archivevoucherbus");
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(appId);
        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
        getObjValue(formarchivevoucherbus, mfBusApply);
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setAppId(appId);
        List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getList(mfBusFincApp);
        if(mfBusFincAppList != null && mfBusFincAppList.size() > 0){
            getObjValue(formarchivevoucherbus, mfBusFincAppList.get(0));
        }
        model.addAttribute("formarchivevoucherbus", formarchivevoucherbus);
        model.addAttribute("query", "");
        model.addAttribute("archivePactStatus", "archivePactStatus");
        model.addAttribute("appId", appId);
        return "/component/archives/ArchiveInfoVoucherReturn_EditForApp";
    }

    /**
     * 凭证退回申请--保存还款凭证
     * @param ajaxData
     * @param appId
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateRepayVoucher")
    @ResponseBody
    public Map<String, Object> updateRepayVoucher(String ajaxData,String appId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        try {
            FormData formarchivevoucherbus = formService.getFormData("archivevoucherbus");
            getFormValue(formarchivevoucherbus, getMapByJson(ajaxData));
            if (this.validateFormData(formarchivevoucherbus)) {
                CodeUtils cu = new CodeUtils();
                Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
                String repayVoucher = constantMap.get("repayVoucher");//还款凭证
                DocManage docManageQuery = new DocManage();
                docManageQuery.setDocBizNo(appId);
                docManageQuery.setDocSplitNo(repayVoucher);
                List<DocManage> docManageList = docManageFeign.getList(docManageQuery);
                if(docManageList == null || docManageList.size() <= 0){//未上传还款凭证
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "必须上传还款凭证！");
                    return dataMap;
                }
                dataMap.put("flag", "success");
                dataMap.put("msg", "保存成功");
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * AJAX凭证退还
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
            FormData formarchivevoucherinsert = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formarchivevoucherinsert, getMapByJson(ajaxData));
            if(this.validateFormDataAnchor(formarchivevoucherinsert)){
                ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
                setObjValue(formarchivevoucherinsert, archiveInfoVoucherReturn);
                archiveInfoVoucherReturn.setRegTime(DateUtil.getDateTime());
                archiveInfoVoucherReturnFeign.insert(archiveInfoVoucherReturn);
                dataMap.put("flag", "success");
                dataMap.put("msg", "凭证退还申请成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "凭证退还申请失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * 凭证退还-补充资料界面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/voucherReturn")
    public String voucherReturn(Model model,String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchivevoucherinsert = null;
        ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
        if("02".equals(archiveInfoVoucherReturn.getDealMode())){
            formarchivevoucherinsert = formService.getFormData("archivevoucherinsert_deal");
            archiveInfoVoucherReturn.setDealMode("02");//凭证处置
        }else{
            formarchivevoucherinsert = formService.getFormData("archivevoucherinsert");
            archiveInfoVoucherReturn.setDealMode("01");//正常凭证退还
        }
        archiveInfoVoucherReturn.setId(id);
        archiveInfoVoucherReturn = archiveInfoVoucherReturnFeign.getById(archiveInfoVoucherReturn);
        getObjValue(formarchivevoucherinsert, archiveInfoVoucherReturn);
        model.addAttribute("formarchivevoucherinsert", formarchivevoucherinsert);
        //业务
        List<MfBusApply> mfBusApplyList = new ArrayList<MfBusApply>();
        if("01".equals(archiveInfoVoucherReturn.getArchivePactStatus())){
            List<ArchiveInfoMain> archiveInfoMainList = new ArrayList<ArchiveInfoMain>();
            ArchiveInfoMain archiveInfoMainQuery = new ArchiveInfoMain();
            archiveInfoMainQuery.setCreditAppId(archiveInfoVoucherReturn.getCreditAppId());
            archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMainQuery);
            String appIds = "";
            for(ArchiveInfoMain main : archiveInfoMainList){
                if(StringUtil.isNotEmpty(main.getAppId())){
                    if(!appIds.contains(main.getAppId())){
                        appIds += main.getAppId() + ",";
                        MfBusApply mfBusApply = new MfBusApply();
                        mfBusApply.setAppId(main.getAppId());
                        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
                        if(mfBusApply != null){
                            mfBusApplyList.add(mfBusApply);
                        }
                    }
                }
            }
        }else{
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(archiveInfoVoucherReturn.getAppId());
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApplyList.add(mfBusApply);
        }

        model.addAttribute("mfBusApplyList", mfBusApplyList);
        model.addAttribute("id", id);
        model.addAttribute("query", "");
        return "/component/archives/ArchiveInfoVoucherReturn_Supplement";
    }

    /**
     * 补充资料再次进行凭证退还
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            dataMap = getMapByJson(ajaxData);
            FormData formarchivevoucherinsert = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formarchivevoucherinsert, getMapByJson(ajaxData));
            if(this.validateFormDataAnchor(formarchivevoucherinsert)){
                ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
                setObjValue(formarchivevoucherinsert, archiveInfoVoucherReturn);
                archiveInfoVoucherReturnFeign.update(archiveInfoVoucherReturn);
                dataMap.put("flag", "success");
                dataMap.put("msg", "退还申请成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "退还申请失败");
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getViewPoint")
    public String getViewPoint(Model model, String id, String taskId, String activityType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchivevoucherapproval = formService.getFormData("archivevoucherapproval");
        ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
        archiveInfoVoucherReturn.setId(id);
        archiveInfoVoucherReturn = archiveInfoVoucherReturnFeign.getById(archiveInfoVoucherReturn);
        getObjValue(formarchivevoucherapproval, archiveInfoVoucherReturn);
        CodeUtils codeUtils = new CodeUtils();
        TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, taskId);// 当前审批节点task
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
        opinionTypeMap.put("hideOpinionType", "4"); // 隐藏审批类型
        opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
        opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
        List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
        this.changeFormProperty(formarchivevoucherapproval, "opinionType", "optionArray", opinionTypeList);
        request.setAttribute("archiveInfoVoucherReturn", archiveInfoVoucherReturn);
        model.addAttribute("formarchivevoucherapproval", formarchivevoucherapproval);
        model.addAttribute("query", "");
        return "/component/archives/ArchiveInfoVoucherReturn_WkfViewPoint";
    }

    /**
     *
     * 方法描述： 审批提交（审批意见保存）
     * @return
     * @throws Exception
     * String
     * @author zhs
     * @date 2016-5-26 上午10:53:17t
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/submitUpdateAjax")
    @ResponseBody
    public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String id, String transition, String nextUser) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String appOpNo = User.getRegNo(request);
        TaskImpl taskApprove = wkfInterfaceFeign.getTaskWithUser(id, "",appOpNo);
        if(!taskId.equals(String.valueOf(taskApprove.getDbid()))){//不相等则审批不在此环节
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
            return dataMap;
        }
        Map map = getMapByJson(ajaxData);
        FormData formarchivevoucherapproval = formService.getFormData((String) map.get("formId"));
        getFormValue(formarchivevoucherapproval, map);
        setObjValue(formarchivevoucherapproval, archiveInfoVoucherReturn);
        dataMap = getMapByJson(ajaxData);
        String opinionType = String.valueOf(dataMap.get("opinionType"));
        String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
        try {
            archiveInfoVoucherReturn.setCurrentSessionRegNo(User.getRegNo(request));
            archiveInfoVoucherReturn.setCurrentSessionRegName(User.getRegName(request));
            archiveInfoVoucherReturn.setCurrentSessionOrgNo(User.getOrgNo(request));
            archiveInfoVoucherReturn.setCurrentSessionOrgName(User.getOrgName(request));
            TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, taskId);
            Result res = archiveInfoVoucherReturnFeign.doCommit(taskId, id, opinionType, approvalOpinion, transition, User.getRegNo(request), nextUser, archiveInfoVoucherReturn);
            if (res.isSuccess()) {
                dataMap.put("flag", "success");
                dataMap.put("msg", res.getMsg());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", res.getMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
            throw e;
        }
        return dataMap;

    }

    /**
     * 凭证退还确认
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/confimTransfer")
    public String confimTransfer(Model model,String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formarchivevoucherconfim = formService.getFormData("archivevoucherconfim");
        ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
        archiveInfoVoucherReturn.setId(id);
        archiveInfoVoucherReturn = archiveInfoVoucherReturnFeign.getById(archiveInfoVoucherReturn);
        getObjValue(formarchivevoucherconfim, archiveInfoVoucherReturn);
        model.addAttribute("formarchivevoucherconfim", formarchivevoucherconfim);
        //他项凭证或者他项凭证
        List<CertiInfo> certiInfoList = new ArrayList<CertiInfo>();
        List<PledgeBaseInfo> pledgeBaseInfoList = new ArrayList<PledgeBaseInfo>();
        String[] docNoArr = archiveInfoVoucherReturn.getDocNo().split(",");
        for(int i=0;i<docNoArr.length;i++){
            if(StringUtil.isNotEmpty(docNoArr[i])){
                DocManage docManage = new DocManage();
                docManage.setDocNo(docNoArr[i]);
                docManage = docManageFeign.getById(docManage);
                CodeUtils cu = new CodeUtils();
                Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
                String originalVoucher = constantMap.get("originalVoucher");
                String otherVoucher = constantMap.get("otherVoucher");
                if(docManage != null ){
                    if(otherVoucher.equals(docManage.getDocSplitNo())){
                        CertiInfo certiInfo = new CertiInfo();
                        certiInfo.setCollateralId(docManage.getDocBizNo());
                        certiInfo = certiInfoFeign.getByCollateralId(certiInfo);
                        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                        pledgeBaseInfo.setPledgeNo(certiInfo.getCollateralId());
                        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
                        certiInfo.setCertificateName(pledgeBaseInfo.getCertificateName());//权属人
                        certiInfo.setExtLstr03(pledgeBaseInfo.getPleCertificateOwner());//原始凭证号
                        certiInfo.setExtLstr04(pledgeBaseInfo.getExtLstr04());//原始凭证名称
                        certiInfoList.add(certiInfo);
                    }else if(originalVoucher.equals(docManage.getDocSplitNo())){
                        PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                        pledgeBaseInfo.setPledgeNo(docManage.getDocBizNo());
                        pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
                        pledgeBaseInfoList.add(pledgeBaseInfo);
                    }

                }
            }
        }
        model.addAttribute("certiInfoList", certiInfoList);
        model.addAttribute("pledgeBaseInfoList", pledgeBaseInfoList);
        //业务
        List<MfBusApply> mfBusApplyList = new ArrayList<MfBusApply>();
        if("01".equals(archiveInfoVoucherReturn.getArchivePactStatus())){
            List<ArchiveInfoMain> archiveInfoMainList = new ArrayList<ArchiveInfoMain>();
            ArchiveInfoMain archiveInfoMainQuery = new ArchiveInfoMain();
            archiveInfoMainQuery.setCreditAppId(archiveInfoVoucherReturn.getCreditAppId());
            archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMainQuery);
            String appIds = "";
            for(ArchiveInfoMain main : archiveInfoMainList){
                if(StringUtil.isNotEmpty(main.getAppId())){
                    if(!appIds.contains(main.getAppId())){
                        appIds += main.getAppId() + ",";
                        MfBusApply mfBusApply = new MfBusApply();
                        mfBusApply.setAppId(main.getAppId());
                        mfBusApply = mfBusApplyFeign.getById(mfBusApply);
                        if(mfBusApply != null){
                            mfBusApplyList.add(mfBusApply);
                        }
                    }
                }
            }
        }else{
            MfBusApply mfBusApply = new MfBusApply();
            mfBusApply.setAppId(archiveInfoVoucherReturn.getAppId());
            mfBusApply = mfBusApplyFeign.getById(mfBusApply);
            mfBusApplyList.add(mfBusApply);
        }

        model.addAttribute("id", id);
        model.addAttribute("mfBusApplyList", mfBusApplyList);
        model.addAttribute("query", "");
        return "/component/archives/ArchiveInfoVoucherReturn_Confim";
    }

    /**
     * AJAX凭证退还确认
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
            FormData formarchivevoucherconfim = formService.getFormData(String.valueOf(dataMap.get("formId")));
            getFormValue(formarchivevoucherconfim, getMapByJson(ajaxData));
            if(this.validateFormDataAnchor(formarchivevoucherconfim)){
                ArchiveInfoVoucherReturn archiveInfoVoucherReturn = new ArchiveInfoVoucherReturn();
                setObjValue(formarchivevoucherconfim, archiveInfoVoucherReturn);
                archiveInfoVoucherReturnFeign.confimTransfer(archiveInfoVoucherReturn);
                dataMap.put("flag", "success");
                dataMap.put("msg", "确认成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "确认失败");
            throw e;
        }
        return dataMap;
    }
}
