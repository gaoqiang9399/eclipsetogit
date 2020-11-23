/**
 *  此类注释掉，如果有使用的方法请使用DocUpLoadController中的方法，如果类中不存在请联系基础项目组
 */

//package app.component.doc.controller;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.core.domain.screen.FormData;
//import com.core.service.screen.FormService;
//import com.core.struts.ActionContext;
//import com.core.struts.BaseFormBean;
//
//import app.base.User;
//import app.base.imageTools.ImgCompress;
//import app.component.app.entity.MfBusAppKind;
//import app.component.appinterface.AppInterfaceFeign;
//import app.component.common.BizPubParm;
//import app.component.common.ZipCompressing;
//import app.component.cus.entity.MfCusCustomer;
//import app.component.cusinterface.CusInterfaceFeign;
//import app.component.doc.entity.DocManage;
//import app.component.doc.entity.DocTypeConfig;
//import app.component.doc.feign.DocManageFeign;
//import app.component.doc.feign.DocTypeConfigFeign;
//import app.component.docinterface.DocInterfaceFeign;
//import app.component.nmd.entity.ParmDic;
//import app.component.pact.entity.MfBusPact;
//import app.component.pactinterface.PactInterfaceFeign;
//import app.tech.oscache.CodeUtils;
//import app.tech.upload.ImageUtil;
//import app.tech.upload.UploadUtil;
//import app.util.Office2PDF;
//import cn.mftcc.common.MessageEnum;
//import cn.mftcc.util.MathExtend;
//import cn.mftcc.util.StringUtil;
//
///**
// * Title: DocBizManageController.java Description:
// * 
// * @author:@dhcc.com.cn
// * @Tue Jan 26 11:15:50 GMT 2016
// **/
//@Controller
//@RequestMapping("/docUpload")
//public class DocUploadControllerOld extends BaseFormBean {
//	@Autowired
//	private HttpServletRequest request;
//	@Autowired
//	private HttpServletResponse response;
//	@Autowired
//	private DocManageFeign docManageFeign;
//	@Autowired
//	private DocInterfaceFeign docInterfaceFeign;
//	@Autowired
//	private DocTypeConfigFeign docTypeConfigFeign;
//	@Autowired
//	private AppInterfaceFeign appInterfaceFeign;
//	@Autowired
//	private CusInterfaceFeign cusInterfaceFeign;
//	@Autowired
//	private PactInterfaceFeign pactInterfaceFeign;
//
//	@RequestMapping("/uploadFile")
//	@ResponseBody public Map<String, Object> uploadFile(String uploadFileName, String relNo, String docType, File upload,
//			String uploadContentType, String docBizNo, String docSplitNo, String cusNo, String scNo) throws Exception {
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		String fileName = uploadFileName;
//		// 添加文件上传格式验证，过滤可能威胁系统安全非法后缀
//		if (fileName.endsWith(".exe") || fileName.endsWith(".jsp") || fileName.endsWith(".bat")
//				|| fileName.endsWith(".dll") || fileName.endsWith(".com") || fileName.endsWith(".sh")
//				|| fileName.endsWith(".py")) {
//			dataMap.put("flag", false);
//			dataMap.put("msg", MessageEnum.ERROR_UPLOAD_FILE_TYPE.getMessage("合法"));
//			// logger.info("上传文件格式不合法,文件名：" + fileName);
//			return dataMap;
//		}
//		int fileNameLength = 50;
//		if (length(fileName) > fileNameLength) {
//			dataMap.put("flag", false);
//			dataMap.put("msg", MessageEnum.ERROR_FILE_NAME_TOOLONG.getMessage());
//			// logger.info("文件名过长，不能超过" + fileNameLength + "，实际长度为：" +
//			// length(fileName));
//			return dataMap;
//		}
//		StringBuilder path = new StringBuilder();
//		StringBuilder pathNew = new StringBuilder();
//		path.append(UploadUtil.getFileUploadPath());
//		UUID uuid = UUID.randomUUID();
//		path.append(File.separator).append(relNo).append(File.separator).append(docType);
//		path.append(File.separator).append(uuid.toString().replaceAll("-", ""));
//		pathNew.append(File.separator).append(relNo).append(File.separator).append(docType);
//		pathNew.append(File.separator).append(uuid.toString().replaceAll("-", ""));
//		String realpath = path.toString();
//		String realpathNew = pathNew.toString();
//		DocManage docManage = new DocManage();
//		if (path.indexOf("../") > -1 || path.indexOf("./") > -1 || path.indexOf("..%2F") > -1
//				|| path.indexOf("..%5C") > -1) {
//			dataMap.put("flag", false);
//			dataMap.put("msg", "文件上传路径不合法！");
//			// logger.info("文件上传路径不合法！path:" + path.toString());
//			return dataMap;
//		}
//
//		InputStream is = null;
//		OutputStream os = null;
//		try {
//			File f = new File(realpath);
//			if (!f.exists()) {
//				f.mkdirs();
//			}
//			is = new FileInputStream(upload);
//			os = new FileOutputStream(new File(realpath, fileName));
//			docManage.setDocSize(this.bytes2kb(is.available()));
//			byte[] buffer = new byte[1024];
//			int length = 0;
//			while (-1 != (length = is.read(buffer, 0, buffer.length))) {
//				os.write(buffer);
//			}
//			if (uploadContentType.startsWith("image")) {
//				ImgCompress imgCom = new ImgCompress(upload, realpath, fileName);
//				File tempFile = imgCom.resizeFix(105, 75);
//				docManage.setCompressPath(tempFile.getPath());
//				// String base64Str = Base64Util.getImageStr(tempFile);
//				// docManage.setImgBase64Blob(base64Str.getBytes());
//				// docManage.setImgBase64(base64Str);
//			}
//			docManage.setRegDate(User.getSysDate(request));
//			docManage.setRegNo(User.getRegNo(request));
//			docManage.setOrgNo(User.getOrgNo(request));
//			docManage.setDocName(fileName);
//			docManage.setDocBizNo(docBizNo);
//			docManage.setScNo(scNo);
//			docManage.setDocSplitNo(docSplitNo);
//			docManage.setDocAddr(realpathNew + File.separator + fileName);
//			docManage.setDocEncryptAddr(docManage.getDocAddr());
//			docManage.setDocType(docType);
//			docManage.setRelNo(relNo);
//			docManage.setCusNo(cusNo);
//			docManageFeign.insert(docManage);
//			dataMap.put("path", path + "/" + fileName);
//			dataMap.put("docType", docType);
//			dataMap.put("docManage", docManage);
//			String uploadType = docManage.getUploadType();
//			if (StringUtil.isNotEmpty(uploadType)) {
//				String path1 = UploadUtil.getFileUploadPathByType(uploadType);
//				// 根据路径类型获取上传的前缀
//				docManage.setDocAddr(path1 + docManage.getDocAddr());
//				docManage.setDocEncryptAddr(path1 + docManage.getDocEncryptAddr());
//			}
//			dataMap.put("flag", true);
//			dataMap.put("msg", MessageEnum.SUCCEED_UPLOAD.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			// logger.error("文件上传失败：", e.getMessage());
//			throw e;
//		} finally {
//			if (os != null) {
//				try {
//					os.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			if (is != null) {
//				try {
//					is.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return dataMap;
//	}
//
//	@RequestMapping("/delFile")
//	@ResponseBody public Map<String, Object> delFile(String docNo, String docBizNo) throws Exception {
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		DocManage docManage = new DocManage();
//		docManage.setDocNo(docNo);
//		docManage.setDocBizNo(docBizNo);
//		docManage = docManageFeign.getById(docManage);
//		String sPath = docManage.getDocEncryptAddr();
//		String cPath = docManage.getCompressPath();// 缩略图
//		if (cPath != null && "".equals(cPath)) {
//			File cfile = new File(cPath);
//			if (!cfile.exists()) { // 不存在返回 false
//			} else {
//				// 判断是否为文件
//				if (cfile.isFile()) { // 为文件时调用删除文件方法
//					deleteFile(cPath);
//				} else { // 为目录时调用删除目录方法
//					deleteDirectory(cPath);
//				}
//			}
//		}
//		try {
//			File file = new File(sPath);
//			// 判断目录或文件是否存在
//			if (!file.exists()) { // 不存在返回 false
//				docManageFeign.delete(docManage);
//				dataMap.put("msg", MessageEnum.NO_FILE.getMessage(""));
//			} else {
//				// 判断是否为文件
//				if (file.isFile()) { // 为文件时调用删除文件方法
//					deleteFile(sPath);
//				} else { // 为目录时调用删除目录方法
//					deleteDirectory(sPath);
//				}
//				docManageFeign.delete(docManage);
//				dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return dataMap;
//	}
//
//	// 下载
//	@RequestMapping("/getFileDownload")
//	@ResponseBody
//	public InputStream getFileDownload(String docNo, String docBizNo) throws Exception {
//		DocManage docManage = new DocManage();
//		docManage.setDocNo(docNo);
//		docManage.setDocBizNo(docBizNo);
//		docManage = docManageFeign.getById(docManage);
//		String sPath = docManage.getDocEncryptAddr();
//		File file = new File(sPath);
//		InputStream inputStream = null;
//		if (!file.exists()) { // 不存在返回 false
//			System.err.println(
//					"Can not find a Java.io.InputStream with the name [inputStream] in the invocation stack. Check the <param name=\"inputName\"> tag specified for this action.检查action中文件下载路径是否正确.");
//		} else {
//			inputStream = new BufferedInputStream(new FileInputStream(sPath));
//		}
//		return inputStream;
//	}
//
//	@RequestMapping("/viewFileAjax")
//	@ResponseBody
//	public Map<String, Object> viewFileAjax(String docNo, String docBizNo, String docAddr) throws Exception {
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		String sPath = docAddr;
//		Office2PDF op = new Office2PDF();
//		String viewPath = op.viewToPdf(sPath, docBizNo + "_" + docNo, false);
//		if (viewPath == null) {
//			dataMap.put("flag", "error");
//			dataMap.put("msg", "文件不存在，或者不支持的文档类型！");
//		} else {
//			String fileName = viewPath.substring(viewPath.lastIndexOf(File.separator) + 1);
//			dataMap.put("flag", "success");
//			dataMap.put("msg", "转换成功！");
//			dataMap.put("viewPath", fileName);
//		}
//		return dataMap;
//	}
//
//	// 多文件打包下载
//	@RequestMapping("/getZipFileDownload")
//	@ResponseBody
//	public InputStream getZipFileDownload(String docBizNo, String docSplitNo, String cusName, String docTypeName,
//			String docSplitName) throws Exception {
//		List<File> files = new ArrayList<File>();
//		StringBuilder path = new StringBuilder();
//		path.append(UploadUtil.getFileUploadPath());
//		path.append(File.separator).append(docBizNo + "-" + docSplitNo + ".zip");
//		String sPath = path.toString();
//		ZipCompressing.zipFiles(files, sPath);
//		String fileName = cusName + "-" + docTypeName + "-" + docSplitName + ".zip";
//		File file = new File(sPath);
//		InputStream inputStream = null;
//		if (!file.exists()) { // 不存在返回 false
//			System.err.println(
//					"Can not find a Java.io.InputStream with the name [inputStream] in the invocation stack. Check the inputName tag specified for this action.检查action中文件下载路径是否正确.");
//		} else {
//			inputStream = new BufferedInputStream(new FileInputStream(sPath));
//		}
//		return inputStream;
//	}
//
//	// 打包下载,完成后删除（未实现）
//	@RequestMapping("/getZipFileDownloadAndDel")
//	@ResponseBody
//	public InputStream getZipFileDownloadAndDel(String docBizNo, String docSplitNo, String cusName, String docTypeName,
//			String docSplitName) throws Exception {
//		List<File> files = new ArrayList<File>();
//		StringBuilder path = new StringBuilder();
//		path.append(UploadUtil.getFileUploadPath());
//		path.append(File.separator).append(docBizNo + "-" + docSplitNo + ".zip");
//		String sPath = path.toString();
//		ZipCompressing.zipFiles(files, sPath);
//		String fileName = cusName + "-" + docTypeName + "-" + docSplitName + ".zip";
//		File file = new File(sPath);
//		InputStream inputStream = null;
//		if (!file.exists()) { // 不存在返回 false
//			System.err.println(
//					"Can not find a Java.io.InputStream with the name [inputStream] in the invocation stack. Check the inputName tag specified for this action.检查action中文件下载路径是否正确.");
//		} else {
//			inputStream = new BufferedInputStream(new FileInputStream(sPath));
//		}
//		return inputStream;
//	}
//
//	/**
//	 * @author czk
//	 * @Description: 根据路径进行下载 date 2016-11-24
//	 */
//	@RequestMapping("/getFileDownload_new")
//	@ResponseBody
//	public void getFileDownload_new(String path) throws Exception {
//		//InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
//		String cusNo = request.getParameter("cusNo");
//		String downTyppe = request.getParameter("downType");
//		MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
//		String cusName = mfCusCustomer.getCusName();
//		String cusBaseType = mfCusCustomer.getCusBaseType();
//		if (BizPubParm.CUS_TYPE_CORP.equals(cusBaseType)) {// 企业
//			cusBaseType = "_企业";
//		} else if (BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)) {// 个人
//			cusBaseType = "_个人";
//		}
//		String fileName = "";
//		if ("1".equals(downTyppe)) {
//			fileName = new String((cusName + cusBaseType + "_资产负债表模板.xls").getBytes(), "ISO8859-1");
//			;
//		} else if ("2".equals(downTyppe)) {
//			fileName = new String((cusName + cusBaseType + "_利润分配表模板.xls").getBytes(), "ISO8859-1");
//		} else if ("3".equals(downTyppe)) {
//			fileName = new String((cusName + cusBaseType + "_现金流量表模板.xls").getBytes(), "ISO8859-1");
//		} else if ("4".equals(downTyppe)) {
//			fileName = new String((cusName + cusBaseType + "_全部模板.xls").getBytes(), "ISO8859-1");
//		} else {
//			fileName = new String((cusName + cusBaseType + "finTemplate.xls").getBytes(), "ISO8859-1");
//		}
//		File file = new File(path);  
//		FileInputStream inputStream = new FileInputStream(file);  
//        byte[] data = new byte[(int)file.length()];  
//        int length = inputStream.read(data);  
//        inputStream.close();  
//      //设置响应头和客户端保存文件名
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("multipart/form-data");
//        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
////        response.setContentType("image/png");  
//  
//        OutputStream stream = response.getOutputStream();  
//        stream.write(data);  
//        stream.flush();  
//        stream.close(); 
////		return void;
//	}
//
//	/**
//	 * @Description: 富友模板下载
//	 * @return
//	 * @throws Exception
//	 * @author: 李伟
//	 * @date: 2017-8-24 下午6:42:52
//	 */
//	@RequestMapping("/getFileDownload_fuyiou")
//	@ResponseBody
//	public InputStream getFileDownload_fuyiou(String path, String fuyiouType) throws Exception {
//		InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
//		String fileName = "";
//		if ("1".equals(fuyiouType)) {
//			fileName = new String(("富友代付.xls").getBytes(), "ISO8859-1");
//		} else {
//			fileName = new String(("富友代收.xls").getBytes(), "ISO8859-1");
//		}
//		return inputStream;
//	}
//
//	/**
//	 * @Description:模板下载
//	 * @return
//	 * @throws Exception
//	 * @author: 李伟
//	 * @date: 2017-9-23 上午11:32:12
//	 */
//	@RequestMapping("/getFileDownload_bus")
//	@ResponseBody
//	public InputStream getFileDownload_bus(String modelFlag, String busExportType, String path) throws Exception {
//		InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
//		String fileName = "";
//		if ("1".equals(modelFlag)) {// 业务导出
//			if ("1".equals(busExportType)) {// 还款计划
//				fileName = new String(("还款计划.xls").getBytes(), "ISO8859-1");
//			}
//		}
//		return inputStream;
//	}
//
//	// form赋值
//	@RequestMapping("/viewIframe")
//	public String viewIframe(Model model, String formId, String docSplitNo, String relNo, String cusNo, String appId,
//			String pactId) throws Exception {
//		ActionContext.initialize(request, response);
//		FormService formService = new FormService();
//		String query = "query";
//		FormData form = null;
//		if (!"".equals(formId) && formId != null && !"undefined".equals(formId)) {
//			// 根据docSplitNo获取文档配置的母版表单编号
//			DocTypeConfig docTypeConfig = new DocTypeConfig();
//			docTypeConfig.setDocSplitNo(docSplitNo);
//			docTypeConfig = docTypeConfigFeign.getById(docTypeConfig);
//			form = formService.getFormData(formId);
//			if (form.getFormId() == null) {// 如果子表单不存在，使用母版表单
//				// logger.error("要件预览的表单" + formId + ".xml文件不存在");
//				form = formService.getFormData(docTypeConfig.getBaseFormNo());
//			}
//			if (form.getFormId() != null) {
//				Map<String, String> parmMap = new HashMap<String, String>();
//				parmMap.put("cusNo", relNo);
//				parmMap.put("appId", relNo);
//				parmMap.put("pactId", relNo);
//				if (StringUtil.isNotEmpty(cusNo)) {
//					parmMap.put("cusNo", cusNo);
//				}
//				if (StringUtil.isNotEmpty(appId)) {
//					parmMap.put("appId", appId);
//					if (StringUtil.isEmpty(pactId)) {
//						MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
//						if (mfBusPact != null) {
//							pactId = mfBusPact.getPactId();
//						}
//					}
//				}
//				if (StringUtil.isNotEmpty(pactId)) {
//					parmMap.put("pactId", pactId);
//				}
//				parmMap.put("pledgeNo", relNo);
//				Object obj = docInterfaceFeign.getDocObject(docTypeConfig, parmMap);
//				if (obj != null) {
//					// 开始处理申请和合同的特殊字段 -- 申请和合同的risk_item表分别添加一个app_id 根据申请号就行处理
//					@SuppressWarnings("unchecked")
//					Map<String, Object> resMap = (Map<String, Object>) obj;
//					if (resMap.get("appId") != null && StringUtil.isNotEmpty(resMap.get("appId").toString())) {
//						// 处理利率的展示
//						Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
//						MfBusAppKind mfBusAppKind = null;
//						mfBusAppKind = new MfBusAppKind();
//						mfBusAppKind.setAppId(resMap.get("appId").toString());
//						mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
//						Object fincRateObj = resMap.get("fincRate");
//						if (fincRateObj != null && !fincRateObj.toString().isEmpty()) {
//							double fincRate = Double.parseDouble(fincRateObj.toString());
//							double showRateMethod = MathExtend.showRateMethod(mfBusAppKind.getRateType(), fincRate,
//									Integer.parseInt(mfBusAppKind.getYearDays()),
//									Integer.parseInt(mfBusAppKind.getRateDecimalDigits()));
//							resMap.put("fincRate", showRateMethod);
//						}
//
//						getObjValue(form, resMap);
//						// 处理利率的单位
//						if (mfBusAppKind != null) {
//							String rateUnit = rateTypeMap.get(mfBusAppKind.getRateType()).getRemark();
//							this.changeFormProperty(form, "fincRate", "unit", rateUnit);
//						}
//					} else {
//						getObjValue(form, obj);
//					}
//				}
//			}
//		}
//		model.addAttribute("form", form);
//		model.addAttribute("query", "");
//		return "/component/doc/webUpload/shenpi";
//	}
//
//	/**
//	 * 删除单个文件
//	 * 
//	 * @param sPath
//	 *            被删除文件的文件名
//	 * @return 单个文件删除成功返回true，否则返回false
//	 */
//	public boolean deleteFile(String sPath) {
//		boolean flag = false;
//		File file = new File(sPath);
//		// 路径为文件且不为空则进行删除
//		if (file.isFile() && file.exists()) {
//			flag = file.delete();
//		}
//		return flag;
//	}
//
//	/**
//	 * 删除目录（文件夹）以及目录下的文件
//	 * 
//	 * @param sPath
//	 *            被删除目录的文件路径
//	 * @return 目录删除成功返回true，否则返回false
//	 */
//	public boolean deleteDirectory(String sPath) {
//		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
//		if (!sPath.endsWith(File.separator)) {
//			sPath = sPath + File.separator;
//		}
//		File dirFile = new File(sPath);
//		// 如果dir对应的文件不存在，或者不是一个目录，则退出
//		if (!dirFile.exists() || !dirFile.isDirectory()) {
//			return false;
//		}
//		boolean flag = true;
//		// 删除文件夹下的所有文件(包括子目录)
//		File[] files = dirFile.listFiles();
//		for (int i = 0; i < files.length; i++) {
//			// 删除子文件
//			if (files[i].isFile()) {
//				flag = deleteFile(files[i].getAbsolutePath());
//				if (!flag)
//					break;
//			} // 删除子目录
//			else {
//				flag = deleteDirectory(files[i].getAbsolutePath());
//				if (!flag)
//					break;
//			}
//		}
//		if (!flag)
//			return false;
//		// 删除当前目录
//		if (dirFile.delete()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	// 查看图片
//	@RequestMapping("/viewImage")
//	@ResponseBody
//	public InputStream viewImage(String docNo, String docBizNo) throws Exception {
//		DocManage docManage = new DocManage();
//		docManage.setDocNo(docNo);
//		docManage.setDocBizNo(docBizNo);
//		docManage = docManageFeign.getById(docManage);
//		String sPath = docManage.getDocEncryptAddr();
//		InputStream inputStream = ImageUtil.getImageInputStream(sPath);
////		if (null == inputStream)
////			return "imageNotExist";
//		return inputStream;
//	}
//
//	// 查看缩略图
//	@RequestMapping("/viewCompressImage")
//	@ResponseBody
//	public InputStream viewCompressImage(String docNo, String docBizNo) throws Exception {
//		DocManage docManage = new DocManage();
//		docManage.setDocNo(docNo);
//		docManage.setDocBizNo(docBizNo);
//		docManage = docManageFeign.getById(docManage);
//		String sPath = docManage.getCompressPath();
//		InputStream inputStream = ImageUtil.getImageInputStream(sPath);
////		if (null == inputStream)
////			return "imageNotExist";
//		return inputStream;
//	}
//
//	/**
//	 * @Description long转文件大小KB单位方法,保留兩位小數點
//	 * @param bytes
//	 * @return
//	 */
//	public Double bytes2kb(long bytes) {
//		BigDecimal filesize = new BigDecimal(bytes);
//		BigDecimal megabyte = new BigDecimal(1024);
//		double returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).doubleValue();
//		BigDecimal b = new BigDecimal(returnValue);
//		returnValue = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//		return returnValue;
//	}
//
//	/**
//	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
//	 * 
//	 * @param String
//	 *            s 需要得到长度的字符串
//	 * @return int 得到的字符串长度
//	 */
//	public static int length(String s) {
//		if (s == null)
//			return 0;
//		char[] c = s.toCharArray();
//		int len = 0;
//		for (int i = 0; i < c.length; i++) {
//			len++;
//			if (!isLetter(c[i])) {
//				len++;
//			}
//		}
//		return len;
//	}
//
//	public static boolean isLetter(char c) {
//		int k = 0x80;
//		return c / k == 0 ? true : false;
//	}
//
//}
