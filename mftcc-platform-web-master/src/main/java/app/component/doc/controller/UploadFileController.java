package app.component.doc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;

import com.core.struts.BaseFormBean;

import app.component.common.FileUtil;
import app.component.doc.entity.Upload;
import app.tech.upload.ImageUtil;
import app.tech.upload.UploadUtil;
import cn.mftcc.common.MessageEnum;

/**
 * @author collapsar
 * @date 2016-08-03
 */
@Controller
@RequestMapping("/uploadFile")
public class UploadFileController extends BaseFormBean {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/uploadForConTemplate")
	public String uploadForConTemplate() throws Exception {
		Upload form = new Upload();
		String p = UploadUtil.getFileUploadPath() + "/cont/";
		String fileName = form.getUploadFileName();
		String msg = "";
		if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")
				|| fileName.endsWith(".jpeg")) {
			FileUtil.createDir(p);
			if ($upload(p + form.getUploadFileName())) {
				// String
				// dp=Base64.newEncode(p+form.getUploadFileName());//暂时不使用加密
				String dp = p + form.getUploadFileName();
				// ProdConTemplate prodConTemplate=new ProdConTemplate();
				// prodConTemplate.setTemplatePath(dp);
				// prodConTemplate.setTemplateName(form.getUploadFileName());
				// prodConTemplate.setTemplateNo(primaryKey);
				// prodConTemplateFeign.updatePath(prodConTemplate);
				msg = dp;
				returnMsg(msg);
			} else {
				msg = "false;" + msg;
				returnMsg(msg);
			}
		} else {
			msg = "false;" + MessageEnum.ERROR_UPLOAD_FILE_TYPE.getMessage("jpg、jpeg、gif、png格式的");
			returnMsg(msg);
			return null;
		}
		return null;
	}

	private boolean $upload(String filePath) {
		Upload form = new Upload();
		if (null == form.getUpload()) {
			return false;
		}
		File file = new File(filePath);
		try {
			if (FileUtil.copy(form.getUpload(), file, true)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RequestMapping("/viewImage")
	@ResponseBody
	public ResponseEntity<byte[]> viewImage(String fileName) throws Exception {
		// inputStream =
		// ImageUtil.getImageInputStream(Base64.newDecode(srcPath));//暂时不使用加密
		String serverpath = UploadUtil.getFileUploadPath() + File.separator +"tmp" + File.separator;
		fileName = URLDecoder.decode(fileName, "UTF-8");
		// if (fileName.endsWith(".jpg") || fileName.endsWith(".png") ||
		// fileName.endsWith(".gif")
		// || fileName.endsWith(".jpeg")) {
		// // 过滤“../xxx”类型路径，避免向上文件遍历语句注入
		// if (fileName.indexOf("../") > -1 || fileName.indexOf("..%2F") > -1 ||
		// fileName.indexOf("..%5C") > -1) {
		//// model.addAttribute("query", "");
		// return "/creditapp/doc/DocManage_ImageNotExist";
		// }
		// inputStream = ImageUtil.getImageInputStream(serverpath + fileName);
		// if (null == inputStream)
		//// model.addAttribute("query", "");
		// return "/creditapp/doc/DocManage_ImageNotExist";
		// } else {
		//// model.addAttribute("query", "");
		// return "/creditapp/doc/DocManage_ImageNotExist";
		// }
		File file = new File(serverpath + fileName);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), HttpStatus.CREATED);
	}

	/**
	 * 打开默认头像
	 */
	@RequestMapping("/viewImageDetail")
	@ResponseBody
	public ResponseEntity<byte[]> viewImageDetail(String srcPath) throws Exception {
		// inputStream =
		// ImageUtil.getImageInputStream(Base64.newDecode(srcPath));//暂时不使用加密
		String serverpath = request.getSession().getServletContext().getRealPath("/");
		srcPath = serverpath + srcPath;
		File file = new File(srcPath);
		// InputStream inputStream = ImageUtil.getImageInputStream(srcPath);
		// if (null == inputStream)
		// return "/creditapp/doc/DocManage_ImageNotExist";
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), HttpStatus.CREATED);
	}

	/**
	 * 
	 * 方法描述： 展现上传的头像
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-26 上午10:34:24
	 */
	@RequestMapping("/viewUploadImageDetail")
	public ResponseEntity<byte[]> viewUploadImageDetail(String srcPath, String fileName) throws Exception {

		// 设置请求头内容,告诉浏览器代开下载窗口
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		// inputStream =
		// ImageUtil.getImageInputStream(Base64.newDecode(srcPath));//暂时不使用加密
		String serverpath = UploadUtil.getFileUploadPath() + File.separator +"cusHeadImg" + File.separator;;
		srcPath = URLDecoder.decode(srcPath, "UTF-8");
		String srcPathTmp = serverpath + srcPath;
		InputStream inputStream = ImageUtil.getImageInputStream(srcPathTmp);
		File file = new File(srcPathTmp);

		if (null == inputStream) {
			serverpath = request.getSession().getServletContext().getRealPath("/");
			srcPathTmp = serverpath + srcPath;
			inputStream = ImageUtil.getImageInputStream(srcPathTmp);
			file = new File(srcPathTmp);
			if (null != inputStream) {
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
			}
		} else {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		}

		if (null == inputStream) {
			serverpath = request.getSession().getServletContext().getRealPath("/");
			if (null == fileName || fileName.isEmpty()) {
				fileName = "op_user.jpg";
			}
			srcPathTmp = serverpath + "themes/factor/images/" + fileName;
			file = new File(srcPathTmp);
			inputStream = ImageUtil.getImageInputStream(srcPathTmp);
		}

		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);

	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/upload", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String ,Object> upload(String formNote, @RequestParam(value = "upload", required = false) MultipartFile multipartFile) throws Exception {
		Map<String ,Object> dataMap = new HashMap<String ,Object>();
		String fileName = multipartFile.getOriginalFilename();
		String serverpath = UploadUtil.getFileUploadPath() + File.separator +"tmp" + File.separator;
		if(formNote != null && "upload".equals(formNote)){
			 serverpath = UploadUtil.getFileUploadPath()+ File.separator +"cusHeadImg" + File.separator;
		}
		String msg = "";
		if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".gif")
				&& !fileName.endsWith(".jpeg")) {
			msg = "false;" + MessageEnum.ERROR_UPLOAD_FILE_TYPE.getMessage("jpg、jpeg、gif、png格式的");
			returnMsg(msg);
			return null;
		}
		File file = new File(serverpath);
		if (!file.exists()) {
			file.mkdir();
		}
		 //将上传文件保存到一个目标文件当中
		multipartFile.transferTo(new File(serverpath + File.separator + fileName));
		dataMap.put("flag", "success");
		dataMap.put("fileName", fileName);
		return dataMap;
	}

	@RequestMapping("/fileDownload")
	@ResponseBody
	public ResponseEntity<byte[]> fileDownload(String srcPath) throws Exception {
		String[] ar = srcPath.split("/");
		String fileName = ar[ar.length - 1];
		// String dpath=Base64.newDecode(srcPath);//暂时不使用加密
		File file = new File(srcPath);
		// InputStream inputStream = null;
		// if (!file.exists()) { // 不存在返回 false
		// System.err.println(
		// "Can not find a Java.io.InputStream with the name [inputStream] in
		// the invocation stack. Check the <param name=\"inputName\"> tag
		// specified for this action.检查action中文件下载路径是否正确.");
		// } else {
		// inputStream = new BufferedInputStream(new FileInputStream(srcPath));
		// }
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), HttpStatus.CREATED);
	}

	private long getFileSizes(File f) {
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);
				s = fis.available();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return s;
	}

	private void returnMsg(String msg) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		if (null == msg) {
			msg = "";
		}
		response.getWriter().write(msg);
	}

	/**
	 * 
	 * 方法描述：删除文件
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-19 下午8:08:27
	 */
	@RequestMapping("/delFile")
	@ResponseBody
	public Map<String,Object> delFile(String srcPath) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String serverpath = UploadUtil.getFileUploadPath();
		srcPath = serverpath + srcPath;
		try {
			File file = new File(srcPath);
			if (file.exists()) {
				// 判断文件是否存在
				if (file.isFile()) {
					deleteFile(srcPath);
					dataMap.put("flag", "success");
				} else { // 为目录时调用删除目录方法
					deleteDirectory(srcPath);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
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
			file.delete();
			flag = true;
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

}