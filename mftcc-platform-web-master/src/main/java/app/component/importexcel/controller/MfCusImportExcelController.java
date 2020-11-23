package app.component.importexcel.controller;

import java.io.PushbackInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import app.component.common.BizPubParm;
import app.component.doc.feign.DocFeign;
import app.component.importexcel.entity.MfCusImportExcelCensus;
import app.component.importexcel.entity.MfCusImportExcelHis;
import app.component.importexcel.feign.MfImportCusPersBaseFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.WaterIdUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.CopySheetUtil;
import com.core.domain.screen.FormData;
import com.core.report.ExpExclUtil;
import com.core.service.screen.FormService;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import app.base.ServiceException;
import app.base.SpringUtil;
import app.component.importexcel.feign.MfCusImportExcelFeign;
import config.YmlConfig;

/**
 * Title: MfCusHouseInfoAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 04 14:45:45 CST 2016
 **/
@Controller
@RequestMapping("/mfCusImportExcel")
public class MfCusImportExcelController extends BaseFormBean {
    @Autowired
    private MfCusImportExcelFeign mfCusImportExcelFeign;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfImportCusPersBaseFeign mfImportCusPersBaseFeign;
    @Autowired
    private DocFeign docFeign;
    // 全局变量
    // 异步参数
    // 表单变量

    /**
     * @return dataMap
     * @功能 导入excel
     * @作者 付晨
     * @日期 2018年5月21日 下午5:15
     */
    @RequestMapping(value = "/checkImportExcel")
    @ResponseBody
    public Map<String, Object> checkImportExcel(@RequestParam(value = "mfCusImportExcel") MultipartFile mfCusImportExcel,
                                                @RequestParam(value = "importType") String importType) throws ServiceException {
        int result = 0;
        YmlConfig ymlConfig = (YmlConfig) SpringUtil.getBean(YmlConfig.class);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ActionContext.initialize(request, response);
        MfCusImportExcelHis mfCusImportExcelHis = new MfCusImportExcelHis();
        Workbook workbook = null;
        try {
            String id = WaterIdUtil.getWaterId();
            String destFileName = mfCusImportExcel.getOriginalFilename();
            //校验导入模板格式
            String upLoadFileNameExt = destFileName.split("\\.")[1];//判断上传的格式
            if (!"xlsx".equals(upLoadFileNameExt) && !"xls".equals(upLoadFileNameExt)) {
                dataMap.put("readFlag", false);
                dataMap.put("msg", "上传文件格式不正确！");
                return dataMap;
            }
            //Map<String, String> sheetMaps = new HashMap<String, String>();
            //获得导入模板中所有sheet
            List<String> sheets = new ArrayList<String>();
            InputStream ins = mfCusImportExcel.getInputStream();
            workbook = WorkbookFactory.create(ins);
            String sheetsStr = "";
            for(int i=0;i<workbook.getNumberOfSheets();i++){
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                if (sheetName.indexOf("&")!=-1){
                    String[] sheetArr = sheet.getSheetName().split("&");
                    sheets.add(sheetArr[1]);
                    sheetsStr = sheetArr[1]+"&"+sheetsStr;
                    //sheetMaps.put(i+"",sheetName);
                }
            }
            mfCusImportExcelHis.setSheetsStr(sheetsStr);
            mfCusImportExcelHis.setSheets(sheets);
            mfCusImportExcelHis.setImportFileName(destFileName);

            //复制导入的文件创建新文件
            String uploadFinPath = ymlConfig.getUpload().get("uploadFinFilePath");//查找Webuploader配置的本地放入excel文件地址
            File dirFile = new File(uploadFinPath);
            if (!dirFile.exists()) {
                if (dirFile.mkdirs()) {
                    System.out.println("创建目录为：" + uploadFinPath + "成功");
                } else {
                    System.out.println("创建目录为：" + uploadFinPath + "失败");
                }
            }
            String allPath = uploadFinPath + File.separator + destFileName;//待生成的excel绝对路径
            File f = new File(allPath);//创建为文件
            if (f.exists())//如果存在则删除
            {
                f.delete();
            }

            mfCusImportExcel.transferTo(f);//生成excel文件
            mfCusImportExcelHis.setUploadFile(allPath);
            mfCusImportExcelHis.setId(id);
            dataMap = mfCusImportExcelFeign.excelToObj(mfCusImportExcelHis);
            dataMap.put("id", mfCusImportExcelHis.getId());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("readFlag", false);
            dataMap.put("msg", "导入文件中数据异常,请检查数据后重新导入！");
        }finally {
            if (null !=workbook){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataMap;
    }

    /**
     * 打开上传excel页面
     *
     * @param cusNo
     * @author LJW date 2017-4-14
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        CodeUtils codeUtils = new CodeUtils();
        JSONArray hisStsArray = codeUtils.getJSONArrayByKeyName("IMPORT_HIS_STS");
        model.addAttribute("hisStsArray", hisStsArray);
        model.addAttribute("cusNo", cusNo);
        model.addAttribute("query", "");
        return "/component/importexcel/MfCusImportExcel_List";
    }
    @RequestMapping(value = "/updateHis")
    @ResponseBody
    public Map<String,Object> updateHis(String id)  throws Exception{
        MfCusImportExcelHis mfCusImportExcelHis = new MfCusImportExcelHis();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            mfCusImportExcelHis.setId(id);
            mfCusImportExcelHis.setDeleteSts("1");
            mfCusImportExcelFeign.updateHis(mfCusImportExcelHis);
            dataMap.put("flag", "success");
        }catch (Exception e){
            dataMap.put("flag", "error");
        }
        return dataMap;
    }

    @RequestMapping(value = "/againDoc")
    public void againDoc(String id,String regTime)  throws Exception{
        ActionContext.initialize(request, response);
        MfCusImportExcelHis mfCusImportExcelHis = new MfCusImportExcelHis();
        mfCusImportExcelHis.setId(id);
        mfCusImportExcelHis = mfCusImportExcelFeign.getById(mfCusImportExcelHis);//查询需要重新下载的历史信息
        String url = mfCusImportExcelHis.getUrl();
       /* String upLoadFileNameExt = StringUtil.subStrRight(url, 4);//判断上传的格式
        regTime = regTime.replaceAll(" ", "").replaceAll(":", "");
        String fileName = regTime+"导入模板"+upLoadFileNameExt;*/
        upLoad(url,mfCusImportExcelHis.getImportFileName());
    }

    /**
     * 打开历史导入界面
     *
     * @author fc date 2018-11-12
     */
    @RequestMapping("/getHisList")
    @ResponseBody
    public Map<String, Object> getSelectList(Integer pageNo, Integer pageSize, String tableId, String tableType,
                                             String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JsonTableUtil jtu = new JsonTableUtil();
        Ipage ipage = this.getIpage();
        ipage.setPageSize(pageSize);
        ipage.setPageNo(pageNo);// 异步传页面翻页参数
        String htmlStr = null;
        try {
            MfCusImportExcelHis mfCusImportExcelHis = new MfCusImportExcelHis();
            mfCusImportExcelHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusImportExcelHis.setCustomSorts(ajaxData);
            mfCusImportExcelHis.setCriteriaList(mfCusImportExcelHis, ajaxData);// 我的筛选
            ipage.setParams(this.setIpageParams("mfCusImportExcelHis", mfCusImportExcelHis));
            ipage = mfCusImportExcelFeign.findByPage(ipage);
            htmlStr = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(htmlStr);
            dataMap.put("ipage", ipage);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "2");
            dataMap.put("msg", e.getMessage());
            throw new Exception(e);
        }
        return dataMap;
    }


    public void upLoad(String targetFilePath,String fileName) throws Exception {
        File file = new File(targetFilePath);
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = createworkbook(inputStream);
        if (workbook!=null) {
            String headStr = "attachment;fileName=\""+new String(fileName.getBytes("UTF-8"), "iso8859-1")+"\"";
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", headStr);
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }

    }

    public static Workbook createworkbook(InputStream inp) throws IOException, InvalidFormatException {
        if (!inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(inp)) {
            return new HSSFWorkbook(inp);
        }
        if (POIXMLDocument.hasOOXMLHeader(inp)) {
            return new XSSFWorkbook(OPCPackage.open(inp));
        }
        throw new IllegalArgumentException("你的excel版本目前poi解析不了");
    }

    /**
     * 打开历史数据导入页面
     * @param model
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadExcel")
    public String uploadExcel(Model model) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formuploadexcelbase = formService.getFormData("uploadexcelbase");
        model.addAttribute("formuploadexcelbase", formuploadexcelbase);
        model.addAttribute("query", "");
        model.addAttribute("flag", "add");
        return "/component/importexcel/MfBusImportExcel_upload";
    }

    /**
     * @描述 将临时表中历史数据导入到业务表中
     * @参数 [id]
     * @返回值 java.util.Map<java.lang.String,java.lang.Object>
     * @创建人  shenhaobing
     * @创建时间 2019/8/16
     * @修改人和其它信息
     */
    @RequestMapping(value = "/saveByTemporaryAjax")
    @ResponseBody
    public Map<String, Object> saveByTemporaryAjax(String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCusImportExcelHis mfCusImportExcelHis = new MfCusImportExcelHis();
            mfCusImportExcelHis.setId(id);
            dataMap = mfCusImportExcelFeign.saveByTemporary(mfCusImportExcelHis);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw e;
        }
        return dataMap;
    }
    /**
     * @描述 校验导入到临时表中历史数据必填、格式、重复、逻辑等
     * @参数 [id]
     * @返回值 java.util.Map<java.lang.String,java.lang.Object>
     * @创建人  shenhaobing
     * @创建时间 2019/8/16
     * @修改人和其它信息
     */
    @RequestMapping(value = "/checkByTemporaryAjax")
    @ResponseBody
    public Map<String, Object> checkByTemporaryAjax(String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCusImportExcelHis mfCusImportExcelHis = new MfCusImportExcelHis();
            mfCusImportExcelHis.setId(id);
            dataMap = mfCusImportExcelFeign.checkByTemporary(mfCusImportExcelHis);
            String checkFlag = (String) dataMap.get("checkFlag");
            //如果校验通过则更新历史数据主表为已完成校验
            if (BizPubParm.YES_NO_Y.equals(checkFlag)){
                dataMap.put("flag","success");
                dataMap.put("msg","校验数据完成！");
            }else {
                dataMap.put("flag","error");
                dataMap.put("msg","校验数据完成！导入模板中存在异常数据，可重新导入修正后的数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw e;
        }
        return dataMap;
    }
    /**
     * @描述 下载解析校验错误数据
     * @参数 [id, dataModel]
     * @返回值 void
     * @创建人  shenhaobing
     * @创建时间 2019/8/2
     * @修改人和其它信息
     */
    @RequestMapping(value = "/downloadErrorHisDataAjax")
    @ResponseBody
    public Map<String, Object> downloadErrorHisDataAjax(String id,String dataModel) throws Exception{
        //读取出文件的所有字节,并将所有字节写出给客户端
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfCusImportExcelHis mfCusImportExcelHis = new MfCusImportExcelHis();
            mfCusImportExcelHis.setId(id);
            mfCusImportExcelHis = mfCusImportExcelFeign.getById(mfCusImportExcelHis);
            if (mfCusImportExcelHis!=null){
                if (StringUtil.isNotEmpty(mfCusImportExcelHis.getErrorFileUrl())){
                    dataMap.put("id",id);
                }else {
                    YmlConfig ymlConfig = (YmlConfig) SpringUtil.getBean(YmlConfig.class);
                    String toPath = ymlConfig.getUpload().get("importErrorFilePath");//查找Webuploader配置的本地放入excel文件地址
                    String filename = "";
                    CodeUtils cu = new CodeUtils();
                    Map<String, String> cuMap = cu.getMapByKeyName("IMPORT_DATA_MODEL");
                    filename = cuMap.get(mfCusImportExcelHis.getDataModel())+"校验不通过数据";
                    toPath = toPath+File.separator+id+File.separator;
                    File dirFile = new File(toPath);
                    if (!dirFile.exists()) {
                        if (dirFile.mkdirs()) {
                            mfCusImportExcelHis = new MfCusImportExcelHis();
                            mfCusImportExcelHis.setId(id);
                            mfCusImportExcelHis.setDataModel(dataModel);
                            List<MfCusImportExcelCensus> mfCusImportExcelCensusList = mfCusImportExcelFeign.getDownData(mfCusImportExcelHis);
                            for (int i = 0; i < mfCusImportExcelCensusList.size(); i++) {
                                MfCusImportExcelCensus mfCusImportExcelCensus = mfCusImportExcelCensusList.get(i);
                                ExpExclUtil eu = new ExpExclUtil();
                                HSSFWorkbook wb = eu.expExclTableForList(mfCusImportExcelCensus.getTableId(), mfCusImportExcelCensus.getObj(),mfCusImportExcelCensus.getSheetName(),false,"");
                                FileOutputStream fileOut = new FileOutputStream(toPath +WaterIdUtil.getWaterId()+".xls");
                                wb.write(fileOut);
                                wb.close();// HSSFWorkbook关闭
                                fileOut.flush();
                                fileOut.close();
                            }
                            mfCusImportExcelHis.setErrorFileUrl(toPath);
                            mfCusImportExcelHis.setErrorFileName(filename+".xls");
                            mfCusImportExcelFeign.updateHis(mfCusImportExcelHis);

                            CopySheetUtil.copySheetToExcelForXls(toPath,toPath,filename);
                        }
                    }
                }
            }
            dataMap.put("flag","success");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return dataMap;
    }

    /**
     * @描述 打包下载历史数据导入模板
     * @参数 []
     * @返回值 org.springframework.http.ResponseEntity<byte[]>
     * @创建人  shenhaobing
     * @创建时间 2019/8/16
     * @修改人和其它信息
     */
    @RequestMapping("/getZipFileDownloadByPath")
    public ResponseEntity<byte[]> getZipFileDownloadByPath() throws Exception {
        YmlConfig ymlConfig = (YmlConfig) SpringUtil.getBean(YmlConfig.class);
        String filePath = ymlConfig.getUpload().get("importModelFilePath");
        String fileName = "系统历史数据导入模板.zip";
        ResponseEntity<byte[]> entity = this.docFeign.getZipFileDownloadByPath(filePath, fileName);
        return entity;
    }
    /**
     * @描述 根据历史数据导入编号跳转历史数据导入详情
     * @参数 [model, id]
     * @返回值 java.lang.String
     * @创建人  shenhaobing
     * @创建时间 2019/8/16
     * @修改人和其它信息
     */
    @RequestMapping(value = "/inputDetail")
    public String inputDetail(Model model,String id) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfCusImportExcelHis mfCusImportExcelHis = new MfCusImportExcelHis();
        mfCusImportExcelHis.setId(id);
        mfCusImportExcelHis = mfCusImportExcelFeign.getById(mfCusImportExcelHis);
        String hisSts = mfCusImportExcelHis.getHisSts();
        MfCusImportExcelCensus mfCusImportExcelCensus = new MfCusImportExcelCensus();
        mfCusImportExcelCensus.setHisId(id);
        List<MfCusImportExcelCensus> mfCusImportExcelCensusList = mfCusImportExcelFeign.getImportExcelCensusList(mfCusImportExcelCensus);
        model.addAttribute("hisId", mfCusImportExcelHis.getId());
        model.addAttribute("hisSts", mfCusImportExcelHis.getHisSts());
        model.addAttribute("query", "");
        model.addAttribute("flag", "detail");
        model.addAttribute("mfCusImportExcelCensusList", mfCusImportExcelCensusList);
        if (!"2".equals(hisSts)){
            return "/component/importexcel/MfBusImportExcel_upload";
        }else {
            return "/component/importexcel/MfBusImportExcel_uploadDetail";
        }
    }
    /**
     * @描述 根据历史数据导入编号导入数据统计明细
     * @参数 [model, id]
     * @返回值 java.util.Map<java.lang.String,java.lang.Object>
     * @创建人  shenhaobing
     * @创建时间 2019/8/16
     * @修改人和其它信息
     */
    @RequestMapping(value = "/getCensusListHtmlAjax")
    @ResponseBody
    public Map<String, Object> getCensusListHtmlAjax(Model model,String id) throws Exception{
        //读取出文件的所有字节,并将所有字节写出给客户端
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JsonTableUtil jtu = new JsonTableUtil();
        try {
            MfCusImportExcelCensus mfCusImportExcelCensus = new MfCusImportExcelCensus();
            mfCusImportExcelCensus.setHisId(id);
            List<MfCusImportExcelCensus> mfCusImportExcelCensusList = mfCusImportExcelFeign.getImportExcelCensusList(mfCusImportExcelCensus);
            if (mfCusImportExcelCensusList!=null&&mfCusImportExcelCensusList.size()>0){
                String htmlStr = jtu.getJsonStr("tableimportexcelcensuslist", "tableTag", mfCusImportExcelCensusList, null, true);
                dataMap.put("htmlStr",htmlStr);
                dataMap.put("flag","success");
            }else {
                dataMap.put("flag","error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return dataMap;
    }
    /**
     * @描述 根据历史数据导入编号下载校验不通过数据文件
     * @参数 [id]
     * @返回值 void
     * @创建人  shenhaobing
     * @创建时间 2019/8/16
     * @修改人和其它信息
     */
    @RequestMapping(value = "/downErrorDataFile")
    public void downErrorDataFile(String id)  throws Exception{
        ActionContext.initialize(request, response);
        MfCusImportExcelHis mfCusImportExcelHis = new MfCusImportExcelHis();
        mfCusImportExcelHis.setId(id);
        mfCusImportExcelHis = mfCusImportExcelFeign.getById(mfCusImportExcelHis);
        String url = mfCusImportExcelHis.getErrorFileUrl();
        String errorFileName = mfCusImportExcelHis.getErrorFileName();
        url = url + File.separator + errorFileName;
        upLoad(url,errorFileName);
    }
}
