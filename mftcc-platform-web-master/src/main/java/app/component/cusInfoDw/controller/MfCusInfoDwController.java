package app.component.cusInfoDw.controller;

import app.component.cusInfoDw.entity.MfCusInfoDw;
import app.component.cusInfoDw.feign.MfCusInfoDwFeign;
import app.component.doc.feign.DocFeign;
import app.component.pact.entity.MfBusPact;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

@Controller
@RequestMapping("/mfCusInfoDw")
public class MfCusInfoDwController extends BaseFormBean {
    private Logger logger = LoggerFactory.getLogger(MfCusInfoDwController.class);
    // 注入业务层
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCusInfoDwFeign mfCusInfoDwFeign;
    @Autowired
    private DocFeign docFeign;


    /**
     * 方法描述： 跳转客户信息下载页面
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-4-9 下午19:00:30
     */
    @RequestMapping("/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/cusInfoDw/MfCusInfoDw";
    }


    /**
     * 方法描述： 跳转客户信息查询页面
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-4-9 下午19:00:30
     */
    @RequestMapping(value = "/queryCusInfoInit")
    public String queryCusInfoInit(Model model, String ajaxDat) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        String formId = "queryCusInfo";
        FormData formqueryCusInfo = formService.getFormData(formId);
        getFormValue(formqueryCusInfo);
        model.addAttribute("formqueryCusInfo", formqueryCusInfo);
        model.addAttribute("query", "");
        return "/component/cusInfoDw/queryCusInfo";
    }

    /**
     * 方法描述： 根据客户号选择合同
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-4-9 下午19:00:30
     */
    @RequestMapping(value = "/selectPactNo")
    @ResponseBody
    public Map<String, Object> selectPactNo(int pageNo, String ajaxData, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<>();
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setCustomQuery(ajaxData);
        try {
            mfBusPact.setCusNo(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);
            ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
            ipage = mfCusInfoDwFeign.findByPage(ipage);
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
     * 方法描述： 查询客户信息生成txt文件
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-4-9 下午19:00:30
     */
    @RequestMapping(value = "/queryAndDw")
    @ResponseBody
    public Map<String, Object> queryAndDw(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, String> paramMap = new HashMap<>();
        try {
            FormData formpqueryCusInfo = formService.getFormData("queryCusInfo");
            getFormValue(formpqueryCusInfo, getMapByJson(ajaxData));
            //非空检验
            if (this.validateFormData(formpqueryCusInfo)) {
                MfCusInfoDw mfCusInfoDw=new MfCusInfoDw();
                setObjValue(formpqueryCusInfo, mfCusInfoDw);
                mfCusInfoDw= mfCusInfoDwFeign.queryAndDw(mfCusInfoDw);
                String filePath = mfCusInfoDw.getStoragePage();
                String fileName =mfCusInfoDw.getFileName();
                dataMap.put("filePath", filePath);
                dataMap.put("fileName", fileName);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("下载"));
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("清理"));
            throw e;
        }
        return dataMap;
    }


    /**
     * 方法描述： 下载客户信息
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-4-9 下午19:00:30
     */
    @RequestMapping("/cusInfoDownload")
    @ResponseBody
    public void getFileDownload_new(String filePath, String fileName) throws Exception {
        FileInputStream inputStream = null;
        OutputStream stream = null;
        try {
            File file = new File(filePath);
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            int length = inputStream.read(data);
            //inputStream.close();
            //设置响应头和客户端保存文件名
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            //stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (stream != null) {
                stream.close();
            }
        }
    }


    /**
     * 方法描述：客户信息下载列表
     *
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-4-9 下午19:00:30
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                              String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusInfoDw mfCusInfoDw = new MfCusInfoDw();
        try {
            mfCusInfoDw.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusInfoDw.setCustomSorts(ajaxData);
            mfCusInfoDw.setCriteriaList(mfCusInfoDw, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);// 异步传页面翻页尺寸

            ipage.setParams(this.setIpageParams("mfCusInfoDw", mfCusInfoDw));
            // 自定义查询Feign方法
            ipage = mfCusInfoDwFeign.findByPageList(ipage);
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
}
