package app.component.interfaces.appinterface.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.BaseFormBean;

import app.base.imageTools.Base64Util;
import app.base.imageTools.ImgCompress;
import app.component.doc.entity.DocManage;
import app.component.interfaces.appinterface.feign.AppDocManageFeign;
import app.tech.upload.UploadUtil;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;


/**
 * 要件信息管理的Action类
 * @author zhang_dlei
 * @date 2017-06-15 下午5:58:30
 */
@Controller
@RequestMapping("/appDocManage")
public class AppDocManageController extends BaseFormBean {
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private AppDocManageFeign appDocManageFeign;
	
	//异步参数
	
	
	/**
	 * 获取上传要件列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDocInfoListAjax")
	@ResponseBody
	public Map<String, Object> getDocInfoListAjax(String ajaxData) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		
		try {
			//调用Bo层服务
			dataMap = appDocManageFeign.getDocInfoList(ajaxData);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取上传要件列表异常");
//			logger.error("获取上传要件列表异常",e);
		}
		return dataMap;
	}
	
	
	/**
	 * 上传要件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadDocFileAjax")
	@ResponseBody
	public Map<String, Object> uploadDocFileAjax(String regNo, String relNo, String uploadFileName, String docType,
			int fileNameLength, File upload, String docBizNo, String docSplitNo, String cusNo, String uploadContentType) throws Exception{
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//验证文件名长度
			String fileName = uploadFileName;
			if(length(fileName) > fileNameLength){
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg",MessageEnum.ERROR_FILE_NAME_TOOLONG.getMessage());
				return dataMap;
	        }
			StringBuilder path = new StringBuilder();
			path.append(UploadUtil.getFileUploadPath());
			UUID uuid = UUID.randomUUID();
			path.append(File.separator).append(relNo).append(File.separator).append(docType);
			path.append(File.separator).append(uuid.toString().replaceAll("-",""));
			String realpath = path.toString();
			if (path.indexOf("../")>-1||path.indexOf("./")>-1||path.indexOf("..%2F")>-1||path.indexOf("..%5C")>-1) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "文件上传路径不合法！");
				return dataMap;
			}
			DocManage docManage = new DocManage();
			if(uploadContentType.startsWith("image")){
				System.out.println("上传的是图片");
				 ImgCompress imgCom = new ImgCompress(upload);  
				 File tempFile = imgCom.resizeFix(105, 75);
				 String base64Str = Base64Util.getImageStr(tempFile);
				 docManage.setImgBase64Blob(base64Str.getBytes());
				 docManage.setImgBase64(base64Str);
				 tempFile.delete();
			}
			docManage.setRegDate(DateUtil.getDate());
			docManage.setRegNo(regNo);
			docManage.setDocName(fileName);
			docManage.setDocBizNo(docBizNo);
			docManage.setDocSplitNo(docSplitNo);
			docManage.setDocAddr(realpath +File.separator+ fileName);
			docManage.setDocEncryptAddr(docManage.getDocAddr());
			docManage.setDocType(docType);
			docManage.setRelNo(relNo);
			docManage.setCusNo(cusNo);
			dataMap = appDocManageFeign.insertDocManage(realpath ,upload ,docManage,request);
			
		}catch (Exception e) {
//			logger.error("上传文件失败异常",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "上传文件失败异常");
		}
		return dataMap;
	}
	
	/**  
    * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1  
    * @param String s 需要得到长度的字符串  
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

}
