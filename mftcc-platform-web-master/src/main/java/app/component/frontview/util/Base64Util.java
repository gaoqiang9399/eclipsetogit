package app.component.frontview.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.web.multipart.MultipartFile;

import app.tech.upload.UploadUtil;
import cn.mftcc.util.StringUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
	/**
	 * 将文件加密为base64字符
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String encodeBase64ByPath(String path) throws IOException{
		File file=new File(path);
		if(!file.exists()){
			return "";
		}
		FileInputStream inputFile= null;
		byte[] buffer = null;
		try {
			inputFile = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			//inputFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputFile != null) {
				inputFile.close();
			}
		}
		  return new BASE64Encoder().encode(buffer);
	}
	/**
	 * 将文件加密为base64字符
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String encodeBase64ByFile(File file) throws IOException{
		if(!file.exists()){
			return "";
		}
		FileInputStream inputFile=null;
		byte[] buffer =null;
        try {
            inputFile = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputFile != null) {
                inputFile.close();
            }
        }
		  return new BASE64Encoder().encode(buffer);
	}
	 /**
	  * 将base64字符解码，保存文件
	  * @param base64Code
	  * @param targetPath
	  * @throws Exception
	  */

	 public static void decoderBase64File(String base64Code, String targetPath,String FileName)
	   throws Exception {
	  byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
	  File file=new File(targetPath);
	  FileOutputStream out =null;
		 try {
			 if (!file.exists() && !file.isDirectory()) {
				 System.out.println(targetPath + "目录不存在，需要创建");
				 //创建目录
				 file.mkdir();
			 }
			 out = new FileOutputStream(targetPath + File.separator + FileName);
			 out.write(buffer);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 if (out != null) {
				 out.close();
			 }
		 }
	 }
	 /**
	  * 保存base64字符到文件
	  * @param base64Code
	  * @param targetPath
	  * @throws Exception
	  */
	public static void saveBase64StrToFile(String base64Code, String targetPath)
			   throws Exception {
		  byte[] buffer = base64Code.getBytes();
		FileOutputStream out =null;
		try {
			out = new FileOutputStream(targetPath);
			out.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	 }
	/**
	 * 将页面传来的file转为base64字符串
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String encodeBase64MultFile(MultipartFile file) throws IOException{
		  return new BASE64Encoder().encode(file.getBytes());
	}
	/**
	 * base64加密字符串
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String str) throws UnsupportedEncodingException{
		return new BASE64Encoder().encodeBuffer(str.getBytes("UTF8"));
	}
	/**
	 * 解密base64字符串
	 * @param baseStr
	 * @return
	 * @throws IOException
	 */
	public static String decode(String baseStr) throws IOException{
		byte[] de=new BASE64Decoder().decodeBuffer(baseStr);
		return new String(de,"UTF8");
	}
	/**
	 * 根据数据库的文件名获取base64字符串，带前缀。
	 * 仅供前端交易模块使用
	 * @param fileName 数据库存的文件名
	 * @return
	 * @throws Exception 
	 */
	public static String getFrontViewImgStr(String fileName) throws Exception{
		String base64Str="";
		if(StringUtil.isNotEmpty(fileName)){
			String name = "";//--文件名
			String extention = "";//--扩展名
			int i = fileName.lastIndexOf(".");
			if(i>-1 && i<fileName.length()){
				name = fileName.substring(0, i); //--文件名
				extention = fileName.substring(i+1); //--扩展名
				String filePath=UploadUtil.getFileUploadPath()+File.separator+"vw"+File.separator+fileName;
				String base64=encodeBase64ByPath(filePath);//base64图片字符
				String pre="data:image/"+extention+";base64,";//base64前缀
				base64Str=pre+base64;
			}
		}
		return base64Str;
	}
	/**
	 * 保存base64字符串到前端交易文件夹
	 * 仅供前端交易使用
	 * @param base64
	 * @param fileName
	 * @throws Exception
	 */
	public static void saveFrontViewStrImg(String base64,String fileName) throws Exception{
		decoderBase64File(base64, UploadUtil.getFileUploadPath()+File.separator+"vw", fileName);
	}
	/**
	 *给base64字符串加上base64前缀(限图片)
	 */
	public static String addFrontStr(String fileName,String baseStr){
		if(StringUtil.isNotEmpty(fileName)){
			String extention = "";//--扩展名
			int i = fileName.lastIndexOf(".");
			if(i>-1 && i<fileName.length()){
				extention = fileName.substring(i+1); //--扩展名
				String pre="data:image/"+extention+";base64,";//base64前缀
				baseStr=pre+baseStr;
			}else{
				baseStr="data:image/jpg;base64,"+baseStr;
			}
		}else{
			baseStr="data:image/jpg;base64,"+baseStr;
		}
		return baseStr;
	}
}
