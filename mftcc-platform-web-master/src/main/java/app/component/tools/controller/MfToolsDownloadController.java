package  app.component.tools.controller;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mftcc.util.WaterIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.model.entity.MfTemplateBizConfig;
import app.component.model.feign.MfTemplateBizConfigFeign;
import app.component.tools.entity.MfToolsDownload;
import app.component.tools.feign.MfToolsDownloadFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfToolsDownloadAction.java
 * Description:
 * @author:kaifa@dhcc.com.cndownloadTemplate
 * @Tue Oct 17 18:29:27 CST 2017
 **/
@Controller
@RequestMapping(value = "/mfToolsDownload")
public class MfToolsDownloadController extends BaseFormBean{
	private Logger logger = LoggerFactory.getLogger(MfToolsDownloadController.class);
	//注入MfToolsDownloadBo
	@Autowired
	private MfToolsDownloadFeign mfToolsDownloadFeign;
	@Autowired
	private MfTemplateBizConfigFeign mfTemplateBizConfigFeign;
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/*
	//全局变量
	private MfToolsDownload mfToolsDownload;
	private List<MfToolsDownload> mfToolsDownloadList;
	private String serialNo;		
	private String tableType;
	private String tableId;
	private int pageNo;
	private String query;
	private InputStream inputStream;  
	private String fileName;  
	//异步参数
	private String ajaxData;
	private Map<String,Object> dataMap;
	//表单变量
	private FormData formtoolsBase;
	private FormData formtoolsDetail;
	private FormService formService = new FormService();
	
	public MfToolsDownloadController(){
		query = "";
	}
	*/
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/tools/MfToolsDownload_List";
	}
	/***
	 * 列表数据查询
	 * @param pageSize 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType, Integer pageSize) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfToolsDownload mfToolsDownload = new MfToolsDownload();
		try {
			mfToolsDownload.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfToolsDownload.setCriteriaList(mfToolsDownload, ajaxData);//我的筛选
			//this.getRoleConditions(mfToolsDownload,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfToolsDownload",mfToolsDownload));
			//自定义查询Bo方法
			ipage = mfToolsDownloadFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String serialNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formtoolsDetail = formService.getFormData("toolsDetail");
		MfToolsDownload mfToolsDownload = new MfToolsDownload();
		mfToolsDownload.setSerialNo(serialNo);
		mfToolsDownload = mfToolsDownloadFeign.getById(mfToolsDownload);
		getObjValue(formtoolsDetail, mfToolsDownload,formData);
		if(mfToolsDownload!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	
	/**
	 * 下载工具
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadTool")
	public ResponseEntity<byte[]> downloadTool(String serialNo) throws Exception{
		InputStream inputStream = null;
		ResponseEntity<byte[]> entity = null;
		try {
			//读取出文件的所有字节,并将所有字节写出给客户端
			MfToolsDownload mfToolsDownload = new MfToolsDownload();
			mfToolsDownload.setSerialNo(serialNo);
			mfToolsDownload = mfToolsDownloadFeign.getById(mfToolsDownload);
			String docPath = PropertiesUtil.getUploadProperty("uploadFilePath");
			docPath += File.separator+"downloadCenter"+File.separator+mfToolsDownload.getAddress();
			File file = new File(docPath);
			if (!file.exists()) {
				return null;
			} else {
				inputStream = new BufferedInputStream(new FileInputStream(file));
			}
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attchement;filename=" + new String(file.getName().getBytes("utf-8"), "ISO8859-1"));
			HttpStatus statusCode = HttpStatus.OK;
			entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
			return entity;
	}
	 private static byte[] toByteArray(InputStream in) throws IOException {
	        ByteArrayOutputStream out=new ByteArrayOutputStream();
	        byte[] buffer=new byte[1024*4];
	        int n=0;
	        while ( (n=in.read(buffer)) !=-1) {
	            out.write(buffer,0,n);
	        }
	        return out.toByteArray();
	    }
	/**
	 * 下载工具
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadToolAjax")
	public Map<String, Object> downloadToolAjax(String serialNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		BufferedInputStream inputStream = null;
		try {
			MfToolsDownload mfToolsDownload = new MfToolsDownload();
			mfToolsDownload.setSerialNo(serialNo);
			mfToolsDownload = mfToolsDownloadFeign.getById(mfToolsDownload);

			String path = request.getServletContext().getRealPath(mfToolsDownload.getAddress());

			inputStream = new BufferedInputStream(new FileInputStream(path));
			File tempFile = new File(path.trim());
			String tempFileName = tempFile.getName();
			String fileName = new String(tempFileName.getBytes(), "ISO8859-1");

//			String path = contextPath + File.separator + mfToolsDownload.getAddress();
//			dataMap.put("flag", "success");
//			dataMap.put("path", path);
//			dataMap.put("msg",MessageEnum.SUCCEED_DOWNLOAD_TOOL.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
//			dataMap.put("flag", "error");
//			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
			throw e;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述： 下载系统中的模板文档(只需要把文档的唯一编号传过来就可以)
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2018-2-10 上午10:27:44
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadTemplate")
	public ResponseEntity<byte[]> downloadTemplate(String ajaxData) throws Exception{
		InputStream inputStream = null;
		ResponseEntity<byte[]> entity = null;
		try {
			//读取出文件的所有字节,并将所有字节写出给客户端
			ActionContext.initialize(request, response);
			String filePathName = "";
			MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
			mfTemplateBizConfig.setTemplateBizConfigId(ajaxData);
			MfTemplateBizConfig templateConfig = mfTemplateBizConfigFeign.getById(mfTemplateBizConfig);
			String templateName = templateConfig.getTemplateNameZh();
			String templateNameEh = templateConfig.getTemplateNameEn();
			String suffix = templateNameEh.substring(templateNameEh.lastIndexOf("."));
			if (StringUtil.isNotEmpty(templateConfig.getDocFileName())) {
				filePathName = templateConfig.getDocFilePath() + templateConfig.getDocFileName();
				suffix = templateConfig.getDocFileName().substring(templateConfig.getDocFileName().lastIndexOf("."));
			} else {
				//文件保存路经
				String filePath = PropertiesUtil.getPageOfficeConfigProperty("office_sys_template");
				if (StringUtil.isEmpty(filePath)) {
					logger.error("pageOffice路径office_sys_template没有配置");
				}
				filePathName = filePath + templateConfig.getTemplateNameEn();
			}
			templateName = templateName + suffix;
			File downLoadFile = new File(filePathName.trim());
			if (!downLoadFile.exists()) {
				logger.info("模板文档不存在,路径:" + filePathName);
				return null;
			} else {
				inputStream = new BufferedInputStream(new FileInputStream(downLoadFile));
			}
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attchement;filename=" + new String(templateName.getBytes("utf-8"), "ISO8859-1"));
			HttpStatus statusCode = HttpStatus.OK;
			entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return entity;
	}

	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String serialNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formtoolsBase = formService.getFormData("toolsBase");
		 getFormValue(formtoolsBase);
		 MfToolsDownload mfToolsDownload = new MfToolsDownload();
		mfToolsDownload.setSerialNo(serialNo);
		 mfToolsDownload = mfToolsDownloadFeign.getById(mfToolsDownload);
		 getObjValue(formtoolsBase, mfToolsDownload);
		 model.addAttribute("formtoolsBase", formtoolsBase);
		 model.addAttribute("query", "");
		return "/component/tools/MfToolsDownload_Detail";
	}
	
	
}
