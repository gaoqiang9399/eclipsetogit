package app.component.cus.report.controller;


import app.component.cus.report.entity.MfCusReportCalc;
import app.component.cus.report.entity.MfCusReportItem;
import app.component.cus.report.feign.MfCusReportItemFeign;
import app.component.finance.finreport.entity.CwSearchReportList;
import app.component.finance.util.CwPublicUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mfCusReportItem")
public class MfCusReportItemController extends BaseFormBean {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCusReportItemFeign mfCusReportItemFeign;

    @RequestMapping(value = "/getListPageNew")
    public String getListPageNew(Model model, String ajaxData, String reportType) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        String reportName = null;
        String reportTypeId="";
        if ("1".equals(reportType)) {
            reportName = "资产负债表";
            reportTypeId="001";
        } else if ("2".equals(reportType)) {
            reportName = "利润分配表";
            reportTypeId="002";
        } else if ("3".equals(reportType)) {
            reportName = "现金流量表";
            reportTypeId="003";
        }else {
        }
        FormData formpfs5008 = formService.getFormData("reportItem001");
        Map map=new HashMap<String,String>();
        map.put("reportTypeId",reportTypeId);
        getFormValue(formpfs5008, map);  //给form赋值

        model.addAttribute("reportType", reportType);
        model.addAttribute("reportName", reportName);
        model.addAttribute("formpfs5008", formpfs5008);

        MfCusReportItem mfCusReportItem=new MfCusReportItem();
        mfCusReportItem.setReportTypeId(reportTypeId);
        List<MfCusReportItem> mfCusReportItemList= mfCusReportItemFeign.getList(mfCusReportItem);
        model.addAttribute("mfCusReportItemList", mfCusReportItemList);
        model.addAttribute("query", "");
        return "/component/cus/report/MfCusReportItem_List";
    }

    /**
     * 方法描述： 进入财务报表设置列表页面
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-3-9 下午2:11:11
     */
    @RequestMapping(value = "/toReportItemSet")
    public String toReportItemSet(Model model, String reportTypeId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("reportTypeId", reportTypeId);
        model.addAttribute("dataMap", dataMap);
        return "component/cus/report/CwReportItem_Set";
    }

    /**
     * 方法描述： 获取财务报表设置列表
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-3-8 下午5:30:06
     */
    @RequestMapping(value = "/getSetReportListAjax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSetReportListAjax(String tableId, String tableType, String reportTypeId,
                                                    String itemflag) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, String> formMap = new HashMap<String, String>();
            formMap.put("reportTypeId", reportTypeId);
            formMap.put("itemflag", itemflag);
            Ipage ipage = this.getIpage();
            List<CwSearchReportList> list = mfCusReportItemFeign.getSetReportList(formMap);
            ipage.setResult(list);
            ipage.setPageCounts(list.size());
            // 返回相应的HTML方法
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
//			logger.error("查询凭证列表数据出错", e);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 进入财务报表预览页面
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-3-13 下午2:53:23
     */
    @RequestMapping(value = "/toReportItemView")
    public String toReportItemView(Model model, String reportTypeId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("reportTypeId", reportTypeId);
        model.addAttribute("dataMap", dataMap);
        return "/component/cus/report/CwReportItem_View";
    }

    /**
     * 方法描述： 进入报表项新增页面
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-3-9 下午2:14:06
     */
    @RequestMapping(value = "/toReportItemAdd")
    public String toReportItemAdd(Model model, String reportItemId, String reportTypeId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> formData = new HashMap<String, Object>();
        FormData formfinreport0002 = formService.getFormData("reportitem0001");
        MfCusReportItem mfCusReportItem = new MfCusReportItem();
        if (StringUtil.isNotEmpty(reportItemId)) {
            MfCusReportItem query = new MfCusReportItem();
            query.setReportItemId(reportItemId);
            query = mfCusReportItemFeign.getById(query);
            mfCusReportItem.setReoprtItemLev(query.getReportItemId());
            mfCusReportItem.setReoprtLevName(query.getReportName());
            mfCusReportItem.setReportItemType(query.getReportItemType());
            dataMap.put("reoprtItemLev", reportItemId);
        } else {
            dataMap.put("reoprtItemLev", "");
        }
        dataMap.put("reportTypeId", reportTypeId);
        mfCusReportItem.setReportTypeId(reportTypeId);
        getObjValue(formfinreport0002, mfCusReportItem, formData);
        model.addAttribute("query", "");
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("formfinreport0002", formfinreport0002);
        model.addAttribute("cwReportItem", mfCusReportItem);
        return "/component/cus/report/CwReportItem_add";
    }

    @RequestMapping(value = "/getReportViewListAjax")
    @ResponseBody
    public Map<String, Object> getReportViewListAjax(String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
            Ipage ipage = this.getIpage();
            List<CwSearchReportList> list = mfCusReportItemFeign.getReportViewList(formMap);
            ipage.setResult(list);
            ipage.setPageCounts(list.size());
            JsonTableUtil jtu = new JsonTableUtil();
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

    /**
     * 方法描述： 进入报表项编辑页面
     *
     * @return String
     * @author Javelin
     * @throws Exception
     * @date 2017-1-23 上午9:11:12
     */
    @RequestMapping(value = "/toDeployPage")
    public String toDeployPage(Model model, String reportItemId) throws Exception {
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        FormData formfinreport0002 = formService.getFormData("finreport0002");
        MfCusReportItem mfCusReportItem = new MfCusReportItem();
        mfCusReportItem.setReportItemId(reportItemId);
        mfCusReportItem = mfCusReportItemFeign.getReportItemDetail(mfCusReportItem);
        getObjValue(formfinreport0002, mfCusReportItem, formData);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("reportItemId", reportItemId);
        dataMap.put("isInput", mfCusReportItem.getIsInput());
        model.addAttribute("cwReportItem", mfCusReportItem);
        model.addAttribute("formfinreport0002", formfinreport0002);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        return "/component/cus/report/CwReportDeploy";
    }

    /**
     * 方法描述： 获取报表项公式列表数据
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-1-23 下午3:37:45
     */
    @RequestMapping(value = "/getItemCalcSetListAjax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getItemCalcSetListAjax(String tableId, String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
            Ipage ipage = this.getIpage();
            List<MfCusReportCalc> list = mfCusReportItemFeign.getItemCalcSetList(formMap);
            ipage.setResult(list);
            ipage.setPageCounts(list.size());
            // 返回相应的HTML方法
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
//			logger.error("查询凭证列表数据出错", e);
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 新增报表项
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-3-9 下午4:30:11
     */
    @RequestMapping(value = "/addItemAjax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addItemAjax(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formfinreport0002 = formService.getFormData("reportitem0001");
            getFormValue(formfinreport0002, getMapByJson(ajaxData));
            if (this.validateFormData(formfinreport0002)) {
                MfCusReportItem mfCusReportItem = new MfCusReportItem();
                setObjValue(formfinreport0002, mfCusReportItem);
                String finBooks = (String)request.getSession().getAttribute("finBooks");
                mfCusReportItemFeign.insert(mfCusReportItem);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
     * 方法描述： 修改报表排序
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-3-10 上午9:12:55
     */
    @RequestMapping(value = "/updateOrderAjax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateOrderAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, String> formMap = new Gson().fromJson(ajaxData, Map.class);
            String r = mfCusReportItemFeign.updateOrder(formMap);
            r = URLDecoder.decode(r, "UTF-8");
            if ("0000".equals(r)){
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", r);
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            // dataMap.put("msg", "修改排序失败");
            dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 修改报表项公式与详情数据
     *
     * @return
     * @throws Exception
     *             String
     * @author Javelin
     * @date 2017-3-10 下午2:18:17
     */
    @RequestMapping(value = "/updateDeployAjax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDeployAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = getMapByJson(ajaxData);
            String r = mfCusReportItemFeign.updateDeployData(paramMap);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }
    /**
     * AJAX新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertItemAjax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertItemAjax(String ajaxData, String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData  formfinreport0002 = formService.getFormData("reportitem0001");
            getFormValue(formfinreport0002, getMapByJson(ajaxData));
            Map<String, Object> mapByJson = getMapByJson(ajaxData);// 2017年10月13日 修改流入流出的bug

            if (this.validateFormData(formfinreport0002)) {
                MfCusReportItem mfCusReportItem = new MfCusReportItem();
                setObjValue(formfinreport0002, mfCusReportItem);
                String reportTypeId2 = mfCusReportItem.getReportTypeId();
                if ("002".equals(reportTypeId2)) {
                    String isInput = (String) mapByJson.get("isInput");
                    mfCusReportItem.setIsInput(isInput);
                }
                mfCusReportItemFeign.insert(mfCusReportItem);
                JsonTableUtil jtu = new JsonTableUtil();
                String  tableHtml = jtu.getJsonStr(tableId,"tableTag", mfCusReportItemFeign.getList(mfCusReportItem), null,true);
                dataMap.put("tableData",tableHtml);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
     * AJAX新增-财务指标元素
     * @param ajaxDataList
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData,String reportType, Object ajaxDataList) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
            List<MfCusReportCalc> cusFinFormulaList = (List<MfCusReportCalc>) JSONArray.toList(jsonArray, new MfCusReportCalc(),
                    new JsonConfig());
            FormData formpfs5008 = formService.getFormData("reportItem001");
            getFormValue(formpfs5008, getMapByJson(ajaxData));
            if (this.validateFormData(formpfs5008)) {
                MfCusReportItem mfCusReportItem = new MfCusReportItem();
                setObjValue(formpfs5008, mfCusReportItem);
                mfCusReportItem.setReportTypeId("00"+reportType);
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("mfCusReportItem", mfCusReportItem);
                paramMap.put("mfCusReportCalcList", cusFinFormulaList);
                mfCusReportItem = mfCusReportItemFeign.insertItemAndCalc(paramMap);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                dataMap.put("mfCusReportItem", mfCusReportItem);
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

    // 更新计算项
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData, String ajaxDataList) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
//        try {
//            JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
//            List<CusFinFormula> cusFinFormulaList = (List<CusFinFormula>) JSONArray.toList(jsonArray, new CusFinFormula(),
//                    new JsonConfig());
//            FormData formpfs5008 = formService.getFormData("pfs5009");
//            getFormValue(formpfs5008, getMapByJson(ajaxData));
//            if (this.validateFormData(formpfs5008)) {
//                CusFinParm cusFinParm = new CusFinParm();
//                setObjValue(formpfs5008, cusFinParm);
//                Map<String, Object> paramMap = new HashMap<String, Object>();
//                paramMap.put("cusFinParm", cusFinParm);
//                paramMap.put("cusFinFormulaList", cusFinFormulaList);
//                cusFinParm = cusFinParmFeign.update1(paramMap);
//                // cusFinParm=cusFinParmFeign.getById(cusFinParm);
//                // List<CusFinParm> list = new ArrayList<CusFinParm>();
//                // list.add(cusFinParm);
//                // getTableData(list);//获取列表
//                dataMap.put("cusFinParm", cusFinParm);
//                dataMap.put("flag", "success");
//                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
//            } else {
//                dataMap.put("flag", "error");
//                dataMap.put("msg", this.getFormulavaliErrorMsg());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            dataMap.put("flag", "error");
//            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
//            throw e;
//        }
        return dataMap;
    }

    /**
     * 根据财务报表指标项和报表类型查询该项是否有计算项
     *
     * @author LJW date 2017-4-12
     * @param codeColumn
     * @param reportType
     * @param query
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String ajaxData, String codeColumn, String reportType, String query) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
//        FormData formpfs5008 = formService.getFormData("pfs5009");
//        CusFinParm cusFinParm = new CusFinParm();
//        cusFinParm.setCodeColumn(codeColumn);
//        System.out.println(codeColumn);
//        cusFinParm.setReportType(reportType);
//        cusFinParm = cusFinParmFeign.getById(cusFinParm);
//        getObjValue(formpfs5008, cusFinParm);
//        JsonFormUtil jfu = new JsonFormUtil();
//        String formHtml = jfu.getJsonStr(formpfs5008, "bootstarpTag", "");
//        List<CusFinFormula> cusFinFormulaList = null;
//        if ("2".equals(cusFinParm.getInputType())) {// 运算项
//            CusFinFormula cusFinFormula = new CusFinFormula();
//            cusFinFormula.setCodeColumn(codeColumn);
//            cusFinFormulaList = cusFinFormulaFeign.getByCodeColumn(cusFinFormula);
//        }
//        dataMap.put("cusFinParm", cusFinParm);
//        dataMap.put("flag", "success");
//        dataMap.put("formHtml", formHtml);
//        dataMap.put("cusFinFormulaList", cusFinFormulaList);
//        dataMap.put("query", "");
        return dataMap;
    }
}
