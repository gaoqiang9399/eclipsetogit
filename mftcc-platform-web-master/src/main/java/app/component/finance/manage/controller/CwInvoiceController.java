package app.component.finance.manage.controller;

import app.base.ServiceException;
import app.component.finance.invoice.entity.CwInvoice;
import app.component.finance.manage.feign.CwInvoiceFeign;
import app.tech.oscache.CodeUtils;
import app.util.ExcelUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发票管理
 * @author zhangdongyang
 * @date 2020/5/21 15:06
 */
@Controller
@RequestMapping("/cwInvoice")
public class CwInvoiceController extends BaseFormBean {
    private static final long serialVersionUID = 9196454891709523438L;

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    private CwInvoiceFeign cwInvoiceFeign;

    /**
     * 发票列表
     * @author zhangdongyang
     * @param model
     * @date 2020/6/1 13:19
     * @return java.lang.String
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);
        // 前台自定义筛选组件的条件项，从数据字典缓存获取。
        JSONArray invoiceTypeCodeArray = new CodeUtils().getJSONArrayByKeyName("INVOICE_TYPE_CODE");
        this.getHttpRequest().setAttribute("invoiceTypeCodeArray", invoiceTypeCodeArray);
        return "/component/finance/invoice/CwInvoice_List";
    }

    /**
     * 发票列表
     * @author zhangdongyang
     * @param model
     * @date 2020/6/1 13:19
     * @return java.lang.String
     */
    /**
     * 查询发票打印列表，通过Ajax方式
     * @author zhangdongyang
     * @param pageNo
     * @param pageSize
     * @param tableId
     * @param tableType
     * @param ajaxData
     * @date 2020/6/1 13:19
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/findInvoiceListByAjax")
    @ResponseBody
    public Map<String, Object> findInvoiceListByAjax(Integer pageNo, Integer pageSize, String tableId,
                                                  String tableType, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        CwInvoice cwInvoice = new CwInvoice();
        try {
            cwInvoice.setCustomQuery(ajaxData);// 自定义查询参数赋值
            cwInvoice.setCustomSorts(ajaxData);// 自定义排序参数赋值
            cwInvoice.setCriteriaList(cwInvoice, ajaxData);// 我的筛选
//            cwCollectConfim.setStatus(BizPubParm.COLLECT_STATUS_2);//查询已到账确认的
            cwInvoice.getCriteriaLists().forEach((criteriaList)->{
                criteriaList.forEach((criteria)->{
                    String condition = criteria.getCondition();
                    if("KPRQ between ".equals(condition)){
                        String secondValue = criteria.getSecondValue();
                        if(secondValue != null && !"".equals(secondValue)){
                            long longValue = Long.parseLong(secondValue);
                            longValue++;
                            criteria.setSecondValue(String.valueOf(longValue));
                        }
                    }
                });
            });
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("cwInvoice", cwInvoice));
            ipage = cwInvoiceFeign.findInvoiceListByAjax(ipage);
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
     * 批量打印
     * @author zhangdongyang
     * @param ids
     * @date 2020/6/2 19:03
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/batchPrint")
    @ResponseBody
    public Map <String, Object> batchPrint(String ids) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        try {
            JSONArray jsonArray = cwInvoiceFeign.getPrintInfoByIds(ids);
            dataMap.put("data", jsonArray.toString());
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }


    /**
     * 根据到账确认id查询发票列表-页面
     * @author zhangdongyang
     * @param collectId
     * @date 2020/5/21 15:34
     * @return map
     */
    @RequestMapping(value = "/invoicePage")
    public String invoicePage(Model model, String collectId) throws ServiceException {
        List<CwInvoice> invoicelist = cwInvoiceFeign.findListByCollectId(collectId);
        model.addAttribute("invoicelist", invoicelist);
        return "/component/finance/invoice/CwInvoice";
    }


    /**
     * 根据到账确认id查询发票列表
     * @author zhangdongyang
     * @param collectId
     * @date 2020/5/21 15:34
     * @return map
     */
    @RequestMapping(value = "/findListByCollectId/{collectId}")
    @ResponseBody
    public Map <String, Object> findListByCollectId(@PathVariable("collectId") String collectId) throws ServiceException {
        List<CwInvoice> invoicelist = cwInvoiceFeign.findListByCollectId(collectId);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        dataMap.put("data", invoicelist);
        dataMap.put("flag", "success");
        dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
        return dataMap;
    }


    /**
     * 根据发票id查询发票详细信息
     * @author zhangdongyang
     * @param id
     * @date 2020/5/21 16:03
     * @return map
     */
    @RequestMapping(value = "/getPrintInfoById/{id}")
    @ResponseBody
    public Map <String, Object> getById(@PathVariable("id") String id) throws Exception {
        JSONObject jsonObject = cwInvoiceFeign.getPrintInfoById(id);
        Map <String, Object> dataMap = new HashMap <String, Object>();
        dataMap.put("data", jsonObject.toString());
        dataMap.put("flag", "success");
        dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
        return dataMap;
    }

    /**
     * 导出excel
     * @author chenyingying
     * @param ids
     * @date 2020/6/10 10:57
     */
    @RequestMapping(value = "/exportExcel")
    @ResponseBody
    public void exportExcel(String ids) throws Exception {
        //查询要导出的数据
        List<Map<String,String>> list = cwInvoiceFeign.getInvoiceInfoByIds(ids);

        //设置标题栏、文件名、表单名
        String[] headerName = { "开票日期","发票类型", "发票号码", "购方名称","纳税人识别号",
                "地址电话","开户行及账号","开票备注","费用名称","金额","税率","税额","价税合计"};
        String fileName = "发票信息.xlsx";
        String sheetname = "发票信息";

        //声明表单内容
        String[][] content = new String[list.size()][headerName.length];
        //遍历要导出的数据列表，构造表单内容
        for (int i=0 ; i<list.size() ; i++) {
            Map<String,String> map = list.get(i);
            //获取表单第i行
            String[] row = content[i];
            //填充内容
            row[0] = map.get("kprq");
            row[1] = getDictNameByDictType(map.get("fplxdm"));
            row[2] = map.get("fphm");
            row[3] = map.get("gfmc");
            row[4] = map.get("gfnsrsbh");
            row[5] = map.get("gfdzdh");
            row[6] = map.get("gfyhzh");
            row[7] = map.get("bz");
            row[8] = map.get("spmc");
            row[9] = map.get("je");
            row[10] =map.get("sl");
            row[11] =map.get("se");
            row[12] =map.get("jshj");
        }

        //获取文档
        XSSFWorkbook workbook = ExcelUtils.getWorkbook(sheetname, headerName, content);
        //声明输出流
        OutputStream outputStream = null;
        //响应到客户端
        try {
            //设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") );
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            //获取输出流
            outputStream = response.getOutputStream();
            //用文档写输出流
            workbook.write(outputStream);
            //刷新输出流
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭输出流
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 通过数据字段type找name
     * @author chenyingying
     * @param type
     * @date 2020/6/10 16:54
     * @return
     * @throws Exception
     */
    public String getDictNameByDictType(String type) throws Exception{
        JSONArray array = new CodeUtils().getJSONArrayByKeyName("INVOICE_TYPE_CODE");
        String name = "";
        for(int i=0;i<array.size();i++){
            JSONObject job = array.getJSONObject(i);
            if(type.equals(job.get("optCode"))){
                name = (String) job.get("optName");
                break;
            }
        }
        return name;
    }

}