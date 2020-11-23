package app.tech.upload;

import java.util.Map;

import app.base.SpringUtil;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;

/**
 * 此方法已弃用，请使用propertiesUtil
 * 
 */

public final class UploadUtil {
	private static Map<String,String> uploadMap;
	
	static{
		YmlConfig yc = SpringUtil.getBean(YmlConfig.class);
		uploadMap = yc.getUpload();
	}
	
	/**
	 * 根据属性名获取upload.properties中对应属性值
	 * @return value
	 */
	public static String getUploadPropValue(String name) throws Exception{
		return uploadMap.get(name);
	}
	/**
	 * 获取文件上传路径
	 * @return value
	 */
	public static String getFileUploadPath() throws Exception{
		String value = null;
		try {
			//获取上传路径类型
			String uploadType = uploadMap.get("uploadFileType");
			uploadType  = StringUtil.isNotEmpty(uploadType)?uploadType.trim():"serv0";
			if("serv1".equals(uploadType)){
				value = uploadMap.get("uploadFilePath_serv1").trim();
			}else if("serv2".equals(uploadType)){
				value = uploadMap.get("uploadFilePath_serv2").trim();
			}else if("serv3".equals(uploadType)){
				value = uploadMap.get("uploadFilePath_serv3").trim();
			}else{
				value = uploadMap.get("uploadFilePath").trim();
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * 
	 * 方法描述： 根据类型获取上传的路径
	 * @param type
	 * @return
	 * @throws Exception
	 * String
	 * @author lzshuai
	 * @date 2017-11-8 下午1:54:10
	 */
	public static String getFileUploadPathByType(String type) throws Exception{
		String value = null;
		try {
			//获取上传路径类型
			if("serv1".equals(type)){
				value = uploadMap.get("uploadFilePath_serv1").trim();
			}else if("serv2".equals(type)){
				value = uploadMap.get("uploadFilePath_serv2").trim();
			}else if("serv3".equals(type)){
				value = uploadMap.get("uploadFilePath_serv3").trim();
			}else{
				value = uploadMap.get("uploadFilePath").trim();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 上传路径的类型   2017年11月8日 lzs
	 * @return value
	 */
	public static String getUploadFileType() throws Exception{
		String value = null;
		try {
			//获取上传路径的类型，不能为空，如果为空， 系统报错
			value = uploadMap.get("uploadFileType");
			value = StringUtil.isNotEmpty(value)?value.trim():"serv0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 获取文件存放路径(上传目录与实际存放路径可能不同,所以分成两个方法)
	 * @return value
	 */
	public static String getFileRealPath() throws Exception{
		String value = null;
		try {
			value = uploadMap.get("fileRealPath").trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * 删除表单
	 * @param String formId
	 * @return value
	 */
	/*public static String delFile(String formId) throws Exception{
		Properties prop = new Properties();
		String value = null;
		InputStream in = UploadUtil.class.getClassLoader().getResourceAsStream(PROP_FILE);
		try {
			prop.load(in);
			value = prop.getProperty("uploadFilePath").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}*/
	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   }  
}