package app.component.doc.controller;

import app.base.User;
import app.base.cache.entity.CacheBase;
import app.base.cacheinterface.BusiCacheInterface;
import app.base.imageTools.ImgCompress;
import app.component.app.entity.MfBusAppKind;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.ZipCompressing;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.CusFeign;
import app.component.cus.feign.MfChangeInfoRecordFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.doc.entity.DocManage;
import app.component.doc.entity.DocTypeConfig;
import app.component.doc.feign.DocFeign;
import app.component.doc.feign.FileUtilFeign;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.PactFeign;
import app.tech.upload.FeignSpringFormEncoder;
import app.tech.upload.UploadUtil;
import app.util.JsonStrHandling;
import app.util.ZipTool;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import feign.Feign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

@Controller
@RequestMapping("/docUpLoad")
public class DocUpLoadController extends BaseFormBean{
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	@Autowired
	private DocFeign docFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private BusiCacheInterface busiCacheInterface;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CusFeign cusFeign;
	@Autowired
	private PactFeign  pactFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfChangeInfoRecordFeign mfChangeInfoRecordFeign;

	
	private FormData form;
	private FormService formService = new FormService();
	
	private static final long serialVersionUID = 9196454891709523438L;
	// 验证字符串是否为正确路径名的正则表达式
	private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
	
	private static int fileNameLength = 150;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	
	private String getHttpUrl() {
		List<ServiceInstance> list = discoveryClient.getInstances("mftcc-platform-fileService");
		String httpurl = "http://";
		if (list != null && list.size() > 0) {
			ServiceInstance instance = list.get(0);
			String url = instance.getHost();
			int port = instance.getPort();
			httpurl = httpurl+url + ":" + port;
		}
		return httpurl;
	}
	/**
	 * 上传到service
	 * @param relNo
	 * @param docType
	 * @param file
	 * @param docBizNo
	 * @param scNo
	 * @param docSplitNo
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFileToService",produces="application/json; charset=utf-8")
	@ResponseBody
	public Map uploadFileToService(String relNo,String docType,@RequestParam(value="upload", required=false) MultipartFile file,String docBizNo,String scNo,String docSplitNo,String cusNo) throws Exception {
		String httpurl = getHttpUrl();
		Gson gson =new Gson();
		DocManage docManage = new DocManage();
		docManage.setCurrentSessionRegNo(User.getRegNo(request));
		docManage.setCurrentSessionRegName(User.getRegName(request));
		docManage.setCurrentSessionOrgName(User.getOrgName(request));
		docManage.setCurrentSessionOrgNo(User.getOrgNo(request));
		String uploadContentType = file.getContentType();
		docManage.setCompressPath(uploadContentType);
		docManage.setCusNo(cusNo);
		FileUtilFeign fileFeign = Feign.builder().encoder(new FeignSpringFormEncoder()).target(FileUtilFeign.class, httpurl);
		String result= fileFeign.uploadFileToService( relNo, docType,  file, docBizNo, scNo, docSplitNo, cusNo,file.getOriginalFilename(),docManage);
		Map<String,Object> resultMap = gson.fromJson(result, new TypeToken<Map<String,Object>>(){}.getType());
//		//要件上传留痕
//		this.docAddChangeRecord(result,User.getRegNo(request));
		return resultMap;
	}

	/**
	 * 上传到service
	 * @param relNo
	 * @param docType
	 * @param files
	 * @param docBizNo
	 * @param scNo
	 * @param docSplitNo
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFileToServiceByForm",produces="application/json;charset=utf-8")
	@ResponseBody
	public Map uploadFileToServiceByForm(String relNo,String docType,@RequestParam(value="upload", required=false) MultipartFile[] files,String docBizNo,String scNo,String docSplitNo,String cusNo) throws Exception {
		String httpurl = getHttpUrl();
		Gson gson =new Gson();
		DocManage docManage = new DocManage();
		docManage.setCurrentSessionRegNo(User.getRegNo(request));
 		docManage.setCurrentSessionRegName(User.getRegName(request));
 		docManage.setCurrentSessionOrgName(User.getOrgName(request));
 		docManage.setCurrentSessionOrgNo(User.getOrgNo(request));
		Map<String,Object> resultMap = new HashMap<>();
		List<Map<String,Object>> resultList = new ArrayList<>();
		for (MultipartFile file:files){
			String uploadContentType = file.getContentType();
			docManage.setCompressPath(uploadContentType);
			FileUtilFeign fileFeign = Feign.builder().encoder(new FeignSpringFormEncoder()).target(FileUtilFeign.class, httpurl);
			String result= fileFeign.uploadFileToService( relNo, docType,  file, docBizNo, scNo, docSplitNo, cusNo,file.getOriginalFilename(),docManage);
			resultList.add(gson.fromJson(result, new TypeToken<Map<String,Object>>(){}.getType()));
			//要件上传留痕
			//this.docAddChangeRecord(result,User.getRegNo(request));
		}
		resultMap.put("resultList",resultList);

		return resultMap;
	}

	/**
	 * 新增要件时，记录变更记录
	 * @param result
	 */
	public void docAddChangeRecord(String result,String opNo){
		Gson gson =new Gson();
		Map<String,Object> updateMap = gson.fromJson(result, new TypeToken<Map<String,Object>>(){}.getType());
		if(updateMap!=null&&(Boolean)updateMap.get("flag")){
			if(updateMap.containsKey("docManage")){
				DocManage docManage  =JsonStrHandling.handlingStrToBean(updateMap.get("docManage"),DocManage.class);
				if(docManage!=null){
					try {
						mfChangeInfoRecordFeign.handleUpdateDoc(docManage.getCusNo(),docManage.getDocSplitNo(),docManage.getDocBizNo(),docManage.getDocName(),opNo,"0");
					} catch (Exception e) {
						logger.error("新增要件留痕功能出现问题");
						e.printStackTrace();
					}
				}
			}



		}
	}
	/**
	 * 新增要件时，记录变更记录
	 * @param
	 */
	public void docDeleteChangeRecord(DocManage docManage,String opNo){
		if(docManage!=null){
			try {
				mfChangeInfoRecordFeign.handleUpdateDoc(docManage.getCusNo(),docManage.getDocSplitNo(),docManage.getDocBizNo(),docManage.getDocName(),opNo,"2");
			} catch (Exception e) {
				logger.error("新增要件留痕功能出现问题");
				e.printStackTrace();
			}
		}
	}
	/**
	 * 上传到web
	 * @param relNo
	 * @param docType
	 * @param file
	 * @param docBizNo
	 * @param scNo
	 * @param docSplitNo
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFile")
	@ResponseBody
	public Map<String,Object> uploadFile(String relNo,String docType,@RequestParam("upload") MultipartFile file,String docBizNo,String scNo,String docSplitNo,String cusNo) throws Exception {
		Map<String,Object> dataMap = new HashMap<String, Object>();
		String fileName = file.getOriginalFilename();
		String uploadContentType = file.getContentType();
		//添加文件上传格式验证，过滤可能威胁系统安全非法后缀
		if(fileName.endsWith(".exe")||fileName.endsWith(".jsp")||fileName.endsWith(".bat")
				||fileName.endsWith(".dll")||fileName.endsWith(".com")||fileName.endsWith(".sh")||fileName.endsWith(".py")){
			dataMap.put("flag", false);
			dataMap.put("msg", MessageEnum.ERROR_UPLOAD_FILE_TYPE.getMessage("合法"));
			logger.info("上传文件格式不合法,文件名："+fileName);
			return dataMap;
		}
		if (length(fileName) > fileNameLength) {
			dataMap.put("flag", false);
			dataMap.put("msg", MessageEnum.ERROR_FILE_NAME_TOOLONG.getMessage());
			logger.info("文件名过长，不能超过"+fileNameLength+"，实际长度为："+length(fileName));
			return dataMap;
		}
		StringBuilder path = new StringBuilder();
		StringBuilder pathNew = new StringBuilder();
		path.append(UploadUtil.getFileUploadPath());
		UUID uuid = UUID.randomUUID();
		path.append(File.separator).append(relNo).append(File.separator).append(docType);
		path.append(File.separator).append(uuid.toString().replaceAll("-", ""));
		pathNew.append(File.separator).append(relNo).append(File.separator).append(docType);
		pathNew.append(File.separator).append(uuid.toString().replaceAll("-", ""));
		String realpath = path.toString();
		String realpathNew = pathNew.toString();
		DocManage docManage = new DocManage();
		if (path.indexOf("../") > -1 || path.indexOf("./") > -1 || path.indexOf("..%2F") > -1 || path.indexOf("..%5C") > -1) {
			dataMap.put("flag", false);
			dataMap.put("msg", "文件上传路径不合法！");
			logger.info("文件上传路径不合法！path:"+path.toString());
			return dataMap;
		}

		InputStream is = null;
//		OutputStream os = null;
		try {
			File f = new File(realpath);
			if (!f.exists()) {
				f.mkdirs();
			}
			is = file.getInputStream();
//			os = new FileOutputStream(new File(realpath, fileName));
			docManage.setDocSize(this.bytes2kb(is.available()));
//			byte[] buffer = new byte[1024];
//			int length = 0;
//			while (-1 != (length = is.read(buffer, 0, buffer.length))) {
//				os.write(buffer);
//			}
			if(uploadContentType.startsWith("image")){
				
		        
				 ImgCompress imgCom = new ImgCompress(file.getInputStream(),realpath,fileName);  
				 File tempFile = imgCom.resizeFix(105, 75);
				 docManage.setCompressPath(tempFile.getPath());
				// String base64Str = Base64Util.getImageStr(tempFile);
				// docManage.setImgBase64Blob(base64Str.getBytes());
				// docManage.setImgBase64(base64Str);
			}
			file.transferTo(new File(realpath,fileName));
			docManage.setRegDate(User.getSysDate(request));
			docManage.setRegNo(User.getRegNo(request));
			docManage.setOrgNo(User.getOrgNo(request));
			docManage.setDocName(fileName);
			docManage.setDocAddr(realpathNew + File.separator + fileName);
			docManage.setDocBizNo(docBizNo);
			docManage.setScNo(scNo);
			docManage.setDocSplitNo(docSplitNo);
			docManage.setDocEncryptAddr(docManage.getDocAddr());
			docManage.setDocType(docType);
			docManage.setRelNo(relNo);
			docManage.setCusNo(cusNo);
			docManage = this.docFeign.insertDocManage(docManage);
			dataMap.put("path", path + "/" + fileName);
			dataMap.put("docType", docType);
			dataMap.put("docManage", docManage);
			String uploadType = docManage.getUploadType();
			if(StringUtil.isNotEmpty(uploadType)){
				String path1 = UploadUtil.getFileUploadPathByType(uploadType);
				//根据路径类型获取上传的前缀
				docManage.setDocAddr(path1+docManage.getDocAddr());
				docManage.setDocEncryptAddr(path1+docManage.getDocEncryptAddr());
			}
			dataMap.put("flag", true);
			dataMap.put("msg", MessageEnum.SUCCEED_UPLOAD.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("文件上传失败：", e.getMessage());
			throw e;
		} finally {
//			if (os != null) {
//				try {
//					os.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return dataMap;
	}
	@RequestMapping("/delFile")
	@ResponseBody
	public Map<String,Object> delFile(String docNo,String docBizNo) throws Exception{
		Map<String,Object> dataMap = new HashMap<String, Object>();
		DocManage docManage = new DocManage();
		docManage.setDocNo(docNo);
		docManage.setDocBizNo(docBizNo);
		docManage = this.docFeign.getByIdDocManage(docManage);
		String sPath = docManage.getDocEncryptAddr();
		String cPath = docManage.getCompressPath();//缩略图
		if(cPath!=null&&"".equals(cPath)){
			File cfile = new File(cPath);
			if (!cfile.exists()) { // 不存在返回 false
			} else {
				// 判断是否为文件
				if (cfile.isFile()) { // 为文件时调用删除文件方法
					deleteFile(cPath);
				} else { // 为目录时调用删除目录方法
					deleteDirectory(cPath);
				}
			}
		}
		try {
			File file = new File(sPath);
			// 判断目录或文件是否存在
			if (!file.exists()) { // 不存在返回 false
				this.docFeign.deleteDocManage(docManage);
				dataMap.put("msg",MessageEnum.NO_FILE.getMessage(""));
			} else {
				// 判断是否为文件
				if (file.isFile()) { // 为文件时调用删除文件方法
					deleteFile(sPath);
				} else { // 为目录时调用删除目录方法
					deleteDirectory(sPath);
				}
				this.docFeign.deleteDocManage(docManage);
				//要件删除时，记录变更记录
				//docDeleteChangeRecord(docManage,User.getRegNo(request));
				dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}
	

	// 下载
	@RequestMapping("/getFileDownload")
	public ResponseEntity<byte[]> getFileDownload(String docNo,String docBizNo) throws Exception {
		  ResponseEntity<byte[]> entity = docFeign.getFileDownload(docNo, docBizNo);
			return entity;
	}
	
	// 下载
	@RequestMapping("/getFileByFilePath")
	public ResponseEntity<byte[]> getFileByFilePath(String filePath) throws Exception {
		  ResponseEntity<byte[]> entity = docFeign.getFileByFilePath(filePath);
			return entity;
	}
//	public String viewFileAjax() throws Exception {
//		dataMap = new HashMap<String, Object>();
//		String sPath = docAddr;
//		Office2PDF op = new Office2PDF();
//		String viewPath = op.viewToPdf(sPath, docBizNo+"_"+docNo,false);
//		if(viewPath == null){
//			dataMap.put("flag", "error");
//			dataMap.put("msg", "文件不存在，或者不支持的文档类型！");
//		}else{
//			String fileName = viewPath.substring(viewPath.lastIndexOf(File.separator)+1);
//			dataMap.put("flag", "success");
//			dataMap.put("msg", "转换成功！");
//			dataMap.put("viewPath", fileName);
//		}
//		return SUCCESS;
//	}
	@RequestMapping("/viewFileAjax")
	@ResponseBody
	public Map<String,Object> viewFileAjax(String docAddr) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			Map<String, String> poCnt = new HashMap<String, String>();
			poCnt.put("saveBtn", "0");// 保存按钮 0无1有
			poCnt.put("readOnly", "1");// 只读 1只读 0非只读
			String sPath = docAddr;
			if(StringUtil.isNotEmpty(sPath)){
				File tempFile = new File(sPath);
				String fileName = tempFile.getName();
				poCnt.put("filePath", tempFile.getParent()+File.separator);// 打开文件路径 
				poCnt.put("fileName", fileName);// 打开文件名
				poCnt.put("fileType", "0");// 打开属性 0自动判断1 doc 2excel 3ppt
				poCnt.put("printBtn", "1");// 打印按钮 0无1有
				dataMap.put("poCnt", gson.toJson(poCnt));
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", "文件不存在，或者不支持的文档类型！");
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "文件预览失败："+e.getMessage());
		}
		return dataMap;
	}
	// 多文件打包下载(要件)
	@RequestMapping("/getZipFileDownloadForDocType")
	public ResponseEntity<byte[]> getZipFileDownloadForDocType(String ajaxData,String cusNo,String docType,String docTypeName) throws Exception {
		ResponseEntity<byte[]> entity = this.docFeign.getZipFileDownloadForDocType(ajaxData, cusNo,docType,docTypeName);
		return entity;
		
	}
	
	// 多文件打包下载(模板) 废弃了 20200608
	@RequestMapping("/getZipFileDownloadForTemplet")
	public ResponseEntity<byte[]> getZipFileDownloadForTemplet(String ajaxData,String appId,String cusNo) throws Exception {
		ZipTool zt = new ZipTool();
		//zu.addFile(filepath);//添加文件
		if(StringUtil.isNotEmpty(ajaxData)){
			MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
			templateBizConfig.setTemBizNo(appId);
			List<MfTemplateBizConfig> templateList = this.docFeign.getBizConfigListFilterRepeat(templateBizConfig);
			for(MfTemplateBizConfig template:templateList){
				if(StringUtil.isNotEmpty(template.getDocFileName())){
					//根据路径类型获取上传的前缀
					zt.addFile(template.getDocFilePath()+template.getDocFileName());
				}
			}
		}
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		//mfCusCustomer = this.docFeign.getByIdMfCusCustomer(mfCusCustomer);
		String fileName = mfCusCustomer.getCusName()+"_"+WaterIdUtil.getWaterId()+".zip";
		fileName = new String(fileName.getBytes(),"ISO8859-1");
		InputStream inputStream = new ByteArrayInputStream(zt.getOutputStream());
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attchement;filename="+fileName);
	    HttpStatus statusCode = HttpStatus.OK;
	    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		return entity;
	}
	
	
    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*4];
        int n=0;
        while ( (n=in.read(buffer)) !=-1) {
            out.write(buffer,0,n);
        }
        return out.toByteArray();
    }
    
	@RequestMapping("/getZipFileDownload")
	public ResponseEntity<byte[]>  getZipFileDownload(String docBizNo,String docSplitNo,String cusName,String docTypeName,String docSplitName) throws Exception {
		List<File> files = new ArrayList<File>();
		StringBuilder path = new StringBuilder();
		path.append(UploadUtil.getFileUploadPath());
		path.append(File.separator).append(docBizNo+"-"+docSplitNo+".zip");
		String sPath = path.toString();
		ZipCompressing.zipFiles(files,sPath);
		String fileName= cusName+"-"+docTypeName+"-"+docSplitName+".zip";
		File file = new File(sPath);
		InputStream inputStream = null;
		if (!file.exists()) { // 不存在返回 false
			System.err.println("Can not find a Java.io.InputStream with the name [inputStream] in the invocation stack. Check the inputName tag specified for this action.检查action中文件下载路径是否正确.");  
		} else {
			inputStream = new BufferedInputStream(new FileInputStream(sPath));
		}
		 //设置文件输出类型
        response.setContentType("application/octet-stream"); 
        response.setHeader("Content-disposition", "attachment; filename=" 
                + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.add("Content-Disposition", "attchement;filename="+fileName);
	    HttpStatus statusCode = HttpStatus.OK;
	    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		return entity;
        
	}
//	// 打包下载,完成后删除（未实现）
//	public String getZipFileDownloadAndDel() throws Exception {
//		List<File> files = new ArrayList<File>();
//		StringBuilder path = new StringBuilder();
//		path.append(UploadUtil.getFileUploadPath());
//		path.append(File.separator).append(docBizNo+"-"+docSplitNo+".zip");
//		String sPath = path.toString();
//		ZipCompressing.zipFiles(files,sPath);
//		String fileName= cusName+"-"+docTypeName+"-"+docSplitName+".zip";
//		File file = new File(sPath);
//		if (!file.exists()) { // 不存在返回 false
//			System.err.println("Can not find a Java.io.InputStream with the name [inputStream] in the invocation stack. Check the inputName tag specified for this action.检查action中文件下载路径是否正确.");  
//		} else {
//			inputStream = new BufferedInputStream(new FileInputStream(sPath));
//		}
//		return SUCCESS;
//	}
//	 
	/**
	 * @author czk
	 * @Description: 根据路径进行下载
	 * date 2016-11-24
	 */
	@RequestMapping("/getFileDownload_new")
	@ResponseBody
	public void getFileDownload_new(String path) throws Exception {
	//InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
		FileInputStream inputStream = null;
		try{

			String cusNo = request.getParameter("cusNo");
			String downTyppe = request.getParameter("downType");
			String cusName ="";
			String cusBaseType ="";
			if(StringUtil.isNotEmpty(cusNo)){
				MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
				 cusName = mfCusCustomer.getCusName();
				 cusBaseType = mfCusCustomer.getCusBaseType();
				if (BizPubParm.CUS_TYPE_CORP.equals(cusBaseType)) {// 企业
					cusBaseType = "_企业";
				} else if (BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)) {// 个人
					cusBaseType = "_个人";
				}else {
				}
			}
			String fileName = "";
			if ("1".equals(downTyppe)) {
				fileName = new String(cusName + cusBaseType + "_资产负债表模板.xls");
			} else if ("2".equals(downTyppe)) {
				fileName = new String(cusName + cusBaseType + "_利润分配表模板.xls");
			} else if ("3".equals(downTyppe)) {
				fileName = new String(cusName + cusBaseType + "_现金流量表模板.xls");
			} else if ("4".equals(downTyppe)) {
				fileName = new String(cusName + cusBaseType + "_全部模板.xls");
			} else if("subjectBal".equals(downTyppe)){
				fileName = new String(cusName + cusBaseType + "_余额科目表.xls");
			}else if("acountBill".equals(downTyppe)){
				fileName = new String(cusName + cusBaseType + "模板-应收账款质押清单.xlsx");
			}
			else {
				fileName = new String(cusName + cusBaseType + "finTemplate.xls");
			}
			File file = new File(path);
			inputStream = new FileInputStream(file);
			byte[] data = new byte[(int)file.length()];
			int length = inputStream.read(data);
			//inputStream.close();
			//设置响应头和客户端保存文件名
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");

			//response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			//    response.setContentType("image/png");

			OutputStream stream = response.getOutputStream();
			stream.write(data);
			stream.flush();
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		finally {
			if(inputStream != null)
			{
				inputStream.close();
			}
		}
//	return void;
}



	/**
	 * @author czk
	 * @Description: 根据路径进行资料下载
	 * date 2016-11-24
	 */
	@RequestMapping("/getFileDownload_ziliao")
	@ResponseBody
	public ResponseEntity<byte[]> getFileDownload_ziliao() throws Exception {
	
	String uploadFinModelFilePath = PropertiesUtil.getUploadProperty("uploadFinModelFilePath");
	
	File file = new File(uploadFinModelFilePath+File.separator+"业务资料包.zip");  
	FileInputStream inputStream = new FileInputStream(file);  
  //设置响应头和客户端保存文件名
   /* response.setCharacterEncoding("utf-8");
    response.setContentType("multipart/form-data");
    String fileName = new String(("业务资料包.zip").getBytes(), "ISO8859-1");
    response.setHeader("Content-Disposition", "attachment;fileName" +fileName);*/
//    response.setContentType("image/png");  
    String fileName = new String(("业务资料包.zip").getBytes(), "ISO8859-1");
    HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	headers.add("Content-Disposition", "attchement;filename=" + fileName);
	HttpStatus statusCode = HttpStatus.OK; 
	ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
	return entity;
//	return void;
}
//	
//	/**
//	 * @Description: 富友模板下载
//	 * @return
//	 * @throws Exception
//	 * @author: 李伟
//	 * @date: 2017-8-24 下午6:42:52
//	 */
//	public String getFileDownload_fuyiou() throws Exception {
//		inputStream = new BufferedInputStream(new FileInputStream(path));
//		if("1".equals(fuyiouType)){
//			fileName = new String(("富友代付.xls").getBytes(),"ISO8859-1");
//		}else{
//			fileName = new String(("富友代收.xls").getBytes(),"ISO8859-1");
//		}
//		return SUCCESS;
//	}
//	
	/**
	 * @Description:模板下载 
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-9-23 上午11:32:12
	 */
	@RequestMapping("/fileDownload_bus")
	public ResponseEntity<byte[]> getFileDownload_bus(String path,String modelFlag,String busExportType) throws Exception {
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
		String fileName = null;
		if("1".equals(modelFlag)){//业务导出
			if("1".equals(busExportType)){//还款计划
				 String time = DateUtil.getDateTime().replaceAll("[[\\s-:punct:]]", "");
				 fileName = new String((time+"还款计划.xls").getBytes(),"ISO8859-1");
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.add("Content-Disposition", "attchement;filename=" + fileName);
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		return entity;
	}
	
	 //form赋值
	@RequestMapping("/viewIframe")
	public String viewIframe(String formId,String docSplitNo,String relNo,String cusNo,String appId,String pactId,Model model) throws Exception{
		ActionContext.initialize(request,response);
		String query="query";
		
		if(!"".equals(formId)&&formId!=null&&!"undefined".equals(formId)){
			//根据docSplitNo获取文档配置的母版表单编号
			DocTypeConfig docTypeConfig = new DocTypeConfig();
			docTypeConfig.setDocSplitNo(docSplitNo);
			docTypeConfig = this.docFeign.getByIdDocTypeConfig(docTypeConfig);
			
			form=formService.getFormData(formId);
			if(form.getFormId() == null){//如果子表单不存在，使用母版表单
				logger.error("要件预览的表单"+formId+".xml文件不存在");
				form=formService.getFormData(docTypeConfig.getBaseFormNo());
			}
			if(form.getFormId() != null){
				Map<String, Object> parmMap = new HashMap<String,Object>();
				parmMap.put("cusNo", relNo);
				parmMap.put("appId", relNo);
				parmMap.put("pactId", relNo);
				if(StringUtil.isNotEmpty(cusNo)){
					parmMap.put("cusNo", cusNo);
				}
				if(StringUtil.isNotEmpty(appId)){
					parmMap.put("appId", appId);
					if(StringUtil.isEmpty(pactId)){
						MfBusPact mfBusPact=this.pactFeign.getByAppIdMfBusPact(appId);
						if(mfBusPact!=null){
							pactId=mfBusPact.getPactId();
						}
					}
				}
				if(StringUtil.isNotEmpty(pactId)){
					parmMap.put("pactId", pactId);
				}
				parmMap.put("pledgeNo", relNo);
				Gson gson = new Gson();
				parmMap.put("docTypeConfig", gson.toJson(docTypeConfig));
				Object obj = this.docFeign.getDocObject(parmMap);
				if(obj!=null){
					//开始处理申请和合同的特殊字段     -- 申请和合同的risk_item表分别添加一个app_id 根据申请号就行处理
					Map<String, Object> resMap = (Map<String, Object>) obj;
					if(resMap.get("appId")!=null && StringUtil.isNotEmpty(resMap.get("appId").toString())){
						//处理利率的展示
						
						MfBusAppKind mfBusAppKind = null;
						mfBusAppKind = new MfBusAppKind();
						mfBusAppKind.setAppId(resMap.get("appId").toString());
						mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
						Object fincRateObj = resMap.get("fincRate");
						if (fincRateObj != null && !fincRateObj.toString().isEmpty()) {
							double fincRate = Double.parseDouble(fincRateObj.toString());
							double showRateMethod = MathExtend.showRateMethod(mfBusAppKind.getRateType(), fincRate, Integer.parseInt(mfBusAppKind.getYearDays()),Integer.parseInt(mfBusAppKind.getRateDecimalDigits()));
							resMap.put("fincRate", showRateMethod);
						}
						
						getObjValue(form, resMap);
						//处理利率的单位
						String rateUnit = null;
						if(mfBusAppKind !=null){
							Collection<Object> list = this.busiCacheInterface.getParmDic("RATE_TYPE");
							Iterator<Object> it = list.iterator();
							while(it.hasNext()){
								ParmDic parmDic = (ParmDic) it.next();
								if(mfBusAppKind.getRateType().equals(parmDic.getOptCode())){
									rateUnit= parmDic.getOptName();
									break;
								}
							}
//							String rateUnit= "";
							this.changeFormProperty(form, "fincRate", "unit", rateUnit);
						}
					}else{
						getObjValue(form, obj);
					}
				}
			}
		}
		model.addAttribute("query", query);
		model.addAttribute("form", form);
		
		return "component/doc/webUpload/shenpi";
	}
	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			flag = file.delete();
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
                    break;
                }
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
                    break;
                }
			}
		}
		if (!flag) {
            return false;
        }
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	//查看缩略图
		@RequestMapping("/viewImage")
		public ResponseEntity<byte[]> viewImage(String docNo,String docBizNo) throws Exception 
		{
//			DocManage docManage = new DocManage();
//			docManage.setDocNo(docNo);
//			docManage.setDocBizNo(docBizNo);
//			docManage = this.docFeign.getByIdDocManage(docManage);
//			String sPath = docManage.getDocEncryptAddr();
//			InputStream inputStream = ImageUtil.getImageInputStream(sPath);
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.IMAGE_PNG);
//		    HttpStatus statusCode = HttpStatus.OK;
		    ResponseEntity<byte[]> entity = docFeign.viewImage(docNo, docBizNo);
			return entity;
		}
	//查看缩略图
	@RequestMapping("/viewCompressImage")
	public ResponseEntity<byte[]> viewCompressImage(String docNo,String docBizNo) throws Exception 
	{
//		DocManage docManage = new DocManage();
//		docManage.setDocNo(docNo);
//		docManage.setDocBizNo(docBizNo);
//		docManage = this.docFeign.getByIdDocManage(docManage);
//		String sPath = docManage.getCompressPath();
//		InputStream inputStream = ImageUtil.getImageInputStream(sPath);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//	    HttpStatus statusCode = HttpStatus.OK;
//	    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		 ResponseEntity<byte[]> entity = docFeign.viewCompressImage(docNo, docBizNo);
		return entity;
	}
	/**
	 * @Description long转文件大小KB单位方法,保留兩位小數點
	 * @param bytes
	 * @return
	 */
	public Double bytes2kb(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024);
		double returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
				.doubleValue();
		BigDecimal b = new BigDecimal(returnValue);
		returnValue = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return returnValue;
	}
	/**  
	    * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1  
	    * @param  s 需要得到长度的字符串
	    * @return int 得到的字符串长度  
	    */   
	   public static int length(String s) {  
	       if (s == null) {
               return 0;
           }
	       char[] c = s.toCharArray();  
	       int len = 0;  
	       for (int i = 0; i < c.length; i++) {  
	           len++;  
	           if (!isLetter(c[i])) {  
	               len++;  
	           }  
	       }  
	       return len;  
	   } 
	   public static boolean isLetter(char c) {   
	       int k = 0x80;   
	       return c / k == 0 ? true : false;   
	   } 

	// 选择文件后多文件打包下载(模板)
    @RequestMapping("/getZipFileDownloadForSelectTemplet")
	public ResponseEntity<byte[]> getZipFileDownloadForSelectTemplet(String appId,String cusNo,String docBizNo,String showName) throws Exception {
		ZipTool zt = new ZipTool();
		if(StringUtil.isNotEmpty(docBizNo)){
			String filePath = "";
			String fileName = "";
			String newFileName = "";
			String suffix = "";
			String sysFilePath = PropertiesUtil.getPageOfficeConfigProperty("office_sys_template");
			if(StringUtil.isEmpty(sysFilePath)){
				logger.error("pageOffice路径office_sys_template没有配置");
			}
			String[] docs = docBizNo.split(",");
			if(docs.length> 0){
				MfTemplateBizConfig templateBizConfig = null;
				for (int i= 0; i<docs.length; i++ ){
					if(StringUtil.isNotBlank(docs[i])){
						templateBizConfig = new MfTemplateBizConfig();
						templateBizConfig.setTemplateBizConfigId(docs[i]);
						templateBizConfig = this.docFeign.getById(templateBizConfig);
					}
					if(null != templateBizConfig){
						if(StringUtil.isNotEmpty(templateBizConfig.getDocFileName())){
							//保存后的文档
							filePath = templateBizConfig.getDocFilePath();
							fileName = templateBizConfig.getDocFileName();
						}else {
							//原始的文档
							if(StringUtil.isEmpty(sysFilePath)){
								continue;
							}else{
								filePath = sysFilePath;
								fileName = templateBizConfig.getTemplateNameEn();
							}
						}
						newFileName = templateBizConfig.getTemplateNameZh();
						suffix = fileName.substring(fileName.lastIndexOf("."));
						if(StringUtil.isNotEmpty(showName)){
							newFileName = newFileName + "_"+ i + suffix;
						}else{
							newFileName = newFileName + suffix;
						}
						zt.addFileReName(filePath+fileName,newFileName);
					}
				}
			}
		}
		String fileName = "模板文件_"+WaterIdUtil.getWaterId()+".zip";
		if(StringUtil.isNotEmpty(cusNo)){
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = this.cusFeign.getByIdMfCusCustomer(mfCusCustomer);
			fileName = mfCusCustomer.getCusName()+"_"+WaterIdUtil.getWaterId()+".zip";
		}
		if(StringUtil.isNotEmpty(showName)){
			fileName = showName + "_"+WaterIdUtil.getWaterId()+".zip";;
		}
		fileName = new String(fileName.getBytes(),"ISO8859-1");
		InputStream inputStream = new ByteArrayInputStream(zt.getOutputStream());
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attchement;filename="+fileName);
	    HttpStatus statusCode = HttpStatus.OK;
	    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		return entity;
	}

	// 多文件打包下载(要件)
	@RequestMapping("/getZipFileDownloadByPath")
	public ResponseEntity<byte[]> getZipFileDownloadByPath(String filePath,String fileName) throws Exception {
		ResponseEntity<byte[]> entity = this.docFeign.getZipFileDownloadByPath(filePath, fileName);
		return entity;
	}
}
